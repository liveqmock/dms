package com.org.dms.dao.service.oldpartMng;

import java.sql.SQLException;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.service.SeBuClaimVO;
import com.org.dms.vo.service.SeBuReturnOrderVO;
import com.org.dms.vo.service.SeBuReturnorderDetailVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 旧件终审DAO
 * @author zts
 *
 */
public class OldPartCheckStorageDao extends BaseDAO{

	 //定义instance
 public  static final OldPartCheckStorageDao getInstance(ActionContext atx)
 {
	 OldPartCheckStorageDao dao = new OldPartCheckStorageDao();
     atx.setDBFactory(dao.factory);
     return dao;
 }
	 /**
	  * 旧件终审查询
	  * @param page
	  * @param conditions
	  * @param user
	  * @return
	  * @throws SQLException
	  */
	 public BaseResultSet oldPartCheckSearch(PageManager page ,String conditions,User user,String claimNo,String dealerCode) throws SQLException{
		   String wheres = conditions;
		   wheres +="  AND T.ORG_ID = O.ORG_ID \n"
		   			+" AND T1.ORG_ID = T.PID \n";
		   		if(null==claimNo||claimNo.equals("")){}else{
		   wheres +="  AND EXISTS(SELECT * FROM SE_BU_RETURNORDER_DETAIL D \n"
		   			+ "  WHERE D.ORDER_ID = O.ORDER_ID \n"
		   			+ "    AND D.CLAIM_NO ='"+claimNo+"' ) \n";
		   		};
		   		if(null==dealerCode||dealerCode.equals("")){}else{
		   wheres +="  AND EXISTS (SELECT 1 FROM SE_BU_RETURNORDER_DETAIL D \n"
		   			+ "  WHERE D.ORDER_ID = O.ORDER_ID"
		   			+ "	   AND D.CLAIM_NO IN (SELECT C.CLAIM_NO\n"
		   			+ "		FROM SE_BU_CLAIM C, TM_ORG TG\n"
		   			+ "    WHERE C.CLAIM_ID = D.CLAIM_ID\n"
		   			+ "		 AND TG.ORG_ID = C.ORG_ID"
		   			+ "      AND TG.CODE LIKE '%"+dealerCode+"%'))\n";
		   		};		
		   wheres += " AND O.ORDER_STATUS = "+DicConstant.HYDZT_05+" \n "+
				    "  AND O.OEM_COMPANY_ID= "+user.getOemCompanyId()+" \n"+
				    "  AND O.STATUS= "+DicConstant.YXBS_01+" \n"
				    +" AND O.FINAL_CHECK_STATUS IS NULL \n"
				    +" ORDER BY O.ORDER_ID";
		   page.setFilter(wheres);
		   BaseResultSet bs = null;
		   StringBuffer sql= new StringBuffer();
		   sql.append("SELECT O.ORDER_ID,\n" );
		   sql.append("       O.ORDER_NO,\n" );
		   sql.append("       O.PRODUCE_DATE,\n" );
		   sql.append("       O.RETURN_DATE,\n" );
		   sql.append("       O.TRANS_TYPE,\n" );
		   sql.append("       O.ORDER_STATUS,\n" );
		   sql.append("       O.ORG_ID ORG_CODE,\n" );
		   sql.append("       O.ORG_ID ORG_NAME,\n" );
		   sql.append("       O.AMOUNT,\n" );
		   sql.append("       O.REMARKS,\n" );
		   sql.append("       T1.CODE PCODE,\n" );
		   sql.append("       T1.ONAME PNAME,\n" );
		   sql.append("       NVL((SELECT NVL(SUM(D.OUGHT_COUNT), 0)\n" );
		   sql.append("             FROM SE_BU_RETURNORDER_DETAIL D\n" );
		   sql.append("            WHERE D.ORDER_ID = O.ORDER_ID\n" );
		   sql.append("            GROUP BY D.ORDER_ID),\n" );
		   sql.append("           0) AS PARTCOUNT,\n" );
		   sql.append("       NVL((SELECT NVL(SUM(D.ALREADY_IN), 0)\n" );
		   sql.append("             FROM SE_BU_RETURNORDER_DETAIL D\n" );
		   sql.append("            WHERE D.ORDER_ID = O.ORDER_ID\n" );
		   sql.append("            GROUP BY D.ORDER_ID),\n" );
		   sql.append("           0) AS ALREADY_IN\n" );
		   sql.append("  FROM SE_BU_RETURN_ORDER           O,\n" );
		   sql.append("       TM_ORG T,\n" );
		   sql.append("       TM_ORG T1");
		   bs=factory.select(sql.toString(), page);
		   bs.setFieldDic("TRANS_TYPE","HYDYSFS");
		   bs.setFieldDic("ORDER_STATUS","HYDZT");
		   bs.setFieldDateFormat("PRODUCE_DATE", "yyyy-MM");
		   bs.setFieldDateFormat("RETURN_DATE", "yyyy-MM-dd");
		   bs.setFieldDateFormat("FOCUS_DATE", "yyyy-MM-dd");
		   bs.setFieldOrgDeptSimpleName("ORG_NAME");//简称
		   bs.setFieldOrgDeptCode("ORG_CODE");
		   return bs;
	 }
	 /**
	  * 旧件终审查询
	  * @param page
	  * @param conditions
	  * @param user
	  * @return
	  * @throws SQLException
	  */
	 public BaseResultSet oldPartCheckedSearch(PageManager page ,String conditions,User user,String claimNo,String dealerCode) throws SQLException{
		 String wheres = conditions;
		 wheres +="  AND T.ORG_ID = O.ORG_ID \n"
				 +" AND T1.ORG_ID = T.PID \n";
		 if(null==claimNo||claimNo.equals("")){}else{
			 wheres +="  AND EXISTS(SELECT * FROM SE_BU_RETURNORDER_DETAIL D \n"
					 + "  WHERE D.ORDER_ID = O.ORDER_ID \n"
					 + "    AND D.CLAIM_NO ='"+claimNo+"' ) \n";
		 };
		 if(null==dealerCode||dealerCode.equals("")){}else{
			 wheres +="  AND EXISTS (SELECT 1 FROM SE_BU_RETURNORDER_DETAIL D \n"
					 + "  WHERE D.ORDER_ID = O.ORDER_ID"
					 + "	   AND D.CLAIM_NO IN (SELECT C.CLAIM_NO\n"
					 + "		FROM SE_BU_CLAIM C, TM_ORG TG\n"
					 + "    WHERE C.CLAIM_ID = D.CLAIM_ID\n"
					 + "		 AND TG.ORG_ID = C.ORG_ID"
					 + "      AND TG.CODE LIKE '%"+dealerCode+"%'))\n";
		 };		
		 wheres += " AND O.ORDER_STATUS = "+DicConstant.HYDZT_05+" \n "+
				 "  AND O.OEM_COMPANY_ID= "+user.getOemCompanyId()+" \n"+
				 "  AND O.STATUS= "+DicConstant.YXBS_01+" \n"
				 +" AND O.FINAL_CHECK_STATUS IS NULL \n"
				 +" ORDER BY O.ORDER_ID";
		 page.setFilter(wheres);
		 BaseResultSet bs = null;
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT O.ORDER_ID,\n" );
		 sql.append("       O.ORDER_NO,\n" );
		 sql.append("       O.PRODUCE_DATE,\n" );
		 sql.append("       O.RETURN_DATE,\n" );
		 sql.append("       O.TRANS_TYPE,\n" );
		 sql.append("       O.ORDER_STATUS,\n" );
		 sql.append("       O.ORG_ID ORG_CODE,\n" );
		 sql.append("       O.ORG_ID ORG_NAME,\n" );
		 sql.append("       O.AMOUNT,\n" );
		 sql.append("       O.REMARKS,\n" );
		 sql.append("       T1.CODE PCODE,\n" );
		 sql.append("       T1.ONAME PNAME,\n" );
		 sql.append("       NVL((SELECT NVL(SUM(D.OUGHT_COUNT), 0)\n" );
		 sql.append("             FROM SE_BU_RETURNORDER_DETAIL D\n" );
		 sql.append("            WHERE D.ORDER_ID = O.ORDER_ID\n" );
		 sql.append("            GROUP BY D.ORDER_ID),\n" );
		 sql.append("           0) AS PARTCOUNT,\n" );
		 sql.append("       NVL((SELECT NVL(SUM(D.ALREADY_IN), 0)\n" );
		 sql.append("             FROM SE_BU_RETURNORDER_DETAIL D\n" );
		 sql.append("            WHERE D.ORDER_ID = O.ORDER_ID\n" );
		 sql.append("            GROUP BY D.ORDER_ID),\n" );
		 sql.append("           0) AS ALREADY_IN\n" );
		 sql.append("  FROM SE_BU_RETURN_ORDER           O,\n" );
		 sql.append("       TM_ORG T,\n" );
		 sql.append("       TM_ORG T1");
		 bs=factory.select(sql.toString(), page);
		 bs.setFieldDic("TRANS_TYPE","HYDYSFS");
		 bs.setFieldDic("ORDER_STATUS","HYDZT");
		 bs.setFieldDateFormat("PRODUCE_DATE", "yyyy-MM");
		 bs.setFieldDateFormat("RETURN_DATE", "yyyy-MM-dd");
		 bs.setFieldDateFormat("FOCUS_DATE", "yyyy-MM-dd");
		 bs.setFieldOrgDeptSimpleName("ORG_NAME");//简称
		 bs.setFieldOrgDeptCode("ORG_CODE");
		 return bs;
	 }
	 /**
	  * 旧件入库查询
	  * @param page
	  * @param conditions
	  * @param user
	  * @return
	  * @throws SQLException
	  */
	 public BaseResultSet oldPartSearch(PageManager page ,String conditions,User user,String claimNo,String dealerCode,String rOrgCode) throws SQLException{
		   String wheres = conditions;
		   wheres +="  AND U.WAREHOUSE_ID = C.WAREHOUSE_ID\n"
		   			+" AND O.ORG_ID = C.ORG_ID \n"
		   			+" AND T.ORG_ID = O.ORG_ID \n"
		   			+" AND T1.ORG_ID = T.PID \n";
		   		if(null==claimNo||claimNo.equals("")){}else{
		   wheres +="  AND EXISTS(SELECT * FROM SE_BU_RETURNORDER_DETAIL D \n"
		   			+ "  WHERE D.ORDER_ID = O.ORDER_ID \n"
		   			+ "    AND D.CLAIM_NO LIKE'%"+claimNo+"%' ) \n";
		   		};
		   		if(null==rOrgCode||rOrgCode.equals("")){}else{
		   			wheres +="AND T.CODE = '"+rOrgCode+"'  \n";
		   		};
		   		if(null==dealerCode||dealerCode.equals("")){}else{
		 		   wheres +="  AND EXISTS (SELECT 1 FROM SE_BU_RETURNORDER_DETAIL D \n"
		 		   			+ "  WHERE D.ORDER_ID = O.ORDER_ID"
		 		   			+ "	   AND D.CLAIM_NO IN (SELECT C.CLAIM_NO\n"
		 		   			+ "		FROM SE_BU_CLAIM C, TM_ORG TG\n"
		 		   			+ "    WHERE C.CLAIM_ID = D.CLAIM_ID\n"
		 		   			+ "		 AND TG.ORG_ID = C.ORG_ID"
		 		   			+ "      AND TG.CODE LIKE '%"+dealerCode+"%'))\n";
		 		   		};		
		   wheres += "AND C.STATUS = "+DicConstant.YXBS_01+" \n"
		   			+ "AND U.STATUS = "+DicConstant.YXBS_01+" \n"
		   			+" AND O.ORDER_STATUS = "+DicConstant.HYDZT_05+" \n "+
				    "  AND O.OEM_COMPANY_ID= "+user.getOemCompanyId()+" \n"+
				    "  AND O.STATUS= "+DicConstant.YXBS_01+" \n"
				    +" AND U.USER_ID = "+user.getUserId()+" \n"
				    +" AND O.FINAL_CHECK_STATUS IS NULL \n"
				    +" ORDER BY O.ORDER_ID";
		   page.setFilter(wheres);
		   BaseResultSet bs = null;
		   StringBuffer sql= new StringBuffer();
		   sql.append("SELECT O.ORDER_ID,\n" );
		   sql.append("       O.ORDER_NO,\n" );
		   sql.append("       O.PRODUCE_DATE,\n" );
		   sql.append("       O.RETURN_DATE,\n" );
		   sql.append("       O.TRANS_TYPE,\n" );
		   sql.append("       O.ORDER_STATUS,\n" );
		   sql.append("       O.ORG_ID ORG_CODE,\n" );
		   sql.append("       O.ORG_ID ORG_NAME,\n" );
		   sql.append("       O.AMOUNT,\n" );
		   sql.append("       O.REMARKS,\n" );
		   sql.append("       T1.CODE PCODE,\n" );
		   sql.append("       T1.ONAME PNAME,\n" );
		   sql.append("       NVL((SELECT NVL(SUM(D.OUGHT_COUNT), 0)\n" );
		   sql.append("             FROM SE_BU_RETURNORDER_DETAIL D\n" );
		   sql.append("            WHERE D.ORDER_ID = O.ORDER_ID\n" );
		   sql.append("            GROUP BY D.ORDER_ID),\n" );
		   sql.append("           0) AS PARTCOUNT,\n" );
		   sql.append("       NVL((SELECT NVL(SUM(D.ALREADY_IN), 0)\n" );
		   sql.append("             FROM SE_BU_RETURNORDER_DETAIL D\n" );
		   sql.append("            WHERE D.ORDER_ID = O.ORDER_ID\n" );
		   sql.append("            GROUP BY D.ORDER_ID),\n" );
		   sql.append("           0) AS ALREADY_IN\n" );
		   sql.append("  FROM SE_BU_RETURN_ORDER           O,\n" );
		   sql.append("       SE_BU_WAREHOUSE_USER         U,\n" );
		   sql.append("       SE_BU_WAREHOUSE_CENTROSTIGMA C,\n" );
		   sql.append("       TM_ORG T,\n" );
		   sql.append("       TM_ORG T1");
		   bs=factory.select(sql.toString(), page);
		   bs.setFieldDic("TRANS_TYPE","HYDYSFS");
		   bs.setFieldDic("ORDER_STATUS","HYDZT");
		   bs.setFieldDateFormat("PRODUCE_DATE", "yyyy-MM");
		   bs.setFieldDateFormat("RETURN_DATE", "yyyy-MM-dd");
		   bs.setFieldDateFormat("FOCUS_DATE", "yyyy-MM-dd");
		   bs.setFieldOrgDeptSimpleName("ORG_NAME");//绠�О
		   bs.setFieldOrgDeptCode("ORG_CODE");
		   return bs;
	}
	 public BaseResultSet oldPartImpSearch(PageManager page ,String conditions,User user) throws SQLException{
		   BaseResultSet bs = null;
		   StringBuffer sql= new StringBuffer();
		   sql.append("SELECT T.TMP_ID,\n" );
		   sql.append("       T.RETURN_ORDER_NO,\n" );
		   sql.append("       T.CLAIM_NO,\n" );
		   sql.append("       T.FAULT_CODE,\n" );
		   sql.append("       T.PART_CODE,\n" );
		   sql.append("       T.PART_NAME,\n" );
		   sql.append("       T.SUPPLIER_NAME,\n" );
		   sql.append("       T.SUPPLIER_CODE,\n" );
		   sql.append("       T.AMOUNT,\n" );
		   sql.append("       T.REMARKS1,\n" );
		   sql.append("       T.OLD_PART_STATUS,\n" );
		   sql.append("       T.ROW_NO,\n" );
		   sql.append("       T.CREATE_USER\n" );
		   sql.append("  FROM SE_BU_RETURN_ORDER_TMP T\n" );
		   sql.append(" WHERE "+conditions+"");
		   sql.append("   AND T.CREATE_USER = '"+user.getAccount()+"'");
		   bs=factory.select(sql.toString(), page);
		   return bs;
	 }
	 public BaseResultSet searchImport(PageManager page ,String conditions,User user) throws SQLException{
		 BaseResultSet bs = null;
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT TMP_ID,\n" );
		 sql.append("       RETURN_ORDER_NO,\n" );
		 sql.append("       CLAIM_NO,\n" );
		 sql.append("       FAULT_CODE,\n" );
		 sql.append("       PART_CODE,\n" );
		 sql.append("       PART_NAME,\n" );
		 sql.append("       SUPPLIER_NAME,\n" );
		 sql.append("       SUPPLIER_CODE,\n" );
		 sql.append("       STORAGE_COUNT,\n" );
		 sql.append("       CREATE_USER,\n" );
		 sql.append("       ROW_NO,\n" );
		 sql.append("       CLAIM_COUNT,\n" ); 
		 sql.append("       OUGHT_COUNT,\n" );
		 sql.append("       ALREADY_IN,\n" );
		 sql.append("       MISS_COUNT\n" );
		 sql.append("  FROM SE_BU_OLDPART_STORAGE_TMP");
		 sql.append(" WHERE "+conditions+"");
		 sql.append("   AND CREATE_USER = '"+user.getAccount()+"'");
		 bs=factory.select(sql.toString(), page);
		 return bs;
	 }
	 /***
	  * 查询已回运的清单
	  * @param page
	  * @param orderId
	  * @return
	  * @throws Exception
	  */
	public BaseResultSet returnPartSearch(PageManager page,String orderId,String conditions)throws Exception{
		   BaseResultSet bs = null;
		   StringBuffer sql= new StringBuffer();
		   sql.append("SELECT D.DETAIL_ID,\n" );
		   sql.append("       D.PART_ID,\n" );
		   sql.append("       D.PART_CODE,\n" );
		   sql.append("       D.PART_NAME,\n" );
		   sql.append("       D.IS_MAIN,\n" );
		   sql.append("       D.PART_AMOUNT,\n" );
		   sql.append("       D.CLAIM_NO,\n" );
		   sql.append("       D.VEHICLE_ID,\n" );
		   sql.append("       D.VIN,\n" );
		   sql.append("       D.MILEAGE,\n" );
		   sql.append("       NVL(D.MISS_COUNT,0) MISS_COUNT,\n" );
		   sql.append("       NVL(D.OUGHT_COUNT,0) OUGHT_COUNT,\n" );
		   sql.append("       NVL(D.SHOULD_COUNT,0)CLAIM_COUNT,\n" );
		   sql.append("       D.ALREADY_IN,\n" );
		   sql.append("       NVL(D.OUGHT_COUNT,0) - NVL(D.ALREADY_IN,0) QUANTITY_IN,\n" );
		   sql.append("       D.BOX_NO,\n" );
		   sql.append("       D.PROSUPPLY_ID,\n" );
		   sql.append("       D.PROSUPPLY_CODE,\n" );
		   sql.append("       D.PROSUPPLY_NAME,\n" );
		   sql.append("       D.DUTYSUPPLY_ID,\n" );
		   sql.append("       D.DUTYSUPPLY_CODE,\n" );
		   sql.append("       D.DUTYSUPPLY_NAME,\n" );
		   sql.append("       D.BROKEN_REASON,\n" );
		   sql.append("       D.REMARKS,\n" );
		   sql.append("       D.FAULT_TYPE,\n" );
		   sql.append("       D.MEASURES,\n" );
		   sql.append("       D.CHECK_DATE,\n" );
		   sql.append("       D.CHECK_USER,\n" );
		   sql.append("       NVL(D.ACTUL_COUNT,0) ACTUL_COUNT,\n" );
		   sql.append("       D.OLD_PART_STATUS,\n" );
		   sql.append("       C.CLAIM_TYPE,\n" );
		   sql.append("       C.ORG_ID ORG_CODE, \n" );
		   sql.append("       C.ORG_ID ORG_NAME\n" );
		   sql.append("  FROM SE_BU_RETURNORDER_DETAIL D ,SE_BU_CLAIM C\n" );
		   sql.append(" WHERE  "+conditions+" AND C.CLAIM_ID = D.CLAIM_ID AND D.ORDER_ID = "+orderId+"\n" );
		   sql.append(" AND D.OLD_PART_STATUS in("+DicConstant.JJZT_01+","+DicConstant.JJZT_03+","+DicConstant.JJZT_04+")\n" );
		   //sql.append(" AND D.STORAGE_STATUS IS NULL   \n" );
		   sql.append(" ORDER BY D.CLAIM_NO,D.PART_ID \n" );
		   
		   bs=factory.select(sql.toString(), page);
		   bs.setFieldDic("CLAIM_TYPE","SPDLX");
		   bs.setFieldDic("IS_MAIN","GZLB");
		   bs.setFieldDic("MEASURES","CLFS");
		   bs.setFieldDic("OLD_PART_STATUS","JJZT");
		   bs.setFieldDateFormat("CHECK_DATE", "yyyy-MM-dd");
		   bs.setFieldOrgDeptSimpleName("ORG_NAME");//简称
		   bs.setFieldOrgDeptCode("ORG_CODE");
		   return bs;
	 }
	/***
	 * 查询已回运的清单
	 * @param page
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet returnPartSearch2(PageManager page,String orderId,String conditions)throws Exception{
		BaseResultSet bs = null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT D.DETAIL_ID,\n" );
		sql.append("       D.PART_ID,\n" );
		sql.append("       D.PART_CODE,\n" );
		sql.append("       D.PART_NAME,\n" );
		sql.append("       D.IS_MAIN,\n" );
		sql.append("       D.PART_AMOUNT,\n" );
		sql.append("       D.CLAIM_NO,\n" );
		sql.append("       D.VEHICLE_ID,\n" );
		sql.append("       D.VIN,\n" );
		sql.append("       D.MILEAGE,\n" );
		sql.append("       NVL(D.MISS_COUNT,0) MISS_COUNT,\n" );
		sql.append("       NVL(D.OUGHT_COUNT,0) OUGHT_COUNT,\n" );
		sql.append("       NVL(D.SHOULD_COUNT,0) CLAIM_COUNT,\n" );
		sql.append("       NVL(D.ALREADY_IN,0) ALREADY_IN,\n" );
		sql.append("       NVL(D.OUGHT_COUNT,0) - NVL(D.ALREADY_IN,0) QUANTITY_IN,\n" );
		sql.append("       D.BOX_NO,\n" );
		sql.append("       D.PROSUPPLY_ID,\n" );
		sql.append("       D.PROSUPPLY_CODE,\n" );
		sql.append("       D.PROSUPPLY_NAME,\n" );
		sql.append("       D.DUTYSUPPLY_ID,\n" );
		sql.append("       D.DUTYSUPPLY_CODE,\n" );
		sql.append("       D.DUTYSUPPLY_NAME,\n" );
		sql.append("       D.BROKEN_REASON,\n" );
		sql.append("       D.REMARKS,\n" );
		sql.append("       D.FAULT_TYPE,\n" );
		sql.append("       D.MEASURES,\n" );
		sql.append("       D.CHECK_DATE,\n" );
		sql.append("       D.CHECK_USER,\n" );
		sql.append("       NVL(D.ACTUL_COUNT,0) ACTUL_COUNT,\n" );
		sql.append("       D.OLD_PART_STATUS,\n" );
		sql.append("       (SELECT TR.DIC_VALUE FROM DIC_TREE TR WHERE TR.ID = C.CLAIM_TYPE) CLAIM_TYPE,\n" );
		sql.append("       (SELECT TR.DIC_VALUE FROM DIC_TREE TR WHERE TR.ID = (SELECT AU.AUTHOR_TYPE FROM SE_BU_PRE_AUTHOR AU WHERE AU.AUTHOR_ID = C.PRE_AUTHOR_ID))AUTHOR_TYPE\n" );
		sql.append("  FROM SE_BU_RETURNORDER_DETAIL D ,SE_BU_CLAIM C\n" );
		sql.append(" WHERE  "+conditions+" AND C.CLAIM_ID = D.CLAIM_ID AND D.ORDER_ID = "+orderId+"\n" );
		sql.append(" AND D.OLD_PART_STATUS in("+DicConstant.JJZT_01+","+DicConstant.JJZT_03+","+DicConstant.JJZT_04+")\n" );
		sql.append(" AND D.STORAGE_STATUS IS NOT NULL   \n" );
		sql.append(" AND C.IF_STORAGE = "+DicConstant.HYDZT_05+"   \n" );
		sql.append("  ORDER BY D.CLAIM_NO,D.PART_ID  \n" );
		
		bs=factory.select(sql.toString(), page);
		bs.setFieldDic("CLAIM_TYPE","SPDLX");
		bs.setFieldDic("IS_MAIN","GZLB");
		bs.setFieldDic("MEASURES","CLFS");
		bs.setFieldDic("OLD_PART_STATUS","JJZT");
		bs.setFieldDateFormat("CHECK_DATE", "yyyy-MM-dd");
		return bs;
	}
	/***
	 * 查询已回运的清单
	 * @param page
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet returnPartSearch3(PageManager page,String orderId,String conditions)throws Exception{
		BaseResultSet bs = null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT D.DETAIL_ID,\n" );
		sql.append("       D.PART_ID,\n" );
		sql.append("       D.PART_CODE,\n" );
		sql.append("       D.PART_NAME,\n" );
		sql.append("       D.IS_MAIN,\n" );
		sql.append("       D.PART_AMOUNT,\n" );
		sql.append("       D.CLAIM_NO,\n" );
		sql.append("       D.VEHICLE_ID,\n" );
		sql.append("       D.VIN,\n" );
		sql.append("       D.MILEAGE,\n" );
		sql.append("       NVL(D.MISS_COUNT,0) MISS_COUNT,\n" );
		sql.append("       NVL(D.OUGHT_COUNT,0) OUGHT_COUNT,\n" );
		sql.append("       NVL(D.MISS_COUNT,0)+NVL(D.OUGHT_COUNT,0) CLAIM_COUNT,\n" );
		sql.append("       NVL(D.ALREADY_IN,0) ALREADY_IN,\n" );
		sql.append("       NVL(D.OUGHT_COUNT,0) - NVL(D.ALREADY_IN,0) QUANTITY_IN,\n" );
		sql.append("       D.BOX_NO,\n" );
		sql.append("       D.PROSUPPLY_ID,\n" );
		sql.append("       D.PROSUPPLY_CODE,\n" );
		sql.append("       D.PROSUPPLY_NAME,\n" );
		sql.append("       D.DUTYSUPPLY_ID,\n" );
		sql.append("       D.DUTYSUPPLY_CODE,\n" );
		sql.append("       D.DUTYSUPPLY_NAME,\n" );
		sql.append("       D.BROKEN_REASON,\n" );
		sql.append("       D.REMARKS,\n" );
		sql.append("       D.FAULT_TYPE,\n" );
		sql.append("       D.MEASURES,\n" );
		sql.append("       D.CHECK_DATE,\n" );
		sql.append("       D.CHECK_USER,\n" );
		sql.append("       NVL(D.ACTUL_COUNT,0) ACTUL_COUNT,\n" );
		sql.append("       D.OLD_PART_STATUS,\n" );
		sql.append("       C.CLAIM_TYPE\n" );
		sql.append("  FROM SE_BU_RETURNORDER_DETAIL D ,SE_BU_CLAIM C\n" );
		sql.append(" WHERE  "+conditions+" AND C.CLAIM_ID = D.CLAIM_ID AND D.ORDER_ID = "+orderId+"\n" );
		sql.append(" AND D.STORAGE_STATUS IS NOT NULL   \n" );
		sql.append(" AND C.IF_STORAGE = "+DicConstant.HYDZT_05+"   \n" );
		sql.append("  ORDER BY D.CLAIM_NO,D.PART_ID  \n" );
		
		bs=factory.select(sql.toString(), page);
		bs.setFieldDic("CLAIM_TYPE","SPDLX");
		bs.setFieldDic("IS_MAIN","GZLB");
		bs.setFieldDic("MEASURES","CLFS");
		bs.setFieldDic("OLD_PART_STATUS","JJZT");
		bs.setFieldDateFormat("CHECK_DATE", "yyyy-MM-dd");
		return bs;
	}
	 /**
	  * 根据明细ID，查询已入库数量
	  * @param detailId
	  * @return
	  * @throws Exception
	  */
	 public QuerySet getOldPartDetail(String detailId)throws Exception{
		 QuerySet qs = null;
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT T.CLAIM_ID, NVL(T.OUGHT_COUNT, 0) OUGHT_COUNT,NVL(ALREADY_IN,0) ALREADY_IN,NVL(T.ACTUL_COUNT, 0) ACTUL_COUNT,T.CLAIM_NO,NVL(T.MISS_COUNT,0)MISS_COUNT,NVL(T.SHOULD_COUNT,0)SHOULD_COUNT\n" );
		 sql.append("  FROM SE_BU_RETURNORDER_DETAIL T\n" );
		 sql.append(" WHERE T.DETAIL_ID ="+detailId+"");
    	 qs = factory.select(null, sql.toString());
    	 return qs;
	 }
	 /**
	  * 根据明细ID，查询已入库数量
	  * @param detailId
	  * @return
	  * @throws Exception
	  */
	 public QuerySet expData1(PageManager page,User user,String rowNo)throws Exception{
		 QuerySet qs = null;
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT TMP_ID,\n" );
		 sql.append("       RETURN_ORDER_NO,\n" );
		 sql.append("       CLAIM_NO,\n" );
		 sql.append("       FAULT_CODE,\n" );
		 sql.append("       PART_CODE,\n" );
		 sql.append("       PART_NAME,\n" );
		 sql.append("       SUPPLIER_NAME,\n" );
		 sql.append("       SUPPLIER_CODE,\n" );
		 sql.append("       STORAGE_COUNT,\n" );
		 sql.append("       CREATE_USER,\n" );
		 sql.append("       ROW_NO,\n" );
		 sql.append("       CLAIM_COUNT,\n" ); 
		 sql.append("       OUGHT_COUNT,\n" );
		 sql.append("       MISS_COUNT,\n" );
		 sql.append("       ALREADY_IN\n" );
		 sql.append("  FROM SE_BU_OLDPART_STORAGE_TMP");
		 sql.append(" WHERE "+rowNo+"");
		 sql.append("   AND CREATE_USER = '"+user.getAccount()+"'");
		 qs = factory.select(null, sql.toString());
		 return qs;
	 }
	 
