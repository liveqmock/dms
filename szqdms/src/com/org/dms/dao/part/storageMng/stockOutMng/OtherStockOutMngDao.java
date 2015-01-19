package com.org.dms.dao.part.storageMng.stockOutMng;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.common.PartOddNumberUtil;
import com.org.dms.vo.part.PtBuStockOutDtlVO;
import com.org.dms.vo.part.PtBuStockOutVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.*;
import com.org.mvc.context.ActionContext;

/**
 * 其他出库Dao
 *
 * @user : lichuang
 * @date : 2014-08-11
 */
public class OtherStockOutMngDao extends BaseDAO {
    //定义instance
    public static final OtherStockOutMngDao getInstance(ActionContext atx) {
        OtherStockOutMngDao dao = new OtherStockOutMngDao();
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
    public BaseResultSet searchOutBill(PageManager page, User user, String conditions,String account) throws Exception {
        StringBuilder wheres = new StringBuilder(conditions);
        wheres.append("   AND A.OUT_STATUS = " + DicConstant.CKDZT_01 + "\n");
        wheres.append("   AND A.OUT_TYPE = " + DicConstant.CKLX_05 + "\n");
        wheres.append("   AND A.STATUS = " + DicConstant.YXBS_01 + "\n");
        wheres.append("   AND A.CREATE_USER = '" + account + "'\n");
        wheres.append("   AND A.OEM_COMPANY_ID=" + user.getOemCompanyId() + "\n");
        wheres.append("   ORDER BY A.CREATE_TIME DESC\n");
        page.setFilter(wheres.toString());

        //定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT A.OUT_ID,\n");
        sql.append("       A.OUT_NO,\n");
        sql.append("       A.OUT_TYPE,\n");
        sql.append("       A.WAREHOUSE_ID,\n");
        sql.append("       A.WAREHOUSE_CODE,\n");
        sql.append("       A.WAREHOUSE_NAME,\n");
        sql.append("       A.OTHER_OUT_TYPE,\n");
        sql.append("       A.REMARKS\n");
        sql.append("  FROM PT_BU_STOCK_OUT A\n");

        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("OUT_TYPE", DicConstant.CKLX);
        bs.setFieldDic("OTHER_OUT_TYPE", DicConstant.QTCKCKLX);
        return bs;
    }

    /**
     * 查询配件
     *
     * @param page
     * @param user
     * @param conditions
     * @param outId       出库单ID
     * @param warehouseId 仓库ID
     * @return
     * @throws Exception
     */
//    public BaseResultSet searchPart(PageManager page, User user, String conditions, String outId, String warehouseId) throws Exception {
//        String wheres = conditions;
//
//        //过滤掉当前出库单已存在的配件
//        wheres += " AND A.WAREHOUSE_ID = "+warehouseId+"\n";
//        wheres += " AND A.PART_ID = B.PART_ID\n";
//        wheres += " AND A.STOCK_ID = D.STOCK_ID\n";
//        wheres += " AND B.PART_STATUS = " + DicConstant.PJZT_01 + "\n";
//        wheres += " AND NOT EXISTS(SELECT 1 FROM PT_BU_STOCK_OUT_DTL C \n";
//        wheres += "                          WHERE C.OUT_ID = " + outId + "\n";
//        wheres += "                          AND A.SUPPLIER_ID = C.SUPPLIER_ID\n";
//        wheres += "                          AND A.PART_ID = C.PART_ID)\n";
//        wheres += " ORDER BY A.PART_CODE ASC\n";
//        page.setFilter(wheres);
//        //定义返回结果集
//        BaseResultSet bs = null;
//        StringBuilder sql = new StringBuilder();
//        sql.append("SELECT A.PART_ID,\n");
//        sql.append("       A.PART_CODE,\n");
//        sql.append("       A.PART_NAME,\n");
//        sql.append("       B.PART_NO,\n");
//        sql.append("       B.UNIT,\n");
//        sql.append("       B.MIN_PACK,\n");
//        sql.append("       B.MIN_UNIT,\n");
//        sql.append("       B.SALE_PRICE,\n");
//        sql.append("       A.SUPPLIER_ID,\n");
//        sql.append("       A.SUPPLIER_CODE,\n");
//        sql.append("       A.SUPPLIER_NAME,\n");
//        sql.append("       A.STOCK_ID,\n");
//        sql.append("       D.DTL_ID,\n");
//        sql.append("       D.AREA_ID,\n");
//        sql.append("       D.AREA_CODE,\n");
//        sql.append("       D.AREA_NAME,\n");
//        sql.append("       D.POSITION_ID,\n");
//        sql.append("       D.POSITION_CODE,\n");
//        sql.append("       D.POSITION_NAME,\n");
//        sql.append("       D.AVAILABLE_AMOUNT\n");
//        sql.append("  FROM PT_BU_STOCK A ,\n");
//        sql.append("       PT_BA_INFO B, PT_BU_STOCK_DTL D \n");
//        //执行方法，不需要传递conn参数
//        bs = factory.select(sql.toString(), page);
//        bs.setFieldDic("UNIT", DicConstant.JLDW);
//        bs.setFieldDic("MIN_UNIT", DicConstant.JLDW);
//        return bs;
//    }
    
    public BaseResultSet searchPart(PageManager page, User user, String conditions, String outId, String warehouseId) throws Exception {
        String wheres = conditions;

        //过滤掉当前出库单已存在的配件
        wheres += " AND NVL(D.AVAILABLE_AMOUNT,0) > 0\n";
        wheres += " AND A.WAREHOUSE_ID = "+warehouseId+"\n";
        wheres += " AND A.PART_ID = B.PART_ID\n";
        wheres += " AND A.STOCK_ID = D.STOCK_ID\n";
        wheres += " AND B.PART_STATUS <> " + DicConstant.PJZT_02 + "\n";
        wheres += " AND NOT EXISTS(SELECT 1 FROM PT_BU_STOCK_OUT_DTL C \n";
        wheres += "                          WHERE C.OUT_ID = " + outId + "\n";
        wheres += "                          AND A.SUPPLIER_ID = C.SUPPLIER_ID\n";
        wheres += "                          AND A.PART_ID = C.PART_ID  AND C.POSITION_ID = D.POSITION_ID )\n";
        wheres += " ORDER BY A.PART_CODE ASC\n";
        page.setFilter(wheres);
        //定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT A.PART_ID,\n");
        sql.append("       A.PART_CODE,\n");
        sql.append("       A.PART_NAME,\n");
        sql.append("       B.PART_NO,\n");
        sql.append("       B.UNIT,\n");
        sql.append("       B.MIN_PACK,\n");
        sql.append("       B.MIN_UNIT,\n");
        sql.append("       B.SALE_PRICE,\n");
        sql.append("       A.SUPPLIER_ID,\n");
        sql.append("       A.SUPPLIER_CODE,\n");
        sql.append("       A.SUPPLIER_NAME,\n");
        sql.append("       A.STOCK_ID,\n");
        sql.append("       D.DTL_ID,\n");
        sql.append("       D.AREA_ID,\n");
        sql.append("       D.AREA_CODE,\n");
        sql.append("       D.AREA_NAME,\n");
        sql.append("       D.POSITION_ID,\n");
        sql.append("       D.POSITION_CODE,\n");
        sql.append("       D.POSITION_NAME,\n");
        sql.append("       D.AVAILABLE_AMOUNT\n");
        sql.append("  FROM PT_BU_STOCK A ,\n");
        sql.append("       PT_BA_INFO B, PT_BU_STOCK_DTL D \n");
        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("UNIT", DicConstant.JLDW);
        bs.setFieldDic("MIN_UNIT", DicConstant.JLDW);
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
     * 查询出库单配件
     *
     * @param page
     * @param user
     * @param conditions
     * @param OUT_ID       出库单ID
     * @param WAREHOUSE_ID 仓库ID
     * @return
     * @throws Exception
     */
    public BaseResultSet searchOutBillPart(PageManager page, User user, String conditions, String OUT_ID, String WAREHOUSE_ID) throws Exception {
//        StringBuilder wheres = new StringBuilder(conditions);
//        wheres.append("   AND A.OUT_ID = " + OUT_ID + "\n");
//        wheres.append("   AND D.WAREHOUSE_ID = " + WAREHOUSE_ID + "\n");
//        wheres.append("   AND A.PART_ID = B.PART_ID\n");
//        wheres.append("   AND A.PART_ID = C.PART_ID(+)\n");
//        wheres.append("   AND A.SUPPLIER_ID = C.SUPPLIER_ID(+)\n");
//        wheres.append("   AND A.PART_ID = D.PART_ID\n");
//        wheres.append("   AND A.SUPPLIER_ID = D.SUPPLIER_ID\n");
//        wheres.append("   AND A.POSITION_ID = C.POSITION_ID(+)\n");
//        wheres.append("   ORDER BY A.PART_CODE ASC\n");
//        page.setFilter(wheres.toString());
//
//        //定义返回结果集
//        BaseResultSet bs = null;
//        StringBuilder sql = new StringBuilder();
//        sql.append("SELECT A.DETAIL_ID,\n");
//        sql.append("       A.PART_ID,\n");
//        sql.append("       A.PART_CODE,\n");
//        sql.append("       A.PART_NAME,\n");
//        sql.append("       A.SUPPLIER_ID,\n");
//        sql.append("       A.SUPPLIER_CODE,\n");
//        sql.append("       A.SUPPLIER_NAME,\n");
//        sql.append("       B.UNIT,\n");
//        sql.append("       B.MIN_PACK,\n");
//        sql.append("       B.MIN_UNIT,\n");
//        sql.append("       NVL(A.OUT_AMOUNT,0) OUT_AMOUNT,\n");
//        sql.append("       C.AVAILABLE_AMOUNT,\n");
//        sql.append("       C.AREA_ID,\n");
//        sql.append("       C.AREA_CODE,\n");
//        sql.append("       C.AREA_NAME,\n");
//        sql.append("       C.POSITION_ID,\n");
//        sql.append("       C.POSITION_CODE,\n");
//        sql.append("       C.POSITION_NAME,\n");
//        sql.append("       D.AMOUNT\n");
//        sql.append("  FROM PT_BU_STOCK_OUT_DTL A,\n");
//        sql.append("       PT_BA_INFO B,\n");
//        sql.append("       PT_BU_STOCK_DTL C,\n");
//        sql.append("       PT_BU_STOCK D");

    	StringBuilder wheres = new StringBuilder(conditions);
        BaseResultSet bs = null;
    	wheres.append("   AND A.OUT_ID = " + OUT_ID + "\n");
        wheres.append("   AND A.PART_ID = C.PART_ID\n");
        wheres.append("   AND A.SUPPLIER_ID = C.SUPPLIER_ID\n");
        wheres.append("   AND A.POSITION_ID = C.POSITION_ID\n");
        wheres.append(" GROUP BY A.PART_ID,\n" );
        wheres.append("          A.PART_CODE,\n" );
        wheres.append("          A.PART_NAME,\n" );
        wheres.append("          A.SUPPLIER_ID,\n" );
        wheres.append("          A.SUPPLIER_CODE,\n" );
        wheres.append("          A.SUPPLIER_NAME,\n" );
        wheres.append("          NVL(A.REMARKS,'')\n" );
        wheres.append(" ORDER BY A.PART_CODE ASC"); 
        page.setFilter(wheres.toString());
        StringBuffer sql1= new StringBuffer();
        sql1.append("SELECT A.PART_ID,\n" );
        sql1.append("       A.PART_CODE,\n" );
        sql1.append("       A.PART_NAME,\n" );
        sql1.append("       A.SUPPLIER_ID,\n" );
        sql1.append("       A.SUPPLIER_CODE,\n" );
        sql1.append("       A.SUPPLIER_NAME,\n" );
        sql1.append("       NVL(A.REMARKS,'') REMARKS,\n" );
        sql1.append("       WM_CONCAT(C.POSITION_ID) POSITION_IDS,\n" );
        sql1.append("       WM_CONCAT(C.POSITION_CODE) POSITION_CODES,\n" );
        sql1.append("       WM_CONCAT(C.POSITION_NAME) POSITION_NAMES,\n" );
        sql1.append("       WM_CONCAT(C.AMOUNT) AMOUNTS,\n" );
        sql1.append("       WM_CONCAT(C.OCCUPY_AMOUNT) OCCUPY_AMOUNTS,\n" );
        sql1.append("       WM_CONCAT(C.AVAILABLE_AMOUNT) AVAILABLE_AMOUNTS,\n" );
        sql1.append("       WM_CONCAT(A.OUT_AMOUNT) OUT_AMOUNTS\n" );
        sql1.append("  FROM PT_BU_STOCK_OUT_DTL A, PT_BU_STOCK_DTL C\n" );   
        bs = factory.select(sql1.toString(), page);
        //执行方法，不需要传递conn参数
        bs = factory.select(sql1.toString(), page);
        bs.setFieldDic("UNIT", DicConstant.JLDW);
        bs.setFieldDic("MIN_UNIT", DicConstant.JLDW);
        return bs;
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
     * 解锁库存(根据出库单明细)
     *
     * @param WAREHOUSE_ID 仓库ID
     * @param DETAIL_ID       出库单明细ID
     * @param user
     * @return
     * @throws Exception
     */
    public boolean unLockStockByOutBillDtl(String WAREHOUSE_ID, String DETAIL_ID, User user)
            throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE PT_BU_STOCK A\n");
        sql.append("   SET A.OCCUPY_AMOUNT    = OCCUPY_AMOUNT -\n");
        sql.append("                            (SELECT B.OUT_AMOUNT\n");
        sql.append("                               FROM PT_BU_STOCK_OUT_DTL B\n");
        sql.append("                              WHERE B.DETAIL_ID = " + DETAIL_ID + "\n");
        sql.append("                                AND B.SUPPLIER_ID = A.SUPPLIER_ID\n");
        sql.append("                                AND B.PART_ID = A.PART_ID),\n");
        sql.append("       A.AVAILABLE_AMOUNT = AVAILABLE_AMOUNT +\n");
        sql.append("                            (SELECT B.OUT_AMOUNT\n");
        sql.append("                               FROM PT_BU_STOCK_OUT_DTL B\n");
        sql.append("                              WHERE B.DETAIL_ID = " + DETAIL_ID + "\n");
        sql.append("                                AND B.SUPPLIER_ID = A.SUPPLIER_ID\n");
        sql.append("                                AND B.PART_ID = A.PART_ID),\n");
        sql.append("       A.UPDATE_USER      = '" + user.getAccount() + "',\n");
        sql.append("       A.UPDATE_TIME      = SYSDATE\n");
        sql.append(" WHERE 1 = 1\n");
        sql.append("   AND A.WAREHOUSE_ID = " + WAREHOUSE_ID + "\n");
        sql.append("   AND EXISTS (SELECT 1\n");
        sql.append("          FROM PT_BU_STOCK_OUT_DTL B\n");
        sql.append("         WHERE B.DETAIL_ID = " + DETAIL_ID + "\n");
        sql.append("           AND B.SUPPLIER_ID = A.SUPPLIER_ID\n");
        sql.append("           AND B.OUT_AMOUNT > 0\n");
        sql.append("           AND B.PART_ID = A.PART_ID)\n");


        return factory.update(sql.toString(), null);
    }

    /**
     * 解锁库存明细(根据出库单明细)
     *
     * @param DETAIL_ID 出库单明细ID
     * @param user
     * @return
     * @throws Exception
     */
    public boolean unLockStockDtlByOutBillDtl(String DETAIL_ID, User user)
            throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE PT_BU_STOCK_DTL A\n");
        sql.append("   SET A.OCCUPY_AMOUNT    = OCCUPY_AMOUNT -\n");
        sql.append("                            (SELECT B.OUT_AMOUNT\n");
        sql.append("                               FROM PT_BU_STOCK_OUT_DTL B\n");
        sql.append("                              WHERE B.DETAIL_ID = " + DETAIL_ID + "\n");
        sql.append("                                AND B.POSITION_ID = A.POSITION_ID\n");
        sql.append("                                AND B.SUPPLIER_ID = A.SUPPLIER_ID\n");
        sql.append("                                AND B.PART_ID = A.PART_ID),\n");
        sql.append("       A.AVAILABLE_AMOUNT = AVAILABLE_AMOUNT +\n");
        sql.append("                            (SELECT B.OUT_AMOUNT\n");
        sql.append("                               FROM PT_BU_STOCK_OUT_DTL B\n");
        sql.append("                              WHERE B.DETAIL_ID = " + DETAIL_ID + "\n");
        sql.append("                                AND B.POSITION_ID = A.POSITION_ID\n");
        sql.append("                                AND B.SUPPLIER_ID = A.SUPPLIER_ID\n");
        sql.append("                                AND B.PART_ID = A.PART_ID),\n");
        sql.append("       A.UPDATE_USER      = '" + user.getAccount() + "',\n");
        sql.append("       A.UPDATE_TIME      = SYSDATE\n");
        sql.append(" WHERE 1 = 1\n");
        sql.append("   AND EXISTS (SELECT 1\n");
        sql.append("          FROM PT_BU_STOCK_OUT_DTL B\n");
        sql.append("         WHERE B.DETAIL_ID = " + DETAIL_ID + "\n");
        sql.append("           AND B.POSITION_ID = A.POSITION_ID\n");
        sql.append("           AND B.SUPPLIER_ID = A.SUPPLIER_ID\n");
        sql.append("           AND B.OUT_AMOUNT > 0\n");
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
     * 校验出库单明细是否合法(不包含出库数量为0的记录)
     *
     * @param OUT_ID 出库单ID
     * @return
     * @throws Exception
     */
    public Boolean checkOutBillDtlIsValid(String OUT_ID) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT 1\n");
        sql.append("  FROM PT_BU_STOCK_OUT_DTL A\n");
        sql.append(" WHERE A.OUT_ID = " + OUT_ID + "\n");
        sql.append("   AND A.OUT_AMOUNT = 0\n");

        QuerySet qs = factory.select(null, sql.toString());
        if (qs.getRowCount() > 0) {
            return false;
        }
        return true;
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
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean deleteOutBillDtl(PtBuStockOutDtlVO vo) throws Exception {
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
        sql.append("         A.OUT_AMOUNT,\n");
        sql.append("         '" + user.getAccount() + "'\n");
        sql.append("    FROM PT_BU_STOCK_OUT_DTL A, PT_BU_STOCK_OUT B\n");
        sql.append("   WHERE A.OUT_ID = B.OUT_ID\n");
        sql.append("   AND A.OUT_ID = " + OUT_ID + "\n");

        return factory.update(sql.toString(), null);
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

    /**
     * 生成出库单号
     * @param WAREHOUSE_CODE 仓库代码
     * @return
     * @throws Exception
     */
    public String createOutBillNo(String WAREHOUSE_CODE) throws Exception{
        return PartOddNumberUtil.getMoveAndOtherOutBillNo(factory,WAREHOUSE_CODE);
    }
    
    
    
    public QuerySet getPartInfo (String PART_ID,String POSITION_ID,String OUT_AMOUNT) throws Exception {

    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.PART_ID,\n" );
    	sql.append("       T.PART_CODE,\n" );
    	sql.append("       T.PART_NAME,\n" );
    	sql.append("       T.SUPPLIER_ID,\n" );
    	sql.append("       T.SUPPLIER_CODE,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("       T.DTL_ID,\n" );
    	sql.append("       T1.SALE_PRICE,\n" );
    	sql.append("       T1.SALE_PRICE *"+OUT_AMOUNT+" AS AMOUNT\n" );
    	sql.append("  FROM PT_BU_STOCK_DTL T, PT_BA_INFO T1\n" );
    	sql.append(" WHERE T.PART_ID = T1.PART_ID\n" );
    	sql.append("   AND T.PART_ID = "+PART_ID+"\n" );
    	sql.append("   AND T.POSITION_ID = "+POSITION_ID+"\n");

        return factory.select(null, sql.toString());
    }
    /**
     * 更新库存明细表占用数量和可用数量
     *
     * @param stockDtlId 库存明细ID
     * @param amount 出库数量
     * @param user
     * @return
     * @throws Exception
     */
    public boolean updateStockDtlAva(String stockDtlId,String amount,User user,String account)
            throws Exception {
    	StringBuffer sql1= new StringBuffer();
    	sql1.append("UPDATE PT_BU_STOCK_DTL D\n" );
    	sql1.append("   SET D.OCCUPY_AMOUNT    = NVL(D.OCCUPY_AMOUNT, 0) + "+amount+",\n" );
    	sql1.append("       D.AVAILABLE_AMOUNT = NVL(D.AVAILABLE_AMOUNT, 0) - "+amount+",\n" );
    	sql1.append("       D.UPDATE_USER='"+account+"',\n" );
    	sql1.append("       D.UPDATE_TIME=SYSDATE\n" );
    	sql1.append(" WHERE D.DTL_ID = "+stockDtlId);
        return factory.update(sql1.toString(), null);
    }
    /**
     * 更新库存主表占用数量和可用数量
     *
     * @param stockDtlId 库存明细ID
     * @param amount 出库数量
     * @param user
     * @return
     * @throws Exception
     */
    public boolean updateStockAva(String stockDtlId,String amount,User user,String account)
            throws Exception {
    	StringBuffer sql1= new StringBuffer();
    	sql1.append("UPDATE PT_BU_STOCK S\n" );
    	sql1.append("   SET S.OCCUPY_AMOUNT    = NVL(OCCUPY_AMOUNT, 0) + "+amount+",\n" );
    	sql1.append("       S.AVAILABLE_AMOUNT = NVL(S.AVAILABLE_AMOUNT, 0) - "+amount+",\n" );
    	sql1.append("       S.UPDATE_USER      = '"+account+"',\n" );
    	sql1.append("       S.UPDATE_TIME      = SYSDATE\n" );
    	sql1.append(" WHERE S.STOCK_ID =\n" );
    	sql1.append("       (SELECT D.STOCK_ID FROM PT_BU_STOCK_DTL D WHERE D.DTL_ID = "+stockDtlId+")");

        return factory.update(sql1.toString(), null);
    }
    public boolean insertOutBillDtl(String outId,String stockDtlId,String amount,User user,String account)
            throws Exception {
    	StringBuffer sql1= new StringBuffer();
    	sql1.append("INSERT INTO PT_BU_STOCK_OUT_DTL\n" );
    	sql1.append("  (DETAIL_ID,\n" );
    	sql1.append("   OUT_ID,\n" );
    	sql1.append("   PART_ID,\n" );
    	sql1.append("   PART_CODE,\n" );
    	sql1.append("   PART_NAME,\n" );
    	sql1.append("   SUPPLIER_ID,\n" );
    	sql1.append("   SUPPLIER_NAME,\n" );
    	sql1.append("   SUPPLIER_CODE,\n" );
    	sql1.append("   POSITION_ID,\n" );
    	sql1.append("   OUT_AMOUNT,\n" );
    	sql1.append("   CREATE_USER,\n" );
    	sql1.append("   CREATE_TIME)\n" );
    	sql1.append("  SELECT F_GETID(),\n" );
    	sql1.append("         "+outId+",\n" );
    	sql1.append("         SD.PART_ID,\n" );
    	sql1.append("         SD.PART_CODE,\n" );
    	sql1.append("         SD.PART_NAME,\n" );
    	sql1.append("         SD.SUPPLIER_ID,\n" );
    	sql1.append("         SD.SUPPLIER_NAME,\n" );
    	sql1.append("         SD.SUPPLIER_CODE,\n" );
    	sql1.append("         SD.POSITION_ID,\n" );
    	sql1.append("         "+amount+",\n" );
    	sql1.append("         '"+account+"',\n" );
    	sql1.append("         SYSDATE\n" );
    	sql1.append("    FROM PT_BU_STOCK_DTL SD\n" );
    	sql1.append("   WHERE SD.DTL_ID = "+stockDtlId+"\n");
        return factory.update(sql1.toString(), null);
    }
    public boolean updateLockStockDtlByOutBillDtl(String partId,String supplierId,String positionId,String yAmount,String outAmount,User user)
            throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE PT_BU_STOCK_DTL A\n");
//		sql.append("   SET A.OCCUPY_AMOUNT    = OCCUPY_AMOUNT -"+yAmount+"+"+outAmount+",\n");
		sql.append("   SET A.OCCUPY_AMOUNT    = OCCUPY_AMOUNT -"+yAmount+",\n");
//		sql.append("       A.AMOUNT = AMOUNT +"+yAmount+"-"+outAmount+",\n");
		sql.append("       A.AMOUNT = AMOUNT -"+outAmount+",\n");
		sql.append("       A.AVAILABLE_AMOUNT = AVAILABLE_AMOUNT +"+yAmount+"-"+outAmount+",\n");
//		sql.append("       A.AVAILABLE_AMOUNT = AVAILABLE_AMOUNT -"+outAmount+",\n");
		sql.append("       A.UPDATE_USER      = '" + user.getAccount() + "',\n");
		sql.append("       A.UPDATE_TIME      = SYSDATE\n");
		sql.append(" WHERE A.PART_ID ="+partId+"\n");
		sql.append("       AND A.POSITION_ID ="+positionId+"\n");
		sql.append("       AND A.SUPPLIER_ID = "+supplierId+"\n");
        return factory.update(sql.toString(), null);
    }
    public boolean updateLockStockByOutBillDtl(String partId,String supplierId,String positionId,String yAmount,String outAmount,User user)
            throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE PT_BU_STOCK A\n");
		sql.append("   SET A.OCCUPY_AMOUNT    = OCCUPY_AMOUNT -"+yAmount+",\n");
//		sql.append("   SET A.OCCUPY_AMOUNT    = OCCUPY_AMOUNT -"+yAmount+"+"+outAmount+",\n");
		sql.append("       A.AMOUNT = AMOUNT -"+outAmount+",\n");
		sql.append("       A.AVAILABLE_AMOUNT = AVAILABLE_AMOUNT +"+yAmount+"-"+outAmount+",\n");
		sql.append("       A.UPDATE_USER      = '" + user.getAccount() + "',\n");
		sql.append("       A.UPDATE_TIME      = SYSDATE\n");
		sql.append(" WHERE 1 = 1\n");
		sql.append("   AND EXISTS (SELECT 1\n");
		sql.append("          FROM PT_BU_STOCK_DTL B\n");
		sql.append("         WHERE B.STOCK_ID = A.STOCK_ID \n");
		sql.append("           AND B.PART_ID = "+partId+"\n");
		sql.append("           AND B.SUPPLIER_ID ="+supplierId+"\n");
		sql.append("           AND B.POSITION_ID ="+positionId+")\n");
        return factory.update(sql.toString(), null);
    }
    public boolean updateOutBillDtl1(String outId,String partId,String supplierId,String positionId,String amount,String remark,User user)
            throws Exception {
    	StringBuffer sql1= new StringBuffer();
    	sql1.append("UPDATE PT_BU_STOCK_OUT_DTL D\n" );
    	sql1.append("   SET D.OUT_AMOUNT  = "+amount+",\n" );
    	sql1.append("       D.REMARKS     = '"+remark+"',\n" );
    	sql1.append("       D.UPDATE_USER = '"+user.getAccount()+"',\n" );
    	sql1.append("       D.UPDATE_TIME = SYSDATE\n" );
    	sql1.append(" WHERE D.OUT_ID = "+outId+"\n" );
    	sql1.append("   AND D.PART_ID = "+partId+"\n" );
    	sql1.append("   AND D.SUPPLIER_ID = "+supplierId+"\n" );
    	sql1.append("   AND D.POSITION_ID = "+positionId);
        return factory.update(sql1.toString(), null);
    }
    public boolean unLockStockDtlByOutBillDtl(String outId, String partId,String supplierId,User user)
            throws Exception {
        StringBuffer sql1= new StringBuffer();
        sql1.append("MERGE INTO PT_BU_STOCK_DTL A\n" );
        sql1.append("USING (SELECT B.PART_ID, B.SUPPLIER_ID, B.POSITION_ID, B.OUT_AMOUNT\n" );
        sql1.append("         FROM PT_BU_STOCK_OUT_DTL B\n" );
        sql1.append("        WHERE B.OUT_ID = "+outId+"\n" );
        sql1.append("          AND B.PART_ID = "+partId+"\n" );
        sql1.append("          AND B.SUPPLIER_ID = "+supplierId+"\n" );
        sql1.append("          AND B.OUT_AMOUNT > 0) C\n" );
        sql1.append("ON (A.PART_ID = C.PART_ID AND A.SUPPLIER_ID = C.SUPPLIER_ID AND A.POSITION_ID = C.POSITION_ID)\n" );
        sql1.append("WHEN MATCHED THEN\n" );
        sql1.append("  UPDATE\n" );
        sql1.append("     SET A.OCCUPY_AMOUNT    = A.OCCUPY_AMOUNT - C.OUT_AMOUNT,\n" );
        sql1.append("         A.AVAILABLE_AMOUNT = A.AVAILABLE_AMOUNT + C.OUT_AMOUNT,\n" );
        sql1.append("         A.UPDATE_USER      = '"+user.getAccount()+"',\n" );
        sql1.append("         A.UPDATE_TIME      = SYSDATE");

        return factory.update(sql1.toString(), null);
    }
    public boolean unLockStockByOutBillDtl(String outId, String partId,String supplierId,User user)
            throws Exception {
    	StringBuffer sql1= new StringBuffer();
    	sql1.append("MERGE INTO PT_BU_STOCK A\n" );
    	sql1.append("USING (SELECT SUM(B.OUT_AMOUNT) AS OUT_AMOUNT, D.STOCK_ID\n" );
    	sql1.append("         FROM PT_BU_STOCK_OUT_DTL B, PT_BU_STOCK_DTL D\n" );
    	sql1.append("        WHERE B.PART_ID = D.PART_ID\n" );
    	sql1.append("          AND B.SUPPLIER_ID = D.SUPPLIER_ID\n" );
    	sql1.append("          AND B.POSITION_ID = D.POSITION_ID\n" );
    	sql1.append("          AND B.OUT_ID = "+outId+"\n" );
    	sql1.append("          AND B.PART_ID = "+partId+"\n" );
    	sql1.append("          AND B.SUPPLIER_ID = "+supplierId+"\n" );
    	sql1.append("          AND B.OUT_AMOUNT > 0  GROUP BY D.STOCK_ID ) C\n" );
    	sql1.append("ON (A.STOCK_ID = C.STOCK_ID)\n" );
    	sql1.append("WHEN MATCHED THEN\n" );
    	sql1.append("  UPDATE\n" );
    	sql1.append("     SET A.OCCUPY_AMOUNT    = A.OCCUPY_AMOUNT - C.OUT_AMOUNT,\n" );
    	sql1.append("         A.AVAILABLE_AMOUNT = A.AVAILABLE_AMOUNT + C.OUT_AMOUNT,\n" );
    	sql1.append("         A.UPDATE_USER      = '"+user.getAccount()+"',\n" );
    	sql1.append("         A.UPDATE_TIME      = SYSDATE");
        return factory.update(sql1.toString(), null);
    }
    public boolean deleteOutBillDtl1(String outId,String partId,String supplierId) throws Exception {
    	StringBuffer sql1= new StringBuffer();
    	sql1.append("DELETE PT_BU_STOCK_OUT_DTL D\n" );
    	sql1.append(" WHERE D.OUT_ID = "+outId+"\n" );
    	sql1.append("   AND D.PART_ID = "+partId+"\n" );
    	sql1.append("   AND D.SUPPLIER_ID = "+supplierId);
        return factory.update(sql1.toString(), null);
    }
    
    public QuerySet whId(String outId)
            throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT WAREHOUSE_ID FROM PT_BU_STOCK_OUT WHERE OUT_ID = "+outId+"");
        return factory.select(null, sql.toString());
    }
    public QuerySet checkPlanPrice(String dtlIds)
            throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT to_char(wm_concat( PART_CODE)) CODES FROM PT_BA_INFO WHERE PART_ID IN (SELECT PART_ID FROM PT_BU_STOCK_DTL WHERE DTL_ID IN("+dtlIds+")) AND NVL(PLAN_PRICE,0)=0\n" );
        return factory.select(null, sql.toString());
    }
    public QuerySet checkLock1(String dtlIds,String warehouseId)
            throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT to_char(wm_concat( PART_CODE)) CODES FROM PT_BU_STOCK WHERE PART_ID IN (SELECT PART_ID FROM PT_BU_STOCK_DTL WHERE DTL_ID IN("+dtlIds+")) AND STOCK_STATUS = "+DicConstant.KCZT_02+" AND WAREHOUSE_ID = "+warehouseId+"\n" );
        return factory.select(null, sql.toString());
    }
    public QuerySet checkLock2(String dtlIds,String warehouseId)
            throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT to_char(wm_concat( PART_CODE)) CODES FROM PT_BU_STOCK WHERE PART_ID IN (SELECT PART_ID FROM PT_BU_STOCK_DTL WHERE DTL_ID IN("+dtlIds+")) AND STOCK_STATUS = "+DicConstant.KCZT_03+" AND WAREHOUSE_ID = "+warehouseId+"\n" );
        return factory.select(null, sql.toString());
    }
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
	   	   sql.append("           "+saleId+",\n" );
	   	   sql.append("           '"+url+"',\n" );
	   	   sql.append("           -"+saleCount+",\n" );
	   	   sql.append("           -"+saleCount+",\n" );
	   	   sql.append("           '',\n" );
	   	   sql.append("           '"+user.getAccount()+"',\n" );
	   	   sql.append("           SYSDATE,\n" );
	   	   sql.append("           "+nPartId+",\n" );
	   	   sql.append("           "+user.getOrgId()+")");
	   	   factory.update(sql.toString(), null);
	      }
	    public QuerySet getPartId (String detailId) throws Exception {

	        StringBuilder sql= new StringBuilder();
	        sql.append("SELECT PART_ID FROM PT_BU_STOCK_DTL WHERE DTL_ID = "+detailId+"\n" );
	        return factory.select(null, sql.toString());
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