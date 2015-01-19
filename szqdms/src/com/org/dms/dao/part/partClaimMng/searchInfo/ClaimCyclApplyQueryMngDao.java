package com.org.dms.dao.part.partClaimMng.searchInfo;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuClaimApplyLogVO;
import com.org.dms.vo.part.PtBuClaimApplyVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
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
public class ClaimCyclApplyQueryMngDao extends BaseDAO {

    /**
     * 定义instance
     *
     * @param pActionContext
     * @return ClaimCyclSetMngDao 配件三包申请Dao
     */
    public static final ClaimCyclApplyQueryMngDao getInstance(ActionContext pActionContext) {

        ClaimCyclApplyQueryMngDao claimCyclApplyMngDao = new ClaimCyclApplyQueryMngDao();
        pActionContext.setDBFactory(claimCyclApplyMngDao.factory);
        return claimCyclApplyMngDao;
    }

    /**
     * 配件三包索赔查询(导出)
     *
     * @pConditions 查询条件
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet download(String pConditions,User user) throws Exception {

        //执行方法，不需要传递conn参数
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT NVL(A.ORG_CODE,'') ORG_CODE,\n" );
    	sql.append("       NVL(A.ORG_NAME,'') ORG_NAME,\n" );
    	sql.append("       NVL(A.APPLY_ID,'') APPLY_ID,\n" );
    	sql.append("       NVL(A.APPLY_NO,'') APPLY_NO,\n" );
    	sql.append("       NVL(A.IN_ORDER_NO,'') IN_ORDER_NO,\n" );
    	sql.append("       NVL(A.IN_ORDER_ID,'') IN_ORDER_ID,\n" );
    	sql.append("       NVL(A.OUT_ORDER_NO,'') OUT_ORDER_NO,\n" );
    	sql.append("       NVL(A.OUT_ORDER_ID,'')OUT_ORDER_ID,\n" );
    	sql.append("       NVL(TO_CHAR(A.OUT_DATE, 'YYYY-MM-DD HH24:MI:SS'),'') OUT_DATE,\n" );
    	sql.append("       NVL(A.PHONE,'') PHONE,\n" );
    	sql.append("       NVL(H.DIC_VALUE,'') UNIT,\n" );
    	sql.append("       NVL(A.SALE_PRICE,'') SALE_PRICE,\n" );
    	sql.append("       NVL(A.AMOUNT,'') AMOUNT,\n" );
    	sql.append("       NVL(A.OUT_COUNT,'') OUT_COUNT,\n" );
    	sql.append("       NVL(A.SUPPLIER_ID,'') SUPPLIER_ID,\n" );
    	sql.append("       NVL(A.SUPPLIER_CODE,'') SUPPLIER_CODE,\n" );
    	sql.append("       NVL(A.SUPPLIER_NAME,'') SUPPLIER_NAME,\n" );
    	sql.append("       NVL(A.CUSTOMER_NAME,'') CUSTOMER_NAME,\n" );
    	sql.append("       NVL(A.PART_ID,'') PART_ID,\n" );
    	sql.append("       NVL(A.PART_CODE,'') PART_CODE,\n" );
    	sql.append("       NVL(A.PART_NAME,'') PART_NAME,\n" );
    	sql.append("       NVL(A.CLAIM_COUNT,'') CLAIM_COUNT,\n" );
    	sql.append("       NVL(TO_CHAR(A.APPLY_DATE, 'YYYY-MM-DD HH24:MI:SS'),'') APPLY_DATE,\n" );
    	sql.append("       NVL(E.DIC_VALUE,'') APPLY_STATUS,\n" );
    	sql.append("       NVL(A.FAULT_CONDITONS,'') FAULT_CONDITONS,\n" );
    	sql.append("       NVL(A.LINK_MAN,'') LINK_MAN,\n" );
    	sql.append("       NVL(A.REMARKS,'') REMARKS,\n" );
    	sql.append("       NVL(A.SOURCE_IN_NO,'') SOURCE_IN_NO,\n" );
    	sql.append("       NVL(A.IN_ORDER_NO,'') SOURCE_OUT_NO,\n" );
    	sql.append("       NVL(TO_CHAR(B.CHECK_DATE, 'YYYY-MM-DD HH24:MI:SS'),'') CHECK_DATE_1,\n" );
    	sql.append("       NVL(F.PERSON_NAME,'') CHECK_USER_1,\n" );
    	sql.append("       NVL(B.CHECK_ORG_NAME,'') CHECK_ORG_NAME_1,\n" );
    	sql.append("       NVL(B.CHECK_REMARK,'') CHECK_REMARK_1,\n" );
    	sql.append("       NVL(TO_CHAR(C.CHECK_DATE, 'YYYY-MM-DD HH24:MI:SS'),'') CHECK_DATE_2,\n" );
    	sql.append("       NVL(G.PERSON_NAME,'') CHECK_USER_2,\n" );
    	sql.append("       NVL(C.CHECK_ORG_NAME,'') CHECK_ORG_NAME_2,\n" );
    	sql.append("       NVL(C.CHECK_REMARK,'') CHECK_REMARK_2,\n" );
    	sql.append("       NVL(TO_CHAR(D.CHECK_DATE, 'YYYY-MM-DD HH24:MI:SS'),'') CHECK_DATE_3\n" );
    	sql.append("  FROM PT_BU_CLAIM_APPLY A,\n" );
    	sql.append("       (SELECT T.APPLY_ID,\n" );
    	sql.append("               T1.CHECK_DATE,\n" );
    	sql.append("               T.CHECK_USER,\n" );
    	sql.append("               T.CHECK_ORG_NAME,\n" );
    	sql.append("               T.CHECK_REMARK\n" );
    	sql.append("          FROM PT_BU_CLAIM_APPLY_LOG T,\n" );
    	sql.append("               (SELECT APPLY_ID, MAX(CHECK_DATE) CHECK_DATE\n" );
    	sql.append("                  FROM PT_BU_CLAIM_APPLY_LOG\n" );
    	sql.append("                 WHERE CHECK_RESULT = 205404\n" );
    	sql.append("                 GROUP BY APPLY_ID) T1\n" );
    	sql.append("         WHERE T.APPLY_ID = T1.APPLY_ID\n" );
    	sql.append("           AND T.CHECK_DATE = T1.CHECK_DATE) B,\n" );
    	sql.append("       (SELECT T.APPLY_ID,\n" );
    	sql.append("               T1.CHECK_DATE,\n" );
    	sql.append("               T.CHECK_USER,\n" );
    	sql.append("               T.CHECK_ORG_NAME,\n" );
    	sql.append("               T.CHECK_REMARK\n" );
    	sql.append("          FROM PT_BU_CLAIM_APPLY_LOG T,\n" );
    	sql.append("               (SELECT APPLY_ID, MAX(CHECK_DATE) CHECK_DATE\n" );
    	sql.append("                  FROM PT_BU_CLAIM_APPLY_LOG\n" );
    	sql.append("                 WHERE CHECK_RESULT = 205405\n" );
    	sql.append("                 GROUP BY APPLY_ID) T1\n" );
    	sql.append("         WHERE T.APPLY_ID = T1.APPLY_ID\n" );
    	sql.append("           AND T.CHECK_DATE = T1.CHECK_DATE) C,\n" );
    	sql.append("       (SELECT APPLY_ID, MAX(CHECK_DATE) CHECK_DATE\n" );
    	sql.append("          FROM PT_BU_CLAIM_APPLY_LOG\n" );
    	sql.append("         WHERE CHECK_RESULT = 205406\n" );
    	sql.append("         GROUP BY APPLY_ID) D,\n" );
    	sql.append("       DIC_TREE E,\n" );
    	sql.append("       TM_USER F,\n" );
    	sql.append("       TM_USER G,\n" );
    	sql.append("       DIC_TREE H\n" );
    	sql.append(" WHERE 1 = 1\n" );
    	sql.append("   AND A.STATUS = 100201\n" );
    	sql.append("   AND A.APPLY_ID = B.APPLY_ID(+)\n" );
    	sql.append("   AND A.APPLY_ID = C.APPLY_ID(+)\n" );
    	sql.append("   AND A.APPLY_ID = D.APPLY_ID(+)\n" );
    	sql.append("   AND A.APPLY_STATUS = E.ID\n" );
    	sql.append("   AND A.UNIT = H.ID\n" );
    	sql.append("   AND B.CHECK_USER = F.ACCOUNT(+)\n" );
    	sql.append("   AND C.CHECK_USER = G.ACCOUNT(+)\n" );
    	sql.append("   AND A.APPLY_STATUS > 205401 AND " + pConditions + "\n" );
    	sql.append(" ORDER BY A.PART_ID");

        return factory.select(null, sql.toString());
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
    public BaseResultSet searchClaimCyclApplyOem(PageManager pPageManager, User pUser, String pConditions) throws Exception {

        String wheres = pConditions;
        wheres += " AND A.STATUS = " + DicConstant.YXBS_01 + "\n"
        		+ " AND A.APPLY_ID = B.APPLY_ID(+)\n"
        		+ " AND A.APPLY_ID = C.APPLY_ID(+)\n"
        		+ " AND A.APPLY_ID = D.APPLY_ID(+)\n"
                + " ORDER BY A.PART_ID";
        pPageManager.setFilter(wheres);
        BaseResultSet baseResultSet = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT A.ORG_CODE,\n" );
        sql.append("       A.ORG_NAME,\n" );
        sql.append("       A.APPLY_ID,\n" );
        sql.append("       A.APPLY_NO,\n" );
        sql.append("       A.IN_ORDER_NO,\n" );
        sql.append("       A.IN_ORDER_ID,\n" );
        sql.append("       A.OUT_ORDER_NO,\n" );
        sql.append("       A.OUT_ORDER_ID,\n" );
        sql.append("       A.OUT_DATE,\n" );
        sql.append("       A.PHONE,\n" );
        sql.append("       A.UNIT,\n" );
        sql.append("       A.SALE_PRICE,\n" );
        sql.append("       A.AMOUNT,\n" );
        sql.append("       A.OUT_COUNT,\n" );
        sql.append("       A.SUPPLIER_ID,\n" );
        sql.append("       A.SUPPLIER_CODE,\n" );
        sql.append("       A.SUPPLIER_NAME,\n" );
        sql.append("       A.CUSTOMER_NAME,\n" );
        sql.append("       A.LINK_MAN,\n" );
        sql.append("       A.PART_ID,\n" );
        sql.append("       A.PART_CODE,\n" );
        sql.append("       A.PART_NAME,\n" );
        sql.append("       A.CLAIM_COUNT,\n" );
        sql.append("       A.APPLY_DATE,\n" );
        sql.append("       A.APPLY_STATUS,\n" );
        sql.append("       A.FAULT_CONDITONS,\n" );
        sql.append("       A.REMARKS,\n" );
        sql.append("       A.SOURCE_IN_NO,\n" );//配送中心入库单
        sql.append("       A.IN_ORDER_NO     SOURCE_OUT_NO,\n" );//配送中心出库单
        sql.append("       B.CHECK_DATE      CHECK_DATE_1,\n" );//初审时间
        sql.append("       B.CHECK_USER      CHECK_USER_1,\n" );//初审人
        sql.append("       B.CHECK_ORG_NAME  CHECK_ORG_NAME_1,\n" );//初审单位
        sql.append("       B.CHECK_REMARK    CHECK_REMARK_1,\n" );//初审意见
        sql.append("       C.CHECK_DATE      CHECK_DATE_2,\n" );//终审时间
        sql.append("       C.CHECK_USER      CHECK_USER_2,\n" );//终审人
        sql.append("       C.CHECK_ORG_NAME  CHECK_ORG_NAME_2,\n" );//终审单位
        sql.append("       C.CHECK_REMARK    CHECK_REMARK_2,\n" );//终审意见
        sql.append("       D.CHECK_DATE      CHECK_DATE_3\n" );//作废时间
        sql.append("  FROM PT_BU_CLAIM_APPLY A,\n" );
        sql.append("       (SELECT T.APPLY_ID,\n" );
        sql.append("               T1.CHECK_DATE,\n" );
        sql.append("               T.CHECK_USER,\n" );
        sql.append("               T.CHECK_ORG_NAME,\n" );
        sql.append("               T.CHECK_REMARK\n" );
        sql.append("          FROM PT_BU_CLAIM_APPLY_LOG T,\n" );
        sql.append("               (SELECT APPLY_ID, MAX(CHECK_DATE) CHECK_DATE\n" );
        sql.append("                  FROM PT_BU_CLAIM_APPLY_LOG\n" );
        sql.append("                 WHERE CHECK_RESULT = "+DicConstant.PJSBSPZT_04+"\n" );
        sql.append("                 GROUP BY APPLY_ID) T1\n" );
        sql.append("         WHERE T.APPLY_ID = T1.APPLY_ID\n" );
        sql.append("           AND T.CHECK_DATE = T1.CHECK_DATE) B,\n" );
        sql.append("       (SELECT T.APPLY_ID,\n" );
        sql.append("               T1.CHECK_DATE,\n" );
        sql.append("               T.CHECK_USER,\n" );
        sql.append("               T.CHECK_ORG_NAME,\n" );
        sql.append("               T.CHECK_REMARK\n" );
        sql.append("          FROM PT_BU_CLAIM_APPLY_LOG T,\n" );
        sql.append("               (SELECT APPLY_ID, MAX(CHECK_DATE) CHECK_DATE\n" );
        sql.append("                  FROM PT_BU_CLAIM_APPLY_LOG\n" );
        sql.append("                 WHERE CHECK_RESULT = "+DicConstant.PJSBSPZT_05+"\n" );
        sql.append("                 GROUP BY APPLY_ID) T1\n" );
        sql.append("         WHERE T.APPLY_ID = T1.APPLY_ID\n" );
        sql.append("           AND T.CHECK_DATE = T1.CHECK_DATE) C,\n" );
        sql.append("               (SELECT APPLY_ID, MAX(CHECK_DATE) CHECK_DATE\n" );
        sql.append("                  FROM PT_BU_CLAIM_APPLY_LOG\n" );
        sql.append("                 WHERE CHECK_RESULT = "+DicConstant.PJSBSPZT_06+"\n" );
        sql.append("                 GROUP BY APPLY_ID) D");
        baseResultSet = factory.select(sql.toString(), pPageManager);
        baseResultSet.setFieldDic("APPLY_STATUS", DicConstant.PJSBSPZT);
        baseResultSet.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
        baseResultSet.setFieldDateFormat("OUT_DATE", "yyyy-MM-dd HH:mm:ss");
        baseResultSet.setFieldDateFormat("CHECK_DATE", "yyyy-MM-dd HH:mm:ss");
        baseResultSet.setFieldDateFormat("CHECK_DATE_1", "yyyy-MM-dd HH:mm:ss");
        baseResultSet.setFieldDateFormat("CHECK_DATE_2", "yyyy-MM-dd HH:mm:ss");
        baseResultSet.setFieldDateFormat("CHECK_DATE_3", "yyyy-MM-dd HH:mm:ss");
        baseResultSet.setFieldDic("UNIT", DicConstant.JLDW);
        baseResultSet.setFieldUserID("CHECK_USER_1");
        baseResultSet.setFieldUserID("CHECK_USER_2");
        return baseResultSet;
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
        wheres += " AND A.APPLY_STATUS<>'"+DicConstant.PJSBSPZT_01+"' AND (A.ORG_ID=" + pUser.getOrgId() + " OR A.ORG_ID IN(SELECT ORG_ID FROM PT_BA_SERVICE_DC WHERE DC_ID=" + pUser.getOrgId() + "))\n"
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
    
    public BaseResultSet getPartClaimInfo(PageManager pageManager, User pUser, String applyId) throws Exception {

        //定义返回结果集
        BaseResultSet baseResultSet = null;
        StringBuilder sql= new StringBuilder();
        sql.append(" SELECT *\n" );
        sql.append(" FROM \n" );
        sql.append("     PT_BU_CLAIM_APPLY \n" );
        sql.append(" WHERE APPLY_ID = "+applyId+"");
        //执行方法，不需要传递conn参数
        baseResultSet = factory.select(sql.toString(), pageManager);
        baseResultSet.setFieldDic("APPLY_STATUS", "PJSBSPZT");
        return baseResultSet;
    }
}