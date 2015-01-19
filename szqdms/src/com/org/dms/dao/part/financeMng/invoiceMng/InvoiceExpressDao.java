package com.org.dms.dao.part.financeMng.invoiceMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuSaleInvoiceSummaryVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 邮寄信息维护Dao
 *
 * @user : lichuang
 * @date : 2014-07-14
 */
public class InvoiceExpressDao extends BaseDAO {
    //定义instance
    public static final InvoiceExpressDao getInstance(ActionContext atx) {
        InvoiceExpressDao dao = new InvoiceExpressDao();
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
        wheres += " AND A.ORDER_STATUS = " + DicConstant.DDZT_06 + "\n";
        wheres += " AND A.INVOICE_STATUS = " + DicConstant.KPZT_02 + "\n";
        wheres += " AND B.EXPRESS_STATUS = " + DicConstant.FPYJZT_01 + "\n";
        wheres += " AND A.ORDER_ID = B.ORDER_ID\n";
        wheres += " AND A.STATUS=" + DicConstant.YXBS_01 + "\n";
        wheres += " AND A.OEM_COMPANY_ID=" + user.getOemCompanyId() + "\n";
        wheres += " ORDER BY A.APPLY_DATE,A.ORG_CODE\n";
        page.setFilter(wheres);
        //定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT A.ORDER_ID,\n");
        sql.append("       A.ORDER_NO,\n");
        sql.append("       A.ORG_ID,\n");
        sql.append("       A.ORG_CODE,\n");
        sql.append("       A.ORG_NAME,\n");
        sql.append("       A.ORDER_STATUS,\n");
        sql.append("       A.INVOICE_STATUS,\n");
        sql.append("       A.REAL_AMOUNT,\n");
        sql.append("       B.SUM_ID,\n");
        sql.append("       B.INVOICE_NO,\n");
        sql.append("       B.INVOICE_AMOUNT,\n");
        sql.append("       B.INVOICE_DATE,\n");
        sql.append("       B.INVOICE_REMARKS\n");
        sql.append("  FROM PT_BU_SALE_ORDER A,PT_BU_SALE_INVOICE_SUMMARY B\n");

        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("ORDER_STATUS", DicConstant.DDZT);
        bs.setFieldDic("INVOICE_STATUS", DicConstant.KPZT);
        bs.setFieldDateFormat("INVOICE_DATE","yyyy-MM-dd");
        return bs;
    }

    /**
     * 修改开票信息
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean updateInvoice(PtBuSaleInvoiceSummaryVO vo)
            throws Exception {
        return factory.update(vo);
    }

}