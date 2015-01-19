package com.org.dms.dao.part.basicInfoMng;

import com.org.dms.common.DicConstant;
import com.org.framework.common.DBFactory;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;

public class PartSupplierRlCheckDao {

	/**
	 * 
	 * @date()2014年11月22日上午11:18:53
	 * @author Administrator
	 * @to_do:校验配件代码是否存在
	 * @param contractId
	 * @param user
	 * @param factory
	 * @return
	 * @throws Exception
	 */
	
	public QuerySet checkPart(User user,DBFactory factory) throws Exception {
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.PART_CODE, T.TMP_NO\n" );
		sql.append("  FROM PT_BA_PART_SUPPLIER_RL_TMP T\n" );
		sql.append(" WHERE T.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
		sql.append("   AND NOT EXISTS\n" );
		sql.append(" (SELECT 1 FROM PT_BA_INFO T1 WHERE T.PART_CODE = T1.PART_CODE)");
	       return factory.select(null,sql.toString());
	   }
		
	/**
	 * 
	 * @date()2014年11月22日上午11:19:10
	 * @author Administrator
	 * @to_do:校验供应商名称是否存在
	 * @param user
	 * @param factory
	 * @return
	 * @throws Exception
	 */
	  public QuerySet checkSupplier(User user,DBFactory factory) throws Exception {
		  StringBuilder sql= new StringBuilder();
		  sql.append("SELECT T.SUPPLIER_CODE, T.TMP_NO\n" );
		  sql.append("  FROM PT_BA_PART_SUPPLIER_RL_TMP T\n" );
		  sql.append(" WHERE T.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
		  sql.append("   AND NOT EXISTS\n" );
		  sql.append(" (SELECT 1 FROM PT_BA_SUPPLIER T1 WHERE T.SUPPLIER_CODE = T1.SUPPLIER_CODE)");
	      return factory.select(null,sql.toString());
	  }
  	/**
	 * 
	 * @date()2014年11月22日上午11:19:10
	 * @author Administrator
	 * @to_do:校验供货周期是否存在
	 * @param user
	 * @param factory
	 * @return
	 * @throws Exception
	 */
	  public QuerySet checkApplyCycle(User user,DBFactory factory) throws Exception {
		  StringBuffer sql= new StringBuffer();
		  sql.append("SELECT T.PART_CODE, T.TMP_NO\n" );
		  sql.append("  FROM PT_BA_PART_SUPPLIER_RL_TMP T\n" );
		  sql.append(" WHERE T.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
		  sql.append("   AND NOT EXISTS\n" );
		  sql.append(" (SELECT 1 FROM USER_PARA_CONFIGURE T1 WHERE T.APPLY_CYCLE = T1.PARAVALUE1 AND T1.APPTYPE = '3001')");
	      return factory.select(null,sql.toString());
	  }
	  /**
	   * 
	   * @date()2014年11月22日上午11:20:34
	   * @author Administrator
	   * @to_do:校验供货关系是否重复
	   * @param user
	   * @param factory
	   * @return
	   * @throws Exception
	   */
	  public QuerySet checkUnique(User user,DBFactory factory) throws Exception {
		  StringBuffer sql= new StringBuffer();
		  sql.append("SELECT T.PART_CODE, T.SUPPLIER_CODE, T.TMP_NO,T.PART_STATUS\n" );
		  sql.append("  FROM PT_BA_PART_SUPPLIER_RL_TMP T,\n" );
		  sql.append("       PT_BA_INFO                 T1,\n" );
		  sql.append("       PT_BA_SUPPLIER             T2,\n" );
		  sql.append("       DIC_TREE                   T4\n" );
		  sql.append(" WHERE T.USER_ACCOUNT = '"+user.getAccount()+"\"'\n" );
		  sql.append("   AND T.PART_CODE = T1.PART_CODE\n" );
		  sql.append("   AND T.SUPPLIER_CODE = T2.SUPPLIER_CODE\n" );
		  sql.append("   AND T.PART_STATUS = T4.DIC_VALUE\n" );
		  sql.append("   AND EXISTS (SELECT 1\n" );
		  sql.append("          FROM PT_BA_PART_SUPPLIER_RL T3\n" );
		  sql.append("         WHERE T1.PART_ID = T3.PART_ID\n" );
		  sql.append("           AND T2.SUPPLIER_ID = T3.SUPPLIER_ID\n" );
		  sql.append("           AND T3.PART_IDENTIFY = T4.ID)");
	      return factory.select(null,sql.toString());
	  }
	  
	  public QuerySet checkCont(User user,DBFactory factory) throws Exception {
		  StringBuilder sql= new StringBuilder();
		  sql.append("SELECT T.SUPPLIER_CODE, T.TMP_NO\n" );
		  sql.append("  FROM PT_BA_PART_SUPPLIER_RL_TMP T\n" );
		  sql.append(" WHERE T.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
		  sql.append("   AND NOT EXISTS\n" );
		  sql.append(" (SELECT 1 FROM PT_BU_PCH_CONTRACT T1 WHERE T.SUPPLIER_CODE = T1.SUPPLIER_CODE)");
	      return factory.select(null,sql.toString());
	  }
	  
	  public QuerySet checkContDtl(User user,DBFactory factory) throws Exception {
		  StringBuilder sql= new StringBuilder();
		  sql.append("SELECT T.PART_CODE, T.TMP_NO\n" );
		  sql.append("  FROM PT_BA_PART_SUPPLIER_RL_TMP T,PT_BA_INFO T2\n" );
		  sql.append(" WHERE T.USER_ACCOUNT = '"+user.getAccount()+"' AND T.PART_CODE = T2.PART_CODE AND T2.IF_ASSEMBLY = "+DicConstant.SF_02+"\n" );
		  sql.append("   AND NOT EXISTS\n" );
		  sql.append(" (SELECT 1 FROM PT_BU_PCH_CONTRACT_DTL T1 WHERE T.PART_CODE = T1.PART_CODE)");
	      return factory.select(null,sql.toString());
	  }
	  
	  public QuerySet checkOne(User user,DBFactory factory) throws Exception {
		  StringBuilder sql= new StringBuilder();
		  sql.append("SELECT T.TMP_NO, T.PART_CODE\n" );
		  sql.append("        FROM PT_BA_PART_SUPPLIER_RL_TMP T\n" );
		  sql.append("       WHERE  T.PART_CODE IN (SELECT A.PART_CODE\n" );
		  sql.append("                               FROM PT_BA_PART_SUPPLIER_RL_TMP A\n" );
		  sql.append("                              WHERE A.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
		  sql.append("                              GROUP BY A.PART_CODE, A.SUPPLIER_CODE\n" );
		  sql.append("                             HAVING(COUNT(A.PART_CODE) > 1))");
	      return factory.select(null,sql.toString());
	  }
}
