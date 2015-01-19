package com.org.dms.dao.part.salesMng.orderMng;

import java.sql.CallableStatement;
import java.sql.Types;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.common.PartOddNumberUtil;
import com.org.dms.vo.part.PtBuSaleOrderCheckVO;
import com.org.dms.vo.part.PtBuSaleOrderDtlVO;
import com.org.dms.vo.part.PtBuSaleOrderPayVO;
import com.org.dms.vo.part.PtBuSaleOrderVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.SequenceUtil;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 三包急件订单提报Dao
 *
 * 三包急件订单提报
 * @author zhengyao
 * @date 2014-09-10
 * @version 1.0
 */
public class ClaimCyclOrderReportMngDao extends BaseDAO {

    /**
     * 定义instance
     *
     * @param pActionContext
     * @return directSaleOrderMngDao 三包急件订单提报Dao
     */
    public static final ClaimCyclOrderReportMngDao getInstance(ActionContext pActionContext) {

        ClaimCyclOrderReportMngDao claimCyclOrderReportMngDao = new ClaimCyclOrderReportMngDao();
        pActionContext.setDBFactory(claimCyclOrderReportMngDao.factory);
        return claimCyclOrderReportMngDao;
    }

    /**
     * VIN校验
     * @param vin 
     * @param engineNo 发动机号
     * @return
     * @throws Exception
     */
    public BaseResultSet vinCheckSearch(String vin,String engineNo) throws Exception {

        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.VEHICLE_ID,\n" );
        sql.append("       T.VIN,\n" );
        sql.append("       T.ENGINE_NO,\n" );
        sql.append("       T.VEHICLE_STATUS,\n" );
        sql.append("       T.MODELS_ID,\n" );
        sql.append("       T.MODELS_CODE,\n" );
        sql.append("       T.CERTIFICATE,\n" );
        sql.append("       T.ENGINE_TYPE,\n" );
        sql.append("       T.USER_TYPE USERNAME,\n" );
        sql.append("       T.USER_TYPE,\n" );
        sql.append("       T.VEHICLE_USE,\n" );
        sql.append("       T.VEHICLE_USE VEHICLENAME,\n" );
        sql.append("       T.DRIVE_FORM,\n" );
        sql.append("       T.DRIVE_FORM DRIVENAME,\n" );
        sql.append("       T.BUY_DATE,\n" );
        sql.append("       T.MILEAGE,\n" );
        sql.append("       T.GUARANTEE_NO,\n" );
        sql.append("       T.FACTORY_DATE,\n" );
        sql.append("       T.MAINTENANCE_DATE,\n" );
        sql.append("       T.SALE_STATUS,\n" );
        sql.append("       T.LICENSE_PLATE,\n" );
        sql.append("       T.USER_NAME,\n" );
        sql.append("       T.USER_NO,\n" );
        sql.append("       T.LINK_MAN,\n" );
        sql.append("       T.PHONE,\n" );
        sql.append("       T.USER_ADDRESS,\n" );
        sql.append("       T.BLACKLISTFLAG\n" );
        sql.append("  FROM MAIN_VEHICLE T\n" );
        sql.append(" WHERE (T.VIN LIKE '%"+vin+"'  OR T.VIN = '"+vin+"')\n" );
        sql.append("   AND T.ENGINE_NO = '"+engineNo+"'");
        return factory.select(sql.toString(), new PageManager());
    }

    /**
     * 配件销售订单付款明细
     */
    public boolean saleOrderPayInsert(PtBuSaleOrderPayVO vo) throws Exception {

        return factory.insert(vo);
    }

    /**
     * 配送中心三包急件订单生成
     */
    public boolean saleOrderInsert(String newOrderId,PtBuSaleOrderVO ptBuSaleOrderVO,User user) throws Exception {

        StringBuilder sql= new StringBuilder();
        sql.append("INSERT INTO PT_BU_SALE_ORDER\n" );
        sql.append("  (SELECT\n" );
        sql.append("    '" + newOrderId + "',\n" );
        sql.append("    '" + PartOddNumberUtil.getSaleOrderNo(factory, "ZY",user.getOrgCode()) + "',\n" );
        sql.append("    '" + DicConstant.DDLX_07 + "',\n" );
        sql.append("    '" + DicConstant.DDZT_02+ "',\n" );
        sql.append("    '" + user.getOrgCode() + "',\n" );
        sql.append("    '" + user.getOrgDept().getOName() + "',\n" );
        sql.append("    '" + ptBuSaleOrderVO.getWarehouseId() + "',\n" );
        sql.append("    '" + ptBuSaleOrderVO.getWarehouseCode() + "',\n" );
        sql.append("    '" + ptBuSaleOrderVO.getWarehouseName() + "',\n" );
        sql.append("    ORDER_AMOUNT,\n" );
        sql.append("    EXPECT_DATE,\n" );
        sql.append("    IF_CREDIT,\n" );
        sql.append("    IF_TRANS,\n" );
        sql.append("    CUSTORM_NAME,\n" );
        sql.append("    TRANS_TYPE,\n" );
        sql.append("    DELIVERY_ADDR,\n" );
        sql.append("    LINK_MAN,\n" );
        sql.append("    PHONE,\n" );
        sql.append("    ZIP_CODE,\n" );
        sql.append("    REMARKS,\n" );
        sql.append("    '" + user.getCompanyId() + "',\n" );
        sql.append("    '" + user.getOrgId() + "',\n" );
        sql.append("    '" + user.getAccount() + "',\n" );
        sql.append("    sysdate,\n" );
        sql.append("    UPDATE_USER,\n" );
        sql.append("    UPDATE_TIME,\n" );
        sql.append("    STATUS,\n" );
        sql.append("    '" + user.getOemCompanyId() + "',\n" );
        sql.append("    SECRET_LEVEL,\n" );
        sql.append("    IF_CHANEL_ORDER,\n" );
        sql.append("    IF_DELAY_ORDER,\n" );
        sql.append("    '" + ptBuSaleOrderVO.getOrderId() + "',\n" );
        sql.append("    '" + ptBuSaleOrderVO.getOrderNo() + "',\n" );
        sql.append("    ADDR_TYPE,\n" );
        sql.append("    DELIVERY_CITY,\n" );
        sql.append("    CITY_NAME,\n" );
        sql.append("    PROM_ID,\n" );
        sql.append("    sysdate,\n" );
        sql.append("    SHIP_STATUS,\n" );
        sql.append("    REAL_AMOUNT,\n" );
        sql.append("    SUPPLIER_ID,\n" );
        sql.append("    SUPPLIER_NAME,\n" );
        sql.append("    SUPPLIER_CODE,\n" );
        sql.append("    INVOICE_STATUS\n" );
        sql.append("     FROM PT_BU_SALE_ORDER\n" );
        sql.append("    WHERE ORDER_ID = '" + ptBuSaleOrderVO.getOrderId() + "')");

        return factory.exec(sql.toString());
    }

    /**
     * 订单审核明细更新审核数量
     */
//    public boolean orderAuditCountUpdate(String auditCount,String dtlId,String orderId,User user) throws Exception {
//
//        StringBuffer sql = new StringBuffer();
//        sql.append("UPDATE PT_BU_SALE_ORDER_DTL SET AUDIT_COUNT ="+auditCount+",UPDATE_USER='"+user.getAccount()+"',UPDATE_TIME=SYSDATE WHERE DTL_ID="+dtlId+"AND ORDER_ID ="+orderId+"\n");
//        return factory.update(sql.toString(), null);
//    }

    /**
     * 订单审核日志
     */
    public boolean partOrderCheckLogInsert(PtBuSaleOrderCheckVO vo) throws Exception {

        return factory.insert(vo);
    }

    /**
     * 订单审核驳回
     */
    public boolean partOrderCheckApproveBack(PtBuSaleOrderVO vo) throws Exception {

        return factory.update(vo);
    }

    /**
     * 查询审核员
     */
    public QuerySet sourceOrderId(String orderId) throws Exception {

    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT NVL(DIR_SOURCE_ORDER_ID,0) DIR_SOURCE_ORDER_ID,NVL(DIR_SOURCE_ORDER_NO,0) DIR_SOURCE_ORDER_NO FROM PT_BU_SALE_ORDER WHERE ORDER_ID= '" + orderId + "'");
        return factory.select(null, sql.toString());
    }

    /**
     * 查询订单中的军品件
     */
    public QuerySet partCheck(String orderId) throws Exception {

    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT A.PART_CODE\n" );
    	sql.append("  FROM PT_BU_SALE_ORDER_DTL A, PT_BA_INFO B\n" );
    	sql.append(" WHERE A.PART_ID = B.PART_ID\n" );
    	sql.append("   AND PART_TYPE = '" + DicConstant.PJLB_03 + "'\n" );
    	sql.append("   AND ORDER_ID = '" + orderId + "'");


        return factory.select(null, sql.toString());
    }

    /**
     * 查询审核员
     */
    public QuerySet searchCheckUser(String orderId) throws Exception {

        StringBuilder sql= new StringBuilder();
        sql.append("SELECT B.USER_ACCOUNT\n" );
        sql.append("  FROM TM_ORG A, PT_BA_ORDER_CHECK B\n" );
        sql.append(" WHERE A.PID = B.ORG_ID\n" );
        sql.append("   AND A.ORG_ID = '" + orderId + "'");

        return factory.select(null, sql.toString());
    }

    /**
     * 订单审核通过
     */
    public boolean partOrderCheckPass(PtBuSaleOrderVO vo) throws Exception {

        return factory.update(vo);
    }

    /**
     * 订单审核明细更新审核数量(配送中心)
     */
    public boolean orderAuditCountUpdate(String auditCount,String dtlId,String orderId,User user) throws Exception {

        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE PT_BU_SALE_ORDER_DTL SET AUDIT_COUNT ="+auditCount+",UPDATE_USER='"+user.getAccount()+"',UPDATE_TIME=SYSDATE WHERE DTL_ID="+dtlId+" AND ORDER_ID ="+orderId+"\n");
        return factory.update(sql.toString(), null);
    }

