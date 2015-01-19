package com.org.dms.dao.service.noticeManage;

import com.org.dms.vo.service.MainProblemFeedbackVO;
import com.org.framework.base.*;
import com.org.framework.common.*;
import com.org.mvc.context.ActionContext;

/**
 * @Title: szqdms
 * @description: 渠道商反馈问题管理类
 * @throws Exception 设定文件
 * @auther fanpeng
 * @date 2014年11月12日 
 */
public class ProblemMngDao extends BaseDAO
{
    //定义instance
    public  static final ProblemMngDao getInstance(ActionContext atx)
    {
        ProblemMngDao dao = new ProblemMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	
	public boolean ask(MainProblemFeedbackVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }
	
	public boolean answer(MainProblemFeedbackVO vo)
    throws Exception
    {
		return factory.update(vo);
    }

	/**
	 * 渠道商查询方法
	 * @param  page
	 * @param  code
	 * @param  conditions
	 * @return
	 * @throws Exception
	 */
    public BaseResultSet dealerSearch(PageManager page, User user, String conditions, String dealerName) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND　APPLY_USER ='" + dealerName +"'" ;
    	wheres += " ORDER　BY　PROBLEM_STATUS,APPLY_DATE" ;
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT PROBLEM_ID,\n" );
        sql.append("       PROBLEM_DESCRIBE,\n" );
        sql.append("       APPLY_USER,\n" );
        sql.append("       APPLY_DATE,\n" );
        sql.append("       FEEDBACK_USER,\n" );
        sql.append("       FEEDBACK_DATE,\n" );
        sql.append("       PROBLEM_STATUS,\n" );
        sql.append("       PROBLEM_CODE,\n" );
        sql.append("       PROBLEM_NAME,\n" );
        sql.append("       FEEDBACK_REMARKS\n" );
        sql.append("  FROM MAIN_PROBLEM_FEEDBACK");

    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	//绑定字典方法，将字典代码翻译为字典名称
		bs.setFieldDic("PROBLEM_STATUS", "WTHFZT");
		//设置日期字段显示格式
		bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("FEEDBACK_DATE", "yyyy-MM-dd");
    	return bs;
    }
    
	/**
	 * 本部查询方法
	 * @param  page
	 * @param  code
	 * @param  conditions
	 * @return
	 * @throws Exception
	 */
    public BaseResultSet oemSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres += " ORDER　BY　PROBLEM_STATUS,APPLY_DATE" ;
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT PROBLEM_ID,\n" );
        sql.append("       PROBLEM_DESCRIBE,\n" );
        sql.append("       APPLY_USER,\n" );
        sql.append("       APPLY_DATE,\n" );
        sql.append("       FEEDBACK_USER,\n" );
        sql.append("       FEEDBACK_DATE,\n" );
        sql.append("       PROBLEM_STATUS,\n" );
        sql.append("       PROBLEM_CODE,\n" );
        sql.append("       PROBLEM_NAME,\n" );
        sql.append("       FEEDBACK_REMARKS\n" );
        sql.append("  FROM MAIN_PROBLEM_FEEDBACK");

    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	//绑定字典方法，将字典代码翻译为字典名称
		bs.setFieldDic("PROBLEM_STATUS", "WTHFZT");
		//设置日期字段显示格式
		bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("FEEDBACK_DATE", "yyyy-MM-dd");
    	return bs;
    }
}