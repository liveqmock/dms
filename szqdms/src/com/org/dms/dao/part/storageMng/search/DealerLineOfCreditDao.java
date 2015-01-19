package com.org.dms.dao.part.storageMng.search;

import java.sql.SQLException;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.mvc.context.ActionContext;

/**
 * 
 * ClassName: DealerLineOfCreditDao 
 * Function: 应收账款统计
 * date: 2014年10月29日 下午8:31:03
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class DealerLineOfCreditDao extends BaseDAO {
	
	public static final DealerLineOfCreditDao getInstance(ActionContext ac){
		DealerLineOfCreditDao dao = new DealerLineOfCreditDao();
		ac.setDBFactory(dao.factory);
		return dao;
	}
	
	/**
	 * 
	 * queryList: 查询表单查询
	 * @author fuxiao
	 * Date:2014年10月23日
	 *
	 */
	public BaseResultSet queryList(PageManager pageManager, String conditions) throws Exception{
		String where = " AND A.STATUS = 100201 AND A.ACCOUNT_TYPE = 202704 AND A.ORG_CODE = T1.CODE AND T1.PID = T2.ORG_ID ORDER BY A.ACCOUNT_ID";
		pageManager.setFilter(conditions + where);
		String sql = 
				"SELECT\n" +
				"       T2.CODE P_ORG_CODE,\n" + 
				"       T2.SNAME P_ORG_NAME,\n" + 
				"       T1.ORG_ID ORG_ID,\n" + 
				"       T1.CODE ORG_CODE,\n" + 
				"       T1.SNAME ORG_NAME,\n" + 
				"       A.ACCOUNT_ID,\n" + 
				"		(\n" +
				"          SELECT AC.AVAILABLE_AMOUNT FROM PT_BU_ACCOUNT AC WHERE AC.ORG_ID = A.ORG_ID AND AC.ACCOUNT_TYPE = 202703\n" + 
				"       ) MATERIALS_AMOUNT," + 
				"		(\n" +
				"          SELECT AC.AVAILABLE_AMOUNT FROM PT_BU_ACCOUNT AC WHERE AC.ORG_ID = A.ORG_ID AND AC.ACCOUNT_TYPE = 202701\n" + 
				"       ) XJ_AMOUNT," + 
				"		(\n" +
				"          SELECT AC.AVAILABLE_AMOUNT FROM PT_BU_ACCOUNT AC WHERE AC.ORG_ID = A.ORG_ID AND AC.ACCOUNT_TYPE = 202702\n" + 
				"       ) CD_AMOUNT," + 
				"		(\n" +
				"          SELECT AC.AVAILABLE_AMOUNT FROM PT_BU_ACCOUNT AC WHERE AC.ORG_ID = A.ORG_ID AND AC.ACCOUNT_TYPE = 202705\n" + 
				"       ) FL_AMOUNT," + 
				"       NVL(A.CLOSE_AMOUNT,0) CLOSE_AMOUNT,\n" + 
				"       NVL(A.AVAILABLE_AMOUNT,0) AVAILABLE_AMOUNT,\n" + 
				"       NVL(A.BALANCE_AMOUNT,0) BALANCE_AMOUNT,\n" + 
				"       NVL(A.OCCUPY_AMOUNT,0) OCCUPY_AMOUNT,\n" + 
				"NVL((SELECT SUM(NVL(F.OCCUPY_FUNDS, 0) - NVL(F.REPAY_AMOUNT, 0))\n" +
				"      FROM PT_BU_SALE_ORDER_OCCUPY_FUNDS F, PT_BU_SALE_ORDER O\n" + 
				"     WHERE F.ACCOUNT_ID = A.ACCOUNT_ID\n" + 
				"       AND F.ORDER_ID = O.ORDER_ID\n" + 
				"       AND O.ORDER_STATUS = 202206\n" + 
				"       AND F.STATUS = 100201),\n" + 
				"    0) SUM_CLOSE_ORDER_AMOUNT,\n" + 
				"NVL((SELECT SUM(NVL(F.OCCUPY_FUNDS, 0) - NVL(F.REPAY_AMOUNT, 0))\n" + 
				"      FROM PT_BU_SALE_ORDER_OCCUPY_FUNDS F, PT_BU_SALE_ORDER O\n" + 
				"     WHERE F.ACCOUNT_ID = A.ACCOUNT_ID\n" + 
				"       AND F.ORDER_ID = O.ORDER_ID\n" + 
				"       AND O.ORDER_STATUS <> 202206\n" + 
				"       AND F.STATUS = 100201),\n" + 
				"    0) SUM_OTHER_ORDER_AMOUNT" +
				"  FROM PT_BU_ACCOUNT A, TM_ORG T1, TM_ORG T2";
		return this.factory.select(sql, pageManager);
	}
	
	/**
	 * 
	 * queryInfoById:根据ID查询详情
	 * @author fuxiao
	 * Date:2014年10月29日
	 *
	 */
	public QuerySet queryInfoById(String id) throws SQLException{
		return this.factory.select(new Object[]{id}, "SELECT\n" +
				"       T2.CODE P_ORG_CODE,\n" + 
				"       T2.SNAME P_ORG_NAME,\n" + 
				"       T1.CODE ORG_CODE,\n" + 
				"       T1.SNAME ORG_NAME,\n" + 
				"		(\n" +
				"          SELECT AC.AVAILABLE_AMOUNT FROM PT_BU_ACCOUNT AC WHERE AC.ORG_ID = A.ORG_ID AND AC.ACCOUNT_TYPE = 202703\n" + 
				"       ) MATERIALS_AMOUNT," + 
				"       A.ACCOUNT_ID,\n" + 
				"       A.AVAILABLE_AMOUNT,\n" + 
				"       A.BALANCE_AMOUNT,\n" + 
				"       A.OCCUPY_AMOUNT,\n" + 
				"NVL((SELECT SUM(NVL(F.OCCUPY_FUNDS, 0) - NVL(F.REPAY_AMOUNT, 0))\n" +
				"      FROM PT_BU_SALE_ORDER_OCCUPY_FUNDS F, PT_BU_SALE_ORDER O\n" + 
				"     WHERE F.ACCOUNT_ID = A.ACCOUNT_ID\n" + 
				"       AND F.ORDER_ID = O.ORDER_ID\n" + 
				"       AND O.ORDER_STATUS = 202206\n" + 
				"       AND F.STATUS = 100201),\n" + 
				"    0) SUM_CLOSE_ORDER_AMOUNT,\n" + 
				"NVL((SELECT SUM(NVL(F.OCCUPY_FUNDS, 0) - NVL(F.REPAY_AMOUNT, 0))\n" + 
				"      FROM PT_BU_SALE_ORDER_OCCUPY_FUNDS F, PT_BU_SALE_ORDER O\n" + 
				"     WHERE F.ACCOUNT_ID = A.ACCOUNT_ID\n" + 
				"       AND F.ORDER_ID = O.ORDER_ID\n" + 
				"       AND O.ORDER_STATUS <> 202206\n" + 
				"       AND F.STATUS = 100201),\n" + 
				"    0) SUM_OTHER_ORDER_AMOUNT" +
				"  FROM PT_BU_ACCOUNT A, TM_ORG T1, TM_ORG T2 WHERE A.ACCOUNT_TYPE = 202704 AND A.ORG_CODE = T1.CODE AND T1.PID = T2.ORG_ID AND A.ACCOUNT_ID = ?");
	}
	
	/**
	 * 
	 * queryOrderInfo: 查询订单详情
	 * @author fuxiao
	 * Date:2014年10月29日
	 *
	 */
	public BaseResultSet queryOrderInfo(PageManager pageManager, String conditions) throws Exception {
		String sql = 
					"SELECT O.ORDER_NO,\n" +
					"       TO_CHAR(O.APPLY_DATE, 'YYYY-MM-DD HH24:MI:SS') APPLY_DATE,\n" + 
					"       TO_CHAR(O.CLOSE_DATE, 'YYYY-MM-DD HH24:MI:SS') CLOSE_DATE,\n" + 
					"       NVL(F.OCCUPY_FUNDS, 0) OCCUPY_FUNDS,\n" + 
					"        NVL(F.REPAY_AMOUNT, 0) REPAY_AMOUNT,\n" + 
					"       NVL(F.OCCUPY_FUNDS, 0) - NVL(F.REPAY_AMOUNT, 0) SUM_OCCUPY_FUNDS,\n" + 
					"       CASE\n" + 
					"         WHEN O.ORDER_STATUS = 202206 THEN\n" + 
					"          '已关闭'\n" + 
					"         ELSE\n" + 
					"          '未关闭'\n" + 
					"       END ORDER_STATUS\n" + 
					"  FROM PT_BU_SALE_ORDER_OCCUPY_FUNDS F, PT_BU_SALE_ORDER O\n" + 
					" WHERE F.ORDER_ID = O.ORDER_ID\n" + 
					"   AND F.STATUS = 100201 AND " + conditions + 
					" ORDER BY O.ORDER_STATUS DESC, O.CLOSE_DATE";
		return this.factory.select(sql, pageManager);
	}

	public BaseResultSet queryAccountInfoById(PageManager pageManager, String conditions,String orgId) throws Exception {
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT ACCOUNT_TYPE, AVAILABLE_AMOUNT, BALANCE_AMOUNT, OCCUPY_AMOUNT\n" );
		sql.append("  FROM PT_BU_ACCOUNT\n" );
		sql.append(" WHERE ORG_ID = "+orgId+"\n" );
		sql.append("   AND ACCOUNT_TYPE <> 202704 ORDER BY ACCOUNT_TYPE ASC");
		return this.factory.select(sql.toString(), pageManager);
	}
	/**
	 * 
	 * @Title: queryDownInfo
	 * @Description: 导出查询
	 * @param conditions
	 * @return
	 * @throws SQLException
	 * @return: QuerySet
	 */
	public QuerySet queryDownInfo(String conditions) throws SQLException {
		String sql  = 
				"SELECT O.ORDER_NO,\n" +
				"       TO_CHAR(O.APPLY_DATE, 'YYYY-MM-DD HH24:MI:SS') APPLY_DATE,\n" + 
				"       TO_CHAR(O.CLOSE_DATE, 'YYYY-MM-DD HH24:MI:SS') CLOSE_DATE,\n" + 
				"       NVL(F.OCCUPY_FUNDS, 0) OCCUPY_FUNDS,\n" + 
				"        NVL(F.REPAY_AMOUNT, 0) REPAY_AMOUNT,\n" + 
				"       NVL(F.OCCUPY_FUNDS, 0) - NVL(F.REPAY_AMOUNT, 0) SUM_OCCUPY_FUNDS,\n" + 
				"       CASE\n" + 
				"         WHEN O.ORDER_STATUS = 202206 THEN\n" + 
				"          '已关闭'\n" + 
				"         ELSE\n" + 
				"          '未关闭'\n" + 
				"       END ORDER_STATUS\n" + 
				"  FROM PT_BU_SALE_ORDER_OCCUPY_FUNDS F, PT_BU_SALE_ORDER O\n" + 
				" WHERE F.ORDER_ID = O.ORDER_ID\n" + 
				"   AND F.STATUS = 100201 AND " + conditions + 
				" ORDER BY O.ORDER_STATUS DESC, O.CLOSE_DATE";
		return this.factory.select(null, sql);

	}
}
