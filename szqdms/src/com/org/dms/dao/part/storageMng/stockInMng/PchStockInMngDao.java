package com.org.dms.dao.part.storageMng.stockInMng;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.common.PartOddNumberUtil;
import com.org.dms.vo.part.*;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.*;
import com.org.mvc.context.ActionContext;

import java.util.Map;

/**
 * 采购验收入库Dao
 *
 * @user : lichuang
 * @date : 2014-07-15
 */
public class PchStockInMngDao extends BaseDAO {
    //定义instance
    public static final PchStockInMngDao getInstance(ActionContext atx) {
        PchStockInMngDao dao = new PchStockInMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    public QuerySet getArea(String position) throws Exception {
    	String sql = "SELECT AREA_ID,AREA_CODE,AREA_NAME FROM PT_BA_WAREHOUSE_POSITION WHERE POSITION_ID='"+position+"'\n";
        return factory.select(null, sql);
    }

    // 插入库存日志
    public void insetStockDtl(String saleCount,User user,String saleId,String url,String nPartId) throws Exception {
 	   StringBuffer sql= new StringBuffer();
 	   sql.append("INSERT INTO PT_BU_DEALER_STOCK_LOG\n" );
 	   sql.append("          (LOG_ID,\n" );
 	   sql.append("           YWZJ,\n" );
 	   sql.append("           ACTION_URL,\n" );
 	   sql.append("           OAMOUNT,\n" );
 	   sql.append("           AMOUNT,\n" );
 	   sql.append("           AAMOUNT,\n" );
 	   sql.append("           UPDATE_USER,\n" );
 	   sql.append("           UPDATE_TIME,\n" );
 	   sql.append("           PART_ID,\n" );
 	   sql.append("           ORG_ID)\n" );
 	   sql.append("        VALUES\n" );
 	   sql.append("          (F_GETID(),\n" );
 	   sql.append("           '"+saleId+"',\n" );
 	   sql.append("           '"+url+"',\n" );
 	   sql.append("           '',\n" );
 	   sql.append("           '"+saleCount+"',\n" );
 	   sql.append("           '"+saleCount+"',\n" );
 	   sql.append("           '"+user.getAccount()+"',\n" );
 	   sql.append("           SYSDATE,\n" );
 	   sql.append("           '"+nPartId+"',\n" );
 	   sql.append("           "+user.getOrgId()+")");

 	   factory.update(sql.toString(), null);
    }

    /**
     * 查询入库单
     *
     * @param page
     * @param user
     * @param conditions
     * @return
     * @throws Exception
     */
    public BaseResultSet searchInBill(PageManager page, User user, String conditions,String partCode,String partName,String distributionNo,String ACCOUNT) throws Exception {
    	String sql1 = "SELECT WAREHOUSE_TYPE FROM PT_BA_WAREHOUSE T1,TM_USER T2 WHERE T1.ORG_ID = T2.ORG_ID AND T2.ACCOUNT = '"+ACCOUNT+"'\n";
		QuerySet qs  = factory.select(null, sql1);
		String warehouseType = "";
		if(qs.getRowCount()>0){
			warehouseType = qs.getString(1, "WAREHOUSE_TYPE");
		}else{
			warehouseType = "";
		}
    	StringBuilder wheres = new StringBuilder(conditions);
        wheres.append("  AND T1.SPLIT_ID = T2.SPLIT_ID\n" );
        wheres.append("   AND T1.WAREHOUSE_ID = T2.WAREHOUSE_ID");
//        wheres.append("   AND A.ORDER_ID = B.SPLIT_ID\n");
//        wheres.append("   AND A.IN_TYPE = " + DicConstant.RKLX_01 + "\n");
//        wheres.append("   AND A.PRINT_STATUS = " + DicConstant.DYZT_01 + "\n");
//        wheres.append("   AND A.STATUS = " + DicConstant.YXBS_01 + "\n");
//        wheres.append("   AND A.OEM_COMPANY_ID=" + user.getOemCompanyId() + "\n");
        wheres.append("   ORDER BY T1.CREATE_TIME DESC\n");
        page.setFilter(wheres.toString());

        //定义返回结果集
        BaseResultSet bs = null;
//        StringBuilder sql = new StringBuilder();
//        sql.append("SELECT A.IN_ID,\n");
//        sql.append("       A.IN_NO,\n");
//        sql.append("       A.IN_TYPE,\n");
//        sql.append("       A.WAREHOUSE_ID,\n");
//        sql.append("       A.WAREHOUSE_CODE,\n");
//        sql.append("       A.WAREHOUSE_NAME,\n");
//        sql.append("       A.ORDER_ID,\n");
//        sql.append("       A.ORDER_NO,\n");
//        sql.append("       A.BUYER,\n");
//        sql.append("       A.SUPPLIER_ID,\n");
//        sql.append("       A.SUPPLIER_CODE,\n");
//        sql.append("       A.SUPPLIER_NAME,\n");
//        sql.append("       B.PURCHASE_AMOUNT,\n");
//        sql.append("       B.PLAN_AMOUNT,\n");
//        sql.append("       A.REMARKS,\n");
//        sql.append("       A.PRINT_STATUS\n");
//        sql.append("  FROM PT_BU_STOCK_IN A,PT_BU_PCH_ORDER_SPLIT B\n");
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T1.SPLIT_ID,\n" );
        sql.append("       T1.SPLIT_NO,\n" );
        sql.append("       T1.PURCHASE_TYPE,\n" );
        sql.append("       T1.WAREHOUSE_ID,\n" );
        sql.append("       T1.WAREHOUSE_CODE,\n" );
        sql.append("       T1.WAREHOUSE_NAME,\n" );
        sql.append("       T1.SUPPLIER_ID,\n" );
        sql.append("       T1.SUPPLIER_CODE,\n" );
        sql.append("       T1.SUPPLIER_NAME,\n" );
        sql.append("       T1.CREATE_USER,\n" );
        sql.append("       T1.CREATE_TIME\n" );
        sql.append("  FROM (SELECT A.SPLIT_ID,\n" );
        sql.append("               A.SPLIT_NO,\n" );
        sql.append("               A.PURCHASE_TYPE,\n" );
        sql.append("               CASE\n" );
        sql.append("                 WHEN A.PURCHASE_TYPE = "+DicConstant.CGDDLX_04+" THEN\n" );
        sql.append("                  (SELECT WAREHOUSE_ID\n" );
        sql.append("                     FROM PT_BA_WAREHOUSE\n" );
        sql.append("                    WHERE WAREHOUSE_TYPE = 100102 AND WAREHOUSE_STATUS = 100201)\n" );
        sql.append("                 WHEN A.PURCHASE_TYPE = "+DicConstant.CGDDLX_07+" THEN\n" );
        sql.append("                  (SELECT WAREHOUSE_ID\n" );
        sql.append("                     FROM PT_BA_WAREHOUSE\n" );
        sql.append("                    WHERE WAREHOUSE_TYPE = 100105 AND WAREHOUSE_STATUS = 100201)\n" );
        sql.append("                 ELSE\n" );
        sql.append("                  (SELECT WAREHOUSE_ID\n" );
        sql.append("                     FROM PT_BA_WAREHOUSE\n" );
        sql.append("                    WHERE WAREHOUSE_TYPE = 100101 AND WAREHOUSE_STATUS = 100201)\n" );
        sql.append("               END WAREHOUSE_ID,\n" );
        sql.append("               CASE\n" );
        sql.append("                 WHEN A.PURCHASE_TYPE = "+DicConstant.CGDDLX_04+" THEN\n" );
        sql.append("                  (SELECT WAREHOUSE_CODE\n" );
        sql.append("                     FROM PT_BA_WAREHOUSE\n" );
        sql.append("                    WHERE WAREHOUSE_TYPE = 100102 AND WAREHOUSE_STATUS = 100201)\n" );
        sql.append("                 WHEN A.PURCHASE_TYPE = "+DicConstant.CGDDLX_07+" THEN\n" );
        sql.append("                  (SELECT WAREHOUSE_CODE\n" );
        sql.append("                     FROM PT_BA_WAREHOUSE\n" );
        sql.append("                    WHERE WAREHOUSE_TYPE = 100105 AND WAREHOUSE_STATUS = 100201)\n" );
        sql.append("                 ELSE\n" );
        sql.append("                  (SELECT WAREHOUSE_CODE\n" );
        sql.append("                     FROM PT_BA_WAREHOUSE\n" );
        sql.append("                    WHERE WAREHOUSE_TYPE = 100101 AND WAREHOUSE_STATUS = 100201)\n" );
        sql.append("               END WAREHOUSE_CODE,\n" );
        sql.append("               CASE\n" );
        sql.append("                 WHEN A.PURCHASE_TYPE = "+DicConstant.CGDDLX_04+" THEN\n" );
        sql.append("                  (SELECT WAREHOUSE_NAME\n" );
        sql.append("                     FROM PT_BA_WAREHOUSE\n" );
        sql.append("                    WHERE WAREHOUSE_TYPE = 100102 AND WAREHOUSE_STATUS = 100201)\n" );
        sql.append("                 WHEN A.PURCHASE_TYPE = "+DicConstant.CGDDLX_07+" THEN\n" );
        sql.append("                  (SELECT WAREHOUSE_NAME\n" );
        sql.append("                     FROM PT_BA_WAREHOUSE\n" );
        sql.append("                    WHERE WAREHOUSE_TYPE = 100105 AND WAREHOUSE_STATUS = 100201)\n" );
        sql.append("                 ELSE\n" );
        sql.append("                  (SELECT WAREHOUSE_NAME\n" );
        sql.append("                     FROM PT_BA_WAREHOUSE\n" );
        sql.append("                    WHERE WAREHOUSE_TYPE = 100101 AND WAREHOUSE_STATUS = 100201)\n" );
        sql.append("               END WAREHOUSE_NAME,\n" );
        sql.append("               A.SUPPLIER_ID,\n" );
        sql.append("               A.SUPPLIER_CODE,\n" );
        sql.append("               A.SUPPLIER_NAME,\n" );
        sql.append("               A.CREATE_USER,\n" );
        sql.append("               A.CREATE_TIME\n" );
        sql.append("          FROM PT_BU_PCH_ORDER_SPLIT A\n" );
        sql.append("         WHERE NVL(A.ACCEPT_COUNT, 0) > NVL(A.STORAGE_COUNT, 0)\n" );
        sql.append("           AND A.ORDER_STATUS <>"+DicConstant.CGDDZT_05+" ) T1,\n" );
        sql.append("       (SELECT DISTINCT B.SPLIT_ID, C.WAREHOUSE_ID\n" );
        sql.append("          FROM PT_BU_PCH_ORDER_SPLIT_DTL B, PT_BA_WAREHOUSE_KEEPER C\n" );
        sql.append("         WHERE B.PART_ID = C.PART_ID AND NVL(B.ACCEPT_COUNT, 0)> NVL(B.STORAGE_COUNT, 0)\n" );
        if(!"".equals(partCode)&&partCode!=null){
        	sql.append("         AND B.PART_CODE LIKE '%"+partCode+"%'\n" );
        }
        if(!"".equals(partName)&&partName!=null){
        	sql.append("         AND B.PART_NAME LIKE '%"+partName+"%'\n" );
        }
        if(!"".equals(distributionNo)&&distributionNo!=null){
        	sql.append("         AND B.DISTRIBUTION_NO LIKE '%"+distributionNo+"%'\n" );
        }
        sql.append("           AND C.USER_ACCOUNT = '"+ACCOUNT+"' AND C.STATUS = "+DicConstant.YXBS_01+") T2\n" );
        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("PURCHASE_TYPE", DicConstant.CGDDLX);
        bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
        //bs.setFieldDic("PRINT_STATUS", DicConstant.DYZT);
        bs.setFieldUserID("CREATE_USER");
        return bs;
    }

    /**
     * 查询采购拆分单
     *
     * @param page
     * @param user
     * @param conditions
     * @param warehouseType 仓库类型
     * @return
     * @throws Exception
     */
    public BaseResultSet searchPch(PageManager page, User user, String conditions, String warehouseType) throws Exception {
        String wheres = conditions;
        if ("".equals(warehouseType)) {
            throw new Exception("入库仓库类型不正确!");
        }
        if ("100101".equals(warehouseType)) {//民品库
            wheres += " AND A.ORDER_TYPE IN(" + DicConstant.CGDDLX_01 + "," + DicConstant.CGDDLX_02 + "," + DicConstant.CGDDLX_03 + "," + DicConstant.CGDDLX_06 + ")\n";
        } else {//军品库
            wheres += " AND A.ORDER_TYPE = " + DicConstant.CGDDLX_04 + "\n";
        }
        wheres += " AND A.ORDER_STATUS = " + DicConstant.CGDDZT_04 + "\n";
        wheres += " AND (A.ACCEPT_COUNT - NVL(A.STORAGE_COUNT,0)) > 0\n";//(验收数量-已入库数量)>0 标识未全部入库
        wheres += " AND NOT EXISTS(\n";
        wheres += "     SELECT 1 FROM PT_BU_STOCK_IN B WHERE B.ORDER_ID = A.SPLIT_ID AND B.PRINT_STATUS = " + DicConstant.DYZT_01 + "\n";
        wheres += " )\n";
        wheres += " AND A.STATUS = " + DicConstant.YXBS_01 + "\n";
        wheres += " AND A.OEM_COMPANY_ID=" + user.getOemCompanyId() + "\n";
        wheres += " ORDER BY A.CREATE_TIME DESC\n";
        page.setFilter(wheres);
        //定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT A.SPLIT_ID,\n");
        sql.append("       A.SPLIT_NO,\n");
        sql.append("       A.PURCHASE_ID,\n");//采购订单主键
        sql.append("       A.ORDER_NO,\n");//采购订单编号
        sql.append("       A.PURCHASE_TYPE,\n");
        sql.append("       A.SUPPLIER_ID,\n");
        sql.append("       A.SUPPLIER_CODE,\n");
        sql.append("       A.SUPPLIER_NAME,\n");
        sql.append("       A.APPLY_USER,\n");//采购员
        sql.append("       A.PLAN_AMOUNT,\n");
        sql.append("       A.PURCHASE_AMOUNT,\n");
        sql.append("       A.PURCHASE_COUNT\n");
        sql.append("  FROM PT_BU_PCH_ORDER_SPLIT A\n");
        bs = factory.select(sql.toString(), page);
        bs.setFieldUserID("APPLY_USER");
        bs.setFieldDic("PURCHASE_TYPE", DicConstant.CGLX);
        return bs;
    }

    /**
     * 查询入库单配件
     *
     * @param page
     * @param user
     * @param conditions
     * @param IN_ID        入库单配件
     * @param ORDER_ID     采购拆分单ID
     * @param WAREHOUSE_ID 入库仓库ID
     * @return
     * @throws Exception
     */
    public BaseResultSet searchInBillPart(PageManager page, User user, String conditions, String ORDER_ID, String WAREHOUSE_ID,String account) throws Exception {
        StringBuilder wheres = new StringBuilder(conditions);
        wheres.append("   ORDER BY T.PART_CODE ASC\n");
//        wheres.append("   AND A.SPLIT_ID = " + ORDER_ID + "\n");
//        wheres.append("   AND A.PART_ID = C.PART_ID\n");
//        wheres.append("   AND A.PART_ID = D.PART_ID(+)\n");
//        wheres.append("   AND A.SUPPLIER_ID = D.SUPPLIER_ID(+)\n");
//        wheres.append("   AND A.SPLIT_ID = E.ORDER_ID(+)\n");
//        wheres.append("   AND A.PART_ID = E.PART_ID(+)\n");
//        wheres.append("	  AND A.PART_ID = F.PART_ID\n" );
//        wheres.append("      AND F.POSITION_ID IN (SELECT S.POSITION_ID\n" );
//        wheres.append("         FROM PT_BA_WAREHOUSE_POSITION S,\n" );
//        wheres.append("              PT_BA_WAREHOUSE_AREA     M,\n" );
//        wheres.append("              PT_BA_WAREHOUSE          N\n" );
//        wheres.append("        WHERE S.AREA_ID = M.AREA_ID\n" );
//        wheres.append("          AND M.WAREHOUSE_ID = N.WAREHOUSE_ID AND N.WAREHOUSE_ID = "+WAREHOUSE_ID+")\n");
//        wheres.append("	  AND A.PART_ID = G.PART_ID\n" );
//        wheres.append("	  AND G.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
//        wheres.append("   AND NVL(A.ACCEPT_COUNT,0)-NVL(A.STORAGE_COUNT,0) >0\n");
//        wheres.append("   GROUP BY A.DETAIL_ID,\n" );
//        wheres.append("       A.PART_ID,\n" );
//        wheres.append("       A.PART_CODE,\n" );
//        wheres.append("       A.PART_NAME,\n" );
//        wheres.append("       C.PART_NO,\n" );
//        wheres.append("       C.UNIT,\n" );
//        wheres.append("       A.SUPPLIER_ID,\n" );
//        wheres.append("       A.SUPPLIER_CODE,\n" );
//        wheres.append("       A.SUPPLIER_NAME,\n" );
//        wheres.append("       D.AMOUNT,\n" );
//        wheres.append("       C.MIN_PACK,\n" );
//        wheres.append("       C.MIN_UNIT,\n" );
//        wheres.append("       A.PCH_COUNT,\n" );
//        wheres.append("       A.SHIP_COUNT,\n" );
//        wheres.append("       A.ACCEPT_COUNT,\n" );
//        wheres.append("       E.PRINT_COUNT,\n" );
//        wheres.append("       A.PCH_PRICE,\n" );
//        wheres.append("       A.PCH_AMOUNT,\n" );
//        wheres.append("       C.PLAN_PRICE,\n" );
//        wheres.append("       A.PLAN_AMOUNT,\n" );
//        wheres.append("       A.STORAGE_COUNT");

//        page.setFilter(wheres.toString());
//        //定义返回结果集
//        BaseResultSet bs = null;
//        StringBuilder sql = new StringBuilder();
//        sql.append("SELECT A.DETAIL_ID,\n");
//        sql.append("       A.PART_ID,\n");
//        sql.append("       A.PART_CODE,\n");
//        sql.append("       A.PART_NAME,\n");
//        sql.append("       C.PART_NO,\n");
//        sql.append("       C.UNIT,\n");
//        sql.append("       A.SUPPLIER_ID,\n");
//        sql.append("       A.SUPPLIER_CODE,\n");
//        sql.append("       A.SUPPLIER_NAME,\n");
//        sql.append("       NVL(D.AMOUNT,0) AMOUNT,\n");
//        sql.append("       C.MIN_PACK,\n");
//        sql.append("       C.MIN_UNIT,\n");
//        sql.append("       A.PCH_COUNT,\n");
//        sql.append("       NVL(A.SHIP_COUNT,0) SHIP_COUNT,\n");
//        sql.append("       NVL(A.ACCEPT_COUNT,0) ACCEPT_COUNT,\n");
//        sql.append("       NVL(E.PRINT_COUNT,0) PRINT_COUNT,\n");
//        sql.append("       NVL(A.ACCEPT_COUNT,0)-NVL(A.STORAGE_COUNT,0) WAIT_COUNT,\n");
//        sql.append("       A.PCH_PRICE,\n");
//        sql.append("       A.PCH_AMOUNT,\n");
//        sql.append("       C.PLAN_PRICE,\n");
//        sql.append("       A.PLAN_AMOUNT,\n");
//        sql.append("	   WM_CONCAT (F.POSITION_ID) POSITION_IDS,\n" );
//        sql.append("       WM_CONCAT (F.POSITION_CODE) POSITION_CODESS,\n" );
//        sql.append("       WM_CONCAT (F.POSITION_NAME) POSITION_NAMES,");
//        sql.append("       NVL(A.STORAGE_COUNT,0) STORAGE_COUNT\n");
//        sql.append("  FROM PT_BU_PCH_ORDER_SPLIT_DTL A, PT_BA_INFO C,(SELECT * FROM PT_BU_STOCK WHERE WAREHOUSE_ID = " + WAREHOUSE_ID + ") D,\n");
//        sql.append("(SELECT SUM(T1.IN_AMOUNT) PRINT_COUNT, T.ORDER_ID, T1.PART_ID\n" );
//        sql.append("   FROM PT_BU_STOCK_IN T, PT_BU_STOCK_IN_DTL T1\n" );
//        sql.append("  WHERE 1 = 1\n" );
//        sql.append("    AND T.IN_ID = T1.IN_ID\n" );
//        sql.append("    AND T.PRINT_STATUS = "+DicConstant.DYZT_02+"\n" );
//        sql.append("  GROUP BY T.ORDER_ID, T1.PART_ID) E,");
//        sql.append("  PT_BA_WAREHOUSE_PART_RL F");
//        sql.append(" ,PT_BA_WAREHOUSE_KEEPER G");
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT *\n" );
        sql.append("  FROM (SELECT T1.DETAIL_ID,\n" );
        sql.append("               T1.PART_ID,\n" );
        sql.append("               T1.PART_CODE,\n" );
        sql.append("               T1.PART_NAME,\n" );
        sql.append("               T1.PART_NO,\n" );
        sql.append("               T1.UNIT,\n" );
        sql.append("               T1.SUPPLIER_ID,\n" );
        sql.append("               T1.SUPPLIER_CODE,\n" );
        sql.append("               T1.SUPPLIER_NAME,\n" );
        sql.append("               T1.AMOUNT,\n" );
        sql.append("               T1.MIN_PACK,\n" );
        sql.append("               T1.MIN_UNIT,\n" );
        sql.append("               T1.PCH_COUNT,\n" );
        sql.append("               T1.SHIP_COUNT,\n" );
        sql.append("               T1.ACCEPT_COUNT,\n" );
        sql.append("               T1.PRINT_COUNT,\n" );
        sql.append("               T1.WAIT_COUNT,\n" );
        sql.append("               T1.PCH_PRICE,\n" );
        sql.append("               T1.PCH_AMOUNT,\n" );
        sql.append("               T1.PLAN_PRICE,\n" );
        sql.append("               T1.PLAN_AMOUNT,\n" );
        sql.append("               T1.STORAGE_COUNT,\n" );
        sql.append("               WM_CONCAT(T2.POSITION_ID) POSITION_IDS,\n" );
        sql.append("               WM_CONCAT(T2.POSITION_CODE) POSITION_CODESS,\n" );
        sql.append("               WM_CONCAT(T2.POSITION_NAME) POSITION_NAMES\n" );
        sql.append("          FROM (SELECT A.DETAIL_ID,\n" );
        sql.append("                       A.PART_ID,\n" );
        sql.append("                       A.PART_CODE,\n" );
        sql.append("                       A.PART_NAME,\n" );
        sql.append("                       C.PART_NO,\n" );
        sql.append("                       C.UNIT,\n" );
        sql.append("                       A.SUPPLIER_ID,\n" );
        sql.append("                       A.SUPPLIER_CODE,\n" );
        sql.append("                       A.SUPPLIER_NAME,\n" );
        sql.append("                       NVL(D.AVAILABLE_AMOUNT, 0) AMOUNT,\n" );
        sql.append("                       C.MIN_PACK,\n" );
        sql.append("                       C.MIN_UNIT,\n" );
        sql.append("                       A.PCH_COUNT,\n" );
        sql.append("                       NVL(A.SHIP_COUNT, 0) SHIP_COUNT,\n" );
        sql.append("                       NVL(A.ACCEPT_COUNT, 0) ACCEPT_COUNT,\n" );
        sql.append("                       NVL(E.PRINT_COUNT, 0) PRINT_COUNT,\n" );
        sql.append("                       NVL(A.ACCEPT_COUNT, 0) - NVL(A.STORAGE_COUNT, 0) WAIT_COUNT,\n" );
        sql.append("                       A.PCH_PRICE,\n" );
        sql.append("                       A.PCH_AMOUNT,\n" );
        sql.append("                       C.PLAN_PRICE,\n" );
        sql.append("                       A.PLAN_AMOUNT,\n" );
        sql.append("                       NVL(A.STORAGE_COUNT, 0) STORAGE_COUNT\n" );
        sql.append("                  FROM PT_BU_PCH_ORDER_SPLIT_DTL A,\n" );
        sql.append("                       PT_BA_INFO C,\n" );
        sql.append("                       (SELECT *\n" );
        sql.append("                          FROM PT_BU_STOCK\n" );
        sql.append("                         WHERE WAREHOUSE_ID = "+WAREHOUSE_ID+") D,\n" );
        sql.append("                       (SELECT SUM(T1.IN_AMOUNT) PRINT_COUNT,\n" );
        sql.append("                               T.ORDER_ID,\n" );
        sql.append("                               T1.PART_ID\n" );
        sql.append("                          FROM PT_BU_STOCK_IN T, PT_BU_STOCK_IN_DTL T1\n" );
        sql.append("                         WHERE 1 = 1\n" );
        sql.append("                           AND T.IN_ID = T1.IN_ID\n" );
        sql.append("                           AND T.PRINT_STATUS = "+DicConstant.DYZT_02+"\n" );
        sql.append("                         GROUP BY T.ORDER_ID, T1.PART_ID) E,\n" );
        sql.append("                       PT_BA_WAREHOUSE_KEEPER G\n" );
        sql.append("                 WHERE 1 = 1\n" );
        sql.append("                   AND A.SPLIT_ID = "+ORDER_ID+"\n" );
        sql.append("                   AND A.PART_ID = C.PART_ID\n" );
        sql.append("                   AND A.PART_ID = D.PART_ID(+)\n" );
        sql.append("                   AND A.SUPPLIER_ID = D.SUPPLIER_ID(+)\n" );
        sql.append("                   AND A.SPLIT_ID = E.ORDER_ID(+)\n" );
        sql.append("                   AND A.PART_ID = E.PART_ID(+)\n" );
        sql.append("                   AND A.PART_ID = G.PART_ID\n" );
        sql.append("                   AND G.USER_ACCOUNT = '"+account+"' AND G.STATUS = "+DicConstant.YXBS_01+"\n" );
        sql.append("                   AND NVL(A.ACCEPT_COUNT, 0) - NVL(A.STORAGE_COUNT, 0) > 0\n" );
        sql.append("                 GROUP BY A.DETAIL_ID,\n" );
        sql.append("                          A.PART_ID,\n" );
        sql.append("                          A.PART_CODE,\n" );
        sql.append("                          A.PART_NAME,\n" );
        sql.append("                          C.PART_NO,\n" );
        sql.append("                          C.UNIT,\n" );
        sql.append("                          A.SUPPLIER_ID,\n" );
        sql.append("                          A.SUPPLIER_CODE,\n" );
        sql.append("                          A.SUPPLIER_NAME,\n" );
        sql.append("                          D.AVAILABLE_AMOUNT,\n" );
        sql.append("                          C.MIN_PACK,\n" );
        sql.append("                          C.MIN_UNIT,\n" );
        sql.append("                          A.PCH_COUNT,\n" );
        sql.append("                          A.SHIP_COUNT,\n" );
        sql.append("                          A.ACCEPT_COUNT,\n" );
        sql.append("                          E.PRINT_COUNT,\n" );
        sql.append("                          A.PCH_PRICE,\n" );
        sql.append("                          A.PCH_AMOUNT,\n" );
        sql.append("                          C.PLAN_PRICE,\n" );
        sql.append("                          A.PLAN_AMOUNT,\n" );
        sql.append("                          A.STORAGE_COUNT) T1\n" );
        sql.append("          LEFT JOIN PT_BA_WAREHOUSE_PART_RL T2\n" );
        sql.append("            ON (T1.PART_ID = T2.PART_ID AND T2.STATUS='"+DicConstant.YXBS_01+"'\n" );
        sql.append("           AND T2.POSITION_ID IN\n" );
        sql.append("               (SELECT S.POSITION_ID\n" );
        sql.append("                  FROM PT_BA_WAREHOUSE_POSITION S,\n" );
        sql.append("                       PT_BA_WAREHOUSE_AREA     M,\n" );
        sql.append("                       PT_BA_WAREHOUSE          N\n" );
        sql.append("                 WHERE S.AREA_ID = M.AREA_ID\n" );
        sql.append("                   AND M.WAREHOUSE_ID = N.WAREHOUSE_ID\n" );
        sql.append("                   AND N.WAREHOUSE_ID = "+WAREHOUSE_ID+"))\n" );
        sql.append("         GROUP BY T1.DETAIL_ID,\n" );
        sql.append("                  T1.PART_ID,\n" );
        sql.append("                  T1.PART_CODE,\n" );
        sql.append("                  T1.PART_NAME,\n" );
        sql.append("                  T1.PART_NO,\n" );
        sql.append("                  T1.UNIT,\n" );
        sql.append("                  T1.SUPPLIER_ID,\n" );
        sql.append("                  T1.SUPPLIER_CODE,\n" );
        sql.append("                  T1.SUPPLIER_NAME,\n" );
        sql.append("                  T1.AMOUNT,\n" );
        sql.append("                  T1.MIN_PACK,\n" );
        sql.append("                  T1.MIN_UNIT,\n" );
        sql.append("                  T1.PCH_COUNT,\n" );
        sql.append("                  T1.SHIP_COUNT,\n" );
        sql.append("                  T1.ACCEPT_COUNT,\n" );
        sql.append("                  T1.PRINT_COUNT,\n" );
        sql.append("                  T1.WAIT_COUNT,\n" );
        sql.append("                  T1.PCH_PRICE,\n" );
        sql.append("                  T1.PCH_AMOUNT,\n" );
        sql.append("                  T1.PLAN_PRICE,\n" );
        sql.append("                  T1.PLAN_AMOUNT,\n" );
        sql.append("                  T1.STORAGE_COUNT) T");
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("UNIT", DicConstant.JLDW);
        bs.setFieldDic("MIN_UNIT", DicConstant.JLDW);
        return bs;
    }

    /**
     * 新增入库单
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean insertInBill(PtBuStockInVO vo)
            throws Exception {
        return factory.insert(vo);
    }

    /**
     * 新增入库单明细
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean insertInBillDtl(PtBuStockInDtlVO vo)
            throws Exception {
        return factory.insert(vo);
    }

    /**
     * 新增库存
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean insertStock(PtBuStockVO vo)
            throws Exception {
        return factory.insert(vo);
    }

    /**
     * 新增库存明细
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean insertStockDtl(PtBuStockDtlVO vo)
            throws Exception {
        return factory.insert(vo);
    }

    /**
     * 更新入库单明细的采购单价/采购金额/计划单价/计划金额/单位
     *
     * @param IN_ID   入库单ID
     * @param IN_TYPE 入库类型
     * @throws Exception
     */
    public void updateInBillDtl(String IN_ID, String IN_TYPE) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE PT_BU_STOCK_IN_DTL A\n");
        sql.append("   SET \n");
        if (DicConstant.RKLX_01.equals(IN_TYPE)) {//只有采购入库更新采购单价和采购金额
            sql.append("       A.PCH_PRICE  =\n");
            sql.append("       (SELECT D.PCH_PRICE\n");
            sql.append("          FROM PT_BU_STOCK_IN            B,\n");
            sql.append("               PT_BU_PCH_ORDER_SPLIT_DTL C,\n");
            sql.append("               PT_BU_PCH_ORDER_SPLIT 	 E,\n");
            sql.append("               PT_BA_PART_SUPPLIER_RL    D\n");
            sql.append("         WHERE B.IN_ID = " + IN_ID + "\n");
            sql.append("           AND B.ORDER_ID = C.SPLIT_ID\n");
            sql.append("           AND C.SPLIT_ID = E.SPLIT_ID\n");
            sql.append("           AND E.SUPPLIER_ID = D.SUPPLIER_ID AND D.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n");
            sql.append("           AND C.PART_ID = D.PART_ID\n");
            sql.append("           AND C.PART_ID = A.PART_ID),\n");
            sql.append("       A.PCH_AMOUNT =\n");
            sql.append("       (SELECT D.PCH_PRICE * A.IN_AMOUNT\n");
            sql.append("          FROM PT_BU_STOCK_IN            B,\n");
            sql.append("               PT_BU_PCH_ORDER_SPLIT_DTL C,\n");
            sql.append("               PT_BU_PCH_ORDER_SPLIT 	 E,\n");
            sql.append("               PT_BA_PART_SUPPLIER_RL    D\n");
            sql.append("         WHERE B.IN_ID = " + IN_ID + "\n");
            sql.append("           AND B.ORDER_ID = C.SPLIT_ID\n");
            sql.append("           AND C.SPLIT_ID = E.SPLIT_ID\n");
            sql.append("           AND E.SUPPLIER_ID = D.SUPPLIER_ID AND D.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n");
            sql.append("           AND C.PART_ID = D.PART_ID\n");
            sql.append("           AND C.PART_ID = A.PART_ID),\n");
        }
        sql.append("       A.PLAN_PRICE =\n");
        sql.append("       (SELECT E.PLAN_PRICE FROM PT_BA_INFO E WHERE E.PART_ID = A.PART_ID),\n");
        sql.append("       A.PLAN_AMOUNT =\n");
        sql.append("       (SELECT E.PLAN_PRICE * A.IN_AMOUNT\n");
        sql.append("          FROM PT_BA_INFO E\n");
        sql.append("         WHERE E.PART_ID = A.PART_ID),\n");
        sql.append("       A.UNIT       =\n");
        sql.append("       (SELECT E.UNIT FROM PT_BA_INFO E WHERE E.PART_ID = A.PART_ID)\n");
        sql.append(" WHERE A.IN_ID = " + IN_ID + "\n");

        factory.update(sql.toString(), null);
    }
    /**
     * 更新入库流水的计划价
     *
     * @param IN_ID 入库单ID
     * @throws Exception
     */
    public void updateInFlow(String IN_ID) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE PT_BU_STOCK_IN_CONTINUAL A\n");
        sql.append("   SET A.PLAN_PRICE =\n");
        sql.append("       (SELECT 1 FROM PT_BA_INFO B WHERE B.PART_ID = A.PART_ID)\n");
        sql.append(" WHERE A.IN_ID = " + IN_ID + "\n");
        factory.update(sql.toString(), null);
    }
    /**
     * 修改入库单
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean updateInBill(PtBuStockInVO vo)
            throws Exception {
        return factory.update(vo);
    }
    public QuerySet selectStockInInfo(String splitId)
            throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT IN_ID, IN_NO\n" );
    	sql.append("  FROM PT_BU_STOCK_IN\n" );
    	sql.append(" WHERE ORDER_ID = "+splitId+"\n" );
    	sql.append("   AND PRINT_STATUS = "+DicConstant.DYZT_01+"\n");
        return factory.select(null, sql.toString());
    }
    public QuerySet selectStockInDetailInfo(String inId,String partId,String supplierId)
            throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT DETAIL_ID\n" );
    	sql.append("  FROM PT_BU_STOCK_IN_DTL\n" );
    	sql.append(" WHERE IN_ID = "+inId+"\n" );
    	sql.append("   AND PART_ID = "+partId+"\n" );
    	sql.append("   AND SUPPLIER_ID = "+supplierId+"\n");
        return factory.select(null, sql.toString());
    }
    /**
     * 修改入库单明细
     *
     * @param map
     * @param user
     * @return
     * @throws Exception
     */
    public boolean updateInBillDtl(Map<String, String> map, User user)
            throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE PT_BU_STOCK_IN_DTL A\n");
        sql.append("   SET A.IN_AMOUNT = A.IN_AMOUNT + " + map.get("CUR_IN_AMOUNT") + ", A.REMARKS = '" + map.get("REMARK") + "'\n");
        sql.append(" WHERE A.IN_ID = " + map.get("IN_ID") + "\n");
        sql.append("   AND A.PART_ID = " + map.get("PART_ID") + "\n");
        sql.append("   AND A.SUPPLIER_ID = " + map.get("SUPPLIER_ID") + "\n");

        return factory.update(sql.toString(), null);
    }

    /**
     * 修改库存
     *
     * @param stockId     库存ID
     * @param curInAmount 本次入库数量
     * @param user
     * @return
     * @throws Exception
     */
    public boolean updateStock(String stockId, String curInAmount, User user)
            throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE PT_BU_STOCK A\n");
        sql.append("   SET A.AMOUNT = A.AMOUNT + " + curInAmount + ",\n");
        sql.append("       A.AVAILABLE_AMOUNT = A.AVAILABLE_AMOUNT + " + curInAmount + ",\n");
        sql.append("       A.UPDATE_USER = '" + user.getAccount() + "',\n");
        sql.append("       A.UPDATE_TIME = SYSDATE\n");
        sql.append(" WHERE A.STOCK_ID = " + stockId + "\n");

        return factory.update(sql.toString(), null);
    }

    /**
     * 修改库存明细
     *
     * @param stockDtlId  库存明细ID
     * @param curInAmount 当前入库数量
     * @param user
     * @return
     * @throws Exception
     */
    public boolean updateStockDtl(String stockDtlId, String curInAmount, User user)
            throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE PT_BU_STOCK_DTL A\n");
        sql.append("   SET A.AMOUNT = A.AMOUNT + " + curInAmount + ",A.AVAILABLE_AMOUNT = A.AVAILABLE_AMOUNT + " + curInAmount + ",UPDATE_USER = '" + user.getAccount() + "',UPDATE_TIME = SYSDATE\n");
        sql.append(" WHERE A.DTL_ID = " + stockDtlId + "\n");

        return factory.update(sql.toString(), null);
    }

    /**
     * 校验库存是否存在
     *
     * @param WAREHOUSE_ID 仓库ID
     * @param PART_ID      配件ID
     * @param SUPPLIER_ID  供应商ID
     * @param user
     * @return
     * @throws Exception
     */
    public String checkStockIsExist(String WAREHOUSE_ID, String PART_ID, String SUPPLIER_ID, User user) throws Exception {
        String stockId = "";
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT A.STOCK_ID\n");
        sql.append("  FROM PT_BU_STOCK A\n");
        sql.append(" WHERE A.WAREHOUSE_ID = " + WAREHOUSE_ID + "\n");
        sql.append("   AND A.PART_ID = " + PART_ID + "\n");
        sql.append("   AND A.SUPPLIER_ID = " + SUPPLIER_ID + "\n");
        sql.append("   AND A.STATUS = " + DicConstant.YXBS_01 + "\n");
        sql.append("   AND A.OEM_COMPANY_ID = " + user.getOemCompanyId() + "\n");

        QuerySet qs = factory.select(null, sql.toString());
        if (qs.getRowCount() > 0) {
            stockId = qs.getString(1, "STOCK_ID");
        }
        return stockId;
    }

    /**
     * 校验库存明细(库位)是否存在
     *
     * @param POSITION_ID 库位ID
     * @param PART_ID     配件ID
     * @param SUPPLIER_ID 供应商ID
     * @param user
     * @return
     * @throws Exception
     */
    public String checkStockDtlIsExist(String POSITION_ID, String PART_ID, String SUPPLIER_ID, User user) throws Exception {
        String stockDtlId = "";
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT A.DTL_ID\n");
        sql.append("  FROM PT_BU_STOCK_DTL A\n");
        sql.append(" WHERE A.POSITION_ID = " + POSITION_ID + "\n");
        sql.append("   AND A.PART_ID = " + PART_ID + "\n");
        sql.append("   AND A.SUPPLIER_ID = " + SUPPLIER_ID + "\n");

        QuerySet qs = factory.select(null, sql.toString());
        if (qs.getRowCount() > 0) {
            stockDtlId = qs.getString(1, "DTL_ID");
        }
        return stockDtlId;
    }

    /**
     * 获取主键ID
     *
     * @return
     * @throws Exception
     */
    public String getId() throws Exception {
        return SequenceUtil.getCommonSerivalNumber(factory);
    }

    /**
     * 删除入库单
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean deleteInBill(PtBuStockInVO vo) throws Exception {
        return factory.delete(vo);
    }

    /**
     * 删除入库单明细
     *
     * @param inId 入库单ID
     * @return
     * @throws Exception
     */
    public boolean deleteInBillDtl(String inId) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM PT_BU_STOCK_IN_DTL WHERE IN_ID = " + inId + "\n");
        return factory.delete(sql.toString(), null);
    }

    /**
     * 校验入库单明细是否存在
     *
     * @param IN_ID 入库单ID
     * @return
     * @throws Exception
     */
    public Boolean checkInBillDtlIsExist(String IN_ID) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT 1\n");
        sql.append("  FROM PT_BU_STOCK_IN_DTL A\n");
        sql.append(" WHERE A.IN_ID = " + IN_ID + "\n");

        QuerySet qs = factory.select(null, sql.toString());
        if (qs.getRowCount() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 修改采购拆分单明细的已入库数量
     *
     * @param map
     * @param user
     * @return
     * @throws Exception
     */
    public boolean updateSplitDtl(Map<String, String> map, User user)
            throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE PT_BU_PCH_ORDER_SPLIT_DTL A\n");
        sql.append("   SET A.STORAGE_COUNT = NVL(A.STORAGE_COUNT,0) + " + map.get("CUR_IN_AMOUNT") + ", A.UPDATE_USER = '" + user.getAccount() + "',A.UPDATE_TIME = SYSDATE\n");
        sql.append(" WHERE A.DETAIL_ID = " + map.get("DETAIL_ID") + "\n");

        return factory.update(sql.toString(), null);
    }

    /**
     * 修改采购拆分单的已入库数量
     *
     * @param splitId      采购拆分单ID
     * @param totalInCount 本次入库数量之和
     * @param user
     * @return
     * @throws Exception
     */
    public boolean updateSplit(String splitId, int totalInCount, User user)
            throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE PT_BU_PCH_ORDER_SPLIT A\n");
        sql.append("   SET A.STORAGE_COUNT = NVL(A.STORAGE_COUNT,0) + " + totalInCount + ", A.UPDATE_USER = '" + user.getAccount() + "',A.UPDATE_TIME = SYSDATE\n");
        sql.append(" WHERE A.SPLIT_ID = " + splitId + "\n");

        return factory.update(sql.toString(), null);
    }
    
    public void updateSplitStatus(String splitId, User user)
            throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT 1 FROM PT_BU_PCH_ORDER_SPLIT WHERE SPLIT_ID ="+splitId+" AND NVL(PURCHASE_COUNT,0)= NVL(STORAGE_COUNT,0)");
        QuerySet qs = factory.select(null, sql.toString());
        if(qs.getRowCount()>0){
        	String sql1 = "UPDATE PT_BU_PCH_ORDER_SPLIT SET ORDER_STATUS = "+DicConstant.CGDDZT_05+",CLOSE_DATE = SYSDATE WHERE SPLIT_ID ="+splitId+"\n";
        	factory.update(sql1, null);
        }
    }

    /**
     * 获取订单审核角色
     * @return
     * @throws Exception
     */
    public QuerySet getRole()throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT P.ROLE_ID,M.LOCATION \n" );
    	sql.append("  FROM EAP_MENU M, TR_ROLE_MENU_MAP P\n" );
    	sql.append(" WHERE M.TITLE = '三包急件订单审核'\n" );
    	sql.append("   AND P.MENU_NAME = M.NAME");
    	return factory.select(null, sql.toString());
    }

    /**
     * 新增入库流水
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean insertContinual(PtBuStockInContinualVO vo)
            throws Exception {
        return factory.insert(vo);
    }


    /**
     * 生成入库单号
     *
     * @param splitId
     * @param splitNo
     * @return
     * @throws Exception
     */
    public String createInBillNo(String splitId, String splitNo) throws Exception {
        return PartOddNumberUtil.getPchInBillNo(factory, splitId, splitNo);
    }
    
    public QuerySet checkPlanPrice(String partIds)
            throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT to_char(wm_concat( PART_CODE)) CODES FROM PT_BA_INFO WHERE PART_ID IN ("+partIds+") AND NVL(PLAN_PRICE,0)=0\n" );
        return factory.select(null, sql.toString());
    }
    public QuerySet getInConNo() throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT INSTORE.NEXTVAL NO FROM DUAL");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
    public QuerySet checkLock1(String partIds,String warehouseId)
            throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT to_char(wm_concat( PART_CODE)) CODES FROM PT_BU_STOCK WHERE PART_ID IN ("+partIds+") AND STOCK_STATUS = "+DicConstant.KCZT_02+" AND WAREHOUSE_ID = "+warehouseId+"\n" );
        return factory.select(null, sql.toString());
    }
    public QuerySet checkLock2(String partIds,String warehouseId)
            throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT to_char(wm_concat( PART_CODE)) CODES FROM PT_BU_STOCK WHERE PART_ID IN ("+partIds+") AND STOCK_STATUS = "+DicConstant.KCZT_03+" AND WAREHOUSE_ID = "+warehouseId+"\n" );
        return factory.select(null, sql.toString());
    }
}