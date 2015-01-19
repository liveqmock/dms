package com.org.dms.action.part.basicInfoMng;

import java.util.*;

import com.org.dms.dao.part.basicInfoMng.DirectTypeDao;
import com.org.dms.vo.part.PtBaDirectTypeVO;

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
 * 直发类型action
 */
public class DirectTypeAction
{
	//日志类
    private Logger logger = com.org.framework.log.log.getLogger(
        "DirectTypeAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private DirectTypeDao dao = DirectTypeDao.getInstance(atx);

    /**
     * 新增直发类型
     * @throws Exception
     * @Author suoxiuli 2014-07-11
     */
    public void insert() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取封装后的response对象
    	//ResponseWrapper response = atx.getResponse();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	PtBaDirectTypeVO vo = new PtBaDirectTypeVO();
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			vo.setValue(hm);
			
			//判断用户账号是否已存在
			QuerySet qs = dao.checkTypeCode(vo.getTypeCode());
			if(qs.getRowCount() > 0)
			{
				String n = qs.getString(1, 1);
				if(Integer.parseInt(n) > 0)
				{
					throw new Exception("直发类型已存在，保存失败！");
				}
			}
			
			//设置通用字段
			vo.setCreateUser(user.getAccount());
			vo.setCreateTime(Pub.getCurrentDate());
			//通过dao，执行插入
			dao.insertDirectType(vo);
			//返回插入结果和成功信息
			atx.setOutMsg(vo.getRowXml(),"直发类型新增成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 更新直发类型
     * @throws Exception
     * @Author suoxiuli 2014-07-11
     */
    public void update() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PtBaDirectTypeVO tempVO = new PtBaDirectTypeVO();
        try
        {
            HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			tempVO.setValue(hm);
			String oldStatus = hm.get("OLD_STATUS");
			
			//判断用户账号是否已存在
			QuerySet qs = dao.checkTypeCode(tempVO.getTypeCode());
			if(qs.getRowCount() > 0)
			{
				String n = qs.getString(1, 1);
				if(Integer.parseInt(n) > 0 && oldStatus.equals(tempVO.getStatus()))
				{
					throw new Exception("直发类型已存在，编辑失败！");
				}
			}
			
			//设置通用字段
			tempVO.setUpdateUser(user.getAccount());
			tempVO.setUpdateTime(Pub.getCurrentDate());
            dao.updateDirectType(tempVO);
            atx.setOutMsg(tempVO.getRowXml(),"直发类型修改成功！");
           
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 删除直发类型
     * @throws Exception
     * @Author suoxiuli 2014-07-12
     */
    /*public void delete() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String typeId = Pub.val(request, "typeId");
        String typeName = Pub.val(request, "typeName");
        try
        {
            //更新直发类型为无效状态
            boolean b = dao.updateDirectTypeStatus(typeId,user.getAccount(),Constant.YXBS_02);
            atx.setOutMsg("","直发类型删除成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }*/

    /**
     * 直发类型查询
     * @throws Exception
     * Author suoxiuli 2014-07-11
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