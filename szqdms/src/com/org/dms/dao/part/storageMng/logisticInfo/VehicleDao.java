package com.org.dms.dao.part.storageMng.logisticInfo;

import java.sql.SQLException;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBaVehicleVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.mvc.context.ActionContext;

public class VehicleDao extends BaseDAO {
	// 定义instance
	public static final VehicleDao getInstance(ActionContext atx) {
		VehicleDao dao = new VehicleDao();
		atx.setDBFactory(dao.factory);
		return dao;
	}

	/**
	 * 判断车辆发动机号是否存在
	 * 
	 * @throws Exception
	 * @Author suoxiuli 2014-08-21
	 */
	public QuerySet checkEngineNo(String engineNO) throws Exception {
		QuerySet qs = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT COUNT(1) NUMS \n");
		sql.append(" FROM PT_BA_VEHICLE \n");
		sql.append(" WHERE ENGINE_NO = '" + engineNO + "'");
		sql.append(" AND STATUS = '" + DicConstant.YXBS_01 + "'");
		qs = factory.select(null, sql.toString());
		return qs;
	}

	/**
	 * 
	 * checkLicenseNoAndCarrierIdSame: 根据车牌号+承运商ID校验车辆是否已经维护： true：已维护，false：未维护
	 * 已维护车辆则不能再次维护
	 * @author fuxiao
	 * Date:2014年11月27日
	 *
	 */
	public boolean checkLicenseNoAndCarrierIdSame(String licenseNo,
			String carrierId) throws SQLException {
		return Integer.parseInt(this.factory.select( "SELECT COUNT(1)\n" +
						"  FROM PT_BA_VEHICLE T\n" + 
						" WHERE T.LICENSE_PLATE = ?\n" + 
						"   AND T.CARRIER_ID = ?"
					, new Object[] {
									licenseNo, carrierId })[0][0]) > 0;
	}

	/**
	 * 车辆信息新增
	 * 
	 * @throws Exception
	 * @Author suoxiuli 2014-08-21
	 */
	public boolean insertVehicle(PtBaVehicleVO vo) throws Exception {
		return factory.insert(vo);
	}

	/**
	 * 车辆信息修改
	 * 
	 * @throws Exception
	 * @Author suoxiuli 2014-08-21
	 */
	public boolean updateVehicle(PtBaVehicleVO vo) throws Exception {
		return factory.update(vo);
	}

	/**
	 * 车辆信息查询
	 * 
	 * @throws Exception
	 * @Author suoxiuli 2014-08-21
	 */
	public BaseResultSet search(PageManager page, String conditions)
			throws Exception {
		String wheres = conditions;
		// wheres += " and status="+DicConstant.YXBS_01+"  ";
		wheres += " ORDER BY CREATE_TIME, VEHICLE_ID DESC";
		page.setFilter(wheres);

		BaseResultSet bs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT CARRIER_ID,\n");
		sql.append("       CARRIER_NAME,\n");
		sql.append("       CARRIER_CODE,\n");
		sql.append("       VEHICLE_TYPE,\n");
		sql.append("       REMARKS,\n");
		sql.append("       VEHICLE_ID,\n");
		sql.append("       VIN,\n");
		sql.append("       ENGINE_NO,\n");
		sql.append("       LICENSE_PLATE,\n");
		sql.append("       COMPANY_ID,\n");
		sql.append("       ORG_ID,\n");
		sql.append("       CREATE_USER,\n");
		sql.append("       CREATE_TIME,\n");
		sql.append("       UPDATE_USER,\n");
		sql.append("       UPDATE_TIME,\n");
		
		sql.append("       DRIVER_NAME,\n");
		sql.append("       DRIVER_NO,\n");
		sql.append("       PHONE,\n");
		sql.append("       ADDRESS,\n");
		sql.append("       FIXED_LINE,\n");
		sql.append("       SEX,\n");
		sql.append("       EMAIL,\n");
		
		sql.append("       STATUS,\n");
		sql.append("       OEM_COMPANY_ID,\n");
		sql.append("       SECRET_LEVEL\n");
		sql.append("  FROM PT_BA_VEHICLE");

		bs = factory.select(sql.toString(), page);
		bs.setFieldDic("VEHICLE_TYPE", "CYCX");
		bs.setFieldDic("STATUS", "YXBS");
		bs.setFieldUserID("CREATE_USER");
		bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
		return bs;
	}

	/**
	 * 更新车辆信息的有效状态
	 * 
	 * @throws Exception
	 * @Author suoxiuli 2014-08-21
	 */
	/*
	 * public boolean updateVehicleStatus(String vehicleId, String updateUser,
	 * String status) throws Exception { StringBuffer sql = new StringBuffer();
	 * sql.append(" update pt_ba_vehicle \n"); sql.append(" set status = '" +
	 * status + "', \n"); sql.append(" update_user = '" + updateUser + "', \n");
	 * sql.append(" update_time = sysdate \n");
	 * sql.append(" where vehicle_id = '" + vehicleId + "' \n"); return
	 * factory.update(sql.toString(), null); }
	 */
}
