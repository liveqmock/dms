package com.org.dms.action.service.claimmng;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.service.claimmng.ClaimSupplierSearchDao;
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
 * 供应商索赔单查询
 * @author zts
 *
 */
public class ClaimSupplierSearchAction {
	private Logger logger = com.org.framework.log.log.getLogger("ClaimSupplierSearchAction");
	private ActionContext atx = ActionContext.getContext();
	private ClaimSupplierSearchDao dao=ClaimSupplierSearchDao.getInstance(atx);
	
	/**
	 * 索赔单查询
	 * @throws Exception
	 */
	public void claimSearch()throws Exception{
		RequestWrapper request = atx.getRequest();
 		PageManager page = new PageManager();
 		User user = (User) atx.getSession().get(Globals.USER_KEY);
 		String conditions = RequestUtil.getConditionsWhere(request,page);
 		try
 		{
 			BaseResultSet bs = dao.claimSearch(page,conditions,user);
 			atx.setOutData("bs", bs);
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
	}
	/**
	 * 供应商端索赔单下载
	 * @throws Exception
	 */
	public void claimDownload()throws Exception{
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
			hBean.setName("CLAIM_NO");
			hBean.setTitle("索赔单号");
			header.add(0,hBean);
			hBean = new HeaderBean();
			hBean.setName("CLAIM_TYPE");
			hBean.setTitle("索赔单类型");
			header.add(1,hBean);
			hBean = new HeaderBean();
			hBean.setName("CLAIM_STATUS");
			hBean.setTitle("索赔单状态");
			header.add(2,hBean);
			hBean = new HeaderBean();
			hBean.setName("APPLY_DATE");
			hBean.setTitle("提报时间");
			header.add(3,hBean);
			hBean = new HeaderBean();
			hBean.setName("CHECKPASS_DATE");
			hBean.setTitle("初审时间");
			header.add(4,hBean);
			hBean = new HeaderBean();
			hBean.setName("OLDPART_FINAL_DATE");
			hBean.setTitle("终审时间");
			header.add(5,hBean); 
			QuerySet qs = dao.oemDownload(conditions,user);
			ExportManager.exportFile(response.getHttpResponse(), "供应商索赔单查询", header, qs);
		}
		catch (Exception e)
		{
			atx.setException(e);
			logger.error(e);
		}
	}
}
