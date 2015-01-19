package com.org.dms.dao.service.basicinfomng;

import com.org.dms.vo.service.SeBaCodeVO;
import com.org.framework.base.*;
import com.org.framework.common.*;
import com.org.mvc.context.ActionContext;

/**
 * @Title: szqdms
 * @description: 基础代码管理类
 * @throws Exception 设定文件
 * @auther fanpeng
 * @date 2014年7月15日 
 */
public class BaCodeMngDao extends BaseDAO
{
    //定义instance
    public  static final BaCodeMngDao getInstance(ActionContext atx)
    {
        BaCodeMngDao dao = new BaCodeMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * 判断故障模式代码是否存在
     * @param code
     * @return
     * @throws Exception
     */
	public QuerySet checkCode(String code) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT COUNT(1) NUMS \n");
    	sql.append(" FROM SE_BA_CODE \n");
    	sql.append(" WHERE CODE = '" + code +"'");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	
	public boolean insertBaCode(SeBaCodeVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }
	
	public boolean updateBaCode(SeBaCodeVO vo) throws Exception
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
    	wheres += " ORDER　BY　CODE_TYPE" ;
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.CODE_TYPE,\n" );
        sql.append("       T.CODE,\n" );
        sql.append("       T.NAME,\n" );
        sql.append("       T.REMARKS,\n" );
        sql.append("       T.STATUS,\n" );
        sql.append("       T.SECRET_LEVEL,\n" );
        sql.append("       T.CODE_ID,\n" );
        sql.append("       T.CREATE_USER,\n" );
        sql.append("       T.CREATE_TIME,\n" );
        sql.append("       T.UPDATE_USER,\n" );
        sql.append("       T.UPDATE_TIME\n" );
        sql.append("  FROM SE_BA_CODE T");

    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	//绑定字典方法，将字典代码翻译为字典名称
		bs.setFieldDic("CODE_TYPE", "DMLB");
		bs.setFieldDic("STATUS", "YXBS");
		bs.setFieldDic("SECRET_LEVEL", "SJMJ");
		//设置日期字段显示格式
		bs.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	return bs;
    }
  
    /**
     * @title: updateBaCodeStatus 
     * @description: TODO(根据状态值更新用户状态标识：有效、无效) 
     * @param account
     * @param status
     * @return
     * @throws Exception    设定文件 
     * @return BaseResultSet    返回类型 
     * @auther fanpeng
     */
    /*
    public boolean updateBaCodeStatus(String codeId, String status) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" UPDATE SE_BA_CODE \n");
    	sql.append(" SET STATUS = '" + status + "' \n");
    	sql.append(" WHERE CODE_ID = '" + codeId + "' \n");
    	return factory.update(sql.toString(), null);
    }
    */
}