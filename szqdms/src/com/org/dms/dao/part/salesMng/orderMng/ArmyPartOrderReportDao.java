package com.org.dms.dao.part.salesMng.orderMng;

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

public class ArmyPartOrderReportDao extends BaseDAO{
	public static final ArmyPartOrderReportDao getInstance(ActionContext atx) {
		ArmyPartOrderReportDao dao = new ArmyPartOrderReportDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	
	public BaseResultSet partOrderSearch(PageManager page, User user, String conditions) throws Exception
    {
		String wheres = conditions;
    	wheres +="AND T.ORDER_TYPE = "+DicConstant.DDLX_08+"\n" +
    					"   AND T.ORDER_STATUS IN ("+DicConstant.DDZT_01+", "+DicConstant.DDZT_04+")\n" + 
    					"   AND T.COMPANY_ID = "+user.getCompanyId()+""+
    					"   AND T.OEM_COMPANY_ID = "+user.getOemCompanyId()+""+
    					"   AND T.ORG_ID = "+user.getOrgId()+"\n" + 
    					" ORDER BY T.CREATE_TIME DESC";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.ORDER_ID,\n" );
    	sql.append("       T.ORDER_NO,\n" );
    	sql.append("       T.ORDER_TYPE,\n" );
    	sql.append("       T.WAREHOUSE_ID,\n" );
    	sql.append("       T.WAREHOUSE_CODE,\n" );
    	sql.append("       T.WAREHOUSE_NAME,\n" );
    	sql.append("       T.EXPECT_DATE,\n" );
    	sql.append("       T.ORDER_AMOUNT,\n" );
    	sql.append("       T.TRANS_TYPE,\n" );
    	sql.append("       T.DELIVERY_ADDR,\n" );
    	sql.append("       T.LINK_MAN,\n" );
    	sql.append("       T.PHONE,\n" );
    	sql.append("       T.ZIP_CODE,\n" );
    	sql.append("       T.REMARKS,\n" );
    	sql.append("       T.ADDR_TYPE,\n" );
    	sql.append("       T.PROVINCE_NAME,\n" );
    	sql.append("       T.CITY_NAME,\n" );
    	sql.append("       T.COUNTRY_NAME,\n" );
    	sql.append("       T.PROVINCE_CODE,\n" );
    	sql.append("       T.CITY_CODE,\n" );
    	sql.append("       T.COUNTRY_CODE,\n" );
    	sql.append("       T.CUSTORM_NAME,\n" );
    	sql.append("       T.ORDER_STATUS,\n" );
    	sql.append("       T.CREATE_USER,\n" );
    	sql.append("       T.APPLY_DATE,\n" );
    	sql.append("       T.SUPPLIER_ID,\n" );
    	sql.append("       T.SUPPLIER_CODE,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("       T.CREATE_TIME,\n" );
    	sql.append("       T.ARMY_CONT_NO,\n" );
    	sql.append("       T.IF_TRANS\n" );
    	sql.append("  FROM PT_BU_SALE_ORDER T");
    	bs = factory.select(sql.toString(), page);
		bs.setFieldDic("ORDER_TYPE", "DDLX");
		bs.setFieldDic("ORDER_STATUS", "DDZT");
		bs.setFieldDic("TRANS_TYPE", "FYFS");
		bs.setFieldDic("IF_TRANS", "SF");
		bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
		bs.setFieldDateFormat("EXPECT_DATE", "yyyy-MM-dd");
		bs.setFieldUserID("CREATE_USER");
    	return bs;
    }
	public BaseResultSet accountSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND T.ACCOUNT_TYPE = "+DicConstant.ZJZHLX_04+" AND ORG_ID = "+user.getOrgId()+"AND T.STATUS ="+DicConstant.YXBS_01+"\n";
    	page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT 2 TYPE, NVL(SUM(AVAILABLE_AMOUNT), 0) AVAILABLE_AMOUNT\n" );
    	sql.append("  FROM PT_BU_ACCOUNT T");
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
	
	/**
	 * 订单主信息新增前验证是否有未提报订单
     */
    public QuerySet orderInfoSaveSearch(User user) throws Exception {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append("SELECT * FROM PT_BU_SALE_ORDER WHERE ORG_ID ="+user.getOrgId()+" AND ORDER_TYPE="+DicConstant.DDLX_08+" AND ORDER_STATUS ="+DicConstant.DDZT_01+"\n");
        qs = factory.select(null,sql.toString());
        return qs;
    }
	public BaseResultSet getWarehouse(PageManager page, User user, String conditions) throws Exception
    {
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.WAREHOUSE_ID, T.WAREHOUSE_CODE, T.WAREHOUSE_NAME\n" );
    	sql.append("  FROM PT_BA_WAREHOUSE T\n" );
    	sql.append(" WHERE T.WAREHOUSE_TYPE = '100102'");
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
	
	public boolean orderInfoInsert(PtBuSaleOrderVO vo)
            throws Exception {
    	String orderId=SequenceUtil.getCommonSerivalNumber(factory);
		vo.setOrderId(orderId);
        return factory.insert(vo);
    }
	public boolean orderInfoUpdate(PtBuSaleOrderVO vo)
	            throws Exception {
        return factory.update(vo);
    }
	/**
	 * 
	 * @date()2014年9月5日上午10:22:31
	 * @author Administrator
	 * @to_do:订单配件查询
	 * @param page
	 * @param user
	 * @param conditions
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet orderPartSearch(PageManager page, User user, String conditions,String orderId) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND A.PART_ID = B.PART_ID AND A.ORDER_ID = "+orderId;
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
    	sql.append("       A.REMARKS,\n" );
    	sql.append("       A.AMOUNT\n" );
    	sql.append("  FROM PT_BU_SALE_ORDER_DTL A, PT_BA_INFO B\n" );
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("UNIT", "JLDW");
    	bs.setFieldDic("MIN_UNIT", "JLDW");
    	bs.setFieldDic("IF_SUPPLIER", "SF");
    	return bs;
    }
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
	public BaseResultSet searchPart(PageManager pageManager, User pUser, String pConditions, String pOrderId,String warehouseId) throws Exception {

        String wheres = pConditions + " AND C.SALE_PRICE >0 AND C.PART_TYPE <> "+DicConstant.PJLB_02
//        		+" AND C.IF_BOOK = "+DicConstant.SF_01
                      + " AND C.PART_STATUS<>'" + DicConstant.PJZT_02+ "'"
                      + " AND C.PART_ID NOT IN (SELECT PART_ID FROM PT_BU_SALE_ORDER_DTL WHERE ORDER_ID='" + pOrderId + "')) A"
                      + "  LEFT JOIN PT_BU_STOCK B\n"
                      + "    ON A.PART_ID = B.PART_ID\n"
                      + "   AND B.WAREHOUSE_ID = '" + warehouseId + "'\n"
                      + " ORDER BY B.PART_CODE";
        pageManager.setFilter(wheres);
        //定义返回结果集
        BaseResultSet baseResultSet = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT A.PART_ID,\n" );
        sql.append("       A.PART_CODE,\n" );
        sql.append("       A.PART_NAME,\n" );
        sql.append("       A.UNIT,\n" );
        sql.append("       A.MIN_PACK,\n" );
        sql.append("       A.MIN_UNIT,\n" );
        sql.append("       A.IF_SUPLY,\n" );
        sql.append("       A.SALE_PRICE,\n" );
        sql.append("       CASE WHEN NVL(B.AVAILABLE_AMOUNT, 0) > 0 THEN '有' ELSE '无' END STOCK,\n" );
        sql.append("       B.SUPPLIER_ID,\n" );
        sql.append("       B.SUPPLIER_CODE,\n" );
        sql.append("       B.SUPPLIER_NAME\n" );
        sql.append("  FROM (SELECT C.PART_ID,\n" );
        sql.append("               C.PART_CODE,\n" );
        sql.append("               C.PART_NAME,\n" );
        sql.append("               C.UNIT,\n" );
        sql.append("               C.MIN_PACK,\n" );
        sql.append("               C.MIN_UNIT,\n" );
        sql.append("               C.IF_SUPLY,\n" );
        sql.append("               C.ARMY_PRICE SALE_PRICE\n" );
        sql.append("          FROM PT_BA_INFO C\n" );
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

	public QuerySet orderAmountSearch(String orderId)throws Exception {

        QuerySet qs = null;
        StringBuffer sql= new StringBuffer();
        sql.append(" SELECT NVL(SUM(ORDER_COUNT * UNIT_PRICE), 0) AMOUNT\n" );
        sql.append("          FROM PT_BU_SALE_ORDER_DTL\n" );
        sql.append("         WHERE ORDER_ID = "+orderId+"\n" );
        qs = factory.select(null,sql.toString());
        return qs;
    }
	public boolean orderAmountUpdate(PtBuSaleOrderVO ptBuSaleOrderVO) throws Exception {

        return factory.update(ptBuSaleOrderVO);
    }
	public boolean deleteOrderPart(PtBuSaleOrderDtlVO ptBuSaleOrderDtlVO) throws Exception {

        return factory.delete(ptBuSaleOrderDtlVO);
    }
	
	public BaseResultSet orderPartAddSearch(PageManager page, User user, String conditions,String orgType,String orderId,String warehouseId) throws Exception
    {
    	String wheres = conditions;
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
		sql.append("       T.STOCK,\n" );
		sql.append("       T.SALE_PRICE\n");
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
		sql.append("               (SELECT SUPPLIER_ID\n" );
		sql.append("                  FROM PT_BA_SUPPLIER\n" );
		sql.append("                 WHERE SUPPLIER_CODE = '9XXXXXX' AND PART_IDENTIFY = "+DicConstant.YXBS_01+") SUPPLIER_ID,\n" );
		sql.append("               '9XXXXXX' SUPPLIER_CODE,\n" );
		sql.append("               (SELECT SUPPLIER_NAME\n" );
		sql.append("                  FROM PT_BA_SUPPLIER\n" );
		sql.append("                 WHERE SUPPLIER_CODE = '9XXXXXX' AND PART_IDENTIFY = "+DicConstant.YXBS_01+") SUPPLIER_NAME,\n" );
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
		sql.append("                       A.ARMY_PRICE SALE_PRICE,\n" );
		sql.append("                       A.IF_SUPLY,\n" );
		sql.append("                       A.IF_DIRECT,\n" );
		sql.append("                       A.IF_ASSEMBLY\n" );
		sql.append("                  FROM PT_BA_INFO A\n" );
		sql.append("                 WHERE A.IF_SUPLY = "+DicConstant.SF_02+"\n" );
		sql.append("                   AND A.PART_STATUS <> "+DicConstant.PJZT_02+"\n" );
		sql.append("                   AND A.PART_TYPE <> "+DicConstant.PJLB_02+"\n" );
//		sql.append("                   AND A.IF_BOOK = "+DicConstant.SF_01+"\n" );
		sql.append("                   AND A.ARMY_PRICE > 0) C LEFT JOIN\n" );
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
		sql.append("               D.IF_SUPLY,\n" );
		sql.append("               D.IF_DIRECT,\n" );
		sql.append("               D.IF_ASSEMBLY,\n" );
		sql.append("               D.SUPPLIER_ID,\n" );
		sql.append("               D.SUPPLIER_CODE,\n" );
		sql.append("               D.SUPPLIER_NAME,\n" );
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
		sql.append("                       A.ARMY_PRICE SALE_PRICE,\n" );
		sql.append("                       A.IF_SUPLY,\n" );
		sql.append("                       C.SUPPLIER_ID,\n" );
		sql.append("                       C.SUPPLIER_CODE,\n" );
		sql.append("                       C.SUPPLIER_NAME,\n" );
		sql.append("                       A.IF_DIRECT,\n" );
		sql.append("                       A.IF_ASSEMBLY\n" );
		sql.append("                  FROM PT_BA_INFO             A,\n" );
		sql.append("                       PT_BA_PART_SUPPLIER_RL B,\n" );
		sql.append("                       PT_BA_SUPPLIER         C\n" );
		sql.append("                 WHERE A.PART_ID = B.PART_ID\n" );
		sql.append("                   AND B.SUPPLIER_ID = C.SUPPLIER_ID\n" );
		sql.append("                   AND B.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n" );
		sql.append("                   AND C.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n" );
		sql.append("                   AND A.IF_SUPLY = "+DicConstant.SF_01+"\n" );
		sql.append("                   AND A.PART_STATUS <> "+DicConstant.PJZT_02+"\n" );
		sql.append("                   AND A.PART_TYPE <> "+DicConstant.PJLB_02+"\n" );
//		sql.append("                   AND A.IF_BOOK = "+DicConstant.SF_01+"\n" );
		sql.append("                   AND A.ARMY_PRICE > 0) D LEFT JOIN\n" );
		sql.append(" PT_BU_STOCK E ON D.PART_ID = E.PART_ID AND D.SUPPLIER_ID = E.SUPPLIER_ID AND E.WAREHOUSE_ID = "+warehouseId+"\n" );
		sql.append(" ) T\n" );
    	bs = factory.select(sql.toString(), page);
		bs.setFieldDic("UNIT", "JLDW");
		bs.setFieldDic("MIN_UNIT", "JLDW");
		bs.setFieldDic("IF_SUPLY", "SF");

		
    	return bs;
    }
	
	public boolean orderPartInfoInsert(PtBuSaleOrderDtlVO vo)
            throws Exception {
        return factory.insert(vo);
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
	    public boolean orderInfoDelete(PtBuSaleOrderVO vo)
	            throws Exception {
	        return factory.delete(vo);
	    }
	    public QuerySet getPayInfo(User user) throws Exception
	    {
	    	QuerySet qs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT T.ACCOUNT_ID,T.ACCOUNT_TYPE,T.AVAILABLE_AMOUNT,T.BALANCE_AMOUNT,T.OCCUPY_AMOUNT FROM PT_BU_ACCOUNT T WHERE T.ORG_ID = "+user.getOrgId()+" AND T.ACCOUNT_TYPE = "+DicConstant.ZJZHLX_04+"");
	    	qs = factory.select(null, sql.toString());
	    	return qs;
	    }
	    public boolean orderFundsInsert(PtBuSaleOrderPayVO vo)
	            throws Exception {
	    	String payId=SequenceUtil.getCommonSerivalNumber(factory);
			vo.setPayId(payId);
	        return factory.insert(vo);
	    }
	    public boolean accountUpdate(String accountId,String payAmount)
	            throws Exception {
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("UPDATE PT_BU_ACCOUNT SET OCCUPY_AMOUNT = OCCUPY_AMOUNT+"+payAmount+" ,AVAILABLE_AMOUNT = AVAILABLE_AMOUNT-"+payAmount+" WHERE ACCOUNT_ID ="+accountId+"\n");
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
	    public void updateOrderPart(String dtlId,String orderId,String orderCount,String remarks,User user) throws Exception {
	    	StringBuffer sql = new StringBuffer();
	    	sql.append("UPDATE PT_BU_SALE_ORDER_DTL SET REMARKS='" + remarks + "',ORDER_COUNT="+orderCount+",AMOUNT= UNIT_PRICE*"+orderCount+",UPDATE_USER='"+user.getAccount()+"',UPDATE_TIME=SYSDATE WHERE DTL_ID ="+dtlId+" AND ORDER_ID ="+orderId+"\n");
	       factory.update(sql.toString(),null);
	    }
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
	        sql.append("       A.COUNT,A.REMARKS\n" );
	        sql.append("  FROM PT_BU_SALE_ORDER_DTL_TMP A, PT_BA_INFO B\n" );
			bs = factory.select(sql.toString(), page);
			return bs;
	   }
	    public QuerySet partOrderInfoSearch(String orderId)
	            throws Exception {
	    	   String sql = "SELECT * FROM PT_BU_SALE_ORDER WHERE ORDER_ID="+orderId+"\n";
	        return factory.select(null,sql);
	    }
	    public void insertPartOrderDetail(User user,String orderId,String promId,String tmpNo,String flag)throws Exception {
			StringBuffer sql= new StringBuffer();
			sql.append("MERGE INTO PT_BU_SALE_ORDER_DTL T\n" );
			sql.append("    USING (" +
							" SELECT PART_ID, PART_CODE, PART_NAME, IF_SUPLY, SUPPLIER_ID, SUPPLIER_CODE, SUPPLIER_NAME, SUM(COUNT) COUNT,REMARKS, ARMY_PRICE, SUM(COUNT * ARMY_PRICE) AMOUNT FROM ("+
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
//			if(!"".equals(promId)&&promId!=null){
//				sql.append("       (SELECT PROM_PRICE FROM PT_BU_PROMOTION_PART WHERE PART_CODE = A.PART_CODE) SALE_PRICE, \n" );
//				sql.append("       A.COUNT * (SELECT PROM_PRICE FROM PT_BU_PROMOTION_PART WHERE PART_CODE = A.PART_CODE) AMOUNT\n" );
//			}else{
//				if("1".equals(flag)){
//					// 直营订单
//					sql.append("       B.RETAIL_PRICE SALE_PRICE,\n" );
//					sql.append("       A.COUNT * B.RETAIL_PRICE AMOUNT\n" );
//				}else{
//					// 正常订单
//		 			sql.append("       B.SALE_PRICE,\n" );
//					sql.append("       A.COUNT * B.SALE_PRICE AMOUNT\n" );
//				}
//			}
			sql.append("       B.ARMY_PRICE,\n" );
			sql.append("       A.COUNT * B.ARMY_PRICE AMOUNT,A.REMARKS\n" );
			sql.append(" FROM PT_BU_SALE_ORDER_DTL_TMP A, PT_BA_INFO B");
			sql.append(" WHERE A.PART_CODE = B.PART_CODE\n" );
			sql.append("   AND A.USER_ACCOUNT='"+user.getAccount()+"' "+tmpNo+"" +
					"  ) T  GROUP BY PART_ID, PART_CODE, PART_NAME, IF_SUPLY,REMARKS, SUPPLIER_ID, SUPPLIER_CODE, SUPPLIER_NAME, ARMY_PRICE "+
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
			sql.append("   AMOUNT,REMARKS,\n" );
			sql.append("   CREATE_USER,\n" );
			sql.append("   CREATE_TIME) VALUES\n" );
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
			sql.append("       T1.ARMY_PRICE, \n" );
			sql.append("       T1.AMOUNT,T1.REMARKS,\n" );
//			sql.append("       T1.CREATE_USER,\n" );
//			sql.append("       T1.CREATE_TIME)\n" );
			sql.append("       '"+user.getAccount()+"',\n" );
			sql.append("       SYSDATE)\n" );
			sql.append("    WHEN MATCHED THEN\n" );
			sql.append("        UPDATE SET T.ORDER_COUNT=T.ORDER_COUNT+T1.COUNT,T.AMOUNT=T.AMOUNT+T1.AMOUNT,T.REMARKS=T1.REMARKS\n" );
	    	factory.update(sql.toString(),null);
	    }
	    public QuerySet expData(String pConditions,User user) throws Exception {

	    	String wheres = " WHERE ROW_NO IN ("+ pConditions + ") AND USER_ACCOUNT='"+user.getAccount()+"' \n";
	    	StringBuilder sql= new StringBuilder();
	    	sql.append("SELECT PART_CODE, SUPPLIER_CODE, COUNT\n" );
	    	sql.append("  FROM PT_BU_SALE_ORDER_DTL_TMP");
	        //执行方法，不需要传递conn参数
	        return factory.select(null, sql.toString()+wheres);
	    }
	    public void partOrderDtlDelete(String orderId)
	            throws Exception {
	    	StringBuffer sql = new StringBuffer();
	    	sql.append("DELETE FROM PT_BU_SALE_ORDER_DTL WHERE ORDER_ID ="+orderId+"\n");
	        factory.delete(sql.toString(), null);
	        StringBuffer sql1 = new StringBuffer();
	    	sql1.append("UPDATE PT_BU_SALE_ORDER SET ORDER_AMOUNT=0 WHERE ORDER_ID ="+orderId+"\n");
	        factory.delete(sql1.toString(), null);
	    }

}
