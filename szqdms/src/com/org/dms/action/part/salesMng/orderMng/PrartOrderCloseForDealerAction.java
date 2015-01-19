package com.org.dms.action.part.salesMng.orderMng;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.salesMng.orderMng.PartOrderCloseForDealerDao;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 
 * @ClassName: PrartOrderCloseForDealerAction
 * @Description: 渠道商订单关闭
 * @author: fuxiao
 * @date: 2014年12月8日 下午8:14:16
 */
public class PrartOrderCloseForDealerAction {
	private Logger logger = com.org.framework.log.log
			.getLogger("PrartOrderCloseForDealerAction");
	private ActionContext atx = ActionContext.getContext();
	private PartOrderCloseForDealerDao dao = PartOrderCloseForDealerDao
			.getInstance(atx);

	/**
	 * 
	 * @Title: partOrderSearch
	 * @Description: 待关闭订单查询
	 * @throws Exception
	 * @return: void
	 */
	public void partOrderSearch() throws Exception {
		RequestWrapper request = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request, page);
		try {
			BaseResultSet bs = dao.partOrderSearch(page, user, conditions);
			atx.setOutData("bs", bs);
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}

	/**
	 * 
	 * @Title: closeOrder
	 * @Description: 关闭订单
	 * @throws Exception
	 * @return: void
	 */
	public void closeOrder() throws Exception {
		RequestWrapper request = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);

		try {
			String orderId = Pub.val(request, "orderId");

			// 关闭订单
			this.dao.closeDealerOrder(user, orderId);
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}

	}
}
