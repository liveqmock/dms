package com.org.dms.dao.part.financeMng.settlement;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuPchOrderSplitVO;
import com.org.dms.vo.part.PtBuPchReturnVO;
import com.org.dms.vo.part.PtBuSupInvoiceSummaryVO;
import com.org.dms.vo.part.PtBuSupSettleLogVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class SupplierSettlementDao extends BaseDAO{
	public  static final SupplierSettlementDao getInstance(ActionContext atx)
    {
		SupplierSettlementDao dao = new SupplierSettlementDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	
	
	public BaseResultSet settleSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
//    	wheres +=" AND T.SETTLE_STATUS ="+DicConstant.GYSJSZT_01+" AND T.INVOICE_STATUS ="+DicConstant.GYSKPZT_05+" AND T.SUPPLIER_ID = T1.SUPPLIER_ID\n";
        wheres +="AND T.SUPPLIER_ID = T1.SUPPLIER_ID\n" +
        				"   AND (EXISTS\n" + 
        				"        (SELECT 1\n" + 
        				"           FROM PT_BU_SUP_INVOICE_SUMMARY_DTL T1, PT_BU_PCH_ORDER_SPLIT T2\n" + 
        				"          WHERE T.SUM_ID = T1.SUM_ID\n" + 
        				"            AND T2.SPLIT_ID = T1.ORDER_ID\n" + 
        				"            AND T2.INVOICE_STATUS = 205204 AND T2.SETTLE_STATUS = 205301) OR EXISTS\n" + 
        				"        (SELECT 1\n" + 
        				"           FROM PT_BU_SUP_INVOICE_SUMMARY_DTL T1, PT_BU_PCH_RETURN T2\n" + 
        				"          WHERE T.SUM_ID = T1.SUM_ID\n" + 
        				"            AND T2.RETURN_ID = T1.ORDER_ID\n" + 
        				"            AND T2.INVOICE_STATUS = 205204 AND T2.SETTLE_STATUS = 205301))";
    	page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.SUM_ID,\n" );
    	sql.append("       T.SUPPLIER_ID,\n" );
    	sql.append("       T.SUPPLIER_CODE,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("       T1.ERP_NO,\n" );
    	sql.append("       T1.PART_IDENTIFY,\n" );
    	sql.append("       T.IN_COUNT,\n" );
    	sql.append("       T.IN_AMOUNT,\n" );
    	sql.append("       T.RETURN_COUNT,\n" );
    	sql.append("       T.RETURN_AMOUNT,\n" );
    	sql.append("       T.INVOICE_AMOUNT,\n" );
    	sql.append("       T.PLAN_AMOUNT,\n" );
    	sql.append("       NVL(T.SETTLE_AMOUNT,0) SETTLE_AMOUNT,\n" );
    	sql.append("       NVL(T.UNSETTLE_AMOUNT,0) UNSETTLE_AMOUNT,\n" );
    	sql.append("       T.INVOICE_MONTH,\n" );
    	sql.append("       T.SETTLE_STATUS,T.REMARKS\n" );
    	sql.append("  FROM PT_BU_SUP_INVOICE_SUMMARY T,PT_BA_SUPPLIER T1");
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("SETTLE_STATUS", "GYSJSZT");
    	bs.setFieldDic("PART_IDENTIFY", "YXBS");
    	return bs;
    }
	
	public BaseResultSet settleOrderSearch(PageManager page, User user, String SUM_ID) throws Exception
    {
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.DETAIL_ID,T.ORDER_ID,'采购' AS ORDER_TYPE,T.TYPE,T1.PURCHASE_COUNT COUNT,T1.PURCHASE_AMOUNT AMOUNT,\n" );
    	sql.append("T1.SPLIT_NO ORDER_NO \n");
    	sql.append("FROM PT_BU_SUP_INVOICE_SUMMARY_DTL T,PT_BU_PCH_ORDER_SPLIT T1\n" );
    	sql.append("WHERE 1=1\n" );
    	sql.append("AND T.ORDER_ID = T1.SPLIT_ID\n" );
    	sql.append("AND T.SUM_ID = "+SUM_ID+"\n" );
    	sql.append("AND T1.INVOICE_NO IS NOT NULL\n" );
    	sql.append("AND T1.SETTLE_STATUS = "+DicConstant.GYSJSZT_01+"\n" );
    	sql.append("UNION\n" );
    	sql.append("SELECT T.DETAIL_ID,T.ORDER_ID,'退货' AS ORDER_TYPE,T.TYPE,T1.COUNT ,T1.AMOUNT,\n" );
    	sql.append("T1.RETURN_NO ORDER_NO \n");
    	sql.append("FROM PT_BU_SUP_INVOICE_SUMMARY_DTL T,PT_BU_PCH_RETURN T1\n" );
    	sql.append("WHERE 1=1\n" );
    	sql.append("AND T.ORDER_ID = T1.RETURN_ID\n" );
    	sql.append("AND T.SUM_ID = "+SUM_ID+"");
    	sql.append("AND T1.INVOICE_NO IS NOT NULL\n" );
    	sql.append("AND T1.SETTLE_STATUS = "+DicConstant.GYSJSZT_01+"\n" );
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
	
	public QuerySet getType(String SUM_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.ORDER_ID,T.TYPE\n" );
    	sql.append("FROM PT_BU_SUP_INVOICE_SUMMARY_DTL T\n" );
    	sql.append("WHERE T.SUM_ID = "+SUM_ID+"");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	public boolean updatePch(PtBuPchOrderSplitVO vo)
    		throws Exception
    {
    	return factory.update(vo);
    }
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
	public QuerySet checkAll(String SUM_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT NVL(A.S_NO, 0) S_NO, NVL(R_NO, 0) R_NO\n" );
    	sql.append("  FROM (SELECT COUNT(T.SPLIT_ID) S_NO, T1.SUM_ID\n" );
    	sql.append("          FROM PT_BU_PCH_ORDER_SPLIT T, PT_BU_SUP_INVOICE_SUMMARY_DTL T1\n" );
    	sql.append("         WHERE T.SPLIT_ID = T1.ORDER_ID\n" );
    	sql.append("           AND T.SETTLE_STATUS <> "+DicConstant.GYSJSZT_02+"\n" );
    	sql.append("         GROUP BY T1.SUM_ID) A,\n" );
    	sql.append("       (SELECT COUNT(T2.RETURN_ID) R_NO, T3.SUM_ID\n" );
    	sql.append("          FROM PT_BU_PCH_RETURN T2, PT_BU_SUP_INVOICE_SUMMARY_DTL T3\n" );
    	sql.append("         WHERE T2.RETURN_ID = T3.ORDER_ID\n" );
    	sql.append("           AND T2.SETTLE_STATUS <> "+DicConstant.GYSJSZT_02+"\n" );
    	sql.append("         GROUP BY T3.SUM_ID) B\n" );
    	sql.append(" WHERE A.SUM_ID = B.SUM_ID(+)\n" );
    	sql.append("   AND A.SUM_ID = "+SUM_ID+"");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	public boolean upSumAmount(String SUM_ID,String AMOUNT,String accountType,User user) throws Exception
    {
		StringBuffer sql= new StringBuffer();
		sql.append("UPDATE PT_BU_SUP_INVOICE_SUMMARY T\n" );
		sql.append("   SET T.SETTLE_AMOUNT   = NVL(T.SETTLE_AMOUNT,0) + "+AMOUNT+",\n" );
		sql.append("       T.UNSETTLE_AMOUNT = T.UNSETTLE_AMOUNT - "+AMOUNT+",\n" );
		sql.append("                           T.SETTLE_STATUS = "+DicConstant.GYSJSZT_02+", T.ACCOUNT_TYPE = "+accountType+", T.UPDATE_TIME = SYSDATE, T.UPDATE_USER = '"+user.getAccount()+"'\n" );
		sql.append(" WHERE T.SUM_ID = "+SUM_ID+"");
    	return factory.update(sql.toString(), null);
    }
	public boolean updatePchOrder(String SUM_ID) throws Exception
    {
		StringBuffer sql= new StringBuffer();
		sql.append("UPDATE PT_BU_PCH_RETURN T\n" );
		sql.append("SET T.SETTLE_STATUS = "+DicConstant.GYSJSZT_02+",\n" );
		sql.append("T.SETTLE_DATE = SYSDATE\n" );
		sql.append("WHERE T.RETURN_ID IN (SELECT ORDER_ID FROM PT_BU_SUP_INVOICE_SUMMARY_DTL WHERE SUM_ID = "+SUM_ID+" AND TYPE = 2) AND T.SETTLE_STATUS = "+DicConstant.GYSJSZT_01+"");
    	return factory.update(sql.toString(), null);
    }
	public BaseResultSet settleModSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres +="AND T.SETTLE_STATUS ="+DicConstant.GYSJSZT_02+"\n";
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
    	sql.append("       T.INVOICE_AMOUNT,T.ACCOUNT_TYPE ,\n" );
    	sql.append("       NVL(T.SETTLE_AMOUNT,0) SETTLE_AMOUNT,\n" );
    	sql.append("       NVL(T.UNSETTLE_AMOUNT,0) UNSETTLE_AMOUNT,\n" );
    	sql.append("       T.INVOICE_MONTH,\n" );
    	sql.append("       T.SETTLE_STATUS\n" );
    	sql.append("  FROM PT_BU_SUP_INVOICE_SUMMARY T");
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("SETTLE_STATUS", "GYSJSZT");
    	bs.setFieldDic("ACCOUNT_TYPE", "ZJZHLX");
    	return bs;
    }
	public boolean modSettle(String SUM_ID,String AMOUNT,User user) throws Exception
    {
		StringBuffer sql= new StringBuffer();
		sql.append("UPDATE PT_BU_SUP_INVOICE_SUMMARY T\n" );
		sql.append("   SET T.SETTLE_AMOUNT   = T.SETTLE_AMOUNT + "+AMOUNT+",\n" );
		sql.append("       T.UNSETTLE_AMOUNT = T.UNSETTLE_AMOUNT - "+AMOUNT+",\n" );
		sql.append("  T.UPDATE_TIME = SYSDATE, T.UPDATE_USER = '"+user.getAccount()+"'\n" );
		sql.append(" WHERE T.SUM_ID = "+SUM_ID+"");
    	return factory.update(sql.toString(), null);
    }
	public QuerySet getSuoSet(String SUM_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT SUPPLIER_ID ,ACCOUNT_TYPE FROM PT_BU_SUP_INVOICE_SUMMARY WHERE 1=1 \n");
    	sql.append("   AND SUM_ID = "+SUM_ID+"");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	public boolean insertLog(PtBuSupSettleLogVO vo)
    		throws Exception
    {
    	return factory.insert(vo);
    }
	public QuerySet getId() throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT F_GETID() AS ID FROM DUAL WHERE 1=1 \n");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	 public BaseResultSet searchImport(PageManager pPageManager, String pConditions) throws Exception {
		    pConditions = pConditions+"AND T.SUPPLIER_CODE = T1.SUPPLIER_CODE";
	        pPageManager.setFilter(pConditions);
	        StringBuffer sql = new StringBuffer();
	        sql.append(" SELECT T.*,T1.ERP_NO\n");
	        sql.append(" FROM \n");
	        sql.append("     PT_BU_SUP_INVOICE_SUMMARY_TMP T,PT_BA_SUPPLIER T1\n");
	        //执行方法，不需要传递conn参数
	        return factory.select(sql.toString(),pPageManager);
	    }
	 public QuerySet getTmp(User user,String tmpNo) throws Exception
	    {
	    	QuerySet qs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT T2.SUM_ID, T2.UNSETTLE_AMOUNT AMOUNT,T.SETTLE_AMOUNT,T1.ID TYPE\n" );
	    	sql.append("  FROM PT_BU_SUP_INVOICE_SUMMARY_TMP T,\n" );
	    	sql.append("       DIC_TREE                      T1,\n" );
	    	sql.append("       PT_BU_SUP_INVOICE_SUMMARY     T2\n" );
	    	sql.append(" WHERE T.ACCOUNT_TYPE = T1.DIC_VALUE\n" );
	    	sql.append("   AND T.SUPPLIER_CODE = T2.SUPPLIER_CODE\n" );
	    	sql.append("   AND T.SELECT_MONTH = T2.INVOICE_MONTH\n" );
	    	sql.append("   AND T.USER_ACCOUNT = '"+user.getAccount()+"'");
	    	if(!"".equals(tmpNo)&&tmpNo!=null){
	    		sql.append(" AND T.TMP_NO NOT IN ("+tmpNo+")\n");
	    	}
	    	qs = factory.select(null, sql.toString());
	    	return qs;
	    }
	 public QuerySet expData(String tmpNo,User user) throws Exception {

			 StringBuffer sql= new StringBuffer();
			 sql.append("SELECT T.SUPPLIER_CODE, T.ACCOUNT_TYPE, T.SELECT_MONTH, T.SETTLE_AMOUNT\n" );
			 sql.append("  FROM PT_BU_SUP_INVOICE_SUMMARY_TMP T\n" );
			 sql.append(" WHERE T.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
			 sql.append("   AND T.TMP_NO NOT IN ("+tmpNo+")");
	        //执行方法，不需要传递conn参数
	        return factory.select(null, sql.toString());
	    }
	 public void updateOrder(String SUM_ID,User user) throws Exception {
			 StringBuffer sql= new StringBuffer();
			 sql.append("UPDATE PT_BU_PCH_ORDER_SPLIT T\n" );
			 sql.append("   SET T.SETTLE_STATUS = 205302,\n" );
			 sql.append("       T.SETTLE_DATE   = SYSDATE,\n" );
			 sql.append("       T.UPDATE_USER   = '"+user.getAccount()+"',\n" );
			 sql.append("       T.UPDATE_TIME   = SYSDATE\n" );
			 sql.append(" WHERE T.SPLIT_ID IN\n" );
			 sql.append("       (SELECT ORDER_ID FROM PT_BU_SUP_INVOICE_SUMMARY_DTL WHERE SUM_ID = "+SUM_ID+")");
		     factory.update(sql.toString(), null);
	    }
	 public QuerySet download(String conditions,PageManager page)throws Exception{
	    	String wheres = "WHERE "+conditions;  
	    	wheres += "  AND T.SUPPLIER_ID = T1.SUPPLIER_ID AND T.SETTLE_STATUS = 205301 AND T.INVOICE_STATUS ="+DicConstant.GYSKPZT_05+"";
	    	wheres += "  ORDER BY T.SUPPLIER_CODE DESC";
	        page.setFilter(wheres);
	    	BaseResultSet bs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT T.SUPPLIER_CODE, T.ACCOUNT_TYPE, T.INVOICE_MONTH, T.SETTLE_AMOUNT,T1.ERP_NO\n" );
	    	sql.append("  FROM PT_BU_SUP_INVOICE_SUMMARY T,PT_BA_SUPPLIER T1\n");
			    return factory.select(null, sql.toString()+wheres);
			}
}
