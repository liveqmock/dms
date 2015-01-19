package com.org.dms.dao.part.salesMng.orderMng;

import com.org.dms.common.DicConstant;
import com.org.framework.common.DBFactory;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;

public class PartOrderImportCheckDao {

	/**
	 * 订单主信息查询
	 */
   public QuerySet partOrderInfoSearch(String orderId,DBFactory factory) throws Exception {
   	   String sql = "SELECT * FROM PT_BU_SALE_ORDER WHERE ORDER_ID="+orderId+"\n";
       return factory.select(null,sql);
   }
   /**
    * 查询是否有不存在的配件
    */
   public QuerySet partBaInfoSearch(User user,DBFactory factory) throws Exception {
	   StringBuffer sql = new StringBuffer();
	   sql.append(" SELECT A.ROW_NO,A.PART_CODE FROM PT_BU_SALE_ORDER_DTL_TMP A WHERE A.USER_ACCOUNT='"+user.getAccount()+"' \n");
	   sql.append(" AND NOT EXISTS(SELECT 1 FROM PT_BA_INFO B WHERE A.PART_CODE=B.PART_CODE AND B.PART_STATUS <>"+DicConstant.PJZT_02+"\n");
	   sql.append(" AND B.PART_TYPE <> "+DicConstant.PJLB_03+" " +
//	   		"AND B.IF_BOOK = "+DicConstant.SF_01+"" +
	   				")\n");
	   return factory.select(null,sql.toString());
   }
   
   public QuerySet armyPartBaInfoSearch(User user,DBFactory factory) throws Exception {
	   StringBuffer sql = new StringBuffer();
	   sql.append(" SELECT A.ROW_NO,A.PART_CODE FROM PT_BU_SALE_ORDER_DTL_TMP A WHERE A.USER_ACCOUNT='"+user.getAccount()+"' \n");
	   sql.append(" AND NOT EXISTS(SELECT 1 FROM PT_BA_INFO B WHERE A.PART_CODE=B.PART_CODE AND B.PART_STATUS <>"+DicConstant.PJZT_02+"\n");
	   sql.append(" AND B.PART_TYPE <> "+DicConstant.PJLB_02+" " +
//	   		"AND B.IF_BOOK = "+DicConstant.SF_01+"" +
	   				")\n");
	   return factory.select(null,sql.toString());
   }
   /**
    * 查询是否有不符合要求的配件
    */
   public QuerySet partInfoSearch(User user,String orderType,String promId,String directTypeId,DBFactory factory) throws Exception {
	   StringBuffer sql = new StringBuffer();
	   sql.append(" SELECT A.ROW_NO,A.PART_CODE FROM PT_BU_SALE_ORDER_DTL_TMP A WHERE A.USER_ACCOUNT='"+user.getAccount()+"' \n");
	   sql.append(" AND NOT EXISTS(SELECT 1 FROM PT_BA_INFO B WHERE A.PART_CODE=B.PART_CODE AND B.PART_STATUS <>"+DicConstant.PJZT_02+"\n");
	   sql.append(" AND B.PART_TYPE <> "+DicConstant.PJLB_03+" AND B.IF_BOOK = "+DicConstant.SF_01+"\n");
	   if(DicConstant.DDLX_01.equals(orderType)||DicConstant.DDLX_02.equals(orderType)||DicConstant.DDLX_03.equals(orderType)||DicConstant.DDLX_07.equals(orderType)){
		   sql.append(" AND B.IF_DIRECT ="+DicConstant.SF_02+" AND B.IF_ASSEMBLY="+DicConstant.SF_02+"\n");
	   }
	   if(DicConstant.DDLX_04.equals(orderType)){
		   sql.append(" AND B.IF_DIRECT ="+DicConstant.SF_02+" AND B.IF_ASSEMBLY="+DicConstant.SF_01+"\n");
	   }
	   if(DicConstant.DDLX_05.equals(orderType)){
		   sql.append(" AND B.IF_DIRECT ="+DicConstant.SF_01+" AND B.DIRECT_TYPE_ID = "+directTypeId+" AND B.IF_ASSEMBLY="+DicConstant.SF_02+"\n");
	   }
	   sql.append(")\n");
	   if(DicConstant.DDLX_06.equals(orderType)){
		   sql.append(" AND NOT EXISTS(SELECT 1 FROM PT_BU_PROMOTION_PART C WHERE A.PART_CODE = C.PART_CODE AND C.PROM_ID = "+promId+")\n");
	   }
	   return factory.select(null,sql.toString());
   }
   /**
    * 查询配件是否存在指定供应商
    */
   public QuerySet supplierCheck(User user,DBFactory factory)throws Exception {
	   StringBuilder sql= new StringBuilder();
	   sql.append("SELECT B.ROW_NO,B.PART_CODE\n" );
	   sql.append("   FROM PT_BA_INFO A, PT_BU_SALE_ORDER_DTL_TMP B\n" );
	   sql.append("  WHERE A.IF_SUPLY = '" + DicConstant.SF_01+ "'\n" );
	   sql.append("    AND B.PART_CODE = A.PART_CODE\n" );
	   sql.append("    AND USER_ACCOUNT = '" + user.getAccount()+ "' AND B.SUPPLIER_CODE IS NULL");

	   return factory.select(null,sql.toString());
   }
   /**
    * 查询供应商是否存在
    */
   public QuerySet supplierBaInfoSearch(User user,DBFactory factory)throws Exception {
	   StringBuffer sql = new StringBuffer();
	   sql.append(" SELECT A.ROW_NO,A.SUPPLIER_CODE FROM PT_BU_SALE_ORDER_DTL_TMP A WHERE A.USER_ACCOUNT='"+user.getAccount()+"' AND A.SUPPLIER_CODE IS NOT NULL\n");
	   sql.append(" AND NOT EXISTS(SELECT 1 FROM PT_BA_SUPPLIER B WHERE A.SUPPLIER_CODE=B.SUPPLIER_CODE AND B.PART_IDENTIFY = "+DicConstant.YXBS_01+")\n");
	   return factory.select(null,sql.toString());
   }
   /**
    * 指定供应商查询供货关系是否存在
    */
   public QuerySet partSupplierBaInfoSearch(User user,DBFactory factory)throws Exception {
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT A.ROW_NO,A.PART_CODE,A.SUPPLIER_CODE FROM PT_BU_SALE_ORDER_DTL_TMP A,PT_BA_INFO B,PT_BA_SUPPLIER C\n" );
	   sql.append("WHERE A.PART_CODE = B.PART_CODE AND A.SUPPLIER_CODE = C.SUPPLIER_CODE AND B.IF_SUPLY ="+DicConstant.SF_01+" AND C.PART_IDENTIFY = "+DicConstant.YXBS_01+" AND A.SUPPLIER_CODE IS NOT NULL AND A.USER_ACCOUNT='"+user.getAccount()+"'\n" );
	   sql.append("AND NOT EXISTS (SELECT 1 FROM PT_BA_PART_SUPPLIER_RL D WHERE B.PART_ID= D.PART_ID AND D.SUPPLIER_ID = C.SUPPLIER_ID AND D.PART_IDENTIFY = "+DicConstant.YXBS_01+")\n");
	   return factory.select(null,sql.toString());
   }
   /**
    * 查询订购数量
    */
   public QuerySet partOrderCountSearch(User user,DBFactory factory)throws Exception {
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT A.ROW_NO,A.PART_CODE,A.COUNT,B.MIN_PACK FROM PT_BU_SALE_ORDER_DTL_TMP A,PT_BA_INFO B WHERE A.PART_CODE = B.PART_CODE AND A.USER_ACCOUNT='"+user.getAccount()+"'\n" );
	   return factory.select(null,sql.toString());
   }
   
