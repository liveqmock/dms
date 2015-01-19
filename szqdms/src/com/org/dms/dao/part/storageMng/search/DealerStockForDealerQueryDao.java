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
 * @ClassName: DealerStockForDealerQueryDao
 * @Description: 渠道库存查询
 * @author: fuxiao
 * @date: 2014年12月16日 下午6:37:14
 */
public class DealerStockForDealerQueryDao extends BaseDAO {

	public static final DealerStockForDealerQueryDao getInstance(
			ActionContext ac) {
		DealerStockForDealerQueryDao dao = new DealerStockForDealerQueryDao();
		ac.setDBFactory(dao.factory);
		return dao;
	}

	/**
	 * 
	 * @Title: queryList
	 * @Description: 表格查询
	 * @param page
	 * @param conditions
	 * @param user
	 * @return
	 * @throws SQLException
	 * @return: BaseResultSet
	 */
	public BaseResultSet queryList(PageManager page, String conditions,
			User user) throws SQLException {
		String sql = " SELECT I.PART_CODE,\n" + "        I.PART_NAME,\n"
				+ "        T.SUPPLIER_CODE,\n" + "        T.SUPPLIER_NAME,\n"
				+ "        T.AMOUNT,\n" + "        T.OCCUPY_AMOUNT,\n"
				+ "        T.AVAILABLE_AMOUNT,\n" + "        I.SALE_PRICE\n"
				+ " FROM PT_BU_DEALER_STOCK T, PT_BA_INFO I\n"
				+ "WHERE T.PART_ID = I.PART_ID\n" + "  AND T.STATUS = '100201'"
				+ "  AND T.ORG_ID = '" + user.getOrgId() + "'" + "  AND "
				+ conditions;
		return this.factory.select(sql, page);
	}

	/**
	 * 
	 * @Title: download
	 * @Description: 下载查询
	 * @param conditions
	 * @param user
	 * @return
	 * @return: QuerySet
	 * @throws SQLException
	 */
	public QuerySet download(String conditions, User user) throws SQLException {
		// TODO Auto-generated method stub
		return this.factory.select(new Object[] { user.getOrgId() },
				" SELECT I.PART_CODE,\n" + "        I.PART_NAME,\n"
						+ "        T.SUPPLIER_CODE,\n"
						+ "        T.SUPPLIER_NAME,\n" + "        T.AMOUNT,\n"
						+ "        T.OCCUPY_AMOUNT,\n"
						+ "        T.AVAILABLE_AMOUNT,\n"
						+ "        I.SALE_PRICE\n"
						+ " FROM PT_BU_DEALER_STOCK T, PT_BA_INFO I\n"
						+ "WHERE T.PART_ID = I.PART_ID\n"
						+ "  AND T.STATUS = '100201'" + "  AND T.ORG_ID = ?  AND " + conditions);
	}

}
