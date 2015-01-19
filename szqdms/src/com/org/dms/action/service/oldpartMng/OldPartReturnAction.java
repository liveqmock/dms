package com.org.dms.action.service.oldpartMng;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.oldpartMng.OldPartReturnDao;
import com.org.dms.vo.service.SeBuReturnOrderVO;
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
 * 旧件回运ACTION
 * @author zts
 *
 */
public class OldPartReturnAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "OldPartReturnAction");
	private ActionContext atx = ActionContext.getContext();
	private OldPartReturnDao dao = OldPartReturnDao.getInstance(atx);
	
	 /**
     * 回运单回运查询
     * @throws Exception
     */
    public void oldPartSearch() throws Exception{
    	RequestWrapper request = atx.getRequest();
 		PageManager page = new PageManager();
 		User user = (User) atx.getSession().get(Globals.USER_KEY);
 		String conditions = RequestUtil.getConditionsWhere(request,page);
 		try
 		{
 			BaseResultSet bs = dao.oldPartSearch(page,conditions,user);
 			atx.setOutData("bs", bs);
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }	
    /**
     * 根据回运单ID查询回运单
     * @throws Exception
     */
    public void oldPartByOrderId()  throws Exception{
    	RequestWrapper request = atx.getRequest();
    	String orderId=Pub.val(request, "orderId");
    	try
		{
    		BaseResultSet bs = dao.oldPartByOrderId(orderId);
    		bs.setFieldDic("TRANS_TYPE","HYDYSFS");
 		    bs.setFieldDateFormat("PRODUCE_DATE", "yyyy-MM");
 		    bs.setFieldDateFormat("RETURN_DATE", "yyyy-MM-dd");
 		    bs.setFieldOrgDeptSimpleName("ORG_NAME");//简称
 		    bs.setFieldOrgDeptCode("ORG_CODE");
    		atx.setOutData("bs" , bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
    }
    /**
     * 查询回运清单
     * @throws Exception
     */
    public void returnPartSearch()throws Exception{
    	RequestWrapper request = atx.getRequest();
 		PageManager page = new PageManager();
 		String conditions = RequestUtil.getConditionsWhere(request,page);
 		String orderId=Pub.val(request, "orderId");
 		try
 		{
 			BaseResultSet bs = dao.returnPartSearch(page,orderId,conditions);
 			atx.setOutData("bs", bs);
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }
    /**
     * 回运
     * @throws Exception
     */
    public void oldPartReturn()throws Exception{
    	 //获取封装后的request对象
    	 RequestWrapper request = atx.getRequest();
    	 User user = (User) atx.getSession().get(Globals.USER_KEY);
    	 String orderId=Pub.val(request, "orderId");
    	 SeBuReturnOrderVO vo = new SeBuReturnOrderVO();
        try
        {
			vo.setOrderId(orderId);
			vo.setUpdateUser(user.getAccount());
			vo.setUpdateTime(Pub.getCurrentDate());
			vo.setOrderStatus(DicConstant.HYDZT_05);//回运状态
			vo.setFocusDate(Pub.getCurrentDate());//集中点回运日期
			//旧件回运
            dao.updateOldPartReturn(vo);
            atx.setOutMsg("","回运成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    
    /**
     * 导出
     * @throws Exception
     */
    public void download()throws Exception{
    	//获取封装后的request对象
    	ResponseWrapper response= atx.getResponse();
        try
        {
        	List<HeaderBean> header = new ArrayList<HeaderBean>();
	    	HeaderBean hBean = null;
	    	hBean = new HeaderBean();
    		hBean.setName("ROWNUM");
    		hBean.setTitle("序号");
    		header.add(0,hBean);
	    	hBean = new HeaderBean();
    		hBean.setName("CLAIM_NO");
    		hBean.setTitle("索赔单号");
    		header.add(1,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("FAULT_CODE");
    		hBean.setTitle("故障代码");
    		header.add(2,hBean);
    		hBean = new HeaderBean();
    		hBean = new HeaderBean();
    		hBean.setName("VIN");
    		hBean.setTitle("VIN");
    		header.add(3,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("ORDER_NO");
    		hBean.setTitle("回运单号");
    		header.add(4,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("PART_CODE");
    		hBean.setTitle("配件代码");
    		header.add(5,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("PART_NAME");
    		hBean.setTitle("配件名称");
    		header.add(6,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("BOX_NO");
    		hBean.setTitle("箱号");
    		header.add(7,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("PROSUPPLY_CODE");
    		hBean.setTitle("生产供应商代码");
    		header.add(8,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("PROSUPPLY_NAME");
    		hBean.setTitle("生产供应商名称");
    		header.add(9,hBean);
    		QuerySet qs = dao.download();
    		ExportManager.exportFile(response.getHttpResponse(), "旧件装箱", header, qs);
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    
    /**
     * 导入数据校验
     * @return
     * @throws Exception
     */
    public List<ExcelErrors> checkData()throws Exception{
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	ExcelErrors errors = null;
		List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
		QuerySet qs1=dao.checkList1(user);//索赔单号、故障代码、配件代码、供应商代码、回运单号 不一致
		QuerySet qs2=dao.checkList2(user);//校验箱号不能为空
		QuerySet qs3=dao.checkList3(user);//校验导入的数据是否重复
		if(qs1.getRowCount()>0){
			for(int i=0;i<qs1.getRowCount();i++){
				errors=new ExcelErrors();
				String rowNum=qs1.getString(i+1, "ROW_NUM");
				String orderNo=qs1.getString(i+1,"ORDER_NO");
				String faultCode=qs1.getString(i+1,"FAULT_CODE");
				String claimNo=qs1.getString(i+1,"CLAIM_NO");
				String partCode=qs1.getString(i+1,"PART_CODE");
				String supplyCode=qs1.getString(i+1,"PROSUPPLY_CODE");
				errors.setRowNum(Integer.parseInt(rowNum));
				errors.setErrorDesc("回运单号:"+orderNo+",索赔单号:"+claimNo+",故障代码:"+faultCode+",配件代码:"+partCode+",供应商代码:"+supplyCode+"与旧件回运数据不一致！");
				errorList.add(errors);
			}
		}
		if(qs2.getRowCount()>0){
			for(int i=0;i<qs2.getRowCount();i++){
				errors=new ExcelErrors();
				String rowNum =qs2.getString(i+1,"ROW_NUM");
				errors.setRowNum(Integer.parseInt(rowNum));
				errors.setErrorDesc("箱号不能空！");
				errorList.add(errors);
			}
		}
		if(qs3.getRowCount()>0){
			for(int i=0;i<qs3.getRowCount();i++){
				errors=new ExcelErrors();
				String orderNo=qs3.getString(i+1,"ORDER_NO");
				String faultCode=qs3.getString(i+1,"FAULT_CODE");
				String claimNo=qs3.getString(i+1,"CLAIM_NO");
				String partCode=qs3.getString(i+1,"PART_CODE");
				String supplyCode=qs3.getString(i+1,"PROSUPPLY_CODE");
				errors.setErrorDesc("回运单号:"+orderNo+",索赔单号:"+claimNo+",故障代码:"+faultCode+",配件代码:"+partCode+",供应商代码:"+supplyCode+"导入数据重复！");
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
     * 导入查询
     * @throws Exception
     */
    public void searchImport()throws Exception{
    	RequestWrapper request = atx.getRequest();
 		PageManager page = new PageManager();
 		User user = (User) atx.getSession().get(Globals.USER_KEY);
 		RequestUtil.getConditionsWhere(request,page);
 		try
 		{
 			BaseResultSet bs = dao.searchImport(page,user);
 			atx.setOutData("bs", bs);
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }
    
    /**
     * 导入确定事件 
     * @throws Exception
     */
    public void oldPartPackFocusImport()throws Exception{
    	//RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
 		try
 		{
 			//更新旧件回运表 装箱字段
 			dao.updateOrderDetail(user);
 			atx.setOutMsg("", "导入成功！");
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }
}
