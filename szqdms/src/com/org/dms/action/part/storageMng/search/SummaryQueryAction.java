package com.org.dms.action.part.storageMng.search;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.storageMng.search.SummaryQueryDao;
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
public class SummaryQueryAction {
	
	// 日志类
	private Logger logger = com.org.framework.log.log.getLogger("SummaryDtlQueryAction");
	// 上下文对象
	private ActionContext atx = ActionContext.getContext();
	// 定义dao对象
	private SummaryQueryDao dao = SummaryQueryDao.getInstance(atx);

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
    		hBean.setName("SUPPLIER_CODE");
    		hBean.setTitle("供应商代码");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("SUPPLIER_NAME");
    		hBean.setTitle("供应商名称");
    		header.add(hBean);
	    	hBean = new HeaderBean();
    		hBean.setName("IRS_AMOUNT");
    		hBean.setTitle("欠款总额");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("I_AMOUNT");
    		hBean.setTitle("开票总额");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("S_AMOUNT");
    		hBean.setTitle("已付总额");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("SS_AMOUNT");
    		hBean.setTitle("应付总额");
    		header.add(hBean);
    		QuerySet qs = dao.queryDownInfo(conditions);
    		os = response.getHttpResponse().getOutputStream();
    		response.getHttpResponse().reset();
    		ExportManager.exportFile(response.getHttpResponse(), "付款查询明细", header, qs);
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
