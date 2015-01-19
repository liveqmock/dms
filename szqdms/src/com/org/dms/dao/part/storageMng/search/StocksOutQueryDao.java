package com.org.dms.dao.part.storageMng.search;

import java.sql.SQLException;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
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
public class StocksOutQueryDao extends BaseDAO {
	
	public static final StocksOutQueryDao getInstance(ActionContext ac){
		StocksOutQueryDao dao = new StocksOutQueryDao();
		ac.setDBFactory(dao.factory);
		return dao;
	}
	
	/**
     * 订单查询(导出)
     *
     * @pConditions 查询条件
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet download(String conditions,User user) throws Exception {
    	
    	if(conditions.indexOf("PART_CODE") != -1 || conditions.indexOf("PART_NAME") != -1){
			conditions += " AND EXISTS (\n" +
							"    SELECT 1 FROM PT_BU_STOCK_OUT_DTL D WHERE D.OUT_ID = T.OUT_ID";
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
    	String sql = 
				"SELECT\n" +
				"   OUT_ID, OUT_NO, ORDER_NO, WAREHOUSE_NAME,\n" + 
				"   (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = OUT_TYPE) OUT_TYPE, " + 
				"   (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = OTHER_OUT_TYPE) OTHER_OUT_TYPE, " + 
				"	OUT_DATE, DEPARTEMENT_NAME,PLAN_AMOUNT,(SELECT DIC_VALUE FROM DIC_TREE WHERE ID = PRINT_STATUS) PRINT_STATUS,\n" + 
				"   PRINT_DATE, ( select person_name from tm_user where account=PRINT_MAN) PRINT_MAN\n" + 
				"FROM (" +
				"SELECT OUT_ID,\n" +
				"       OUT_NO,\n" + 
				"       ORDER_NO,\n" + 
				"       O.WAREHOUSE_NAME,\n" + 
				"       OUT_TYPE,\n" + 
				"       OTHER_OUT_TYPE,\n" + 
				"       OUT_DATE,\n" + 
				"       CASE\n" + 
				"         WHEN OUT_TYPE = 201403 THEN (SELECT W.WAREHOUSE_NAME FROM PT_BA_WAREHOUSE W WHERE W.WAREHOUSE_ID = O.RECEIVE_WAREHOUSE)\n" + 
				"         WHEN OUT_TYPE = 201404 THEN (SELECT ORD.SUPPLIER_NAME FROM PT_BU_PCH_RETURN ORD WHERE ORD.RETURN_ID = O.ORDER_ID)\n" + 
				"         ELSE (SELECT ORD.ORG_NAME FROM PT_BU_SALE_ORDER ORD WHERE ORD.ORDER_ID = O.ORDER_ID)\n" + 
				"       END AS DEPARTEMENT_NAME,\n" + 
				" (SELECT SUM(NVL(D.OUT_AMOUNT,0)*NVL(D.PLAN_PRICE,0)) FROM PT_BU_STOCK_OUT_DTL D WHERE D.OUT_ID =O.OUT_ID) PLAN_AMOUNT,"+
				"       PRINT_STATUS,\n" + 
				"       PRINT_DATE,\n" + 
				"       OUT_STATUS,\n" + 
				"       O.PRINT_MAN,\n" + 
				"       O.STATUS\n" + 
				"  FROM PT_BU_STOCK_OUT O, PT_BA_WAREHOUSE W\n" + 
				"  WHERE O.WAREHOUSE_ID = W.WAREHOUSE_ID AND O.OUT_STATUS = 201602\n" ; 
//	// 判断用户是否为军品用户
//	if(this.checkUserIsAM(user)){
//		sql += " AND W.WAREHOUSE_TYPE = 100102 ";
//	} else {
//		sql += " AND W.WAREHOUSE_TYPE <> 100102 ";
//	}
	sql += "  ORDER BY O.OUT_ID" + ") T WHERE " + conditions;
        return factory.select(null, sql.toString());
    }
	/**
	 * 
	 * queryList: 查询表单查询
	 * @author fuxiao
	 * Date:2014年10月23日
	 *
	 */
	public BaseResultSet queryList(PageManager pageManager, String conditions, User user,String part) throws Exception{
		BaseResultSet rs = null;
		String sql = 
					"SELECT\n" +
					"   OUT_ID, OUT_NO, ORDER_NO, WAREHOUSE_NAME,\n" + 
					"   OUT_TYPE, OTHER_OUT_TYPE, OUT_DATE, DEPARTEMENT_NAME, PLAN_AMOUNT, PRINT_STATUS,\n" + 
					"   OUT_AMOUNT,"+
					"   PRINT_DATE, PRINT_MAN\n" + 
					"FROM (" +
					"SELECT OUT_ID,\n" +
					"       OUT_NO,\n" + 
					"       ORDER_NO,\n" + 
					"       O.WAREHOUSE_NAME,\n" + 
					"       OUT_TYPE,\n" + 
					"       OTHER_OUT_TYPE,\n" + 
					"       OUT_DATE,\n" + 
					"       CASE\n" + 
					"         WHEN OUT_TYPE = 201403 THEN (SELECT W.WAREHOUSE_NAME FROM PT_BA_WAREHOUSE W WHERE W.WAREHOUSE_ID = O.RECEIVE_WAREHOUSE)\n" + 
					"         WHEN OUT_TYPE = 201404 THEN (SELECT ORD.SUPPLIER_NAME FROM PT_BU_PCH_RETURN ORD WHERE ORD.RETURN_ID = O.ORDER_ID)\n" + 
					"         ELSE (SELECT ORD.ORG_NAME FROM PT_BU_SALE_ORDER ORD WHERE ORD.ORDER_ID = O.ORDER_ID)\n" + 
					"       END AS DEPARTEMENT_NAME,\n" + 
					"   (SELECT SUM(OUT_AMOUNT) FROM PT_BU_STOCK_OUT_DTL WHERE OUT_ID=O.OUT_ID";
					if (!"".equals(part)) {
						sql+=part;
					}
					sql+=") OUT_AMOUNT,"+
					" (SELECT SUM(NVL(D.OUT_AMOUNT,0)*NVL(D.PLAN_PRICE,0)) FROM PT_BU_STOCK_OUT_DTL D WHERE D.OUT_ID =O.OUT_ID) PLAN_AMOUNT,"+
					"       PRINT_STATUS,\n" + 
					"       PRINT_DATE,\n" + 
					"       OUT_STATUS,\n" + 
					"       O.PRINT_MAN,\n" + 
					"       O.STATUS\n" + 
					"  FROM PT_BU_STOCK_OUT O, PT_BA_WAREHOUSE W\n" + 
					"  WHERE O.WAREHOUSE_ID = W.WAREHOUSE_ID AND O.OUT_STATUS = 201602\n" ; 
//		// 判断用户是否为军品用户
//		if(this.checkUserIsAM(user)){
//			sql += " AND W.WAREHOUSE_TYPE = 100102 ";
//		} else {
//			sql += " AND W.WAREHOUSE_TYPE <> 100102 ";
//		}
		sql += "  ORDER BY O.OUT_ID" + ") T WHERE " + conditions;
		rs =  this.factory.select(sql, pageManager);
		rs.setFieldDic("OUT_TYPE", "CKLX");		// 出库类型
		rs.setFieldDic("OTHER_OUT_TYPE", "QTCKCKLX");		// 其他出库类型
		rs.setFieldDic("PRINT_STATUS", "DYZT");	// 是否打印
		rs.setFieldDateFormat("OUT_DATE", "yyyy-MM-dd HH:mm:ss");
		rs.setFieldDateFormat("PRINT_DATE", "yyyy-MM-dd HH:mm:ss");
		rs.setFieldUserID("PRINT_MAN");
		return rs;
	}
	
