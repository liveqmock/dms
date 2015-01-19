package com.org.dms.action.part.salesMng.search;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.salesMng.search.NotPutInStorageQueryDao;
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
 * ClassName: NotPutInStorageQueryAction 
 * Function: 未入库订单汇总
 * date: 2014年11月3日 下午4:12:08
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class NotPutInStorageQueryAction {
	
	// 日志类
	private Logger logger = com.org.framework.log.log.getLogger("NotPutInStorageQueryAction");
	// 上下文对象
	private ActionContext atx = ActionContext.getContext();
	// 定义dao对象
	private NotPutInStorageQueryDao dao = NotPutInStorageQueryDao.getInstance(atx);

	/**
	 * 
	 * queryListInfo: 表单查询  
	 * @author fuxiao Date:2014年10月23日上午10:36:58
	 */
	public void queryListInfo() {
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			PageManager pageManager = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
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
 		PageManager pageManager = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
 		OutputStream os = null;
        try
        {
        	List<HeaderBean> header = new ArrayList<HeaderBean>();
        	HeaderBean hBean = null;
    		hBean = new HeaderBean();
    		hBean.setName("ORG_CODE");
    		hBean.setTitle("库区编码");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("ORG_NAME");
    		hBean.setTitle("库区名称");
    		header.add(hBean);
	    	hBean = new HeaderBean();
    		hBean.setName("ORDER_NO");
    		hBean.setTitle("入库单号");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("REAL_AMOUNT");
    		hBean.setTitle("入库单金额");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("LAST_AREA");
    		hBean.setTitle("上级发货库区");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("ORDER_NO");
    		hBean.setTitle("发货库区出库单号");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("OUT_DATE");
    		hBean.setTitle("出库日期");
    		header.add(hBean);
    		QuerySet qs = dao.queryDownInfo(conditions);
    		os = response.getHttpResponse().getOutputStream();
    		response.getHttpResponse().reset();
    		ExportManager.exportFile(response.getHttpResponse(), "未入库订单汇总", header, qs);
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
