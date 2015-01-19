package com.org.dms.dao.service.claimmng;

import java.sql.SQLException;
import java.util.Map;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuDealerStockVO;
import com.org.dms.vo.service.SeBuClaimCheckVO;
import com.org.dms.vo.service.SeBuClaimVO;
import com.org.frameImpl.Constant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class ClaimCheckDao extends BaseDAO
{
    //定义instance
    public  static final ClaimCheckDao getInstance(ActionContext atx)
    {
    	ClaimCheckDao dao = new ClaimCheckDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
    
    /**
     * 索赔单查询
     * @param page
     * @param condition
     * @param user
     * @return
     * @throws Exception
     */
    public BaseResultSet claimSearch(PageManager page,String condition,User user)throws Exception{
    	String where = condition;
	       where += " AND T.WORK_ID = O.WORK_ID \n "+
	       			" AND NVL(T.STOCK_MEET,0) <> "+DicConstant.SF_02+"\n"+     //外采单通过 或者 直接满足
	    		    " AND T.CLAIM_STATUS="+DicConstant.SPDZT_03+" \n"+  //查询索赔单自动审核通过
	    		    " AND T.OPERATE_USER = '"+user.getAccount()+"' \n"+
					//" AND (SELECT COUNT(1)\n" +
					//"         FROM SE_BU_CLAIM_FAULT_PART P\n" + 
					//"        WHERE P.CLAIM_ID = T.CLAIM_ID\n" + 
					//"          AND P.FAULT_TYPE = 301601\n" + 
					//"          AND P.SUPPLIER_OPTION_STATUS IS NULL) = 0 \n"+  //供应商审核，全部审核或者超时 加状态
	       			" ORDER BY T.APPLY_DATE ";
		page.setFilter(where);
		BaseResultSet bs=null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.CLAIM_ID,\n" );
		sql.append("       T.CLAIM_NO,\n" ); 
		sql.append("       O.WORK_ID,\n" );
		sql.append("       O.WORK_NO,\n" );
		sql.append("       T.CLAIM_TYPE,\n" );
		sql.append("       T.CLAIM_STATUS,\n" );
		sql.append("       T.APPLY_DATE,\n" );
		sql.append("       T.APPLY_COUNT\n" );
		sql.append("  FROM SE_BU_CLAIM T, SE_BU_WORK_ORDER O\n" );
		bs=factory.select(sql.toString(), page);
		bs.setFieldDic("CLAIM_STATUS","SPDZT");
		bs.setFieldDic("CLAIM_TYPE","SPDLX");
		bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
		return bs;
    }

    /**
     * 索赔单绑定审核人
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean claimUpdate(SeBuClaimVO vo)throws Exception{
    	return factory.update(vo);
    }
    /**
     * 索赔单费用查询
     * @param claimId
     * @return
     * @throws Exception
     */
    public BaseResultSet claimCostsSearch(String claimId) throws Exception
    {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.CLAIM_ID,\n" );
    	sql.append("       NVL(T.OUT_AMOUNT, 0) OUT_AMOUNT, --外出总费用\n" );
    	sql.append("       NVL(T.MATERIAL_COSTS, 0) MATERIAL_COSTS, --材料总费用\n" );
    	sql.append("       NVL(T2.WORK_COSTS, 0) WORK_COSTS, --工时总费用\n" );
    	sql.append("       NVL(T.MAINTENANCE_COSTS, 0) MAINTENANCE_COSTS, -- 首保费\n" );
    	sql.append("       NVL(T.SERVICE_COST, 0) SERVICE_COST, --服务活动费\n" );
    	sql.append("       NVL(T.SAFE_COSTS, 0) SAFE_COSTS, --安全检查费用\n" );
    	sql.append("       NVL(T.TRAIN_COSTS, 0) TRAIN_COSTS, --售前培训费用\n" );
    	sql.append("       NVL(T1.OUT_COSTS, 0) OUT_COSTS, --一次外出费用\n" );
    	sql.append("       NVL(T1.SEVEH_COSTS, 0) SEVEH_COSTS, --服务车费\n" );
    	sql.append("       NVL(T1.MEALS_COSTS, 0) MEALS_COSTS, --在途餐补\n" );
    	sql.append("       NVL(T1.TRAVEL_COSTS, 0) TRAVEL_COSTS, --差旅费\n" );
    	sql.append("       NVL(T1.OTHER_COSTS, 0) OTHER_COSTS, --其他费\n" );
    	sql.append("       NVL(T1.SEC_VEH_COSTS, 0) SEC_VEH_COSTS, --二次外出费\n" );
    	sql.append("	   (NVL(T.OUT_AMOUNT, 0) + NVL(T.MATERIAL_COSTS, 0) +\n" );
    	sql.append("       NVL(T2.WORK_COSTS, 0) + NVL(T.MAINTENANCE_COSTS, 0) +\n" );
    	sql.append("       NVL(T.SERVICE_COST, 0) + NVL(T.SAFE_COSTS, 0) +\n" );
    	sql.append("       NVL(T.TRAIN_COSTS, 0)) CLAIM_AMOUNT --索赔单总费用\n");
    	sql.append("  FROM SE_BU_CLAIM T,\n" );
    	sql.append("       (SELECT O.CLAIM_ID,\n" );
    	sql.append("               SUM(O.OUT_COSTS) OUT_COSTS,\n" );
    	sql.append("               SUM(O.SEVEH_COSTS) SEVEH_COSTS,\n" );
    	sql.append("               SUM(O.MEALS_COSTS) MEALS_COSTS,\n" );
    	sql.append("               SUM(O.TRAVEL_COSTS) TRAVEL_COSTS,\n" );
    	sql.append("               SUM(O.OTHER_COSTS) OTHER_COSTS,\n" );
    	sql.append("               SUM(O.SEC_VEH_COSTS) SEC_VEH_COSTS\n" );
    	sql.append("          FROM SE_BU_CLAIM_OUT O\n" );
    	sql.append("         GROUP BY O.CLAIM_ID) T1,\n" );
    	sql.append("       (SELECT F.CLAIM_ID, SUM(F.WORK_COSTS) WORK_COSTS\n" );
    	sql.append("          FROM SE_BU_CLAIM_FAULT F\n" );
    	sql.append("         GROUP BY F.CLAIM_ID) T2\n" );
    	sql.append(" WHERE T.CLAIM_ID = T1.CLAIM_ID(+)\n" );
    	sql.append("   AND T.CLAIM_ID = T2.CLAIM_ID(+)\n" );
    	sql.append("   AND T.CLAIM_ID = "+claimId+"");
    	return factory.select(sql.toString(), new PageManager());
    }
    
    
    /**
     * 附件查询 
     * @param page
     * @param user
     * @param workId 工单ID
     * @return
     * @throws SQLException
     */
    public BaseResultSet fileSearch(PageManager page,User user,String workId) throws SQLException{
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
     * 索赔单类型为服务活动 更新车辆未使用
     * @param claimId
     * @param user
     * @return
     * @throws Exception
     */
    public boolean updateActivityVehicle(String claimId,User user)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("UPDATE SE_BU_ACTIVITY_VEHICLE E\n" );
    	sql.append("   SET E.CLAIM_USER = "+DicConstant.SF_02+", E.UPDATE_USER = '"+user.getAccount()+"', E.UPDATE_TIME = SYSDATE\n" );
    	sql.append(" WHERE EXISTS (SELECT 1\n" );
    	sql.append("          FROM SE_BU_CLAIM C\n" );
    	sql.append("         WHERE C.VEHICLE_ID = E.VEHICLE_ID\n" );
    	sql.append("           AND C.ACTIVITY_ID = E.ACTIVITY_ID\n" );
    	sql.append("           AND C.CLAIM_ID = "+claimId+")");
    	return factory.update(sql.toString(), null);
    }
    
    /**
     * 索赔单驳回、拒绝 将车辆最新里程 更新成 上一次里程
     * @param claimId
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
	public boolean updateVehicle(String claimId,Map map,User user)throws Exception{
    	String flag ="0";
 	   	String sbxx=(String)map.get("sbxx");//首保信息
 	   	String gCount=(String)map.get("dbcs");//定保次数，-1
	 	String safechecktimes=(String)map.get("aqjccs");//安全检查次数 0
	 	String traintimes=(String)map.get("sqjccs");//售前检查培训次数0
	 	StringBuffer sql= new StringBuffer();
	 	sql.append("UPDATE MAIN_VEHICLE V\n" );
	 	sql.append("   SET V.UPDATE_USER='"+user.getAccount()+"',V.UPDATE_TIME = SYSDATE ,V.MILEAGE = V.LRUNKM\n");
	 	if(sbxx !=null && !"".equals(sbxx)){
	 		sql.append("   ,V.MAINTENANCE_DATE = '' \n");
	 		sql.append("   ,V.FMAINTAINFLAG = 100102 \n");//强保标识
	 		flag="1";
	 	}
	 	if(gCount!=null &&!"".equals(gCount))
	 	{
	 		sql.append("   ,V.G_COUNT = V.G_COUNT - 1 \n");
	 		flag="1";
	 	}
	 	if(safechecktimes!=null &&!"".equals(safechecktimes))
	 	{
	 		sql.append("   ,V.SAFECHECKTIMES = V.SAFECHECKTIMES - 1 \n");
	 		flag="1";
	 	}
	 	if(traintimes!=null &&!"".equals(traintimes))
	 	{
	 		sql.append("   ,V.TRAINTIMES = V.TRAINTIMES - 1 \n");
	 		flag="1";
	 	}
	 	sql.append(" WHERE EXISTS (SELECT 1\n" );
    	sql.append("          FROM SE_BU_CLAIM C\n" );
    	sql.append("         WHERE C.VEHICLE_ID = V.VEHICLE_ID\n" );
    	sql.append("           AND C.CLAIM_ID = "+claimId+")");
	 	if("1".equals(flag)){
	 		return factory.update(sql.toString(), null);
	 	}else{
	 		return true;
	 	}
    	/*
    	StringBuffer sql= new StringBuffer();
    	sql.append("UPDATE MAIN_VEHICLE V\n" );
    	sql.append("   SET V.MILEAGE = V.LRUNKM\n" );
    	sql.append(" WHERE EXISTS (SELECT 1\n" );
    	sql.append("          FROM SE_BU_CLAIM C\n" );
    	sql.append("         WHERE C.VEHICLE_ID = V.VEHICLE_ID\n" );
    	sql.append("           AND C.CLAIM_ID = "+claimId+")");
    	return factory.update(sql.toString(), null);*/
    }
    
    /**
     * 更新预授权
     * @return
     * @throws Exception
     */
    public boolean updatePreAuthor(String claimId,User user)throws Exception{
 	    StringBuffer sql= new StringBuffer();
    	sql.append("UPDATE SE_BU_PRE_AUTHOR T\n" );
  	   	sql.append("   SET T.IF_APPLYCLAIM = "+DicConstant.SF_02+", T.UPDATE_USER = '"+user.getAccount()+"', T.UPDATE_TIME = SYSDATE\n" );
    	sql.append(" WHERE EXISTS (SELECT 1\n" );
    	sql.append("          FROM SE_BU_CLAIM C\n" );
    	sql.append("         WHERE C.PRE_AUTHOR_ID = T.AUTHOR_ID\n" );
    	sql.append("           AND C.CLAIM_ID = "+claimId+")");
    	return factory.update(sql.toString(), null);
    }
    /**
     * 获取索赔单旧件需要回运个数
     * @param claimId
     * @return
     * @throws Exception
     */
    public QuerySet getReturnOldPartCount(String claimId)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT COUNT(1) SL\n" );
    	sql.append("  FROM SE_BU_CLAIM_FAULT_PART P, PT_BA_INFO I\n" );
    	sql.append(" WHERE P.OLD_PART_ID = I.PART_ID\n" );
    	sql.append("   AND P.CLAIM_ID = "+claimId+"\n" );
    	sql.append("   AND P.MEASURES = 300602 \n");
    	sql.append("   AND I.IF_RETURN = 100101");
    	return factory.select(null, sql.toString());
    }
    
    /**
     * 更新不需要回运旧件的审核通过数量
     * @param claimId
     * @return
     * @throws Exception
     */
    public boolean updateOught(String claimId)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("UPDATE SE_BU_CLAIM_FAULT_PART P\n" );
    	sql.append("   SET P.OUGHT_COUNT = P.OLD_PART_COUNT\n" );
    	sql.append(" WHERE P.CLAIM_ID = "+claimId+"\n" );
    	sql.append("   AND EXISTS (SELECT 1\n" );
    	sql.append("          FROM PT_BA_INFO I\n" );
    	sql.append("         WHERE P.OLD_PART_ID = I.PART_ID\n" );
    	sql.append("           AND I.IF_RETURN = 100102)");
    	return factory.update(sql.toString(), null);
    }
    /**
     * 更新维修和加装的审核数量
     * @param claimId
     * @return
     * @throws Exception
     */
    public boolean updateOught1(String claimId)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("UPDATE SE_BU_CLAIM_FAULT_PART P\n" );
    	sql.append("   SET P.OUGHT_COUNT = DECODE(P.OLD_PART_COUNT,\n" );
    	sql.append("                              '',\n" );
    	sql.append("                              P.NEW_PART_COUNT,\n" );
    	sql.append("                              P.OLD_PART_COUNT)\n" );
    	sql.append(" WHERE P.CLAIM_ID = "+claimId+"\n" );
    	sql.append("   AND P.MEASURES IN (300601, 300603)");
    	return factory.update(sql.toString(), null);
    }
    /**
     * 索赔单审核
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean claimCheckUpdae(SeBuClaimVO vo)throws Exception{
    	return factory.update(vo);
    }
    
    /**
     * 审核轨迹
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean insertCheck(SeBuClaimCheckVO vo)throws Exception{
    	return factory.insert(vo);
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
	   	sql.append("       T.SEC_VEH_COSTS,\n" );
	   	sql.append("       T.START_PHONE_LONGITUDE,\n" );
	   	sql.append("       T.END_PHONE_LONGITUDE,\n" );
	   	sql.append("       T.START_PHONE_LATITUDE,\n" );
	   	sql.append("       T.END_PHONE_LATITUDE,\n" );
	   	sql.append("       T.START_VEHICLE_LONGITUDE,\n" );
	   	sql.append("       T.END_VEHICLE_LONGITUDE,\n" );
	   	sql.append("       T.START_VEHICLE_LATITUDE,\n" );
	   	sql.append("       T.END_VEHICLE_LATITUDE,\n" );
		sql.append("       T.VEHICLE_PHONE_MILEAGE,\n" );
	   	sql.append("       T.START_PHONE_LOCATION,\n" );
	   	sql.append("       T.END_PHONE_LOCATION,\n" );
	   	sql.append("       T.START_VEHICLE_LOCATION,\n" );
	   	sql.append("       T.END_VEHICLE_LOCATION,\n" );
	   	sql.append("       T.GPS_MILEAGE\n" );
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
     * 故障信息(供应商审核意见如果是同意 ，则将不查出来)
     * @param page
     * @param user
     * @param conditions
     * @return
     * @throws Exception
     */
    public BaseResultSet searchClaimPattern(PageManager page, String claimId) throws Exception
    {
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
 	   	sql.append("  FROM SE_BU_CLAIM_FAULT A\n");
 	   	sql.append(" WHERE STATUS="+Constant.YXBS_01+" \n");
 	    sql.append(" AND CLAIM_ID="+claimId+" \n");
	 	sql.append(" AND NOT EXISTS (SELECT 1\n" );
	 	sql.append("         FROM SE_BU_CLAIM_FAULT_PART F\n" );
	 	sql.append("        WHERE F.FAULT_TYPE = 301601\n" );
	 	sql.append("          AND A.CLAIM_DTL_ID = F.CLAIM_DTL_ID\n" );
	 	sql.append("          AND F.SUPPLIER_OPTION_STATUS = 307501)");
 	   	bs = factory.select(sql.toString(), page);
 	   	return bs;
    }
    /**
     * 查询索赔单中旧件ID，与供应商ID
     * @return
     * @throws Exception
     */
    public QuerySet queryParts(String claimId)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT D.PART_ID,\n" );
    	sql.append("       D.PART_CODE,\n" );
    	sql.append("       D.PART_NAME,\n" );
    	sql.append("       R.ORG_ID,\n" );
    	sql.append("       D.SUPPLIER_ID,\n" );
    	sql.append("       D.SUPPLIER_CODE,\n" );
    	sql.append("       D.SUPPLIER_NAME,\n" );
    	sql.append("       R.SALE_ID,\n" );
    	sql.append("       D.SALE_COUNT,\n" );
    	sql.append("       D.DTL_ID\n" );
    	sql.append("  FROM PT_BU_REAL_SALE R, PT_BU_REAL_SALE_DTL D\n" );
    	sql.append(" WHERE R.SALE_ID = D.SALE_ID\n" );
    	sql.append("   AND R.CLAIM_ID = "+claimId+"");
    	sql.append("   AND R.STATUS = "+DicConstant.YXBS_01+"");
 	   return factory.select(null, sql.toString());
    }
    /**
     * 查询索赔单中旧件ID，与供应商ID,服务站对应的可用库存
     * @return
     * @throws Exception
     */
    public QuerySet queryStock(String oPartId,String oSupplierId, String orgId)throws Exception{
 	   StringBuffer sql= new StringBuffer();
 	   sql.append("SELECT NVL(T.AVAILABLE_AMOUNT,0) AVAILABLE_AMOUNT, NVL(T.OCCUPY_AMOUNT,0) OCCUPY_AMOUNT, NVL(T.AMOUNT,0) AMOUNT,T.STOCK_ID\n" );
 	   sql.append("  FROM PT_BU_DEALER_STOCK T\n" );
 	   sql.append(" WHERE T.PART_ID ="+oPartId+"\n" );
 	   sql.append("   AND T.SUPPLIER_ID ="+oSupplierId+"\n" );
 	   sql.append("   AND T.ORG_ID ="+orgId+"\n" );
 	   sql.append("   AND T.STATUS = "+DicConstant.YXBS_01+"");
 	   return factory.select(null, sql.toString());
    }
    
    /**
     * 库存锁定数量
     */
    public boolean DealerStockUpdate(PtBuDealerStockVO vo) throws Exception {

        return factory.update(vo);
    }
    /**
     * 将实销出库单更新为无效。
     */
    public boolean updateRsStatus(String claimId) throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("UPDATE PT_BU_REAL_SALE R\n" );
    	sql.append("   SET R.STATUS = "+DicConstant.YXBS_02+"\n" );
    	sql.append(" WHERE R.CLAIM_ID = "+claimId+"\n" );
    	sql.append("   AND R.STATUS = "+DicConstant.YXBS_01+"");
		return factory.update(sql.toString(), null);

    }
    /**
     * 将外采申请单更新为无效。
     */
    public boolean updateObStatus(String claimId) throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("UPDATE PT_BU_OUT_BUY R\n" );
    	sql.append("   SET R.STATUS = "+DicConstant.YXBS_02+"\n" );
    	sql.append(" WHERE R.CLAIM_ID = "+claimId+"\n" );
    	sql.append("   AND R.STATUS = "+DicConstant.YXBS_01+"");
    	return factory.update(sql.toString(), null);
    	
    }
    /**
     * 将实销出库单更新为已出库
     */
    public boolean updateRsSaleStatus(String claimId) throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("UPDATE PT_BU_REAL_SALE R\n" );
    	sql.append("   SET R.SALE_STATUS = "+DicConstant.SXDZT_02+"\n" );
    	sql.append(" WHERE R.CLAIM_ID = "+claimId+"\n" );
    	sql.append("   AND R.STATUS = "+DicConstant.YXBS_01+"");
    	return factory.update(sql.toString(), null);
    	
    }
    
    /**
     * 索赔单费用调整查询
     * @param page
     * @param condition
     * @param user
     * @return
     * @throws Exception
     */
    public BaseResultSet claimCostAdjustSearch(PageManager page,String condition,User user)throws Exception{
    	String where = condition;
    	       where += " AND C.CLAIM_STATUS IN (301005, 301015) \n"+ 
    		            " ORDER BY C.CLAIM_ID";
		page.setFilter(where);
		BaseResultSet bs=null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT C.CLAIM_ID,\n" );
    	sql.append("       C.CLAIM_NO,\n" );
    	sql.append("       C.VIN,\n" );
    	sql.append("       C.CLAIM_TYPE,\n" );
    	sql.append("       C.CLAIM_STATUS,\n" );
    	sql.append("       C.APPLY_DATE,\n" );
    	sql.append("       C.ORG_ID       ORG_CODE,\n" );
    	sql.append("       C.ORG_ID       ORG_NAME,\n" );
    	sql.append("       NVL(C.ADJUST_COSTS,0) ADJUST_COSTS,\n" );
    	sql.append("       C.ADJUST_REMARKS\n" );
    	sql.append("  FROM SE_BU_CLAIM C\n" );
    	bs=factory.select(sql.toString(), page);
    	bs.setFieldDic("CLAIM_STATUS","SPDZT");
    	bs.setFieldDic("CLAIM_TYPE","SPDLX");
    	bs.setFieldDateFormat("APPLY_DATE","yyyy-MM-dd HH:mm:ss");
    	bs.setFieldOrgDeptSimpleName("ORG_NAME");//简称
    	bs.setFieldOrgDeptCode("ORG_CODE");
    	return bs;
    }
    public void insetStockDtl1(Integer saleCount,User user,String saleId,String url,String nPartId,String orgId) throws Exception
    {
    	StringBuffer sql= new StringBuffer();
    	sql.append("INSERT INTO PT_BU_DEALER_STOCK_LOG\n" );
    	sql.append("          (LOG_ID,\n" );
    	sql.append("           YWZJ,\n" );
    	sql.append("           ACTION_URL,\n" );
    	sql.append("           OAMOUNT,\n" );
    	sql.append("           AMOUNT,\n" );
    	sql.append("           AAMOUNT,\n" );
    	sql.append("           UPDATE_USER,\n" );
    	sql.append("           UPDATE_TIME,\n" );
    	sql.append("           PART_ID,\n" );
    	sql.append("           ORG_ID)\n" );
    	sql.append("        VALUES\n" );
    	sql.append("          (F_GETID(),\n" );
    	sql.append("           '"+saleId+"',\n" );
    	sql.append("           '"+url+"',\n" );
    	sql.append("           '-"+saleCount+"',\n" );//占用
    	sql.append("           '',\n" );//总数
    	sql.append("           '"+saleCount+"',\n" );//可用
    	sql.append("           '"+user.getAccount()+"',\n" );
    	sql.append("           SYSDATE,\n" );
    	sql.append("           '"+nPartId+"',\n" );
    	sql.append("           '"+orgId+"')");
    	
    	factory.update(sql.toString(), null);
    }
    public void insetStockDtl(Integer saleCount,User user,String saleId,String url,String nPartId,String orgId) throws Exception
    {
 	   StringBuffer sql= new StringBuffer();
 	   sql.append("INSERT INTO PT_BU_DEALER_STOCK_LOG\n" );
 	   sql.append("          (LOG_ID,\n" );
 	   sql.append("           YWZJ,\n" );
 	   sql.append("           ACTION_URL,\n" );
 	   sql.append("           OAMOUNT,\n" );
 	   sql.append("           AMOUNT,\n" );
 	   sql.append("           AAMOUNT,\n" );
 	   sql.append("           UPDATE_USER,\n" );
 	   sql.append("           UPDATE_TIME,\n" );
 	   sql.append("           PART_ID,\n" );
 	   sql.append("           ORG_ID)\n" );
 	   sql.append("        VALUES\n" );
 	   sql.append("          (F_GETID(),\n" );
 	   sql.append("           '"+saleId+"',\n" );
 	   sql.append("           '"+url+"',\n" );
 	   sql.append("           '-"+saleCount+"',\n" );//占用
 	   sql.append("           '-"+saleCount+"',\n" );//总数
 	   sql.append("           '',\n" );//可用
 	   sql.append("           '"+user.getAccount()+"',\n" );
 	   sql.append("           SYSDATE,\n" );
 	   sql.append("           '"+nPartId+"',\n" );
 	   sql.append("           '"+orgId+"')");

 	   factory.update(sql.toString(), null);
    }
    public void insetStockChange(String dtlId,String stockId, String orgId,User user) throws Exception
    {
    	StringBuffer sql= new StringBuffer();
    	sql.append("INSERT INTO PT_BU_DEALER_STOCK_CHANGE(\n" );
    	sql.append("       CHANGE_ID,\n" );
    	sql.append("       STOCK_ID,\n" );
    	sql.append("       ORG_ID,\n" );
    	sql.append("       ORG_CODE,\n" );
    	sql.append("       ORG_NAME,\n" );
    	sql.append("       PART_ID,\n" );
    	sql.append("       PART_CODE,\n" );
    	sql.append("       PART_NAME,\n" );
    	sql.append("       COUNT,\n" );
    	sql.append("       APPLY_DATE,\n" );
    	sql.append("       APPLY_TYPE,\n" );
    	sql.append("       CREATE_USER,\n" );
    	sql.append("       CREATE_TIME,\n" );
    	sql.append("       STATUS,\n" );
    	sql.append("       SUPPLIER_ID,\n" );
    	sql.append("       SUPPLIER_CODE,\n" );
    	sql.append("       SUPPLIER_NAME,\n" );
    	sql.append("       STORAGE_TYPE,\n" );
    	sql.append("       OUT_NO)\n" );
    	sql.append("SELECT F_GETID(),\n" );
    	sql.append("        '"+stockId+"',--STOCK_ID\n" );
    	sql.append("        '"+orgId+"',--ORG_ID\n" );
    	sql.append("        (SELECT G.CODE FROM TM_ORG G WHERE G.ORG_ID = "+orgId+"),--ORG_CODE\n" );
    	sql.append("        (SELECT G.ONAME FROM TM_ORG G WHERE G.ORG_ID = "+orgId+"),--ORG_NAME\n" );
    	sql.append("        D.PART_ID,\n" );
    	sql.append("        D.PART_CODE,\n" );
    	sql.append("        D.PART_NAME,\n" );
    	sql.append("        D.SALE_COUNT,\n" );
    	sql.append("        SYSDATE,\n" );
    	sql.append("        '204102',\n" );
    	sql.append("        '"+user.getAccount()+"',--创建人\n" );
    	sql.append("        SYSDATE,\n" );
    	sql.append("        '100201',\n" );
    	sql.append("        D.SUPPLIER_ID,\n" );
    	sql.append("        D.SUPPLIER_CODE,\n" );
    	sql.append("        D.SUPPLIER_NAME,\n" );
    	sql.append("        '204703',\n" );
    	sql.append("        (SELECT R.SALE_NO FROM PT_BU_REAL_SALE R WHERE R.SALE_ID = D.SALE_ID)\n" );
    	sql.append(" FROM PT_BU_REAL_SALE_DTL D\n" );
    	sql.append("  WHERE D.DTL_ID ="+dtlId+"");
    	
    	factory.update(sql.toString(), null);
    }
}