   public QuerySet partArmyOrderCountSearch(User user,DBFactory factory)throws Exception {
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT A.ROW_NO,A.PART_CODE,A.COUNT,B.MIN_PACK FROM PT_BU_SALE_ORDER_DTL_TMP A,PT_BA_INFO B WHERE A.PART_CODE = B.PART_CODE AND A.USER_ACCOUNT='"+user.getAccount()+"' AND NVL(A.COUNT,0)>0\n" );
	   return factory.select(null,sql.toString());
   }
   /**
    * 查询配件价格
    */
   public QuerySet partOrderPriceSearch(User user,DBFactory factory,String orderType)throws Exception {
	   StringBuilder sql= new StringBuilder();
	   sql.append("SELECT A.ROW_NO,A.PART_CODE\n" );
	   sql.append("  FROM PT_BU_SALE_ORDER_DTL_TMP A, PT_BA_INFO B\n" );
	   sql.append(" WHERE A.PART_CODE = B.PART_CODE\n" );
	   sql.append("   AND A.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
	   if (DicConstant.DDLX_07.equals(orderType)) {
		   // 直营订单
		   sql.append("   AND NVL(B.RETAIL_PRICE, 0) <= 0");
	   } else if (DicConstant.DDLX_10.equals(orderType)) {
		   // 技术升级订单   
		   sql.append("   AND NVL(B.PLAN_PRICE, 0) <= 0");
	   } else {
		   // 普通订单
		   sql.append("   AND NVL(B.SALE_PRICE, 0) <= 0");
	   }

	   return factory.select(null,sql.toString());
   }
   
   public QuerySet partOrderArmyPriceSearch(User user,DBFactory factory)throws Exception {
	   StringBuilder sql= new StringBuilder();
	   sql.append("SELECT A.ROW_NO,A.PART_CODE\n" );
	   sql.append("  FROM PT_BU_SALE_ORDER_DTL_TMP A, PT_BA_INFO B\n" );
	   sql.append(" WHERE A.PART_CODE = B.PART_CODE\n" );
	   sql.append("   AND A.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
	   sql.append("   AND NVL(B.ARMY_PRICE, 0) <= 0");

	   return factory.select(null,sql.toString());
   }
}
