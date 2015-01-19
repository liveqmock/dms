package com.org.dms.dao.part.storageMng.search;

import java.sql.SQLException;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 
 * ClassName: InventoryQueryDao 
 * Function:信息查询 - 仓储相关 - 车厂库存查询Dao
 * date: 2014年9月10日 下午3:10:23
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 
 */
public class InventoryQueryDao extends BaseDAO {

	public static final InventoryQueryDao getInstance(ActionContext ac) {
		InventoryQueryDao dao = new InventoryQueryDao();
		ac.setDBFactory(dao.factory);
		return dao;
	}
	
	/**
	 * 
	 * queryList: 车厂库存查询：汇总查询
	 * @author fuxiao
	 * @param page
	 * @param conditions
	 * @param user
	 * @return
	 * @throws SQLException
	 * @since JDK 1.6
	 
	 */
	public BaseResultSet querySummarizeList(PageManager page, String conditions, User user) throws SQLException{
		String sql = 
					"SELECT\n" +
					"   WAREHOUSE_ID, WAREHOUSE_CODE, WAREHOUSE_NAME, PART_CODE, PART_NAME,\n" + 
					"   AMOUNT, OCCUPY_AMOUNT, AVAILABLE_AMOUNT,\n" + 
					"   USER_NAME, USER_ACCOUNT, PLAN_PRICE, SALE_PRICE, UPPER_LIMIT, LOWER_LIMIT\n" + 
					"FROM (\n" + 
					"  SELECT\n" + 
					"          T1.WAREHOUSE_ID, T1.WAREHOUSE_CODE, T1.WAREHOUSE_NAME, I.PART_CODE, I.PART_NAME,\n" + 
					"          SUM(T1.AMOUNT) AMOUNT, SUM(T1.OCCUPY_AMOUNT) OCCUPY_AMOUNT, SUM(T1.AVAILABLE_AMOUNT) AVAILABLE_AMOUNT,\n" + 
					"          WK.USER_NAME,WK.USER_ACCOUNT,I.PLAN_PRICE, I.SALE_PRICE,SA.UPPER_LIMIT,SA.LOWER_LIMIT\n" + 
					"      FROM PT_BU_STOCK T1,\n" + 
					"           PT_BA_WAREHOUSE_KEEPER WK,\n" + 
					"           PT_BA_INFO I,\n" + 
					"           PT_BA_STOCK_SAFESTOCKS SA\n" + 
					"      WHERE  T1.PART_ID = WK.PART_ID\n" + 
					"            AND T1.WAREHOUSE_ID = WK.WAREHOUSE_ID\n" + 
					"            AND T1.PART_ID = I.PART_ID\n" + 
					"            AND T1.PART_ID = SA.PART_ID(+)\n" + 
					"            AND T1.WAREHOUSE_ID = SA.STOCK_ID(+)\n" + 
					"            AND T1.STATUS = 100201\n" + 
					"      GROUP BY T1.WAREHOUSE_ID, T1.WAREHOUSE_CODE, T1.WAREHOUSE_NAME, I.PART_CODE, I.PART_NAME,\n" + 
					"               WK.USER_NAME,WK.USER_ACCOUNT, I.PLAN_PRICE, I.SALE_PRICE,SA.UPPER_LIMIT,SA.LOWER_LIMIT\n" + 
					"      ORDER BY I.PART_CODE\n" + 
					") T1 WHERE " + conditions;
		return this.factory.select(sql, page);
	}
	
