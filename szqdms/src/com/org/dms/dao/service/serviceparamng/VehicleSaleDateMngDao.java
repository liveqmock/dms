package com.org.dms.dao.service.serviceparamng;

import com.org.dms.vo.service.MainVehicleSdateLogVO;
import com.org.dms.vo.service.MainVehicleVO;
import com.org.framework.base.*;
import com.org.framework.common.*;
import com.org.mvc.context.ActionContext;

/**
 * @Title: szqdms
 * @description: 民车/军车销售日期更改管理类
 * @throws Exception 设定文件
 * @auther fanpeng
 * @date 2014年7月27日 
 */
public class VehicleSaleDateMngDao extends BaseDAO
{
    //定义instance
    public  static final VehicleSaleDateMngDao getInstance(ActionContext atx)
    {
        VehicleSaleDateMngDao dao = new VehicleSaleDateMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	
	public boolean updateVehicleSaleDate(MainVehicleVO vo) throws Exception
    {
    	return factory.update(vo);
    }
	
	//修改成功后,给历史表插入数据
	public boolean insertVehicleSaleDateLog(MainVehicleSdateLogVO vo) throws Exception
    {
    	return factory.insert(vo);
    }
	
	//审批通过,更新车辆数据表和日志表
	public boolean check(MainVehicleSdateLogVO vo, String vin, String newSDate) throws Exception
    {
		StringBuffer sql= new StringBuffer();
		sql.append("UPDATE MAIN_VEHICLE SET BUY_DATE=to_date('"+newSDate+"','yyyy-mm-dd') WHERE VIN='"+vin+"' \n" );//更新车辆数据表中销售日期
		factory.update(sql.toString(), null);
		
		return factory.update(vo);
    }
	
	//审批驳回,更新日志表
	public boolean checkNo(MainVehicleSdateLogVO vo) throws Exception
    {	
		return factory.update(vo);
    }

    public BaseResultSet search(PageManager page, User user, String conditions,String userType) throws Exception
    {
    	String wheres = conditions;
		wheres += " AND USER_TYPE='" + userType + "'";
    	wheres += " ORDER　BY　MODELS_CODE" ;
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT *\n" );
        sql.append("  FROM MAIN_VEHICLE T");

    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
		//设置日期字段显示格式
		bs.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
    	return bs;
    }
    
	/**
	 * 查询所有状态的销售日期更改申请
	 */
    public BaseResultSet searchCheck(PageManager page, User user, String conditions, String userType) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND　USER_TYPE ='" + userType +"'" ;
    	wheres += " ORDER　BY　APPLY_COMPANY,APPLY_DATE" ;
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT LOG_ID,\n" );
        sql.append("       VIN,\n" );
        sql.append("       MODELS_CODE,\n" );
        sql.append("       (SELECT MAX(DECODE(SP.PRODUCTSTATE,\n" + 
						"                          '20',\n" + 
						"                          '在库',\n" + 
						"                          '30',\n" + 
						"                          '开票',\n" + 
						"                          '50',\n" + 
						"                          '在途',\n" + 
						"                          '60',\n" + 
						"                          '到货',\n" + 
						"                          '70',\n" + 
						"                          '已售',\n" + 
						"                          '其他'))\n" + 
						"          FROM SD2_PRODUCT SP\n" + 
						"         WHERE SP.VIN = SUBSTR(L.VIN, -8)) SALE_STATUS,\n" );
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
        sql.append("  FROM MAIN_VEHICLE_SDATE_LOG L");

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
}