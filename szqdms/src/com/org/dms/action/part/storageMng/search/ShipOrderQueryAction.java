package com.org.dms.action.part.storageMng.search;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.storageMng.search.ShipOrderQueryDao;
import com.org.framework.Globals;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.fileimport.ExportManager;
import com.org.framework.fileimport.HeaderBean;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;

/**
 * 
 * ClassName: ShipOrderQueryAction 
 * Function: 发货详单查询
 * date: 2014年10月25日 上午11:10:53
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class ShipOrderQueryAction {
	// 日志类
	private Logger logger = com.org.framework.log.log.getLogger("ShipOrderQueryAction");
	// 上下文对象
	private ActionContext atx = ActionContext.getContext();
	// 定义dao对象
	private ShipOrderQueryDao dao = ShipOrderQueryDao.getInstance(atx);

	/**
	 * 
	 * queryListInfo: 表单查询  
	 * @author fuxiao Date:2014年10月23日上午10:36:58
	 */
	public void queryListInfo() {
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			PageManager pageManager = new PageManager();
			User user = (User) atx.getSession().get(Globals.USER_KEY);
			String conditions = RequestUtil.getConditionsWhere(requestWrapper,pageManager);
			atx.setOutData("bs", this.dao.queryList(pageManager, conditions, user));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}

	/**
	 * 
	 * exportExcel: 查询数据导出  
	 * @author fuxiao Date:2014年10月23日
	 *
	 */
	public void exportExcel() throws Exception{
		ResponseWrapper response= atx.getResponse();
    	RequestWrapper requestWrapper = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	PageManager page = new PageManager();
 		String conditions = RequestUtil.getConditionsWhere(requestWrapper,page);
 		OutputStream os = null;
        try
        {
        	List<HeaderBean> header = new ArrayList<HeaderBean>();
        	HeaderBean hBean = null;
	    	hBean = new HeaderBean();
    		hBean.setName("SHIP_NO");
    		hBean.setTitle("发运单号");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("SHIP_DATE");
    		hBean.setTitle("发运日期");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("SHIP_STATUS");
    		hBean.setTitle("发运状态");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("RECEIPT_NO");
    		hBean.setTitle("回执单号");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("ORDER_NO");
    		hBean.setTitle("订单号");
    		header.add(hBean);
/*    		hBean = new HeaderBean();
    		hBean.setName("ORDER_AMOUNT");
    		hBean.setTitle("订单金额");
    		header.add(hBean);*/
    		hBean = new HeaderBean();
    		hBean.setName("ORDER_AMOUNT");
    		hBean.setTitle("实发金额");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("ORG_NAME");
    		hBean.setTitle("收货单位");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("CARRIER_NAME");
    		hBean.setTitle("货运公司");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("CARRIER_CODE");
    		hBean.setTitle("货运公司代码");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("DELIVERY_ADDR");
    		hBean.setTitle("收货地址");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("LICENSE_PLATE");
    		hBean.setTitle("车牌");
    		header.add(hBean);
    		QuerySet qs = dao.queryDownInfo(conditions, user);
    		os = response.getHttpResponse().getOutputStream();
    		response.getHttpResponse().reset();
    		ExportManager.exportFile(response.getHttpResponse(), "发货详情下载", header, qs);
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
        finally
        {
        	if (os != null)
            {
              os.close();
            }
        }
	}
}
