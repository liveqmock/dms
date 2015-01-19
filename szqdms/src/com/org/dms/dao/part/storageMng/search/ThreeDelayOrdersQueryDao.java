package com.org.dms.dao.part.storageMng.search;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 
 * ClassName: ThreeDelayOrdersQueryDao 
 * Function: 三包延期订单查询
 * date: 2014年11月19日 下午10:35:59
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class ThreeDelayOrdersQueryDao extends BaseDAO {
	
	public static final ThreeDelayOrdersQueryDao getInstance(ActionContext ac){
		ThreeDelayOrdersQueryDao dao = new ThreeDelayOrdersQueryDao();
		ac.setDBFactory(dao.factory);
		return dao;
	}
	
	/**
	 * 
	 * queryList:三包延期订单查询
	 * @author fuxiao
	 * Date:2014年11月19日
	 *
	 */
	public BaseResultSet queryList(PageManager pageManager, String conditions, User user) throws Exception{
		BaseResultSet rs = null;
		String sql = 
					"SELECT T.ORDER_ID, T.ORDER_NO, T.APPLY_DATE, T.ORG_CODE, T.ORG_NAME, T.ORDER_AMOUNT,\n" +
					"       TD.PART_CODE, TD.PART_NAME, TD.ORDER_COUNT, W.WAREHOUSE_CODE, W.WAREHOUSE_NAME,\n" + 
					"       S.AMOUNT, S.OCCUPY_AMOUNT, S.AVAILABLE_AMOUNT\n" + 
					"  FROM PT_BU_SALE_ORDER T, PT_BU_SALE_ORDER_DTL TD, PT_BU_STOCK S, PT_BA_WAREHOUSE W\n" + 
					" WHERE T.ORDER_ID = TD.ORDER_ID\n" + 
					"   AND S.WAREHOUSE_ID = W.WAREHOUSE_ID\n" + 
					"   AND TD.PART_ID = S.PART_ID\n" + 
					"   AND T.IF_DELAY_ORDER = 100101\n" + 
					"   AND T.DIR_SOURCE_ORDER_ID IS NOT NULL\n" + 
					"   AND T.ORDER_TYPE='"+DicConstant.DDLX_09+"'\n" + 
					"   AND T.ORDER_STATUS = 202202\n" + 
					"   AND W.WAREHOUSE_TYPE = 100101\n" + 
					"   AND S.AVAILABLE_AMOUNT > 0 AND " +  conditions +
					"  ORDER BY T.ORDER_ID" ;
		rs =  this.factory.select(sql, pageManager);
		rs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
		return rs;
	}
	
}
