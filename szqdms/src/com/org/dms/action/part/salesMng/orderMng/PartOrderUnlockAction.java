package com.org.dms.action.part.salesMng.orderMng;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.salesMng.orderMng.PartOrderUnlockDao;
import com.org.dms.vo.part.PtBuSaleOrderVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

public class PartOrderUnlockAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "Logger");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private PartOrderUnlockDao dao = PartOrderUnlockDao.getInstance(atx);
	    
	    public void partOrderSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			try
			{
				QuerySet checkIfam = dao.checkIfAm(user);
				String ifAm = checkIfam.getString(1, "IS_AM");
				BaseResultSet bs = dao.partOrderSearch(page,user,conditions,ifAm);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    /**
	     * 
	     * @date()2014年10月10日下午3:58:40
	     * @author Administrator
	     * @to_do:已提报之后的订单解锁释放库存，释放占用资金，如有发料单将发料单置为无效
	     * @throws Exception
	     */
	    public void partOrderUnlock() throws Exception {
	    	//获取封装后的request对象
	    	RequestWrapper request = atx.getRequest();
	    	User user = (User) atx.getSession().get(Globals.USER_KEY);
	    	//String orgId= user.getOrgId();
	        String orderId = Pub.val(request, "orderId");//定单ID
	        String orderStatus = Pub.val(request, "orderStatus");//定单状态
	        String shipStatus = Pub.val(request, "shipStatus");//发运状态
	        PtBuSaleOrderVO vo =new PtBuSaleOrderVO();
	        try
	        {
	        	//订单释放资金占用
	        	//dao.orderReleaseFreez(orderId,user);
	        	if(orderStatus.equals(DicConstant.DDZT_03)){
	        		//库存占用释放
	        		dao.orderInventoryFreez(orderId,user);
	        	}
	        	if(shipStatus==DicConstant.DDFYZT_02){
	        		//如果发运状态为发料单已生成，将发料单置为无效。
	        		dao.updateIssue(orderId,user);
	        	}
	        	
	        	// 删除订单占用记录
	        	dao.deleteOrderOccupy(orderId);
	        	//更改订单明细表信息
	        	/**
	        	 * 1，判断是否有延期单生成如果有，将订购数量改为审核数量，将审核数量置为0
	        	 * 2，如果没有则不修改订购数量，直接将审核数量置为0
	        	 */
	        	QuerySet checkDelay = dao.checkDelay(orderId);
	        	if(checkDelay.getRowCount()>0){//有延期订单
	        		dao.updateOrderDtl01(orderId,user);
	        	}else{//无延期订单
	        		dao.updateOrderDtl02(orderId,user);
	        	}
	        	QuerySet qs = dao.queryOrderAmount(orderId);
	        	if(qs.getRowCount()>0){
	        		vo.setOrderAmount(qs.getString(1, "ORDER_AMOUNT"));
	        	}
	        	//更改订单主表信息
	        	vo.setOrderId(orderId);
	        	vo.setOrderStatus(DicConstant.DDZT_02);
	        	vo.setShipStatus(DicConstant.DDFYZT_01);
	        	vo.setUpdateTime(Pub.getCurrentDate());
	        	vo.setUpdateUser(user.getAccount());
	        	dao.updateOrderStatus(vo);
	        	atx.setOutMsg("1", "解锁成功！");
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }
	    
	    public void saleOrderInfoSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			try
			{
				String ORDER_ID = Pub.val(request, "ORDER_ID");
				BaseResultSet bs = dao.saleOrderInfoSearch(page,user,conditions,ORDER_ID);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    public void partOrderDetailSearch() throws Exception {
	        RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        String orderId = request.getParamValue("orderId");
	        PageManager page = new PageManager();
	        String conditions = RequestUtil.getConditionsWhere(request, page);
	        page.setPageRows(10000);
	        try {
	            BaseResultSet bs = dao.partOrderDetailSearch(page, user, conditions,orderId);
	            atx.setOutData("bs", bs);
	        } catch (Exception e) {
	            logger.error(e);
	            atx.setException(e);
	        }
	    }
	    public void accountSearch() throws Exception {
	        RequestWrapper request = atx.getRequest();
	        PageManager page = new PageManager();
	        String orgId = request.getParamValue("orgId");
	        String conditions = RequestUtil.getConditionsWhere(request, page);
	        try {
	            BaseResultSet bs = dao.accountSearch(page, orgId, conditions);
	            atx.setOutData("bs", bs);
	        } catch (Exception e) {
	            logger.error(e);
	            atx.setException(e);
	        }
	    }
	    public void orderFundsListSearch() throws Exception {
	        RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        String orderId = request.getParamValue("orderId");
	        PageManager page = new PageManager();
	        String conditions = RequestUtil.getConditionsWhere(request, page);
	        try {
	            BaseResultSet bs = dao.orderFundsListSearch(page, user, conditions,orderId);
	            atx.setOutData("bs", bs);
	        } catch (Exception e) {
	            logger.error(e);
	            atx.setException(e);
	        }
	    }
	    public void orderCheckListSearch() throws Exception {
	        RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        String orderId = request.getParamValue("orderId");
	        PageManager page = new PageManager();
	        String conditions = RequestUtil.getConditionsWhere(request, page);
	        try {
	            BaseResultSet bs = dao.orderCheckListSearch(page, user, conditions,orderId);
	            atx.setOutData("bs", bs);
	        } catch (Exception e) {
	            logger.error(e);
	            atx.setException(e);
	        }
	    }
	    public void getCarrier() throws Exception {
	        RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        PageManager page = new PageManager();
	        String conditions = RequestUtil.getConditionsWhere(request, page);
	        try {
	            BaseResultSet bs = dao.getCarrier(page, user, conditions);
	            atx.setOutData("bs", bs);
	        } catch (Exception e) {
	            logger.error(e);
	            atx.setException(e);
	        }
	    }

}
