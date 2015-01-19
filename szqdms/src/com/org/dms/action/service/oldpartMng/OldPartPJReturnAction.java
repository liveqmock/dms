package com.org.dms.action.service.oldpartMng;


import org.apache.log4j.Logger;

import com.org.dms.dao.service.oldpartMng.OldPartReturnPJDao;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 配件管理-旧件回运Action
 * @author fuxiao
 *
 */
public class OldPartPJReturnAction {
	private Logger logger = com.org.framework.log.log.getLogger("OldPartPJReturnAction");
	private ActionContext atx = ActionContext.getContext();
	private OldPartReturnPJDao dao = OldPartReturnPJDao.getInstance(atx);
	
	 /**
     * 回运单回运查询
     * @throws Exception
     */
    public void oldPartSearch() throws Exception{
    	RequestWrapper request = atx.getRequest();
 		PageManager page = new PageManager();
 		User user = (User) atx.getSession().get(Globals.USER_KEY);
 		String conditions = RequestUtil.getConditionsWhere(request,page);
 		try
 		{
 			BaseResultSet bs = dao.searchReturnBoxList(page,conditions,user);
 			atx.setOutData("bs", bs);
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }	
    
    /**
     * 更新回运时间
     * @throws Exception
     */
    public void updateReturnDate() throws Exception{
    	RequestWrapper request = atx.getRequest();
    	String applyIds = Pub.val(request, "applyIds");
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	try {
			this.dao.updatePartReturnDate(applyIds, user);
		} catch (Exception e) {
 			logger.error(e);
 			atx.setException(e);
		}
    }
}
