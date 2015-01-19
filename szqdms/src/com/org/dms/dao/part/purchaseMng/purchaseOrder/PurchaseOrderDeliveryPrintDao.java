package com.org.dms.dao.part.purchaseMng.purchaseOrder;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class PurchaseOrderDeliveryPrintDao extends BaseDAO{
	public  static final PurchaseOrderDeliveryPrintDao getInstance(ActionContext atx)
    {
		PurchaseOrderDeliveryPrintDao dao = new PurchaseOrderDeliveryPrintDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	
	public BaseResultSet orderSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres +="AND T.SUPPLIER_ID = T1.SUPPLIER_ID AND T1.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n" +
    			"   AND EXISTS (SELECT 1"+
    			"	FROM PT_BU_PCH_ORDER_SHIPPING O"+
    			"	WHERE T.SPLIT_ID = O.PURCHASE_ID"+
    			"	AND T.LAST_SHIP_DATE = O.SHIP_DATE)"+
				"   AND T.SPLIT_ID = T2.SPLIT_ID(+)\n" +
				"   AND T.SPLIT_ID = T3.SPLIT_ID(+)\n" +
				"   AND T1.ORG_ID = "+user.getOrgId()+"\n" + 
				"   AND T.OEM_COMPANY_ID = "+user.getOemCompanyId()+"\n" + 
				"   AND T.IF_ON_TIME = "+DicConstant.SF_01+"\n" + 
				"   AND T.ORDER_STATUS IN( "+DicConstant.CGDDZT_03+","+DicConstant.CGDDZT_04+","+DicConstant.CGDDZT_05+")";
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
		bs.setFieldUserID("APPLY_USER");
    	return bs;
    }
	public QuerySet queryPchOrderInfo(User user, String SPLIT_ID) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.SUPPLIER_NAME,\n" );
        sql.append("       T.SPLIT_NO,\n" );
        sql.append("       TO_CHAR(SYSDATE, 'YYYY-MM-DD') PRINT_DATE\n" );
        sql.append("  FROM PT_BU_PCH_ORDER_SPLIT      T\n" );
        sql.append("  WHERE T.SPLIT_ID="+SPLIT_ID+"\n" );
        //执行方法，不需要传递conn参数
        qs = factory.select(null,sql.toString());
        return qs;
    }
	public QuerySet queryPchOrderDtl(User user, String SPLIT_ID) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
        //执行方法，不需要传递conn参数
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT DISTINCT T.PART_CODE,\n" );
        sql.append("                T.PART_NAME,\n" );
        sql.append("                T4.DIC_VALUE UNIT,\n" );
        sql.append("                T3.MIN_PACK,\n" );
        sql.append("                T.PCH_COUNT,\n" );
        sql.append("                T1.SHIPPING_AMOUNT\n" );
        sql.append("  FROM PT_BU_PCH_ORDER_SPLIT_DTL T,\n" );
        sql.append("       PT_BU_PCH_ORDER_SHIPPING  T1,\n" );
        sql.append("       PT_BU_PCH_ORDER_SPLIT     T2,\n" );
        sql.append("       PT_BA_INFO                T3,\n" );
        sql.append("       DIC_TREE                  T4\n" );
        sql.append(" WHERE T.SPLIT_ID = T1.PURCHASE_ID\n" );
        sql.append("   AND T.SPLIT_ID = T2.SPLIT_ID\n" );
        sql.append("   AND T.DETAIL_ID = T1.DETAIL_ID\n" );
        sql.append("   AND T2.LAST_SHIP_DATE = T1.SHIP_DATE\n" );
        sql.append("   AND T.PART_ID = T3.PART_ID\n" );
        sql.append("   AND T3.UNIT = T4.ID\n" );
        sql.append("   AND NVL(T1.SHIPPING_AMOUNT,0) > 0\n" );
        sql.append("   AND T.SPLIT_ID = "+SPLIT_ID+"");
        qs = factory.select(null,sql.toString());
        return qs;
    }
	
	public QuerySet queryDirPchOrderInfo(User user, String SPLIT_ID) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.SUPPLIER_NAME,\n" );
        sql.append("       T.SPLIT_NO,\n" );
        sql.append("       T2.ORDER_NO,\n" );
        sql.append("       T2.ORG_NAME,\n" );
        sql.append("       T2.DELIVERY_ADDR,\n" );
        sql.append("       TO_CHAR(SYSDATE, 'YYYY-MM-DD') PRINT_DATE\n" );
        sql.append("  FROM PT_BU_PCH_ORDER_SPLIT T, PT_BU_PCH_ORDER T1, PT_BU_SALE_ORDER T2\n" );
        sql.append(" WHERE T.PURCHASE_ID = T1.PURCHASE_ID\n" );
        sql.append("   AND T1.SALE_ORDER_ID = T2.ORDER_ID");
        sql.append("   AND T.SPLIT_ID="+SPLIT_ID+"\n" );
        //执行方法，不需要传递conn参数
        qs = factory.select(null,sql.toString());
        return qs;
    }
}
