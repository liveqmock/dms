package com.org.dms.action.part.financeMng.settlement;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.financeMng.settlement.SupplierSettlementModeDao;
import com.org.dms.vo.part.PtBuPchOrderSplitVO;
import com.org.dms.vo.part.PtBuPchReturnVO;
import com.org.dms.vo.part.PtBuSupInvoiceSummaryVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

public class SupplierSettlementModeAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "Logger");
	private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private SupplierSettlementModeDao dao = SupplierSettlementModeDao.getInstance(atx);
	
	
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
	

}
