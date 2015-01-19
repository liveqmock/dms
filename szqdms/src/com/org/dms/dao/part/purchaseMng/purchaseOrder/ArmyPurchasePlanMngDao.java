package com.org.dms.dao.part.purchaseMng.purchaseOrder;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuPchOrderDtlVO;
import com.org.dms.vo.part.PtBuPchOrderVO;
import com.org.dms.vo.part.PtBuPurchaseplayTmpVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class ArmyPurchasePlanMngDao extends BaseDAO{
	 public  static final ArmyPurchasePlanMngDao getInstance(ActionContext atx)
	    {
		 ArmyPurchasePlanMngDao dao = new ArmyPurchasePlanMngDao();
	        atx.setDBFactory(dao.factory);
	        return dao;
	    }
	 
	 public BaseResultSet partSearch(PageManager page, User user, String conditions,String type,String account) throws Exception
	    {
	    	String wheres = conditions;
	    	wheres += " AND T.PART_ID = T0.PART_ID AND T0.STATUS="+DicConstant.YXBS_01+" AND T.ORDER_COUNT>0 AND T0.USER_ACCOUNT = '"+account+"'\n";
	    	wheres += " ORDER BY T.PART_CODE,T.SUPPLIER_CODE ASC\n";
	        page.setFilter(wheres);
	    	BaseResultSet bs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT T.PART_ID||T.SUPPLIER_ID KEY_ID,\n" );
	    	sql.append("       T.PART_ID,\n" );
	    	sql.append("       T.PART_CODE,\n" );
	    	sql.append("       T.PART_NAME,\n" );
	    	sql.append("       T.PART_NO,\n" );
	    	sql.append("       T.UNIT,\n" );
	    	sql.append("       T.MIN_PACK,\n" );
	    	sql.append("       T.MIN_UNIT,\n" );
	    	sql.append("       T.IF_SUPLY,\n" );
	    	sql.append("       T.SUPPLIER_ID,\n" );
	    	sql.append("       T.APPLY_CYCLE,\n" );
	    	sql.append("       T.SUPPLIER_CODE,\n" );
	    	sql.append("       T.SUPPLIER_NAME,\n" );
	    	sql.append("       DECODE(T.IF_SUPLY,\n" );
	    	sql.append("              100101,\n" );
	    	sql.append("              1,\n" );
	    	sql.append("              (SELECT COUNT(*)\n" );
	    	sql.append("                 FROM PT_BA_PART_SUPPLIER_RL A, PT_BA_SUPPLIER B\n" );
	    	sql.append("                WHERE A.SUPPLIER_ID = B.SUPPLIER_ID\n" );
	    	sql.append("                  AND A.PART_ID = T.PART_ID\n" );
	    	sql.append("                  AND A.PART_IDENTIFY ="+DicConstant.YXBS_01+"\n" );
	    	sql.append("                  AND B.PART_IDENTIFY = "+DicConstant.YXBS_01+")) ROWSPAN,\n" );
	    	sql.append("       T.PLAN_PRICE,\n" );
	    	sql.append("       T.ORDER_COUNT,\n" );
	    	sql.append("       T.ALL_PRICE,\n" );
	    	sql.append("       T.AVAILABLE_AMOUNT,\n" );
	    	sql.append("       T.UPPER_LIMIT,\n" );
	    	sql.append("       T.LOWER_LIMIT,\n" );
	    	sql.append("       T0.USER_ACCOUNT,\n" );
	    	sql.append("       (SELECT WM_CONCAT(I.AREA_CODE)\n" );
	    	sql.append("                                   FROM PT_BA_WAREHOUSE_POSITION H,\n" );
	    	sql.append("                                        PT_BA_WAREHOUSE_AREA     I,\n" );
	    	sql.append("                                        PT_BA_WAREHOUSE          J,\n" );
	    	sql.append("                                        PT_BA_WAREHOUSE_PART_RL  G\n" );
	    	sql.append("                                  WHERE H.AREA_ID = I.AREA_ID\n" );
	    	sql.append("                                    AND I.WAREHOUSE_ID = J.WAREHOUSE_ID\n" );
	    	sql.append("                                    AND G.PART_ID = T.PART_ID\n" );
	    	sql.append("                                    AND G.POSITION_ID = H.POSITION_ID\n" );
	    	sql.append("                                    AND J.WAREHOUSE_TYPE = 100102\n" );
	    	sql.append("                                    AND H.STATUS = "+DicConstant.YXBS_01+") POSITION_NAME\n" );
	    	sql.append("  FROM (SELECT T.PART_ID,\n" );
	    	sql.append("               T.PART_CODE,\n" );
	    	sql.append("               T.PART_NAME,\n" );
	    	sql.append("               T.PART_NO,\n" );
	    	sql.append("               T.UNIT,\n" );
	    	sql.append("               T.MIN_PACK,\n" );
	    	sql.append("               T.MIN_UNIT,\n" );
	    	sql.append("               T.IF_SUPLY,\n" );
	    	sql.append("               T.SUPPLIER_ID,\n" );
	    	sql.append("               T.APPLY_CYCLE,\n" );
	    	sql.append("               T.SUPPLIER_CODE,\n" );
	    	sql.append("               T.SUPPLIER_NAME,\n" );
	    	sql.append("               T.PLAN_PRICE,\n" );
	    	sql.append("               (T.ORDER_COUNT + NVL(S.LOWER_LIMIT,0)- NVL(T.PCH_COUNT,0) - NVL(S.AVAILABLE_AMOUNT,0)) ORDER_COUNT,\n" );
	    	sql.append("               (T.ORDER_COUNT + NVL(S.LOWER_LIMIT,0)- NVL(T.PCH_COUNT,0) - NVL(S.AVAILABLE_AMOUNT,0)) * T.PLAN_PRICE ALL_PRICE,\n" );
	    	sql.append("               S.AVAILABLE_AMOUNT,\n" );
	    	sql.append("               S.UPPER_LIMIT,\n" );
	    	sql.append("               S.LOWER_LIMIT\n" );
	    	sql.append("          FROM (SELECT T1.PART_ID,\n" );
	    	sql.append("                       T1.PART_CODE,\n" );
	    	sql.append("                       T1.PART_NAME,\n" );
	    	sql.append("                       T1.PART_NO,\n" );
	    	sql.append("                       T1.UNIT,\n" );
	    	sql.append("                       T1.MIN_PACK,\n" );
	    	sql.append("                       T1.MIN_UNIT,\n" );
	    	sql.append("                       T1.IF_SUPLY,\n" );
	    	sql.append("                       T1.SUPPLIER_ID,\n" );
	    	sql.append("                       T1.APPLY_CYCLE,\n" );
	    	sql.append("                       T1.SUPPLIER_CODE,\n" );
	    	sql.append("                       T1.SUPPLIER_NAME,\n" );
	    	sql.append("                       T1.PLAN_PRICE,\n" );
	    	sql.append("                       NVL(T1.ORDER_COUNT, 0) ORDER_COUNT,\n" );
	    	sql.append("                       NVL(T2.PCH_COUNT, 0) PCH_COUNT\n" );
	    	sql.append("                  FROM (SELECT B.PART_ID,\n" );
	    	sql.append("                               B.PART_CODE,\n" );
	    	sql.append("                               C.PART_NAME,\n" );
	    	sql.append("                               C.PART_NO,\n" );
	    	sql.append("                               C.UNIT,\n" );
	    	sql.append("                               C.MIN_PACK,\n" );
	    	sql.append("                               C.MIN_UNIT,\n" );
	    	sql.append("                               C.IF_SUPLY,\n" );
	    	sql.append("                               B.SUPPLIER_ID,\n" );
	    	sql.append("                               E.APPLY_CYCLE,\n" );
	    	sql.append("                               B.SUPPLIER_CODE,\n" );
	    	sql.append("                               B.SUPPLIER_NAME,\n" );
	    	sql.append("                               C.PLAN_PRICE,\n" );
	    	sql.append("                               NVL(SUM(B.ORDER_COUNT), 0) ORDER_COUNT\n" );
	    	sql.append("                          FROM PT_BU_SALE_ORDER     A,\n" );
	    	sql.append("                               PT_BU_SALE_ORDER_DTL B,\n" );
	    	sql.append("                               PT_BA_INFO           C,\n" );
	    	sql.append("                               PT_BA_SUPPLIER       D,\n" );
	    	sql.append("                               PT_BA_PART_SUPPLIER_RL E\n" );
	    	sql.append("                         WHERE A.ORDER_ID = B.ORDER_ID\n" );
	    	sql.append("                           AND B.PART_ID = C.PART_ID\n" );
	    	sql.append("                           AND B.SUPPLIER_ID = D.SUPPLIER_ID\n" );
	    	sql.append("                           AND B.SUPPLIER_ID = E.SUPPLIER_ID\n" );
	    	sql.append("						   AND A.ORDER_STATUS = "+DicConstant.DDZT_02+"\n" );
	    	sql.append("                           AND B.PART_ID = E.PART_ID  AND B.IF_SUPPLIER = "+DicConstant.SF_01+"\n" );
	    	sql.append("                           AND C.PART_STATUS = "+DicConstant.PJZT_01+"\n" );
	    	sql.append("                           AND A.IF_DELAY_ORDER = "+DicConstant.SF_01+"\n" );
	    	sql.append("                           AND A.ORDER_TYPE = "+DicConstant.DDLX_08+"\n" );
	    	sql.append("                           AND A.IF_CHANEL_ORDER = "+DicConstant.SF_02+" AND D.PART_IDENTIFY = "+DicConstant.YXBS_01+" AND E.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n" );
//	    	sql.append("						   AND EXISTS\n" );
//	    	sql.append("                        (SELECT 1\n" );
//	    	sql.append("                                 FROM PT_BU_PCH_CONTRACT     O,\n" );
//	    	sql.append("                                      PT_BU_PCH_CONTRACT_DTL P\n" );
//	    	sql.append("                                WHERE O.CONTRACT_ID = O.CONTRACT_ID\n" );
//	    	sql.append("                                  AND D.SUPPLIER_CODE = O.SUPPLIER_CODE\n" );
//	    	sql.append("                                  AND C.PART_CODE = P.PART_CODE\n" );
//	    	sql.append("                                  AND O.CONTRACT_STATUS IN("+DicConstant.CGHTZT_09+","+DicConstant.CGHTZT_10+")\n" );
//	    	sql.append("                                  AND O.STATUS = "+DicConstant.YXBS_01+")");
	    	sql.append("                         GROUP BY B.PART_ID,\n" );
	    	sql.append("                                  B.PART_CODE,\n" );
	    	sql.append("                                  C.PART_NAME,\n" );
	    	sql.append("                                  C.PART_NO,\n" );
	    	sql.append("                                  C.UNIT,\n" );
	    	sql.append("                                  C.MIN_PACK,\n" );
	    	sql.append("                                  C.MIN_UNIT,\n" );
	    	sql.append("                                  C.IF_SUPLY,\n" );
	    	sql.append("                                  B.SUPPLIER_ID,\n" );
	    	sql.append("                                  E.APPLY_CYCLE,\n" );
	    	sql.append("                                  B.SUPPLIER_CODE,\n" );
	    	sql.append("                                  B.SUPPLIER_NAME,\n" );
	    	sql.append("                                  C.PLAN_PRICE) T1\n" );
	    	sql.append("                  LEFT JOIN (SELECT B.PART_ID,\n" );
	    	sql.append("                                   A.SUPPLIER_ID,\n" );
	    	sql.append("                                   NVL(SUM(B.PCH_COUNT), 0) PCH_COUNT\n" );
	    	sql.append("                              FROM PT_BU_PCH_ORDER_SPLIT     A,\n" );
	    	sql.append("                                   PT_BU_PCH_ORDER_SPLIT_DTL B,\n" );
	    	sql.append("                                   PT_BA_INFO                C\n" );
	    	sql.append("                             WHERE A.SPLIT_ID = B.SPLIT_ID\n" );
	    	sql.append("                               AND B.PART_ID = C.PART_ID\n" );
	    	sql.append("                               AND A.ORDER_TYPE = "+DicConstant.CGDDLX_04+"\n" );
	    	sql.append("                               AND C.IF_SUPLY = "+DicConstant.SF_01+"\n" );
	    	sql.append("                               AND A.ORDER_STATUS IN ("+DicConstant.CGDDZT_01+","+DicConstant.CGDDZT_02+", "+DicConstant.CGDDZT_03+", "+DicConstant.CGDDZT_04+")\n" );
	    	sql.append("                               AND NVL(B.PCH_COUNT, 0) > NVL(B.STORAGE_COUNT, 0)\n" );
	    	sql.append("                             GROUP BY B.PART_ID, A.SUPPLIER_ID) T2\n" );
	    	sql.append("                    ON T1.PART_ID = T2.PART_ID\n" );
	    	sql.append("                   AND T1.SUPPLIER_ID = T2.SUPPLIER_ID) T\n" );
	    	sql.append("          LEFT JOIN (SELECT T1.PART_ID,T1.AVAILABLE_AMOUNT,T1.SUPPLIER_ID,F.UPPER_LIMIT,F.LOWER_LIMIT FROM(SELECT E.PART_ID,\n" );
	    	sql.append("                            E.AVAILABLE_AMOUNT,\n" );
	    	sql.append("                            E.SUPPLIER_ID,\n" );
	    	sql.append("                            E.WAREHOUSE_ID\n");
	    	sql.append("                       FROM PT_BU_STOCK E WHERE E.WAREHOUSE_ID = (SELECT WAREHOUSE_ID FROM PT_BA_WAREHOUSE WHERE WAREHOUSE_TYPE = 100102 AND WAREHOUSE_STATUS = "+DicConstant.YXBS_01+"))T1\n" );
	    	sql.append("                       LEFT JOIN PT_BA_STOCK_SAFESTOCKS F\n" );
	    	sql.append("                         ON T1.PART_ID = F.PART_ID\n" );
	    	sql.append("                        AND T1.SUPPLIER_ID = F.SUPPLIER_ID\n" );
	    	sql.append("                        AND T1.WAREHOUSE_ID = F.STOCK_ID) S\n" );
	    	sql.append("            ON T.PART_ID = S.PART_ID\n" );
	    	sql.append("           AND T.SUPPLIER_ID = S.SUPPLIER_ID\n" );
	    	sql.append("        UNION ALL\n" );
	    	sql.append("        SELECT T.PART_ID,\n" );
	    	sql.append("               T.PART_CODE,\n" );
	    	sql.append("               T.PART_NAME,\n" );
	    	sql.append("               T.PART_NO,\n" );
	    	sql.append("               T.UNIT,\n" );
	    	sql.append("               T.MIN_PACK,\n" );
	    	sql.append("               T.MIN_UNIT,\n" );
	    	sql.append("               T.IF_SUPLY,\n" );
	    	sql.append("               T.SUPPLIER_ID,\n" );
	    	sql.append("               T.APPLY_CYCLE,\n" );
	    	sql.append("               T.SUPPLIER_CODE,\n" );
	    	sql.append("               T.SUPPLIER_NAME,\n" );
	    	sql.append("               T.PLAN_PRICE,\n" );
	    	sql.append("               (T.ORDER_COUNT + NVL(S.LOWER_LIMIT,0)- NVL(T.PCH_COUNT,0) - NVL(S.AVAILABLE_AMOUNT,0)) ORDER_COUNT,\n" );
	    	sql.append("               (T.ORDER_COUNT + NVL(S.LOWER_LIMIT,0)- NVL(T.PCH_COUNT,0) - NVL(S.AVAILABLE_AMOUNT,0)) * T.PLAN_PRICE ALL_PRICE,\n" );
	    	sql.append("               S.AVAILABLE_AMOUNT,\n" );
	    	sql.append("               S.UPPER_LIMIT,\n" );
	    	sql.append("               S.LOWER_LIMIT\n" );
	    	sql.append("          FROM (SELECT T1.PART_ID,\n" );
	    	sql.append("                       T1.PART_CODE,\n" );
	    	sql.append("                       T1.PART_NAME,\n" );
	    	sql.append("                       T1.PART_NO,\n" );
	    	sql.append("                       T1.UNIT,\n" );
	    	sql.append("                       T1.MIN_PACK,\n" );
	    	sql.append("                       T1.MIN_UNIT,\n" );
	    	sql.append("                       T1.IF_SUPLY,\n" );
	    	sql.append("                       T1.SUPPLIER_ID,\n" );
	    	sql.append("                       T1.APPLY_CYCLE,\n" );
	    	sql.append("                       T1.SUPPLIER_CODE,\n" );
	    	sql.append("                       T1.SUPPLIER_NAME,\n" );
	    	sql.append("                       T1.PLAN_PRICE,\n" );
	    	sql.append("                       NVL(T1.ORDER_COUNT, 0) ORDER_COUNT,\n" );
	    	sql.append("                       NVL(T2.PCH_COUNT, 0) PCH_COUNT\n" );
	    	sql.append("                  FROM (SELECT B.PART_ID,\n" );
	    	sql.append("                               B.PART_CODE,\n" );
	    	sql.append("                               C.PART_NAME,\n" );
	    	sql.append("                               C.PART_NO,\n" );
	    	sql.append("                               C.UNIT,\n" );
	    	sql.append("                               C.MIN_PACK,\n" );
	    	sql.append("                               C.MIN_UNIT,\n" );
	    	sql.append("                               C.IF_SUPLY,\n" );
	    	sql.append("                               D.SUPPLIER_ID,\n" );
	    	sql.append("                               D.APPLY_CYCLE,\n" );
	    	sql.append("                               E.SUPPLIER_CODE,\n" );
	    	sql.append("                               E.SUPPLIER_NAME,\n" );
	    	sql.append("                               C.PLAN_PRICE,\n" );
	    	sql.append("                               NVL(SUM(B.ORDER_COUNT), 0) ORDER_COUNT,\n" );
	    	sql.append("                               NVL(SUM(B.ORDER_COUNT), 0) * C.PLAN_PRICE ALL_PRICE\n" );
	    	sql.append("                          FROM PT_BU_SALE_ORDER       A,\n" );
	    	sql.append("                               PT_BU_SALE_ORDER_DTL   B,\n" );
	    	sql.append("                               PT_BA_INFO             C,\n" );
	    	sql.append("                               PT_BA_PART_SUPPLIER_RL D,\n" );
	    	sql.append("                               PT_BA_SUPPLIER         E\n" );
	    	sql.append("                         WHERE A.ORDER_ID = B.ORDER_ID\n" );
	    	sql.append("                           AND B.PART_ID = C.PART_ID\n" );
	    	sql.append("                           AND C.PART_ID = D.PART_ID\n" );
	    	sql.append("                           AND D.SUPPLIER_ID = E.SUPPLIER_ID\n" );
	    	sql.append("						   AND A.ORDER_STATUS = "+DicConstant.DDZT_02+"\n" );
	    	sql.append("                           AND C.PART_STATUS = "+DicConstant.PJZT_01+"\n" );
	    	sql.append("                           AND B.IF_SUPPLIER = "+DicConstant.SF_02+"\n" );
	    	sql.append("                           AND A.IF_DELAY_ORDER = "+DicConstant.SF_01+"\n" );
	    	sql.append("                           AND A.ORDER_TYPE = "+DicConstant.DDLX_08+" AND D.PART_IDENTIFY = "+DicConstant.YXBS_01+" AND E.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n" );
//	    	sql.append("						   AND EXISTS\n" );
//	    	sql.append("                        (SELECT 1\n" );
//	    	sql.append("                                 FROM PT_BU_PCH_CONTRACT     O,\n" );
//	    	sql.append("                                      PT_BU_PCH_CONTRACT_DTL P\n" );
//	    	sql.append("                                WHERE O.CONTRACT_ID = O.CONTRACT_ID\n" );
//	    	sql.append("                                  AND E.SUPPLIER_CODE = O.SUPPLIER_CODE\n" );
//	    	sql.append("                                  AND C.PART_CODE = P.PART_CODE\n" );
//	    	sql.append("                                  AND O.CONTRACT_STATUS IN("+DicConstant.CGHTZT_09+", "+DicConstant.CGHTZT_10+")\n" );
//	    	sql.append("                                  AND O.STATUS = "+DicConstant.YXBS_01+")");
	    	sql.append("                         GROUP BY B.PART_ID,\n" );
	    	sql.append("                                  B.PART_CODE,\n" );
	    	sql.append("                                  C.PART_NAME,\n" );
	    	sql.append("                                  C.PART_NO,\n" );
	    	sql.append("                                  C.UNIT,\n" );
	    	sql.append("                                  C.MIN_PACK,\n" );
	    	sql.append("                                  C.MIN_UNIT,\n" );
	    	sql.append("                                  C.IF_SUPLY,\n" );
	    	sql.append("                                  D.SUPPLIER_ID,\n" );
	    	sql.append("                                  D.APPLY_CYCLE,\n" );
	    	sql.append("                                  E.SUPPLIER_CODE,\n" );
	    	sql.append("                                  E.SUPPLIER_NAME,\n" );
	    	sql.append("                                  C.PLAN_PRICE) T1\n" );
	    	sql.append("                  LEFT JOIN (SELECT B.PART_ID,\n" );
	    	sql.append("                                   NVL(SUM(B.PCH_COUNT), 0) PCH_COUNT\n" );
	    	sql.append("                              FROM PT_BU_PCH_ORDER_SPLIT     A,\n" );
	    	sql.append("                                   PT_BU_PCH_ORDER_SPLIT_DTL B,\n" );
	    	sql.append("                                   PT_BA_INFO                C\n" );
	    	sql.append("                             WHERE A.SPLIT_ID = B.SPLIT_ID\n" );
	    	sql.append("                               AND B.PART_ID = C.PART_ID\n" );
	    	sql.append("                               AND A.ORDER_TYPE = "+DicConstant.CGDDLX_04+"\n" );
	    	sql.append("                               AND C.IF_SUPLY = "+DicConstant.SF_02+"\n" );
	    	sql.append("                               AND A.ORDER_STATUS IN ("+DicConstant.CGDDZT_01+","+DicConstant.CGDDZT_02+", "+DicConstant.CGDDZT_03+", "+DicConstant.CGDDZT_04+")\n" );
	    	sql.append("                               AND NVL(B.PCH_COUNT, 0) > NVL(B.STORAGE_COUNT, 0)\n" );
	    	sql.append("                             GROUP BY B.PART_ID) T2\n" );
	    	sql.append("                    ON T1.PART_ID = T2.PART_ID) T\n" );
	    	sql.append("          LEFT JOIN (SELECT T1.PART_ID,T1.AVAILABLE_AMOUNT,T1.SUPPLIER_ID,F.UPPER_LIMIT,F.LOWER_LIMIT FROM(SELECT E.PART_ID,\n" );
	    	sql.append("                            E.AVAILABLE_AMOUNT,\n" );
	    	sql.append("                            E.SUPPLIER_ID,\n" );
	    	sql.append("                            E.WAREHOUSE_ID\n");
	    	sql.append("                       FROM PT_BU_STOCK E WHERE E.WAREHOUSE_ID = (SELECT WAREHOUSE_ID FROM PT_BA_WAREHOUSE WHERE WAREHOUSE_TYPE = 100102 AND WAREHOUSE_STATUS = "+DicConstant.YXBS_01+"))T1\n" );
	    	sql.append("                       LEFT JOIN PT_BA_STOCK_SAFESTOCKS F\n" );
	    	sql.append("                         ON T1.PART_ID = F.PART_ID\n" );
	    	sql.append("                        AND T1.SUPPLIER_ID = F.SUPPLIER_ID\n" );
	    	sql.append("                        AND T1.WAREHOUSE_ID = F.STOCK_ID) S\n" );
	    	sql.append("            ON T.PART_ID = S.PART_ID) T,PT_BA_PCH_ATTRIBUTE T0");
	    	bs = factory.select(sql.toString(), page);
			bs.setFieldUserID("USER_ACCOUNT");
			bs.setFieldDic("IF_SUPLY", "SF");
			bs.setFieldDic("UNIT", "JLDW");
			bs.setFieldDic("MIN_UNIT", "JLDW");
	    	return bs;
	    }
	 
	 public BaseResultSet partReSearch(PageManager page, String account, String conditions,String type) throws Exception
	    {
	    	String wheres = conditions;
	    	wheres += "  AND T.USER_ACCOUNT ='"+account+"' AND T.PLAN_TYPE='"+type+"'\n";
	    	wheres += " ORDER BY T.ID ASC\n";
	        page.setFilter(wheres);
	    	BaseResultSet bs = null;
	    	StringBuffer sql= new StringBuffer();
	    	//sql.append("SELECT PART_ID||SUPPLIER_ID KEY_ID,PART_ID,PART_CODE,PART_NAME,PART_NO,UNIT,MIN_PACK,MIN_UNIT,IF_SUPLY,SUPPLIER_ID,APPLY_CYCLE,SUPPLIER_CODE,SUPPLIER_NAME,ROWSPAN,PLAN_PRICE,ORDER_COUNT,ALL_PRICE,AVAILABLE_AMOUNT,UPPER_LIMIT,LOWER_LIMIT,USER_ACCOUNT,POSITION_NAME,PCH_COUNT,REMARK,PLAN_TYPE FROM PT_BU_PURCHASEPLAY_TMP");
	    	sql.append("SELECT T.PART_ID || T.SUPPLIER_ID KEY_ID,\n" );
	    	sql.append("       T.PART_ID,\n" );
	    	sql.append("       T.PART_CODE,\n" );
	    	sql.append("       T.PART_NAME,\n" );
	    	sql.append("       T.PART_NO,\n" );
	    	sql.append("       T.UNIT,\n" );
	    	sql.append("       T.MIN_PACK,\n" );
	    	sql.append("       T.MIN_UNIT,\n" );
	    	sql.append("       T.IF_SUPLY,\n" );
	    	sql.append("       T.SUPPLIER_ID,\n" );
	    	sql.append("       T.APPLY_CYCLE,\n" );
	    	sql.append("       T.SUPPLIER_CODE,\n" );
	    	sql.append("       T.SUPPLIER_NAME,\n" );
	    	sql.append("       T.ROWSPAN,\n" );
	    	sql.append("       T.PLAN_PRICE,\n" );
	    	sql.append("       T.ORDER_COUNT,\n" );
	    	sql.append("       T.ALL_PRICE,\n" );
	    	sql.append("       T.AVAILABLE_AMOUNT,\n" );
	    	sql.append("       T.UPPER_LIMIT,\n" );
	    	sql.append("       T.LOWER_LIMIT,\n" );
	    	sql.append("       T.USER_ACCOUNT,\n" );
	    	sql.append("       T.POSITION_NAME,\n" );
	    	sql.append("       T.PCH_COUNT,\n" );
	    	sql.append("       T.REMARK,\n" );
	    	sql.append("       T.PLAN_TYPE\n" );
	    	sql.append("  FROM PT_BU_PURCHASEPLAY_TMP T");
	    	bs = factory.select(sql.toString(), page);
			bs.setFieldUserID("USER_ACCOUNT");
			bs.setFieldDic("IF_SUPLY", "SF");
			bs.setFieldDic("UNIT", "JLDW");
			bs.setFieldDic("MIN_UNIT", "JLDW");
	    	return bs;
	    }
	 
	 public QuerySet download(String conditions,User user,String type)throws Exception{
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT ROWNUM,SUM(T1.ORDER_COUNT) COUNT,\n" );
		 sql.append("       T1.PART_ID,\n" );
		 sql.append("       T1.PART_CODE,\n" );
		 sql.append("       T1.PART_NAME,\n" );
		 sql.append("       T2.USER_NAME\n" );
		 sql.append("  FROM PT_BU_SALE_ORDER T, PT_BU_SALE_ORDER_DTL T1, PT_BA_PCH_ATTRIBUTE T2\n" );
		 sql.append(" WHERE 1 = 1\n" );
		 sql.append("   AND T.ORDER_ID = T1.ORDER_ID\n" );
		 sql.append("   AND T1.PART_ID = T2.PART_ID\n" );
		 sql.append("   AND T2.USER_ACCOUNT = '"+user.getAccount()+"'\n");
		 if(type.equals(DicConstant.CGDDLX_01)){
			 sql.append("   AND T.ORDER_TYPE != "+DicConstant.DDLX_01+"\n" );
		 }else {
			 sql.append("   AND T.ORDER_TYPE != "+DicConstant.DDLX_01+"\n" );
		 }
		 sql.append("   AND T.IF_DELAY_ORDER = "+DicConstant.SF_01+"\n" );
		 sql.append("   AND T.IF_CHANEL_ORDER = "+DicConstant.SF_02+"\n" );
		 sql.append("   AND T.ORDER_STATUS = "+DicConstant.DDZT_02+"\n" );
		 sql.append(" GROUP BY T1.PART_ID, T1.PART_CODE, T1.PART_NAME, T2.USER_NAME,ROWNUM");
		 sql.append(" ORDER BY ROWNUM ");
		    return factory.select(null, sql.toString());
		}
	 public boolean insertPurchaseOrder(PtBuPchOrderVO vo)
	            throws Exception
	    {
	    	return factory.insert(vo);
	    }
	 public boolean updatePurchaseOrder(PtBuPchOrderVO vo)
	            throws Exception
	    {
	    	return factory.update(vo);
	    }
	 public boolean inserPurchaseOrderDtl(PtBuPchOrderDtlVO vo)
	            throws Exception
	    {
	    	return factory.insert(vo);
	    }
	 public QuerySet getSup(String SUPPLIERS) throws Exception
	    {
	    	QuerySet qs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT DISTINCT T.SUPPLIER_ID, T.SUPPLIER_NAME, T.SUPPLIER_CODE\n" );
	    	sql.append("  FROM PT_BA_SUPPLIER T\n" );
	    	sql.append(" WHERE T.SUPPLIER_ID IN ("+SUPPLIERS+") AND T.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n");
	    	qs = factory.select(null, sql.toString());
	    	return qs;
	    }
	 public QuerySet getPart(String PART_ID,String SUPPLIER_ID) throws Exception
	    {
	    	QuerySet qs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT T2.USER_ACCOUNT,T.APPLY_CYCLE,NVL(T.PCH_PRICE,0) PCH_PRICE,NVL(T1.PLAN_PRICE,0) PLAN_PRICE,T1.PART_CODE,T1.PART_NAME FROM PT_BA_PART_SUPPLIER_RL T,PT_BA_INFO T1,PT_BA_PCH_ATTRIBUTE T2 WHERE T.PART_ID = T1.PART_ID AND T.PART_ID = "+PART_ID+" AND T.SUPPLIER_ID="+SUPPLIER_ID+" AND T.PART_ID = T2.PART_ID AND T.PART_IDENTIFY = "+DicConstant.YXBS_01+"");
	    	qs = factory.select(null, sql.toString());
	    	return qs;
	    }
	 public QuerySet checkPch(String PURCHASE_TYPE,String SUPPLIER_CODE,User user) throws Exception
	    {
	    	QuerySet qs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT 1\n" );
	    	sql.append("  FROM PT_BU_PCH_ORDER\n" );
	    	sql.append(" WHERE PURCHASE_TYPE ="+PURCHASE_TYPE+"\n" );
	    	sql.append("   AND SUPPLIER_CODE ='"+SUPPLIER_CODE+"'\n" );
	    	sql.append("   AND APPLY_USER ='"+user.getAccount()+"'\n");
	    	sql.append("   AND ORDER_STATUS ="+DicConstant.CGDDZT_01+"\n");
	    	qs = factory.select(null, sql.toString());
	    	return qs;
	    }
	 public QuerySet getDic(String purchaseType) throws Exception
	    {
	    	QuerySet qs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT DIC_VALUE\n" );
	    	sql.append("FROM DIC_TREE WHERE ID ="+purchaseType+"");
	    	qs = factory.select(null, sql.toString());
	    	return qs;
	    }
	 
	 public boolean insertPurchasePlanTmp(PtBuPurchaseplayTmpVO vo)
	            throws Exception
	    {
	    	return factory.insert(vo);
	    }
	 public boolean deletePurchasePlanTmp(String type, String account)
	            throws Exception
	    {
		 	String sql = " DELETE FROM PT_BU_PURCHASEPLAY_TMP WHERE PLAN_TYPE='" + type + "' AND USER_ACCOUNT='" + account +"'";
	    	return factory.update(sql, null);
	    }
	 public QuerySet getApply() throws Exception
	    {
	    	QuerySet qs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT DISTINCT USER_ACCOUNT FROM PT_BA_PCH_ATTRIBUTE\n" );
	    	qs = factory.select(null, sql.toString());
	    	return qs;
	    }
}
