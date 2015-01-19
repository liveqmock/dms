package com.org.frameImpl.action.parasmng;

import java.util.HashMap;

import com.org.frameImpl.Constant;
import com.org.frameImpl.dao.parasmng.UserParaConfigureDao;
import com.org.frameImpl.vo.UserParaConfigureVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.common.cache.CacheManager;
import com.org.framework.log.LogManager;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * @Description:业务参数管理类
 * @Copyright: Copyright (c) 2014
 * @Date: Jul 24, 2014
 * @author andy.ten@tom.com
 */
public class UserParaConfigureAction
{
	//上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private UserParaConfigureDao dao = UserParaConfigureDao.getInstance(atx);
	private static org.apache.log4j.Logger logger = org.apache.log4j.LogManager.
	        getLogger("UserParaConfigureAction");
	
	/**
	 * 新增业务参数
	 * @throws Exception
	 * @author andy.ten@tom.com 
	 * @Time Jul 24, 2014 5:51:13 PM
	 */
	public void insert() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
            UserParaConfigureVO vo = new UserParaConfigureVO();
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			vo.setValue(hm);
			//判断参数代码是否已存在
			QuerySet qs = dao.checkParaKey(vo.getParakey());
			if(qs.getRowCount() > 0)
			{
				String n = qs.getString(1, 1);
				if(Integer.parseInt(n) > 0)
				{
					throw new Exception("参数代码已存在，保存失败！");
				}
			}
			vo.setCreateUser(user.getAccount());
			vo.setCreateTime(Pub.getCurrentDate());
			dao.insert(vo);
			atx.setOutMsg(vo.getRowXml(),"业务参数新增成功！");
            //日志
            LogManager.writeUserLog("","",Globals.OPERATION_TYPE_INSERT,LogManager.RESULT_SUCCESS
                                    ,"添加业务参数 ["+vo.getTypename()+" / "+vo.getParaname()+"] 成功",user);
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
            //日志
            LogManager.writeUserLog("", "", Globals.OPERATION_TYPE_INSERT, LogManager.RESULT_FAILURE,"添加业务参数失败："+e.toString(), user);
        }
    }

    /**
     * 更新业务参数
     * @throws Exception
     */
    public void update() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        UserParaConfigureVO tempVO = new UserParaConfigureVO();
        try
        {
            HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			tempVO.setValue(hm);
			tempVO.setUpdateUser(user.getAccount());
			tempVO.setUpdateTime(Pub.getCurrentDate());
			//通过dao，执行更新
            dao.update(tempVO);
            //返回更新结果和成功信息
            atx.setOutMsg(tempVO.getRowXml(),"业务参数修改成功！");
          
            //日志
            LogManager.writeUserLog("", "", Globals.OPERATION_TYPE_UPDATE, LogManager.RESULT_SUCCESS
                    , "修改业务参数["+tempVO.getTypename()+" / "+tempVO.getParaname()+"] 成功", user);
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
            //日志
            LogManager.writeUserLog("","",Globals.OPERATION_TYPE_UPDATE,LogManager.RESULT_FAILURE
                    ,"修改业务参数失败："+e.toString(),user);
        }
    }
    
    /**
     * 同步集群方法
     * @throws Exception
     * @author andy.ten@tom.com 
     * @Time Jul 10, 2014 1:35:17 PM
     */
    public void synchronize() 
        	throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	String paraKey = Pub.val(request, "paraKey");
    	String action = Pub.val(request, "type");
    	int type = 0;
    	if("1".equals(action))
    		type = 1;
    	else if("2".equals(action))
    		type = 2;
    	else if("3".equals(action))
    		type = 3;
    	//广播节点
        CacheManager.broadcastChanges(CacheManager.CACHE_PARAMS,paraKey, type);
    }
    
    /**
     * 删除用户
     * @throws Exception
     */
    public void delete() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String sn = Pub.val(request, "sn");
        try
        {
            //更新为无效状态
            dao.updateStatus(sn, Constant.YXBS_02);
            logger.info("@业务参数：sn [" + sn + "] 注销成功...");
            //返回更新结果和成功信息
            atx.setOutMsg("","业务参数删除成功！");

             LogManager.writeUserLog("","",Globals.OPERATION_TYPE_DELETE,LogManager.RESULT_SUCCESS
                                        ,"删除业务参数 ["+sn+"] 成功",user);
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
            LogManager.writeUserLog("","",Globals.OPERATION_TYPE_DELETE,LogManager.RESULT_FAILURE
                    ,"删除业务参数失败："+e.toString(),user);
        }
    }
	
    /**
     * 查询业务参数
     * @throws Exception
     * @author andy.ten@tom.com 
     * @Time Jul 24, 2014 5:50:59 PM
     */
	public void search()
			throws Exception
	{
		RequestWrapper request = atx.getRequest();
		PageManager page = null;
		String conditions = null;
		try
		{
			page = new PageManager();
			conditions = RequestUtil.getConditionsWhere(request, page);
			BaseResultSet bs = dao.search(page,conditions);
			atx.setOutData("bs", bs);
		}
		catch(Exception e)
		{
			logger.error(e);
            atx.setException(e);
		}
	}
}
