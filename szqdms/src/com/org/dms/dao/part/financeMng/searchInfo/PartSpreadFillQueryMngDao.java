package com.org.dms.dao.part.financeMng.searchInfo;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 配件价差调整查询Dao
 *
 * 配件价差调整查询
 * @author zhengyao
 * @date 2014-11-05
 * @version 1.0
 */
public class PartSpreadFillQueryMngDao extends BaseDAO {

    /**
     * 定义instance
     *
     * @param pActionContext
     * @return partSpreadFillQueryMngDao 配件价差调整查询Dao
     */
    public static final PartSpreadFillQueryMngDao getInstance(ActionContext pActionContext) {

    	PartSpreadFillQueryMngDao partSpreadFillQueryMngDao = new PartSpreadFillQueryMngDao();
        pActionContext.setDBFactory(partSpreadFillQueryMngDao.factory);
        return partSpreadFillQueryMngDao;
    }

    /**
     * 配件价差调整查询(导出)
     *
     * @pConditions 查询条件
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet download(String pConditions,User pUser) throws Exception {

        String wheres = "WHERE "+ pConditions;
        StringBuilder sql= new StringBuilder();
        sql.append("SELECT ORG_CODE,ORG_NAME, PART_CODE, PART_NAME,(SELECT DIC_VALUE FROM DIC_TREE WHERE ID = FILL_TYPE) FILL_TYPE,PRICE_DATE,OLD_PRICE,NEW_PRICE,\n" );
        sql.append("       SPREAD, DELIVERY_COUNT, SALE_COUNT,FILL_COUNT,FILL_AMOUNT,CHANEL_COUNT\n" );
        sql.append("  FROM PT_BU_SPREAD_FILL\n" );

        //执行方法，不需要传递conn参数
        return factory.select(null, sql.toString()+wheres);
    }

    /**
     * 配件价差调整查询(PT_BU_SPREAD_FILL)查询
     *
     * @param pConditions 月份
     * @param pUser 当前登录user对象
     * @return QuerySet 结果集
     * @throws Exception
     */
    public BaseResultSet searchOrder(PageManager pPageManager, User pUser,String pConditions) throws Exception {

        String wheres = pConditions;
        pPageManager.setFilter(wheres);
        StringBuilder sql= new StringBuilder();
        sql.append("SELECT FILL_ID,ORG_CODE,ORG_NAME, PART_CODE, PART_NAME,FILL_TYPE,PRICE_DATE,OLD_PRICE,NEW_PRICE,\n" );
        sql.append("       SPREAD, DELIVERY_COUNT, SALE_COUNT,FILL_COUNT,FILL_AMOUNT,CHANEL_COUNT\n" );
        sql.append("  FROM PT_BU_SPREAD_FILL\n" );
        //执行方法，不需要传递conn参数
        return factory.select(sql.toString(),pPageManager);
    }
    
    public BaseResultSet searchOrderDtl(PageManager pPageManager, User pUser,String pConditions) throws Exception {
        String wheres = pConditions;
        pPageManager.setFilter(wheres);
        StringBuilder sql= new StringBuilder();
        sql.append("SELECT ORG_CODE,ORG_NAME, PART_CODE, PART_NAME,PRICE_DATE,OLD_PRICE,NEW_PRICE,\n" );
        sql.append("       SPREAD, FILL_COUNT,FILL_AMOUNT\n" );
        sql.append("  FROM PT_BU_SPREAD_FILL_DTL\n" );
        //执行方法，不需要传递conn参数
        return factory.select(sql.toString(),pPageManager);
    }
}
