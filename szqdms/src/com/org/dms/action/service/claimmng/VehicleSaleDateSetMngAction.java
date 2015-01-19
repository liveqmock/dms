package com.org.dms.action.service.claimmng;

import java.util.*;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.claimmng.VehicleSaleDateSetMngDao;
import com.org.dms.vo.service.MainVehicleSdateLogVO;

import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;

import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * @Title: szqdms
 * @description: 车辆销售日期申请设置方法
 * @throws Exception 设定文件
 * @auther fanpeng
 * @date 2014年09月10日 
 */
public class VehicleSaleDateSetMngAction
{
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private VehicleSaleDateSetMngDao dao = VehicleSaleDateSetMngDao.getInstance(atx);

    /**
     * 销售日期更改申请
     * @throws Exception
     */
    public void apply() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	MainVehicleSdateLogVO vo = new MainVehicleSdateLogVO();
			HashMap<String,String> hm;
			//将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			//将hashmap映射到vo对象中,完成匹配赋值
			vo.setValue(hm);
			//设置通用字段
			vo.setApplyCompany(user.getPersonName());//设置申请单位
			vo.setApplyDate(Pub.getCurrentDate());//设置申请时间
			vo.setCheckStatus(DicConstant.CLXSRQZT_01);//设置审批状态为"未审批"
			//通过dao，执行插入
			dao.apply(vo);
			//返回插入结果和成功信息
			atx.setOutMsg(vo.getRowXml(),"销售日期更改申请成功，请上传附件！");
           
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
        }
    }

    /**
     * 服务商查询所有审批状态的记录
     * @throws Exception
     */
    public void searchDealer() throws Exception
    {
    	//定义request对象
	    RequestWrapper request = atx.getRequest();
	    //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
		PageManager page = new PageManager();
		//将request流中的信息转化为where条件
		
		//获取服务商名称
		String dealerName = user.getPersonName();
		
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			//执行dao中search方法，BaseResultSet：结果集封装对象
			BaseResultSet bs = dao.searchDealer(page,user,conditions,dealerName);
			//输出结果集，第一个参数”bs”为固定名称，不可修改
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			atx.setException(e);
		}
	}
    
    //弹出框查询VIN
    public void vinSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.vinSearch(page,user,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			atx.setException(e);
		}
	}
    
}