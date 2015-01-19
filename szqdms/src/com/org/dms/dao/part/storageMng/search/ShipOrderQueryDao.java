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
 * ClassName: ShipOrderQueryDao 
 * Function: 发货单查询Dao
 * date: 2014年10月25日 上午11:11:59
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class ShipOrderQueryDao extends BaseDAO {
	
	public static final ShipOrderQueryDao getInstance(ActionContext ac){
		ShipOrderQueryDao dao = new ShipOrderQueryDao();
		ac.setDBFactory(dao.factory);
		return dao;
	}
	
	/**
	 * 
	 * checkUserIsAM: 判断用户的org是否属于军品，true：是，false: 不是
	 * @author fuxiao
	 * Date:2014年11月14日
	 *
	 */
	public boolean checkUserIsAM(User user) throws SQLException{
		String res = this.factory.select("SELECT F_IS_AM("+user.getOrgId()+") FROM DUAL")[0][0];
		return res.equals("1");
	}
	
	/**
	 * 
	 * queryList: 查询表单查询
	 * @author fuxiao
	 * Date:2014年10月23日
	 *
	 */
	public BaseResultSet queryList(PageManager pageManager, String conditions, User user) throws Exception{
		BaseResultSet rs = null;
		
		// 判断是否为军品用户,添加订单类型限制
		if(this.checkUserIsAM(user)){
			pageManager.setFilter(
					"		SH.SHIP_ID = SD.SHIP_ID\n" +
					"       AND SD.ORDER_ID = O.ORDER_ID\n" + 
					"       AND SH.SHIP_ID = CA.SHIP_ID(+)\n" + 
					"       AND SH.SHIP_STATUS >= " + DicConstant.FYDZT_04 + 
					"		AND O.ORDER_TYPE = "+DicConstant.DDLX_08 +
					" 		AND " + conditions + 
					" ORDER BY SH.SHIP_DATE DESC,O.ORDER_NO DESC" );
		} else {
			pageManager.setFilter(
					"		SH.SHIP_ID = SD.SHIP_ID\n" +
					"       AND SD.ORDER_ID = O.ORDER_ID\n" + 
					"       AND SH.SHIP_ID = CA.SHIP_ID(+)\n" + 
					"       AND SH.SHIP_STATUS >= " + DicConstant.FYDZT_04 + 
					"		AND O.ORDER_TYPE <> "+DicConstant.DDLX_08 +
					" 		AND " + conditions + 
					" ORDER BY SH.SHIP_DATE DESC,O.ORDER_NO DESC" );
		}
		
		String sql = 
					"SELECT DISTINCT\n" +
					"       SH.SHIP_NO,\n" + 
					"       SH.SHIP_DATE,\n" + 
					"       SH.SHIP_STATUS,\n" + 
					"       CA.RECEIPT_NO,\n" + 
					"       O.ORDER_NO,\n" + 
					"       (\n" + 
					"          SELECT SUM(I.UNIT_PRICE * I.DELIVERY_COUNT)\n" + 
					"            FROM PT_BU_SALE_ORDER_DTL I\n" + 
					"           WHERE I.ORDER_ID = O.ORDER_ID\n" + 
					"       ) ORDER_AMOUNT,\n" + 
					"       O.ORG_ID,\n" + 
					"       O.REAL_AMOUNT,\n" + 
					"       O.ORG_NAME,\n" + 
					"       SH.CARRIER_NAME,\n" + 
					"       SH.CARRIER_CODE,\n" + 
					"       O.DELIVERY_ADDR,\n" + 
					"       CA.LICENSE_PLATE\n" + 
					"  FROM PT_BU_ORDER_SHIP         SH,\n" + 
					"       PT_BU_ORDER_SHIP_DTL     SD,\n" + 
					"       PT_BU_SALE_ORDER         O,\n" + 
					"       PT_BU_ORDER_SHIP_CARRIER CA";
		rs = this.factory.select(sql, pageManager);
		rs.setFieldDic("SHIP_STATUS", "FYDZT");
		rs.setFieldDateFormat("SHIP_DATE", "yyyy-MM-dd HH:mm:ss");
		return rs;
	}
	
	/**
	 * 
	 * queryDownInfo: 查询需要下载的数据
	 * @author fuxiao
	 * Date:2014年10月23日
	 *
	 */
	public QuerySet queryDownInfo(String conditions, User user) throws Exception {
		
		String sql  = "SELECT DISTINCT\n" +
				"       SH.SHIP_NO,\n" + 
				"       SH.SHIP_DATE,\n" + 
				"       DC.DIC_VALUE SHIP_STATUS,\n" + 
				"       CA.RECEIPT_NO,\n" + 
				"       O.ORDER_NO,\n" + 
				"       (\n" + 
				"          SELECT SUM(I.UNIT_PRICE * I.DELIVERY_COUNT)\n" + 
				"            FROM PT_BU_SALE_ORDER_DTL I\n" + 
				"           WHERE I.ORDER_ID = O.ORDER_ID\n" + 
				"       ) ORDER_AMOUNT,\n" +
				"       O.REAL_AMOUNT,\n" + 
				"       O.ORG_ID,\n" + 
				"       O.ORG_NAME,\n" + 
				"       SH.CARRIER_NAME,\n" + 
				"       SH.CARRIER_CODE,\n" + 
				"       O.DELIVERY_ADDR,\n" + 
				"       CA.LICENSE_PLATE\n" + 
				"  FROM PT_BU_ORDER_SHIP         SH,\n" + 
				"       PT_BU_ORDER_SHIP_DTL     SD,\n" + 
				"       PT_BU_SALE_ORDER         O,\n" + 
				"       DIC_TREE                 DC,\n" + 
				"       PT_BU_ORDER_SHIP_CARRIER CA WHERE " +
				"		SH.SHIP_ID = SD.SHIP_ID\n" +
				"       AND SD.ORDER_ID = O.ORDER_ID\n" + 
				"       AND SH.SHIP_STATUS = DC.ID\n" + 
				"       AND SH.SHIP_ID = CA.SHIP_ID(+)\n" + 
				"       AND SH.SHIP_STATUS >= " + DicConstant.FYDZT_04;
		// 判断是否为军品用户,添加订单类型限制
		if(this.checkUserIsAM(user)){
			sql += " AND O.ORDER_TYPE = "+DicConstant.DDLX_08;
		} else {
			sql += " AND O.ORDER_TYPE <> "+DicConstant.DDLX_08;
		}
				
		sql += " 		AND " + conditions +  " ORDER BY SH.SHIP_DATE DESC,O.ORDER_NO DESC";
		
		return this.factory.select(null, sql);
	}
}
