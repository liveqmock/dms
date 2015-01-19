package com.org.dms.dao.part.storageMng.stockOutMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuStockOutVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 出库单打印Dao
 *
 * @user : lichuang
 * @date : 2014-08-08
 */
public class OutBillPrintDao extends BaseDAO {
    //定义instance
    public static final OutBillPrintDao getInstance(ActionContext atx) {
        OutBillPrintDao dao = new OutBillPrintDao();
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
        wheres.append("   AND T.STATUS = " + DicConstant.YXBS_01 + "\n");
        wheres.append("   AND T.OEM_COMPANY_ID=" + user.getOemCompanyId() + "\n");
        wheres.append("   ORDER BY T.OUT_DATE DESC\n");
        page.setFilter(wheres.toString());

        //定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT *\n");
        sql.append("  FROM (SELECT A.OUT_ID,\n");
        sql.append("               A.OUT_NO,\n");
        sql.append("               A.WAREHOUSE_CODE,\n");
        sql.append("               A.WAREHOUSE_NAME,\n");
        sql.append("               A.ORDER_ID,\n");
        sql.append("               A.ORDER_NO,\n");
        sql.append("               A.OUT_TYPE,\n");
        sql.append("               B.ORG_NAME       RECV_UNIT,\n");
//        sql.append("               NULL             RECV_WAREHOUSE,\n");
        sql.append("               C.CHECK_USER,\n");
        sql.append("               A.OUT_DATE,\n");
        sql.append("               A.STATUS,\n");
        sql.append("               A.CREATE_TIME,\n");
        sql.append("               B.APPLY_DATE,\n");
        sql.append("               A.OEM_COMPANY_ID\n");
        sql.append("          FROM PT_BU_STOCK_OUT A, PT_BU_SALE_ORDER B, PT_BU_SALE_ORDER_CHECK C\n");
        sql.append("         WHERE A.ORDER_ID = B.ORDER_ID\n");
        sql.append("           AND A.ORDER_ID = C.ORDER_ID\n");
        sql.append("           AND B.ORDER_STATUS='"+DicConstant.DDZT_06+"'\n");
        sql.append("           AND C.CHECK_RESULT = " + DicConstant.DDZT_03 + "\n");
        sql.append("           AND A.OUT_TYPE IN (" + DicConstant.CKLX_01 + ", " + DicConstant.CKLX_02 + ", " + DicConstant.CKLX_06 + ")\n");
        sql.append("           AND A.OUT_STATUS = " + DicConstant.CKDZT_02 + "\n");
        sql.append("           AND A.PRINT_STATUS = " + DicConstant.DYZT_01 + "\n");
        sql.append("        UNION ALL\n");
        sql.append("        SELECT A.OUT_ID,\n");
        sql.append("               A.OUT_NO,\n");
        sql.append("               A.WAREHOUSE_CODE,\n");
        sql.append("               A.WAREHOUSE_NAME,\n");
        sql.append("               A.ORDER_ID,\n");
        sql.append("               A.ORDER_NO,\n");
        sql.append("               A.OUT_TYPE,\n");
        sql.append("               B.WAREHOUSE_NAME      RECV_UNIT,\n");
//        sql.append("               NULL             RECV_UNIT,\n");
//        sql.append("               B.WAREHOUSE_NAME RECV_WAREHOUSE,\n");
        sql.append("               A.CREATE_USER CHECK_USER,\n");
        sql.append("               A.OUT_DATE,\n");
        sql.append("               A.STATUS,\n");
        sql.append("               A.CREATE_TIME,\n");
        sql.append("               NULL APPLY_DATE,\n");
        sql.append("               A.OEM_COMPANY_ID\n");
        sql.append("          FROM PT_BU_STOCK_OUT A, PT_BA_WAREHOUSE B\n");
        sql.append("         WHERE A.RECEIVE_WAREHOUSE = B.WAREHOUSE_ID\n");
        sql.append("           AND A.OUT_TYPE = " + DicConstant.CKLX_03 + "\n");
        sql.append("           AND A.OUT_STATUS = " + DicConstant.CKDZT_02 + "\n");
        sql.append("           AND A.PRINT_STATUS = " + DicConstant.DYZT_01 + "\n");
        sql.append("        UNION ALL\n");
        sql.append("        SELECT A.OUT_ID,\n");
        sql.append("               A.OUT_NO,\n");
        sql.append("               A.WAREHOUSE_CODE,\n");
        sql.append("               A.WAREHOUSE_NAME,\n");
        sql.append("               A.ORDER_ID,\n");
        sql.append("               A.ORDER_NO,\n");
        sql.append("               A.OUT_TYPE,\n");
        sql.append("               B.SUPPLIER_NAME RECV_UNIT,\n");
//        sql.append("               NULL      RECV_WAREHOUSE,\n");
        sql.append("               B.CREATE_USER CHECK_USER,\n");
        sql.append("               A.OUT_DATE,\n");
        sql.append("               A.STATUS,\n");
        sql.append("               A.CREATE_TIME,\n");
        sql.append("               NULL  APPLY_DATE,\n");
        sql.append("               A.OEM_COMPANY_ID\n");
        sql.append("          FROM PT_BU_STOCK_OUT A, PT_BU_PCH_RETURN B\n");
        sql.append("         WHERE A.ORDER_ID = B.RETURN_ID\n");
        sql.append("           AND A.OUT_TYPE = " + DicConstant.CKLX_04 + "\n");
        sql.append("           AND A.OUT_STATUS = " + DicConstant.CKDZT_02 + "\n");
        sql.append("           AND A.PRINT_STATUS = " + DicConstant.DYZT_01 + "\n");
        sql.append("        UNION ALL\n");
        sql.append("        SELECT A.OUT_ID,\n");
        sql.append("               A.OUT_NO,\n");
        sql.append("               A.WAREHOUSE_CODE,\n");
        sql.append("               A.WAREHOUSE_NAME,\n");
        sql.append("               A.ORDER_ID,\n");
        sql.append("               A.ORDER_NO,\n");
        sql.append("               A.OUT_TYPE,\n");
        sql.append("               NULL             RECV_UNIT,\n");
//        sql.append("               NULL             RECV_WAREHOUSE,\n");
        sql.append("               A.CREATE_USER CHECK_USER,\n");
        sql.append("               A.OUT_DATE,\n");
        sql.append("               A.STATUS,\n");
        sql.append("               A.CREATE_TIME,\n");
        sql.append("               NULL APPLY_DATE,\n");
        sql.append("               A.OEM_COMPANY_ID\n");
        sql.append("          FROM PT_BU_STOCK_OUT A\n");
        sql.append("         WHERE 1 = 1\n");
        sql.append("           AND A.OUT_TYPE = " + DicConstant.CKLX_05 + "\n");
        sql.append("           AND A.OUT_STATUS = " + DicConstant.CKDZT_02 + "\n");
        sql.append("           AND A.PRINT_STATUS = " + DicConstant.DYZT_01 + ") T\n");

        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("OUT_TYPE", DicConstant.CKLX);
        bs.setFieldDateFormat("OUT_DATE", "yyyy-MM-dd HH:mm:ss");
        bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
        bs.setFieldUserID("CHECK_USER");
        return bs;
    }

