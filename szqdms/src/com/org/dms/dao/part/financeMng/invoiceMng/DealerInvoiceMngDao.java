package com.org.dms.dao.part.financeMng.invoiceMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuDealerInvoiceSummaryVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class DealerInvoiceMngDao extends BaseDAO{

	public  static final DealerInvoiceMngDao getInstance(ActionContext atx)
    {
		DealerInvoiceMngDao dao = new DealerInvoiceMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	
	public BaseResultSet invioceSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres +="AND NVL(T.INVOICE_AMOUNT, 0) > 0\n" +
    			"AND T.INVOICE_STATUS = "+DicConstant.KPZT_01+"\n";
    	page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.SUM_ID,\n" );
    	sql.append("       T.ORG_ID,\n" );
    	sql.append("       T.ORG_CODE,\n" );
    	sql.append("       T.ORG_NAME,\n" );
    	sql.append("       T.INVOICE_MONTH,\n" );
    	sql.append("       T.SALE_COUNT,\n" );
    	sql.append("       T.SALE_AMOUNT,\n" );
    	sql.append("       NVL(T.RETURN_COUNT,0) RETURN_COUNT,\n" );
    	sql.append("       NVL(T.RETURN_AMOUNT,0) RETURN_AMOUNT,\n" );
    	sql.append("       T.INVOICE_AMOUNT\n" );
    	sql.append("  FROM PT_BU_DEALER_INVOICE_SUMMARY T");
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
	public BaseResultSet invioceOrderSearch(PageManager page, User user, String SUM_ID) throws Exception
    {
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.*\n" );
    	sql.append("  FROM (SELECT SUM(NVL(T2.DELIVERY_COUNT, 0) * NVL(T2.UNIT_PRICE, 0)) AMOUNT,\n" );
    	sql.append("               SUM(NVL(T2.DELIVERY_COUNT, 0)) COUNT,\n" );
    	sql.append("               T1.ORDER_NO ORDER_NO,\n" );
    	sql.append("               T1.ORDER_ID ORDER_ID,\n" );
    	sql.append("               T4.DIC_VALUE ORDER_TYPE,\n" );
    	sql.append("               '1' TYPE,\n" );
    	sql.append("               T1.INVOICE_STATUS\n" );
    	sql.append("          FROM PT_BU_SALE_ORDER         T1,\n" );
    	sql.append("               PT_BU_SALE_ORDER_DTL     T2,\n" );
    	sql.append("               PT_BU_DEALER_INVOICE_DTL T3,\n" );
    	sql.append("               DIC_TREE                 T4\n" );
    	sql.append("         WHERE T1.ORDER_ID = T2.ORDER_ID\n" );
    	sql.append("           AND T1.ORDER_ID = T3.ORDER_ID\n" );
    	sql.append("           AND T1.ORDER_TYPE = T4.ID\n" );
    	sql.append("           AND T3.SUM_ID = "+SUM_ID+"\n" );
    	sql.append("         GROUP BY T1.ORDER_NO,\n" );
    	sql.append("                  T1.ORDER_ID,\n" );
    	sql.append("                  T4.DIC_VALUE,\n" );
    	sql.append("                  '1',\n" );
    	sql.append("                  T1.INVOICE_STATUS\n" );
    	sql.append("        UNION\n" );
    	sql.append("        SELECT SUM(NVL(T2.RETURN_COUNT, 0) * NVL(T2.SALE_PRICE, 0)) AMOUNT,\n" );
    	sql.append("               SUM(NVL(T2.RETURN_COUNT, 0)) COUNT,\n" );
    	sql.append("               T1.RETURN_NO ORDER_NO,\n" );
    	sql.append("               T1.RETURN_ID ORDER_ID,\n" );
    	sql.append("               '销售退库' ORDER_TYPE,\n" );
    	sql.append("               '2' TYPE,\n" );
    	sql.append("               T1.INVOICE_STATUS\n" );
    	sql.append("          FROM PT_BU_RETURN_APPLY       T1,\n" );
    	sql.append("               PT_BU_RETURN_APPLY_DTL   T2,\n" );
    	sql.append("               PT_BU_DEALER_INVOICE_DTL T3\n" );
    	sql.append("         WHERE 1 = 1\n" );
    	sql.append("           AND T1.RETURN_ID = T2.RETURN_ID\n" );
    	sql.append("           AND T1.RETURN_ID = T3.ORDER_ID\n" );
    	sql.append("           AND T3.SUM_ID = "+SUM_ID+"\n" );
    	sql.append("         GROUP BY T1.RETURN_NO,\n" );
    	sql.append("                  T1.RETURN_ID,\n" );
    	sql.append("                  '采购退货',\n" );
    	sql.append("                  '2',\n" );
    	sql.append("                  T1.INVOICE_STATUS) T\n" );
    	sql.append(" ORDER BY T.ORDER_TYPE, T.ORDER_NO DESC");
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("INVOICE_STATUS", "KPZT");
    	return bs;
    }
	public boolean updateSum(PtBuDealerInvoiceSummaryVO vo)
    		throws Exception
    {
    	return factory.update(vo);
    }
	public boolean updateSaleStatus(String sumId,String No) throws Exception
    {
		StringBuffer sql= new StringBuffer();
		sql.append("UPDATE PT_BU_SALE_ORDER T\n" );
		sql.append("   SET T.INVOICE_STATUS = 202302, T.INVOICE_NO = '"+No+"'\n" );
		sql.append(" WHERE T.ORDER_ID IN\n" );
		sql.append("       (SELECT ORDER_ID FROM PT_BU_DEALER_INVOICE_DTL WHERE SUM_ID = "+sumId+")");
    	return factory.update(sql.toString(), null);
    }
	public boolean updateRetStatus(String sumId,String No) throws Exception
    {
		StringBuffer sql= new StringBuffer();
		sql.append("UPDATE PT_BU_RETURN_APPLY T\n" );
		sql.append("   SET T.INVOICE_STATUS = 202302, T.INVOICE_NO = '"+No+"'\n" );
		sql.append(" WHERE T.RETURN_ID IN\n" );
		sql.append("       (SELECT ORDER_ID FROM PT_BU_DEALER_INVOICE_DTL WHERE SUM_ID = "+sumId+")");
    	return factory.update(sql.toString(), null);
    }
	public BaseResultSet searchSummary(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres +="AND NVL(T.INVOICE_AMOUNT, 0) > 0\n" +
    			"AND T.INVOICE_STATUS = "+DicConstant.KPZT_02+" AND T.EXPRESS_STATUS = "+DicConstant.FPYJZT_01+"\n";
    	page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.*\n" );
    	sql.append("  FROM PT_BU_DEALER_INVOICE_SUMMARY T");
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDateFormat("INVOICE_DATE", "yyyy-MM-dd");
    	bs.setFieldDic("INVOICE_STATUS", "KPZT");
    	return bs;
    }
	public BaseResultSet searchSign(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres +="AND NVL(T.INVOICE_AMOUNT, 0) > 0\n" +
    			"AND T.INVOICE_STATUS = "+DicConstant.KPZT_02+" AND T.EXPRESS_STATUS = "+DicConstant.FPYJZT_02+" AND T.ORG_ID = "+user.getOrgId()+"\n";
    	page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.*\n" );
    	sql.append("  FROM PT_BU_DEALER_INVOICE_SUMMARY T");
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDateFormat("INVOICE_DATE", "yyyy-MM-dd");
    	bs.setFieldDic("INVOICE_STATUS", "KPZT");
    	return bs;
    }
}
