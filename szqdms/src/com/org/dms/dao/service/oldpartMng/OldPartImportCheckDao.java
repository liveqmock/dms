package com.org.dms.dao.service.oldpartMng;

import com.org.dms.common.DicConstant;
import com.org.framework.common.DBFactory;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;

/**
 * 旧件终审导入验证Dao
 *
 * @author zhengyao
 * @date 2014-10-28
 * @version 1.0
 */
public class OldPartImportCheckDao {
	 public QuerySet queryImp(User user, DBFactory factory) throws Exception{
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT TMP.TMP_ID,\n" );
		 sql.append("       TMP.RETURN_ORDER_NO,\n" );
		 sql.append("       TMP.CLAIM_NO,\n" );
		 sql.append("       TMP.FAULT_CODE,\n" );
		 sql.append("       TMP.PART_CODE,\n" );
		 sql.append("       TMP.PART_NAME,\n" );
		 sql.append("       TMP.SUPPLIER_NAME,\n" );
		 sql.append("       TMP.SUPPLIER_CODE,\n" );
		 sql.append("       TMP.AMOUNT,\n" );
		 sql.append("       TMP.REMARKS1,\n" );
		 sql.append("       TMP.OLD_PART_STATUS,\n" );
		 sql.append("       TMP.CREATE_USER,\n" );
		 sql.append("       TMP.ROW_NO\n" );
		 sql.append("  FROM SE_BU_RETURN_ORDER_TMP TMP\n" );
		 sql.append(" WHERE TMP.CREATE_USER = '"+user.getAccount()+"'");
		 return factory.select(null, sql.toString());
	 }
	 public QuerySet queryid(String claimNo,String faultCode,String partCode,String supplierCode,String returnOrderNo, DBFactory factory) throws Exception{
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT D.DETAIL_ID, D.CLAIM_ID,NVL(D.OUGHT_COUNT,0)OUGHT_COUNT,NVL(D.ACTUL_COUNT,0)ACTUL_COUNT,NVL(D.MISS_COUNT,0)MISS_COUNT,D.OLD_PART_STATUS,NVL(D.SHOULD_COUNT,0) SHOULD_COUNT \n" );
		 sql.append("  FROM SE_BU_RETURNORDER_DETAIL D,SE_BU_CLAIM C,SE_BU_CLAIM_FAULT F\n" );
		 sql.append(" WHERE C.CLAIM_ID = D.CLAIM_ID\n" );
		 sql.append(" AND F.CLAIM_ID = C.CLAIM_ID\n" );
		 sql.append(" AND D.CLAIM_NO ='"+claimNo+"'\n" );
		 sql.append(" AND D.PART_CODE ='"+partCode+"'\n" );
		 sql.append(" AND D.PROSUPPLY_CODE ='"+supplierCode+"'\n" );
		 sql.append(" AND D.ORDER_NO ='"+returnOrderNo+"'\n" );
		 sql.append(" AND F.FAULT_CODE ='"+faultCode+"'");
		 return factory.select(null, sql.toString());
	 }
	 public QuerySet queryReturnNo(User user, DBFactory factory) throws Exception{
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT TMP.ROW_NO,TMP.RETURN_ORDER_NO\n" );
		 sql.append("  FROM SE_BU_RETURN_ORDER_TMP TMP\n" );
		 sql.append(" WHERE TMP.CREATE_USER = '"+user.getAccount()+"'\n" );
		 sql.append("   AND NOT EXISTS (SELECT  O.ORDER_NO\n" );
		 sql.append("          FROM SE_BU_RETURN_ORDER O\n" );
		 sql.append("         WHERE O.ORDER_NO = TMP.RETURN_ORDER_NO)");
		 return factory.select(null, sql.toString());
	 }
	 public QuerySet queryClaimNo(User user, DBFactory factory) throws Exception{
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT TMP.ROW_NO,TMP.CLAIM_NO,TMP.RETURN_ORDER_NO\n" );
		 sql.append("  FROM SE_BU_RETURN_ORDER_TMP TMP\n" );
		 sql.append(" WHERE TMP.CREATE_USER = '"+user.getAccount()+"'\n" );
		 sql.append("   AND NOT EXISTS (SELECT O.ORDER_NO\n" );
		 sql.append("          FROM SE_BU_RETURNORDER_DETAIL O, SE_BU_CLAIM C\n" );
		 sql.append("         WHERE O.CLAIM_ID = C.CLAIM_ID\n" );
		 sql.append("           AND O.ORDER_NO = TMP.RETURN_ORDER_NO)");
		 return factory.select(null, sql.toString());
	 }
	 public QuerySet queryNos(User user, DBFactory factory) throws Exception{
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT TMP.ROW_NO,TMP.CLAIM_NO,TMP.RETURN_ORDER_NO,TMP.FAULT_CODE,TMP.PART_CODE,TMP.SUPPLIER_CODE\n" );
		 sql.append("  FROM SE_BU_RETURN_ORDER_TMP TMP\n" );
		 sql.append(" WHERE TMP.CREATE_USER = '"+user.getAccount()+"'\n" );
		 sql.append("   AND NOT EXISTS\n" );
		 sql.append(" (SELECT 1 FROM SE_BU_RETURNORDER_DETAIL D,\n" );
		 sql.append("               SE_BU_CLAIM              C,\n" );
		 sql.append("               SE_BU_CLAIM_FAULT        T\n" );
		 sql.append("         WHERE D.CLAIM_ID = C.CLAIM_ID\n" );
		 sql.append("           AND C.CLAIM_ID = T.CLAIM_ID\n" );
		 sql.append("           AND TMP.CLAIM_NO = D.CLAIM_NO\n" );
		 sql.append("           AND TMP.RETURN_ORDER_NO = D.ORDER_NO\n" );
		 sql.append("           AND TMP.PART_CODE = D.PART_CODE\n" );
		 sql.append("           AND TMP.FAULT_CODE = T.FAULT_CODE\n" );
		 sql.append("           AND TMP.SUPPLIER_CODE = D.PROSUPPLY_CODE)");
		 return factory.select(null, sql.toString());
	 }
	 public QuerySet checkList3(User user, DBFactory factory) throws Exception{
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT A.ROW_NO,\n" );
		 sql.append("       A.RETURN_ORDER_NO,\n" );
		 sql.append("       A.CLAIM_NO,\n" );
		 sql.append("       A.FAULT_CODE,\n" );
		 sql.append("       A.PART_CODE,\n" );
		 sql.append("       A.SUPPLIER_CODE\n" );
		 sql.append("  FROM SE_BU_RETURN_ORDER_TMP A,\n" );
		 sql.append("       (SELECT COUNT(T.TMP_ID) AS COU,\n" );
		 sql.append("               T.RETURN_ORDER_NO,\n" );
		 sql.append("               T.CLAIM_NO,\n" );
		 sql.append("               T.FAULT_CODE,\n" );
		 sql.append("               T.PART_CODE,\n" );
		 sql.append("               T.SUPPLIER_CODE\n" );
		 sql.append("          FROM SE_BU_RETURN_ORDER_TMP T\n" );
		 sql.append("         WHERE T.CREATE_USER = '"+user.getAccount()+"'\n" );
		 sql.append("         GROUP BY T.RETURN_ORDER_NO,\n" );
		 sql.append("                  T.CLAIM_NO,\n" );
		 sql.append("                  T.FAULT_CODE,\n" );
		 sql.append("                  T.PART_CODE,\n" );
		 sql.append("                  T.SUPPLIER_CODE\n" );
		 sql.append("        HAVING COUNT(T.TMP_ID) > 1) B\n" );
		 sql.append(" WHERE A.RETURN_ORDER_NO = B.RETURN_ORDER_NO\n" );
		 sql.append("   AND A.CLAIM_NO = B.CLAIM_NO\n" );
		 sql.append("   AND A.FAULT_CODE = B.FAULT_CODE\n" );
		 sql.append("   AND A.PART_CODE = B.PART_CODE\n" );
		 sql.append("   AND A.SUPPLIER_CODE = B.SUPPLIER_CODE");
			return factory.select(null, sql.toString());
		}
	 public QuerySet queryStatus(User user, DBFactory factory) throws Exception{
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT TMP.ROW_NO,TMP.CLAIM_NO,TMP.RETURN_ORDER_NO,TMP.FAULT_CODE,TMP.PART_CODE,TMP.SUPPLIER_CODE\n" );
		 sql.append("  FROM SE_BU_RETURN_ORDER_TMP TMP\n" );
		 sql.append(" WHERE TMP.CREATE_USER = '"+user.getAccount()+"'\n" );
		 sql.append("   AND  EXISTS\n" );
		 sql.append(" (SELECT 1 FROM SE_BU_RETURNORDER_DETAIL D,\n" );
		 sql.append("               SE_BU_CLAIM              C,\n" );
		 sql.append("               SE_BU_CLAIM_FAULT        T\n" );
		 sql.append("         WHERE D.CLAIM_ID = C.CLAIM_ID\n" );
		 sql.append("           AND C.CLAIM_ID = T.CLAIM_ID\n" );
		 sql.append("           AND TMP.CLAIM_NO = D.CLAIM_NO\n" );
		 sql.append("           AND TMP.RETURN_ORDER_NO = D.ORDER_NO\n" );
		 sql.append("           AND TMP.PART_CODE = D.PART_CODE\n" );
		 sql.append("           AND TMP.FAULT_CODE = T.FAULT_CODE\n" );
		 sql.append("           AND TMP.SUPPLIER_CODE = D.PROSUPPLY_CODE\n" );
		 sql.append("          AND D.OLD_PART_STATUS ="+DicConstant.JJZT_02+")");
		 return factory.select(null, sql.toString());
	 }
}
