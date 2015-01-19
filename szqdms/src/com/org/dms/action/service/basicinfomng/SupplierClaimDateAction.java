package com.org.dms.action.service.basicinfomng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.service.basicinfomng.SupplierClaimDateDao;
import com.org.dms.vo.part.PtBaInfoVO;
import com.org.dms.vo.part.PtBaSupplierVO;
import com.org.dms.vo.service.PtBaPartSupplierRlVO;
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

public class SupplierClaimDateAction {
	private Logger logger = com.org.framework.log.log.getLogger("SupplierClaimDateAction");
	 private ActionContext atx = ActionContext.getContext();
	 private SupplierClaimDateDao dao = SupplierClaimDateDao.getInstance(atx);
	/**
	  * 供应商索赔承担系数查询
	  * @throws Exception
	  */
	 public void supplierPartSearch()throws Exception{
		 RequestWrapper request = atx.getRequest();
		 PageManager page = new PageManager();
		 User user = (User) atx.getSession().get(Globals.USER_KEY);
		 String conditions = RequestUtil.getConditionsWhere(request,page);
		 try
		 {
			 BaseResultSet bs = dao.supplierPartSearch(page,conditions,user);
			 atx.setOutData("bs", bs);
		 }
		 catch (Exception e)
		 {
			 logger.error(e);
			 atx.setException(e);
		 }
	 }
	 
