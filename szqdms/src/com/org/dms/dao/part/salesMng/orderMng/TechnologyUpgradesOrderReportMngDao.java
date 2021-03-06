package com.org.dms.dao.part.salesMng.orderMng;

import java.sql.CallableStatement;
import java.sql.Types;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.common.PartOddNumberUtil;
import com.org.dms.vo.part.PtBuSaleOrderCheckVO;
import com.org.dms.vo.part.PtBuSaleOrderDtlVO;
import com.org.dms.vo.part.PtBuSaleOrderVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.SequenceUtil;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 技术升级订单提报Dao
 *
 * 技术升级订单提报
 * @author zhengyao
 * @date 2014-09-10
 * @version 1.0
 */
public class TechnologyUpgradesOrderReportMngDao extends BaseDAO {

    /**
     * 定义instance
     *
     * @param pActionContext
     * @return directSaleOrderMngDao 技术升级订单提报Dao
     */
    public static final TechnologyUpgradesOrderReportMngDao getInstance(ActionContext pActionContext) {

        TechnologyUpgradesOrderReportMngDao technologyUpgradesOrderReportMngDao = new TechnologyUpgradesOrderReportMngDao();
        pActionContext.setDBFactory(technologyUpgradesOrderReportMngDao.factory);
        return technologyUpgradesOrderReportMngDao;
    }

