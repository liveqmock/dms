package com.org.dms.action.service.part.mainTenanceApplication;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.mainTenanceApplication.MainTenanceApplicationDao;
import com.org.framework.common.QuerySet;
import com.org.framework.fileimport.ExportManager;
import com.org.framework.fileimport.HeaderBean;
import com.org.framework.util.Pub;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;

/**
 * 
 * ClassName: MainTenanceApplicationExportAction 
 * Function: 配件信息申请导出
 * date: 2014年10月23日 上午11:41:50
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class MainTenanceApplicationExportAction {
	private Logger logger = com.org.framework.log.log.getLogger("Logger");
	// 上下文对象
	private ActionContext atx = ActionContext.getContext();
	// 定义dao对象
	private MainTenanceApplicationDao dao = MainTenanceApplicationDao.getInstance(atx);
	
	
	/**
	 * 
	 * download:(这里用一句话描述这个方法的作用). 
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * @author fuxiao
	 * Date:2014年10月19日上午11:33:49
	 * @throws Exception
	 
	 */
	public void download()throws Exception{
    	ResponseWrapper response= atx.getResponse();
    	RequestWrapper requestWrapper = atx.getRequest();
		String applicationId = Pub.val(requestWrapper, "APPLICATION_ID_S_E");
 		OutputStream os = null;
        try
        {
        	List<HeaderBean> header = new ArrayList<HeaderBean>();
        	HeaderBean hBean = null;
	    	hBean = new HeaderBean();
    		hBean.setName("APPLICATION_NO");
    		hBean.setTitle("申请单号");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("PART_NO");
    		hBean.setTitle("配件代码");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("PART_NAME");
    		hBean.setTitle("配件名称");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("VIN");
    		hBean.setTitle("车辆识别码");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("PROCESS_ROUTE");
    		hBean.setTitle("工艺路线");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("OWN_FIRST_LEVEL");
    		hBean.setTitle("所属一级");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("OWN_SECOND_LEVEL");
    		hBean.setTitle("所属二级");
    		header.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("SUPPLIER_NAME");
    		hBean.setTitle("供应商名称");
    		header.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("IF_MAINTAIN");
    		hBean.setTitle("是否维护");
    		header.add(hBean);
    		
    		QuerySet qs = dao.queryPartInfoApplicationInfo(applicationId);
    		os = response.getHttpResponse().getOutputStream();
    		response.getHttpResponse().reset();
    		ExportManager.exportFile(response.getHttpResponse(), "配件信息申请单明细", header, qs);
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
