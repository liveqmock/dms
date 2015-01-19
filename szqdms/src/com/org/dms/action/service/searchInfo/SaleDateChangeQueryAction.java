package com.org.dms.action.service.searchInfo;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.searchInfo.SaleDateChangeQueryDao;
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
 * @ClassName: SaleDateChangeQueryAction
 * @Description: 销售日期变更查询
 * @author: fuxiao
 * @date: 2015年1月5日 下午7:28:55
 */
public class SaleDateChangeQueryAction {

	// 日志类
	private Logger logger = com.org.framework.log.log
			.getLogger("SaleDateChangeQueryAction");
	// 上下文对象
	private ActionContext atx = ActionContext.getContext();
	// 定义dao对象
	private SaleDateChangeQueryDao dao = SaleDateChangeQueryDao
			.getInstance(atx);

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
			String conditions = RequestUtil.getConditionsWhere(requestWrapper,
					pageManager);
			conditions = this.conditionsFilter(requestWrapper, conditions);
			atx.setOutData("bs", this.dao.queryList(pageManager, conditions));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
	
	
	/**
	 * 
	 * @Title: conditionsFilter
	 * @Description: 查询条件过滤
	 * @param requestWrapper
	 * @param conditions
	 * @return
	 * @return: String
	 */
	public String conditionsFilter(RequestWrapper requestWrapper, String conditions){
		Map<String, String> hm = RequestUtil.getValues(requestWrapper);
		String sfStatus = hm.get("SF");
		if(sfStatus != null && !"".equals(sfStatus)){
			if(DicConstant.SF_01.equals(sfStatus)){
				conditions += " AND OLD_SDATE IS NULL ";
			} else {
				conditions += " AND OLD_SDATE IS NOT NULL";
			}
		}
		return conditions;
	}

	/**
	 * 
	 * @Title: exportExcel
	 * @Description: Excel数据导出
	 * @throws Exception
	 * @return: void
	 */
	public void exportExcel() throws Exception {
		ResponseWrapper response = atx.getResponse();
		RequestWrapper requestWrapper = atx.getRequest();
		PageManager page = new PageManager();
		String conditions = RequestUtil
				.getConditionsWhere(requestWrapper, page);
		conditions = this.conditionsFilter(requestWrapper, conditions);
		OutputStream os = null;
		try {
			List<HeaderBean> header = new ArrayList<HeaderBean>();
			HeaderBean hBean = null;
			hBean = new HeaderBean();
			hBean.setName("VIN");
			hBean.setTitle("VIN");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("MODELS_CODE");
			hBean.setTitle("车型代码");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("SALE_STATUS");
			hBean.setTitle("车辆状态");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("OLD_SDATE");
			hBean.setTitle("原销售日期");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("NEW_SDATE");
			hBean.setTitle("申请销售日期");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("APPLY_REASON");
			hBean.setTitle("申请原因");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("APPLY_COMPANY");
			hBean.setTitle("申请单位");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("APPLY_DATE");
			hBean.setTitle("申请时间");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("CHECK_REMARKS");
			hBean.setTitle("审批结果");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("CHECK_USER");
			hBean.setTitle("审批人");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("CHECK_DATE");
			hBean.setTitle("审批时间");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("CHECK_STATUS");
			hBean.setTitle("审批状态");
			header.add(hBean);
			
			QuerySet qs = dao.queryDownInfo(conditions);
			os = response.getHttpResponse().getOutputStream();
			response.getHttpResponse().reset();
			ExportManager.exportFile(response.getHttpResponse(), "销售日期更改查询",
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