    /**
     * 配送中心技术升级订单生成
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
     * 占用库存
     *
     * @param orderId 订单ID
     * @throws Exception
     */
    public void orderFreez(String orderId) throws Exception {

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
    public void delayOrderInfoInsert(String orderId,User user,String sourceOrderIdString,boolean flag) throws Exception {
    	String sourceOrderNo="";
    	String delaySourceOrderId="";
    	if(flag){
	    	// 服务站中心单号
	        sourceOrderNo = PartOddNumberUtil.getDelaySaleOrderNo(factory, sourceOrderIdString);
	        // 服务站订单ID
	        delaySourceOrderId = SequenceUtil.getCommonSerivalNumber(factory);
	        StringBuffer sql= new StringBuffer();
	        sql.append("INSERT INTO PT_BU_SALE_ORDER (ORDER_ID,ORDER_NO,ORDER_TYPE,ORDER_STATUS,ORG_CODE,\n" );
	        sql.append("   ORG_NAME,WAREHOUSE_ID,WAREHOUSE_CODE,WAREHOUSE_NAME,EXPECT_DATE,IF_CREDIT,\n" );
	        sql.append("   IF_TRANS,TRANS_TYPE,DELIVERY_ADDR,LINK_MAN,PHONE,ZIP_CODE,REMARKS,COMPANY_ID,\n" );
	        sql.append("   ORG_ID,CREATE_USER,CREATE_TIME,STATUS,OEM_COMPANY_ID,IF_CHANEL_ORDER,IF_DELAY_ORDER,\n" );
	        sql.append("   SOURCE_ORDER_ID,SOURCE_ORDER_NO,ADDR_TYPE,PROVINCE_CODE,PROVINCE_NAME,CITY_CODE,CITY_NAME,COUNTRY_CODE,COUNTRY_NAME,PROM_ID,APPLY_DATE)\n" );
	        sql.append("  SELECT "+delaySourceOrderId+",'"+sourceOrderNo+"',ORDER_TYPE,"+DicConstant.DDZT_02+",ORG_CODE,\n" );
	        sql.append("   ORG_NAME,WAREHOUSE_ID,WAREHOUSE_CODE,WAREHOUSE_NAME,EXPECT_DATE,IF_CREDIT,\n" );
	        sql.append("   IF_TRANS,TRANS_TYPE,DELIVERY_ADDR,LINK_MAN,PHONE,ZIP_CODE,REMARKS,COMPANY_ID,\n" );
	        sql.append("   ORG_ID,'"+user.getAccount()+"',SYSDATE,STATUS,OEM_COMPANY_ID,IF_CHANEL_ORDER,"+DicConstant.SF_01+",\n" );
	        sql.append("   ORDER_ID,ORDER_NO,ADDR_TYPE,PROVINCE_CODE,PROVINCE_NAME,CITY_CODE,CITY_NAME,COUNTRY_CODE,COUNTRY_NAME,PROM_ID,APPLY_DATE\n" );
	        sql.append("    FROM PT_BU_SALE_ORDER WHERE ORDER_ID = "+sourceOrderIdString+"\n" );
	        //插入延期订单主信息
	        factory.update(sql.toString(), null);
	
	        StringBuffer sql1= new StringBuffer();
	        sql1.append("INSERT INTO PT_BU_SALE_ORDER_DTL(DTL_ID,ORDER_ID,PART_ID,PART_CODE,PART_NAME,PART_NO,ORDER_COUNT,\n" );
	        sql1.append("CREATE_USER,CREATE_TIME,IF_SUPPLIER,SUPPLIER_ID,SUPPLIER_CODE,SUPPLIER_NAME,UNIT_PRICE,AMOUNT,PLAN_PRICE,REMARKS)\n" );
	        sql1.append("SELECT F_GETID(),"+delaySourceOrderId+",PART_ID,PART_CODE,PART_NAME,PART_NO,ORDER_COUNT-AUDIT_COUNT,'"+user.getAccount()+"',\n" );
	        sql1.append("SYSDATE,IF_SUPPLIER,SUPPLIER_ID,SUPPLIER_CODE,SUPPLIER_NAME,UNIT_PRICE,\n" );
	        sql1.append("(ORDER_COUNT-AUDIT_COUNT)*UNIT_PRICE,PLAN_PRICE,REMARKS FROM PT_BU_SALE_ORDER_DTL\n" );
	        sql1.append("WHERE ORDER_ID ="+sourceOrderIdString+" AND ORDER_COUNT-AUDIT_COUNT>0");
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
    	}
    	// 配送中心单号
        String orderNo = PartOddNumberUtil.getDelaySaleOrderNo(factory, orderId);
        String delayOrderId = SequenceUtil.getCommonSerivalNumber(factory);
        StringBuffer sql00= new StringBuffer();
        sql00.append("INSERT INTO PT_BU_SALE_ORDER (ORDER_ID,ORDER_NO,ORDER_TYPE,ORDER_STATUS,ORG_CODE,\n" );
        sql00.append("   ORG_NAME,WAREHOUSE_ID,WAREHOUSE_CODE,WAREHOUSE_NAME,EXPECT_DATE,IF_CREDIT,\n" );
        sql00.append("   IF_TRANS,TRANS_TYPE,DELIVERY_ADDR,LINK_MAN,PHONE,ZIP_CODE,REMARKS,COMPANY_ID,\n" );
        sql00.append("   ORG_ID,CREATE_USER,CREATE_TIME,STATUS,OEM_COMPANY_ID,IF_CHANEL_ORDER,IF_DELAY_ORDER,\n" );
        sql00.append("   SOURCE_ORDER_ID,SOURCE_ORDER_NO,ADDR_TYPE,PROVINCE_CODE,PROVINCE_NAME,CITY_CODE,CITY_NAME,COUNTRY_CODE,COUNTRY_NAME,PROM_ID,APPLY_DATE,DIR_SOURCE_ORDER_ID,DIR_SOURCE_ORDER_NO)\n" );
        sql00.append("  SELECT "+delayOrderId+",'"+orderNo+"',ORDER_TYPE,"+DicConstant.DDZT_02+",ORG_CODE,\n" );
        sql00.append("   ORG_NAME,(SELECT WAREHOUSE_ID FROM PT_BA_WAREHOUSE WHERE WAREHOUSE_TYPE =100105 AND STATUS=100201),(SELECT WAREHOUSE_CODE FROM PT_BA_WAREHOUSE WHERE WAREHOUSE_TYPE =100105 AND STATUS=100201),(SELECT WAREHOUSE_NAME FROM PT_BA_WAREHOUSE WHERE WAREHOUSE_TYPE =100105 AND STATUS=100201),EXPECT_DATE,IF_CREDIT,\n" );
        sql00.append("   IF_TRANS,TRANS_TYPE,DELIVERY_ADDR,LINK_MAN,PHONE,ZIP_CODE,REMARKS,COMPANY_ID,\n" );
        sql00.append("   ORG_ID,'"+user.getAccount()+"',SYSDATE,STATUS,OEM_COMPANY_ID,IF_CHANEL_ORDER,"+DicConstant.SF_01+",\n" );
        if(!"".equals(delaySourceOrderId)){
        	sql00.append("   ORDER_ID,ORDER_NO,ADDR_TYPE,PROVINCE_CODE,PROVINCE_NAME,CITY_CODE,CITY_NAME,COUNTRY_CODE,COUNTRY_NAME,PROM_ID,APPLY_DATE,"+delaySourceOrderId+",'"+sourceOrderNo+"'\n" );
        }else{
        	sql00.append("   ORDER_ID,ORDER_NO,ADDR_TYPE,PROVINCE_CODE,PROVINCE_NAME,CITY_CODE,CITY_NAME,COUNTRY_CODE,COUNTRY_NAME,PROM_ID,APPLY_DATE,NULL,NULL\n" );
        }
        sql00.append("    FROM PT_BU_SALE_ORDER WHERE ORDER_ID = "+orderId+"\n" );
        //插入延期订单主信息
        factory.update(sql00.toString(), null);

        StringBuffer sql11= new StringBuffer();
        sql11.append("INSERT INTO PT_BU_SALE_ORDER_DTL(DTL_ID,ORDER_ID,PART_ID,PART_CODE,PART_NAME,PART_NO,ORDER_COUNT,\n" );
        sql11.append("CREATE_USER,CREATE_TIME,IF_SUPPLIER,SUPPLIER_ID,SUPPLIER_CODE,SUPPLIER_NAME,UNIT_PRICE,AMOUNT,PLAN_PRICE,REMARKS)\n" );
        sql11.append("SELECT F_GETID(),"+delayOrderId+",PART_ID,PART_CODE,PART_NAME,PART_NO,ORDER_COUNT-AUDIT_COUNT,'"+user.getAccount()+"',\n" );
        sql11.append("SYSDATE,IF_SUPPLIER,SUPPLIER_ID,SUPPLIER_CODE,SUPPLIER_NAME,UNIT_PRICE,\n" );
        sql11.append("(ORDER_COUNT-AUDIT_COUNT)*UNIT_PRICE,PLAN_PRICE,REMARKS FROM PT_BU_SALE_ORDER_DTL\n" );
        sql11.append("WHERE ORDER_ID ="+orderId+" AND ORDER_COUNT-AUDIT_COUNT>0");
        //插入延期订单明细信息
        factory.update(sql11.toString(), null);

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
     * 更新资金账户
     */
    public boolean insertOrderPay(String orderId,String accountId,String payAmount,User user) throws Exception {
    	StringBuffer sql2= new StringBuffer();
        sql2.append("INSERT INTO PT_BU_SALE_ORDER_PAY(PAY_ID,ORDER_ID,ACCOUNT_ID,PAY_AMOUNT,CREATE_USER,CREATE_TIME,ACCOUNT_TYPE)\n" );
        sql2.append("SELECT F_GETID(),"+orderId+","+accountId+","+payAmount+",'"+user.getAccount()+"',SYSDATE,"+DicConstant.ZJZHLX_04+" FROM DUAL\n");
        return factory.update(sql2.toString(), null);
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
        sql.append("UPDATE PT_BU_ACCOUNT SET OCCUPY_AMOUNT = OCCUPY_AMOUNT+"+payAmount+" ,AVAILABLE_AMOUNT = AVAILABLE_AMOUNT-"+payAmount+" WHERE ACCOUNT_ID =+"+accountId+"\n");
        return factory.update(sql.toString(), null);
    }

    /**
     * 查询审核员
     */
    public QuerySet sourceOrderId(String orderId) throws Exception {

    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT DIR_SOURCE_ORDER_ID FROM PT_BU_SALE_ORDER WHERE ORDER_ID= '" + orderId + "'");

        return factory.select(null, sql.toString());
    }

    /**
     * 资金占用释放
     */
    public void orderReleaseFreez(String orderId,User user)
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
            sql2.append("UPDATE PT_BU_ACCOUNT SET OCCUPY_AMOUNT =OCCUPY_AMOUNT -"+occupyFunds+",AVAILABLE_AMOUNT= AVAILABLE_AMOUNT+"+occupyFunds+",\n");
            sql2.append(" UPDATE_USER='"+user.getAccount()+"',UPDATE_TIME=SYSDATE WHERE ACCOUNT_ID="+accountId+" AND ACCOUNT_TYPE="+accountType+"\n");
            factory.update(sql2.toString(), null);
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
     * 订单审核明细更新审核数量
     */
    public QuerySet searchOrderDtl(String orderId) throws Exception {

    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT DTL_ID FROM PT_BU_SALE_ORDER_DTL WHERE ORDER_ID='"+orderId+"'");
        return factory.select(null,sql.toString());
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
        sql.append("       T.AVAILABLE_AMOUNT\n" );
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
        sql.append("                       B.WAREHOUSE_ID\n" );
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
        sql.append("           AND D.WAREHOUSE_ID = E.WAREHOUSE_ID) T");
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("UNIT", "JLDW");
        bs.setFieldDic("MIN_UNIT", "JLDW");
        bs.setFieldDic("IF_SUPPLIER", "SF");
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
    public BaseResultSet searchPart(PageManager pageManager, User pUser, String pConditions, String pOrderId,String warehouseId) throws Exception {

        String wheres = pConditions;
        wheres += " AND NOT EXISTS(SELECT 1 FROM PT_BU_SALE_ORDER_DTL S WHERE S.ORDER_ID ="+pOrderId+" AND S.PART_ID = T.PART_ID AND T.SUPPLIER_ID = S.SUPPLIER_ID)\n";
        wheres += " ORDER BY T.PART_CODE ASC,T.SUPPLIER_CODE ASC\n";
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
        sql.append("                   AND A.PART_TYPE <> "+DicConstant.PJLB_03+"\n" );
//        sql.append("                   AND A.IF_BOOK = "+DicConstant.SF_01+"\n" );
        sql.append("                   AND A.SALE_PRICE > 0) C LEFT JOIN\n" );
        sql.append(" PT_BU_STOCK B ON C.PART_ID = B.PART_ID AND B.SUPPLIER_CODE = '9XXXXXX' AND B.WAREHOUSE_ID = "+warehouseId+"\n" );
        sql.append("        UNION\n" );
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
        sql.append("                   AND A.PART_TYPE <> "+DicConstant.PJLB_03+"\n" );
//        sql.append("                   AND A.IF_BOOK = "+DicConstant.SF_01+"\n" );
        sql.append("                   AND A.SALE_PRICE > 0) D LEFT JOIN\n" );
        sql.append(" PT_BU_STOCK E ON D.PART_ID = E.PART_ID AND D.SUPPLIER_ID = E.SUPPLIER_ID AND E.WAREHOUSE_ID = "+warehouseId+"\n" );
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
    public BaseResultSet searchDirectBusinessOrderCheck(PageManager page,String pConditions, User pUser) throws Exception {

        String wheres = pConditions;
        wheres += " AND (A.ORDER_ID =B.DIR_SOURCE_ORDER_ID OR A.ORDER_ID=B.SOURCE_ORDER_ID)   AND B.STATUS = '" + DicConstant.YXBS_01 + "'\n";
        StringBuilder sql1= new StringBuilder();
        sql1.append("UNION SELECT C.ORDER_ID,\n" );
        sql1.append("       subStr(C.ORDER_NO,-1) AS DELAY_COUNT,\n" );
        sql1.append("       C.ORDER_NO,\n" );
        sql1.append("       C.ORDER_TYPE,\n" );
        sql1.append("       C.ORDER_STATUS,\n" );
        sql1.append("       C.ORG_CODE,\n" );
        sql1.append("       C.ORG_NAME,\n" );
        sql1.append("       C.WAREHOUSE_ID,\n" );
        sql1.append("       C.WAREHOUSE_CODE,\n" );
        sql1.append("       C.WAREHOUSE_NAME,\n" );
        sql1.append("       C.ORDER_AMOUNT,\n" );
        sql1.append("       C.EXPECT_DATE,\n" );
        sql1.append("       C.IF_CREDIT,\n" );
        sql1.append("       C.IF_TRANS,\n" );
        sql1.append("       C.CUSTORM_NAME,\n" );
        sql1.append("       C.TRANS_TYPE,\n" );
        sql1.append("       C.DELIVERY_ADDR,\n" );
        sql1.append("       C.LINK_MAN,\n" );
        sql1.append("       C.PHONE,\n" );
        sql1.append("       C.ZIP_CODE,\n" );
        sql1.append("       C.REMARKS,\n" );
        sql1.append("       C.COMPANY_ID,\n" );
        sql1.append("       C.ORG_ID,\n" );
        sql1.append("       C.CREATE_USER,\n" );
        sql1.append("       C.CREATE_TIME,\n" );
        sql1.append("       C.UPDATE_USER,\n" );
        sql1.append("       C.UPDATE_TIME,\n" );
        sql1.append("       C.STATUS,\n" );
        sql1.append("       C.OEM_COMPANY_ID,\n" );
        sql1.append("       C.SECRET_LEVEL,\n" );
        sql1.append("       C.IF_CHANEL_ORDER,\n" );
        sql1.append("       C.IF_DELAY_ORDER,\n" );
        sql1.append("       C.SOURCE_ORDER_ID,\n" );
        sql1.append("       C.SOURCE_ORDER_NO,\n" );
        sql1.append("       C.DIR_SOURCE_ORDER_ID,\n" );
        sql1.append("       C.DIR_SOURCE_ORDER_NO,\n" );
        sql1.append("       C.ADDR_TYPE,\n" );
        sql1.append("       C.PROVINCE_CODE,\n" );
        sql1.append("       C.PROVINCE_NAME,\n" );
        sql1.append("       C.CITY_CODE,\n" );
        sql1.append("       C.CITY_NAME,\n" );
        sql1.append("       C.COUNTRY_CODE,\n" );
        sql1.append("       C.COUNTRY_NAME,\n" );
        sql1.append("       C.PROM_ID,\n" );
        sql1.append("       C.APPLY_DATE,\n" );
        sql1.append("       C.SHIP_STATUS,\n" );
        sql1.append("       C.REAL_AMOUNT FROM PT_BU_SALE_ORDER C WHERE C.ORG_ID='"+pUser.getOrgId()+"' AND C.ORDER_STATUS='"+DicConstant.DDZT_02+"' ");
        wheres+=sql1.toString();
        page.setFilter(wheres);
        BaseResultSet baseResultSet = null;
        StringBuilder sql= new StringBuilder();
        sql.append("SELECT B.ORDER_ID,\n" );
        sql.append("       subStr(B.ORDER_NO,-1) AS DELAY_COUNT,\n" );
        sql.append("       B.ORDER_NO,\n" );
        sql.append("       B.ORDER_TYPE,\n" );
        sql.append("       B.ORDER_STATUS,\n" );
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
        sql.append("       B.REAL_AMOUNT\n" );
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
        // 提报时间
        baseResultSet.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
        // 提报人
        baseResultSet.setFieldUserID("CREATE_USER");
        return baseResultSet;
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
        wheres += " AND B.IF_CHANEL_ORDER='"+DicConstant.SF_02+"' AND B.STATUS = '" + DicConstant.YXBS_01 + "'\n";
        page.setFilter(wheres);
        BaseResultSet baseResultSet = null;
        StringBuilder sql= new StringBuilder();
        sql.append("SELECT B.ORDER_ID,\n" );
        sql.append("       subStr(B.ORDER_NO,-1) AS DELAY_COUNT,\n" );
        sql.append("       B.ORDER_NO,\n" );
        sql.append("       B.ORDER_TYPE,\n" );
        sql.append("       B.ORDER_STATUS,\n" );
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
        sql.append("       B.REAL_AMOUNT\n" );
        sql.append("  FROM PT_BU_SALE_ORDER B\n" );
        //执行方法，不需要传递conn参数
        baseResultSet = factory.select(sql.toString(),page);
        // 是否延期订单
        baseResultSet.setFieldDic("IF_DELAY_ORDER", DicConstant.SF);
        // 订单类型
        baseResultSet.setFieldDic("ORDER_TYPE", DicConstant.DDLX);
        // 订单状态
        baseResultSet.setFieldDic("ORDER_STATUS", DicConstant.DDZT);
        // 运输方式
        baseResultSet.setFieldDic("TRANS_TYPE", DicConstant.FYFS);
        // 期望到货日期
        baseResultSet.setFieldDateFormat("EXPECT_DATE", "yyyy-MM-dd");
        // 提报时间
        baseResultSet.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
        // 提报人
        baseResultSet.setFieldUserID("CREATE_USER");
        return baseResultSet;
    }

    public BaseResultSet dealerSearchDirectBusinessOrder(PageManager page,String pConditions, User pUser) throws Exception {

        String wheres = pConditions;
        wheres += " AND A.STATUS = '" + DicConstant.YXBS_01 + "'\n";
        page.setFilter(wheres);
        BaseResultSet baseResultSet = null;
        StringBuilder sql= new StringBuilder();
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
        sql.append("       A.EXPECT_DATE,\n" );
        sql.append("       A.IF_CREDIT,\n" );
        sql.append("       A.IF_TRANS,\n" );
        sql.append("       A.CUSTORM_NAME,\n" );
        sql.append("       A.TRANS_TYPE,\n" );
        sql.append("       A.DELIVERY_ADDR,\n" );
        sql.append("       A.LINK_MAN,\n" );
        sql.append("       A.PHONE,\n" );
        sql.append("       A.ZIP_CODE,\n" );
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
        sql.append("       A.REAL_AMOUNT\n" );
        sql.append("  FROM PT_BU_SALE_ORDER A\n" );
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
        // 提报时间
        baseResultSet.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
        // 提报人
        baseResultSet.setFieldUserID("CREATE_USER");
        return baseResultSet;
    }
    
    /**
     * 资金账户可用余额查询
     *
     * @param pUser 当前登录user对象
     * @return baseResultSet 结果集
     * @throws Exception
     */
    public Object accountSearch(User user,boolean flag) throws Exception {

        StringBuffer sql= new StringBuffer();
        sql.append("SELECT NVL(AVAILABLE_AMOUNT,0) AVAILABLE_AMOUNT,ACCOUNT_ID,ACCOUNT_TYPE\n" );
        sql.append("  FROM PT_BU_ACCOUNT\n");
        sql.append(" WHERE ACCOUNT_TYPE ="+DicConstant.ZJZHLX_04+"\n");
        sql.append(" AND ORG_ID = (SELECT DC_ID FROM PT_BA_SERVICE_DC WHERE ORG_ID='" + user.getOrgId() + "' AND IF_DEFAULT='" + DicConstant.SF_01 + "') ");
        sql.append(" AND STATUS ="+DicConstant.YXBS_01+"\n");
        if (flag) {
            return factory.select(sql.toString(), new PageManager());
        } else {
            return factory.select(null, sql.toString());
        }
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
}
