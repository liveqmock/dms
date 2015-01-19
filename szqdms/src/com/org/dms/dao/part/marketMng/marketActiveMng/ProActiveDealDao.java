package com.org.dms.dao.part.marketMng.marketActiveMng;

import com.org.dms.vo.part.PtBuProActiveDealVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.mvc.context.ActionContext;

public class ProActiveDealDao extends BaseDAO {

	//定义instance
    public  static final ProActiveDealDao getInstance(ActionContext atx)
    {
    	ProActiveDealDao dao = new ProActiveDealDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
	 * 主车厂活动方案制定查询
	 * @throws Exception
     * @Author suoxiuli 2014-09-24
	 */
    public BaseResultSet searchProActive(PageManager page, String conditions, String orgId) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND  (C.STATUS = 100201 or C.STATUS is null)";
    	wheres += " AND  (C.DEAL_STATUS in (206101,206104) or C.DEAL_STATUS is null)";
    	wheres += " AND  B.ACTIVE_ID = C.ACTIVE_ID(+)";
    	wheres += " AND  B.ORG_ID = C.ORG_ID(+)";
    	//配送中心、服务站活动执行提报查询
    	if (orgId != null && orgId != "") {
    		wheres += " AND  B.ORG_ID = "+orgId;
    	}
    	
    	wheres += " AND  B.ACTIVE_ID = A.ACTIVE_ID";
    	wheres += " AND  B.ORG_ID = E.ORG_ID";
		wheres += " ORDER BY C.CREATE_TIME DESC ";
		page.setFilter(wheres);
    	
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("select C.DEAL_ID,\n" );
    	sql.append("       A.ACTIVE_ID,\n" );
    	sql.append("       A.ACTIVE_CODE,\n" );
    	sql.append("       A.ACTIVE_NAME,\n" );
    	sql.append("       A.ACTIVE_STATUS,\n" );
    	sql.append("       A.START_DATE,\n" );
    	sql.append("       A.END_DATE,\n" );
    	sql.append("       A.ISSUE_USER,\n" );
    	sql.append("       A.ISSUE_TIME,\n" );
    	sql.append("       B.ORG_ID,\n" );
    	sql.append("       C.DEAL_CONTENT,\n" );
    	sql.append("       C.PLAN_FEE,\n" );
    	sql.append("       C.PERSON_NUMS,\n" );
    	sql.append("       C.DEAL_STATUS,\n" );
    	sql.append("       C.REMARKS,\n" );
    	sql.append("       C.STATUS,\n" );
    	sql.append("       C.COMPANY_ID,\n" );
    	sql.append("       C.CREATE_USER,\n" );
    	sql.append("       C.CREATE_TIME,\n" );
    	sql.append("       C.UPDATE_USER,\n" );
    	sql.append("       C.UPDATE_TIME,\n" );
    	sql.append("       C.REPORT_USER,\n" );
    	sql.append("       C.REPORT_TIME,\n" );
    	sql.append("       E.CODE,\n" );
    	sql.append("       E.ONAME\n" );
    	sql.append("  from PT_BU_PRO_ACTIVE A,\n" );
    	sql.append("       PT_BU_PRO_ACTIVE_DEALER B,\n" );
    	sql.append("       PT_BU_PRO_ACTIVE_DEAL C,\n" );
    	sql.append("       TM_ORG E");

    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("STATUS","YXBS");
    	bs.setFieldDic("ACTIVE_STATUS","HDLCZT");
    	bs.setFieldUserID("ISSUE_USER");
    	bs.setFieldDateFormat("ISSUE_TIME", "yyyy-MM-dd");
    	bs.setFieldUserID("CREATE_USER");
    	bs.setFieldDateFormat("START_DATE", "yyyy-MM-dd");
    	bs.setFieldDateFormat("END_DATE", "yyyy-MM-dd");
    	bs.setFieldDic("DEAL_STATUS","HDZXZT");
    	bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	bs.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	bs.setFieldUserID("REPORT_USER");
    	bs.setFieldDateFormat("REPORT_TIME", "yyyy-MM-dd HH:mm:ss");
    	return bs;
    }
    
    /**
     * 判断活动执行方案活动代码及执行方案机构是否存在
     * @throws Exception
     * @Author suoxiuli 2014-10-16
     */
	public QuerySet checkActiveCodeAndOrgId(String activeCode, String orgId) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT COUNT(1) NUMS \n");
    	sql.append(" FROM PT_BU_PRO_ACTIVE_DEAL \n");
    	sql.append(" WHERE ACTIVE_CODE = '"+activeCode+"' AND ORG_ID = " + orgId +"" );
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	
    /**
	 * 活动执行方案新增
	 * @throws Exception
     * @Author suoxiuli 2014-09-24
	 */
    public boolean insertProActiveDeal(PtBuProActiveDealVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }
	
    /**
	 * 活动执行方案修改
	 * @throws Exception
     * @Author suoxiuli 2014-09-24
	 */
	public boolean updateProActiveDeal(PtBuProActiveDealVO vo) throws Exception
    {
    	return factory.update(vo);
    }
    
    /**
     * 活动执行方案提报
     * @throws Exception
     * @Author suoxiuli 2014-09-25
     */
    public boolean proActiveDealReport(String dealId, String dealStatus, String reportUser) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" update PT_BU_PRO_ACTIVE_DEAL \n");
    	sql.append(" set DEAL_STATUS = "+ dealStatus +", \n");
    	sql.append(" REPORT_USER = '"+ reportUser +"', \n");
    	sql.append(" REPORT_TIME = sysdate \n");
    	sql.append(" WHERE DEAL_ID = '" + dealId + "' \n");
    	return factory.update(sql.toString(), null);
    }
    
}
