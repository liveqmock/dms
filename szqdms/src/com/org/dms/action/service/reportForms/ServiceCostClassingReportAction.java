package com.org.dms.action.service.reportForms;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.service.reportForms.ServiceCostClassingReportDao;
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

public class ServiceCostClassingReportAction {

	private Logger logger = com.org.framework.log.log
			.getLogger("ServiceCostClassingReportAction");
	private ActionContext atx = ActionContext.getContext();
	private ServiceCostClassingReportDao dao = ServiceCostClassingReportDao.getInstance(atx);
/**
 * 查询数据
 * 
 * @throws Exception
 */
	public void search() throws Exception {
		RequestWrapper request = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request, page);
		conditions=conditions.replaceAll("T.SETTLE_DATE", " TO_char(t.SETTLE_DATE, 'YYYY-MM')");
		try {
		
			BaseResultSet bs = dao.search(page, user, conditions);
			atx.setOutData("bs", bs);
		} catch (Exception e) {
			atx.setException(e);
			logger.error(e);
		}
	}

	/**
	 * 导出表数据
	 * 
	 * @throws Exception
	 */
	public void download() throws Exception {

		// 定义request对象
		ResponseWrapper response = atx.getResponse();
		RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request, page);
		conditions=conditions.replaceAll("T.SETTLE_DATE", " TO_char(t.SETTLE_DATE, 'YYYY-MM')");
		try {
			List<HeaderBean> header = new ArrayList<HeaderBean>();
			HeaderBean hBean = null;
			hBean = new HeaderBean();
			hBean.setName("ROWNUM");
			hBean.setTitle("序号");
			header.add(0, hBean);

			hBean = new HeaderBean();
			hBean.setName("NAME");
			hBean.setTitle("分类");
			header.add(1, hBean);
			hBean = new HeaderBean();
			hBean.setName("A1");
			hBean.setTitle("0万<X≤0.5万");
			header.add(2, hBean);
			hBean = new HeaderBean();
			hBean.setName("A2");
			hBean.setTitle("0.5万< X ≤1万");
			header.add(3, hBean);

			hBean = new HeaderBean();
			hBean.setName("A3");
			hBean.setTitle("1万< X ≤2万");
			header.add(4, hBean);
			hBean = new HeaderBean();
			hBean.setName("A4");
			hBean.setTitle("2万< X ≤4万");
			header.add(5, hBean);
			hBean = new HeaderBean();
			hBean.setName("A5");
			hBean.setTitle("4万< X ≤8万");
			header.add(6, hBean);
			hBean = new HeaderBean();
			hBean.setName("A6");
			hBean.setTitle("8万< X ≤16万");
			header.add(7, hBean);
			hBean = new HeaderBean();
			hBean.setName("A7");
			hBean.setTitle("16万<X ≤32万");
			header.add(8, hBean);
			hBean = new HeaderBean();
			hBean.setName("A8");
			hBean.setTitle("32万< X≤64万");
			header.add(9, hBean);
			hBean = new HeaderBean();
			hBean.setName("A9");
			hBean.setTitle("64万< X");
			header.add(10, hBean);
			hBean = new HeaderBean();
			hBean.setName("A10");
			hBean.setTitle("合计");
			header.add(11, hBean);
			QuerySet querySet = dao.download(conditions);
			ExportManager.exportFile(response.getHttpResponse(), "服务站费用分梯统计表",
					header, querySet);
		} catch (Exception e) {
			atx.setException(e);
			logger.error(e);
		}
	}

}
