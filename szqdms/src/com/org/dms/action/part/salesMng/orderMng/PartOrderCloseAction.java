package com.org.dms.action.part.salesMng.orderMng;


import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.salesMng.orderMng.PartOrderCloseDao;
import com.org.dms.vo.part.PtBuAccountLogVO;
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
public class PartOrderCloseAction {
    private Logger logger = com.org.framework.log.log.getLogger("PartOrderCloseAction");
    private ActionContext atx = ActionContext.getContext();
    private PartOrderCloseDao dao = PartOrderCloseDao.getInstance(atx);

    /**
     * 配件订单明细打印查询
     * @throws Exception
     */
    public void partOrderDtlPrintSearch() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PageManager page = new PageManager();
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            String orderId = Pub.val(request, "orderId");
            page.setPageRows(999999);
            BaseResultSet bs = dao.partOrderDtlPrintSearch(page, user, conditions, orderId);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
    /**
     * 订单关闭查询
     */
    public void partOrderCloseSearch() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PageManager page = new PageManager();
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            BaseResultSet bs = dao.partOrderCloseSearch(page, user, conditions);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
    /**
     * 订单关闭
     * @throws Exception
     */
    public void partOrderClose() throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String orgId= user.getOrgId();
        String orderId = Pub.val(request, "orderId");//定单ID
        // 总金额
        Double zje = 0.00;
        // 占用金额
        Double zyje= null;
        try
        {
        	// 查询订单状态
            QuerySet transTypeQuerySet = dao.checkOrderType(orderId);
            QuerySet qst1 = dao.getOrderDtl(orderId);
            String transType = "";
            String orderType = "";
            if (qst1.getRowCount()>0) {
            	transType = transTypeQuerySet.getString(1, "TRANS_TYPE");
            	orderType = transTypeQuerySet.getString(1, "ORDER_TYPE");
            }
            // 发运方式为：自提
            if(DicConstant.FYFS_02.equals(transType)){
            	for (int i=0;i<qst1.getRowCount();i++) {
            		String partId = qst1.getString(i+1, "PART_ID");
            		dao.updateSaleOrderDtl(orderId,user,partId);
            	}
            } else {
            	// 包装数量是否与出库数量一致验证
                QuerySet querySet = dao.closeCheck(orderId);
                if (querySet.getRowCount() <= 0) {
                    throw new Exception("包装数量与出库数量不一致.");
                }
            }
            
            // 实发总金额
            QuerySet querySet1 = dao.selectSaleOrderAmount(orderId,user);
            if (querySet1.getRowCount() > 0) {
            	zje = Double.valueOf(querySet1.getString(1, "COUNT"));
            }
            // 更新实发金额
            PtBuSaleOrderVO vo =new PtBuSaleOrderVO();
            vo.setOrderId(orderId);
            vo.setOrderStatus(DicConstant.DDZT_06);
            vo.setInvoiceStatus(DicConstant.KPZT_01);
            vo.setUpdateTime(Pub.getCurrentDate());
            vo.setUpdateUser(user.getAccount());
            vo.setCloseDate(Pub.getCurrentDate());

            if (!DicConstant.DDLX_05.equals(orderType)) {
            	// 直发订单
            	vo.setRealAmount(zje.toString());
            }
            dao.updateOrder(vo);

            QuerySet qsOrder = dao.selectSourceOrder(orderId);
            if (qsOrder.getRowCount() > 0) {
            	String sourceOrderId = qsOrder.getString(1, "DIR_SOURCE_ORDER_ID");
            	// 有原单(三包急件，技术升级，直营订单)
            	vo.setOrderId(sourceOrderId);
            	// 更新实发金额(原订单)
            	dao.updateOrder(vo);
            	// 发运方式为：自提
            	if(DicConstant.FYFS_02.equals(transType)){
            		QuerySet qst2 = dao.getOrderDtl(sourceOrderId);
            		for (int j=0;j<qst2.getRowCount();j++) {
            			String partId = qst2.getString(j+1, "PART_ID");
            			dao.updateSaleOrderDtl(sourceOrderId,user,partId);
            		}
                }
            }
            
            //查询占用金额
            QuerySet qs = dao.queryZYJE(orderId);
            // 剩余金额
            Double syje = zje;
            if(qs.getRowCount()>0){
                for(int i=0;i<qs.getRowCount();i++){
                    String occupyFunds = qs.getString(i+1, "OCCUPY_FUNDS");
                    String accountId = qs.getString(i+1, "ACCOUNT_ID");
                    String accountType = qs.getString(i+1, "ACCOUNT_TYPE");
                    String ofundsId = qs.getString(i+1, "OFUNDS_ID");
	                // 占用金额
	                zyje=Double.parseDouble(occupyFunds);
                    //实施订单占用金额实扣---除信用额度
                    if(!DicConstant.ZJZHLX_04.equals(accountType)){
	                    if(syje>0){
		                    if (syje >=zyje) {
		                    	//将实发金额更新至订单表中
			                    dao.updateAmount(zyje,accountId);//更新账户
			                    dao.updateFunds(ofundsId);//更新占用记录
			                    String amount =zyje.toString();
			                    PtBuAccountLogVO vo1 = new PtBuAccountLogVO();
			                    vo1.setAccountId(accountId);
			                    vo1.setLogType(DicConstant.ZJYDLX_04);
			                    vo1.setAmount(amount);
			                    vo1.setOrgId(orgId);
			                    vo1.setSourceaccountType(accountType);
			                    vo1.setInDate(Pub.getCurrentDate());
			                    vo1.setCreateTime(Pub.getCurrentDate());
			                    vo1.setCreateUser(user.getAccount());
			                    dao.insertamountLog(vo1);//插入资金异动
			                    syje = syje-zyje;
		                    } else {
		                    	//Double hkje = zyje-syje;
		                    	dao.updateAmountTwo(zyje,syje,accountId);//更新账户
			                    dao.updateFunds(ofundsId);//更新占用记录
			                    PtBuAccountLogVO vo1 = new PtBuAccountLogVO();
			                    vo1.setAccountId(accountId);
			                    vo1.setLogType(DicConstant.ZJYDLX_04);
			                    vo1.setAmount(syje.toString());
			                    vo1.setOrgId(orgId);
			                    vo1.setSourceaccountType(accountType);
			                    vo1.setInDate(Pub.getCurrentDate());
			                    vo1.setCreateTime(Pub.getCurrentDate());
			                    vo1.setCreateUser(user.getAccount());
			                    dao.insertamountLog(vo1);//插入资金异动
			                    syje = 0.00;
		                    }
	                    }else{
	                    	dao.updateAmountTwo(zyje,syje,accountId);//更新账户
		                    dao.updateFunds(ofundsId);//更新占用记录
	                    }
	                    //更新标识
	                    PtBuSaleOrderVO vo1 =new PtBuSaleOrderVO();
	                    vo1.setOrderId(orderId);
	                    vo1.setFlag("1");
	                    dao.updateOrder(vo1);
                    }else{
                    	if(zyje>syje){
                    		dao.updateAmountXyed(zyje,syje,accountId);//更新账户
		                    dao.updateXyedFunds(ofundsId,String.valueOf(syje));//更新占用记录
                    	}
                    }
                }
            }
            //更新计划价格
            dao.updatePlanPrice(orderId);
            dao.updateOutFlow(orderId);
            //返回更新结果和成功信息
            atx.setOutMsg("1","关闭成功！");
        }
        catch (Exception e)
        {
            atx.setException(e);
            logger.error(e);
        }
    }
}
