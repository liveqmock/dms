package com.org.frameImpl.dao;

import com.org.frameImpl.vo.DicTreeVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * @Description:字典dao类
 * @Copyright: Copyright (c) 2011
 * @Date: 2011-10-7
 * @mail   andy.ten@tom.com
 */
public class DicTreeDao extends BaseDAO
{
	//定义instance
    public  static final DicTreeDao getInstance(ActionContext atx)
    {
    	DicTreeDao dao = new DicTreeDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
    
    public boolean insert(DicTreeVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }
	
	public boolean update(DicTreeVO vo) throws Exception
    {
    	return factory.update(vo);
    }
	
	public boolean delete(DicTreeVO vo) throws Exception
    {
    	return factory.delete(vo);
    }
	
	public boolean deleteRoot(String pid) throws Exception
	{
		String sql = " DELETE FROM DIC_TREE WHERE ID=" + pid +" OR PARENT_ID = " + pid;
		return factory.update(sql, null);
	}
	
	public QuerySet checkCodeExist(String dicCode) throws Exception
	{
		String sql = " SELECT COUNT(1) FROM DIC_TREE WHERE DIC_CODE='" + dicCode +"'";
		QuerySet qs = factory.select(null, sql);
		return qs;
	}
    public BaseResultSet search(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres += " ORDER  BY ORDER_NUM ASC \n";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT ID, PARENT_ID, DIC_CODE, DIC_VALUE, DIC_VALUE_SPELL, DIC_VALUE_ASPELL, NVL(ORDER_NUM,0) ORDER_NUM, DIC_NAME_CODE \n ");
    	sql.append(" FROM DIC_TREE \n");
    	bs = factory.select(sql.toString(), page);
    	
    	return bs;
    }
    public BaseResultSet searchRoot(PageManager page, User user, String conditions) throws Exception
    {
    	//page.setPageRows(100);
    	String wheres = conditions;
    	wheres += " AND PARENT_ID = 0 ORDER  BY ID ASC \n";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT ID, PARENT_ID, DIC_CODE, DIC_VALUE, DIC_VALUE_SPELL, DIC_VALUE_ASPELL, NVL(ORDER_NUM,0) ORDER_NUM, DIC_NAME_CODE \n ");
    	sql.append(" FROM DIC_TREE \n");
    	bs = factory.select(sql.toString(), page);
    	
    	return bs;
    }
}