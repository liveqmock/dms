package com.org.dms.dao.part.financeMng.settlement;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class SupplierSettlementModeDao extends BaseDAO{
	public  static final SupplierSettlementModeDao getInstance(ActionContext atx)
    {
		SupplierSettlementModeDao dao = new SupplierSettlementModeDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	public BaseResultSet settleSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres +="AND T.SETTLE_STATUS ="+DicConstant.GYSJSZT_02+"\n";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.SUM_ID,\n" );
    	sql.append("       T.SUPPLIER_ID,\n" );
    	sql.append("       T.SUPPLIER_CODE,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("       T.IN_COUNT,\n" );
    	sql.append("       T.IN_AMOUNT,\n" );
    	sql.append("       T.RETURN_COUNT,\n" );
    	sql.append("       T.RETURN_AMOUNT,\n" );
    	sql.append("       T.INVOICE_AMOUNT,\n" );
    	sql.append("       NVL(T.SETTLE_AMOUNT,0) SETTLE_AMOUNT,\n" );
    	sql.append("       NVL(T.UNSETTLE_AMOUNT,0) UNSETTLE_AMOUNT,\n" );
    	sql.append("       T.INVOICE_MONTH,\n" );
    	sql.append("       T.SETTLE_STATUS\n" );
    	sql.append("  FROM PT_BU_SUP_INVOICE_SUMMARY T");
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("SETTLE_STATUS", "GYSJSZT");
    	return bs;
    }
	

}
