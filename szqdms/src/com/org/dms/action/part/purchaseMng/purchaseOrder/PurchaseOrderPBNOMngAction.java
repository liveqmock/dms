package com.org.dms.action.part.purchaseMng.purchaseOrder;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.purchaseMng.purchaseOrder.PurchaseOrderPBNOMngDao;
import com.org.dms.vo.part.PtBuPchOrderSplitVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

public class PurchaseOrderPBNOMngAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "Logger");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private PurchaseOrderPBNOMngDao dao = PurchaseOrderPBNOMngDao.getInstance(atx);
	    
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
	    public void purchaseOrderUpdate() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        PtBuPchOrderSplitVO tempVO = new PtBuPchOrderSplitVO();
	        try
	        {
	            HashMap<String,String> hm;
				hm = RequestUtil.getValues(request);
				tempVO.setValue(hm);
				tempVO.setUpdateUser(user.getAccount());
				tempVO.setUpdateTime(Pub.getCurrentDate());//
	            dao.updatePurchaseOrder(tempVO);
	            atx.setOutMsg(tempVO.getRowXml(),"计配号添加成功！");
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }

}
