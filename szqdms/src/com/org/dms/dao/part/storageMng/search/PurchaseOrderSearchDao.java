package com.org.dms.dao.part.storageMng.search;

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
 * ClassName: PurchaseOrderSearchDao 
 * Function: 采购订单查询Dao
 * date: 2014年9月11日 下午2:33:00
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 
 */
public class PurchaseOrderSearchDao extends BaseDAO{
	
	public static final PurchaseOrderSearchDao getInstance(ActionContext ac){
		PurchaseOrderSearchDao dao = new PurchaseOrderSearchDao();
		ac.setDBFactory(dao.factory);
		return dao;
	}
	
	/**
	 * 
	 * queryOrderInfo:查询订单信息
	 * @author fuxiao
	 * @param page
	 * @param conditions
	 * @param user
	 * @return
	 * @throws SQLException
	 * @since JDK 1.6
	 */
	public BaseResultSet queryPurchaseOrderInfo(PageManager page, String conditions, User user) throws SQLException{
		StringBuilder sb = new StringBuilder(conditions);
		sb.append(" AND STATUS = '" +DicConstant.YXBS_01+ "'");
		page.setFilter(sb.toString());
		String sql = "SELECT\n" +
						"      PURCHASE_ID,\n" + 
						"      ORDER_NO,\n" + 
						"      SELECT_MONTH,\n" + 
						"      PURCHASE_TYPE,\n" + 
						"      PURCHASE_CATEGORY,\n" + 
						"      ORDER_TYPE,\n" + 
						"      ORDER_STATUS,\n" + 
						"      SUPPLIER_CODE,\n" + 
						"      SUPPLIER_NAME\n" + 
						"FROM PT_BU_PCH_ORDER";
		BaseResultSet brs = this.factory.select(sql, page);
		
		// 月度
		brs.setFieldDic("SELECT_MONTH", "yyyy-MM");
		
		// 采购类型
		brs.setFieldDic("PURCHASE_TYPE", DicConstant.CGLX);
		
		// 采购类别
		brs.setFieldDic("PURCHASE_CATEGORY", DicConstant.CGLB);
		
		// 采购订单类型
		brs.setFieldDic("ORDER_TYPE", DicConstant.CGDDLX);
		
		// 采购订单状态
		brs.setFieldDic("ORDER_STATUS", DicConstant.CGDDZT);
		
		return brs;
		
	}
	
	
	/**
	 * 
	 * queryPurchaseOrderInfoByPurchaseId:根据采购订单ID查询采购订单详细信息
	 * @author fuxiao
	 * @param purchaseId
	 * @return
	 * @since JDK 1.6
	 */
	public QuerySet queryPurchaseOrderInfoByPurchaseId(String purchaseId){
		// TODO
		return null;
	}
	
	
	/**
	 * 
	 * queryOrderPartInfo: 根据采购订单ID查询订单中配件详情
	 * @author fuxiao
	 * @param page
	 * @param conditions
	 * @param user
	 * @return
	 * @since JDK 1.6
	 * @throws SQLException 
	 */
	public BaseResultSet queryOrderPartInfo(PageManager page,String purchaseId, String conditions, User user) throws SQLException{
		StringBuilder sb = new StringBuilder(conditions);
		sb.append(" AND PURCHASE_ID = " + purchaseId);
		page.setFilter(sb.toString());
		String sql = "SELECT\n" +
						"     PURCHASE_ID,PART_CODE,PART_NAME,UNIT,MIN_PACK,PCH_COUNT,PCH_PRICE,PCH_AMOUNT,DELIVERY_CYCLE\n" + 
						"FROM (\n" + 
						"    SELECT\n" + 
						"          T2.PURCHASE_ID,\n" + 
						"          T2.PART_CODE,\n" + 
						"          T2.PART_NAME,\n" + 
						"          T3.UNIT,\n" + 
						"          T3.MIN_PACK,\n" + 
						"          T2.PCH_COUNT,\n" + 
						"          T2.PCH_PRICE,\n" + 
						"          T2.PCH_AMOUNT,\n" + 
						"          T2.DELIVERY_CYCLE\n" + 
						"    FROM PT_BU_PCH_ORDER_DTL T2,\n" + 
						"         PT_BA_INFO T3\n" + 
						"    WHERE T2.PART_ID = T3.PART_ID\n" + 
						")";
		BaseResultSet rs =  this.factory.select(sql, page);
		rs.setFieldDic("UNIT", DicConstant.JLDW);
		return rs;
	}
	
}
