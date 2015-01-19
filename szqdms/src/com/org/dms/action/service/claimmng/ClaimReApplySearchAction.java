package com.org.dms.action.service.claimmng;

import org.apache.log4j.Logger;

import com.org.dms.dao.service.claimmng.ClaimReApplySearchDao;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

public class ClaimReApplySearchAction {
	private Logger logger = com.org.framework.log.log.getLogger("ClaimReApplySearchAction");
	private ActionContext atx = ActionContext.getContext();
	private ClaimReApplySearchDao dao=ClaimReApplySearchDao.getInstance(atx);
	 
	 /**
	  * 重新提单查询oem
     * @throws Exception
    */
    public void claimSearch() throws Exception{
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
     * 重新提单查询oem
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
