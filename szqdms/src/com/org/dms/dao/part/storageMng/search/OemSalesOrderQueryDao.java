package com.org.dms.dao.part.storageMng.search;

import java.sql.SQLException;
import java.util.Map;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 
 * ClassName: DealerSalesOrderQueryDao 
 * Function: 经销商本部订单查询
 * date: 2014年9月12日 上午10:20:31
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 */
public class OemSalesOrderQueryDao extends BaseDAO{
	
	public static final OemSalesOrderQueryDao getInstance(ActionContext atx){
		OemSalesOrderQueryDao dao = new OemSalesOrderQueryDao();
		atx.setDBFactory(dao.factory);
		return dao;
	}
	
	
	/**
	 * 
	 * querySalesOrderIndexInfo: 本部订单主页面查询
	 * @author fuxiao
	 * @param page
	 * @param conditions
	 * @param user
	 * @return
	 * @throws SQLException
	 * @since JDK 1.6
	 */
	public BaseResultSet querySalesOrderIndexInfo(PageManager page, String conditions, User user) throws SQLException{
		StringBuilder sb = new StringBuilder(conditions);
		sb.append(" AND STATUS = '" +DicConstant.YXBS_01+ "' AND (ORDER_STATUS <> '"+DicConstant.DDZT_01+"' OR ORDER_STATUS <> '"+DicConstant.DDZT_04+"' )");
		page.setFilter(sb.toString());
		String strSql = "SELECT\n" +
						"      ORDER_ID,\n" + 
						"      ORDER_NO,\n" + 
						"      ORDER_TYPE,\n" + 
						"      ORDER_STATUS,\n" + 
						"      SHIP_STATUS,\n" + 
						"      INVOICE_STATUS,\n" + 
						"      TRANS_TYPE,\n" + 
						"      IF_DELAY_ORDER,\n" + 
						"      APPLY_DATE,\n" + 
						"      ORDER_AMOUNT,\n" + 
						"      REAL_AMOUNT,\n" + 
						"      CLOSE_DATE\n" + 
						"FROM PT_BU_SALE_ORDER ";
		BaseResultSet rs = this.factory.select(strSql, page);
		rs.setFieldDic("ORDER_TYPE", "DDLX");
		rs.setFieldDic("ORDER_STATUS", "DDZT");
		rs.setFieldDic("SHIP_STATUS", "DDFYZT");
		rs.setFieldDic("INVOICE_STATUS", "KPZT");
		rs.setFieldDic("TRANS_TYPE", "FYFS");
		rs.setFieldDic("IF_DELAY_ORDER", "SF");
		rs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
		rs.setFieldDateFormat("CLOSE_DATE", "yyyy-MM-dd");
		return rs;
	}
	
	/**
	 * queryOrderInfoByOrderId: 通过OrderID查询订单详细信息及经销商配件账户信息
	 * @author fuxiao
	 * @param orderId
	 * @return
	 * @throws SQLException
	 * @since JDK 1.6
	 */
	public QuerySet queryOrderInfoByOrderId(String orderId) throws SQLException{
		String sql = 	
				"SELECT ORDER_ID,ORDER_NO,ORDER_TYPE,ORDER_STATUS,SHIP_STATUS,INVOICE_STATUS,TRANS_TYPE,IF_DELAY_ORDER,IF_CHANEL_ORDER,WAREHOUSE_CODE,WAREHOUSE_NAME,ORDER_AMOUNT,EXPECT_DATE,IF_CREDIT,IF_TRANS,CUSTORM_NAME,DELIVERY_ADDR,LINK_MAN,PHONE,ZIP_CODE,REMARKS,ACCOUNT_TYPE,AVAILABLE_AMOUNT,BALANCE_AMOUNT,OCCUPY_AMOUNT\n" +
						"FROM(\n" + 
						"  SELECT\n" + 
						"        T1.ORDER_ID,\n" + 
						"        T1.ORDER_NO,\n" + 
						"       (SELECT DIC_VALUE FROM DIC_TREE DIC WHERE DIC.ID = T1.ORDER_TYPE) ORDER_TYPE,\n" + 
						"       (SELECT DIC_VALUE FROM DIC_TREE DIC WHERE DIC.ID = T1.ORDER_STATUS) ORDER_STATUS,\n" + 
						"       (SELECT DIC_VALUE FROM DIC_TREE DIC WHERE DIC.ID = T1.SHIP_STATUS) SHIP_STATUS,\n" + 
						"       (SELECT DIC_VALUE FROM DIC_TREE DIC WHERE DIC.ID = T1.INVOICE_STATUS) INVOICE_STATUS,\n" + 
						"       (SELECT DIC_VALUE FROM DIC_TREE DIC WHERE DIC.ID = T1.TRANS_TYPE) TRANS_TYPE,\n" + 
						"       (SELECT DIC_VALUE FROM DIC_TREE DIC WHERE DIC.ID = T1.IF_DELAY_ORDER) IF_DELAY_ORDER,\n" + 
						"       (SELECT DIC_VALUE FROM DIC_TREE DIC WHERE DIC.ID = T1.IF_CHANEL_ORDER) IF_CHANEL_ORDER,\n" + 
						"        T1.WAREHOUSE_CODE,\n" + 
						"        T1.WAREHOUSE_NAME,\n" + 
						"        T1.ORDER_AMOUNT,\n" + 
						"        T1.EXPECT_DATE,\n" + 
						"       (SELECT DIC_VALUE FROM DIC_TREE DIC WHERE DIC.ID = T1.IF_CREDIT) IF_CREDIT,\n" + 
						"       (SELECT DIC_VALUE FROM DIC_TREE DIC WHERE DIC.ID = T1.IF_TRANS) IF_TRANS,\n" + 
						"        T1.CUSTORM_NAME,\n" + 
						"        T1.DELIVERY_ADDR,\n" + 
						"        T1.LINK_MAN,\n" + 
						"        T1.PHONE,\n" + 
						"        T1.ZIP_CODE,\n" + 
						"        T1.REMARKS,\n" + 
						"       (SELECT DIC_VALUE FROM DIC_TREE DIC WHERE DIC.ID = T2.ACCOUNT_TYPE) ACCOUNT_TYPE,\n" + 
						"        T2.AVAILABLE_AMOUNT,\n" + 
						"        T2.BALANCE_AMOUNT,\n" + 
						"        T2.OCCUPY_AMOUNT\n" + 
						"  FROM PT_BU_SALE_ORDER T1, PT_BU_ACCOUNT T2\n" + 
						"  WHERE T1.ORDER_ID = T2.ORG_ID(+)\n" + 
						" ) WHERE  ORDER_ID = ?";
		return this.factory.select(new Object[]{orderId}, sql);
	}
	
	
	/**
	 * queryOrderAmount:根据OrderID查询经销商订单扣款详情情况
	 * @author fuxiao
	 * Date:2014年9月12日上午10:32:55
	 * @param orderId
	 * @return
	 * @throws SQLException
	 */
	public BaseResultSet queryOrderAmountDetails(PageManager page, String conditions, String orderId, User user) throws SQLException{
		StringBuilder sb = new StringBuilder(conditions);
		sb.append(" AND ORDER_ID = "+ orderId);
		page.setFilter(sb.toString());
		String strSql = "SELECT\n" +
						"     ACCOUNT_TYPE,ACCOUNT_TYPE_NAME,AVAILABLE_AMOUNT,PAY_AMOUNT\n" + 
						"FROM (\n" + 
						"SELECT\n" + 
						"     T1.ORDER_ID,\n" + 
						"     T2.ACCOUNT_TYPE,\n" + 
						"     (SELECT DIC_VALUE FROM DIC_TREE DIC WHERE DIC.ID = T2.ACCOUNT_TYPE) ACCOUNT_TYPE_NAME,\n" + 
						"     T2.AVAILABLE_AMOUNT,\n" + 
						"     T1.PAY_AMOUNT\n" + 
						"FROM PT_BU_SALE_ORDER_PAY T1,\n" + 
						"     PT_BU_ACCOUNT T2\n" + 
						"WHERE T1.ACCOUNT_ID = T2.ACCOUNT_ID\n" + 
						")";
		return this.factory.select(strSql, page);
	}
	
	
	 /**
	 * queryOrderCheckInfo: 根据OrderID查询订单审核情况
	 * @author fuxiao
	 * Date:2014年9月12日上午10:35:35
	 * @param orderId
	 * @return
	 * @throws SQLException
	 */
	public BaseResultSet queryOrderCheckInfo(PageManager page, String conditions, String orderId, User user) throws SQLException{
		
		StringBuilder sb = new StringBuilder(conditions);
		sb.append(" AND ORDER_ID = "+ orderId);
		page.setFilter(sb.toString());
		String strSql = "SELECT\n" +
						"      CHECK_ID,\n" + 
						"      CHECK_USER,\n" + 
						"      CHECK_DATE,\n" + 
						"     (SELECT DIC_VALUE FROM DIC_TREE DIC WHERE DIC.ID = CHECK_RESULT) CHECK_RESULT,\n" + 
						"     CHECK_ORG,\n" + 
						"      REMARKS\n" + 
						"FROM PT_BU_SALE_ORDER_CHECK";
		BaseResultSet rs =  this.factory.select(strSql, page);
		rs.setFieldDateFormat("CHECK_DATE", "yyyy-MM-dd");
		rs.setFieldDic("CHECK_RESULT", "SHJG");
		return rs;
	}
	
