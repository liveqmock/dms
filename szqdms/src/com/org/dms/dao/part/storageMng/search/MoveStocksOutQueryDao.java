package com.org.dms.dao.part.storageMng.search;

import java.sql.SQLException;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 
  * ClassName: MoveStocksOutQueryDao   * Function: 移库查询
 *  * date: 2014年11月18日 下午10:17:03  * @author fuxiao  * @version 1.0.0
 *  * @since JDK 1.6
 *
 */
public class MoveStocksOutQueryDao extends BaseDAO {

	public static final MoveStocksOutQueryDao getInstance(ActionContext ac) {
		MoveStocksOutQueryDao dao = new MoveStocksOutQueryDao();
		ac.setDBFactory(dao.factory);
		return dao;
	}

	/**
	 * 订单查询(导出)
	 *
	 * @pConditions 查询条件
	 * @return QuerySet 结果集
	 * @throws Exception
	 */
	public QuerySet download(String conditions, User user) throws Exception {

		if (conditions.indexOf("PART_CODE") != -1
				|| conditions.indexOf("PART_NAME") != -1) {
			conditions += " AND EXISTS (\n"
					+ "    SELECT 1 FROM PT_BU_STOCK_OUT_DTL D WHERE D.OUT_ID = O.OUT_ID";
			if (conditions.indexOf("PART_NAME") != -1) {
				String partNameSql = conditions.substring(conditions
						.indexOf("PART_NAME"), conditions.indexOf("'",
						conditions.indexOf("PART_NAME") + 15) + 1);
				conditions = conditions.replace(" AND " + partNameSql, "");
				conditions += " AND " + partNameSql;
			}
			if (conditions.indexOf("PART_CODE") != -1) {
				String partNameSql = conditions.substring(conditions
						.indexOf("PART_CODE"), conditions.indexOf("'",
						conditions.indexOf("PART_CODE") + 15) + 1);
				conditions = conditions.replace(" AND " + partNameSql, "");
				conditions += " AND " + partNameSql;
			}
			conditions += " ) ";
		}

		if (conditions.indexOf("IN_WAREHOUSE_CODE") != -1) {
			conditions += " AND EXISTS (\n"
					+ "    SELECT 1 FROM PT_BA_WAREHOUSE W  WHERE W.WAREHOUSE_ID = O.RECEIVE_WAREHOUSE";
			String partNameSql = conditions.substring(
					conditions.indexOf("IN_WAREHOUSE_CODE"),
					conditions.indexOf("'",
							conditions.indexOf("IN_WAREHOUSE_CODE") + 23) + 1);
			conditions = conditions.replace(" AND " + partNameSql, "");
			conditions += " AND WAREHOUSE_CODE "
					+ partNameSql.substring("IN_WAREHOUSE_CODE".length());
			conditions += " ) ";
		}

		if (conditions.indexOf("IN_WAREHOUSE_NAME") != -1) {
			conditions += " AND EXISTS (\n"
					+ "    SELECT 1 FROM PT_BA_WAREHOUSE W  WHERE W.WAREHOUSE_ID = O.RECEIVE_WAREHOUSE";
			String partNameSql = conditions.substring(
					conditions.indexOf("IN_WAREHOUSE_NAME"),
					conditions.indexOf("'",
							conditions.indexOf("IN_WAREHOUSE_NAME") + 23) + 1);
			conditions = conditions.replace(" AND " + partNameSql, "");
			conditions += " AND WAREHOUSE_NAME "
					+ partNameSql.substring("IN_WAREHOUSE_NAME".length());
			conditions += " ) ";
		}

		String sql = "SELECT OUT_ID,\n"
				+ "       OUT_NO,\n"
				+ "       O.WAREHOUSE_ID OUT_WAREHOUSE_ID,\n"
				+ "       O.WAREHOUSE_CODE OUT_WAREHOUSE_CODE,\n"
				+ "       O.Warehouse_Name OUT_WAREHOUSE_NAME,\n"
				+ "       OUT_DATE,\n"
				+ "       O.RECEIVE_WAREHOUSE IN_WAREHOUSE_ID,\n"
				+ "       (SELECT W.WAREHOUSE_CODE FROM PT_BA_WAREHOUSE W WHERE W.WAREHOUSE_ID = O.RECEIVE_WAREHOUSE) IN_WAREHOUSE_CODE,\n"
				+ "       (SELECT W.WAREHOUSE_NAME FROM PT_BA_WAREHOUSE W WHERE W.WAREHOUSE_ID = O.RECEIVE_WAREHOUSE) IN_WAREHOUSE_NAME,\n"
				+ "       (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = PRINT_STATUS) PRINT_STATUS,\n"
				+ "       PRINT_DATE,\n"
				+ "       OUT_STATUS,\n"
				+ "       (SELECT PERSON_NAME FROM TM_USER WHERE ACCOUNT=O.PRINT_MAN) PRINT_MAN,\n"
				+ "       O.STATUS,\n"
				+ "       (SELECT SUM(A.PLAN_AMOUNT) FROM PT_BU_STOCK_OUT_DTL A WHERE A.OUT_ID=O.OUT_ID) PLAN_AMOUNT,\n"
				+ "       O.MOVE_MAN\n" + "  FROM PT_BU_STOCK_OUT O \n"
				+ "  WHERE O.OUT_STATUS = 201602\n"
				+ "    AND O.STATUS = 100201\n"
				+ "    AND O.OUT_TYPE ='201403'\n";

		// 判断用户是否为库管员
		if (this.isKeeper(user)) {
			sql += "    AND (\n"
					+ "        EXISTS (\n"
					+ "           SELECT 1 FROM PT_BA_WAREHOUSE_KEEPER K WHERE K.WAREHOUSE_ID = O.WAREHOUSE_ID AND K.USER_ACCOUNT = '"
					+ user.getAccount()
					+ "'\n"
					+ "        ) OR EXISTS (\n"
					+ "           SELECT 1 FROM PT_BA_WAREHOUSE_KEEPER K WHERE K.WAREHOUSE_ID = O.RECEIVE_WAREHOUSE AND K.USER_ACCOUNT = '"
					+ user.getAccount() + "'\n" + "        )\n" + "    )";
		}

		sql += " 	AND " + conditions;

		return factory.select(null, sql);
	}

