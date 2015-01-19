package com.org.dms.dao.service.basicinfomng;

import com.org.dms.vo.service.MainDealerStarLogVO;
import com.org.dms.vo.service.MainDealerVO;
import com.org.framework.base.*;
import com.org.framework.common.*;
import com.org.mvc.context.ActionContext;

/**
 * @Title: szqdms
 * @description: 渠道商星级评定管理类
 * @throws Exception 设定文件
 * @auther fanpeng
 * @date 2014年8月26日 
 */
public class DealerStarMngDao extends BaseDAO
{
    //定义instance
    public  static final DealerStarMngDao getInstance(ActionContext atx)
    {
        DealerStarMngDao dao = new DealerStarMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	
	public boolean updateDealStar(MainDealerVO vo) throws Exception
    {
    	return factory.update(vo);
    }
	
	//星级评定日志表信息新增
	public boolean insertDealStarLog(MainDealerStarLogVO vo) throws Exception
    {
    	return factory.insert(vo);
    }

    public BaseResultSet search(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND T.BELONG_OFFICE=M.ORG_ID" ;
    	wheres += " AND T.DEALER_STAR=N.PARAKEY" ;
    	wheres += " AND T.STATUS = '100201'" ;
    	wheres += " ORDER　BY　T.BELONG_OFFICE,T.DEALER_STAR" ;
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;

    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.DEALER_ID,\n" );
    	sql.append("       T.DEALER_NAME,\n" );
    	sql.append("       T.DEALER_CODE,\n" );
    	sql.append("       T.DEALER_STAR,\n" );
    	sql.append("       T.BELONG_OFFICE,\n" );
    	sql.append("       M.ONAME,\n" );
    	sql.append("       N.PARANAME,\n" );
    	sql.append("       T.STATUS,\n" );
    	sql.append("       T.DEALER_TYPE\n" );
    	sql.append("  FROM MAIN_DEALER T, TM_ORG M, USER_PARA_CONFIGURE N");

    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
    
    
    //星级评定轨迹查询
    public BaseResultSet searchLog(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres += " ORDER　BY　T.DEALER_CODE,T.MODIFY_TIME" ;
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;

    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.LOG_ID,\n" );
    	sql.append("       T.DEALER_CODE,\n" );
    	sql.append("       T.DEALER_NAME,\n" );
    	sql.append("       (SELECT N.PARANAME　FROM USER_PARA_CONFIGURE N WHERE T.OLD_STAR=N.PARAKEY) AS OLDPARANAME,\n" );
    	sql.append("       (SELECT N.PARANAME　FROM USER_PARA_CONFIGURE N WHERE T.NEW_STAR=N.PARAKEY) AS NEWPARANAME,\n" );
    	sql.append("       T.OLD_STAR,\n" );
    	sql.append("       T.NEW_STAR,\n" );
    	sql.append("       T.MODIFY_USER,\n" );
    	sql.append("       T.MODIFY_TIME\n" );
    	sql.append("  FROM MAIN_DEALER_STAR_LOG T");

    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	
    	//设置日期字段显示格式
		bs.setFieldDateFormat("MODIFY_TIME", "yyyy-MM-dd HH:mm:ss");
    	return bs;
    }
    
    /**
     * 查询服务站星级临时表信息（校验临时表数据）
     * @throws Exception
     * @Author fanpeng 2014-10-10
     */
    public QuerySet searchDealerStarTmp(User user)throws Exception{
		
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT \n" );
		sql.append(" TMP_ID,\n" );
		sql.append(" DEALER_CODE,\n" );
		sql.append(" DEALER_STAR,\n" );
		sql.append(" USER_ACCOUNT,\n" );
		sql.append(" ROW_NO\n" );
		sql.append(" FROM MAIN_DEALER_STAR_TMP\n" );
		sql.append(" WHERE USER_ACCOUNT = '"+user.getPersonName()+"'\n" );
		sql.append(" ORDER BY ROW_NO\n" );
		return factory.select(null, sql.toString());
		
	}
    /**
     * 查询服务站星级临时表信息(校验成功后，将正确数据显示到前台，用bs方式显示)
     * @throws Exception
     * @Author fanpeng 2014-10-10
     */
    public BaseResultSet searchSuccess(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND USER_ACCOUNT = '"+user.getPersonName()+"'" ;
    	wheres += "  ORDER BY ROW_NO" ;
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;

    	StringBuffer sql= new StringBuffer();
		sql.append("SELECT \n" );
		sql.append(" TMP_ID,\n" );
		sql.append(" DEALER_CODE,\n" );
		sql.append(" DEALER_STAR,\n" );
		sql.append(" USER_ACCOUNT,\n" );
		sql.append(" ROW_NO\n" );
		sql.append(" FROM MAIN_DEALER_STAR_TMP\n" );
    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
    
    /**
     * 判断服务站代码是否存在（校验临时表数据）
     * @throws Exception
     * @Author fanpeng 2014-10-11
     */
	public QuerySet checkDealerCode(String dealerCode) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT COUNT(1) NUMS \n");
    	sql.append(" FROM MAIN_DEALER \n");
    	sql.append(" WHERE DEALER_CODE = '" + dealerCode +"'");
    	sql.append(" AND STATUS = 100201 ");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	
    /**
     * 判断星级代码是否存在（校验临时表数据）
     * @throws Exception
     * @Author fanpeng 2014-10-11
     */
	public QuerySet checkDealerStar(String dealerStar) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT COUNT(1) NUMS \n");
    	sql.append(" FROM USER_PARA_CONFIGURE \n");
    	sql.append(" WHERE PARAKEY = '" + dealerStar +"'");
    	sql.append(" AND APPTYPE = 2003 ");
    	sql.append(" AND STATUS = 100201 ");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	   
	    /**
	     * 校验导入的服务站代码是否重复
	     * @throws Exception
	     * @Author fanpeng 2014-10-24
	     */
	    public QuerySet checkMultipleDealerCode(User user, String dealerCode)throws Exception{
			
	    	StringBuffer sql= new StringBuffer();
	    	if (!dealerCode.equals("")) {
		    	sql.append("SELECT A.ROW_NO, A.DEALER_CODE\n" );
		    	sql.append("  FROM MAIN_DEALER_STAR_TMP A,\n" );
		    	sql.append("       (" );
	    	}
	    	sql.append("SELECT DEALER_CODE, COUNT(DEALER_CODE)\n" );
	    	sql.append("          FROM MAIN_DEALER_STAR_TMP\n" );
	    	sql.append("         WHERE USER_ACCOUNT = '"+user.getPersonName()+"'\n" );
	    	sql.append("         GROUP BY DEALER_CODE\n" );
	    	sql.append("        HAVING COUNT(DEALER_CODE) > 1" );
	    	if (!dealerCode.equals("")) {
		    	sql.append(") B\n" );
		    	sql.append(" WHERE A.DEALER_CODE = B.DEALER_CODE\n" );
		    	sql.append("   AND A.DEALER_CODE = '"+dealerCode+"'\n" );
		    	sql.append("   ORDER BY A.ROW_NO\n" );
	    	}
			return factory.select(null, sql.toString());
		}
	   
		/**
	    * 校验导入的序号（行号）是否重复
	    * @param user
	    * @return
	    * @throws Exception
	    */
	   public QuerySet checkMultipleRowNo(User user)throws Exception{
		   StringBuffer sql= new StringBuffer();
		   sql.append("SELECT ROW_NO,\n" );
		   sql.append("       COUNT(ROW_NO)\n" );
		   sql.append("  FROM MAIN_DEALER_STAR_TMP\n" );
		   sql.append(" WHERE USER_ACCOUNT = '"+user.getPersonName()+"'\n" );
		   sql.append(" GROUP BY ROW_NO\n" );
		   sql.append("HAVING COUNT(ROW_NO) > 1");
		   return factory.select(null, sql.toString());
	   }
	   
	    /**
	     * 查询服务站星级主表和临时表联合信息（把临时表的数据更新到主表中）
	     * @throws Exception
	     * @Author fanpeng 2014-10-17
	     */
		public QuerySet searchDealerStarTmpImport(User user) throws Exception{

			StringBuffer sql= new StringBuffer();
			sql.append("SELECT T.DEALER_ID,\n" );
			sql.append("       T.DEALER_CODE,\n" );
			sql.append("       T.DEALER_NAME,\n" );
			sql.append("       T.DEALER_STAR AS OLD_STAR,\n" );
			sql.append("       W.TMP_ID,\n" );
			sql.append("       W.DEALER_STAR AS NEW_STAR,\n" );
			sql.append("       W.USER_ACCOUNT,\n" );
			sql.append("       W.ROW_NO\n" );
			sql.append("  FROM MAIN_DEALER T, MAIN_DEALER_STAR_TMP W\n" );
			sql.append(" WHERE T.DEALER_CODE = W.DEALER_CODE\n" );

			return factory.select(null, sql.toString());
		}
		
	    //下载(导出)
	    public QuerySet download(String conditions, User user) throws Exception
	    {	    	

	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT ROWNUM,\n" );
	    	sql.append("       T.DEALER_ID,\n" );
	    	sql.append("       T.DEALER_NAME,\n" );
	    	sql.append("       T.DEALER_CODE,\n" );
	    	sql.append("       T.DEALER_STAR,\n" );
	    	sql.append("       T.BELONG_OFFICE,\n" );
	    	sql.append("       M.ONAME,\n" );
	    	sql.append("       N.PARANAME,\n" );
	    	sql.append("       T.STATUS,\n" );
	    	sql.append("       T.DEALER_TYPE\n" );
	    	sql.append("  FROM MAIN_DEALER T, TM_ORG M, USER_PARA_CONFIGURE N");
			sql.append(" where "+conditions+"\n" );
			sql.append(" AND T.BELONG_OFFICE=M.ORG_ID\n" );
			sql.append(" AND T.DEALER_STAR=N.PARAKEY\n" );
			sql.append(" AND T.STATUS = '100201'\n" );

	    	return factory.select(null, sql.toString());
	    }

}