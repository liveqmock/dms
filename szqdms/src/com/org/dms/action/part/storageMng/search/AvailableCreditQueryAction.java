package com.org.dms.action.part.storageMng.search;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.storageMng.search.AvailableCreditQueryDao;
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
 * @ClassName: AvailableCreditQueryAction
 * @Description: 可用额度查询
 * @author: fuxiao
 * @date: 2014年12月23日 下午5:51:12
 */
public class AvailableCreditQueryAction {
	// 日志类
	private Logger logger = com.org.framework.log.log
			.getLogger("AvailableCreditQueryAction");
	
	// 上下文对象
	private ActionContext atx = ActionContext.getContext();
	
	// 定义dao对象
	private AvailableCreditQueryDao dao = AvailableCreditQueryDao
			.getInstance(atx);

	/**
	 * 
	 * @Title: queryListInfo
	 * @Description: 表单查询
	 * @return: void
	 */
	public void queryListInfo() {
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			PageManager pageManager = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(requestWrapper,
					pageManager);
			atx.setOutData("bs", this.dao.queryList(pageManager, conditions));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}

	/**
	 * 
	 * @Title: exportExcel
	 * @Description: 明细导出
	 * @throws Exception
	 * @return: void
	 */
	public void exportExcel() throws Exception {
		ResponseWrapper response = atx.getResponse();
		RequestWrapper requestWrapper = atx.getRequest();
		PageManager page = new PageManager();
		String conditions = RequestUtil
				.getConditionsWhere(requestWrapper, page);
		OutputStream os = null;
		try {
			List<HeaderBean> header = new ArrayList<HeaderBean>();
			HeaderBean hBean = null;

			hBean = new HeaderBean();
			hBean.setName("ORG_CODE");
			hBean.setTitle("渠道编码");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("ORG_NAME");
			hBean.setTitle("渠道商名称");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("LINE_OF_CREDIT");
			hBean.setTitle("授信额度");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("BEGIN_AMOUNT");
			hBean.setTitle("期初结转");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("CLOSE_ORDER_AMOUNT");
			hBean.setTitle("已关闭订单");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("NOT_CLOSE_ORDER_AMOUNT");
			hBean.setTitle("未关闭订单");
			header.add(hBean);
			
//			hBean = new HeaderBean();
//			hBean.setName("RETURN_ORDER_AMOUNT");
//			hBean.setTitle("退单费用");
//			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("BACK_AMOUNT");
			hBean.setTitle("回款合计");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("RECEIVABLES");
			hBean.setTitle("应收账款");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("AVAILABLE_CREDIT");
			hBean.setTitle("可用额度");
			header.add(hBean);

			QuerySet qs = dao.queryDownInfo(conditions);
			os = response.getHttpResponse().getOutputStream();
			response.getHttpResponse().reset();
			ExportManager.exportFile(response.getHttpResponse(), "可用额度查询",
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
	
	public void searchListInfo() {
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			PageManager pageManager = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(requestWrapper,
					pageManager);
			atx.setOutData("bs", this.dao.searchList(pageManager, conditions));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
}
