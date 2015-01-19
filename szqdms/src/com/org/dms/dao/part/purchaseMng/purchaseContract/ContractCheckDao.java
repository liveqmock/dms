package com.org.dms.dao.part.purchaseMng.purchaseContract;

import com.org.dms.common.DicConstant;
import com.org.framework.common.DBFactory;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;

public class ContractCheckDao {

	/**
	 * 配件重复验证
	 */
   public QuerySet checkUnique(String contractId,User user,DBFactory factory) throws Exception {
	   StringBuilder sql= new StringBuilder();
	   sql.append("SELECT B.TMP_NO, B.PART_CODE\n" );
	   sql.append("  FROM PT_BU_PCH_CONTRACT_DTL A, PT_BU_PCH_CONT_PART_TMP B\n" );
	   sql.append(" WHERE A.PART_CODE = B.PART_CODE\n" );
	   sql.append("   AND B.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
	   sql.append("   AND A.CONTRACT_ID = "+contractId+"");
       return factory.select(null,sql.toString());
   }
	/**
	 * 采购价 (是否是数字)
	 */
  public QuerySet check(User user,DBFactory factory) throws Exception {
	  StringBuilder sql= new StringBuilder();
	  sql.append("SELECT TMP_NO, PART_CODE, UNIT_PRICE, MIN_PACK_UNIT\n" );
	  sql.append("  FROM PT_BU_PCH_CONT_PART_TMP\n" );
	  sql.append(" WHERE USER_ACCOUNT = '"+user.getAccount()+"'");
      return factory.select(null,sql.toString());
  }
  public QuerySet getSup(String CONTRACT_ID,DBFactory factory) throws Exception {

      StringBuffer sql = new StringBuffer();
      sql.append("SELECT SUPPLIER_CODE FROM PT_BU_PCH_CONTRACT WHERE CONTRACT_ID = "+CONTRACT_ID+"");
      //执行方法，不需要传递conn参数
      return factory.select(null,sql.toString());
  }
  public QuerySet checkOther(User user,String supplierCode,DBFactory factory) throws Exception {
	  StringBuffer sql= new StringBuffer();
	  sql.append("SELECT T.TMP_NO, T.PART_CODE\n" );
	  sql.append("  FROM PT_BU_PCH_CONT_PART_TMP T\n" );
	  sql.append(" WHERE USER_ACCOUNT = '"+user.getAccount()+"'\n" );
	  sql.append("   AND EXISTS (SELECT 1\n" );
	  sql.append("          FROM PT_BU_PCH_CONTRACT_DTL T1\n" );
	  sql.append("         WHERE T.PART_CODE = T1.PART_CODE\n" );
	  sql.append("           AND T1.CONTRACT_ID IN\n" );
	  sql.append("               (SELECT CONTRACT_ID\n" );
	  sql.append("                  FROM PT_BU_PCH_CONTRACT\n" );
	  sql.append("                 WHERE SUPPLIER_CODE = '"+supplierCode+"' AND CONTRACT_STATUS <>"+DicConstant.CGHTZT_08+"\n" );
	  sql.append("                   AND EFFECTIVE_CYCLE_BEGIN <= SYSDATE\n" );
	  sql.append("                   AND EFFECTIVE_CYCLE_END >= SYSDATE))");
      return factory.select(null,sql.toString());
  }
}
