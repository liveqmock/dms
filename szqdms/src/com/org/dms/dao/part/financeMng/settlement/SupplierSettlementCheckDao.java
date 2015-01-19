package com.org.dms.dao.part.financeMng.settlement;

import com.org.dms.common.DicConstant;
import com.org.framework.common.DBFactory;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;

public class SupplierSettlementCheckDao {
	
	
	public QuerySet checkSet(User pUser,DBFactory factory) throws Exception {

        StringBuilder sql= new StringBuilder();
        sql.append("SELECT \n" );
        sql.append("    T.TMP_ID,\n" );
        sql.append("    T.TMP_NO,\n" );
        sql.append("    T.SUPPLIER_CODE,\n" );
        sql.append("    T.USER_ACCOUNT\n" );
        sql.append("    FROM PT_BU_SUP_INVOICE_SUMMARY_TMP T\n" );
        sql.append(" WHERE USER_ACCOUNT='" + pUser.getAccount() + "' AND EXISTS(SELECT 1 FROM PT_BU_SUP_INVOICE_SUMMARY T1 WHERE T.SUPPLIER_CODE = T1.SUPPLIER_CODE AND T.SELECT_MONTH = T1.INVOICE_MONTH AND T1.SETTLE_STATUS = 205302)\n" );

        return factory.select(null, sql.toString());
    }
	
	public QuerySet checkSup(User pUser,DBFactory factory) throws Exception {

        StringBuilder sql= new StringBuilder();
        sql.append("SELECT \n" );
        sql.append("    T.TMP_ID,\n" );
        sql.append("    T.TMP_NO,\n" );
        sql.append("    T.SUPPLIER_CODE,\n" );
        sql.append("    T.USER_ACCOUNT\n" );
        sql.append("    FROM PT_BU_SUP_INVOICE_SUMMARY_TMP T\n" );
        sql.append(" WHERE USER_ACCOUNT='" + pUser.getAccount() + "' AND NOT EXISTS(SELECT 1 FROM PT_BA_SUPPLIER T1 WHERE T.SUPPLIER_CODE = T1.SUPPLIER_CODE AND T1.PART_IDENTIFY = "+DicConstant.YXBS_01+")\n" );

        return factory.select(null, sql.toString());
    }
	public QuerySet checkAmount(User pUser,DBFactory factory) throws Exception {

        StringBuilder sql= new StringBuilder();
        sql.append("SELECT \n" );
        sql.append("    T.TMP_ID,\n" );
        sql.append("    T.TMP_NO,\n" );
        sql.append("    T.SUPPLIER_CODE,\n" );
        sql.append("    T.USER_ACCOUNT\n" );
        sql.append("    FROM PT_BU_SUP_INVOICE_SUMMARY_TMP T\n" );
        sql.append(" WHERE USER_ACCOUNT='" + pUser.getAccount() + "' AND T.SETTLE_AMOUNT = 0\n" );

        return factory.select(null, sql.toString());
    }

}
