package com.org.dms.dao.part.storageMng.search;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class PromotionQueryDao extends BaseDAO{
	public static final PromotionQueryDao getInstance(ActionContext atx) {

		PromotionQueryDao dao = new PromotionQueryDao();
		atx.setDBFactory(dao.factory);

        return dao;
    }
	
	public BaseResultSet promotionSearch(PageManager page, User user, String conditions) throws Exception {
        String wheres = conditions;
        wheres += " AND PBP.OEM_COMPANY_ID=" + user.getOemCompanyId()+" AND PBP.PROM_STATUS IN("+DicConstant.CXHDZT_01+","+DicConstant.CXHDZT_02+")\n";
        wheres +="  AND EXISTS (SELECT 1 FROM PT_BU_PROMOTION_AREA T WHERE T.PROM_ID = PBP.PROM_ID AND T.AREA_ID = (SELECT PID FROM TM_ORG WHERE ORG_ID = "+user.getOrgId()+"))";
        wheres += " ORDER BY PBP.CREATE_TIME DESC\n";
        page.setFilter(wheres);
        //定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT PBP.PROM_ID,\n");
        sql.append("       PBP.PROM_CODE,\n");
        sql.append("       PBP.PROM_NAME,\n");
        sql.append("       PBP.PROM_TYPE,\n");
        sql.append("       PBP.START_DATE,\n");
        sql.append("       PBP.END_DATE,\n");
        sql.append("       PBP.IF_TRANS_FREE,\n");
        sql.append("       PBP.PROM_STATUS,\n");
        sql.append("       PBP.ANNOUNCEMENT_NO,\n");
        sql.append("       PBP.REMARKS\n");
        sql.append("  FROM PT_BU_PROMOTION PBP\n");
        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("PROM_TYPE", "CXHDLX");
        bs.setFieldDic("PROM_STATUS", "CXHDZT");
        bs.setFieldDic("IF_TRANS_FREE", "SF");
        bs.setFieldDateFormat("START_DATE", "yyyy-MM-dd");
        bs.setFieldDateFormat("END_DATE", "yyyy-MM-dd");
        return bs;
    }

}
