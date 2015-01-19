package com.org.dms.action.service.basicinfomng;

import java.util.*;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.basicinfomng.VehiclePositionMngDao;
import com.org.dms.vo.service.SeBaVehiclePositionVO;

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
 * @description: 车辆部位方法
 * @throws Exception 设定文件
 * @auther fanpeng
 * @date 2014年09月05日 
 */
public class VehiclePositionMngAction
{
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private VehiclePositionMngDao dao = VehiclePositionMngDao.getInstance(atx);

    /**
     * 新增车辆部位
     * @throws Exception
     */
    public void insert() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	SeBaVehiclePositionVO vo = new SeBaVehiclePositionVO();
			HashMap<String,String> hm;
			//将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			//将hashmap映射到vo对象中,完成匹配赋值
			vo.setValue(hm);
			
			String positionLevelNew=hm.get("POSITION_LEVEL_NEW");
			int positionLevel = Integer.parseInt(positionLevelNew)+1;//计算车辆部位级别
			
			//判断基础代码是否已存在
			QuerySet qs = dao.checkCode(vo.getPositionCode());
			if(qs.getRowCount() > 0)
			{
				String n = qs.getString(1, 1);
				if(Integer.parseInt(n) > 0)
				{
					throw new Exception("车辆部位代码已存在，保存失败！");
				}
			}
			//设置通用字段
			vo.setCreateUser(user.getPersonName());
			vo.setCreateTime(Pub.getCurrentDate());
			vo.setPositionLevel(positionLevel+"");
			//通过dao，执行插入
			dao.insertVehiclePosition(vo);
			//返回插入结果和成功信息
			atx.setOutMsg(vo.getRowXml(),"车辆部位代码新增成功！");      
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
        }
    }

    /**
     * 更新车辆部位
     * @throws Exception
     */
    public void update() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	SeBaVehiclePositionVO tempVO = new SeBaVehiclePositionVO();
        try
        {
            HashMap<String,String> hm;
            //将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			//将hashmap映射到vo对象中,完成匹配赋值
			tempVO.setValue(hm);
			
			String positionLevelNew=hm.get("POSITION_LEVEL_NEW");
			
			//判断是否修改总成级别，若无修改总成级别不变
			if(positionLevelNew!=""){
				int positionLevel = Integer.parseInt(positionLevelNew)+1;//计算车辆部位级别
				tempVO.setPositionLevel(positionLevel+"");
			}
			
			//通过dao，执行更新
			tempVO.setUpdateTime(Pub.getCurrentDate());
			tempVO.setUpdateUser(user.getPersonName());
            dao.updateVehiclePosition(tempVO);
            //返回更新结果和成功信息
            atx.setOutMsg(tempVO.getRowXml(),"车辆部位信息修改成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
        }
    }

    /**
     * 删除车辆部位
     * @throws Exception
     */
    public void delete() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //通过request获取页面传递的参数，对于null值通过该方法将转换为""
        String positionId = Pub.val(request, "positionId");
        
        SeBaVehiclePositionVO tempVO = new SeBaVehiclePositionVO();
		tempVO.setPositionId(positionId);
		tempVO.setUpdateTime(Pub.getCurrentDate());
		tempVO.setUpdateUser(user.getPersonName());
		tempVO.setStatus(DicConstant.YXBS_02);
        try
        {
                dao.updateVehiclePosition(tempVO);
                //返回更新结果和成功信息
                atx.setOutMsg("","车辆部位信息删除成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
        }
    }

    /**
     * 查询车辆部位
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
    
    //弹出框查询父级总成
    public void pNameSearch() throws Exception
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
    		hBean.setName("P_NAME");
    		hBean.setTitle("父级总成");
    		header.add(1,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("POSITION_LEVEL");
    		hBean.setTitle("级别");
    		header.add(2,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("POSITION_CODE");
    		hBean.setTitle("车辆部位代码");
    		header.add(3,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("POSITION_NAME");
    		hBean.setTitle("车辆部位名称");
    		header.add(4,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("DIC_VALUE");
    		hBean.setTitle("状态");
    		header.add(5,hBean);
    		QuerySet qs = dao.download(conditions,user);
    		ExportManager.exportFile(response.getHttpResponse(), "车辆部位明细", header, qs);
        }
        catch (Exception e)
        {
        	atx.setException(e);
        }
    }
}