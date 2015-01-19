package com.org.dms.action.part.storageMng.search;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.storageMng.search.StockInAndOutQueryDao;
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
 * ClassName: StockInAndOutQueryAction 
 * Function: 配件出入库统计
 * date: 2014年10月31日 下午2:00:30
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class StockInAndOutQueryAction {
	
	// 日志类
	private Logger logger = com.org.framework.log.log.getLogger("SummaryDtlQueryAction");
	// 上下文对象
	private ActionContext atx = ActionContext.getContext();
	// 定义dao对象
	private StockInAndOutQueryDao dao = StockInAndOutQueryDao.getInstance(atx);

	/**
	 * 
	 * queryListInfo: 表单查询  
	 * @author fuxiao Date:2014年10月23日上午10:36:58
	 */
	public void queryListInfo() {
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			PageManager pageManager = new PageManager();
			RequestUtil.getConditionsWhere(requestWrapper,pageManager);
			String sql = dao.getString(requestWrapper);
			atx.setOutData("bs", this.dao.queryList(pageManager, sql));
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
    	String sql = dao.getString(requestWrapper);
 		OutputStream os = null;
        try
        {
        	List<HeaderBean> header = new ArrayList<HeaderBean>();
        	HeaderBean hBean = null;
    		hBean = new HeaderBean();
    		hBean.setName("PART_CODE");
    		hBean.setTitle("零件编号");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("PART_NAME");
    		hBean.setTitle("零件名称");
    		hBean.setWidth(50);
    		header.add(hBean);
	    	hBean = new HeaderBean();
    		hBean.setName("OUT_AMOUNT");
    		hBean.setTitle("出库数量");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("IN_AMOUNT");
    		hBean.setTitle("入库数量");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("OUT_NO_COUNT");
    		hBean.setTitle("出库单次数");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("IN_NO_COUNT");
    		hBean.setTitle("入库单次数");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("WAREHOUSE_NAME");
    		hBean.setTitle("库区");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("USER_NAME");
    		hBean.setTitle("库管员");
    		header.add(hBean);
    		QuerySet qs = dao.queryDownInfo(sql);
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
