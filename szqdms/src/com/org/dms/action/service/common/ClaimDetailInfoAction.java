package com.org.dms.action.service.common;

import org.apache.log4j.Logger;

import com.org.dms.dao.service.common.ClaimDetailInfoDao;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 索赔单明细ACTION
 * @author zts
 *
 */
public class ClaimDetailInfoAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "ClaimDetailInfoAction");
	private ActionContext atx = ActionContext.getContext();
	private ClaimDetailInfoDao dao = ClaimDetailInfoDao.getInstance(atx);
	
	/**
     * 查询索赔单信息
     * @throws Exception
     */
    public void searchClaimInfo() throws Exception{
    	RequestWrapper request = atx.getRequest();
 		PageManager page = new PageManager();
 		String claimId=Pub.val(request, "claimId");
 		RequestUtil.getConditionsWhere(request,page);
 		try
 		{
 			BaseResultSet bs = dao.searchClaimInfo(page,claimId);
 			atx.setOutData("bs", bs);
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }
    
    /**
     * 外出信息
     * @throws Exception
     */
    public void searchClaimOutInfo()throws Exception{
    	RequestWrapper request = atx.getRequest();
 		PageManager page = new PageManager();
 		String claimId=Pub.val(request, "claimId");
 		RequestUtil.getConditionsWhere(request,page);
 		try
 		{
 			BaseResultSet bs = dao.searchClaimOutInfo(page,claimId);
 			atx.setOutData("bs", bs);
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }
    
    /**
     * 故障信息
     * @throws Exception
     */
    public void searchClaimPattern()throws Exception{
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.searchClaimPattern(page,user,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
    }
    
    /**
     * 附件信息查询
     * @throws Exception
     */
    public void fileSearch()throws Exception{
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		RequestUtil.getConditionsWhere(request,page);
		try
		{
			String workId=request.getParamValue("workId");
			BaseResultSet bs = dao.fileSearch(page,user,workId);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
    }
    
    /**
     * 审核轨迹
     * @throws Exception
     */
    public void hisCheckSearch()throws Exception{
    	RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		RequestUtil.getConditionsWhere(request,page);
		try
		{
			String claimId=request.getParamValue("claimId");
			BaseResultSet bs = dao.hisCheckSearch(page,claimId);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
    }
    
    /**
     * 故障配件信息
     * @throws Exception
     */
    public void searchFaultPart()throws Exception{
    	RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		RequestUtil.getConditionsWhere(request,page);
		String claimDtlId=Pub.val(request,"claimDtlId");
		try
		{
			
			BaseResultSet bs = dao.searchFaultPart(page,claimDtlId);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
    }
}
