package com.org.dms.dao.part.salesMng.orderMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuSaleOrderVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class PartOrderUnlockDao extends BaseDAO{
	//定义instance
    public  static final PartOrderUnlockDao getInstance(ActionContext atx)
    {
    	PartOrderUnlockDao dao = new PartOrderUnlockDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
    /**
     * 
     * @date()2014年10月10日下午3:55:39
     * @author Administrator
     * @to_do:审核通过的订单查询
     * @param page
     * @param user
     * @param conditions
     * @return
     * @throws Exception
     */
    public BaseResultSet partOrderSearch(PageManager page, User user, String conditions,String ifAm) throws Exception
    {
        String wheres = conditions+ " AND T.ORG_ID IN (SELECT ORG_ID FROM TM_ORG WHERE PID IN (SELECT ORG_ID FROM PT_BA_ORDER_CHECK WHERE USER_ACCOUNT='"+user.getAccount()+"'))";
              // 不是直发订单，并且生成发料单。
              wheres +=" AND T.ORDER_TYPE <> "+DicConstant.DDLX_05+"";
        if(DicConstant.SF_01.equals(ifAm)){
        	wheres += " AND T.ORDER_STATUS = "+DicConstant.DDZT_03+"\n"
                    + " AND T.IF_CHANEL_ORDER ="+DicConstant.SF_02+"\n"
                    + " AND T.ORDER_ID = T1.ORDER_ID\n"
                    + " AND T.ORDER_TYPE = "+DicConstant.DDLX_08+" "
                    + " AND T1.TOTAL_COUNT >0\n"
                    + " AND (T.SHIP_STATUS  ="+DicConstant.DDFYZT_01+"\n"
                    + " OR T.SHIP_STATUS IS NULL)"
                    + " AND T.STATUS ="+DicConstant.YXBS_01+""
                    + " ORDER BY T.APPLY_DATE ASC\n";
        }else{
        	wheres += " AND T.ORDER_STATUS = "+DicConstant.DDZT_03+"\n"
                    + " AND T.IF_CHANEL_ORDER ="+DicConstant.SF_02+"\n"
                    + " AND T.ORDER_TYPE <> "+DicConstant.DDLX_08+" "
                    + " AND T.ORDER_ID = T1.ORDER_ID\n"
                    + " AND T1.TOTAL_COUNT >0\n"
                    + " AND (T.SHIP_STATUS  ="+DicConstant.DDFYZT_01+"\n"
                    + " OR T.SHIP_STATUS IS NULL)"
                    + " AND T.STATUS ="+DicConstant.YXBS_01+""
                    + " ORDER BY T.APPLY_DATE ASC\n";
        }
        page.setFilter(wheres);
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.ORDER_ID,\n" );
        sql.append("       T.ORDER_NO,\n" );
        sql.append("       T.ORDER_TYPE,\n" );
        sql.append("       T.ORG_ID,\n" );
        sql.append("       T.ORG_CODE,\n" );
        sql.append("       T.ORG_NAME,\n" );
        sql.append("       T.WAREHOUSE_ID,\n" );
        sql.append("       T.WAREHOUSE_CODE,\n" );
        sql.append("       T.WAREHOUSE_NAME,\n" );
        sql.append("       T.EXPECT_DATE,\n" );
        sql.append("       T.ORDER_AMOUNT,\n" );
        sql.append("       T.APPLY_DATE,\n" );
        sql.append("       T.CREATE_USER,\n" );
        sql.append("       T.REMARKS,\n" );
        sql.append("       T.IF_DELAY_ORDER,\n" );
        sql.append("       T.PROM_ID,\n" );
        sql.append("       T.IF_CREDIT,\n" );
        sql.append("       T.IF_TRANS,\n" );
        sql.append("       T.PROVINCE_CODE,\n" );
        sql.append("       T.PROVINCE_NAME,\n" );
        sql.append("       T.CITY_CODE,\n" );
        sql.append("       T.CITY_NAME,\n" );
        sql.append("       T.COUNTRY_CODE,\n" );
        sql.append("       T.COUNTRY_NAME,\n" );
        sql.append("       T.TRANS_TYPE,\n" );
        sql.append("       T.ZIP_CODE,\n" );
        sql.append("       T.DELIVERY_ADDR,\n" );
        sql.append("       T.LINK_MAN,\n" );
        sql.append("       T.PHONE,\n" );
        sql.append("       T.SHIP_STATUS,\n" );
        sql.append("       T.ORDER_STATUS\n" );
        sql.append("  FROM PT_BU_SALE_ORDER T,");
        sql.append("  (SELECT SUM(AUDIT_COUNT) TOTAL_COUNT ,ORDER_ID FROM PT_BU_SALE_ORDER_DTL GROUP BY ORDER_ID)T1");
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("ORDER_TYPE", "DDLX");
        bs.setFieldDic("ORDER_STATUS", "DDZT");
        bs.setFieldDic("TRANS_TYPE", "FYFS");
        bs.setFieldDic("IF_CREDIT", "SF");
        bs.setFieldDic("IF_TRANS", "SF");
        bs.setFieldDic("IF_DELAY_ORDER", "SF");
        bs.setFieldDateFormat("EXPECT_DATE", "yyyy-MM-dd");
        bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
        // 提报人
        bs.setFieldUserID("CREATE_USER");
        return bs;
    }
    /**
     * 
     * @date()2014年10月10日下午3:56:59
     * @author Administrator
     * @to_do:库存占用释放
     * @param orderId
     * @param user
     * @throws Exception
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
     * 
     * @date()2014年10月10日下午3:56:40
     * @author Administrator
     * @to_do:资金占用释放
     * @param orderId
     * @param user
     * @throws Exception
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
     * 
     * @date()2014年10月10日下午3:57:30
     * @author Administrator
     * @to_do:将对应发料单置为无效
     * @param orderId
     * @param user
     * @return
     * @throws Exception
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
    public boolean updateOrderStatus(PtBuSaleOrderVO vo)throws Exception {
        
        return factory.update(vo);
    }
    public QuerySet checkDelay(String orderId) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT 1 FROM PT_BU_SALE_ORDER T WHERE T.SOURCE_ORDER_ID = "+orderId+" ");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
    
    public boolean deleteOrderOccupy(String orderId)throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("DELETE FROM PT_BU_STOCK_OCCUPY_LOG WHERE ORDER_ID = "+orderId+"");
        return factory.update(sql.toString(), null);
    }
    public boolean updateOrderDtl01(String orderId,User user)throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("UPDATE PT_BU_SALE_ORDER_DTL T\n" );
    	sql.append("   SET T.ORDER_COUNT = T.AUDIT_COUNT,\n" );
    	sql.append("       T.AUDIT_COUNT = 0,\n" );
    	sql.append("       T.UPDATE_USER = '"+user.getAccount()+"',\n" );
    	sql.append("       T.UPDATE_TIME = SYSDATE\n" );
    	sql.append(" WHERE T.ORDER_ID = "+orderId+"");
        return factory.update(sql.toString(), null);
    }
    public boolean updateOrderDtl02(String orderId,User user)throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("UPDATE PT_BU_SALE_ORDER_DTL T\n" );
    	sql.append("   SET T.AUDIT_COUNT = 0,\n" );
    	sql.append("       T.UPDATE_USER = '"+user.getAccount()+"',\n" );
    	sql.append("       T.UPDATE_TIME = SYSDATE\n" );
    	sql.append(" WHERE T.ORDER_ID = "+orderId+"");
        return factory.update(sql.toString(), null);
    }
    
    public QuerySet queryOrderAmount(String orderId)throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT SUM(NVL(T.ORDER_COUNT,0)*T.UNIT_PRICE) ORDER_AMOUNT FROM PT_BU_SALE_ORDER_DTL T\n" );
    	sql.append(" WHERE T.ORDER_ID = "+orderId+"");
    	return factory.select(null, sql.toString());
    }
    public BaseResultSet saleOrderInfoSearch(PageManager page, User user, String conditions,String ORDER_ID) throws Exception
    {
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.ORDER_ID,\n" );
    	sql.append("       T.ORDER_NO,\n" );
    	sql.append("       T.ORDER_TYPE,\n" );
    	sql.append("       T.ORG_ID,\n" );
    	sql.append("       T.ORG_CODE,\n" );
    	sql.append("       T.ORG_NAME,\n" );
    	sql.append("       T.WAREHOUSE_ID,\n" );
    	sql.append("       T.WAREHOUSE_CODE,\n" );
    	sql.append("       T.WAREHOUSE_NAME,\n" );
    	sql.append("       T.EXPECT_DATE,\n" );
    	sql.append("       T.ORDER_AMOUNT,\n" );
    	sql.append("       T.APPLY_DATE,\n" );
    	sql.append("       T.CREATE_USER,\n" );
    	sql.append("       T.REMARKS,\n" );
    	sql.append("       T.IF_DELAY_ORDER,\n" );
    	sql.append("       T.PROM_ID,\n" );
    	sql.append("       T.IF_CREDIT,\n" );
    	sql.append("       T.IF_TRANS,\n" );
    	sql.append("       T.PROVINCE_CODE,\n" );
    	sql.append("       T.PROVINCE_NAME,\n" );
    	sql.append("       T.CITY_CODE,\n" );
    	sql.append("       T.CITY_NAME,\n" );
    	sql.append("       T.COUNTRY_CODE,\n" );
    	sql.append("       T.COUNTRY_NAME,\n" );
    	sql.append("       T.TRANS_TYPE,\n" );
    	sql.append("       T.ZIP_CODE,\n" );
    	sql.append("       T.DELIVERY_ADDR,\n" );
    	sql.append("       T.LINK_MAN,\n" );
    	sql.append("       T.PHONE,\n" );
    	sql.append("       T.DIRECT_TYPE_NAME,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("       T.PROM_NAME\n" );
    	sql.append("  FROM (SELECT A.ORDER_ID,\n" );
    	sql.append("               A.ORDER_NO,\n" );
    	sql.append("               A.ORDER_TYPE,\n" );
    	sql.append("               A.ORG_ID,\n" );
    	sql.append("               A.ORG_CODE,\n" );
    	sql.append("               A.ORG_NAME,\n" );
    	sql.append("               A.WAREHOUSE_ID,\n" );
    	sql.append("               A.WAREHOUSE_CODE,\n" );
    	sql.append("               A.WAREHOUSE_NAME,\n" );
    	sql.append("               A.EXPECT_DATE,\n" );
    	sql.append("               A.ORDER_AMOUNT,\n" );
    	sql.append("               A.APPLY_DATE,\n" );
    	sql.append("               A.CREATE_USER,\n" );
    	sql.append("               A.REMARKS,\n" );
    	sql.append("               A.IF_DELAY_ORDER,\n" );
    	sql.append("               A.IF_CHANEL_ORDER,\n" );
    	sql.append("               A.PROM_ID,\n" );
    	sql.append("               A.IF_CREDIT,\n" );
    	sql.append("               A.IF_TRANS,\n" );
    	sql.append("               A.PROVINCE_CODE,\n" );
    	sql.append("               A.PROVINCE_NAME,\n" );
    	sql.append("               A.CITY_CODE,\n" );
    	sql.append("               A.CITY_NAME,\n" );
    	sql.append("               A.COUNTRY_CODE,\n" );
    	sql.append("               A.COUNTRY_NAME,\n" );
    	sql.append("               A.TRANS_TYPE,\n" );
    	sql.append("               A.ZIP_CODE,\n" );
    	sql.append("               A.DELIVERY_ADDR,\n" );
    	sql.append("               A.LINK_MAN,\n" );
    	sql.append("               A.PHONE,\n" );
    	sql.append("               A.ORDER_STATUS,\n" );
    	sql.append("               A.DIRECT_TYPE_NAME,\n" );
    	sql.append("               A.SUPPLIER_NAME,\n" );
    	sql.append("               B.PROM_NAME\n" );
    	sql.append("          FROM PT_BU_SALE_ORDER A\n" );
    	sql.append("          LEFT JOIN PT_BU_PROMOTION B\n" );
    	sql.append("            ON A.PROM_ID = B.PROM_ID) T\n" );
    	sql.append(" WHERE 1 = 1\n" );
    	sql.append("   AND T.ORDER_ID = "+ORDER_ID+"\n" );
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("ORDER_TYPE", "DDLX");
		bs.setFieldDic("ORDER_STATUS", "DDZT");
		bs.setFieldDic("TRANS_TYPE", "FYFS");
		bs.setFieldDic("IF_CREDIT", "SF");
		bs.setFieldDic("IF_TRANS", "SF");
		bs.setFieldUserID("ORG_ID");
		bs.setFieldDateFormat("EXPECT_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDic("IF_DELAY_ORDER", DicConstant.SF);
    	return bs;
    }
    public BaseResultSet partOrderDetailSearch(PageManager page, User user, String conditions,String orderId) throws Exception
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
    	sql.append("       T.AUDIT_COUNT,\n" );
    	sql.append("       T.WAREHOUSE_ID,\n" );
    	sql.append("       T.AMOUNT,\n" );
    	sql.append("       T.OCCUPY_AMOUNT,\n" );
    	sql.append("       T.AVAILABLE_AMOUNT\n" );
    	//sql.append("       T.USER_ACCOUNT\n" );
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
    	sql.append("               D.AUDIT_COUNT,\n" );
    	sql.append("               D.ORDER_COUNT,\n" );
    	sql.append("               D.WAREHOUSE_ID,\n" );
    	//sql.append("               D.USER_ACCOUNT,\n" );
    	sql.append("               NVL(E.AMOUNT, 0) AMOUNT,\n" );
    	sql.append("               NVL(E.OCCUPY_AMOUNT, 0) OCCUPY_AMOUNT,\n" );
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
    	sql.append("                       A.AUDIT_COUNT,\n" );
    	sql.append("                       A.ORDER_COUNT,\n" );
    	sql.append("                       B.WAREHOUSE_ID\n" );
    	//sql.append("                       P.USER_ACCOUNT\n" );
    	sql.append("                  FROM PT_BU_SALE_ORDER_DTL A,\n" );
    	sql.append("                       PT_BU_SALE_ORDER     B,\n" );
    	sql.append("                       PT_BA_INFO           C\n" );
    	//sql.append("                       PT_BA_WAREHOUSE_KEEPER P\n" );
    	sql.append("                 WHERE 1 = 1\n" );
    	sql.append("                   AND A.ORDER_ID = B.ORDER_ID\n" );
    	sql.append("                   AND A.PART_ID = C.PART_ID\n" );
    	sql.append("                   AND A.AUDIT_COUNT >0\n" );
    	//sql.append("                   AND A.PART_ID = P.PART_ID\n" );
    	sql.append("                   AND A.ORDER_ID = "+orderId+") D\n" );
    	sql.append("          LEFT JOIN PT_BU_STOCK E\n" );
        sql.append("            ON D.PART_ID = E.PART_ID\n" );
        sql.append("           AND D.SUPPLIER_ID = E.SUPPLIER_ID\n" );
        sql.append("           AND D.WAREHOUSE_ID = E.WAREHOUSE_ID) T");
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("UNIT", "JLDW");
    	bs.setFieldDic("MIN_UNIT", "JLDW");
    	bs.setFieldDic("IF_SUPPLIER", "SF");
    	bs.setFieldUserID("USER_ACCOUNT");
    	return bs;
    }
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
    public BaseResultSet orderFundsListSearch(PageManager page, User user, String conditions,String orderId) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND A.ACCOUNT_ID = B.ACCOUNT_ID AND B.ACCOUNT_TYPE= C.ID AND C.DIC_NAME_CODE='"+DicConstant.ZJZHLX+"' AND A.ORDER_ID = "+orderId+"\n";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT A.PAY_ID,A.ACCOUNT_ID, C.DIC_VALUE ACCOUNT_TYPE, B.AVAILABLE_AMOUNT, A.PAY_AMOUNT\n" );
    	sql.append("  FROM PT_BU_SALE_ORDER_PAY A, PT_BU_ACCOUNT B,DIC_TREE C\n" );
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
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
    public BaseResultSet getCarrier(PageManager page, User user, String conditions) throws Exception
    {
    	StringBuffer sql= new StringBuffer();
    	BaseResultSet bs = null;
    	sql.append("SELECT T.IS_AM FROM TM_ORG T WHERE T.ORG_ID = "+user.getOrgId()+"\n" );
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
    public QuerySet checkIfAm(User user) throws Exception
    {
    	StringBuffer sql= new StringBuffer();
    	QuerySet qs = null;
    	sql.append("SELECT T.IS_AM FROM TM_ORG T WHERE T.ORG_ID = "+user.getOrgId()+"\n" );
    	qs = factory.select(null, sql.toString());
    	return qs;
    }

}
