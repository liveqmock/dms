package com.org.dms.action.part.storageMng.search;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.storageMng.search.PurchaseOrderSearchDao;
import com.org.framework.Globals;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 
 * ClassName: PurchaseOrderSearchAction 
 * Function: 采购订单查询.
 * date: 2014年9月12日 上午11:32:18
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 
 */
public class PurchaseOrderSearchAction {
	// 日志类
	private Logger logger = com.org.framework.log.log.getLogger("PurchaseOrderSearchAction");
	// 上下文对象
	private ActionContext atx = ActionContext.getContext();
	// 定义dao对象
	private PurchaseOrderSearchDao dao = PurchaseOrderSearchDao.getInstance(atx);

	/**
	 * 
	 * queryPurchareOrderInfo: 根据查询条件查询采购订单信息
	 * @author fuxiao
	 * @since JDK 1.6
	 */
	public void queryPurchareOrderInfo(){
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			PageManager pageManager = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(requestWrapper,pageManager);
			User user = (User) atx.getSession().get(Globals.USER_KEY);
			atx.setOutData("bs", this.dao.queryPurchaseOrderInfo(pageManager, conditions, user));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
	
	/**
	 * 
	 * queryPurchaseOrderPartInfo:根据采购订单PurchaseId查询订单中的配件详细信息
	 * @author fuxiao
	 * @since JDK 1.6
	 */
	public void queryPurchaseOrderPartInfo(){
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			String purchaseId = Pub.val(requestWrapper, "purchaseId");
			PageManager pageManager = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(requestWrapper,pageManager);
			User user = (User) atx.getSession().get(Globals.USER_KEY);
			atx.setOutData("bs", this.dao.queryOrderPartInfo(pageManager, purchaseId, conditions, user));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
}
