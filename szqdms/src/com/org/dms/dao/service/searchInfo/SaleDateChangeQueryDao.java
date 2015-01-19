package com.org.dms.dao.service.searchInfo;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.mvc.context.ActionContext;

/**
 * 
 * @ClassName: SaleDateChangeQueryDao
 * @Description: 销售日期变更查询Dao
 * @author: fuxiao
 * @date: 2015年1月5日 下午7:31:06
 */
public class SaleDateChangeQueryDao extends BaseDAO{
	
	public static final SaleDateChangeQueryDao getInstance(ActionContext ac) {
		SaleDateChangeQueryDao dao = new SaleDateChangeQueryDao();
		ac.setDBFactory(dao.factory);
		return dao;
	}

	/**
	 * 
	 * @Title: queryList
	 * @Description: 表格数据查询
	 * @param pageManager
	 * @param conditions
	 * @return
	 * @throws Exception
	 * @return: BaseResultSet
	 */
	public BaseResultSet queryList(PageManager pageManager, String conditions)
			throws Exception {
		BaseResultSet rs = null;
		String sql = 
				"SELECT LOG_ID,\n" +
						"       VIN,\n" + 
						"       (SELECT MAX(DECODE(SP.PRODUCTSTATE,\n" + 
						"                          '20',\n" + 
						"                          '在库',\n" + 
						"                          '30',\n" + 
						"                          '开票',\n" + 
						"                          '50',\n" + 
						"                          '在途',\n" + 
						"                          '60',\n" + 
						"                          '到货',\n" + 
						"                          '70',\n" + 
						"                          '已售',\n" + 
						"                          '其他'))\n" + 
						"          FROM SD2_PRODUCT SP\n" + 
						"         WHERE SP.VIN = SUBSTR(L.VIN, -8)) SALE_STATUS,\n" + 
						"       MODELS_CODE,\n" + 
						"       USER_TYPE,\n" + 
						"       OLD_SDATE,\n" + 
						"       NEW_SDATE,\n" + 
						"       APPLY_REASON,\n" + 
						"       APPLY_COMPANY,\n" + 
						"       APPLY_DATE,\n" + 
						"       CHECK_REMARKS,\n" + 
						"       CHECK_USER,\n" + 
						"       CHECK_DATE,\n" + 
						"       CHECK_STATUS\n" + 
						"  FROM MAIN_VEHICLE_SDATE_LOG L\n" + 
						" WHERE " + conditions +
						" ORDER　BY　APPLY_COMPANY, APPLY_DATE";
		rs = this.factory.select(sql, pageManager);
		rs.setFieldDic("CHECK_STATUS", "CLXSRQZT");
		rs.setFieldDateFormat("OLD_SDATE", "yyyy-MM-dd");
		rs.setFieldDateFormat("NEW_SDATE", "yyyy-MM-dd");
		rs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
		rs.setFieldDateFormat("CHECK_DATE", "yyyy-MM-dd");
		return rs;
	}

	/**
	 * 
	 * @Title: queryDownInfo
	 * @Description: 下载数据查询
	 * @param conditions
	 * @return
	 * @throws Exception
	 * @return: QuerySet
	 */
	public QuerySet queryDownInfo(String conditions) throws Exception {
		return this.factory
				.select(null,
						"SELECT LOG_ID,\n" +
								"       VIN,\n" + 
								"       (SELECT MAX(DECODE(SP.PRODUCTSTATE,\n" + 
								"                          '20',\n" + 
								"                          '在库',\n" + 
								"                          '30',\n" + 
								"                          '开票',\n" + 
								"                          '50',\n" + 
								"                          '在途',\n" + 
								"                          '60',\n" + 
								"                          '到货',\n" + 
								"                          '70',\n" + 
								"                          '已售',\n" + 
								"                          '其他'))\n" + 
								"          FROM SD2_PRODUCT SP\n" + 
								"         WHERE SP.VIN = SUBSTR(L.VIN, -8)) SALE_STATUS,\n" + 
								"       MODELS_CODE,\n" + 
								"       USER_TYPE,\n" + 
								"       OLD_SDATE,\n" + 
								"       NEW_SDATE,\n" + 
								"       APPLY_REASON,\n" + 
								"       APPLY_COMPANY,\n" + 
								"       APPLY_DATE,\n" + 
								"       CHECK_REMARKS,\n" + 
								"       CHECK_USER,\n" + 
								"       CHECK_DATE,\n" + 
								"       (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = CHECK_STATUS) CHECK_STATUS\n" + 
								"  FROM MAIN_VEHICLE_SDATE_LOG L\n" + 
								" WHERE " + conditions +
								" ORDER　BY　APPLY_COMPANY, APPLY_DATE");
	}
}