	 /**
	  * 供应商索赔系数为设置
	  * @throws Exception
	  */
	 public void rateUpdate()throws Exception{
		//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        try
        {
        	PtBaPartSupplierRlVO vo =new PtBaPartSupplierRlVO();
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			vo.setValue(hm);
			dao.updateSpR(vo);
			atx.setOutMsg(vo.getRowXml(),"保存成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
	 }

	 /**
	  * 旧件管理费系数维护
	  * @throws Exception
	  */
	 public void oldPartManageSearch()throws Exception{
		 RequestWrapper request = atx.getRequest();
		 PageManager page = new PageManager();
		 User user = (User) atx.getSession().get(Globals.USER_KEY);
		 String conditions = RequestUtil.getConditionsWhere(request,page);
		 try
		 {
			 BaseResultSet bs = dao.oldPartManageSearch(page,conditions,user);
			 atx.setOutData("bs", bs);
		 }
		 catch (Exception e)
		 {
			 logger.error(e);
			 atx.setException(e);
		 }
	 }
	 
	 /**
	  * 旧件管理费系数设置
	  * @throws Exception
	  */
	 public void oldPartManageUpdate()throws Exception{
		 RequestWrapper request = atx.getRequest();
		 try
		 {
			 PtBaInfoVO vo =new PtBaInfoVO();
			 HashMap<String,String> hm;
			 hm = RequestUtil.getValues(request);
			 vo.setValue(hm);
			 dao.updateOldManageFee(vo);
			 atx.setOutMsg(vo.getRowXml(),"保存成功.");
		 }
		 catch (Exception e)
		 {
			 //设置失败异常处理
			 atx.setException(e);
			 logger.error(e);
		 }
	 }
	 
	 /**
	  * 供应商审核权维护
	  * @throws Exception
	  */
	 public void suppClaimCheckSearch()throws Exception{
		 RequestWrapper request = atx.getRequest();
		 PageManager page = new PageManager();
		 User user = (User) atx.getSession().get(Globals.USER_KEY);
		 String conditions = RequestUtil.getConditionsWhere(request,page);
		 try
		 {
			 BaseResultSet bs = dao.suppClaimCheckSearch(page,conditions,user);
			 atx.setOutData("bs", bs);
		 }
		 catch (Exception e)
		 {
			 logger.error(e);
			 atx.setException(e);
		 }
	 }
	 
	 /**
	  * 索赔审核权限设置
	  * @throws Exception
	  */
	 public void claimCheckUpdate()throws Exception{
		 RequestWrapper request = atx.getRequest();
		 try
		 {
			 PtBaSupplierVO vo =new PtBaSupplierVO();
			 HashMap<String,String> hm;
			 hm = RequestUtil.getValues(request);
			 vo.setValue(hm);
			 dao.updateSupplier(vo);
			 vo.bindFieldToDic("IF_CLAIM_CHECK","SF");
			 atx.setOutMsg(vo.getRowXml(),"保存成功！");
		 }
		 catch (Exception e)
		 {
			 //设置失败异常处理
			 atx.setException(e);
			 logger.error(e);
		 }
	 }
	 
	 /**
	  * 导出
	  * @throws Exception
	  */
	 public void partDownload()throws Exception{
		 RequestWrapper request = atx.getRequest();
		 ResponseWrapper response= atx.getResponse();
		 PageManager page = new PageManager();
 		 User user = (User) atx.getSession().get(Globals.USER_KEY);
 		 String conditions = RequestUtil.getConditionsWhere(request,page);
         try
         {
        	 int index = 0;
        	 List<HeaderBean> header = new ArrayList<HeaderBean>();
        	 HeaderBean hBean = null;
	    	 hBean = new HeaderBean();
    		 hBean.setName("PART_CODE");
    		 hBean.setTitle("配件代码");
    		 header.add(index++,hBean);
    		 hBean = new HeaderBean();
    		 hBean.setName("PART_NAME");
    		 hBean.setTitle("配件名称");
    		 header.add(index++,hBean);
    		 hBean = new HeaderBean();
    		 hBean.setName("OLD_MANAGE_FEE");
    		 hBean.setTitle("旧件管理费系数");
    		 header.add(index++,hBean);
    		 QuerySet qs = dao.partDownload(conditions,user);
    		 ExportManager.exportFile(response.getHttpResponse(), "旧件管理费系数设置", header, qs);
         }
         catch (Exception e)
         {
        	atx.setException(e);
            logger.error(e);
         }
	 }
	 
	 /**
	  * 导入查询
	  * @throws Exception
	  */
	 public void  partImpSearch()throws Exception{
		 RequestWrapper request = atx.getRequest();
 		 PageManager page = new PageManager();
 		 User user = (User) atx.getSession().get(Globals.USER_KEY);
 		 RequestUtil.getConditionsWhere(request,page);
 		 String conditions = RequestUtil.getConditionsWhere(request,page);
 		 try
 		 {
 			 BaseResultSet bs = dao.partImpSearch(page,conditions,user);
 			 atx.setOutData("bs", bs);
 		 }
 		 catch (Exception e)
 		 {
 	   		logger.error(e);
 			atx.setException(e);
 		 }
	 }
	 
	 /**
	  * 导出错误数据
	  * @throws Exception
	  */
	 public void  expData()throws Exception{
		 RequestWrapper request = atx.getRequest();
		 ResponseWrapper response= atx.getResponse();
 		 User user = (User) atx.getSession().get(Globals.USER_KEY);
 		// String conditions = RequestUtil.getConditionsWhere(request,page);
 		 String rowStrNo=Pub.val(request, "rowStrNo");
         try
         {
        	 int index = 0;
        	 List<HeaderBean> header = new ArrayList<HeaderBean>();
        	 HeaderBean hBean = null;
	    	 hBean = new HeaderBean();
    		 hBean.setName("PART_CODE");
    		 hBean.setTitle("配件代码");
    		 header.add(index++,hBean);
    		 hBean = new HeaderBean();
    		 hBean.setName("PART_NAME");
    		 hBean.setTitle("配件名称");
    		 header.add(index++,hBean);
    		 hBean = new HeaderBean();
    		 hBean.setName("OLD_MANAGE_FEE");
    		 hBean.setTitle("旧件管理费系数");
    		 header.add(index++,hBean);
    		 String rowNum="";
    		 if(!(rowStrNo==null || "".equals(rowStrNo))){
    			 rowNum =" P.ROW_NUM IN ("+rowStrNo+")";
    		 }else{
    			 rowNum =" P.ROW_NUM IS NULL  ";
    		 }
    		 QuerySet qs = dao.expData(rowNum,user);
    		 ExportManager.exportFile(response.getHttpResponse(), "旧件管理费系数导入错误数据", header, qs);
         }
         catch (Exception e)
         {
        	atx.setException(e);
            logger.error(e);
         }
	 }
	 
	 /**
	  * 导入确定按钮
	  * @throws Exception
	  */
	 public void oldPartManageImport()throws Exception{
		 RequestWrapper request = atx.getRequest();
		 String rowStrNo=Pub.val(request, "rowStrNo");
		 User user = (User) atx.getSession().get(Globals.USER_KEY);
		 try
		 {
			 String rowNum=" 1=1 ";
			 if(!(rowStrNo==null || "".equals(rowStrNo))){
				 rowNum +=" AND T.ROW_NUM NOT IN ("+rowStrNo+")";
    		 }
			 dao.updateOldPart(rowNum,user);
			 atx.setOutMsg("","保存成功.");
		 }
		 catch (Exception e)
		 {
			 //设置失败异常处理
			 atx.setException(e);
			 logger.error(e);
		 }
	 }
}
