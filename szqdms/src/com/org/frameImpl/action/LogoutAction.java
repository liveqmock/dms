package com.org.frameImpl.action;

import com.org.framework.common.User;
import com.org.framework.Globals;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * @Description:登录退出类
 * @Copyright: Copyright (c) 2011
 * @Date: 2011-10-7
 * @mail   andy.ten@tom.com
 */

public class LogoutAction 
{
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    protected void process() throws  Exception 
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取封装后的response对象
    	//ResponseWrapper response = atx.getResponse();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        if(user != null)
        {
        	com.org.framework.log.LogManager.writeLogoutLog(user);
        }
        request.getSession().invalidate();
    	atx.setForword("index");
    }
}
