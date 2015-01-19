package com.org.dms.dao.part.basicInfoMng;

import java.sql.CallableStatement;
import java.sql.SQLException;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.mvc.context.ActionContext;

/**
 * 
 * @ClassName: DealerReceivablesDao
 * @Description: 应收账款Dao
 * @author: fuxiao
 * @date: 2014年12月24日 下午6:22:44
 */
public class DealerReceivablesDao extends BaseDAO {

	public static final DealerReceivablesDao getInstance(ActionContext atx) {
		DealerReceivablesDao dao = new DealerReceivablesDao();
		atx.setDBFactory(dao.factory);
		return dao;
	}

	/**
	 * 
	 * @Title: receivablesDay
	 * @Description: 结算日期确定
	 * @param dayId
	 * @param account
	 * @return: void
	 */
	public void receivablesDay(String dayId, String account) throws Exception {
		this.factory
				.update("UPDATE PT_BA_INVOICE_DAY T\n"
						+ "   SET T.DAY_STATUS = ?, T.UPDATE_USER = ?, T.UPDATE_TIME = SYSDATE\n"
						+ " WHERE T.DAY_ID = ?", new Object[] {
						DicConstant.JSRQZT_01, account, dayId });

	}

	/**
	 * 
	 * @Title: insertReceivableData
	 * @Description: 生成应收账款数据
	 * @param dayId
	 * @param account
	 * @throws Exception
	 * @return: void
	 */
	public void insertReceivableData(String dayId, String account)
			throws Exception {
		CallableStatement proc = null;
		proc = factory.getConnection().prepareCall(
				"{call P_ADD_DEALER_RECEIVABLES_LOG(?, ?)}");
		proc.setString(1, dayId);
		proc.setString(2, account);
		proc.execute();
	}

	/**
	 * 
	 * @Title: queryList
	 * @Description: 表格数据查询
	 * @param pageManager
	 * @param conditions
	 * @return
	 * @throws Exception
	 * @return: BaseResultSet
	 */
	public BaseResultSet queryList(PageManager pageManager, String conditions)
			throws Exception {
		String sql = "SELECT TID,\n"
				+ "       ORG_ID,\n"
				+ "       ORG_CODE,\n"
				+ "       ORG_NAME,\n"
				+ "       (SELECT O2.ONAME\n"
				+ "          FROM TM_ORG O1, TM_ORG O2\n"
				+ "         WHERE O1.PID = O2.ORG_ID\n"
				+ "           AND O1.ORG_ID = T.ORG_ID) PNAME,\n"
				+ "       TO_CHAR(RECEIVABLES_DATE, 'YYYY-MM') RECEIVABLES_DATE,\n"
				+ "       BEGIN_AMOUNT,\n" + "       CLOSE_ORDER_AMOUNT,\n"
				+ "       BACK_AMOUNT,\n" + "       YEER_CLOSE_ORDER_AMOUNT,\n"
				+ "       YEAR_BACK_AMOUNT,\n" + "       END_AMOUNT\n"
				+ "  FROM PT_BU_DEALER_RECEIVABLES_LOG T WHERE "
				+ conditions + " ORDER BY T.ORG_ID, T.RECEIVABLES_DATE ";
		return this.factory.select(sql, pageManager);
	}

	/**
	 * 
	 * @Title: queryDownInfo
	 * @Description: 导出数据查询
	 * @param conditions
	 * @return
	 * @throws SQLException
	 * @return: QuerySet
	 */
	public QuerySet queryDownInfo(String conditions) throws SQLException {
		String sql = "SELECT TID,\n"
				+ "       ORG_ID,\n"
				+ "       ORG_CODE,\n"
				+ "       ORG_NAME,\n"
				+ "       (SELECT O2.ONAME\n"
				+ "          FROM TM_ORG O1, TM_ORG O2\n"
				+ "         WHERE O1.PID = O2.ORG_ID\n"
				+ "           AND O1.ORG_ID = T.ORG_ID) PNAME,\n"
				+ "       TO_CHAR(RECEIVABLES_DATE, 'YYYY-MM') RECEIVABLES_DATE,\n"
				+ "       BEGIN_AMOUNT,\n" + "       CLOSE_ORDER_AMOUNT,\n"
				+ "       BACK_AMOUNT,\n" + "       YEER_CLOSE_ORDER_AMOUNT,\n"
				+ "       YEAR_BACK_AMOUNT,\n" + "       END_AMOUNT\n"
				+ "  FROM PT_BU_DEALER_RECEIVABLES_LOG T WHERE "
				+ conditions + " ORDER BY T.ORG_ID, T.RECEIVABLES_DATE ";
		return this.factory.select(null, sql);

	}

}
