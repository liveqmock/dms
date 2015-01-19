package com.org.dms.dao.part.storageMng.stockOutMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuPchReturnVO;
import com.org.dms.vo.part.PtBuStockOutDtlVO;
import com.org.dms.vo.part.PtBuStockOutVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.*;
import com.org.mvc.context.ActionContext;

/**
 * 采购退货出库Dao
 *
 * @user : lichuang
 * @date : 2014-08-05
 */
public class PchRetStockOutMngDao extends BaseDAO {
    //定义instance
    public static final PchRetStockOutMngDao getInstance(ActionContext atx) {
        PchRetStockOutMngDao dao = new PchRetStockOutMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }


    /**
     * 查询出库单
     *
     * @param page
     * @param user
     * @param conditions
     * @return
     * @throws Exception
     */
    public BaseResultSet searchPchReturn(PageManager page, User user, String conditions,String ACCOUNT) throws Exception {
    	String wheres = conditions;
//    	wheres +="AND T.RETURN_STATUS = "+DicConstant.CGTHDZT_03+"\n" +
//    					"  AND T.OEM_COMPANY_ID = "+user.getOemCompanyId()+""+
//    					"  AND T.RETURN_ID = T1.RETURN_ID"+
//    					"  AND T.RETURN_ID = T2.ORDER_ID(+)"+
//    					"  AND T1.WAREHOUSE_ID = T3.WAREHOUSE_ID"+
//    					"  AND T3.WAREHOUSE_TYPE = 100101\n" + 
//    					"  ORDER BY T.RETURN_NO DESC";
    	wheres +="AND T.RETURN_STATUS = 201103\n" +
    					"   AND EXISTS (SELECT 1\n" + 
    					"          FROM PT_BU_PCH_RETURN_DTL O, PT_BA_WAREHOUSE_KEEPER P\n" + 
    					"         WHERE O.PART_ID = P.PART_ID\n" + 
    					"           AND P.USER_ACCOUNT = '"+ACCOUNT+"'\n" + 
    					"           AND O.RETURN_ID = T.RETURN_ID\n" + 
    					"           AND O.COUNT > 0)\n" + 
						"AND NOT EXISTS (SELECT 1\n" +
						"       FROM PT_BU_PCH_RETURN_DTL A1,\n" + 
						"            PT_BU_STOCK_OUT      A2,\n" + 
						"            PT_BU_STOCK_OUT_DTL  A3,\n" + 
						"            PT_BA_WAREHOUSE_KEEPER A4\n" + 
						"      WHERE A1.RETURN_ID = A2.ORDER_ID\n" + 
						"        AND A2.OUT_ID = A3.OUT_ID\n" + 
						"        AND A1.PART_ID = A3.PART_ID\n" + 
						"        AND A1.PART_ID = A4.PART_ID\n" +
						"        AND A4.USER_ACCOUNT = '"+ACCOUNT+"'\n" +
						"        AND A1.RETURN_ID = T.RETURN_ID)"+
    					"   AND T.RETURN_ID = T2.ORDER_ID(+)\n" + 
    					" ORDER BY T.RETURN_NO DESC";
        page.setFilter(wheres);

        //定义返回结果集
        BaseResultSet bs = null;
//        StringBuffer sql= new StringBuffer();
//        sql.append("SELECT T.RETURN_ID,\n" );
//        sql.append("       T.RETURN_NO,\n" );
//        sql.append("       T.SUPPLIER_NAME,\n" );
//        sql.append("       T.SUPPLIER_CODE,\n" );
//        sql.append("       T.ORDER_USER,\n" );
//        sql.append("       T.ORDER_DATE,\n" );
//        sql.append("       T3.WAREHOUSE_ID,\n" );
//        sql.append("       T3.WAREHOUSE_CODE,\n" );
//        sql.append("       T3.WAREHOUSE_NAME,\n" );
//        sql.append("       NVL(T2.RATE,0) RATE\n" );
//        sql.append("  FROM PT_BU_PCH_RETURN T,\n");
//        sql.append("(SELECT DISTINCT C.WAREHOUSE_ID, A.RETURN_ID\n" );
//        sql.append("   FROM PT_BU_PCH_RETURN_DTL     A,\n" );
//        sql.append("        PT_BA_WAREHOUSE_POSITION B,\n" );
//        sql.append("        PT_BA_WAREHOUSE_AREA     C,\n" );
//        sql.append("        PT_BA_WAREHOUSE_KEEPER   D\n" );
//        sql.append("  WHERE A.POSITION_ID = B.POSITION_ID\n" );
//        sql.append("    AND B.AREA_ID = C.AREA_ID\n" );
//        sql.append("    AND C.WAREHOUSE_ID = D.WAREHOUSE_ID\n" );
//        sql.append("    AND D.USER_ACCOUNT = '"+ACCOUNT+"') T1,");
//        sql.append("(SELECT ROUND(NVL(SUM(A.OUT_AMOUNT), 0) / SUM(C.COUNT), 2) RATE,\n" );
//        sql.append("        B.ORDER_ID\n" );
//        sql.append("   FROM PT_BU_STOCK_OUT_DTL  A,\n" );
//        sql.append("        PT_BU_STOCK_OUT      B,\n" );
//        sql.append("        PT_BU_PCH_RETURN_DTL C\n" );
//        sql.append("  WHERE A.OUT_ID = B.OUT_ID\n" );
//        sql.append("    AND B.ORDER_ID = C.RETURN_ID\n" );
//        sql.append("  GROUP BY B.ORDER_ID) T2, PT_BA_WAREHOUSE T3");
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.RETURN_ID,\n" );
        sql.append("       T.RETURN_NO,\n" );
        sql.append("       T.SUPPLIER_NAME,\n" );
        sql.append("       T.SUPPLIER_CODE,\n" );
        sql.append("       T.ORDER_USER,\n" );
        sql.append("       T.ORDER_DATE,\n" );
        sql.append("       NVL(T2.RATE, 0) RATE\n" );
        sql.append("  FROM PT_BU_PCH_RETURN T,\n" );
        sql.append("       (SELECT ROUND(NVL(SUM(A.OUT_AMOUNT), 0) / SUM(C.COUNT), 2) RATE,\n" );
        sql.append("               B.ORDER_ID\n" );
        sql.append("          FROM PT_BU_STOCK_OUT_DTL  A,\n" );
        sql.append("               PT_BU_STOCK_OUT      B,\n" );
        sql.append("               PT_BU_PCH_RETURN_DTL C\n" );
        sql.append("         WHERE A.OUT_ID = B.OUT_ID\n" );
        sql.append("           AND B.ORDER_ID = C.RETURN_ID\n" );
        sql.append("         GROUP BY B.ORDER_ID) T2");
        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        bs.setFieldDateFormat("ORDER_DATE", "yyyy-MM-dd");
        bs.setFieldUserID("ORDER_USER");
        return bs;
    }

