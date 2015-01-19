package com.org.frameImpl.action.logmng;

import org.apache.log4j.Logger;

import com.org.frameImpl.dao.logmng.LogUserOperaDao;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * @Description:操作日志类
 * @Copyright: Copyright (c) 2014
 * @Date: Jul 24, 2014
 * @author andy.ten@tom.com
 */
public class LogUserOperaAction 
{
	private Logger logger = com.org.framework.log.log.getLogger("LogUserOperaAction");
	
	//上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private LogUserOperaDao dao = LogUserOperaDao.getInstance(atx);
    
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
