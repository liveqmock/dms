package com.org.dms.dao.service.basicinfomng;

import com.org.framework.common.DBFactory;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;

public class OldPartManageCheckDao {
	
	/**
	 * 判断 代码与名称是否一致
	 * @param user
	 * @param factory
	 * @return
	 * @throws Exception
	 */
	public QuerySet checkList1(User user, DBFactory factory)throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.TMP_ID, T.PART_CODE, T.PART_NAME, T.ROW_NUM\n" );
		sql.append("  FROM SE_BA_OLDPARTMANAGE_TMP T\n" );
		sql.append(" WHERE NOT EXISTS (SELECT 1\n" );
		sql.append("          FROM PT_BA_INFO I\n" );
		sql.append("         WHERE I.SE_STATUS = 100201\n" );
		sql.append("           AND T.PART_CODE = I.PART_CODE\n" );
		sql.append("           AND T.PART_NAME = I.PART_NAME)\n" );
		sql.append("   AND T.USER_ACCOUNT = '"+user.getAccount()+"'");
	    return factory.select(null, sql.toString());
    }
	
	/**
	 * 判断重复记录
	 * @param user
	 * @param factory
	 * @return
	 * @throws Exception
	 */
	public QuerySet checkList2(User user, DBFactory factory)throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT B.ROW_NUM, B.PART_CODE, B.PART_NAME\n" );
		sql.append("  FROM (\n" );
		sql.append("        SELECT T.PART_CODE, T.PART_NAME, COUNT(1)\n" );
		sql.append("          FROM SE_BA_OLDPARTMANAGE_TMP T\n" );
		sql.append("         WHERE T.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
		sql.append("         GROUP BY T.PART_CODE, T.PART_NAME\n" );
		sql.append("        HAVING COUNT(1) > 1) A, SE_BA_OLDPARTMANAGE_TMP B\n" );
		sql.append("         WHERE A.PART_CODE = B.PART_CODE\n" );
		sql.append("           AND A.PART_NAME = B.PART_NAME\n" );
		sql.append("           AND B.USER_ACCOUNT = '"+user.getAccount()+"'");
		return factory.select(null, sql.toString());
	}
	
	/**
	 * 校验系数不能为空
	 * @param user
	 * @param factory
	 * @return
	 * @throws Exception
	 */
	public QuerySet checkList3(User user, DBFactory factory)throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.PART_CODE, T.PART_NAME,T.ROW_NUM\n" );
		sql.append("  FROM SE_BA_OLDPARTMANAGE_TMP T\n" );
		sql.append(" WHERE T.OLD_MANAGE_FEE IS NULL\n" );
		sql.append("   AND T.USER_ACCOUNT = '"+user.getAccount()+"'");
		return factory.select(null, sql.toString());
	}
	
	/**
	 * 
	 * @param user
	 * @param factory
	 * @return
	 * @throws Exception
	 */
	public  QuerySet checkList4(User user, DBFactory factory)throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.PART_CODE, T.PART_NAME,T.ROW_NUM,T.OLD_MANAGE_FEE\n" );
		sql.append("  FROM SE_BA_OLDPARTMANAGE_TMP T\n" );
		sql.append(" WHERE T.USER_ACCOUNT = '"+user.getAccount()+"'");
		return factory.select(null, sql.toString());
	}
}
