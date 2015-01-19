package com.org.dms.dao.part.salesMng.orderMng;

import java.util.Calendar;

import com.org.dms.common.DicConstant;
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
 * @Description:订单提报Dao
 * @Date: 2014-07-14
 * @Author gouwentan
 */
public class PartOrderReportDao extends BaseDAO{
	public static final PartOrderReportDao getInstance(ActionContext atx) {
		PartOrderReportDao dao = new PartOrderReportDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * 订单查询(导出)
     *
     * @pConditions 查询条件
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet expData(String pConditions,User user) throws Exception {

    	String wheres = " WHERE ROW_NO IN ("+ pConditions + ") AND USER_ACCOUNT='"+user.getAccount()+"' \n";
    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT PART_CODE, SUPPLIER_CODE, COUNT\n" );
    	sql.append("  FROM PT_BU_SALE_ORDER_DTL_TMP");
        //执行方法，不需要传递conn参数
        return factory.select(null, sql.toString()+wheres);
    }
	/**
	 * 订单提报验证
	 *
	 * @param orderId 订单主键
	 * @param orderType 订单类型
	 * @return 结果集
	 */
	public QuerySet partOrderReportCheck(String orderId,String orderType,User user)  throws Exception {

		StringBuilder sql= new StringBuilder();
		sql.append("SELECT 1\n" );
		sql.append("  FROM PT_BU_SALE_ORDER\n" );
		sql.append(" WHERE ORDER_ID = '" + orderId + "'\n" );
		sql.append("   AND CREATE_TIME = (SELECT MIN(CREATE_TIME) CREATE_TIME\n" );
		sql.append("                        FROM PT_BU_SALE_ORDER\n" );
		sql.append("                       WHERE ORG_ID='" + user.getOrgId() + "' AND ORDER_TYPE = '" + orderType + "'  AND ORDER_STATUS IN ('"+DicConstant.DDZT_01+"',"+DicConstant.DDZT_04+") AND WAREHOUSE_ID=(SELECT WAREHOUSE_ID FROM PT_BU_SALE_ORDER WHERE ORDER_ID='"+orderId+"'))");

		return factory.select(null, sql.toString());
	}
	/**
	 * 订单查询
	 */
	public BaseResultSet partOrderSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND T.ORDER_STATUS IN ("+DicConstant.DDZT_01+", "+DicConstant.DDZT_04+") AND T.ORG_ID = "+user.getOrgId()+"\n";
    	String orgType = user.getOrgDept().getOrgType();
    	if (DicConstant.ZZLB_09.equals(orgType)) {
    		// 配送中心
    		wheres += " AND T.ORDER_TYPE IN ("+DicConstant.DDLX_01+", "+DicConstant.DDLX_02+", "+DicConstant.DDLX_03+", "+DicConstant.DDLX_04+", "+DicConstant.DDLX_05+", "+DicConstant.DDLX_06+","+DicConstant.DDLX_11+","+DicConstant.DDLX_12+")";
    	}
    	if (DicConstant.ZZLB_10.equals(orgType)||DicConstant.ZZLB_11.equals(orgType)) {
    		// 服务站
    		wheres += " AND T.ORDER_TYPE IN ("+DicConstant.DDLX_01+", "+DicConstant.DDLX_02+", "+DicConstant.DDLX_03+", "+DicConstant.DDLX_04+", "+DicConstant.DDLX_05+", "+DicConstant.DDLX_06+", "+DicConstant.DDLX_07+","+DicConstant.DDLX_11+","+DicConstant.DDLX_12+")";
    	}
    	wheres += " ORDER BY T.ORDER_STATUS DESC,T.CREATE_TIME ASC\n";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.ORDER_ID,T.ORDER_NO,T.ORDER_TYPE,T.WAREHOUSE_ID,T.WAREHOUSE_CODE,T.WAREHOUSE_NAME,\n");
    	sql.append("  T.EXPECT_DATE,T.ORDER_AMOUNT,T.IF_CREDIT,T.IF_TRANS,T.TRANS_TYPE,T.DELIVERY_ADDR,T.LINK_MAN,\n");
    	sql.append("  T.PHONE,T.ZIP_CODE,T.REMARKS,T.ADDR_TYPE,T.PROVINCE_CODE,T.PROVINCE_NAME,T.CITY_CODE,T.CITY_NAME,T.COUNTRY_CODE,T.COUNTRY_NAME,T.ORDER_STATUS,\n");
    	sql.append("  T.CREATE_USER,T.APPLY_DATE,T.PROM_ID,T.PROM_NAME,T.SUPPLIER_ID,T.SUPPLIER_CODE,T.SUPPLIER_NAME,T.DIRECT_TYPE_ID,T.DIRECT_TYPE_CODE,T.DIRECT_TYPE_NAME,T.CREATE_TIME,T.VIN,T.BELONG_ASSEMBLY, \n");
    	sql.append("  (SELECT REMARKS FROM PT_BU_SALE_ORDER_CHECK WHERE CHECK_ID = (SELECT MAX(C.CHECK_ID) FROM PT_BU_SALE_ORDER_CHECK C WHERE ORDER_ID =(SELECT ORDER_ID FROM PT_BU_SALE_ORDER WHERE ORDER_ID = T.ORDER_ID))) AS CHECK_REMARKS\n" );
    	sql.append(" FROM (SELECT A.ORDER_ID,\n" );
    	sql.append("       A.ORDER_NO,\n" );
    	sql.append("       A.ORDER_TYPE,\n" );
    	sql.append("       A.WAREHOUSE_ID,\n" );
    	sql.append("       A.WAREHOUSE_CODE,\n" );
    	sql.append("       A.WAREHOUSE_NAME,\n" );
    	sql.append("       A.EXPECT_DATE,\n" );
    	sql.append("       A.ORDER_AMOUNT,\n" );
    	sql.append("       A.IF_CREDIT,\n" );
    	sql.append("       A.IF_TRANS,\n" );
    	sql.append("       A.TRANS_TYPE,\n" );
    	sql.append("       A.DELIVERY_ADDR,\n" );
    	sql.append("       A.ORG_ID,\n" );
    	sql.append("       A.LINK_MAN,\n" );
    	sql.append("       A.PHONE,\n" );
    	sql.append("       A.ZIP_CODE,\n" );
    	sql.append("       A.REMARKS,\n" );
    	sql.append("       A.ADDR_TYPE,\n" );
    	sql.append("       A.PROVINCE_CODE,\n" );
    	sql.append("       A.PROVINCE_NAME,\n" );
    	sql.append("       A.CITY_CODE,\n" );
    	sql.append("       A.CITY_NAME,\n" );
    	sql.append("       A.COUNTRY_CODE,\n" );
    	sql.append("       A.COUNTRY_NAME,\n" );
    	sql.append("       A.ORDER_STATUS,\n" );
    	sql.append("       A.CREATE_USER,\n" );
    	sql.append("       A.APPLY_DATE,\n" );
    	sql.append("       A.PROM_ID,\n" );
    	sql.append("       B.PROM_NAME,\n" );
    	sql.append("       A.SUPPLIER_ID,\n" );
    	sql.append("       A.SUPPLIER_CODE,\n" );
    	sql.append("       A.SUPPLIER_NAME,\n" );
    	sql.append("       A.DIRECT_TYPE_ID,\n" );
    	sql.append("       A.DIRECT_TYPE_CODE,\n" );
    	sql.append("       A.DIRECT_TYPE_NAME,\n" );
    	sql.append("       A.VIN,\n" );
    	sql.append("       A.BELONG_ASSEMBLY,\n" );
    	sql.append("       A.CREATE_TIME\n" );
    	sql.append("  FROM PT_BU_SALE_ORDER A LEFT JOIN PT_BU_PROMOTION B ON A.PROM_ID = B.PROM_ID )T\n");
    	bs = factory.select(sql.toString(), page);
		bs.setFieldDic("ORDER_TYPE", "DDLX");
		bs.setFieldDic("ORDER_STATUS", "DDZT");
		bs.setFieldDic("TRANS_TYPE", "FYFS");
		bs.setFieldDic("IF_CREDIT", "SF");
		bs.setFieldDic("IF_TRANS", "SF");
		bs.setFieldDic("BELONG_ASSEMBLY", DicConstant.PJZCLB);
		bs.setFieldDateFormat("EXPECT_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
        // 提报人
        bs.setFieldUserID("CREATE_USER");
    	return bs;
    }
	/**
	 * 资金账户可用余额查询
	 */
	public BaseResultSet accountSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND T.ACCOUNT_TYPE IN ("+DicConstant.ZJZHLX_01+", "+DicConstant.ZJZHLX_02+", "+DicConstant.ZJZHLX_03+")\n";
    	wheres += " AND ORG_ID = "+user.getOrgId()+"AND T.STATUS ="+DicConstant.YXBS_01+"\n";
    	wheres += " UNION SELECT 2 TYPE, NVL(SUM(AVAILABLE_AMOUNT),0) AVAILABLE_AMOUNT FROM PT_BU_ACCOUNT T\n";
    	wheres += " WHERE T.ACCOUNT_TYPE = "+DicConstant.ZJZHLX_04+" AND ORG_ID = "+user.getOrgId()+"AND T.STATUS ="+DicConstant.YXBS_01+"\n";
    	page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT 1 TYPE, NVL(SUM(T.AVAILABLE_AMOUNT),0) AVAILABLE_AMOUNT\n" );
    	sql.append("  FROM PT_BU_ACCOUNT T\n" );
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
	/**
	 * 配件明细重复校验
	 */
	public QuerySet orderPartCheck(String orderId,String partId,String supplierId) throws Exception
    {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.PART_CODE FROM PT_BU_SALE_ORDER_DTL T WHERE T.ORDER_ID='"+orderId+"' AND T.PART_ID='"+partId+"' AND T.SUPPLIER_ID='"+supplierId+"'\n");
    	return factory.select(null, sql.toString());
    }
	/**
	 * 订单规则查询
	 */
	public BaseResultSet orderTypeRuleSearch(PageManager page, User user, String conditions,String orderType) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND ORDER_TYPE="+orderType+" AND T.STATUS ="+DicConstant.YXBS_01+"\n";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.ORDER_TYPE,\n" );
    	sql.append("       T.FIRST_LETTER,\n" );
    	sql.append("       T.IF_FREE,\n" );
    	sql.append("       T.IF_CHOOSEADDR,\n" );
    	sql.append("       T.IF_FUNDS,\n" );
    	sql.append("       T.IF_OWNPICK\n" );
    	sql.append("  FROM PT_BA_ORDER_TYPE_RULE T\n");
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
	/**
	 * 订单主信息新增前验证是否有未提报订单
     */
    public QuerySet orderInfoSearch(User user,String orderType,String warehouseId) throws Exception {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append("SELECT * FROM PT_BU_SALE_ORDER WHERE ORG_ID||WAREHOUSE_ID ="+user.getOrgId()+warehouseId+" AND ORDER_TYPE="+orderType+" AND ORDER_STATUS IN ("+DicConstant.DDZT_01+","+DicConstant.DDZT_04+")\n");
        qs = factory.select(null,sql.toString());
        return qs;
    }
	/**
	 * 订单主信息新增
     */
    public boolean orderInfoInsert(PtBuSaleOrderVO vo)
            throws Exception {
    	String orderId=SequenceUtil.getCommonSerivalNumber(factory);
		vo.setOrderId(orderId);
        return factory.insert(vo);
    }
    /**
	 * 订单主信息修改
     */
    public boolean orderInfoUpdate(PtBuSaleOrderVO vo)
            throws Exception {
        return factory.update(vo);
    }

