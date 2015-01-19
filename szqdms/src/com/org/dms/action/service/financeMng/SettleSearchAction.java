package com.org.dms.action.service.financeMng;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.service.financeMng.SettleSearchDao;
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
 * 结算单查询ACTION
 * @author 	zts
 *
 */
public class SettleSearchAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "SettleSearchAction");
	private ActionContext atx = ActionContext.getContext();
	private SettleSearchDao dao = SettleSearchDao.getInstance(atx);

	/**
	 * 服务商 结算单查询
	 * @throws Exception
	 */
	public void settleDealerSearch()throws Exception{
		RequestWrapper request = atx.getRequest();
 		PageManager page = new PageManager();
 		User user = (User) atx.getSession().get(Globals.USER_KEY);
 		String conditions = RequestUtil.getConditionsWhere(request,page);
 		try
 		{
 			BaseResultSet bs = dao.settleDealerSearch(page,conditions,user);
 			atx.setOutData("bs", bs);
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
	}
	/**
	 * 车厂端 明细查询
	 * @throws Exception
	 */
	public void searchSettleOemDetail()throws Exception{
		RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.searchSettleOemDetail(page,conditions,user);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
	/**
	 * 下端 明细查询
	 * @throws Exception
	 */
	public void searchSettleDetail()throws Exception{
		RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.searchSettleDetail(page,conditions,user);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}

	/**
	 * 厂端 结算单查询
	 * @throws Exception
	 */
	public void settleOemSearch()throws Exception{
		RequestWrapper request = atx.getRequest();
 		PageManager page = new PageManager();
 		User user = (User) atx.getSession().get(Globals.USER_KEY);
 		String conditions = RequestUtil.getConditionsWhere(request,page);
 		try
 		{
 			BaseResultSet bs = dao.settleOemSearch(page,conditions,user);
 			atx.setOutData("bs", bs);
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
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
			hBean.setName("SETTLE_NO");
			hBean.setTitle("结算单号");
			header.add(2,hBean);
			hBean = new HeaderBean();
			hBean.setName("SETTLE_DATE");
			hBean.setTitle("结算单产生日期");
			header.add(3,hBean);
			hBean = new HeaderBean();
			hBean.setName("SETTLE_TYPE");
			hBean.setTitle("结算类型");
			header.add(4,hBean);
			hBean = new HeaderBean();
			hBean.setName("CLAIM_COUNT");
			hBean.setTitle("索赔单数");
			header.add(5,hBean);
			hBean = new HeaderBean();
			hBean.setName("COSTS");
			hBean.setTitle("服务费/材料费");
			header.add(6,hBean);
			hBean = new HeaderBean();
			hBean.setName("RE_COSTS");
			hBean.setTitle("旧件运费/配件返利");
			header.add(7,hBean);
			hBean = new HeaderBean();
			hBean.setName("POLICY_SUP");
			hBean.setTitle("政策支持");
			header.add(8,hBean);
			hBean = new HeaderBean();
			hBean.setName("CASH_GIFT");
			hBean.setTitle("礼金卡");
			header.add(9,hBean);
			hBean = new HeaderBean();
			hBean.setName("CAR_AWARD");
			hBean.setTitle("售车奖励");
			header.add(10,hBean);
			hBean = new HeaderBean();
			hBean.setName("AP_COSTS");
			hBean.setTitle("考核费用");
			header.add(11,hBean);
			hBean = new HeaderBean();
			hBean.setName("OTHERS");
			hBean.setTitle("其他费用");
			header.add(12,hBean);
			hBean = new HeaderBean();
			hBean.setName("MANUALLY_COST");
			hBean.setTitle("手工帐总费用");
			header.add(13,hBean);
			hBean = new HeaderBean();
			hBean.setName("SUMMARY");
			hBean.setTitle("索赔总金额(元)");
			header.add(14,hBean);
			hBean = new HeaderBean();
			hBean.setName("SETTLE_STATUS");
			hBean.setTitle("状态");
			header.add(15,hBean);
			hBean = new HeaderBean();
			hBean.setName("INVOICE_NO");
			hBean.setTitle("发票号");
			header.add(16,hBean);
			hBean = new HeaderBean();
			hBean.setName("INVOICE_DATE");
			hBean.setTitle("开票日期");
			header.add(17,hBean);
			hBean = new HeaderBean();
			hBean.setName("INVOICE_AMOUNT");
			hBean.setTitle("发票金额");
			header.add(18,hBean);
			QuerySet qs = dao.oemDownload(conditions);
			ExportManager.exportFile(response.getHttpResponse(), "索赔查询", header, qs);
		}
		catch (Exception e)
		{
			atx.setException(e);
			logger.error(e);
		}
	}
	public void dtlDownload()throws Exception{
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
			hBean.setName("CLAIM_NO");
			hBean.setTitle("索赔单号");
			header.add(2,hBean);
			hBean = new HeaderBean();
			hBean.setName("SETTLE_NO");
			hBean.setTitle("结算单号");
			header.add(3,hBean);
			hBean = new HeaderBean();
			hBean.setName("SETTLE_DATE");
			hBean.setTitle("结算单产生日期");
			header.add(4,hBean);
			hBean = new HeaderBean();
			hBean.setName("SETTLE_TYPE");
			hBean.setTitle("结算类型");
			header.add(5,hBean);
			hBean = new HeaderBean();
			hBean.setName("WORK_COSTS");
			hBean.setTitle("工时金额");
			header.add(6,hBean);
			hBean = new HeaderBean();
			hBean.setName("OUT_COSTS");
			hBean.setTitle("外出金额");
			header.add(7,hBean);
			hBean = new HeaderBean();
			hBean.setName("MAINTENANCE_COSTS");
			hBean.setTitle("首保费");
			header.add(8,hBean);
			hBean = new HeaderBean();
			hBean.setName("MATERIAL_COSTS");
			hBean.setTitle("材料费");
			header.add(9,hBean);
			hBean = new HeaderBean();
			hBean.setName("AQJC_COSTS");
			hBean.setTitle("安全检查费");
			header.add(10,hBean);
			hBean = new HeaderBean();
			hBean.setName("SQPX_COSTS");
			hBean.setTitle("售前培训费");
			header.add(11,hBean);
			hBean = new HeaderBean();
			hBean.setName("SPZF");
			hBean.setTitle("索赔总费");
			header.add(12,hBean);
			hBean = new HeaderBean();
			hBean.setName("SETTLE_STATUS");
			hBean.setTitle("结算单状态");
			header.add(13,hBean);
			hBean = new HeaderBean();
			hBean.setName("INVOICE_NO");
			hBean.setTitle("发票号");
			header.add(14,hBean);
			hBean = new HeaderBean();
			hBean.setName("INVOICE_DATE");
			hBean.setTitle("开票日期");
			header.add(15,hBean);
			
			QuerySet qs = dao.dtlDownload(conditions);
			ExportManager.exportFile(response.getHttpResponse(), "结算明细导出", header, qs);
		}
		catch (Exception e)
		{
			atx.setException(e);
			logger.error(e);
		}
	}
	public void dealerDtlDownload()throws Exception{
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
			hBean.setName("CLAIM_NO");
			hBean.setTitle("索赔单号");
			header.add(2,hBean);
			hBean = new HeaderBean();
			hBean.setName("SETTLE_NO");
			hBean.setTitle("结算单号");
			header.add(3,hBean);
			hBean = new HeaderBean();
			hBean.setName("SETTLE_DATE");
			hBean.setTitle("结算单产生日期");
			header.add(4,hBean);
			hBean = new HeaderBean();
			hBean.setName("SETTLE_TYPE");
			hBean.setTitle("结算类型");
			header.add(5,hBean);
			hBean = new HeaderBean();
			hBean.setName("WORK_COSTS");
			hBean.setTitle("工时金额");
			header.add(6,hBean);
			hBean = new HeaderBean();
			hBean.setName("OUT_COSTS");
			hBean.setTitle("外出金额");
			header.add(7,hBean);
			hBean = new HeaderBean();
			hBean.setName("MAINTENANCE_COSTS");
			hBean.setTitle("首保费");
			header.add(8,hBean);
			hBean = new HeaderBean();
			hBean.setName("MATERIAL_COSTS");
			hBean.setTitle("材料费");
			header.add(9,hBean);
			hBean = new HeaderBean();
			hBean.setName("AQJC_COSTS");
			hBean.setTitle("安全检查费");
			header.add(10,hBean);
			hBean = new HeaderBean();
			hBean.setName("SQPX_COSTS");
			hBean.setTitle("售前培训费");
			header.add(11,hBean);
			hBean = new HeaderBean();
			hBean.setName("SPZF");
			hBean.setTitle("索赔总费");
			header.add(12,hBean);
			hBean = new HeaderBean();
			hBean.setName("SETTLE_STATUS");
			hBean.setTitle("结算单状态");
			header.add(13,hBean);
			hBean = new HeaderBean();
			hBean.setName("INVOICE_NO");
			hBean.setTitle("发票号");
			header.add(14,hBean);
			hBean = new HeaderBean();
			hBean.setName("INVOICE_DATE");
			hBean.setTitle("开票日期");
			header.add(15,hBean);
			
			QuerySet qs = dao.dealerDtlDownload(conditions,user);
			ExportManager.exportFile(response.getHttpResponse(), "结算明细导出", header, qs);
		}
		catch (Exception e)
		{
			atx.setException(e);
			logger.error(e);
		}
	}
}
