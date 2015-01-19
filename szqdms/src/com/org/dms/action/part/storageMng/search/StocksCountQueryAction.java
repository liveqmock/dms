package com.org.dms.action.part.storageMng.search;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.storageMng.search.StocksCountQueryDao;
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
 * ClassName: StocksCountQueryAction 
 * Function: 本部库存查询
 * date: 2014年11月2日 下午2:10:13
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class StocksCountQueryAction {
	
	// 日志类
	private Logger logger = com.org.framework.log.log.getLogger("StocksCountQueryAction");
	
	// 上下文对象
	private ActionContext atx = ActionContext.getContext();
	
	// 定义dao对象
	private StocksCountQueryDao dao = StocksCountQueryDao.getInstance(atx);

	/**
	 * 
	 * queryListInfo:前台表单查询
	 * @author fuxiao
	 * Date:2014年10月24日
	 *
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
	 * @Title: queryListInfoAndPrice
	 * @Description: 包含价格
	 * @return: void
	 */
	public void queryListInfoAndPrice() {
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			PageManager pageManager = new PageManager();
			User user = (User) atx.getSession().get(Globals.USER_KEY);
			String conditions = RequestUtil.getConditionsWhere(requestWrapper,pageManager);
			atx.setOutData("bs", this.dao.queryListAndPrice(pageManager, conditions, user));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}

	
	/**
	 * 
	 * exportExcel: Excel导出
	 * @author fuxiao
	 * Date:2014年10月24日
	 *
	 */
	public void exportExcel() throws Exception{
		ResponseWrapper response= atx.getResponse();
    	RequestWrapper requestWrapper = atx.getRequest();
    	PageManager page = new PageManager();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
 		String conditions = RequestUtil.getConditionsWhere(requestWrapper,page);
 		OutputStream os = null;
        try
        {
        	List<HeaderBean> header = new ArrayList<HeaderBean>();
        	HeaderBean hBean = null;
    		
	    	hBean = new HeaderBean();
    		hBean.setName("PART_CODE");
    		hBean.setTitle("配件编号");
    		header.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("PART_NAME");
    		hBean.setTitle("配件名称");
    		hBean.setWidth(50);
    		header.add(hBean);

    		hBean = new HeaderBean();
    		hBean.setName("AMOUNT");
    		hBean.setTitle("总库存");
    		header.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("OCCUPY_AMOUNT");
    		hBean.setTitle("占用库存");
    		header.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("AVAILABLE_AMOUNT");
    		hBean.setTitle("可用库存");
    		header.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("SUPPLIER_NAME");
    		hBean.setTitle("供应商");
    		header.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("USER_NAME");
    		hBean.setTitle("库管员");
    		header.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("WAREHOUSE_NAME");
    		hBean.setTitle("仓库");
    		header.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("AREA_NAME");
    		hBean.setTitle("库区");
    		header.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("POSITION_NAME");
    		hBean.setTitle("货位");
    		header.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("POSITION_CODE");
    		hBean.setTitle("货位代码");
    		header.add(hBean);
    		
    		QuerySet qs = dao.queryDownInfo(conditions, user);
    		os = response.getHttpResponse().getOutputStream();
    		response.getHttpResponse().reset();
    		ExportManager.exportFile(response.getHttpResponse(), "本部库存查询", header, qs);
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
	
	/**
	 * 
	 * @Title: exportExcelAndPrice
	 * @Description: 包含价格
	 * @throws Exception
	 * @return: void
	 */
	public void exportExcelAndPrice() throws Exception{
		ResponseWrapper response= atx.getResponse();
    	RequestWrapper requestWrapper = atx.getRequest();
    	PageManager page = new PageManager();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
 		String conditions = RequestUtil.getConditionsWhere(requestWrapper,page);
 		OutputStream os = null;
        try
        {
        	List<HeaderBean> header = new ArrayList<HeaderBean>();
        	HeaderBean hBean = null;
    		
	    	hBean = new HeaderBean();
    		hBean.setName("PART_CODE");
    		hBean.setTitle("配件编号");
    		header.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("PART_NAME");
    		hBean.setTitle("配件名称");
    		hBean.setWidth(50);
    		header.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("PLAN_PRICE");
    		hBean.setTitle("计划价");
    		header.add(hBean);

    		hBean = new HeaderBean();
    		hBean.setName("AMOUNT");
    		hBean.setTitle("总库存");
    		header.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("OCCUPY_AMOUNT");
    		hBean.setTitle("占用库存");
    		header.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("AVAILABLE_AMOUNT");
    		hBean.setTitle("可用库存");
    		header.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("SUPPLIER_NAME");
    		hBean.setTitle("供应商");
    		header.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("USER_NAME");
    		hBean.setTitle("库管员");
    		header.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("WAREHOUSE_NAME");
    		hBean.setTitle("仓库");
    		header.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("AREA_NAME");
    		hBean.setTitle("库区");
    		header.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("POSITION_NAME");
    		hBean.setTitle("货位");
    		header.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("POSITION_CODE");
    		hBean.setTitle("货位代码");
    		header.add(hBean);
    		
    		QuerySet qs = dao.queryDownInfoAndPrice(conditions, user);
    		os = response.getHttpResponse().getOutputStream();
    		response.getHttpResponse().reset();
    		ExportManager.exportFile(response.getHttpResponse(), "本部库存查询", header, qs);
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
