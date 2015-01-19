package com.org.dms.dao.part.financeMng.creditMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuAccountVO;
import com.org.dms.vo.part.PtBuCreditAdjustLogVO;
import com.org.dms.vo.part.PtBuCreditLineVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class CreditAdjustedMngDao extends BaseDAO{
	public  static final CreditAdjustedMngDao getInstance(ActionContext atx)
    {
		CreditAdjustedMngDao dao = new CreditAdjustedMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	/**
	 * 
	 * @date()2014年8月5日下午2:20:32
	 * @author Administrator
	 * @to_do:信用额度查询
	 * @param page
	 * @param user
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet searchCredit(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
/*    	wheres +="AND T.ORG_ID = T1.ORG_ID \n"+
    			"AND T1.ACCOUNT_ID = T2.ACCOUNT_ID(+)"+
    			"AND T1.ACCOUNT_TYPE = "+DicConstant.ZJZHLX_04+"\n"+
    			"AND T.STATUS = "+DicConstant.YXBS_01+"\n" +
				"AND T.OEM_COMPANY_ID = "+user.getOemCompanyId()+"\n" + 
				"ORDER BY T.UPDATE_TIME DESC";*/
    	wheres +="AND T.STATUS = "+DicConstant.YXBS_01+" ORDER BY ORG_ID DESC \n";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.ORG_ID,\n" );
    	sql.append("       T.ORG_CODE,\n" );
    	sql.append("       T.ORG_NAME,\n" );
    	sql.append("       T.LINE_ID,\n" );
    	sql.append("         TO_CHAR(T.BEGIN_DATE,'YYYY-MM-DD')||'~'||TO_CHAR(T.END_DATE,'YYYY-MM-DD') YS_DATE,\n" );
    	sql.append("       T.NOW_LIMIT,\n" );
    	sql.append("       T.REMARKS,\n" );
    	sql.append("       T.CREDIT_TYPE,T.CREATE_USER,T.CREATE_TIME\n" );
    	sql.append("  FROM PT_BU_CREDIT_LINE T");
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("CREDIT_TYPE", "XYEDLX");
    	bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	bs.setFieldUserID("CREATE_USER");
    	/*StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.LINE_ID,\n" );
    	sql.append("       T.ORG_ID,\n" );
    	sql.append("       T.ORG_CODE,\n" );
    	sql.append("       T.ORG_NAME,\n" );
    	sql.append("       T.NOW_LIMIT,\n" );
    	sql.append("       NVL(T2.OCCUPY_FUNDS, 0) OCCUPY_FUNDS,\n" );
    	sql.append("       T1.ACCOUNT_ID\n" );
    	sql.append("  FROM PT_BU_CREDIT_LINE T,\n" );
    	sql.append("       PT_BU_ACCOUNT T1,\n" );
    	sql.append("       (SELECT ACCOUNT_ID,\n" );
    	sql.append("               SUM(OCCUPY_FUNDS - NVL(REPAY_AMOUNT, 0)) OCCUPY_FUNDS\n" );
    	sql.append("          FROM PT_BU_SALE_ORDER_OCCUPY_FUNDS\n" );
    	sql.append("         WHERE ACCOUNT_TYPE = "+DicConstant.ZJZHLX_04+"\n" );
    	sql.append("           AND STATUS = "+DicConstant.YXBS_01+"\n" );
    	sql.append("         GROUP BY ACCOUNT_ID) T2");*/
/*    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("AMOUNT_TYPE", "ZJZHLX");*/
    	return bs;
    }
	
	public QuerySet getLimit(String ACCOUNT_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.BALANCE_AMOUNT, --总额度\n" );
    	sql.append("       T.AVAILABLE_AMOUNT, --可用额度\n" );
    	sql.append("       T1.NOW_LIMIT --信用额度表中额度\n" );
    	sql.append("  FROM PT_BU_ACCOUNT T, PT_BU_CREDIT_LINE T1\n" );
    	sql.append(" WHERE T.ORG_ID = T1.ORG_ID\n" );
    	sql.append("   AND T.ACCOUNT_TYPE = "+DicConstant.ZJZHLX_04+"\n" );
    	sql.append("   AND T.ACCOUNT_ID = "+ACCOUNT_ID+"");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	
	/**
	 * 
	 * @date()2014年8月5日下午3:45:20
	 * @author Administrator
	 * @to_do:修改信用额度表中额度
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean updateCredit(PtBuCreditLineVO vo)
            throws Exception
    {
    	return factory.update(vo);
    }
	/**
	 * 
	 * @date()2014年8月5日下午3:45:44
	 * @author Administrator
	 * @to_do:更新用户资金账户信息
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean updateAccount(PtBuAccountVO vo)
            throws Exception
    {
    	return factory.update(vo);
    }
	
	/**
	 * 
	 * @date()2014年8月4日下午4:02:23
	 * @author Administrator
	 * @to_do:插入信用账户调整轨迹表
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean inserCreditLog(PtBuCreditAdjustLogVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }

	
	public boolean updateCreditAccount(String ORG_ID, String NOW_LIMIT,String AFT_LIMIT, User user)
            throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE PT_BU_ACCOUNT T\n" );
        sql.append("   SET T.BALANCE_AMOUNT   = T.BALANCE_AMOUNT - "+NOW_LIMIT+" + "+AFT_LIMIT+",\n" );
        sql.append("       T.AVAILABLE_AMOUNT = T.AVAILABLE_AMOUNT - "+NOW_LIMIT+" + "+AFT_LIMIT+"\n" );
        sql.append(" WHERE T.ORG_ID = "+ORG_ID+"\n" );
        sql.append("   AND T.ACCOUNT_TYPE ="+DicConstant.ZJZHLX_04+"");
        return factory.update(sql.toString(), null);
    }
}
