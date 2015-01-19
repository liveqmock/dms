package com.org.dms.action.service.milesPolicy;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.milesPolicy.MilesPolicyReportDao;
import com.org.dms.vo.service.MainVehicleVO;
import com.org.dms.vo.service.SeBuMillionGuaranteeExtVO;
import com.org.dms.vo.service.SeBuMillionGuaranteeVO;
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
 * 定保单ACTION
 * @author Administrator
 *
 */
public class MilesPolicyReportAction {

	//日志类
    private Logger logger = com.org.framework.log.log.getLogger("MilesPolicyReportAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private MilesPolicyReportDao dao = MilesPolicyReportDao.getInstance(atx);

    /**
     * 万里定保单查询
     * @throws Exception
     */
    public void milesPolicyReportSearch() throws Exception
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
			BaseResultSet bs = dao.milesPolicyReportSearch(page,user,conditions,orgId);
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
     * 万里定保单下端查询
     * @throws Exception
     */
    public void milesPolicyDelSearch() throws Exception
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
			BaseResultSet bs = dao.milesPolicyDelSearch(page,user,conditions,orgId);
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
     * 万里定保单上端查询
     * @throws Exception
     */
    public void milesPolicyOemSearch() throws Exception
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
    		BaseResultSet bs = dao.milesPolicyOemSearch(page,user,conditions,orgId);
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
    public void milesPolicyVinCheck()  throws Exception{
    	RequestWrapper request = atx.getRequest();
    	try
		{
    		String vin=request.getParamValue("diVinVal");
    		String engineNo=request.getParamValue("diEngineNoVal");
    		BaseResultSet bs = dao.vinCheckSearch(vin,engineNo);
    		bs.setFieldDic("VEHICLEUSENAME", "CLYT");
    		bs.setFieldDic("USERNAME","CLYHLX");
    		bs.setFieldDic("DRIVENAME","QDXS");
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
     * 定保单新增保存
     * @throws Exception
     */
    public void milesPolicyInsert() throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	SeBuMillionGuaranteeVO vo = new SeBuMillionGuaranteeVO();
        	SeBuMillionGuaranteeExtVO voExt=new SeBuMillionGuaranteeExtVO();
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			int vehicleMilesCount=0;
			String vehicleId=hm.get("VEHICLE_ID");
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
			String GNo=dao.getGNo();
			vo.setValue(hm);
			voExt.setValue(hm);
			vo.setGStatus(DicConstant.DBDZT_01);//定保单状态(已保存)
			vo.setStatus(DicConstant.YXBS_01);//状态(有效)
			vo.setGNo(GNo);
			vo.setCreateUser(user.getAccount());// 设置创建人
			vo.setCreateTime(Pub.getCurrentDate());// 创建时间
			vo.setOrgId(user.getOrgId());
			vo.setCompanyId(user.getCompanyId());
			vo.setOemCompanyId(user.getOemCompanyId());
			//通过dao，执行插入
			dao.milesPolicyInsert(vo);
			//返回插入结果和成功信息
			voExt.setGId(vo.getGId());;
		    voExt.setGNo(GNo);
		    voExt.setEngineType(voExt.getEngineType());
		    voExt.setUserType(voExt.getUserType());
		    voExt.setVehicleUse(voExt.getVehicleUse());
		    voExt.setGStatus(DicConstant.DBDZT_01);
			voExt.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
	        voExt.bindFieldToDic("G_STATUS","DBDZT");
	        voExt.bindFieldToDic("VEHICLENAME","CLYT");
	        voExt.bindFieldToDic("DriveForm","QDXS");
	        voExt.bindFieldToDic("UserName","CLYHLX");
			atx.setOutMsg(voExt.getRowXml(),"定保单新增成功！");
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
     * 定保单修改保存
     * @throws Exception
     */
    public void milesPolicyUpdate() throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
         	SeBuMillionGuaranteeVO vo = new SeBuMillionGuaranteeVO();
         	SeBuMillionGuaranteeExtVO voExt=new SeBuMillionGuaranteeExtVO();
			HashMap<String,String> hm;
			//将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			int vehicleMilesCount=0;
			String vehicleId=hm.get("VEHICLE_ID");
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
			String gId=hm.get("G_ID");
			String licensePlate=hm.get("LICENSE_PLATE");
			String userName=hm.get("USER_NAME");
			String userNo=hm.get("USER_NO");
			String linkMan=hm.get("LINK_MAN");
			String phone=hm.get("PHONE");
			String userAddress=hm.get("USER_ADDRESS");
			String remarks=hm.get("REMARKS");
			vo.setGId(gId);
			vo.setValue(hm);
			voExt.setValue(hm);
			vo.setLicensePlate(licensePlate);
			vo.setUserName(userName);
			vo.setUserNo(userNo);
			vo.setLinkMan(linkMan);
			vo.setPhone(phone);
			vo.setUserAddress(userAddress);
			vo.setRemarks(remarks);
			vo.setGStatus(DicConstant.DBDZT_01);//定保单状态(已保存)
			vo.setUpdateUser(user.getAccount());// 设置创建人
			vo.setUpdateTime(Pub.getCurrentDate());// 创建时间
			vo.setOrgId(user.getOrgId());
			vo.setCompanyId(user.getCompanyId());
			//通过dao，执行插入
			dao.milesPolicyUpdate(vo);
			//返回插入结果和成功信息
			voExt.setGId(vo.getGId());
		    voExt.setGNo(vo.getGNo());
		    voExt.setEngineType(voExt.getEngineType());
		    voExt.setUserType(voExt.getUserType());
		    voExt.setVehicleUse(voExt.getVehicleUse());
		    voExt.setGStatus(DicConstant.DBDZT_01);
			voExt.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
	        voExt.bindFieldToDic("G_STATUS","DBDZT");
	        voExt.bindFieldToDic("VEHICLENAME","CLYT");
	        voExt.bindFieldToDic("DriveForm","QDXS");
	        voExt.bindFieldToDic("UserName","CLYHLX");
			atx.setOutMsg(voExt.getRowXml(),"定保单修改成功！");
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
     * 定保单提报
     * @throws Exception
     */
    public void milesPolicyReport() throws Exception {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
        String gId = Pub.val(request, "gId");//定保单ID
        String flag = Pub.val(request, "flag");//标示符  如果1直接提报，如果是2先保存再提报
        SeBuMillionGuaranteeVO vo = new SeBuMillionGuaranteeVO();
        MainVehicleVO vo1 = new MainVehicleVO();
        try
        {
        	if(flag.equals("1")){
	        	vo.setGId(gId);
        	}else if(flag.equals("2")){
        		HashMap<String,String> hm;
				hm = RequestUtil.getValues(request);
				vo.setValue(hm);
        	}
	        	QuerySet qs = dao.checkGCount(gId);
	    		String id=qs.getString(1, 2);
	    		String lrunkm=qs.getString(1, "MILEAGE");
	    		QuerySet qs1 = dao.mileageSearch(gId);
	    		String mileage=qs1.getString(1, "MILEAGE");
	    		vo1.setVehicleId(id);
	    		if(mileage!=null &&!"".equals(mileage)){
	    			vo1.setMileage(mileage);
	    			vo1.setDrunkm(mileage);
	    		}
	    		if(lrunkm!=null&&!"".equals(lrunkm)){
	    			vo1.setLrunkm(lrunkm);
	    		}
	        	vo.setUpdateUser(user.getAccount());
				vo.setUpdateTime(Pub.getCurrentDate());
				vo.setGDate(Pub.getCurrentDate());
				vo.setGStatus(DicConstant.DBDZT_02);
				vo.setApplyDate(Pub.getCurrentDate());
				dao.milesPolicyUpdate(vo);
		        dao.vehicleUpdate(vo1);
		        //返回更新结果和成功信息
		        atx.setOutMsg("1","提报成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 定保单删除
     * @throws Exception
     */
    public void milesPolicyDelete() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        String gId = Pub.val(request, "gId");
        try
        {
        	//删除项目信息
        	dao.milesPolicyDelete(gId);
            atx.setOutMsg("","删除成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    
}