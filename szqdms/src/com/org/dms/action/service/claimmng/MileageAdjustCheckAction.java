package com.org.dms.action.service.claimmng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.claimmng.MileageAdjustCheckDao;
import com.org.dms.vo.service.SeBuClaimCheckVO;
import com.org.dms.vo.service.SeBuClaimVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 里程调整审核ACTION
 * @author zts
 *
 */
public class MileageAdjustCheckAction {
	private Logger logger = com.org.framework.log.log.getLogger("MileageAdjustCheckAction");
	private ActionContext atx = ActionContext.getContext();
	private MileageAdjustCheckDao dao=MileageAdjustCheckDao.getInstance(atx);
	
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
	 * 里程调整审核通过
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
			QuerySet qs=dao.getClaim(claimId);
			String ensureMileage="";
			String  vehicleId="";
			if(qs.getRowCount()>0){
				vehicleId=qs.getString(1, "VEHICLE_ID");//车辆ID
				ensureMileage=qs.getString(1, "ENSURE_MILEAGE");//确认里程
			}
			String mileageCheckReason=hm.get("MILEAGE_CHECK_OPINION");//审核意见
			vo.setClaimId(claimId);
			vo.setMileageCheckOpinion(mileageCheckReason);
			vo.setMileageApplyStatus(DicConstant.LCSQZT_03);//审核通过
			vo.setMileage(ensureMileage);//行驶里程
			vo.setUpdateUser(user.getAccount());
			vo.setUpdateTime(Pub.getCurrentDate());
            dao.updateClaim(vo);
            //审核轨迹
            SeBuClaimCheckVO checkVO=new SeBuClaimCheckVO();
            checkVO.setClaimId(claimId);
            checkVO.setCheckUser(user.getAccount());
            checkVO.setCheckDate(Pub.getCurrentDate());
            checkVO.setCheckResult(DicConstant.SPDZT_14);//里程调整通过
            checkVO.setCheckRemarks(mileageCheckReason);//意见
            dao.insertCheck(checkVO);
            //更新车辆表 里程
            if(vehicleId != ""){
            	/*vehicleVO.setVehicleId(vehicleId);
            	vehicleVO.setMileage(ensureMileage);
            	vehicleVO.setUpdateUser(user.getAccount());
            	vehicleVO.setUpdateTime(Pub.getCurrentDate());*/
            	dao.updateVehicle(vehicleId,ensureMileage,user);
            }
          
            atx.setOutMsg("","审核通过！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
	}
	/**
	 * 里程调整审核驳回
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
			String mileageCheckReason=hm.get("MILEAGE_CHECK_OPINION");//审核意见
			vo.setClaimId(claimId);
			vo.setMileageCheckOpinion(mileageCheckReason);
			vo.setMileageApplyStatus(DicConstant.LCSQZT_02);//审核驳回
			vo.setUpdateUser(user.getAccount());
			vo.setUpdateTime(Pub.getCurrentDate());
            dao.updateClaim(vo);
            //审核轨迹
            SeBuClaimCheckVO checkVO=new SeBuClaimCheckVO();
            checkVO.setClaimId(claimId);
            checkVO.setCheckUser(user.getAccount());
            checkVO.setCheckDate(Pub.getCurrentDate());
            checkVO.setCheckResult(DicConstant.SPDZT_13);//里程调整通过
            checkVO.setCheckRemarks(mileageCheckReason);//意见
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
