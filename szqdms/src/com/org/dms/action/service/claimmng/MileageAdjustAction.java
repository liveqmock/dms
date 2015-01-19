package com.org.dms.action.service.claimmng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.claimmng.MileageAdjustDao;
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
 * 里程调整申请ACTION
 * @author zts
 *
 */
public class MileageAdjustAction {
	private Logger logger = com.org.framework.log.log.getLogger("MileageAdjustAction");
	private ActionContext atx = ActionContext.getContext();
	private MileageAdjustDao dao=MileageAdjustDao.getInstance(atx);
	 
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
	 * 索赔单修改
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
			String claimId=hm.get("CLAIM_ID");//索赔单ID
			String mileage=hm.get("ENSURE_MILEAGE");//正确里程
			String mileageApplyReason=hm.get("MILEAGE_APPLY_REASON");//申请原因
			vo.setClaimId(claimId);
			vo.setMileageApplyReason(mileageApplyReason);
			vo.setEnsureMileage(mileage);
			vo.setMileageApplyStatus(DicConstant.LCSQZT_01);//已申请
			vo.setUpdateUser(user.getAccount());
			vo.setUpdateTime(Pub.getCurrentDate());
            dao.updateClaim(vo);
            atx.setOutMsg("","申请成功！");
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
