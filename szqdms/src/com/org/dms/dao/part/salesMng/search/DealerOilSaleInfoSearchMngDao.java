package com.org.dms.dao.part.salesMng.search;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class DealerOilSaleInfoSearchMngDao extends BaseDAO{
	public  static final DealerOilSaleInfoSearchMngDao getInstance(ActionContext atx)
    {
		DealerOilSaleInfoSearchMngDao dao = new DealerOilSaleInfoSearchMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	
	public BaseResultSet saleSearch(PageManager page, User user, String conditions,String ORG_CODE,String BEGIN_DATE,String END_DATE) throws Exception {
        //定义返回结果集
		String wheres = conditions;
		wheres +=" AND A.ORG_CODE = B.CODE\n"+
				"ORDER BY A.ORG_CODE\n";
		page.setFilter(wheres);
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT A.*, B.ONAME \n" );
        if(!"".equals(BEGIN_DATE)&&BEGIN_DATE!=null){
        	sql.append(",'"+BEGIN_DATE+"' BEGIN_DATE\n" );
        }
        if(!"".equals(END_DATE)&&BEGIN_DATE!=null){
        	sql.append(",'"+END_DATE+"' END_DATE\n" );
        }
        sql.append("  FROM (SELECT SUM(NVL(T1.DELIVERY_COUNT, 0) * T1.UNIT_PRICE) AMOUNT,\n" );
        sql.append("               T.ORG_CODE\n" );
        sql.append("          FROM PT_BU_SALE_ORDER T, PT_BU_SALE_ORDER_DTL T1, PT_BA_INFO T2\n" );
        sql.append("         WHERE T.ORDER_ID = T1.ORDER_ID\n" );
        sql.append("           AND T1.PART_ID = T2.PART_ID\n" );
        if(!"".equals(ORG_CODE)&&ORG_CODE!=null){
        	sql.append("           AND T.ORG_CODE = '"+ORG_CODE+"'\n" );
        }
        sql.append("           AND T2.IF_OIL = 100101\n" );
        sql.append("           AND T.SHIP_STATUS >= 204806\n" );
        if(!"".equals(BEGIN_DATE)&&BEGIN_DATE!=null){
        	sql.append("           AND T.CLOSE_DATE >= TO_DATE('"+BEGIN_DATE+"', 'YYYY-MM-DD')\n" );
        }
        if(!"".equals(END_DATE)&&BEGIN_DATE!=null){
        	sql.append("           AND T.CLOSE_DATE <= TO_DATE('"+END_DATE+" 23:59:59', 'YYYY-MM-DD HH24:MI:SS')\n" );
        }
        sql.append("         GROUP BY T.ORG_CODE) A,\n" );
        sql.append("       TM_ORG B");
        bs = factory.select(sql.toString(), page);
        return bs;
    }
	public QuerySet saleDownload(String ORG_CODE,String BEGIN_DATE,String END_DATE) throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT A.*, B.ONAME \n" );
        if(!"".equals(BEGIN_DATE)&&BEGIN_DATE!=null){
        	sql.append(",'"+BEGIN_DATE+"' BEGIN_DATE\n" );
        }else{
        	sql.append(",'' BEGIN_DATE\n" );
        }
        if(!"".equals(END_DATE)&&BEGIN_DATE!=null){
        	sql.append(",'"+END_DATE+"' END_DATE\n" );
        }else{
        	sql.append(",'' AS END_DATE\n" );
        }
        sql.append("  FROM (SELECT SUM(NVL(T1.DELIVERY_COUNT, 0) * T1.UNIT_PRICE) AMOUNT,\n" );
        sql.append("               T.ORG_CODE\n" );
        sql.append("          FROM PT_BU_SALE_ORDER T, PT_BU_SALE_ORDER_DTL T1, PT_BA_INFO T2\n" );
        sql.append("         WHERE T.ORDER_ID = T1.ORDER_ID\n" );
        sql.append("           AND T1.PART_ID = T2.PART_ID\n" );
        if(!"".equals(ORG_CODE)&&ORG_CODE!=null){
        	sql.append("           AND T.ORG_CODE = '"+ORG_CODE+"'\n" );
        }
        sql.append("           AND T2.IF_OIL = 100101\n" );
        sql.append("           AND T.SHIP_STATUS >= 204806\n" );
        if(!"".equals(BEGIN_DATE)&&BEGIN_DATE!=null){
        	sql.append("           AND T.CLOSE_DATE >= TO_DATE('"+BEGIN_DATE+"', 'YYYY-MM-DD')\n" );
        }
        if(!"".equals(END_DATE)&&BEGIN_DATE!=null){
        	sql.append("           AND T.CLOSE_DATE < TO_DATE('"+END_DATE+" 23:59:59', 'YYYY-MM-DD HH24:MI:SS')\n" );
        }
        sql.append("         GROUP BY T.ORG_CODE) A,\n" );
        sql.append("       TM_ORG B");
        sql.append("    WHERE 1=1	AND A.ORG_CODE = B.CODE\n");
    	sql.append("ORDER BY A.ORG_CODE");
    	return factory.select(null, sql.toString());
    }
	
	/**
	 * 
	 * @Title: saleDownload
	 * @Description: 对原先下载方法进行重载，添加查询条件
	 * @param conditions
	 * @param ORG_CODE
	 * @param BEGIN_DATE
	 * @param END_DATE
	 * @return
	 * @throws Exception
	 * @return: QuerySet
	 */
	public QuerySet saleDownload(String conditions, String ORG_CODE,String BEGIN_DATE,String END_DATE) throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT A.*, B.ONAME \n" );
        if(!"".equals(BEGIN_DATE)&&BEGIN_DATE!=null){
        	sql.append(",'"+BEGIN_DATE+"' BEGIN_DATE\n" );
        }else{
        	sql.append(",'' BEGIN_DATE\n" );
        }
        if(!"".equals(END_DATE)&&BEGIN_DATE!=null){
        	sql.append(",'"+END_DATE+"' END_DATE\n" );
        }else{
        	sql.append(",'' AS END_DATE\n" );
        }
        sql.append("  FROM (SELECT SUM(NVL(T1.DELIVERY_COUNT, 0) * T1.UNIT_PRICE) AMOUNT,\n" );
        sql.append("               T.ORG_CODE\n" );
        sql.append("          FROM PT_BU_SALE_ORDER T, PT_BU_SALE_ORDER_DTL T1, PT_BA_INFO T2\n" );
        sql.append("         WHERE T.ORDER_ID = T1.ORDER_ID\n" );
        sql.append("           AND T1.PART_ID = T2.PART_ID\n" );
        if(!"".equals(ORG_CODE)&&ORG_CODE!=null){
        	sql.append("           AND T.ORG_CODE = '"+ORG_CODE+"'\n" );
        }
        sql.append("           AND T2.IF_OIL = 100101\n" );
        sql.append("           AND T.SHIP_STATUS >= 204806\n" );
        if(!"".equals(BEGIN_DATE)&&BEGIN_DATE!=null){
        	sql.append("           AND T.CLOSE_DATE >= TO_DATE('"+BEGIN_DATE+"', 'YYYY-MM-DD')\n" );
        }
        if(!"".equals(END_DATE)&&BEGIN_DATE!=null){
        	sql.append("           AND T.CLOSE_DATE < TO_DATE('"+END_DATE+" 23:59:59', 'YYYY-MM-DD HH24:MI:SS')\n" );
        }
        sql.append("         GROUP BY T.ORG_CODE) A,\n" );
        sql.append("       TM_ORG B");
        sql.append("    WHERE 1=1	AND A.ORG_CODE = B.CODE and " + conditions + "\n");
    	sql.append("ORDER BY A.ORG_CODE");
    	return factory.select(null, sql.toString());
    }
	
	
	public BaseResultSet storageSearch(PageManager page, User user, String conditions) throws Exception {
        //定义返回结果集
		String wheres = conditions;
		wheres +="AND A.ORG_CODE = B.CODE\n" +
				"AND A.PART_ID = C.PART_ID\n" + 
				"AND C.IF_OIL = 100101"+
				"ORDER BY A.ORG_CODE,A.ORG_CODE";
		page.setFilter(wheres);
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT A.AMOUNT,A.ORG_CODE,B.ONAME,C.PART_CODE,C.PART_NAME\n" );
        sql.append("FROM (SELECT SUM(NVL(T.AMOUNT,0)*T1.SALE_PRICE) AMOUNT,T.ORG_CODE,T.PART_ID\n" );
        sql.append("FROM PT_BU_DEALER_STOCK T,PT_BA_INFO T1\n" );
        sql.append("WHERE 1=1AND T.PART_ID = T1.PART_ID\n" );
        sql.append("AND T1.IF_OIL = 100101\n" );
        sql.append("GROUP BY T.ORG_CODE,T.PART_ID) A,TM_ORG B,PT_BA_INFO C\n" );
        bs = factory.select(sql.toString(), page);
        return bs;
    }
	public QuerySet storageDownload(String conditions) throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT A.AMOUNT, A.ORG_CODE, B.ONAME, C.PART_CODE, C.PART_NAME\n" );
		sql.append("  FROM (SELECT SUM(NVL(T.AMOUNT, 0) * T1.SALE_PRICE) AMOUNT,\n" );
		sql.append("               T.ORG_CODE,\n" );
		sql.append("               T.PART_ID\n" );
		sql.append("          FROM PT_BU_DEALER_STOCK T, PT_BA_INFO T1\n" );
		sql.append("         WHERE 1 = 1AND T.PART_ID = T1.PART_ID\n" );
		sql.append("           AND T1.IF_OIL = 100101\n" );
		sql.append("         GROUP BY T.ORG_CODE, T.PART_ID) A,\n" );
		sql.append("       TM_ORG B,\n" );
		sql.append("       PT_BA_INFO C\n" );
		sql.append(" WHERE 1 = 1\n" );
		sql.append("   AND A.ORG_CODE = B.CODE\n" );
		sql.append("   AND A.PART_ID = C.PART_ID\n" );
		sql.append("   AND C.IF_OIL = 100101 AND "+conditions+"\n" );
		sql.append(" ORDER BY A.ORG_CODE, A.ORG_CODE");
    	return factory.select(null, sql.toString());
    }

}
