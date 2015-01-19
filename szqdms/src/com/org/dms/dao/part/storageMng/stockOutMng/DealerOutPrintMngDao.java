package com.org.dms.dao.part.storageMng.stockOutMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuDealerPrintLogVO;
import com.org.dms.vo.part.PtBuRealSaleVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class DealerOutPrintMngDao extends BaseDAO{
	public  static final DealerOutPrintMngDao getInstance(ActionContext atx)
    {
		DealerOutPrintMngDao dao = new DealerOutPrintMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	
	public BaseResultSet outSearch(PageManager page, User user, String conditions) throws Exception
    {
		String wheres = conditions;
		wheres +="  AND NOT EXISTS (SELECT 1 FROM PT_BU_DEALER_PRINT_LOG T1 WHERE T.ORDER_ID = T1.ORDER_ID) ORDER BY ORDER_NO DESC\n";
		page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.*\n" );
    	sql.append("  FROM (SELECT T.SALE_ID ORDER_ID,\n" );
    	sql.append("               T.SALE_NO ORDER_NO,\n" );
    	sql.append("               T.CUSTOMER_NAME CUST_NAME,\n" );
    	sql.append("               T.SALE_AMOUNT ORDER_AMOUNT,\n" );
    	sql.append("               1 AS ORDER_TYPE,\n" );
    	sql.append("               '实销出库' AS OUT_TYPE\n" );
    	sql.append("          FROM PT_BU_REAL_SALE T\n" );
    	sql.append("         WHERE T.STATUS = '"+DicConstant.YXBS_01+"'\n" );
    	sql.append("           AND T.ORG_ID = '"+user.getOrgId()+"'\n" );
    	sql.append("           AND T.SALE_STATUS = '"+DicConstant.SXDZT_02+"'\n" );
    	sql.append("           AND EXISTS (SELECT 1\n" );
    	sql.append("                  FROM PT_BU_REAL_SALE A, PT_BU_REAL_SALE_DTL B\n" );
    	sql.append("                 WHERE 1 = 1\n" );
    	sql.append("                   AND A.SALE_ID = B.SALE_ID)\n" );
    	sql.append("        UNION ALL\n" );
    	sql.append("        SELECT A.ORDER_ID,\n" );
    	sql.append("               A.ORDER_NO,\n" );
    	sql.append("               A.ORG_NAME CUST_NAME,\n" );
    	sql.append("               A.ORDER_AMOUNT,\n" );
    	sql.append("               2 AS ORDER_TYPE,\n" );
    	sql.append("               '销售出库' AS OUT_TYPE\n" );
    	sql.append("          FROM PT_BU_SALE_ORDER A\n" );
    	sql.append("          where 1=1\n" );
//    	sql.append("           AND A.ORDER_STATUS = '"+DicConstant.DDZT_06+"'\n" );
//    	sql.append("           AND A.ORDER_TYPE NOT IN ('"+DicConstant.DDLX_07+"', '"+DicConstant.DDLX_09+"', '"+DicConstant.DDLX_10+"')\n" );
    	sql.append("           AND A.SHIP_STATUS IN('"+DicConstant.DDFYZT_06+"','"+DicConstant.DDFYZT_07+"')\n" );
    	sql.append("           AND A.STATUS = '"+DicConstant.YXBS_01+"'\n" );
    	sql.append("           AND A.WAREHOUSE_ID = "+user.getOrgId()+" )T\n" );
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
	
	public QuerySet queryRealInfo(User user, String ORDER_ID) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
        //执行方法，不需要传递conn参数
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.CUSTOMER_NAME CUST_NAME, T.SALE_NO OUT_NO, T1.ONAME ORG_NAME,\n" );
        sql.append("       TO_CHAR(SYSDATE, 'YYYY-MM-DD') PRINT_DATE\n" );
        sql.append("  FROM PT_BU_REAL_SALE T, TM_ORG T1\n" );
        sql.append(" WHERE T.ORG_ID = T1.ORG_ID\n" );
        sql.append("   AND T.SALE_ID = "+ORDER_ID+"");
        qs = factory.select(null,sql.toString());
        return qs;
    }
	
	public QuerySet queryRealDtlInfo(User user, String ORDER_ID) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
        //执行方法，不需要传递conn参数
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.PART_CODE,\n" );
        sql.append("       T.PART_NAME,\n" );
        sql.append("       T1.DIC_VALUE UNIT,\n" );
        sql.append("       T.SALE_PRICE,\n" );
        sql.append("       T.SALE_COUNT,\n" );
        sql.append("       T.AMOUNT\n" );
        sql.append("  FROM PT_BU_REAL_SALE_DTL T, DIC_TREE T1\n" );
        sql.append(" WHERE T.UNIT = T1.ID\n" );
        sql.append(" AND T.SALE_ID = "+ORDER_ID+" AND NVL(T.SALE_COUNT,0)>0");
        qs = factory.select(null,sql.toString());
        return qs;
    }
	
	public QuerySet querySaleInfo(User user, String ORDER_ID) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
        //执行方法，不需要传递conn参数
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.ORG_NAME CUST_NAME, T.ORDER_NO OUT_NO, T1.ONAME ORG_NAME,\n" );
        sql.append("       TO_CHAR(SYSDATE, 'YYYY-MM-DD') PRINT_DATE\n" );
        sql.append("  FROM PT_BU_SALE_ORDER T, TM_ORG T1\n" );
        sql.append(" WHERE T.WAREHOUSE_ID = T1.ORG_ID\n" );
        sql.append("   AND T.ORDER_ID = "+ORDER_ID+"");
        qs = factory.select(null,sql.toString());
        return qs;
    }
	
	public QuerySet querySaleDtlInfo(User user, String ORDER_ID) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
        //执行方法，不需要传递conn参数
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.PART_CODE,\n" );
        sql.append("       T.PART_NAME,\n" );
        sql.append("       T1.DIC_VALUE UNIT,\n" );
        sql.append("       T.DELIVERY_COUNT SALE_COUNT,\n" );
        sql.append("       T.DELIVERY_COUNT * T.UNIT_PRICE AMOUNT,\n" );
        sql.append("       T.UNIT_PRICE SALE_PRICE\n" );
        sql.append("  FROM PT_BU_SALE_ORDER_DTL T, DIC_TREE T1, PT_BA_INFO T2\n" );
        sql.append(" WHERE T.PART_ID = T2.PART_ID\n" );
        sql.append("   AND T2.UNIT = T1.ID");
        sql.append(" AND T.ORDER_ID = "+ORDER_ID+" AND NVL(T.DELIVERY_COUNT,0)>0 AND NVL(T.AUDIT_COUNT,0)>0");
        qs = factory.select(null,sql.toString());
        return qs;
    }
	
	public BaseResultSet inSearch(PageManager page, User user, String conditions) throws Exception
    {
		String wheres = conditions;
		wheres +="  AND NOT EXISTS (SELECT 1 FROM PT_BU_DEALER_PRINT_LOG T1 WHERE T.IN_ID = T1.ORDER_ID) ORDER BY IN_NO DESC\n";
		page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.*\n" );
    	sql.append("  FROM (SELECT A.ORDER_ID IN_ID,\n" );
    	sql.append("               A.ORDER_NO IN_NO,\n" );
    	sql.append("               1 AS ORDER_TYPE,\n" );
    	sql.append("               '销售订单入库' AS IN_TYPE,\n" );
    	sql.append("               A.REAL_AMOUNT AMOUNT,\n" );
    	sql.append("               A.WAREHOUSE_NAME SENDER\n" );
    	sql.append("          FROM PT_BU_SALE_ORDER A\n" );
    	sql.append("         WHERE A.STATUS = 100201\n" );
    	sql.append("           AND A.ORDER_STATUS ="+DicConstant.DDZT_06+"\n" );
    	sql.append("           AND A.SHIP_STATUS IN("+DicConstant.DDFYZT_07+")\n" );
    	sql.append("           AND A.ORG_ID = "+user.getOrgId()+"\n" );
    	sql.append("        UNION ALL\n" );
    	sql.append("        SELECT C.RETURN_ID IN_ID,\n" );
    	sql.append("               C.RETURN_NO IN_NO,\n" );
    	sql.append("               2 AS ORDER_TYPE,\n" );
    	sql.append("               '销售退件入库' AS IN_TYPE,\n" );
    	sql.append("               C.RETURN_AMOUNT AMOUNT,\n" );
    	sql.append("               C.APPLY_ORG_NAME SENDER\n" );
    	sql.append("          FROM PT_BU_RETURN_APPLY C\n" );
    	sql.append("         WHERE 1 = 1\n" );
    	sql.append("           AND C.APPLY_SATUS IN( '"+DicConstant.TJSQDZT_05+"','"+DicConstant.TJSQDZT_06+"')\n" );
    	sql.append("           AND C.RECEIVE_ORG_ID = "+user.getOrgId()+"");
    	sql.append("UNION ALL\n" );
    	sql.append("SELECT B.SALE_ID IN_ID,\n" );
    	sql.append("       B.SALE_NO IN_NO,\n" );
    	sql.append("       3 AS ORDER_TYPE,\n" );
    	sql.append("       '实销退件入库' AS IN_TYPE,\n" );
    	sql.append("       SUM(A.RETURN_AMOUNT) AMOUNT,\n" );
    	sql.append("       B.CUSTOMER_NAME SENDER\n" );
    	sql.append("  FROM PT_BU_REAL_SALE_RETURN A, PT_BU_REAL_SALE B\n" );
    	sql.append(" WHERE A.SALE_ID = B.SALE_ID\n" );
    	sql.append("   AND B.ORG_ID = "+user.getOrgId()+"\n" );
    	sql.append(" GROUP BY B.SALE_ID, B.SALE_NO, B.CUSTOMER_NAME ) T");

    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
	public QuerySet querySaleIn(User user, String ORDER_ID) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
        //执行方法，不需要传递conn参数
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.ORDER_NO IN_NO,T1.SNAME ORG_NAME,\n" );
        sql.append("       TO_CHAR(SYSDATE, 'YYYY-MM-DD') PRINT_DATE\n" );
        sql.append("FROM PT_BU_SALE_ORDER T,TM_ORG T1");
        sql.append("   WHERE 1=1 AND T.ORDER_ID = "+ORDER_ID+" AND T.ORG_CODE = T1.CODE");
        qs = factory.select(null,sql.toString());
        return qs;
    }
	public QuerySet querySaleInDtlInfo(User user, String ORDER_ID) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
        //执行方法，不需要传递conn参数
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.PART_CODE,\n" );
        sql.append("       T.PART_NAME,\n" );
        sql.append("       T1.DIC_VALUE UNIT,\n" );
        sql.append("       T.DELIVERY_COUNT SALE_COUNT,\n" );
        sql.append("       T.DELIVERY_COUNT * T.UNIT_PRICE AMOUNT,\n" );
        sql.append("       T.UNIT_PRICE SALE_PRICE\n" );
        sql.append("  FROM PT_BU_SALE_ORDER_DTL T, DIC_TREE T1, PT_BA_INFO T2\n" );
        sql.append(" WHERE T.PART_ID = T2.PART_ID\n" );
        sql.append("   AND T2.UNIT = T1.ID");
        sql.append(" AND T.ORDER_ID = "+ORDER_ID+" AND NVL(T.DELIVERY_COUNT,0)>0");
        qs = factory.select(null,sql.toString());
        return qs;
    }
	public QuerySet querySaleRetIn(User user, String ORDER_ID) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
        //执行方法，不需要传递conn参数
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT A.SNAME ORG_NAME,\n" );
        sql.append("       C.RETURN_AMOUNT AMOUNT,\n" );
        sql.append("       A.SNAME SENDER,\n" );
        sql.append("       C.RETURN_NO IN_NO,\n" );
        sql.append("       TO_CHAR(SYSDATE, 'YYYY-MM-DD') PRINT_DATE\n" );
        sql.append("  FROM PT_BU_RETURN_APPLY C,TM_ORG A,TM_ORG B\n" );
        sql.append("   WHERE 1=1 AND C.RETURN_ID = "+ORDER_ID+" AND C.RECEIVE_ORG_CODE = A.CODE AND C.APPLY_ORG_CODE = B.CODE");
        qs = factory.select(null,sql.toString());
        return qs;
    }
	public QuerySet querySaleRetInDtlInfo(User user, String ORDER_ID) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
        //执行方法，不需要传递conn参数
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.PART_CODE,\n" );
        sql.append("       T.PART_NAME,\n" );
        sql.append("       T1.DIC_VALUE UNIT,\n" );
        sql.append("       T.RETURN_COUNT SALE_COUNT,\n" );
        sql.append("       T.AMOUNT,\n" );
        sql.append("       T.SALE_PRICE\n" );
        sql.append("  FROM PT_BU_RETURN_APPLY_DTL T, DIC_TREE T1\n" );
        sql.append(" WHERE T.UNIT = T1.ID");
        sql.append(" AND NVL(T.RETURN_COUNT,0) >0 AND T.RETURN_ID = "+ORDER_ID+"");
        qs = factory.select(null,sql.toString());
        return qs;
    }
	public boolean updateRealSale(PtBuRealSaleVO vo)
            throws Exception {
        return factory.update(vo);
    }
	
	public QuerySet queryRealSaleRetIn(User user, String ORDER_ID) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
        //执行方法，不需要传递conn参数