    /**
     * 查询出库单配件
     *
     * @param page
     * @param user
     * @param conditions
     * @param OUT_ID     出库单ID
     * @return
     * @throws Exception
     */
    public BaseResultSet queryOutBillPart(PageManager page, User user, String conditions, String OUT_ID) throws Exception {
        StringBuilder wheres = new StringBuilder(conditions);
        wheres.append("   AND A.OUT_ID = " + OUT_ID + "\n");
        wheres.append("   AND A.PART_ID = B.PART_ID\n");
        wheres.append("   AND A.POSITION_ID = D.POSITION_ID\n");
        wheres.append("   AND B.UNIT = C.ID\n");
        wheres.append("   ORDER BY ROWNUM ASC\n");
        page.setFilter(wheres.toString());

        //定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT A.PART_ID,\n");
        sql.append("       A.PART_CODE,\n");
        sql.append("       A.PART_NAME,\n");
        sql.append("       B.PART_NO,\n");
        sql.append("       C.DIC_VALUE UNIT,\n");
        sql.append("       D.POSITION_CODE,\n");
        sql.append("       D.AREA_CODE,\n");
        sql.append("       A.OUT_AMOUNT,\n");
        sql.append("       B.PLAN_PRICE,\n");
        sql.append("       B.PLAN_PRICE * A.OUT_AMOUNT PLAN_AMOUNT,\n");
        sql.append("       B.REMARKS REMARK,\n");
        sql.append("       ROWNUM\n");
        sql.append("  FROM PT_BU_STOCK_OUT_DTL            A,\n");
        sql.append("       PT_BA_INFO                B,\n");
        sql.append("       DIC_TREE                C,\n");
        sql.append("       PT_BA_WAREHOUSE_POSITION     D\n");

        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        return bs;
    }

