package com.org.dms.action.part.storageMng.logisticInfo;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.storageMng.logisticInfo.DriverDao;
import com.org.dms.vo.part.PtBaDriverVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 司机信息管理action
 */
public class DriverAction
{
	//日志类
    private Logger logger = com.org.framework.log.log.getLogger(
        "DriverAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private DriverDao dao = DriverDao.getInstance(atx);

    /**
     * 新增司机信息
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
        	PtBaDriverVO vo = new PtBaDriverVO();
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			vo.setValue(hm);
			
			//判断司机身份证号是否已存在
/*			QuerySet qs = dao.checkDriverNO(vo.getDriverNo());
			if(qs.getRowCount() > 0)
			{
				String n = qs.getString(1, 1);
				if(Integer.parseInt(n) > 0)
				{
					throw new Exception("此司机信息已有，保存失败！");
				}
			}*/
			
			// 根据身份证号及承运商ID判断是否存在
			if(dao.checkDriverNOAndCarrierId(vo.getDriverNo(), vo.getCarrierId())){
				throw new Exception("司机信息已存在");
			}
			
			vo.setCreateUser(user.getAccount());
			vo.setCreateTime(Pub.getCurrentDate());
			
			dao.insertDriver(vo);
			//返回插入结果和成功信息
			atx.setOutMsg(vo.getRowXml(),"司机信息新增成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 更新司机信息
     * @throws Exception
     * @Author suoxiuli 2014-08-21
     */
    public void update() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PtBaDriverVO tempVO = new PtBaDriverVO();
        try
        {
            HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			tempVO.setValue(hm);
			
			//设置通用字段
			tempVO.setUpdateUser(user.getAccount());
			tempVO.setUpdateTime(Pub.getCurrentDate());
			
            dao.updateDriver(tempVO);
            atx.setOutMsg(tempVO.getRowXml(),"司机信息修改成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 删除司机信息
     * @throws Exception
     * @Author suoxiuli 2014-08-21
     */
    /*public void delete() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String driverId = Pub.val(request, "driverId");
        try
        {
            //更新司机信息为无效状态
            boolean b = dao.updateDriverStatus(driverId, user.getAccount(), Constant.YXBS_02);
            atx.setOutMsg("","司机信息删除成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }*/

    /**
     * 司机信息查询
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