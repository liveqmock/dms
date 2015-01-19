package com.org.dms.dao.part.financeMng.searchInfo;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class DealerRebaseSearchMngDao extends BaseDAO{
	public  static final DealerRebaseSearchMngDao getInstance(ActionContext atx)
    {
		DealerRebaseSearchMngDao dao = new DealerRebaseSearchMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	public BaseResultSet rebateSearch(PageManager page, User user, String conditions) throws Exception
    {
/*    	String wheres = conditions;
    	wheres +="AND T.DEPTCODE = T1.CODE AND T1.PID = T2.ORG_ID ORDER BY T.DEPTCODE, T2.CODE";
    	page.setFilter(wheres);*/
    	BaseResultSet bs = null;
    	/*StringBuffer sql= new StringBuffer();
    	sql.append("SELECT A.*, NVL(ROUND(A.ZZFL / B.ZZFL * 100, 2), 0) RATE\n" );
    	sql.append("  FROM (SELECT T2.ONAME,\n" );
    	sql.append("               T2.CODE,\n" );
    	sql.append("               T.DEPTCODE,\n" );
    	sql.append("               T.DEPTNAME,\n" );
    	sql.append("               T.ZZFL,\n" );
    	sql.append("               T.YPCGJE,\n" );
    	sql.append("               T.ILCGJE,\n" );
    	sql.append("               T.JLCGJE,\n" );
    	sql.append("               T.HLCGJE,\n" );
    	sql.append("               T.DZCJE,\n" );
    	sql.append("               T.JD,\n" );
    	sql.append("               T.GLFJE,\n" );
    	sql.append("               T.FLCGJE + T.GLCGJE AS QTBYJE\n" );
    	sql.append("          FROM PSZXJDFL T, TM_ORG T1, TM_ORG T2\n" );
    	sql.append("         WHERE "+conditions+"\n" );
    	sql.append("           AND T.DEPTCODE = T1.CODE\n" );
    	sql.append("           AND T1.PID = T2.ORG_ID) A\n" );
    	sql.append("  LEFT JOIN PSZXJDFL B\n" );
    	sql.append("    ON A.DEPTCODE = B.DEPTCODE\n" );
    	sql.append("   AND A.JD = B.JD + 1\n" );
    	sql.append(" ORDER BY A.DEPTCODE, A.CODE");*/
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT A.*,\n" );
    	sql.append("       A.WC + A.YP + A.FL + A.GL + A.HL + A.IL + A.JL + A.KL + A.LL AS BYXSFLZE,\n" );
    	sql.append("       NVL(ROUND(A.ZZFL / B.ZZFL * 100, 2), 0) RATE\n" );
    	sql.append("  FROM (SELECT T2.ONAME,\n" );
    	sql.append("               T2.CODE,\n" );
    	sql.append("               T.DEPTCODE,\n" );
    	sql.append("               T.DEPTNAME,\n" );
    	sql.append("               T.JDPJCGJE,\n" );
    	sql.append("               T.ZZFL,\n" );
    	sql.append("               T.YPCGJE,\n" );
    	sql.append("               T.ILCGJE,\n" );
    	sql.append("               T.JLCGJE,\n" );
    	sql.append("               T.HLCGJE,\n" );
    	sql.append("               T.BLCGJE,\n" );
    	sql.append("               T.ALCGJE,\n" );
    	sql.append("               T.DZCJE,\n" );
    	sql.append("               T.JD,\n" );
    	sql.append("               T.FLCGJE,\n" );
    	sql.append("               T.GLFJE,\n" );
    	sql.append("               T.GLCGJE,\n" );
    	sql.append("               T.LLCGJE,\n" );
    	sql.append("               T.KLCGJE,\n" );
    	sql.append("               ROUND(T.WCFLJE, 2) AS WC,\n" );
    	sql.append("               ROUND(T.YPFLJE, 2) AS YP,\n" );
    	sql.append("               ROUND(T.FLCGJE * 0.1, 2) AS FL,\n" );
    	sql.append("               ROUND(T.GLCGJE * 0.05, 2) AS GL,\n" );
    	sql.append("               ROUND(T.HLCGJE * 0.08, 2) AS HL,\n" );
    	sql.append("               ROUND(T.ILCGJE * 0.05, 2) AS IL,\n" );
    	sql.append("               ROUND(T.JLCGJE * 0.05, 2) AS JL,\n" );
    	sql.append("               ROUND(T.KLCGJE * 0.03, 2) AS KL,\n" );
    	sql.append("               ROUND(T.LLCGJE * 0.1, 2) AS LL,\n" );
    	sql.append("               T.FLCGJE + T.GLCGJE AS QTBYJE\n" );
    	sql.append("          FROM PSZXJDFL T, TM_ORG T1, TM_ORG T2\n" );
    	sql.append("         WHERE "+conditions+"\n" );
    	sql.append("           AND T.DEPTCODE = T1.CODE\n" );
    	sql.append("           AND T1.PID = T2.ORG_ID) A\n" );
    	sql.append("  LEFT JOIN PSZXJDFL B\n" );
    	sql.append("    ON A.DEPTCODE = B.DEPTCODE\n" );
    	sql.append("   AND A.JD = B.JD + 1\n" );
    	sql.append(" ORDER BY A.DEPTCODE, A.CODE");
  /*  	sql.append("SELECT T2.ONAME,T2.CODE,T.*,T.FLCGJE+T.GLCGJE AS QTBYJE,T.JDPJCGJE-T.ALCGJE-T.BLCGJE-T.YPCGJE-T.FLCGJE-T.GLCGJE-T.HLCGJE-T.ILCGJE-T.JLCGJE-T.KLCGJE-T.LLCGJE AS QTJE\n" );
    	sql.append("FROM PSZXJDFL T,TM_ORG T1,TM_ORG T2");*/
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
	
	public QuerySet getOrg(String ORG_CODE) throws Exception {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CODE,ONAME FROM TM_ORG WHERE CODE = '"+ORG_CODE+"'\n");
        return factory.select(null, sql.toString());
    }
	public QuerySet getInfo(String ORG_CODE,String JD) throws Exception {

		StringBuffer sql= new StringBuffer();
		sql.append("SELECT A.*,\n" );
		sql.append("       ROUND(1 - A.WC - A.YP - A.FL - A.GL - A.HL - A.IL - A.JL - A.KL - A.LL,\n" );
		sql.append("             2) AS QT\n" );
		sql.append("  FROM (SELECT ROUND(T.WCFLJE / T.ZZFL, 2) AS WC,\n" );
		sql.append("               ROUND(T.YPFLJE / T.ZZFL, 2) AS YP,\n" );
		sql.append("               ROUND(T.FLCGJE * 0.1 / T.ZZFL, 2) AS FL,\n" );
		sql.append("               ROUND(T.GLCGJE * 0.05 / T.ZZFL, 2) AS GL,\n" );
		sql.append("               ROUND(T.HLCGJE * 0.08 / T.ZZFL, 2) AS HL,\n" );
		sql.append("               ROUND(T.ILCGJE * 0.05 / T.ZZFL, 2) AS IL,\n" );
		sql.append("               ROUND(T.JLCGJE * 0.05 / T.ZZFL, 2) AS JL,\n" );
		sql.append("               ROUND(T.KLCGJE * 0.03 / T.ZZFL, 2) AS KL,\n" );
		sql.append("               ROUND(T.LLCGJE * 0.1 / T.ZZFL, 2) AS LL\n" );
		sql.append("          FROM PSZXJDFL T\n" );
		sql.append("         WHERE T.DEPTCODE = '"+ORG_CODE+"'\n" );
		sql.append("           AND JD = '"+JD+"') A");
        return factory.select(null, sql.toString());
    }

}
