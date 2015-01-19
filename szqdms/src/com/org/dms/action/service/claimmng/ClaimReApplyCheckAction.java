package com.org.dms.action.service.claimmng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.claimmng.ClaimReApplyCheckDao;
import com.org.dms.vo.service.SeBuClaimCheckVO;
import com.org.dms.vo.service.SeBuClaimVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
/**
 * 重新提单审核ACTION
 * @author zts
 *
 */
public class ClaimReApplyCheckAction {
	 private Logger logger = com.org.framework.log.log.getLogger("ClaimReApplyCheckAction");
	 private ActionContext atx = ActionContext.getContext();
	 private ClaimReApplyCheckDao dao=ClaimReApplyCheckDao.getInstance(atx);
	 
	 /**
	  * 索赔单查询
      * @throws Exception
     */
     public void claimSearch() throws Exception{
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
     * 重新提单审核通过
     * @throws Exception
     */
    public void claimPassUpdate()throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	SeBuClaimVO vo=new SeBuClaimVO();
        	HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			String claimId=hm.get("CLAIM_ID");//索赔单ID
			String checkOpinion=hm.get("CHECK_OPINION");//审核意见
			vo.setClaimId(claimId);
			vo.setCheckOpinion(checkOpinion);
			vo.setClaimStatus(DicConstant.SPDZT_06);//人工审核驳回
			vo.setReapplyStatus(DicConstant.CXSQZT_03);//重新审核通过
			vo.setUpdateUser(user.getAccount());
			vo.setUpdateTime(Pub.getCurrentDate());
            dao.updateClaim(vo);
            SeBuClaimCheckVO checkVO=new SeBuClaimCheckVO();
            checkVO.setClaimId(claimId);
            checkVO.setCheckUser(user.getAccount());
            checkVO.setCheckDate(Pub.getCurrentDate());
            checkVO.setCheckResult(DicConstant.SPDZT_12);//重新提单通过
            checkVO.setCheckRemarks(checkOpinion);//意见
            dao.insertCheck(checkVO);
            atx.setOutMsg("","审核通过！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    
    /**
     * 重新提单审核驳回
     * @throws Exception
     */
    public void claimRejectUpdate()throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	SeBuClaimVO vo=new SeBuClaimVO();
        	HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			String claimId=hm.get("CLAIM_ID");//索赔单ID
			String checkOpinion=hm.get("CHECK_OPINION");//审核意见
			vo.setClaimId(claimId);
			vo.setReapplyStatus(DicConstant.CXSQZT_02);//重新审核驳回
			vo.setCheckOpinion(checkOpinion);
			vo.setUpdateUser(user.getAccount());
			vo.setUpdateTime(Pub.getCurrentDate());
			vo.setRecheckDate(Pub.getCurrentDate());//重新提单驳回日期
            dao.updateClaim(vo);
            //审核轨迹
            SeBuClaimCheckVO checkVO=new SeBuClaimCheckVO();
            checkVO.setClaimId(claimId);
            checkVO.setCheckUser(user.getAccount());
            checkVO.setCheckDate(Pub.getCurrentDate());
            checkVO.setCheckResult(DicConstant.SPDZT_11);//重新提单驳回
            checkVO.setCheckRemarks(checkOpinion);//意见
            dao.insertCheck(checkVO);
            atx.setOutMsg("","审核驳回！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    
    /**
     * 审核轨迹查询
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
}
