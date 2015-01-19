package com.org.dms.action.part.salesMng.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.salesMng.search.DealerOilSaleInfoSearchMngDao;
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

public class DealerOilSaleInfoSearchMngAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "DealerOilSaleInfoSearchMngAction");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private DealerOilSaleInfoSearchMngDao dao = DealerOilSaleInfoSearchMngDao.getInstance(atx);
	    
	    public void saleSearch() throws Exception {

	        try {
	        	RequestWrapper request = atx.getRequest();
		        User user = (User) atx.getSession().get(Globals.USER_KEY);
		        PageManager page = new PageManager();
		        String conditions = RequestUtil.getConditionsWhere(request,page);
	            Map<String, String> hm = RequestUtil.getValues(request);
	            String ORG_CODE = hm.get("ORG_CODE");
	            String BEGIN_DATE = hm.get("BEGIN_DATE");
	            String END_DATE = hm.get("END_DATE");
	            // BaseResultSet：结果集封装对象
	            BaseResultSet baseResultSet = dao.saleSearch(page, user, conditions,ORG_CODE,BEGIN_DATE,END_DATE);
	            // 输出结果集，第一个参数”bs”为固定名称，不可修改
	            atx.setOutData("bs", baseResultSet);
	        } catch (Exception e) {
	            logger.error(e);
	            atx.setException(e);
	        }
	    }
	    //saleDownload
	    public void saleDownload()throws Exception{
			//获取封装后的request对象
			RequestWrapper request = atx.getRequest();
			ResponseWrapper response= atx.getResponse();
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			try
			{
				List<HeaderBean> header = new ArrayList<HeaderBean>();
				HeaderBean hBean = null;
				hBean = new HeaderBean();
				hBean.setName("ORG_CODE");
				hBean.setTitle("渠道商代码");
				header.add(0,hBean);
				hBean = new HeaderBean();
				hBean.setName("ONAME");
				hBean.setTitle("渠道商名称");
				header.add(1,hBean); 
				hBean = new HeaderBean();
				hBean.setName("AMOUNT");
				hBean.setTitle("金额");
				header.add(2,hBean);
				hBean = new HeaderBean();
				hBean.setName("BEGIN_DATE");
				hBean.setTitle("开始时间");
				header.add(3,hBean);
				hBean = new HeaderBean();
				hBean.setName("END_DATE");
				hBean.setTitle("结束时间");
				header.add(4,hBean);
				Map<String, String> hm = RequestUtil.getValues(request);
	            String ORG_CODE = hm.get("ORG_CODE");
	            String BEGIN_DATE = hm.get("BEGIN_DATE");
	            String END_DATE = hm.get("END_DATE");
	            
	            // begin 2015-01-05 添加渠道类别查询条件 by fuxiao 
				// QuerySet qs = dao.saleDownload(ORG_CODE,BEGIN_DATE,END_DATE);
				QuerySet qs = dao.saleDownload(conditions, ORG_CODE,BEGIN_DATE,END_DATE);
				// end 
				
				ExportManager.exportFile(response.getHttpResponse(), "渠道油品采购统计", header, qs);
			}
			catch (Exception e)
			{
				atx.setException(e);
				logger.error(e);
			}
		}
	    public void storageSearch() throws Exception {

	        try {
	        	RequestWrapper request = atx.getRequest();
		        User user = (User) atx.getSession().get(Globals.USER_KEY);
		        PageManager page = new PageManager();
		        String conditions = RequestUtil.getConditionsWhere(request,page);
	            // BaseResultSet：结果集封装对象
	            BaseResultSet baseResultSet = dao.storageSearch(page, user, conditions);
	            // 输出结果集，第一个参数”bs”为固定名称，不可修改
	            atx.setOutData("bs", baseResultSet);
	        } catch (Exception e) {
	            logger.error(e);
	            atx.setException(e);
	        }
	    }
	    //storageDownload
	    public void storageDownload()throws Exception{
			//获取封装后的request对象
			RequestWrapper request = atx.getRequest();
			ResponseWrapper response= atx.getResponse();
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			try
			{
				List<HeaderBean> header = new ArrayList<HeaderBean>();
				HeaderBean hBean = null;
				hBean = new HeaderBean();
				hBean.setName("ORG_CODE");
				hBean.setTitle("渠道商代码");
				header.add(0,hBean);
				hBean = new HeaderBean();
				hBean.setName("ONAME");
				hBean.setTitle("渠道商名称");
				header.add(1,hBean); 
				hBean = new HeaderBean();
				hBean.setName("PART_CODE");
				hBean.setTitle("配件代码");
				header.add(2,hBean);
				hBean = new HeaderBean();
				hBean.setName("PART_NAME");
				hBean.setTitle("配件名称");
				hBean.setWidth(50);
				header.add(3,hBean);
				hBean = new HeaderBean();
				hBean.setName("AMOUNT");
				hBean.setTitle("库存金额");
				header.add(4,hBean);
				QuerySet qs = dao.storageDownload(conditions);
				ExportManager.exportFile(response.getHttpResponse(), "渠道油品库存统计", header, qs);
			}
			catch (Exception e)
			{
				atx.setException(e);
				logger.error(e);
			}
		}
}
