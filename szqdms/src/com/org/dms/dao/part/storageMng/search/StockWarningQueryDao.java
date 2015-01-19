package com.org.dms.dao.part.storageMng.search;

import java.sql.Connection;
import java.sql.SQLException;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 
 * @ClassName: StockWarningQueryDao
 * @Description: 库存安全查询
 * @author: fuxiao
 * @date: 2014年12月2日 下午9:02:56
 */
public class StockWarningQueryDao extends BaseDAO {

	public static final StockWarningQueryDao getInstance(ActionContext ac) {
		StockWarningQueryDao dao = new StockWarningQueryDao();
		ac.setDBFactory(dao.factory);
		return dao;
	}
	
	// 获取连接
	public Connection getConn(){
		return super.factory.getConnection();
	}
	
	/**
	 * 
	 * @Title: checIfPchUser
	 * @Description: 检查用户是否数据采购员
	 * @param userAccount
	 * @return
	 * @throws Exception
	 * @return: boolean
	 */
	public boolean checIfPchUser(String userAccount) throws Exception{
		return Integer.parseInt(this.factory.select("SELECT COUNT(1) FROM PT_BA_PCH_ATTRIBUTE T WHERE T.USER_ACCOUNT = ?", new Object[]{userAccount})[0][0]) > 0;
	}

