package com.org.dms.action.service.guaranteesHurryPart;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.guaranteesHurryPart.GuaranteesHurryPartDao;
import com.org.dms.vo.service.SeBuDispatchDtlVO;
import com.org.dms.vo.service.SeBuDispatchExtVO;
import com.org.dms.vo.service.SeBuDispatchVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 三包急件申请提报ACTION
 * @author Administrator
 *
 */
public class GuaranteesHurryPartAction {

	//日志类
    private Logger logger = com.org.framework.log.log.getLogger("GuaranteesHurryPartAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private GuaranteesHurryPartDao dao = GuaranteesHurryPartDao.getInstance(atx);

    /**
     * 三包急件申请查询
     * @throws Exception
     */
    public void dispatchSearch() throws Exception
    {
    	//定义request对象
	    RequestWrapper request = atx.getRequest();
	    //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
		PageManager page = new PageManager();
		//将request流中的信息转化为where条件
		String conditions = RequestUtil.getConditionsWhere(request,page);
		String orgId=user.getOrgId();
		try
		{
			//执行dao中search方法，BaseResultSet：结果集封装对象
			BaseResultSet bs = dao.dispatchSearch(page,user,conditions,orgId);
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
     * 下端查询页面功能
     * @throws Exception
     */
    public void dispatchDelSearch() throws Exception
    {
    	//定义request对象
    	RequestWrapper request = atx.getRequest();
    	//获取session中的user对象
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	//定义查询分页对象
    	PageManager page = new PageManager();
    	//将request流中的信息转化为where条件
    	String conditions = RequestUtil.getConditionsWhere(request,page);
    	String orgId=user.getOrgId();
    	try
    	{
    		//执行dao中search方法，BaseResultSet：结果集封装对象
    		BaseResultSet bs = dao.dispatchDelSearch(page,user,conditions,orgId);
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
     * 三包急件申请上端查询
     * @throws Exception
     */
    public void dispatchOemSearch() throws Exception
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
    		BaseResultSet bs = dao.dispatchOemSearch(page,user,conditions);
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
     * 车辆校验查询
     * @throws Exception
     */
    public void guaranteesHurryPartVinCheck()  throws Exception{
    	RequestWrapper request = atx.getRequest();
    	try
		{
    		String vin=request.getParamValue("diVinVal");
    		String engineNo=request.getParamValue("diEngineNoVal");
    		BaseResultSet bs = dao.vinCheckSearch(vin,engineNo);
    		bs.setFieldDateFormat("BUY_DATE","yyyy-MM-dd");
    		bs.setFieldDateFormat("FACTORY_DATE","yyyy-MM-dd");
    		bs.setFieldDateFormat("MAINTENANCE_DATE","yyyy-MM-dd");
    		atx.setOutData("bs" , bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
    }
    /**        
     * @title: searchHurryParts
     * @description: 
     * @throws Exception    设定文件
     * @return void    返回类型
     * @auther sunxuedong
     */
    public void searchHurryParts() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{	
			String dispatchId=Pub.val(request, "dispatchId");
			BaseResultSet bs = dao.searchHurryParts(page,user,conditions,dispatchId);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    /**
     * 三包急件新增保存
     * @throws Exception
     */
    public void hurryPartInsert() throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	SeBuDispatchVO vo = new SeBuDispatchVO();
        	SeBuDispatchExtVO voExt=new SeBuDispatchExtVO();
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			int vehicleMilesCount=0;
			String vehicleId=hm.get("VEHICLE_ID");          
			String mileage=hm.get("MILEAGE");
			String faulAnalyse=hm.get("FAULT_ANALYSE");
			int mileages=Integer.parseInt(mileage);//页面填写的行驶里程
			QuerySet vehicleMile = dao.searchVehicleMiles(vehicleId);
			String vehicleMiles=vehicleMile.getString(1, 1);
			if(vehicleMiles.equals("")){
    			vehicleMilesCount=0;
    		}else{
    			vehicleMilesCount=Integer.parseInt(vehicleMiles);//数据库中保存的行驶里程
    		}
			if(vehicleMilesCount<=mileages){
				String dispatchNo=dao.getdispatchNo();
				vo.setValue(hm);
				voExt.setValue(hm);
				vo.setStatus(DicConstant.YXBS_01);//状态(有效)
				vo.setFaultAnalyse(faulAnalyse);
				vo.setDispatchStatus(DicConstant.SBJJSQZT_01);
				vo.setDispatchNo(dispatchNo);
				vo.setCreateUser(user.getAccount());// 设置创建人
				vo.setCreateTime(Pub.getCurrentDate());// 创建时间
				vo.setOrgId(user.getOrgId());
				vo.setCompanyId(user.getCompanyId());
				vo.setOemCompanyId(user.getOemCompanyId());
				//通过dao，执行插入
				dao.hurryPartInsert(vo);
				//返回插入结果和成功信息
				voExt.setDispatchId(vo.getDispatchId());
			    voExt.setDispatchNo(dispatchNo);
			    voExt.setDispatchStatus(DicConstant.SBJJSQZT_01);
				voExt.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
		        voExt.bindFieldToDic("DISPATCH_STATUSR","SBJJSQZT");
				atx.setOutMsg(voExt.getRowXml(),"三包急件新增成功！");
			}else{
				atx.setOutMsg(voExt.getRowXml(),"填写的里程不可小于数据库中保存的行驶里程！");
			}
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }
    
    /**
     * 三包急件修改保存
     * @throws Exception
     */
    public void hurryPartUpdate() throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	SeBuDispatchVO vo = new SeBuDispatchVO();
        	SeBuDispatchExtVO voExt=new SeBuDispatchExtVO();
			HashMap<String,String> hm;
			//将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			String dispatchId=hm.get("DISPATCH_ID");
			int vehicleMilesCount=0;
			String vehicleId=hm.get("VEHICLE_ID");
			String faulAnalyse=hm.get("FAULT_ANALYSE");
			String mileage=hm.get("MILEAGE");
			int mileages=Integer.parseInt(mileage);//页面填写的行驶里程
			QuerySet vehicleMile = dao.searchVehicleMiles(vehicleId);
			String vehicleMiles=vehicleMile.getString(1, 1);
			if(vehicleMiles.equals("")){
    			vehicleMilesCount=0;
    		}else{
    			vehicleMilesCount=Integer.parseInt(vehicleMiles);//数据库中保存的行驶里程
    		}
			if(vehicleMilesCount<=mileages){
			//将hashmap映射到vo对象中,完成匹配赋值
				
			vo.setValue(hm);
			vo.setDispatchId(dispatchId);
			voExt.setValue(hm);
			vo.setFaultAnalyse(faulAnalyse);
			vo.setUpdateUser(user.getAccount());// 设置创建人
			vo.setUpdateTime(Pub.getCurrentDate());// 创建时间
			vo.setOrgId(user.getOrgId());
			vo.setCompanyId(user.getCompanyId());
			//通过dao，执行插入
			dao.hurryPartUpdate(vo);
			//返回插入结果和成功信息
			voExt.setDispatchId(vo.getDispatchId());
		    voExt.setDispatchNo(vo.getDispatchNo());
		    voExt.setDispatchStatus(DicConstant.SBJJSQZT_01);
			voExt.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
			voExt.setFieldDateFormat("FAULT_DATE", "yyyy-MM-dd");
	        voExt.bindFieldToDic("DISPATCH_STATUSR","SBJJSQZT");
			atx.setOutMsg(voExt.getRowXml(),"三包急件修改成功！");
			}else{
			atx.setOutMsg(voExt.getRowXml(),"填写的里程不可小于数据库中保存的行驶里程！");
			}
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 三包急件提报
     * @throws Exception
     */
    public void hurryPartReport() throws Exception {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
        String dispatchId = Pub.val(request, "dispatchId");//三包急件ID
        String flag = Pub.val(request, "flag");//标示符  如果1直接提报，如果是2先保存再提报
        SeBuDispatchVO vo = new SeBuDispatchVO();
        String orgId= user.getOrgId();
        int dealerCount;
        int psCount;
        String partCode = null;
        String partName=null;
        String pspartCode=null;
        String pspartName=null;
        try
        {
        QuerySet qsPartsCount =dao.queryPartsCount(dispatchId);
        QuerySet qs =dao.queryAmount(dispatchId,orgId);
        if(qsPartsCount.getRowCount()>0){
        	 if(qs.getRowCount()>0){
             	String count=qs.getString(1, 1);
                  dealerCount=Integer.parseInt(count);
                  partCode=qs.getString(1, 3);
                  partName=qs.getString(1, 4);
             }else{
             	dealerCount=0;
             }
             QuerySet psqs =dao.queryPSAmount(dispatchId,orgId);
             if(psqs.getRowCount()>0){
             	 String pscount=psqs.getString(1, 1);
                  psCount=Integer.parseInt(pscount);
                  pspartCode=psqs.getString(1, 3);
                  pspartName=psqs.getString(1, 4);
             }else{
             	 psCount=0;
             }
             if(flag.equals("1")){
             	vo.setDispatchId(dispatchId);
             	  for(int i=0;i<qsPartsCount.getRowCount();i++){
                   	if (dealerCount!=0){
                   		vo.setUpdateUser(user.getAccount());
           				vo.setUpdateTime(Pub.getCurrentDate());
           				vo.setApplyDate(Pub.getCurrentDate());
           				vo.setDispatchStatus(DicConstant.SBJJSQZT_03);
           				vo.setCheckRearks("提报失败！"+partCode+":"+partName+"配件本地库存已存在本配件");
           				dao.hurryPartUpdate(vo);
                   		atx.setOutMsg("","提报失败！"+partCode+":"+partName+"配件本地库存已存在本配件");
                   	}else{
                   		if(psCount!=0){
               			vo.setUpdateUser(user.getAccount());
           				vo.setUpdateTime(Pub.getCurrentDate());
           				vo.setApplyDate(Pub.getCurrentDate());
           				vo.setDispatchStatus(DicConstant.SBJJSQZT_03);
           				vo.setCheckRearks("提报失败！"+pspartCode+":"+pspartName+"配件配送中心库存已存在本配件");
           				dao.hurryPartUpdate(vo);
                   		atx.setOutMsg("","提报失败！"+pspartCode+":"+pspartName+"配件配送中心库存已存在本配件");
                   		}else{
                   			vo.setUpdateUser(user.getAccount());
                 			vo.setUpdateTime(Pub.getCurrentDate());
                 			vo.setApplyDate(Pub.getCurrentDate());
                 			vo.setCheckRearks("");
                 			vo.setDispatchStatus(DicConstant.SBJJSQZT_02);
                 			vo.setCheckRearks("无");	
                 			dao.hurryPartUpdate(vo);
                 	        //返回更新结果和成功信息
                 	        atx.setOutMsg("1","提报成功！");
           	        		}
           	        	}
           	        }
         	}else if(flag.equals("2")){
         		HashMap<String,String> hm;
     			hm = RequestUtil.getValues(request);
     			vo.setValue(hm);
     			  for(int i=0;i<qsPartsCount.getRowCount();i++){
     		        	if (dealerCount !=0){
     		        		vo.setUpdateUser(user.getAccount());
     						vo.setUpdateTime(Pub.getCurrentDate());
     						vo.setApplyDate(Pub.getCurrentDate());
     						vo.setDispatchStatus(DicConstant.SBJJSQZT_03);
     						vo.setCheckRearks("提报失败！"+partCode+":"+partName+"配件本地库存已存在本配件");
     						dao.hurryPartUpdate(vo);
     		        		atx.setOutMsg("","提报失败！"+partCode+":"+partName+"配件本地库存已存在本配件");
     		        	}else{
     		        		if(psCount!=0){
     		    			vo.setUpdateUser(user.getAccount());
     						vo.setUpdateTime(Pub.getCurrentDate());
     						vo.setApplyDate(Pub.getCurrentDate());
     						vo.setDispatchStatus(DicConstant.SBJJSQZT_03);
     						vo.setCheckRearks("提报失败！"+pspartCode+":"+pspartName+"配件配送中心库存已存在本配件");	
     						dao.hurryPartUpdate(vo);
     						atx.setOutMsg("","提报失败！"+pspartCode+":"+pspartName+"配件配送中心库存已存在本配件");
     		        		}else{
     		        			vo.setUpdateUser(user.getAccount());
     		        			vo.setUpdateTime(Pub.getCurrentDate());
     		        			vo.setApplyDate(Pub.getCurrentDate());
     		        			vo.setCheckRearks("");	
     		        			vo.setDispatchStatus(DicConstant.SBJJSQZT_02);
     		        			vo.setCheckRearks("无");	
     		        			dao.hurryPartUpdate(vo);
     		        	        //返回更新结果和成功信息
     		        	        atx.setOutMsg("1","提报成功！");
     			        		}
     			        	}
     			        }
         			}
        	}else{
        		atx.setOutMsg("","请先选择配件！");
        }
       
       
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 三包急件删除
     * @throws Exception
     */
    public void dispatchPartDelete() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        String dispatchId = Pub.val(request, "dispatchId");
        try
        {
        	dao.dispatchPartDelete(dispatchId);
            atx.setOutMsg("","删除成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    public void searchParts() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{	
			String dispatchId=Pub.val(request, "dispatchId");
			BaseResultSet bs = dao.searchParts(page,user,conditions,dispatchId);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    /**
     * 新增配件
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
                String remarks=hm.get("REMARKS");
                String salePrice=hm.get("SALE_PRICE");
                String dispatchId=hm.get("DISPATCH_ID");
                String partIds = hm.get("PARTIDS");//配件ID(逗号分隔)
                String partCodes = hm.get("PARTCODES");//促销配件代码(逗号分隔)
                String partNames = hm.get("PARTNAMES");//促销配件名称(逗号分隔)
                String quantity = hm.get("QUANTITY");//促销配件促销价(逗号分隔)
                String[] partIdArr = partIds.split(",");
                String[] partCodeArr = partCodes.split(",");
                String[] partNameArr = partNames.split(",");
                String[] salePriceArr = salePrice.split(",");
                String[] quantityArr= quantity.split(",");
                String[] remarksArr= remarks.split(",");
                for (int i = 0; i < partIdArr.length; i++) {
                	SeBuDispatchDtlVO vo = new SeBuDispatchDtlVO();
                    vo.setPartId(partIdArr[i]);
                    vo.setDispatchId(dispatchId);
                    vo.setPartCode(partCodeArr[i]);
                    vo.setPartName(partNameArr[i]);
                    vo.setClaimPrice(salePriceArr[i]);
                    vo.setCount(quantityArr[i]);
                    if("anull".equals(remarksArr[i])){
                    	remarksArr[i]="";
                    }
                    vo.setRemarks(remarksArr[i]);	
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
    public void deleteParts() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
       
        try
        {
        	String mxids=Pub.val(request, "mxids");
			 dao.deletePartByMxids(mxids);
			 atx.setOutMsg("","三包急件配件删除成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
}