	/**
	 * 
	 * @Title: isKeeper
	 * @Description: 判断用户是否为库管员
	 * @param user
	 * @return true 库管员 false 非库管员
	 * @throws Exception
	 * @return: boolean
	 */
	public boolean isKeeper(User user) throws Exception {
		return Integer
				.parseInt(this.factory
						.select("SELECT COUNT(1) FROM PT_BA_WAREHOUSE_KEEPER K WHERE K.USER_ACCOUNT = ?",
								new Object[] { user.getAccount() })[0][0]) > 0;
	}

	/**
	 * 
	  * queryList: 查询表单查询  * @author fuxiao Date:2014年10月23日
	 *
	 */
	public BaseResultSet queryList(PageManager pageManager, String conditions,
			User user) throws Exception {
		BaseResultSet rs = null;
		String sql = "SELECT OUT_ID,\n"
				+ "       OUT_NO,\n"
				+ "       O.WAREHOUSE_ID OUT_WAREHOUSE_ID,\n"
				+ "       O.WAREHOUSE_CODE OUT_WAREHOUSE_CODE,\n"
				+ "       O.Warehouse_Name OUT_WAREHOUSE_NAME,\n"
				+ "       OUT_DATE,\n"
				+ "       O.RECEIVE_WAREHOUSE IN_WAREHOUSE_ID,\n"
				+ "       (SELECT W.WAREHOUSE_CODE FROM PT_BA_WAREHOUSE W WHERE W.WAREHOUSE_ID = O.RECEIVE_WAREHOUSE) IN_WAREHOUSE_CODE,\n"
				+ "       (SELECT W.WAREHOUSE_NAME FROM PT_BA_WAREHOUSE W WHERE W.WAREHOUSE_ID = O.RECEIVE_WAREHOUSE) IN_WAREHOUSE_NAME,\n"
				+ "       PRINT_STATUS,\n"
				+ "       PRINT_DATE,\n"
				+ "       OUT_STATUS,\n"
				+ "       O.PRINT_MAN,\n"
				+ "       O.STATUS,\n"
				+ "       (SELECT SUM(A.PLAN_AMOUNT) FROM PT_BU_STOCK_OUT_DTL A WHERE A.OUT_ID=O.OUT_ID) PLAN_AMOUNT,\n"
				+ "       O.MOVE_MAN\n" + "  FROM PT_BU_STOCK_OUT O \n"
				+ "  WHERE O.OUT_STATUS = 201602\n"
				+ "    AND O.STATUS = 100201\n"
				+ "    AND O.OUT_TYPE ='201403'\n";

		// 判断用户是否为库管员
		if (this.isKeeper(user)) {
			sql += "    AND (\n"
					+ "        EXISTS (\n"
					+ "           SELECT 1 FROM PT_BA_WAREHOUSE_KEEPER K WHERE K.WAREHOUSE_ID = O.WAREHOUSE_ID AND K.USER_ACCOUNT = '"
					+ user.getAccount()
					+ "'\n"
					+ "        ) OR EXISTS (\n"
					+ "           SELECT 1 FROM PT_BA_WAREHOUSE_KEEPER K WHERE K.WAREHOUSE_ID = O.RECEIVE_WAREHOUSE AND K.USER_ACCOUNT = '"
					+ user.getAccount() + "'\n" + "        )\n" + "    )";
		}
		sql += " 	AND " + conditions;

		rs = this.factory.select(sql, pageManager);
		rs.setFieldDic("OUT_TYPE", "CKLX"); // 出库类型
		rs.setFieldDic("PRINT_STATUS", "DYZT"); // 是否打印
		rs.setFieldDateFormat("OUT_DATE", "yyyy-MM-dd HH:mm:ss");
		rs.setFieldDateFormat("PRINT_DATE", "yyyy-MM-dd HH:mm:ss");
		rs.setFieldUserID("PRINT_MAN");
		return rs;
	}


