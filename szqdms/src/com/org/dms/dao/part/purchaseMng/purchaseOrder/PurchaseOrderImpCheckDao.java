package com.org.dms.dao.part.purchaseMng.purchaseOrder;

import com.org.dms.common.DicConstant;
import com.org.framework.common.DBFactory;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;

public class PurchaseOrderImpCheckDao {
	public QuerySet getSup(DBFactory factory ,String PURCHASE_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT SUPPLIER_ID FROM PT_BU_PCH_ORDER WHERE PURCHASE_ID = "+PURCHASE_ID+"");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	public QuerySet checkExist(DBFactory factory ,User user) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT TMP_NO, T.PART_CODE\n" );
    	sql.append("  FROM PT_BU_PCH_ORDER_PART_TMP T\n" );
    	sql.append(" WHERE T.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
    	sql.append("   AND NOT EXISTS\n" );
    	sql.append(" (SELECT 1 FROM PT_BA_INFO T1 WHERE T.PART_CODE = T1.PART_CODE)");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	public QuerySet checkAttribute(DBFactory factory ,User user) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.TMP_NO, T.PART_CODE\n" );
    	sql.append("  FROM PT_BU_PCH_ORDER_PART_TMP T\n" );
    	sql.append(" WHERE T.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
    	sql.append("   AND NOT EXISTS\n" );
    	sql.append(" (SELECT 1 FROM PT_BA_PCH_ATTRIBUTE T1 WHERE T.PART_CODE = T1.PART_CODE)");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	public QuerySet checkPchCycle(DBFactory factory ,User user,String SUPPLIER_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.TMP_NO, T.PART_CODE, T1.PART_ID\n" );
    	sql.append("  FROM PT_BU_PCH_ORDER_PART_TMP T, PT_BA_INFO T1\n" );
    	sql.append(" WHERE 1 = 1\n" );
    	sql.append("   AND T.PART_CODE = T1.PART_CODE\n" );
    	sql.append("   AND T.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
    	sql.append("   AND NOT EXISTS (SELECT 1\n" );
    	sql.append("          FROM PT_BA_PART_SUPPLIER_RL T2\n" );
    	sql.append("         WHERE T2.PART_ID = T1.PART_ID\n" );
    	sql.append("           AND T2.SUPPLIER_ID = "+SUPPLIER_ID+" AND T2.PART_IDENTIFY = "+DicConstant.YXBS_01+")");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	public QuerySet checkPchPrice(DBFactory factory ,User user,String SUPPLIER_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.TMP_NO, T.PART_CODE, T1.PART_ID\n" );
    	sql.append("  FROM PT_BU_PCH_ORDER_PART_TMP T, PT_BA_INFO T1\n" );
    	sql.append(" WHERE 1 = 1\n" );
    	sql.append("   AND T.PART_CODE = T1.PART_CODE\n" );
    	sql.append("   AND T.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
    	sql.append("   AND NOT EXISTS (SELECT 1\n" );
    	sql.append("          FROM PT_BA_PART_SUPPLIER_RL T2\n" );
    	sql.append("         WHERE T2.PART_ID = T1.PART_ID\n" );
    	sql.append("           AND T2.PCH_PRICE IS NOT NULL\n" );
    	sql.append("           AND T2.SUPPLIER_ID = "+SUPPLIER_ID+" AND T2.PART_IDENTIFY = "+DicConstant.YXBS_01+")");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	public QuerySet checkPlanPrice(DBFactory factory ,User user,String SUPPLIER_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.TMP_NO, T.PART_CODE, T1.PART_ID\n" );
    	sql.append("  FROM PT_BU_PCH_ORDER_PART_TMP T, PT_BA_INFO T1\n" );
    	sql.append(" WHERE 1 = 1\n" );
    	sql.append("   AND T.PART_CODE = T1.PART_CODE\n" );
    	sql.append("   AND T.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
    	sql.append("   AND T1.PLAN_PRICE IS NULL\n" );
//    	sql.append("   AND NOT EXISTS (SELECT 1\n" );
//    	sql.append("          FROM PT_BA_PART_SUPPLIER_RL T2\n" );
//    	sql.append("         WHERE T2.PART_ID = T1.PART_ID\n" );
//    	sql.append("           AND T2.SUPPLIER_ID = "+SUPPLIER_ID+" AND T2.PART_IDENTIFY = "+DicConstant.YXBS_01+")");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	public QuerySet checkUnique(DBFactory factory ,User user,String bParams) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.TMP_NO, T.PART_CODE, T1.PART_ID\n" );
    	sql.append("  FROM PT_BU_PCH_ORDER_PART_TMP T, PT_BA_INFO T1\n" );
    	sql.append(" WHERE 1 = 1\n" );
    	sql.append("   AND T.PART_CODE = T1.PART_CODE\n" );
    	sql.append("   AND T.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
    	sql.append("   AND EXISTS (SELECT 1\n" );
    	sql.append("          FROM PT_BU_PCH_ORDER_DTL T2\n" );
    	sql.append("         WHERE T2.PART_ID = T1.PART_ID\n" );
    	sql.append("           AND T2.PURCHASE_ID = "+bParams+")");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	/**
	 * 
	 * @date()2014年11月5日下午12:29:13
	 * @author Administrator
	 * @to_do:
	 * @param factory
	 * @param user
	 * @param bParams
	 * @return
	 * @throws Exception
	 */
	public QuerySet checkContract(DBFactory factory ,User user,String bParams) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT TMP_NO, PART_CODE\n" );
    	sql.append("  FROM PT_BU_PCH_ORDER_PART_TMP T\n" );
    	sql.append(" WHERE 1 = 1 AND T.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
    	sql.append("   AND NOT EXISTS (SELECT 1\n" );
    	sql.append("          FROM PT_BU_PCH_ORDER        A,\n" );
    	sql.append("               PT_BU_PCH_CONTRACT     B,\n" );
    	sql.append("               PT_BU_PCH_CONTRACT_DTL C\n" );
    	sql.append("         WHERE A.SUPPLIER_CODE = B.SUPPLIER_CODE\n" );
    	sql.append("           AND A.PURCHASE_ID = "+bParams+"\n" );
    	sql.append("           AND C.PART_CODE = T.PART_CODE)");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	public QuerySet checkCount(User user,DBFactory factory)throws Exception {
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT A.TMP_NO,A.PART_CODE,A.PCH_COUNT FROM PT_BU_PCH_ORDER_PART_TMP A WHERE 1=1 AND A.USER_ACCOUNT='"+user.getAccount()+"'\n" );
	   return factory.select(null,sql.toString());
   }

}
