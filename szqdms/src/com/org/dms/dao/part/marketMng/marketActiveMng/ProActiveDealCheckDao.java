package com.org.dms.dao.part.marketMng.marketActiveMng;

import com.org.dms.vo.part.PtBuProActiveDealCheckVO;
import com.org.dms.vo.part.PtBuProActiveDealVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.mvc.context.ActionContext;
import com.org.dms.common.DicConstant;

public class ProActiveDealCheckDao extends BaseDAO {

	//定义instance
    public  static final ProActiveDealCheckDao getInstance(ActionContext atx)
    {
    	ProActiveDealCheckDao dao = new ProActiveDealCheckDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
	 * 活动方案制定查询
	 * @throws Exception
     * @Author suoxiuli 2014-09-24
	 */
    public BaseResultSet searchProActiveDeal(PageManager page, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND  A.STATUS = 100201";
    	wheres += " AND  B.DEAL_STATUS = 206102";
    	wheres += " AND  B.ORG_ID = C.ORG_ID";
    	wheres += " AND  A.ACTIVE_ID = B.ACTIVE_ID";
		wheres += " ORDER BY B.CREATE_TIME DESC ";
		page.setFilter(wheres);
    	
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("select B.DEAL_ID,\n" );
    	sql.append("       B.ACTIVE_ID,\n" );
    	sql.append("       B.ACTIVE_CODE,\n" );
    	sql.append("       B.ACTIVE_NAME,\n" );
    	sql.append("       A.START_DATE,\n" );
    	sql.append("       A.END_DATE,\n" );
    	sql.append("       A.ACTIVE_CONTENT,\n" );
    	sql.append("       B.DEAL_CONTENT,\n" );
    	sql.append("       B.PLAN_FEE,\n" );
    	sql.append("       B.DEAL_STATUS,\n" );
    	sql.append("       B.STATUS,\n" );
    	sql.append("       B.PERSON_NUMS,\n" );
    	sql.append("       B.ORG_ID,\n" );
    	sql.append("       B.COMPANY_ID,\n" );
    	sql.append("       B.CREATE_USER,\n" );
    	sql.append("       B.CREATE_TIME,\n" );
    	sql.append("       B.UPDATE_USER,\n" );
    	sql.append("       B.UPDATE_TIME,\n" );
    	sql.append("       B.REPORT_USER,\n" );
    	sql.append("       B.REPORT_TIME,\n" );
    	sql.append("       C.CODE,\n" );
    	sql.append("       C.ONAME\n" );
    	sql.append("  from PT_BU_PRO_ACTIVE A,\n" );
    	sql.append("       PT_BU_PRO_ACTIVE_DEAL B,\n" );
    	sql.append("       TM_ORG  C");

    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("STATUS","YXBS");
    	bs.setFieldUserID("CREATE_USER");
    	bs.setFieldDateFormat("START_DATE", "yyyy-MM-dd");
    	bs.setFieldDateFormat("END_DATE", "yyyy-MM-dd");
    	bs.setFieldDic("DEAL_STATUS","HDZXZT");
    	bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	bs.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	bs.setFieldUserID("REPORT_USER");
    	bs.setFieldDateFormat("REPORT_TIME", "yyyy-MM-dd");
    	return bs;
    }
	
    /**
	 * 活动执行方案审核通过|驳回
	 * @throws Exception
     * @Author suoxiuli 2014-09-25
	 */
    public boolean insertProActiveDealLogCheck(PtBuProActiveDealCheckVO vo, String checkResult)
            throws Exception
    {
    	//1、更新活动执行状态为审核通过
    	StringBuffer sql = new StringBuffer();
    	sql.append(" update PT_BU_PRO_ACTIVE_DEAL \n");
    	if (checkResult.equals("302001")) {
			//审核通过
    		sql.append(" set DEAL_STATUS = "+ DicConstant.HDZXZT_03 +" \n");
    		vo.setCheckResult(DicConstant.SHJG_01);
		}
		else {
			//审核驳回
			sql.append(" set DEAL_STATUS = "+ DicConstant.HDZXZT_04 +" \n");
			vo.setCheckResult(DicConstant.SHJG_02);
		}
    	
    	sql.append(" where deal_id = '" + vo.getDealId() + "' \n");
    	factory.update(sql.toString(), null);
    	
    	//2、新增审核意见
    	return factory.insert(vo);
    }
}