	 /**
	  * 主表更新
	  * @param vo
	  * @return
	  * @throws Exception
	  */
	 public boolean returnOldPartUpdate(SeBuReturnOrderVO vo)throws Exception{
		 return factory.update(vo);
	 }
	 
	 /**
	  * 入库完成更新 索赔单表旧件终审入库日期
	  * @param orderId
	  * @return
	  * @throws Exception
	  */
	 public boolean claimUpdate(String orderId)throws Exception{
		 StringBuffer sql= new StringBuffer();
		 sql.append("UPDATE SE_BU_CLAIM C\n" );
		 sql.append("   SET C.OLDPART_FINAL_DATE = SYSDATE\n" );
		 sql.append(" WHERE EXISTS (SELECT 1 \n" );
		 sql.append("          FROM SE_BU_RETURNORDER_DETAIL D\n" );
		 sql.append("         WHERE D.ORDER_ID = "+orderId+"\n" );
		 sql.append("           AND D.CLAIM_ID = C.CLAIM_ID)");
		 return factory.update(sql.toString(), null);
	 }
	 /**
	  * 入库完成更新 索赔单表旧件终审入库日期
	  * @param orderId
	  * @return
	  * @throws Exception
	  */
	 public boolean updateFaultPartCount(String partId,String cId,String prosupplyId,String claimDtlId,String oughtCount)throws Exception{
		 StringBuffer sql= new StringBuffer();
		 sql.append("UPDATE SE_BU_CLAIM_FAULT_PART T\n" );
		 sql.append("   SET T.OUGHT_COUNT = "+oughtCount+"\n" );
		 sql.append(" WHERE T.CLAIM_ID ="+cId+"\n" );
		 sql.append("   AND T.OLD_PART_ID ="+partId+"\n" );
		 sql.append("   AND T.OLD_SUPPLIER_ID ="+prosupplyId+"\n" );
		 sql.append("   AND T.CLAIM_DTL_ID ="+claimDtlId+"");
		 return factory.update(sql.toString(), null);
	 }
	 /**
	  * 更新旧件库存表 （存在更新，不存在插入） T表中数据和USING 后面检索出的数据比较 
	  * @param detailId
	  * @param sl
	  * @param user
	  * @return
	  * @throws Exception
	  */
	 public boolean insertReturnStore(String detailId,String rksl,User user)throws Exception{
		 StringBuffer sql= new StringBuffer();
		 sql.append("MERGE INTO SE_BU_RETURN_STORAGE T\n" );
		// sql.append("    USING (SELECT A.*,W.AREA_ID, W.AREA_CODE, W.AREA_NAME FROM SE_BU_RETURNORDER_DETAIL A,SE_BA_WAREHOUSE_SUPPLIER S,SE_BA_WAREHOUSE_AREA W  WHERE A.DETAIL_ID = "+detailId+" AND A.PROSUPPLY_ID=S.SUPPLIER_ID AND S.AREA_ID=W.AREA_ID ) T1\n" );
		 sql.append("    USING (SELECT A.* FROM SE_BU_RETURNORDER_DETAIL A  WHERE A.DETAIL_ID = "+detailId+" ) T1\n" );
		 sql.append("    ON (T.PART_ID = T1.PART_ID AND T.SUPPLIER_ID=T1.PROSUPPLY_ID )\n" );
		 sql.append("    WHEN MATCHED THEN\n" );
		 sql.append("    UPDATE\n" );
		 sql.append("     SET T.SUM_AMOUNT     = T.SUM_AMOUNT + "+rksl+",\n" );
		 sql.append("         T.SURPLUS_AMOUNT = T.SURPLUS_AMOUNT + "+rksl+",\n" );
		 sql.append("         T.UPDATE_USER = '"+user.getAccount()+"',\n" );
		 sql.append("         T.UPDATE_TIME = SYSDATE, \n" );
		 sql.append("         T.STORAGE_MONTH = SYSDATE \n" );
		 sql.append("       WHERE T.PART_ID = T1.PART_ID\n" );
		 sql.append("    WHEN NOT MATCHED THEN\n" );
		 sql.append("      INSERT\n" );
		 sql.append("        (T.STORAGE_ID,\n" );
		 sql.append("         T.PART_ID,\n" );
		 sql.append("         T.PART_CODE,\n" );
		 sql.append("         T.PART_NAME,\n" );
		 sql.append("         T.SUPPLIER_ID,\n" );
		 sql.append("         T.SUPPLIER_NAME,\n" );
		 sql.append("         T.SUPPLIER_CODE,\n" );
		 sql.append("         T.SUM_AMOUNT,\n" );
		 sql.append("         T.SURPLUS_AMOUNT,\n" );
		 sql.append("         T.OUT_AMOUNT,\n" );
		 sql.append("         T.CREATE_USER,\n" );
		 sql.append("         T.CREATE_TIME,\n" );
		 sql.append("         T.COMPANY_ID,\n" );
		 sql.append("         T.OEM_COMPANY_ID,\n" );
		 sql.append("         T.STORAGE_MONTH,\n" );
		 sql.append("         T.STATUS) \n" );
		// sql.append("         T.WAREHOUSE_ID,\n" );
		// sql.append("         T.WAREHOUSE_CODE,\n" );
		// sql.append("         T.WAREHOUSE_NAME\n" );
		 sql.append("      VALUES\n" );
		 sql.append("        (F_GETID(),\n" );
		 sql.append("         T1.PART_ID,\n" );
		 sql.append("         T1.PART_CODE,\n" );
		 sql.append("         T1.PART_NAME,\n" );
		 sql.append("         T1.PROSUPPLY_ID,\n" );
		 sql.append("         T1.PROSUPPLY_NAME,\n" );
		 sql.append("         T1.PROSUPPLY_CODE,\n" );
		 sql.append("         "+rksl+",\n" );
		 sql.append("         "+rksl+",\n" );
		 sql.append("         0,\n" );
		 sql.append("         '"+user.getAccount()+"',\n" );
		 sql.append("         SYSDATE,\n" );
		 sql.append("         "+user.getCompanyId()+",\n" );
		 sql.append("         "+user.getOemCompanyId()+",\n" );
		 sql.append("         SYSDATE,\n" );
		 sql.append("         "+DicConstant.YXBS_01+")\n");
		// sql.append("         T1.AREA_ID,\n" );
		// sql.append("         T1.AREA_CODE,\n" );
		// sql.append("         T1.AREA_NAME" );
		 return factory.update(sql.toString(), null);
	 }
	 /**
	  * 明细更新
	  * @param vo
	  * @return
	  * @throws Exception
	  */
	 public boolean updateDetail(SeBuReturnorderDetailVO vo)throws Exception{
		 return factory.update(vo);
	 }
	 public boolean updateCheckDate(SeBuClaimVO vo)throws Exception{
		 return factory.update(vo);
	 }
	 
