package com.org.dms.action.part.basicInfoMng;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.org.dms.common.RowMapUtils;
import com.org.dms.dao.part.basicInfoMng.PartPriceChangeDao;
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
 * @ClassName: PartPriceChangeQueryAction
 * @Description: 配件调价查询
 * @author: fuxiao
 * @date: 2014年12月1日 下午11:06:47
 */
public class PartPriceChangeQueryAction {
	// 日志类
	private Logger logger = com.org.framework.log.log
			.getLogger("PartPriceChangeQueryAction");
	// 上下文对象
	private ActionContext atx = ActionContext.getContext();
	// 定义dao对象
	private PartPriceChangeDao dao = PartPriceChangeDao.getInstance(atx);

	/**
	 * 
	 * @Title: queryListInfo
	 * @Description: 表格查询
	 * @return: void
	 */
	public void queryListInfo() {
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			PageManager pageManager = new PageManager();
			User user = (User) atx.getSession().get(Globals.USER_KEY);
			String conditions = RequestUtil.getConditionsWhere(requestWrapper,
					pageManager);
			atx.setOutData("bs",
					this.dao.queryList(pageManager, conditions, user, true));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}

	/**
	 * 
	 * @Title: queryListInfoSum
	 * @Description: 查询金额汇总
	 * @return: void
	 */
	public void queryListInfoSum() {
		RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		try {
			String conditions = RequestUtil.getConditionsWhere(request, page);
			QuerySet qs = dao.searchPriceReportSum(conditions);
			Map<String, Map<String, String>> resultMap = RowMapUtils.toConvert(qs);					
			JSONObject jsonObj = JSONObject.fromObject(resultMap);
			atx.setOutMsg(jsonObj.toString());
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}

	/**
	 * 
	 * @Title: exportExcel
	 * @Description: 下载
	 * @throws Exception
	 * @return: void
	 */
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
    		hBean.setName("PART_CODE");
    		hBean.setTitle("配件代码");
    		header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("PART_NAME");
            hBean.setTitle("配件名称");
            hBean.setWidth(50);
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("ORIGINAL_PRICE");
            hBean.setTitle("调价前价格");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("NOW_PRICE");
            hBean.setTitle("调价后价格");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("JGCY");
            hBean.setTitle("价差");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("PRICE_TYPE");
            hBean.setTitle("配件价格类型");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("CREATE_USER");
            hBean.setTitle("调价人");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("CREATE_TIME");
            hBean.setTitle("调价时间");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("WAREHOUSE_NAME");
            hBean.setTitle("仓库名称");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("USABLE_STOCK");
            hBean.setTitle("库存数量");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("IN_STRANS_STOCK");
            hBean.setTitle("在途数量");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("CYJE");
            hBean.setTitle("差异金额 ");
            header.add(hBean);
			QuerySet qs = dao.queryDownInfo(conditions, user);
			os = response.getHttpResponse().getOutputStream();
			response.getHttpResponse().reset();
			ExportManager.exportFile(response.getHttpResponse(), "调价记录查询",
					header, qs);
		} catch (Exception e) {
			e.printStackTrace();
			atx.setException(e);
			logger.error(e);
		} finally {
			if (os != null) {
				os.close();
			}
		}
	}
	
	
	/**
	 * 
	 * @Title: exportExcel
	 * @Description: 下载
	 * @throws Exception
	 * @return: void
	 */
	public void exportExcelForDealer() throws Exception {
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
    		hBean.setName("PART_CODE");
    		hBean.setTitle("配件代码");
    		header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("PART_NAME");
            hBean.setTitle("配件名称");
            hBean.setWidth(50);
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("ORIGINAL_PRICE");
            hBean.setTitle("调价前价格");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("NOW_PRICE");
            hBean.setTitle("调价后价格");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("JGCY");
            hBean.setTitle("价差");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("PRICE_TYPE");
            hBean.setTitle("配件价格类型");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("CREATE_TIME");
            hBean.setTitle("调价时间");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("CYJE");
            hBean.setTitle("差异金额 ");
            header.add(hBean);
			QuerySet qs = dao.queryDownInfo(conditions, user);
			os = response.getHttpResponse().getOutputStream();
			response.getHttpResponse().reset();
			ExportManager.exportFile(response.getHttpResponse(), "配件价格变动查询",
					header, qs);
		} catch (Exception e) {
			e.printStackTrace();
			atx.setException(e);
			logger.error(e);
		} finally {
			if (os != null) {
				os.close();
			}
		}
	}
}
