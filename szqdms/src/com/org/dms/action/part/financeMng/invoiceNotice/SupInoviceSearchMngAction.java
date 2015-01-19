package com.org.dms.action.part.financeMng.invoiceNotice;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.financeMng.invoiceNotice.SupInoviceSearchMngDao;
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

public class SupInoviceSearchMngAction {

	private Logger logger = com.org.framework.log.log.getLogger(
	        "Logger");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private SupInoviceSearchMngDao dao = SupInoviceSearchMngDao.getInstance(atx);
	
	
	public void invioceSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		
		try
		{
			BaseResultSet bs = dao.invioceSearch(page,user,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
	//supplierInvioceSearch
	public void supplierInvioceSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		
		try
		{
			BaseResultSet bs = dao.supplierInvioceSearch(page,user,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
	public void saleInvioceSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		
		try
		{
			BaseResultSet bs = dao.saleInvioceSearch(page,user,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
	//dealerSaleInvioceSearch
	public void dealerSaleInvioceSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		
		try
		{
			BaseResultSet bs = dao.dealerSaleInvioceSearch(page,user,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
	public void exportExcel() throws Exception {
		ResponseWrapper response = atx.getResponse();
		RequestWrapper requestWrapper = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil
				.getConditionsWhere(requestWrapper, page);
		OutputStream os = null;
		try {
			List<HeaderBean> header = new ArrayList<HeaderBean>();
			HeaderBean hBean = null;
			hBean = new HeaderBean();

			hBean.setName("SUPPLIER_CODE");
			hBean.setTitle("供应商编码");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("SUPPLIER_NAME");
			hBean.setTitle("供应商名称");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("INVOICE_MONTH");
			hBean.setTitle("结算月份");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("ORDER_NO");
			hBean.setTitle("订单号");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("PURCHASE_AMOUNT");
			hBean.setTitle("金额");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("INVOICE_NO");
			hBean.setTitle("发票号");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("APPLY_USER");
			hBean.setTitle("开票通知人");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("INVOICE_DATE");
			hBean.setTitle("开票时间");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("INVOICE_STATUS");
			hBean.setTitle("开票状态");
			header.add(hBean);
			
			QuerySet qs = dao.download(conditions, user);
			os = response.getHttpResponse().getOutputStream();
			response.getHttpResponse().reset();
			ExportManager.exportFile(response.getHttpResponse(), "供应商开票信息",
					header, qs);
		} catch (Exception e) {
			atx.setException(e);
			logger.error(e);
		} finally {
			if (os != null) {
				os.close();
			}
		}
	}
	public void supExportExcel() throws Exception {
		ResponseWrapper response = atx.getResponse();
		RequestWrapper requestWrapper = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil
				.getConditionsWhere(requestWrapper, page);
		OutputStream os = null;
		try {
			List<HeaderBean> header = new ArrayList<HeaderBean>();
			HeaderBean hBean = null;
			hBean = new HeaderBean();

			hBean.setName("SUPPLIER_CODE");
			hBean.setTitle("供应商编码");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("SUPPLIER_NAME");
			hBean.setTitle("供应商名称");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("INVOICE_MONTH");
			hBean.setTitle("结算月份");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("ORDER_NO");
			hBean.setTitle("订单号");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("PURCHASE_AMOUNT");
			hBean.setTitle("金额");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("INVOICE_NO");
			hBean.setTitle("发票号");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("APPLY_USER");
			hBean.setTitle("开票通知人");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("INVOICE_DATE");
			hBean.setTitle("开票时间");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("INVOICE_STATUS");
			hBean.setTitle("开票状态");
			header.add(hBean);
			
			QuerySet qs = dao.supExportExcel(conditions, user);
			os = response.getHttpResponse().getOutputStream();
			response.getHttpResponse().reset();
			ExportManager.exportFile(response.getHttpResponse(), "供应商开票信息",
					header, qs);
		} catch (Exception e) {
			atx.setException(e);
			logger.error(e);
		} finally {
			if (os != null) {
				os.close();
			}
		}
	}
	public void saleExportExcel() throws Exception {
		ResponseWrapper response = atx.getResponse();
		RequestWrapper requestWrapper = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil
				.getConditionsWhere(requestWrapper, page);
		OutputStream os = null;
		try {
			List<HeaderBean> header = new ArrayList<HeaderBean>();
			HeaderBean hBean = null;
			hBean = new HeaderBean();

			hBean.setName("ORG_CODE");
			hBean.setTitle("渠道商编码");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("ORG_NAME");
			hBean.setTitle("渠道商名称");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("INVOICE_MONTH");
			hBean.setTitle("结算月份");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("ORDER_NO");
			hBean.setTitle("订单号");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("REAL_AMOUNT");
			hBean.setTitle("实发金额");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("INVOICE_NO");
			hBean.setTitle("发票号");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("INVOICE_DATE");
			hBean.setTitle("开票时间");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("INVOICE_STATUS");
			hBean.setTitle("开票状态");
			header.add(hBean);
			
			QuerySet qs = dao.saleExportExcel(conditions, user);
			os = response.getHttpResponse().getOutputStream();
			response.getHttpResponse().reset();
			ExportManager.exportFile(response.getHttpResponse(), "渠道商开票信息",
					header, qs);
		} catch (Exception e) {
			atx.setException(e);
			logger.error(e);
		} finally {
			if (os != null) {
				os.close();
			}
		}
	}
	
	public void dealerExportExcel() throws Exception {
		ResponseWrapper response = atx.getResponse();
		RequestWrapper requestWrapper = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil
				.getConditionsWhere(requestWrapper, page);
		OutputStream os = null;
		try {
			List<HeaderBean> header = new ArrayList<HeaderBean>();
			HeaderBean hBean = null;
			hBean = new HeaderBean();

			hBean.setName("ORG_CODE");
			hBean.setTitle("渠道商编码");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("ORG_NAME");
			hBean.setTitle("渠道商名称");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("INVOICE_MONTH");
			hBean.setTitle("结算月份");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("ORDER_NO");
			hBean.setTitle("订单号");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("REAL_AMOUNT");
			hBean.setTitle("实发金额");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("INVOICE_NO");
			hBean.setTitle("发票号");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("INVOICE_DATE");
			hBean.setTitle("开票时间");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("INVOICE_STATUS");
			hBean.setTitle("开票状态");
			header.add(hBean);
			
			QuerySet qs = dao.dealerExportExcel(conditions, user);
			os = response.getHttpResponse().getOutputStream();
			response.getHttpResponse().reset();
			ExportManager.exportFile(response.getHttpResponse(), "渠道商开票信息",
					header, qs);
		} catch (Exception e) {
			atx.setException(e);
			logger.error(e);
		} finally {
			if (os != null) {
				os.close();
			}
		}
	}
}
