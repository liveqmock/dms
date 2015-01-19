package com.org.dms.dao.part.storageMng.stockInMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuStockInVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 入库单打印Dao
 *
 * @user : lichuang
 * @date : 2014-08-08
 */
public class InBillPrintDao extends BaseDAO {
    //定义instance
    public static final InBillPrintDao getInstance(ActionContext atx) {
        InBillPrintDao dao = new InBillPrintDao();
        atx.setDBFactory(dao.factory);
        return dao;
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
        wheres.append("   AND T.STATUS = " + DicConstant.YXBS_01 + "\n");
        wheres.append("   AND T.OEM_COMPANY_ID=" + user.getOemCompanyId() + "\n");
        wheres.append("   ORDER BY T.IN_DATE DESC\n");
        page.setFilter(wheres.toString());

        //定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT *\n");
        sql.append("  FROM (SELECT A.IN_ID,\n");
        sql.append("               A.IN_NO,\n");
        sql.append("               A.WAREHOUSE_CODE,\n");
        sql.append("               A.WAREHOUSE_NAME,\n");
        sql.append("               A.ORDER_ID,\n");
        sql.append("               A.ORDER_NO,\n");
        sql.append("               A.IN_TYPE,\n");
        sql.append("               B.SUPPLIER_NAME   OUT_UNIT,\n");
        sql.append("               NULL              OUT_WAREHOUSE,\n");
        sql.append("               B.PURCHASE_AMOUNT,\n");
        sql.append("               B.PLAN_AMOUNT,\n");
        sql.append("               C.AMOUNT,\n");
        sql.append("               A.IN_DATE,\n");
        sql.append("               A.STATUS,\n");
        sql.append("               A.OEM_COMPANY_ID\n");
        sql.append("          FROM PT_BU_STOCK_IN A, PT_BU_PCH_ORDER_SPLIT B,\n");
        sql.append("(SELECT SUM(A.IN_AMOUNT * B.PCH_PRICE) AMOUNT, A.IN_ID\n" );
        sql.append("   FROM PT_BU_STOCK_IN_DTL A, PT_BA_PART_SUPPLIER_RL B\n" );
        sql.append("  WHERE A.PART_ID = B.PART_ID AND B.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n" );
        sql.append("  GROUP BY A.IN_ID) C");
        sql.append("         WHERE A.ORDER_ID = B.SPLIT_ID\n");
        sql.append("           AND A.IN_ID = C.IN_ID\n");
        sql.append("           AND A.IN_TYPE = " + DicConstant.RKLX_01 + "\n");
        sql.append("           AND A.PRINT_STATUS = " + DicConstant.DYZT_01 + "\n");
        sql.append("        UNION ALL\n");
        sql.append("        SELECT A.IN_ID,\n");
        sql.append("               A.IN_NO,\n");
        sql.append("               A.WAREHOUSE_CODE,\n");
        sql.append("               A.WAREHOUSE_NAME,\n");
        sql.append("               A.ORDER_ID,\n");
        sql.append("               A.ORDER_NO,\n");
        sql.append("               A.IN_TYPE,\n");
        sql.append("               B.WAREHOUSE_NAME OUT_UNIT,\n");
        sql.append("               B.WAREHOUSE_NAME OUT_WAREHOUSE,\n");
        sql.append("               NULL             PURCHASE_AMOUNT,\n");
        sql.append("               NULL             PLAN_AMOUNT,\n");
        sql.append("               C.AMOUNT,\n");
        sql.append("               A.IN_DATE,\n");
        sql.append("               A.STATUS,\n");
        sql.append("               A.OEM_COMPANY_ID\n");
        sql.append("          FROM PT_BU_STOCK_IN A, PT_BU_STOCK_OUT B,\n");
        sql.append("(SELECT SUM(A.IN_AMOUNT * B.SALE_PRICE) AMOUNT, A.IN_ID\n" );
        sql.append("   FROM PT_BU_STOCK_IN_DTL A, PT_BA_INFO B\n" );
        sql.append("  WHERE A.PART_ID = B.PART_ID\n" );
        sql.append("  GROUP BY A.IN_ID) C");
        sql.append("         WHERE A.ORDER_ID = B.OUT_ID\n");
        sql.append("           AND A.IN_ID = C.IN_ID\n");
        sql.append("           AND A.IN_TYPE = " + DicConstant.RKLX_02 + "\n");
        sql.append("           AND A.IN_STATUS = " + DicConstant.RKDZT_02 + "\n");
        sql.append("           AND A.PRINT_STATUS = " + DicConstant.DYZT_01 + "\n");
        sql.append("        UNION ALL\n");
        sql.append("        SELECT A.IN_ID,\n");
        sql.append("               A.IN_NO,\n");
        sql.append("               A.WAREHOUSE_CODE,\n");
        sql.append("               A.WAREHOUSE_NAME,\n");
        sql.append("               A.ORDER_ID,\n");
        sql.append("               A.ORDER_NO,\n");
        sql.append("               A.IN_TYPE,\n");
        sql.append("               B.APPLY_ORG_NAME OUT_UNIT,\n");
        sql.append("               NULL             OUT_WAREHOUSE,\n");
        sql.append("               NULL             PURCHASE_AMOUNT,\n");
        sql.append("               NULL             PLAN_AMOUNT,\n");
        sql.append("               C.AMOUNT,\n");
        sql.append("               A.IN_DATE,\n");
        sql.append("               A.STATUS,\n");
        sql.append("               A.OEM_COMPANY_ID\n");
        sql.append("          FROM PT_BU_STOCK_IN A, PT_BU_RETURN_APPLY B,\n");
        sql.append("(SELECT SUM(A.IN_AMOUNT * B.SALE_PRICE) AMOUNT, A.IN_ID\n" );
        sql.append("   FROM PT_BU_STOCK_IN_DTL A, PT_BA_INFO B\n" );
        sql.append("  WHERE A.PART_ID = B.PART_ID\n" );
        sql.append("  GROUP BY A.IN_ID) C");
        sql.append("         WHERE A.ORDER_ID = B.RETURN_ID\n");
        sql.append("           AND A.IN_ID = C.IN_ID\n");
        sql.append("           AND A.IN_TYPE = " + DicConstant.RKLX_03 + "\n");
        sql.append("           AND B.APPLY_SATUS = " + DicConstant.TJSQDZT_05 + "\n");
        sql.append("           AND A.IN_STATUS = " + DicConstant.RKDZT_02 + "\n");
        sql.append("           AND A.PRINT_STATUS = " + DicConstant.DYZT_01 + "\n");
        sql.append("        UNION ALL\n");
        sql.append("        SELECT A.IN_ID,\n");
        sql.append("               A.IN_NO,\n");
        sql.append("               A.WAREHOUSE_CODE,\n");
        sql.append("               A.WAREHOUSE_NAME,\n");
        sql.append("               A.ORDER_ID,\n");
        sql.append("               A.ORDER_NO,\n");
        sql.append("               A.IN_TYPE,\n");
        sql.append("               NULL             OUT_UNIT,\n");
        sql.append("               NULL             OUT_WAREHOUSE,\n");
        sql.append("               NULL             PURCHASE_AMOUNT,\n");
        sql.append("               NULL             PLAN_AMOUNT,\n");
        sql.append("               C.AMOUNT,\n");
        sql.append("               A.IN_DATE,\n");
        sql.append("               A.STATUS,\n");
        sql.append("               A.OEM_COMPANY_ID\n");
        sql.append("          FROM PT_BU_STOCK_IN A,\n");
        sql.append("(SELECT SUM(A.IN_AMOUNT * B.PLAN_PRICE) AMOUNT, A.IN_ID\n" );
        sql.append("        FROM PT_BU_STOCK_IN_DTL A, PT_BA_INFO B\n" );
        sql.append("       WHERE A.PART_ID = B.PART_ID\n" );
        sql.append("       GROUP BY A.IN_ID) C");
        sql.append("           WHERE A.IN_TYPE = " + DicConstant.RKLX_04 + "\n");
        sql.append("		AND A.IN_ID = C.IN_ID");
        sql.append("           AND A.IN_STATUS = " + DicConstant.RKDZT_02 + "\n");
        sql.append("           AND A.PRINT_STATUS = " + DicConstant.DYZT_01 + ") T\n");


        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("IN_TYPE", DicConstant.RKLX);
        bs.setFieldDateFormat("IN_DATE", "yyyy-MM-dd HH:mm:ss");
        return bs;
    }

