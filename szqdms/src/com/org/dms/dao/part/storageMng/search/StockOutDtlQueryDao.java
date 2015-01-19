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
public class StockOutDtlQueryDao extends BaseDAO {
    
    public static final StockOutDtlQueryDao getInstance(ActionContext ac){
        StockOutDtlQueryDao dao = new StockOutDtlQueryDao();
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

            BaseResultSet rs = null;
            String condition = conditions;
            condition += " ORDER BY T.OUT_DATE DESC";
            pageManager.setFilter(condition);
            StringBuilder sql= new StringBuilder();
            sql.append("SELECT T.PART_CODE,T.PART_NAME,T.WAREHOUSE_CODE,T.WAREHOUSE_NAME,T.ORDER_NO,T.OUT_NO," );
            sql.append("       T.OUT_TYPE,T.OTHER_OUT_TYPE,T.OUT_DATE,T.CLOSE_DATE,T.OUT_AMOUNT,T.PLAN_PRICE,T.PLAN_AMOUNT,T.SALE_PRICE," );
            sql.append("       T.SALE_AMOUNT,T.ORG_CODE,T.ORG_NAME FROM\n" );
            // 三包急件出库,销售出库,直营出库
            sql.append("    (SELECT B.PART_CODE,B.PART_NAME,A.WAREHOUSE_CODE,A.WAREHOUSE_NAME,A.ORDER_NO,A.OUT_NO," );
            sql.append("        A.OUT_TYPE,A.OTHER_OUT_TYPE,A.OUT_DATE,C.CLOSE_DATE,B.OUT_AMOUNT,B.PLAN_PRICE,B.PLAN_AMOUNT," );
            sql.append("        B.SALE_PRICE,B.SALE_AMOUNT,C.ORG_CODE,C.ORG_NAME " );
            sql.append("    FROM PT_BU_STOCK_OUT A,PT_BU_STOCK_OUT_DTL B,PT_BU_SALE_ORDER C\n" );
            sql.append("    WHERE A.OUT_ID = B.OUT_ID AND A.OUT_STATUS='"+DicConstant.CKDZT_02+"' AND A.OUT_TYPE IN ('"+DicConstant.CKLX_01+"','"+DicConstant.CKLX_02+"','"+DicConstant.CKLX_06+"')" );
            sql.append("    AND A.ORDER_NO=C.ORDER_NO(+)\n" );
            // 采购退货出库
            sql.append("    UNION ALL\n" );
            sql.append("    SELECT B.PART_CODE,B.PART_NAME,A.WAREHOUSE_CODE,A.WAREHOUSE_NAME,A.ORDER_NO,A.OUT_NO," );
            sql.append("        A.OUT_TYPE,A.OTHER_OUT_TYPE,A.OUT_DATE,R.CLOSE_DATE,B.OUT_AMOUNT,B.PLAN_PRICE," );
            sql.append("        B.PLAN_AMOUNT,B.SALE_PRICE,B.SALE_AMOUNT,R.SUPPLIER_CODE ORG_CODE,R.SUPPLIER_NAME ORG_NAME " );
            sql.append("    FROM PT_BU_STOCK_OUT A,PT_BU_STOCK_OUT_DTL B,PT_BU_PCH_RETURN R\n" );
            sql.append("    WHERE A.OUT_ID = B.OUT_ID AND A.OUT_STATUS='"+DicConstant.CKDZT_02+"' AND A.OUT_TYPE IN ('"+DicConstant.CKLX_04+"') AND A.ORDER_ID = R.RETURN_ID\n" );
            // 其他出库,移库出库
            sql.append("    UNION ALL\n" );
            sql.append("    SELECT B.PART_CODE,B.PART_NAME,A.WAREHOUSE_CODE,A.WAREHOUSE_NAME,C.IN_NO ORDER_NO,A.OUT_NO," );
            sql.append("        A.OUT_TYPE,A.OTHER_OUT_TYPE,A.OUT_DATE,TO_DATE('', 'YYYY-MM-DD') CLOSE_DATE,B.OUT_AMOUNT,B.PLAN_PRICE," );
            sql.append("        B.PLAN_AMOUNT,B.SALE_PRICE,B.SALE_AMOUNT,C.WAREHOUSE_CODE ORG_CODE,C.WAREHOUSE_NAME ORG_NAME" );
            sql.append("    FROM PT_BU_STOCK_OUT A,PT_BU_STOCK_OUT_DTL B,PT_BU_STOCK_IN C\n" );
            sql.append("    WHERE A.OUT_ID = B.OUT_ID AND A.OUT_STATUS='"+DicConstant.CKDZT_02+"' AND A.OUT_TYPE IN ('"+DicConstant.CKLX_03+"','"+DicConstant.CKLX_05+"') AND A.OUT_NO=C.IN_NO(+)) T");
            rs = this.factory.select(sql.toString(), pageManager);
            rs.setFieldDic("OUT_TYPE", DicConstant.CKLX);
            rs.setFieldDic("OTHER_OUT_TYPE", DicConstant.QTCKCKLX);
            rs.setFieldDateFormat("OUT_DATE", "yyyy-MM-dd");
            rs.setFieldDateFormat("CLOSE_DATE", "yyyy-MM-dd");
            rs.setFieldDateFormat("PRINT_DATE", "yyyy-MM-dd");

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
            sql.append("SELECT T.PART_CODE,T.PART_NAME,T.WAREHOUSE_CODE,T.WAREHOUSE_NAME,T.ORDER_NO,T.OUT_NO," );
            sql.append("       (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = T.OUT_TYPE) OUT_TYPE," );
            sql.append("       (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = T.OTHER_OUT_TYPE) OTHER_OUT_TYPE," );
            sql.append("       T.OUT_DATE,T.CLOSE_DATE,T.OUT_AMOUNT,T.PLAN_PRICE,T.PLAN_AMOUNT,T.SALE_PRICE," );
            sql.append("       T.SALE_AMOUNT,T.ORG_CODE,T.ORG_NAME FROM\n" );
            // 三包急件出库,销售出库,直营出库
            sql.append("    (SELECT B.PART_CODE,B.PART_NAME,A.WAREHOUSE_CODE,A.WAREHOUSE_NAME,A.ORDER_NO,A.OUT_NO," );
            sql.append("        A.OUT_TYPE,A.OTHER_OUT_TYPE,A.OUT_DATE,C.CLOSE_DATE,B.OUT_AMOUNT,B.PLAN_PRICE,B.PLAN_AMOUNT," );
            sql.append("        B.SALE_PRICE,B.SALE_AMOUNT,C.ORG_CODE,C.ORG_NAME " );
            sql.append("    FROM PT_BU_STOCK_OUT A,PT_BU_STOCK_OUT_DTL B,PT_BU_SALE_ORDER C\n" );
            sql.append("    WHERE A.OUT_ID = B.OUT_ID AND A.OUT_STATUS='"+DicConstant.CKDZT_02+"' AND A.OUT_TYPE IN ('"+DicConstant.CKLX_01+"','"+DicConstant.CKLX_02+"','"+DicConstant.CKLX_06+"')" );
            sql.append("    AND A.ORDER_NO=C.ORDER_NO(+)\n" );
            // 采购退货出库
            sql.append("    UNION ALL\n" );
            sql.append("    SELECT B.PART_CODE,B.PART_NAME,A.WAREHOUSE_CODE,A.WAREHOUSE_NAME,A.ORDER_NO,A.OUT_NO," );
            sql.append("        A.OUT_TYPE,A.OTHER_OUT_TYPE,A.OUT_DATE,TO_DATE(R.CLOSE_DATE, 'YYYY-MM-DD') CLOSE_DATE,B.OUT_AMOUNT,B.PLAN_PRICE," );
            sql.append("        B.PLAN_AMOUNT,B.SALE_PRICE,B.SALE_AMOUNT,R.SUPPLIER_CODE ORG_CODE,R.SUPPLIER_NAME ORG_NAME " );
            sql.append("    FROM PT_BU_STOCK_OUT A,PT_BU_STOCK_OUT_DTL B,PT_BU_PCH_RETURN R\n" );
            sql.append("    WHERE A.OUT_ID = B.OUT_ID AND A.OUT_STATUS='"+DicConstant.CKDZT_02+"' AND A.OUT_TYPE IN ('"+DicConstant.CKLX_04+"') AND A.ORDER_ID=R.RETURN_ID\n" );
            // 其他出库,移库出库
            sql.append("    UNION ALL\n" );
            sql.append("    SELECT B.PART_CODE,B.PART_NAME,A.WAREHOUSE_CODE,A.WAREHOUSE_NAME,C.IN_NO ORDER_NO,A.OUT_NO," );
            sql.append("        A.OUT_TYPE,A.OTHER_OUT_TYPE,A.OUT_DATE,TO_DATE('', 'YYYY-MM-DD') CLOSE_DATE,B.OUT_AMOUNT,B.PLAN_PRICE," );
            sql.append("        B.PLAN_AMOUNT,B.SALE_PRICE,B.SALE_AMOUNT,C.WAREHOUSE_CODE ORG_CODE,C.WAREHOUSE_NAME ORG_NAME" );
            sql.append("    FROM PT_BU_STOCK_OUT A,PT_BU_STOCK_OUT_DTL B,PT_BU_STOCK_IN C\n" );
            sql.append("    WHERE A.OUT_ID = B.OUT_ID AND A.OUT_STATUS='"+DicConstant.CKDZT_02+"' AND A.OUT_TYPE IN ('"+DicConstant.CKLX_03+"','"+DicConstant.CKLX_05+"') AND A.OUT_NO=C.IN_NO(+)) T");

        return this.factory.select(null,sql.toString() + " WHERE " + conditions +" ORDER BY T.OUT_DATE DESC");
    }
    
