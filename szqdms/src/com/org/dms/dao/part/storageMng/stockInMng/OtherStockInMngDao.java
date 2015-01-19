package com.org.dms.dao.part.storageMng.stockInMng;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.common.PartOddNumberUtil;
import com.org.dms.vo.part.*;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.*;
import com.org.mvc.context.ActionContext;

import java.util.HashMap;
import java.util.Map;

/**
 * 其他入库Dao
 *
 * @user : lichuang
 * @date : 2014-08-07
 */
public class OtherStockInMngDao extends BaseDAO {
    //定义instance
    public static final OtherStockInMngDao getInstance(ActionContext atx) {
        OtherStockInMngDao dao = new OtherStockInMngDao();
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
    public BaseResultSet searchInBill(PageManager page, User user, String conditions) throws Exception {
        StringBuilder wheres = new StringBuilder(conditions);
        wheres.append("   AND A.WAREHOUSE_ID=PW.WAREHOUSE_ID\n");
        wheres.append("   AND A.IN_TYPE = " + DicConstant.RKLX_04 + "\n");
        wheres.append("   AND A.IN_STATUS = " + DicConstant.RKDZT_01 + "\n");
        wheres.append("   AND A.STATUS = " + DicConstant.YXBS_01 + "\n");
        wheres.append("   AND A.ORG_ID = " + user.getOrgId() + "\n");
        wheres.append("   AND A.OEM_COMPANY_ID=" + user.getOemCompanyId() + "\n");
        wheres.append("   ORDER BY A.CREATE_TIME DESC\n");
        page.setFilter(wheres.toString());

        //定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT A.IN_ID,\n");
        sql.append("       A.IN_NO,\n");
        sql.append("       A.IN_TYPE,\n");
        sql.append("       A.WAREHOUSE_ID,\n");
        sql.append("       A.WAREHOUSE_CODE,\n");
        sql.append("       A.WAREHOUSE_NAME,\n");
        sql.append("       A.REMARKS,PW.WAREHOUSE_TYPE\n");
        sql.append("  FROM PT_BU_STOCK_IN A,PT_BA_WAREHOUSE PW\n");

        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("IN_TYPE", DicConstant.RKLX);
        return bs;
    }

    /**
     * 查询入库单配件
     *
     * @param page
     * @param user
     * @param conditions
     * @param IN_ID        入库单ID
     * @param WAREHOUSE_ID 仓库ID
     * @return
     * @throws Exception
     */
    public BaseResultSet searchInBillPart(PageManager page, User user, String conditions, String IN_ID, String WAREHOUSE_ID) throws Exception {
        StringBuilder wheres = new StringBuilder(conditions);
//        wheres.append("   AND A.IN_ID = " + IN_ID + "\n");
//        wheres.append("   AND A.PART_ID = B.PART_ID\n");
//        wheres.append("   AND A.PART_ID = C.PART_ID(+)\n");
//        wheres.append("   AND A.SUPPLIER_ID = B.SUPPLIER_ID(+)\n");
//        
//        
//        wheres.append("   AND A.PART_ID = D.PART_ID(+)\n" );
//        wheres.append("   AND A.SUPPLIER_ID = D.SUPPLIER_ID(+)\n" );
//        wheres.append("   AND A.PART_ID = E.PART_ID(+)\n" );
//        wheres.append("   AND A.PART_ID = F.PART_ID\n" );
//        wheres.append("   AND A.PART_ID = G.PART_ID\n" );
//        wheres.append("   AND F.STATUS='"+DicConstant.YXBS_01+"'\n" );
//        wheres.append("   AND F.POSITION_ID IN\n" );
//        wheres.append("       (SELECT S.POSITION_ID\n" );
//        wheres.append("          FROM PT_BA_WAREHOUSE_POSITION S,\n" );
//        wheres.append("               PT_BA_WAREHOUSE_AREA     M,\n" );
//        wheres.append("               PT_BA_WAREHOUSE          N\n" );
//        wheres.append("         WHERE S.AREA_ID = M.AREA_ID\n" );
//        wheres.append("           AND M.WAREHOUSE_ID = N.WAREHOUSE_ID\n" );
//        wheres.append("           AND N.WAREHOUSE_ID = '" + WAREHOUSE_ID + "')\n" );
//        wheres.append(" GROUP BY A.DETAIL_ID,\n" );
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
//        wheres.append("          D.AMOUNT,\n" );
//        wheres.append("          A.IN_AMOUNT,\n" );
//        wheres.append("          A.REMARKS\n" );
//        
//        wheres.append("   ORDER BY A.PART_CODE ASC\n");
        page.setFilter(wheres.toString());
        //定义返回结果集
        BaseResultSet bs = null;
//        StringBuilder sql = new StringBuilder();
//        sql.append("SELECT A.DETAIL_ID,\n");
//        sql.append("       A.PART_ID,\n");
//        sql.append("       A.PART_CODE,\n");
//        sql.append("       A.PART_NAME,\n");
//        sql.append("       A.SUPPLIER_ID,\n");
//        sql.append("       A.SUPPLIER_CODE,\n");
//        sql.append("       A.SUPPLIER_NAME,\n");
//        sql.append("       C.PART_NO,\n");
//        sql.append("       C.UNIT,\n");
//        sql.append("       C.MIN_PACK,\n");
//        sql.append("       C.MIN_UNIT,\n");
//        sql.append("       NVL(D.AMOUNT,0) AMOUNT,\n");
//        sql.append("       NVL(A.IN_AMOUNT,0) IN_AMOUNT,\n");
//        
//        sql.append("       WM_CONCAT(F.POSITION_ID) POSITION_IDS,\n" );
//        sql.append("       WM_CONCAT(F.POSITION_CODE) POSITION_CODESS,\n" );
//        sql.append("       WM_CONCAT(F.POSITION_NAME) POSITION_NAMES,\n" );
//        
//        sql.append("       A.REMARKS\n");
//        sql.append("  FROM PT_BU_STOCK_IN_DTL A, PT_BA_INFO C, (SELECT * FROM  PT_BU_STOCK_IN_DTL WHERE IN_ID = " + IN_ID + ") B,\n");
//        sql.append("       (SELECT * FROM PT_BU_STOCK WHERE WAREHOUSE_ID = '" + WAREHOUSE_ID + "') D,\n" );
//        sql.append("       (SELECT SUM(T1.IN_AMOUNT) PRINT_COUNT, T.ORDER_ID, T1.PART_ID\n" );
//        sql.append("          FROM PT_BU_STOCK_IN T, PT_BU_STOCK_IN_DTL T1\n" );
//        sql.append("         WHERE 1 = 1\n" );
//        sql.append("           AND T.IN_ID = T1.IN_ID\n" );
//        sql.append("           AND T.PRINT_STATUS = '" + DicConstant.DYZT_02 + "'\n" );
//        sql.append("         GROUP BY T.ORDER_ID, T1.PART_ID) E,\n" );
//        sql.append("       PT_BA_WAREHOUSE_PART_RL F,\n" );
//        sql.append("       (SELECT * FROM PT_BA_WAREHOUSE_KEEPER WHERE USER_ACCOUNT = '"+user.getAccount()+"') G\n" );
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT *\n" );
        sql.append("  FROM (SELECT T1.DETAIL_ID,\n");
        sql.append("               T1.PART_ID,\n");
        sql.append("               T1.PART_CODE,\n");
        sql.append("               T1.PART_NAME,\n");
        sql.append("               T1.SUPPLIER_ID,\n");
        sql.append("               T1.SUPPLIER_CODE,\n");
        sql.append("               T1.SUPPLIER_NAME,\n");
        sql.append("               T1.PART_NO,\n");
        sql.append("               T1.UNIT,\n");
        sql.append("               T1.MIN_PACK,\n");
        sql.append("               T1.MIN_UNIT,\n");
        sql.append("               T1.AMOUNT,\n");
        sql.append("               T1.IN_AMOUNT,\n");
        sql.append("               WM_CONCAT(T2.POSITION_ID) POSITION_IDS,\n" );
        sql.append("               WM_CONCAT(T2.POSITION_CODE) POSITION_CODESS,\n" );
        sql.append("               WM_CONCAT(T2.POSITION_NAME) POSITION_NAMES,\n" );
        sql.append("               T1.REMARKS\n" );
        sql.append("          FROM (SELECT A.DETAIL_ID,\n");
        sql.append("                       A.PART_ID,\n");
        sql.append("                       A.PART_CODE,\n");
        sql.append("                       A.PART_NAME,\n");
        sql.append("                       A.SUPPLIER_ID,\n");
        sql.append("                       A.SUPPLIER_CODE,\n");
        sql.append("                       A.SUPPLIER_NAME,\n");
        sql.append("                       C.PART_NO,\n");
        sql.append("                       C.UNIT,\n");
        sql.append("                       C.MIN_PACK,\n");
        sql.append("                       C.MIN_UNIT,\n");
        sql.append("                       NVL(D.AMOUNT,0) AMOUNT,\n");
        sql.append("                       NVL(A.IN_AMOUNT,0) IN_AMOUNT,\n");
        sql.append("                       B.REMARKS\n" );
        sql.append("  FROM PT_BU_STOCK_IN_DTL A, PT_BA_INFO C, (SELECT * FROM  PT_BU_STOCK_IN_DTL WHERE IN_ID = " + IN_ID + ") B,\n");
        sql.append("       (SELECT * FROM PT_BU_STOCK WHERE WAREHOUSE_ID = '" + WAREHOUSE_ID + "') D,\n" );
        sql.append("       (SELECT SUM(T1.IN_AMOUNT) PRINT_COUNT, T.ORDER_ID, T1.PART_ID\n" );
        sql.append("          FROM PT_BU_STOCK_IN T, PT_BU_STOCK_IN_DTL T1\n" );
        sql.append("         WHERE 1 = 1\n" );
        sql.append("           AND T.IN_ID = T1.IN_ID\n" );
        sql.append("           AND T.PRINT_STATUS = '" + DicConstant.DYZT_02 + "'\n" );
        sql.append("         GROUP BY T.ORDER_ID, T1.PART_ID) E,\n" );
        sql.append("       (SELECT * FROM PT_BA_WAREHOUSE_KEEPER WHERE USER_ACCOUNT = '"+user.getAccount()+"') G\n" );
        sql.append("                 WHERE A.IN_ID = " + IN_ID + "\n");
        sql.append("   AND A.PART_ID = B.PART_ID\n");
        sql.append("   AND A.PART_ID = C.PART_ID(+)\n");
        sql.append("   AND A.SUPPLIER_ID = B.SUPPLIER_ID(+)\n");
        
        
        sql.append("   AND A.PART_ID = D.PART_ID(+)\n" );
        sql.append("   AND A.SUPPLIER_ID = D.SUPPLIER_ID(+)\n" );
        sql.append("   AND A.PART_ID = E.PART_ID(+)\n" );
        sql.append("   AND A.PART_ID = G.PART_ID\n" );
        sql.append("                 GROUP BY A.DETAIL_ID,\n");
        sql.append("                       A.PART_ID,\n");
        sql.append("                       A.PART_CODE,\n");
        sql.append("                       A.PART_NAME,\n");
        sql.append("                       A.SUPPLIER_ID,\n");
        sql.append("                       A.SUPPLIER_CODE,\n");
        sql.append("                       A.SUPPLIER_NAME,\n");
        sql.append("                       C.PART_NO,\n");
        sql.append("                       C.UNIT,\n");
        sql.append("                       C.MIN_PACK,\n");
        sql.append("                       C.MIN_UNIT,\n");
        sql.append("                       D.AMOUNT,\n");
        sql.append("                       A.IN_AMOUNT,\n");
        sql.append("                       B.REMARKS) T1\n" );
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
        sql.append("         GROUP BY T1.DETAIL_ID,\n");
        sql.append("               T1.PART_ID,\n");
        sql.append("               T1.PART_CODE,\n");
        sql.append("               T1.PART_NAME,\n");
        sql.append("               T1.SUPPLIER_ID,\n");
        sql.append("               T1.SUPPLIER_CODE,\n");
        sql.append("               T1.SUPPLIER_NAME,\n");
        sql.append("               T1.PART_NO,\n");
        sql.append("               T1.UNIT,\n");
        sql.append("               T1.MIN_PACK,\n");
        sql.append("               T1.MIN_UNIT,\n");
        sql.append("               T1.AMOUNT,\n");
        sql.append("               T1.IN_AMOUNT,\n");
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
    public boolean insertStockDtl(PtBuStockDtlVO vo)
            throws Exception {
        return factory.insert(vo);
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
        sql.append(" WHERE A.DETAIL_ID = " + map.get("DETAIL_ID") + "\n");

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
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean deleteInBillDtl(PtBuStockInDtlVO vo) throws Exception {
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
     * 删除入库数量为0的入库单明细
     *
     * @param inId 入库单ID
     * @return
     * @throws Exception
     */
    public boolean deleteUselessInBillDtl(String inId) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM PT_BU_STOCK_IN_DTL WHERE IN_ID = " + inId + " AND (IN_AMOUNT = 0 OR IN_AMOUNT IS NULL)\n");
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
     * 校验入库单明细是否存在
     *
     * @param IN_ID         入库单ID
     * @param PART_ID       配件ID
     * @param SUPPLIER_CODE 供应商代码
     * @return
     * @throws Exception
     */
    public Boolean checkInBillDtlIsExist(String IN_ID, String PART_ID, String SUPPLIER_CODE) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT 1\n");
        sql.append("  FROM PT_BU_STOCK_IN_DTL A\n");
        sql.append(" WHERE A.PART_ID = " + PART_ID + "\n");
        sql.append("   AND A.IN_ID = '" + IN_ID + "'\n");
        sql.append("   AND A.SUPPLIER_CODE = '" + SUPPLIER_CODE + "'\n");

        QuerySet qs = factory.select(null, sql.toString());
        if (qs.getRowCount() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 新增入库流水
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean insertContinual(PtBuStockInContinualVO vo) throws Exception {
        return factory.insert(vo);
    }

    /**
     * 入库完成验证
     *
     * @param pInId 入库单ID
     * @param pReturnId 销售退件单ID
     * @return 结果集
     * @throws Exception
     */
    public QuerySet inStockCheck(String pInId) throws Exception {

    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT 1\n" );
    	sql.append("  FROM \n" );
    	sql.append("PT_BU_STOCK_IN_DTL \n" );
    	sql.append("   WHERE IN_ID = '" + pInId + "' AND NVL(IN_AMOUNT,0) = '0'\n" );


        //执行方法，不需要传递conn参数
        return factory.select(null, sql.toString());
    }

    /**
     * 查询配件
     *
     * @param page
     * @param user
     * @param conditions
     * @param inId          入库单ID
     * @param warehouseType 仓库类型
     * @return
     * @throws Exception
     */
    public BaseResultSet searchPart(PageManager page, User user, String conditions, String inId, String warehouseType) throws Exception {
        String wheres = conditions;

        if ("100101".equals(warehouseType)) {
            wheres += " AND A.PART_TYPE IN(" + DicConstant.PJLB_01 + "," + DicConstant.PJLB_02 + ")\n";
        } else if ("100102".equals(warehouseType)) {
            wheres += " AND A.PART_TYPE IN(" + DicConstant.PJLB_01 + "," + DicConstant.PJLB_03 + ")\n";
        }

        wheres += " AND A.PART_STATUS <> " + DicConstant.PJZT_02 + "\n";
//        wheres += " AND NOT EXISTS(SELECT 1 FROM PT_BU_STOCK_IN_DTL B \n";
//        wheres += "                          WHERE B.IN_ID = "+inId+"\n";
//        wheres += "                          AND A.PART_ID = B.PART_ID)\n";
        wheres+="AND EXISTS(SELECT 1 FROM PT_BA_WAREHOUSE_PART_RL RL WHERE RL.PART_ID=A.PART_ID)";
        wheres+="AND PART_ID IN (SELECT PART_ID FROM PT_BA_WAREHOUSE_KEEPER WHERE USER_ACCOUNT = '"+user.getAccount()+"')";
        wheres += " ORDER BY A.PART_CODE ASC\n";
        page.setFilter(wheres);
        //定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT A.PART_ID,\n");
        sql.append("       A.PART_CODE,\n");
        sql.append("       A.PART_NAME,\n");
        sql.append("       A.PART_NO,\n");
        sql.append("       A.UNIT,\n");
        sql.append("       A.MIN_PACK,\n");
        sql.append("       A.MIN_UNIT,\n");
        sql.append("       A.SALE_PRICE,\n");
        sql.append("       A.IF_SUPLY\n");
        sql.append("  FROM PT_BA_INFO A\n");
        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("IF_SUPLY", DicConstant.SF);
        bs.setFieldDic("UNIT", DicConstant.JLDW);
        bs.setFieldDic("MIN_UNIT", DicConstant.JLDW);
        return bs;
    }

    /**
     * 查询供应商信息
     *
     * @param supplierCode
     * @return
     * @throws Exception
     */
    public Map<String, String> querySupplierByCode(String supplierCode) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT A.SUPPLIER_ID, A.SUPPLIER_NAME\n");
        sql.append("  FROM PT_BA_SUPPLIER A\n");
        sql.append(" WHERE A.SUPPLIER_CODE = '" + supplierCode + "' AND A.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n");

        QuerySet qs = factory.select(null, sql.toString());
        Map<String, String> map = new HashMap<String, String>();

        if (qs.getRowCount() > 0) {
            map.put("SUPPLIER_ID", qs.getString(1, "SUPPLIER_ID"));
            map.put("SUPPLIER_NAME", qs.getString(1, "SUPPLIER_NAME"));
        }
        return map;
    }

    /**
     * 生成入库单号
     * @param warehouseCode
     * @return
     * @throws Exception
     */
    public String createInBillNo(String warehouseCode)throws Exception{
        return PartOddNumberUtil.getOtherInBillNo(factory,warehouseCode);
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
}