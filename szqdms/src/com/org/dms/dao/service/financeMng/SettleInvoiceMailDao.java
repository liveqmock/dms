package com.org.dms.dao.service.financeMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.service.SeBuClaimSettleVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;
/**
 * 结算单邮寄DAO
 * @author zts
 *
 */
public class SettleInvoiceMailDao extends BaseDAO {
	 //定义instance
	public  static final SettleInvoiceMailDao getInstance(ActionContext atx)
	{
		SettleInvoiceMailDao dao = new SettleInvoiceMailDao();	
		atx.setDBFactory(dao.factory);
		return dao;
	}

	/**
	 * 结算单邮寄查询
	 * @param page
	 * @param condition
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet settleInvoiceSearch(PageManager page,String condition,User user)throws Exception{
		String where = condition;
		  	   where += " AND T.SETTLE_ID = C.SETTLE_ID(+) \n"+
		  	   			" AND T.SETTLE_STATUS ="+DicConstant.JSZT_03+"\n"+
	               		" AND T.ORG_ID ="+user.getOrgId()+"\n"+
		  	   			" ORDER BY T.SETTLE_NO ";
		page.setFilter(where);
		BaseResultSet bs=null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.SETTLE_ID,\n" );
		sql.append("       T.SETTLE_NO,\n" );
		sql.append("       T.ORG_ID,\n" );
		sql.append("       T.ORG_CODE,\n" );
		sql.append("       T.ORG_NAME,\n" );
		sql.append("       T.SETTLE_DATE,\n" );
		sql.append("       T.COSTS,\n" );
		sql.append("       T.RE_COSTS,\n" );
		sql.append("       T.POLICY_SUP,\n" );
		sql.append("       T.CASH_GIFT,\n" );
		sql.append("       T.CAR_AWARD,\n" );
		sql.append("       T.AP_COSTS,\n" );
		sql.append("       T.OTHERS,\n" );
		sql.append("       T.SUMMARY,\n" );
		sql.append("       T.SETTLE_STATUS,\n" );
		sql.append("       T.SETTLE_TYPE,\n" );
		sql.append("       C.CLAIM_COUNT\n" );
		sql.append("  FROM SE_BU_CLAIM_SETTLE T,");
		sql.append("       (SELECT R.SETTLE_ID, COUNT(R.CLAIM_ID) CLAIM_COUNT\n" );
		sql.append("          FROM SE_BU_CLAIM_SETTLE_REL R\n" );
		sql.append("         GROUP BY R.SETTLE_ID) C");
		bs=factory.select(sql.toString(), page);
		bs.setFieldDic("SETTLE_TYPE", "JSLX");
		bs.setFieldDic("SETTLE_STATUS", "JSZT");
		bs.setFieldDateFormat("SETTLE_DATE","yyyy-MM-dd");
		return bs;
	}
	
	/**
	 * 结算单发票邮寄更新
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean settleUpdate(SeBuClaimSettleVO vo)throws Exception{
		return factory.update(vo);
	}
}
