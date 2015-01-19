package com.org.dms.action.part.purchaseMng.purchaseOrder;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.purchaseMng.purchaseOrder.DirectOrderSearchDao;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.fileimport.ExportManager;
import com.org.framework.fileimport.HeaderBean;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;

public class DirectOrderSearchAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "Logger");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private DirectOrderSearchDao dao = DirectOrderSearchDao.getInstance(atx);
	    
	    public void orderSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			try
			{
				BaseResultSet bs = dao.orderSearch(page,user,conditions);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    
	    public void supOrderSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			try
			{
				BaseResultSet bs = dao.supOrderSearch(page,user,conditions);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    //exportExcel
	    public void exportExcel()throws Exception{

	        RequestWrapper request = atx.getRequest();
	        ResponseWrapper response= atx.getResponse();
	        PageManager page = new PageManager();
	         User user = (User) atx.getSession().get(Globals.USER_KEY);
	         String conditions = RequestUtil.getConditionsWhere(request,page);
	         String orderId = Pub.val(request, "ORDER_ID");
	         String splitNo = Pub.val(request, "SPLIT_NO");
	        try {
	            List<HeaderBean> header = new ArrayList<HeaderBean>();
	            HeaderBean hBean = null;
	            
	            
	            hBean = new HeaderBean();
	            hBean.setName("SPLIT_NO");
	            hBean.setTitle("采购单号");
	            header.add(0,hBean);
	            
	            hBean = new HeaderBean();
	            hBean.setName("ORDER_NO");
	            hBean.setTitle("销售单号");
	            header.add(1,hBean);
	            
	            hBean = new HeaderBean();
	            hBean.setName("PART_CODE");
	            hBean.setTitle("配件代码");
	            header.add(2,hBean);

	            hBean = new HeaderBean();
	            hBean.setName("PART_NAME");
	            hBean.setTitle("配件名称");
	            hBean.setWidth(50);
	            header.add(3,hBean);

	            hBean = new HeaderBean();
	            hBean.setName("UNIT");
	            hBean.setTitle("计量单位");
	            header.add(4,hBean);
	            
	            hBean = new HeaderBean();
	            hBean.setName("MIN_PACK");
	            hBean.setTitle("最小包装数");
	            header.add(5,hBean);
	            
	            hBean = new HeaderBean();
	            hBean.setName("ORDER_COUNT");
	            hBean.setTitle("订购数量");
	            header.add(6,hBean);
	            
	            hBean = new HeaderBean();
	            hBean.setName("AUDIT_COUNT");
	            hBean.setTitle("审核数量");
	            header.add(7,hBean);
	            
	            hBean = new HeaderBean();
	            hBean.setName("DELIVERY_COUNT");
	            hBean.setTitle("发运数量");
	            header.add(8,hBean);
	            
	            hBean = new HeaderBean();
	            hBean.setName("SIGN_COUNT");
	            hBean.setTitle("签收数量");
	            header.add(9,hBean);
	            
	            QuerySet querySet = dao.downloadOrder(splitNo,orderId);
	            QuerySet getOrgName = dao.getOrderName(orderId);
	            ExportManager.exportFile(response.getHttpResponse(), getOrgName.getString(1, "ORG_NAME"), header, querySet);
	        } catch (Exception e) {
	            atx.setException(e);
	            logger.error(e);
	        }
	    }
	    
	    public void partOrderDetailSearch() throws Exception {
	        RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        String orderId = request.getParamValue("orderId");
//	        String pchOrderNo = request.getParamValue("pchOrderNo");
	        PageManager page = new PageManager();
	        String conditions = RequestUtil.getConditionsWhere(request, page);
	        page.setPageRows(10000);
	        try {
	            BaseResultSet bs = dao.partOrderDetailSearch(page, user, conditions,orderId);
	            atx.setOutData("bs", bs);
	        } catch (Exception e) {
	            logger.error(e);
	            atx.setException(e);
	        }
	    }
	    public void partOrderDetailSearch1() throws Exception {
	        RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        String orderId = request.getParamValue("orderId");
//	        String pchOrderNo = request.getParamValue("pchOrderNo");
	        PageManager page = new PageManager();
	        String conditions = RequestUtil.getConditionsWhere(request, page);
	        page.setPageRows(10000);
	        try {
	            BaseResultSet bs = dao.partOrderDetailSearch1(page, user, conditions,orderId);
	            atx.setOutData("bs", bs);
	        } catch (Exception e) {
	            logger.error(e);
	            atx.setException(e);
	        }
	    }
}
