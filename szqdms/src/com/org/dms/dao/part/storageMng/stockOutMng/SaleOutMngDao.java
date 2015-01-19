package com.org.dms.dao.part.storageMng.stockOutMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuSaleOrderVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 销售出库Dao
 *
 * 配送中心出库
 * @author zhengyao
 * @date 2014-08-06
 * @version 1.0
 */
public class SaleOutMngDao extends BaseDAO {

    /**
     * 定义instance
     *
     * @param pActionContext
     * @return saleOutMngDao 销售出库Dao
     */
    public static final SaleOutMngDao getInstance(ActionContext pActionContext) {

        SaleOutMngDao saleOutMngDao = new SaleOutMngDao();
        pActionContext.setDBFactory(saleOutMngDao.factory);

        return saleOutMngDao;
    }

    /**
     * 销售订单表(pt_bu_sale_order)修改
     *
     * @param pPtBuSaleOrderVO 销售订单实体
     * @param pUser 当前登录user对象
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean updateSaleOrder(PtBuSaleOrderVO pPtBuSaleOrderVO, User pUser) throws Exception {

        // 修改销售订单表sql
        // 发运单状态-已发运
        String sql = " UPDATE \n"
                   + "     PT_BU_SALE_ORDER \n"
                   + " SET \n"
                   + "     SHIP_STATUS = " + DicConstant.DDFYZT_06 + ",\n"
                   + "     REAL_AMOUNT = " + pPtBuSaleOrderVO.getRealAmount() + ",\n"
                   + "     CLOSE_DATE=sysdate,\n"
                   + "     ORDER_STATUS='" + DicConstant.DDZT_06+ "',\n"
                   + "     UPDATE_USER='" + pUser.getAccount()+ "',\n"
                   + "     UPDATE_TIME=sysdate \n"
                   + " WHERE \n"
                   + "     ORDER_ID='" + pPtBuSaleOrderVO.getOrderId() + "'\n";

        return factory.update(sql, null);
    }

    /**
     * 配件服务站库存(pt_bu_dealer_stock)修改
     *
     * @param pPartIdOutCount 配件主键,实出库数量,应出数量,销售订单号
     * @param pUser 当前登录user对象
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean updateDealerStock(String pPartIdOutCount,User pUser) throws Exception {
    	String dtlId = pPartIdOutCount.split(",")[0];
    	String partId = pPartIdOutCount.split(",")[1];
    	String auditCount = pPartIdOutCount.split(",")[3];
    	String outCount = pPartIdOutCount.split(",")[2];
    	String supplierId = pPartIdOutCount.split(",")[4];
    	String sqlstr = " UPDATE PT_BU_SALE_ORDER_DTL SET DELIVERY_COUNT = "+outCount+",UPDATE_USER='"+pUser.getAccount()+"',UPDATE_TIME=SYSDATE WHERE DTL_ID="+dtlId+"\n";
        factory.update(sqlstr, null);
    	// 修配件服务站库存表sql
        StringBuffer sql = new StringBuffer();
        sql.append(" UPDATE PT_BU_DEALER_STOCK \n");
        sql.append(" SET AMOUNT = AMOUNT-"+outCount+",\n");
        sql.append("  OCCUPY_AMOUNT = OCCUPY_AMOUNT -"+auditCount+",\n");
        sql.append("  AVAILABLE_AMOUNT = AVAILABLE_AMOUNT +"+auditCount+"-"+outCount+",\n");
        sql.append("  UPDATE_USER = '"+pUser.getAccount()+"',UPDATE_TIME = SYSDATE\n");
        sql.append(" WHERE ORG_ID = "+pUser.getOrgId()+"\n");
        sql.append(" AND PART_ID = "+partId+"\n");
        sql.append(" AND SUPPLIER_ID = "+supplierId+"\n");
        return factory.update(sql.toString(), null);
    }

    /**
     * 配件库存服务站异动(pt_bu_dealer_stock_change)修改
     *
     * @param pPartIdOutCount 配件主键,实出库数量,应出数量,出库订单号
     * @param pUser 当前登录user对象
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean insertDealerStockChange(String pPartIdOutCount, User pUser) throws Exception {
    	String partId = pPartIdOutCount.split(",")[1];
    	String outCount = pPartIdOutCount.split(",")[2];
    	String orderNo = pPartIdOutCount.split(",")[5];
    	String supplierId = pPartIdOutCount.split(",")[4];
        StringBuilder sql= new StringBuilder();
        sql.append("INSERT INTO PT_BU_DEALER_STOCK_CHANGE\n" );
        sql.append("  (SELECT F_GETID(),\n" );
        sql.append("          STOCK_ID,\n" );
        sql.append("          ORG_ID,\n" );
        sql.append("          ORG_CODE,\n" );
        sql.append("          ORG_NAME,\n" );
        sql.append("          PART_ID,\n" );
        sql.append("          PART_CODE,\n" );
        sql.append("          PART_NAME,\n" );
        sql.append("          " + outCount + ",\n" );
        sql.append("          sysdate,\n" );
        sql.append("          '" + DicConstant.CZLX_02 + "',\n" );
        sql.append("          NULL,\n" );
        sql.append("          '" + pUser.getAccount() + "',\n" );
        sql.append("          sysdate,\n" );
        sql.append("          NULL,\n" );
        sql.append("          NULL,\n" );
        sql.append("          '" + DicConstant.YXBS_01 + "',\n" );
        sql.append("          SUPPLIER_ID,\n" );
        sql.append("          SUPPLIER_CODE,\n" );
        sql.append("          SUPPLIER_NAME,\n" );
        sql.append("          '" + DicConstant.QDCRKLX_01 + "',\n" );
        sql.append("          NULL,\n" );
        sql.append("          '" + orderNo+ "'\n" );
        sql.append("     FROM PT_BU_DEALER_STOCK\n" );
        sql.append("    WHERE ORG_ID = '" + pUser.getOrgId() + "'\n" );
        sql.append("      AND PART_ID = '" +partId + "' AND SUPPLIER_ID = "+supplierId+")\n");

        return factory.exec(sql.toString());
    }

    /**
     * 销售订单表(pt_bu_sale_order)查询
     *
     * @param pPageManager 查询分页对象
     * @param pUser 当前登录user对象
     * @param pConditions sql条件(默认：1=1)
     * @return baseResultSet 结果集
     * @throws Exception
     */
    public BaseResultSet searchSaleOrder(PageManager pPageManager, User pUser, String pConditions) throws Exception {

        // 查询销售订单(审核通过,未发料)
         String wheres = " A.ORDER_STATUS <> '" + DicConstant.DDZT_01 + "' \n"
        		+ " AND A.ORDER_TYPE NOT IN ('"+DicConstant.DDLX_07+"','"+DicConstant.DDLX_08+"','"+DicConstant.DDLX_09+"','"+DicConstant.DDLX_10+"') \n"
                + " AND A.SHIP_STATUS ='" + DicConstant.DDFYZT_01 + "' \n"
                + " AND A.STATUS = '" + DicConstant.YXBS_01 + "'\n"
                + " AND A.IF_CHANEL_ORDER = '" + DicConstant.SF_01 + "'\n"
                + " AND A.WAREHOUSE_ID = " + pUser.getOrgId() + "\n"
                + " AND A.ORDER_ID = B.ORDER_ID"
                + " AND NVL(B.AUDIT_COUNT,0) >0"
                + " AND " + pConditions
                + " ORDER BY A.APPLY_DATE ASC\n";
        pPageManager.setFilter(wheres);
        //定义返回结果集
        BaseResultSet baseResultSet = null;
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT A.ORDER_ID,\n" );
        sql.append("       A.ORDER_NO,\n" );
        sql.append("       A.ORDER_TYPE,\n" );
        sql.append("       A.ORDER_STATUS,\n" );
        sql.append("       A.ORG_CODE,\n" );
        sql.append("       A.ORG_NAME,\n" );
        sql.append("       A.WAREHOUSE_ID,\n" );
        sql.append("       A.WAREHOUSE_CODE,\n" );
        sql.append("       A.WAREHOUSE_NAME,\n" );
        sql.append("       A.ORDER_AMOUNT,\n" );
        sql.append("       A.APPLY_DATE,\n" );
        sql.append("       (SELECT SUM(NVL(B.AUDIT_COUNT, 0) * B.UNIT_PRICE) FROM PT_BU_SALE_ORDER_DTL B WHERE B.ORDER_ID = A.ORDER_ID) SHOULD_AMOUNT\n" );
        sql.append("  FROM PT_BU_SALE_ORDER A,(SELECT SUM(AUDIT_COUNT) AUDIT_COUNT,ORDER_ID FROM PT_BU_SALE_ORDER_DTL WHERE 1=1 GROUP BY ORDER_ID) B");
        // 执行方法，不需要传递conn参数
        baseResultSet = factory.select(sql.toString(), pPageManager);
        baseResultSet.setFieldDic("ORDER_TYPE", DicConstant.DDLX);
        baseResultSet.setFieldDic("ORDER_STATUS", DicConstant.DDZT);
        // 申请日期绑定
        baseResultSet.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");

        return baseResultSet;
    }

