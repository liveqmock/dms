package com.org.dms.action.service.reportForms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import com.org.dms.dao.service.reportForms.EachVehicleServiceCostMngDao;
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
 * @description: 售后报表——单车服务费统计表
 * @throws Exception 设定文件
 * @auther fanpeng
 * @date 2014年1月05日 
 */
public class EachVehicleServiceCostMngAction
{
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private EachVehicleServiceCostMngDao dao = EachVehicleServiceCostMngDao.getInstance(atx);

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
    		hBean.setName("TOTAL_SALENUMBER");
    		hBean.setTitle("当月销售车辆数");
    		header.add(3,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("ONE");
    		hBean.setTitle("一月");
    		header.add(4,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("TWO");
    		hBean.setTitle("二月");
    		header.add(5,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("THREE");
    		hBean.setTitle("三月");
    		header.add(6,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("FOUR");
    		hBean.setTitle("四月");
    		header.add(7,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("FIVE");
    		hBean.setTitle("五月");
    		header.add(8,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("SIX");
    		hBean.setTitle("六月");
    		header.add(9,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("TOTLECOST");
    		hBean.setTitle("合计费用");
    		header.add(10,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("DCWXCB");
    		hBean.setTitle("单车维修成本");
    		header.add(11,hBean);
    		QuerySet qs = dao.download(conditions,user,hm);
    		ExportManager.exportFile(response.getHttpResponse(), "单车服务费统计表", header, qs);
        }
        catch (Exception e)
        {
        	atx.setException(e);
        }
    }
}