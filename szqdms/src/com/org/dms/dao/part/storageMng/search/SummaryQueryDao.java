package com.org.dms.dao.part.storageMng.search;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.mvc.context.ActionContext;

/**
 * 
 * ClassName: SummaryQueryDao 
 * Function: 供应商应付款查询
 * date: 2014年10月30日 下午9:44:59
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class SummaryQueryDao extends BaseDAO {
	
	public static final SummaryQueryDao getInstance(ActionContext ac){
		SummaryQueryDao dao = new SummaryQueryDao();
		ac.setDBFactory(dao.factory);
		return dao;
	}
	
	/**
	 * 
	 * queryList: 查询表单查询
	 * @author fuxiao
	 * Date:2014年10月23日
	 *
	 */
	public BaseResultSet queryList(PageManager pageManager, String conditions) throws Exception{
		BaseResultSet rs = null;
		String sql = 
						"SELECT\n" +
						"      SUPPLIER_CODE,\n" + 
						"      SUPPLIER_NAME,\n" + 
						"      IRS_AMOUNT,\n" + 
						"      I_AMOUNT,\n" + 
						"      S_AMOUNT,\n" + 
						"      (IRS_AMOUNT - S_AMOUNT) SS_AMOUNT\n" + 
						"FROM (\n" + 
						"  SELECT\n" + 
						"        SUPPLIER_CODE,\n" + 
						"        SUPPLIER_NAME,\n" + 
						"        SUM(NVL(IN_AMOUNT,0)) - SUM(NVL(RETURN_AMOUNT,0)) + SUM(NVL(SETTLE_AMOUNT,0)) IRS_AMOUNT,\n" + 
						"        SUM(NVL(INVOICE_AMOUNT,0)) I_AMOUNT,\n" + 
						"        SUM(NVL(SETTLE_AMOUNT,0)) S_AMOUNT\n" + 
						"  FROM PT_BU_SUP_INVOICE_SUMMARY\n" + 
						" WHERE STATUS = 100201\n" + 
						"  GROUP BY SUPPLIER_CODE, SUPPLIER_NAME\n" + 
						") TAB WHERE " + conditions;
		rs = this.factory.select(sql, pageManager);
		return rs;
	}
	
	/**
	 * 
	 * queryDownInfo: 查询需要下载的数据
	 * @author fuxiao
	 * Date:2014年10月23日
	 *
	 */
	public QuerySet queryDownInfo(String conditions) throws Exception {
		return this.factory.select(null, 
									"SELECT\n" +
									"      SUPPLIER_CODE,\n" + 
									"      SUPPLIER_NAME,\n" + 
									"      IRS_AMOUNT,\n" + 
									"      I_AMOUNT,\n" + 
									"      S_AMOUNT,\n" + 
									"      (IRS_AMOUNT - S_AMOUNT) SS_AMOUNT\n" + 
									"FROM (\n" + 
									"  SELECT\n" + 
									"        SUPPLIER_CODE,\n" + 
									"        SUPPLIER_NAME,\n" + 
									"        SUM(NVL(IN_AMOUNT,0)) - SUM(NVL(RETURN_AMOUNT,0)) + SUM(NVL(SETTLE_AMOUNT,0)) IRS_AMOUNT,\n" + 
									"        SUM(NVL(INVOICE_AMOUNT,0)) I_AMOUNT,\n" + 
									"        SUM(NVL(SETTLE_AMOUNT,0)) S_AMOUNT\n" + 
									"  FROM PT_BU_SUP_INVOICE_SUMMARY\n" + 
									" WHERE STATUS = 100201\n" + 
									"  GROUP BY SUPPLIER_CODE, SUPPLIER_NAME\n" + 
									") TAB WHERE " + conditions
					);
	}
}
	