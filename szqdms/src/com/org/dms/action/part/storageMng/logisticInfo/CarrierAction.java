package com.org.dms.action.part.storageMng.logisticInfo;

import java.util.*;

import com.org.dms.dao.part.storageMng.logisticInfo.CarrierDao;
import com.org.dms.vo.part.PtBaCarrierVO;
import com.org.frameImpl.Constant;

import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import org.apache.log4j.Logger;

import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 承运商信息管理action
 */
public class CarrierAction
{
	//日志类
    private Logger logger = com.org.framework.log.log.getLogger(
        "CarrierAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private CarrierDao dao = CarrierDao.getInstance(atx);

    /**
     * 新增承运商信息
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
        	PtBaCarrierVO vo = new PtBaCarrierVO();
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			vo.setValue(hm);
			
			//判断承运商代码是否已存在
			QuerySet qs = dao.checkCarrier(vo.getCarrierCode());
			if(qs.getRowCount() > 0)
			{
				String n = qs.getString(1, 1);
				if(Integer.parseInt(n) > 0)
				{
					throw new Exception("此承运商信息已有，保存失败！");
				}
			}
			
			vo.setCreateUser(user.getAccount());
			vo.setCreateTime(Pub.getCurrentDate());
			
			dao.insertCarrier(vo);
			//返回插入结果和成功信息
			atx.setOutMsg(vo.getRowXml(),"承运商信息新增成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 更新承运商信息
     * @throws Exception
     * @Author suoxiuli 2014-08-21
     */
    public void update() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PtBaCarrierVO tempVO = new PtBaCarrierVO();
        try
        {
            HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			tempVO.setValue(hm);
			
			//设置通用字段
			tempVO.setUpdateUser(user.getAccount());
			tempVO.setUpdateTime(Pub.getCurrentDate());
			
            dao.updateCarrier(tempVO);
            atx.setOutMsg(tempVO.getRowXml(),"承运商信息修改成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 承运商信息查询
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