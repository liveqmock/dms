package com.org.dms.action.part.storageMng.search;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.storageMng.search.StockWarningQueryDao;
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
 * @ClassName: StockWarningQueryAction
 * @Description: 库存安全查询
 * @author: fuxiao
 * @date: 2014年12月2日 下午9:02:20
 */
public class StockWarningQueryAction {
	// 日志类
	private Logger logger = com.org.framework.log.log
			.getLogger("StockWarningQueryAction");
	// 上下文对象
	private ActionContext atx = ActionContext.getContext();
	// 定义dao对象
	private StockWarningQueryDao dao = StockWarningQueryDao.getInstance(atx);

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
			conditions = this.filterConditions(conditions, requestWrapper);
			Map<String, String> map = RequestUtil.getValues(requestWrapper);
			String warehouseCode = map.get("STOCK_CODE") == null ? "" : map.get("STOCK_CODE") ;
			atx.setOutData("bs",
					this.dao.queryList(pageManager, conditions, user, true, warehouseCode));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
	
	/**
	 * 
	 * @Title: filterConditions
	 * @Description: 对查询条件转换
	 * @param conditions
	 * @param request
	 * @return
	 * @return: String
	 */
	private String filterConditions(String conditions, RequestWrapper request){
		Map<String, String> map = RequestUtil.getValues(request);
		String pchCountB = map.get("PCH_COUNT_B") == null ? "" : map.get("PCH_COUNT_B");
		if(!pchCountB.equals("")){
			conditions += " AND NVL(PCH_COUNT,0) >= " + pchCountB + " ";
		}
		
		String pchCountE = map.get("PCH_COUNT_E") == null ? "" : map.get("PCH_COUNT_E");
		if(!pchCountE.equals("")){
			conditions += " AND NVL(PCH_COUNT,0) <= " + pchCountE + " ";
		}
		
		return conditions;
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
		conditions = this.filterConditions(conditions, requestWrapper);
		
		Map<String, String> map = RequestUtil.getValues(requestWrapper);
		String warehouseCode = map.get("STOCK_CODE") == null ? "" : map.get("STOCK_CODE") ;
		OutputStream os = null;
		try {
			List<HeaderBean> header = new ArrayList<HeaderBean>();
			HeaderBean hBean = null;
			hBean = new HeaderBean();

			hBean.setName("PART_CODE");
			hBean.setTitle("配件编号");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("PART_NAME");
			hBean.setTitle("配件名称");
			hBean.setWidth(50);
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("UPPER_LIMIT");
			hBean.setTitle("库存上限");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("LOWER_LIMIT");
			hBean.setTitle("库存下限");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("PCH_COUNT");
			hBean.setTitle("在途数量");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("AVAILABLE_AMOUNT");
			hBean.setTitle("可用数量");
			header.add(hBean);

			hBean = new HeaderBean();
			hBean.setName("USER_NAME");
			hBean.setTitle("采购员");
			header.add(hBean);
			
			hBean = new HeaderBean();
			hBean.setName("SUPPLIER_CODE");
			hBean.setTitle("供应商编码");
			header.add(hBean);

			QuerySet qs = dao.queryDownInfo(conditions, user, warehouseCode);
			os = response.getHttpResponse().getOutputStream();
			response.getHttpResponse().reset();
			ExportManager.exportFile(response.getHttpResponse(), "库存安全查询",
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
