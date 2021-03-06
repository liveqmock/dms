package com.org.dms.action.part.storageMng.search;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.storageMng.search.OrderCompletionQueryDao;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.fileimport.ExportManager;
import com.org.framework.fileimport.HeaderBean;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;

/**
 * 
 * ClassName: OrderCompletionQueryAction 
 * Function: 配送中心订单完成情况统计
 * date: 2014年11月1日 上午11:08:07
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class OrderCompletionQueryAction {
	
	// 日志类
	private Logger logger = com.org.framework.log.log.getLogger("OrderCompletionQueryAction");
	// 上下文对象
	private ActionContext atx = ActionContext.getContext();
	// 定义dao对象
	private OrderCompletionQueryDao dao = OrderCompletionQueryDao.getInstance(atx);

	/**
	 * 
	 * queryListInfo: 表单查询  
	 * @author fuxiao Date:2014年10月23日上午10:36:58
	 */
	public void queryListInfo() {
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			PageManager pageManager = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(requestWrapper,pageManager);
			atx.setOutData("bs", this.dao.queryList(pageManager, conditions));
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
    	PageManager page = new PageManager();
 		String conditions = RequestUtil.getConditionsWhere(requestWrapper,page);
 		OutputStream os = null;
        try
        {
        	List<HeaderBean> header = new ArrayList<HeaderBean>();
        	HeaderBean hBean = null;
    		hBean = new HeaderBean();
    		hBean.setName("ORG_CODE");
    		hBean.setTitle("配送中心代码");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("ORG_NAME");
    		hBean.setTitle("配送中心名称");
    		header.add(hBean);
	    	hBean = new HeaderBean();
    		hBean.setName("YYYY_ORDER_AMOUNT");
    		hBean.setTitle("年度订单累计金额");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("YYYY_REAL_AMOUNT");
    		hBean.setTitle("年度订单实发金额");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("MM_ORDER_AMOUNT");
    		hBean.setTitle("本月订单累计金额");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("MM_REAL_AMOUNT");
    		hBean.setTitle("本月订单实发金额");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("MM_RATE");
    		hBean.setTitle("本月实发率");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("WW_ORDER_AMOUNT");
    		hBean.setTitle("本周订单累计金额");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("WW_REAL_AMOUNT");
    		hBean.setTitle("本周订单实发金额");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("WW_RATE");
    		hBean.setTitle("本周实发率");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("BALANCE_AMOUNT");
    		hBean.setTitle("可用信用度");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("PART_AMOUNT");
    		hBean.setTitle("实时库存额");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("BA_AMOUNT");
    		hBean.setTitle("应收账款");
    		header.add(hBean);
    		QuerySet qs = dao.queryDownInfo(conditions);
    		os = response.getHttpResponse().getOutputStream();
    		response.getHttpResponse().reset();
    		ExportManager.exportFile(response.getHttpResponse(), "配送中心订单完成情况统计", header, qs);
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
