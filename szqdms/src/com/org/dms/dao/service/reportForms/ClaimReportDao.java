package com.org.dms.dao.service.reportForms;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class ClaimReportDao extends BaseDAO {
	// 定义instance
	public static final ClaimReportDao getInstance(ActionContext atx) {
		ClaimReportDao dao = new ClaimReportDao();
		atx.setDBFactory(dao.factory);
		return dao;
	}

	/**
	 * @throws Exception
	 *             设定文件
	 * @return BaseResultSet 返回类型
	 */
	public BaseResultSet search(PageManager page, User user, String conditions)
			throws Exception {
		// 定义返回结果集
		BaseResultSet bs = null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.CLAIM_NO,\n" );
		sql.append("       F.VIN,\n" );
		sql.append("       F.MODELS_CODE,\n" );
		sql.append("       F.ENGINE_NO,\n" );
		sql.append("       F.VEHICLE_SUPP,\n" );
		sql.append("       TO_CHAR(F.BUY_DATE,'YYYY-MM-DD') BUY_DATE,\n" );
		sql.append("\n" );
		sql.append("          NVL(T.MATERIAL_COSTS, 0) MATERIAL_COSTS, --材料费\n" );
		sql.append("        NVL(T.OUT_AMOUNT, 0) OUT_AMOUNT, --外出费用,\n" );
		sql.append("       (SELECT TO_CHAR(MIN(P.FAULT_DATE),'YYYY-MM-DD') FROM SE_BU_CLAIM P WHERE T.VIN = P.VIN) F_FAULT_DATE, --首次故障日期\n" );
		sql.append("      TO_CHAR(T.FAULT_DATE,'YYYY-MM-DD') AS FAULT_DATE,\n" );
		sql.append("       (SELECT MILEAGE\n" );
		sql.append("          FROM SE_BU_CLAIM\n" );
		sql.append("         WHERE ROWID =\n" );
		sql.append("               ((SELECT MIN(ROWID) FROM SE_BU_CLAIM P WHERE T.VIN = P.VIN))) F_MILEAGE, --首次里程,\n" );
		sql.append("       T.MILEAGE T_MILEAGE,\n" );
		sql.append("       G.CODE,\n" );
		sql.append("       G.ONAME,\n" );
		sql.append("       F.USER_NAME,\n" );
		sql.append("       F.USER_ADDRESS,\n" );
		sql.append("       F.PHONE,\n" );
		sql.append("       F.MILEAGE,\n" );
		sql.append("       B.FAULT_CODE,\n" );
		sql.append("       B.FAULT_NAME,\n" );
		sql.append("       C.OLD_PART_CODE,\n" );
		sql.append("       C.OLD_PART_NAME,\n" );
		sql.append("        (SELECT X.SUPPLIER_NAME FROM PT_BA_SUPPLIER X WHERE  X.SUPPLIER_ID= C.OLD_SUPPLIER_ID)  OLD_SUPPLIER, --旧件厂家\n" );
		sql.append("       NVL(B.WORK_COSTS, 0) WORK_COSTS, --工时费\n" );
		sql.append("       NVL(C.CLAIM_COSTS, 0) CLAIM_COSTS, --材料费\n" );
		sql.append("       NVL(D.SEVEH_COSTS, 0) SEVEH_COSTS, --出车费\n" );
		sql.append("       D.MILEAGE D_MILEAGE-- 出车里程\n" );
		sql.append("\n" );
		sql.append("  FROM SE_BU_CLAIM            T,\n" );
		sql.append("       SE_BU_CLAIM_FAULT      B,\n" );
		sql.append("       SE_BU_CLAIM_FAULT_PART C,\n" );
		sql.append("       SE_BU_CLAIM_OUT        D,\n" );
		sql.append("       MAIN_VEHICLE           F,\n" );
		sql.append("       TM_ORG                 G\n" );
		sql.append("\n" );
		sql.append(" WHERE T.CLAIM_ID = B.CLAIM_ID\n" );
		sql.append("   AND B.CLAIM_ID = C.CLAIM_ID\n" );
		sql.append("   AND B.CLAIM_DTL_ID = C.CLAIM_DTL_ID\n" );
		sql.append("   AND T.CLAIM_ID = D.CLAIM_ID\n" );
		sql.append("   AND T.VEHICLE_ID = F.VEHICLE_ID\n" );
		sql.append("   AND T.ORG_ID = G.ORG_ID\n" );
		//sql.append("  -- AND C.FAULT_TYPE = '301601'\n" );
		sql.append("   AND T.STATUS = '100201'\n" );
		sql.append("   AND B.STATUS = '100201'\n" );
		sql.append("   AND C.STATUS = '100201'\n" );
		sql.append(" AND "+conditions);
		sql.append("    ORDER BY CLAIM_NO,OLD_SUPPLIER");



		// 执行方法，不需要传递conn参数
		bs = factory.select(sql.toString(), page);
		return bs;
	}

	public QuerySet download(String conditions) throws Exception {

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT  ROWNUM,T.* FROM(");
		sql.append("SELECT T.CLAIM_NO,\n" );
		sql.append("       F.VIN,\n" );
		sql.append("       F.MODELS_CODE,\n" );
		sql.append("       F.ENGINE_NO,\n" );
		sql.append("       F.VEHICLE_SUPP,\n" );
		sql.append("       TO_CHAR(F.BUY_DATE,'YYYY-MM-DD') BUY_DATE,\n" );
		sql.append("\n" );
		sql.append("          NVL(T.MATERIAL_COSTS, 0) MATERIAL_COSTS, --材料费\n" );
		sql.append("        NVL(T.OUT_AMOUNT, 0) OUT_AMOUNT, --外出费用,\n" );
		sql.append("       (SELECT TO_CHAR(MIN(P.FAULT_DATE),'YYYY-MM-DD') FROM SE_BU_CLAIM P WHERE T.VIN = P.VIN) F_FAULT_DATE, --首次故障日期\n" );
		sql.append("      TO_CHAR(T.FAULT_DATE,'YYYY-MM-DD') AS FAULT_DATE,\n" );
		sql.append("       (SELECT MILEAGE\n" );
		sql.append("          FROM SE_BU_CLAIM\n" );
		sql.append("         WHERE ROWID =\n" );
		sql.append("               ((SELECT MIN(ROWID) FROM SE_BU_CLAIM P WHERE T.VIN = P.VIN))) F_MILEAGE, --首次里程,\n" );
		sql.append("       T.MILEAGE T_MILEAGE,\n" );
		sql.append("       G.CODE,\n" );
		sql.append("       G.ONAME,\n" );
		sql.append("       F.USER_NAME,\n" );
		sql.append("       F.USER_ADDRESS,\n" );
		sql.append("       F.PHONE,\n" );
		sql.append("       F.MILEAGE,\n" );
		sql.append("       B.FAULT_CODE,\n" );
		sql.append("       B.FAULT_NAME,\n" );
		sql.append("       C.OLD_PART_CODE,\n" );
		sql.append("       C.OLD_PART_NAME,\n" );
		sql.append("        (SELECT X.SUPPLIER_NAME FROM PT_BA_SUPPLIER X WHERE  X.SUPPLIER_ID= C.OLD_SUPPLIER_ID)  OLD_SUPPLIER, --旧件厂家\n" );
		sql.append("       NVL(B.WORK_COSTS, 0) WORK_COSTS, --工时费\n" );
		sql.append("       NVL(C.CLAIM_COSTS, 0) CLAIM_COSTS, --材料费\n" );
		sql.append("       NVL(D.SEVEH_COSTS, 0) SEVEH_COSTS, --出车费\n" );
		sql.append("       D.MILEAGE D_MILEAGE-- 出车里程\n" );
		sql.append("\n" );
		sql.append("  FROM SE_BU_CLAIM            T,\n" );
		sql.append("       SE_BU_CLAIM_FAULT      B,\n" );
		sql.append("       SE_BU_CLAIM_FAULT_PART C,\n" );
		sql.append("       SE_BU_CLAIM_OUT        D,\n" );
		sql.append("       MAIN_VEHICLE           F,\n" );
		sql.append("       TM_ORG                 G\n" );
		sql.append("\n" );
		sql.append(" WHERE T.CLAIM_ID = B.CLAIM_ID\n" );
		sql.append("   AND B.CLAIM_ID = C.CLAIM_ID\n" );
		sql.append("   AND B.CLAIM_DTL_ID = C.CLAIM_DTL_ID\n" );
		sql.append("   AND T.CLAIM_ID = D.CLAIM_ID\n" );
		sql.append("   AND T.VEHICLE_ID = F.VEHICLE_ID\n" );
		sql.append("   AND T.ORG_ID = G.ORG_ID\n" );
		//sql.append("  -- AND C.FAULT_TYPE = '301601'\n" );
		sql.append("   AND T.STATUS = '100201'\n" );
		sql.append("   AND B.STATUS = '100201'\n" );
		sql.append("   AND C.STATUS = '100201'\n" );
		sql.append(" AND "+conditions);
		sql.append("    ORDER BY CLAIM_NO,OLD_SUPPLIER");

		sql.append("    ) T ORDER BY ROWNUM");
		// 执行方法，不需要传递conn参数
		return factory.select(null, sql.toString());
	}
}
