package com.org.dms.action.part.storageMng.search;

import java.util.Map;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.storageMng.search.PartStockInOrOutSearchMngDao;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

public class PartStockInOrOutSearchMngAction {
	//searchPart
	private Logger logger = com.org.framework.log.log.getLogger(
	        "ContractInfoSearchManageAciton");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private PartStockInOrOutSearchMngDao dao = PartStockInOrOutSearchMngDao.getInstance(atx);

	public void searchPart() throws Exception {

        try {
        	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
            String conditions = RequestUtil.getConditionsWhere(request, page);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = dao.searchPart(page, user, conditions);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
	public void stockSearch() throws Exception {

        try {
        	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
            Map<String, String> hm = RequestUtil.getValues(request);
            // BaseResultSet：结果集封装对象
            page.setPageRows(9999999);
            BaseResultSet baseResultSet = dao.stockSearch(page, user, hm);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
	//outOrderSearch
	public void outOrderSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			String OUT_ID = Pub.val(request, "OUT_ID");
			BaseResultSet bs = dao.outOrderSearch(page,user,conditions,OUT_ID);
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
        String OUT_ID = Pub.val(request, "OUT_ID");
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            BaseResultSet bs = dao.outOrderInfo(page, OUT_ID, conditions);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
	//outOrderSearch
	public void inOrderSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			String IN_ID = Pub.val(request, "IN_ID");
			BaseResultSet bs = dao.inOrderSearch(page,user,conditions,IN_ID);
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
        String IN_ID = Pub.val(request, "IN_ID");
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            BaseResultSet bs = dao.inOrderInfo(page, IN_ID, conditions);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
}
