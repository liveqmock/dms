package com.org.dms.dao.part.storageMng.overstockParts;

import java.sql.SQLException;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 
 * ClassName: 经销商积压件功能Dao
 * date: 2014年9月16日 上午9:24:54
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 
 */
public class DealerOverstockPartDao extends BaseDAO{

	public static final DealerOverstockPartDao getInstance(ActionContext ac){
		DealerOverstockPartDao dao = new DealerOverstockPartDao();
		ac.setDBFactory(dao.factory);
		return dao;
	}
	
	/**
	 * 
	 * queryStorageParts:查询登录用户相关仓库配件
	 * @author fuxiao
	 * Date:2014年9月16日上午9:26:49
	 * @param page
	 * @param conditions
	 * @param user
	 * @return
	 * @throws Exception 
	 */
	public BaseResultSet queryStorageParts(PageManager page, String conditions, User user) throws Exception{
		StringBuffer sb = new StringBuffer(conditions);
		sb.append(" AND STATUS = '"+ DicConstant.YXBS_01 +"' AND  ORG_ID = '"+ user.getOrgId() +"' ");
		page.setFilter(sb.toString());
		String strSql = 
				        "SELECT\n" +
						"  STOCK_ID, ORG_ID, PART_CODE, PART_NAME, PART_NO, IS_OVERSTOCK, AMOUNT, OCCUPY_AMOUNT, AVAILABLE_AMOUNT, STORAGE_STATUS, SUPPLIER_CODE, SUPPLIER_NAME, STATUS\n" + 
						"FROM (\n" + 
						"  SELECT\n" + 
						"         STOCK_ID, ORG_ID, PART_CODE, PART_NAME, PART_NO, IS_OVERSTOCK, AMOUNT, OCCUPY_AMOUNT, AVAILABLE_AMOUNT, STORAGE_STATUS, SUPPLIER_CODE, SUPPLIER_NAME, STATUS\n" + 
						"  FROM PT_BU_DEALER_STOCK \n" + 
						"  ORDER BY STOCK_ID\n" + 
						") T ";
		BaseResultSet bs = this.factory.select(strSql, page);
		
		// 是否为积压件
		bs.setFieldDic("IS_OVERSTOCK", "SF");
		
		// 库存状态:
		bs.setFieldDic("STORAGE_STATUS", "KCZT");
		return bs;
	}
	
	/**
	 * 
	 * queryOverstockPart: 查询全部的积压件
	 * @author fuxiao
	 * Date:2014年10月16日上午11:23:11
	 * @param page
	 * @param conditions
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet queryOverstockPart(PageManager page, String conditions, User user) throws Exception{
		String strSql = 
						"  SELECT\n" + 
						"         STOCK_ID, ORG_CODE, ORG_NAME, PART_CODE, PART_NAME, PART_NO, IS_OVERSTOCK, AMOUNT, OCCUPY_AMOUNT, AVAILABLE_AMOUNT, STORAGE_STATUS, SUPPLIER_CODE, SUPPLIER_NAME, STATUS\n" + 
						"  FROM PT_BU_DEALER_STOCK \n" + 
						"  WHERE IS_OVERSTOCK = '" + DicConstant.SF_01 + "'" +
						" AND STATUS = '"+ DicConstant.YXBS_01 +"'" + " AND " + conditions + 
						"  ORDER BY STOCK_ID" ;
		BaseResultSet bs = this.factory.select(strSql, page);
		// 是否为积压件
		bs.setFieldDic("IS_OVERSTOCK", "SF");
		// 库存状态:
		bs.setFieldDic("STORAGE_STATUS", "KCZT");
		return bs;
	}
	
	/**
	 * 
	 * updateOverstockPartMark: 标记配件是否为积压件
	 * @author fuxiao
	 * Date:2014年9月16日上午9:42:02
	 * @param stockIds
	 * @param status
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public boolean updateOverstockPartMark(String stockIds,String status, User user) throws SQLException {
		String sql = "UPDATE PT_BU_DEALER_STOCK\n" +
						"SET IS_OVERSTOCK = ?,\n" + 
						"    UPDATE_USER = ?,\n" + 
						"    UPDATE_TIME = SYSDATE\n" + 
						"WHERE STOCK_ID IN ("+stockIds+")";
		return this.factory.update(sql, new Object[]{status, user.getAccount()});
	}
	
	
	/**
	 * 
	 * queryOverstockPartsAll: 积压件调拨查询:查询可以调拨的零件(status:有效,是否为积压件：是，库存状态:正常 ),且不是自家的库存的配件
	 * @author fuxiao
	 * Date:2014年9月16日下午2:11:26
	 * @param page
	 * @param conditions
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public BaseResultSet queryOverstockPartsAll(PageManager page, String conditions, User user) throws SQLException{
		StringBuilder sb = new StringBuilder(conditions);
		sb.append(" AND STATUS = '" + DicConstant.YXBS_01 + "' AND IS_OVERSTOCK = '" + DicConstant.SF_01 + "' AND STORAGE_STATUS = '"+DicConstant.KCZT_01+"' ");
		sb.append(" AND ORG_ID <> '" + user.getOrgId() + "'");
		page.setFilter(sb.toString());
		String strSql = "SELECT\n" +
						"      STOCK_ID,PART_CODE,PART_NAME,PART_NO,IS_OVERSTOCK,AMOUNT,OCCUPY_AMOUNT,AVAILABLE_AMOUNT,STORAGE_STATUS,SUPPLIER_CODE,SUPPLIER_NAME,SALE_PRICE,PART_TYPE,MIN_PACK,MIN_UNIT,UNIT,STATUS,ORG_ID,ORG_CODE,ORG_NAME\n" + 
						"FROM(\n" + 
						"      SELECT\n" + 
						"       S.STOCK_ID,\n" + 
						"       I.PART_CODE,\n" + 
						"       I.PART_NAME,\n" + 
						"       I.PART_NO,\n" + 
						"       S.IS_OVERSTOCK,\n" + 
						"       S.AMOUNT,\n" + 
						"       S.OCCUPY_AMOUNT,\n" + 
						"       S.AVAILABLE_AMOUNT,\n" + 
						"       S.STORAGE_STATUS,\n" + 
						"       S.SUPPLIER_CODE,\n" + 
						"       S.SUPPLIER_NAME,\n" + 
						"       I.SALE_PRICE,\n" + 
						"       I.PART_TYPE,\n" + 
						"       I.MIN_PACK,\n" + 
						"       I.MIN_UNIT,\n" + 
						"       I.UNIT,\n" + 
						"       S.STATUS,\n" + 
					    "       S.ORG_ID,\n" +
					    "       O.CODE ORG_CODE,\n" +
					    "       O.ONAME ORG_NAME\n" +
						"      FROM\n" + 
						"      PT_BU_DEALER_STOCK S,PT_BA_INFO I,TM_ORG O WHERE S.PART_ID = I.PART_ID AND S.ORG_ID = O.ORG_ID ORDER BY S.ORG_ID,S.PART_ID \n" + 
						")";
		BaseResultSet bs = this.factory.select(strSql, page);
			
		// 是否为积压件
		bs.setFieldDic("IS_OVERSTOCK", "SF");
			
		// 库存状态:
		bs.setFieldDic("STORAGE_STATUS", "KCZT");
		
		// 配件类型
		bs.setFieldDic("PART_TYPE", "PJLB");
		
		// 计量单位
		bs.setFieldDic("UNIT", "JLDW");
		
		return bs;
	}
	
}
