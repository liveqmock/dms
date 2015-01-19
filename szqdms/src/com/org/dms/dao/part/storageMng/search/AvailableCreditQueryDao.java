package com.org.dms.dao.part.storageMng.search;

import java.sql.SQLException;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.mvc.context.ActionContext;

/**
 * 
 * @ClassName: AvailableCreditQueryDao
 * @Description: 可用额度查询Dao
 * @author: fuxiao
 * @date: 2014年12月23日 下午5:51:56
 */
public class AvailableCreditQueryDao extends BaseDAO {
	
	public static final AvailableCreditQueryDao getInstance(ActionContext ac){
		AvailableCreditQueryDao dao = new AvailableCreditQueryDao();
		ac.setDBFactory(dao.factory);
		return dao;
	}
	

	/**
	 * 
	 * @Title: queryList
	 * @Description: 查询
	 * @param pageManager
	 * @param conditions
	 * @return
	 * @throws Exception
	 * @return: BaseResultSet
	 */
	public BaseResultSet queryList(PageManager pageManager, String conditions) throws Exception{
		String wheres = conditions;
    	wheres += " AND T.ORG_ID = T1.ORG_ID(+) ORDER BY T.ORG_ID\n";
    	pageManager.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.ORG_ID,\n" );
    	sql.append("       T.ORG_CODE,\n" );
    	sql.append("       T.ORG_NAME,\n" );
    	sql.append("       T.LINE_OF_CREDIT,\n" );
    	sql.append("       T.BEGIN_AMOUNT,\n" );
    	sql.append("       T.CLOSE_ORDER_AMOUNT+T.GDEDZY CLOSE_ORDER_AMOUNT,\n" );
    	sql.append("       T.NOT_CLOSE_ORDER_AMOUNT,\n" );
    	sql.append("       T.GDEDZY,\n" );
    	sql.append("       T.GDEDYH,\n" );
    	sql.append("       T.BACK_AMOUNT,\n" );
    	sql.append("       (T.BEGIN_AMOUNT + T.CLOSE_ORDER_AMOUNT + T.NOT_CLOSE_ORDER_AMOUNT +T.GDEDZY+ T.GDEDYH+NVL(T1.REAL_AMOUNT, 0) -\n" );
    	sql.append("       T.BACK_AMOUNT) RECEIVABLES,\n" );
    	sql.append("       (T.LINE_OF_CREDIT - T.BEGIN_AMOUNT - T.CLOSE_ORDER_AMOUNT -\n" );
    	sql.append("       T.NOT_CLOSE_ORDER_AMOUNT - T.GDEDZY - T.GDEDYH + T.BACK_AMOUNT +\n" );
    	sql.append("       NVL(T1.REAL_AMOUNT, 0)) AVAILABLE_CREDIT\n" );
    	sql.append("  FROM (SELECT O.ORG_ID,\n" );
    	sql.append("               O.CODE ORG_CODE,\n" );
    	sql.append("               O.ONAME ORG_NAME,\n" );
    	sql.append("               NVL((SELECT SUM(T.NOW_LIMIT)\n" );
    	sql.append("                     FROM PT_BU_CREDIT_LINE T\n" );
    	sql.append("                    WHERE T.ORG_ID = O.ORG_ID\n" );
    	sql.append("                      AND T.STATUS = 100201),\n" );
    	sql.append("                   0) LINE_OF_CREDIT, -- 授信额度\n" );
    	sql.append("               NVL((SELECT T.CLOSE_AMOUNT\n" );
    	sql.append("                     FROM PT_BU_ACCOUNT T\n" );
    	sql.append("                    WHERE T.ORG_ID = O.ORG_ID\n" );
    	sql.append("                      AND T.STATUS = 100201\n" );
    	sql.append("                      AND T.ACCOUNT_TYPE = 202704),\n" );
    	sql.append("                   0) BEGIN_AMOUNT, -- 期初结转\n" );
    	sql.append("               NVL((SELECT SUM(REAL_AMOUNT)\n" );
    	sql.append("                     FROM PT_BU_SALE_ORDER T\n" );
    	sql.append("                    WHERE T.ORG_ID = O.ORG_ID\n" );
    	sql.append("                      AND T.ORDER_STATUS = 202206\n" );
    	sql.append("                      AND T.IF_CREDIT = 100102\n" );
    	sql.append("                      AND T.ORDER_TYPE <>203799\n" );
    	sql.append("                      AND T.CLOSE_DATE >= TO_DATE('20141103', 'YYYYMMDD')),\n" );
    	sql.append("                   0) CLOSE_ORDER_AMOUNT, -- 关闭订单总金额\n" );
    	sql.append("               NVL((SELECT SUM(NVL(F.OCCUPY_FUNDS, 0))\n" );
    	sql.append("                     FROM PT_BU_SALE_ORDER_OCCUPY_FUNDS F,\n" );
    	sql.append("                          PT_BU_SALE_ORDER              SO,\n" );
    	sql.append("                          PT_BU_ACCOUNT                 A\n" );
    	sql.append("                    WHERE F.ACCOUNT_ID = A.ACCOUNT_ID\n" );
    	sql.append("                      AND F.ORDER_ID = SO.ORDER_ID\n" );
    	sql.append("                      AND SO.ORDER_STATUS = 202206\n" );
    	sql.append("                      AND F.ACCOUNT_TYPE = 202704\n" );
    	sql.append("                      AND SO.CLOSE_DATE >= TO_DATE('20141103', 'YYYYMMDD')\n" );
    	sql.append("                      AND NVL(F.OCCUPY_FUNDS, 0) = NVL(F.REPAY_AMOUNT, 0)\n" );
    	sql.append("                      AND A.ORG_ID = O.ORG_ID),\n" );
    	sql.append("                   0) GDEDYH, -- 关闭订单额度已还\n" );
    	sql.append("               NVL((SELECT SUM(NVL(F.OCCUPY_FUNDS, 0))\n" );
    	sql.append("                     FROM PT_BU_SALE_ORDER_OCCUPY_FUNDS F,\n" );
    	sql.append("                          PT_BU_SALE_ORDER              SO,\n" );
    	sql.append("                          PT_BU_ACCOUNT                 A\n" );
    	sql.append("                    WHERE F.ACCOUNT_ID = A.ACCOUNT_ID\n" );
    	sql.append("                      AND F.ORDER_ID = SO.ORDER_ID\n" );
    	sql.append("                      AND SO.ORDER_STATUS = 202206\n" );
    	sql.append("                      AND F.ACCOUNT_TYPE = 202704\n" );
    	sql.append("                      AND SO.CLOSE_DATE >= TO_DATE('20141103', 'YYYYMMDD')\n" );
    	sql.append("                      AND F.STATUS = 100201\n" );
    	sql.append("                      AND A.ORG_ID = O.ORG_ID),\n" );
    	sql.append("                   0) GDEDZY, -- 关闭订单额度金额\n" );
    	sql.append("               NVL((SELECT SUM(NVL(F.OCCUPY_FUNDS, 0))\n" );
    	sql.append("                     FROM PT_BU_SALE_ORDER_OCCUPY_FUNDS F,\n" );
    	sql.append("                          PT_BU_SALE_ORDER              SO,\n" );
    	sql.append("                          PT_BU_ACCOUNT                 A\n" );
    	sql.append("                    WHERE F.ACCOUNT_ID = A.ACCOUNT_ID\n" );
    	sql.append("                      AND F.ORDER_ID = SO.ORDER_ID\n" );
    	sql.append("                      AND SO.ORDER_STATUS IN (202202, 202203)\n" );
    	sql.append("                      AND F.STATUS = 100201\n" );
    	sql.append("                      AND A.ORG_ID = O.ORG_ID),\n" );
    	sql.append("                   0) NOT_CLOSE_ORDER_AMOUNT, -- 未关闭订单金额\n" );
    	sql.append("               NVL((SELECT SUM(CASE\n" );
    	sql.append("                                WHEN AL.LOG_TYPE = 202802 OR AL.LOG_TYPE = 202804 OR\n" );
    	sql.append("                                     AL.LOG_TYPE = 202805 OR AL.LOG_TYPE = 202809 THEN\n" );
    	sql.append("                                 (-1 * AL.AMOUNT)\n" );
    	sql.append("                                ELSE\n" );
    	sql.append("                                 AL.AMOUNT\n" );
    	sql.append("                              END)\n" );
    	sql.append("                     FROM PT_BU_ACCOUNT_LOG AL, PT_BU_ACCOUNT A\n" );
    	sql.append("                    WHERE AL.ACCOUNT_ID = A.ACCOUNT_ID\n" );
    	sql.append("                      AND NOT EXISTS\n" );
    	sql.append("                    (SELECT 1\n" );
    	sql.append("                             FROM PT_BU_ACCOUNT_LOG AL_C, PT_BU_ACCOUNT A_C\n" );
    	sql.append("                            WHERE AL_C.ACCOUNT_ID = A_C.ACCOUNT_ID\n" );
    	sql.append("                              AND ((A_C.ACCOUNT_TYPE IN\n" );
    	sql.append("                                  (202701, 202702, 202703) AND\n" );
    	sql.append("                                  AL_C.LOG_TYPE = 202804) AND\n" );
    	sql.append("                                  AL_C.CREATE_USER <> 'YANGXUMING')\n" );
    	sql.append("                              AND AL_C.LOG_ID = AL.LOG_ID)\n" );
    	sql.append("                      AND A.ORG_ID = O.ORG_ID),\n" );
    	sql.append("                   0) BACK_AMOUNT -- 回款汇总\n" );
    	sql.append("          FROM TM_ORG O\n" );
    	sql.append("         WHERE O.ORG_TYPE = 200005) T,\n" );
    	sql.append("       (SELECT T.ORG_ID, SUM(T.REAL_AMOUNT) REAL_AMOUNT\n" );
    	sql.append("          FROM (SELECT A.ORDER_ID,\n" );
    	sql.append("                       A.ORDER_NO,\n" );
    	sql.append("                       A.ORG_ID,\n" );
    	sql.append("                       A.ORG_CODE,\n" );
    	sql.append("                       SUM(NVL(B.DELIVERY_COUNT, 0) * NVL(B.UNIT_PRICE, 0)) REAL_AMOUNT\n" );
    	sql.append("                  FROM PT_BU_SALE_ORDER A, PT_BU_SALE_ORDER_DTL B\n" );
    	sql.append("                 WHERE A.ORDER_ID = B.ORDER_ID\n" );
    	sql.append("                   AND A.IF_CREDIT = 100102\n" );
    	sql.append("                   AND A.IF_CHANEL_ORDER = 100102\n" );
    	sql.append("                   AND A.ORDER_TYPE <> 203799\n" );
    	sql.append("                   AND A.CLOSE_DATE >= TO_DATE('2014-11-03', 'YYYY-MM-DD')\n" );
    	sql.append("                   AND A.ORDER_STATUS = 202206\n" );
    	sql.append("                   AND A.FLAG IS NULL\n" );
    	sql.append("                 GROUP BY A.ORDER_ID, A.ORDER_NO, A.ORG_ID, A.ORG_CODE) T,\n" );
    	sql.append("               PT_BU_SALE_ORDER_PAY T1,\n" );
    	sql.append("               PT_BU_ACCOUNT T2\n" );
    	sql.append("         WHERE T.ORDER_ID = T1.ORDER_ID\n" );
    	sql.append("           AND T1.ACCOUNT_ID = T2.ACCOUNT_ID\n" );
    	sql.append("           AND T.REAL_AMOUNT > 0\n" );
    	sql.append("           AND T2.ACCOUNT_TYPE <> 202704\n" );
    	sql.append("           AND NOT EXISTS (SELECT 1\n" );
    	sql.append("                  FROM PT_BU_SALE_ORDER_OCCUPY_FUNDS S\n" );
    	sql.append("                 WHERE S.ORDER_ID = T.ORDER_ID\n" );
    	sql.append("                   AND S.STATUS = 100201)\n" );
    	sql.append("         GROUP BY T.ORG_ID) T1\n");
    	bs = factory.select(sql.toString(), pageManager);
    	return bs;
	}
	
