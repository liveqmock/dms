package com.org.dms.dao.part.financeMng.invoiceNotice;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class SupInoviceSearchMngDao extends BaseDAO{
	public  static final SupInoviceSearchMngDao getInstance(ActionContext atx)
    {
		SupInoviceSearchMngDao dao = new SupInoviceSearchMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	public BaseResultSet invioceSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres +=" ORDER BY A.SUPPLIER_CODE, A.ORDER_NO";
    	page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT A.* FROM\n" );
    	sql.append("(SELECT T.SUPPLIER_ID,\n" );
    	sql.append("       T.SUPPLIER_CODE,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("       T.INVOICE_MONTH,\n" );
    	sql.append("       T1.TYPE,\n" );
    	sql.append("       T2.SPLIT_NO ORDER_NO,\n" );
    	sql.append("       T2.SPLIT_ID ORDER_ID,\n" );
    	sql.append("       T2.PURCHASE_AMOUNT,\n" );
    	sql.append("       T2.INVOICE_STATUS,\n" );
    	sql.append("       T2.INVOICE_DATE,\n" );
    	sql.append("       T2.INVOICE_NO,\n" );
    	sql.append("       T2.APPLY_USER\n" );
    	sql.append("  FROM PT_BU_SUP_INVOICE_SUMMARY     T,\n" );
    	sql.append("       PT_BU_SUP_INVOICE_SUMMARY_DTL T1,\n" );
    	sql.append("       PT_BU_PCH_ORDER_SPLIT         T2\n" );
    	sql.append(" WHERE 1 = 1\n" );
    	sql.append("   AND T.SUM_ID = T1.SUM_ID\n" );
    	sql.append("   AND T1.ORDER_ID = T2.SPLIT_ID\n" );
    	sql.append("   AND NVL(T2.STORAGE_COUNT, 0) > 0\n" );
    	sql.append("   UNION ALL\n" );
    	sql.append("   SELECT T.SUPPLIER_ID,\n" );
    	sql.append("       T.SUPPLIER_CODE,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("       T.INVOICE_MONTH,\n" );
    	sql.append("       T1.TYPE,\n" );
    	sql.append("       T2.RETURN_NO ORDER_NO,\n" );
    	sql.append("       T2.RETURN_ID ORDER_ID,\n" );
    	sql.append("       0-T2.AMOUNT PURCHASE_AMOUNT,\n" );
    	sql.append("       T2.INVOICE_STATUS,\n" );
    	sql.append("       T2.INVOICE_DATE,\n" );
    	sql.append("       T2.INVOICE_NO,\n" );
    	sql.append("       T2.CREATE_USER APPLY_USER\n" );
    	sql.append("  FROM PT_BU_SUP_INVOICE_SUMMARY     T,\n" );
    	sql.append("       PT_BU_SUP_INVOICE_SUMMARY_DTL T1,\n" );
    	sql.append("       PT_BU_PCH_RETURN         T2\n" );
    	sql.append(" WHERE 1 = 1\n" );
    	sql.append("   AND T.SUM_ID = T1.SUM_ID\n" );
    	sql.append("   AND T1.ORDER_ID = T2.RETURN_ID\n" );
    	sql.append("   AND NVL(T2.AMOUNT, 0) > 0) A");
//    	StringBuffer sql= new StringBuffer();
//    	sql.append("SELECT T.SUPPLIER_ID,\n" );
//    	sql.append("       T.SUPPLIER_CODE,\n" );
//    	sql.append("       T.SUPPLIER_NAME,\n" );
//    	sql.append("       T.INVOICE_MONTH,\n" );
//    	sql.append("       T2.SPLIT_NO,\n" );
//    	sql.append("       T2.SPLIT_ID,\n" );
//    	sql.append("       T2.PURCHASE_AMOUNT,\n" );
//    	sql.append("       T2.INVOICE_STATUS,\n" );
//    	sql.append("       T2.INVOICE_DATE,\n" );
//    	sql.append("       T2.INVOICE_NO,\n" );
//    	sql.append("       T2.APPLY_USER\n" );
//    	sql.append("  FROM PT_BU_SUP_INVOICE_SUMMARY     T,\n" );
//    	sql.append("       PT_BU_SUP_INVOICE_SUMMARY_DTL T1,\n" );
//    	sql.append("       PT_BU_PCH_ORDER_SPLIT         T2");
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("INVOICE_STATUS", "GYSKPZT");
    	bs.setFieldUserID("APPLY_USER");
    	bs.setFieldDateFormat("INVOICE_DATE", "yyyy-MM-dd");
    	return bs;
    }
	public BaseResultSet supplierInvioceSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres +=" AND A.SUPPLIER_ID = T3.SUPPLIER_ID"+
    			 " AND T3.ORG_ID = "+user.getOrgId()+""+
    			 " ORDER BY A.SUPPLIER_CODE, A.ORDER_NO";
    	page.setFilter(wheres);
    	BaseResultSet bs = null;
//    	StringBuffer sql= new StringBuffer();
//    	sql.append("SELECT T.SUPPLIER_ID,\n" );
//    	sql.append("       T.SUPPLIER_CODE,\n" );
//    	sql.append("       T.SUPPLIER_NAME,\n" );
//    	sql.append("       T.INVOICE_MONTH,\n" );
//    	sql.append("       T2.SPLIT_NO,\n" );
//    	sql.append("       T2.SPLIT_ID,\n" );
//    	sql.append("       T2.PURCHASE_AMOUNT,\n" );
//    	sql.append("       T2.INVOICE_STATUS,\n" );
//    	sql.append("       T2.INVOICE_DATE,\n" );
//    	sql.append("       T2.INVOICE_NO,\n" );
//    	sql.append("       T2.APPLY_USER\n" );
//    	sql.append("  FROM PT_BU_SUP_INVOICE_SUMMARY     T,\n" );
//    	sql.append("       PT_BU_SUP_INVOICE_SUMMARY_DTL T1,\n" );
//    	sql.append("       PT_BU_PCH_ORDER_SPLIT         T2,");
//    	sql.append("       PT_BA_SUPPLIER                T3");
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT A.* FROM\n" );
    	sql.append("(SELECT T.SUPPLIER_ID,\n" );
    	sql.append("       T.SUPPLIER_CODE,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("       T.INVOICE_MONTH,\n" );
    	sql.append("       T1.TYPE,\n" );
    	sql.append("       T2.SPLIT_NO ORDER_NO,\n" );
    	sql.append("       T2.SPLIT_ID ORDER_ID,\n" );
    	sql.append("       T2.PURCHASE_AMOUNT,\n" );
    	sql.append("       T2.INVOICE_STATUS,\n" );
    	sql.append("       T2.INVOICE_DATE,\n" );
    	sql.append("       T2.INVOICE_NO,\n" );
    	sql.append("       T2.APPLY_USER\n" );
    	sql.append("  FROM PT_BU_SUP_INVOICE_SUMMARY     T,\n" );
    	sql.append("       PT_BU_SUP_INVOICE_SUMMARY_DTL T1,\n" );
    	sql.append("       PT_BU_PCH_ORDER_SPLIT         T2\n" );
    	sql.append(" WHERE 1 = 1\n" );
    	sql.append("   AND T.SUM_ID = T1.SUM_ID\n" );
    	sql.append("   AND T1.ORDER_ID = T2.SPLIT_ID\n" );
    	sql.append("   AND NVL(T2.STORAGE_COUNT, 0) > 0\n" );
    	sql.append("   UNION ALL\n" );
    	sql.append("   SELECT T.SUPPLIER_ID,\n" );
    	sql.append("       T.SUPPLIER_CODE,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("       T.INVOICE_MONTH,\n" );
    	sql.append("       T1.TYPE,\n" );
    	sql.append("       T2.RETURN_NO ORDER_NO,\n" );
    	sql.append("       T2.RETURN_ID ORDER_ID,\n" );
    	sql.append("       0-T2.AMOUNT PURCHASE_AMOUNT,\n" );
    	sql.append("       T2.INVOICE_STATUS,\n" );
    	sql.append("       T2.INVOICE_DATE,\n" );
    	sql.append("       T2.INVOICE_NO,\n" );
    	sql.append("       T2.CREATE_USER APPLY_USER\n" );
    	sql.append("  FROM PT_BU_SUP_INVOICE_SUMMARY     T,\n" );
    	sql.append("       PT_BU_SUP_INVOICE_SUMMARY_DTL T1,\n" );
    	sql.append("       PT_BU_PCH_RETURN         T2\n" );
    	sql.append(" WHERE 1 = 1\n" );
    	sql.append("   AND T.SUM_ID = T1.SUM_ID\n" );
    	sql.append("   AND T1.ORDER_ID = T2.RETURN_ID\n" );
    	sql.append("   AND NVL(T2.AMOUNT, 0) > 0) A,PT_BA_SUPPLIER T3");
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("INVOICE_STATUS", "GYSKPZT");
    	bs.setFieldUserID("APPLY_USER");
    	bs.setFieldDateFormat("INVOICE_DATE", "yyyy-MM-dd");
    	return bs;
    }
	
	public BaseResultSet saleInvioceSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres +="AND T.SUM_ID = T1.SUM_ID\n" +
    					"   AND T1.ORDER_ID = T2.ORDER_ID\n" + 
    					"   AND NVL(T2.REAL_AMOUNT, 0) > 0\n" + 
    					" ORDER BY T.ORG_CODE, T2.ORDER_NO";
    	page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT DISTINCT T2.ORDER_ID,T.ORG_ID,\n" );
    	sql.append("       T.ORG_CODE,\n" );
    	sql.append("       T.ORG_NAME,\n" );
    	sql.append("       T.INVOICE_MONTH,\n" );
    	sql.append("       T2.ORDER_NO,\n" );
    	sql.append("       T2.REAL_AMOUNT,\n" );
    	sql.append("       T2.INVOICE_STATUS,\n" );
    	sql.append("       T.INVOICE_DATE,\n" );
    	sql.append("       T2.INVOICE_NO\n" );
    	sql.append("  FROM PT_BU_DEALER_INVOICE_SUMMARY     T,\n" );
    	sql.append("       PT_BU_DEALER_INVOICE_DTL T1,\n" );
    	sql.append("       PT_BU_SALE_ORDER         T2");
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("INVOICE_STATUS", "KPZT");
    	bs.setFieldDateFormat("INVOICE_DATE", "yyyy-MM-dd");
    	return bs;
    }
	public BaseResultSet dealerSaleInvioceSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres +="AND T.SUM_ID = T1.SUM_ID\n" +
    					"   AND T1.ORDER_ID = T2.ORDER_ID\n" + 
    					"   AND NVL(T2.REAL_AMOUNT, 0) > 0\n" + 
    					"   AND T2.ORG_ID = "+user.getOrgId()+"\n" + 
    					" ORDER BY T.ORG_CODE, T2.ORDER_NO";
    	page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT DISTINCT T2.ORDER_ID,T.ORG_ID,\n" );
    	sql.append("       T.ORG_CODE,\n" );
    	sql.append("       T.ORG_NAME,\n" );
    	sql.append("       T.INVOICE_MONTH,\n" );
    	sql.append("       T2.ORDER_NO,\n" );
    	sql.append("       T2.REAL_AMOUNT,\n" );
    	sql.append("       T2.INVOICE_STATUS,\n" );
    	sql.append("       T.INVOICE_DATE,\n" );
    	sql.append("       T2.INVOICE_NO\n" );
    	sql.append("  FROM PT_BU_DEALER_INVOICE_SUMMARY     T,\n" );
    	sql.append("       PT_BU_DEALER_INVOICE_DTL T1,\n" );
    	sql.append("       PT_BU_SALE_ORDER         T2");
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("INVOICE_STATUS", "KPZT");
    	bs.setFieldDateFormat("INVOICE_DATE", "yyyy-MM-dd");
    	return bs;
    }
	public QuerySet download(String conditions,User user) throws Exception {
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT A.*\n" );
		sql.append("  FROM (SELECT T.SUPPLIER_ID,\n" );
		sql.append("               T.SUPPLIER_CODE,\n" );
		sql.append("               T.SUPPLIER_NAME,\n" );
		sql.append("               T.INVOICE_MONTH,\n" );
		sql.append("               T1.TYPE,\n" );
		sql.append("               T2.SPLIT_NO        ORDER_NO,\n" );
		sql.append("               T2.SPLIT_ID        ORDER_ID,\n" );
		sql.append("               T2.PURCHASE_AMOUNT,\n" );
		sql.append("               T4.DIC_VALUE       INVOICE_STATUS,\n" );
		sql.append("               TO_CHAR(T2.INVOICE_DATE,'YYYY-MM-DD') INVOICE_DATE,\n" );
		sql.append("               T2.INVOICE_NO,\n" );
		sql.append("               T3.PERSON_NAME     APPLY_USER\n" );
		sql.append("          FROM PT_BU_SUP_INVOICE_SUMMARY     T,\n" );
		sql.append("               PT_BU_SUP_INVOICE_SUMMARY_DTL T1,\n" );
		sql.append("               PT_BU_PCH_ORDER_SPLIT         T2,\n" );
		sql.append("               TM_USER                       T3,\n" );
		sql.append("               DIC_TREE                      T4\n" );
		sql.append("         WHERE 1 = 1\n" );
		sql.append("           AND T.SUM_ID = T1.SUM_ID\n" );
		sql.append("           AND T2.APPLY_USER = T3.ACCOUNT\n" );
		sql.append("           AND T2.INVOICE_STATUS = T4.ID(+)\n" );
		sql.append("           AND T1.ORDER_ID = T2.SPLIT_ID\n" );
		sql.append("           AND NVL(T2.STORAGE_COUNT, 0) > 0\n" );
		sql.append("        UNION ALL\n" );
		sql.append("        SELECT T.SUPPLIER_ID,\n" );
		sql.append("               T.SUPPLIER_CODE,\n" );
		sql.append("               T.SUPPLIER_NAME,\n" );
		sql.append("               T.INVOICE_MONTH,\n" );
		sql.append("               T1.TYPE,\n" );
		sql.append("               T2.RETURN_NO ORDER_NO,\n" );
		sql.append("               T2.RETURN_ID ORDER_ID,\n" );
		sql.append("               0 - T2.AMOUNT PURCHASE_AMOUNT,\n" );
		sql.append("               T4.DIC_VALUE INVOICE_STATUS,\n" );
		sql.append("               TO_CHAR(T2.INVOICE_DATE,'YYYY-MM-DD') INVOICE_DATE,\n" );
		sql.append("               T2.INVOICE_NO,\n" );
		sql.append("               T3.PERSON_NAME APPLY_USER\n" );
		sql.append("          FROM PT_BU_SUP_INVOICE_SUMMARY     T,\n" );
		sql.append("               PT_BU_SUP_INVOICE_SUMMARY_DTL T1,\n" );
		sql.append("               PT_BU_PCH_RETURN              T2,\n" );
		sql.append("               TM_USER                       T3,\n" );
		sql.append("               DIC_TREE                      T4\n" );
		sql.append("         WHERE 1 = 1\n" );
		sql.append("           AND T.SUM_ID = T1.SUM_ID\n" );
		sql.append("           AND T2.INVOICE_STATUS = T4.ID(+)\n" );
		sql.append("           AND T1.ORDER_ID = T2.RETURN_ID\n" );
		sql.append("           AND T2.CREATE_USER = T3.ACCOUNT\n" );
		sql.append("           AND NVL(T2.AMOUNT, 0) > 0) A WHERE "+conditions+"\n" );
		sql.append(" ORDER BY A.SUPPLIER_CODE, A.ORDER_NO");
        return factory.select(null, sql.toString());
    }
	//supExportExcel
	public QuerySet supExportExcel(String conditions,User user) throws Exception {
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT A.*\n" );
		sql.append("  FROM (SELECT T.SUPPLIER_ID,\n" );
		sql.append("               T.SUPPLIER_CODE,\n" );
		sql.append("               T.SUPPLIER_NAME,\n" );
		sql.append("               T.INVOICE_MONTH,\n" );
		sql.append("               T1.TYPE,\n" );
		sql.append("               T2.SPLIT_NO        ORDER_NO,\n" );
		sql.append("               T2.SPLIT_ID        ORDER_ID,\n" );
		sql.append("               T2.PURCHASE_AMOUNT,\n" );
		sql.append("               T4.DIC_VALUE       INVOICE_STATUS,\n" );
		sql.append("               TO_CHAR(T2.INVOICE_DATE,'YYYY-MM-DD') INVOICE_DATE,\n" );
		sql.append("               T2.INVOICE_NO,\n" );
		sql.append("               T3.PERSON_NAME     APPLY_USER\n" );
		sql.append("          FROM PT_BU_SUP_INVOICE_SUMMARY     T,\n" );
		sql.append("               PT_BU_SUP_INVOICE_SUMMARY_DTL T1,\n" );
		sql.append("               PT_BU_PCH_ORDER_SPLIT         T2,\n" );
		sql.append("               TM_USER                       T3,\n" );
		sql.append("               DIC_TREE                      T4\n" );
		sql.append("         WHERE 1 = 1\n" );
		sql.append("           AND T.SUM_ID = T1.SUM_ID\n" );
		sql.append("           AND T2.APPLY_USER = T3.ACCOUNT\n" );
		sql.append("           AND T2.INVOICE_STATUS = T4.ID(+)\n" );
		sql.append("           AND T1.ORDER_ID = T2.SPLIT_ID\n" );
		sql.append("           AND NVL(T2.STORAGE_COUNT, 0) > 0\n" );
		sql.append("        UNION ALL\n" );
		sql.append("        SELECT T.SUPPLIER_ID,\n" );
		sql.append("               T.SUPPLIER_CODE,\n" );
		sql.append("               T.SUPPLIER_NAME,\n" );
		sql.append("               T.INVOICE_MONTH,\n" );
		sql.append("               T1.TYPE,\n" );
		sql.append("               T2.RETURN_NO ORDER_NO,\n" );
		sql.append("               T2.RETURN_ID ORDER_ID,\n" );
		sql.append("               0 - T2.AMOUNT PURCHASE_AMOUNT,\n" );
		sql.append("               T4.DIC_VALUE INVOICE_STATUS,\n" );
		sql.append("               TO_CHAR(T2.INVOICE_DATE,'YYYY-MM-DD') INVOICE_DATE,\n" );
		sql.append("               T2.INVOICE_NO,\n" );
		sql.append("               T3.PERSON_NAME APPLY_USER\n" );
		sql.append("          FROM PT_BU_SUP_INVOICE_SUMMARY     T,\n" );
		sql.append("               PT_BU_SUP_INVOICE_SUMMARY_DTL T1,\n" );
		sql.append("               PT_BU_PCH_RETURN              T2,\n" );
		sql.append("               TM_USER                       T3,\n" );
		sql.append("               DIC_TREE                      T4\n" );
		sql.append("         WHERE 1 = 1\n" );
		sql.append("           AND T.SUM_ID = T1.SUM_ID\n" );
		sql.append("           AND T2.INVOICE_STATUS = T4.ID(+)\n" );
		sql.append("           AND T1.ORDER_ID = T2.RETURN_ID\n" );
		sql.append("           AND T2.CREATE_USER = T3.ACCOUNT\n" );
		sql.append("           AND NVL(T2.AMOUNT, 0) > 0) A,PT_BA_SUPPLIER B WHERE A.SUPPLIER_ID = B.SUPPLIER_ID AND B.ORG_ID = "+user.getOrgId()+" AND "+conditions+"\n" );
		sql.append(" ORDER BY A.SUPPLIER_CODE, A.ORDER_NO");
        return factory.select(null, sql.toString());
    }
	//saleExportExcel
	public QuerySet saleExportExcel(String conditions,User user) throws Exception {
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT DISTINCT T2.ORDER_ID,\n" );
		sql.append("                T.ORG_ID,\n" );
		sql.append("                T.ORG_CODE,\n" );
		sql.append("                T.ORG_NAME,\n" );
		sql.append("                T.INVOICE_MONTH,\n" );
		sql.append("                T2.ORDER_NO,\n" );
		sql.append("                T2.REAL_AMOUNT,\n" );
		sql.append("                T3.DIC_VALUE INVOICE_STATUS,\n" );
		sql.append("                TO_CHAR(T.INVOICE_DATE,'YYYY-MM-DD') INVOICE_DATE,\n" );
		sql.append("                T2.INVOICE_NO\n" );
		sql.append("  FROM PT_BU_DEALER_INVOICE_SUMMARY T,\n" );
		sql.append("       PT_BU_DEALER_INVOICE_DTL     T1,\n" );
		sql.append("       PT_BU_SALE_ORDER             T2,\n" );
		sql.append("       DIC_TREE                     T3\n" );
		sql.append(" WHERE 1 = 1\n" );
		sql.append("   AND T.SUM_ID = T1.SUM_ID\n" );
		sql.append("   AND T1.ORDER_ID = T2.ORDER_ID\n" );
		sql.append("   AND T2.INVOICE_STATUS = T3.ID\n" );
		sql.append("   AND NVL(T2.REAL_AMOUNT, 0) > 0\n" );
		sql.append(" ORDER BY T.ORG_CODE, T2.ORDER_NO");
        return factory.select(null, sql.toString());
    }
	public QuerySet dealerExportExcel(String conditions,User user) throws Exception {
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT DISTINCT T2.ORDER_ID,\n" );
		sql.append("                T.ORG_ID,\n" );
		sql.append("                T.ORG_CODE,\n" );
		sql.append("                T.ORG_NAME,\n" );
		sql.append("                T.INVOICE_MONTH,\n" );
		sql.append("                T2.ORDER_NO,\n" );
		sql.append("                T2.REAL_AMOUNT,\n" );
		sql.append("                T3.DIC_VALUE INVOICE_STATUS,\n" );
		sql.append("                TO_CHAR(T.INVOICE_DATE,'YYYY-MM-DD') INVOICE_DATE,\n" );
		sql.append("                T2.INVOICE_NO\n" );
		sql.append("  FROM PT_BU_DEALER_INVOICE_SUMMARY T,\n" );
		sql.append("       PT_BU_DEALER_INVOICE_DTL     T1,\n" );
		sql.append("       PT_BU_SALE_ORDER             T2,\n" );
		sql.append("       DIC_TREE                     T3 \n" );
		sql.append(" WHERE 1 = 1\n" );
		sql.append("   AND T.SUM_ID = T1.SUM_ID\n" );
		sql.append("   AND T1.ORDER_ID = T2.ORDER_ID\n" );
		sql.append("   AND T2.INVOICE_STATUS = T3.ID\n" );
		sql.append("   AND T.ORG_ID = "+user.getOrgId()+"\n" );
		sql.append("   AND NVL(T2.REAL_AMOUNT, 0) > 0\n" );
		sql.append(" ORDER BY T.ORG_CODE, T2.ORDER_NO");
        return factory.select(null, sql.toString());
    }
}
