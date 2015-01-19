package com.org.dms.action.service.claimmng;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.service.claimmng.ClaimSearchDao;
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
 * 索赔单查询ACTION
 * @author Administrator
 *
 */
public class ClaimSearchAction {

    private Logger logger = com.org.framework.log.log.getLogger("ClaimSearchAction");
    private ActionContext atx = ActionContext.getContext();
    private ClaimSearchDao dao = ClaimSearchDao.getInstance(atx);

    /**
     * 索赔单查询(上端)
     * @throws Exception
     */
    public void claimSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			String outbuyStatus=Pub.val(request, "outbuyStatus");
			if(null==outbuyStatus||outbuyStatus.equals("")){
				outbuyStatus="-1";
			}
			BaseResultSet bs = dao.claimSearch(page,user,conditions,outbuyStatus);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    /**
     * 索赔单查询(下端)
     * @throws Exception
     */
    public void claimDelSearch() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	PageManager page = new PageManager();
    	String conditions = RequestUtil.getConditionsWhere(request,page);
    	String orgId=user.getOrgId();
    	try
    	{
    		BaseResultSet bs = dao.claimDelSearch(page,user,conditions,orgId);
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    /**
     * 索赔单查询(办事处)
     * @throws Exception
     */
    public void claimAgencySearch() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	PageManager page = new PageManager();
    	String conditions = RequestUtil.getConditionsWhere(request,page);
    	String orgId=user.getOrgId();
    	try
    	{
    		BaseResultSet bs = dao.claimAgencySearch(page,user,conditions,orgId);
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
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
    		hBean.setName("CODE");
    		hBean.setTitle("渠道商代码");
    		header.add(0,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("ONAME");
    		hBean.setTitle("渠道商名称");
    		header.add(1,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("WORK_NO");
    		hBean.setTitle("派工单号");
    		header.add(2,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("CLAIM_NO");
    		hBean.setTitle("索赔单号");
    		header.add(3,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("CLAIM_TYPE");
    		hBean.setTitle("索赔类型");
    		header.add(4,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("VIN");
    		hBean.setTitle("VIN");
    		header.add(5,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("CLAIM_STATUS");
    		hBean.setTitle("索赔单状态");
    		header.add(6,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("APPLY_DATE");
    		hBean.setTitle("提报时间");
    		header.add(7,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("REJECT_DATE");
    		hBean.setTitle("驳回时间");
    		header.add(8,hBean);
    		QuerySet qs = dao.download(conditions,user);
    		ExportManager.exportFile(response.getHttpResponse(), "索赔查询", header, qs);
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
	public void oemDownload()throws Exception{
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
			hBean.setName("ORG_CODE");
			hBean.setTitle("渠道商代码");
			header.add(0,hBean);
			hBean = new HeaderBean();
			hBean.setName("ORG_NAME");
			hBean.setTitle("渠道商名称");
			header.add(1,hBean);
			hBean = new HeaderBean();
			hBean.setName("CODE");
			hBean.setTitle("办事处代码");
			header.add(2,hBean);
			hBean = new HeaderBean();
			hBean.setName("ONAME");
			hBean.setTitle("办事处名称");
			header.add(3,hBean);
			hBean = new HeaderBean();
			hBean.setName("WORK_NO");
			hBean.setTitle("派工单号");
			header.add(4,hBean);
			hBean = new HeaderBean();
			hBean.setName("CLAIM_NO");
			hBean.setTitle("索赔单号");
			header.add(5,hBean);
			hBean = new HeaderBean();
			hBean.setName("CLAIM_TYPE");
			hBean.setTitle("索赔类型");
			header.add(6,hBean);
			hBean = new HeaderBean();
			hBean.setName("VIN");
			hBean.setTitle("VIN");
			header.add(7,hBean);
			hBean = new HeaderBean();
			hBean.setName("CLAIM_STATUS");
			hBean.setTitle("索赔单状态");
			header.add(8,hBean);
			hBean = new HeaderBean();
			hBean.setName("APPLY_DATE");
			hBean.setTitle("提报时间");
			header.add(9,hBean);
			hBean = new HeaderBean();
			hBean.setName("APPLY_DATE");
			hBean.setTitle("提报时间");
			header.add(10,hBean);
			hBean = new HeaderBean();
			hBean.setName("APPLY_DATE");
			hBean.setTitle("提报时间");
			header.add(11,hBean);
			hBean = new HeaderBean();
			hBean.setName("APPLY_DATE");
			hBean.setTitle("提报时间");
			header.add(12,hBean);
			hBean = new HeaderBean();
			hBean.setName("REJECT_DATE");
			hBean.setTitle("驳回时间");
			header.add(13,hBean);
			QuerySet qs = dao.oemDownload(conditions);
			ExportManager.exportFile(response.getHttpResponse(), "索赔查询", header, qs);
		}
		catch (Exception e)
		{
			atx.setException(e);
			logger.error(e);
		}
	}
	public void dealerDownload()throws Exception{
		//获取封装后的request对象
		RequestWrapper request = atx.getRequest();
		ResponseWrapper response= atx.getResponse();
		PageManager page = new PageManager();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			List<HeaderBean> header = new ArrayList<HeaderBean>();
			HeaderBean hBean = null;
			hBean = new HeaderBean();
			hBean.setName("ORG_CODE");
			hBean.setTitle("渠道商代码");
			header.add(0,hBean);
			hBean = new HeaderBean();
			hBean.setName("ORG_NAME");
			hBean.setTitle("渠道商名称");
			header.add(1,hBean);
			hBean = new HeaderBean();
			hBean.setName("WORK_NO");
			hBean.setTitle("派工单号");
			header.add(2,hBean);
			hBean = new HeaderBean();
			hBean.setName("CLAIM_NO");
			hBean.setTitle("索赔单号");
			header.add(3,hBean);
			hBean = new HeaderBean();
			hBean.setName("CLAIM_TYPE");
			hBean.setTitle("索赔类型");
			header.add(4,hBean);
			hBean = new HeaderBean();
			hBean.setName("VIN");
			hBean.setTitle("VIN");
			header.add(5,hBean);
			hBean = new HeaderBean();
			hBean.setName("CLAIM_STATUS");
			hBean.setTitle("索赔单状态");
			header.add(6,hBean);
			hBean = new HeaderBean();
			hBean.setName("APPLY_DATE");
			hBean.setTitle("提报时间");
			header.add(7,hBean);
			hBean = new HeaderBean();
			hBean.setName("REJECT_DATE");
			hBean.setTitle("驳回时间");
			header.add(8,hBean);
			QuerySet qs = dao.dealerDownload(conditions,user);
			ExportManager.exportFile(response.getHttpResponse(), "索赔查询", header, qs);
		}
		catch (Exception e)
		{
			atx.setException(e);
			logger.error(e);
		}
	}
	
	/**
	 * 初审费用查询
	 * @throws Exception
	 */
	public void claimCostsSearch()throws Exception{
		RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			String activityCode = Pub.val(request, "activityCode");
			String modelsCode = Pub.val(request, "modelsCode");
			String engineType = Pub.val(request, "engineType");
			BaseResultSet bs = dao.claimCostsSearch(page,user,conditions,activityCode,modelsCode,engineType);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
	/**
	 * 初审导出
	 * @throws Exception
	 */
	public void passDownload()throws Exception{
		//获取封装后的request对象
		RequestWrapper request = atx.getRequest();
		ResponseWrapper response= atx.getResponse();
		PageManager page = new PageManager();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			List<HeaderBean> header = new ArrayList<HeaderBean>();
			int i=0;
			HeaderBean hBean = null;
			hBean = new HeaderBean();
			hBean.setName("CLAIM_NO");
			hBean.setTitle("索赔单号");
			header.add(i++,hBean);
			hBean = new HeaderBean();
			hBean.setName("CLAIM_TYPE");
			hBean.setTitle("索赔单类型");
			header.add(i++,hBean);
			hBean = new HeaderBean();
			hBean.setName("VIN");
			hBean.setTitle("VIN");
			header.add(i++,hBean);
			hBean = new HeaderBean();
			hBean.setName("CLAIM_AMOUNT");
			hBean.setTitle("单子总费用");
			header.add(i++,hBean);
			hBean = new HeaderBean();
			hBean.setName("MATERIAL_COSTS");
			hBean.setTitle("材料费");
			header.add(i++,hBean);
			hBean = new HeaderBean();
			hBean.setName("OUT_AMOUNT");
			hBean.setTitle("外出总费用");
			header.add(i++,hBean);
			hBean = new HeaderBean();
			hBean.setName("OUT_COSTS");
			hBean.setTitle("一次外出");
			header.add(i++,hBean);
			hBean = new HeaderBean();
			hBean.setName("SEC_VEH_COSTS");
			hBean.setTitle("二次外出");
			header.add(i++,hBean);
			hBean = new HeaderBean();
			hBean.setName("MEALS_COSTS");
			hBean.setTitle("在途补助");
			header.add(i++,hBean);
			hBean = new HeaderBean();
			hBean.setName("SEVEH_COSTS");
			hBean.setTitle("单车服务费");
			header.add(i++,hBean);
			hBean = new HeaderBean();
			hBean.setName("TRAVEL_COSTS");
			hBean.setTitle("差旅费");
			header.add(i++,hBean);
			hBean = new HeaderBean();
			hBean.setName("OTHER_COSTS");
			hBean.setTitle("其它费用");
			header.add(i++,hBean);
			hBean = new HeaderBean();
			hBean.setName("WORK_COSTS");
			hBean.setTitle("工时费");
			header.add(i++,hBean);
			hBean = new HeaderBean();
			hBean.setName("MAINTENANCE_COSTS");
			hBean.setTitle("首保费");
			header.add(i++,hBean);
			hBean = new HeaderBean();
			hBean.setName("SERVICE_COST");
			hBean.setTitle("服务活动费");
			header.add(i++,hBean);
			hBean = new HeaderBean();
			hBean.setName("SAFE_COSTS");
			hBean.setTitle("安全检查费");
			header.add(i++,hBean);
			hBean = new HeaderBean();
			hBean.setName("TRAIN_COSTS");
			hBean.setTitle("售前培训费");
			header.add(i++,hBean);
			String activityCode = Pub.val(request, "activityCode");
			String modelsCode = Pub.val(request, "modelsCode");
			String engineType = Pub.val(request, "engineType");
			QuerySet qs = dao.passDownload(conditions,user,activityCode,modelsCode,engineType);
			ExportManager.exportFile(response.getHttpResponse(), "初审报单费用", header, qs);
		}
		catch (Exception e)
		{
			atx.setException(e);
			logger.error(e);
		}
	}
	/**
	 * 终审查询
	 * @throws Exception
	 */
	public void claimFinalCostsSearch()throws Exception{
		RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			String activityCode = Pub.val(request, "activityCode");
			String modelsCode = Pub.val(request, "modelsCode");
			String engineType = Pub.val(request, "engineType");
			BaseResultSet bs = dao.claimFinalCostsSearch(page,user,conditions,activityCode,modelsCode,engineType);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
	/**
	 * 终审导出
	 * @throws Exception
	 */
	public void finalDownload()throws Exception{
		//获取封装后的request对象
		RequestWrapper request = atx.getRequest();
		ResponseWrapper response= atx.getResponse();
		PageManager page = new PageManager();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			String activityCode = Pub.val(request, "activityCode");
			String modelsCode = Pub.val(request, "modelsCode");
			String engineType = Pub.val(request, "engineType");
			List<HeaderBean> header = new ArrayList<HeaderBean>();
			int i=0;
			HeaderBean hBean = null;
			hBean = new HeaderBean();
			hBean.setName("CLAIM_NO");
			hBean.setTitle("索赔单号");
			header.add(i++,hBean);
			hBean = new HeaderBean();
			hBean.setName("CLAIM_TYPE");
			hBean.setTitle("索赔单类型");
			header.add(i++,hBean);
			hBean = new HeaderBean();
			hBean.setName("VIN");
			hBean.setTitle("VIN");
			header.add(i++,hBean);
			hBean = new HeaderBean();
			hBean.setName("CLAIM_AMOUNT");
			hBean.setTitle("单子总费用");
			header.add(i++,hBean);
			hBean = new HeaderBean();
			hBean.setName("MATERIAL_COSTS");
			hBean.setTitle("材料费");
			header.add(i++,hBean);
			hBean = new HeaderBean();
			hBean.setName("OUT_AMOUNT");
			hBean.setTitle("外出总费用");
			header.add(i++,hBean);
			hBean = new HeaderBean();
			hBean.setName("WORK_COSTS");
			hBean.setTitle("工时费");
			header.add(i++,hBean);
			hBean = new HeaderBean();
			hBean.setName("MAINTENANCE_COSTS");
			hBean.setTitle("首保费");
			header.add(i++,hBean);
			hBean = new HeaderBean();
			hBean.setName("SERVICE_COST");
			hBean.setTitle("服务活动费");
			header.add(i++,hBean);
			hBean = new HeaderBean();
			hBean.setName("SAFE_COSTS");
			hBean.setTitle("安全检查费");
			header.add(i++,hBean);
			hBean = new HeaderBean();
			hBean.setName("TRAIN_COSTS");
			hBean.setTitle("售前培训费");
			header.add(i++,hBean);
			QuerySet qs = dao.finalDownload(conditions,user,activityCode,modelsCode,engineType);
			ExportManager.exportFile(response.getHttpResponse(), "终审报单费用", header, qs);
		}
		catch (Exception e)
		{
			atx.setException(e);
			logger.error(e);
		}
	}
}