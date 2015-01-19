package com.org.dms.dao.service.oldpartMng;

import com.org.framework.common.DBFactory;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;

/**
 * 旧件装箱导入验证Dao
 *
 * @author zhengyao
 * @date 2014-10-28
 * @version 1.0
 */
public class OldPartPackImpCheckDao {

/**
    * 校验索赔单号、配件代码、供应商代码 一致
    * (数量可以改)
    * @return
    * @throws Exception
    */
   public QuerySet checkList1(User user, DBFactory factory)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT T.CLAIM_NO, T.PART_CODE, T.PROSUPPLY_CODE, T.ROW_NUM\n" );
	   sql.append("  FROM SE_BU_RETURNORDER_DTL_TMP T\n" );
	   sql.append(" WHERE T.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
	   sql.append("AND NOT EXISTS (SELECT D.*\n" );
	   sql.append("       FROM SE_BU_CLAIM               C,\n" );
	   sql.append("            SE_BU_CLAIM_FAULT         F,\n" );
	   sql.append("            SE_BU_CLAIM_FAULT_PART    D,\n" );
	   sql.append("            PT_BA_SUPPLIER            P,\n" );
	   sql.append("            PT_BA_INFO                B\n" );
	   sql.append("      WHERE T.CLAIM_NO = C.CLAIM_NO\n" );
	   sql.append("        AND C.CLAIM_ID = F.CLAIM_ID\n" );
	   sql.append("        AND F.CLAIM_DTL_ID = D.CLAIM_DTL_ID\n" );
	   sql.append("        AND T.FAULT_CODE = F.FAULT_CODE\n" );
	   sql.append("        AND T.PART_CODE = D.OLD_PART_CODE\n" );
	   sql.append("        AND D.OLD_PART_ID = B.PART_ID\n" );
	   sql.append("        AND T.PROSUPPLY_CODE = P.SUPPLIER_CODE\n" );
	   sql.append("        AND D.OLD_SUPPLIER_ID = P.SUPPLIER_ID\n" );
	 //  sql.append("        AND T.OUGHT_COUNT = D.OLD_PART_COUNT\n");
	   //sql.append("           AND D.IF_RETURN = "+DicConstant.SF_02+"\n" );//索赔单配件 未回运的
	  // sql.append("           AND B.IF_RETURN = "+DicConstant.SF_01+"\n");//配件属性 需要回运的旧件
	   sql.append("           )");//配件属性 需要回运的旧件
	   sql.append("         ORDER BY TO_NUMBER(T.ROW_NUM) ");
	   return factory.select(null, sql.toString());
   }
   /**
    * 数量不能大于故障配件数量
    * @param user
    * @return
    * @throws Exception
    */
   public QuerySet checkList6(User user, DBFactory factory)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT T.CLAIM_NO, T.PART_CODE, T.PROSUPPLY_CODE, T.ROW_NUM\n" );
	   sql.append("  FROM SE_BU_RETURNORDER_DTL_TMP T\n" );
	   sql.append(" WHERE T.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
	   sql.append("AND EXISTS (SELECT D.*\n" );
	   sql.append("       FROM SE_BU_CLAIM               C,\n" );
	   sql.append("            SE_BU_CLAIM_FAULT         F,\n" );
	   sql.append("            SE_BU_CLAIM_FAULT_PART    D,\n" );
	   sql.append("            PT_BA_SUPPLIER            P,\n" );
	   sql.append("            PT_BA_INFO                B\n" );
	   sql.append("      WHERE T.CLAIM_NO = C.CLAIM_NO\n" );
	   sql.append("        AND C.CLAIM_ID = F.CLAIM_ID\n" );
	   sql.append("        AND F.CLAIM_DTL_ID = D.CLAIM_DTL_ID\n" );
	   sql.append("        AND T.FAULT_CODE = F.FAULT_CODE\n" );
	   sql.append("        AND T.PART_CODE = D.OLD_PART_CODE\n" );
	   sql.append("        AND D.OLD_PART_ID = B.PART_ID\n" );
	   sql.append("        AND T.PROSUPPLY_CODE = P.SUPPLIER_CODE\n" );
	   sql.append("        AND D.OLD_SUPPLIER_ID = P.SUPPLIER_ID\n" );
	   sql.append("        AND T.OUGHT_COUNT > D.OLD_PART_COUNT - NVL(D.OLD_PART_ALREADY_IN, 0)\n");
	   //sql.append("           AND D.IF_RETURN = "+DicConstant.SF_02+"\n" );//索赔单配件 未回运的
	   //sql.append("           AND B.IF_RETURN = "+DicConstant.SF_01+"\n");//配件属性 需要回运的旧件
	   sql.append("           )");
	   sql.append("         ORDER BY TO_NUMBER(T.ROW_NUM) ");
	   return factory.select(null, sql.toString());
   }
   /**
    * 校验箱号不能为空
    * @param user
    * @return
    * @throws Exception
    */
   public QuerySet checkList2(User user, DBFactory factory)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT T.ROW_NUM\n" );
	   sql.append("  FROM SE_BU_RETURNORDER_DTL_TMP T\n" );
	   sql.append(" WHERE T.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
	   sql.append("   AND T.BOX_NO IS NULL");
	   return factory.select(null, sql.toString());
   }
   
