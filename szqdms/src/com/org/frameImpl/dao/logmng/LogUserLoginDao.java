package com.org.frameImpl.dao.logmng;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class LogUserLoginDao extends BaseDAO
{
	//定义instance
    public  static final LogUserLoginDao getInstance(ActionContext atx)
    {
    	LogUserLoginDao dao = new LogUserLoginDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
    
    public BaseResultSet search(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres += " ORDER BY LOGINTIME DESC ";
    	page.setFilter(wheres.toUpperCase());
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT LOGINID,USERID,UERNAME,USERDEPTID,LOGINIP,LOGINTIME,LOGINSTATUS,LOGOUTTIME,LOGINAREA,REALIP,MACADDRESS \n");
    	sql.append(" FROM LOG_USERLOGIN \n");
    	BaseResultSet bs = null;
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDateFormat("LOGINTIME", "yyyy-MM-dd HH:mm:ss");
    	bs.setFieldDateFormat("LOGOUTTIME", "yyyy-MM-dd HH:mm:ss");
    	return bs;
    }
}
