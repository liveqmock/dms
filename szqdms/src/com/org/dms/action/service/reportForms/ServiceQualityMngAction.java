package com.org.dms.action.service.reportForms;

import java.util.ArrayList;
import java.util.List;

import com.org.dms.dao.service.reportForms.ServiceQualityMngDao;

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
 * @description: 售后报表——售后质量信息
 * @throws Exception 设定文件
 * @auther fanpeng
 * @date 2014年10月29日 
 */
public class ServiceQualityMngAction
{
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private ServiceQualityMngDao dao = ServiceQualityMngDao.getInstance(atx);

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
		//将request流中的信息转化为where条件
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			//执行dao中search方法，BaseResultSet：结果集封装对象
			BaseResultSet bs = dao.search(page,user,conditions);
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
        try
        {
        	List<HeaderBean> header = new ArrayList<HeaderBean>();
	    	HeaderBean hBean = null;
	    	hBean = new HeaderBean();
    		hBean.setName("ROWNUM");
    		hBean.setTitle("序号");
    		header.add(0,hBean);
	    	hBean = new HeaderBean();
    		hBean.setName("SUPPLIER_NAME");
    		hBean.setTitle("供应商名称");
    		header.add(1,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("ONAME");
    		hBean.setTitle("服务站名称");
    		header.add(2,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("CLAIM_NO");
    		hBean.setTitle("索赔单号");
    		header.add(3,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("VIN");
    		hBean.setTitle("底盘号");
    		header.add(4,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("MODELS_CODE");
    		hBean.setTitle("车辆型号");
    		header.add(5,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("BUY_DATE");
    		hBean.setTitle("购车日期");
    		header.add(6,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("FAULT_DATE");
    		hBean.setTitle("故障日期");
    		header.add(7,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("MILEAGE");
    		hBean.setTitle("行驶里程");
    		header.add(8,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("OLD_PART_NAME");
    		hBean.setTitle("零件名称");
    		header.add(9,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("OLD_PART_CODE");
    		hBean.setTitle("零件图号");
    		header.add(10,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("DIC_VALUE");
    		hBean.setTitle("处理措施");
    		header.add(11,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("PRICE");
    		hBean.setTitle("报单追偿总额");
    		header.add(12,hBean);
    		QuerySet qs = dao.download(conditions,user);
    		ExportManager.exportFile(response.getHttpResponse(), "售后质量信息报表", header, qs);
        }
        catch (Exception e)
        {
        	atx.setException(e);
        }
    }
}