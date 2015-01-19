package com.org.dms.dao.part.planMng.forecast;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuForcastDtlVO;
import com.org.dms.vo.part.PtBuForcastVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 预测汇总调整Dao
 *
 * 调整配送中心的预测上报
 * @author zhengyao
 * @date 2014-07-15
 * @version 1.0
 */
public class ForecastCarTotalMngDao extends BaseDAO {

    /**
     * 定义instance
     *
     * @param pActionContext
     * @return warehouseMngDao 预测上报Dao
     */
    public static final ForecastCarTotalMngDao getInstance(ActionContext pActionContext) {

        ForecastCarTotalMngDao forecastTotalMngDao = new ForecastCarTotalMngDao();
        pActionContext.setDBFactory(forecastTotalMngDao.factory);
        return forecastTotalMngDao;
    }

    /**
     * 配件预测表(pt_bu_forcast)添加
     *
     * @param ptBuForcastVO 配件预测实体
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean insertForecast(PtBuForcastVO ptBuForcastVO) throws Exception {

        return factory.insert(ptBuForcastVO);
    }

    /**
     * 配件预测明细表(pt_bu_forcast_dtl)添加
     *
     * @param ptBuForcastDtlVO 配件预测明细实体
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean insertPartReportDetail(PtBuForcastDtlVO ptBuForcastDtlVO) throws Exception {

        return factory.insert(ptBuForcastDtlVO);
    }

    /**
     * 配件预测明细表(pt_bu_forcast_dtl)修改
     * @param ptBuForcastVO 配件预测明细实体
     * @param pWhere 条件
     * @return true:成功;false:失败;
     * @throws Exception 
     */
    public boolean updateForecastDetail(PtBuForcastDtlVO ptBuForcastDtlVO) throws Exception {

        // 删除配件预测明细表sql
        String stockString = " UPDATE \n"
                           + " PT_BU_FORCAST_DTL \n"
                           + " SET \n"
                           + "    COUNT='" + ptBuForcastDtlVO.getCount() + "'"
                           + " WHERE \n"
                           + "     FORCAST_ID='" + ptBuForcastDtlVO.getForcastId() + "' \n"
                           + " AND PART_ID='" + ptBuForcastDtlVO.getPartId() + "'\n";
        return factory.update(stockString, null);
    }

