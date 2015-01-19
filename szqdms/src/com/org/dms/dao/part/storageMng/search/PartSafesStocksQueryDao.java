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
 * @ClassName: PartSafesStocksQueryDao   
 * @Function: 车厂配件上下限查询 
 * @date: 2014年10月23日 上午10:45:59 
 * @author fuxiao  * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class PartSafesStocksQueryDao extends BaseDAO {

	public static final PartSafesStocksQueryDao getInstance(ActionContext ac) {
		PartSafesStocksQueryDao dao = new PartSafesStocksQueryDao();
		ac.setDBFactory(dao.factory);
		return dao;
	}

	/**
	 * 
	  * queryList: 查询表单查询  * @author fuxiao Date:2014年10月23日
	 *
	 */
	public BaseResultSet queryList(PageManager pageManager, String conditions,
			User user) throws Exception {
		pageManager.setFilter(conditions);

		String sql = "SELECT PART_CODE,PART_NAME,STOCK_CODE,STOCK_NAME,UNIT,MIN_PACK||'/'||MIN_UNIT MM,UPPER_LIMIT,LOWER_LIMIT FROM (\n"
				+ "  SELECT I.PART_CODE,\n"
				+ "         I.PART_NAME,\n"
				+ "         S.STOCK_CODE,\n"
				+ "         S.STOCK_NAME,\n"
				+ "         (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = I.UNIT) UNIT,\n"
				+ "         I.MIN_PACK,\n"
				+ "         (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = I.MIN_UNIT) MIN_UNIT,\n"
				+ "         NVL(S.UPPER_LIMIT,0) UPPER_LIMIT,\n"
				+ "         NVL(S.LOWER_LIMIT,0) LOWER_LIMIT\n"
				+ "    FROM PT_BA_STOCK_SAFESTOCKS S,\n"
				+ "         PT_BA_INFO I,\n"
				+ "         PT_BA_WAREHOUSE W\n"
				+ "   WHERE S.PART_ID = I.PART_ID\n"
				+ "         AND S.STATUS = '100201'\n"
				+ "         AND I.STATUS = '100201'\n"
				+ "         AND W.WAREHOUSE_ID = S.STOCK_ID\n";
		// 判断用户是否为军品用户
		if (this.checkUserIsAM(user)) {
			sql += " AND W.WAREHOUSE_TYPE = 100102 ";
		} else {
			sql += " AND W.WAREHOUSE_TYPE <> 100102 ";
		}
		sql += "    ORDER BY I.PART_ID\n)";
		return this.factory.select(sql, pageManager);
	}

	/**
	 * 对查询条件进行转换
	 * 
	 * @param conditions
	 * @return
	 */
	public String convertStr(String conditions) {
		if (conditions.indexOf("AVAILABLE_AMOUNT") != -1) {
			String tempSql = conditions.substring(
					conditions.indexOf("AVAILABLE_AMOUNT"),
					conditions.indexOf("'",
							conditions.indexOf("AVAILABLE_AMOUNT") + 20) + 1);
			conditions = conditions.replace(" AND  " + tempSql, "");
			if (tempSql.indexOf("&gt;") != -1) {
				conditions += " AND AVAILABLE_AMOUNT > UPPER_LIMIT ";
			} else if (tempSql.indexOf("&lt;") != -1) {
				conditions += " AND AVAILABLE_AMOUNT < LOWER_LIMIT ";
			}
		}
		return conditions;
	}

	public BaseResultSet queryList(PageManager pageManager, String conditions,
			User user, boolean isQueryArea) throws Exception {
		conditions = this.convertStr(conditions);
		pageManager.setFilter(conditions);
		String sql = "SELECT PART_CODE,PART_NAME,STOCK_CODE,STOCK_NAME,AREA_CODE,AREA_NAME,UNIT,MIN_PACK||'/'||MIN_UNIT MM,UPPER_LIMIT,LOWER_LIMIT,AVAILABLE_AMOUNT,USER_NAME,USER_ACCOUNT FROM (\n"
				+ "SELECT I.PART_CODE,\n"
				+ "       I.PART_NAME,\n"
				+ "       S.STOCK_CODE,\n"
				+ "       S.STOCK_NAME,\n"
				+ "       (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = I.UNIT) UNIT,\n"
				+ "       I.MIN_PACK,\n"
				+ "       (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = I.MIN_UNIT) MIN_UNIT,\n"
				+ "       NVL(S.UPPER_LIMIT,0) UPPER_LIMIT,\n"
				+ "       NVL(S.LOWER_LIMIT,0) LOWER_LIMIT,\n"
				+ "       WA.AREA_CODE,\n"
				+ "       WA.AREA_NAME,\n"
				+ "       SD.AVAILABLE_AMOUNT,\n"
				+ "       PA.USER_NAME,\n"
				+ "       PA.USER_ACCOUNT\n"
				+ "  FROM PT_BA_STOCK_SAFESTOCKS S,\n"
				+ "       PT_BA_INFO I,\n"
				+ "       PT_BA_WAREHOUSE W,\n"
				+ "       PT_BA_WAREHOUSE_AREA WA,\n"
				+ "       PT_BU_STOCK_DTL SD,\n"
				+ "       PT_BA_PCH_ATTRIBUTE    PA\n"
				+ " WHERE S.PART_ID = I.PART_ID\n"
				+ "       AND W.WAREHOUSE_ID = S.STOCK_ID\n"
				+ "       AND W.WAREHOUSE_ID = WA.WAREHOUSE_ID\n"
				+ "       AND I.PART_ID = PA.PART_ID(+)\n"
				+ "       AND SD.PART_ID = S.PART_ID\n"
				+ "       AND SD.AREA_ID = WA.AREA_ID\n"
				+ "       AND S.STATUS = '100201'\n"
				+ "       AND I.STATUS = '100201'";
		// 判断用户是否为军品用户
		if (this.checkUserIsAM(user)) {
			sql += " AND W.WAREHOUSE_TYPE = 100102 ";
		} else {
			sql += " AND W.WAREHOUSE_TYPE <> 100102 ";
		}
		sql += "    ORDER BY I.PART_ID\n)";
		return this.factory.select(sql, pageManager);
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
	  * queryDownInfo: 查询需要下载的数据  * @author fuxiao Date:2014年10月23日
	 *
	 */
	public QuerySet queryDownInfo(String conditions, User user)
			throws Exception {
		conditions = this.convertStr(conditions);
		String sql = "SELECT PART_CODE,PART_NAME,STOCK_CODE,STOCK_NAME,AREA_CODE,AREA_NAME,UNIT,MIN_PACK||'/'||MIN_UNIT MM,UPPER_LIMIT,LOWER_LIMIT,AVAILABLE_AMOUNT,USER_NAME,USER_ACCOUNT FROM (\n"
				+ "SELECT I.PART_CODE,\n"
				+ "       I.PART_NAME,\n"
				+ "       S.STOCK_CODE,\n"
				+ "       S.STOCK_NAME,\n"
				+ "       (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = I.UNIT) UNIT,\n"
				+ "       I.MIN_PACK,\n"
				+ "       (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = I.MIN_UNIT) MIN_UNIT,\n"
				+ "       NVL(S.UPPER_LIMIT,0) UPPER_LIMIT,\n"
				+ "       NVL(S.LOWER_LIMIT,0) LOWER_LIMIT,\n"
				+ "       WA.AREA_CODE,\n"
				+ "       WA.AREA_NAME,\n"
				+ "       SD.AVAILABLE_AMOUNT,\n"
				+ "       PA.USER_NAME,\n"
				+ "       PA.USER_ACCOUNT\n"
				+ "  FROM PT_BA_STOCK_SAFESTOCKS S,\n"
				+ "       PT_BA_INFO I,\n"
				+ "       PT_BA_WAREHOUSE W,\n"
				+ "       PT_BA_WAREHOUSE_AREA WA,\n"
				+ "       PT_BU_STOCK_DTL SD,\n"
				+ "       PT_BA_PCH_ATTRIBUTE    PA\n"
				+ " WHERE S.PART_ID = I.PART_ID\n"
				+ "       AND W.WAREHOUSE_ID = S.STOCK_ID\n"
				+ "       AND W.WAREHOUSE_ID = WA.WAREHOUSE_ID\n"
				+ "       AND I.PART_ID = PA.PART_ID(+)\n"
				+ "       AND SD.PART_ID = S.PART_ID\n"
				+ "       AND SD.AREA_ID = WA.AREA_ID\n"
				+ "       AND S.STATUS = '100201'\n"
				+ "       AND I.STATUS = '100201'";
		// 判断用户是否为军品用户
		if (this.checkUserIsAM(user)) {
			sql += " AND W.WAREHOUSE_TYPE = 100102 ";
		} else {
			sql += " AND W.WAREHOUSE_TYPE <> 100102 ";
		}
		sql += "    ORDER BY I.PART_ID\n)";
		return this.factory.select(null, sql + " WHERE " + conditions);
	}
}
