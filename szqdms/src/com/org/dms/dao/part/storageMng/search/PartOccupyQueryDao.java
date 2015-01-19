package com.org.dms.dao.part.storageMng.search;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.mvc.context.ActionContext;

/**
 * 
 * ClassName: PartOccupyQueryDao 
 * Function: 配件占用查询
 * date: 2014年11月18日 下午11:17:24
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class PartOccupyQueryDao extends BaseDAO {
	
	public static final PartOccupyQueryDao getInstance(ActionContext ac){
		PartOccupyQueryDao dao = new PartOccupyQueryDao();
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
					"SELECT\n" +
					"  PART_ID, PART_CODE, PART_NAME, OCCUPY_AMOUNT, POSITION_NAME, AREA_NAME,\n" + 
					"  ORD.ORDER_NO, ORD.ORDER_STATUS, ORD.ORDER_TYPE, ORD.APPLY_DATE, ORD.ORG_CODE, ORD.ORG_NAME \n" + 
					"FROM (\n" + 
					"SELECT I.PART_ID, I.PART_CODE, I.PART_NAME, G.OCCUPY_AMOUNT,\n" + 
					"       D.POSITION_NAME, D.AREA_NAME, \n" + 
					"       CASE\n" + 
					"         WHEN (A.ORDER_TYPE = 203710 OR A.ORDER_TYPE = 203709 OR A.ORDER_TYPE = 203707) AND NVL(A.DIR_SOURCE_ORDER_ID,0) <> 0 THEN\n" + 
					"           (SELECT SO.ORDER_ID FROM PT_BU_SALE_ORDER SO WHERE SO.ORDER_ID = A.ORDER_ID)\n" + 
					"         ELSE A.ORDER_ID\n" + 
					"       END AS ORDER_ID\n" + 
					"  FROM PT_BU_STOCK_OCCUPY_LOG G, PT_BU_SALE_ORDER A, PT_BU_STOCK_DTL D, PT_BA_INFO I\n" + 
					" WHERE G.ORDER_ID = A.ORDER_ID\n" + 
					"  AND G.STOCK_ID = D.STOCK_ID\n" + 
					"  AND G.PART_ID = I.PART_ID\n" + 
					"  AND A.ORDER_STATUS = '202203'\n" + 
					"  AND A.SHIP_STATUS < 204803\n" + 
					"  AND EXISTS (\n" + 
					"             SELECT 1\n" + 
					"              FROM PT_BU_SALE_ORDER_CHECK B\n" + 
					"              WHERE A.ORDER_ID = B.ORDER_ID\n" + 
					"                AND B.CHECK_RESULT = '202203'\n" + 
					"             )\n" + 
					") T1, PT_BU_SALE_ORDER ORD WHERE T1.ORDER_ID = ORD.ORDER_ID AND " + conditions +
					"  ORDER BY PART_ID";
		rs =  this.factory.select(sql, pageManager);
		rs.setFieldDic("ORDER_TYPE", "DDLX");
		rs.setFieldDic("ORDER_STATUS", "DDZT");
		rs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
		rs.setFieldUserID("PRINT_MAN");
		return rs;
	}

}
