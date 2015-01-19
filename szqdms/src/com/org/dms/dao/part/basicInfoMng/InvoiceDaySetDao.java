package com.org.dms.dao.part.basicInfoMng;

import java.sql.CallableStatement;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBaInvoiceDayVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.SequenceUtil;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;
/**
 * @Description:结算日设置Dao
 * @Date: 2014-12-18
 * @Author gouwentan
 */
public class InvoiceDaySetDao extends BaseDAO{
	public static final InvoiceDaySetDao getInstance(ActionContext atx) {
		InvoiceDaySetDao dao = new InvoiceDaySetDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	/**
	 * 结算日设置查询
	 */
	public BaseResultSet invoiceDaySetSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres += " ORDER BY CREATE_TIME DESC\n";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT DAY_ID,\n" );
    	sql.append("       INVOICE_MONTH,\n" );
    	sql.append("       START_DATE,\n" );
    	sql.append("       END_DATE,\n" );
    	sql.append("       INVOICE_TYPE,\n" );
    	sql.append("       INVOICE_STATUS,\n" );
    	sql.append("       INVOICE_DATE,\n" );
    	sql.append("       CREATE_TIME,\n" );
    	sql.append("       CREATE_USER,\n" );
    	sql.append("       DAY_STATUS,\n" ); // 添加结算日期状态
    	sql.append("       CASE\n" );
    	sql.append("         WHEN INVOICE_TYPE = 206501 THEN\n" );
    	sql.append("          (SELECT CASE\n" );
    	sql.append("                    WHEN COUNT(1) > 1 THEN\n" );
    	sql.append("                     100101\n" );
    	sql.append("                    ELSE\n" );
    	sql.append("                     100102\n" );
    	sql.append("                  END\n" );
    	sql.append("             FROM PT_BU_SUP_INVOICE_SUMMARY B\n" );
    	sql.append("            WHERE B.INVOICE_STATUS >= 205202\n" );
    	sql.append("              AND B.START_DATE = T.START_DATE\n" );
    	sql.append("              AND B.END_DATE = T.END_DATE)\n" );
    	sql.append("         ELSE\n" );
    	sql.append("          (SELECT CASE\n" );
    	sql.append("                    WHEN COUNT(1) > 1 THEN\n" );
    	sql.append("                     100101\n" );
    	sql.append("                    ELSE\n" );
    	sql.append("                     100102\n" );
    	sql.append("                  END\n" );
    	sql.append("             FROM PT_BU_DEALER_INVOICE_SUMMARY B\n" );
    	sql.append("            WHERE B.INVOICE_STATUS = 202302\n" );
    	sql.append("              AND B.START_DATE = T.START_DATE\n" );
    	sql.append("              AND B.END_DATE = T.END_DATE)\n" );
    	sql.append("       END FLAG\n" );
    	sql.append("  FROM PT_BA_INVOICE_DAY T");
    	bs = factory.select(sql.toString(), page);
		bs.setFieldDic("INVOICE_TYPE", "PJJSLX");
		bs.setFieldDic("INVOICE_STATUS", "TJZT");
		bs.setFieldDic("FLAG", "SF");
		bs.setFieldDateFormat("START_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("END_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("INVOICE_DATE", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDic("DAY_STATUS", "JSRQZT"); // 添加结算日期状态
    	return bs;
    }
	
	/**
	 * 结算日新增
	 */
	public boolean invoiceDaySetInsert(PtBaInvoiceDayVO vo)
            throws Exception {
		String dayId=SequenceUtil.getCommonSerivalNumber(factory);
		vo.setDayId(dayId);
        return factory.insert(vo);
    }
	/**
	 * 结算日修改
	 */
	public boolean invoiceDaySetUpdate(PtBaInvoiceDayVO vo)
            throws Exception {
        return factory.update(vo);
    }
	
	public void invoice(String dayId) throws Exception {
		QuerySet qs= null;
		StringBuffer sql= new StringBuffer();
    	sql.append("SELECT DAY_ID,\n" );
    	sql.append("       INVOICE_MONTH,\n" );
    	sql.append("       TO_CHAR(START_DATE,'yyyy-MM-dd') START_DATE,\n" );
    	sql.append("       TO_CHAR(END_DATE,'yyyy-MM-dd') END_DATE,\n" );
    	sql.append("       INVOICE_TYPE,\n" );
    	sql.append("       INVOICE_STATUS,\n" );
    	sql.append("       INVOICE_DATE,\n" );
    	sql.append("       CREATE_TIME,\n" );
    	sql.append("       CREATE_USER,\n" );
    	sql.append("       CASE\n" );
    	sql.append("         WHEN INVOICE_TYPE = 206501 THEN\n" );
    	sql.append("          (SELECT CASE\n" );
    	sql.append("                    WHEN COUNT(1) > 1 THEN\n" );
    	sql.append("                     100101\n" );
    	sql.append("                    ELSE\n" );
    	sql.append("                     100102\n" );
    	sql.append("                  END\n" );
    	sql.append("             FROM PT_BU_SUP_INVOICE_SUMMARY B\n" );
    	sql.append("            WHERE B.INVOICE_STATUS >= 205201\n" );
    	sql.append("              AND B.START_DATE = T.START_DATE\n" );
    	sql.append("              AND B.END_DATE = T.END_DATE)\n" );
    	sql.append("         ELSE\n" );
    	sql.append("          (SELECT CASE\n" );
    	sql.append("                    WHEN COUNT(1) > 1 THEN\n" );
    	sql.append("                     100101\n" );
    	sql.append("                    ELSE\n" );
    	sql.append("                     100102\n" );
    	sql.append("                  END\n" );
    	sql.append("             FROM PT_BU_DEALER_INVOICE_SUMMARY B\n" );
    	sql.append("            WHERE B.INVOICE_STATUS = 202302\n" );
    	sql.append("              AND B.START_DATE = T.START_DATE\n" );
    	sql.append("              AND B.END_DATE = T.END_DATE)\n" );
    	sql.append("       END FLAG\n" );
    	sql.append("  FROM PT_BA_INVOICE_DAY T WHERE DAY_ID = "+dayId+"\n");
		qs = factory.select(null, sql.toString());
		if(qs.getRowCount()>0){
			String invoiceType = qs.getString(1,"INVOICE_TYPE");
			String startDate = qs.getString(1,"START_DATE");
			String endDate = qs.getString(1,"END_DATE");
//			CallableStatement proc = null;
//	        proc = factory.getConnection().prepareCall("{call P_PART_SUP_OR_DEALER_INVOICE(?,?,?)}");
//	        proc.setString(1, invoiceType);
//	        proc.setString(2, startDate);
//	        proc.setString(3, endDate);
//	        proc.execute();
			if(DicConstant.PJJSLX_01.equals(invoiceType)){
				StringBuffer sql1= new StringBuffer();
				sql1.append("INSERT INTO PT_BU_SUP_INVOICE_SUMMARY\n" );
				sql1.append("      (SUM_ID,\n" );
				sql1.append("       SUPPLIER_ID,\n" );
				sql1.append("       SUPPLIER_NAME,\n" );
				sql1.append("       SUPPLIER_CODE,\n" );
				sql1.append("       INVOICE_MONTH,\n" );
				sql1.append("       IN_AMOUNT,\n" );
				sql1.append("       IN_COUNT,\n" );
				sql1.append("       RETURN_AMOUNT,\n" );
				sql1.append("       RETURN_COUNT,\n" );
				sql1.append("       INVOICE_AMOUNT,\n" );
				sql1.append("       PLAN_AMOUNT,\n" );
				sql1.append("       SETTLE_STATUS,\n" );
				sql1.append("       INVOICE_STATUS,\n" );
				sql1.append("       START_DATE,\n" );
				sql1.append("       END_DATE,\n" );
				sql1.append("       CREATE_TIME)\n" );
				sql1.append("      SELECT F_GETID(),\n" );
				sql1.append("             A.SUPPLIER_ID,\n" );
				sql1.append("             A.SUPPLIER_NAME,\n" );
				sql1.append("             A.SUPPLIER_CODE,\n" );
				sql1.append("             TO_CHAR(SYSDATE, 'YYYY-MM'),\n" );
				sql1.append("             NVL(A.STORAGE_AMOUNT,0),\n" );
				sql1.append("             NVL(A.STORGAE_COUNT,0),\n" );
				sql1.append("             NVL(B.RET_AMOUNT,0),\n" );
				sql1.append("             NVL(B.RET_COUNT,0),\n" );
				sql1.append("             NVL(A.STORAGE_AMOUNT, 0) - NVL(B.RET_AMOUNT, 0),\n" );
				sql1.append("             NVL(A.IPLAN_AMOUNT, 0) - NVL(B.RPLAN_AMOUNT, 0),\n" );
				sql1.append("             205301,\n" );
				sql1.append("             205201,\n" );
				sql1.append("             TO_DATE('"+startDate+"', 'YYYY-MM-DD'),\n" );
				sql1.append("             TO_DATE('"+endDate+"', 'YYYY-MM-DD'),\n" );
				sql1.append("             SYSDATE\n" );
				sql1.append("        FROM (SELECT SUM(NVL(T.STORAGE_COUNT, 0) * NVL(T.PCH_PRICE, 0)) STORAGE_AMOUNT,\n" );
				sql1.append("                     SUM(NVL(T.STORAGE_COUNT, 0) * NVL(T.PLAN_PRICE, 0)) IPLAN_AMOUNT,\n" );
				sql1.append("                     SUM(NVL(T.STORAGE_COUNT, 0)) STORGAE_COUNT,\n" );
				sql1.append("                     T1.SUPPLIER_ID,\n" );
				sql1.append("                     T2.SUPPLIER_NAME,\n" );
				sql1.append("                     T2.SUPPLIER_CODE\n" );
				sql1.append("                FROM PT_BU_PCH_ORDER_SPLIT_DTL T, PT_BU_PCH_ORDER_SPLIT T1,PT_BA_SUPPLIER T2\n" );
				sql1.append("               WHERE T.SPLIT_ID = T1.SPLIT_ID AND T1.SUPPLIER_ID =T2.SUPPLIER_ID\n" );
				sql1.append("                 AND T1.SETTLE_STATUS = '205301'\n" );
				sql1.append("                 AND T1.ORDER_STATUS = '201005'\n" );
				sql1.append("                 AND NVL(T.STORAGE_COUNT,0) > 0\n" );
				sql1.append("                 AND T1.CLOSE_DATE >=\n" );
				sql1.append("                     TO_DATE('"+startDate+" 00:00:00',\n" );
				sql1.append("                             'YYYY-MM-DD HH24:MI:SS')\n" );
				sql1.append("                 AND T1.CLOSE_DATE <=\n" );
				sql1.append("                     TO_DATE('"+endDate+" 23:59:59',\n" );
				sql1.append("                             'YYYY-MM-DD HH24:MI:SS')\n" );
				sql1.append("                 AND NOT EXISTS\n" );
				sql1.append("               (SELECT 1\n" );
				sql1.append("                        FROM PT_BU_SUP_INVOICE_SUMMARY_DTL D\n" );
				sql1.append("                       WHERE D.ORDER_ID = T1.SPLIT_ID)\n" );
				sql1.append("               GROUP BY T1.SUPPLIER_ID, T2.SUPPLIER_NAME, T2.SUPPLIER_CODE) A,\n" );
				sql1.append("             (SELECT SUM(NVL(T2.COUNT, 0) * NVL(T2.PCH_PRICE, 0)) RET_AMOUNT,\n" );
				sql1.append("                     SUM(NVL(T2.COUNT, 0) * NVL(T2.PLAN_PRICE, 0)) RPLAN_AMOUNT,\n" );
				sql1.append("                     SUM(NVL(T2.COUNT, 0)) RET_COUNT,\n" );
				sql1.append("                     T3.SUPPLIER_ID\n" );
				sql1.append("                FROM PT_BU_PCH_RETURN_DTL T2, PT_BU_PCH_RETURN T3\n" );
				sql1.append("               WHERE T2.RETURN_ID = T3.RETURN_ID\n" );
				sql1.append("                 AND T3.SETTLE_STATUS = '205301'\n" );
				sql1.append("                 AND T3.RETURN_STATUS = '201104'\n" );
				sql1.append("                 AND NVL(T2.COUNT,0) > 0\n" );
				sql1.append("                 AND T3.CLOSE_DATE >=\n" );
				sql1.append("                     TO_DATE('"+startDate+" 00:00:00',\n" );
				sql1.append("                             'YYYY-MM-DD HH24:MI:SS')\n" );
				sql1.append("                 AND T3.CLOSE_DATE <=\n" );
				sql1.append("                     TO_DATE('"+endDate+" 23:59:59',\n" );
				sql1.append("                             'YYYY-MM-DD HH24:MI:SS')\n" );
				sql1.append("                 AND NOT EXISTS\n" );
				sql1.append("               (SELECT 1\n" );
				sql1.append("                        FROM PT_BU_SUP_INVOICE_SUMMARY_DTL D\n" );
				sql1.append("                       WHERE D.ORDER_ID = T3.RETURN_ID)\n" );
				sql1.append("               GROUP BY T3.SUPPLIER_ID) B\n" );
				sql1.append("       WHERE 1 = 1\n" );
				sql1.append("         AND A.SUPPLIER_ID = B.SUPPLIER_ID(+)");
				factory.update(sql1.toString(), null);
				StringBuffer sql3 = new StringBuffer();
				sql3.append("                UPDATE PT_BU_PCH_ORDER_SPLIT T1 SET T1.INVOICE_STATUS = 205201\n" );
				sql3.append("               WHERE T1.SETTLE_STATUS = '205301'\n" );
				sql3.append("                 AND T1.ORDER_STATUS = '201005'\n" );
				sql3.append("                 AND NVL(T1.STORAGE_COUNT,0) > 0\n" );
				sql3.append("                 AND T1.CLOSE_DATE >=\n" );
				sql3.append("                     TO_DATE('"+startDate+" 00:00:00',\n" );
				sql3.append("                             'YYYY-MM-DD HH24:MI:SS')\n" );
				sql3.append("                 AND T1.CLOSE_DATE <=\n" );
				sql3.append("                     TO_DATE('"+endDate+" 23:59:59',\n" );
				sql3.append("                             'YYYY-MM-DD HH24:MI:SS')\n" );
				sql3.append("                 AND NOT EXISTS\n" );
				sql3.append("               (SELECT 1\n" );
				sql3.append("                        FROM PT_BU_SUP_INVOICE_SUMMARY_DTL D\n" );
				sql3.append("                       WHERE D.ORDER_ID = T1.SPLIT_ID)\n" );
				factory.update(sql3.toString(),null);
				StringBuffer sql4 = new StringBuffer();
				sql4.append("                UPDATE PT_BU_PCH_RETURN T4 SET T4.INVOICE_STATUS = 205201\n" );
				sql4.append("               WHERE T4.SETTLE_STATUS = '205301'\n" );
				sql4.append("                 AND T4.RETURN_STATUS = '201104'\n" );
				sql4.append("                 AND NVL(T4.COUNT,0) > 0\n" );
				sql4.append("                 AND T4.CLOSE_DATE >=\n" );
				sql4.append("                     TO_DATE('"+startDate+" 00:00:00',\n" );
				sql4.append("                             'YYYY-MM-DD HH24:MI:SS')\n" );
				sql4.append("                 AND T4.CLOSE_DATE <=\n" );
				sql4.append("                     TO_DATE('"+endDate+" 23:59:59',\n" );
				sql4.append("                             'YYYY-MM-DD HH24:MI:SS')\n" );
				sql4.append("                 AND NOT EXISTS\n" );
				sql4.append("               (SELECT 1\n" );
				sql4.append("                        FROM PT_BU_SUP_INVOICE_SUMMARY_DTL D\n" );
				sql4.append("                       WHERE D.ORDER_ID = T4.RETURN_ID)");
				factory.update(sql4.toString(),null);
				StringBuffer sql2= new StringBuffer();
				sql2.append("INSERT INTO PT_BU_SUP_INVOICE_SUMMARY_DTL\n" );
				sql2.append("      (DETAIL_ID, SUPPLIER_ID, ORDER_ID, TYPE, SUM_ID)\n" );
				sql2.append("      SELECT F_GETID(), SUPPLIER_ID, ORDER_ID, TYPE, SUM_ID\n" );
				sql2.append("        FROM (SELECT T1.SUPPLIER_ID AS SUPPLIER_ID,\n" );
				sql2.append("                     T1.SPLIT_ID AS ORDER_ID,\n" );
				sql2.append("                     '1' AS TYPE,\n" );
				sql2.append("                     (SELECT M.SUM_ID\n" );
				sql2.append("                        FROM PT_BU_SUP_INVOICE_SUMMARY M\n" );
				sql2.append("                       WHERE M.SUPPLIER_ID = T1.SUPPLIER_ID\n" );
				sql2.append("                         AND M.START_DATE =\n" );
				sql2.append("                             TO_DATE('"+startDate+"', 'YYYY-MM-DD')\n" );
				sql2.append("                         AND M.END_DATE = TO_DATE('"+endDate+"', 'YYYY-MM-DD')) SUM_ID\n" );
				sql2.append("                FROM PT_BU_PCH_ORDER_SPLIT T1\n" );
				sql2.append("               WHERE T1.SETTLE_STATUS = '205301'\n" );
				sql2.append("                 AND T1.ORDER_STATUS = '201005'\n" );
				sql2.append("                 AND NVL(T1.STORAGE_COUNT,0) > 0\n" );
				sql2.append("                 AND T1.CLOSE_DATE >=\n" );
				sql2.append("                     TO_DATE('"+startDate+" 00:00:00',\n" );
				sql2.append("                             'YYYY-MM-DD HH24:MI:SS')\n" );
				sql2.append("                 AND T1.CLOSE_DATE <=\n" );
				sql2.append("                     TO_DATE('"+endDate+" 23:59:59',\n" );
				sql2.append("                             'YYYY-MM-DD HH24:MI:SS')\n" );
				sql2.append("                 AND NOT EXISTS\n" );
				sql2.append("               (SELECT 1\n" );
				sql2.append("                        FROM PT_BU_SUP_INVOICE_SUMMARY_DTL D\n" );
				sql2.append("                       WHERE D.ORDER_ID = T1.SPLIT_ID)\n" );
				sql2.append("              UNION\n" );
				sql2.append("              SELECT T4.SUPPLIER_ID,\n" );
				sql2.append("                     T4.RETURN_ID AS ORDER_ID,\n" );
				sql2.append("                     '2' AS TYPE,\n" );
				sql2.append("                     (SELECT M.SUM_ID\n" );
				sql2.append("                        FROM PT_BU_SUP_INVOICE_SUMMARY M\n" );
				sql2.append("                       WHERE M.SUPPLIER_ID = T4.SUPPLIER_ID\n" );
				sql2.append("                         AND M.START_DATE =\n" );
				sql2.append("                             TO_DATE('"+startDate+"', 'YYYY-MM-DD')\n" );
				sql2.append("                         AND M.END_DATE = TO_DATE('"+endDate+"', 'YYYY-MM-DD')) SUM_ID\n" );
				sql2.append("                FROM PT_BU_PCH_RETURN T4\n" );
				sql2.append("               WHERE T4.SETTLE_STATUS = '205301'\n" );
				sql2.append("                 AND T4.RETURN_STATUS = '201104'\n" );
				sql2.append("                 AND NVL(T4.COUNT,0) > 0\n" );
				sql2.append("                 AND T4.CLOSE_DATE >=\n" );
				sql2.append("                     TO_DATE('"+startDate+" 00:00:00',\n" );
				sql2.append("                             'YYYY-MM-DD HH24:MI:SS')\n" );
				sql2.append("                 AND T4.CLOSE_DATE <=\n" );
				sql2.append("                     TO_DATE('"+endDate+" 23:59:59',\n" );
				sql2.append("                             'YYYY-MM-DD HH24:MI:SS')\n" );
				sql2.append("                 AND NOT EXISTS\n" );
				sql2.append("               (SELECT 1\n" );
				sql2.append("                        FROM PT_BU_SUP_INVOICE_SUMMARY_DTL D\n" );
				sql2.append("                       WHERE D.ORDER_ID = T4.RETURN_ID))");
				factory.update(sql2.toString(),null);
			}else{
				StringBuffer sql3= new StringBuffer();
				sql3.append("INSERT INTO PT_BU_DEALER_INVOICE_SUMMARY\n" );
				sql3.append("      (SUM_ID,\n" );
				sql3.append("       ORG_ID,\n" );
				sql3.append("       ORG_NAME,\n" );
				sql3.append("       ORG_CODE,\n" );
				sql3.append("       INVOICE_MONTH,\n" );
				sql3.append("       SALE_AMOUNT,\n" );
				sql3.append("       SALE_COUNT,\n" );
				sql3.append("       RETURN_AMOUNT,\n" );
				sql3.append("       RETURN_COUNT,\n" );
				sql3.append("       INVOICE_AMOUNT,\n" );
				sql3.append("       PLAN_AMOUNT,\n" );
				sql3.append("       INVOICE_STATUS,\n" );
				sql3.append("       START_DATE,\n" );
				sql3.append("       END_DATE,\n" );
				sql3.append("       CREATE_TIME)\n" );
				sql3.append("      SELECT F_GETID(),\n" );
				sql3.append("             A.ORG_ID,\n" );
				sql3.append("             A.ORG_NAME,\n" );
				sql3.append("             A.ORG_CODE,\n" );
				sql3.append("             TO_CHAR(SYSDATE, 'YYYY-MM'),\n" );
				sql3.append("             NVL(A.DELIVERY_AMOUNT,0),\n" );
				sql3.append("             NVL(A.DELIVERY_COUNT,0),\n" );
				sql3.append("             NVL(B.RET_AMOUNT,0),\n" );
				sql3.append("             NVL(B.RET_COUNT,0),\n" );
				sql3.append("             NVL(A.DELIVERY_AMOUNT, 0) - NVL(B.RET_AMOUNT, 0),\n" );
				sql3.append("             NVL(A.IPLAN_AMOUNT, 0) - NVL(B.RPLAN_AMOUNT, 0),\n" );
				sql3.append("             202301,\n" );
				sql3.append("             TO_DATE('"+startDate+"', 'YYYY-MM-DD'),\n" );
				sql3.append("             TO_DATE('"+endDate+"', 'YYYY-MM-DD'),\n" );
				sql3.append("             SYSDATE\n" );
				sql3.append("        FROM (SELECT SUM(NVL(T.DELIVERY_COUNT, 0) * NVL(T.UNIT_PRICE, 0)) DELIVERY_AMOUNT,\n" );
				sql3.append("                     SUM(NVL(T.DELIVERY_COUNT, 0) * NVL(T.PLAN_PRICE, 0)) IPLAN_AMOUNT,\n" );
				sql3.append("                     SUM(NVL(T.DELIVERY_COUNT, 0)) DELIVERY_COUNT,\n" );
				sql3.append("                     T1.ORG_ID,\n" );
				sql3.append("                     T2.ONAME ORG_NAME,\n" );
				sql3.append("                     T2.CODE ORG_CODE\n" );
				sql3.append("                FROM PT_BU_SALE_ORDER_DTL T, PT_BU_SALE_ORDER T1,TM_ORG T2\n" );
				sql3.append("               WHERE T.ORDER_ID = T1.ORDER_ID AND T2.ORG_ID = T1.ORG_ID\n" );
				sql3.append("                 AND T1.INVOICE_STATUS = '202301'\n" );
				sql3.append("                 AND T1.ORDER_STATUS = '202206'\n" );
				sql3.append("                 AND T1.IF_CHANEL_ORDER = '100102'\n" );
				sql3.append("                 AND T.DELIVERY_COUNT > 0\n" );
				sql3.append("                 AND T1.CLOSE_DATE >=\n" );
				sql3.append("                     TO_DATE('"+startDate+" 00:00:00',\n" );
				sql3.append("                             'YYYY-MM-DD HH24:MI:SS')\n" );
				sql3.append("                 AND T1.CLOSE_DATE <=\n" );
				sql3.append("                     TO_DATE('"+endDate+" 23:59:59',\n" );
				sql3.append("                             'YYYY-MM-DD HH24:MI:SS')\n" );
				sql3.append("               GROUP BY T1.ORG_ID, T2.ONAME, T2.CODE) A,\n" );
				sql3.append("             (SELECT SUM(NVL(T2.IN_COUNT, 0) * NVL(T2.SALE_PRICE, 0)) RET_AMOUNT,\n" );
				sql3.append("                     SUM(NVL(T2.IN_COUNT, 0) * NVL(T2.PLAN_PRICE, 0)) RPLAN_AMOUNT,\n" );
				sql3.append("                     SUM(NVL(T2.IN_COUNT, 0)) RET_COUNT,\n" );
				sql3.append("                     T3.APPLY_ORG_ID ORG_ID\n" );
				sql3.append("                FROM PT_BU_RETURN_APPLY_DTL T2, PT_BU_RETURN_APPLY T3\n" );
				sql3.append("               WHERE T2.RETURN_ID = T3.RETURN_ID\n" );
				sql3.append("                 AND T3.INVOICE_STATUS = '202301'\n" );
				sql3.append("                 AND T3.APPLY_SATUS = '202405'\n" );
				sql3.append("                 AND T3.RECEIVE_ORG_CODE = 'K000001'\n" );
				sql3.append("                 AND NVL(T2.IN_COUNT,0) > 0\n" );
				sql3.append("                 AND T3.CLOSE_DATE >=\n" );
				sql3.append("                     TO_DATE('"+startDate+" 00:00:00',\n" );
				sql3.append("                             'YYYY-MM-DD HH24:MI:SS')\n" );
				sql3.append("                 AND T3.CLOSE_DATE <=\n" );
				sql3.append("                     TO_DATE('"+endDate+" 23:59:59',\n" );
				sql3.append("                             'YYYY-MM-DD HH24:MI:SS')\n" );
				sql3.append("               GROUP BY T3.APPLY_ORG_ID) B\n" );
				sql3.append("       WHERE 1 = 1\n" );
				sql3.append("         AND A.ORG_ID = B.ORG_ID(+)");
				factory.update(sql3.toString(), null);
				StringBuffer sql4= new StringBuffer();
				sql4.append("INSERT INTO PT_BU_DEALER_INVOICE_DTL\n" );
				sql4.append("      (DTL_ID, ORG_ID, ORDER_ID, TYPE, SUM_ID)\n" );
				sql4.append("      SELECT F_GETID(), ORG_ID, ORDER_ID, TYPE, SUM_ID\n" );
				sql4.append("        FROM (SELECT T1.ORG_ID AS ORG_ID,\n" );
				sql4.append("                     T1.ORDER_ID AS ORDER_ID,\n" );
				sql4.append("                     '1' AS TYPE,\n" );
				sql4.append("                     (SELECT M.SUM_ID\n" );
				sql4.append("                        FROM PT_BU_DEALER_INVOICE_SUMMARY M\n" );
				sql4.append("                       WHERE M.ORG_ID = T1.ORG_ID\n" );
				sql4.append("                         AND M.START_DATE =\n" );
				sql4.append("                             TO_DATE('"+startDate+"', 'YYYY-MM-DD')\n" );
				sql4.append("                         AND M.END_DATE = TO_DATE('"+endDate+"', 'YYYY-MM-DD')) SUM_ID\n" );
				sql4.append("                FROM PT_BU_SALE_ORDER T1\n" );
				sql4.append("               WHERE T1.INVOICE_STATUS = '202301'\n" );
				sql4.append("                 AND T1.ORDER_STATUS = '202206'\n" );
				sql4.append("                 AND T1.IF_CHANEL_ORDER = '100102'\n" );
				sql4.append("                 AND NVL(T1.REAL_AMOUNT,0) > 0\n" );
				sql4.append("                 AND T1.CLOSE_DATE >=\n" );
				sql4.append("                     TO_DATE('"+startDate+" 00:00:00',\n" );
				sql4.append("                             'YYYY-MM-DD HH24:MI:SS')\n" );
				sql4.append("                 AND T1.CLOSE_DATE <=\n" );
				sql4.append("                     TO_DATE('"+endDate+" 23:59:59',\n" );
				sql4.append("                             'YYYY-MM-DD HH24:MI:SS')\n" );
				sql4.append("              UNION\n" );
				sql4.append("              SELECT T4.APPLY_ORG_ID AS ORG_ID,\n" );
				sql4.append("                     T4.RETURN_ID AS ORDER_ID,\n" );
				sql4.append("                     '2' AS TYPE,\n" );
				sql4.append("                     (SELECT M.SUM_ID\n" );
				sql4.append("                        FROM PT_BU_DEALER_INVOICE_SUMMARY M\n" );
				sql4.append("                       WHERE M.ORG_ID = T4.APPLY_ORG_ID\n" );
				sql4.append("                         AND M.START_DATE =\n" );
				sql4.append("                             TO_DATE('"+startDate+"', 'YYYY-MM-DD')\n" );
				sql4.append("                         AND M.END_DATE = TO_DATE('"+endDate+"', 'YYYY-MM-DD')) SUM_ID\n" );
				sql4.append("                FROM PT_BU_RETURN_APPLY T4\n" );
				sql4.append("               WHERE T4.INVOICE_STATUS = '202301'\n" );
				sql4.append("                 AND T4.APPLY_SATUS = '202405'\n" );
				sql4.append("                 AND T4.RECEIVE_ORG_CODE = 'K000001'\n" );
				sql4.append("                 AND T4.CLOSE_DATE >=\n" );
				sql4.append("                     TO_DATE('"+startDate+" 00:00:00',\n" );
				sql4.append("                             'YYYY-MM-DD HH24:MI:SS')\n" );
				sql4.append("                 AND T4.CLOSE_DATE <=\n" );
				sql4.append("                     TO_DATE('"+endDate+" 23:59:59',\n" );
				sql4.append("                             'YYYY-MM-DD HH24:MI:SS'))");
				factory.update(sql4.toString(),null);
			}
	        String sql5 = "UPDATE PT_BA_INVOICE_DAY SET INVOICE_STATUS=206602,INVOICE_DATE=SYSDATE WHERE DAY_ID = "+dayId+"\n";
	        factory.update(sql5, null);
		}
    }
	public void delInvoice(String dayId) throws Exception {
		QuerySet qs= null;
		StringBuffer sql= new StringBuffer();
    	sql.append("SELECT DAY_ID,\n" );
    	sql.append("       INVOICE_MONTH,\n" );
    	sql.append("       TO_CHAR(START_DATE,'yyyy-MM-dd') START_DATE,\n" );
    	sql.append("       TO_CHAR(END_DATE,'yyyy-MM-dd') END_DATE,\n" );
    	sql.append("       INVOICE_TYPE,\n" );
    	sql.append("       INVOICE_STATUS,\n" );
    	sql.append("       INVOICE_DATE,\n" );
    	sql.append("       CREATE_TIME,\n" );
    	sql.append("       CREATE_USER,\n" );
    	sql.append("       CASE\n" );
    	sql.append("         WHEN INVOICE_TYPE = 206502 THEN\n" );
    	sql.append("          (SELECT CASE\n" );
    	sql.append("                    WHEN COUNT(1) > 1 THEN\n" );
    	sql.append("                     100101\n" );
    	sql.append("                    ELSE\n" );
    	sql.append("                     100102\n" );
    	sql.append("                  END\n" );
    	sql.append("             FROM PT_BU_SUP_INVOICE_SUMMARY B\n" );
    	sql.append("            WHERE B.INVOICE_STATUS >= 205201\n" );
    	sql.append("              AND B.START_DATE = T.START_DATE\n" );
    	sql.append("              AND B.END_DATE = T.END_DATE)\n" );
    	sql.append("         ELSE\n" );
    	sql.append("          (SELECT CASE\n" );
    	sql.append("                    WHEN COUNT(1) > 1 THEN\n" );
    	sql.append("                     100101\n" );
    	sql.append("                    ELSE\n" );
    	sql.append("                     100102\n" );
    	sql.append("                  END\n" );
    	sql.append("             FROM PT_BU_DEALER_INVOICE_SUMMARY B\n" );
    	sql.append("            WHERE B.INVOICE_STATUS = 202302\n" );
    	sql.append("              AND B.START_DATE = T.START_DATE\n" );
    	sql.append("              AND B.END_DATE = T.END_DATE)\n" );
    	sql.append("       END FLAG\n" );
    	sql.append("  FROM PT_BA_INVOICE_DAY T WHERE DAY_ID = "+dayId+"\n");
		qs = factory.select(null, sql.toString());
		if(qs.getRowCount()>0){
			String flag = qs.getString(1, "FLAG");
			if("100102".equals(flag)){
				String invoiceType = qs.getString(1,"INVOICE_TYPE");
				String startDate = qs.getString(1,"START_DATE");
				String endDate = qs.getString(1,"END_DATE");
				if("206501".equals(invoiceType)){
					String sql3 = "UPDATE PT_BU_PCH_ORDER_SPLIT T SET T.INVOICE_STATUS = NULL WHERE T.SPLIT_ID IN(SELECT ORDER_ID FROM PT_BU_SUP_INVOICE_SUMMARY_DTL WHERE TYPE =1 AND SUM_ID IN(SELECT SUM_ID FROM PT_BU_SUP_INVOICE_SUMMARY WHERE START_DATE =TO_DATE('"+startDate+"','yyyy-MM-dd') AND END_DATE=TO_DATE('"+endDate+"','yyyy-MM-dd')))";
					String sql4 = "UPDATE PT_BU_PCH_RETURN T SET T.INVOICE_STATUS = NULL WHERE T.RETURN_ID IN(SELECT ORDER_ID FROM PT_BU_SUP_INVOICE_SUMMARY_DTL WHERE TYPE =2 AND SUM_ID IN(SELECT SUM_ID FROM PT_BU_SUP_INVOICE_SUMMARY WHERE START_DATE =TO_DATE('"+startDate+"','yyyy-MM-dd') AND END_DATE=TO_DATE('"+endDate+"','yyyy-MM-dd')))";
					String sql1 = "DELETE FROM PT_BU_SUP_INVOICE_SUMMARY_DTL WHERE SUM_ID IN(SELECT SUM_ID FROM PT_BU_SUP_INVOICE_SUMMARY WHERE START_DATE =TO_DATE('"+startDate+"','yyyy-MM-dd') AND END_DATE=TO_DATE('"+endDate+"','yyyy-MM-dd'))\n";
					String sql2 = "DELETE FROM PT_BU_SUP_INVOICE_SUMMARY WHERE START_DATE =TO_DATE('"+startDate+"','yyyy-MM-dd') AND END_DATE=TO_DATE('"+endDate+"','yyyy-MM-dd')\n";
					factory.update(sql3, null);
					factory.update(sql4, null);
					factory.update(sql1, null);
					factory.update(sql2, null);
				}
				if("206502".equals(invoiceType)){
					String sql1 = "DELETE FROM PT_BU_DEALER_INVOICE_DTL WHERE SUM_ID IN(SELECT SUM_ID FROM PT_BU_DEALER_INVOICE_SUMMARY WHERE START_DATE =TO_DATE('"+startDate+"','yyyy-MM-dd') AND END_DATE=TO_DATE('"+endDate+"','yyyy-MM-dd'))\n";
					String sql2 = "DELETE FROM PT_BU_DEALER_INVOICE_SUMMARY WHERE START_DATE =TO_DATE('"+startDate+"','yyyy-MM-dd') AND END_DATE=TO_DATE('"+endDate+"','yyyy-MM-dd')\n";
					factory.update(sql1, null);
					factory.update(sql2, null);
				}
				String sql3 = "UPDATE PT_BA_INVOICE_DAY SET INVOICE_STATUS=206601,INVOICE_DATE=NULL WHERE DAY_ID = "+dayId+"\n";
		        factory.update(sql3, null);
			}else{
				throw new Exception("已发生结算业务，不能清除.");
			}
		}
    }
}
