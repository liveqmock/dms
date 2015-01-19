package com.org.dms.dao.part.basicInfoMng;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.planMng.forecast.ForecastMngDao;
import com.org.dms.vo.part.PtBaInfoVO;
import com.org.dms.vo.part.PtBaPriceLogVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class PtbaSelectDao extends BaseDAO {
	 /**
     * 定义instance
     *
     * @param pActionContext
     * @return warehouseMngDao 配件预测Dao
     */
    public static final PtbaSelectDao getInstance(ActionContext pActionContext) {

    	PtbaSelectDao ptbaselectdao = new PtbaSelectDao();
        pActionContext.setDBFactory(ptbaselectdao.factory);
        return ptbaselectdao;
    }

	 /**
     * 配件预测表(pt_bu_forcast)查询
     *
     * @param pPageManager 查询分页对象
     * @param pUser 当前登录user对象
     * @param pConditions sql条件(默认：1=1)
     * @return baseResultSet 结果集
     * @throws Exception
     */
    public Object searchForecast(PageManager pPageManager, User pUser, String pConditions,boolean pFlag) throws Exception {

        String wheres = pConditions;
        // 按所属机构查询，并且按预测月份升序排列
        wheres += " AND ORG_ID = " + pUser.getOrgId() + "\n"
                + " ORDER BY FORCAST_MONTH";
        pPageManager.setFilter(wheres);
        //定义返回结果集
        BaseResultSet baseResultSet = null;
        QuerySet querySet = null;
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT \n");
        sql.append("     FORCAST_ID,\n");
        sql.append("     FORCAST_MONTH,\n");
        sql.append("     FORCAST_STATUS\n");
        sql.append(" FROM \n");
        sql.append("     PT_BU_FORCAST \n");
        if (pFlag){
            // 执行方法，不需要传递conn参数
            baseResultSet = factory.select(sql.toString(), pPageManager);
//            baseResultSet.setFieldDic("FORCAST_STATUS", DicConstant.PJYCZT);
            return baseResultSet;
        } else {
            sql.append("WHERE " + wheres);
            // 执行方法，不需要传递conn参数
            querySet = factory.select(null,sql.toString());
            return querySet;
        }
    }
    /**
     * 配件预测明细表(pt_bu_forcast_dtl)查询
     *
     * @param pPageManager 查询分页对象
     * @param pUser 当前登录user对象
     * @param pConditions sql条件(默认：1=1)
     * @return QuerySet 结果集
     * @throws Exception
     */
    public BaseResultSet searchForecastDetail(PageManager pPageManager, String pConditions) throws Exception {

        pPageManager.setFilter(pConditions);
        //定义返回结果集
        BaseResultSet baseResultSet = null;
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT \n");
        sql.append("     DETAIL_ID,\n");
        sql.append("     FORCAST_ID,\n");
        sql.append("     PART_ID,\n");
        sql.append("     PART_CODE,\n");
        sql.append("     PART_NAME,\n");
        sql.append("     COUNT\n");
        sql.append(" FROM \n");
        sql.append("     PT_BU_FORCAST_DTL \n");
        //执行方法，不需要传递conn参数
        baseResultSet = factory.select(sql.toString(),pPageManager);
        return baseResultSet;
    }
}
