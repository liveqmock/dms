package com.org.dms.dao.part.basicInfoMng;

import com.org.dms.common.DicConstant;
import com.org.framework.common.DBFactory;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;

public class WarehouseKeeperCheckDao {
	public QuerySet checkPart(User user,DBFactory factory) throws Exception{
    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT A.TMP_NO,A.PART_CODE\n" );
    	sql.append("  FROM PT_BA_WAREHOUSE_KEEPER_TMP A\n" );
    	sql.append(" WHERE 1=1 AND NOT EXISTS (SELECT 1 FROM PT_BA_INFO B WHERE A.PART_CODE = B.PART_CODE)\n" );
        sql.append(" AND A.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
        return factory.select(null, sql.toString());
    }
	public QuerySet checkWarehouse(User user,DBFactory factory) throws Exception{
    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT A.TMP_NO,A.WAREHOUSE_CODE\n" );
    	sql.append("  FROM PT_BA_WAREHOUSE_KEEPER_TMP A\n" );
    	sql.append(" WHERE 1=1 AND NOT EXISTS (SELECT 1 FROM PT_BA_WAREHOUSE B WHERE A.WAREHOUSE_CODE = B.WAREHOUSE_CODE)\n" );
        sql.append(" AND A.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
        return factory.select(null, sql.toString());
    }
	public QuerySet checkUser(User user,DBFactory factory) throws Exception{
    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT A.TMP_NO,A.KEEP_MAN_ACOUNT\n" );
    	sql.append("  FROM PT_BA_WAREHOUSE_KEEPER_TMP A\n" );
    	sql.append(" WHERE 1=1 AND NOT EXISTS (SELECT 1 FROM TM_USER B WHERE A.KEEP_MAN_ACOUNT = B.ACCOUNT AND B.ORG_ID = 10000063)\n" );
        sql.append(" AND A.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
        return factory.select(null, sql.toString());
    }

}
