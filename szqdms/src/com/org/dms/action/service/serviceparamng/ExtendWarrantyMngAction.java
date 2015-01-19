package com.org.dms.action.service.serviceparamng;

import java.util.*;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.serviceparamng.ExtendWarrantyMngDao;
import com.org.dms.vo.service.SeBaExtendWarrantyVO;

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
 * @Title: szqdms
 * @description: 延保策略设置方法
 * @throws Exception 设定文件
 * @auther fanpeng
 * @date 2014年8月29日 
 */
public class ExtendWarrantyMngAction
{
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private ExtendWarrantyMngDao dao = ExtendWarrantyMngDao.getInstance(atx);

    /**
     * 新增延保策略
     * @throws Exception
     */
    public void insert() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	SeBaExtendWarrantyVO vo = new SeBaExtendWarrantyVO();
			HashMap<String,String> hm;
			//将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			//将hashmap映射到vo对象中,完成匹配赋值
			vo.setValue(hm);
			//判断延保策略代码是否已存在
			QuerySet qs = dao.checkWarrantyCode(vo.getWarrantyCode());
			if(qs.getRowCount() > 0)
			{
				String n = qs.getString(1, 1);
				if(Integer.parseInt(n) > 0)
				{
					throw new Exception("延保策略代码已存在，保存失败！");
				}
			}
			//设置通用字段
			vo.setCreateUser(user.getPersonName());
			vo.setCreateTime(Pub.getCurrentDate());
			//通过dao，执行插入
			dao.insertExtendWarranty(vo);
			//返回插入结果和成功信息
			atx.setOutMsg(vo.getRowXml(),"延保策略代码新增成功！");
           
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
        }
    }

    /**
     * 更新延保策略
     * @throws Exception
     */
    public void update() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	SeBaExtendWarrantyVO tempVO = new SeBaExtendWarrantyVO();
        try
        {
            HashMap<String,String> hm;
            //将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			//将hashmap映射到vo对象中,完成匹配赋值
			tempVO.setValue(hm);
			//通过dao，执行更新
			tempVO.setUpdateTime(Pub.getCurrentDate());
			tempVO.setUpdateUser(user.getPersonName());
            dao.updateExtendWarranty(tempVO);
            //返回更新结果和成功信息
            atx.setOutMsg(tempVO.getRowXml(),"延保策略修改成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
        }
    }

    /**
     * 删除延保策略
     * @throws Exception
     */
    public void delete() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //通过request获取页面传递的参数，对于null值通过该方法将转换为""
        String warrantyId = Pub.val(request, "warrantyId");
        
        SeBaExtendWarrantyVO tempVO = new SeBaExtendWarrantyVO();
		tempVO.setWarrantyId(warrantyId);
		tempVO.setUpdateTime(Pub.getCurrentDate());
		tempVO.setUpdateUser(user.getPersonName());
		tempVO.setStatus(DicConstant.YXBS_02);
        try
        {
                dao.updateExtendWarranty(tempVO);
                //返回更新结果和成功信息
                atx.setOutMsg("","延保策略删除成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
        }
    }

    /**
     * 查询延保策略
     * @throws Exception
     */
    public void search() throws Exception
    {
    	//定义request对象
	    RequestWrapper request = atx.getRequest();
	    //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
		PageManager page = new PageManager();
		//将request流中的信息转化为where条件
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			//执行dao中search方法，BaseResultSet：结果集封装对象
			BaseResultSet bs = dao.search(page,user,conditions);
			//输出结果集，第一个参数”bs”为固定名称，不可修改
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			atx.setException(e);
		}
	}
    
    /**
     * 服务商车辆延保查询
     * @throws Exception
     */
    public void searchDealer() throws Exception
    {
    	//定义request对象
	    RequestWrapper request = atx.getRequest();
	    //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
		PageManager page = new PageManager();
		//将request流中的信息转化为where条件
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			//执行dao中search方法，BaseResultSet：结果集封装对象
			BaseResultSet bs = dao.searchDealer(page,user,conditions);
			//输出结果集，第一个参数”bs”为固定名称，不可修改
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			atx.setException(e);
		}
	}
}