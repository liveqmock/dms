package com.org.dms.dao.part.storageMng.stockInMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuReturnApplyDtlVO;
import com.org.dms.vo.part.PtBuReturnApplyVO;
import com.org.dms.vo.part.PtBuSaleOrderVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.SequenceUtil;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 销售退件入库Dao
 *
 * 配送中心或车厂销售退件入库
 * @author zhengyao
 * @date 2014-08-06
 * @version 1.0
 */
public class SaleReturnPurchaseInMngDao extends BaseDAO {

    /**
     * 定义instance
     *
     * @param pActionContext
     * @return returnPurchaseCheckMngDao 销售退件入库Dao
     */
    public static final SaleReturnPurchaseInMngDao getInstance(ActionContext pActionContext) {

        SaleReturnPurchaseInMngDao saleReturnPurchaseInMngDao = new SaleReturnPurchaseInMngDao();
        pActionContext.setDBFactory(saleReturnPurchaseInMngDao.factory);

        return saleReturnPurchaseInMngDao;
    }

    /**
     * 退件申请表(pt_bu_return_apply)修改
     *
     * @param pPtBuReturnApplyVO 退件申请实体
     * @param pUser 当前登录user对象
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean updateReturnPurchaseApply(PtBuReturnApplyVO pPtBuReturnApplyVO, User pUser) throws Exception {

        // 修改配件预测表sql
        String sql = " UPDATE \n"
                   + "     PT_BU_RETURN_APPLY \n"
                   + " SET \n"
                   + "     APPLY_SATUS='" + pPtBuReturnApplyVO.getApplySatus() + "',\n"
                   + "     UPDATE_USER='" + pUser.getAccount()+ "',\n"
                   + "     CLOSE_DATE=sysdate, \n"
                   + "     UPDATE_TIME=sysdate, \n"
                   + "     IN_STOCK_DATE=sysdate \n"
                   + " WHERE \n"
                   + "     RETURN_ID='" + pPtBuReturnApplyVO.getReturnId() + "'\n";

        return factory.update(sql, null);
    }

    /**
     * 配件服务站库存(pt_bu_dealer_stock)修改
     *
     * @param pPartIdOutCount 配件主键,入库数量
     * @param pUser 当前登录user对象
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean updateDealerStock(String pPartIdOutCount,User pUser,String returnId) throws Exception {

    	StringBuilder sql= new StringBuilder();
    	sql.append("MERGE INTO PT_BU_DEALER_STOCK T\n" );
    	sql.append("USING (SELECT PART_ID,\n" );
    	sql.append("              PART_CODE,\n" );
    	sql.append("              PART_NAME,\n" );
    	sql.append("              SUPPLIER_ID,\n" );
    	sql.append("              SUPPLIER_CODE,\n" );
    	sql.append("              SUPPLIER_NAME\n" );
    	sql.append("         FROM PT_BU_RETURN_APPLY_DTL\n" );
    	sql.append("        WHERE RETURN_ID = '"+returnId+"') T1\n" );
    	sql.append("ON (T.PART_ID = T1.PART_ID AND T.SUPPLIER_ID = T1.SUPPLIER_ID AND T.ORG_ID='" + pUser.getOrgId() + "')\n" );
    	sql.append("WHEN MATCHED THEN\n" );
    	sql.append(" UPDATE");
    	sql.append(" SET AMOUNT = \n");
        sql.append("         (SELECT \n");
        sql.append("              AMOUNT+" + pPartIdOutCount.split(",")[1] + "\n");
        sql.append("          FROM \n");
        sql.append("              PT_BU_DEALER_STOCK \n");
        sql.append("          WHERE \n");
        sql.append("             ORG_ID='" + pUser.getOrgId() + "'\n");
        sql.append("          AND PART_ID='" + pPartIdOutCount.split(",")[0] + "'),\n");
        sql.append("     AVAILABLE_AMOUNT = \n");
        sql.append("         (SELECT \n");
        sql.append("              AVAILABLE_AMOUNT+" + pPartIdOutCount.split(",")[1] + "\n");
        sql.append("          FROM \n");
        sql.append("              PT_BU_DEALER_STOCK \n");
        sql.append("          WHERE \n");
        sql.append("              ORG_ID='" + pUser.getOrgId() + "'\n");
        sql.append("          AND PART_ID='" + pPartIdOutCount.split(",")[0] + "')\n");
        sql.append(" WHERE \n");
        sql.append("     ORG_ID='" + pUser.getOrgId() + "'\n");
        sql.append(" AND PART_ID='" + pPartIdOutCount.split(",")[0] + "'");
        sql.append(" AND SUPPLIER_ID='" + pPartIdOutCount.split(",")[4] + "'" );
    	sql.append(" WHEN NOT MATCHED THEN\n" );
    	sql.append(" INSERT\n" );
    	sql.append("    (STOCK_ID,\n" );
    	sql.append("    PART_ID,\n" );
    	sql.append("    PART_CODE,\n" );
    	sql.append("    PART_NAME,\n" );
    	sql.append("    SUPPLIER_ID,\n" );
    	sql.append("    SUPPLIER_CODE,\n" );
    	sql.append("    SUPPLIER_NAME,\n" );
    	sql.append("    ORG_ID,\n" );
    	sql.append("    ORG_CODE,\n" );
    	sql.append("    ORG_NAME,\n" );
    	sql.append("    AMOUNT,\n" );
    	sql.append("    AVAILABLE_AMOUNT,\n" );
    	sql.append("    STORAGE_STATUS,\n" );
    	sql.append("    CREATE_USER,\n" );
    	sql.append("    CREATE_TIME,\n" );
    	sql.append("    STATUS,\n" );
    	sql.append("    IS_OVERSTOCK)\n" );
    	sql.append("VALUES (F_GETID(),\n" );
    	sql.append("       T1.PART_ID,\n" );
    	sql.append("       T1.PART_CODE,\n" );
    	sql.append("       T1.PART_NAME,\n" );
    	sql.append("       T1.SUPPLIER_ID,\n" );
    	sql.append("       T1.SUPPLIER_CODE,\n" );
    	sql.append("       T1.SUPPLIER_NAME,\n" );
    	sql.append("       '"+ pUser.getOrgId() +"',\n" );
    	sql.append("       '"+ pUser.getOrgCode() +"',\n" );
    	sql.append("       '"+ pUser.getOrgDept().getOName() +"',\n" );
    	sql.append("       " + pPartIdOutCount.split(",")[1] + ",\n" );
    	sql.append("       " + pPartIdOutCount.split(",")[1] + ",\n" );
    	sql.append("       '"+DicConstant.KCZT_01+"',\n" );
    	sql.append("       '"+ pUser.getAccount() +"',\n" );
    	sql.append("       SYSDATE,\n" );
    	sql.append("       '"+DicConstant.YXBS_01+"',\n" );
    	sql.append("       '"+DicConstant.SF_02+"')");

    	
        // 修配件配送中心库存表sql
//        String sql = " UPDATE \n"
//                   + "     PT_BU_DEALER_STOCK \n"
//                   + " SET AMOUNT = \n"
//                   + "         (SELECT \n"
//                   + "              AMOUNT+" + pPartIdOutCount.split(",")[1] + "\n"
//                   + "          FROM \n"
//                   + "              PT_BU_DEALER_STOCK \n"
//                   + "          WHERE \n"
//                   + "             ORG_ID='" + pUser.getOrgId() + "'\n"
//                   + "          AND PART_ID='" + pPartIdOutCount.split(",")[0] + "'),\n"
//                   + "     AVAILABLE_AMOUNT = \n"
//                   + "         (SELECT \n"
//                   + "              AVAILABLE_AMOUNT+" + pPartIdOutCount.split(",")[1] + "\n"
//                   + "          FROM \n"
//                   + "              PT_BU_DEALER_STOCK \n"
//                   + "          WHERE \n"
//                   + "              ORG_ID='" + pUser.getOrgId() + "'\n"
//                   + "          AND PART_ID='" + pPartIdOutCount.split(",")[0] + "')\n"
//                   + " WHERE \n"
//                   + "     ORG_ID='" + pUser.getOrgId() + "'\n"
//                   + " AND PART_ID='" + pPartIdOutCount.split(",")[0] + "'"
//                   + " AND SUPPLIER_ID='" + pPartIdOutCount.split(",")[4] + "'";


        // 修配件服务站库存表sql
        String sql1 = " UPDATE \n"
                   + "     PT_BU_DEALER_STOCK \n"
                   + " SET AMOUNT = \n"
                   + "         (SELECT \n"
                   + "              AMOUNT-" + pPartIdOutCount.split(",")[1] + "\n"
                   + "          FROM \n"
                   + "              PT_BU_DEALER_STOCK \n"
                   + "          WHERE \n"
                   + "             ORG_ID=(SELECT APPLY_ORG_ID FROM PT_BU_RETURN_APPLY WHERE RETURN_ID = '" + returnId + "')\n"
                   + "          AND PART_ID='" + pPartIdOutCount.split(",")[0] + "'),\n"
                   + "     OCCUPY_AMOUNT = \n"
                   + "         (SELECT \n"
                   + "              OCCUPY_AMOUNT-" + pPartIdOutCount.split(",")[2] + "\n"
                   + "          FROM \n"
                   + "              PT_BU_DEALER_STOCK \n"
                   + "          WHERE \n"
                   + "              ORG_ID=(SELECT APPLY_ORG_ID FROM PT_BU_RETURN_APPLY WHERE RETURN_ID = '" + returnId + "')\n"
                   + "          AND PART_ID='" + pPartIdOutCount.split(",")[0] + "'),\n"
                   + "     AVAILABLE_AMOUNT = \n"
                   + "         (SELECT \n"
                   + "              AVAILABLE_AMOUNT+" + pPartIdOutCount.split(",")[2] + "-"+ pPartIdOutCount.split(",")[1] + "\n"
                   + "          FROM \n"
                   + "              PT_BU_DEALER_STOCK \n"
                   + "          WHERE \n"
                   + "              ORG_ID=(SELECT APPLY_ORG_ID FROM PT_BU_RETURN_APPLY WHERE RETURN_ID = '" + returnId + "')\n"
                   + "          AND PART_ID='" + pPartIdOutCount.split(",")[0] + "')\n"
                   + " WHERE \n"
                   + "     ORG_ID=(SELECT APPLY_ORG_ID FROM PT_BU_RETURN_APPLY WHERE RETURN_ID = '" + returnId + "')\n"
                   + " AND PART_ID='" + pPartIdOutCount.split(",")[0] + "'"
                   + " AND SUPPLIER_ID='" + pPartIdOutCount.split(",")[4] + "'";

        return factory.update(sql.toString(), null)&&factory.update(sql1, null);
    }

    /**
     * 配件库存服务站异动(pt_bu_dealer_stock_change)修改
     *
     * @param pPartIdInCount 配件主键,出库数量,应入数量,入库订单号
     * @param pUser 当前登录user对象
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean insertDealerStockChange(String pPartIdInCount, User pUser,String returnId) throws Exception {

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
        sql.append("          " + pPartIdInCount.split(",")[1] + ",\n" );
        sql.append("          sysdate,\n" );
        sql.append("          '" + DicConstant.CZLX_01 + "',\n" );
        sql.append("          '',\n" );
        sql.append("          '" + pUser.getAccount() + "',\n" );
        sql.append("          sysdate,\n" );
        sql.append("          '',\n" );
        sql.append("          '',\n" );
        sql.append("          '" + DicConstant.YXBS_01 + "',\n" );
        sql.append("          SUPPLIER_ID,\n" );
        sql.append("          SUPPLIER_CODE,\n" );
        sql.append("          SUPPLIER_NAME,\n" );
        sql.append("          '" + DicConstant.QDCRKLX_06 + "',\n" );
        sql.append("          '" + pPartIdInCount.split(",")[3] + "',\n" );
        sql.append("          ''\n" );
        sql.append("     FROM PT_BU_DEALER_STOCK\n" );
        sql.append("    WHERE ORG_ID = '" + pUser.getOrgId() + "'\n" );
        sql.append("      AND SUPPLIER_ID = '" + pPartIdInCount.split(",")[4] + "'\n" );
        sql.append("      AND PART_ID = '" + pPartIdInCount.split(",")[0] + "')");

        
        StringBuilder sql1= new StringBuilder();
        sql1.append("INSERT INTO PT_BU_DEALER_STOCK_CHANGE\n" );
        sql1.append("  (SELECT F_GETID(),\n" );
        sql1.append("          STOCK_ID,\n" );
        sql1.append("          ORG_ID,\n" );
        sql1.append("          ORG_CODE,\n" );
        sql1.append("          ORG_NAME,\n" );
        sql1.append("          PART_ID,\n" );
        sql1.append("          PART_CODE,\n" );
        sql1.append("          PART_NAME,\n" );
        sql1.append("          " + pPartIdInCount.split(",")[1] + ",\n" );
        sql1.append("          sysdate,\n" );
        sql1.append("          '" + DicConstant.CZLX_02 + "',\n" );
        sql1.append("          '',\n" );
        sql1.append("          '" + pUser.getAccount() + "',\n" );
        sql1.append("          sysdate,\n" );
        sql1.append("          '',\n" );
        sql1.append("          '',\n" );
        sql1.append("          '" + DicConstant.YXBS_01 + "',\n" );
        sql1.append("          SUPPLIER_ID,\n" );
        sql1.append("          SUPPLIER_CODE,\n" );
        sql1.append("          SUPPLIER_NAME,\n" );
        sql1.append("          '" + DicConstant.QDCRKLX_05 + "',\n" );
        sql1.append("          '',\n" );
        sql1.append("          '" + pPartIdInCount.split(",")[3] + "'\n" );
        sql1.append("     FROM PT_BU_DEALER_STOCK\n" );
        sql1.append("    WHERE ORG_ID=(SELECT APPLY_ORG_ID FROM PT_BU_RETURN_APPLY WHERE RETURN_ID = '" + returnId + "')\n" );
        sql1.append("      AND SUPPLIER_ID = '" + pPartIdInCount.split(",")[4] + "'\n" );
        sql1.append("      AND PART_ID = '" + pPartIdInCount.split(",")[0] + "')");
        
        return factory.exec(sql1.toString())&&factory.exec(sql.toString());
    }

    /**
     * 退件申请表(pt_bu_return_apply)查询
     *
     * @param pPageManager 查询分页对象
     * @param pUser 当前登录user对象
     * @param pConditions sql条件(默认：1=1)
     * @return baseResultSet 结果集
     * @throws Exception
     */
    public BaseResultSet searchReturnPurchaseApply(PageManager pPageManager, User pUser, String pConditions) throws Exception {

        String wheres = pConditions;
        // 查询已出库订单
        wheres += " AND APPLY_SATUS ='" + DicConstant.TJSQDZT_03+ "' \n"
                + " AND RECEIVE_ORG_ID = " + pUser.getOrgId() + "\n"
                + " ORDER BY RETURN_NO";
        pPageManager.setFilter(wheres);
        //定义返回结果集
        BaseResultSet baseResultSet = null;
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT \n");
        sql.append("     RETURN_ID,\n");
        sql.append("     RETURN_NO,\n");
        sql.append("     RECEIVE_ORG_ID,\n");
        sql.append("     RECEIVE_ORG_CODE,\n");
        sql.append("     RECEIVE_ORG_NAME,\n");
        sql.append("     SOURCE_ORDER_NO,\n");
        sql.append("     SOURCE_ORDER_ID,\n");
        sql.append("     APPLY_ORG_CODE,\n");
        sql.append("     APPLY_ORG_NAME,\n");
        sql.append("     APPLY_SATUS, \n");
        sql.append("     APPLY_DATE, \n");
        sql.append("     CHECK_DATE, \n");
        sql.append("     CHECK_USER, \n");
        sql.append("     RETURN_COUNT, \n");
        sql.append("     RETURN_AMOUNT \n");
        sql.append(" FROM \n");
        sql.append("     PT_BU_RETURN_APPLY \n");
        // 执行方法，不需要传递conn参数
        baseResultSet = factory.select(sql.toString(), pPageManager);
        baseResultSet.setFieldDic("APPLY_SATUS", DicConstant.TJSQDZT);
        // 申请日期绑定
        baseResultSet.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
        // 审核日期绑定
        baseResultSet.setFieldDateFormat("CHECK_DATE", "yyyy-MM-dd");

        return baseResultSet;
    }

