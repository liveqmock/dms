package com.org.dms.dao.service.common;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class VehicleInfoMngDao extends BaseDAO{
	 //定义instance
    public  static final VehicleInfoMngDao getInstance(ActionContext atx)
    {
    	VehicleInfoMngDao dao = new VehicleInfoMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
    /**
     * @title: searchVehicleInfo 
   	 * @description: TODO( 根据vehicleId查询车辆信息)
	 * @param page
	 * @param user
	 * @param conditions
	 * @param vehicleId
	 * @return BaseResultSet
	 * @throws Exception
	 */
    public BaseResultSet searchVehicleInfo(PageManager page, User user, String conditions,String vehicleId) throws Exception
    {
    	String wheres = conditions;
    	wheres+=" AND V.VEHICLE_ID="+vehicleId;
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT V.VEHICLE_ID,\n" );
    	sql.append("       V.VIN,\n" );
    	sql.append("       V.ENGINE_NO,\n" );
    	sql.append("       V.VEHICLE_STATUS,\n" );
    	sql.append("       V.MODELS_ID,\n" );
    	sql.append("       V.MODELS_CODE,\n" );
    	sql.append("       V.CERTIFICATE,\n" );
    	sql.append("       V.ENGINE_TYPE,\n" );
    	sql.append("       V.USER_TYPE,\n" );
    	sql.append("       V.VEHICLE_USE,\n" );
    	sql.append("       V.DRIVE_FORM,\n" );
    	sql.append("       V.BUY_DATE,\n" );
    	sql.append("       V.MILEAGE,\n" );
    	sql.append("       V.GUARANTEE_NO,\n" );
    	sql.append("       V.FACTORY_DATE,\n" );
    	sql.append("       V.G_COUNT,\n" );
    	sql.append("       V.MAINTENANCE_DATE,\n" );
    	sql.append("       V.STATUS,\n" );
    	sql.append("       V.USER_NAME,\n" );
    	sql.append("       V.LINK_MAN,\n" );
    	sql.append("       V.USER_NO,\n" );
    	sql.append("       V.PHONE,\n" );
    	sql.append("       V.USER_ADDRESS,\n" );
    	sql.append("       V.CERTIFICATEDATE,\n" );
    	sql.append("       V.FMAINTAINFLAG,\n" );
    	sql.append("       V.DRUNKM,\n" );
    	sql.append("       V.LRUNKM,\n" );
    	sql.append("       V.LICENSE_PLATE\n" );
    	sql.append("  FROM MAIN_VEHICLE V");
    	bs = factory.select(sql.toString(), page);
		bs.setFieldDic("VEHICLE_STATUS", "CLZT");
		bs.setFieldDic("USER_TYPE", "YHLX");
		bs.setFieldDic("DRIVE_FORM", "QDXS");
		bs.setFieldDic("VEHICLE_USE", "CLYT");
		bs.setFieldDic("FMAINTAINFLAG", "SF");
		bs.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("FACTORY_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("MAINTENANCE_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("CERTIFICATEDATE", "yyyy-MM-dd");
    	return bs;
    }
    /**
     * @title: searchClaimInfo 
   	 * @description: TODO( 根据vehicleId查询索赔信息)
	 * @param page
	 * @param user
	 * @param conditions
	 * @param vehicleId
	 * @return BaseResultSet
	 * @throws Exception
	 */
    public BaseResultSet searchClaimInfo(PageManager page, User user, String conditions,String vehicleId) throws Exception
    {
    	String wheres = conditions;
    	//wheres+=" AND C.CLAIM_STATUS > 301001";
    	wheres+=" AND C.STATUS = 100201 AND C.VEHICLE_ID = "+vehicleId;
    	wheres+=" ORDER BY C.CLAIM_ID ";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT C.CLAIM_NO,\n" );
    	sql.append("       C.ORG_ID,\n" );
    	sql.append("       C.VIN,\n" );
    	sql.append("       C.CLAIM_TYPE,\n" );
    	sql.append("       C.CLAIM_STATUS,\n" );
    	sql.append("       C.CLAIM_ID,\n" );
    	sql.append("       C.MILEAGE,\n" );
    	sql.append("       C.REPAIR_DATE,\n" );
    	sql.append("       C.VEHICLE_ID,\n" );
    	sql.append("       C.STATUS\n" );
    	sql.append("  FROM SE_BU_CLAIM C\n" );
    	bs = factory.select(sql.toString(), page);
		bs.setFieldDic("CLAIM_STATUS", "SPDZT");
		bs.setFieldDic("CLAIM_TYPE", "SPDLX");
		bs.setFieldOrgDeptSimpleName("ORG_ID");
		bs.setFieldDateFormat("REPAIR_DATE", "yyyy-MM-dd HH:mm");
    	return bs;
    }
}
