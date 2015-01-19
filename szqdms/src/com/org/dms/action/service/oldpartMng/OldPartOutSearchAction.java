package com.org.dms.action.service.oldpartMng;

import org.apache.log4j.Logger;

import com.org.dms.dao.service.oldpartMng.OldPartOutSearchDao;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 旧件出库查询
 * @author zts
 *
 */
public class OldPartOutSearchAction {

	private Logger logger = com.org.framework.log.log.getLogger(
	        "OldPartOutSearchAction");
	private ActionContext atx = ActionContext.getContext();
	private OldPartOutSearchDao dao = OldPartOutSearchDao.getInstance(atx);
	
	/**
	 * 旧件出库查询
	 * @throws Exception
	 */
	public void oldPartOutSearch()throws Exception{
		RequestWrapper request = atx.getRequest();
  		PageManager page = new PageManager();
  		User user = (User) atx.getSession().get(Globals.USER_KEY);
  		String conditions = RequestUtil.getConditionsWhere(request,page);
  		try
  		{
  			BaseResultSet bs = dao.oldPartOutSearch(page,conditions,user);
  			atx.setOutData("bs", bs);
  		}
  		catch (Exception e)
  		{
  			logger.error(e);
  			atx.setException(e);
  		}
	}
}
