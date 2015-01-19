package com.org.dms.dao.part.salesMng.orderMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuAccountLogVO;
import com.org.dms.vo.part.PtBuSaleOrderVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;
/**
 * @Description:订单关闭Dao
 * @Date: 2014.08.25
 * @Author sunxuedong
 */
public class PartOrderCloseDao extends BaseDAO{
    public static final PartOrderCloseDao getInstance(ActionContext atx) {
        PartOrderCloseDao dao = new PartOrderCloseDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
    
    /**
     * 查询订单明细
     *
     * @param page
     * @param user
     * @param conditions
     * @return
     * @throws Exception
     */
    public BaseResultSet partOrderDtlPrintSearch(PageManager page, User user, String conditions, String orderId) throws Exception {
        String wheres = conditions;
        wheres += " AND A.ORDER_ID = " + orderId + "\n";
        wheres += " AND A.PART_ID = B.PART_ID\n";
        wheres += " ORDER BY A.PART_CODE ASC\n";
        page.setFilter(wheres);
        //定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql= new StringBuilder();
        sql.append("SELECT A.PART_ID,\n" );
        sql.append("       A.PART_CODE,\n" );
        sql.append("       A.PART_NAME,\n" );
        sql.append("       B.UNIT,\n" );
        sql.append("       B.MIN_PACK,\n" );
        sql.append("       B.MIN_UNIT,\n" );
        sql.append("       A.ORDER_COUNT,\n" );
        sql.append("       A.AUDIT_COUNT,\n" );
        sql.append("       A.REMARKS,\n" );
        sql.append("       A.AMOUNT\n" );
        sql.append("  FROM PT_BU_SALE_ORDER_DTL A, PT_BA_INFO B\n" );

        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("UNIT", DicConstant.JLDW);
        bs.setFieldDic("MIN_UNIT", DicConstant.JLDW);
        return bs;
    }
    
    /**
     * 订单查询
     */
    public BaseResultSet partOrderCloseSearch(PageManager page, User user, String conditions) throws Exception
    {
        String wheres = conditions;
        wheres += " AND T.ORDER_STATUS = "+DicConstant.DDZT_03+" AND T.IF_CHANEL_ORDER ="+DicConstant.SF_02+"\n"
        		+" 	  AND ((F_IS_AM("+user.getOrgId()+") = 1 AND T.ORDER_TYPE = "+DicConstant.DDLX_08+") OR (F_IS_AM("+user.getOrgId()+") <> 1 AND T.ORDER_TYPE <> "+DicConstant.DDLX_08+")) \n"
        		+ " AND T.ORG_ID IN (SELECT ORG_ID FROM TM_ORG WHERE PID IN (SELECT ORG_ID FROM PT_BA_ORDER_CHECK WHERE USER_ACCOUNT='"+user.getAccount()+"'))"
                + " AND T.SHIP_STATUS  IN("+DicConstant.DDFYZT_04+","+DicConstant.DDFYZT_05+","+DicConstant.DDFYZT_06+","+DicConstant.DDFYZT_07+")"
                + " ORDER BY T.APPLY_DATE ASC\n";
        page.setFilter(wheres);
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.ORDER_ID,T.ORDER_NO,T.ORDER_TYPE,T.ORG_ID,T.ORG_CODE,T.ORG_NAME,T.WAREHOUSE_ID,T.ORDER_STATUS,T.REAL_AMOUNT,\n" );
        sql.append("T.WAREHOUSE_CODE,T.WAREHOUSE_NAME,T.EXPECT_DATE,T.ORDER_AMOUNT,T.APPLY_DATE,T.CREATE_USER,\n" );
        sql.append("T.REMARKS,T.IF_DELAY_ORDER,T.PROM_ID,T.IF_CREDIT,T.IF_TRANS,T.PROVINCE_CODE,T.PROVINCE_NAME,T.CITY_CODE,T.CITY_NAME,T.COUNTRY_CODE,T.COUNTRY_NAME,\n" );
        sql.append("T.TRANS_TYPE,T.ZIP_CODE,T.DELIVERY_ADDR,T.LINK_MAN,T.PHONE,T.SHIP_STATUS\n" );
        sql.append(" FROM PT_BU_SALE_ORDER T");
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("ORDER_TYPE", "DDLX");
        bs.setFieldDic("ORDER_STATUS", "DDZT");
        bs.setFieldDic("TRANS_TYPE", "FYFS");
        bs.setFieldDic("IF_CREDIT", "SF");
        bs.setFieldDic("IF_TRANS", "SF");
        bs.setFieldDateFormat("EXPECT_DATE", "yyyy-MM-dd");
        bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
        // 提报人
        bs.setFieldUserID("CREATE_USER");
        return bs;
    }

    /**
     * 修改发运单对应的订单的明细的发运数量
     *
     * @param orderId 订单ID
     * @param user
     * @return
     * @throws Exception
     */
    public boolean updateSaleOrderDtl(String orderId, User user,String partId)
            throws Exception {
        StringBuilder sql= new StringBuilder();
        sql.append("UPDATE PT_BU_SALE_ORDER_DTL A\n" );
        sql.append("   SET A.DELIVERY_COUNT =  (SELECT B.OUT_AMOUNT FROM PT_BU_STOCK_OUT_DTL B \n" );
        sql.append("       WHERE B.OUT_ID=(SELECT OUT_ID FROM PT_BU_STOCK_OUT WHERE ORDER_ID="+orderId+") AND B.PART_ID="+partId+" ),\n" );
        sql.append("       A.UPDATE_USER    = '"+user.getAccount()+"',\n" );
        sql.append("       A.UPDATE_TIME    = SYSDATE\n" );
        sql.append(" WHERE A.PART_ID="+partId+"\n");
        sql.append("   AND A.ORDER_ID="+orderId+"\n" );

        return factory.update(sql.toString(), null);
    }

    /**
     * 查询订单状态
     * 
     * @param orderId 订单ID
     * @return
     * @throws Exception
     */
    public QuerySet checkOrderType(String orderId) throws Exception {
        QuerySet qs = null;
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT  TRANS_TYPE,ORDER_TYPE FROM PT_BU_SALE_ORDER WHERE ORDER_ID = "
                + orderId + "");
        qs = factory.select(null, sql.toString());
        return qs;
    }

