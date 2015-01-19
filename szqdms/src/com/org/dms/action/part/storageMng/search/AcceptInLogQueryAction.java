package com.org.dms.action.part.storageMng.search;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.storageMng.search.AcceptInLogQueryDao;
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
 * ClassName: AcceptInLogQueryAction 
 * Function: 入库验收日志查询
 * date: 2014年11月15日 下午10:31:07
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class AcceptInLogQueryAction {
	// 日志类
	private Logger logger = com.org.framework.log.log.getLogger("AcceptInLogQueryAction");
	// 上下文对象
	private ActionContext atx = ActionContext.getContext();
	// 定义dao对象
	private AcceptInLogQueryDao dao = AcceptInLogQueryDao.getInstance(atx);

	/**
	 * 
	 * queryListInfo: 表单查询  
	 * @author fuxiao 
	 * @Date:2014年10月23日上午10:36:58
	 */
	public void queryListInfo() {
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			PageManager pageManager = new PageManager();
			User user = (User) atx.getSession().get(Globals.USER_KEY);
			String conditions = RequestUtil.getConditionsWhere(requestWrapper,pageManager);
			atx.setOutData("bs", this.dao.queryList(pageManager, conditions, user, true));
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
    		hBean.setName("UNIT");
    		hBean.setTitle("配件单位");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("PLAN_PRICE");
    		hBean.setTitle("计划价格");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("PLAN_AMOUNT");
    		hBean.setTitle("计划金额");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("CHECK_COUNT");
    		hBean.setTitle("验收数量");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("CHECK_TIME");
    		hBean.setTitle("验收时间");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("SPLIT_NO");
    		hBean.setTitle("采购单号");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("SUPPLIER_NAME");
    		hBean.setTitle("供货商名称");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("SUPPLIER_CODE");
    		hBean.setTitle("供货商代码");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("DISTRIBUTION_NO");
    		hBean.setTitle("配送号");
    		header.add(hBean);
    		QuerySet qs = dao.downQuery(conditions);
    		os = response.getHttpResponse().getOutputStream();
    		response.getHttpResponse().reset();
    		ExportManager.exportFile(response.getHttpResponse(), "入库验收查询", header, qs);
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
