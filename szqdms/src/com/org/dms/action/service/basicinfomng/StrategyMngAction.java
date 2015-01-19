package com.org.dms.action.service.basicinfomng;

import java.util.HashMap;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.basicinfomng.StrategyMngDao;
import com.org.dms.vo.service.SeBaStrategyModelsVO;
import com.org.dms.vo.service.SeBaStrategyProvinceVO;
import com.org.dms.vo.service.SeBaStrategyVO;

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
 * @description: 三包策略管理方法
 * @throws Exception 设定文件
 * @auther fanpeng
 * @date 2014年09月03日 
 */
public class StrategyMngAction
{
    private ActionContext atx = ActionContext.getContext();
    private StrategyMngDao dao = StrategyMngDao.getInstance(atx);

    /**
     * 新增三包策略
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
        	SeBaStrategyVO vo = new SeBaStrategyVO();
			HashMap<String,String> hm;
			//将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			//将hashmap映射到vo对象中,完成匹配赋值
			vo.setValue(hm);
			//判断三包策略代码是否已存在
			QuerySet qs = dao.checkCode(vo.getStrategyCode());
			if(qs.getRowCount() > 0)
			{
				String n = qs.getString(1, 1);
				if(Integer.parseInt(n) > 0)
				{
					throw new Exception("三包策略代码已存在，保存失败！");
				}
			}
			//设置通用字段
			vo.setCreateUser(user.getPersonName());
			vo.setCreateTime(Pub.getCurrentDate());
			//通过dao，执行插入
			dao.insertStrategy(vo);
			//返回插入结果和成功信息
			atx.setOutMsg(vo.getRowXml(),"三包策略新增成功！");
           
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
        }
    }
    
    /**
     * 新增三包车型
     * @throws Exception
    */ 
    public void insertModels() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	SeBaStrategyModelsVO vo = new SeBaStrategyModelsVO();
        	HashMap<String, String> hm;
            //将request流转换为hashmap结构体
            hm = RequestUtil.getValues(request);
            String modelsIds=hm.get("MODELS_IDS");
			String strategyId=hm.get("STRATEGY_ID");

			String createUser=user.getPersonName();
			dao.insertModels(modelsIds,createUser,strategyId);
			atx.setOutMsg(vo.getRowXml(),"三包策略车型新增成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
        }
    }
    
    /**
     * 新增三包省份
     * @throws Exception
    */ 
    public void insertProvince() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	
        	SeBaStrategyProvinceVO vo = new SeBaStrategyProvinceVO();
        	HashMap<String, String> hm;
            //将request流转换为hashmap结构体
            hm = RequestUtil.getValues(request);
            String provinceIds=hm.get("PROVINCE_IDS");
			String strategyId=hm.get("STRATEGY_ID");

			String createUser=user.getPersonName();
			dao.insertProvince(provinceIds,createUser,strategyId);
			atx.setOutMsg(vo.getRowXml(),"三包策略省份新增成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
        }
    }
    
    /**
     * 更新三包策略
     * @throws Exception
     */
    public void update() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	SeBaStrategyVO tempVO = new SeBaStrategyVO();
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
            dao.updateStrategy(tempVO);
            //返回更新结果和成功信息
            atx.setOutMsg(tempVO.getRowXml(),"三包策略修改成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
        }
    }

    /**
     * 删除三包策略
     * @throws Exception
     */
    //删除只更新有效标识，更新人，更新时间，所以目前与update方法一致
    public void delete() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //通过request获取页面传递的参数，对于null值通过该方法将转换为""
        String strategyId = Pub.val(request, "strategyId");
        
        SeBaStrategyVO tempVO = new SeBaStrategyVO();
		tempVO.setStrategyId(strategyId);
		tempVO.setUpdateTime(Pub.getCurrentDate());
		tempVO.setUpdateUser(user.getPersonName());
		tempVO.setStatus(DicConstant.YXBS_02);
        try
        {
                dao.updateStrategy(tempVO);
                //返回更新结果和成功信息
                atx.setOutMsg("","三包策略删除成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
        }
    }
    
    /**
     * 删除三包策略车型
     * @throws Exception
     */
    public void deleteModels() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //通过request获取页面传递的参数，对于null值通过该方法将转换为""
        String relationIds = Pub.val(request, "relationIds");
        String userName = user.getPersonName();
        
        try
        {
                dao.deleteModels(relationIds,userName);
                //返回更新结果和成功信息
                atx.setOutMsg("","三包策略车型删除成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
        }
    }
    
    /**
     * 删除三包策略省份
     * @throws Exception
     */
    public void deleteProvince() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //通过request获取页面传递的参数，对于null值通过该方法将转换为""
        String relationIds = Pub.val(request, "relationIds");
        String userName = user.getPersonName();
        
        try
        {
                dao.deleteProvince(relationIds,userName);
                //返回更新结果和成功信息
                atx.setOutMsg("","三包策略省份删除成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
        }
    }

    /**
     * @title: search
     * @description: TODO(查询三包策略方法)
     * @throws Exception    设定文件
     * @return void    返回类型
     * @auther fanpeng
     * @date 2014年9月2日
     */
    public void search() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.search(page,user,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			atx.setException(e);
		}
	}
    /**        
     * @title: searchStrategyModels
     * @description: TODO(查询三包策略车型方法)
     * @throws Exception    设定文件
     * @return void    返回类型
     * @auther fanpeng
     * @date 2014年09月03日
    */
    public void searchStrategyModels() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{	
			String strategyId=Pub.val(request, "strategyId");
			BaseResultSet bs = dao.searchStrategyModels(page,user,conditions,strategyId);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			atx.setException(e);
		}
	}
    
    /**        
     * @title: searchStrategyModels
     * @description: TODO(查询三包策略省份方法)
     * @throws Exception    设定文件
     * @return void    返回类型
     * @auther fanpeng
     * @date 2014年09月03日
    */
    public void searchStrategyProvince() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{	
			String strategyId=Pub.val(request, "strategyId");
			BaseResultSet bs = dao.searchStrategyProvince(page,user,conditions,strategyId);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			atx.setException(e);
		}
	}    
    
    /**
     * @title: searchModels
     * @description: TODO(查询所有未绑定三包策略的车型方法)
     * @throws Exception    设定文件
     * @return void    返回类型
     * @auther fanpeng
     * @date 2014年09月03日
     */
    public void searchModels() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{	
			String strategyId=Pub.val(request, "strategyId");
			BaseResultSet bs = dao.searchModels(page,user,conditions,strategyId);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			atx.setException(e);
		}
	}
    /**
     * @title: searchProvince
     * @description: TODO(查询所有未绑定三包策略的省份方法)
     * @throws Exception    设定文件
     * @return void    返回类型
     * @auther fanpeng
     * @date 2014年09月04日
     */
    public void searchProvince() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{	
			String strategyId=Pub.val(request, "strategyId");
			BaseResultSet bs = dao.searchProvince(page,user,conditions,strategyId);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			atx.setException(e);
		}
	}
}