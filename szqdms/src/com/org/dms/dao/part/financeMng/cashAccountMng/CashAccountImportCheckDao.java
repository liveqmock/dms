package com.org.dms.dao.part.financeMng.cashAccountMng;

import com.org.framework.common.DBFactory;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;

public class CashAccountImportCheckDao {
	/**
     * 渠道商账户是否存在(导入验证)
     * @param orgCode
     * @param user
     * @return
     * @throws Exception
     */
    public QuerySet accountIdCheck (User user,String accountType,DBFactory factory) throws Exception {

    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT TMP_NO,ORG_CODE\n" );
    	sql.append("  FROM PT_BU_ACCOUNT_TMP\n" );
    	sql.append(" WHERE USER_ACCOUNT='"+user.getAccount()+"' AND ORG_CODE NOT IN\n" );
    	sql.append("       (SELECT ORG_CODE FROM PT_BU_ACCOUNT WHERE ACCOUNT_TYPE = '" + accountType + "')");
        return factory.select(null, sql.toString());
    }

    /**
     * 导入金额验证(导入验证)
     * @param orgCode
     * @param user
     * @return
     * @throws Exception
     */
    public QuerySet searchAmount (User user,DBFactory factory) throws Exception {

    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT TMP_NO,ORG_CODE, AMOUNT FROM PT_BU_ACCOUNT_TMP WHERE USER_ACCOUNT = '" + user.getAccount() + "'");
        return factory.select(null, sql.toString());
    }
}
