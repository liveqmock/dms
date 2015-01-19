package com.org.dms.dao.service.financeMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.service.SeBuClaimSettleVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;
/**
 * 结算发票打印DAO
 * @author zts
 *
 */
public class SettleInvoicePrintDao extends BaseDAO {
	 //定义instance
	public  static final SettleInvoicePrintDao getInstance(ActionContext atx)
	{
		SettleInvoicePrintDao dao = new SettleInvoicePrintDao();	
		atx.setDBFactory(dao.factory);
		return dao;
	}

	/**
	 * 打印查询
	 * @param page
	 * @param condition
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet settleInvoicePrintSearch(PageManager page,String condition,User user)throws Exception{
		BaseResultSet bs=null;
		String wheres =condition;
		if(user.getOrgId().equals("10002078")||user.getOrgId().equals("10002660")||user.getOrgId().equals("10002280")||user.getOrgId().equals("10002291")||user.getOrgId().equals("10003311")||user.getOrgId().equals("10001892")||user.getOrgId().equals("10001869")||user.getOrgId().equals("10002420")||user.getOrgId().equals("10002465")||user.getOrgId().equals("10002445")  ){
			   wheres += " AND T.SETTLE_ID = C.SETTLE_ID(+)\n "
			   		   + " AND T.ORG_ID = G.ORG_ID\n"
			   		   + " AND G.PID = G1.ORG_ID\n"
			   			//+ " AND TO_CHAR(T.SETTLE_DATE, 'YYYY-MM') =(SELECT TO_CHAR(SYSDATE, 'YYYY-MM') FROM DUAL) \n"+
					   	/* " AND T.SUMMARY > 0 \n"+
						 " AND (T.SUMMARY +\n" +
						 "       nvl((SELECT T1.SUMMARY\n" + 
					     "           FROM SE_BU_CLAIM_SETTLE T1\n" + 
						 "          WHERE TO_CHAR(T1.SETTLE_DATE, 'YYYY-MM') =\n" + 
						 "                (SELECT TO_CHAR(ADD_MONTHS(SYSDATE, -1), 'YYYY-MM') FROM DUAL)),0)) > 0 \n"+*/

