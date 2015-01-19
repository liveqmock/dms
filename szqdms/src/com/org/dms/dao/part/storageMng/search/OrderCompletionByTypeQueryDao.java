package com.org.dms.dao.part.storageMng.search;


import java.util.Map;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 
 * ClassName: OrderCompletionByTypeQueryDao 
 * Function: 配送中心订单完成情况统计(按类别)
 * date: 2014年11月1日 下午6:46:24
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class OrderCompletionByTypeQueryDao extends BaseDAO {
	
	public static final OrderCompletionByTypeQueryDao getInstance(ActionContext ac){
		OrderCompletionByTypeQueryDao dao = new OrderCompletionByTypeQueryDao();
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
	public BaseResultSet queryList(RequestWrapper requestWrapper, PageManager pageManager, String conditions) throws Exception{
		BaseResultSet rs = null;
		String sql = this.filterSql(conditions, requestWrapper);
		rs = this.factory.select(sql, pageManager);
		return rs;
	}
	
	/**
	 * 
	 * @Title: filterConditions
	 * @Description: Sql过滤
	 * @param conditions
	 * @param requestWrapper
	 * @return
	 * @return: String
	 */
	public String filterSql(String conditions, RequestWrapper requestWrapper){
		Map<String, String> map = RequestUtil.getValues(requestWrapper);
		String beginDate = map.get("CLOSE_DATE_B") == null ? "" : map.get("CLOSE_DATE_B");
		String endDate = map.get("CLOSE_DATE_E") == null ? "" : map.get("CLOSE_DATE_E");
		String sql = 
				"SELECT\n" +
				"   O.CODE ORG_CODE, O.ONAME ORG_NAME, NVL(LT_MM_AMOUNT, 0) LT_MM_AMOUNT, NVL(LT_YYYY_AMOUNT, 0) LT_YYYY_AMOUNT,\n" + 
				"   NVL(LHQ_MM_AMOUNT, 0) LHQ_MM_AMOUNT, NVL(LHQ_YYYY_AMOUNT, 0) LHQ_YYYY_AMOUNT, NVL(YP_MM_AMOUNT, 0) YP_MM_AMOUNT, NVL(YP_YYYY_AMOUNT, 0) YP_YYYY_AMOUNT,\n" + 
				"   NVL(LX_MM_AMOUNT, 0) LX_MM_AMOUNT,  NVL(LX_YYYY_AMOUNT, 0) LX_YYYY_AMOUNT\n" + 
				"FROM (\n" + 
				"  SELECT\n" + 
				"     CASE\n" + 
				"       WHEN T1.ORG_ID IS NULL THEN T2.ORG_ID\n" + 
				"       ELSE T1.ORG_ID\n" + 
				"     END AS ORG_ID, LT_MM_AMOUNT, LT_YYYY_AMOUNT,\n" + 
				"     LHQ_MM_AMOUNT, LHQ_YYYY_AMOUNT, YP_MM_AMOUNT,\n" + 
				"     YP_YYYY_AMOUNT, LX_MM_AMOUNT, LX_YYYY_AMOUNT\n" + 
				"  FROM (\n" + 
				"    SELECT\n" + 
				"           O.ORG_ID,\n" + 
				"           NVL(SUM(DECODE(T.TYPE_CODE, 'LTZF', OD.DELIVERY_COUNT, 0) * I.SALE_PRICE), 0) LT_YYYY_AMOUNT,\n" + 
				"           NVL(SUM(DECODE(T.TYPE_CODE, 'LHQZF', OD.DELIVERY_COUNT, 0) * I.SALE_PRICE), 0) LHQ_YYYY_AMOUNT,\n" + 
				"           NVL(SUM(DECODE(T.TYPE_CODE, 'YPZF', OD.DELIVERY_COUNT, 0) * I.SALE_PRICE), 0) YP_YYYY_AMOUNT,\n" + 
				"           NVL(SUM(DECODE(T.TYPE_CODE, 'LXZF', OD.DELIVERY_COUNT, 0) * I.SALE_PRICE), 0) LX_YYYY_AMOUNT\n" + 
				"    FROM PT_BU_SALE_ORDER O, PT_BU_SALE_ORDER_DTL OD, PT_BA_INFO I, PT_BA_DIRECT_TYPE T\n" + 
				"    WHERE O.ORDER_ID = OD.ORDER_ID\n" + 
				"      AND OD.PART_ID = I.PART_ID\n" + 
				"      AND I.DIRECT_TYPE_ID = T.TYPE_ID\n" + 
				"      AND O.STATUS = 100201\n" + 
				"      AND O.ORDER_STATUS = 202206\n" + 
				"      AND O.APPLY_DATE >= TRUNC(SYSDATE, 'YYYY')\n"; 
		
		if(!"".equals(beginDate)){
			sql += "   AND O.CLOSE_DATE >= TO_DATE('" + beginDate + "', 'YYYY-MM-DD')\n";
		}
		if(!"".equals(endDate)){
			sql += "   AND O.CLOSE_DATE <= TO_DATE('" + endDate + "', 'YYYY-MM-DD')\n";
		}
		
			sql +=	"    GROUP BY O.ORG_ID\n" + 
				"  ) T1 FULL JOIN (\n" + 
				"      SELECT\n" + 
				"           O.ORG_ID,\n" + 
				"           NVL(SUM(DECODE(T.TYPE_CODE, 'LTZF', OD.DELIVERY_COUNT, 0) * I.SALE_PRICE), 0) LT_MM_AMOUNT,\n" + 
				"           NVL(SUM(DECODE(T.TYPE_CODE, 'LHQZF', OD.DELIVERY_COUNT, 0) * I.SALE_PRICE), 0) LHQ_MM_AMOUNT,\n" + 
				"           NVL(SUM(DECODE(T.TYPE_CODE, 'YPZF', OD.DELIVERY_COUNT, 0) * I.SALE_PRICE), 0) YP_MM_AMOUNT,\n" + 
				"           NVL(SUM(DECODE(T.TYPE_CODE, 'LXZF', OD.DELIVERY_COUNT, 0) * I.SALE_PRICE), 0) LX_MM_AMOUNT\n" + 
				"    FROM PT_BU_SALE_ORDER O, PT_BU_SALE_ORDER_DTL OD, PT_BA_INFO I, PT_BA_DIRECT_TYPE T\n" + 
				"    WHERE O.ORDER_ID = OD.ORDER_ID\n" + 
				"      AND OD.PART_ID = I.PART_ID\n" + 
				"      AND I.DIRECT_TYPE_ID = T.TYPE_ID\n" + 
				"      AND O.STATUS = 100201\n" + 
				"      AND O.ORDER_STATUS = 202206\n" + 
				"      AND O.APPLY_DATE >= TRUNC(SYSDATE, 'MM')\n";
				
		if(!"".equals(beginDate)){
			sql += "   AND O.CLOSE_DATE >= TO_DATE('" + beginDate + "', 'YYYY-MM-DD')\n";
		}
		if(!"".equals(endDate)){
			sql += "   AND O.CLOSE_DATE <= TO_DATE('" + endDate + "', 'YYYY-MM-DD')\n";
		}
			sql +=	"    GROUP BY O.ORG_ID\n" + 
				"  ) T2 ON T1.ORG_ID = T2.ORG_ID\n" + 
				") TAB, TM_ORG O\n" + 
				"WHERE " + conditions + " AND TAB.ORG_ID = O.ORG_ID\n" + 
				"AND O.ORG_TYPE = 200005 AND O.STATUS = 100201";
		return sql;
	}
	
	/**
	 * 
	 * queryDownInfo: 查询需要下载的数据
	 * @author fuxiao
	 * Date:2014年10月23日
	 *
	 */
	public QuerySet queryDownInfo(RequestWrapper requestWrapper, String conditions) throws Exception {
		return this.factory.select(null, this.filterSql(conditions, requestWrapper));
	}
}
	