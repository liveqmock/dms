package com.org.dms.dao.service.claimmng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.service.SeBaCheckUserVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class CheckUserDao extends BaseDAO
{
    //定义instance
    public  static final CheckUserDao getInstance(ActionContext atx)
    {
    	CheckUserDao dao = new CheckUserDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
    
    /**
     * 审核员查询
     * @param page
     * @param user
     * @param conditions
     * @return
     * @throws Exception
     */
    public BaseResultSet checkUserSearch(PageManager page,User user,String conditions)throws Exception{
    	String wheres = conditions;
    		   wheres += " AND C.USER_TYPE ="+DicConstant.CLYHLX_01+" \n";
    	page.setFilter(wheres);
    	BaseResultSet bs=null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT C.CU_ID, C.USER_NAME, C.IF_DISTRIB FROM SE_BA_CHECK_USER C  ");
    	bs =factory.select(sql.toString(), page);
    	bs.setFieldDic("IF_DISTRIB","SF");
    	return bs;
    }
    
    /**
     * 新增审核员查询
     * @param page
     * @param user
     * @param conditions
     * @return
     * @throws Exception
     */
    public BaseResultSet searchUser(PageManager page,User user,String conditions)throws Exception{
    	BaseResultSet bs=null;
    	String wheres = conditions;
    		   wheres +=" AND T.PERSON_TYPE IN ("+DicConstant.JGJB_01+","+DicConstant.JGJB_02+","+DicConstant.JGJB_03+","+DicConstant.JGJB_08+")\n"+
    				    " AND T.STATUS ="+DicConstant.YXBS_01+"\n"+
    				    " AND NOT EXISTS\n"+
    				    " (SELECT 1 FROM SE_BA_CHECK_USER U WHERE T.ACCOUNT = U.USER_ACCOUNT)";
    	page.setFilter(wheres);
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.ACCOUNT, T.PERSON_NAME, T.ORG_ID ORG_CODE \n" );
    	sql.append("  FROM TM_USER T\n" );
    	bs=factory.select(sql.toString(), page);
    	bs.setFieldOrgDeptSimpleName("ORG_CODE");
    	return bs;
    }
    
    /**
     * 查询当前最大序号
     * @return
     * @throws Exception
     */
   public QuerySet maxSeqNo()throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT NVL(MAX(TO_NUMBER(SEQ_NO)), 0) SEQ_NO FROM SE_BA_CHECK_USER WHERE USER_TYPE = "+DicConstant.CLYHLX_01+"");
	   return factory.select(null, sql.toString());
   }
   
   /**
    * 新增审核员信息
    * @param vo
    * @return
    * @throws Exception
    */
   public boolean insertUser(SeBaCheckUserVO vo)throws Exception{
	   return factory.insert(vo);
   }
   
   /**
    * 删除审核人
    * @param cuId 
    * @return
    * @throws Exception
    */
   public boolean userDelete(String cuId)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("DELETE FROM SE_BA_CHECK_USER WHERE CU_ID = "+cuId+"");
	   return factory.delete(sql.toString(), null);
   }
   
   /**
    * 修改审核人
    * @param vo
    * @return
    * @throws Exception
    */
   public boolean updateCheckUser(SeBaCheckUserVO vo)throws Exception{
	   return factory.update(vo);
   }
   
   /**
    * 审核数量
    * @param page
    * @param user
    * @param conditions
    * @return
    * @throws Exception
    */
   public BaseResultSet checkCountSearch(PageManager page,User user,String conditions)throws Exception{
	   String wheres = conditions;
	   wheres +=" AND C.OPERATE_USER = U.USER_ACCOUNT\n" +
				" AND U.USER_TYPE = "+DicConstant.CLYHLX_01+"\n" + 
				" AND NVL(C.IF_PERSON_CHECK, 0) = "+DicConstant.SF_01+" \n"+
				" AND C.CLAIM_STATUS = "+DicConstant.SPDZT_05+"\n" + 
				" GROUP BY  U.USER_ACCOUNT, U.USER_NAME \n"+
				"  ORDER BY  U.USER_ACCOUNT";
	   page.setFilter(wheres);
	   BaseResultSet bs=null;
	   StringBuffer sql= new StringBuffer();
	   sql.append(" SELECT  U.USER_ACCOUNT, U.USER_NAME, COUNT(1) CLAIM_COUNT\n" );
	   sql.append("  FROM SE_BU_CLAIM C, SE_BA_CHECK_USER U \n");
	   bs =factory.select(sql.toString(), page);
	   return bs;
   }
   
}
