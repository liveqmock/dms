package com.org.dms.dao.service.noticeManage;

import com.org.dms.vo.service.MainProblemTypeVO;
import com.org.framework.base.*;
import com.org.framework.common.*;
import com.org.mvc.context.ActionContext;

/**
 * @Title: szqdms
 * @description: 问题类型管理类
 * @throws Exception 设定文件
 * @auther fanpeng
 * @date 2014年11月11日 
 */
public class ProblemTypeMngDao extends BaseDAO
{
    //定义instance
    public  static final ProblemTypeMngDao getInstance(ActionContext atx)
    {
        ProblemTypeMngDao dao = new ProblemTypeMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * 判断问题类型代码是否存在
     * @param code
     * @return
     * @throws Exception
     */
	public QuerySet checkCode(String code) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT COUNT(1) NUMS \n");
    	sql.append(" FROM MAIN_PROBLEM_TYPE \n");
    	sql.append(" WHERE PROBLEM_CODE = '" + code +"'");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	
	public boolean insertProblemType(MainProblemTypeVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }
	
	public boolean updateProblemType(MainProblemTypeVO vo) throws Exception
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
    	wheres += " ORDER　BY　T.PROBLEM_CODE" ;
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.ID,\n" );
        sql.append("       T.PROBLEM_CODE,\n" );
        sql.append("       T.PROBLEM_NAME,\n" );
        sql.append("       T.TYPE_STATUS,\n" );
        sql.append("       T.REMARKS,\n" );
        sql.append("       T.CREATE_USER,\n" );
        sql.append("       T.CREATE_TIME,\n" );
        sql.append("       T.UPDATE_USER,\n" );
        sql.append("       T.UPDATE_TIME\n" );
        sql.append("  FROM MAIN_PROBLEM_TYPE T");

    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	//绑定字典方法，将字典代码翻译为字典名称
		bs.setFieldDic("TYPE_STATUS", "YXBS");
		//设置日期字段显示格式
		bs.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
		bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	return bs;
    }
}