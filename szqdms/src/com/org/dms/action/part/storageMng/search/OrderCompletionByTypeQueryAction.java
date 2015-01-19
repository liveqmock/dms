package com.org.dms.action.part.storageMng.search;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.storageMng.search.OrderCompletionByTypeQueryDao;
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
 * ClassName: OrderCompletionByTypeQueryAction 
 * Function: 配送中心订单完成情况统计(按类别)
 * date: 2014年11月1日 下午6:46:00
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class OrderCompletionByTypeQueryAction {
	
	// 日志类
	private Logger logger = com.org.framework.log.log.getLogger("OrderCompletionByTypeQueryAction");
	// 上下文对象
	private ActionContext atx = ActionContext.getContext();
	// 定义dao对象
	private OrderCompletionByTypeQueryDao dao = OrderCompletionByTypeQueryDao.getInstance(atx);

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
			atx.setOutData("bs", this.dao.queryList(requestWrapper, pageManager, conditions));
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
    		hBean.setName("LT_MM_AMOUNT");
    		hBean.setTitle("轮胎类当月销售累计");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("LT_YYYY_AMOUNT");
    		hBean.setTitle("轮胎类年销售累计");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("LHQ_MM_AMOUNT");
    		hBean.setTitle("离合器类当月销售累计");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("LHQ_YYYY_AMOUNT");
    		hBean.setTitle("离合器类年销售累计");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("YP_MM_AMOUNT");
    		hBean.setTitle("油品类当月销售累计");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("YP_YYYY_AMOUNT");
    		hBean.setTitle("油品类年销售累计");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("LX_MM_AMOUNT");
    		hBean.setTitle("滤芯类当月销售累计");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("LX_YYYY_AMOUNT");
    		hBean.setTitle("滤芯类年销售累计");
    		header.add(hBean);
    		QuerySet qs = dao.queryDownInfo(requestWrapper, conditions);
    		os = response.getHttpResponse().getOutputStream();
    		response.getHttpResponse().reset();
    		ExportManager.exportFile(response.getHttpResponse(), "配送中心订单完成情况统计（按类别）", header, qs);
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
