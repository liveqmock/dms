package com.org.dms.dao.part.storageMng.search;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 
 * ClassName: MoneyRemitQueryDao 
 * Function: 回款信息查询
 * date: 2014年10月24日 下午7:11:35
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class MoneyRemitQueryDao extends BaseDAO {
	
	public static final MoneyRemitQueryDao getInstance(ActionContext ac){
		MoneyRemitQueryDao dao = new MoneyRemitQueryDao();
		ac.setDBFactory(dao.factory);
		return dao;
	}
	
	/**
     * 订单查询(导出)
     *
     * @pConditions 查询条件
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet download(String conditions,User user) throws Exception {
    	
		String sql = 
					"SELECT REMIT_ID, AGENCY_CODE, AGENCY_NAME, ORG_CODE, ORG_NAME, REMIT_STATUS, REMIT_STATUS_NAME, AMOUNT_TYPE, DRAFT_NO, BILL_AMOUNT, TO_ACCOUNT_DATE, FILIING_DATE, REMARK, UPDATE_USER, UPDATE_TIME FROM (\n" +
					"SELECT\n" + 
					"       R.REMIT_ID,\n" + 
					"       O2.CODE AGENCY_CODE,\n" + 
					"       O2.ONAME AGENCY_NAME,\n" + 
					"       O1.CODE ORG_CODE,\n" + 
					"       O1.ONAME ORG_NAME,\n" + 
					"       R.REMIT_STATUS,\n" + 
					"       DECODE(REMIT_STATUS,202502,'未入账',202503,'已入账','') REMIT_STATUS_NAME,\n" + 
					"       (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = R.AMOUNT_TYPE) AMOUNT_TYPE,\n" + 
					"       R.DRAFT_NO,\n" + 
					"       R.BILL_AMOUNT,\n" + 
					"       DECODE(REMIT_STATUS,202503,R.UPDATE_TIME,NULL) TO_ACCOUNT_DATE,\n" + 
					"       R.FILIING_DATE,\n" + 
					"       R.REMARK,\n" + 
					"       CASE\n" + 
					"         WHEN REMIT_STATUS = 202503 THEN\n" + 
					"               (SELECT U.PERSON_NAME FROM TM_USER U WHERE U.ACCOUNT = R.UPDATE_USER)\n" + 
					"         ELSE NULL\n" + 
					"       END AS UPDATE_USER,\n" + 
					"       DECODE(REMIT_STATUS,202503,R.UPDATE_TIME,NULL) UPDATE_TIME\n" + 
					"  FROM PT_BU_MONEY_REMIT R,TM_ORG O1,TM_ORG O2\n" + 
					" WHERE R.ORG_ID = O1.ORG_ID AND O1.PID = O2.ORG_ID AND R.STATUS = 100201 AND R.REMIT_STATUS <> 202501\n" + 
					"		AND ((F_IS_AM("+user.getOrgId()+") = 1 AND O1.IS_AM = 100101) OR (F_IS_AM("+user.getOrgId()+") <> 1 AND NVL(O1.IS_AM,100102) = 100102))"+
					" ORDER BY R.REMIT_ID\n" + 
					")";

        return factory.select(null, sql.toString()+" WHERE "+conditions);
    }

	/**
	 * 
	 * queryList: 查询表单查询
	 * @author fuxiao
	 * Date:2014年10月23日
	 *
	 */
	public BaseResultSet queryList(PageManager pageManager, String conditions, User user) throws Exception{
		BaseResultSet rs = null;
		pageManager.setFilter(conditions);
		String sql = 
					"SELECT REMIT_ID, AGENCY_CODE, AGENCY_NAME, ORG_CODE, ORG_NAME, REMIT_STATUS, REMIT_STATUS_NAME, AMOUNT_TYPE, DRAFT_NO, BILL_AMOUNT, TO_ACCOUNT_DATE, FILIING_DATE, REMARK, UPDATE_USER, UPDATE_TIME FROM (\n" +
					"SELECT\n" + 
					"       R.REMIT_ID,\n" + 
					"       O2.CODE AGENCY_CODE,\n" + 
					"       O2.ONAME AGENCY_NAME,\n" + 
					"       O1.CODE ORG_CODE,\n" + 
					"       O1.ONAME ORG_NAME,\n" + 
					"       R.REMIT_STATUS,\n" + 
					"       DECODE(REMIT_STATUS,202502,'未入账',202503,'已入账','') REMIT_STATUS_NAME,\n" + 
					"       R.AMOUNT_TYPE,\n" + 
					"       R.DRAFT_NO,\n" + 
					"       R.BILL_AMOUNT,\n" + 
					"       DECODE(REMIT_STATUS,202503,R.UPDATE_TIME,NULL) TO_ACCOUNT_DATE,\n" + 
					"       R.FILIING_DATE,\n" + 
					"       R.REMARK,\n" + 
					"       CASE\n" + 
					"         WHEN REMIT_STATUS = 202503 THEN\n" + 
					"               (SELECT U.PERSON_NAME FROM TM_USER U WHERE U.ACCOUNT = R.UPDATE_USER)\n" + 
					"         ELSE NULL\n" + 
					"       END AS UPDATE_USER,\n" + 
					"       DECODE(REMIT_STATUS,202503,R.UPDATE_TIME,NULL) UPDATE_TIME\n" + 
					"  FROM PT_BU_MONEY_REMIT R,TM_ORG O1,TM_ORG O2\n" + 
					" WHERE R.ORG_ID = O1.ORG_ID AND O1.PID = O2.ORG_ID AND R.STATUS = 100201 AND R.REMIT_STATUS <> 202501\n" + 
					"		AND ((F_IS_AM("+user.getOrgId()+") = 1 AND O1.IS_AM = 100101) OR (F_IS_AM("+user.getOrgId()+") <> 1 AND NVL(O1.IS_AM,100102) = 100102))"+
					" ORDER BY R.REMIT_ID\n" + 
					")";
		rs = this.factory.select(sql, pageManager);
		rs.setFieldDic("AMOUNT_TYPE", "ZJZHLX");
		rs.setFieldDateFormat("TO_ACCOUNT_DATE", "yyyy-MM-dd HH:mm:ss");
		rs.setFieldDateFormat("FILIING_DATE", "yyyy-MM-dd HH:mm:ss");
		rs.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd HH:mm:ss");
		return rs;
	}

}
