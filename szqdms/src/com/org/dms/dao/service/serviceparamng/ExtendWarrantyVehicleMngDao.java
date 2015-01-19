package com.org.dms.dao.service.serviceparamng;

import com.org.dms.vo.service.SeBaExtendWarrantyVehicleVO;
import com.org.framework.base.*;
import com.org.framework.common.*;
import com.org.mvc.context.ActionContext;

/**
 * @Title: szqdms
 * @description: 延保策略与车辆关系管理类
 * @throws Exception 设定文件
 * @auther fanpeng
 * @date 2014年8月29日 
 */
public class ExtendWarrantyVehicleMngDao extends BaseDAO
{
    //定义instance
    public  static final ExtendWarrantyVehicleMngDao getInstance(ActionContext atx)
    {
        ExtendWarrantyVehicleMngDao dao = new ExtendWarrantyVehicleMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * 判断延保策略与车辆关系是否存在
     * @throws Exception
     */
	public QuerySet checkWarrantyVehicleCode(String vehicleId,String warrantyId) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT COUNT(1) NUMS \n");
    	sql.append(" FROM SE_BA_EXTEND_WARRANTY_VEHICLE \n");
    	sql.append(" WHERE VEHICLE_ID = '" + vehicleId +"' and WARRANTY_ID = '" + warrantyId +"'");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	
	public boolean insertExtendWarrantyVehicle(SeBaExtendWarrantyVehicleVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }
	
	public boolean updateExtendWarrantyVehicle(SeBaExtendWarrantyVehicleVO vo) throws Exception
    {
    	return factory.update(vo);
    }
	
	/**
	 * 删除延保策略与车辆关系
	 * 
	 * @throws Exception
	 * @Author fanpeng 2014-08-29
	 */
	public boolean deleteExtendWarrantyVehicle(String relationIds)
			throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" DELETE FROM SE_BA_EXTEND_WARRANTY_VEHICLE \n");
		sql.append(" WHERE RELATION_ID IN (" + relationIds + ") \n");
		return factory.update(sql.toString(), null);
	}

	/**
	 * 查询方法
	 * @throws Exception
	 */
    public BaseResultSet search(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres += " ORDER　BY　WARRANTY_CODE DESC" ;
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT WARRANTY_ID,\n" );
        sql.append("       WARRANTY_NAME,\n" );
        sql.append("       WARRANTY_CODE,\n" );
        sql.append("       RELATION_ID,\n" );
        sql.append("       VEHICLE_ID,\n" );
        sql.append("       VIN,\n" );
        sql.append("       CREATE_USER,\n" );
        sql.append("       CREATE_TIME,\n" );
        sql.append("       UPDATE_USER,\n" );
        sql.append("       UPDATE_TIME\n" );
        sql.append("  FROM SE_BA_EXTEND_WARRANTY_VEHICLE");

    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	//绑定字典方法，将字典代码翻译为字典名称
		bs.setFieldDic("STATUS", "YXBS");
		//设置日期字段显示格式
		bs.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	return bs;
    }
    
    /**
     * 查询弹出框VIN
     * @throws Exception
     */
    public BaseResultSet vinSearch(PageManager page, User user, String conditions) throws Exception {
        StringBuilder wheres = new StringBuilder(conditions);
        wheres.append("   ORDER BY BUY_DATE DESC\n");
        page.setFilter(wheres.toString());

        //定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT VEHICLE_ID,\n");
        sql.append("       VIN,\n");
        sql.append("       MODELS_CODE,\n");
        sql.append("       BUY_DATE\n");
        sql.append("  FROM MAIN_VEHICLE \n");

        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
		
        //设置日期字段显示格式
		bs.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
        return bs;
    }
}