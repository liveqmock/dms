package com.org.dms.dao.part.purchaseMng.purchaseOrder;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuPchOrderDtlVO;
import com.org.dms.vo.part.PtBuPchOrderSplitDtlVO;
import com.org.dms.vo.part.PtBuPchOrderSplitVO;
import com.org.dms.vo.part.PtBuPchOrderVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class VerticalShippingOrderAduitDao extends BaseDAO{
	public  static final VerticalShippingOrderAduitDao getInstance(ActionContext atx)
    {
		VerticalShippingOrderAduitDao dao = new VerticalShippingOrderAduitDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	/**
	 * 
	 * @date()2014年8月25日上午9:50:59
	 * @author Administrator
	 * @to_do:直发订单查询
	 * @param page
	 * @param user
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet orderSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres +="AND T.STATUS = "+DicConstant.YXBS_01+"\n" +
						"AND EXISTS (SELECT 1\n" +
						"       FROM PT_BA_PCH_ATTRIBUTE A, PT_BU_PCH_ORDER_DTL B\n" + 
						"      WHERE A.PART_ID = B.PART_ID\n" + 
						"        AND B.PURCHASE_ID = T.PURCHASE_ID\n" + 
						"        AND A.USER_ACCOUNT = '"+user.getAccount()+"')"+
//    					"AND T.APPLY_USER = '"+user.getAccount()+"'\n" + 
    					"AND T.OEM_COMPANY_ID = "+user.getOemCompanyId()+"\n" + 
//    					"AND T.ORG_ID = "+user.getOrgId()+"\n" + 
    					"AND T.PURCHASE_TYPE = "+DicConstant.CGDDLX_05+"\n"+
    					"AND T.SALE_ORDER_ID = T1.ORDER_ID\n"+
    					"AND T.ORDER_STATUS = "+DicConstant.CGDDZT_01+"\n" + 
    					"ORDER BY T.CREATE_TIME DESC";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.PURCHASE_ID,\n" );
    	sql.append("       T.ORDER_NO,\n" );
    	sql.append("       T.PURCHASE_TYPE,\n" );
    	sql.append("       T.SUPPLIER_CODE,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("	   T.SUPPLIER_ID,");
    	sql.append("       T.SELECT_MONTH,\n" );
    	sql.append("       T1.ORDER_ID SALE_ORDER_ID,\n" );
    	sql.append("       T1.ORDER_NO SALE_ORDER_NO,\n" );
    	sql.append("       T.APPLY_DATE,\n" );
    	sql.append("       T.APPLY_USER\n" );
    	sql.append("  FROM PT_BU_PCH_ORDER T,PT_BU_SALE_ORDER T1");
    	bs = factory.select(sql.toString(), page);
		bs.setFieldDic("PURCHASE_TYPE", "CGDDLX");
		bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
		bs.setFieldUserID("APPLY_USER");
    	return bs;
    }
	/**
	 * 
	 * @date()2014年8月25日上午9:51:09
	 * @author Administrator
	 * @to_do:订单配件查询
	 * @param page
	 * @param user
	 * @param PURCHASE_ID
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet orderPartSearch(PageManager page, User user, String PURCHASE_ID, String supplierId) throws Exception
    {
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.DETAIL_ID,\n" );
    	sql.append("       T.PURCHASE_ID,\n" );
    	sql.append("       T.PART_ID,\n" );
    	sql.append("       T.PART_CODE,\n" );
    	sql.append("       T.PART_NAME,\n" );
    	sql.append("       T1.UNIT,\n" );
    	sql.append("       T1.MIN_UNIT,\n" );
    	sql.append("       T1.MIN_PACK,\n" );
    	sql.append("       T.PCH_COUNT,\n" );
    	sql.append("       P.PCH_PRICE,\n" );
    	sql.append("       P.PLAN_PRICE,\n" );
    	sql.append("       (P.PCH_PRICE*T.PCH_COUNT) AMOUNT,\n" );
    	sql.append("       T.DELIVERY_CYCLE,\n" );
    	sql.append("       T.REMARKS\n" );
    	sql.append("  FROM PT_BU_PCH_ORDER_DTL T, PT_BA_INFO T1, PT_BA_PART_SUPPLIER_RL P\n" );
    	sql.append(" WHERE T.PART_ID = T1.PART_ID\n" );
    	sql.append("   AND P.PART_ID = T1.PART_ID\n" );
    	sql.append("   AND T.PURCHASE_ID = "+PURCHASE_ID+"\n" );
    	sql.append("   AND P.SUPPLIER_ID = "+supplierId+" AND P.PART_IDENTIFY = "+DicConstant.YXBS_01+"");

    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("UNIT", "JLDW");
    	bs.setFieldDic("MIN_UNIT", "JLDW");
    	return bs;
    }
	/**
	 * 
	 * @date()2014年8月25日上午9:51:22
	 * @author Administrator
	 * @to_do:获取应拆分的配件信息
	 * @param PURCHASE_ID
	 * @return
	 * @throws Exception
	 */
	 public QuerySet getDelivery(String PURCHASE_ID) throws Exception
	    {
	    	QuerySet qs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT DISTINCT (T.DELIVERY_CYCLE) DELIVERY_CYCLE,\n" );
	    	sql.append("                T1.SELECT_MONTH,\n" );
	    	sql.append("                T1.ORDER_NO,\n" );
	    	sql.append("                T1.PURCHASE_TYPE,\n" );
	    	sql.append("                T1.SUPPLIER_ID,\n" );
	    	sql.append("                T1.SUPPLIER_NAME,\n" );
	    	sql.append("                T1.SUPPLIER_CODE,\n" );
	    	sql.append("                T1.ORDER_TYPE,\n" );
	    	sql.append("                T1.ORDER_STATUS,\n" );
	    	sql.append("                T2.IF_SUPLY ");
	    	sql.append("  FROM PT_BU_PCH_ORDER_DTL T, PT_BU_PCH_ORDER T1,PT_BA_INFO T2\n" );
	    	sql.append(" WHERE 1 = 1\n" );
	    	sql.append("   AND T.PURCHASE_ID = T1.PURCHASE_ID\n" );
	    	sql.append("   AND T.PART_ID = T2.PART_ID\n" );
	    	sql.append("   AND T.PURCHASE_ID = "+PURCHASE_ID+"");
	    	qs = factory.select(null, sql.toString());
	    	return qs;
	    }
	 /**
	  * 
	  * @date()2014年8月25日上午9:51:41
	  * @author Administrator
	  * @to_do:新增直发采购订单拆分单
	  * @param vo
	  * @return
	  * @throws Exception
	  */
	 public boolean insertSplitOrder(PtBuPchOrderSplitVO vo)
	            throws Exception {
	        return factory.insert(vo);
	    }
	 
	 
	 public QuerySet checkIfSQ(String SUPPLIER_ID) throws Exception
	    {
	    	QuerySet qs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT 1\n" );
	    	sql.append("  FROM PT_BA_SUPPLIER\n" );
	    	sql.append(" WHERE SUPPLIER_CODE LIKE '%SQGH%'\n" );
	    	sql.append("   AND SUPPLIER_ID = "+SUPPLIER_ID+" AND PART_IDENTIFY = "+DicConstant.YXBS_01+"");
	    	qs = factory.select(null, sql.toString());
	    	return qs;
	    }
	 /**
	  * 
	  * @date()2014年8月25日上午9:52:03
	  * @author Administrator
	  * @to_do:获取拆分单配件
	  * @param PURCHASE_ID
	  * @param DELIVERY_CYCLE
	  * @return
	  * @throws Exception
	  */
	 public QuerySet getSplitPart(String PURCHASE_ID,String SUPPLIER_ID) throws Exception
	    {
	    	QuerySet qs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT T.PART_ID,\n" );
	    	sql.append("       T.PART_NAME,\n" );
	    	sql.append("       T.PART_CODE,\n" );
	    	sql.append("       T.PCH_COUNT,\n" );
	    	sql.append("       T.PCH_COUNT * NVL(T1.PCH_PRICE,0) PCH_AMOUNT,\n" );
	    	sql.append("       T.PCH_COUNT * NVL(T2.PLAN_PRICE,0) PLAN_AMOUNT,\n" );
	    	sql.append("       NVL(T1.PCH_PRICE,0) PCH_PRICE,\n" );
	    	sql.append("       NVL(T2.PLAN_PRICE,0)PLAN_PRICE,\n" );
	    	sql.append("       T2.IF_SUPLY,\n" );
	    	sql.append("       T.DETAIL_ID,\n" );
	    	sql.append("       T.REMARKS\n" );
	    	sql.append("  FROM PT_BU_PCH_ORDER_DTL T, PT_BA_PART_SUPPLIER_RL T1, PT_BA_INFO T2\n" );
	    	sql.append(" WHERE T.PART_ID = T1.PART_ID\n" );
	    	sql.append("   AND T.PART_ID = T2.PART_ID\n" );
	    	sql.append("   AND T.PURCHASE_ID = "+PURCHASE_ID+"\n" );
	    	sql.append("   AND T1.SUPPLIER_ID = "+SUPPLIER_ID+" AND T1.PART_IDENTIFY = "+DicConstant.YXBS_01+"");
	    	qs = factory.select(null, sql.toString());
	    	return qs;
	    }
	 /**
	  * 
	  * @date()2014年8月25日上午9:52:15
	  * @author Administrator
	  * @to_do:获取计划价格
	  * @param PART_ID
	  * @return
	  * @throws Exception
	  */
	 public QuerySet getPlanPrice(String PART_ID) throws Exception
	    {
	    	QuerySet qs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT PLAN_PRICE FROM PT_BA_INFO WHERE PART_ID = "+PART_ID+"");
	    	qs = factory.select(null, sql.toString());
	    	return qs;
	    }
	 /**
	  * 
	  * @date()2014年8月25日上午9:52:27
	  * @author Administrator
	  * @to_do:如果配件为未指定供应商，则插入待定供应商ID CODE NAME
	  * @param sup_no
	  * @return
	  * @throws Exception
	  */
	 public QuerySet getTmpSup(String sup_no) throws Exception
	    {
	    	QuerySet qs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT T.SUPPLIER_ID, T.SUPPLIER_NAME, T.SUPPLIER_CODE\n" );
	    	sql.append("  FROM PT_BA_SUPPLIER T\n" );
	    	sql.append(" WHERE T.SUPPLIER_CODE = '"+sup_no+"' AND T.PART_IDENTIFY = "+DicConstant.YXBS_01+"");
	    	qs = factory.select(null, sql.toString());
	    	return qs;
	    }
	 /**
	  * 
	  * @date()2014年8月25日上午9:53:14
	  * @author Administrator
	  * @to_do:插入拆分单配件信息
	  * @param vo
	  * @return
	  * @throws Exception
	  */
	 public boolean insertSplitOrderPart(PtBuPchOrderSplitDtlVO vo)
	            throws Exception {
	        return factory.insert(vo);
	    }
	 /**
	  * 
	  * @date()2014年8月25日上午10:05:12
	  * @author Administrator
	  * @to_do:更改拆分单采购数量 计划采购总金额 采购总金额
	  * @param vo
	  * @return
	  * @throws Exception
	  */
	 public boolean updatePurchaseOrderSplit(PtBuPchOrderSplitVO vo)
	    		throws Exception
	    {
	    	return factory.update(vo);
	    }
	 /**
	  * 
	  * @date()2014年8月25日上午9:55:27
	  * @author Administrator
	  * @to_do:更改主表信息
	  * @param vo
	  * @return
	  * @throws Exception
	  */
	 public boolean updatePurchaseOrder(PtBuPchOrderVO vo)
	    		throws Exception
	    {
	    	return factory.update(vo);
	    }

	 public boolean updatePurchaseOrderDtl(PtBuPchOrderDtlVO vo)
	    		throws Exception
	    {
	    	return factory.update(vo);
	    }
	 
	 public QuerySet checkPrice(String purchaseId,String supplierId) throws Exception
	    {
	    	QuerySet qs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT TO_CHAR(wm_concat(T.PART_CODE)) CODES\n" );
	    	sql.append("FROM PT_BU_PCH_ORDER_DTL T,PT_BA_PART_SUPPLIER_RL T1\n" );
	    	sql.append("WHERE T.PART_ID = T1.PART_ID\n" );
	    	sql.append("AND NVL(T1.PCH_PRICE,0)=0\n" );
	    	sql.append("AND T.PURCHASE_ID = "+purchaseId+"\n" );
	    	sql.append("AND T1.SUPPLIER_ID = "+supplierId+"");
	    	qs = factory.select(null, sql.toString());
	    	return qs;
	    }
	 public QuerySet checkPlan(String purchaseId) throws Exception
	    {
	    	QuerySet qs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT TO_CHAR(wm_concat(T.PART_CODE)) CODES\n" );
	    	sql.append("FROM PT_BU_PCH_ORDER_DTL T,PT_BA_INFO T1\n" );
	    	sql.append("WHERE T.PART_ID = T1.PART_ID\n" );
	    	sql.append("AND NVL(T1.PLAN_PRICE,0)=0\n" );
	    	sql.append("AND T.PURCHASE_ID = "+purchaseId+"\n" );
	    	qs = factory.select(null, sql.toString());
	    	return qs;
	    }
	 public QuerySet checkPos(String purchaseId) throws Exception
	    {
	    	QuerySet qs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT TO_CHAR(wm_concat(T.PART_CODE)) CODES\n" );
	    	sql.append("  FROM PT_BU_PCH_ORDER_DTL T\n" );
	    	sql.append(" WHERE T.PURCHASE_ID = "+purchaseId+"\n" );
	    	sql.append("   AND NOT EXISTS (SELECT 1\n" );
	    	sql.append("          FROM PT_BA_WAREHOUSE_PART_RL T1\n" );
	    	sql.append("         WHERE T.PART_ID = T1.PART_ID AND T1.IF_DEFAULT = "+DicConstant.SF_01+")");
	    	qs = factory.select(null, sql.toString());
	    	return qs;
	    }
}
