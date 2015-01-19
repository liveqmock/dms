package com.org.dms.dao.service.common;

import java.sql.SQLException;

import com.org.frameImpl.Constant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class ClaimDetailInfoDao extends BaseDAO{
	 //定义instance
    public  static final ClaimDetailInfoDao getInstance(ActionContext atx)
    {
    	ClaimDetailInfoDao dao = new ClaimDetailInfoDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * 查询索赔单信息
     * @param page
     * @param user
     * @param claimId
     * @return
     * @throws Exception
     */
    public BaseResultSet searchClaimInfo(PageManager page,String claimId)throws Exception{
    	BaseResultSet bs=null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.CLAIM_ID, --索赔单ID\n" );
    	sql.append("       T.CLAIM_NO, --索赔单号\n" );
    	sql.append("       O.WORK_NO,--工单单号\n" );
    	sql.append("       O.WORK_ID,--工单ID\n" );
    	sql.append("       T.VEHICLE_ID, --车辆ID\n" );
    	sql.append("       T.VIN, --VIN\n" );
    	sql.append("       T.CLAIM_TYPE, --索赔类型\n" );
    	sql.append("       T.CLAIM_STATUS, --索赔状态\n" );
    	sql.append("       T.IF_PRE_AUTHOR, --是否预授权\n" );
    	sql.append("       T.PRE_AUTHOR_ID, --预授权主键\n" );
    	sql.append("       T.APPLY_USER_TYPE, --报修人类型\n" );
    	sql.append("       T.APPLY_ADDRES, --报修地址\n" );
    	sql.append("       (SELECT S.NAME FROM SE_BA_CODE S WHERE S.CODE = T.FAULT_FROM AND S.CODE_TYPE = 302701 ) FAULT_FROM,\n" );
    	sql.append("       T.FAULT_ADDRESS, --故障地点\n" );
    	sql.append("       T.FAULT_DATE, --故障时间\n" );
 	   	sql.append("       (SELECT S.NAME FROM SE_BA_CODE S WHERE S.CODE = T.FAULT_ANLYSIE AND S.CODE_TYPE = 302702 ) FAULT_ANLYSIE,\n" );
    	sql.append("       T.ACTIVITY_ID, --活动ID\n" );
    	sql.append("       V.MODELS_CODE AS MODEL_CODE, --车辆型号\n" );
    	sql.append("       V.ENGINE_TYPE, --发动机型号\n" );
    	sql.append("       V.DRIVE_FORM, --驱动行驶\n" );
    	sql.append("       T.USER_TYPE AS USER_TYPE_NAME, --用户类型\n" );
    	sql.append("       T.VEHICLE_USE AS VEHICLE_NAME, --车辆用途\n" );
    	sql.append("       T.BUY_DATE, --购车日期\n" );
    	sql.append("       T.MILEAGE, --里程\n" );
    	sql.append("       T.GUARANTEE_NO, -- 报修卡号\n" );
    	sql.append("       T.APPLY_REPAIR_DATE, --报修时间\n" );
    	sql.append("       T.REPAIR_ADDRESS, --检修地址\n" );
    	sql.append("       T.REPAIR_DATE, --检修时间\n" );
    	sql.append("       T.REPAIR_USER, --检修人\n" );
    	sql.append("       T.DISPATCH_ID, --三包急件\n" );
    	sql.append("       T.TRAIN_COSTS, --售前培训费用\n" );
    	sql.append("       T.SAFE_COSTS, --安全检查\n" );
    	sql.append("       T.MAINTENANCE_COSTS, --首保费用\n" );
    	sql.append("       T.MAINTENANCE_DATE, --首保日期\n" );
    	sql.append("       T.LICENSE_PLATE, --牌照\n" );
    	sql.append("       T.USER_NAME, --用户名称\n" );
    	sql.append("       T.USER_NO, --身份证号\n" );
    	sql.append("       T.LINK_MAN, --联系人\n" );
    	sql.append("       T.PHONE, --联系电话\n" );
    	sql.append("       T.USER_ADDRESS, --用户地址\n" );
    	sql.append("       T.REMARKS, --备注\n" );
    	sql.append("       T.ORG_ID, --服务商\n" );
    	sql.append("       T.ENGINE_NO, --发动机号\n" );
    	sql.append("       T.APPLY_USER, --报修人\n" );
    	sql.append("       T.SE_TYPE, --服务类型\n" );
    	sql.append("       T.APPLY_MOBIL, --报修人电话\n" );
    	sql.append("       T.PROVINCE_CODE, --省\n" );
    	sql.append("       T.CITY_CODE, --市\n" );
    	sql.append("	   T.COUNTY_CODE, --县\n" );
    	sql.append("       A.ACTIVITY_CODE,--活动代码\n" );
    	sql.append("       AU.AUTHOR_NO,--预授权单号\n" );
    	sql.append("       D.DISPATCH_NO --三包急件单号\n");
    	sql.append(" FROM SE_BU_CLAIM      T,\n" );
    	sql.append("      MAIN_VEHICLE     V,\n" );
    	sql.append("      SE_BU_WORK_ORDER O,\n" );
    	sql.append("      SE_BU_ACTIVITY   A,\n" );
    	sql.append("      SE_BU_PRE_AUTHOR AU,\n" );
    	sql.append("      SE_BU_DISPATCH   D\n" );
    	sql.append("WHERE T.VEHICLE_ID = V.VEHICLE_ID\n" );
    	sql.append("  AND T.WORK_ID = O.WORK_ID\n" );
    	sql.append("  AND T.ACTIVITY_ID = A.ACTIVITY_ID(+)\n" );
    	sql.append("  AND T.PRE_AUTHOR_ID = AU.AUTHOR_ID(+)\n");
    	sql.append("  AND T.DISPATCH_ID = D.DISPATCH_ID(+)\n");
    	sql.append("   AND T.CLAIM_ID = "+claimId+"");
    	bs=factory.select(sql.toString(), page);
  	    bs.setFieldDic("CLAIM_STATUS", "SPDZT");
  	    bs.setFieldDic("SE_TYPE", "FWLX");
  	    bs.setFieldDic("CLAIM_TYPE", "SPDLX");
  	    bs.setFieldDic("USER_TYPE", "YHLX");
  	    bs.setFieldDic("APPLY_USER_TYPE", "BXRLX");
  	    bs.setFieldDic("USER_TYPE_NAME", "CLYHLX");
  	    bs.setFieldDic("VEHICLE_NAME", "CLYT");
  	    bs.setFieldDic("DRIVE_FORM", "QDXS");
  	    bs.setFieldOrgDeptSimpleName("ORG_ID");
  	    bs.setFieldDateFormat("BUY_DATE","yyyy-MM-dd");
  	    bs.setFieldDateFormat("REPAIR_DATE", "yyyy-MM-dd HH:mm");
  	    bs.setFieldDateFormat("APPLY_REPAIR_DATE", "yyyy-MM-dd HH:mm");
  	    bs.setFieldDateFormat("FAULT_DATE", "yyyy-MM-dd HH:mm");
  	    bs.setFieldDateFormat("REJECT_DATE", "yyyy-MM-dd HH:mm");
  	    bs.setFieldDateFormat("MAINTENANCE_DATE", "yyyy-MM-dd");
  	    bs.setFieldSimpleXZQH("PROVINCE_CODE");
  	    bs.setFieldSimpleXZQH("CITY_CODE");
  	    bs.setFieldSimpleXZQH("COUNTY_CODE");
    	return bs;
    }
    
    /**
     * 外出信息
     * @param page
     * @param claimId
     * @return
     * @throws Exception
     */
    public BaseResultSet searchClaimOutInfo(PageManager page,String claimId)throws Exception{
    	BaseResultSet bs = null;
	   	StringBuffer sql= new StringBuffer();
	   	sql.append("SELECT T.OUT_ID,\n" );
	   	sql.append("       T.CLAIM_ID,\n" );
	   	sql.append("       T.OUT_TIMES,\n" );
	   	sql.append("       T.COST_AMOUNT,\n" );
	   	sql.append("       T.OUT_COSTS,\n" );
	   	sql.append("       T.SEVEH_COSTS,\n" );
	   	sql.append("       T.MEALS_COSTS,\n" );
	   	sql.append("       T.VEHBOAT_COSTS,\n" );
	   	sql.append("       T.TRAVEL_COSTS,\n" );
	   	sql.append("       T.OTHER_COSTS,\n" );
	   	sql.append("       T.OUT_TYPE,\n" );
	   	sql.append("       T.OUT_UCOUNT,\n" );
	   	sql.append("       T.OUT_USER,\n" );
	   	sql.append("       T.GO_DATE,\n" );
	   	sql.append("       T.ARRIVE_DATE,\n" );
	   	sql.append("       T.LEAVE_DATE,\n" );
	   	sql.append("       T.MILEAGE,\n" );
	   	sql.append("       T.VEHICLE_NO,\n" );
	   	sql.append("       T.ON_WAY_DAYS,\n" );
	   	sql.append("       T.OUTDATE_TYPE,\n" );
	   	sql.append("       T.REMARKS,\n" );
	   	sql.append("       T.TRAVEL_DAYS,\n" );
	   	sql.append("       T.IS_OUT_TIMES,\n" );
	   	sql.append("       T.SEC_VEH_COSTS\n" );
	   	sql.append("  FROM SE_BU_CLAIM_OUT T\n");
	   	sql.append(" WHERE T.CLAIM_ID ="+claimId+"");
	 	bs = factory.select(sql.toString(), page);
	 	bs.setFieldDic("OUT_TYPE","WCFS");
	 	bs.setFieldDic("IS_OUT_TIMES","SF");
	 	bs.setFieldDateFormat("GO_DATE", "yyyy-MM-dd HH:mm");
		bs.setFieldDateFormat("ARRIVE_DATE", "yyyy-MM-dd HH:mm");
		bs.setFieldDateFormat("LEAVE_DATE", "yyyy-MM-dd HH:mm");
		bs.setFieldDic("OUTDATE_TYPE", "WCSJ");
	   	return bs;
    }
    
    /**
     * 故障信息
     * @param page
     * @param user
     * @param conditions
     * @return
     * @throws Exception
     */
    public BaseResultSet searchClaimPattern(PageManager page, User user, String conditions) throws Exception
    {
 	   	String wheres = conditions;
 	   	wheres += "  AND STATUS="+Constant.YXBS_01;
 	       page.setFilter(wheres);
 	    page.setPageRows(100);
 	   	BaseResultSet bs = null;
 	   	StringBuffer sql= new StringBuffer();
 	   	sql.append("SELECT CLAIM_DTL_ID,\n" );
 	   	sql.append("       CLAIM_ID,\n" );
 	   	sql.append("       FAULT_ID,\n" );
 	   	sql.append("       FAULT_CODE,\n" );
 	   	sql.append("       FAULT_NAME,\n" );
 	   	sql.append("       WORK_TIME,\n" );
 	   	sql.append("       WORK_TIME_UPRICE,\n" );
 	   	sql.append("       STAR_LEVEL_UPRICE,\n" );
 	   	sql.append("       ENCOURAGE_UPRICE,\n" );
 	   	sql.append("       WORK_COSTS,\n" );
 	   	sql.append("       CREATE_USER,\n" );
 	   	sql.append("       CREATE_TIME,\n" );
 	   	sql.append("       STATUS,\n" );
 	    sql.append("       WORK_MULTIPLE,\n" );
		sql.append("       (SELECT C.NAME FROM SE_BA_FAULT_PATTERN P ,SE_BA_CODE C WHERE P.PATTERN_ID=FAULT_ID AND P.SEVERITY = C.CODE_ID) AS　SEVERITY\n" );
 	   	sql.append("  FROM SE_BU_CLAIM_FAULT");	 
 	   	bs = factory.select(sql.toString(), page);
 	   	return bs;
    }
    
    /**
     * 附件查询
     * @param page 分页
     * @param user 用户
     * @return
     * @throws SQLException 
     */
    public BaseResultSet fileSearch(PageManager page,User user,String workId) throws SQLException{
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.* FROM FS_FILESTORE T WHERE T.YWZJ = "+workId+" ORDER BY T.CJSJ");
    	bs=factory.select(sql.toString(), page);
    	bs.setFieldDateFormat("CJSJ","yyyy-MM-dd HH:mm:ss");
    	return bs;
    }
    
    /**
     * 审核轨迹查询
     * @param page
     * @param condition
     * @param user
     * @return
     * @throws Exception
     */
    public BaseResultSet hisCheckSearch(PageManager page,String claimId)throws Exception{
 	   BaseResultSet bs = null;
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT C.CHECK_USER, C.CHECK_DATE, C.CHECK_RESULT, C.CHECK_REMARKS\n" );
	   sql.append("  FROM SE_BU_CLAIM_CHECK C, SE_BU_CLAIM T\n" );
 	   sql.append(" WHERE C.CLAIM_ID = T.CLAIM_ID\n" );
 	   sql.append(" AND T.CLAIM_ID="+claimId+"\n" );
 	   sql.append(" ORDER BY C.CHECK_DATE ");
 	   bs=factory.select(sql.toString(), page);
 	   bs.setFieldDic("CHECK_RESULT","SPDZT");
 	   bs.setFieldDateFormat("CHECK_DATE", "yyyy-MM-dd HH:mm:ss");
 	   return bs;
    }
    
    /**
     * 查询故障配件信息
     * @param page
     * @param claimDtlId
     * @throws Exception
     */
    public  BaseResultSet searchFaultPart(PageManager page,String claimDtlId)throws Exception{
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.FAULT_TYPE,\n" );
    	sql.append("       T.MEASURES,\n" );
    	sql.append("       T.OLD_PART_CODE,\n" );
    	sql.append("       T.OLD_PART_NAME,\n" );
    	sql.append("       T.OLD_PART_COUNT,\n" );
    	sql.append("       O.SUPPLIER_NAME   OLD_SUPPLIER_NAME,\n" );
    	sql.append("       O.SUPPLIER_CODE   OLD_SUPPLIER_CODE,\n" );
    	sql.append("       T.OLD_PART_STREAM,\n" );
    	sql.append("       T.NEW_PART_CODE,\n" );
    	sql.append("       T.NEW_PART_NAME,\n" );
    	sql.append("       T.NEW_PART_COUNT,\n" );
    	sql.append("       N.SUPPLIER_NAME   NEW_SUPPLIER_NAME,\n" );
    	sql.append("       N.SUPPLIER_CODE   NEW_SUPPLIER_CODE,\n" );
    	sql.append("       T.NEW_PART_STREAM,\n" );
    	sql.append("       T.REPAY_UPRICE,\n" );
    	sql.append("       T.CLAIM_UPRICE,\n" );
    	sql.append("       T.BRIDGE_CODE,\n" );
    	sql.append("       T.FAULT_REASON\n" );
    	sql.append("  FROM SE_BU_CLAIM_FAULT_PART T, PT_BA_SUPPLIER O, PT_BA_SUPPLIER N\n" );
    	sql.append(" WHERE T.CLAIM_DTL_ID ="+claimDtlId+"\n" );
    	sql.append("   AND T.OLD_SUPPLIER_ID = O.SUPPLIER_ID(+) \n" );
    	sql.append("   AND T.NEW_SUPPLIER_ID = N.SUPPLIER_ID(+) ");
    	bs=factory.select(sql.toString(), page);
    	bs.setFieldDic("FAULT_TYPE", "GZLB");
    	bs.setFieldDic("MEASURES", "CLFS");
   	    return bs;
    }
} 
