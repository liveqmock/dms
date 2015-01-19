package com.org.dms.dao.part.storageMng.search;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class DealerPartChangeSearchDao extends BaseDAO{
	public  static final DealerPartChangeSearchDao getInstance(ActionContext atx)
    {
		DealerPartChangeSearchDao dao = new DealerPartChangeSearchDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	
	public BaseResultSet partChangeSearch(PageManager page, User user, String conditions,String BEGIN_DATE,String END_DATE) throws Exception
    {
    	String wheres = conditions;
    	wheres +=" ORDER BY T.ORG_ID";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.*\n" );
    	sql.append("  FROM (SELECT T1.ORG_ID,\n" );
    	sql.append("               T1.ORG_CODE,\n" );
    	sql.append("               T1.ORG_NAME,\n" );
    	sql.append("               T1.PART_CODE,\n" );
    	sql.append("               T1.PART_NAME,\n" );
    	sql.append("               T1.PART_NO,\n" );
    	sql.append("               NVL(T1.AMOUNT, 0) BEGIN_COUNT,\n" );
    	sql.append("               NVL(T3.PERIOD_IN_COUNT, 0) PERIOD_IN_COUNT,\n" );
    	sql.append("               NVL(T4.PERIOD_OUT_COUNT, 0) PERIOD_OUT_COUNT,\n" );
    	sql.append("               NVL((NVL(T1.AMOUNT, 0) + NVL(T3.PERIOD_IN_COUNT, 0) -\n" );
    	sql.append("                   NVL(T4.PERIOD_OUT_COUNT, 0)),\n" );
    	sql.append("                   0) END_COUNT\n" );
    	sql.append("          FROM PT_BU_DEALER_STOCK_STATISTICS T1,\n" );
    	sql.append("               (SELECT NVL(SUM(C.COUNT), 0) PERIOD_IN_COUNT, C.STOCK_ID\n" );
    	sql.append("                  FROM PT_BU_DEALER_STOCK_CHANGE C\n" );
    	sql.append("                 WHERE C.STORAGE_TYPE IN ("+DicConstant.QDCRKLX_02+", "+DicConstant.QDCRKLX_04+", "+DicConstant.QDCRKLX_06+")\n" );
    	sql.append("                   AND TO_CHAR(C.APPLY_DATE, 'YYYY-MM-DD') >= '"+BEGIN_DATE+"'\n" );
    	sql.append("                   AND TO_CHAR(C.APPLY_DATE, 'YYYY-MM-DD') <= '"+END_DATE+"'\n" );
    	sql.append("                 GROUP BY C.STOCK_ID) T3,\n" );
    	sql.append("               (SELECT NVL(SUM(D.COUNT), 0) PERIOD_OUT_COUNT, D.STOCK_ID\n" );
    	sql.append("                  FROM PT_BU_DEALER_STOCK_CHANGE D\n" );
    	sql.append("                 WHERE D.STORAGE_TYPE IN ("+DicConstant.QDCRKLX_01+", "+DicConstant.QDCRKLX_03+", "+DicConstant.QDCRKLX_05+")\n" );
    	sql.append("                   AND TO_CHAR(D.APPLY_DATE, 'YYYY-MM-DD') >= '"+BEGIN_DATE+"'\n" );
    	sql.append("                   AND TO_CHAR(D.APPLY_DATE, 'YYYY-MM-DD') <= '"+END_DATE+"'\n" );
    	sql.append("                 GROUP BY D.STOCK_ID) T4\n" );
    	sql.append("         WHERE 1 = 1\n" );
    	sql.append("           AND TO_CHAR(T1.STATIS_DATE, 'YYYY-MM-DD') = '"+BEGIN_DATE+"'\n" );
    	sql.append("           AND T1.STOCK_ID = T3.STOCK_ID(+)\n" );
    	sql.append("           AND T1.STOCK_ID = T4.STOCK_ID(+)) T \n");
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
	
	
	public QuerySet download(String pConditions,User user,String BEGIN_DATE,String END_DATE) throws Exception {

    	String wheres = "WHERE "+ pConditions+" ORDER BY T.ORG_ID";
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.*\n" );
    	sql.append("  FROM (SELECT T1.ORG_ID,\n" );
    	sql.append("               T1.ORG_CODE,\n" );
    	sql.append("               T1.ORG_NAME,\n" );
    	sql.append("               T1.PART_CODE,\n" );
    	sql.append("               T1.PART_NAME,\n" );
    	sql.append("               T1.PART_NO,\n" );
    	sql.append("               NVL(T1.AMOUNT, 0) BEGIN_COUNT,\n" );
    	sql.append("               NVL(T3.PERIOD_IN_COUNT, 0) PERIOD_IN_COUNT,\n" );
    	sql.append("               NVL(T4.PERIOD_OUT_COUNT, 0) PERIOD_OUT_COUNT,\n" );
    	sql.append("               NVL((NVL(T1.AMOUNT, 0) + NVL(T3.PERIOD_IN_COUNT, 0) -\n" );
    	sql.append("                   NVL(T4.PERIOD_OUT_COUNT, 0)),\n" );
    	sql.append("                   0) END_COUNT\n" );
    	sql.append("          FROM PT_BU_DEALER_STOCK_STATISTICS T1,\n" );
    	sql.append("               (SELECT NVL(SUM(C.COUNT), 0) PERIOD_IN_COUNT, C.STOCK_ID\n" );
    	sql.append("                  FROM PT_BU_DEALER_STOCK_CHANGE C\n" );
    	sql.append("                 WHERE C.STORAGE_TYPE IN ("+DicConstant.QDCRKLX_02+", "+DicConstant.QDCRKLX_04+", "+DicConstant.QDCRKLX_06+")\n" );
    	sql.append("                   AND TO_CHAR(C.APPLY_DATE, 'YYYY-MM-DD') >= '"+BEGIN_DATE+"'\n" );
    	sql.append("                   AND TO_CHAR(C.APPLY_DATE, 'YYYY-MM-DD') <= '"+END_DATE+"'\n" );
    	sql.append("                 GROUP BY C.STOCK_ID) T3,\n" );
    	sql.append("               (SELECT NVL(SUM(D.COUNT), 0) PERIOD_OUT_COUNT, D.STOCK_ID\n" );
    	sql.append("                  FROM PT_BU_DEALER_STOCK_CHANGE D\n" );
    	sql.append("                 WHERE D.STORAGE_TYPE IN ("+DicConstant.QDCRKLX_01+", "+DicConstant.QDCRKLX_03+", "+DicConstant.QDCRKLX_05+")\n" );
    	sql.append("                   AND TO_CHAR(D.APPLY_DATE, 'YYYY-MM-DD') >= '"+BEGIN_DATE+"'\n" );
    	sql.append("                   AND TO_CHAR(D.APPLY_DATE, 'YYYY-MM-DD') <= '"+END_DATE+"'\n" );
    	sql.append("                 GROUP BY D.STOCK_ID) T4\n" );
    	sql.append("         WHERE 1 = 1\n" );
    	sql.append("           AND TO_CHAR(T1.STATIS_DATE, 'YYYY-MM-DD') = '"+BEGIN_DATE+"'\n" );
    	sql.append("           AND T1.STOCK_ID = T3.STOCK_ID(+)\n" );
    	sql.append("           AND T1.STOCK_ID = T4.STOCK_ID(+)) T \n");
        //执行方法，不需要传递conn参数
        return factory.select(null, sql.toString()+wheres);
    }

}
