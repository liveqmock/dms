package com.org.dms.dao.service.serviceactivity;

import com.org.framework.common.DBFactory;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;

/**
 * 车辆信息导入验证Dao
 *
 * @date 2014-10-28
 * @version 1.0
 */
public class MainVehicleSetImportCheckDao {

	  /**
     * 校验数据是否重复
     * @param user
     * @return
     * @throws Exception
     */
    public QuerySet checkList1(User user, DBFactory factory)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT TMP.ROW_NO,TMP.VIN\n" );
    	sql.append("  FROM MAIN_VEHICLE_TMP TMP,\n" );
    	sql.append("       (SELECT COUNT(T.TMP_ID), T.VIN\n" );
    	sql.append("          FROM MAIN_VEHICLE_TMP T\n" );
    	sql.append("          WHERE  T.CREATE_USER ='"+user.getAccount()+"'\n" );
    	sql.append("         GROUP BY T.VIN\n" );
    	sql.append("        HAVING(COUNT(T.TMP_ID)) > 1) B\n" );
    	sql.append(" WHERE TMP.VIN = B.VIN");
    	return factory.select(null, sql.toString());
    }
    
    /**
     * 校验数据是否在车辆表中存在
     * @param user
     * @return
     * @throws Exception
     */
    public QuerySet checkList2(User user, DBFactory factory)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.ROW_NO, T.VIN, COUNT(1)\n" );
    	sql.append("  FROM MAIN_VEHICLE_TMP T\n" );
    	sql.append(" WHERE T.CREATE_USER = '"+user.getAccount()+"'\n" );
    	sql.append("   AND EXISTS\n" );
    	sql.append(" (SELECT 1 FROM MAIN_VEHICLE V\n" );
    	sql.append("   WHERE SUBSTR(T.VIN, LENGTH(T.VIN) - 8 + 1, 8) = V.SVIN\n" );
    	sql.append("      OR EXISTS\n" );
    	sql.append("   (SELECT 1 FROM MAIN_VEHICLE V WHERE T.VIN = V.VIN))\n" );
    	sql.append(" GROUP BY T.ROW_NO, T.VIN");
    	return factory.select(null, sql.toString());
    }
    /**
     * 校验数据是否在车辆表中存在
     * @param user
     * @return
     * @throws Exception
     */
    public QuerySet checkList3(User user, DBFactory factory)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.ROW_NO,T.VIN\n" );
    	sql.append("  FROM MAIN_VEHICLE_TMP T\n" );
    	sql.append(" where LENGTH(TRIM(t.vin)) <> 17\n" );
    	sql.append("   AND T.CREATE_USER = '"+user.getAccount()+"'");
    	return factory.select(null, sql.toString());
    }
}
