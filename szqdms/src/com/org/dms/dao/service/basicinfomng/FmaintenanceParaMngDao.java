package com.org.dms.dao.service.basicinfomng;

import com.org.dms.vo.service.SeBaFmaintenanceParaVO;
import com.org.framework.base.*;
import com.org.framework.common.*;
import com.org.mvc.context.ActionContext;

/**
 * @Title: szqdms
 * @description: 首保参数管理类
 * @throws Exception 设定文件
 * @auther fanpeng
 * @date 2014年7月21日 
 */
public class FmaintenanceParaMngDao extends BaseDAO
{
    //定义instance
    public  static final FmaintenanceParaMngDao getInstance(ActionContext atx)
    {
        FmaintenanceParaMngDao dao = new FmaintenanceParaMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * 判断首保参数记录是否存在
     * @param status
     * @return
     * @throws Exception
     */
	public QuerySet checkRowNumber() throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT COUNT(1) NUMS \n");
    	sql.append(" FROM SE_BA_FMAINTENANCE_PARA \n");
    	sql.append(" WHERE 1 = 1"); 
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	
	public boolean insertFmaintenancePara(SeBaFmaintenanceParaVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }
	
	public boolean updateFmaintenancePara(SeBaFmaintenanceParaVO vo) throws Exception
    {             
    	return factory.update(vo);
    }

	/**
	 * 首保参数查询方法
	 * @param  page
	 * @param  code
	 * @param  conditions
	 * @return
	 * @throws Exception
	 */
    public BaseResultSet search(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.PARA_ID,\n" );
    	sql.append("       T.ENGINE,\n" );
    	sql.append("       T.GEARBOX,\n" );
    	sql.append("       T.BRIDGE,\n" );
    	sql.append("       T.CREATE_USER,\n" );
    	sql.append("       T.CREATE_TIME,\n" );
    	sql.append("       T.UPDATE_USER,\n" );
    	sql.append("       T.UPDATE_TIME,\n" );
    	sql.append("       T.STATUS,\n" );
    	sql.append("       T.SECRET_LEVEL\n" );
    	sql.append("  FROM SE_BA_FMAINTENANCE_PARA T");
     
    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	//绑定字典方法，将字典代码翻译为字典名称
		bs.setFieldDic("STATUS", "YXBS");
		bs.setFieldDic("SECRET_LEVEL", "SJMJ");
		//设置日期字段显示格式
		bs.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	return bs;
    }
}