	 public boolean updateStatus(SeBuReturnOrderVO vo)throws Exception{
		 return factory.update(vo);
	 }
	 
	 /**
	  * 查询是否都回运
	  * @param orderId
	  * @return
	  * @throws Exception
	  */
	 public QuerySet getCount(String orderId)throws Exception{
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT COUNT(T.DETAIL_ID) COU \n" );
		 sql.append("  FROM SE_BU_RETURNORDER_DETAIL T\n" );
		 sql.append(" WHERE T.OUGHT_COUNT != NVL(T.ALREADY_IN, 0)\n" );
		 sql.append(" AND T.ORDER_ID="+orderId+"");
		 return factory.select(null, sql.toString());
	 }
	 /**
	  * 查询是否都回运
	  * @param orderId
	  * @return
	  * @throws Exception
	  */
	 public QuerySet checklist2(String claimId)throws Exception{
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT *\n" );
		 sql.append("  FROM SE_BU_RETURNORDER_DETAIL D\n" );
		 sql.append(" WHERE NOT EXISTS\n" );
		 sql.append(" (SELECT *\n" );
		 sql.append("          FROM SE_BU_RETURNORDER_DETAIL DD\n" );
		 sql.append("         WHERE D.DETAIL_ID = DD.DETAIL_ID\n" );
		 sql.append("           AND DD.STORAGE_STATUS IS NOT NULL)\n" );
		 sql.append("   AND D.CLAIM_ID = "+claimId+"" );
		 return factory.select(null, sql.toString());
	 }
	 public QuerySet queryReturnNo(User user) throws Exception{
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT TMP.ROW_NO,TMP.RETURN_ORDER_NO\n" );
		 sql.append("  FROM SE_BU_RETURN_ORDER_TMP TMP\n" );
		 sql.append(" WHERE TMP.CREATE_USER = '"+user.getAccount()+"'\n" );
		 sql.append("   AND NOT EXISTS (SELECT  O.ORDER_NO\n" );
		 sql.append("          FROM SE_BU_RETURN_ORDER O\n" );
		 sql.append("         WHERE O.ORDER_NO = TMP.RETURN_ORDER_NO)");
		 return factory.select(null, sql.toString());
	 }
	 public QuerySet queryClaimNo(User user) throws Exception{
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT TMP.ROW_NO,TMP.CLAIM_NO,TMP.RETURN_ORDER_NO\n" );
		 sql.append("  FROM SE_BU_RETURN_ORDER_TMP TMP\n" );
		 sql.append(" WHERE TMP.CREATE_USER = '"+user.getAccount()+"'\n" );
		 sql.append("   AND NOT EXISTS (SELECT O.ORDER_NO\n" );
		 sql.append("          FROM SE_BU_RETURNORDER_DETAIL O, SE_BU_CLAIM C\n" );
		 sql.append("         WHERE O.CLAIM_ID = C.CLAIM_ID\n" );
		 sql.append("           AND O.ORDER_NO = TMP.RETURN_ORDER_NO)");
		 return factory.select(null, sql.toString());
	 }
	 
