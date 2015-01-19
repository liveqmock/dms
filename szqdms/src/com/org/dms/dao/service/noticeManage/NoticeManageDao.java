package com.org.dms.dao.service.noticeManage;

import java.sql.SQLException;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.service.MainBulletinPermissionVO;
import com.org.dms.vo.service.MainBulletinRangeVO;
import com.org.dms.vo.service.MainBulletinTypeVO;
import com.org.dms.vo.service.MainBulletinVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.SequenceUtil;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;
/**
 * @Description:通知通告管理DAO
 * @version 1.0
 * @remark 
 */
public class NoticeManageDao extends BaseDAO
{
    //定义instance
    public  static final NoticeManageDao getInstance(ActionContext atx)
    {
    	NoticeManageDao dao = new NoticeManageDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
    /**
     * 查询通知通告类别
     */
     public BaseResultSet noticeManageSearch(PageManager page, User user, String conditions) throws Exception
          {
	          	String wheres = conditions;
	          	wheres += " AND T.STATUS = "+DicConstant.YXBS_01+""
	          			+ " AND TU.USER_ID(+) = MP.USER_ID \n"
	          			+ " AND T.TYPE_ID = MP.TYPE_ID(+)\n"
	          			+ " GROUP BY T.TYPE_ID, T.TYPE_NAME, T.TYPE_STATUS\n"
	          			+ " ORDER BY T.TYPE_ID";
	              page.setFilter(wheres);
	          	//定义返回结果集
	          	BaseResultSet bs = null;
	          	StringBuffer sql= new StringBuffer();
	          	sql.append("SELECT T.TYPE_ID,\n" );
	          	sql.append("       T.TYPE_NAME,\n" );
	          	sql.append("       T.TYPE_STATUS,\n" );
	          	sql.append("       REPLACE(WMSYS.WM_CONCAT(TO_CHAR(TU.PERSON_NAME)), ',', ',') ALL_NAME,\n" );
	          	sql.append("       REPLACE(WMSYS.WM_CONCAT(TU.USER_ID), ',', ',') ALL_ID\n" );
	          	sql.append("  FROM MAIN_BULLETIN_TYPE T,MAIN_BULLETIN_PERMISSION MP,TM_USER TU");
	          	//执行方法，不需要传递conn参数
	          	bs = factory.select(sql.toString(), page);
	          	bs.setFieldDic("TYPE_STATUS","YXBS");
	          	return bs;
     }
     public BaseResultSet noticeRangeSearch(PageManager page, User user, String conditions,String bulletinId) throws Exception
     {
     	String wheres = conditions;
     	wheres += " AND TG.ORG_ID = MR.ORG_ID"
     			+ " AND MR.BULLETIN_ID = "+bulletinId+" "
     		    + " ORDER BY MR.RANGE_ID ";
         page.setFilter(wheres);
     	//定义返回结果集
     	BaseResultSet bs = null;
     	StringBuffer sql= new StringBuffer();
     	sql.append("SELECT MR.RANGE_ID,\n" );
     	sql.append("       MR.BULLETIN_ID,\n" );
     	sql.append("       MR.ORG_TYPE,\n" );
     	sql.append("       MR.ORG_ID,\n" );
     	sql.append("       MR.STATUS,\n" );
     	sql.append("       MR.SIGN_USER_ID,\n" );
     	sql.append("       MR.SIGN_DATE,\n" );
     	sql.append("       TG.ONAME,\n" );
     	sql.append("       TG.CODE\n" );
     	sql.append("  FROM MAIN_BULLETIN_RANGE MR,TM_ORG TG");
     	//执行方法，不需要传递conn参数
     	bs = factory.select(sql.toString(), page);
     	return bs;
     }
     /**
    	 * @title: searchModel
    	 * 查询所有渠道商
    	 * @date 2014年7月3日09:14:52
    	 */
        public BaseResultSet searchOrgDealrs(PageManager page, User user, String conditions,String bulletinId,String bulletin_range,String bscCode) throws Exception
        {
        	String wheres = conditions;
        		wheres += "AND TOG.ORG_TYPE in ("+bulletin_range+")\n";
				if(null==bscCode||bscCode.equals("")){
	        	}else{
	        		if(bulletin_range.equals(DicConstant.ZZLB_11)){
	        			wheres += "AND TOG.PID = T.ORG_ID\n"
	        				   +  "AND T.CODE = '"+bscCode+"' ";
	        		}
	        	}
				wheres += "AND NOT EXISTS (SELECT B.ORG_ID FROM MAIN_BULLETIN_RANGE B  WHERE TOG.ORG_ID = B.ORG_ID  AND B.BULLETIN_ID = "+bulletinId+") "
            	        +" ORDER BY TOG.CODE ";
        	page.setFilter(wheres);
        	//定义返回结果集
        	BaseResultSet bs = null;
        	StringBuffer sql= new StringBuffer();
        	sql.append("SELECT TOG.CODE,\n" );
        	sql.append("       TOG.ONAME,\n" );
        	sql.append("       TOG.DIVISION_CODE,\n" );
        	sql.append("       TOG.NAME_EN,\n" );
        	sql.append("       TOG.PID,\n" );
        	sql.append("       TOG.SNAME,\n" );
        	sql.append("       TOG.LEVEL_CODE,\n" );
        	sql.append("       TOG.COMPANY_ID,\n" );
        	sql.append("       TOG.ORG_ID,\n" );
        	sql.append("       TOG.CREATE_USER,\n" );
        	sql.append("       TOG.CREATE_TIME,\n" );
        	sql.append("       TOG.UPDATE_USER,\n" );
        	sql.append("       TOG.UPDATE_TIME,\n" );
        	sql.append("       TOG.STATUS,\n" );
        	sql.append("       TOG.ORG_TYPE,\n" );
        	sql.append("       TOG.BUS_TYPE,\n" );
        	sql.append("       TOG.OEM_COMPANY_ID\n" );
        	sql.append("  FROM TM_ORG TOG\n" );
        	if(null==bscCode||bscCode.equals("")){
        	}else{
        		if(bulletin_range.equals(DicConstant.ZZLB_11)){
        			sql.append("  ,TM_ORG T\n" );
        		}
        	}
        	//执行方法，不需要传递conn参数
        	bs = factory.select(sql.toString(), page);
        	bs.setFieldDic("ORG_TYPE", DicConstant.ZZLB);
        	return bs;
        	
        }
      //新增发布范围。
    	public boolean insertOrgs1(String CreateUser,String bulletin_range,String bulletinId)throws Exception{
    		StringBuffer sql= new StringBuffer();
    		sql.append("INSERT INTO MAIN_BULLETIN_RANGE T (\n" );
    		sql.append("       RANGE_ID,\n" );
    		sql.append("       BULLETIN_ID,\n" );
    		sql.append("       ORG_TYPE,\n" );
    		sql.append("       ORG_ID,\n" );
    		sql.append("       STATUS,\n" );
    		sql.append("       CREATE_BY,\n" );
    		sql.append("       CREATE_DATE)\n" );
    		sql.append("SELECT F_GETID(),\n" );
    		sql.append("       "+bulletinId+",\n" );
    		sql.append("       G.ORG_TYPE,\n" );
    		sql.append("       G.ORG_ID,\n" );
    		sql.append("       "+DicConstant.TGQSZT_01+",\n" );
    		sql.append("       '"+CreateUser+"',\n" );
    		sql.append("       SYSDATE\n" );
    		sql.append("FROM TM_ORG G\n" );
    		sql.append("WHERE G.ORG_TYPE IN ("+bulletin_range+")AND NOT EXISTS (SELECT B.ORG_ID\n" );
    		sql.append("          FROM MAIN_BULLETIN_RANGE B\n" );
    		sql.append("         WHERE G.ORG_ID = B.ORG_ID\n" );
    		sql.append("           AND B.BULLETIN_ID = "+bulletinId+")\n" );
    		sql.append(" ORDER BY G.ORG_ID");

    		return factory.update(sql.toString(),null);
    	    }
    	public boolean insertOrgs(String CreateUser,String mxids,String bulletinId)throws Exception{
    		StringBuffer sql= new StringBuffer();
    		sql.append("INSERT INTO MAIN_BULLETIN_RANGE T (\n" );
    		sql.append("       RANGE_ID,\n" );
    		sql.append("       BULLETIN_ID,\n" );
    		sql.append("       ORG_TYPE,\n" );
    		sql.append("       ORG_ID,\n" );
    		sql.append("       STATUS,\n" );
    		sql.append("       CREATE_BY,\n" );
    		sql.append("       CREATE_DATE)\n" );
    		sql.append("SELECT F_GETID(),\n" );
    		sql.append("       "+bulletinId+",\n" );
    		sql.append("       G.ORG_TYPE,\n" );
    		sql.append("       G.ORG_ID,\n" );
    		sql.append("       "+DicConstant.TGQSZT_01+",\n" );
    		sql.append("       '"+CreateUser+"',\n" );
    		sql.append("       SYSDATE\n" );
    		sql.append("FROM TM_ORG G\n" );
    		sql.append("WHERE G.ORG_ID IN("+mxids+")");
    		return factory.update(sql.toString(),null);
    	    }
     /**
      * 查询通知通告内容
      */
     public BaseResultSet noticeContentSearch(PageManager page, User user, String conditions,String userId) throws Exception
     {
    	 String wheres = conditions;
		 wheres += "AND MB.STATUS = "+DicConstant.YXBS_01+"\n"
		 		+ " AND MB.BULLETIN_STATUS IN( "+DicConstant.TGFBZT_01+","+DicConstant.TGFBZT_02+")"
		 		+ " AND MP.USER_ID = "+userId+"\n"
		 		+ " AND MB.TYPE_ID = MP.TYPE_ID\n"
		 		+ " AND MT.TYPE_ID = MB.TYPE_ID\n"
				+ " ORDER BY MB.BULLETIN_ID DESC";
    	 page.setFilter(wheres);
    	 //定义返回结果集
    	 BaseResultSet bs = null;
    	 StringBuffer sql= new StringBuffer();
    	 sql.append("SELECT MB.BULLETIN_ID,\n" );
    	 sql.append("       MT.TYPE_NAME,\n" );
    	 sql.append("       MT.TYPE_ID,\n" );
    	 sql.append("       MB.TITLE,\n" );
    	 sql.append("       MB.CONTENT,\n" );
    	 sql.append("       MB.BULLETIN_RANGE,\n" );
    	 sql.append("       MB.BULLETIN_STATUS,\n" );
    	 sql.append("       MB.ORG_ID,\n" );
    	 sql.append("       MB.USER_ID,\n" );
    	 sql.append("       MB.COMPANY_ID,\n" );
    	 sql.append("       MB.CREATE_USER,\n" );
    	 sql.append("       MB.CREATE_TIME,\n" );
    	 sql.append("       MB.UPDATE_USER,\n" );
    	 sql.append("       MB.UPDATE_TIME,\n" );
    	 sql.append("       MB.STATUS,\n" );
    	 sql.append("       MB.OEM_COMPANY_ID,\n" );
    	 sql.append("       MB.SECRET_LEVEL\n" );
    	 sql.append("  FROM MAIN_BULLETIN_PERMISSION MP,\n" );
    	 sql.append("       MAIN_BULLETIN MB,\n");
    	 sql.append("       MAIN_BULLETIN_TYPE MT");
    	 //执行方法，不需要传递conn参数
    	 bs = factory.select(sql.toString(), page);
    	 bs.setFieldDic("BULLETIN_STATUS", "TGFBZT");
    	 bs.setFieldDic("BULLETIN_RANGE", "ZZLB");
    	 return bs;
     }
     /**
      * 查询通知通告归档
      */
     public BaseResultSet noticeArchiveSearch(PageManager page, User user, String conditions,String userId) throws Exception
     {
    	 String wheres = conditions;
    	 wheres += "AND MB.STATUS = "+DicConstant.YXBS_01+"\n"
    			 + " AND MB.BULLETIN_STATUS IN ("+DicConstant.TGFBZT_02+","+DicConstant.TGFBZT_03+")"
    			 + " AND MR.STATUS = "+DicConstant.TGQSZT_02+""
    			// + " AND MP.USER_ID = "+userId+"\n"
    			 + " AND MB.TYPE_ID = MP.TYPE_ID\n"
    			 + " AND MT.TYPE_ID = MB.TYPE_ID\n"
    			 + " AND MR.BULLETIN_ID = MB.BULLETIN_ID\n"
    			 + " ORDER BY MB.BULLETIN_ID,MB.SEND_DATE DESC";
    	 page.setFilter(wheres);
    	 //定义返回结果集
    	 BaseResultSet bs = null;
    	 StringBuffer sql= new StringBuffer();
    	 sql.append("SELECT DISTINCT MB.BULLETIN_ID,\n" );
    	 sql.append("       MT.TYPE_NAME,\n" );
    	 sql.append("       MT.TYPE_ID,\n" );
    	 sql.append("       MB.TITLE,\n" );
    	 sql.append("       MB.CONTENT,\n" );
    	 sql.append("       MB.BULLETIN_STATUS,\n" );
    	 sql.append("       MB.USER_ID,\n" );
    	 sql.append("       MB.COMPANY_ID,\n" );
    	 sql.append("       MB.CREATE_USER,\n" );
    	 sql.append("       MB.CREATE_TIME,\n" );
    	 sql.append("       MB.UPDATE_USER,\n" );
    	 sql.append("       MB.UPDATE_TIME,\n" );
    	 sql.append("       MB.STATUS,\n" );
    	 sql.append("       MB.OEM_COMPANY_ID,\n" );
    	 sql.append("       MB.SECRET_LEVEL,");
    	 sql.append("       MB.SEND_DATE");
    	 sql.append("  FROM MAIN_BULLETIN_PERMISSION MP,\n" );
    	 sql.append("       MAIN_BULLETIN MB,\n");
    	 sql.append("       MAIN_BULLETIN_TYPE MT,");
    	 sql.append("       MAIN_BULLETIN_RANGE MR");
    	 //执行方法，不需要传递conn参数
    	 bs = factory.select(sql.toString(), page);
    	 bs.setFieldDic("BULLETIN_STATUS", "TGFBZT");
    	 bs.setFieldDic("BULLETIN_RANGE", "ZZLB");
    	 bs.setFieldDateFormat("SEND_DATE","yyyy-MM-dd HH:MM");
    	 return bs;
     }
     /**
      * 查询通知通告签收情况
      */
     public BaseResultSet noticeSignedSearch(PageManager page, User user, String conditions,String userId) throws Exception
     {
    	 String wheres = conditions;
    	 wheres += "AND MB.STATUS = "+DicConstant.YXBS_01+"\n"
    			 + " AND MB.BULLETIN_STATUS = "+DicConstant.TGFBZT_02+""
    			 + " AND TG.ORG_ID = MR.ORG_ID\n"
    			 + " AND MB.TYPE_ID = MP.TYPE_ID\n"
    			 + " AND MT.TYPE_ID = MB.TYPE_ID\n"
    			 + " AND MR.BULLETIN_ID = MB.BULLETIN_ID\n"
    			 + " ORDER BY MB.BULLETIN_ID,MR.STATUS DESC";
    	 page.setFilter(wheres);
    	 //定义返回结果集
    	 BaseResultSet bs = null;
    	 StringBuffer sql= new StringBuffer();
    	 sql.append("SELECT MB.BULLETIN_ID,\n" );
    	 sql.append("       MT.TYPE_NAME,\n" );
    	 sql.append("       MT.TYPE_ID,\n" );
    	 sql.append("       MB.TITLE,\n" );
    	 sql.append("       MB.CONTENT,\n" );
    	 sql.append("       MB.BULLETIN_RANGE,\n" );
    	 sql.append("       MB.BULLETIN_STATUS,\n" );
    	 sql.append("       MR.ORG_ID ORG_CODE,\n" );
    	 sql.append("       MR.ORG_ID ORG_NAME,\n" );
    	 sql.append("       MB.USER_ID,\n" );
    	 sql.append("       MB.COMPANY_ID,\n" );
    	 sql.append("       MB.CREATE_USER,\n" );
    	 sql.append("       MB.CREATE_TIME,\n" );
    	 sql.append("       MB.UPDATE_USER,\n" );
    	 sql.append("       MB.UPDATE_TIME,\n" );
    	 sql.append("       MB.STATUS,\n" );
    	 sql.append("       MB.OEM_COMPANY_ID,\n" );
    	 sql.append("       MR.STATUS SIGN_STATUS,\n" );
    	 sql.append("       MR.SIGN_DATE,\n" );
    	 sql.append("       (SELECT T.PERSON_NAME FROM TM_USER T WHERE T.USER_ID = MR.SIGN_USER_ID)PERSON_NAME,\n" );
    	 sql.append("       MB.SECRET_LEVEL\n" );
    	 sql.append("  FROM MAIN_BULLETIN_PERMISSION MP,\n" );
    	 sql.append("       MAIN_BULLETIN MB,\n");
    	 sql.append("       MAIN_BULLETIN_TYPE MT,");
    	 sql.append("       MAIN_BULLETIN_RANGE MR,");
    	 sql.append("       TM_ORG      		TG");
    	 //执行方法，不需要传递conn参数
    	 bs = factory.select(sql.toString(), page);
    	 bs.setFieldDic("BULLETIN_STATUS", "TGFBZT");
    	 bs.setFieldDic("BULLETIN_RANGE", "ZZLB");
    	 bs.setFieldDic("SIGN_STATUS", "TGQSZT");
    	 bs.setFieldDateFormat("SIGN_DATE","yyyy-MM-dd HH:MM");
    	 bs.setFieldOrgDeptSimpleName("ORG_NAME");//简称
    	 bs.setFieldOrgDeptCode("ORG_CODE");
    	 return bs;
     }
     /**
      * 查询通知通告
      */
     public BaseResultSet noticeSearch(PageManager page, User user, String conditions,String userId) throws Exception
     {
    	 String wheres = conditions;
    	 wheres += "AND MB.STATUS = "+DicConstant.YXBS_01+"\n"
    		  // + " AND MB.BULLETIN_STATUS = "+DicConstant.TGFBZT_02+""
    			 //+ " AND MP.USER_ID = "+userId+"\n"
    			 + " AND MB.TYPE_ID = MP.TYPE_ID\n"
    			 + " AND MT.TYPE_ID = MB.TYPE_ID\n"
    			 + " AND MR.BULLETIN_ID = MB.BULLETIN_ID\n"
    			 + " ORDER BY MB.BULLETIN_ID DESC";
    	 page.setFilter(wheres);
    	 //定义返回结果集
    	 BaseResultSet bs = null;
    	 StringBuffer sql= new StringBuffer();
    	 sql.append("SELECT MB.BULLETIN_ID,\n" );
    	 sql.append("       MT.TYPE_NAME,\n" );
    	 sql.append("       MT.TYPE_ID,\n" );
    	 sql.append("       MB.TITLE,\n" );
    	 sql.append("       MB.CONTENT,\n" );
    	 sql.append("       MB.BULLETIN_RANGE,\n" );
    	 sql.append("       MB.BULLETIN_STATUS,\n" );
    	 sql.append("       MR.ORG_ID ORG_CODE,\n" );
    	 sql.append("       MR.ORG_ID ORG_NAME,\n" );
    	 sql.append("       MB.USER_ID,\n" );
    	 sql.append("       MB.COMPANY_ID,\n" );
    	 sql.append("       MB.CREATE_USER,\n" );
    	 sql.append("       MB.CREATE_TIME,\n" );
    	 sql.append("       MB.UPDATE_USER,\n" );
    	 sql.append("       MB.UPDATE_TIME,\n" );
    	 sql.append("       MB.STATUS,\n" );
    	 sql.append("       MB.OEM_COMPANY_ID,\n" );
    	 sql.append("       MR.STATUS SIGN_STATUS,\n" );
    	 sql.append("       MB.SECRET_LEVEL\n" );
    	 sql.append("  FROM MAIN_BULLETIN_PERMISSION MP,\n" );
    	 sql.append("       MAIN_BULLETIN MB,\n");
    	 sql.append("       MAIN_BULLETIN_TYPE MT,");
    	 sql.append("       MAIN_BULLETIN_RANGE MR");
    	 //执行方法，不需要传递conn参数
    	 bs = factory.select(sql.toString(), page);
    	 bs.setFieldDic("BULLETIN_STATUS", "TGFBZT");
    	 bs.setFieldDic("BULLETIN_RANGE", "ZZLB");
    	 bs.setFieldDic("SIGN_STATUS", "TGQSZT");
    	 bs.setFieldOrgDeptSimpleName("ORG_NAME");//简称
    	 bs.setFieldOrgDeptCode("ORG_CODE");
    	 return bs;
     }
     /**
      * 查询通知通告内容
      */
     public BaseResultSet noticeSignSearch(PageManager page, User user, String conditions,String userId) throws Exception
     {
    	 String wheres = conditions;
    	 wheres += " AND MT.TYPE_ID = MB.TYPE_ID\n"
    			 + " AND MR.BULLETIN_ID = MB.BULLETIN_ID\n"
    			 +  "AND MB.STATUS = "+DicConstant.YXBS_01+"\n"
    			 + " AND MB.BULLETIN_STATUS IN( "+DicConstant.TGFBZT_02+","+DicConstant.TGFBZT_03+")\n"
    			 //+ " AND MP.USER_ID = "+userId+"\n"
    			 + " AND MR.ORG_ID ="+user.getOrgId()+"\n"
    			 + " AND MR.STATUS ="+DicConstant.TGQSZT_01+"\n"
    			 + " ORDER BY MB.BULLETIN_ID DESC";
    	 page.setFilter(wheres);
    	 //定义返回结果集
    	 BaseResultSet bs = null;
    	 StringBuffer sql= new StringBuffer();
    	 sql.append("SELECT MB.BULLETIN_ID,\n" );
    	 sql.append("       MT.TYPE_NAME,\n" );
    	 sql.append("       MT.TYPE_ID,\n" );
    	 sql.append("       MB.TITLE,\n" );
    	 sql.append("       MB.CONTENT,\n" );
    	 sql.append("       MB.BULLETIN_RANGE,\n" );
    	 sql.append("       MB.BULLETIN_STATUS,\n" );
    	 sql.append("       MB.ORG_ID,\n" );
    	 sql.append("       MB.USER_ID,\n" );
    	 sql.append("       MB.COMPANY_ID,\n" );
    	 sql.append("       MB.CREATE_USER,\n" );
    	 sql.append("       MB.CREATE_TIME,\n" );
    	 sql.append("       MB.UPDATE_USER,\n" );
    	 sql.append("       MB.UPDATE_TIME,\n" );
    	 sql.append("       MB.STATUS,\n" );
    	 sql.append("       MB.OEM_COMPANY_ID,\n" );
    	 sql.append("       MB.SECRET_LEVEL,\n" );
    	 sql.append("       MR.RANGE_ID\n" );
    	 sql.append("  FROM MAIN_BULLETIN MB,\n");
    	 sql.append("       MAIN_BULLETIN_TYPE MT,");
    	 sql.append("       MAIN_BULLETIN_RANGE MR");
    	 //执行方法，不需要传递conn参数
    	 bs = factory.select(sql.toString(), page);
    	 bs.setFieldDic("BULLETIN_STATUS", "TGFBZT");
    	 bs.setFieldDic("BULLETIN_RANGE", "ZZLB");
    	 return bs;
     }
     /**
      * 查询通知通告内容
      */
     public BaseResultSet noticeSignSearch1(PageManager page, User user, String conditions,String userId) throws Exception
     {
    	 String wheres = conditions;
    	 wheres += " AND MT.TYPE_ID = MB.TYPE_ID\n"
    			 + " AND MR.BULLETIN_ID = MB.BULLETIN_ID\n"
    			 +  "AND MB.STATUS = "+DicConstant.YXBS_01+"\n"
    			 + " AND MB.BULLETIN_STATUS IN( "+DicConstant.TGFBZT_02+","+DicConstant.TGFBZT_03+")\n"
    			 //+ " AND MP.USER_ID = "+userId+"\n"
    			 + " AND MR.ORG_ID ="+user.getOrgId()+"\n"
    			 + " ORDER BY MB.BULLETIN_ID DESC";
    	 page.setFilter(wheres);
    	 //定义返回结果集
    	 BaseResultSet bs = null;
    	 StringBuffer sql= new StringBuffer();
    	 sql.append("SELECT MB.BULLETIN_ID,\n" );
    	 sql.append("       MT.TYPE_NAME,\n" );
    	 sql.append("       MT.TYPE_ID,\n" );
    	 sql.append("       MB.TITLE,\n" );
    	 sql.append("       MB.CONTENT,\n" );
    	 sql.append("       MB.BULLETIN_RANGE,\n" );
    	 sql.append("       MB.BULLETIN_STATUS,\n" );
    	 sql.append("       MB.ORG_ID,\n" );
    	 sql.append("       MB.USER_ID,\n" );
    	 sql.append("       MB.COMPANY_ID,\n" );
    	 sql.append("       MB.CREATE_USER,\n" );
    	 sql.append("       MB.CREATE_TIME,\n" );
    	 sql.append("       MB.UPDATE_USER,\n" );
    	 sql.append("       MB.UPDATE_TIME,\n" );
    	 sql.append("       MB.STATUS,\n" );
    	 sql.append("       MB.OEM_COMPANY_ID,\n" );
    	 sql.append("       MB.SECRET_LEVEL,\n" );
    	 sql.append("       MR.RANGE_ID,\n" );
    	 sql.append("       MR.STATUS QSSTATUS\n" );
    	 sql.append("  FROM MAIN_BULLETIN MB,\n");
    	 sql.append("       MAIN_BULLETIN_TYPE MT,");
    	 sql.append("       MAIN_BULLETIN_RANGE MR");
    	 //执行方法，不需要传递conn参数
    	 bs = factory.select(sql.toString(), page);
    	 bs.setFieldDic("BULLETIN_STATUS", "TGFBZT");
    	 bs.setFieldDic("BULLETIN_RANGE", "ZZLB");
    	 return bs;
     }
     /**
    	 * @title: searchUsers
    	 * 查询未添加用户
    	 * @date 二〇一四年八月九日 16:14:29
    	 */
        public BaseResultSet searchUsers(PageManager page, User user, String conditions,String typeId) throws Exception
        {
        	String wheres = conditions;
        	wheres +=" AND T.ORG_ID = TG.ORG_ID\n"
        		   + " AND T.STATUS = "+DicConstant.YXBS_01+""
        		   + " AND NOT EXISTS (SELECT B.USER_ID FROM MAIN_BULLETIN_PERMISSION B WHERE T.USER_ID = B.USER_ID AND B.TYPE_ID ="+typeId+")"
        	       + " ORDER BY T.USER_ID ";
        	page.setFilter(wheres);
        	//定义返回结果集
        	BaseResultSet bs = null;
        	StringBuffer sql= new StringBuffer();
        	sql.append("SELECT T.PERSON_NAME,\n" );
        	sql.append("       T.USER_SN,\n" );
        	sql.append("       T.ACCOUNT,\n" );
        	sql.append("       T.USER_ID,\n" );
        	sql.append("       TG.ONAME\n" );
        	sql.append("  FROM TM_USER T,TM_ORG TG");
        	//执行方法，不需要传递conn参数
        	bs = factory.select(sql.toString(), page);
        	return bs;
        }
        /**
         * @title: searchUsers
         * 查询已添加用户
         * @date 二〇一四年八月九日 16:14:29
         */
        public BaseResultSet searchNoticeUser(PageManager page, User user, String conditions,String typeId) throws Exception
        {
        	String wheres = conditions;
        	wheres += " AND TU.USER_ID = MP.USER_ID\n"
        			+ " AND TG.ORG_ID = TU.ORG_ID\n"
        			+ " AND MP.TYPE_ID ="+typeId+""
        			+ " ORDER BY MP.LIMIT_ID ";
        	page.setFilter(wheres);
        	//定义返回结果集
        	BaseResultSet bs = null;
        	StringBuffer sql= new StringBuffer();
        	sql.append("SELECT MP.LIMIT_ID,\n" );
        	sql.append("       MP.TYPE_ID,\n" );
        	sql.append("       TU.USER_SN,\n" );
        	sql.append("       TU.PERSON_NAME,\n" );
        	sql.append("       TG.ONAME\n" );
        	sql.append("  FROM MAIN_BULLETIN_PERMISSION MP,\n" );
        	sql.append("       TM_USER TU,\n" );
        	sql.append("       TM_ORG TG");
        	//执行方法，不需要传递conn参数
        	bs = factory.select(sql.toString(), page);
        	return bs;
        }
     public boolean insert(MainBulletinTypeVO vo)throws Exception {
    	String typeId=SequenceUtil.getCommonSerivalNumber(factory);
 		vo.setTypeId(typeId);
 		return factory.insert(vo);
 	 }
     public boolean insert(MainBulletinVO vo)throws Exception {
    	 return factory.insert(vo);
     }
     public boolean insertRange(MainBulletinRangeVO vo)throws Exception {
    	 return factory.insert(vo);
     }
     public boolean insertUser(MainBulletinPermissionVO vo)throws Exception {
  		return factory.insert(vo);
  	 }
 	public boolean updateContent(MainBulletinVO vo) throws Exception{
     	return factory.update(vo);
    }
 	public boolean updateBulletinStatus(MainBulletinVO vo) throws Exception{
 		return factory.update(vo);
 	}
 	public boolean update(MainBulletinTypeVO vo) throws Exception{
     	return factory.update(vo);
    }
    /**
     * 附件查询
     * @param page 分页
     * @param user 用户
     * @return
     * @throws SQLException 
     */
    public BaseResultSet fileSearch(PageManager page,User user,String bulletinId) throws SQLException{
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.* FROM FS_FILESTORE T WHERE T.YWZJ = "+bulletinId+"");
    	bs=factory.select(sql.toString(), page);
    	bs.setFieldDateFormat("CJSJ", "yyyy-MM-dd HH:mm:ss");
    	return bs;
    }
 	//删除通告内容维护
 	public boolean deleteBulletin(String bulletinId) throws Exception{
 		StringBuffer sql= new StringBuffer();
 		sql.append("DELETE FROM MAIN_BULLETIN T WHERE T.BULLETIN_ID ="+bulletinId+"");
		return factory.delete(sql.toString(), null);

 	}
    /**
     * @title: deleteDealer
     * @return
     * @throws Exception    设定文件 
     * @return boolean    返回类型 
     */
    public boolean deleteDealer(String mxids) throws Exception
    {
 	   StringBuffer sql = new StringBuffer();
 	   sql.append(" DELETE MAIN_BULLETIN_RANGE A WHERE A.RANGE_ID IN ("+mxids+")\n");
 	   return factory.delete(sql.toString(), null);
    }
    /**
     * @title: deleteNoticeUserByMxids
     * @return
     * @throws Exception    设定文件 
     * @return boolean    返回类型 
     */
    public boolean deleteNoticeUserByMxids(String mxids) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" DELETE MAIN_BULLETIN_PERMISSION A WHERE A.LIMIT_ID IN ("+mxids+") \n");
    	return factory.delete(sql.toString(), null);
    }
    public boolean preAuthAttaDelete(String fjid) throws Exception
    {
    	StringBuffer sql= new StringBuffer();
    	sql.append("DELETE FROM FS_FILESTORE T\n" );
    	sql.append("WHERE T.FJID ="+fjid+"");
    	return factory.delete(sql.toString(), null);
    }
    public boolean updateStatus(MainBulletinRangeVO vo)throws Exception
    {
    	return factory.update(vo);
    }
    /**
     * 查询符合条件的所有ORG
     * @return
     * @throws Exception
     */
    public QuerySet queryAll(String bulletinId,String bulletin_range)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT TOG.CODE,\n" );
    	sql.append("       TOG.ONAME,\n" );
    	sql.append("       TOG.DIVISION_CODE,\n" );
    	sql.append("       TOG.NAME_EN,\n" );
    	sql.append("       TOG.PID,\n" );
    	sql.append("       TOG.SNAME,\n" );
    	sql.append("       TOG.LEVEL_CODE,\n" );
    	sql.append("       TOG.COMPANY_ID,\n" );
    	sql.append("       TOG.ORG_ID,\n" );
    	sql.append("       TOG.CREATE_USER,\n" );
    	sql.append("       TOG.CREATE_TIME,\n" );
    	sql.append("       TOG.UPDATE_USER,\n" );
    	sql.append("       TOG.UPDATE_TIME,\n" );
    	sql.append("       TOG.STATUS,\n" );
    	sql.append("       TOG.ORG_TYPE,\n" );
    	sql.append("       TOG.BUS_TYPE,\n" );
    	sql.append("       TOG.OEM_COMPANY_ID\n" );
    	sql.append("  FROM TM_ORG TOG\n" );
    	sql.append(" WHERE 1 = 1\n" );
    	sql.append("   AND TOG.ORG_TYPE in( "+bulletin_range+")\n" );
    	sql.append("   AND NOT EXISTS (SELECT B.ORG_ID\n" );
    	sql.append("          FROM MAIN_BULLETIN_RANGE B\n" );
    	sql.append("         WHERE TOG.ORG_ID = B.ORG_ID\n" );
    	sql.append("           AND B.BULLETIN_ID = "+bulletinId+")\n" );
    	sql.append(" ORDER BY TOG.ORG_ID");
 	   return factory.select(null, sql.toString());
    }
    /**
     * 查询符合条件的所有ORG
     * @return
     * @throws Exception
     */
    public QuerySet getRange(String bulletinId)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT R.ORG_ID, T.TYPE_NAME,M.BULLETIN_ID\n" );
    	sql.append("  FROM MAIN_BULLETIN_RANGE R, MAIN_BULLETIN M, MAIN_BULLETIN_TYPE T\n" );
    	sql.append(" WHERE R.BULLETIN_ID = '"+bulletinId+"'\n" );
    	sql.append("   AND M.BULLETIN_ID = R.BULLETIN_ID\n" );
    	sql.append("   AND T.TYPE_ID = M.TYPE_ID");
    	return factory.select(null, sql.toString());
    }
    public QuerySet queryUserId(String oId)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT U.USER_ID FROM TM_USER U WHERE U.ORG_ID = '"+oId+"'\n" );
    	return factory.select(null, sql.toString());
    }
}