    public BaseResultSet getAmount(PageManager page, String conditions) throws Exception
    {
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT SUM(PLAN_AMOUNT) PLAN_AMOUNT ,SUM(SALE_AMOUNT) SALE_AMOUNT FROM (SELECT T.PART_CODE,\n" );
        sql.append("       T.PART_NAME,\n" );
        sql.append("       T.WAREHOUSE_CODE,\n" );
        sql.append("       T.WAREHOUSE_NAME,\n" );
        sql.append("       T.ORDER_NO,\n" );
        sql.append("       T.OUT_NO,\n" );
        sql.append("       T.OUT_TYPE,\n" );
        sql.append("       T.OTHER_OUT_TYPE,\n" );
        sql.append("       T.OUT_DATE,\n" );
        sql.append("       T.CLOSE_DATE,\n" );
        sql.append("       T.OUT_AMOUNT,\n" );
        sql.append("       T.PLAN_PRICE,\n" );
        sql.append("       T.PLAN_AMOUNT,\n" );
        sql.append("       T.SALE_PRICE,\n" );
        sql.append("       T.SALE_AMOUNT,\n" );
        sql.append("       T.ORG_CODE,\n" );
        sql.append("       T.ORG_NAME\n" );
        sql.append("  FROM (SELECT B.PART_CODE,\n" );
        sql.append("               B.PART_NAME,\n" );
        sql.append("               A.WAREHOUSE_CODE,\n" );
        sql.append("               A.WAREHOUSE_NAME,\n" );
        sql.append("               A.ORDER_NO,\n" );
        sql.append("               A.OUT_NO,\n" );
        sql.append("               A.OUT_TYPE,\n" );
        sql.append("               A.OTHER_OUT_TYPE,\n" );
        sql.append("               A.OUT_DATE,\n" );
        sql.append("               C.CLOSE_DATE,\n" );
        sql.append("               B.OUT_AMOUNT,\n" );
        sql.append("               B.PLAN_PRICE,\n" );
        sql.append("               B.PLAN_AMOUNT,\n" );
        sql.append("               B.SALE_PRICE,\n" );
        sql.append("               B.SALE_AMOUNT,\n" );
        sql.append("               C.ORG_CODE,\n" );
        sql.append("               C.ORG_NAME\n" );
        sql.append("          FROM PT_BU_STOCK_OUT A, PT_BU_STOCK_OUT_DTL B, PT_BU_SALE_ORDER C\n" );
        sql.append("         WHERE A.OUT_ID = B.OUT_ID\n" );
        sql.append("           AND A.OUT_STATUS = '201602'\n" );
        sql.append("           AND A.OUT_TYPE IN ('201401', '201402', '201406')\n" );
        sql.append("           AND A.ORDER_NO = C.ORDER_NO(+)\n" );
        sql.append("        UNION ALL\n" );
        sql.append("        SELECT B.PART_CODE,\n" );
        sql.append("               B.PART_NAME,\n" );
        sql.append("               A.WAREHOUSE_CODE,\n" );
        sql.append("               A.WAREHOUSE_NAME,\n" );
        sql.append("               A.ORDER_NO,\n" );
        sql.append("               A.OUT_NO,\n" );
        sql.append("               A.OUT_TYPE,\n" );
        sql.append("               A.OTHER_OUT_TYPE,\n" );
        sql.append("               A.OUT_DATE,\n" );
        sql.append("               R.CLOSE_DATE,\n" );
        sql.append("               B.OUT_AMOUNT,\n" );
        sql.append("               B.PLAN_PRICE,\n" );
        sql.append("               B.PLAN_AMOUNT,\n" );
        sql.append("               B.SALE_PRICE,\n" );
        sql.append("               B.SALE_AMOUNT,\n" );
        sql.append("               R.SUPPLIER_CODE  ORG_CODE,\n" );
        sql.append("               R.SUPPLIER_NAME  ORG_NAME\n" );
        sql.append("          FROM PT_BU_STOCK_OUT A, PT_BU_STOCK_OUT_DTL B, PT_BU_PCH_RETURN R\n" );
        sql.append("         WHERE A.OUT_ID = B.OUT_ID\n" );
        sql.append("           AND A.OUT_STATUS = '201602'\n" );
        sql.append("           AND A.OUT_TYPE IN ('201404')\n" );
        sql.append("           AND A.ORDER_ID = R.RETURN_ID\n" );
        sql.append("        UNION ALL\n" );
        sql.append("        SELECT B.PART_CODE,\n" );
        sql.append("               B.PART_NAME,\n" );
        sql.append("               A.WAREHOUSE_CODE,\n" );
        sql.append("               A.WAREHOUSE_NAME,\n" );
        sql.append("               C.IN_NO ORDER_NO,\n" );
        sql.append("               A.OUT_NO,\n" );
        sql.append("               A.OUT_TYPE,\n" );
        sql.append("               A.OTHER_OUT_TYPE,\n" );
        sql.append("               A.OUT_DATE,\n" );
        sql.append("               TO_DATE('', 'YYYY-MM-DD') CLOSE_DATE,\n" );
        sql.append("               B.OUT_AMOUNT,\n" );
        sql.append("               B.PLAN_PRICE,\n" );
        sql.append("               B.PLAN_AMOUNT,\n" );
        sql.append("               B.SALE_PRICE,\n" );
        sql.append("               B.SALE_AMOUNT,\n" );
        sql.append("               C.WAREHOUSE_CODE ORG_CODE,\n" );
        sql.append("               C.WAREHOUSE_NAME ORG_NAME\n" );
        sql.append("          FROM PT_BU_STOCK_OUT A, PT_BU_STOCK_OUT_DTL B, PT_BU_STOCK_IN C\n" );
        sql.append("         WHERE A.OUT_ID = B.OUT_ID\n" );
        sql.append("           AND A.OUT_STATUS = '201602'\n" );
        sql.append("           AND A.OUT_TYPE IN ('201403', '201405')\n" );
        sql.append("           AND A.OUT_NO = C.IN_NO(+)) T\n" );
        sql.append(" WHERE 1 = 1\n" );
        sql.append("   AND "+conditions+"\n" );
        sql.append(")");
        bs = factory.select(sql.toString(), page);
        return bs;
    }
}
    