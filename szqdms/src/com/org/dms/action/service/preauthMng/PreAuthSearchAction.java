package com.org.dms.action.service.preauthMng;

import org.apache.log4j.Logger;

import com.org.dms.dao.service.preauth.PreAuthSearchDao;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 预授权查询ACTION
 * @author zts
 *
 */
public class PreAuthSearchAction {
	//日志类
    private Logger logger = com.org.framework.log.log.getLogger(
        "PreAuthSearchAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private PreAuthSearchDao dao = PreAuthSearchDao.getInstance(atx);
    
    /**
     * dealer预授权查询
     * @throws Exception
     */
    public void preAuthDrlSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.preAuthDrlSearch(page,user,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
	/**
     * oem预授权查询
     * @throws Exception
     */
    public void preAuthOemSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.preAuthOemsearch(page,user,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
}
