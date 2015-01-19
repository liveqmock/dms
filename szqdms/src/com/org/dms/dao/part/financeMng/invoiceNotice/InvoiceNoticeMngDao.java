package com.org.dms.dao.part.financeMng.invoiceNotice;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuPchOrderSplitVO;
import com.org.dms.vo.part.PtBuPchReturnVO;
import com.org.dms.vo.part.PtBuSupInvoiceSummaryDtlVO;
import com.org.dms.vo.part.PtBuSupInvoiceSummaryVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class InvoiceNoticeMngDao extends BaseDAO{
	public  static final InvoiceNoticeMngDao getInstance(ActionContext atx)
    {
		InvoiceNoticeMngDao dao = new InvoiceNoticeMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	/**
	 * 
	 * @date()2014年8月8日上午11:11:19
	 * @author Administrator
	 * @to_do:通知供应商开票查询
	 * @param page
	 * @param user
	 * @param conditions
	 * @param END_DAY
	 * @return
	 * @throws Exception
	 */
//	public BaseResultSet invioceSearch(PageManager page, User user, String conditions,String END_DAY_01,String END_DAY_02) throws Exception
//    {
//    	String wheres = conditions;
//    	wheres +="AND A.SUPPLIER_ID = B.SUPPLIER_ID(+)\n";
//    	wheres +="AND A.SUPPLIER_ID = C.SUPPLIER_ID(+)\n";
//        page.setFilter(wheres);
//    	BaseResultSet bs = null;
//    	StringBuffer sql= new StringBuffer();
//    	sql.append("SELECT A.SUPPLIER_ID,\n" );
//    	sql.append("       A.SUPPLIER_NAME,\n" );
//    	sql.append("       A.SUPPLIER_CODE,\n" );
//    	sql.append("       TO_CHAR(SYSDATE, 'YYYY-MM') INVOICE_MONTH,\n" );
//    	sql.append("       A.STORAGE_AMOUNT,\n" );
//    	sql.append("       A.STORGAE_COUNT,\n" );
//    	sql.append("       NVL(B.RET_AMOUNT, 0) RET_AMOUNT,\n" );
//    	sql.append("       NVL(B.RET_COUNT, 0) RET_COUNT,\n" );
//    	sql.append("       NVL((A.STORAGE_AMOUNT-RET_AMOUNT+C.UNSETTLE_AMOUNT),0) INVOICE_AMOUNT,\n" );
//    	sql.append("       NVL(A.STORGAE_COUNT-RET_COUNT,0) INVOICE_COUNT\n" );
//    	sql.append("  FROM (SELECT SUM(T.STORAGE_COUNT * T.PCH_PRICE) STORAGE_AMOUNT,\n" );
//    	sql.append("               SUM(T.STORAGE_COUNT) STORGAE_COUNT,\n" );
//    	sql.append("               T1.SUPPLIER_ID,\n" );
//    	sql.append("               T1.SUPPLIER_NAME,\n" );
//    	sql.append("               T1.SUPPLIER_CODE\n" );
//    	sql.append("          FROM PT_BU_PCH_ORDER_SPLIT_DTL T, PT_BU_PCH_ORDER_SPLIT T1\n" );
//    	sql.append("         WHERE T.SPLIT_ID = T1.SPLIT_ID\n" );
//    	sql.append("           AND T1.INVOICE_NO IS NULL \n" );
////    	sql.append("           AND T1.INVOICE_STATUS IS NULL \n" );
//    	sql.append("           AND NOT EXISTS (SELECT 1 FROM PT_BU_SUP_INVOICE_SUMMARY_DTL TA WHERE TA.ORDER_ID = T1.SPLIT_ID) \n" );
//    	sql.append("           AND T1.STORAGE_COUNT > 0 \n" );
//    	sql.append("           AND T1.CLOSE_DATE <=\n" );
//    	sql.append("               (SELECT TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, 0), 'YYYY-MM') || '-"+END_DAY_02+"',\n" );
//    	sql.append("                               'YYYY-MM-DD')\n" );
//    	sql.append("                  FROM DUAL)\n" );
//    	sql.append("           AND T1.CLOSE_DATE > (SELECT TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -1),\n" );
//    	sql.append("                                                       'YYYY-MM') || '-"+END_DAY_01+"',\n" );
//    	sql.append("                                               'YYYY-MM-DD')\n" );
//    	sql.append("                                  FROM DUAL)\n" );
//    	sql.append("         GROUP BY T1.SUPPLIER_ID, T1.SUPPLIER_NAME, T1.SUPPLIER_CODE) A,\n" );
//    	sql.append("       (SELECT SUM(T2.AMOUNT) RET_AMOUNT,\n" );
//    	sql.append("               SUM(T2.COUNT) RET_COUNT,\n" );
//    	sql.append("               T3.SUPPLIER_ID\n" );
//    	sql.append("          FROM PT_BU_PCH_RETURN_DTL T2, PT_BU_PCH_RETURN T3\n" );
//    	sql.append("         WHERE T2.RETURN_ID = T3.RETURN_ID\n" );
//    	sql.append("           AND T3.INVOICE_STATUS IS NULL \n" );
////    	sql.append("           AND T3.INVOICE_NO IS NULL \n" );
//    	sql.append("           AND NOT EXISTS (SELECT 1 FROM PT_BU_SUP_INVOICE_SUMMARY_DTL TB WHERE TB.ORDER_ID = T2.RETURN_ID) \n" );
//    	sql.append("           AND T3.COUNT >0 \n" );
//    	sql.append("           AND T3.CLOSE_DATE <=\n" );
//    	sql.append("               (SELECT TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, 0), 'YYYY-MM') || '-"+END_DAY_02+"',\n" );
//    	sql.append("                               'YYYY-MM-DD')\n" );
//    	sql.append("                  FROM DUAL)\n" );
//    	sql.append("           AND T3.CLOSE_DATE > (SELECT TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -1),\n" );
//    	sql.append("                                                       'YYYY-MM') || '-"+END_DAY_01+"',\n" );
//    	sql.append("                                               'YYYY-MM-DD')\n" );
//    	sql.append("                                  FROM DUAL)\n" );
//    	sql.append("         GROUP BY T3.SUPPLIER_ID) B,");
//    	sql.append("	(SELECT NVL(SUM(X.UNSETTLE_AMOUNT),0)UNSETTLE_AMOUNT,X.SUPPLIER_ID FROM PT_BU_SUP_INVOICE_SUMMARY X GROUP BY X.SUPPLIER_ID) C");
//    	bs = factory.select(sql.toString(), page);
//    	return bs;
//    }
	
	public BaseResultSet invioceSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres +=" AND INVOICE_STATUS = 205201 AND NVL(T.INVOICE_AMOUNT,0) >0 ORDER BY T.INVOICE_MONTH DESC,T.SUPPLIER_CODE DESC\n";
//        wheres +="AND NVL(T.INVOICE_AMOUNT, 0) > 0\n" +
//				"           AND (EXISTS\n" + 
//				"                (SELECT 1\n" + 
//				"                   FROM PT_BU_SUP_INVOICE_SUMMARY_DTL T1,\n" + 
//				"                        PT_BU_PCH_ORDER_SPLIT         T2\n" + 
//				"                  WHERE T.SUM_ID = T1.SUM_ID\n" + 
//				"                    AND T2.SPLIT_ID = T1.ORDER_ID\n" + 
//				"                    AND NVL(T2.INVOICE_STATUS, 0) < "+DicConstant.GYSKPZT_02+") OR EXISTS\n" + 
//				"                (SELECT 1\n" + 
//				"                   FROM PT_BU_SUP_INVOICE_SUMMARY_DTL T1, PT_BU_PCH_RETURN T2\n" + 
//				"                  WHERE T.SUM_ID = T1.SUM_ID\n" + 
//				"                    AND T2.RETURN_ID = T1.ORDER_ID\n" + 
//				"                    AND NVL(T2.INVOICE_STATUS, 0) < "+DicConstant.GYSKPZT_02+"))\n" + 
//				"                    ORDER BY T.INVOICE_MONTH DESC, T.SUPPLIER_CODE DESC";
    	page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.SUM_ID,\n" );
    	sql.append("       T.SUPPLIER_CODE,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("       T.INVOICE_MONTH,\n" );
    	sql.append("       T.IN_COUNT,\n" );
    	sql.append("       T.IN_AMOUNT,\n" );
    	sql.append("       T.RETURN_COUNT,\n" );
    	sql.append("       T.RETURN_AMOUNT,\n" );
    	sql.append("       T.INVOICE_AMOUNT\n" );
    	sql.append("  FROM PT_BU_SUP_INVOICE_SUMMARY T");
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
	
	/**
	 * 
	 * @date()2014年8月8日上午11:11:38
	 * @author Administrator
	 * @to_do:开票明细查询
	 * @param page
	 * @param user
	 * @param SUPPLIER_ID
	 * @param END_DAY
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet invioceOrderSearch(PageManager page, User user, String SUM_ID) throws Exception
    {
    	BaseResultSet bs = null;
//    	StringBuffer sql= new StringBuffer();
//    	sql.append("SELECT NVL(SUM(T.STORAGE_COUNT * T.PCH_PRICE), 0) AMOUNT,\n" );
//    	sql.append("       NVL(SUM(T.STORAGE_COUNT), 0) COUNT,\n" );
//    	sql.append("       T1.SPLIT_NO ORDER_NO,\n" );
//    	sql.append("       T2.DIC_VALUE ORDER_TYPE,\n" );
//    	sql.append("       T1.SUPPLIER_CODE,\n" );
//    	sql.append("       T1.SUPPLIER_NAME,\n" );
//    	sql.append("       T1.ORDER_NO OLD_ORDER_NO\n" );
//    	sql.append("  FROM PT_BU_PCH_ORDER_SPLIT_DTL T, PT_BU_PCH_ORDER_SPLIT T1, DIC_TREE T2\n" );
//    	sql.append(" WHERE T.SPLIT_ID = T1.SPLIT_ID\n" );
//    	sql.append("   AND T1.ORDER_TYPE = T2.ID\n" );
//    	sql.append("   AND T1.STORAGE_COUNT >0\n" );
//    	sql.append("   AND T1.SUPPLIER_ID = "+SUPPLIER_ID+"\n" );
//    	sql.append("   AND T1.CLOSE_DATE <=\n" );
//    	sql.append("       (SELECT TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, 0), 'YYYY-MM') || '-"+END_DAY_02+"', 'YYYY-MM-DD')\n" );
//    	sql.append("          FROM DUAL)\n" );
//    	sql.append("   AND T1.CLOSE_DATE >\n" );
//    	sql.append("       (SELECT TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -1), 'YYYY-MM') || '-"+END_DAY_01+"',\n" );
//    	sql.append("                       'YYYY-MM-DD')\n" );
//    	sql.append("          FROM DUAL)\n" );
//    	sql.append(" GROUP BY T1.SPLIT_NO, T2.DIC_VALUE, T1.SUPPLIER_CODE, T1.SUPPLIER_NAME,T1.ORDER_NO\n" );
//    	sql.append(" UNION\n" );
//    	sql.append(" SELECT NVL(SUM(T3.AMOUNT),0) AMOUNT,\n" );
//    	sql.append("       NVL(SUM(T3.COUNT),0) COUNT,\n" );
//    	sql.append("       T4.RETURN_NO  ORDER_NO,\n" );
//    	sql.append("       T5.DIC_VALUE ORDER_TYPE,\n" );
//    	sql.append("       T4.SUPPLIER_CODE,\n" );
//    	sql.append("       T4.SUPPLIER_NAME,\n" );
//    	sql.append("       T4.PURCHASE_NO OLD_ORDER_NO\n" );
//    	sql.append("  FROM PT_BU_PCH_RETURN_DTL T3, PT_BU_PCH_RETURN T4, DIC_TREE T5\n" );
//    	sql.append(" WHERE 1=1\n" );
//    	sql.append(" AND T3.RETURN_ID = T4.RETURN_ID\n" );
//    	sql.append(" AND T4.RETURN_TYPE = T5.ID\n" );
//    	sql.append(" AND T4.COUNT >0\n" );
//    	sql.append(" AND T4.SUPPLIER_ID = "+SUPPLIER_ID+"\n" );
//    	sql.append("   AND T4.CLOSE_DATE <=\n" );
//    	sql.append("       (SELECT TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, 0), 'YYYY-MM') || '-"+END_DAY_02+"', 'YYYY-MM-DD')\n" );
//    	sql.append("          FROM DUAL)\n" );
//    	sql.append("   AND T4.CLOSE_DATE >\n" );
//    	sql.append("       (SELECT TO_DATE(TO_CHAR(ADD_MONTHS(ADD_MONTHS(SYSDATE, -1), -1), 'YYYY-MM') || '-"+END_DAY_01+"',\n" );
//    	sql.append("                       'YYYY-MM-DD')\n" );
//    	sql.append("          FROM DUAL)\n" );
//    	sql.append(" GROUP BY T4.RETURN_NO,T5.DIC_VALUE,\n" );
//    	sql.append("       T4.SUPPLIER_CODE,\n" );
//    	sql.append("       T4.SUPPLIER_NAME,\n" );
//    	sql.append("       T4.PURCHASE_NO");
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT B.SPLIT_ID ORDER_ID,\n" );
    	sql.append("       B.SPLIT_NO ORDER_NO,\n" );
    	sql.append("       B.SUPPLIER_NAME,\n" );
    	sql.append("       B.SUPPLIER_CODE,\n" );
    	sql.append("       '采购' AS TYPE,\n" );
    	sql.append("       B.STORAGE_COUNT COUNT,\n" );
    	sql.append("       B.CLOSE_DATE,\n" );
    	sql.append("       B.INVOICE_STATUS,\n" );
    	sql.append("       C.AMOUNT,B.APPLY_USER\n" );
    	sql.append("  FROM PT_BU_SUP_INVOICE_SUMMARY_DTL A,\n" );
    	sql.append("       PT_BU_PCH_ORDER_SPLIT B,\n" );
    	sql.append("       (SELECT SUM(T.STORAGE_COUNT * T.PCH_PRICE) AMOUNT, T.SPLIT_ID\n" );
    	sql.append("          FROM PT_BU_PCH_ORDER_SPLIT_DTL T\n" );
    	sql.append("         WHERE 1 = 1\n" );
    	sql.append("         GROUP BY T.SPLIT_ID) C\n" );
    	sql.append(" WHERE A.ORDER_ID = B.SPLIT_ID\n" );
    	sql.append("   AND A.ORDER_ID = C.SPLIT_ID(+)\n" );
    	sql.append("   AND A.SUM_ID = "+SUM_ID+"\n" );
    	sql.append("UNION ALL\n" );
    	sql.append("SELECT E.RETURN_ID ORDER_ID,\n" );
    	sql.append("       E.RETURN_NO ORDER_NO,\n" );
    	sql.append("       E.SUPPLIER_NAME,\n" );
    	sql.append("       E.SUPPLIER_CODE,\n" );
    	sql.append("       '退货' AS TYPE,\n" );
    	sql.append("       E.COUNT,\n" );
    	sql.append("       E.CLOSE_DATE,\n" );
    	sql.append("       E.INVOICE_STATUS,\n" );
    	sql.append("       F.AMOUNT,E.CREATE_USER APPLY_USER\n" );
    	sql.append("  FROM PT_BU_SUP_INVOICE_SUMMARY_DTL D,\n" );
    	sql.append("       PT_BU_PCH_RETURN E,\n" );
    	sql.append("       (SELECT SUM(T1.RETURN_AMOUNT * T1.PCH_PRICE) AMOUNT, T1.RETURN_ID\n" );
    	sql.append("          FROM PT_BU_PCH_RETURN_DTL T1\n" );
    	sql.append("         WHERE 1 = 1\n" );
    	sql.append("         GROUP BY T1.RETURN_ID) F\n" );
    	sql.append(" WHERE D.ORDER_ID = E.RETURN_ID\n" );
    	sql.append("   AND D.ORDER_ID = F.RETURN_ID(+)\n" );
    	sql.append("   AND D.SUM_ID = "+SUM_ID+"");
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldUserID("APPLY_USER");
    	bs.setFieldDateFormat("CLOSE_DATE", "yyyy-MM-dd HH:mm:ss");
    	bs.setFieldDic("INVOICE_STATUS", "GYSKPZT");
    	return bs;
    }
	/**
	 * 
	 * @date()2014年8月8日上午11:31:52
	 * @author Administrator
	 * @to_do:获取采购拆分单ID
	 * @param SUPPLIER_ID
	 * @param END_DAY
	 * @return
	 * @throws Exception
	 */
	public QuerySet getPchOrderId(String SUM_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.ORDER_ID,T1.INVOICE_STATUS\n" );
    	sql.append("  FROM PT_BU_SUP_INVOICE_SUMMARY_DTL T,PT_BU_PCH_ORDER_SPLIT T1\n" );
    	sql.append(" WHERE  T.TYPE = '1' AND T.SUM_ID = "+SUM_ID+" AND T.ORDER_ID = T1.SPLIT_ID");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	/**
	 * 
	 * @date()2014年8月8日上午11:32:06
	 * @author Administrator
	 * @to_do:获取采购退货单ID
	 * @param SUPPLIER_ID
	 * @param END_DAY
	 * @return
	 * @throws Exception
	 */
	public QuerySet getRetOrderId(String SUM_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.ORDER_ID,T1.INVOICE_STATUS\n" );
    	sql.append("  FROM PT_BU_SUP_INVOICE_SUMMARY_DTL T,PT_BU_PCH_RETURN T1\n" );
    	sql.append(" WHERE  T.TYPE = '2' AND T.SUM_ID = "+SUM_ID+" AND T.ORDER_ID = T1.RETURN_ID");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	/**
	 * 
	 * @date()2014年8月8日下午1:53:36
	 * @author Administrator
	 * @to_do:更新供应商对应采购单开票状态
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean updatePch(PtBuPchOrderSplitVO vo)
    		throws Exception
    {
    	return factory.update(vo);
    }
	/**
	 * 
	 * @date()2014年8月13日上午10:23:48
	 * @author Administrator
	 * @to_do:更新采购退货单状态
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean updateRet(PtBuPchReturnVO vo)
    		throws Exception
    {
    	return factory.update(vo);
    }
	/**
	 * 
	 * @date()2014年8月13日上午10:24:15
	 * @author Administrator
	 * @to_do:供应商开票信息汇总下载
	 * @param user
	 * @param SUPPLIER_ID
	 * @param END_DAY
	 * @return
	 * @throws Exception
	 */
	public QuerySet download( User user, String SUPPLIER_ID,String END_DAY_01,String END_DAY_02) throws Exception{
		   QuerySet qs = null;
		   StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT A.SUPPLIER_ID,\n" );
	    	sql.append("       A.SUPPLIER_NAME,\n" );
	    	sql.append("       A.SUPPLIER_CODE,\n" );
	    	sql.append("       A.STORAGE_AMOUNT,\n" );
	    	sql.append("       TO_CHAR(SYSDATE, 'YYYY-MM') INVOICE_MONTH,\n" );
	    	sql.append("       A.STORGAE_COUNT,\n" );
	    	sql.append("       NVL(B.RET_AMOUNT, 0) RET_AMOUNT,\n" );
	    	sql.append("       NVL(B.RET_COUNT, 0) RET_COUNT,\n" );
	    	sql.append("       NVL(A.STORAGE_AMOUNT-RET_AMOUNT,0) INVOICE_AMOUNT,\n" );
	    	sql.append("       NVL(A.STORGAE_COUNT-RET_COUNT,0) INVOICE_COUNT\n" );
	    	sql.append("  FROM (SELECT SUM(T.STORAGE_COUNT * T.PCH_PRICE) STORAGE_AMOUNT,\n" );
	    	sql.append("               SUM(T.STORAGE_COUNT) STORGAE_COUNT,\n" );
	    	sql.append("               T1.SUPPLIER_ID,\n" );
	    	sql.append("               T1.SUPPLIER_NAME,\n" );
	    	sql.append("               T1.SUPPLIER_CODE\n" );
	    	sql.append("          FROM PT_BU_PCH_ORDER_SPLIT_DTL T, PT_BU_PCH_ORDER_SPLIT T1\n" );
	    	sql.append("         WHERE T.SPLIT_ID = T1.SPLIT_ID\n" );
	    	sql.append("           AND T1.STORAGE_COUNT>0 \n" );
	    	sql.append("           AND T1.INVOICE_NO IS NULL \n" );
	    	sql.append("           AND T1.CLOSE_DATE <=\n" );
	    	sql.append("               (SELECT TO_DATE(TO_CHAR(SYSDATE, 'YYYY-MM') || '-"+END_DAY_02+"',\n" );
	    	sql.append("                               'YYYY-MM-DD')\n" );
	    	sql.append("                  FROM DUAL)\n" );
	    	sql.append("           AND T1.CLOSE_DATE > (SELECT TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -1),\n" );
	    	sql.append("                                                       'YYYY-MM') || '-"+END_DAY_01+"',\n" );
	    	sql.append("                                               'YYYY-MM-DD')\n" );
	    	sql.append("                                  FROM DUAL)\n" );
	    	sql.append("         GROUP BY T1.SUPPLIER_ID, T1.SUPPLIER_NAME, T1.SUPPLIER_CODE) A,\n" );
	    	sql.append("       (SELECT SUM(T2.AMOUNT) RET_AMOUNT,\n" );
	    	sql.append("               SUM(T2.COUNT) RET_COUNT,\n" );
	    	sql.append("               T3.SUPPLIER_ID\n" );
	    	sql.append("          FROM PT_BU_PCH_RETURN_DTL T2, PT_BU_PCH_RETURN T3\n" );
	    	sql.append("         WHERE T2.RETURN_ID = T3.RETURN_ID\n" );
	    	sql.append("           AND T3.INVOICE_NO IS NULL \n" );
	    	sql.append("           AND T3.COUNT > 0 \n" );
	    	sql.append("           AND T3.CLOSE_DATE <=\n" );
	    	sql.append("               (SELECT TO_DATE(TO_CHAR(SYSDATE, 'YYYY-MM') || '-"+END_DAY_02+"',\n" );
	    	sql.append("                               'YYYY-MM-DD')\n" );
	    	sql.append("                  FROM DUAL)\n" );
	    	sql.append("           AND T3.CLOSE_DATE > (SELECT TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -1),\n" );
	    	sql.append("                                                       'YYYY-MM') || '-"+END_DAY_01+"',\n" );
	    	sql.append("                                               'YYYY-MM-DD')\n" );
	    	sql.append("                                  FROM DUAL)\n" );
	    	sql.append("         GROUP BY T3.SUPPLIER_ID) B \n");
	    	sql.append("   WHERE   A.SUPPLIER_ID = B.SUPPLIER_ID(+) ");
	    	if(SUPPLIER_ID!=null&&!"".equals(SUPPLIER_ID)){
	    		sql.append("           AND A.SUPPLIER_ID = "+SUPPLIER_ID+"");
	    	}
		   qs = factory.select(null, sql.toString());
		   return qs;
	   }
	public QuerySet getOrderInfo( User user, String SUPPLIER_ID,String END_DAY) throws Exception{
		   QuerySet qs = null;
		   StringBuffer sql= new StringBuffer();
		   sql.append("SELECT NVL(SUM(T.STORAGE_COUNT * T.PCH_PRICE), 0) AMOUNT,\n" );
	    	sql.append("       NVL(SUM(T.STORAGE_COUNT), 0) COUNT,\n" );
	    	sql.append("       T1.SPLIT_NO ORDER_NO,\n" );
	    	sql.append("       T2.DIC_VALUE ORDER_TYPE,\n" );
	    	sql.append("       T1.SUPPLIER_CODE,\n" );
	    	sql.append("       T1.SUPPLIER_NAME,\n" );
	    	sql.append("       T1.ORDER_NO OLD_ORDER_NO\n" );
	    	sql.append("  FROM PT_BU_PCH_ORDER_SPLIT_DTL T, PT_BU_PCH_ORDER_SPLIT T1, DIC_TREE T2\n" );
	    	sql.append(" WHERE T.SPLIT_ID = T1.SPLIT_ID\n" );
	    	sql.append("   AND T1.ORDER_TYPE = T2.ID\n" );
	    	sql.append("   AND T1.SUPPLIER_ID = "+SUPPLIER_ID+"\n" );
	    	sql.append("   AND T1.CLOSE_DATE <=\n" );
	    	sql.append("       (SELECT TO_DATE(TO_CHAR(SYSDATE, 'YYYY-MM') || '-"+END_DAY+"', 'YYYY-MM-DD')\n" );
	    	sql.append("          FROM DUAL)\n" );
	    	sql.append("   AND T1.CLOSE_DATE >\n" );
	    	sql.append("       (SELECT TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -1), 'YYYY-MM') || '-"+END_DAY+"',\n" );
	    	sql.append("                       'YYYY-MM-DD')\n" );
	    	sql.append("          FROM DUAL)\n" );
	    	sql.append(" GROUP BY T1.SPLIT_NO, T2.DIC_VALUE, T1.SUPPLIER_CODE, T1.SUPPLIER_NAME,T1.ORDER_NO\n" );
	    	sql.append(" UNION\n" );
	    	sql.append(" SELECT NVL(SUM(T3.AMOUNT),0) AMOUNT,\n" );
	    	sql.append("       NVL(SUM(T3.COUNT),0) COUNT,\n" );
	    	sql.append("       T4.RETURN_NO  ORDER_NO,\n" );
	    	sql.append("       T5.DIC_VALUE ORDER_TYPE,\n" );
	    	sql.append("       T4.SUPPLIER_CODE,\n" );
	    	sql.append("       T4.SUPPLIER_NAME,\n" );
	    	sql.append("       T4.PURCHASE_NO OLD_ORDER_NO\n" );
	    	sql.append("  FROM PT_BU_PCH_RETURN_DTL T3, PT_BU_PCH_RETURN T4, DIC_TREE T5\n" );
	    	sql.append(" WHERE 1=1\n" );
	    	sql.append(" AND T3.RETURN_ID = T4.RETURN_ID\n" );
	    	sql.append(" AND T4.RETURN_TYPE = T5.ID\n" );
	    	sql.append(" AND T4.SUPPLIER_ID = "+SUPPLIER_ID+"\n" );
	    	sql.append("   AND T4.CLOSE_DATE <=\n" );
	    	sql.append("       (SELECT TO_DATE(TO_CHAR(SYSDATE, 'YYYY-MM') || '-"+END_DAY+"', 'YYYY-MM-DD')\n" );
	    	sql.append("          FROM DUAL)\n" );
	    	sql.append("   AND T4.CLOSE_DATE >\n" );
	    	sql.append("       (SELECT TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -1), 'YYYY-MM') || '-"+END_DAY+"',\n" );
	    	sql.append("                       'YYYY-MM-DD')\n" );
	    	sql.append("          FROM DUAL)\n" );
	    	sql.append(" GROUP BY T4.RETURN_NO,T5.DIC_VALUE,\n" );
	    	sql.append("       T4.SUPPLIER_CODE,\n" );
	    	sql.append("       T4.SUPPLIER_NAME,\n" );
	    	sql.append("       T4.PURCHASE_NO");
		   qs = factory.select(null, sql.toString());
		   return qs;
	   }
	/**
	 * 
	 * @date()2014年8月13日上午11:14:29
	 * @author Administrator
	 * @to_do:插入供应商开票信息汇总表
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean insertSummary(PtBuSupInvoiceSummaryVO vo)
    		throws Exception
    {
    	return factory.insert(vo);
    }
	/**
	 * 
	 * @date()2014年8月13日下午2:35:15
	 * @author Administrator
	 * @to_do:插入明细汇总表
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean inserSummaryDtl(PtBuSupInvoiceSummaryDtlVO vo)
    		throws Exception
    {
    	return factory.insert(vo);
    }
	public boolean updateSup(PtBuSupInvoiceSummaryVO vo)
    		throws Exception
    {
    	return factory.update(vo);
    }
	public QuerySet getUnAmount(String SUM_ID) throws Exception{
		   QuerySet qs = null;
		   StringBuffer sql= new StringBuffer();
		   sql.append("SELECT NVL(T.IN_AMOUNT, 0) + NVL(T.RETURN_AMOUNT, 0) UN_AMOUNT\n" );
		   sql.append("  FROM PT_BU_SUP_INVOICE_SUMMARY T\n" );
		   sql.append(" WHERE T.SUM_ID = "+SUM_ID+"\n");
		   qs = factory.select(null, sql.toString());
		   return qs;
	   }
	public void updateSupDtl(String SUM_ID) throws Exception
    {
		StringBuffer sql= new StringBuffer();
		sql.append("UPDATE PT_BU_SUP_INVOICE_SUMMARY_DTL T SET T.I_STATUS = "+DicConstant.GYSKPZT_02+" WHERE T.SUM_ID = "+SUM_ID+"");
    	factory.update(sql.toString(), null);
    }
}
