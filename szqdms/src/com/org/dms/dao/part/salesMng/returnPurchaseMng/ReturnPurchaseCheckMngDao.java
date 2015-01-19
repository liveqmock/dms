package com.org.dms.dao.part.salesMng.returnPurchaseMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuReturnApplyVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 退件审核Dao
 *
 * 配送中心审核
 * @author zhengyao
 * @date 2014-08-02
 * @version 1.0
 */
public class ReturnPurchaseCheckMngDao extends BaseDAO {

    /**
     * 定义instance
     *
     * @param pActionContext
     * @return returnPurchaseCheckMngDao 退件审核Dao
     */
    public static final ReturnPurchaseCheckMngDao getInstance(ActionContext pActionContext) {

        ReturnPurchaseCheckMngDao returnPurchaseCheckMngDao = new ReturnPurchaseCheckMngDao();
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
                   + "     CHECK_REMARKS='" + pPtBuReturnApplyVO.getCheckRemarks() + "',\n"
                   + "     CHECK_USER='" + pUser.getAccount() + "',\n"
                   + "     CHECK_DATE=sysdate,\n"
                   + "     APPLY_SATUS='" + pPtBuReturnApplyVO.getApplySatus() + "',\n"
                   + "     UPDATE_USER='" + pUser.getAccount()+ "',\n"
                   + "     UPDATE_TIME=sysdate \n"
                   + " WHERE \n"
                   + "     RETURN_ID='" + pPtBuReturnApplyVO.getReturnId() + "'\n";

        return factory.update(sql, null);
    }

    /**
     * 配件服务站库存(pt_bu_dealer_stock)修改
     *
     * @param pPtBuReturnApplyVO 退件申请实体
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean updateDealerStock(PtBuReturnApplyVO pPtBuReturnApplyVO) throws Exception {

        // 修配件服务站库存表sql
        StringBuilder sql= new StringBuilder();
        sql.append("UPDATE PT_BU_DEALER_STOCK A\n" );
        sql.append("   SET (OCCUPY_AMOUNT, AVAILABLE_AMOUNT) =\n" );
        sql.append("       (SELECT B.OCCUPY_AMOUNT - C.RETURN_COUNT,\n" );
        sql.append("               B.AVAILABLE_AMOUNT + C.RETURN_COUNT\n" );
        sql.append("          FROM PT_BU_DEALER_STOCK     B,\n" );
        sql.append("               PT_BU_RETURN_APPLY_DTL C,\n" );
        sql.append("               PT_BU_RETURN_APPLY     D\n" );
        sql.append("         WHERE B.PART_ID = C.PART_ID\n" );
        sql.append("           AND C.RETURN_ID = D.RETURN_ID\n" );
        sql.append("           AND B.ORG_ID = D.APPLY_ORG_ID\n" );
        sql.append("           AND C.RETURN_ID = '" + pPtBuReturnApplyVO.getReturnId() + "'\n" );
        sql.append("           AND A.PART_ID = C.PART_ID)\n" );
        sql.append(" WHERE ORG_ID = (SELECT APPLY_ORG_ID\n" );
        sql.append("                   FROM PT_BU_RETURN_APPLY\n" );
        sql.append("                  WHERE RETURN_ID = '" + pPtBuReturnApplyVO.getReturnId() + "')");
        sql.append("       AND EXISTS (SELECT 1 \n" );
        sql.append("          FROM PT_BU_DEALER_STOCK     B,\n" );
        sql.append("               PT_BU_RETURN_APPLY_DTL C,\n" );
        sql.append("               PT_BU_RETURN_APPLY     D\n" );
        sql.append("         WHERE B.PART_ID = C.PART_ID\n" );
        sql.append("           AND C.RETURN_ID = D.RETURN_ID\n" );
        sql.append("           AND B.ORG_ID = D.APPLY_ORG_ID\n" );
        sql.append("           AND C.RETURN_ID = '" + pPtBuReturnApplyVO.getReturnId() + "'\n" );
        sql.append("           AND A.PART_ID = C.PART_ID)\n" );

        return factory.update(sql.toString(), null);
    }

    /**
     * 退件申请表(pt_bu_return_apply)查询(车厂)
     *
     * @param pPageManager 查询分页对象
     * @param pUser 当前登录user对象
     * @param pConditions sql条件(默认：1=1)
     * @return baseResultSet 结果集
     * @throws Exception
     */
    public BaseResultSet searchReturnPurchaseApplyCar(PageManager pPageManager, User pUser, String pConditions) throws Exception {

        String wheres = pConditions;
        // 查询已申请
        wheres += " AND APPLY_SATUS ='" + DicConstant.TJSQDZT_02+ "' \n"
                + " AND RECEIVE_ORG_ID IN (SELECT WAREHOUSE_ID FROM PT_BA_WAREHOUSE)\n"
                + " AND ORG_ID IN (SELECT ORG_ID FROM TM_ORG WHERE PID IN (SELECT ORG_ID FROM PT_BA_ORDER_CHECK WHERE USER_ACCOUNT='"+pUser.getAccount()+"'))"
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
        sql.append("     APPLY_DATE \n");
        sql.append(" FROM \n");
        sql.append("     PT_BU_RETURN_APPLY \n");
        // 执行方法，不需要传递conn参数
        baseResultSet = factory.select(sql.toString(), pPageManager);
        baseResultSet.setFieldDic("APPLY_SATUS", DicConstant.TJSQDZT);
        baseResultSet.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");

        return baseResultSet;
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
    public BaseResultSet searchReturnPurchaseApply(PageManager pPageManager, User pUser, String pConditions) throws Exception {

        String wheres = pConditions;
        // 查询已申请
        wheres += " AND APPLY_SATUS ='" + DicConstant.TJSQDZT_02+ "' \n"
                + " AND RECEIVE_ORG_ID = " + pUser.getOrgId() + "\n"
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
        sql.append("     APPLY_DATE \n");
        sql.append(" FROM \n");
        sql.append("     PT_BU_RETURN_APPLY \n");
        // 执行方法，不需要传递conn参数
        baseResultSet = factory.select(sql.toString(), pPageManager);
        baseResultSet.setFieldDic("APPLY_SATUS", DicConstant.TJSQDZT);
        baseResultSet.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");

        return baseResultSet;
    }

    /**
     * 退件申请明细表(pt_bu_return_apply_dtl)查询
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