	/**
	 * 
	 * queryDetailsList:详细信息查询
	 * @author fuxiao
	 * Date:2014年9月13日下午1:24:50
	 * @param page
	 * @param conditions
	 * @param user
	 * @return
	 
	 * @throws SQLException 
	 */
	public BaseResultSet queryDetailsList(PageManager page, String conditions, User user) throws SQLException{

		String sql = 
					"SELECT T1.WAREHOUSE_ID, T1.WAREHOUSE_CODE, T1.WAREHOUSE_NAME,\n" +
					"       T1.AREA_ID, T1.AREA_CODE, T1.AREA_NAME,\n" + 
					"       T1.POSITION_ID, T1.POSITION_CODE, T1.POSITION_NAME,\n" + 
					"       T1.PART_ID, T1.PART_CODE, T1.PART_NAME,\n" + 
					"       T1.AMOUNT, T1.OCCUPY_AMOUNT, T1.AVAILABLE_AMOUNT,\n" + 
					"       T1.USER_NAME, T1.USER_ACCOUNT, T1.PLAN_PRICE, T1.SALE_PRICE,\n" + 
					"       T1.MIN_PACK,SA.UPPER_LIMIT, SA.LOWER_LIMIT\n" + 
					"FROM (\n" + 
					" SELECT\n" + 
					"       W.WAREHOUSE_ID, W.WAREHOUSE_CODE, W.WAREHOUSE_NAME,\n" + 
					"       SD.AREA_ID, SD.AREA_CODE, SD.AREA_NAME,\n" + 
					"       SD.POSITION_ID, SD.POSITION_CODE, SD.POSITION_NAME, I.PART_ID, I.PART_CODE, I.PART_NAME,\n" + 
					"       K.USER_NAME, K.USER_ACCOUNT, I.PLAN_PRICE, I.SALE_PRICE, I.MIN_PACK,\n" + 
					"       SUM(SD.AMOUNT) AMOUNT, SUM(SD.OCCUPY_AMOUNT) OCCUPY_AMOUNT, SUM(SD.AVAILABLE_AMOUNT) AVAILABLE_AMOUNT\n" + 
					" FROM PT_BU_STOCK_DTL SD , PT_BU_STOCK S , PT_BA_WAREHOUSE W, PT_BA_INFO I, PT_BA_WAREHOUSE_KEEPER K\n" + 
					" WHERE SD.STOCK_ID = S.STOCK_ID\n" + 
					"  AND S.WAREHOUSE_ID = W.WAREHOUSE_ID\n" + 
					"  AND S.STATUS = 100201\n" + 
					"  AND I.PART_ID = SD.PART_ID\n" + 
					"  AND S.PART_ID = K.PART_ID\n" + 
					"  AND S.WAREHOUSE_ID = K.WAREHOUSE_ID AND K.PART_ID = SD.PART_ID \n" + 
					" GROUP BY W.WAREHOUSE_ID, W.WAREHOUSE_CODE, W.WAREHOUSE_NAME,\n" + 
					"       SD.AREA_ID, SD.AREA_CODE, SD.AREA_NAME,\n" + 
					"       SD.POSITION_ID, SD.POSITION_CODE, SD.POSITION_NAME, I.PART_ID, I.PART_CODE, I.PART_NAME,\n" + 
					"       K.USER_NAME, K.USER_ACCOUNT, I.PLAN_PRICE, I.SALE_PRICE, I.MIN_PACK\n" + 
					"\n" + 
					") T1, PT_BA_STOCK_SAFESTOCKS SA\n" + 
					"WHERE " + conditions + " AND T1.PART_ID = SA.PART_ID(+) AND T1.WAREHOUSE_ID = SA.STOCK_ID(+)\n" + 
					"ORDER BY T1.PART_CODE";
		return this.factory.select(sql, page);
	}
	
	/**
	 * 
	 * @Title: dealerQueryList
	 * @Description: 渠道查询本部库存
	 * @param page
	 * @param conditions
	 * @return
	 * @throws SQLException
	 * @return: BaseResultSet
	 */
	public BaseResultSet dealerQueryList(PageManager page, String conditions) throws SQLException{

		String sql = 

					"SELECT I.PART_ID,\n" +
					"       I.PART_CODE,\n" + 
					"       I.PART_NAME,\n" + 
					"       T1.AMOUNT,\n" + 
					"       T1.OCCUPY_AMOUNT,\n" + 
					"       T1.AVAILABLE_AMOUNT,\n" + 
					"       I.SALE_PRICE\n" + 
					"  FROM PT_BU_STOCK T1, PT_BA_INFO I\n" + 
					" WHERE T1.PART_ID = I.PART_ID\n" + 
					"   AND EXISTS (SELECT 1\n" + 
					"          FROM PT_BA_WAREHOUSE W\n" + 
					"         WHERE W.WAREHOUSE_TYPE = 100101\n" + 
					"           AND W.STATUS = 100201\n" + 
					"           AND W.WAREHOUSE_ID = T1.WAREHOUSE_ID)\n" + 
					"   AND T1.STATUS = 100201\n" + " AND " + conditions +
					" ORDER BY I.PART_ID";

		return this.factory.select(sql, page);
	}
	
}
