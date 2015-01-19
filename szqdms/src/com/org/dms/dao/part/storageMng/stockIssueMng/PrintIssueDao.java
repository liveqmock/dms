package com.org.dms.dao.part.storageMng.stockIssueMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuIssueOrderVO;
import com.org.dms.vo.part.PtBuSaleOrderVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.SequenceUtil;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

import java.sql.CallableStatement;

/**
 * 打印发料单Dao
 *
 * @user : lichuang
 * @date : 2014-07-21
 */
public class PrintIssueDao extends BaseDAO {
    //定义instance
    public static final PrintIssueDao getInstance(ActionContext atx) {
        PrintIssueDao dao = new PrintIssueDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * 查询销售订单
     *
     * @param page
     * @param user
     * @param conditions
     * @return
     * @throws Exception
     */
    public BaseResultSet searchSaleOrder(PageManager page, User user, String conditions) throws Exception {
        String wheres = conditions;
        wheres += " AND A.ORDER_ID = B.ORDER_ID\n";
        wheres += " AND B.CHECK_USER = C.ACCOUNT\n";
        wheres += " AND A.ORDER_STATUS = " + DicConstant.DDZT_03 + "\n";
        wheres += " AND A.SHIP_STATUS IN( " + DicConstant.DDFYZT_01 + "," + DicConstant.DDFYZT_02 + ")\n";
        wheres += " AND B.CHECK_RESULT = " + DicConstant.DDZT_03 + "\n";
        wheres += " AND A.STATUS = " + DicConstant.YXBS_01 + "\n";
        wheres += " AND A.OEM_COMPANY_ID=" + user.getOemCompanyId() + "\n";
        wheres += " AND ('1' IN (SELECT 1 FROM PT_BU_ISSUE_ORDER D WHERE A.ORDER_ID = D.ORDER_ID AND D.PRINT_STATUS = "+DicConstant.DYZT_01+") OR '0' = (SELECT COUNT(1) FROM PT_BU_ISSUE_ORDER D WHERE A.ORDER_ID = D.ORDER_ID))";
        wheres += " AND (SELECT COUNT(*) FROM PT_BU_STOCK_OCCUPY_LOG WHERE ORDER_ID =A.ORDER_ID)>0\n";
        wheres += " AND A.ORDER_TYPE <>"+DicConstant.DDLX_05+"\n";
        wheres += " AND B.CHECK_DATE =(SELECT MAX(S.CHECK_DATE)  FROM PT_BU_SALE_ORDER_CHECK S WHERE A.ORDER_ID= S.ORDER_ID)\n";
        wheres += " AND (SELECT SUM(M.AUDIT_COUNT) FROM PT_BU_SALE_ORDER_DTL M WHERE A.ORDER_ID = M.ORDER_ID)>0\n";
        wheres += " ORDER BY A.APPLY_DATE DESC\n";
        page.setFilter(wheres);
        //定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT A.ORDER_ID,\n");
        sql.append("       A.ORDER_NO,\n");
        sql.append("       A.ORDER_TYPE,\n");
        sql.append("       A.SHIP_STATUS,\n");
        sql.append("       A.ORG_CODE,\n");
        sql.append("       A.ORG_NAME,\n");
        sql.append("       A.APPLY_DATE,\n");
        sql.append("       A.WAREHOUSE_ID,\n");
        sql.append("       B.CHECK_USER,\n");
        sql.append("       B.CHECK_DATE\n");
        sql.append("  FROM PT_BU_SALE_ORDER A, PT_BU_SALE_ORDER_CHECK B, TM_USER C\n");

        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("ORDER_TYPE", DicConstant.DDLX);
        bs.setFieldDic("SHIP_STATUS", DicConstant.DDFYZT);
        bs.setFieldUserID("CHECK_USER");
        bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
        bs.setFieldDateFormat("CHECK_DATE", "yyyy-MM-dd HH:mm:ss");
        return bs;
    }

    /**
     * 查询销售订单配件
     *
     * @param page
     * @param user
     * @param conditions
     * @return
     * @throws Exception
     */
    public BaseResultSet searchSaleOrderPart(PageManager page, User user, String conditions, String ORDER_ID) throws Exception {
        String wheres = conditions;
        wheres += " AND A.ORDER_ID = " + ORDER_ID + "\n";
        wheres += " AND A.PART_ID = B.PART_ID\n";
        wheres += " AND A.PART_ID = C.PART_ID\n";
        wheres += " AND A.ORDER_ID = D.ORDER_ID\n";
        wheres += " AND D.WAREHOUSE_ID = B.WAREHOUSE_ID\n";
        wheres += " AND NVL(A.AUDIT_COUNT,0) >0\n";
        wheres += " AND B.STATUS = " + DicConstant.YXBS_01 + "\n";
        //wheres += " AND B.OEM_COMPANY_ID=" + user.getOemCompanyId() + "\n";
        wheres += " ORDER BY A.PART_CODE ASC\n";
        page.setFilter(wheres);
        //定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT A.PART_ID,\n");
        sql.append("       A.PART_CODE,\n");
        sql.append("       A.PART_NAME,\n");
        sql.append("       C.UNIT,\n");
        sql.append("       C.MIN_PACK,\n");
        sql.append("       C.MIN_UNIT,\n");
        sql.append("       A.IF_SUPPLIER,\n");
        sql.append("       A.SUPPLIER_ID,\n");
        sql.append("       A.SUPPLIER_CODE,\n");
        sql.append("       A.SUPPLIER_NAME,\n");
        sql.append("       A.ORDER_COUNT,\n");
        sql.append("       A.AUDIT_COUNT,\n");
        sql.append("       B.USER_NAME\n");
        sql.append("  FROM PT_BU_SALE_ORDER_DTL A, PT_BA_WAREHOUSE_KEEPER B, PT_BA_INFO C,PT_BU_SALE_ORDER D\n");
        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("IF_SUPPLIER", DicConstant.SF);
        bs.setFieldDic("UNIT", DicConstant.JLDW);
        bs.setFieldDic("MIN_UNIT", DicConstant.JLDW);
        return bs;
    }

    /**
     * 查询发料单
     *
     * @param page
     * @param user
     * @param conditions
     * @return
     * @throws Exception
     */
    public BaseResultSet searchIssueOrder(PageManager page, User user, String conditions, String ORDER_ID) throws Exception {
        String wheres = conditions;
        wheres += " AND A.ORDER_ID = " + ORDER_ID + "\n";
        wheres += " ORDER BY A.ISSUE_NO ASC\n";
        page.setFilter(wheres);
        //定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT A.ISSUE_ID,\n");
        sql.append("       A.ISSUE_NO,\n");
        sql.append("       A.PRINT_STATUS,\n");
        sql.append("       A.ISSUE_STATUS,\n");
        sql.append("       A.USER_NAME,\n");
        sql.append("       A.KEEPER_ID\n");
        sql.append("  FROM PT_BU_ISSUE_ORDER A\n");
        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("PRINT_STATUS", DicConstant.DYZT);
        return bs;
    }

    /**
     * 查询发料单明细
     *
     * @param page
     * @param user
     * @param conditions
     * @return
     * @throws Exception
     */
    public BaseResultSet searchIssueDtl(PageManager page, User user, String conditions, String ISSUE_ID) throws Exception {
        String wheres = conditions;
        wheres += " AND A.ISSUE_ID = " + ISSUE_ID + "\n";
        wheres += " AND A.PART_ID = B.PART_ID\n";
        wheres += " ORDER BY A.PART_CODE ASC\n";
        page.setFilter(wheres);
        //定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT A.PART_ID,\n");
        sql.append("       A.PART_CODE,\n");
        sql.append("       A.PART_NAME,\n");
        sql.append("       B.UNIT,\n");
        sql.append("       B.MIN_PACK,\n");
        sql.append("       B.MIN_UNIT,\n");
        sql.append("       A.POSITION_CODE,\n");
        sql.append("       A.SHOULD_COUNT,\n");
        sql.append("       A.REAL_COUNT,\n");
        sql.append("       A.REMARK\n");
        sql.append("  FROM PT_BU_ISSUE_ORDER_DTL A,PT_BA_INFO B\n");
        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("UNIT", DicConstant.JLDW);
        bs.setFieldDic("MIN_UNIT", DicConstant.JLDW);
        return bs;
    }
    
    
    public BaseResultSet searchPartTitle(PageManager page, User user, String conditions, String ISSUE_ID) throws Exception {
        //定义返回结果集
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.SUPPLIER_NAME,\n" );
        sql.append("       T.PART_CODE,\n" );
        sql.append("       T.PART_NAME,\n" );
        sql.append("       NVL(T.SHOULD_COUNT, 0) || T2.DIC_VALUE COUNT\n" );
        sql.append("  FROM PT_BU_ISSUE_ORDER_DTL T, PT_BA_INFO T1, DIC_TREE T2\n" );
        sql.append(" WHERE T.PART_ID = T1.PART_ID\n" );
        sql.append("   AND T1.UNIT = T2.ID\n" );
        sql.append("   AND T.ISSUE_ID = "+ISSUE_ID+"");
        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
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
     * 修改销售订单
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean updateSaleOrder(PtBuSaleOrderVO vo)
            throws Exception {
        return factory.update(vo);
    }

    /**
     * 修改发料单
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean updateIssueOrder(PtBuIssueOrderVO vo)
            throws Exception {
        return factory.update(vo);
    }
    
    public QuerySet getWarehouseId(String ORDER_ID) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
        StringBuilder sql= new StringBuilder();
        sql.append("SELECT 1 FROM PT_BU_SALE_ORDER WHERE DIR_SOURCE_ORDER_ID = "+ORDER_ID+"\n" );
        //执行方法，不需要传递conn参数
        qs = factory.select(null,sql.toString());
        return qs;
    }

    public QuerySet partIdCheckWarehouseKeeper(String ORDER_ID,String warehouseId) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
        StringBuilder sql= new StringBuilder();
        sql.append("SELECT PART_CODE\n" );
        sql.append("  FROM PT_BU_SALE_ORDER_DTL\n" );
        sql.append(" WHERE ORDER_ID = '"+ORDER_ID+"'\n" );
        sql.append("   AND PART_ID NOT IN\n" );
        sql.append("       (SELECT A.PART_ID\n" );
        sql.append("          FROM PT_BU_SALE_ORDER_DTL A, PT_BA_WAREHOUSE_KEEPER B\n" );
        sql.append("         WHERE A.ORDER_ID = '"+ORDER_ID+"'\n" );
        sql.append("           AND A.PART_ID = B.PART_ID AND B.WAREHOUSE_ID =(SELECT WAREHOUSE_ID FROM PT_BU_SALE_ORDER WHERE "+warehouseId+"))\n" );

        //执行方法，不需要传递conn参数
        qs = factory.select(null,sql.toString());
        return qs;
    }

    public QuerySet getOrderShipStatus(String ORDER_ID) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
        StringBuilder sql= new StringBuilder();
        sql.append("SELECT SHIP_STATUS\n" );
        sql.append("  FROM PT_BU_SALE_ORDER\n" );
        sql.append(" WHERE ORDER_ID = '"+ORDER_ID+"'\n" );
        //执行方法，不需要传递conn参数
        qs = factory.select(null,sql.toString());
        return qs;
    }
    /**
     * 调用存储过程生成发料单
     *
     * @param ORDER_ID 销售订单ID
     * @throws Exception
     */
    public void createIssueOrder(String ORDER_ID) throws Exception {
        CallableStatement proc = null;
        proc = factory.getConnection().prepareCall("{call P_PART_CREATE_ISSUE_ORDER(?)}");
        proc.setString(1, ORDER_ID);
        proc.execute();
    }
    
    
    public QuerySet queryIssueOrderDtl(User user, String ISSUE_ID) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT A.PART_ID,\n" );
        sql.append("       A.PART_CODE,\n" );
        sql.append("       A.PART_NAME,\n" );
        sql.append("       NVL(C.DIC_VALUE,'') UNIT,\n" );
        sql.append("       B.MIN_PACK,\n" );
        sql.append("       NVL(D.DIC_VALUE,'') MIN_UNIT,\n" );
        sql.append("       A.POSITION_CODE,\n" );
        sql.append("       A.SHOULD_COUNT,\n" );
        sql.append("       A.REAL_COUNT,\n" );
        sql.append("       A.REMARK\n" );
        sql.append("  FROM PT_BU_ISSUE_ORDER_DTL A, PT_BA_INFO B,DIC_TREE C,DIC_TREE D\n" );
        sql.append(" WHERE 1 = 1\n" );
        sql.append("   AND A.ISSUE_ID = "+ISSUE_ID+"\n" );
        sql.append("   AND B.UNIT = C.ID(+)\n" );
        sql.append("   AND B.MIN_UNIT = D.ID(+)\n" );
        sql.append("   AND A.PART_ID = B.PART_ID\n" );
        sql.append(" ORDER BY A.PART_CODE ASC");
        //执行方法，不需要传递conn参数
        qs = factory.select(null,sql.toString());
        return qs;
    }
    public QuerySet queryIssueOrderInfo(User user, String ISSUE_ID) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT B.ORG_NAME,\n" );
        sql.append("       A.ISSUE_NO,\n" );
        sql.append("       A.USER_NAME KEEPER_USER,\n" );
        sql.append("       D.PERSON_NAME CHECK_USER,\n" );
        sql.append("       E.DIC_VALUE TRANS_TYPE,\n" );
        sql.append("       B.ORDER_TYPE,\n" );
        sql.append("       B.CUSTORM_NAME,\n" );
        sql.append("       TO_CHAR(SYSDATE, 'YYYY-MM-DD') PRINT_DATE\n" );
        sql.append("  FROM PT_BU_ISSUE_ORDER      A,\n" );
        sql.append("       PT_BU_SALE_ORDER       B,\n" );
        sql.append("       (SELECT ORDER_ID, CHECK_USER, MAX(CHECK_DATE) CHECK_DATE FROM PT_BU_SALE_ORDER_CHECK WHERE CHECK_RESULT = 202203 GROUP BY ORDER_ID, CHECK_USER) C,\n" );
        sql.append("       TM_USER                D,\n" );
        sql.append("       DIC_TREE               E\n" );
        sql.append(" WHERE A.ORDER_ID = B.ORDER_ID\n" );
        sql.append("   AND B.ORDER_ID = C.ORDER_ID\n" );
        sql.append("   AND B.TRANS_TYPE = E.ID\n" );
        sql.append("   AND C.CHECK_USER = D.ACCOUNT\n" );
        sql.append("   AND A.ISSUE_ID = "+ISSUE_ID+" ORDER BY C.CHECK_DATE DESC\n");
        //执行方法，不需要传递conn参数
        qs = factory.select(null,sql.toString());
        return qs;
    }
    
    
    public QuerySet getTitleInfo(User user, String ISSUE_ID) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.SUPPLIER_NAME,\n" );
        sql.append("       T.PART_CODE,\n" );
        sql.append("       T.PART_NAME,\n" );
        sql.append("       T.POSITION_CODE,\n" );
        sql.append("       T4.ORG_NAME,\n" );
        sql.append("       NVL(T.SHOULD_COUNT, 0) || T2.DIC_VALUE COUNT, NVL(T.SHOULD_COUNT, 0) AMOUNT,NVL(T2.DIC_VALUE,'') UNIT\n" );
        sql.append("  FROM PT_BU_ISSUE_ORDER_DTL T, PT_BA_INFO T1, DIC_TREE T2,PT_BU_ISSUE_ORDER T3,PT_BU_SALE_ORDER T4\n" );
        sql.append(" WHERE T.PART_ID = T1.PART_ID AND T3.ORDER_ID = T4.ORDER_ID\n" );
        sql.append("   AND T1.UNIT = T2.ID(+) AND T.ISSUE_ID = T3.ISSUE_ID\n" );
        sql.append("   AND T.ISSUE_ID = "+ISSUE_ID+" ORDER BY T.PART_CODE ASC");
        //执行方法，不需要传递conn参数
        qs = factory.select(null,sql.toString());
        return qs;
    }
    
    
    public QuerySet checkTH(String ISSUE_ID) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
        //执行方法，不需要传递conn参数
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.DIRECT_TYPE_CODE\n" );
        sql.append("FROM PT_BU_SALE_ORDER T ,PT_BU_ISSUE_ORDER T1\n" );
        sql.append("WHERE T.ORDER_ID = T1.ORDER_ID\n" );
        sql.append("AND T1.ISSUE_ID = "+ISSUE_ID+"");
        qs = factory.select(null,sql.toString());
        return qs;
    }
}