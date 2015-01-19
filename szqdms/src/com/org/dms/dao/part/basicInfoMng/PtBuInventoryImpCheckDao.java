package com.org.dms.dao.part.basicInfoMng;

import com.org.dms.common.DicConstant;
import com.org.framework.common.DBFactory;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;

public class PtBuInventoryImpCheckDao {
	public QuerySet checkExist(DBFactory factory ,User user) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT ROW_NUM, T.PART_CODE\n" );
    	sql.append("  FROM PT_BU_INVENTORY_TMP T\n" );
    	sql.append(" WHERE T.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
    	sql.append("   AND NOT EXISTS\n" );
    	sql.append(" (SELECT 1 FROM PT_BA_INFO T1 WHERE T.PART_CODE = T1.PART_CODE)");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	
	public QuerySet checkArea(DBFactory factory ,User user,String INVENTORY_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.ROW_NUM, T.PART_CODE, T.AREA_CODE\n" );
    	sql.append("  FROM PT_BU_INVENTORY_TMP T\n" );
    	sql.append(" WHERE T.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
    	sql.append("   AND NOT EXISTS (SELECT 1\n" );
    	sql.append("          FROM PT_BU_INVENTORY T1, PT_BA_WAREHOUSE_AREA T2\n" );
    	sql.append("         WHERE T1.WAREHOUSE_ID = T2.WAREHOUSE_ID\n" );
    	sql.append("           AND T.AREA_CODE = T2.AREA_CODE\n" );
    	sql.append("           AND T1.INVENTORY_ID = "+INVENTORY_ID+")");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	public QuerySet checkPosi(DBFactory factory ,User user,String INVENTORY_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.ROW_NUM, T.PART_CODE, T.POSITION_NAME\n" );
    	sql.append("  FROM PT_BU_INVENTORY_TMP T\n" );
    	sql.append(" WHERE T.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
    	sql.append("   AND NOT EXISTS\n" );
    	sql.append(" (SELECT 1\n" );
    	sql.append("          FROM PT_BU_INVENTORY T1, PT_BU_STOCK_DTL T2, PT_BU_STOCK T3\n" );
    	sql.append("         WHERE T1.WAREHOUSE_ID = T3.WAREHOUSE_ID\n" );
    	sql.append("           AND T2.STOCK_ID = T3.STOCK_ID\n" );
    	sql.append("           AND T.PART_CODE = T2.PART_CODE\n" );
    	sql.append("           AND T.POSITION_NAME = T2.POSITION_NAME\n" );
    	sql.append("           AND T1.INVENTORY_ID = "+INVENTORY_ID+")");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	public QuerySet checkUnique(DBFactory factory ,User user) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.PART_CODE, T.ROW_NUM\n" );
    	sql.append("  FROM PT_BU_INVENTORY_TMP T\n" );
    	sql.append(" WHERE T.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
    	sql.append("   AND EXISTS (SELECT COUNT(T1.PART_CODE)\n" );
    	sql.append("          FROM PT_BU_INVENTORY_TMP T1\n" );
    	sql.append("         WHERE T.USER_ACCOUNT = T1.USER_ACCOUNT\n" );
    	sql.append("           AND T.PART_CODE = T1.PART_CODE\n" );
    	sql.append("           AND T.POSITION_NAME = T1.POSITION_NAME\n" );
    	sql.append("         GROUP BY T1.PART_CODE, T1.POSITION_NAME\n" );
    	sql.append("        HAVING COUNT(1) > 1)");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	public QuerySet checkOther(DBFactory factory ,User user) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.PART_CODE, T.ROW_NUM\n" );
    	sql.append("  FROM PT_BU_INVENTORY_TMP T\n" );
    	sql.append(" WHERE T.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
    	sql.append("   AND EXISTS (SELECT 1\n" );
    	sql.append("          FROM PT_BU_INVENTORY T1, PT_BU_INVENTORY_DTL T2\n" );
    	sql.append("         WHERE T1.INVENTORY_ID = T2.INVENTORY_ID\n" );
    	sql.append("           AND T.PART_CODE = T2.PART_CODE\n" );
    	sql.append("           AND T.POSITION_NAME = T2.POSITION_NAME\n" );
    	sql.append("           AND NVL(T1.INVENTORY_STATUS, 0) < "+DicConstant.PDZT_04+")\n" );
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	
	public QuerySet checkExist1(DBFactory factory ,User user) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT ROW_NUM, T.PART_CODE\n" );
    	sql.append("  FROM PT_BU_INVENTORY_DTL_TMP T\n" );
    	sql.append(" WHERE T.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
    	sql.append("   AND NOT EXISTS\n" );
    	sql.append(" (SELECT 1 FROM PT_BA_INFO T1 WHERE T.PART_CODE = T1.PART_CODE)");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	public QuerySet checkPosi1(DBFactory factory ,User user,String INVENTORY_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.ROW_NUM, T.PART_CODE, T.POSITION_CODE\n" );
    	sql.append("  FROM PT_BU_INVENTORY_DTL_TMP T\n" );
    	sql.append(" WHERE T.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
    	sql.append("   AND NOT EXISTS\n" );
    	sql.append(" (SELECT 1\n" );
    	sql.append("          FROM PT_BU_INVENTORY T1, PT_BU_STOCK_DTL T2, PT_BU_STOCK T3\n" );
    	sql.append("         WHERE T1.WAREHOUSE_ID = T3.WAREHOUSE_ID\n" );
    	sql.append("           AND T2.STOCK_ID = T3.STOCK_ID\n" );
    	sql.append("           AND T.PART_CODE = T2.PART_CODE\n" );
    	sql.append("           AND T.POSITION_CODE = T2.POSITION_CODE\n" );
    	sql.append("           AND T1.INVENTORY_ID = "+INVENTORY_ID+")");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	public QuerySet checkUnique1(DBFactory factory ,User user) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.PART_CODE, T.ROW_NUM\n" );
    	sql.append("  FROM PT_BU_INVENTORY_DTL_TMP T\n" );
    	sql.append(" WHERE T.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
    	sql.append("   AND EXISTS (SELECT COUNT(T1.PART_CODE)\n" );
    	sql.append("          FROM PT_BU_INVENTORY_DTL_TMP T1\n" );
    	sql.append("         WHERE T.USER_ACCOUNT = T1.USER_ACCOUNT\n" );
    	sql.append("           AND T.PART_CODE = T1.PART_CODE\n" );
    	sql.append("           AND T.POSITION_CODE = T1.POSITION_CODE\n" );
    	sql.append("         GROUP BY T1.PART_CODE, T1.POSITION_CODE\n" );
    	sql.append("        HAVING COUNT(1) > 1)");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	public QuerySet checkCount(DBFactory factory ,User user) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.ROW_NUM, T.PART_CODE\n" );
    	sql.append("  FROM PT_BU_INVENTORY_DTL_TMP T\n" );
    	sql.append(" WHERE T.MATERIAL_COUNT IS NULL\n" );
    	sql.append(" AND T.USER_ACCOUNT = '"+user.getAccount()+"'");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
}