    /**
     * 更新出库单
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean updateOutBill(PtBuStockOutVO vo) throws Exception {
        return factory.update(vo);
    }

    /**
     * 更新出库单明细的采购单价/采购金额/计划单价/计划金额/单位
     *
     * @param OUT_ID 出库单ID
     * @throws Exception
     */
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
        factory.update(sql.toString(), null);
    }
    
    
    public QuerySet querySaleInInfo(User user, String OUT_ID) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T3.PERSON_NAME CHECK_USER, T1.ORDER_NO, T1.ORG_NAME,\n" );
        sql.append("       TO_CHAR(SYSDATE, 'YYYY-MM-DD') PRINT_DATE\n" );
        sql.append("  FROM PT_BU_STOCK_OUT T, PT_BU_SALE_ORDER T1, PT_BU_SALE_ORDER_CHECK T2,TM_USER T3\n" );
        sql.append(" WHERE T.ORDER_ID = T1.ORDER_ID AND T2.CHECK_USER = T3.ACCOUNT\n" );
        sql.append("   AND T.ORDER_ID = T2.ORDER_ID\n" );
        sql.append("   AND T.OUT_ID = "+OUT_ID+"");
        qs = factory.select(null,sql.toString());
        return qs;
    }
    
    public QuerySet querySaleDtlInfo(User user, String OUT_ID) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.PART_CODE,\n" );
        sql.append("       T.PART_NAME,\n" );
        sql.append("       T1.PART_NO,\n" );
        sql.append("       T2.DIC_VALUE UNIT,\n" );
        sql.append("       T3.AREA_CODE,\n" );
        sql.append("       T3.POSITION_CODE,\n" );
        sql.append("       NVL(T.OUT_AMOUNT,0) OUT_AMOUNT,\n" );
        sql.append("       NVL(T.PLAN_PRICE,0) PLAN_PRICE,\n" );
        sql.append("       NVL(T.PLAN_AMOUNT,0) PLAN_AMOUNT\n" );
        sql.append("  FROM PT_BU_STOCK_OUT_DTL      T,\n" );
        sql.append("       PT_BA_INFO               T1,\n" );
        sql.append("       DIC_TREE                 T2,\n" );
        sql.append("       PT_BA_WAREHOUSE_POSITION T3\n" );
        sql.append(" WHERE T.PART_ID = T1.PART_ID\n" );
        sql.append("   AND T1.UNIT = T2.ID\n" );
        sql.append("   AND T.POSITION_ID = T3.POSITION_ID");
        sql.append("   AND T.OUT_ID = "+OUT_ID+"");
        sql.append(" ORDER BY T.PART_CODE ASC");
        //执行方法，不需要传递conn参数
        qs = factory.select(null,sql.toString());
        return qs;
    }
    
    public QuerySet queryMoveInInfo(User user, String OUT_ID) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT A.OUT_NO,A.WAREHOUSE_NAME,B.WAREHOUSE_NAME RECEIVE_WAREHOUSE,\n" );
        sql.append("       TO_CHAR(SYSDATE, 'YYYY-MM-DD') PRINT_DATE\n" );
        sql.append("  FROM PT_BU_STOCK_OUT A,PT_BA_WAREHOUSE B\n" );
        sql.append(" WHERE A.RECEIVE_WAREHOUSE = B.WAREHOUSE_ID");
        sql.append(" AND A.OUT_ID = "+ OUT_ID + "\n");
        //执行方法，不需要传递conn参数
        qs = factory.select(null,sql.toString());
        return qs;
    }
    public QuerySet queryMoveInDtlInfo(User user, String OUT_ID) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.PART_CODE,\n" );
        sql.append("       T.PART_NAME,\n" );
        sql.append("       T1.PART_NO,\n" );
        sql.append("       T2.DIC_VALUE UNIT,\n" );
        sql.append("       T3.AREA_CODE,\n" );
        sql.append("       T3.POSITION_CODE,\n" );
        sql.append("       NVL(T.OUT_AMOUNT,0) OUT_AMOUNT,\n" );
        sql.append("       NVL(T.PLAN_PRICE,0) PLAN_PRICE,\n" );
        sql.append("       NVL(T.PLAN_AMOUNT,0) PLAN_AMOUNT\n" );
        sql.append("  FROM PT_BU_STOCK_OUT_DTL      T,\n" );
        sql.append("       PT_BA_INFO               T1,\n" );
        sql.append("       DIC_TREE                 T2,\n" );
        sql.append("       PT_BA_WAREHOUSE_POSITION T3\n" );
        sql.append(" WHERE T.PART_ID = T1.PART_ID\n" );
        sql.append("   AND T1.UNIT = T2.ID\n" );
        sql.append("   AND T.POSITION_ID = T3.POSITION_ID");
        sql.append("   AND T.OUT_ID = "+OUT_ID+"");
        sql.append(" ORDER BY T.PART_CODE ASC");
        //执行方法，不需要传递conn参数
        qs = factory.select(null,sql.toString());
        return qs;
    }
    
    public QuerySet queryOtherInInfo(User user, String OUT_ID) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT A.OUT_NO,B.PERSON_NAME, \n" );
        sql.append("       TO_CHAR(SYSDATE, 'YYYY-MM-DD') PRINT_DATE\n" );
        sql.append("  FROM PT_BU_STOCK_OUT A,TM_USER B\n" );
        sql.append(" WHERE A.CREATE_USER = B.ACCOUNT AND  A.OUT_ID = "+ OUT_ID + "\n");
        //执行方法，不需要传递conn参数
        qs = factory.select(null,sql.toString());
        return qs;
    }
    public QuerySet queryDirSaleInfo(User user, String OUT_ID) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T3.PERSON_NAME CHECK_USER, T1.ORDER_NO, NVL(T4.CUSTORM_NAME,'') ORG_NAME,\n" );
        sql.append("       TO_CHAR(SYSDATE, 'YYYY-MM-DD') PRINT_DATE\n" );
        sql.append("  FROM PT_BU_STOCK_OUT T, PT_BU_SALE_ORDER T1, PT_BU_SALE_ORDER_CHECK T2,TM_USER T3,PT_BU_SALE_ORDER T4\n" );
        sql.append(" WHERE T.ORDER_ID = T1.ORDER_ID AND T2.CHECK_USER = T3.ACCOUNT\n" );
        sql.append("   AND T.ORDER_ID = T2.ORDER_ID\n" );
        sql.append("   AND T1.DIR_SOURCE_ORDER_ID = T4.ORDER_ID(+)");
        sql.append("   AND T.OUT_ID = "+OUT_ID+"");
        qs = factory.select(null,sql.toString());
        return qs;
    }

    public QuerySet queryPchRetInfo(User user, String OUT_ID) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.OUT_NO,\n" );
        sql.append("       T1.SUPPLIER_NAME,\n" );
        sql.append("       T2.PERSON_NAME APPLY_USER,\n" );
        sql.append("       TO_CHAR(SYSDATE, 'YYYY-MM-DD') PRINT_DATE\n" );
        sql.append("  FROM PT_BU_STOCK_OUT T, PT_BU_PCH_RETURN T1, TM_USER T2\n" );
        sql.append(" WHERE T.ORDER_ID = T1.RETURN_ID\n" );
        sql.append("   AND T1.CREATE_USER = T2.ACCOUNT");
        sql.append("   AND T.OUT_ID = "+ OUT_ID + "\n");
        //执行方法，不需要传递conn参数
        qs = factory.select(null,sql.toString());
        return qs;
    }
    public QuerySet queryPchRetDtlInfo(User user, String OUT_ID) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
