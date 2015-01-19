package com.org.dms.dao.service.financeMng;

import com.org.dms.common.DicConstant;
import com.org.framework.common.DBFactory;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;

/**
 * 旧件装箱导入验证Dao
 *
 * @author zhengyao
 * @date 2014-10-28
 * @version 1.0
 */
public class StatementAdjustImpCheckDao {
	/**
	 * 结算单号不能为空
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public QuerySet checkList1(User user, DBFactory factory) throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.TMP_ID,\n" );
		sql.append("       T.ROW_NUM,\n" );
		sql.append("       T.USER_ACCOUNT,\n" );
		sql.append("       T.SETTLE_NO,\n" );
		sql.append("       T.ORG_CODE,\n" );
		sql.append("       T.ORG_NAME,\n" );
		sql.append("       T.SETTLE_DATE,\n" );
		sql.append("       T.SE_COSTS,\n" );
		sql.append("       T.SE_RE_COSTS,\n" );
		sql.append("       T.SE_POLICY_SUP,\n" );
		sql.append("       T.SE_CASH_GIFT,\n" );
		sql.append("       T.SE_CAR_AWARD,\n" );
		sql.append("       T.SE_AP_COSTS,\n" );
		sql.append("       T.SE_OTHERS,\n" );
		sql.append("       T.SE_REMARKS\n" );
		sql.append("  FROM SE_BU_CLAIM_SETTLE_TMP T\n" );
		sql.append(" WHERE T.USER_ACCOUNT = '"+user.getAccount()+"'");
		return factory.select(null, sql.toString());
	}
	/**
	 * 单号为当前月份单号
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public QuerySet checkList2(User user, DBFactory factory)throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT TM.ROW_NUM, TM.SETTLE_NO\n" );
		sql.append("  FROM SE_BU_CLAIM_SETTLE_TMP TM\n" );
		sql.append(" WHERE TM.USER_ACCOUNT = '"+user.getAccount()+"' AND NOT EXISTS\n" );
		sql.append(" (SELECT 1\n" );
		sql.append("          FROM SE_BU_CLAIM_SETTLE T\n" );
		sql.append("         WHERE T.SETTLE_NO = TM.SETTLE_NO\n" );
		sql.append("           AND TO_CHAR(T.SETTLE_DATE, 'YYYY-MM') =\n" );
		sql.append("               (SELECT TO_CHAR(SYSDATE, 'YYYY-MM') FROM DUAL) AND T.SETTLE_STATUS='"+DicConstant.JSZT_01+"')");
		return factory.select(null, sql.toString());
	}
}
