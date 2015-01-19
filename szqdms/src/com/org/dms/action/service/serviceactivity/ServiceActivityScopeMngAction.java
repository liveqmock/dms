package com.org.dms.action.service.serviceactivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.serviceactivity.ServiceActivityScopeMngDao;
import com.org.dms.vo.service.SeBuActivityAreaVO;
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
 * 服务活动范围维护action
 */
public class ServiceActivityScopeMngAction
{
	private Logger logger = com.org.framework.log.log.getLogger("ServiceActivityScopeMngAction");
	private ActionContext atx = ActionContext.getContext();
	private ServiceActivityScopeMngDao dao = ServiceActivityScopeMngDao.getInstance(atx);

	public void search() throws Exception{
		 RequestWrapper request = atx.getRequest();
		 User user = (User) atx.getSession().get(Globals.USER_KEY);
		 PageManager page = new PageManager();
		 String oemCompanyId= user.getOemCompanyId();
		 String conditions = RequestUtil.getConditionsWhere(request,page);
		 try{
			BaseResultSet bs = dao.searchScope(page,user,conditions,oemCompanyId);
			atx.setOutData("bs", bs);
		 }catch(Exception e){
		    logger.error(e);
			atx.setException(e);
		 }
	}
	public void searchPublish() throws Exception{
		 RequestWrapper request = atx.getRequest();
		 User user = (User) atx.getSession().get(Globals.USER_KEY);
		 PageManager page = new PageManager();
		 String oemCompanyId= user.getOemCompanyId();
		 String conditions = RequestUtil.getConditionsWhere(request,page);
		 try{
			BaseResultSet bs = dao.searchPublish(page,user,conditions,oemCompanyId);
			atx.setOutData("bs", bs);
		 }catch(Exception e){
		    logger.error(e);
			atx.setException(e);
		 }
	}
	
