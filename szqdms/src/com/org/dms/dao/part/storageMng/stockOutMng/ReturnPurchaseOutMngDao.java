package com.org.dms.dao.part.storageMng.stockOutMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuReturnApplyVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 退件出库Dao
 *
 * 配送中心或车厂退件出库
 * @author zhengyao
 * @date 2014-08-02
 * @version 1.0
 */
public class ReturnPurchaseOutMngDao extends BaseDAO {

    /**
     * 定义instance
     *
     * @param pActionContext
     * @return returnPurchaseCheckMngDao 退件出库Dao
     */
    public static final ReturnPurchaseOutMngDao getInstance(ActionContext pActionContext) {

        ReturnPurchaseOutMngDao returnPurchaseCheckMngDao = new ReturnPurchaseOutMngDao();
        pActionContext.setDBFactory(returnPurchaseCheckMngDao.factory);

        return returnPurchaseCheckMngDao;
    }

    /**
     * 退件申请表(pt_bu_return_apply)修改
     *
     * @param pPtBuReturnApplyVO 退件申请实体
     * @param pUser 当前登录user对象
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean updateReturnPurchaseApply(PtBuReturnApplyVO pPtBuReturnApplyVO, User pUser) throws Exception {

        // 修改配件预测表sql
        String sql = " UPDATE \n"
                   + "     PT_BU_RETURN_APPLY \n"
                   + " SET \n"
                   + "     APPLY_SATUS='" + pPtBuReturnApplyVO.getApplySatus() + "',\n"
                   + "     UPDATE_USER='" + pUser.getAccount()+ "',\n"
                   + "     UPDATE_TIME=sysdate, \n"
                   + "     OUT_STOCK_DATE=sysdate \n"
                   + " WHERE \n"
                   + "     RETURN_ID='" + pPtBuReturnApplyVO.getReturnId() + "'\n";

        return factory.update(sql, null);
    }

    /**
     * 配件服务站库存(pt_bu_dealer_stock)修改
     *
     * @param pPartIdOutCount 配件主键,出库数量,退件数量,退件订单号
     * @param pUser 当前登录user对象
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean updateDealerStock(String pPartIdOutCount,User pUser) throws Exception {

        // 少出数量(应出-实出)
        String availableAmount = pPartIdOutCount.split(",")[2] + "-" + pPartIdOutCount.split(",")[1];

        // 修配件服务站库存表sql
        String sql = " UPDATE \n"
                   + "     PT_BU_DEALER_STOCK \n"
                   + " SET AMOUNT = \n"
                   + "         (SELECT \n"
                   + "              AMOUNT-" + pPartIdOutCount.split(",")[1] + "\n"
                   + "          FROM \n"
                   + "              PT_BU_DEALER_STOCK \n"
                   + "          WHERE \n"
                   + "             ORG_ID='" + pUser.getOrgId() + "'\n"
                   + "          AND PART_ID='" + pPartIdOutCount.split(",")[0] + "'),\n"
                   + "     OCCUPY_AMOUNT = \n"
                   + "         (SELECT \n"
                   + "              OCCUPY_AMOUNT-" + pPartIdOutCount.split(",")[2] + "\n"
                   + "          FROM \n"
                   + "              PT_BU_DEALER_STOCK \n"
                   + "          WHERE \n"
                   + "              ORG_ID='" + pUser.getOrgId() + "'\n"
                   + "          AND PART_ID='" + pPartIdOutCount.split(",")[0] + "'),\n"
                   + "     AVAILABLE_AMOUNT = \n"
                   + "         (SELECT \n"
                   + "              AVAILABLE_AMOUNT+(" + availableAmount + ")\n"
                   + "          FROM \n"
                   + "              PT_BU_DEALER_STOCK \n"
                   + "          WHERE \n"
                   + "              ORG_ID='" + pUser.getOrgId() + "'\n"
                   + "          AND PART_ID='" + pPartIdOutCount.split(",")[0] + "')\n"
                   + " WHERE \n"
                   + "     STORAGE_STATUS = " + DicConstant.KCZT_01 + "\n"
                   + " AND ORG_ID='" + pUser.getOrgId() + "'\n"
                   + " AND PART_ID='" + pPartIdOutCount.split(",")[0] + "'";

        return factory.update(sql, null);
    }

    /**
     * 配件库存服务站异动(pt_bu_dealer_stock_change)修改
     *
     * @param pPartIdOutCount 配件主键,出库数量,退件数量,退件订单号
     * @param pUser 当前登录user对象
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean insertDealerStockChange(String pPartIdOutCount, User pUser) throws Exception {

        StringBuilder sql= new StringBuilder();
        sql.append("INSERT INTO PT_BU_DEALER_STOCK_CHANGE\n" );
        sql.append("  (SELECT F_GETID(),\n" );
        sql.append("          STOCK_ID,\n" );
        sql.append("          ORG_ID,\n" );
        sql.append("          ORG_CODE,\n" );
        sql.append("          ORG_NAME,\n" );
        sql.append("          PART_ID,\n" );
        sql.append("          PART_CODE,\n" );
        sql.append("          PART_NAME,\n" );
        sql.append("          " + pPartIdOutCount.split(",")[1] + ",\n" );
        sql.append("          sysdate,\n" );
        sql.append("          '" + DicConstant.CZLX_02 + "',\n" );
        sql.append("          '',\n" );
        sql.append("          '" + pUser.getAccount() + "',\n" );
        sql.append("          sysdate,\n" );
        sql.append("          '',\n" );
        sql.append("          '',\n" );
        sql.append("          '" + DicConstant.YXBS_01 + "',\n" );
        sql.append("          SUPPLIER_ID,\n" );
        sql.append("          SUPPLIER_CODE,\n" );
        sql.append("          SUPPLIER_NAME,\n" );
        sql.append("          '" + DicConstant.QDCRKLX_05 + "',\n" );
        sql.append("          '',\n" );
        sql.append("          '" + pPartIdOutCount.split(",")[3] + "'\n" );
        sql.append("     FROM PT_BU_DEALER_STOCK\n" );
        sql.append("    WHERE ORG_ID = '" + pUser.getOrgId() + "'\n" );
        sql.append("      AND PART_ID = '" + pPartIdOutCount.split(",")[0] + "')");

        return factory.exec(sql.toString());
    }

    /**
     * 退件申请表(pt_bu_return_apply)查询
     *
     * @param pPageManager 查询分页对象
     * @param pUser 当前登录user对象
     * @param pConditions sql条件(默认：1=1)
     * @return baseResultSet 结果集
     * @throws Exception
     */
    public BaseResultSet searchSaleOrder(PageManager pPageManager, User pUser, String pConditions) throws Exception {

        String wheres = pConditions;
        // 查询审核通过
        wheres += " AND APPLY_SATUS ='" + DicConstant.TJSQDZT_03+ "' \n"
                + " AND APPLY_ORG_ID = " + pUser.getOrgId() + "\n"
                + " ORDER BY RETURN_NO";
        pPageManager.setFilter(wheres);
        //定义返回结果集
        BaseResultSet baseResultSet = null;
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT \n");
        sql.append("     RETURN_ID,\n");
        sql.append("     RETURN_NO,\n");
        sql.append("     RECEIVE_ORG_ID,\n");
        sql.append("     RECEIVE_ORG_CODE,\n");
        sql.append("     RECEIVE_ORG_NAME,\n");
        sql.append("     SOURCE_ORDER_NO,\n");
        sql.append("     SOURCE_ORDER_ID,\n");
        sql.append("     APPLY_ORG_CODE,\n");
        sql.append("     APPLY_ORG_NAME,\n");
        sql.append("     APPLY_SATUS, \n");
        sql.append("     APPLY_DATE, \n");
        sql.append("     CHECK_DATE, \n");
        sql.append("     CHECK_USER, \n");
        sql.append("     RETURN_COUNT, \n");
        sql.append("     RETURN_AMOUNT \n");
        sql.append(" FROM \n");
        sql.append("     PT_BU_RETURN_APPLY \n");
        // 执行方法，不需要传递conn参数
        baseResultSet = factory.select(sql.toString(), pPageManager);
        baseResultSet.setFieldDic("APPLY_SATUS", DicConstant.TJSQDZT);
        // 申请日期绑定
        baseResultSet.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
        // 审核日期绑定
        baseResultSet.setFieldDateFormat("CHECK_DATE", "yyyy-MM-dd");
        // 审核员
        baseResultSet.setFieldUserID("CHECK_USER");

        return baseResultSet;
    }

