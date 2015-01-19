package com.org.frameImpl.dao.logmng;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class LogUserOperaDao extends BaseDAO
{
	//定义instance
    public  static final LogUserOperaDao getInstance(ActionContext atx)
    {
    	LogUserOperaDao dao = new LogUserOperaDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
    
    public BaseResultSet search(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres += " ORDER BY OPERATETIME DESC ";
    	page.setFilter(wheres.toUpperCase());
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT OPID,USERID,USERNAME,USERDEPTID,OPERATEIP,OPERATETIME,YWLX,RESULT,MEMO,OPERATETYPE \n");
    	sql.append(" FROM LOG_USEROPERATION \n");
    	BaseResultSet bs = null;
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDateFormat("OPERATETIME", "yyyy-MM-dd HH:mm:ss");
    	return bs;
    }
}
