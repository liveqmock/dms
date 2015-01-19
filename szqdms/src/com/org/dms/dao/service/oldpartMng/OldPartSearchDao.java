package com.org.dms.dao.service.oldpartMng;

import java.sql.SQLException;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 旧件查询
 * @author zts
 *
 */
public class OldPartSearchDao extends BaseDAO{
	//定义instance
	public  static final OldPartSearchDao getInstance(ActionContext atx)
	{
		OldPartSearchDao dao = new OldPartSearchDao();
		atx.setDBFactory(dao.factory);
		return dao;
	}
	
	/**
	 * 回运单查询
	 * @param page
	 * @param conditions
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public BaseResultSet oldPartSearch(PageManager page ,String conditions,User user) throws SQLException{
		   String wheres = conditions;
		   wheres +=" AND RO.ORDER_ID = D.ORDER_ID "+
				    " AND C.CLAIM_ID = D.CLAIM_ID\n"
				   +" AND G.ORG_ID = C.ORG_ID"+
				    " ORDER BY RO.ORDER_ID\n";
		   page.setFilter(wheres);
		   BaseResultSet bs = null;
		   StringBuffer sql1= new StringBuffer();
		   sql1.append("SELECT D.PART_ID,\n" );
		   sql1.append("         D.PART_CODE,\n" );
		   sql1.append("         D.PART_NAME,\n" );
		   sql1.append("         D.CLAIM_NO,\n" );
		   sql1.append("         D.CLAIM_ID,\n" );
		   sql1.append("         D.REMARKS,\n" );
		   sql1.append("         NVL(D.OUGHT_COUNT,0) OUGHT_COUNT,\n" );
		   sql1.append("         NVL(D.ACTUL_COUNT,0) ACTUL_COUNT,\n" );
		   sql1.append("         NVL(D.SHOULD_COUNT,0) SHOULD_COUNT,\n" );
		   sql1.append("         NVL(D.SHOULD_COUNT,0)-NVL(D.ACTUL_COUNT,0)-NVL(D.MISS_COUNT,0) TGSL,\n" );
		   sql1.append("         NVL(D.ACTUL_COUNT,0)+NVL(D.MISS_COUNT,0) ZFSL,\n" );
		   sql1.append("         D.PROSUPPLY_CODE,\n" );
		   sql1.append("         D.PROSUPPLY_NAME,\n" );
		   sql1.append("         NVL(D.MISS_COUNT,0) MISS_COUNT,\n" );
		   sql1.append("         RO.PRODUCE_DATE,\n" );
		   sql1.append("         RO.RETURN_DATE,\n" );
		   sql1.append("         C.ORG_ID AS ORG_NAME,\n" ); 
		   sql1.append("         C.ORG_ID AS ORG_CODE,\n" );
		   sql1.append("         RO.ORDER_ID,\n" );
		   sql1.append("         RO.ORDER_NO,\n" );
		   sql1.append("         D.OLD_PART_STATUS\n" );
		   sql1.append("    FROM SE_BU_RETURN_ORDER RO, SE_BU_RETURNORDER_DETAIL D, SE_BU_CLAIM C,TM_ORG G\n" );
		   bs=factory.select(sql1.toString(), page);
		   bs.setFieldDateFormat("PRODUCE_DATE", "yyyy-MM");
		   bs.setFieldDateFormat("RETURN_DATE", "yyyy-MM-dd");
		   bs.setFieldOrgDeptCode("ORG_CODE");
		   bs.setFieldOrgDeptSimpleName("ORG_NAME");
		   bs.setFieldDic("OLD_PART_STATUS", "JJZT");
		   return bs;
	   }
	/**
	 * 回运单查询
	 * @param page
	 * @param conditions
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public BaseResultSet supOldPartSearch(PageManager page ,String conditions,User user) throws SQLException{
		String wheres = conditions;
		wheres +=" AND D.DUTYSUPPLY_ID = S.SUPPLIER_ID "
				+ "AND RO.ORG_ID = WC.ORG_ID AND BW.WAREHOUSE_ID = WC.WAREHOUSE_ID\n"+
				"  AND RO.ORDER_ID = D.ORDER_ID\n "
				+" AND WC.STATUS = 100201\n"
				+" AND G.ORG_ID = S.ORG_ID \n"
				+" AND G.ORG_ID = "+user.getOrgId()+""+
			" ORDER BY D.CLAIM_ID, D.PART_ID\n";
		page.setFilter(wheres);
		BaseResultSet bs = null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT D.PART_ID,\n" );
		sql.append("       D.PART_CODE,\n" );
		sql.append("       D.PART_NAME,\n" );
		sql.append("       D.CLAIM_NO,\n" );
		sql.append("       D.CLAIM_ID,\n" );
		sql.append("       NVL(D.OUGHT_COUNT, 0) OUGHT_COUNT,\n" );
		sql.append("       NVL(D.MISS_COUNT, 0) MISS_COUNT,\n" );
		sql.append("       RO.PRODUCE_DATE,\n" );
		sql.append("       D.IS_MAIN,\n" );
		sql.append("       D.CHECK_USER,\n" );
		sql.append("       D.CHECK_DATE,\n" );
		sql.append("       BW.WAREHOUSE_CODE,\n" );
		sql.append("       BW.WAREHOUSE_NAME,\n" );
		sql.append("       D.OLD_PART_STATUS\n" );
		sql.append("  FROM SE_BU_RETURNORDER_DETAIL D,\n" );
		sql.append("       PT_BA_SUPPLIER           S,\n" );
		sql.append("       SE_BU_RETURN_ORDER       RO,\n" );
		sql.append("       SE_BU_WAREHOUSE_CENTROSTIGMA WC,\n" );
		sql.append("       SE_BA_WAREHOUSE            BW,\n" );
		sql.append("       TM_ORG                   G");
		bs=factory.select(sql.toString(), page);
		bs.setFieldDateFormat("PRODUCE_DATE", "yyyy-MM");
		bs.setFieldDateFormat("CHECK_DATE", "yyyy-MM-dd");
		bs.setFieldDic("IS_MAIN", "GZLB");
		bs.setFieldDic("OLD_PART_STATUS", "JJZT");
		bs.setFieldOrgDeptCode("ORG_CODE");
		bs.setFieldOrgDeptSimpleName("ORG_NAME");
		return bs;
	}
	/**
	 * 回运单查询
	 * @param page
	 * @param conditions
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public BaseResultSet oldPartMissSearch(PageManager page ,String conditions,User user) throws SQLException{
		String wheres = conditions;
		wheres +="AND U.WAREHOUSE_ID = C.WAREHOUSE_ID\n" +
				"  AND D.ORDER_ID = O.ORDER_ID\n" + 
				"  AND F.CLAIM_DTL_ID = D.CLAIM_DTL_ID\n" + 
				"  AND O.ORG_ID = C.ORG_ID\n" + 
				"  AND T.ORG_ID = O.ORG_ID\n" + 
				"  AND T1.ORG_ID = T.PID\n" + 
				"  AND C.STATUS = 100201\n" + 
				"  AND U.STATUS = 100201\n" + 
				"  AND O.ORDER_STATUS = 302505\n" + 
				"  AND O.STATUS = 100201\n" + 
				"  AND U.USER_ID = "+user.getUserId()+"\n" + 
				"  AND O.FINAL_CHECK_STATUS IS NULL\n" + 
				"ORDER BY O.ORDER_ID";

		page.setFilter(wheres);
		BaseResultSet bs = null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT O.ORDER_ID,\n" );
		sql.append("       O.ORDER_NO,\n" );
		sql.append("       O.PRODUCE_DATE,\n" );
		sql.append("       D.CLAIM_NO,\n" );
		sql.append("       D.PART_ID,\n" );
		sql.append("       D.PART_CODE,\n" );
		sql.append("       D.PART_NAME,\n" );
		sql.append("       F.FAULT_CODE,\n" );
		sql.append("       D.PROSUPPLY_CODE,\n" );
		sql.append("       D.PROSUPPLY_NAME,\n" );
		sql.append("       NVL(D.SHOULD_COUNT,0)SHOULD_COUNT,\n" );
		sql.append("       NVL(D.OUGHT_COUNT,0)OUGHT_COUNT,\n" );
		sql.append("       NVL(D.MISS_COUNT,0)MISS_COUNT,\n" );
		sql.append("       O.ORG_ID ORG_CODE,\n" );
		sql.append("       O.ORG_ID ORG_NAME,\n" );
		sql.append("       T1.CODE PCODE,\n" );
		sql.append("       D.OLD_PART_STATUS,\n" );
		sql.append("       T1.ONAME PNAME\n" );
		sql.append("  FROM SE_BU_RETURN_ORDER           O,\n" );
		sql.append("       SE_BU_RETURNORDER_DETAIL     D,\n" );
		sql.append("       SE_BU_WAREHOUSE_USER         U,\n" );
		sql.append("       SE_BU_WAREHOUSE_CENTROSTIGMA C,\n" );
		sql.append("       SE_BU_CLAIM_FAULT            F,\n" );
		sql.append("       TM_ORG                       T,\n" );
		sql.append("       TM_ORG                       T1");

		bs=factory.select(sql.toString(), page);
		bs.setFieldDateFormat("PRODUCE_DATE", "yyyy-MM");
		bs.setFieldDic("IS_MAIN", "GZLB");
		bs.setFieldDic("OLD_PART_STATUS", "JJZT");
		bs.setFieldOrgDeptCode("ORG_CODE");
		bs.setFieldOrgDeptSimpleName("ORG_NAME");
		return bs;
	}
	/**
	 * 回运单信息下载
	 * @return
	 * @throws Exception
	 */
	public QuerySet download(String conditions)throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT D.PART_ID,\n" );
		sql.append("       D.PART_CODE,\n" );
		sql.append("       D.PART_NAME,\n" );
		sql.append("       D.CLAIM_NO,\n" );
		sql.append("       D.CLAIM_ID,\n" );
		sql.append("       D.REMARKS,\n" );
		sql.append("       NVL(D.OUGHT_COUNT, 0) OUGHT_COUNT,\n" );
		sql.append("       NVL(D.ACTUL_COUNT, 0) ACTUL_COUNT,\n" );
		sql.append("       NVL(D.SHOULD_COUNT, 0) SHOULD_COUNT,\n" );
		sql.append("       D.PROSUPPLY_CODE,\n" );
		sql.append("       D.PROSUPPLY_NAME,\n" );
		sql.append("       NVL(D.MISS_COUNT, 0) MISS_COUNT,\n" );
		sql.append("       RO.PRODUCE_DATE,\n" );
		sql.append("       RO.RETURN_DATE,\n" );
		sql.append("       (SELECT G.CODE FROM TM_ORG G WHERE G.ORG_ID = C.ORG_ID) ORG_CODE,\n" );
		sql.append("       (SELECT G.ONAME FROM TM_ORG G WHERE G.ORG_ID = C.ORG_ID) ORG_NAME,\n" );
		sql.append("       RO.ORDER_ID,\n" );
		sql.append("       RO.ORDER_NO,\n" );
		sql.append("       D.OLD_PART_STATUS\n" );
		sql.append("  FROM SE_BU_RETURN_ORDER       RO,\n" );
		sql.append("       SE_BU_RETURNORDER_DETAIL D,\n" );
		sql.append("       SE_BU_CLAIM              C\n" );
		sql.append(" WHERE "+conditions+" \n" );
		sql.append("AND RO.ORDER_ID = D.ORDER_ID\n" );
		sql.append("   AND C.CLAIM_ID = D.CLAIM_ID\n" );
		sql.append(" ORDER BY RO.ORDER_ID");


