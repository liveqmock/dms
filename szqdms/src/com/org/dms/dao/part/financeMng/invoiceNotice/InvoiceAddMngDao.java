package com.org.dms.dao.part.financeMng.invoiceNotice;

import java.util.HashMap;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuPchOrderSplitVO;
import com.org.dms.vo.part.PtBuPchReturnVO;
import com.org.dms.vo.part.PtBuSupInvoiceSummaryVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class InvoiceAddMngDao extends BaseDAO{
	public  static final InvoiceAddMngDao getInstance(ActionContext atx)
    {
		InvoiceAddMngDao dao = new InvoiceAddMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	/**
	 * 
	 * @date()2014年8月9日下午1:37:53
	 * @author Administrator
	 * @to_do:开票通知查询
	 * @param page
	 * @param user
	 * @param conditions
	 * @param END_DAY
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet invioceSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres +="AND T.SUPPLIER_ID = T1.SUPPLIER_ID\n"+
//    			"AND T.INVOICE_STATUS IN ("+DicConstant.GYSKPZT_01+","+DicConstant.GYSKPZT_04+","+DicConstant.GYSKPZT_06+")\n"+
//    			"AND T.SETTLE_STATUS IN ("+DicConstant.GYSJSZT_01+","+DicConstant.GYSJSZT_03+")"+

				"AND (EXISTS\n" +
				"                        (SELECT 1\n" + 
				"                           FROM PT_BU_SUP_INVOICE_SUMMARY_DTL T1,\n" + 
				"                                PT_BU_PCH_ORDER_SPLIT         T2\n" + 
				"                          WHERE T.SUM_ID = T1.SUM_ID\n" + 
				"                            AND T2.SPLIT_ID = T1.ORDER_ID\n" + 
				"                            AND T2.INVOICE_STATUS IN( "+DicConstant.GYSKPZT_02+", "+DicConstant.GYSKPZT_05+")) OR EXISTS\n" + 
				"                        (SELECT 1\n" + 
				"                           FROM PT_BU_SUP_INVOICE_SUMMARY_DTL T1, PT_BU_PCH_RETURN T2\n" + 
				"                          WHERE T.SUM_ID = T1.SUM_ID\n" + 
				"                            AND T2.RETURN_ID = T1.ORDER_ID\n" + 
				"                            AND T2.INVOICE_STATUS IN( "+DicConstant.GYSKPZT_02+", "+DicConstant.GYSKPZT_05+")))"+
    			"AND T1.ORG_ID = "+user.getOrgId()+" AND T1.PART_IDENTIFY = "+DicConstant.YXBS_01+" \n";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.SUM_ID,\n" );
    	sql.append("       T.SUPPLIER_ID,\n" );
    	sql.append("       T.SUPPLIER_CODE,\n" );
    	sql.append("       T.SUPPLIER_NAME,T.INVOICE_MONTH,\n" );
    	sql.append("       T.IN_COUNT,\n" );
    	sql.append("       T.IN_AMOUNT,\n" );
    	sql.append("       T.RETURN_COUNT,\n" );
    	sql.append("       nvl(T.RETURN_AMOUNT,0)RETURN_AMOUNT,\n" );
    	sql.append("       T.INVOICE_STATUS,\n" );
    	sql.append("       T.ADUIT_REMARKS,\n" );
    	sql.append("       T.INVOICE_AMOUNT,T.REMARKS,T.INVOICE_NO,NVL(T.HAS_INVOICE_AMOUNT,0) HAS_INVOICE_AMOUNT,NVL(T.UN_INVOICE_AMOUNT,0)UN_INVOICE_AMOUNT\n" );
    	sql.append("  FROM PT_BU_SUP_INVOICE_SUMMARY T, PT_BA_SUPPLIER T1");
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
	
	public BaseResultSet invioceOrderSearch(PageManager page, User user, String SUM_ID,String conditions) throws Exception
    {
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.* FROM (SELECT SUM(NVL(T2.STORAGE_COUNT, 0) * NVL(T2.PCH_PRICE, 0)) AMOUNT,\n" );
    	sql.append("       SUM(NVL(T2.STORAGE_COUNT, 0)) COUNT,\n" );
    	sql.append("       T1.SPLIT_NO ORDER_NO,\n" );
    	sql.append("       T1.SPLIT_ID ORDER_ID,\n" );
    	sql.append("       T4.DIC_VALUE ORDER_TYPE,\n" );
    	sql.append("       '1' TYPE,T3.I_STATUS,T1.INVOICE_STATUS\n" );
    	sql.append("  FROM PT_BU_PCH_ORDER_SPLIT         T1,\n" );
    	sql.append("       PT_BU_PCH_ORDER_SPLIT_DTL     T2,\n" );
    	sql.append("       PT_BU_SUP_INVOICE_SUMMARY_DTL T3,\n" );
    	sql.append("       DIC_TREE                      T4\n" );
    	sql.append(" WHERE T1.SPLIT_ID = T2.SPLIT_ID\n" );
    	sql.append("   AND T1.SPLIT_ID = T3.ORDER_ID\n" );
    	sql.append("   AND T1.ORDER_TYPE = T4.ID\n" );
    	sql.append("   AND T1.SETTLE_STATUS = 205301\n" );
    	sql.append("   AND T3.SUM_ID ="+SUM_ID+"\n" );
    	sql.append(" GROUP BY T1.SPLIT_NO, T1.SPLIT_ID, T4.DIC_VALUE,'1',T3.I_STATUS,T1.INVOICE_STATUS\n" );
    	sql.append("UNION\n" );
    	sql.append("SELECT SUM(NVL(T2.COUNT, 0) * NVL(T2.PCH_PRICE, 0)) AMOUNT,\n" );
    	sql.append("       SUM(NVL(T2.COUNT, 0)) COUNT,\n" );
    	sql.append("       T1.RETURN_NO ORDER_NO,\n" );
    	sql.append("       T1.RETURN_ID ORDER_ID,\n" );
    	sql.append("       '采购退货' ORDER_TYPE,\n" );
    	sql.append("       '2' TYPE,T3.I_STATUS,T1.INVOICE_STATUS\n" );
    	sql.append("  FROM PT_BU_PCH_RETURN              T1,\n" );
    	sql.append("       PT_BU_PCH_RETURN_DTL          T2,\n" );
    	sql.append("       PT_BU_SUP_INVOICE_SUMMARY_DTL T3\n" );
    	sql.append(" WHERE 1 = 1\n" );
    	sql.append("   AND T1.RETURN_ID = T2.RETURN_ID\n" );
    	sql.append("   AND T1.RETURN_ID = T3.ORDER_ID\n" );
    	sql.append("   AND T1.SETTLE_STATUS = 205301\n" );
    	sql.append("   AND T3.SUM_ID ="+SUM_ID+"\n" );
    	sql.append(" GROUP BY T1.RETURN_NO, T1.RETURN_ID, '采购退货','2',T3.I_STATUS,T1.INVOICE_STATUS )T WHERE "+conditions+" ORDER BY T.ORDER_TYPE ,T.ORDER_NO DESC\n");
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("INVOICE_STATUS", "GYSKPZT");
    	return bs;
    }
	/**
	 * 
	 * @date()2014年8月11日上午11:10:26
	 * @author Administrator
	 * @to_do:向采购拆分单中插入发票号
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
	  * @date()2014年8月11日上午11:10:46
	  * @author Administrator
	  * @to_do:向采购退货单中插入发票号
	  * @param vo
	  * @return
	  * @throws Exception
	  */
	 public boolean updateRet(PtBuPchReturnVO vo)
	    		throws Exception
	    {
	    	return factory.update(vo);
	    }
	 
	 public boolean updateSum(PtBuSupInvoiceSummaryVO vo)
	    		throws Exception
	    {
	    	return factory.update(vo);
	    }
	 
	 public QuerySet getOrder(String SUPPLIER_ID) throws Exception
	    {
	    	QuerySet qs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT T1.ORDER_ID, T1.TYPE\n" );
	    	sql.append("  FROM PT_BU_SUP_INVOICE_SUMMARY T, PT_BU_SUP_INVOICE_SUMMARY_DTL T1\n" );
	    	sql.append(" WHERE T.SUM_ID = T1.SUM_ID\n" );
	    	sql.append("   AND T.INVOICE_STATUS = "+DicConstant.GYSKPZT_02+"");
	    	qs = factory.select(null, sql.toString());
	    	return qs;
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
	 public QuerySet getAmount(String orderId) throws Exception
	    {
	    	QuerySet qs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT SUM(T.STORAGE_COUNT*T.PCH_PRICE) PCH_AMOUNT FROM PT_BU_PCH_ORDER_SPLIT_DTL T WHERE T.SPLIT_ID = "+orderId+"");
	    	qs = factory.select(null, sql.toString());
	    	return qs;
	    }
	 public QuerySet getNo(String SUM_ID) throws Exception
	    {
	    	QuerySet qs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT T.INVOICE_NO\n" );
	    	sql.append("FROM PT_BU_SUP_INVOICE_SUMMARY T\n" );
	    	sql.append("WHERE T.SUM_ID = "+SUM_ID+"");
	    	qs = factory.select(null, sql.toString());
	    	return qs;
	    }
	 public boolean updateSumSup(HashMap<String, String> hm,User user,String SUM_ID) throws Exception
	    {
		 StringBuffer sql= new StringBuffer();
		 sql.append("UPDATE PT_BU_SUP_INVOICE_SUMMARY T\n" );
		 sql.append("SET T.HAS_INVOICE_AMOUNT = '"+hm.get("HASINVOICEAMOUNTS")+"',\n" );
		 sql.append("T.UN_INVOICE_AMOUNT = '"+hm.get("UNINVOICEAMOUNTS")+"',\n" );
		 sql.append("T.REMARKS = '"+hm.get("REMARK")+"',\n" );
		 sql.append("T.UPDATE_USER = '"+user.getAccount()+"',\n" );
		 sql.append("T.UPDATE_TIME = SYSDATE\n" );
		 sql.append("WHERE SUM_ID = '"+SUM_ID+"'");
	    	return factory.update(sql.toString(), null);
	    }

	 public boolean updateSumDtl(String SUM_ID) throws Exception
	    {
		 StringBuffer sql= new StringBuffer();
		 sql.append("UPDATE PT_BU_SUP_INVOICE_SUMMARY_DTL T SET T.I_STATUS = "+DicConstant.GYSKPZT_02+" WHERE T.I_STATUS = "+DicConstant.GYSKPZT_05+" AND SUM_ID = "+SUM_ID+" \n" );
	    	return factory.update(sql.toString(), null);
	    }
	 public boolean addSumDtl(String SUM_ID,String NO,String ORDERIDS) throws Exception
	    {
		 StringBuffer sql= new StringBuffer();
		 sql.append("UPDATE PT_BU_SUP_INVOICE_SUMMARY_DTL T SET T.I_STATUS = "+DicConstant.GYSKPZT_05+",T.I_NO = '"+NO+"' WHERE T.ORDER_ID IN ("+ORDERIDS+") AND T.SUM_ID = "+SUM_ID+"\n" );
	    	return factory.update(sql.toString(), null);
	    }
	 public boolean reportSum(String SUM_ID,String NO,String ORDERIDS) throws Exception
	    {
		 StringBuffer sql= new StringBuffer();
		 sql.append("UPDATE PT_BU_SUP_INVOICE_SUMMARY_DTL T SET T.I_STATUS = "+DicConstant.GYSKPZT_03+",T.I_NO = '"+NO+"' WHERE T.ORDER_ID IN ("+ORDERIDS+") AND T.SUM_ID = "+SUM_ID+"\n" );
	    	return factory.update(sql.toString(), null);
	    }
	 public BaseResultSet getInvoiceNo(PageManager page, String SUM_ID, String conditions) throws Exception
	    {
	        BaseResultSet bs = null;
	        StringBuffer sql= new StringBuffer();
	        sql.append("SELECT DISTINCT T.I_NO FROM PT_BU_SUP_INVOICE_SUMMARY_DTL T WHERE T.I_STATUS = "+DicConstant.GYSKPZT_05+" AND T.SUM_ID = "+SUM_ID+"" );
	        bs = factory.select(sql.toString(), page);
	        return bs;
	    }
}
