package com.org.dms.action.part.storageMng.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.storageMng.search.DealerOutSearchMngDao;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.fileimport.ExportManager;
import com.org.framework.fileimport.HeaderBean;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;

public class DealerOutSearchMngAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "Logger");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    // 获取session中的user对象
	    private User user = (User) atx.getSession().get(Globals.USER_KEY);
	    //定义dao对象
	    private DealerOutSearchMngDao dao = DealerOutSearchMngDao.getInstance(atx);
	    // 定义reponse对象
	    private ResponseWrapper responseWrapper = atx.getResponse();
	    /**
	     * 订单查询导出
	     * @throws Exception
	     */
	    public void download()throws Exception{

	        try {
	        	//获取封装后的request对象
	        	RequestWrapper request = atx.getRequest();
	        	// 定义查询分页对象
	            PageManager pageManager = new PageManager();
	            // 将request流中的信息转化为where条件
	            String conditions = RequestUtil.getConditionsWhere(request, pageManager);
	            List<HeaderBean> header = new ArrayList<HeaderBean>();
	            HeaderBean hBean = null;
	            hBean = new HeaderBean();
	            hBean.setName("ORDER_NO");
	            hBean.setTitle("订单编号");
	            header.add(hBean);

	            hBean = new HeaderBean();
	            hBean.setName("ORDER_TYPE");
	            hBean.setTitle("订单类型");
	            header.add(hBean);

	            hBean = new HeaderBean();
	            hBean.setName("ORDER_STATUS");
	            hBean.setTitle("订单状态");
	            header.add(hBean);

	            hBean = new HeaderBean();
	            hBean.setName("ORG_NAME");
	            hBean.setTitle("提报单位");
	            header.add(hBean);

	            hBean = new HeaderBean();
	            hBean.setName("APPLY_DATE");
	            hBean.setTitle("提报时间");
	            header.add(hBean);

	            hBean = new HeaderBean();
	            hBean.setName("CLOSE_DATE");
	            hBean.setTitle("关闭时间");
	            header.add(hBean);

	            hBean = new HeaderBean();
	            hBean.setName("ORDER_AMOUNT");
	            hBean.setTitle("订单金额(元)");
	            header.add(hBean);

	            hBean = new HeaderBean();
	            hBean.setName("SHOULD_AMOUNT");
	            hBean.setTitle("应发金额(元)");
	            header.add(hBean);

	            QuerySet querySet = dao.download(conditions,user);
	            ExportManager.exportFile(responseWrapper.getHttpResponse(), "DDCXDC", header, querySet);
	        } catch (Exception e) {
	            atx.setException(e);
	            logger.error(e);
	        }
	    }
	    
	    public void searchSaleOrder() throws Exception {

	        try {
	        	RequestWrapper request = atx.getRequest();
		        User user = (User) atx.getSession().get(Globals.USER_KEY);
	            PageManager pageManager = new PageManager();
	            String conditions = RequestUtil.getConditionsWhere(request, pageManager);
	            BaseResultSet baseResultSet = null;
	            baseResultSet = dao.searchSaleOrder(pageManager, user, conditions);
	            baseResultSet.setFieldDic("ORDER_STATUS", DicConstant.DDZT);
	            atx.setOutData("bs", baseResultSet);
	        } catch (Exception e) {
	            logger.error(e);
	            atx.setException(e);
	        }
	    }

}
