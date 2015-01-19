package com.org.dms.action.service.claimmng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.claimmng.ClaimReApplyDao;
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
 * 重新提单申请ACTION
 * @author zts
 *
 */
public class ClaimReApplyAction {
	 private Logger logger = com.org.framework.log.log.getLogger("ClaimReApplyAction");
	 private ActionContext atx = ActionContext.getContext();
	 private ClaimReApplyDao dao=ClaimReApplyDao.getInstance(atx);
	 
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
     * 审核查询
     * @throws Exception
     */
    public void checkSearch()throws Exception{
    	RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.checkSearch(page,conditions,user);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
    }
    
    /**
     * 索赔单重新申请 
     * @throws Exception
     */
    public void claimUpdate()throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	SeBuClaimVO vo=new SeBuClaimVO();
        	HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			String claimId=hm.get("C.CLAIM_ID");
			vo.setValue(hm);
			vo.setClaimId(claimId);
			vo.setUpdateUser(user.getAccount());
			vo.setUpdateTime(Pub.getCurrentDate());
			vo.setReapplyStatus(DicConstant.CXSQZT_01);
            dao.updateClaim(vo);
            atx.setOutMsg("","申请成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
}
