package com.org.dms.action.part.storageMng.logisticInfo;

import java.util.*;

import com.org.dms.dao.part.storageMng.logisticInfo.TransMilesDao;
import com.org.dms.vo.part.PtBaTransMilesVO;
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
 * 运费里程数维护action
 */
public class TransMilesAction
{
	//日志类
    private Logger logger = com.org.framework.log.log.getLogger(
        "TransMilesAction");
    private ActionContext atx = ActionContext.getContext();
    private TransMilesDao dao = TransMilesDao.getInstance(atx);

    /**
     * 新增运费里程数
     * @throws Exception
     * @Author suoxiuli 2014-08-22
     */
    public void insert() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	PtBaTransMilesVO vo = new PtBaTransMilesVO();
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			vo.setValue(hm);
			
			//判断运费里程数是否已存在
			QuerySet qs = dao.checkTransMiles(vo.getBirthlandCode(),vo.getProvinceCode(),
					vo.getCityCode(),vo.getCountryCode(),vo.getAddress());
			if(qs.getRowCount() > 0)
			{
				String n = qs.getString(1, 1);
				if(Integer.parseInt(n) > 0)
				{
					throw new Exception("此运费里程数信息已有，保存失败！");
				}
			}
			
			vo.setCreateUser(user.getAccount());
			vo.setCreateTime(Pub.getCurrentDate());
			
			dao.insertTransMiles(vo);
			//返回插入结果和成功信息
			atx.setOutMsg(vo.getRowXml(),"运费里程数新增成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 更新运费里程数
     * @throws Exception
     * @Author suoxiuli 2014-08-22
     */
    public void update() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PtBaTransMilesVO tempVO = new PtBaTransMilesVO();
        try
        {
            HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			tempVO.setValue(hm);
			
			//设置通用字段
			tempVO.setUpdateUser(user.getAccount());
			tempVO.setUpdateTime(Pub.getCurrentDate());
			
            dao.updateTransMiles(tempVO);
            atx.setOutMsg(tempVO.getRowXml(),"运费里程数修改成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 删除运费里程数
     * @throws Exception
     * @Author suoxiuli 2014-08-22
     */
    public void delete() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String milesId = Pub.val(request, "milesId");
        try
        {
            //更新运费里程数为无效状态
            boolean b = dao.updateTransMilesStatus(milesId, user.getAccount(), Constant.YXBS_02);
            atx.setOutMsg("","运费里程数删除成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 运费里程数查询
     * @throws Exception
     * Author suoxiuli 2014-08-22
     */
    public void search() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
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