package com.org.dms.action.part.basicInfoMng;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.basicInfoMng.DealerReceivablesDao;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.fileimport.ExportManager;
import com.org.framework.fileimport.HeaderBean;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;

/**
 * 
 * @ClassName: DealerReceivablesAction
 * @Description: 经销商应收账款统计Action
 * @author: fuxiao
 * @date: 2014年12月24日 下午6:23:24
 */
public class DealerReceivablesAction {
	
	private Logger logger = com.org.framework.log.log
			.getLogger("DealerReceivablesAction");
	
	private ActionContext atx = ActionContext.getContext();
	
	private DealerReceivablesDao dao = DealerReceivablesDao.getInstance(atx);

	/**
	 * 
	 * @Title: receivablesDay
	 * @Description: 结算日期确定，并生成应收账款统计数据
	 * @throws Exception
	 * @return: void
	 */
	public void receivablesDay() throws Exception {
		RequestWrapper request = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		String dayId = Pub.val(request, "dayId");
		try {
			dao.receivablesDay(dayId, user.getAccount());		// 改变结算日期状态
			dao.insertReceivableData(dayId, user.getAccount()); // 生成应收账款统计数据
		} catch (Exception e) {
			e.printStackTrace();
			atx.setException(e);
			logger.error(e);
		}
	}
	
	/**
	 * 
	 * @Title: searchInfo
	 * @Description: 应收账款统计查询
	 * @throws Exception
	 * @return: void
	 */
	public void queryListInfo() throws Exception {
		RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request, page);
		conditions = this.filterConditions(conditions, request);
		try {
			BaseResultSet bs = dao.queryList(page, conditions);
			atx.setOutData("bs", bs);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
	
	/**
	 * 
	 * @Title: filterConditions
	 * @Description: 查询条件转换
	 * @param conditions
	 * @param request
	 * @return
	 * @return: String
	 */
	public String filterConditions(String conditions, RequestWrapper request){
		Map<String, String> map = RequestUtil.getValues(request);
		String beginDate = map.get("RECEIVABLES_DATE_B") != null ? map.get("RECEIVABLES_DATE_B") : "";
		String endDae = map.get("RECEIVABLES_DATE_E") != null ? map.get("RECEIVABLES_DATE_E") : "";
		if(!"".equals(beginDate)){
			conditions += "AND TRUNC(RECEIVABLES_DATE, 'MM') >= TO_DATE('"+ beginDate +"', 'YYYY-MM')";
		}
		
		if(!"".equals(endDae)){
			conditions += "AND TRUNC(RECEIVABLES_DATE, 'MM') <= TO_DATE('"+ endDae +"', 'YYYY-MM')";
		}
		return conditions;
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
		conditions = this.filterConditions(conditions, requestWrapper);
		OutputStream os = null;
		try {
			List<HeaderBean> header = new ArrayList<HeaderBean>();
			HeaderBean hBean = null;

			hBean = new HeaderBean();
			hBean.setName("ORG_CODE");
			hBean.setTitle("渠道商代码");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("ORG_NAME");
			hBean.setTitle("渠道商名称");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("PNAME");
			hBean.setTitle("办事处名称");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("RECEIVABLES_DATE");
			hBean.setTitle("统计时间");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("BEGIN_AMOUNT");
			hBean.setTitle("期初余额");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("CLOSE_ORDER_AMOUNT");
			hBean.setTitle("本期借方发生额");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("BACK_AMOUNT");
			hBean.setTitle("本期贷方发生额");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("YEER_CLOSE_ORDER_AMOUNT");
			hBean.setTitle("本年累计借方发生额");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("YEAR_BACK_AMOUNT");
			hBean.setTitle("本年累计贷方发生额");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("END_AMOUNT");
			hBean.setTitle("期末余额");
			header.add(hBean);

			QuerySet qs = dao.queryDownInfo(conditions);
			os = response.getHttpResponse().getOutputStream();
			response.getHttpResponse().reset();
			ExportManager.exportFile(response.getHttpResponse(), "年度应收汇总查询",
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
