package com.org.dms.dao.part.storageMng.search;

import java.util.HashMap;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 
 * ClassName: StockInAndOutQueryDao 
 * Function: 配件出入库统计表
 * date: 2014年10月31日 下午1:59:24
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class StockInAndOutQueryDao extends BaseDAO {
	
	public static final StockInAndOutQueryDao getInstance(ActionContext ac){
		StockInAndOutQueryDao dao = new StockInAndOutQueryDao();
		ac.setDBFactory(dao.factory);
		return dao;
	}
	
	/**
	 * 
	 * getString: 生成查询SQL
	 * 这个查询SQL比较特殊，需要特殊处理
	 * @author fuxiao
	 * Date:2014年10月31日
	 * @throws Exception 
	 *
	 */
	public String getString(RequestWrapper requestWrapper) throws Exception{
		HashMap<String, String> hm = RequestUtil.getValues(requestWrapper);
		String inOutDateBegin = hm.get("IN_OUT_DATE_B");
		String inOutDateEnd = hm.get("IN_OUT_DATE_E");
		String partCode = hm.get("PART_CODE");
		String partName = hm.get("PART_NAME");
		String conditions = " 1 = 1 " + (partCode != null && !"".equals(partCode) ? " AND I.PART_CODE LIKE '%"+partCode+"%'" : "") +
										(partName != null && !"".equals(partName) ? " AND I.PART_NAME LIKE '%"+partName+"%'" : "") ;
		String sql = 
				"SELECT\n" +
				"        I.PART_ID, I.PART_CODE, I.PART_NAME, I.PART_NO,\n" + 
				"        NVL(T1.OUT_AMOUNT, 0) OUT_AMOUNT, NVL(T1.IN_AMOUNT, 0) IN_AMOUNT, NVL(T1.OUT_NO_COUNT, 0) OUT_NO_COUNT,\n" + 
				"        NVL(T1.IN_NO_COUNT, 0) IN_NO_COUNT, W.WAREHOUSE_NAME, K.USER_NAME\n" + 
				"FROM (\n" + 
				"     SELECT\n" + 
				"            CASE\n" + 
				"              WHEN OUT_TAB.PART_ID IS NULL THEN IN_TAB.PART_ID\n" + 
				"              ELSE OUT_TAB.PART_ID\n" + 
				"            END AS PART_ID, OUT_AMOUNT, IN_AMOUNT, OUT_NO_COUNT, IN_NO_COUNT\n" + 
				"     FROM (\n" + 
				"         SELECT SOD.PART_ID, SUM(SOD.OUT_AMOUNT) OUT_AMOUNT, COUNT(SO.OUT_NO) OUT_NO_COUNT\n" + 
				"         FROM\n" + 
				"               PT_BU_STOCK_OUT_DTL SOD, PT_BU_STOCK_OUT SO\n" + 
				"         WHERE 1 = 1 " + (inOutDateBegin != null && !"".equals(inOutDateBegin) ? " AND SO.OUT_DATE >= TO_DATE('"+inOutDateBegin+"','YYYY-MM-DD HH24:MI:SS')" : "") + 
										  (inOutDateEnd != null && !"".equals(inOutDateEnd) ? " AND SO.OUT_DATE <= TO_DATE('"+inOutDateEnd+" 23:59:59','YYYY-MM-DD HH24:MI:SS')" : "") +
				"				AND SOD.OUT_ID = SO.OUT_ID AND SO.OUT_STATUS = '201602' \n" + 
				"         GROUP BY SOD.PART_ID\n" + 
				"     ) OUT_TAB FULL JOIN (\n" + 
				"         SELECT SID.PART_ID, SUM(SID.IN_AMOUNT) IN_AMOUNT, COUNT(SI.IN_NO) IN_NO_COUNT\n" + 
				"           FROM PT_BU_STOCK_IN_DTL  SID, PT_BU_STOCK_IN SI\n" + 
				"         WHERE 1 = 1 "  + (inOutDateBegin != null && !"".equals(inOutDateBegin) ? " AND SI.IN_DATE >= TO_DATE('"+inOutDateBegin+"','YYYY-MM-DD HH24:MI:SS')" : "") + 
										   (inOutDateEnd != null && !"".equals(inOutDateEnd) ? " AND SI.IN_DATE <= TO_DATE('"+inOutDateEnd+" 23:59:59','YYYY-MM-DD HH24:MI:SS')" : "") +
				"				AND SID.IN_ID = SI.IN_ID AND SI.IN_STATUS = '201502' \n" + 
				"         GROUP BY SID.PART_ID\n" + 
				"    ) IN_TAB ON OUT_TAB.PART_ID = IN_TAB.PART_ID\n" + 
				") T1 , PT_BA_INFO I, PT_BA_WAREHOUSE W, PT_BA_WAREHOUSE_KEEPER K\n" + 
				"WHERE " + conditions +
				"	   AND T1.PART_ID = I.PART_ID\n" + 
				"      AND I.PART_ID = K.PART_ID\n" + 
				"      AND K.WAREHOUSE_ID = W.WAREHOUSE_ID\n" + 
				"      AND W.WAREHOUSE_TYPE = '100101'\n" + 
				"      AND W.WAREHOUSE_STATUS = '100201'\n" + 
				"ORDER BY I.PART_ID";
		return sql;
	}
	
	/**
	 * 
	 * queryList: 查询表单查询
	 * @author fuxiao
	 * Date:2014年10月23日
	 *
	 */
	public BaseResultSet queryList(PageManager pageManager, String sql) throws Exception{
		return this.factory.select(sql, pageManager);
	}
	
	/**
	 * 
	 * queryDownInfo: 查询需要下载的数据
	 * @author fuxiao
	 * Date:2014年10月23日
	 *
	 */
	public QuerySet queryDownInfo(String sql) throws Exception {
		return this.factory.select(null, sql);
	}
}
	