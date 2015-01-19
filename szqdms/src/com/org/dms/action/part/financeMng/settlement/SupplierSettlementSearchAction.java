package com.org.dms.action.part.financeMng.settlement;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.financeMng.settlement.SupplierSettlementSearchDao;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

public class SupplierSettlementSearchAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "Logger");
	private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private SupplierSettlementSearchDao dao = SupplierSettlementSearchDao.getInstance(atx);

    public void settleSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		
		try
		{
			BaseResultSet bs = dao.settleSearch(page,user,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    //settleOrderSearch
    public void settleOrderSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		RequestUtil.getConditionsWhere(request,page);
		try
		{
			String SUM_ID = Pub.val(request, "SUM_ID");
			BaseResultSet bs = dao.invioceOrderSearch(page,user,SUM_ID);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    public void settleInfo() throws Exception {
        RequestWrapper request = atx.getRequest();
        PageManager page = new PageManager();
        String SUM_ID = request.getParamValue("SUM_ID");
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            BaseResultSet bs = dao.settleInfo(page, SUM_ID, conditions);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
}
