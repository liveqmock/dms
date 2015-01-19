package com.org.dms.action.service.reportForms;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.service.reportForms.ClaimReportDao;
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

public class ClaimReportAction {
	private Logger logger = com.org.framework.log.log
			.getLogger("ClaimReportAction");
	private ActionContext atx = ActionContext.getContext();
	private ClaimReportDao dao = ClaimReportDao.getInstance(atx);
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
		try {
			List<HeaderBean> header = new ArrayList<HeaderBean>();
			HeaderBean hBean = null;
			hBean = new HeaderBean();
			hBean.setName("ROWNUM");
			hBean.setTitle("序号");
			header.add(0, hBean);

			hBean = new HeaderBean();
			hBean.setName("CLAIM_NO");
			hBean.setTitle("索赔单号");
			header.add(1, hBean);
			hBean = new HeaderBean();
			hBean.setName("VIN");
			hBean.setTitle("底盘号");
			header.add(2, hBean);
			hBean = new HeaderBean();
			hBean.setName("MODELS_CODE");
			hBean.setTitle("车辆型号");
			header.add(3, hBean);

			hBean = new HeaderBean();
			hBean.setName("ENGINE_NO");
			hBean.setTitle("发动机号");
			header.add(4, hBean);
			hBean = new HeaderBean();
			hBean.setName("VEHICLE_SUPP");
			hBean.setTitle("生成单位");
			header.add(5, hBean);
			hBean = new HeaderBean();
			hBean.setName("BUY_DATE");
			hBean.setTitle("购车日期");
			header.add(6, hBean);
			hBean = new HeaderBean();
			hBean.setName("F_FAULT_DATE");
			hBean.setTitle("首次故障日期");
			header.add(7, hBean);
			hBean = new HeaderBean();
			hBean.setName("FAULT_DATE");
			hBean.setTitle("故障日期");
			header.add(8, hBean);
			hBean = new HeaderBean();
			hBean.setName("F_MILEAGE");
			hBean.setTitle("首次故障里程");
			header.add(9, hBean);
			hBean = new HeaderBean();
			hBean.setName("T_MILEAGE");
			hBean.setTitle("故障里程");
			header.add(10, hBean);
			hBean = new HeaderBean();
			hBean.setName("CODE");
			hBean.setTitle("服务站代码");
			header.add(11, hBean);
			hBean = new HeaderBean();
			hBean.setName("ONAME");
			hBean.setTitle("服务站名称");
			header.add(12, hBean);
			hBean = new HeaderBean();
			hBean.setName("USER_NAME");
			hBean.setTitle("用户姓名");
			header.add(13, hBean);
			hBean = new HeaderBean();
			hBean.setName("USER_ADDRESS");
			hBean.setTitle("用户地址");
			header.add(14, hBean);
			hBean = new HeaderBean();
			hBean.setName("PHONE");
			hBean.setTitle("联系电话");
			header.add(15, hBean);
			hBean = new HeaderBean();
			hBean.setName("MILEAGE");
			hBean.setTitle("强保里程");
			header.add(16, hBean);
			hBean = new HeaderBean();
			hBean.setName("FAULT_CODE");
			hBean.setTitle("故障编号");
			header.add(17, hBean);
			hBean = new HeaderBean();
			hBean.setName("FAULT_NAME");
			hBean.setTitle("故障名称");
			header.add(18, hBean);
			hBean = new HeaderBean();
			hBean.setName("OLD_PART_CODE");
			hBean.setTitle("故障件编号");
			header.add(19, hBean);
			hBean = new HeaderBean();
			hBean.setName("OLD_PART_NAME");
			hBean.setTitle("故障件名称");
			header.add(20, hBean);
			hBean = new HeaderBean();
			hBean.setName("OLD_SUPPLIER");
			hBean.setTitle("故障件生成厂家");
			header.add(21, hBean);
			hBean = new HeaderBean();
			hBean.setName("WORK_COSTS");
			hBean.setTitle("工时费");
			header.add(22, hBean);
			hBean = new HeaderBean();
			hBean.setName("CLAIM_COSTS");
			hBean.setTitle("材料费");
			header.add(23, hBean);
			hBean = new HeaderBean();
			hBean.setName("SEVEH_COSTS");
			hBean.setTitle("出车费");
			header.add(24, hBean);
			hBean = new HeaderBean();
			hBean.setName("D_MILEAGE");
			hBean.setTitle("出车里程");
			header.add(25, hBean);
			QuerySet querySet = dao.download(conditions);
			ExportManager.exportFile(response.getHttpResponse(), "索赔报单信息细表",
					header, querySet);
		} catch (Exception e) {
			atx.setException(e);
			logger.error(e);
		}
	}
}
