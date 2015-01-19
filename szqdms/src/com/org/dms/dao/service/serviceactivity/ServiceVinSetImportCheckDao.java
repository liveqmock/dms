package com.org.dms.dao.service.serviceactivity;

import com.org.framework.common.DBFactory;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;

/**
 * 服务活动VIN导入验证Dao
 *
 * @author zhengyao
 * @date 2014-10-28
 * @version 1.0
 */
public class ServiceVinSetImportCheckDao {

 /**
	 * 校验VIN是否已维护在数据库中。
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public QuerySet checkList(User user, DBFactory factory) throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT TMP.VIN,TMP.ROW_NO\n" );
		sql.append("  FROM SE_BU_ACTIVITY_VEHICLE_TMP TMP WHERE TMP.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
		sql.append(" AND NOT EXISTS (SELECT 1 FROM MAIN_VEHICLE MV WHERE MV.SVIN = TMP.VIN)\n" );
		return factory.select(null, sql.toString());
	}
	/**
	 * 校验VIN是否已维护在业务表中（是否重复）。
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public QuerySet checkList2(User user, String activityId, DBFactory factory) throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT TMP.VIN,TMP.ROW_NO\n" );
		sql.append("  FROM SE_BU_ACTIVITY_VEHICLE_TMP TMP WHERE TMP.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
		sql.append(" AND EXISTS(SELECT 1 FROM SE_BU_ACTIVITY_VEHICLE SV WHERE TMP.VIN = SV.VIN AND SV.ACTIVITY_ID = "+activityId+")");
		return factory.select(null, sql.toString());
	}
	/**
	 * 校验VIN是否在导入文件中重复填写（是否重复）。
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public QuerySet checkList3(User user, DBFactory factory) throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT A.ROW_NO,A.VIN\n" );
		sql.append("  FROM SE_BU_ACTIVITY_VEHICLE_TMP A,\n" );
		sql.append("       (SELECT COUNT(T.TMP_ID) AS COU, T.VIN\n" );
		sql.append("          FROM SE_BU_ACTIVITY_VEHICLE_TMP T\n" );
		sql.append("         WHERE T.USER_ACCOUNT = 'ADMIN'\n" );
		sql.append("         GROUP BY T.VIN\n" );
		sql.append("        HAVING COUNT(T.TMP_ID) > 1) B\n" );
		sql.append(" WHERE A.VIN = B.VIN");
		sql.append(" ORDER BY A.ROW_NO");
		return factory.select(null, sql.toString());
	}
}
