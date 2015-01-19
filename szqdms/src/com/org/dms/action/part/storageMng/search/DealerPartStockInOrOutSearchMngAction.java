package com.org.dms.action.part.storageMng.search;

import java.util.Map;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.storageMng.search.DealerPartStockInOrOutSearchMngDao;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

public class DealerPartStockInOrOutSearchMngAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "ContractInfoSearchManageAciton");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private DealerPartStockInOrOutSearchMngDao dao = DealerPartStockInOrOutSearchMngDao.getInstance(atx);
	    
	    public void stockSearch() throws Exception {

	        try {
	        	RequestWrapper request = atx.getRequest();
		        User user = (User) atx.getSession().get(Globals.USER_KEY);
				PageManager page = new PageManager();
	            Map<String, String> hm = RequestUtil.getValues(request);
	            page.setPageRows(9999999);
	            // BaseResultSet：结果集封装对象
	            BaseResultSet baseResultSet = dao.stockSearch(page, user, hm);
	            // 输出结果集，第一个参数”bs”为固定名称，不可修改
	            atx.setOutData("bs", baseResultSet);
	        } catch (Exception e) {
	            logger.error(e);
	            atx.setException(e);
	        }
	    }
	    //dealerStockSearch
	    public void dealerStockSearch() throws Exception {

	        try {
	        	RequestWrapper request = atx.getRequest();
		        User user = (User) atx.getSession().get(Globals.USER_KEY);
				PageManager page = new PageManager();
	            Map<String, String> hm = RequestUtil.getValues(request);
	            // BaseResultSet：结果集封装对象
	            page.setPageRows(9999999);
	            BaseResultSet baseResultSet = dao.dealerStockSearch(page, user, hm);
	            // 输出结果集，第一个参数”bs”为固定名称，不可修改
	            atx.setOutData("bs", baseResultSet);
	        } catch (Exception e) {
	            logger.error(e);
	            atx.setException(e);
	        }
	    }
	    public void outOrderSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			try
			{
				String ORDER_NO = Pub.val(request, "ORDER_NO");
				BaseResultSet bs = dao.outOrderSearch(page,user,conditions,ORDER_NO);
				atx.setOutData("bs", bs);
				
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    public void outOrderInfo() throws Exception {
	        RequestWrapper request = atx.getRequest();
	        PageManager page = new PageManager();
	        String ORDER_NO = Pub.val(request, "ORDER_NO");
	        String conditions = RequestUtil.getConditionsWhere(request, page);
	        try {
	        	QuerySet getType = dao.getType(ORDER_NO);
				String type = getType.getString(1, "STORAGE_TYPE");
				if(type.equals(DicConstant.QDCRKLX_01)){
					BaseResultSet bs = dao.saleOrderInfo(page, ORDER_NO, conditions);
		            atx.setOutData("bs", bs);
				}else if(type.equals(DicConstant.QDCRKLX_03)){
					BaseResultSet bs = dao.realOrderInfo(page, ORDER_NO, conditions);
		            atx.setOutData("bs", bs);
				}else{
					BaseResultSet bs = dao.retOrderInfo(page, ORDER_NO, conditions);
		            atx.setOutData("bs", bs);
				}
	            
	        } catch (Exception e) {
	            logger.error(e);
	            atx.setException(e);
	        }
	    }
	    public void inOrderSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			try
			{
				String ORDER_NO = Pub.val(request, "ORDER_NO");
				BaseResultSet bs = dao.inOrderSearch(page,user,conditions,ORDER_NO);
				atx.setOutData("bs", bs);
				
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    public void inOrderInfo() throws Exception {
	        RequestWrapper request = atx.getRequest();
	        PageManager page = new PageManager();
	        String ORDER_NO = Pub.val(request, "ORDER_NO");
	        String conditions = RequestUtil.getConditionsWhere(request, page);
	        try {
	        	QuerySet getType = dao.getType1(ORDER_NO);
				String type = getType.getString(1, "STORAGE_TYPE");
				if(type.equals(DicConstant.QDCRKLX_02)){
					BaseResultSet bs = dao.saleOrderInfo1(page, ORDER_NO, conditions);
		            atx.setOutData("bs", bs);
				}else if(type.equals(DicConstant.QDCRKLX_04)){
					BaseResultSet bs = dao.realOrderInfo1(page, ORDER_NO, conditions);
		            atx.setOutData("bs", bs);
				}else{
					BaseResultSet bs = dao.retOrderInfo1(page, ORDER_NO, conditions);
		            atx.setOutData("bs", bs);
				}
	            
	        } catch (Exception e) {
	            logger.error(e);
	            atx.setException(e);
	        }
	    }

}
