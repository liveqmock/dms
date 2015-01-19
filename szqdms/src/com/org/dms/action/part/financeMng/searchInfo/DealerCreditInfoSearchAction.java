package com.org.dms.action.part.financeMng.searchInfo;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.financeMng.searchInfo.DealerCreditInfoSearchDao;
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

public class DealerCreditInfoSearchAction {
	
	private Logger logger = com.org.framework.log.log.getLogger(
	        "Logger");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private DealerCreditInfoSearchDao dao = DealerCreditInfoSearchDao.getInstance(atx);
	    
	    public void creditSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			try
			{
				BaseResultSet bs = dao.creditSearch(page,user,conditions);
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
	    		hBean.setName("P_CODE");
	    		hBean.setTitle("办事处代码");
	    		header.add(hBean);
	    		hBean = new HeaderBean();
	    		hBean.setName("P_NAME");
	    		hBean.setTitle("办事处名称");
	    		header.add(hBean);
	    		hBean = new HeaderBean();
	    		hBean.setName("ORG_CODE");
	    		hBean.setTitle("配送中心代码");
	    		header.add(hBean);
	    		hBean = new HeaderBean();
	    		hBean.setName("ORG_NAME");
	    		hBean.setTitle("配送中心名称");
	    		header.add(hBean);
	    		hBean = new HeaderBean();
	    		hBean.setName("BALANCE_AMOUNT");
	    		hBean.setTitle("信用额度");
	    		header.add(hBean);
	    		hBean = new HeaderBean();
	    		hBean.setName("OCC_AMOUNT");
	    		hBean.setTitle("已关闭订单占用");
	    		header.add(hBean);
	    		hBean = new HeaderBean();
	    		hBean.setName("SHOULD_REPAY");
	    		hBean.setTitle("未关闭订单占用");
	    		header.add(hBean);
	    		hBean = new HeaderBean();
	    		hBean.setName("OCCUPY_AMOUNT");
	    		hBean.setTitle("总占用");
	    		header.add(hBean);
	    		hBean = new HeaderBean();
	    		hBean.setName("AVAILABLE_AMOUNT");
	    		hBean.setTitle("总余额");
	    		header.add(hBean);
	    		QuerySet qs = dao.download(conditions,page);
	    		os = response.getHttpResponse().getOutputStream();
	    		response.getHttpResponse().reset();
	    		ExportManager.exportFile(response.getHttpResponse(), "配送中心可用信用额度查询", header, qs);
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
