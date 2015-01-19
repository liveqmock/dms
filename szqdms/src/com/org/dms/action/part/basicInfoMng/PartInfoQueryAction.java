package com.org.dms.action.part.basicInfoMng;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.basicInfoMng.PartInfoQueryDao;
import com.org.framework.common.PageManager;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 
 * @ClassName: PartInfoQueryAction
 * @Description: 配件信息查询控件Action
 * @author: fuxiao
 * @date: 2014年12月9日 下午3:05:20
 */
public class PartInfoQueryAction {
	// 日志类
	private Logger logger = com.org.framework.log.log
			.getLogger("PartPriceChangeQueryAction");
	// 上下文对象
	private ActionContext atx = ActionContext.getContext();
	// 定义dao对象
	private PartInfoQueryDao dao = PartInfoQueryDao.getInstance(atx);

	/**
	 * 
	 * @Title: queryListInfo
	 * @Description: 表格查询
	 * @return: void
	 */
	public void queryListInfo() {
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			PageManager pageManager = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
			atx.setOutData("bs", this.dao.queryList(pageManager, conditions));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
}
