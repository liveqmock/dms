package com.org.dms.action.service.workOrder;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.workOrder.WorkOrderMngDao;
import com.org.dms.vo.service.SeBuWorkOrderVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

public class WorkOrderMngAction {
	private Logger logger = com.org.framework.log.log.getLogger("WorkOrderMngAction");
    private ActionContext atx = ActionContext.getContext();
    private WorkOrderMngDao dao = WorkOrderMngDao.getInstance(atx);
    
    /**
     * 工单维护新增
     * @throws Exception      
     */
    public void workOrderInsert() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	SeBuWorkOrderVO vo = new SeBuWorkOrderVO();
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			String wokrOrderNo=dao.getNo();
			vo.setValue(hm);
			vo.setWorkNo(wokrOrderNo);
			vo.setOrgId(user.getOrgId());
			vo.setCreateTime(Pub.getCurrentDate());
			vo.setStatus(DicConstant.YXBS_01);
			vo.setCreateUser(user.getAccount());
			dao.insertWorkOrder(vo);
			atx.setOutMsg(vo.getRowXml(),"新增成功!");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * workOrderUpdate
     * 工单维护修改
     * @throws Exception
     */
    public void workOrderUpdate() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	try
    	{
    		SeBuWorkOrderVO vo = new SeBuWorkOrderVO();
    		HashMap<String,String> hm;
    		hm = RequestUtil.getValues(request);
    		vo.setValue(hm);
    		vo.setUpdateTime(Pub.getCurrentDate());
    		vo.setUpdateUser(user.getAccount());
    		dao.updateWorkOrder(vo);
    		atx.setOutMsg(vo.getRowXml(),"修改成功!");
    	}
    	catch (Exception e)
    	{
    		atx.setException(e);
    		logger.error(e);
    	}
    }
    /**
     * workOrderUpdate
     * 工单维护提报
     * @throws Exception
     */
    public void workOrderReport() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	String workId = Pub.val(request, "workId"); 
        String flag = Pub.val(request, "flag");//标示符  如果1直接提报，如果是2先保存再提报
    	try
    	{
    		SeBuWorkOrderVO vo = new SeBuWorkOrderVO();
    		if(flag.equals("1")){
    			vo.setWorkId(workId);
    		}else if(flag.equals("2")){
    			HashMap<String,String> hm;
        		hm = RequestUtil.getValues(request);
        		vo.setValue(hm);
    		};
    		vo.setUpdateTime(Pub.getCurrentDate());
    		vo.setUpdateUser(user.getAccount());
    		vo.setWorkStatus(DicConstant.PGDZT_01);
    		dao.updateWorkOrder(vo);
    		atx.setOutMsg("1","提报成功!");
    	}
    	catch (Exception e)
    	{
    		atx.setException(e);
    		logger.error(e);
    	}
    }
    /**
     * workOrderUpdate
     * 工单维护删除
     * @throws Exception
     */
    public void workOrderDelete() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	String workId = Pub.val(request, "workId");
    	try
    	{
    		dao.workOrderDelete(workId);
    		atx.setOutMsg("1","删除成功!");
    	}
    	catch (Exception e)
    	{
    		atx.setException(e);
    		logger.error(e);
    	}
    }
    /**
     * @title: search
     * @description: TODO(查询服务活动方法)
     * @throws Exception    设定文件
     * @return void    返回类型
     * @auther sunxuedong
     * @date 2014.09.25
     */
    public void search() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.search(page,user,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
}
