package com.org.dms.dao.part.purchaseMng.purchaseReturn;

import com.org.dms.common.DicConstant;
import com.org.framework.common.DBFactory;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;

public class PurchaseReturnImportCheckDao {
	
	public QuerySet querySid(DBFactory factory, String bParams) throws Exception
	 {
		 QuerySet qs = null;
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT T.SUPPLIER_ID FROM PT_BU_PCH_RETURN T WHERE T.RETURN_ID = "+bParams+"\n" );
		 qs = factory.select(null, sql.toString());
		 return qs;
	 }
	public QuerySet queryId(DBFactory factory) throws Exception
	 {
		 QuerySet qs = null;
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT T.WAREHOUSE_ID FROM PT_BA_WAREHOUSE T WHERE T.WAREHOUSE_TYPE ="+DicConstant.SF_01+" AND T.STATUS="+DicConstant.YXBS_01+"\n" );
		 qs = factory.select(null, sql.toString());
		 return qs;
	 }
	public QuerySet queryIfSuply(DBFactory factory,User user,String bParams) throws Exception
	 {
		 QuerySet qs = null;
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT PART_CODE, TMP_NO\n" );
		 sql.append("  FROM PT_BU_PCH_RETURN_PART_TMP T\n" );
		 sql.append(" WHERE NOT EXISTS (SELECT 1\n" );
		 sql.append("          FROM PT_BA_PART_SUPPLIER_RL R, PT_BA_INFO P\n" );
		 sql.append("         WHERE R.PART_ID = P.PART_ID AND R.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n" );
		 sql.append("           AND R.SUPPLIER_ID =\n" );
		 sql.append("               (SELECT SUPPLIER_ID\n" );
		 sql.append("                  FROM PT_BU_PCH_RETURN\n" );
		 sql.append("                 WHERE RETURN_ID = "+bParams+")\n" );
		 sql.append("           AND T.PART_CODE = P.PART_CODE\n" );
		 sql.append("           AND T.USER_ACCOUNT = '"+user.getAccount()+"')");

		 qs = factory.select(null, sql.toString());
		 return qs;
	 }
	public QuerySet checkList1(DBFactory factory,User user) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.RETURN_NO,T.TMP_NO\n" );
    	sql.append("  FROM PT_BU_PCH_RETURN_PART_TMP T\n" );
    	sql.append(" WHERE NOT EXISTS (SELECT 1\n" );
    	sql.append("          FROM PT_BU_PCH_RETURN A\n" );
    	sql.append("         WHERE T.RETURN_NO = A.RETURN_NO\n" );
    	sql.append("           AND T.USER_ACCOUNT = '"+user.getAccount()+"')");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	public QuerySet checkPartCode(DBFactory factory,User user) throws Exception
	 {
		 QuerySet qs = null;
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT T.PART_CODE,T.TMP_NO\n" );
		 sql.append("  FROM PT_BU_PCH_RETURN_PART_TMP T\n" );
		 sql.append(" WHERE NOT EXISTS (SELECT *\n" );
		 sql.append("          FROM PT_BA_INFO A\n" );
		 sql.append("         WHERE T.PART_CODE = A.PART_CODE\n" );
		 sql.append("           AND T.USER_ACCOUNT = '"+user.getAccount()+"')");
		 qs = factory.select(null, sql.toString());
		 return qs;
	 }
	public QuerySet checkPartPoCode(DBFactory factory,User user) throws Exception
	 {
		 QuerySet qs = null;
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT T.PART_CODE,T.POSITION_CODE,T.TMP_NO\n" );
		 sql.append("  FROM PT_BU_PCH_RETURN_PART_TMP T\n" );
		 sql.append(" WHERE NOT EXISTS (SELECT 1\n" );
		 sql.append("          FROM PT_BA_WAREHOUSE_PART_RL R\n" );
		 sql.append("         WHERE T.PART_CODE = R.PART_CODE\n" );
		 sql.append("           AND T.POSITION_CODE = R.POSITION_CODE\n" );
		 sql.append("           AND T.USER_ACCOUNT = '"+user.getAccount()+"')");
		 qs = factory.select(null, sql.toString());
		 return qs;
	 }
	public QuerySet checkKthAmount(DBFactory factory,User user,String sId,String wId) throws Exception
	 {
		 QuerySet qs = null;
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT T.PART_CODE,T.POSITION_CODE,T.SL,TMP.RETURN_COUNT\n" );
		 sql.append("  FROM (SELECT T1.*,\n" );
		 sql.append("               (NVL(T1.STORAGE_COUNT, 0) - NVL(T2.RETURN_AMOUNT, 0)) SL\n" );
		 sql.append("          FROM (SELECT B.SUPPLIER_ID,\n" );
		 sql.append("                       D.PART_ID,\n" );
		 sql.append("                       D.PART_CODE,\n" );
		 sql.append("                       D.PART_NAME,\n" );
		 sql.append("                       D.UNIT,\n" );
		 sql.append("                       D.MIN_PACK,\n" );
		 sql.append("                       D.MIN_UNIT,\n" );
		 sql.append("                       D.PART_NO,\n" );
		 sql.append("                       E.PCH_PRICE,\n" );
		 sql.append("                       D.PLAN_PRICE,\n" );
		 sql.append("                       NVL(G.AVAILABLE_AMOUNT, 0) AVAILABLE_AMOUNT,\n" );
		 sql.append("                       G.POSITION_ID,\n" );
		 sql.append("                       G.POSITION_CODE,\n" );
		 sql.append("                       G.POSITION_NAME,\n" );
		 sql.append("                       SUM(NVL(B.STORAGE_COUNT, 0)) STORAGE_COUNT\n" );
		 sql.append("                  FROM PT_BU_PCH_ORDER_SPLIT     A,\n" );
		 sql.append("                       PT_BU_PCH_ORDER_SPLIT_DTL B,\n" );
		 sql.append("                       PT_BA_PCH_ATTRIBUTE       C,\n" );
		 sql.append("                       PT_BA_INFO                D,\n" );
		 sql.append("                       PT_BA_PART_SUPPLIER_RL    E,\n" );
		 sql.append("                       PT_BA_WAREHOUSE_PART_RL   F,\n" );
		 sql.append("                       PT_BU_STOCK_DTL           G\n" );
		 sql.append("                 WHERE A.SPLIT_ID = B.SPLIT_ID\n" );
		 sql.append("                   AND B.PART_ID = C.PART_ID\n" );
		 sql.append("                   AND B.PART_ID = D.PART_ID\n" );
		 sql.append("                   AND B.PART_ID = E.PART_ID\n" );
		 sql.append("                   AND F.PART_ID = B.PART_ID\n" );
		 sql.append("                   AND G.PART_ID = D.PART_ID\n" );
		 sql.append("                   AND G.POSITION_ID = F.POSITION_ID\n" );
		 sql.append("                   AND F.POSITION_ID IN\n" );
		 sql.append("                       (SELECT S.POSITION_ID\n" );
		 sql.append("                          FROM PT_BA_WAREHOUSE_POSITION S,\n" );
		 sql.append("                               PT_BA_WAREHOUSE_AREA     M,\n" );
		 sql.append("                               PT_BA_WAREHOUSE          N\n" );
		 sql.append("                         WHERE S.AREA_ID = M.AREA_ID\n" );
		 sql.append("                           AND M.WAREHOUSE_ID = N.WAREHOUSE_ID\n" );
		 sql.append("                           AND N.WAREHOUSE_ID = "+wId+")\n" );
		 sql.append("                   AND A.SUPPLIER_ID = E.SUPPLIER_ID AND E.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n" );
		 sql.append("                   AND B.SUPPLIER_ID = G.SUPPLIER_ID\n" );
		 sql.append("                   AND A.SUPPLIER_ID = "+sId+"\n" );
		 sql.append("                   AND B.STORAGE_COUNT > 0\n" );
		 sql.append("                 GROUP BY B.SUPPLIER_ID,\n" );
		 sql.append("                          G.AVAILABLE_AMOUNT,\n" );
		 sql.append("                          D.PART_ID,\n" );
		 sql.append("                          D.PART_CODE,\n" );
		 sql.append("                          D.PART_NAME,\n" );
		 sql.append("                          D.PART_NO,\n" );
		 sql.append("                          D.UNIT,\n" );
		 sql.append("                          D.MIN_PACK,\n" );
		 sql.append("                          D.MIN_UNIT,\n" );
		 sql.append("                          E.PCH_PRICE,\n" );
		 sql.append("                          D.PLAN_PRICE,\n" );
		 sql.append("                          G.POSITION_ID,\n" );
		 sql.append("                          G.POSITION_CODE,\n" );
		 sql.append("                          G.POSITION_NAME) T1\n" );
		 sql.append("          LEFT JOIN (SELECT G.SUPPLIER_ID,\n" );
		 sql.append("                           G.PART_ID,\n" );
		 sql.append("                           SUM(NVL(G.RETURN_AMOUNT, 0)) RETURN_AMOUNT\n" );
		 sql.append("                      FROM PT_BU_PCH_RETURN F, PT_BU_PCH_RETURN_DTL G\n" );
		 sql.append("                     WHERE F.RETURN_ID = G.RETURN_ID\n" );
		 sql.append("                       AND F.SUPPLIER_ID = "+sId+"\n" );
		 sql.append("                       AND G.RETURN_AMOUNT > 0\n" );
		 sql.append("                       AND F.RETURN_STATUS <> 201101\n" );
		 sql.append("                     GROUP BY G.SUPPLIER_ID, G.PART_ID) T2\n" );
		 sql.append("            ON T1.PART_ID = T2.PART_ID\n" );
		 sql.append("           AND T1.SUPPLIER_ID = T2.SUPPLIER_ID\n" );
		 sql.append("           AND T1.STORAGE_COUNT - T2.RETURN_AMOUNT > 0) T,\n" );
		 sql.append("       PT_BU_PCH_RETURN_PART_TMP TMP\n" );
		 sql.append(" WHERE T.PART_CODE = TMP.PART_CODE\n" );
		 sql.append("   AND T.POSITION_CODE = TMP.POSITION_CODE\n" );
		 sql.append("   AND TMP.RETURN_COUNT > T.SL");
		 sql.append("   AND TMP.USER_ACCOUNT = '"+user.getAccount()+"'");
		 qs = factory.select(null, sql.toString());
		 return qs;
	 }
	public QuerySet checkAvaAmount(DBFactory factory,User user,String sId,String wId) throws Exception
	 {
		 QuerySet qs = null;
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT T.PART_CODE, T.POSITION_CODE, T.AVAILABLE_AMOUNT, TMP.RETURN_COUNT\n" );
		 sql.append("  FROM (SELECT T1.AVAILABLE_AMOUNT, POSITION_CODE, PART_CODE\n" );
		 sql.append("          FROM (SELECT B.SUPPLIER_ID,\n" );
		 sql.append("                       D.PART_ID,\n" );
		 sql.append("                       D.PART_CODE,\n" );
		 sql.append("                       D.PART_NAME,\n" );
		 sql.append("                       D.UNIT,\n" );
		 sql.append("                       D.MIN_PACK,\n" );
		 sql.append("                       D.MIN_UNIT,\n" );
		 sql.append("                       D.PART_NO,\n" );
		 sql.append("                       E.PCH_PRICE,\n" );
		 sql.append("                       D.PLAN_PRICE,\n" );
		 sql.append("                       NVL(G.AVAILABLE_AMOUNT, 0) AVAILABLE_AMOUNT,\n" );
		 sql.append("                       G.POSITION_ID,\n" );
		 sql.append("                       G.POSITION_CODE,\n" );
		 sql.append("                       G.POSITION_NAME,\n" );
		 sql.append("                       SUM(NVL(B.STORAGE_COUNT, 0)) STORAGE_COUNT\n" );
		 sql.append("                  FROM PT_BU_PCH_ORDER_SPLIT     A,\n" );
		 sql.append("                       PT_BU_PCH_ORDER_SPLIT_DTL B,\n" );
		 sql.append("                       PT_BA_PCH_ATTRIBUTE       C,\n" );
		 sql.append("                       PT_BA_INFO                D,\n" );
		 sql.append("                       PT_BA_PART_SUPPLIER_RL    E,\n" );
		 sql.append("                       PT_BA_WAREHOUSE_PART_RL   F,\n" );
		 sql.append("                       PT_BU_STOCK_DTL           G\n" );
		 sql.append("                 WHERE A.SPLIT_ID = B.SPLIT_ID\n" );
		 sql.append("                   AND B.PART_ID = C.PART_ID\n" );
		 sql.append("                   AND B.PART_ID = D.PART_ID\n" );
		 sql.append("                   AND B.PART_ID = E.PART_ID\n" );
		 sql.append("                   AND F.PART_ID = B.PART_ID\n" );
		 sql.append("                   AND G.PART_ID = D.PART_ID\n" );
		 sql.append("                   AND G.POSITION_ID = F.POSITION_ID\n" );
		 sql.append("                   AND F.POSITION_ID IN\n" );
		 sql.append("                       (SELECT S.POSITION_ID\n" );
		 sql.append("                          FROM PT_BA_WAREHOUSE_POSITION S,\n" );
		 sql.append("                               PT_BA_WAREHOUSE_AREA     M,\n" );
		 sql.append("                               PT_BA_WAREHOUSE          N\n" );
		 sql.append("                         WHERE S.AREA_ID = M.AREA_ID\n" );
		 sql.append("                           AND M.WAREHOUSE_ID = N.WAREHOUSE_ID\n" );
		 sql.append("                           AND N.WAREHOUSE_ID = "+wId+")\n" );
		 sql.append("                   AND A.SUPPLIER_ID = E.SUPPLIER_ID AND E.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n" );
		 sql.append("                   AND B.SUPPLIER_ID = G.SUPPLIER_ID\n" );
		 sql.append("                   AND A.SUPPLIER_ID = "+sId+"\n" );
		 sql.append("                   AND B.STORAGE_COUNT > 0\n" );
		 sql.append("                 GROUP BY B.SUPPLIER_ID,\n" );
		 sql.append("                          G.AVAILABLE_AMOUNT,\n" );
		 sql.append("                          D.PART_ID,\n" );
		 sql.append("                          D.PART_CODE,\n" );
		 sql.append("                          D.PART_NAME,\n" );
		 sql.append("                          D.PART_NO,\n" );
		 sql.append("                          D.UNIT,\n" );
		 sql.append("                          D.MIN_PACK,\n" );
		 sql.append("                          D.MIN_UNIT,\n" );
		 sql.append("                          E.PCH_PRICE,\n" );
		 sql.append("                          D.PLAN_PRICE,\n" );
		 sql.append("                          G.POSITION_ID,\n" );
		 sql.append("                          G.POSITION_CODE,\n" );
		 sql.append("                          G.POSITION_NAME) T1\n" );
		 sql.append("          LEFT JOIN (SELECT G.SUPPLIER_ID,\n" );
		 sql.append("                           G.PART_ID,\n" );
		 sql.append("                           SUM(NVL(G.RETURN_AMOUNT, 0)) RETURN_AMOUNT\n" );
		 sql.append("                      FROM PT_BU_PCH_RETURN F, PT_BU_PCH_RETURN_DTL G\n" );
		 sql.append("                     WHERE F.RETURN_ID = G.RETURN_ID\n" );
		 sql.append("                       AND F.SUPPLIER_ID = "+sId+"\n" );
		 sql.append("                       AND G.RETURN_AMOUNT > 0\n" );
		 sql.append("                       AND F.RETURN_STATUS <> 201101\n" );
		 sql.append("                     GROUP BY G.SUPPLIER_ID, G.PART_ID) T2\n" );
		 sql.append("            ON T1.PART_ID = T2.PART_ID\n" );
		 sql.append("           AND T1.SUPPLIER_ID = T2.SUPPLIER_ID\n" );
		 sql.append("           AND T1.STORAGE_COUNT - T2.RETURN_AMOUNT > 0) T,\n" );
		 sql.append("       PT_BU_PCH_RETURN_PART_TMP TMP\n" );
		 sql.append(" WHERE T.PART_CODE = TMP.PART_CODE\n" );
		 sql.append("   AND T.POSITION_CODE = TMP.POSITION_CODE\n" );
		 sql.append("   AND TMP.RETURN_COUNT > T.AVAILABLE_AMOUNT");
		 qs = factory.select(null, sql.toString());
		 return qs;
	 }

}