	/**
	 * 
	 * @Title: queryList
	 * @Description: 表单查询
	 * @param pageManager
	 * @param conditions
	 * @param user
	 * @param isSub
	 * @return
	 * @throws Exception
	 * @return: BaseResultSet
	 */
	public BaseResultSet queryList(PageManager pageManager, String conditions,
			User user, boolean isSub) throws Exception {

		if (conditions.indexOf("PART_CODE") != -1
				|| conditions.indexOf("PART_NAME") != -1) {
			conditions += " AND EXISTS (\n"
					+ "    SELECT 1 FROM PT_BU_STOCK_OUT_DTL D WHERE D.OUT_ID = O.OUT_ID";
			if (conditions.indexOf("PART_NAME") != -1) {
				String partNameSql = conditions.substring(conditions
						.indexOf("PART_NAME"), conditions.indexOf("'",
						conditions.indexOf("PART_NAME") + 15) + 1);
				conditions = conditions.replace(" AND " + partNameSql, "");
				conditions += " AND " + partNameSql;
			}
			if (conditions.indexOf("PART_CODE") != -1) {
				String partNameSql = conditions.substring(conditions
						.indexOf("PART_CODE"), conditions.indexOf("'",
						conditions.indexOf("PART_CODE") + 15) + 1);
				conditions = conditions.replace(" AND " + partNameSql, "");
				conditions += " AND " + partNameSql;
			}
			conditions += " ) ";
		}

		if (conditions.indexOf("IN_WAREHOUSE_CODE") != -1) {
			conditions += " AND EXISTS (\n"
					+ "    SELECT 1 FROM PT_BA_WAREHOUSE W  WHERE W.WAREHOUSE_ID = O.RECEIVE_WAREHOUSE";
			String partNameSql = conditions.substring(
					conditions.indexOf("IN_WAREHOUSE_CODE"),
					conditions.indexOf("'",
							conditions.indexOf("IN_WAREHOUSE_CODE") + 23) + 1);
			conditions = conditions.replace("AND  " + partNameSql, "");
			conditions += " AND WAREHOUSE_CODE "
					+ partNameSql.substring("IN_WAREHOUSE_CODE".length());
			conditions += " ) ";
		}

		return this.queryList(pageManager, conditions, user);
	}

