package com.org.dms.dao.part.financeMng.remitMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuAccountLogVO;
import com.org.dms.vo.part.PtBuMoneyRemitVO;
import com.org.dms.vo.part.PtBuSaleOrderOccupyFundsVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class RemitCheckInConfirmDao extends BaseDAO{
	
	public  static final RemitCheckInConfirmDao getInstance(ActionContext atx)
    {
		RemitCheckInConfirmDao dao = new RemitCheckInConfirmDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	/**
	 * 
	 * @date()2014年8月1日上午9:00:07
	 * @author Administrator
	 * @to_do:打款查询
	 * @param page
	 * @param user
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet remitSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres +="AND T.STATUS = "+DicConstant.YXBS_01+"\n" +
    					"AND T.OEM_COMPANY_ID = "+user.getOemCompanyId()+"\n" + 
    					"AND T.ORG_ID = T1.ORG_ID"+"\n" + 
    					//"AND T1.ORG_ID = "+DicConstant.ZZLB_08+"\n" + 
    					"AND T.REMIT_STATUS = "+DicConstant.DKZT_02+"\n";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.REMIT_ID,\n" );
    	sql.append("       T.FILIING_DATE,\n" );
    	sql.append("       T.AMOUNT_TYPE,\n" );
    	sql.append("       T.DRAFT_NO,\n" );
    	sql.append("       T.BILL_AMOUNT,\n" );
    	sql.append("       T1.CODE ORG_CODE,\n" );
    	sql.append("       T1.ONAME ORG_NAME,\n" );
    	sql.append("       T.REMARK\n" );
    	sql.append("  FROM PT_BU_MONEY_REMIT T,TM_ORG T1");
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("AMOUNT_TYPE", "ZJZHLX");
		bs.setFieldDateFormat("FILIING_DATE", "yyyy-MM-dd");
    	return bs;
    }
	/**
	 * 
	 * @date()2014年8月1日上午9:00:23
	 * @author Administrator
	 * @to_do:打款确认
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean updateRemit(PtBuMoneyRemitVO vo)
            throws Exception
    {
    	return factory.update(vo);
    }
	/**
	 * 
	 * @date()2014年8月1日上午9:13:39
	 * @author Administrator
	 * @to_do:获取渠道ID账户类型打款金额
	 * @param REMIT_ID
	 * @return
	 * @throws Exception
	 */
	public QuerySet getAccount(String REMIT_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.ORG_ID, T.AMOUNT_TYPE, T.BILL_AMOUNT, T.REMARK, T1.ACCOUNT_ID,NVL(T1.OCCUPY_AMOUNT,0) OCCUPY_AMOUNT,NVL(T1.CLOSE_AMOUNT,0)-NVL(T1.PAY_CLOSE_AMOUNT,0) CLOSE_AMOUNT\n" );
    	sql.append("  FROM PT_BU_MONEY_REMIT T, PT_BU_ACCOUNT T1\n" );
    	sql.append(" WHERE T.ORG_ID = T1.ORG_ID\n" );
    	sql.append("   AND T1.ACCOUNT_TYPE = "+DicConstant.ZJZHLX_04+"\n" );
    	sql.append("   AND T.REMIT_ID = "+REMIT_ID+"");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	
	public QuerySet getAcc(String REMIT_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.AMOUNT_TYPE,T1.ACCOUNT_ID\n" );
    	sql.append("  FROM PT_BU_MONEY_REMIT T, PT_BU_ACCOUNT T1\n" );
    	sql.append(" WHERE T.ORG_ID = T1.ORG_ID AND T.AMOUNT_TYPE = T1.ACCOUNT_TYPE\n" );
    	sql.append("   AND T.REMIT_ID = "+REMIT_ID+"");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	
	public QuerySet checkCreditAll(String ACCOUNT_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT NVL(SUM(NVL(T.OCCUPY_FUNDS,0)-NVL(REPAY_AMOUNT,0)),0) ALL_OCCUPY_FUNDS\n" );
    	sql.append("  FROM PT_BU_SALE_ORDER_OCCUPY_FUNDS T\n" );
    	sql.append(" WHERE 1 = 1\n" );
    	sql.append("   AND T.ACCOUNT_TYPE = "+DicConstant.ZJZHLX_04+"\n" );
    	sql.append("   AND T.STATUS = "+DicConstant.YXBS_01+"\n" );
    	sql.append("   AND T.ACCOUNT_ID = "+ACCOUNT_ID+"");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	
	public QuerySet checkCredit(String ACCOUNT_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.OFUNDS_ID,T.ORDER_ID, (T.OCCUPY_FUNDS-NVL(REPAY_AMOUNT,0)) OCCUPY_FUNDS,NVL(REPAY_AMOUNT,0) REPAY_AMOUNT\n" );
    	sql.append("  FROM PT_BU_SALE_ORDER_OCCUPY_FUNDS T,PT_BU_SALE_ORDER T1\n" );
    	sql.append(" WHERE 1 = 1 AND T.ORDER_ID = T1.ORDER_ID AND T1.ORDER_STATUS =202206\n" );
    	sql.append("   AND T.ACCOUNT_TYPE = "+DicConstant.ZJZHLX_04+"\n" );
    	sql.append("   AND T.STATUS = "+DicConstant.YXBS_01+"\n" );
    	sql.append("   AND T.ACCOUNT_ID = "+ACCOUNT_ID+"");
    	sql.append("   ORDER BY T.CREATE_TIME");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	
	/**
	 * 
	 * @date()2014年8月1日上午9:16:17
	 * @author Administrator
	 * @to_do:修改渠道账户金额
	 * @param ORG_ID
	 * @param ACCOUNT_TYPE
	 * @param BILL_AMOUNT
	 * @return
	 * @throws Exception
	 */
	public boolean updateAccountAmount(String ORG_ID,String ACCOUNT_TYPE,String BILL_AMOUNT ) throws Exception
    {
		StringBuffer sql= new StringBuffer();
		sql.append("UPDATE PT_BU_ACCOUNT\n" );
		sql.append("   SET AVAILABLE_AMOUNT = AVAILABLE_AMOUNT + "+BILL_AMOUNT+",\n" );
		sql.append("       BALANCE_AMOUNT   = BALANCE_AMOUNT + "+BILL_AMOUNT+"\n" );
		sql.append(" WHERE ORG_ID = "+ORG_ID+"\n" );
		sql.append("   AND ACCOUNT_TYPE = "+ACCOUNT_TYPE+"");
    	return factory.update(sql.toString(), null);
    }
	/**
	 * 
	 * @date()2014年8月1日上午9:38:57
	 * @author Administrator
	 * @to_do:获取渠道账户ID
	 * @param ORG_ID
	 * @param ACCOUNT_TYPE
	 * @return
	 * @throws Exception
	 */
	public QuerySet getAccountId(String ORG_ID,String ACCOUNT_TYPE) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT ACCOUNT_ID\n" );
    	sql.append("FROM PT_BU_ACCOUNT\n" );
    	sql.append("WHERE ORG_ID = "+ORG_ID+"\n" );
    	sql.append("   AND ACCOUNT_TYPE = "+ACCOUNT_TYPE+"");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	/**
	 * 
	 * @date()2014年8月1日上午9:39:47
	 * @author Administrator
	 * @to_do:新增账户异动
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean insertLog(PtBuAccountLogVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }
	/**
	 * 
	 * @date()2014年8月20日下午5:47:29
	 * @author Administrator
	 * @to_do:更改订单资金占用表
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean updateOfunds(PtBuSaleOrderOccupyFundsVO vo)
            throws Exception
    {
    	return factory.update(vo);
    }
	public boolean updateCloseCredit(String ORG_ID,String money ) throws Exception
    {
		StringBuffer sql= new StringBuffer();
		sql.append("UPDATE PT_BU_ACCOUNT\n" );
		sql.append("   SET PAY_CLOSE_AMOUNT = NVL(PAY_CLOSE_AMOUNT,0) + "+money+"\n" );
		sql.append(" WHERE ORG_ID = "+ORG_ID+"\n" );
		sql.append("   AND ACCOUNT_TYPE = "+DicConstant.ZJZHLX_04+"");
    	return factory.update(sql.toString(), null);
    }
	public boolean updateCredit(String ORG_ID,String money ) throws Exception
    {
		StringBuffer sql= new StringBuffer();
		sql.append("UPDATE PT_BU_ACCOUNT\n" );
		sql.append("   SET OCCUPY_AMOUNT = OCCUPY_AMOUNT - "+money+",\n" );
		sql.append("   AVAILABLE_AMOUNT = AVAILABLE_AMOUNT +"+money+"\n" );
		sql.append(" WHERE ORG_ID = "+ORG_ID+"\n" );
		sql.append("   AND ACCOUNT_TYPE = "+DicConstant.ZJZHLX_04+"");
    	return factory.update(sql.toString(), null);
    }
	public boolean purchaseOrderDelete(String REMIT_ID ) throws Exception
    {
    	StringBuffer sql= new StringBuffer();
    	sql.append("DELETE FROM PT_BU_MONEY_REMIT WHERE REMIT_ID ="+REMIT_ID+" ");
    	return factory.update(sql.toString(), null);
    }

}
