package com.org.dms.dao.part.salesMng.orderMng;

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
 * ClassName: ThreeOrderQueryForDealerDao 
 * Function: 三包急件订单关系查询
 * date: 2014年11月21日 下午9:09:47
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class ThreeOrderQueryForDealerDao extends BaseDAO {

    public static final ThreeOrderQueryForDealerDao getInstance(ActionContext pActionContext) {

        ThreeOrderQueryForDealerDao partOrderQueryMngDao = new ThreeOrderQueryForDealerDao();
        pActionContext.setDBFactory(partOrderQueryMngDao.factory);
        return partOrderQueryMngDao;
    }
    
    /**
     * 
     * searchOrder: 订单查询
     * @author fuxiao
     * Date:2014年11月21日
     *
     */
    public BaseResultSet searchOrder(PageManager pPageManager, User pUser,String pConditions) throws Exception {
    	BaseResultSet baseResultSet = null;
    	String str = 
				"SELECT\n" +
				"       T1.ORDER_ID ORDER_ID_S, T1.ORDER_NO ORDER_NO_S, T1.ORG_CODE ORG_CODE_S,\n" + 
				"       T1.ORG_NAME ORG_NAME_S, \n" + 
				"       T2.ORDER_ID, T2.ORDER_NO,T2.ORG_CODE, T2.ORG_NAME, T2.ORDER_STATUS, T2.APPLY_DATE,\n" + 
				"       T2.ORDER_AMOUNT, T2.REAL_AMOUNT, T2.SHIP_STATUS, T2.TRANS_TYPE,\n" + 
				"       (SELECT MAX(CHECK_DATE)\n" + 
				"           FROM PT_BU_SALE_ORDER_CHECK C\n" + 
				"         WHERE C.ORDER_ID = T2.ORDER_ID) CHECK_DATE,\n" + 
				"       T2.CLOSE_DATE\n" + 
				" FROM PT_BU_SALE_ORDER T1, -- 原订单\n" + 
				"      PT_BU_SALE_ORDER T2  -- 配送中心订单\n" + 
				"WHERE T2.DIR_SOURCE_ORDER_ID = T1.ORDER_ID\n" + 
				"   AND T1.ORDER_TYPE = '203709'\n" + 
				"   AND T1.ORDER_STATUS <> '202201'\n" ; 
    	
    	// 判断用户组织类别: 配送中心
    	if(pUser.getOrgDept().getOrgType().equals(DicConstant.ZZLB_09)){
    		str += "   AND T2.ORG_ID = '"+pUser.getOrgId()+"'\n"; 
    	} else if(pUser.getOrgDept().getOrgType().equals(DicConstant.ZZLB_10)){
    		str += "   AND T1.ORG_ID = '"+pUser.getOrgId()+"'\n"; 
    	}
    	
	   str += "   AND " + pConditions + " ORDER BY T1.ORDER_ID DESC";
				
        baseResultSet =  factory.select(str,pPageManager);
        baseResultSet.setFieldDic("S_ORDER_STATUS_S", DicConstant.DDZT);     	      // 原订单状态	
        baseResultSet.setFieldDic("ORDER_STATUS", DicConstant.DDZT);   	   		  // 配送中心订单状态	
        baseResultSet.setFieldDic("SHIP_STATUS", DicConstant.DDFYZT);  	   		  // 订单发运状态
        baseResultSet.setFieldDic("TRANS_TYPE", DicConstant.FYFS);                // 发运方式
        baseResultSet.setFieldDateFormat("S_APPLY_DATE_S", "yyyy-MM-dd HH:mm:ss");  // 提报日期
        baseResultSet.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");    // 提报日期
        baseResultSet.setFieldDateFormat("CHECK_DATE", "yyyy-MM-dd HH:mm:ss");    // 订单审核日期
        baseResultSet.setFieldDateFormat("CLOSE_DATE", "yyyy-MM-dd HH:mm:ss");    // 订单关闭日期
        return baseResultSet;
    }
    
    
    /**
     * 
     * checkOrgType: 判断组织类别：true: 配送中心 false 经销商
     * @author fuxiao
     * Date:2014年11月21日
     *
     */
    public boolean checkOrgType(User user) throws NumberFormatException, SQLException {
    	return Integer.parseInt(this.factory.select("SELECT DECODE(T.ORG_TYPE, '200005', 1, 0) FROM TM_ORG T WHERE T.ORG_ID = ?", new Object[]{user.getOrgId()})[0][0]) > 0;
    }
    
    /**
     * 
     * searchOrder: 添加截取查询条件
     * @author fuxiao
     * Date:2014年11月15日
     *
     */
    public BaseResultSet searchOrder(PageManager pPageManager, User pUser,String pConditions, boolean isSub) throws Exception {
    	
		if(pConditions.indexOf("PART_CODE") != -1 || pConditions.indexOf("PART_NAME") != -1){
			pConditions += " AND EXISTS (\n" +
							"    SELECT 1 FROM PT_BU_SALE_ORDER_DTL D WHERE D.ORDER_ID = T2.ORDER_ID";
			if(pConditions.indexOf("PART_NAME") != -1){
				String partNameSql = pConditions.substring(pConditions.indexOf("PART_NAME"), pConditions.indexOf("'", pConditions.indexOf("PART_NAME") + 15) + 1);
				pConditions = pConditions.replace(" AND "+partNameSql, "");
				pConditions +=" AND " + partNameSql;
			}
			if(pConditions.indexOf("PART_CODE") != -1){
				String partNameSql = pConditions.substring(pConditions.indexOf("PART_CODE"), pConditions.indexOf("'", pConditions.indexOf("PART_CODE") + 15) + 1);
				pConditions = pConditions.replace(" AND "+partNameSql, "");
				pConditions +=" AND " + partNameSql;
			}
			pConditions += " ) ";
		}
    	return this.searchOrder(pPageManager, pUser, pConditions);
    }
    /**
     * 订单查询(导出)
     *
     * @pConditions 查询条件
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet download(String pConditions,User pUser) throws Exception {
    	
    	if(pConditions.indexOf("PART_CODE") != -1 || pConditions.indexOf("PART_NAME") != -1){
			pConditions += " AND EXISTS (\n" +
							"    SELECT 1 FROM PT_BU_SALE_ORDER_DTL D WHERE D.ORDER_ID = T2.ORDER_ID";
			if(pConditions.indexOf("PART_NAME") != -1){
				String partNameSql = pConditions.substring(pConditions.indexOf("PART_NAME"), pConditions.indexOf("'", pConditions.indexOf("PART_NAME") + 15) + 1);
				pConditions = pConditions.replace(" AND "+partNameSql, "");
				pConditions +=" AND " + partNameSql;
			}
			if(pConditions.indexOf("PART_CODE") != -1){
				String partNameSql = pConditions.substring(pConditions.indexOf("PART_CODE"), pConditions.indexOf("'", pConditions.indexOf("PART_CODE") + 15) + 1);
				pConditions = pConditions.replace(" AND "+partNameSql, "");
				pConditions +=" AND " + partNameSql;
			}
			pConditions += " ) ";
		}
		
    	
    	String str = 
				"SELECT\n" +
				"       T1.ORDER_ID ORDER_ID_S, T1.ORDER_NO ORDER_NO_S, T1.ORG_CODE ORG_CODE_S,\n" + 
				"       T1.ORG_NAME ORG_NAME_S, \n" + 
				"       T2.ORDER_ID, T2.ORDER_NO,T2.ORG_CODE, T2.ORG_NAME, (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = T2.ORDER_STATUS) ORDER_STATUS, T2.APPLY_DATE,\n" + 
				"       T2.ORDER_AMOUNT, T2.REAL_AMOUNT, (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = T2.ORDER_STATUS) SHIP_STATUS, T2.TRANS_TYPE,\n" + 
				"       (SELECT MAX(CHECK_DATE)\n" + 
				"           FROM PT_BU_SALE_ORDER_CHECK C\n" + 
				"         WHERE C.ORDER_ID = T2.ORDER_ID) CHECK_DATE,\n" + 
				"       T2.CLOSE_DATE\n" + 
				" FROM PT_BU_SALE_ORDER T1, -- 原订单\n" + 
				"      PT_BU_SALE_ORDER T2  -- 配送中心订单\n" + 
				"WHERE T2.DIR_SOURCE_ORDER_ID = T1.ORDER_ID\n" + 
				"   AND T1.ORDER_TYPE = '203709'\n" + 
				"   AND T1.ORDER_STATUS <> '202201'\n" ; 
    	
    	// 判断用户组织类别: 配送中心
    	if(pUser.getOrgDept().getOrgType().equals(DicConstant.ZZLB_09)){
    		str += "   AND T2.ORG_ID = '"+pUser.getOrgId()+"'\n"; 
    	} else if(pUser.getOrgDept().getOrgType().equals(DicConstant.ZZLB_10)){
    		str += "   AND T1.ORG_ID = '"+pUser.getOrgId()+"'\n"; 
    	}
	   str += "   AND " + pConditions + " ORDER BY T1.ORDER_ID DESC";
        return factory.select(null, str);
    }



    
	/**
	 * 
	 * checkUserIsAM: 判断用户的org是否属于军品，true：是，false: 不是
	 * @author fuxiao
	 * Date:2014年11月14日
	 *
	 */
	public boolean checkUserIsAM(User user) throws SQLException{
		String res = this.factory.select("SELECT F_IS_AM("+user.getOrgId()+") FROM DUAL")[0][0];
		return res.equals("1");
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
    
  //----------------------报表查询：配件经销商销量统计查询--------------------------------------------------	
	/**
     * 配件经销商销量统计查询
     * @throws Exception
     * @Author suoxiuli 2014-11-1
     */
	public BaseResultSet dealerSalesStatisticsQuery(PageManager page,String conditions, 
			String startTime, String endTime) throws Exception
    {
    	String wheres = conditions;  
    	wheres += " AND T1.STATUS = 100201";
    	wheres += " AND T1.ORG_TYPE = 200006";
    	wheres += " AND A.STATUS = 100201";
    	wheres += " AND T1.CODE = A.ORG_CODE";
    	wheres += " AND A.DC_CODE = T2.CODE(+)";
    	wheres += " AND T1.PID = T3.ORG_ID";
    	wheres += " AND T1.CODE = C.CODE(+)";
    	wheres += " ORDER BY T1.CODE, A.DC_CODE DESC";
        page.setFilter(wheres);
        
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T1.ORG_ID as DEALER_ID,\n" ); // --经销商ID
    	sql.append("       T1.CODE as DEALER_CODE,\n" ); // --经销商CODE
    	sql.append("       T1.ONAME as DEALER_NAME,\n" ); // --经销商NAME
    	sql.append("       A.DC_ID,\n" ); // --配送中心ID
    	sql.append("       A.DC_CODE,\n" ); // --配送中心CDOE
    	sql.append("       T2.ONAME as DC_NAME,\n" ); // --配送中心NAME
    	sql.append("       T3.ORG_ID as OFFICE_ID,\n" ); // --办事处ID
    	sql.append("       T3.CODE as OFFICE_CODE,\n" ); // --办事处CODE
    	sql.append("       T3.ONAME as OFFICE_NAME,\n" ); // --办事处NAME
    	sql.append("       NVL(C.SUM_ORDER_AMOUNT, 0) as SUM_ORDER_AMOUNT,\n" ); // --订单总金额
    	sql.append("       NVL(C.SUM_REAL_AMOUNT, 0) as SUM_REAL_AMOUNT,\n" ); // --实发总金额
    	sql.append("       '"+startTime.replace('-', '.')+"' || '-' || '"+endTime.replace('-', '.')+"' as DATE_PIREIOD\n" );
    	sql.append("  FROM\n" );
    	
    	sql.append("       TM_ORG T1,\n" );
    	
    	sql.append("       PT_BA_SERVICE_DC A,\n" );
    	
    	sql.append("       TM_ORG T2,\n" );
    	
    	sql.append("       TM_ORG T3,\n" );
    	
    	sql.append("       (SELECT T4.ORG_ID,\n" ); // --经销商ID
    	sql.append("               T4.CODE,\n" ); // --经销商CODE
    	sql.append("               T4.ONAME,\n" ); // --经销商NAME
    	sql.append("               SUM(NVL(B.ORDER_AMOUNT, 0)) as SUM_ORDER_AMOUNT,\n" );
    	sql.append("               SUM(NVL(B.REAL_AMOUNT, 0)) as SUM_REAL_AMOUNT\n" );
    	sql.append("          FROM TM_ORG T4, PT_BU_SALE_ORDER B\n" );
    	sql.append("         WHERE T4.STATUS = 100201\n" );
    	sql.append("           AND T4.ORG_TYPE = 200006\n" );
    	sql.append("           AND (B.STATUS = 100201 or B.STATUS is null)\n" );
    	sql.append("           AND (B.ORDER_STATUS = 202206 or B.ORDER_STATUS is null)\n" ); // --已关闭订单
    	sql.append("           AND B.CLOSE_DATE BETWEEN to_date('"+startTime+"', 'yyyy-mm-dd') and\n" );
    	sql.append("               to_date('"+endTime+"', 'yyyy-mm-dd')\n" );
    	sql.append("           AND T4.CODE = B.ORG_CODE(+)\n" );
    	sql.append("         GROUP BY T4.ORG_ID, T4.CODE, T4.ONAME) C\n" );

    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
	
	/**
     * 配件经销商销量统计导出:报表的导出按钮
     * @throws Exception
     * @Author suoxiuli 2014-11-1
     */
    public QuerySet dealerSalesStatiReportExportExcel(PageManager page, String conditions, 
    		String startTime, String endTime) throws Exception {

    	String wheres = " WHERE "+ conditions; 
    	wheres += " AND T1.STATUS = 100201";
    	wheres += " AND T1.ORG_TYPE = 200006";
    	wheres += " AND A.STATUS = 100201";
    	wheres += " AND T1.CODE = A.ORG_CODE";
    	wheres += " AND A.DC_CODE = T2.CODE(+)";
    	wheres += " AND T1.PID = T3.ORG_ID";
    	wheres += " AND T1.CODE = C.CODE(+)";
    	wheres += " ORDER BY T1.CODE, A.DC_CODE DESC";
        
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT ROWNUM,\n" ); // --序号
    	sql.append("       T1.ORG_ID as DEALER_ID,\n" ); // --经销商ID
    	sql.append("       T1.CODE as DEALER_CODE,\n" ); // --经销商CODE
    	sql.append("       T1.ONAME as DEALER_NAME,\n" ); // --经销商NAME
    	sql.append("       A.DC_ID,\n" ); // --配送中心ID
    	sql.append("       A.DC_CODE,\n" ); // --配送中心CDOE
    	sql.append("       T2.ONAME as DC_NAME,\n" ); // --配送中心NAME
    	sql.append("       T3.ORG_ID as OFFICE_ID,\n" ); // --办事处ID
    	sql.append("       T3.CODE as OFFICE_CODE,\n" ); // --办事处CODE
    	sql.append("       T3.ONAME as OFFICE_NAME,\n" ); // --办事处NAME
    	sql.append("       NVL(C.SUM_ORDER_AMOUNT, 0) as SUM_ORDER_AMOUNT,\n" ); // --订单总金额
    	sql.append("       NVL(C.SUM_REAL_AMOUNT, 0) as SUM_REAL_AMOUNT,\n" ); // --实发总金额
    	sql.append("       '"+startTime.replace('-', '.')+"' || '-' || '"+endTime.replace('-', '.')+"' as DATE_PIREIOD\n" );
    	sql.append("  FROM\n" );
    	
    	sql.append("       TM_ORG T1,\n" );
    	
    	sql.append("       PT_BA_SERVICE_DC A,\n" );
    	
    	sql.append("       TM_ORG T2,\n" );
    	
    	sql.append("       TM_ORG T3,\n" );
    	
    	sql.append("       (SELECT T4.ORG_ID,\n" ); // --经销商ID
    	sql.append("               T4.CODE,\n" ); // --经销商CODE
    	sql.append("               T4.ONAME,\n" ); // --经销商NAME
    	sql.append("               SUM(NVL(B.ORDER_AMOUNT, 0)) as SUM_ORDER_AMOUNT,\n" );
    	sql.append("               SUM(NVL(B.REAL_AMOUNT, 0)) as SUM_REAL_AMOUNT\n" );
    	sql.append("          FROM TM_ORG T4, PT_BU_SALE_ORDER B\n" );
    	sql.append("         WHERE T4.STATUS = 100201\n" );
    	sql.append("           AND T4.ORG_TYPE = 200006\n" );
    	sql.append("           AND (B.STATUS = 100201 or B.STATUS is null)\n" );
    	sql.append("           AND (B.ORDER_STATUS = 202206 or B.ORDER_STATUS is null)\n" ); // --已关闭订单
    	sql.append("           AND B.CLOSE_DATE BETWEEN to_date('"+startTime+"', 'yyyy-mm-dd') and\n" );
    	sql.append("               to_date('"+endTime+"', 'yyyy-mm-dd')\n" );
    	sql.append("           AND T4.CODE = B.ORG_CODE(+)\n" );
    	sql.append("         GROUP BY T4.ORG_ID, T4.CODE, T4.ONAME) C\n" );

        //执行方法，不需要传递conn参数
    	return factory.select(null, sql.toString()+wheres);
    }
    
    
    public QuerySet queryOrderDtl(User user, String ORDER_ID) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
//        StringBuffer sql= new StringBuffer();
//        sql.append("SELECT T.PART_CODE,\n" );
//        sql.append("       T.PART_NAME,\n" );
//        sql.append("       T2.DIC_VALUE,\n" );
//        sql.append("       T1.MIN_PACK || '/' || T2.DIC_VALUE UNIT,\n" );
//        sql.append("       T.ORDER_COUNT,\n" );
//        sql.append("       T4.OUT_AMOUNT COUNT,\n" );
//        sql.append("       T4.OUT_AMOUNT * T.UNIT_PRICE AMOUINT,\n" );
//        sql.append("       T.REMARKS\n" );
//        sql.append("  FROM PT_BU_SALE_ORDER_DTL T,\n" );
//        sql.append("       PT_BA_INFO           T1,\n" );
//        sql.append("       DIC_TREE             T2,\n" );
//        sql.append("       PT_BU_STOCK_OUT      T3,\n" );
//        sql.append("       PT_BU_STOCK_OUT_DTL  T4\n" );
//        sql.append(" WHERE T.PART_ID = T1.PART_ID\n" );
//        sql.append("   AND T1.MIN_UNIT = T2.ID\n" );
//        sql.append("   AND T.ORDER_ID = T3.ORDER_ID(+)\n" );
//        sql.append("   AND T3.OUT_ID = T4.OUT_ID\n" );
//        sql.append("   AND T.PART_ID = T4.PART_ID");
//        sql.append("   AND T.ORDER_ID = "+ORDER_ID+"");
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.PART_CODE,\n" );
        sql.append("       T.PART_NAME,\n" );
        sql.append("       T2.DIC_VALUE,\n" );
        sql.append("       T1.MIN_PACK || '/' || T2.DIC_VALUE UNIT,\n" );
        sql.append("       T.ORDER_COUNT,\n" );
        sql.append("       NVL(T3.COUNT,0) COUNT,\n" );
        sql.append("       NVL(T3.AMOUINT,0) AMOUINT,\n" );
        sql.append("       T.REMARKS\n" );
        sql.append("  FROM PT_BU_SALE_ORDER_DTL T,\n" );
        sql.append("       PT_BA_INFO T1,\n" );
        sql.append("       DIC_TREE T2,\n" );
        sql.append("       (SELECT A.DTL_ID,\n" );
        sql.append("               SUM(C.OUT_AMOUNT * A.UNIT_PRICE) AMOUINT,\n" );
        sql.append("               SUM(C.OUT_AMOUNT) COUNT\n" );
        sql.append("          FROM PT_BU_SALE_ORDER_DTL A,\n" );
        sql.append("               PT_BU_STOCK_OUT      B,\n" );
        sql.append("               PT_BU_STOCK_OUT_DTL  C\n" );
        sql.append("         WHERE A.ORDER_ID = B.ORDER_ID\n" );
        sql.append("           AND A.PART_ID = C.PART_ID\n" );
        sql.append("           AND B.OUT_ID = C.OUT_ID\n" );
        sql.append("         GROUP BY A.DTL_ID) T3\n" );
        sql.append(" WHERE T.PART_ID = T1.PART_ID\n" );
        sql.append("   AND T1.MIN_UNIT = T2.ID\n" );
        sql.append("   AND T.DTL_ID = T3.DTL_ID(+)\n" );
        sql.append("   AND T.ORDER_ID = "+ORDER_ID+"");
        qs = factory.select(null,sql.toString());
        return qs;
    }
    public QuerySet queryOrderInfo(User user, String ORDER_ID) throws Exception {
        //定义返回结果集
        QuerySet qs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.ORG_NAME,\n" );
        sql.append("       T.ORDER_NO,\n" );
        sql.append("       NVL(T2.AMOUNT, 0) AMOUNT,\n" );
        sql.append("       TO_CHAR(SYSDATE, 'YYYY-MM-DD') PRINT_DATE\n" );
        sql.append("  FROM PT_BU_SALE_ORDER T， (SELECT SUM(C.OUT_AMOUNT * A.UNIT_PRICE) AMOUNT,\n" );
        sql.append("                                   A.ORDER_ID\n" );
        sql.append("                              FROM PT_BU_SALE_ORDER_DTL A,\n" );
        sql.append("                                   PT_BU_STOCK_OUT      B,\n" );
        sql.append("                                   PT_BU_STOCK_OUT_DTL  C\n" );
        sql.append("                             WHERE A.ORDER_ID = B.ORDER_ID\n" );
        sql.append("                               AND A.PART_ID = C.PART_ID\n" );
        sql.append("                             GROUP BY A.ORDER_ID) T2\n" );
        sql.append(" WHERE T.ORDER_ID = "+ORDER_ID+"\n" );
        sql.append("   AND T.ORDER_ID = T2.ORDER_ID(+)");
        qs = factory.select(null,sql.toString());
        return qs;
    }
}
