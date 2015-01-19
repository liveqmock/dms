package com.org.dms.dao.part.purchaseMng.purchaseOrder;

import com.org.dms.common.DicConstant;
import com.org.frameImpl.Constant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class PurchaseOrderDetailDao extends BaseDAO{
	public  static final PurchaseOrderDetailDao getInstance(ActionContext atx)
    {
		PurchaseOrderDetailDao dao = new PurchaseOrderDetailDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	
	public BaseResultSet purchaseOrderInfoSearch(PageManager page, User user, String conditions,String SPLIT_ID) throws Exception
    {
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.SPLIT_ID,\n" );
    	sql.append("       T.SPLIT_NO,\n" );
    	sql.append("       T.SELECT_MONTH,\n" );
    	sql.append("       T.PURCHASE_TYPE,\n" );
    	sql.append("       T.SUPPLIER_ID,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("       T.SUPPLIER_CODE,\n" );
    	sql.append("       T.REPUIREMENT_TIME\n" );
    	sql.append("  FROM PT_BU_PCH_ORDER_SPLIT T\n" );
    	sql.append("  WHERE T.SPLIT_ID = "+SPLIT_ID+"");
    	bs = factory.select(sql.toString(), page);
		bs.setFieldDic("PURCHASE_TYPE", "CGDDLX");
		bs.setFieldDateFormat("REPUIREMENT_TIME", "yyyy-MM-dd");
    	return bs;
    }
	
	public BaseResultSet purchaseOrderPartSearch(PageManager page, User user, String SPLIT_ID) throws Exception
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
    	sql.append("       NVL(T.STORAGE_COUNT,0) STORAGE_COUNT,\n" );
    	sql.append("       NVL(T.STORAGE_COUNT,0)*NVL(T.PCH_PRICE,0 )STORAGE_AMOUNT,\n" );
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
	
	
	/**
	 * 
	 * @Title: orderSearch
	 * @Description: 采购订单查询Table
	 * @param page
	 * @param user
	 * @param conditions
	 * @return
	 * @throws Exception
	 * @return: BaseResultSet
	 */
	public BaseResultSet orderSearch(PageManager page, User user, String conditions) throws Exception
    {
		
		// 添加配件代码配件名称查询
		if(conditions.indexOf("PART_CODE") != -1 || conditions.indexOf("PART_NAME") != -1){
			conditions += " AND EXISTS (\n" +
							"    SELECT 1 FROM PT_BU_PCH_ORDER_SPLIT_DTL D WHERE D.SPLIT_ID = T.SPLIT_ID";
			if(conditions.indexOf("PART_NAME") != -1){
				String partNameSql = conditions.substring(conditions.indexOf("PART_NAME"), conditions.indexOf("'", conditions.indexOf("PART_NAME") + 15) + 1);
				conditions = conditions.replace(" AND "+partNameSql, "");
				conditions +=" AND " + partNameSql;
			}
			if(conditions.indexOf("PART_CODE") != -1){
				String partNameSql = conditions.substring(conditions.indexOf("PART_CODE"), conditions.indexOf("'", conditions.indexOf("PART_CODE") + 15) + 1);
				conditions = conditions.replace(" AND "+partNameSql, "");
				conditions +=" AND " + partNameSql;
			}
			conditions += " ) ";
		}
		
		// 判断用户是否为采供科，如果为采供科则只可查询到自己创建的采供订单
		if(user.getOrgCode().equals("XS10905")){
			conditions += " AND T.APPLY_USER = '" + user.getAccount() + "'";
		}
		
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.SPLIT_ID,\n" );
    	sql.append("       T.SPLIT_NO,\n" );
    	sql.append("       T.PLAN_DISTRIBUTION,\n" );
    	sql.append("       T.ORDER_STATUS,\n" );
    	sql.append("       T.PURCHASE_TYPE,\n" );
    	
    	// 添加采购金额，已开票金额 by fuxiao 2014-12-19
    	sql.append( "T.PURCHASE_AMOUNT,\n" +
    					"       (\n" + 
    					"          SELECT NVL(SUM(D.PCH_AMOUNT),0) FROM PT_BU_STOCK_IN I, PT_BU_STOCK_IN_DTL D\n" + 
    					"          WHERE I.IN_ID = D.IN_ID\n" + 
    					"                AND I.ORDER_ID = T.SPLIT_ID\n" + 
    					"                AND i.print_status = 201702\n" + 
    					"       ) PRINT_AMOUNT,");
    	
    	sql.append("       T.SUPPLIER_CODE,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("	   T.SUPPLIER_ID,");
    	sql.append("       T.SELECT_MONTH,\n" );
    	sql.append("       T.CLOSE_DATE,\n" );
    	sql.append("       T.APPLY_DATE,\n" );
    	sql.append("       T.APPLY_USER\n" );
    	sql.append("  FROM PT_BU_PCH_ORDER_SPLIT T \n" + " WHERE T.STATUS = "+DicConstant.YXBS_01+"\n" +
				"AND T.ORDER_STATUS <> "+DicConstant.CGDDZT_01+ " AND " +  conditions + " ORDER BY T.CREATE_TIME DESC");
    	bs = factory.select(sql.toString(), page);
		bs.setFieldDic("PURCHASE_TYPE", "CGDDLX");
		bs.setFieldDic("ORDER_STATUS", "CGDDZT");
		bs.setFieldDateFormat("CLOSE_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
		bs.setFieldUserID("APPLY_USER");
    	return bs;
    }
	
	public BaseResultSet partSearch(PageManager page, User user, String conditions) throws Exception
    {

    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.DETAIL_ID,\n" );
    	sql.append("       T.SPLIT_ID,\n" );
    	sql.append("       T.PART_ID,\n" );
    	sql.append("       T1.PART_CODE,\n" );
    	sql.append("       T1.PART_NAME,\n" );
    	sql.append("       T1.UNIT,\n" );
    	sql.append("       T1.MIN_PACK,\n" );
    	sql.append("       T1.MIN_UNIT,");
    	sql.append("       T.PCH_COUNT,\n" );
    	sql.append("       NVL(T.PCH_PRICE,0) PCH_PRICE,\n" );
    	sql.append("       NVL(T.PCH_AMOUNT,0) PCH_AMOUNT,\n" );
    	sql.append("       NVL(T.STORAGE_COUNT,0) STORAGE_COUNT,\n" );
    	
    	// 添加开票数量： by fuxiao 2014-12-19
    	sql.append( "(\n" +
    					"   SELECT NVL(SUM(D.IN_AMOUNT),0) FROM PT_BU_STOCK_IN I, PT_BU_STOCK_IN_DTL D\n" + 
    					"   WHERE I.IN_ID = D.IN_ID\n" + 
    					"         AND I.ORDER_ID = T.SPLIT_ID\n" + 
    					"         AND D.PART_ID = T.PART_ID\n" + 
    					"         AND i.print_status = 201702\n" + 
    					") PRINT_COUNT," );
    	
    	sql.append("       T2.DELIVERY_CYCLE,\n" );
    	sql.append("       T.REMARKS\n" );
    	sql.append("  FROM PT_BU_PCH_ORDER_SPLIT_DTL T, PT_BA_INFO T1, PT_BU_PCH_ORDER_SPLIT T2\n" );
    	sql.append(" WHERE T.PART_ID = T1.PART_ID\n" );
    	sql.append("   AND T.SPLIT_ID = T2.SPLIT_ID AND " + conditions );
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("UNIT", "JLDW");
    	bs.setFieldDic("MIN_UNIT", "JLDW");
    	return bs;
    
    }

}
