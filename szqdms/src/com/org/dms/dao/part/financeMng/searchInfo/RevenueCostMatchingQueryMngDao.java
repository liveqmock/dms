package com.org.dms.dao.part.financeMng.searchInfo;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 收入成本匹配查询Dao
 *
 * 收入成本匹配查询
 * @author zhengyao
 * @date 2014-10-30
 * @version 1.0
 */
public class RevenueCostMatchingQueryMngDao extends BaseDAO {

    /**
     * 定义instance
     *
     * @param pActionContext
     * @return revenueCostMatchingQueryMngDao 收入成本匹配查询Dao
     */
    public static final RevenueCostMatchingQueryMngDao getInstance(ActionContext pActionContext) {

        RevenueCostMatchingQueryMngDao revenueCostMatchingQueryMngDao = new RevenueCostMatchingQueryMngDao();
        pActionContext.setDBFactory(revenueCostMatchingQueryMngDao.factory);
        return revenueCostMatchingQueryMngDao;
    }

    /**
     * 收入成本匹配查询(导出)
     *
     * @pConditions 查询条件
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet download(String pConditions) throws Exception {

        String wheres = "WHERE "+ pConditions + "\n";
	        wheres += " AND A.ORDER_ID = B.ORDER_ID AND A.ORG_ID = C.ORG_ID AND C.ORG_TYPE = "+DicConstant.ZZLB_09+" AND NVL(B.DELIVERY_COUNT, 0) > 0\n";
	        wheres += " AND A.ORDER_STATUS = "+DicConstant.DDZT_06+" AND A.SHIP_STATUS IN("+DicConstant.DDFYZT_04+","+DicConstant.DDFYZT_05+","+DicConstant.DDFYZT_06+","+DicConstant.DDFYZT_07+")\n";
	        wheres += " GROUP BY A.ORDER_NO, A.ORDER_ID, A.ORG_CODE, C.ONAME, A.CLOSE_DATE ORDER BY A.CLOSE_DATE DESC\n";
//        StringBuilder sql= new StringBuilder();
//        sql.append("SELECT A.ORDER_NO,\n" );
//        sql.append("       A.ORDER_ID,\n" );
//        sql.append("       A.ORG_NAME,\n" );
//        sql.append("       NVL((SELECT SUM(UNIT_PRICE * DELIVERY_COUNT) FROM PT_BU_SALE_ORDER_DTL WHERE ORDER_ID=A.ORDER_ID),0) UNIT_PRICE,\n" );
//        sql.append("       NVL((SELECT SUM(PLAN_PRICE * DELIVERY_COUNT) FROM PT_BU_SALE_ORDER_DTL WHERE ORDER_ID=A.ORDER_ID),0) PLAN_PRICE,\n" );
//        sql.append("       A.CLOSE_DATE\n" );
//        sql.append("  FROM PT_BU_SALE_ORDER A\n" );
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT A.ORDER_NO,\n" );
        sql.append("       A.ORDER_ID,\n" );
        sql.append("       A.ORG_CODE,\n" );
        sql.append("       C.ONAME ORG_NAME,\n" );
        sql.append("       A.CLOSE_DATE,\n" );
        sql.append("       SUM(NVL(B.UNIT_PRICE,0) * NVL(B.DELIVERY_COUNT, 0)) UNIT_PRICE,\n" );
        sql.append("       SUM(NVL(B.PLAN_PRICE,0) * NVL(B.DELIVERY_COUNT, 0)) PLAN_PRICE\n" );
        sql.append("  FROM PT_BU_SALE_ORDER A, PT_BU_SALE_ORDER_DTL B, TM_ORG C\n" );

        //执行方法，不需要传递conn参数
        return factory.select(null, sql.toString()+wheres);
    }

    /**
     * 收入成本匹配表(PT_BU_SALE_ORDER)查询
     *
     * @param pConditions 月份
     * @param pUser 当前登录user对象
     * @return QuerySet 结果集
     * @throws Exception
     */
    public BaseResultSet searchOrder(PageManager pPageManager, User pUser,String pConditions) throws Exception {

        String wheres = pConditions;
//        wheres += "AND A.ORG_ID NOT IN (SELECT T1.ORG_ID FROM TM_ORG T1 WHERE T1.ORG_TYPE IN ('"+DicConstant.ZZLB_10+"','"+DicConstant.ZZLB_11+"'))";
//        wheres += " AND A.ORDER_STATUS = '"+DicConstant.DDZT_06+"' AND A.DIR_SOURCE_ORDER_ID IS NULL ORDER BY A.CLOSE_DATE DESC\n";
        wheres += " AND A.ORDER_ID = B.ORDER_ID AND A.ORG_ID = C.ORG_ID AND C.ORG_TYPE = "+DicConstant.ZZLB_09+" AND NVL(B.DELIVERY_COUNT, 0) > 0\n";
        wheres += " AND A.ORDER_STATUS = "+DicConstant.DDZT_06+" AND A.SHIP_STATUS IN("+DicConstant.DDFYZT_04+","+DicConstant.DDFYZT_05+","+DicConstant.DDFYZT_06+","+DicConstant.DDFYZT_07+")\n";
        wheres += " GROUP BY A.ORDER_NO, A.ORDER_ID, A.ORG_CODE, C.ONAME, A.CLOSE_DATE ORDER BY A.CLOSE_DATE DESC\n";
        pPageManager.setFilter(wheres);
//        StringBuilder sql= new StringBuilder();
//        sql.append("SELECT A.ORDER_NO,\n" );
//        sql.append("       A.ORDER_ID,\n" );
//        sql.append("       A.ORG_NAME,\n" );
//        sql.append("       NVL((SELECT SUM(UNIT_PRICE * DELIVERY_COUNT) FROM PT_BU_SALE_ORDER_DTL WHERE ORDER_ID=A.ORDER_ID),0) UNIT_PRICE,\n" );
//        sql.append("       NVL((SELECT SUM(PLAN_PRICE * DELIVERY_COUNT) FROM PT_BU_SALE_ORDER_DTL WHERE ORDER_ID=A.ORDER_ID),0) PLAN_PRICE,\n" );
//        sql.append("       A.CLOSE_DATE\n" );
//        sql.append("  FROM PT_BU_SALE_ORDER A\n" );
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT A.ORDER_NO,\n" );
        sql.append("       A.ORDER_ID,\n" );
        sql.append("       A.ORG_CODE,\n" );
        sql.append("       C.ONAME ORG_NAME,\n" );
        sql.append("       A.CLOSE_DATE,\n" );
        sql.append("       SUM(NVL(B.UNIT_PRICE,0) * NVL(B.DELIVERY_COUNT, 0)) UNIT_PRICE,\n" );
        sql.append("       SUM(NVL(B.PLAN_PRICE,0) * NVL(B.DELIVERY_COUNT, 0)) PLAN_PRICE\n" );
        sql.append("  FROM PT_BU_SALE_ORDER A, PT_BU_SALE_ORDER_DTL B, TM_ORG C\n" );
        //执行方法，不需要传递conn参数
        return factory.select(sql.toString(),pPageManager);
    }