    /**
     * 查询采购退货单
     *
     * @param page
     * @param user
     * @param conditions
     * @return
     * @throws Exception
     */
    public BaseResultSet searchPchRet(PageManager page, User user, String conditions) throws Exception {
        StringBuilder wheres = new StringBuilder(conditions);
        wheres.append("   AND A.PURCHASE_ID = B.SPLIT_ID\n");
        wheres.append("   AND A.RETURN_STATUS = " + DicConstant.CGTHDZT_03 + "\n");
        wheres.append("   AND B.ORDER_TYPE IN(" + DicConstant.CGDDLX_01 + "," + DicConstant.CGDDLX_02 + "," + DicConstant.CGDDLX_03 + ")\n");
        wheres.append("   AND NOT EXISTS(SELECT 1 FROM PT_BU_STOCK_OUT C WHERE C.ORDER_ID = A.RETURN_ID)\n");
        wheres.append("   AND A.STATUS = " + DicConstant.YXBS_01 + "\n");
        wheres.append("   AND A.OEM_COMPANY_ID=" + user.getOemCompanyId() + "\n");
        wheres.append("   ORDER BY A.CREATE_TIME DESC\n");
        page.setFilter(wheres.toString());

        //定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT A.RETURN_ID,\n");
        sql.append("       A.RETURN_NO,\n");
        sql.append("       A.ORDER_USER,\n");
        sql.append("       B.SPLIT_ID,\n");
        sql.append("       B.SPLIT_NO,\n");
        sql.append("       A.SUPPLIER_ID,\n");
        sql.append("       A.SUPPLIER_CODE,\n");
        sql.append("       A.SUPPLIER_NAME\n");
        sql.append("  FROM PT_BU_PCH_RETURN A, PT_BU_PCH_ORDER_SPLIT B\n");

        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        bs.setFieldUserID("ORDER_USER");
        return bs;
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
     * 新增出库单
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean insertOutBill(PtBuStockOutVO vo)
            throws Exception {
        return factory.insert(vo);
    }

    /**
     * 新增出库单明细
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean insertOutBillDtl(PtBuStockOutDtlVO vo)
            throws Exception {
        return factory.insert(vo);
    }

    /**
     * 修改出库单
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean updateOutBill(PtBuStockOutVO vo)
            throws Exception {
        return factory.update(vo);
    }

    /**
     * 修改采购退货单
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean updatePchReturn(PtBuPchReturnVO vo)
            throws Exception {
        return factory.update(vo);
    }

    /**
     * 查询出库单配件
     *
     * @param page
     * @param user
     * @param conditions
     * @param OUT_ID     出库单ID
     * @param ORDER_ID   采购退货单ID
     * @return
     * @throws Exception
     */
    public BaseResultSet searchOutBillPart(PageManager page, User user, String conditions, String ORDER_ID,String account) throws Exception {

        //定义返回结果集
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.DETAIL_ID,\n" );
        sql.append("       T.PART_ID,\n" );
        sql.append("       T.PART_NAME,\n" );
        sql.append("       T.PART_CODE,\n" );
        sql.append("       T.POSITION_ID,\n" );
        sql.append("       T.POSITION_CODE,\n" );
        sql.append("       T.POSITION_NAME,\n" );
        sql.append("       T.COUNT,\n" );
        sql.append("       T1.MIN_PACK,\n" );
        sql.append("       T1.MIN_UNIT,\n" );
        sql.append("       T1.UNIT,\n" );
        sql.append("       T2.AVAILABLE_AMOUNT\n" );
        sql.append("  FROM PT_BU_PCH_RETURN_DTL T, PT_BA_INFO T1, PT_BU_STOCK_DTL T2,PT_BA_WAREHOUSE_KEEPER T3\n" );
        sql.append(" WHERE T.PART_ID = T1.PART_ID\n" );
        sql.append("   AND T.PART_ID = T2.PART_ID\n" );
        sql.append("   AND T.PART_ID = T3.PART_ID\n" );
        sql.append("   AND T3.USER_ACCOUNT = '"+account+"'\n" );
        sql.append("   AND T.COUNT > 0\n" );
        sql.append("   AND T.POSITION_ID = T2.POSITION_ID\n" );
        sql.append("   AND T.RETURN_ID = "+ORDER_ID+"");

        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("UNIT", DicConstant.JLDW);
        bs.setFieldDic("MIN_UNIT", DicConstant.JLDW);
        return bs;
    }

    /**
     * 修改库存
     *
     * @param WAREHOUSE_ID 仓库ID
     * @param OUT_ID       出库单ID
     * @param user
     * @return
     * @throws Exception
     */
    public boolean updateStock(String WAREHOUSE_ID, String OUT_ID, User user)
            throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE PT_BU_STOCK A\n");
        sql.append("   SET A.OCCUPY_AMOUNT    = OCCUPY_AMOUNT -\n");
        sql.append("                            (SELECT B.OUT_AMOUNT\n");
        sql.append("                               FROM PT_BU_STOCK_OUT_DTL B\n");
        sql.append("                              WHERE B.OUT_ID = " + OUT_ID + "\n");
        sql.append("                                AND B.SUPPLIER_ID = A.SUPPLIER_ID\n");
        sql.append("                                AND B.PART_ID = A.PART_ID),\n");
        sql.append("       A.AMOUNT = AMOUNT -\n");
        sql.append("                            (SELECT B.OUT_AMOUNT\n");
        sql.append("                               FROM PT_BU_STOCK_OUT_DTL B\n");
        sql.append("                              WHERE B.OUT_ID = " + OUT_ID + "\n");
        sql.append("                                AND B.SUPPLIER_ID = A.SUPPLIER_ID\n");
        sql.append("                                AND B.PART_ID = A.PART_ID),\n");
        sql.append("       A.UPDATE_USER      = '" + user.getAccount() + "',\n");
        sql.append("       A.UPDATE_TIME      = SYSDATE\n");
        sql.append(" WHERE 1 = 1\n");
        sql.append("   AND A.WAREHOUSE_ID = " + WAREHOUSE_ID + "\n");
        sql.append("   AND EXISTS (SELECT 1\n");
        sql.append("          FROM PT_BU_STOCK_OUT_DTL B\n");
        sql.append("         WHERE B.OUT_ID = " + OUT_ID + "\n");
        sql.append("           AND B.SUPPLIER_ID = A.SUPPLIER_ID\n");
        sql.append("           AND B.PART_ID = A.PART_ID)\n");

        return factory.update(sql.toString(), null);
    }

