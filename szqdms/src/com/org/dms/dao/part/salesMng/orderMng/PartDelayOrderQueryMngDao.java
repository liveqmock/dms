package com.org.dms.dao.part.salesMng.orderMng;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 延期订单查询Dao
 *
 * 延期订单查询
 * @author zhengyao
 * @date 2014-10-28
 * @version 1.0
 */
public class PartDelayOrderQueryMngDao extends BaseDAO {

    /**
     * 定义instance
     *
     * @param pActionContext
     * @return warehouseMngDao 延期订单查询Dao
     */
    public static final PartDelayOrderQueryMngDao getInstance(ActionContext pActionContext) {

        PartDelayOrderQueryMngDao partDelayOrderQueryMngDao = new PartDelayOrderQueryMngDao();
        pActionContext.setDBFactory(partDelayOrderQueryMngDao.factory);
        return partDelayOrderQueryMngDao;
    }

    /**
     * 延期订单查询(导出)
     *
     * @pConditions 查询条件
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet download(String pConditions) throws Exception {
    	String sql =    "SELECT\n" +
				"       B.PART_CODE, B.PART_NAME, T1.DIC_VALUE PART_STATUS,  T.DIC_VALUE UNIT, T2.PERSON_NAME USER_ACCOUNT, C.WAREHOUSE_CODE, C.WAREHOUSE_NAME, A.ORDER_COUNT,\n" + 
				"       D.AMOUNT, B.MIN_UNIT, B.MIN_PACK, DECODE(SIGN(A.ORDER_COUNT - D.AMOUNT), 1, A.ORDER_COUNT - D.AMOUNT, 0) COUNT\n" + 
				" FROM PT_BA_INFO B,\n" + 
				"     (\n" + 
				"      SELECT DT.PART_ID, SUM(DT.ORDER_COUNT) ORDER_COUNT, SO.WAREHOUSE_ID\n" + 
				"        FROM PT_BU_SALE_ORDER_DTL DT, PT_BU_SALE_ORDER SO\n" + 
				"       WHERE SO.ORDER_ID =DT.ORDER_ID AND SO.ORDER_STATUS="+DicConstant.DDZT_02+" AND SO.IF_DELAY_ORDER = 100101 AND SO.IF_CHANEL_ORDER=100102" + 
				"       GROUP BY DT.PART_ID, SO.WAREHOUSE_ID\n" + 
				"      ) A,\n" + 
				"      (\n" + 
				"      SELECT ST.PART_ID, SUM(ST.AVAILABLE_AMOUNT) AMOUNT\n" + 
				"        FROM PT_BU_STOCK ST\n" + 
				"       GROUP BY ST.PART_ID\n" + 
				"      ) D,\n" + 
				"      PT_BA_WAREHOUSE_KEEPER C,DIC_TREE T,DIC_TREE T1,TM_USER T2\n" + 
				"WHERE A.PART_ID = B.PART_ID\n" + 
				"   AND A.PART_ID = C.PART_ID\n" + 
				"   AND A.PART_ID = D.PART_ID" +
				
				"   AND B.PART_STATUS = T1.ID" +
				"   AND B.UNIT = T.ID" +
				"   AND C.USER_ACCOUNT = T2.ACCOUNT" +
				" 	AND C.WAREHOUSE_ID = A.WAREHOUSE_ID AND "+ pConditions;

        //执行方法，不需要传递conn参数
        return factory.select(null, sql );
    }

    /**
     * 延期订单表(PT_BU_SALE_ORDER)查询
     *
     * @param pConditions 月份
     * @param pUser 当前登录user对象
     * @return QuerySet 结果集
     * @throws Exception
     */
    public BaseResultSet searchOrder(PageManager pPageManager, User pUser,String pConditions,String conditions) throws Exception {
    	String sql =    "SELECT\n" +
    					"       B.PART_CODE, B.PART_NAME, B.PART_STATUS,  B.UNIT, C.USER_ACCOUNT, C.WAREHOUSE_CODE, C.WAREHOUSE_NAME, A.ORDER_COUNT,\n" + 
    					"       D.AMOUNT, B.MIN_UNIT, B.MIN_PACK, DECODE(SIGN(A.ORDER_COUNT - D.AMOUNT), 1, A.ORDER_COUNT - D.AMOUNT, 0) COUNT\n" + 
    					" FROM PT_BA_INFO B,\n" + 
    					"     (\n" + 
    					"      SELECT DT.PART_ID, SUM(DT.ORDER_COUNT) ORDER_COUNT, SO.WAREHOUSE_ID\n" + 
    					"        FROM PT_BU_SALE_ORDER_DTL DT, PT_BU_SALE_ORDER SO\n" + 
    					"       WHERE SO.ORDER_ID =DT.ORDER_ID AND SO.ORDER_STATUS="+DicConstant.DDZT_02+" AND SO.IF_DELAY_ORDER = 100101 AND SO.IF_CHANEL_ORDER=100102" + 
    					"       GROUP BY DT.PART_ID, SO.WAREHOUSE_ID\n" + 
    					"      ) A,\n" + 
    					"      (\n" + 
    					"      SELECT ST.PART_ID, SUM(ST.AVAILABLE_AMOUNT) AMOUNT\n" + 
    					"        FROM PT_BU_STOCK ST\n" + 
    					"       GROUP BY ST.PART_ID\n" + 
    					"      ) D,\n" + 
    					"      PT_BA_WAREHOUSE_KEEPER C\n" + 
    					"WHERE A.PART_ID = B.PART_ID\n" + 
    					"   AND A.PART_ID = C.PART_ID\n" + 
    					"   AND A.PART_ID = D.PART_ID" +
    					" 	AND C.WAREHOUSE_ID = A.WAREHOUSE_ID AND "+ pConditions;
        //执行方法，不需要传递conn参数
        return factory.select(sql, pPageManager);
    }

    /**
     * 获取条件
     *
     * @param pUser 当前登录user对象
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet getWhere(User user) throws Exception {

        StringBuilder sql= new StringBuilder();
        sql.append("SELECT ' AND C.WAREHOUSE_CODE' || CASE\n" );
        sql.append("         WHEN IS_AM = '" + DicConstant.SF_01 + "' THEN\n" );
        sql.append("          '='\n" );
        sql.append("         ELSE\n" );
        sql.append("          '<>'\n" );
        sql.append("       END ||''''||\n" );
        sql.append("       (SELECT WAREHOUSE_CODE FROM PT_BA_WAREHOUSE WHERE WAREHOUSE_TYPE = (SELECT PARAKEY FROM USER_PARA_CONFIGURE WHERE PARAKEY = '100102'))||'''' AS T\n" );
        sql.append("  FROM TM_ORG\n" );
        sql.append(" WHERE ORG_ID = '"+user.getOrgId()+"'");
        //执行方法，不需要传递conn参数
        return factory.select(null,sql.toString());
    }

    /**
     * 订单明细表(PT_BU_SALE_ORDER_DTL)查询
     *
     * @param pPageManager 查询分页对象
     * @param pUser 当前登录user对象
     * @param pConditions sql条件(默认：1=1)
     * @return QuerySet 结果集
     * @throws Exception
     */
    public BaseResultSet searchOrderDtl(PageManager pPageManager, String pConditions) throws Exception {

    	String wheres = pConditions;
    	wheres += " AND A.PART_ID = B.PART_ID ";
    	pPageManager.setFilter(wheres);
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
    	bs = factory.select(sql.toString(), pPageManager);
        //执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(),pPageManager);
        return bs;
    }
}