//        StringBuffer sql= new StringBuffer();
//        sql.append("SELECT T.PART_CODE,\n" );
//        sql.append("       T.PART_NAME,\n" );
//        sql.append("       T1.PART_NO,\n" );
//        sql.append("       T2.DIC_VALUE UNIT,\n" );
//        sql.append("       T3.AREA_CODE,\n" );
//        sql.append("       T3.POSITION_CODE,\n" );
//        sql.append("       NVL(T.OUT_AMOUNT,0) OUT_AMOUNT,\n" );
//        sql.append("       NVL(T.PLAN_PRICE,0) PLAN_PRICE,\n" );
//        sql.append("       NVL(T.PLAN_AMOUNT,0) PLAN_AMOUNT\n" );
//        sql.append("  FROM PT_BU_STOCK_OUT_DTL      T,\n" );
//        sql.append("       PT_BA_INFO               T1,\n" );
//        sql.append("       DIC_TREE                 T2,\n" );
//        sql.append("       PT_BA_WAREHOUSE_POSITION T3\n" );
//        sql.append(" WHERE T.PART_ID = T1.PART_ID\n" );
//        sql.append("   AND T1.UNIT = T2.ID\n" );
//        sql.append("   AND T.POSITION_ID = T3.POSITION_ID");
//        sql.append("   AND T.OUT_ID = "+OUT_ID+"");
//        sql.append(" ORDER BY T.PART_CODE ASC");
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.PART_CODE,\n" );
        sql.append("       T.PART_NAME,\n" );
        sql.append("       T1.PART_NO,\n" );
        sql.append("       T2.DIC_VALUE UNIT,\n" );
        sql.append("       T3.AREA_CODE,\n" );
        sql.append("       T3.POSITION_CODE,\n" );
        sql.append("       NVL(T.OUT_AMOUNT, 0) OUT_AMOUNT,\n" );
        sql.append("       NVL(T.PLAN_PRICE, 0) PLAN_PRICE,\n" );
        sql.append("       NVL(T.PLAN_AMOUNT, 0) PLAN_AMOUNT,\n" );
        sql.append("       T5.REMARKS\n" );
        sql.append("  FROM PT_BU_STOCK_OUT_DTL      T,\n" );
        sql.append("       PT_BA_INFO               T1,\n" );
        sql.append("       DIC_TREE                 T2,\n" );
        sql.append("       PT_BA_WAREHOUSE_POSITION T3,\n" );
        sql.append("       PT_BU_STOCK_OUT          T4,\n" );
        sql.append("       PT_BU_PCH_RETURN_DTL     T5\n" );
        sql.append(" WHERE T.PART_ID = T1.PART_ID\n" );
        sql.append("   AND T1.UNIT = T2.ID\n" );
        sql.append("   AND T.OUT_ID = T4.OUT_ID\n" );
        sql.append("   AND T4.ORDER_ID = T5.RETURN_ID\n" );
        sql.append("   AND T.PART_ID = T5.PART_ID\n" );
        sql.append("   AND T.POSITION_ID = T3.POSITION_ID\n" );
        sql.append("   AND T.OUT_ID = "+OUT_ID+"\n" );
        sql.append(" ORDER BY T.PART_CODE ASC");

        //执行方法，不需要传递conn参数
        qs = factory.select(null,sql.toString());
        return qs;
    }
}