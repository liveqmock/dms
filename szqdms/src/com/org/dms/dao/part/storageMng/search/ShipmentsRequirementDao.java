package com.org.dms.dao.part.storageMng.search;

import java.util.Map;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 
 * @ClassName: ShipmentsRequirementDao
 * @Description: 发货满足率统计
 * @author: fuxiao
 * @date: 2014年12月3日 下午3:26:55
 */
public class ShipmentsRequirementDao extends BaseDAO {

	public static final ShipmentsRequirementDao getInstance(ActionContext ac) {
		ShipmentsRequirementDao dao = new ShipmentsRequirementDao();
		ac.setDBFactory(dao.factory);
		return dao;
	}

	/**
	 * 
	 * @Title: queryList
	 * @Description: 表格数据
	 * @param pageManager
	 * @param hm
	 * @param user
	 * @return
	 * @throws Exception
	 * @return: BaseResultSet
	 */
	public BaseResultSet queryList(PageManager pageManager,
			Map<String, String> hm, User user) throws Exception {
		BaseResultSet rs = null;
		StringBuilder sb = new StringBuilder("");
		if (hm.get("APPLY_DATE_B") != null
				&& !hm.get("APPLY_DATE_B").equals("")) {
			sb.append(" AND SO.APPLY_DATE >= TO_DATE('"
					+ hm.get("APPLY_DATE_B") + "','YYYY-MM')");
		}
		if (hm.get("APPLY_DATE_E") != null
				&& !hm.get("APPLY_DATE_E").equals("")) {
			sb.append(" AND SO.APPLY_DATE < ADD_MONTHS(TO_DATE('"
					+ hm.get("APPLY_DATE_E") + "','YYYY-MM'), 1)");
		}
		if (hm.get("SO.ORDER_TYPE") != null
				&& !hm.get("SO.ORDER_TYPE").equals("")) {
			sb.append(" AND SO.ORDER_TYPE = " + hm.get("SO.ORDER_TYPE") + "");
		}
		if (hm.get("SO.ORDER_STATUS") != null
				&& !hm.get("SO.ORDER_STATUS").equals("")) {
			sb.append(" AND SO.ORDER_STATUS = " + hm.get("SO.ORDER_STATUS")
					+ "");
		}
		if (hm.get("SO.IF_DELAY_ORDER") != null
				&& !hm.get("SO.IF_DELAY_ORDER").equals("")) {
			sb.append(" AND SO.IF_DELAY_ORDER = " + hm.get("SO.IF_DELAY_ORDER")
					+ "");
		}
		
		if(hm.get("O.ORG_TYPE") != null && !hm.get("O.ORG_TYPE").equals("")){
			sb.append(" AND O.ORG_TYPE = " + hm.get("O.ORG_TYPE")
					+ "");
		}
		String sql = "SELECT\n"
				+ "    ORG_NAME, ORDER_TYPE, PLAN_CATEGORY_COUNT, NVL(EXE_CATEGORY_COUNT, 0) EXE_CATEGORY_COUNT, "
				+ " CONCAT(ROUND(NVL(EXE_CATEGORY_COUNT, 0) / DECODE(PLAN_CATEGORY_COUNT,0,1,PLAN_CATEGORY_COUNT), 4) * 100, '%') RATE_CATEGORY_COUNT,\n"
				+ "    PLAN_ORDER_PART_COUNT, NVL(EXE_ORDER_PART_COUNT, 0) EXE_ORDER_PART_COUNT, "
				+ " CONCAT(ROUND(NVL(EXE_ORDER_PART_COUNT,0) / DECODE(PLAN_ORDER_PART_COUNT,0,1,PLAN_ORDER_PART_COUNT), 4) * 100, '%') RATE_ORDER_PART_COUNT,\n"
				+ "    PLAN_AMOUNT, NVL(EXE_AMOUNT, 0) EXE_AMOUNT, "
				+ " CONCAT(ROUND(NVL(EXE_AMOUNT, 0) / DECODE(PLAN_AMOUNT,0,1,PLAN_AMOUNT), 4) * 100, '%') RATE_AMOUNT\n"
				+ "FROM (\n"
				+ "  SELECT\n"
				+ "         O.ONAME ORG_NAME,\n"
				+ "         SO.ORDER_TYPE,\n"
				+ "         COUNT(SOD.PART_ID) PLAN_CATEGORY_COUNT,\n"
				+ "         NVL(SUM(DECODE(SOD.DELIVERY_COUNT,0,0,1)),0) EXE_CATEGORY_COUNT,\n"
				+ "         NVL(SUM(SOD.ORDER_COUNT),0) PLAN_ORDER_PART_COUNT,\n"
				+ "         NVL(SUM(SOD.DELIVERY_COUNT),0) EXE_ORDER_PART_COUNT,\n"
				+ "         NVL(SUM(SOD.ORDER_COUNT * I.SALE_PRICE),0) PLAN_AMOUNT,\n"
				+ "         NVL(SUM(SOD.DELIVERY_COUNT * I.SALE_PRICE),0) EXE_AMOUNT\n"
				+ "    FROM PT_BU_SALE_ORDER      SO,\n"
				+ "         PT_BU_SALE_ORDER_DTL  SOD,\n"
				+ "         PT_BA_INFO            I,\n"
				+ "         TM_ORG	            O\n"
				+ "   WHERE SO.ORDER_ID = SOD.ORDER_ID\n"
				+ "     AND O.ORG_ID = SO.ORG_ID\n"
				+ "     AND I.PART_ID = SOD.PART_ID\n"
				+ "     AND SO.STATUS = 100201\n"
				+ "	  AND SO.ORDER_STATUS NOT IN (202201, 202202, 202204, 202205, 202207 )"
				+ "	  " + sb.toString() + "   GROUP BY O.ONAME,SO.ORDER_TYPE\n"
				+
				// "   ORDER BY SO.ORG_NAME\n" +
				"   ) T";
		rs = this.factory.select(sql, pageManager);
		rs.setFieldDic("ORDER_TYPE", "DDLX");
		return rs;
	}
}
