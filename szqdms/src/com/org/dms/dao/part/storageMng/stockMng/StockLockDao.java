package com.org.dms.dao.part.storageMng.stockMng;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 库存锁定Dao
 *
 * @user : lichuang
 * @date : 2014-07-03
 */
public class StockLockDao extends BaseDAO {
    //定义instance
    public static final StockLockDao getInstance(ActionContext atx) {
        StockLockDao dao = new StockLockDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * 查询库存
     *
     * @param page
     * @param user
     * @param conditions
     * @return
     * @throws Exception
     */
    public BaseResultSet searchStock(PageManager page, User user, String conditions) throws Exception {
        String wheres = conditions;
        wheres += " AND PBS.OEM_COMPANY_ID=" + user.getOemCompanyId() + " AND PBS.STOCK_STATUS IN ("+DicConstant.KCZT_01+","+DicConstant.KCZT_02+")\n";
        wheres += " ORDER BY PBS.PART_CODE ASC\n";
        page.setFilter(wheres);
        //定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT PBS.STOCK_ID,\n");
        sql.append("       PBS.PART_ID,\n");
        sql.append("       PBS.PART_CODE,\n");
        sql.append("       PBS.PART_NAME,\n");
        sql.append("       PBS.WAREHOUSE_ID,\n");
        sql.append("       PBS.WAREHOUSE_CODE,\n");
        sql.append("       PBS.WAREHOUSE_NAME,\n");
        sql.append("       PBS.STOCK_STATUS,\n");
        sql.append("       PBS.AMOUNT,\n");
        sql.append("       PBS.OCCUPY_AMOUNT,\n");
        sql.append("       PBS.AVAILABLE_AMOUNT,\n");
        sql.append("       PBS.SUPPLIER_ID,\n");
        sql.append("       PBS.SUPPLIER_CODE,\n");
        sql.append("       PBS.SUPPLIER_NAME\n");
        sql.append("  FROM PT_BU_STOCK PBS\n");
        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("STOCK_STATUS", DicConstant.KCZT);
        return bs;
    }

    /**
     * 修改库存状态
     * @param user
     * @param STOCKIDS 库存状态
     * @param STOCK_STATUS 库存IDs(逗号分隔)
     * @return
     * @throws Exception
     */
    public boolean updateStockStatus(User user, String STOCKIDS, String STOCK_STATUS) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE PT_BU_STOCK PBS\n");
        sql.append("   SET PBS.STOCK_STATUS = " + STOCK_STATUS + ",\n");
        sql.append("       PBS.UPDATE_USER = '" + user.getAccount() + "',\n");
        sql.append("       PBS.UPDATE_TIME = SYSDATE\n");
        sql.append(" WHERE PBS.STOCK_ID IN (" + STOCKIDS + ")\n");
        return factory.update(sql.toString(), null);
    }
}