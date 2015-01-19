package com.org.dms.dao.part.storageMng.search;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.mvc.context.ActionContext;

/**
 * 
 * ClassName: SummaryDtlQueryDao 
 * Function: 付款查询明细
 * date: 2014年10月30日 下午9:17:00
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class SummaryDtlQueryDao extends BaseDAO {
	
	public static final SummaryDtlQueryDao getInstance(ActionContext ac){
		SummaryDtlQueryDao dao = new SummaryDtlQueryDao();
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
				"SELECT TO_DATE(INVOICE_MONTH, 'yyyy-MM') INVOICE_MONTH,\n" +
				"       SU.SUPPLIER_CODE,\n" + 
				"       SU.SUPPLIER_NAME,\n" + 
				"       ACCOUNT_TYPE,\n" + 
				"       SETTLE_AMOUNT\n" + 
				"  FROM PT_BU_SUP_INVOICE_SUMMARY S, PT_BA_SUPPLIER SU " +
				"  WHERE SU.SUPPLIER_ID = S.SUPPLIER_ID AND S.SETTLE_AMOUNT>0 AND S.STATUS = " + DicConstant.YXBS_01 + " AND " + conditions;
		rs = this.factory.select(sql, pageManager);
		rs.setFieldDic("ACCOUNT_TYPE", "ZJZHLX");
		rs.setFieldDateFormat("INVOICE_MONTH", "yyyy-MM");
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
						"SELECT TO_CHAR(TO_DATE(INVOICE_MONTH, 'yyyy-MM'), 'YYYY-MM') INVOICE_MONTH,\n" +
						"       SU.SUPPLIER_CODE,\n" + 
						"       SU.SUPPLIER_NAME,\n" + 
						"       (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = ACCOUNT_TYPE) ACCOUNT_TYPE,\n" + 
						"       SETTLE_AMOUNT\n" + 
						"  FROM PT_BU_SUP_INVOICE_SUMMARY S, PT_BA_SUPPLIER SU WHERE SU.SUPPLIER_ID = S.SUPPLIER_ID AND S.STATUS = " + DicConstant.YXBS_01 + " AND " + conditions
					);
	}
}
	