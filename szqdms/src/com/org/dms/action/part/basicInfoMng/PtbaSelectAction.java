package com.org.dms.action.part.basicInfoMng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.basicInfoMng.PtBaInfoDao;
import com.org.dms.dao.part.basicInfoMng.PtBaPriceLogDao;
import com.org.dms.dao.part.basicInfoMng.PtbaSelectDao;
import com.org.dms.dao.part.planMng.forecast.ForecastMngDao;
import com.org.dms.vo.part.PtBaInfoVO;
import com.org.dms.vo.part.PtBaInfoVO_Ext;
import com.org.dms.vo.part.PtBaPartSupplierRlVO;
import com.org.dms.vo.part.PtBaPriceLogVO;
import com.org.frameImpl.Constant;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

public class PtbaSelectAction {
	 // 日志类
    private Logger logger = com.org.framework.log.log.getLogger(this.getClass().getName());
	// 上下文对象
    private ActionContext actionContext = ActionContext.getContext();
    // 获取session中的user对象
    private User user = (User) actionContext.getSession().get(Globals.USER_KEY);
    // 定义配件预测Dao对象
    private PtbaSelectDao ptbaselectdao = PtbaSelectDao.getInstance(actionContext);
	// 定义request对象
    private RequestWrapper requestWrapper = actionContext.getRequest();
	 /**
     * 配件预测查询
     * @throws Exception
     */
    public void searchForecast() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = (BaseResultSet)ptbaselectdao.searchForecast(pageManager, user, conditions,true);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }
    /**
     * 配件预测明细查询
     * @throws Exception
     */
    public void searchForecastDetail() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = ptbaselectdao.searchForecastDetail(pageManager,conditions);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }    

}

