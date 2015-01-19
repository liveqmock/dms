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
 * 直营订单提报Dao
 *
 * 直营订单提报
 * @author zhengyao
 * @date 2014-08-25
 * @version 1.0
 */
public class DirectBusinessOrderMngDao extends BaseDAO {

    /**
     * 定义instance
     *
     * @param pActionContext
     * @return directSaleOrderMngDao 直营订单提报Dao
     */
    public static final DirectBusinessOrderMngDao getInstance(ActionContext pActionContext) {

        DirectBusinessOrderMngDao directSaleOrderMngDao = new DirectBusinessOrderMngDao();
        pActionContext.setDBFactory(directSaleOrderMngDao.factory);
        return directSaleOrderMngDao;
    }

    /**
     * 配件销售订单付款明细
     */
    public boolean saleOrderPayInsert(PtBuSaleOrderPayVO vo) throws Exception {

        return factory.insert(vo);
    }

    /**
     * 直营店订单生成
     */
    public void saleOrderInsert(String newOrderId,PtBuSaleOrderVO ptBuSaleOrderVO,User user) throws Exception {

        StringBuilder sql= new StringBuilder();
        sql.append("INSERT INTO PT_BU_SALE_ORDER(ORDER_ID,ORDER_NO,ORDER_TYPE,ORDER_STATUS,ORG_CODE,ORG_NAME,WAREHOUSE_ID,WAREHOUSE_CODE,WAREHOUSE_NAME," +
        		"ORDER_AMOUNT,EXPECT_DATE,IF_CREDIT,IF_TRANS,CUSTORM_NAME,TRANS_TYPE,DELIVERY_ADDR,LINK_MAN,PHONE,ZIP_CODE,REMARKS,COMPANY_ID,ORG_ID,CREATE_USER," +
        		"CREATE_TIME,STATUS,OEM_COMPANY_ID,IF_CHANEL_ORDER,IF_DELAY_ORDER,DIR_SOURCE_ORDER_ID,DIR_SOURCE_ORDER_NO,ADDR_TYPE,DELIVERY_CITY,CITY_NAME,APPLY_DATE)\n" );
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
        sql.append("    STATUS,\n" );
        sql.append("    '" + user.getOemCompanyId() + "',\n" );
        sql.append("    "+DicConstant.SF_02+",\n" );
        sql.append("    "+DicConstant.SF_02+",\n" );
        sql.append("    '" + ptBuSaleOrderVO.getOrderId() + "',\n" );
        sql.append("    '" + ptBuSaleOrderVO.getOrderNo() + "',\n" );
        sql.append("    ADDR_TYPE,\n" );
        sql.append("    DELIVERY_CITY,\n" );
        sql.append("    CITY_NAME,\n" );
        sql.append("    sysdate\n" );
        sql.append("     FROM PT_BU_SALE_ORDER\n" );
        sql.append("    WHERE ORDER_ID = '" + ptBuSaleOrderVO.getOrderId() + "')");

        factory.exec(sql.toString());
        
        String sql1 = "DELETE FROM PT_BU_SALE_ORDER_DTL WHERE ORDER_ID = "+newOrderId+"\n";
    	factory.update(sql1, null);
    	StringBuilder sql2= new StringBuilder();
    	sql2.append("INSERT INTO PT_BU_SALE_ORDER_DTL\n" );
    	sql2.append("  (DTL_ID,\n" );
    	sql2.append("   ORDER_ID,\n" );
    	sql2.append("   PART_ID,\n" );
    	sql2.append("   PART_CODE,\n" );
    	sql2.append("   PART_NAME,\n" );
    	sql2.append("   ORDER_COUNT,\n" );
    	sql2.append("   CREATE_USER,\n" );
    	sql2.append("   CREATE_TIME,\n" );
    	sql2.append("   IF_SUPPLIER,\n" );
    	sql2.append("   SUPPLIER_ID,\n" );
    	sql2.append("   SUPPLIER_CODE,\n" );
    	sql2.append("   SUPPLIER_NAME,\n" );
    	sql2.append("   UNIT_PRICE,\n" );
    	sql2.append("   AMOUNT)\n" );
    	sql2.append("  SELECT F_GETID(),"+newOrderId+",\n" );
    	sql2.append("         PART_ID,\n" );
    	sql2.append("         PART_CODE,\n" );
    	sql2.append("         PART_NAME,\n" );
    	sql2.append("         ORDER_COUNT,\n" );
    	sql2.append("         CREATE_USER,\n" );
    	sql2.append("         CREATE_TIME,\n" );
    	sql2.append("         IF_SUPPLIER,\n" );
    	sql2.append("         SUPPLIER_ID,\n" );
    	sql2.append("         SUPPLIER_CODE,\n" );
    	sql2.append("         SUPPLIER_NAME,\n" );
    	sql2.append("         UNIT_PRICE,\n" );
    	sql2.append("         AMOUNT\n" );
    	sql2.append("    FROM PT_BU_SALE_ORDER_DTL A\n" );
    	sql2.append("   WHERE ORDER_ID ="+ptBuSaleOrderVO.getOrderId()+"\n");
        factory.exec(sql2.toString());
    }

