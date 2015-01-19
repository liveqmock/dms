package com.org.dms.dao.part.storageMng.stockOutMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuSaleOrderVO;
import com.org.dms.vo.part.PtBuStockOutModLogVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class OutBillModDao extends BaseDAO{
	//定义instance
    public static final OutBillModDao getInstance(ActionContext atx) {
    	OutBillModDao dao = new OutBillModDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
    public BaseResultSet searchOutBill(PageManager page, User user, String conditions,String partCode,String partName) throws Exception {
        StringBuilder wheres = new StringBuilder(conditions);
        if(!"".equals(partName)&&partName!=null&&!"".equals(partCode)&&partCode!=null){
        	wheres.append("   AND EXISTS (SELECT 1 FROM PT_BU_STOCK_OUT_DTL X WHERE X.PART_CODE LIKE '%"+partCode+"%' AND X.PART_NAME LIKE '%"+partName+"%' AND X.OUT_ID = T.OUT_ID)\n");
        }else if(!"".equals(partName)&&partName!=null){
        	wheres.append("   AND EXISTS (SELECT 1 FROM PT_BU_STOCK_OUT_DTL X WHERE X.PART_NAME LIKE '%"+partName+"%' AND X.OUT_ID = T.OUT_ID)\n");
        }else if(!"".equals(partCode)&&partCode!=null){
        	wheres.append("   AND EXISTS (SELECT 1 FROM PT_BU_STOCK_OUT_DTL X WHERE X.PART_CODE LIKE '%"+partCode+"%' AND X.OUT_ID = T.OUT_ID)\n");
        }
        wheres.append("   AND T.STATUS = " + DicConstant.YXBS_01 + "\n");
        wheres.append("   AND T.OEM_COMPANY_ID=" + user.getOemCompanyId() + "\n");
        wheres.append("   ORDER BY T.OUT_DATE DESC\n");
        page.setFilter(wheres.toString());

        //定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT *\n");
        sql.append("  FROM (SELECT A.OUT_ID,\n");
        sql.append("               A.OUT_NO,\n");
        sql.append("               A.WAREHOUSE_CODE,\n");
        sql.append("               A.WAREHOUSE_NAME,\n");
        sql.append("               A.ORDER_ID,\n");
        sql.append("               A.ORDER_NO,\n");
        sql.append("               A.OUT_TYPE,\n");
        sql.append("               B.ORG_NAME       RECV_UNIT,\n");
        sql.append("               C.CHECK_USER,\n");
        sql.append("               A.OUT_DATE,\n");
        sql.append("               A.STATUS,\n");
        sql.append("               A.CREATE_TIME,\n");
        sql.append("               B.APPLY_DATE,\n");
        sql.append("               A.OEM_COMPANY_ID\n");
        sql.append("          FROM PT_BU_STOCK_OUT A, PT_BU_SALE_ORDER B, (SELECT ORDER_ID, CHECK_USER, MAX(CHECK_DATE) CHECK_DATE FROM PT_BU_SALE_ORDER_CHECK WHERE CHECK_RESULT = 202203 GROUP BY ORDER_ID, CHECK_USER)  C\n");
        sql.append("         WHERE A.ORDER_ID = B.ORDER_ID\n");
        sql.append("           AND A.ORDER_ID = C.ORDER_ID\n");
        sql.append("           AND A.OUT_TYPE IN (" + DicConstant.CKLX_01 + ", " + DicConstant.CKLX_02 + ", " + DicConstant.CKLX_06 + ")\n");
        sql.append("           AND B.SHIP_STATUS IN (" + DicConstant.DDFYZT_03 + ")\n");
        sql.append("           AND A.OUT_STATUS = " + DicConstant.CKDZT_02 + "\n");
        sql.append("           AND A.PRINT_STATUS = " + DicConstant.DYZT_01 + "\n");
        sql.append("UNION ALL\n" );
        sql.append("           SELECT A.OUT_ID,\n" );
        sql.append("               A.OUT_NO,\n" );
        sql.append("               A.WAREHOUSE_CODE,\n" );
        sql.append("               A.WAREHOUSE_NAME,\n" );
        sql.append("               A.ORDER_ID,\n" );
        sql.append("               A.ORDER_NO,\n" );
        sql.append("               A.OUT_TYPE,\n" );
        sql.append("               B.ORG_NAME       RECV_UNIT,\n" );
        sql.append("               C.CHECK_USER,\n" );
        sql.append("               A.OUT_DATE,\n" );
        sql.append("               A.STATUS,\n" );
        sql.append("               A.CREATE_TIME,\n" );
        sql.append("               B.APPLY_DATE,\n" );
        sql.append("               A.OEM_COMPANY_ID\n" );
        sql.append("          FROM PT_BU_STOCK_OUT A, PT_BU_SALE_ORDER B, (SELECT ORDER_ID, CHECK_USER, MAX(CHECK_DATE) CHECK_DATE FROM PT_BU_SALE_ORDER_CHECK WHERE CHECK_RESULT = 202203 GROUP BY ORDER_ID, CHECK_USER)  C\n" );
        sql.append("         WHERE A.ORDER_ID = B.ORDER_ID\n" );
        sql.append("           AND A.ORDER_ID = C.ORDER_ID\n" );
        sql.append("           AND A.OUT_TYPE IN (201401, 201402, 201406)\n" );
        sql.append("           AND B.SHIP_STATUS IN (204806)\n" );
        sql.append("           AND A.OUT_STATUS = 201602\n" );
        sql.append("           AND B.TRANS_TYPE = 203902\n" );
        sql.append("           AND A.PRINT_STATUS = 201701");
        sql.append("           ) T\n");
        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("OUT_TYPE", DicConstant.CKLX);
        bs.setFieldDateFormat("OUT_DATE", "yyyy-MM-dd HH:mm:ss");
        bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
        bs.setFieldUserID("CHECK_USER");
        return bs;
    }
    
    public BaseResultSet searchPart(PageManager page, User user, String conditions, String ORDER_ID) throws Exception {

        //执行方法，不需要传递conn参数
    	 BaseResultSet bs = null;
    	 StringBuffer sql= new StringBuffer();
    	 sql.append("SELECT A.PART_ID,\n" );
    	 sql.append("       A.PART_CODE,\n" );
    	 sql.append("       A.PART_NAME,\n" );
    	 sql.append("       B.PART_NO,\n" );
    	 sql.append("       A.DETAIL_ID,\n" );
    	 sql.append("       B.UNIT,\n" );
    	 sql.append("       D.POSITION_CODE,\n" );
    	 sql.append("       D.POSITION_ID,\n" );
    	 sql.append("       D.AREA_CODE,\n" );
    	 sql.append("       A.OUT_AMOUNT,\n" );
    	 sql.append("       B.PLAN_PRICE,\n" );
    	 sql.append("       B.PLAN_PRICE * A.OUT_AMOUNT PLAN_AMOUNT,\n" );
    	 sql.append("       B.REMARKS REMARK,\n" );
    	 sql.append("       ROWNUM\n" );
    	 sql.append("  FROM PT_BU_STOCK_OUT_DTL      A,\n" );
    	 sql.append("       PT_BA_INFO               B,\n" );
    	 sql.append("       PT_BA_WAREHOUSE_POSITION D\n" );
    	 sql.append(" WHERE 1 = 1\n" );
    	 sql.append("   AND A.OUT_ID = "+ORDER_ID+"\n" );
    	 sql.append("   AND A.PART_ID = B.PART_ID\n" );
    	 sql.append("   AND A.POSITION_ID = D.POSITION_ID");
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("UNIT", DicConstant.JLDW);
        return bs;
    }
    
    
    public QuerySet getOld(String OUT_ID,String DTL_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.OUT_AMOUNT, T1.SALE_PRICE, T1.PLAN_PRICE,T2.WAREHOUSE_ID,T.PART_ID,T2.ORDER_ID,T.KEEP_MAN\n" );
    	sql.append("  FROM PT_BU_STOCK_OUT_DTL T,PT_BA_INFO T1,PT_BU_STOCK_OUT T2\n" );
    	sql.append("  WHERE T.OUT_ID ="+OUT_ID+" AND T.PART_ID = T1.PART_ID AND T.OUT_ID = T2.OUT_ID\n" );
    	sql.append("  AND T.DETAIL_ID ="+DTL_ID+"");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
    
    public void updateOutDtl(String OUT_ID,String DTL_ID,String COUNT,String SALE_AMOUNT,String PLAN_AMOUNT,User user) throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("UPDATE PT_BU_STOCK_OUT_DTL T\n" );
    	sql.append("   SET T.OUT_AMOUNT ="+COUNT+", T.SALE_AMOUNT = "+SALE_AMOUNT+", T.PLAN_AMOUNT = "+PLAN_AMOUNT+",T.UPDATE_TIME = SYSDATE,T.UPDATE_USER = '"+user.getAccount()+"'\n" );
    	sql.append(" WHERE T.DETAIL_ID = "+DTL_ID+"\n" );
    	sql.append("   AND T.OUT_ID = "+OUT_ID+"");
       factory.update(sql.toString(),null);
    }
    
    public void updateStock(String WAREHOUSE_ID,String PART_ID,String DEF_COUNT,User user) throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("UPDATE PT_BU_STOCK T\n" );
    	sql.append("   SET T.AVAILABLE_AMOUNT = T.AVAILABLE_AMOUNT + "+DEF_COUNT+",\n" );
    	sql.append("       T.AMOUNT = 	T.AMOUNT + "+DEF_COUNT+",\n" );
    	sql.append("       T.UPDATE_TIME      = SYSDATE,\n" );
    	sql.append("       T.UPDATE_USER      = '"+user.getAccount()+"'\n" );
    	sql.append(" WHERE T.PART_ID = "+PART_ID+"\n" );
    	sql.append("   AND T.WAREHOUSE_ID = "+WAREHOUSE_ID+"");
       factory.update(sql.toString(),null);
    }
    
    public void updateStockDtl(String PART_ID,String POSITION_ID,String DEF_COUNT,User user) throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("UPDATE PT_BU_STOCK_DTL T\n" );
    	sql.append("   SET T.AVAILABLE_AMOUNT = T.AVAILABLE_AMOUNT + "+DEF_COUNT+",\n" );
    	sql.append("       T.AMOUNT = T.AMOUNT + "+DEF_COUNT+",\n" );
    	sql.append("       T.UPDATE_TIME      = SYSDATE,\n" );
    	sql.append("       T.UPDATE_USER      = '"+user.getAccount()+"'\n" );
    	sql.append(" WHERE T.PART_ID = "+PART_ID+"\n" );
    	sql.append("   AND T.POSITION_ID = "+POSITION_ID+"\n");
       factory.update(sql.toString(),null);
    }
    
    public void deleteBoxNo(String PART_ID,String ORDER_ID) throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("DELETE FROM PT_BU_BOX_UP WHERE PART_ID = "+PART_ID+" AND ORDER_ID = "+ORDER_ID+" ");
       factory.update(sql.toString(),null);
    }
    
    public void updateIssueOrderDtl(String ORDER_ID,String PART_ID,String POSITION_ID,String COUNT,User user) throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("UPDATE PT_BU_ISSUE_ORDER_DTL T\n" );
    	sql.append("   SET T.REAL_COUNT = "+COUNT+",\n" );
    	sql.append("       T.UPDATE_TIME      = SYSDATE,\n" );
    	sql.append("       T.UPDATE_USER      = '"+user.getAccount()+"'\n" );
    	sql.append(" WHERE T.PART_ID = "+PART_ID+"\n" );
    	sql.append("   AND T.POSITION_ID = "+POSITION_ID+"\n");
    	sql.append("   AND T.ORDER_ID = "+ORDER_ID+"\n");
       factory.update(sql.toString(),null);
    }
    
    public void updateSaleOrder(String PART_ID,String ORDER_ID,String COUNT,User user) throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("UPDATE PT_BU_ISSUE_ORDER_DTL T\n" );
    	sql.append("   SET T.REAL_COUN = "+COUNT+", T.UPDATE_USER = "+user.getAccount()+",T.UPDATE_TIME = SYSDATE\n" );
    	sql.append(" WHERE T.ORDER_ID = "+ORDER_ID+"\n" );
    	sql.append("   AND PART_ID = "+PART_ID+"");
       factory.update(sql.toString(),null);
    }
    public boolean updatePartSaleOrder(PtBuSaleOrderVO vo)
            throws Exception {
        return factory.update(vo);
    }
    public QuerySet checkBox(String PART_ID,String ORDER_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT 1 FROM PT_BU_BOX_UP T\n" );
    	sql.append("  WHERE T.PART_ID ="+PART_ID+" AND T.ORDER_ID = "+ORDER_ID+"\n" );
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
    
    public QuerySet getOutAmount(String outId)throws Exception
    {
        QuerySet qs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT SUM(NVL(T.OUT_AMOUNT,0)) OUT_AMOUNT\n" );
        sql.append("FROM PT_BU_STOCK_OUT_DTL T\n" );
        sql.append("WHERE T.OUT_ID = "+outId+"\n");
        qs = factory.select(null, sql.toString());
        return qs;
    }
    
    public void orderReleaseFreez(String orderId,User user)
            throws Exception {
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
    public boolean insertLog(PtBuStockOutModLogVO vo)
            throws Exception {
        return factory.insert(vo);
    }
    
    public QuerySet checkLock1(String partIds,String warehouseId)
            throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT to_char(wm_concat( PART_CODE)) CODES FROM PT_BU_STOCK WHERE PART_ID IN ("+partIds+") AND STOCK_STATUS = "+DicConstant.KCZT_02+" AND WAREHOUSE_ID = "+warehouseId+"\n" );
        return factory.select(null, sql.toString());
    }
    public QuerySet checkLock2(String partIds,String warehouseId)
            throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT to_char(wm_concat( PART_CODE)) CODES FROM PT_BU_STOCK WHERE PART_ID IN ("+partIds+") AND STOCK_STATUS = "+DicConstant.KCZT_03+" AND WAREHOUSE_ID = "+warehouseId+"\n" );
        return factory.select(null, sql.toString());
    }
}
