package com.org.dms.dao.part.basicInfoMng;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 
 * @ClassName: PartPriceChangeDao
 * @Description: 配件调价记录查询Dao
 * @author: fuxiao
 * @date: 2014年12月1日 下午11:08:29
 */
public class PartPriceChangeDao extends BaseDAO {

	public static final PartPriceChangeDao getInstance(ActionContext ac) {
		PartPriceChangeDao dao = new PartPriceChangeDao();
		ac.setDBFactory(dao.factory);
		return dao;
	}

	/**
	 * 对查询条件进行转换
	 * 
	 * @param conditions
	 * @return
	 */
	public String convertStr(String conditions) {
		if (conditions.indexOf("PRICE_CHANGE") != -1) {
			String tempSql = conditions.substring(conditions
					.indexOf("PRICE_CHANGE"), conditions.indexOf("'",
					conditions.indexOf("PRICE_CHANGE") + 15) + 1);
			conditions = conditions.replace(" AND  " + tempSql, "");
			if (tempSql.indexOf("3") != -1) {
				conditions += " AND A.NOW_PRICE > A.ORIGINAL_PRICE ";
			} else if (tempSql.indexOf("2") != -1) {
				conditions += " AND A.NOW_PRICE < A.ORIGINAL_PRICE ";
			}
		}
		return conditions;
	}

	/**
	 * 
	 * @Title: queryList
	 * @Description: 表格内容查询
	 * @param pageManager
	 * @param conditions
	 * @param user
	 * @param isQueryArea
	 * @return
	 * @throws Exception
	 * @return: BaseResultSet
	 */
	public BaseResultSet queryList(PageManager pageManager, String conditions,
			User user, boolean isQueryArea) throws Exception {
		BaseResultSet rs = null;
		conditions = this.convertStr(conditions);
		pageManager.setFilter(conditions + " ORDER BY A.LOG_ID");
		String sql = "SELECT A.LOG_ID,\n"
				+ "       A.PART_ID,\n"
				+ "       A.PART_CODE,\n"
				+ "       A.PART_NAME,\n"
				+ "       A.NOW_PRICE,\n"
				+ "       A.ORIGINAL_PRICE,\n"
				+ "       ABS(NVL(A.NOW_PRICE, 0) - NVL(ORIGINAL_PRICE, 0)) AS JGCY,\n"
				+ "       A.PRICE_TYPE,\n"
				+ "       A.CREATE_USER,\n"
				+ "       A.CREATE_TIME,\n"
				+ "       A.WAREHOUSE_CODE,\n"
				+ "       (SELECT W.WAREHOUSE_NAME\n"
				+ "          FROM PT_BA_WAREHOUSE W\n"
				+ "         WHERE W.WAREHOUSE_CODE = A.WAREHOUSE_CODE) WAREHOUSE_NAME,\n"
				+ "       NVL(A.USABLE_STOCK, 0) USABLE_STOCK,\n"
				+ "       NVL(A.IN_STRANS_STOCK, 0) IN_STRANS_STOCK,\n"
				+ "       ABS(NVL(A.NOW_PRICE, 0) - NVL(ORIGINAL_PRICE, 0)) *\n"
				+ "       (NVL(A.USABLE_STOCK, 0) + NVL(A.IN_STRANS_STOCK, 0)) CYJE\n"
				+ "  FROM PT_BA_PRICE_LOG A";
		rs = this.factory.select(sql, pageManager);
		rs.setFieldDic("PRICE_TYPE", "PJJGLX"); // 价格调整类型
		rs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
		return rs;
	}

	// 调价轨迹报表查询
	public QuerySet searchPriceReportSum(String conditions) throws Exception {
		// 对查询条件转换
		conditions = convertStr(conditions);
		String sql = "SELECT SUM(CASE\n"
				+ "             WHEN NVL(A.NOW_PRICE, 0) - NVL(A.ORIGINAL_PRICE, 0) > 0 THEN\n"
				+ "              (NVL(A.NOW_PRICE, 0) - NVL(A.ORIGINAL_PRICE, 0))\n"
				+ "             ELSE\n"
				+ "              0\n"
				+ "           END * (NVL(A.IN_STRANS_STOCK, 0) + NVL(A.USABLE_STOCK, 0))) -\n"
				+ "       SUM(CASE\n"
				+ "             WHEN NVL(A.NOW_PRICE, 0) - NVL(A.ORIGINAL_PRICE, 0) < 0 THEN\n"
				+ "              (NVL(A.ORIGINAL_PRICE, 0) - NVL(A.NOW_PRICE, 0))\n"
				+ "             ELSE\n"
				+ "              0\n"
				+ "           END * (NVL(A.IN_STRANS_STOCK, 0) + NVL(A.USABLE_STOCK, 0))) AS CYJEZH \n"
				+ "  FROM PT_BA_PRICE_LOG A WHERE ";
		return factory.select(null, sql + conditions);
	}

	/**
	 * 
	 * @Title: queryDownInfo
	 * @Description: 下载查询
	 * @param conditions
	 * @param user
	 * @return
	 * @throws Exception
	 * @return: QuerySet
	 */
	public QuerySet queryDownInfo(String conditions, User user)
			throws Exception {
		conditions = this.convertStr(conditions);
		String sql = "SELECT A.LOG_ID,\n"
				+ "       A.PART_ID,\n"
				+ "       A.PART_CODE,\n"
				+ "       A.PART_NAME,\n"
				+ "       A.NOW_PRICE,\n"
				+ "       A.ORIGINAL_PRICE,\n"
				+ "       ABS(NVL(A.NOW_PRICE, 0) - NVL(ORIGINAL_PRICE, 0)) AS JGCY,\n"
				+ "       (SELECT DIC_VALUE FROM DIC_TREE T WHERE T.ID = A.PRICE_TYPE ) PRICE_TYPE,\n"
				+ "       A.CREATE_USER,\n"
				+ "       A.CREATE_TIME,\n"
				+ "       A.WAREHOUSE_CODE,\n"
				+ "       (SELECT W.WAREHOUSE_NAME\n"
				+ "          FROM PT_BA_WAREHOUSE W\n"
				+ "         WHERE W.WAREHOUSE_CODE = A.WAREHOUSE_CODE) WAREHOUSE_NAME,\n"
				+ "       NVL(A.USABLE_STOCK, 0) USABLE_STOCK,\n"
				+ "       NVL(A.IN_STRANS_STOCK, 0) IN_STRANS_STOCK,\n"
				+ "       ABS(NVL(A.NOW_PRICE, 0) - NVL(ORIGINAL_PRICE, 0)) *\n"
				+ "       (NVL(A.USABLE_STOCK, 0) + NVL(A.IN_STRANS_STOCK, 0)) CYJE\n"
				+ "  FROM PT_BA_PRICE_LOG A";
		return this.factory.select(null, sql + " WHERE " + conditions
				+ " ORDER BY A.LOG_ID");
	}
}
