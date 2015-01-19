package com.org.dms.action.part.storageMng.search;

import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.org.dms.common.RowMapUtils;
import com.org.dms.dao.part.storageMng.search.DealerLineOfCreditDao;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.fileimport.ExportManager;
import com.org.framework.fileimport.HeaderBean;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;

/**
 * 
 * @ClassName: DealerLineOfCreditAction
 * @Description: 应收账款统计
 * @author: ALONY
 * @date: 2014年12月8日 上午10:27:40
 */
public class DealerLineOfCreditAction {
	// 日志类
	private Logger logger = com.org.framework.log.log
			.getLogger("DealerLineOfCreditAction");
	// 上下文对象
	private ActionContext atx = ActionContext.getContext();
	// 定义dao对象
	private DealerLineOfCreditDao dao = DealerLineOfCreditDao.getInstance(atx);

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
	 * @Title: queryInfoById
	 * @Description: 子页面主信息
	 * @return: void
	 */
	public void queryInfoById() {
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			String id = Pub.val(requestWrapper, "id");
			QuerySet qs = this.dao.queryInfoById(id); // 查询入库单主信息
			Map<String, Map<String, String>> resultMap = RowMapUtils
					.toConvert(qs); // 用来保存拼接的数据，用来转换成JSON
			JSONObject jsonObj = JSONObject.fromObject(resultMap);
			atx.setOutMsg(jsonObj.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}

	
	/**
	 * 
	 * @Title: queryOrderInfo
	 * @Description: 子页面查询
	 * @return: void
	 */
	public void queryOrderInfo() {
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			PageManager pageManager = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(requestWrapper,
					pageManager);
			conditions = this.filterConditons(conditions, requestWrapper);
			atx.setOutData("bs",
					this.dao.queryOrderInfo(pageManager, conditions));
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
		conditions = this.filterConditons(conditions, requestWrapper);
		OutputStream os = null;
		try {
			List<HeaderBean> header = new ArrayList<HeaderBean>();
			HeaderBean hBean = null;
			
			hBean = new HeaderBean();
			hBean.setName("ORDER_NO");
			hBean.setTitle("订单号");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("APPLY_DATE");
			hBean.setTitle("提报日期");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("CLOSE_DATE");
			hBean.setTitle("关单日期");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("OCCUPY_FUNDS");
			hBean.setTitle("已占用");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("REPAY_AMOUNT");
			hBean.setTitle("已回款");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("SUM_OCCUPY_FUNDS");
			hBean.setTitle("剩余占用");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("ORDER_STATUS");
			hBean.setTitle("关单状态");
			header.add(hBean);
			
			QuerySet qs = dao.queryDownInfo(conditions);
			os = response.getHttpResponse().getOutputStream();
			response.getHttpResponse().reset();
			ExportManager.exportFile(response.getHttpResponse(), "订单占用明细",
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
	
	/**
	 * 
	 * @Title: filterConditons
	 * @Description: 查询条件过滤
	 * @param conditons
	 * @param requestWrapper
	 * @return
	 * @return: String
	 */
	public String filterConditons(String conditions, RequestWrapper requestWrapper){
		Map<String, String> map = RequestUtil.getValues(requestWrapper);
		String orderStatus = map.get("ORDER_STATUS");
		if(orderStatus != null && !"".equals(orderStatus)){
			if(orderStatus.equals("9999")){
				conditions += " AND O.ORDER_STATUS <> 202206";
			} else {
				conditions += " AND O.ORDER_STATUS = 202206";
			}
		}
		return conditions;
	}

	public void queryAccountInfoById() {
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			PageManager pageManager = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(requestWrapper,
					pageManager);
			conditions = this.filterConditons(conditions, requestWrapper);
			String orgId = requestWrapper.getParamValue("orgId");
			BaseResultSet bs = dao.queryAccountInfoById(pageManager, conditions,orgId);
			bs.setFieldDic("ACCOUNT_TYPE", "ZJZHLX");
			atx.setOutData("bs",bs);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
}