	/**
	 * queryOrderCheckDetailsInfo: 根据审核表主键ID，checkid查询审核详情
	 * @author fuxiao
	 * Date:2014年9月12日上午11:14:06
	 * @param page
	 * @param conditions
	 * @param checkId
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public BaseResultSet queryOrderCheckDetailsInfo(PageManager page, String conditions, String checkId, User user) throws SQLException{
		StringBuilder sb = new StringBuilder(conditions);
		sb.append(" AND CHECK_ID = ?");
		page.setFilter(sb.toString());
		String strSql = "SELECT\n" +
						"    PART_CODE,\n" + 
						"    PART_NAME,\n" + 
						"    ORDER_COUNT,\n" + 
						"    CHECK_COUNT,\n" + 
						"    REMARKS\n" + 
						"FROM PT_BU_SALE_ORDER_CHECK_DTL";
		return this.factory.select(strSql, page);
	}
	
	
	/**
	 * queryOrderPartInfo: 根据OrderId查询订单中的配件详情
	 * @author fuxiao
	 * Date:2014年9月12日上午10:44:27
	 * @param page
	 * @param conditions
	 * @param orderId
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public BaseResultSet queryOrderPartInfo(PageManager page, String conditions, String orderId, User user) throws SQLException{
		StringBuilder sb = new StringBuilder(conditions);
		sb.append(" AND ORDER_ID = " + orderId);
		page.setFilter(sb.toString());
		String strSql = 
						" SELECT\n" +
						"      PART_CODE,\n" + 
						"      PART_NAME,\n" + 
						"      UNIT_PRICE,\n" + 
						"      PLAN_PRICE,\n" + 
						"      PLAN_UNIT,\n" + 
						"      ORDER_COUNT,\n" + 
						"      DELIVERY_COUNT,\n" + 
						"      SIGN_COUNT,\n" + 
						"      AMOUNT,\n" + 
						"      REMARKS\n" + 
						" FROM PT_BU_SALE_ORDER_DTL";
		BaseResultSet rs = this.factory.select(strSql, page);
		rs.setFieldDic("PLAN_UNIT", "JLDW");
		return rs;
	}
	
	
	/**
	 * 
	 * @Title: getSql
	 * @Description: 在途查询sql生成
	 * @param request
	 * @return
	 * @return: String
	 */
	public String getSql(RequestWrapper request){
		Map<String, String> map = RequestUtil.getValues(request);
		String wcode = map.get("WAREHOUSE_CODE") == null ? "" : map.get("WAREHOUSE_CODE");
		String strSql = "SELECT * FROM (\n" +
				"SELECT D.PART_CODE,\n" +
				"       D.PART_NAME,\n" + 
				"       SUM(D.OUT_AMOUNT) OUT_COUNTS,\n" + 
				"       I.PLAN_PRICE,\n" + 
				"       (SUM(D.OUT_AMOUNT) * I.PLAN_PRICE) ALL_AMOUNT\n" + 
				"  FROM PT_BU_STOCK_OUT      O,\n" + 
				"       PT_BU_STOCK_OUT_DTL  D,\n" + 
				"       PT_BU_SALE_ORDER     A,\n" + 
				"       PT_BU_SALE_ORDER_DTL B,\n" + 
				"       PT_BA_INFO           I\n" + 
				" WHERE O.OUT_ID = D.OUT_ID\n" + 
				"   AND O.ORDER_ID = A.ORDER_ID\n" + 
				"   AND A.ORDER_ID = B.ORDER_ID\n" + 
				"   AND D.PART_ID = B.PART_ID\n" + 
				"   AND D.PART_ID = I.PART_ID\n" + 
				"   AND O.OUT_STATUS = 201602\n" + 
				"   AND A.ORDER_STATUS = 202203\n" + 
				"   AND A.SHIP_STATUS IN (204803, 204804, 204805, 204806)\n";
		if(!"".equals(wcode)){
			strSql += " AND O.WAREHOUSE_CODE = '" + wcode + "'"; 
		}
		strSql += " GROUP BY D.PART_CODE, D.PART_NAME, I.PLAN_PRICE)";
		return strSql;
	}
	
	/**
	 * 
	 * @Title: querySaleOnroad
	 * @Description: 添加仓库查询条件
	 * @param page
	 * @param conditions
	 * @param user
	 * @param request
	 * @return
	 * @throws SQLException
	 * @return: BaseResultSet
	 */
	public BaseResultSet querySaleOnroad(PageManager page, String conditions, User user, RequestWrapper request) throws SQLException{
		StringBuilder sb = new StringBuilder(conditions);
		page.setFilter(sb.toString());
		String strSql = this.getSql(request);
		BaseResultSet rs = this.factory.select(strSql, page);
		return rs;
	} 
	
	/**
	 * 
	 * querySaleOnroad: 销售在途物资查询
	 * @author wangzhen
	 * @param page
	 * @param conditions
	 * @param user
	 * @return
	 * @throws SQLException
	 * @since JDK 1.6
	 */
	public BaseResultSet querySaleOnroad(PageManager page, String conditions, User user) throws SQLException{
		StringBuilder sb = new StringBuilder(conditions);
		page.setFilter(sb.toString());
		String strSql = "SELECT * FROM (\n" +
						"SELECT D.PART_CODE,\n" +
						"       D.PART_NAME,\n" + 
						"       SUM(D.OUT_AMOUNT) OUT_COUNTS,\n" + 
						"       I.PLAN_PRICE,\n" + 
						"       (SUM(D.OUT_AMOUNT) * I.PLAN_PRICE) ALL_AMOUNT\n" + 
						"  FROM PT_BU_STOCK_OUT      O,\n" + 
						"       PT_BU_STOCK_OUT_DTL  D,\n" + 
						"       PT_BU_SALE_ORDER     A,\n" + 
						"       PT_BU_SALE_ORDER_DTL B,\n" + 
						"       PT_BA_INFO           I\n" + 
						" WHERE O.OUT_ID = D.OUT_ID\n" + 
						"   AND O.ORDER_ID = A.ORDER_ID\n" + 
						"   AND A.ORDER_ID = B.ORDER_ID\n" + 
						"   AND D.PART_ID = B.PART_ID\n" + 
						"   AND D.PART_ID = I.PART_ID\n" + 
						"   AND O.OUT_STATUS = 201602\n" + 
						"   AND A.ORDER_STATUS = 202203\n" + 
						"   AND A.SHIP_STATUS IN (204803, 204804, 204805, 204806)\n" + 
						" GROUP BY D.PART_CODE, D.PART_NAME, I.PLAN_PRICE)";
		BaseResultSet rs = this.factory.select(strSql, page);
		return rs;
	}
	
	/**
	 * 
	 * downloadSaleOnroad: 销售在途物资下载
	 * @author wangzhen
	 * @param page
	 * @param conditions
	 * @param user
	 * @return
	 * @throws SQLException
	 * @since JDK 1.6
	 */
	public QuerySet downloadSaleOnroad(String conditions, User user) throws SQLException{
		String strSql = "SELECT * FROM (\n" +
						"SELECT D.PART_CODE,\n" +
						"       D.PART_NAME,\n" + 
						"       SUM(D.OUT_AMOUNT) OUT_COUNTS,\n" + 
						"       I.PLAN_PRICE,\n" + 
						"       (SUM(D.OUT_AMOUNT) * I.PLAN_PRICE) ALL_AMOUNT\n" + 
						"  FROM PT_BU_STOCK_OUT      O,\n" + 
						"       PT_BU_STOCK_OUT_DTL  D,\n" + 
						"       PT_BU_SALE_ORDER     A,\n" + 
						"       PT_BU_SALE_ORDER_DTL B,\n" + 
						"       PT_BA_INFO           I\n" + 
						" WHERE O.OUT_ID = D.OUT_ID\n" + 
						"   AND O.ORDER_ID = A.ORDER_ID\n" + 
						"   AND A.ORDER_ID = B.ORDER_ID\n" + 
						"   AND D.PART_ID = B.PART_ID\n" + 
						"   AND D.PART_ID = I.PART_ID\n" + 
						"   AND O.OUT_STATUS = 201602\n" + 
						"   AND A.ORDER_STATUS = 202203\n" + 
						"   AND A.SHIP_STATUS IN (204803, 204804, 204805, 204806)\n" + 
						" GROUP BY D.PART_CODE, D.PART_NAME, I.PLAN_PRICE)";
		return factory.select(null, strSql + " WHERE " + conditions);
	}
	