    /**
     * 订单审核明细更新审核数量(服务站)
     */
    public boolean orderAuditCountUpdateServer(String auditCount,String dtlId,String orderId,User user) throws Exception {

        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE PT_BU_SALE_ORDER_DTL SET AUDIT_COUNT ="+auditCount+",UPDATE_USER='"+user.getAccount()+"',UPDATE_TIME=SYSDATE WHERE PART_ID=(SELECT PART_ID FROM PT_BU_SALE_ORDER_DTL WHERE DTL_ID="+dtlId+") AND ORDER_ID ="+orderId+"\n");
        return factory.update(sql.toString(), null);
    }
    /**
     * 订单审核明细更新审核数量
     */
    public QuerySet searchOrderDtl(String orderId) throws Exception {

    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT DTL_ID FROM PT_BU_SALE_ORDER_DTL WHERE ORDER_ID='"+orderId+"'");
        return factory.select(null,sql.toString());
    }

    /**
     * 占用库存
     *
     * @param orderId 订单ID
     * @throws Exception
     */
    public void orderFreez(String orderId) throws Exception {
//    	CallableStatement proc1 = factory.getConnection().prepareCall("{call P_PART_FREEZ_FUNDS(?,?)}");
//         proc1.setString(1, orderId);
//         proc1.registerOutParameter(2, Types.VARCHAR);
//         proc1.execute();
//         if(!"".equals(proc1.getString(2))&&proc1.getString(2)!=null){
//             throw new Exception(proc1.getString(2));
//         }
        CallableStatement proc2 = factory.getConnection().prepareCall("{call P_PART_FREEZ_STORE(?,?)}");
        proc2.setString(1, orderId);
        proc2.registerOutParameter(2, Types.VARCHAR);
        proc2.execute();
        if(!"".equals(proc2.getString(2))&&proc2.getString(2)!=null){
            throw new Exception(proc2.getString(2));
        }
    }

    /**
     * 延期订单生成
     */
    public void delayOrderInfoInsert(String orderId,User user,String sourceOrderIdString) throws Exception {

    	// 服务站中心单号
        String sourceOrderNo = PartOddNumberUtil.getDelaySaleOrderNo(factory, sourceOrderIdString);
        // 服务站订单ID
        String delaySourceOrderId = SequenceUtil.getCommonSerivalNumber(factory);
        StringBuffer sql= new StringBuffer();
        sql.append("INSERT INTO PT_BU_SALE_ORDER (ORDER_ID,ORDER_NO,ORDER_TYPE,ORDER_STATUS,ORG_CODE,\n" );
        sql.append("   ORG_NAME,WAREHOUSE_ID,WAREHOUSE_CODE,WAREHOUSE_NAME,EXPECT_DATE,IF_CREDIT,\n" );
        sql.append("   IF_TRANS,TRANS_TYPE,DELIVERY_ADDR,LINK_MAN,PHONE,ZIP_CODE,REMARKS,COMPANY_ID,\n" );
        sql.append("   ORG_ID,CREATE_USER,CREATE_TIME,STATUS,OEM_COMPANY_ID,IF_CHANEL_ORDER,IF_DELAY_ORDER,\n" );
        sql.append("   SOURCE_ORDER_ID,SOURCE_ORDER_NO,ADDR_TYPE,PROVINCE_CODE,PROVINCE_NAME,CITY_CODE,CITY_NAME,COUNTRY_CODE,COUNTRY_NAME,PROM_ID,APPLY_DATE\n" );
        sql.append("   ,SB_VIN,SB_ENGINE_NO,SB_MODELS_CODE,SB_BUY_DATE,SB_MILEAGE,SB_USER_NAME,SB_LINK_MAN,SB_PHONE,SB_USER_ADDRESS,SB_FAULT_DATE,SB_FAULT_ANALYSE_CODE,SB_FAULT_ANALYSE_NAME)\n" );
        sql.append("  SELECT "+delaySourceOrderId+",'"+sourceOrderNo+"',ORDER_TYPE,"+DicConstant.DDZT_02+",ORG_CODE,\n" );
        sql.append("   ORG_NAME,WAREHOUSE_ID,WAREHOUSE_CODE,WAREHOUSE_NAME,EXPECT_DATE,IF_CREDIT,\n" );
        sql.append("   IF_TRANS,TRANS_TYPE,DELIVERY_ADDR,LINK_MAN,PHONE,ZIP_CODE,REMARKS,COMPANY_ID,\n" );
        sql.append("   ORG_ID,'"+user.getAccount()+"',SYSDATE,STATUS,OEM_COMPANY_ID,IF_CHANEL_ORDER,"+DicConstant.SF_01+",\n" );
        sql.append("   ORDER_ID,ORDER_NO,ADDR_TYPE,PROVINCE_CODE,PROVINCE_NAME,CITY_CODE,CITY_NAME,COUNTRY_CODE,COUNTRY_NAME,PROM_ID,APPLY_DATE\n" );
        sql.append("   ,SB_VIN,SB_ENGINE_NO,SB_MODELS_CODE,SB_BUY_DATE,SB_MILEAGE,SB_USER_NAME,SB_LINK_MAN,SB_PHONE,SB_USER_ADDRESS,SB_FAULT_DATE,SB_FAULT_ANALYSE_CODE,SB_FAULT_ANALYSE_NAME\n" );
        sql.append("    FROM PT_BU_SALE_ORDER WHERE ORDER_ID = "+sourceOrderIdString+"\n" );
        //插入延期订单主信息
        factory.update(sql.toString(), null);

        StringBuffer sql1= new StringBuffer();
        sql1.append("INSERT INTO PT_BU_SALE_ORDER_DTL(DTL_ID,ORDER_ID,PART_ID,PART_CODE,PART_NAME,PART_NO,ORDER_COUNT,\n" );
        sql1.append("CREATE_USER,CREATE_TIME,IF_SUPPLIER,SUPPLIER_ID,SUPPLIER_CODE,SUPPLIER_NAME,UNIT_PRICE,AMOUNT,PLAN_PRICE,REMARKS)\n" );
        sql1.append("SELECT F_GETID(),"+delaySourceOrderId+",PART_ID,PART_CODE,PART_NAME,PART_NO,ORDER_COUNT - AUDIT_COUNT,'"+user.getAccount()+"',\n" );
        sql1.append("SYSDATE,IF_SUPPLIER,SUPPLIER_ID,SUPPLIER_CODE,SUPPLIER_NAME,UNIT_PRICE,\n" );
        sql1.append("(ORDER_COUNT - AUDIT_COUNT)*UNIT_PRICE,PLAN_PRICE,REMARKS FROM PT_BU_SALE_ORDER_DTL\n" );
        sql1.append("WHERE ORDER_ID ="+sourceOrderIdString+" AND ORDER_COUNT - AUDIT_COUNT>0");
        //插入延期订单明细信息
        factory.update(sql1.toString(), null);

        QuerySet qs1 = null;
        StringBuffer sql6= new StringBuffer();
        sql6.append("SELECT NVL(SUM(ORDER_COUNT * UNIT_PRICE),0) ORDER_AMOUNT\n" );
        sql6.append("  FROM PT_BU_SALE_ORDER_DTL A\n" );
        sql6.append(" WHERE A.ORDER_ID = "+delaySourceOrderId+"\n");
        // 查询延期订单详细金额汇总
        qs1 = factory.select(null, sql6.toString());

        if(qs1.getRowCount()>0){
            String orderAmount = qs1.getString(1, "ORDER_AMOUNT");
            StringBuffer sql7 = new StringBuffer();
            sql7.append("UPDATE PT_BU_SALE_ORDER SET ORDER_AMOUNT="+orderAmount+" WHERE ORDER_ID = "+delaySourceOrderId+"\n");
            //更新延期订单总金额
            factory.update(sql7.toString(), null);
        }
        
    	// 配送中心单号
        String orderNo = PartOddNumberUtil.getDelaySaleOrderNo(factory, orderId);
        String delayOrderId = SequenceUtil.getCommonSerivalNumber(factory);
        StringBuffer sql00= new StringBuffer();
        sql00.append("INSERT INTO PT_BU_SALE_ORDER (ORDER_ID,ORDER_NO,ORDER_TYPE,ORDER_STATUS,ORG_CODE,\n" );
        sql00.append("   ORG_NAME,WAREHOUSE_ID,WAREHOUSE_CODE,WAREHOUSE_NAME,EXPECT_DATE,IF_CREDIT,\n" );
        sql00.append("   IF_TRANS,TRANS_TYPE,DELIVERY_ADDR,LINK_MAN,PHONE,ZIP_CODE,REMARKS,COMPANY_ID,\n" );
        sql00.append("   ORG_ID,CREATE_USER,CREATE_TIME,STATUS,OEM_COMPANY_ID,IF_CHANEL_ORDER,IF_DELAY_ORDER,\n" );
        sql00.append("   SOURCE_ORDER_ID,SOURCE_ORDER_NO,ADDR_TYPE,PROVINCE_CODE,PROVINCE_NAME,CITY_CODE,CITY_NAME,COUNTRY_CODE,COUNTRY_NAME,PROM_ID,APPLY_DATE,DIR_SOURCE_ORDER_ID,DIR_SOURCE_ORDER_NO\n" );
        sql00.append("   ,SB_VIN,SB_ENGINE_NO,SB_MODELS_CODE,SB_BUY_DATE,SB_MILEAGE,SB_USER_NAME,SB_LINK_MAN,SB_PHONE,SB_USER_ADDRESS,SB_FAULT_DATE,SB_FAULT_ANALYSE_CODE,SB_FAULT_ANALYSE_NAME)\n" );
        sql00.append("  SELECT "+delayOrderId+",'"+orderNo+"',ORDER_TYPE,"+DicConstant.DDZT_02+",ORG_CODE,\n" );
        sql00.append("   ORG_NAME,WAREHOUSE_ID,WAREHOUSE_CODE,WAREHOUSE_NAME,EXPECT_DATE,IF_CREDIT,\n" );
        sql00.append("   IF_TRANS,TRANS_TYPE,DELIVERY_ADDR,LINK_MAN,PHONE,ZIP_CODE,REMARKS,COMPANY_ID,\n" );
        sql00.append("   ORG_ID,'"+user.getAccount()+"',SYSDATE,STATUS,OEM_COMPANY_ID,IF_CHANEL_ORDER,"+DicConstant.SF_01+",\n" );
        sql00.append("   ORDER_ID,ORDER_NO,ADDR_TYPE,PROVINCE_CODE,PROVINCE_NAME,CITY_CODE,CITY_NAME,COUNTRY_CODE,COUNTRY_NAME,PROM_ID,APPLY_DATE,"+delaySourceOrderId+",'"+sourceOrderNo+"'\n" );
        sql00.append("   ,SB_VIN,SB_ENGINE_NO,SB_MODELS_CODE,SB_BUY_DATE,SB_MILEAGE,SB_USER_NAME,SB_LINK_MAN,SB_PHONE,SB_USER_ADDRESS,SB_FAULT_DATE,SB_FAULT_ANALYSE_CODE,SB_FAULT_ANALYSE_NAME\n" );
        sql00.append("    FROM PT_BU_SALE_ORDER WHERE ORDER_ID = "+orderId+"\n" );
        //插入延期订单主信息
        factory.update(sql00.toString(), null);

        StringBuffer sql11= new StringBuffer();
        sql11.append("INSERT INTO PT_BU_SALE_ORDER_DTL(DTL_ID,ORDER_ID,PART_ID,PART_CODE,PART_NAME,PART_NO,ORDER_COUNT,\n" );
        sql11.append("CREATE_USER,CREATE_TIME,IF_SUPPLIER,SUPPLIER_ID,SUPPLIER_CODE,SUPPLIER_NAME,UNIT_PRICE,AMOUNT,PLAN_PRICE,REMARKS)\n" );
        sql11.append("SELECT F_GETID(),"+delayOrderId+",PART_ID,PART_CODE,PART_NAME,PART_NO,ORDER_COUNT - AUDIT_COUNT,'"+user.getAccount()+"',\n" );
        sql11.append("SYSDATE,IF_SUPPLIER,SUPPLIER_ID,SUPPLIER_CODE,SUPPLIER_NAME,UNIT_PRICE,REMARKS,\n" );
        sql11.append("(ORDER_COUNT - AUDIT_COUNT)*UNIT_PRICE,PLAN_PRICE FROM PT_BU_SALE_ORDER_DTL\n" );
        sql11.append("WHERE ORDER_ID ="+orderId+" AND ORDER_COUNT - AUDIT_COUNT>0");
        //插入延期订单明细信息
        factory.update(sql11.toString(), null);
        
        StringBuffer sql2= new StringBuffer();
        sql2.append("INSERT INTO PT_BU_SALE_ORDER_PAY(PAY_ID,ORDER_ID,ACCOUNT_ID,PAY_AMOUNT,CREATE_USER,CREATE_TIME,ACCOUNT_TYPE)\n" );
        sql2.append("SELECT F_GETID(),"+delayOrderId+",ACCOUNT_ID,PAY_AMOUNT,'"+user.getAccount()+"',SYSDATE,ACCOUNT_TYPE FROM\n" );
        sql2.append(" (SELECT T.ACCOUNT_ID,NVL(T.PAY_AMOUNT,0) - NVL(B.OCCUPY_FUNDS,0) PAY_AMOUNT,T.ACCOUNT_TYPE\n" );
        sql2.append("FROM (SELECT A.ORDER_ID,A.ACCOUNT_ID,A.ACCOUNT_TYPE,A.PAY_AMOUNT FROM PT_BU_SALE_ORDER_PAY A\n" );
        sql2.append(" WHERE ORDER_ID ="+orderId+")T LEFT JOIN PT_BU_SALE_ORDER_OCCUPY_FUNDS B\n");
        sql2.append(" ON T.ORDER_ID = B.ORDER_ID AND T.ACCOUNT_ID = B.ACCOUNT_ID  AND T.ACCOUNT_TYPE =B.ACCOUNT_TYPE\n");
        sql2.append(" AND B.STATUS ="+DicConstant.YXBS_01+" ) T WHERE PAY_AMOUNT>0\n");
        //插入延期订单付款信息
        factory.update(sql2.toString(), null);
        
        QuerySet qs11 = null;
        StringBuffer sql61= new StringBuffer();
        sql61.append("SELECT NVL(SUM(ORDER_COUNT * UNIT_PRICE),0) ORDER_AMOUNT\n" );
        sql61.append("  FROM PT_BU_SALE_ORDER_DTL A\n" );
        sql61.append(" WHERE A.ORDER_ID = "+delayOrderId+"\n");
        // 查询延期订单详细金额汇总
        qs11 = factory.select(null, sql61.toString());

        if(qs11.getRowCount()>0){
            String orderAmount = qs11.getString(1, "ORDER_AMOUNT");
            StringBuffer sql7 = new StringBuffer();
            sql7.append("UPDATE PT_BU_SALE_ORDER SET ORDER_AMOUNT="+orderAmount+" WHERE ORDER_ID = "+delayOrderId+"\n");
            //更新延期订单总金额
            factory.update(sql7.toString(), null);
            QuerySet qs = null;
            StringBuffer sql3= new StringBuffer();
            sql3.append("SELECT A.ACCOUNT_ID,A.ACCOUNT_TYPE,A.PAY_AMOUNT, B.AVAILABLE_AMOUNT\n" );
            sql3.append("  FROM PT_BU_SALE_ORDER_PAY A, PT_BU_ACCOUNT B\n" );
            sql3.append(" WHERE A.ACCOUNT_ID = B.ACCOUNT_ID\n" );
            sql3.append("   AND A.ORDER_ID = "+delayOrderId+"\n");
            //查询延期订单付款信息
            qs = factory.select(null, sql3.toString());
            if(qs.getRowCount()>0){
                for(int i=0;i<qs.getRowCount();i++){
                    String accountId  = qs.getString(i+1, "ACCOUNT_ID");
                    String payAmount  = qs.getString(i+1, "PAY_AMOUNT");
                    String accountType  = qs.getString(i+1, "ACCOUNT_TYPE");
                    //更新资金账户
                    StringBuffer sql4= new StringBuffer();
                    sql4.append("UPDATE PT_BU_ACCOUNT SET OCCUPY_AMOUNT = OCCUPY_AMOUNT+"+orderAmount+" ,AVAILABLE_AMOUNT = AVAILABLE_AMOUNT - "+orderAmount+" WHERE ACCOUNT_ID =+"+accountId+"\n");
                    factory.update(sql4.toString(), null);
                    //插入延期订单资金占用
                    StringBuffer sql5= new StringBuffer();
                    sql5.append("INSERT INTO PT_BU_SALE_ORDER_OCCUPY_FUNDS\n" );
                    sql5.append("  (OFUNDS_ID,\n" );
                    sql5.append("   ORDER_ID,\n" );
                    sql5.append("   ACCOUNT_ID,\n" );
                    sql5.append("   ACCOUNT_TYPE,\n" );
                    sql5.append("   OCCUPY_FUNDS,\n" );
                    sql5.append("   CREATE_USER,\n" );
                    sql5.append("   CREATE_TIME,\n" );
                    sql5.append("   STATUS)\n" );
                    sql5.append("VALUES\n" );
                    sql5.append("  (F_GETID(),"+delayOrderId+","+accountId+","+accountType+","+orderAmount+",'"+user.getAccount()+"',SYSDATE,"+DicConstant.YXBS_01+")\n");
                    factory.update(sql5.toString(), null);
                }
            }
        }
        
    }