	/**
	 * 对查询条件进行转换
	 * 
	 * @param conditions
	 * @return
	 */
	public String convertStr(String conditions) {

		String tempSql = "";

		// 配件上下限
		if (conditions.indexOf("AVAILABLE_AMOUNT") != -1) {
			tempSql = conditions.substring(
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

	/**
	 * 
	 * @Title: checkStockType
	 * @Description: 根据前台转来的stockcode添加订单的过滤限制
	 * @param conditions
	 * @return
	 * @return: String
	 */
	public String checkStockType(String warehouseCode) {
		String tempStr = "";
		// 本部库查询出军品订单外的搜友订单,军品仓库与之相反
		if (warehouseCode.equals("K000001")) {
			tempStr = " AND S.order_type <> 203204 ";
		} else {
			tempStr = " AND S.order_type = 203204 ";
		}
		return tempStr;
	}

	/**
	 * 
	 * @Title: queryList
	 * @Description: 需要对查询进行过滤
	 * @param pageManager
	 * @param conditions
	 * @param user
	 * @param isQueryArea
	 * @return
	 * @throws Exception
	 * @return: BaseResultSet
	 */
	public BaseResultSet queryList(PageManager pageManager, String conditions,
			User user, boolean isQueryArea, String warehouseCode) throws Exception {
		String tempStr = this.checkStockType(warehouseCode);
		conditions = this.convertStr(conditions);
		String sql = 
				"SELECT I.PART_ID,\n" +
						"       I.PART_CODE,\n" + 
						"       I.PART_NAME,\n" + 
						"       NVL(PCH_COUNT, 0) PCH_COUNT,\n" + 
						"       NVL(AVAILABLE_AMOUNT, 0) AVAILABLE_AMOUNT,\n" + 
						"       UPPER_LIMIT,\n" + 
						"       LOWER_LIMIT,\n" + 
						"       USER_NAME,\n" + 
						"       USER_ACCOUNT,\n" + 
						"       TO_CHAR(SP.SUPPLIER_CODE) SUPPLIER_CODE\n" + 
						"  FROM (\n" + 
						"        -- 在途数量查询\n" + 
						"        SELECT SD.PART_ID, SUM(SD.PCH_COUNT) PCH_COUNT\n" + 
						"          FROM PT_BU_PCH_ORDER_SPLIT_DTL SD\n" + 
						"         WHERE EXISTS (SELECT 1\n" + 
						"                  FROM PT_BU_PCH_ORDER_SPLIT S\n" + 
						"                 WHERE S.SPLIT_ID = SD.SPLIT_ID\n" + 
						"                   AND S.ORDER_STATUS IN (201002, 201003, 201004)\n" + 
						"                   AND S.ORDER_TYPE <> 203205\n"  + tempStr +
						"  )\n" + 
						"           AND SD.PCH_COUNT > NVL(SD.STORAGE_COUNT, 0)\n" + 
						"         GROUP BY SD.PART_ID\n" + 
						"\n" + 
						"        ) TAB1,\n" + 
						"\n" + 
						"       (\n" + 
						"        -- 预警查询\n" + 
						"        SELECT BS.PART_ID,\n" + 
						"                BS.AVAILABLE_AMOUNT,\n" + 
						"                SS.UPPER_LIMIT,\n" + 
						"                SS.LOWER_LIMIT\n" + 
						"          FROM PT_BU_STOCK BS, PT_BA_STOCK_SAFESTOCKS SS\n" + 
						"         WHERE BS.WAREHOUSE_CODE = '"+warehouseCode+"'\n" + 
						"           AND BS.WAREHOUSE_ID = SS.STOCK_ID(+)\n" + 
						"           AND BS.PART_ID = SS.PART_ID(+)\n" + 
						"\n" + 
						"        ) TAB2,\n" + 
						"       PT_BA_INFO I,\n" + 
						"       PT_BA_PCH_ATTRIBUTE PA,\n" + 
						"       (\n" + 
						"        -- 供应商查询\n" + 
						"        SELECT ZH_CONCAT(B.SUPPLIER_CODE) SUPPLIER_CODE, A.PART_ID\n" + 
						"          FROM PT_BA_PART_SUPPLIER_RL A, PT_BA_SUPPLIER B\n" + 
						"         WHERE A.SUPPLIER_ID = B.SUPPLIER_ID\n" + 
						"         GROUP BY PART_ID) SP\n" + 
						" WHERE I.PART_ID = TAB2.PART_ID\n" + 
						"   AND I.PART_ID = TAB1.PART_ID(+)\n" + 
						"   AND I.PART_ID = PA.PART_ID(+)\n" + 
						"   AND I.PART_ID = SP.PART_ID(+)\n" + 
						"   AND EXISTS (SELECT 1\n" + 
						"          FROM PT_BU_SALE_ORDER_DTL D\n" + 
						"         WHERE EXISTS (SELECT 1\n" + 
						"                  FROM PT_BU_SALE_ORDER O\n" + 
						"                 WHERE O.IF_CHANEL_ORDER = 100102\n" + 
						"                   AND O.ORDER_STATUS = 202203)\n" + 
						"           AND D.ORDER_COUNT = D.AUDIT_COUNT\n" + 
						"           AND D.PART_ID = I.PART_ID)\n" + " AND " + conditions + 
						" ORDER BY I.PART_ID";
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
	public QuerySet queryDownInfo(String conditions, User user, String warehouseCode)
			throws Exception {
		String tempStr = this.checkStockType(warehouseCode);
		conditions = this.convertStr(conditions);
		String sql = 
				"SELECT I.PART_ID,\n" +
						"       I.PART_CODE,\n" + 
						"       I.PART_NAME,\n" + 
						"       NVL(PCH_COUNT, 0) PCH_COUNT,\n" + 
						"       NVL(AVAILABLE_AMOUNT, 0) AVAILABLE_AMOUNT,\n" + 
						"       UPPER_LIMIT,\n" + 
						"       LOWER_LIMIT,\n" + 
						"       USER_NAME,\n" + 
						"       USER_ACCOUNT,\n" + 
						"       TO_CHAR(SP.SUPPLIER_CODE) SUPPLIER_CODE\n" + 
						"  FROM (\n" + 
						"        -- 在途数量查询\n" + 
						"        SELECT SD.PART_ID, SUM(SD.PCH_COUNT) PCH_COUNT\n" + 
						"          FROM PT_BU_PCH_ORDER_SPLIT_DTL SD\n" + 
						"         WHERE EXISTS (SELECT 1\n" + 
						"                  FROM PT_BU_PCH_ORDER_SPLIT S\n" + 
						"                 WHERE S.SPLIT_ID = SD.SPLIT_ID\n" + 
						"                   AND S.ORDER_STATUS IN (201002, 201003, 201004)\n" + 
						"                   AND S.ORDER_TYPE <> 203205\n"  + tempStr +
						"  )\n" + 
						"           AND SD.PCH_COUNT > NVL(SD.STORAGE_COUNT, 0)\n" + 
						"         GROUP BY SD.PART_ID\n" + 
						"\n" + 
						"        ) TAB1,\n" + 
						"\n" + 
						"       (\n" + 
						"        -- 预警查询\n" + 
						"        SELECT BS.PART_ID,\n" + 
						"                BS.AVAILABLE_AMOUNT,\n" + 
						"                SS.UPPER_LIMIT,\n" + 
						"                SS.LOWER_LIMIT\n" + 
						"          FROM PT_BU_STOCK BS, PT_BA_STOCK_SAFESTOCKS SS\n" + 
						"         WHERE BS.WAREHOUSE_CODE = '"+warehouseCode+"'\n" + 
						"           AND BS.WAREHOUSE_ID = SS.STOCK_ID(+)\n" + 
						"           AND BS.PART_ID = SS.PART_ID(+)\n" + 
						"\n" + 
						"        ) TAB2,\n" + 
						"       PT_BA_INFO I,\n" + 
						"       PT_BA_PCH_ATTRIBUTE PA,\n" + 
						"       (\n" + 
						"        -- 供应商查询\n" + 
						"        SELECT ZH_CONCAT(B.SUPPLIER_CODE) SUPPLIER_CODE, A.PART_ID\n" + 
						"          FROM PT_BA_PART_SUPPLIER_RL A, PT_BA_SUPPLIER B\n" + 
						"         WHERE A.SUPPLIER_ID = B.SUPPLIER_ID\n" + 
						"         GROUP BY PART_ID) SP\n" + 
						" WHERE I.PART_ID = TAB2.PART_ID\n" + 
						"   AND I.PART_ID = TAB1.PART_ID(+)\n" + 
						"   AND I.PART_ID = PA.PART_ID(+)\n" + 
						"   AND I.PART_ID = SP.PART_ID(+)\n" + 
						"   AND EXISTS (SELECT 1\n" + 
						"          FROM PT_BU_SALE_ORDER_DTL D\n" + 
						"         WHERE EXISTS (SELECT 1\n" + 
						"                  FROM PT_BU_SALE_ORDER O\n" + 
						"                 WHERE O.IF_CHANEL_ORDER = 100102\n" + 
						"                   AND O.ORDER_STATUS = 202203)\n" + 
						"           AND D.ORDER_COUNT = D.AUDIT_COUNT\n" + 
						"           AND D.PART_ID = I.PART_ID)\n" + " AND " + conditions + 
						" ORDER BY I.PART_ID";
		return this.factory.select(null, sql);
	}
}