	/**
	 * 
	 * @Title: downloadSaleOnroad
	 * @Description: 查询下载
	 * @param conditions
	 * @param user
	 * @param request
	 * @return
	 * @throws SQLException
	 * @return: QuerySet
	 */
	public QuerySet downloadSaleOnroad(String conditions, User user, RequestWrapper request) throws SQLException{
		String strSql = this.getSql(request); 
		return factory.select(null, strSql + " WHERE " + conditions);
	}
	
	/**
	 * 
	 * reaOutWarehouseQuery:区域配送中心出库统计
	 * @author wangzhen
	 * @param page
	 * @param conditions
	 * @param user
	 * @return
	 * @throws SQLException
	 * @since JDK 1.6
	 */
	public BaseResultSet areaOutWarehouseQuery(PageManager page, String conditions, User user) throws SQLException{

		StringBuffer sql= new StringBuffer();
		sql.append("SELECT *\n" );
		sql.append("  FROM (SELECT O.CLOSE_DATE OUT_DATE,\n" );
		sql.append("               O.WAREHOUSE_ID ORG_ID,\n" );
		sql.append("               O.WAREHOUSE_CODE PSZX_CODE,\n" );
		sql.append("               O.WAREHOUSE_NAME PSZX_NAME,\n" );
		sql.append("               I.PART_CODE,\n" );
		sql.append("               I.PART_NAME,\n" );
		sql.append("               I.UNIT,\n" );
		sql.append("               D.DELIVERY_COUNT OUT_COUNTS,\n" );
		sql.append("               D.UNIT_PRICE SALE_PRICE,\n" );
		sql.append("               D.DELIVERY_COUNT * D.UNIT_PRICE SALE_AMOUNT,\n" );
		sql.append("               O.ORG_CODE TO_CODE,\n" );
		sql.append("               O.ORG_NAME TO_NAME,\n" );
		sql.append("               D.REMARKS\n" );
		sql.append("          FROM PT_BU_SALE_ORDER O, PT_BU_SALE_ORDER_DTL D, PT_BA_INFO I\n" );
		sql.append("         WHERE O.ORDER_ID = D.ORDER_ID\n" );
		sql.append("           AND D.PART_ID = I.PART_ID\n" );
		sql.append("           AND O.WAREHOUSE_ID IN\n" );
		sql.append("               (SELECT T.ORG_ID FROM TM_ORG T WHERE T.ORG_TYPE = 200005)\n" );
		sql.append("           AND D.SPLANNO IS NULL\n" );
		sql.append("           AND O.SHIP_STATUS IN (204806, 204807)\n" );
		sql.append("           AND D.DELIVERY_COUNT > 0\n" );
		sql.append("           AND O.IF_CHANEL_ORDER = 100101\n" );
		sql.append("           AND O.ORDER_STATUS = 202206\n" );
		sql.append("        UNION ALL\n" );
		sql.append("        SELECT A.SALE_DATE OUT_DATE,\n" );
		sql.append("               MO.ORG_ID,\n" );
		sql.append("               MO.CODE PSZX_CODE,\n" );
		sql.append("               MO.ONAME PSZX_NAME,\n" );
		sql.append("               C.PART_CODE,\n" );
		sql.append("               C.PART_NAME,\n" );
		sql.append("               C.UNIT,\n" );
		sql.append("               B.SALE_COUNT OUT_COUNTS,\n" );
		sql.append("               B.SALE_PRICE SALE_PRICE,\n" );
		sql.append("               B.SALE_COUNT * B.SALE_PRICE SALE_AMOUNT,\n" );
		sql.append("               '' TO_CODE,\n" );
		sql.append("               A.CUSTOMER_NAME TO_NAME,\n" );
		sql.append("               '' REMARKS\n" );
		sql.append("          FROM PT_BU_REAL_SALE     A,\n" );
		sql.append("               PT_BU_REAL_SALE_DTL B,\n" );
		sql.append("               PT_BA_INFO          C,\n" );
		sql.append("               TM_ORG              MO\n" );
		sql.append("         WHERE A.SALE_ID = B.SALE_ID\n" );
		sql.append("           AND B.PART_ID = C.PART_ID\n" );
		sql.append("           AND A.ORG_ID = MO.ORG_ID\n" );
		sql.append("           AND A.ORG_ID IN\n" );
		sql.append("               (SELECT T.ORG_ID FROM TM_ORG T WHERE T.ORG_TYPE = 200005)\n" );
		sql.append("           AND A.SALE_STATUS = 205002\n" );
		sql.append("           AND A.STATUS = 100201) A");

        sql.append("	WHERE " + conditions);
        //执行方法，不需要传递conn参数
        return factory.select(sql.toString(),page);
	}
	
	/**
	 * 
	 * downloadSaleOnroad: 销售在途物资下载
	 * @author wangzhen
	 * @param page
	 * @param conditions
	 * @param user
	 * @return
	 * @throws SQLException
	 * @since JDK 1.6
	 */
	public QuerySet downAreaOutWarehouse(String conditions, User user) throws SQLException{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT *\n" );
		sql.append("  FROM (SELECT O.CLOSE_DATE OUT_DATE,\n" );
		sql.append("               O.WAREHOUSE_ID ORG_ID,\n" );
		sql.append("               O.WAREHOUSE_CODE PSZX_CODE,\n" );
		sql.append("               O.WAREHOUSE_NAME PSZX_NAME,\n" );
		sql.append("               I.PART_CODE,\n" );
		sql.append("               I.PART_NAME,\n" );
		sql.append("               (SELECT DT.DIC_VALUE FROM DIC_TREE DT WHERE DT.ID = I.UNIT) UNIT,\n" );
		sql.append("               D.DELIVERY_COUNT OUT_COUNTS,\n" );
		sql.append("               D.UNIT_PRICE SALE_PRICE,\n" );
		sql.append("               D.DELIVERY_COUNT * D.UNIT_PRICE SALE_AMOUNT,\n" );
		sql.append("               O.ORG_CODE TO_CODE,\n" );
		sql.append("               O.ORG_NAME TO_NAME,\n" );
		sql.append("               D.REMARKS\n" );
		sql.append("          FROM PT_BU_SALE_ORDER O, PT_BU_SALE_ORDER_DTL D, PT_BA_INFO I\n" );
		sql.append("         WHERE O.ORDER_ID = D.ORDER_ID\n" );
		sql.append("           AND D.PART_ID = I.PART_ID\n" );
		sql.append("           AND O.WAREHOUSE_ID IN\n" );
		sql.append("               (SELECT T.ORG_ID FROM TM_ORG T WHERE T.ORG_TYPE = 200005)\n" );
		sql.append("           AND D.SPLANNO IS NULL\n" );
		sql.append("           AND O.SHIP_STATUS IN (204806, 204807)\n" );
		sql.append("           AND D.DELIVERY_COUNT > 0\n" );
		sql.append("           AND O.IF_CHANEL_ORDER = 100101\n" );
		sql.append("           AND O.ORDER_STATUS = 202206\n" );
		sql.append("        UNION\n" );
		sql.append("        SELECT A.SALE_DATE OUT_DATE,\n" );
		sql.append("               MO.ORG_ID,\n" );
		sql.append("               MO.CODE PSZX_CODE,\n" );
		sql.append("               MO.ONAME PSZX_NAME,\n" );
		sql.append("               C.PART_CODE,\n" );
		sql.append("               C.PART_NAME,\n" );
		sql.append("               (SELECT DT.DIC_VALUE FROM DIC_TREE DT WHERE DT.ID = C.UNIT) UNIT,\n" );
		sql.append("               B.SALE_COUNT OUT_COUNTS,\n" );
		sql.append("               B.SALE_PRICE SALE_PRICE,\n" );
		sql.append("               B.SALE_COUNT * B.SALE_PRICE SALE_AMOUNT,\n" );
		sql.append("               '' TO_CODE,\n" );
		sql.append("               A.CUSTOMER_NAME TO_NAME,\n" );
		sql.append("               '' REMARKS\n" );
		sql.append("          FROM PT_BU_REAL_SALE     A,\n" );
		sql.append("               PT_BU_REAL_SALE_DTL B,\n" );
		sql.append("               PT_BA_INFO          C,\n" );
		sql.append("               TM_ORG              MO\n" );
		sql.append("         WHERE A.SALE_ID = B.SALE_ID\n" );
		sql.append("           AND B.PART_ID = C.PART_ID\n" );
		sql.append("           AND A.ORG_ID = MO.ORG_ID\n" );
		sql.append("           AND A.ORG_ID IN\n" );
		sql.append("               (SELECT T.ORG_ID FROM TM_ORG T WHERE T.ORG_TYPE = 200005)\n" );
		sql.append("           AND A.SALE_STATUS = 205002\n" );
		sql.append("           AND A.STATUS = 100201) A");
		return factory.select(null, sql + " WHERE " + conditions);
	}
	
