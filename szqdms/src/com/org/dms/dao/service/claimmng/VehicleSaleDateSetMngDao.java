package com.org.dms.dao.service.claimmng;

import com.org.dms.vo.service.MainVehicleSdateLogVO;
import com.org.framework.base.*;
import com.org.framework.common.*;
import com.org.mvc.context.ActionContext;

/**
 * @Title: szqdms
 * @description: 车辆销售日期更改申请管理类
 * @throws Exception 设定文件
 * @auther fanpeng
 * @date 2014年09月10日 
 */
public class VehicleSaleDateSetMngDao extends BaseDAO
{
    //定义instance
    public  static final VehicleSaleDateSetMngDao getInstance(ActionContext atx)
    {
        VehicleSaleDateSetMngDao dao = new VehicleSaleDateSetMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	
	public boolean apply(MainVehicleSdateLogVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }

	/**
	 * 查询方法
	 * @param  page
	 * @param  code
	 * @param  conditions
	 * @return
	 * @throws Exception
	 */
    public BaseResultSet searchDealer(PageManager page, User user, String conditions, String dealerName) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND　APPLY_COMPANY ='" + dealerName +"'" ;
    	wheres += " ORDER　BY　CHECK_STATUS,APPLY_DATE" ;
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT LOG_ID,\n" );
        sql.append("       VIN,\n" );
        sql.append("       MODELS_CODE,\n" );
        sql.append("       USER_TYPE,\n" );
        sql.append("       OLD_SDATE,\n" );
        sql.append("       NEW_SDATE,\n" );
        sql.append("       APPLY_REASON,\n" );
        sql.append("       APPLY_COMPANY,\n" );
        sql.append("       APPLY_DATE,\n" );
        sql.append("       CHECK_REMARKS,\n" );
        sql.append("       CHECK_USER,\n" );
        sql.append("       CHECK_DATE,\n" );
        sql.append("       CHECK_STATUS\n" );
        sql.append("  FROM MAIN_VEHICLE_SDATE_LOG");

    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	//绑定字典方法，将字典代码翻译为字典名称
		bs.setFieldDic("CHECK_STATUS", "CLXSRQZT");
		//设置日期字段显示格式
		bs.setFieldDateFormat("OLD_SDATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("NEW_SDATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("CHECK_DATE", "yyyy-MM-dd");
    	return bs;
    }

    /**
     * 查询弹出框VIN
     * @throws Exception
     */
    public BaseResultSet vinSearch(PageManager page, User user, String conditions) throws Exception {
        StringBuilder wheres = new StringBuilder(conditions);
        wheres.append("   ORDER BY VEHICLE_ID DESC\n");
        page.setFilter(wheres.toString());

        //定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT VEHICLE_ID,\n");
        sql.append("       VIN,\n");
        sql.append("       MODELS_CODE,\n");
        sql.append("       USER_TYPE,\n");
        sql.append("       MAINTENANCE_DATE,\n");
        sql.append("       BUY_DATE\n");
        sql.append("  FROM MAIN_VEHICLE \n");

        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
		
        //设置日期字段显示格式
		bs.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("MAINTENANCE_DATE", "yyyy-MM-dd");
		bs.setFieldDic("USER_TYPE", "CLYHLX");
        return bs;
    }

}