	    return factory.select(null, sql.toString());
	}
	/**
	 * 旧件库房回运单下载
	 * @return
	 * @throws Exception
	 */
	public QuerySet missDownload(String conditions,User user)throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT O.ORDER_ID,\n" );
		sql.append("       O.ORDER_NO,\n" );
		sql.append("       D.CLAIM_NO,\n" );
		sql.append("       D.PART_ID,\n" );
		sql.append("       O.PRODUCE_DATE,\n" );
		sql.append("       D.PART_CODE,\n" );
		sql.append("       D.PART_NAME,\n" );
		sql.append("       F.FAULT_CODE,\n" );
		sql.append("       D.PROSUPPLY_CODE,\n" );
		sql.append("       D.PROSUPPLY_NAME,\n" );
		sql.append("       NVL(D.SHOULD_COUNT, 0) SHOULD_COUNT,\n" );
		sql.append("       NVL(D.OUGHT_COUNT, 0) OUGHT_COUNT,\n" );
		sql.append("       NVL(D.MISS_COUNT, 0) MISS_COUNT,\n" );
		sql.append("   (SELECT G.CODE FROM TM_ORG G ,SE_BU_CLAIM C WHERE G.ORG_ID = C.ORG_ID AND C.CLAIM_ID = D.CLAIM_ID )CODE,\n" );
	    sql.append("   (SELECT G.ONAME FROM TM_ORG G ,SE_BU_CLAIM C WHERE G.ORG_ID = C.ORG_ID AND C.CLAIM_ID = D.CLAIM_ID )ONAME,\n" );
		sql.append("       D.OLD_PART_STATUS,\n" );
		sql.append("       T1.CODE PCODE,\n" );
		sql.append("       T1.ONAME PNAME\n" );
		sql.append("  FROM SE_BU_RETURN_ORDER           O,\n" );
		sql.append("       SE_BU_RETURNORDER_DETAIL     D,\n" );
		sql.append("       SE_BU_WAREHOUSE_USER         U,\n" );
		sql.append("       SE_BU_WAREHOUSE_CENTROSTIGMA C,\n" );
		sql.append("       SE_BU_CLAIM_FAULT            F,\n" );
		sql.append("       TM_ORG                       T,\n" );
		sql.append("       TM_ORG                       T1\n" );
		sql.append(" WHERE "+conditions+"\n" );
		sql.append("   AND U.WAREHOUSE_ID = C.WAREHOUSE_ID\n" );
		sql.append("   AND D.ORDER_ID = O.ORDER_ID\n" );
		sql.append("   AND F.CLAIM_DTL_ID = D.CLAIM_DTL_ID\n" );
		sql.append("   AND O.ORG_ID = C.ORG_ID\n" );
		sql.append("   AND T.ORG_ID = O.ORG_ID\n" );
		sql.append("   AND T1.ORG_ID = T.PID\n" );
		sql.append("   AND C.STATUS = 100201\n" );
		sql.append("   AND U.STATUS = 100201\n" );
		sql.append("   AND O.ORDER_STATUS = 302505\n" );
		sql.append("   AND O.STATUS = 100201\n" );
		sql.append("   AND U.USER_ID = "+user.getUserId()+"\n" );
		sql.append("   AND O.FINAL_CHECK_STATUS IS NULL\n" );
		sql.append(" ORDER BY O.ORDER_ID");
		return factory.select(null, sql.toString());
	}
	/**
	 * 索赔单下载
	 * @return
	 * @throws Exception
	 */
	public QuerySet supDownload(String conditions,User user)throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT D.PART_ID,\n" );
		sql.append("       D.PART_CODE,\n" );
		sql.append("       D.PART_NAME,\n" );
		sql.append("       D.CLAIM_NO,\n" );
		sql.append("       D.CLAIM_ID,\n" );
		sql.append("       NVL(D.OUGHT_COUNT, 0) OUGHT_COUNT,\n" );
		sql.append("       NVL(D.MISS_COUNT, 0) MISS_COUNT,\n" );
		sql.append("       RO.PRODUCE_DATE,\n" );
		sql.append("       D.IS_MAIN,\n" );
		sql.append("       BW.WAREHOUSE_CODE,\n" );
		sql.append("       BW.WAREHOUSE_NAME,\n" );
		sql.append("       D.OLD_PART_STATUS\n" );
		sql.append("  FROM SE_BU_RETURNORDER_DETAIL     D,\n" );
		sql.append("       PT_BA_SUPPLIER               S,\n" );
		sql.append("       SE_BU_RETURN_ORDER           RO,\n" );
		sql.append("       SE_BU_WAREHOUSE_CENTROSTIGMA WC,\n" );
		sql.append("       SE_BA_WAREHOUSE              BW,\n" );
		sql.append("       TM_ORG                       G");
		
		sql.append(" WHERE "+conditions+" \n" );
		sql.append("   AND D.DUTYSUPPLY_ID = S.SUPPLIER_ID\n" );
		sql.append("   AND BW.WAREHOUSE_ID = WC.WAREHOUSE_ID\n" );
		sql.append("   AND RO.ORG_ID = WC.ORG_ID\n" );
		sql.append("   AND WC.STATUS = 100201\n" );
		sql.append("   AND RO.ORDER_ID = D.ORDER_ID\n" );
		sql.append("   AND G.ORG_ID = S.ORG_ID\n" );
		sql.append("   AND G.ORG_ID = "+user.getOrgId()+"\n" );
		sql.append(" ORDER BY D.CLAIM_ID, D.PART_ID");
		
		return factory.select(null, sql.toString());
	}
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public QuerySet download1(String conditions)throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT D.PART_ID,\n" );
		sql.append("         D.PART_CODE,\n" );
		sql.append("         D.PART_NAME,\n" );
		sql.append("         D.CLAIM_NO,\n" );
		sql.append("         D.CLAIM_ID,\n" );
		sql.append("         D.OUGHT_COUNT,\n" );
		sql.append("         D.PROSUPPLY_CODE,\n" );
		sql.append("         D.PROSUPPLY_NAME,\n" );
		sql.append("         RO.PRODUCE_DATE,\n" );
		sql.append("         TO_CHAR(RO.RETURN_DATE,'YYYY-MM-DD') AS RETURN_DATE,\n" );
		sql.append("         MD.DEALER_NAME AS ORG_NAME,\n" ); 
		sql.append("         MD.DEALER_CODE AS ORG_CODE,\n" );
		sql.append("         RO.ORDER_ID,\n" );
		sql.append("         RO.ORDER_NO\n" );
		sql.append("  FROM SE_BU_RETURN_ORDER RO, SE_BU_RETURNORDER_DETAIL D, SE_BU_CLAIM C,MAIN_DEALER MD\n" );
		sql.append(" WHERE "+conditions+" \n" );
		sql.append("   AND RO.ORDER_ID = D.ORDER_ID\n" );
		sql.append("   AND C.CLAIM_ID = D.CLAIM_ID\n" );
		sql.append("   AND C.ORG_ID=MD.ORG_ID\n" );
		sql.append(" ORDER BY  RO.ORDER_ID");
	    return factory.select(null, sql.toString());
	}

}
