package com.org.dms.dao.part.storageMng.search;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.mvc.context.ActionContext;

/**
 * 
 * ClassName: OrderCompletionQueryDao 
 * Function: 配送中心订单完成情况统计
 * date: 2014年11月1日 上午11:09:08
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class OrderCompletionQueryDao extends BaseDAO {
	
	public static final OrderCompletionQueryDao getInstance(ActionContext ac){
		OrderCompletionQueryDao dao = new OrderCompletionQueryDao();
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
				"SELECT S_ID,\n" +
						"       ORG_ID,\n" + 
						"       ORG_NAME,\n" + 
						"       ORG_CODE,\n" + 
						"       YYYY_ORDER_AMOUNT,\n" + 
						"       YYYY_REAL_AMOUNT,\n" + 
						"       MM_ORDER_AMOUNT,\n" + 
						"       MM_REAL_AMOUNT,\n" + 
						"       MM_RATE,\n" + 
						"       WW_ORDER_AMOUNT,\n" + 
						"       WW_REAL_AMOUNT,\n" + 
						"       WW_RATE,\n" + 
						"       PART_AMOUNT,\n" + 
						"       BALANCE_AMOUNT,\n" + 
						"       BA_AMOUNT\n" + 
						"  FROM PT_BU_PART_CENTER_STATISTICS WHERE " + conditions;
					
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
		String sql = 
				"SELECT S_ID,\n" +
						"       ORG_ID,\n" + 
						"       ORG_NAME,\n" + 
						"       ORG_CODE,\n" + 
						"       YYYY_ORDER_AMOUNT,\n" + 
						"       YYYY_REAL_AMOUNT,\n" + 
						"       MM_ORDER_AMOUNT,\n" + 
						"       MM_REAL_AMOUNT,\n" + 
						"       MM_RATE,\n" + 
						"       WW_ORDER_AMOUNT,\n" + 
						"       WW_REAL_AMOUNT,\n" + 
						"       WW_RATE,\n" + 
						"       PART_AMOUNT,\n" + 
						"       BALANCE_AMOUNT,\n" + 
						"       BA_AMOUNT\n" + 
						"  FROM PT_BU_PART_CENTER_STATISTICS WHERE " + conditions;
		return this.factory.select(null, sql);
	}
}
	