	 public QuerySet queryid(String claimNo,String faultCode,String partCode,String supplierCode,String returnOrderNo) throws Exception{
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT D.DETAIL_ID, D.CLAIM_ID,NVL(D.OUGHT_COUNT,0)OUGHT_COUNT,NVL(D.ACTUL_COUNT,0)ACTUL_COUNT,NVL(D.MISS_COUNT,0)MISS_COUNT,D.OLD_PART_STATUS \n" );
		 sql.append("  FROM SE_BU_RETURNORDER_DETAIL D,SE_BU_CLAIM C,SE_BU_CLAIM_FAULT F\n" );
		 sql.append(" WHERE C.CLAIM_ID = D.CLAIM_ID\n" );
		 sql.append(" AND F.CLAIM_ID = C.CLAIM_ID\n" );
		 sql.append(" AND D.CLAIM_NO ='"+claimNo+"'\n" );
		 sql.append(" AND D.PART_CODE ='"+partCode+"'\n" );
		 sql.append(" AND D.PROSUPPLY_CODE ='"+supplierCode+"'\n" );
		 sql.append(" AND D.ORDER_NO ='"+returnOrderNo+"'\n" );
		 sql.append(" AND F.FAULT_CODE ='"+faultCode+"'");
		 return factory.select(null, sql.toString());
	 }
	 public QuerySet queryNos(User user) throws Exception{
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT TMP.ROW_NO,TMP.CLAIM_NO,TMP.RETURN_ORDER_NO,TMP.FAULT_CODE,TMP.PART_CODE,TMP.SUPPLIER_CODE\n" );
		 sql.append("  FROM SE_BU_RETURN_ORDER_TMP TMP\n" );
		 sql.append(" WHERE TMP.CREATE_USER = '"+user.getAccount()+"'\n" );
		 sql.append("   AND NOT EXISTS\n" );
		 sql.append(" (SELECT 1 FROM SE_BU_RETURNORDER_DETAIL D,\n" );
		 sql.append("               SE_BU_CLAIM              C,\n" );
		 sql.append("               SE_BU_CLAIM_FAULT        T\n" );
		 sql.append("         WHERE D.CLAIM_ID = C.CLAIM_ID\n" );
		 sql.append("           AND C.CLAIM_ID = T.CLAIM_ID\n" );
		 sql.append("           AND TMP.CLAIM_NO = D.CLAIM_NO\n" );
		 sql.append("           AND TMP.RETURN_ORDER_NO = D.ORDER_NO\n" );
		 sql.append("           AND TMP.PART_CODE = D.PART_CODE\n" );
		 sql.append("           AND TMP.FAULT_CODE = T.FAULT_CODE\n" );
		 sql.append("           AND TMP.SUPPLIER_CODE = D.PROSUPPLY_CODE)");
		 return factory.select(null, sql.toString());
	 }
	 public QuerySet checkList3(User user) throws Exception{
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT COUNT(T.TMP_ID) AS COU,\n" );
		 sql.append("       T.RETURN_ORDER_NO,\n" );
		 sql.append("       T.CLAIM_NO,\n" );
		 sql.append("       T.FAULT_CODE,\n" );
		 sql.append("       T.PART_CODE,\n" );
		 sql.append("       T.SUPPLIER_CODE\n" );
		 sql.append("  FROM SE_BU_RETURN_ORDER_TMP T\n" );
		 sql.append(" WHERE T.CREATE_USER = '"+user.getAccount()+"'\n" );
		 sql.append(" GROUP BY T.RETURN_ORDER_NO,\n" );
		 sql.append("          T.CLAIM_NO,\n" );
		 sql.append("          T.FAULT_CODE,\n" );
		 sql.append("          T.PART_CODE,\n" );
		 sql.append("          T.SUPPLIER_CODE\n" );
		 sql.append("HAVING COUNT(T.TMP_ID) > 1");
			return factory.select(null, sql.toString());
		}
	 public QuerySet queryImp(User user) throws Exception{
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT TMP.TMP_ID,\n" );
		 sql.append("       TMP.RETURN_ORDER_NO,\n" );
		 sql.append("       TMP.CLAIM_NO,\n" );
		 sql.append("       TMP.FAULT_CODE,\n" );
		 sql.append("       TMP.PART_CODE,\n" );
		 sql.append("       TMP.PART_NAME,\n" );
		 sql.append("       TMP.SUPPLIER_NAME,\n" );
		 sql.append("       TMP.SUPPLIER_CODE,\n" );
		 sql.append("       TMP.AMOUNT,\n" );
		 sql.append("       TMP.REMARKS1,\n" );
		 sql.append("       TMP.OLD_PART_STATUS,\n" );
		 sql.append("       TMP.CREATE_USER,\n" );
		 sql.append("       TMP.ROW_NO\n" );
		 sql.append("  FROM SE_BU_RETURN_ORDER_TMP TMP\n" );
		 sql.append(" WHERE TMP.CREATE_USER = '"+user.getAccount()+"'");
		 return factory.select(null, sql.toString());
	 }
	 public QuerySet checkList(User user,String rowNo) throws Exception{
		 StringBuffer sql= new StringBuffer();
		   sql.append("SELECT T.TMP_ID,\n" );
		   sql.append("       T.RETURN_ORDER_NO,\n" );
		   sql.append("       T.CLAIM_NO,\n" );
		   sql.append("       T.FAULT_CODE,\n" );
		   sql.append("       T.PART_CODE,\n" );
		   sql.append("       T.PART_NAME,\n" );
		   sql.append("       T.SUPPLIER_NAME,\n" );
		   sql.append("       T.SUPPLIER_CODE,\n" );
		   sql.append("       T.AMOUNT,\n" );
		   sql.append("       T.REMARKS1,\n" );
		   sql.append("       T.OLD_PART_STATUS,\n" );
		   sql.append("       T.ROW_NO,\n" );
		   sql.append("       T.CREATE_USER\n" );
		   sql.append("  FROM SE_BU_RETURN_ORDER_TMP T\n" );
		   sql.append(" WHERE T.CREATE_USER = '"+user.getAccount()+"' "+rowNo+"");
		return factory.select(null, sql.toString());
	 }
	 public QuerySet queryStatus(User user) throws Exception{
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT TMP.ROW_NO,TMP.CLAIM_NO,TMP.RETURN_ORDER_NO,TMP.FAULT_CODE,TMP.PART_CODE,TMP.SUPPLIER_CODE\n" );
		 sql.append("  FROM SE_BU_RETURN_ORDER_TMP TMP\n" );
		 sql.append(" WHERE TMP.CREATE_USER = '"+user.getAccount()+"'\n" );
		 sql.append("   AND  EXISTS\n" );
		 sql.append(" (SELECT 1 FROM SE_BU_RETURNORDER_DETAIL D,\n" );
		 sql.append("               SE_BU_CLAIM              C,\n" );
		 sql.append("               SE_BU_CLAIM_FAULT        T\n" );
		 sql.append("         WHERE D.CLAIM_ID = C.CLAIM_ID\n" );
		 sql.append("           AND C.CLAIM_ID = T.CLAIM_ID\n" );
		 sql.append("           AND TMP.CLAIM_NO = D.CLAIM_NO\n" );
		 sql.append("           AND TMP.RETURN_ORDER_NO = D.ORDER_NO\n" );
		 sql.append("           AND TMP.PART_CODE = D.PART_CODE\n" );
		 sql.append("           AND TMP.FAULT_CODE = T.FAULT_CODE\n" );
		 sql.append("           AND TMP.SUPPLIER_CODE = D.PROSUPPLY_CODE\n" );
		 sql.append("          AND D.OLD_PART_STATUS ="+DicConstant.JJZT_02+")");
		 return factory.select(null, sql.toString());
	 }
	 /**
	  * 获取所有已回运状态的回运单
	  * @return
	  * @throws Exception
	  */
	 public QuerySet getOrder()throws Exception{
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT DISTINCT T.ORDER_ID\n" );
		 sql.append("  FROM SE_BU_RETURN_ORDER T,\n" );
		 sql.append("       SE_BU_RETURNORDER_DETAIL D,\n" );
		 sql.append("       SE_BU_CLAIM C\n" );
		 sql.append(" WHERE T.ORDER_ID = D.ORDER_ID\n" );
		 sql.append("   AND C.CLAIM_ID = D.CLAIM_ID\n" );
		 sql.append("   AND C.IF_STORAGE = "+DicConstant.HYDZT_05+"\n" );
		 sql.append("   AND T.ORDER_STATUS = "+DicConstant.HYDZT_05+"");

		 return factory.select(null, sql.toString());
	 }
	 /**
	  * 获取所有已回运状态的回运单
	  * @return
	  * @throws Exception
	  */
	 public QuerySet queryCid(String orderId)throws Exception{
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT D.CLAIM_ID FROM SE_BU_RETURNORDER_DETAIL D WHERE D.ORDER_ID = "+orderId+"\n" );
		 return factory.select(null, sql.toString());
	 }
	 public QuerySet queryClaims(String orderId) throws Exception{
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT D.DETAIL_ID,\n" );
		 sql.append("       D.PART_ID,\n" );
		 sql.append("       D.PART_CODE,\n" );
		 sql.append("       D.PART_NAME,\n" );
		 sql.append("       D.PART_AMOUNT,\n" );
		 sql.append("       D.CLAIM_NO,\n" );
		 sql.append("       D.VEHICLE_ID,\n" );
		 sql.append("       D.VIN,\n" );
		 sql.append("       D.MILEAGE,\n" );
		 sql.append("       NVL(D.OUGHT_COUNT, 0) OUGHT_COUNT,\n" );
		 sql.append("       NVL(D.MISS_COUNT, 0) MISS_COUNT,\n" );
		 sql.append("       NVL(D.ALREADY_IN, 0) ALREADY_IN,\n" );
		 sql.append("       D.OUGHT_COUNT - NVL(D.ALREADY_IN, 0) QUANTITY_IN,\n" );
		 sql.append("       D.BOX_NO,\n" );
		 sql.append("       D.PROSUPPLY_ID,\n" );
		 sql.append("       D.PROSUPPLY_CODE,\n" );
		 sql.append("       D.PROSUPPLY_NAME,\n" );
		 sql.append("       D.DUTYSUPPLY_ID,\n" );
		 sql.append("       D.DUTYSUPPLY_CODE,\n" );
		 sql.append("       D.DUTYSUPPLY_NAME,\n" );
		 sql.append("       D.CLAIM_DTL_ID,\n" );
		 sql.append("       D.BROKEN_REASON,\n" );
		 sql.append("       D.REMARKS,\n" );
		 sql.append("       D.FAULT_TYPE,\n" );
		 sql.append("       D.MEASURES,\n" );
		 sql.append("       D.CHECK_DATE,\n" );
		 sql.append("       D.CHECK_USER,\n" );
		 sql.append("       NVL(D.ACTUL_COUNT, 0) ACTUL_COUNT,\n" );
		 sql.append("       D.OLD_PART_STATUS,\n" );
		 sql.append("       C.CLAIM_TYPE,\n" );
		 sql.append("       C.CLAIM_ID\n" );
		 sql.append("  FROM SE_BU_RETURNORDER_DETAIL D, SE_BU_CLAIM C\n" );
		 sql.append(" WHERE  1=1 AND C.CLAIM_ID = D.CLAIM_ID AND C.IF_STORAGE = "+DicConstant.HYDZT_05+" AND D.ORDER_ID = "+orderId+" AND D.OLD_PART_STATUS ="+DicConstant.JJZT_01+"" );
		 return factory.select(null, sql.toString());
	 }
	 public QuerySet queryParts(String orderId,String claimNo) throws Exception{
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT  D.CLAIM_ID\n" );
		 sql.append("  FROM SE_BU_RETURNORDER_DETAIL D\n" );
		 sql.append(" WHERE D.OLD_PART_STATUS in(305801,305803,305804)\n" );
		 sql.append("   AND D.CLAIM_NO = '"+claimNo+"'\n" );
		 return factory.select(null, sql.toString());
	 }
	 /**
	  * 获取待审数量和 实发数量
	  * @param dtlId
	  * @return
	  * @throws Exception
	  */
	 public QuerySet queryAmount(String dtlId) throws Exception{
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT NVL(D.ACTUL_COUNT,0) ACTUL_COUNT,NVL(D.MISS_COUNT,0) MISS_COUNT,NVL(D.OUGHT_COUNT,0) OUGHT_COUNT,NVL(D.ALREADY_IN,0)ALREADY_IN, D.OLD_PART_STATUS FROM SE_BU_RETURNORDER_DETAIL D WHERE D.DETAIL_ID ="+dtlId+" \n" );
		 return factory.select(null, sql.toString());
	 }
	 public QuerySet queryClaimId(String claimNo) throws Exception{
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT T.CLAIM_ID FROM SE_BU_CLAIM T WHERE T.CLAIM_NO='"+claimNo+"'" );
		 return factory.select(null, sql.toString());
	 }
	 public QuerySet queryPassParts(String orderId,String claimNo) throws Exception{
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT COUNT(*) SL,C.CLAIM_ID\n" );
		 sql.append("  FROM SE_BU_RETURNORDER_DETAIL D, SE_BU_CLAIM C\n" );
		 sql.append(" WHERE 1 = 1\n" );
		 sql.append("   AND C.CLAIM_ID = D.CLAIM_ID\n" );
		 sql.append("   AND D.ORDER_ID = "+orderId+"\n" );
		 sql.append("   AND C.CLAIM_NO = '"+claimNo+"'\n" );
		 sql.append("   AND D.OLD_PART_STATUS = "+DicConstant.JJZT_02+"\n" );
		 sql.append(" GROUP BY C.CLAIM_ID");
		 return factory.select(null, sql.toString());
	 }
	 public QuerySet sumClaims(String orderId) throws Exception{
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT A.DETAIL_ID\n" );
		 sql.append("  FROM SE_BU_RETURNORDER_DETAIL A\n" );
		 sql.append(" WHERE A.ORDER_ID = "+orderId+"\n" );
		 sql.append("   AND EXISTS (SELECT 1\n" );
		 sql.append("          FROM SE_BU_CLAIM B\n" );
		 sql.append("         WHERE B.CLAIM_ID = A.CLAIM_ID\n" );
		 sql.append("           AND B.OLDPART_FINAL_DATE IS NULL)");
		 return factory.select(null, sql.toString());
	 }
	 public QuerySet download(String conditions,User user) throws Exception{
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT D.DETAIL_ID,\n" );
		 sql.append("       D.PART_ID,\n" );
		 sql.append("       D.PART_CODE,\n" );
		 sql.append("       D.PART_NAME,\n" );
		 sql.append("       D.CLAIM_NO,\n" );
		 sql.append("       D.PROSUPPLY_ID,\n" );
		 sql.append("       D.PROSUPPLY_CODE,\n" );
		 sql.append("       D.PROSUPPLY_NAME,\n" );
		 sql.append("       D.ORDER_NO,\n" );
		 sql.append("       T.FAULT_CODE,\n" );
		 sql.append("       NVL(D.SHOULD_COUNT, 0) SHOULD_COUNT,\n" );
		 sql.append("       NVL(D.OUGHT_COUNT, 0) OUGHT_COUNT,\n" );
		 sql.append("       NVL(D.MISS_COUNT, 0) MISS_COUNT,\n" );
		 sql.append("       NVL(D.ALREADY_IN, 0) ALREADY_IN,\n" );
		 sql.append("       (SELECT G.CODE FROM TM_ORG G WHERE G.ORG_ID = C.ORG_ID) CODE,\n" );
		 sql.append("       (SELECT G.ONAME FROM TM_ORG G WHERE G.ORG_ID = C.ORG_ID) NAME\n" );
		 sql.append("  FROM SE_BU_RETURNORDER_DETAIL     D,\n" );
		 sql.append("       SE_BU_WAREHOUSE_USER         U,\n" );
		 sql.append("       SE_BU_WAREHOUSE_CENTROSTIGMA W,\n" );
		 sql.append("       SE_BU_RETURN_ORDER           O,\n" );
		 sql.append("       SE_BU_CLAIM                  C,\n" );
		 sql.append("       SE_BU_CLAIM_FAULT            T,\n" );
		 sql.append("       SE_BU_CLAIM_FAULT_PART       P\n" );
		 sql.append(" WHERE "+conditions+"\n" );
		 sql.append("   AND C.CLAIM_ID = D.CLAIM_ID\n" );
		 sql.append("   AND O.ORG_ID = W.ORG_ID\n" );
		 sql.append("   AND W.WAREHOUSE_ID = U.WAREHOUSE_ID\n" );
		 sql.append("   AND O.ORDER_ID = D.ORDER_ID\n" );
		 sql.append("   AND D.OLD_PART_STATUS in (305801, 305803, 305804)\n" );
		 sql.append("   AND T.CLAIM_ID = C.CLAIM_ID\n" );
		 sql.append("   AND P.OLD_PART_ID = D.PART_ID\n" );
		 sql.append("   AND P.OLD_SUPPLIER_ID = D.PROSUPPLY_ID\n" );
		 sql.append("   AND P.CLAIM_ID = D.CLAIM_ID\n" );
		 sql.append("   AND P.CLAIM_DTL_ID = T.CLAIM_DTL_ID\n" );
		 sql.append("   AND D.STORAGE_STATUS IS NOT NULL\n" );
		 sql.append("   AND C.IF_STORAGE = 302505\n" );
		 sql.append("   AND U.USER_ID = "+user.getUserId()+"\n" );
		 sql.append(" ORDER BY D.CLAIM_NO, D.PART_ID");
		 return factory.select(null, sql.toString());
	 }
	 
