package com.org.dms.action.part.salesMng.spreadFill;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.salesMng.spreadFill.SpreadFillDao;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

public class SpreadFillAction {
	 private Logger logger = com.org.framework.log.log.getLogger("SpreadFillAction");
	    private ActionContext atx = ActionContext.getContext();
	    private SpreadFillDao dao = SpreadFillDao.getInstance(atx);
    /**
     *价差应补查询
     */
    public void spreadFillSearch() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
        	PageManager page = new PageManager();
            String conditions = RequestUtil.getConditionsWhere(request, page);
            HashMap<String, String> hm;
            hm = RequestUtil.getValues(request);
            String startDate = hm.get("START_DATE");
            String endDate = hm.get("END_DATE");
            String fillType = hm.get("FILL_TYPE");
            String partCode =  hm.get("T.PART_CODE");
            String orgId = hm.get("T.ORG_ID");
            page.setPageRows(100000);
            BaseResultSet bs = dao.spreadFillSearch(page, user, conditions,startDate,endDate,fillType,partCode,orgId);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
}
