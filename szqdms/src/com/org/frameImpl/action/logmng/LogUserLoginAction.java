package com.org.frameImpl.action.logmng;

import org.apache.log4j.Logger;

import com.org.frameImpl.dao.logmng.LogUserLoginDao;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * @Copyright: Copyright (c) 2011
 * @Date: 2012-7-20
 */

public class LogUserLoginAction
{
	private Logger logger = com.org.framework.log.log.getLogger(
	        "LogUserLoginAction");
	//上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private LogUserLoginDao dao = LogUserLoginDao.getInstance(atx);
	public void search() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.search(page,user,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
}