//        StringBuffer sql= new StringBuffer();
//        sql.append("SELECT A.SNAME ORG_NAME,\n" );
//        sql.append("       C.RETURN_AMOUNT AMOUNT,\n" );
//        sql.append("       A.SNAME SENDER,\n" );
//        sql.append("       C.RETURN_NO IN_NO,\n" );
//        sql.append("       TO_CHAR(SYSDATE, 'YYYY-MM-DD') PRINT_DATE\n" );
//        sql.append("  FROM PT_BU_REAL_SALE C,TM_ORG A,TM_ORG B\n" );
//        sql.append("   WHERE 1=1 AND C.RETURN_ID = "+ORDER_ID+" AND C.RECEIVE_ORG_CODE = A.CODE AND C.APPLY_ORG_CODE = B.CODE");
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.CUSTOMER_NAME, T1.SNAME ORG_NAME, T.SALE_NO IN_NO,\n" );
        sql.append("       TO_CHAR(SYSDATE, 'YYYY-MM-DD') PRINT_DATE\n" );
        sql.append("  FROM PT_BU_REAL_SALE T, TM_ORG T1\n" );
        sql.append(" WHERE 1 = 1\n" );
        sql.append("   AND T.ORG_ID = T1.ORG_ID\n" );
        sql.append("   AND T.SALE_ID = "+ORDER_ID+"");
        qs = factory.select(null,sql.toString());
        return qs;
    }
	public QuerySet queryRealSaleRetInDtlInfo(User user, String ORDER_ID) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
        //执行方法，不需要传递conn参数
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT X.*, X.SALE_COUNT * X.SALE_PRICE AMOUNT\n" );
        sql.append("  FROM (SELECT T1.PART_CODE,\n" );
        sql.append("               T1.PART_NAME,\n" );
        sql.append("               T2.DIC_VALUE UNIT,\n" );
        sql.append("               SUM(T.RETURN_COUNT) SALE_COUNT,\n" );
        sql.append("               T1.SALE_PRICE\n" );
        sql.append("          FROM PT_BU_REAL_SALE_RETURN T, PT_BU_REAL_SALE_DTL T1, DIC_TREE T2\n" );
        sql.append("         WHERE T.SALE_ID = T1.SALE_ID\n" );
        sql.append("           AND T1.UNIT = T2.ID\n" );
        sql.append("           AND T.PART_ID = T1.PART_ID\n" );
        sql.append("           AND T.SALE_ID = "+ORDER_ID+"\n" );
        sql.append("         GROUP BY T1.PART_CODE, T1.PART_NAME, T2.DIC_VALUE, T1.SALE_PRICE) X");
        qs = factory.select(null,sql.toString());
        return qs;
    }
	public boolean insertLog(PtBuDealerPrintLogVO vo)
            throws Exception {
        return factory.insert(vo);
    }
	public BaseResultSet printInSearch(PageManager page, User user, String conditions) throws Exception
    {
		String wheres = conditions;
		wheres +=" ORDER BY IN_NO DESC\n";
		page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.*\n" );
    	sql.append("  FROM (SELECT A.ORDER_ID IN_ID,\n" );
    	sql.append("               A.ORDER_NO IN_NO,\n" );
    	sql.append("               1 AS ORDER_TYPE,\n" );
    	sql.append("               '销售订单入库' AS IN_TYPE,\n" );
    	sql.append("               A.REAL_AMOUNT AMOUNT,\n" );
    	sql.append("               A.WAREHOUSE_NAME SENDER\n" );
    	sql.append("          FROM PT_BU_SALE_ORDER A, PT_BU_SALE_ORDER_CHECK B\n" );
    	sql.append("         WHERE A.ORDER_ID = B.ORDER_ID\n" );
    	sql.append("           AND A.STATUS = 100201\n" );
    	sql.append("           AND B.CHECK_RESULT = "+DicConstant.DDZT_03+"\n" );
    	sql.append("           AND A.SHIP_STATUS IN("+DicConstant.DDFYZT_06+","+DicConstant.DDFYZT_07+")\n" );
    	sql.append("           AND A.ORG_ID = "+user.getOrgId()+"\n" );
    	sql.append("        UNION ALL\n" );
    	sql.append("        SELECT C.RETURN_ID IN_ID,\n" );
    	sql.append("               C.RETURN_NO IN_NO,\n" );
    	sql.append("               2 AS ORDER_TYPE,\n" );
    	sql.append("               '销售退件入库' AS IN_TYPE,\n" );
    	sql.append("               C.RETURN_AMOUNT AMOUNT,\n" );
    	sql.append("               C.APPLY_ORG_NAME SENDER\n" );
    	sql.append("          FROM PT_BU_RETURN_APPLY C\n" );
    	sql.append("         WHERE 1 = 1\n" );
    	sql.append("           AND C.APPLY_SATUS IN( '"+DicConstant.TJSQDZT_05+"','"+DicConstant.TJSQDZT_06+"')\n" );
    	sql.append("           AND C.RECEIVE_ORG_ID = "+user.getOrgId()+"");
    	sql.append("UNION ALL\n" );
    	sql.append("SELECT B.SALE_ID IN_ID,\n" );
    	sql.append("       B.SALE_NO IN_NO,\n" );
    	sql.append("       3 AS ORDER_TYPE,\n" );
    	sql.append("       '实销退件入库' AS IN_TYPE,\n" );
    	sql.append("       SUM(A.RETURN_AMOUNT) AMOUNT,\n" );
    	sql.append("       B.CUSTOMER_NAME SENDER\n" );
    	sql.append("  FROM PT_BU_REAL_SALE_RETURN A, PT_BU_REAL_SALE B\n" );
    	sql.append(" WHERE A.SALE_ID = B.SALE_ID\n" );
    	sql.append("   AND B.ORG_ID = "+user.getOrgId()+"\n" );
    	sql.append(" GROUP BY B.SALE_ID, B.SALE_NO, B.CUSTOMER_NAME ) T");

    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
	
	public BaseResultSet outPrintSearch(PageManager page, User user, String conditions) throws Exception
    {
		String wheres = conditions;
		wheres +="  ORDER BY ORDER_NO DESC\n";
		page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.*\n" );
    	sql.append("  FROM (SELECT T.SALE_ID ORDER_ID,\n" );
    	sql.append("               T.SALE_NO ORDER_NO,\n" );
    	sql.append("               T.CUSTOMER_NAME CUST_NAME,\n" );
    	sql.append("               T.SALE_AMOUNT ORDER_AMOUNT,\n" );
    	sql.append("               1 AS ORDER_TYPE,\n" );
    	sql.append("               '实销出库' AS OUT_TYPE\n" );
    	sql.append("          FROM PT_BU_REAL_SALE T\n" );
    	sql.append("         WHERE T.STATUS = '"+DicConstant.YXBS_01+"'\n" );
    	sql.append("           AND T.ORG_ID = '"+user.getOrgId()+"'\n" );
    	sql.append("           AND T.SALE_STATUS = '"+DicConstant.SXDZT_02+"'\n" );
    	sql.append("           AND EXISTS (SELECT 1\n" );
    	sql.append("                  FROM PT_BU_REAL_SALE A, PT_BU_REAL_SALE_DTL B\n" );
    	sql.append("                 WHERE 1 = 1\n" );
    	sql.append("                   AND A.SALE_ID = B.SALE_ID)\n" );
    	sql.append("        UNION ALL\n" );
    	sql.append("        SELECT A.ORDER_ID,\n" );
    	sql.append("               A.ORDER_NO,\n" );
    	sql.append("               A.ORG_NAME CUST_NAME,\n" );
    	sql.append("               A.ORDER_AMOUNT,\n" );
    	sql.append("               2 AS ORDER_TYPE,\n" );
    	sql.append("               '销售出库' AS OUT_TYPE\n" );
    	sql.append("          FROM PT_BU_SALE_ORDER A\n" );
    	sql.append("          where 1=1\n" );
//    	sql.append("           AND A.ORDER_STATUS = '"+DicConstant.DDZT_06+"'\n" );
//    	sql.append("           AND A.ORDER_TYPE NOT IN ('"+DicConstant.DDLX_07+"', '"+DicConstant.DDLX_09+"', '"+DicConstant.DDLX_10+"')\n" );
    	sql.append("           AND A.SHIP_STATUS IN('"+DicConstant.DDFYZT_06+"','"+DicConstant.DDFYZT_07+"')\n" );
    	sql.append("           AND A.STATUS = '"+DicConstant.YXBS_01+"'\n" );
    	sql.append("           AND A.WAREHOUSE_ID = "+user.getOrgId()+" )T\n" );
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }

}