    /**
     * 订单审核明细更新审核数量
     */
    public boolean orderAuditCountUpdate(String auditCount,String dtlId,String orderId,User user) throws Exception {

        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE PT_BU_SALE_ORDER_DTL SET AUDIT_COUNT ="+auditCount+",UPDATE_USER='"+user.getAccount()+"',UPDATE_TIME=SYSDATE WHERE DTL_ID="+dtlId+"AND ORDER_ID ="+orderId+"\n");
        return factory.update(sql.toString(), null);
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
     * 订单主信息修改
     */
    public boolean orderInfoUpdate(PtBuSaleOrderVO vo) throws Exception {

        return factory.update(vo);
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
     * 更新资金账户
     */
    public boolean accountUpdate(String accountId,String payAmount) throws Exception {

        StringBuffer sql= new StringBuffer();
        sql.append("UPDATE PT_BU_ACCOUNT SET OCCUPY_AMOUNT = OCCUPY_AMOUNT+"+payAmount+" ,AVAILABLE_AMOUNT = AVAILABLE_AMOUNT - "+payAmount+" WHERE ACCOUNT_ID =+"+accountId+"\n");
        return factory.update(sql.toString(), null);
    }
    /**
	 * 订单资金使用查询
	 */
	public BaseResultSet orderFundsSearch(PageManager page, User user, String conditions,String orderId) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND A.ACCOUNT_ID = B.ACCOUNT_ID AND A.ORDER_ID = "+orderId+"\n";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT A.PAY_ID,A.ACCOUNT_ID, A.ACCOUNT_TYPE, B.AVAILABLE_AMOUNT, A.PAY_AMOUNT\n" );
    	sql.append("  FROM PT_BU_SALE_ORDER_PAY A, PT_BU_ACCOUNT B\n" );
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("ACCOUNT_TYPE", "ZJZHLX");
    	return bs;
    }
	/**
	 * 订单使用金额查询
	 */
	public QuerySet orderPayAmountSearch(String orderId) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT NVL(SUM(PAY_AMOUNT),0) PAY_AMOUNT\n" );
    	sql.append("  FROM PT_BU_SALE_ORDER_PAY\n" );
    	sql.append(" WHERE ORDER_ID = "+orderId+"\n");
    	qs = factory.select(null, sql.toString());
    	return qs;
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
//        String wheres = conditions;
//        wheres += " AND A.PART_ID = B.PART_ID AND A.ORDER_ID = "+orderId;
//        page.setFilter(wheres);
//        BaseResultSet bs = null;
//        StringBuffer sql= new StringBuffer();
//        sql.append("SELECT A.DTL_ID,\n" );
//        sql.append("       A.PART_ID,\n" );
//        sql.append("       A.PART_CODE,\n" );
//        sql.append("       A.PART_NAME,\n" );
//        sql.append("       A.PART_NO,\n" );
//        sql.append("       B.UNIT,\n" );
//        sql.append("       B.MIN_PACK,\n" );
//        sql.append("       B.MIN_UNIT,\n" );
//        sql.append("       A.UNIT_PRICE,\n" );
//        sql.append("       A.IF_SUPPLIER,\n" );
//        sql.append("       A.SUPPLIER_ID,\n" );
//        sql.append("       A.SUPPLIER_CODE,\n" );
//        sql.append("       A.SUPPLIER_NAME,\n" );
//        sql.append("       A.ORDER_COUNT,\n" );
//        sql.append("       A.AMOUNT\n" );
//        sql.append("  FROM PT_BU_SALE_ORDER_DTL A, PT_BA_INFO B\n" );
//        bs = factory.select(sql.toString(), page);
//        bs.setFieldDic("UNIT", "JLDW");
//        bs.setFieldDic("MIN_UNIT", "JLDW");
//        bs.setFieldDic("IF_SUPPLIER", "SF");
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
        sql.append("           AND D.SUPPLIER_ID = E.SUPPLIER_ID\n" );
        sql.append("           AND D.WAREHOUSE_ID = E.WAREHOUSE_ID) T");
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("UNIT", "JLDW");
        bs.setFieldDic("MIN_UNIT", "JLDW");
        bs.setFieldDic("IF_SUPPLIER", "SF");
        return bs;
    }

    /**
     * 延期订单生成
     */
    public void delayOrderInfoInsert(String orderId,User user) throws Exception {

        String orderNo = PartOddNumberUtil.getDelaySaleOrderNo(factory, orderId);
        String delayOrderId = SequenceUtil.getCommonSerivalNumber(factory);
        StringBuffer sql= new StringBuffer();
        sql.append("INSERT INTO PT_BU_SALE_ORDER (ORDER_ID,ORDER_NO,ORDER_TYPE,ORDER_STATUS,ORG_CODE,\n" );
        sql.append("   ORG_NAME,WAREHOUSE_ID,WAREHOUSE_CODE,WAREHOUSE_NAME,EXPECT_DATE,IF_CREDIT,\n" );
        sql.append("   IF_TRANS,TRANS_TYPE,DELIVERY_ADDR,LINK_MAN,PHONE,ZIP_CODE,REMARKS,COMPANY_ID,\n" );
        sql.append("   ORG_ID,CREATE_USER,CREATE_TIME,STATUS,OEM_COMPANY_ID,IF_CHANEL_ORDER,IF_DELAY_ORDER,\n" );
        sql.append("   SOURCE_ORDER_ID,SOURCE_ORDER_NO,ADDR_TYPE,PROVINCE_CODE,PROVINCE_NAME,CITY_CODE,CITY_NAME,COUNTRY_CODE,COUNTRY_NAME,PROM_ID,APPLY_DATE)\n" );
        sql.append("  SELECT "+delayOrderId+",'"+orderNo+"',ORDER_TYPE,"+DicConstant.DDZT_02+",ORG_CODE,\n" );
        sql.append("   ORG_NAME,WAREHOUSE_ID,WAREHOUSE_CODE,WAREHOUSE_NAME,EXPECT_DATE,IF_CREDIT,\n" );
        sql.append("   IF_TRANS,TRANS_TYPE,DELIVERY_ADDR,LINK_MAN,PHONE,ZIP_CODE,REMARKS,COMPANY_ID,\n" );
        sql.append("   ORG_ID,'"+user.getAccount()+"',SYSDATE,STATUS,OEM_COMPANY_ID,IF_CHANEL_ORDER,"+DicConstant.SF_01+",\n" );
        sql.append("   ORDER_ID,ORDER_NO,ADDR_TYPE,PROVINCE_CODE,PROVINCE_NAME,CITY_CODE,CITY_NAME,COUNTRY_CODE,COUNTRY_NAME,PROM_ID,APPLY_DATE\n" );
        sql.append("    FROM PT_BU_SALE_ORDER WHERE ORDER_ID = "+orderId+"\n" );
        //插入延期订单主信息
        factory.update(sql.toString(), null);

        StringBuffer sql1= new StringBuffer();
        sql1.append("INSERT INTO PT_BU_SALE_ORDER_DTL(DTL_ID,ORDER_ID,PART_ID,PART_CODE,PART_NAME,PART_NO,ORDER_COUNT,\n" );
        sql1.append("CREATE_USER,CREATE_TIME,IF_SUPPLIER,SUPPLIER_ID,SUPPLIER_CODE,SUPPLIER_NAME,UNIT_PRICE,AMOUNT,PLAN_PRICE)\n" );
        sql1.append("SELECT F_GETID(),"+delayOrderId+",PART_ID,PART_CODE,PART_NAME,PART_NO,ORDER_COUNT-AUDIT_COUNT,'"+user.getAccount()+"',\n" );
        sql1.append("SYSDATE,IF_SUPPLIER,SUPPLIER_ID,SUPPLIER_CODE,SUPPLIER_NAME,UNIT_PRICE,\n" );
        sql1.append("(ORDER_COUNT-AUDIT_COUNT)*UNIT_PRICE,PLAN_PRICE FROM PT_BU_SALE_ORDER_DTL\n" );
        sql1.append("WHERE ORDER_ID ="+orderId+" AND ORDER_COUNT-AUDIT_COUNT>0");
        //插入延期订单明细信息
        factory.update(sql1.toString(), null);

        QuerySet qs1 = null;
        StringBuffer sql6= new StringBuffer();
        sql6.append("SELECT SUM(AMOUNT) ORDER_AMOUNT\n" );
        sql6.append("  FROM PT_BU_SALE_ORDER_DTL A\n" );
        sql6.append(" WHERE A.ORDER_ID = "+delayOrderId+"\n");
        // 查询延期订单详细金额汇总
        qs1 = factory.select(null, sql6.toString());

        if(qs1.getRowCount()>0){
            String orderAmount = qs1.getString(1, "ORDER_AMOUNT");
            StringBuffer sql7 = new StringBuffer();
            sql7.append("UPDATE PT_BU_SALE_ORDER SET ORDER_AMOUNT="+orderAmount+" WHERE ORDER_ID = "+delayOrderId+"\n");
            //更新延期订单总金额
            factory.update(sql7.toString(), null);
        }
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
        wheres += " ORDER BY T.PART_CODE ASC,T.SUPPLIER_ID ASC\n";
        pageManager.setFilter(wheres);
        //定义返回结果集
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
        sql.append("	   T.SALE_PRICE,\n");
        sql.append("	   T.RETAIL_PRICE\n");
        sql.append("  FROM (SELECT C.PART_ID,\n" );
        sql.append("               C.PART_CODE,\n" );
        sql.append("               C.PART_NAME,\n" );
        sql.append("               C.PART_NO,\n" );
        sql.append("               C.UNIT,\n" );
        sql.append("               C.MIN_UNIT,\n" );
        sql.append("               C.MIN_PACK,\n" );
        sql.append("               C.SALE_PRICE,\n" );
        sql.append("               C.RETAIL_PRICE,\n" );
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
        sql.append("                       A.RETAIL_PRICE,\n" );
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
//        sql.append("                   AND A.SALE_PRICE > 0) C LEFT JOIN\n" );
        sql.append("                   ) C LEFT JOIN\n" );
        sql.append(" PT_BU_STOCK B ON C.PART_ID = B.PART_ID AND B.SUPPLIER_CODE = '9XXXXXX' AND B.WAREHOUSE_ID = "+warehouseId+"\n" );
        sql.append("        UNION ALL\n" );
        sql.append("        SELECT D.PART_ID,\n" );
        sql.append("               D.PART_CODE,\n" );
        sql.append("               D.PART_NAME,\n" );
        sql.append("               D.PART_NO,\n" );
        sql.append("               D.UNIT,\n" );
        sql.append("               D.MIN_UNIT,\n" );
        sql.append("               D.MIN_PACK,\n" );
        sql.append("               D.SALE_PRICE,\n" );
        sql.append("               D.RETAIL_PRICE,\n" );
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
        sql.append("                       A.RETAIL_PRICE,\n" );
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
//        sql.append("                   AND A.SALE_PRICE > 0) D LEFT JOIN\n" );
        sql.append("                   ) D LEFT JOIN\n" );
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
    public boolean updateDirectBusinessOrder(PtBuSaleOrderVO ptBuSaleOrderVO) throws Exception {

        return factory.update(ptBuSaleOrderVO);
    }

    /**
     * 销售订单表(PT_BU_SALE_ORDER)删除
     *
     * @param ptBuSaleOrderVO 销售订单实体
     * @param pUser 当前登录user对象
     * @return true:成功;false:失败;
     * @throws Exception 
     */
    public boolean deleteDirectBusinessOrder(PtBuSaleOrderVO ptBuSaleOrderVO, User pUser) throws Exception {

        // 按所属公司,所属机构删除
        String wheres = " AND ORG_ID = " + pUser.getOrgId() + "\n";
        // 配件收货地址变更表sql
        String stockString = "DELETE \n"
                           + "FROM \n"
                           + "    PT_BU_SALE_ORDER \n"
                           + "WHERE \n"
                           + "    ORDER_ID='" + ptBuSaleOrderVO.getOrderId() + "'"
                           + wheres;
        return factory.delete(stockString, null);
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
        wheres += " AND STATUS = '" + DicConstant.YXBS_01 + "'\n";
        page.setFilter(wheres);
        BaseResultSet baseResultSet = null;
        StringBuilder sql= new StringBuilder();
        sql.append("SELECT ORDER_ID,\n" );
        sql.append("       subStr(ORDER_NO,-1) AS DELAY_COUNT,\n" );
        sql.append("       ORDER_NO,\n" );
        sql.append("       ORDER_TYPE,\n" );
        sql.append("       ORDER_STATUS,\n" );
        sql.append("       ORG_CODE,\n" );
        sql.append("       ORG_NAME,\n" );
        sql.append("       WAREHOUSE_ID,\n" );
        sql.append("       WAREHOUSE_CODE,\n" );
        sql.append("       WAREHOUSE_NAME,\n" );
        sql.append("       ORDER_AMOUNT,\n" );
        sql.append("       EXPECT_DATE,\n" );
        sql.append("       IF_CREDIT,\n" );
        sql.append("       IF_TRANS,\n" );
        sql.append("       CUSTORM_NAME,\n" );
        sql.append("       TRANS_TYPE,\n" );
        sql.append("       DELIVERY_ADDR,\n" );
        sql.append("       LINK_MAN,\n" );
        sql.append("       PHONE,\n" );
        sql.append("       ZIP_CODE,\n" );
        sql.append("       REMARKS,\n" );
        sql.append("       COMPANY_ID,\n" );
        sql.append("       ORG_ID,\n" );
        sql.append("       CREATE_USER,\n" );
        sql.append("       CREATE_TIME,\n" );
        sql.append("       UPDATE_USER,\n" );
        sql.append("       UPDATE_TIME,\n" );
        sql.append("       STATUS,\n" );
        sql.append("       OEM_COMPANY_ID,\n" );
        sql.append("       SECRET_LEVEL,\n" );
        sql.append("       IF_CHANEL_ORDER,\n" );
        sql.append("       IF_DELAY_ORDER,\n" );
        sql.append("       SOURCE_ORDER_ID,\n" );
        sql.append("       SOURCE_ORDER_NO,\n" );
        sql.append("       ADDR_TYPE,\n" );
        sql.append("       PROVINCE_CODE,\n" );
        sql.append("       PROVINCE_NAME,\n" );
        sql.append("       CITY_CODE,\n" );
        sql.append("       CITY_NAME,\n" );
        sql.append("       COUNTRY_CODE,\n" );
        sql.append("       COUNTRY_NAME,\n" );
        sql.append("       PROM_ID,\n" );
        sql.append("       APPLY_DATE,\n" );
        sql.append("       SHIP_STATUS,\n" );
        sql.append("       REAL_AMOUNT\n" );
        sql.append("  FROM PT_BU_SALE_ORDER\n" );
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
        baseResultSet.setFieldDic("IF_DELAY_ORDER", DicConstant.SF);
        return baseResultSet;
    }
    /**
     * 资金账户可用余额查询(审核)
     *
     * @param pUser 当前登录user对象
     * @return baseResultSet 结果集
     * @throws Exception
     */
    public QuerySet accountSearch0(String orderId,String accountTypes) throws Exception {

        StringBuffer sql= new StringBuffer();
        sql.append("SELECT NVL(AVAILABLE_AMOUNT,0) AVAILABLE_AMOUNT,ACCOUNT_TYPE\n" );
        sql.append("  FROM PT_BU_ACCOUNT\n");
        sql.append(" WHERE ACCOUNT_TYPE IN ("+accountTypes+")\n");
        sql.append(" AND ORG_ID = (SELECT WAREHOUSE_ID FROM PT_BU_SALE_ORDER WHERE ORDER_ID='"+orderId+"') AND STATUS ="+DicConstant.YXBS_01+"\n");
        return factory.select(null, sql.toString());
    }
    /**
     * 资金账户可用余额查询(审核)
     *
     * @param pUser 当前登录user对象
     * @return baseResultSet 结果集
     * @throws Exception
     */
    public Object accountSearch(String orderId,boolean flag) throws Exception {

        StringBuffer sql= new StringBuffer();
        sql.append("SELECT NVL(AVAILABLE_AMOUNT,0) AVAILABLE_AMOUNT,ACCOUNT_ID,ACCOUNT_TYPE\n" );
        sql.append("  FROM PT_BU_ACCOUNT\n");
        sql.append(" WHERE ACCOUNT_TYPE IN ('"+DicConstant.ZJZHLX_01+"','"+DicConstant.ZJZHLX_02+"')\n");
        sql.append(" AND ORG_ID = (SELECT WAREHOUSE_ID FROM PT_BU_SALE_ORDER WHERE ORDER_ID='"+orderId+"') AND STATUS ="+DicConstant.YXBS_01+"\n");
        if (flag) {
            return factory.select(sql.toString(), new PageManager());
        } else {
            return factory.select(null, sql.toString());
        }
    }

    /**
     * 资金账户可用余额查询(提报)
     *
     * @param pUser 当前登录user对象
     * @return baseResultSet 结果集
     * @throws Exception
     */
    public Object accountSearch(User user,boolean flag) throws Exception {

        StringBuffer sql= new StringBuffer();
        sql.append("SELECT NVL(AVAILABLE_AMOUNT,0) AVAILABLE_AMOUNT,ACCOUNT_ID,ACCOUNT_TYPE\n" );
        sql.append("  FROM PT_BU_ACCOUNT\n");
        sql.append(" WHERE ACCOUNT_TYPE ="+DicConstant.ZJZHLX_01+"\n");
        sql.append(" AND ORG_ID = '"+user.getOrgId()+"' AND STATUS ="+DicConstant.YXBS_01+"\n");
        if (flag) {
            return factory.select(sql.toString(), new PageManager());
        } else {
            return factory.select(null, sql.toString());
        }
    }

    /**
     * 配件销售订单(PT_BU_SALE_ORDER)添加
     *
     * @param ptBuSaleOrderVO 配件销售订单实体
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean insertDirectBusinessOrder(PtBuSaleOrderVO ptBuSaleOrderVO) throws Exception {

        return factory.insert(ptBuSaleOrderVO);
    }
    
    /**
     * 查询订单资金是否已使用
     */
    public boolean orderAccountPaySearch(String orderId,String accountId,String accountType)throws Exception {
    	boolean flag = false;
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append(" SELECT PAY_ID\n" );
    	sql.append("          FROM PT_BU_SALE_ORDER_PAY\n" );
    	sql.append("         WHERE ORDER_ID = "+orderId+"\n" );
    	sql.append("         AND ACCOUNT_ID = "+accountId+"\n" );
    	sql.append("         AND ACCOUNT_TYPE = "+accountType+"\n" );
    	qs = factory.select(null,sql.toString());
    	if(qs.getRowCount()>0){
    		flag =true;
    	}
    	return flag;
    }
	/**
	 * 新增付款信息
     */
    public boolean orderFundsInsert(PtBuSaleOrderPayVO vo)
            throws Exception {
    	String payId=SequenceUtil.getCommonSerivalNumber(factory);
		vo.setPayId(payId);
        return factory.insert(vo);
    }
	/**
	 * 修改付款信息
     */
    public boolean orderFundsUpdate(PtBuSaleOrderPayVO vo)
            throws Exception {
        return factory.update(vo);
    }
    /**
	 * 删除付款信息
     */
    public boolean orderFundsDelete(PtBuSaleOrderPayVO vo)
            throws Exception {
        return factory.delete(vo);
    }
    
    /**
     * 资金账户可用余额查询(提报)
     *
     * @param pUser 当前登录user对象
     * @return baseResultSet 结果集
     * @throws Exception
     */
    public Object accountSearch1(User user) throws Exception {

    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT A.ACCOUNT_ID, A.ACCOUNT_TYPE, A.AVAILABLE_AMOUNT\n" );
    	sql.append("  FROM PT_BU_ACCOUNT A\n" );
    	sql.append(" WHERE A.ORG_ID = '"+user.getOrgId()+"'\n" );
    	sql.append("   AND A.ACCOUNT_TYPE IN (202701, 202702)");
        return factory.select(sql.toString(), new PageManager());
    }
}
