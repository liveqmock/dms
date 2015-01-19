package com.org.dms.dao.part.storageMng.stockInMng;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.common.PartOddNumberUtil;
import com.org.dms.vo.part.*;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.*;
import com.org.mvc.context.ActionContext;

import java.util.Map;

/**
 * 销售退件入库Dao
 *
 * @user : lichuang
 * @date : 2014-08-06
 */
public class SaleRetStockInMngDao extends BaseDAO {
    //定义instance
    public static final SaleRetStockInMngDao getInstance(ActionContext atx) {
        SaleRetStockInMngDao dao = new SaleRetStockInMngDao();
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
    public BaseResultSet searchInBill(PageManager page, User user, String conditions,String ACCOUNT) throws Exception {

        StringBuilder wheres = new StringBuilder(conditions);
        wheres.append("   AND EXISTS (SELECT 1\n" );
        wheres.append("          FROM TM_ORG B\n" );
        wheres.append("         WHERE B.ORG_ID = A.APPLY_ORG_ID\n" );
        wheres.append("           AND B.ORG_TYPE = '" + DicConstant.ZZLB_09 + "')\n" );
        wheres.append("   AND A.RETURN_ID IN\n" );
        wheres.append("       (SELECT RETURN_ID\n" );
        wheres.append("          FROM PT_BU_RETURN_APPLY_DTL C, PT_BA_WAREHOUSE_KEEPER D\n" );
        wheres.append("         WHERE C.PART_ID = D.PART_ID\n" );
        wheres.append("           AND D.USER_ACCOUNT = '" + user.getAccount() + "')");
        wheres.append("   AND A.STATUS = " + DicConstant.YXBS_01 + "\n");
        wheres.append("   AND A.APPLY_SATUS='" + DicConstant.TJSQDZT_03 + "' ");
        wheres.append("   AND NVL((SELECT SUM(IN_AMOUNT) B FROM PT_BU_STOCK_IN_DTL WHERE IN_NO=A.RETURN_NO AND PART_ID IN (SELECT PART_ID FROM PT_BA_WAREHOUSE_KEEPER WHERE USER_ACCOUNT = '" + ACCOUNT + "' )),0) <>\n");
        wheres.append("   (SELECT SUM(RETURN_COUNT) C FROM PT_BU_RETURN_APPLY_DTL WHERE RETURN_ID=A.RETURN_ID AND PART_ID IN (SELECT PART_ID FROM PT_BA_WAREHOUSE_KEEPER WHERE USER_ACCOUNT = '" + ACCOUNT + "' )) \n");
        wheres.append("   ORDER BY A.RETURN_NO DESC\n");
        page.setFilter(wheres.toString());

        //定义返回结果集
        BaseResultSet bs = null;
        
        StringBuilder sql= new StringBuilder();
        sql.append("SELECT A.RETURN_ID,\n" );
        sql.append("       A.RETURN_NO,\n" );
        sql.append("       A.APPLY_ORG_ID,\n" );
        sql.append("       A.APPLY_ORG_CODE,\n" );
        sql.append("       A.APPLY_ORG_NAME,\n" );
        sql.append("       '" + DicConstant.RKLX_03 + "' AS IN_TYPE,\n" );
        sql.append("       A.RECEIVE_ORG_ID,\n" );
        sql.append("       A.RECEIVE_ORG_CODE,\n" );
        sql.append("       A.RECEIVE_ORG_NAME,\n" );
        sql.append("       A.CHECK_USER,\n" );
        sql.append("       A.CHECK_DATE,\n" );
        // 入库数量完成率
        sql.append("NVL(ROUND(((SELECT SUM(IN_AMOUNT)\n" );
        sql.append("          FROM PT_BU_STOCK_IN_DTL\n" );
        sql.append("         WHERE IN_NO  = A.RETURN_NO) /\n" );
        sql.append("       (SELECT SUM(RETURN_COUNT)\n" );
        sql.append("          FROM PT_BU_RETURN_APPLY_DTL\n" );
        sql.append("         WHERE RETURN_ID = A.RETURN_ID) * 100 ),2),0) AS STORAGE_RATE,");

        // 入库品种完成率
        sql.append("NVL(ROUND(((SELECT SUM(CASE\n" );
        sql.append("                                WHEN T1.IN_AMOUNT = T2.RETURN_COUNT THEN\n" );
        sql.append("                                 1\n" );
        sql.append("                                ELSE\n" );
        sql.append("                                 0\n" );
        sql.append("                              END)\n" );
        sql.append("                     FROM PT_BU_STOCK_IN_DTL T1, PT_BU_RETURN_APPLY_DTL T2\n" );
        sql.append("                    WHERE T2.PART_ID = T1.PART_ID\n" );
        sql.append("                      AND T1.IN_NO = A.RETURN_NO AND T2.RETURN_ID = A.RETURN_ID) /\n" );
        sql.append("                 (SELECT COUNT(RETURN_COUNT)\n" );
        sql.append("                     FROM PT_BU_RETURN_APPLY_DTL\n" );
        sql.append("                    WHERE RETURN_ID = A.RETURN_ID) * 100),\n" );
        sql.append("                 2),\n" );
        sql.append("           0) AS STORAGE_RATE1");

        sql.append("  FROM PT_BU_RETURN_APPLY A\n" );
        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        // 入库类型
        bs.setFieldDic("IN_TYPE", DicConstant.RKLX);
        // 审核人
		bs.setFieldUserID("CHECK_USER");
        // 审核日期
        bs.setFieldDateFormat("CHECK_DATE", "yyyy-MM-dd");
        return bs;
    }

    /**
     * 查询销售退件单
     *
     * @param page
     * @param user
     * @param conditions
     * @param WAREHOUSE_ID 仓库ID
     * @return
     * @throws Exception
     */
    public BaseResultSet searchRet(PageManager page, User user, String conditions, String WAREHOUSE_ID) throws Exception {
        String wheres = conditions;
        wheres += " AND A.RECEIVE_ORG_ID = " + WAREHOUSE_ID + "\n";
        wheres += " AND NOT EXISTS(\n";
        wheres += "     SELECT 1 FROM PT_BU_STOCK_IN B WHERE B.ORDER_ID = A.RETURN_ID\n";
        wheres += " )\n";
        wheres += " AND EXISTS(\n";
        wheres += "     SELECT 1 FROM TM_ORG C WHERE C.ORG_ID = A.APPLY_ORG_ID AND C.ORG_TYPE = "+DicConstant.ZZLB_09+" \n";
        wheres += " )\n";
        wheres += " AND A.APPLY_SATUS = " + DicConstant.TJSQDZT_06 + "\n";
        wheres += " AND A.STATUS = " + DicConstant.YXBS_01 + "\n";
        wheres += " AND A.OEM_COMPANY_ID=" + user.getOemCompanyId() + "\n";
        wheres += " ORDER BY A.CREATE_TIME DESC\n";
        page.setFilter(wheres);
        //定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT A.RETURN_ID,\n");
        sql.append("       A.RETURN_NO,\n");
        sql.append("       A.APPLY_ORG_ID,\n");
        sql.append("       A.APPLY_ORG_CODE,\n");
        sql.append("       A.APPLY_ORG_NAME,\n");
        sql.append("       A.RETURN_COUNT,\n");
        sql.append("       A.RETURN_AMOUNT,\n");
        sql.append("       A.CHECK_USER\n");
        sql.append("  FROM PT_BU_RETURN_APPLY A\n");
        bs = factory.select(sql.toString(), page);
        bs.setFieldUserID("CHECK_USER");
        return bs;
    }

    /**
     * 查询入库单配件
     *
     * @param page
     * @param user
     * @param conditions
     * @param IN_ID        入库单ID
     * @param ORDER_ID     销售退件单ID
     * @param WAREHOUSE_ID 仓库ID
     * @return
     * @throws Exception
     */
    public BaseResultSet searchInBillPart(PageManager page, User user, String conditions, String IN_ID, String returnId, String WAREHOUSE_ID) throws Exception {
        StringBuilder wheres = new StringBuilder(conditions);
//        wheres.append("   AND NVL(A.RETURN_COUNT,0) <> NVL(B.IN_AMOUNT,0)\n" );
//        wheres.append("   AND A.RETURN_ID = '" + returnId + "'\n" );
//        wheres.append("   AND A.PART_ID = B.PART_ID(+)\n" );
//        wheres.append("   AND A.SUPPLIER_ID = B.SUPPLIER_ID(+)\n" );
//        wheres.append("   AND A.PART_ID = C.PART_ID\n" );
//        wheres.append("   AND A.PART_ID = D.PART_ID(+)\n" );
//        wheres.append("   AND A.SUPPLIER_ID = D.SUPPLIER_ID(+)\n" );
//        wheres.append("   AND A.PART_ID = E.PART_ID(+)\n" );
//        wheres.append("   AND A.PART_ID = G.PART_ID\n" );
//        wheres.append("   AND A.PART_ID = F.PART_ID(+)\n" );
////        wheres.append("   AND F.STATUS='"+DicConstant.YXBS_01+"'\n" );
//        wheres.append("   AND F.POSITION_ID IN\n" );
//        wheres.append("       (SELECT S.POSITION_ID\n" );
//        wheres.append("          FROM PT_BA_WAREHOUSE_POSITION S,\n" );
//        wheres.append("               PT_BA_WAREHOUSE_AREA     M,\n" );
//        wheres.append("               PT_BA_WAREHOUSE          N\n" );
//        wheres.append("         WHERE S.AREA_ID = M.AREA_ID\n" );
//        wheres.append("           AND M.WAREHOUSE_ID = N.WAREHOUSE_ID\n" );
//        wheres.append("           AND N.WAREHOUSE_ID = '" + WAREHOUSE_ID + "')\n" );
//        wheres.append(" GROUP BY A.DTL_ID,\n" );
//        wheres.append("       A.PART_ID,\n" );
//        wheres.append("       A.PART_CODE,\n" );
//        wheres.append("       A.PART_NAME,\n" );
//        wheres.append("       A.SUPPLIER_ID,\n" );
//        wheres.append("       A.SUPPLIER_CODE,\n" );
//        wheres.append("       A.SUPPLIER_NAME,\n" );
//        wheres.append("       C.PART_NO,\n" );
//        wheres.append("       C.UNIT,\n" );
//        wheres.append("       C.MIN_PACK,\n" );
//        wheres.append("       C.MIN_UNIT,\n" );
//        wheres.append("       A.RETURN_COUNT,\n" );
//        wheres.append("          D.AMOUNT,\n" );
//        wheres.append("          B.IN_AMOUNT,\n" );
//        wheres.append("          B.REMARKS\n" );
//        wheres.append(" ORDER BY A.PART_CODE ASC\n" );
        page.setFilter(wheres.toString());
        //定义返回结果集
        BaseResultSet bs = null;
        
        StringBuilder sql= new StringBuilder();
        sql.append("SELECT *\n" );
        sql.append("  FROM (SELECT T1.DTL_ID,\n" );
        sql.append("               T1.PART_ID,\n" );
        sql.append("               T1.PART_CODE,\n" );
        sql.append("               T1.PART_NAME,\n" );
        sql.append("               T1.SUPPLIER_ID,\n" );
        sql.append("               T1.SUPPLIER_CODE,\n" );
        sql.append("               T1.SUPPLIER_NAME,\n" );
        sql.append("               T1.PART_NO,\n" );
        sql.append("               T1.UNIT,\n" );
        sql.append("               T1.MIN_PACK,\n" );
        sql.append("               T1.MIN_UNIT,\n" );
        sql.append("               T1.RETURN_COUNT,\n" );
        sql.append("               T1.AMOUNT,\n" );
        sql.append("               T1.IN_AMOUNT,\n" );
        sql.append("               WM_CONCAT(T2.POSITION_ID) POSITION_IDS,\n" );
        sql.append("               WM_CONCAT(T2.POSITION_CODE) POSITION_CODESS,\n" );
        sql.append("               WM_CONCAT(T2.POSITION_NAME) POSITION_NAMES,\n" );
        sql.append("               T1.REMARKS\n" );
        sql.append("          FROM (SELECT A.DTL_ID,\n" );
        sql.append("                       A.PART_ID,\n" );
        sql.append("                       A.PART_CODE,\n" );
        sql.append("                       A.PART_NAME,\n" );
        sql.append("                       A.SUPPLIER_ID,\n" );
        sql.append("                       A.SUPPLIER_CODE,\n" );
        sql.append("                       A.SUPPLIER_NAME,\n" );
        sql.append("                       C.PART_NO,\n" );
        sql.append("                       C.UNIT,\n" );
        sql.append("                       C.MIN_PACK,\n" );
        sql.append("                       C.MIN_UNIT,\n" );
        sql.append("                       A.RETURN_COUNT,\n" );
        sql.append("                       NVL(D.AMOUNT, 0) AMOUNT,\n" );
        sql.append("                       NVL(B.IN_AMOUNT, 0) IN_AMOUNT,\n" );
        sql.append("                       B.REMARKS\n" );
        sql.append("                  FROM PT_BU_RETURN_APPLY_DTL A,\n" );
        sql.append("                       (SELECT *\n" );
        sql.append("                          FROM PT_BU_STOCK_IN_DTL\n" );
        sql.append("                         WHERE IN_ID IN\n" );
        sql.append("                               (SELECT IN_ID\n" );
        sql.append("                                  FROM PT_BU_STOCK_IN\n" );
        sql.append("                                 WHERE ORDER_ID = '" + returnId + "')) B,\n" );
        sql.append("                       PT_BA_INFO C,\n" );
        sql.append("                       (SELECT *\n" );
        sql.append("                          FROM PT_BU_STOCK\n" );
        sql.append("                         WHERE WAREHOUSE_ID = '" + WAREHOUSE_ID + "') D,\n" );
        sql.append("                       (SELECT SUM(T1.IN_AMOUNT) PRINT_COUNT,\n" );
        sql.append("                               T.ORDER_ID,\n" );
        sql.append("                               T1.PART_ID\n" );
        sql.append("                          FROM PT_BU_STOCK_IN T, PT_BU_STOCK_IN_DTL T1\n" );
        sql.append("                         WHERE 1 = 1\n" );
        sql.append("                           AND T.IN_ID = T1.IN_ID\n" );
        sql.append("                           AND T.PRINT_STATUS = '" + DicConstant.DYZT_02 + "'\n" );
        sql.append("                         GROUP BY T.ORDER_ID, T1.PART_ID) E,\n" );
        sql.append("                       (SELECT *\n" );
        sql.append("                          FROM PT_BA_WAREHOUSE_KEEPER\n" );
        sql.append("                         WHERE USER_ACCOUNT = '"+user.getAccount()+"') G\n" );
        sql.append("                 WHERE NVL(A.RETURN_COUNT, 0) <> NVL(B.IN_AMOUNT, 0)\n" );
        sql.append("                   AND A.RETURN_ID = '" + returnId + "'\n" );
        sql.append("                   AND A.PART_ID = B.PART_ID(+)\n" );
        sql.append("                   AND A.SUPPLIER_ID = B.SUPPLIER_ID(+)\n" );
        sql.append("                   AND A.PART_ID = C.PART_ID\n" );
        sql.append("                   AND A.PART_ID = D.PART_ID(+)\n" );
        sql.append("                   AND A.SUPPLIER_ID = D.SUPPLIER_ID(+)\n" );
        sql.append("                   AND A.PART_ID = E.PART_ID(+)\n" );
        sql.append("                   AND A.PART_ID = G.PART_ID\n" );
        sql.append("                 GROUP BY A.DTL_ID,\n" );
        sql.append("                          A.PART_ID,\n" );
        sql.append("                          A.PART_CODE,\n" );
        sql.append("                          A.PART_NAME,\n" );
        sql.append("                          A.SUPPLIER_ID,\n" );
        sql.append("                          A.SUPPLIER_CODE,\n" );
        sql.append("                          A.SUPPLIER_NAME,\n" );
        sql.append("                          C.PART_NO,\n" );
        sql.append("                          C.UNIT,\n" );
        sql.append("                          C.MIN_PACK,\n" );
        sql.append("                          C.MIN_UNIT,\n" );
        sql.append("                          A.RETURN_COUNT,\n" );
        sql.append("                          D.AMOUNT,\n" );
        sql.append("                          B.IN_AMOUNT,\n" );
        sql.append("                          B.REMARKS) T1\n" );
        sql.append("          LEFT JOIN PT_BA_WAREHOUSE_PART_RL T2\n" );
        sql.append("            ON (T1.PART_ID = T2.PART_ID AND T2.STATUS = '"+DicConstant.YXBS_01+"' AND\n" );
        sql.append("               T2.POSITION_ID IN\n" );
        sql.append("               (SELECT S.POSITION_ID\n" );
        sql.append("                   FROM PT_BA_WAREHOUSE_POSITION S,\n" );
        sql.append("                        PT_BA_WAREHOUSE_AREA     M,\n" );
        sql.append("                        PT_BA_WAREHOUSE          N\n" );
        sql.append("                  WHERE S.AREA_ID = M.AREA_ID\n" );
        sql.append("                    AND M.WAREHOUSE_ID = N.WAREHOUSE_ID\n" );
        sql.append("                    AND N.WAREHOUSE_ID = '" + WAREHOUSE_ID + "'))\n" );
        sql.append("         GROUP BY T1.DTL_ID,\n" );
        sql.append("                  T1.PART_ID,\n" );
        sql.append("                  T1.PART_CODE,\n" );
        sql.append("                  T1.PART_NAME,\n" );
        sql.append("                  T1.SUPPLIER_ID,\n" );
        sql.append("                  T1.SUPPLIER_CODE,\n" );
        sql.append("                  T1.SUPPLIER_NAME,\n" );
        sql.append("                  T1.PART_NO,\n" );
        sql.append("                  T1.UNIT,\n" );
        sql.append("                  T1.MIN_PACK,\n" );
        sql.append("                  T1.MIN_UNIT,\n" );
        sql.append("                  T1.RETURN_COUNT,\n" );
        sql.append("                  T1.AMOUNT,\n" );
        sql.append("                  T1.IN_AMOUNT,\n" );
        sql.append("                  T1.REMARKS) T");
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
    public boolean insertStockDtl(PtBuStockDtlVO vo) throws Exception {
        return factory.insert(vo);
    }

    /**
     * 修改销售退件申请单
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean updateReturnApply(PtBuReturnApplyVO vo) throws Exception {
        return factory.update(vo);
    }

    /**
     * 修改入库单
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean updateInBill(PtBuStockInVO vo) throws Exception {
        return factory.update(vo);
    }

    /**
     * 入库完成验证
     *
     * @param pInId 入库单ID
     * @param pReturnId 销售退件单ID
     * @return 结果集
     * @throws Exception
     */
    public QuerySet inStockCheck(String pInId, String pReturnId) throws Exception {

    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT 1\n" );
    	sql.append("  FROM (SELECT SUM(IN_AMOUNT) A\n" );
    	sql.append("          FROM PT_BU_STOCK_IN_DTL A\n" );
    	sql.append("         WHERE A.IN_ID = '" + pInId + "') T1,\n" );
    	sql.append("       (SELECT SUM(RETURN_COUNT) B\n" );
    	sql.append("          FROM PT_BU_RETURN_APPLY_DTL\n" );
    	sql.append("         WHERE RETURN_ID = '" + pReturnId + "') T2\n" );
    	sql.append(" WHERE T1.A = T2.B");


        //执行方法，不需要传递conn参数
        return factory.select(null, sql.toString());
    }

    /**
     * 入库单明细配件查询
     *
     * @param pOrderId 入库单ID
     * @param pUser 当前登录user对象
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet searchReturnPart(String pOrderId, User pUser) throws Exception {

        StringBuilder sql= new StringBuilder();
        sql.append("SELECT A.PART_ID, A.SUPPLIER_ID, A.WAREHOUSE_ID, B.IN_AMOUNT\n" );
        sql.append("  FROM PT_BU_STOCK A, PT_BU_STOCK_IN_DTL B\n" );
        sql.append(" WHERE A.PART_ID = B.PART_ID\n" );
        sql.append("   AND A.SUPPLIER_ID = B.SUPPLIER_ID\n" );
        sql.append("   AND A.WAREHOUSE_ID =\n" );
        sql.append("       (SELECT WAREHOUSE_ID\n" );
        sql.append("          FROM PT_BU_STOCK_IN\n" );
        sql.append("         WHERE IN_ID = '" + pOrderId + "')\n" );
        sql.append("   AND B.IN_ID = '" + pOrderId + "'");

        //执行方法，不需要传递conn参数
        return factory.select(null, sql.toString());
    }

    /**
     * 修改库存占用
     *
     * @param partId 配件ID
     * @param supplierId 供应商ID
     * @param returnCount 入库数量
     * @param inId 入库ID
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean updateStock(String partId,String supplierId,String returnCount,String inId) throws Exception {

        // 修改库存表sql
        StringBuilder sql= new StringBuilder();
        sql.append("UPDATE PT_BU_STOCK\n" );
        sql.append("   SET AVAILABLE_AMOUNT    = AVAILABLE_AMOUNT + '" + returnCount + "',\n" );
        sql.append("       AMOUNT = AMOUNT + '" + returnCount + "'\n" );
        sql.append(" WHERE PART_ID = '" + partId + "'\n" );
        sql.append("   AND SUPPLIER_ID = '" + supplierId + "'\n");
        sql.append("   AND WAREHOUSE_ID = \n");
        sql.append("       (SELECT WAREHOUSE_ID\n" );
        sql.append("          FROM PT_BU_STOCK_IN\n" );
        sql.append("         WHERE IN_ID = '" + inId + "')\n" );

        return factory.update(sql.toString(), null);
    }

    /**
     * 入库单号是否存在
     * 
     * @param splitId
     * @return
     * @throws Exception
     */
    public QuerySet selectStockInInfo(String splitId) throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT IN_ID, IN_NO\n" );
    	sql.append("  FROM PT_BU_STOCK_IN\n" );
    	sql.append(" WHERE ORDER_ID = "+splitId+"\n" );
//    	sql.append("   AND PRINT_STATUS = "+DicConstant.DYZT_01+"\n");
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
        sql.append("       UPDATE_USER = '" + user.getAccount() + "',\n");
        sql.append("       UPDATE_TIME = SYSDATE\n");
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
     * 修改移库入库明细的已入库数量
     *
     * @param map
     * @param user
     * @return
     * @throws Exception
     */
    public boolean updateSplitDtl(Map<String, String> map, User user)
            throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE PT_BU_STOCK_IN_DTL A\n");
        sql.append("   SET A.IN_AMOUNT = NVL(A.IN_AMOUNT,0) + " + map.get("CUR_IN_AMOUNT")+"\n");
        sql.append(" WHERE A.DETAIL_ID = " + map.get("DETAIL_ID") + "\n");

        return factory.update(sql.toString(), null);
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
     * @param RETURN_NO
     * @return
     */
    public String createInBillNo(String RETURN_NO) {
        return PartOddNumberUtil.getSaleRetInBillNo(RETURN_NO);
    }
    /**
     * 
     * @date()2014年8月30日下午2:22:48
     * @author Administrator
     * @to_do:获取退件金额
     * @param IN_ID
     * @return
     * @throws Exception
     */
    public QuerySet getMoney(String IN_ID) throws Exception
    {
        QuerySet qs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT SUM(T1.IN_AMOUNT * T2.SALE_PRICE) AMOUNT\n" );
        sql.append("  FROM PT_BU_STOCK_IN T, PT_BU_STOCK_IN_DTL T1, PT_BU_RETURN_APPLY_DTL T2\n" );
        sql.append(" WHERE T.ORDER_ID = T2.RETURN_ID\n" );
        sql.append("   AND T.IN_ID = T1.IN_ID\n" );
        sql.append("   AND T1.PART_ID = T2.PART_ID\n" );
        sql.append("   AND T.IN_ID = "+IN_ID+"");
        qs = factory.select(null, sql.toString());
        return qs;
    }
    /**
     * 
     * @date()2014年8月30日下午2:23:00
     * @author Administrator
     * @to_do:获取账户ID
     * @param IN_ID
     * @return
     * @throws Exception
     */
    public QuerySet getAccount(String IN_ID) throws Exception
    {
        QuerySet qs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T2.ACCOUNT_ID\n" );
        sql.append("  FROM PT_BU_STOCK_IN T, PT_BU_RETURN_APPLY T1, PT_BU_ACCOUNT T2\n" );
        sql.append(" WHERE T.ORDER_ID = T1.RETURN_ID\n" );
        sql.append("   AND T1.APPLY_ORG_ID = T2.ORG_ID\n" );
        sql.append("   AND T2.ACCOUNT_TYPE = "+DicConstant.ZJZHLX_03+"\n" );
        sql.append("   AND T.IN_ID = "+IN_ID+"");
        qs = factory.select(null, sql.toString());
        return qs;
    }
    
    public boolean accountLogInsert(PtBuAccountLogVO vo)
            throws Exception {
        return factory.insert(vo);
    }
    /**
     * 
     * @date()2014年8月30日下午2:23:13
     * @author Administrator
     * @to_do:更改材料费总金额 可用金额
     * @param ACCOUNT_ID
     * @param amount
     * @return
     * @throws Exception
     */
    public boolean updateAccount(String ACCOUNT_ID,String amount) throws Exception
    {
        StringBuffer sql= new StringBuffer();
        sql.append("UPDATE PT_BU_ACCOUNT\n" );
        sql.append("   SET AVAILABLE_AMOUNT = AVAILABLE_AMOUNT + "+amount+",\n" );
        sql.append("       BALANCE_AMOUNT   = BALANCE_AMOUNT + "+amount+"\n" );
        sql.append(" WHERE ACCOUNT_ID = "+ACCOUNT_ID+"\n" );
        return factory.update(sql.toString(), null);
    }
    public QuerySet checkPlanPrice(String partIds)
            throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT to_char(wm_concat( PART_CODE)) CODES FROM PT_BA_INFO WHERE PART_ID IN ("+partIds+") AND NVL(PLAN_PRICE,0)=0\n" );
        return factory.select(null, sql.toString());
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
    
    public void updateInBillDtl(String IN_ID) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE PT_BU_STOCK_IN_DTL A\n");
        sql.append("   SET \n");
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
    
    public QuerySet getStockId()
            throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT F_GETID() STOCK_ID FROM DUAL\n" );
        return factory.select(null, sql.toString());
    }
    public void updateRetOrder(String IN_ID,String returnId) throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("UPDATE PT_BU_RETURN_APPLY_DTL T\n" );
    	sql.append("   SET T.IN_COUNT  =\n" );
    	sql.append("       (SELECT SUM(NVL(IN_AMOUNT,0))\n" );
    	sql.append("          FROM PT_BU_STOCK_IN_DTL T1, PT_BU_STOCK_IN T2\n" );
    	sql.append("         WHERE T.RETURN_ID = T2.ORDER_ID\n" );
    	sql.append("           AND T1.IN_ID = T2.IN_ID\n" );
    	sql.append("           AND T.PART_ID = T1.PART_ID\n" );
    	sql.append("           AND T2.IN_ID = "+IN_ID+"),\n" );
    	sql.append("       T.PLAN_PRICE =\n" );
    	sql.append("       (SELECT T3.PLAN_PRICE FROM PT_BA_INFO T3 WHERE T.PART_ID = T3.PART_ID)\n" );
    	sql.append(" WHERE T.RETURN_ID = "+returnId+"");
        factory.update(sql.toString(), null);
        StringBuffer sql2= new StringBuffer();
    	sql2.append("SELECT B.DTL_ID,B.PART_ID,A.APPLY_ORG_ID,B.SUPPLIER_ID,B.RETURN_COUNT,NVL(B.IN_COUNT,0) IN_COUNT\n" );
    	sql2.append("  FROM PT_BU_RETURN_APPLY A, PT_BU_RETURN_APPLY_DTL B\n" );
    	sql2.append(" WHERE A.RETURN_ID = B.RETURN_ID\n" );
    	sql2.append(" AND A.RETURN_ID ="+returnId+"\n" );
    	QuerySet qs = factory.select(null, sql2.toString());
    	if(qs.getRowCount()>0){
    		for(int i = 0;i<qs.getRowCount();i++){
    			String partId= qs.getString(i+1, "PART_ID");
    			String orgId= qs.getString(i+1, "APPLY_ORG_ID");
    			String supplierId= qs.getString(i+1, "SUPPLIER_ID");
    			String retrunCount= qs.getString(i+1, "RETURN_COUNT");
    			String inCount= qs.getString(i+1, "IN_COUNT");
    			String sql3 = "UPDATE PT_BU_DEALER_STOCK T SET T.AMOUNT =T.AMOUNT-"+inCount+",T.OCCUPY_AMOUNT = T.OCCUPY_AMOUNT-"+retrunCount+" ,T.AVAILABLE_AMOUNT= T.AVAILABLE_AMOUNT+"+retrunCount+"-"+inCount+"  WHERE PART_ID ="+partId+" AND ORG_ID ="+orgId+" AND SUPPLIER_ID = "+supplierId+"\n";
    			factory.update(sql3.toString(), null);
    		}
    	}
    }
}