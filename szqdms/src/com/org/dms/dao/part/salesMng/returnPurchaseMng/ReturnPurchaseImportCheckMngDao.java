package com.org.dms.dao.part.salesMng.returnPurchaseMng;

import com.org.dms.common.DicConstant;
import com.org.framework.common.DBFactory;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;

public class ReturnPurchaseImportCheckMngDao {

	/**
	 * 订单主信息查询
	 */
   public QuerySet partOrderInfoSearch(String orderId,DBFactory factory) throws Exception {
   	   String sql = "SELECT * FROM PT_BU_RETURN_APPLY WHERE RETURN_ID="+orderId+"\n";
       return factory.select(null,sql);
   }
   /**
    * 查询是否有不存在的配件
    */
   public QuerySet partBaInfoSearch(User user,DBFactory factory,String returnId) throws Exception {
	   StringBuffer sql = new StringBuffer();
	   sql.append(" SELECT A.TMP_NO,A.PART_CODE FROM PT_BU_RETURN_APPLY_DTL_TMP A WHERE A.USER_ACCOUNT='"+user.getAccount()+"' \n");
	   sql.append("AND NOT EXISTS(SELECT 1\n" );
	   sql.append("  FROM PT_BA_INFO B, PT_BU_DEALER_STOCK D\n" );
	   sql.append(" WHERE 1 = 1\n" );
	   sql.append("   AND B.PART_ID = D.PART_ID\n" );
	   sql.append("   AND NVL(D.AVAILABLE_AMOUNT, 0) > 0\n" );
	   sql.append("   AND B.PART_ID NOT IN\n" );
	   sql.append("       (SELECT PART_ID\n" );
	   sql.append("          FROM PT_BU_RETURN_APPLY_DTL\n" );
	   sql.append("         WHERE RETURN_ID = '"+returnId+"')\n" );
	   sql.append("   AND D.ORG_ID = '"+user.getOrgId()+"')");
	   return factory.select(null,sql.toString());
   }
   /**
    * 查询供应商是否存在
    */
   public QuerySet supplierBaInfoSearch(User user,DBFactory factory)throws Exception {
	   StringBuffer sql = new StringBuffer();
	   sql.append(" SELECT A.TMP_NO,A.SUPPLIER_CODE FROM PT_BU_RETURN_APPLY_DTL_TMP A WHERE A.USER_ACCOUNT='"+user.getAccount()+"' AND A.SUPPLIER_CODE IS NOT NULL\n");
	   sql.append(" AND NOT EXISTS(SELECT 1 FROM PT_BA_SUPPLIER B WHERE A.SUPPLIER_CODE=B.SUPPLIER_CODE AND B.PART_IDENTIFY = "+DicConstant.YXBS_01+")\n");
	   return factory.select(null,sql.toString());
   }
   /**
    * 指定供应商查询供货关系是否存在
    */
   public QuerySet partSupplierBaInfoSearch(User user,DBFactory factory)throws Exception {
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT A.TMP_NO,A.PART_CODE,A.SUPPLIER_CODE FROM PT_BU_RETURN_APPLY_DTL_TMP A,PT_BA_INFO B,PT_BA_SUPPLIER C\n" );
	   sql.append("WHERE A.PART_CODE = B.PART_CODE AND A.SUPPLIER_CODE = C.SUPPLIER_CODE AND B.IF_SUPLY ="+DicConstant.SF_01+" AND C.PART_IDENTIFY = "+DicConstant.YXBS_01+" AND A.SUPPLIER_CODE IS NOT NULL AND A.USER_ACCOUNT='"+user.getAccount()+"'\n" );
	   sql.append("AND NOT EXISTS (SELECT 1 FROM PT_BA_PART_SUPPLIER_RL D WHERE B.PART_ID= D.PART_ID AND D.SUPPLIER_ID = C.SUPPLIER_ID AND D.PART_IDENTIFY = "+DicConstant.YXBS_01+")\n");
	   return factory.select(null,sql.toString());
   }
   /**
    * 查询订购数量
    */
   public QuerySet partOrderCountSearch(User user,DBFactory factory)throws Exception {
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT A.TMP_NO,A.PART_CODE,A.RETURN_COUNT,B.MIN_PACK FROM PT_BU_RETURN_APPLY_DTL_TMP A,PT_BA_INFO B WHERE A.PART_CODE = B.PART_CODE AND A.USER_ACCOUNT='"+user.getAccount()+"'\n" );
	   return factory.select(null,sql.toString());
   }
}
