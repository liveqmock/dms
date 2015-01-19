package com.org.dms.dao.service.reportForms;

import java.util.HashMap;

import com.org.framework.base.*;
import com.org.framework.common.*;
import com.org.mvc.context.ActionContext;

/**
 * @Title: szqdms
 * @description: 售后报表——单车服务费统计表
 * @throws Exception 设定文件
 * @auther fanpeng
 * @date 2014年01月05日 
 */
public class EachVehicleServiceCostMngDao extends BaseDAO
{
    //定义instance
    public  static final EachVehicleServiceCostMngDao getInstance(ActionContext atx)
    {
        EachVehicleServiceCostMngDao dao = new EachVehicleServiceCostMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

	/**
	 * 报表查询
	 * @param  page
	 * @param  code
	 * @param  conditions
	 * @return
	 * @throws Exception
	 */
    public BaseResultSet search(PageManager page, User user, String conditions, HashMap<String,String> hm) throws Exception
    {
    	String beginDate=hm.get("BEGIN_DATE");
    	String endDate=hm.get("END_DATE");
    	//定义返回结果集
    	BaseResultSet bs = null;
    	
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT YEARMONTH,CX,TOTAL_SALENUMBER,ONE,TWO,THREE,FOUR,FIVE,SIX,(ONE+TWO+THREE+FOUR+FIVE+SIX) AS TOTLECOST,ROUND((ONE+TWO+THREE+FOUR+FIVE+SIX)/TOTAL_SALENUMBER,2) AS DCWXCB\n" );
    	sql.append("FROM\n" );
    	sql.append("(SELECT DECODE(A.CX,1,'载货',2,'越野',3,'自卸',4,'牵引',5,'特种','') CX,\n" );
    	sql.append("       A.YEARMONTH,\n" );
    	sql.append("       SUM(CASE WHEN A.REPAIR_DATE<=ADD_MONTHS(A.BUY_DATE,1) THEN A.TOTAL_COSTS ELSE 0 END) ONE,\n" );
    	sql.append("       SUM(CASE WHEN A.REPAIR_DATE>ADD_MONTHS(A.BUY_DATE,1) AND A.REPAIR_DATE<=ADD_MONTHS(A.BUY_DATE,2) THEN A.TOTAL_COSTS ELSE 0 END) TWO,\n" );
    	sql.append("       SUM(CASE WHEN A.REPAIR_DATE>ADD_MONTHS(A.BUY_DATE,2) AND A.REPAIR_DATE<=ADD_MONTHS(A.BUY_DATE,3) THEN A.TOTAL_COSTS ELSE 0 END) THREE,\n" );
    	sql.append("       SUM(CASE WHEN A.REPAIR_DATE>ADD_MONTHS(A.BUY_DATE,3) AND A.REPAIR_DATE<=ADD_MONTHS(A.BUY_DATE,4) THEN A.TOTAL_COSTS ELSE 0 END) FOUR,\n" );
    	sql.append("       SUM(CASE WHEN A.REPAIR_DATE>ADD_MONTHS(A.BUY_DATE,4) AND A.REPAIR_DATE<=ADD_MONTHS(A.BUY_DATE,5) THEN A.TOTAL_COSTS ELSE 0 END) FIVE,\n" );
    	sql.append("       SUM(CASE WHEN A.REPAIR_DATE>ADD_MONTHS(A.BUY_DATE,5) AND A.REPAIR_DATE<=ADD_MONTHS(A.BUY_DATE,6) THEN A.TOTAL_COSTS ELSE 0 END) SIX,\n" );
    	sql.append("       --查询各车型月销售总量\n" );
    	sql.append("       (SELECT COUNT(V.VIN)\n" );
    	sql.append("          FROM MAIN_VEHICLE V\n" );
    	sql.append("         WHERE SUBSTR(V.MODELS_CODE, 3, 1) = A.CX\n" );
    	sql.append("           AND TO_CHAR(V.BUY_DATE, 'YYYY-MM') = A.YEARMONTH\n" );
    	sql.append("       GROUP BY (SUBSTR(V.MODELS_CODE, 3, 1), TO_CHAR(V.BUY_DATE, 'YYYY-MM')))\n" );
    	sql.append("       AS TOTAL_SALENUMBER\n" );
    	sql.append("  FROM\n" );
    	sql.append("      (SELECT T.VIN,\n" );
    	sql.append("             M.MODELS_CODE,\n" );
    	sql.append("             SUBSTR(M.MODELS_CODE, 3, 1)  AS  CX,               --车型\n" );
    	sql.append("             TO_CHAR(M.BUY_DATE, 'YYYY-MM')  AS  YEARMONTH,     --设置销售日期为年月格式\n" );
    	sql.append("             M.BUY_DATE,                                        --销售日期\n" );
    	sql.append("             T.REPAIR_DATE,                                     --检修日期\n" );
    	sql.append("             NVL(T.MATERIAL_COSTS, 0) +                         --材料费\n" );
    	sql.append("             (SELECT SUM(G.WORK_COSTS) FROM  SE_BU_CLAIM_FAULT G WHERE T.CLAIM_ID = G.CLAIM_ID GROUP BY G.CLAIM_ID) +      --总工时费\n" );
    	sql.append("             NVL((SELECT NVL(O.TRAVEL_COSTS, 0)+NVL(O.SEVEH_COSTS, 0) + NVL(O.SEC_VEH_COSTS, 0) FROM  SE_BU_CLAIM_OUT O WHERE T.CLAIM_ID = O.CLAIM_ID),0)  --差旅费与服务车费\n" );
    	sql.append("             AS TOTAL_COSTS\n" );
    	sql.append("        FROM SE_BU_CLAIM       T,\n" );
    	sql.append("             MAIN_VEHICLE      M\n" );
    	sql.append("       WHERE T.VIN = M.VIN\n" );
    	sql.append("         AND T.CLAIM_TYPE IN (301401,301404)      --索赔单类型为正常保修，售前检查\n" );
    	sql.append("         AND T.CLAIM_STATUS IN (301005,301015)    --索赔单状态为初审通过，终审通过\n" );
    	sql.append("         AND M.BUY_DATE IS NOT NULL\n" );
    	sql.append("         AND TO_CHAR(M.BUY_DATE,'YYYY-MM')>='"+beginDate+"'\n" );
    	sql.append("         AND TO_CHAR(M.BUY_DATE,'YYYY-MM')<='"+endDate+"'\n" );
    	sql.append("         ) A\n" );
    	sql.append("   GROUP BY (A.CX, A.YEARMONTH)\n" );
    	sql.append("   )B\n" );
    	sql.append("   ORDER BY  YEARMONTH,CX");
    	
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
    
    //下载(导出)
    public QuerySet download(String conditions, User user, HashMap<String,String> hm) throws Exception
    {	 
    	String beginDate=hm.get("BEGIN_DATE");
    	String endDate=hm.get("END_DATE");
    	
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT ROWNUM,YEARMONTH,CX,TOTAL_SALENUMBER,ONE,TWO,THREE,FOUR,FIVE,SIX,TOTLECOST,DCWXCB FROM\n" );
    	sql.append("(SELECT YEARMONTH,CX,TOTAL_SALENUMBER,ONE,TWO,THREE,FOUR,FIVE,SIX,(ONE+TWO+THREE+FOUR+FIVE+SIX) AS TOTLECOST,ROUND((ONE+TWO+THREE+FOUR+FIVE+SIX)/TOTAL_SALENUMBER,2) AS DCWXCB\n" );
    	sql.append("FROM\n" );
    	sql.append("(SELECT DECODE(A.CX,1,'载货车',2,'越野车',3,'自卸车',4,'牵引车',5,'特种车','') CX,\n" );
    	sql.append("       A.YEARMONTH,\n" );
    	sql.append("       SUM(CASE WHEN A.REPAIR_DATE<=ADD_MONTHS(A.BUY_DATE,1) THEN A.TOTAL_COSTS ELSE 0 END) ONE,\n" );
    	sql.append("       SUM(CASE WHEN A.REPAIR_DATE>ADD_MONTHS(A.BUY_DATE,1) AND A.REPAIR_DATE<=ADD_MONTHS(A.BUY_DATE,2) THEN A.TOTAL_COSTS ELSE 0 END) TWO,\n" );
    	sql.append("       SUM(CASE WHEN A.REPAIR_DATE>ADD_MONTHS(A.BUY_DATE,2) AND A.REPAIR_DATE<=ADD_MONTHS(A.BUY_DATE,3) THEN A.TOTAL_COSTS ELSE 0 END) THREE,\n" );
    	sql.append("       SUM(CASE WHEN A.REPAIR_DATE>ADD_MONTHS(A.BUY_DATE,3) AND A.REPAIR_DATE<=ADD_MONTHS(A.BUY_DATE,4) THEN A.TOTAL_COSTS ELSE 0 END) FOUR,\n" );
    	sql.append("       SUM(CASE WHEN A.REPAIR_DATE>ADD_MONTHS(A.BUY_DATE,4) AND A.REPAIR_DATE<=ADD_MONTHS(A.BUY_DATE,5) THEN A.TOTAL_COSTS ELSE 0 END) FIVE,\n" );
    	sql.append("       SUM(CASE WHEN A.REPAIR_DATE>ADD_MONTHS(A.BUY_DATE,5) AND A.REPAIR_DATE<=ADD_MONTHS(A.BUY_DATE,6) THEN A.TOTAL_COSTS ELSE 0 END) SIX,\n" );
    	sql.append("       --查询各车型月销售总量\n" );
    	sql.append("       (SELECT COUNT(V.VIN)\n" );
    	sql.append("          FROM MAIN_VEHICLE V\n" );
    	sql.append("         WHERE SUBSTR(V.MODELS_CODE, 3, 1) = A.CX\n" );
    	sql.append("           AND TO_CHAR(V.BUY_DATE, 'YYYY-MM') = A.YEARMONTH\n" );
    	sql.append("       GROUP BY (SUBSTR(V.MODELS_CODE, 3, 1), TO_CHAR(V.BUY_DATE, 'YYYY-MM')))\n" );
    	sql.append("       AS TOTAL_SALENUMBER\n" );
    	sql.append("  FROM\n" );
    	sql.append("      (SELECT T.VIN,\n" );
    	sql.append("             M.MODELS_CODE,\n" );
    	sql.append("             SUBSTR(M.MODELS_CODE, 3, 1)  AS  CX,               --车型\n" );
    	sql.append("             TO_CHAR(M.BUY_DATE, 'YYYY-MM')  AS  YEARMONTH,     --设置销售日期为年月格式\n" );
    	sql.append("             M.BUY_DATE,                                        --销售日期\n" );
    	sql.append("             T.REPAIR_DATE,                                     --检修日期\n" );
    	sql.append("             NVL(T.MATERIAL_COSTS, 0) +                         --材料费\n" );
    	sql.append("             (SELECT SUM(G.WORK_COSTS) FROM  SE_BU_CLAIM_FAULT G WHERE T.CLAIM_ID = G.CLAIM_ID GROUP BY G.CLAIM_ID) +      --总工时费\n" );
    	sql.append("             NVL((SELECT NVL(O.TRAVEL_COSTS, 0)+NVL(O.SEVEH_COSTS, 0) + NVL(O.SEC_VEH_COSTS, 0) FROM  SE_BU_CLAIM_OUT O WHERE T.CLAIM_ID = O.CLAIM_ID),0)  --差旅费与服务车费\n" );
    	sql.append("             AS TOTAL_COSTS\n" );
    	sql.append("        FROM SE_BU_CLAIM       T,\n" );
    	sql.append("             MAIN_VEHICLE      M\n" );
    	sql.append("       WHERE T.VIN = M.VIN\n" );
    	sql.append("         AND T.CLAIM_TYPE IN (301401,301404)      --索赔单类型为正常保修，售前检查\n" );
    	sql.append("         AND T.CLAIM_STATUS IN (301005,301015)    --索赔单状态为初审通过，终审通过\n" );
    	sql.append("         AND M.BUY_DATE IS NOT NULL\n" );
    	sql.append("         AND TO_CHAR(M.BUY_DATE,'YYYY-MM')>='"+beginDate+"'\n" );
    	sql.append("         AND TO_CHAR(M.BUY_DATE,'YYYY-MM')<='"+endDate+"'\n" );
    	sql.append("         ) A\n" );
    	sql.append("   GROUP BY (A.CX, A.YEARMONTH)\n" );
    	sql.append("   )B\n" );
    	sql.append("   ORDER BY  YEARMONTH,CX)");

    	return factory.select(null, sql.toString());
    }
}