package com.org.dms.action.part.storageMng.stockMng;

import com.org.dms.dao.part.storageMng.stockMng.StockLockDao;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import org.apache.log4j.Logger;

import java.util.HashMap;

/**
 * 库存锁定Action
 *
 * @user : lichuang
 * @date : 2014-07-03
 */
public class StockLockAction {
    //日志类
    private Logger logger = com.org.framework.log.log.getLogger(
            "StockLockAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private StockLockDao dao = StockLockDao.getInstance(atx);


    /**
     * 查询库存
     *
     * @throws Exception
     */
    public void searchStock() throws Exception {
        //定义request对象
        RequestWrapper request = atx.getRequest();
        //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
        PageManager page = new PageManager();
        //将request流中的信息转化为where条件
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            //BaseResultSet：结果集封装对象
            BaseResultSet bs = dao.searchStock(page, user, conditions);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 修改库存状态
     *
     * @throws Exception
     */
    public void updateStockStatus() throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //通过request获取页面传递的参数，对于null值通过该方法将转换为""

        try {
            HashMap<String, String> hm;
            //将request流转换为hashmap结构体
            hm = RequestUtil.getValues(request);
            String STOCK_STATUS = hm.get("STOCK_STATUS");//库存状态
            String STOCKIDS = hm.get("STOCKIDS");//库存IDs(逗号分隔)
            dao.updateStockStatus(user, STOCKIDS, STOCK_STATUS);
            atx.setOutMsg("", "修改成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }

}