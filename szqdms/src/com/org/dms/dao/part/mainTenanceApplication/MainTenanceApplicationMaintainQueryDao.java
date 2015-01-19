package com.org.dms.dao.part.mainTenanceApplication;

import java.sql.SQLException;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 
 * @ClassName: MainTenanceApplicationMaintainQueryDao
 * @Description: 待维护配件查询Dao
 * @author: ALONY
 * @date: 2014年12月8日 上午10:56:03
 */
public class MainTenanceApplicationMaintainQueryDao extends BaseDAO {

	public static final MainTenanceApplicationMaintainQueryDao getInstance(
			ActionContext ac) {
		MainTenanceApplicationMaintainQueryDao dao = new MainTenanceApplicationMaintainQueryDao();
		ac.setDBFactory(dao.factory);
		return dao;
	}

	/**
	 * 
	 * @Title: queryApplicationList
	 * @Description: 待维护配件查询
	 * @param page
	 * @param conditions
	 * @param user
	 * @return
	 * @throws Exception
	 * @return: BaseResultSet
	 */
	public BaseResultSet queryApplicationList(PageManager page,
			String conditions, User user) throws Exception {
		BaseResultSet bs = null;
		String sql =
		"SELECT A.APPLICATION_NO,\n"
				+ "       A.APPLICATION_TIME,\n"
				+ "       PIE.PART_NO,\n"
				+ "       PIE.PART_NAME,\n"
				+ "       PIE.VIN,\n"
				+ "       PIE.PROCESS_ROUTE,\n"
				+ "       PIE.OWN_FIRST_LEVEL,\n"
				+ "       PIE.OWN_SECOND_LEVEL,\n"
				+ "       PIE.SUPPLIER_NAME,\n"
				+ "       PIE.ENGINEERING_DEPARTMENT_REMARK,\n"
				+ "       PIE.PURCHASING_DEPARTMENT_REMARK\n"
				+ "  FROM PT_BU_MAINTENANCE_APPLICATION A, PT_BU_PART_INFO_ENTRY PIE\n"
				+ " WHERE A.APPLICATION_ID = PIE.APPLICATION_ID\n"
				+ "  AND A.APPLICATION_STATUS = 307105\n"
				+ "  AND NOT EXISTS (\n"
				+ "      SELECT 1 FROM PT_BA_INFO I WHERE I.PART_CODE = PIE.PART_NO\n"
				+ "  )" + "AND " + conditions + " ORDER BY A.APPLICATION_TIME DESC";
		bs = this.factory.select(sql, page);
		bs.setFieldDateFormat("APPLICATION_TIME", "yyyy-MM-dd HH:mm:ss");
		return bs;
	}

	/**
	 * 
	 * @Title: queryDownInfo
	 * @Description: 报表查询
	 * @param conditions
	 * @param user
	 * @return
	 * @throws SQLException
	 * @return: QuerySet
	 */
	public QuerySet queryDownInfo(String conditions, User user) throws SQLException {
		String sql =
		"SELECT A.APPLICATION_NO,\n"
				+ "       A.APPLICATION_TIME,\n"
				+ "       PIE.PART_NO,\n"
				+ "       PIE.PART_NAME,\n"
				+ "       PIE.VIN,\n"
				+ "       PIE.PROCESS_ROUTE,\n"
				+ "       PIE.OWN_FIRST_LEVEL,\n"
				+ "       PIE.OWN_SECOND_LEVEL,\n"
				+ "       PIE.SUPPLIER_NAME,\n"
				+ "       PIE.ENGINEERING_DEPARTMENT_REMARK,\n"
				+ "       PIE.PURCHASING_DEPARTMENT_REMARK\n"
				+ "  FROM PT_BU_MAINTENANCE_APPLICATION A, PT_BU_PART_INFO_ENTRY PIE\n"
				+ " WHERE A.APPLICATION_ID = PIE.APPLICATION_ID\n"
				+ "  AND A.APPLICATION_STATUS = 307105\n"
				+ "  AND NOT EXISTS (\n"
				+ "      SELECT 1 FROM PT_BA_INFO I WHERE I.PART_CODE = PIE.PART_NO\n"
				+ "  )" + "AND " + conditions + " ORDER BY A.APPLICATION_TIME DESC";
		return this.factory.select(null, sql);
	}

}
