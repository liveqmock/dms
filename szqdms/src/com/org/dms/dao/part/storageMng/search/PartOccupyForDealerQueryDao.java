package com.org.dms.dao.part.storageMng.search;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 
 * @ClassName: PartOccupyForDealerQueryDao
 * @Description: 经销商订单占用查询
 * @author: fuxiao
 * @date: 2014年12月17日 下午8:01:49
 */
public class PartOccupyForDealerQueryDao extends BaseDAO {
	
	public static final PartOccupyForDealerQueryDao getInstance(ActionContext ac){
		PartOccupyForDealerQueryDao dao = new PartOccupyForDealerQueryDao();
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
	public BaseResultSet queryList(PageManager pageManager, String conditions, User user) throws Exception{
		BaseResultSet rs = null;
		String sql = 
					"SELECT I.PART_ID, I.PART_CODE, I.PART_NAME, T1.OCCUPY_AMOUNT, T1.ORDER_NO,  T1.ORDER_TYPE\n" +
					"  FROM (SELECT OD.PART_ID,\n" + 
					"               SUM(OD.AUDIT_COUNT) OCCUPY_AMOUNT,\n" + 
					"               O.ORDER_NO,\n" + 
					"               '销售订单' AS ORDER_TYPE\n" + 
					"          FROM PT_BU_SALE_ORDER O, PT_BU_SALE_ORDER_DTL OD\n" + 
					"         WHERE O.ORDER_ID = OD.ORDER_ID\n" + 
					"           AND O.WAREHOUSE_ID = " + user.getOrgId() + "\n" + 
					"           AND O.ORDER_STATUS = 202203\n" + 
					"           AND O.STATUS = 100201\n" + 
					"           AND OD.AUDIT_COUNT > 0\n" + 
					"         GROUP BY OD.PART_ID, O.ORDER_NO\n" + 
					"        UNION ALL\n" + 
					"        SELECT SD.PART_ID, SUM(SD.SALE_COUNT), S.SALE_NO, '实销订单'\n" + 
					"          FROM PT_BU_REAL_SALE_DTL SD, PT_BU_REAL_SALE S\n" + 
					"         WHERE SD.SALE_ID = S.SALE_ID\n" + 
					"           AND S.ORG_ID = " + user.getOrgId() + "\n" + 
					"           AND S.SALE_STATUS = 205001\n" + 
					"           AND S.STATUS = 100201\n" + 
					"         GROUP BY SD.PART_ID, S.SALE_NO\n" + 
					"        UNION ALL\n" + 
					"        SELECT AD.PART_ID, SUM(AD.RETURN_COUNT), A.RETURN_NO, '退货单'\n" + 
					"          FROM PT_BU_RETURN_APPLY A, PT_BU_RETURN_APPLY_DTL AD\n" + 
					"         WHERE A.RETURN_ID = AD.RETURN_ID\n" + 
					"           AND A.ORG_ID = " + user.getOrgId() + "\n" + 
					"           AND A.APPLY_SATUS = 202403\n" + 
					"           AND A.STATUS = 100201\n" + 
					"         GROUP BY AD.PART_ID, A.RETURN_NO" +
					"		UNION ALL\n" +
					"		SELECT D.PART_ID,\n" + 
					"       	   SUM(D.APPLY_COUNTS),\n" + 
					"              A.OVERSTOCK_NO,\n" + 
					"              '积压件调拨单'\n" + 
					"  		FROM PT_BU_OVERSTOCK_PARTS_DTL D, PT_BU_OVERSTOCK_PARTS_APPLY A\n" + 
					" 		WHERE D.OVERSTOCK_ID = A.OVERSTOCK_ID\n" + 
					"   		  AND A.APPLY_STATUS = 306203\n" + 
					"   		  AND A.STATUS = 100201\n" + 
					"   		  AND A.OUT_ORG_ID = " + user.getOrgId() + "\n" + 
					" 		GROUP BY D.PART_ID, A.OVERSTOCK_NO" +
					" 		) T1,\n" + 
					"       PT_BA_INFO I\n" + 
					" WHERE T1.PART_ID = I.PART_ID" + " AND " + conditions + " ORDER BY I.PART_ID";
		rs =  this.factory.select(sql, pageManager);
		return rs;
	}

}
