package com.org.dms.dao.part.marketMng.marketActiveMng;

import com.org.dms.vo.part.PtBuProActiveSummaryVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.mvc.context.ActionContext;

public class ProActiveSummaryDao extends BaseDAO {

	//定义instance
    public  static final ProActiveSummaryDao getInstance(ActionContext atx)
    {
    	ProActiveSummaryDao dao = new ProActiveSummaryDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
	 * 活动总结查询
	 * @throws Exception
     * @Author suoxiuli 2014-09-26
	 */
    public BaseResultSet searchProActiveSummary(PageManager page, String conditions, String orgId) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND (D.STATUS = 100201 or D.STATUS is null)";
    	wheres += " AND (D.SUMMARY_STATUS in (206101, 206104) or D.SUMMARY_STATUS is null)";
    	wheres += " AND C.CHECK_RESULT = 302001";
    	wheres += " AND C.DEAL_ID = D.DEAL_ID(+)";
    	
    	wheres += " AND C.DEAL_ID = B.DEAL_ID";
    	//配送中心、服务站活动执行提报查询
    	if (orgId != null && orgId != "") {
    		wheres += " AND  B.ORG_ID = "+orgId;
    	}
    	wheres += " AND B.ACTIVE_ID = A.ACTIVE_ID";
		wheres += " ORDER BY B.CREATE_TIME, B.DEAL_ID DESC ";
		page.setFilter(wheres);
    	
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("select A.ACTIVE_ID,\n" );
    	sql.append("       A.ACTIVE_CODE,\n" );
    	sql.append("       A.ACTIVE_NAME,\n" );
    	sql.append("       A.ACTIVE_STATUS,\n" );
    	sql.append("       A.START_DATE,\n" );
    	sql.append("       A.END_DATE,\n" );
    	sql.append("       A.ACTIVE_CONTENT,\n" );
    	sql.append("       B.DEAL_ID,\n" );
    	sql.append("       B.DEAL_CONTENT,\n" );
    	sql.append("       B.PLAN_FEE,\n" );
    	sql.append("       B.DEAL_STATUS,\n" );
    	sql.append("       B.ORG_ID,\n" );
    	sql.append("       C.CHECK_CON,\n" );
    	sql.append("       C.CHECK_RESULT,\n" );
    	sql.append("       C.CHECK_USER,\n" );
    	sql.append("       C.CHECK_DATE,\n" );
    	sql.append("       D.SUMMARY_ID,\n" );
    	sql.append("       D.SUMMARY_STATUS,\n" );
    	sql.append("       D.REAL_FEE,\n" );
    	sql.append("       D.SUMMARY_CON,\n" );
    	sql.append("       D.REMARKS,\n" );
    	sql.append("       D.CREATE_USER,\n" );
    	sql.append("       D.CREATE_TIME,\n" );
    	sql.append("       D.UPDATE_USER,\n" );
    	sql.append("       D.UPDATE_TIME,\n" );
    	sql.append("       D.STATUS\n" );
    	sql.append("  from PT_BU_PRO_ACTIVE            A,\n" );
    	//主车厂科室、配送中心、服务站活动执行提报查询
    	/*if (orgId != null && orgId != "") {
    		sql.append("       PT_BU_PRO_ACTIVE_DEALER    E,\n" );
    	}*/
    	sql.append("       PT_BU_PRO_ACTIVE_DEAL       B,\n" );
    	sql.append("       PT_BU_PRO_ACTIVE_DEAL_CHECK C,\n" );
    	sql.append("       PT_BU_PRO_ACTIVE_SUMMARY    D");

    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("ACTIVE_STATUS","HDLCZT");
    	bs.setFieldDateFormat("START_DATE", "yyyy-MM-dd");
    	bs.setFieldDateFormat("END_DATE", "yyyy-MM-dd");
    	bs.setFieldDic("DEAL_STATUS","HDZXZT");
    	bs.setFieldDic("CHECK_RESULT","SHJG");
    	bs.setFieldUserID("CHECK_USER");
    	bs.setFieldDateFormat("CHECK_DATE", "yyyy-MM-dd");
    	bs.setFieldDic("SUMMARY_STATUS","HDZXZT");
    	bs.setFieldUserID("CREATE_USER");
    	bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	bs.setFieldUserID("UPDATE_USER");
    	bs.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	bs.setFieldDic("STATUS","YXBS");
    	return bs;
    }
    
    /**
     * 判断活动总结执行方案执行主键是否存在
     * @throws Exception
     * @Author suoxiuli 2014-09-26
     */
	public QuerySet checkDealId(String dealId) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT COUNT(1) NUMS \n");
    	sql.append(" FROM PT_BU_PRO_ACTIVE_SUMMARY \n");
    	sql.append(" WHERE DEAL_ID = '" + dealId +"'");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	
    /**
	 * 活动总结新增
	 * @throws Exception
     * @Author suoxiuli 2014-09-26
	 */
    public boolean insertProActiveSummary(PtBuProActiveSummaryVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }
	
    /**
	 * 活动总结修改
	 * @throws Exception
     * @Author suoxiuli 2014-09-26
	 */
	public boolean updateProActiveSummary(PtBuProActiveSummaryVO vo) throws Exception
    {
    	return factory.update(vo);
    }
    
    /**
     * 活动总结方案提报
     * @throws Exception
     * @Author suoxiuli 2014-09-26
     */
    public boolean proActiveSummaryReport(String summaryId, String summaryStatus, String sumReportUser) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" update PT_BU_PRO_ACTIVE_SUMMARY \n");
    	sql.append(" set SUMMARY_STATUS = "+ summaryStatus +", \n");
    	sql.append(" SUM_REPORT_USER = '"+ sumReportUser +"', \n");
    	sql.append(" SUM_REPORT_TIME = sysdate \n");
    	sql.append(" where summary_id = '" + summaryId + "' \n");
    	return factory.update(sql.toString(), null);
    }
    
}
