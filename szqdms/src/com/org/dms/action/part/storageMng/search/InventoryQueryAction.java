package com.org.dms.action.part.storageMng.search;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.storageMng.search.InventoryQueryDao;
import com.org.framework.Globals;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 
 * @ClassName: InventoryQueryAction
 * @Description: 车厂库存查询
 * @author: fuxiao
 * @date: 2014年9月10日 下午3:01:32
 */
public class InventoryQueryAction {
    //日志类
    private Logger logger = com.org.framework.log.log.getLogger("InventoryQueryAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private InventoryQueryDao  dao = InventoryQueryDao.getInstance(atx);
	
    /**
     * 
     * queryListInfo:汇总查询
     * @author fuxiao
     * Date:2014年9月13日下午1:20:55
     */
	public void queryListInfo(){
		try {
			RequestWrapper request = atx.getRequest();
			PageManager page = new PageManager();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			String conditions = RequestUtil.getConditionsWhere(request, page);
			atx.setOutData("bs", this.dao.querySummarizeList(page, conditions, user));
		} catch (Exception e) {
			 logger.error(e);
             atx.setException(e);
		}
	}
	
	/**
	 * queryDetailsInfo: 详情查询
	 * @author fuxiao
	 * Date:2014年9月13日下午1:20:14
	 */
	public void queryDetailsInfo(){
		try {
			RequestWrapper request = atx.getRequest();
			PageManager page = new PageManager();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			String conditions = RequestUtil.getConditionsWhere(request, page);
			atx.setOutData("bs", this.dao.queryDetailsList(page, conditions, user));
		} catch (Exception e) {
			 logger.error(e);
             atx.setException(e);
		}
	}
	
	/**
	 * 
	 * @Title: dealerQuery
	 * @Description: 经销商，配送中心查询车厂库存
	 * @return: void
	 */
	public void dealerQuery(){
		try {
			RequestWrapper request = atx.getRequest();
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request, page);
			atx.setOutData("bs", this.dao.dealerQueryList(page, conditions));
		} catch (Exception e) {
			 logger.error(e);
             atx.setException(e);
		}
	}
	
}