    /**
     * 查询采购入库单配件
     *
     * @param page
     * @param user
     * @param conditions
     * @return
     * @throws Exception
     */
    public BaseResultSet queryPchInBillPart(PageManager page, User user, String conditions, String IN_ID) throws Exception {
        StringBuilder wheres = new StringBuilder(conditions);
        wheres.append("   AND A.IN_ID = " + IN_ID + "\n");
        wheres.append("   AND A.IN_ID = B.IN_ID\n");
        wheres.append("   AND A.ORDER_ID = C.SPLIT_ID\n");
        wheres.append("   AND B.PART_ID = C.PART_ID\n");
        wheres.append("   AND C.PART_ID = D.PART_ID\n");
        wheres.append("   AND C.SPLIT_ID = G.SPLIT_ID\n");
        wheres.append("   AND G.SUPPLIER_ID = D.SUPPLIER_ID AND D.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n");
        wheres.append("   AND B.PART_ID = E.PART_ID\n");
        wheres.append("   AND E.UNIT = F.ID\n");
        wheres.append("   ORDER BY B.PART_CODE ASC\n");
        page.setFilter(wheres.toString());

        //定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT B.PART_ID,\n");
        sql.append("       B.PART_CODE,\n");
        sql.append("       B.PART_NAME,\n");
        sql.append("       E.PART_NO,\n");
        sql.append("       F.DIC_VALUE UNIT,\n");
        sql.append("       B.IN_AMOUNT,\n");
        sql.append("       D.PCH_PRICE,\n");
        sql.append("       D.PCH_PRICE * B.IN_AMOUNT PCH_AMOUNT,\n");
        sql.append("       E.PLAN_PRICE,\n");
        sql.append("       E.PLAN_PRICE * B.IN_AMOUNT PLAN_AMOUNT,\n");
        sql.append("       ROWNUM\n");
        sql.append("  FROM PT_BU_STOCK_IN            A,\n");
        sql.append("       PT_BU_STOCK_IN_DTL        B,\n");
        sql.append("       PT_BU_PCH_ORDER_SPLIT_DTL C,\n");
        sql.append("       PT_BA_PART_SUPPLIER_RL    D,\n");
        sql.append("       PT_BA_INFO                E,\n");
        sql.append("       DIC_TREE                  F,\n");
        sql.append("       PT_BU_PCH_ORDER_SPLIT     G\n");
        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        return bs;
    }

