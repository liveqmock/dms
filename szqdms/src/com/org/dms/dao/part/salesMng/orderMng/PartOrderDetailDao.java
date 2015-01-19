package com.org.dms.dao.part.salesMng.orderMng;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class PartOrderDetailDao extends BaseDAO{
	public static final PartOrderDetailDao getInstance(ActionContext atx) {
		PartOrderDetailDao dao = new PartOrderDetailDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	/**
     * 获取订购单位
     *
     * @pConditions 查询条件
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet getOrgName(String orderId) throws Exception {
    	
    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT ORG_NAME FROM PT_BU_SALE_ORDER WHERE ORDER_ID='"+orderId+"'");

    	//执行方法，不需要传递conn参数
        return factory.select(null, sql.toString());
    }
	/**
     * 订单明细查询(导出)
     *
     * @pConditions 查询条件
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet download(String orderId) throws Exception {

    	String wheres = " ORDER BY PART_CODE ASC\n";
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.DTL_ID,\n" );
    	sql.append("       T.ORDER_NO,\n" );
    	sql.append("       T.PART_ID,\n" );
    	sql.append("       T.PART_CODE,\n" );
    	sql.append("       T.PART_NAME,\n" );
    	sql.append("       (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = T.UNIT) UNIT,\n" );
    	sql.append("       T.MIN_PACK||'/'||(SELECT DIC_VALUE FROM DIC_TREE WHERE ID = T.MIN_UNIT) MINI,\n" );
    	sql.append("       T.UNIT_PRICE,\n" );
    	sql.append("       T.IF_SUPPLIER,\n" );
    	sql.append("       T.SUPPLIER_ID,\n" );
    	sql.append("       T.SUPPLIER_CODE,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("       T.ORDER_COUNT,\n" );
    	sql.append("       T.AUDIT_COUNT,\n" );
    	sql.append("       T.DELIVERY_COUNT,\n" );
    	sql.append("       T.REMARKS,\n" );
    	sql.append("       T.SIGN_COUNT,\n" );
    	sql.append("       T.WAREHOUSE_ID,\n" );
    	sql.append("       T.AMOUNT,\n" );
    	sql.append("       T.OCCUPY_AMOUNT,\n" );
    	sql.append("       T.AVAILABLE_AMOUNT\n" );
    	sql.append("  FROM (SELECT D.DTL_ID,\n" );
    	sql.append("               D.ORDER_NO,\n" );
    	sql.append("               D.PART_ID,\n" );
    	sql.append("               D.PART_CODE,\n" );
    	sql.append("               D.PART_NAME,\n" );
    	sql.append("               D.UNIT,\n" );
    	sql.append("               D.MIN_PACK,\n" );
    	sql.append("               D.MIN_UNIT,\n" );
    	sql.append("               D.UNIT_PRICE,\n" );
    	sql.append("               D.IF_SUPPLIER,\n" );
    	sql.append("               D.SUPPLIER_ID,\n" );
    	sql.append("               D.SUPPLIER_CODE,\n" );
    	sql.append("               D.SUPPLIER_NAME,\n" );
    	sql.append("               D.ORDER_COUNT,\n" );
    	sql.append("               D.AUDIT_COUNT,\n" );
    	sql.append("               D.DELIVERY_COUNT,\n" );
    	sql.append("               D.REMARKS,\n" );
    	sql.append("               D.SIGN_COUNT,\n" );
    	sql.append("               D.WAREHOUSE_ID,\n" );
    	sql.append("               NVL(E.AMOUNT, 0) AMOUNT,\n" );
    	sql.append("               NVL(E.OCCUPY_AMOUNT, 0) OCCUPY_AMOUNT,\n" );
    	sql.append("               NVL(E.AVAILABLE_AMOUNT, 0) AVAILABLE_AMOUNT\n" );
    	sql.append("          FROM (SELECT A.DTL_ID,\n" );
    	sql.append("                       B.ORDER_NO,\n" );
    	sql.append("                       A.PART_ID,\n" );
    	sql.append("                       A.PART_CODE,\n" );
    	sql.append("                       A.PART_NAME,\n" );
    	sql.append("                       C.UNIT,\n" );
    	sql.append("                       C.MIN_PACK,\n" );
    	sql.append("                       C.MIN_UNIT,\n" );
    	sql.append("                       A.UNIT_PRICE,\n" );
    	sql.append("                       A.IF_SUPPLIER,\n" );
    	sql.append("                       A.SUPPLIER_ID,\n" );
    	sql.append("                       A.SUPPLIER_CODE,\n" );
    	sql.append("                       A.SUPPLIER_NAME,\n" );
    	sql.append("                       A.ORDER_COUNT,\n" );
    	sql.append("                       A.AUDIT_COUNT,\n" );
    	sql.append("                       A.DELIVERY_COUNT,\n" );
    	sql.append("                       A.REMARKS,\n" );
    	sql.append("                       A.SIGN_COUNT,\n" );
    	sql.append("                       B.WAREHOUSE_ID\n" );
    	sql.append("                  FROM PT_BU_SALE_ORDER_DTL A,\n" );
    	sql.append("                       PT_BU_SALE_ORDER     B,\n" );
    	sql.append("                       PT_BA_INFO           C\n" );
    	sql.append("                 WHERE 1 = 1\n" );
    	sql.append("                   AND A.ORDER_ID = B.ORDER_ID\n" );
    	sql.append("                   AND A.PART_ID = C.PART_ID\n" );
    	sql.append("                   AND A.ORDER_ID = "+orderId+") D\n" );
    	sql.append("          LEFT JOIN PT_BU_STOCK E\n" );
    	sql.append("            ON D.PART_ID = E.PART_ID\n" );
    	sql.append("           AND D.SUPPLIER_ID = E.SUPPLIER_ID\n" );
    	sql.append("           AND D.WAREHOUSE_ID = E.WAREHOUSE_ID) T");
//    	return factory.select(sql.toString(), page);
//    	bs.setFieldDic("UNIT", "JLDW");
//    	bs.setFieldDic("MIN_UNIT", "JLDW");
//    	bs.setFieldDic("IF_SUPPLIER", "SF");
//    	bs.setFieldUserID("USER_ACCOUNT");
//    	return bs;

        //执行方法，不需要传递conn参数
        return factory.select(null, sql.toString()+wheres);
    }

	public BaseResultSet saleOrderInfoSearch(PageManager page, User user, String conditions,String ORDER_ID) throws Exception
    {
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.ORDER_ID,\n" );
    	sql.append("       T.ORDER_NO,\n" );
    	sql.append("       T.ORDER_TYPE,\n" );
    	sql.append("       T.ORG_ID,\n" );
    	sql.append("       T.ORG_CODE,\n" );
    	sql.append("       T.ORG_NAME,\n" );
    	sql.append("       T.WAREHOUSE_ID,\n" );
    	sql.append("       T.WAREHOUSE_CODE,\n" );
    	sql.append("       T.WAREHOUSE_NAME,\n" );
    	sql.append("       T.EXPECT_DATE,\n" );
    	sql.append("       T.ORDER_AMOUNT,\n" );
    	sql.append("       T.APPLY_DATE,\n" );
    	sql.append("       T.CREATE_USER,\n" );
    	sql.append("       T.REMARKS,\n" );
    	sql.append("       T.IF_DELAY_ORDER,\n" );
    	sql.append("       T.PROM_ID,\n" );
    	sql.append("       T.IF_CREDIT,\n" );
    	sql.append("       T.IF_TRANS,\n" );
    	sql.append("       T.PROVINCE_CODE,\n" );
    	sql.append("       T.PROVINCE_NAME,\n" );
    	sql.append("       T.CITY_CODE,\n" );
    	sql.append("       T.CITY_NAME,\n" );
    	sql.append("       T.COUNTRY_CODE,\n" );
    	sql.append("       T.COUNTRY_NAME,\n" );
    	sql.append("       T.TRANS_TYPE,\n" );
    	sql.append("       T.ZIP_CODE,\n" );
    	sql.append("       NVL(T.VIN,'')VIN,\n" );
    	sql.append("       T.BELONG_ASSEMBLY,\n" );
    	sql.append("       T.PLAN_PRODUCE_NO,\n" );
//    	sql.append("       CASE\n" );
//    	sql.append("       WHEN T.COUNTRY_NAME IS NULL THEN\n" );
//    	sql.append("       T.CITY_NAME || T.DELIVERY_ADDR\n" );
//    	sql.append("       ELSE\n" );
//    	sql.append("       T.COUNTRY_NAME || T.DELIVERY_ADDR\n" );
//    	sql.append("       END AS DELIVERY_ADDR,\n" );
    	sql.append("       T.DELIVERY_ADDR,\n" );
    	sql.append("       T.LINK_MAN,\n" );
    	sql.append("       T.PHONE,\n" );
    	sql.append("       T.DIRECT_TYPE_NAME,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("       T.PROM_NAME\n" );
    	sql.append("  FROM (SELECT A.ORDER_ID,\n" );
    	sql.append("               A.ORDER_NO,\n" );
    	sql.append("               A.ORDER_TYPE,\n" );
    	sql.append("               A.ORG_ID,\n" );
    	sql.append("               A.ORG_CODE,\n" );
    	sql.append("               A.ORG_NAME,\n" );
    	sql.append("               A.WAREHOUSE_ID,\n" );
    	sql.append("               A.WAREHOUSE_CODE,\n" );
    	sql.append("               A.WAREHOUSE_NAME,\n" );
    	sql.append("               A.EXPECT_DATE,\n" );
    	sql.append("               A.ORDER_AMOUNT,\n" );
    	sql.append("               A.APPLY_DATE,\n" );
    	sql.append("               A.CREATE_USER,\n" );
    	sql.append("               A.REMARKS,\n" );
    	sql.append("               A.IF_DELAY_ORDER,\n" );
    	sql.append("               A.IF_CHANEL_ORDER,\n" );
    	sql.append("               A.PROM_ID,\n" );
    	sql.append("               A.IF_CREDIT,\n" );
    	sql.append("               A.BELONG_ASSEMBLY,\n" );
    	sql.append("               A.PLAN_PRODUCE_NO,\n" );
    	sql.append("               A.IF_TRANS,\n" );
    	sql.append("               A.PROVINCE_CODE,\n" );
    	sql.append("               A.PROVINCE_NAME,\n" );
    	sql.append("               A.CITY_CODE,\n" );
    	sql.append("               A.CITY_NAME,\n" );
    	sql.append("               A.COUNTRY_CODE,\n" );
    	sql.append("               A.COUNTRY_NAME,\n" );
    	sql.append("               A.TRANS_TYPE,\n" );
    	sql.append("               A.ZIP_CODE,\n" );
    	sql.append("               A.DELIVERY_ADDR,\n" );
    	sql.append("               A.LINK_MAN,\n" );
    	sql.append("               A.VIN,\n" );
    	sql.append("               A.PHONE,\n" );
    	sql.append("               A.ORDER_STATUS,\n" );
    	sql.append("               A.DIRECT_TYPE_NAME,\n" );
    	sql.append("               A.SUPPLIER_NAME,\n" );
    	sql.append("               B.PROM_NAME\n" );
    	sql.append("          FROM PT_BU_SALE_ORDER A\n" );
    	sql.append("          LEFT JOIN PT_BU_PROMOTION B\n" );
    	sql.append("            ON A.PROM_ID = B.PROM_ID) T\n" );
    	sql.append(" WHERE 1 = 1\n" );
    	sql.append("   AND T.ORDER_ID = "+ORDER_ID+"\n" );
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("ORDER_TYPE", "DDLX");
		bs.setFieldDic("ORDER_STATUS", "DDZT");
		bs.setFieldDic("TRANS_TYPE", "FYFS");
		bs.setFieldDic("IF_CREDIT", "SF");
		bs.setFieldDic("IF_TRANS", "SF");
		bs.setFieldUserID("ORG_ID");
		bs.setFieldDateFormat("EXPECT_DATE", "yyyy-MM-dd");
		bs.setFieldDic("BELONG_ASSEMBLY", "PJZCLB");
		bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDic("IF_DELAY_ORDER", DicConstant.SF);
    	return bs;
    }
	public BaseResultSet accountSearch(PageManager page, String orgId, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND T.ACCOUNT_TYPE IN ("+DicConstant.ZJZHLX_01+", "+DicConstant.ZJZHLX_02+", "+DicConstant.ZJZHLX_03+")\n";
    	wheres += " AND ORG_ID = "+orgId+"AND T.STATUS ="+DicConstant.YXBS_01+"\n";
    	wheres += " UNION SELECT 2 TYPE, NVL(SUM(AVAILABLE_AMOUNT),0) AVAILABLE_AMOUNT FROM PT_BU_ACCOUNT T\n";
    	wheres += " WHERE T.ACCOUNT_TYPE = "+DicConstant.ZJZHLX_04+" AND ORG_ID = "+orgId+"AND T.STATUS ="+DicConstant.YXBS_01+"\n";
    	page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT 1 TYPE, NVL(SUM(T.AVAILABLE_AMOUNT),0) AVAILABLE_AMOUNT\n" );
    	sql.append("  FROM PT_BU_ACCOUNT T\n" );
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
	public BaseResultSet orderFundsListSearch(PageManager page, User user, String conditions,String orderId) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND A.ACCOUNT_ID = B.ACCOUNT_ID AND B.ACCOUNT_TYPE= C.ID AND C.DIC_NAME_CODE='"+DicConstant.ZJZHLX+"' AND A.ORDER_ID = "+orderId+"\n";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT A.PAY_ID,A.ACCOUNT_ID, C.DIC_VALUE ACCOUNT_TYPE, B.AVAILABLE_AMOUNT, A.PAY_AMOUNT\n" );
    	sql.append("  FROM PT_BU_SALE_ORDER_PAY A, PT_BU_ACCOUNT B,DIC_TREE C\n" );
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
	public BaseResultSet orderCheckListSearch(PageManager page, User user, String conditions,String orderId) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND A.CHECK_ORG = B.ORG_ID AND A.CHECK_RESULT = C.ID AND C.DIC_NAME_CODE ='"+DicConstant.DDZT+"' AND A.ORDER_ID="+orderId+" ORDER BY A.CHECK_DATE DESC\n";
        page.setFilter(wheres);
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT A.CHECK_DATE,\n" );
    	sql.append("       C.DIC_VALUE  CHECK_RESULT,\n" );
    	sql.append("       B.ONAME      CHECK_ORG,\n" );
    	sql.append("       A.CHECK_USER,\n" );
    	sql.append("       A.REMARKS\n" );
    	sql.append("  FROM PT_BU_SALE_ORDER_CHECK A, TM_ORG B, DIC_TREE C\n" );
    	return factory.select(sql.toString(), page);
    }
	public BaseResultSet partOrderDetailSearch(PageManager page, User user, String conditions,String orderId) throws Exception
    {
    	String wheres = conditions;
    	wheres += " ORDER BY PART_CODE ASC\n";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.DTL_ID,\n" );
    	sql.append("       T.ORDER_NO,\n" );
    	sql.append("       T.PART_ID,\n" );
    	sql.append("       T.PART_CODE,\n" );
    	sql.append("       T.PART_NAME,\n" );
    	sql.append("       T.UNIT,\n" );
    	sql.append("       T.MIN_PACK,\n" );
    	sql.append("       T.MIN_UNIT,\n" );
    	sql.append("       T.UNIT_PRICE,\n" );
    	sql.append("       T.IF_SUPPLIER,\n" );
    	sql.append("       T.SUPPLIER_ID,\n" );
    	sql.append("       T.SUPPLIER_CODE,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("       T.ORDER_COUNT,\n" );
    	sql.append("       T.AUDIT_COUNT,\n" );
    	sql.append("       T.DELIVERY_COUNT,\n" );
    	sql.append("       T.SIGN_COUNT,\n" );
    	sql.append("       T.WAREHOUSE_ID,\n" );
    	sql.append("       T.AMOUNT,\n" );
    	sql.append("       T.REMARKS,\n" );
    	sql.append("       T.OCCUPY_AMOUNT,\n" );
    	sql.append("       T.AVAILABLE_AMOUNT\n" );
    	sql.append("  FROM (SELECT D.DTL_ID,\n" );
    	sql.append("               D.ORDER_NO,\n" );
    	sql.append("               D.PART_ID,\n" );
    	sql.append("               D.PART_CODE,\n" );
    	sql.append("               D.PART_NAME,\n" );
    	sql.append("               D.UNIT,\n" );
    	sql.append("               D.MIN_PACK,\n" );
    	sql.append("               D.MIN_UNIT,\n" );
    	sql.append("               D.UNIT_PRICE,\n" );
    	sql.append("               D.IF_SUPPLIER,\n" );
    	sql.append("               D.SUPPLIER_ID,\n" );
    	sql.append("               D.SUPPLIER_CODE,\n" );
    	sql.append("               D.SUPPLIER_NAME,\n" );
    	sql.append("               D.ORDER_COUNT,\n" );
    	sql.append("               D.AUDIT_COUNT,\n" );
    	sql.append("               D.DELIVERY_COUNT,\n" );
    	sql.append("               D.SIGN_COUNT,\n" );
    	sql.append("               D.WAREHOUSE_ID,\n" );
    	sql.append("               D.REMARKS,\n" );
    	sql.append("               NVL(E.AMOUNT, 0) AMOUNT,\n" );
    	sql.append("               NVL(E.OCCUPY_AMOUNT, 0) OCCUPY_AMOUNT,\n" );
    	sql.append("               NVL(E.AVAILABLE_AMOUNT, 0) AVAILABLE_AMOUNT\n" );
    	sql.append("          FROM (SELECT A.DTL_ID,\n" );
    	sql.append("                       A.PART_ID,\n" );
    	sql.append("                       C.PART_CODE,\n" );
    	sql.append("                       C.PART_NAME,\n" );
    	sql.append("                       C.UNIT,\n" );
    	sql.append("                       C.MIN_PACK,\n" );
    	sql.append("                       C.MIN_UNIT,\n" );
    	sql.append("                       A.UNIT_PRICE,\n" );
    	sql.append("                       A.IF_SUPPLIER,\n" );
    	sql.append("                       A.SUPPLIER_ID,\n" );
    	sql.append("                       A.SUPPLIER_CODE,\n" );
    	sql.append("                       A.SUPPLIER_NAME,\n" );
    	sql.append("                       A.ORDER_COUNT,\n" );
    	sql.append("                       A.AUDIT_COUNT,\n" );
    	sql.append("                       A.DELIVERY_COUNT,\n" );
    	sql.append("                       A.SIGN_COUNT,\n" );
    	sql.append("                       B.WAREHOUSE_ID,\n" );
    	sql.append("                       A.REMARKS,\n" );
    	sql.append("                       B.ORDER_NO \n" );
    	sql.append("                  FROM PT_BU_SALE_ORDER_DTL A,\n" );
    	sql.append("                       PT_BU_SALE_ORDER     B,\n" );
    	sql.append("                       PT_BA_INFO           C\n" );
    	sql.append("                 WHERE 1 = 1\n" );
    	sql.append("                   AND A.ORDER_ID = B.ORDER_ID\n" );
    	sql.append("                   AND A.PART_ID = C.PART_ID\n" );
    	sql.append("                   AND A.ORDER_ID = "+orderId+") D\n" );
    	sql.append("          LEFT JOIN PT_BU_STOCK E\n" );
    	sql.append("            ON D.PART_ID = E.PART_ID\n" );
    	sql.append("           AND D.SUPPLIER_ID = E.SUPPLIER_ID\n" );
    	sql.append("           AND D.WAREHOUSE_ID = E.WAREHOUSE_ID) T");
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("UNIT", "JLDW");
    	bs.setFieldDic("MIN_UNIT", "JLDW");
    	bs.setFieldDic("IF_SUPPLIER", "SF");
    	bs.setFieldUserID("USER_ACCOUNT");
    	return bs;
    }

}
