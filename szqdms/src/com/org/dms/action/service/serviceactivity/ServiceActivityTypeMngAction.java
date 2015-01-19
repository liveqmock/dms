package com.org.dms.action.service.serviceactivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.serviceactivity.ServiceActivityTypeMngDao;
import com.org.dms.vo.service.SeBuActivityModelsVO;
import com.org.dms.vo.service.SeBuActivityPartVO;
import com.org.dms.vo.service.SeBuActivityVO;
import com.org.dms.vo.service.SeBuActivityVehageVO;
import com.org.dms.vo.service.SeBuActivityVehicleVO;
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
 * 服务活动管理action
 */
public class ServiceActivityTypeMngAction
{
    private Logger logger = com.org.framework.log.log.getLogger("ServiceActivityTypeMngAction");
    private ActionContext atx = ActionContext.getContext();
    private ServiceActivityTypeMngDao dao = ServiceActivityTypeMngDao.getInstance(atx);

    /**
     * 新增服务活动
     * @throws Exception      
     */
    public void insert() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	SeBuActivityVO vo = new SeBuActivityVO();
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			vo.setValue(hm);
			String ifFixcosts=hm.get("IF_FIXCOSTS");
			String ActivityCode=dao.getActivityCode();
			vo.setActivityCode(ActivityCode);
			vo.setCreateUser(user.getAccount());
			vo.setCreateTime(Pub.getCurrentDate());
			vo.setActivityStatus(DicConstant.HDZT_01);
			vo.setCompanyId(user.getCompanyId());
			vo.setOemCompanyId(user.getOemCompanyId());
			vo.setOrgId(user.getOrgId());
			vo.setStatus(DicConstant.YXBS_01);
			dao.insertServiceActivity(vo);
			