    /**
     * 申请退件明细表(pt_bu_return_apply_dtl)查询
     *
     * @param pPageManager 查询分页对象
     * @param pUser 当前登录user对象
     * @param pConditions sql条件(默认：1=1)
     * @return QuerySet 结果集
     * @throws Exception
     */
    public BaseResultSet searchReturnPurchaseApplyDtl(PageManager pPageManager, String pConditions) throws Exception {

        String where = pConditions + " AND A.PART_ID=B.PART_ID";
        pPageManager.setFilter(where);
        //定义返回结果集
        BaseResultSet baseResultSet = null;
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT \n");
        sql.append("     A.DTL_ID,\n");
        sql.append("     A.RETURN_ID,\n");
        sql.append("     A.PART_ID,\n");
        sql.append("     A.PART_CODE,\n");
        sql.append("     A.PART_NAME,\n");
        sql.append("     A.SUPPLIER_ID,\n");
        sql.append("     A.SUPPLIER_CODE,\n");
        sql.append("     A.SUPPLIER_NAME,\n");
        sql.append("     A.UNIT,\n");
        sql.append("     A.RETURN_COUNT,\n");
        sql.append("     A.SALE_PRICE,\n");
        sql.append("     A.AMOUNT,\n");
        sql.append("     B.MIN_PACK,\n");
        sql.append("     B.MIN_UNIT\n");
        sql.append(" FROM \n");
        sql.append("     PT_BU_RETURN_APPLY_DTL A,PT_BA_INFO B\n");
        //执行方法，不需要传递conn参数
        baseResultSet = factory.select(sql.toString(),pPageManager);

        return baseResultSet;
    }
    public boolean updateApplyDtl(PtBuReturnApplyDtlVO vo)
            throws Exception {
        return factory.update(vo);
    }
    public void updateApplyStockDtl(String returnId,String dtlId)
            throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT B.DTL_ID,B.PART_ID,A.APPLY_ORG_ID,B.SUPPLIER_ID,B.RETURN_COUNT\n" );
    	sql.append("  FROM PT_BU_RETURN_APPLY A, PT_BU_RETURN_APPLY_DTL B\n" );
    	sql.append(" WHERE A.RETURN_ID = B.RETURN_ID\n" );
    	sql.append(" AND A.RETURN_ID ="+returnId+"\n" );
    	sql.append("   AND B.DTL_ID NOT IN ("+dtlId+")");
    	QuerySet qs = factory.select(null, sql.toString());
    	if(qs.getRowCount()>0){
    		for(int i = 0;i<qs.getRowCount();i++){
    			String partId= qs.getString(i+1, "PART_ID");
    			String orgId= qs.getString(i+1, "APPLY_ORG_ID");
    			String supplierId= qs.getString(i+1, "SUPPLIER_ID");
    			String retrunCount= qs.getString(i+1, "RETURN_COUNT");
    			String sql2 = "UPDATE PT_BU_DEALER_STOCK T SET T.OCCUPY_AMOUNT = T.OCCUPY_AMOUNT-"+retrunCount+" ,T.AVAILABLE_AMOUNT= T.AVAILABLE_AMOUNT+"+retrunCount+"  WHERE PART_ID ="+partId+" AND ORG_ID ="+orgId+" AND SUPPLIER_ID = "+supplierId+"\n";
    			factory.update(sql2.toString(), null);
    		}
    	}
    }
}