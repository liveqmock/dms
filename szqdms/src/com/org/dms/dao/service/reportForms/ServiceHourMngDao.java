package com.org.dms.dao.service.reportForms;

import com.org.framework.base.*;
import com.org.framework.common.*;
import com.org.mvc.context.ActionContext;

/**
 * @Title: szqdms
 * @description: 售后报表——服务维修响应时间（服务站/办事处）统计表
 * @throws Exception 设定文件
 * @auther fanpeng
 * @date 2014年12月15日 
 */
public class ServiceHourMngDao extends BaseDAO
{
    //定义instance
    public  static final ServiceHourMngDao getInstance(ActionContext atx)
    {
        ServiceHourMngDao dao = new ServiceHourMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

	/**
	 * 报表查询（按服务站）
	 * @param  page
	 * @param  code
	 * @param  conditions
	 * @return
	 * @throws Exception
	 */
    public BaseResultSet search_fwz(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres += "GROUP BY (T.ORG_ID, T.DEALER, T.BSCID, T.BSC, T.YEARMONTH)" ;
    	wheres += "ORDER BY T.BSCID, T.ORG_ID,T.YEARMONTH" ;
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.ORG_ID, T.DEALER, T.BSCID, T.BSC, T.YEARMONTH,\n" );
    	sql.append("\n" );
    	sql.append("--站外本月，上月，去年上月维修响应时间\n" );
    	sql.append("       SUM(CASE WHEN T.IF_OUT = 100101 THEN NVL(T.HOUR,0) ELSE 0 END) HOUR_OUT,\n" );
    	sql.append("       SUM(CASE WHEN T.IF_OUT = 100101 THEN NVL(T.LASTMONTH_HOUR,0) ELSE 0 END) LASTMONTH_HOUR_OUT,\n" );
    	sql.append("       SUM(CASE WHEN T.IF_OUT = 100101 THEN NVL(T.LASTYEAR_HOUR,0) ELSE 0 END) LASTYEAR_HOUR_OUT,\n" );
    	sql.append("--站外环比\n" );
    	sql.append("       ROUND((SUM(CASE WHEN T.IF_OUT = 100101 THEN NVL(T.HOUR,0) ELSE 0 END)-SUM(CASE WHEN T.IF_OUT = 100101 THEN NVL(T.LASTMONTH_HOUR,0) ELSE 0 END))/\n" );
    	sql.append("             (CASE WHEN (SUM(CASE WHEN T.IF_OUT = 100101 THEN NVL(T.LASTMONTH_HOUR,0) ELSE 0 END)) = 0 THEN 1\n" );
    	sql.append("                   ELSE (SUM(CASE WHEN T.IF_OUT = 100101 THEN NVL(T.LASTMONTH_HOUR,0) END)) END),2) MONTH_GROUTH_OUT,\n" );
    	sql.append("--站外同比\n" );
    	sql.append("       ROUND((SUM(CASE WHEN T.IF_OUT = 100101 THEN NVL(T.HOUR,0) ELSE 0 END)-SUM(CASE WHEN T.IF_OUT = 100101 THEN NVL(T.LASTYEAR_HOUR,0) ELSE 0 END))/\n" );
    	sql.append("             (CASE WHEN (SUM(CASE WHEN T.IF_OUT = 100101 THEN NVL(T.LASTYEAR_HOUR,0) ELSE 0 END)) = 0 THEN 1\n" );
    	sql.append("                   ELSE (SUM(CASE WHEN T.IF_OUT = 100101 THEN NVL(T.LASTYEAR_HOUR,0) END)) END),2) YEAR_GROUTH_OUT,\n" );
    	sql.append("\n" );
    	sql.append("--站内环比\n" );
    	sql.append("       ROUND((SUM(CASE WHEN T.IF_OUT = 100102 THEN NVL(T.HOUR,0) ELSE 0 END)-SUM(CASE WHEN T.IF_OUT = 100102 THEN NVL(T.LASTMONTH_HOUR,0) ELSE 0 END))/\n" );
    	sql.append("       		 (CASE WHEN (SUM(CASE WHEN T.IF_OUT = 100102 THEN NVL(T.LASTMONTH_HOUR,0) ELSE 0 END)) = 0 THEN 1\n" );
    	sql.append("                   ELSE (SUM(CASE WHEN T.IF_OUT = 100102 THEN NVL(T.LASTMONTH_HOUR,0) END)) END),2) MONTH_GROUTH_IN,\n" );
    	sql.append("--站内同比\n" );
    	sql.append("       ROUND((SUM(CASE WHEN T.IF_OUT = 100102 THEN NVL(T.HOUR,0) ELSE 0 END)-SUM(CASE WHEN T.IF_OUT = 100102 THEN NVL(T.LASTYEAR_HOUR,0) ELSE 0 END))/\n" );
    	sql.append("       		 (CASE WHEN (SUM(CASE WHEN T.IF_OUT = 100102 THEN NVL(T.LASTYEAR_HOUR,0) ELSE 0 END)) = 0 THEN 1\n" );
    	sql.append("             	   ELSE (SUM(CASE WHEN T.IF_OUT = 100102 THEN NVL(T.LASTYEAR_HOUR,0) END)) END),2) YEAR_GROUTH_IN,\n" );
    	sql.append("\n" );
    	sql.append("--站内本月，上月，去年上月维修响应时间\n" );
    	sql.append("       SUM(CASE WHEN T.IF_OUT = 100102 THEN NVL(T.HOUR,0) ELSE 0 END) HOUR_IN,\n" );
    	sql.append("       SUM(CASE WHEN T.IF_OUT = 100102 THEN NVL(T.LASTMONTH_HOUR,0) ELSE 0 END) LASTMONTH_HOUR_IN,\n" );
    	sql.append("       SUM(CASE WHEN T.IF_OUT = 100102 THEN NVL(T.LASTYEAR_HOUR,0) ELSE 0 END) LASTYEAR_HOUR_IN\n" );
    	sql.append("  FROM SE_BU_SERVICE_HOUR T\n" );
    	
    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
    
    /**
	 * 报表查询（按办事处）
	 * @param  page
	 * @param  code
	 * @param  conditions
	 * @return
	 * @throws Exception
	 */
    public BaseResultSet search_bsc(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres += "ORDER BY A.YEARMONTH,A.BSCID" ;
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT A.BSCID,A.BSC,A.YEARMONTH,\n" );
    	sql.append("    ROUND(A.HOUR_OUT,2) HOUR_OUT,\n" );
    	sql.append("    ROUND((HOUR_OUT-LASTMONTH_HOUR_OUT)/\n" );
    	sql.append("   (CASE WHEN LASTMONTH_HOUR_OUT = 0 THEN 1  ELSE LASTMONTH_HOUR_OUT END),2) MONTH_GROUTH_OUT,\n" );
    	sql.append("    ROUND((HOUR_OUT-LASTYEAR_HOUR_OUT)/\n" );
    	sql.append("   (CASE WHEN LASTYEAR_HOUR_OUT = 0 THEN 1  ELSE LASTMONTH_HOUR_OUT END),2) YEAR_GROUTH_OUT,\n" );
    	sql.append("    ROUND(A.HOUR_IN,2) HOUR_IN,\n" );
    	sql.append("    ROUND((HOUR_IN-LASTMONTH_HOUR_IN)/\n" );
    	sql.append("   (CASE WHEN LASTMONTH_HOUR_IN = 0 THEN 1  ELSE LASTMONTH_HOUR_IN END),2) MONTH_GROUTH_IN,\n" );
    	sql.append("    ROUND((HOUR_IN-LASTYEAR_HOUR_IN)/\n" );
    	sql.append("   (CASE WHEN LASTYEAR_HOUR_IN = 0 THEN 1  ELSE LASTMONTH_HOUR_IN END),2) YEAR_GROUTH_IN\n" );
    	sql.append("\n" );
    	sql.append("FROM (SELECT T.BSCID, T.BSC, T.YEARMONTH,\n" );
    	sql.append("   --办事处站外\n" );
    	sql.append("   ((CASE WHEN T.IF_OUT = 100101 THEN SUM(T.HOUR) ELSE 0 END)/\n" );
    	sql.append("   (CASE WHEN T.IF_OUT = 100101 THEN COUNT(T.ORG_ID) ELSE 1 END)) AS HOUR_OUT,           --本月平均时间\n" );
    	sql.append("\n" );
    	sql.append("   ((CASE WHEN T.IF_OUT = 100101 THEN SUM(T.LASTMONTH_HOUR) ELSE 0 END)/\n" );
    	sql.append("   (CASE WHEN T.IF_OUT = 100101 THEN COUNT(T.ORG_ID) ELSE 1 END)) AS LASTMONTH_HOUR_OUT, --上月平均时间\n" );
    	sql.append("\n" );
    	sql.append("   ((CASE WHEN T.IF_OUT = 100101 THEN SUM(T.LASTYEAR_HOUR) ELSE 0 END)/\n" );
    	sql.append("   (CASE WHEN T.IF_OUT = 100101 THEN COUNT(T.ORG_ID) ELSE 1 END)) AS LASTYEAR_HOUR_OUT,  --去年本月平均时间\n" );
    	sql.append("\n" );
    	sql.append("   --办事处站内\n" );
    	sql.append("   ((SELECT (CASE WHEN X.IF_OUT = 100102 THEN SUM(X.HOUR) ELSE 0 END) FROM SE_BU_SERVICE_HOUR X WHERE X.BSCID=T.BSCID AND X.YEARMONTH=T.YEARMONTH AND X.IF_OUT=100102 GROUP BY ( X.BSCID, X.BSC, X.YEARMONTH, X.IF_OUT))/\n" );
    	sql.append("   (SELECT (CASE WHEN X.IF_OUT = 100102 THEN COUNT(X.ORG_ID) ELSE 1 END) FROM SE_BU_SERVICE_HOUR X WHERE X.BSCID=T.BSCID AND X.YEARMONTH=T.YEARMONTH AND X.IF_OUT=100102 GROUP BY ( X.BSCID, X.BSC, X.YEARMONTH, X.IF_OUT))) AS HOUR_IN,           --本月平均时间\n" );
    	sql.append("\n" );
    	sql.append("   ((SELECT (CASE WHEN X.IF_OUT = 100102 THEN SUM(X.LASTMONTH_HOUR) ELSE 0 END) FROM SE_BU_SERVICE_HOUR X WHERE X.BSCID=T.BSCID AND X.YEARMONTH=T.YEARMONTH AND X.IF_OUT=100102 GROUP BY ( X.BSCID, X.BSC, X.YEARMONTH, X.IF_OUT))/\n" );
    	sql.append("   (SELECT (CASE WHEN X.IF_OUT = 100102 THEN COUNT(X.ORG_ID) ELSE 1 END) FROM SE_BU_SERVICE_HOUR X WHERE X.BSCID=T.BSCID AND X.YEARMONTH=T.YEARMONTH AND X.IF_OUT=100102 GROUP BY ( X.BSCID, X.BSC, X.YEARMONTH, X.IF_OUT))) AS LASTMONTH_HOUR_IN, --上月平均时间\n" );
    	sql.append("\n" );
    	sql.append("   ((SELECT (CASE WHEN X.IF_OUT = 100102 THEN SUM(X.LASTYEAR_HOUR) ELSE 0 END) FROM SE_BU_SERVICE_HOUR X WHERE X.BSCID=T.BSCID AND X.YEARMONTH=T.YEARMONTH AND X.IF_OUT=100102 GROUP BY ( X.BSCID, X.BSC, X.YEARMONTH, X.IF_OUT))/\n" );
    	sql.append("   (SELECT (CASE WHEN X.IF_OUT = 100102 THEN COUNT(X.ORG_ID) ELSE 1 END) FROM SE_BU_SERVICE_HOUR X WHERE X.BSCID=T.BSCID AND X.YEARMONTH=T.YEARMONTH AND X.IF_OUT=100102 GROUP BY ( X.BSCID, X.BSC, X.YEARMONTH, X.IF_OUT))) AS LASTYEAR_HOUR_IN   --去年本月平均时间\n" );
    	sql.append("  FROM SE_BU_SERVICE_HOUR T\n" );
    	sql.append("  WHERE T.IF_OUT = 100101\n" );
    	sql.append("  GROUP BY ( T.BSCID, T.BSC, T.YEARMONTH, T.IF_OUT)) A\n" );
    	
    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
   
    //下载(导出)服务站
    public QuerySet download_fwz(String conditions, User user) throws Exception
    {	    	

    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT ROWNUM, ORG_ID, DEALER, BSCID, BSC, YEARMONTH, HOUR_OUT, MONTH_GROUTH_OUT, YEAR_GROUTH_OUT, HOUR_IN, MONTH_GROUTH_IN, YEAR_GROUTH_IN FROM\n" );
    	sql.append("(SELECT T.ORG_ID, T.DEALER, T.BSCID, T.BSC, T.YEARMONTH,\n" );
    	sql.append("\n" );
    	sql.append("--站外本月，上月，去年上月维修响应时间\n" );
    	sql.append("       SUM(CASE WHEN T.IF_OUT = 100101 THEN NVL(T.HOUR,0) ELSE 0 END) HOUR_OUT,\n" );
    	sql.append("       SUM(CASE WHEN T.IF_OUT = 100101 THEN NVL(T.LASTMONTH_HOUR,0) ELSE 0 END) LASTMONTH_HOUR_OUT,\n" );
    	sql.append("       SUM(CASE WHEN T.IF_OUT = 100101 THEN NVL(T.LASTYEAR_HOUR,0) ELSE 0 END) LASTYEAR_HOUR_OUT,\n" );
    	sql.append("--站外环比\n" );
    	sql.append("       ROUND((SUM(CASE WHEN T.IF_OUT = 100101 THEN NVL(T.HOUR,0) ELSE 0 END)-SUM(CASE WHEN T.IF_OUT = 100101 THEN NVL(T.LASTMONTH_HOUR,0) ELSE 0 END))/\n" );
    	sql.append("             (CASE WHEN (SUM(CASE WHEN T.IF_OUT = 100101 THEN NVL(T.LASTMONTH_HOUR,0) ELSE 0 END)) = 0 THEN 1\n" );
    	sql.append("                   ELSE (SUM(CASE WHEN T.IF_OUT = 100101 THEN NVL(T.LASTMONTH_HOUR,0) END)) END),2) MONTH_GROUTH_OUT,\n" );
    	sql.append("--站外同比\n" );
    	sql.append("       ROUND((SUM(CASE WHEN T.IF_OUT = 100101 THEN NVL(T.HOUR,0) ELSE 0 END)-SUM(CASE WHEN T.IF_OUT = 100101 THEN NVL(T.LASTYEAR_HOUR,0) ELSE 0 END))/\n" );
    	sql.append("             (CASE WHEN (SUM(CASE WHEN T.IF_OUT = 100101 THEN NVL(T.LASTYEAR_HOUR,0) ELSE 0 END)) = 0 THEN 1\n" );
    	sql.append("                   ELSE (SUM(CASE WHEN T.IF_OUT = 100101 THEN NVL(T.LASTYEAR_HOUR,0) END)) END),2) YEAR_GROUTH_OUT,\n" );
    	sql.append("\n" );
    	sql.append("--站内环比\n" );
    	sql.append("       ROUND((SUM(CASE WHEN T.IF_OUT = 100102 THEN NVL(T.HOUR,0) ELSE 0 END)-SUM(CASE WHEN T.IF_OUT = 100102 THEN NVL(T.LASTMONTH_HOUR,0) ELSE 0 END))/\n" );
    	sql.append("       		 (CASE WHEN (SUM(CASE WHEN T.IF_OUT = 100102 THEN NVL(T.LASTMONTH_HOUR,0) ELSE 0 END)) = 0 THEN 1\n" );
    	sql.append("                   ELSE (SUM(CASE WHEN T.IF_OUT = 100102 THEN NVL(T.LASTMONTH_HOUR,0) END)) END),2) MONTH_GROUTH_IN,\n" );
    	sql.append("--站内同比\n" );
    	sql.append("       ROUND((SUM(CASE WHEN T.IF_OUT = 100102 THEN NVL(T.HOUR,0) ELSE 0 END)-SUM(CASE WHEN T.IF_OUT = 100102 THEN NVL(T.LASTYEAR_HOUR,0) ELSE 0 END))/\n" );
    	sql.append("       		 (CASE WHEN (SUM(CASE WHEN T.IF_OUT = 100102 THEN NVL(T.LASTYEAR_HOUR,0) ELSE 0 END)) = 0 THEN 1\n" );
    	sql.append("             	   ELSE (SUM(CASE WHEN T.IF_OUT = 100102 THEN NVL(T.LASTYEAR_HOUR,0) END)) END),2) YEAR_GROUTH_IN,\n" );
    	sql.append("\n" );
    	sql.append("--站内本月，上月，去年上月维修响应时间\n" );
    	sql.append("       SUM(CASE WHEN T.IF_OUT = 100102 THEN NVL(T.HOUR,0) ELSE 0 END) HOUR_IN,\n" );
    	sql.append("       SUM(CASE WHEN T.IF_OUT = 100102 THEN NVL(T.LASTMONTH_HOUR,0) ELSE 0 END) LASTMONTH_HOUR_IN,\n" );
    	sql.append("       SUM(CASE WHEN T.IF_OUT = 100102 THEN NVL(T.LASTYEAR_HOUR,0) ELSE 0 END) LASTYEAR_HOUR_IN\n" );
    	sql.append("  FROM SE_BU_SERVICE_HOUR T\n" );
		sql.append(" where "+conditions+"\n" );
		sql.append(" GROUP BY (T.ORG_ID, T.DEALER, T.BSCID, T.BSC, T.YEARMONTH)\n" );
		sql.append(" ORDER BY T.BSCID, T.ORG_ID, T.YEARMONTH)\n" );

    	return factory.select(null, sql.toString());
    }
    
    //下载(导出)办事处
    public QuerySet download_bsc(String conditions, User user) throws Exception
    {	    	

    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT ROWNUM, BSCID, BSC, YEARMONTH, HOUR_OUT, MONTH_GROUTH_OUT, YEAR_GROUTH_OUT, HOUR_IN, MONTH_GROUTH_IN, YEAR_GROUTH_IN FROM\n" );
    	sql.append("(SELECT A.BSCID,A.BSC,A.YEARMONTH,\n" );
    	sql.append("    ROUND(A.HOUR_OUT,2) HOUR_OUT,\n" );
    	sql.append("    ROUND((HOUR_OUT-LASTMONTH_HOUR_OUT)/\n" );
    	sql.append("   (CASE WHEN LASTMONTH_HOUR_OUT = 0 THEN 1  ELSE LASTMONTH_HOUR_OUT END),2) MONTH_GROUTH_OUT,\n" );
    	sql.append("    ROUND((HOUR_OUT-LASTYEAR_HOUR_OUT)/\n" );
    	sql.append("   (CASE WHEN LASTYEAR_HOUR_OUT = 0 THEN 1  ELSE LASTMONTH_HOUR_OUT END),2) YEAR_GROUTH_OUT,\n" );
    	sql.append("    ROUND(A.HOUR_IN,2) HOUR_IN,\n" );
    	sql.append("    ROUND((HOUR_IN-LASTMONTH_HOUR_IN)/\n" );
    	sql.append("   (CASE WHEN LASTMONTH_HOUR_IN = 0 THEN 1  ELSE LASTMONTH_HOUR_IN END),2) MONTH_GROUTH_IN,\n" );
    	sql.append("    ROUND((HOUR_IN-LASTYEAR_HOUR_IN)/\n" );
    	sql.append("   (CASE WHEN LASTYEAR_HOUR_IN = 0 THEN 1  ELSE LASTMONTH_HOUR_IN END),2) YEAR_GROUTH_IN\n" );
    	sql.append("\n" );
    	sql.append("FROM (SELECT T.BSCID, T.BSC, T.YEARMONTH,\n" );
    	sql.append("   --办事处站外\n" );
    	sql.append("   ((CASE WHEN T.IF_OUT = 100101 THEN SUM(T.HOUR) ELSE 0 END)/\n" );
    	sql.append("   (CASE WHEN T.IF_OUT = 100101 THEN COUNT(T.ORG_ID) ELSE 1 END)) AS HOUR_OUT,           --本月平均时间\n" );
    	sql.append("\n" );
    	sql.append("   ((CASE WHEN T.IF_OUT = 100101 THEN SUM(T.LASTMONTH_HOUR) ELSE 0 END)/\n" );
    	sql.append("   (CASE WHEN T.IF_OUT = 100101 THEN COUNT(T.ORG_ID) ELSE 1 END)) AS LASTMONTH_HOUR_OUT, --上月平均时间\n" );
    	sql.append("\n" );
    	sql.append("   ((CASE WHEN T.IF_OUT = 100101 THEN SUM(T.LASTYEAR_HOUR) ELSE 0 END)/\n" );
    	sql.append("   (CASE WHEN T.IF_OUT = 100101 THEN COUNT(T.ORG_ID) ELSE 1 END)) AS LASTYEAR_HOUR_OUT,  --去年本月平均时间\n" );
    	sql.append("\n" );
    	sql.append("   --办事处站内\n" );
    	sql.append("   ((SELECT (CASE WHEN X.IF_OUT = 100102 THEN SUM(X.HOUR) ELSE 0 END) FROM SE_BU_SERVICE_HOUR X WHERE X.BSCID=T.BSCID AND X.YEARMONTH=T.YEARMONTH AND X.IF_OUT=100102 GROUP BY ( X.BSCID, X.BSC, X.YEARMONTH, X.IF_OUT))/\n" );
    	sql.append("   (SELECT (CASE WHEN X.IF_OUT = 100102 THEN COUNT(X.ORG_ID) ELSE 1 END) FROM SE_BU_SERVICE_HOUR X WHERE X.BSCID=T.BSCID AND X.YEARMONTH=T.YEARMONTH AND X.IF_OUT=100102 GROUP BY ( X.BSCID, X.BSC, X.YEARMONTH, X.IF_OUT))) AS HOUR_IN,           --本月平均时间\n" );
    	sql.append("\n" );
    	sql.append("   ((SELECT (CASE WHEN X.IF_OUT = 100102 THEN SUM(X.LASTMONTH_HOUR) ELSE 0 END) FROM SE_BU_SERVICE_HOUR X WHERE X.BSCID=T.BSCID AND X.YEARMONTH=T.YEARMONTH AND X.IF_OUT=100102 GROUP BY ( X.BSCID, X.BSC, X.YEARMONTH, X.IF_OUT))/\n" );
    	sql.append("   (SELECT (CASE WHEN X.IF_OUT = 100102 THEN COUNT(X.ORG_ID) ELSE 1 END) FROM SE_BU_SERVICE_HOUR X WHERE X.BSCID=T.BSCID AND X.YEARMONTH=T.YEARMONTH AND X.IF_OUT=100102 GROUP BY ( X.BSCID, X.BSC, X.YEARMONTH, X.IF_OUT))) AS LASTMONTH_HOUR_IN, --上月平均时间\n" );
    	sql.append("\n" );
    	sql.append("   ((SELECT (CASE WHEN X.IF_OUT = 100102 THEN SUM(X.LASTYEAR_HOUR) ELSE 0 END) FROM SE_BU_SERVICE_HOUR X WHERE X.BSCID=T.BSCID AND X.YEARMONTH=T.YEARMONTH AND X.IF_OUT=100102 GROUP BY ( X.BSCID, X.BSC, X.YEARMONTH, X.IF_OUT))/\n" );
    	sql.append("   (SELECT (CASE WHEN X.IF_OUT = 100102 THEN COUNT(X.ORG_ID) ELSE 1 END) FROM SE_BU_SERVICE_HOUR X WHERE X.BSCID=T.BSCID AND X.YEARMONTH=T.YEARMONTH AND X.IF_OUT=100102 GROUP BY ( X.BSCID, X.BSC, X.YEARMONTH, X.IF_OUT))) AS LASTYEAR_HOUR_IN   --去年本月平均时间\n" );
    	sql.append("  FROM SE_BU_SERVICE_HOUR T\n" );
    	sql.append("  WHERE T.IF_OUT = 100101\n" );
    	sql.append("  GROUP BY ( T.BSCID, T.BSC, T.YEARMONTH, T.IF_OUT)) A\n" );
    	sql.append("  ORDER BY A.YEARMONTH,A.BSCID)\n" );

    	return factory.select(null, sql.toString());
    }
}