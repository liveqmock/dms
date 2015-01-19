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

public class CreditDistributionMngDao extends BaseDAO{
	public  static final CreditDistributionMngDao getInstance(ActionContext atx)
    {
		CreditDistributionMngDao dao = new CreditDistributionMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	/**
	 * 
	 * @date()2014年8月4日下午3:33:42
	 * @author Administrator
	 * @to_do:信用额度分配查询
	 * @param page
	 * @param user
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet searchCredit(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres +=" ORDER BY T.ORG_ID DESC \n";
//    			"AND T.STATUS = "+DicConstant.YXBS_01+"\n" +
//    					"AND T.OEM_COMPANY_ID = "+user.getOemCompanyId()+"\n" + 
//    					"AND T.ACCOUNT_TYPE = "+DicConstant.ZJZHLX_04+"\n" + 
//    					"AND (NOT EXISTS\n" + 
//    					"     (SELECT 1 FROM PT_BU_CREDIT_LINE T1 WHERE T.ORG_ID = T1.ORG_ID AND T1.STATUS = "+DicConstant.YXBS_01+") OR\n" + 
//    					"     EXISTS (SELECT 1\n" + 
//    					"               FROM PT_BU_CREDIT_LINE T2\n" + 
//    					"              WHERE T.ORG_ID = T2.ORG_ID\n" + 
//    					"                AND T2.STATUS = "+DicConstant.YXBS_02+"))"+
//    					"ORDER BY T.UPDATE_TIME DESC";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
//    	StringBuffer sql= new StringBuffer();
//    	sql.append("SELECT T.ACCOUNT_ID,\n" );
//    	sql.append("       T.AVAILABLE_AMOUNT,\n" );
//    	sql.append("       T.BALANCE_AMOUNT,\n" );
//    	sql.append("       T.OCCUPY_AMOUNT,\n" );
//    	sql.append("       T.ORG_CODE,\n" );
//    	sql.append("       T.ORG_NAME,\n" );
//    	sql.append("       T.ORG_ID\n" );
//    	sql.append("  FROM PT_BU_ACCOUNT T");
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.ORG_ID,\n" );
    	sql.append("       T.ORG_CODE,\n" );
    	sql.append("       T.ORG_NAME,\n" );
    	sql.append("       T.LINE_ID,\n" );
    	sql.append("         TO_CHAR(T.BEGIN_DATE,'YYYY-MM-DD')||'~'||TO_CHAR(T.END_DATE,'YYYY-MM-DD') YS_DATE,\n" );
    	sql.append("       T.NOW_LIMIT,\n" );
    	sql.append("       T.REMARKS,\n" );
    	sql.append("       T.CREDIT_TYPE,T.STATUS,T.CREATE_USER,T.CREATE_TIME\n" );
    	sql.append("  FROM PT_BU_CREDIT_LINE T");
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("CREDIT_TYPE", "XYEDLX");
    	bs.setFieldDic("STATUS", "YXBS");
    	bs.setFieldUserID("CREATE_USER");
    	bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	return bs;
    }
	/**
	 * 
	 * @date()2014年8月4日下午3:36:21
	 * @author Administrator
	 * @to_do:新增信用额度分配
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean insertCredit(PtBuCreditLineVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }
	/**
	 * 
	 * @date()2014年8月4日下午3:36:54
	 * @author Administrator
	 * @to_do:获取信用额度帐号ID 
	 * @param ORG_ID
	 * @return
	 * @throws Exception
	 */
	public QuerySet getAccountId(String ORG_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT ACCOUNT_ID\n" );
    	sql.append("FROM PT_BU_ACCOUNT\n" );
    	sql.append("WHERE ORG_ID = "+ORG_ID+"\n" );
    	sql.append("AND ACCOUNT_TYPE ="+DicConstant.ZJZHLX_04+"");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	/**
	 * 
	 * @date()2014年8月4日下午3:48:55
	 * @author Administrator
	 * @to_do:修改渠道信用账户余额与可用余额
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
	 * @to_do:插入信用账户调整轨迹表(第一次插入调整前金额为0)
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean inserCreditLog(PtBuCreditAdjustLogVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }
	
	
	public boolean updateAccountCredit(String ORG_ID, String NOW_LIMIT, User user)
            throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE PT_BU_ACCOUNT T\n" );
        sql.append("   SET T.BALANCE_AMOUNT   = T.BALANCE_AMOUNT + "+NOW_LIMIT+",\n" );
        sql.append("       T.AVAILABLE_AMOUNT = T.AVAILABLE_AMOUNT + "+NOW_LIMIT+"\n" );
        sql.append(" WHERE T.ORG_ID = "+ORG_ID+"\n" );
        sql.append("   AND T.ACCOUNT_TYPE ="+DicConstant.ZJZHLX_04+"");
        return factory.update(sql.toString(), null);
    }
	
	public QuerySet checkInDate(String PURCHASE_TYPE) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT 1\n" );
    	sql.append("  FROM PT_BU_CREDIT_LINE T\n" );
    	sql.append(" WHERE 1=1 AND T.CREDIT_TYPE = "+DicConstant.XYEDLX_01+"\n" );
    	sql.append("   AND T.STATUS = "+DicConstant.YXBS_01+"\n" );
    	sql.append("   AND T.BEGIN_DATE<= SYSDATE\n" );
    	sql.append("   AND T.END_DATE>=SYSDATE");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }

}
