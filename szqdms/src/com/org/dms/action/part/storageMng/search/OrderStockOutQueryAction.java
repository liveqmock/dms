package com.org.dms.action.part.storageMng.search;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.storageMng.search.OrderStockOutQueryDao;
import com.org.framework.common.BaseResultSet;
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
 * ClassName: OrderStockOutQueryAction 
 * Function: 销售出库明细
 * date: 2014年10月30日 下午8:37:07
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class OrderStockOutQueryAction {
	// 日志类
	private Logger logger = com.org.framework.log.log.getLogger("OrderStockOutQueryAction");
	// 上下文对象
	private ActionContext atx = ActionContext.getContext();
	// 定义dao对象
	private OrderStockOutQueryDao dao = OrderStockOutQueryDao.getInstance(atx);

	/**
	 * 
	 * queryListInfo: 表单查询  
	 * @author fuxiao Date:2014年10月23日上午10:36:58
	 */
	public void queryListInfo() {
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			PageManager pageManager = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(requestWrapper,pageManager);
			atx.setOutData("bs", this.dao.queryList(pageManager, conditions));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}

	/**
	 * 
	 * exportExcel: 查询数据导出  
	 * @author fuxiao Date:2014年10月23日
	 *
	 */
	public void exportExcel() throws Exception{
		ResponseWrapper response= atx.getResponse();
    	RequestWrapper requestWrapper = atx.getRequest();
    	PageManager page = new PageManager();
 		String conditions = RequestUtil.getConditionsWhere(requestWrapper,page);
 		OutputStream os = null;
        try
        {
        	List<HeaderBean> header = new ArrayList<HeaderBean>();
        	HeaderBean hBean = null;
	    	hBean = new HeaderBean();
    		hBean.setName("PART_CODE");
    		hBean.setTitle("配件代码");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("PART_NAME");
    		hBean.setTitle("配件名称");
    		hBean.setWidth(50);
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("WAREHOUSE_NAME");
    		hBean.setTitle("出库仓库");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("ORDER_NO");
    		hBean.setTitle("销售订单号");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("OUT_NO");
    		hBean.setTitle("出库号");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("OUT_TYPE");
    		hBean.setTitle("出库类型");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("OUT_DATE");
    		hBean.setTitle("出库时间");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("CLOSE_DATE");
    		hBean.setTitle("订单关闭时间");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("OUT_AMOUNT");
    		hBean.setTitle("出库数量");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("PLAN_PRICE");
    		hBean.setTitle("计划价");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("PLAN_AMOUNT");
    		hBean.setTitle("出库金额");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("SALE_PRICE");
    		hBean.setTitle("经销商价格");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("SALE_AMOUNT");
    		hBean.setTitle("经销商金额");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("ORG_CODE");
    		hBean.setTitle("客户代码");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("ORG_NAME");
    		hBean.setTitle("客户名称");
    		header.add(hBean);
    		QuerySet qs = dao.queryDownInfo(conditions);
    		os = response.getHttpResponse().getOutputStream();
    		response.getHttpResponse().reset();
    		ExportManager.exportFile(response.getHttpResponse(), "销售出库明细", header, qs);
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
        finally
        {
        	if (os != null)
            {
              os.close();
            }
        }
	}
	public void getAmount() throws Exception {
        RequestWrapper request = atx.getRequest();
        PageManager page = new PageManager();
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            BaseResultSet bs = dao.getAmount(page, conditions);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
}