    /**
     * 销售订单明细表(pt_bu_sale_order_dtl)查询
     *
     * @param pPageManager 查询分页对象
     * @param pUser 当前登录user对象
     * @param pConditions sql条件(默认：1=1)
     * @return QuerySet 结果集
     * @throws Exception
     */
    public BaseResultSet searchSaleOrderDtl(PageManager pPageManager, String pConditions) throws Exception {

        String where = pConditions + " AND A.PART_ID=B.PART_ID AND NVL(A.AUDIT_COUNT,0)>0 ORDER BY A.PART_CODE";
        pPageManager.setFilter(where);
        //定义返回结果集
        BaseResultSet baseResultSet = null;
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT \n");
        sql.append("     A.DTL_ID,\n");
        sql.append("     A.ORDER_ID,\n");
        sql.append("     A.PART_ID,\n");
        sql.append("     A.PART_CODE,\n");
        sql.append("     A.PART_NAME,\n");
        sql.append("     A.SUPPLIER_ID,\n");
        sql.append("     A.SUPPLIER_CODE,\n");
        sql.append("     A.SUPPLIER_NAME,\n");
        sql.append("     A.UNIT_PRICE,\n");
        sql.append("     A.AUDIT_COUNT,\n");
        sql.append("     A.AMOUNT,\n");
        sql.append("     B.MIN_PACK,\n");
        sql.append("     B.UNIT,\n");
        sql.append("     B.MIN_UNIT\n");
        sql.append(" FROM \n");
        sql.append("     PT_BU_SALE_ORDER_DTL A,PT_BA_INFO B\n");
        //执行方法，不需要传递conn参数
        baseResultSet = factory.select(sql.toString(),pPageManager);
        baseResultSet.setFieldDic("UNIT", DicConstant.JLDW);

        return baseResultSet;
    }
    
