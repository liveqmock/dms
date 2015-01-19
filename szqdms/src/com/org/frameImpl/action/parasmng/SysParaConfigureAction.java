package com.org.frameImpl.action.parasmng;

import com.org.frameImpl.dao.parasmng.SysParaConfigureDao;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * @Description:系统参数管理类
 * @Copyright: Copyright (c) 2011
 * @Date: 2012-7-19
 */

public class SysParaConfigureAction
{
	//上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private SysParaConfigureDao dao = SysParaConfigureDao.getInstance(atx);
	private static org.apache.log4j.Logger logger = org.apache.log4j.LogManager.
	        getLogger("SysParaConfigureAction");
	
	
	public void search()
			throws Exception
	{
		RequestWrapper request = atx.getRequest();
		PageManager page = null;
		String contitions = null;
		try
		{
			page = new PageManager();
			contitions = RequestUtil.getConditionsWhere(request, page);
			page.setFilter(contitions);
			BaseResultSet bs = dao.queryList(page);
			atx.setOutData("bs", bs);
		}
		catch(Exception e)
		{
			logger.error(e);
            atx.setException(e);
		}
	}
}
