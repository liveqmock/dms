package com.org.dms.dao.part.salesMng.orderMng;

import java.sql.CallableStatement;
import java.sql.SQLException;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 
 * @ClassName: PartOrderCloseForDealerDao
 * @Description: 渠道商订单关闭
 * @author: fuxiao
 * @date: 2014年12月8日 下午8:16:46
 */
public class PartOrderCloseForDealerDao extends BaseDAO {

	public static final PartOrderCloseForDealerDao getInstance(ActionContext atx) {
		PartOrderCloseForDealerDao dao = new PartOrderCloseForDealerDao();
		atx.setDBFactory(dao.factory);
		return dao;
	}

	/**
	 * 
	 * @Title: partOrderSearch
	 * @Description: 带关闭订单查询
	 * @param page
	 * @param user
	 * @param conditions
	 * @return
	 * @throws Exception
	 * @return: BaseResultSet
	 */
	public BaseResultSet partOrderSearch(PageManager page, User user,
			String conditions) throws Exception {
		String wheres = conditions;
		wheres += "AND T.ORDER_STATUS IN (" + DicConstant.DDZT_02 + ","
				+ DicConstant.DDZT_03 + ")\n" + "AND T.ORDER_TYPE NOT IN('"
				+ DicConstant.DDLX_09 + "','" + DicConstant.DDLX_10 + "')"
				+ "AND T.STATUS = " + DicConstant.YXBS_01 + ""
				+ "AND T.IF_CHANEL_ORDER = " + DicConstant.SF_01;

		if (user.getOrgDept().getOrgType().equals(DicConstant.ZZLB_09)) {
			wheres += "AND T.WAREHOUSE_ID = " + user.getOrgId() + "\n";
		}

		page.setFilter(wheres);
		BaseResultSet bs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T.ORDER_ID, T.ORDER_NO,subStr(T.ORDER_NO,-1) AS DELAY_COUNT, T.ORDER_TYPE, T.ORDER_STATUS, T.ORG_CODE, T.ORG_NAME, T.WAREHOUSE_ID,\n");
		sql.append("       T.WAREHOUSE_CODE, T.WAREHOUSE_NAME, T.ORDER_AMOUNT, T.EXPECT_DATE,T.IF_CREDIT,T.IF_TRANS,T.CUSTORM_NAME,\n");
		sql.append("       T.TRANS_TYPE,T.DELIVERY_ADDR,T.LINK_MAN,T.PHONE,T.ZIP_CODE,T.REMARKS,T.COMPANY_ID,T.ORG_ID,T.CREATE_USER,\n");
		sql.append("       T.CREATE_TIME,T.UPDATE_USER,T.UPDATE_TIME,T.STATUS,T.OEM_COMPANY_ID,T.SECRET_LEVEL,T.IF_CHANEL_ORDER,\n");
		sql.append("       T.SOURCE_ORDER_NO,T.ADDR_TYPE,T.PROVINCE_CODE,T.PROVINCE_NAME,T.CITY_CODE,T.CITY_NAME,T.COUNTRY_CODE,T.COUNTRY_NAME,T.PROM_ID,T.APPLY_DATE,T.SHIP_STATUS,T.REAL_AMOUNT,T.IF_DELAY_ORDER,T.VIN\n");
		sql.append("  FROM PT_BU_SALE_ORDER T");
		bs = factory.select(sql.toString(), page);
		bs.setFieldDic("ORDER_TYPE", "DDLX");
		bs.setFieldDic("ORDER_STATUS", "DDZT");
		bs.setFieldDic("TRANS_TYPE", "FYFS");
		bs.setFieldDic("IF_CREDIT", "SF");
		bs.setFieldDic("IF_TRANS", "SF");
		bs.setFieldDateFormat("EXPECT_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDic("IF_DELAY_ORDER", DicConstant.SF);
		// 提报人
		bs.setFieldUserID("CREATE_USER");
		return bs;
	}

	/**
	 * 
	 * @Title: releasePartCountByOrderId
	 * @Description: 根据订单ID，释放订单中占用的配件数量
	 * @param user
	 * @param orderId
	 * @return
	 * @return: boolean
	 * @throws SQLException
	 */
	public void closeDealerOrder(User user, String orderId)
			throws SQLException {
		CallableStatement proc = null;
		proc = this.factory.getConnection().prepareCall(
				"{call P_CLOSE_DEALER_ORDER(?, ?)}");
		proc.setString("V_ORDER_ID", orderId);
		proc.setString("V_USER_ACCOUNT", user.getAccount());
		proc.execute();
	}
}
