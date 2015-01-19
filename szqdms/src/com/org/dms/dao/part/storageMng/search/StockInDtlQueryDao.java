package com.org.dms.dao.part.storageMng.search;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.mvc.context.ActionContext;

/**
 * 
 * ClassName: OrderSplitQueryDao 
 * Function: 采购订单入库明细
 * date: 2014年10月30日 下午7:35:40
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class StockInDtlQueryDao extends BaseDAO {
    
    public static final StockInDtlQueryDao getInstance(ActionContext ac){
        StockInDtlQueryDao dao = new StockInDtlQueryDao();
        ac.setDBFactory(dao.factory);
        return dao;
    }
    
    /**
     * 
     * queryList: 查询表单查询
     * @author fuxiao
     * Date:2014年10月23日
     *
     */
    public BaseResultSet queryList(PageManager pageManager, String conditions) throws Exception{
        pageManager.setFilter(conditions + " ORDER BY T.IN_DATE DESC");
        BaseResultSet rs = null;
        StringBuilder sql= new StringBuilder();
        sql.append("SELECT T.PART_CODE,T.PART_NAME,T.IN_TYPE,T.WAREHOUSE_NAME,T.DISTRIBUTION_NO,T.ORDER_NO,T.IN_NO,\n" );
        sql.append("       T.IN_DATE,T.PRINT_DATE,T.IN_AMOUNT,T.PLAN_PRICE, T.SALE_PRICE, DECODE(T.IN_TYPE, 201303, TO_CHAR(NVL(T.IN_AMOUNT, 0) * NVL(T.SALE_PRICE, 0)), '') SALE_AMOUNT, T.PLAN_AMOUNT,T.PCH_PRICE,T.PCH_AMOUNT,\n" );
        sql.append("       T.SUPPLIER_CODE,T.SUPPLIER_NAME,T.CHECK_USER FROM\n" );
        // 采购入库
        sql.append("  (SELECT B.PART_CODE,B.PART_NAME,A.IN_TYPE,A.WAREHOUSE_NAME,\n" );
        sql.append("      D.DISTRIBUTION_NO,A.ORDER_NO,A.IN_NO,A.IN_DATE,A.PRINT_DATE,B.IN_AMOUNT,B.PLAN_PRICE, '' SALE_PRICE, \n" );
        sql.append("      B.PLAN_AMOUNT,B.PCH_PRICE,B.PCH_AMOUNT,C.SUPPLIER_CODE,C.SUPPLIER_NAME,A.BUYER CHECK_USER \n" );
        sql.append("  FROM PT_BU_STOCK_IN A,PT_BU_STOCK_IN_DTL B,PT_BU_PCH_ORDER_SPLIT C,PT_BU_PCH_ORDER_SPLIT_DTL D\n" );
        sql.append("  WHERE A.IN_ID=B.IN_ID AND A.IN_STATUS='"+DicConstant.RKDZT_02+"' AND A.IN_TYPE = '"+DicConstant.RKLX_01+"' AND A.ORDER_ID=C.SPLIT_ID AND B.PART_ID=D.PART_ID AND C.SPLIT_ID=D.SPLIT_ID\n" );
        // 销售退件入库
        sql.append("  UNION ALL\n" );
        sql.append("  SELECT B.PART_CODE,B.PART_NAME,A.IN_TYPE,A.WAREHOUSE_NAME,'' DISTRIBUTION_NO,C.RETURN_NO ORDER_NO,\n" );
        sql.append("      A.IN_NO,A.IN_DATE,A.PRINT_DATE,B.IN_AMOUNT,B.PLAN_PRICE, (\n" +
					"  SELECT TO_CHAR(AD.SALE_PRICE)\n" + 
					"    FROM PT_BU_RETURN_APPLY_DTL AD\n" + 
					"   WHERE AD.RETURN_ID = C.RETURN_ID\n" + 
					"     AND AD.PART_ID = B.PART_ID\n" + 
					") SALE_PRICE, B.PLAN_AMOUNT,B.PCH_PRICE,B.PCH_AMOUNT,\n" );
        sql.append("      C.APPLY_ORG_CODE SUPPLIER_CODE,C.APPLY_ORG_NAME SUPPLIER_NAME,C.CHECK_USER CHECK_USER \n" );
        sql.append("  FROM PT_BU_STOCK_IN A,PT_BU_STOCK_IN_DTL B,PT_BU_RETURN_APPLY C\n" );
        sql.append("  WHERE A.IN_ID=B.IN_ID AND A.IN_STATUS='"+DicConstant.RKDZT_02+"' AND A.IN_TYPE = '"+DicConstant.RKLX_03+"' AND A.ORDER_ID=C.RETURN_ID(+)\n" );
        // 移库入库,其他入库
        sql.append("  UNION ALL\n" );
        sql.append("  SELECT B.PART_CODE,B.PART_NAME,A.IN_TYPE,A.WAREHOUSE_NAME,'' DISTRIBUTION_NO,C.OUT_NO ORDER_NO,\n" );
        sql.append("      A.IN_NO,A.IN_DATE,A.PRINT_DATE,B.IN_AMOUNT,B.PLAN_PRICE, '' ,B.PLAN_AMOUNT,B.PCH_PRICE,B.PCH_AMOUNT,\n" );
        sql.append("      C.WAREHOUSE_CODE SUPPLIER_CODE,C.WAREHOUSE_NAME SUPPLIER_NAME,A.KEEPER_MAN CHECK_USER \n" );
        sql.append("  FROM PT_BU_STOCK_IN A,PT_BU_STOCK_IN_DTL B,PT_BU_STOCK_OUT C\n" );
        sql.append("  WHERE A.IN_ID=B.IN_ID AND A.IN_STATUS='"+DicConstant.RKDZT_02+"' AND A.IN_TYPE IN ('"+DicConstant.RKLX_02+"','"+DicConstant.RKLX_04+"') AND A.IN_NO=C.OUT_NO(+)) T");
        rs = this.factory.select(sql.toString(), pageManager);
        rs.setFieldUserID("CHECK_USER");
        rs.setFieldDateFormat("IN_DATE", "yyyy-MM-dd");
        rs.setFieldDateFormat("PRINT_DATE", "yyyy-MM-dd");
        rs.setFieldDic("IN_TYPE", DicConstant.RKLX);
        return rs;
    }
    
    /**
     * 
     * queryDownInfo: 查询需要下载的数据
     * @author fuxiao
     * Date:2014年10月23日
     *
     */
    public QuerySet queryDownInfo(String conditions) throws Exception {

        StringBuilder sql= new StringBuilder();
        sql.append("SELECT T.PART_CODE,T.PART_NAME,(SELECT DIC_VALUE FROM DIC_TREE WHERE ID = T.IN_TYPE) IN_TYPE," );
        sql.append("       T.WAREHOUSE_NAME,T.DISTRIBUTION_NO,T.ORDER_NO,T.IN_NO,\n" );
        sql.append("       T.IN_DATE,T.PRINT_DATE,T.IN_AMOUNT,T.PLAN_PRICE, T.SALE_PRICE, DECODE(T.IN_TYPE, 201303, TO_CHAR(NVL(T.IN_AMOUNT, 0) * NVL(T.SALE_PRICE, 0)), '') SALE_AMOUNT, T.PLAN_AMOUNT,T.PCH_PRICE,T.PCH_AMOUNT,\n" );
        sql.append("       T.SUPPLIER_CODE,T.SUPPLIER_NAME,(SELECT PERSON_NAME FROM TM_USER WHERE ACCOUNT=T.CHECK_USER) CHECK_USER FROM\n" );
        // 采购入库
        sql.append("  (SELECT B.PART_CODE,B.PART_NAME,A.IN_TYPE,A.WAREHOUSE_NAME,\n" );
        sql.append("      D.DISTRIBUTION_NO,A.ORDER_NO,A.IN_NO,A.IN_DATE,A.PRINT_DATE,B.IN_AMOUNT,B.PLAN_PRICE, '' SALE_PRICE,\n" );
        sql.append("      B.PLAN_AMOUNT,B.PCH_PRICE,B.PCH_AMOUNT,C.SUPPLIER_CODE,C.SUPPLIER_NAME,A.BUYER CHECK_USER \n" );
        sql.append("  FROM PT_BU_STOCK_IN A,PT_BU_STOCK_IN_DTL B,PT_BU_PCH_ORDER_SPLIT C,PT_BU_PCH_ORDER_SPLIT_DTL D\n" );
        sql.append("  WHERE A.IN_ID=B.IN_ID AND A.IN_STATUS='"+DicConstant.RKDZT_02+"' AND A.IN_TYPE = '"+DicConstant.RKLX_01+"' AND A.ORDER_ID=C.SPLIT_ID AND C.SPLIT_ID=D.SPLIT_ID AND B.PART_ID =D.PART_ID\n" );
        // 销售退件入库
        sql.append("  UNION ALL\n" );
        sql.append("  SELECT B.PART_CODE,B.PART_NAME,A.IN_TYPE,A.WAREHOUSE_NAME,'' DISTRIBUTION_NO,C.RETURN_NO ORDER_NO,\n" );
        sql.append("      A.IN_NO,A.IN_DATE,A.PRINT_DATE,B.IN_AMOUNT,B.PLAN_PRICE, (\n" +
					"  SELECT TO_CHAR(AD.SALE_PRICE)\n" + 
					"    FROM PT_BU_RETURN_APPLY_DTL AD\n" + 
					"   WHERE AD.RETURN_ID = C.RETURN_ID\n" + 
					"     AND AD.PART_ID = B.PART_ID\n" + 
					") SALE_PRICE, B.PLAN_AMOUNT,B.PCH_PRICE,B.PCH_AMOUNT,\n" );
        sql.append("      C.APPLY_ORG_CODE SUPPLIER_CODE,C.APPLY_ORG_NAME SUPPLIER_NAME,C.CHECK_USER CHECK_USER \n" );
        sql.append("  FROM PT_BU_STOCK_IN A,PT_BU_STOCK_IN_DTL B,PT_BU_RETURN_APPLY C\n" );
        sql.append("  WHERE A.IN_ID=B.IN_ID AND A.IN_STATUS='"+DicConstant.RKDZT_02+"' AND A.IN_TYPE = '"+DicConstant.RKLX_03+"' AND A.ORDER_ID=C.RETURN_ID(+)\n" );
        // 移库入库,其他入库
        sql.append("  UNION ALL\n" );
        sql.append("  SELECT B.PART_CODE,B.PART_NAME,A.IN_TYPE,A.WAREHOUSE_NAME,'' DISTRIBUTION_NO,C.OUT_NO ORDER_NO,\n" );
        sql.append("      A.IN_NO,A.IN_DATE,A.PRINT_DATE,B.IN_AMOUNT,B.PLAN_PRICE, '', B.PLAN_AMOUNT,B.PCH_PRICE,B.PCH_AMOUNT,\n" );
        sql.append("      C.WAREHOUSE_CODE SUPPLIER_CODE,C.WAREHOUSE_NAME SUPPLIER_NAME,A.KEEPER_MAN CHECK_USER \n" );
        sql.append("  FROM PT_BU_STOCK_IN A,PT_BU_STOCK_IN_DTL B,PT_BU_STOCK_OUT C\n" );
        sql.append("  WHERE A.IN_ID=B.IN_ID AND A.IN_STATUS='"+DicConstant.RKDZT_02+"' AND A.IN_TYPE IN ('"+DicConstant.RKLX_02+"','"+DicConstant.RKLX_04+"') AND A.IN_NO=C.OUT_NO(+)) T");

        return this.factory.select(null,sql.toString() + " WHERE " + conditions + " ORDER BY T.IN_DATE DESC");
    }
    
    
    public BaseResultSet getAmount(PageManager page, String conditions) throws Exception
    {
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT NVL(SUM(X.PLAN_AMOUNT),0) PLAN_AMOUNT,NVL(SUM(X.PCH_AMOUNT),0) PCH_AMOUNT FROM\n" );
        sql.append("(SELECT T.PART_CODE,\n" );
        sql.append("       T.PART_NAME,\n" );
        sql.append("       T.IN_TYPE,\n" );
        sql.append("       T.WAREHOUSE_NAME,\n" );
        sql.append("       T.DISTRIBUTION_NO,\n" );
        sql.append("       T.ORDER_NO,\n" );
        sql.append("       T.IN_NO,\n" );
        sql.append("       T.IN_DATE,\n" );
        sql.append("       T.PRINT_DATE,\n" );
        sql.append("       T.IN_AMOUNT,\n" );
        sql.append("       T.PLAN_PRICE,\n" );
        sql.append("       T.SALE_PRICE,\n" );
        sql.append("       DECODE(T.IN_TYPE,\n" );
        sql.append("              201303,\n" );
        sql.append("              TO_CHAR(NVL(T.IN_AMOUNT, 0) * NVL(T.SALE_PRICE, 0)),\n" );
        sql.append("              '') SALE_AMOUNT,\n" );
        sql.append("       T.PLAN_AMOUNT,\n" );
        sql.append("       T.PCH_PRICE,\n" );
        sql.append("       T.PCH_AMOUNT,\n" );
        sql.append("       T.SUPPLIER_CODE,\n" );
        sql.append("       T.SUPPLIER_NAME,\n" );
        sql.append("       T.CHECK_USER\n" );
        sql.append("  FROM (SELECT B.PART_CODE,\n" );
        sql.append("               B.PART_NAME,\n" );
        sql.append("               A.IN_TYPE,\n" );
        sql.append("               A.WAREHOUSE_NAME,\n" );
        sql.append("               D.DISTRIBUTION_NO,\n" );
        sql.append("               A.ORDER_NO,\n" );
        sql.append("               A.IN_NO,\n" );
        sql.append("               A.IN_DATE,\n" );
        sql.append("               A.PRINT_DATE,\n" );
        sql.append("               B.IN_AMOUNT,\n" );
        sql.append("               B.PLAN_PRICE,\n" );
        sql.append("               '' SALE_PRICE,\n" );
        sql.append("               B.PLAN_AMOUNT,\n" );
        sql.append("               B.PCH_PRICE,\n" );
        sql.append("               B.PCH_AMOUNT,\n" );
        sql.append("               C.SUPPLIER_CODE,\n" );
        sql.append("               C.SUPPLIER_NAME,\n" );
        sql.append("               A.BUYER CHECK_USER\n" );
        sql.append("          FROM PT_BU_STOCK_IN            A,\n" );
        sql.append("               PT_BU_STOCK_IN_DTL        B,\n" );
        sql.append("               PT_BU_PCH_ORDER_SPLIT     C,\n" );
        sql.append("               PT_BU_PCH_ORDER_SPLIT_DTL D\n" );
        sql.append("         WHERE A.IN_ID = B.IN_ID\n" );
        sql.append("           AND A.IN_STATUS = '201502'\n" );
        sql.append("           AND A.IN_TYPE = '201301'\n" );
        sql.append("           AND A.ORDER_ID = C.SPLIT_ID\n" );
        sql.append("           AND B.PART_ID = D.PART_ID\n" );
        sql.append("           AND C.SPLIT_ID = D.SPLIT_ID\n" );
        sql.append("        UNION ALL\n" );
        sql.append("        SELECT B.PART_CODE,\n" );
        sql.append("               B.PART_NAME,\n" );
        sql.append("               A.IN_TYPE,\n" );
        sql.append("               A.WAREHOUSE_NAME,\n" );
        sql.append("               '' DISTRIBUTION_NO,\n" );
        sql.append("               C.RETURN_NO ORDER_NO,\n" );
        sql.append("               A.IN_NO,\n" );
        sql.append("               A.IN_DATE,\n" );
        sql.append("               A.PRINT_DATE,\n" );
        sql.append("               B.IN_AMOUNT,\n" );
        sql.append("               B.PLAN_PRICE,\n" );
        sql.append("               (SELECT TO_CHAR(AD.SALE_PRICE)\n" );
        sql.append("                  FROM PT_BU_RETURN_APPLY_DTL AD\n" );
        sql.append("                 WHERE AD.RETURN_ID = C.RETURN_ID\n" );
        sql.append("                   AND AD.PART_ID = B.PART_ID) SALE_PRICE,\n" );
        sql.append("               B.PLAN_AMOUNT,\n" );
        sql.append("               B.PCH_PRICE,\n" );
        sql.append("               B.PCH_AMOUNT,\n" );
        sql.append("               C.APPLY_ORG_CODE SUPPLIER_CODE,\n" );
        sql.append("               C.APPLY_ORG_NAME SUPPLIER_NAME,\n" );
        sql.append("               C.CHECK_USER CHECK_USER\n" );
        sql.append("          FROM PT_BU_STOCK_IN A, PT_BU_STOCK_IN_DTL B, PT_BU_RETURN_APPLY C\n" );
        sql.append("         WHERE A.IN_ID = B.IN_ID\n" );
        sql.append("           AND A.IN_STATUS = '201502'\n" );
        sql.append("           AND A.IN_TYPE = '201303'\n" );
        sql.append("           AND A.ORDER_ID = C.RETURN_ID(+)\n" );
        sql.append("        UNION ALL\n" );
        sql.append("        SELECT B.PART_CODE,\n" );
        sql.append("               B.PART_NAME,\n" );
        sql.append("               A.IN_TYPE,\n" );
        sql.append("               A.WAREHOUSE_NAME,\n" );
        sql.append("               '' DISTRIBUTION_NO,\n" );
        sql.append("               C.OUT_NO ORDER_NO,\n" );
        sql.append("               A.IN_NO,\n" );
        sql.append("               A.IN_DATE,\n" );
        sql.append("               A.PRINT_DATE,\n" );
        sql.append("               B.IN_AMOUNT,\n" );
        sql.append("               B.PLAN_PRICE,\n" );
        sql.append("               '',\n" );
        sql.append("               B.PLAN_AMOUNT,\n" );
        sql.append("               B.PCH_PRICE,\n" );
        sql.append("               B.PCH_AMOUNT,\n" );
        sql.append("               C.WAREHOUSE_CODE SUPPLIER_CODE,\n" );
        sql.append("               C.WAREHOUSE_NAME SUPPLIER_NAME,\n" );
        sql.append("               A.KEEPER_MAN CHECK_USER\n" );
        sql.append("          FROM PT_BU_STOCK_IN A, PT_BU_STOCK_IN_DTL B, PT_BU_STOCK_OUT C\n" );
        sql.append("         WHERE A.IN_ID = B.IN_ID\n" );
        sql.append("           AND A.IN_STATUS = '201502'\n" );
        sql.append("           AND A.IN_TYPE IN ('201302', '201304')\n" );
        sql.append("           AND A.IN_NO = C.OUT_NO(+)) T\n" );
        sql.append(" WHERE 1 = 1\n" );
        sql.append("   AND "+conditions+"\n" );
        sql.append(")X");
        bs = factory.select(sql.toString(), page);
        return bs;
    }
}
    