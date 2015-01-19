package com.org.frameImpl.dao.parasmng;

import com.org.dms.common.DicConstant;
import com.org.frameImpl.vo.UserParaConfigureVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.mvc.context.ActionContext;

public class UserParaConfigureDao extends BaseDAO
{
	 //定义instance
    public  static final UserParaConfigureDao getInstance(ActionContext atx)
    {
    	UserParaConfigureDao dao = new UserParaConfigureDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	/**
	 * 校验参数代码是否存在
	 * @param paraKey
	 * @return
	 * @throws Exception
	 * @author andy.ten@tom.com 
	 * @Time Jul 24, 2014 5:53:55 PM
	 */
    public QuerySet checkParaKey(String paraKey) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT COUNT(1) NUMS \n");
    	sql.append(" FROM USER_PARA_CONFIGURE \n");
    	sql.append(" WHERE PARAKEY = '" + paraKey +"'");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }

    public boolean insert(UserParaConfigureVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }
	
	public boolean update(UserParaConfigureVO vo) throws Exception
    {
    	return factory.update(vo);
    }
    
	public boolean updateStatus(String sn, String status) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" UPDATE USER_PARA_CONFIGURE \n");
    	sql.append(" SET STATUS = " + status + " \n");
    	sql.append(" WHERE SN = " + sn + " \n");
    	return factory.update(sql.toString(), null);
    }
	
    /**
     * 查询业务参数表
     * @param page
     * @param conditions
     * @return
     * @throws Exception
     * @author andy.ten@tom.com 
     * @Time Jul 24, 2014 5:53:46 PM
     */
	public BaseResultSet search(PageManager page,String conditions) throws Exception
	{
		String wheres = conditions;
    	wheres += " ORDER BY APPTYPE,PARAKEY ";
		page.setFilter(wheres);
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT SN,APPTYPE,TYPENAME, PARAKEY, PARANAME, PARAVALUE1, PARAVALUE2, PARAVALUE3, PARAVALUE4,BUS_TYPE,STATUS ");
		sql.append(" FROM USER_PARA_CONFIGURE ");
		BaseResultSet bs = factory.select(sql.toString(), page);
		bs.setFieldDic("STATUS", DicConstant.YXBS);
		bs.setFieldDic("BUS_TYPE", DicConstant.YWLX);
		
		return bs;
	}
}
