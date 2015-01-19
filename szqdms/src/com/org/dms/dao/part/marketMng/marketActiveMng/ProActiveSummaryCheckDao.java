package com.org.dms.dao.part.marketMng.marketActiveMng;

import com.org.dms.vo.part.PtBuProActiveSummaryCheckVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.mvc.context.ActionContext;
import com.org.dms.common.DicConstant;

public class ProActiveSummaryCheckDao extends BaseDAO {

	//定义instance
    public  static final ProActiveSummaryCheckDao getInstance(ActionContext atx)
    {
    	ProActiveSummaryCheckDao dao = new ProActiveSummaryCheckDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
	 * 活动总结提报结果查询
	 * @throws Exception
     * @Author suoxiuli 2014-09-26
	 */
    public BaseResultSet searchProActiveSummary(PageManager page, String conditions) throws Exception
    {
		String wheres = conditions;
    	wheres += " AND C.DEAL_ID = D.DEAL_ID";
    	//wheres += " AND D.STATUS = 100201";  在条件页面上
    	//wheres += " AND D.SUMMARY_STATUS = 206102";  在条件页面上
    	wheres += " AND C.DEAL_ID = B.DEAL_ID";
    	wheres += " AND B.ORG_ID = E.ORG_ID";
    	wheres += " AND B.ACTIVE_ID = A.ACTIVE_ID";
		wheres += " ORDER BY D.CREATE_TIME, D.SUMMARY_ID DESC ";
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
    	sql.append("       B.PERSON_NUMS,\n" );
    	sql.append("       B.REPORT_USER,\n" );
    	sql.append("       B.REPORT_TIME,\n" );
    	sql.append("       B.ORG_ID,\n" );
    	sql.append("       C.CHECK_USER,\n" );
    	sql.append("       C.CHECK_DATE,\n" );
    	sql.append("       D.SUMMARY_ID,\n" );
    	sql.append("       D.SUMMARY_STATUS,\n" );
    	sql.append("       D.REAL_FEE,\n" );
    	sql.append("       D.SUMMARY_CON,\n" );
    	sql.append("       D.CREATE_USER,\n" );
    	sql.append("       D.CREATE_TIME,\n" );
    	sql.append("       D.UPDATE_USER,\n" );
    	sql.append("       D.UPDATE_TIME,\n" );
    	sql.append("       D.SUM_REPORT_USER,\n" );
    	sql.append("       D.SUM_REPORT_TIME,\n" );
    	sql.append("       D.STATUS,\n" );
    	sql.append("       E.CODE,\n" );
    	sql.append("       E.ONAME\n" );
    	sql.append("  from PT_BU_PRO_ACTIVE            A,\n" );
    	sql.append("       PT_BU_PRO_ACTIVE_DEAL       B,\n" );
    	sql.append("       PT_BU_PRO_ACTIVE_DEAL_CHECK C,\n" );
    	sql.append("       PT_BU_PRO_ACTIVE_SUMMARY    D,\n" );
    	sql.append("       TM_ORG                      E");

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
    	bs.setFieldUserID("REPORT_USER");//执行方案提报人
    	bs.setFieldDateFormat("REPORT_TIME", "yyyy-MM-dd");//执行方案提报时间
    	bs.setFieldUserID("SUM_REPORT_USER");
    	bs.setFieldDateFormat("SUM_REPORT_TIME", "yyyy-MM-dd");
    	bs.setFieldDic("STATUS","YXBS");
    	return bs;
    }
	
    /**
	 * 活动总结审核通过|驳回
	 * @throws Exception
     * @Author suoxiuli 2014-09-26
	 */                                           
    public boolean insertProActiveSummaryLogCheck(PtBuProActiveSummaryCheckVO vo, String checkResult)
            throws Exception
    {
    	//1、更新活动执行状态为审核通过
    	StringBuffer sql = new StringBuffer();
    	sql.append(" update PT_BU_PRO_ACTIVE_SUMMARY \n");
    	if (checkResult.equals("302001")) {
			//审核通过
    		sql.append(" set SUMMARY_STATUS = "+ DicConstant.HDZXZT_03 +" \n");
    		vo.setCheckResult("302001");
		}
		else {
			//审核驳回
			sql.append(" set SUMMARY_STATUS = "+ DicConstant.HDZXZT_04 +" \n");
			vo.setCheckResult("302002");
		}
    	
    	sql.append(" where SUMMARY_ID = '" + vo.getSummaryId() + "' \n");
    	factory.update(sql.toString(), null);
    	
    	//2、新增审核意见
    	return factory.insert(vo);
    }
    
    /**
	 * 活动信息查询详细信息(主车厂和渠道商)
	 * @throws Exception
     * @Author suoxiuli 2014-10-13
	 */
    public BaseResultSet searchProActiveDetail(PageManager page, String conditions, String orgId) throws Exception
    {
		String wheres = conditions;

		//活动总结审核通过的记录
		wheres += " AND F.CHECK_RESULT = 302001";
		wheres += " AND F.SUMMARY_ID = D.SUMMARY_ID";
    	wheres += " AND C.DEAL_ID = D.DEAL_ID";
    	wheres += " AND C.DEAL_ID = B.DEAL_ID";
    	wheres += " AND B.ORG_ID = G.ORG_ID";
    	wheres += " AND B.ACTIVE_ID = A.ACTIVE_ID";
    	
    	//主车厂科室、配送中心、服务站活动执行提报查询
    	if (orgId != null && orgId != "" && !orgId.equals("10000001")) {
    		wheres += " AND E.ORG_ID = "+orgId;
    		wheres += " AND A.ACTIVE_ID = E.ACTIVE_ID";
    	}
		wheres += " ORDER BY D.CREATE_TIME, D.SUMMARY_ID DESC ";
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
    	sql.append("       B.PERSON_NUMS,\n" );
    	sql.append("       B.DEAL_STATUS,\n" );
    	sql.append("       B.ORG_ID,\n" );
    	sql.append("       G.CODE,\n" );
    	sql.append("       G.ONAME,\n" );
    	sql.append("       C.CHECK_USER,\n" );
    	sql.append("       C.CHECK_DATE,\n" );
    	sql.append("       D.SUMMARY_ID,\n" );
    	sql.append("       D.SUMMARY_STATUS,\n" );
    	sql.append("       D.REAL_FEE,\n" );
    	sql.append("       D.SUMMARY_CON,\n" );
    	sql.append("       D.CREATE_USER,\n" );
    	sql.append("       D.CREATE_TIME,\n" );
    	sql.append("       D.UPDATE_USER,\n" );
    	sql.append("       D.UPDATE_TIME,\n" );
    	sql.append("       D.STATUS,\n" );
    	sql.append("       F.CHECK_CON,\n" );
    	sql.append("       F.REMARKS\n" );//总结备注
    	sql.append("  from PT_BU_PRO_ACTIVE            A,\n" );
    	//主车厂科室、配送中心、服务站活动执行提报查询
    	if (orgId != null && orgId != "" && !orgId.equals("10000001")) {
    		sql.append("       PT_BU_PRO_ACTIVE_DEALER E,\n" );
    	}
    	sql.append("       PT_BU_PRO_ACTIVE_DEAL       B,\n" );
    	sql.append("       PT_BU_PRO_ACTIVE_DEAL_CHECK C,\n" );
    	sql.append("       PT_BU_PRO_ACTIVE_SUMMARY    D,\n" );
    	sql.append("	 PT_BU_PRO_ACTIVE_SUMMAR_CHECK F,\n" );
    	sql.append("	 TM_ORG G" );

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
}