	/**
	 * 
	 * @Title: queryPartOutIn
	 * @Description: 配件出入库汇总查询
	 * @param page
	 * @param conditions
	 * @param user
	 * @param request
	 * @return
	 * @throws SQLException
	 * @return: BaseResultSet
	 */
	public BaseResultSet queryPartOutIn(PageManager page, String conditions, User user, RequestWrapper request) throws SQLException{
		Map<String, String> map = RequestUtil.getValues(request);
		String bday = map.get("IN_DATE_B") == null ? "" : map.get("IN_DATE_B");
		String eday = map.get("IN_DATE_E") == null ? "" : map.get("IN_DATE_E");
		String ocode = map.get("ORG_CODE") == null ? "" : map.get("ORG_CODE");
		String oname = map.get("ONAME") == null ? "" : map.get("ONAME");
		String strSql = 
						//----服务站部分----
						"SELECT T8.PART_CODE, T8.PART_NAME, T9.CODE, T9.ONAME, T7.*\n" + 
						"  FROM (SELECT T5.PART_ID,\n" + 
						"               T5.ORG_ID,\n" + 
						"               NVL(T5.QC, 0) QC,\n" + 
						"               NVL(T5.CSL, 0) CSL,\n" + 
						"               NVL(T6.RSL, 0) RSL,\n" + 
						"               (NVL(T5.QC, 0) - NVL(T5.CSL, 0) + NVL(T6.RSL, 0)) QM\n" + 
						"          FROM (SELECT T3.PART_ID,\n" + 
						"                       T3.ORG_ID,\n" + 
						"                       T3.QC,\n" + 
						"                       (NVL(T3.CSL, 0) + NVL(T4.CSL, 0)) CSL\n" + 
						"                  FROM (SELECT T1.PART_ID, T1.ORG_ID, T1.QC, T2.CSL\n" + 
						"                          FROM \n" +
						//--期初
						"                               (SELECT S.PART_ID, S.ORG_ID, S.AMOUNT QC\n" + 
						"                                  FROM PT_BU_DEALER_STOCK_STATISTICS S\n" + 
						"                                 WHERE S.ORG_ID IS NOT NULL\n";
						if(!"".equals(ocode)){
							strSql += " AND S.ORG_CODE LIKE '%" + ocode + "%'"; 
						}
						if(!"".equals(oname)){
							strSql += " AND S.ORG_NAME LIKE '%" + oname + "%'"; 
						}
						strSql += 
						"                                   AND S.STATIS_DATE = TO_DATE('"+bday+"', 'YYYY-MM-DD')) T1\n" + 
						"                          LEFT JOIN\n" + 
						//--服务站退配送中心出库
						"                         (SELECT B.PART_ID,\n" + 
						"                                A.APPLY_ORG_ID ORG_ID,\n" + 
						"                                SUM(AD.RETURN_COUNT) CSL\n" + 
						"                           FROM PT_BA_INFO             B,\n" + 
						"                                PT_BU_RETURN_APPLY     A,\n" + 
						"                                PT_BU_RETURN_APPLY_DTL AD\n" + 
						"                          WHERE B.PART_ID = AD.PART_ID\n" + 
						"                            AND A.RETURN_ID = AD.RETURN_ID\n" + 
						"                            AND A.APPLY_ORG_ID IN\n" + 
						"                                (SELECT O.ORG_ID\n" + 
						"                                   FROM TM_ORG O\n" + 
						"                                  WHERE O.ORG_TYPE = 200005)\n" + 
						"                            AND A.IN_STOCK_DATE >= TO_DATE('"+bday+"', 'YYYY-MM-DD')\n" + 
						"                            AND A.IN_STOCK_DATE <= TO_DATE('"+eday+"', 'YYYY-MM-DD')\n";
						if(!"".equals(ocode)){
							strSql += " AND A.APPLY_ORG_CODE LIKE '%" + ocode + "%'"; 
						}
						if(!"".equals(oname)){
							strSql += " AND A.APPLY_ORG_NAME LIKE '%" + oname + "%'"; 
						}
						strSql += 
						"                          GROUP BY B.PART_ID, A.APPLY_ORG_ID) T2\n" + 
						"                            ON T1.PART_ID = T2.PART_ID\n" + 
						"                           AND T1.ORG_ID = T2.ORG_ID) T3\n" + 
						"                  LEFT JOIN\n" + 
						//--实销出库
						"                 (SELECT B.PART_ID, S.ORG_ID, SUM(SD.SALE_COUNT) CSL\n" + 
						"                   FROM PT_BA_INFO          B,\n" + 
						"                        PT_BU_REAL_SALE     S,\n" + 
						"                        PT_BU_REAL_SALE_DTL SD,\n" + 
						"                        TM_ORG              O\n" + 
						"                  WHERE B.PART_ID = SD.PART_ID\n" + 
						"                    AND S.SALE_ID = SD.SALE_ID\n" + 
						"                    AND S.ORG_ID = O.ORG_ID\n" + 
						"                    AND S.ORG_ID IN\n" + 
						"                        (SELECT O.ORG_ID\n" + 
						"                           FROM TM_ORG O\n" + 
						"                          WHERE O.ORG_TYPE IN (200006, 200007))\n" + 
						"                    AND S.SALE_DATE >= TO_DATE('"+bday+"', 'YYYY-MM-DD')\n" + 
						"                    AND S.SALE_DATE <= TO_DATE('"+eday+"', 'YYYY-MM-DD')\n";
						if(!"".equals(ocode)){
							strSql += " AND O.CODE LIKE '%" + ocode + "%'"; 
						}
						if(!"".equals(oname)){
							strSql += " AND O.ONAME LIKE '%" + oname + "%'"; 
						}
						strSql += 
						"                  GROUP BY B.PART_ID, S.ORG_ID) T4\n" + 
						"                    ON T3.PART_ID = T4.PART_ID\n" + 
						"                   AND T3.ORG_ID = T4.ORG_ID) T5\n" + 
						"          LEFT JOIN\n" + 
						//--订单入库
						"         (SELECT B.PART_ID, O.ORG_ID, SUM(OD.ORDER_COUNT) RSL\n" + 
						"           FROM PT_BA_INFO B, PT_BU_SALE_ORDER O, PT_BU_SALE_ORDER_DTL OD\n" + 
						"          WHERE OD.PART_ID = B.PART_ID\n" + 
						"            AND OD.ORDER_ID = O.ORDER_ID\n" + 
						"            AND O.ORG_ID IN\n" + 
						"                (SELECT O.ORG_ID\n" + 
						"                   FROM TM_ORG O\n" + 
						"                  WHERE O.ORG_TYPE IN (200006, 200007))\n" + 
						"            AND O.CLOSE_DATE >= TO_DATE('"+bday+"', 'YYYY-MM-DD')\n" + 
						"            AND O.CLOSE_DATE <= TO_DATE('"+eday+"', 'YYYY-MM-DD')\n";
						if(!"".equals(ocode)){
							strSql += " AND O.ORG_CODE LIKE '%" + ocode + "%'"; 
						}
						if(!"".equals(oname)){
							strSql += " AND O.ORG_NAME LIKE '%" + oname + "%'"; 
						}
						strSql += 
						"          GROUP BY B.PART_ID, O.ORG_ID) T6\n" + 
						"            ON T5.PART_ID = T6.PART_ID\n" + 
						"           AND T5.ORG_ID = T6.ORG_ID) T7,\n" + 
						"       PT_BA_INFO T8,\n" + 
						"       TM_ORG T9\n" + 
						" WHERE T7.PART_ID = T8.PART_ID\n" + 
						"   AND T7.ORG_ID = T9.ORG_ID\n" + 
						"UNION ALL\n" + 
						//----配送中心部分----
						"SELECT T15.PART_CODE, T15.PART_NAME, T14.CODE, T14.ONAME, T13.*\n" + 
						"  FROM (SELECT T11.PART_ID,\n" + 
						"               T11.ORG_ID,\n" + 
						"               T11.QC,\n" + 
						"               T11.CSL,\n" + 
						"               (NVL(T11.RSL, 0) + NVL(T12.RSL, 0)) RSL,\n" + 
						"               (NVL(T11.QC,0) - NVL(T11.CSL,0) + NVL(T11.RSL, 0) + NVL(T12.RSL, 0)) QM\n" + 
						"          FROM (SELECT T9.PART_ID,\n" + 
						"                       T9.ORG_ID,\n" + 
						"                       T9.QC,\n" + 
						"                       T9.CSL,\n" + 
						"                       (NVL(T9.RSL, 0) + NVL(T10.RSL, 0)) RSL\n" + 
						"                  FROM (SELECT T7.PART_ID, T7.ORG_ID, T7.QC, T7.CSL, T8.RSL\n" + 
						"                          FROM (SELECT T5.PART_ID,\n" + 
						"                                       T5.ORG_ID,\n" + 
						"                                       T5.QC,\n" + 
						"                                       (NVL(T5.CSL, 0) + NVL(T6.CSL, 0)) CSL\n" + 
						"                                  FROM (SELECT T3.PART_ID,\n" + 
						"                                               T3.ORG_ID,\n" + 
						"                                               T3.QC,\n" + 
						"                                               (NVL(T3.CSL, 0) + NVL(T4.CSL, 0)) CSL\n" + 
						"                                          FROM (SELECT T1.PART_ID,\n" + 
						"                                                       T1.ORG_ID,\n" + 
						"                                                       T1.QC,\n" + 
						"                                                       T2.CSL\n" + 
						"                                                  FROM \n" +
						//--期初
						"                                                       (SELECT S.PART_ID,\n" + 
						"                                                               S.ORG_ID,\n" + 
						"                                                               S.AMOUNT QC\n" + 
						"                                                          FROM PT_BU_DEALER_STOCK_STATISTICS S\n" + 
						"                                                         WHERE S.ORG_ID IS NOT NULL\n";
						if(!"".equals(ocode)){
							strSql += " AND S.ORG_CODE LIKE '%" + ocode + "%'"; 
						}
						if(!"".equals(oname)){
							strSql += " AND S.ORG_NAME LIKE '%" + oname + "%'"; 
						}
						strSql += 
						"                                                           AND S.STATIS_DATE = TO_DATE('"+bday+"', 'YYYY-MM-DD')) T1\n" + 
						"                                                  LEFT JOIN\n" + 
						//--实销出库
						"                                                 (SELECT B.PART_ID,\n" + 
						"                                                        RS.ORG_ID,\n" + 
						"                                                        SUM(SD.SALE_COUNT) CSL\n" + 
						"                                                   FROM PT_BA_INFO          B,\n" + 
						"                                                        PT_BU_REAL_SALE     RS,\n" + 
						"                                                        PT_BU_REAL_SALE_DTL SD,\n" + 
						"                                                        TM_ORG              O\n" + 
						"                                                  WHERE B.PART_ID = SD.PART_ID\n" + 
						"                                                    AND RS.SALE_ID = SD.SALE_ID\n" + 
						"                                                    AND RS.ORG_ID = O.ORG_ID\n";
						if(!"".equals(ocode)){
							strSql += " AND O.CODE LIKE '%" + ocode + "%'"; 
						}
						if(!"".equals(oname)){
							strSql += " AND O.ONAME LIKE '%" + oname + "%'"; 
						}
						strSql += 
						"                                                    AND RS.ORG_ID IN\n" + 
						"                                                        (SELECT O.ORG_ID\n" + 
						"                                                           FROM TM_ORG O\n" + 
						"                                                          WHERE O.ORG_TYPE = 200005)\n" + 
						"                                                    AND RS.SALE_DATE >= TO_DATE('"+bday+"', 'YYYY-MM-DD')\n" + 
						"                                                    AND RS.SALE_DATE <= TO_DATE('"+eday+"', 'YYYY-MM-DD')\n" + 
						"                                                  GROUP BY B.PART_ID, RS.ORG_ID) T2\n" + 
						"                                                    ON T1.PART_ID = T2.PART_ID\n" + 
						"                                                   AND T1.ORG_ID = T2.ORG_ID) T3\n" + 
						"                                          LEFT JOIN\n" + 
						//--销售出库
						"                                         (SELECT B.PART_ID,\n" + 
						"                                                O.ORG_ID,\n" + 
						"                                                SUM(OD.ORDER_COUNT) CSL\n" + 
						"                                           FROM PT_BA_INFO           B,\n" + 
						"                                                PT_BU_SALE_ORDER     O,\n" + 
						"                                                PT_BU_SALE_ORDER_DTL OD\n" + 
						"                                          WHERE OD.PART_ID = B.PART_ID\n" + 
						"                                            AND OD.ORDER_ID = O.ORDER_ID\n";
						if(!"".equals(ocode)){
							strSql += " AND O.ORG_CODE LIKE '%" + ocode + "%'"; 
						}
						if(!"".equals(oname)){
							strSql += " AND O.ORG_NAME LIKE '%" + oname + "%'"; 
						}
						strSql += 
						"                                            AND O.ORG_ID IN\n" + 
						"                                                (SELECT O.ORG_ID\n" + 
						"                                                   FROM TM_ORG O\n" + 
						"                                                  WHERE O.ORG_TYPE = 200005)\n" + 
						"                                            AND O.CLOSE_DATE >= TO_DATE('"+bday+"', 'YYYY-MM-DD')\n" + 
						"                                            AND O.CLOSE_DATE <= TO_DATE('"+eday+"', 'YYYY-MM-DD')\n" + 
						"                                          GROUP BY B.PART_ID, O.ORG_ID) T4\n" + 
						"                                            ON T3.PART_ID = T4.PART_ID\n" + 
						"                                           AND T3.ORG_ID = T4.ORG_ID) T5\n" + 
						"                                  LEFT JOIN\n" + 
						//--配送中心退件出库
						"                                 (SELECT B.PART_ID,\n" + 
						"                                        A.APPLY_ORG_ID ORG_ID,\n" + 
						"                                        SUM(AD.RETURN_COUNT) CSL\n" + 
						"                                   FROM PT_BA_INFO             B,\n" + 
						"                                        PT_BU_RETURN_APPLY     A,\n" + 
						"                                        PT_BU_RETURN_APPLY_DTL AD\n" + 
						"                                  WHERE B.PART_ID = AD.PART_ID\n" + 
						"                                    AND A.RETURN_ID = AD.RETURN_ID\n";
						if(!"".equals(ocode)){
							strSql += " AND A.APPLY_ORG_CODE LIKE '%" + ocode + "%'"; 
						}
						if(!"".equals(oname)){
							strSql += " AND A.APPLY_ORG_NAME LIKE '%" + oname + "%'"; 
						}
						strSql += 
						"                                    AND A.IN_STOCK_DATE >= TO_DATE('"+bday+"', 'YYYY-MM-DD')\n" + 
						"                                    AND A.IN_STOCK_DATE <= TO_DATE('"+eday+"', 'YYYY-MM-DD')\n" + 
						"                                  GROUP BY B.PART_ID, A.APPLY_ORG_ID) T6\n" + 
						"                                    ON T5.PART_ID = T6.PART_ID\n" + 
						"                                   AND T5.ORG_ID = T6.ORG_ID) T7\n" + 
						"                          LEFT JOIN\n" + 
						//--服务站退配送中心入库
						"                         (SELECT B.PART_ID,\n" + 
						"                                A.RECEIVE_ORG_ID ORG_ID,\n" + 
						"                                SUM(AD.RETURN_COUNT) RSL\n" + 
						"                           FROM PT_BA_INFO             B,\n" + 
						"                                PT_BU_RETURN_APPLY     A,\n" + 
						"                                PT_BU_RETURN_APPLY_DTL AD\n" + 
						"                          WHERE B.PART_ID = AD.PART_ID\n" + 
						"                            AND A.RETURN_ID = AD.RETURN_ID\n" + 
						"                            AND A.IN_STOCK_DATE >= TO_DATE('"+bday+"', 'YYYY-MM-DD')\n" + 
						"                            AND A.IN_STOCK_DATE <= TO_DATE('"+eday+"', 'YYYY-MM-DD')\n";
						if(!"".equals(ocode)){
							strSql += " AND A.RECEIVE_ORG_CODE LIKE '%" + ocode + "%'"; 
						}
						if(!"".equals(oname)){
							strSql += " AND A.RECEIVE_ORG_NAME LIKE '%" + oname + "%'"; 
						}
						strSql += 
						"                          GROUP BY B.PART_ID, A.RECEIVE_ORG_ID) T8\n" + 
						"                            ON T7.PART_ID = T8.PART_ID\n" + 
						"                           AND T7.ORG_ID = T8.ORG_ID) T9\n" + 
						"                  LEFT JOIN\n" + 
						//--实销退件入库
						"                 (SELECT B.PART_ID, S.ORG_ID, SUM(R.RETURN_COUNT) RSL\n" + 
						"                   FROM PT_BA_INFO             B,\n" + 
						"                        PT_BU_REAL_SALE_RETURN R,\n" + 
						"                        PT_BU_REAL_SALE        S,\n" + 
						"                        TM_ORG                 O\n" + 
						"                  WHERE B.PART_ID = R.PART_ID\n" + 
						"                    AND R.SALE_ID = S.SALE_ID\n" + 
						"                    AND S.ORG_ID = S.ORG_ID\n" + 
						"                    AND S.SALE_DATE >= TO_DATE('"+bday+"', 'YYYY-MM-DD')\n" + 
						"                    AND S.SALE_DATE <= TO_DATE('"+eday+"', 'YYYY-MM-DD')\n";
						if(!"".equals(ocode)){
							strSql += " AND O.CODE LIKE '%" + ocode + "%'"; 
						}
						if(!"".equals(oname)){
							strSql += " AND O.ONAME LIKE '%" + oname + "%'"; 
						}
						strSql += 
						"                    AND S.ORG_ID IN (SELECT O.ORG_ID\n" + 
						"                                       FROM TM_ORG O\n" + 
						"                                      WHERE O.ORG_TYPE = 200005)\n" + 
						"                  GROUP BY B.PART_ID, S.ORG_ID) T10\n" + 
						"                    ON T9.PART_ID = T10.PART_ID\n" + 
						"                   AND T9.ORG_ID = T10.ORG_ID) T11\n" + 
						"          LEFT JOIN\n" + 
						//--订单入库
						"         (SELECT B.PART_ID, O.ORG_ID, SUM(OD.ORDER_COUNT) RSL\n" + 
						"           FROM PT_BA_INFO B, PT_BU_SALE_ORDER O, PT_BU_SALE_ORDER_DTL OD\n" + 
						"          WHERE OD.PART_ID = B.PART_ID\n" + 
						"            AND OD.ORDER_ID = O.ORDER_ID\n" + 
						"            AND O.CLOSE_DATE >= TO_DATE('"+bday+"', 'YYYY-MM-DD')\n" + 
						"            AND O.CLOSE_DATE <= TO_DATE('"+eday+"', 'YYYY-MM-DD')\n";
						if(!"".equals(ocode)){
							strSql += " AND O.ORG_CODE LIKE '%" + ocode + "%'"; 
						}
						if(!"".equals(oname)){
							strSql += " AND O.ORG_NAME LIKE '%" + oname + "%'"; 
						}
						strSql += 
						"            AND O.ORG_ID IN\n" + 
						"                (SELECT O.ORG_ID FROM TM_ORG O WHERE O.ORG_TYPE = 200005)\n" + 
						"          GROUP BY B.PART_ID, O.ORG_ID) T12\n" + 
						"            ON T11.PART_ID = T12.PART_ID\n" + 
						"           AND T11.ORG_ID = T12.ORG_ID) T13,\n" + 
						"       TM_ORG T14,\n" + 
						"       PT_BA_INFO T15\n" + 
						" WHERE T13.ORG_ID = T14.ORG_ID\n" + 
						"   AND T13.PART_ID = T15.PART_ID";

		BaseResultSet rs = this.factory.select(strSql, page);
		return rs;
	}
	
