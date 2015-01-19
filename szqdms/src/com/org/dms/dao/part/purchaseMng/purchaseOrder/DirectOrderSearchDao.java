package com.org.dms.dao.part.purchaseMng.purchaseOrder;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class DirectOrderSearchDao extends BaseDAO{
	
	public  static final DirectOrderSearchDao getInstance(ActionContext atx)
    {
		DirectOrderSearchDao dao = new DirectOrderSearchDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	
	public BaseResultSet orderSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres +="ORDER BY ORDER_NO DESC ";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.*\n" );
    	sql.append("  FROM (SELECT T.SPLIT_NO      ORDER_NO,\n" );
    	sql.append("               T.SUPPLIER_NAME,\n" );
    	sql.append("               T.SUPPLIER_CODE,\n" );
    	sql.append("               T.SELECT_MONTH,\n" );
    	sql.append("               T1.ORDER_NO     SALE_ORDER_NO,\n" );
    	sql.append("               T1.ORDER_ID,\n" );
    	sql.append("               T1.ORG_CODE,\n" );
    	sql.append("               T1.ORG_NAME,\n" );
    	sql.append("               T1.APPLY_DATE,\n" );
    	sql.append("               T.ORDER_STATUS\n" );
    	sql.append("          FROM PT_BU_PCH_ORDER_SPLIT T,\n" );
    	sql.append("               PT_BU_SALE_ORDER      T1,\n" );
    	sql.append("               PT_BU_PCH_ORDER       T2\n" );
    	sql.append("         WHERE T2.SALE_ORDER_ID = T1.ORDER_ID\n" );
    	sql.append("           AND T.PURCHASE_ID = T2.PURCHASE_ID\n" );
    	sql.append("           AND T.ORDER_STATUS IN ("+DicConstant.CGDDZT_02+", "+DicConstant.CGDDZT_03+", "+DicConstant.CGDDZT_04+", "+DicConstant.CGDDZT_05+")) T");
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("ORDER_STATUS","CGDDZT");
    	bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
    	return bs;
    }
	
	public BaseResultSet supOrderSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres +="ORDER BY ORDER_NO DESC ";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.*\n" );
    	sql.append("  FROM (SELECT T.SPLIT_NO      ORDER_NO,\n" );
    	sql.append("               T.SUPPLIER_NAME,\n" );
    	sql.append("               T.SUPPLIER_CODE,\n" );
    	sql.append("               T.SELECT_MONTH,\n" );
    	sql.append("               T1.ORDER_NO     SALE_ORDER_NO,\n" );
    	sql.append("               T1.ORDER_ID,\n" );
    	sql.append("               T1.ORG_CODE,\n" );
    	sql.append("               T1.ORG_NAME,\n" );
    	sql.append("               T1.APPLY_DATE,\n" );
    	sql.append("               T.ORDER_STATUS\n" );
    	sql.append("          FROM PT_BU_PCH_ORDER_SPLIT T,\n" );
    	sql.append("               PT_BU_SALE_ORDER      T1,\n" );
    	sql.append("               PT_BU_PCH_ORDER       T2,\n" );
    	sql.append("               PT_BA_SUPPLIER        T3\n" );
    	sql.append("         WHERE T2.SALE_ORDER_ID = T1.ORDER_ID\n" );
    	sql.append("           AND T.PURCHASE_ID = T2.PURCHASE_ID AND T.SUPPLIER_ID = T3.SUPPLIER_ID" );
    	
		// 判断用户是否为供应商
		if (user.getOrgDept().getOrgType().equals(DicConstant.ZZLB_12)) {
			sql.append("           AND T3.ORG_ID = "+user.getOrgId()+"\n" );
		}
    	
    	sql.append("           AND T.ORDER_STATUS IN ("+DicConstant.CGDDZT_02+", "+DicConstant.CGDDZT_03+", "+DicConstant.CGDDZT_04+","+DicConstant.CGDDZT_05+")) T");
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("ORDER_STATUS","CGDDZT");
    	bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
    	return bs;
    }
	
	
	public QuerySet downloadOrder(String splitNo,String orderId)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.DTL_ID,\n" );
    	sql.append("       T2.SPLIT_NO,\n" );
    	sql.append("       T.ORDER_NO,\n" );
    	sql.append("       T.PART_ID,\n" );
    	sql.append("       T.PART_CODE,\n" );
    	sql.append("       T.PART_NAME,\n" );
    	sql.append("       T1.DIC_VALUE UNIT,\n" );
    	sql.append("       T.MIN_PACK||'/'||T1.DIC_VALUE MIN_PACK,\n" );
    	//sql.append("       T1.DIC_VALUE MIN_UNIT,\n" );
    	sql.append("       T.UNIT_PRICE,\n" );
    	sql.append("       T.IF_SUPPLIER,\n" );
    	sql.append("       T.SUPPLIER_ID,\n" );
    	sql.append("       T.SUPPLIER_CODE,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("       T.ORDER_COUNT,\n" );
    	sql.append("       T.AUDIT_COUNT,\n" );
    	sql.append("       NVL(T.DELIVERY_COUNT,0) DELIVERY_COUNT,\n" );
    	sql.append("       NVL(T.SIGN_COUNT,0) SIGN_COUNT,\n" );
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
    	//sql.append("               D.MIN_UNIT,\n" );
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
    	sql.append("               NVL(E.AMOUNT, 0) AMOUNT,\n" );
    	sql.append("               NVL(E.OCCUPY_AMOUNT, 0) OCCUPY_AMOUNT,\n" );
    	sql.append("               NVL(E.AVAILABLE_AMOUNT, 0) AVAILABLE_AMOUNT\n" );
    	sql.append("          FROM (SELECT A.DTL_ID,\n" );
    	sql.append("                       A.PART_ID,\n" );
    	sql.append("                       A.PART_CODE,\n" );
    	sql.append("                       A.PART_NAME,\n" );
    	sql.append("                       C.UNIT,\n" );
    	sql.append("                       C.MIN_PACK,\n" );
    	//sql.append("                       C.MIN_UNIT,\n" );
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
    	sql.append("                       B.ORDER_NO\n" );
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
    	sql.append("           AND D.WAREHOUSE_ID = E.WAREHOUSE_ID) T,DIC_TREE T1, ");
    	sql.append("  (SELECT SPLIT_NO\n" );
    	sql.append("  FROM PT_BU_PCH_ORDER_SPLIT\n" );
    	sql.append(" WHERE PURCHASE_ID =\n" );
    	sql.append("       (SELECT PURCHASE_ID FROM PT_BU_PCH_ORDER WHERE SALE_ORDER_ID = "+orderId+")) T2 WHERE T.UNIT = T1.ID ORDER BY PART_CODE ASC");
	    return factory.select(null, sql.toString());
	}
	
	public QuerySet getOrderName(String orderId)throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT ORG_NAME FROM PT_BU_SALE_ORDER WHERE ORDER_ID = "+orderId+"");
	    return factory.select(null, sql.toString());
	}
	
	public BaseResultSet saleOrderInfoSearch(PageManager page, User user, String conditions,String ORDER_ID) throws Exception
    {
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.ORDER_ID,\n" );
    	sql.append("       T.ORDER_NO,\n" );
    	sql.append("       T1.SPLIT_NO,\n" );
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
    	sql.append("               A.PHONE,\n" );
    	sql.append("               A.ORDER_STATUS,\n" );
    	sql.append("               A.DIRECT_TYPE_NAME,\n" );
    	sql.append("               A.SUPPLIER_NAME,\n" );
    	sql.append("               B.PROM_NAME\n" );
    	sql.append("          FROM PT_BU_SALE_ORDER A\n" );
    	sql.append("          LEFT JOIN PT_BU_PROMOTION B\n" );
    	sql.append("            ON A.PROM_ID = B.PROM_ID) T,\n" );
    	sql.append("(SELECT SPLIT_NO\n" );
    	sql.append("  FROM PT_BU_PCH_ORDER_SPLIT\n" );
    	sql.append(" WHERE PURCHASE_ID =\n" );
    	sql.append("       (SELECT PURCHASE_ID FROM PT_BU_PCH_ORDER WHERE SALE_ORDER_ID = "+ORDER_ID+")) T1");
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
		bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDic("IF_DELAY_ORDER", DicConstant.SF);
    	return bs;
    }
	
	public BaseResultSet partOrderDetailSearch(PageManager page, User user, String conditions,String orderId) throws Exception
    {
    	String wheres = conditions;
    	wheres += "  ORDER BY PART_CODE ASC\n";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.DTL_ID,\n" );
    	sql.append("       T.ORDER_NO,\n" );
    	sql.append("       T1.SPLIT_NO,\n" );
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
    	sql.append("           AND D.WAREHOUSE_ID = E.WAREHOUSE_ID) T,");
    	sql.append("  (SELECT SPLIT_NO\n" );
    	sql.append("  FROM PT_BU_PCH_ORDER_SPLIT\n" );
    	sql.append(" WHERE PURCHASE_ID =\n" );
    	sql.append("       (SELECT PURCHASE_ID FROM PT_BU_PCH_ORDER WHERE SALE_ORDER_ID = "+orderId+")) T1");
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("UNIT", "JLDW");
    	bs.setFieldDic("MIN_UNIT", "JLDW");
    	bs.setFieldDic("IF_SUPPLIER", "SF");
    	bs.setFieldUserID("USER_ACCOUNT");
    	return bs;
    }
	
	public BaseResultSet partOrderDetailSearch1(PageManager page, User user, String conditions,String orderId) throws Exception
    {
    	String wheres = conditions;
    	wheres += "  ORDER BY PART_CODE ASC\n";
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