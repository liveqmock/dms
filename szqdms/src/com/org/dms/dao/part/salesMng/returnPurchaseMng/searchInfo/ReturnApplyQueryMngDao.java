package com.org.dms.dao.part.salesMng.returnPurchaseMng.searchInfo;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 订单查询Dao
 *
 * 订单查询
 * @author zhengyao
 * @date 2014-10-23
 * @version 1.0
 */
public class ReturnApplyQueryMngDao extends BaseDAO {

    /**
     * 定义instance
     *
     * @param pActionContext
     * @return warehouseMngDao 订单查询Dao
     */
    public static final ReturnApplyQueryMngDao getInstance(ActionContext pActionContext) {

        ReturnApplyQueryMngDao partOrderQueryMngDao = new ReturnApplyQueryMngDao();
        pActionContext.setDBFactory(partOrderQueryMngDao.factory);
        return partOrderQueryMngDao;
    }

    /**
     * 退件申请表(pt_bu_return_apply)查询
     *
     * @param pPageManager 查询分页对象
     * @param pUser 当前登录user对象
     * @param pConditions sql条件(默认：1=1)
     * @return baseResultSet 结果集
     * @throws Exception
     */
    public BaseResultSet searchReturnPurchaseApply(PageManager pPageManager, User pUser, String pConditions) throws Exception {

        String wheres = pConditions;
        // 查询未申请,审核驳回退件订单
        wheres += " AND APPLY_SATUS<>'"+DicConstant.TJSQDZT_01+"' AND (APPLY_ORG_ID = " + pUser.getOrgId() + " OR RECEIVE_ORG_ID = " + pUser.getOrgId() + ")\n"
                + " ORDER BY RETURN_NO";
        pPageManager.setFilter(wheres);
        //定义返回结果集
        BaseResultSet baseResultSet = null;
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT \n");
        sql.append("     RETURN_ID,\n");
        sql.append("     RETURN_NO,\n");
        sql.append("     RECEIVE_ORG_ID,\n");
        sql.append("     RECEIVE_ORG_CODE,\n");
        sql.append("     RECEIVE_ORG_NAME,\n");
        sql.append("     SOURCE_ORDER_NO,\n");
        sql.append("     SOURCE_ORDER_ID,\n");
        sql.append("     APPLY_ORG_CODE,\n");
        sql.append("     APPLY_ORG_NAME,\n");
        sql.append("     REMARKS,\n");
        sql.append("     CHECK_REMARKS,\n");
        sql.append("     APPLY_SATUS \n");
        sql.append(" FROM \n");
        sql.append("     PT_BU_RETURN_APPLY \n");
        // 执行方法，不需要传递conn参数
        baseResultSet = factory.select(sql.toString(), pPageManager);
        baseResultSet.setFieldDic("APPLY_SATUS", DicConstant.TJSQDZT);

        return baseResultSet;
    }
}
