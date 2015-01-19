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
 * 预测上报Dao
 *
 * 配件预测的增删改查
 * @author zhengyao
 * @date 2014-07-15
 * @version 1.0
 */
public class ForecastMngDao extends BaseDAO {

    /**
     * 定义instance
     *
     * @param pActionContext
     * @return warehouseMngDao 预测上报Dao
     */
    public static final ForecastMngDao getInstance(ActionContext pActionContext) {

        ForecastMngDao forecastMngDao = new ForecastMngDao();
        pActionContext.setDBFactory(forecastMngDao.factory);
        return forecastMngDao;
    }

    /**
     * 导入验证(配件数量)
     *
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet partCountCheck(User pUser) throws Exception {

        StringBuilder sql= new StringBuilder();
        sql.append("SELECT \n" );
        sql.append("    TMP_ID,\n" );
        sql.append("    PART_CODE,\n" );
        sql.append("    PART_NAME,\n" );
        sql.append("    PART_COUNT,\n" );
        sql.append("    REMARKS,\n" );
        sql.append("    USER_ACCOUNT\n" );
        sql.append("    FROM PT_BU_FORCAST_DTL_TMP\n" );
        sql.append(" WHERE USER_ACCOUNT='" + pUser.getAccount() + "'\n" );

        return factory.select(null, sql.toString());
    }

    /**
     * 导入验证(配件是否存在)
     *
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet partCheck(User pUser) throws Exception {

        StringBuilder sql= new StringBuilder();
        sql.append("SELECT \n" );
        sql.append("    TMP_ID,\n" );
        sql.append("    PART_CODE,\n" );
        sql.append("    PART_NAME,\n" );
        sql.append("    PART_COUNT,\n" );
        sql.append("    REMARKS,\n" );
        sql.append("    USER_ACCOUNT\n" );
        sql.append("    FROM PT_BU_FORCAST_DTL_TMP\n" );
        sql.append(" WHERE PART_CODE NOT IN (SELECT PART_CODE \n" );
        sql.append("                       FROM PT_BA_INFO)\n" );
        sql.append(" AND USER_ACCOUNT='" + pUser.getAccount() + "'\n" );

        return factory.select(null, sql.toString());
    }

    /**
     * 导入验证(配件重复)
     *
     * @param pForecastId 预测主键
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet partRepeatCheck(String pForecastId,User pUser) throws Exception {

        StringBuilder sql= new StringBuilder();
        sql.append("SELECT \n" );
        sql.append("    TMP_ID,\n" );
        sql.append("    PART_CODE,\n" );
        sql.append("    PART_NAME,\n" );
        sql.append("    PART_COUNT,\n" );
        sql.append("    REMARKS,\n" );
        sql.append("    USER_ACCOUNT\n" );
        sql.append("    FROM PT_BU_FORCAST_DTL_TMP\n" );
        sql.append(" WHERE PART_CODE IN (SELECT PART_CODE\n" );
        sql.append("                       FROM PT_BU_FORCAST_DTL\n" );
        sql.append("                      WHERE FORCAST_ID = '" + pForecastId + "')");
        sql.append(" AND USER_ACCOUNT='" + pUser.getAccount() + "'\n" );

        return factory.select(null, sql.toString());
    }

    /**
     * 预测配件明细临时表(pt_bu_forcast_dtl_tmp)查询
     *
     * @param pPageManager 查询分页对象
     * @param pConditions sql条件(默认：1=1)
     * @return BaseResultSet 结果集
     * @throws Exception
     */
    public BaseResultSet searchImport(PageManager pPageManager, String pConditions) throws Exception {

        pPageManager.setFilter(pConditions);
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT \n");
        sql.append("     PART_CODE, \n");
        sql.append("     PART_COUNT \n");
        sql.append(" FROM \n");
        sql.append("     PT_BU_FORCAST_DTL_TMP \n");
        //执行方法，不需要传递conn参数
        return factory.select(sql.toString(),pPageManager);
    }

