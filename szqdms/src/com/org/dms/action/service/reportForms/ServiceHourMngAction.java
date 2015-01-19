package com.org.dms.action.service.reportForms;

import java.util.ArrayList;
import java.util.List;

import com.org.dms.dao.service.reportForms.ServiceHourMngDao;

import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.fileimport.ExportManager;
import com.org.framework.fileimport.HeaderBean;
import com.org.framework.util.RequestUtil;

import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;

/**
 * @Title: szqdms
 * @description: 售后报表——服务维修响应时间（服务站/办事处）统计表
 * @throws Exception 设定文件
 * @auther fanpeng
 * @date 2014年12月15日 
 */
public class ServiceHourMngAction
{
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private ServiceHourMngDao dao = ServiceHourMngDao.getInstance(atx);

    /**
     * 报表查询（按服务站）
     * @throws Exception
     */
    public void search_fwz() throws Exception
    {
    	//定义request对象
	    RequestWrapper request = atx.getRequest();
	    //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
		PageManager page = new PageManager();
		//将request流中的信息转化为where条件
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			//执行dao中search方法，BaseResultSet：结果集封装对象
			BaseResultSet bs = dao.search_fwz(page,user,conditions);
			//输出结果集，第一个参数”bs”为固定名称，不可修改
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			atx.setException(e);
		}
	}
    
    /**
     * 报表查询（按办事处）
     * @throws Exception
     */
    public void search_bsc() throws Exception
    {
    	//定义request对象
	    RequestWrapper request = atx.getRequest();
	    //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
		PageManager page = new PageManager();
		//将request流中的信息转化为where条件
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			//执行dao中search方法，BaseResultSet：结果集封装对象
			BaseResultSet bs = dao.search_bsc(page,user,conditions);
			//输出结果集，第一个参数”bs”为固定名称，不可修改
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			atx.setException(e);
		}
	}
    
    /**
     * 下载（导出）服务站
     * @throws Exception
     */
    public void download_fwz()throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	ResponseWrapper response= atx.getResponse();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	PageManager page = new PageManager();
    	String conditions = RequestUtil.getConditionsWhere(request,page);
        try
        {
        	List<HeaderBean> header = new ArrayList<HeaderBean>();
	    	HeaderBean hBean = null;
	    	hBean = new HeaderBean();
    		hBean.setName("ROWNUM");
    		hBean.setTitle("序号");
    		header.add(0,hBean);
	    	hBean = new HeaderBean();
    		hBean.setName("BSC");
    		hBean.setTitle("办事处");
    		header.add(1,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("DEALER");
    		hBean.setTitle("渠道商");
    		header.add(2,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("YEARMONTH");
    		hBean.setTitle("月份");
    		header.add(3,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("HOUR_OUT");
    		hBean.setTitle("维修响应时间（站外）");
    		header.add(4,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("MONTH_GROUTH_OUT");
    		hBean.setTitle("环比（站外）");
    		header.add(5,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("YEAR_GROUTH_OUT");
    		hBean.setTitle("同比（站外）");
    		header.add(6,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("HOUR_IN");
    		hBean.setTitle("维修响应时间（站内）");
    		header.add(7,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("MONTH_GROUTH_IN");
    		hBean.setTitle("环比（站内）");
    		header.add(8,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("YEAR_GROUTH_IN");
    		hBean.setTitle("同比（站内）");
    		header.add(9,hBean);
    		QuerySet qs = dao.download_fwz(conditions,user);
    		ExportManager.exportFile(response.getHttpResponse(), "服务站维修响应时间统计表", header, qs);
        }
        catch (Exception e)
        {
        	atx.setException(e);
        }
    }
    
    /**
     * 下载（导出）办事处
     * @throws Exception
     */
    public void download_bsc()throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	ResponseWrapper response= atx.getResponse();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	PageManager page = new PageManager();
    	String conditions = RequestUtil.getConditionsWhere(request,page);
        try
        {
        	List<HeaderBean> header = new ArrayList<HeaderBean>();
	    	HeaderBean hBean = null;
	    	hBean = new HeaderBean();
    		hBean.setName("ROWNUM");
    		hBean.setTitle("序号");
    		header.add(0,hBean);
	    	hBean = new HeaderBean();
    		hBean.setName("BSC");
    		hBean.setTitle("办事处");
    		header.add(1,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("YEARMONTH");
    		hBean.setTitle("月份");
    		header.add(2,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("HOUR_OUT");
    		hBean.setTitle("维修响应时间（站外）");
    		header.add(3,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("MONTH_GROUTH_OUT");
    		hBean.setTitle("环比（站外）");
    		header.add(4,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("YEAR_GROUTH_OUT");
    		hBean.setTitle("同比（站外）");
    		header.add(5,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("HOUR_IN");
    		hBean.setTitle("维修响应时间（站内）");
    		header.add(6,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("MONTH_GROUTH_IN");
    		hBean.setTitle("环比（站内）");
    		header.add(7,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("YEAR_GROUTH_IN");
    		hBean.setTitle("同比（站内）");
    		header.add(8,hBean);
    		QuerySet qs = dao.download_bsc(conditions,user);
    		ExportManager.exportFile(response.getHttpResponse(), "办事处维修响应时间统计表", header, qs);
        }
        catch (Exception e)
        {
        	atx.setException(e);
        }
    }
}