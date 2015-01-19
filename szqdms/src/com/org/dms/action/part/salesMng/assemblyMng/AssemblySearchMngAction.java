package com.org.dms.action.part.salesMng.assemblyMng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.salesMng.assemblyMng.AssemblySearchMngDao;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

public class AssemblySearchMngAction {
	 // 日志类
    private Logger logger = com.org.framework.log.log.getLogger(this.getClass().getName());
    // 上下文对象
    private ActionContext actionContext = ActionContext.getContext();
    // 获取session中的user对象
    private User user = (User) actionContext.getSession().get(Globals.USER_KEY);
    // 定义仓库管理Dao对象
    private AssemblySearchMngDao assemblyMngDao = AssemblySearchMngDao.getInstance(actionContext);
    // 定义request对象
    private RequestWrapper requestWrapper = actionContext.getRequest();
    // 获取页面信息
    private HashMap<String, String> hashMap = RequestUtil.getValues(requestWrapper);
    
    public void searchForecast() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = (BaseResultSet)assemblyMngDao.searchForecast(pageManager, user, conditions,true);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }
    public void searchForecastDetail() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            String id = Pub.val(requestWrapper, "ID");
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = assemblyMngDao.searchForecastDetail(pageManager,id);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }
    //OEMSearchForecast
    public void OEMSearchForecast() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = (BaseResultSet)assemblyMngDao.OEMSearchForecast(pageManager, user, conditions,true);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

}