    /**
     * 修改库存明细
     *
     * @param OUT_ID 出库单ID
     * @param user
     * @return
     * @throws Exception
     */
    public boolean updateStockDtl(String OUT_ID, User user)
            throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE PT_BU_STOCK_DTL A\n");
        sql.append("   SET A.OCCUPY_AMOUNT    = OCCUPY_AMOUNT -\n");
        sql.append("                            (SELECT B.OUT_AMOUNT\n");
        sql.append("                               FROM PT_BU_STOCK_OUT_DTL B\n");
        sql.append("                              WHERE B.OUT_ID = " + OUT_ID + "\n");
        sql.append("                                AND B.POSITION_ID = A.POSITION_ID\n");
        sql.append("                                AND B.SUPPLIER_ID = A.SUPPLIER_ID\n");
        sql.append("                                AND B.PART_ID = A.PART_ID),\n");
        sql.append("       A.AMOUNT = AMOUNT -\n");
        sql.append("                            (SELECT B.OUT_AMOUNT\n");
        sql.append("                               FROM PT_BU_STOCK_OUT_DTL B\n");
        sql.append("                              WHERE B.OUT_ID = " + OUT_ID + "\n");
        sql.append("                                AND B.POSITION_ID = A.POSITION_ID\n");
        sql.append("                                AND B.SUPPLIER_ID = A.SUPPLIER_ID\n");
        sql.append("                                AND B.PART_ID = A.PART_ID),\n");
        sql.append("       A.UPDATE_USER      = '" + user.getAccount() + "',\n");
        sql.append("       A.UPDATE_TIME      = SYSDATE\n");
        sql.append(" WHERE 1 = 1\n");
        sql.append("   AND EXISTS (SELECT 1\n");
        sql.append("          FROM PT_BU_STOCK_OUT_DTL B\n");
        sql.append("         WHERE B.OUT_ID = " + OUT_ID + "\n");
        sql.append("           AND B.POSITION_ID = A.POSITION_ID\n");
        sql.append("           AND B.SUPPLIER_ID = A.SUPPLIER_ID\n");
        sql.append("           AND B.PART_ID = A.PART_ID)\n");

