package com.org.dms.action.service.reportForms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.service.reportForms.partRepairAmountReportDao;
import com.org.frameImpl.Constant;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.fileimport.ExportManager;
import com.org.framework.fileimport.HeaderBean;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;

/**
 * 报表查询ACTION
 * @author Administrator
 *
 */
public class partRepairAmountReportAction {

    private Logger logger = com.org.framework.log.log.getLogger("ClaimSearchAction");
    private ActionContext atx = ActionContext.getContext();
    private partRepairAmountReportDao dao = partRepairAmountReportDao.getInstance(atx);
    private ResponseWrapper response = atx.getResponse();

    /**
     * 零件保修更换数量台帐																							
     * @throws Exception
     */
    public void partRepairSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		try
		{
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			String year=hm.get("APPLY_DATE");
			String partCode=hm.get("PART_CODE");
			String partName=hm.get("PART_NAME");
			String bYear=String.valueOf(Integer.valueOf(year)-1);
			BaseResultSet bs = dao.partRepairSearch(page,user,year,partCode,partName,bYear);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    
    /**
     * 供应商追偿明细表																							
     * @throws Exception
     */
    public void supRepairSearch() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	PageManager page = new PageManager();
    	try
    	{
    		HashMap<String,String> hm;
    		hm = RequestUtil.getValues(request);
    		String supCode =hm.get("SUPPLIER_CODE");
    		String supName =hm.get("SUPPLIER_NAME");
    		String year =hm.get("YEAR");
    	    dao.delete(year);
    	    dao.callProcedure(year);
    		BaseResultSet bs = dao.supRepairSearch(page,user,supCode,supName,year);
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    /**
     * 强定保次数查询																					
     * @throws Exception
     */
    public void claimQdbSearch() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	PageManager page = new PageManager();
    	String conditions = RequestUtil.getConditionsWhere(request, page);
    	try
    	{
    		BaseResultSet bs = dao.claimQdbSearch(page,user,conditions);
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    /**
     * 里程差异查询																					
     * @throws Exception
     */
    public void claimLccySearch() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	PageManager page = new PageManager();
    	String conditions = RequestUtil.getConditionsWhere(request, page);
    	try
    	{
    		BaseResultSet bs = dao.claimLccySearch(page,user,conditions);
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    /**
     * 激励金额查询																					
     * @throws Exception
     */
    public void claimXjjlSearch() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	PageManager page = new PageManager();
    	String conditions = RequestUtil.getConditionsWhere(request, page);
    	try
    	{
    		BaseResultSet bs = dao.claimXjjlSearch(page,user,conditions);
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    /**
     * 车辆维修频次查询																					
     * @throws Exception
     */
    public void vehicleRepairCountSearch() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	PageManager page = new PageManager();
    	String conditions = RequestUtil.getConditionsWhere(request, page);
    	try
    	{
    		BaseResultSet bs = dao.vehicleRepairCountSearch(page,user,conditions);
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    /**
     * 服务站旧件数据统计表																				
     * @throws Exception
     */
    public void oldPartCountSearch() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	PageManager page = new PageManager();
    	String conditions = RequestUtil.getConditionsWhere(request, page);
    	try
    	{
    		BaseResultSet bs = dao.oldPartCountSearch(page,user,conditions);
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    /**
     * 供应商旧件数据统计表																			
     * @throws Exception
     */
    public void supOldPartCountSearch() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	PageManager page = new PageManager();
    	String conditions = RequestUtil.getConditionsWhere(request, page);
    	try
    	{
    		BaseResultSet bs = dao.supOldPartCountSearch(page,user,conditions);
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    /**
     * 陕重汽全国服务站返回旧件																			
     * @throws Exception
     */
    public void oldPartReturnCountSearch() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	PageManager page = new PageManager();
    	String conditions = RequestUtil.getConditionsWhere(request, page);
    	try
    	{
    		BaseResultSet bs = dao.oldPartReturnCountSearch(page,user,conditions);
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    /**
     * 旧件管理费用统计															
     * @throws Exception
     */
    public void oldPartManageSearch() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	PageManager page = new PageManager();
    	String conditions = RequestUtil.getConditionsWhere(request, page);
    	try
    	{
    		BaseResultSet bs = dao.oldPartManageSearch(page,user,conditions);
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    /**
     * 强，定保索赔单查询
     * @throws Exception
     */
    public void qdbDownload()throws Exception{
    	
    	try {
    		//获取封装后的request对象
    		RequestWrapper request = atx.getRequest();
        	PageManager page = new PageManager();
     		String conditions = RequestUtil.getConditionsWhere(request,page);
    		// 定义查询分页对象
    		// 将request流中的信息转化为where条件
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
    		hBean.setName("BSCMC");
    		hBean.setTitle("办事处名称");
    		header.add(2,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("CLAIM_NO");
    		hBean.setTitle("索赔单号");
    		header.add(3,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("CLAIM_TYPE");
    		hBean.setTitle("索赔单类型");
    		header.add(4,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("VIN");
    		hBean.setTitle("VIN");
    		header.add(5,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("MODELS_CODE");
    		hBean.setTitle("车辆型号");
    		header.add(6,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("BUY_DATE");
    		hBean.setTitle("购车日期");
    		header.add(7,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("OLDPART_FINAL_DATE");
    		hBean.setTitle("终审日期");
    		header.add(8,hBean);
    		
    		QuerySet querySet = dao.Qdbdownload(conditions);
    		ExportManager.exportFile(response.getHttpResponse(), "服务商车辆强，定保统计", header, querySet);
    	} catch (Exception e) {
    		atx.setException(e);
    		logger.error(e);
    	}
    }
    /**
     * 里程差异查询
     * @throws Exception
     */
    public void LccyDownload()throws Exception{
    	
    	try {
    		//获取封装后的request对象
    		RequestWrapper request = atx.getRequest();
    		PageManager page = new PageManager();
    		String conditions = RequestUtil.getConditionsWhere(request,page);
    		// 定义查询分页对象
    		// 将request流中的信息转化为where条件
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
    		hBean.setName("BSCMC");
    		hBean.setTitle("办事处名称");
    		header.add(2,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("CLAIM_NO");
    		hBean.setTitle("索赔单号");
    		header.add(3,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("CLAIM_TYPE");
    		hBean.setTitle("索赔单类型");
    		header.add(4,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("VIN");
    		hBean.setTitle("VIN");
    		header.add(5,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("MODELS_CODE");
    		hBean.setTitle("车辆型号");
    		header.add(6,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("BUY_DATE");
    		hBean.setTitle("购车日期");
    		header.add(7,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("OLDPART_FINAL_DATE");
    		hBean.setTitle("终审日期");
    		header.add(8,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("MILEAGE");
    		hBean.setTitle("报单提报里程");
    		header.add(9,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("GPS_MILEAGE");
    		hBean.setTitle("GPS调取里程");
    		header.add(10,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("LCC");
    		hBean.setTitle("差异值");
    		header.add(11,hBean);
    		
    		QuerySet querySet = dao.LccyDownload(conditions);
    		ExportManager.exportFile(response.getHttpResponse(), "里程差异统计表", header, querySet);
    	} catch (Exception e) {
    		atx.setException(e);
    		logger.error(e);
    	}
    }
    /**
     * 服务商激励金额统计
     * @throws Exception
     */
    public void jljeDownload()throws Exception{
    	
    	try {
    		//获取封装后的request对象
    		RequestWrapper request = atx.getRequest();
    		PageManager page = new PageManager();
    		String conditions = RequestUtil.getConditionsWhere(request,page);
    		// 定义查询分页对象
    		// 将request流中的信息转化为where条件
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
    		hBean.setName("BSCMC");
    		hBean.setTitle("办事处名称");
    		header.add(2,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("CLAIM_NO");
    		hBean.setTitle("索赔单号");
    		header.add(3,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("CLAIM_TYPE");
    		hBean.setTitle("索赔单类型");
    		header.add(4,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("VIN");
    		hBean.setTitle("VIN");
    		header.add(5,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("MODELS_CODE");
    		hBean.setTitle("车辆型号");
    		header.add(6,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("BUY_DATE");
    		hBean.setTitle("购车日期");
    		header.add(7,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("OLDPART_FINAL_DATE");
    		hBean.setTitle("终审日期");
    		header.add(8,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("XJJE");
    		hBean.setTitle("星级金额");
    		header.add(9,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("JLJE");
    		hBean.setTitle("激励金额");
    		header.add(10,hBean);
    		
    		QuerySet querySet = dao.jljeDownload(conditions);
    		ExportManager.exportFile(response.getHttpResponse(), "里程差异统计表", header, querySet);
    	} catch (Exception e) {
    		atx.setException(e);
    		logger.error(e);
    	}
    }
    /**
     * 单车维修频次统计
     * @throws Exception
     */
    public void vehicleRepairCounDownload()throws Exception{
    	
    	try {
    		//获取封装后的request对象
    		RequestWrapper request = atx.getRequest();
    		PageManager page = new PageManager();
    		String conditions = RequestUtil.getConditionsWhere(request,page);
    		// 定义查询分页对象
    		// 将request流中的信息转化为where条件
    		List<HeaderBean> header = new ArrayList<HeaderBean>();
    		HeaderBean hBean = null;
    		hBean = new HeaderBean();
    		hBean.setName("VIN");
    		hBean.setTitle("VIN");
    		header.add(0,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("BDS");
    		hBean.setTitle("报单数");
    		header.add(1,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("WXS");
    		hBean.setTitle("维修数");
    		header.add(2,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("JJS");
    		hBean.setTitle("旧件数");
    		header.add(3,hBean);
    	 
    		QuerySet querySet = dao.vehicleRepairCounDownload(conditions);
    		ExportManager.exportFile(response.getHttpResponse(), "里程差异统计表", header, querySet);
    	} catch (Exception e) {
    		atx.setException(e);
    		logger.error(e);
    	}
    }
    /**
     * 服务站旧件数据统计表
     * @throws Exception
     */
    public void oldPartCountDownload()throws Exception{
    	
    	try {
    		//获取封装后的request对象
    		RequestWrapper request = atx.getRequest();
    		PageManager page = new PageManager();
    		String conditions = RequestUtil.getConditionsWhere(request,page);
    		// 定义查询分页对象
    		// 将request流中的信息转化为where条件
    		List<HeaderBean> header = new ArrayList<HeaderBean>();
    		HeaderBean hBean = null;
    		hBean = new HeaderBean();
    		hBean.setName("BSC");
    		hBean.setTitle("办事处名称");
    		header.add(0,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("JZD");
    		hBean.setTitle("集中点名称");
    		header.add(1,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("FWZ");
    		hBean.setTitle("服务站名称");
    		header.add(2,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("PRODUCE_DATE");
    		hBean.setTitle("旧件产生月份");
    		header.add(3,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("SHOULD_COUNT");
    		hBean.setTitle("报单数量");
    		header.add(4,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("OUGHT_COUNT");
    		hBean.setTitle("返集中点数量");
    		header.add(5,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("ALREADY_IN");
    		hBean.setTitle("返西安库数量");
    		header.add(6,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("ALREADY_OUT");
    		hBean.setTitle("供应商出库数量");
    		header.add(7,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("ACTUL_COUNT");
    		hBean.setTitle("供应商异议数量");
    		header.add(8,hBean);
    		
    		QuerySet querySet = dao.oldPartCountDownload(conditions);
    		ExportManager.exportFile(response.getHttpResponse(), "里程差异统计表", header, querySet);
    	} catch (Exception e) {
    		atx.setException(e);
    		logger.error(e);
    	}
    }
    /**
     * 供应商旧件数据统计表
     * @throws Exception
     */
    public void supOldPartCountDownload()throws Exception{
    	
    	try {
    		//获取封装后的request对象
    		RequestWrapper request = atx.getRequest();
    		PageManager page = new PageManager();
    		String conditions = RequestUtil.getConditionsWhere(request,page);
    		// 定义查询分页对象
    		// 将request流中的信息转化为where条件
    		List<HeaderBean> header = new ArrayList<HeaderBean>();
    		HeaderBean hBean = null;
    		
    		hBean = new HeaderBean();
    		hBean.setName("PROSUPPLY_NAME");
    		hBean.setTitle("供应商名称");
    		header.add(0,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("BSC");
    		hBean.setTitle("办事处名称");
    		header.add(1,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("FWZ");
    		hBean.setTitle("服务站名称");
    		header.add(2,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("PRODUCE_DATE");
    		hBean.setTitle("旧件产生月份");
    		header.add(3,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("SHOULD_COUNT");
    		hBean.setTitle("报单数量");
    		header.add(4,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("OUGHT_COUNT");
    		hBean.setTitle("返集中点数量");
    		header.add(5,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("ALREADY_IN");
    		hBean.setTitle("返西安库数量");
    		header.add(6,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("ALREADY_OUT");
    		hBean.setTitle("供应商出库数量");
    		header.add(7,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("ACTUL_COUNT");
    		hBean.setTitle("供应商异议数量");
    		header.add(8,hBean);
    		
    		QuerySet querySet = dao.supOldPartCountDownload(conditions);
    		ExportManager.exportFile(response.getHttpResponse(), "里程差异统计表", header, querySet);
    	} catch (Exception e) {
    		atx.setException(e);
    		logger.error(e);
    	}
    }
    /**
     * 陕重汽全国服务站返回旧件
     * @throws Exception
     */
    public void oldPartReturnCountDownload()throws Exception{
    	
    	try {
    		//获取封装后的request对象
    		RequestWrapper request = atx.getRequest();
    		PageManager page = new PageManager();
    		String conditions = RequestUtil.getConditionsWhere(request,page);
    		// 定义查询分页对象
    		// 将request流中的信息转化为where条件
    		List<HeaderBean> header = new ArrayList<HeaderBean>();
    		HeaderBean hBean = null;
    		
    		hBean = new HeaderBean();
    		hBean.setName("WAREHOUSE_NAME");
    		hBean.setTitle("旧件库房");
    		header.add(0,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("BSCMC");
    		hBean.setTitle("办事处名称");
    		header.add(1,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("FWZDM");
    		hBean.setTitle("服务站代码");
    		header.add(2,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("FWZJC");
    		hBean.setTitle("服务站简称");
    		header.add(3,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("FWZMC");
    		hBean.setTitle("服务站名称");
    		header.add(4,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("SHOULD_COUNT");
    		hBean.setTitle("报单数量");
    		header.add(5,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("OUGHT_COUNT");
    		hBean.setTitle("返集中点数量");
    		header.add(6,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("ALREADY_IN");
    		hBean.setTitle("返西安库数量");
    		header.add(7,hBean);
    		QuerySet querySet = dao.oldPartReturnCountDownload(conditions);
    		ExportManager.exportFile(response.getHttpResponse(), "陕重汽全国服务站返回旧件记录", header, querySet);
    	} catch (Exception e) {
    		atx.setException(e);
    		logger.error(e);
    	}
    }
    /**
     * 旧件管理费用统计
     * @throws Exception
     */
    public void oldPartManageDownload()throws Exception{
    	
    	try {
    		//获取封装后的request对象
    		RequestWrapper request = atx.getRequest();
    		PageManager page = new PageManager();
    		String conditions = RequestUtil.getConditionsWhere(request,page);
    		// 定义查询分页对象
    		// 将request流中的信息转化为where条件
    		List<HeaderBean> header = new ArrayList<HeaderBean>();
    		HeaderBean hBean = null;
    		
    		hBean = new HeaderBean();
    		hBean.setName("SUPPLIER_CODE");
    		hBean.setTitle("供应商代码");
    		header.add(0,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("SUPPLIER_NAME");
    		hBean.setTitle("供应商名称");
    		header.add(1,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("PART_CODE");
    		hBean.setTitle("配件代码");
    		header.add(2,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("PART_NAME");
    		hBean.setTitle("配件名称");
    		header.add(3,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("SE_REPRICE");
    		hBean.setTitle("追偿单价");
    		header.add(4,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("OLD_MANAGE_FEE");
    		hBean.setTitle("旧件管理系数");
    		header.add(5,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("OUT_AMOUNT");
    		hBean.setTitle("旧件出库数量");
    		header.add(6,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("OUT_JE");
    		hBean.setTitle("管理费金额");
    		header.add(7,hBean);
    		QuerySet querySet = dao.oldPartManageDownload(conditions);
    		ExportManager.exportFile(response.getHttpResponse(), "旧件管理费用统计", header, querySet);
    	} catch (Exception e) {
    		atx.setException(e);
    		logger.error(e);
    	}
    }
    /**
     * 零件保修更换数量台帐导出
     * @throws Exception
     */
    public void download()throws Exception{

        try {
        	//获取封装后的request对象
        	RequestWrapper request = atx.getRequest();
        	HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			String year=hm.get("APPLY_DATE");
			String partCode=hm.get("PART_CODE");
			String partName=hm.get("PART_NAME");
			String bYear=String.valueOf(Integer.valueOf(year)-1);
        	// 定义查询分页对象
            // 将request流中的信息转化为where条件
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;
            hBean = new HeaderBean();
            hBean.setName("PART_CODE");
            hBean.setTitle("配件代码");
            header.add(0,hBean);

            hBean = new HeaderBean();
            hBean.setName("PART_NAME");
            hBean.setTitle("配件名称");
            header.add(1,hBean);

            hBean = new HeaderBean();
            hBean.setName("F_POSITION_NAME");
            hBean.setTitle("配件部位");
            header.add(2,hBean);

            hBean = new HeaderBean();
            hBean.setName("B_OLD_PART_COUNT01");
            hBean.setTitle("上一年1月");
            header.add(3,hBean);

            hBean = new HeaderBean();
            hBean.setName("OLD_PART_COUNT01");
            hBean.setTitle("1月");
            header.add(4,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("TB01");
            hBean.setTitle("同比增长率%");
            header.add(5,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("B_OLD_PART_COUNT02");
            hBean.setTitle("上一年2月");
            header.add(6,hBean);

            hBean = new HeaderBean();
            hBean.setName("OLD_PART_COUNT02");
            hBean.setTitle("2月");
            header.add(7,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("TB02");
            hBean.setTitle("同比增长率%");
            header.add(8,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("B_OLD_PART_COUNT03");
            hBean.setTitle("上一年3月");
            header.add(9,hBean);

            hBean = new HeaderBean();
            hBean.setName("OLD_PART_COUNT03");
            hBean.setTitle("3月");
            header.add(10,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("TB03");
            hBean.setTitle("同比增长率%");
            header.add(11,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("B_OLD_PART_COUNT04");
            hBean.setTitle("上一年4月");
            header.add(12,hBean);

            hBean = new HeaderBean();
            hBean.setName("OLD_PART_COUNT04");
            hBean.setTitle("4月");
            header.add(13,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("TB04");
            hBean.setTitle("同比增长率%");
            header.add(14,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("B_OLD_PART_COUNT05");
            hBean.setTitle("上一年5月");
            header.add(15,hBean);

            hBean = new HeaderBean();
            hBean.setName("OLD_PART_COUNT05");
            hBean.setTitle("5月");
            header.add(16,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("TB05");
            hBean.setTitle("同比增长率%");
            header.add(17,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("B_OLD_PART_COUNT06");
            hBean.setTitle("上一年1月");
            header.add(18,hBean);

            hBean = new HeaderBean();
            hBean.setName("OLD_PART_COUNT06");
            hBean.setTitle("6月");
            header.add(19,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("TB06");
            hBean.setTitle("同比增长率%");
            header.add(20,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("B_OLD_PART_COUNT07");
            hBean.setTitle("上一年7月");
            header.add(21,hBean);

            hBean = new HeaderBean();
            hBean.setName("OLD_PART_COUNT07");
            hBean.setTitle("7月");
            header.add(22,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("TB07");
            hBean.setTitle("同比增长率%");
            header.add(23,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("B_OLD_PART_COUNT08");
            hBean.setTitle("上一年8月");
            header.add(24,hBean);

            hBean = new HeaderBean();
            hBean.setName("OLD_PART_COUNT01");
            hBean.setTitle("8月");
            header.add(25,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("TB08");
            hBean.setTitle("同比增长率%");
            header.add(26,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("B_OLD_PART_COUNT09");
            hBean.setTitle("上一年9月");
            header.add(27,hBean);

            hBean = new HeaderBean();
            hBean.setName("OLD_PART_COUNT09");
            hBean.setTitle("9月");
            header.add(28,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("TB09");
            hBean.setTitle("同比增长率%");
            header.add(29,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("B_OLD_PART_COUNT10");
            hBean.setTitle("上一年10月");
            header.add(30,hBean);

            hBean = new HeaderBean();
            hBean.setName("OLD_PART_COUNT10");
            hBean.setTitle("10月");
            header.add(31,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("TB10");
            hBean.setTitle("同比增长率%");
            header.add(32,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("B_OLD_PART_COUNT11");
            hBean.setTitle("上一年11月");
            header.add(33,hBean);

            hBean = new HeaderBean();
            hBean.setName("OLD_PART_COUNT11");
            hBean.setTitle("11月");
            header.add(34,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("TB11");
            hBean.setTitle("同比增长率%");
            header.add(35,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("B_OLD_PART_COUNT12");
            hBean.setTitle("上一年12月");
            header.add(36,hBean);

            hBean = new HeaderBean();
            hBean.setName("OLD_PART_COUNT12");
            hBean.setTitle("12月");
            header.add(37,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("TB12");
            hBean.setTitle("同比增长率%");
            header.add(38,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("ZSL");
            hBean.setTitle("1-12月总数量");
            header.add(39,hBean);
            
            QuerySet querySet = dao.download(year,partCode,partName,bYear);
            ExportManager.exportFile(response.getHttpResponse(), "LJBXGH", header, querySet);
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 供应商追偿明细表导出
     * @throws Exception
     */
    public void supDownload()throws Exception{
    	
    	try {
    		//获取封装后的request对象
    		RequestWrapper request = atx.getRequest();
    		HashMap<String,String> hm;
    		hm = RequestUtil.getValues(request);
    		String supCode =hm.get("SUPPLIER_CODE");
    		String supName =hm.get("SUPPLIER_NAME");
    		String year =hm.get("YEAR");
    		int qYear=Integer.valueOf(year)-2;
    		int bYear=Integer.valueOf(year)-1;
    		// 定义查询分页对象
    		// 将request流中的信息转化为where条件
    		List<HeaderBean> header = new ArrayList<HeaderBean>();
    		HeaderBean hBean = null;
    		hBean = new HeaderBean();
    		hBean.setName("SUPPLIER_CODE");
    		hBean.setTitle("供应商代码");
    		header.add(0,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("SUPPLIER_NAME");
    		hBean.setTitle("供应商名称");
    		header.add(1,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("LAST_MONTH");
    		hBean.setTitle(""+bYear+""+"年12月");
    		header.add(2,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("FIRST_JAN");
    		hBean.setTitle(""+qYear+""+"年1月");
    		header.add(3,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("SECOND_JAN");
    		hBean.setTitle(""+bYear+""+"年1月");
    		header.add(4,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("THIRD_JAN");
    		hBean.setTitle(""+year+""+"年1月");
    		header.add(5,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("RATE_JAN_YOY");
    		hBean.setTitle("同比增长率");
    		header.add(6,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("RATE_JAN_MOM");
    		hBean.setTitle("环比增长率");
    		header.add(7,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("FIRST_FEB");
    		hBean.setTitle(""+qYear+""+"年2月");
    		header.add(8,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("SECOND_FEB");
    		hBean.setTitle(""+bYear+""+"年2月");
    		header.add(9,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("THIRD_FEB");
    		hBean.setTitle(""+year+""+"年2月");
    		header.add(10,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("RATE_FEB_YOY");
    		hBean.setTitle("同比增长率%");
    		header.add(11,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("RATE_FEB_MOM");
    		hBean.setTitle("环比增长率%");
    		header.add(12,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("FIRST_MAR");
    		hBean.setTitle(""+qYear+""+"年3月");
    		header.add(13,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("SECOND_MAR");
    		hBean.setTitle(""+bYear+""+"年2月");
    		header.add(14,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("THIRD_MAR");
    		hBean.setTitle(""+year+""+"年2月");
    		header.add(15,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("RATE_MAR_YOY");
    		hBean.setTitle("同比增长率%");
    		header.add(16,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("RATE_MAR_YOY");
    		hBean.setTitle("环比增长率%");
    		header.add(17,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("FIRST_APR");
    		hBean.setTitle(""+qYear+""+"年4月");
    		header.add(18,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("SECOND_APR");
    		hBean.setTitle(""+bYear+""+"年4月");
    		header.add(19,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("THIRD_APR");
    		hBean.setTitle(""+year+""+"年3月");
    		header.add(20,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("RATE_APR_YOY");
    		hBean.setTitle("同比增长率%");
    		header.add(21,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("RATE_APR_MOM");
    		hBean.setTitle("环比增长率%");
    		header.add(22,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("FIRST_MAY");
    		hBean.setTitle(""+qYear+""+"年5月");
    		header.add(23,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("SECOND_MAY");
    		hBean.setTitle(""+bYear+""+"年5月");
    		header.add(24,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("THIRD_MAY");
    		hBean.setTitle(""+year+""+"年5月");
    		header.add(25,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("RATE_MAY_YOY");
    		hBean.setTitle("同比增长率%");
    		header.add(26,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("RATE_MAY_MOM");
    		hBean.setTitle("环比增长率%");
    		header.add(27,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("FIRST_JUN");
    		hBean.setTitle(""+qYear+""+"年6月");
    		header.add(28,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("SECOND_JUN");
    		hBean.setTitle(""+bYear+""+"年6月");
    		header.add(29,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("THIRD_JUN");
    		hBean.setTitle(""+year+""+"年6月");
    		header.add(30,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("RATE_JUN_YOY");
    		hBean.setTitle("同比增长率%");
    		header.add(31,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("RATE_JUN_MOM");
    		hBean.setTitle("环比增长率%");
    		header.add(32,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("FIRST_JULY");
    		hBean.setTitle(""+qYear+""+"年7月");
    		header.add(33,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("SECOND_JULY");
    		hBean.setTitle(""+bYear+""+"年7月");
    		header.add(34,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("THIRD_JULY");
    		hBean.setTitle(""+year+""+"年7月");
    		header.add(35,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("RATE_JULY_YOY");
    		hBean.setTitle("同比增长率%");
    		header.add(36,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("RATE_JULY_MOM");
    		hBean.setTitle("环比增长率%");
    		header.add(37,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("FIRST_AUGUST");
    		hBean.setTitle(""+qYear+""+"年8月");
    		header.add(38,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("SECOND_AUGUST");
    		hBean.setTitle(""+bYear+""+"年8月");
    		header.add(39,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("THIRD_AUGUST");
    		hBean.setTitle(""+year+""+"年8月");
    		header.add(40,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("RATE_AUGUST_YOY");
    		hBean.setTitle("同比增长率%");
    		header.add(41,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("RATE_AUGUST_MOM");
    		hBean.setTitle("环比增长率%");
    		header.add(42,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("FIRST_SEPT");
    		hBean.setTitle(""+qYear+""+"年9月");
    		header.add(43,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("SECOND_SEPT");
    		hBean.setTitle(""+bYear+""+"年9月");
    		header.add(44,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("THIRD_SEPT");
    		hBean.setTitle(""+year+""+"年9月");
    		header.add(45,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("RATE_SEPT_YOY");
    		hBean.setTitle("同比增长率%");
    		header.add(46,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("RATE_SEPT_MOM");
    		hBean.setTitle("环比增长率%");
    		header.add(47,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("FIRST_OCT");
    		hBean.setTitle(""+qYear+""+"年10月");
    		header.add(48,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("SECOND_OCT");
    		hBean.setTitle(""+bYear+""+"年10月");
    		header.add(49,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("THIRD_OCT");
    		hBean.setTitle(""+year+""+"年10月");
    		header.add(50,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("RATE_OCT_YOY");
    		hBean.setTitle("同比增长率%");
    		header.add(51,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("RATE_OCT_MOM");
    		hBean.setTitle("环比增长率%");
    		header.add(52,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("FIRST_NOV");
    		hBean.setTitle(""+qYear+""+"年11月");
    		header.add(53,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("SECOND_NOV");
    		hBean.setTitle(""+bYear+""+"年11月");
    		header.add(54,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("THIRD_NOV");
    		hBean.setTitle(""+year+""+"年11月");
    		header.add(55,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("RATE_NOV_YOY");
    		hBean.setTitle("同比增长率%");
    		header.add(56,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("RATE_NOV_MOM");
    		hBean.setTitle("环比增长率%");
    		header.add(57,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("FIRST_DEC");
    		hBean.setTitle(""+qYear+""+"年12月");
    		header.add(58,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("SECOND_DEC");
    		hBean.setTitle(""+bYear+""+"年12月");
    		header.add(59,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("THIRD_DEC");
    		hBean.setTitle(""+year+""+"年12月");
    		header.add(60,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("RATE_DEC_YOY");
    		hBean.setTitle("同比增长率%");
    		header.add(61,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("RATE_DEC_MOM");
    		hBean.setTitle("环比增长率%");
    		header.add(62,hBean);
    		QuerySet querySet = dao.supDownload(supCode,supName,year);
    		ExportManager.exportFile(response.getHttpResponse(), "LJBXGH", header, querySet);
    	} catch (Exception e) {
    		atx.setException(e);
    		logger.error(e);
    	}
    }
}