package com.org.dms.action.service.reportForms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import com.org.dms.dao.service.reportForms.PrimaryFaultMngDao;
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
 * @description: 售后报表——初期故障率统计表
 * @throws Exception 设定文件
 * @auther fanpeng
 * @date 2014年1月05日 
 */
public class PrimaryFaultMngAction
{
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private PrimaryFaultMngDao dao = PrimaryFaultMngDao.getInstance(atx);

    /**
     * 报表查询
     * @throws Exception
     */
    public void search() throws Exception
    {
    	//定义request对象
	    RequestWrapper request = atx.getRequest();
	    //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
		PageManager page = new PageManager();
		
		HashMap<String,String> hm;
		//将request流转换为hashmap结构体
		hm = RequestUtil.getValues(request);
		
		//将request流中的信息转化为where条件
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			//执行dao中search方法，BaseResultSet：结果集封装对象
			BaseResultSet bs = dao.search(page,user,conditions,hm);
			//输出结果集，第一个参数”bs”为固定名称，不可修改
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			atx.setException(e);
		}
	}
    
    /**
     * 下载（导出）
     * @throws Exception
     */
    public void download()throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	ResponseWrapper response= atx.getResponse();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	PageManager page = new PageManager();
    	String conditions = RequestUtil.getConditionsWhere(request,page);
    	
		HashMap<String,String> hm;
		//将request流转换为hashmap结构体
		hm = RequestUtil.getValues(request);
        try
        {
        	List<HeaderBean> header = new ArrayList<HeaderBean>();
	    	HeaderBean hBean = null;
	    	hBean = new HeaderBean();
    		hBean.setName("ROWNUM");
    		hBean.setTitle("序号");
    		header.add(0,hBean);
	    	hBean = new HeaderBean();
    		hBean.setName("YEARMONTH");
    		hBean.setTitle("购车月份");
    		header.add(1,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("CX");
    		hBean.setTitle("车型");
    		header.add(2,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("FAULTNUMBER");
    		hBean.setTitle("直接故障项次");
    		header.add(3,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("TOTAL_SALENUMBER");
    		hBean.setTitle("销售车辆数");
    		header.add(4,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("GZL");
    		hBean.setTitle("初期故障率");
    		header.add(5,hBean);
    		QuerySet qs = dao.download(conditions,user,hm);
    		ExportManager.exportFile(response.getHttpResponse(), "初期故障率统计表", header, qs);
        }
        catch (Exception e)
        {
        	atx.setException(e);
        }
    }
}