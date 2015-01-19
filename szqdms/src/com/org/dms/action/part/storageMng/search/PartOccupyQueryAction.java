package com.org.dms.action.part.storageMng.search;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.storageMng.search.PartOccupyQueryDao;
import com.org.framework.common.PageManager;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 
 * ClassName: PartOccupyQueryAction 
 * Function: 配件占用查询
 * date: 2014年11月18日 下午11:15:51
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class PartOccupyQueryAction {
	// 日志类
	private Logger logger = com.org.framework.log.log.getLogger("PartOccupyQueryAction");
	// 上下文对象
	private ActionContext atx = ActionContext.getContext();
	// 定义dao对象
	private PartOccupyQueryDao dao = PartOccupyQueryDao.getInstance(atx);


	/**
	 * 
	 * queryListInfo:配件占用查询
	 * @author fuxiao
	 * Date:2014年11月18日
	 *
	 */
	public void queryListInfo() {
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			PageManager pageManager = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(requestWrapper,pageManager);
			atx.setOutData("bs", this.dao.queryList(pageManager, conditions));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
	
}
