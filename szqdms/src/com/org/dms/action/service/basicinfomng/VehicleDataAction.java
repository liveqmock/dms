package com.org.dms.action.service.basicinfomng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.service.basicinfomng.VehicleDataDao;
import com.org.dms.vo.service.MainVehicleProductVO;
import com.org.dms.vo.service.MainVehicleVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.fileimport.ExcelErrors;
import com.org.framework.fileimport.ExportManager;
import com.org.framework.fileimport.HeaderBean;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;
/**
 * 车辆数据维护ACTION
 * @author zts
 *
 */
public class VehicleDataAction {
    private Logger logger = com.org.framework.log.log.getLogger("VehicleDataAction");
    private ActionContext atx = ActionContext.getContext();
    private VehicleDataDao dao = VehicleDataDao.getInstance(atx);
    
    /**
     * 车辆信息查询
     * @throws Exception
     */
    public void vehicleSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.vehicleSearch(page,user,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    
    /**
     * 车辆信息修改
     * @throws Exception
     */
    public void vehicleInsert()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	try
    	{
    		MainVehicleVO vo = new MainVehicleVO();
    		HashMap<String,String> hm;
    		hm = RequestUtil.getValues(request);
    		String vin= hm.get("VIN");
    		String svin = vin.substring(9);
    		QuerySet qs =dao.queryVin(svin);
    		if(qs.getRowCount()>0){
    			atx.setOutMsg("1","VIN已存在,不可添加。");
    		}else{
    		vo.setValue(hm);
    		vo.setUpdateUser(user.getAccount());// 设置创建人
    		vo.setUpdateTime(Pub.getCurrentDate());// 创建时间
    		vo.setOrgId(user.getOrgId());
    		vo.setCompanyId(user.getCompanyId());
    		dao.vehicleInsert(vo);
    		vo.bindFieldToDic("USER_TYPE","CLYHLX");
    		vo.bindFieldToDic("VEHICLE_USE","CLYT");
    		vo.bindFieldToDic("DRIVE_FORM", "QDXS");
    		vo.bindFieldToDic("SALE_STATUS", "CLXSZT");
    		vo.bindFieldToDic("BLACKLISTFLAG","SF");
    		vo.bindFieldToDic("STATUS", "YXBS");
    		vo.setVehicleId(vo.getVehicleId());
    		vo.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
    		vo.setFieldDateFormat("FACTORY_DATE", "yyyy-MM-dd");
    		vo.setFieldDateFormat("MAINTENANCE_DATE", "yyyy-MM-dd");
    		atx.setOutMsg(vo.getRowXml(),"车辆信息新增成功！");
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
     * 车辆信息修改
     * @throws Exception
     */
    public void vehicleUpdate()throws Exception{
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	MainVehicleVO vo = new MainVehicleVO();
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			vo.setValue(hm);
			vo.setUpdateUser(user.getAccount());// 设置创建人
			vo.setUpdateTime(Pub.getCurrentDate());// 创建时间
			vo.setOrgId(user.getOrgId());
			vo.setCompanyId(user.getCompanyId());
			dao.vehicleUpdate(vo);
			vo.bindFieldToDic("USER_TYPE","CLYHLX");
	    	vo.bindFieldToDic("VEHICLE_USE","CLYT");
	    	vo.bindFieldToDic("DRIVE_FORM", "QDXS");
	    	vo.bindFieldToDic("SALE_STATUS", "CLXSZT");
	    	vo.bindFieldToDic("BLACKLISTFLAG","SF");
	    	vo.bindFieldToDic("STATUS", "YXBS");
	    	vo.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
	    	vo.setFieldDateFormat("FACTORY_DATE", "yyyy-MM-dd");
	    	vo.setFieldDateFormat("MAINTENANCE_DATE", "yyyy-MM-dd");
			atx.setOutMsg(vo.getRowXml(),"车辆信息修改成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }
    
    /**
     * 导入查询
     * @throws Exception
     */
    public void searchImportVehicle()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	PageManager page = new PageManager();
 		User user = (User) atx.getSession().get(Globals.USER_KEY);
 		RequestUtil.getConditionsWhere(request,page);
 		String conditions = RequestUtil.getConditionsWhere(request, page);
 		try
 		{
 			BaseResultSet bs = dao.searchImportVehicle(page,user,conditions);
 			atx.setOutData("bs", bs);
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }
    /**
     * 校验
     * @return
     * @throws Exception
     */
    public  List<ExcelErrors> checkData()throws Exception{
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	ExcelErrors errors = null;
		List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
		QuerySet qs1=dao.checkList1(user);//校验数据是否重复
		QuerySet qs2=dao.checkList2(user);//校验数据是否在车辆表中存在
		if(qs1.getRowCount()>0){
			for(int i=0;i<qs1.getRowCount();i++){
				errors=new ExcelErrors();
				String vin =qs1.getString(i+1,"VIN");
				errors.setErrorDesc("导入数据中的vin:"+vin+"重复！");
				errorList.add(errors);
			}
		}
		if(qs2.getRowCount()>0){
			for(int i=0;i<qs2.getRowCount();i++){
				errors=new ExcelErrors();
				String rowNo =qs2.getString(i+1,"ROW_NO");
				String vin =qs2.getString(i+1,"VIN");
				errors.setRowNum(Integer.parseInt(rowNo));
				errors.setErrorDesc("导入数据中的vin:"+vin+"在车辆表中存在！");
				errorList.add(errors);
			}
		}
		if(errorList!=null&&errorList.size()>0){
			return errorList;
		}else{
			return null;
		}
    }
    
    /**
     * 车辆导入
     * @throws Exception
     */
    public void VehicleImport()throws Exception{
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	RequestWrapper request = atx.getRequest();
    	try
 		{
    		String tmpNo = Pub.val(request, "tmpNo");
        	String rowNo = "";
        	if ("".equals(tmpNo)==false) {
        		rowNo = " AND T.ROW_NO NOT IN ("+tmpNo+") ";
        	}
 		    dao.insertVehicle(user,rowNo);
 			atx.setOutMsg("", "导入成功！");
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }

    /**
     *  车辆生产厂家规则查询
     * @throws Exception
     */
    public void vehicleProductSearch()throws Exception {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.vehicleProductSearch(page,user,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
    }
    
    /**
     * 新增
     * @throws Exception
     */
    public void productInsert()throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	MainVehicleProductVO vo = new MainVehicleProductVO();
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			vo.setValue(hm);
			vo.setCreateUser(user.getAccount());// 设置创建人
			vo.setCreateTime(Pub.getCurrentDate());// 创建时间
			dao.insertProduct(vo);
			atx.setOutMsg(vo.getRowXml(),"新增成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }
    
    /**
     * 修改
     * @throws Exception
     */
    public void productUpdate()throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	MainVehicleProductVO vo = new MainVehicleProductVO();
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			vo.setValue(hm);
			vo.setUpdateUser(user.getAccount());// 修改人
			vo.setUpdateTime(Pub.getCurrentDate());// 修改时间
			dao.updateProduct(vo);
			atx.setOutMsg(vo.getRowXml(),"修改成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }
    
    /**
     * 删除
     * @throws Exception
     */
    public void vehicleProductDelete() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        String vehicleProId = Pub.val(request, "vehicleProId");
        try
        {
        	dao.productDelete(vehicleProId);
            atx.setOutMsg("","删除成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
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
            
            hBean = new HeaderBean();
            hBean.setName("ENGINE_NO");
            hBean.setTitle("发动机号");
            header.add(1,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("MODELS_CODE");
            hBean.setTitle("车型代码");
            header.add(2,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("ENGINE_TYPE");
            hBean.setTitle("发动机型号");
            header.add(3,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("CERTIFICATE");
            hBean.setTitle("合格证号");
            header.add(4,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("BUY_DATE");
            hBean.setTitle("购车日期");
            header.add(5,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("FACTORY_DATE");
            hBean.setTitle("车辆出厂日期");
            header.add(6,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("MAINTENANCE_DATE");
            hBean.setTitle("首保日期");
            header.add(7,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("CERTIFICATEDATE");
            hBean.setTitle("合格发证日期");
            header.add(8,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("GUARANTEE_NO");
            hBean.setTitle("保修卡号");
            header.add(9,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SALE_STATUS");
            hBean.setTitle("车辆销售状态");
            header.add(10,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("LICENSE_PLATE");
            hBean.setTitle("车牌号码");
            header.add(11,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("USER_NAME");
            hBean.setTitle("用户姓名");
            header.add(12,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("USER_NO");
            hBean.setTitle("身份证号");
            header.add(13,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("LINK_MAN");
            hBean.setTitle("联系人");
            header.add(14,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("PHONE");
            hBean.setTitle("联系电话");
            header.add(15,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("USER_ADDRESS");
            hBean.setTitle("地址");
            header.add(16,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("INSIDECODE");
            hBean.setTitle("内部车型编号");
            header.add(17,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("CONFIGURE");
            hBean.setTitle("车辆配置");
            header.add(18,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("CONTRACTAREANO");
            hBean.setTitle("合同号");
            header.add(19,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("PRODUCTLINECODE");
            hBean.setTitle("系列");
            header.add(20,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("BLACKLISTFLAG");
            hBean.setTitle("黑名单");
            header.add(21,hBean);
            
            QuerySet querySet = dao.expData(conditions,user);
            ExportManager.exportFile(response.getHttpResponse(), "wrongVIN", header, querySet);
        } catch (Exception e) {
        	atx.setException(e);
            logger.error(e);
        }
    }
}
