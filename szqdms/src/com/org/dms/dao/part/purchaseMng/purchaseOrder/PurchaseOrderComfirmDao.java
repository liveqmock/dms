package com.org.dms.dao.part.purchaseMng.purchaseOrder;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuPchOrderSplitVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class PurchaseOrderComfirmDao extends BaseDAO{
	public  static final PurchaseOrderComfirmDao getInstance(ActionContext atx)
    {
		PurchaseOrderComfirmDao dao = new PurchaseOrderComfirmDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	/**
	 * 
	 * @date()2014年7月16日下午2:56:52
	 * @author Administrator
	 * @to_do:订单确认查询
	 * @param page
	 * @param user
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet orderSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres +="AND T.SUPPLIER_ID = T1.SUPPLIER_ID\n" +
    					"   AND T1.ORG_ID = "+user.getOrgId()+"\n" + 
    					//"   AND T.COMPANY_ID = "+user.getCompanyId()+"\n" + 
    					"   AND T.OEM_COMPANY_ID = "+user.getOemCompanyId()+"\n" + 
    					"   AND T.ORDER_STATUS = "+DicConstant.CGDDZT_02+" AND T1.PART_IDENTIFY = "+DicConstant.YXBS_01+""+
    					"	ORDER BY SPLIT_ID DESC";
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
    	sql.append("       T.REPUIREMENT_TIME\n" );
    	sql.append("  FROM PT_BU_PCH_ORDER_SPLIT T, PT_BA_SUPPLIER T1");
    	bs = factory.select(sql.toString(), page);
		bs.setFieldDic("PURCHASE_TYPE", DicConstant.CGDDLX);
		bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("REPUIREMENT_TIME", "yyyy-MM-dd");
		bs.setFieldUserID("APPLY_USER");
    	return bs;
    }
	
	public QuerySet download(User user, String SPLIT_ID) throws Exception
    {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.DETAIL_ID,\n" );
    	sql.append("       T.SPLIT_ID,\n" );
    	sql.append("       (SELECT SPLIT_NO FROM PT_BU_PCH_ORDER_SPLIT WHERE SPLIT_ID=T.SPLIT_ID) SPLIT_NO,\n" );
    	sql.append("       T.PART_ID,\n" );
    	sql.append("       T.PART_CODE,\n" );
    	sql.append("       T.PART_NAME,\n" );
    	sql.append("       (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = T1.UNIT) UNIT,\n" );
    	sql.append("       T1.MIN_PACK||'/'||(SELECT DIC_VALUE FROM DIC_TREE WHERE ID = T1.MIN_UNIT) MIN_PACK,\n" );
    	sql.append("       T1.MIN_UNIT,");
    	sql.append("       T.PCH_COUNT,\n" );
    	sql.append("       T.PCH_PRICE,\n" );
    	sql.append("       T.PCH_AMOUNT,\n" );
    	sql.append("       T2.DELIVERY_CYCLE,\n" );
    	sql.append("       T.REMARKS\n" );
    	sql.append("  FROM PT_BU_PCH_ORDER_SPLIT_DTL T, PT_BA_INFO T1, PT_BU_PCH_ORDER_SPLIT T2\n" );
    	sql.append(" WHERE T.PART_ID = T1.PART_ID\n" );
    	sql.append("   AND T.SPLIT_ID = T2.SPLIT_ID\n" );
    	sql.append("	AND T.SPLIT_ID = "+SPLIT_ID+"");
    	return factory.select(null,sql.toString());
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
    	sql.append("  FROM PT_BU_PCH_ORDER_SPLIT_DTL T, PT_BA_INFO T1, PT_BU_PCH_ORDER_SPLIT T2\n" );
    	sql.append(" WHERE T.PART_ID = T1.PART_ID\n" );
    	sql.append("   AND T.SPLIT_ID = T2.SPLIT_ID\n" );
    	sql.append("	AND T.SPLIT_ID = "+SPLIT_ID+"");
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("UNIT", "JLDW");
    	bs.setFieldDic("MIN_UNIT", "JLDW");
    	return bs;
    }
	public boolean updatePurchaseOrderSplit(PtBuPchOrderSplitVO vo)
    		throws Exception
    {
    	return factory.update(vo);
    }
	
	public QuerySet getPchType(String SPLIT_ID) throws Exception{
    	QuerySet qs = null;StringBuffer sql= new StringBuffer();
    	sql.append("SELECT PURCHASE_TYPE FROM PT_BU_PCH_ORDER_SPLIT WHERE SPLIT_ID = "+SPLIT_ID+"");
        qs = factory.select(null, sql.toString());
    	return qs;
	}
	public boolean closeSplit(String SPLIT_ID,User user) throws Exception
    {
        StringBuffer sql= new StringBuffer();
        sql.append("UPDATE PT_BU_PCH_ORDER_SPLIT SET ORDER_STATUS = "+DicConstant.CGDDZT_05+",UPDATE_TIME=SYSDATE,UPDATE_USER = "+user.getAccount()+" WHERE SPLIT_ID = "+SPLIT_ID+"");
        return factory.update(sql.toString(), null);
    }
	public boolean closePurchase(String SPLIT_ID,User user) throws Exception
    {
        StringBuffer sql= new StringBuffer();
        sql.append("UPDATE PT_BU_PCH_ORDER SET ORDER_STATUS = "+DicConstant.CGDDZT_05+",UPDATE_TIME=SYSDATE,UPDATE_USER = "+user.getAccount()+" WHERE PURCHASE_ID = (SELECT PURCHASE_ID FROM PT_BU_PCH_ORDER_SPLIT WHERE SPLIT_ID = "+SPLIT_ID+")");
        return factory.update(sql.toString(), null);
    }
	public QuerySet getNewPurchaseId() throws Exception{
    	QuerySet qs = null;StringBuffer sql= new StringBuffer();
    	sql.append("SELECT F_GETID() NEW_ID FROM DUAL");
        qs = factory.select(null, sql.toString());
    	return qs;
	}
	public boolean createNewPurchase(String NEW_ID, String SPLIT_ID) throws Exception {
		StringBuffer sql= new StringBuffer();
		sql.append("INSERT INTO PT_BU_PCH_ORDER\n" );
		sql.append("  (PURCHASE_ID,\n" );
		sql.append("   SELECT_MONTH,\n" );
		sql.append("   PURCHASE_TYPE,\n" );
		sql.append("   APPLY_USER,\n" );
		sql.append("   APPLY_DATE,\n" );
		sql.append("   ORDER_STATUS,\n" );
		sql.append("   COMPANY_ID,\n" );
		sql.append("   ORG_ID,\n" );
		sql.append("   CREATE_USER,\n" );
		sql.append("   CREATE_TIME,\n" );
		sql.append("   STATUS,\n" );
		sql.append("   OEM_COMPANY_ID,\n" );
		sql.append("   SALE_ORDER_ID)\n" );
		sql.append("  SELECT '"+NEW_ID+"',\n" );
		sql.append("         T.SELECT_MONTH,\n" );
		sql.append("         T.PURCHASE_TYPE,\n" );
		sql.append("         T.APPLY_USER,\n" );
		sql.append("         T.APPLY_DATE,\n" );
		sql.append("         '"+DicConstant.CGDDZT_01+"',\n" );
		sql.append("         T.COMPANY_ID,\n" );
		sql.append("         T.ORG_ID,\n" );
		sql.append("         T.CREATE_USER,\n" );
		sql.append("         T.CREATE_TIME,\n" );
		sql.append("         T.STATUS,\n" );
		sql.append("         T.OEM_COMPANY_ID,\n" );
		sql.append("         T.SALE_ORDER_ID\n" );
		sql.append("    FROM PT_BU_PCH_ORDER T\n" );
		sql.append("   WHERE T.PURCHASE_ID = (SELECT PURCHASE_ID FROM PT_BU_PCH_ORDER_SPLIT WHERE SPLIT_ID = "+SPLIT_ID+")");
        return factory.update(sql.toString(), null);
    }

	public boolean createNewPurchaseDtl(String NEW_ID, String SPLIT_ID) throws Exception {
		StringBuffer sql= new StringBuffer();
		sql.append("INSERT INTO PT_BU_PCH_ORDER_DTL\n" );
		sql.append("  (DETAIL_ID, PURCHASE_ID, PART_ID, PART_NAME, PART_CODE, PCH_COUNT)\n" );
		sql.append("  SELECT F_GETID(), '"+NEW_ID+"', PART_ID, PART_NAME, PART_CODE, PCH_COUNT\n" );
		sql.append("    FROM PT_BU_PCH_ORDER_DTL\n" );
		sql.append("   WHERE PURCHASE_ID =\n" );
		sql.append("         (SELECT PURCHASE_ID FROM PT_BU_PCH_ORDER_SPLIT WHERE SPLIT_ID = "+SPLIT_ID+")");
		return factory.update(sql.toString(), null);
    }
}
