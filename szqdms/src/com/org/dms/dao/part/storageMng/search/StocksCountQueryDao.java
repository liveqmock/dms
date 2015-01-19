package com.org.dms.dao.part.storageMng.search;

import java.sql.Connection;
import java.sql.SQLException;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 
 * @ClassName: StocksCountQueryDao
 * @Description: 本部库存查询
 * @author: fuxiao
 * @date: 2014年11月2日 下午2:10:33
 */
public class StocksCountQueryDao extends BaseDAO {

	public static final StocksCountQueryDao getInstance(ActionContext ac) {
		StocksCountQueryDao dao = new StocksCountQueryDao();
		ac.setDBFactory(dao.factory);
		return dao;
	}

	public Connection getConnection() {
		return super.factory.getConnection();
	}

	/**
	 * 
	 * @Title: queryList
	 * @Description: 查询表单查询
	 * @param pageManager
	 * @param conditions
	 * @param user
	 * @return
	 * @throws Exception
	 * @return: BaseResultSet
	 */
	public BaseResultSet queryList(PageManager pageManager, String conditions,
			User user) throws Exception {
		String sql = "SELECT\n"
				+ "  D.DTL_ID,\n"
				+ "  D.PART_ID,\n"
				+ "  D.PART_CODE,\n"
				+ "  D.PART_NAME,\n"
				+ "  ST.PART_NO,\n"
				+ "  D.AMOUNT,\n"
				+ "  D.OCCUPY_AMOUNT,\n"
				+ "  D.AVAILABLE_AMOUNT,\n"
				+ "  D.SUPPLIER_NAME,\n"
				+ "  K.USER_NAME,\n"
				+ "  ST.WAREHOUSE_NAME,\n"
				+ "  ST.WAREHOUSE_CODE,\n"
				+ "  D.AREA_NAME,\n"
				+ "  D.AREA_CODE,\n"
				+ "  D.POSITION_NAME,\n"
				+ "  D.POSITION_CODE\n"
				+ "FROM PT_BU_STOCK_DTL D, PT_BU_STOCK ST, PT_BA_WAREHOUSE_KEEPER K, PT_BA_WAREHOUSE W\n"
				+ " WHERE D.STOCK_ID = ST.STOCK_ID AND K.WAREHOUSE_ID = ST.WAREHOUSE_ID "
				+ " AND ST.PART_ID = K.PART_ID AND ST.STATUS = "
				+ DicConstant.YXBS_01 + " AND " + conditions
				+ " AND ST.WAREHOUSE_ID = W.WAREHOUSE_ID ";

		// begin 20150106 by fuxiao 赵工提出取消军品的限制
		// 判断用户是否为军品用户
		if (this.checkUserIsAM(user)) {
			sql += " AND W.WAREHOUSE_TYPE = 100102 ";
		}
		/*
		 * else { sql += " AND W.WAREHOUSE_TYPE <> 100102 "; }
		 */
		// end

		sql += " ORDER BY D.PART_CODE";
		return this.factory.select(sql, pageManager);
	}
	
	/**
	 * 
	 * @Title: queryList
	 * @Description: 包含价格
	 * @param pageManager
	 * @param conditions
	 * @param user
	 * @return
	 * @throws Exception
	 * @return: BaseResultSet
	 */
	public BaseResultSet queryListAndPrice(PageManager pageManager, String conditions,
			User user) throws Exception {
		String sql = "SELECT\n"
				+ "  D.DTL_ID,\n"
				+ "  D.PART_ID,\n"
				+ "  D.PART_CODE,\n"
				+ "  D.PART_NAME,\n"
				+ "  ST.PART_NO,\n"
				+ "  D.AMOUNT,\n"
				+ "  I.PLAN_PRICE,\n"
				+ "  D.OCCUPY_AMOUNT,\n"
				+ "  D.AVAILABLE_AMOUNT,\n"
				+ "  D.SUPPLIER_NAME,\n"
				+ "  K.USER_NAME,\n"
				+ "  ST.WAREHOUSE_NAME,\n"
				+ "  ST.WAREHOUSE_CODE,\n"
				+ "  D.AREA_NAME,\n"
				+ "  D.AREA_CODE,\n"
				+ "  D.POSITION_NAME,\n"
				+ "  D.POSITION_CODE\n"
				+ "FROM PT_BU_STOCK_DTL D, PT_BU_STOCK ST, PT_BA_WAREHOUSE_KEEPER K, PT_BA_WAREHOUSE W, PT_BA_INFO I\n"
				+ " WHERE D.STOCK_ID = ST.STOCK_ID AND K.WAREHOUSE_ID = ST.WAREHOUSE_ID "
				+ " AND D.PART_ID = I.PART_ID AND ST.PART_ID = K.PART_ID AND ST.STATUS = "
				+ DicConstant.YXBS_01 + " AND " + conditions
				+ " AND ST.WAREHOUSE_ID = W.WAREHOUSE_ID ";

		// begin 20150106 by fuxiao 赵工提出取消军品的限制
		// 判断用户是否为军品用户
		if (this.checkUserIsAM(user)) {
			sql += " AND W.WAREHOUSE_TYPE = 100102 ";
		}
		/*
		 * else { sql += " AND W.WAREHOUSE_TYPE <> 100102 "; }
		 */
		// end

		sql += " ORDER BY D.PART_CODE";
		return this.factory.select(sql, pageManager);
	}

