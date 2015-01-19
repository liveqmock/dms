package com.org.dms.action.service.basicinfomng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.org.dms.dao.service.basicinfomng.PartStreamSetMngDao;
import com.org.dms.vo.service.PtBaPartSupplierRlExtendsVO;
import com.org.dms.vo.service.PtBaPartSupplierRlVO;


import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.fileimport.ExportManager;
import com.org.framework.fileimport.HeaderBean;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;

import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;

/**
 * @Title: szqdms
 * @description: 配件流水号设置方法
 * @throws Exception 设定文件
 * @auther fanpeng
 * @date 2014年7月24日 
 */
public class PartStreamSetMngAction
{
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private PartStreamSetMngDao dao = PartStreamSetMngDao.getInstance(atx);

    /**
     * 设置配件流水号
     * @throws Exception
     */
    public void set() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	String ifStream = Pub.val(request, "ifStream");
        PtBaPartSupplierRlVO tempVO = new PtBaPartSupplierRlVO();
        PtBaPartSupplierRlExtendsVO extendsTempVO = new PtBaPartSupplierRlExtendsVO();//继承后的VO
        try
        {
            HashMap<String,String> hm;
            //将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			//将hashmap映射到vo对象中,完成匹配赋值
			tempVO.setValue(hm);
			tempVO.setIfStream(ifStream);
			
			extendsTempVO.setValue(hm);
			extendsTempVO.setIfStream(ifStream);
						
			//通过dao，执行更新
            dao.setStream(tempVO);
            //返回更新结果和成功信息
            atx.setOutMsg(extendsTempVO.getRowXml(),"配件流水号设置成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
        }
    }

    /**
     * 查询配件流水号
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
     * 批量设置流水号方法
     * @throws Exception
     */
    public void batchSet() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        String relationIds = Pub.val(request, "relationIds");
        String ifStream = Pub.val(request, "ifStream");
        
        try
        {	
            dao.batchSet(relationIds,ifStream);
            atx.setOutMsg("","配件流水号批量设置成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
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
    		hBean.setName("PART_CODE");
    		hBean.setTitle("配件代码");
    		header.add(1,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("PART_NAME");
    		hBean.setTitle("配件名称");
    		header.add(2,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("SUPPLIER_CODE");
    		hBean.setTitle("供应商代码");
    		header.add(3,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("SUPPLIER_NAME");
    		hBean.setTitle("供应商名称");
    		header.add(4,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("DIC_VALUE");
    		hBean.setTitle("是否设置流水号");
    		header.add(5,hBean);
    		QuerySet qs = dao.download(conditions,user);
    		ExportManager.exportFile(response.getHttpResponse(), "配件流水号设置明细", header, qs);
        }
        catch (Exception e)
        {
        	atx.setException(e);
        }
    }
}