package com.org.dms.dao.part.storageMng.search;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.mvc.context.ActionContext;

/**
 * 
 * ClassName: OrderStockOutQueryDao 
 * Function: 销售出库明细
 * date: 2014年10月30日 下午8:37:17
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class OrderStockOutQueryDao extends BaseDAO {
	
	public static final OrderStockOutQueryDao getInstance(ActionContext ac){
		OrderStockOutQueryDao dao = new OrderStockOutQueryDao();
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
						"  D.PART_CODE, D.PART_NAME, SO.WAREHOUSE_NAME , SO.ORDER_NO, SO.OUT_NO, SO.OUT_TYPE,\n" + 
						"  SO.OUT_DATE, O.CLOSE_DATE, CASE WHEN O.ORDER_STATUS =202206 THEN NVL(D.DELIVERY_COUNT,0) ELSE NVL(SOD.OUT_AMOUNT,0) END OUT_AMOUNT," +
						" CASE WHEN O.ORDER_STATUS =202206 THEN D.PLAN_PRICE ELSE SOD.PLAN_PRICE END PLAN_PRICE,\n" + 
						"  CASE WHEN O.ORDER_STATUS =202206 THEN NVL(D.DELIVERY_COUNT,0)*NVL(D.PLAN_PRICE,0) ELSE NVL(SOD.OUT_AMOUNT,0) * NVL(SOD.PLAN_PRICE,0)END PLAN_AMOUNT," +
						" D.UNIT_PRICE SALE_PRICE, " +
						"CASE WHEN O.ORDER_STATUS =202206 THEN NVL(D.DELIVERY_COUNT,0)* D.UNIT_PRICE ELSE NVL(SOD.OUT_AMOUNT,0) * D.UNIT_PRICE END SALE_AMOUNT, " +
						"O.ORG_CODE, O.ORG_NAME\n" + 
						"FROM\n" + 
						"  PT_BU_STOCK_OUT SO, PT_BU_STOCK_OUT_DTL SOD, PT_BU_SALE_ORDER O, PT_BU_SALE_ORDER_DTL D\n" + 
						"WHERE " + 
						"	   SO.OUT_ID = SOD.OUT_ID\n" + 
						"      AND SO.ORDER_ID = O.ORDER_ID\n" + 
						"      AND O.ORDER_ID = D.ORDER_ID\n" + 
						"      AND SOD.PART_ID = D.PART_ID\n" +
						"      AND SO.OUT_TYPE IN (201401, 201402, 201406)\n" + 
						// "      AND O.ORDER_STATUS = 202206\n" + 
						"      AND SO.OUT_STATUS = 201602\n" + 
						
						// 打印状态加上查询没有数据
						// "      AND SO.PRINT_STATUS = 201702\n" + 
						"      AND O.STATUS = 100201 AND " + conditions + " ORDER BY SO.OUT_DATE DESC";
		rs = this.factory.select(sql, pageManager);
		rs.setFieldDic("OUT_TYPE", "CKLX");
		rs.setFieldDateFormat("OUT_DATE", "yyyy-MM-dd HH:mm:ss");
		rs.setFieldDateFormat("CLOSE_DATE", "yyyy-MM-dd HH:mm:ss");
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
		return this.factory.select(null, 
							"SELECT\n" +
							"  D.PART_CODE, D.PART_NAME, SO.WAREHOUSE_NAME, SO.ORDER_NO, SO.OUT_NO, (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = SO.OUT_TYPE) OUT_TYPE,\n" + 
							"  TO_CHAR(SO.OUT_DATE, 'YYYY-MM-DD HH24:MI:SS') OUT_DATE, TO_CHAR(O.CLOSE_DATE, 'YYYY-MM-DD HH24:MI:SS') CLOSE_DATE, " +
							"CASE WHEN O.ORDER_STATUS =202206 THEN NVL(D.DELIVERY_COUNT,0) ELSE NVL(SOD.OUT_AMOUNT,0) END OUT_AMOUNT, " +
							"CASE WHEN O.ORDER_STATUS =202206 THEN D.PLAN_PRICE ELSE SOD.PLAN_PRICE END PLAN_PRICE,\n" + 
							"  CASE WHEN O.ORDER_STATUS =202206 THEN NVL(D.DELIVERY_COUNT,0)*NVL(D.PLAN_PRICE,0) ELSE NVL(SOD.OUT_AMOUNT,0) * NVL(SOD.PLAN_PRICE,0)END PLAN_AMOUNT," +
							" D.UNIT_PRICE SALE_PRICE, CASE WHEN O.ORDER_STATUS =202206 THEN NVL(D.DELIVERY_COUNT,0)* D.UNIT_PRICE ELSE NVL(SOD.OUT_AMOUNT,0) * D.UNIT_PRICE END SALE_AMOUNT," +
							" O.ORG_CODE, O.ORG_NAME\n" + 
							"FROM\n" + 
							"  PT_BU_STOCK_OUT SO, PT_BU_STOCK_OUT_DTL SOD, PT_BU_SALE_ORDER O, PT_BU_SALE_ORDER_DTL D\n" + 
							"WHERE " + 
							"	   SO.OUT_ID = SOD.OUT_ID\n" + 
							"      AND SO.ORDER_ID = O.ORDER_ID\n" + 
							"      AND O.ORDER_ID = D.ORDER_ID\n" + 
							"      AND SOD.PART_ID = D.PART_ID\n" +
							"      AND SO.OUT_TYPE IN (201401, 201402, 201406)\n" + 
							// "      AND O.ORDER_STATUS = 202206\n" + 
							"      AND SO.OUT_STATUS = 201602\n" + 
							// "      AND SO.PRINT_STATUS = 201702\n" + 
							"      AND O.STATUS = 100201 AND " + conditions + " ORDER BY SO.OUT_DATE DESC"
					);
	}
	
	public BaseResultSet getAmount(PageManager page, String conditions) throws Exception
    {
        BaseResultSet bs = null;
//        StringBuffer sql= new StringBuffer();
//        sql.append("SELECT SUM(A.PLAN_AMOUNT ) PLAN_AMOUNT, SUM(A.SALE_AMOUNT) SALE_AMOUNT FROM\n" );
//        sql.append("(SELECT CASE\n" );
//        sql.append("         WHEN O.ORDER_STATUS = 202206 THEN\n" );
//        sql.append("          NVL(D.DELIVERY_COUNT, 0) * NVL(D.PLAN_PRICE, 0)\n" );
//        sql.append("         ELSE\n" );
//        sql.append("          NVL(SOD.OUT_AMOUNT, 0) * NVL(SOD.PLAN_PRICE, 0)\n" );
//        sql.append("       END PLAN_AMOUNT,\n" );
//        sql.append("       CASE\n" );
//        sql.append("         WHEN O.ORDER_STATUS = 202206 THEN\n" );
//        sql.append("          NVL(D.DELIVERY_COUNT, 0) * D.UNIT_PRICE\n" );
//        sql.append("         ELSE\n" );
//        sql.append("          NVL(SOD.OUT_AMOUNT, 0) * D.UNIT_PRICE\n" );
//        sql.append("       END SALE_AMOUNT\n" );
//        sql.append("  FROM PT_BU_STOCK_OUT      SO,\n" );
//        sql.append("       PT_BU_STOCK_OUT_DTL  SOD,\n" );
//        sql.append("       PT_BU_SALE_ORDER     O,\n" );
//        sql.append("       PT_BU_SALE_ORDER_DTL D\n" );
//        sql.append(" WHERE SO.OUT_ID = SOD.OUT_ID\n" );
//        sql.append("   AND SO.ORDER_ID = O.ORDER_ID\n" );
//        sql.append("   AND O.ORDER_ID = D.ORDER_ID\n" );
//        sql.append("   AND SOD.PART_ID = D.PART_ID\n" );
//        sql.append("   AND SO.OUT_TYPE IN (201401, 201402, 201406)\n" );
//        sql.append("   AND SO.OUT_STATUS = 201602\n" );
//        sql.append("   AND O.STATUS = 100201\n" );
//        sql.append("   AND "+conditions+"\n" );
//        sql.append("   AND O.CLOSE_DATE >= to_date('2014-11-21', 'YYYY-MM-DD')\n" );
//        sql.append(") A");
        StringBuffer sql= new StringBuffer();
        sql.append("select sum(SALE_AMOUNT) SALE_AMOUNT,  sum(PLAN_AMOUNT) PLAN_AMOUNT from (\n" );
        sql.append("SELECT CASE\n" );
        sql.append("         WHEN O.ORDER_STATUS = 202206 THEN\n" );
        sql.append("          NVL(D.DELIVERY_COUNT, 0) * D.UNIT_PRICE\n" );
        sql.append("         ELSE\n" );
        sql.append("          NVL(SOD.OUT_AMOUNT, 0) * D.UNIT_PRICE\n" );
        sql.append("       END SALE_AMOUNT,\n" );
        sql.append("       CASE\n" );
        sql.append("         WHEN O.ORDER_STATUS = 202206 THEN\n" );
        sql.append("          NVL(D.DELIVERY_COUNT, 0) * NVL(D.PLAN_PRICE, 0)\n" );
        sql.append("         ELSE\n" );
        sql.append("          NVL(SOD.OUT_AMOUNT, 0) * NVL(SOD.PLAN_PRICE, 0)\n" );
        sql.append("       END PLAN_AMOUNT\n" );
        sql.append("  FROM PT_BU_STOCK_OUT      SO,\n" );
        sql.append("       PT_BU_STOCK_OUT_DTL  SOD,\n" );
        sql.append("       PT_BU_SALE_ORDER     O,\n" );
        sql.append("       PT_BU_SALE_ORDER_DTL D\n" );
        sql.append(" WHERE SO.OUT_ID = SOD.OUT_ID\n" );
        sql.append("   AND SO.ORDER_ID = O.ORDER_ID\n" );
        sql.append("   AND O.ORDER_ID = D.ORDER_ID\n" );
        sql.append("   AND SOD.PART_ID = D.PART_ID\n" );
        sql.append("   AND SO.OUT_TYPE IN (201401, 201402, 201406)\n" );
        sql.append("   AND SO.OUT_STATUS = 201602\n" );
        sql.append("   AND O.STATUS = 100201\n" );
        sql.append("   AND "+conditions+")");
        bs = factory.select(sql.toString(), page);
        return bs;
    }
}
	