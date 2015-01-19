package com.org.dms.action.part.purchaseMng.purchaseContract;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.purchaseMng.purchaseContract.ContractInfoSearchDao;
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

public class ContractInfoSearchManageAciton {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "ContractInfoSearchManageAciton");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private ContractInfoSearchDao dao = ContractInfoSearchDao.getInstance(atx);
	    /**
	     * 
	     * @date()2014年7月1日 下午4:01:34
	     * @user()WangChong
	     * TO_DO:合同查询
	     * @throws Exception
	     */
	    public void contractSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			RequestUtil.getConditionsWhere(request,page);
			try
			{
				String orgCode = user.getOrgCode();
				Map<String, String> hm = RequestUtil.getValues(request);
				BaseResultSet bs = dao.contractSearch(page,user, hm,orgCode);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    /**
	     * 
	     * @date()
	     * @author Administrator
	     * to_do: 合同采购配件查询
	     * @throws Exception
	     */
	    public void contractPartSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			RequestUtil.getConditionsWhere(request,page);
			try
			{
				String CONTRACT_ID = Pub.val(request, "CONTRACT_ID");
				BaseResultSet bs = dao.contractPartSearch(page,user,CONTRACT_ID);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    /**
	     * 
	     * @date()
	     * @author Administrator
	     * to_do: 合同采购配件查询
	     * @throws Exception
	     */
	    public void contractAppendixSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			RequestUtil.getConditionsWhere(request,page);
			try
			{
				String CONTRACT_ID = Pub.val(request, "CONTRACT_ID");
				BaseResultSet bs = dao.contractAppendixSearch(page,user,CONTRACT_ID);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    /**
	     * 
	     * @date()
	     * @author Administrator
	     * to_do: 合同轨迹查询
	     * @throws Exception
	     */
	    public void contractTrackSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			RequestUtil.getConditionsWhere(request,page);
			try
			{
				String CONTRACT_ID = Pub.val(request, "CONTRACT_ID");
				BaseResultSet bs = dao.contractTrackSearch(page,user,CONTRACT_ID);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    public void contractDtlSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			RequestUtil.getConditionsWhere(request,page);
			try
			{
				String CONTRACT_ID = Pub.val(request, "CONTRACT_ID");
				BaseResultSet bs = dao.contractDtlSearch(page,user,CONTRACT_ID);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    //download
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
				hBean.setName("PART_CODE");
				hBean.setTitle("供货品种代码");
				header.add(0,hBean);
				hBean = new HeaderBean();
				hBean.setName("PART_NAME");
				hBean.setTitle("供货品种名称");
				hBean.setWidth(50);
				header.add(1,hBean); 
				hBean = new HeaderBean();
				hBean.setName("UNIT_PRICE");
				hBean.setTitle("单价(不含税)");
				header.add(2,hBean);
				hBean = new HeaderBean();
				hBean.setName("DELIVERY_CYCLE");
				hBean.setTitle("供货周期");
				header.add(3,hBean);
				hBean = new HeaderBean();
				hBean.setName("MIN_PACK_UNIT");
				hBean.setTitle("最小包装单位");
				header.add(4,hBean);
				hBean = new HeaderBean();
				hBean.setName("MIN_PACK_COUNT");
				hBean.setTitle("最小包装数量");
				header.add(5,hBean);
				hBean = new HeaderBean();
				hBean.setName("REMARKS");
				hBean.setTitle("备注");
				header.add(6,hBean);
				String CONTRACT_ID = Pub.val(request, "CONTRACT_ID");
				QuerySet qs = dao.download(CONTRACT_ID,conditions);
				ExportManager.exportFile(response.getHttpResponse(), "采购合同明细", header, qs);
			}
			catch (Exception e)
			{
				atx.setException(e);
				logger.error(e);
			}
		}

}
