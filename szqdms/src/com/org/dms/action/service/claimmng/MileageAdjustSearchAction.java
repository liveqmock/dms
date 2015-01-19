package com.org.dms.action.service.claimmng;

import org.apache.log4j.Logger;

import com.org.dms.dao.service.claimmng.MileageAdjustSearchDao;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

public class MileageAdjustSearchAction {
	private Logger logger = com.org.framework.log.log.getLogger("MileageAdjustSearchAction");
	private ActionContext atx = ActionContext.getContext();
	private MileageAdjustSearchDao dao=MileageAdjustSearchDao.getInstance(atx);
	 
	/**
	 * 里程调整查询dealer
	 * @throws Exception
	 */
	public void claimSearch()throws Exception{
		RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
		    BaseResultSet bs = dao.claimSearch(page,conditions,user);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
	
	/**
	 * 里程调整查询oem
	 * @throws Exception
	 */
	public void claimOemSearch()throws Exception{
		RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
		    BaseResultSet bs = dao.claimOemSearch(page,conditions,user);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
}
