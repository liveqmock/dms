package com.org.dms.action.service.reportForms;

import java.util.ArrayList;
import java.util.List;

import com.org.dms.dao.service.reportForms.UnknownDetailMngDao;
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
 * @description: 售后报表——未知标识明细表
 * @throws Exception 设定文件
 * @auther fanpeng
 * @date 2014年10月30日 
 */
public class UnknownDetailMngAction
{
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private UnknownDetailMngDao dao = UnknownDetailMngDao.getInstance(atx);

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
    		hBean.setName("ONAME");
    		hBean.setTitle("服务站名称");
    		header.add(1,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("CLAIM_NO");
    		hBean.setTitle("索赔单号");
    		header.add(2,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("OLD_PART_NAME");
    		hBean.setTitle("零件名称");
    		header.add(3,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("OLD_PART_CODE");
    		hBean.setTitle("零件图号");
    		header.add(4,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("WORK_COSTS");
    		hBean.setTitle("工时费");
    		header.add(5,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("SEVEH_COSTS");
    		hBean.setTitle("服务车费");
    		header.add(6,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("TRAVEL_COSTS");
    		hBean.setTitle("差旅费");
    		header.add(7,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("MEALS_COSTS");
    		hBean.setTitle("在途餐补费");
    		header.add(8,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("MATERIAL_COSTS");
    		hBean.setTitle("材料费");
    		header.add(9,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("OTHER_COSTS");
    		hBean.setTitle("其他费用");
    		header.add(10,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("TOTAL_COST");
    		hBean.setTitle("合计费用");
    		header.add(11,hBean);
    		QuerySet qs = dao.download(conditions,user);
    		ExportManager.exportFile(response.getHttpResponse(), "未知标识明细表", header, qs);
        }
        catch (Exception e)
        {
        	atx.setException(e);
        }
    }
}