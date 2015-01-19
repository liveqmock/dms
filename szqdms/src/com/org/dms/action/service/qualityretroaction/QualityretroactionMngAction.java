package com.org.dms.action.service.qualityretroaction;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.qualityretroaction.QualityretroactionDao;
import com.org.dms.vo.service.SeBuQualityFbackDealVO;
import com.org.dms.vo.service.SeBuQualityFbackPartVO;
import com.org.dms.vo.service.SeBuQualityFbackVO;
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
 * 质量反馈action
 */
public class QualityretroactionMngAction
{
    private Logger logger = com.org.framework.log.log.getLogger("QualityretroactionMngAction");
    private ActionContext atx = ActionContext.getContext();
    private QualityretroactionDao dao = QualityretroactionDao.getInstance(atx);

    public void qualityretroactionSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		String orgId = user.getOrgId();
		try
		{
			BaseResultSet bs = dao.qualityretroactionSearch(page,user,conditions,orgId);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    //下端质量反馈查询
    public void qualityretroactionDelSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String orgId = user.getOrgId();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.qualityretroactionDelSearch(page,orgId,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    //上端质量反馈查询
    public void qualityretroactionOemSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String oemCompanyId = user.getOemCompanyId();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.qualityretroactionOemSearch(page,user,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    //上端质量反馈处理
    public void qualityretroactionDisposeSearch() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	PageManager page = new PageManager();
    	String oemCompanyId = user.getOemCompanyId();
    	String conditions = RequestUtil.getConditionsWhere(request,page);
    	try
    	{
    		BaseResultSet bs = dao.qualityretroactionDisposeSearch(page,user,conditions);
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    //质量反馈关闭查询
    public void qualityretroactionCloseSearch() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	PageManager page = new PageManager();
    	String orgId = user.getOrgId();
    	String conditions = RequestUtil.getConditionsWhere(request,page);
    	try
    	{
    		BaseResultSet bs = dao.qualityretroactionCloseSearch(page,orgId,conditions);
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    //质量反馈处理意见查询
    public void searchDealRemarks() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	String fbackId=Pub.val(request, "fbackId");
    	try
    	{
    		BaseResultSet bs = dao.searchDealRemarks(fbackId);
    		bs.setFieldDateFormat("DEAL_DATE", "yyyy-MM-dd");
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    //质量反馈驳回意见查询
    public void searchRejectRemarks() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	String fbackId=Pub.val(request, "fbackId");
    	try
    	{
    		BaseResultSet bs = dao.searchRejectRemarks(fbackId);
    		bs.setFieldDateFormat("REJECT_DATE", "yyyy-MM-dd");
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
    public void qualityretroactionVinCheck()  throws Exception{
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
    public void qualityRetroactionInsert() throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	SeBuQualityFbackVO vo = new SeBuQualityFbackVO();
        	SeBuQualityFbackVO voExt=new SeBuQualityFbackVO();
			HashMap<String,String> hm;
			
			//将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			String cusBuyCount=hm.get("CUS_BUY_COUNT");
			String driStatus=hm.get("DRI_STATUS");
			String vehicleUseType=hm.get("VEHICLE_USE_TYPE");
			String dailyWork=hm.get("DAILY_WORK");
			String faultAddress=hm.get("FAULT_ADDRESS");
			String dailyRoad=hm.get("DAILY_ROAD");
			String maintenanceStatus=hm.get("MAINTENANCE_STATUS");
			String vehicleStatus=hm.get("VEHICLE_STATUS");
			String fbackApproace=hm.get("FBACK_APPROACE");
			//将hashmap映射到vo对象中,完成匹配赋值
			vo.setValue(hm);
			voExt.setValue(hm);
			vo.setCusBuyCount(cusBuyCount);
			vo.setDriStatus(driStatus);
			vo.setVehicleUseType(vehicleUseType);
			vo.setDailyWork(dailyWork);
			vo.setFaultAddress(faultAddress);
			vo.setDailyRoad(dailyRoad);
			vo.setMaintenanceStatus(maintenanceStatus);
			vo.setVehicleStatus(vehicleStatus);
			vo.setFbackApproace(fbackApproace);
			vo.setStatus(DicConstant.YXBS_01);//状态(有效)
			vo.setFbackStatus(DicConstant.ZLFKZT_01);
			vo.setCreateUser(user.getAccount());// 设置创建人
			vo.setCreateTime(Pub.getCurrentDate());// 创建时间
			vo.setOrgId(user.getOrgId());
			vo.setCompanyId(user.getCompanyId());
			vo.setOemCompanyId(user.getOemCompanyId());
			//通过dao，执行插入
			dao.qualityRetroactionInsert(vo);
			voExt.setFbackId(vo.getFbackId());
			atx.setOutMsg(voExt.getRowXml(),"质量反馈新增成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }
    public void qualityRetroactionUpdate() throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	SeBuQualityFbackVO vo = new SeBuQualityFbackVO();
        	SeBuQualityFbackVO voExt=new SeBuQualityFbackVO();
			HashMap<String,String> hm;
			
			//将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			String cusBuyCount=hm.get("CUS_BUY_COUNT");
			String driStatus=hm.get("DRI_STATUS");
			String vehicleUseType=hm.get("VEHICLE_USE_TYPE");
			String dailyWork=hm.get("DAILY_WORK");
			String faultAddress=hm.get("FAULT_ADDRESS");
			String dailyRoad=hm.get("DAILY_ROAD");
			String maintenanceStatus=hm.get("MAINTENANCE_STATUS");
			String vehicleStatus=hm.get("VEHICLE_STATUS");
			String fbackApproace=hm.get("FBACK_APPROACE");
			//将hashmap映射到vo对象中,完成匹配赋值
			vo.setValue(hm);
			voExt.setValue(hm);
			vo.setCusBuyCount(cusBuyCount);
			vo.setDriStatus(driStatus);
			vo.setVehicleUseType(vehicleUseType);
			vo.setDailyWork(dailyWork);
			vo.setFaultAddress(faultAddress);
			vo.setDailyRoad(dailyRoad);
			vo.setMaintenanceStatus(maintenanceStatus);
			vo.setVehicleStatus(vehicleStatus);
			vo.setFbackApproace(fbackApproace);
			vo.setUpdateUser(user.getAccount());
			vo.setUpdateTime(Pub.getCurrentDate());
			vo.setOrgId(user.getOrgId());
			vo.setCompanyId(user.getCompanyId());
			//通过dao，执行插入
			dao.qualityRetroactionUpdate(vo);
			//返回插入结果和成功信息
			voExt.setFbackId(vo.getFbackId());
			atx.setOutMsg(voExt.getRowXml(),"质量反馈修改成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }
    public void qualityretroactionDispose() throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	try
    	{
    		SeBuQualityFbackDealVO vo = new SeBuQualityFbackDealVO();
    		SeBuQualityFbackVO FbackVO = new SeBuQualityFbackVO();
    		HashMap<String,String> hm;
    		
    		//将request流转换为hashmap结构体
    		hm = RequestUtil.getValues(request);
    		String dealRemarks=hm.get("DEAL_REMARKS");
    		String fbackId=Pub.val(request, "fbackId");
    		vo.setDealRemarks(dealRemarks);
    		vo.setFbackId(fbackId);
    		FbackVO.setFbackId(fbackId);
    		FbackVO.setFbackStatus(DicConstant.ZLFKZT_03);
    		vo.setDealOrgId(user.getOrgId());
    		vo.setDealOrgCode(user.getOrgCode());
    		vo.setDealOrgName(user.getOrgDept().getOName());
    		vo.setDealUser(user.getAccount());
    		vo.setDealDate(Pub.getCurrentDate());
    		vo.setCreateUser(user.getAccount());
    		vo.setCreateTime(Pub.getCurrentDate());
    		//通过dao，执行插入
    		dao.qualityRetroactionDispose(vo);
    		dao.qualityRetroactionUpdate(FbackVO);
    		//返回插入结果和成功信息
    		atx.setOutMsg("1","质量反馈处理成功！");
    	}
    	catch (Exception e)
    	{
    		//设置失败异常处理
    		atx.setException(e);
    		logger.error(e);
    	}
    }
    //查询已添加的配件信息
    public void searchQualityParts() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{	
			String fbackId=Pub.val(request, "fbackId");
			BaseResultSet bs = dao.searchQualityParts(page,user,conditions,fbackId);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    //查询未添加的配件信息
    public void searchPart() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{	
			String fbackId=Pub.val(request, "fbackId");
			BaseResultSet bs = dao.searchParts(page,user,conditions,fbackId);
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
            RequestWrapper request = atx.getRequest();
            try {
                HashMap<String, String> hm;
                //将request流转换为hashmap结构体
                hm = RequestUtil.getValues(request);
                String salePrice=hm.get("SALE_PRICE");
                String fbackId=hm.get("FBACK_ID");
                String partIds = hm.get("PARTIDS");//配件ID(逗号分隔)
                String partCodes = hm.get("PARTCODES");//促销配件代码(逗号分隔)
                String partNames = hm.get("PARTNAMES");//促销配件名称(逗号分隔)
                String supplierId = hm.get("SUPPLIER_ID");//促销配件名称(逗号分隔)
                String supplierCode = hm.get("SUPPLIER_CODE");//促销配件名称(逗号分隔)
                String supplierName = hm.get("SUPPLIER_NAME");//促销配件名称(逗号分隔)
                String quantity = hm.get("QUANTITY");//促销配件促销价(逗号分隔)
                String[] partIdArr = partIds.split(",");
                String[] partCodeArr = partCodes.split(",");
                String[] partNameArr = partNames.split(",");
                String[] salePriceArr = salePrice.split(",");
                String[] supplierIdArr = supplierId.split(",");
                String[] supplierCodeArr = supplierCode.split(",");
                String[] supplierNameArr = supplierName.split(",");
                String[] quantityArr= quantity.split(",");
                for (int i = 0; i < partIdArr.length; i++) {
                	SeBuQualityFbackPartVO vo = new SeBuQualityFbackPartVO();
                    vo.setPartId(partIdArr[i]);
                    vo.setFbackId(fbackId);
                    vo.setPartCode(partCodeArr[i]);
                    vo.setPartName(partNameArr[i]);
                    vo.setUnitPrice(salePriceArr[i]);
                    vo.setSupplierId(supplierIdArr[i]);
                    vo.setSupplierCode(supplierCodeArr[i]);
                    vo.setSupplierName(supplierNameArr[i]);
                    vo.setCount(quantityArr[i]);
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
     * 删除质量反馈配件
     * @throws Exception      
     */
    public void deleteParts() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
       
        try
        {
        	String mxids=Pub.val(request, "mxids");
			 dao.deletePartByMxids(mxids);
			 atx.setOutMsg("","质量反馈配件删除成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    //删除质量反馈
    public void qualityretroactionDelete() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        String fbackId = Pub.val(request, "fbackId");
        try
        {
        	   dao.qualityretroactionDelete(fbackId);
        	   atx.setOutMsg("","质量反馈删除成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 质量反馈提报
     * @throws Exception
     */
    public void qualityretroactionReport() throws Exception {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
        String fbackId = Pub.val(request, "fbackId");//质量反馈ID
        String flag = Pub.val(request, "flag");//标示符  如果1直接提报，如果是2先保存再提报
        SeBuQualityFbackVO vo = new SeBuQualityFbackVO();
        try
        {
        	QuerySet qsPartsCount =dao.queryPartsCount(fbackId);
        	if(qsPartsCount.getRowCount()>0){
        	if(flag.equals("1")){
	        	vo.setFbackId(fbackId);
        	}else if(flag.equals("2")){
        		HashMap<String,String> hm;
				hm = RequestUtil.getValues(request);
				vo.setValue(hm);
        	}
	        	vo.setUpdateUser(user.getAccount());
				vo.setUpdateTime(Pub.getCurrentDate());
				vo.setWriteDate(Pub.getCurrentDate());
				vo.setFbackStatus(DicConstant.ZLFKZT_02);
				dao.qualityretroactionReport(vo);
		        //返回更新结果和成功信息
		        atx.setOutMsg("1","提报成功！");
        	}else{
        		atx.setOutMsg("","提报前请先选怎配件！");
        	}
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 质量反馈关闭
     * @throws Exception
     */
    public void qualityretroactionClose() throws Exception {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	String fbackId = Pub.val(request, "fbackId");//质量反馈ID
    	SeBuQualityFbackVO vo = new SeBuQualityFbackVO();
    	try
    	{
    		vo.setFbackId(fbackId);
    		vo.setUpdateUser(user.getAccount());
    		vo.setUpdateTime(Pub.getCurrentDate());
    		vo.setFbackStatus(DicConstant.ZLFKZT_05);
    		
    		
    		dao.qualityretroactionReport(vo);
    		//返回更新结果和成功信息
    		atx.setOutMsg("","质量反馈关闭成功！");
    	}
    	catch (Exception e)
    	{
    		atx.setException(e);
    		logger.error(e);
    	}
    }
    /**
     * 质量反馈驳回
     * @throws Exception
     */
    public void qualityretroactionReject() throws Exception {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	String fbackId = Pub.val(request, "fbackId");//质量反馈ID
    	SeBuQualityFbackVO vo = new SeBuQualityFbackVO();
    	SeBuQualityFbackDealVO vo1 = new SeBuQualityFbackDealVO();
    	try
    	{
    		HashMap<String,String> hm;
    		hm = RequestUtil.getValues(request);
    		String rejectRemarks=hm.get("REJECT_REMARKS");
    		vo.setFbackId(fbackId);
    		vo.setUpdateUser(user.getAccount());
    		vo.setUpdateTime(Pub.getCurrentDate());
    		vo.setFbackStatus(DicConstant.ZLFKZT_04);
    		dao.qualityretroactionReport(vo);
    		vo1.setRejectRemarks(rejectRemarks);
    		vo1.setRejectDate(Pub.getCurrentDate());
    		vo1.setRejectUser(user.getAccount());
    		vo1.setFbackId(fbackId);
    		vo1.setDealOrgId(user.getOrgId());
    		vo1.setDealOrgCode(user.getOrgCode());
    		vo1.setDealOrgName(user.getOrgDept().getOName());
    		vo1.setDealUser(user.getAccount());
    		vo1.setDealDate(Pub.getCurrentDate());
    		vo1.setCreateUser(user.getAccount());
    		vo1.setCreateTime(Pub.getCurrentDate());
    		dao.insertRejectRemarks(vo1);
    		//返回更新结果和成功信息
    		atx.setOutMsg("1","质量反馈驳回成功！");
    	}
    	catch (Exception e)
    	{
    		atx.setException(e);
    		logger.error(e);
    	}
    }
}