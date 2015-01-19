package com.org.dms.dao.part.storageMng.stockOutMng;

import java.sql.SQLException;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.common.PartOddNumberUtil;
import com.org.dms.vo.part.PtBuStockOutDtlVO;
import com.org.dms.vo.part.PtBuStockOutVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.SequenceUtil;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 移库出库Dao
 *
 * @user : lichuang
 * @date : 2014-08-06
 */
public class MoveStockOutMngDao extends BaseDAO {
    //定义instance
    public static final MoveStockOutMngDao getInstance(ActionContext atx) {
        MoveStockOutMngDao dao = new MoveStockOutMngDao();
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
    public BaseResultSet searchOutBill(PageManager page, User user, String conditions) throws Exception {
        StringBuilder wheres = new StringBuilder(conditions);
        wheres.append("   AND A.RECEIVE_WAREHOUSE = B.WAREHOUSE_ID\n");
        wheres.append("   AND A.OUT_STATUS = " + DicConstant.CKDZT_01 + "\n");
        wheres.append("   AND A.OUT_TYPE = " + DicConstant.CKLX_03 + "\n");
        wheres.append("   AND A.STATUS = " + DicConstant.YXBS_01 + "\n");
        wheres.append("   AND A.OEM_COMPANY_ID=" + user.getOemCompanyId() + "\n");
        wheres.append("   AND A.WAREHOUSE_ID IN\n" );
        wheres.append("    (SELECT DISTINCT K.WAREHOUSE_ID\n" );
        wheres.append("       FROM PT_BA_WAREHOUSE_KEEPER K\n" );
        wheres.append("      WHERE K.USER_ACCOUNT ='"+user.getAccount()+"')");
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
        sql.append("       B.WAREHOUSE_ID DES_WAREHOUSE_ID,\n");
        sql.append("       B.WAREHOUSE_CODE DES_WAREHOUSE_CODE,\n");
        sql.append("       B.WAREHOUSE_NAME DES_WAREHOUSE_NAME,\n");
        sql.append("       B.WAREHOUSE_TYPE DES_WAREHOUSE_TYPE,\n");
        sql.append("       A.REMARKS,\n");
        sql.append("       A.LINK_MAN,\n");
        sql.append("       A.MOVE_MAN,\n");
        sql.append("       A.MOVE_DATE,\n");
        sql.append("       A.LINK_TEL\n");
        sql.append("  FROM PT_BU_STOCK_OUT A,PT_BA_WAREHOUSE B");

        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("OUT_TYPE", DicConstant.CKLX);
        bs.setFieldDateFormat("MOVE_DATE", "yyyy-MM-dd HH:mm");
        return bs;
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
        StringBuilder wheres = new StringBuilder(conditions);
        BaseResultSet bs = null;
       /*wheres.append("   AND A.OUT_ID = " + OUT_ID + "\n");
        wheres.append("   AND D.WAREHOUSE_ID = " + WAREHOUSE_ID + "\n");
        wheres.append("   AND A.PART_ID = B.PART_ID\n");
        wheres.append("   AND A.PART_ID = C.PART_ID(+)\n");
        wheres.append("   AND A.SUPPLIER_ID = C.SUPPLIER_ID(+)\n");
        wheres.append("   AND A.PART_ID = D.PART_ID\n");
        wheres.append("   AND A.SUPPLIER_ID = D.SUPPLIER_ID\n");
        wheres.append("   AND A.POSITION_ID = C.POSITION_ID(+)\n");
        wheres.append("   ORDER BY A.PART_CODE ASC\n");
        page.setFilter(wheres.toString());

        //定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT A.DETAIL_ID,\n");
        sql.append("       A.PART_ID,\n");
        sql.append("       A.PART_CODE,\n");
        sql.append("       A.PART_NAME,\n");
        sql.append("       A.SUPPLIER_ID,\n");
        sql.append("       A.SUPPLIER_CODE,\n");
        sql.append("       A.SUPPLIER_NAME,\n");
        sql.append("       B.UNIT,\n");
        sql.append("       B.MIN_PACK,\n");
        sql.append("       B.MIN_UNIT,\n");
        sql.append("       NVL(A.OUT_AMOUNT,0) OUT_AMOUNT,\n");
        sql.append("       C.AVAILABLE_AMOUNT,\n");
        sql.append("       C.AREA_ID,\n");
        sql.append("       C.AREA_CODE,\n");
        sql.append("       C.AREA_NAME,\n");
        sql.append("       C.POSITION_ID,\n");
        sql.append("       C.POSITION_CODE,\n");
        sql.append("       C.POSITION_NAME,\n");
        sql.append("       D.AMOUNT\n");
        sql.append("  FROM PT_BU_STOCK_OUT_DTL A,\n");
        sql.append("       PT_BA_INFO B,\n");
        sql.append("       PT_BU_STOCK_DTL C,\n");
        sql.append("       PT_BU_STOCK D");

        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("UNIT", DicConstant.JLDW);
        bs.setFieldDic("MIN_UNIT", DicConstant.JLDW);*/
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
    public BaseResultSet searchPart(PageManager page, User user, String conditions, String outId, String warehouseId) throws Exception {
        String wheres = conditions;

        //过滤掉当前出库单已存在的配件
        wheres += " AND NVL(D.AVAILABLE_AMOUNT,0) > 0\n";
        wheres += " AND A.WAREHOUSE_ID = "+warehouseId+"\n";
        wheres += " AND A.PART_ID = B.PART_ID AND E.USER_ACCOUNT = '"+user.getAccount()+"'\n";
        wheres += " AND A.STOCK_ID = D.STOCK_ID AND B.PART_ID = D.PART_ID AND A.WAREHOUSE_ID = E.WAREHOUSE_ID AND A.PART_ID= E.PART_ID \n";
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
        sql.append("       PT_BA_INFO B, PT_BU_STOCK_DTL D ,PT_BA_WAREHOUSE_KEEPER E\n");
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
     * 重复订单验证(相同出库库区和收货库区的订单)
     * 
     * @param warehouseId 出库仓库ID+目标仓库ID
     * @param pUser 
     * @return 结果集
     * @throws Exception
     */
    public QuerySet insertOutBillCheck (String warehouseId) throws Exception {

        StringBuilder sql= new StringBuilder();
        sql.append("SELECT 1 FROM PT_BU_STOCK_OUT WHERE WAREHOUSE_ID || RECEIVE_WAREHOUSE = '" + warehouseId + "' AND OUT_STATUS="+DicConstant.CKDZT_01+"\n" );
        return factory.select(null, sql.toString());
    }

    /**
     * 新增出库单
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean insertOutBill(PtBuStockOutVO vo) throws Exception {
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
     * 新增出库单明细
     *
     * @param outId 移库出库单主键
     * @param stockDtlId 库存明细ID
     * @param amount 出库数量
     * @param user
     * @return
     * @throws Exception
     */
    public boolean insertOutBillDtl(String outId,String stockDtlId,String amount,User user)
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
    	sql1.append("         '"+user.getAccount()+"',\n" );
    	sql1.append("         SYSDATE\n" );
    	sql1.append("    FROM PT_BU_STOCK_DTL SD\n" );
    	sql1.append("   WHERE SD.DTL_ID = "+stockDtlId+"\n");
        return factory.update(sql1.toString(), null);
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
    public boolean updateStockDtlAva(String stockDtlId,String amount,User user)
            throws Exception {
    	StringBuffer sql1= new StringBuffer();
    	sql1.append("UPDATE PT_BU_STOCK_DTL D\n" );
    	sql1.append("   SET D.OCCUPY_AMOUNT    = NVL(D.OCCUPY_AMOUNT, 0) + "+amount+",\n" );
    	sql1.append("       D.AVAILABLE_AMOUNT = NVL(D.AVAILABLE_AMOUNT, 0) - "+amount+",\n" );
    	sql1.append("       D.UPDATE_USER='"+user.getAccount()+"',\n" );
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
    public boolean updateStockAva(String stockDtlId,String amount,User user)
            throws Exception {
    	StringBuffer sql1= new StringBuffer();
    	sql1.append("UPDATE PT_BU_STOCK S\n" );
    	sql1.append("   SET S.OCCUPY_AMOUNT    = NVL(S.OCCUPY_AMOUNT, 0) + "+amount+",\n" );
    	sql1.append("       S.AVAILABLE_AMOUNT = NVL(S.AVAILABLE_AMOUNT, 0) - "+amount+",\n" );
    	sql1.append("       S.UPDATE_USER      = '"+user.getAccount()+"',\n" );
    	sql1.append("       S.UPDATE_TIME      = SYSDATE\n" );
    	sql1.append(" WHERE S.STOCK_ID =\n" );
    	sql1.append("       (SELECT D.STOCK_ID FROM PT_BU_STOCK_DTL D WHERE D.DTL_ID = "+stockDtlId+")");

        return factory.update(sql1.toString(), null);
    }
    /**
     * 根据目标仓库类型处理，插入操作人与零件关系
     * @param outId 出库主键
     * @return
     * @throws Exception
     */
    public boolean updateStockInKeeper(String outId)
            throws Exception {
    	StringBuffer sql1= new StringBuffer();
    	sql1.append("INSERT INTO PT_BU_STOCK_IN_KEEPER\n" );
    	sql1.append("  (ID, OUT_ID, USER_ACCOUNT)\n" );
    	sql1.append("  SELECT F_GETID(), A.OUT_ID, A.USER_ACCOUNT\n" );
    	sql1.append("    FROM (SELECT DISTINCT D.OUT_ID, W.USER_ACCOUNT\n" );
    	sql1.append("            FROM PT_BU_STOCK_OUT_DTL D, PT_BA_WAREHOUSE_KEEPER W\n" );
    	sql1.append("           WHERE D.OUT_ID = "+outId+"\n" );
    	sql1.append("             AND D.PART_ID = W.PART_ID) A");
        return factory.update(sql1.toString(), null);
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
     * 修改出库单明细
     *
     * @param outId 出库单ID
     * @param partId 配件ID
     * @param supplierId 供应商ID
     * @param positionId 货位ID
     * @param amount 出库数量
     * @param remark 备注
     * @param user
     * @return
     * @throws Exception
     */
    public boolean updateOutBillDtl(String outId,String partId,String supplierId,String positionId,String amount,String remark,User user)
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
        StringBuffer sql1= new StringBuffer();
        sql1.append("MERGE INTO PT_BU_STOCK A\n" );
        sql1.append("USING (SELECT SUM(B.OUT_AMOUNT) AS OUT_AMOUNT, D.STOCK_ID\n" );
        sql1.append("         FROM PT_BU_STOCK_OUT_DTL B, PT_BU_STOCK_DTL D\n" );
        sql1.append("        WHERE B.PART_ID = D.PART_ID\n" );
        sql1.append("          AND B.SUPPLIER_ID = D.SUPPLIER_ID\n" );
        sql1.append("          AND B.POSITION_ID = D.POSITION_ID\n" );
        sql1.append("          AND B.OUT_ID = "+OUT_ID+"\n" );
        sql1.append("          AND B.OUT_AMOUNT > 0\n" );
        sql1.append("        GROUP BY D.STOCK_ID) C\n" );
        sql1.append("ON (A.STOCK_ID = C.STOCK_ID AND A.WAREHOUSE_ID = "+WAREHOUSE_ID+")\n" );
        sql1.append("WHEN MATCHED THEN\n" );
        sql1.append("  UPDATE\n" );
        sql1.append("     SET A.AMOUNT    = A.AMOUNT - C.OUT_AMOUNT,\n" );
        sql1.append("         A.OCCUPY_AMOUNT = A.OCCUPY_AMOUNT - C.OUT_AMOUNT,\n" );
        sql1.append("         A.UPDATE_USER      = '"+user.getAccount()+"',\n" );
        sql1.append("         A.UPDATE_TIME      = SYSDATE");


        return factory.update(sql1.toString(), null);
    }

    /**
     * 解锁库存(根据出库单明细)
     *
     * @param outId 出库单ID
     * @param partId 配件ID
     * @param supplierId 供应商ID
     * @param user
     * @return
     * @throws Exception
     */
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
    /**
     * 更新库存冻结数量(可能增加或者减少,yAmount-outAmount)
     *
      * @param partId 配件ID
     * @param supplierId 供应商ID
     * @param positionId 货位ID
     * @param yAmount 原数量
     * @param outAmount 出库数量
     * @param user
     * @return
     * @throws Exception
     */
    public boolean updateLockStockByOutBillDtl(String partId,String supplierId,String positionId,String yAmount,String outAmount,User user)
            throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE PT_BU_STOCK A\n");
		sql.append("   SET A.OCCUPY_AMOUNT    = OCCUPY_AMOUNT -"+yAmount+"+"+outAmount+",\n");
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

    /**
     * 解锁库存明细(根据出库单明细)
     *
     * @param outId 出库单ID
     * @param partId 配件ID
     * @param supplierId 供应商ID
     * @param user
     * @return
     * @throws Exception
     */
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
   
    /**
     * 更新库存明细冻结数量(可能增加或者减少,yAmount-outAmount)
     *
     * @param partId 配件ID
     * @param supplierId 供应商ID
     * @param positionId 货位ID
     * @param yAmount 原数量
     * @param outAmount 出库数量
     * @param user
     * @return
     * @throws Exception
     */
    public boolean updateLockStockDtlByOutBillDtl(String partId,String supplierId,String positionId,String yAmount,String outAmount,User user)
            throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE PT_BU_STOCK_DTL A\n");
		sql.append("   SET A.OCCUPY_AMOUNT    = OCCUPY_AMOUNT -"+yAmount+"+"+outAmount+",\n");
		sql.append("       A.AVAILABLE_AMOUNT = AVAILABLE_AMOUNT +"+yAmount+"-"+outAmount+",\n");
		sql.append("       A.UPDATE_USER      = '" + user.getAccount() + "',\n");
		sql.append("       A.UPDATE_TIME      = SYSDATE\n");
		sql.append(" WHERE A.PART_ID ="+partId+"\n");
		sql.append("       AND A.POSITION_ID ="+positionId+"\n");
		sql.append("       AND A.SUPPLIER_ID = "+supplierId+"\n");
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
     * @param partId 配件ID
     * @param supplierId 供应商ID
     * @return
     * @throws Exception
     */
    public boolean deleteOutBillDtl(String outId,String partId,String supplierId) throws Exception {
    	StringBuffer sql1= new StringBuffer();
    	sql1.append("DELETE PT_BU_STOCK_OUT_DTL D\n" );
    	sql1.append(" WHERE D.OUT_ID = "+outId+"\n" );
    	sql1.append("   AND D.PART_ID = "+partId+"\n" );
    	sql1.append("   AND D.SUPPLIER_ID = "+supplierId);
        return factory.update(sql1.toString(), null);
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
        //sql.append("         '"+PartOddNumberUtil.getOutContinualNo(factory)+"',\n");
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
     *
     * @param WAREHOUSE_CODE 仓库代码
     * @return
     * @throws Exception
     */
    public String createOutBillNo(String WAREHOUSE_CODE) throws Exception {
        return PartOddNumberUtil.getMoveAndOtherOutBillNo(factory, WAREHOUSE_CODE);
    }
    public BaseResultSet movePartImpSearch(PageManager page ,String conditions,User user) throws SQLException{
		   BaseResultSet bs = null;
		   StringBuffer sql1= new StringBuffer();
		   sql1.append("SELECT T.TMP_ID,\n" );
		   sql1.append("       T.PART_CODE,\n" );
		   sql1.append("       T.SUPPLIER_CODE,\n" );
		   sql1.append("       T.POSITION_CODE,\n" );
		   sql1.append("       T.ROW_NUM,\n" );
		   sql1.append("       T.MOVE_COUNT\n" );
		   sql1.append("  FROM PT_BU_MOVE_OUT_PART_TMP T\n" );
		   sql1.append(" WHERE T.USER_ACCOUNT = '"+user.getAccount()+"'");
		   bs=factory.select(sql1.toString(), page);
		   return bs;
	 }
    /**
	    * 校验空的数据
	    * @param user
	    * @return
	    * @throws Exception
	    */
	   public QuerySet checkList1(User user)throws Exception{
		   StringBuffer sql1= new StringBuffer();
		   sql1.append("SELECT T.ROW_NUM\n" );
		   sql1.append("  FROM PT_BU_MOVE_OUT_PART_TMP T\n" );
		   sql1.append(" WHERE (T.PART_CODE IS NULL OR T.PART_CODE = '' OR T.SUPPLIER_CODE IS NULL OR\n" );
		   sql1.append("       T.SUPPLIER_CODE = '' OR T.POSITION_CODE IS NULL OR\n" );
		   sql1.append("       T.POSITION_CODE = '' OR T.MOVE_COUNT IS NULL)\n" );
		   sql1.append("   AND T.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
		   sql1.append(" ORDER BY ROW_NUM");
		   return factory.select(null, sql1.toString());
	   }
	   /**
	    * 校验不合法数据
	    * @param user
	    * @return
	    * @throws Exception
	    */
	   public QuerySet checkList2(User user)throws Exception{
		   StringBuffer sql1= new StringBuffer();
		   sql1.append("SELECT T.ROW_NUM, T.PART_CODE, T.SUPPLIER_CODE, T.POSITION_CODE\n" );
		   sql1.append("  FROM PT_BU_MOVE_OUT_PART_TMP T\n" );
		   sql1.append(" WHERE (NOT EXISTS\n" );
		   sql1.append("        (SELECT 1\n" );
		   sql1.append("           FROM PT_BA_INFO I\n" );
		   sql1.append("          WHERE I.PART_STATUS = 200601\n" );
		   sql1.append("            AND I.PART_CODE = T.PART_CODE) OR NOT EXISTS\n" );
		   sql1.append("        (SELECT 1\n" );
		   sql1.append("           FROM PT_BA_SUPPLIER S\n" );
		   sql1.append("          WHERE S.SUPPLIER_CODE = T.SUPPLIER_CODE AND S.PART_IDENTIFY = "+DicConstant.YXBS_01+") OR NOT EXISTS\n" );
		   sql1.append("        (SELECT 1\n" );
		   sql1.append("           FROM PT_BA_WAREHOUSE_POSITION P\n" );
		   sql1.append("          WHERE P.POSITION_CODE = T.POSITION_CODE))\n" );
		   sql1.append("   AND T.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
		   sql1.append(" ORDER BY ROW_NUM");

		   return factory.select(null, sql1.toString());
	   }
	   /**
	    * 校验是否存在重复数据
	    * @param user
	    * @return
	    * @throws Exception
	    */
	   public QuerySet checkList3(User user)throws Exception{
		   StringBuffer sql1= new StringBuffer();
		   sql1.append("SELECT COUNT(T.TMP_ID) AS COU,\n" );
		   sql1.append("       T.PART_CODE,\n" );
		   sql1.append("       T.SUPPLIER_CODE,\n" );
		   sql1.append("       T.POSITION_CODE\n" );
		   sql1.append("  FROM PT_BU_MOVE_OUT_PART_TMP T\n" );
		   sql1.append(" WHERE T.USER_ACCOUNT = '"+user+"'\n" );
		   sql1.append(" GROUP BY T.PART_CODE, T.SUPPLIER_CODE, T.POSITION_CODE\n" );
		   sql1.append("HAVING COUNT(T.TMP_ID) > 1");
		   return factory.select(null, sql1.toString());
	   }
	   /**
	    * 校验供货关系不存在(只校验配件基本档案中是指定供应的配件代码)
	    * @param user
	    * @return
	    * @throws Exception
	    */
	   public QuerySet checkList4(User user)throws Exception{
		   StringBuffer sql1= new StringBuffer();
		   sql1.append("SELECT M.ROW_NUM,M.PART_CODE,M.SUPPLIER_CODE,M.POSITION_CODE,M.MOVE_COUNT\n" );
		   sql1.append("  FROM PT_BU_MOVE_OUT_PART_TMP M\n" );
		   sql1.append(" WHERE NOT EXISTS\n" );
		   sql1.append(" (SELECT 1\n" );
		   sql1.append("          FROM PT_BA_PART_SUPPLIER_RL R, PT_BA_SUPPLIER S, PT_BA_INFO I\n" );
		   sql1.append("         WHERE R.SUPPLIER_ID = S.SUPPLIER_ID AND R.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n" );
		   sql1.append("           AND R.PART_ID = I.PART_ID AND S.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n" );
		   sql1.append("           AND S.SUPPLIER_CODE = M.SUPPLIER_CODE\n" );
		   sql1.append("           AND I.PART_CODE = M.PART_CODE)\n" );
		   sql1.append("   AND EXISTS (SELECT 1\n" );
		   sql1.append("          FROM PT_BU_MOVE_OUT_PART_TMP T, PT_BA_INFO I\n" );
		   sql1.append("         WHERE T.PART_CODE = I.PART_CODE\n" );
		   sql1.append("           AND I.IF_SUPLY = 100101\n" );
		   sql1.append("           AND T.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
		   sql1.append("           AND T.TMP_ID = M.TMP_ID)\n" );
		   sql1.append("    AND M.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
		   sql1.append("ORDER BY M.ROW_NUM");
		   return factory.select(null, sql1.toString());
	   }
	   /**
	    * 校验在库存表中不存在记录
	    * @param user
	    * @return
	    * @throws Exception
	    */
	   public QuerySet checkList5(User user,String warehouseId)throws Exception{
		   StringBuffer sql1= new StringBuffer();
		   sql1.append("SELECT T.TMP_ID,\n" );
		   sql1.append("       T.ROW_NUM,\n" );
		   sql1.append("       T.PART_CODE,\n" );
		   sql1.append("       I.PART_ID,\n" );
		   sql1.append("       T.SUPPLIER_CODE,\n" );
		   sql1.append("       T.POSITION_CODE,\n" );
		   sql1.append("       T.MOVE_COUNT\n" );
		   sql1.append("  FROM PT_BU_MOVE_OUT_PART_TMP T, PT_BA_INFO I\n" );
		   sql1.append("\n" );
		   sql1.append(" WHERE T.PART_CODE = I.PART_CODE\n" );
		   sql1.append("   AND (NOT EXISTS\n" );
		   sql1.append("        (SELECT 1\n" );
		   sql1.append("           FROM PT_BU_STOCK S\n" );
		   sql1.append("          WHERE S.PART_ID = I.PART_ID\n" );
		   sql1.append("            AND S.SUPPLIER_CODE =\n" );
		   sql1.append("                DECODE(I.IF_SUPLY, '100101', T.SUPPLIER_CODE, '9XXXXXX')\n" );
		   sql1.append("            AND S.WAREHOUSE_ID = "+warehouseId+") OR NOT EXISTS\n" );
		   sql1.append("        (SELECT 1\n" );
		   sql1.append("           FROM PT_BU_STOCK S, PT_BU_STOCK_DTL D\n" );
		   sql1.append("          WHERE S.PART_ID = I.PART_ID\n" );
		   sql1.append("            AND S.SUPPLIER_CODE =\n" );
		   sql1.append("                DECODE(I.IF_SUPLY, '100101', T.SUPPLIER_CODE, '9XXXXXX')\n" );
		   sql1.append("            AND S.STOCK_ID = D.STOCK_ID\n" );
		   sql1.append("            AND D.POSITION_CODE = T.POSITION_CODE\n" );
		   sql1.append("            AND S.WAREHOUSE_ID = "+warehouseId+")\n" );
		   sql1.append("       )\n" );
		   sql1.append("   AND T.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
		   sql1.append(" ORDER BY T.ROW_NUM");
		   return factory.select(null, sql1.toString());
	   }
	   /**
	    * 校验导入数量超过可用库存
	    * @param user
	    * @return
	    * @throws Exception
	    */
	   public QuerySet checkList6(User user,String warehouseId)throws Exception{
		   StringBuffer sql1= new StringBuffer();
		   sql1.append("SELECT T.TMP_ID,\n" );
		   sql1.append("       T.ROW_NUM,\n" );
		   sql1.append("       T.PART_CODE,\n" );
		   sql1.append("       I.PART_ID,\n" );
		   sql1.append("       T.SUPPLIER_CODE,\n" );
		   sql1.append("       T.POSITION_CODE,\n" );
		   sql1.append("       T.MOVE_COUNT\n" );
		   sql1.append("  FROM PT_BU_MOVE_OUT_PART_TMP T,\n" );
		   sql1.append("       PT_BA_INFO              I,\n" );
		   sql1.append("       PT_BU_STOCK             S,\n" );
		   sql1.append("       PT_BU_STOCK_DTL         D\n" );
		   sql1.append(" WHERE T.PART_CODE = I.PART_CODE\n" );
		   sql1.append("   AND S.WAREHOUSE_ID = "+warehouseId+"\n" );
		   sql1.append("   AND S.STOCK_ID = D.STOCK_ID\n" );
		   sql1.append("   AND D.PART_ID = D.PART_ID\n" );
		   sql1.append("   AND D.POSITION_CODE = T.POSITION_CODE\n" );
		   sql1.append("   AND D.SUPPLIER_CODE =\n" );
		   sql1.append("       DECODE(I.IF_SUPLY, '100101', T.SUPPLIER_CODE, '9XXXXXX')\n" );
		   sql1.append("   AND D.AVAILABLE_AMOUNT < T.MOVE_COUNT\n" );
		   sql1.append("   AND T.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
		   sql1.append("ORDER BY T.ROW_NUM");
		   return factory.select(null, sql1.toString());
	   }
	   /**
	    * 校验导入数据在出库明细表中不存在
	    * @param user
	    * @return
	    * @throws Exception
	    */
	   public QuerySet checkList7(User user,String outId)throws Exception{
		   StringBuffer sql1= new StringBuffer();
		   sql1.append("SELECT T.TMP_ID,\n" );
		   sql1.append("       T.ROW_NUM,\n" );
		   sql1.append("       T.PART_CODE,\n" );
		   sql1.append("       I.PART_ID,\n" );
		   sql1.append("       T.SUPPLIER_CODE,\n" );
		   sql1.append("       T.POSITION_CODE,\n" );
		   sql1.append("       T.MOVE_COUNT\n" );
		   sql1.append("  FROM PT_BU_MOVE_OUT_PART_TMP T, PT_BA_INFO I, PT_BA_WAREHOUSE_POSITION P\n" );
		   sql1.append(" WHERE T.PART_CODE = I.PART_CODE\n" );
		   sql1.append("   AND P.POSITION_CODE = T.POSITION_CODE\n" );
		   sql1.append("   AND EXISTS (SELECT 1\n" );
		   sql1.append("          FROM PT_BU_STOCK_OUT_DTL D\n" );
		   sql1.append("         WHERE D.PART_ID = I.PART_ID\n" );
		   sql1.append("           AND D.SUPPLIER_CODE =\n" );
		   sql1.append("               DECODE(I.IF_SUPLY, '100101', T.SUPPLIER_CODE, '9XXXXXX')\n" );
		   sql1.append("           AND D.POSITION_ID = P.POSITION_ID\n" );
		   sql1.append("           AND D.OUT_ID = "+outId+")\n" );
		   sql1.append("   AND T.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
		   sql1.append(" ORDER BY T.ROW_NUM");
		   return factory.select(null, sql1.toString());
	   }
	   /**
	     * 
	     *导入功能插入出库明细
	     * @param outId 出库单ID
	     * @param user
	     * @return
	     * @throws Exception
	     */
	    public Boolean insetrOutBillDtl(String outId,User user) throws Exception {
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
	    	sql1.append("         I.PART_ID,\n" );
	    	sql1.append("         I.PART_CODE,\n" );
	    	sql1.append("         I.PART_NAME,\n" );
	    	sql1.append("         SUPPLIER_ID,\n" );
	    	sql1.append("         S.SUPPLIER_NAME,\n" );
	    	sql1.append("         S.SUPPLIER_CODE,\n" );
	    	sql1.append("         P.POSITION_ID,\n" );
	    	sql1.append("         T.MOVE_COUNT,\n" );
	    	sql1.append("         '"+user.getAccount()+"',\n" );
	    	sql1.append("         SYSDATE\n" );
	    	sql1.append("    FROM PT_BU_MOVE_OUT_PART_TMP  T,\n" );
	    	sql1.append("         PT_BA_INFO               I,\n" );
	    	sql1.append("         PT_BA_SUPPLIER           S,\n" );
	    	sql1.append("         PT_BA_WAREHOUSE_POSITION P\n" );
	    	sql1.append("   WHERE T.PART_CODE = I.PART_CODE\n" );
	    	sql1.append("     AND S.SUPPLIER_CODE =\n" );
	    	sql1.append("         DECODE(I.IF_SUPLY, '100101', T.SUPPLIER_CODE, '9XXXXXX')\n" );
	    	sql1.append("     AND P.POSITION_CODE = T.POSITION_CODE AND S.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n" );
	    	sql1.append("     AND T.USER_ACCOUNT = '"+user.getAccount()+"'");
	    	return factory.update(sql1.toString(), null);
	    }
	    /**
	     * 
	     *导入功能更新库存明细
	     * @param user
	     * @return
	     * @throws Exception
	     */
	    public Boolean updateStockDtl(User user) throws Exception {
	    	StringBuffer sql1= new StringBuffer();
	    	sql1.append("MERGE INTO PT_BU_STOCK_DTL D\n" );
	    	sql1.append("USING (SELECT T.PART_CODE,\n" );
	    	sql1.append("              DECODE(I.IF_SUPLY, '100101', T.SUPPLIER_CODE, '9XXXXXX') AS SUPPLIER_CODE,\n" );
	    	sql1.append("              T.POSITION_CODE,\n" );
	    	sql1.append("              T.MOVE_COUNT\n" );
	    	sql1.append("         FROM PT_BU_MOVE_OUT_PART_TMP T, PT_BA_INFO I\n" );
	    	sql1.append("        WHERE T.PART_CODE = I.PART_CODE\n" );
	    	sql1.append("          AND T.USER_ACCOUNT = '"+user.getAccount()+"') C\n" );
	    	sql1.append("ON (D.PART_CODE = C.PART_CODE AND D.SUPPLIER_CODE = C.SUPPLIER_CODE AND D.POSITION_CODE = C.POSITION_CODE)\n" );
	    	sql1.append("WHEN MATCHED THEN\n" );
	    	sql1.append("  UPDATE\n" );
	    	sql1.append("     SET D.OCCUPY_AMOUNT    = NVL(D.OCCUPY_AMOUNT, 0) + C.MOVE_COUNT,\n" );
	    	sql1.append("         D.AVAILABLE_AMOUNT = NVL(D.AVAILABLE_AMOUNT, 0) - C.MOVE_COUNT,\n" );
	    	sql1.append("         D.UPDATE_TIME      = SYSDATE,\n" );
	    	sql1.append("         D.UPDATE_USER      = '"+user.getAccount()+"'");
	    	return factory.update(sql1.toString(), null);
	    }

	    /**
	     * 查询库管对应仓库
	     *
	     * @return
	     */
	    public BaseResultSet getWarehouse (User user) throws Exception {

	    	StringBuilder sql= new StringBuilder();
	    	sql.append("SELECT WAREHOUSE_ID,WAREHOUSE_CODE,WAREHOUSE_NAME FROM PT_BA_WAREHOUSE WHERE ORG_ID='" + user.getOrgId() + "'");

	    	return factory.select(sql.toString(), new PageManager());
	    }
	    /**
	     * 
	     *导入功能更新库存主表
	     * @param user
	     * @return
	     * @throws Exception
	     */
	    public Boolean updateStock(User user,String warehouseId) throws Exception {
	    	StringBuffer sql1= new StringBuffer();
	    	sql1.append("MERGE INTO PT_BU_STOCK D\n" );
	    	sql1.append("USING (SELECT T.PART_CODE,\n" );
	    	sql1.append("              DECODE(I.IF_SUPLY, '100101', T.SUPPLIER_CODE, '9XXXXXX') AS SUPPLIER_CODE,\n" );
	    	sql1.append("              T.MOVE_COUNT\n" );
	    	sql1.append("         FROM PT_BU_MOVE_OUT_PART_TMP T, PT_BA_INFO I\n" );
	    	sql1.append("        WHERE T.PART_CODE = I.PART_CODE\n" );
	    	sql1.append("          AND T.USER_ACCOUNT = '"+user.getAccount()+"') C\n" );
	    	sql1.append("ON (D.PART_CODE = C.PART_CODE AND D.SUPPLIER_CODE = C.SUPPLIER_CODE AND D.WAREHOUSE_ID = "+warehouseId+")\n" );
	    	sql1.append("WHEN MATCHED THEN\n" );
	    	sql1.append("  UPDATE\n" );
	    	sql1.append("     SET D.OCCUPY_AMOUNT    = NVL(D.OCCUPY_AMOUNT, 0) + C.MOVE_COUNT,\n" );
	    	sql1.append("         D.AVAILABLE_AMOUNT = NVL(D.AVAILABLE_AMOUNT, 0) - C.MOVE_COUNT,\n" );
	    	sql1.append("         D.UPDATE_TIME      = SYSDATE,\n" );
	    	sql1.append("         D.UPDATE_USER      = '"+user.getAccount()+"'");
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