        return factory.update(sql.toString(), null);
    }

    /**
     * 删除出库单
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean deleteOutBill(PtBuStockOutVO vo) throws Exception {
        return factory.delete(vo);
    }

    /**
     * 删除出库单明细
     *
     * @param outId 出库单ID
     * @return
     * @throws Exception
     */
    public boolean deleteOutBillDtl(String outId) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM PT_BU_STOCK_OUT_DTL WHERE OUT_ID = " + outId + "\n");
        return factory.delete(sql.toString(), null);
    }

    /**
     * 新增出库流水
     *
     * @param OUT_ID 出库单ID
     * @param user
     * @return
     * @throws Exception
     */
    public boolean insertOutFlow(String OUT_ID, User user) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO PT_BU_STOCK_OUT_CONTINUAL\n");
        sql.append("  (CONTINUAL_ID,\n");
        sql.append("   CONTINUAL_NO,\n");
        sql.append("   OUT_ID,\n");
        sql.append("   OUT_NO,\n");
        sql.append("   PART_ID,\n");
        sql.append("   PART_CODE,\n");
        sql.append("   PART_NAME,\n");
        sql.append("   OUT_DATE,\n");
        sql.append("   KEEPER_MAN,\n");
        sql.append("   OUT_COUNT,\n");
//        sql.append("   OUT_COUNT_TMP,\n");
        sql.append("   CREATE_MAN)\n");
        sql.append("  SELECT F_GETID(),\n");
        sql.append("         F_GETOUT_CONTINUAL(),\n");
        sql.append("         A.OUT_ID,\n");
        sql.append("         B.OUT_NO,\n");
        sql.append("         A.PART_ID,\n");
        sql.append("         A.PART_CODE,\n");
        sql.append("         A.PART_NAME,\n");
        sql.append("         SYSDATE,\n");
        sql.append("         A.KEEP_MAN,\n");
        sql.append("         A.OUT_AMOUNT ,\n");