	/**
	 * 登记页面查询方法
	 */
	public void searchVIN() throws Exception{
		 RequestWrapper request = atx.getRequest();
		 User user = (User) atx.getSession().get(Globals.USER_KEY);
		 PageManager page = new PageManager();
		 String conditions = RequestUtil.getConditionsWhere(request,page);
		 String orgId= user.getOrgId();
		 String vin=Pub.val(request, "vin");
         try{
         //查询VIN是否存在
         QuerySet queryVin = dao.queryVin(vin);
         if(queryVin.getRowCount()>0){
        	 String mileage=queryVin.getString(1, "MILEAGE");//行驶里程
             String vehicleVin=queryVin.getString(1, "SVIN");//VIN
             String buyDate=queryVin.getString(1, "BUY_DATE");//销售日期
             String modelsCode=queryVin.getString(1, "MODELS_CODE");//车型代码
             String factoryDate=queryVin.getString(1, "FACTORY_DATE");//生产日期
        	//VIN在数据库中已存在，进行下一步校验：校验VIN是否在服务活动车辆表中维护数据。
        	 QuerySet queryVehicleVin = dao.queryVehicleVin(vehicleVin);
        	 if(queryVehicleVin.getRowCount()>0){
        		 //VIN在服务活动车辆表中存在，终止判断，返回查询数据。填写VIN作为限制条件后，只读取添加的VIN
        		 if(!"".equals(buyDate)){
        			 String dateType=DicConstant.RQLX_01;
	        		 BaseResultSet bs =dao.searchVehicleVin(page,user,conditions,orgId,vehicleVin,dateType,buyDate,factoryDate);
	        		 if(page.getCountRows()>0){
	    				 atx.setOutData("bs", bs);
	    				 return;
	        		 }
        		 }
        		 if(!"".equals(factoryDate)){
        			 String dateType=DicConstant.RQLX_02;
        			 BaseResultSet bs =dao.searchVehicleVin(page,user,conditions,orgId,vehicleVin,dateType,buyDate,factoryDate);
        			 if(page.getCountRows()>0){
        				 atx.setOutData("bs", bs);
        				 return;
        			 }
        		 }
        	 }
    		 if(!"".equals(mileage)){
    			 BaseResultSet bs =dao.searchVehicleMileage(page,user,conditions,orgId,mileage,vehicleVin);
    			 if(bs!=null){
    				 if(page.getCountRows()>0){
    				 atx.setOutData("bs", bs);
    				 return;
        			 }
        		 }
    		 }
    		 //校验车型是否满足
    		 if(!"".equals(modelsCode)){
    			 BaseResultSet bs =dao.searchVehicleModels(page,user,conditions,orgId,vehicleVin);
        		 if(bs!=null){
        			 if(page.getCountRows()>0){
        			 atx.setOutData("bs", bs);
        			 return;
        			 }
        		 }
    		 }
        	 
    		 //如果公里数不满足，进行下一步校验：是否符合服务活动中的车龄条件
    		 if(!"".equals(buyDate)){
    			 String dateType=DicConstant.RQLX_01;
    			 BaseResultSet bs =dao.searchVehicleVehage(page,user,conditions,orgId,dateType,buyDate,factoryDate,vehicleVin);
    			 if(bs!=null){
    				 if(page.getCountRows()>0){
    				 atx.setOutData("bs", bs);
    				 return;
        			 }
        		 }
    		 }
    		 if(!"".equals(factoryDate)){
    			 String dateType=DicConstant.RQLX_02;
    			 BaseResultSet bs =dao.searchVehicleVehage(page,user,conditions,orgId,dateType,buyDate,factoryDate,vehicleVin);
    			 if(bs!=null){
    				 if(page.getCountRows()>0){
        			 atx.setOutData("bs", bs);
        			 return;
        			 }
        		 }
    		 } 
    		
    		 atx.setOutMsg("没有查询到符合该VIN的服务活动！");
         }else{
        	 atx.setOutMsg("","VIN不存在！");
         }
		 }catch(Exception e){
		    logger.error(e);
			atx.setException(e);
		 }
	}
	//下端服务活动车辆登记查询
	public void searchVehicel() throws Exception{
		 RequestWrapper request = atx.getRequest();
		 User user = (User) atx.getSession().get(Globals.USER_KEY);
		 PageManager page = new PageManager();
		 String orgId= user.getOrgId();
		 String conditions = RequestUtil.getConditionsWhere(request,page);
		 try{
			BaseResultSet bs = dao.searchVehicel(page,user,conditions,orgId);
			atx.setOutData("bs", bs);
		 }catch(Exception e){
		    logger.error(e);
			atx.setException(e);
		 }
	}
	//上端服务活动车辆登记查询
	public void searchOemVehicel() throws Exception{
		 RequestWrapper request = atx.getRequest();
		 User user = (User) atx.getSession().get(Globals.USER_KEY);
		 PageManager page = new PageManager();
		 String conditions = RequestUtil.getConditionsWhere(request,page);
		 try{
			BaseResultSet bs = dao.searchOemVehicel(page,user,conditions);
			atx.setOutData("bs", bs);
		 }catch(Exception e){
		    logger.error(e);
			atx.setException(e);
		 }
	}
	//主机厂端服务活动查询
	public void OemSearch() throws Exception{
		RequestWrapper request = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		 String oemCompanyId= user.getOemCompanyId();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try{
			BaseResultSet bs = dao.OemSearch(page,user,conditions,oemCompanyId);
			atx.setOutData("bs", bs);
		}catch(Exception e){
			logger.error(e);
			atx.setException(e);
		}
	}
	//渠道商端服务活动查询
	public void delSearch() throws Exception{
		RequestWrapper request = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		 String orgId= user.getOrgId();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try{
			BaseResultSet bs = dao.delSearch(page,user,conditions,orgId);
			atx.setOutData("bs", bs);
		}catch(Exception e){
			logger.error(e);
			atx.setException(e);
		}
	}
	public void searchService() throws Exception{
		 RequestWrapper request = atx.getRequest();
		 User user = (User) atx.getSession().get(Globals.USER_KEY);
		 PageManager page = new PageManager();
		 String conditions = RequestUtil.getConditionsWhere(request,page);
		 try{
			String activityId=Pub.val(request, "activityId");
			BaseResultSet bs = dao.searchService(page,user,conditions,activityId);
			atx.setOutData("bs", bs);
		 }catch(Exception e){
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
     * @description: TODO(查询服务活动VIN)
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
    public void searchDealers() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{	
			String activityId=Pub.val(request, "activityId");
			BaseResultSet bs = dao.searchDealers(page,user,conditions,activityId);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    /**
     * @title: searchOrgDealrs
     * @description: TODO(查询未添加的渠道商方法)
     * @throws Exception    设定文件
     * @return void    返回类型
     * @auther sunxuedong
     * @date 2014年7月4日15:55:27
     */
    public void searchOrgDealrs() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{	
			String activityId=Pub.val(request, "activityId");
			BaseResultSet bs = dao.searchOrgDealrs(page,user,conditions,activityId);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    public void insertDealers() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	
        	HashMap<String, String> hm;
        	hm = RequestUtil.getValues(request);
        	SeBuActivityAreaVO vo = new SeBuActivityAreaVO();
			String mxids=Pub.val(request, "mxids");
			String activityName=hm.get("ACTIVITY_NAME");
			String activityId=Pub.val(request, "activityId");
			String activityCode=Pub.val(request, "activityCode");
			vo.setActivityId(activityId);
			String CreateUser=user.getAccount();
			dao.insertDealers(mxids,CreateUser,activityId,activityCode,activityName);
			atx.setOutMsg(vo.getRowXml(),"服务活动渠道商新增成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 删除渠道商
     * @throws Exception      
     */
    public void deleteDealer() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
       
        try
        {
        	String mxids=Pub.val(request, "mxid");
			 dao.deleteDealer(mxids);
			 atx.setOutMsg("","服务活动渠道商删除成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 更新
     * @throws Exception
     */
    public void doCancel() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        try
        {
        	String activityId = Pub.val(request, "activityId");
            dao.doCancel(activityId);
            atx.setOutMsg("","服务活动发布成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    public void doPublish() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	 User user = (User) atx.getSession().get(Globals.USER_KEY);
    	 String CreatUser = user.getAccount();
        try
        {
        	String activityId = Pub.val(request, "activityId");
        	QuerySet qs = dao.queryScope(activityId);
        	if(qs.getRowCount()>0){
        		dao.doPublish(activityId);
        	}else{
        	dao.insertAllDealers(activityId,CreatUser);
        	dao.doPublish(activityId);
        	}
            atx.setOutMsg("","服务活动发布成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    public void doRegister() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
    	try
    	{
    		SeBuActivityVehicleVO vo = new SeBuActivityVehicleVO();
    		HashMap<String, String> hm;
            //将request流转换为hashmap结构体
    		String vehicleId = null;
            hm = RequestUtil.getValues(request);
            String activityId=hm.get("ACTIVITY_ID");
			String activityCode=hm.get("ACTIVITY_CODE");
			String activityName=hm.get("ACTIVITY_NAME");
			String Vin=hm.get("VEHICLE_VIN");
            String orgCode = hm.get("ORG_CODE");
            String orgName = hm.get("ORG_NAME");
            String svin = "";
            QuerySet qs=dao.queryVin(Vin);
            if(qs.getRowCount()>0){
               vehicleId = qs.getString(1, "VEHICLE_ID");
               svin = qs.getString(1, "SVIN");
            }
    		String orgId = user.getOrgId();
    		QuerySet repeatCheck= dao.repeatCheck(svin,activityId);
    		if(repeatCheck.getRowCount()>0){
    			String relationId= repeatCheck.getString(1, "RELATION_ID");
    			String status =repeatCheck.getString(1, "REGIST_STATUS");
    			if(status.equals(DicConstant.CLDJZT_02)){
    				atx.setOutMsg("1","");
    			}else{
    				vo.setRelationId(relationId);
        			vo.setClaimUser(DicConstant.SF_02);
        			vo.setApplyDate(Pub.getCurrentDate());
        			vo.setOrgId(orgId);
        			vo.setVin(Vin);
        			vo.setVehicleId(vehicleId);
        			vo.setOrgCode(orgCode);
            		vo.setOrgName(orgName);
            		vo.setRegistStatus(DicConstant.CLDJZT_02);
            		vo.setUpdateTime(Pub.getCurrentDate());
            		vo.setUpdateUser(user.getAccount());
            		dao.registerUpdate(vo);
            		atx.setOutMsg("","服务活动车辆登记成功！");
    			}
    		}else{
	    		vo.setActivityId(activityId);
	    		vo.setActivityCode(activityCode);
	    		vo.setActivityName(activityName);
	    		vo.setVehicleId(vehicleId);
	    		vo.setApplyDate(Pub.getCurrentDate());
	    		vo.setOrgId(orgId);
	    		vo.setVin(Vin);
	    		vo.setClaimUser(DicConstant.SF_02);
	    		vo.setRegistStatus(DicConstant.CLDJZT_02);
	    		vo.setOrgCode(orgCode);
	    		vo.setOrgName(orgName);
	    		vo.setIfRegistProduce(DicConstant.SF_01);
	    		vo.setCreateTime(Pub.getCurrentDate());
			    vo.setCreateUser(user.getAccount());
			    vo.setStatus(DicConstant.YXBS_01);
			    dao.doRegister(vo);
			    atx.setOutMsg("","服务活动车辆登记成功！");
    		}
		   
    	}
    	catch (Exception e)
    	{
    		atx.setException(e);
    		logger.error(e);
    	}
    }
    public void doClose() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        try
        {
        	String activityId = Pub.val(request, "activityId");
            dao.doClose(activityId);
            atx.setOutMsg("","服务活动关闭成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    public void download()throws Exception{
		//获取封装后的request对象
		RequestWrapper request = atx.getRequest();
		ResponseWrapper response= atx.getResponse();
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			List<HeaderBean> header = new ArrayList<HeaderBean>();
			HeaderBean hBean = null;
			hBean = new HeaderBean();
			hBean.setName("CODE");
			hBean.setTitle("渠道商代码");
			header.add(0,hBean);
			hBean = new HeaderBean();
			hBean.setName("ONAME");
			hBean.setTitle("渠道商名称");
			header.add(1,hBean);
			hBean = new HeaderBean();
			hBean.setName("ACTIVITY_CODE");
			hBean.setTitle("活动代码");
			header.add(2,hBean);
			hBean = new HeaderBean();
			hBean.setName("ACTIVITY_NAME");
			hBean.setTitle("活动名称");
			header.add(3,hBean);
			hBean = new HeaderBean();
			hBean.setName("VIN");
			hBean.setTitle("VIN");
			header.add(4,hBean);
			hBean = new HeaderBean();
			hBean.setName("CLAIM_USER");
			hBean.setTitle("是否已报单");
			header.add(5,hBean);
			hBean = new HeaderBean();
			hBean.setName("APPLY_DATE");
			hBean.setTitle("登记日期");
			header.add(6,hBean);
			hBean = new HeaderBean();
			hBean.setName("SOLUTION");
			hBean.setTitle("方案");
			header.add(7,hBean);
			QuerySet qs = dao.download(conditions);
			ExportManager.exportFile(response.getHttpResponse(), "服务活动车辆", header, qs);
		}
		catch (Exception e)
		{
			atx.setException(e);
			logger.error(e);
		}
	}
}