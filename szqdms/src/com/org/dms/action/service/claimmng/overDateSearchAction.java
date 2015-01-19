package com.org.dms.action.service.claimmng;

import org.apache.log4j.Logger;

import com.org.dms.dao.service.claimmng.overDateSearchDao;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
/**
 * 超单日期查询
 * @author zts
 *
 */
public class overDateSearchAction {
	private Logger logger = com.org.framework.log.log.getLogger("overDateSearchAction");
	private ActionContext atx = ActionContext.getContext();
	private overDateSearchDao dao = overDateSearchDao.getInstance(atx);
	 
	/**
	 * 超单日期查询 
	 * @throws Exception
	 */
	public void claimSearch()throws Exception{
		RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.claimSearch(page,user);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
	
}
