package com.org.dms.dao.part.salesMng.orderMng;

import java.sql.SQLException;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.common.PartOddNumberUtil;
import com.org.dms.vo.part.PtBuSaleOrderCheckVO;
import com.org.dms.vo.part.PtBuSaleOrderVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.SequenceUtil;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;
/**
 * @Description:订单审核Dao
 * @Date: 2014.08.19
 * @Author SUNXUEDONG
 */
public class DelPartOrderCheckDao extends BaseDAO{
	public static final DelPartOrderCheckDao getInstance(ActionContext atx) {
		DelPartOrderCheckDao dao = new DelPartOrderCheckDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	/**
	 * 订单查询
	 */
	public BaseResultSet partOrderCheckSearch(PageManager page, User user, String conditions,String orgId) throws Exception
    {
    	String wheres = conditions;
    	wheres += "AND T.WAREHOUSE_ID ="+orgId+"\n"
    		   +  "AND T.ORDER_STATUS = "+DicConstant.DDZT_02+"\n"
    		   +  "AND T.ORDER_TYPE NOT IN('"+DicConstant.DDLX_09+"','"+DicConstant.DDLX_10+"')"
    		   +  "AND T.STATUS = "+DicConstant.YXBS_01+"";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.ORDER_ID, T.ORDER_NO,subStr(T.ORDER_NO,-1) AS DELAY_COUNT, T.ORDER_TYPE, T.ORDER_STATUS, T.ORG_CODE, T.ORG_NAME, T.WAREHOUSE_ID,\n" );
    	sql.append("       T.WAREHOUSE_CODE, T.WAREHOUSE_NAME, T.ORDER_AMOUNT, T.EXPECT_DATE,T.IF_CREDIT,T.IF_TRANS,T.CUSTORM_NAME,\n" );
    	sql.append("       T.TRANS_TYPE,T.DELIVERY_ADDR,T.LINK_MAN,T.PHONE,T.ZIP_CODE,T.REMARKS,T.COMPANY_ID,T.ORG_ID,T.CREATE_USER,\n" );
    	sql.append("       T.CREATE_TIME,T.UPDATE_USER,T.UPDATE_TIME,T.STATUS,T.OEM_COMPANY_ID,T.SECRET_LEVEL,T.IF_CHANEL_ORDER,\n" );
    	sql.append("       T.SOURCE_ORDER_NO,T.ADDR_TYPE,T.PROVINCE_CODE,T.PROVINCE_NAME,T.CITY_CODE,T.CITY_NAME,T.COUNTRY_CODE,T.COUNTRY_NAME,T.PROM_ID,T.APPLY_DATE,T.SHIP_STATUS,T.REAL_AMOUNT,T.IF_DELAY_ORDER,T.VIN\n" );
    	sql.append("  FROM PT_BU_SALE_ORDER T");
    	bs = factory.select(sql.toString(), page);
		bs.setFieldDic("ORDER_TYPE", "DDLX");
		bs.setFieldDic("ORDER_STATUS", "DDZT");
		bs.setFieldDic("TRANS_TYPE", "FYFS");
		bs.setFieldDic("IF_CREDIT", "SF");
		bs.setFieldDic("IF_TRANS", "SF");
		bs.setFieldDateFormat("EXPECT_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDic("IF_DELAY_ORDER", DicConstant.SF);
        // 提报人
        bs.setFieldUserID("CREATE_USER");
    	return bs;
    }
	/**
	 * 资金账户可用余额查询
	 */
	public BaseResultSet accountSearch(PageManager page, String orgId, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND T.ACCOUNT_TYPE IN ("+DicConstant.ZJZHLX_01+", "+DicConstant.ZJZHLX_02+", "+DicConstant.ZJZHLX_03+")\n";
    	wheres += " AND ORG_ID = "+orgId+"AND T.STATUS ="+DicConstant.YXBS_01+"\n";
    	wheres += " UNION SELECT 2 TYPE, NVL(SUM(AVAILABLE_AMOUNT),0) AVAILABLE_AMOUNT FROM PT_BU_ACCOUNT T\n";
    	wheres += " WHERE T.ACCOUNT_TYPE = "+DicConstant.ZJZHLX_04+" AND ORG_ID = "+orgId+"AND T.STATUS ="+DicConstant.YXBS_01+"\n";
    	page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT 1 TYPE, NVL(SUM(T.AVAILABLE_AMOUNT),0) AVAILABLE_AMOUNT\n" );
    	sql.append("  FROM PT_BU_ACCOUNT T\n" );
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
	/**
	 * 订单审核记录查询
	 */
	public BaseResultSet orderCheckListSearch(PageManager page, User user, String conditions,String orderId) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND A.CHECK_ORG = B.ORG_ID AND A.CHECK_RESULT = C.ID AND C.DIC_NAME_CODE ='"+DicConstant.DDZT+"' AND A.ORDER_ID="+orderId+" ORDER BY A.CHECK_DATE DESC\n";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT A.CHECK_DATE,\n" );
    	sql.append("       C.DIC_VALUE  CHECK_RESULT,\n" );
    	sql.append("       B.ONAME      CHECK_ORG,\n" );
    	sql.append("       A.CHECK_USER,\n" );
    	sql.append("       A.REMARKS\n" );
    	sql.append("  FROM PT_BU_SALE_ORDER_CHECK A, TM_ORG B, DIC_TREE C\n" );
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDateFormat("CHECK_DATE", "yyyy-MM-dd");
    	return bs;
    }
	/**
	 * 订单订购配件列表查询
	 */
	public BaseResultSet partOrderDetailSearch(PageManager page, User user, String conditions,String orderId,String orgId) throws Exception
    {
    	String wheres = conditions;
    	wheres += " ORDER BY PART_CODE ASC\n";
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
    	sql.append("       T.REMARKS,\n" );
    	sql.append("       T.WAREHOUSE_ID,\n" );
    	sql.append("       T.AVAILABLE_AMOUNT\n" );
    	sql.append("FROM (SELECT T.DTL_ID,\n" );
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
    	sql.append("       T.REMARKS,\n" );
    	sql.append("       T.WAREHOUSE_ID,\n" );
    	sql.append("       NVL(T2.AVAILABLE_AMOUNT,0) AVAILABLE_AMOUNT\n" );
    	sql.append("FROM (SELECT A.DTL_ID,\n" );
    	sql.append("       A.PART_ID,\n" );
    	sql.append("       A.PART_CODE,\n" );
    	sql.append("       A.PART_NAME,\n" );
    	sql.append("       C.UNIT,\n" );
    	sql.append("       C.MIN_PACK,\n" );
    	sql.append("       C.MIN_UNIT,\n" );
    	sql.append("       A.UNIT_PRICE,\n" );
    	sql.append("       A.IF_SUPPLIER,\n" );
    	sql.append("       A.SUPPLIER_ID,\n" );
    	sql.append("       A.SUPPLIER_CODE,\n" );
    	sql.append("       A.SUPPLIER_NAME,\n" );
    	sql.append("       A.ORDER_COUNT,\n" );
    	sql.append("       A.REMARKS,\n" );
    	sql.append("       B.WAREHOUSE_ID\n" );
    	sql.append("  FROM PT_BU_SALE_ORDER_DTL A, PT_BU_SALE_ORDER B, PT_BA_INFO C\n" );
    	sql.append("  WHERE A.ORDER_ID = B.ORDER_ID AND A.PART_ID = C.PART_ID AND A.ORDER_ID="+orderId+")T LEFT JOIN\n");
    	sql.append(" PT_BU_DEALER_STOCK T2 ON T.PART_ID = T2.PART_ID AND T.SUPPLIER_ID = T2.SUPPLIER_ID AND T.WAREHOUSE_ID = T2.ORG_ID)T\n");
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("UNIT", "JLDW");
    	bs.setFieldDic("MIN_UNIT", "JLDW");
    	bs.setFieldDic("IF_SUPPLIER", "SF");
    	return bs;
    }
    /**
	 * 订单审核明细更新审核数量
     */
    public void orderAuditCountUpdate(String auditCount,String dtlId,String orderId,User user)throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.PART_CODE, NVL(A.AVAILABLE_AMOUNT, 0) AVAILABLE_AMOUNT\n" );
    	sql.append("  FROM (SELECT B.PART_ID, B.SUPPLIER_ID, B.PART_CODE\n" );
    	sql.append("          FROM PT_BU_SALE_ORDER_DTL B\n" );
    	sql.append("         WHERE DTL_ID = "+dtlId+"\n" );
    	sql.append("           AND ORDER_ID = "+orderId+") T\n" );
    	sql.append("  LEFT JOIN PT_BU_DEALER_STOCK A\n" );
    	sql.append("    ON (T.PART_ID = A.PART_ID AND A.SUPPLIER_ID = T.SUPPLIER_ID AND\n" );
    	sql.append("       A.ORG_ID = "+user.getOrgId()+")\n");
    	QuerySet qs = factory.select(null, sql.toString());
    	if(qs.getRowCount()>0){
    		String partCode = qs.getString(1, "PART_CODE");
    		String availableAmount= qs.getString(1, "AVAILABLE_AMOUNT");
    		if(Integer.valueOf(auditCount)>Integer.valueOf(availableAmount)){
    			throw new Exception("配件"+partCode+"可用库存数量不足，请关闭页面重新审核.");
    		}
    		StringBuffer sql1 = new StringBuffer();
        	sql1.append("UPDATE PT_BU_SALE_ORDER_DTL SET AUDIT_COUNT ="+auditCount+",UPDATE_USER='"+user.getAccount()+"',UPDATE_TIME=SYSDATE WHERE DTL_ID="+dtlId+"AND ORDER_ID ="+orderId+"\n");
            factory.update(sql1.toString(), null);
    	}
    }
    /**
   	 * 订单审核驳回
     */
    public boolean partOrderCheckPass(PtBuSaleOrderVO vo)throws Exception {
        return factory.update(vo);
    }
    /**
	 * 订单审核日志
     */
    public boolean partOrderCheckLogInsert(PtBuSaleOrderCheckVO vo)throws Exception {
        return factory.insert(vo);
    }
    /**
   	 * 延期订单生成
        */
       public void delayOrderInfoInsert(String orderId,User user)
               throws Exception {
       	String orderNo = PartOddNumberUtil.getDelaySaleOrderNo(factory, orderId);
       	String delayOrderId = SequenceUtil.getCommonSerivalNumber(factory);
       	StringBuffer sql= new StringBuffer();
       	sql.append("INSERT INTO PT_BU_SALE_ORDER (ORDER_ID,ORDER_NO,ORDER_TYPE,ORDER_STATUS,ORG_CODE,\n" );
       	sql.append("   ORG_NAME,WAREHOUSE_ID,WAREHOUSE_CODE,WAREHOUSE_NAME,EXPECT_DATE,IF_CREDIT,\n" );
       	sql.append("   IF_TRANS,TRANS_TYPE,DELIVERY_ADDR,LINK_MAN,PHONE,ZIP_CODE,REMARKS,COMPANY_ID,\n" );
       	sql.append("   ORG_ID,CREATE_USER,CREATE_TIME,STATUS,OEM_COMPANY_ID,IF_CHANEL_ORDER,IF_DELAY_ORDER,\n" );
       	sql.append("   SOURCE_ORDER_ID,SOURCE_ORDER_NO,ADDR_TYPE,PROVINCE_CODE,PROVINCE_NAME,CITY_CODE,CITY_NAME,COUNTRY_CODE,COUNTRY_NAME,PROM_ID,APPLY_DATE,DIRECT_TYPE_ID,DIRECT_TYPE_CODE,DIRECT_TYPE_NAME,VIN,PLAN_PRODUCE_NO,BELONG_ASSEMBLY)\n" );
       	sql.append("  SELECT "+delayOrderId+",'"+orderNo+"',ORDER_TYPE,"+DicConstant.DDZT_02+",ORG_CODE,\n" );
       	sql.append("   ORG_NAME,WAREHOUSE_ID,WAREHOUSE_CODE,WAREHOUSE_NAME,EXPECT_DATE,IF_CREDIT,\n" );
       	sql.append("   IF_TRANS,TRANS_TYPE,DELIVERY_ADDR,LINK_MAN,PHONE,ZIP_CODE,REMARKS,COMPANY_ID,\n" );
       	sql.append("   ORG_ID,'"+user.getAccount()+"',SYSDATE,STATUS,OEM_COMPANY_ID,IF_CHANEL_ORDER,"+DicConstant.SF_01+",\n" );
       	sql.append("   ORDER_ID,ORDER_NO,ADDR_TYPE,PROVINCE_CODE,PROVINCE_NAME,CITY_CODE,CITY_NAME,COUNTRY_CODE,COUNTRY_NAME,PROM_ID,APPLY_DATE,DIRECT_TYPE_ID,DIRECT_TYPE_CODE,DIRECT_TYPE_NAME,VIN,PLAN_PRODUCE_NO,BELONG_ASSEMBLY\n" );
       	sql.append("    FROM PT_BU_SALE_ORDER WHERE ORDER_ID = "+orderId+"\n" );
       	//插入延期订单主信息
       	factory.update(sql.toString(), null);
       	
       	StringBuffer sql1= new StringBuffer();
       	sql1.append("INSERT INTO PT_BU_SALE_ORDER_DTL(DTL_ID,ORDER_ID,PART_ID,PART_CODE,PART_NAME,PART_NO,ORDER_COUNT,\n" );
       	sql1.append("CREATE_USER,CREATE_TIME,IF_SUPPLIER,SUPPLIER_ID,SUPPLIER_CODE,SUPPLIER_NAME,UNIT_PRICE,AMOUNT)\n" );
       	sql1.append("SELECT F_GETID(),"+delayOrderId+",PART_ID,PART_CODE,PART_NAME,PART_NO,ORDER_COUNT-AUDIT_COUNT,'"+user.getAccount()+"',\n" );
       	sql1.append("SYSDATE,IF_SUPPLIER,SUPPLIER_ID,SUPPLIER_CODE,SUPPLIER_NAME,UNIT_PRICE,\n" );
       	sql1.append("(ORDER_COUNT-AUDIT_COUNT)*UNIT_PRICE FROM PT_BU_SALE_ORDER_DTL\n" );
       	sql1.append("WHERE ORDER_ID ="+orderId+" AND ORDER_COUNT-AUDIT_COUNT>0");
       	//插入延期订单明细信息
       	factory.update(sql1.toString(), null);
       	
       	QuerySet qs1 = null;
       	StringBuffer sql6= new StringBuffer();
       	sql6.append("SELECT SUM(ORDER_COUNT*UNIT_PRICE) ORDER_AMOUNT\n" );
       	sql6.append("  FROM PT_BU_SALE_ORDER_DTL A\n" );
       	sql6.append(" WHERE A.ORDER_ID = "+delayOrderId+"\n");
       	//查询延期订单付款信息
       	qs1 = factory.select(null, sql6.toString());
       	if(qs1.getRowCount()>0){
       		String orderAmount = qs1.getString(1, "ORDER_AMOUNT");
       		StringBuffer sql7 = new StringBuffer();
       		sql7.append("UPDATE PT_BU_SALE_ORDER SET ORDER_AMOUNT="+orderAmount+" WHERE ORDER_ID = "+delayOrderId+"\n");
       		//更新延期订单总金额
       		factory.update(sql7.toString(), null);
       	}
   }
    public QuerySet queryAmout(String partIds,String supplierIds,String orgId) throws SQLException {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.OCCUPY_AMOUNT,T.AVAILABLE_AMOUNT FROM PT_BU_DEALER_STOCK T WHERE T.ORG_ID = "+orgId+" AND T.PART_ID ="+partIds+" AND T.SUPPLIER_ID ="+supplierIds+"\n" );
    	qs = factory.select(null,sql.toString());
    	return qs;
    }
	public void updateAmount2(String partId, String supplierIds, String orgId,String auditCount) throws SQLException {
		StringBuffer sql= new StringBuffer(); 
		sql.append("UPDATE PT_BU_DEALER_STOCK T SET T.OCCUPY_AMOUNT=T.OCCUPY_AMOUNT+"+auditCount+" ,T.AVAILABLE_AMOUNT=T.AVAILABLE_AMOUNT-"+auditCount+" WHERE T.ORG_ID = "+orgId+" AND T.PART_ID ="+partId+" AND T.SUPPLIER_ID ="+supplierIds+"\n" );
		factory.update(sql.toString(), null);
		
	}
	public void updateAmount1(String partId,String orgId,String auditCount) throws SQLException {
		StringBuffer sql= new StringBuffer(); 
		sql.append("UPDATE PT_BU_DEALER_STOCK T SET T.OCCUPY_AMOUNT=T.OCCUPY_AMOUNT+"+auditCount+" ,T.AVAILABLE_AMOUNT=T.AVAILABLE_AMOUNT-"+auditCount+" WHERE T.ORG_ID = "+orgId+" AND T.PART_ID ="+partId+"\n" );
		factory.update(sql.toString(), null);
	}
	/**
	 * 订单审核驳回
     */
    public boolean partOrderCheckApproveBack(PtBuSaleOrderVO vo)
            throws Exception {
        return factory.update(vo);
    }
}
