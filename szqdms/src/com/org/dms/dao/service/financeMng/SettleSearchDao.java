package com.org.dms.dao.service.financeMng;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 结算单查询DAO
 * @author zts
 *
 */
public class SettleSearchDao extends BaseDAO {
	 //定义instance
	public  static final SettleSearchDao getInstance(ActionContext atx)
	{
		SettleSearchDao dao = new SettleSearchDao();	
		atx.setDBFactory(dao.factory);
		return dao;
	}

	/**
	 * 服务店 结算单查询
	 * @param page
	 * @param condition
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet settleDealerSearch(PageManager page,String condition,User user)throws Exception{
		String where = condition;
			  where += " AND T.SETTLE_ID = C.SETTLE_ID \n"+
					  "  AND T.SETTLE_STATUS >="+DicConstant.JSZT_02+""+
		               " AND T.ORG_ID ="+user.getOrgId()+"";
		page.setFilter(where);
		BaseResultSet bs=null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.SETTLE_ID,\n" );
		sql.append("       T.SETTLE_NO,\n" );
		sql.append("       T.ORG_ID,\n" );
		sql.append("       T.ORG_CODE,\n" );
		sql.append("       T.ORG_NAME,\n" );
		sql.append("       T.SETTLE_DATE,\n" );
		sql.append("       T.COSTS,\n" );
		sql.append("       T.RE_COSTS,\n" );
		sql.append("       T.POLICY_SUP,\n" );
		sql.append("       T.CASH_GIFT,\n" );
		sql.append("       T.CAR_AWARD,\n" );
		sql.append("       T.AP_COSTS,\n" );
		sql.append("       T.OTHERS,\n" );
		sql.append("       T.SUMMARY,\n" );
		sql.append("       T.REMARKS,\n" );
		sql.append("       T.INVOICE_NO,\n" );
		sql.append("       T.INVOICE_AMOUNT,\n" );
		sql.append("       T.INVOICE_DATE,\n" );
		sql.append("       T.SETTLE_STATUS,\n" );
		sql.append("       T.SETTLE_TYPE,\n" );
		sql.append("       C.CLAIM_COUNT\n" );
		sql.append("  FROM SE_BU_CLAIM_SETTLE T,");
		sql.append("       (SELECT R.SETTLE_ID, COUNT(R.CLAIM_ID) CLAIM_COUNT\n" );
		sql.append("          FROM SE_BU_CLAIM_SETTLE_REL R\n" );
		sql.append("         GROUP BY R.SETTLE_ID) C");
		bs=factory.select(sql.toString(), page);
		bs.setFieldDic("SETTLE_TYPE", "JSLX");
		bs.setFieldDic("SETTLE_STATUS", "JSZT");
		bs.setFieldDateFormat("SETTLE_DATE","yyyy-MM-dd");
		bs.setFieldDateFormat("INVOICE_DATE","yyyy-MM-dd");
		return bs;
	}
	/**
	 * 车厂端 结算单明细查询
	 * @param page
	 * @param condition
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet searchSettleOemDetail(PageManager page,String condition,User user)throws Exception{
		String where = condition;
		where +=  " AND T.SETTLE_ID = R.SETTLE_ID\n"+
				  " AND C.CLAIM_ID = R.CLAIM_ID\n"+
				  " AND T.STATUS="+DicConstant.YXBS_01+"\n"+
				  //" AND T.OEM_COMPANY_ID = "+user.getOemCompanyId()+""
				  " ORDER BY T.SETTLE_NO";
		page.setFilter(where);
		BaseResultSet bs=null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.SETTLE_ID,\n" );
		sql.append("       T.SETTLE_NO,\n" );
		sql.append("       T.SETTLE_DATE,\n" );
		sql.append("       T.SETTLE_TYPE,\n" );
		sql.append("       C.CLAIM_ID,\n" );
		sql.append("       C.CLAIM_NO,\n" );
		sql.append("       T.ORG_CODE,\n" );
		sql.append("       T.ORG_NAME,\n" );
		sql.append("       T.INVOICE_NO,\n" );
		sql.append("       T.INVOICE_DATE,\n" );
		sql.append("       T.SETTLE_STATUS,\n" );
		sql.append("       NVL(R.WORK_COSTS, 0) WORK_COSTS, --工时费\n" );
		sql.append("       NVL(R.AQJC_COSTS, 0) AQJC_COSTS, --安全检查费\n" );
		sql.append("       NVL(R.SQPX_COSTS, 0) SQPX_COSTS, --售前培训费\n" );
		sql.append("       NVL(R.OUT_COSTS, 0) OUT_COSTS, --外出费用\n" );
		sql.append("       NVL(R.MAINTENANCE_COSTS, 0) MAINTENANCE_COSTS, --首保费用\n" );
		sql.append("       NVL(R.MATERIAL_COSTS, 0) MATERIAL_COSTS, --材料费用\n" );
		sql.append("       NVL(R.WORK_COSTS, 0) + NVL(R.OUT_COSTS, 0) +NVL(R.SQPX_COSTS, 0)+\n" );
		sql.append("       NVL(R.MAINTENANCE_COSTS, 0) + NVL(R.MATERIAL_COSTS, 0)+NVL(R.AQJC_COSTS, 0) SPZF --索赔总费\n" );
		sql.append("  FROM SE_BU_CLAIM_SETTLE T, SE_BU_CLAIM_SETTLE_REL R, SE_BU_CLAIM C");
		bs=factory.select(sql.toString(), page);
		bs.setFieldDic("SETTLE_TYPE", "JSLX");
		bs.setFieldDic("SETTLE_STATUS", "JSZT");
		bs.setFieldDateFormat("SETTLE_DATE","yyyy-MM-dd HH:mm:ss");
		bs.setFieldDateFormat("INVOICE_DATE","yyyy-MM-dd");
		return bs;
	}
	
	/**
	 * 车厂端 结算单明细查询
	 * @param page
	 * @param condition
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet searchSettleDetail(PageManager page,String condition,User user)throws Exception{
		String where = condition;
			  where += "  AND T.SETTLE_ID = R.SETTLE_ID \n"
			  		+ "AND C.CLAIM_ID = R.CLAIM_ID "
			  		+ "AND T.SETTLE_STATUS >= "+DicConstant.JSZT_02+" "
			  		+ "AND T.STATUS = "+DicConstant.YXBS_01+" "
		            +" AND C.ORG_ID ="+user.getOrgId()+" "
		            + "ORDER BY T.SETTLE_NO ";
		page.setFilter(where);
		BaseResultSet bs=null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.SETTLE_ID,\n" );
		sql.append("       T.SETTLE_NO,\n" );
		sql.append("       T.SETTLE_DATE,\n" );
		sql.append("       T.SETTLE_TYPE,\n" );
		sql.append("       C.CLAIM_ID,\n" );
		sql.append("       C.CLAIM_NO,\n" );
		sql.append("       T.ORG_CODE,\n" );
		sql.append("       T.ORG_NAME,\n" );
		sql.append("       T.INVOICE_NO,\n" );
		sql.append("       T.INVOICE_DATE,\n" );
		sql.append("       T.SETTLE_STATUS,\n" );
		sql.append("       NVL(R.WORK_COSTS, 0) WORK_COSTS, --工时费\n" );
		sql.append("       NVL(R.AQJC_COSTS, 0) AQJC_COSTS, --安全检查费\n" );
		sql.append("       NVL(R.SQPX_COSTS, 0) SQPX_COSTS, --售前培训费\n" );
		sql.append("       NVL(R.OUT_COSTS, 0) OUT_COSTS, --外出费用\n" );
		sql.append("       NVL(R.MAINTENANCE_COSTS, 0) MAINTENANCE_COSTS, --首保费用\n" );
		sql.append("       NVL(R.MATERIAL_COSTS, 0) MATERIAL_COSTS, --材料费用\n" );
		sql.append("       NVL(R.WORK_COSTS, 0) + NVL(R.OUT_COSTS, 0) +NVL(R.SQPX_COSTS, 0)+\n" );
		sql.append("       NVL(R.MAINTENANCE_COSTS, 0) + NVL(R.MATERIAL_COSTS, 0)+NVL(R.AQJC_COSTS, 0) SPZF --索赔总费\n" );
		sql.append("  FROM SE_BU_CLAIM_SETTLE T, SE_BU_CLAIM_SETTLE_REL R, SE_BU_CLAIM C");
		bs=factory.select(sql.toString(), page);
		bs.setFieldDic("SETTLE_TYPE", "JSLX");
		bs.setFieldDic("SETTLE_STATUS", "JSZT");
		bs.setFieldDateFormat("SETTLE_DATE","yyyy-MM-dd");
		bs.setFieldDateFormat("INVOICE_DATE","yyyy-MM-dd");
		return bs;
	}
	
	/**
	 * 厂端结算单查询
	 * @param page
	 * @param condition
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet settleOemSearch(PageManager page,String condition,User user)throws Exception{
		String where = condition;
		  	   where += " AND T.SETTLE_ID = C.SETTLE_ID(+) \n"+
		  			    //" AND T.OEM_COMPANY_ID ="+user.getOemCompanyId()+"\n"+
		  	   			" ORDER BY T.ORG_ID,T.SETTLE_NO ";
		page.setFilter(where);
		BaseResultSet bs=null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.SETTLE_ID,\n" );
		sql.append("       T.SETTLE_NO,\n" );
		sql.append("       T.ORG_ID,\n" );
		sql.append("       T.ORG_CODE,\n" );
		sql.append("       T.ORG_NAME,\n" );
		sql.append("       T.SETTLE_DATE,\n" );
    	sql.append("       NVL(T.COSTS,0)COSTS,\n" );
    	sql.append("       NVL(T.RE_COSTS,0)RE_COSTS,\n" );
    	sql.append("       NVL(T.POLICY_SUP,0)POLICY_SUP,\n" );
    	sql.append("       NVL(T.CASH_GIFT,0)CASH_GIFT,\n" );
    	sql.append("       NVL(T.CAR_AWARD,0)CAR_AWARD,\n" );
    	sql.append("       NVL(T.AP_COSTS,0)AP_COSTS,\n" );
    	sql.append("       NVL(T.OTHERS,0)OTHERS,\n" );
    	sql.append("       NVL(T.SUMMARY,0)SUMMARY,\n" );
    	sql.append("       NVL(T.MANUALLY_COST,0)MANUALLY_COST,\n" );
		sql.append("       T.REMARKS,\n" );
		sql.append("       T.INVOICE_NO,\n" );
		sql.append("       T.INVOICE_AMOUNT,\n" );
		sql.append("       T.INVOICE_DATE,\n" );
		sql.append("       T.SETTLE_STATUS,\n" );
		sql.append("       T.SETTLE_TYPE,\n" );
		sql.append("       C.CLAIM_COUNT\n" );
		sql.append("  FROM SE_BU_CLAIM_SETTLE T,");
		sql.append("       (SELECT R.SETTLE_ID, COUNT(R.CLAIM_ID) CLAIM_COUNT\n" );
		sql.append("          FROM SE_BU_CLAIM_SETTLE_REL R\n" );
		sql.append("         GROUP BY R.SETTLE_ID) C");
		bs=factory.select(sql.toString(), page);
		bs.setFieldDic("SETTLE_TYPE", "JSLX");
		bs.setFieldDic("SETTLE_STATUS", "JSZT");
		bs.setFieldDateFormat("SETTLE_DATE","yyyy-MM-dd");
		bs.setFieldDateFormat("INVOICE_DATE","yyyy-MM-dd");
		return bs;
	}
	  public QuerySet oemDownload(String conditions) throws Exception{
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT T.SETTLE_ID,\n" );
	    	sql.append("       T.SETTLE_NO,\n" );
	    	sql.append("       T.ORG_ID,\n" );
	    	sql.append("       T.ORG_CODE,\n" );
	    	sql.append("       T.ORG_NAME,\n" );
	    	sql.append("       T.SETTLE_DATE,\n" );
	    	sql.append("       NVL(T.COSTS,0)COSTS,\n" );
	    	sql.append("       NVL(T.RE_COSTS,0)RE_COSTS,\n" );
	    	sql.append("       NVL(T.POLICY_SUP,0)POLICY_SUP,\n" );
	    	sql.append("       NVL(T.CASH_GIFT,0)CASH_GIFT,\n" );
	    	sql.append("       NVL(T.CAR_AWARD,0)CAR_AWARD,\n" );
	    	sql.append("       NVL(T.AP_COSTS,0)AP_COSTS,\n" );
	    	sql.append("       NVL(T.OTHERS,0)OTHERS,\n" );
	    	sql.append("       NVL(T.SUMMARY,0)SUMMARY,\n" );
	    	sql.append("       NVL(T.MANUALLY_COST,0)MANUALLY_COST,\n" );
	    	sql.append("       T.REMARKS,\n" );
	    	sql.append("       T.INVOICE_NO,\n" );
	    	sql.append("       T.INVOICE_AMOUNT,\n" );
	    	sql.append("       T.INVOICE_DATE,\n" );
	    	sql.append("       (SELECT D.DIC_VALUE FROM DIC_TREE D WHERE D.ID = T.SETTLE_STATUS)SETTLE_STATUS,\n" );
	    	sql.append("       (SELECT D.DIC_VALUE FROM DIC_TREE D WHERE D.ID = T.SETTLE_TYPE)SETTLE_TYPE,\n" );
	    	sql.append("       C.CLAIM_COUNT\n" );
	    	sql.append("  FROM SE_BU_CLAIM_SETTLE T,\n" );
	    	sql.append("       (SELECT R.SETTLE_ID, COUNT(R.CLAIM_ID) CLAIM_COUNT\n" );
	    	sql.append("          FROM SE_BU_CLAIM_SETTLE_REL R\n" );
	    	sql.append("         GROUP BY R.SETTLE_ID) C\n" );
	    	sql.append(" WHERE "+conditions+"\n" );
	    	sql.append("   AND T.SETTLE_ID = C.SETTLE_ID(+)\n" );
	    	sql.append(" ORDER BY T.ORG_ID, T.SETTLE_NO");

	    	return factory.select(null, sql.toString());
	    }
	  public QuerySet dtlDownload(String conditions) throws Exception{
		  StringBuffer sql= new StringBuffer();
		  sql.append("SELECT T.SETTLE_ID,\n" );
		  sql.append("       T.SETTLE_NO,\n" );
		  sql.append("       (SELECT TR.DIC_VALUE FROM DIC_TREE TR WHERE TR.ID = T.SETTLE_TYPE)SETTLE_TYPE,\n" );
		  sql.append("       T.SETTLE_DATE,\n" );
		  sql.append("       C.CLAIM_ID,\n" );
		  sql.append("       C.CLAIM_NO,\n" );
		  sql.append("       T.ORG_CODE,\n" );
		  sql.append("       T.ORG_NAME,\n" );
		  sql.append("       T.INVOICE_NO,\n" );
		  sql.append("       T.INVOICE_DATE,\n" );
		  sql.append("       (SELECT TR.DIC_VALUE FROM DIC_TREE TR WHERE TR.ID = T.SETTLE_STATUS) SETTLE_STATUS,\n" );
		  sql.append("       NVL(R.WORK_COSTS, 0) WORK_COSTS, --工时费\n" );
		  sql.append("       NVL(R.AQJC_COSTS, 0) AQJC_COSTS, --安全检查费\n" );
		  sql.append("       NVL(R.SQPX_COSTS, 0) SQPX_COSTS, --售前培训费\n" );
		  sql.append("       NVL(R.OUT_COSTS, 0) OUT_COSTS, --外出费用\n" );
		  sql.append("       NVL(R.MAINTENANCE_COSTS, 0) MAINTENANCE_COSTS, --首保费用\n" );
		  sql.append("       NVL(R.MATERIAL_COSTS, 0) MATERIAL_COSTS, --材料费用\n" );
		  sql.append("       NVL(R.WORK_COSTS, 0) + NVL(R.OUT_COSTS, 0) + NVL(R.SQPX_COSTS, 0) +\n" );
		  sql.append("       NVL(R.MAINTENANCE_COSTS, 0) + NVL(R.MATERIAL_COSTS, 0) +\n" );
		  sql.append("       NVL(R.AQJC_COSTS, 0) SPZF --索赔总费\n" );
		  sql.append("  FROM SE_BU_CLAIM_SETTLE T, SE_BU_CLAIM_SETTLE_REL R, SE_BU_CLAIM C\n" );
		  sql.append(" WHERE "+conditions+"\n" );
		  sql.append("   AND T.SETTLE_ID = R.SETTLE_ID\n" );
		  sql.append("   AND C.CLAIM_ID = R.CLAIM_ID\n" );
		  sql.append("   AND T.STATUS = 100201\n" );
		  sql.append(" ORDER BY T.SETTLE_NO");

		  return factory.select(null, sql.toString());
	  }
	  public QuerySet dealerDtlDownload(String conditions,User user) throws Exception{
		  StringBuffer sql= new StringBuffer();
		  sql.append("SELECT T.SETTLE_ID,\n" );
		  sql.append("       T.SETTLE_NO,\n" );
		  sql.append("       T.SETTLE_DATE,\n" );
		  sql.append("       (SELECT TR.DIC_VALUE FROM DIC_TREE TR WHERE TR.ID = T.SETTLE_TYPE) SETTLE_TYPE,\n" );
		  sql.append("       (SELECT TR.DIC_VALUE FROM DIC_TREE TR WHERE TR.ID = T.SETTLE_STATUS) SETTLE_STATUS,\n" );
		  sql.append("       C.CLAIM_ID,\n" );
		  sql.append("       C.CLAIM_NO,\n" );
		  sql.append("       T.ORG_CODE,\n" );
		  sql.append("       T.ORG_NAME,\n" );
		  sql.append("       T.INVOICE_NO,\n" );
		  sql.append("       T.INVOICE_DATE,\n" );
		  sql.append("       NVL(R.WORK_COSTS, 0) WORK_COSTS, --工时费\n" );
		  sql.append("       NVL(R.AQJC_COSTS, 0) AQJC_COSTS, --安全检查费\n" );
		  sql.append("       NVL(R.SQPX_COSTS, 0) SQPX_COSTS, --售前培训费\n" );
		  sql.append("       NVL(R.OUT_COSTS, 0) OUT_COSTS, --外出费用\n" );
		  sql.append("       NVL(R.MAINTENANCE_COSTS, 0) MAINTENANCE_COSTS, --首保费用\n" );
		  sql.append("       NVL(R.MATERIAL_COSTS, 0) MATERIAL_COSTS, --材料费用\n" );
		  sql.append("       NVL(R.WORK_COSTS, 0) + NVL(R.OUT_COSTS, 0) +NVL(R.SQPX_COSTS, 0)+\n" );
		  sql.append("       NVL(R.MAINTENANCE_COSTS, 0) + NVL(R.MATERIAL_COSTS, 0) +\n" );
		  sql.append("       NVL(R.AQJC_COSTS, 0) SPZF --索赔总费\n" );
		  sql.append("  FROM SE_BU_CLAIM_SETTLE T, SE_BU_CLAIM_SETTLE_REL R, SE_BU_CLAIM C\n" );
		  sql.append(" WHERE "+conditions+"\n" );
		  sql.append("   AND T.SETTLE_ID = R.SETTLE_ID\n" );
		  sql.append("   AND C.CLAIM_ID = R.CLAIM_ID\n" );
		  sql.append("   AND T.SETTLE_STATUS >= 304502\n" );
		  sql.append("   AND T.STATUS = 100201\n" );
		  sql.append("   AND C.ORG_ID = "+user.getOrgId()+"\n" );
		  sql.append(" ORDER BY T.SETTLE_NO");

		  return factory.select(null, sql.toString());
	  }
}
