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
 * ClassName: DealerSalesOrderQueryDao 
 * Function: 经销商本部订单查询
 * date: 2014年9月12日 上午10:20:31
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 */
public class DealerSalesOrderQueryDao extends BaseDAO{
	
	public static final DealerSalesOrderQueryDao getInstance(ActionContext atx){
		DealerSalesOrderQueryDao dao = new DealerSalesOrderQueryDao();
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
		sb.append(" AND ORDER_STATUS<>'"+DicConstant.DDZT_01+"' AND STATUS = '" +DicConstant.YXBS_01+ "' AND ORG_ID = '" + user.getOrgId() + "' ORDER BY ORDER_ID DESC");
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
						"      LINK_MAN,\n" + 
						"      PHONE,\n" + 
						"      CLOSE_DATE\n" + 
						"FROM PT_BU_SALE_ORDER T ";
		BaseResultSet rs = this.factory.select(strSql, page);
		rs.setFieldDic("ORDER_TYPE", "DDLX");
		rs.setFieldDic("ORDER_STATUS", "DDZT");
		rs.setFieldDic("SHIP_STATUS", "DDFYZT");
		rs.setFieldDic("INVOICE_STATUS", "KPZT");
		rs.setFieldDic("TRANS_TYPE", "FYFS");
		rs.setFieldDic("IF_DELAY_ORDER", "SF");
		rs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
		rs.setFieldDateFormat("CLOSE_DATE", "yyyy-MM-dd HH:mm:ss");
		return rs;
	}
	
	/**
     * 订单查询(导出)
     *
     * @pConditions 查询条件
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet download(String pConditions,User user) throws Exception {
    	if(pConditions.indexOf("PART_CODE") != -1 || pConditions.indexOf("PART_NAME") != -1){
			pConditions += " AND EXISTS (\n" +
							"    SELECT 1 FROM PT_BU_SALE_ORDER_DTL D WHERE D.ORDER_ID = T.ORDER_ID";
			if(pConditions.indexOf("PART_NAME") != -1){
				String partNameSql = pConditions.substring(pConditions.indexOf("PART_NAME"), pConditions.indexOf("'", pConditions.indexOf("PART_NAME") + 15) + 1);
				pConditions = pConditions.replace(" AND "+partNameSql, "");
				pConditions +=" AND " + partNameSql;
			}
			if(pConditions.indexOf("PART_CODE") != -1){
				String partNameSql = pConditions.substring(pConditions.indexOf("PART_CODE"), pConditions.indexOf("'", pConditions.indexOf("PART_CODE") + 15) + 1);
				pConditions = pConditions.replace(" AND "+partNameSql, "");
				pConditions +=" AND " + partNameSql;
			}
			pConditions += " ) ";
		}
    	StringBuilder sb = new StringBuilder(pConditions);
		sb.append(" AND ORDER_STATUS<>'"+DicConstant.DDZT_01+"' AND STATUS = '" +DicConstant.YXBS_01+ "' AND ORG_ID = '" + user.getOrgId() + "' ORDER BY ORDER_ID DESC");
//		page.setFilter(sb.toString());
		String strSql = "SELECT\n" +
						"      ORDER_ID,\n" + 
						"      ORDER_NO,\n" + 
						"      (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = ORDER_TYPE) ORDER_TYPE,\n" + 
						"      (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = ORDER_STATUS) ORDER_STATUS,\n" + 
						"      (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = SHIP_STATUS) SHIP_STATUS,\n" + 
						"      (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = INVOICE_STATUS) INVOICE_STATUS,\n" + 
						"      (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = TRANS_TYPE) TRANS_TYPE,\n" + 
						"      (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = IF_DELAY_ORDER) IF_DELAY_ORDER,\n" + 
						"      APPLY_DATE,\n" + 
						"      LINK_MAN,\n" + 
						"      PHONE,\n" + 
						"      ORDER_AMOUNT,\n" + 
						"      REAL_AMOUNT,\n" + 
						"      CLOSE_DATE\n" + 
						"FROM PT_BU_SALE_ORDER T WHERE";
        return factory.select(null, strSql+sb.toString());
    }

    /**
     * 
     * searchOrder: 添加截取查询条件
     * @author fuxiao
     * Date:2014年11月15日
     *
     */
    public BaseResultSet searchOrder(PageManager pPageManager, User pUser,String pConditions, boolean isSub) throws Exception {
    	
		if(pConditions.indexOf("PART_CODE") != -1 || pConditions.indexOf("PART_NAME") != -1){
			pConditions += " AND EXISTS (\n" +
							"    SELECT 1 FROM PT_BU_SALE_ORDER_DTL D WHERE D.ORDER_ID = T.ORDER_ID";
			if(pConditions.indexOf("PART_NAME") != -1){
				String partNameSql = pConditions.substring(pConditions.indexOf("PART_NAME"), pConditions.indexOf("'", pConditions.indexOf("PART_NAME") + 15) + 1);
				pConditions = pConditions.replace(" AND "+partNameSql, "");
				pConditions +=" AND " + partNameSql;
			}
			if(pConditions.indexOf("PART_CODE") != -1){
				String partNameSql = pConditions.substring(pConditions.indexOf("PART_CODE"), pConditions.indexOf("'", pConditions.indexOf("PART_CODE") + 15) + 1);
				pConditions = pConditions.replace(" AND "+partNameSql, "");
				pConditions +=" AND " + partNameSql;
			}
			pConditions += " ) ";
		}
    	return this.querySalesOrderIndexInfo(pPageManager, pConditions, pUser);
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
						"        TO_CHAR(T1.EXPECT_DATE, 'YYYY-MM-DD HH24:MI:SS') EXPECT_DATE,\n" + 
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
		BaseResultSet rs = null;
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
		rs = this.factory.select(strSql, page);
		rs.setFieldDic("ACCOUNT_TYPE", "ZJZHLX");
		return rs;
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
						"      (SELECT PERSON_NAME FROM TM_USER U WHERE U.ACCOUNT = CHECK_USER) CHECK_USER,\n" + 
						"      CHECK_DATE,\n" + 
						"     (SELECT DIC_VALUE FROM DIC_TREE DIC WHERE DIC.ID = CHECK_RESULT) CHECK_RESULT,\n" + 
						"     (SELECT ONAME FROM TM_ORG T WHERE T.ORG_ID = CHECK_ORG) CHECK_ORG,\n" + 
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
}