			String activityId=vo.getActivityId();
			SeBuActivityVehageVO voVe=new SeBuActivityVehageVO();
			voVe.setActivityId(activityId);
			voVe.setVehageType(DicConstant.RQLX_01);
			dao.insertVehage(voVe);
			SeBuActivityVehageVO voVe1=new SeBuActivityVehageVO();
			voVe1.setActivityId(activityId);
			voVe1.setVehageType(DicConstant.RQLX_02);
			dao.insertVehage(voVe1);
			if(ifFixcosts.equals(DicConstant.SF_02)){
				atx.setOutMsg(vo.getRowXml(),"新增成功,建议下一步维护工时与零件信息！");
			}else{
				atx.setOutMsg(vo.getRowXml(),"服务活动新增成功！");
			}
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 新增活动配件
     *
     * @throws Exception
     */
    public void insertParts() throws Exception {
            //获取封装后的request对象                      
            RequestWrapper request = atx.getRequest();
            //获取封装后的response对象
            //ResponseWrapper response = atx.getResponse();
            //获取当前登录user对象
            User user = (User) atx.getSession().get(Globals.USER_KEY);
            try {
                HashMap<String, String> hm;
                //将request流转换为hashmap结构体
                hm = RequestUtil.getValues(request);
                String activityId=hm.get("ACTIVITYID");
    			String activityCode=hm.get("ACTIVITYCODE");
    			String activityName=hm.get("ACTIVITYNAME");
                String partIds = hm.get("PARTIDS");//配件ID(逗号分隔)
                String partCodes = hm.get("PARTCODES");//促销配件代码(逗号分隔)
                String partNames = hm.get("PARTNAMES");//促销配件名称(逗号分隔)
                String quantity = hm.get("QUANTITY");//促销配件促销价(逗号分隔)
                String[] partIdArr = partIds.split(",");
                String[] partCodeArr = partCodes.split(",");
                String[] partNameArr = partNames.split(",");
                String[] quantityArr= quantity.split(",");
                for (int i = 0; i < partIdArr.length; i++) {
                    SeBuActivityPartVO vo = new SeBuActivityPartVO();
                    vo.setActivityId(activityId);
                    vo.setActivityCode(activityCode);
                    vo.setActivityName(activityName);
                    vo.setPartId(partIdArr[i]);
                    vo.setPartCode(partCodeArr[i]);
                    vo.setPartName(partNameArr[i]);
                    vo.setQuantity(quantityArr[i]);
                    vo.setCreateTime(Pub.getCurrentDate());
                    vo.setCreateUser(user.getAccount());
                    dao.insertParts(vo);
                }
                //返回插入结果和成功信息
                atx.setOutMsg("", "新增成功！");
            } catch (Exception e) {
                atx.setException(e);
                logger.error(e);
            }
        }
    /**
     * 新增vin信息
     */
    public void insertVINs() throws Exception {
            //获取封装后的request对象                      insertPros
            RequestWrapper request = atx.getRequest();
            //获取封装后的response对象
            //ResponseWrapper response = atx.getResponse();
            //获取当前登录user对象
            User user = (User) atx.getSession().get(Globals.USER_KEY);
            try {
                HashMap<String, String> hm;
                //将request流转换为hashmap结构体
                hm = RequestUtil.getValues(request);
                String activityId=hm.get("ACTIVITYID");
    			String activityCode=hm.get("ACTIVITYCODE");
    			String activityName=hm.get("ACTIVITYNAME");
                String vehicleId = hm.get("VEHICLE_ID");//配件ID(逗号分隔)
                String vin = hm.get("VIN");//促销配件代码(逗号分隔)
                String[] vinArr = vin.split(",");
                String[] vehicleIdArr = vehicleId.split(",");
                for (int i = 0; i < vinArr.length; i++) {
                	SeBuActivityVehicleVO vo = new SeBuActivityVehicleVO();
                    vo.setActivityId(activityId);
                    vo.setActivityCode(activityCode);
                    vo.setActivityName(activityName);
                    vo.setRegistStatus(DicConstant.CLDJZT_01);
                    vo.setStatus(DicConstant.YXBS_01);
                    vo.setClaimUser(DicConstant.SF_02);
                    vo.setVin(vinArr[i]);
                    vo.setVehicleId(vehicleIdArr[i]);
                    vo.setCreateTime(Pub.getCurrentDate());
                    vo.setCreateUser(user.getAccount());
                    dao.insertVIN(vo);
                }
                //返回插入结果和成功信息
                atx.setOutMsg("", "新增成功！");
            } catch (Exception e) {
                atx.setException(e);
                logger.error(e);
            }
        }
    /**
     * 新增服务活动车型
     * @throws Exception
     */
    public void insertModels() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	
        	SeBuActivityModelsVO vo = new SeBuActivityModelsVO();
        	HashMap<String, String> hm;
            //将request流转换为hashmap结构体
            hm = RequestUtil.getValues(request);
            String activityId=hm.get("ACTIVITYID");
			String activityCode=hm.get("ACTIVITYCODE");
			String activityName=hm.get("ACTIVITYNAME");
            String modelsId = hm.get("MODELS_ID");
			vo.setActivityId(activityId);
			String CreateUser=user.getAccount();
			dao.insertModels(modelsId,CreateUser,activityId,activityCode,activityName);
			atx.setOutMsg(vo.getRowXml(),"服务活动车型新增成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 新增服务活动工时
     * @throws Exception
     */
    public void insertTimes() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	HashMap<String, String> hm;
            //将request流转换为hashmap结构体
            hm = RequestUtil.getValues(request);
            String activityId=hm.get("ACTIVITYID");
			String activityCode=hm.get("ACTIVITYCODE");
			String activityName=hm.get("ACTIVITYNAME");
            String taskTimeId = hm.get("TASK_TIME_ID");
			String CreateUser=user.getAccount();
			dao.insertTimes(taskTimeId,CreateUser,activityId,activityCode,activityName);
			atx.setOutMsg("","服务活动工时新增成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 更新服务活动
     * @throws Exception
     */
    public void update() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        SeBuActivityVO tempVO = new SeBuActivityVO();
        try
        {
            HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			tempVO.setValue(hm);
			tempVO.setLanguage((String)request.getSession().get(Globals.SYS_LANGUAGE));
			tempVO.setUpdateUser(user.getAccount());
			tempVO.setUpdateTime(Pub.getCurrentDate());
            dao.updateServiceActivity(tempVO);
            atx.setOutMsg(tempVO.getRowXml(),"服务活动信息修改成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 车龄信息保存
     * @throws Exception
     */
    public void saveCl() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        SeBuActivityVehageVO vo = new SeBuActivityVehageVO();
        try
        {
            HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			vo.setValue(hm);
			vo.setCreateTime(Pub.getCurrentDate());
			vo.setCreateUser(user.getAccount());
			vo.setStatus(DicConstant.YXBS_01);
            dao.saveCl(vo);
            atx.setOutMsg("","服务活动车龄修改成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 删除服务活动
     * @throws Exception
     */
    public void delete() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        String activityStatus = Pub.val(request, "activityStatus");
        String activityId = Pub.val(request, "activityId");
        try
        {
           if(DicConstant.HDZT_01.equals(activityStatus)){
        	   dao.deleteByActivityId(activityId);
        	   atx.setOutMsg("","服务活动删除成功！");
           }else{
        	   atx.setOutMsg("","只有活动状态为未发布与已取消的记录才可以进行删除操作！");
           }
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 删除服务活动车型
     * @throws Exception      
     */
    public void deleteModels() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
       
        try
        {
        	String mxids=Pub.val(request, "mxids");
			 dao.deleteCxByMxids(mxids);
			 atx.setOutMsg("","服务活动车型删除成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 删除服务活动配件
     * @throws Exception      
     */
    public void deleteParts() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
       
        try
        {
        	String mxids=Pub.val(request, "mxids");
			 dao.deletePartByMxids(mxids);
			 atx.setOutMsg("","服务活动配件删除成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 删除服务活动VIN
     * @throws Exception      
     */
    public void deleteVIN() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	
    	try
    	{
    		String mxids=Pub.val(request, "mxids");
    		dao.deleteVINByMxids(mxids);
    		atx.setOutMsg("","服务活动VIN删除成功！");
    	}
    	catch (Exception e)
    	{
    		atx.setException(e);
    		logger.error(e);
    	}
    }
    /**
     * 删除服务活动工时
     * @throws Exception
     */
    public void deleteTimes() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
       
        try
        {
        	String mxids=Pub.val(request, "mxids");
			 dao.deleteGsByMxids(mxids);
			 atx.setOutMsg("","服务活动工时删除成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 删除附件
     * @throws Exception
     */
    public void attaDelete() throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        String fjid = Pub.val(request, "fjid");
        try
        {
        	//删除预授权项目
            dao.preAuthAttaDelete(fjid);
            //返回更新结果和成功信息
            atx.setOutMsg("","删除成功！");
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
     * @date 2014年7月2日09:05:00
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
    /**        
     * @title: searchServiceModel
     * @description: TODO(查询服务活动车型方法)
     * @throws Exception    设定文件
     * @return void    返回类型
     * @auther sunxuedong
     * @date 2014年7月3日09:05:00
     */
    public void searchServiceModel() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{	
			String activityId=Pub.val(request, "activityId");
			BaseResultSet bs = dao.searchServiceModel(page,user,conditions,activityId);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    /**        
     * @title: searchServiceParts
     * @description: TODO(查询服务活动车型方法)
     * @throws Exception    设定文件
     * @return void    返回类型
     * @auther sunxuedong
     * @date 2014年7月3日09:05:00
     */
    public void searchServiceParts() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{	
			String activityId=Pub.val(request, "activityId");
			BaseResultSet bs = dao.searchServiceParts(page,user,conditions,activityId);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    /**        
     * @title: searchServiceVin
     * @description: TODO(查询服务活动车型方法)
     * @throws Exception    设定文件
     * @return void    返回类型
     * @auther sunxuedong
     * @date 2014年7月3日09:05:00
     */
    public void searchServiceVin() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{	
			String activityId=Pub.val(request, "activityId");
			BaseResultSet bs = dao.searchServiceVin(page,user,conditions,activityId);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    /**        
     * @title: searchServiceTime
     * @description: TODO(查询服务活动车型方法) 
     * @throws Exception    设定文件
     * @return void    返回类型
     * @auther sunxuedong
     * @date 2014年7月3日09:05:00
     */
    public void searchServiceTime() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{	
			String activityId=Pub.val(request, "activityId");
			BaseResultSet bs = dao.searchServiceTime(page,user,conditions,activityId);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    /**
     * @title: searchModel
     * @description: TODO(查询服务活动方法)
     * @throws Exception    设定文件
     * @return void    返回类型
     * @auther sunxuedong
     * @date 2014年7月4日15:55:27
     */
    public void searchModel() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{	
			String activityId=Pub.val(request, "activityId");
			BaseResultSet bs = dao.searchModel(page,user,conditions,activityId);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    /**
     * @title: searchTime
     * @description: TODO(查询服务活动方法)
     * @throws Exception    设定文件
     * @return void    返回类型
     * @auther sunxuedong
     * @date 2014年7月5日10:01:43 
     */
    public void searchTime() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{	
			String activityId=Pub.val(request, "activityId");
			BaseResultSet bs = dao.searchTime(page,user,conditions,activityId);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    /**
     * @title: searchPart
     * @description: TODO(查询服务活动配件方法)
     * @throws Exception    设定文件
     * @return void    返回类型
     * @auther sunxuedong
     * @date 2014年7月5日10:01:43 
     */
    public void searchPart() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{	
			String activityId=Pub.val(request, "activityId");
			BaseResultSet bs = dao.searchPart(page,user,conditions,activityId);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    /**
     * @title: searchVIN      
     * @description: TODO(查询服务活动项目)
     * @throws Exception    设定文件
     * @return void    返回类型
     * @auther sunxuedong
     * @date 2014年7月5日10:01:43 searchProjects
     */
    public void searchVIN() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{	
			String activityId=Pub.val(request, "activityId");
			BaseResultSet bs = dao.searchVIN(page,user,conditions,activityId);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    /**
     * @title: searchServiceVehage      
     * @description: TODO(查询服务活动车龄)
     * @throws Exception    设定文件
     * @return void    返回类型
     * @auther sunxuedong
     * @date 2014年7月5日10:01:43 
     */
    public void searchServiceVehage() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{	
			String activityId=Pub.val(request, "activityId");
			BaseResultSet bs = dao.searchServiceVehage(page,user,conditions,activityId);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    /**
     * 查询附件
     * @throws Exception
     */
    public void fileSearch() throws Exception {
    	//定义request对象
	    RequestWrapper request = atx.getRequest();
	    //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
		PageManager page = new PageManager();
	    RequestUtil.getConditionsWhere(request,page);
		try
		{
			String activityId=request.getParamValue("activityId");
			//执行dao中search方法，BaseResultSet：结果集封装对象
			BaseResultSet bs = dao.fileSearch(page,user,activityId);
			//输出结果集，第一个参数”bs”为固定名称，不可修改
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
    }
  /**
     * @return 
     * @title: search
     * @description: TODO(查询服务活动方法)
     * @throws Exception    设定文件
     * @return void    返回类型
     * @auther sunxuedong
     * @date 2014年7月2日09:05:00
     *//*
	public List<ExcelErrors> checkVIN(String activityId) throws Exception
    {
        User user = (User) atx.getSession().get(Globals.USER_KEY);
      	ExcelErrors errors = null;
    	List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
		try
		{
			QuerySet qs1=dao.checkList(user);
			if(qs1.getRowCount()>0){
				for(int i=0;i<qs1.getRowCount();i++){
					errors=new ExcelErrors();
					String rowNo=qs1.getString(i+1, "ROW_NO"); 
					String vin=qs1.getString(i+1,"VIN");
					errors.setRowNum(Integer.parseInt(rowNo));
					errors.setErrorDesc("底盘号:"+vin+"在车辆数据中不存在。");
					errorList.add(errors);
				}
			}
			QuerySet qs2=dao.checkList2(user,activityId);
			if(qs2.getRowCount()>0){
				for(int i=0;i<qs2.getRowCount();i++){
					errors=new ExcelErrors();
					String rowNo=qs2.getString(i+1, "ROW_NO"); 
					String vin=qs2.getString(i+1,"VIN");
					errors.setRowNum(Integer.parseInt(rowNo));
					errors.setErrorDesc("底盘号:"+vin+"已维护到服务活动VIN信息中，不可重复添加");
					errorList.add(errors);
				}
			}
			QuerySet qs3=dao.checkList3(user);
			if(qs3.getRowCount()>0){
				for(int i=0;i<qs3.getRowCount();i++){
					errors=new ExcelErrors();
					String vin=qs3.getString(i+1,"VIN");
					String cou=qs3.getString(i+1,"COU");
					errors.setErrorDesc("底盘号:"+vin+"在导入模板中已重复填写"+cou+"次！");
					errorList.add(errors);
				}
			}
			
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
			if(errorList!=null&&errorList.size()>0){
				return errorList;
			}else{
				return null;
			}
		}*/
    /**
     * 导入验证成功查询
     * @throws Exception
     */
    public void searchImport()throws Exception{
    	//RequestWrapper request = atx.getRequest();
 		PageManager page = new PageManager();
 		User user = (User) atx.getSession().get(Globals.USER_KEY);
 		RequestWrapper request = atx.getRequest();
 		String conditions = RequestUtil.getConditionsWhere(request, page);
 		try
 		{
 			BaseResultSet bs = dao.searchImport(page,user,conditions);
 			atx.setOutData("bs", bs);
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }
    /**
     * 插入正式表
     * @throws Exception
     */
    public void serviceActivityVinImport()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
 		String activityId=Pub.val(request, "activityId");
 		QuerySet qs = dao.queryCode(activityId);
 		String activityCode=qs.getString(1, "ACTIVITY_CODE");
 		String activityName=qs.getString(1, "ACTIVITY_NAME");
 		try
 		{
 			String tmpNo = Pub.val(request, "tmpNo");
        	String rowNo = "";
        	if ("".equals(tmpNo)==false) {
        		rowNo = " AND B.ROW_NO NOT IN ("+tmpNo+") ";
        	}
 			dao.insertDetail(activityId,activityCode,activityName,user,rowNo);
 			atx.setOutMsg("", "导入成功！");
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }
    /**
     * 错误数据导出
     * @throws Exception
     */
    public void expData()throws Exception{

        try {
        	RequestWrapper request = atx.getRequest();
            String conditions = Pub.val(request, "seqs");
            ResponseWrapper response= atx.getResponse();
            User user = (User) atx.getSession().get(Globals.USER_KEY);
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;
            hBean = new HeaderBean();
            hBean.setName("VIN");
            hBean.setTitle("VIN");
            header.add(0,hBean);
            
            QuerySet querySet = dao.expData(conditions,user);
            ExportManager.exportFile(response.getHttpResponse(), "wrongVIN", header, querySet);
        } catch (Exception e) {
        	atx.setException(e);
            logger.error(e);
        }
    }
}