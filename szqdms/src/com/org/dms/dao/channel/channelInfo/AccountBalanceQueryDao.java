package com.org.dms.dao.channel.channelInfo;

import java.sql.SQLException;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 
 * ClassName: AccountBalanceQueryDao 
 * Function: 账户余额查询Dao
 * date: 2014年10月27日 上午11:33:16
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class AccountBalanceQueryDao extends BaseDAO {
	
	public static final AccountBalanceQueryDao getInstance(ActionContext ac){
		AccountBalanceQueryDao dao = new AccountBalanceQueryDao();
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
	public BaseResultSet queryList(PageManager pageManager, String conditions, User user) throws Exception{
		BaseResultSet rs = null;
		pageManager.setFilter(conditions + " AND A.STATUS = 100201 AND A.ORG_ID = '" + user.getOrgId() + "' ORDER BY A.ACCOUNT_ID ");
		String sql = 
						"SELECT\n" +
						"       A.ACCOUNT_ID,\n" + 
						"       A.ACCOUNT_TYPE,\n" + 
						"       A.AVAILABLE_AMOUNT,\n" + 
						"       A.BALANCE_AMOUNT,\n" + 
						"       A.OCCUPY_AMOUNT,\n" + 
						"       NVL(A.CLOSE_AMOUNT, 0) CLOSE_AMOUNT,\n" + 
						"       NVL(A.PAY_CLOSE_AMOUNT, 0) PAY_CLOSE_AMOUNT,\n" + 
						"       A.ORG_CODE,\n" + 
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
						" FROM PT_BU_ACCOUNT A";
		rs = this.factory.select(sql, pageManager);
		rs.setFieldDic("ACCOUNT_TYPE", "ZJZHLX");
		return rs;
	}
	
	/**
	 * 
	 * queryDetailsList:详情
	 * @author fuxiao
	 * Date:2014年10月27日
	 *
	 */
	public BaseResultSet queryDetailsList(PageManager pageManager, String conditions, User user) throws Exception{
		String sql = 
					"SELECT O.ORDER_NO,\n" +
					"       (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = O.ORDER_TYPE ) ORDER_TYPE,\n" + 
					"       TO_CHAR(O.APPLY_DATE, 'YYYY-MM-DD HH24:MI:SS') APPLY_DATE,\n" + 
					"       TO_CHAR(O.CLOSE_DATE, 'YYYY-MM-DD HH24:MI:SS') CLOSE_DATE,\n" + 
					"       NVL(F.OCCUPY_FUNDS, 0) OCCUPY_FUNDS,\n" + 
					"       NVL(F.REPAY_AMOUNT, 0) REPAY_AMOUNT,\n" + 
					"       NVL(F.OCCUPY_FUNDS, 0) - NVL(F.REPAY_AMOUNT, 0) SUM_OCCUPY_FUNDS,\n" + 
					"       CASE\n" + 
					"         WHEN O.ORDER_STATUS = 202206 THEN\n" + 
					"          '已关闭'\n" + 
					"         ELSE\n" + 
					"          '未关闭'\n" + 
					"       END ORDER_STATUS\n" + 
					"  FROM PT_BU_SALE_ORDER_OCCUPY_FUNDS F, PT_BU_SALE_ORDER O\n" + 
					" WHERE O.ORDER_ID = F.ORDER_ID\n" + 
					"   AND F.STATUS = 100201 AND " + conditions + 
					" ORDER BY O.ORDER_STATUS DESC, O.CLOSE_DATE";
		return  this.factory.select(sql, pageManager);
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
		String sql = 
				"SELECT O.ORDER_NO,\n" +
				"       (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = O.ORDER_TYPE ) ORDER_TYPE,\n" + 
				"       TO_CHAR(O.APPLY_DATE, 'YYYY-MM-DD HH24:MI:SS') APPLY_DATE,\n" + 
				"       TO_CHAR(O.CLOSE_DATE, 'YYYY-MM-DD HH24:MI:SS') CLOSE_DATE,\n" + 
				"       NVL(F.OCCUPY_FUNDS, 0) OCCUPY_FUNDS,\n" + 
				"       NVL(F.REPAY_AMOUNT, 0) REPAY_AMOUNT,\n" + 
				"       NVL(F.OCCUPY_FUNDS, 0) - NVL(F.REPAY_AMOUNT, 0) SUM_OCCUPY_FUNDS,\n" + 
				"       CASE\n" + 
				"         WHEN O.ORDER_STATUS = 202206 THEN\n" + 
				"          '已关闭'\n" + 
				"         ELSE\n" + 
				"          '未关闭'\n" + 
				"       END ORDER_STATUS\n" + 
				"  FROM PT_BU_SALE_ORDER_OCCUPY_FUNDS F, PT_BU_SALE_ORDER O\n" + 
				" WHERE O.ORDER_ID = F.ORDER_ID\n" + 
				"   AND F.STATUS = 100201 AND " + conditions + 
				" ORDER BY O.ORDER_STATUS DESC, O.CLOSE_DATE";
		return this.factory.select(null, sql);

	}
}