    /**
     * 订单主信息修改
     */
    public boolean orderInfoUpdate(PtBuSaleOrderVO vo) throws Exception {

        return factory.update(vo);
    }
    
    /**
     * 查询配送中心信用额度账户
     * @throws Exception
     */
    public QuerySet queryAccoutInfo(String orderId)  throws Exception{
        String sql = "SELECT ACCOUNT_ID,OCCUPY_AMOUNT,AVAILABLE_AMOUNT FROM PT_BU_ACCOUNT WHERE ORG_ID = (SELECT ORG_ID FROM PT_BU_SALE_ORDER WHERE ORDER_ID ="+orderId+") AND ACCOUNT_TYPE ="+DicConstant.ZJZHLX_04+"\n";
        QuerySet qs = factory.select(null, sql);
        return qs;
    }

    /**
     * 订单资金占用
     */
    public boolean orderFundsOccupy(String orderId,String accountId,String accountType,String payAmount,User user) throws Exception {

        StringBuffer sql= new StringBuffer();
        sql.append("INSERT INTO PT_BU_SALE_ORDER_OCCUPY_FUNDS\n" );
        sql.append("  (OFUNDS_ID,\n" );
        sql.append("   ORDER_ID,\n" );
        sql.append("   ACCOUNT_ID,\n" );
        sql.append("   ACCOUNT_TYPE,\n" );
        sql.append("   OCCUPY_FUNDS,\n" );
        sql.append("   CREATE_USER,\n" );
        sql.append("   CREATE_TIME,\n" );
        sql.append("   STATUS)\n" );
        sql.append("VALUES\n" );
        sql.append("  (F_GETID(),"+orderId+","+accountId+","+accountType+","+payAmount+",'"+user.getAccount()+"',SYSDATE,"+DicConstant.YXBS_01+")\n");

        return factory.update(sql.toString(), null);
    }

    /**
     * 更新资金账户
     */
    public boolean accountUpdate(String accountId,String payAmount) throws Exception {

        StringBuffer sql= new StringBuffer();
        sql.append("UPDATE PT_BU_ACCOUNT SET OCCUPY_AMOUNT = OCCUPY_AMOUNT+"+payAmount+" ,AVAILABLE_AMOUNT = AVAILABLE_AMOUNT - "+payAmount+" WHERE ACCOUNT_ID =+"+accountId+"\n");
        return factory.update(sql.toString(), null);
    }
    
