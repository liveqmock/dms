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
 * ClassName: StocksInQueryDao 
 * Function: 入库单查询Dao
 * date: 2014年10月23日 下午2:12:29
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class StocksInQueryDao extends BaseDAO {
	
	public static final StocksInQueryDao getInstance(ActionContext ac){
		StocksInQueryDao dao = new StocksInQueryDao();
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
    	
    	// 判断用户是否为军品用户
//    			if(this.checkUserIsAM(user)){
//    				conditions += " AND W.WAREHOUSE_TYPE = 100102 ";
//    			} else {
//    				conditions += " AND W.WAREHOUSE_TYPE <> 100102 ";
//    			}
    			
    			if(conditions.indexOf("PART_CODE") != -1 || conditions.indexOf("PART_NAME") != -1){
    				conditions += " AND EXISTS (\n" +
    								"    SELECT 1 FROM PT_BU_STOCK_IN_DTL D WHERE D.IN_ID = T.IN_ID";
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
    			
    			if(conditions.indexOf("DISTRIBUTION_NO") != -1){
    				String tempStr = conditions.substring(conditions.indexOf("DISTRIBUTION_NO"), conditions.indexOf("'", conditions.indexOf("DISTRIBUTION_NO") + 22) + 1);
    				conditions = conditions.replace(" AND " + tempStr, "");
    				conditions += " AND EXISTS (\n" +
    						"    SELECT 1 FROM PT_BU_PCH_ORDER_SPLIT_DTL D, PT_BU_PCH_ORDER_SPLIT S " +
    						"      WHERE D.SPLIT_ID = S.SPLIT_ID AND S.SPLIT_NO = T.ORDER_NO " + " AND " + tempStr + " ) ";
    			}
    	
    	String sql = 
				"SELECT\n" +
				"  IN_ID,\n" + 
				"  IN_NO,\n" + 
				"  ORDER_NO,\n" + 
				"(\n" +
				"  SELECT SUM(D.PLAN_AMOUNT)\n" + 
				"    FROM PT_BU_STOCK_IN_DTL D\n" + 
				"   WHERE D.IN_ID = T.IN_ID\n" + 
				") PLAN_AMOUNT," +
				"CASE\n" +
				"   WHEN IN_TYPE = 201303 THEN\n" + 
				"        (SELECT TO_CHAR(SUM(NVL(AD.SALE_PRICE, 0) * NVL(D.IN_AMOUNT, 0)))\n" + 
				"          FROM PT_BU_RETURN_APPLY     A,\n" + 
				"               PT_BU_RETURN_APPLY_DTL AD,\n" + 
				"               PT_BU_STOCK_IN_DTL     D\n" + 
				"         WHERE A.RETURN_ID = AD.RETURN_ID\n" + 
				"          AND AD.PART_ID = D.PART_ID\n" + 
				"          AND D.IN_ID = T.IN_ID\n" + 
				"          AND A.RETURN_ID = T.ORDER_ID)\n" + 
				"   ELSE ''\n" + 
				"END SALE_AMOUNT," +
				"CASE\n" +
				"  WHEN IN_TYPE = 201303 THEN\n" + 
				"   (SELECT O.ONAME\n" + 
				"      FROM PT_BU_RETURN_APPLY A, TM_ORG O\n" + 
				"     WHERE A.RETURN_ID = T.ORDER_ID\n" + 
				"       AND A.ORG_ID = O.ORG_ID)\n" + 
				"  ELSE\n" + 
				"   ''\n" + 
				"END RETURN_NAME," +
				"  W.WAREHOUSE_NAME,\n" + 
				"  (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = IN_TYPE) IN_TYPE,\n" + 
				"  IN_DATE,\n" + 
				"  SUPPLIER_NAME,\n" + 
				"  (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = PRINT_STATUS) PRINT_STATUS,\n" + 
				"  PRINT_DATE,\n" + 
				"  (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = IN_STATUS) IN_STATUS,\n" + 
				"  (SELECT PERSON_NAME FROM TM_USER WHERE ACCOUNT=T.PRINT_MAN) PRINT_MAN\n" + 
				"FROM PT_BU_STOCK_IN T, PT_BA_WAREHOUSE W\n" + 
				"WHERE T.WAREHOUSE_ID = W.WAREHOUSE_ID\n" + 
				"AND T.IN_STATUS = 201502 AND T.STATUS = 100201" +
				" AND " + conditions +
				" ORDER BY IN_ID";
    	return factory.select(null, sql);
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
					"SELECT\n" +
					"  IN_ID,\n" + 
					"  IN_NO,\n" + 
					"  ORDER_NO,\n" + 
					"(\n" +
					"  SELECT SUM(D.PLAN_PRICE*D.IN_AMOUNT)\n" + 
					"    FROM PT_BU_STOCK_IN_DTL D\n" + 
					"   WHERE D.IN_ID = T.IN_ID\n" + 
					") PLAN_AMOUNT," +
					"CASE\n" +
					"   WHEN IN_TYPE = 201303 THEN\n" + 
					"        (SELECT TO_CHAR(SUM(NVL(AD.SALE_PRICE, 0) * NVL(D.IN_AMOUNT, 0)))\n" + 
					"          FROM PT_BU_RETURN_APPLY     A,\n" + 
					"               PT_BU_RETURN_APPLY_DTL AD,\n" + 
					"               PT_BU_STOCK_IN_DTL     D\n" + 
					"         WHERE A.RETURN_ID = AD.RETURN_ID\n" + 
					"          AND AD.PART_ID = D.PART_ID\n" + 
					"          AND D.IN_ID = T.IN_ID\n" + 
					"          AND A.RETURN_ID = T.ORDER_ID)\n" + 
					"   ELSE ''\n" + 
					"END SALE_AMOUNT," +
					"CASE\n" +
					"  WHEN IN_TYPE = 201303 THEN\n" + 
					"   (SELECT O.ONAME\n" + 
					"      FROM PT_BU_RETURN_APPLY A, TM_ORG O\n" + 
					"     WHERE A.RETURN_ID = T.ORDER_ID\n" + 
					"       AND A.ORG_ID = O.ORG_ID)\n" + 
					"  ELSE\n" + 
					"   ''\n" + 
					"END RETURN_NAME," +
					"  W.WAREHOUSE_NAME,\n" + 
					"  IN_TYPE,\n" + 
					"  IN_DATE,\n" + 
					"  SUPPLIER_NAME,\n" + 
					"  PRINT_STATUS,\n" + 
					"  PRINT_DATE,\n" + 
					"  IN_STATUS,\n" + 
					"  T.PRINT_MAN\n" + 
					"FROM PT_BU_STOCK_IN T, PT_BA_WAREHOUSE W\n" + 
					"WHERE T.WAREHOUSE_ID = W.WAREHOUSE_ID\n" + 
					"AND T.IN_STATUS = 201502 AND T.STATUS = 100201" +
					" AND " + conditions +
					" ORDER BY IN_ID";
		rs =  this.factory.select(sql, pageManager);
		rs.setFieldDic("IN_TYPE", "RKLX");	// 入库类型
		rs.setFieldDic("PRINT_STATUS", "DYZT");	// 是否打印
		rs.setFieldDateFormat("IN_DATE", "yyyy-MM-dd HH:mm:ss");
		rs.setFieldDateFormat("PRINT_DATE", "yyyy-MM-dd HH:mm:ss");
		rs.setFieldUserID("PRINT_MAN");
		return rs;
	}
	
	/**
	 * 
	 * queryList:新增配件代码配件名称查询条件
	 * @author fuxiao
	 * Date:2014年11月14日
	 *
	 */
	public BaseResultSet queryList(PageManager pageManager, String conditions, User user, boolean isSub) throws Exception{
		
//		// 判断用户是否为军品用户
//		if(this.checkUserIsAM(user)){
//			conditions += " AND W.WAREHOUSE_TYPE = 100102 ";
//		} else {
//			conditions += " AND W.WAREHOUSE_TYPE <> 100102 ";
//		}
		
		if(conditions.indexOf("PART_CODE") != -1 || conditions.indexOf("PART_NAME") != -1){
			conditions += " AND EXISTS (\n" +
							"    SELECT 1 FROM PT_BU_STOCK_IN_DTL D WHERE D.IN_ID = T.IN_ID";
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
		
		if(conditions.indexOf("DISTRIBUTION_NO") != -1){
			String tempStr = conditions.substring(conditions.indexOf("DISTRIBUTION_NO"), conditions.indexOf("'", conditions.indexOf("DISTRIBUTION_NO") + 22) + 1);
			conditions = conditions.replace(" AND " + tempStr, "");
			conditions += " AND EXISTS (\n" +
					"    SELECT 1 FROM PT_BU_PCH_ORDER_SPLIT_DTL D, PT_BU_PCH_ORDER_SPLIT S " +
					"      WHERE D.SPLIT_ID = S.SPLIT_ID AND S.SPLIT_NO = T.ORDER_NO " + " AND " + tempStr + " ) ";
		}
		return this.queryList(pageManager, conditions, user);
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
	 * queryStockInfoById:根据InId查询入库单主信息
	 * @author fuxiao
	 * Date:2014年10月23日
	 *
	 */
	public QuerySet queryStockInfoById(String inId) throws SQLException{
		String sql = 
				"SELECT IN_ID, IN_NO, ORDER_NO, WAREHOUSE_NAME, IN_TYPE, IN_DATE, SUPPLIER_NAME, PRINT_STATUS, PRINT_DATE, PRINT_MAN, STATUS FROM (\n" +
				"  SELECT\n" + 
				"    IN_ID,\n" + 
				"    IN_NO,\n" + 
				"    ORDER_NO,\n" + 
				"    WAREHOUSE_NAME,\n" + 
				"    IN_TYPE,\n" + 
				"    TO_CHAR(IN_DATE, 'YYYY-MM-DD HH24:MI:SS') IN_DATE,\n" + 
				"    SUPPLIER_NAME,\n" + 
				"    PRINT_STATUS,\n" + 
				"    TO_CHAR(PRINT_DATE, 'YYYY-MM-DD HH24:MI:SS') PRINT_DATE,\n" + 
				"    (SELECT U.PERSON_NAME FROM TM_USER U WHERE U.ACCOUNT = T.PRINT_MAN) PRINT_MAN,\n" + 
				"    T.STATUS\n" + 
				"  FROM PT_BU_STOCK_IN T\n" + 
				"  ORDER BY T.IN_NO\n" + 
				") WHERE IN_ID = ?";
		return this.factory.select(new Object[]{ inId }, sql);
	}
	
	/**
	 * 
	 * queryStockInfoById:查询入库单详细信息
	 * @author fuxiao
	 * Date:2014年10月23日
	 *
	 */
	public BaseResultSet queryStockInfoDetailsById(PageManager pageManage, String conditions) throws Exception {
		String sql = 
				"  SELECT DETAIL_ID,\n" + 
				"         IN_ID,\n" + 
				"         PART_ID,\n" + 
				"         PART_CODE,\n" + 
				"         PART_NAME,\n" + 
				"         IN_AMOUNT,\n" + 
				"         REMARKS,\n" + 
				"         IN_NO,\n" + 
				"         PCH_PRICE,\n" + 
				"         PCH_AMOUNT,\n" + 
				"         PLAN_PRICE,\n" + 
				"         PLAN_AMOUNT,\n" + 
				"         UNIT,\n" + 
				"         SUPPLIER_ID\n" + 
				"    FROM PT_BU_STOCK_IN_DTL WHERE "+conditions+" AND NVL(IN_AMOUNT,0)>0 ORDER BY IN_ID\n";
		return this.factory.select(sql, pageManage);
	}

}
