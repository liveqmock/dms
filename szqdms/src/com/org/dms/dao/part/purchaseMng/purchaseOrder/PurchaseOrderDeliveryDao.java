package com.org.dms.dao.part.purchaseMng.purchaseOrder;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.common.PartOddNumberUtil;
import com.org.dms.vo.part.PtBuIssueOrderDtlVO;
import com.org.dms.vo.part.PtBuIssueOrderVO;
import com.org.dms.vo.part.PtBuPchOrderShippingVO;
import com.org.dms.vo.part.PtBuPchOrderSplitDtlVO;
import com.org.dms.vo.part.PtBuPchOrderSplitVO;
import com.org.dms.vo.part.PtBuSaleOrderDtlVO;
import com.org.dms.vo.part.PtBuSaleOrderVO;
import com.org.dms.vo.part.PtBuStockInContinualVO;
import com.org.dms.vo.part.PtBuStockInDtlVO;
import com.org.dms.vo.part.PtBuStockInVO;
import com.org.dms.vo.part.PtBuStockOutContinualVO;
import com.org.dms.vo.part.PtBuStockOutDtlVO;
import com.org.dms.vo.part.PtBuStockOutVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.SequenceUtil;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class PurchaseOrderDeliveryDao extends BaseDAO{
	public  static final PurchaseOrderDeliveryDao getInstance(ActionContext atx)
    {
		PurchaseOrderDeliveryDao dao = new PurchaseOrderDeliveryDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	/**
	 * 
	 * @date()2014年7月17日下午2:53:09
	 * @author Administrator
	 * @to_do:订单发运查询
	 * @param page
	 * @param user
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet orderSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres +="AND T.SUPPLIER_ID = T1.SUPPLIER_ID AND T1.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n" +
				"   AND NVL(T3.SHIP_COUNT,0) < NVL(T3.PCH_COUNT,0)\n" +
				"   AND T.SPLIT_ID = T2.SPLIT_ID(+)\n" +
				"   AND T.SPLIT_ID = T3.SPLIT_ID(+)\n" +
				"   AND T1.ORG_ID = "+user.getOrgId()+"\n" + 
				//"   AND T.COMPANY_ID = "+user.getCompanyId()+"\n" + 
				"   AND T.OEM_COMPANY_ID = "+user.getOemCompanyId()+"\n" + 
				"   AND T.IF_ON_TIME = "+DicConstant.SF_01+"\n" + 
				"   AND T.ORDER_STATUS IN( "+DicConstant.CGDDZT_03+","+DicConstant.CGDDZT_04+")";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.SPLIT_ID,\n" );
    	sql.append("       T.PURCHASE_ID,\n" );
    	sql.append("       T.SPLIT_NO,\n" );
    	sql.append("       T.SELECT_MONTH,\n" );
    	sql.append("       T.PURCHASE_TYPE,\n" );
    	sql.append("       T.APPLY_USER,\n" );
    	sql.append("       T.APPLY_DATE,\n" );
    	sql.append("       T.SUPPLIER_ID,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("       T.SUPPLIER_CODE,\n" );
    	sql.append("       T.REPUIREMENT_TIME,\n" );
    	sql.append("       T.LAST_SHIP_DATE,\n" );
    	sql.append("       T.FIRST_SHIP_DATE,\n" );
    	sql.append("       T2.SALE_ORDER_NO,\n" );
    	sql.append("       T2.ORDER_ID,\n" );
    	sql.append("       T.SHIP_TIMES\n" );
    	sql.append("  FROM PT_BU_PCH_ORDER_SPLIT T,\n" );
    	sql.append("       PT_BA_SUPPLIER T1,\n" );
    	sql.append("       (SELECT A.ORDER_NO SALE_ORDER_NO, A.ORDER_ID, C.SPLIT_ID\n" );
    	sql.append("          FROM PT_BU_SALE_ORDER      A,\n" );
    	sql.append("               PT_BU_PCH_ORDER       B,\n" );
    	sql.append("               PT_BU_PCH_ORDER_SPLIT C\n" );
    	sql.append("         WHERE A.ORDER_ID = B.SALE_ORDER_ID\n" );
    	sql.append("           AND B.PURCHASE_ID = C.PURCHASE_ID) T2,\n" );
    	sql.append("       (SELECT NVL(SUM(PCH_COUNT), 0) PCH_COUNT,\n" );
    	sql.append("               NVL(SUM(SHIP_COUNT), 0) SHIP_COUNT,\n" );
    	sql.append("               SPLIT_ID\n" );
    	sql.append("          FROM PT_BU_PCH_ORDER_SPLIT_DTL\n" );
    	sql.append("         GROUP BY SPLIT_ID) T3");

    	bs = factory.select(sql.toString(), page);
		bs.setFieldDic("PURCHASE_TYPE", DicConstant.CGDDLX);
		bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("FIRST_SHIP_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("LAST_SHIP_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("REPUIREMENT_TIME", "yyyy-MM-dd");
		bs.setFieldUserID("APPLY_USER");
    	return bs;
    }
	/**
	 * 
	 * @date()2014年7月17日下午3:05:57
	 * @author Administrator
	 * @to_do:订单配件查询
	 * @param page
	 * @param user
	 * @param SPLIT_ID
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet orderPartSearch(PageManager page, User user, String SPLIT_ID) throws Exception
    {
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.DETAIL_ID,\n" );
    	sql.append("       T.SPLIT_ID,\n" );
    	sql.append("       T.PART_ID,\n" );
    	sql.append("       T.PART_CODE,\n" );
    	sql.append("       T.PART_NAME,\n" );
    	sql.append("       T1.UNIT,\n" );
    	sql.append("       T1.MIN_PACK,\n" );
    	sql.append("       T1.MIN_UNIT,");
    	sql.append("       T.PCH_COUNT,\n" );
    	sql.append("       T.PCH_PRICE,\n" );
    	sql.append("       T.PCH_AMOUNT,\n" );
    	sql.append("       T.PCH_COUNT - NVL(T.SHIP_COUNT, 0) WILL_SHIP_COUNT,\n" );
    	sql.append("       NVL(T.SHIP_COUNT, 0) SHIP_COUNT,\n" );
    	sql.append("       T2.DELIVERY_CYCLE,\n" );
    	sql.append("       T3.DIRECT_TYPE_CODE,\n" );
    	sql.append("       T.REMARKS\n" );
    	sql.append("  FROM PT_BU_PCH_ORDER_SPLIT_DTL T, PT_BA_INFO T1, PT_BU_PCH_ORDER_SPLIT T2,PT_BA_INFO T3\n" );
    	sql.append(" WHERE T.PART_ID = T1.PART_ID AND T.PART_ID = T3.PART_ID(+)\n" );
    	sql.append("   AND T.SPLIT_ID = T2.SPLIT_ID");
    	sql.append("    AND T.PCH_COUNT>NVL(T.SHIP_COUNT,0)");
    	sql.append("   AND T.SPLIT_ID = "+SPLIT_ID+" ORDER BY PART_CODE");
    	bs = factory.select(sql.toString(), page);
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("UNIT", "JLDW");
    	bs.setFieldDic("MIN_UNIT", "JLDW");
    	return bs;
    }
	public boolean shippingPart(PtBuPchOrderSplitDtlVO vo)
            throws Exception {
        return factory.update(vo);
    }
	public boolean updateShipping(PtBuPchOrderSplitVO vo)
            throws Exception {
        return factory.update(vo);
    }
	public String getId() throws Exception {
        return SequenceUtil.getCommonSerivalNumber(factory);
    }
	public String createInBillNo(String splitId, String splitNo) throws Exception {
        return PartOddNumberUtil.getPchInBillNo(factory, splitId, splitNo);
    }
	/**
	 * 
	 * @date()2014年9月10日上午10:50:11
	 * @author Administrator
	 * @to_do:生成入库单
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean insertInBill(PtBuStockInVO vo)
            throws Exception {
        return factory.insert(vo);
    }
	
	public boolean updateSplitOrder(PtBuPchOrderSplitVO vo)
            throws Exception {
        return factory.update(vo);
    }
	public QuerySet getStock() throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.WAREHOUSE_ID,T.WAREHOUSE_CODE,T.WAREHOUSE_NAME FROM PT_BA_WAREHOUSE T WHERE T.WAREHOUSE_STATUS = 100201 AND T.WAREHOUSE_TYPE = 100101 AND T.WAREHOUSE_ID = 2014071100000859");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	/**
	 * 
	 * @date()2014年9月10日上午10:50:25
	 * @author Administrator
	 * @to_do:生成出库单
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean insertOutBill(PtBuStockOutVO vo)
            throws Exception {
        return factory.insert(vo);
    }
	
	public QuerySet getSale(String SPLIT_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.ORDER_ID,\n" );
    	sql.append("       T.ORDER_NO,\n" );
    	sql.append("       T.ORG_CODE,\n" );
    	sql.append("       T.ORG_NAME,\n" );
    	sql.append("       T.ORG_ID,\n" );
    	sql.append("       T.WAREHOUSE_ID,\n" );
    	sql.append("       T.WAREHOUSE_CODE,\n" );
    	sql.append("       T.WAREHOUSE_NAME,\n" );
    	sql.append("       T3.CHECK_USER,\n" );
    	sql.append("       T2.SUPPLIER_ID,\n" );
    	sql.append("       T2.SUPPLIER_NAME,\n" );
    	sql.append("       T2.SUPPLIER_CODE,\n" );
    	sql.append("       T2.SPLIT_NO,\n" );
    	sql.append("       T1.APPLY_USER\n" );
    	sql.append("  FROM PT_BU_SALE_ORDER       T,\n" );
    	sql.append("       PT_BU_PCH_ORDER        T1,\n" );
    	sql.append("       PT_BU_PCH_ORDER_SPLIT  T2,\n" );
    	sql.append("       PT_BU_SALE_ORDER_CHECK T3\n" );
    	sql.append(" WHERE T.ORDER_ID = T1.SALE_ORDER_ID\n" );
    	sql.append("   AND T1.PURCHASE_ID = T2.PURCHASE_ID\n" );
    	sql.append("   AND T.ORDER_ID = T3.ORDER_ID\n" );
    	sql.append("   AND T2.SPLIT_ID = "+SPLIT_ID+"");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	public QuerySet getPartInfo(String DETAIL_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.PART_ID,\n" );
    	sql.append("       T.PART_NAME,\n" );
    	sql.append("       T.PART_CODE,\n" );
    	sql.append("       T.PCH_PRICE,\n" );
    	sql.append("       T1.PLAN_PRICE,\n" );
    	sql.append("       T.SUPPLIER_ID,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("       T.SUPPLIER_CODE,\n" );
    	sql.append("       T1.UNIT,\n" );
    	sql.append("       T2.POSITION_ID,\n" );
    	sql.append("       T2.POSITION_CODE,\n" );
    	sql.append("       T2.POSITION_NAME,\n" );
    	sql.append("       T3.UNIT_PRICE\n" );
    	sql.append("  FROM PT_BU_PCH_ORDER_SPLIT_DTL T,\n" );
    	sql.append("       PT_BA_INFO                T1,\n" );
    	sql.append("       PT_BA_WAREHOUSE_PART_RL   T2,\n" );
    	sql.append("       PT_BU_SALE_ORDER_DTL      T3,\n" );
    	sql.append("       PT_BU_PCH_ORDER           T4,\n" );
    	sql.append("       PT_BU_PCH_ORDER_SPLIT     T5\n" );
    	sql.append(" WHERE T.PART_ID = T1.PART_ID\n" );
    	sql.append("   AND T.PART_ID = T2.PART_ID\n" );
    	sql.append("   AND T.SPLIT_ID = T5.SPLIT_ID\n" );
    	sql.append("   AND T5.PURCHASE_ID = T4.PURCHASE_ID\n" );
    	sql.append("   AND T4.SALE_ORDER_ID = T3.ORDER_ID\n" );
    	sql.append("   AND T3.PART_ID = T.PART_ID\n" );
    	sql.append("   AND T2.IF_DEFAULT = "+DicConstant.SF_01+"\n" );
    	sql.append("   AND T.DETAIL_ID = "+DETAIL_ID+"");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	public boolean inserPartIn(PtBuStockInDtlVO vo)
            throws Exception {
        return factory.insert(vo);
    }

	public boolean insertPartContinualIn(PtBuStockInContinualVO vo)
            throws Exception {
        return factory.insert(vo);
    }
	public boolean inserPartOut(PtBuStockOutDtlVO vo)
            throws Exception {
        return factory.insert(vo);
    }

	public boolean insertPartContinualOut(PtBuStockOutContinualVO vo)
            throws Exception {
        return factory.insert(vo);
    }
	
	public QuerySet getOrderType(String SPLIT_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT PURCHASE_TYPE FROM PT_BU_PCH_ORDER_SPLIT WHERE 1=1");
    	sql.append("   AND SPLIT_ID = "+SPLIT_ID+"");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	public QuerySet queryifFirst(String SPLIT_ID) throws Exception
	{
		QuerySet qs = null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT NVL(T.SHIP_COUNT,0) SHIP_COUNT,NVL(T.SHIP_TIMES,0) SHIP_TIMES FROM PT_BU_PCH_ORDER_SPLIT T WHERE SPLIT_ID = "+SPLIT_ID+"  AND T.FIRST_SHIP_DATE IS NOT NULL");
		qs = factory.select(null, sql.toString());
		return qs;
	}
	
	public QuerySet getPchPrice(String DTL_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.PCH_PRICE FROM PT_BU_PCH_ORDER_SPLIT_DTL T WHERE T.DETAIL_ID = "+DTL_ID+"");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	public boolean inserShipLog(PtBuPchOrderShippingVO vo)
            throws Exception {
        return factory.insert(vo);
    }
	public QuerySet checkIfSQ(String SPLIT_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT 1\n" );
    	sql.append("  FROM PT_BU_PCH_ORDER_SPLIT\n" );
    	sql.append(" WHERE SUPPLIER_CODE NOT LIKE '%SQGH%'\n" );
    	sql.append("   AND SPLIT_ID = "+SPLIT_ID+"");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	public boolean updateSaleOrder(PtBuSaleOrderVO vo)
            throws Exception {
        return factory.update(vo);
    }
	public boolean updatePchOrderAccept(String SPLIT_ID,String DETAIL_ID,String COUNT,User user)
            throws Exception {
		StringBuffer sql= new StringBuffer();
		sql.append("UPDATE PT_BU_PCH_ORDER_SPLIT_DTL T\n" );
		sql.append("   SET T.ACCEPT_COUNT = "+COUNT+",T.STORAGE_COUNT = "+COUNT+", T.UPDATE_USER = '"+user.getAccount()+"', T.UPDATE_TIME = SYSDATE\n" );
		sql.append(" WHERE T.SPLIT_ID = "+SPLIT_ID+" AND T.DETAIL_ID = "+DETAIL_ID+"");
        return factory.update(sql.toString(), null);
    }
	public boolean insertIssue(PtBuIssueOrderVO vo)
            throws Exception {
        return factory.insert(vo);
    }
	public boolean insertIssueDtl(PtBuIssueOrderDtlVO vo)
            throws Exception {
        return factory.insert(vo);
    }
	public QuerySet getDevSplit(String SPLIT_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT NVL(SUM(PCH_COUNT),0) COUNT FROM PT_BU_PCH_ORDER_SPLIT_DTL \n" );
    	sql.append(" WHERE SPLIT_ID = "+SPLIT_ID+"");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	public QuerySet getSaleDtl(String ORDER_ID,String PART_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.DTL_ID, T.UNIT_PRICE\n" );
    	sql.append("  FROM PT_BU_SALE_ORDER_DTL T\n" );
    	sql.append(" WHERE T.ORDER_ID = "+ORDER_ID+"\n" );
    	sql.append("   AND T.PART_ID = "+PART_ID+"");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	public boolean updateSaleDtl(PtBuSaleOrderDtlVO vo)
            throws Exception {
        return factory.update(vo);
    }
	public QuerySet getInConNo() throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT INSTORE.NEXTVAL NO FROM DUAL");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	public QuerySet getOutConNo() throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT F_GETOUT_CONTINUAL C_NO FROM DUAL");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	public QuerySet getRealAmount(String SALE_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT SUM(NVL(T.DELIVERY_COUNT,0) * NVL(T.UNIT_PRICE,0)) REAL_AMOUNT\n" );
    	sql.append("  FROM PT_BU_SALE_ORDER_DTL T\n" );
    	sql.append(" WHERE T.ORDER_ID = "+SALE_ID+"");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	public QuerySet getKeeper() throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT ACCOUNT,PERSON_NAME,USER_ID FROM TM_USER WHERE ACCOUNT = 'TONGHUIKU'");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	public QuerySet getAduit(String ORDER_ID,String PART_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.AUDIT_COUNT\n" );
    	sql.append("FROM PT_BU_SALE_ORDER_DTL T\n" );
    	sql.append("WHERE T.ORDER_ID = "+ORDER_ID+"\n" );
    	sql.append("AND T.PART_ID = "+PART_ID+"");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	public QuerySet getStorage(String SPLIT_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT SUM(NVL(T.STORAGE_COUNT,0)) COUNT\n" );
    	sql.append("  FROM PT_BU_PCH_ORDER_SPLIT_DTL T\n" );
    	sql.append(" WHERE T.SPLIT_ID = "+SPLIT_ID+"");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	public void updateInBillDtl(String IN_ID, String IN_TYPE) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE PT_BU_STOCK_IN_DTL A\n");
        sql.append("   SET \n");
        if (DicConstant.RKLX_01.equals(IN_TYPE)) {//只有采购入库更新采购单价和采购金额
            sql.append("       A.PCH_PRICE  =\n");
            sql.append("       (SELECT D.PCH_PRICE\n");
            sql.append("          FROM PT_BU_STOCK_IN            B,\n");
            sql.append("               PT_BU_PCH_ORDER_SPLIT_DTL C,\n");
            sql.append("               PT_BU_PCH_ORDER_SPLIT 	 E,\n");
            sql.append("               PT_BA_PART_SUPPLIER_RL    D\n");
            sql.append("         WHERE B.IN_ID = " + IN_ID + "\n");
            sql.append("           AND B.ORDER_ID = C.SPLIT_ID\n");
            sql.append("           AND C.SPLIT_ID = E.SPLIT_ID\n");
            sql.append("           AND E.SUPPLIER_ID = D.SUPPLIER_ID AND D.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n");
            sql.append("           AND C.PART_ID = D.PART_ID\n");
            sql.append("           AND C.PART_ID = A.PART_ID),\n");
            sql.append("       A.PCH_AMOUNT =\n");
            sql.append("       (SELECT D.PCH_PRICE * A.IN_AMOUNT\n");
            sql.append("          FROM PT_BU_STOCK_IN            B,\n");
            sql.append("               PT_BU_PCH_ORDER_SPLIT_DTL C,\n");
            sql.append("               PT_BU_PCH_ORDER_SPLIT 	 E,\n");
            sql.append("               PT_BA_PART_SUPPLIER_RL    D\n");
            sql.append("         WHERE B.IN_ID = " + IN_ID + "\n");
            sql.append("           AND B.ORDER_ID = C.SPLIT_ID\n");
            sql.append("           AND C.SPLIT_ID = E.SPLIT_ID\n");
            sql.append("           AND E.SUPPLIER_ID = D.SUPPLIER_ID AND D.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n");
            sql.append("           AND C.PART_ID = D.PART_ID\n");
            sql.append("           AND C.PART_ID = A.PART_ID),\n");
        }
        sql.append("       A.PLAN_PRICE =\n");
        sql.append("       (SELECT E.PLAN_PRICE FROM PT_BA_INFO E WHERE E.PART_ID = A.PART_ID),\n");
        sql.append("       A.PLAN_AMOUNT =\n");
        sql.append("       (SELECT E.PLAN_PRICE * A.IN_AMOUNT\n");
        sql.append("          FROM PT_BA_INFO E\n");
        sql.append("         WHERE E.PART_ID = A.PART_ID),\n");
        sql.append("       A.UNIT       =\n");
        sql.append("       (SELECT E.UNIT FROM PT_BA_INFO E WHERE E.PART_ID = A.PART_ID)\n");
        sql.append(" WHERE A.IN_ID = " + IN_ID + "\n");

        factory.update(sql.toString(), null);
    }
    /**
     * 更新入库流水的计划价
     *
     * @param IN_ID 入库单ID
     * @throws Exception
     */
    public void updateInFlow(String IN_ID) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE PT_BU_STOCK_IN_CONTINUAL A\n");
        sql.append("   SET A.PLAN_PRICE =\n");
        sql.append("       (SELECT 1 FROM PT_BA_INFO B WHERE B.PART_ID = A.PART_ID)\n");
        sql.append(" WHERE A.IN_ID = " + IN_ID + "\n");
        factory.update(sql.toString(), null);
    }
    
    /**
     * 资金占用释放
     */
    public void orderReleaseFreez(String orderId,User user)
            throws Exception {
        QuerySet qs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT A.OFUNDS_ID,A.ACCOUNT_ID,A.ACCOUNT_TYPE,A.OCCUPY_FUNDS FROM PT_BU_SALE_ORDER_OCCUPY_FUNDS A \n" );
        sql.append("  WHERE STATUS = "+DicConstant.YXBS_01+"\n" );
        sql.append("   AND A.ORDER_ID = "+orderId+"\n");
        qs = factory.select(null, sql.toString());
        if(qs.getRowCount()>0){
            for(int i=0;i<qs.getRowCount();i++){
                String ofundsId = qs.getString(i+1, "OFUNDS_ID");
                String accountId = qs.getString(i+1, "ACCOUNT_ID");
                String accountType = qs.getString(i+1, "ACCOUNT_TYPE");
                String occupyFunds = qs.getString(i+1, "OCCUPY_FUNDS");
                StringBuffer sql1 = new StringBuffer();
                sql1.append("UPDATE PT_BU_SALE_ORDER_OCCUPY_FUNDS SET STATUS ="+DicConstant.YXBS_02+",UPDATE_USER='"+user.getAccount()+"',\n");
                sql1.append("UPDATE_TIME=SYSDATE WHERE OFUNDS_ID = "+ofundsId+"\n");
                factory.update(sql1.toString(), null);
                StringBuffer sql2 = new StringBuffer();
                sql2.append("UPDATE PT_BU_ACCOUNT SET OCCUPY_AMOUNT =OCCUPY_AMOUNT -"+occupyFunds+",AVAILABLE_AMOUNT= AVAILABLE_AMOUNT+"+occupyFunds+",\n");
                sql2.append(" UPDATE_USER='"+user.getAccount()+"',UPDATE_TIME=SYSDATE WHERE ACCOUNT_ID="+accountId+" AND ACCOUNT_TYPE="+accountType+"\n");
                factory.update(sql2.toString(), null);
            }
        }
    }
}