	/**
	 * 
	  * checkUserIsAM: 判断用户的org是否属于军品，true：是，false: 不是  * @author fuxiao
	 * Date:2014年11月14日
	 *
	 */
	public boolean checkUserIsAM(User user) throws SQLException {
		String res = this.factory.select("SELECT F_IS_AM(" + user.getOrgId()
				+ ") FROM DUAL")[0][0];
		return res.equals("1");
	}

	/**
	 * 
	  * queryStockInfoById:根据outId查询出单主信息  * @author fuxiao Date:2014年10月23日
	 *
	 */
	public QuerySet queryMoveStockInfoById(String outId) throws SQLException {
		String sql = "SELECT\n"
				+ "    OUT_ID, OUT_NO,\n"
				+ "    O.WAREHOUSE_ID OUT_WAREHOUSE_ID,\n"
				+ "    O.WAREHOUSE_CODE OUT_WAREHOUSE_CODE,\n"
				+ "    O.Warehouse_Name OUT_WAREHOUSE_NAME,\n"
				+ "    TO_CHAR(OUT_DATE, 'YYYY-MM-DD HH24:MI:SS') OUT_DATE,\n"
				+ "    O.RECEIVE_WAREHOUSE IN_WAREHOUSE_ID,\n"
				+ "    (SELECT W.WAREHOUSE_CODE FROM PT_BA_WAREHOUSE W WHERE W.WAREHOUSE_ID = O.RECEIVE_WAREHOUSE) IN_WAREHOUSE_CODE,\n"
				+ "    (SELECT W.WAREHOUSE_NAME FROM PT_BA_WAREHOUSE W WHERE W.WAREHOUSE_ID = O.RECEIVE_WAREHOUSE) IN_WAREHOUSE_NAME,\n"
				+ "    (SELECT T.DIC_VALUE FROM DIC_TREE T WHERE T.ID = PRINT_STATUS) PRINT_STATUS,\n"
				+ "    TO_CHAR(PRINT_DATE, 'YYYY-MM-DD HH24:MI:SS') PRINT_DATE,\n"
				+ "    (SELECT U.PERSON_NAME FROM TM_USER U WHERE U.ACCOUNT = PRINT_MAN) PRINT_MAN,\n"
				+ "    REMARKS,\n" + "    MOVE_MAN,\n"
				+ "    TO_CHAR(MOVE_DATE, 'YYYY-MM-DD HH24:MI:SS') MOVE_DATE\n"
				+ " FROM PT_BU_STOCK_OUT O\n" + "WHERE O.OUT_ID = ?";
		return this.factory.select(new Object[] { outId }, sql);
	}

	/**
	 * 
	  * queryStockInfoById:查询出库单详细信息  * @author fuxiao Date:2014年10月23日
	 *
	 */
	public BaseResultSet queryStockInfoDetailsById(PageManager pageManage,
			String conditions) throws Exception {
		pageManage
				.setFilter("T.OUT_ID = T1.OUT_ID AND T.PART_ID = I.PART_ID AND "
						+ conditions + "ORDER BY T.PART_ID");
		String sql = "  SELECT T.OUT_ID, I.PART_CODE, I.PART_NAME, I.PLAN_PRICE, (I.PLAN_PRICE * T.OUT_AMOUNT) PLAN_AMOUNT, T.OUT_AMOUNT,T.REMARKS,\n"
				+ "          (SELECT P.POSITION_NAME FROM PT_BA_WAREHOUSE_POSITION P WHERE P.POSITION_ID = T.POSITION_ID) POSITION_NAME"
				+ "    FROM PT_BU_STOCK_OUT_DTL T,PT_BU_STOCK_OUT T1, PT_BA_INFO I \n";
		return this.factory.select(sql, pageManage);

	}

}
