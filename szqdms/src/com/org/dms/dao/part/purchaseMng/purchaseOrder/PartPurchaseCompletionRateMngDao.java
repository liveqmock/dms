package com.org.dms.dao.part.purchaseMng.purchaseOrder;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class PartPurchaseCompletionRateMngDao extends BaseDAO{
	public  static final PartPurchaseCompletionRateMngDao getInstance(ActionContext atx)
    {
		PartPurchaseCompletionRateMngDao dao = new PartPurchaseCompletionRateMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	
	public BaseResultSet pchPartSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres +="AND T3.SUPPLIER_ID = T2.SUPPLIER_ID\n" +
    					"AND T1.PART_ID = T2.PART_ID\n" + 
    					"AND T.SPLIT_ID = T1.SPLIT_ID AND T2.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n" + 
    					"AND T.PURCHASE_ID = T3.PURCHASE_ID AND T.ORDER_STATUS = "+DicConstant.CGDDZT_05+"";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.SPLIT_NO,\n" );
    	sql.append("       T.SUPPLIER_CODE,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("       T1.PART_NAME,\n" );
    	sql.append("       T1.PART_CODE,\n" );
    	sql.append("       T2.APPLY_CYCLE,\n" );
    	sql.append("       T3.APPLY_DATE,\n" );
    	sql.append("       T1.PCH_COUNT,\n" );
    	sql.append("       T1.STORAGE_COUNT,\n" );
    	sql.append("       T.REPUIREMENT_TIME,\n" );
    	sql.append("       ROUND(NVL(((SELECT SUM(A.SHIPPING_AMOUNT)\n" );
    	sql.append("                     FROM PT_BU_PCH_ORDER_SHIPPING A\n" );
    	sql.append("                    WHERE A.SHIP_DATE < T.REPUIREMENT_TIME\n" );
    	sql.append("                      AND A.DETAIL_ID = T1.DETAIL_ID) / T1.STORAGE_COUNT),\n" );
    	sql.append("                 0),\n" );
    	sql.append("             2) * 100 ON_TIME_RATE,\n" );
    	sql.append("       ROUND(NVL(((SELECT SUM(A.SHIPPING_AMOUNT)\n" );
    	sql.append("                     FROM PT_BU_PCH_ORDER_SHIPPING A\n" );
    	sql.append("                    WHERE A.SHIP_DATE > T.REPUIREMENT_TIME\n" );
    	sql.append("                      AND A.DETAIL_ID = T1.DETAIL_ID) / T1.STORAGE_COUNT),\n" );
    	sql.append("                 0),\n" );
    	sql.append("             2) * 100 OUT_TIME_RATE,\n" );
    	sql.append("       ROUND(NVL(((T1.PCH_COUNT - T1.STORAGE_COUNT) / T1.PCH_COUNT), 0), 2) * 100 UN_RATE\n" );
    	sql.append("  FROM PT_BU_PCH_ORDER_SPLIT     T,\n" );
    	sql.append("       PT_BU_PCH_ORDER_SPLIT_DTL T1,\n" );
    	sql.append("       PT_BA_PART_SUPPLIER_RL    T2,\n" );
    	sql.append("       PT_BU_PCH_ORDER           T3");
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDateFormat("REPUIREMENT_TIME", "yyyy-MM-dd");
    	bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
    	return bs;
    }
	
	
	
	public QuerySet download(String conditions,PageManager page)throws Exception{
    	String wheres = "WHERE "+ conditions;
    	wheres+="AND T3.SUPPLIER_ID = T2.SUPPLIER_ID AND T2.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n";
		wheres+="AND T1.PART_ID = T2.PART_ID\n"; 
		wheres+="AND T.SPLIT_ID = T1.SPLIT_ID\n"; 
		wheres+="AND T.PURCHASE_ID = T3.PURCHASE_ID AND T.ORDER_STATUS = "+DicConstant.CGDDZT_05+"\n";
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.SPLIT_NO,\n" );
    	sql.append("       T.SUPPLIER_CODE,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("       T1.PART_NAME,\n" );
    	sql.append("       T1.PART_CODE,\n" );
    	sql.append("       T2.APPLY_CYCLE,\n" );
    	sql.append("       TO_CHAR(T3.APPLY_DATE,'YYYY-MM-DD') APPLY_DATE,\n" );
    	sql.append("       T1.PCH_COUNT,\n" );
    	sql.append("       T1.STORAGE_COUNT,\n" );
    	sql.append("       TO_CHAR(T.REPUIREMENT_TIME,'YYYY-MM-DD') REPUIREMENT_TIME,\n" );
    	sql.append("       ROUND(NVL(((SELECT SUM(A.SHIPPING_AMOUNT)\n" );
    	sql.append("                     FROM PT_BU_PCH_ORDER_SHIPPING A\n" );
    	sql.append("                    WHERE A.SHIP_DATE < T.REPUIREMENT_TIME\n" );
    	sql.append("                      AND A.DETAIL_ID = T1.DETAIL_ID) / T1.STORAGE_COUNT),\n" );
    	sql.append("                 0),\n" );
    	sql.append("             2) * 100 ON_TIME_RATE,\n" );
    	sql.append("       ROUND(NVL(((SELECT SUM(A.SHIPPING_AMOUNT)\n" );
    	sql.append("                     FROM PT_BU_PCH_ORDER_SHIPPING A\n" );
    	sql.append("                    WHERE A.SHIP_DATE > T.REPUIREMENT_TIME\n" );
    	sql.append("                      AND A.DETAIL_ID = T1.DETAIL_ID) / T1.STORAGE_COUNT),\n" );
    	sql.append("                 0),\n" );
    	sql.append("             2) * 100 OUT_TIME_RATE,\n" );
    	sql.append("       ROUND(NVL(((T1.PCH_COUNT - T1.STORAGE_COUNT) / T1.PCH_COUNT), 0), 2) * 100 UN_RATE\n" );
    	sql.append("  FROM PT_BU_PCH_ORDER_SPLIT     T,\n" );
    	sql.append("       PT_BU_PCH_ORDER_SPLIT_DTL T1,\n" );
    	sql.append("       PT_BA_PART_SUPPLIER_RL    T2,\n" );
    	sql.append("       PT_BU_PCH_ORDER           T3 \n");
		    return factory.select(null, sql.toString()+wheres);
		}

}