//        sql.append("         A.OUT_AMOUNT OUT_AMOUNT_TMP,\n");
        sql.append("         '" + user.getAccount() + "'\n");
        sql.append("    FROM PT_BU_STOCK_OUT_DTL A, PT_BU_STOCK_OUT B\n");
        sql.append("   WHERE A.OUT_ID = B.OUT_ID\n");
        sql.append("   AND A.OUT_ID = " + OUT_ID + "\n");

        return factory.update(sql.toString(), null);
    }

    /**
     * 校验出库单明细是否存在
     *
     * @param OUT_ID      出库单ID
     * @param PART_ID     配件ID
     * @param POSITION_ID 库位ID
     * @param SUPPLIER_ID 供应商ID
     * @param user
     * @return
     * @throws Exception
     */
    public String checkOutBillDtlIsExist(String OUT_ID, String PART_ID, String POSITION_ID, String SUPPLIER_ID, User user) throws Exception {
        String DETAIL_ID = "";
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT A.DETAIL_ID\n");
        sql.append("  FROM PT_BU_STOCK_OUT_DTL A\n");
        sql.append(" WHERE A.OUT_ID = " + OUT_ID + "\n");
        sql.append("   AND A.PART_ID = " + PART_ID + "\n");
        sql.append("   AND A.POSITION_ID = " + POSITION_ID + "\n");
        sql.append("   AND A.SUPPLIER_ID = " + SUPPLIER_ID + "\n");

        QuerySet qs = factory.select(null, sql.toString());
        if (qs.getRowCount() > 0) {
            DETAIL_ID = qs.getString(1, "DETAIL_ID");
        }
        return DETAIL_ID;
    }

    /**
     * 修改出库单明细
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean updateOutBillDtl(PtBuStockOutDtlVO vo)
            throws Exception {
        return factory.update(vo);
    }

    /**
     * 解锁库存明细
     *
     * @param OUT_ID 出库单ID
     * @param user
     * @return
     * @throws Exception
     */
    public boolean unLockStockDtl(String OUT_ID, User user)
            throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE PT_BU_STOCK_DTL A\n");
        sql.append("   SET A.OCCUPY_AMOUNT    = OCCUPY_AMOUNT -\n");
        sql.append("                            (SELECT B.OUT_AMOUNT\n");
        sql.append("                               FROM PT_BU_STOCK_OUT_DTL B\n");
        sql.append("                              WHERE B.OUT_ID = " + OUT_ID + "\n");
        sql.append("                                AND B.POSITION_ID = A.POSITION_ID\n");
        sql.append("                                AND B.SUPPLIER_ID = A.SUPPLIER_ID\n");
        sql.append("                                AND B.PART_ID = A.PART_ID),\n");
        sql.append("       A.AVAILABLE_AMOUNT = AVAILABLE_AMOUNT +\n");
        sql.append("                            (SELECT B.OUT_AMOUNT\n");
        sql.append("                               FROM PT_BU_STOCK_OUT_DTL B\n");
        sql.append("                              WHERE B.OUT_ID = " + OUT_ID + "\n");
        sql.append("                                AND B.POSITION_ID = A.POSITION_ID\n");
        sql.append("                                AND B.SUPPLIER_ID = A.SUPPLIER_ID\n");
        sql.append("                                AND B.PART_ID = A.PART_ID),\n");
        sql.append("       A.UPDATE_USER      = '" + user.getAccount() + "',\n");
        sql.append("       A.UPDATE_TIME      = SYSDATE\n");
        sql.append(" WHERE 1 = 1\n");
        sql.append("   AND EXISTS (SELECT 1\n");
        sql.append("          FROM PT_BU_STOCK_OUT_DTL B\n");
        sql.append("         WHERE B.OUT_ID = " + OUT_ID + "\n");
        sql.append("           AND B.POSITION_ID = A.POSITION_ID\n");
        sql.append("           AND B.SUPPLIER_ID = A.SUPPLIER_ID\n");
        sql.append("           AND B.PART_ID = A.PART_ID)\n");


        return factory.update(sql.toString(), null);
    }

    /**
     * 解锁库存
     *
     * @param WAREHOUSE_ID 仓库ID
     * @param OUT_ID       出库单ID
     * @param user
     * @return
     * @throws Exception
     */
    public boolean unLockStock(String WAREHOUSE_ID, String OUT_ID, User user)
            throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE PT_BU_STOCK A\n");
        sql.append("   SET A.OCCUPY_AMOUNT    = OCCUPY_AMOUNT -\n");
        sql.append("                            (SELECT B.OUT_AMOUNT\n");
        sql.append("                               FROM PT_BU_STOCK_OUT_DTL B\n");
        sql.append("                              WHERE B.OUT_ID = " + OUT_ID + "\n");
        sql.append("                                AND B.SUPPLIER_ID = A.SUPPLIER_ID\n");
        sql.append("                                AND B.PART_ID = A.PART_ID),\n");
        sql.append("       A.AVAILABLE_AMOUNT = AVAILABLE_AMOUNT +\n");
        sql.append("                            (SELECT B.OUT_AMOUNT\n");
        sql.append("                               FROM PT_BU_STOCK_OUT_DTL B\n");
        sql.append("                              WHERE B.OUT_ID = " + OUT_ID + "\n");
        sql.append("                                AND B.SUPPLIER_ID = A.SUPPLIER_ID\n");
        sql.append("                                AND B.PART_ID = A.PART_ID),\n");
        sql.append("       A.UPDATE_USER      = '" + user.getAccount() + "',\n");
        sql.append("       A.UPDATE_TIME      = SYSDATE\n");
        sql.append(" WHERE 1 = 1\n");
        sql.append("   AND A.WAREHOUSE_ID = " + WAREHOUSE_ID + "\n");
        sql.append("   AND EXISTS (SELECT 1\n");
        sql.append("          FROM PT_BU_STOCK_OUT_DTL B\n");
        sql.append("         WHERE B.OUT_ID = " + OUT_ID + "\n");
        sql.append("           AND B.SUPPLIER_ID = A.SUPPLIER_ID\n");
        sql.append("           AND B.PART_ID = A.PART_ID)\n");


        return factory.update(sql.toString(), null);
    }

    /**
     * 锁定库存明细
     *
     * @param OUT_ID 出库单ID
     * @param user
     * @return
     * @throws Exception
     */
    public boolean lockStockDtl(String OUT_ID, User user)
            throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE PT_BU_STOCK_DTL A\n");
        sql.append("   SET A.OCCUPY_AMOUNT    = OCCUPY_AMOUNT +\n");
        sql.append("                            (SELECT B.OUT_AMOUNT\n");
        sql.append("                               FROM PT_BU_STOCK_OUT_DTL B\n");
        sql.append("                              WHERE B.OUT_ID = " + OUT_ID + "\n");
        sql.append("                                AND B.POSITION_ID = A.POSITION_ID\n");
        sql.append("                                AND B.SUPPLIER_ID = A.SUPPLIER_ID\n");
        sql.append("                                AND B.PART_ID = A.PART_ID),\n");
        sql.append("       A.AVAILABLE_AMOUNT = AVAILABLE_AMOUNT -\n");
        sql.append("                            (SELECT B.OUT_AMOUNT\n");
        sql.append("                               FROM PT_BU_STOCK_OUT_DTL B\n");
        sql.append("                              WHERE B.OUT_ID = " + OUT_ID + "\n");
        sql.append("                                AND B.POSITION_ID = A.POSITION_ID\n");
        sql.append("                                AND B.SUPPLIER_ID = A.SUPPLIER_ID\n");
        sql.append("                                AND B.PART_ID = A.PART_ID),\n");
        sql.append("       A.UPDATE_USER      = '" + user.getAccount() + "',\n");
        sql.append("       A.UPDATE_TIME      = SYSDATE\n");
        sql.append(" WHERE 1 = 1\n");
        sql.append("   AND EXISTS (SELECT 1\n");
        sql.append("          FROM PT_BU_STOCK_OUT_DTL B\n");
        sql.append("         WHERE B.OUT_ID = " + OUT_ID + "\n");
        sql.append("           AND B.POSITION_ID = A.POSITION_ID\n");
        sql.append("           AND B.SUPPLIER_ID = A.SUPPLIER_ID\n");
        sql.append("           AND B.PART_ID = A.PART_ID)\n");


        return factory.update(sql.toString(), null);
    }

    /**
     * 锁定库存
     *
     * @param WAREHOUSE_ID 仓库ID
     * @param OUT_ID       出库单ID
     * @param user
     * @return
     * @throws Exception
     */
    public boolean lockStock(String WAREHOUSE_ID, String OUT_ID, User user)
            throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE PT_BU_STOCK A\n");
        sql.append("   SET A.OCCUPY_AMOUNT    = OCCUPY_AMOUNT +\n");
        sql.append("                            (SELECT B.OUT_AMOUNT\n");
        sql.append("                               FROM PT_BU_STOCK_OUT_DTL B\n");
        sql.append("                              WHERE B.OUT_ID = " + OUT_ID + "\n");
        sql.append("                                AND B.SUPPLIER_ID = A.SUPPLIER_ID\n");
        sql.append("                                AND B.PART_ID = A.PART_ID),\n");
        sql.append("       A.AVAILABLE_AMOUNT = AVAILABLE_AMOUNT -\n");
        sql.append("                            (SELECT B.OUT_AMOUNT\n");
        sql.append("                               FROM PT_BU_STOCK_OUT_DTL B\n");
        sql.append("                              WHERE B.OUT_ID = " + OUT_ID + "\n");
        sql.append("                                AND B.SUPPLIER_ID = A.SUPPLIER_ID\n");
        sql.append("                                AND B.PART_ID = A.PART_ID),\n");
        sql.append("       A.UPDATE_USER      = '" + user.getAccount() + "',\n");
        sql.append("       A.UPDATE_TIME      = SYSDATE\n");
        sql.append(" WHERE 1 = 1\n");
        sql.append("   AND A.WAREHOUSE_ID = " + WAREHOUSE_ID + "\n");
        sql.append("   AND EXISTS (SELECT 1\n");
        sql.append("          FROM PT_BU_STOCK_OUT_DTL B\n");
        sql.append("         WHERE B.OUT_ID = " + OUT_ID + "\n");
        sql.append("           AND B.SUPPLIER_ID = A.SUPPLIER_ID\n");
        sql.append("           AND B.PART_ID = A.PART_ID)\n");


        return factory.update(sql.toString(), null);
    }

    /**
     * 校验出库单明细是否存在
     *
     * @param OUT_ID 出库单ID
     * @return
     * @throws Exception
     */
    public Boolean checkOutBillDtlIsExist(String OUT_ID) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT 1\n");
        sql.append("  FROM PT_BU_STOCK_OUT_DTL A\n");
        sql.append(" WHERE A.OUT_ID = " + OUT_ID + "\n");

        QuerySet qs = factory.select(null, sql.toString());
        if (qs.getRowCount() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 查询库位可用库存
     *
     * @param PART_ID     配件ID
     * @param POSITION_ID 库位ID
     * @param SUPPLIER_ID 供应商ID
     * @return
     * @throws Exception
     */
    public String queryPositionAvailableAmount(String PART_ID, String POSITION_ID, String SUPPLIER_ID) throws Exception {
        String availableAmount = "";
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT A.AVAILABLE_AMOUNT FROM PT_BU_STOCK_DTL A WHERE A.PART_ID = " + PART_ID + " AND A.POSITION_ID = " + POSITION_ID + " AND A.SUPPLIER_ID = " + SUPPLIER_ID + "\n");
        QuerySet qs = factory.select(null, sql.toString());
        if (qs.getRowCount() > 0) {
            availableAmount = qs.getString(1, "AVAILABLE_AMOUNT");
        }
        return availableAmount;
    }
    public QuerySet getWareInfo(String RETURN_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append(" SELECT T.WAREHOUSE_ID,T.WAREHOUSE_CODE,T.WAREHOUSE_NAME,T1.RETURN_NO FROM PT_BA_WAREHOUSE T,PT_BU_PCH_RETURN T1 WHERE T.WAREHOUSE_TYPE = 100101 AND T.WAREHOUSE_STATUS = "+DicConstant.YXBS_01+" AND T1.RETURN_ID = "+RETURN_ID+" " );
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
    public QuerySet checkFinish(String RETURN_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT 1\n" );
    	sql.append("  FROM PT_BU_PCH_RETURN_DTL T\n" );
    	sql.append(" WHERE T.RETURN_ID = "+RETURN_ID+"\n" );
    	sql.append("   AND NOT EXISTS (SELECT 1\n" );
    	sql.append("          FROM PT_BU_STOCK_OUT T1, PT_BU_STOCK_OUT_DTL T2\n" );
    	sql.append("         WHERE T1.OUT_ID = T2.OUT_ID\n" );
    	sql.append("           AND T1.ORDER_ID = T.RETURN_ID\n" );
    	sql.append("           AND T.PART_ID = T2.PART_ID)");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
    
    public QuerySet getRetInfo(String DETAIL_ID,String OUT_AMOUNT) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.PART_ID,\n" );
    	sql.append("       T.PART_NAME,\n" );
    	sql.append("       T.PART_CODE,\n" );
    	sql.append("       T.POSITION_ID,\n" );
    	sql.append("       T.SUPPLIER_ID,\n" );
    	sql.append("       T.SUPPLIER_CODE,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("       T4.UNIT,\n" );
    	sql.append("       NVL(T2.PLAN_PRICE, 0) PLAN_PRICE,\n" );
    	sql.append("       NVL(T2.PLAN_PRICE, 0) * "+OUT_AMOUNT+" PLAN_AMOUNT\n" );
    	sql.append("  FROM PT_BU_PCH_RETURN_DTL   T,\n" );
    	sql.append("       PT_BA_PART_SUPPLIER_RL T2,\n" );
    	sql.append("       PT_BU_PCH_RETURN       T3,\n" );
    	sql.append("       PT_BA_INFO       T4\n" );
    	sql.append(" WHERE T.DETAIL_ID = "+DETAIL_ID+"\n" );
    	sql.append("   AND T.PART_ID = T2.PART_ID\n" );
    	sql.append("   AND T.RETURN_ID = T3.RETURN_ID\n" );
    	sql.append("   AND T3.SUPPLIER_ID = T2.SUPPLIER_ID AND T2.PART_IDENTIFY = "+DicConstant.YXBS_01+"");
    	sql.append("   AND T.PART_ID = T4.PART_ID");
    	qs = factory.select(null, sql.toString());
    	return qs;
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
    
    public QuerySet checkOut(String RETURN_ID)
            throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT OUT_ID FROM PT_BU_STOCK_OUT WHERE ORDER_ID = "+RETURN_ID+"");
    	return factory.select(null, sql.toString());
    }
    public void insetStockDtl(String saleCount,String aAmount,String SHOULD_COUNT,User user,String saleId,String url,String nPartId) throws Exception {
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
   	   sql.append("           "+saleId+",\n" );
   	   sql.append("           '"+url+"',\n" );
   	   sql.append("           -"+SHOULD_COUNT+",\n" );
   	   sql.append("           -"+saleCount+",\n" );
   	   sql.append("           +"+aAmount+",\n" );
   	   sql.append("           '"+user.getAccount()+"',\n" );
   	   sql.append("           SYSDATE,\n" );
   	   sql.append("           "+nPartId+",\n" );
   	   sql.append("           "+user.getOrgId()+")");

   	   factory.update(sql.toString(), null);
      }
    
    
    public void updateOutBillDtl(String OUT_ID) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE PT_BU_STOCK_OUT_DTL A\n");
        sql.append("   SET \n");
        sql.append("       A.PLAN_PRICE =\n");
        sql.append("       (SELECT B.PLAN_PRICE FROM PT_BA_INFO B WHERE B.PART_ID = A.PART_ID),\n");
        sql.append("       A.PLAN_AMOUNT =\n");
        sql.append("       (SELECT B.PLAN_PRICE * A.OUT_AMOUNT\n");
        sql.append("          FROM PT_BA_INFO B\n");
        sql.append("         WHERE B.PART_ID = A.PART_ID),\n");
        sql.append("       A.UNIT       =\n");
        sql.append("       (SELECT B.UNIT FROM PT_BA_INFO B WHERE B.PART_ID = A.PART_ID)\n");
        sql.append(" WHERE A.OUT_ID = " + OUT_ID + "\n");

        factory.update(sql.toString(), null);
    }

    /**
     * 更新出库流水的计划价
     *
     * @param OUT_ID 出库单ID
     * @throws Exception
     */
    public void updateOutFlow(String OUT_ID) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE PT_BU_STOCK_OUT_CONTINUAL A\n");
        sql.append("   SET A.PLAN_PRICE =\n");
        sql.append("       (SELECT 1 FROM PT_BA_INFO B WHERE B.PART_ID = A.PART_ID)\n");
        sql.append(" WHERE A.OUT_ID = " + OUT_ID + "\n");
    }
    
}