package com.org.dms.action.part.storageMng.search;

import java.util.Map;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.storageMng.search.ShipmentsRequirementDao;
import com.org.framework.Globals;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 
 * ClassName: ShipmentsRequirementAction 
 * Function: 发货满足率查询统计 -> 修改为订单满足率统计
 * date: 2014年10月27日 上午10:02:48
 * @author fuxiao
 * @version 1.1.0
 * @since JDK 1.6
 *
 */
public class ShipmentsRequirementAction {
	// 日志类
	private Logger logger = com.org.framework.log.log.getLogger("ShipmentsRequirementAction");
	// 上下文对象
	private ActionContext atx = ActionContext.getContext();
	// 定义dao对象
	private ShipmentsRequirementDao dao = ShipmentsRequirementDao.getInstance(atx);

	/**
	 * 
	 * queryListInfo: 表单查询  
	 * @author fuxiao Date:2014年10月23日上午10:36:58
	 */
	public void queryListInfo() {
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			PageManager pageManager = new PageManager();
			User user = (User) atx.getSession().get(Globals.USER_KEY);
			RequestUtil.getConditionsWhere(requestWrapper,pageManager);
			Map<String, String> hm = RequestUtil.getValues(requestWrapper);
			atx.setOutData("bs", this.dao.queryList(pageManager, hm, user));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}

}