    /**
     * 配件预测明细临时表(导出)
     *
     * @pConditions 查询条件
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet expData(String pConditions,User user) throws Exception {

    	String wheres = " WHERE TMP_NO IN ("+ pConditions + ") AND USER_ACCOUNT='"+user.getAccount()+"'\n";
    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT PART_CODE, PART_COUNT\n" );
    	sql.append("  FROM PT_BU_FORCAST_DTL_TMP");
        //执行方法，不需要传递conn参数
        return factory.select(null, sql.toString()+wheres);
    }

    /**
     * 配件预测明细表(pt_bu_forcast_dtl)导入更新
     *
     * @param pForcastId 预测ID
     * @param pUser
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean updateForecastDtl(String pForcastId,User pUser,String tmpNo) throws Exception {

        // 修改配件预测明细表sql
        String stockString = " UPDATE \n"
                           + "     PT_BU_FORCAST_DTL A \n"
                           + " SET COUNT="
                           + "     (SELECT \n"
                           + "         B.PART_COUNT \n"
                           + "      FROM \n"
                           + "         PT_BU_FORCAST_DTL_TMP B \n"
                           + "      WHERE \n"
                           + "         B.USER_ACCOUNT='" + pUser.getAccount() + "' "+tmpNo+"\n"
                           + "      AND A.PART_CODE=B.PART_CODE) \n"
                           + " WHERE \n"
                           + "    A.FORCAST_ID='" + pForcastId + "'\n"
                           + " AND A.PART_CODE IN (SELECT \n"
                           + "                         B.PART_CODE \n"
                           + "                     FROM \n"
                           + "                         PT_BU_FORCAST_DTL_TMP B\n"
                           + "                     WHERE B.USER_ACCOUNT = '" + pUser.getAccount() + "'"+tmpNo+")";

        // 新增配件预测明细表sql
        StringBuffer sql = new StringBuffer();
        sql.append(" INSERT INTO PT_BU_FORCAST_DTL \n");
        sql.append("     (SELECT \n");
        sql.append("         F_GETID()," + pForcastId + ",B.PART_ID,\n");
        sql.append("         A.PART_CODE,\n");
        sql.append("         B.PART_NAME,\n");
        sql.append("         A.PART_COUNT \n");
        sql.append("     FROM \n");
        sql.append("         PT_BU_FORCAST_DTL_TMP A,PT_BA_INFO B\n");
        sql.append("     WHERE  \n");
        sql.append("         A.PART_CODE NOT IN(\n");
        sql.append("             SELECT \n");
        sql.append("                 PART_CODE \n");
        sql.append("             FROM \n");
        sql.append("                 PT_BU_FORCAST_DTL\n");
        sql.append("             WHERE \n");
        sql.append("                 FORCAST_ID=" + pForcastId + ")\n");
        sql.append("     AND A.PART_CODE=B.PART_CODE AND USER_ACCOUNT='" + pUser.getAccount() + "')");
        boolean insertFlag = factory.exec(sql.toString());
        boolean updateFlag =factory.update(stockString, null);
        return insertFlag&&updateFlag;
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
     * 配件预测表(pt_bu_forcast)修改
     *
     * @param ptBuForcastVO 配件预测实体
     * @param pUser 当前登录user对象
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean updateForecast(PtBuForcastVO ptBuForcastVO, User pUser) throws Exception {

        String where = " AND OEM_COMPANY_ID=" + pUser.getOemCompanyId() + "\n";
        // 修改配件预测表sql
        String forecastString = "UPDATE \n"
                              + "    PT_BU_FORCAST \n"
                              + "SET \n"
                              + "    FORCAST_STATUS='" + ptBuForcastVO.getForcastStatus()+ "',\n"
                              + "    COMPANY_ID='" + pUser.getCompanyId()+ "',\n"
                              + "    ORG_ID='" + pUser.getOrgId()+ "',\n"
                              + "    UPDATE_USER='" + pUser.getAccount()+ "',\n"
                              + "    UPDATE_TIME=sysdate \n"
                              + "WHERE \n"
                              + "    FORCAST_ID='" + ptBuForcastVO.getForcastId() + "'\n"
                              + where;
        return factory.update(forecastString, null);
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
                           + "    PT_BU_FORCAST_DTL \n"
                           + " SET \n"
                           + "    COUNT='" + ptBuForcastDtlVO.getCount() + "'"
                           + " WHERE \n"
                           + "    FORCAST_ID='" + ptBuForcastDtlVO.getForcastId() + "' \n"
                           + " AND PART_ID='" + ptBuForcastDtlVO.getPartId() + "'\n";
        return factory.update(stockString, null);
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
     * 配件预测表(pt_bu_forcast)查询
     *
     * @param ptBuForcastVO 配件预测实体
     * @param pUser 当前登录user对象
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet searchByForecastMonth(PtBuForcastVO ptBuForcastVO, User pUser) throws Exception {

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT \n");
        sql.append("     FORCAST_MONTH \n");
        sql.append(" FROM \n");
        sql.append("     PT_BU_FORCAST \n");
        sql.append(" WHERE \n");
        sql.append("     FORCAST_MONTH='" + ptBuForcastVO.getForcastMonth() + "' \n");
        sql.append(" AND ORG_ID = " + pUser.getOrgId() + "\n");
        //执行方法，不需要传递conn参数
        return factory.select(null,sql.toString());
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
    public QuerySet searchByForecastMonth(PtBuForcastDtlVO pPtBuForcastDtlVO) throws Exception {

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT \n");
        sql.append("     1 \n");
        sql.append(" FROM \n");
        sql.append("     PT_BU_FORCAST_DTL \n");
        sql.append(" WHERE \n");
        sql.append("     FORCAST_ID='" + pPtBuForcastDtlVO.getForcastId() + "' \n");
        sql.append(" AND PART_CODE ='" + pPtBuForcastDtlVO.getPartCode() + "'\n");
        //执行方法，不需要传递conn参数
        return factory.select(null,sql.toString());
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

    /**
     * 查询配件
     *
     * @param pageManager 查询分页对象
     * @param pUser 当前登录user对象
     * @param pConditions sql条件(默认：1=1)
     * @return baseResultSet 结果集
     * @throws Exception
     */
    public BaseResultSet searchPart(PageManager pageManager, User pUser, String pConditions,String pForcastMonth) throws Exception {

        //过滤掉当前促销活动已存在的配件
        String wheres = pConditions + " AND PBI.PART_STATUS='"+DicConstant.PJZT_01+"' AND PBI.PART_ID NOT IN (SELECT PART_ID FROM PT_BU_FORCAST_DTL WHERE "
                      + " FORCAST_ID IN( \n"
                      + " SELECT \n"
                      + "     FORCAST_ID \n"
                      + " FROM \n"
                      + "     PT_BU_FORCAST \n"
                      + " WHERE \n"
                      + "     FORCAST_MONTH='" + pForcastMonth + "' \n"
                      + " AND  ORG_ID='" + pUser.getOrgId() + "')) ORDER BY PBI.PART_CODE\n";
        pageManager.setFilter(wheres);
        //定义返回结果集
        BaseResultSet baseResultSet = null;
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT PBI.PART_ID,\n");
        sql.append("     PBI.PART_CODE,\n");
        sql.append("     PBI.PART_NAME\n");
        sql.append(" FROM PT_BA_INFO PBI\n");
        //执行方法，不需要传递conn参数
        baseResultSet = factory.select(sql.toString(), pageManager);
        return baseResultSet;
    }
}