	/**
	 * 
	 * queryList: 添加配件名称，配件代码模糊查询
	 * @author fuxiao
	 * Date:2014年11月14日
	 *
	 */
	public BaseResultSet queryList(PageManager pageManager, String conditions, User user, boolean isSub) throws Exception{
		
		String part = "";
		if(conditions.indexOf("PART_CODE") != -1 || conditions.indexOf("PART_NAME") != -1){
			conditions += " AND EXISTS (\n" +
							"    SELECT 1 FROM PT_BU_STOCK_OUT_DTL D WHERE D.OUT_ID = T.OUT_ID";
			if(conditions.indexOf("PART_NAME") != -1){
				String partNameSql = conditions.substring(conditions.indexOf("PART_NAME"), conditions.indexOf("'", conditions.indexOf("PART_NAME") + 15) + 1);
				conditions = conditions.replace(" AND "+partNameSql, "");
				conditions +=" AND " + partNameSql;
				part+=" AND " + partNameSql;
			}
			if(conditions.indexOf("PART_CODE") != -1){
				String partNameSql = conditions.substring(conditions.indexOf("PART_CODE"), conditions.indexOf("'", conditions.indexOf("PART_CODE") + 15) + 1);
				conditions = conditions.replace(" AND "+partNameSql, "");
				conditions +=" AND " + partNameSql;
				part+=" AND " + partNameSql;
			}
			conditions += " ) ";
		}
		return this.queryList(pageManager, conditions, user,part);
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
	 * queryStockInfoById:根据outId查询出单主信息
	 * @author fuxiao
	 * Date:2014年10月23日
	 *
	 */
	public QuerySet queryStockInfoById(String outId) throws SQLException{
		String sql = 
					"SELECT OUT_ID, OUT_NO, ORDER_NO, WAREHOUSE_NAME, OUT_TYPE, " + 
					" (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = OTHER_OUT_TYPE) OTHER_OUT_TYPE, " + 
					" OUT_DATE, DEPARTEMENT_NAME, PRINT_STATUS, PRINT_DATE, PRINT_MAN, STATUS FROM (\n" +
					"  SELECT OUT_ID,\n" + 
					"         OUT_NO,\n" + 
					"         ORDER_NO,\n" + 
					"         (SELECT W.WAREHOUSE_NAME FROM PT_BA_WAREHOUSE W WHERE W.WAREHOUSE_ID = O.WAREHOUSE_ID) WAREHOUSE_NAME,\n" + 
					"         OUT_TYPE,\n" + 
					"         OTHER_OUT_TYPE,\n" + 
					"         OUT_DATE,\n" + 
					"         CASE\n" + 
					"           WHEN OUT_TYPE = 201403 THEN (SELECT W.WAREHOUSE_NAME FROM PT_BA_WAREHOUSE W WHERE W.WAREHOUSE_ID = O.RECEIVE_WAREHOUSE)\n" + 
					"           WHEN OUT_TYPE = 201404 THEN (SELECT ORD.SUPPLIER_NAME FROM PT_BU_PCH_RETURN ORD WHERE ORD.RETURN_ID = O.ORDER_ID)\n" + 
					"           ELSE (SELECT ORD.ORG_NAME FROM PT_BU_SALE_ORDER ORD WHERE ORD.ORDER_ID = O.ORDER_ID)\n" + 
					"         END AS DEPARTEMENT_NAME,\n" + 
					"         PRINT_STATUS,\n" + 
					"         PRINT_DATE,\n" + 
					"         (SELECT U.PERSON_NAME FROM TM_USER U WHERE U.ACCOUNT = O.PRINT_MAN) PRINT_MAN,\n" + 
					"         O.STATUS\n" + 
					"    FROM PT_BU_STOCK_OUT O\n" + 
					"    ORDER BY O.OUT_ID\n" + 
					") WHERE OUT_ID = ?";
		return this.factory.select(new Object[]{ outId }, sql);
	}
	
	/**
	 * 
	 * queryStockInfoById:查询出库单详细信息
	 * @author fuxiao
	 * Date:2014年10月23日
	 *
	 */
	public BaseResultSet queryStockInfoDetailsById(PageManager pageManage, String conditions) throws Exception {
		pageManage.setFilter(conditions + " AND T.OUT_ID = T1.OUT_ID AND T.OUT_AMOUNT>0\n"
				+ "ORDER BY PART_CODE");
		String sql = 
					"  SELECT T.OUT_ID, T.PART_CODE, T.PART_NAME, T.PLAN_PRICE, T.PLAN_AMOUNT, T.OUT_AMOUNT," + 
					"CASE\n" +
					"  WHEN T1.OUT_TYPE = 201401 OR T1.OUT_TYPE = 201402 OR T1.OUT_TYPE = 201406 THEN\n" + 
					"       (SELECT TO_CHAR(T2.SHOULD_COUNT)\n" + 
					"         FROM PT_BU_ISSUE_ORDER_DTL T2\n" + 
					"        WHERE T1.ORDER_ID = T2.ORDER_ID\n" + 
					"          AND T.PART_ID = T2.PART_ID)\n" + 
					"  ELSE ''\n" + 
					"END AS SHOULD_COUNT,"+
					" T.REMARKS\n" + 
					"    FROM PT_BU_STOCK_OUT_DTL T,PT_BU_STOCK_OUT T1\n";
		return this.factory.select(sql, pageManage);
		
	}

}
