package com.org.dms.dao.service.financeMng;


import com.org.dms.common.DicConstant;
import com.org.dms.vo.service.SeBuClaimSettleVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 结算发票签收DAO
 * @author zts
 *
 */

public class SettleInvoiceReceiptDao extends BaseDAO {
	 //定义instance
	public  static final SettleInvoiceReceiptDao getInstance(ActionContext atx)
	{
		SettleInvoiceReceiptDao dao = new SettleInvoiceReceiptDao();	
		atx.setDBFactory(dao.factory);
		return dao;
	}
	
	/**
	 * 结算发票签收查询
	 * @param page
	 * @param condition
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet settleInvoiceReceiptSearch(PageManager page,String condition,User user)throws Exception{
		String where = condition;
		       where += " AND T.SETTLE_ID = C.SETTLE_ID(+) \n"+
						" AND TO_CHAR(T.SETTLE_DATE, 'YYYY-MM') =\n" +
						"      (SELECT TO_CHAR(SYSDATE, 'YYYY-MM') FROM DUAL)\n" + 
						"  AND T.SETTLE_STATUS ="+DicConstant.JSZT_04+"\n" + 
						//" AND T.OEM_COMPANY_ID="+user.getOemCompanyId()+"\n"+
						"ORDER BY T.ORG_ID";
		page.setFilter(where);
		BaseResultSet bs=null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.SETTLE_ID,\n" );
		sql.append("       T.SETTLE_NO,\n" );
		sql.append("       T.EXPRESS_NO,\n" );
		sql.append("       T.ORG_ID,\n" );
		sql.append("       T.ORG_CODE,\n" );
		sql.append("       T.ORG_NAME,\n" );
		sql.append("       T.SETTLE_DATE,\n" );
		sql.append("       T.SETTLE_TYPE,\n" );
		sql.append("       NVL(T.COSTS, 0) COSTS,\n" );
		sql.append("       NVL(T.RE_COSTS, 0) RE_COSTS,\n" );
		sql.append("       NVL(T.POLICY_SUP, 0) POLICY_SUP,\n" );
		sql.append("       NVL(T.CASH_GIFT, 0) CASH_GIFT,\n" );
		sql.append("       NVL(T.CAR_AWARD, 0) CAR_AWARD,\n" );
		sql.append("       NVL(T.AP_COSTS, 0) AP_COSTS,\n" );
		sql.append("       NVL(T.OTHERS, 0) OTHERS,\n" );
		sql.append("       NVL(T.SUMMARY, 0) SUMMARY,\n" );
		sql.append("       T.REMARKS,\n" );
		sql.append("       T.SETTLE_STATUS,\n" );
		sql.append("       C.CLAIM_COUNT,\n" );
		sql.append("        T.INVOICE_NO\n" );
		sql.append("  FROM SE_BU_CLAIM_SETTLE T,\n" );
		sql.append("       (SELECT R.SETTLE_ID, COUNT(R.CLAIM_ID) CLAIM_COUNT\n" );
		sql.append("          FROM SE_BU_CLAIM_SETTLE_REL R\n" );
		sql.append("         GROUP BY R.SETTLE_ID) C");
		bs=factory.select(sql.toString(), page);
		bs.setFieldDic("SETTLE_TYPE","JSLX");
		bs.setFieldDateFormat("SETTLE_DATE", "yyyy-MM-dd");
		return bs;
	}
	
	/**
	 * 结算签收更新
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean settleUpdate(SeBuClaimSettleVO vo)throws Exception{
		return factory.update(vo);
	}
}
