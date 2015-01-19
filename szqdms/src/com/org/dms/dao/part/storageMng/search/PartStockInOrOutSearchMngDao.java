package com.org.dms.dao.part.storageMng.search;

import java.util.Map;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class PartStockInOrOutSearchMngDao extends BaseDAO{
	public  static final PartStockInOrOutSearchMngDao getInstance(ActionContext atx)
    {
		PartStockInOrOutSearchMngDao dao = new PartStockInOrOutSearchMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	 public BaseResultSet searchPart(PageManager page, User user,String conditions) throws Exception {

	        String wheres = conditions + " AND PART_STATUS <> "+DicConstant.PJZT_02+"";
	        page.setFilter(wheres);
	        //定义返回结果集
	        BaseResultSet baseResultSet = null;
	        StringBuilder sql= new StringBuilder();
	        sql.append(" SELECT \n" );
	        sql.append("     PART_ID,\n" );
	        sql.append("     PART_NAME,\n" );
	        sql.append("     PART_CODE\n" );
	        sql.append(" FROM \n" );
	        sql.append("     PT_BA_INFO\n" );
	        //执行方法，不需要传递conn参数
	        baseResultSet = factory.select(sql.toString(), page);
	        return baseResultSet;
	    }
	 public BaseResultSet stockSearch(PageManager page, User user, Map<String, String> hm) throws Exception {
	        //定义返回结果集
	        BaseResultSet bs = null;
	        StringBuffer sql= new StringBuffer();
	        sql.append("SELECT A.*\n" );
	        sql.append("  FROM (SELECT'"+hm.get("BEGIN_DATE")+"' AS STOCK_DATE,\n" );
	        sql.append("               0 AS IN_AMOUNT,\n" );
	        sql.append("               0 AS OUT_AMOUNT,\n" );
	        sql.append("               NVL(T.AMOUNT, 0) BALANCE_AMOUNT,\n" );
	        sql.append("               '' AS ORDER_NO,\n" );
	        sql.append("               1 AS ORDER_ID,\n" );
	        sql.append("               3 AS TYPE,\n" );
	        sql.append("               0 AS ORDER_TYPE\n" );
	        sql.append("          FROM PT_BU_STOCK_STATISTICS T\n" );
	        sql.append("         WHERE T.PART_ID = "+hm.get("PART_ID")+"\n" );
	        sql.append("           AND T.WAREHOUSE_ID = "+hm.get("WAREHOUSE_ID")+"\n" );
	        sql.append("           AND T.STATIS_DATE = TO_DATE('"+hm.get("BEGIN_DATE")+"', 'YYYY-MM-DD HH24:MI:SS')\n" );
	        sql.append("        UNION ALL\n" );
	        sql.append("        SELECT TO_CHAR(T.IN_DATE,'YYYY-MM-DD')    AS STOCK_DATE,\n" );
	        sql.append("               T1.IN_AMOUNT,\n" );
	        sql.append("               0            AS OUT_AMOUNT,\n" );
	        sql.append("               0            AS BALANCE_AMOUNT,\n" );
	        sql.append("               T.IN_NO      AS ORDER_NO,\n" );
	        sql.append("               T.IN_ID      AS ORDER_ID,\n" );
	        sql.append("               1            AS TYPE,\n" );
	        sql.append("               T.IN_TYPE    AS ORDER_TYPE\n" );
	        sql.append("          FROM PT_BU_STOCK_IN T, PT_BU_STOCK_IN_DTL T1\n" );
	        sql.append("         WHERE T.IN_ID = T1.IN_ID\n" );
	        sql.append("           AND T.WAREHOUSE_ID = "+hm.get("WAREHOUSE_ID")+"\n" );
	        sql.append("           AND T1.PART_ID = "+hm.get("PART_ID")+"\n" );
	        sql.append("           AND T.IN_DATE >= TO_DATE('"+hm.get("BEGIN_DATE")+" 00:00:00', 'YYYY-MM-DD HH24:MI:SS')\n" );
	        sql.append("           AND T.IN_DATE <= TO_DATE('"+hm.get("END_DATE")+" 23:59:59', 'YYYY-MM-DD HH24:MI:SS')\n" );
	        sql.append("        UNION ALL\n" );
	        sql.append("        SELECT TO_CHAR(T.OUT_DATE,'YYYY-MM-DD')    AS STOCK_DATE,\n" );
	        sql.append("               0             AS IN_AMOUNT,\n" );
	        sql.append("               T1.OUT_AMOUNT,\n" );
	        sql.append("               0             AS BALANCE_AMOUNT,\n" );
	        sql.append("               T.OUT_NO      AS ORDER_NO,\n" );
	        sql.append("               T.OUT_ID      AS ORDER_ID,\n" );
	        sql.append("               2             AS TYPE,\n" );
	        sql.append("               T.OUT_TYPE    AS ORDER_TYPE\n" );
	        sql.append("          FROM PT_BU_STOCK_OUT T, PT_BU_STOCK_OUT_DTL T1\n" );
	        sql.append("         WHERE T.OUT_ID = T1.OUT_ID\n" );
	        sql.append("           AND T.WAREHOUSE_ID = "+hm.get("WAREHOUSE_ID")+"\n" );
	        sql.append("           AND T1.PART_ID = "+hm.get("PART_ID")+"\n" );
	        sql.append("           AND T.OUT_DATE >= TO_DATE('"+hm.get("BEGIN_DATE")+" 00:00:00', 'YYYY-MM-DD HH24:MI:SS')\n" );
	        sql.append("           AND T.OUT_DATE <= TO_DATE('"+hm.get("END_DATE")+" 23:59:59', 'YYYY-MM-DD HH24:MI:SS')) A\n" );
	        sql.append(" ORDER BY A.STOCK_DATE");
	        bs = factory.select(sql.toString(), page);
	        bs.setFieldDateFormat("STOCK_DATE", "yyyy-MM-dd");
	        return bs;
	    }
	 
	 public BaseResultSet outOrderSearch(PageManager page, User user, String conditions,String OUT_ID ) throws Exception
	    {
	    	BaseResultSet bs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT T.PART_CODE, T.PART_NAME, NVL(T.OUT_AMOUNT,0) OUT_AMOUNT, T2.UNIT, T1.OUT_TYPE\n" );
	    	sql.append("  FROM PT_BU_STOCK_OUT_DTL T, PT_BU_STOCK_OUT T1,PT_BA_INFO T2\n" );
	    	sql.append(" WHERE T.OUT_ID = T1.OUT_ID AND T.PART_ID = T2.PART_ID\n" );
	    	sql.append("   AND T1.OUT_ID = "+OUT_ID+"");
	    	bs = factory.select(sql.toString(), page);
	    	bs.setFieldDic("UNIT", "JLDW");
	    	bs.setFieldDic("OUT_TYPE", "CKLX");
	    	return bs;
	    }
	 public BaseResultSet outOrderInfo(PageManager page, String OUT_ID, String conditions) throws Exception
	    {
	        String wheres = conditions;
	        wheres += " AND  T.OUT_ID = "+OUT_ID+"\n";
	        page.setFilter(wheres);
	        BaseResultSet bs = null;
	        StringBuffer sql= new StringBuffer();
	        sql.append("SELECT T.*\n" );
	        sql.append("  FROM (SELECT A.OUT_ID,A.OUT_NO NO, B.ORG_NAME NAME --销售出库 三包出库 直营出库\n" );
	        sql.append("          FROM PT_BU_STOCK_OUT A, PT_BU_SALE_ORDER B\n" );
	        sql.append("         WHERE A.ORDER_ID = B.ORDER_ID\n" );
	        sql.append("           AND A.OUT_TYPE IN (201401, 201402, 201406)\n" );
	        sql.append("        UNION ALL\n" );
	        sql.append("        SELECT A.OUT_ID,A.OUT_NO NO, B.WAREHOUSE_NAME NAME\n" );
	        sql.append("          FROM PT_BU_STOCK_OUT A, PT_BA_WAREHOUSE B\n" );
	        sql.append("         WHERE A.OUT_TYPE = 201403\n" );
	        sql.append("           AND A.RECEIVE_WAREHOUSE = B.WAREHOUSE_ID\n" );
	        sql.append("        UNION ALL\n" );
	        sql.append("        SELECT A.OUT_ID,A.OUT_NO NO, B.SUPPLIER_NAME NAME\n" );
	        sql.append("          FROM PT_BU_STOCK_OUT A, PT_BU_PCH_RETURN B\n" );
	        sql.append("         WHERE A.OUT_TYPE = 201404\n" );
	        sql.append("           AND A.ORDER_ID = B.RETURN_ID\n" );
	        sql.append("        UNION ALL\n" );
	        sql.append("        SELECT A.OUT_ID,A.OUT_NO， '其他' AS NAME\n" );
	        sql.append("          FROM PT_BU_STOCK_OUT A\n" );
	        sql.append("         WHERE A.OUT_TYPE = 201405) T");
	        bs = factory.select(sql.toString(), page);
	        return bs;
	    }
	 public BaseResultSet inOrderSearch(PageManager page, User user, String conditions,String IN_ID ) throws Exception
	    {
	    	BaseResultSet bs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT T.PART_CODE, T.PART_NAME, NVL(T.IN_AMOUNT,0) IN_AMOUNT, T.UNIT, T1.IN_TYPE\n" );
	    	sql.append("  FROM PT_BU_STOCK_IN_DTL T, PT_BU_STOCK_IN T1\n" );
	    	sql.append(" WHERE T.IN_ID = T1.IN_ID\n" );
	    	sql.append("   AND T1.IN_ID = "+IN_ID+"");
	    	bs = factory.select(sql.toString(), page);
	    	bs.setFieldDic("UNIT", "JLDW");
	    	bs.setFieldDic("IN_TYPE", "RKLX");
	    	return bs;
	    }
	 public BaseResultSet inOrderInfo(PageManager page, String IN_ID, String conditions) throws Exception
	    {
	        String wheres = conditions;
	        wheres += " AND  T.IN_ID = "+IN_ID+"\n";
	        page.setFilter(wheres);
	        BaseResultSet bs = null;
	        StringBuffer sql= new StringBuffer();
	        sql.append("SELECT T.*\n" );
	        sql.append("  FROM (SELECT A.IN_ID,A.IN_NO NO, B.SUPPLIER_NAME NAME --销售出库 三包出库 直营出库\n" );
	        sql.append("          FROM PT_BU_STOCK_IN A, PT_BU_PCH_ORDER_SPLIT B\n" );
	        sql.append("         WHERE A.ORDER_ID = B.SPLIT_ID\n" );
	        sql.append("           AND A.IN_TYPE = 201301\n" );
	        sql.append("        UNION ALL\n" );
	        sql.append("        SELECT A.IN_ID,A.IN_NO NO, A.WAREHOUSE_NAME NAME\n" );
	        sql.append("          FROM PT_BU_STOCK_IN A\n" );
	        sql.append("         WHERE A.IN_TYPE = 201302\n" );
	        sql.append("        UNION ALL\n" );
	        sql.append("        SELECT A.IN_ID,A.IN_NO NO, B.APPLY_ORG_NAME NAME\n" );
	        sql.append("          FROM PT_BU_STOCK_IN A, PT_BU_RETURN_APPLY B\n" );
	        sql.append("         WHERE A.IN_TYPE = 201303\n" );
	        sql.append("           AND A.ORDER_ID = B.RETURN_ID\n" );
	        sql.append("        UNION ALL\n" );
	        sql.append("        SELECT A.IN_ID,A.IN_NO， '其他' AS NAME\n" );
	        sql.append("          FROM PT_BU_STOCK_IN A\n" );
	        sql.append("         WHERE A.IN_TYPE = 201405) T");
	        bs = factory.select(sql.toString(), page);
	        return bs;
	    }
}
