package com.org.dms.dao.part.storageMng.search;

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
 * @ClassName: DealerSaleOrderQueryDao
 * @Description: 销售出库查询（供）
 * @author: fuxiao
 * @date: 2014年12月18日 下午3:44:03
 */
public class DealerSaleOrderQueryDao extends BaseDAO {

	public static final DealerSaleOrderQueryDao getInstance(ActionContext atx) {
		DealerSaleOrderQueryDao dao = new DealerSaleOrderQueryDao();
		atx.setDBFactory(dao.factory);
		return dao;
	}

	/**
	 * 
	 * @Title: searchInfo
	 * @Description: 表格查询方法
	 * @param conditions
	 * @param page
	 * @param user
	 * @return
	 * @throws SQLException
	 * @return: BaseResultSet
	 */
	public BaseResultSet searchInfo(String conditions, PageManager page,
			User user) throws SQLException {

		// 定义返回结果集
		BaseResultSet bs = null;

		String sql = "SELECT O.ORDER_ID, O.ORDER_NO,\n" + "       O.ORDER_TYPE,\n"
				+ "       O.ORDER_STATUS,\n" + "       O.ORG_CODE,\n"
				+ "       O.ORG_NAME,\n" + "       O.LINK_MAN,\n"
				+ "       O.PHONE,\n" + "       O.DELIVERY_ADDR,\n"
				+ "       O.REAL_AMOUNT,\n" + "       O.APPLY_DATE, \n" 
				+ " O.CLOSE_DATE,"
				+ " MAX(C.APPLY_DATE) OUT_DATE, "
				+ " (SELECT SUM(D.UNIT_PRICE * C.COUNT)\n" +
				"   FROM PT_BU_SALE_ORDER_DTL D, PT_BU_DEALER_STOCK_CHANGE C\n" + 
				"  WHERE D.ORDER_ID = O.ORDER_ID\n" + 
				"    AND C.OUT_NO = O.ORDER_NO\n" + 
				"    AND C.PART_ID = D.PART_ID\n" + 
				"    AND C.APPLY_TYPE = 204102\n" + 
				"    AND C.STORAGE_TYPE = 204701) AMOUNT "
				+ "  FROM PT_BU_SALE_ORDER O, PT_BU_DEALER_STOCK_CHANGE C \n" + " WHERE O.ORDER_NO = C.OUT_NO(+) AND EXISTS (SELECT 1\n"
				+ "          FROM PT_BU_DEALER_STOCK_CHANGE C\n"
				+ "         WHERE C.APPLY_TYPE = 204102\n"
				+ "           AND C.STORAGE_TYPE = 204701\n"
				+ "           AND C.ORG_ID = "+user.getOrgId()+"\n"
				+ "           AND O.ORDER_NO = C.OUT_NO) AND " + conditions + " GROUP BY O.ORDER_ID, O.ORDER_NO, O.ORDER_TYPE, O.ORDER_STATUS, O.ORG_CODE, O.ORG_NAME, O.LINK_MAN, O.PHONE, O.DELIVERY_ADDR, O.REAL_AMOUNT, O.APPLY_DATE, O.CLOSE_DATE ORDER BY O.ORDER_ID DESC";
		bs = factory.select(sql, page);
		bs.setFieldDic("ORDER_TYPE", "DDLX");
		bs.setFieldDic("ORDER_STATUS", "DDZT");
		bs.setFieldDic("UNIT", DicConstant.JLDW);
		bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDateFormat("CLOSE_DATE", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDateFormat("OUT_DATE", "yyyy-MM-dd HH:mm:ss");
		return bs;
	}

	/**
	 * 
	 * @Title: queryDownInfo
	 * @Description: excel下载查询
	 * @param conditions
	 * @param user
	 * @return
	 * @return: QuerySet
	 * @throws SQLException
	 */
	public QuerySet queryDownInfo(String conditions, User user)
			throws SQLException {
		String sql = "SELECT O.ORDER_ID, O.ORDER_NO,\n"
				+ "      (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = O.ORDER_TYPE ) ORDER_TYPE,\n"
				+ "      (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = O.ORDER_STATUS ) ORDER_STATUS,\n"
				+ "      O.ORG_CODE,\n"
				+ "      O.ORG_NAME,\n"
				+ "      O.LINK_MAN,\n"
				+ "      O.PHONE,\n"
				+ "      TO_CHAR(MAX(C.APPLY_DATE), 'YYYY-MM-DD HH24:MI:SS') OUT_DATE, "
				+ "      O.DELIVERY_ADDR,\n"
				+ "      O.REAL_AMOUNT,\n"
				+ " O.CLOSE_DATE,"
				+ " (SELECT SUM(D.UNIT_PRICE * C.COUNT)\n" +
				"   FROM PT_BU_SALE_ORDER_DTL D, PT_BU_DEALER_STOCK_CHANGE C\n" + 
				"  WHERE D.ORDER_ID = O.ORDER_ID\n" + 
				"    AND C.OUT_NO = O.ORDER_NO\n" + 
				"    AND C.PART_ID = D.PART_ID\n" + 
				"    AND C.APPLY_TYPE = 204102\n" + 
				"    AND C.STORAGE_TYPE = 204701) AMOUNT, "
				+ "      TO_CHAR(O.APPLY_DATE, 'YYYY-MM-DD HH24:MI:SS') APPLY_DATE,\n"
				+ " (SELECT SUM(D.UNIT_PRICE * C.COUNT)\n" +
				"   FROM PT_BU_SALE_ORDER_DTL D, PT_BU_DEALER_STOCK_CHANGE C\n" + 
				"  WHERE D.ORDER_ID = O.ORDER_ID\n" + 
				"    AND C.OUT_NO = O.ORDER_NO\n" + 
				"    AND C.PART_ID = D.PART_ID\n" + 
				"    AND C.APPLY_TYPE = 204102\n" + 
				"    AND C.STORAGE_TYPE = 204701) AMOUNT "
				+ " FROM PT_BU_SALE_ORDER O, PT_BU_DEALER_STOCK_CHANGE C \n" + "WHERE O.ORDER_NO = C.OUT_NO(+) AND  EXISTS (SELECT 1\n"
				+ "         FROM PT_BU_DEALER_STOCK_CHANGE C\n"
				+ "        WHERE C.APPLY_TYPE = 204102\n"
				+ "          AND C.STORAGE_TYPE = 204701\n"
				+ "          AND C.ORG_ID = "+user.getOrgId()+"\n"
				+ "          AND O.ORDER_NO = C.OUT_NO) AND " + conditions + " GROUP BY O.ORDER_ID, O.ORDER_NO, O.ORDER_TYPE, O.ORDER_STATUS, O.ORG_CODE, O.ORG_NAME, O.LINK_MAN, O.PHONE, O.DELIVERY_ADDR, O.REAL_AMOUNT, O.APPLY_DATE, O.CLOSE_DATE ORDER BY O.ORDER_ID";
		return this.factory.select(null, sql);

	}

}
