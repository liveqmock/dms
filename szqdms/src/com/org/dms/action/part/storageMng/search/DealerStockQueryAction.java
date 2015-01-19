package com.org.dms.action.part.storageMng.search;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.storageMng.search.DealerStockQueryDao;
import com.org.framework.Globals;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 
 * ClassName: DealerStockQueryAction 
 * Function: 信息查询 - 仓储相关 - 经销商库存查询Action
 * date: 2014年9月10日 下午3:01:32
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 
 */
public class DealerStockQueryAction {
    //日志类
    private Logger logger = com.org.framework.log.log.getLogger("DealerStockQueryAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private DealerStockQueryDao  dao = DealerStockQueryDao.getInstance(atx);
	
    /**
     * 车厂库存查询
     * queryListInfo:(这里用一句话描述这个方法的作用). 
     * @author fuxiao
     * @since JDK 1.6
     */
	public void queryListInfo(){
		try {
			RequestWrapper request = atx.getRequest();
			PageManager page = new PageManager();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			String conditions = RequestUtil.getConditionsWhere(request, page);
			atx.setOutData("bs", this.dao.queryList(page, conditions, user));
		} catch (Exception e) {
			 logger.error(e);
             atx.setException(e);
		}
		
	}
	
	
}
