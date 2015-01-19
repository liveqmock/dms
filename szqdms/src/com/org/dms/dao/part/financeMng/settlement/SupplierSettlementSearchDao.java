package com.org.dms.dao.part.financeMng.settlement;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class SupplierSettlementSearchDao extends BaseDAO{
	public  static final SupplierSettlementSearchDao getInstance(ActionContext atx)
    {
		SupplierSettlementSearchDao dao = new SupplierSettlementSearchDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	
	
	public BaseResultSet settleSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres +=" AND T.SETTLE_STATUS IN("+DicConstant.GYSJSZT_01+","+DicConstant.GYSJSZT_02+") AND T.INVOICE_STATUS ="+DicConstant.GYSKPZT_05+"\n";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.SUM_ID,\n" );
    	sql.append("       T.SUPPLIER_ID,\n" );
    	sql.append("       T.SUPPLIER_CODE,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("       T.IN_COUNT,\n" );
    	sql.append("       T.IN_AMOUNT,\n" );
    	sql.append("       T.RETURN_COUNT,\n" );
    	sql.append("       T.RETURN_AMOUNT,\n" );
    	sql.append("       T.INVOICE_AMOUNT,\n" );
    	sql.append("       T.PLAN_AMOUNT,\n" );
    	sql.append("       NVL(T.SETTLE_AMOUNT,0) SETTLE_AMOUNT,\n" );
    	sql.append("       NVL(T.UNSETTLE_AMOUNT,0) UNSETTLE_AMOUNT,\n" );
    	sql.append("       T.INVOICE_MONTH,\n" );
    	sql.append("       T.SETTLE_STATUS\n" );
    	sql.append("  FROM PT_BU_SUP_INVOICE_SUMMARY T");
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("SETTLE_STATUS", "GYSJSZT");
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
    	sql.append("           AND T1.INVOICE_STATUS IN ("+DicConstant.GYSKPZT_03+","+DicConstant.GYSKPZT_05+")\n" );
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
    	sql.append("               T5.DIC_VALUE ORDER_TYPE,\n" );
    	sql.append("               T4.SUPPLIER_CODE,\n" );
    	sql.append("               T4.SUPPLIER_NAME,\n" );
    	sql.append("               T4.PURCHASE_NO OLD_ORDER_NO,\n" );
    	sql.append("               T4.INVOICE_NO,\n" );
    	sql.append("               T4.INVOICE_REMARKS\n" );
    	sql.append("          FROM PT_BU_PCH_RETURN_DTL T3, PT_BU_PCH_RETURN T4, DIC_TREE T5\n" );
    	sql.append("         WHERE 1 = 1\n" );
    	sql.append("           AND T3.RETURN_ID = T4.RETURN_ID\n" );
    	sql.append("           AND T4.INVOICE_STATUS IN ("+DicConstant.GYSKPZT_03+","+DicConstant.GYSKPZT_05+")\n" );
    	sql.append("           AND T4.RETURN_TYPE = T5.ID\n" );
    	sql.append("         GROUP BY T4.RETURN_NO,\n" );
    	sql.append("                  T5.DIC_VALUE,\n" );
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
	public BaseResultSet settleInfo(PageManager page, String SUM_ID, String conditions) throws Exception
    {
        String wheres = conditions;
        wheres += " AND T.SUM_ID = "+SUM_ID+" AND T.SETTLE_STATUS=T1.ID\n";
        page.setFilter(wheres);
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.INVOICE_NO NO,\n" );
        sql.append("       T.SETTLE_AMOUNT,\n" );
        sql.append("       T.UNSETTLE_AMOUNT,\n" );
        sql.append("       T1.DIC_VALUE SETTLE_STATUS,\n" );
        sql.append("       T.SUPPLIER_CODE,\n" );
        sql.append("       T.SUPPLIER_NAME,\n" );
        sql.append("       T.INVOICE_MONTH\n" );
        sql.append("  FROM PT_BU_SUP_INVOICE_SUMMARY T,DIC_TREE T1");
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("SETTLE_STATUS", "GYSJSZT");
        return bs;
    }

}
