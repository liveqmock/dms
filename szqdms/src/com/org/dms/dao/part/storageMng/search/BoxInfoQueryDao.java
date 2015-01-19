package com.org.dms.dao.part.storageMng.search;

import java.sql.SQLException;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.mvc.context.ActionContext;

/**
 * 
 * ClassName: StocksOutQueryDao 
 * Function: 出库单查询Dao
 * date: 2014年10月23日 下午2:12:29
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class BoxInfoQueryDao extends BaseDAO {
	
	public static final BoxInfoQueryDao getInstance(ActionContext ac){
		BoxInfoQueryDao dao = new BoxInfoQueryDao();
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
				"       O.ORDER_ID, O.ORDER_NO, O.ORDER_TYPE, O.ORDER_STATUS,\n" + 
				"       O.ORG_CODE, O.ORG_NAME, O.APPLY_DATE,\n" + 
				"       O.ORDER_AMOUNT\n" + 
				"  FROM PT_BU_SALE_ORDER O\n" + 
				" WHERE\n" + 
				"       O.STATUS = 100201\n" + 
				"       AND O.SHIP_STATUS >= 204804\n" + 
				"       AND EXISTS (\n" + 
				"           SELECT 1 FROM PT_BU_BOX_UP U\n" + 
				"            WHERE U.ORDER_ID = O.ORDER_ID\n" + 
				"       )\n" + 
				"		AND " + conditions +
				"  ORDER BY O.ORDER_ID";
		rs =  this.factory.select(sql, pageManager);
		rs.setFieldDic("ORDER_TYPE", "DDLX");							// 订单类型
		rs.setFieldDic("ORDER_STATUS", "DDZT");							// 订单状态
		rs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");							// 申请时间
		return rs;
	}
	
    /**
     * 
     * searchOrder: 添加截取查询条件
     * @author fuxiao
     * Date:2014年11月15日
     *
     */
    public BaseResultSet queryList(PageManager pageManager, String conditions, boolean isSub) throws Exception {
		if(conditions.indexOf("PART_CODE") != -1 || conditions.indexOf("PART_NAME") != -1){
			conditions += " AND EXISTS (\n" +
							"    SELECT 1 FROM PT_BU_BOX_UP D WHERE D.ORDER_ID = O.ORDER_ID";
			if(conditions.indexOf("PART_NAME") != -1){
				String partNameSql = conditions.substring(conditions.indexOf("PART_NAME"), conditions.indexOf("'", conditions.indexOf("PART_NAME") + 15) + 1);
				conditions = conditions.replace(" AND "+partNameSql, "");
				conditions +=" AND " + partNameSql;
			}
			if(conditions.indexOf("PART_CODE") != -1){
				String partNameSql = conditions.substring(conditions.indexOf("PART_CODE"), conditions.indexOf("'", conditions.indexOf("PART_CODE") + 15) + 1);
				conditions = conditions.replace(" AND "+partNameSql, "");
				conditions +=" AND " + partNameSql;
			}
			conditions += " ) ";
		}
    	return this.queryList(pageManager, conditions);
    }
   
    /**
     * 
     * queryBoxInfoById: 装箱单信息
     * @author fuxiao
     * Date:2014年11月20日
     *
     */
    public QuerySet queryBoxInfoById(String id) throws SQLException{
    	return this.factory.select(new Object[]{id}, "SELECT\n" +
													"       O.ORDER_ID, O.ORDER_NO, " + 
													"		(SELECT DIC_VALUE FROM DIC_TREE WHERE ID = O.ORDER_TYPE) ORDER_TYPE, " + 
													"		(SELECT DIC_VALUE FROM DIC_TREE WHERE ID = O.ORDER_STATUS) ORDER_STATUS, " + 
													"       O.ORG_CODE, O.ORG_NAME, " + 
													"		TO_CHAR(APPLY_DATE, 'YYYY-MM-DD HH24:MI:SS') APPLY_DATE,\n" + 
													"       O.ORDER_AMOUNT\n" + 
													"  FROM PT_BU_SALE_ORDER O\n" + 
													" WHERE\n" + 
													"       O.STATUS = 100201\n" + 
													"       AND O.SHIP_STATUS >= 204804\n" + 
													"       AND EXISTS (\n" + 
													"           SELECT 1 FROM PT_BU_BOX_UP U\n" + 
													"            WHERE U.ORDER_ID = O.ORDER_ID\n" + 
													"       )\n" + 
													"		AND ORDER_ID = ?");
    }
    
    /**
     * 
     * queryBoxDetailsList: 装箱单详细信息
     * @author fuxiao
     * Date:2014年11月20日
     *
     */
    public BaseResultSet queryBoxDetailsListById(PageManager pageManager, String conditions) throws Exception {
		BaseResultSet rs = null;
		String sql = 
				"SELECT\n" +
    					"       O.ORDER_NO, U.ISSUE_NO, I.PART_CODE, I.PART_NAME,\n" + 
    					"       U.BOX_NO, U.COUNT,\n" + 
    					"       (\n" + 
    					"         SELECT D.ORDER_COUNT FROM PT_BU_SALE_ORDER_DTL D\n" + 
    					"                WHERE D.ORDER_ID = O.ORDER_ID\n" + 
    					"                  AND D.PART_ID = U.PART_ID\n" + 
    					"       ) ORDER_COUNT\n" + 
    					"  FROM PT_BU_BOX_UP U, PT_BU_SALE_ORDER O, PT_BA_INFO I\n" + 
    					" WHERE U.ORDER_ID = O.ORDER_ID\n" + 
    					"       AND U.PART_ID = I.PART_ID\n" + 
    					"       AND O.STATUS = 100201\n" + 
    					"       AND O.SHIP_STATUS >= 204804\n" + 
    					"		AND " + conditions +
    					"  ORDER BY O.ORDER_ID";
		rs =  this.factory.select(sql, pageManager);
		return rs;
    }
	
	/**
	 * 
	 * queryDownInfo: 查询下载信息
	 * @author fuxiao
	 * Date:2014年11月20日
	 *
	 */
	public QuerySet queryDownInfoById(String conditions) throws Exception {
		return this.factory.select(null, "SELECT\n" +
											"       O.ORDER_NO, U.ISSUE_NO, I.PART_CODE, I.PART_NAME,\n" + 
											"       U.BOX_NO, U.COUNT,\n" + 
											"       (\n" + 
											"         SELECT D.DELIVERY_COUNT FROM PT_BU_SALE_ORDER_DTL D\n" + 
											"                WHERE D.ORDER_ID = O.ORDER_ID\n" + 
											"                  AND D.PART_ID = U.PART_ID\n" + 
											"       ) DELIVERY_COUNT,T.REMARKS\n" + 
											"  FROM PT_BU_BOX_UP U, PT_BU_SALE_ORDER O, PT_BA_INFO I,PT_BU_SALE_ORDER_DTL T\n" + 
											" WHERE U.ORDER_ID = O.ORDER_ID\n" + 
											"       AND U.PART_ID = I.PART_ID\n" + 
											"       AND U.PART_ID = T.PART_ID\n" + 
											"       AND U.ORDER_ID = T.ORDER_ID\n" + 
											"       AND O.STATUS = 100201\n" + 
											"       AND O.SHIP_STATUS >= 204804\n" + 
											"		AND " + conditions+" ORDER BY BOX_NO ASC");
	}
	/**
	 * 
	 * queryDownInfo: 查询渠道名称
	 * @author fuxiao
	 * Date:2014年11月20日
	 *
	 */
	public QuerySet queryOrgName(String orderId) throws Exception {
		return this.factory.select(null, "SELECT ONAME FROM TM_ORG WHERE ORG_ID =(SELECT ORG_ID FROM PT_BU_SALE_ORDER WHERE ORDER_ID="+orderId+")");
	}
}
