package com.org.dms.dao.part.partClaimMng;

import com.org.framework.common.DBFactory;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;

/**
 * 配件三包期设置导入验证Dao
 *
 * @author zhengyao
 * @date 2014-10-28
 * @version 1.0
 */
public class ClaimCyclSetImportCheckDao {

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
        sql.append("    CLAIM_MONTH,\n" );
        sql.append("    EXTENSION_MONTH,\n" );
        sql.append("    USER_ACCOUNT\n" );
        sql.append("    FROM PT_BA_CLAIM_CYCLE_SET_TMP\n" );
        sql.append(" WHERE USER_ACCOUNT = '" + pUser.getAccount() + "'");
        return factory.select(null, sql.toString());
    }

    /**
     * 导入验证(配件是否存在)
     *
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet partCheck(User pUser,DBFactory factory) throws Exception{

        StringBuilder sql= new StringBuilder();
        sql.append("SELECT TMP_NO,PART_CODE, PART_NAME\n" );
        sql.append("  FROM PT_BA_CLAIM_CYCLE_SET_TMP\n" );
        sql.append(" WHERE PART_CODE || PART_NAME NOT IN\n" );
        sql.append("       (SELECT PBI.PART_CODE || PBI.PART_NAME\n" );
        sql.append("          FROM PT_BA_INFO PBI)\n" );
        sql.append("   AND USER_ACCOUNT = '" + pUser.getAccount() + "'");

        return factory.select(null, sql.toString());
    }
}
