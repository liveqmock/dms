package com.org.dms.action.channel.channelInfo;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.org.dms.dao.channel.channelInfo.AccountBalanceQueryDao;
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
 * ClassName: AccountBalanceQueryAction 
 * Function: 渠道信息查询 -> 财务相关 -> 账户余额查询
 * date: 2014年10月27日 上午11:32:30
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class AccountBalanceQueryAction {
	// 日志类
	private Logger logger = com.org.framework.log.log.getLogger("AccountBalanceQueryAction");
	// 上下文对象
	private ActionContext atx = ActionContext.getContext();
	// 定义dao对象
	private AccountBalanceQueryDao dao = AccountBalanceQueryDao.getInstance(atx);

	/**
	 * 
	 * queryListInfo: 表单查询  
	 * @author fuxiao Date:2014年10月23日上午10:36:58
	 */
	public void queryListInfo() {
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager pageManager = new PageManager();
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
	 * queryListDetailsInfo: 表单详情查询
	 * @author fuxiao
	 * Date:2014年10月27日
	 *
	 */
	public void queryListDetailsInfo() {
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager pageManager = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(requestWrapper,pageManager);
			conditions = this.filterConditons(conditions, requestWrapper);
			atx.setOutData("bs", this.dao.queryDetailsList(pageManager, conditions, user));
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
			hBean.setName("ORDER_TYPE");
			hBean.setTitle("订单类型");
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
}
