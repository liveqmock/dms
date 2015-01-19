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
 * ClassName: PartRequirementQueryDao 
 * Function: 配件需求统计
 * date: 2014年10月29日 下午3:38:50
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class PartRequirementQueryDao extends BaseDAO {
	
	public static final PartRequirementQueryDao getInstance(ActionContext ac){
		PartRequirementQueryDao dao = new PartRequirementQueryDao();
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
		String sql = 
					"SELECT PART_CODE, PART_NAME, PART_NO, ORDER_COUNT, REAL_COUNT, decode(ORDER_COUNT,0,0,ROUND(REAL_COUNT/ORDER_COUNT, 4))*100||'%' RATE FROM (\n" +
					"  SELECT\n" + 
					"     I.PART_CODE, I.PART_NAME, I.PART_NO,\n" + 
					"     SUM(DECODE(O.IF_DELAY_ORDER, 100102, OD.ORDER_COUNT,0)) ORDER_COUNT,\n" + 
					"     SUM(NVL(OD.DELIVERY_COUNT,0)) REAL_COUNT\n" + 
					"    FROM PT_BU_SALE_ORDER O, PT_BU_SALE_ORDER_DTL OD, PT_BA_INFO I\n" + 
					"   WHERE " + 
					" 	  O.ORDER_ID = OD.ORDER_ID\n" + 
					"     AND I.PART_ID = OD.PART_ID\n" + 
					"     AND O.STATUS = 100201\n" + 
					"     AND O.IF_CHANEL_ORDER = 100102\n" + 
					"     AND O.SHIP_STATUS >= 204806\n";
		// 判断是否为军品用户,添加订单类型限制
		if(this.checkUserIsAM(user)){
			sql += " AND O.ORDER_TYPE = "+DicConstant.DDLX_08;
		} else {
			sql += " AND O.ORDER_TYPE <> "+DicConstant.DDLX_08;
		}
		sql +=	    "	  AND "+ conditions + 
					"     GROUP BY I.PART_CODE, I.PART_NAME, I.PART_NO\n" + 
					"     ORDER BY I.PART_CODE\n" + 
					")";
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
	public QuerySet queryDownInfo(String conditions, User user) throws Exception {
		
		String sql = "SELECT PART_CODE, PART_NAME, PART_NO, ORDER_COUNT, REAL_COUNT, decode(ORDER_COUNT,0,0,ROUND(REAL_COUNT/ORDER_COUNT, 4))*100||'%' RATE FROM (\n" +
				"  SELECT\n" + 
				"     I.PART_CODE, I.PART_NAME, I.PART_NO,\n" + 
				"     SUM(DECODE(O.IF_DELAY_ORDER, 100102, OD.ORDER_COUNT,0)) ORDER_COUNT,\n" + 
				"     SUM(NVL(OD.DELIVERY_COUNT,0)) REAL_COUNT\n" + 
				"    FROM PT_BU_SALE_ORDER O, PT_BU_SALE_ORDER_DTL OD, PT_BA_INFO I\n" + 
				"   WHERE " + 
				" 	  O.ORDER_ID = OD.ORDER_ID\n" + 
				"     AND I.PART_ID = OD.PART_ID\n" + 
				"     AND O.STATUS = 100201\n" + 
				"     AND O.IF_CHANEL_ORDER = 100102\n" + 
				"     AND O.SHIP_STATUS >= 204806\n"; 
		
		// 判断是否为军品用户,添加订单类型限制
		if(this.checkUserIsAM(user)){
			sql += " AND O.ORDER_TYPE = "+DicConstant.DDLX_08;
		} else {
			sql += " AND O.ORDER_TYPE <> "+DicConstant.DDLX_08;
		}
		
		sql +=  "     AND " + conditions + 
				"     GROUP BY I.PART_CODE, I.PART_NAME, I.PART_NO\n" + 
				"     ORDER BY I.PART_CODE\n" + 
				")";
		return this.factory.select(null, sql);
	}
}
