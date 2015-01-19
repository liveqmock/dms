package com.org.dms.action.part.salesMng.orderMng;


import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.salesMng.orderMng.PartOrderCancelDao;
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

/**
 * @Author: sunxuedong
 * @Date: 2014.08.25
 * @Description:配件订单关闭Action
 */
public class PartOrderCancelAction {
	private Logger logger = com.org.framework.log.log.getLogger("PartOrderCancelAction");
	private ActionContext atx = ActionContext.getContext();
	private PartOrderCancelDao dao = PartOrderCancelDao.getInstance(atx);
	/**
     * 订单取消查询
     */
    public void partOrderCancelSearch() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PageManager page = new PageManager();
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            BaseResultSet bs = dao.partOrderCancelSearch(page, user, conditions);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
    /**
     * 订单取消
     * @throws Exception
     */
    public void partOrderCancel() throws Exception {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	//String orgId= user.getOrgId();
        String orderId = Pub.val(request, "orderId");//定单ID
        String orderStatus = Pub.val(request, "orderStatus");//定单状态
        String shipStatus = Pub.val(request, "shipStatus");//发运状态
        String orderType = Pub.val(request, "orderType");//发运状态
        PtBuSaleOrderVO vo =new PtBuSaleOrderVO();
        try
        {
        	if(DicConstant.DDLX_05.equals(orderType)){
        		QuerySet qs =dao.queryPchOrderStatus(orderId);
        		if(qs.getRowCount()>0){
        			dao.deletePchOrder(orderId);
        		}else{
        			throw new Exception("该直发订单的直发采购订单已提报，不能进行取消操作.");
        		}
        	}
        	//订单释放资金占用
        	dao.orderReleaseFreez(orderId,user);
        	if(orderStatus.equals(DicConstant.DDZT_03)){
        		//库存占用释放
        		dao.orderInventoryFreez(orderId,user);
        	}
        	if(shipStatus==DicConstant.DDFYZT_02){
        		//如果发运状态为发料单已生成，将发料单置为无效。
        		dao.updateIssue(orderId,user);
        	}
        	vo.setOrderId(orderId);
        	vo.setOrderStatus(DicConstant.DDZT_05);
        	vo.setUpdateTime(Pub.getCurrentDate());
        	vo.setUpdateUser(user.getAccount());
        	dao.updateOrderStatus(vo);
        	atx.setOutMsg("1", "取消成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
}