    public QuerySet getRealAmount(String orderId) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT SUM(NVL(T1.UNIT_PRICE, 0) * NVL(T1.DELIVERY_COUNT, 0)) REAL_AMOUNT\n" );
    	sql.append("  FROM PT_BU_SALE_ORDER_DTL T1\n" );
    	sql.append(" WHERE T1.ORDER_ID = "+orderId+"");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
    public void freezStockOccupy(String orderId,String dtlId) throws Exception{
    	StringBuffer sql2= new StringBuffer();
    	sql2.append("SELECT B.DTL_ID,B.PART_ID,A.WAREHOUSE_ID ORG_ID,B.SUPPLIER_ID,B.AUDIT_COUNT\n" );
    	sql2.append("  FROM PT_BU_SALE_ORDER A, PT_BU_SALE_ORDER_DTL B\n" );
    	sql2.append(" WHERE A.ORDER_ID = B.ORDER_ID\n" );
    	sql2.append(" AND B.AUDIT_COUNT >0\n" );
    	sql2.append(" AND A.ORDER_ID ="+orderId+"\n" );
    	sql2.append(" AND B.DTL_ID NOT IN("+dtlId+")\n" );
    	QuerySet qs = factory.select(null, sql2.toString());
    	if(qs.getRowCount()>0){
    		for(int i = 0;i<qs.getRowCount();i++){
    			String partId= qs.getString(i+1, "PART_ID");
    			String orgId= qs.getString(i+1, "ORG_ID");
    			String supplierId= qs.getString(i+1, "SUPPLIER_ID");
    			String auditCount= qs.getString(i+1, "AUDIT_COUNT");
    			String sql3 = "UPDATE PT_BU_DEALER_STOCK T SET T.OCCUPY_AMOUNT = T.OCCUPY_AMOUNT-"+auditCount+" ,T.AVAILABLE_AMOUNT= T.AVAILABLE_AMOUNT+"+auditCount+" WHERE PART_ID ="+partId+" AND ORG_ID ="+orgId+" AND SUPPLIER_ID = "+supplierId+"\n";
    			factory.update(sql3.toString(), null);
    		}
    	}
    }
    public void insetStockDtl(String saleCount,String aAmount,String SHOULD_COUNT,User user,String saleId,String url,String nPartId) throws Exception {
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
   	   sql.append("           -"+SHOULD_COUNT+",\n" );
   	   sql.append("           -"+saleCount+",\n" );
   	   sql.append("           +"+aAmount+",\n" );
   	   sql.append("           '"+user.getAccount()+"',\n" );
   	   sql.append("           SYSDATE,\n" );
   	   sql.append("           "+nPartId+",\n" );
   	   sql.append("           "+user.getOrgId()+")");
   	   factory.update(sql.toString(), null);
      }
}