    /**
     * 申请退件明细表(pt_bu_return_apply_dtl)查询
     *
     * @param pPageManager 查询分页对象
     * @param pUser 当前登录user对象
     * @param pConditions sql条件(默认：1=1)
     * @return QuerySet 结果集
     * @throws Exception
     */
    public BaseResultSet searchReturnPurchaseApplyDtl(PageManager pPageManager, String pConditions) throws Exception {

        String where = pConditions + " AND A.PART_ID=B.PART_ID";
        pPageManager.setFilter(where);
        //定义返回结果集
        BaseResultSet baseResultSet = null;
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT \n");
        sql.append("     A.DTL_ID,\n");
        sql.append("     A.RETURN_ID,\n");
        sql.append("     A.PART_ID,\n");
        sql.append("     A.PART_CODE,\n");
        sql.append("     A.PART_NAME,\n");
        sql.append("     A.SUPPLIER_ID,\n");
        sql.append("     A.SUPPLIER_CODE,\n");
        sql.append("     A.SUPPLIER_NAME,\n");
        sql.append("     A.UNIT,\n");
        sql.append("     A.RETURN_COUNT,\n");
        sql.append("     A.SALE_PRICE,\n");
        sql.append("     A.AMOUNT,\n");
        sql.append("     B.MIN_PACK,\n");
        sql.append("     B.MIN_UNIT\n");
        sql.append(" FROM \n");
        sql.append("     PT_BU_RETURN_APPLY_DTL A,PT_BA_INFO B\n");
        //执行方法，不需要传递conn参数
        baseResultSet = factory.select(sql.toString(),pPageManager);

        return baseResultSet;
    }
}