						+ " AND T.SUMMARY > 0\n" +
						 " AND T.SUMMARY +  NVL(T.LAST_TOTAL_COST, 0)> 0"+
			   			 " AND T.SETTLE_STATUS IN("+DicConstant.JSZT_02+","+DicConstant.JSZT_03+")\n"+
			             " AND T.ORG_ID ="+user.getOrgId()+"\n"+
			   			 " ORDER BY T.SETTLE_NO";	
		}else{
		
			   wheres += " AND T.SETTLE_ID = C.SETTLE_ID(+)\n "
			   		   + " AND T.ORG_ID = G.ORG_ID\n"
			   		   + " AND G.PID = G1.ORG_ID\n"
			   			+ " AND TO_CHAR(T.SETTLE_DATE, 'YYYY-MM') =(SELECT TO_CHAR(SYSDATE, 'YYYY-MM') FROM DUAL) \n"+
					   	/* " AND T.SUMMARY > 0 \n"+
						 " AND (T.SUMMARY +\n" +
						 "       nvl((SELECT T1.SUMMARY\n" + 
					     "           FROM SE_BU_CLAIM_SETTLE T1\n" + 
						 "          WHERE TO_CHAR(T1.SETTLE_DATE, 'YYYY-MM') =\n" + 
						 "                (SELECT TO_CHAR(ADD_MONTHS(SYSDATE, -1), 'YYYY-MM') FROM DUAL)),0)) > 0 \n"+*/

						 " AND T.SUMMARY > 0\n" +
						 " AND T.SUMMARY +  NVL(T.LAST_TOTAL_COST, 0)> 0"+
			   			 " AND T.SETTLE_STATUS IN("+DicConstant.JSZT_02+","+DicConstant.JSZT_03+")\n"+
			             " AND T.ORG_ID ="+user.getOrgId()+"\n"+
			   			 " ORDER BY T.SETTLE_NO";	}
		page.setFilter(wheres);
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.SETTLE_ID,\n" );
		sql.append("       T.SETTLE_NO,\n" );
		sql.append("       T.SETTLE_DATE,\n" );
		sql.append("       TO_CHAR(T.INVOICE_DATE,'YYYY') JSN,\n" );
		sql.append("       TO_CHAR(T.INVOICE_DATE,'MM') JSY,\n" );
		sql.append("       TO_CHAR(T.INVOICE_DATE,'DD') JSR,\n" );
		sql.append("       T.SETTLE_TYPE,\n" );
		sql.append("       T.COSTS COSTS,\n" );
		sql.append("       T.RE_COSTS,\n" );
		sql.append("       T.POLICY_SUP,\n" );
		sql.append("       T.CASH_GIFT,\n" );
		sql.append("       T.CAR_AWARD,\n" );
		sql.append("       T.AP_COSTS,\n" );
		sql.append("       T.OTHERS,\n" );
		sql.append("	   T.REMARKS,\n" );
		sql.append("	   G1.ONAME,\n" );
		sql.append("	   G.SNAME,");
		/*sql.append("       CASE\n" );
		sql.append("         WHEN (SELECT T1.SUMMARY\n" );
		sql.append("                 FROM SE_BU_CLAIM_SETTLE T1\n" );
		sql.append("                WHERE TO_CHAR(T1.SETTLE_DATE, 'YYYY-MM') =\n" );
		sql.append("                      (SELECT TO_CHAR(ADD_MONTHS(SYSDATE, -1), 'YYYY-MM')\n" );
		sql.append("                         FROM DUAL)) < 0 THEN\n" );
		sql.append("          (T.SUMMARY +\n" );
		sql.append("          (SELECT T1.SUMMARY\n" );
		sql.append("              FROM SE_BU_CLAIM_SETTLE T1\n" );
		sql.append("             WHERE TO_CHAR(T1.SETTLE_DATE, 'YYYY-MM') =\n" );
		sql.append("                   (SELECT TO_CHAR(ADD_MONTHS(SYSDATE, -1), 'YYYY-MM')\n" );
		sql.append("                      FROM DUAL)))\n" );
		sql.append("         ELSE\n" );
		sql.append("          T.SUMMARY\n" );
		sql.append("       END SUMMARY,\n" );*/
		sql.append("       CASE \n" );
		sql.append("       	 WHEN NVL(T.LAST_TOTAL_COST, 0) < 0 THEN \n" );
		sql.append("           T.SUMMARY + NVL(T.LAST_TOTAL_COST, 0) \n" );
		sql.append("         ELSE \n" );
		sql.append("           T.SUMMARY \n" );
		sql.append("       END SUMMARY, \n");
		sql.append("       TO_CHAR(SYSDATE, 'YYYY-MM-DD') SJ,\n" );
		sql.append("       T.ORG_CODE,\n" );
		sql.append("       T.ORG_NAME,\n" );
		sql.append("       C.CLAIM_COUNT,\n" );
		sql.append("       (SELECT Y.CNAME FROM TM_COMPANY Y WHERE Y.COMPANY_ID="+user.getCompanyId()+") AS COMPANY_NAME\n" );
		sql.append("  FROM SE_BU_CLAIM_SETTLE T,\n" );
		sql.append("       (SELECT R.SETTLE_ID, COUNT(R.CLAIM_ID) CLAIM_COUNT\n" );
		sql.append("          FROM SE_BU_CLAIM_SETTLE_REL R\n" );
		sql.append("         GROUP BY R.SETTLE_ID) C, TM_ORG G ,TM_ORG G1");
		bs=factory.select(sql.toString(), page);
		bs.setFieldDic("SETTLE_TYPE","JSLX");
		bs.setFieldDateFormat("SETTLE_DATE", "yyyy-MM-dd");
		return bs;
	}
	
	/**
	 * 结算打印更新
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean settleUpdate(SeBuClaimSettleVO vo)throws Exception{
		return factory.update(vo);
	}
}