   /**
    * 校验导入的数据是否重复
    * @param user
    * @return
    * @throws Exception
    */
   public QuerySet checkList3(User user, DBFactory factory)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT T.ROW_NUM ,T.CLAIM_NO,T.FAULT_CODE,T.PART_CODE,T.PROSUPPLY_CODE FROM (\n" );
	   sql.append("    SELECT P.CLAIM_NO,\n" );
	   sql.append("           P.FAULT_CODE,\n" );
	   sql.append("           P.PART_CODE,\n" );
	   sql.append("           P.PROSUPPLY_CODE,\n" );
	   sql.append("           P.OUGHT_COUNT,\n" );
	   sql.append("           COUNT(P.TMP_ID)\n" );
	   sql.append("      FROM SE_BU_RETURNORDER_DTL_TMP P\n" );
	   sql.append("     WHERE P.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
	   sql.append("     GROUP BY P.CLAIM_NO,\n" );
	   sql.append("              P.FAULT_CODE,\n" );
	   sql.append("              P.PART_CODE,\n" );
	   sql.append("              P.PROSUPPLY_CODE,\n" );
	   sql.append("              P.OUGHT_COUNT\n" );
	   sql.append("    HAVING COUNT(P.TMP_ID) > 1)A,SE_BU_RETURNORDER_DTL_TMP T\n" );
	   sql.append("    WHERE T.CLAIM_NO = A.CLAIM_NO\n" );
	   sql.append("    AND T.FAULT_CODE = A.FAULT_CODE\n" );
	   sql.append("    AND T.PART_CODE = A.PART_CODE\n" );
	   sql.append("    AND T.PROSUPPLY_CODE = A.PROSUPPLY_CODE");
	   return factory.select(null, sql.toString());
   }
   /**
    * 校验导入的数据是否在旧件明细表中存在
    * @param user
    * @return
    * @throws Exception
    */
   public QuerySet checkList4(User user, DBFactory factory)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT T.ROW_NUM,T.CLAIM_NO, T.PART_CODE, T.OUGHT_COUNT, T.PROSUPPLY_CODE\n" );
	   sql.append("  FROM SE_BU_RETURNORDER_DTL_TMP T\n" );
	   sql.append(" WHERE T.USER_ACCOUNT ='"+user.getAccount()+"'\n" );
	   sql.append("  AND EXISTS (SELECT 1\n" );
	   sql.append("           FROM SE_BU_RETURNORDER_DETAIL P\n" );
	   sql.append("          WHERE T.CLAIM_NO = P.CLAIM_NO\n" );
	   sql.append("            AND T.PART_CODE = P.PART_CODE\n" );
	   sql.append("            AND T.OUGHT_COUNT = P.OUGHT_COUNT\n" );
	   sql.append("            AND T.PROSUPPLY_CODE = P.PROSUPPLY_CODE) \n");
	   sql.append("    ORDER BY TO_NUMBER(T.ROW_NUM)  ");
	   return factory.select(null, sql.toString());
   }
   /**
    * 校验导入的数据不存在旧件不回运表
    * @param user
    * @return
    * @throws Exception
    */
   public QuerySet checkList5(User user, DBFactory factory)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT T.CLAIM_NO, T.PART_CODE, T.PROSUPPLY_CODE, T.ROW_NUM\n" );
	   sql.append("  FROM SE_BU_RETURNORDER_DTL_TMP T\n" );
	   sql.append(" WHERE T.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
	   sql.append("   AND EXISTS (SELECT 1\n" );
	   sql.append("          FROM SE_BU_RETURNORDER_NOT     N,\n" );
	   sql.append("               SE_BU_RETURNORDER_NOT_DTL D,\n" );
	   sql.append("               SE_BU_CLAIM               C,\n" );
	   sql.append("               SE_BU_CLAIM_FAULT         F\n" );
	   sql.append("         WHERE N.NOTBACK_ID = D.NOTBACK_ID\n" );
	   sql.append("           AND N.CLAIM_ID = C.CLAIM_ID\n" );
	   sql.append("           AND C.CLAIM_ID = F.CLAIM_ID\n" );
	   sql.append("           AND T.CLAIM_NO = C.CLAIM_NO\n" );
	   sql.append("           AND T.PART_CODE = D.PART_CODE\n" );
	   sql.append("           AND N.APPLY_STATUS = 303002\n" );
	   sql.append("           AND T.FAULT_CODE = F.FAULT_CODE)\n");
	   sql.append("    ORDER BY TO_NUMBER(T.ROW_NUM)  ");
	   return factory.select(null, sql.toString());
   }
   /**
    * 校验导入的数据是否非本集中点对应的服务站产生的数据。
    * @param user
    * @return
    * @throws Exception
    */
   public QuerySet checkList7(User user, DBFactory factory)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT T.ROW_NUM,T.CLAIM_NO\n" );
	   sql.append("  FROM SE_BU_RETURNORDER_DTL_TMP T\n" );
	   sql.append(" WHERE NOT EXISTS (SELECT 1\n" );
	   sql.append("          FROM SE_BU_CLAIM C, SE_BA_RETURN_DEALER_RELATION D\n" );
	   sql.append("         WHERE T.CLAIM_NO = C.CLAIM_NO\n" );
	   sql.append("           AND C.ORG_ID = D.D_ORGID\n" );
	   sql.append("           AND D.R_ORGID = "+user.getOrgId()+" )");
	   sql.append("           AND T.USER_ACCOUNT ='"+user.getAccount()+"' \n" );
	   sql.append("    ORDER BY TO_NUMBER(T.ROW_NUM)  ");
	   return factory.select(null, sql.toString());
   }
   /**
    * 校验导入的旧件入库数量是否为空
    * @param user
    * @return
    * @throws Exception
    */
   public QuerySet checkList8(User user, DBFactory factory)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT  T.ROW_NO\n" );
	   sql.append("  FROM SE_BU_OLDPART_STORAGE_TMP T\n" );
	   sql.append(" WHERE T.STORAGE_COUNT IS NULL");
	   sql.append("   AND T.CREATE_USER ='"+user.getAccount()+"' \n" );
	   sql.append("    ORDER BY TO_NUMBER(T.ROW_NO)  ");
	   return factory.select(null, sql.toString());
   }
   /**
    * @param user
    * @return
    * @throws Exception
    */
   public QuerySet checkList9(User user, DBFactory factory)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT  T.ROW_NO,T.STORAGE_COUNT,T.RETURN_ORDER_NO, T.CLAIM_NO,T.FAULT_CODE,T.PART_CODE,T.SUPPLIER_CODE\n" );
	   sql.append("  FROM SE_BU_OLDPART_STORAGE_TMP T\n" );
	   sql.append(" WHERE T.STORAGE_COUNT IS NOT NULL");
	   sql.append("   AND T.CREATE_USER ='"+user.getAccount()+"' \n" );
	   sql.append("    ORDER BY TO_NUMBER(T.ROW_NO)  ");
	   return factory.select(null, sql.toString());
   }
   public QuerySet queryReturnNo(User user, DBFactory factory) throws Exception{
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT TMP.ROW_NO,TMP.RETURN_ORDER_NO\n" );
		 sql.append("  FROM SE_BU_OLDPART_STORAGE_TMP TMP\n" );
		 sql.append(" WHERE TMP.CREATE_USER = '"+user.getAccount()+"'\n" );
		 sql.append("   AND NOT EXISTS (SELECT  O.ORDER_NO\n" );
		 sql.append("          FROM SE_BU_RETURN_ORDER O\n" );
		 sql.append("         WHERE O.ORDER_NO = TMP.RETURN_ORDER_NO)");
		 return factory.select(null, sql.toString());
	 }
   public QuerySet queryClaimNo(User user, DBFactory factory) throws Exception{
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT TMP.ROW_NO,TMP.CLAIM_NO,TMP.RETURN_ORDER_NO\n" );
		 sql.append("  FROM SE_BU_OLDPART_STORAGE_TMP TMP\n" );
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
		 sql.append("  FROM SE_BU_OLDPART_STORAGE_TMP TMP\n" );
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
   public QuerySet queryCount(User user, DBFactory factory,String orderNo,String claimNo,String faultCode,String partCode,String supCode) throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT D.DETAIL_ID,\n" );
	   sql.append("       NVL(D.ALREADY_IN, 0) ALREADY_IN,\n" );
	   sql.append("       NVL(D.OUGHT_COUNT, 0) OUGHT_COUNT\n" );
	   sql.append("  FROM SE_BU_RETURNORDER_DETAIL D, SE_BU_CLAIM_FAULT T\n" );
	   sql.append(" WHERE T.CLAIM_DTL_ID = D.CLAIM_DTL_ID\n" );
	   sql.append("   AND T.CLAIM_ID = D.CLAIM_ID\n" );
	   sql.append("   AND D.ORDER_NO = '"+orderNo+"'\n" );
	   sql.append("   AND D.CLAIM_NO = '"+claimNo+"'\n" );
	   sql.append("   AND D.PART_CODE = '"+partCode+"'\n" );
	   sql.append("   AND D.PROSUPPLY_CODE = '"+supCode+"'\n" );
	   sql.append("   AND T.FAULT_CODE = '"+faultCode+"'");
	   return factory.select(null, sql.toString());
   }
   public QuerySet queryAll(User user, DBFactory factory) throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT T.CLAIM_NO, T.FAULT_CODE, T.PART_CODE, T.PROSUPPLY_CODE,T.ROW_NUM\n" );
	   sql.append("  FROM SE_BU_RETURNORDER_DTL_TMP T\n" );
	   sql.append(" WHERE T.USER_ACCOUNT = '"+user.getAccount()+"'");
	   return factory.select(null, sql.toString());
   }
   public QuerySet queryDtl(String claimNo,String faultCode,String partCode,String prosupplyCode,DBFactory factory) throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT D.ORDER_NO,D.ORDER_ID\n" );
	   sql.append("  FROM SE_BU_RETURNORDER_DETAIL D,SE_BU_CLAIM_FAULT F\n" );
	   sql.append(" WHERE D.PART_CODE ='"+partCode+"'\n" );
	   sql.append("   AND D.PROSUPPLY_CODE ='"+prosupplyCode+"'\n" );
	   sql.append("   AND D.CLAIM_NO ='"+claimNo+"'\n" );
	   sql.append("   AND F.FAULT_CODE='"+faultCode+"'\n" );
	   sql.append("   AND F.CLAIM_DTL_ID = D.CLAIM_DTL_ID");
	   return factory.select(null, sql.toString());
   }
}
