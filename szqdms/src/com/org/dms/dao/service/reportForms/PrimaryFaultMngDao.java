package com.org.dms.dao.service.reportForms;

import java.util.HashMap;

import com.org.framework.base.*;
import com.org.framework.common.*;
import com.org.mvc.context.ActionContext;

/**
 * @Title: szqdms
 * @description: 售后报表——初期故障率统计表
 * @throws Exception 设定文件
 * @auther fanpeng
 * @date 2014年01月05日 
 */
public class PrimaryFaultMngDao extends BaseDAO
{
    //定义instance
    public  static final PrimaryFaultMngDao getInstance(ActionContext atx)
    {
        PrimaryFaultMngDao dao = new PrimaryFaultMngDao();
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
    	sql.append("SELECT B.YEARMONTH,B.CX,FAULTNUMBER,TOTAL_SALENUMBER,ROUND(FAULTNUMBER/TOTAL_SALENUMBER,2) AS GZL\n" );
    	sql.append("FROM\n" );
    	sql.append("(SELECT A.YEARMONTH,\n" );
    	sql.append("       DECODE(A.CX,1,'载货',2,'越野',3,'自卸',4,'牵引',5,'特种','') CX,\n" );
    	sql.append("       --查询各车型月销售总量\n" );
    	sql.append("       (SELECT COUNT(V.VIN)\n" );
    	sql.append("          FROM MAIN_VEHICLE V\n" );
    	sql.append("         WHERE SUBSTR(V.MODELS_CODE, 3, 1) = A.CX\n" );
    	sql.append("           AND TO_CHAR(V.BUY_DATE, 'YYYY-MM') = A.YEARMONTH\n" );
    	sql.append("          GROUP BY (SUBSTR(V.MODELS_CODE, 3, 1), TO_CHAR(V.BUY_DATE, 'YYYY-MM')))\n" );
    	sql.append("        AS TOTAL_SALENUMBER,\n" );
    	sql.append("        SUM(A.EACH_FAULTNUMBER) AS FAULTNUMBER  --故障次数总和\n" );
    	sql.append("  FROM\n" );
    	sql.append("      (SELECT T.VIN,\n" );
    	sql.append("             T.CLAIM_NO,\n" );
    	sql.append("             M.MODELS_CODE,\n" );
    	sql.append("             SUBSTR(M.MODELS_CODE, 3, 1)  AS  CX,               --车型\n" );
    	sql.append("             TO_CHAR(M.BUY_DATE, 'YYYY-MM')  AS  YEARMONTH,     --设置销售日期为年月格式\n" );
    	sql.append("             M.BUY_DATE,                                        --销售日期\n" );
    	sql.append("             M.MILEAGE,\n" );
    	sql.append("             (SELECT COUNT(1) FROM SE_BU_CLAIM_FAULT F WHERE F.CLAIM_ID=T.CLAIM_ID GROUP BY F.CLAIM_ID) AS EACH_FAULTNUMBER  --每个索赔单故障次数\n" );
    	sql.append("        FROM SE_BU_CLAIM       T,\n" );
    	sql.append("             MAIN_VEHICLE      M\n" );
    	sql.append("       WHERE T.VIN = M.VIN\n" );
    	sql.append("         AND T.REPAIR_DATE<=ADD_MONTHS(M.BUY_DATE, +5)    --统计销售日期5个月内数据\n" );
    	sql.append("         AND T.CLAIM_TYPE =301401                         --索赔单状态为正常保修\n" );
    	sql.append("         AND M.BUY_DATE IS NOT NULL\n" );
    	sql.append("         --4:牵引车统计30000公里内，其他统计15000公里内\n" );
    	sql.append("         AND M.MILEAGE<=DECODE(SUBSTR(M.MODELS_CODE, 3, 1),'4',30000,15000)\n" );
    	sql.append("         AND TO_CHAR(M.BUY_DATE,'YYYY-MM')>='"+beginDate+"'\n" );
    	sql.append("         AND TO_CHAR(M.BUY_DATE,'YYYY-MM')<='"+endDate+"'\n" );
    	sql.append("         ) A\n" );
    	sql.append("       GROUP BY (A.CX, A.YEARMONTH)\n" );
    	sql.append("       ) B\n" );
    	sql.append("ORDER BY  B.YEARMONTH,B.CX");
    	
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
    
    //下载(导出)
    public QuerySet download(String conditions, User user, HashMap<String,String> hm) throws Exception
    {	 
    	String beginDate=hm.get("BEGIN_DATE");
    	String endDate=hm.get("END_DATE");
    	
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT ROWNUM,YEARMONTH,CX,FAULTNUMBER,TOTAL_SALENUMBER,GZL FROM\n" );
    	sql.append("(SELECT B.YEARMONTH,B.CX,FAULTNUMBER,TOTAL_SALENUMBER,ROUND(FAULTNUMBER/TOTAL_SALENUMBER,2) AS GZL\n" );
    	sql.append("FROM\n" );
    	sql.append("(SELECT A.YEARMONTH,\n" );
    	sql.append("       DECODE(A.CX,1,'载货车',2,'越野车',3,'自卸车',4,'牵引车',5,'特种车','') CX,\n" );
    	sql.append("       --查询各车型月销售总量\n" );
    	sql.append("       (SELECT COUNT(V.VIN)\n" );
    	sql.append("          FROM MAIN_VEHICLE V\n" );
    	sql.append("         WHERE SUBSTR(V.MODELS_CODE, 3, 1) = A.CX\n" );
    	sql.append("           AND TO_CHAR(V.BUY_DATE, 'YYYY-MM') = A.YEARMONTH\n" );
    	sql.append("          GROUP BY (SUBSTR(V.MODELS_CODE, 3, 1), TO_CHAR(V.BUY_DATE, 'YYYY-MM')))\n" );
    	sql.append("        AS TOTAL_SALENUMBER,\n" );
    	sql.append("        SUM(A.EACH_FAULTNUMBER) AS FAULTNUMBER  --故障次数总和\n" );
    	sql.append("  FROM\n" );
    	sql.append("      (SELECT T.VIN,\n" );
    	sql.append("             T.CLAIM_NO,\n" );
    	sql.append("             M.MODELS_CODE,\n" );
    	sql.append("             SUBSTR(M.MODELS_CODE, 3, 1)  AS  CX,               --车型\n" );
    	sql.append("             TO_CHAR(M.BUY_DATE, 'YYYY-MM')  AS  YEARMONTH,     --设置销售日期为年月格式\n" );
    	sql.append("             M.BUY_DATE,                                        --销售日期\n" );
    	sql.append("             M.MILEAGE,\n" );
    	sql.append("             (SELECT COUNT(1) FROM SE_BU_CLAIM_FAULT F WHERE F.CLAIM_ID=T.CLAIM_ID GROUP BY F.CLAIM_ID) AS EACH_FAULTNUMBER  --每个索赔单故障次数\n" );
    	sql.append("        FROM SE_BU_CLAIM       T,\n" );
    	sql.append("             MAIN_VEHICLE      M\n" );
    	sql.append("       WHERE T.VIN = M.VIN\n" );
    	sql.append("         AND T.REPAIR_DATE<=ADD_MONTHS(M.BUY_DATE, +5)    --统计销售日期5个月内数据\n" );
    	sql.append("         AND T.CLAIM_TYPE =301401                         --索赔单状态为正常保修\n" );
    	sql.append("         AND M.BUY_DATE IS NOT NULL\n" );
    	sql.append("         --4:牵引车统计30000公里内，其他统计15000公里内\n" );
    	sql.append("         AND M.MILEAGE<=DECODE(SUBSTR(M.MODELS_CODE, 3, 1),'4',30000,15000)\n" );
    	sql.append("         AND TO_CHAR(M.BUY_DATE,'YYYY-MM')>='"+beginDate+"'\n" );
    	sql.append("         AND TO_CHAR(M.BUY_DATE,'YYYY-MM')<='"+endDate+"'\n" );
    	sql.append("         ) A\n" );
    	sql.append("       GROUP BY (A.CX, A.YEARMONTH)\n" );
    	sql.append("       ) B\n" );
    	sql.append("ORDER BY  B.YEARMONTH,B.CX)");

    	return factory.select(null, sql.toString());
    }
}