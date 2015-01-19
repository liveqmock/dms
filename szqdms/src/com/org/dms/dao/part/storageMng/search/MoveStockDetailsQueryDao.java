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
 * ClassName: MoveStockDetailsQueryDao 
 * Function: 移库数据统计查询Dao
 * date: 2014年11月25日 下午2:07:02
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class MoveStockDetailsQueryDao extends BaseDAO {
	
	public static final MoveStockDetailsQueryDao getInstance(ActionContext ac){
		MoveStockDetailsQueryDao dao = new MoveStockDetailsQueryDao();
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
	public String getString(RequestWrapper requestWrapper, boolean isDown) throws Exception{
		HashMap<String, String> hm = RequestUtil.getValues(requestWrapper);
		String inOutDateBegin = hm.get("MOVE_DATE_B");
		String inOutDateEnd = hm.get("MOVE_DATE_E");
		String partCode = hm.get("PART_CODE");
		String partName = hm.get("PART_NAME");
		String outWarehouseCode = hm.get("OUT_WAREHOUSE_CODE");
		String inWarehouseCode = hm.get("IN_WAREHOUSE_CODE");
		String conditions = " 1 = 1 " + (partCode != null && !"".equals(partCode) ? " AND I.PART_CODE LIKE '%"+partCode+"%'" : "") +
										(partName != null && !"".equals(partName) ? " AND I.PART_NAME LIKE '%"+partName+"%'" : "") +  
										(inOutDateBegin != null && !"".equals(inOutDateBegin) ? " AND O.MOVE_DATE >= TO_DATE('"+inOutDateBegin+"','YYYY-MM-DD')" : "") +
										(inOutDateEnd != null && !"".equals(inOutDateEnd) ? " AND O.MOVE_DATE <= TO_DATE('"+inOutDateEnd+" 23:59:59','YYYY-MM-DD HH24:MI:SS')" : "") +
										(outWarehouseCode != null && !"".equals(outWarehouseCode) ? " AND O.WAREHOUSE_CODE LIKE '"+outWarehouseCode+"'" : "") +
										(inWarehouseCode != null && !"".equals(inWarehouseCode) ? " AND EXISTS ( SELECT 1 FROM PT_BA_WAREHOUSE T WHERE T.WAREHOUSE_ID = O.RECEIVE_WAREHOUSE AND T.WAREHOUSE_CODE LIKE '"+ inWarehouseCode +"')" : "") ;
		if(!isDown){
			return   "SELECT D.PART_ID, I.PART_CODE, I.PART_NAME, O.OUT_NO,\n" +
						"       O.WAREHOUSE_ID OUT_WAREHOUSE_ID,\n" + 
						"       O.WAREHOUSE_CODE OUT_WAREHOUSE_CODE,\n" + 
						"       O.Warehouse_Name OUT_WAREHOUSE_NAME,\n" + 
						"       D.OUT_AMOUNT,D.PLAN_PRICE,D.OUT_AMOUNT * D.PLAN_PRICE PLAN_AMOUNT,\n" + 
						"       O.RECEIVE_WAREHOUSE IN_WAREHOUSE_ID,\n" + 
						"       (SELECT W.WAREHOUSE_CODE FROM PT_BA_WAREHOUSE W WHERE W.WAREHOUSE_ID = O.RECEIVE_WAREHOUSE) IN_WAREHOUSE_CODE,\n" + 
						"       (SELECT W.WAREHOUSE_NAME FROM PT_BA_WAREHOUSE W WHERE W.WAREHOUSE_ID = O.RECEIVE_WAREHOUSE) IN_WAREHOUSE_NAME,\n" + 
						"       O.MOVE_DATE,\n" + 
						"       D.REMARKS\n" + 
						"  FROM PT_BU_STOCK_OUT_DTL D, PT_BU_STOCK_OUT O, PT_BA_INFO I\n" + 
						" WHERE D.OUT_ID = O.OUT_ID\n" + 
						"  AND D.PART_ID = I.PART_ID\n" + 
						"  AND O.OUT_TYPE = 201403\n" + 
						"  AND O.OUT_STATUS = 201602" + " AND " + conditions;
		} else {
			return   "SELECT D.PART_ID, I.PART_CODE, I.PART_NAME, O.OUT_NO,\n" +
						"       O.WAREHOUSE_ID OUT_WAREHOUSE_ID,\n" + 
						"       O.WAREHOUSE_CODE OUT_WAREHOUSE_CODE,\n" + 
						"       O.Warehouse_Name OUT_WAREHOUSE_NAME,\n" + 
						"       D.OUT_AMOUNT,D.PLAN_PRICE,D.OUT_AMOUNT * D.PLAN_PRICE PLAN_AMOUNT,\n" + 
						"       O.RECEIVE_WAREHOUSE IN_WAREHOUSE_ID,\n" + 
						"       (SELECT W.WAREHOUSE_CODE FROM PT_BA_WAREHOUSE W WHERE W.WAREHOUSE_ID = O.RECEIVE_WAREHOUSE) IN_WAREHOUSE_CODE,\n" + 
						"       (SELECT W.WAREHOUSE_NAME FROM PT_BA_WAREHOUSE W WHERE W.WAREHOUSE_ID = O.RECEIVE_WAREHOUSE) IN_WAREHOUSE_NAME,\n" + 
						"       TO_CHAR(O.MOVE_DATE, 'YYYY-MM-DD HH24:MI:SS') MOVE_DATE,\n" + 
						"       D.REMARKS\n" + 
						"  FROM PT_BU_STOCK_OUT_DTL D, PT_BU_STOCK_OUT O, PT_BA_INFO I\n" + 
						" WHERE D.OUT_ID = O.OUT_ID\n" + 
						"  AND D.PART_ID = I.PART_ID\n" + 
						"  AND O.OUT_TYPE = 201403\n" + 
						"  AND O.OUT_STATUS = 201602" + " AND " + conditions;
			
		}
	}
	
	/**
	 * 
	 * queryList: 查询表单查询
	 * @author fuxiao
	 * Date:2014年10月23日
	 *
	 */
	public BaseResultSet queryList(PageManager pageManager, String sql) throws Exception{
		 BaseResultSet rs =  this.factory.select(sql, pageManager);
		 rs.setFieldDateFormat("MOVE_DATE", "yyyy-MM-dd HH:mm:ss");
		 return rs;
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
	