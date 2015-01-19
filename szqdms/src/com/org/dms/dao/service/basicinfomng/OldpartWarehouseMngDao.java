package com.org.dms.dao.service.basicinfomng;

import com.org.dms.vo.service.SeBaWarehouseVO;
import com.org.framework.base.*;
import com.org.framework.common.*;
import com.org.mvc.context.ActionContext;

/**
 * @Title: szqdms
 * @description: 旧件仓库管理类
 * @throws Exception 设定文件
 * @auther fanpeng
 * @date 2014年8月18日 
 */
public class OldpartWarehouseMngDao extends BaseDAO
{
    //定义instance
    public  static final OldpartWarehouseMngDao getInstance(ActionContext atx)
    {
        OldpartWarehouseMngDao dao = new OldpartWarehouseMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * 判断旧件仓库代码是否存在
     * @param code
     * @return
     * @throws Exception
     */
	public QuerySet checkCode(String code) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT COUNT(1) NUMS \n");
    	sql.append(" FROM SE_BA_WAREHOUSE \n");
    	sql.append(" WHERE WAREHOUSE_CODE = '" + code +"'");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	
	public boolean insertOldpartWarehouse(SeBaWarehouseVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }
	
	public boolean updateOldpartWarehouse(SeBaWarehouseVO vo) throws Exception
    {
    	return factory.update(vo);
    }

	/**
	 * 查询方法
	 * @param  page
	 * @param  code
	 * @param  conditions
	 * @return
	 * @throws Exception
	 */
    public BaseResultSet search(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres += " ORDER　BY　WAREHOUSE_TYPE" ;
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.WAREHOUSE_ID,\n" );
    	sql.append("       T.WAREHOUSE_CODE,\n" );
    	sql.append("       T.WAREHOUSE_NAME,\n" );
    	sql.append("       T.WAREHOUSE_TYPE,\n" );
    	sql.append("       T.STATUS,\n" );
    	sql.append("       T.CREATE_USER,\n" );
    	sql.append("       T.CREATE_TIME,\n" );
    	sql.append("       T.UPDATE_USER,\n" );
    	sql.append("       T.UPDATE_TIME\n" );
    	sql.append("  FROM SE_BA_WAREHOUSE T");

    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	//绑定字典方法，将字典代码翻译为字典名称
		bs.setFieldDic("WAREHOUSE_TYPE", "JJKCLX");
		bs.setFieldDic("STATUS", "YXBS");
		//bs.setFieldDic("SECRET_LEVEL", "SJMJ");
		//设置日期字段显示格式
		bs.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	return bs;
    }
}