package com.org.dms.action.service.oldpartMng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.service.oldpartMng.RecoverListSearchDao;
import com.org.dms.vo.service.SeBuRecoveryVO;
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
public class RecoverListSearchAction {
    private Logger logger = com.org.framework.log.log.getLogger(
        "recoverListSearchAction");
    private ActionContext atx = ActionContext.getContext();
    private RecoverListSearchDao dao = RecoverListSearchDao.getInstance(atx);
    
    /**
     * 追偿清单查询
     * @throws Exception
     */
    public void searchRecoverList() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.searchRecoverList(page,conditions,user);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    
    /**
     * 追偿清单调整查询
     * @throws Exception
     */
    public void searchRecoverAdjust()throws Exception{
    	RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.searchRecoverAdjust(page,conditions,user);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
    }
    
    /**
     * 追偿清单调整保存
     * @throws Exception
     */
    public void recoveryAdjustSave()throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	SeBuRecoveryVO vo=new SeBuRecoveryVO();
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			vo.setValue(hm);
			vo.setAdjustTime(Pub.getCurrentDate());
			vo.setAdjustUser(user.getAccount());
			dao.updateRecovery(vo);
			vo.setFieldDateFormat("RECOVERY_DATE","yyyy-MM-dd");
			atx.setOutMsg(vo.getRowXml(),"调整成功.");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }
    
    /***
     * 追偿清单未知费用明细
     * @throws Exception
     */
    public void searchRecoveryDetail()throws Exception{
    	RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		RequestUtil.getConditionsWhere(request,page);
		String recoveryId=Pub.val(request, "recoveryId");
		try
		{
			BaseResultSet bs = dao.searchRecoveryDetail(page,user,recoveryId);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
    }
    
    /**
     * 供应商追偿清单查询
     * @throws Exception
     */
    public void searchSupRecoverList() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	PageManager page = new PageManager();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	String conditions = RequestUtil.getConditionsWhere(request,page);
    	try
    	{
    		BaseResultSet bs = dao.searchSupRecoverList(page,conditions,user);
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    /**
     * 追偿清单查询导出
     * @throws Exception
     */
    public void download()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	ResponseWrapper response= atx.getResponse();
    	PageManager page = new PageManager();
        try {
        	String conditions = RequestUtil.getConditionsWhere(request,page);
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            int index=0;
            HeaderBean hBean = null;
            hBean = new HeaderBean();
            hBean.setName("SUPPLIER_CODE");
            hBean.setTitle("供应商代码");
            header.add(index++,hBean);
            hBean = new HeaderBean();
            hBean.setName("SUPPLIER_NAME");
            hBean.setTitle("供应商名称");
            header.add(index++,hBean);
            hBean = new HeaderBean();
            hBean.setName("RECOVERY_DATE");
            hBean.setTitle("追偿日期");
            header.add(index++,hBean);
            hBean = new HeaderBean();
            hBean.setName("CLAIM_NO");
            hBean.setTitle("索赔单号");
            header.add(index++,hBean);
            hBean = new HeaderBean();
            hBean.setName("MATERIAL_COSTS");
            hBean.setTitle("材料费");
            header.add(index++,hBean);
            hBean = new HeaderBean();
            hBean.setName("WORK_COSTS");
            hBean.setTitle("工时费");
            header.add(index++,hBean);
            hBean = new HeaderBean();
            hBean.setName("SEVEH_COSTS");
            hBean.setTitle("外出车费");
            header.add(index++,hBean);
            hBean = new HeaderBean();
            hBean.setName("MEALS_COSTS");
            hBean.setTitle("人员补助");
            header.add(index++,hBean);
            hBean.setName("UNKNOWN_COST");
            hBean.setTitle("未知费用");
            header.add(index++,hBean);
            QuerySet querySet = dao.download(conditions);
            ExportManager.exportFile(response.getHttpResponse(), "追偿清单数据", header, querySet);
        } catch (Exception e) {
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 供应商追偿清单下载
     * @throws Exception
     */
    public void downloadSup()throws Exception{
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	RequestWrapper request = atx.getRequest();
    	ResponseWrapper response= atx.getResponse();
    	PageManager page = new PageManager();
        try {
        	String conditions = RequestUtil.getConditionsWhere(request,page);
    		List<HeaderBean> header = new ArrayList<HeaderBean>();
    		int index=0;
            HeaderBean hBean = null;
            hBean = new HeaderBean();
            hBean.setName("RECOVERY_DATE");
            hBean.setTitle("追偿日期");
            header.add(index++,hBean);
            hBean = new HeaderBean();
            hBean.setName("TOTAL_COST");
            hBean.setTitle("总费用");
            header.add(index++,hBean);
            hBean = new HeaderBean();
            hBean.setName("CLAIM_TOTAL_COST");
            hBean.setTitle("服务总费用");
            header.add(index++,hBean);
            hBean = new HeaderBean();
            hBean.setName("CLAIM_MANAGE_COST");
            hBean.setTitle("服务管理费");
            header.add(index++,hBean);
            hBean = new HeaderBean();
            hBean.setName("OLDPART_MANAGE_COST");
            hBean.setTitle("旧件管理费");
            header.add(index++,hBean);
            hBean = new HeaderBean();
            hBean.setName("ADJUST_COST");
            hBean.setTitle("调整费用");
            header.add(index++,hBean);
            hBean = new HeaderBean();
            hBean.setName("UNKNOWN_TOTAL_COST");
            hBean.setTitle("未知费用");
            header.add(index++,hBean);
    		QuerySet querySet = dao.downloadSup(user,conditions);
    		ExportManager.exportFile(response.getHttpResponse(), "追偿费用数据", header, querySet);
    	} catch (Exception e) {
    		atx.setException(e);
    		logger.error(e);
    	}
    }
    
    /**
     * 追偿费用明细查询 （索赔单）
     * @throws Exception
     */
    public void searchRecoveryClaimDetail()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	PageManager page = new PageManager();
    	RequestUtil.getConditionsWhere(request,page);
    	String recoveryId=Pub.val(request,"recoveryId");
    	try
    	{
    		BaseResultSet bs = dao.searchRecoveryClaimDetail(page,recoveryId);
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    /**
     * 导出
     * @throws Exception
     */
    public void downloadClaim()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	ResponseWrapper response= atx.getResponse();
    	String recoveryId=Pub.val(request, "recoveryId");
        try {
    		List<HeaderBean> header = new ArrayList<HeaderBean>();
    		int index=0;
            HeaderBean hBean = null;
            hBean = new HeaderBean();
            hBean.setName("SUPPLIER_CODE");
            hBean.setTitle("供应商代码");
            header.add(index++,hBean);
            hBean = new HeaderBean();
            hBean.setName("SUPPLIER_NAME");
            hBean.setTitle("供应商名称");
            header.add(index++,hBean);
            hBean = new HeaderBean();
            hBean.setName("CLAIM_NO");
            hBean.setTitle("索赔单号");
            header.add(index++,hBean);
            hBean = new HeaderBean();
            hBean.setName("CLAIM_COST");
            hBean.setTitle("材料费");
            header.add(index++,hBean);
            hBean = new HeaderBean();
            hBean.setName("WORK_COSTS");
            hBean.setTitle("工时费");
            header.add(index++,hBean);
            hBean = new HeaderBean();
            hBean.setName("SEVEH_COSTS");
            hBean.setTitle("外出车费");
            header.add(index++,hBean);
            hBean = new HeaderBean();
            hBean.setName("MEALS_COSTS");
            hBean.setTitle("人员补助");
            header.add(index++,hBean);
            hBean.setName("UNKNOWN_COST");
            hBean.setTitle("未知费用");
            header.add(index++,hBean);
    		QuerySet querySet = dao.downloadClaim(recoveryId);
    		ExportManager.exportFile(response.getHttpResponse(), "索赔单追偿费用数据", header, querySet);
    	} catch (Exception e) {
    		atx.setException(e);
    		logger.error(e);
    	}
    }
    
    /**
     * 供应商追偿清单查询明细
     * @throws Exception
     */
    public void searchSuppRecoverDetailList()throws Exception{
    	RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.searchSuppRecoverDetailList(page,conditions,user);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
    }
    
    /**
     * 供应商追偿清单查询明细导出
     * @throws Exception
     */
    public void downloadSuppRecoverDetailList()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	ResponseWrapper response= atx.getResponse();
    	PageManager page = new PageManager();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
        	String conditions = RequestUtil.getConditionsWhere(request,page);
    		List<HeaderBean> header = new ArrayList<HeaderBean>();
    		int index=0;
            HeaderBean hBean = null;
            hBean = new HeaderBean();
            hBean.setName("RECOVERY_DATE");
            hBean.setTitle("追偿日期");
            header.add(index++,hBean);
            hBean = new HeaderBean();
            hBean.setName("CLAIM_NO");
            hBean.setTitle("索赔单号");
            header.add(index++,hBean);
            hBean = new HeaderBean();
            hBean.setName("CLAIM_COST");
            hBean.setTitle("材料费");
            header.add(index++,hBean);
            hBean = new HeaderBean();
            hBean.setName("WORK_COSTS");
            hBean.setTitle("工时费");
            header.add(index++,hBean);
            hBean = new HeaderBean();
            hBean.setName("SEVEH_COSTS");
            hBean.setTitle("外出车费");
            header.add(index++,hBean);
            hBean = new HeaderBean();
            hBean.setName("MEALS_COSTS");
            hBean.setTitle("人员补助");
            header.add(index++,hBean);
            hBean = new HeaderBean();
            hBean.setName("CLAIM_COST");
            hBean.setTitle("索赔保单总费用");
            header.add(index++,hBean);
            hBean = new HeaderBean();
            hBean.setName("UNKNOWN_COST");
            hBean.setTitle("其中包含未知费用");
            header.add(index++,hBean);
    		QuerySet querySet = dao.downloadSuppRecoverDetailList(conditions,user);
    		ExportManager.exportFile(response.getHttpResponse(), "追偿费用数据", header, querySet);
    	} catch (Exception e) {
    		atx.setException(e);
    		logger.error(e);
    	}
    }
    
    /**
     * 调整完成
     * @throws Exception
     */
    public void adjustFinal()throws Exception {
    	 User user = (User) atx.getSession().get(Globals.USER_KEY);
         try
         {
         	QuerySet qs = dao.queryAdjust();
         	if(qs.getRowCount()>0){
         		String sl=qs.getString(1, 1);
         		if(!"0".equals(sl)){
         			dao.updateIfAdjust(user);
                 	atx.setOutMsg("1", "调整成功.");
         		}
         	}
         }
         catch (Exception e)
         {
         	atx.setException(e);
            logger.error(e);
         }
    }
}
