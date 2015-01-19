package com.org.dms.action.service.basicinfomng;

import java.util.HashMap;

import com.org.dms.dao.service.basicinfomng.OldpartWarehouseAreaSupplierMngDao;
import com.org.dms.vo.service.SeBaWarehouseSupplierVO;

import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * @Title: szqdms
 * @description: 供应商旧件库区管理方法
 * @throws Exception 设定文件
 * @auther fanpeng
 * @date 2014年8月25日 
 */
public class OldpartWarehouseAreaSupplierMngAction
{
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private OldpartWarehouseAreaSupplierMngDao dao = OldpartWarehouseAreaSupplierMngDao.getInstance(atx);

    /**
     * 批量新增供应商旧件库区
     * @throws Exception
     * @Author fanpeng 2014-08-25
     */
    public void batchInsert() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        String supplierIds = Pub.val(request, "supplierIds");
        
        try
        {
        	HashMap<String,String> hm;
 			hm = RequestUtil.getValues(request);
 			String areaId = hm.get("AREA_ID");
 			String areaCode = hm.get("AREA_CODE");
 			String areaName = hm.get("AREA_NAME");
 	 			
			dao.batchInsertWarehouseAreaSupplier(supplierIds, areaId, areaCode, areaName);
            
            atx.setOutMsg("","供应商旧件库区新增成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
        }
    }

    /**
     * 单个修改供应商旧件库区
     * @throws Exception
     * @Author fanpeng 2014-08-25
     */
    public void update() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        SeBaWarehouseSupplierVO tempVO = new SeBaWarehouseSupplierVO();
        try
        {
            HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			tempVO.setValue(hm);
			
            dao.updateWarehouseAreaSupplier(tempVO);
            atx.setOutMsg(tempVO.getRowXml(),"供应商旧件库区修改成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
        }
    }
    
    /**
     * 批量修改供应商旧件库区
     * @throws Exception
     * @Author fanpeng 2014-08-25
     */
    public void batchUpdate() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        String relationIds = Pub.val(request, "relationIds");
        
        try
        {
        	
            HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			
			String areaId = hm.get("AREA_ID");
			String areaCode = hm.get("AREA_CODE");
			String areaName = hm.get("AREA_NAME");
            dao.batchUpdateWarehouseAreaSupplier(relationIds,areaId,areaCode,areaName);
            atx.setOutMsg("","供应商旧件库区批量修改成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
        }
    }

    /**
     * 批量删除供应商旧件库区
     * @throws Exception
     * @Author fanpeng 2014-08-25
     */
    public void delete() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
        String relationIds = Pub.val(request, "relationIds");
        try
        {
            dao.batchDeleteWarehouseAreaSupplier(relationIds);
            atx.setOutMsg("","供应商旧件库区删除成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
        }
    }

    /**
     * 查询供应商旧件库区
     * @throws Exception
     * Author fanpeng 2014-08-25
     */
    public void search() throws Exception
    {
    	//定义request对象
	    RequestWrapper request = atx.getRequest();
        //定义查询分页对象
		PageManager page = new PageManager();
		//将request流中的信息转化为where条件
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.search(page,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			atx.setException(e);
		}
	}
    
    /**
     * 批量查询未绑定旧件库区的供应商
     * @throws Exception
     * Author fanpeng 2014-08-25
     */
    public void searchNewSupplier() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		
		try
		{
			BaseResultSet bs = dao.searchNewSupplier(page, conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			atx.setException(e);
		}
    }
    
    
}