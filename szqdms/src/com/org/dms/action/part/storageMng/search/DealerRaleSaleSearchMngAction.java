package com.org.dms.action.part.storageMng.search;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.storageMng.search.DealerRaleSaleSearchMngDao;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
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

public class DealerRaleSaleSearchMngAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "Logger");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private DealerRaleSaleSearchMngDao dao = DealerRaleSaleSearchMngDao.getInstance(atx);
	    
	    public void realSalesearch() throws Exception {
	        RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        PageManager page = new PageManager();
	        String conditions = RequestUtil.getConditionsWhere(request,page);
	        
	        try {
	        	HashMap<String,String> hm;
				hm = RequestUtil.getValues(request);
				String partCode = hm.get("PART_CODE");
	            BaseResultSet bs = dao.realSalesearch(page,user,conditions,partCode);
	            atx.setOutData("bs", bs);
	        } catch (Exception e) {
	            logger.error(e);
	            atx.setException(e);
	        }
	    }
	    
	    public void realSaleOrderInfoSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			try
			{
				String SALE_ID = Pub.val(request, "SALE_ID");
				BaseResultSet bs = dao.saleOrderInfoSearch(page,user,conditions,SALE_ID);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    public void realSaleDtlsearch() throws Exception {
	        RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        PageManager page = new PageManager();
	        String saleId=request.getParamValue("saleId");
	        try {
	        	RequestUtil.getConditionsWhere(request, page);
	        	page.setPageRows(999999);
	            BaseResultSet bs = dao.realSaleDtlsearch(page,user,saleId);
	            atx.setOutData("bs", bs);
	        } catch (Exception e) {
	            logger.error(e);
	            atx.setException(e);
	        }
	    }

	    
	    /**
	     * 
	     * @Title: exportExcel
	     * @Description: 前台下载方法
	     * @throws Exception
	     * @return: void
	     */
	    public void exportExcel() throws Exception{
			ResponseWrapper response= atx.getResponse();
	    	RequestWrapper requestWrapper = atx.getRequest();
	    	User user = (User) atx.getSession().get(Globals.USER_KEY);
	    	PageManager page = new PageManager();
	 		String conditions = RequestUtil.getConditionsWhere(requestWrapper,page);
	 		HashMap<String,String> hm = RequestUtil.getValues(requestWrapper);
			String partCode = hm.get("PART_CODE");
	 		OutputStream os = null;
	        try
	        {
	        	List<HeaderBean> header = new ArrayList<HeaderBean>();
	        	HeaderBean hBean = null;
	        	
		    	hBean = new HeaderBean();
	    		hBean.setName("SALE_NO");
	    		hBean.setTitle("实销单号");
	    		header.add(hBean);
	    		
	    		hBean = new HeaderBean();
	    		hBean.setName("CUSTOMER_NAME");
	    		hBean.setTitle("客户名称");
	    		header.add(hBean);
	    		
	    		hBean = new HeaderBean();
	    		hBean.setName("LINK_PHONE");
	    		hBean.setTitle("联系电话");
	    		header.add(hBean);
	    		
	    		hBean = new HeaderBean();
	    		hBean.setName("LINK_ADDR");
	    		hBean.setTitle("联系地址");
	    		header.add(hBean);
	    		
	    		hBean = new HeaderBean();
	    		hBean.setName("SALE_STATUS");
	    		hBean.setTitle("实销状态");
	    		header.add(hBean);
	    		
	    		hBean = new HeaderBean();
	    		hBean.setName("AMOUNT");
	    		hBean.setTitle("实销金额");
	    		header.add(hBean);
	    		
	    		hBean = new HeaderBean();
	    		hBean.setName("OUT_TYPE");
	    		hBean.setTitle("实销类型");
	    		header.add(hBean);
	    		
	    		hBean = new HeaderBean();
	    		hBean.setName("SALE_DATE");
	    		hBean.setTitle("实销日期");
	    		header.add(hBean);
	    		
	    		QuerySet qs = dao.queryDownInfo(conditions, user, partCode);
	    		os = response.getHttpResponse().getOutputStream();
	    		response.getHttpResponse().reset();
	    		ExportManager.exportFile(response.getHttpResponse(), "实销出库查询", header, qs);
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
