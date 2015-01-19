package com.org.dms.action.part.purchaseMng.purchaseOrder;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.purchaseMng.purchaseOrder.PurchaseOrderShutDao;
import com.org.dms.vo.part.PtBuPchOrderSplitVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

public class PurchaseOrderShutAction {
	 private Logger logger = com.org.framework.log.log.getLogger(
	        "Logger");
	 private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private PurchaseOrderShutDao dao = PurchaseOrderShutDao.getInstance(atx);
	    /**
	     * 
	     * @date()2014年7月18日上午10:05:30
	     * @author Administrator
	     * @to_do:订单关闭查询
	     * @throws Exception
	     */
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
	public void orderShutDown() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PtBuPchOrderSplitVO tempVO = new PtBuPchOrderSplitVO();
        try
        {
			 String SPLIT_ID = Pub.val(request, "SPLIT_ID");
			QuerySet checkAll = dao.checkAll(SPLIT_ID);
			if(checkAll.getRowCount()>0){
				throw new Exception("该订单所验收配件未能全部入库,不能关单!");
			}else{
				tempVO.setSplitId(SPLIT_ID);
				tempVO.setOrderStatus(DicConstant.CGDDZT_05);
				tempVO.setCloseDate(Pub.getCurrentDate());
				tempVO.setUpdateUser(user.getAccount());
				tempVO.setUpdateTime(Pub.getCurrentDate());
	            dao.shutDownOrder(tempVO);
				atx.setOutMsg("","订单关闭成功！");
			}
			
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }

}
