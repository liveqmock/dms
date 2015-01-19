package com.org.dms.dao.service.claimmng;
import java.sql.SQLException;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 索赔单查询DAO
 * @author Administrator
 *
 */
public class ClaimSearchDao extends BaseDAO
{
    //定义instance
    public  static final ClaimSearchDao getInstance(ActionContext atx)
    {
    	ClaimSearchDao dao = new ClaimSearchDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * 车厂端索赔单查询
     * @param page 分页
     * @param user 用户
     * @param conditions 前台条件
     * @throws SQLException 
     */
    public BaseResultSet claimSearch(PageManager page, User user, String conditions,String outbuyStatus) throws SQLException{
    
    	String wheres = conditions;
    	int outStatus=Integer.valueOf(outbuyStatus);
    	if(-1==outStatus){
    		wheres   +=" AND SC.WORK_ID = SO.WORK_ID(+)\n"
    				+ "  AND SC.ORG_ID = G.ORG_ID \n"
    				+ "  AND G.PID = G1.ORG_ID \n"
       			  // +"  AND SC.STATUS="+DicConstant.YXBS_01+"\n"
       			     +"  AND SC.CLAIM_STATUS >= "+DicConstant.SPDZT_02+""
       			   +"  ORDER BY SC.APPLY_DATE,SC.REJECT_DATE";
    		page.setFilter(wheres);
    	}else if(1==outStatus){
    		wheres   +=" AND SC.WORK_ID = SO.WORK_ID(+)\n"
          	      // +"  AND SC.STATUS="+DicConstant.YXBS_01+"\n"
          			 + " AND SC.STOCK_MEET IS NOT NULL"
          			 +"  AND SC.CLAIM_STATUS >= "+DicConstant.SPDZT_02+""
          	       +"  ORDER BY SC.APPLY_DATE,SC.REJECT_DATE";
    		page.setFilter(wheres);
    	}else if(2==outStatus){
    		wheres   +=" AND SC.WORK_ID = SO.WORK_ID(+)\n"
         			// +"  AND SC.STATUS="+DicConstant.YXBS_01+"\n"
         			 + " AND SC.STOCK_MEET IS NULL"
         			 +"  AND SC.CLAIM_STATUS >= "+DicConstant.SPDZT_02+""
         			 +"  ORDER BY SC.APPLY_DATE,SC.REJECT_DATE";
    		page.setFilter(wheres);
    	}
    	
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT SO.WORK_NO,\n" );
    	sql.append("       SC.CLAIM_STATUS,\n" );
    	sql.append("       SC.CLAIM_TYPE,\n" );
    	sql.append("       SC.CLAIM_NO,\n" );
    	sql.append("       SC.CLAIM_ID,\n" );
    	sql.append("       SC.ACTIVITY_ID,\n" );
    	sql.append("       (SELECT A.ACTIVITY_CODE FROM SE_BU_ACTIVITY A WHERE A.ACTIVITY_ID = SC.ACTIVITY_ID)ACTIVITY_CODE,\n" );
    	sql.append("       SC.ORG_ID ORG_CODE,\n" );
    	sql.append("       SC.ORG_ID ORG_NAME,\n" );
    	sql.append("       SC.APPLY_DATE,\n" );
    	sql.append("       SC.REJECT_DATE,\n" );
    	sql.append("       SC.VEHICLE_ID,\n" );
    	sql.append("	   SC.VIN,\n" );
    	sql.append("       SC.FAULT_DATE,\n" );
    	sql.append("       SC.APPLY_REPAIR_DATE,\n" );
    	sql.append("       SC.REPAIR_DATE,");
    	sql.append("       SC.OPERATE_USER,");
    	sql.append("       G1.ONAME,");
    	sql.append("       G1.CODE,");
    	sql.append("	   CASE\n" );
    	sql.append("         WHEN SC.STOCK_MEET = 100102\n" );
    	sql.append("           THEN '审核中'\n" );
    	sql.append("         ELSE ''\n" );
    	sql.append("       END STOCK_STATUS ,");
    	sql.append("	   CASE\n" );
    	sql.append("         WHEN SC.STOCK_MEET IN ('100101', '100102') THEN\n" );
    	sql.append("          '是'\n" );
    	sql.append("         ELSE\n" );
    	sql.append("          '否'\n" );
    	sql.append("       END STOCK_MEET\n");
    	sql.append("  FROM SE_BU_WORK_ORDER SO, SE_BU_CLAIM SC,TM_ORG G,TM_ORG G1");
    	bs=factory.select(sql.toString(), page);
    	bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
    	bs.setFieldDateFormat("REJECT_DATE", "yyyy-MM-dd HH:mm:ss");
    	bs.setFieldDateFormat("FAULT_DATE", "yyyy-MM-dd HH:mm:ss");
    	bs.setFieldDateFormat("APPLY_REPAIR_DATE", "yyyy-MM-dd HH:mm:ss");
    	bs.setFieldDateFormat("REPAIR_DATE", "yyyy-MM-dd HH:mm:ss");
    	bs.setFieldDic("CLAIM_STATUS", "SPDZT");
    	bs.setFieldDic("CLAIM_TYPE", "SPDLX");
        bs.setFieldOrgDeptSimpleName("ORG_NAME");
		bs.setFieldOrgDeptCode("ORG_CODE");
    	return bs;
    }
    /**
     * 下端索赔单查询
     * @param page 分页
     * @param user 用户
     * @param conditions 前台条件
     * @throws SQLException 
     */
    public BaseResultSet claimDelSearch(PageManager page, User user, String conditions,String orgId) throws SQLException{
    	
    	String wheres = conditions;
    	wheres   +=" AND SC.WORK_ID = SO.WORK_ID(+)\n"
    			 + " AND SC.ORG_ID="+orgId+" \n"
    			// +"  AND SC.STATUS="+DicConstant.YXBS_01+"\n"
    			 +"  AND SC.CLAIM_STATUS >= "+DicConstant.SPDZT_02+""
    			 + " ORDER BY SC.APPLY_DATE,SC.REJECT_DATE";
    	page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT SO.WORK_NO,\n" );
    	sql.append("       SC.CLAIM_STATUS,\n" );
    	sql.append("       SC.CLAIM_NO,\n" );
    	sql.append("       SC.CLAIM_TYPE,\n" );
    	sql.append("       SC.CLAIM_ID,\n" );
    	sql.append("       SC.APPLY_DATE,\n" );
    	sql.append("       SC.REJECT_DATE,\n" );
    	sql.append("       SC.VEHICLE_ID,\n" );
    	sql.append("       SC.VIN,\n" );
    	sql.append("       SC.FAULT_DATE,\n" );
    	sql.append("       SC.APPLY_REPAIR_DATE,\n" );
    	sql.append("       SC.REPAIR_DATE,");
    	sql.append("	   CASE\n" );
    	sql.append("        WHEN SC.STOCK_MEET = 100102\n" );
    	sql.append("           THEN '审核中'\n" );
    	sql.append("        ELSE ''\n" );
    	sql.append("     END STOCK_MEET");
    	sql.append("  FROM SE_BU_WORK_ORDER SO, SE_BU_CLAIM SC");
    	bs=factory.select(sql.toString(), page);
    	bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
    	bs.setFieldDateFormat("REJECT_DATE", "yyyy-MM-dd HH:mm:ss");
    	bs.setFieldDateFormat("FAULT_DATE", "yyyy-MM-dd HH:mm:ss");
    	bs.setFieldDateFormat("APPLY_REPAIR_DATE", "yyyy-MM-dd HH:mm:ss");
    	bs.setFieldDateFormat("REPAIR_DATE", "yyyy-MM-dd HH:mm:ss");
    	bs.setFieldDic("CLAIM_TYPE", "SPDLX");
    	bs.setFieldDic("CLAIM_STATUS", "SPDZT");
    	return bs;
    }
    /**
     * 索赔单查询(办事处)
     * @param page 分页
     * @param user 用户
     * @param conditions 前台条件
     * @throws SQLException 
     */
    public BaseResultSet claimAgencySearch(PageManager page, User user, String conditions,String orgId) throws SQLException{
    	
    	String wheres = conditions;
    	wheres   +="AND SC.WORK_ID = SO.WORK_ID(+)\n"
    			+ " AND T.ORG_ID = SC.ORG_ID\n"
    			+ " AND TG.ORG_ID = T.PID\n"
    			+ " AND TG.ORG_ID="+orgId+" \n"
    			//+"  AND SC.STATUS="+DicConstant.YXBS_01+"\n"
    			+"  AND SC.CLAIM_STATUS >= "+DicConstant.SPDZT_02+""
    			+ " ORDER BY SC.APPLY_DATE,SC.REJECT_DATE";
    	page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT SO.WORK_NO,\n" );
    	sql.append("       SC.CLAIM_STATUS,\n" );
    	sql.append("       SC.CLAIM_NO,\n" );
    	sql.append("       SC.CLAIM_ID,\n" );
    	sql.append("       SC.CLAIM_TYPE,\n" );
    	sql.append("       T.ORG_ID,\n" );
    	sql.append("       T.CODE,\n" );
    	sql.append("       T.ONAME,\n" );
    	sql.append("       SC.APPLY_DATE,\n" );
    	sql.append("       SC.REJECT_DATE,\n" );
    	sql.append("       SC.VEHICLE_ID,\n" );
    	sql.append("       SC.VIN\n" );
    	sql.append("  FROM SE_BU_WORK_ORDER SO, SE_BU_CLAIM SC, TM_ORG T, TM_ORG TG");
    	bs=factory.select(sql.toString(), page);
    	bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
    	bs.setFieldDateFormat("REJECT_DATE", "yyyy-MM-dd HH:mm:ss");
    	bs.setFieldDic("CLAIM_TYPE", "SPDLX");
    	bs.setFieldDic("CLAIM_STATUS", "SPDZT");
    	return bs;
    }
    public QuerySet download(String conditions,User user) throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT SO.WORK_NO,\n" );
    	sql.append("       SC.CLAIM_NO,\n" );
    	sql.append("       SC.CLAIM_ID,\n" );
    	sql.append("       SC.CLAIM_TYPE,\n" );
    	sql.append("       T.ORG_ID,\n" );
    	sql.append("       T.CODE,\n" );
    	sql.append("       T.ONAME,\n" );
    	sql.append("       SC.APPLY_DATE,\n" );
    	sql.append("       SC.REJECT_DATE,\n" );
    	sql.append("       SC.VEHICLE_ID,\n" );
    	sql.append("       (SELECT TR.DIC_VALUE FROM DIC_TREE TR WHERE TR.ID = SC.CLAIM_STATUS) CLAIM_STATUS,\n" );
    	sql.append("       (SELECT TR.DIC_VALUE FROM DIC_TREE TR WHERE TR.ID = SC.CLAIM_TYPE) CLAIM_TYPE,\n" );
    	sql.append("       SC.VIN\n" );
    	sql.append("  FROM SE_BU_WORK_ORDER SO, SE_BU_CLAIM SC, TM_ORG T, TM_ORG TG\n" );
    	sql.append(" WHERE "+conditions+"\n" );
    	sql.append("   AND SC.WORK_ID = SO.WORK_ID(+)\n" );
    	sql.append("   AND T.ORG_ID = SC.ORG_ID\n" );
    	sql.append("   AND TG.ORG_ID = T.PID\n" );
    	sql.append("   AND TG.ORG_ID = "+user.getOrgId()+"\n" );
    	//sql.append("   AND SC.STATUS = 100201\n" );
    	sql.append("   AND SC.CLAIM_STATUS >= 301002\n" );
    	sql.append(" ORDER BY SC.APPLY_DATE, SC.REJECT_DATE");
		 return factory.select(null, sql.toString());
	 }
    public QuerySet oemDownload(String conditions) throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT SO.WORK_NO,\n" );
    	sql.append("       SC.CLAIM_NO,\n" );
    	sql.append("       (SELECT TR.DIC_VALUE FROM DIC_TREE TR WHERE TR.ID = SC.CLAIM_TYPE) CLAIM_TYPE,\n" );
    	sql.append("       (SELECT TR.DIC_VALUE FROM DIC_TREE TR WHERE TR.ID = SC.CLAIM_STATUS) CLAIM_STATUS,\n" );
    	sql.append("       SC.CLAIM_ID,\n" );
    	sql.append("       (SELECT CODE FROM TM_ORG G WHERE SC.ORG_ID = G.ORG_ID) ORG_CODE,\n" );
    	sql.append("       (SELECT G.ONAME FROM TM_ORG G WHERE SC.ORG_ID = G.ORG_ID) ORG_NAME,\n" );
    	sql.append("       SC.APPLY_DATE,\n" );
    	sql.append("       SC.REJECT_DATE,\n" );
    	sql.append("       SC.VEHICLE_ID,\n" );
    	sql.append("       SC.VIN,\n" );
    	sql.append("       SC.FAULT_DATE,\n" );
    	sql.append("       SC.APPLY_REPAIR_DATE,\n" );
    	sql.append("       SC.REPAIR_DATE,\n" );
    	sql.append("       G1.CODE,\n" );
    	sql.append("       G1.ONAME\n" );
    	sql.append("  FROM SE_BU_WORK_ORDER SO, SE_BU_CLAIM SC,TM_ORG G,TM_ORG G1\n" );
    	sql.append(" WHERE "+conditions+"\n" );
    	sql.append("   AND SC.WORK_ID = SO.WORK_ID(+)\n" );
    	sql.append("   AND SC.ORG_ID = G.ORG_ID \n" );
    	sql.append("   AND G.PID = G1.ORG_ID\n" );
    	//sql.append("   AND SC.STATUS = 100201\n" );
    	sql.append("   AND SC.CLAIM_STATUS >= 301002\n" );
    	sql.append(" ORDER BY SC.CLAIM_ID");
    	return factory.select(null, sql.toString());
    }
    public QuerySet dealerDownload(String conditions,User user) throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT SO.WORK_NO,\n" );
    	sql.append("       (SELECT TR.DIC_VALUE FROM DIC_TREE TR WHERE TR.ID = SC.CLAIM_TYPE) CLAIM_TYPE,\n" );
    	sql.append("       (SELECT TR.DIC_VALUE FROM DIC_TREE TR WHERE TR.ID = SC.CLAIM_STATUS) CLAIM_STATUS,\n" );
    	sql.append("       SC.CLAIM_NO,\n" );
    	sql.append("       SC.CLAIM_ID,\n" );
    	sql.append("       (SELECT CODE FROM TM_ORG G WHERE SC.ORG_ID = G.ORG_ID) ORG_CODE,\n" );
    	sql.append("       (SELECT G.ONAME FROM TM_ORG G WHERE SC.ORG_ID = G.ORG_ID) ORG_NAME,\n" );
    	sql.append("       SC.APPLY_DATE,\n" );
    	sql.append("       SC.REJECT_DATE,\n" );
    	sql.append("       SC.VEHICLE_ID,\n" );
    	sql.append("       SC.VIN\n" );
    	sql.append("  FROM SE_BU_WORK_ORDER SO, SE_BU_CLAIM SC\n" );
    	sql.append(" WHERE "+conditions+"\n" );
    	sql.append("   AND SC.WORK_ID = SO.WORK_ID(+)\n" );
    	sql.append("   AND SC.ORG_ID = "+user.getOrgId()+"\n" );
    	//sql.append("   AND SC.STATUS = 100201\n" );
    	sql.append(" ORDER BY SC.CLAIM_ID");
    	return factory.select(null, sql.toString());
    }
    
    /**
     * 索赔单费用查询
     * @param page
     * @param user
     * @param conditions
     * @return
     * @throws Exception
     */
    public BaseResultSet claimCostsSearch(PageManager page, User user, String conditions,String activityCode,String modelsCode,String engineType)throws Exception{
    	String orgType=user.getOrgDept().getOrgType();
    	String wheres =conditions;
    		   wheres += " AND T.CLAIM_ID = T1.CLAIM_ID(+)\n"+
    				     " AND T.CLAIM_ID = T2.CLAIM_ID(+)\n"+
    				     " AND T.CLAIM_STATUS ="+DicConstant.SPDZT_05+"  \n";
    		   if(null==activityCode||activityCode.equals("")){}else{
    			   wheres+=" AND T.ACTIVITY_ID IN ( SELECT ACTIVITY_ID FROM SE_BU_ACTIVITY WHERE ACTIVITY_CODE LIKE '%"+activityCode+"%')";
    		   }
    		   if(null==modelsCode||modelsCode.equals("")){}else{
    			   wheres+=" AND T.VIN IN( SELECT V.VIN FROM MAIN_VEHICLE V WHERE V.MODELS_CODE LIKE '%"+modelsCode+"%')";
    		   }
    		   if(null==activityCode||activityCode.equals("")){}else{
    			   wheres+=" AND T.VIN IN( SELECT V.VIN FROM MAIN_VEHICLE V WHERE V.ENGINE_TYPE LIKE '%"+engineType+"%')";
    		   }
    		    //服务站
    			if("200007".equals(orgType))
    	    	{
    	    		wheres += "  AND T.ORG_ID = "+user.getOrgId()+" \n";
    	    	} 
        page.setFilter(wheres);
        BaseResultSet bs =null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.CLAIM_ID,\n" );
    	sql.append("       T.CLAIM_NO,\n");
    	sql.append("       T.CLAIM_TYPE,\n");
    	sql.append("       T.ORG_ID ORG_NAME,\n");
    	sql.append("       T.ORG_ID ORG_CODE,\n");
    	sql.append("       T.VIN,\n");
    	sql.append("       T.G_TIMES,\n");
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
    	bs=factory.select(sql.toString(),page);
    	bs.setFieldDic("CLAIM_TYPE", "SPDLX");
    	bs.setFieldOrgDeptSimpleName("ORG_NAME");//简称
		bs.setFieldOrgDeptCode("ORG_CODE");
    	return bs;
    }
    
    /**
     * 初审费用导出
     * @param user
     * @param conditions
     * @return
     * @throws Exception
     */
    public QuerySet passDownload(String conditions,User user,String activityCode, String modelsCode,String engineType)throws Exception{
    	String orgType=user.getOrgDept().getOrgType();
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.CLAIM_ID,\n" );
		sql.append("       T.CLAIM_NO,\n");
		sql.append("       (SELECT D.DIC_VALUE FROM DIC_TREE D WHERE TO_CHAR(D.DIC_CODE) = TO_CHAR(T.CLAIM_TYPE)) CLAIM_TYPE,\n");
		sql.append("       T.VIN,\n");
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
		sql.append(" WHERE "+conditions+" \n");
		sql.append(" AND T.CLAIM_ID = T1.CLAIM_ID(+)\n");
		sql.append(" AND T.CLAIM_ID = T2.CLAIM_ID(+)\n");
		sql.append(" AND T.CLAIM_STATUS ="+DicConstant.SPDZT_05+" \n");
		//服务站
		if("200007".equals(orgType))
    	{
			sql.append("  AND T.ORG_ID = "+user.getOrgId()+" ");
    	} 
		if(null==activityCode||activityCode.equals("")){}else{
		   sql.append(" AND T.ACTIVITY_ID IN ( SELECT ACTIVITY_ID FROM SE_BU_ACTIVITY WHERE ACTIVITY_CODE LIKE '%"+activityCode+"%')\n ");
	    }
	    if(null==modelsCode||modelsCode.equals("")){}else{
		   sql.append(" AND T.VIN IN( SELECT V.VIN FROM MAIN_VEHICLE V WHERE V.MODELS_CODE LIKE '%"+modelsCode+"%')\n ");
	    }
	    if(null==activityCode||activityCode.equals("")){}else{
		   sql.append(" AND T.VIN IN( SELECT V.VIN FROM MAIN_VEHICLE V WHERE V.ENGINE_TYPE LIKE '%"+engineType+"%')\n ");
	    }
		return factory.select(null, sql.toString());
    }
    
    /**
     * 终审费用查询
     * @param page
     * @param user
     * @param conditions
     * @return
     * @throws Exception
     */
    public BaseResultSet claimFinalCostsSearch(PageManager page, User user, String conditions,String activityCode, String modelsCode,String engineType)throws Exception{
    	String orgType=user.getOrgDept().getOrgType();
    	String wheres =conditions;
		   wheres += " AND T.CLAIM_ID = T2.CLAIM_ID(+)\n"+
				     " AND T.CLAIM_STATUS ="+DicConstant.SPDZT_15+"  \n";
		   if(null==activityCode||activityCode.equals("")){}else{
			   wheres+=" AND T.ACTIVITY_ID IN ( SELECT ACTIVITY_ID FROM SE_BU_ACTIVITY WHERE ACTIVITY_CODE LIKE '%"+activityCode+"%')";
		   }
		   if(null==modelsCode||modelsCode.equals("")){}else{
			   wheres+=" AND T.VIN IN( SELECT V.VIN FROM MAIN_VEHICLE V WHERE V.MODELS_CODE LIKE '%"+modelsCode+"%')";
		   }
		   if(null==activityCode||activityCode.equals("")){}else{
			   wheres+=" AND T.VIN IN( SELECT V.VIN FROM MAIN_VEHICLE V WHERE V.ENGINE_TYPE LIKE '%"+engineType+"%')";
		   }
		   //服务站
			if("200007".equals(orgType))
	    	{
				wheres += " AND T.ORG_ID = "+user.getOrgId()+" ";
	    	} 
		page.setFilter(wheres);
		BaseResultSet bs =null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.CLAIM_ID,\n" );
    	sql.append("       T.CLAIM_NO,\n" );
    	sql.append("       T.CLAIM_TYPE,\n" );
    	sql.append("       T.ORG_ID ORG_CODE,\n" );
    	sql.append("       T.ORG_ID ORG_NAME,\n" );
    	sql.append("       T.VIN,\n" );
    	sql.append("       T.G_TIMES,\n");
    	sql.append("       NVL(T.OUT_AMOUNT, 0) OUT_AMOUNT, --外出总费用\n" );
    	sql.append("       NVL(T.MATERIAL_COSTS, 0) MATERIAL_COSTS, --材料总费用\n" );
    	sql.append("       NVL(T2.WORK_COSTS, 0) WORK_COSTS, --工时总费用\n" );
    	sql.append("       NVL(T.MAINTENANCE_COSTS, 0) MAINTENANCE_COSTS, -- 首保费\n" );
    	sql.append("       NVL(T.SERVICE_COST, 0) SERVICE_COST, --服务活动费\n" );
    	sql.append("       NVL(T.SAFE_COSTS, 0) SAFE_COSTS, --安全检查费用\n" );
    	sql.append("       NVL(T.TRAIN_COSTS, 0) TRAIN_COSTS, --售前培训费用\n" );
    	sql.append("       (NVL(T.OUT_AMOUNT, 0) + NVL(T.MATERIAL_COSTS, 0) +\n" );
    	sql.append("       NVL(T2.WORK_COSTS, 0) + NVL(T.MAINTENANCE_COSTS, 0) +\n" );
    	sql.append("       NVL(T.SERVICE_COST, 0) + NVL(T.SAFE_COSTS, 0) +\n" );
    	sql.append("       NVL(T.TRAIN_COSTS, 0)) CLAIM_AMOUNT --索赔单总费用\n" );
    	sql.append("  FROM SE_BU_CLAIM T,\n" );
    	sql.append("       (SELECT F.CLAIM_ID, SUM(F.WORK_COSTS) WORK_COSTS\n" );
    	sql.append("          FROM SE_BU_CLAIM_FAULT F\n" );
    	sql.append("         GROUP BY F.CLAIM_ID) T2");
    	bs=factory.select(sql.toString(), page);
    	bs.setFieldDic("CLAIM_TYPE", "SPDLX");
    	bs.setFieldOrgDeptSimpleName("ORG_NAME");//简称
		bs.setFieldOrgDeptCode("ORG_CODE");
    	return bs;
    }
    /**
     * 终审费用导出
     * @param conditions
     * @param user
     * @return
     * @throws Exception
     */
    public QuerySet finalDownload(String conditions,User user,String activityCode, String modelsCode,String engineType)throws Exception{
    	String orgType=user.getOrgDept().getOrgType();
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.CLAIM_ID,\n" );
		sql.append("       T.CLAIM_NO,\n" );
		sql.append("       (SELECT D.DIC_VALUE FROM DIC_TREE D WHERE TO_CHAR(D.DIC_CODE) = TO_CHAR(T.CLAIM_TYPE)) CLAIM_TYPE,\n" );
		sql.append("       T.VIN,\n" );
		sql.append("       NVL(T.OUT_AMOUNT, 0) OUT_AMOUNT, --外出总费用\n" );
		sql.append("       NVL(T.MATERIAL_COSTS, 0) MATERIAL_COSTS, --材料总费用\n" );
		sql.append("       NVL(T2.WORK_COSTS, 0) WORK_COSTS, --工时总费用\n" );
		sql.append("       NVL(T.MAINTENANCE_COSTS, 0) MAINTENANCE_COSTS, -- 首保费\n" );
		sql.append("       NVL(T.SERVICE_COST, 0) SERVICE_COST, --服务活动费\n" );
		sql.append("       NVL(T.SAFE_COSTS, 0) SAFE_COSTS, --安全检查费用\n" );
		sql.append("       NVL(T.TRAIN_COSTS, 0) TRAIN_COSTS, --售前培训费用\n" );
		sql.append("       (NVL(T.OUT_AMOUNT, 0) + NVL(T.MATERIAL_COSTS, 0) +\n" );
		sql.append("       NVL(T2.WORK_COSTS, 0) + NVL(T.MAINTENANCE_COSTS, 0) +\n" );
		sql.append("       NVL(T.SERVICE_COST, 0) + NVL(T.SAFE_COSTS, 0) +\n" );
		sql.append("       NVL(T.TRAIN_COSTS, 0)) CLAIM_AMOUNT --索赔单总费用\n" );
		sql.append("  FROM SE_BU_CLAIM T,\n" );
		sql.append("       (SELECT F.CLAIM_ID, SUM(F.WORK_COSTS) WORK_COSTS\n" );
		sql.append("          FROM SE_BU_CLAIM_FAULT F\n" );
		sql.append("         GROUP BY F.CLAIM_ID) T2 \n");
		sql.append(" WHERE "+conditions+" \n");
		sql.append(" AND T.CLAIM_ID = T2.CLAIM_ID(+) \n");
		sql.append(" AND T.CLAIM_STATUS ="+DicConstant.SPDZT_15+"  \n ");
		//服务站
		if("200007".equals(orgType))
    	{
			sql.append(" AND T.ORG_ID = "+user.getOrgId()+"");
    	} 
		if(null==activityCode||activityCode.equals("")){}else{
		   sql.append(" AND T.ACTIVITY_ID IN ( SELECT ACTIVITY_ID FROM SE_BU_ACTIVITY WHERE ACTIVITY_CODE LIKE '%"+activityCode+"%')\n ");
	    }
	    if(null==modelsCode||modelsCode.equals("")){}else{
		   sql.append(" AND T.VIN IN( SELECT V.VIN FROM MAIN_VEHICLE V WHERE V.MODELS_CODE LIKE '%"+modelsCode+"%')\n ");
	    }
	    if(null==activityCode||activityCode.equals("")){}else{
		   sql.append(" AND T.VIN IN( SELECT V.VIN FROM MAIN_VEHICLE V WHERE V.ENGINE_TYPE LIKE '%"+engineType+"%')\n ");
	    }
		return factory.select(null, sql.toString());
    }
}
