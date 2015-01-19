package com.org.dms.dao.part.purchaseMng.purchaseOrder;

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
 * @ClassName: PchOrderForDealerQuerylDao
 * @Description: 采购订单查询（供应商）
 * @author: ALONY
 * @date: 2014年12月3日 下午3:52:23
 */
public class PchOrderForDealerQuerylDao extends BaseDAO {

	public static final PchOrderForDealerQuerylDao getInstance(ActionContext atx) {
		PchOrderForDealerQuerylDao dao = new PchOrderForDealerQuerylDao();
		atx.setDBFactory(dao.factory);
		return dao;
	}

	/**
	 * 
	 * @Title: queryList
	 * @Description: 表格查询
	 * @param pageManager
	 * @param conditions
	 * @param user
	 * @return
	 * @throws Exception
	 * @return: BaseResultSet
	 */
	public BaseResultSet queryList(PageManager pageManager, String conditions,
			User user) throws Exception {
		BaseResultSet rs = null;
		String sql = "SELECT T.SPLIT_ID,\n" + "       T.SPLIT_NO, T.PURCHASE_AMOUNT, \n"
				+ "       T.PLAN_DISTRIBUTION,\n" + "       T.ORDER_STATUS,\n"
				+ "       T.PURCHASE_TYPE,\n" + "       T.SUPPLIER_CODE,\n"
				+ "       T.SUPPLIER_NAME,\n" + "       T.SUPPLIER_ID,\n"
				+ "       T.SELECT_MONTH,\n" + "       T.APPLY_DATE, T.CLOSE_DATE, \n"
				+ "       T.APPLY_USER\n" + "  FROM PT_BU_PCH_ORDER_SPLIT T\n"
				+ " WHERE T.STATUS = 100201\n"
				+ "       AND T.ORDER_STATUS <> 201001\n";

		// 判断用户是否为供应商
		if (user.getOrgDept().getOrgType().equals(DicConstant.ZZLB_12)) {
			sql += "       AND EXISTS (\n"
					+ "           SELECT 1 FROM PT_BA_SUPPLIER S\n"
					+ "                  WHERE S.SUPPLIER_ID = T.SUPPLIER_ID\n"
					+ "                        AND S.ORG_ID = "
					+ user.getOrgId() + "\n" + "       )";
		}
		sql += " 		AND " + conditions + " ORDER BY T.SPLIT_ID DESC";
		rs = this.factory.select(sql, pageManager);
		rs.setFieldDic("ORDER_STATUS", "CGDDZT");
		rs.setFieldDic("PURCHASE_TYPE", "CGDDLX");
		rs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
		rs.setFieldDateFormat("CLOSE_DATE", "yyyy-MM-dd HH:mm:ss");
		rs.setFieldUserID("APPLY_USER");
		return rs;
	}

	/**
	 * 
	 * @Title: queryInfoById
	 * @Description: 根据ID查询详细信息
	 * @param id
	 * @return
	 * @throws SQLException
	 * @return: QuerySet
	 */
	public QuerySet queryInfoById(String id) throws SQLException {
		return this.factory
				.select(new Object[] { id },
						"SELECT SPLIT_ID, PURCHASE_ID, SPLIT_NO, PURCHASE_AMOUNT, SELECT_MONTH, "
								+ " (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = PURCHASE_TYPE ) PURCHASE_TYPE, PURCHASE_CATEGORY, SUPPLIER_ID, SUPPLIER_NAME,\n"
								+ "       SUPPLIER_CODE, ORDER_TYPE, PLAN_AMOUNT, PURCHASE_AMOUNT, PURCHASE_COUNT, "
								+ " (SELECT PERSON_NAME FROM TM_USER WHERE ACCOUNT = APPLY_USER) APPLY_USER, "
								+ " TO_CHAR(APPLY_DATE, 'YYYY-MM-DD HH24:MI:SS') APPLY_DATE, "
								+ " TO_CHAR(CLOSE_DATE, 'YYYY-MM-DD HH24:MI:SS') CLOSE_DATE, "
								+ "    (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = ORDER_STATUS ) ORDER_STATUS,\n"
								+ "       COMPANY_ID, ORG_ID, STATUS, OEM_COMPANY_ID, SECRET_LEVEL, ORDER_NO, IF_ON_TIME, CONFIRM_ADVISE, DELIVERY_CYCLE, SHIP_COUNT,\n"
								+ "       STORAGE_COUNT, CLOSE_DATE, INVOICE_STATUS, INVOICE_DATE, INVOICE_NO, INVOICE_REMARKS, SETTLE_STATUS, SETTLE_DATE, SETTLE_REMARKS,\n"
								+ "       PACC_NO, COMPLETE_DATE, ACCEPT_COUNT, REPUIREMENT_TIME, SUPPLIER_DATE, SHIP_TIMES, FIRST_SHIP_DATE, LAST_SHIP_DATE, PLAN_DISTRIBUTION\n"
								+ "FROM PT_BU_PCH_ORDER_SPLIT WHERE SPLIT_ID = ?");
	}

