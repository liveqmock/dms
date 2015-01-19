package com.org.dms.action.service.oldpartMng;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.common.PDFReport;
import com.org.dms.dao.service.oldpartMng.OldPartSearchDao;
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
/**
 * 旧件查询ACTION
 * @author zts
 *
 */
public class OldPartSearchAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "OldPartSearchAction");
	private ActionContext atx = ActionContext.getContext();
	private OldPartSearchDao dao = OldPartSearchDao.getInstance(atx);
	/**
     * 回运单查询
     * @throws Exception
     */
    public void oldPartSearch() throws Exception{
    	RequestWrapper request = atx.getRequest();
 		PageManager page = new PageManager();
 		User user = (User) atx.getSession().get(Globals.USER_KEY);
 		String conditions = RequestUtil.getConditionsWhere(request,page);
 		try
 		{
 			BaseResultSet bs = dao.oldPartSearch(page,conditions,user);
 			atx.setOutData("bs", bs);
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }
    /**
     * 回运单查询
     * @throws Exception
     */
    public void supOldPartSearch() throws Exception{
    	RequestWrapper request = atx.getRequest();
    	PageManager page = new PageManager();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	String conditions = RequestUtil.getConditionsWhere(request,page);
    	try
    	{
    		BaseResultSet bs = dao.supOldPartSearch(page,conditions,user);
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    /**
     * 回运单查询(旧件库房)
     * @throws Exception
     */
    public void oldPartMissSearch() throws Exception{
    	RequestWrapper request = atx.getRequest();
    	PageManager page = new PageManager();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	String conditions = RequestUtil.getConditionsWhere(request,page);
    	try
    	{
    		BaseResultSet bs = dao.oldPartMissSearch(page,conditions,user);
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    /**
     *  回运单下载
     * @throws Exception
     */
	public void download()throws Exception{
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
    		hBean.setName("ORDER_NO");
    		hBean.setTitle("回运单号");
    		header.add(0,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("PRODUCE_DATE");
    		hBean.setTitle("旧件产生月份");
    		header.add(1,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("RETURN_DATE");
    		hBean.setTitle("回运日期");
    		header.add(2,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("CLAIM_NO");
    		hBean.setTitle("索赔单号");
    		header.add(3,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("ORG_CODE");
    		hBean.setTitle("渠道商代码");
    		header.add(4,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("ORG_NAME");
    		hBean.setTitle("渠道商名称");
    		header.add(5,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("PART_CODE");
    		hBean.setTitle("配件代码");
    		header.add(6,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("PART_NAME");
    		hBean.setTitle("配件名称");
    		header.add(7,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("PROSUPPLY_CODE");
    		hBean.setTitle("供应商代码");
    		header.add(8,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("PROSUPPLY_NAME");
    		hBean.setTitle("供应商名称");
    		header.add(9,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("SHOULD_COUNT");
    		hBean.setTitle("应返件数");
    		header.add(10,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("OUGHT_COUNT");
    		hBean.setTitle("实返件数");
    		header.add(11,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("MISS_COUNT");
    		hBean.setTitle("缺失件数");
    		header.add(12,hBean);
    		QuerySet qs = dao.download(conditions);
    		ExportManager.exportFile(response.getHttpResponse(), "旧件回运信息", header, qs);
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
	
	public void missPartDownload()throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	ResponseWrapper response= atx.getResponse();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	PageManager page = new PageManager();
 		String conditions = RequestUtil.getConditionsWhere(request,page);
        try
        {
        	List<HeaderBean> header = new ArrayList<HeaderBean>();
        	HeaderBean hBean = null;
        	
        	hBean = new HeaderBean();
    		hBean.setName("CODE");
    		hBean.setTitle("渠道商代码");
    		header.add(0,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("ONAME");
    		hBean.setTitle("渠道商名称");
    		header.add(1,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("PRODUCE_DATE");
    		hBean.setTitle("旧件产生月份");
    		header.add(2,hBean); 
    		
	    	hBean = new HeaderBean();
    		hBean.setName("ORDER_NO");
    		hBean.setTitle("回运单号");
    		header.add(3,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("CLAIM_NO");
    		hBean.setTitle("索赔单号");
    		header.add(4,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("PART_CODE");
    		hBean.setTitle("配件代码");
    		header.add(5,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("PART_NAME");
    		hBean.setTitle("配件名称");
    		header.add(6,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("FAULT_CODE");
    		hBean.setTitle("故障代码");
    		header.add(7,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("PROSUPPLY_CODE");
    		hBean.setTitle("供应商代码");
    		header.add(8,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("PROSUPPLY_NAME");
    		hBean.setTitle("供应商名称");
    		header.add(9,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("SHOULD_COUNT");
    		hBean.setTitle("应返件数");
    		header.add(10,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("OUGHT_COUNT");
    		hBean.setTitle("实返件数");
    		header.add(11,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("MISS_COUNT");
    		hBean.setTitle("缺失件数");
    		header.add(12,hBean);
    		
    		QuerySet qs = dao.missDownload(conditions,user);
    		ExportManager.exportFile(response.getHttpResponse(), "索赔查询", header, qs);
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
	/**
	 *  回运单下载
	 * @throws Exception
	 */
	public void download1()throws Exception{
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
			hBean.setName("ORDER_NO");
			hBean.setTitle("回运单号");
			header.add(0,hBean);
			hBean = new HeaderBean();
			hBean.setName("PRODUCE_DATE");
			hBean.setTitle("旧件产生月份");
			header.add(1,hBean);
			hBean = new HeaderBean();
			hBean.setName("RETURN_DATE");
			hBean.setTitle("回运日期");
			header.add(2,hBean);
			hBean = new HeaderBean();
			hBean.setName("CLAIM_NO");
			hBean.setTitle("索赔单号");
			header.add(3,hBean);
			hBean = new HeaderBean();
			hBean.setName("ORG_CODE");
			hBean.setTitle("渠道商代码");
			header.add(4,hBean);
			hBean = new HeaderBean();
			hBean.setName("ORG_NAME");
			hBean.setTitle("渠道商名称");
			header.add(5,hBean);
			hBean = new HeaderBean();
			hBean.setName("PART_CODE");
			hBean.setTitle("配件代码");
			header.add(6,hBean);
			hBean = new HeaderBean();
			hBean.setName("PART_NAME");
			hBean.setTitle("配件名称");
			header.add(7,hBean);
			hBean = new HeaderBean();
			hBean.setName("PROSUPPLY_CODE");
			hBean.setTitle("供应商代码");
			header.add(8,hBean);
			hBean = new HeaderBean();
			hBean.setName("PROSUPPLY_NAME");
			hBean.setTitle("供应商名称");
			header.add(9,hBean);
			hBean = new HeaderBean();
			hBean.setName("OUGHT_COUNT");
			hBean.setTitle("件数");
			header.add(10,hBean);
			QuerySet qs = dao.download1(conditions);
			ExportManager.exportFile(response.getHttpResponse(), "旧件回运信息", header, qs);
		}
		catch (Exception e)
		{
			atx.setException(e);
			logger.error(e);
		}
	}
	/**
	 *  回运单下载
	 * @throws Exception
	 */
	public void supDownload()throws Exception{
		//获取封装后的request对象
		RequestWrapper request = atx.getRequest();
		ResponseWrapper response= atx.getResponse();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		OutputStream os = null;
		ByteArrayOutputStream ba =new ByteArrayOutputStream();
		try
		{
			List<HeaderBean> header = new ArrayList<HeaderBean>();
			HeaderBean hBean = null;
			hBean = new HeaderBean();
			hBean.setName("CLAIM_NO");
			hBean.setTitle("索赔单号");
			header.add(0,hBean);
			hBean = new HeaderBean();
			hBean.setName("PRODUCE_DATE");
			hBean.setTitle("旧件产生月份");
			header.add(1,hBean);
			hBean = new HeaderBean();
			hBean.setName("PART_CODE");
			hBean.setTitle("配件代码");
			header.add(2,hBean);
			hBean = new HeaderBean();
			hBean.setName("PART_NAME");
			hBean.setTitle("配件名称");
			header.add(3,hBean);
			hBean = new HeaderBean();
			hBean.setName("IS_MAIN");
			hBean.setTitle("是否主损件");
			header.add(4,hBean);
			hBean = new HeaderBean();
			hBean.setName("OUGHT_COUNT");
			hBean.setTitle("实返件数");
			header.add(5,hBean);
			hBean = new HeaderBean();
			hBean.setName("MISS_COUNT");
			hBean.setTitle("未回运件数");
			header.add(6,hBean);
			hBean = new HeaderBean();
			hBean.setName("OLD_PART_STATUS");
			hBean.setTitle("旧件状态");
			header.add(7,hBean);
			hBean = new HeaderBean();
			hBean.setName("WAREHOUSE_CODE");
			hBean.setTitle("仓库代码");
			header.add(8,hBean);
			hBean = new HeaderBean();
			hBean.setName("WAREHOUSE_NAME");
			hBean.setTitle("仓库名称");
			header.add(9,hBean);
			QuerySet qs = dao.supDownload(conditions,user);
			ExportManager.exportFile(response.getHttpResponse(), "供应商旧件查询", header, qs);
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
