package com.org.dms.dao.part.salesMng.spreadFill;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class SpreadFillDao extends BaseDAO{
	public static final SpreadFillDao getInstance(ActionContext atx) {
		SpreadFillDao dao = new SpreadFillDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	/**
	 * 价差应补查询
	 */
	public BaseResultSet spreadFillSearch(PageManager page, User user, String conditions,String startDate,String endDate,String fillType,String partCode,String orgId) throws Exception
    {
    	String wheres = conditions;
    	wheres += " GROUP BY T.ORG_ID, T.ORG_CODE, T.ORG_NAME, T.PART_ID, T.PART_CODE, T.PART_NAME,\n";
    	wheres += "          T.OLD_PRICE, T.NEW_PRICE, T.SPREAD, T.CREATE_TIME,T.FILL_TYPE,T.DELIVERY_COUNT,T.SALE_COUNT\n";
    	wheres += " ORDER BY T.ORG_CODE,T.PART_CODE ASC\n";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.ORG_ID,\n" );
    	sql.append("       T.ORG_CODE,\n" );
    	sql.append("       T.ORG_NAME,\n" );
    	sql.append("       T.PART_ID,\n" );
    	sql.append("       T.PART_CODE,\n" );
    	sql.append("       T.PART_NAME,\n" );
    	sql.append("       T.OLD_PRICE,\n" );
    	sql.append("       T.NEW_PRICE,\n" );
    	sql.append("       T.SPREAD,\n" );
    	sql.append("       T.CREATE_TIME,\n" );
    	sql.append("       T.FILL_TYPE,\n" );
    	if(DicConstant.JCLX_01.equals(fillType)){
    		sql.append("           T.DELIVERY_COUNT,\n" );
    		sql.append("           T.SALE_COUNT,\n" );
    		sql.append("           SUM(T1.DELIVERY_COUNT) CHANEL_COUNT,\n" );
    		sql.append("           T.DELIVERY_COUNT-T.SALE_COUNT FILL_COUNT,\n" );
    		sql.append("           (T.DELIVERY_COUNT-T.SALE_COUNT) * T.SPREAD FILL_AMOUNT\n" );
    	}else{
    		sql.append("           T.DELIVERY_COUNT DELIVERY_COUNT,\n" );
    		sql.append("           NULL AS SALE_COUNT,\n" );
    		sql.append("           NULL AS CHANEL_COUNT,\n" );
    		sql.append("           T.DELIVERY_COUNT FILL_COUNT,\n" );
    		sql.append("           T.DELIVERY_COUNT * T.SPREAD FILL_AMOUNT\n" );
    	}
    	sql.append("  FROM (SELECT T.ORG_ID,\n" );
    	sql.append("               T.ORG_CODE,\n" );
    	sql.append("               T.ORG_NAME,\n" );
    	sql.append("               T.PART_ID,\n" );
    	sql.append("               T.PART_CODE,\n" );
    	sql.append("               T.PART_NAME,\n" );
    	sql.append("               T.ORIGINAL_PRICE OLD_PRICE,\n" );
    	sql.append("               T.NOW_PRICE NEW_PRICE,\n" );
    	sql.append("               T.ORIGINAL_PRICE - T.NOW_PRICE SPREAD,\n" );
    	sql.append("               T.CREATE_TIME,\n" );
    	sql.append("               "+fillType+" FILL_TYPE,\n" );
		sql.append("           	   SUM(T.DELIVERY_COUNT) DELIVERY_COUNT,\n" );
		sql.append("               NVL(T3.SALE_COUNT, 0) SALE_COUNT\n" );
    	sql.append("          FROM (SELECT T2.ORG_ID,\n" );
    	sql.append("                       T2.ORG_CODE,\n" );
    	sql.append("                       T2.ORG_NAME,\n" );
    	sql.append("                       T1.PART_ID,\n" );
    	sql.append("                       T2.PART_CODE,\n" );
    	sql.append("                       T2.PART_NAME,\n" );
    	sql.append("                       T1.ORIGINAL_PRICE,\n" );
    	sql.append("                       T1.NOW_PRICE,\n" );
    	sql.append("                       T1.CREATE_TIME,\n" );
    	sql.append("                       T2.APPLY_DATE,\n" );
    	sql.append("                       T2.DELIVERY_COUNT\n" );
    	sql.append("                  FROM (SELECT A.PART_ID,\n" );
    	sql.append("                               B.ORIGINAL_PRICE,\n" );
    	sql.append("                               C.NOW_PRICE,\n" );
    	sql.append("                               C.CREATE_TIME\n" );
    	sql.append("                          FROM (SELECT PART_ID,\n" );
    	sql.append("                                       MIN(CREATE_TIME) MIN_TIME,\n" );
    	sql.append("                                       MAX(CREATE_TIME) MAX_TIME\n" );
    	sql.append("                                  FROM PT_BA_PRICE_LOG\n" );
    	sql.append("                                 WHERE 1 = 1\n" );
    	sql.append("                                   AND PRICE_TYPE = "+DicConstant.PJJGLX_02+"\n" );
    	sql.append("                           AND CREATE_TIME >=\n" );
    	sql.append("                               TO_DATE('"+startDate+" 00:00:00',\n" );
    	sql.append("                                       'yyyy-MM-dd hh24:mi:ss')\n" );
    	sql.append("                           AND CREATE_TIME <=\n" );
    	sql.append("                               TO_DATE('"+endDate+" 23:59:59',\n" );
    	sql.append("                                       'yyyy-MM-dd hh24:mi:ss')\n" );
    	sql.append("                                 GROUP BY PART_ID) A,\n" );
    	sql.append("                               PT_BA_PRICE_LOG B,\n" );
    	sql.append("                               PT_BA_PRICE_LOG C\n" );
    	sql.append("                         WHERE A.PART_ID = B.PART_ID\n" );
    	sql.append("                           AND A.MIN_TIME = B.CREATE_TIME\n" );
    	sql.append("                           AND A.PART_ID = C.PART_ID\n" );
    	sql.append("                           AND A.MAX_TIME = C.CREATE_TIME\n" );
    	sql.append("                           AND B.PRICE_TYPE = "+DicConstant.PJJGLX_02+"\n" );
    	sql.append("                           AND C.PRICE_TYPE = "+DicConstant.PJJGLX_02+"\n" );
    	sql.append("                           AND B.ORIGINAL_PRICE > C.NOW_PRICE) T1,\n" );
    	sql.append("                       (SELECT A.ORG_ID,\n" );
    	sql.append("                               C.CODE ORG_CODE,\n" );
    	sql.append("                               C.ONAME ORG_NAME,\n" );
    	sql.append("                               B.PART_ID,\n" );
    	sql.append("                               B.PART_CODE,\n" );
    	sql.append("                               B.PART_NAME,\n" );
    	sql.append("                               A.APPLY_DATE,\n" );
    	sql.append("                               NVL(B.DELIVERY_COUNT, 0) DELIVERY_COUNT\n" );
    	sql.append("                          FROM PT_BU_SALE_ORDER     A,\n" );
    	sql.append("                               PT_BU_SALE_ORDER_DTL B,\n" );
    	sql.append("                               TM_ORG               C\n" );
    	sql.append("                         WHERE A.ORDER_ID = B.ORDER_ID\n" );
    	sql.append("                           AND A.ORG_ID = C.ORG_ID\n" );
    	sql.append("                           AND A.ORDER_STATUS = "+DicConstant.DDZT_06+"\n" );
    	sql.append("                           AND A.IF_CHANEL_ORDER = "+DicConstant.SF_02+"\n" );
    	sql.append("                           AND B.DELIVERY_COUNT > 0\n" );
    	sql.append("                           AND (B.FILL_STATUS IS NULL OR\n" );
    	sql.append("                               B.FILL_STATUS = "+DicConstant.SF_02+")) T2\n" );
    	sql.append("                 WHERE T1.PART_ID = T2.PART_ID\n" );
    	sql.append("                   AND T2.APPLY_DATE >= ADD_MONTHS(T1.CREATE_TIME, -3)\n" );
    	if(!"".equals(partCode)&&partCode !=null){
			sql.append(" AND T2.PART_CODE LIKE '%"+partCode+"%'\n");
		}
		if(!"".equals(orgId)&&orgId !=null){
			sql.append(" AND T2.ORG_ID IN ("+orgId+")\n");
		}
    	sql.append("                   AND T2.APPLY_DATE <= T1.CREATE_TIME) T\n" );
    	sql.append("          LEFT JOIN (SELECT A.ORG_ID,\n" );
    	sql.append("                           B.PART_ID,\n" );
    	sql.append("                           A.SALE_DATE,\n" );
    	sql.append("                           SUM(NVL(B.SALE_COUNT, 0)) SALE_COUNT\n" );
    	sql.append("                      FROM PT_BU_REAL_SALE A, PT_BU_REAL_SALE_DTL B\n" );
    	sql.append("                     WHERE A.SALE_ID = B.SALE_ID\n" );
    	sql.append("                       AND A.SALE_STATUS = "+DicConstant.SXDZT_02+"\n" );
    	sql.append("                     GROUP BY A.ORG_ID, B.PART_ID, A.SALE_DATE) T3\n" );
    	sql.append("            ON T.ORG_ID = T3.ORG_ID\n" );
    	sql.append("           AND T.PART_ID = T3.PART_ID\n" );
    	sql.append("           AND T3.SALE_DATE >= ADD_MONTHS(T.CREATE_TIME, -3)\n" );
    	sql.append("           AND T3.SALE_DATE <= T.CREATE_TIME\n" );
    	sql.append("          GROUP BY T.ORG_ID, T.ORG_CODE, T.ORG_NAME, T.PART_ID, T.PART_CODE, T.PART_NAME,\n" );
    	sql.append("                  T.ORIGINAL_PRICE, T.NOW_PRICE, T.CREATE_TIME, T3.SALE_COUNT,"+fillType+") T\n" );
    	sql.append("  LEFT JOIN (SELECT B.PART_ID,\n" );
    	sql.append("                    B.PART_CODE,\n" );
    	sql.append("                    B.PART_NAME,\n" );
    	sql.append("                    A.APPLY_DATE,\n" );
    	sql.append("                    A.WAREHOUSE_ID,\n" );
    	sql.append("                    NVL(B.DELIVERY_COUNT, 0) DELIVERY_COUNT\n" );
    	sql.append("               FROM PT_BU_SALE_ORDER A, PT_BU_SALE_ORDER_DTL B, TM_ORG C\n" );
    	sql.append("              WHERE A.ORDER_ID = B.ORDER_ID\n" );
    	sql.append("                AND A.ORG_ID = C.ORG_ID\n" );
    	sql.append("                AND A.ORDER_STATUS = "+DicConstant.DDZT_06+"\n" );
    	sql.append("                AND A.IF_CHANEL_ORDER = "+DicConstant.SF_01+"\n" );
    	sql.append("                AND B.DELIVERY_COUNT > 0\n" );
    	sql.append("                AND (B.FILL_STATUS IS NULL OR B.FILL_STATUS = "+DicConstant.SF_02+")) T1\n" );
    	sql.append("    ON (T.ORG_ID = T1.WAREHOUSE_ID AND T.PART_ID = T1.PART_ID AND\n" );
    	sql.append("       T1.APPLY_DATE >= ADD_MONTHS(T.CREATE_TIME, -3) AND\n" );
    	sql.append("       T1.APPLY_DATE <= T.CREATE_TIME)\n" );
    	
    	//价差应补信息查询
    	bs = factory.select(sql.toString(), page);
		StringBuffer sql1 = new StringBuffer();
		sql1.append("INSERT INTO PT_BU_SPREAD_FILL (FILL_ID,PRICE_DATE,ORG_ID,ORG_CODE,ORG_NAME,PART_ID,PART_CODE,PART_NAME,OLD_PRICE,NEW_PRICE,\n");
		sql1.append("SPREAD,DELIVERY_COUNT,SALE_COUNT,CHANEL_COUNT,FILL_COUNT,FILL_AMOUNT,FILL_TYPE,CREATE_USER,CREATE_TIME,STATUS,OEM_COMPANY_ID)\n");
		sql1.append("SELECT F_GETID(),T.CREATE_TIME,T.ORG_ID,T.ORG_CODE,T.ORG_NAME,T.PART_ID,T.PART_CODE,T.PART_NAME,T.OLD_PRICE,T.NEW_PRICE,\n");
		sql1.append("T.SPREAD,T.DELIVERY_COUNT,T.SALE_COUNT,T.CHANEL_COUNT,T.FILL_COUNT,T.FILL_AMOUNT,T.FILL_TYPE,'"+user.getAccount()+"',SYSDATE,"+DicConstant.YXBS_01+","+user.getCompanyId()+" FROM("+sql.toString()+" AND "+wheres.toString()+")T\n");
		//价差应补信息插入
		factory.update(sql1.toString(), null);
		if(DicConstant.JCLX_01.equals(fillType)){
			StringBuffer sql3= new StringBuffer();
			sql3.append("INSERT INTO PT_BU_SPREAD_FILL_DTL(DTL_ID,FILL_ID,ORG_ID,ORG_CODE,ORG_NAME,PART_ID,PART_CODE,PART_NAME,OLD_PRICE,NEW_PRICE,SPREAD,FILL_COUNT,FILL_AMOUNT,CREATE_USER,CREATE_TIME,PRICE_DATE)\n" );
			sql3.append("SELECT F_GETID(),\n" );
			sql3.append("       (SELECT S.FILL_ID\n" );
			sql3.append("          FROM PT_BU_SPREAD_FILL S\n" );
			sql3.append("         WHERE S.ORG_ID = T2.ORG_ID\n" );
			sql3.append("           AND S.PART_ID = T1.PART_ID\n" );
			sql3.append("           AND S.PRICE_DATE = T1.CREATE_TIME),\n" );
			sql3.append("       T2.CHANEL_ID,\n" );
			sql3.append("       T2.ORG_CODE,\n" );
			sql3.append("       T2.ORG_NAME,\n" );
			sql3.append("       T1.PART_ID,\n" );
			sql3.append("       T2.PART_CODE,\n" );
			sql3.append("       T2.PART_NAME,\n" );
			sql3.append("       T1.ORIGINAL_PRICE,\n" );
			sql3.append("       T1.NOW_PRICE,\n" );
			sql3.append("       T1.ORIGINAL_PRICE - T1.NOW_PRICE,\n" );
			sql3.append("       T2.DELIVERY_COUNT,\n" );
			sql3.append("       (T1.ORIGINAL_PRICE - T1.NOW_PRICE) * T2.DELIVERY_COUNT,\n" );
			sql3.append("       '"+user.getAccount()+"',\n" );
			sql3.append("       SYSDATE,\n" );
			sql3.append("       T1.CREATE_TIME\n" );
			sql3.append("  FROM (SELECT A.PART_ID, B.ORIGINAL_PRICE, C.NOW_PRICE, C.CREATE_TIME\n" );
			sql3.append("          FROM (SELECT PART_ID,\n" );
			sql3.append("                       MIN(CREATE_TIME) MIN_TIME,\n" );
			sql3.append("                       MAX(CREATE_TIME) MAX_TIME\n" );
			sql3.append("                  FROM PT_BA_PRICE_LOG\n" );
			sql3.append("                 WHERE 1 = 1\n" );
			sql3.append("                   AND PRICE_TYPE = "+DicConstant.PJJGLX_02+"\n" );
	    	sql3.append("                           AND CREATE_TIME >=\n" );
	    	sql3.append("                               TO_DATE('"+startDate+" 00:00:00',\n" );
	    	sql3.append("                                       'yyyy-MM-dd hh24:mi:ss')\n" );
	    	sql3.append("                           AND CREATE_TIME <=\n" );
	    	sql3.append("                               TO_DATE('"+endDate+" 23:59:59',\n" );
	    	sql3.append("                                       'yyyy-MM-dd hh24:mi:ss')\n" );
			sql3.append("                 GROUP BY PART_ID) A,\n" );
			sql3.append("               PT_BA_PRICE_LOG B,\n" );
			sql3.append("               PT_BA_PRICE_LOG C\n" );
			sql3.append("         WHERE A.PART_ID = B.PART_ID\n" );
			sql3.append("           AND A.MIN_TIME = B.CREATE_TIME\n" );
			sql3.append("           AND A.PART_ID = C.PART_ID\n" );
			sql3.append("           AND A.MAX_TIME = C.CREATE_TIME\n" );
			sql3.append("           AND B.PRICE_TYPE = "+DicConstant.PJJGLX_02+"\n" );
			sql3.append("           AND C.PRICE_TYPE = "+DicConstant.PJJGLX_02+"\n" );
			sql3.append("           AND B.ORIGINAL_PRICE > C.NOW_PRICE) T1,\n" );
			sql3.append("       (SELECT A.ORG_ID CHANEL_ID,\n" );
			sql3.append("               C.CODE ORG_CODE,\n" );
			sql3.append("               C.ONAME ORG_NAME,\n" );
			sql3.append("               B.PART_ID,\n" );
			sql3.append("               B.PART_CODE,\n" );
			sql3.append("               B.PART_NAME,\n" );
			sql3.append("               A.APPLY_DATE,\n" );
			sql3.append("               NVL(B.DELIVERY_COUNT, 0) DELIVERY_COUNT,\n" );
			sql3.append("               A.WAREHOUSE_ID ORG_ID\n" );
			sql3.append("          FROM PT_BU_SALE_ORDER A, PT_BU_SALE_ORDER_DTL B, TM_ORG C\n" );
			sql3.append("         WHERE A.ORDER_ID = B.ORDER_ID\n" );
			sql3.append("           AND A.ORG_ID = C.ORG_ID\n" );
			sql3.append("           AND A.ORDER_STATUS = "+DicConstant.DDZT_06+"\n" );
			sql3.append("           AND A.IF_CHANEL_ORDER = "+DicConstant.SF_01+"\n" );
			sql3.append("           AND B.DELIVERY_COUNT > 0\n" );
			sql3.append("           AND (B.FILL_STATUS IS NULL OR B.FILL_STATUS = "+DicConstant.SF_02+")) T2\n" );
			sql3.append(" WHERE T1.PART_ID = T2.PART_ID\n" );
			sql3.append("   AND T2.APPLY_DATE >= ADD_MONTHS(T1.CREATE_TIME, -3)\n" );
			sql3.append("   AND T2.APPLY_DATE <= T1.CREATE_TIME\n");
			if(!"".equals(partCode)&&partCode !=null){
				sql3.append(" AND T1.PART_CODE LIKE '%"+partCode+"%'\n");
			}
			if(!"".equals(orgId)&&orgId !=null){
				sql3.append(" AND T2.ORG_ID IN ("+orgId+")\n");
			}
			factory.update(sql3.toString(), null);
			
			
			StringBuffer sql4= new StringBuffer();
			sql4.append("UPDATE PT_BU_SALE_ORDER_DTL SET FILL_STATUS = '"+DicConstant.SF_01+"' WHERE DTL_ID IN(\n");
			sql4.append("SELECT T2.DTL_ID\n" );
			sql4.append("  FROM (SELECT A.PART_ID, B.ORIGINAL_PRICE, C.NOW_PRICE, C.CREATE_TIME\n" );
			sql4.append("          FROM (SELECT PART_ID,\n" );
			sql4.append("                       MIN(CREATE_TIME) MIN_TIME,\n" );
			sql4.append("                       MAX(CREATE_TIME) MAX_TIME\n" );
			sql4.append("                  FROM PT_BA_PRICE_LOG\n" );
			sql4.append("                 WHERE 1 = 1\n" );
			sql4.append("                   AND PRICE_TYPE = "+DicConstant.PJJGLX_02+"\n" );
	    	sql4.append("                           AND CREATE_TIME >=\n" );
	    	sql4.append("                               TO_DATE('"+startDate+" 00:00:00',\n" );
	    	sql4.append("                                       'yyyy-MM-dd hh24:mi:ss')\n" );
	    	sql4.append("                           AND CREATE_TIME <=\n" );
	    	sql4.append("                               TO_DATE('"+endDate+" 23:59:59',\n" );
	    	sql4.append("                                       'yyyy-MM-dd hh24:mi:ss')\n" );
			sql4.append("                 GROUP BY PART_ID) A,\n" );
			sql4.append("               PT_BA_PRICE_LOG B,\n" );
			sql4.append("               PT_BA_PRICE_LOG C\n" );
			sql4.append("         WHERE A.PART_ID = B.PART_ID\n" );
			sql4.append("           AND A.MIN_TIME = B.CREATE_TIME\n" );
			sql4.append("           AND A.PART_ID = C.PART_ID\n" );
			sql4.append("           AND A.MAX_TIME = C.CREATE_TIME\n" );
			sql4.append("           AND B.PRICE_TYPE = "+DicConstant.PJJGLX_02+"\n" );
			sql4.append("           AND C.PRICE_TYPE = "+DicConstant.PJJGLX_02+"\n" );
			sql4.append("           AND B.ORIGINAL_PRICE > C.NOW_PRICE) T1,\n" );
			sql4.append("       (SELECT A.ORG_ID CHANEL_ID,\n" );
			sql4.append("               C.CODE ORG_CODE,\n" );
			sql4.append("               C.ONAME ORG_NAME,\n" );
			sql4.append("               B.DTL_ID,\n" );
			sql4.append("               B.PART_ID,\n" );
			sql4.append("               B.PART_CODE,\n" );
			sql4.append("               B.PART_NAME,\n" );
			sql4.append("               A.APPLY_DATE,\n" );
			sql4.append("               NVL(B.DELIVERY_COUNT, 0) DELIVERY_COUNT,\n" );
			sql4.append("               A.WAREHOUSE_ID ORG_ID\n" );
			sql4.append("          FROM PT_BU_SALE_ORDER A, PT_BU_SALE_ORDER_DTL B, TM_ORG C\n" );
			sql4.append("         WHERE A.ORDER_ID = B.ORDER_ID\n" );
			sql4.append("           AND A.ORG_ID = C.ORG_ID\n" );
			sql4.append("           AND A.ORDER_STATUS = "+DicConstant.DDZT_06+"\n" );
			sql4.append("           AND A.IF_CHANEL_ORDER = "+DicConstant.SF_01+"\n" );
			sql4.append("           AND B.DELIVERY_COUNT > 0\n" );
			sql4.append("           AND (B.FILL_STATUS IS NULL OR B.FILL_STATUS = "+DicConstant.SF_02+")) T2\n" );
			sql4.append(" WHERE T1.PART_ID = T2.PART_ID\n" );
			sql4.append("   AND T2.APPLY_DATE >= ADD_MONTHS(T1.CREATE_TIME, -3)\n" );
			sql4.append("   AND T2.APPLY_DATE <= T1.CREATE_TIME\n");
			if(!"".equals(partCode)&&partCode !=null){
				sql4.append(" AND T1.PART_CODE LIKE '%"+partCode+"%'\n");
			}
			if(!"".equals(orgId)&&orgId !=null){
				sql4.append(" AND T2.ORG_ID IN ("+orgId+")\n");
			}
			sql4.append(" )\n");
			factory.update(sql4.toString(), null);
			
		}
		StringBuffer sql2 = new StringBuffer();
		sql2.append("UPDATE PT_BU_SALE_ORDER_DTL SET FILL_STATUS = '"+DicConstant.SF_01+"' WHERE DTL_ID IN(\n");
		sql2.append("SELECT T2.DTL_ID\n" );
		sql2.append("  FROM (SELECT A.PART_ID, B.ORIGINAL_PRICE, C.NOW_PRICE, C.CREATE_TIME\n" );
		sql2.append("          FROM (SELECT PART_ID,\n" );
		sql2.append("                       MIN(CREATE_TIME) MIN_TIME,\n" );
		sql2.append("                       MAX(CREATE_TIME) MAX_TIME\n" );
		sql2.append("                  FROM PT_BA_PRICE_LOG\n" );
		sql2.append("                 WHERE 1 = 1 AND PRICE_TYPE="+DicConstant.PJJGLX_02+"\n" );
		sql2.append("                   AND CREATE_TIME >=\n" );
		sql2.append("                       TO_DATE('"+startDate+" 00:00:00', 'YYYY-MM-DD HH24:MI:SS')\n" );
		sql2.append("                   AND CREATE_TIME <=\n" );
		sql2.append("                       TO_DATE('"+endDate+" 23:59:59', 'YYYY-MM-DD HH24:MI:SS')\n" );
		sql2.append("                 GROUP BY PART_ID) A,\n" );
		sql2.append("               PT_BA_PRICE_LOG B,\n" );
		sql2.append("               PT_BA_PRICE_LOG C\n" );
		sql2.append("         WHERE A.PART_ID = B.PART_ID\n" );
		sql2.append("           AND A.MIN_TIME = B.CREATE_TIME\n" );
		sql2.append("           AND A.PART_ID = C.PART_ID\n" );
		sql2.append("           AND A.MAX_TIME = C.CREATE_TIME AND B.PRICE_TYPE="+DicConstant.PJJGLX_02+" AND C.PRICE_TYPE="+DicConstant.PJJGLX_02+"\n" );
		sql2.append("           AND B.ORIGINAL_PRICE > C.NOW_PRICE) T1,\n" );
		sql2.append("       (SELECT A.ORG_ID,\n" );
		sql2.append("               C.CODE ORG_CODE,\n" );
		sql2.append("               C.ONAME ORG_NAME,\n" );
		sql2.append("               B.DTL_ID,\n" );
		sql2.append("               B.PART_ID,\n" );
		sql2.append("               B.PART_CODE,\n" );
		sql2.append("               B.PART_NAME,\n" );
		sql2.append("               A.APPLY_DATE,\n" );
		sql2.append("               NVL(B.DELIVERY_COUNT, 0) DELIVERY_COUNT\n" );
		sql2.append("          FROM PT_BU_SALE_ORDER A, PT_BU_SALE_ORDER_DTL B,TM_ORG C\n" );
		sql2.append("         WHERE A.ORDER_ID = B.ORDER_ID AND A.ORG_ID =C.ORG_ID\n" );
		sql2.append("           AND A.ORDER_STATUS = "+DicConstant.DDZT_06+"\n" );
		sql2.append("           AND A.IF_CHANEL_ORDER = "+DicConstant.SF_02+"\n" );
		sql2.append("           AND B.DELIVERY_COUNT > 0\n" );
		sql2.append("           AND (B.FILL_STATUS IS NULL OR B.FILL_STATUS = "+DicConstant.SF_02+")) T2\n" );
		sql2.append(" WHERE T1.PART_ID = T2.PART_ID\n" );
		sql2.append("   AND T2.APPLY_DATE >= ADD_MONTHS(T1.CREATE_TIME, -3)\n" );
		sql2.append("   AND T2.APPLY_DATE <= T1.CREATE_TIME\n");
		if(!"".equals(partCode)&&partCode !=null){
			sql2.append(" AND T2.PART_CODE LIKE '%"+partCode+"%'\n");
		}
		if(!"".equals(orgId)&&orgId !=null){
			sql2.append(" AND T2.ORG_ID IN ("+orgId+")\n");
		}
		sql2.append(" )\n");
		//更新价差应补状态为：已补
		factory.update(sql2.toString(), null);
		bs.setFieldDic("FILL_TYPE", "JCLX");
		bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	return bs;
    }
}