    /**
     * 查询销售退件入库单配件
     *
     * @param page
     * @param user
     * @param conditions
     * @return
     * @throws Exception
     */
    public BaseResultSet querySaleRetInBillPart(PageManager page, User user, String conditions, String IN_ID) throws Exception {
        StringBuilder wheres = new StringBuilder(conditions);
        wheres.append("   AND A.IN_ID = " + IN_ID + "\n");
        wheres.append("   AND A.IN_ID = B.IN_ID\n");
        wheres.append("   AND A.ORDER_ID = C.RETURN_ID\n");
        wheres.append("   AND B.PART_ID = C.PART_ID\n");
        wheres.append("   AND C.PART_ID = D.PART_ID\n");
        wheres.append("   AND D.UNIT = E.ID\n");
        wheres.append("   ORDER BY B.PART_CODE ASC\n");
        page.setFilter(wheres.toString());

        //定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT B.PART_ID,\n");
        sql.append("       B.PART_CODE,\n");
        sql.append("       B.PART_NAME,\n");
        sql.append("       D.PART_NO,\n");
        sql.append("       E.DIC_VALUE UNIT,\n");
        sql.append("       B.IN_AMOUNT,\n");
        sql.append("       C.SALE_PRICE,\n");
        sql.append("       C.SALE_PRICE * B.IN_AMOUNT RET_AMOUNT,\n");
        sql.append("       D.PLAN_PRICE,\n");
        sql.append("       D.PLAN_PRICE * B.IN_AMOUNT PLAN_AMOUNT,\n");
        sql.append("       ROWNUM\n");
        sql.append("  FROM PT_BU_STOCK_IN            A,\n");
        sql.append("       PT_BU_STOCK_IN_DTL        B,\n");
        sql.append("       PT_BU_RETURN_APPLY_DTL C,\n");
        sql.append("       PT_BA_INFO                D,\n");
        sql.append("       DIC_TREE                E\n");


        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        return bs;
    }

    /**
     * 查询移库入库单和其他入库单配件
     *
     * @param page
     * @param user
     * @param conditions
     * @return
     * @throws Exception
     */
    public BaseResultSet queryMoveAndOtherInBillPart(PageManager page, User user, String conditions, String IN_ID) throws Exception {
        StringBuilder wheres = new StringBuilder(conditions);
        wheres.append("   AND A.IN_ID = " + IN_ID + "\n");
        wheres.append("   AND A.PART_ID = B.PART_ID\n");
        wheres.append("   AND B.UNIT = C.ID\n");
        wheres.append("   ORDER BY B.PART_CODE ASC\n");
        page.setFilter(wheres.toString());

        //定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT A.PART_ID,\n");
        sql.append("       A.PART_CODE,\n");
        sql.append("       A.PART_NAME,\n");
        sql.append("       B.PART_NO,\n");
        sql.append("       C.DIC_VALUE UNIT,\n");
        sql.append("       A.IN_AMOUNT,\n");
        sql.append("       B.PLAN_PRICE,\n");
        sql.append("       B.PLAN_PRICE * A.IN_AMOUNT PLAN_AMOUNT,\n");
        sql.append("       ROWNUM\n");
        sql.append("  FROM PT_BU_STOCK_IN_DTL            A,\n");
        sql.append("       PT_BA_INFO                B,\n");
        sql.append("       DIC_VALUE                C\n");


        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        return bs;
    }

