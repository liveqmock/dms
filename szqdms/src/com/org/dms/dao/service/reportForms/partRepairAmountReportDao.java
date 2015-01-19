package com.org.dms.dao.service.reportForms;

import java.sql.CallableStatement;
import java.sql.SQLException;

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
public class partRepairAmountReportDao extends BaseDAO
{
    //定义instance
    public  static final partRepairAmountReportDao getInstance(ActionContext atx)
    {
    	partRepairAmountReportDao dao = new partRepairAmountReportDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * 零件保修更换数量台帐	
     */
    public BaseResultSet partRepairSearch(PageManager page, User user, String year,String partCode,String partName,String bYear) throws SQLException{
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.PART_ID,\n" );
    	sql.append("       T.PART_CODE,\n" );
    	sql.append("       T.PART_NAME,\n" );
    	sql.append("       T.F_POSITION_NAME,\n" );
    	sql.append("       NVL(T.OLD_PART_COUNT01, 0) OLD_PART_COUNT01,\n" );
    	sql.append("       NVL(T.OLD_PART_COUNT02, 0) OLD_PART_COUNT02,\n" );
    	sql.append("       NVL(T.OLD_PART_COUNT03, 0) OLD_PART_COUNT03,\n" );
    	sql.append("       NVL(T.OLD_PART_COUNT04, 0) OLD_PART_COUNT04,\n" );
    	sql.append("       NVL(T.OLD_PART_COUNT05, 0) OLD_PART_COUNT05,\n" );
    	sql.append("       NVL(T.OLD_PART_COUNT06, 0) OLD_PART_COUNT06,\n" );
    	sql.append("       NVL(T.OLD_PART_COUNT07, 0) OLD_PART_COUNT07,\n" );
    	sql.append("       NVL(T.OLD_PART_COUNT08, 0) OLD_PART_COUNT08,\n" );
    	sql.append("       NVL(T.OLD_PART_COUNT09, 0) OLD_PART_COUNT09,\n" );
    	sql.append("       NVL(T.OLD_PART_COUNT10, 0) OLD_PART_COUNT10,\n" );
    	sql.append("       NVL(T.OLD_PART_COUNT11, 0) OLD_PART_COUNT11,\n" );
    	sql.append("       NVL(T.OLD_PART_COUNT12, 0) OLD_PART_COUNT12, --本年旧件更换数量\n" );
    	sql.append("\n" );
    	sql.append("       NVL(T.OLD_PART_COUNT01, 0) + NVL(T.OLD_PART_COUNT02, 0) +\n" );
    	sql.append("       NVL(T.OLD_PART_COUNT03, 0) + NVL(T.OLD_PART_COUNT04, 0) +\n" );
    	sql.append("       NVL(T.OLD_PART_COUNT05, 0) + NVL(T.OLD_PART_COUNT06, 0) +\n" );
    	sql.append("       NVL(T.OLD_PART_COUNT07, 0) + NVL(T.OLD_PART_COUNT08, 0) +\n" );
    	sql.append("       NVL(T.OLD_PART_COUNT09, 0) + NVL(T.OLD_PART_COUNT10, 0) +\n" );
    	sql.append("       NVL(T.OLD_PART_COUNT11, 0) + NVL(T.OLD_PART_COUNT12, 0) ZSL, --本年旧件更换总数量\n" );
    	sql.append("\n" );
    	sql.append("       NVL(T.B_OLD_PART_COUNT01, 0) B_OLD_PART_COUNT01,\n" );
    	sql.append("       NVL(T.B_OLD_PART_COUNT02, 0) B_OLD_PART_COUNT02,\n" );
    	sql.append("       NVL(T.B_OLD_PART_COUNT03, 0) B_OLD_PART_COUNT03,\n" );
    	sql.append("       NVL(T.B_OLD_PART_COUNT04, 0) B_OLD_PART_COUNT04,\n" );
    	sql.append("       NVL(T.B_OLD_PART_COUNT05, 0) B_OLD_PART_COUNT05,\n" );
    	sql.append("       NVL(T.B_OLD_PART_COUNT06, 0) B_OLD_PART_COUNT06,\n" );
    	sql.append("       NVL(T.B_OLD_PART_COUNT07, 0) B_OLD_PART_COUNT07,\n" );
    	sql.append("       NVL(T.B_OLD_PART_COUNT08, 0) B_OLD_PART_COUNT08,\n" );
    	sql.append("       NVL(T.B_OLD_PART_COUNT09, 0) B_OLD_PART_COUNT09,\n" );
    	sql.append("       NVL(T.B_OLD_PART_COUNT10, 0) B_OLD_PART_COUNT10,\n" );
    	sql.append("       NVL(T.B_OLD_PART_COUNT11, 0) B_OLD_PART_COUNT11,\n" );
    	sql.append("       NVL(T.B_OLD_PART_COUNT12, 0) B_OLD_PART_COUNT12, --上一年旧件更换数量\n" );
    	sql.append("\n" );
    	sql.append("       CASE\n" );
    	sql.append("         WHEN NVL(T.B_OLD_PART_COUNT01, 0) = 0 THEN\n" );
    	sql.append("          CASE\n" );
    	sql.append("            WHEN NVL(T.OLD_PART_COUNT01, 0) = 0 THEN\n" );
    	sql.append("             0\n" );
    	sql.append("            ELSE\n" );
    	sql.append("             100\n" );
    	sql.append("          END\n" );
    	sql.append("         ELSE\n" );
    	sql.append("          NVL(T.OLD_PART_COUNT01, 0) -NVL(T.B_OLD_PART_COUNT01, 0) / NVL(T.B_OLD_PART_COUNT01, 0)\n" );
    	sql.append("       END TB01,\n" );
    	sql.append("       CASE\n" );
    	sql.append("         WHEN NVL(T.B_OLD_PART_COUNT02, 0) = 0 THEN\n" );
    	sql.append("          CASE\n" );
    	sql.append("            WHEN NVL(T.OLD_PART_COUNT02, 0) = 0 THEN\n" );
    	sql.append("             0\n" );
    	sql.append("            ELSE\n" );
    	sql.append("             100\n" );
    	sql.append("          END\n" );
    	sql.append("         ELSE\n" );
    	sql.append("          NVL(T.OLD_PART_COUNT02, 0) -NVL(T.B_OLD_PART_COUNT02, 0) / NVL(T.B_OLD_PART_COUNT02, 0)\n" );
    	sql.append("       END TB02,\n" );
    	sql.append("       CASE\n" );
    	sql.append("         WHEN NVL(T.B_OLD_PART_COUNT03, 0) = 0 THEN\n" );
    	sql.append("          CASE\n" );
    	sql.append("            WHEN NVL(T.OLD_PART_COUNT03, 0) = 0 THEN\n" );
    	sql.append("             0\n" );
    	sql.append("            ELSE\n" );
    	sql.append("             100\n" );
    	sql.append("          END\n" );
    	sql.append("         ELSE\n" );
    	sql.append("          NVL(T.OLD_PART_COUNT03, 0) -NVL(T.B_OLD_PART_COUNT03, 0) / NVL(T.B_OLD_PART_COUNT03, 0)\n" );
    	sql.append("       END TB03,\n" );
    	sql.append("       CASE\n" );
    	sql.append("         WHEN NVL(T.B_OLD_PART_COUNT04, 0) = 0 THEN\n" );
    	sql.append("          CASE\n" );
    	sql.append("            WHEN NVL(T.OLD_PART_COUNT04, 0) = 0 THEN\n" );
    	sql.append("             0\n" );
    	sql.append("            ELSE\n" );
    	sql.append("             100\n" );
    	sql.append("          END\n" );
    	sql.append("         ELSE\n" );
    	sql.append("          NVL(T.OLD_PART_COUNT04, 0) -NVL(T.B_OLD_PART_COUNT04, 0) / NVL(T.B_OLD_PART_COUNT04, 0)\n" );
    	sql.append("       END TB04,\n" );
    	sql.append("       CASE\n" );
    	sql.append("         WHEN NVL(T.B_OLD_PART_COUNT05, 0) = 0 THEN\n" );
    	sql.append("          CASE\n" );
    	sql.append("            WHEN NVL(T.OLD_PART_COUNT05, 0) = 0 THEN\n" );
    	sql.append("             0\n" );
    	sql.append("            ELSE\n" );
    	sql.append("             100\n" );
    	sql.append("          END\n" );
    	sql.append("         ELSE\n" );
    	sql.append("          NVL(T.OLD_PART_COUNT05, 0) -NVL(T.B_OLD_PART_COUNT05, 0) / NVL(T.B_OLD_PART_COUNT05, 0)\n" );
    	sql.append("       END TB05,\n" );
    	sql.append("       CASE\n" );
    	sql.append("         WHEN NVL(T.B_OLD_PART_COUNT06, 0) = 0 THEN\n" );
    	sql.append("          CASE\n" );
    	sql.append("            WHEN NVL(T.OLD_PART_COUNT06, 0) = 0 THEN\n" );
    	sql.append("             0\n" );
    	sql.append("            ELSE\n" );
    	sql.append("             100\n" );
    	sql.append("          END\n" );
    	sql.append("         ELSE\n" );
    	sql.append("          NVL(T.OLD_PART_COUNT06, 0) -NVL(T.B_OLD_PART_COUNT06, 0) / NVL(T.B_OLD_PART_COUNT06, 0)\n" );
    	sql.append("       END TB06,\n" );
    	sql.append("       CASE\n" );
    	sql.append("         WHEN NVL(T.B_OLD_PART_COUNT07, 0) = 0 THEN\n" );
    	sql.append("          CASE\n" );
    	sql.append("            WHEN NVL(T.OLD_PART_COUNT07, 0) = 0 THEN\n" );
    	sql.append("             0\n" );
    	sql.append("            ELSE\n" );
    	sql.append("             100\n" );
    	sql.append("          END\n" );
    	sql.append("         ELSE\n" );
    	sql.append("          NVL(T.OLD_PART_COUNT07, 0) -NVL(T.B_OLD_PART_COUNT07, 0) / NVL(T.B_OLD_PART_COUNT07, 0)\n" );
    	sql.append("       END TB07,\n" );
    	sql.append("       CASE\n" );
    	sql.append("         WHEN NVL(T.B_OLD_PART_COUNT08, 0) = 0 THEN\n" );
    	sql.append("          CASE\n" );
    	sql.append("            WHEN NVL(T.OLD_PART_COUNT08, 0) = 0 THEN\n" );
    	sql.append("             0\n" );
    	sql.append("            ELSE\n" );
    	sql.append("             100\n" );
    	sql.append("          END\n" );
    	sql.append("         ELSE\n" );
    	sql.append("          NVL(T.OLD_PART_COUNT08, 0) -NVL(T.B_OLD_PART_COUNT08, 0) / NVL(T.B_OLD_PART_COUNT08, 0)\n" );
    	sql.append("       END TB08,\n" );
    	sql.append("       CASE\n" );
    	sql.append("         WHEN NVL(T.B_OLD_PART_COUNT09, 0) = 0 THEN\n" );
    	sql.append("          CASE\n" );
    	sql.append("            WHEN NVL(T.OLD_PART_COUNT09, 0) = 0 THEN\n" );
    	sql.append("             0\n" );
    	sql.append("            ELSE\n" );
    	sql.append("             100\n" );
    	sql.append("          END\n" );
    	sql.append("         ELSE\n" );
    	sql.append("          NVL(T.OLD_PART_COUNT09, 0) -NVL(T.B_OLD_PART_COUNT09, 0) / NVL(T.B_OLD_PART_COUNT09, 0)\n" );
    	sql.append("       END TB09,\n" );
    	sql.append("       CASE\n" );
    	sql.append("         WHEN NVL(T.B_OLD_PART_COUNT10, 0) = 0 THEN\n" );
    	sql.append("          CASE\n" );
    	sql.append("            WHEN NVL(T.OLD_PART_COUNT10, 0) = 0 THEN\n" );
    	sql.append("             0\n" );
    	sql.append("            ELSE\n" );
    	sql.append("             100\n" );
    	sql.append("          END\n" );
    	sql.append("         ELSE\n" );
    	sql.append("          NVL(T.OLD_PART_COUNT10, 0) -NVL(T.B_OLD_PART_COUNT10, 0) / NVL(T.B_OLD_PART_COUNT10, 0)\n" );
    	sql.append("       END TB10,\n" );
    	sql.append("       CASE\n" );
    	sql.append("         WHEN NVL(T.B_OLD_PART_COUNT11, 0) = 0 THEN\n" );
    	sql.append("          CASE\n" );
    	sql.append("            WHEN NVL(T.OLD_PART_COUNT11, 0) = 0 THEN\n" );
    	sql.append("             0\n" );
    	sql.append("            ELSE\n" );
    	sql.append("             100\n" );
    	sql.append("          END\n" );
    	sql.append("         ELSE\n" );
    	sql.append("          NVL(T.OLD_PART_COUNT11, 0) -NVL(T.B_OLD_PART_COUNT11, 0) / NVL(T.B_OLD_PART_COUNT11, 0)\n" );
    	sql.append("       END TB11,\n" );
    	sql.append("       CASE\n" );
    	sql.append("         WHEN NVL(T.B_OLD_PART_COUNT12, 0) = 0 THEN\n" );
    	sql.append("          CASE\n" );
    	sql.append("            WHEN NVL(T.OLD_PART_COUNT12, 0) = 0 THEN\n" );
    	sql.append("             0\n" );
    	sql.append("            ELSE\n" );
    	sql.append("             100\n" );
    	sql.append("          END\n" );
    	sql.append("         ELSE\n" );
    	sql.append("          NVL(T.OLD_PART_COUNT12, 0) -NVL(T.B_OLD_PART_COUNT12, 0) / NVL(T.B_OLD_PART_COUNT12, 0)\n" );
    	sql.append("       END TB12\n" );
    	sql.append("\n" );
    	sql.append("  FROM (SELECT I.PART_ID,\n" );
    	sql.append("               I.PART_CODE,\n" );
    	sql.append("               I.PART_NAME,\n" );
    	sql.append("               I.F_POSITION_NAME,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '01' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+year+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END OLD_PART_COUNT01,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '02' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+year+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END OLD_PART_COUNT02,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '03' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+year+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END OLD_PART_COUNT03,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '04' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+year+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END OLD_PART_COUNT04,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '05' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+year+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END OLD_PART_COUNT05,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '06' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+year+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END OLD_PART_COUNT06,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '07' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+year+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END OLD_PART_COUNT07,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '08' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+year+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END OLD_PART_COUNT08,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '09' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+year+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END OLD_PART_COUNT09,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '10' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+year+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END OLD_PART_COUNT10,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '11' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+year+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END OLD_PART_COUNT11,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '12' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+year+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END OLD_PART_COUNT12,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '01' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+bYear+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END B_OLD_PART_COUNT01,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '02' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+bYear+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END B_OLD_PART_COUNT02,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '03' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+bYear+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END B_OLD_PART_COUNT03,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '04' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+bYear+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END B_OLD_PART_COUNT04,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '05' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+bYear+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END B_OLD_PART_COUNT05,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '06' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+bYear+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END B_OLD_PART_COUNT06,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '07' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+bYear+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END B_OLD_PART_COUNT07,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '08' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+bYear+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END B_OLD_PART_COUNT08,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '09' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+bYear+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END B_OLD_PART_COUNT09,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '10' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+bYear+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END B_OLD_PART_COUNT10,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '11' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+bYear+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END B_OLD_PART_COUNT11,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '12' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+bYear+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END B_OLD_PART_COUNT12\n" );
    	sql.append("          FROM SE_BU_CLAIM_FAULT_PART P, SE_BU_CLAIM C, PT_BA_INFO I\n" );
    	sql.append("         WHERE P.CLAIM_ID = C.CLAIM_ID\n" );
    	sql.append("           AND P.OLD_PART_ID = I.PART_ID\n" );
    	if(null==partCode||partCode.equals("")){
    	}else{
    		sql.append("           AND I.PART_CODE LIKE '%"+partCode+"%'\n" );
    	}
    	if(null==partName||partName.equals("")){
    	}else{
    		sql.append("           AND I.PART_CODE LIKE '%"+partName+"%'\n" );
    	}
    	sql.append("         GROUP BY I.PART_ID, I.PART_CODE, I.PART_NAME, I.F_POSITION_NAME) T\n" );
    	sql.append(" GROUP BY T.PART_ID,\n" );
    	sql.append("          T.PART_CODE,\n" );
    	sql.append("          T.PART_NAME,\n" );
    	sql.append("          T.F_POSITION_NAME,\n" );
    	sql.append("          T.OLD_PART_COUNT01,\n" );
    	sql.append("          T.OLD_PART_COUNT02,\n" );
    	sql.append("          T.OLD_PART_COUNT03,\n" );
    	sql.append("          T.OLD_PART_COUNT04,\n" );
    	sql.append("          T.OLD_PART_COUNT05,\n" );
    	sql.append("          T.OLD_PART_COUNT06,\n" );
    	sql.append("          T.OLD_PART_COUNT07,\n" );
    	sql.append("          T.OLD_PART_COUNT08,\n" );
    	sql.append("          T.OLD_PART_COUNT09,\n" );
    	sql.append("          T.OLD_PART_COUNT10,\n" );
    	sql.append("          T.OLD_PART_COUNT11,\n" );
    	sql.append("          T.OLD_PART_COUNT12,\n" );
    	sql.append("          T.B_OLD_PART_COUNT01,\n" );
    	sql.append("          T.B_OLD_PART_COUNT02,\n" );
    	sql.append("          T.B_OLD_PART_COUNT03,\n" );
    	sql.append("          T.B_OLD_PART_COUNT04,\n" );
    	sql.append("          T.B_OLD_PART_COUNT05,\n" );
    	sql.append("          T.B_OLD_PART_COUNT06,\n" );
    	sql.append("          T.B_OLD_PART_COUNT07,\n" );
    	sql.append("          T.B_OLD_PART_COUNT08,\n" );
    	sql.append("          T.B_OLD_PART_COUNT09,\n" );
    	sql.append("          T.B_OLD_PART_COUNT10,\n" );
    	sql.append("          T.B_OLD_PART_COUNT11,\n" );
    	sql.append("          T.B_OLD_PART_COUNT12");
    	bs=factory.select(sql.toString(), page);
    	bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
    	bs.setFieldDateFormat("REJECT_DATE", "yyyy-MM-dd HH:mm:ss");
    	bs.setFieldDic("CLAIM_STATUS", "SPDZT");
        bs.setFieldOrgDeptSimpleName("ORG_NAME");
		bs.setFieldOrgDeptCode("ORG_CODE");
    	return bs;
    }
    /**
     * 零件保修更换数量台帐	
     */
    public BaseResultSet supRepairSearch(PageManager page, User user,String supCode,String supName,String year) throws SQLException{
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT QID,\n" );
    	sql.append("       SEARCH_YEAR,\n" );
    	sql.append("       SUPPLIER_ID,\n" );
    	sql.append("       SUPPLIER_NAME,\n" );
    	sql.append("       SUPPLIER_CODE,\n" );
    	sql.append("       LAST_MONTH,\n" );
    	sql.append("       FIRST_JAN,\n" );
    	sql.append("       SECOND_JAN,\n" );
    	sql.append("       THIRD_JAN,\n" );
    	sql.append("       RATE_JAN_YOY,\n" );
    	sql.append("       RATE_JAN_MOM,\n" );
    	sql.append("       FIRST_FEB,\n" );
    	sql.append("       SECOND_FEB,\n" );
    	sql.append("       THIRD_FEB,\n" );
    	sql.append("       RATE_FEB_YOY,\n" );
    	sql.append("       RATE_FEB_MOM,\n" );
    	sql.append("       FIRST_MAR,\n" );
    	sql.append("       SECOND_MAR,\n" );
    	sql.append("       THIRD_MAR,\n" );
    	sql.append("       RATE_MAR_YOY,\n" );
    	sql.append("       RATE_MAR_MOM,\n" );
    	sql.append("       FIRST_APR,\n" );
    	sql.append("       SECOND_APR,\n" );
    	sql.append("       THIRD_APR,\n" );
    	sql.append("       RATE_APR_YOY,\n" );
    	sql.append("       RATE_APR_MOM,\n" );
    	sql.append("       FIRST_MAY,\n" );
    	sql.append("       SECOND_MAY,\n" );
    	sql.append("       THIRD_MAY,\n" );
    	sql.append("       RATE_MAY_YOY,\n" );
    	sql.append("       RATE_MAY_MOM,\n" );
    	sql.append("       FIRST_JUN,\n" );
    	sql.append("       SECOND_JUN,\n" );
    	sql.append("       THIRD_JUN,\n" );
    	sql.append("       RATE_JUN_YOY,\n" );
    	sql.append("       RATE_JUN_MOM,\n" );
    	sql.append("       FIRST_JULY,\n" );
    	sql.append("       SECOND_JULY,\n" );
    	sql.append("       THIRD_JULY,\n" );
    	sql.append("       RATE_JULY_YOY,\n" );
    	sql.append("       RATE_JULY_MOM,\n" );
    	sql.append("       FIRST_AUGUST,\n" );
    	sql.append("       SECOND_AUGUST,\n" );
    	sql.append("       THIRD_AUGUST,\n" );
    	sql.append("       RATE_AUGUST_YOY,\n" );
    	sql.append("       RATE_AUGUST_MOM,\n" );
    	sql.append("       FIRST_SEPT,\n" );
    	sql.append("       SECOND_SEPT,\n" );
    	sql.append("       THIRD_SEPT,\n" );
    	sql.append("       RATE_SEPT_YOY,\n" );
    	sql.append("       RATE_SEPT_MOM,\n" );
    	sql.append("       FIRST_OCT,\n" );
    	sql.append("       SECOND_OCT,\n" );
    	sql.append("       THIRD_OCT,\n" );
    	sql.append("       RATE_OCT_YOY,\n" );
    	sql.append("       RATE_OCT_MOM,\n" );
    	sql.append("       FIRST_NOV,\n" );
    	sql.append("       SECOND_NOV,\n" );
    	sql.append("       THIRD_NOV,\n" );
    	sql.append("       RATE_NOV_YOY,\n" );
    	sql.append("       RATE_NOV_MOM,\n" );
    	sql.append("       FIRST_DEC,\n" );
    	sql.append("       SECOND_DEC,\n" );
    	sql.append("       THIRD_DEC,\n" );
    	sql.append("       RATE_DEC_YOY,\n" );
    	sql.append("       RATE_DEC_MOM\n" );
    	sql.append("  FROM SE_BU_RECOVERY_QUERY");
    	sql.append("  WHERE SEARCH_YEAR='"+year+"' ");
    	if(null==supCode||supCode.equals("")){}else{
    		sql.append("  AND SUPPLIER_CODE LIKE '%"+supCode+"%'");
    	}
    	if(null==supName||supName.equals("")){}else{
    		sql.append("  AND SUPPLIER_CODE LIKE '%"+supName+"%'");
    	}
    	
    	bs=factory.select(sql.toString(), page);
    	return bs;
    }
    /**
     * 强，定保次数查询
     */
    public BaseResultSet claimQdbSearch(PageManager page, User user,String conditions) throws SQLException{
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.CLAIM_ID,\n" );
    	sql.append("       T.CLAIM_NO,\n" );
    	sql.append("       T.CLAIM_TYPE,\n" );
    	sql.append("       T.VIN,\n" );
    	sql.append("       T.VEHICLE_ID,\n" );
    	sql.append("       G.CODE,\n" );
    	sql.append("       G.ONAME,\n" );
    	sql.append("       G1.CODE BSCDM,\n" );
    	sql.append("       G1.ONAME BSCMC,\n" );
    	sql.append("       T.BUY_DATE,\n" );
    	sql.append("       T.OLDPART_FINAL_DATE,\n" );
    	sql.append("       T.CLAIM_STATUS,\n" );
    	sql.append("       (SELECT M.MODELS_CODE\n" );
    	sql.append("          FROM MAIN_VEHICLE M\n" );
    	sql.append("         WHERE M.VEHICLE_ID = T.VEHICLE_ID) MODELS_CODE\n" );
    	sql.append("  FROM SE_BU_CLAIM T,TM_ORG G ,TM_ORG G1\n");
    	sql.append("WHERE "+conditions+"\n");
    	sql.append("AND  T.CLAIM_TYPE IN (301402, 301405)\n");
    	sql.append("AND T.ORG_ID = G.ORG_ID\n");
    	sql.append("AND G.PID = G1.ORG_ID\n");
    	sql.append("AND T.CLAIM_STATUS = 301015");
    	bs=factory.select(sql.toString(), page);
    	bs.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd HH:mm:ss");
    	bs.setFieldDateFormat("OLDPART_FINAL_DATE", "yyyy-MM-dd HH:mm:ss");
    	bs.setFieldDic("CLAIM_STATUS", "SPDZT");
    	bs.setFieldDic("CLAIM_TYPE", "SPDLX");
    	
    	return bs;
    }
    /**
     * 保单里程差异查询
     */
    public BaseResultSet claimLccySearch(PageManager page, User user,String conditions) throws SQLException{
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.CLAIM_ID,\n" );
    	sql.append("       T.CLAIM_NO,\n" );
    	sql.append("       T.CLAIM_TYPE,\n" );
    	sql.append("       T.CLAIM_STATUS,\n" );
    	sql.append("       G.CODE,\n" );
    	sql.append("       G.ONAME,\n" );
    	sql.append("       G1.CODE BSCDM,\n" );
    	sql.append("       G1.ONAME BSCMC,\n" );
    	sql.append("       T.VIN,\n" );
    	sql.append("       T.VEHICLE_ID,\n" );
    	sql.append("       T.BUY_DATE,\n" );
    	sql.append("       T.OLDPART_FINAL_DATE,\n" );
    	sql.append("       (SELECT M.MODELS_CODE\n" );
    	sql.append("          FROM MAIN_VEHICLE M\n" );
    	sql.append("         WHERE M.VEHICLE_ID = T.VEHICLE_ID) MODELS_CODE,\n" );
    	sql.append("       (SELECT O.MILEAGE FROM SE_BU_CLAIM_OUT O WHERE O.CLAIM_ID = T.CLAIM_ID)MILEAGE,\n" );
    	sql.append("       (SELECT O.GPS_MILEAGE FROM SE_BU_CLAIM_OUT O WHERE O.CLAIM_ID = T.CLAIM_ID)GPS_MILEAGE,\n" );
    	sql.append("       (SELECT O.GPS_MILEAGE - O.MILEAGE FROM SE_BU_CLAIM_OUT O WHERE O.CLAIM_ID = T.CLAIM_ID)LCC\n" );
    	sql.append("  FROM SE_BU_CLAIM T,TM_ORG G ,TM_ORG G1\n");
    	sql.append("WHERE "+conditions+"\n");
    	sql.append("AND T.ORG_ID = G.ORG_ID\n");
    	sql.append("AND G.PID = G1.ORG_ID\n");
    	sql.append("AND T.CLAIM_STATUS = 301015");
    	bs=factory.select(sql.toString(), page);
    	bs.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd HH:mm:ss");
    	bs.setFieldDateFormat("OLDPART_FINAL_DATE", "yyyy-MM-dd HH:mm:ss");
    	bs.setFieldDic("CLAIM_STATUS", "SPDZT");
    	bs.setFieldDic("CLAIM_TYPE", "SPDLX");
    	
    	
    	return bs;
    }
    /**
     * 服务商激励金额查询
     */
    public BaseResultSet claimXjjlSearch(PageManager page, User user,String conditions) throws SQLException{
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.CLAIM_ID,\n" );
    	sql.append("       T.CLAIM_NO,\n" );
    	sql.append("       T.CLAIM_TYPE,\n" );
    	sql.append("       T.CLAIM_STATUS,\n" );
    	sql.append("       T.VIN,\n" );
    	sql.append("       T.VEHICLE_ID,\n" );
    	sql.append("       G.CODE,\n" );
    	sql.append("       G.ONAME,\n" );
    	sql.append("       G1.CODE BSCDM,\n" );
    	sql.append("       G1.ONAME BSCMC,\n" );
    	sql.append("       T.BUY_DATE,\n" );
    	sql.append("       T.OLDPART_FINAL_DATE,\n" );
    	sql.append("       (SELECT M.MODELS_CODE\n" );
    	sql.append("          FROM MAIN_VEHICLE M\n" );
    	sql.append("         WHERE M.VEHICLE_ID = T.VEHICLE_ID) MODELS_CODE,\n" );
    	sql.append("       (SELECT SUM(F.STAR_LEVEL_UPRICE * F.WORK_COSTS)\n" );
    	sql.append("          FROM SE_BU_CLAIM_FAULT F\n" );
    	sql.append("         WHERE F.CLAIM_ID = T.CLAIM_ID\n" );
    	sql.append("         GROUP BY F.CLAIM_ID) XJJE,\n" );
    	sql.append("       (SELECT SUM(F.ENCOURAGE_UPRICE * F.WORK_COSTS)\n" );
    	sql.append("          FROM SE_BU_CLAIM_FAULT F\n" );
    	sql.append("         WHERE F.CLAIM_ID = T.CLAIM_ID\n" );
    	sql.append("         GROUP BY F.CLAIM_ID)JLJE");
    	sql.append("  FROM SE_BU_CLAIM T,TM_ORG G ,TM_ORG G1\n");
    	sql.append("WHERE "+conditions+"\n");
    	sql.append("AND T.ORG_ID = G.ORG_ID\n");
    	sql.append("AND G.PID = G1.ORG_ID\n");
    	sql.append("AND T.CLAIM_STATUS = 301015");
    	bs=factory.select(sql.toString(), page);
    	bs.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd HH:mm:ss");
    	bs.setFieldDateFormat("OLDPART_FINAL_DATE", "yyyy-MM-dd HH:mm:ss");
    	bs.setFieldDic("CLAIM_STATUS", "SPDZT");
    	bs.setFieldDic("CLAIM_TYPE", "SPDLX");
    	
    	
    	return bs;
    }
    /**
     * 车辆维修频次查询		
     */
    public BaseResultSet vehicleRepairCountSearch(PageManager page, User user,String conditions) throws SQLException{
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.VIN,\n" );
    	sql.append("       COUNT(T.CLAIM_ID) BDS,\n" );
    	sql.append("       (SELECT COUNT(C.CLAIM_ID)\n" );
    	sql.append("          FROM SE_BU_CLAIM C\n" );
    	sql.append("         WHERE C.VIN = T.VIN\n" );
    	sql.append("           AND C.CLAIM_TYPE NOT IN (301402, 301405, 301407, 301406)) WXS,\n" );
    	sql.append("       (SELECT SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("          FROM SE_BU_CLAIM_FAULT_PART P\n" );
    	sql.append("         WHERE P.CLAIM_ID IN\n" );
    	sql.append("               (SELECT CC.CLAIM_ID FROM SE_BU_CLAIM CC WHERE CC.VIN = T.VIN)) JJS\n" );
    	sql.append("  FROM SE_BU_CLAIM T\n" );
    	sql.append("WHERE "+conditions+"\n");
    	sql.append(" AND T.CREATE_TIME > TO_DATE('2014-11-01','YYYY-MM-DD') \n");
    	sql.append(" GROUP BY T.VIN");
    	bs=factory.select(sql.toString(), page);
    	
    	return bs;
    }
    /**
     * 服务站旧件数据统计表
     */
    public BaseResultSet oldPartCountSearch(PageManager page, User user,String conditions) throws SQLException{
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT G1.ONAME       BSC,\n" );
    	sql.append("       G.ONAME        FWZ,\n" );
    	sql.append("       RL.R_ORGNAME   JZD,\n" );
    	sql.append("       A.PRODUCE_DATE,\n" );
    	sql.append("       A.SHOULD_COUNT,\n" );
    	sql.append("       A.OUGHT_COUNT,\n" );
    	sql.append("       A.ALREADY_IN,\n" );
    	sql.append("       A.ALREADY_OUT,\n" );
    	sql.append("       A.ACTUL_COUNT\n" );
    	sql.append("  FROM TM_ORG G,\n" );
    	sql.append("       TM_ORG G1,\n" );
    	sql.append("       SE_BA_RETURN_DEALER_RELATION RL,\n" );
    	sql.append("       (SELECT C.ORG_ID,\n" );
    	sql.append("               R.PRODUCE_DATE,\n" );
    	sql.append("               SUM(D.SHOULD_COUNT) SHOULD_COUNT,\n" );
    	sql.append("               SUM(D.OUGHT_COUNT) OUGHT_COUNT,\n" );
    	sql.append("               SUM(D.ALREADY_IN) ALREADY_IN,\n" );
    	sql.append("               SUM(D.ALREADY_OUT) ALREADY_OUT,\n" );
    	sql.append("               SUM(D.ACTUL_COUNT) ACTUL_COUNT\n" );
    	sql.append("          FROM SE_BU_RETURNORDER_DETAIL D,\n" );
    	sql.append("               SE_BU_CLAIM              C,\n" );
    	sql.append("               SE_BU_RETURN_ORDER       R\n" );
    	sql.append("         WHERE C.CLAIM_ID = D.CLAIM_ID\n" );
    	sql.append("           AND R.ORDER_ID = D.ORDER_ID\n" );
    	sql.append("         GROUP BY C.ORG_ID, R.PRODUCE_DATE) A\n");
    	sql.append("WHERE "+conditions+"\n");
    	sql.append("  AND A.ORG_ID = G.ORG_ID\n" );
    	sql.append("  AND G.PID = G1.ORG_ID\n" );
    	sql.append("  AND RL.D_ORGID = G.ORG_ID\n" );
    	sql.append("ORDER BY A.PRODUCE_DATE, G1.ONAME, G.ONAME, RL.R_ORGNAME");

    	bs=factory.select(sql.toString(), page);
    	
    	return bs;
    }
    /**
     * 供应商旧件数据统计表
     */
    public BaseResultSet supOldPartCountSearch(PageManager page, User user,String conditions) throws SQLException{
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT G1.ONAME       BSC,\n" );
    	sql.append("       G.ONAME        FWZ,\n" );
    	sql.append("       A.PROSUPPLY_NAME,\n" );
    	sql.append("       A.PRODUCE_DATE,\n" );
    	sql.append("       A.SHOULD_COUNT,\n" );
    	sql.append("       A.OUGHT_COUNT,\n" );
    	sql.append("       A.ALREADY_IN,\n" );
    	sql.append("       A.ALREADY_OUT,\n" );
    	sql.append("       A.ACTUL_COUNT\n" );
    	sql.append("  FROM TM_ORG G,\n" );
    	sql.append("       TM_ORG G1,\n" );
    	sql.append("       (SELECT C.ORG_ID,\n" );
    	sql.append("               D.PROSUPPLY_ID,\n" );
    	sql.append("               D.PROSUPPLY_NAME,\n" );
    	sql.append("               R.PRODUCE_DATE,\n" );
    	sql.append("               SUM(D.SHOULD_COUNT) SHOULD_COUNT,\n" );
    	sql.append("               SUM(D.OUGHT_COUNT) OUGHT_COUNT,\n" );
    	sql.append("               SUM(D.ALREADY_IN) ALREADY_IN,\n" );
    	sql.append("               SUM(D.ALREADY_OUT) ALREADY_OUT,\n" );
    	sql.append("               SUM(D.ACTUL_COUNT) ACTUL_COUNT\n" );
    	sql.append("          FROM SE_BU_RETURNORDER_DETAIL D,\n" );
    	sql.append("               SE_BU_CLAIM              C,\n" );
    	sql.append("               SE_BU_RETURN_ORDER       R\n" );
    	sql.append("         WHERE C.CLAIM_ID = D.CLAIM_ID\n" );
    	sql.append("           AND R.ORDER_ID = D.ORDER_ID\n" );
    	sql.append("         GROUP BY C.ORG_ID, R.PRODUCE_DATE, D.PROSUPPLY_ID, D.PROSUPPLY_NAME) A\n");
    	sql.append("WHERE "+conditions+"\n");
    	sql.append("  AND A.ORG_ID = G.ORG_ID\n" );
    	sql.append("  AND G.PID = G1.ORG_ID\n" );
    	sql.append("ORDER BY A.PROSUPPLY_NAME,A.PRODUCE_DATE, G1.ONAME, G.ONAME");

    	
    	bs=factory.select(sql.toString(), page);
    	
    	return bs;
    }
    /**
     * 供应商旧件数据统计表
     */
    public BaseResultSet oldPartReturnCountSearch(PageManager page, User user,String conditions) throws SQLException{
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT BW.WAREHOUSE_NAME,\n" );
    	sql.append("       G.CODE FWZDM,\n" );
    	sql.append("       G.ONAME FWZMC,\n" );
    	sql.append("       G.SNAME FWZJC,\n" );
    	sql.append("       G1.ONAME BSCMC,\n" );
    	sql.append("       GG.ONAME JZDMC,\n" );
    	sql.append("       SUM(NVL(D.SHOULD_COUNT, 0)) SHOULD_COUNT,\n" );
    	sql.append("       SUM(NVL(D.OUGHT_COUNT, 0)) OUGHT_COUNT,\n" );
    	sql.append("       SUM(NVL(D.ALREADY_IN, 0)) ALREADY_IN\n" );
    	sql.append("  FROM SE_BU_RETURN_ORDER           RO,\n" );
    	sql.append("       SE_BU_RETURNORDER_DETAIL     D,\n" );
    	sql.append("       SE_BU_CLAIM                  C,\n" );
    	sql.append("       TM_ORG                       G,\n" );
    	sql.append("       TM_ORG                       G1,\n" );
    	sql.append("       TM_ORG                       GG,\n" );
    	sql.append("       SE_BU_WAREHOUSE_CENTROSTIGMA WC,\n" );
    	sql.append("       SE_BA_WAREHOUSE              BW\n" );
    	sql.append(" WHERE "+conditions+"\n" );
    	sql.append("   AND RO.ORDER_STATUS in ('302505', '302506')\n" );
    	sql.append("   AND RO.ORDER_ID = D.ORDER_ID\n" );
    	sql.append("   AND C.CLAIM_ID = D.CLAIM_ID\n" );
    	sql.append("   AND RO.ORDER_ID = D.ORDER_ID\n" );
    	sql.append("   AND BW.WAREHOUSE_ID = WC.WAREHOUSE_ID\n" );
    	sql.append("   AND G.ORG_ID = C.ORG_ID\n" );
    	sql.append("   AND G.PID = G1.ORG_ID\n" );
    	sql.append("   AND GG.ORG_ID = RO.ORG_ID\n" );
    	sql.append(" GROUP BY BW.WAREHOUSE_NAME, GG.ONAME, G.ONAME, G.CODE, G.SNAME, G1.ONAME\n" );
    	sql.append(" ORDER BY BW.WAREHOUSE_NAME, GG.ONAME, G.ONAME, G.CODE, G.SNAME, G1.ONAME");
    	
    	bs=factory.select(sql.toString(), page);
    	
    	return bs;
    }
    /**
     * 旧件管理费用统计
     */
    public BaseResultSet oldPartManageSearch(PageManager page, User user,String conditions) throws SQLException{
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.SUPPLIER_CODE,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("       T.PART_CODE,\n" );
    	sql.append("       T.PART_NAME,\n" );
    	sql.append("       P.SE_REPRICE,\n" );
    	sql.append("       P.OLD_MANAGE_FEE,\n" );
    	sql.append("       SUM(T.OUT_AMOUNT) OUT_AMOUNT,\n" );
    	sql.append("       SUM(T.OUT_AMOUNT*P.SE_REPRICE*P.OLD_MANAGE_FEE)OUT_JE\n" );
    	sql.append("  FROM SE_BU_RETURN_OUT T,PT_BA_INFO P\n" );
    	sql.append(" WHERE "+conditions+"\n" );
    	sql.append("   AND T.OUT_TYPE = 303101\n" );
    	sql.append("   AND P.PART_ID = T.PART_ID\n" );
    	sql.append(" GROUP BY  T.SUPPLIER_CODE,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("       T.PART_CODE,\n" );
    	sql.append("       T.PART_NAME,\n" );
    	sql.append("       P.SE_REPRICE,\n" );
    	sql.append("       P.OLD_MANAGE_FEE");
    	
    	bs=factory.select(sql.toString(), page);
    	
    	return bs;
    }
    /**
     * 零件保修更换数量台帐(导出)
     */
    public QuerySet download(String year,String partCode,String partName,String bYear) throws Exception {

    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.PART_ID,\n" );
    	sql.append("       T.PART_CODE,\n" );
    	sql.append("       T.PART_NAME,\n" );
    	sql.append("       T.F_POSITION_NAME,\n" );
    	sql.append("       NVL(T.OLD_PART_COUNT01, 0) OLD_PART_COUNT01,\n" );
    	sql.append("       NVL(T.OLD_PART_COUNT02, 0) OLD_PART_COUNT02,\n" );
    	sql.append("       NVL(T.OLD_PART_COUNT03, 0) OLD_PART_COUNT03,\n" );
    	sql.append("       NVL(T.OLD_PART_COUNT04, 0) OLD_PART_COUNT04,\n" );
    	sql.append("       NVL(T.OLD_PART_COUNT05, 0) OLD_PART_COUNT05,\n" );
    	sql.append("       NVL(T.OLD_PART_COUNT06, 0) OLD_PART_COUNT06,\n" );
    	sql.append("       NVL(T.OLD_PART_COUNT07, 0) OLD_PART_COUNT07,\n" );
    	sql.append("       NVL(T.OLD_PART_COUNT08, 0) OLD_PART_COUNT08,\n" );
    	sql.append("       NVL(T.OLD_PART_COUNT09, 0) OLD_PART_COUNT09,\n" );
    	sql.append("       NVL(T.OLD_PART_COUNT10, 0) OLD_PART_COUNT10,\n" );
    	sql.append("       NVL(T.OLD_PART_COUNT11, 0) OLD_PART_COUNT11,\n" );
    	sql.append("       NVL(T.OLD_PART_COUNT12, 0) OLD_PART_COUNT12, --本年旧件更换数量\n" );
    	sql.append("\n" );
    	sql.append("       NVL(T.OLD_PART_COUNT01, 0) + NVL(T.OLD_PART_COUNT02, 0) +\n" );
    	sql.append("       NVL(T.OLD_PART_COUNT03, 0) + NVL(T.OLD_PART_COUNT04, 0) +\n" );
    	sql.append("       NVL(T.OLD_PART_COUNT05, 0) + NVL(T.OLD_PART_COUNT06, 0) +\n" );
    	sql.append("       NVL(T.OLD_PART_COUNT07, 0) + NVL(T.OLD_PART_COUNT08, 0) +\n" );
    	sql.append("       NVL(T.OLD_PART_COUNT09, 0) + NVL(T.OLD_PART_COUNT10, 0) +\n" );
    	sql.append("       NVL(T.OLD_PART_COUNT11, 0) + NVL(T.OLD_PART_COUNT12, 0) ZSL, --本年旧件更换总数量\n" );
    	sql.append("\n" );
    	sql.append("       NVL(T.B_OLD_PART_COUNT01, 0) B_OLD_PART_COUNT01,\n" );
    	sql.append("       NVL(T.B_OLD_PART_COUNT02, 0) B_OLD_PART_COUNT02,\n" );
    	sql.append("       NVL(T.B_OLD_PART_COUNT03, 0) B_OLD_PART_COUNT03,\n" );
    	sql.append("       NVL(T.B_OLD_PART_COUNT04, 0) B_OLD_PART_COUNT04,\n" );
    	sql.append("       NVL(T.B_OLD_PART_COUNT05, 0) B_OLD_PART_COUNT05,\n" );
    	sql.append("       NVL(T.B_OLD_PART_COUNT06, 0) B_OLD_PART_COUNT06,\n" );
    	sql.append("       NVL(T.B_OLD_PART_COUNT07, 0) B_OLD_PART_COUNT07,\n" );
    	sql.append("       NVL(T.B_OLD_PART_COUNT08, 0) B_OLD_PART_COUNT08,\n" );
    	sql.append("       NVL(T.B_OLD_PART_COUNT09, 0) B_OLD_PART_COUNT09,\n" );
    	sql.append("       NVL(T.B_OLD_PART_COUNT10, 0) B_OLD_PART_COUNT10,\n" );
    	sql.append("       NVL(T.B_OLD_PART_COUNT11, 0) B_OLD_PART_COUNT11,\n" );
    	sql.append("       NVL(T.B_OLD_PART_COUNT12, 0) B_OLD_PART_COUNT12, --上一年旧件更换数量\n" );
    	sql.append("\n" );
    	sql.append("       CASE\n" );
    	sql.append("         WHEN NVL(T.B_OLD_PART_COUNT01, 0) = 0 THEN\n" );
    	sql.append("          CASE\n" );
    	sql.append("            WHEN NVL(T.OLD_PART_COUNT01, 0) = 0 THEN\n" );
    	sql.append("             0\n" );
    	sql.append("            ELSE\n" );
    	sql.append("             100\n" );
    	sql.append("          END\n" );
    	sql.append("         ELSE\n" );
    	sql.append("          NVL(T.OLD_PART_COUNT01, 0) -NVL(T.B_OLD_PART_COUNT01, 0) / NVL(T.B_OLD_PART_COUNT01, 0)\n" );
    	sql.append("       END TB01,\n" );
    	sql.append("       CASE\n" );
    	sql.append("         WHEN NVL(T.B_OLD_PART_COUNT02, 0) = 0 THEN\n" );
    	sql.append("          CASE\n" );
    	sql.append("            WHEN NVL(T.OLD_PART_COUNT02, 0) = 0 THEN\n" );
    	sql.append("             0\n" );
    	sql.append("            ELSE\n" );
    	sql.append("             100\n" );
    	sql.append("          END\n" );
    	sql.append("         ELSE\n" );
    	sql.append("          NVL(T.OLD_PART_COUNT02, 0) -NVL(T.B_OLD_PART_COUNT02, 0) / NVL(T.B_OLD_PART_COUNT02, 0)\n" );
    	sql.append("       END TB02,\n" );
    	sql.append("       CASE\n" );
    	sql.append("         WHEN NVL(T.B_OLD_PART_COUNT03, 0) = 0 THEN\n" );
    	sql.append("          CASE\n" );
    	sql.append("            WHEN NVL(T.OLD_PART_COUNT03, 0) = 0 THEN\n" );
    	sql.append("             0\n" );
    	sql.append("            ELSE\n" );
    	sql.append("             100\n" );
    	sql.append("          END\n" );
    	sql.append("         ELSE\n" );
    	sql.append("          NVL(T.OLD_PART_COUNT03, 0) -NVL(T.B_OLD_PART_COUNT03, 0) / NVL(T.B_OLD_PART_COUNT03, 0)\n" );
    	sql.append("       END TB03,\n" );
    	sql.append("       CASE\n" );
    	sql.append("         WHEN NVL(T.B_OLD_PART_COUNT04, 0) = 0 THEN\n" );
    	sql.append("          CASE\n" );
    	sql.append("            WHEN NVL(T.OLD_PART_COUNT04, 0) = 0 THEN\n" );
    	sql.append("             0\n" );
    	sql.append("            ELSE\n" );
    	sql.append("             100\n" );
    	sql.append("          END\n" );
    	sql.append("         ELSE\n" );
    	sql.append("          NVL(T.OLD_PART_COUNT04, 0) -NVL(T.B_OLD_PART_COUNT04, 0) / NVL(T.B_OLD_PART_COUNT04, 0)\n" );
    	sql.append("       END TB04,\n" );
    	sql.append("       CASE\n" );
    	sql.append("         WHEN NVL(T.B_OLD_PART_COUNT05, 0) = 0 THEN\n" );
    	sql.append("          CASE\n" );
    	sql.append("            WHEN NVL(T.OLD_PART_COUNT05, 0) = 0 THEN\n" );
    	sql.append("             0\n" );
    	sql.append("            ELSE\n" );
    	sql.append("             100\n" );
    	sql.append("          END\n" );
    	sql.append("         ELSE\n" );
    	sql.append("          NVL(T.OLD_PART_COUNT05, 0) -NVL(T.B_OLD_PART_COUNT05, 0) / NVL(T.B_OLD_PART_COUNT05, 0)\n" );
    	sql.append("       END TB05,\n" );
    	sql.append("       CASE\n" );
    	sql.append("         WHEN NVL(T.B_OLD_PART_COUNT06, 0) = 0 THEN\n" );
    	sql.append("          CASE\n" );
    	sql.append("            WHEN NVL(T.OLD_PART_COUNT06, 0) = 0 THEN\n" );
    	sql.append("             0\n" );
    	sql.append("            ELSE\n" );
    	sql.append("             100\n" );
    	sql.append("          END\n" );
    	sql.append("         ELSE\n" );
    	sql.append("          NVL(T.OLD_PART_COUNT06, 0) -NVL(T.B_OLD_PART_COUNT06, 0) / NVL(T.B_OLD_PART_COUNT06, 0)\n" );
    	sql.append("       END TB06,\n" );
    	sql.append("       CASE\n" );
    	sql.append("         WHEN NVL(T.B_OLD_PART_COUNT07, 0) = 0 THEN\n" );
    	sql.append("          CASE\n" );
    	sql.append("            WHEN NVL(T.OLD_PART_COUNT07, 0) = 0 THEN\n" );
    	sql.append("             0\n" );
    	sql.append("            ELSE\n" );
    	sql.append("             100\n" );
    	sql.append("          END\n" );
    	sql.append("         ELSE\n" );
    	sql.append("          NVL(T.OLD_PART_COUNT07, 0) -NVL(T.B_OLD_PART_COUNT07, 0) / NVL(T.B_OLD_PART_COUNT07, 0)\n" );
    	sql.append("       END TB07,\n" );
    	sql.append("       CASE\n" );
    	sql.append("         WHEN NVL(T.B_OLD_PART_COUNT08, 0) = 0 THEN\n" );
    	sql.append("          CASE\n" );
    	sql.append("            WHEN NVL(T.OLD_PART_COUNT08, 0) = 0 THEN\n" );
    	sql.append("             0\n" );
    	sql.append("            ELSE\n" );
    	sql.append("             100\n" );
    	sql.append("          END\n" );
    	sql.append("         ELSE\n" );
    	sql.append("          NVL(T.OLD_PART_COUNT08, 0) -NVL(T.B_OLD_PART_COUNT08, 0) / NVL(T.B_OLD_PART_COUNT08, 0)\n" );
    	sql.append("       END TB08,\n" );
    	sql.append("       CASE\n" );
    	sql.append("         WHEN NVL(T.B_OLD_PART_COUNT09, 0) = 0 THEN\n" );
    	sql.append("          CASE\n" );
    	sql.append("            WHEN NVL(T.OLD_PART_COUNT09, 0) = 0 THEN\n" );
    	sql.append("             0\n" );
    	sql.append("            ELSE\n" );
    	sql.append("             100\n" );
    	sql.append("          END\n" );
    	sql.append("         ELSE\n" );
    	sql.append("          NVL(T.OLD_PART_COUNT09, 0) -NVL(T.B_OLD_PART_COUNT09, 0) / NVL(T.B_OLD_PART_COUNT09, 0)\n" );
    	sql.append("       END TB09,\n" );
    	sql.append("       CASE\n" );
    	sql.append("         WHEN NVL(T.B_OLD_PART_COUNT10, 0) = 0 THEN\n" );
    	sql.append("          CASE\n" );
    	sql.append("            WHEN NVL(T.OLD_PART_COUNT10, 0) = 0 THEN\n" );
    	sql.append("             0\n" );
    	sql.append("            ELSE\n" );
    	sql.append("             100\n" );
    	sql.append("          END\n" );
    	sql.append("         ELSE\n" );
    	sql.append("          NVL(T.OLD_PART_COUNT10, 0) -NVL(T.B_OLD_PART_COUNT10, 0) / NVL(T.B_OLD_PART_COUNT10, 0)\n" );
    	sql.append("       END TB10,\n" );
    	sql.append("       CASE\n" );
    	sql.append("         WHEN NVL(T.B_OLD_PART_COUNT11, 0) = 0 THEN\n" );
    	sql.append("          CASE\n" );
    	sql.append("            WHEN NVL(T.OLD_PART_COUNT11, 0) = 0 THEN\n" );
    	sql.append("             0\n" );
    	sql.append("            ELSE\n" );
    	sql.append("             100\n" );
    	sql.append("          END\n" );
    	sql.append("         ELSE\n" );
    	sql.append("          NVL(T.OLD_PART_COUNT11, 0) -NVL(T.B_OLD_PART_COUNT11, 0) / NVL(T.B_OLD_PART_COUNT11, 0)\n" );
    	sql.append("       END TB11,\n" );
    	sql.append("       CASE\n" );
    	sql.append("         WHEN NVL(T.B_OLD_PART_COUNT12, 0) = 0 THEN\n" );
    	sql.append("          CASE\n" );
    	sql.append("            WHEN NVL(T.OLD_PART_COUNT12, 0) = 0 THEN\n" );
    	sql.append("             0\n" );
    	sql.append("            ELSE\n" );
    	sql.append("             100\n" );
    	sql.append("          END\n" );
    	sql.append("         ELSE\n" );
    	sql.append("          NVL(T.OLD_PART_COUNT12, 0) -NVL(T.B_OLD_PART_COUNT12, 0) / NVL(T.B_OLD_PART_COUNT12, 0)\n" );
    	sql.append("       END TB12\n" );
    	sql.append("\n" );
    	sql.append("  FROM (SELECT I.PART_ID,\n" );
    	sql.append("               I.PART_CODE,\n" );
    	sql.append("               I.PART_NAME,\n" );
    	sql.append("               I.F_POSITION_NAME,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '01' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+year+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END OLD_PART_COUNT01,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '02' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+year+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END OLD_PART_COUNT02,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '03' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+year+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END OLD_PART_COUNT03,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '04' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+year+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END OLD_PART_COUNT04,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '05' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+year+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END OLD_PART_COUNT05,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '06' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+year+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END OLD_PART_COUNT06,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '07' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+year+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END OLD_PART_COUNT07,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '08' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+year+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END OLD_PART_COUNT08,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '09' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+year+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END OLD_PART_COUNT09,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '10' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+year+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END OLD_PART_COUNT10,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '11' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+year+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END OLD_PART_COUNT11,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '12' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+year+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END OLD_PART_COUNT12,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '01' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+bYear+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END B_OLD_PART_COUNT01,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '02' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+bYear+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END B_OLD_PART_COUNT02,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '03' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+bYear+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END B_OLD_PART_COUNT03,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '04' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+bYear+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END B_OLD_PART_COUNT04,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '05' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+bYear+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END B_OLD_PART_COUNT05,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '06' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+bYear+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END B_OLD_PART_COUNT06,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '07' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+bYear+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END B_OLD_PART_COUNT07,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '08' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+bYear+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END B_OLD_PART_COUNT08,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '09' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+bYear+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END B_OLD_PART_COUNT09,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '10' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+bYear+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END B_OLD_PART_COUNT10,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '11' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+bYear+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END B_OLD_PART_COUNT11,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN MAX(TO_CHAR(C.APPLY_DATE, 'MM')) = '12' AND\n" );
    	sql.append("                      MAX(TO_CHAR(C.APPLY_DATE, 'YYYY')) = '"+bYear+"' THEN\n" );
    	sql.append("                  SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  NULL\n" );
    	sql.append("               END B_OLD_PART_COUNT12\n" );
    	sql.append("          FROM SE_BU_CLAIM_FAULT_PART P, SE_BU_CLAIM C, PT_BA_INFO I\n" );
    	sql.append("         WHERE P.CLAIM_ID = C.CLAIM_ID\n" );
    	sql.append("           AND P.OLD_PART_ID = I.PART_ID\n" );
    	if(null==partCode||partCode.equals("")){
    	}else{
    		sql.append("           AND I.PART_CODE LIKE '%"+partCode+"%'\n" );
    	}
    	if(null==partName||partName.equals("")){
    	}else{
    		sql.append("           AND I.PART_CODE LIKE '%"+partName+"%'\n" );
    	}
    	sql.append("         GROUP BY I.PART_ID, I.PART_CODE, I.PART_NAME, I.F_POSITION_NAME) T\n" );
    	sql.append(" GROUP BY T.PART_ID,\n" );
    	sql.append("          T.PART_CODE,\n" );
    	sql.append("          T.PART_NAME,\n" );
    	sql.append("          T.F_POSITION_NAME,\n" );
    	sql.append("          T.OLD_PART_COUNT01,\n" );
    	sql.append("          T.OLD_PART_COUNT02,\n" );
    	sql.append("          T.OLD_PART_COUNT03,\n" );
    	sql.append("          T.OLD_PART_COUNT04,\n" );
    	sql.append("          T.OLD_PART_COUNT05,\n" );
    	sql.append("          T.OLD_PART_COUNT06,\n" );
    	sql.append("          T.OLD_PART_COUNT07,\n" );
    	sql.append("          T.OLD_PART_COUNT08,\n" );
    	sql.append("          T.OLD_PART_COUNT09,\n" );
    	sql.append("          T.OLD_PART_COUNT10,\n" );
    	sql.append("          T.OLD_PART_COUNT11,\n" );
    	sql.append("          T.OLD_PART_COUNT12,\n" );
    	sql.append("          T.B_OLD_PART_COUNT01,\n" );
    	sql.append("          T.B_OLD_PART_COUNT02,\n" );
    	sql.append("          T.B_OLD_PART_COUNT03,\n" );
    	sql.append("          T.B_OLD_PART_COUNT04,\n" );
    	sql.append("          T.B_OLD_PART_COUNT05,\n" );
    	sql.append("          T.B_OLD_PART_COUNT06,\n" );
    	sql.append("          T.B_OLD_PART_COUNT07,\n" );
    	sql.append("          T.B_OLD_PART_COUNT08,\n" );
    	sql.append("          T.B_OLD_PART_COUNT09,\n" );
    	sql.append("          T.B_OLD_PART_COUNT10,\n" );
    	sql.append("          T.B_OLD_PART_COUNT11,\n" );
    	sql.append("          T.B_OLD_PART_COUNT12");
        //执行方法，不需要传递conn参数
        return factory.select(null, sql.toString());
    }
    
    /**
     * 零件保修更换数量台帐(导出)
     */
    public QuerySet supDownload(String supCode,String supName,String year) throws Exception {
    	
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT QID,\n" );
    	sql.append("       SEARCH_YEAR,\n" );
    	sql.append("       SUPPLIER_ID,\n" );
    	sql.append("       SUPPLIER_NAME,\n" );
    	sql.append("       SUPPLIER_CODE,\n" );
    	sql.append("       LAST_MONTH,\n" );
    	sql.append("       FIRST_JAN,\n" );
    	sql.append("       SECOND_JAN,\n" );
    	sql.append("       THIRD_JAN,\n" );
    	sql.append("       RATE_JAN_YOY,\n" );
    	sql.append("       RATE_JAN_MOM,\n" );
    	sql.append("       FIRST_FEB,\n" );
    	sql.append("       SECOND_FEB,\n" );
    	sql.append("       THIRD_FEB,\n" );
    	sql.append("       RATE_FEB_YOY,\n" );
    	sql.append("       RATE_FEB_MOM,\n" );
    	sql.append("       FIRST_MAR,\n" );
    	sql.append("       SECOND_MAR,\n" );
    	sql.append("       THIRD_MAR,\n" );
    	sql.append("       RATE_MAR_YOY,\n" );
    	sql.append("       RATE_MAR_MOM,\n" );
    	sql.append("       FIRST_APR,\n" );
    	sql.append("       SECOND_APR,\n" );
    	sql.append("       THIRD_APR,\n" );
    	sql.append("       RATE_APR_YOY,\n" );
    	sql.append("       RATE_APR_MOM,\n" );
    	sql.append("       FIRST_MAY,\n" );
    	sql.append("       SECOND_MAY,\n" );
    	sql.append("       THIRD_MAY,\n" );
    	sql.append("       RATE_MAY_YOY,\n" );
    	sql.append("       RATE_MAY_MOM,\n" );
    	sql.append("       FIRST_JUN,\n" );
    	sql.append("       SECOND_JUN,\n" );
    	sql.append("       THIRD_JUN,\n" );
    	sql.append("       RATE_JUN_YOY,\n" );
    	sql.append("       RATE_JUN_MOM,\n" );
    	sql.append("       FIRST_JULY,\n" );
    	sql.append("       SECOND_JULY,\n" );
    	sql.append("       THIRD_JULY,\n" );
    	sql.append("       RATE_JULY_YOY,\n" );
    	sql.append("       RATE_JULY_MOM,\n" );
    	sql.append("       FIRST_AUGUST,\n" );
    	sql.append("       SECOND_AUGUST,\n" );
    	sql.append("       THIRD_AUGUST,\n" );
    	sql.append("       RATE_AUGUST_YOY,\n" );
    	sql.append("       RATE_AUGUST_MOM,\n" );
    	sql.append("       FIRST_SEPT,\n" );
    	sql.append("       SECOND_SEPT,\n" );
    	sql.append("       THIRD_SEPT,\n" );
    	sql.append("       RATE_SEPT_YOY,\n" );
    	sql.append("       RATE_SEPT_MOM,\n" );
    	sql.append("       FIRST_OCT,\n" );
    	sql.append("       SECOND_OCT,\n" );
    	sql.append("       THIRD_OCT,\n" );
    	sql.append("       RATE_OCT_YOY,\n" );
    	sql.append("       RATE_OCT_MOM,\n" );
    	sql.append("       FIRST_NOV,\n" );
    	sql.append("       SECOND_NOV,\n" );
    	sql.append("       THIRD_NOV,\n" );
    	sql.append("       RATE_NOV_YOY,\n" );
    	sql.append("       RATE_NOV_MOM,\n" );
    	sql.append("       FIRST_DEC,\n" );
    	sql.append("       SECOND_DEC,\n" );
    	sql.append("       THIRD_DEC,\n" );
    	sql.append("       RATE_DEC_YOY,\n" );
    	sql.append("       RATE_DEC_MOM\n" );
    	sql.append("  FROM SE_BU_RECOVERY_QUERY");
    	sql.append("  WHERE SEARCH_YEAR='"+year+"' ");
    	if(null==supCode||supCode.equals("")){}else{
    		sql.append("  AND SUPPLIER_CODE LIKE '%"+supCode+"%'");
    	}
    	if(null==supName||supName.equals("")){}else{
    		sql.append("  AND SUPPLIER_CODE LIKE '%"+supName+"%'");
    	}
    	//执行方法，不需要传递conn参数
    	return factory.select(null, sql.toString());
    }
    /**
     * 强，定保次数查询（下载）
     */
    public QuerySet Qdbdownload(String conditions) throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.CLAIM_ID,\n" );
    	sql.append("       T.CLAIM_NO,\n" );
    	sql.append("       (SELECT TR.DIC_VALUE FROM DIC_TREE TR WHERE TR.ID =T.CLAIM_TYPE)CLAIM_TYPE,\n" );
    	sql.append("       T.VIN,\n" );
    	sql.append("       G.CODE,\n" );
    	sql.append("       G.ONAME,\n" );
    	sql.append("       G1.CODE BSCDM,\n" );
    	sql.append("       G1.ONAME BSCMC,\n" );
    	sql.append("       T.BUY_DATE,\n" );
    	sql.append("       T.OLDPART_FINAL_DATE,\n" );
    	sql.append("       (SELECT M.MODELS_CODE\n" );
    	sql.append("          FROM MAIN_VEHICLE M\n" );
    	sql.append("         WHERE M.VEHICLE_ID = T.VEHICLE_ID) MODELS_CODE\n" );
    	sql.append("  FROM SE_BU_CLAIM T,TM_ORG G ,TM_ORG G1\n" );
    	sql.append("WHERE "+conditions+"\n"); 
    	sql.append("   AND T.ORG_ID = G.ORG_ID\n");
    	sql.append("   AND G.PID = G1.ORG_ID\n");
    	sql.append("   AND T.CLAIM_TYPE IN (301402, 301405)\n" );
    	sql.append("   AND T.CLAIM_STATUS = 301015");
    	return factory.select(null, sql.toString());
    }
    /**
     * 里程差异查询（下载）
     */
    public QuerySet LccyDownload(String conditions) throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.CLAIM_ID,\n" );
    	sql.append("       T.CLAIM_NO,\n" );
    	sql.append("       (SELECT TR.DIC_VALUE FROM DIC_TREE TR WHERE TR.ID =T.CLAIM_TYPE)CLAIM_TYPE,\n" );
    	sql.append("       T.VIN,\n" );
    	sql.append("       G.CODE,\n" );
    	sql.append("       G.ONAME,\n" );
    	sql.append("       G1.CODE BSCDM,\n" );
    	sql.append("       G1.ONAME BSCMC,\n" );
    	sql.append("       T.BUY_DATE,\n" );
    	sql.append("       T.OLDPART_FINAL_DATE,\n" );
    	sql.append("       (SELECT M.MODELS_CODE\n" );
    	sql.append("          FROM MAIN_VEHICLE M\n" );
    	sql.append("         WHERE M.VEHICLE_ID = T.VEHICLE_ID) MODELS_CODE,\n" );
    	sql.append("       (SELECT O.MILEAGE FROM SE_BU_CLAIM_OUT O WHERE O.CLAIM_ID = T.CLAIM_ID)MILEAGE,\n" );
    	sql.append("       (SELECT O.GPS_MILEAGE FROM SE_BU_CLAIM_OUT O WHERE O.CLAIM_ID = T.CLAIM_ID)GPS_MILEAGE,\n" );
    	sql.append("       (SELECT O.GPS_MILEAGE - O.MILEAGE FROM SE_BU_CLAIM_OUT O WHERE O.CLAIM_ID = T.CLAIM_ID)LCC\n" );
    	sql.append("  FROM SE_BU_CLAIM T,TM_ORG G ,TM_ORG G1\n" );
    	sql.append("WHERE "+conditions+"\n"); 
    	sql.append("   AND T.ORG_ID = G.ORG_ID\n");
    	sql.append("   AND G.PID = G1.ORG_ID\n");
    	sql.append("   AND T.CLAIM_STATUS = 301015");
    	return factory.select(null, sql.toString());
    }
    /**
     * 星级激励（下载）
     */
    public QuerySet jljeDownload(String conditions) throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.CLAIM_ID,\n" );
    	sql.append("       T.CLAIM_NO,\n" );
    	sql.append("       T.CLAIM_TYPE,\n" );
    	sql.append("       T.CLAIM_STATUS,\n" );
    	sql.append("       T.VIN,\n" );
    	sql.append("       T.VEHICLE_ID,\n" );
    	sql.append("       G.CODE,\n" );
    	sql.append("       G.ONAME,\n" );
    	sql.append("       G1.CODE BSCDM,\n" );
    	sql.append("       G1.ONAME BSCMC,\n" );
    	sql.append("       T.BUY_DATE,\n" );
    	sql.append("       T.OLDPART_FINAL_DATE,\n" );
    	sql.append("       (SELECT M.MODELS_CODE\n" );
    	sql.append("          FROM MAIN_VEHICLE M\n" );
    	sql.append("         WHERE M.VEHICLE_ID = T.VEHICLE_ID) MODELS_CODE,\n" );
    	sql.append("       (SELECT SUM(F.STAR_LEVEL_UPRICE * F.WORK_COSTS)\n" );
    	sql.append("          FROM SE_BU_CLAIM_FAULT F\n" );
    	sql.append("         WHERE F.CLAIM_ID = T.CLAIM_ID\n" );
    	sql.append("         GROUP BY F.CLAIM_ID) XJJE,\n" );
    	sql.append("       (SELECT SUM(F.ENCOURAGE_UPRICE * F.WORK_COSTS)\n" );
    	sql.append("          FROM SE_BU_CLAIM_FAULT F\n" );
    	sql.append("         WHERE F.CLAIM_ID = T.CLAIM_ID\n" );
    	sql.append("         GROUP BY F.CLAIM_ID)JLJE");
    	sql.append("  FROM SE_BU_CLAIM T,TM_ORG G ,TM_ORG G1\n" );
    	sql.append("WHERE "+conditions+"\n"); 
    	sql.append("   AND T.ORG_ID = G.ORG_ID\n");
    	sql.append("   AND G.PID = G1.ORG_ID\n");
    	sql.append("   AND T.CLAIM_STATUS = 301015");
    	return factory.select(null, sql.toString());
    }
    /**
     * 单车维修频次
     */
    public QuerySet vehicleRepairCounDownload(String conditions) throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.VIN,\n" );
    	sql.append("       COUNT(T.CLAIM_ID) BDS,\n" );
    	sql.append("       (SELECT COUNT(C.CLAIM_ID)\n" );
    	sql.append("          FROM SE_BU_CLAIM C\n" );
    	sql.append("         WHERE C.VIN = T.VIN\n" );
    	sql.append("           AND C.CLAIM_TYPE NOT IN (301402, 301405, 301407, 301406)) WXS,\n" );
    	sql.append("       (SELECT SUM(P.OLD_PART_COUNT)\n" );
    	sql.append("          FROM SE_BU_CLAIM_FAULT_PART P\n" );
    	sql.append("         WHERE P.CLAIM_ID IN\n" );
    	sql.append("               (SELECT CC.CLAIM_ID FROM SE_BU_CLAIM CC WHERE CC.VIN = T.VIN)) JJS\n" );
    	sql.append("  FROM SE_BU_CLAIM T\n" );
    	sql.append("WHERE "+conditions+"\n"); 
    	sql.append(" AND T.CREATE_TIME > TO_DATE('2014-11-01','YYYY-MM-DD') \n"); 
    	sql.append(" GROUP BY T.VIN");
     	return factory.select(null, sql.toString());
    }
    /**
     * 服务站旧件数据统计表
     */
    public QuerySet oldPartCountDownload(String conditions) throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT G1.ONAME       BSC,\n" );
    	sql.append("       G.ONAME        FWZ,\n" );
    	sql.append("       RL.R_ORGNAME   JZD,\n" );
    	sql.append("       A.PRODUCE_DATE,\n" );
    	sql.append("       A.SHOULD_COUNT,\n" );
    	sql.append("       A.OUGHT_COUNT,\n" );
    	sql.append("       A.ALREADY_IN,\n" );
    	sql.append("       A.ALREADY_OUT,\n" );
    	sql.append("       A.ACTUL_COUNT\n" );
    	sql.append("  FROM TM_ORG G,\n" );
    	sql.append("       TM_ORG G1,\n" );
    	sql.append("       SE_BA_RETURN_DEALER_RELATION RL,\n" );
    	sql.append("       (SELECT C.ORG_ID,\n" );
    	sql.append("               R.PRODUCE_DATE,\n" );
    	sql.append("               SUM(D.SHOULD_COUNT) SHOULD_COUNT,\n" );
    	sql.append("               SUM(D.OUGHT_COUNT) OUGHT_COUNT,\n" );
    	sql.append("               SUM(D.ALREADY_IN) ALREADY_IN,\n" );
    	sql.append("               SUM(D.ALREADY_OUT) ALREADY_OUT,\n" );
    	sql.append("               SUM(D.ACTUL_COUNT) ACTUL_COUNT\n" );
    	sql.append("          FROM SE_BU_RETURNORDER_DETAIL D,\n" );
    	sql.append("               SE_BU_CLAIM              C,\n" );
    	sql.append("               SE_BU_RETURN_ORDER       R\n" );
    	sql.append("         WHERE C.CLAIM_ID = D.CLAIM_ID\n" );
    	sql.append("           AND R.ORDER_ID = D.ORDER_ID\n" );
    	sql.append("         GROUP BY C.ORG_ID, R.PRODUCE_DATE) A\n");
    	sql.append("WHERE "+conditions+"\n");
    	sql.append("  AND A.ORG_ID = G.ORG_ID\n" );
    	sql.append("  AND G.PID = G1.ORG_ID\n" );
    	sql.append("  AND RL.D_ORGID = G.ORG_ID\n" );
    	sql.append("ORDER BY A.PRODUCE_DATE, G1.ONAME, G.ONAME, RL.R_ORGNAME");
    	return factory.select(null, sql.toString());
    }
    /**
     * 供应商旧件数据统计表
     */
    public QuerySet supOldPartCountDownload(String conditions) throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT G1.ONAME       BSC,\n" );
    	sql.append("       G.ONAME        FWZ,\n" );
    	sql.append("       A.PROSUPPLY_NAME,\n" );
    	sql.append("       A.PRODUCE_DATE,\n" );
    	sql.append("       A.SHOULD_COUNT,\n" );
    	sql.append("       A.OUGHT_COUNT,\n" );
    	sql.append("       A.ALREADY_IN,\n" );
    	sql.append("       A.ALREADY_OUT,\n" );
    	sql.append("       A.ACTUL_COUNT\n" );
    	sql.append("  FROM TM_ORG G,\n" );
    	sql.append("       TM_ORG G1,\n" );
    	sql.append("       (SELECT C.ORG_ID,\n" );
    	sql.append("               D.PROSUPPLY_ID,\n" );
    	sql.append("               D.PROSUPPLY_NAME,\n" );
    	sql.append("               R.PRODUCE_DATE,\n" );
    	sql.append("               SUM(D.SHOULD_COUNT) SHOULD_COUNT,\n" );
    	sql.append("               SUM(D.OUGHT_COUNT) OUGHT_COUNT,\n" );
    	sql.append("               SUM(D.ALREADY_IN) ALREADY_IN,\n" );
    	sql.append("               SUM(D.ALREADY_OUT) ALREADY_OUT,\n" );
    	sql.append("               SUM(D.ACTUL_COUNT) ACTUL_COUNT\n" );
    	sql.append("          FROM SE_BU_RETURNORDER_DETAIL D,\n" );
    	sql.append("               SE_BU_CLAIM              C,\n" );
    	sql.append("               SE_BU_RETURN_ORDER       R\n" );
    	sql.append("         WHERE C.CLAIM_ID = D.CLAIM_ID\n" );
    	sql.append("           AND R.ORDER_ID = D.ORDER_ID\n" );
    	sql.append("         GROUP BY C.ORG_ID, R.PRODUCE_DATE, D.PROSUPPLY_ID, D.PROSUPPLY_NAME) A\n");
    	sql.append("WHERE "+conditions+"\n");
    	sql.append("  AND A.ORG_ID = G.ORG_ID\n" );
    	sql.append("  AND G.PID = G1.ORG_ID\n" );
    	sql.append("ORDER BY A.PROSUPPLY_NAME,A.PRODUCE_DATE, G1.ONAME, G.ONAME");
    	return factory.select(null, sql.toString());
    }
    /**
     * 陕重汽全国服务站返回旧件
     */
    public QuerySet oldPartReturnCountDownload(String conditions) throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT BW.WAREHOUSE_NAME,\n" );
    	sql.append("       G.CODE FWZDM,\n" );
    	sql.append("       G.ONAME FWZMC,\n" );
    	sql.append("       G.SNAME FWZJC,\n" );
    	sql.append("       G1.ONAME BSCMC,\n" );
    	sql.append("       GG.ONAME JZDMC,\n" );
    	sql.append("       SUM(NVL(D.SHOULD_COUNT, 0)) SHOULD_COUNT,\n" );
    	sql.append("       SUM(NVL(D.OUGHT_COUNT, 0)) OUGHT_COUNT,\n" );
    	sql.append("       SUM(NVL(D.ALREADY_IN, 0)) ALREADY_IN\n" );
    	sql.append("  FROM SE_BU_RETURN_ORDER           RO,\n" );
    	sql.append("       SE_BU_RETURNORDER_DETAIL     D,\n" );
    	sql.append("       SE_BU_CLAIM                  C,\n" );
    	sql.append("       TM_ORG                       G,\n" );
    	sql.append("       TM_ORG                       G1,\n" );
    	sql.append("       TM_ORG                       GG,\n" );
    	sql.append("       SE_BU_WAREHOUSE_CENTROSTIGMA WC,\n" );
    	sql.append("       SE_BA_WAREHOUSE              BW\n" );
    	sql.append(" WHERE "+conditions+"\n" );
    	sql.append("   AND RO.ORDER_STATUS in ('302505', '302506')\n" );
    	sql.append("   AND RO.ORDER_ID = D.ORDER_ID\n" );
    	sql.append("   AND C.CLAIM_ID = D.CLAIM_ID\n" );
    	sql.append("   AND RO.ORDER_ID = D.ORDER_ID\n" );
    	sql.append("   AND BW.WAREHOUSE_ID = WC.WAREHOUSE_ID\n" );
    	sql.append("   AND G.ORG_ID = C.ORG_ID\n" );
    	sql.append("   AND G.PID = G1.ORG_ID\n" );
    	sql.append("   AND GG.ORG_ID = RO.ORG_ID\n" );
    	sql.append(" GROUP BY BW.WAREHOUSE_NAME, GG.ONAME, G.ONAME, G.CODE, G.SNAME, G1.ONAME\n" );
    	sql.append(" ORDER BY BW.WAREHOUSE_NAME, GG.ONAME, G.ONAME, G.CODE, G.SNAME, G1.ONAME");
    	return factory.select(null, sql.toString());
    }
    /**
     * 旧件管理费用统计
     */
    public QuerySet oldPartManageDownload(String conditions) throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.SUPPLIER_CODE,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("       T.PART_CODE,\n" );
    	sql.append("       T.PART_NAME,\n" );
    	sql.append("       P.SE_REPRICE,\n" );
    	sql.append("       P.OLD_MANAGE_FEE,\n" );
    	sql.append("       SUM(T.OUT_AMOUNT) OUT_AMOUNT,\n" );
    	sql.append("       SUM(T.OUT_AMOUNT*P.SE_REPRICE*P.OLD_MANAGE_FEE)OUT_JE\n" );
    	sql.append("  FROM SE_BU_RETURN_OUT T,PT_BA_INFO P\n" );
    	sql.append(" WHERE "+conditions+"\n" );
    	sql.append("   AND T.OUT_TYPE = 303101\n" );
    	sql.append("   AND P.PART_ID = T.PART_ID\n" );
    	sql.append(" GROUP BY  T.SUPPLIER_CODE,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("       T.PART_CODE,\n" );
    	sql.append("       T.PART_NAME,\n" );
    	sql.append("       P.SE_REPRICE,\n" );
    	sql.append("       P.OLD_MANAGE_FEE");
    	return factory.select(null, sql.toString());
    }
    public boolean delete(String year) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" DELETE SE_BU_RECOVERY_QUERY A WHERE A.SEARCH_YEAR = '"+year+"' \n");
    	return factory.delete(sql.toString(), null);
    }

	public void callProcedure(String year) throws SQLException {
		CallableStatement proc = null;
        proc = factory.getConnection().prepareCall("{call P_INSERT_RECOVERY_QUERY(?)}");
        proc.setString(1, year);
        proc.execute();
		
	}
}