    /**
     * 获取订单审核角色
     * @return
     * @throws Exception
     */
    public QuerySet getRole(String title)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT P.ROLE_ID,M.LOCATION \n" );
    	sql.append("  FROM EAP_MENU M, TR_ROLE_MENU_MAP P\n" );
    	sql.append(" WHERE M.TITLE = '" + title + "'\n" );
    	sql.append("   AND P.MENU_NAME = M.NAME");
    	return factory.select(null, sql.toString());
    }

    /**
     * 获取订单号
     * @return
     * @throws Exception
     */
    public QuerySet getOrderNo(String orderId)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT ORDER_NO \n" );
    	sql.append("  FROM PT_BU_SALE_ORDER\n" );
    	sql.append(" WHERE ORDER_ID = '"+orderId+"'\n" );
    	return factory.select(null, sql.toString());
    }
    /**
	 * 订单信息删除
     */
    public boolean orderInfoDelete(PtBuSaleOrderVO vo)
            throws Exception {
        return factory.delete(vo);
    }
    /**
	 * 订单配件信息删除
     */
    public boolean orderPartInfoDelete(String orderId)
            throws Exception {
    	StringBuffer sql = new StringBuffer();
    	sql.append("DELETE FROM PT_BU_SALE_ORDER_DTL WHERE ORDER_ID ="+orderId+"\n");
        return factory.delete(sql.toString(), null);
    }
    
    /**
	 * 订单付款信息删除
     */
    public boolean orderPayInfoDelete(String orderId)
            throws Exception {
    	StringBuffer sql = new StringBuffer();
    	sql.append("DELETE FROM PT_BU_SALE_ORDER_PAY WHERE ORDER_ID ="+orderId+"\n");
        return factory.delete(sql.toString(), null);
    }
    
    /**
	 * 订单明细情况
     */
    public void partOrderDtlDelete(String orderId)
            throws Exception {
    	StringBuffer sql = new StringBuffer();
    	sql.append("DELETE FROM PT_BU_SALE_ORDER_DTL WHERE ORDER_ID ="+orderId+"\n");
        factory.delete(sql.toString(), null);
        StringBuffer sql1 = new StringBuffer();
    	sql1.append("UPDATE PT_BU_SALE_ORDER SET ORDER_AMOUNT=0 WHERE ORDER_ID ="+orderId+"\n");
        factory.delete(sql1.toString(), null);
    }

    /**
	 * 订单提报规则查询
	 */
	public QuerySet orderReportRuleSearch(String orderType) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.ORDER_TYPE,\n" );
    	sql.append("       T.IF_APPLYDATE,\n" );
    	sql.append("       T.IF_APPLYTIMES,\n" );
    	sql.append("       T.SE_STARTDATE,\n" );
    	sql.append("       T.SE_ENDDATE,\n" );
    	sql.append("       T.DC_STARTDATE,\n" );
    	sql.append("       T.DC_ENDDATE,\n" );
    	sql.append("       T.APPLY_TIMES\n" );
    	sql.append("  FROM PT_BA_ORDER_TYPE_RULE T\n");
    	sql.append(" WHERE ORDER_TYPE="+orderType+" AND T.STATUS ="+DicConstant.YXBS_01+"\n");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	/**
	 * 当月订单提报次数查询
	 */
	public QuerySet orderReportTimesSearch(String orderType,User user,int year,int month) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT COUNT(*) COUNTS\n" );
    	sql.append("  FROM PT_BU_SALE_ORDER\n" );
    	sql.append(" WHERE ORDER_TYPE = "+orderType+"\n" );
    	sql.append("   AND ORDER_STATUS NOT IN ("+DicConstant.DDZT_04+","+DicConstant.DDZT_05+")\n" );
    	sql.append("   AND ORG_ID = "+user.getOrgId()+"\n" );
    	sql.append("   AND IF_DELAY_ORDER <> "+DicConstant.SF_01+"\n" );
    	if(month<10){
    		sql.append("   AND TO_CHAR(APPLY_DATE, 'YYYY-MM') LIKE '"+year+"-0"+month+"%'\n");
    	}else{
    		sql.append("   AND TO_CHAR(APPLY_DATE, 'YYYY-MM') LIKE '"+year+"-"+month+"%'\n");
    	}
    	
    	qs = factory.select(null, sql.toString());
    	return qs;
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
	public QuerySet orderFundsAmountSearch(String orderId) throws Exception
    {
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
	 * 订单配件查询
	 */
	public BaseResultSet orderPartSearch(PageManager page, User user, String conditions,String orderId) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND A.PART_ID = B.PART_ID AND A.ORDER_ID = "+orderId+ " ORDER BY A.PART_CODE";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT A.DTL_ID,\n" );
    	sql.append("       A.PART_ID,\n" );
    	sql.append("       A.PART_CODE,\n" );
    	sql.append("       A.PART_NAME,\n" );
    	sql.append("       A.PART_NO,\n" );
    	sql.append("       B.UNIT,\n" );
    	sql.append("       B.MIN_PACK,\n" );
    	sql.append("       B.MIN_UNIT,\n" );
    	sql.append("       A.UNIT_PRICE,\n" );
    	sql.append("       A.IF_SUPPLIER,\n" );
    	sql.append("       A.SUPPLIER_ID,\n" );
    	sql.append("       A.SUPPLIER_CODE,\n" );
    	sql.append("       A.SUPPLIER_NAME,\n" );
    	sql.append("       A.ORDER_COUNT,\n" );
    	sql.append("       NVL((SELECT AVAILABLE_AMOUNT FROM PT_BU_DEALER_STOCK WHERE PART_ID=A.PART_ID AND SUPPLIER_ID=A.SUPPLIER_ID AND ORG_ID='"+user.getOrgId()+"'),0) AVAILABLE_AMOUNT,\n" );
    	sql.append("       A.REMARKS,\n" );
    	sql.append("       A.AMOUNT\n" );
    	sql.append("  FROM PT_BU_SALE_ORDER_DTL A, PT_BA_INFO B\n" );
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("UNIT", "JLDW");
    	bs.setFieldDic("MIN_UNIT", "JLDW");
    	bs.setFieldDic("IF_SUPPLIER", "SF");
    	return bs;
    }
	
	/**
	 * 
	 * @Title: dealerOrderPartSearch
	 * @Description: 经销商配件订单中配件查询:  与上面方法的区别是，如果配件供应商为9XXXXXX，则不显示供应商名称.
	 * @param page
	 * @param user
	 * @param conditions
	 * @param orderId
	 * @return
	 * @throws Exception
	 * @return: BaseResultSet
	 */
	public BaseResultSet dealerOrderPartSearch(PageManager page, User user, String conditions,String orderId) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND A.PART_ID = B.PART_ID AND A.ORDER_ID = "+orderId+ " ORDER BY A.PART_CODE";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT A.DTL_ID,\n" );
    	sql.append("       A.PART_ID,\n" );
    	sql.append("       A.PART_CODE,\n" );
    	sql.append("       A.PART_NAME,\n" );
    	sql.append("       A.PART_NO,\n" );
    	sql.append("       B.UNIT,\n" );
    	sql.append("       B.MIN_PACK,\n" );
    	sql.append("       B.MIN_UNIT,\n" );
    	sql.append("       A.UNIT_PRICE,\n" );
    	sql.append("       A.IF_SUPPLIER,\n" );
    	sql.append("       A.SUPPLIER_ID,\n" );
    	sql.append("       A.SUPPLIER_CODE,\n" );
    	sql.append("       DECODE(A.SUPPLIER_CODE, '9XXXXXX', '', A.SUPPLIER_NAME) SUPPLIER_NAME,\n" );
    	sql.append("       A.ORDER_COUNT,\n" );
    	sql.append("       NVL((SELECT AVAILABLE_AMOUNT FROM PT_BU_DEALER_STOCK WHERE PART_ID=A.PART_ID AND SUPPLIER_ID=A.SUPPLIER_ID AND ORG_ID='"+user.getOrgId()+"'),0) AVAILABLE_AMOUNT,\n" );
    	sql.append("       A.REMARKS,\n" );
    	sql.append("       A.AMOUNT\n" );
    	sql.append("  FROM PT_BU_SALE_ORDER_DTL A, PT_BA_INFO B\n" );
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("UNIT", "JLDW");
    	bs.setFieldDic("MIN_UNIT", "JLDW");
    	bs.setFieldDic("IF_SUPPLIER", "SF");
    	return bs;
    }
	
	
	
    /**
	 * 订单添加配件查询
	 */
	public BaseResultSet orderPartAddSearch(PageManager page, User user, String conditions,String orgType,String orderId,String orderType,String warehouseId,String promId,String directTypeId,String belongAssembly) throws Exception
    {
    	String wheres = conditions;
    	
    	if(DicConstant.DDLX_04.equals(orderType)){
    		// 总成订单
    		wheres += " AND T.IF_DIRECT ="+DicConstant.SF_02+" AND T.IF_ASSEMBLY="+DicConstant.SF_01+"\n"
    		        + " AND T.BELONG_ASSEMBLY IN ('" + DicConstant.PJZCLB_08 + "','" + belongAssembly +"')\n";
    	}
    	else if(DicConstant.DDLX_05.equals(orderType)){
    		// 直发订单
//    		wheres += " AND T.IF_DIRECT ="+DicConstant.SF_01+" AND T.DIRECT_TYPE_ID = "+directTypeId+" AND T.IF_ASSEMBLY="+DicConstant.SF_02+"\n";
    		wheres += " AND T.IF_DIRECT ="+DicConstant.SF_01+" AND T.DIRECT_TYPE_ID = (SELECT DIRECT_TYPE_ID FROM PT_BU_SALE_ORDER WHERE ORDER_ID = "+orderId+") AND T.IF_ASSEMBLY="+DicConstant.SF_02+"\n";
    		wheres += " AND EXISTS (SELECT 1 FROM PT_BA_PART_SUPPLIER_RL S WHERE S.PART_ID= T.PART_ID AND S.PART_IDENTIFY = "+DicConstant.YXBS_01+")\n";
    		//wheres += " AND T.SUPPLIER_ID = "+supplierId+"\n";
    	}
    	else if(DicConstant.DDLX_06.equals(orderType)){
    		// 促销订单
    		wheres += " AND T.PART_ID = T2.PART_ID AND T2.PROM_ID ="+promId+"\n";
    	}else{
    		// 其他订单
    		wheres += " AND T.IF_DIRECT ="+DicConstant.SF_02+" AND T.IF_ASSEMBLY="+DicConstant.SF_02+"\n";
    	}
    	wheres += " AND NOT EXISTS(SELECT 1 FROM PT_BU_SALE_ORDER_DTL S WHERE S.ORDER_ID ="+orderId+" AND S.PART_ID = T.PART_ID AND T.SUPPLIER_ID = S.SUPPLIER_ID)\n";
    	wheres += " ORDER BY T.PART_CODE ASC,T.SUPPLIER_ID ASC\n";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
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
		if(DicConstant.DDLX_06.equals(orderType)){
    		sql.append("T2.PROM_PRICE SALE_PRICE\n");
    	}else{
    		sql.append("T.SALE_PRICE\n");
    	}
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
		sql.append("                  B.AVAILABLE_AMOUNT \n" );
		sql.append("                 ELSE\n" );
		sql.append("                  0 \n" );
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
//		sql.append("                   AND A.IF_BOOK = "+DicConstant.SF_01+"\n" );
//		sql.append("                   AND A.SALE_PRICE > 0) C LEFT JOIN\n" );
		sql.append("                   ) C LEFT JOIN\n" );
		if(DicConstant.ZZLB_09.equals(orgType)){
    		sql.append(" PT_BU_STOCK B ON C.PART_ID = B.PART_ID AND B.SUPPLIER_CODE = '9XXXXXX' AND B.WAREHOUSE_ID = "+warehouseId+"\n" );
    	}else{
			sql.append(" PT_BU_DEALER_STOCK B ON C.PART_ID = B.PART_ID AND B.SUPPLIER_CODE = '9XXXXXX' AND B.ORG_ID = "+warehouseId+"\n" );
    	}
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
		sql.append("                  E.AVAILABLE_AMOUNT \n" );
		sql.append("                 ELSE\n" );
		sql.append("                  0 \n" );
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
//		sql.append("                   AND A.IF_BOOK = "+DicConstant.SF_01+"\n" );
//		sql.append("                   AND A.SALE_PRICE > 0) D LEFT JOIN\n" );
		sql.append("                   ) D LEFT JOIN\n" );
		if(DicConstant.ZZLB_09.equals(orgType)){
    		sql.append(" PT_BU_STOCK E ON D.PART_ID = E.PART_ID AND D.SUPPLIER_ID = E.SUPPLIER_ID AND E.WAREHOUSE_ID = "+warehouseId+"\n" );
    	}else{
			sql.append(" PT_BU_DEALER_STOCK E ON D.PART_ID = E.PART_ID AND D.SUPPLIER_ID = E.SUPPLIER_ID  AND E.ORG_ID = "+warehouseId+"\n" );
    	}
		sql.append(" ) T\n" );
		if(DicConstant.DDLX_06.equals(orderType)){
    		sql.append(",PT_BU_PROMOTION_PART T2\n");
    	}
    	bs = factory.select(sql.toString(), page);
		bs.setFieldDic("UNIT", "JLDW");
		bs.setFieldDic("MIN_UNIT", "JLDW");
		bs.setFieldDic("IF_SUPLY", "SF");

		
    	return bs;
    }
	
	/**
	 * 订单配件信息新增
     */
    public boolean orderPartInfoInsert(PtBuSaleOrderDtlVO vo)
            throws Exception {
        return factory.insert(vo);
    }

    /**
     * 取的计划价
     */
    public QuerySet getPlanPrice(String partId)throws Exception {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append(" SELECT NVL(PLAN_PRICE,0) PLAN_PRICE FROM PT_BA_INFO WHERE PART_ID = '"+partId+"'\n" );
    	qs = factory.select(null,sql.toString());
    	return qs;
    }

    /**
     * 查询订单总金额
     */
    public QuerySet orderAmountSearch(String orderId)throws Exception {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append(" SELECT NVL(SUM(ORDER_COUNT * UNIT_PRICE), 0) AMOUNT\n" );
    	sql.append("          FROM PT_BU_SALE_ORDER_DTL\n" );
    	sql.append("         WHERE ORDER_ID = "+orderId+"\n" );
    	qs = factory.select(null,sql.toString());
    	return qs;
    }
    /**
	 * 更新订单总金额
     */
    public boolean orderAmountUpdate(PtBuSaleOrderVO vo)
            throws Exception {
        return factory.update(vo);
    }
    /**
     * 删除配件
     */
    public boolean deleteOrderPart(PtBuSaleOrderDtlVO vo) throws Exception {
        return factory.delete(vo);
    }
    /**
     * 修改配件订单明细
     */
    public void updateOrderPart(String dtlId,String orderId,String orderCount,String remarks,User user) throws Exception {
    	StringBuffer sql = new StringBuffer();
    	sql.append("UPDATE PT_BU_SALE_ORDER_DTL SET REMARKS='" + remarks + "',ORDER_COUNT="+orderCount+",AMOUNT= UNIT_PRICE*"+orderCount+",UPDATE_USER='"+user.getAccount()+"',UPDATE_TIME=SYSDATE WHERE DTL_ID ="+dtlId+" AND ORDER_ID ="+orderId+"\n");
       factory.update(sql.toString(),null);
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
	 * 订单资金使用删除
	 */
	public boolean orderFundsDelete(String orderId) throws Exception
    {
    	StringBuffer sql= new StringBuffer();
    	sql.append("DELETE FROM PT_BU_SALE_ORDER_PAY WHERE ORDER_ID='"+orderId+"'\n" );
    	return factory.delete(sql.toString(), null);
    }
	/**
	 * 订单免运费次数查询
	 */
	public BaseResultSet transFreeTimesSearch(PageManager page, User user, String conditions,String orderType) throws Exception
    {
		Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        BaseResultSet bs = null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT CASE\n" );
		sql.append("         WHEN A.FREE_TIMES - B.COUNTS > 0 THEN\n" );
		sql.append("          "+DicConstant.SF_01+"\n" );
		sql.append("         ELSE\n" );
		sql.append("          "+DicConstant.SF_02+"\n" );
		sql.append("       END IF_FREE\n" );
		sql.append("  FROM (SELECT FREE_TIMES\n" );
		sql.append("          FROM PT_BA_ORDER_TYPE_RULE\n" );
		sql.append("         WHERE ORDER_TYPE = "+orderType+"\n" );
		sql.append("           AND IF_FREE = "+DicConstant.SF_01+") A,\n" );
		sql.append("       (SELECT COUNT(ORDER_ID) COUNTS\n" );
		sql.append("          FROM PT_BU_SALE_ORDER\n" );
		sql.append("         WHERE ORDER_TYPE <> "+DicConstant.DDLX_06+"\n" );
		sql.append("           AND ORDER_TYPE = "+orderType+"\n" );
		sql.append("           AND ORDER_STATUS <> "+DicConstant.DDZT_05+"\n" );
		sql.append("           AND TRANS_TYPE = "+DicConstant.FYFS_01+"\n" );
		sql.append("           AND ORG_ID = "+user.getOrgId()+"\n" );
		sql.append("           AND IF_DELAY_ORDER = "+DicConstant.SF_02+"\n" );
		sql.append("           AND IF_TRANS ="+DicConstant.SF_01+"\n" );
		if(month<10){
			sql.append("           AND TO_CHAR(APPLY_DATE, 'YYYY-MM') = '"+String.valueOf(year)+"-0"+String.valueOf(month)+"') B");
		}else{
			sql.append("           AND TO_CHAR(APPLY_DATE, 'YYYY-MM') = '"+String.valueOf(year)+"-"+String.valueOf(month)+"') B");
		}
		bs = factory.select(sql.toString(), page);
    	return bs;
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
	 * 更新资金账户
     */
    public boolean accountUpdate(String accountId,String payAmount)
            throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("UPDATE PT_BU_ACCOUNT SET OCCUPY_AMOUNT = OCCUPY_AMOUNT+"+payAmount+" ,AVAILABLE_AMOUNT = AVAILABLE_AMOUNT-"+payAmount+" WHERE ACCOUNT_ID =+"+accountId+"\n");
        return factory.update(sql.toString(), null);
    }
    
    /**
	 * 订单资金占用
     */
    public boolean orderFundsOccupy(String orderId,String accountId,String accountType,String payAmount,User user)
            throws Exception {
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
     * 配件订单明细导入临时表查询
     */
    public QuerySet searchPartDtlTmp(User user)throws Exception {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append("\n");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
    
	/**
	 * 订单主信息查询
	 */
   public QuerySet partOrderInfoSearch(String orderId)
           throws Exception {
   	   String sql = "SELECT * FROM PT_BU_SALE_ORDER WHERE ORDER_ID="+orderId+"\n";
       return factory.select(null,sql);
   }
   /**
    * 查询是否有不存在的配件
    */
   public QuerySet partBaInfoSearch(User user)
           throws Exception {
	   StringBuffer sql = new StringBuffer();
	   sql.append(" SELECT A.ROW_NO,A.PART_CODE FROM PT_BU_SALE_ORDER_DTL_TMP A WHERE A.USER_ACCOUNT='"+user.getAccount()+"' \n");
	   sql.append(" AND NOT EXISTS(SELECT 1 FROM PT_BA_INFO B WHERE A.PART_CODE=B.PART_CODE AND B.PART_STATUS <>"+DicConstant.PJZT_02+"\n");
	   sql.append(" AND B.PART_TYPE <> "+DicConstant.PJLB_03+" " +
//	   		"AND B.IF_BOOK = "+DicConstant.SF_01+"" +
	   				")\n");
	   return factory.select(null,sql.toString());
   }
   /**
    * 查询是否有不符合要求的配件
    */
   public QuerySet partInfoSearch(User user,String orderType,String promId,String directTypeId)
           throws Exception {
	   StringBuffer sql = new StringBuffer();
	   sql.append(" SELECT A.ROW_NO,A.PART_CODE FROM PT_BU_SALE_ORDER_DTL_TMP A WHERE A.USER_ACCOUNT='"+user.getAccount()+"' \n");
	   sql.append(" AND NOT EXISTS(SELECT 1 FROM PT_BA_INFO B WHERE A.PART_CODE=B.PART_CODE AND B.PART_STATUS <>"+DicConstant.PJZT_02+"\n");
	   sql.append(" AND B.PART_TYPE <> "+DicConstant.PJLB_03+" " +
//	   		"AND B.IF_BOOK = "+DicConstant.SF_01+"" +
	   				"\n");
	   if(DicConstant.DDLX_01.equals(orderType)||DicConstant.DDLX_02.equals(orderType)||DicConstant.DDLX_03.equals(orderType)||DicConstant.DDLX_07.equals(orderType)){
		   sql.append(" AND B.IF_DIRECT ="+DicConstant.SF_02+" AND B.IF_ASSEMBLY="+DicConstant.SF_02+"\n");
	   }
	   if(DicConstant.DDLX_04.equals(orderType)){
		   sql.append(" AND B.IF_DIRECT ="+DicConstant.SF_02+" AND B.IF_ASSEMBLY="+DicConstant.SF_01+"\n");
	   }
	   if(DicConstant.DDLX_05.equals(orderType)){
		   sql.append(" AND B.IF_DIRECT ="+DicConstant.SF_01+" AND B.DIRECT_TYPE_ID = "+directTypeId+" AND B.IF_ASSEMBLY="+DicConstant.SF_02+"\n");
	   }
	   sql.append(")\n");
	   if(DicConstant.DDLX_06.equals(orderType)){
		   sql.append(" AND NOT EXISTS(SELECT 1 FROM PT_BU_PROMOTION_PART C WHERE A.PART_CODE = C.PART_CODE AND C.PROM_ID = "+promId+")\n");
	   }
	   return factory.select(null,sql.toString());
   }
   /**
    * 查询供应商是否存在
    */
   public QuerySet supplierBaInfoSearch(User user)throws Exception {
	   StringBuffer sql = new StringBuffer();
	   sql.append(" SELECT A.ROW_NO,A.SUPPLIER_CODE FROM PT_BU_SALE_ORDER_DTL_TMP A WHERE A.USER_ACCOUNT='"+user.getAccount()+"' AND A.SUPPLIER_CODE IS NOT NULL\n");
	   sql.append(" AND NOT EXISTS(SELECT 1 FROM PT_BA_SUPPLIER B WHERE A.SUPPLIER_CODE=B.SUPPLIER_CODE AND B.PART_IDENTIFY = "+DicConstant.YXBS_01+")\n");
	   return factory.select(null,sql.toString());
   }
   /**
    * 指定供应商查询供货关系是否存在
    */
   public QuerySet partSupplierBaInfoSearch(User user)throws Exception {
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT A.ROW_NO,A.PART_CODE,A.SUPPLIER_CODE FROM PT_BU_SALE_ORDER_DTL_TMP A,PT_BA_INFO B,PT_BA_SUPPLIER C\n" );
	   sql.append("WHERE A.PART_CODE = B.PART_CODE AND A.SUPPLIER_CODE = C.SUPPLIER_CODE AND B.IF_SUPLY ="+DicConstant.SF_01+" AND C.PART_IDENTIFY = "+DicConstant.YXBS_01+" AND A.SUPPLIER_CODE IS NOT NULL AND A.USER_ACCOUNT='"+user.getAccount()+"'\n" );
	   sql.append("AND NOT EXISTS (SELECT 1 FROM PT_BA_PART_SUPPLIER_RL D WHERE B.PART_ID= D.PART_ID AND D.SUPPLIER_ID = C.SUPPLIER_ID AND AND D.PART_IDENTIFY = "+DicConstant.YXBS_01+")\n");
	   return factory.select(null,sql.toString());
   }
   /**
    * 查询订购数量
    */
   public QuerySet partOrderCountSearch(User user)throws Exception {
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT A.ROW_NO,A.PART_CODE,A.COUNT,B.MIN_PACK FROM PT_BU_SALE_ORDER_DTL_TMP A,PT_BA_INFO B WHERE A.PART_CODE = B.PART_CODE AND A.USER_ACCOUNT='"+user.getAccount()+"'\n" );
	   return factory.select(null,sql.toString());
   }
   
   /**
    * 查询配件是否已存在
    */
   public QuerySet partOrderDetailSearch(User user,String orderId)throws Exception {
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT A.ROW_NO,A.PART_CODE FROM PT_BU_SALE_ORDER_DTL_TMP A WHERE A.USER_ACCOUNT='"+user.getAccount()+"'\n" );
	   sql.append(" AND EXISTS(SELECT 1 FROM PT_BU_SALE_ORDER_DTL B WHERE A.PART_CODE = B.PART_CODE AND B.ORDER_ID = "+orderId+")");
	   return factory.select(null,sql.toString());
   }
   
   /**
	 * 导入成功查询
	 */
	public BaseResultSet searchImportPart(PageManager page, User user, String conditions) throws Exception{
		String wheres = conditions;
		wheres += " AND A.PART_CODE = B.PART_CODE AND A.USER_ACCOUNT ='"+user.getAccount()+"'\n";
		page.setFilter(wheres);
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT A.PART_CODE,\n" );
        sql.append("       B.PART_NAME,\n" );
        sql.append("       B.IF_SUPLY,\n" );
        sql.append("       A.SUPPLIER_CODE,\n" );
        sql.append("       (SELECT C.SUPPLIER_NAME\n" );
        sql.append("          FROM PT_BA_SUPPLIER C\n" );
        sql.append("         WHERE A.SUPPLIER_CODE = C.SUPPLIER_CODE AND C.PART_IDENTIFY = "+DicConstant.YXBS_01+") SUPPLIER_NAME,\n" );
        sql.append("       A.REMARKS,\n" );
        sql.append("       A.COUNT\n" );
        sql.append("  FROM PT_BU_SALE_ORDER_DTL_TMP A, PT_BA_INFO B\n" );
		bs = factory.select(sql.toString(), page);
		return bs;
   }
    /**
     * 查询订单总金额
     *
     * @param orderId 订单ID
     * @return 结果集
     * @throws Exception
     */
    public QuerySet getserverOrderId(String orderId)throws Exception {

        QuerySet qs = null;
        StringBuffer sql= new StringBuffer();
        sql.append(" SELECT NVL(DIR_SOURCE_ORDER_ID,0) DIR_SOURCE_ORDER_ID FROM PT_BU_SALE_ORDER WHERE ORDER_ID = "+orderId+"\n" );
        qs = factory.select(null,sql.toString());
        return qs;
    }
	/**
	 * 插入配件订单明细
	 */
	public void insertPartOrderDetail(User user,String orderId,String promId,String tmpNo,String flag)throws Exception {
		StringBuffer sql= new StringBuffer();
		sql.append("MERGE INTO PT_BU_SALE_ORDER_DTL T\n" );
		sql.append("    USING (" +
						" SELECT PART_ID, PART_CODE, PART_NAME, IF_SUPLY, SUPPLIER_ID, SUPPLIER_CODE, SUPPLIER_NAME, SUM(COUNT) COUNT, SALE_PRICE, SUM(COUNT * SALE_PRICE) AMOUNT,REMARKS FROM ("+
				        " SELECT \n" );
		sql.append("       B.PART_ID,\n" );
		sql.append("       B.PART_CODE,\n" );
		sql.append("       B.PART_NAME,\n" );
		sql.append("       B.IF_SUPLY,\n" );
		sql.append("       CASE\n" );
		sql.append("         WHEN B.IF_SUPLY = "+DicConstant.SF_02+" THEN\n" );
		sql.append("          (SELECT SUPPLIER_ID\n" );
		sql.append("             FROM PT_BA_SUPPLIER\n" );
		sql.append("            WHERE SUPPLIER_CODE = '9XXXXXX' AND PART_IDENTIFY = "+DicConstant.YXBS_01+")\n" );
		sql.append("         ELSE\n" );
		sql.append("          (SELECT SUPPLIER_ID\n" );
		sql.append("             FROM PT_BA_SUPPLIER\n" );
		sql.append("            WHERE SUPPLIER_CODE = A.SUPPLIER_CODE AND PART_IDENTIFY = "+DicConstant.YXBS_01+")\n" );
		sql.append("       END SUPPLIER_ID,\n" );
		sql.append("       CASE\n" );
		sql.append("         WHEN B.IF_SUPLY = "+DicConstant.SF_02+" THEN\n" );
		sql.append("          '9XXXXXX'\n" );
		sql.append("         ELSE\n" );
		sql.append("          A.SUPPLIER_CODE\n" );
		sql.append("       END SUPPLIER_CODE,\n" );
		sql.append("       CASE\n" );
		sql.append("         WHEN B.IF_SUPLY = "+DicConstant.SF_02+" THEN\n" );
		sql.append("          (SELECT SUPPLIER_NAME\n" );
		sql.append("             FROM PT_BA_SUPPLIER\n" );
		sql.append("            WHERE SUPPLIER_CODE = '9XXXXXX' AND PART_IDENTIFY = "+DicConstant.YXBS_01+")\n" );
		sql.append("         ELSE\n" );
		sql.append("          (SELECT SUPPLIER_NAME\n" );
		sql.append("             FROM PT_BA_SUPPLIER\n" );
		sql.append("            WHERE SUPPLIER_CODE = A.SUPPLIER_CODE AND PART_IDENTIFY = "+DicConstant.YXBS_01+")\n" );
		sql.append("       END SUPPLIER_NAME,\n" );
		sql.append("       A.COUNT,\n" );
		if(!"".equals(promId)&&promId!=null){
			sql.append("       (SELECT PROM_PRICE FROM PT_BU_PROMOTION_PART WHERE PART_CODE = A.PART_CODE) SALE_PRICE, \n" );
			sql.append("       A.COUNT * (SELECT PROM_PRICE FROM PT_BU_PROMOTION_PART WHERE PART_CODE = A.PART_CODE) AMOUNT\n" );
		}else{
			if("1".equals(flag)){
				// 直营订单
				sql.append("       B.RETAIL_PRICE SALE_PRICE,\n" );
				sql.append("       A.COUNT * B.RETAIL_PRICE AMOUNT\n" );
			} else if ("2".equals(flag)) {
				// 技术升级订单
	 			sql.append("       B.PLAN_PRICE SALE_PRICE,\n" );
				sql.append("       A.COUNT * B.PLAN_PRICE AMOUNT\n" );
			} else{
				// 正常订单
	 			sql.append("       B.SALE_PRICE,\n" );
				sql.append("       A.COUNT * B.SALE_PRICE AMOUNT\n" );
			}
		}
//		sql.append("       '"+user.getAccount()+"',\n" );
		sql.append("       ,A.REMARKS\n" );
		sql.append(" FROM PT_BU_SALE_ORDER_DTL_TMP A, PT_BA_INFO B");
		sql.append(" WHERE A.PART_CODE = B.PART_CODE\n" );
		sql.append("   AND A.USER_ACCOUNT='"+user.getAccount()+"' "+tmpNo+"" +
				"  ) T  GROUP BY PART_ID, PART_CODE, PART_NAME, IF_SUPLY, SUPPLIER_ID, SUPPLIER_CODE, SUPPLIER_NAME, SALE_PRICE,REMARKS "+
				") T1\n" );
		sql.append("    ON (T.PART_ID = T1.PART_ID AND T.SUPPLIER_ID=T1.SUPPLIER_ID AND T.ORDER_ID = '"+orderId+"')\n" );
		sql.append("    WHEN NOT MATCHED THEN\n" );
		sql.append("INSERT \n" );
		sql.append("  (DTL_ID,\n" );
		sql.append("   ORDER_ID,\n" );
		sql.append("   PART_ID,\n" );
		sql.append("   PART_CODE,\n" );
		sql.append("   PART_NAME,\n" );
		sql.append("   IF_SUPPLIER,\n" );
		sql.append("   SUPPLIER_ID,\n" );
		sql.append("   SUPPLIER_CODE,\n" );
		sql.append("   SUPPLIER_NAME,\n" );
		sql.append("   ORDER_COUNT,\n" );
		sql.append("   UNIT_PRICE,\n" );
		sql.append("   AMOUNT,\n" );
		sql.append("   CREATE_USER,\n" );
		sql.append("   CREATE_TIME,REMARKS) VALUES\n" );
		sql.append("( F_GETID(),\n" );
		sql.append("       '"+orderId+"',\n" );
		sql.append("       T1.PART_ID,\n" );
		sql.append("       T1.PART_CODE,\n" );
		sql.append("       T1.PART_NAME,\n" );
		sql.append("       T1.IF_SUPLY,\n" );
		sql.append("       T1.SUPPLIER_ID,\n" );
		sql.append("       T1.SUPPLIER_CODE,\n" );
		sql.append("       T1.SUPPLIER_NAME,\n" );
		sql.append("       T1.COUNT,\n" );
		sql.append("       T1.SALE_PRICE, \n" );
		sql.append("       T1.AMOUNT,\n" );
//		sql.append("       T1.CREATE_USER,\n" );
//		sql.append("       T1.CREATE_TIME)\n" );
		sql.append("       '"+user.getAccount()+"',\n" );
		sql.append("       SYSDATE,T1.REMARKS)\n" );
		sql.append("    WHEN MATCHED THEN\n" );
		sql.append("        UPDATE SET T.ORDER_COUNT=T.ORDER_COUNT+T1.COUNT,T.AMOUNT=T.AMOUNT+T1.AMOUNT,T.REMARKS= T1.REMARKS\n" );
    	factory.update(sql.toString(),null);
    }
	
	/**
	 * 汇总渠道延期订单
	 */
	public void totailDelayPartOrder(User user,String orderId,String orderType,String promId,String directTypeId)throws Exception {
		StringBuffer sql2= new StringBuffer();
		sql2.append("DELETE FROM PT_BU_SALE_ORDER_DTL WHERE ORDER_ID = "+orderId+"\n" );
		factory.delete(sql2.toString(),null);
		StringBuffer sql= new StringBuffer();
		sql.append("INSERT INTO PT_BU_SALE_ORDER_DTL\n" );
		sql.append("  (DTL_ID,\n" );
		sql.append("   ORDER_ID,\n" );
		sql.append("   PART_ID,\n" );
		sql.append("   PART_CODE,\n" );
		sql.append("   PART_NAME,\n" );
		sql.append("   IF_SUPPLIER,\n" );
		sql.append("   SUPPLIER_ID,\n" );
		sql.append("   SUPPLIER_CODE,\n" );
		sql.append("   SUPPLIER_NAME,\n" );
		sql.append("   ORDER_COUNT,\n" );
		sql.append("   UNIT_PRICE,\n" );
		sql.append("   AMOUNT,\n" );
		sql.append("   CREATE_USER,\n" );
		sql.append("   CREATE_TIME)\n" );
		sql.append("SELECT F_GETID(),\n" );
		sql.append("       "+orderId+",\n" );
		sql.append("       T.PART_ID,\n" );
		sql.append("       T.PART_CODE,\n" );
		sql.append("       T.PART_NAME,\n" );
		sql.append("       T.IF_SUPPLIER,\n" );
		sql.append("       T.SUPPLIER_ID,\n" );
		sql.append("       T.SUPPLIER_CODE,\n" );
		sql.append("       T.SUPPLIER_NAME,\n" );
		sql.append("       T.ORDER_COUNT,\n" );
		sql.append("       T.UNIT_PRICE,\n" );
		sql.append("       T.AMOUNT,\n" );
		sql.append("       '"+user.getAccount()+"',\n" );
		sql.append("       SYSDATE\n" );
		sql.append("FROM (SELECT B.PART_ID,\n" );
		sql.append("       B.PART_CODE,\n" );
		sql.append("       B.PART_NAME,\n" );
		sql.append("       B.IF_SUPPLIER,\n" );
		sql.append("       B.SUPPLIER_ID,\n" );
		sql.append("       B.SUPPLIER_CODE,\n" );
		sql.append("       B.SUPPLIER_NAME,\n" );
		sql.append("       B.UNIT_PRICE,\n" );
		sql.append("       SUM(NVL(B.ORDER_COUNT, 0)) ORDER_COUNT,\n" );
		sql.append("       SUM(NVL(B.ORDER_COUNT, 0)) * B.UNIT_PRICE AMOUNT\n" );
		sql.append("  FROM PT_BU_SALE_ORDER A, PT_BU_SALE_ORDER_DTL B\n" );
		sql.append(" WHERE A.ORDER_ID = B.ORDER_ID\n" );
		sql.append("   AND A.ORDER_STATUS = "+DicConstant.DDZT_02+"\n" );
		sql.append("   AND A.IF_DELAY_ORDER = "+DicConstant.SF_01+"\n" );
		sql.append("   AND A.WAREHOUSE_ID = "+user.getOrgId()+"\n" );
		sql.append("   AND (A.IF_TOTAIL IS NULL OR A.IF_TOTAIL ="+DicConstant.SF_02+")\n" );
		sql.append("   AND A.ORDER_TYPE = "+orderType+"\n" );
		if(DicConstant.DDLX_05.equals(orderType)){
			sql.append("   AND A.DIRECT_TYPE_ID = "+directTypeId+"\n" );
		}
		if(DicConstant.DDLX_06.equals(orderType)){
			sql.append("   AND A.PROM_ID = "+promId+"\n" );
		}
		sql.append(" GROUP BY A.PROM_ID,\n" );
		sql.append("          A.DIRECT_TYPE_ID,\n" );
		sql.append("          B.PART_ID,\n" );
		sql.append("          B.PART_CODE,\n" );
		sql.append("          B.PART_NAME,\n" );
		sql.append("          B.IF_SUPPLIER,\n" );
		sql.append("          B.SUPPLIER_ID,\n" );
		sql.append("          B.SUPPLIER_CODE,\n" );
		sql.append("          B.SUPPLIER_NAME,\n" );
		sql.append("          B.UNIT_PRICE)T \n");
    	factory.update(sql.toString(),null);
    	
    	StringBuffer sql1= new StringBuffer();
		sql1.append("UPDATE PT_BU_SALE_ORDER SET IF_TOTAIL = "+DicConstant.SF_01+"\n" );
		sql1.append(" WHERE ORDER_STATUS = "+DicConstant.DDZT_02+"\n" );
		sql1.append("   AND IF_DELAY_ORDER = "+DicConstant.SF_01+"\n" );
		sql1.append("   AND WAREHOUSE_ID = "+user.getOrgId()+"\n" );
		sql1.append("   AND (IF_TOTAIL IS NULL OR IF_TOTAIL ="+DicConstant.SF_02+")\n" );
		sql1.append("   AND ORDER_TYPE = "+orderType+"\n" );
		if(DicConstant.DDLX_05.equals(orderType)){
			sql1.append("   AND DIRECT_TYPE_ID = "+directTypeId+"\n" );
		}
		if(DicConstant.DDLX_06.equals(orderType)){
			sql1.append("   AND PROM_ID = "+promId+"\n" );
		}
    	factory.update(sql1.toString(),null);
    }
	
	/**
     * 订单明细查询(导出)
     *
     * @pConditions 查询条件
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet download(String orderId,User user) throws Exception {

    	String wheres = "WHERE  A.PART_ID = B.PART_ID AND A.ORDER_ID = "+orderId+ " ORDER BY A.PART_CODE";
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT A.DTL_ID,\n" );
    	sql.append("       (SELECT ORDER_NO FROM PT_BU_SALE_ORDER WHERE ORDER_ID=A.ORDER_ID) ORDER_NO,\n" );
    	sql.append("       A.PART_ID,\n" );
    	sql.append("       A.PART_CODE,\n" );
    	sql.append("       A.PART_NAME,\n" );
    	sql.append("       A.PART_NO,\n" );
    	sql.append("       (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = B.UNIT) UNIT,\n" );
    	sql.append("       B.MIN_PACK||'/'||(SELECT DIC_VALUE FROM DIC_TREE WHERE ID = B.MIN_UNIT) MINI,\n" );
    	sql.append("       A.UNIT_PRICE,\n" );
    	sql.append("       A.IF_SUPPLIER,\n" );
    	sql.append("       A.SUPPLIER_ID,\n" );
    	sql.append("       A.SUPPLIER_CODE,\n" );
    	sql.append("       A.SUPPLIER_NAME,\n" );
    	sql.append("       A.ORDER_COUNT,\n" );
    	sql.append("       NVL((SELECT AVAILABLE_AMOUNT FROM PT_BU_DEALER_STOCK WHERE PART_ID=A.PART_ID AND SUPPLIER_ID=A.SUPPLIER_ID AND ORG_ID='"+user.getOrgId()+"'),0) AVAILABLE_AMOUNT,\n" );
    	sql.append("       A.REMARKS,\n" );
    	sql.append("       A.AMOUNT\n" );
    	sql.append("  FROM PT_BU_SALE_ORDER_DTL A, PT_BA_INFO B\n" );
//    	return factory.select(sql.toString(), page);
//    	bs.setFieldDic("UNIT", "JLDW");
//    	bs.setFieldDic("MIN_UNIT", "JLDW");
//    	bs.setFieldDic("IF_SUPPLIER", "SF");
//    	bs.setFieldUserID("USER_ACCOUNT");
//    	return bs;

        //执行方法，不需要传递conn参数
        return factory.select(null, sql.toString()+wheres);
    }
	/**
     * 获取订购单位
     *
     * @pConditions 查询条件
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet getOrgName(String orderId) throws Exception {
    	
    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT ORG_NAME FROM PT_BU_SALE_ORDER WHERE ORDER_ID='"+orderId+"'");

    	//执行方法，不需要传递conn参数
        return factory.select(null, sql.toString());
    }
}
