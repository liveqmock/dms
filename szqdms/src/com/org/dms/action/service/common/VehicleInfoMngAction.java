package com.org.dms.action.service.common;

import org.apache.log4j.Logger;

import com.org.dms.dao.service.common.VehicleInfoMngDao;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 查询车辆信息公共ACTION
 * @author zjy
 *
 */
public class VehicleInfoMngAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "VehicleInfoMngAction");
	private ActionContext atx = ActionContext.getContext();
	private VehicleInfoMngDao dao = VehicleInfoMngDao.getInstance(atx);
	/**
     * 根据车辆ID查询车辆信息
     * @throws Exception
     */
    public void searchVehicleInfo() throws Exception{
    	RequestWrapper request = atx.getRequest();
 		PageManager page = new PageManager();
 		User user = (User) atx.getSession().get(Globals.USER_KEY);
 		String vehicleId=Pub.val(request, "vehicleId");
 		String conditions = RequestUtil.getConditionsWhere(request,page);
 		try
 		{
 			BaseResultSet bs = dao.searchVehicleInfo(page, user, conditions,vehicleId);
 			atx.setOutData("bs", bs);
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }
    /*
    * 根据车辆ID查询索赔信息
    * @throws Exception
    */
   public void searchClaimInfo() throws Exception{
   	RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		String vehicleId=Pub.val(request, "vehicleId");
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.searchClaimInfo(page, user, conditions,vehicleId);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
   }
}