    /**
     * 配件预测表(pt_bu_forcast)查询
     *
     * @param pConditions 月份
     * @param pUser 当前登录user对象
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet searchByForecastMonth(String pConditions, User pUser) throws Exception {

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT \n");
        sql.append("     FORCAST_STATUS \n");
        sql.append(" FROM \n");
        sql.append("     PT_BU_FORCAST \n");
        sql.append(" WHERE \n");
        sql.append("     FORCAST_MONTH='" + pConditions + "' \n");
        sql.append(" AND ORG_ID = " + pUser.getOrgId() + "\n");
        //执行方法，不需要传递conn参数
        return factory.select(null,sql.toString());
    }

    public BaseResultSet searchByForecastMonth2(PageManager page,String pConditions,String forecastMonth, User pUser) throws Exception {
    	String wheres = pConditions;
        wheres += "1=1 AND FORCAST_MONTH='" + forecastMonth + "' AND ORG_ID = " + pUser.getOrgId() + "\n";
        page.setFilter(wheres);
    	BaseResultSet baseResultSet = null;
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT \n");
        sql.append("     FORCAST_STATUS \n");
        sql.append(" FROM \n");
        sql.append("     PT_BU_FORCAST \n");
        //执行方法，不需要传递conn参数
        baseResultSet = factory.select(sql.toString(),page);
        return baseResultSet;
    }
    /**
     * 配件预测明细表(pt_bu_forcast_dtl)重复验证
     *
     * @param pPageManager 查询分页对象
     * @param pUser 当前登录user对象
     * @param pConditions sql条件(默认：1=1)
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet searchByForecastMonth(String pForcastMonth, PtBuForcastDtlVO pPtBuForcastDtlVO,User pUser) throws Exception {

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT \n");
        sql.append("     1 \n");
        sql.append(" FROM \n");
        sql.append("     PT_BU_FORCAST_DTL \n");
        sql.append(" WHERE \n");
        sql.append("     FORCAST_ID IN (SELECT FORCAST_ID FROM PT_BU_FORCAST WHERE ORG_ID='" + pUser.getOrgId() + "' AND FORCAST_MONTH = '" + pForcastMonth + "') \n");
        sql.append(" AND PART_CODE ='" + pPtBuForcastDtlVO.getPartCode() + "'\n");
        //执行方法，不需要传递conn参数
        return factory.select(null,sql.toString());
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
        wheres += " AND ORG_ID= " + pUser.getOrgId() + "\n"
                + " ORDER BY FORCAST_MONTH";
        pPageManager.setFilter(wheres);
        // 定义返回结果集
        BaseResultSet baseResultSet = null;
        QuerySet querySet = null;
        StringBuffer sql = new StringBuffer();
        sql.append("\n");
        sql.append(" SELECT \n");
        sql.append("     FORCAST_ID,\n");
        sql.append("     FORCAST_MONTH,\n");
        sql.append("     FORCAST_STATUS,\n");
        sql.append("     ORG_ID\n");
        sql.append(" FROM \n");
        sql.append("     PT_BU_FORCAST \n");
        if (pFlag){
            // 执行方法，不需要传递conn参数
            baseResultSet = factory.select(sql.toString(), pPageManager);
            baseResultSet.setFieldDic("FORCAST_STATUS", DicConstant.PJYCZT);
            return baseResultSet;
        } else {
            sql.append("WHERE " + wheres);
            // 执行方法，不需要传递conn参数
            querySet = factory.select(null,sql.toString());
            return querySet;
        }
        
        
    }

    /**
     * 经销商配件预测表(pt_bu_forcast)查询
     *
     * @param pPageManager 查询分页对象
     * @param pUser 当前登录user对象
     * @param pConditions sql条件(默认：1=1)
     * @return baseResultSet 结果集
     * @throws Exception
     */
    public BaseResultSet searchForecastByDealer(PageManager pPageManager, User pUser, String pConditions) throws Exception {

    	 String wheres = pConditions;
         // 按所属机构查询，并且按预测月份升序排列
         wheres += " AND PBF.ORG_ID=TMO.ORG_ID \n"
                 + " AND PBF.FORCAST_STATUS=" + DicConstant.PJYCZT_02 + " AND TMO.ORG_TYPE ="+DicConstant.ZZLB_09+"\n"
                 + " ORDER BY PBF.FORCAST_MONTH";
         pPageManager.setFilter(wheres);
        //定义返回结果集
        BaseResultSet baseResultSet = null;
        StringBuffer sql = new StringBuffer();
        sql.append("\n");
        sql.append(" SELECT \n");
        sql.append("     TMO.CODE,\n");
        sql.append("     TMO.ONAME,\n");
        sql.append("     PBF.FORCAST_ID,\n");
        sql.append("     PBF.FORCAST_MONTH,\n");
        sql.append("     PBF.ORG_ID\n");
        sql.append(" FROM \n");
        sql.append("     PT_BU_FORCAST PBF,TM_ORG TMO \n");
        //执行方法，不需要传递conn参数
        baseResultSet = factory.select(sql.toString(), pPageManager);
        baseResultSet.setFieldDic("FORCAST_STATUS", DicConstant.PJYCZT);
        return baseResultSet;
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
        sql.append("\n");
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

    /**
     * 配件预测明细表(pt_bu_forcast_dtl)添加
     *
     * @param pPageManager 查询分页对象
     * @param pUser 当前登录user对象
     * @param pConditions sql条件(默认：1=1)
     * @return QuerySet 结果集
     * @throws Exception
     */
    public boolean insertForecastDetail(User pUser, String pConditions) throws Exception {

        StringBuffer sql = new StringBuffer();
        sql.append("\n");
        sql.append("INSERT INTO PT_BU_FORCAST_DTL \n");
        sql.append("    SELECT f_getid(),\n");
        sql.append("        (SELECT FORCAST_ID \n");
        sql.append("            FROM PT_BU_FORCAST \n");
        sql.append("            WHERE FORCAST_MONTH = '" + pConditions+ "' \n");
        sql.append("            AND ORG_ID = " + pUser.getOrgId()+ ") as FORCAST_ID,\n");
        sql.append("        PART_ID,\n");
        sql.append("        PART_CODE, \n");
        sql.append("        PART_NAME, \n");
        sql.append("        COUNT \n");
        sql.append("    FROM \n");
        sql.append("        (SELECT PART_ID, PART_CODE, PART_NAME, SUM(COUNT) AS COUNT \n");
        sql.append("        FROM PT_BU_FORCAST_DTL A,PT_BU_FORCAST B,TM_ORG C \n");
        sql.append("        WHERE A.FORCAST_ID = B.FORCAST_ID AND B.FORCAST_ORG_ID = C.ORG_ID AND C.ORG_TYPE ="+DicConstant.ZZLB_09+"\n");
        sql.append("            AND B.FORCAST_MONTH = '" + pConditions+ "' \n");
        sql.append("        GROUP BY PART_ID, PART_CODE, PART_NAME \n");
        sql.append("        )");
        //执行方法，不需要传递conn参数
        return factory.exec(sql.toString());
    }

    /**
     * 查询服务站提报日(业务参数配置表:user_para_configure)
     *
     * @param pParakey 参数key
     * @return String[][] 结果集
     * @throws Exception
     */
    public String[][] searchByReportDay(String pParakey) throws Exception {

        StringBuffer sql = new StringBuffer();
        sql.append("\n");
        sql.append(" SELECT \n");
        sql.append("     PARAVALUE1, \n");
        sql.append("     PARAVALUE2 \n");
        sql.append(" FROM \n");
        sql.append("     USER_PARA_CONFIGURE \n");
        sql.append(" WHERE \n");
        sql.append("     PARAKEY='" + pParakey + "' \n");
        sql.append(" AND STATUS=" + DicConstant.YXBS_01 + "\n");
        //执行方法，不需要传递conn参数
        return factory.select(sql.toString());
    }

    /**
     * 配件预测表(pt_bu_forcast)修改
     *
     * @param ptBuForcastVO 配件预测实体
     * @param pUser 当前登录user对象
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean updateForecast(PtBuForcastVO ptBuForcastVO, User pUser) throws Exception {

        String where = " AND ORG_ID=" + pUser.getOrgId() + "\n";
        // 修改配件预测表sql
        String forecastString = "UPDATE \n"
                               + "PT_BU_FORCAST \n"
                               + "SET \n"
                               + "FORCAST_STATUS='" + ptBuForcastVO.getForcastStatus()+ "',\n"
                               + "COMPANY_ID='" + pUser.getCompanyId()+ "',\n"
                               + "ORG_ID='" + pUser.getOrgId()+ "',\n"
                               + "UPDATE_USER='" + pUser.getAccount()+ "',\n"
                               + "UPDATE_TIME=sysdate \n"
                               + "WHERE \n"
                               + "FORCAST_MONTH='" + ptBuForcastVO.getForcastMonth() + "'\n"
                               + where;
        return factory.update(forecastString, null);
    }

    /**
     * 配件预测表(pt_bu_forcast)删除
     * @param ptBuForcastVO 配件预测实体
     * @param pUser 当前登录user对象
     * @return true:成功;false:失败;
     * @throws Exception 
     */
    public boolean deleteForecast(PtBuForcastVO ptBuForcastVO, User pUser) throws Exception {

        // 按所属公司,所属机构删除
        String wheres = " AND ORG_ID = " + pUser.getOrgId() + "\n";
        // 删除配件预测表sql
        String stockString = "DELETE \n"
                           + "FROM \n"
                           + "    PT_BU_FORCAST \n"
                           + "WHERE \n"
                           + "    FORCAST_ID='" + ptBuForcastVO.getForcastId() + "'"
                           + wheres;
        return factory.delete(stockString, null);
    }

    /**
     * 配件预测明细表(pt_bu_forcast_dtl)删除
     * @param ptBuForcastVO 配件预测明细实体
     * @param pWhere 条件
     * @return true:成功;false:失败;
     * @throws Exception 
     */
    public boolean deleteForecastDetail(PtBuForcastDtlVO ptBuForcastDtlVO, String pWhere) throws Exception {

        // 删除配件预测明细表sql
        String stockString = "DELETE \n"
                           + "FROM \n"
                           + "    PT_BU_FORCAST_DTL \n"
                           + "WHERE \n"
                           + "    FORCAST_ID='" + ptBuForcastDtlVO.getForcastId() + "'"
                           + pWhere;
        return factory.delete(stockString, null);
    }
}