    /**
     * 更新资金账户
     */
    public boolean insertOrderPay(String orderId,String accountId,String payAmount,User user) throws Exception {
    	StringBuffer sql1= new StringBuffer();
    	sql1.append("DELETE FROM PT_BU_SALE_ORDER_PAY WHERE ORDER_ID ="+orderId+"\n");
    	factory.update(sql1.toString(), null);
    	StringBuffer sql2= new StringBuffer();
        sql2.append("INSERT INTO PT_BU_SALE_ORDER_PAY(PAY_ID,ORDER_ID,ACCOUNT_ID,PAY_AMOUNT,CREATE_USER,CREATE_TIME,ACCOUNT_TYPE)\n" );
        sql2.append("SELECT F_GETID(),"+orderId+","+accountId+","+payAmount+",'"+user.getAccount()+"',SYSDATE,"+DicConstant.ZJZHLX_04+" FROM DUAL\n");
        return factory.update(sql2.toString(), null);
    }
    
    /**
     * 资金占用释放
     */
    public void orderReleaseFreez1(String orderId,User user)
            throws Exception {
        QuerySet qs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT A.OFUNDS_ID,A.ACCOUNT_ID,A.ACCOUNT_TYPE,A.OCCUPY_FUNDS FROM PT_BU_SALE_ORDER_OCCUPY_FUNDS A \n" );
        sql.append("  WHERE STATUS = "+DicConstant.YXBS_01+"\n" );
        sql.append("   AND A.ORDER_ID = "+orderId+"\n");
        qs = factory.select(null, sql.toString());
        if(qs.getRowCount()>0){
            String ofundsId = qs.getString(1, "OFUNDS_ID");
            String accountId = qs.getString(1, "ACCOUNT_ID");
            String accountType = qs.getString(1, "ACCOUNT_TYPE");
            String occupyFunds = qs.getString(1, "OCCUPY_FUNDS");
            StringBuffer sql1 = new StringBuffer();
            sql1.append("UPDATE PT_BU_SALE_ORDER_OCCUPY_FUNDS SET STATUS ="+DicConstant.YXBS_02+",UPDATE_USER='"+user.getAccount()+"',\n");
            sql1.append("UPDATE_TIME=SYSDATE WHERE OFUNDS_ID = "+ofundsId+"\n");
            factory.update(sql1.toString(), null);
            StringBuffer sql2 = new StringBuffer();
            sql2.append("UPDATE PT_BU_ACCOUNT SET OCCUPY_AMOUNT =OCCUPY_AMOUNT - "+occupyFunds+",AVAILABLE_AMOUNT= AVAILABLE_AMOUNT+"+occupyFunds+",\n");
            sql2.append(" UPDATE_USER='"+user.getAccount()+"',UPDATE_TIME=SYSDATE WHERE ACCOUNT_ID="+accountId+" AND ACCOUNT_TYPE="+accountType+"\n");
            factory.update(sql2.toString(), null);
//            StringBuffer sql3= new StringBuffer();
//            sql3.append("SELECT SUM(NVL(A.AUDIT_COUNT,0)* NVL(A.UNIT_PRICE,0)) ORDER_AMOUNT\n" );
//            sql3.append("  FROM PT_BU_SALE_ORDER_DTL A\n" );
//            sql3.append(" WHERE A.ORDER_ID = "+orderId+"\n");
//            // 查询订单审核通过金额
//            QuerySet qs11 = factory.select(null, sql3.toString());
//            if(qs11.getRowCount()>0){
//                String orderAmount = qs11.getString(1, "ORDER_AMOUNT");
//                if(!"".equals(orderAmount)&&Double.parseDouble(orderAmount)>0){
//                	//更新资金账户
//                    StringBuffer sql4= new StringBuffer();
//                    sql4.append("UPDATE PT_BU_ACCOUNT SET OCCUPY_AMOUNT = OCCUPY_AMOUNT+"+orderAmount+" ,AVAILABLE_AMOUNT = AVAILABLE_AMOUNT - "+orderAmount+" WHERE ACCOUNT_ID =+"+accountId+"\n");
//                    factory.update(sql4.toString(), null);
//                    //插入订单资金占用
//                    StringBuffer sql5= new StringBuffer();
//                    sql5.append("INSERT INTO PT_BU_SALE_ORDER_OCCUPY_FUNDS\n" );
//                    sql5.append("  (OFUNDS_ID,\n" );
//                    sql5.append("   ORDER_ID,\n" );
//                    sql5.append("   ACCOUNT_ID,\n" );
//                    sql5.append("   ACCOUNT_TYPE,\n" );
//                    sql5.append("   OCCUPY_FUNDS,\n" );
//                    sql5.append("   CREATE_USER,\n" );
//                    sql5.append("   CREATE_TIME,\n" );
//                    sql5.append("   STATUS)\n" );
//                    sql5.append("VALUES\n" );
//                    sql5.append("  (F_GETID(),"+orderId+","+accountId+","+accountType+","+orderAmount+",'"+user.getAccount()+"',SYSDATE,"+DicConstant.YXBS_01+")\n");
//                    factory.update(sql5.toString(), null);
//                }
//            }
        }
    }
    
    public void orderReleaseFreez2(String orderId,User user)
            throws Exception {
        QuerySet qs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT A.OFUNDS_ID,A.ACCOUNT_ID,A.ACCOUNT_TYPE,A.OCCUPY_FUNDS FROM PT_BU_SALE_ORDER_OCCUPY_FUNDS A \n" );
        sql.append("  WHERE STATUS = "+DicConstant.YXBS_01+"\n" );
        sql.append("   AND A.ORDER_ID = "+orderId+"\n");
        qs = factory.select(null, sql.toString());
        if(qs.getRowCount()>0){
            String ofundsId = qs.getString(1, "OFUNDS_ID");
            String accountId = qs.getString(1, "ACCOUNT_ID");
            String accountType = qs.getString(1, "ACCOUNT_TYPE");
            String occupyFunds = qs.getString(1, "OCCUPY_FUNDS");
            StringBuffer sql1 = new StringBuffer();
            sql1.append("UPDATE PT_BU_SALE_ORDER_OCCUPY_FUNDS SET STATUS ="+DicConstant.YXBS_02+",UPDATE_USER='"+user.getAccount()+"',\n");
            sql1.append("UPDATE_TIME=SYSDATE WHERE OFUNDS_ID = "+ofundsId+"\n");
            factory.update(sql1.toString(), null);
            StringBuffer sql2 = new StringBuffer();
            sql2.append("UPDATE PT_BU_ACCOUNT SET OCCUPY_AMOUNT =OCCUPY_AMOUNT - "+occupyFunds+",AVAILABLE_AMOUNT= AVAILABLE_AMOUNT+"+occupyFunds+",\n");
            sql2.append(" UPDATE_USER='"+user.getAccount()+"',UPDATE_TIME=SYSDATE WHERE ACCOUNT_ID="+accountId+" AND ACCOUNT_TYPE="+accountType+"\n");
            factory.update(sql2.toString(), null);
            
            StringBuffer sql3= new StringBuffer();
            sql3.append("SELECT SUM(NVL(A.AUDIT_COUNT,0)* NVL(A.UNIT_PRICE,0)) ORDER_AMOUNT\n" );
            sql3.append("  FROM PT_BU_SALE_ORDER_DTL A\n" );
            sql3.append(" WHERE A.ORDER_ID = "+orderId+"\n");
            // 查询订单审核通过金额
            QuerySet qs11 = factory.select(null, sql3.toString());
            if(qs11.getRowCount()>0){
                String orderAmount = qs11.getString(1, "ORDER_AMOUNT");
                if(!"".equals(orderAmount)&&Double.parseDouble(orderAmount)>0){
                	//更新资金账户
                    StringBuffer sql4= new StringBuffer();
                    sql4.append("UPDATE PT_BU_ACCOUNT SET OCCUPY_AMOUNT = OCCUPY_AMOUNT+"+orderAmount+" ,AVAILABLE_AMOUNT = AVAILABLE_AMOUNT - "+orderAmount+" WHERE ACCOUNT_ID =+"+accountId+"\n");
                    factory.update(sql4.toString(), null);
                    //插入订单资金占用
                    StringBuffer sql5= new StringBuffer();
                    sql5.append("INSERT INTO PT_BU_SALE_ORDER_OCCUPY_FUNDS\n" );
                    sql5.append("  (OFUNDS_ID,\n" );
                    sql5.append("   ORDER_ID,\n" );
                    sql5.append("   ACCOUNT_ID,\n" );
                    sql5.append("   ACCOUNT_TYPE,\n" );
                    sql5.append("   OCCUPY_FUNDS,\n" );
                    sql5.append("   CREATE_USER,\n" );
                    sql5.append("   CREATE_TIME,\n" );
                    sql5.append("   STATUS)\n" );
                    sql5.append("VALUES\n" );
                    sql5.append("  (F_GETID(),"+orderId+","+accountId+","+accountType+","+orderAmount+",'"+user.getAccount()+"',SYSDATE,"+DicConstant.YXBS_01+")\n");
                    factory.update(sql5.toString(), null);
                }
            }
        }
    }
    /**
     * 删除资金使用
     */
    public void deleteOrderPay(String orderId,User user)
            throws Exception {
        StringBuffer sql1 = new StringBuffer();
        sql1.append("DELETE FROM PT_BU_SALE_ORDER_PAY WHERE ORDER_ID ="+orderId+"\n");
        factory.update(sql1.toString(), null);
    }
    /**
     * 订单使用金额与账户可用余额查询
     */
    public QuerySet orderFundsAmountSearch(String orderId) throws Exception {

        QuerySet qs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT A.ACCOUNT_ID,A.ACCOUNT_TYPE,A.PAY_AMOUNT, B.AVAILABLE_AMOUNT\n" );
        sql.append("  FROM PT_BU_SALE_ORDER_PAY A, PT_BU_ACCOUNT B\n" );
        sql.append(" WHERE A.ACCOUNT_ID = B.ACCOUNT_ID\n" );
        sql.append("   AND A.ORDER_ID = "+orderId+"\n");
        qs = factory.select(null, sql.toString());
        return qs;
    }