    /**
     * 查询订单明细
     * 
     * @param orderId 订单ID
     * @return
     * @throws Exception
     */
    public QuerySet getOrderDtl(String orderId) throws Exception {
        QuerySet qs = null;
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT  PART_ID FROM PT_BU_SALE_ORDER_DTL WHERE ORDER_ID = "+ orderId + "");
        qs = factory.select(null, sql.toString());
        return qs;
    }

    /**
     * 包装数量是否与出库数量一致查询
     *
     * @param orderId 订单ID
     * @return 结果集
     * @throws Exception
     */
    public QuerySet closeCheck(String orderId) throws Exception {

    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT NVL((SELECT SUM(A.COUNT)\n" );
    	sql.append("              FROM PT_BU_BOX_UP A\n" );
    	sql.append("             WHERE A.ORDER_ID = '"+orderId+"') -\n" );
    	sql.append("           (SELECT SUM(OUT_AMOUNT)\n" );
    	sql.append("              FROM PT_BU_STOCK_OUT_DTL\n" );
    	sql.append("             WHERE OUT_ID = (SELECT OUT_ID\n" );
    	sql.append("                               FROM PT_BU_STOCK_OUT\n" );
    	sql.append("                              WHERE ORDER_ID = '"+orderId+"')),\n" );
    	sql.append("           -1)\n" );
    	sql.append("  FROM DUAL");
        return factory.select(null, sql.toString());
    }

    /**
     * 订单使用金额查询
     */
    public QuerySet queryZYJE(String orderId) throws Exception
    {
        QuerySet qs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.OFUNDS_ID,T.OCCUPY_FUNDS-NVL(T.REPAY_AMOUNT,0) OCCUPY_FUNDS,T.ACCOUNT_TYPE,T.ACCOUNT_ID FROM PT_BU_SALE_ORDER_OCCUPY_FUNDS T WHERE T.ORDER_ID ="+orderId+" AND T.STATUS ="+DicConstant.YXBS_01+" ORDER BY T.ACCOUNT_TYPE ASC\n" );
        qs = factory.select(null, sql.toString());
        return qs;
    }

    /**
     * 查询发运单对应的订单的原订单明细的发运数量
     *
     * @param SHIP_ID 发运单ID
     * @param user
     * @return
     * @throws Exception
     */
    public QuerySet selectSaleOrderAmount(String orderId, User user) throws Exception {

    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT NVL(SUM(NVL(C.DELIVERY_COUNT,0) * C.UNIT_PRICE),0) COUNT\n" );
    	sql.append("  FROM PT_BU_SALE_ORDER_DTL C\n" );
    	sql.append(" WHERE C.ORDER_ID = '" + orderId + "'\n" );

        return factory.select(null, sql.toString());
    }

    /**
     * 查询订单是否有原单
     *
     * @param SHIP_ID 发运单ID
     * @param user
     * @return
     * @throws Exception
     */
    public QuerySet selectSourceOrder(String orderId) throws Exception {

    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT DIR_SOURCE_ORDER_ID\n" );
    	sql.append("  FROM PT_BU_SALE_ORDER\n" );
    	sql.append(" WHERE ORDER_ID = '"+orderId+"'\n" );
    	sql.append("   AND DIR_SOURCE_ORDER_ID IS NOT NULL");

        return factory.select(null, sql.toString());
    }

    /**
     * 订单占用金额变为无效
     */
    public boolean updateFunds(String ofundsId) throws Exception
    {
        StringBuffer sql= new StringBuffer();
        sql.append("UPDATE PT_BU_SALE_ORDER_OCCUPY_FUNDS SET STATUS="+DicConstant.YXBS_02+" WHERE OFUNDS_ID ="+ofundsId+"\n" );
        return factory.update(sql.toString(), null);
    }
    
    /**
     * 订单占用金额变为实发金额
     */
    public boolean updateXyedFunds(String ofundsId,String syje) throws Exception
    {
        StringBuffer sql= new StringBuffer();
        sql.append("UPDATE PT_BU_SALE_ORDER_OCCUPY_FUNDS SET OCCUPY_FUNDS="+syje+" WHERE OFUNDS_ID ="+ofundsId+"\n" );
        return factory.update(sql.toString(), null);
    }
    /**
     * 订单关闭实扣金额
     */
    public boolean updateAmount(Double zyje,String accountId)throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append(" UPDATE PT_BU_ACCOUNT T\n" );
        sql.append("   SET T.OCCUPY_AMOUNT  = T.OCCUPY_AMOUNT - "+zyje+",\n" );
        sql.append("       T.BALANCE_AMOUNT = T.BALANCE_AMOUNT - "+zyje+"\n" );
        sql.append(" WHERE T.ACCOUNT_ID = "+accountId+" ");
        return factory.update(sql.toString(), null);
    }
    /**
     * 订单关闭实扣金额
     */
    public boolean updateAmountTwo(Double zyje,Double syje,String accountId)throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append(" UPDATE PT_BU_ACCOUNT T\n" );
        sql.append("   SET T.OCCUPY_AMOUNT  = T.OCCUPY_AMOUNT - "+zyje+",\n" );
        sql.append("       T.BALANCE_AMOUNT = T.BALANCE_AMOUNT - "+syje+",\n" );
        sql.append("       T.AVAILABLE_AMOUNT = T.AVAILABLE_AMOUNT + "+zyje+"- "+syje+"\n" );
        sql.append(" WHERE T.ACCOUNT_ID = "+accountId+" ");
        return factory.update(sql.toString(), null);
    }
    /**
     * 订单关闭实扣金额
     */
    public boolean updateAmountXyed(Double zyje,Double syje,String accountId)throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append(" UPDATE PT_BU_ACCOUNT T\n" );
        sql.append("   SET T.OCCUPY_AMOUNT  = T.OCCUPY_AMOUNT -"+zyje+"+"+syje+",\n" );
        //sql.append("       T.BALANCE_AMOUNT = T.BALANCE_AMOUNT -"+syje+",\n" );
        sql.append("       T.AVAILABLE_AMOUNT = T.AVAILABLE_AMOUNT + "+zyje+"- "+syje+"\n" );
        sql.append(" WHERE T.ACCOUNT_ID = "+accountId+" ");
        return factory.update(sql.toString(), null);
    }
    /**
     * 订单关闭状态更新
     */
    public boolean updateOrder(PtBuSaleOrderVO vo)throws Exception {
        
        return factory.update(vo);
    }
    /**
     * 资金异动明细日志新增
     */
    public boolean insertamountLog(PtBuAccountLogVO vo)throws Exception {
        
        return factory.insert(vo);
    }
    /**
     * 更新计划价
     */
    public void updatePlanPrice(String orderId)throws Exception {
    	
    	StringBuffer sql= new StringBuffer();
    	sql.append("UPDATE PT_BU_STOCK_OUT_DTL S\n" );
    	sql.append("   SET S.PLAN_PRICE =\n" );
    	sql.append("       (SELECT NVL(PLAN_PRICE,0) FROM PT_BA_INFO T WHERE S.PART_ID = T.PART_ID),\n" );
    	sql.append("       S.PLAN_AMOUNT = NVL(S.OUT_AMOUNT, 0) *\n" );
    	sql.append("                       (SELECT NVL(PLAN_PRICE,0)\n" );
    	sql.append("                          FROM PT_BA_INFO T\n" );
    	sql.append("                         WHERE S.PART_ID = T.PART_ID),\n" );
    	sql.append("       S.SALE_PRICE =(SELECT UNIT_PRICE FROM PT_BU_SALE_ORDER_DTL D WHERE D.PART_ID = S.PART_ID AND D.ORDER_ID = "+orderId+"),\n" );
    	sql.append("       S.SALE_AMOUNT = NVL(S.OUT_AMOUNT, 0) *(SELECT UNIT_PRICE FROM PT_BU_SALE_ORDER_DTL D WHERE D.PART_ID = S.PART_ID AND D.ORDER_ID = "+orderId+")\n" );
    	sql.append("   WHERE S.OUT_ID = (SELECT OUT_ID FROM PT_BU_STOCK_OUT WHERE ORDER_ID ="+orderId+")\n");
    	factory.update(sql.toString(), null);
    	StringBuffer sql2= new StringBuffer();
    	sql2.append("UPDATE PT_BU_SALE_ORDER_DTL S\n" );
    	sql2.append("   SET S.PLAN_PRICE =\n" );
    	sql2.append("       (SELECT NVL(PLAN_PRICE, 0)\n" );
    	sql2.append("          FROM PT_BA_INFO T\n" );
    	sql2.append("         WHERE S.PART_ID = T.PART_ID)\n" );
    	sql2.append(" WHERE S.ORDER_ID ="+orderId+"\n");
    	factory.update(sql2.toString(), null);
    }
    public void updateOutFlow(String orderId) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE PT_BU_STOCK_OUT_CONTINUAL A\n");
        sql.append("   SET A.PLAN_PRICE =\n");
        sql.append("       (SELECT NVL(B.PLAN_PRICE,0) FROM PT_BA_INFO B WHERE B.PART_ID = A.PART_ID)\n");
        sql.append(" WHERE A.OUT_ID = (SELECT OUT_ID FROM PT_BU_STOCK_OUT WHERE ORDER_ID ="+orderId+")\n");
        factory.update(sql.toString(), null);
    }
}
