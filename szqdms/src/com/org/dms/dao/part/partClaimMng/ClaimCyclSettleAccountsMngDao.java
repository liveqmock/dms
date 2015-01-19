package com.org.dms.dao.part.partClaimMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuClaimApplyVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 配件三包结算Dao
 *
 * 配件三包结算的增删改查
 * @author zhengyao
 * @date 2014-08-15
 * @version 1.0
 */
public class ClaimCyclSettleAccountsMngDao extends BaseDAO {

    /**
     * 定义instance
     *
     * @param pActionContext
     * @return ClaimCyclSetMngDao 配件三包结算Dao
     */
    public static final ClaimCyclSettleAccountsMngDao getInstance(ActionContext pActionContext) {

        ClaimCyclSettleAccountsMngDao claimCyclSettleAccountsMngDao = new ClaimCyclSettleAccountsMngDao();
        pActionContext.setDBFactory(claimCyclSettleAccountsMngDao.factory);
        return claimCyclSettleAccountsMngDao;
    }

    /**
     * 配件三包申请表(PT_BU_CLAIM_APPLY)修改
     * @param pPtBuClaimApplyVO 配件三包申请实体
     * @return true:成功;false:失败;
     * @throws Exception 
     */
    public boolean updateClaimCyclApply(PtBuClaimApplyVO pPtBuClaimApplyVO,User pUser) throws Exception {

        // 修改配件三包申请表sql
        String sql = " UPDATE \n"
                   + "     PT_BU_CLAIM_APPLY \n"
                   + " SET \n"
                   + "     SETTLE_ACCOUNT='" + DicConstant.PJSBSPJSZT_02 + "',"
                   + "     SETTLE_ACCOUNT_DATE=sysdate,"
//                   + "     APPLY_STATUS='" + pPtBuClaimApplyVO.getApplyStatus() + "',"
                   + "     UPDATE_USER='" + pUser.getAccount() + "',"
                   + "     UPDATE_TIME=sysdate"
                   + " WHERE \n"
                   + "     APPLY_ID='" + pPtBuClaimApplyVO.getApplyId() + "'";
        return factory.delete(sql, null);
    }

    /**
     * 配件三包申请表(PT_BU_CLAIM_APPLY)查询
     *
     * @param pPageManager 查询分页对象
     * @param pUser 当前登录user对象
     * @param pConditions sql条件(默认：1=1)
     * @return baseResultSet 结果集
     * @throws Exception
     */
    public BaseResultSet searchClaimCyclApply(PageManager pPageManager, User pUser, String pConditions) throws Exception {

        String wheres = pConditions;
        // 按所属机构查询，并且按配件代码升序排列
        wheres += " AND A.APPLY_ID=B.APPLY_ID AND B.CHECK_RESULT='" + DicConstant.PJSBSPZT_05+ "'"
                + " AND B.CHECK_DATE=(SELECT MAX(CHECK_DATE) FROM PT_BU_CLAIM_APPLY_LOG)"
                + " AND APPLY_STATUS=" + DicConstant.PJSBSPZT_05 +"\n"
                + " AND SETTLE_ACCOUNT<>" + DicConstant.PJSBSPJSZT_02 +"\n"
                + " AND STATUS = " + DicConstant.YXBS_01 + "\n"
                + " ORDER BY PART_CODE";
        pPageManager.setFilter(wheres);
        //定义返回结果集
        BaseResultSet baseResultSet = null;
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT \n");
        sql.append("     A.ORG_CODE,\n");
        sql.append("     A.ORG_NAME,\n");
        sql.append("     A.APPLY_ID,\n");
        sql.append("     A.APPLY_NO,\n");
        sql.append("     A.IN_ORDER_NO,\n");
        sql.append("     A.OUT_ORDER_NO,\n");
        sql.append("     A.OUT_DATE,\n");
        sql.append("     A.PHONE,\n");
        sql.append("     A.UNIT,\n");
        sql.append("     A.SALE_PRICE,\n");
        sql.append("     A.AMOUNT,\n");
        sql.append("     A.OUT_COUNT,\n");
        sql.append("     A.SUPPLIER_ID,\n");
        sql.append("     A.SUPPLIER_CODE,\n");
        sql.append("     A.SUPPLIER_NAME,\n");
        sql.append("     A.CUSTOMER_NAME,\n");
        sql.append("     A.PART_ID,\n");
        sql.append("     A.PART_CODE,\n");
        sql.append("     A.PART_NAME,\n");
        sql.append("     A.CLAIM_COUNT,\n");
        sql.append("     A.APPLY_DATE,\n");
        sql.append("     A.APPLY_STATUS,\n");
        sql.append("     A.FAULT_CONDITONS,\n");
        sql.append("     B.CHECK_DATE,\n");
        sql.append("     B.CHECK_USER,\n");
        sql.append("     A.REMARKS\n");
        sql.append(" FROM \n");
        sql.append("     PT_BU_CLAIM_APPLY A,PT_BU_CLAIM_APPLY_LOG B \n");
        // 执行方法，不需要传递conn参数
        baseResultSet = factory.select(sql.toString(), pPageManager);
        baseResultSet.setFieldDic("APPLY_STATUS", DicConstant.PJSBSPZT);
        baseResultSet.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
        baseResultSet.setFieldDateFormat("CHECK_DATE", "yyyy-MM-dd");
        baseResultSet.setFieldDateFormat("OUT_DATE", "yyyy-MM-dd");
        baseResultSet.setFieldDic("UNIT", DicConstant.JLDW);
        return baseResultSet;
    }
}