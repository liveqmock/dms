package com.org.frameImpl.dao.parasmng;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.mvc.context.ActionContext;

public class SysParaConfigureDao extends BaseDAO
{
	 //定义instance
    public  static final SysParaConfigureDao getInstance(ActionContext atx)
    {
    	SysParaConfigureDao dao = new SysParaConfigureDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	
	public BaseResultSet queryList(PageManager page) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT SN, PARAKEY, PARANAME, PARAVALUE1, PARAVALUE2, PARAVALUE3, PARAVALUE4 ");
		sql.append(" FROM SYS_PARA_CONFIGURE ");
		BaseResultSet bs = factory.select(sql.toString(), page);
		return bs;
	}
}
