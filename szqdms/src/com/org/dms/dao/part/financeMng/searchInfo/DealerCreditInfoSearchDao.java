package com.org.dms.dao.part.financeMng.searchInfo;


import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class DealerCreditInfoSearchDao extends BaseDAO{
	
	public  static final DealerCreditInfoSearchDao getInstance(ActionContext atx)
    {
		DealerCreditInfoSearchDao dao = new DealerCreditInfoSearchDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	
	public BaseResultSet creditSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres +="AND T.ACCOUNT_TYPE = "+DicConstant.ZJZHLX_04+"\n" +
    					"AND T.ORG_CODE = T1.ORG_CODE(+)\n" + 
    					"AND T.ORG_ID = O1.ORG_ID\n" + 
    					"AND O1.PID = O2.ORG_ID ORDER BY ORG_CODE ";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT O2.CODE            P_CODE,\n" );
    	sql.append("       O2.ONAME           P_NAME,\n" );
    	sql.append("       T.ORG_CODE,\n" );
    	sql.append("       T.ORG_NAME,\n" );
    	sql.append("       T.BALANCE_AMOUNT,\n" );
    	sql.append("       T.AVAILABLE_AMOUNT,\n" );
    	sql.append("       T.OCCUPY_AMOUNT,\n" );
    	sql.append("       NVL(T1.OCC_AMOUNT,0) OCC_AMOUNT,\n" );
    	sql.append("       NVL(T.OCCUPY_AMOUNT - T1.OCC_AMOUNT,0) SHOULD_REPAY\n" );
    	sql.append("  FROM PT_BU_ACCOUNT T,\n" );
    	sql.append("       TM_ORG O1,\n" );
    	sql.append("       TM_ORG O2,\n" );
    	sql.append("       (SELECT SUM(A.OCCUPY_FUNDS) OCC_AMOUNT, B.ORG_CODE\n" );
    	sql.append("          FROM PT_BU_SALE_ORDER_OCCUPY_FUNDS A, PT_BU_SALE_ORDER B\n" );
    	sql.append("         WHERE A.ORDER_ID = B.ORDER_ID\n" );
    	sql.append("           AND A.STATUS = "+DicConstant.YXBS_01+"\n" );
    	sql.append("           AND A.ACCOUNT_TYPE = "+DicConstant.ZJZHLX_04+"\n" );
    	sql.append("           AND B.ORDER_STATUS = "+DicConstant.DDZT_06+"\n" );
    	sql.append("         GROUP BY B.ORG_CODE) T1");
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
	
	public QuerySet download(String conditions,PageManager page)throws Exception{
	    	String wheres = "WHERE "+ conditions;
	    	wheres+="AND T.ACCOUNT_TYPE = "+DicConstant.ZJZHLX_04+"\n" +
					"AND T.ORG_CODE = T1.ORG_CODE(+)\n" + 
					"AND T.ORG_ID = O1.ORG_ID\n" + 
					"AND O1.PID = O2.ORG_ID ORDER BY ORG_CODE ";
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT O2.CODE            P_CODE,\n" );
	    	sql.append("       O2.ONAME           P_NAME,\n" );
	    	sql.append("       T.ORG_CODE,\n" );
	    	sql.append("       T.ORG_NAME,\n" );
	    	sql.append("       T.BALANCE_AMOUNT,\n" );
	    	sql.append("       T.AVAILABLE_AMOUNT,\n" );
	    	sql.append("       T.OCCUPY_AMOUNT,\n" );
	    	sql.append("       NVL(T1.OCC_AMOUNT,0) OCC_AMOUNT,\n" );
	    	sql.append("       NVL(T.OCCUPY_AMOUNT - T1.OCC_AMOUNT,0) SHOULD_REPAY\n" );
	    	sql.append("  FROM PT_BU_ACCOUNT T,\n" );
	    	sql.append("       TM_ORG O1,\n" );
	    	sql.append("       TM_ORG O2,\n" );
	    	sql.append("       (SELECT SUM(A.OCCUPY_FUNDS) OCC_AMOUNT, B.ORG_CODE\n" );
	    	sql.append("          FROM PT_BU_SALE_ORDER_OCCUPY_FUNDS A, PT_BU_SALE_ORDER B\n" );
	    	sql.append("         WHERE A.ORDER_ID = B.ORDER_ID\n" );
	    	sql.append("           AND A.STATUS = "+DicConstant.YXBS_01+"\n" );
	    	sql.append("           AND A.ACCOUNT_TYPE = "+DicConstant.ZJZHLX_04+"\n" );
	    	sql.append("           AND B.ORDER_STATUS = "+DicConstant.DDZT_06+"\n" );
	    	sql.append("         GROUP BY B.ORG_CODE) T1 \n");
		    return factory.select(null, sql.toString()+wheres);
		}

}
