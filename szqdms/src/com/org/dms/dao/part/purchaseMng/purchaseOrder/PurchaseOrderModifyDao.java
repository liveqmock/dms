package com.org.dms.dao.part.purchaseMng.purchaseOrder;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuPchOrderSplitDtlVO;
import com.org.dms.vo.part.PtBuPchOrderSplitVO;
import com.org.dms.vo.part.PtBuPchOrderVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class PurchaseOrderModifyDao extends BaseDAO{
	
	public  static final PurchaseOrderModifyDao getInstance(ActionContext atx)
    {
		PurchaseOrderModifyDao dao = new PurchaseOrderModifyDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	
	 public BaseResultSet orderSearch(PageManager page, User user, String conditions) throws Exception
	    {
	    	String wheres = conditions;
	    	wheres +="AND T.ORDER_STATUS ="+DicConstant.CGDDZT_03+"\n" +
	    			"   AND T1.SPLIT_ID = T.SPLIT_ID\n" + 
					"   AND T.STATUS = "+DicConstant.YXBS_01+"\n" + 
					"   AND T.IF_ON_TIME = "+DicConstant.SF_02+"\n" + 
					"   AND T.ORG_ID = "+user.getOrgId()+"\n" + 
					"   AND T.APPLY_USER ='"+user.getAccount()+"'\n";
	        page.setFilter(wheres);
	    	BaseResultSet bs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT DISTINCT T.SPLIT_NO,\n" );
	    	sql.append("       T.DELIVERY_CYCLE,\n" );
	    	sql.append("       T.SPLIT_ID,\n" );
	    	sql.append("       T.SELECT_MONTH,\n" );
	    	sql.append("       T.PURCHASE_TYPE,\n" );
	    	sql.append("       T.ORDER_TYPE,\n" );
	    	sql.append("       T.IF_ON_TIME,\n");
	    	sql.append("       T.CONFIRM_ADVISE,\n");
	    	sql.append("       T.SUPPLIER_ID,\n" );
	    	sql.append("       T.SUPPLIER_NAME,\n" );
	    	sql.append("       T.SUPPLIER_CODE,\n" );
	    	sql.append("       T.SUPPLIER_DATE,\n" );
	    	sql.append("       T.APPLY_USER,\n" );
	    	sql.append("       T.APPLY_DATE,\n" );
	    	sql.append("       T.REPUIREMENT_TIME\n" );
	    	sql.append("  FROM PT_BU_PCH_ORDER_SPLIT T,PT_BU_PCH_ORDER_SPLIT_DTL T1");
	    	bs = factory.select(sql.toString(), page);
			bs.setFieldDic("PURCHASE_TYPE", "CGDDLX");
			bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
			bs.setFieldDateFormat("SUPPLIER_DATE", "yyyy-MM-dd");
			bs.setFieldDateFormat("REPUIREMENT_TIME", "yyyy-MM-dd");
			bs.setFieldUserID("APPLY_USER");
	    	return bs;
	    }
	 
	 
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
	    	sql.append("       T2.DELIVERY_CYCLE,\n" );
	    	sql.append("       T.REMARKS\n" );
	    	sql.append("  FROM PT_BU_PCH_ORDER_SPLIT_DTL T, PT_BA_INFO T1,PT_BU_PCH_ORDER_SPLIT T2\n" );
	    	sql.append(" WHERE T.PART_ID = T1.PART_ID");
	    	sql.append("	AND T.SPLIT_ID = T2.SPLIT_ID");
	    	sql.append("	AND T.SPLIT_ID = "+SPLIT_ID+"");
	    	bs = factory.select(sql.toString(), page);
	    	bs.setFieldDic("UNIT", "JLDW");
	    	bs.setFieldDic("MIN_UNIT", "JLDW");
	    	return bs;
	    }
	 
	 public BaseResultSet searchPart(PageManager page, User user, String conditions,String SUPPLIER_ID, String SPLIT_ID,String DELIVERY_CYCLE) throws Exception {
	        String wheres = conditions;
	        wheres +="AND T.PART_ID = T1.PART_ID\n" +
					"AND T.PART_ID = T2.PART_ID\n" + 
					"AND T1.SUPPLIER_ID = T3.SUPPLIER_ID\n" + 
					"AND T.PART_TYPE != "+DicConstant.PJLB_03+"\n" + 
					"AND T.PART_STATUS = "+DicConstant.PJZT_01+"\n" + 
					"AND T1.SUPPLIER_ID = "+SUPPLIER_ID+" AND T1.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n" + 
					"AND T2.USER_ACCOUNT = '"+user.getAccount()+"'\n" + 
					"AND NOT EXISTS (SELECT 1\n" + 
					"       FROM PT_BU_PCH_ORDER_SPLIT_DTL T0\n" + 
					"      WHERE T0.SPLIT_ID = "+SPLIT_ID+"\n" + 
					"        AND T.PART_ID = T0.PART_ID)  ORDER BY T.PART_CODE";
	        page.setFilter(wheres);
	        BaseResultSet bs = null;
	        StringBuffer sql= new StringBuffer();
	        sql.append("SELECT T.PART_ID,\n" );
	        sql.append("       T.PART_CODE,\n" );
	        sql.append("       T.PART_NAME,\n" );
	        sql.append("       T.UNIT,\n" );
	        sql.append("       T.MIN_PACK,\n" );
	        sql.append("       T1.APPLY_CYCLE,\n" );
	        sql.append("       T1.PCH_PRICE\n" );
	        sql.append("  FROM PT_BA_INFO T, PT_BA_PART_SUPPLIER_RL T1, PT_BA_PCH_ATTRIBUTE T2,PT_BA_SUPPLIER T3");
	        bs = factory.select(sql.toString(), page);
	        bs.setFieldDic("UNIT", "JLDW");
	        return bs;
	    }
	 /**
	  * 
	  * @date()2014年7月17日上午11:01:44
	  * @author Administrator
	  * @to_do:获取原拆分单采购总数，采购总金额，计划总金额，原采购订单采购总数，采购总金额，计划总金额
	  * @param SPLIT_ID
	  * @return
	  * @throws Exception
	  */
	 public QuerySet getOldAmount(String SPLIT_ID) throws Exception
	    {
	    	QuerySet qs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT T.PLAN_AMOUNT      S_PLAN_AMOUNT,\n" );
	    	sql.append("       T.PURCHASE_AMOUNT  S_PURCHASE_AMOUNT,\n" );
	    	sql.append("       T.PURCHASE_COUNT   S_PURCHASE_COUNT,\n" );
	    	sql.append("       T1.PURCHASE_ID,\n" );
	    	sql.append("       T1.PLAN_AMOUNT,\n" );
	    	sql.append("       T1.PURCHASE_AMOUNT,\n" );
	    	sql.append("       T1.PURCHASE_COUNT\n" );
	    	sql.append("  FROM PT_BU_PCH_ORDER_SPLIT T, PT_BU_PCH_ORDER T1\n" );
	    	sql.append(" WHERE 1 = 1\n" );
	    	sql.append("   AND T.PURCHASE_ID = T1.PURCHASE_ID\n" );
	    	sql.append("   AND T.SPLIT_ID = "+SPLIT_ID+"");
	    	qs = factory.select(null, sql.toString());
	    	return qs;
	    }
	 /**
	  * 
	  * @date()2014年7月17日上午11:02:37
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
	    	sql.append("SELECT NVL(PLAN_PRICE,0) PLAN_PRICE FROM PT_BA_INFO WHERE PART_ID = "+PART_ID+"");
	    	qs = factory.select(null, sql.toString());
	    	return qs;
	    }
	 public boolean purchaseOrderSplitPartDelete(String SPLIT_ID ) throws Exception
	    {
			StringBuffer sql= new StringBuffer();
			sql.append("DELETE FROM PT_BU_PCH_ORDER_SPLIT_DTL WHERE SPLIT_ID = "+SPLIT_ID+"");
	    	return factory.update(sql.toString(), null);
	    }
	 /**
	  * 
	  * @date()2014年7月17日上午10:51:22
	  * @author Administrator
	  * @to_do:配件采购订单调整新增配件
	  * @param vo
	  * @return
	  * @throws Exception
	  */
	 public boolean insertPart(PtBuPchOrderSplitDtlVO vo)
	            throws Exception {
	        return factory.insert(vo);
	    }
	 public boolean delParts(String DETAIL_ID ) throws Exception
	    {
			StringBuffer sql= new StringBuffer();
			sql.append("DELETE FROM PT_BU_PCH_ORDER_SPLIT_DTL WHERE DETAIL_ID = "+DETAIL_ID+"");
	    	return factory.update(sql.toString(), null);
	    }
	 public boolean updatePurchaseOrderSplit(PtBuPchOrderSplitVO vo)
	            throws Exception {
	        return factory.update(vo);
	    }
	 public boolean updatePurchaseOrder(PtBuPchOrderVO vo)
	            throws Exception {
	        return factory.update(vo);
	    }
	 public QuerySet getOldSplit(String DETAIL_ID) throws Exception
	    {
	    	QuerySet qs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT T.SPLIT_ID,\n" );
	    	sql.append("       T.PURCHASE_AMOUNT - T1.PCH_AMOUNT S_PCH_AMOUNT,\n" );
	    	sql.append("       T.PURCHASE_COUNT - T1.PCH_COUNT S_PCH_COUNT,\n" );
	    	sql.append("       T.PLAN_AMOUNT - T1.PLAN_AMOUNT S_PLAN_AMOUNT\n" );
	    	sql.append("  FROM PT_BU_PCH_ORDER_SPLIT T, PT_BU_PCH_ORDER_SPLIT_DTL T1\n" );
	    	sql.append(" WHERE T.SPLIT_ID = T1.SPLIT_ID");
	    	sql.append("   AND T1.DETAIL_ID = "+DETAIL_ID+"");
	    	qs = factory.select(null, sql.toString());
	    	return qs;
	    }
	 public QuerySet getOldPurchase(String DETAIL_ID) throws Exception
	    {
	    	QuerySet qs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT T2.PURCHASE_ID,\n" );
	    	sql.append("       T2.PURCHASE_AMOUNT - T1.PCH_AMOUNT P_PURCHASE_AMOUNT,\n" );
	    	sql.append("       T2.PURCHASE_COUNT - T1.PCH_COUNT P_PURCHASE_COUNT,\n" );
	    	sql.append("       T2.PLAN_AMOUNT - T1.PLAN_AMOUNT P_PLAN_AMOUNT\n" );
	    	sql.append("  FROM PT_BU_PCH_ORDER_SPLIT     T,\n" );
	    	sql.append("       PT_BU_PCH_ORDER_SPLIT_DTL T1,\n" );
	    	sql.append("       PT_BU_PCH_ORDER           T2\n" );
	    	sql.append(" WHERE T.SPLIT_ID = T1.SPLIT_ID\n" );
	    	sql.append("   AND T.PURCHASE_ID = T2.PURCHASE_ID\n" );
	    	sql.append("   AND T1.DETAIL_ID = "+DETAIL_ID+"");
	    	qs = factory.select(null, sql.toString());
	    	return qs;
	    }
	 
    public QuerySet getOld(String SPLIT_ID,String DETAIL_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.PCH_COUNT, T.PCH_PRICE, T.PCH_AMOUNT, T.PLAN_AMOUNT, NVL(T.PLAN_PRICE,0) PLAN_PRICE\n" );
    	sql.append("  FROM PT_BU_PCH_ORDER_SPLIT_DTL T\n" );
    	sql.append("  WHERE T.SPLIT_ID ="+SPLIT_ID+"\n" );
    	sql.append("  AND T.DETAIL_ID ="+DETAIL_ID+"");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
    public QuerySet getPch(String SPLIT_ID,String PART_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.PURCHASE_ID, T1.DETAIL_ID\n" );
    	sql.append("  FROM PT_BU_PCH_ORDER T, PT_BU_PCH_ORDER_DTL T1, PT_BU_PCH_ORDER_SPLIT T2\n" );
    	sql.append(" WHERE T.PURCHASE_ID = T1.PURCHASE_ID\n" );
    	sql.append("   AND T2.PURCHASE_ID = T.PURCHASE_ID\n" );
    	sql.append("   AND T1.PART_ID = "+PART_ID+"\n" );
    	sql.append("   AND T2.SPLIT_ID = "+SPLIT_ID+"");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
    public void updateSplAmount(String SPLIT_ID,String PLAN_AMOUNT,String NEW_PLAN_AMOUNT,String PCH_AMOUNT,String NEW_PCH_AMOUNT,String PCH_COUNT,String COUNT,User user) throws Exception {
    	StringBuffer sql = new StringBuffer();
    	sql.append("UPDATE PT_BU_PCH_ORDER_SPLIT SET PURCHASE_AMOUNT=PURCHASE_AMOUNT-"+PCH_AMOUNT+"+"+NEW_PCH_AMOUNT+",PLAN_AMOUNT = PLAN_AMOUNT-"+PLAN_AMOUNT+"+"+NEW_PLAN_AMOUNT+",PURCHASE_COUNT=PURCHASE_COUNT-"+PCH_COUNT+"+"+COUNT+",UPDATE_USER='"+user.getAccount()+"',UPDATE_TIME=SYSDATE WHERE SPLIT_ID = "+SPLIT_ID+"\n");
       factory.update(sql.toString(),null);
    }
    public void updateSplOrderPart(String DETAIL_ID,String SPLIT_ID,String COUNT,String REMARKS,User user) throws Exception {
    	StringBuffer sql = new StringBuffer();
    	sql.append("UPDATE PT_BU_PCH_ORDER_SPLIT_DTL SET REMARKS='" + REMARKS + "',PCH_COUNT="+COUNT+",PCH_AMOUNT= PCH_PRICE*"+COUNT+",PLAN_AMOUNT= NVL(PLAN_PRICE,0)*"+COUNT+",UPDATE_USER='"+user.getAccount()+"',UPDATE_TIME=SYSDATE WHERE DETAIL_ID ="+DETAIL_ID+" AND SPLIT_ID ="+SPLIT_ID+"\n");
       factory.update(sql.toString(),null);
    }
    public void updatePchOrderPart(String DETAIL_ID,String SPLIT_ID,String COUNT,String REMARKS,User user) throws Exception {
    	StringBuffer sql = new StringBuffer();
    	sql.append("UPDATE PT_BU_PCH_ORDER_SPLIT_DTL SET REMARKS='" + REMARKS + "',PCH_COUNT="+COUNT+",PCH_AMOUNT= PCH_PRICE*"+COUNT+",PLAN_AMOUNT= NVL(PLAN_PRICE,0)*"+COUNT+",UPDATE_USER='"+user.getAccount()+"',UPDATE_TIME=SYSDATE WHERE DETAIL_ID ="+DETAIL_ID+" AND SPLIT_ID ="+SPLIT_ID+"\n");
       factory.update(sql.toString(),null);
    }
    public void updatePurAmount(String PURCHASE_ID,String PLAN_AMOUNT,String NEW_PLAN_AMOUNT,String PCH_AMOUNT,String NEW_PCH_AMOUNT,String PCH_COUNT,String COUNT,User user) throws Exception {
    	StringBuffer sql = new StringBuffer();
    	sql.append("UPDATE PT_BU_PCH_ORDER SET PURCHASE_AMOUNT=PURCHASE_AMOUNT-"+PCH_AMOUNT+"+"+NEW_PCH_AMOUNT+",PLAN_AMOUNT = PLAN_AMOUNT-"+PLAN_AMOUNT+"+"+NEW_PLAN_AMOUNT+",PURCHASE_COUNT=PURCHASE_COUNT-"+PCH_COUNT+"+"+COUNT+",UPDATE_USER='"+user.getAccount()+"',UPDATE_TIME=SYSDATE WHERE PURCHASE_ID = "+PURCHASE_ID+"\n");
       factory.update(sql.toString(),null);
    }
    
}
