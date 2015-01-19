package com.org.dms.dao.service.reportForms;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class ServiceCostReportDao extends BaseDAO {
	// 定义instance
	public static final ServiceCostReportDao getInstance(ActionContext atx) {
		ServiceCostReportDao dao = new ServiceCostReportDao();
		atx.setDBFactory(dao.factory);
		return dao;
	}

	/**
	 * @throws Exception
	 *             设定文件
	 * @return BaseResultSet 返回类型
	 */
	public BaseResultSet search(PageManager page, User user, String conditions,String aYear,String bYear,String cYear)
			throws Exception {
		// 定义返回结果集
		BaseResultSet bs = null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT NAME,\n" );
		sql.append("       A1,\n" );
		sql.append("       A2,\n" );
		sql.append("       A3,\n" );
		sql.append("       CASE\n" );
		sql.append("         WHEN A2 = 0 THEN\n" );
		sql.append("          0\n" );
		sql.append("         ELSE\n" );
		sql.append("\n" );
		sql.append("          ROUND((A1 - A2) / A2, 2)\n" );
		sql.append("       END A4,\n" );
		sql.append("       CASE\n" );
		sql.append("         WHEN A2 = 0 THEN\n" );
		sql.append("          0\n" );
		sql.append("         ELSE\n" );
		sql.append("\n" );
		sql.append("          ROUND((A1 - A3) / A3, 2)\n" );
		sql.append("       END A5\n" );
		sql.append("  FROM (SELECT '网络终审工时费' NAME,\n" );
		sql.append("\n" );
		sql.append("               SUM(CASE\n" );
		sql.append("                     WHEN A.SETTLE_DATE =\n" );
		sql.append("                      '"+aYear+"'\n" );
		sql.append("                      THEN\n" );
		sql.append("                      WORK_COSTS\n" );
		sql.append("                   END) A1,\n" );
		sql.append("               SUM(CASE\n" );
		sql.append("                     WHEN A.SETTLE_DATE =\n" );
		sql.append("                      '"+bYear+"'\n" );
		sql.append("                      THEN\n" );
		sql.append("                      WORK_COSTS\n" );
		sql.append("                   END) A2,\n" );
		sql.append("               SUM(CASE\n" );
		sql.append("                     WHEN A.SETTLE_DATE =\n" );
		sql.append("'"+cYear+"'" );
		sql.append("                       THEN\n" );
		sql.append("                      WORK_COSTS\n" );
		sql.append("                   END) A3\n" );
		sql.append("\n" );
		sql.append("          FROM (SELECT SETTLE_DATE,\n" );
		sql.append("                       SUM(WORK_COSTS) WORK_COSTS,\n" );
		sql.append("                       SUM(MATERIAL_COSTS) MATERIAL_COSTS,\n" );
		sql.append("                       SUM(SUMMARY) SUMMARY\n" );
		sql.append("                  FROM (SELECT ORG_CODE,\n" );
		sql.append("                               TO_CHAR(T.SETTLE_DATE, 'YYYY-MM') SETTLE_DATE,\n" );
		sql.append("\n" );
		sql.append("                               (SELECT SUM(NVL(B.WORK_COSTS, 0))\n" );
		sql.append("                                  FROM SE_BU_CLAIM_SETTLE_REL B\n" );
		sql.append("                                 WHERE B.SETTLE_ID = T.SETTLE_ID) WORK_COSTS,\n" );
		sql.append("                               (SELECT SUM(NVL(B.MATERIAL_COSTS, 0))\n" );
		sql.append("                                  FROM SE_BU_CLAIM_SETTLE_REL B\n" );
		sql.append("                                 WHERE B.SETTLE_ID = T.SETTLE_ID) MATERIAL_COSTS,\n" );
		sql.append("                               NVL(T.SUMMARY, 0) SUMMARY\n" );
		sql.append("                          FROM SE_BU_CLAIM_SETTLE T\n" );
		sql.append("                         WHERE 1 = 1\n" );
		sql.append("                           AND (TO_CHAR(T.SETTLE_DATE, 'YYYY-MM') =\n" );
		sql.append("                           '"+aYear+"'\n" );
		sql.append("                            OR\n" );
		sql.append("                               TO_CHAR(T.SETTLE_DATE, 'YYYY-MM') =\n" );
		sql.append("                                '"+bYear+"'\n" );
		sql.append("                                OR\n" );
		sql.append("                               TO_CHAR(T.SETTLE_DATE, 'YYYY-MM') =\n" );
		sql.append("                                '"+cYear+"'\n" );
		sql.append("                                )\n" );
		sql.append("                           AND T.STATUS = 100201\n" );
		sql.append("                        -- AND ORG_CODE = '0022601'\n" );
		sql.append("                        )\n" );
		sql.append("                 GROUP BY SETTLE_DATE) A)\n" );
		sql.append("UNION\n" );
		sql.append("\n" );
		sql.append("SELECT NAME,\n" );
		sql.append("       A1,\n" );
		sql.append("       A2,\n" );
		sql.append("       A3,\n" );
		sql.append("       CASE\n" );
		sql.append("         WHEN A2 = 0 THEN\n" );
		sql.append("          0\n" );
		sql.append("         ELSE\n" );
		sql.append("\n" );
		sql.append("          ROUND((A1 - A2) / A2, 2)\n" );
		sql.append("       END A4,\n" );
		sql.append("       CASE\n" );
		sql.append("         WHEN A2 = 0 THEN\n" );
		sql.append("          0\n" );
		sql.append("         ELSE\n" );
		sql.append("\n" );
		sql.append("          ROUND((A1 - A3) / A3, 2)\n" );
		sql.append("       END A5\n" );
		sql.append("  FROM (SELECT '网络终审材料费' NAME,\n" );
		sql.append("\n" );
		sql.append("               SUM(CASE\n" );
		sql.append("                     WHEN A.SETTLE_DATE =\n" );
		sql.append("                      '"+aYear+"'\n" );
		sql.append("                       THEN\n" );
		sql.append("                      MATERIAL_COSTS\n" );
		sql.append("                   END) A1,\n" );
		sql.append("               SUM(CASE\n" );
		sql.append("                     WHEN A.SETTLE_DATE =\n" );
		sql.append("                     '"+bYear+"'\n" );
		sql.append("                     THEN\n" );
		sql.append("                      MATERIAL_COSTS\n" );
		sql.append("                   END) A2,\n" );
		sql.append("               SUM(CASE\n" );
		sql.append("                     WHEN A.SETTLE_DATE =\n" );
		sql.append("'"+cYear+"'" );
		sql.append("                      THEN\n" );
		sql.append("                      MATERIAL_COSTS\n" );
		sql.append("                   END) A3\n" );
		sql.append("\n" );
		sql.append("          FROM (SELECT SETTLE_DATE,\n" );
		sql.append("                       SUM(WORK_COSTS) WORK_COSTS,\n" );
		sql.append("                       SUM(MATERIAL_COSTS) MATERIAL_COSTS,\n" );
		sql.append("                       SUM(SUMMARY) SUMMARY\n" );
		sql.append("                  FROM (SELECT ORG_CODE,\n" );
		sql.append("                               TO_CHAR(T.SETTLE_DATE, 'YYYY-MM') SETTLE_DATE,\n" );
		sql.append("\n" );
		sql.append("                               (SELECT SUM(NVL(B.WORK_COSTS, 0))\n" );
		sql.append("                                  FROM SE_BU_CLAIM_SETTLE_REL B\n" );
		sql.append("                                 WHERE B.SETTLE_ID = T.SETTLE_ID) WORK_COSTS,\n" );
		sql.append("                               (SELECT SUM(NVL(B.MATERIAL_COSTS, 0))\n" );
		sql.append("                                  FROM SE_BU_CLAIM_SETTLE_REL B\n" );
		sql.append("                                 WHERE B.SETTLE_ID = T.SETTLE_ID) MATERIAL_COSTS,\n" );
		sql.append("                               NVL(T.SUMMARY, 0) SUMMARY\n" );
		sql.append("                          FROM SE_BU_CLAIM_SETTLE T\n" );
		sql.append("                         WHERE 1 = 1\n" );
		sql.append("                           AND (TO_CHAR(T.SETTLE_DATE, 'YYYY-MM') =\n" );
		sql.append("                           '"+aYear+"'\n" );
		sql.append("                           OR\n" );
		sql.append("                               TO_CHAR(T.SETTLE_DATE, 'YYYY-MM') =\n" );
		sql.append("                                '"+bYear+"'\n" );
		sql.append("                                 OR\n" );
		sql.append("                               TO_CHAR(T.SETTLE_DATE, 'YYYY-MM') =\n" );
		sql.append("                               '"+cYear+"'\n" );
		sql.append("                               )\n" );
		sql.append("                           AND T.STATUS = 100201\n" );
		sql.append("                        -- AND ORG_CODE = '0022601'\n" );
		sql.append("                        )\n" );
		sql.append("                 GROUP BY SETTLE_DATE) A)\n" );
		sql.append("UNION\n" );
		sql.append("\n" );
		sql.append("SELECT NAME,\n" );
		sql.append("       A1,\n" );
		sql.append("       A2,\n" );
		sql.append("       A3,\n" );
		sql.append("       CASE\n" );
		sql.append("         WHEN A2 = 0 THEN\n" );
		sql.append("          0\n" );
		sql.append("         ELSE\n" );
		sql.append("\n" );
		sql.append("          ROUND((A1 - A2) / A2, 2)\n" );
		sql.append("       END A4,\n" );
		sql.append("       CASE\n" );
		sql.append("         WHEN A2 = 0 THEN\n" );
		sql.append("          0\n" );
		sql.append("         ELSE\n" );
		sql.append("\n" );
		sql.append("          ROUND((A1 - A3) / A3, 2)\n" );
		sql.append("       END A5\n" );
		sql.append("  FROM (SELECT '网络终审总费用' NAME,\n" );
		sql.append("\n" );
		sql.append("               SUM(CASE\n" );
		sql.append("                     WHEN A.SETTLE_DATE =\n" );
		sql.append("                      '"+aYear+"'\n" );
		sql.append("                      THEN\n" );
		sql.append("                      SUMMARY\n" );
		sql.append("                   END) A1,\n" );
		sql.append("               SUM(CASE\n" );
		sql.append("                     WHEN A.SETTLE_DATE =\n" );
		sql.append("                     '"+bYear+"'\n" );
		sql.append("                      THEN\n" );
		sql.append("                      SUMMARY\n" );
		sql.append("                   END) A2,\n" );
		sql.append("               SUM(CASE\n" );
		sql.append("                     WHEN A.SETTLE_DATE =\n" );
		sql.append("'"+cYear+"'" );
		sql.append("                       THEN\n" );
		sql.append("                      SUMMARY\n" );
		sql.append("                   END) A3\n" );
		sql.append("\n" );
		sql.append("          FROM (SELECT SETTLE_DATE,\n" );
		sql.append("                       SUM(WORK_COSTS) WORK_COSTS,\n" );
		sql.append("                       SUM(MATERIAL_COSTS) MATERIAL_COSTS,\n" );
		sql.append("                       SUM(SUMMARY) SUMMARY\n" );
		sql.append("                  FROM (SELECT ORG_CODE,\n" );
		sql.append("                               TO_CHAR(T.SETTLE_DATE, 'YYYY-MM') SETTLE_DATE,\n" );
		sql.append("\n" );
		sql.append("                               (SELECT SUM(NVL(B.WORK_COSTS, 0))\n" );
		sql.append("                                  FROM SE_BU_CLAIM_SETTLE_REL B\n" );
		sql.append("                                 WHERE B.SETTLE_ID = T.SETTLE_ID) WORK_COSTS,\n" );
		sql.append("                               (SELECT SUM(NVL(B.MATERIAL_COSTS, 0))\n" );
		sql.append("                                  FROM SE_BU_CLAIM_SETTLE_REL B\n" );
		sql.append("                                 WHERE B.SETTLE_ID = T.SETTLE_ID) MATERIAL_COSTS,\n" );
		sql.append("                               NVL(T.SUMMARY, 0) SUMMARY\n" );
		sql.append("                          FROM SE_BU_CLAIM_SETTLE T\n" );
		sql.append("                         WHERE 1 = 1\n" );
		sql.append("                           AND (TO_CHAR(T.SETTLE_DATE, 'YYYY-MM') =\n" );
		sql.append("                            '"+aYear+"'\n" );
		sql.append("                            OR\n" );
		sql.append("                               TO_CHAR(T.SETTLE_DATE, 'YYYY-MM') =\n" );
		sql.append("                                '"+bYear+"'\n" );
		sql.append("                                 OR\n" );
		sql.append("                               TO_CHAR(T.SETTLE_DATE, 'YYYY-MM') =\n" );
		sql.append("                                '"+cYear+"'\n" );
		sql.append("                                )\n" );
		sql.append("                           AND T.STATUS = 100201\n" );
		sql.append("                        -- AND ORG_CODE = '0022601'\n" );
		sql.append("                        )\n" );
		sql.append("                 GROUP BY SETTLE_DATE) A)");


		// 执行方法，不需要传递conn参数
		bs = factory.select(sql.toString(), page);
		return bs;
	}

	public QuerySet download(String conditions, String aYear,String bYear,String cYear) throws Exception {

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT  ROWNUM,T.* FROM(");
		sql.append("SELECT NAME,\n" );
		sql.append("       A1,\n" );
		sql.append("       A2,\n" );
		sql.append("       A3,\n" );
		sql.append("       CASE\n" );
		sql.append("         WHEN A2 = 0 THEN\n" );
		sql.append("          0\n" );
		sql.append("         ELSE\n" );
		sql.append("\n" );
		sql.append("          ROUND((A1 - A2) / A2, 2)\n" );
		sql.append("       END A4,\n" );
		sql.append("       CASE\n" );
		sql.append("         WHEN A2 = 0 THEN\n" );
		sql.append("          0\n" );
		sql.append("         ELSE\n" );
		sql.append("\n" );
		sql.append("          ROUND((A1 - A3) / A3, 2)\n" );
		sql.append("       END A5\n" );
		sql.append("  FROM (SELECT '网络终审工时费' NAME,\n" );
		sql.append("\n" );
		sql.append("               SUM(CASE\n" );
		sql.append("                     WHEN A.SETTLE_DATE =\n" );
		sql.append("                      '"+aYear+"'\n" );
		sql.append("                      THEN\n" );
		sql.append("                      WORK_COSTS\n" );
		sql.append("                   END) A1,\n" );
		sql.append("               SUM(CASE\n" );
		sql.append("                     WHEN A.SETTLE_DATE =\n" );
		sql.append("                      '"+bYear+"'\n" );
		sql.append("                      THEN\n" );
		sql.append("                      WORK_COSTS\n" );
		sql.append("                   END) A2,\n" );
		sql.append("               SUM(CASE\n" );
		sql.append("                     WHEN A.SETTLE_DATE =\n" );
		sql.append("'"+cYear+"'" );
		sql.append("                       THEN\n" );
		sql.append("                      WORK_COSTS\n" );
		sql.append("                   END) A3\n" );
		sql.append("\n" );
		sql.append("          FROM (SELECT SETTLE_DATE,\n" );
		sql.append("                       SUM(WORK_COSTS) WORK_COSTS,\n" );
		sql.append("                       SUM(MATERIAL_COSTS) MATERIAL_COSTS,\n" );
		sql.append("                       SUM(SUMMARY) SUMMARY\n" );
		sql.append("                  FROM (SELECT ORG_CODE,\n" );
		sql.append("                               TO_CHAR(T.SETTLE_DATE, 'YYYY-MM') SETTLE_DATE,\n" );
		sql.append("\n" );
		sql.append("                               (SELECT SUM(NVL(B.WORK_COSTS, 0))\n" );
		sql.append("                                  FROM SE_BU_CLAIM_SETTLE_REL B\n" );
		sql.append("                                 WHERE B.SETTLE_ID = T.SETTLE_ID) WORK_COSTS,\n" );
		sql.append("                               (SELECT SUM(NVL(B.MATERIAL_COSTS, 0))\n" );
		sql.append("                                  FROM SE_BU_CLAIM_SETTLE_REL B\n" );
		sql.append("                                 WHERE B.SETTLE_ID = T.SETTLE_ID) MATERIAL_COSTS,\n" );
		sql.append("                               NVL(T.SUMMARY, 0) SUMMARY\n" );
		sql.append("                          FROM SE_BU_CLAIM_SETTLE T\n" );
		sql.append("                         WHERE 1 = 1\n" );
		sql.append("                           AND (TO_CHAR(T.SETTLE_DATE, 'YYYY-MM') =\n" );
		sql.append("                           '"+aYear+"'\n" );
		sql.append("                            OR\n" );
		sql.append("                               TO_CHAR(T.SETTLE_DATE, 'YYYY-MM') =\n" );
		sql.append("                                '"+bYear+"'\n" );
		sql.append("                                OR\n" );
		sql.append("                               TO_CHAR(T.SETTLE_DATE, 'YYYY-MM') =\n" );
		sql.append("                                '"+cYear+"'\n" );
		sql.append("                                )\n" );
		sql.append("                           AND T.STATUS = 100201\n" );
		sql.append("                        -- AND ORG_CODE = '0022601'\n" );
		sql.append("                        )\n" );
		sql.append("                 GROUP BY SETTLE_DATE) A)\n" );
		sql.append("UNION\n" );
		sql.append("\n" );
		sql.append("SELECT NAME,\n" );
		sql.append("       A1,\n" );
		sql.append("       A2,\n" );
		sql.append("       A3,\n" );
		sql.append("       CASE\n" );
		sql.append("         WHEN A2 = 0 THEN\n" );
		sql.append("          0\n" );
		sql.append("         ELSE\n" );
		sql.append("\n" );
		sql.append("          ROUND((A1 - A2) / A2, 2)\n" );
		sql.append("       END A4,\n" );
		sql.append("       CASE\n" );
		sql.append("         WHEN A2 = 0 THEN\n" );
		sql.append("          0\n" );
		sql.append("         ELSE\n" );
		sql.append("\n" );
		sql.append("          ROUND((A1 - A3) / A3, 2)\n" );
		sql.append("       END A5\n" );
		sql.append("  FROM (SELECT '网络终审材料费' NAME,\n" );
		sql.append("\n" );
		sql.append("               SUM(CASE\n" );
		sql.append("                     WHEN A.SETTLE_DATE =\n" );
		sql.append("                      '"+aYear+"'\n" );
		sql.append("                       THEN\n" );
		sql.append("                      MATERIAL_COSTS\n" );
		sql.append("                   END) A1,\n" );
		sql.append("               SUM(CASE\n" );
		sql.append("                     WHEN A.SETTLE_DATE =\n" );
		sql.append("                     '"+bYear+"'\n" );
		sql.append("                     THEN\n" );
		sql.append("                      MATERIAL_COSTS\n" );
		sql.append("                   END) A2,\n" );
		sql.append("               SUM(CASE\n" );
		sql.append("                     WHEN A.SETTLE_DATE =\n" );
		sql.append("'"+cYear+"'" );
		sql.append("                      THEN\n" );
		sql.append("                      MATERIAL_COSTS\n" );
		sql.append("                   END) A3\n" );
		sql.append("\n" );
		sql.append("          FROM (SELECT SETTLE_DATE,\n" );
		sql.append("                       SUM(WORK_COSTS) WORK_COSTS,\n" );
		sql.append("                       SUM(MATERIAL_COSTS) MATERIAL_COSTS,\n" );
		sql.append("                       SUM(SUMMARY) SUMMARY\n" );
		sql.append("                  FROM (SELECT ORG_CODE,\n" );
		sql.append("                               TO_CHAR(T.SETTLE_DATE, 'YYYY-MM') SETTLE_DATE,\n" );
		sql.append("\n" );
		sql.append("                               (SELECT SUM(NVL(B.WORK_COSTS, 0))\n" );
		sql.append("                                  FROM SE_BU_CLAIM_SETTLE_REL B\n" );
		sql.append("                                 WHERE B.SETTLE_ID = T.SETTLE_ID) WORK_COSTS,\n" );
		sql.append("                               (SELECT SUM(NVL(B.MATERIAL_COSTS, 0))\n" );
		sql.append("                                  FROM SE_BU_CLAIM_SETTLE_REL B\n" );
		sql.append("                                 WHERE B.SETTLE_ID = T.SETTLE_ID) MATERIAL_COSTS,\n" );
		sql.append("                               NVL(T.SUMMARY, 0) SUMMARY\n" );
		sql.append("                          FROM SE_BU_CLAIM_SETTLE T\n" );
		sql.append("                         WHERE 1 = 1\n" );
		sql.append("                           AND (TO_CHAR(T.SETTLE_DATE, 'YYYY-MM') =\n" );
		sql.append("                           '"+aYear+"'\n" );
		sql.append("                           OR\n" );
		sql.append("                               TO_CHAR(T.SETTLE_DATE, 'YYYY-MM') =\n" );
		sql.append("                                '"+bYear+"'\n" );
		sql.append("                                 OR\n" );
		sql.append("                               TO_CHAR(T.SETTLE_DATE, 'YYYY-MM') =\n" );
		sql.append("                               '"+cYear+"'\n" );
		sql.append("                               )\n" );
		sql.append("                           AND T.STATUS = 100201\n" );
		sql.append("                        -- AND ORG_CODE = '0022601'\n" );
		sql.append("                        )\n" );
		sql.append("                 GROUP BY SETTLE_DATE) A)\n" );
		sql.append("UNION\n" );
		sql.append("\n" );
		sql.append("SELECT NAME,\n" );
		sql.append("       A1,\n" );
		sql.append("       A2,\n" );
		sql.append("       A3,\n" );
		sql.append("       CASE\n" );
		sql.append("         WHEN A2 = 0 THEN\n" );
		sql.append("          0\n" );
		sql.append("         ELSE\n" );
		sql.append("\n" );
		sql.append("          ROUND((A1 - A2) / A2, 2)\n" );
		sql.append("       END A4,\n" );
		sql.append("       CASE\n" );
		sql.append("         WHEN A2 = 0 THEN\n" );
		sql.append("          0\n" );
		sql.append("         ELSE\n" );
		sql.append("\n" );
		sql.append("          ROUND((A1 - A3) / A3, 2)\n" );
		sql.append("       END A5\n" );
		sql.append("  FROM (SELECT '网络终审总费用' NAME,\n" );
		sql.append("\n" );
		sql.append("               SUM(CASE\n" );
		sql.append("                     WHEN A.SETTLE_DATE =\n" );
		sql.append("                      '"+aYear+"'\n" );
		sql.append("                      THEN\n" );
		sql.append("                      SUMMARY\n" );
		sql.append("                   END) A1,\n" );
		sql.append("               SUM(CASE\n" );
		sql.append("                     WHEN A.SETTLE_DATE =\n" );
		sql.append("                     '"+bYear+"'\n" );
		sql.append("                      THEN\n" );
		sql.append("                      SUMMARY\n" );
		sql.append("                   END) A2,\n" );
		sql.append("               SUM(CASE\n" );
		sql.append("                     WHEN A.SETTLE_DATE =\n" );
		sql.append("'"+cYear+"'" );
		sql.append("                       THEN\n" );
		sql.append("                      SUMMARY\n" );
		sql.append("                   END) A3\n" );
		sql.append("\n" );
		sql.append("          FROM (SELECT SETTLE_DATE,\n" );
		sql.append("                       SUM(WORK_COSTS) WORK_COSTS,\n" );
		sql.append("                       SUM(MATERIAL_COSTS) MATERIAL_COSTS,\n" );
		sql.append("                       SUM(SUMMARY) SUMMARY\n" );
		sql.append("                  FROM (SELECT ORG_CODE,\n" );
		sql.append("                               TO_CHAR(T.SETTLE_DATE, 'YYYY-MM') SETTLE_DATE,\n" );
		sql.append("\n" );
		sql.append("                               (SELECT SUM(NVL(B.WORK_COSTS, 0))\n" );
		sql.append("                                  FROM SE_BU_CLAIM_SETTLE_REL B\n" );
		sql.append("                                 WHERE B.SETTLE_ID = T.SETTLE_ID) WORK_COSTS,\n" );
		sql.append("                               (SELECT SUM(NVL(B.MATERIAL_COSTS, 0))\n" );
		sql.append("                                  FROM SE_BU_CLAIM_SETTLE_REL B\n" );
		sql.append("                                 WHERE B.SETTLE_ID = T.SETTLE_ID) MATERIAL_COSTS,\n" );
		sql.append("                               NVL(T.SUMMARY, 0) SUMMARY\n" );
		sql.append("                          FROM SE_BU_CLAIM_SETTLE T\n" );
		sql.append("                         WHERE 1 = 1\n" );
		sql.append("                           AND (TO_CHAR(T.SETTLE_DATE, 'YYYY-MM') =\n" );
		sql.append("                            '"+aYear+"'\n" );
		sql.append("                            OR\n" );
		sql.append("                               TO_CHAR(T.SETTLE_DATE, 'YYYY-MM') =\n" );
		sql.append("                                '"+bYear+"'\n" );
		sql.append("                                 OR\n" );
		sql.append("                               TO_CHAR(T.SETTLE_DATE, 'YYYY-MM') =\n" );
		sql.append("                                '"+cYear+"'\n" );
		sql.append("                                )\n" );
		sql.append("                           AND T.STATUS = 100201\n" );
		sql.append("                        -- AND ORG_CODE = '0022601'\n" );
		sql.append("                        )\n" );
		sql.append("                 GROUP BY SETTLE_DATE) A)");


		sql.append("    ) T ORDER BY ROWNUM");
		// 执行方法，不需要传递conn参数
		return factory.select(null, sql.toString());
	}
}