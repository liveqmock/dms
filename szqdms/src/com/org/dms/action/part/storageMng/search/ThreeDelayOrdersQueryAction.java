package com.org.dms.action.part.storageMng.search;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.storageMng.search.ThreeDelayOrdersQueryDao;
import com.org.framework.Globals;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 
 * ClassName: ThreeDelayOrdersQueryAction 
 * Function: 三包延期订单查询
 * date: 2014年11月19日 下午10:34:42
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class ThreeDelayOrdersQueryAction {
	// 日志类
	private Logger logger = com.org.framework.log.log.getLogger("ThreeDelayOrdersQueryAction");
	// 上下文对象
	private ActionContext atx = ActionContext.getContext();
	// 定义dao对象
	private ThreeDelayOrdersQueryDao dao = ThreeDelayOrdersQueryDao.getInstance(atx);

	/**
	 * 
	 * queryListInfo: 表单查询  
	 * @author fuxiao 
	 * @Date:2014年10月23日上午10:36:58
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
