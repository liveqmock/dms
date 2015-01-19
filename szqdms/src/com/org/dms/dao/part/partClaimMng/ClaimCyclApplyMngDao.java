package com.org.dms.dao.part.partClaimMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuClaimApplyLogVO;
import com.org.dms.vo.part.PtBuClaimApplyVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 配件三包申请Dao
 *
 * 配件三包申请的增删改查
 * @author zhengyao
 * @date 2014-08-11
 * @version 1.0
 */
public class ClaimCyclApplyMngDao extends BaseDAO {

    /**
     * 定义instance
     *
     * @param pActionContext
     * @return ClaimCyclSetMngDao 配件三包申请Dao
     */
    public static final ClaimCyclApplyMngDao getInstance(ActionContext pActionContext) {

        ClaimCyclApplyMngDao claimCyclApplyMngDao = new ClaimCyclApplyMngDao();
        pActionContext.setDBFactory(claimCyclApplyMngDao.factory);
        return claimCyclApplyMngDao;
    }

    /**
     * 配件三包申请表(PT_BU_CLAIM_APPLY)添加
     *
     * @param ptBuClaimApplyVO 配件三包期申请实体
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean insertClaimCyclApply(PtBuClaimApplyVO ptBuClaimApplyVO) throws Exception {

        return factory.insert(ptBuClaimApplyVO);
    }

    /**
     * 配件三包申请表(PT_BU_CLAIM_APPLY)添加
     *
     * @param ptBuClaimApplyVO 配件三包期申请实体
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean insertClaimCyclApplyLog(PtBuClaimApplyLogVO ptBuClaimApplyLogVO) throws Exception {

        return factory.insert(ptBuClaimApplyLogVO);
    }

    /**
     * 配件三包申请表(PT_BU_CLAIM_APPLY)修改
     *
     * @param pPtBuClaimApplyVO 配件三包申请实体
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean updateClaimCyclApply(PtBuClaimApplyVO pPtBuClaimApplyVO) throws Exception {

        return factory.update(pPtBuClaimApplyVO);
    }

    /**
     * 配件三包申请表(PT_BU_CLAIM_APPLY)删除
     * @param pPtBuClaimApplyVO 配件三包申请实体
     * @return true:成功;false:失败;
     * @throws Exception 
     */
    public boolean deleteClaimCyclApply(PtBuClaimApplyVO pPtBuClaimApplyVO) throws Exception {

        // 删除配件三包期设置表sql
        String sql = "DELETE \n"
                   + "FROM \n"
                   + "    PT_BU_CLAIM_APPLY \n"
                   + "WHERE \n"
                   + "    APPLY_ID='" + pPtBuClaimApplyVO.getApplyId() + "'";
        return factory.delete(sql, null);
    }

