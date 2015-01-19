package com.org.dms.action.part.storageMng.overstockParts;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.storageMng.overstockParts.DealerOverstockPartDao;
import com.org.framework.Globals;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 
 * ClassName: DealerOverstockPartAction 
 * Function: 积压件维护Action
 * date: 2014年9月16日 下午3:25:19
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 
 */
public class DealerOverstockPartAction {

	// 日志类
	private Logger logger = com.org.framework.log.log.getLogger("DealerOverstockPartAction");

	// 上下文对象
	private ActionContext atx = ActionContext.getContext();

	// 定义dao对象
	private DealerOverstockPartDao dao = DealerOverstockPartDao.getInstance(atx);

	/**
	 * queryOverstockPart: 积压件维护查询
	 * @author fuxiao
	 * Date:2014年9月16日上午9:51:44
	 */
	public void queryOverstockPart(){
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			PageManager pageManager = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(requestWrapper,pageManager);
			User user = (User) atx.getSession().get(Globals.USER_KEY);
			atx.setOutData("bs", this.dao.queryStorageParts(pageManager, conditions, user));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
    }
	
	/**
	 * 
	 * queryOverstockPartAll: 全网积压件查询
	 * @author fuxiao
	 * Date:2014年10月16日上午11:20:03
	 */
	public void queryOverstockPartAll(){
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			PageManager pageManager = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(requestWrapper,pageManager);
			User user = (User) atx.getSession().get(Globals.USER_KEY);
			atx.setOutData("bs", this.dao.queryOverstockPart(pageManager, conditions, user));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
    }
	
	/**
	 * 
	 * updatePartIsOverstockMark:更新积压件标示
	 * @author fuxiao
	 * Date:2014年9月16日上午9:53:10
	 */
	public void updatePartIsOverstockMark(){
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			String stockIds = Pub.val(requestWrapper, "stockIds");
			String status = Pub.val(requestWrapper, "status");
			User user = (User) atx.getSession().get(Globals.USER_KEY);
			this.dao.updateOverstockPartMark(stockIds, status, user);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
	
	/**
	 * 
	 * queryOverstockUsableParts:查询可用的积压件
	 * @author fuxiao
	 * Date:2014年9月17日上午9:37:56
	 */
	public void queryOverstockUsableParts(){
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			PageManager pageManager = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(requestWrapper,pageManager);
			User user = (User) atx.getSession().get(Globals.USER_KEY);
			atx.setOutData("bs", this.dao.queryOverstockPartsAll(pageManager, conditions, user));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
	
}
