package com.org.dms.action.part.storageMng.search;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.storageMng.search.DealerSaleOrderQueryDao;
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
 * @ClassName: DealerSaleOrderSearchAction
 * @Description: 销售出库查询（配送中心）
 * @author: fuxiao
 * @date: 2014年12月18日 下午3:42:29
 */
public class DealerSaleOrderSearchAction {

	private Logger logger = com.org.framework.log.log
			.getLogger("DealerSaleOrderSearchAction");

	// 上下文对象
	private ActionContext atx = ActionContext.getContext();

	// 定义dao对象
	private DealerSaleOrderQueryDao dao = DealerSaleOrderQueryDao
			.getInstance(atx);

	/**
	 * 
	 * @Title: searchInfo
	 * @Description: 表格数据显示
	 * @throws Exception
	 * @return: void
	 */
	public void searchInfo() throws Exception {
		RequestWrapper request = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request, page);
		conditions = this.filterConditions(conditions, request);
		try {
			BaseResultSet bs = dao.searchInfo(conditions, page, user);
			atx.setOutData("bs", bs);
		} catch (Exception e) {
			logger.error(e);
			atx.setException(e);
		}
	}
	
	/**
	 * 
	 * @Title: filterConditions
	 * @Description: 对查询条件进行过滤或添加或转换
	 * @param conditions
	 * @param request
	 * @return
	 * @return: String
	 */
	private String filterConditions(String conditions, RequestWrapper request){
		HashMap<String, String> hm = RequestUtil.getValues(request);
		String PART_ID = hm.get("PART_ID");
		if(PART_ID != null && !"".equals(PART_ID)){
			conditions += " AND EXISTS (SELECT 1 FROM PT_BU_SALE_ORDER_DTL D WHERE D.PART_ID = " + PART_ID + ")";
		}
		return conditions;
	}


	/**
	 * 
	 * @Title: exportExcel
	 * @Description: 前台下载方法
	 * @throws Exception
	 * @return: void
	 */
	public void exportExcel() throws Exception {
		ResponseWrapper response = atx.getResponse();
		RequestWrapper requestWrapper = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(requestWrapper, page);
		conditions = this.filterConditions(conditions, requestWrapper);
		OutputStream os = null;
		try {
			List<HeaderBean> header = new ArrayList<HeaderBean>();
			HeaderBean hBean = null;

			hBean = new HeaderBean();
			hBean.setName("ORDER_NO");
			hBean.setTitle("销售单号");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("ORG_CODE");
			hBean.setTitle("客户代码");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("ORG_NAME");
			hBean.setTitle("客户名称");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("LINK_MAN");
			hBean.setTitle("联系人");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("PHONE");
			hBean.setTitle("联系电话");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("DELIVERY_ADDR");
			hBean.setTitle("交货地址");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("ORDER_TYPE");
			hBean.setTitle("订单类型");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("ORDER_STATUS");
			hBean.setTitle("订单状态");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("OUT_DATE");
			hBean.setTitle("出库日期");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("AMOUNT");
			hBean.setTitle("实发金额");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("APPLY_DATE");
			hBean.setTitle("申请日期");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("CLOSE_DATE");
			hBean.setTitle("关单日期");
			header.add(hBean);
			
			QuerySet qs = dao.queryDownInfo(conditions, user);
			os = response.getHttpResponse().getOutputStream();
			response.getHttpResponse().reset();
			ExportManager.exportFile(response.getHttpResponse(), "销售出库查询",
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
