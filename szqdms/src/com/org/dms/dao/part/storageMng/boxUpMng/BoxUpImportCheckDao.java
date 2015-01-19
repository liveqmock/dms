package com.org.dms.dao.part.storageMng.boxUpMng;

import com.org.framework.common.DBFactory;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;

public class BoxUpImportCheckDao {
	
	public QuerySet checkList1(DBFactory factory,User user) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM PT_BU_BOX_UP_TMP A WHERE A.USER_ACCOUNT = '" + user.getAccount() + "'\n");
        return factory.select(null, sql.toString());
    }
	public QuerySet checkList2(DBFactory factory,User user, String ORDER_ID) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT A.TMP_NO\n");
        sql.append("  FROM PT_BU_BOX_UP_TMP A\n");
        sql.append(" WHERE A.USER_ACCOUNT = '" + user.getAccount() + "'\n");
        sql.append("  AND NOT EXISTS (SELECT 1\n");
        sql.append("          FROM PT_BU_ISSUE_ORDER_DTL B\n");
        sql.append("         WHERE B.ORDER_ID = " + ORDER_ID + "\n");
        sql.append("           AND B.ISSUE_NO = A.ISSUE_NO\n");
        sql.append("           AND B.PART_CODE = A.PART_CODE)\n");

        return factory.select(null, sql.toString());
    }
	
	public QuerySet checkVel(DBFactory factory,User user,String VEHICLE_ID,String SHIP_ID) throws Exception {
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.BOX_NO, T.TMP_NO\n" );
		sql.append("  FROM PT_BU_VEL_BOX_RL_TMP T\n" );
		sql.append(" WHERE T.USER_ACCONT = '"+user.getAccount()+"'\n" );
		sql.append("   AND EXISTS (SELECT 1\n" );
		sql.append("          FROM PT_BU_SHIP_VAN_BOX_RL T1\n" );
		sql.append("         WHERE T.BOX_NO = T1.BOX_NO\n" );
		sql.append("           AND T1.VEHICLE_ID = "+VEHICLE_ID+"\n" );
		sql.append("           AND T1.SHIP_ID = "+SHIP_ID+")");
        return factory.select(null, sql.toString());
    }
	public QuerySet checkBox(DBFactory factory,User user,String SHIP_ID) throws Exception {
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.BOX_NO, T.TMP_NO\n" );
		sql.append("  FROM PT_BU_VEL_BOX_RL_TMP T\n" );
		sql.append(" WHERE T.USER_ACCONT = '"+user.getAccount()+"'\n" );
		sql.append("   AND NOT EXISTS\n" );
		sql.append(" (SELECT 1\n" );
		sql.append("          FROM PT_BU_BOX_UP T1\n" );
		sql.append("         WHERE T.BOX_NO = T1.BOX_NO\n" );
		sql.append("           AND T1.ORDER_ID IN (SELECT ORDER_ID\n" );
		sql.append("                                 FROM PT_BU_ORDER_SHIP_DTL\n" );
		sql.append("                                WHERE SHIP_ID = "+SHIP_ID+"))");
        return factory.select(null, sql.toString());
    }

}