	/**
	 * 
	 * @Title: queryInfoDetailsById
	 * @Description: 根据ID查询子表信息
	 * @param pageManager
	 * @param conditions
	 * @return
	 * @throws Exception
	 * @return: BaseResultSet
	 */
	public BaseResultSet queryInfoDetailsById(PageManager pageManager,
			String conditions) throws Exception {
		BaseResultSet rs = null;
		String sql = "SELECT\n"
				+ "       DETAIL_ID, SPLIT_ID, I.PART_ID, I.PART_NAME, I.PART_CODE, SUPPLIER_ID,\n"
				+ "       SUPPLIER_NAME, SUPPLIER_CODE, PCH_COUNT, D.PCH_PRICE, PCH_AMOUNT, D.REMARKS,\n"
				+ "       ADJUST_REMARKS, PLAN_AMOUNT, SHIP_COUNT, STORAGE_COUNT,\n"
				+ "       I.PLAN_PRICE, ACCEPT_COUNT, DISTRIBUTION_NO, \n"
				+ "		I.UNIT, I.MIN_PACK "
				+ "FROM PT_BU_PCH_ORDER_SPLIT_DTL D, PT_BA_INFO I\n"
				+ "WHERE D.PART_ID = I.PART_ID AND " + conditions;
		rs = this.factory.select(sql, pageManager);
		rs.setFieldDic("UNIT", "JLDW");
		return rs;
	}

	/**
	 * 
	 * @Title: queryDownInfo
	 * @Description: 下载信息查询
	 * @param conditions
	 * @param user
	 * @return
	 * @throws Exception
	 * @return: QuerySet
	 */
	public QuerySet queryDownInfo(String conditions, User user)
			throws Exception {
		String sql = "SELECT T.SPLIT_ID,\n"
				+ "       T.SPLIT_NO, T.PURCHASE_AMOUNT,\n"
				+ "       T.PLAN_DISTRIBUTION,\n"
				+ "       (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = T.ORDER_STATUS ) ORDER_STATUS,\n"
				+ "       (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = T.PURCHASE_TYPE ) PURCHASE_TYPE,\n"
				+ "       T.SUPPLIER_CODE,\n"
				+ "       T.SUPPLIER_NAME,\n"
				+ "       T.SUPPLIER_ID,\n"
				+ "       T.SELECT_MONTH,\n"
				+ "       TO_CHAR(T.APPLY_DATE, 'YYYY-MM-DD HH24:MI:SS') APPLY_DATE,\n"
				+ " 	  TO_CHAR(T.CLOSE_DATE, 'YYYY-MM-DD HH24:MI:SS') CLOSE_DATE, "
				+ "       (SELECT PERSON_NAME FROM TM_USER WHERE ACCOUNT = T.APPLY_USER) APPLY_USER\n"
				+ "  FROM PT_BU_PCH_ORDER_SPLIT T\n"
				+ " WHERE T.STATUS = 100201\n"
				+ "       AND T.ORDER_STATUS <> 201001\n";

		// 判断用户是否为供应商
		if (user.getOrgDept().getOrgType().equals(DicConstant.ZZLB_12)) {
			sql += "       AND EXISTS (\n"
					+ "           SELECT 1 FROM PT_BA_SUPPLIER S\n"
					+ "                  WHERE S.SUPPLIER_ID = T.SUPPLIER_ID\n"
					+ "                        AND S.ORG_ID = "
					+ user.getOrgId() + "\n" + "       )";
		}
		sql += " 		AND " + conditions + " ORDER BY T.SPLIT_ID DESC";
		return this.factory.select(null, sql);
	}
	
	public QuerySet download(User user, String SPLIT_ID) throws Exception
    {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.DETAIL_ID,\n" );
    	sql.append("       T.SPLIT_ID,\n" );
    	sql.append("       (SELECT SPLIT_NO FROM PT_BU_PCH_ORDER_SPLIT WHERE SPLIT_ID=T.SPLIT_ID) SPLIT_NO,\n" );
    	sql.append("       T.PART_ID,\n" );
    	sql.append("       T.PART_CODE,\n" );
    	sql.append("       T.PART_NAME,\n" );
    	sql.append("       (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = T1.UNIT) UNIT,\n" );
    	sql.append("       T1.MIN_PACK||'/'||(SELECT DIC_VALUE FROM DIC_TREE WHERE ID = T1.MIN_UNIT) MIN_PACK,\n" );
    	sql.append("       T1.MIN_UNIT,");
    	sql.append("       T.PCH_COUNT,\n" );
    	sql.append("       T.PCH_PRICE,\n" );
    	sql.append("       T.PCH_AMOUNT,\n" );
    	sql.append("       T2.DELIVERY_CYCLE,\n" );
    	sql.append("       T.REMARKS\n" );
    	sql.append("  FROM PT_BU_PCH_ORDER_SPLIT_DTL T, PT_BA_INFO T1, PT_BU_PCH_ORDER_SPLIT T2\n" );
    	sql.append(" WHERE T.PART_ID = T1.PART_ID\n" );
    	sql.append("   AND T.SPLIT_ID = T2.SPLIT_ID\n" );
    	sql.append("	AND T.SPLIT_ID = "+SPLIT_ID+"");
    	return factory.select(null,sql.toString());
    }
}
