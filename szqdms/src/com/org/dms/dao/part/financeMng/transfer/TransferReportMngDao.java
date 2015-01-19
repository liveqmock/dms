package com.org.dms.dao.part.financeMng.transfer;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuAccountTransferVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class TransferReportMngDao extends BaseDAO{
	public  static final TransferReportMngDao getInstance(ActionContext atx)
    {
		TransferReportMngDao dao = new TransferReportMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	
	/**
	 * 
	 * @date()2014年8月1日下午4:43:00
	 * @author Administrator
	 * @to_do:转账查询
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
    					"AND T.OEM_COMPANY_ID = "+user.getOemCompanyId()+"\n" + 
    					"AND T.COMPANY_ID = "+user.getCompanyId()+"\n" + 
    					"AND T.ORG_ID = "+user.getOrgId()+"\n" + 
    					"AND T.TRANSFER_STATUS IN( "+DicConstant.ZZZT_01+","+DicConstant.ZZZT_04+")\n";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.TRANSFER_ID,\n" );
    	sql.append("       T.IN_TYPE,\n" );
    	sql.append("       T.OUT_TYPE,\n" );
    	sql.append("       T.APPLY_DATE,\n" );
    	sql.append("       T.AMOUNT,\n" );
    	sql.append("       T.AUDIT_REMARK,\n" );
    	sql.append("       T.TRANSFER_STATUS,\n" );
    	sql.append("       T.REMARKS\n" );
    	sql.append("  FROM PT_BU_ACCOUNT_TRANSFER T\n" );
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("IN_TYPE", "ZJZHLX");
    	bs.setFieldDic("OUT_TYPE", "ZJZHLX");
    	bs.setFieldDic("TRANSFER_STATUS", "ZZZT");
		bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
    	return bs;
    }

	/**
	 * 
	 * @date()2014年8月1日下午6:47:21
	 * @author Administrator
	 * @to_do:获取账户余额 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet getAccount(User user) throws Exception
    {
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT BALANCE_AMOUNT FROM PT_BU_ACCOUNT WHERE ORG_ID = "+user.getOrgId()+" AND ACCOUNT_TYPE = "+DicConstant.ZJZHLX_05+"");
    	bs = factory.select(sql.toString(), new PageManager());
    	return bs;
    }
	/**
	 * 
	 * @date()2014年8月1日下午6:49:10
	 * @author Administrator
	 * @to_do:新增转账信息
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean insertTransfer(PtBuAccountTransferVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }
	/**
	 * 
	 * @date()2014年8月1日下午6:49:21
	 * @author Administrator
	 * @to_do:修改转账信息
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean updateTransfer(PtBuAccountTransferVO vo)
            throws Exception
    {
    	return factory.update(vo);
    }
 public boolean transferDelete(String TRANSFER_ID ) throws Exception
    {
    	StringBuffer sql= new StringBuffer();
    	sql.append("DELETE FROM PT_BU_ACCOUNT_TRANSFER WHERE TRANSFER_ID = "+TRANSFER_ID+"");
    	return factory.update(sql.toString(), null);
    }

}