	 public QuerySet storageInDownLoad(User user,String conditions,String claimNo,String dealerCode) throws Exception{
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT O.DETAIL_ID,\n" );
		 sql.append("       O.ORDER_NO,\n" );
		 sql.append("       O.PART_ID,\n" );
		 sql.append("       O.PART_CODE,\n" );
		 sql.append("       F.FAULT_CODE,\n" );
		 sql.append("       O.PART_NAME,\n" );
		 sql.append("       O.IS_MAIN,\n" );
		 sql.append("       O.PART_AMOUNT,\n" );
		 sql.append("       O.CLAIM_NO,\n" );
		 sql.append("       O.VEHICLE_ID,\n" );
		 sql.append("       O.VIN,\n" );
		 sql.append("       O.MILEAGE,\n" );
		 sql.append("       NVL(O.MISS_COUNT, 0) MISS_COUNT, --缺失旧件数量\n" );
		 sql.append("       NVL(O.OUGHT_COUNT, 0) OUGHT_COUNT, --实返旧件数量\n" );
		 sql.append("       NVL(O.SHOULD_COUNT, 0) SHOULD_COUNT, --应返旧件数量\n" );
		 sql.append("       NVL(O.ALREADY_IN, 0) ALREADY_IN, --已入库旧件数量\n" );
		 sql.append("       NVL(O.OUGHT_COUNT, 0) - NVL(O.ALREADY_IN, 0) QUANTITY_IN, --未入库旧件数量\n" );
		 sql.append("       '' BCRKS,\n" );
		 sql.append("       O.BOX_NO,\n" );
		 sql.append("       O.PROSUPPLY_ID,\n" );
		 sql.append("       O.PROSUPPLY_CODE,\n" );
		 sql.append("       O.PROSUPPLY_NAME,\n" );
		 sql.append("       O.DUTYSUPPLY_ID,\n" );
		 sql.append("       O.DUTYSUPPLY_CODE,\n" );
		 sql.append("       O.DUTYSUPPLY_NAME,\n" );
		 sql.append("       O.BROKEN_REASON,\n" );
		 sql.append("       O.REMARKS,\n" );
		 sql.append("       O.FAULT_TYPE,\n" );
		 sql.append("       O.MEASURES,\n" );
		 sql.append("       O.CHECK_DATE,\n" );
		 sql.append("       O.CHECK_USER,\n" );
		 sql.append("       NVL(O.ACTUL_COUNT, 0) ACTUL_COUNT,\n" );
		 sql.append("       O.OLD_PART_STATUS,\n" );
		 sql.append("       C.CLAIM_TYPE,\n" );
		 sql.append("       (SELECT G.CODE FROM TM_ORG G WHERE G.ORG_ID = C.ORG_ID) CODE,\n" );
		 sql.append("       (SELECT G.ONAME FROM TM_ORG G WHERE G.ORG_ID = C.ORG_ID) NAME\n" );
		 sql.append("  FROM SE_BU_RETURNORDER_DETAIL     O,\n" );
		 sql.append("       SE_BU_CLAIM                  C,\n" );
		 sql.append("       SE_BU_RETURN_ORDER           RO,\n" );
		 sql.append("       SE_BU_WAREHOUSE_CENTROSTIGMA C1,\n" );
		 sql.append("       SE_BU_CLAIM_FAULT            F,\n" );
		 sql.append("       TM_ORG                       T1,\n" );
		 sql.append("       TM_ORG                       T");
		 sql.append(" WHERE "+conditions+"\n" );
		 sql.append("   AND C.CLAIM_ID = O.CLAIM_ID\n" );
		 sql.append("   AND F.CLAIM_DTL_ID = O.CLAIM_DTL_ID\n" );
		 sql.append("   AND RO.ORDER_ID = O.ORDER_ID\n" );
		 sql.append("   AND T1.ORG_ID = C.ORG_ID\n" );
		 sql.append("   AND RO.ORG_ID = C1.ORG_ID\n" );
		 sql.append("   AND RO.ORG_ID = T.ORG_ID\n" );
		 sql.append("   AND C1.STATUS = 100201\n" );
		 sql.append("   AND WAREHOUSE_ID = (SELECT U.WAREHOUSE_ID\n" );
		 sql.append("                        FROM SE_BU_WAREHOUSE_USER U\n" );
		 sql.append("                       WHERE U.USER_ID = "+user.getUserId()+")");
		 if(null==claimNo||claimNo.equals("")){
		 }else{
			 sql.append(" AND  O.CLAIM_NO = '"+claimNo+"'\n" );
		 }
		 if(null==claimNo||claimNo.equals("")){
		 }else{
			 sql.append(" AND  O.CLAIM_NO = '"+claimNo+"'\n" );
		 }
		 if(null==dealerCode||dealerCode.equals("")){}else{
			 sql.append(" AND T1.CODE LIKE'%"+dealerCode+"%'\n" );
		 }
		 sql.append("   AND RO.ORDER_STATUS = 302505\n" );
		 sql.append("   AND O.OLD_PART_STATUS in (305801, 305803, 305804)\n" );
		 sql.append(" ORDER BY O.ORDER_ID, O.CLAIM_ID");

		 return factory.select(null, sql.toString());
	 }
	 public QuerySet expData(User user,String conditions) throws Exception{
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT T.TMP_ID,\n" );
		 sql.append("       T.RETURN_ORDER_NO,\n" );
		 sql.append("       T.CLAIM_NO,\n" );
		 sql.append("       T.FAULT_CODE,\n" );
		 sql.append("       T.PART_CODE,\n" );
		 sql.append("       T.PART_NAME,\n" );
		 sql.append("       T.SUPPLIER_NAME,\n" );
		 sql.append("       T.SUPPLIER_CODE,\n" );
		 sql.append("       T.AMOUNT,\n" );
		 sql.append("       T.REMARKS1,\n" );
		 sql.append("       T.OLD_PART_STATUS,\n" );
		 sql.append("       T.ROW_NO,\n" );
		 sql.append("       T.CREATE_USER\n" );
		 sql.append("  FROM SE_BU_RETURN_ORDER_TMP T\n" );
		 sql.append(" WHERE T.ROW_NO IN ("+conditions+")\n" );
		 sql.append("   AND T.CREATE_USER = '"+user.getAccount()+"'");
		 return factory.select(null, sql.toString());
	 }
	 /**
     * 更新不需要回运旧件的审核通过数量
     * @param claimId
     * @return
     * @throws Exception
     */
    public boolean updateOught(String claimId)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("UPDATE SE_BU_CLAIM_FAULT_PART P\n" );
    	sql.append("   SET P.OUGHT_COUNT = P.OLD_PART_COUNT\n" );
    	sql.append(" WHERE P.CLAIM_ID = "+claimId+"\n" );
    	sql.append("   AND EXISTS (SELECT 1\n" );
    	sql.append("          FROM PT_BA_INFO I\n" );
    	sql.append("         WHERE P.OLD_PART_ID = I.PART_ID\n" );
    	sql.append("           AND I.IF_RETURN = 100102)");
    	return factory.update(sql.toString(), null);
    }
    /**
     * 更新维修和加装的审核数量
     * @param claimId
     * @return
     * @throws Exception
     */
    public boolean updateOught1(String claimId)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("UPDATE SE_BU_CLAIM_FAULT_PART P\n" );
    	sql.append("   SET P.OUGHT_COUNT = DECODE(P.OLD_PART_COUNT,\n" );
    	sql.append("                              '',\n" );
    	sql.append("                              P.NEW_PART_COUNT,\n" );
    	sql.append("                              P.OLD_PART_COUNT)\n" );
    	sql.append(" WHERE P.CLAIM_ID = "+claimId+"\n" );
    	sql.append("   AND P.MEASURES IN (300601, 300603)");
    	return factory.update(sql.toString(), null);
    }
    public QuerySet queryCount(User user,String rowNo) throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.STORAGE_COUNT,\n" );
    	sql.append("       D.DETAIL_ID\n" );
    	sql.append("  FROM SE_BU_OLDPART_STORAGE_TMP T,\n" );
    	sql.append("       SE_BU_RETURNORDER_DETAIL  D,\n" );
    	sql.append("       SE_BU_CLAIM_FAULT         F\n" );
    	sql.append(" WHERE D.CLAIM_DTL_ID = F.CLAIM_DTL_ID\n" );
    	sql.append("   AND D.CLAIM_ID = F.CLAIM_ID\n" );
    	sql.append("   AND T.RETURN_ORDER_NO = D.ORDER_NO\n" );
    	sql.append("   AND T.CLAIM_NO = D.CLAIM_NO\n" );
    	sql.append("   AND T.PART_CODE = D.PART_CODE\n" );
    	sql.append("   AND T.SUPPLIER_CODE = D.PROSUPPLY_CODE\n" );
    	sql.append("   AND T.FAULT_CODE = F.FAULT_CODE\n" );
		sql.append("   AND T.CREATE_USER = '"+user.getAccount()+"' "+rowNo+" ");
		return factory.select(null, sql.toString());
	 }
}
