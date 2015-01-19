package com.org.dms.dao.part.purchaseMng.purchaseOrder;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class PurchasePartInfoMngDao extends BaseDAO{
	//定义instance
    public  static final PurchasePartInfoMngDao getInstance(ActionContext atx)
    {
    	PurchasePartInfoMngDao dao = new PurchasePartInfoMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
    
    public BaseResultSet pchPartSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres +="  AND T.SPLIT_ID = T1.SPLIT_ID AND T.ORDER_STATUS = "+DicConstant.CGDDZT_05+"\n" +
    					"GROUP BY T.SUPPLIER_ID,\n" + 
    					"         T.SUPPLIER_NAME,\n" + 
    					"         T.SUPPLIER_CODE,\n" + 
    					"         T1.PART_ID,\n" + 
    					"         T1.PART_NAME,\n" + 
    					"         T1.PART_CODE";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.SUPPLIER_ID,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("       T.SUPPLIER_CODE,\n" );
    	sql.append("       T1.PART_ID,\n" );
    	sql.append("       T1.PART_NAME,\n" );
    	sql.append("       T1.PART_CODE,\n" );
    	sql.append("       NVL(COUNT(T1.PART_ID), 0) PCH_FREQUERNCY,\n" );
    	sql.append("       NVL(SUM(T1.PCH_COUNT), 0) TOTAL_PCH_AMOUNT,\n" );
    	sql.append("       NVL(SUM(T1.STORAGE_COUNT), 0) TOTAL_IN_AMOUNT,\n" );
    	sql.append("       ROUND(NVL(SUM(T1.PCH_COUNT) / SUM(T1.STORAGE_COUNT), 0), 2)*100 PCH_RATE\n" );
    	sql.append("  FROM PT_BU_PCH_ORDER_SPLIT T, PT_BU_PCH_ORDER_SPLIT_DTL T1");
    	bs = factory.select(sql.toString(), page);
/*		bs.setFieldDic("PURCHASE_TYPE", "CGDDLX");
		bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
		bs.setFieldUserID("APPLY_USER");*/
    	return bs;
    }
    
    public QuerySet download(String conditions,PageManager page)throws Exception{
    	String wheres = "WHERE "+ conditions;
    	wheres+="   AND T.SPLIT_ID = T1.SPLIT_ID AND T.ORDER_STATUS = "+DicConstant.CGDDZT_05+"\n";
    	wheres+=" GROUP BY T.SUPPLIER_ID,\n" ;
    	wheres+="          T.SUPPLIER_NAME,\n" ;
    	wheres+="          T.SUPPLIER_CODE,\n" ;
    	wheres+="          T1.PART_ID,\n" ;
    	wheres+="          T1.PART_NAME,\n" ;
    	wheres+="          T1.PART_CODE";
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.SUPPLIER_ID,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("       T.SUPPLIER_CODE,\n" );
    	sql.append("       T1.PART_ID,\n" );
    	sql.append("       T1.PART_NAME,\n" );
    	sql.append("       T1.PART_CODE,\n" );
    	sql.append("       NVL(COUNT(T1.PART_ID), 0) PCH_FREQUERNCY,\n" );
    	sql.append("       NVL(SUM(T1.PCH_COUNT), 0) TOTAL_PCH_AMOUNT,\n" );
    	sql.append("       NVL(SUM(T1.STORAGE_COUNT), 0) TOTAL_IN_AMOUNT,\n" );
    	sql.append("       ROUND(NVL(SUM(T1.PCH_COUNT) / SUM(T1.STORAGE_COUNT), 0), 2)*100 PCH_RATE\n" );
    	sql.append("  FROM PT_BU_PCH_ORDER_SPLIT T, PT_BU_PCH_ORDER_SPLIT_DTL T1\n" );
		    return factory.select(null, sql.toString()+wheres);
		}

}
