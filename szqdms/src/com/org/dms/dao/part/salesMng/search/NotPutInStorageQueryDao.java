package com.org.dms.dao.part.salesMng.search;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.mvc.context.ActionContext;

/**
 * 
 * ClassName: NotPutInStorageQueryDao 
 * Function: 未入库订单汇总
 * date: 2014年11月3日 下午4:13:53
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class NotPutInStorageQueryDao extends BaseDAO {
	
	public static final NotPutInStorageQueryDao getInstance(ActionContext ac){
		NotPutInStorageQueryDao dao = new NotPutInStorageQueryDao();
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
					"        ORG.CODE ORG_CODE,\n" + 
					"        ORG.ONAME ORG_NAME,\n" + 
					"        T.ORDER_NO,\n" + 
					"        T.REAL_AMOUNT,\n" + 
					"        CASE\n" + 
					"          WHEN ORG.ORG_TYPE = 200005  THEN T.WAREHOUSE_NAME\n" + 
					"          ELSE (SELECT OT.ONAME FROM TM_ORG OT WHERE OT.ORG_ID = ORG.PID)\n" + 
					"        END AS LAST_AREA,\n" + 
					"        (SELECT O.OUT_DATE FROM PT_BU_STOCK_OUT O WHERE O.ORDER_ID = T.ORDER_ID) OUT_DATE\n" + 
					"  FROM PT_BU_SALE_ORDER T, TM_ORG ORG\n" + 
					" WHERE T.ORG_ID = ORG.ORG_ID\n" + 
					"   AND T.ORDER_STATUS = '202206'\n" + 
					"   AND T.SHIP_STATUS = '204806'" + " AND " + conditions + 
					" ORDER BY T.ORDER_ID";
		rs = this.factory.select(sql, pageManager);
		rs.setFieldDateFormat("OUT_DATE", "yyyy-MM-dd HH:mm:ss");
		return rs;
	}
	
	/**
	 * 
	 * queryDownInfo: 查询需要下载的数据
	 * @author fuxiao
	 * Date:2014年10月23日
	 *
	 */
	public QuerySet queryDownInfo(String conditions) throws Exception {
		String sql = 
				"SELECT\n" +
						"        ORG.CODE ORG_CODE,\n" + 
						"        ORG.ONAME ORG_NAME,\n" + 
						"        T.ORDER_NO,\n" + 
						"        T.REAL_AMOUNT,\n" + 
						"        CASE\n" + 
						"          WHEN ORG.ORG_TYPE = 200005  THEN T.WAREHOUSE_NAME\n" + 
						"          ELSE (SELECT OT.ONAME FROM TM_ORG OT WHERE OT.ORG_ID = ORG.PID)\n" + 
						"        END AS LAST_AREA,\n" + 
						"        (SELECT O.OUT_DATE FROM PT_BU_STOCK_OUT O WHERE O.ORDER_ID = T.ORDER_ID) OUT_DATE\n" + 
						"  FROM PT_BU_SALE_ORDER T, TM_ORG ORG\n" + 
						" WHERE T.ORG_ID = ORG.ORG_ID\n" + 
						"   AND T.ORDER_STATUS = '202206'\n" + 
						"   AND T.SHIP_STATUS = '204806'" + " AND " + conditions + 
						" ORDER BY T.ORDER_ID";
		return this.factory.select(null, sql);
	}
}
	