    /**
     * 提报(配件三包申请表(PT_BU_CLAIM_APPLY)修改)
     * @param pPtBuClaimApplyVO 配件三包申请实体
     * @return true:成功;false:失败;
     * @throws Exception 
     */
    public boolean applyClaimCyclApply(PtBuClaimApplyVO pPtBuClaimApplyVO,String applyStatus) throws Exception {

        // 删除配件三包期设置表sql
        String sql = " UPDATE \n"
                   + "     PT_BU_CLAIM_APPLY \n"
                   + " SET \n"
                   + "     APPLY_STATUS='" + applyStatus+ "'\n"
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
    public BaseResultSet searchClaimCyclApply(PageManager pPageManager, User pUser, String pConditions,boolean pFlag) throws Exception {

        String wheres = pConditions;
        // 按所属机构查询，并且按配件代码升序排列
//        wheres += " AND A.APPLY_ID=B.APPLY_ID AND B.CHECK_RESULT='" + DicConstant.PJSBSPZT_03+ "'"
//                + " AND B.CHECK_DATE=(SELECT MAX(CHECK_DATE) FROM PT_BU_CLAIM_APPLY_LOG)"
        wheres += " AND A.ORG_ID=" + pUser.getOrgId() + "\n"
                + " AND A.APPLY_STATUS IN ('" + DicConstant.PJSBSPZT_01 +"','" + DicConstant.PJSBSPZT_03 + "')\n"
                + " AND A.STATUS = " + DicConstant.YXBS_01 + "\n"
                + " ORDER BY A.PART_CODE";
        pPageManager.setFilter(wheres);
        //定义返回结果集
        BaseResultSet baseResultSet = null;
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT \n");
        sql.append("     A.APPLY_ID,\n");
        sql.append("     A.APPLY_NO,\n");
        sql.append("     A.IN_ORDER_NO,\n");
        sql.append("     A.IN_ORDER_ID,\n");
        sql.append("     A.OUT_ORDER_NO,\n");
        sql.append("     A.OUT_ORDER_ID,\n");
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
//        sql.append("     B.CHECK_DATE,\n");
//        sql.append("     B.CHECK_REMARK,\n");
        sql.append("     A.REMARKS\n");
        sql.append(" FROM \n");
        sql.append("     PT_BU_CLAIM_APPLY A\n");
        // 执行方法，不需要传递conn参数
        baseResultSet = factory.select(sql.toString(), pPageManager);
        baseResultSet.setFieldDic("APPLY_STATUS", DicConstant.PJSBSPZT);
        baseResultSet.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
        baseResultSet.setFieldDateFormat("OUT_DATE", "yyyy-MM-dd");
        baseResultSet.setFieldDateFormat("CHECK_DATE", "yyyy-MM-dd");
        baseResultSet.setFieldDic("UNIT", DicConstant.JLDW);
        return baseResultSet;
    }

    /**
     * 查询入库订单
     *
     * @param pageManager 查询分页对象
     * @param pUser 当前登录user对象
     * @param pConditions sql条件(默认：1=1)
     * @return baseResultSet 结果集
     * @throws Exception
     */
    public BaseResultSet searchInStockOrder(PageManager pageManager, User pUser, String pConditions) throws Exception {

        String wheres = pConditions + " AND ORG_ID = '" + pUser.getOrgId() + "'";
        pageManager.setFilter(wheres);
        //定义返回结果集
        BaseResultSet baseResultSet = null;
        StringBuilder sql= new StringBuilder();
        sql.append(" SELECT \n" );
        sql.append("     ORDER_ID,\n" );
        sql.append("     ORDER_NO,\n" );
        sql.append("     ORDER_TYPE\n" );
        sql.append(" FROM \n" );
        sql.append("     PT_BU_SALE_ORDER\n" );
        //执行方法，不需要传递conn参数
        baseResultSet = factory.select(sql.toString(), pageManager);
        baseResultSet.setFieldDic("ORDER_TYPE", DicConstant.DDLX);
        return baseResultSet;
    }

    /**
     * 查询出库订单
     *
     * @param pageManager 查询分页对象
     * @param pUser 当前登录user对象
     * @param pConditions sql条件(默认：1=1)
     * @return baseResultSet 结果集
     * @throws Exception
     */
    public BaseResultSet searchOutStockOrder(PageManager pageManager, User pUser, String pConditions) throws Exception {

        String wheres = pConditions + " AND ORG_ID = '" + pUser.getOrgId() + "'"
                      + " AND SALE_STATUS = '" + DicConstant.SXDZT_02 + "'";
        pageManager.setFilter(wheres);
        //定义返回结果集
        BaseResultSet baseResultSet = null;
        StringBuilder sql= new StringBuilder();
        sql.append(" SELECT \n" );
        sql.append("     SALE_ID,\n" );
        sql.append("     SALE_NO,\n" );
        sql.append("     SALE_DATE,\n" );
        sql.append("     LINK_PHONE,\n" );
        sql.append("     CUSTOMER_NAME\n" );
        sql.append(" FROM \n" );
        sql.append("     PT_BU_REAL_SALE\n" );
        //执行方法，不需要传递conn参数
        baseResultSet = factory.select(sql.toString(), pageManager);
        baseResultSet.setFieldDateFormat("SALE_DATE", "yyyy-MM-dd");
        return baseResultSet;
    }
}