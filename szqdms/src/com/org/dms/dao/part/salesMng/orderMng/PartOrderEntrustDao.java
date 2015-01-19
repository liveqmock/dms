package com.org.dms.dao.part.salesMng.orderMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuInvoiceEntrustDtlVO;
import com.org.dms.vo.part.PtBuInvoiceEntrustVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class PartOrderEntrustDao extends BaseDAO{
	public static final PartOrderEntrustDao getInstance(ActionContext atx) {
		PartOrderEntrustDao dao = new PartOrderEntrustDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	
	public BaseResultSet partOrderEntrustSearch(PageManager page, User user, String conditions) throws Exception
    {
        String wheres = conditions;
        wheres += " AND T.ENTRUST_ID = T1.ENTRUST_ID(+) AND T.PRINT_STATUS = "+DicConstant.DYZT_01+" AND T.USER_ACCOUNT = '"+user.getAccount()+"'\n"
                + " ORDER BY T.ENTRUST_ID ASC\n";
        page.setFilter(wheres);
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.ENTRUST_ID,\n" );
        sql.append("       T.ENTRUST_NO,\n" );
        sql.append("       T.SELECT_MONTH,\n" );
        sql.append("       T.USER_ACCOUNT,\n" );
        sql.append("       T.TARIFF_TYPE,\n" );
        sql.append("       T.ORG_CODE,\n" );
        sql.append("       T.ORG_NAME,\n" );
        sql.append("       T.ADDRESS,\n" );
        sql.append("       T.TELEPHONE,\n" );
        sql.append("       T.TARIFF_NO,\n" );
        sql.append("       T.OPEN_BANK,\n" );
        sql.append("       T.BANK_ACCOUNT,\n" );
        sql.append("       T.PRINT_STATUS,\n" );
        sql.append("       T.PRINT_DATE,\n" );
        sql.append("       T1.AMOUNT\n" );
        sql.append("  FROM PT_BU_INVOICE_ENTRUST T,\n" );
        sql.append("       (SELECT SUM(A.IN_INVOICE_AMOUNT) AMOUNT, A.ENTRUST_ID\n" );
        sql.append("          FROM PT_BU_INVOICE_ENTRUST_DTL A\n" );
        sql.append("         GROUP BY A.ENTRUST_ID) T1");
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("TARIFF_TYPE", "FPLX");
        bs.setFieldDic("PRINT_STATUS", "DYZT");
        bs.setFieldDateFormat("PRINT_DATE", "yyyy-MM-dd");
        bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
        // 提报人
        bs.setFieldUserID("USER_ACCOUNT");
        return bs;
    }
	
	
	public BaseResultSet orgSearch(PageManager page, User user, String conditions ) throws Exception
    {
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T2.DEALER_NAME ONAME,\n" );
    	sql.append("       T1.CODE,\n" );
    	sql.append("       T2.ADDRESS,\n" );
    	sql.append("       T2.BUS_PERSON_TEL,\n" );
    	sql.append("       T2.TARIFF,\n" );
    	sql.append("       T2.OPEN_BANK,\n" );
    	sql.append("       T2.BANK_ACCOUNT\n" );
    	sql.append("  FROM TM_ORG T1, MAIN_DEALER T2\n" );
    	sql.append(" WHERE T1.ORG_ID = T2.ORG_ID\n" );
    	sql.append("   AND T1.ORG_TYPE ="+DicConstant.ZZLB_09+"\n" );
    	sql.append("   AND T1.ORG_ID IN\n" );
    	sql.append("       (SELECT ORG_ID\n" );
    	sql.append("          FROM TM_ORG\n" );
    	sql.append("         WHERE PID IN (SELECT ORG_ID\n" );
    	sql.append("                         FROM PT_BA_ORDER_CHECK\n" );
    	sql.append("                        WHERE USER_ACCOUNT = '"+user.getAccount()+"'))");
    	//sql.append("  AND NOT EXISTS\n" );
    	//sql.append("(SELECT 1 FROM PT_BU_INVOICE_ENTRUST T3 WHERE T1.CODE = T3.ORG_CODE AND SELECT_MONTH = TO_CHAR(SYSDATE,'YYYY-MM'))");
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
	
	public boolean insertEntrust(PtBuInvoiceEntrustVO vo)
            throws Exception {
        return factory.insert(vo);
    }
	
	public boolean updateEntrust(PtBuInvoiceEntrustVO vo)
            throws Exception {
        return factory.update(vo);
    }

	public QuerySet getOrderInfo(String ORDER_ID) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
        //执行方法，不需要传递conn参数
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.ORDER_ID, T.ORG_CODE, T.ORG_NAME\n" );
        sql.append("  FROM PT_BU_SALE_ORDER T\n" );
        sql.append(" WHERE T.ORDER_ID = "+ORDER_ID+"");
        qs = factory.select(null,sql.toString());
        return qs;
    }
	public QuerySet getEntrust(String ENTRUST_ID) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
        //执行方法，不需要传递conn参数
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.ENTRUST_ID,\n" );
        sql.append("       T.ENTRUST_NO,\n" );
        sql.append("       T.SELECT_MONTH,\n" );
        sql.append("       T1.PERSON_NAME USER_ACCOUNT,\n" );
        sql.append("       T2.DIC_VALUE TARIFF_TYPE,\n" );
        sql.append("       T.ORG_CODE,\n" );
        sql.append("       T.ORG_NAME,\n" );
        sql.append("       T.ADDRESS,\n" );
        sql.append("       T.TELEPHONE,\n" );
        sql.append("       T.TARIFF_NO,\n" );
        sql.append("       T.OPEN_BANK,\n" );
        sql.append("       T.BANK_ACCOUNT,\n" );
        sql.append("       T.PRINT_STATUS,\n" );
        sql.append("       T.REMARKS,\n" );
        sql.append("       TO_CHAR(SYSDATE, 'YYYY-MM-DD') PRINT_DATE\n" );
        sql.append("  FROM PT_BU_INVOICE_ENTRUST T,TM_USER T1,DIC_TREE T2\n" );
        sql.append(" WHERE T.ENTRUST_ID = "+ENTRUST_ID+" AND T.USER_ACCOUNT = T1.ACCOUNT AND T.TARIFF_TYPE = T2.ID");
        qs = factory.select(null,sql.toString());
        return qs;
    }
	public BaseResultSet proSearch(PageManager page, User user, String ENTRUST_ID) throws Exception
    {
//    	String wheres = conditions;
//        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.DTL_ID,\n" );
    	sql.append("       T.PRO_NAME,\n" );
    	sql.append("       T.PRO_COUNT,\n" );
    	sql.append("       T.PRO_UNIT,\n" );
    	sql.append("       T.IN_INVOICE_PRICE,\n" );
    	sql.append("       T.IN_INVOICE_AMOUNT\n" );
    	sql.append("  FROM PT_BU_INVOICE_ENTRUST_DTL T\n" );
    	sql.append(" WHERE T.ENTRUST_ID = "+ENTRUST_ID+"");
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
	public boolean insertEntrustDtl(PtBuInvoiceEntrustDtlVO vo)
            throws Exception {
        return factory.insert(vo);
    }
	public boolean partUpdate(PtBuInvoiceEntrustDtlVO vo)
            throws Exception {
        return factory.update(vo);
    }
	public boolean updateParts(String DTL_ID ) throws Exception
    {
		StringBuffer sql= new StringBuffer();
		sql.append("DELETE FROM PT_BU_INVOICE_ENTRUST_DTL WHERE DTL_ID = "+DTL_ID+"");
    	return factory.update(sql.toString(), null);
    }
	public QuerySet queryEntrustDtl(String ENTRUST_ID) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
        //执行方法，不需要传递conn参数
        StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.DTL_ID,\n" );
    	sql.append("       T.PRO_NAME,\n" );
    	sql.append("       T.PRO_COUNT,\n" );
    	sql.append("       T.PRO_UNIT,\n" );
    	sql.append("       T.IN_INVOICE_PRICE,\n" );
    	sql.append("       T.IN_INVOICE_AMOUNT\n" );
    	sql.append("  FROM PT_BU_INVOICE_ENTRUST_DTL T\n" );
    	sql.append(" WHERE T.ENTRUST_ID = "+ENTRUST_ID+"");
        qs = factory.select(null,sql.toString());
        return qs;
    }
	
	public BaseResultSet getOrg(User user,PageManager page) throws Exception
    {
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT 1 AS FLAG FROM TM_ORG T WHERE T.CODE ='S029001' AND T.PID IN (SELECT ORG_ID FROM PT_BA_ORDER_CHECK WHERE USER_ACCOUNT = '"+user.getAccount()+"')");
        bs = factory.select(sql.toString(), page);
        return bs;
    }
}
