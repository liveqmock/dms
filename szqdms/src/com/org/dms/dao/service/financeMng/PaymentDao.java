package com.org.dms.dao.service.financeMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.service.SeBuClaimSettleVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;
/**
 * 付款单据下载DAO
 * @author zts
 *
 */
public class PaymentDao extends BaseDAO {
	 //定义instance
	public  static final PaymentDao getInstance(ActionContext atx)
	{
		PaymentDao dao = new PaymentDao();	
		atx.setDBFactory(dao.factory);
		return dao;
	}
	
	/**
	 * 付款单据下载查询
	 * @param page
	 * @param condition
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet settleSearch(PageManager page,String condition,User user)throws Exception{
		String where = condition;
	       where += " AND T.SETTLE_ID = C.SETTLE_ID(+) \n"+
					" AND TO_CHAR(T.SETTLE_DATE, 'YYYY-MM') =\n" +
					"      (SELECT TO_CHAR(SYSDATE, 'YYYY-MM') FROM DUAL)\n" + 
					"  AND T.SETTLE_STATUS ="+DicConstant.JSZT_06+"\n" + 
					//" AND T.OEM_COMPANY_ID="+user.getOemCompanyId()+"\n"+
					"ORDER BY T.RECEIVE_TIME";
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
		sql.append("       T.INVOICE_NO\n" );
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
	 * 付款单据下载查询
	 * @param page
	 * @param condition
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet settleImpSearch(PageManager page,User user)throws Exception{
		page.setFilter("");
		BaseResultSet bs=null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.TMP_ID,\n" );
		sql.append("       T.SETTLE_NO,\n" );
		sql.append("       T.PAY_DATE,\n" );
		sql.append("       T.PAY_AMOUNT,\n" );
		sql.append("       T.IF_PAY,\n" );
		sql.append("       T.REMARKS,\n" );
		sql.append("       T.ROW_NO,\n" );
		sql.append("       T.USER_ACCOUNT,\n" );
		sql.append("       T.ORG_CODE\n" );
		sql.append("  FROM SE_BU_CLAIM_SETTLE_MODIFY_TMP T");
		sql.append("    WHERE T.USER_ACCOUNT ='"+user.getAccount()+"'");
		sql.append("    ORDER BY T.ROW_NO");
		
		bs=factory.select(sql.toString(), page);
		bs.setFieldDateFormat("PAY_DATE", "yyyy-MM-dd");
		return bs;
	}
	
	/**
	 * 付款单据下载
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public QuerySet download(String conditions,User user)throws Exception{
		StringBuffer sql= new StringBuffer();
	    sql.append("SELECT ROWNUM,\n" );
	    sql.append("       T.SETTLE_ID,\n" );
	    sql.append("       T.ORG_ID,\n" );
	    sql.append("       T.ORG_CODE,\n" );
	    sql.append("       T.ORG_NAME,\n" );
	    sql.append("       T.SETTLE_NO,\n" );
	    sql.append("       TO_CHAR(T.SETTLE_DATE,'YYYY-MM-DD') SETTLE_DATE,\n" );
	    sql.append("       DECODE(T.SETTLE_TYPE,"+DicConstant.JSLX_01+",'服务费','材料费') SETTLE_TYPE,\n" );
	    sql.append("       NVL(T.COSTS, 0) COSTS,\n" );
	    sql.append("       NVL(T.RE_COSTS, 0) RE_COSTS,\n" );
	    sql.append("       NVL(T.POLICY_SUP, 0) POLICY_SUP,\n" );
	    sql.append("       NVL(T.CASH_GIFT, 0) CASH_GIFT,\n" );
	    sql.append("       NVL(T.CAR_AWARD, 0) CAR_AWARD,\n" );
	    sql.append("       NVL(T.AP_COSTS, 0) AP_COSTS,\n" );
	    sql.append("       NVL(T.OTHERS, 0) OTHERS,\n" );
	    sql.append("       NVL(T.SUMMARY, 0) SUMMARY,\n" );
	    sql.append("       (SELECT D.ERP_ID FROM TM_ORG D WHERE D.ORG_ID = T.ORG_ID) ERP_ID,\n");
	    sql.append("       T.REMARKS\n" );
	    sql.append("  FROM SE_BU_CLAIM_SETTLE T\n");
	    sql.append(" where "+conditions+" AND  TO_CHAR(T.SETTLE_DATE, 'YYYY-MM') =  (SELECT TO_CHAR(SYSDATE, 'YYYY-MM') FROM DUAL)\n");
	    //sql.append(" AND T.OEM_COMPANY_ID="+user.getOemCompanyId()+"\n");
	    sql.append(" AND T.SETTLE_STATUS='"+DicConstant.JSZT_06+"' \n");
	    sql.append(" AND T.STATUS ="+DicConstant.YXBS_01+" \n");
	    sql.append(" ORDER BY T.RECEIVE_TIME");
	    return factory.select(null, sql.toString());
	}
	/**
	 * 付款单据下载
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public QuerySet UpdateDownload(String conditions,User user)throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.SETTLE_NO,\n" );
		sql.append("       ROWNUM,\n" );
		sql.append("       T.ORG_CODE,\n" );
		sql.append("       ''FKRQ,\n" );
		sql.append("       ''FKJE,\n" );
		sql.append("       ''SFYF,\n" );
		sql.append("       ''BZ\n" );
		sql.append("  FROM SE_BU_CLAIM_SETTLE T,\n" );
		sql.append("       (SELECT R.SETTLE_ID, COUNT(R.CLAIM_ID) CLAIM_COUNT\n" );
		sql.append("          FROM SE_BU_CLAIM_SETTLE_REL R\n" );
		sql.append("         GROUP BY R.SETTLE_ID) C WHERE  "+conditions+"  AND T.SETTLE_ID = C.SETTLE_ID(+)\n" );
		sql.append(" AND TO_CHAR(T.SETTLE_DATE, 'YYYY-MM') =\n" );
		sql.append("      (SELECT TO_CHAR(SYSDATE, 'YYYY-MM') FROM DUAL)\n" );
	 	sql.append("  AND T.SETTLE_STATUS =304506\n" );
		sql.append("ORDER BY T.RECEIVE_TIME");
		return factory.select(null, sql.toString());
	}
	
	/**
	 * 结算单修改
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean settleUpdate(SeBuClaimSettleVO vo)throws Exception{
		return factory.update(vo);
	}
	/**
	 * 结算单付款结果更新导入校验：校验表格中的渠道商CODE在数据库中是否存在
	 * queryOrgCode
	 */
	public QuerySet queryOrgCode(User user) throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT TMP.ROW_NO,TMP.ORG_CODE\n" );
		sql.append("  FROM SE_BU_CLAIM_SETTLE_MODIFY_TMP TMP\n" );
		sql.append(" WHERE TMP.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
		sql.append("   AND NOT EXISTS\n" );
		sql.append(" (SELECT 1 FROM TM_ORG T WHERE T.ERP_ID = TMP.ORG_CODE)");
		 return factory.select(null, sql.toString());
	 }
	/**
	 * 结算单付款结果更新导入校验：校验表格中的渠道商CODE与结算单号是否统一。
	 * querySettleNo
	 */
	public QuerySet querySettleNo(User user) throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT TMP.ROW_NO, TMP.SETTLE_NO,TMP.ORG_CODE\n" );
		sql.append("  FROM SE_BU_CLAIM_SETTLE_MODIFY_TMP TMP\n" );
		sql.append(" WHERE TMP.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
		sql.append("   AND NOT EXISTS (SELECT 1\n" );
		sql.append("          FROM SE_BU_CLAIM_SETTLE T, TM_ORG TG\n" );
		sql.append("         WHERE TG.ORG_ID = T.ORG_ID\n" );
		sql.append("           AND TG.ERP_ID = TMP.ORG_CODE\n" );
		sql.append("           AND T.SETTLE_NO = TMP.SETTLE_NO)");
		return factory.select(null, sql.toString());
	}
	public QuerySet checkList(User user) throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.TMP_ID,\n" );
		sql.append("       T.SETTLE_NO,\n" );
		sql.append("       TO_DATE(T.PAY_DATE,'YYYY-MM-DD') PAY_DATE, \n" );
		sql.append("       T.PAY_AMOUNT,\n" );
		sql.append("       T.IF_PAY,\n" );
		sql.append("       T.REMARKS,\n" );
		sql.append("       T.ROW_NO,\n" );
		sql.append("       T.USER_ACCOUNT,\n" );
		sql.append("       T.ORG_CODE\n" );
		sql.append("  FROM SE_BU_CLAIM_SETTLE_MODIFY_TMP T\n" );
		sql.append(" WHERE T.USER_ACCOUNT = '"+user.getAccount()+"'");
		return factory.select(null, sql.toString());
	}
	public QuerySet queryId(String settleNo) throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.SETTLE_ID FROM SE_BU_CLAIM_SETTLE T WHERE T.SETTLE_NO = '"+settleNo+"'\n" );
		return factory.select(null, sql.toString());
	}
 }
