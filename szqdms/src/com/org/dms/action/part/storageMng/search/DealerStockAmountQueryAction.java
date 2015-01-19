package com.org.dms.action.part.storageMng.search;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.storageMng.search.DealerStockAmountQueryDao;
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
 * ClassName: SummaryQueryAction 
 * Function: 供应商应付款查询
 * date: 2014年10月30日 下午9:44:37
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class DealerStockAmountQueryAction {
	
	// 日志类
	private Logger logger = com.org.framework.log.log.getLogger("DealerStockAmountQueryAction");
	// 上下文对象
	private ActionContext atx = ActionContext.getContext();
	// 定义dao对象
	private DealerStockAmountQueryDao dao = DealerStockAmountQueryDao.getInstance(atx);

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
	 * @Title: exportExcel
	 * @Description: Excel数据导出
	 * @throws Exception
	 * @return: void
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
    		hBean.setName("ORG_CODE");
    		hBean.setTitle("渠道代码");
    		header.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("ORG_NAME");
    		hBean.setTitle("渠道名称");
    		header.add(hBean);
    		
	    	hBean = new HeaderBean();
    		hBean.setName("ORG_TYPE");
    		hBean.setTitle("渠道类型");
    		header.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("P_ORG_NAME");
    		hBean.setTitle("所属办事处");
    		header.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("SUM_AMOUNT");
    		hBean.setTitle("库存金额");
    		header.add(hBean);
    		
    		QuerySet qs = dao.queryDownInfo(conditions);
    		os = response.getHttpResponse().getOutputStream();
    		response.getHttpResponse().reset();
    		ExportManager.exportFile(response.getHttpResponse(), "渠道库存金额统计", header, qs);
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
}
