package com.org.dms.action.part.storageMng.search;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.storageMng.search.PartOccupyForDealerQueryDao;
import com.org.framework.Globals;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 
 * @ClassName: PartOccupyForDealerQueryAction
 * @Description: 经销商订单占用查询
 * @author: fuxiao
 * @date: 2014年12月17日 下午8:00:59
 */
public class PartOccupyForDealerQueryAction {
	// 日志类
	private Logger logger = com.org.framework.log.log.getLogger("PartOccupyQueryAction");
	// 上下文对象
	private ActionContext atx = ActionContext.getContext();
	// 定义dao对象
	private PartOccupyForDealerQueryDao dao = PartOccupyForDealerQueryDao.getInstance(atx);

	/**
	 * 
	 * @Title: queryListInfo
	 * @Description: 占用查询
	 * @return: void
	 */
	public void queryListInfo() {
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			PageManager pageManager = new PageManager();
			User user = (User) atx.getSession().get(Globals.USER_KEY);
			String conditions = RequestUtil.getConditionsWhere(requestWrapper,pageManager);
			atx.setOutData("bs", this.dao.queryList(pageManager, conditions, user));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
	
}
