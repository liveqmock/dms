package com.org.dms.dao.part.financeMng.invoiceNotice;

import oracle.net.aso.d;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuSupInvoiceSummaryVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class InvoiceConfirmMngDao extends BaseDAO{
	public  static final InvoiceConfirmMngDao getInstance(ActionContext atx)
    {
		InvoiceConfirmMngDao dao = new InvoiceConfirmMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	
	public BaseResultSet invioceSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres +="AND (EXISTS\n" +
			"        (SELECT 1\n" + 
			"           FROM PT_BU_SUP_INVOICE_SUMMARY_DTL T1, PT_BU_PCH_ORDER_SPLIT T2\n" + 
			"          WHERE T.SUM_ID = T1.SUM_ID\n" + 
			"            AND T2.SPLIT_ID = T1.ORDER_ID\n" + 
			"            AND T2.INVOICE_STATUS = "+DicConstant.GYSKPZT_03+") OR EXISTS\n" + 
			"        (SELECT 1\n" + 
			"           FROM PT_BU_SUP_INVOICE_SUMMARY_DTL T1, PT_BU_PCH_RETURN T2\n" + 
			"          WHERE T.SUM_ID = T1.SUM_ID\n" + 
			"            AND T2.RETURN_ID = T1.ORDER_ID\n" + 
			"            AND T2.INVOICE_STATUS = "+DicConstant.GYSKPZT_03+"))";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.SUM_ID,\n" );
    	sql.append("       T.SUPPLIER_ID,\n" );
    	sql.append("       T.SUPPLIER_CODE,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("       T.IN_COUNT,\n" );
    	sql.append("       T.INVOICE_MONTH,\n" );
    	sql.append("       T.IN_AMOUNT,\n" );
    	sql.append("       T.RETURN_COUNT,\n" );
    	sql.append("       T.RETURN_AMOUNT,\n" );
    	sql.append("       T.HAS_INVOICE_AMOUNT,\n" );
    	sql.append("       T.INVOICE_STATUS\n" );
    	sql.append("  FROM PT_BU_SUP_INVOICE_SUMMARY T");
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("INVOICE_STATUS", "GYSKPZT");
    	return bs;
    }
	
	public BaseResultSet invioceOrderSearch(PageManager page, User user, String SUM_ID) throws Exception
    {
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT P.*,Q.TYPE\n" );
    	sql.append("  FROM (SELECT NVL(SUM(T.STORAGE_COUNT * T.PCH_PRICE), 0) AMOUNT,\n" );
    	sql.append("               NVL(SUM(T.STORAGE_COUNT), 0) COUNT,\n" );
    	sql.append("               T1.SPLIT_NO ORDER_NO,\n" );
    	sql.append("               T1.SPLIT_ID ORDER_ID,\n" );
    	sql.append("               T2.DIC_VALUE ORDER_TYPE,\n" );
    	sql.append("               T1.SUPPLIER_CODE,\n" );
    	sql.append("               T1.SUPPLIER_NAME,\n" );
    	sql.append("               T1.ORDER_NO OLD_ORDER_NO,\n" );
    	sql.append("               T1.INVOICE_NO,\n" );
    	sql.append("               T1.INVOICE_REMARKS\n" );
    	sql.append("          FROM PT_BU_PCH_ORDER_SPLIT_DTL T,\n" );
    	sql.append("               PT_BU_PCH_ORDER_SPLIT     T1,\n" );
    	sql.append("               DIC_TREE                  T2\n" );
    	sql.append("         WHERE T.SPLIT_ID = T1.SPLIT_ID\n" );
    	sql.append("           AND T1.ORDER_TYPE = T2.ID\n" );
    	sql.append("           AND T1.INVOICE_NO IS NOT NULL\n" );
    	sql.append("           AND T1.INVOICE_STATUS IN ("+DicConstant.GYSKPZT_03+")\n" );
    	sql.append("           AND T1.SETTLE_STATUS <> "+DicConstant.GYSJSZT_02+"\n" );
    	sql.append("         GROUP BY T1.SPLIT_NO,\n" );
    	sql.append("                  T2.DIC_VALUE,\n" );
    	sql.append("                  T1.SUPPLIER_CODE,\n" );
    	sql.append("                  T1.SUPPLIER_NAME,\n" );
    	sql.append("                  T1.ORDER_NO,\n" );
    	sql.append("                  T1.SPLIT_ID,\n" );
    	sql.append("                  T1.ORDER_TYPE,\n" );
    	sql.append("                  T1.INVOICE_NO,\n" );
    	sql.append("                  T1.INVOICE_REMARKS\n" );
    	sql.append("        UNION\n" );
    	sql.append("        SELECT NVL(SUM(T3.AMOUNT), 0) AMOUNT,\n" );
    	sql.append("               NVL(SUM(T3.COUNT), 0) COUNT,\n" );
    	sql.append("               T4.RETURN_NO ORDER_NO,\n" );
    	sql.append("               T4.RETURN_ID ORDER_ID,\n" );
    	sql.append("               '采购退货' ORDER_TYPE,\n" );
    	sql.append("               T4.SUPPLIER_CODE,\n" );
    	sql.append("               T4.SUPPLIER_NAME,\n" );
    	sql.append("               T4.PURCHASE_NO OLD_ORDER_NO,\n" );
    	sql.append("               T4.INVOICE_NO,\n" );
    	sql.append("               T4.INVOICE_REMARKS\n" );
    	sql.append("          FROM PT_BU_PCH_RETURN_DTL T3, PT_BU_PCH_RETURN T4\n" );
    	sql.append("         WHERE 1 = 1\n" );
    	sql.append("           AND T3.RETURN_ID = T4.RETURN_ID\n" );
    	sql.append("           AND T4.INVOICE_NO IS NOT NULL\n" );
    	sql.append("           AND T4.INVOICE_STATUS IN ("+DicConstant.GYSKPZT_03+")\n" );
    	sql.append("           AND T4.SETTLE_STATUS <> "+DicConstant.GYSJSZT_02+"\n" );
    	sql.append("         GROUP BY T4.RETURN_NO,\n" );
    	sql.append("                  '采购退货',\n" );
    	sql.append("                  T4.SUPPLIER_CODE,\n" );
    	sql.append("                  T4.SUPPLIER_NAME,\n" );
    	sql.append("                  T4.PURCHASE_NO,\n" );
    	sql.append("                  T4.RETURN_ID,\n" );
    	sql.append("                  T4.RETURN_TYPE,\n" );
    	sql.append("                  T4.INVOICE_NO,\n" );
    	sql.append("                  T4.INVOICE_REMARKS) P,\n" );
    	sql.append("       PT_BU_SUP_INVOICE_SUMMARY O,\n" );
    	sql.append("       PT_BU_SUP_INVOICE_SUMMARY_DTL Q\n" );
    	sql.append(" WHERE P.ORDER_ID = Q.ORDER_ID\n" );
    	sql.append("   AND O.SUM_ID = Q.SUM_ID\n" );
    	sql.append("   AND O.SUM_ID = "+SUM_ID+"");
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
	/**
	 * 
	 * @date()2014年8月14日下午5:13:44
	 * @author Administrator
	 * @to_do:更改开票汇审核状态
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean updateSum(PtBuSupInvoiceSummaryVO vo)
            throws Exception
    {
    	return factory.update(vo);
    }

	public QuerySet checkAll(String SUM_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT NVL(A.S_NO, 0) S_NO, NVL(R_NO, 0) R_NO\n" );
    	sql.append("  FROM (SELECT COUNT(T.SPLIT_ID) S_NO, T1.SUM_ID\n" );
    	sql.append("          FROM PT_BU_PCH_ORDER_SPLIT T, PT_BU_SUP_INVOICE_SUMMARY_DTL T1\n" );
    	sql.append("         WHERE T.SPLIT_ID = T1.ORDER_ID\n" );
    	sql.append("           AND T.INVOICE_NO IS NULL\n" );
    	sql.append("         GROUP BY T1.SUM_ID) A,\n" );
    	sql.append("       (SELECT COUNT(T2.RETURN_ID) R_NO, T3.SUM_ID\n" );
    	sql.append("          FROM PT_BU_PCH_RETURN T2, PT_BU_SUP_INVOICE_SUMMARY_DTL T3\n" );
    	sql.append("         WHERE T2.RETURN_ID = T3.ORDER_ID\n" );
    	sql.append("           AND T2.INVOICE_NO IS NULL\n" );
    	sql.append("         GROUP BY T3.SUM_ID) B\n" );
    	sql.append(" WHERE A.SUM_ID = B.SUM_ID(+)\n" );
    	sql.append("   AND A.SUM_ID = "+SUM_ID+"");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	public boolean updatePchOrder(String SUM_ID,String status) throws Exception
    {
		StringBuffer sql= new StringBuffer();
		sql.append("UPDATE PT_BU_PCH_ORDER_SPLIT\n" );
		sql.append("   SET INVOICE_STATUS = "+status+"\n" );
		sql.append(" WHERE SPLIT_ID IN (SELECT ORDER_ID\n" );
		sql.append("                      FROM PT_BU_SUP_INVOICE_SUMMARY_DTL\n" );
		sql.append("                     WHERE 1=1\n" );
		sql.append("                       AND SUM_ID = "+SUM_ID+") AND INVOICE_STATUS = "+DicConstant.GYSKPZT_03+"");
    	return factory.update(sql.toString(), null);
    }
	public boolean updateRetOrder(String SUM_ID,String status) throws Exception
    {
		StringBuffer sql= new StringBuffer();
		sql.append("UPDATE PT_BU_PCH_RETURN\n" );
		sql.append("   SET INVOICE_STATUS = "+status+"\n" );
		sql.append(" WHERE RETURN_ID IN (SELECT ORDER_ID\n" );
		sql.append("                      FROM PT_BU_SUP_INVOICE_SUMMARY_DTL\n" );
		sql.append("                     WHERE 1=1\n" );
		sql.append("                       AND SUM_ID = "+SUM_ID+") AND INVOICE_STATUS = "+DicConstant.GYSKPZT_03+"");
    	return factory.update(sql.toString(), null);
    }
	public boolean updateSumDtl(String SUM_ID,String status) throws Exception
    {
		StringBuffer sql= new StringBuffer();
		sql.append("UPDATE PT_BU_SUP_INVOICE_SUMMARY_DTL T SET T.I_STATUS = "+status+" WHERE T.I_STATUS = "+DicConstant.GYSKPZT_03+"AND T.SUM_ID = "+SUM_ID+" ");
    	return factory.update(sql.toString(), null);
    }
	public BaseResultSet invioceInfo(PageManager page, String SUM_ID, String conditions) throws Exception
    {
        String wheres = conditions;
        wheres += " AND T.SUM_ID = T0.SUM_ID AND T.SUM_ID = "+SUM_ID+"\n";
        page.setFilter(wheres);
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.INVOICE_NO         NO,\n" );
        sql.append("       T0.COM_NO            NO1,\n" );
        sql.append("       T.HAS_INVOICE_AMOUNT AMOUNTS,\n" );
        sql.append("       T.SUPPLIER_CODE,\n" );
        sql.append("       T.SUPPLIER_NAME,\n" );
        sql.append("       T.INVOICE_MONTH,\n" );
        sql.append("       T.REMARKS\n" );
        sql.append("  FROM PT_BU_SUP_INVOICE_SUMMARY T,\n" );
        sql.append("       (SELECT DISTINCT INVOICE_NO COM_NO, T2.SUM_ID\n" );
        sql.append("          FROM PT_BU_PCH_ORDER_SPLIT T1, PT_BU_SUP_INVOICE_SUMMARY_DTL T2\n" );
        sql.append("         WHERE T1.SPLIT_ID = T2.ORDER_ID\n" );
        sql.append("           AND T1.INVOICE_NO IS NOT NULL) T0\n" );
        bs = factory.select(sql.toString(), page);
        return bs;
    }
	
	public QuerySet getSet(String SUM_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT NVL(A.HAS_INVOICE_AMOUNT,0) HAS_INVOICE_AMOUNT, A.INVOICE_NO,NVL(A.UNSETTLE_AMOUNT,0) UNSETTLE_AMOUNT,NVL(A.SETTLE_AMOUNT,0) SETTLE_AMOUNT FROM PT_BU_SUP_INVOICE_SUMMARY A WHERE 1=1\n" );
    	sql.append("   AND A.SUM_ID = "+SUM_ID+"");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	public QuerySet getNo(String SUM_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT DISTINCT INVOICE_NO COM_NO\n" );
    	sql.append("  FROM PT_BU_PCH_ORDER_SPLIT T1, PT_BU_SUP_INVOICE_SUMMARY_DTL T2\n" );
    	sql.append(" WHERE T1.SPLIT_ID = T2.ORDER_ID\n" );
    	sql.append("   AND T1.INVOICE_NO IS NOT NULL AND T1.INVOICE_STATUS = 205203\n" );
    	sql.append("   AND T2.SUM_ID = "+SUM_ID+"");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
}
