package com.org.dms.dao.part.financeMng.transfer;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuAccountLogVO;
import com.org.dms.vo.part.PtBuAccountTransferVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class TransferReportAdjustMngADao extends BaseDAO{
	public  static final TransferReportAdjustMngADao getInstance(ActionContext atx)
    {
		TransferReportAdjustMngADao dao = new TransferReportAdjustMngADao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	/**
	 * 
	 * @date()2014年8月2日上午9:55:33
	 * @author Administrator
	 * @to_do:转账审核查询
	 * @param page
	 * @param user
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet transferSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres +="AND T.STATUS = "+DicConstant.YXBS_01+"\n" +
    					"AND T.ORG_ID = T1.ORG_ID \n"+
    					"AND T.OEM_COMPANY_ID = "+user.getOemCompanyId()+"\n" + 
    					"AND T.TRANSFER_STATUS= "+DicConstant.ZZZT_02+"\n";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T1.ORG_ID,\n" );
    	sql.append("       T1.CODE,\n" );
    	sql.append("       T1.ONAME,\n" );
    	sql.append("       T.TRANSFER_ID,\n" );
    	sql.append("       T.OUT_TYPE,\n" );
    	sql.append("       T.IN_TYPE,\n" );
    	sql.append("       T.APPLY_DATE,\n" );
    	sql.append("       T.AMOUNT,\n" );
    	sql.append("       T.REMARKS\n" );
    	sql.append("  FROM PT_BU_ACCOUNT_TRANSFER T, TM_ORG T1\n" );
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("OUT_TYPE", "ZJZHLX");
    	bs.setFieldDic("IN_TYPE", "ZJZHLX");
		bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
    	return bs;
    }
	/**
	 * 
	 * @date()2014年8月2日上午10:01:02
	 * @author Administrator
	 * @to_do:获取账户余额 
	 * @param TRANSFER_ID
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet getAccount(String TRANSFER_ID) throws Exception
    {
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.BALANCE_AMOUNT\n" );
    	sql.append("  FROM PT_BU_ACCOUNT T, PT_BU_ACCOUNT_TRANSFER T1\n" );
    	sql.append(" WHERE T.ORG_ID = T1.ORG_ID\n" );
    	sql.append("   AND T.ACCOUNT_TYPE = "+DicConstant.ZJZHLX_05+"\n" );
    	sql.append("   AND T1.TRANSFER_ID = "+TRANSFER_ID+"");
    	bs = factory.select(sql.toString(), new PageManager());
    	return bs;
    }
	/**
	 * 
	 * @date()2014年8月2日上午10:26:28
	 * @author Administrator
	 * @to_do:转账申请驳回
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean updateTransfer(PtBuAccountTransferVO vo)
            throws Exception
    {
    	return factory.update(vo);
    }
	/**
	 * 
	 * @date()2014年8月2日上午11:18:50
	 * @author Administrator
	 * @to_do:获取返利账户ID
	 * @param ORG_ID
	 * @return
	 * @throws Exception
	 */
	public QuerySet getRebate(String ORG_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.ACCOUNT_ID\n" );
    	sql.append("FROM PT_BU_ACCOUNT T\n" );
    	sql.append("WHERE T.ACCOUNT_TYPE = "+DicConstant.ZJZHLX_05+" \n");
    	sql.append("AND T.ORG_ID = "+ORG_ID+"");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	/**
	 * 
	 * @date()2014年8月2日上午11:19:40
	 * @author Administrator
	 * @to_do:获取材料费账户ID
	 * @param ORG_ID
	 * @return
	 * @throws Exception
	 */
	public QuerySet getMaterial(String ORG_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.ACCOUNT_ID\n" );
    	sql.append("FROM PT_BU_ACCOUNT T\n" );
    	sql.append("WHERE T.ACCOUNT_TYPE = "+DicConstant.ZJZHLX_03+"\n");
    	sql.append("AND T.ORG_ID = "+ORG_ID+"");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	/**
	 * 
	 * @date()2014年8月2日上午11:27:14
	 * @author Administrator
	 * @to_do:增加材料费账户金额
	 * @param ID
	 * @param AMOUNT
	 * @return
	 * @throws Exception
	 */
	public boolean changeAccount01(String ID,String AMOUNT ) throws Exception
    {
		StringBuffer sql= new StringBuffer();
		sql.append("UPDATE PT_BU_ACCOUNT SET AVAILABLE_AMOUNT = AVAILABLE_AMOUNT+"+AMOUNT+", BALANCE_AMOUNT=BALANCE_AMOUNT+"+AMOUNT+"\n" );
		sql.append("WHERE ACCOUNT_ID ="+ID+"");
    	return factory.update(sql.toString(), null);
    }
	/**
	 * 
	 * @date()2014年8月2日上午11:27:14
	 * @author Administrator
	 * @to_do:减少返利账户金额
	 * @param ID
	 * @param AMOUNT
	 * @return
	 * @throws Exception
	 */
	public boolean changeAccount02(String ID,String AMOUNT ) throws Exception
    {
		StringBuffer sql= new StringBuffer();
		sql.append("UPDATE PT_BU_ACCOUNT SET AVAILABLE_AMOUNT = AVAILABLE_AMOUNT-"+AMOUNT+", BALANCE_AMOUNT=BALANCE_AMOUNT-"+AMOUNT+"\n" );
		sql.append("WHERE ACCOUNT_ID ="+ID+"");
    	return factory.update(sql.toString(), null);
    }
	/**
	 * 
	 * @date()2014年8月2日上午11:32:38
	 * @author Administrator
	 * @to_do:插入资金异动
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean insertLog(PtBuAccountLogVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }

}
