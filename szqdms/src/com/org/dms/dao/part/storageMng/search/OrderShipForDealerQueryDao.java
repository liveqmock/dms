package com.org.dms.dao.part.storageMng.search;

import java.sql.SQLException;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 
 * ClassName: OrderShipForDealerQueryDao 
 * Function: 承运信息查询Dao
 * date: 2014年11月28日 下午1:04:59
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class OrderShipForDealerQueryDao extends BaseDAO {
	
	public static final OrderShipForDealerQueryDao getInstance(ActionContext ac){
		OrderShipForDealerQueryDao dao = new OrderShipForDealerQueryDao();
		ac.setDBFactory(dao.factory);
		return dao;
	}
	

	/**
	 * 
	 * queryList: 主table信息查询
	 * @author fuxiao
	 * Date:2014年11月28日
	 *
	 */
	public BaseResultSet queryList(PageManager pageManager, String conditions, User user) throws Exception{
		BaseResultSet rs = null;
		String sql = 
					"SELECT A.SHIP_ID,\n" +
					"       A.SHIP_NO,\n" + 
					"       A.SHIP_STATUS,\n" + 
					"       A.CREATE_USER,\n" + 
					"       A.CREATE_TIME,\n" + 
					"       A.REMARKS\n" + 
					"  FROM PT_BU_ORDER_SHIP A\n" + 
					" WHERE A.CARRIER_ID =\n" + 
					"       (SELECT CARRIER_ID FROM PT_BA_CARRIER WHERE ORG_ID = '"+user.getOrgId()+"')\n" + 
					"   AND A.STATUS = 100201\n" + 
					"	AND " + conditions +
					" ORDER BY A.SHIP_ID DESC";
		rs =  this.factory.select(sql, pageManager);
		rs.setFieldDic("SHIP_STATUS", "FYDZT");		
		rs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
		rs.setFieldUserID("CREATE_USER");
		return rs;
	}
	
	/**
	 * 
	 * queryList: 添加订单号搜索
	 * @author fuxiao
	 * Date:2014年11月14日
	 *
	 */
	public BaseResultSet queryList(PageManager pageManager, String conditions, User user, boolean isSub) throws Exception{
		// 判断是否需要对查询条件做转换
		if( isSub ){
			conditions = this.convertStr(conditions);
		}
		return this.queryList(pageManager, conditions, user);
	}
	
	/**
	 * 
	 * convertStr: 对查询条件做转换
	 * @author fuxiao
	 * Date:2014年11月28日
	 *
	 */
	public String convertStr(String conditions) {
		
		// 对订单号条件转换
		if(conditions.indexOf("ORDER_NO") != -1){
			conditions += " AND EXISTS (\n" +
							"    SELECT 1 FROM PT_BU_SALE_ORDER O, PT_BU_ORDER_SHIP_DTL D  WHERE O.ORDER_ID = D.ORDER_ID AND D.SHIP_ID = A.SHIP_ID ";
			String partNameSql = conditions.substring(conditions.indexOf("ORDER_NO"), conditions.indexOf("'", conditions.indexOf("ORDER_NO") + 15) + 1);
			conditions = conditions.replace(" AND "+partNameSql, "");
			conditions +=" AND " + partNameSql;
			conditions += " ) ";
		}
		
		return conditions;
	}
	
	/**
	 * 
	 * queryInfoById: 根据ID查询主信息
	 * @author fuxiao
	 * Date:2014年11月28日
	 *
	 */
	public QuerySet queryInfoById(String id) throws SQLException{
		String sql = 
				"SELECT A.SHIP_ID,\n" +
				"       A.SHIP_NO,\n" + 
				"       A.SHIP_STATUS,\n" + 
				"       A.CREATE_USER,\n" + 
				"       A.CREATE_TIME,\n" + 
				"       A.REMARKS\n" + 
				"  FROM PT_BU_ORDER_SHIP A\n" + 
				" WHERE A.SHIP_ID = ?" ;
		return this.factory.select(new Object[]{ id }, sql);
	}


	public BaseResultSet shipSearch(PageManager page, User user, String conditions,String ORDER_NO) throws Exception
    {
    	String wheres = conditions;
    	if(!"".equals(ORDER_NO)&&ORDER_NO!=null){
    		wheres +="AND  T.SHIP_ID = T1.SHIP_ID AND T.SHIP_STATUS >"+DicConstant.FYDZT_03+"\n" +
					"   AND EXISTS (SELECT 1\n" + 
					"          FROM PT_BU_ORDER_SHIP_DTL A, PT_BU_SALE_ORDER B\n" + 
					"         WHERE A.ORDER_ID = B.ORDER_ID\n" + 
					"           AND A.SHIP_ID = T.SHIP_ID\n" + 
					"           AND B.ORDER_NO LIKE '%"+ORDER_NO+"%'\n" + 
					"           AND B.ORG_ID = '"+user.getOrgId()+"')";
    	}else{
    		wheres +="AND  T.SHIP_ID = T1.SHIP_ID AND T.SHIP_STATUS >"+DicConstant.FYDZT_03+"\n" +
					"   AND EXISTS (SELECT 1\n" + 
					"          FROM PT_BU_ORDER_SHIP_DTL A, PT_BU_SALE_ORDER B\n" + 
					"         WHERE A.ORDER_ID = B.ORDER_ID\n" + 
					"           AND A.SHIP_ID = T.SHIP_ID\n" + 
					"           AND B.ORG_ID = '"+user.getOrgId()+"')";
    	}
    	
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.SHIP_ID,\n" );
    	sql.append("       T.SHIP_NO,\n" );
    	sql.append("       T.SHIP_STATUS,\n" );
    	sql.append("       T.SHIP_DATE,\n" );
    	sql.append("       T.CREATE_TIME,\n" );
    	sql.append("       T.CREATE_USER,\n" );
    	sql.append("       T1.RECEIPT_NO,\n" );
    	sql.append("       T1.DEL_ID,\n" );
    	sql.append("       T1.LICENSE_PLATE\n" );
    	sql.append("  FROM PT_BU_ORDER_SHIP T, PT_BU_ORDER_SHIP_CARRIER T1");
    	bs = factory.select(sql.toString(), page);
		bs.setFieldDic("SHIP_STATUS", "FYDZT");
		bs.setFieldDateFormat("SHIP_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
		bs.setFieldUserID("CREATE_USER");
    	return bs;
    }
	
	/**
	 * 
	 * queryDriverInfoById: 查询司机信息
	 * @author fuxiao
	 * Date:2014年11月28日
	 *
	 */
	public BaseResultSet queryDriverInfoById(PageManager pageManage, String conditions) throws Exception {
		String sql = "SELECT A.DEL_ID,\n" +
						"       A.DRIVER_ID,\n" + 
						"       A.DRIVER_NAME,\n" + 
						"       A.DRIVER_PHONE,\n" + 
						"       A.VEHICLE_ID,\n" + 
						"       A.LICENSE_PLATE\n" + 
						"  FROM PT_BU_ORDER_SHIP_CARRIER A WHERE " + conditions;
		return this.factory.select(sql, pageManage);
	}
	
	/**
	 * 
	 * queryStockInfoById:查询出库单详细信息
	 * @author fuxiao
	 * Date:2014年10月23日
	 *
	 */
	public BaseResultSet queryOrderInfoById(PageManager pageManage, String conditions) throws Exception {
		String sql = 
						"SELECT A.ORDER_ID,\n" +
						"       A.ORDER_NO,\n" + 
						"       A.ORDER_TYPE,\n" + 
						"       A.ORG_NAME,\n" + 
						"       SUM(NVL(C.COUNT, 0)) COUNT,\n" + 
						"       SUM(NVL(C.COUNT, 0) * B.UNIT_PRICE) AMOUNT,\n" + 
						"       A.DELIVERY_ADDR,\n" + 
						"       A.LINK_MAN,\n" + 
						"       A.PHONE,\n" + 
						"       D.DTL_ID\n" + 
						"  FROM PT_BU_SALE_ORDER     A,\n" + 
						"       PT_BU_SALE_ORDER_DTL B,\n" + 
						"       PT_BU_BOX_UP         C,\n" + 
						"       PT_BU_ORDER_SHIP_DTL D\n" + 
						" WHERE 1 = 1\n" + 
						"   AND A.ORDER_ID = B.ORDER_ID\n" + 
						"   AND A.ORDER_ID = C.ORDER_ID\n" + 
						"   AND B.PART_ID = C.PART_ID\n" + 
						"   AND A.ORDER_ID = D.ORDER_ID\n" + 
						"   AND " + conditions +
						"   AND A.STATUS = 100201\n" + 
						" GROUP BY A.ORDER_ID,\n" + 
						"          A.ORDER_NO,\n" + 
						"          A.ORDER_TYPE,\n" + 
						"          A.ORG_NAME,\n" + 
						"          A.DELIVERY_ADDR,\n" + 
						"          A.LINK_MAN,\n" + 
						"          A.PHONE,\n" + 
						"          A.CREATE_TIME,\n" + 
						"          D.DTL_ID\n" + 
						" ORDER BY A.ORDER_ID DESC";

		return this.factory.select(sql, pageManage);
		
	}
	
	public QuerySet getType(User user) throws Exception
	 {
		 QuerySet qs = null;
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT ORG_TYPE FROM TM_ORG WHERE ORG_ID = "+user.getOrgId()+"");
		 qs = factory.select(null, sql.toString());
		 return qs;
	 }
	
	public BaseResultSet shipSearchOem(PageManager page, User user, String conditions,String ORDER_NO) throws Exception
    {
    	String wheres = conditions;
    	if(!"".equals(ORDER_NO)&&ORDER_NO!=null){
    		wheres +="AND  T.SHIP_ID = T1.SHIP_ID\n" +
					"   AND EXISTS (SELECT 1\n" + 
					"          FROM PT_BU_ORDER_SHIP_DTL A, PT_BU_SALE_ORDER B\n" + 
					"         WHERE A.ORDER_ID = B.ORDER_ID\n" + 
					"           AND A.SHIP_ID = T.SHIP_ID\n" + 
					"           AND B.ORDER_NO LIKE '%"+ORDER_NO+"%')\n";
    	}else{
    		wheres +="AND  T.SHIP_ID = T1.SHIP_ID\n";
    	}
    	
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.SHIP_ID,\n" );
    	sql.append("       T.SHIP_NO,\n" );
    	sql.append("       T.SHIP_STATUS,\n" );
    	sql.append("       T.SHIP_DATE,\n" );
    	sql.append("       T.CREATE_TIME,\n" );
    	sql.append("       T.CREATE_USER,\n" );
    	sql.append("       T1.RECEIPT_NO,\n" );
    	sql.append("       T1.DEL_ID,\n" );
    	sql.append("       T1.LICENSE_PLATE\n" );
    	sql.append("  FROM PT_BU_ORDER_SHIP T, PT_BU_ORDER_SHIP_CARRIER T1");
    	bs = factory.select(sql.toString(), page);
		bs.setFieldDic("SHIP_STATUS", "FYDZT");
		bs.setFieldDateFormat("SHIP_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
		bs.setFieldUserID("CREATE_USER");
    	return bs;
    }

	public BaseResultSet shipSearchCar(PageManager page, User user, String conditions,String ORDER_NO) throws Exception
    {
    	String wheres = conditions;
    	if(!"".equals(ORDER_NO)&&ORDER_NO!=null){
    		wheres +="AND  T.SHIP_ID = T1.SHIP_ID AND T.CARRIER_ID = T2.CARRIER_ID AND T2.ORG_ID = "+user.getOrgId()+"\n" +
					"   AND EXISTS (SELECT 1\n" + 
					"          FROM PT_BU_ORDER_SHIP_DTL A, PT_BU_SALE_ORDER B\n" + 
					"         WHERE A.ORDER_ID = B.ORDER_ID\n" + 
					"           AND A.SHIP_ID = T.SHIP_ID\n" + 
					"           AND B.ORDER_NO LIKE '%"+ORDER_NO+"%')\n";
    	}else{
    		wheres +="AND  T.SHIP_ID = T1.SHIP_ID AND T.CARRIER_ID = T2.CARRIER_ID AND T2.ORG_ID = "+user.getOrgId()+"\n" ;
    	}
    	
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.SHIP_ID,\n" );
    	sql.append("       T.SHIP_NO,\n" );
    	sql.append("       T.SHIP_STATUS,\n" );
    	sql.append("       T.SHIP_DATE,\n" );
    	sql.append("       T.CREATE_TIME,\n" );
    	sql.append("       T.CREATE_USER,\n" );
    	sql.append("       T1.RECEIPT_NO,\n" );
    	sql.append("       T1.DEL_ID,\n" );
    	sql.append("       T1.LICENSE_PLATE\n" );
    	sql.append("  FROM PT_BU_ORDER_SHIP T, PT_BU_ORDER_SHIP_CARRIER T1,PT_BA_CARRIER T2");
    	bs = factory.select(sql.toString(), page);
		bs.setFieldDic("SHIP_STATUS", "FYDZT");
		bs.setFieldDateFormat("SHIP_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
		bs.setFieldUserID("CREATE_USER");
    	return bs;
    }
	
	
	/**
	 * 
	 * @Title: queryDownInfo1
	 * @Description: 下载信息
	 * @param conditions
	 * @param user
	 * @param orderNo
	 * @return
	 * @throws Exception
	 * @return: QuerySet
	 */
	public QuerySet queryDownInfo1(String conditions, User user, String orderNo) throws Exception {
		
		StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.SHIP_ID,\n" );
    	sql.append("       T.SHIP_NO,\n" );
    	sql.append("       (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = T.SHIP_STATUS ) SHIP_STATUS,\n" );
    	sql.append("       T.SHIP_DATE,\n" );
    	sql.append("       T.CREATE_TIME,\n" );
    	sql.append("       T.CREATE_USER,\n" );
    	sql.append("       T1.RECEIPT_NO,\n" );
    	sql.append("       T1.DEL_ID,\n" );
    	sql.append("       T1.LICENSE_PLATE,\n" );
    	sql.append("       T.REMARKS\n" );
    	sql.append("  FROM PT_BU_ORDER_SHIP T, PT_BU_ORDER_SHIP_CARRIER T1,PT_BA_CARRIER T2");
    	
    	String wheres = conditions;
    	if(orderNo!=null && !"".equals(orderNo)){
    		wheres +="AND  T.SHIP_ID = T1.SHIP_ID AND T.CARRIER_ID = T2.CARRIER_ID AND T2.ORG_ID = "+user.getOrgId()+"\n" +
					"   AND EXISTS (SELECT 1\n" + 
					"          FROM PT_BU_ORDER_SHIP_DTL A, PT_BU_SALE_ORDER B\n" + 
					"         WHERE A.ORDER_ID = B.ORDER_ID\n" + 
					"           AND A.SHIP_ID = T.SHIP_ID\n" + 
					"           AND B.ORDER_NO LIKE '%"+orderNo+"%'\n";
    	}else{
    		wheres +="AND  T.SHIP_ID = T1.SHIP_ID AND T.CARRIER_ID = T2.CARRIER_ID AND T2.ORG_ID = "+user.getOrgId()+"\n" ;
    	}
		
		return this.factory.select(null, sql.toString() + " where " + wheres);
	}
	
	/**
	 * 
	 * @Title: queryDownInfo2
	 * @Description: 下载信息
	 * @param conditions
	 * @param user
	 * @param orderNo
	 * @return
	 * @throws Exception
	 * @return: QuerySet
	 */
	public QuerySet queryDownInfo2(String conditions, User user, String orderNo) throws Exception {
		
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.SHIP_ID,\n" );
    	sql.append("       T.SHIP_NO,\n" );
    	sql.append("       T.SHIP_STATUS,\n" );
    	sql.append("       T.SHIP_DATE,\n" );
    	sql.append("       T.CREATE_TIME,\n" );
    	sql.append("       T.CREATE_USER,\n" );
    	sql.append("       T1.RECEIPT_NO,\n" );
    	sql.append("       T1.DEL_ID,\n" );
    	sql.append("       T1.LICENSE_PLATE,\n" );
    	sql.append("       T.REMARKS\n" );
    	sql.append("  FROM PT_BU_ORDER_SHIP T, PT_BU_ORDER_SHIP_CARRIER T1");
    	
    	String wheres = conditions;
    	if(orderNo!=null && !"".equals(orderNo)){
    		wheres +="AND  T.SHIP_ID = T1.SHIP_ID\n" +
					"   AND EXISTS (SELECT 1\n" + 
					"          FROM PT_BU_ORDER_SHIP_DTL A, PT_BU_SALE_ORDER B\n" + 
					"         WHERE A.ORDER_ID = B.ORDER_ID\n" + 
					"           AND A.SHIP_ID = T.SHIP_ID\n" + 
					"           AND B.ORDER_NO LIKE '%"+orderNo+"%'\n";
    	}else{
    		wheres +="AND  T.SHIP_ID = T1.SHIP_ID\n";
    	}
    	return this.factory.select(null, sql.toString() + " where " + wheres);
	}
}