    /**
     * 订单明细表(PT_BU_SALE_ORDER_DTL)查询
     *
     * @param pPageManager 查询分页对象
     * @param pUser 当前登录user对象
     * @param pConditions sql条件(默认：1=1)
     * @return QuerySet 结果集
     * @throws Exception
     */
    public BaseResultSet searchOrderDtl(PageManager pPageManager, String pConditions) throws Exception {

        String wheres = pConditions;
        wheres += " AND A.PART_ID = B.PART_ID ";
        pPageManager.setFilter(wheres);
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT A.DTL_ID,\n" );
        sql.append("       A.PART_ID,\n" );
        sql.append("       A.PART_CODE,\n" );
        sql.append("       A.PART_NAME,\n" );
        sql.append("       A.PART_NO,\n" );
        sql.append("       B.UNIT,\n" );
        sql.append("       B.MIN_PACK,\n" );
        sql.append("       B.MIN_UNIT,\n" );
        sql.append("       A.UNIT_PRICE,\n" );
        sql.append("       A.IF_SUPPLIER,\n" );
        sql.append("       A.SUPPLIER_ID,\n" );
        sql.append("       A.SUPPLIER_CODE,\n" );
        sql.append("       A.SUPPLIER_NAME,\n" );
        sql.append("       A.ORDER_COUNT,\n" );
        sql.append("       A.REMARKS,\n" );
        sql.append("       A.AMOUNT\n" );
        sql.append("  FROM PT_BU_SALE_ORDER_DTL A, PT_BA_INFO B\n" );
        bs = factory.select(sql.toString(), pPageManager);
        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(),pPageManager);
        return bs;
    }
    public BaseResultSet getAmount(PageManager page, String conditions) throws Exception
    {
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT SUM(NVL(B.UNIT_PRICE, 0) * NVL(B.DELIVERY_COUNT, 0)) UNIT_AMOUNT,\n" );
        sql.append("       SUM(NVL(B.PLAN_PRICE, 0) * NVL(B.DELIVERY_COUNT, 0)) PLAN_AMOUNT\n" );
        sql.append("  FROM PT_BU_SALE_ORDER A, PT_BU_SALE_ORDER_DTL B, TM_ORG C\n" );
        sql.append(" WHERE "+conditions+"\n" );
        sql.append("   AND A.ORDER_ID = B.ORDER_ID\n" );
        sql.append("   AND A.ORG_ID = C.ORG_ID\n" );
        sql.append("   AND C.ORG_TYPE = 200005\n" );
        sql.append("   AND NVL(B.DELIVERY_COUNT, 0) > 0\n" );
        sql.append("   AND A.ORDER_STATUS = 202206\n" );
        sql.append("   AND A.SHIP_STATUS IN (204804, 204805, 204806, 204807)\n" );
        bs = factory.select(sql.toString(), page);
        return bs;
    }
}
