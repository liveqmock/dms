package com.org.dms.action.service.claimmng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.claimmng.ClaimLeaderCheckDao;
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
 * 索赔单主管审批
 * @author zts
 *
 */
public class ClaimLeaderCheckAction {
	 private Logger logger = com.org.framework.log.log.getLogger("ClaimLeaderCheckAction");
	 private ActionContext atx = ActionContext.getContext();
	 private ClaimLeaderCheckDao dao=ClaimLeaderCheckDao.getInstance(atx);
	 
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
	  * 索赔单领导审批
	  * @throws Exception
	  */
	 public void claimCheckUpdate()throws Exception{
		//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	SeBuClaimVO vo=new SeBuClaimVO();
        	HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			String claimId=hm.get("CLAIM_ID");
			String checkRemarks=hm.get("CHECK_REMARKS");
			vo.setClaimId(claimId);
			vo.setClaimStatus(DicConstant.SPDZT_03);//索赔单状态 (自动审核通过)
			vo.setUpdateUser(user.getAccount());
			vo.setUpdateTime(Pub.getCurrentDate());
            dao.claimCheckUpdae(vo);//修改索赔单
            
            //插入审核轨迹
            SeBuClaimCheckVO checkVo=new SeBuClaimCheckVO();
            checkVo.setClaimId(claimId);
            checkVo.setCheckUser(user.getAccount());
            checkVo.setCheckDate(Pub.getCurrentDate());
            checkVo.setCheckResult(DicConstant.SPDZT_10);//索赔单状态 (领导审核完成)
            checkVo.setCheckRemarks(checkRemarks);
            checkVo.setCreateTime(Pub.getCurrentDate());
            checkVo.setCreateUser(user.getAccount());
            checkVo.setOemCompanyId(user.getOemCompanyId());
            dao.insertCheck(checkVo);
            atx.setOutMsg("1","审核完成");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
	 }
}
