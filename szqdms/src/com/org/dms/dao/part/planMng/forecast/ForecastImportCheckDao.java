package com.org.dms.dao.part.planMng.forecast;

import com.org.framework.common.DBFactory;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;

public class ForecastImportCheckDao {

    /**
     * 导入验证(配件数量)
     *
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet partCountCheck(User pUser,DBFactory factory) throws Exception {

        StringBuilder sql= new StringBuilder();
        sql.append("SELECT \n" );
        sql.append("    TMP_ID,\n" );
        sql.append("    TMP_NO,\n" );
        sql.append("    PART_CODE,\n" );
        sql.append("    PART_NAME,\n" );
        sql.append("    PART_COUNT,\n" );
        sql.append("    REMARKS,\n" );
        sql.append("    USER_ACCOUNT\n" );
        sql.append("    FROM PT_BU_FORCAST_DTL_TMP\n" );
        sql.append(" WHERE USER_ACCOUNT='" + pUser.getAccount() + "'\n" );

        return factory.select(null, sql.toString());
    }

    /**
     * 导入验证(配件是否存在)
     *
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet partCheck(User pUser,DBFactory factory) throws Exception {

        StringBuilder sql= new StringBuilder();
        sql.append("SELECT \n" );
        sql.append("    TMP_ID,\n" );
        sql.append("    TMP_NO,\n" );
        sql.append("    PART_CODE,\n" );
        sql.append("    PART_NAME,\n" );
        sql.append("    PART_COUNT,\n" );
        sql.append("    REMARKS,\n" );
        sql.append("    USER_ACCOUNT\n" );
        sql.append("    FROM PT_BU_FORCAST_DTL_TMP\n" );
        sql.append(" WHERE PART_CODE NOT IN (SELECT PART_CODE \n" );
        sql.append("                       FROM PT_BA_INFO)\n" );
        sql.append(" AND USER_ACCOUNT='" + pUser.getAccount() + "'\n" );

        return factory.select(null, sql.toString());
    }

    /**
     * 导入验证(配件重复)
     *
     * @param pForecastId 预测主键
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet partRepeatCheck(String pForecastId,User pUser,DBFactory factory) throws Exception {

        StringBuilder sql= new StringBuilder();
        sql.append("SELECT \n" );
        sql.append("    TMP_ID,\n" );
        sql.append("    TMP_NO,\n" );
        sql.append("    PART_CODE,\n" );
        sql.append("    PART_NAME,\n" );
        sql.append("    PART_COUNT,\n" );
        sql.append("    REMARKS,\n" );
        sql.append("    USER_ACCOUNT\n" );
        sql.append("    FROM PT_BU_FORCAST_DTL_TMP\n" );
        sql.append(" WHERE PART_CODE IN (SELECT PART_CODE\n" );
        sql.append("                       FROM PT_BU_FORCAST_DTL\n" );
        sql.append("                      WHERE FORCAST_ID = '" + pForecastId + "')");
        sql.append(" AND USER_ACCOUNT='" + pUser.getAccount() + "'\n" );

        return factory.select(null, sql.toString());
    }
}