	/**
	 * 
	 * @Title: queryDownInfo
	 * @Description: 导出查询
	 * @param conditions
	 * @return
	 * @throws SQLException
	 * @return: QuerySet
	 */
	public QuerySet queryDownInfo(String conditions) throws SQLException {
		String wheres = conditions;
    	wheres += " AND T.ORG_ID = T1.ORG_ID(+) ORDER BY T.ORG_ID\n";
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.ORG_ID,\n" );
    	sql.append("       T.ORG_CODE,\n" );
    	sql.append("       T.ORG_NAME,\n" );
    	sql.append("       T.LINE_OF_CREDIT,\n" );
    	sql.append("       T.BEGIN_AMOUNT,\n" );
    	sql.append("       T.CLOSE_ORDER_AMOUNT+T.GDEDZY CLOSE_ORDER_AMOUNT,\n" );
    	sql.append("       T.NOT_CLOSE_ORDER_AMOUNT,\n" );
    	sql.append("       T.GDEDZY,\n" );
    	sql.append("       T.GDEDYH,\n" );
    	sql.append("       T.BACK_AMOUNT,\n" );
    	sql.append("       (T.BEGIN_AMOUNT + T.CLOSE_ORDER_AMOUNT + T.NOT_CLOSE_ORDER_AMOUNT +T.GDEDZY+ T.GDEDYH+NVL(T1.REAL_AMOUNT, 0) -\n" );
    	sql.append("       T.BACK_AMOUNT) RECEIVABLES,\n" );
    	sql.append("       (T.LINE_OF_CREDIT - T.BEGIN_AMOUNT - T.CLOSE_ORDER_AMOUNT -\n" );
    	sql.append("       T.NOT_CLOSE_ORDER_AMOUNT - T.GDEDZY - T.GDEDYH + T.BACK_AMOUNT +\n" );
    	sql.append("       NVL(T1.REAL_AMOUNT, 0)) AVAILABLE_CREDIT\n" );
    	sql.append("  FROM (SELECT O.ORG_ID,\n" );
    	sql.append("               O.CODE ORG_CODE,\n" );
    	sql.append("               O.ONAME ORG_NAME,\n" );
    	sql.append("               NVL((SELECT SUM(T.NOW_LIMIT)\n" );
    	sql.append("                     FROM PT_BU_CREDIT_LINE T\n" );
    	sql.append("                    WHERE T.ORG_ID = O.ORG_ID\n" );
    	sql.append("                      AND T.STATUS = 100201),\n" );
    	sql.append("                   0) LINE_OF_CREDIT, -- 授信额度\n" );
    	sql.append("               NVL((SELECT T.CLOSE_AMOUNT\n" );
    	sql.append("                     FROM PT_BU_ACCOUNT T\n" );
    	sql.append("                    WHERE T.ORG_ID = O.ORG_ID\n" );
    	sql.append("                      AND T.STATUS = 100201\n" );
    	sql.append("                      AND T.ACCOUNT_TYPE = 202704),\n" );
    	sql.append("                   0) BEGIN_AMOUNT, -- 期初结转\n" );
    	sql.append("               NVL((SELECT SUM(REAL_AMOUNT)\n" );
    	sql.append("                     FROM PT_BU_SALE_ORDER T\n" );
    	sql.append("                    WHERE T.ORG_ID = O.ORG_ID\n" );
    	sql.append("                      AND T.ORDER_STATUS = 202206\n" );
    	sql.append("                      AND T.IF_CREDIT = 100102\n" );
    	sql.append("                      AND T.ORDER_TYPE <>203799\n" );
    	sql.append("                      AND T.CLOSE_DATE >= TO_DATE('20141103', 'YYYYMMDD')),\n" );
    	sql.append("                   0) CLOSE_ORDER_AMOUNT, -- 关闭订单总金额\n" );
    	sql.append("               NVL((SELECT SUM(NVL(F.OCCUPY_FUNDS, 0))\n" );
    	sql.append("                     FROM PT_BU_SALE_ORDER_OCCUPY_FUNDS F,\n" );
    	sql.append("                          PT_BU_SALE_ORDER              SO,\n" );
    	sql.append("                          PT_BU_ACCOUNT                 A\n" );
    	sql.append("                    WHERE F.ACCOUNT_ID = A.ACCOUNT_ID\n" );
    	sql.append("                      AND F.ORDER_ID = SO.ORDER_ID\n" );
    	sql.append("                      AND SO.ORDER_STATUS = 202206\n" );
    	sql.append("                      AND F.ACCOUNT_TYPE = 202704\n" );
    	sql.append("                      AND SO.CLOSE_DATE >= TO_DATE('20141103', 'YYYYMMDD')\n" );
    	sql.append("                      AND NVL(F.OCCUPY_FUNDS, 0) = NVL(F.REPAY_AMOUNT, 0)\n" );
    	sql.append("                      AND A.ORG_ID = O.ORG_ID),\n" );
    	sql.append("                   0) GDEDYH, -- 关闭订单额度已还\n" );
    	sql.append("               NVL((SELECT SUM(NVL(F.OCCUPY_FUNDS, 0))\n" );
    	sql.append("                     FROM PT_BU_SALE_ORDER_OCCUPY_FUNDS F,\n" );
    	sql.append("                          PT_BU_SALE_ORDER              SO,\n" );
    	sql.append("                          PT_BU_ACCOUNT                 A\n" );
    	sql.append("                    WHERE F.ACCOUNT_ID = A.ACCOUNT_ID\n" );
    	sql.append("                      AND F.ORDER_ID = SO.ORDER_ID\n" );
    	sql.append("                      AND SO.ORDER_STATUS = 202206\n" );
    	sql.append("                      AND F.ACCOUNT_TYPE = 202704\n" );
    	sql.append("                      AND SO.CLOSE_DATE >= TO_DATE('20141103', 'YYYYMMDD')\n" );
    	sql.append("                      AND F.STATUS = 100201\n" );
    	sql.append("                      AND A.ORG_ID = O.ORG_ID),\n" );
    	sql.append("                   0) GDEDZY, -- 关闭订单额度金额\n" );
    	sql.append("               NVL((SELECT SUM(NVL(F.OCCUPY_FUNDS, 0))\n" );
    	sql.append("                     FROM PT_BU_SALE_ORDER_OCCUPY_FUNDS F,\n" );
    	sql.append("                          PT_BU_SALE_ORDER              SO,\n" );
    	sql.append("                          PT_BU_ACCOUNT                 A\n" );
    	sql.append("                    WHERE F.ACCOUNT_ID = A.ACCOUNT_ID\n" );
    	sql.append("                      AND F.ORDER_ID = SO.ORDER_ID\n" );
    	sql.append("                      AND SO.ORDER_STATUS IN (202202, 202203)\n" );
    	sql.append("                      AND F.STATUS = 100201\n" );
    	sql.append("                      AND A.ORG_ID = O.ORG_ID),\n" );
    	sql.append("                   0) NOT_CLOSE_ORDER_AMOUNT, -- 未关闭订单金额\n" );
    	sql.append("               NVL((SELECT SUM(CASE\n" );
    	sql.append("                                WHEN AL.LOG_TYPE = 202802 OR AL.LOG_TYPE = 202804 OR\n" );
    	sql.append("                                     AL.LOG_TYPE = 202805 OR AL.LOG_TYPE = 202809 THEN\n" );
    	sql.append("                                 (-1 * AL.AMOUNT)\n" );
    	sql.append("                                ELSE\n" );
    	sql.append("                                 AL.AMOUNT\n" );
    	sql.append("                              END)\n" );
    	sql.append("                     FROM PT_BU_ACCOUNT_LOG AL, PT_BU_ACCOUNT A\n" );
    	sql.append("                    WHERE AL.ACCOUNT_ID = A.ACCOUNT_ID\n" );
    	sql.append("                      AND NOT EXISTS\n" );
    	sql.append("                    (SELECT 1\n" );
    	sql.append("                             FROM PT_BU_ACCOUNT_LOG AL_C, PT_BU_ACCOUNT A_C\n" );
    	sql.append("                            WHERE AL_C.ACCOUNT_ID = A_C.ACCOUNT_ID\n" );
    	sql.append("                              AND ((A_C.ACCOUNT_TYPE IN\n" );
    	sql.append("                                  (202701, 202702, 202703) AND\n" );
    	sql.append("                                  AL_C.LOG_TYPE = 202804) AND\n" );
    	sql.append("                                  AL_C.CREATE_USER <> 'YANGXUMING')\n" );
    	sql.append("                              AND AL_C.LOG_ID = AL.LOG_ID)\n" );
    	sql.append("                      AND A.ORG_ID = O.ORG_ID),\n" );
    	sql.append("                   0) BACK_AMOUNT -- 回款汇总\n" );
    	sql.append("          FROM TM_ORG O\n" );
    	sql.append("         WHERE O.ORG_TYPE = 200005) T,\n" );
    	sql.append("       (SELECT T.ORG_ID, SUM(T.REAL_AMOUNT) REAL_AMOUNT\n" );
    	sql.append("          FROM (SELECT A.ORDER_ID,\n" );
    	sql.append("                       A.ORDER_NO,\n" );
    	sql.append("                       A.ORG_ID,\n" );
    	sql.append("                       A.ORG_CODE,\n" );
    	sql.append("                       SUM(NVL(B.DELIVERY_COUNT, 0) * NVL(B.UNIT_PRICE, 0)) REAL_AMOUNT\n" );
    	sql.append("                  FROM PT_BU_SALE_ORDER A, PT_BU_SALE_ORDER_DTL B\n" );
    	sql.append("                 WHERE A.ORDER_ID = B.ORDER_ID\n" );
    	sql.append("                   AND A.IF_CREDIT = 100102\n" );
    	sql.append("                   AND A.IF_CHANEL_ORDER = 100102\n" );
    	sql.append("                   AND A.CLOSE_DATE >= TO_DATE('2014-11-03', 'YYYY-MM-DD')\n" );
    	sql.append("                   AND A.ORDER_STATUS = 202206\n" );
    	sql.append("                   AND A.ORDER_TYPE <> 203799\n" );
    	sql.append("                   AND A.FLAG IS NULL\n" );
    	sql.append("                 GROUP BY A.ORDER_ID, A.ORDER_NO, A.ORG_ID, A.ORG_CODE) T,\n" );
    	sql.append("               PT_BU_SALE_ORDER_PAY T1,\n" );
    	sql.append("               PT_BU_ACCOUNT T2\n" );
    	sql.append("         WHERE T.ORDER_ID = T1.ORDER_ID\n" );
    	sql.append("           AND T1.ACCOUNT_ID = T2.ACCOUNT_ID\n" );
    	sql.append("           AND T.REAL_AMOUNT > 0\n" );
    	sql.append("           AND T2.ACCOUNT_TYPE <> 202704\n" );
    	sql.append("           AND NOT EXISTS (SELECT 1\n" );
    	sql.append("                  FROM PT_BU_SALE_ORDER_OCCUPY_FUNDS S\n" );
    	sql.append("                 WHERE S.ORDER_ID = T.ORDER_ID\n" );
    	sql.append("                   AND S.STATUS = 100201)\n" );
    	sql.append("         GROUP BY T.ORG_ID) T1\n");
    	sql.append(" WHERE "+wheres+"\n");
		return this.factory.select(null, sql.toString());

	}
	public BaseResultSet searchList(PageManager pageManager, String conditions) throws Exception{
		String wheres = conditions;
    	wheres += " ORDER BY T.ORG_ID\n";
    	pageManager.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.ORG_ID,\n" );
    	sql.append("       T.ORG_CODE,\n" );
    	sql.append("       T.ORG_NAME,\n" );
    	sql.append("       T.ZJYE,\n" );
    	sql.append("       T.ZJGDKK,\n" );
    	sql.append("       T.ZJZY,\n" );
    	sql.append("       T.ZJKY,\n" );
    	sql.append("       T.XYED,\n" );
    	sql.append("       T.QCJZQK,\n" );
    	sql.append("       T.QCJZYH,\n" );
    	sql.append("       T.XYEDGDHK,\n" );
    	sql.append("       T.XYEDGDZY,\n" );
    	sql.append("       T.XYEDWGDZY,\n" );
    	sql.append("       T.XYEDKY,\n" );
    	sql.append("       (T.ZJYE + T.XYED) - (T.QCJZQK - QCJZYH) -\n" );
    	sql.append("       (T.ZJZY + T.XYEDGDZY + T.XYEDWGDZY) ZKY\n" );
    	sql.append("  FROM (SELECT A.ORG_ID,\n" );
    	sql.append("               A.CODE ORG_CODE,\n" );
    	sql.append("               A.ONAME ORG_NAME,\n" );
    	sql.append("               NVL(B.BALANCE_AMOUNT, 0) ZJYE,\n" );
    	sql.append("               NVL(B.CLOSE_ORDER_AMOUNT, 0) ZJGDKK,\n" );
    	sql.append("               NVL(B.OCCUPY_AMOUNT, 0) ZJZY,\n" );
    	sql.append("               NVL(B.AVAILABLE_AMOUNT, 0) ZJKY,\n" );
    	sql.append("               NVL(C.BALANCE_AMOUNT, 0) XYED,\n" );
    	sql.append("               NVL(C.CLOSE_AMOUNT, 0) QCJZQK,\n" );
    	sql.append("               NVL(C.PAY_CLOSE_AMOUNT, 0) QCJZYH,\n" );
    	sql.append("               NVL(C.CLOSE_REPAY_AMOUNT, 0) XYEDGDHK,\n" );
    	sql.append("               NVL(C.CLOSE_OCCUPY, 0) XYEDGDZY,\n" );
    	sql.append("               NVL(C.NO_CLOSE_OCCUPY, 0) XYEDWGDZY,\n" );
    	sql.append("               NVL(C.AVAILABLE_AMOUNT, 0) XYEDKY\n" );
    	sql.append("          FROM TM_ORG A,\n" );
    	sql.append("               (SELECT T.ORG_ID,\n" );
    	sql.append("                       NVL(SUM(NVL(T.BALANCE_AMOUNT, 0)), 0) BALANCE_AMOUNT,\n" );
    	sql.append("                       (SELECT SUM(REAL_AMOUNT)\n" );
    	sql.append("                          FROM PT_BU_SALE_ORDER S\n" );
    	sql.append("                         WHERE S.ORG_ID = T.ORG_ID\n" );
    	sql.append("                           AND S.ORDER_STATUS = 202206\n" );
    	sql.append("                           AND S.IF_CREDIT = 100102\n" );
    	sql.append("                           AND S.CLOSE_DATE >= TO_DATE('20141103', 'YYYYMMDD')) CLOSE_ORDER_AMOUNT,\n" );
    	sql.append("                       NVL(SUM(NVL(T.OCCUPY_AMOUNT, 0)), 0) OCCUPY_AMOUNT,\n" );
    	sql.append("                       NVL(SUM(NVL(T.AVAILABLE_AMOUNT, 0)), 0) AVAILABLE_AMOUNT\n" );
    	sql.append("                  FROM PT_BU_ACCOUNT T\n" );
    	sql.append("                 WHERE T.ACCOUNT_TYPE IN (202701, 202702, 202703, 202705)\n" );
    	sql.append("                 GROUP BY T.ORG_ID) B,\n" );
    	sql.append("               (SELECT T.ORG_ID,\n" );
    	sql.append("                       NVL(T.BALANCE_AMOUNT, 0) BALANCE_AMOUNT,\n" );
    	sql.append("                       (SELECT SUM(REPAY_AMOUNT)\n" );
    	sql.append("                          FROM PT_BU_SALE_ORDER_OCCUPY_FUNDS S\n" );
    	sql.append("                         WHERE S.ACCOUNT_ID = T.ACCOUNT_ID\n" );
    	sql.append("                           AND S.STATUS = 100202\n" );
    	sql.append("                           AND S.REPAY_AMOUNT > 0) CLOSE_REPAY_AMOUNT,\n" );
    	sql.append("                       (SELECT SUM(NVL(F.OCCUPY_FUNDS, 0) -\n" );
    	sql.append("                                   NVL(F.REPAY_AMOUNT, 0))\n" );
    	sql.append("                          FROM PT_BU_SALE_ORDER_OCCUPY_FUNDS F,\n" );
    	sql.append("                               PT_BU_SALE_ORDER              SO,\n" );
    	sql.append("                               PT_BU_ACCOUNT                 A\n" );
    	sql.append("                         WHERE F.ACCOUNT_ID = A.ACCOUNT_ID\n" );
    	sql.append("                           AND F.ORDER_ID = SO.ORDER_ID\n" );
    	sql.append("                           AND SO.ORDER_STATUS = 202206\n" );
    	sql.append("                           AND F.STATUS = 100201\n" );
    	sql.append("                           AND F.ACCOUNT_TYPE =202704\n" );
    	sql.append("                           AND A.ORG_ID = T.ORG_ID) CLOSE_OCCUPY,\n" );
    	sql.append("                       (SELECT SUM(NVL(F.OCCUPY_FUNDS, 0) -\n" );
    	sql.append("                                   NVL(F.REPAY_AMOUNT, 0))\n" );
    	sql.append("                          FROM PT_BU_SALE_ORDER_OCCUPY_FUNDS F,\n" );
    	sql.append("                               PT_BU_SALE_ORDER              SO,\n" );
    	sql.append("                               PT_BU_ACCOUNT                 A\n" );
    	sql.append("                         WHERE F.ACCOUNT_ID = A.ACCOUNT_ID\n" );
    	sql.append("                           AND F.ORDER_ID = SO.ORDER_ID\n" );
    	sql.append("                           AND SO.ORDER_STATUS IN (202202, 202203)\n" );
    	sql.append("                           AND F.STATUS = 100201\n" );
    	sql.append("                           AND F.ACCOUNT_TYPE =202704\n" );
    	sql.append("                           AND A.ORG_ID = T.ORG_ID) NO_CLOSE_OCCUPY,\n" );
    	sql.append("                       NVL(T.AVAILABLE_AMOUNT, 0) AVAILABLE_AMOUNT,\n" );
    	sql.append("                       NVL(T.CLOSE_AMOUNT, 0) CLOSE_AMOUNT,\n" );
    	sql.append("                       NVL(T.PAY_CLOSE_AMOUNT, 0) PAY_CLOSE_AMOUNT\n" );
    	sql.append("                  FROM PT_BU_ACCOUNT T\n" );
    	sql.append("                 WHERE T.ACCOUNT_TYPE = 202704) C\n" );
    	sql.append("         WHERE A.ORG_TYPE = 200005\n" );
    	sql.append("           AND A.ORG_ID = B.ORG_ID(+)\n" );
    	sql.append("           AND A.ORG_ID = C.ORG_ID(+)) T\n");
    	bs = factory.select(sql.toString(), pageManager);
    	return bs;
	}
}
