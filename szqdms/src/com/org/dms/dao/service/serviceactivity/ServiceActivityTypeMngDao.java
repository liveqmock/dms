package com.org.dms.dao.service.serviceactivity;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.service.SeBuActivityPartVO;
import com.org.dms.vo.service.SeBuActivityVO;
import com.org.dms.vo.service.SeBuActivityVehageVO;
import com.org.dms.vo.service.SeBuActivityVehicleVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.SequenceUtil;
import com.org.framework.common.User;
import com.org.framework.common.dataset.DataObjImpl;
import com.org.mvc.context.ActionContext;
/**
 * @Description:服务活动类别管理
 * @Date: 2014-7-1
 * @author sunxuedong
 * @version 1.0
 * @remark 
 */
public class ServiceActivityTypeMngDao extends BaseDAO
{
    //定义instance
    public  static final ServiceActivityTypeMngDao getInstance(ActionContext atx)
    {
        ServiceActivityTypeMngDao dao = new ServiceActivityTypeMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
    //新增
	public boolean insertServiceActivity(SeBuActivityVO vo)
            throws Exception
    {
		String activityId=SequenceUtil.getCommonSerivalNumber(factory);
		vo.setActivityId(activityId);
    	return factory.insert(vo);
    }
	public boolean insertVehage(SeBuActivityVehageVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }
	
    /**
     * 新增配件
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean insertParts(SeBuActivityPartVO vo)
            throws Exception {
        return factory.insert(vo);
    }
    /**
     * 新增配件
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean insertVIN(SeBuActivityVehicleVO vo)
    		throws Exception {
    	return factory.insert(vo);
    }
	//新增车型
	public boolean insertModels(String modelsId,String CreateUser,String activityId,String activityCode,String activityName)
	            throws Exception
	    {
		String ids=modelsId;
		StringBuffer sql= new StringBuffer();
		sql.append("INSERT INTO SE_BU_ACTIVITY_MODELS\n" );
		sql.append("  (RELATION_ID,\n" );
		sql.append("   MODELS_CODE,\n" );
		sql.append("   MODELS_NAME,\n" );
		sql.append("   MODELS_ID,\n" );
		sql.append("    ACTIVITY_ID,\n" );
		sql.append("    ACTIVITY_CODE,\n" );
		sql.append("    ACTIVITY_NAME,\n" );
		sql.append("    CREATE_USER,\n" );
		sql.append("    CREATE_TIME)\n" );
		sql.append("  SELECT F_GETID(),\n" );
		sql.append("         B.MODELS_CODE,\n" );
		sql.append("         B.MODELS_NAME,\n" );
		sql.append("         B.MODELS_ID,\n" );
		sql.append("         '"+activityId+"',\n" );
		sql.append("         '"+activityCode+"',\n" );
		sql.append("         '"+activityName+"',\n" );
		sql.append("         '"+CreateUser+"',\n" );
		sql.append("         SYSDATE\n" );
		sql.append("    FROM MAIN_MODELS B\n" );
		sql.append("   WHERE B.MODELS_ID IN ("+ids+")");
		return factory.update(sql.toString(),null);
	    }
	//新增工时
		public boolean insertTimes(String taskTimeId,String CreateUser,String activityId,String activityCode,String activityName)
		            throws Exception
		    {
		String ids=taskTimeId;
		StringBuffer sql= new StringBuffer();
		sql.append("INSERT INTO SE_BU_ACTIVITY_TASK_TIME\n" );
		sql.append("  (RELATION_ID,\n" );
		sql.append("   TASK_TIME_CODE,\n" );
		sql.append("   TASK_TIME_NAME,\n" );
		sql.append("   TASK_TIME_ID,\n" );
		sql.append("   ACTIVITY_ID,\n" );
		sql.append("   ACTIVITY_CODE,\n" );
		sql.append("   ACTIVITY_NAME,\n" );
		sql.append("   CREATE_USER,\n" );
		sql.append("   CREATE_TIME)\n" );
		sql.append("  SELECT F_GETID(),\n" );
		sql.append("         B.TIME_CODE,\n" );
		sql.append("         B.TIME_NAME,\n" );
		sql.append("         B.AMOUNT_ID,\n" );
		sql.append("         '"+activityId+"',\n" );
		sql.append("         '"+activityCode+"',\n" );
		sql.append("         '"+activityName+"',\n" );
		sql.append("         '"+CreateUser+"',\n" );
		sql.append("         SYSDATE\n" );
		sql.append("    FROM SE_BA_TASK_AMOUNT B\n" );
		sql.append("   WHERE B.AMOUNT_ID IN  ("+ids+")");
		return factory.update(sql.toString(),null);
	    }
	//修改
	public boolean updateServiceActivity(SeBuActivityVO tempVO) throws Exception
    {
    	return factory.update(tempVO);
    }

	/**
	 * @title: search
	 * @date 2014年7月2日09:14:52
	 */
    public BaseResultSet search(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND T.STATUS="+DicConstant.YXBS_01+""
    			+ " AND T.ACTIVITY_STATUS IN ("+DicConstant.HDZT_01+","+DicConstant.HDZT_03+","+DicConstant.HDZT_02+")"
    			+ " ORDER BY  T.ACTIVITY_ID";
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
    	sql.append("       STATUS\n" );
    	sql.append("  FROM SE_BU_ACTIVITY T");
    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
		bs.setFieldDateFormat("START_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("END_DATE", "yyyy-MM-dd");
		bs.setFieldDic("STATUS","YXBS");
		bs.setFieldDic("IF_CLAIM","SF");
		bs.setFieldDic("IN_ACCOUNT_TYPE","RZLX");
		bs.setFieldDic("IF_PERSON_CHECK","SF");
		bs.setFieldDic("IF_FIXCOSTS","SF");
		bs.setFieldDic("ACTIVITY_TYPE","HDLB");
		bs.setFieldDic("ACTIVITY_STATUS","HDZT");
		bs.setFieldDic("MANAGE_TYPE","CLFS");
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
  	 * @title: searchServiceParts
  	 * 查询服务活动配件
  	 * @date 2014年7月2日09:14:52
  	 */
    public BaseResultSet searchServiceParts(PageManager page, User user, String 

    		conditions,String activityId) throws Exception
    		      {
    		      	String wheres = conditions;
    		      	wheres += " AND T.ACTIVITY_ID = B.ACTIVITY_ID\n"
    		      			+ " AND B.ACTIVITY_ID = "+activityId+"\n"
    		      			+ " AND T.PART_ID = PT.PART_ID\n"
    		      		  +"ORDER BY T.RELATION_ID ";
    		          page.setFilter(wheres);
    		      	//定义返回结果集
    		      	BaseResultSet bs = null;
    		      	StringBuffer sql= new StringBuffer();
    		      	sql.append("SELECT T.RELATION_ID,\n" );
    		      	sql.append("       T.ACTIVITY_ID,\n" );
    		      	sql.append("       T.ACTIVITY_CODE,\n" );
    		      	sql.append("       T.ACTIVITY_NAME,\n" );
    		      	sql.append("       (T.QUANTITY * PT.SE_CLPRICE) AMOUNT,\n" );
    		      	sql.append("       T.PART_ID,\n" );
    		      	sql.append("       T.PART_CODE,\n" );
    		      	sql.append("       T.PART_NAME,\n" );
    		      	sql.append("       T.QUANTITY,\n" );
    		      	sql.append("       T.CREATE_USER,\n" );
    		      	sql.append("       T.CREATE_TIME,\n" );
    		      	sql.append("       T.UPDATE_USER,\n" );
    		      	sql.append("       T.UPDATE_TIME\n" );
    		      	sql.append("  FROM SE_BU_ACTIVITY_PART T,\n");
    		    	sql.append("       SE_BU_ACTIVITY B,\n" );
    		    	sql.append("        PT_BA_INFO PT\n" );
    		      	//执行方法，不需要传递conn参数
    		      	bs = factory.select(sql.toString(), page);
    		      	return bs;
    		      }
    /**
  	 * @title: searchServiceProjects
  	 * 查询服务活动项目
  	 */
    public BaseResultSet searchServiceVin(PageManager page, User user, String conditions,String activityId) throws Exception{
      	String wheres = conditions;
      	wheres += " AND  T.ACTIVITY_ID = SA.ACTIVITY_ID AND SA.ACTIVITY_ID = "+activityId+" "
      			+ " AND T.VEHICLE_ID = MV.VEHICLE_ID"
      			+ " AND T.IF_REGIST_PRODUCE IS NULL"
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
      	sql.append("       MV.FACTORY_DATE\n" );
      	sql.append("  FROM SE_BU_ACTIVITY_VEHICLE T, SE_BU_ACTIVITY SA, MAIN_VEHICLE MV");
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
	 * @title: searchModel
	 * 查询所有车型
	 * @date 2014年7月3日09:14:52
	 */
    public BaseResultSet searchModel(PageManager page, User user, String conditions,String activityId) throws Exception
    {
    	String wheres = conditions;
    	wheres += "AND NOT EXISTS (SELECT B.MODELS_ID FROM SE_BU_ACTIVITY_MODELS B  WHERE A.MODELS_ID = B.MODELS_ID  AND B.ACTIVITY_ID = "+activityId+") "
    	       +"ORDER BY A.MODELS_ID ";
    	page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT A.MODELS_ID,\n" );
    	sql.append("       A.MODELS_CODE,\n" );
    	sql.append("       A.MODELS_NAME,\n" );
    	sql.append("       A.MODELS_STATUS,\n" );
    	sql.append("       A.CREATE_USER,\n" );
    	sql.append("       A.CREATE_TIME,\n" );
    	sql.append("       A.UPDATE_USER,\n" );
    	sql.append("       A.UPDATE_TIME\n" );
    	sql.append("  FROM MAIN_MODELS A\n" );
    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
    /**
	 * @title: searchTime
	 * 查询所有工时
	 * @date 2014年7月3日09:14:52
	 */
    public BaseResultSet searchTime1(PageManager page, User user, String conditions,String activityId) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND T.PATTERN_ID = B.PATTERN_ID "
    			+ " AND NOT EXISTS (SELECT C.TASK_TIME_ID FROM se_bu_activity_task_time C  WHERE T.AMOUNT_ID = C.TASK_TIME_ID AND C.ACTIVITY_ID = "+activityId+")"
    			+ " ORDER BY T.AMOUNT_ID ";
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.AMOUNT_ID,\n" );
    	sql.append("       T.COMPANY_ID,\n" );
    	sql.append("       T.CREATE_TIME,\n" );
    	sql.append("       T.CREATE_USER,\n" );
    	sql.append("       T.FAULT_PATTERN_CODE,\n" );
    	sql.append("       T.FAULT_PATTERN_NAME,\n" );
    	sql.append("       T.OEM_COMPANY_ID,\n" );
    	sql.append("       T.ORG_ID,\n" );
    	sql.append("       T.RELATION_ID,\n" );
    	sql.append("       T.SECRET_LEVEL,\n" );
    	sql.append("       T.STATUS,\n" );
    	sql.append("       T.UPDATE_TIME,\n" );
    	sql.append("       T.UPDATE_USER,\n" );
    	sql.append("       T.TIME_CODE,\n" );
    	sql.append("       T.TIME_NAME,\n" );
    	sql.append("       B.PATTERN_ID,\n" );
    	sql.append("       B.FAULT_CODE,\n" );
    	sql.append("       B.FAULT_NAME,\n" );
    	sql.append("       B.POSITION_CODE,\n" );
    	sql.append("       B.POSITION_NAME\n" );
    	sql.append("  FROM SE_BA_FAULT_TASKTIME T, SE_BA_FAULT_PATTERN B");
    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("STATUS","YXBS");
    	return bs;
    }
    public BaseResultSet searchTime(PageManager page, User user, String conditions,String activityId) throws Exception
    {
    	String wheres = conditions;
    	wheres += "AND NOT EXISTS (SELECT B.TASK_TIME_ID FROM se_bu_activity_task_time B  WHERE A.AMOUNT_ID = B.TASK_TIME_ID AND B.ACTIVITY_ID = "+activityId+")"
    		   +"ORDER BY A.AMOUNT_ID ";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT A.AMOUNT_ID,\n" );
    	sql.append("       A.TIME_CODE,\n" );
    	sql.append("       A.TIME_NAME,\n" );
    	sql.append("       A.USER_TYPE,\n" );
    	sql.append("       A.AMOUNT_SET,\n" );
    	sql.append("       A.MODELS_ID,\n" );
    	sql.append("       A.MODELS_CODE,\n" );
    	sql.append("       A.POSITION_ID,\n" );
    	sql.append("       A.POSITION_CODE,\n" );
    	sql.append("       A.CREATE_USER,\n" );
    	sql.append("       A.CREATE_TIME,\n" );
    	sql.append("       A.UPDATE_USER,\n" );
    	sql.append("       A.UPDATE_TIME,\n" );
    	sql.append("       A.STATUS,\n" );
    	sql.append("       A.SECRET_LEVEL,\n" );
    	sql.append("       A.REMARKS\n" );
    	sql.append("  FROM SE_BA_TASK_AMOUNT A");
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
    /**
   	 * @title: searchPart
   	 * 查询所有配件
   	 * @date 2014年7月3日09:14:52
   	 */
       public BaseResultSet searchPart(PageManager page, User user, String conditions,String activityId) throws Exception
       {
       	String wheres = conditions;
       	wheres += " AND NOT EXISTS (SELECT B.PART_ID   FROM SE_BU_ACTIVITY_PART B WHERE T.PART_ID = B.PART_ID AND B.ACTIVITY_ID = "+activityId+") "
       		   +"ORDER BY T.PART_ID ";
       	page.setFilter(wheres);
       	//定义返回结果集
       	BaseResultSet bs = null;
       	StringBuffer sql= new StringBuffer();
       	sql.append("SELECT T.PART_ID,\n" );
       	sql.append("       T.PART_CODE,\n" );
       	sql.append("       T.PART_NAME,\n" );
       	sql.append("       T.PART_NO,\n" );
       	sql.append("       T.UNIT,\n" );
       	sql.append("       T.PART_TYPE,\n" );
       	sql.append("       T.ATTRIBUTE,\n" );
       	sql.append("       T.MIN_PACK,\n" );
       	sql.append("       T.MIN_UNIT,\n" );
       	sql.append("       T.IF_DIRECT,\n" );
       	sql.append("       T.IF_OUT,\n" );
       	sql.append("       T.IF_BOOK,\n" );
       	sql.append("       T.IF_RETURN,\n" );
       	sql.append("       T.IF_ASSEMBLY,\n" );
       	sql.append("       T.F_POSITION_ID BELONG_ASSEMBLY,\n" );
       	sql.append("       T.IF_SCAN,\n" );
       	sql.append("       T.IF_SUPLY,\n" );
       	sql.append("       T.PART_STATUS,\n" );
       	sql.append("       T.IF_STREAM,\n" );
       	sql.append("       T.REMARKS,\n" );
       	sql.append("       T.PCH_PRICE,\n" );
       	sql.append("       T.SE_CLPRICE,\n" );
       	sql.append("       T.ARMY_PRICE,\n" );
       	sql.append("       T.PLAN_PRICE,\n" );
       	sql.append("       T.CREATE_USER,\n" );
       	sql.append("       T.CREATE_TIME,\n" );
       	sql.append("       T.UPDATE_USER,\n" );
       	sql.append("       T.UPDATE_TIME\n" );
       	sql.append("  FROM PT_BA_INFO T");
       	//执行方法，不需要传递conn参数
       	bs = factory.select(sql.toString(), page);
       	return bs;
       }
       /**
      	 * @title: searchVIN
      	 * @date 2014年7月3日09:14:52
      	 */
      public BaseResultSet searchVIN(PageManager page, User user, String conditions,String activityId) throws Exception
      {
      	String wheres = conditions;
      	wheres += "AND NOT EXISTS (SELECT B.VEHICLE_ID FROM SE_BU_ACTIVITY_VEHICLE B WHERE MV.VEHICLE_ID = B.VEHICLE_ID AND B.ACTIVITY_ID = "+activityId+") "
      		   +"ORDER BY MV.VEHICLE_ID ";
      	page.setFilter(wheres);
      	//定义返回结果集
      	BaseResultSet bs = null;
      	StringBuffer sql= new StringBuffer();
      	sql.append("SELECT MV.SVIN,\n" );
      	sql.append("       MV.VEHICLE_ID,\n" );
      	sql.append("       MV.ENGINE_NO,\n" );
      	sql.append("       MV.BUY_DATE,\n" );
      	sql.append("       MV.FACTORY_DATE\n" );
      	sql.append("  FROM  MAIN_VEHICLE MV");
      	//执行方法，不需要传递conn参数
      	bs = factory.select(sql.toString(), page);
     	bs.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
      	bs.setFieldDateFormat("FACTORY_DATE", "yyyy-MM-dd");
      	return bs;
      }
       /**
      	 * @title: searchProjects
      	 * 查询所有配件
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
        * @title: deletePartByMxids
        * @return
        * @throws Exception    设定文件 
        * @return boolean    返回类型 
        * @date 2014年7月3日11:17:22
        */
       public boolean deletePartByMxids(String mxids) throws Exception
       {
       	StringBuffer sql = new StringBuffer();
       	sql.append(" DELETE SE_BU_ACTIVITY_PART A WHERE A.RELATION_ID IN ("+mxids+")   \n");
       	return factory.delete(sql.toString(), null);
       }
       /**
        * @title: deleteVINByMxids
        * @return
        * @throws Exception    设定文件 
        * @return boolean    返回类型 
        * @date 2014年7月3日11:17:22
        */
       public boolean deleteVINByMxids(String mxids) throws Exception
       {
    	   StringBuffer sql = new StringBuffer();
    	   sql.append(" DELETE SE_BU_ACTIVITY_VEHICLE A WHERE A.RELATION_ID IN ("+mxids+")   \n");
    	   return factory.delete(sql.toString(), null);
       }
    /**
     * @title: deleteByActivityId
     * @param activityId
     * @return
     * @throws Exception    设定文件 
     * @return boolean    返回类型 
     * @date 2014年7月3日11:17:22
     */
    public boolean deleteByActivityId(String activityId) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" UPDATE SE_BU_ACTIVITY T SET T.STATUS = "+DicConstant.YXBS_02+" WHERE T.ACTIVITY_ID="+activityId+"\n");
    	return factory.update(sql.toString(), null);
    }
    /**
     * @title: saveCl
     * @param relationId
     * @return
     * @throws Exception    设定文件 
     * @return boolean    返回类型 
     * @date 2014年7月3日11:17:22
     */
    public boolean saveCl(SeBuActivityVehageVO vo) throws Exception
    {
    	return factory.update(vo);
    }
    /**
     * @title: deleteCxByMxids
     * @return
     * @throws Exception    设定文件 
     * @return boolean    返回类型 
     * @date 2014年7月3日11:17:22
     */
    public boolean deleteCxByMxids(String mxids) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" DELETE SE_BU_ACTIVITY_MODELS A WHERE A.RELATION_ID IN ("+mxids+") \n");
    	return factory.delete(sql.toString(), null);
    }
    /**
     * @title: deleteCxByMxids
     * @return
     * @throws Exception    设定文件 
     * @return boolean    返回类型 
     * @date 2014年7月3日11:17:22
     */
    public boolean deleteGsByMxids(String mxids) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" DELETE SE_BU_ACTIVITY_TASK_TIME A WHERE A.RELATION_ID IN ("+mxids+") \n");
    	return factory.delete(sql.toString(), null);
    }
    /**
     * 删除预授权项目
     * @param authorId
     * @return
     * @throws Exception
     */
    public boolean preAuthAttaDelete(String fjid) throws Exception
    {
    	StringBuffer sql= new StringBuffer();
    	sql.append("DELETE FROM FS_FILESTORE T\n" );
    	sql.append("WHERE T.FJID ="+fjid+"");
    	return factory.delete(sql.toString(), null);
    }
    /**
     * 附件查询
     * @param page 分页
     * @param user 用户
     * @return
     * @throws SQLException 
     */
    public BaseResultSet fileSearch(PageManager page,User user,String activityId) throws SQLException{
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.* FROM FS_FILESTORE T WHERE T.YWZJ = "+activityId+"");
    	bs=factory.select(sql.toString(), page);
    	bs.setFieldDateFormat("CJSJ", "yyyy-MM-dd");
    	return bs;
    }
    /**
     * 生成活动代码
     * @param vo
     * @return
     * @throws Exception
     */
    public String getActivityCode()throws Exception
    {
    	QuerySet qs = null;
    	String date = getDateToString();
    	String num = "HD";
    	if(date!=null){
			date = date.replaceAll("-", "");
			num+=date;
		}
    	StringBuffer sql = new StringBuffer();
		sql.append("SELECT max(").append("ACTIVITY_CODE").append(") as HDDM FROM ");
		sql.append( "SE_BU_ACTIVITY").append(" t");
		sql.append(" where t.").append("ACTIVITY_CODE").append(" like '%").append(num).append("%'");
		qs = factory.select(null, sql.toString());
		if(qs.getRowCount()==0){
			 num+="0001";
		}else{
			    DataObjImpl dateObj = (DataObjImpl) qs.getDataObjs().get(0);
			 	String tem  = dateObj.getString("HDDM");

			if(tem==null||"null".equals(tem)){
				num+="0001";	
			}else{
				int sz = Integer.parseInt(tem.substring(tem.length()-4, tem.length()))+1;
				//如果1位数
				if(String.valueOf(sz).length()==1){
					num=tem.substring(0, tem.length()-4)+"000"+String.valueOf(sz);
				}
				//如果2位数
				else if(String.valueOf(sz).length()==2){
					num=tem.substring(0, tem.length()-4)+"00"+String.valueOf(sz);
				}
				//如果3位数
				else if(String.valueOf(sz).length()==3){
					num=tem.substring(0, tem.length()-4)+"0"+String.valueOf(sz);
				}
				//如果4位数
				else{
				num=tem.substring(0, tem.length()-4)+String.valueOf(sz);
				}
			}
		 }
		return num;
    }
    public static String getDateToString(){
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String d1 = formatter.format(date);
		if(null!=d1&&!"".equals(d1))
		return d1;
		else return null;
	}	
    /**
	 * 校验VIN是否已维护在数据库中。
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public QuerySet checkList(User user) throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT TMP.VIN,TMP.ROW_NO\n" );
		sql.append("  FROM SE_BU_ACTIVITY_VEHICLE_TMP TMP WHERE TMP.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
		sql.append(" AND NOT EXISTS (SELECT 1 FROM MAIN_VEHICLE MV WHERE MV.VIN = TMP.VIN)\n" );
		return factory.select(null, sql.toString());
	}
	/**
	 * 校验VIN是否已维护在业务表中（是否重复）。
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public QuerySet checkList2(User user, String activityId) throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT TMP.VIN,TMP.ROW_NO\n" );
		sql.append("  FROM SE_BU_ACTIVITY_VEHICLE_TMP TMP WHERE TMP.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
		sql.append(" AND EXISTS(SELECT 1 FROM SE_BU_ACTIVITY_VEHICLE SV WHERE TMP.VIN = SV.VIN AND SV.ACTIVITY_ID = "+activityId+")");
		return factory.select(null, sql.toString());
	}
	/**
	 * 校验VIN是否在导入文件中重复填写（是否重复）。
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public QuerySet checkList3(User user) throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT COUNT(T.TMP_ID) AS COU, T.VIN\n" );
		sql.append("  FROM SE_BU_ACTIVITY_VEHICLE_TMP T\n" );
		sql.append("   WHERE T.USER_ACCOUNT='"+user.getAccount()+"'\n" );
		sql.append(" GROUP BY T.VIN\n" );
		sql.append("HAVING COUNT(T.TMP_ID) > 1");
		return factory.select(null, sql.toString());
	}
	public BaseResultSet searchImport(PageManager page,User user,String conditions)throws Exception{
	   BaseResultSet bs = null;
	   StringBuffer sql= new StringBuffer();
	    sql.append("SELECT T.TMP_ID,\n" );
	    sql.append("        T.ROW_NO,\n" );
		sql.append("        T.VIN,\n" );
		sql.append("        T.USER_ACCOUNT\n" );
		sql.append("   FROM SE_BU_ACTIVITY_VEHICLE_TMP T\n" );
		sql.append(" WHERE "+conditions+"");
		sql.append(" AND T.USER_ACCOUNT = '"+user.getAccount()+"'");
		sql.append(" ORDER BY T.ROW_NO");
	   bs=factory.select(sql.toString(), page);
	   return bs;
	}
	public QuerySet queryCode(String activityId) throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.ACTIVITY_CODE,T.ACTIVITY_NAME FROM  SE_BU_ACTIVITY T WHERE T.ACTIVITY_ID = '"+activityId+"'" );
		return factory.select(null, sql.toString());
	}
	public QuerySet queryVin(User user) throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.VIN FROM SE_BU_ACTIVITY_VEHICLE_TMP T WHERE T.USER_ACCOUNT ='"+user.getAccount()+"'");
		return factory.select(null, sql.toString());
	}
	public QuerySet queryVehicle(String vin) throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.VEHICLE_ID FROM MAIN_VEHICLE T WHERE T.VIN in ('"+vin+"')");
		return factory.select(null, sql.toString());
	}
	public QuerySet expData(String conditions,User user) throws Exception{
		StringBuffer sql= new StringBuffer();
	    sql.append("SELECT T.TMP_ID,\n" );
	    sql.append("        T.ROW_NO,\n" );
		sql.append("        T.VIN,\n" );
		sql.append("        T.USER_ACCOUNT\n" );
		sql.append("   FROM SE_BU_ACTIVITY_VEHICLE_TMP T\n" );
		sql.append(" WHERE T.ROW_NO IN ("+conditions+")");
		sql.append(" AND T.USER_ACCOUNT = '"+user.getAccount()+"'");
		sql.append(" ORDER BY T.ROW_NO");
		return factory.select(null, sql.toString());
	}
	//新增vin
	public boolean insertDetail(String activityId,String activityCode,String activityName,User user,String rowNo)throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("INSERT INTO SE_BU_ACTIVITY_VEHICLE\n" );
		sql.append("  (RELATION_ID,\n" );
		sql.append("   REGIST_STATUS,\n" );
		sql.append("   VIN,\n" );
		sql.append("   VEHICLE_ID,\n" );
		sql.append("   ACTIVITY_ID,\n" );
		sql.append("   ACTIVITY_CODE,\n" );
		sql.append("   ACTIVITY_NAME,\n" );
		sql.append("   STATUS,\n" );
		sql.append("   CREATE_USER,\n" );
		sql.append("   CREATE_TIME)\n" );
		sql.append("  SELECT F_GETID(),\n" );
		sql.append("         "+DicConstant.CLDJZT_01+",\n" );
		sql.append("         B.VIN,\n" );
		sql.append("         TV.VEHICLE_ID,\n" );
		sql.append("         '"+activityId+"',\n" );
		sql.append("         '"+activityCode+"',\n" );
		sql.append("         '"+activityName+"',\n" );
		sql.append("         "+DicConstant.YXBS_01+",\n" );
		sql.append("         '"+user.getAccount()+"',\n" );
		sql.append("         SYSDATE\n" );
		sql.append("    FROM SE_BU_ACTIVITY_VEHICLE_TMP B,MAIN_VEHICLE TV \n" );
		sql.append("   WHERE B.USER_ACCOUNT = '"+user.getAccount()+"' "+rowNo+" ");
		sql.append("   AND B.VIN = TV.SVIN");
		return factory.update(sql.toString(),null);
	    }
}