    /**
     * 删除配件
     *
     * @param ptBuSaleOrderDtlVO 配件销售订单明细实体
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean deleteOrderPart(PtBuSaleOrderDtlVO ptBuSaleOrderDtlVO) throws Exception {

        return factory.delete(ptBuSaleOrderDtlVO);
    }

    /**
     * 修改配件订单明细
     */
    public void updateOrderPart(String dtlId,String orderId,String orderCount,User user) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE PT_BU_SALE_ORDER_DTL SET ORDER_COUNT="+orderCount+",AMOUNT= UNIT_PRICE*"+orderCount+",UPDATE_USER='"+user.getAccount()+"',UPDATE_TIME=SYSDATE WHERE DTL_ID ="+dtlId+" AND ORDER_ID ="+orderId+"\n");
       factory.update(sql.toString(),null);
    }

    /**
     * 查询订单总金额
     *
     * @param orderId 订单ID
     * @return 结果集
     * @throws Exception
     */
    public QuerySet orderAmountSearch(String orderId)throws Exception {

        QuerySet qs = null;
        StringBuffer sql= new StringBuffer();
        sql.append(" SELECT NVL(SUM(ORDER_COUNT * UNIT_PRICE),0) AMOUNT\n" );
        sql.append("          FROM PT_BU_SALE_ORDER_DTL\n" );
        sql.append("         WHERE ORDER_ID = "+orderId+"\n" );
        qs = factory.select(null,sql.toString());
        return qs;
    }

    /**
     * 更新订单总金额
     *
     * @param ptBuSaleOrderVO 配件销售订单实体
     * @return true:成功;false:失败;
     */
    public boolean orderAmountUpdate(PtBuSaleOrderVO ptBuSaleOrderVO) throws Exception {

        return factory.update(ptBuSaleOrderVO);
    }

    /**
     * 订单配件查询
     *
     * @param pageManager 查询分页对象
     * @param pUser 当前登录user对象
     * @param conditions sql条件(默认：1=1)
     * @param orderId 订单ID
     * @return baseResultSet 结果集
     * @return
     * @throws Exception
     */
    public BaseResultSet orderPartSearch(PageManager page, User user, String conditions,String orderId) throws Exception {
        String wheres = conditions;
        wheres += " ORDER BY PART_CODE ASC";
        page.setFilter(wheres);
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.DTL_ID,\n" );
        sql.append("       T.PART_ID,\n" );
        sql.append("       T.PART_CODE,\n" );
        sql.append("       T.PART_NAME,\n" );
        sql.append("       T.UNIT,\n" );
        sql.append("       T.MIN_PACK,\n" );
        sql.append("       T.MIN_UNIT,\n" );
        sql.append("       T.UNIT_PRICE,\n" );
        sql.append("       T.IF_SUPPLIER,\n" );
        sql.append("       T.SUPPLIER_ID,\n" );
        sql.append("       T.SUPPLIER_CODE,\n" );
        sql.append("       T.SUPPLIER_NAME,\n" );
        sql.append("       T.ORDER_COUNT,\n" );
        sql.append("       T.WAREHOUSE_ID,\n" );
        sql.append("       T.AVAILABLE_AMOUNT,\n" );
        sql.append("       T.DEALER_AMOUNT,\n" );
        sql.append("       T.TYPE_CODE,\n" );
        sql.append("       T.MONTH_PART_COUNT,\n" );
        sql.append("       T.PART_STATUS\n" );
        sql.append(" FROM(SELECT T.DTL_ID,\n" );
        sql.append("       T.PART_ID,\n" );
        sql.append("       T.PART_CODE,\n" );
        sql.append("       T.PART_NAME,\n" );
        sql.append("       T.UNIT,\n" );
        sql.append("       T.MIN_PACK,\n" );
        sql.append("       T.MIN_UNIT,\n" );
        sql.append("       T.UNIT_PRICE,\n" );
        sql.append("       T.IF_SUPPLIER,\n" );
        sql.append("       T.SUPPLIER_ID,\n" );
        sql.append("       T.SUPPLIER_CODE,\n" );
        sql.append("       T.SUPPLIER_NAME,\n" );
        sql.append("       T.ORDER_COUNT,\n" );
        sql.append("       T.WAREHOUSE_ID,\n" );
        sql.append("       T.AVAILABLE_AMOUNT,\n" );
        sql.append("       NVL(T1.AVAILABLE_AMOUNT,0) DEALER_AMOUNT,\n" );
        sql.append("       (SELECT TYPE_CODE FROM PT_BA_DIRECT_TYPE WHERE TYPE_ID=T.DIRECT_TYPE_ID) TYPE_CODE,\n" );
        sql.append("       NVL(T.MONTH_PART_COUNT,0) MONTH_PART_COUNT,\n" );
        sql.append("       T.PART_STATUS\n" );
        sql.append("  FROM (SELECT D.DTL_ID,\n" );
        sql.append("               D.PART_ID,\n" );
        sql.append("               D.PART_CODE,\n" );
        sql.append("               D.PART_NAME,\n" );
        sql.append("               D.UNIT,\n" );
        sql.append("               D.MIN_PACK,\n" );
        sql.append("               D.MIN_UNIT,\n" );
        sql.append("               D.UNIT_PRICE,\n" );
        sql.append("               D.IF_SUPPLIER,\n" );
        sql.append("               D.SUPPLIER_ID,\n" );
        sql.append("               D.SUPPLIER_CODE,\n" );
        sql.append("               D.SUPPLIER_NAME,\n" );
        sql.append("               D.ORDER_COUNT,\n" );
        sql.append("               D.WAREHOUSE_ID,\n" );
        sql.append("               D.ORG_ID,\n" );
        sql.append("               D.DIRECT_TYPE_ID,\n" );
        sql.append("               D.MONTH_PART_COUNT,\n" );
        sql.append("               D.PART_STATUS,\n" );
        sql.append("               NVL(E.AVAILABLE_AMOUNT, 0) AVAILABLE_AMOUNT\n" );
        sql.append("          FROM (SELECT A.DTL_ID,\n" );
        sql.append("                       A.PART_ID,\n" );
        sql.append("                       A.PART_CODE,\n" );
        sql.append("                       A.PART_NAME,\n" );
        sql.append("                       C.UNIT,\n" );
        sql.append("                       C.MIN_PACK,\n" );
        sql.append("                       C.MIN_UNIT,\n" );
        sql.append("                       A.UNIT_PRICE,\n" );
        sql.append("                       A.IF_SUPPLIER,\n" );
        sql.append("                       A.SUPPLIER_ID,\n" );
        sql.append("                       A.SUPPLIER_CODE,\n" );
        sql.append("                       A.SUPPLIER_NAME,\n" );
        sql.append("                       A.ORDER_COUNT,\n" );
        sql.append("                       B.WAREHOUSE_ID,\n" );
        sql.append("                       B.ORG_ID,\n" );
        sql.append("                       C.DIRECT_TYPE_ID,\n" );
        sql.append("                       (SELECT SUM(B1.ORDER_COUNT) FROM PT_BU_SALE_ORDER A1,PT_BU_SALE_ORDER_DTL B1\n" );
        sql.append("                            WHERE A1.ORDER_ID = B1.ORDER_ID AND B1.PART_ID =A.PART_ID \n" );
        sql.append("                            AND A1.ORG_ID =(SELECT ORG_ID FROM PT_BU_SALE_ORDER WHERE ORDER_ID =B.DIR_SOURCE_ORDER_ID)\n" );
        sql.append("                            AND TO_CHAR(A1.APPLY_DATE,'YYYY-MM')=TO_CHAR(SYSDATE,'YYYY-MM') AND A1.ORDER_STATUS IN\n" );
        sql.append("                             ('"+DicConstant.DDZT_02+"','"+DicConstant.DDZT_03+"','"+DicConstant.DDZT_06+"')) MONTH_PART_COUNT,\n" );
        sql.append("                       C.PART_STATUS\n" );
        sql.append("                  FROM PT_BU_SALE_ORDER_DTL A,\n" );
        sql.append("                       PT_BU_SALE_ORDER     B,\n" );
        sql.append("                       PT_BA_INFO           C\n" );
        sql.append("                 WHERE 1 = 1\n" );
        sql.append("                   AND A.ORDER_ID = B.ORDER_ID\n" );
        sql.append("                   AND A.PART_ID = C.PART_ID\n" );
        sql.append("                   AND A.ORDER_ID = "+orderId+") D\n" );
        sql.append("          LEFT JOIN PT_BU_STOCK E\n" );
        sql.append("            ON D.PART_ID = E.PART_ID\n" );
        sql.append("           AND E.STOCK_STATUS='"+DicConstant.KCZT_01+"'\n" );
        sql.append("           AND D.SUPPLIER_ID = E.SUPPLIER_ID\n" );
        sql.append("           AND D.WAREHOUSE_ID = E.WAREHOUSE_ID) T LEFT JOIN PT_BU_DEALER_STOCK T1 ON T.ORG_ID = T1.ORG_ID AND T.PART_ID = T1.PART_ID)T");
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("UNIT", "JLDW");
        bs.setFieldDic("MIN_UNIT", "JLDW");
        bs.setFieldDic("IF_SUPPLIER", "SF");
        bs.setFieldDic("PART_STATUS", DicConstant.PJZT);
        return bs;
    }

    /**
     * 配件销售订单表(PT_BU_SALE_ORDER_DTL)添加
     *
     * @param ptBuSaleOrderVO 配件销售订单实体
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean insertSaleOrderDetail(PtBuSaleOrderDtlVO ptBuSaleOrderDtlVO) throws Exception {

        return factory.insert(ptBuSaleOrderDtlVO);
    }

    /**
     * 取的计划价
     */
    public QuerySet getPlanPrice(String partId)throws Exception {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append(" SELECT PLAN_PRICE FROM PT_BA_INFO WHERE PART_ID = '"+partId+"'\n" );
    	qs = factory.select(null,sql.toString());
    	return qs;
    }

    /**
     * 查询配件
     *
     * @param pageManager 查询分页对象
     * @param pUser 当前登录user对象
     * @param pConditions sql条件(默认：1=1)
     * @param pOrderId 订单ID
     * @return baseResultSet 结果集
     * @throws Exception
     */
    public BaseResultSet searchPart(PageManager pageManager, User pUser, String pConditions, String pOrderId,String warehouseId,String orgId) throws Exception {

        String wheres = pConditions;
        wheres += " AND NOT EXISTS(SELECT 1 FROM PT_BU_SALE_ORDER_DTL S WHERE S.ORDER_ID ="+pOrderId+" AND S.PART_ID = T.PART_ID AND T.SUPPLIER_ID = S.SUPPLIER_ID)\n";
        
        // begin by fuxiao 20141231: 赵冬冬提出取消此处限制
        // wheres += "AND NOT EXISTS (SELECT 1 FROM PT_BU_DEALER_STOCK S WHERE T.PART_ID = S.PART_ID AND S.ORG_ID = "+orgId+" AND T.SUPPLIER_ID = S.SUPPLIER_ID AND NVL(S.AVAILABLE_AMOUNT,0) >0)\n";
        // end 
        
        wheres += " ORDER BY T.PART_CODE ASC,T.SUPPLIER_ID ASC\n";
        pageManager.setFilter(wheres);
        BaseResultSet baseResultSet = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.PART_ID||SUPPLIER_ID KEY_ID,\n" );
        sql.append("       T.PART_ID,\n" );
        sql.append("       T.PART_CODE,\n" );
        sql.append("       T.PART_NAME,\n" );
        sql.append("       T.PART_NO,\n" );
        sql.append("       T.UNIT,\n" );
        sql.append("       T.MIN_UNIT,\n" );
        sql.append("       T.MIN_PACK,\n" );
        sql.append("       T.IF_SUPLY,\n" );
        sql.append("       T.SUPPLIER_ID,\n" );
        sql.append("       T.SUPPLIER_CODE,\n" );
        sql.append("       T.SUPPLIER_NAME,\n" );
        sql.append("       T.DIRECT_TYPE_ID,\n" );
        sql.append("       T.BELONG_ASSEMBLY,\n" );
        sql.append("       T.STOCK,\n" );
        sql.append("T.SALE_PRICE\n");
        sql.append("  FROM (SELECT C.PART_ID,\n" );
        sql.append("               C.PART_CODE,\n" );
        sql.append("               C.PART_NAME,\n" );
        sql.append("               C.PART_NO,\n" );
        sql.append("               C.UNIT,\n" );
        sql.append("               C.MIN_UNIT,\n" );
        sql.append("               C.MIN_PACK,\n" );
        sql.append("               C.SALE_PRICE,\n" );
        sql.append("               C.IF_SUPLY,\n" );
        sql.append("               C.IF_DIRECT,\n" );
        sql.append("               C.IF_ASSEMBLY,\n" );
        sql.append("               C.BELONG_ASSEMBLY,\n" );
        sql.append("               (SELECT SUPPLIER_ID\n" );
        sql.append("                  FROM PT_BA_SUPPLIER\n" );
        sql.append("                 WHERE SUPPLIER_CODE = '9XXXXXX' AND PART_IDENTIFY = "+DicConstant.YXBS_01+") SUPPLIER_ID,\n" );
        sql.append("               '9XXXXXX' SUPPLIER_CODE,\n" );
        sql.append("               (SELECT SUPPLIER_NAME\n" );
        sql.append("                  FROM PT_BA_SUPPLIER\n" );
        sql.append("                 WHERE SUPPLIER_CODE = '9XXXXXX' AND PART_IDENTIFY = "+DicConstant.YXBS_01+") SUPPLIER_NAME,\n" );
        sql.append("               C.DIRECT_TYPE_ID,\n" );
        sql.append("               CASE\n" );
        sql.append("                 WHEN NVL(B.AVAILABLE_AMOUNT, 0) > 0 THEN\n" );
        sql.append("                  '有'\n" );
        sql.append("                 ELSE\n" );
        sql.append("                  '无'\n" );
        sql.append("               END STOCK\n" );
        sql.append("          FROM (SELECT A.PART_ID,\n" );
        sql.append("                       A.PART_CODE,\n" );
        sql.append("                       A.PART_NAME,\n" );
        sql.append("                       A.PART_NO,\n" );
        sql.append("                       A.UNIT,\n" );
        sql.append("                       A.MIN_UNIT,\n" );
        sql.append("                       A.MIN_PACK,\n" );
        sql.append("                       A.SALE_PRICE,\n" );
        sql.append("                       A.IF_SUPLY,\n" );
        sql.append("                       A.IF_DIRECT,\n" );
        sql.append("                       A.BELONG_ASSEMBLY,\n" );
        sql.append("                       A.IF_ASSEMBLY,\n" );
        sql.append("                       A.DIRECT_TYPE_ID\n" );
        sql.append("                  FROM PT_BA_INFO A\n" );
        sql.append("                 WHERE A.IF_SUPLY = "+DicConstant.SF_02+"\n" );
        sql.append("                   AND A.PART_STATUS <> "+DicConstant.PJZT_02+"\n" );
//        sql.append("                   AND A.PART_TYPE <> "+DicConstant.PJLB_03+"\n" );
//        sql.append("                   AND A.IF_BOOK = "+DicConstant.SF_01+"\n" );
//        sql.append("                   AND A.SALE_PRICE > 0) C LEFT JOIN\n" );
        sql.append("                   ) C LEFT JOIN\n" );
        sql.append(" PT_BU_DEALER_STOCK B ON (C.PART_ID = B.PART_ID AND B.SUPPLIER_CODE = '9XXXXXX' AND B.ORG_ID = '" + orgId + "')\n" );
        sql.append("        UNION ALL\n" );
        sql.append("        SELECT D.PART_ID,\n" );
        sql.append("               D.PART_CODE,\n" );
        sql.append("               D.PART_NAME,\n" );
        sql.append("               D.PART_NO,\n" );
        sql.append("               D.UNIT,\n" );
        sql.append("               D.MIN_UNIT,\n" );
        sql.append("               D.MIN_PACK,\n" );
        sql.append("               D.SALE_PRICE,\n" );
        sql.append("               D.IF_SUPLY,\n" );
        sql.append("               D.IF_DIRECT,\n" );
        sql.append("               D.IF_ASSEMBLY,\n" );
        sql.append("               D.BELONG_ASSEMBLY,\n" );
        sql.append("               D.SUPPLIER_ID,\n" );
        sql.append("               D.SUPPLIER_CODE,\n" );
        sql.append("               D.SUPPLIER_NAME,\n" );
        sql.append("               D.DIRECT_TYPE_ID,\n" );
        sql.append("               CASE\n" );
        sql.append("                 WHEN NVL(E.AVAILABLE_AMOUNT, 0) > 0 THEN\n" );
        sql.append("                  '有'\n" );
        sql.append("                 ELSE\n" );
        sql.append("                  '无'\n" );
        sql.append("               END STOCK\n" );
        sql.append("          FROM (SELECT A.PART_ID,\n" );
        sql.append("                       A.PART_CODE,\n" );
        sql.append("                       A.PART_NAME,\n" );
        sql.append("                       A.PART_NO,\n" );
        sql.append("                       A.UNIT,\n" );
        sql.append("                       A.MIN_UNIT,\n" );
        sql.append("                       A.MIN_PACK,\n" );
        sql.append("                       A.SALE_PRICE,\n" );
        sql.append("                       A.IF_SUPLY,\n" );
        sql.append("                       C.SUPPLIER_ID,\n" );
        sql.append("                       C.SUPPLIER_CODE,\n" );
        sql.append("                       C.SUPPLIER_NAME,\n" );
        sql.append("                       A.IF_DIRECT,\n" );
        sql.append("                       A.IF_ASSEMBLY,\n" );
        sql.append("                       A.BELONG_ASSEMBLY,\n" );
        sql.append("                       A.DIRECT_TYPE_ID\n" );
        sql.append("                  FROM PT_BA_INFO             A,\n" );
        sql.append("                       PT_BA_PART_SUPPLIER_RL B,\n" );
        sql.append("                       PT_BA_SUPPLIER         C\n" );
        sql.append("                 WHERE A.PART_ID = B.PART_ID\n" );
        sql.append("                   AND B.SUPPLIER_ID = C.SUPPLIER_ID\n" );
        sql.append("                   AND B.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n" );
        sql.append("                   AND C.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n" );
        sql.append("                   AND A.IF_SUPLY = "+DicConstant.SF_01+"\n" );
        sql.append("                   AND A.PART_STATUS <> "+DicConstant.PJZT_02+"\n" );
//        sql.append("                   AND A.PART_TYPE <> "+DicConstant.PJLB_03+"\n" );
//        sql.append("                   AND A.IF_BOOK = "+DicConstant.SF_01+"\n" );
//        sql.append("                   AND A.SALE_PRICE > 0) D LEFT JOIN\n" );
        sql.append("                   ) D LEFT JOIN\n" );
        sql.append(" PT_BU_DEALER_STOCK E ON (D.PART_ID = E.PART_ID AND D.SUPPLIER_ID = E.SUPPLIER_ID  AND E.ORG_ID = '" + orgId + "')\n" );
        sql.append(" ) T\n" );
        //执行方法，不需要传递conn参数
        baseResultSet = factory.select(sql.toString(), pageManager);
        // 计量单位
        baseResultSet.setFieldDic("UNIT", DicConstant.JLDW);
        // 最小包装单位
        baseResultSet.setFieldDic("MIN_UNIT", DicConstant.JLDW);
        // 是否指定供应商
        baseResultSet.setFieldDic("IF_SUPLY", DicConstant.SF);
        return baseResultSet;
    }

    /**
     * 销售订单表(PT_BU_SALE_ORDER)修改
     *
     * @param ptBuSaleOrderVO 销售订单实体
     * @return true:成功;false:失败;
     * @throws Exception 
     */
    public boolean updateClaimCyclOrder(PtBuSaleOrderVO ptBuSaleOrderVO) throws Exception {

        return factory.update(ptBuSaleOrderVO);
    }

    /**
     * 直营订单提报查询
     *
     * @param pPageManager 查询分页对象
     * @param pUser 当前登录user对象
     * @param pConditions sql条件(默认：1=1)
     * @return baseResultSet 结果集
     * @throws Exception
     */
    public BaseResultSet searchDirectBusinessOrder(PageManager page,String pConditions, User pUser) throws Exception {

        String wheres = pConditions;
        wheres += " AND A.ORDER_ID =B.DIR_SOURCE_ORDER_ID AND B.STATUS = '" + DicConstant.YXBS_01 + "' ORDER BY B.APPLY_DATE \n";
        page.setFilter(wheres);
        BaseResultSet baseResultSet = null;
        StringBuilder sql= new StringBuilder();
        sql.append("SELECT B.ORDER_ID,\n" );
        sql.append("       subStr(B.ORDER_NO,-1) AS DELAY_COUNT,\n" );
        sql.append("       B.ORDER_NO,\n" );
        sql.append("       B.ORDER_TYPE,\n" );
        sql.append("       B.ORDER_STATUS,\n" );
        sql.append("       (SELECT ORG_CODE FROM PT_BU_SALE_ORDER WHERE ORDER_ID=B.DIR_SOURCE_ORDER_ID) SERVER_ORG_CODE,\n" );
        sql.append("       (SELECT ORG_NAME FROM PT_BU_SALE_ORDER WHERE ORDER_ID=B.DIR_SOURCE_ORDER_ID) SERVER_ORG_NAME,\n" );
        sql.append("       B.ORG_CODE,\n" );
        sql.append("       B.ORG_NAME,\n" );
        sql.append("       B.WAREHOUSE_ID,\n" );
        sql.append("       B.WAREHOUSE_CODE,\n" );
        sql.append("       B.WAREHOUSE_NAME,\n" );
        sql.append("       B.ORDER_AMOUNT,\n" );
        sql.append("       B.EXPECT_DATE,\n" );
        sql.append("       B.IF_CREDIT,\n" );
        sql.append("       B.IF_TRANS,\n" );
        sql.append("       B.CUSTORM_NAME,\n" );
        sql.append("       B.TRANS_TYPE,\n" );
        sql.append("       B.DELIVERY_ADDR,\n" );
        sql.append("       B.LINK_MAN,\n" );
        sql.append("       B.PHONE,\n" );
        sql.append("       B.ZIP_CODE,\n" );
        sql.append("       B.REMARKS,\n" );
        sql.append("       B.COMPANY_ID,\n" );
        sql.append("       B.ORG_ID,\n" );
        sql.append("       B.CREATE_USER,\n" );
        sql.append("       B.CREATE_TIME,\n" );
        sql.append("       B.UPDATE_USER,\n" );
        sql.append("       B.UPDATE_TIME,\n" );
        sql.append("       B.STATUS,\n" );
        sql.append("       B.OEM_COMPANY_ID,\n" );
        sql.append("       B.SECRET_LEVEL,\n" );
        sql.append("       B.IF_CHANEL_ORDER,\n" );
        sql.append("       B.IF_DELAY_ORDER,\n" );
        sql.append("       B.SOURCE_ORDER_ID,\n" );
        sql.append("       B.SOURCE_ORDER_NO,\n" );
        sql.append("       B.DIR_SOURCE_ORDER_ID,\n" );
        sql.append("       B.DIR_SOURCE_ORDER_NO,\n" );
        sql.append("       B.ADDR_TYPE,\n" );
        sql.append("       B.PROVINCE_CODE,\n" );
        sql.append("       B.PROVINCE_NAME,\n" );
        sql.append("       B.CITY_CODE,\n" );
        sql.append("       B.CITY_NAME,\n" );
        sql.append("       B.COUNTRY_CODE,\n" );
        sql.append("       B.COUNTRY_NAME,\n" );
        sql.append("       B.PROM_ID,\n" );
        sql.append("       B.APPLY_DATE,\n" );
        sql.append("       B.SHIP_STATUS,\n" );
        sql.append("       B.SB_MODELS_CODE,\n" );
        sql.append("       B.SB_BUY_DATE,\n" );
        sql.append("       B.SB_VIN,\n" );
        sql.append("       B.SB_FAULT_DATE,\n" );
        sql.append("       B.SB_FAULT_ANALYSE_NAME,\n" );
        sql.append("       B.REAL_AMOUNT,\n" );
        
        sql.append("NVL(ROUND(((SELECT SUM(CASE\n" );
    	sql.append("                            WHEN T1.AVAILABLE_AMOUNT / DECODE(T3.ORDER_COUNT,'0','1',T3.ORDER_COUNT) > 1 THEN\n" );
    	sql.append("                             1\n" );
    	sql.append("                            ELSE\n" );
    	sql.append("                             T1.AVAILABLE_AMOUNT / DECODE(T3.ORDER_COUNT,'0','1',T3.ORDER_COUNT)\n" );
    	sql.append("                          END)\n" );
    	sql.append("                 FROM PT_BU_STOCK          T1,\n" );
    	sql.append("                      PT_BU_SALE_ORDER     T2,\n" );
    	sql.append("                      PT_BU_SALE_ORDER_DTL T3\n" );
    	sql.append("                WHERE T1.PART_ID = T3.PART_ID\n" );
    	sql.append("                  AND T2.WAREHOUSE_ID = T1.WAREHOUSE_ID\n" );
    	sql.append("                  AND T2.ORDER_ID = T3.ORDER_ID\n" );
    	sql.append("                  AND T3.ORDER_ID = B.ORDER_ID) /\n" );
    	sql.append("             (SELECT COUNT(1)\n" );
    	sql.append("                 FROM PT_BU_SALE_ORDER_DTL\n" );
    	sql.append("                WHERE ORDER_ID = B.ORDER_ID)) * 100,\n" );
    	sql.append("             2),0) AS RATE,\n" );
    	sql.append("       NVL(ROUND(((SELECT SUM(CASE\n" );
    	sql.append("                            WHEN NVL(T1.AVAILABLE_AMOUNT, 0) = '0' THEN\n" );
    	sql.append("                             0\n" );
    	sql.append("                            ELSE\n" );
    	sql.append("                             1\n" );
    	sql.append("                          END)\n" );
    	sql.append("                 FROM PT_BU_STOCK          T1,\n" );
    	sql.append("                      PT_BU_SALE_ORDER     T2,\n" );
    	sql.append("                      PT_BU_SALE_ORDER_DTL T3\n" );
    	sql.append("                WHERE T1.PART_ID = T3.PART_ID\n" );
    	sql.append("                  AND T2.WAREHOUSE_ID = T1.WAREHOUSE_ID\n" );
    	sql.append("                  AND T2.ORDER_ID = T3.ORDER_ID\n" );
    	sql.append("                  AND T3.ORDER_ID = B.ORDER_ID) /\n" );
    	sql.append("             (SELECT COUNT(1)\n" );
    	sql.append("                 FROM PT_BU_SALE_ORDER_DTL\n" );
    	sql.append("                WHERE ORDER_ID = B.ORDER_ID)) * 100,\n" );
    	sql.append("             2),0) AS RATE1");
        
        sql.append("  FROM PT_BU_SALE_ORDER A,PT_BU_SALE_ORDER B\n" );
        //执行方法，不需要传递conn参数
        baseResultSet = factory.select(sql.toString(),page);
        // 订单类型
        baseResultSet.setFieldDic("ORDER_TYPE", DicConstant.DDLX);
        // 订单状态
        baseResultSet.setFieldDic("ORDER_STATUS", DicConstant.DDZT);
        // 运输方式
        baseResultSet.setFieldDic("TRANS_TYPE", DicConstant.FYFS);
        // 期望到货日期
        baseResultSet.setFieldDateFormat("EXPECT_DATE", "yyyy-MM-dd");
        // 故障日期
        baseResultSet.setFieldDateFormat("SB_FAULT_DATE", "yyyy-MM-dd HH:mm:ss");
        // 提报时间
        baseResultSet.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
        // 购车日期
        baseResultSet.setFieldDateFormat("SB_BUY_DATE", "yyyy-MM-dd HH:mm:ss");
        // 提报人
        baseResultSet.setFieldUserID("CREATE_USER");
        baseResultSet.setFieldDic("IF_DELAY_ORDER", DicConstant.SF);
        return baseResultSet;
    }
    
    public BaseResultSet dealerSearchDirectBusinessOrder(PageManager page,String pConditions, User pUser) throws Exception {

        String wheres = pConditions;
        wheres += " AND A.ORDER_ID = B.DIR_SOURCE_ORDER_ID AND A.STATUS = '" + DicConstant.YXBS_01 + "'\n";
        page.setFilter(wheres);
        BaseResultSet baseResultSet = null;
        StringBuilder sql= new StringBuilder();
        sql.append("SELECT A.ORDER_ID,\n" );
        sql.append("       A.ORDER_NO,\n" );
        sql.append("       A.ORDER_TYPE,\n" );
        sql.append("       A.ORDER_STATUS,\n" );
        sql.append("       A.ORG_CODE,\n" );
        sql.append("       A.ORG_NAME,\n" );
        sql.append("       B.WAREHOUSE_ID,\n" );
        sql.append("       B.WAREHOUSE_CODE,\n" );
        sql.append("       B.WAREHOUSE_NAME,\n" );
        sql.append("       A.ORDER_AMOUNT,\n" );
        sql.append("       A.EXPECT_DATE,\n" );
        sql.append("       A.IF_CREDIT,\n" );
        sql.append("       A.IF_TRANS,\n" );
        sql.append("       A.CUSTORM_NAME,\n" );
        sql.append("       A.TRANS_TYPE,\n" );
        sql.append("       A.DELIVERY_ADDR,\n" );
        sql.append("       A.LINK_MAN,\n" );
        sql.append("       A.PHONE,\n" );
        sql.append("       A.ZIP_CODE,\n" );
        sql.append("       (SELECT REMARKS FROM PT_BU_SALE_ORDER_CHECK WHERE CHECK_ID = (SELECT MAX(C.CHECK_ID) FROM PT_BU_SALE_ORDER_CHECK C WHERE ORDER_ID =(SELECT ORDER_ID FROM PT_BU_SALE_ORDER WHERE DIR_SOURCE_ORDER_ID = A.ORDER_ID))) as CHECK_REMARKS,\n" );
        sql.append("       A.REMARKS,\n" );
        sql.append("       A.COMPANY_ID,\n" );
        sql.append("       A.ORG_ID,\n" );
        sql.append("       A.CREATE_USER,\n" );
        sql.append("       A.CREATE_TIME,\n" );
        sql.append("       A.UPDATE_USER,\n" );
        sql.append("       A.UPDATE_TIME,\n" );
        sql.append("       A.STATUS,\n" );
        sql.append("       A.OEM_COMPANY_ID,\n" );
        sql.append("       A.SECRET_LEVEL,\n" );
        sql.append("       A.IF_CHANEL_ORDER,\n" );
        sql.append("       A.IF_DELAY_ORDER,\n" );
        sql.append("       A.SOURCE_ORDER_ID,\n" );
        sql.append("       A.SOURCE_ORDER_NO,\n" );
        sql.append("       A.DIR_SOURCE_ORDER_ID,\n" );
        sql.append("       A.DIR_SOURCE_ORDER_NO,\n" );
        sql.append("       A.ADDR_TYPE,\n" );
        sql.append("       A.PROVINCE_CODE,\n" );
        sql.append("       A.PROVINCE_NAME,\n" );
        sql.append("       A.CITY_CODE,\n" );
        sql.append("       A.CITY_NAME,\n" );
        sql.append("       A.COUNTRY_CODE,\n" );
        sql.append("       A.COUNTRY_NAME,\n" );
        sql.append("       A.PROM_ID,\n" );
        sql.append("       A.APPLY_DATE,\n" );
        sql.append("       A.SHIP_STATUS,\n" );
        sql.append("       A.REAL_AMOUNT,\n" );
        sql.append("       A.SB_VIN,\n" );
        sql.append("       A.SB_ENGINE_NO,\n" );
        sql.append("       A.SB_MODELS_CODE,\n" );
        sql.append("       A.SB_BUY_DATE,\n" );
        sql.append("       A.SB_MILEAGE,\n" );
        sql.append("       A.SB_USER_NAME,\n" );
        sql.append("       A.SB_LINK_MAN,\n" );
        sql.append("       A.SB_PHONE,\n" );
        sql.append("       A.SB_USER_ADDRESS,\n" );
        sql.append("       A.SB_FAULT_DATE,\n" );
        sql.append("       A.SB_FAULT_ANALYSE_CODE,\n" );
        sql.append("       A.SB_FAULT_ANALYSE_NAME\n" );
        sql.append("  FROM PT_BU_SALE_ORDER A,PT_BU_SALE_ORDER B\n" );
        //执行方法，不需要传递conn参数
        baseResultSet = factory.select(sql.toString(),page);
        // 订单类型
        baseResultSet.setFieldDic("ORDER_TYPE", DicConstant.DDLX);
        // 订单状态
        baseResultSet.setFieldDic("ORDER_STATUS", DicConstant.DDZT);
        // 运输方式
        baseResultSet.setFieldDic("TRANS_TYPE", DicConstant.FYFS);
        // 期望到货日期
        baseResultSet.setFieldDateFormat("EXPECT_DATE", "yyyy-MM-dd");
        // 购车日期
        baseResultSet.setFieldDateFormat("SB_BUY_DATE", "yyyy-MM-dd");
        // 本次故障日期
        baseResultSet.setFieldDateFormat("SB_FAULT_DATE", "yyyy-MM-dd");
        // 提报时间
        baseResultSet.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
        // 提报人
        baseResultSet.setFieldUserID("CREATE_USER");
        return baseResultSet;
    }

    /**
     * 查询服务站生成的配送中心的订单主键和单号
     *
     * @param pUser 当前登录user对象
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet searchSourceOrderId(String orderId) throws Exception {

        StringBuffer sql= new StringBuffer();
        sql.append("SELECT ORDER_ID,ORDER_NO FROM PT_BU_SALE_ORDER WHERE DIR_SOURCE_ORDER_ID='" + orderId + "' \n" );
        return factory.select(null, sql.toString());
    }

    /**
     * 查询服务站默认配送中心的ORG_ID
     *
     * @param pUser 当前登录user对象
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet searchOrgId(User pUser) throws Exception {

        StringBuffer sql= new StringBuffer();
        sql.append("SELECT ORG_ID,CODE,ONAME from tm_org WHERE ORG_ID =(SELECT DC_ID FROM PT_BA_SERVICE_DC WHERE ORG_ID='" + pUser.getOrgId() + "' AND IF_DEFAULT='" + DicConstant.SF_01 + "') \n" );
        return factory.select(null, sql.toString());
    }
    
    public void updateOrderDtl(String serverOrderId,String orderId)throws Exception {
        String sql1 = "DELETE FROM PT_BU_SALE_ORDER_DTL WHERE ORDER_ID = "+orderId+"\n";
        factory.update(sql1, null);
        StringBuilder sql= new StringBuilder();
        sql.append("INSERT INTO PT_BU_SALE_ORDER_DTL\n" );
        sql.append("  (DTL_ID,\n" );
        sql.append("   ORDER_ID,\n" );
        sql.append("   PART_ID,\n" );
        sql.append("   PART_CODE,\n" );
        sql.append("   PART_NAME,\n" );
        sql.append("   ORDER_COUNT,\n" );
        sql.append("   CREATE_USER,\n" );
        sql.append("   CREATE_TIME,\n" );
        sql.append("   IF_SUPPLIER,\n" );
        sql.append("   SUPPLIER_ID,\n" );
        sql.append("   SUPPLIER_CODE,\n" );
        sql.append("   SUPPLIER_NAME,\n" );
        sql.append("   UNIT_PRICE,\n" );
        sql.append("   AMOUNT)\n" );
        sql.append("  SELECT F_GETID(),"+orderId+",\n" );
        sql.append("         PART_ID,\n" );
        sql.append("         PART_CODE,\n" );
        sql.append("         PART_NAME,\n" );
        sql.append("         ORDER_COUNT,\n" );
        sql.append("         CREATE_USER,\n" );
        sql.append("         CREATE_TIME,\n" );
        sql.append("         IF_SUPPLIER,\n" );
        sql.append("         SUPPLIER_ID,\n" );
        sql.append("         SUPPLIER_CODE,\n" );
        sql.append("         SUPPLIER_NAME,\n" );
        sql.append("         UNIT_PRICE,\n" );
        sql.append("         AMOUNT\n" );
        sql.append("    FROM PT_BU_SALE_ORDER_DTL A\n" );
        sql.append("   WHERE ORDER_ID ="+serverOrderId+"\n");
        factory.update(sql.toString(), null);
    }
    /**
     * 配件销售订单(PT_BU_SALE_ORDER)添加
     *
     * @param ptBuSaleOrderVO 配件销售订单实体
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean insertClaimCyclOrder(PtBuSaleOrderVO ptBuSaleOrderVO) throws Exception {

        return factory.insert(ptBuSaleOrderVO);
    }

    /**
     * 获取订单审核角色
     * @return
     * @throws Exception
     */
    public QuerySet getRole()throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT P.ROLE_ID,M.LOCATION \n" );
    	sql.append("  FROM EAP_MENU M, TR_ROLE_MENU_MAP P\n" );
    	sql.append(" WHERE M.TITLE = '三包急件订单审核'\n" );
    	sql.append("   AND P.MENU_NAME = M.NAME");
    	return factory.select(null, sql.toString());
    }
    
    public QuerySet queryOrderDtl(User user, String ORER_ID) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.PART_CODE,\n" );
        sql.append("       T.PART_NAME,\n" );
        sql.append("       T2.DIC_VALUE UNIT,\n" );
        sql.append("       T1.MIN_PACK,\n" );
        sql.append("       T2.DIC_VALUE MIN_UNIT,\n" );
        sql.append("       T.ORDER_COUNT,\n" );
        sql.append("       T.AMOUNT,\n" );
        sql.append("       T.REMARKS\n" );
        sql.append("  FROM PT_BU_SALE_ORDER_DTL T, PT_BA_INFO T1,DIC_TREE T2\n" );
        sql.append(" WHERE T.PART_ID = T1.PART_ID");
        sql.append("	AND T1.UNIT = T2.ID");
        sql.append("	AND T.ORDER_ID = "+ORER_ID+"");
        //执行方法，不需要传递conn参数
        qs = factory.select(null,sql.toString());
        return qs;
    }
    public QuerySet queryOrderInfo(User user, String ORDER_ID) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
        //执行方法，不需要传递conn参数
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.CUSTORM_NAME,\n" );
        sql.append("       T.ORDER_NO,\n" );
        sql.append("       T.ORDER_AMOUNT,\n" );
        sql.append("       TO_CHAR(SYSDATE, 'YYYY-MM-DD') PRINT_DATE,\n" );
        sql.append("       T.LINK_MAN,\n" );
        sql.append("       T.PHONE,\n" );
        sql.append("       T.DELIVERY_ADDR\n" );
        sql.append("  FROM PT_BU_SALE_ORDER T\n" );
        sql.append(" WHERE T.ORDER_ID = "+ORDER_ID+"");
        qs = factory.select(null,sql.toString());
        return qs;
    }
}
