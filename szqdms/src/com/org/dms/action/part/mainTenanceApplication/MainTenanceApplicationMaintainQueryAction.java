package com.org.dms.action.part.mainTenanceApplication;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.mainTenanceApplication.MainTenanceApplicationMaintainQueryDao;
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
 * 
 * @ClassName: MainTenanceApplicationMaintainQueryAction
 * @Description: 待维护配件查询
 * @author: 付啸
 * @date: 2014年12月8日 上午10:54:24
 */
public class MainTenanceApplicationMaintainQueryAction {

	private Logger logger = com.org.framework.log.log
			.getLogger("MainTenanceApplicationMaintainQueryAction");
	// 上下文对象
	private ActionContext atx = ActionContext.getContext();
	// 定义dao对象
	private MainTenanceApplicationMaintainQueryDao dao = MainTenanceApplicationMaintainQueryDao
			.getInstance(atx);

	// 申请查询
	public void maintainQuery() throws Exception {
		RequestWrapper request = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request, page);
		try {
			BaseResultSet bs = dao.queryApplicationList(page, conditions, user);
			atx.setOutData("bs", bs);
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}

	/**
	 * 
	 * @Title: exportExcel
	 * @Description: 导出
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
			hBean.setName("APPLICATION_NO");
			hBean.setTitle("申请单号");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("APPLICATION_TIME");
			hBean.setTitle("申请时间");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("PART_NO");
			hBean.setTitle("配件代码");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("PART_NAME");
			hBean.setTitle("配件名称");
			hBean.setWidth(50);
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("VIN");
			hBean.setTitle("车辆识别码");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("PROCESS_ROUTE");
			hBean.setTitle("工艺路线");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("OWN_FIRST_LEVEL");
			hBean.setTitle("所属一级");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("OWN_SECOND_LEVEL");
			hBean.setTitle("所属二级");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("SUPPLIER_NAME");
			hBean.setTitle("供应商名称");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("ENGINEERING_DEPARTMENT_REMARK");
			hBean.setTitle("技术科审核备注");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("PURCHASING_DEPARTMENT_REMARK");
			hBean.setTitle("采供科审核备注");
			header.add(hBean);
			
			QuerySet qs = dao.queryDownInfo(conditions, user);
			os = response.getHttpResponse().getOutputStream();
			response.getHttpResponse().reset();
			ExportManager.exportFile(response.getHttpResponse(), "待维护配件信息",header, qs);
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
