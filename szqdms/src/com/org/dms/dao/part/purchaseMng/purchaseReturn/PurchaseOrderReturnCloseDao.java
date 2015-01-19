package com.org.dms.dao.part.purchaseMng.purchaseReturn;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuPchReturnVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class PurchaseOrderReturnCloseDao extends BaseDAO{
	public  static final PurchaseOrderReturnCloseDao getInstance(ActionContext atx)
    {
		PurchaseOrderReturnCloseDao dao = new PurchaseOrderReturnCloseDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	
	public BaseResultSet returnOrderSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres +="AND T.STATUS = "+DicConstant.YXBS_01+"\n" +
				"AND T.ORDER_USER = '"+user.getAccount()+"'\n" + 
				"AND T.RETURN_STATUS IN( "+DicConstant.CGTHDZT_01+","+DicConstant.CGTHDZT_02+","+DicConstant.CGTHDZT_03+")"+ 
				"ORDER BY RETURN_NO DESC\n" ;
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.RETURN_ID,\n" );
    	sql.append("       T.RETURN_NO,\n" );
    	sql.append("       T.PURCHASE_NO,\n" );
    	sql.append("       T.PURCHASE_ID,\n" );
    	sql.append("       T.IS_AGREED,\n" );
    	sql.append("       T.ADVICE,\n" );
    	sql.append("       T.SUPPLIER_CODE,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("	   T.SUPPLIER_ID,");
    	sql.append("       T.RETURN_TYPE,\n" );
    	sql.append("       T.ORDER_DATE,\n" );
    	sql.append("       T.ORDER_USER\n" );
    	sql.append("  FROM PT_BU_PCH_RETURN T");
    	bs = factory.select(sql.toString(), page);
		bs.setFieldDic("RETURN_TYPE", "CGTHLX");
		bs.setFieldDateFormat("ORDER_DATE", "yyyy-MM-dd");
		bs.setFieldUserID("ORDER_USER");
    	return bs;
    }
	public QuerySet getWareInfo(String RETURN_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append(" SELECT T.WAREHOUSE_ID,T.WAREHOUSE_CODE,T.WAREHOUSE_NAME,T1.RETURN_NO FROM PT_BA_WAREHOUSE T,PT_BU_PCH_RETURN T1 WHERE T.WAREHOUSE_TYPE = 100101 AND T.WAREHOUSE_STATUS = "+DicConstant.YXBS_01+" AND T1.RETURN_ID = "+RETURN_ID+" " );
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	public QuerySet checkOut(String RETURN_ID) throws Exception {
        QuerySet qs = null;
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT OUT_ID FROM PT_BU_STOCK_OUT WHERE ORDER_ID = "+RETURN_ID+"");
        qs = factory.select(null, sql.toString());
        return qs;
    }
    public boolean updateRet(PtBuPchReturnVO vo)throws Exception {
        
        return factory.update(vo);
    }
    public boolean updateStockDtl(String OUT_ID, User user)
            throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE PT_BU_STOCK_DTL A\n");
        sql.append("   SET A.OCCUPY_AMOUNT    = OCCUPY_AMOUNT -\n");
        sql.append("                            (SELECT B.OUT_AMOUNT\n");
        sql.append("                               FROM PT_BU_STOCK_OUT_DTL B\n");
        sql.append("                              WHERE B.OUT_ID = " + OUT_ID + "\n");
        sql.append("                                AND B.POSITION_ID = A.POSITION_ID\n");
        sql.append("                                AND B.SUPPLIER_ID = A.SUPPLIER_ID\n");
        sql.append("                                AND B.PART_ID = A.PART_ID),\n");
        sql.append("       A.AMOUNT = AMOUNT -\n");
        sql.append("                            (SELECT B.OUT_AMOUNT\n");
        sql.append("                               FROM PT_BU_STOCK_OUT_DTL B\n");
        sql.append("                              WHERE B.OUT_ID = " + OUT_ID + "\n");
        sql.append("                                AND B.POSITION_ID = A.POSITION_ID\n");
        sql.append("                                AND B.SUPPLIER_ID = A.SUPPLIER_ID\n");
        sql.append("                                AND B.PART_ID = A.PART_ID),\n");
        sql.append("       A.UPDATE_USER      = '" + user.getAccount() + "',\n");
        sql.append("       A.UPDATE_TIME      = SYSDATE\n");
        sql.append(" WHERE 1 = 1\n");
        sql.append("   AND EXISTS (SELECT 1\n");
        sql.append("          FROM PT_BU_STOCK_OUT_DTL B\n");
        sql.append("         WHERE B.OUT_ID = " + OUT_ID + "\n");
        sql.append("           AND B.POSITION_ID = A.POSITION_ID\n");
        sql.append("           AND B.SUPPLIER_ID = A.SUPPLIER_ID\n");
        sql.append("           AND B.PART_ID = A.PART_ID)\n");

        return factory.update(sql.toString(), null);
    }
    public boolean updateStock(String WAREHOUSE_ID, String OUT_ID, User user)
            throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE PT_BU_STOCK A\n");
        sql.append("   SET A.OCCUPY_AMOUNT    = OCCUPY_AMOUNT -\n");
        sql.append("                            (SELECT B.OUT_AMOUNT\n");
        sql.append("                               FROM PT_BU_STOCK_OUT_DTL B\n");
        sql.append("                              WHERE B.OUT_ID = " + OUT_ID + "\n");
        sql.append("                                AND B.SUPPLIER_ID = A.SUPPLIER_ID\n");
        sql.append("                                AND B.PART_ID = A.PART_ID),\n");
        sql.append("       A.AMOUNT = AMOUNT -\n");
        sql.append("                            (SELECT B.OUT_AMOUNT\n");
        sql.append("                               FROM PT_BU_STOCK_OUT_DTL B\n");
        sql.append("                              WHERE B.OUT_ID = " + OUT_ID + "\n");
        sql.append("                                AND B.SUPPLIER_ID = A.SUPPLIER_ID\n");
        sql.append("                                AND B.PART_ID = A.PART_ID),\n");
        sql.append("       A.UPDATE_USER      = '" + user.getAccount() + "',\n");
        sql.append("       A.UPDATE_TIME      = SYSDATE\n");
        sql.append(" WHERE 1 = 1\n");
        sql.append("   AND A.WAREHOUSE_ID = " + WAREHOUSE_ID + "\n");
        sql.append("   AND EXISTS (SELECT 1\n");
        sql.append("          FROM PT_BU_STOCK_OUT_DTL B\n");
        sql.append("         WHERE B.OUT_ID = " + OUT_ID + "\n");
        sql.append("           AND B.SUPPLIER_ID = A.SUPPLIER_ID\n");
        sql.append("           AND B.PART_ID = A.PART_ID)\n");

        return factory.update(sql.toString(), null);
    }
    
    public boolean updateStockDtl1(String OUT_ID,String RETURN_ID, User user)
            throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("UPDATE PT_BU_STOCK_DTL T\n" );
    	sql.append("   SET T.OCCUPY_AMOUNT    = NVL(T.OCCUPY_AMOUNT, 0) -\n" );
    	sql.append("                            (SELECT NVL(COUNT, 0)\n" );
    	sql.append("                               FROM PT_BU_PCH_RETURN_DTL T1\n" );
    	sql.append("                              WHERE T.PART_ID = T1.PART_ID\n" );
    	sql.append("                                AND T.POSITION_ID = T1.POSITION_ID\n" );
    	sql.append("                                AND T1.RETURN_ID = "+RETURN_ID+"),\n" );
    	sql.append("       T.AVAILABLE_AMOUNT = T.AVAILABLE_AMOUNT +\n" );
    	sql.append("                            (SELECT NVL(COUNT, 0)\n" );
    	sql.append("                               FROM PT_BU_PCH_RETURN_DTL T1\n" );
    	sql.append("                              WHERE T.PART_ID = T1.PART_ID\n" );
    	sql.append("                                AND T.POSITION_ID = T1.POSITION_ID\n" );
    	sql.append("                                AND T1.RETURN_ID = "+RETURN_ID+"),\n" );
    	sql.append("       T.UPDATE_USER      = '"+user.getAccount()+"',\n" );
    	sql.append("       T.UPDATE_TIME      = SYSDATE\n" );
    	sql.append(" WHERE T.PART_ID IN (SELECT PART_ID\n" );
    	sql.append("                       FROM PT_BU_PCH_RETURN_DTL\n" );
    	sql.append("                      WHERE RETURN_ID = "+RETURN_ID+")\n" );
    	sql.append("                         AND T.PART_ID NOT IN\n" );
    	sql.append("       (SELECT PART_ID FROM PT_BU_STOCK_OUT_DTL WHERE OUT_ID = "+OUT_ID+")");
        return factory.update(sql.toString(), null);
    }
    public boolean updateStock1(String WAREHOUSE_ID, String OUT_ID,String RETURN_ID, User user)
            throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("UPDATE PT_BU_STOCK T\n" );
    	sql.append("   SET T.OCCUPY_AMOUNT    = NVL(T.OCCUPY_AMOUNT, 0) -\n" );
    	sql.append("                            (SELECT NVL(COUNT, 0)\n" );
    	sql.append("                               FROM PT_BU_PCH_RETURN_DTL T1\n" );
    	sql.append("                              WHERE T.PART_ID = T1.PART_ID\n" );
    	sql.append("                                AND T1.RETURN_ID = "+RETURN_ID+"),\n" );
    	sql.append("       T.AVAILABLE_AMOUNT = T.AVAILABLE_AMOUNT +\n" );
    	sql.append("                            (SELECT NVL(COUNT, 0)\n" );
    	sql.append("                               FROM PT_BU_PCH_RETURN_DTL T1\n" );
    	sql.append("                              WHERE T.PART_ID = T1.PART_ID\n" );
    	sql.append("                                AND T1.RETURN_ID = "+RETURN_ID+"),\n" );
    	sql.append("       T.UPDATE_USER      = '"+user.getAccount()+"',\n" );
    	sql.append("       T.UPDATE_TIME      = SYSDATE\n" );
    	sql.append(" WHERE T.WAREHOUSE_ID = "+WAREHOUSE_ID+"\n" );
    	sql.append("   AND T.PART_ID IN (SELECT PART_ID\n" );
    	sql.append("                       FROM PT_BU_PCH_RETURN_DTL\n" );
    	sql.append("                      WHERE RETURN_ID = "+RETURN_ID+")\n" );
    	sql.append("   AND T.PART_ID NOT IN\n" );
    	sql.append("       (SELECT PART_ID FROM PT_BU_STOCK_OUT_DTL WHERE OUT_ID = "+OUT_ID+")");
        return factory.update(sql.toString(), null);
    }
    public boolean insertOutFlow(String OUT_ID, User user) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO PT_BU_STOCK_OUT_CONTINUAL\n");
        sql.append("  (CONTINUAL_ID,\n");
        sql.append("   CONTINUAL_NO,\n");
        sql.append("   OUT_ID,\n");
        sql.append("   OUT_NO,\n");
        sql.append("   PART_ID,\n");
        sql.append("   PART_CODE,\n");
        sql.append("   PART_NAME,\n");
        sql.append("   OUT_DATE,\n");
        sql.append("   KEEPER_MAN,\n");
        sql.append("   OUT_COUNT,\n");
        sql.append("   CREATE_MAN)\n");
        sql.append("  SELECT F_GETID(),\n");
        sql.append("         F_GETOUT_CONTINUAL(),\n");
        sql.append("         A.OUT_ID,\n");
        sql.append("         B.OUT_NO,\n");
        sql.append("         A.PART_ID,\n");
        sql.append("         A.PART_CODE,\n");
        sql.append("         A.PART_NAME,\n");
        sql.append("         SYSDATE,\n");
        sql.append("         A.KEEP_MAN,\n");
        sql.append("         A.OUT_AMOUNT ,\n");
        sql.append("         '" + user.getAccount() + "'\n");
        sql.append("    FROM PT_BU_STOCK_OUT_DTL A, PT_BU_STOCK_OUT B\n");
        sql.append("   WHERE A.OUT_ID = B.OUT_ID\n");
        sql.append("   AND A.OUT_ID = " + OUT_ID + "\n");
        return factory.update(sql.toString(), null);
    }
    
    
    
    public boolean updateStockNoOut(String WAREHOUSE_ID, String RETURN_ID, User user)
            throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("UPDATE PT_BU_STOCK T\n" );
    	sql.append("   SET T.OCCUPY_AMOUNT    = NVL(T.OCCUPY_AMOUNT, 0) -\n" );
    	sql.append("                            (SELECT NVL(COUNT, 0)\n" );
    	sql.append("                               FROM PT_BU_PCH_RETURN_DTL T1\n" );
    	sql.append("                              WHERE T.PART_ID = T1.PART_ID\n" );
    	sql.append("                                AND T1.RETURN_ID = "+RETURN_ID+"),\n" );
    	sql.append("       T.AVAILABLE_AMOUNT = T.AVAILABLE_AMOUNT +\n" );
    	sql.append("                            (SELECT NVL(COUNT, 0)\n" );
    	sql.append("                               FROM PT_BU_PCH_RETURN_DTL T1\n" );
    	sql.append("                              WHERE T.PART_ID = T1.PART_ID\n" );
    	sql.append("                                AND T1.RETURN_ID = "+RETURN_ID+"),\n" );
    	sql.append("       T.UPDATE_USER      = '"+user.getAccount()+"',\n" );
    	sql.append("       T.UPDATE_TIME      = SYSDATE\n" );
    	sql.append(" WHERE T.WAREHOUSE_ID = "+WAREHOUSE_ID+"\n" );
    	sql.append("   AND T.PART_ID IN (SELECT PART_ID\n" );
    	sql.append("                       FROM PT_BU_PCH_RETURN_DTL\n" );
    	sql.append("                      WHERE RETURN_ID = "+RETURN_ID+")");
        return factory.update(sql.toString(), null);
    }
    
    public boolean updateStockDtlNoOut(String RETURN_ID, User user)
            throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("UPDATE PT_BU_STOCK_DTL T\n" );
    	sql.append("   SET T.OCCUPY_AMOUNT    = NVL(T.OCCUPY_AMOUNT, 0) -\n" );
    	sql.append("                            (SELECT NVL(COUNT, 0)\n" );
    	sql.append("                               FROM PT_BU_PCH_RETURN_DTL T1\n" );
    	sql.append("                              WHERE T.PART_ID = T1.PART_ID\n" );
    	sql.append("                                AND T.POSITION_ID = T1.POSITION_ID\n" );
    	sql.append("                                AND T1.RETURN_ID = "+RETURN_ID+"),\n" );
    	sql.append("       T.AVAILABLE_AMOUNT = T.AVAILABLE_AMOUNT +\n" );
    	sql.append("                            (SELECT NVL(COUNT, 0)\n" );
    	sql.append("                               FROM PT_BU_PCH_RETURN_DTL T1\n" );
    	sql.append("                              WHERE T.PART_ID = T1.PART_ID\n" );
    	sql.append("                                AND T.POSITION_ID = T1.POSITION_ID\n" );
    	sql.append("                                AND T1.RETURN_ID = "+RETURN_ID+"),\n" );
    	sql.append("       T.UPDATE_USER      = '"+user.getAccount()+"',\n" );
    	sql.append("       T.UPDATE_TIME      = SYSDATE\n" );
    	sql.append(" WHERE T.PART_ID IN (SELECT PART_ID\n" );
    	sql.append("                       FROM PT_BU_PCH_RETURN_DTL\n" );
    	sql.append("                      WHERE RETURN_ID = "+RETURN_ID+")");
        return factory.update(sql.toString(), null);
    }

}