	/**
	 * 
	 * @Title: downPartOutIn
	 * @Description: 配件出入库汇总查询下载
	 * @param page
	 * @param conditions
	 * @param user
	 * @param request
	 * @return
	 * @throws SQLException
	 * @return: BaseResultSet
	 */
	public QuerySet downPartOutIn(PageManager page, String conditions, User user, RequestWrapper request) throws SQLException{
		Map<String, String> map = RequestUtil.getValues(request);
		String bday = map.get("IN_DATE_B") == null ? "" : map.get("IN_DATE_B");
		String eday = map.get("IN_DATE_E") == null ? "" : map.get("IN_DATE_E");
		String ocode = map.get("ORG_CODE") == null ? "" : map.get("ORG_CODE");
		String oname = map.get("ONAME") == null ? "" : map.get("ONAME");
		StringBuffer sql= new StringBuffer();
		//----服务站----
		sql.append("SELECT T8.PART_CODE, T8.PART_NAME, T9.CODE, T9.ONAME, T7.*\n" );
		sql.append("  FROM (SELECT T5.PART_ID,\n" );
		sql.append("               T5.ORG_ID,\n" );
		sql.append("               NVL(T5.QC, 0) QC,\n" );
		sql.append("               NVL(T5.CSL, 0) CSL,\n" );
		sql.append("               NVL(T6.RSL, 0) RSL,\n" );
		sql.append("               (NVL(T5.QC, 0) - NVL(T5.CSL, 0) + NVL(T6.RSL, 0)) QM\n" );
		sql.append("          FROM (SELECT T3.PART_ID,\n" );
		sql.append("                       T3.ORG_ID,\n" );
		sql.append("                       T3.QC,\n" );
		sql.append("                       (NVL(T3.CSL, 0) + NVL(T4.CSL, 0)) CSL\n" );
		sql.append("                  FROM (SELECT T1.PART_ID, T1.ORG_ID, T1.QC, T2.CSL\n" );
		sql.append("                          FROM \n" );
		//--期初
		sql.append("                               (SELECT S.PART_ID, S.ORG_ID, S.AMOUNT QC\n" );
		sql.append("                                  FROM PT_BU_DEALER_STOCK_STATISTICS S\n" );
		sql.append("                                 WHERE S.ORG_ID IS NOT NULL\n" );
		if(!"".equals(ocode)){
			sql.append(" AND S.ORG_CODE LIKE '%" + ocode + "%'\n" );
		}
		if(!"".equals(oname)){
			sql.append(" AND S.ORG_NAME LIKE '%" + oname + "%'\n" );
		}
		sql.append("                                   AND S.STATIS_DATE = TO_DATE('"+bday+"', 'YYYY-MM-DD')) T1\n" );
		sql.append("                          LEFT JOIN\n" );
		//--服务站退配送中心出库
		sql.append("                         (SELECT B.PART_ID,\n" );
		sql.append("                                A.APPLY_ORG_ID ORG_ID,\n" );
		sql.append("                                SUM(AD.RETURN_COUNT) CSL\n" );
		sql.append("                           FROM PT_BA_INFO             B,\n" );
		sql.append("                                PT_BU_RETURN_APPLY     A,\n" );
		sql.append("                                PT_BU_RETURN_APPLY_DTL AD\n" );
		sql.append("                          WHERE B.PART_ID = AD.PART_ID\n" );
		sql.append("                            AND A.RETURN_ID = AD.RETURN_ID\n" );
		sql.append("                            AND A.APPLY_ORG_ID IN\n" );
		sql.append("                                (SELECT O.ORG_ID\n" );
		sql.append("                                   FROM TM_ORG O\n" );
		sql.append("                                  WHERE O.ORG_TYPE = 200005)\n" );
		sql.append("                            AND A.IN_STOCK_DATE >= TO_DATE('"+bday+"', 'YYYY-MM-DD')\n" );
		sql.append("                            AND A.IN_STOCK_DATE <= TO_DATE('"+eday+"', 'YYYY-MM-DD')\n" );
		if(!"".equals(ocode)){
			sql.append(" AND A.APPLY_ORG_CODE LIKE '%" + ocode + "%'\n" );
		}
		if(!"".equals(oname)){
			sql.append(" AND A.APPLY_ORG_NAME LIKE '%" + oname + "%'\n" );
		}
		sql.append("                          GROUP BY B.PART_ID, A.APPLY_ORG_ID) T2\n" );
		sql.append("                            ON T1.PART_ID = T2.PART_ID\n" );
		sql.append("                           AND T1.ORG_ID = T2.ORG_ID) T3\n" );
		sql.append("                  LEFT JOIN\n" );
		//--实销出库
		sql.append("                 (SELECT B.PART_ID, S.ORG_ID, SUM(SD.SALE_COUNT) CSL\n" );
		sql.append("                   FROM PT_BA_INFO          B,\n" );
		sql.append("                        PT_BU_REAL_SALE     S,\n" );
		sql.append("                        PT_BU_REAL_SALE_DTL SD,\n" );
		sql.append("                        TM_ORG              O\n" );
		sql.append("                  WHERE B.PART_ID = SD.PART_ID\n" );
		sql.append("                    AND S.SALE_ID = SD.SALE_ID\n" );
		sql.append("                    AND O.ORG_ID = S.ORG_ID\n" );
		sql.append("                    AND S.ORG_ID IN\n" );
		sql.append("                        (SELECT O.ORG_ID\n" );
		sql.append("                           FROM TM_ORG O\n" );
		sql.append("                          WHERE O.ORG_TYPE IN (200006, 200007))\n" );
		sql.append("                    AND S.SALE_DATE >= TO_DATE('"+bday+"', 'YYYY-MM-DD')\n" );
		sql.append("                    AND S.SALE_DATE <= TO_DATE('"+eday+"', 'YYYY-MM-DD')\n" );
		if(!"".equals(ocode)){
			sql.append(" AND O.CODE LIKE '%" + ocode + "%'\n" );
		}
		if(!"".equals(oname)){
			sql.append(" AND O.ONAME LIKE '%" + oname + "%'\n" );
		}
		sql.append("                  GROUP BY B.PART_ID, S.ORG_ID) T4\n" );
		sql.append("                    ON T3.PART_ID = T4.PART_ID\n" );
		sql.append("                   AND T3.ORG_ID = T4.ORG_ID) T5\n" );
		sql.append("          LEFT JOIN\n" );
		//--订单入库
		sql.append("         (SELECT B.PART_ID, O.ORG_ID, SUM(OD.ORDER_COUNT) RSL\n" );
		sql.append("           FROM PT_BA_INFO B, PT_BU_SALE_ORDER O, PT_BU_SALE_ORDER_DTL OD\n" );
		sql.append("          WHERE OD.PART_ID = B.PART_ID\n" );
		sql.append("            AND OD.ORDER_ID = O.ORDER_ID\n" );
		sql.append("            AND O.ORG_ID IN\n" );
		sql.append("                (SELECT O.ORG_ID\n" );
		sql.append("                   FROM TM_ORG O\n" );
		sql.append("                  WHERE O.ORG_TYPE IN (200006, 200007))\n" );
		sql.append("            AND O.CLOSE_DATE >= TO_DATE('"+bday+"', 'YYYY-MM-DD')\n" );
		sql.append("            AND O.CLOSE_DATE <= TO_DATE('"+eday+"', 'YYYY-MM-DD')\n" );
		if(!"".equals(ocode)){
			sql.append(" AND O.ORG_CODE LIKE '%" + ocode + "%'\n" );
		}
		if(!"".equals(oname)){
			sql.append(" AND O.ORG_NAME LIKE '%" + oname + "%'\n" );
		}
		sql.append("          GROUP BY B.PART_ID, O.ORG_ID) T6\n" );
		sql.append("            ON T5.PART_ID = T6.PART_ID\n" );
		sql.append("           AND T5.ORG_ID = T6.ORG_ID) T7,\n" );
		sql.append("       PT_BA_INFO T8,\n" );
		sql.append("       TM_ORG T9\n" );
		sql.append(" WHERE T7.PART_ID = T8.PART_ID\n" );
		sql.append("   AND T7.ORG_ID = T9.ORG_ID\n" );
		sql.append("UNION ALL\n" );
		//----配送中心----
		sql.append("SELECT T15.PART_CODE, T15.PART_NAME, T14.CODE, T14.ONAME, T13.*\n" );
		sql.append("  FROM (SELECT T11.PART_ID,\n" );
		sql.append("               T11.ORG_ID,\n" );
		sql.append("               T11.QC,\n" );
		sql.append("               T11.CSL,\n" );
		sql.append("               (NVL(T11.RSL, 0) + NVL(T12.RSL, 0)) RSL,\n" );
		sql.append("               (NVL(T11.QC,0) - NVL(T11.CSL,0) + NVL(T11.RSL, 0) + NVL(T12.RSL, 0)) QM\n" );
		sql.append("          FROM (SELECT T9.PART_ID,\n" );
		sql.append("                       T9.ORG_ID,\n" );
		sql.append("                       T9.QC,\n" );
		sql.append("                       T9.CSL,\n" );
		sql.append("                       (NVL(T9.RSL, 0) + NVL(T10.RSL, 0)) RSL\n" );
		sql.append("                  FROM (SELECT T7.PART_ID, T7.ORG_ID, T7.QC, T7.CSL, T8.RSL\n" );
		sql.append("                          FROM (SELECT T5.PART_ID,\n" );
		sql.append("                                       T5.ORG_ID,\n" );
		sql.append("                                       T5.QC,\n" );
		sql.append("                                       (NVL(T5.CSL, 0) + NVL(T6.CSL, 0)) CSL\n" );
		sql.append("                                  FROM (SELECT T3.PART_ID,\n" );
		sql.append("                                               T3.ORG_ID,\n" );
		sql.append("                                               T3.QC,\n" );
		sql.append("                                               (NVL(T3.CSL, 0) + NVL(T4.CSL, 0)) CSL\n" );
		sql.append("                                          FROM (SELECT T1.PART_ID,\n" );
		sql.append("                                                       T1.ORG_ID,\n" );
		sql.append("                                                       T1.QC,\n" );
		sql.append("                                                       T2.CSL\n" );
		sql.append("                                                  FROM \n" );
		//--期初
		sql.append("                                                       (SELECT S.PART_ID,\n" );
		sql.append("                                                               S.ORG_ID,\n" );
		sql.append("                                                               S.AMOUNT QC\n" );
		sql.append("                                                          FROM PT_BU_DEALER_STOCK_STATISTICS S\n" );
		sql.append("                                                         WHERE S.ORG_ID IS NOT NULL\n" );
		if(!"".equals(ocode)){
			sql.append(" AND S.ORG_CODE LIKE '%" + ocode + "%'\n" );
		}
		if(!"".equals(oname)){
			sql.append(" AND S.ORG_NAME LIKE '%" + oname + "%'\n" );
		}
		sql.append("                                                           AND S.STATIS_DATE = TO_DATE('"+bday+"', 'YYYY-MM-DD')) T1\n" );
		sql.append("                                                  LEFT JOIN\n" );
		//--实销出库
		sql.append("                                                 (SELECT B.PART_ID,\n" );
		sql.append("                                                        RS.ORG_ID,\n" );
		sql.append("                                                        SUM(SD.SALE_COUNT) CSL\n" );
		sql.append("                                                   FROM PT_BA_INFO          B,\n" );
		sql.append("                                                        PT_BU_REAL_SALE     RS,\n" );
		sql.append("                                                        PT_BU_REAL_SALE_DTL SD,\n" );
		sql.append("                                                        TM_ORG              O\n" );
		sql.append("                                                  WHERE B.PART_ID = SD.PART_ID\n" );
		sql.append("                                                    AND RS.SALE_ID = SD.SALE_ID\n" );
		sql.append("                                                    AND O.ORG_ID = RS.ORG_ID\n" );
		if(!"".equals(ocode)){
			sql.append(" AND O.CODE LIKE '%" + ocode + "%'\n" );
		}
		if(!"".equals(oname)){
			sql.append(" AND O.ONAME LIKE '%" + oname + "%'\n" );
		}
		sql.append("                                                    AND RS.ORG_ID IN\n" );
		sql.append("                                                        (SELECT O.ORG_ID\n" );
		sql.append("                                                           FROM TM_ORG O\n" );
		sql.append("                                                          WHERE O.ORG_TYPE = 200005)\n" );
		sql.append("                                                    AND RS.SALE_DATE >= TO_DATE('"+bday+"', 'YYYY-MM-DD')\n" );
		sql.append("                                                    AND RS.SALE_DATE <= TO_DATE('"+eday+"', 'YYYY-MM-DD')\n" );
		sql.append("                                                  GROUP BY B.PART_ID, RS.ORG_ID) T2\n" );
		sql.append("                                                    ON T1.PART_ID = T2.PART_ID\n" );
		sql.append("                                                   AND T1.ORG_ID = T2.ORG_ID) T3\n" );
		sql.append("                                          LEFT JOIN\n" );
		//--销售出库
		sql.append("                                         (SELECT B.PART_ID,\n" );
		sql.append("                                                O.ORG_ID,\n" );
		sql.append("                                                SUM(OD.ORDER_COUNT) CSL\n" );
		sql.append("                                           FROM PT_BA_INFO           B,\n" );
		sql.append("                                                PT_BU_SALE_ORDER     O,\n" );
		sql.append("                                                PT_BU_SALE_ORDER_DTL OD\n" );
		sql.append("                                          WHERE OD.PART_ID = B.PART_ID\n" );
		sql.append("                                            AND OD.ORDER_ID = O.ORDER_ID\n" );
		if(!"".equals(ocode)){
			sql.append(" AND O.ORG_CODE LIKE '%" + ocode + "%'\n" );
		}
		if(!"".equals(oname)){
			sql.append(" AND O.ORG_NAME LIKE '%" + oname + "%'\n" );
		}
		sql.append("                                            AND O.ORG_ID IN\n" );
		sql.append("                                                (SELECT O.ORG_ID\n" );
		sql.append("                                                   FROM TM_ORG O\n" );
		sql.append("                                                  WHERE O.ORG_TYPE = 200005)\n" );
		sql.append("                                            AND O.CLOSE_DATE >= TO_DATE('"+bday+"', 'YYYY-MM-DD')\n" );
		sql.append("                                            AND O.CLOSE_DATE <= TO_DATE('"+eday+"', 'YYYY-MM-DD')\n" );
		sql.append("                                          GROUP BY B.PART_ID, O.ORG_ID) T4\n" );
		sql.append("                                            ON T3.PART_ID = T4.PART_ID\n" );
		sql.append("                                           AND T3.ORG_ID = T4.ORG_ID) T5\n" );
		sql.append("                                  LEFT JOIN\n" );
		//--配送中心退件出库
		sql.append("                                 (SELECT B.PART_ID,\n" );
		sql.append("                                        A.APPLY_ORG_ID ORG_ID,\n" );
		sql.append("                                        SUM(AD.RETURN_COUNT) CSL\n" );
		sql.append("                                   FROM PT_BA_INFO             B,\n" );
		sql.append("                                        PT_BU_RETURN_APPLY     A,\n" );
		sql.append("                                        PT_BU_RETURN_APPLY_DTL AD\n" );
		sql.append("                                  WHERE B.PART_ID = AD.PART_ID\n" );
		sql.append("                                    AND A.RETURN_ID = AD.RETURN_ID\n" );
		if(!"".equals(ocode)){
			sql.append(" AND A.APPLY_ORG_CODE LIKE '%" + ocode + "%'\n" );
		}
		if(!"".equals(oname)){
			sql.append(" AND A.APPLY_ORG_NAME LIKE '%" + oname + "%'\n" );
		}
		sql.append("                                    AND A.IN_STOCK_DATE >= TO_DATE('"+bday+"', 'YYYY-MM-DD')\n" );
		sql.append("                                    AND A.IN_STOCK_DATE <= TO_DATE('"+eday+"', 'YYYY-MM-DD')\n" );
		sql.append("                                  GROUP BY B.PART_ID, A.APPLY_ORG_ID) T6\n" );
		sql.append("                                    ON T5.PART_ID = T6.PART_ID\n" );
		sql.append("                                   AND T5.ORG_ID = T6.ORG_ID) T7\n" );
		sql.append("                          LEFT JOIN\n" );
		//--服务站退配送中心入库
		sql.append("                         (SELECT B.PART_ID,\n" );
		sql.append("                                A.RECEIVE_ORG_ID ORG_ID,\n" );
		sql.append("                                SUM(AD.RETURN_COUNT) RSL\n" );
		sql.append("                           FROM PT_BA_INFO             B,\n" );
		sql.append("                                PT_BU_RETURN_APPLY     A,\n" );
		sql.append("                                PT_BU_RETURN_APPLY_DTL AD\n" );
		sql.append("                          WHERE B.PART_ID = AD.PART_ID\n" );
		sql.append("                            AND A.RETURN_ID = AD.RETURN_ID\n" );
		sql.append("                            AND A.IN_STOCK_DATE >= TO_DATE('"+bday+"', 'YYYY-MM-DD')\n" );
		sql.append("                            AND A.IN_STOCK_DATE <= TO_DATE('"+eday+"', 'YYYY-MM-DD')\n" );
		if(!"".equals(ocode)){
			sql.append(" AND A.RECEIVE_ORG_CODE LIKE '%" + ocode + "%'\n" );
		}
		if(!"".equals(oname)){
			sql.append(" AND A.RECEIVE_ORG_NAME LIKE '%" + oname + "%'\n" );
		}
		sql.append("                          GROUP BY B.PART_ID, A.RECEIVE_ORG_ID) T8\n" );
		sql.append("                            ON T7.PART_ID = T8.PART_ID\n" );
		sql.append("                           AND T7.ORG_ID = T8.ORG_ID) T9\n" );
		sql.append("                  LEFT JOIN\n" );
		//--实销退件入库
		sql.append("                 (SELECT B.PART_ID, S.ORG_ID, SUM(R.RETURN_COUNT) RSL\n" );
		sql.append("                   FROM PT_BA_INFO             B,\n" );
		sql.append("                        PT_BU_REAL_SALE_RETURN R,\n" );
		sql.append("                        PT_BU_REAL_SALE        S,\n" );
		sql.append("                        TM_ORG                 O\n" );
		sql.append("                  WHERE B.PART_ID = R.PART_ID\n" );
		sql.append("                    AND R.SALE_ID = S.SALE_ID\n" );
		sql.append("                    AND S.ORG_ID = O.ORG_ID\n" );
		sql.append("                    AND S.SALE_DATE >= TO_DATE('"+bday+"', 'YYYY-MM-DD')\n" );
		sql.append("                    AND S.SALE_DATE <= TO_DATE('"+eday+"', 'YYYY-MM-DD')\n" );
		if(!"".equals(ocode)){
			sql.append(" AND O.CODE LIKE '%" + ocode + "%'\n" );
		}
		if(!"".equals(oname)){
			sql.append(" AND O.ONAME LIKE '%" + oname + "%'\n" );
		}
		sql.append("                    AND S.ORG_ID IN (SELECT O.ORG_ID\n" );
		sql.append("                                       FROM TM_ORG O\n" );
		sql.append("                                      WHERE O.ORG_TYPE = 200005)\n" );
		sql.append("                  GROUP BY B.PART_ID, S.ORG_ID) T10\n" );
		sql.append("                    ON T9.PART_ID = T10.PART_ID\n" );
		sql.append("                   AND T9.ORG_ID = T10.ORG_ID) T11\n" );
		sql.append("          LEFT JOIN\n" );
		//--订单入库
		sql.append("         (SELECT B.PART_ID, O.ORG_ID, SUM(OD.ORDER_COUNT) RSL\n" );
		sql.append("           FROM PT_BA_INFO B, PT_BU_SALE_ORDER O, PT_BU_SALE_ORDER_DTL OD\n" );
		sql.append("          WHERE OD.PART_ID = B.PART_ID\n" );
		sql.append("            AND OD.ORDER_ID = O.ORDER_ID\n" );
		sql.append("            AND O.CLOSE_DATE >= TO_DATE('"+bday+"', 'YYYY-MM-DD')\n" );
		sql.append("            AND O.CLOSE_DATE <= TO_DATE('"+eday+"', 'YYYY-MM-DD')\n" );
		if(!"".equals(ocode)){
			sql.append(" AND O.ORG_CODE LIKE '%" + ocode + "%'\n" );
		}
		if(!"".equals(oname)){
			sql.append(" AND O.ORG_NAME LIKE '%" + oname + "%'\n" );
		}
		sql.append("            AND O.ORG_ID IN\n" );
		sql.append("                (SELECT O.ORG_ID FROM TM_ORG O WHERE O.ORG_TYPE = 200005)\n" );
		sql.append("          GROUP BY B.PART_ID, O.ORG_ID) T12\n" );
		sql.append("            ON T11.PART_ID = T12.PART_ID\n" );
		sql.append("           AND T11.ORG_ID = T12.ORG_ID) T13,\n" );
		sql.append("       TM_ORG T14,\n" );
		sql.append("       PT_BA_INFO T15\n" );
		sql.append(" WHERE T13.ORG_ID = T14.ORG_ID\n" );
		sql.append("   AND T13.PART_ID = T15.PART_ID");

		return factory.select(null, sql.toString());
	} 
}
