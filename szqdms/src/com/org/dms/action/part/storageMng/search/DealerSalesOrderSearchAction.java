package com.org.dms.action.part.storageMng.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.storageMng.search.DealerSalesOrderQueryDao;
import com.org.framework.Globals;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.fileimport.ExportManager;
import com.org.framework.fileimport.HeaderBean;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;

public class DealerSalesOrderSearchAction {

	// 日志类
	private Logger logger = com.org.framework.log.log.getLogger("DealerSalesOrderSearchAction");
	// 上下文对象
	private ActionContext atx = ActionContext.getContext();
	// 定义dao对象
	private DealerSalesOrderQueryDao dao = DealerSalesOrderQueryDao.getInstance(atx);
    // 定义reponse对象
    private ResponseWrapper responseWrapper = atx.getResponse();
    // 获取session中的user对象
    private User user = (User) atx.getSession().get(Globals.USER_KEY);

	/**
     * 订单查询导出
     * @throws Exception
     */
    public void download()throws Exception{

        try {
        	//获取封装后的request对象
        	RequestWrapper request = atx.getRequest();
        	// 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(request, pageManager);
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;
            hBean = new HeaderBean();
            hBean.setName("ORDER_NO");
            hBean.setTitle("订单编号");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("ORDER_TYPE");
            hBean.setTitle("订单类型");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("ORDER_STATUS");
            hBean.setTitle("订单状态");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("SHIP_STATUS");
            hBean.setTitle("发运状态");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("INVOICE_STATUS");
            hBean.setTitle("开票状态");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("TRANS_TYPE");
            hBean.setTitle("运输方式");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("LINK_MAN");
            hBean.setTitle("联系人");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("PHONE");
            hBean.setTitle("联系电话");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("IF_DELAY_ORDER");
            hBean.setTitle("是否延期");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("APPLY_DATE");
            hBean.setTitle("提报日期");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("ORDER_AMOUNT");
            hBean.setTitle("订单总金额");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("REAL_AMOUNT");
            hBean.setTitle("实发总金额");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("CLOSE_DATE");
            hBean.setTitle("关闭订单日期");
            header.add(hBean);
            
            QuerySet querySet = dao.download(conditions,user);
            ExportManager.exportFile(responseWrapper.getHttpResponse(), "DDCXDC", header, querySet);
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
	/**
	 * 
	 * salesOrderQuery: 主页面Index表格信息查询
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * @author fuxiao
	 * Date:2014年9月12日上午11:35:13
	 */
	public void salesOrderQuery(){
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			PageManager pageManager = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(requestWrapper,pageManager);
			User user = (User) atx.getSession().get(Globals.USER_KEY);
			atx.setOutData("bs", this.dao.searchOrder(pageManager, user,conditions, true));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
	
	/**
	 * 
	 * orderPartInfoQuery:查询配件详情
	 * @author fuxiao
	 * Date:2014年9月12日下午11:59:08
	 */
	public void orderPartInfoQuery(){
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			PageManager pageManager = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(requestWrapper,pageManager);
			String orderId = Pub.val(requestWrapper, "orderId");
			User user = (User) atx.getSession().get(Globals.USER_KEY);
			atx.setOutData("bs", this.dao.queryOrderPartInfo(pageManager, conditions, orderId, user));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
	
	/**
	 * 
	 * selectOrderInfoById: 查询订单主信息，并已JSON格式返回
	 * @author fuxiao
	 * Date:2014年9月12日下午4:11:58
	 */
	public void selectOrderInfoById(){
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			String orderId = Pub.val(requestWrapper, "orderId");
			
			// 查询订单主信息
			QuerySet  tempQS = this.dao.queryOrderInfoByOrderId(orderId);
			Map<String,String> tempMap = new HashMap<String,String>();
			if(tempQS.getRowCount() > 0){
				tempMap.put("ORDER_NO", tempQS.getString(1, "ORDER_NO"));
				tempMap.put("ORDER_TYPE", tempQS.getString(1, "ORDER_TYPE"));
				tempMap.put("ORDER_STATUS", tempQS.getString(1, "ORDER_STATUS"));
				tempMap.put("SHIP_STATUS", tempQS.getString(1, "SHIP_STATUS"));
				tempMap.put("INVOICE_STATUS", tempQS.getString(1, "INVOICE_STATUS"));
				tempMap.put("TRANS_TYPE", tempQS.getString(1, "TRANS_TYPE"));
				tempMap.put("IF_DELAY_ORDER", tempQS.getString(1, "IF_DELAY_ORDER"));
				tempMap.put("IF_CHANEL_ORDER", tempQS.getString(1, "IF_CHANEL_ORDER"));
				tempMap.put("WAREHOUSE_CODE", tempQS.getString(1, "WAREHOUSE_CODE"));
				tempMap.put("WAREHOUSE_NAME", tempQS.getString(1, "WAREHOUSE_NAME"));
				tempMap.put("ORDER_AMOUNT", tempQS.getString(1, "ORDER_AMOUNT"));
				tempMap.put("EXPECT_DATE", tempQS.getString(1, "EXPECT_DATE"));
				tempMap.put("IF_CREDIT", tempQS.getString(1, "IF_CREDIT"));
				tempMap.put("IF_TRANS", tempQS.getString(1, "IF_TRANS"));
				tempMap.put("CUSTORM_NAME", tempQS.getString(1, "CUSTORM_NAME"));
				tempMap.put("DELIVERY_ADDR", tempQS.getString(1, "DELIVERY_ADDR"));
				tempMap.put("LINK_MAN", tempQS.getString(1, "LINK_MAN"));
				tempMap.put("PHONE", tempQS.getString(1, "PHONE"));
				tempMap.put("ZIP_CODE", tempQS.getString(1, "ZIP_CODE"));
				tempMap.put("REMARKS", tempQS.getString(1, "REMARKS"));
			}
			JSONObject jsonObj = JSONObject.fromObject(tempMap);
			atx.setOutMsg(jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
	
	/**
	 * 
	 * salesOrderAmountById:根据orderId获取金额扣款明细
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * @author fuxiao
	 * Date:2014年9月12日下午5:48:57
	 */
	public void salesOrderAmountById(){
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			PageManager pageManager = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(requestWrapper,pageManager);
			String orderId = Pub.val(requestWrapper, "orderId");
			User user = (User) atx.getSession().get(Globals.USER_KEY);
			atx.setOutData("bs", this.dao.queryOrderAmountDetails(pageManager, conditions,orderId, user));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
	
	/**
	 * 
	 * checkOrderInfoById: 根据OrderId查询订单审核情况
	 * @author fuxiao
	 * Date:2014年9月12日下午10:15:37
	 */
	public void checkOrderInfoById(){
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			PageManager pageManager = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(requestWrapper,pageManager);
			String orderId = Pub.val(requestWrapper, "orderId");
			User user = (User) atx.getSession().get(Globals.USER_KEY);
			atx.setOutData("bs", this.dao.queryOrderCheckInfo(pageManager, conditions,orderId, user));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
	
}
