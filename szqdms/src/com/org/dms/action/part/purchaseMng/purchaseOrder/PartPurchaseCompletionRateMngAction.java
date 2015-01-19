package com.org.dms.action.part.purchaseMng.purchaseOrder;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.purchaseMng.purchaseOrder.PartPurchaseCompletionRateMngDao;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.fileimport.ExportManager;
import com.org.framework.fileimport.HeaderBean;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;

public class PartPurchaseCompletionRateMngAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "Logger");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private PartPurchaseCompletionRateMngDao dao = PartPurchaseCompletionRateMngDao.getInstance(atx);
	    
	    public void pchPartSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			try
			{
				BaseResultSet bs = dao.pchPartSearch(page,user,conditions);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    
	    
	    public void exportExcel() throws Exception{
			ResponseWrapper response= atx.getResponse();
	    	RequestWrapper requestWrapper = atx.getRequest();
	    	PageManager page = new PageManager();
	    	page.setPageRows(99999);
	 		String conditions = RequestUtil.getConditionsWhere(requestWrapper,page);
	 		OutputStream os = null;
	        try
	        {
	        	List<HeaderBean> header = new ArrayList<HeaderBean>();
	        	HeaderBean hBean = null;
		    	hBean = new HeaderBean();
	    		hBean.setName("SUPPLIER_NAME");
	    		hBean.setTitle("供应商名称");
	    		header.add(hBean);
	    		hBean = new HeaderBean();
	    		hBean.setName("SUPPLIER_CODE");
	    		hBean.setTitle("供应商编码");
	    		header.add(hBean);
	    		hBean = new HeaderBean();
	    		hBean.setName("SPLIT_NO");
	    		hBean.setTitle("采购订单拆分号");
	    		header.add(hBean);
	    		hBean = new HeaderBean();
	    		hBean.setName("PART_CODE");
	    		hBean.setTitle("配件代码");
	    		header.add(hBean);
	    		hBean = new HeaderBean();
	    		hBean.setName("PART_NAME");
	    		hBean.setTitle("配件名称");
	    		hBean.setWidth(50);
	    		header.add(hBean);
	    		hBean = new HeaderBean();
	    		hBean.setName("APPLY_CYCLE");
	    		hBean.setTitle("采购周期");
	    		header.add(hBean);
	    		hBean = new HeaderBean();
	    		hBean.setName("PCH_COUNT");
	    		hBean.setTitle("计划采购数量");
	    		header.add(hBean);
	    		hBean = new HeaderBean();
	    		hBean.setName("STORAGE_COUNT");
	    		hBean.setTitle("累计完成数量");
	    		header.add(hBean);
	    		hBean = new HeaderBean();
	    		hBean.setName("APPLY_DATE");
	    		hBean.setTitle("订单创建时间");
	    		header.add(hBean);
	    		hBean = new HeaderBean();
	    		hBean.setName("REPUIREMENT_TIME");
	    		hBean.setTitle("要求完成时间");
	    		header.add(hBean);
	    		hBean = new HeaderBean();
	    		hBean.setName("ON_TIME_RATE");
	    		hBean.setTitle("按期完成率(%)");
	    		header.add(hBean);
	    		hBean.setName("OUT_TIME_RATE");
	    		hBean.setTitle("拖期完成率(%)");
	    		header.add(hBean);
	    		hBean.setName("UN_RATE");
	    		hBean.setTitle("未完成率(%)");
	    		header.add(hBean);
	    		QuerySet qs = dao.download(conditions,page);
	    		os = response.getHttpResponse().getOutputStream();
	    		response.getHttpResponse().reset();
	    		ExportManager.exportFile(response.getHttpResponse(), "配件采购完成率汇总", header, qs);
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