    /**
     * 更新入库单
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean updateInBill(PtBuStockInVO vo) throws Exception {
        return factory.update(vo);
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
//            sql.append("       A.PCH_PRICE  =\n");
//            sql.append("       (SELECT D.PCH_PRICE\n");
//            sql.append("          FROM PT_BU_STOCK_IN            B,\n");
//            sql.append("               PT_BU_PCH_ORDER_SPLIT_DTL C,\n");
//            sql.append("               PT_BU_PCH_ORDER_SPLIT 	 E,\n");
//            sql.append("               PT_BA_INFO    D\n");
//            sql.append("         WHERE B.IN_ID = " + IN_ID + "\n");
//            sql.append("           AND B.ORDER_ID = C.SPLIT_ID\n");
//            sql.append("           AND C.SPLIT_ID = E.SPLIT_ID\n");
//            sql.append("           AND E.SUPPLIER_ID = D.SUPPLIER_ID AND D.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n");
//            sql.append("           AND C.PART_ID = D.PART_ID\n");
//            sql.append("           AND C.PART_ID = A.PART_ID),\n");
//            sql.append("       A.PCH_AMOUNT =\n");
//            sql.append("       (SELECT D.PCH_PRICE * A.IN_AMOUNT\n");
//            sql.append("          FROM PT_BU_STOCK_IN            B,\n");
//            sql.append("               PT_BU_PCH_ORDER_SPLIT_DTL C,\n");
//            sql.append("               PT_BU_PCH_ORDER_SPLIT 	 E,\n");
//            sql.append("               PT_BA_PART_SUPPLIER_RL    D\n");
//            sql.append("         WHERE B.IN_ID = " + IN_ID + "\n");
//            sql.append("           AND B.ORDER_ID = C.SPLIT_ID\n");
//            sql.append("           AND C.SPLIT_ID = E.SPLIT_ID\n");
//            sql.append("           AND E.SUPPLIER_ID = D.SUPPLIER_ID AND D.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n");
//            sql.append("           AND C.PART_ID = D.PART_ID\n");
//            sql.append("           AND C.PART_ID = A.PART_ID),\n");
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
    public QuerySet queryPchInInfo(User user, String IN_ID) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
//        StringBuffer sql= new StringBuffer();
//        sql.append("SELECT A.IN_ID,\n");
//        sql.append("               A.IN_NO,\n");
//        sql.append("               A.WAREHOUSE_CODE,\n");
//        sql.append("               A.WAREHOUSE_NAME,\n");
//        sql.append("               A.ORDER_ID,\n");
//        sql.append("               A.ORDER_NO,\n");
//        sql.append("				TO_CHAR(SYSDATE, 'YYYY-MM-DD') PRINT_DATE,");
//        sql.append("               D.DIC_VALUE IN_TYPE,\n");
//        sql.append("               B.SUPPLIER_NAME   OUT_UNIT,\n");
//        sql.append("               NULL              OUT_WAREHOUSE,\n");
//        sql.append("               B.PURCHASE_AMOUNT,\n");
//        sql.append("               B.PLAN_AMOUNT,\n");
//        sql.append("               C.AMOUNT,\n");
//        sql.append("               A.IN_DATE,\n");
//        sql.append("               A.STATUS,\n");
//        sql.append("               A.OEM_COMPANY_ID\n");
//        sql.append("          FROM PT_BU_STOCK_IN A, PT_BU_PCH_ORDER_SPLIT B,\n");
//        sql.append("(SELECT SUM(A.IN_AMOUNT * B.PCH_PRICE) AMOUNT, A.IN_ID\n" );
//        sql.append("   FROM PT_BU_STOCK_IN_DTL A, PT_BA_PART_SUPPLIER_RL B\n" );
//        sql.append("  WHERE A.PART_ID = B.PART_ID AND B.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n" );
//        sql.append("  GROUP BY A.IN_ID) C,DIC_TREE D");
//        sql.append("         WHERE A.ORDER_ID = B.SPLIT_ID\n");
//        sql.append("           AND A.IN_ID = C.IN_ID\n");
//        sql.append("           AND A.IN_TYPE = D.ID\n");
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT A.IN_ID,\n" );
        sql.append("       A.IN_NO,\n" );
        sql.append("       A.WAREHOUSE_CODE,\n" );
        sql.append("       A.WAREHOUSE_NAME,\n" );
        sql.append("       A.ORDER_ID,\n" );
        sql.append("       A.ORDER_NO,\n" );
        sql.append("       TO_CHAR(SYSDATE, 'YYYY-MM-DD') PRINT_DATE,\n" );
        sql.append("       D.DIC_VALUE IN_TYPE,\n" );
        sql.append("       B.SUPPLIER_NAME OUT_UNIT,\n" );
        sql.append("       NULL OUT_WAREHOUSE,\n" );
        sql.append("       B.PURCHASE_AMOUNT,\n" );
        sql.append("       C.AMOUNT,\n" );
        sql.append("       E.PLAN_AMOUNT,\n" );
        sql.append("       A.IN_DATE,\n" );
        sql.append("       A.STATUS,\n" );
        sql.append("       A.OEM_COMPANY_ID\n" );
        sql.append("  FROM PT_BU_STOCK_IN A,\n" );
        sql.append("       PT_BU_PCH_ORDER_SPLIT B,\n" );
        sql.append("       (SELECT SUM(A.IN_AMOUNT * A.PCH_PRICE) AMOUNT, A.IN_ID\n" );
        sql.append("          FROM PT_BU_STOCK_IN_DTL A\n" );
        sql.append("         WHERE 1=1\n" );
        sql.append("         GROUP BY A.IN_ID) C,\n" );
        sql.append("                (SELECT SUM(A.IN_AMOUNT * A.PLAN_PRICE) PLAN_AMOUNT, A.IN_ID\n" );
        sql.append("          FROM PT_BU_STOCK_IN_DTL A\n" );
        sql.append("         WHERE 1=1\n" );
        sql.append("         GROUP BY A.IN_ID) E,\n" );
        sql.append("       DIC_TREE D\n" );
        sql.append(" WHERE A.ORDER_ID = B.SPLIT_ID\n" );
        sql.append("   AND A.IN_ID = C.IN_ID\n" );
        sql.append("   AND A.IN_ID = E.IN_ID\n" );
        sql.append("   AND A.IN_TYPE = D.ID");
        sql.append("   AND A.IN_ID = "+ IN_ID + "\n");
        //执行方法，不需要传递conn参数
        qs = factory.select(null,sql.toString());
        return qs;
    }
    
    public QuerySet queryPchInDtlInfo(User user, String IN_ID,String flag) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
        if("1".equals(flag)){
        	StringBuffer sql= new StringBuffer();
            sql.append("SELECT B.PART_ID,\n" );
            sql.append("       B.PART_CODE,\n" );
            sql.append("       B.PART_NAME,\n" );
            sql.append("       D.PART_NO,\n" );
            sql.append("       NVL(E.DIC_VALUE,'') UNIT,\n" );
            sql.append("       B.IN_AMOUNT,\n" );
            sql.append("       C.DISTRIBUTION_NO,\n" );
            sql.append("       ROUND(NVL(B.PCH_PRICE,0),2) PCH_PRICE,\n" );
            sql.append("       ROUND(NVL(B.PCH_PRICE,0) * B.IN_AMOUNT,2) PCH_AMOUNT,\n" );
            sql.append("       ROUND(NVL(B.PLAN_PRICE,0),2) PLAN_PRICE,\n" );
            sql.append("       ROUND(NVL(B.PLAN_PRICE,0) * B.IN_AMOUNT,2) PLAN_AMOUNT,\n" );
            sql.append("       ROWNUM\n" );
            sql.append("  FROM PT_BU_STOCK_IN            A,\n" );
            sql.append("       PT_BU_STOCK_IN_DTL        B,\n" );
            sql.append("       PT_BU_PCH_ORDER_SPLIT_DTL C,\n" );
            sql.append("       PT_BA_INFO                D,\n" );
            sql.append("       DIC_TREE                  E\n" );
            sql.append(" WHERE 1 = 1\n" );
            sql.append("   AND A.IN_ID = "+IN_ID+"\n" );
            sql.append("   AND A.IN_ID = B.IN_ID\n" );
            sql.append("   AND A.ORDER_ID = C.SPLIT_ID\n" );
            sql.append("   AND B.PART_ID = C.PART_ID\n" );
            sql.append("   AND C.PART_ID = D.PART_ID\n" );
            sql.append("   AND B.IN_AMOUNT >0 \n" );
            sql.append("   AND D.UNIT = E.ID(+)\n" );
            sql.append(" ORDER BY B.PART_CODE ASC");
            qs = factory.select(null,sql.toString());
        }else{
        	StringBuffer sql= new StringBuffer();
            sql.append("SELECT B.PART_ID,\n" );
            sql.append("       B.PART_CODE,\n" );
            sql.append("       B.PART_NAME,\n" );
            sql.append("       D.PART_NO,\n" );
            sql.append("       NVL(E.DIC_VALUE,'') UNIT,\n" );
            sql.append("       B.IN_AMOUNT,\n" );
            sql.append("       C.DISTRIBUTION_NO,\n" );
            sql.append("       ROUND(NVL(B.PCH_PRICE,0),2) PCH_PRICE,\n" );
            sql.append("       ROUND(NVL(B.PCH_PRICE,0) * B.IN_AMOUNT,3) PCH_AMOUNT,\n" );
            sql.append("       ROUND(NVL(B.PLAN_PRICE,0),2) PLAN_PRICE,\n" );
            sql.append("       ROUND(NVL(B.PLAN_PRICE,0) * B.IN_AMOUNT,3) PLAN_AMOUNT,\n" );
            sql.append("       ROWNUM\n" );
            sql.append("  FROM PT_BU_STOCK_IN            A,\n" );
            sql.append("       PT_BU_STOCK_IN_DTL        B,\n" );
            sql.append("       PT_BU_PCH_ORDER_SPLIT_DTL C,\n" );
            sql.append("       PT_BA_INFO                D,\n" );
            sql.append("       DIC_TREE                  E\n" );
            sql.append(" WHERE 1 = 1\n" );
            sql.append("   AND A.IN_ID = "+IN_ID+"\n" );
            sql.append("   AND A.IN_ID = B.IN_ID\n" );
            sql.append("   AND A.ORDER_ID = C.SPLIT_ID\n" );
            sql.append("   AND B.PART_ID = C.PART_ID\n" );
            sql.append("   AND C.PART_ID = D.PART_ID\n" );
            sql.append("   AND B.IN_AMOUNT >0 \n" );
            sql.append("   AND D.UNIT = E.ID(+)\n" );
            sql.append(" ORDER BY B.PART_CODE ASC");
            qs = factory.select(null,sql.toString());
        }
        //执行方法，不需要传递conn参数
        
        return qs;
    }
    public QuerySet queryMoveInInfo(User user, String IN_ID) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT A.IN_ID,\n" );
        sql.append("       A.IN_NO,\n" );
        sql.append("       A.WAREHOUSE_CODE,\n" );
        sql.append("       A.WAREHOUSE_NAME,\n" );
        sql.append("       A.ORDER_ID,\n" );
        sql.append("       A.ORDER_NO,\n" );
        sql.append("       TO_CHAR(SYSDATE, 'YYYY-MM-DD') PRINT_DATE,\n" );
        sql.append("       D.DIC_VALUE IN_TYPE,\n" );
        sql.append("       B.WAREHOUSE_NAME OUT_UNIT,\n" );
        sql.append("       A.IN_DATE,\n" );
        sql.append("       A.STATUS,\n" );
        sql.append("       E.PLAN_AMOUNT,\n" );
        sql.append("       A.OEM_COMPANY_ID\n" );
        sql.append("  FROM PT_BU_STOCK_IN A,\n" );
        sql.append("       PT_BU_STOCK_OUT B,\n" );
        sql.append("       DIC_TREE D,\n" );
        sql.append("(SELECT SUM(A.IN_AMOUNT * A.PLAN_PRICE) PLAN_AMOUNT, A.IN_ID\n" );
        sql.append("          FROM PT_BU_STOCK_IN_DTL A\n" );
        sql.append("         WHERE 1=1\n" );
        sql.append("         GROUP BY A.IN_ID) E");
        sql.append(" WHERE A.ORDER_ID = B.OUT_ID\n" );
        sql.append("   AND A.IN_TYPE = D.ID\n" );
        sql.append("   AND A.IN_ID = E.IN_ID\n");
        sql.append("   AND A.IN_ID = "+ IN_ID + "\n");
        //执行方法，不需要传递conn参数
        qs = factory.select(null,sql.toString());
        return qs;
    }
    public QuerySet queryMoveInDtlInfo(User user, String IN_ID,String flag) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
        
        if("1".equals(flag)){
        	StringBuffer sql= new StringBuffer();
            sql.append("SELECT B.PART_ID,\n" );
            sql.append("       B.PART_CODE,\n" );
            sql.append("       B.PART_NAME,\n" );
            sql.append("       D.PART_NO,\n" );
            sql.append("       NVL(E.DIC_VALUE,'') UNIT,\n" );
            sql.append("       B.IN_AMOUNT,\n" );
            sql.append("       NVL(D.SALE_PRICE,0) PCH_PRICE,\n" );
            sql.append("       NVL(D.SALE_PRICE,0) * B.IN_AMOUNT PCH_AMOUNT,\n" );
            sql.append("       NVL(B.PLAN_PRICE,0) PLAN_PRICE,\n" );
            sql.append("       NVL(B.PLAN_PRICE,0) * B.IN_AMOUNT PLAN_AMOUNT,\n" );
            sql.append("       ROWNUM\n" );
            sql.append("  FROM PT_BU_STOCK_IN            A,\n" );
            sql.append("       PT_BU_STOCK_IN_DTL        B,\n" );
            sql.append("       PT_BA_INFO                D,\n" );
            sql.append("       DIC_TREE                  E\n" );
            sql.append(" WHERE 1 = 1\n" );
            sql.append("   AND A.IN_ID = "+IN_ID+"\n" );
            sql.append("   AND A.IN_ID = B.IN_ID\n" );
            sql.append("   AND B.PART_ID = D.PART_ID\n" );
            sql.append("   AND B.IN_AMOUNT >0 \n" );
            sql.append("   AND D.UNIT = E.ID\n" );
            sql.append(" ORDER BY B.PART_CODE ASC");
            qs = factory.select(null,sql.toString());
        }else{
        	StringBuffer sql= new StringBuffer();
            sql.append("SELECT B.PART_ID,\n" );
            sql.append("       B.PART_CODE,\n" );
            sql.append("       B.PART_NAME,\n" );
            sql.append("       D.PART_NO,\n" );
            sql.append("       NVL(E.DIC_VALUE,'') UNIT,\n" );
            sql.append("       B.IN_AMOUNT,\n" );
            sql.append("       NVL(D.SALE_PRICE,0) PCH_PRICE,\n" );
            sql.append("       NVL(D.SALE_PRICE,0) * B.IN_AMOUNT PCH_AMOUNT,\n" );
            sql.append("       NVL(B.PLAN_PRICE,0) PLAN_PRICE,\n" );
            sql.append("       NVL(B.PLAN_PRICE,0) * B.IN_AMOUNT PLAN_AMOUNT,\n" );
            sql.append("       ROWNUM\n" );
            sql.append("  FROM PT_BU_STOCK_IN            A,\n" );
            sql.append("       PT_BU_STOCK_IN_DTL        B,\n" );
            sql.append("       PT_BA_INFO                D,\n" );
            sql.append("       DIC_TREE                  E\n" );
            sql.append(" WHERE 1 = 1\n" );
            sql.append("   AND A.IN_ID = "+IN_ID+"\n" );
            sql.append("   AND A.IN_ID = B.IN_ID\n" );
            sql.append("   AND B.PART_ID = D.PART_ID\n" );
            sql.append("   AND B.IN_AMOUNT >0 \n" );
            sql.append("   AND D.UNIT = E.ID\n" );
            sql.append(" ORDER BY B.PART_CODE ASC");
            qs = factory.select(null,sql.toString());
        }
        
        //执行方法，不需要传递conn参数
       
        return qs;
    }
    public QuerySet queryRetInInfo(User user, String IN_ID) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT A.IN_ID,\n" );
        sql.append("       A.IN_NO,\n" );
        sql.append("       A.WAREHOUSE_CODE,\n" );
        sql.append("       A.WAREHOUSE_NAME,\n" );
        sql.append("       A.ORDER_ID,\n" );
        sql.append("       B.RETURN_NO,\n" );
        sql.append("       B.APPLY_ORG_NAME,\n" );
        sql.append("       C.DIC_VALUE IN_TYPE,\n" );
        sql.append("       D.ONAME OUT_UNIT,\n" );
        sql.append("       TO_CHAR(SYSDATE, 'YYYY-MM-DD') PRINT_DATE,\n" );
        sql.append("       A.IN_DATE,\n" );
        sql.append("       E.PLAN_AMOUNT,\n" );
        sql.append("       A.STATUS,\n" );
        sql.append("       A.OEM_COMPANY_ID\n" );
        sql.append("  FROM PT_BU_STOCK_IN A,\n" );
        sql.append("       PT_BU_RETURN_APPLY B,DIC_TREE C,TM_ORG D,\n" );
        sql.append("(SELECT SUM(A.IN_AMOUNT * A.PLAN_PRICE) PLAN_AMOUNT, A.IN_ID\n" );
        sql.append("          FROM PT_BU_STOCK_IN_DTL A\n" );
        sql.append("         WHERE 1=1\n" );
        sql.append("         GROUP BY A.IN_ID) E");
        sql.append(" WHERE A.ORDER_ID = B.RETURN_ID AND A.IN_ID = E.IN_ID AND A.IN_TYPE = C.ID AND B.APPLY_ORG_CODE = D.CODE\n" );
        sql.append("   AND A.IN_ID = "+ IN_ID + "\n");
        //执行方法，不需要传递conn参数
        qs = factory.select(null,sql.toString());
        return qs;
    }
    public QuerySet queryRetInDtlInfo(User user, String IN_ID,String flag) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
        if("1".equals(flag)){
        	StringBuffer sql= new StringBuffer();
            sql.append("SELECT B.PART_ID,\n" );
            sql.append("       B.PART_CODE,\n" );
            sql.append("       B.PART_NAME,\n" );
            sql.append("       D.PART_NO,\n" );
            sql.append("       NVL(E.DIC_VALUE,'') UNIT,\n" );
            sql.append("       B.RETURN_COUNT IN_AMOUNT,\n" );
            sql.append("       NVL(B.SALE_PRICE,0) PCH_PRICE,\n" );
            sql.append("       NVL(B.SALE_PRICE,0) * B.RETURN_COUNT PCH_AMOUNT,\n" );
            sql.append("       NVL(B.PLAN_PRICE,0) PLAN_PRICE,\n" );
            sql.append("       NVL(B.PLAN_PRICE,0) * B.RETURN_COUNT PLAN_AMOUNT,\n" );
            sql.append("       ROWNUM\n" );
            sql.append("  FROM PT_BU_STOCK_IN            A,\n" );
            sql.append("       PT_BU_RETURN_APPLY_DTL    B,\n" );
            sql.append("       PT_BA_INFO                D,\n" );
            sql.append("       DIC_TREE                  E\n" );
            sql.append(" WHERE 1 = 1\n" );
            sql.append("   AND A.IN_ID = "+IN_ID+"\n" );
            sql.append("   AND A.ORDER_ID = B.RETURN_ID\n" );
            sql.append("   AND B.PART_ID = D.PART_ID\n" );
            sql.append("   AND B.IN_AMOUNT >0 \n" );
            sql.append("   AND D.UNIT = E.ID(+)\n" );
            sql.append(" ORDER BY B.PART_CODE ASC");
            //执行方法，不需要传递conn参数
            qs = factory.select(null,sql.toString());
        }else{
        	StringBuffer sql= new StringBuffer();
            sql.append("SELECT B.PART_ID,\n" );
            sql.append("       B.PART_CODE,\n" );
            sql.append("       B.PART_NAME,\n" );
            sql.append("       D.PART_NO,\n" );
            sql.append("       NVL(E.DIC_VALUE,'') UNIT,\n" );
            sql.append("       B.RETURN_COUNT IN_AMOUNT,\n" );
            sql.append("       NVL(B.SALE_PRICE,0) PCH_PRICE,\n" );
            sql.append("       NVL(B.SALE_PRICE,0) * B.RETURN_COUNT PCH_AMOUNT,\n" );
            sql.append("       NVL(B.PLAN_PRICE,0) PLAN_PRICE,\n" );
            sql.append("       NVL(B.PLAN_PRICE,0) * B.RETURN_COUNT PLAN_AMOUNT,\n" );
            sql.append("       ROWNUM\n" );
            sql.append("  FROM PT_BU_STOCK_IN            A,\n" );
            sql.append("       PT_BU_RETURN_APPLY_DTL    B,\n" );
            sql.append("       PT_BA_INFO                D,\n" );
            sql.append("       DIC_TREE                  E\n" );
            sql.append(" WHERE 1 = 1\n" );
            sql.append("   AND A.IN_ID = "+IN_ID+"\n" );
            sql.append("   AND A.ORDER_ID = B.RETURN_ID\n" );
            sql.append("   AND B.PART_ID = D.PART_ID\n" );
            sql.append("   AND B.IN_AMOUNT >0 \n" );
            sql.append("   AND D.UNIT = E.ID(+)\n" );
            sql.append(" ORDER BY B.PART_CODE ASC");
            //执行方法，不需要传递conn参数
            qs = factory.select(null,sql.toString());
        }
        return qs;
    }
    public QuerySet queryOtherInInfo(User user, String IN_ID) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT A.IN_ID,\n" );
        sql.append("       A.IN_NO,\n" );
        sql.append("       A.WAREHOUSE_CODE,\n" );
        sql.append("       A.WAREHOUSE_NAME,\n" );
        sql.append("       A.ORDER_ID,\n" );
        sql.append("       A.ORDER_NO,\n" );
        sql.append("       C.DIC_VALUE IN_TYPE,\n" );
        sql.append("       TO_CHAR(SYSDATE, 'YYYY-MM-DD') PRINT_DATE,\n" );
        sql.append("       A.IN_DATE,\n" );
        sql.append("       A.STATUS,\n" );
        sql.append("       A.OEM_COMPANY_ID\n" );
        sql.append("  FROM PT_BU_STOCK_IN A,\n" );
        sql.append("       DIC_TREE C\n" );
        sql.append(" WHERE  A.IN_TYPE = C.ID \n" );
        sql.append("   AND A.IN_ID = "+ IN_ID + "\n");
        //执行方法，不需要传递conn参数
        qs = factory.select(null,sql.toString());
        return qs;
    }
    
    public QuerySet queryOtherInDtlInfo(User user, String IN_ID,String flag) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
        
        if("1".equals(flag)){
        	StringBuffer sql= new StringBuffer();
            sql.append("SELECT B.PART_ID,\n" );
            sql.append("       B.PART_CODE,\n" );
            sql.append("       B.PART_NAME,\n" );
            sql.append("       D.PART_NO,\n" );
            sql.append("       NVL(E.DIC_VALUE,'') UNIT,\n" );
            sql.append("       B.IN_AMOUNT,\n" );
            sql.append("       NVL(D.SALE_PRICE,0) PCH_PRICE,\n" );
            sql.append("       NVL(D.SALE_PRICE,0) * B.IN_AMOUNT PCH_AMOUNT,\n" );
            sql.append("       NVL(B.PLAN_PRICE,0) PLAN_PRICE,\n" );
            sql.append("       NVL(B.PLAN_PRICE,0) * B.IN_AMOUNT PLAN_AMOUNT,\n" );
            sql.append("       ROWNUM\n" );
            sql.append("  FROM PT_BU_STOCK_IN            A,\n" );
            sql.append("       PT_BU_STOCK_IN_DTL    	 B,\n" );
            sql.append("       PT_BA_INFO                D,\n" );
            sql.append("       DIC_TREE                  E\n" );
            sql.append(" WHERE 1 = 1\n" );
            sql.append("   AND A.IN_ID = "+IN_ID+"\n" );
            sql.append("   AND A.IN_ID = B.IN_ID\n" );
            sql.append("   AND B.PART_ID = D.PART_ID\n" );
            sql.append("   AND D.UNIT = E.ID(+)\n" );
            sql.append(" ORDER BY B.PART_CODE ASC");
            //执行方法，不需要传递conn参数
            qs = factory.select(null,sql.toString());
        }else{
        	StringBuffer sql= new StringBuffer();
            sql.append("SELECT B.PART_ID,\n" );
            sql.append("       B.PART_CODE,\n" );
            sql.append("       B.PART_NAME,\n" );
            sql.append("       D.PART_NO,\n" );
            sql.append("       NVL(E.DIC_VALUE,'') UNIT,\n" );
            sql.append("       B.IN_AMOUNT,\n" );
            sql.append("       NVL(D.SALE_PRICE,0) PCH_PRICE,\n" );
            sql.append("       NVL(D.SALE_PRICE,0) * B.IN_AMOUNT PCH_AMOUNT,\n" );
            sql.append("       NVL(B.PLAN_PRICE,0) PLAN_PRICE,\n" );
            sql.append("       NVL(B.PLAN_PRICE,0) * B.IN_AMOUNT PLAN_AMOUNT,\n" );
            sql.append("       ROWNUM\n" );
            sql.append("  FROM PT_BU_STOCK_IN            A,\n" );
            sql.append("       PT_BU_STOCK_IN_DTL    	 B,\n" );
            sql.append("       PT_BA_INFO                D,\n" );
            sql.append("       DIC_TREE                  E\n" );
            sql.append(" WHERE 1 = 1\n" );
            sql.append("   AND A.IN_ID = "+IN_ID+"\n" );
            sql.append("   AND A.IN_ID = B.IN_ID\n" );
            sql.append("   AND B.PART_ID = D.PART_ID\n" );
            sql.append("   AND D.UNIT = E.ID(+)\n" );
            sql.append(" ORDER BY B.PART_CODE ASC");
            //执行方法，不需要传递conn参数
            qs = factory.select(null,sql.toString());
        }
        //执行方法，不需要传递conn参数
        return qs;
    }
    public QuerySet getInType(String IN_ID) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT IN_TYPE FROM PT_BU_STOCK_IN WHERE IN_ID = "+IN_ID+"\n" );
        //执行方法，不需要传递conn参数
        qs = factory.select(null,sql.toString());
        return qs;
    }

}