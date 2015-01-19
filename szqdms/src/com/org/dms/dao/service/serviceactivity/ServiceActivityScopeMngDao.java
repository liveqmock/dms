package com.org.dms.dao.service.serviceactivity;

import java.sql.SQLException;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.service.SeBuActivityVehicleVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;
/**
 * @Description:服务活动范围维护DAO
 * @Date: 2014-7-5
 * @author sunxuedong
 * @version 1.0
 * @remark 
 */
public class ServiceActivityScopeMngDao extends BaseDAO
{
    //定义instance
    public  static final ServiceActivityScopeMngDao getInstance(ActionContext atx)
    {
    	ServiceActivityScopeMngDao dao = new ServiceActivityScopeMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
    /**服务活动发布主页面查询方法
	 * @title: search
	 * @date 2014年7月2日09:14:52
	 */
    public BaseResultSet searchPublish(PageManager page, User user, String conditions,String oemCompanyId) throws Exception
    {
    	String wheres = conditions;
    	wheres +=" AND T.STATUS="+DicConstant.YXBS_01+""
    		   + " AND T.ACTIVITY_STATUS IN ("+DicConstant.HDZT_02+","+DicConstant.HDZT_01+","+DicConstant.HDZT_03+")"
    		   + " AND T.OEM_COMPANY_ID="+oemCompanyId+""
    		   + " ORDER BY T.ACTIVITY_ID";
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.ACTIVITY_ID,\n" );
    	sql.append("       T.ACTIVITY_CODE,\n" );
    	sql.append("       T.ACTIVITY_NAME,\n" );
    	sql.append("       T.ACTIVITY_TYPE,\n" );
    	sql.append("       T.ACTIVITY_STATUS,\n" );
    	sql.append("       T.MANAGE_TYPE,\n" );
    	sql.append("       BEGIN_MILEAGE,\n" );
    	sql.append("       END_MILEAGE,\n" );
    	sql.append("       IN_ACCOUNT_TYPE,\n" );
    	sql.append("       IF_PERSON_CHECK,\n" );
    	sql.append("       T.START_DATE,\n" );
    	sql.append("       T.END_DATE,\n" );
    	sql.append("       T.IF_CLAIM,\n" );
    	sql.append("       T.IF_FIXCOSTS,\n" );
    	sql.append("       T.ACTIVITY_COSTS,\n" );
    	sql.append("       T.SOLUTION,\n" );
    	sql.append("       T.REMARKS,\n" );
    	sql.append("       T.COMPANY_ID,\n" );
    	sql.append("       T.ORG_ID,\n" );
    	sql.append("       T.CREATE_USER,\n" );
    	sql.append("       T.CREATE_TIME,\n" );
    	sql.append("       T.UPDATE_USER,\n" );
    	sql.append("       T.UPDATE_TIME,\n" );
    	sql.append("       T.STATUS\n" );
    	sql.append("  FROM SE_BU_ACTIVITY T");
    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
		bs.setFieldDateFormat("START_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("END_DATE", "yyyy-MM-dd");
		bs.setFieldDic("STATUS","YXBS");
		bs.setFieldDic("IF_CLAIM","SF");
		bs.setFieldDic("IF_FIXCOSTS","SF");
		bs.setFieldDic("ACTIVITY_TYPE","HDLB");
		bs.setFieldDic("ACTIVITY_STATUS","HDZT");
		bs.setFieldDic("MANAGE_TYPE","CLFS");
    	return bs;
    }
    /**服务活动车辆登记主页面查询方法
   	 * @title: search
   	 * @date 2014年7月2日09:14:52
   	 */
       public BaseResultSet searchVIN(PageManager page, User user, String conditions,String orgId,String vin) throws Exception
       {
       	String wheres = conditions;
       	wheres +="AND T.ACTIVITY_ID = SA.ACTIVITY_ID\n"
       		   + "AND T.STATUS="+DicConstant.YXBS_01+"\n"
       		   + "AND T.ACTIVITY_STATUS IN ("+DicConstant.HDZT_02+")\n"
       		   + "AND SA.ORG_ID="+orgId+"\n"
       		   + "ORDER BY T.ACTIVITY_ID";
           page.setFilter(wheres);
       	//定义返回结果集
       	BaseResultSet bs = null;
       	StringBuffer sql= new StringBuffer();
       	sql.append("SELECT T.ACTIVITY_ID,\n" );
       	sql.append("       T.ACTIVITY_CODE,\n" );
       	sql.append("       T.ACTIVITY_NAME,\n" );
       	sql.append("       T.ACTIVITY_TYPE,\n" );
       	sql.append("       T.ACTIVITY_STATUS,\n" );
       	sql.append("       T.MANAGE_TYPE,\n" );
       	sql.append("       T.MILEAGE,\n" );
       	sql.append("       T.START_DATE,\n" );
       	sql.append("       T.END_DATE,\n" );
       	sql.append("       T.IF_CLAIM,\n" );
       	sql.append("       T.IF_FIXCOSTS,\n" );
       	sql.append("       T.ACTIVITY_COSTS,\n" );
       	sql.append("       T.SOLUTION,\n" );
       	sql.append("       T.REMARKS,\n" );
       	sql.append("       T.COMPANY_ID,\n" );
       	sql.append("       T.ORG_ID,\n" );
       	sql.append("       T.CREATE_USER,\n" );
       	sql.append("       T.CREATE_TIME,\n" );
       	sql.append("       T.UPDATE_USER,\n" );
       	sql.append("       T.UPDATE_TIME\n" );
       	sql.append("  FROM SE_BU_ACTIVITY T,SE_BU_ACTIVITY_AREA SA\n" );
       	//执行方法，不需要传递conn参数
       	bs = factory.select(sql.toString(), page);
   		bs.setFieldDateFormat("START_DATE", "yyyy-MM-dd");
   		bs.setFieldDateFormat("END_DATE", "yyyy-MM-dd");
   		bs.setFieldDic("STATUS","YXBS");
   		bs.setFieldDic("IF_CLAIM","SF");
   		bs.setFieldDic("IF_FIXCOSTS","SF");
   		bs.setFieldDic("ACTIVITY_TYPE","HDLB");
   		bs.setFieldDic("ACTIVITY_STATUS","HDZT");
   		bs.setFieldDic("MANAGE_TYPE","CLFS");
       	return bs;
       }
       /**根据vin从服务活动车辆表，与服务活动表中查询出显示数据
        * @title: searchVehicleVin
        */
       public BaseResultSet searchVehicleVin(PageManager page, User user, String conditions,String orgId,String vehicleVin,String dateType,String buyDate,String factoryDate) throws Exception
       {
    	   //定义返回结果集
    	   BaseResultSet bs = null;
    	   StringBuffer sql= new StringBuffer();
    	   sql.append("SELECT DISTINCT *\n" );
    	   sql.append("  FROM (SELECT SA.ACTIVITY_CODE,--以VIN作为条件查询\n" );
    	   sql.append("               '"+vehicleVin+"' AS VEHICLE_VIN,\n" );
    	   sql.append("               SA.ACTIVITY_ID,\n" );
    	   sql.append("               SA.ACTIVITY_NAME,\n" );
    	   sql.append("               SA.ACTIVITY_TYPE,\n" );
    	   sql.append("               SA.MANAGE_TYPE,\n" );
    	   sql.append("               SA.START_DATE,\n" );
    	   sql.append("               SA.END_DATE,\n" );
    	   sql.append("               SA.ACTIVITY_STATUS,\n" );
    	   sql.append("               SA.BEGIN_MILEAGE,\n" );
    	   sql.append("               SA.END_MILEAGE,\n" );
    	   sql.append("               SA.IN_ACCOUNT_TYPE,\n" );
    	   sql.append("               SA.IF_PERSON_CHECK,\n" );
    	   sql.append("               SA.IF_CLAIM,\n" );
    	   sql.append("               SA.IF_FIXCOSTS,\n" );
    	   sql.append("               SA.ACTIVITY_COSTS,\n" );
    	   sql.append("               SA.SOLUTION,\n" );
    	   sql.append("               SA.REMARKS,\n" );
    	   sql.append("               SA.COMPANY_ID,\n" );
    	   sql.append("               SA.ORG_ID,\n" );
    	   sql.append("               SA.CREATE_USER,\n" );
    	   sql.append("               SA.CREATE_TIME,\n" );
    	   sql.append("               SA.UPDATE_USER,\n" );
    	   sql.append("               SA.UPDATE_TIME,\n" );
    	   sql.append("               SA.STATUS,\n" );
    	   sql.append("               SA.OEM_COMPANY_ID\n" );
    	   sql.append("          FROM SE_BU_ACTIVITY_VEHICLE SV,\n" );
    	   sql.append("               SE_BU_ACTIVITY         SA,\n" );
    	   sql.append("               SE_BU_ACTIVITY_AREA    AA\n" );
    	   sql.append("         WHERE 1 = 1\n" );
    	   sql.append("           AND SA.ACTIVITY_ID = SV.ACTIVITY_ID\n" );
    	   sql.append("           AND AA.ACTIVITY_ID = SA.ACTIVITY_ID\n" );
    	   sql.append("           AND SA.STATUS = 100201\n" );
    	   sql.append("           AND SA.ACTIVITY_STATUS = 300702\n" );
    	   sql.append("           AND SV.REGIST_STATUS = 304901\n" );
    	   sql.append("           AND SV.VIN = '"+vehicleVin+"'\n" );
    	   sql.append("           AND AA.ORG_ID = "+orgId+"\n" );
    	   sql.append("        UNION ALL --无任何限制条件的活动\n" );
    	   sql.append("        SELECT SA1.ACTIVITY_CODE,\n" );
    	   sql.append("               '"+vehicleVin+"' AS VEHICLE_VIN,\n" );
    	   sql.append("               SA1.ACTIVITY_ID,\n" );
    	   sql.append("               SA1.ACTIVITY_NAME,\n" );
    	   sql.append("               SA1.ACTIVITY_TYPE,\n" );
    	   sql.append("               SA1.MANAGE_TYPE,\n" );
    	   sql.append("               SA1.START_DATE,\n" );
    	   sql.append("               SA1.END_DATE,\n" );
    	   sql.append("               SA1.ACTIVITY_STATUS,\n" );
    	   sql.append("               SA1.BEGIN_MILEAGE,\n" );
    	   sql.append("               SA1.END_MILEAGE,\n" );
    	   sql.append("               SA1.IN_ACCOUNT_TYPE,\n" );
    	   sql.append("               SA1.IF_PERSON_CHECK,\n" );
    	   sql.append("               SA1.IF_CLAIM,\n" );
    	   sql.append("               SA1.IF_FIXCOSTS,\n" );
    	   sql.append("               SA1.ACTIVITY_COSTS,\n" );
    	   sql.append("               SA1.SOLUTION,\n" );
    	   sql.append("               SA1.REMARKS,\n" );
    	   sql.append("               SA1.COMPANY_ID,\n" );
    	   sql.append("               SA1.ORG_ID,\n" );
    	   sql.append("               SA1.CREATE_USER,\n" );
    	   sql.append("               SA1.CREATE_TIME,\n" );
    	   sql.append("               SA1.UPDATE_USER,\n" );
    	   sql.append("               SA1.UPDATE_TIME,\n" );
    	   sql.append("               SA1.STATUS,\n" );
    	   sql.append("               SA1.OEM_COMPANY_ID\n" );
    	   sql.append("          FROM SE_BU_ACTIVITY SA1, SE_BU_ACTIVITY_AREA AA1\n" );
    	   sql.append("         WHERE SA1.ACTIVITY_ID = AA1.ACTIVITY_ID\n" );
    	   sql.append("           AND SA1.BEGIN_MILEAGE IS NULL\n" );
    	   sql.append("           AND SA1.END_MILEAGE IS NULL\n" );
    	   sql.append("           AND SA1.STATUS = 100201\n" );
    	   sql.append("           AND SA1.ACTIVITY_STATUS = 300702\n" );
    	   sql.append("           AND AA1.ORG_ID = "+orgId+"\n" );
    	   sql.append("           AND NOT EXISTS (SELECT 1\n" );
    	   sql.append("                  FROM SE_BU_ACTIVITY_MODELS SM\n" );
    	   sql.append("                 WHERE SA1.ACTIVITY_ID = SM.ACTIVITY_ID)\n" );
    	   sql.append("           AND NOT EXISTS (SELECT 1\n" );
    	   sql.append("                  FROM SE_BU_ACTIVITY_VEHICLE V\n" );
    	   sql.append("                 WHERE V.ACTIVITY_ID = SA1.ACTIVITY_ID\n" );
    	   sql.append("                   AND V.IF_REGIST_PRODUCE IS NULL)\n" );
    	   sql.append("           AND EXISTS (SELECT 1\n" );
    	   sql.append("                  FROM SE_BU_ACTIVITY_VEHAGE SM\n" );
    	   sql.append("                 WHERE SA1.ACTIVITY_ID = SM.ACTIVITY_ID\n" );
    	   sql.append("                   AND SM.START_DATE IS NULL\n" );
    	   sql.append("                   AND SM.END_DATE IS NULL)\n" );
    	   sql.append("        UNION ALL --限制车辆行驶里程的活动\n" );
    	   sql.append("        SELECT SA1.ACTIVITY_CODE,\n" );
    	   sql.append("               '"+vehicleVin+"' AS VEHICLE_VIN,\n" );
    	   sql.append("               SA1.ACTIVITY_ID,\n" );
    	   sql.append("               SA1.ACTIVITY_NAME,\n" );
    	   sql.append("               SA1.ACTIVITY_TYPE,\n" );
    	   sql.append("               SA1.MANAGE_TYPE,\n" );
    	   sql.append("               SA1.START_DATE,\n" );
    	   sql.append("               SA1.END_DATE,\n" );
    	   sql.append("               SA1.ACTIVITY_STATUS,\n" );
    	   sql.append("               SA1.BEGIN_MILEAGE,\n" );
    	   sql.append("               SA1.END_MILEAGE,\n" );
    	   sql.append("               SA1.IN_ACCOUNT_TYPE,\n" );
    	   sql.append("               SA1.IF_PERSON_CHECK,\n" );
    	   sql.append("               SA1.IF_CLAIM,\n" );
    	   sql.append("               SA1.IF_FIXCOSTS,\n" );
    	   sql.append("               SA1.ACTIVITY_COSTS,\n" );
    	   sql.append("               SA1.SOLUTION,\n" );
    	   sql.append("               SA1.REMARKS,\n" );
    	   sql.append("               SA1.COMPANY_ID,\n" );
    	   sql.append("               SA1.ORG_ID,\n" );
    	   sql.append("               SA1.CREATE_USER,\n" );
    	   sql.append("               SA1.CREATE_TIME,\n" );
    	   sql.append("               SA1.UPDATE_USER,\n" );
    	   sql.append("               SA1.UPDATE_TIME,\n" );
    	   sql.append("               SA1.STATUS,\n" );
    	   sql.append("               SA1.OEM_COMPANY_ID\n" );
    	   sql.append("          FROM SE_BU_ACTIVITY SA1, SE_BU_ACTIVITY_AREA AA1\n" );
    	   sql.append("         WHERE SA1.ACTIVITY_ID = AA1.ACTIVITY_ID\n" );
    	   sql.append("           AND SA1.BEGIN_MILEAGE <\n" );
    	   sql.append("               (SELECT MV.MILEAGE FROM MAIN_VEHICLE MV WHERE MV.SVIN = '"+vehicleVin+"')\n" );
    	   sql.append("           AND SA1.END_MILEAGE >\n" );
    	   sql.append("               (SELECT MV.MILEAGE FROM MAIN_VEHICLE MV WHERE MV.SVIN = '"+vehicleVin+"')\n" );
    	   sql.append("           AND SA1.STATUS = 100201\n" );
    	   sql.append("           AND SA1.ACTIVITY_STATUS = 300702\n" );
    	   sql.append("           AND AA1.ORG_ID = "+orgId+"\n" );
    	   sql.append("           AND NOT EXISTS (SELECT 1\n" );
    	   sql.append("                  FROM SE_BU_ACTIVITY_MODELS SM\n" );
    	   sql.append("                 WHERE SA1.ACTIVITY_ID = SM.ACTIVITY_ID)\n" );
    	   sql.append("           AND NOT EXISTS (SELECT 1\n" );
    	   sql.append("                  FROM SE_BU_ACTIVITY_VEHICLE V\n" );
    	   sql.append("                 WHERE V.ACTIVITY_ID = SA1.ACTIVITY_ID\n" );
    	   sql.append("                   AND V.IF_REGIST_PRODUCE IS NULL)\n" );
    	   sql.append("           AND EXISTS (SELECT 1\n" );
    	   sql.append("                  FROM SE_BU_ACTIVITY_VEHAGE SM\n" );
    	   sql.append("                 WHERE SA1.ACTIVITY_ID = SM.ACTIVITY_ID\n" );
    	   sql.append("                   AND SM.START_DATE IS NULL\n" );
    	   sql.append("                   AND SM.END_DATE IS NULL)\n" );
    	   sql.append("        UNION ALL --限制车辆型号的服务活动\n" );
    	   sql.append("        SELECT SA1.ACTIVITY_CODE,\n" );
    	   sql.append("               '"+vehicleVin+"' AS VEHICLE_VIN,\n" );
    	   sql.append("               SA1.ACTIVITY_ID,\n" );
    	   sql.append("               SA1.ACTIVITY_NAME,\n" );
    	   sql.append("               SA1.ACTIVITY_TYPE,\n" );
    	   sql.append("               SA1.MANAGE_TYPE,\n" );
    	   sql.append("               SA1.START_DATE,\n" );
    	   sql.append("               SA1.END_DATE,\n" );
    	   sql.append("               SA1.ACTIVITY_STATUS,\n" );
    	   sql.append("               SA1.BEGIN_MILEAGE,\n" );
    	   sql.append("               SA1.END_MILEAGE,\n" );
    	   sql.append("               SA1.IN_ACCOUNT_TYPE,\n" );
    	   sql.append("               SA1.IF_PERSON_CHECK,\n" );
    	   sql.append("               SA1.IF_CLAIM,\n" );
    	   sql.append("               SA1.IF_FIXCOSTS,\n" );
    	   sql.append("               SA1.ACTIVITY_COSTS,\n" );
    	   sql.append("               SA1.SOLUTION,\n" );
    	   sql.append("               SA1.REMARKS,\n" );
    	   sql.append("               SA1.COMPANY_ID,\n" );
    	   sql.append("               SA1.ORG_ID,\n" );
    	   sql.append("               SA1.CREATE_USER,\n" );
    	   sql.append("               SA1.CREATE_TIME,\n" );
    	   sql.append("               SA1.UPDATE_USER,\n" );
    	   sql.append("               SA1.UPDATE_TIME,\n" );
    	   sql.append("               SA1.STATUS,\n" );
    	   sql.append("               SA1.OEM_COMPANY_ID\n" );
    	   sql.append("          FROM SE_BU_ACTIVITY        SA1,\n" );
    	   sql.append("               SE_BU_ACTIVITY_AREA   AA1,\n" );
    	   sql.append("               SE_BU_ACTIVITY_VEHAGE AG,\n" );
    	   sql.append("               SE_BU_ACTIVITY_MODELS SM\n" );
    	   sql.append("         WHERE SA1.ACTIVITY_ID = AA1.ACTIVITY_ID\n" );
    	   sql.append("           AND AG.ACTIVITY_ID = SA1.ACTIVITY_ID\n" );
    	   sql.append("           AND SA1.BEGIN_MILEAGE IS NULL\n" );
    	   sql.append("           AND SA1.END_MILEAGE IS NULL\n" );
    	   sql.append("           AND SA1.ACTIVITY_ID = SM.ACTIVITY_ID\n" );
    	   sql.append("           AND SA1.STATUS = 100201\n" );
    	   sql.append("           AND SA1.ACTIVITY_STATUS = 300702\n" );
    	   sql.append("           AND AA1.ORG_ID = "+orgId+"\n" );
    	   sql.append("           AND SM.MODELS_ID =(SELECT MV.MODELS_ID FROM MAIN_VEHICLE MV WHERE MV.SVIN = '"+vehicleVin+"')\n" );
    	   sql.append("           AND NOT EXISTS (SELECT 1\n" );
    	   sql.append("                  FROM SE_BU_ACTIVITY_VEHICLE V\n" );
    	   sql.append("                 WHERE V.ACTIVITY_ID = SA1.ACTIVITY_ID\n" );
    	   sql.append("                   AND V.IF_REGIST_PRODUCE IS NULL)\n" );
    	   sql.append("           AND EXISTS (SELECT 1\n" );
    	   sql.append("                  FROM SE_BU_ACTIVITY_VEHAGE SM\n" );
    	   sql.append("                 WHERE SA1.ACTIVITY_ID = SM.ACTIVITY_ID\n" );
    	   sql.append("                   AND SM.START_DATE IS NULL\n" );
    	   sql.append("                   AND SM.END_DATE IS NULL)\n" );
    	   sql.append("        UNION ALL --限制车辆销售/生成日期的服务活动。\n" );
    	   sql.append("        SELECT SA1.ACTIVITY_CODE,\n" );
    	   sql.append("               '"+vehicleVin+"' AS VEHICLE_VIN,\n" );
    	   sql.append("               SA1.ACTIVITY_ID,\n" );
    	   sql.append("               SA1.ACTIVITY_NAME,\n" );
    	   sql.append("               SA1.ACTIVITY_TYPE,\n" );
    	   sql.append("               SA1.MANAGE_TYPE,\n" );
    	   sql.append("               SA1.START_DATE,\n" );
    	   sql.append("               SA1.END_DATE,\n" );
    	   sql.append("               SA1.ACTIVITY_STATUS,\n" );
    	   sql.append("               SA1.BEGIN_MILEAGE,\n" );
    	   sql.append("               SA1.END_MILEAGE,\n" );
    	   sql.append("               SA1.IN_ACCOUNT_TYPE,\n" );
    	   sql.append("               SA1.IF_PERSON_CHECK,\n" );
    	   sql.append("               SA1.IF_CLAIM,\n" );
    	   sql.append("               SA1.IF_FIXCOSTS,\n" );
    	   sql.append("               SA1.ACTIVITY_COSTS,\n" );
    	   sql.append("               SA1.SOLUTION,\n" );
    	   sql.append("               SA1.REMARKS,\n" );
    	   sql.append("               SA1.COMPANY_ID,\n" );
    	   sql.append("               SA1.ORG_ID,\n" );
    	   sql.append("               SA1.CREATE_USER,\n" );
    	   sql.append("               SA1.CREATE_TIME,\n" );
    	   sql.append("               SA1.UPDATE_USER,\n" );
    	   sql.append("               SA1.UPDATE_TIME,\n" );
    	   sql.append("               SA1.STATUS,\n" );
    	   sql.append("               SA1.OEM_COMPANY_ID\n" );
    	   sql.append("          FROM SE_BU_ACTIVITY        SA1,\n" );
    	   sql.append("               SE_BU_ACTIVITY_AREA   AA1,\n" );
    	   sql.append("               SE_BU_ACTIVITY_VEHAGE AG\n" );
    	   sql.append("         WHERE SA1.ACTIVITY_ID = AA1.ACTIVITY_ID\n" );
    	   sql.append("           AND AG.ACTIVITY_ID = SA1.ACTIVITY_ID\n" );
    	   sql.append("           AND SA1.BEGIN_MILEAGE IS NULL\n" );
    	   sql.append("           AND SA1.END_MILEAGE IS NULL\n" );
    	   sql.append("           AND SA1.STATUS = 100201\n" );
    	   sql.append("           AND SA1.ACTIVITY_STATUS = 300702\n" );
    	   sql.append("           AND AA1.ORG_ID = "+orgId+"\n" );
    	   if(dateType.equals(DicConstant.RQLX_01)){
    		   
    		   sql.append("   AND AG.VEHAGE_TYPE = "+DicConstant.RQLX_01+"\n");
    		   sql.append("   AND AG.START_DATE <= TO_DATE('"+buyDate+"','YYYY-MM-DD HH24:MI:SS')\n");
        	   sql.append("   AND AG.END_DATE>= TO_DATE('"+buyDate+"','YYYY-MM-DD HH24:MI:SS')\n" ); 
    	   }
    	   if(dateType.equals(DicConstant.RQLX_02)){
    		   sql.append("   AND AG.VEHAGE_TYPE = "+DicConstant.RQLX_02+"\n");
    		   sql.append("   AND AG.START_DATE <=TO_DATE('"+factoryDate+"','YYYY-MM-DD HH24:MI:SS')\n");
    		   sql.append("   AND AG.END_DATE>=TO_DATE('"+factoryDate+"','YYYY-MM-DD HH24:MI:SS')\n" ); 
    	   }
    	   sql.append("           AND NOT EXISTS (SELECT 1\n" );
    	   sql.append("                  FROM SE_BU_ACTIVITY_MODELS SM\n" );
    	   sql.append("                 WHERE SA1.ACTIVITY_ID = SM.ACTIVITY_ID)\n" );
    	   sql.append("           AND NOT EXISTS (SELECT 1\n" );
    	   sql.append("                  FROM SE_BU_ACTIVITY_VEHICLE V\n" );
    	   sql.append("                 WHERE V.ACTIVITY_ID = SA1.ACTIVITY_ID\n" );
    	   sql.append("                   AND V.IF_REGIST_PRODUCE IS NULL)\n" );
    	   sql.append("           AND EXISTS (SELECT 1\n" );
    	   sql.append("                  FROM SE_BU_ACTIVITY_VEHAGE SM\n" );
    	   sql.append("                 WHERE SA1.ACTIVITY_ID = SM.ACTIVITY_ID\n" );
    	   sql.append("                   AND SM.START_DATE IS NULL\n" );
    	   sql.append("                   AND SM.END_DATE IS NULL))");
    	   
    	   bs = factory.select(sql.toString(), page);
    	   bs.setFieldDateFormat("START_DATE", "yyyy-MM-dd");
    	   bs.setFieldDateFormat("END_DATE", "yyyy-MM-dd");
    	   bs.setFieldDic("STATUS","YXBS");
    	   bs.setFieldDic("IF_CLAIM","SF");
    	   bs.setFieldDic("IF_FIXCOSTS","SF");
    	   bs.setFieldDic("ACTIVITY_TYPE","HDLB");
    	   bs.setFieldDic("ACTIVITY_STATUS","HDZT");
    	   bs.setFieldDic("MANAGE_TYPE","CLFS");
    	   return bs;
       }
       /**根据vin从车辆表，与服务活动表中查询出显示数据
        * @title: searchVehicleModels
        */
       public BaseResultSet searchVehicleModels(PageManager page, User user, String conditions,String orgId,String vehicleVin) throws Exception
       {
    	   //定义返回结果集
    	   BaseResultSet bs = null;
    	   StringBuffer sql= new StringBuffer();
    	   sql.append("SELECT DISTINCT T.ACTIVITY_ID,\n" );
    	   sql.append("                '"+vehicleVin+"' AS VEHICLE_VIN,\n" );
    	   sql.append("                T.ACTIVITY_CODE,\n" );
    	   sql.append("                T.ACTIVITY_NAME,\n" );
    	   sql.append("                T.ACTIVITY_TYPE,\n" );
    	   sql.append("                T.ACTIVITY_STATUS,\n" );
    	   sql.append("                T.MANAGE_TYPE,\n" );
    	   sql.append("                T.BEGIN_MILEAGE,\n" );
    	   sql.append("                T.END_MILEAGE,\n" );
    	   sql.append("                T.IN_ACCOUNT_TYPE,\n" );
    	   sql.append("                T.IF_PERSON_CHECK,\n" );
    	   sql.append("                T.START_DATE,\n" );
    	   sql.append("                T.END_DATE,\n" );
    	   sql.append("                T.IF_CLAIM,\n" );
    	   sql.append("                T.IF_FIXCOSTS,\n" );
    	   sql.append("                T.ACTIVITY_COSTS,\n" );
    	   sql.append("                T.SOLUTION,\n" );
    	   sql.append("                T.REMARKS,\n" );
    	   sql.append("                T.COMPANY_ID,\n" );
    	   sql.append("                T.ORG_ID,\n" );
    	   sql.append("                T.CREATE_USER,\n" );
    	   sql.append("                T.CREATE_TIME,\n" );
    	   sql.append("                T.UPDATE_USER,\n" );
    	   sql.append("                T.UPDATE_TIME\n" );
    	   sql.append("  FROM SE_BU_ACTIVITY        T,\n" );
    	   sql.append("       SE_BU_ACTIVITY_AREA   SA,\n" );
    	   sql.append("       SE_BU_ACTIVITY_MODELS SM,\n" );
    	   sql.append("       MAIN_VEHICLE          MV\n" );
    	   sql.append(" WHERE 1 = 1\n" );
    	   sql.append("   AND SA.ACTIVITY_ID = T.ACTIVITY_ID\n" );
    	   sql.append("   AND T.ACTIVITY_ID = SM.ACTIVITY_ID\n" );
    	   sql.append("   AND SM.MODELS_ID = MV.MODELS_ID\n" );
    	   sql.append("   AND NOT EXISTS (SELECT 1\n" );
    	   sql.append("          FROM SE_BU_ACTIVITY_VEHICLE V\n" );
    	   sql.append("         WHERE V.ACTIVITY_ID = T.ACTIVITY_ID\n" );
    	   sql.append("           AND V.IF_REGIST_PRODUCE IS NULL)\n" );
    	   sql.append("   AND MV.VIN = '"+vehicleVin+"'\n" );
    	   sql.append("   AND T.STATUS = 100201\n" );
    	   sql.append("   AND T.ACTIVITY_STATUS = 300702\n" );
    	   sql.append("   AND SA.ORG_ID = "+orgId+"\n" );
    	   sql.append("UNION ALL\n" );
    	   sql.append("SELECT SA1.ACTIVITY_ID,\n" );
    	   sql.append("       '"+vehicleVin+"' AS VEHICLE_VIN,\n" );
    	   sql.append("       SA1.ACTIVITY_CODE,\n" );
    	   sql.append("       SA1.ACTIVITY_NAME,\n" );
    	   sql.append("       SA1.ACTIVITY_TYPE,\n" );
    	   sql.append("       SA1.ACTIVITY_STATUS,\n" );
    	   sql.append("       SA1.MANAGE_TYPE,\n" );
    	   sql.append("       SA1.BEGIN_MILEAGE,\n" );
    	   sql.append("       SA1.END_MILEAGE,\n" );
    	   sql.append("       SA1.IN_ACCOUNT_TYPE,\n" );
    	   sql.append("       SA1.IF_PERSON_CHECK,\n" );
    	   sql.append("       SA1.START_DATE,\n" );
    	   sql.append("       SA1.END_DATE,\n" );
    	   sql.append("       SA1.IF_CLAIM,\n" );
    	   sql.append("       SA1.IF_FIXCOSTS,\n" );
    	   sql.append("       SA1.ACTIVITY_COSTS,\n" );
    	   sql.append("       SA1.SOLUTION,\n" );
    	   sql.append("       SA1.REMARKS,\n" );
    	   sql.append("       SA1.COMPANY_ID,\n" );
    	   sql.append("       SA1.ORG_ID,\n" );
    	   sql.append("       SA1.CREATE_USER,\n" );
    	   sql.append("       SA1.CREATE_TIME,\n" );
    	   sql.append("       SA1.UPDATE_USER,\n" );
    	   sql.append("       SA1.UPDATE_TIME\n" );
    	   sql.append("  FROM SE_BU_ACTIVITY SA1, SE_BU_ACTIVITY_AREA AA1\n" );
    	   sql.append(" WHERE SA1.ACTIVITY_ID = AA1.ACTIVITY_ID\n" );
    	   sql.append("   AND SA1.BEGIN_MILEAGE IS NULL\n" );
    	   sql.append("   AND SA1.END_MILEAGE IS NULL\n" );
    	   sql.append("   AND SA1.STATUS = 100201\n" );
    	   sql.append("   AND SA1.ACTIVITY_STATUS = 300702\n" );
    	   sql.append("   AND AA1.ORG_ID = "+orgId+"\n" );
    	   sql.append("   AND NOT EXISTS (SELECT 1\n" );
    	   sql.append("          FROM SE_BU_ACTIVITY_MODELS SM\n" );
    	   sql.append("         WHERE SA1.ACTIVITY_ID = SM.ACTIVITY_ID)\n" );
    	   sql.append("   AND NOT EXISTS (SELECT 1\n" );
    	   sql.append("          FROM SE_BU_ACTIVITY_PART SM\n" );
    	   sql.append("         WHERE SA1.ACTIVITY_ID = SM.ACTIVITY_ID)\n" );
    /*	   sql.append("   AND NOT EXISTS (SELECT 1\n" );
    	   sql.append("          FROM SE_BU_ACTIVITY_TASK_TIME SM\n" );
    	   sql.append("         WHERE SA1.ACTIVITY_ID = SM.ACTIVITY_ID)\n" );*/
    	   sql.append("   AND NOT EXISTS (SELECT 1\n" );
    	   sql.append("          FROM SE_BU_ACTIVITY_VEHICLE V\n" );
    	   sql.append("         WHERE V.ACTIVITY_ID = SA1.ACTIVITY_ID\n" );
    	   sql.append("           AND V.IF_REGIST_PRODUCE IS NULL)\n" );
    	   sql.append("   AND EXISTS (SELECT 1\n" );
    	   sql.append("          FROM SE_BU_ACTIVITY_VEHAGE SM\n" );
    	   sql.append("         WHERE SA1.ACTIVITY_ID = SM.ACTIVITY_ID\n" );
    	   sql.append("           AND SM.START_DATE IS NULL\n" );
    	   sql.append("           AND SM.END_DATE IS NULL)");
    	   
    	   bs = factory.select(sql.toString(), page);
    	   bs.setFieldDateFormat("START_DATE", "yyyy-MM-dd");
    	   bs.setFieldDateFormat("END_DATE", "yyyy-MM-dd");
    	   bs.setFieldDic("STATUS","YXBS");
    	   bs.setFieldDic("IF_CLAIM","SF");
    	   bs.setFieldDic("IF_FIXCOSTS","SF");
    	   bs.setFieldDic("ACTIVITY_TYPE","HDLB");
    	   bs.setFieldDic("ACTIVITY_STATUS","HDZT");
    	   bs.setFieldDic("MANAGE_TYPE","CLFS");
    	   return bs;
       }
       /**根据vin从车辆表，与服务活动表中查询出显示数据
        * @title: searchVehicleModels
        */
       public BaseResultSet searchAllActivity(PageManager page, User user, String conditions,String orgId,String vehicleVin) throws Exception
       {
    	   //定义返回结果集
    	   page.setFilter("");
    	   BaseResultSet bs = null;
    	   StringBuffer sql= new StringBuffer();
    	   sql.append("SELECT T.ACTIVITY_ID,\n" );
    	   sql.append("       '"+vehicleVin+"' AS VEHICLE_VIN,\n" );
    	   sql.append("       T.ACTIVITY_CODE,\n" );
    	   sql.append("       T.ACTIVITY_NAME,\n" );
    	   sql.append("       T.ACTIVITY_TYPE,\n" );
    	   sql.append("       T.ACTIVITY_STATUS,\n" );
    	   sql.append("       T.MANAGE_TYPE,\n" );
       	   sql.append("       T.BEGIN_MILEAGE,\n" );
           sql.append("       T.END_MILEAGE,\n" );
       	   sql.append("       T.IN_ACCOUNT_TYPE,\n" );
       	   sql.append("       T.IF_PERSON_CHECK,\n" );
    	   sql.append("       T.START_DATE,\n" );
    	   sql.append("       T.END_DATE,\n" );
    	   sql.append("       T.IF_CLAIM,\n" );
    	   sql.append("       T.IF_FIXCOSTS,\n" );
    	   sql.append("       T.ACTIVITY_COSTS,\n" );
    	   sql.append("       T.SOLUTION,\n" );
    	   sql.append("       T.REMARKS,\n" );
    	   sql.append("       T.COMPANY_ID,\n" );
    	   sql.append("       T.ORG_ID,\n" );
    	   sql.append("       T.CREATE_USER,\n" );
    	   sql.append("       T.CREATE_TIME,\n" );
    	   sql.append("       T.UPDATE_USER,\n" );
    	   sql.append("       T.UPDATE_TIME\n" );
    	   sql.append("  FROM SE_BU_ACTIVITY T, SE_BU_ACTIVITY_AREA SA\n" );
    	   sql.append(" WHERE SA.ACTIVITY_ID = T.ACTIVITY_ID\n" );
    	   sql.append("   AND T.BEGIN_MILEAGE IS NULL\n" );
    	   sql.append("   AND T.END_MILEAGE IS NULL\n" );
    	   sql.append("   AND NOT EXISTS (SELECT 1\n" );
    	   sql.append("          FROM SE_BU_ACTIVITY_MODELS SM\n" );
    	   sql.append("         WHERE T.ACTIVITY_ID = SM.ACTIVITY_ID)\n" );
    	   sql.append("   AND NOT EXISTS (SELECT 1\n" );
    	   sql.append("          FROM SE_BU_ACTIVITY_PART SM\n" );
    	   sql.append("         WHERE T.ACTIVITY_ID = SM.ACTIVITY_ID)\n" );
    	   sql.append("   AND NOT EXISTS (SELECT 1\n" );
    	   sql.append("          FROM SE_BU_ACTIVITY_VEHICLE SM\n" );
    	   sql.append("         WHERE T.ACTIVITY_ID = SM.ACTIVITY_ID)\n" );
/*    	   sql.append("   AND NOT EXISTS (SELECT 1\n" );
    	   sql.append("          FROM SE_BU_ACTIVITY_TASK_TIME SM\n" );
    	   sql.append("         WHERE T.ACTIVITY_ID = SM.ACTIVITY_ID)\n" );*/
    	   sql.append("   AND NOT EXISTS (SELECT 1\n" );
    	   sql.append("          FROM SE_BU_ACTIVITY_VEHAGE SM\n" );
    	   sql.append("         WHERE T.ACTIVITY_ID = SM.ACTIVITY_ID\n" );
    	   sql.append("   AND NOT EXISTS ( SELECT 1 FROM SE_BU_ACTIVITY_VEHICLE V \n");
    	   sql.append("  	 WHERE V.ACTIVITY_ID = T.ACTIVITY_ID \n");
    	   sql.append("   	 AND V.IF_REGIST_PRODUCE IS NULL) \n");
    	   sql.append("           AND T.START_DATE IS NULL\n" );
    	   sql.append("           AND T.END_DATE IS NULL)\n" );
    	   sql.append("   AND T.STATUS="+DicConstant.YXBS_01+"\n" );
    	   sql.append("   AND T.ACTIVITY_STATUS = "+DicConstant.HDZT_02+"\n" );
    	   sql.append("   AND SA.ORG_ID = "+orgId+"\n" );
    	   sql.append("union all\n" );
    	   sql.append("SELECT T.ACTIVITY_ID,\n" );
    	   sql.append("       '"+vehicleVin+"' AS VEHICLE_VIN,\n" );
    	   sql.append("       T.ACTIVITY_CODE,\n" );
    	   sql.append("       T.ACTIVITY_NAME,\n" );
    	   sql.append("       T.ACTIVITY_TYPE,\n" );
    	   sql.append("       T.ACTIVITY_STATUS,\n" );
    	   sql.append("       T.MANAGE_TYPE,\n" );
       	   sql.append("       T.BEGIN_MILEAGE,\n" );
           sql.append("       T.END_MILEAGE,\n" );
       	   sql.append("       T.IN_ACCOUNT_TYPE,\n" );
       	   sql.append("       T.IF_PERSON_CHECK,\n" );
    	   sql.append("       T.START_DATE,\n" );
    	   sql.append("       T.END_DATE,\n" );
    	   sql.append("       T.IF_CLAIM,\n" );
    	   sql.append("       T.IF_FIXCOSTS,\n" );
    	   sql.append("       T.ACTIVITY_COSTS,\n" );
    	   sql.append("       T.SOLUTION,\n" );
    	   sql.append("       T.REMARKS,\n" );
    	   sql.append("       T.COMPANY_ID,\n" );
    	   sql.append("       T.ORG_ID,\n" );
    	   sql.append("       T.CREATE_USER,\n" );
    	   sql.append("       T.CREATE_TIME,\n" );
    	   sql.append("       T.UPDATE_USER,\n" );
    	   sql.append("       T.UPDATE_TIME\n" );
    	   sql.append("  FROM SE_BU_ACTIVITY T\n" );
    	   sql.append(" WHERE T.BEGIN_MILEAGE IS NULL\n" );
    	   sql.append("   AND T.END_MILEAGE IS NULL\n" );
    	   sql.append("   AND NOT EXISTS (SELECT 1\n" );
    	   sql.append("          FROM SE_BU_ACTIVITY_MODELS SM\n" );
    	   sql.append("         WHERE T.ACTIVITY_ID = SM.ACTIVITY_ID)\n" );
    	   sql.append("   AND NOT EXISTS (SELECT 1\n" );
    	   sql.append("          FROM SE_BU_ACTIVITY_PART SM\n" );
    	   sql.append("         WHERE T.ACTIVITY_ID = SM.ACTIVITY_ID)\n" );
    	   sql.append("   AND NOT EXISTS (SELECT 1\n" );
    	   sql.append("          FROM SE_BU_ACTIVITY_VEHICLE SM\n" );
    	   sql.append("         WHERE T.ACTIVITY_ID = SM.ACTIVITY_ID)\n" );
/*    	   sql.append("   AND NOT EXISTS (SELECT 1\n" );
    	   sql.append("          FROM SE_BU_ACTIVITY_TASK_TIME SM\n" );
    	   sql.append("         WHERE T.ACTIVITY_ID = SM.ACTIVITY_ID)\n" );*/
    	   sql.append("   AND NOT EXISTS (SELECT 1\n" );
    	   sql.append("          FROM SE_BU_ACTIVITY_VEHAGE SM\n" );
    	   sql.append("         WHERE T.ACTIVITY_ID = SM.ACTIVITY_ID\n" );
    	   sql.append("           AND T.START_DATE IS NULL\n" );
    	   sql.append("           AND T.END_DATE IS NULL)\n" );
    	   sql.append("   AND NOT EXISTS (SELECT 1\n" );
    	   sql.append("          FROM SE_BU_ACTIVITY_AREA SA\n" );
    	   sql.append("         WHERE T.ACTIVITY_ID = SA.ACTIVITY_ID)\n" );
    	   sql.append("   AND T.STATUS="+DicConstant.YXBS_01+"\n" );
    	   sql.append("   AND T.ACTIVITY_STATUS = "+DicConstant.HDZT_02+"\n" );
    	   bs = factory.select(sql.toString(), page);
    	   bs.setFieldDateFormat("START_DATE", "yyyy-MM-dd");
    	   bs.setFieldDateFormat("END_DATE", "yyyy-MM-dd");
    	   bs.setFieldDic("STATUS","YXBS");
    	   bs.setFieldDic("IF_CLAIM","SF");
    	   bs.setFieldDic("IF_FIXCOSTS","SF");
    	   bs.setFieldDic("ACTIVITY_TYPE","HDLB");
    	   bs.setFieldDic("ACTIVITY_STATUS","HDZT");
    	   bs.setFieldDic("MANAGE_TYPE","CLFS");
    	   return bs;
       }
       /**判断车辆的行驶里程是否满足服务活动要求的行驶里程
        * @title: searchVehicleVin
        * @date 2014年7月2日09:14:52
        */
       public BaseResultSet searchVehicleMileage(PageManager page, User user, String conditions,String orgId,String mileage,String vehicleVin) throws Exception
       {
    	   page.setFilter("");
    	   //定义返回结果集
    	   BaseResultSet bs = null;
    	   StringBuffer sql= new StringBuffer();
    	   sql.append("SELECT '"+vehicleVin+"' AS VEHICLE_VIN,\n" );
    	   sql.append("       T.ACTIVITY_ID,\n" );
    	   sql.append("       T.ACTIVITY_CODE,\n" );
    	   sql.append("       T.ACTIVITY_NAME,\n" );
    	   sql.append("       T.ACTIVITY_TYPE,\n" );
    	   sql.append("       T.ACTIVITY_STATUS,\n" );
    	   sql.append("       T.MANAGE_TYPE,\n" );
    	   sql.append("       T.BEGIN_MILEAGE,\n" );
    	   sql.append("       T.END_MILEAGE,\n" );
    	   sql.append("       T.IN_ACCOUNT_TYPE,\n" );
    	   sql.append("       T.IF_PERSON_CHECK,\n" );
    	   sql.append("       T.START_DATE,\n" );
    	   sql.append("       T.END_DATE,\n" );
    	   sql.append("       T.IF_CLAIM,\n" );
    	   sql.append("       T.IF_FIXCOSTS,\n" );
    	   sql.append("       T.ACTIVITY_COSTS,\n" );
    	   sql.append("       T.SOLUTION,\n" );
    	   sql.append("       T.REMARKS,\n" );
    	   sql.append("       T.COMPANY_ID,\n" );
    	   sql.append("       T.ORG_ID,\n" );
    	   sql.append("       T.CREATE_USER,\n" );
    	   sql.append("       T.CREATE_TIME,\n" );
    	   sql.append("       T.UPDATE_USER,\n" );
    	   sql.append("       T.UPDATE_TIME\n" );
    	   sql.append("  FROM SE_BU_ACTIVITY T, SE_BU_ACTIVITY_AREA SA\n" );
    	   sql.append(" WHERE SA.ACTIVITY_ID = T.ACTIVITY_ID\n" );
    	   sql.append("   AND T.BEGIN_MILEAGE <= "+mileage+"\n" );
    	   sql.append("   AND T.BEGIN_MILEAGE IS NOT NULL\n" );
    	   sql.append("   AND NOT EXISTS (SELECT 1\n" );
    	   sql.append("          FROM SE_BU_ACTIVITY_VEHICLE V\n" );
    	   sql.append("         WHERE V.ACTIVITY_ID = T.ACTIVITY_ID\n" );
    	   sql.append("           AND V.IF_REGIST_PRODUCE IS NULL)\n" );
    	   sql.append("   AND T.END_MILEAGE >= "+mileage+"\n" );
    	   sql.append("   AND T.END_MILEAGE IS NOT NULL\n" );
    	   sql.append("   AND SA.ORG_ID = "+orgId+"\n" );
    	   sql.append("   AND T.STATUS = 100201\n" );
    	   sql.append("   AND T.ACTIVITY_STATUS = 300702\n" );
    	   sql.append("UNION ALL\n" );
    	   sql.append("SELECT '"+vehicleVin+"' AS VEHICLE_VIN,\n" );
    	   sql.append("       SA1.ACTIVITY_ID,\n" );
    	   sql.append("       SA1.ACTIVITY_CODE,\n" );
    	   sql.append("       SA1.ACTIVITY_NAME,\n" );
    	   sql.append("       SA1.ACTIVITY_TYPE,\n" );
    	   sql.append("       SA1.ACTIVITY_STATUS,\n" );
    	   sql.append("       SA1.MANAGE_TYPE,\n" );
    	   sql.append("       SA1.BEGIN_MILEAGE,\n" );
    	   sql.append("       SA1.END_MILEAGE,\n" );
    	   sql.append("       SA1.IN_ACCOUNT_TYPE,\n" );
    	   sql.append("       SA1.IF_PERSON_CHECK,\n" );
    	   sql.append("       SA1.START_DATE,\n" );
    	   sql.append("       SA1.END_DATE,\n" );
    	   sql.append("       SA1.IF_CLAIM,\n" );
    	   sql.append("       SA1.IF_FIXCOSTS,\n" );
    	   sql.append("       SA1.ACTIVITY_COSTS,\n" );
    	   sql.append("       SA1.SOLUTION,\n" );
    	   sql.append("       SA1.REMARKS,\n" );
    	   sql.append("       SA1.COMPANY_ID,\n" );
    	   sql.append("       SA1.ORG_ID,\n" );
    	   sql.append("       SA1.CREATE_USER,\n" );
    	   sql.append("       SA1.CREATE_TIME,\n" );
    	   sql.append("       SA1.UPDATE_USER,\n" );
    	   sql.append("       SA1.UPDATE_TIME\n" );
    	   sql.append("  FROM SE_BU_ACTIVITY SA1, SE_BU_ACTIVITY_AREA AA1\n" );
    	   sql.append(" WHERE SA1.ACTIVITY_ID = AA1.ACTIVITY_ID\n" );
    	   sql.append("   AND SA1.BEGIN_MILEAGE IS NULL\n" );
    	   sql.append("   AND SA1.END_MILEAGE IS NULL\n" );
    	   sql.append("   AND SA1.STATUS = 100201\n" );
    	   sql.append("   AND SA1.ACTIVITY_STATUS = 300702\n" );
    	   sql.append("   AND AA1.ORG_ID = "+orgId+"\n" );
    	   sql.append("   AND NOT EXISTS (SELECT 1\n" );
    	   sql.append("          FROM SE_BU_ACTIVITY_MODELS SM\n" );
    	   sql.append("         WHERE SA1.ACTIVITY_ID = SM.ACTIVITY_ID)\n" );
    	   sql.append("   AND NOT EXISTS (SELECT 1\n" );
    	   sql.append("          FROM SE_BU_ACTIVITY_PART SM\n" );
    	   sql.append("         WHERE SA1.ACTIVITY_ID = SM.ACTIVITY_ID)\n" );
/*    	   sql.append("   AND NOT EXISTS (SELECT 1\n" );
    	   sql.append("          FROM SE_BU_ACTIVITY_TASK_TIME SM\n" );
    	   sql.append("         WHERE SA1.ACTIVITY_ID = SM.ACTIVITY_ID)\n" );*/
    	   sql.append("   AND NOT EXISTS (SELECT 1\n" );
    	   sql.append("          FROM SE_BU_ACTIVITY_VEHICLE V\n" );
    	   sql.append("         WHERE V.ACTIVITY_ID = SA1.ACTIVITY_ID\n" );
    	   sql.append("           AND V.IF_REGIST_PRODUCE IS NULL)\n" );
    	   sql.append("   AND EXISTS (SELECT 1\n" );
    	   sql.append("          FROM SE_BU_ACTIVITY_VEHAGE SM\n" );
    	   sql.append("         WHERE SA1.ACTIVITY_ID = SM.ACTIVITY_ID\n" );
    	   sql.append("           AND SM.START_DATE IS NULL\n" );
    	   sql.append("           AND SM.END_DATE IS NULL)");
    	   
    	   bs = factory.select(sql.toString(), page);
    	   bs.setFieldDateFormat("START_DATE", "yyyy-MM-dd");
    	   bs.setFieldDateFormat("END_DATE", "yyyy-MM-dd");
    	   bs.setFieldDic("STATUS","YXBS");
    	   bs.setFieldDic("IF_CLAIM","SF");
    	   bs.setFieldDic("IF_FIXCOSTS","SF");
    	   bs.setFieldDic("ACTIVITY_TYPE","HDLB");
    	   bs.setFieldDic("ACTIVITY_STATUS","HDZT");
    	   bs.setFieldDic("MANAGE_TYPE","CLFS");
    	   return bs;
       }
       /**判断车辆的购买日期与出厂日期是否满足服务活动要求的日期
        * @title: searchVehicleVin
        * @date 2014年7月2日09:14:52
        */
       public BaseResultSet searchVehicleVehage(PageManager page, User user, String conditions,String orgId,String dateType,String buyDate,String factoryDate,String vehicleVin) throws Exception
       {
    	   //定义返回结果集
    	   page.setFilter("");
    	   BaseResultSet bs = null;
    	   StringBuffer sql= new StringBuffer();
    	   sql.append("SELECT T.ACTIVITY_ID,\n" );
    	   sql.append("       '"+vehicleVin+"' AS VEHICLE_VIN,\n" );
    	   sql.append("       T.ACTIVITY_CODE,\n" );
    	   sql.append("       T.ACTIVITY_NAME,\n" );
    	   sql.append("       T.ACTIVITY_TYPE,\n" );
    	   sql.append("       T.ACTIVITY_STATUS,\n" );
    	   sql.append("       T.MANAGE_TYPE,\n" );
       	   sql.append("       T.BEGIN_MILEAGE,\n" );
           sql.append("       T.END_MILEAGE,\n" );
       	   sql.append("       T.IN_ACCOUNT_TYPE,\n" );
       	   sql.append("       T.IF_PERSON_CHECK,\n" );
    	   sql.append("       T.START_DATE,\n" );
    	   sql.append("       T.END_DATE,\n" );
    	   sql.append("       T.IF_CLAIM,\n" );
    	   sql.append("       T.IF_FIXCOSTS,\n" );
    	   sql.append("       T.ACTIVITY_COSTS,\n" );
    	   sql.append("       T.SOLUTION,\n" );
    	   sql.append("       T.REMARKS,\n" );
    	   sql.append("       T.COMPANY_ID,\n" );
    	   sql.append("       T.ORG_ID,\n" );
    	   sql.append("       T.CREATE_USER,\n" );
    	   sql.append("       T.CREATE_TIME,\n" );
    	   sql.append("       T.UPDATE_USER,\n" );
    	   sql.append("       T.UPDATE_TIME\n" );
    	   sql.append("  FROM SE_BU_ACTIVITY T,SE_BU_ACTIVITY_AREA SA,SE_BU_ACTIVITY_VEHAGE SV\n" );
    	   sql.append(" WHERE SV.VEHAGE_TYPE ="+dateType+"\n");
    	   if(dateType.equals(DicConstant.RQLX_01)){
    		   sql.append("   AND SV.VEHAGE_TYPE = "+DicConstant.RQLX_02+"\n");
    		   sql.append("   AND SV.START_DATE <= TO_DATE('"+buyDate+"','YYYY-MM-DD HH24:MI:SS')\n");
        	   sql.append("   AND SV.END_DATE>= TO_DATE('"+buyDate+"','YYYY-MM-DD HH24:MI:SS')\n" ); 
    	   }
    	   if(dateType.equals(DicConstant.RQLX_02)){
    		   sql.append("   AND SV.VEHAGE_TYPE = "+DicConstant.RQLX_02+"\n");
    		   sql.append("   AND SV.START_DATE <=TO_DATE('"+factoryDate+"','YYYY-MM-DD HH24:MI:SS')\n");
    		   sql.append("   AND SV.END_DATE>=TO_DATE('"+factoryDate+"','YYYY-MM-DD HH24:MI:SS')\n" ); 
    	   }
    	   sql.append("    AND SV.START_DATE IS NOT NULL\n" );
    	   sql.append("   AND SV.END_DATE IS NOT NULL\n" );
    	   sql.append("   AND SA.ACTIVITY_ID = T.ACTIVITY_ID\n" );
    	   sql.append("   AND NOT EXISTS ( SELECT 1 FROM SE_BU_ACTIVITY_VEHICLE V \n");
    	   sql.append("  	 WHERE V.ACTIVITY_ID = T.ACTIVITY_ID \n");
    	   sql.append("  	 AND V.IF_REGIST_PRODUCE IS NULL) \n");
    	   sql.append("   AND T.ACTIVITY_ID = SV.ACTIVITY_ID\n" );
    	   sql.append("   AND SA.ORG_ID = "+orgId+"\n");
    	   sql.append("   AND T.STATUS="+DicConstant.YXBS_01+"\n");
    	   sql.append("   AND T.ACTIVITY_STATUS ="+DicConstant.HDZT_02+"\n");
    	   sql.append("   UNION ALL\n");
    	   sql.append("SELECT SA1.ACTIVITY_ID,\n" );
    	   sql.append("       '"+vehicleVin+"' AS VEHICLE_VIN,\n" );
    	   sql.append("       SA1.ACTIVITY_CODE,\n" );
    	   sql.append("       SA1.ACTIVITY_NAME,\n" );
    	   sql.append("       SA1.ACTIVITY_TYPE,\n" );
    	   sql.append("       SA1.ACTIVITY_STATUS,\n" );
    	   sql.append("       SA1.MANAGE_TYPE,\n" );
    	   sql.append("       SA1.BEGIN_MILEAGE,\n" );
    	   sql.append("       SA1.END_MILEAGE,\n" );
    	   sql.append("       SA1.IN_ACCOUNT_TYPE,\n" );
    	   sql.append("       SA1.IF_PERSON_CHECK,\n" );
    	   sql.append("       SA1.START_DATE,\n" );
    	   sql.append("       SA1.END_DATE,\n" );
    	   sql.append("       SA1.IF_CLAIM,\n" );
    	   sql.append("       SA1.IF_FIXCOSTS,\n" );
    	   sql.append("       SA1.ACTIVITY_COSTS,\n" );
    	   sql.append("       SA1.SOLUTION,\n" );
    	   sql.append("       SA1.REMARKS,\n" );
    	   sql.append("       SA1.COMPANY_ID,\n" );
    	   sql.append("       SA1.ORG_ID,\n" );
    	   sql.append("       SA1.CREATE_USER,\n" );
    	   sql.append("       SA1.CREATE_TIME,\n" );
    	   sql.append("       SA1.UPDATE_USER,\n" );
    	   sql.append("       SA1.UPDATE_TIME\n" );
    	   sql.append("  FROM SE_BU_ACTIVITY SA1, SE_BU_ACTIVITY_AREA AA1\n" );
    	   sql.append(" WHERE SA1.ACTIVITY_ID = AA1.ACTIVITY_ID\n" );
    	   sql.append("   AND SA1.BEGIN_MILEAGE IS NULL\n" );
    	   sql.append("   AND SA1.END_MILEAGE IS NULL\n" );
    	   sql.append("   AND SA1.STATUS = 100201\n" );
    	   sql.append("   AND SA1.ACTIVITY_STATUS = 300702\n" );
    	   sql.append("   AND AA1.ORG_ID = "+orgId+"\n" );
    	   sql.append("   AND NOT EXISTS (SELECT 1\n" );
    	   sql.append("          FROM SE_BU_ACTIVITY_MODELS SM\n" );
    	   sql.append("         WHERE SA1.ACTIVITY_ID = SM.ACTIVITY_ID)\n" );
    	   sql.append("   AND NOT EXISTS (SELECT 1\n" );
    	   sql.append("          FROM SE_BU_ACTIVITY_PART SM\n" );
    	   sql.append("         WHERE SA1.ACTIVITY_ID = SM.ACTIVITY_ID)\n" );
/*    	   sql.append("   AND NOT EXISTS (SELECT 1\n" );
    	   sql.append("          FROM SE_BU_ACTIVITY_TASK_TIME SM\n" );
    	   sql.append("         WHERE SA1.ACTIVITY_ID = SM.ACTIVITY_ID)\n" );*/
    	   sql.append("   AND NOT EXISTS (SELECT 1\n" );
    	   sql.append("          FROM SE_BU_ACTIVITY_VEHICLE V\n" );
    	   sql.append("         WHERE V.ACTIVITY_ID = SA1.ACTIVITY_ID\n" );
    	   sql.append("           AND V.IF_REGIST_PRODUCE IS NULL)\n" );
    	   sql.append("   AND EXISTS (SELECT 1\n" );
    	   sql.append("          FROM SE_BU_ACTIVITY_VEHAGE SM\n" );
    	   sql.append("         WHERE SA1.ACTIVITY_ID = SM.ACTIVITY_ID\n" );
    	   sql.append("           AND SM.START_DATE IS NULL\n" );
    	   sql.append("           AND SM.END_DATE IS NULL)");

    	   bs = factory.select(sql.toString(), page);
    	   bs.setFieldDateFormat("START_DATE", "yyyy-MM-dd");
    	   bs.setFieldDateFormat("END_DATE", "yyyy-MM-dd");
    	   bs.setFieldDic("STATUS","YXBS");
    	   bs.setFieldDic("IF_CLAIM","SF");
    	   bs.setFieldDic("IF_FIXCOSTS","SF");
    	   bs.setFieldDic("ACTIVITY_TYPE","HDLB");
    	   bs.setFieldDic("ACTIVITY_STATUS","HDZT");
    	   bs.setFieldDic("MANAGE_TYPE","CLFS");
    	   return bs;
       }
       /**
      	 * @title: search
      	 * @date 2014年7月2日09:14:52
      	 */
          public BaseResultSet searchVehicel(PageManager page, User user, String conditions,String orgId) throws Exception
          {
          	String wheres = conditions;
          	wheres 
          		   +="AND T.ACTIVITY_ID = SA.ACTIVITY_ID\n"
          		   + "AND T.STATUS = "+DicConstant.YXBS_01+" \n"
          		   + "AND T.ORG_ID="+orgId+"\n";
              page.setFilter(wheres);
          	//定义返回结果集
          	BaseResultSet bs = null;
          	StringBuffer sql= new StringBuffer();
          	sql.append("SELECT T.RELATION_ID,\n" );
          	sql.append("       T.ACTIVITY_ID,\n" );
          	sql.append("       T.ACTIVITY_CODE,\n" );
          	sql.append("       T.ACTIVITY_NAME,\n" );
          	sql.append("       T.VEHICLE_ID,\n" );
          	sql.append("       T.CLAIM_USER,\n" );
          	sql.append("       T.APPLY_DATE,\n" );
          	sql.append("       T.ORG_ID,\n" );
          	sql.append("       T.ORG_CODE,\n" );
          	sql.append("       T.ORG_NAME,\n" );
          	sql.append("       T.CREATE_USER,\n" );
          	sql.append("       T.CREATE_TIME,\n" );
          	sql.append("       T.UPDATE_USER,\n" );
          	sql.append("       T.UPDATE_TIME,\n" );
          	sql.append("       T.VIN,\n" );
          	sql.append("       SA.SOLUTION\n" );
          	sql.append("  FROM SE_BU_ACTIVITY_VEHICLE T,\n" );
          	sql.append("       SE_BU_ACTIVITY SA\n" );
          	//执行方法，不需要传递conn参数
          	bs = factory.select(sql.toString(), page);
      		bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
      		bs.setFieldDic("STATUS","YXBS");
      		bs.setFieldDic("CLAIM_USER","SF");
          	return bs;
          }
          /**
           * @title: search
           * @date 2014年7月2日09:14:52
           */
          public BaseResultSet searchOemVehicel(PageManager page, User user, String conditions) throws Exception
          {
        	  String wheres = conditions;
        	  wheres  +="AND T.ACTIVITY_ID = SA.ACTIVITY_ID\n"
        	  		  + "AND TG.ORG_ID = T.ORG_ID\n"
        			  + "AND T.STATUS = "+DicConstant.YXBS_01+"\n"
        			  + "ORDER BY TG.CODE,T.ACTIVITY_ID";
        	  page.setFilter(wheres);
        	  //定义返回结果集
        	  BaseResultSet bs = null;
        	  StringBuffer sql= new StringBuffer();
        	  sql.append("SELECT T.RELATION_ID,\n" );
        	  sql.append("       T.ACTIVITY_ID,\n" );
        	  sql.append("       T.ACTIVITY_CODE,\n" );
        	  sql.append("       T.ACTIVITY_NAME,\n" );
        	  sql.append("       T.VEHICLE_ID,\n" );
        	  sql.append("       T.CLAIM_USER,\n" );
        	  sql.append("       T.APPLY_DATE,\n" );
        	  sql.append("       T.ORG_ID,\n" );
        	  sql.append("       T.ORG_CODE,\n" );
        	  sql.append("       T.ORG_NAME,\n" );
        	  sql.append("       T.CREATE_USER,\n" );
        	  sql.append("       T.CREATE_TIME,\n" );
        	  sql.append("       T.UPDATE_USER,\n" );
        	  sql.append("       T.UPDATE_TIME,\n" );
        	  sql.append("       T.VIN,\n" );
        	  sql.append("       TG.CODE,\n" );
        	  sql.append("       TG.ONAME,\n" );
        	  sql.append("       SA.SOLUTION\n" );
        	  sql.append("  FROM SE_BU_ACTIVITY_VEHICLE T,SE_BU_ACTIVITY SA,TM_ORG TG\n" );
        	  //执行方法，不需要传递conn参数
        	  bs = factory.select(sql.toString(), page);
        	  bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
        	  bs.setFieldDic("STATUS","YXBS");
        	  bs.setFieldDic("CLAIM_USER","SF");
        	  return bs;
          }
    /**服务活动查询主页面查询方法
	 * @title: OemSearch
	 * @date 2014年7月2日09:14:52
	 */
    public BaseResultSet OemSearch(PageManager page, User user, String conditions,String oemCompanyId) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND T.STATUS="+DicConstant.YXBS_01+""
    			+ "AND T.OEM_COMPANY_ID="+oemCompanyId+"";
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.ACTIVITY_ID,\n" );
    	sql.append("       T.ACTIVITY_CODE,\n" );
    	sql.append("       T.ACTIVITY_NAME,\n" );
    	sql.append("       T.ACTIVITY_TYPE,\n" );
    	sql.append("       T.ACTIVITY_STATUS,\n" );
    	sql.append("       T.MANAGE_TYPE,\n" );
    	sql.append("       BEGIN_MILEAGE,\n" );
    	sql.append("       END_MILEAGE,\n" );
    	sql.append("       IN_ACCOUNT_TYPE,\n" );
    	sql.append("       IF_PERSON_CHECK,\n" );
    	sql.append("       T.START_DATE,\n" );
    	sql.append("       T.END_DATE,\n" );
    	sql.append("       T.IF_CLAIM,\n" );
    	sql.append("       T.IF_FIXCOSTS,\n" );
    	sql.append("       T.ACTIVITY_COSTS,\n" );
    	sql.append("       T.SOLUTION,\n" );
    	sql.append("       T.REMARKS,\n" );
    	sql.append("       T.COMPANY_ID,\n" );
    	sql.append("       T.ORG_ID,\n" );
    	sql.append("       T.CREATE_USER,\n" );
    	sql.append("       T.CREATE_TIME,\n" );
    	sql.append("       T.UPDATE_USER,\n" );
    	sql.append("       T.UPDATE_TIME\n" );
    	sql.append("  FROM SE_BU_ACTIVITY T");
    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
		bs.setFieldDateFormat("START_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("END_DATE", "yyyy-MM-dd");
		bs.setFieldDic("STATUS","YXBS");
		bs.setFieldDic("IF_CLAIM","SF");
		bs.setFieldDic("IF_FIXCOSTS","SF");
		bs.setFieldDic("ACTIVITY_TYPE","HDLB");
		bs.setFieldDic("ACTIVITY_STATUS","HDZT");
		bs.setFieldDic("MANAGE_TYPE","CLFS");
    	return bs;
    }
    /**服务活动范围维护主页面查询方法
	 * @title: search
	 * @date 2014年7月2日09:14:52
	 */
    public BaseResultSet searchScope(PageManager page, User user, String conditions,String oemCompanyId) throws Exception
    {
    	String wheres = conditions;
    	wheres += "AND T.STATUS="+DicConstant.YXBS_01+""
    			+ "AND T.ACTIVITY_STATUS IN ("+DicConstant.HDZT_01+","+DicConstant.HDZT_03+")"
    			+ "AND T.OEM_COMPANY_ID="+oemCompanyId+""
    			+ "ORDER BY ACTIVITY_ID";
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT ACTIVITY_ID,\n" );
    	sql.append("       ACTIVITY_CODE,\n" );
    	sql.append("       ACTIVITY_NAME,\n" );
    	sql.append("       ACTIVITY_TYPE,\n" );
    	sql.append("       ACTIVITY_STATUS,\n" );
    	sql.append("       MANAGE_TYPE,\n" );
    	sql.append("       BEGIN_MILEAGE,\n" );
    	sql.append("       END_MILEAGE,\n" );
    	sql.append("       IN_ACCOUNT_TYPE,\n" );
    	sql.append("       IF_PERSON_CHECK,\n" );
    	sql.append("       START_DATE,\n" );
    	sql.append("       END_DATE,\n" );
    	sql.append("       IF_CLAIM,\n" );
    	sql.append("       IF_FIXCOSTS,\n" );
    	sql.append("       ACTIVITY_COSTS,\n" );
    	sql.append("       SOLUTION,\n" );
    	sql.append("       REMARKS,\n" );
    	sql.append("       COMPANY_ID,\n" );
    	sql.append("       ORG_ID,\n" );
    	sql.append("       CREATE_USER,\n" );
    	sql.append("       CREATE_TIME,\n" );
    	sql.append("       UPDATE_USER,\n" );
    	sql.append("       UPDATE_TIME,\n" );
    	sql.append("       NVL ((SELECT COUNT(A.ACTIVITY_ID) FROM SE_BU_ACTIVITY_AREA A WHERE  A.ACTIVITY_ID(+) = T.ACTIVITY_ID GROUP BY ACTIVITY_ID),0)SL\n" );
    	sql.append("  FROM SE_BU_ACTIVITY T");
    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
		bs.setFieldDateFormat("START_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("END_DATE", "yyyy-MM-dd");
		bs.setFieldDic("STATUS","YXBS");
		bs.setFieldDic("IF_CLAIM","SF");
		bs.setFieldDic("IF_FIXCOSTS","SF");
		bs.setFieldDic("ACTIVITY_TYPE","HDLB");
		bs.setFieldDic("ACTIVITY_STATUS","HDZT");
		bs.setFieldDic("MANAGE_TYPE","CLFS");
    	return bs;
    }
    /**服务活动查询主页面查询方法
   	 * @title: OemSearch
   	 * @date 2014年7月2日09:14:52
   	 */
       public BaseResultSet delSearch(PageManager page, User user, String conditions,String orgId) throws Exception
       {
       	String wheres = conditions;
       	wheres += "AND T.ACTIVITY_ID = TA.ACTIVITY_ID \n"
       			+ "AND T.ACTIVITY_STATUS = "+DicConstant.HDZT_02+" \n"
       			+ "AND T.STATUS="+DicConstant.YXBS_01+"\n"
       			+ "AND TA.ORG_ID="+orgId+"\n";
           page.setFilter(wheres);
       	//定义返回结果集
       	BaseResultSet bs = null;
       	StringBuffer sql= new StringBuffer();
       	sql.append("SELECT T.ACTIVITY_ID,\n" );
       	sql.append("       T.ACTIVITY_CODE,\n" );
       	sql.append("       T.ACTIVITY_NAME,\n" );
       	sql.append("       T.ACTIVITY_TYPE,\n" );
       	sql.append("       T.ACTIVITY_STATUS,\n" );
       	sql.append("       T.MANAGE_TYPE,\n" );
    	sql.append("       T.BEGIN_MILEAGE,\n" );
    	sql.append("       T.END_MILEAGE,\n" );
    	sql.append("       T.IN_ACCOUNT_TYPE,\n" );
    	sql.append("       T.IF_PERSON_CHECK,\n" );
       	sql.append("       T.START_DATE,\n" );
       	sql.append("       T.END_DATE,\n" );
       	sql.append("       T.IF_CLAIM,\n" );
       	sql.append("       T.IF_FIXCOSTS,\n" );
       	sql.append("       T.ACTIVITY_COSTS,\n" );
       	sql.append("       T.SOLUTION,\n" );
       	sql.append("       T.REMARKS,\n" );
       	sql.append("       T.COMPANY_ID,\n" );
       	sql.append("       T.ORG_ID,\n" );
       	sql.append("       T.CREATE_USER,\n" );
       	sql.append("       T.CREATE_TIME,\n" );
       	sql.append("       T.UPDATE_USER,\n" );
       	sql.append("       T.UPDATE_TIME\n" );
       	sql.append("FROM SE_BU_ACTIVITY T,\n" );
       	sql.append("      SE_BU_ACTIVITY_AREA TA");

       	//执行方法，不需要传递conn参数
       	bs = factory.select(sql.toString(), page);
   		bs.setFieldDateFormat("START_DATE", "yyyy-MM-dd");
   		bs.setFieldDateFormat("END_DATE", "yyyy-MM-dd");
   		bs.setFieldDic("STATUS","YXBS");
   		bs.setFieldDic("IF_CLAIM","SF");
   		bs.setFieldDic("IF_FIXCOSTS","SF");
   		bs.setFieldDic("ACTIVITY_TYPE","HDLB");
   		bs.setFieldDic("ACTIVITY_STATUS","HDZT");
   		bs.setFieldDic("MANAGE_TYPE","CLFS");
       	return bs;
       }
    /**
   	 * @title: search
   	 * @date 2014年7月2日09:14:52
   	 */
       public BaseResultSet searchService(PageManager page, User user, String conditions,String activityId) throws Exception
       {
       	String wheres = conditions;
       	wheres += "AND T.STATUS="+DicConstant.YXBS_01+"\n"
       			+ "AND ACTIVITY_ID="+activityId+"\n"
       			+ "ORDER BY ACTIVITY_ID";
           page.setFilter(wheres);
       	//定义返回结果集
       	BaseResultSet bs = null;
       	StringBuffer sql= new StringBuffer();
       	sql.append("SELECT ACTIVITY_ID,\n" );
       	sql.append("       ACTIVITY_CODE,\n" );
       	sql.append("       ACTIVITY_NAME,\n" );
       	sql.append("       ACTIVITY_TYPE,\n" );
       	sql.append("       ACTIVITY_STATUS,\n" );
       	sql.append("       MANAGE_TYPE,\n" );
    	sql.append("       BEGIN_MILEAGE,\n" );
    	sql.append("       END_MILEAGE,\n" );
    	sql.append("       IN_ACCOUNT_TYPE,\n" );
    	sql.append("       IF_PERSON_CHECK,\n" );
       	sql.append("       START_DATE,\n" );
       	sql.append("       END_DATE,\n" );
       	sql.append("       IF_CLAIM,\n" );
       	sql.append("       IF_FIXCOSTS,\n" );
       	sql.append("       ACTIVITY_COSTS,\n" );
       	sql.append("       SOLUTION,\n" );
       	sql.append("       REMARKS,\n" );
       	sql.append("       COMPANY_ID,\n" );
       	sql.append("       ORG_ID,\n" );
       	sql.append("       CREATE_USER,\n" );
       	sql.append("       CREATE_TIME,\n" );
       	sql.append("       UPDATE_USER,\n" );
       	sql.append("       UPDATE_TIME\n" );
       	sql.append("  FROM SE_BU_ACTIVITY T");
       	//执行方法，不需要传递conn参数
       	bs = factory.select(sql.toString(), page);
   		bs.setFieldDateFormat("START_DATE", "yyyy-MM-dd");
   		bs.setFieldDateFormat("END_DATE", "yyyy-MM-dd");
   		bs.setFieldDic("STATUS","YXBS");
   		bs.setFieldDic("IF_CLAIM","SF");
   		bs.setFieldDic("IF_FIXCOSTS","SF");
   		bs.setFieldDic("ACTIVITY_TYPE","HDLB");
   		bs.setFieldDic("ACTIVITY_STATUS","HDZT");
   		bs.setFieldDic("MANAGE_TYPE","CLFS");
   		bs.setFieldDic("IN_ACCOUNT_TYPE","RZLX");
   		bs.setFieldDic("IF_PERSON_CHECK","SF");
       	return bs;
       }
    /**
  	 * @title: searchServiceVehage
  	 * @date 2014年7月3日09:14:52
  	 */
      public BaseResultSet searchServiceVehage(PageManager page, User user, String conditions,String activityId) throws Exception
      {
      	String wheres = conditions;
      	wheres += "AND A.ACTIVITY_ID = T.ACTIVITY_ID AND A.ACTIVITY_ID="+activityId+" "
      		   +"ORDER BY T.VEHAGE_TYPE ";
      	page.setFilter(wheres);
      	//定义返回结果集
      	BaseResultSet bs = null;
      	StringBuffer sql= new StringBuffer();
      	sql.append("SELECT T.RELATION_ID,\n" );
      	sql.append("       T.ACTIVITY_ID,\n" );
      	sql.append("       T.ACTIVITY_CODE,\n" );
      	sql.append("       T.ACTIVITY_NAME,\n" );
      	sql.append("       T.VEHAGE_TYPE,\n" );
      	sql.append("       T.START_DATE,\n" );
      	sql.append("       T.END_DATE,\n" );
      	sql.append("       T.CREATE_USER,\n" );
      	sql.append("       T.CREATE_TIME,\n" );
      	sql.append("       T.UPDATE_USER,\n" );
      	sql.append("       T.UPDATE_TIME\n" );
      	sql.append("  FROM SE_BU_ACTIVITY_VEHAGE T,");
      	sql.append("         SE_BU_ACTIVITY A");
      	//执行方法，不需要传递conn参数
      	bs = factory.select(sql.toString(), page);
      	bs.setFieldDic("VEHAGE_TYPE","RQLX");
      	bs.setFieldDateFormat("START_DATE", "yyyy-MM-dd");
      	bs.setFieldDateFormat("END_DATE", "yyyy-MM-dd");
      	return bs;
      }
      /**
  	 * @title: searchServiceModel
  	 * 查询服务活动车型
  	 * @date 2014年7月2日09:14:52
  	 */
      public BaseResultSet searchServiceModel(PageManager page, User user, String conditions,String activityId) throws Exception
      {
      	String wheres = conditions;
      	wheres += " AND T.ACTIVITY_ID = B.ACTIVITY_ID AND B.ACTIVITY_ID = "+activityId+" "
      		   +"ORDER BY T.RELATION_ID ";
          page.setFilter(wheres);
      	//定义返回结果集
      	BaseResultSet bs = null;
      	StringBuffer sql= new StringBuffer();
      	sql.append("SELECT T.RELATION_ID,\n" );
      	sql.append("       T.ACTIVITY_ID,\n" );
      	sql.append("       T.ACTIVITY_CODE,\n" );
      	sql.append("       T.ACTIVITY_NAME,\n" );
      	sql.append("       T.MODELS_ID,\n" );
      	sql.append("       T.MODELS_CODE,\n" );
      	sql.append("       T.MODELS_NAME,\n" );
      	sql.append("       T.CREATE_USER,\n" );
      	sql.append("       T.CREATE_TIME,\n" );
      	sql.append("       T.UPDATE_USER,\n" );
      	sql.append("       T.UPDATE_TIME\n" );
      	sql.append("  FROM SE_BU_ACTIVITY_MODELS T,\n" );
      	sql.append("       SE_BU_ACTIVITY B\n" );
      	//执行方法，不需要传递conn参数
      	bs = factory.select(sql.toString(), page);
      	return bs;
      }
	  /**
	 * @title: searchServiceTime
	 * 查询服务活动工时
	 * @date 2014年7月2日09:14:52
	 */
	 public BaseResultSet searchServiceTime(PageManager page, User user, String conditions,String activityId) throws Exception
	 {
	 	String wheres = conditions;
	 	wheres += " AND T.ACTIVITY_ID = B.ACTIVITY_ID AND B.ACTIVITY_ID = "+activityId+" "
	 		  +"ORDER BY T.RELATION_ID ";
	     page.setFilter(wheres);
	 	//定义返回结果集
	 	BaseResultSet bs = null;
	 	StringBuffer sql= new StringBuffer();
	 	sql.append("SELECT T.RELATION_ID,\n" );
	 	sql.append("       T.ACTIVITY_ID,\n" );
	 	sql.append("       T.ACTIVITY_CODE,\n" );
	 	sql.append("       T.ACTIVITY_NAME,\n" );
	 	sql.append("       T.TASK_TIME_ID,\n" );
	 	sql.append("       T.TASK_TIME_CODE,\n" );
	 	sql.append("       T.TASK_TIME_NAME,\n" );
	 	sql.append("       T.CREATE_USER,\n" );
	 	sql.append("       T.CREATE_TIME,\n" );
	 	sql.append("       T.UPDATE_USER,\n" );
	 	sql.append("       T.UPDATE_TIME\n" );
	 	sql.append("  FROM SE_BU_ACTIVITY_TASK_TIME T, SE_BU_ACTIVITY B\n" );
	 	//执行方法，不需要传递conn参数
	 	bs = factory.select(sql.toString(), page);
	 	return bs;
	 }
 /**
 * @title: searchServiceParts
 * 查询服务活动配件
 * @date 2014年7月2日09:14:52
 */
 public BaseResultSet searchServiceParts(PageManager page, User user, String 

 		conditions,String activityId) throws Exception
 		      {
 		      	String wheres = conditions;
 		      	wheres += " AND T.ACTIVITY_ID = B.ACTIVITY_ID AND B.ACTIVITY_ID = "+activityId+" "
	      		  +"ORDER BY T.RELATION_ID ";
	          page.setFilter(wheres);
	      	//定义返回结果集
	      	BaseResultSet bs = null;
	      	StringBuffer sql= new StringBuffer();
	      	sql.append("SELECT T.RELATION_ID,\n" );
	      	sql.append("       T.ACTIVITY_ID,\n" );
	      	sql.append("       T.ACTIVITY_CODE,\n" );
	      	sql.append("       T.ACTIVITY_NAME,\n" );
	      	sql.append("       T.PART_ID,\n" );
	      	sql.append("       T.PART_CODE,\n" );
	      	sql.append("       T.PART_NAME,\n" );
	      	sql.append("       T.QUANTITY,\n" );
	      	sql.append("       T.CREATE_USER,\n" );
	      	sql.append("       T.CREATE_TIME,\n" );
	      	sql.append("       T.UPDATE_USER,\n" );
	      	sql.append("       T.UPDATE_TIME\n" );
	      	sql.append("  FROM SE_BU_ACTIVITY_PART T,\n");
	    	sql.append("       SE_BU_ACTIVITY B\n" );
	      	//执行方法，不需要传递conn参数
	      	bs = factory.select(sql.toString(), page);
	      	return bs;
	      }
 /**
 * @title: searchServiceProjects
 * 查询服务活动项目
 */
 public BaseResultSet searchServiceProjects(PageManager page, User user, String 

conditions,String activityId) throws Exception
      {
      	String wheres = conditions;
      	wheres += " AND T.ACTIVITY_ID = B.ACTIVITY_ID AND B.ACTIVITY_ID = "+activityId+" "
      		   +"ORDER BY T.RELATION_ID ";
          page.setFilter(wheres);
      	//定义返回结果集
      	BaseResultSet bs = null;
      	StringBuffer sql= new StringBuffer();
      	sql.append("SELECT T.RELATION_ID,\n" );
      	sql.append("       T.ACTIVITY_ID,\n" );
      	sql.append("       T.ACTIVITY_CODE,\n" );
      	sql.append("       T.ACTIVITY_NAME,\n" );
      	sql.append("       T.PROJECT_ID,\n" );
      	sql.append("       T.PROJECT_CODE,\n" );
      	sql.append("       T.PROJECT_NAME,\n" );
      	sql.append("       T.AMOUNT,\n" );
      	sql.append("       T.CREATE_USER,\n" );
      	sql.append("       T.CREATE_TIME,\n" );
      	sql.append("       T.UPDATE_USER,\n" );
      	sql.append("       T.UPDATE_TIME\n" );
      	sql.append("  FROM SE_BU_ACTIVITY_PROJECT T,");
    	sql.append("       SE_BU_ACTIVITY B\n" );
      	//执行方法，不需要传递conn参数
      	bs = factory.select(sql.toString(), page);
      	return bs;
      }
 /**
	 * @title: searchServiceVin
	 * 查询服务活动VIN
	 */
 public BaseResultSet searchServiceVin(PageManager page, User user, String conditions,String activityId) throws Exception{
   	String wheres = conditions;
   	wheres += " AND  T.ACTIVITY_ID = SA.ACTIVITY_ID AND SA.ACTIVITY_ID = "+activityId+" "
   			+ " AND T.VEHICLE_ID = MV.VEHICLE_ID(+) "
   			+ " AND T.IF_REGIST_PRODUCE IS NULL "
   			+ " AND MV.MODELS_ID = MM.MODELS_ID(+) "
   		    + " ORDER BY T.RELATION_ID ";
       page.setFilter(wheres);
   	//定义返回结果集
   	BaseResultSet bs = null;
   	StringBuffer sql= new StringBuffer();
   	sql.append("SELECT T.RELATION_ID,\n" );
   	sql.append("       T.ACTIVITY_ID,\n" );
   	sql.append("       T.ACTIVITY_CODE,\n" );
   	sql.append("       T.ACTIVITY_NAME,\n" );
   	sql.append("       T.VEHICLE_ID,\n" );
   	sql.append("       T.CLAIM_USER,\n" );
   	sql.append("       T.APPLY_DATE,\n" );
   	sql.append("       T.ORG_ID,\n" );
   	sql.append("       T.ORG_CODE,\n" );
   	sql.append("       T.ORG_NAME,\n" );
   	sql.append("       T.OFFICE_ID,\n" );
   	sql.append("       T.REGIST_STATUS,\n" );
   	sql.append("       T.VIN,\n" );
   	sql.append("       T.CREATE_USER,\n" );
   	sql.append("       T.CREATE_TIME,\n" );
   	sql.append("       T.UPDATE_USER,\n" );
   	sql.append("       T.UPDATE_TIME,\n" );
   	sql.append("       T.STATUS,\n" );
   	sql.append("       MV.ENGINE_NO,\n" );
   	sql.append("       MV.BUY_DATE,\n" );
   	sql.append("       MV.FACTORY_DATE,\n" );
   	sql.append("       MM.MODELS_NAME\n" );
   	sql.append("  FROM SE_BU_ACTIVITY_VEHICLE T, SE_BU_ACTIVITY SA, MAIN_VEHICLE MV,MAIN_MODELS MM");
   	//执行方法，不需要传递conn参数
   	bs = factory.select(sql.toString(), page);
   	bs.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
   	bs.setFieldDateFormat("FACTORY_DATE", "yyyy-MM-dd");
   	return bs;
   }
 /**
	 * @title: searchServiceTime
	 * 查询服务活动工时
	 * @date 2014年7月2日09:14:52
	 */
    public BaseResultSet searchDealers(PageManager page, User user, String conditions,String activityId) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND T.ACTIVITY_ID = B.ACTIVITY_ID AND B.ACTIVITY_ID = "+activityId+" "
    		   +"ORDER BY T.AREA_ID ";
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.AREA_ID,\n" );
    	sql.append("       T.ACTIVITY_ID,\n" );
    	sql.append("       T.AREA_NAME,\n" );
    	sql.append("       T.AREA_CODE,\n" );
    	sql.append("       T.ORG_ID,\n" );
    	sql.append("       T.ORG_CODE,\n" );
    	sql.append("       T.ORG_NAME,\n" );
    	sql.append("       T.CREATE_USER,\n" );
    	sql.append("       T.CREATE_TIME,\n" );
    	sql.append("       T.UPDATE_USER,\n" );
    	sql.append("       T.UPDATE_TIME\n" );
    	sql.append("  FROM SE_BU_ACTIVITY_AREA T, SE_BU_ACTIVITY B");
    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
    /**
   	 * @title: searchModel
   	 * 查询所有渠道商
   	 * @date 2014年7月3日09:14:52
   	 */
       public BaseResultSet searchOrgDealrs(PageManager page, User user, String conditions,String activityId) throws Exception
       {
       	String wheres = conditions;
       	wheres += "AND TOG.LEVEL_CODE = "+DicConstant.JGJB_05+"\n"
       			//+ "AND TOG.BUS_TYPE = "+DicConstant.YWLX_02+"\n"
       			+ "AND TOG.ORG_TYPE = "+DicConstant.ZZLB_11+"\n"
       			+ "AND NOT EXISTS (SELECT B.ORG_ID FROM SE_BU_ACTIVITY_AREA B  WHERE TOG.ORG_ID = B.ORG_ID  AND B.ACTIVITY_ID = "+activityId+") "
       	       +"ORDER BY TOG.ORG_ID ";
       	page.setFilter(wheres);
       	//定义返回结果集
       	BaseResultSet bs = null;
       	StringBuffer sql= new StringBuffer();
       	sql.append("SELECT TOG.CODE,\n" );
       	sql.append("       TOG.ONAME,\n" );
       	sql.append("       TOG.DIVISION_CODE,\n" );
       	sql.append("       TOG.NAME_EN,\n" );
       	sql.append("       TOG.PID,\n" );
       	sql.append("       TOG.SNAME,\n" );
       	sql.append("       TOG.LEVEL_CODE,\n" );
       	sql.append("       TOG.COMPANY_ID,\n" );
       	sql.append("       TOG.ORG_ID,\n" );
       	sql.append("       TOG.CREATE_USER,\n" );
       	sql.append("       TOG.CREATE_TIME,\n" );
       	sql.append("       TOG.UPDATE_USER,\n" );
       	sql.append("       TOG.UPDATE_TIME,\n" );
       	sql.append("       TOG.STATUS,\n" );
       	sql.append("       TOG.ORG_TYPE,\n" );
       	sql.append("       TOG.BUS_TYPE,\n" );
       	sql.append("       TOG.OEM_COMPANY_ID\n" );
       	sql.append("  FROM TM_ORG TOG\n" );
       	//执行方法，不需要传递conn参数
       	bs = factory.select(sql.toString(), page);
       	return bs;
       }
     //新增渠道商
   	public boolean insertDealers(String mxids,String CreateUser,String activityId,String activityCode,String activityName)
   	            throws Exception
   	    {
   		String ids=mxids;
   		StringBuffer sql= new StringBuffer();
   		sql.append("INSERT INTO SE_BU_ACTIVITY_AREA\n" );
   		sql.append("   (AREA_ID,\n" );
   		sql.append("    ORG_CODE,\n" );
   		sql.append("    ORG_NAME,\n" );
   		sql.append("    ORG_ID,\n" );
   		sql.append("    ACTIVITY_ID,\n" );
   		sql.append("    AREA_CODE,\n" );
   		sql.append("    AREA_NAME,\n" );
   		sql.append("    CREATE_USER,\n" );
   		sql.append("    CREATE_TIME)\n" );
        sql.append("  SELECT F_GETID(),\n" );
        sql.append("         B.CODE,\n" );
        sql.append("         B.ONAME,\n" );
        sql.append("         B.ORG_ID,\n" );
        sql.append("         '"+activityId+"',\n" );
        sql.append("         '"+activityCode+"',\n" );
        sql.append("         '"+activityName+"',\n" );
        sql.append("         '"+CreateUser+"',\n" );
        sql.append("         SYSDATE\n" );
        sql.append("    FROM TM_ORG B\n" );
        sql.append("   WHERE B.ORG_ID IN ("+ids+")");
   		return factory.update(sql.toString(),null);
   	    }
    /**
     * @title: deleteDealer
     * @return
     * @throws Exception    设定文件 
     * @return boolean    返回类型 
     * @date 2014年7月3日11:17:22
     */
    public boolean deleteDealer(String mxids) throws Exception
    {
 	   StringBuffer sql = new StringBuffer();
 	   sql.append(" DELETE SE_BU_ACTIVITY_AREA A WHERE A.AREA_ID IN ("+mxids+")   \n");
 	   return factory.delete(sql.toString(), null);
    }
    //发布
    public boolean doPublish(String activityId) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" UPDATE SE_BU_ACTIVITY T SET T.ACTIVITY_STATUS ="+DicConstant.HDZT_02+" WHERE T.ACTIVITY_ID="+activityId+"\n");
    	return factory.update(sql.toString(), null);
    }
    //关闭
    public boolean doClose(String activityId) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" UPDATE SE_BU_ACTIVITY T SET T.ACTIVITY_STATUS ="+DicConstant.HDZT_04+" WHERE T.ACTIVITY_ID="+activityId+"\n");
    	return factory.update(sql.toString(), null);
    }
    //取消
    public boolean doCancel(String activityId) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" UPDATE SE_BU_ACTIVITY T SET T.ACTIVITY_STATUS ="+DicConstant.HDZT_03+" WHERE T.ACTIVITY_ID="+activityId+"\n");
    	return factory.update(sql.toString(), null);
    }
	public boolean doRegister(SeBuActivityVehicleVO vo)throws Exception {
		return factory.insert(vo);
	 }
	public boolean registerUpdate(SeBuActivityVehicleVO vo)throws Exception {
		return factory.update(vo);
	}
	
	//查询VIN是否存在
	public QuerySet queryVin(String vin) throws SQLException {
		QuerySet qs = null;
		StringBuffer sql= new StringBuffer();
		if(vin.length()>8){
			sql.append("SELECT T.SVIN,T.VIN,T.MODELS_CODE,T.VEHICLE_ID,T.MILEAGE,TO_CHAR(T.BUY_DATE,'YYYY-MM-DD HH24:MI:SS') BUY_DATE,TO_CHAR(T.FACTORY_DATE,'YYYY-MM-DD HH24:MI:SS') FACTORY_DATE  FROM MAIN_VEHICLE T WHERE T.VIN = '"+vin+"'");
        }else{
        	sql.append("SELECT T.SVIN,T.VIN,T.MODELS_CODE,T.VEHICLE_ID,T.MILEAGE,TO_CHAR(T.BUY_DATE,'YYYY-MM-DD HH24:MI:SS') BUY_DATE,TO_CHAR(T.FACTORY_DATE,'YYYY-MM-DD HH24:MI:SS') FACTORY_DATE  FROM MAIN_VEHICLE T WHERE T.SVIN = '"+vin+"'");
        }
		qs = factory.select(null,sql.toString());
		return qs;
	}
	//查询VIN是否在服务活动车辆表中已维护
	public QuerySet queryVehicleVin(String vehicleVin) throws SQLException {
		QuerySet qs = null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT DISTINCT T.VIN,T.VEHICLE_ID,T.REGIST_STATUS FROM SE_BU_ACTIVITY_VEHICLE T WHERE T.VIN = '"+vehicleVin+"' AND T.IF_REGIST_PRODUCE IS NULL");
		qs = factory.select(null,sql.toString());
		return qs;
	}
	//查询VIN是否在服务活动车辆表中已维护
	public QuerySet repeatCheck(String Vin,String activityId) throws SQLException {
		QuerySet qs = null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.RELATION_ID,T.REGIST_STATUS FROM SE_BU_ACTIVITY_VEHICLE T WHERE T.VIN ='"+Vin+"' AND T.ACTIVITY_ID ="+activityId+"");
		qs = factory.select(null,sql.toString());
		return qs;
	}
	//查询服务活动是否已经维护了发布范围。
	public QuerySet queryScope(String activityId) throws SQLException {
		QuerySet qs = null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT 1 FROM SE_BU_ACTIVITY T,SE_BU_ACTIVITY_AREA SA WHERE T.ACTIVITY_ID = SA.ACTIVITY_ID AND T.ACTIVITY_ID = "+activityId+"");
		qs = factory.select(null,sql.toString());
		return qs;
	}
    //新增渠道商
   	public boolean insertAllDealers(String activityId,String CreateUser) throws Exception
   	    {
   		StringBuffer sql= new StringBuffer();
   		sql.append("INSERT INTO SE_BU_ACTIVITY_AREA\n" );
   		sql.append(" 	    (AREA_ID,\n" );
   		sql.append("         ORG_CODE,\n" );
   		sql.append("         ORG_NAME,\n" );
   		sql.append("         ORG_ID,\n" );
   		sql.append("         ACTIVITY_ID,\n" );
   		sql.append("         AREA_CODE,\n" );
   		sql.append("         AREA_NAME,\n" );
   		sql.append("         CREATE_USER,\n" );
   		sql.append("         CREATE_TIME)\n" );
   		sql.append("        SELECT F_GETID(),\n" );
   		sql.append("               B.CODE,\n" );
   		sql.append("               B.ONAME,\n" );
   		sql.append("               B.ORG_ID,\n" );
   		sql.append("               '"+activityId+"',\n" );
   		sql.append("               (SELECT A.ACTIVITY_CODE FROM SE_BU_ACTIVITY A WHERE A.ACTIVITY_ID = "+activityId+"),\n" );
   		sql.append("               (SELECT B.ACTIVITY_NAME FROM SE_BU_ACTIVITY B WHERE B.ACTIVITY_ID = "+activityId+"),\n" );
   		sql.append("               '"+CreateUser+"',\n" );
   		sql.append("               SYSDATE\n" );
   		sql.append("          FROM TM_ORG B");
        sql.append("   WHERE B.ORG_ID IN (SELECT T.ORG_ID FROM TM_ORG T WHERE T.ORG_TYPE = "+DicConstant.ZZLB_11+" AND T.STATUS = "+DicConstant.YXBS_01+" AND T.LEVEL_CODE = "+DicConstant.JGJB_05+")");
   		return factory.update(sql.toString(),null);
    }
    public QuerySet download(String conditions) throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.RELATION_ID,\n" );
    	sql.append("       T.ACTIVITY_ID,\n" );
    	sql.append("       T.ACTIVITY_CODE,\n" );
    	sql.append("       T.ACTIVITY_NAME,\n" );
    	sql.append("       T.VEHICLE_ID,\n" );
    	sql.append("       (SELECT T1.DIC_VALUE FROM DIC_TREE T1 WHERE T1.ID =  T.CLAIM_USER) CLAIM_USER,\n" );
    	sql.append("       T.APPLY_DATE,\n" );
    	sql.append("       T.ORG_ID,\n" );
    	sql.append("       T.ORG_CODE,\n" );
    	sql.append("       T.ORG_NAME,\n" );
    	sql.append("       T.CREATE_USER,\n" );
    	sql.append("       T.CREATE_TIME,\n" );
    	sql.append("       T.UPDATE_USER,\n" );
    	sql.append("       T.UPDATE_TIME,\n" );
    	sql.append("       T.VIN,\n" );
    	sql.append("       TG.CODE,\n" );
    	sql.append("       TG.ONAME,\n" );
    	sql.append("       SA.SOLUTION\n" );
    	sql.append("  FROM SE_BU_ACTIVITY_VEHICLE T, SE_BU_ACTIVITY SA, TM_ORG TG\n" );
    	sql.append(" WHERE "+conditions+" AND T.ACTIVITY_ID = SA.ACTIVITY_ID\n" );
    	sql.append("   AND TG.ORG_ID = T.ORG_ID\n" );
    	sql.append("   AND T.STATUS = 100201");
    	sql.append("   ORDER BY TG.CODE,T.ACTIVITY_ID");
    	return factory.select(null, sql.toString());
    }
}