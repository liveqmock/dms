package com.org.dms.action.part.purchaseMng.purchaseOrder;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.purchaseMng.purchaseOrder.PurchaseOrderDetailDao;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

public class PurchaseOrderDetailAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "PurchaseOrderDetailAction");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private PurchaseOrderDetailDao dao = PurchaseOrderDetailDao.getInstance(atx);
	    
	    public void purchaseOrderInfoSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			try
			{
				String SPLIT_ID = Pub.val(request, "SPLIT_ID");
				BaseResultSet bs = dao.purchaseOrderInfoSearch(page,user,conditions,SPLIT_ID);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    
	    public void purchaseOrderPartSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			try
			{
				String SPLIT_ID = Pub.val(request,"SPLIT_ID");
				RequestUtil.getConditionsWhere(request,page);
				BaseResultSet bs = dao.purchaseOrderPartSearch(page,user,SPLIT_ID);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    
	    
	    public void orderSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			try
			{
				BaseResultSet bs = dao.orderSearch(page,user,conditions);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    
	    public void partSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			try
			{
				BaseResultSet bs = dao.partSearch(page,user, conditions);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}

}
