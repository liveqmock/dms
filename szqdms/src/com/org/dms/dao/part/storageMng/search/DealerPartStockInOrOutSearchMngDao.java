package com.org.dms.dao.part.storageMng.search;

import java.util.Map;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class DealerPartStockInOrOutSearchMngDao extends BaseDAO{
	public  static final DealerPartStockInOrOutSearchMngDao getInstance(ActionContext atx)
    {
		DealerPartStockInOrOutSearchMngDao dao = new DealerPartStockInOrOutSearchMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	public BaseResultSet stockSearch(PageManager page, User user, Map<String, String> hm) throws Exception {
        //定义返回结果集
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT A.* FROM\n" );
        sql.append("(SELECT '"+hm.get("BEGIN_DATE")+"' AS STOCK_DATE, 0 AS IN_COUNT,0 AS OUT_COUNT,NVL(T.AMOUNT, 0) BALANCE_COUNT,'' AS ORDER_NO, 1 AS ORDER_ID,3 AS TYPE\n" );
        sql.append("FROM PT_BU_DEALER_STOCK_STATISTICS T\n" );
        sql.append("WHERE T.PART_ID = "+hm.get("PART_ID")+"\n" );
        sql.append("AND T.ORG_CODE = '"+hm.get("ORG_CODE")+"'\n" );
        sql.append("AND T.STATIS_DATE = TO_DATE('"+hm.get("BEGIN_DATE")+"', 'YYYY-MM-DD HH24:MI:SS')\n" );
        sql.append("UNION ALL\n" );
        sql.append("SELECT TO_CHAR(T.CREATE_TIME,'YYYY-MM-DD') STOCK_DATE, T.COUNT IN_COUNT,0 AS OUT_COUNT,0 AS BALANCE_COUNT,T1.ORDER_NO AS ORDER_NO, T1.ORDER_ID AS ORDER_ID,1 AS TYPE\n" );
        sql.append("FROM PT_BU_DEALER_STOCK_CHANGE T,PT_BU_SALE_ORDER T1\n" );
        sql.append("WHERE T.ORG_CODE = '"+hm.get("ORG_CODE")+"'\n" );
        sql.append("AND T.PART_ID = "+hm.get("PART_ID")+"\n" );
        sql.append("AND T.STORAGE_TYPE = 204702\n" );
        sql.append("AND T.IN_NO = T1.ORDER_NO\n" );
        sql.append("AND T.CREATE_TIME >= TO_DATE('"+hm.get("BEGIN_DATE")+"', 'YYYY-MM-DD HH24:MI:SS')\n" );
        sql.append("AND T.CREATE_TIME <= TO_DATE('"+hm.get("END_DATE")+" 23:59:59', 'YYYY-MM-DD HH24:MI:SS')\n" );
        sql.append("UNION ALL\n" );
        sql.append("SELECT TO_CHAR(T.CREATE_TIME,'YYYY-MM-DD') STOCK_DATE, T.COUNT IN_COUNT,0 AS OUT_COUNT,0 AS BALANCE_COUNT,T1.RETURN_NO  AS ORDER_NO,T1.RETURN_ID AS ORDER_ID,1 AS TYPE\n" );
        sql.append("FROM PT_BU_DEALER_STOCK_CHANGE T,PT_BU_RETURN_APPLY T1\n" );
        sql.append("WHERE T.ORG_CODE = '"+hm.get("ORG_CODE")+"'\n" );
        sql.append("AND T.PART_ID = "+hm.get("PART_ID")+"\n" );
        sql.append("AND T.STORAGE_TYPE = 204704\n" );
        sql.append("AND T.IN_NO = T1.RETURN_NO\n" );
        sql.append("AND T.CREATE_TIME >= TO_DATE('"+hm.get("BEGIN_DATE")+"', 'YYYY-MM-DD HH24:MI:SS')\n" );
        sql.append("AND T.CREATE_TIME <= TO_DATE('"+hm.get("END_DATE")+" 23:59:59', 'YYYY-MM-DD HH24:MI:SS')\n" );
        sql.append("UNION ALL\n" );
        sql.append("SELECT TO_CHAR(T.CREATE_TIME,'YYYY-MM-DD') STOCK_DATE, T.COUNT IN_COUNT,0 AS OUT_COUNT,0 AS BALANCE_COUNT,T1.SALE_NO  AS ORDER_NO,T1.SALE_ID AS ORDER_ID,1 AS TYPE\n" );
        sql.append("FROM PT_BU_DEALER_STOCK_CHANGE T,PT_BU_REAL_SALE T1\n" );
        sql.append("WHERE T.ORG_CODE = '"+hm.get("ORG_CODE")+"'\n" );
        sql.append("AND T.PART_ID = "+hm.get("PART_ID")+"\n" );
        sql.append("AND T.STORAGE_TYPE = 204706\n" );
        sql.append("AND T.IN_NO = T1.SALE_NO\n" );
        sql.append("AND T.CREATE_TIME >= TO_DATE('"+hm.get("BEGIN_DATE")+"', 'YYYY-MM-DD HH24:MI:SS')\n" );
        sql.append("AND T.CREATE_TIME <= TO_DATE('"+hm.get("END_DATE")+" 23:59:59', 'YYYY-MM-DD HH24:MI:SS')\n" );
        sql.append("UNION ALL\n" );
        sql.append("SELECT TO_CHAR(T.CREATE_TIME,'YYYY-MM-DD') STOCK_DATE, 0 AS IN_COUNT,T.COUNT AS OUT_COUNT,0 AS BALANCE_COUNT,T1.ORDER_NO AS ORDER_NO, T1.ORDER_ID AS ORDER_ID,2 AS TYPE\n" );
        sql.append("FROM PT_BU_DEALER_STOCK_CHANGE T,PT_BU_SALE_ORDER T1\n" );
        sql.append("WHERE T.ORG_CODE = '"+hm.get("ORG_CODE")+"'\n" );
        sql.append("AND T.PART_ID = "+hm.get("PART_ID")+"\n" );
        sql.append("AND T.STORAGE_TYPE = 204701\n" );
        sql.append("AND T.OUT_NO = T1.ORDER_NO\n" );
        sql.append("AND T.CREATE_TIME >= TO_DATE('"+hm.get("BEGIN_DATE")+"', 'YYYY-MM-DD HH24:MI:SS')\n" );
        sql.append("AND T.CREATE_TIME <= TO_DATE('"+hm.get("END_DATE")+" 23:59:59', 'YYYY-MM-DD HH24:MI:SS')\n" );
        sql.append("UNION ALL\n" );
        sql.append("SELECT TO_CHAR(T.CREATE_TIME,'YYYY-MM-DD') STOCK_DATE,  0 AS IN_COUNT,T.COUNT AS OUT_COUNT,0 AS BALANCE_COUNT,T1.SALE_NO AS ORDER_NO, T1.SALE_ID AS ORDER_ID,2 AS TYPE\n" );
        sql.append("FROM PT_BU_DEALER_STOCK_CHANGE T,PT_BU_REAL_SALE T1\n" );
        sql.append("WHERE T.ORG_CODE = '"+hm.get("ORG_CODE")+"'\n" );
        sql.append("AND T.PART_ID = "+hm.get("PART_ID")+"\n" );
        sql.append("AND T.STORAGE_TYPE = 204703\n" );
        sql.append("AND T.OUT_NO = T1.SALE_NO\n" );
        sql.append("AND T.CREATE_TIME >= TO_DATE('"+hm.get("BEGIN_DATE")+"', 'YYYY-MM-DD HH24:MI:SS')\n" );
        sql.append("AND T.CREATE_TIME <= TO_DATE('"+hm.get("END_DATE")+" 23:59:59', 'YYYY-MM-DD HH24:MI:SS')\n" );
        sql.append("UNION ALL\n" );
        sql.append("SELECT TO_CHAR(T.CREATE_TIME,'YYYY-MM-DD') STOCK_DATE,  0 AS IN_COUNT,T.COUNT AS OUT_COUNT,0 AS BALANCE_COUNT,T1.RETURN_NO AS ORDER_NO,T1.RETURN_ID AS ORDER_ID,2 AS TYPE\n" );
        sql.append("FROM PT_BU_DEALER_STOCK_CHANGE T,PT_BU_RETURN_APPLY T1\n" );
        sql.append("WHERE T.ORG_CODE = '"+hm.get("ORG_CODE")+"'\n" );
        sql.append("AND T.PART_ID = "+hm.get("PART_ID")+"\n" );
        sql.append("AND T.STORAGE_TYPE = 204705\n" );
        sql.append("AND T.OUT_NO = T1.RETURN_NO\n" );
        sql.append("AND T.CREATE_TIME >= TO_DATE('"+hm.get("BEGIN_DATE")+"', 'YYYY-MM-DD HH24:MI:SS')\n" );
        sql.append("AND T.CREATE_TIME <= TO_DATE('"+hm.get("END_DATE")+" 23:59:59', 'YYYY-MM-DD HH24:MI:SS')) A\n" );
        sql.append("ORDER BY A.STOCK_DATE");
        bs = factory.select(sql.toString(), page);
        bs.setFieldDateFormat("STOCK_DATE", "yyyy-MM-dd");
        return bs;
    }
	public BaseResultSet dealerStockSearch(PageManager page, User user, Map<String, String> hm) throws Exception {
        //定义返回结果集
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT A.* FROM\n" );
        sql.append("(SELECT '"+hm.get("BEGIN_DATE")+"' AS STOCK_DATE, 0 AS IN_COUNT,0 AS OUT_COUNT,NVL(T.AMOUNT, 0) BALANCE_COUNT,'' AS ORDER_NO, 1 AS ORDER_ID,3 AS TYPE\n" );
        sql.append("FROM PT_BU_DEALER_STOCK_STATISTICS T\n" );
        sql.append("WHERE T.PART_ID = "+hm.get("PART_ID")+"\n" );
        sql.append("AND T.ORG_CODE = '"+user.getOrgCode()+"'\n" );
        sql.append("AND T.STATIS_DATE = TO_DATE('"+hm.get("BEGIN_DATE")+"', 'YYYY-MM-DD HH24:MI:SS')\n" );
        sql.append("UNION ALL\n" );
        sql.append("SELECT TO_CHAR(T.CREATE_TIME,'YYYY-MM-DD') STOCK_DATE, T.COUNT IN_COUNT,0 AS OUT_COUNT,0 AS BALANCE_COUNT,T1.ORDER_NO AS ORDER_NO, T1.ORDER_ID AS ORDER_ID,1 AS TYPE\n" );
        sql.append("FROM PT_BU_DEALER_STOCK_CHANGE T,PT_BU_SALE_ORDER T1\n" );
        sql.append("WHERE T.ORG_CODE = '"+user.getOrgCode()+"'\n" );
        sql.append("AND T.PART_ID = "+hm.get("PART_ID")+"\n" );
        sql.append("AND T.STORAGE_TYPE = 204702\n" );
        sql.append("AND T.IN_NO = T1.ORDER_NO\n" );
        sql.append("AND T.CREATE_TIME >= TO_DATE('"+hm.get("BEGIN_DATE")+"', 'YYYY-MM-DD HH24:MI:SS')\n" );
        sql.append("AND T.CREATE_TIME <= TO_DATE('"+hm.get("END_DATE")+" 23:59:59', 'YYYY-MM-DD HH24:MI:SS')\n" );
        sql.append("UNION ALL\n" );
        sql.append("SELECT TO_CHAR(T.CREATE_TIME,'YYYY-MM-DD') STOCK_DATE, T.COUNT IN_COUNT,0 AS OUT_COUNT,0 AS BALANCE_COUNT,T1.RETURN_NO  AS ORDER_NO,T1.RETURN_ID AS ORDER_ID,1 AS TYPE\n" );
        sql.append("FROM PT_BU_DEALER_STOCK_CHANGE T,PT_BU_RETURN_APPLY T1\n" );
        sql.append("WHERE T.ORG_CODE = '"+user.getOrgCode()+"'\n" );
        sql.append("AND T.PART_ID = "+hm.get("PART_ID")+"\n" );
        sql.append("AND T.STORAGE_TYPE = 204704\n" );
        sql.append("AND T.IN_NO = T1.RETURN_NO\n" );
        sql.append("AND T.CREATE_TIME >= TO_DATE('"+hm.get("BEGIN_DATE")+"', 'YYYY-MM-DD HH24:MI:SS')\n" );
        sql.append("AND T.CREATE_TIME <= TO_DATE('"+hm.get("END_DATE")+" 23:59:59', 'YYYY-MM-DD HH24:MI:SS')\n" );
        sql.append("UNION ALL\n" );
        sql.append("SELECT TO_CHAR(T.CREATE_TIME,'YYYY-MM-DD') STOCK_DATE, T.COUNT IN_COUNT,0 AS OUT_COUNT,0 AS BALANCE_COUNT,T1.SALE_NO  AS ORDER_NO,T1.SALE_ID AS ORDER_ID,1 AS TYPE\n" );
        sql.append("FROM PT_BU_DEALER_STOCK_CHANGE T,PT_BU_REAL_SALE T1\n" );
        sql.append("WHERE T.ORG_CODE = '"+user.getOrgCode()+"'\n" );
        sql.append("AND T.PART_ID = "+hm.get("PART_ID")+"\n" );
        sql.append("AND T.STORAGE_TYPE = 204706\n" );
        sql.append("AND T.IN_NO = T1.SALE_NO\n" );
        sql.append("AND T.CREATE_TIME >= TO_DATE('"+hm.get("BEGIN_DATE")+"', 'YYYY-MM-DD HH24:MI:SS')\n" );
        sql.append("AND T.CREATE_TIME <= TO_DATE('"+hm.get("END_DATE")+" 23:59:59', 'YYYY-MM-DD HH24:MI:SS')\n" );
        sql.append("UNION ALL\n" );
        sql.append("SELECT TO_CHAR(T.CREATE_TIME,'YYYY-MM-DD') STOCK_DATE, 0 AS IN_COUNT,T.COUNT AS OUT_COUNT,0 AS BALANCE_COUNT,T1.ORDER_NO AS ORDER_NO, T1.ORDER_ID AS ORDER_ID,2 AS TYPE\n" );
        sql.append("FROM PT_BU_DEALER_STOCK_CHANGE T,PT_BU_SALE_ORDER T1\n" );
        sql.append("WHERE T.ORG_CODE = '"+user.getOrgCode()+"'\n" );
        sql.append("AND T.PART_ID = "+hm.get("PART_ID")+"\n" );
        sql.append("AND T.STORAGE_TYPE = 204701\n" );
        sql.append("AND T.OUT_NO = T1.ORDER_NO\n" );
        sql.append("AND T.CREATE_TIME >= TO_DATE('"+hm.get("BEGIN_DATE")+"', 'YYYY-MM-DD HH24:MI:SS')\n" );
        sql.append("AND T.CREATE_TIME <= TO_DATE('"+hm.get("END_DATE")+" 23:59:59', 'YYYY-MM-DD HH24:MI:SS')\n" );
        sql.append("UNION ALL\n" );
        sql.append("SELECT TO_CHAR(T.CREATE_TIME,'YYYY-MM-DD') STOCK_DATE,  0 AS IN_COUNT,T.COUNT AS OUT_COUNT,0 AS BALANCE_COUNT,T1.SALE_NO AS ORDER_NO, T1.SALE_ID AS ORDER_ID,2 AS TYPE\n" );
        sql.append("FROM PT_BU_DEALER_STOCK_CHANGE T,PT_BU_REAL_SALE T1\n" );
        sql.append("WHERE T.ORG_CODE = '"+user.getOrgCode()+"'\n" );
        sql.append("AND T.PART_ID = "+hm.get("PART_ID")+"\n" );
        sql.append("AND T.STORAGE_TYPE = 204703\n" );
        sql.append("AND T.OUT_NO = T1.SALE_NO\n" );
        sql.append("AND T.CREATE_TIME >= TO_DATE('"+hm.get("BEGIN_DATE")+"', 'YYYY-MM-DD HH24:MI:SS')\n" );
        sql.append("AND T.CREATE_TIME <= TO_DATE('"+hm.get("END_DATE")+" 23:59:59', 'YYYY-MM-DD HH24:MI:SS')\n" );
        sql.append("UNION ALL\n" );
        sql.append("SELECT TO_CHAR(T.CREATE_TIME,'YYYY-MM-DD') STOCK_DATE,  0 AS IN_COUNT,T.COUNT AS OUT_COUNT,0 AS BALANCE_COUNT,T1.RETURN_NO AS ORDER_NO,T1.RETURN_ID AS ORDER_ID,2 AS TYPE\n" );
        sql.append("FROM PT_BU_DEALER_STOCK_CHANGE T,PT_BU_RETURN_APPLY T1\n" );
        sql.append("WHERE T.ORG_CODE = '"+user.getOrgCode()+"'\n" );
        sql.append("AND T.PART_ID = "+hm.get("PART_ID")+"\n" );
        sql.append("AND T.STORAGE_TYPE = 204705\n" );
        sql.append("AND T.OUT_NO = T1.RETURN_NO\n" );
        sql.append("AND T.CREATE_TIME >= TO_DATE('"+hm.get("BEGIN_DATE")+"', 'YYYY-MM-DD HH24:MI:SS')\n" );
        sql.append("AND T.CREATE_TIME <= TO_DATE('"+hm.get("END_DATE")+" 23:59:59', 'YYYY-MM-DD HH24:MI:SS')) A\n" );
        sql.append("ORDER BY A.STOCK_DATE");
        bs = factory.select(sql.toString(), page);
        bs.setFieldDateFormat("STOCK_DATE", "yyyy-MM-dd");
        return bs;
    }
	public QuerySet getType(String ORDER_NO) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT DISTINCT  STORAGE_TYPE FROM PT_BU_DEALER_STOCK_CHANGE WHERE OUT_NO = '"+ORDER_NO+"'");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	public QuerySet getType1(String ORDER_NO) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT DISTINCT  STORAGE_TYPE FROM PT_BU_DEALER_STOCK_CHANGE WHERE IN_NO = '"+ORDER_NO+"'");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	/*public BaseResultSet saleOrderSearch(PageManager page, User user, String conditions,String ORDER_NO ) throws Exception
    {
    	BaseResultSet bs = null;
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("UNIT", "JLDW");
    	bs.setFieldDic("OUT_TYPE", "CKLX");
    	return bs;
    }
	public BaseResultSet realOrderSearch(PageManager page, User user, String conditions,String ORDER_NO ) throws Exception
    {
    	BaseResultSet bs = null;
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("UNIT", "JLDW");
    	bs.setFieldDic("OUT_TYPE", "CKLX");
    	return bs;
    }
	public BaseResultSet retOrderSearch(PageManager page, User user, String conditions,String ORDER_NO ) throws Exception
    {
    	BaseResultSet bs = null;
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("UNIT", "JLDW");
    	bs.setFieldDic("OUT_TYPE", "CKLX");
    	return bs;
    }*/
	public BaseResultSet outOrderSearch(PageManager page, User user, String conditions,String ORDER_NO ) throws Exception
    {
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.PART_CODE, T.PART_NAME, T.COUNT, T1.UNIT, T.STORAGE_TYPE\n" );
    	sql.append("  FROM PT_BU_DEALER_STOCK_CHANGE T, PT_BA_INFO T1\n" );
    	sql.append(" WHERE T.PART_ID = T1.PART_ID\n" );
    	sql.append("   AND T.OUT_NO = '"+ORDER_NO+"' AND T.STORAGE_TYPE IN (204701,204703,204705)");
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("UNIT", "JLDW");
    	bs.setFieldDic("STORAGE_TYPE", "QDCRKLX");
    	return bs;
    }
	public BaseResultSet saleOrderInfo(PageManager page, String ORDER_NO, String conditions) throws Exception
    {
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.ORG_NAME,T.ORDER_NO FROM PT_BU_SALE_ORDER T WHERE T.ORDER_NO = '"+ORDER_NO+"'");
        bs = factory.select(sql.toString(), page);
        return bs;
    }
	public BaseResultSet realOrderInfo(PageManager page, String ORDER_NO, String conditions) throws Exception
    {
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.CUSTOMER_NAME ORG_NAME,T.SALE_NO ORDER_NO FROM PT_BU_REAL_SALE T WHERE T.SALE_NO = '"+ORDER_NO+"'");
        bs = factory.select(sql.toString(), page);
        return bs;
    }
	public BaseResultSet retOrderInfo(PageManager page, String ORDER_NO, String conditions) throws Exception
    {
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.RECEIVE_ORG_NAME ORG_NAME,T.RETURN_NO ORDER_NO FROM PT_BU_REAL_SALE T WHERE T.RETURN_NO = '"+ORDER_NO+"'");
        bs = factory.select(sql.toString(), page);
        return bs;
    }
	public BaseResultSet inOrderSearch(PageManager page, User user, String conditions,String ORDER_NO ) throws Exception
    {
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.PART_CODE, T.PART_NAME, T.COUNT, T1.UNIT, T.STORAGE_TYPE\n" );
    	sql.append("  FROM PT_BU_DEALER_STOCK_CHANGE T, PT_BA_INFO T1\n" );
    	sql.append(" WHERE T.PART_ID = T1.PART_ID\n" );
    	sql.append("   AND T.IN_NO = '"+ORDER_NO+"' AND T.STORAGE_TYPE IN (204702,204704,204706)");
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("UNIT", "JLDW");
    	bs.setFieldDic("STORAGE_TYPE", "QDCRKLX");
    	return bs;
    }
	public BaseResultSet saleOrderInfo1(PageManager page, String ORDER_NO, String conditions) throws Exception
    {
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.WAREHOUSE_NAME ORG_NAME,T.ORDER_NO FROM PT_BU_SALE_ORDER T WHERE T.ORDER_NO = '"+ORDER_NO+"'");
        bs = factory.select(sql.toString(), page);
        return bs;
    }
	public BaseResultSet realOrderInfo1(PageManager page, String ORDER_NO, String conditions) throws Exception
    {
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.CUSTOMER_NAME ORG_NAME,T.SALE_NO ORDER_NO FROM PT_BU_REAL_SALE T WHERE T.SALE_NO = '"+ORDER_NO+"'");
        bs = factory.select(sql.toString(), page);
        return bs;
    }
	public BaseResultSet retOrderInfo1(PageManager page, String ORDER_NO, String conditions) throws Exception
    {
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.RECEIVE_ORG_NAME ORG_NAME,T.RETURN_NO ORDER_NO FROM PT_BU_REAL_SALE T WHERE T.RETURN_NO = '"+ORDER_NO+"'");
        bs = factory.select(sql.toString(), page);
        return bs;
    }
}
