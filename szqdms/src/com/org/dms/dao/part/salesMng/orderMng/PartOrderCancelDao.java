package com.org.dms.dao.part.salesMng.orderMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuAccountLogVO;
import com.org.dms.vo.part.PtBuSaleOrderVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;
/**
 * @Description:订单取消Dao
 * @Date: 2014.08.25
 * @Author sunxuedong
 */
public class PartOrderCancelDao extends BaseDAO{
    public static final PartOrderCancelDao getInstance(ActionContext atx) {
        PartOrderCancelDao dao = new PartOrderCancelDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
    /**
     * 订单查询
     */
    public BaseResultSet partOrderCancelSearch(PageManager page, User user, String conditions) throws Exception
    {
        String wheres = conditions;
        wheres += " ORDER BY T.APPLY_DATE ASC\n";
        page.setFilter(wheres);
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT *\n" );
        sql.append("  FROM (SELECT T.ORDER_ID,\n" );
        sql.append("               T.ORDER_NO,\n" );
        sql.append("               T.ORDER_TYPE,\n" );
        sql.append("               T.ORG_ID,\n" );
        sql.append("               T.ORG_CODE,\n" );
        sql.append("               T.ORG_NAME,\n" );
        sql.append("               T.WAREHOUSE_ID,\n" );
        sql.append("               T.WAREHOUSE_CODE,\n" );
        sql.append("               T.WAREHOUSE_NAME,\n" );
        sql.append("               T.EXPECT_DATE,\n" );
        sql.append("               T.ORDER_AMOUNT,\n" );
        sql.append("               T.APPLY_DATE,\n" );
        sql.append("               T.CREATE_USER,\n" );
        sql.append("               T.REMARKS,\n" );
        sql.append("               T.IF_DELAY_ORDER,\n" );
        sql.append("               T.PROM_ID,\n" );
        sql.append("               T.IF_CREDIT,\n" );
        sql.append("               T.IF_TRANS,\n" );
        sql.append("               T.PROVINCE_CODE,\n" );
        sql.append("               T.PROVINCE_NAME,\n" );
        sql.append("               T.CITY_CODE,\n" );
        sql.append("               T.CITY_NAME,\n" );
        sql.append("               T.COUNTRY_CODE,\n" );
        sql.append("               T.COUNTRY_NAME,\n" );
        sql.append("               T.TRANS_TYPE,\n" );
        sql.append("               T.ZIP_CODE,\n" );
        sql.append("               T.DELIVERY_ADDR,\n" );
        sql.append("               T.LINK_MAN,\n" );
        sql.append("               T.PHONE,\n" );
        sql.append("               T.SHIP_STATUS,\n" );
        sql.append("               T.ORDER_STATUS,\n" );
        sql.append("               T.STATUS\n" );
        sql.append("          FROM PT_BU_SALE_ORDER T\n" );
        sql.append("         WHERE 1 = 1\n" );
        sql.append("           AND T.ORDER_STATUS IN ("+DicConstant.DDZT_03+", "+DicConstant.DDZT_02+")\n" );
        sql.append("           AND T.ORDER_TYPE <> "+DicConstant.DDLX_05+"\n" );
        sql.append("           AND T.IF_CHANEL_ORDER = "+DicConstant.SF_02+"\n" );
        sql.append("           AND (T.SHIP_STATUS = "+DicConstant.DDFYZT_01+" OR T.SHIP_STATUS IS NULL)\n" );
        sql.append("           AND T.ORG_ID IN\n" );
        sql.append("               (SELECT ORG_ID\n" );
        sql.append("                  FROM TM_ORG\n" );
        sql.append("                 WHERE PID IN (SELECT ORG_ID\n" );
        sql.append("                                 FROM PT_BA_ORDER_CHECK\n" );
        sql.append("                                WHERE USER_ACCOUNT = '"+user.getAccount()+"'))\n" );
        sql.append("           AND T.STATUS = "+DicConstant.YXBS_01+"\n" );
        sql.append("        UNION\n" );
        sql.append("        SELECT T.ORDER_ID,\n" );
        sql.append("               T.ORDER_NO,\n" );
        sql.append("               T.ORDER_TYPE,\n" );
        sql.append("               T.ORG_ID,\n" );
        sql.append("               T.ORG_CODE,\n" );
        sql.append("               T.ORG_NAME,\n" );
        sql.append("               T.WAREHOUSE_ID,\n" );
        sql.append("               T.WAREHOUSE_CODE,\n" );
        sql.append("               T.WAREHOUSE_NAME,\n" );
        sql.append("               T.EXPECT_DATE,\n" );
        sql.append("               T.ORDER_AMOUNT,\n" );
        sql.append("               T.APPLY_DATE,\n" );
        sql.append("               T.CREATE_USER,\n" );
        sql.append("               T.REMARKS,\n" );
        sql.append("               T.IF_DELAY_ORDER,\n" );
        sql.append("               T.PROM_ID,\n" );
        sql.append("               T.IF_CREDIT,\n" );
        sql.append("               T.IF_TRANS,\n" );
        sql.append("               T.PROVINCE_CODE,\n" );
        sql.append("               T.PROVINCE_NAME,\n" );
        sql.append("               T.CITY_CODE,\n" );
        sql.append("               T.CITY_NAME,\n" );
        sql.append("               T.COUNTRY_CODE,\n" );
        sql.append("               T.COUNTRY_NAME,\n" );
        sql.append("               T.TRANS_TYPE,\n" );
        sql.append("               T.ZIP_CODE,\n" );
        sql.append("               T.DELIVERY_ADDR,\n" );
        sql.append("               T.LINK_MAN,\n" );
        sql.append("               T.PHONE,\n" );
        sql.append("               T.SHIP_STATUS,\n" );
        sql.append("               T.ORDER_STATUS,\n" );
        sql.append("               T.STATUS\n" );
        sql.append("          FROM PT_BU_SALE_ORDER T\n" );
        sql.append("         WHERE 1 = 1\n" );
        sql.append("           AND T.ORDER_STATUS IN ("+DicConstant.DDZT_03+", "+DicConstant.DDZT_02+")\n" );
        sql.append("           AND T.ORDER_TYPE = "+DicConstant.DDLX_05+"\n" );
        sql.append("           AND T.IF_CHANEL_ORDER = "+DicConstant.SF_02+"\n" );
        sql.append("           AND (T.SHIP_STATUS = "+DicConstant.DDFYZT_01+" OR T.SHIP_STATUS IS NULL)\n" );
        sql.append("           AND T.ORG_ID IN\n" );
        sql.append("               (SELECT ORG_ID\n" );
        sql.append("                  FROM TM_ORG\n" );
        sql.append("                 WHERE PID IN (SELECT ORG_ID\n" );
        sql.append("                                 FROM PT_BA_ORDER_CHECK\n" );
        sql.append("                                WHERE USER_ACCOUNT = '"+user.getAccount()+"'))\n" );
        sql.append("           AND T.STATUS = "+DicConstant.YXBS_01+"\n" );
        sql.append("           AND EXISTS (SELECT 1\n" );
        sql.append("                  FROM PT_BU_PCH_ORDER S\n" );
        sql.append("                 WHERE T.ORDER_ID = S.SALE_ORDER_ID\n" );
        sql.append("                   AND S.ORDER_STATUS = "+DicConstant.CGDDZT_01+")) T");
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("ORDER_TYPE", "DDLX");
        bs.setFieldDic("ORDER_STATUS", "DDZT");
        bs.setFieldDic("TRANS_TYPE", "FYFS");
        bs.setFieldDic("IF_CREDIT", "SF");
        bs.setFieldDic("IF_TRANS", "SF");
        bs.setFieldDateFormat("EXPECT_DATE", "yyyy-MM-dd");
        bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
        // 提报人
        bs.setFieldUserID("CREATE_USER");
        return bs;
    }
        /**
         * 库存占用释放
         */
        public void orderReleaseFreez(String orderId,User user) throws Exception {
         QuerySet qs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT A.OFUNDS_ID,A.ACCOUNT_ID,A.ACCOUNT_TYPE,A.OCCUPY_FUNDS FROM PT_BU_SALE_ORDER_OCCUPY_FUNDS A \n" );
        sql.append("  WHERE STATUS = "+DicConstant.YXBS_01+"\n" );
        sql.append("   AND A.ORDER_ID = "+orderId+"\n");
        qs = factory.select(null, sql.toString());
        if(qs.getRowCount()>0){
            for(int i=0;i<qs.getRowCount();i++){
                String ofundsId = qs.getString(i+1, "OFUNDS_ID");
                String accountId = qs.getString(i+1, "ACCOUNT_ID");
                String accountType = qs.getString(i+1, "ACCOUNT_TYPE");
                String occupyFunds = qs.getString(i+1, "OCCUPY_FUNDS");
                StringBuffer sql1 = new StringBuffer();
                sql1.append("UPDATE PT_BU_SALE_ORDER_OCCUPY_FUNDS SET STATUS ="+DicConstant.YXBS_02+",UPDATE_USER='"+user.getAccount()+"',\n");
                sql1.append("UPDATE_TIME=SYSDATE WHERE OFUNDS_ID = "+ofundsId+"\n");
                factory.update(sql1.toString(), null);
                StringBuffer sql2 = new StringBuffer();
                sql2.append("UPDATE PT_BU_ACCOUNT SET OCCUPY_AMOUNT =OCCUPY_AMOUNT -"+occupyFunds+",AVAILABLE_AMOUNT= AVAILABLE_AMOUNT+"+occupyFunds+",\n");
                sql2.append(" UPDATE_USER='"+user.getAccount()+"',UPDATE_TIME=SYSDATE WHERE ACCOUNT_ID="+accountId+" AND ACCOUNT_TYPE="+accountType+"\n");
                factory.update(sql2.toString(), null);
            }
        }
    }
        /**
         * 库存占用释放
         */
        public void orderInventoryFreez(String orderId,User user) throws Exception {
        QuerySet qs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.STOCK_ID,\n" );
        sql.append("       T.DTL_ID,\n" );
        sql.append("       T.PART_ID,\n" );
        sql.append("       T.PART_CODE,\n" );
        sql.append("       T.PART_NAME,\n" );
        sql.append("       T.POSITION_ID,\n" );
        sql.append("       T.POSITION_CODE,\n" );
        sql.append("       T.POSITION_NAME,\n" );
        sql.append("       T.SUPPLIER_ID,\n" );
        sql.append("       T.SUPPLIER_CODE,\n" );
        sql.append("       T.SUPPLIER_CODE,\n" );
        sql.append("       T.SUPPLIER_NAME,\n" );
        sql.append("       T.OCCUPY_AMOUNT\n" );
        sql.append("  FROM PT_BU_STOCK_OCCUPY_LOG T\n" );
        sql.append(" WHERE T.ORDER_ID = "+orderId+"\n");
        qs = factory.select(null, sql.toString());
        if(qs.getRowCount()>0){
            for(int i=0;i<qs.getRowCount();i++){
                String stockId = qs.getString(i+1, "STOCK_ID");//仓库ID
                String dtlId = qs.getString(i+1, "DTL_ID");//库存明细主键
                String partId = qs.getString(i+1, "PART_ID");//配件主键
                //String positionId = qs.getString(i+1, "POSITION_ID");//库位主键
                String supplierId = qs.getString(i+1, "SUPPLIER_ID");//供应商ID
                String occupyAmount = qs.getString(i+1, "OCCUPY_AMOUNT");//占用数量
                 StringBuffer sql1= new StringBuffer();
                sql1.append("UPDATE PT_BU_STOCK T\n" );
                sql1.append("   SET T.AVAILABLE_AMOUNT = T.AVAILABLE_AMOUNT + "+occupyAmount+",\n" );
                sql1.append("       T.OCCUPY_AMOUNT= T.OCCUPY_AMOUNT - "+occupyAmount+",\n" );
                sql1.append("       T.UPDATE_USER = '"+user.getAccount()+"',\n" );
                sql1.append("       T.UPDATE_TIME=SYSDATE\n" );
                sql1.append(" WHERE T.STOCK_ID ="+stockId+"\n" );
                sql1.append("     AND T.PART_ID ="+partId+"\n" );
                if(supplierId!=null && !"".equals(supplierId)){
                    sql1.append("   AND T.SUPPLIER_ID ="+supplierId+"");
                }
                factory.update(sql1.toString(), null);
                StringBuffer sql2= new StringBuffer();
                 sql2.append("UPDATE PT_BU_STOCK_DTL T\n" );
                 sql2.append("   SET T.AVAILABLE_AMOUNT = T.AVAILABLE_AMOUNT + "+occupyAmount+",\n" );
                 sql2.append("       T.OCCUPY_AMOUNT= T.OCCUPY_AMOUNT - "+occupyAmount+",\n" );
                sql2.append("       T.UPDATE_USER = '"+user.getAccount()+"',\n" );
                sql2.append("       T.UPDATE_TIME=SYSDATE\n" );
                sql2.append(" WHERE T.STOCK_ID ="+stockId+"\n" );
                sql2.append("   AND T.PART_ID ="+partId+"\n" );
                sql2.append("   AND T.DTL_ID ="+dtlId+"\n" );
                if(supplierId!=null && !"".equals(supplierId)){
                    sql2.append("   AND T.SUPPLIER_ID ="+supplierId+"");
                }
                factory.update(sql2.toString(), null);
            }
        }
    }
    /**
     * 订单使用金额查询
     */
    public QuerySet queryZYJE(String orderId) throws Exception
    {
        QuerySet qs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.OCCUPY_FUNDS,T.ACCOUNT_ID FROM PT_BU_SALE_ORDER_OCCUPY_FUNDS T WHERE T.ORDER_ID ="+orderId+" AND T.ACCOUNT_TYPE NOT IN("+DicConstant.ZJZHLX_04+") AND T.STATUS ="+DicConstant.YXBS_01+"\n" );
        qs = factory.select(null, sql.toString());
        return qs;
    }
    /**
     * 订单关闭实扣金额
     */
    public boolean updateAmount(Double zje,String accountId)throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append(" UPDATE PT_BU_ACCOUNT T\n" );
        sql.append("   SET T.OCCUPY_AMOUNT  = T.OCCUPY_AMOUNT - "+zje+",\n" );
        sql.append("       T.BALANCE_AMOUNT = T.BALANCE_AMOUNT - "+zje+"\n" );
        sql.append(" WHERE T.ACCOUNT_ID = "+accountId+" ");
        return factory.update(sql.toString(), null);
    }
    /**
     * 订单关闭实扣金额
     */
    public boolean updateIssue(String orderId,User user)throws Exception {
        StringBuffer sql= new StringBuffer();
        sql.append("UPDATE PT_BU_ISSUE_ORDER T\n" );
        sql.append("   SET T.STATUS = "+DicConstant.YXBS_02+",\n" );
        sql.append("       T.UPDATE_USER = '"+user.getAccount()+"'\n" );
        sql.append("       T.UPDATE_TIME = SYSDATE\n" );
        sql.append(" WHERE T.ORDER_ID ="+orderId+"");
        return factory.update(sql.toString(), null);
    }
    /**
     * 订单关闭状态更新
     */
    public boolean updateOrderStatus(PtBuSaleOrderVO vo)throws Exception {
        
        return factory.update(vo);
    }
    /**
     * 资金异动明细日志新增
     */
    public boolean insertamountLog(PtBuAccountLogVO vo)throws Exception {
        
        return factory.insert(vo);
    }
    public QuerySet queryPchOrderStatus(String orderId)throws Exception {
    	StringBuffer sql = new StringBuffer();
    	sql.append("SELECT * FROM PT_BU_PCH_ORDER S\n" );
        sql.append(" WHERE S.SALE_ORDER_ID = "+orderId+"\n" );
        sql.append(" AND S.ORDER_STATUS = "+DicConstant.CGDDZT_01+"\n");
    	QuerySet qs = factory.select(null, sql.toString());
    	return qs;
    }
    public void deletePchOrder(String orderId)throws Exception {
    	StringBuffer sql = new StringBuffer();
    	sql.append("DELETE FROM PT_BU_PCH_ORDER_DTL\n" );
        sql.append(" WHERE PURCHASE_ID=(SELECT PURCHASE_ID FROM PT_BU_PCH_ORDER WHERE SALE_ORDER_ID ="+orderId+")\n" );
        factory.update(sql.toString(),null);
        StringBuffer sql2 = new StringBuffer();
    	sql2.append("DELETE  FROM PT_BU_PCH_ORDER WHERE SALE_ORDER_ID ="+orderId+"\n" );
        factory.update(sql2.toString(),null);
    }
    
}
