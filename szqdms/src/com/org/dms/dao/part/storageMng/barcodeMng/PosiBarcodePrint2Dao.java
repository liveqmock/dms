package com.org.dms.dao.part.storageMng.barcodeMng;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.DBFactory;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 库位条码打印Dao
 *
 * @user : lichuang
 * @date : 2014-08-01
 */
public class PosiBarcodePrint2Dao{

    /**
     * 查询库位
     *
     * @param page
     * @param user
     * @param conditions
     * @return
     * @throws Exception
     */
    public BaseResultSet searchPosition(DBFactory factory,PageManager page, User user, String conditions) throws Exception {
        String wheres = conditions;
        wheres += "  AND A.STATUS = " + DicConstant.YXBS_01 + "\n";
        wheres += "  AND A.COMPANY_ID=" + user.getCompanyId() + "\n";
        wheres += " ORDER BY A.POSITION_CODE ASC\n";
        page.setFilter(wheres);
        //定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql= new StringBuilder();
        sql.append("SELECT A.POSITION_ID,\n" );
        sql.append("       A.POSITION_CODE,\n" );
        sql.append("       A.POSITION_NAME\n" );
        sql.append("  FROM PT_BA_WAREHOUSE_POSITION A\n" );

        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        return bs;
    }

    /**
     * 查询库位信息
     * @param POSITION_ID
     * @return
     * @throws Exception
     */
    public QuerySet queryPosition(DBFactory factory,String POSITION_ID) throws Exception {
        StringBuilder sql= new StringBuilder();
        sql.append("SELECT A.POSITION_ID,\n" );
        sql.append("       A.POSITION_CODE,\n" );
        sql.append("       A.POSITION_NAME\n" );
        sql.append("  FROM PT_BA_WAREHOUSE_POSITION A\n" );
        sql.append("   WHERE A.POSITION_ID = "+POSITION_ID+"\n" );

        return factory.select(null,sql.toString());
    }
}