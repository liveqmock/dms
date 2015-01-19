package com.org.dms.action.part.salesMng.search;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.salesMng.search.PartAssemblyQueryDao;
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
 * ClassName: PartAssemblyQueryAction 
 * Function: 大总成销量统计报表
 * date: 2014年11月3日 下午4:12:08
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class PartAssemblyQueryAction {
	
	// 日志类
	private Logger logger = com.org.framework.log.log.getLogger("PartAssemblyQueryAction");
	// 上下文对象
	private ActionContext atx = ActionContext.getContext();
	// 定义dao对象
	private PartAssemblyQueryDao dao = PartAssemblyQueryDao.getInstance(atx);

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
			Map<String,String> hm = RequestUtil.getValues(requestWrapper);
			atx.setOutData("bs", this.dao.queryList(pageManager, hm));
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
 		Map<String,String> hm = RequestUtil.getValues(requestWrapper);
 		OutputStream os = null;
        try
        {

        	List<HeaderBean> header = new ArrayList<HeaderBean>();
        	HeaderBean hBean = null;
    		hBean = new HeaderBean();
    		hBean.setName("ORG_CODE");
    		hBean.setTitle("单位代码");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("ORG_NAME");
    		hBean.setTitle("单位名称");
    		header.add(hBean);
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
    		hBean.setName("PART_COUNT");
    		hBean.setTitle("出库量");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("COUNT_PRICE");
    		hBean.setTitle("出库金额");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("BELONG_ASSEMBLY");
    		hBean.setTitle("所属总成");
    		header.add(hBean);
    		QuerySet qs = dao.queryDownInfo(hm);
    		os = response.getHttpResponse().getOutputStream();
    		response.getHttpResponse().reset();
    		ExportManager.exportFile(response.getHttpResponse(), "大总成销量统计报表", header, qs);
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
