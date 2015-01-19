package com.org.dms.action.part.storageMng.logisticInfo;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.storageMng.logisticInfo.VehicleDao;
import com.org.dms.vo.part.PtBaVehicleVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 车辆信息管理action
 */
public class VehicleAction
{
	//日志类
    private Logger logger = com.org.framework.log.log.getLogger(
        "VehicleAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private VehicleDao dao = VehicleDao.getInstance(atx);

    /**
     * 新增车辆信息
     * @throws Exception
     * @Author suoxiuli 2014-08-21
     */
    public void insert() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	PtBaVehicleVO vo = new PtBaVehicleVO();
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			vo.setValue(hm);
			
/*			//判断车辆发动机号是否已存在
			QuerySet qs = dao.checkEngineNo(vo.getEngineNo());
			if(qs.getRowCount() > 0)
			{
				String n = qs.getString(1, 1);
				if(Integer.parseInt(n) > 0)
				{
					throw new Exception("此车辆信息已有，保存失败！");
				}
			}*/
			
			// 根据车牌号+承运商ID校验车辆信息是否已维护
			if(dao.checkLicenseNoAndCarrierIdSame(hm.get("LICENSE_PLATE"), hm.get("CARRIER_ID"))){
				throw new Exception("系统已存在此车辆信息，不能重复维护！");
			}
			
			vo.setCreateUser(user.getAccount());
			vo.setCreateTime(Pub.getCurrentDate());
			
			dao.insertVehicle(vo);
			//返回插入结果和成功信息
			atx.setOutMsg(vo.getRowXml(),"车辆信息新增成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 更新车辆信息
     * @throws Exception
     * @Author suoxiuli 2014-08-21
     */
    public void update() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PtBaVehicleVO tempVO = new PtBaVehicleVO();
        try
        {
            HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			tempVO.setValue(hm);
			
			//设置通用字段
			tempVO.setUpdateUser(user.getAccount());
			tempVO.setUpdateTime(Pub.getCurrentDate());
			
            dao.updateVehicle(tempVO);
            atx.setOutMsg(tempVO.getRowXml(),"车辆信息修改成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 删除车辆信息
     * @throws Exception
     * @Author suoxiuli 2014-08-21
     */
    /*public void delete() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String vehicleId = Pub.val(request, "vehicleId");
        try
        {
            //更新承运商信息为无效状态
            boolean b = dao.updateVehicleStatus(vehicleId, user.getAccount(), Constant.YXBS_02);
            atx.setOutMsg("","车辆信息删除成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }*/

    /**
     * 车辆信息查询
     * @throws Exception
     * Author suoxiuli 2014-08-21
     */
    public void search() throws Exception
    {
    	//定义request对象
	    RequestWrapper request = atx.getRequest();
        //定义查询分页对象
		PageManager page = new PageManager();
		//将request流中的信息转化为where条件
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.search(page,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    
}