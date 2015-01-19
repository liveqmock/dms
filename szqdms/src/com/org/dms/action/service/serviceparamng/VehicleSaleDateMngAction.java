package com.org.dms.action.service.serviceparamng;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.serviceparamng.VehicleSaleDateMngDao;
import com.org.dms.vo.service.MainVehicleSdateLogVO;
import com.org.dms.vo.service.MainVehicleVO;
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
 * @description: 民车/军车销售日期更改审批方法
 * @throws Exception 设定文件
 * @auther fanpeng
 * @date 2014年8月27日 
 */
public class VehicleSaleDateMngAction
{
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private VehicleSaleDateMngDao dao = VehicleSaleDateMngDao.getInstance(atx);

    /**
     * 修改民车/军车销售日期
     * @throws Exception
     */
    public void update() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	MainVehicleVO tempVO = new MainVehicleVO();
    	MainVehicleSdateLogVO tempLogVO = new MainVehicleSdateLogVO();
        try
        {
            HashMap<String,String> hm;
            //将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			//将hashmap映射到vo对象中,完成匹配赋值
			
			String oldBuydate = hm.get("OLD_BUY_DATE");
			String buyDate = hm.get("BUY_DATE");	
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			
			tempVO.setValue(hm);
			tempVO.setUpdateTime(Pub.getCurrentDate());
			tempVO.setUpdateUser(user.getPersonName());
			
			//给历史表中插入数据
			tempLogVO.setValue(hm);
			tempLogVO.setNewSdate(sdf.parse(buyDate));
			tempLogVO.setOldSdate(sdf.parse(oldBuydate));
			tempLogVO.setCheckUser(user.getPersonName());
			tempLogVO.setCheckDate(Pub.getCurrentDate());
			tempLogVO.setCheckStatus(DicConstant.CLXSRQZT_02);
			
			//历史表							
			dao.insertVehicleSaleDateLog(tempLogVO);
			
			//原表
			dao.updateVehicleSaleDate(tempVO);
            //返回更新结果和成功信息
            atx.setOutMsg(tempVO.getRowXml(),"车辆销售日期更改成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
        }
    }
    
    /**
     * 销售日期更改审批
     * @throws Exception
     */
    public void check() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String checkStatus = Pub.val(request, "checkStatus");
        try
        {
        	MainVehicleSdateLogVO vo = new MainVehicleSdateLogVO();
			HashMap<String,String> hm;
			//将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			//将hashmap映射到vo对象中,完成匹配赋值
			vo.setValue(hm);
			//设置通用字段
			vo.setCheckUser(user.getPersonName());//设置审批人
			vo.setCheckDate(Pub.getCurrentDate());//设置审批时间
			vo.setCheckStatus(checkStatus);//设置审批状态为"审批通过/审批驳回"
			
			String vin = hm.get("VIN");//获得vin,用于更新车辆数据表销售日期
			String newSDate = hm.get("NEW_SDATE");//获取审批通过销售日期			
			
			//销售日期审批驳回
			if(checkStatus.equals(DicConstant.CLXSRQZT_03)){
				dao.checkNo(vo);
				atx.setOutMsg(vo.getRowXml(),"销售日期更改审批驳回！");
			}else{
				//销售日期申请通过
				//通过dao，执行插入
				dao.check(vo,vin,newSDate);
				//返回插入结果和成功信息
				atx.setOutMsg(vo.getRowXml(),"销售日期更改审批通过！");
        	}
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
        }
    }

    /**
     * 查询民车/军车销售日期
     * @throws Exception
     */
    public void search() throws Exception
    {
    	//定义request对象
	    RequestWrapper request = atx.getRequest();
	    //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String userType = Pub.val(request, "userType");
        //定义查询分页对象
		PageManager page = new PageManager();
		//将request流中的信息转化为where条件
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			//执行dao中search方法，BaseResultSet：结果集封装对象
			BaseResultSet bs = dao.search(page,user,conditions,userType);
			//输出结果集，第一个参数”bs”为固定名称，不可修改
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			atx.setException(e);
		}
	}
    
    /**
     * 查询所有销售日期更改申请
     * @throws Exception
     */
    public void searchCheck() throws Exception
    {
    	//定义request对象
	    RequestWrapper request = atx.getRequest();
	    //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String userType = Pub.val(request, "userType");
        //定义查询分页对象
		PageManager page = new PageManager();
		//将request流中的信息转化为where条件
		
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			//执行dao中search方法，BaseResultSet：结果集封装对象
			BaseResultSet bs = dao.searchCheck(page,user,conditions,userType);
			//输出结果集，第一个参数”bs”为固定名称，不可修改
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			atx.setException(e);
		}
	}
}