	/**
	 * 
	 * @Title: checkUserIsAM
	 * @Description: 判断用户的org是否属于军品，true：是，false: 不是
	 * @param user
	 * @return
	 * @throws SQLException
	 * @return: boolean
	 */
	public boolean checkUserIsAM(User user) throws SQLException {
		String res = this.factory.select("SELECT F_IS_AM(" + user.getOrgId()
				+ ") FROM DUAL")[0][0];
		return res.equals("1");
	}

	/**
	 * 
	 * @Title: queryDownInfo
	 * @Description: 查询需要下载的数据
	 * @param conditions
	 * @param user
	 * @return
	 * @throws Exception
	 * @return: QuerySet
	 */
	public QuerySet queryDownInfo(String conditions, User user)
			throws Exception {

		String sql = "SELECT\n"
				+ "  D.DTL_ID,\n"
				+ "  D.PART_ID,\n"
				+ "  D.PART_CODE,\n"
				+ "  D.PART_NAME,\n"
				+ "  ST.PART_NO,\n"
				+ "  D.AMOUNT,\n"
				+ "  D.OCCUPY_AMOUNT,\n"
				+ "  D.AVAILABLE_AMOUNT,\n"
				+ "  D.SUPPLIER_NAME,\n"
				+ "  K.USER_NAME,\n"
				+ "  ST.WAREHOUSE_NAME,\n"
				+ "  ST.WAREHOUSE_CODE,\n"
				+ "  D.AREA_NAME,\n"
				+ "  D.AREA_CODE,\n"
				+ "  D.POSITION_NAME,\n"
				+ "  D.POSITION_CODE\n"
				+ "FROM PT_BU_STOCK_DTL D, PT_BU_STOCK ST, PT_BA_WAREHOUSE_KEEPER K, PT_BA_WAREHOUSE W\n"
				+ " WHERE D.STOCK_ID = ST.STOCK_ID AND K.WAREHOUSE_ID = ST.WAREHOUSE_ID "
				+ " AND ST.PART_ID = K.PART_ID AND ST.STATUS = "
				+ DicConstant.YXBS_01 + " AND " + conditions
				+ " AND ST.WAREHOUSE_ID = W.WAREHOUSE_ID ";

		// begin 20150106 by fuxiao 赵工提出取消军品的限制
		// 判断用户是否为军品用户
		if (this.checkUserIsAM(user)) {
			sql += " AND W.WAREHOUSE_TYPE = 100102 ";
		}
		// end

		sql += " ORDER BY D.PART_CODE";
		return this.factory.select(null, sql);
	}
	

	/**
	 * 
	 * @Title: queryDownInfoAndPrice
	 * @Description: 包含价格
	 * @param conditions
	 * @param user
	 * @return
	 * @throws Exception
	 * @return: QuerySet
	 */
	public QuerySet queryDownInfoAndPrice(String conditions, User user)
			throws Exception {

		String sql = "SELECT\n"
				+ "  D.DTL_ID,\n"
				+ "  D.PART_ID,\n"
				+ "  D.PART_CODE,\n"
				+ "  D.PART_NAME,\n"
				+ "  ST.PART_NO,\n"
				+ "  D.AMOUNT,\n"
				+ "  I.PLAN_PRICE,\n"
				+ "  D.OCCUPY_AMOUNT,\n"
				+ "  D.AVAILABLE_AMOUNT,\n"
				+ "  D.SUPPLIER_NAME,\n"
				+ "  K.USER_NAME,\n"
				+ "  ST.WAREHOUSE_NAME,\n"
				+ "  ST.WAREHOUSE_CODE,\n"
				+ "  D.AREA_NAME,\n"
				+ "  D.AREA_CODE,\n"
				+ "  D.POSITION_NAME,\n"
				+ "  D.POSITION_CODE\n"
				+ "FROM PT_BU_STOCK_DTL D, PT_BU_STOCK ST, PT_BA_WAREHOUSE_KEEPER K, PT_BA_WAREHOUSE W, PT_BA_INFO I\n"
				+ " WHERE D.STOCK_ID = ST.STOCK_ID AND K.WAREHOUSE_ID = ST.WAREHOUSE_ID "
				+ " AND D.PART_ID = I.PART_ID AND ST.PART_ID = K.PART_ID AND ST.STATUS = "
				+ DicConstant.YXBS_01 + " AND " + conditions
				+ " AND ST.WAREHOUSE_ID = W.WAREHOUSE_ID ";

		// begin 20150106 by fuxiao 赵工提出取消军品的限制
		// 判断用户是否为军品用户
		if (this.checkUserIsAM(user)) {
			sql += " AND W.WAREHOUSE_TYPE = 100102 ";
		}
		// end

		sql += " ORDER BY D.PART_CODE";
		return this.factory.select(null, sql);
	}
}
