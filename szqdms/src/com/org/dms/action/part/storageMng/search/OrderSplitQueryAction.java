package com.org.dms.action.part.storageMng.search;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.storageMng.search.OrderSplitQueryDao;
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
 * @ClassName: OrderSplitQueryAction 
 * @Function: 采购订单入库明细查询
 * @date: 2014年10月30日 下午7:34:36
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class OrderSplitQueryAction {
	// 日志类
	private Logger logger = com.org.framework.log.log.getLogger("OrderSplitQueryAction");
	// 上下文对象
	private ActionContext atx = ActionContext.getContext();
	// 定义dao对象
	private OrderSplitQueryDao dao = OrderSplitQueryDao.getInstance(atx);

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
/*    		hBean = new HeaderBean();
    		hBean.setName("PART_NO");
    		hBean.setTitle("配件图号");
    		header.add(hBean);*/
    		
    		hBean = new HeaderBean();
    		hBean.setName("DISTRIBUTION_NO");
    		hBean.setTitle("配送号");
    		header.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("SPLIT_NO");
    		hBean.setTitle("采购单号");
    		header.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("WAREHOUSE_NAME");
    		hBean.setTitle("仓库");
    		header.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("IN_NO");
    		hBean.setTitle("入库单号");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("IN_DATE");
    		hBean.setTitle("入库日期");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("PRINT_DATE");
    		hBean.setTitle("打印日期");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("IN_AMOUNT");
    		hBean.setTitle("入库数量");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("PLAN_PRICE");
    		hBean.setTitle("入库计划价");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("PLAN_AMOUNT");
    		hBean.setTitle("入库金额");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("PCH_PRICE");
    		hBean.setTitle("采购价");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("PCH_AMOUNT");
    		hBean.setTitle("采购金额");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("SUPPLIER_CODE");
    		hBean.setTitle("供应商代码");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("SUPPLIER_NAME");
    		hBean.setTitle("供应商名称");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("BUYER");
    		hBean.setTitle("计划员");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("SETTLE_STATUS");
    		hBean.setTitle("结算状态");
    		header.add(hBean);
    		QuerySet qs = dao.queryDownInfo(conditions);
    		os = response.getHttpResponse().getOutputStream();
    		response.getHttpResponse().reset();
    		ExportManager.exportFile(response.getHttpResponse(), "采购订单入库明细", header, qs);
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
