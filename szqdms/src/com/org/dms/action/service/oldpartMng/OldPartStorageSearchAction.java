package com.org.dms.action.service.oldpartMng;

import org.apache.log4j.Logger;

import com.org.dms.dao.service.oldpartMng.OldPartStorageSearchDao;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 旧件库存查询ACTION
 * @author zts
 *
 */
public class OldPartStorageSearchAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "OldPartStorageSearchAction");
	private ActionContext atx = ActionContext.getContext();
	private OldPartStorageSearchDao dao = OldPartStorageSearchDao.getInstance(atx);

	/**
	 * 汇总查询
	 * @throws Exception
	 */
	public void oldPartStorageSearch()throws Exception{
		RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.oldPartStorageSearch(page,conditions,user);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
	
	/**
	 * 明细查询
	 * @throws Exception
	 */
	public void oldPartStorageDetailSearch()throws Exception{
		RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.oldPartStorageDetailSearch(page,conditions,user);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
}
