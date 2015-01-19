package com.org.dms.action.service.financeMng;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.financeMng.ClaimSettleDao;
import com.org.dms.vo.service.SeBuClaimSettleVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 索赔结算ACTION
 * @author zts
 *
 */
public class ClaimSettleAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "ClaimSettleAction");
	private ActionContext atx = ActionContext.getContext();
	private ClaimSettleDao dao = ClaimSettleDao.getInstance(atx);

	/**
     * 索赔结算查询
     * @throws Exception
     */
    public void claimSettleSearch() throws Exception{
    	RequestWrapper request = atx.getRequest();
 		PageManager page = new PageManager();
 		User user = (User) atx.getSession().get(Globals.USER_KEY);
 		String conditions = RequestUtil.getConditionsWhere(request,page);
 		try
 		{
 			BaseResultSet bs = dao.claimSettleSearch(page,conditions,user);
 			atx.setOutData("bs", bs);
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }
    
    /**
     * 索赔结算
     * @throws Exception
     */
    public void settleUpdate()throws Exception{
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String settleId=Pub.val(request, "settleId");
        try
        {
        	SeBuClaimSettleVO vo=new SeBuClaimSettleVO();
        	vo.setSettleId(settleId);
        	vo.setSettleStatus(DicConstant.JSZT_06);//已结算
			vo.setUpdateUser(user.getAccount());
			vo.setUpdateTime(Pub.getCurrentDate());
			dao.settleUpdate(vo);
			atx.setOutMsg("","结算成功！");
	     }
	     catch (Exception e)
	     {
	    	 atx.setException(e);
	         logger.error(e);
	     }
    }
}
