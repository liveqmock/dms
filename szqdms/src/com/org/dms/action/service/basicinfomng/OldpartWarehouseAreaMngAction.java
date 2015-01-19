package com.org.dms.action.service.basicinfomng;

import java.util.*;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.basicinfomng.OldpartWarehouseAreaMngDao;
import com.org.dms.vo.service.SeBaWarehouseAreaVO;
import com.org.dms.vo.service.SeBuWarehouseCentrostigmaVO;
import com.org.dms.vo.service.SeBuWarehouseUserVO;
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
 * @description: 旧件库区方法
 * @throws Exception 设定文件
 * @auther fanpeng
 * @date 2014年8月19日 
 */
public class OldpartWarehouseAreaMngAction
{
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private OldpartWarehouseAreaMngDao dao = OldpartWarehouseAreaMngDao.getInstance(atx);

    /**
     * 新增旧件库区
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
        	SeBaWarehouseAreaVO vo = new SeBaWarehouseAreaVO();
			HashMap<String,String> hm;
			//将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			//将hashmap映射到vo对象中,完成匹配赋值
			vo.setValue(hm);
			//判断库区代码是否已存在
			QuerySet qs = dao.checkCode(vo.getAreaCode());
			if(qs.getRowCount() > 0)
			{
				String n = qs.getString(1, 1);
				if(Integer.parseInt(n) > 0)
				{
					throw new Exception("库区代码已存在，保存失败！");
				}
			}
			//设置通用字段
			vo.setCreateUser(user.getPersonName());
			vo.setCreateTime(Pub.getCurrentDate());
			//通过dao，执行插入
			dao.insertOldpartWarehouseArea(vo);
			//返回插入结果和成功信息
			atx.setOutMsg(vo.getRowXml(),"旧件库区新增成功！");
           
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
        }
    }
    /**
     * 新增旧件仓库
     * @throws Exception
     */
    public void insertUser() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	try
    	{
    		SeBuWarehouseUserVO vo = new SeBuWarehouseUserVO();
    		HashMap<String,String> hm;
    		hm = RequestUtil.getValues(request);
    		vo.setValue(hm);
    		String userId=hm.get("T.USER_ID");
    		vo.setUserId(userId);
    		vo.setCreateUser(user.getPersonName());
    		vo.setCreateTime(Pub.getCurrentDate());
    		dao.insertOldpartWarehouseUser(vo);
    		atx.setOutMsg(vo.getRowXml(),"库管与仓库关系新增成功。");
    	}
    	catch (Exception e)
    	{
    		//设置失败异常处理
    		atx.setException(e);
    	}
    }
    /**
     * 新增旧件仓库与旧件集中点的关系
     * @throws Exception
     */
    public void insertOrg() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	try
    	{
    		SeBuWarehouseCentrostigmaVO vo = new SeBuWarehouseCentrostigmaVO();
    		HashMap<String,String> hm;
    		hm = RequestUtil.getValues(request);
    		vo.setValue(hm);
    		String orgId=hm.get("ORG_ID");
    		vo.setOrgId(orgId);
    		vo.setCreateUser(user.getPersonName());
    		vo.setCreateTime(Pub.getCurrentDate());
    		dao.insertOldpartWarehouseOrg(vo);
    		atx.setOutMsg(vo.getRowXml(),"旧件集中点与仓库关系新增成功。");
    	}
    	catch (Exception e)
    	{
    		//设置失败异常处理
    		atx.setException(e);
    	}
    }

    /**
     * 更新旧件集中点与仓库关系
     * @throws Exception
     */
    public void updateOrg() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	SeBuWarehouseCentrostigmaVO tempVO = new SeBuWarehouseCentrostigmaVO();
    	try
    	{
    		HashMap<String,String> hm;
    		hm = RequestUtil.getValues(request);
    		tempVO.setValue(hm);
    		String orgId=hm.get("ORG_ID");
    		tempVO.setOrgId(orgId);
    		tempVO.setUpdateTime(Pub.getCurrentDate());
    		tempVO.setUpdateUser(user.getPersonName());
    		dao.updateOldpartWarehouseOrg(tempVO);
    		atx.setOutMsg(tempVO.getRowXml(),"旧件集中点与仓库关系修改成功。");
    	}
    	catch (Exception e)
    	{
    		//设置失败异常处理
    		atx.setException(e);
    	}
    }
    /**
     * 更新库管与仓库关系
     * @throws Exception
     */
    public void updateUser() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	SeBuWarehouseUserVO tempVO = new SeBuWarehouseUserVO();
    	try
    	{
    		HashMap<String,String> hm;
    		hm = RequestUtil.getValues(request);
    		tempVO.setValue(hm);
    		String userId=hm.get("T.USER_ID");
    		tempVO.setUserId(userId);
    		tempVO.setUpdateTime(Pub.getCurrentDate());
    		tempVO.setUpdateUser(user.getPersonName());
    		dao.updateOldpartWarehouseUser(tempVO);
    		atx.setOutMsg(tempVO.getRowXml(),"库管与仓库关系修改成功。");
    	}
    	catch (Exception e)
    	{
    		//设置失败异常处理
    		atx.setException(e);
    	}
    }
    /**
     * 更新旧件库区
     * @throws Exception
     */
    public void update() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	SeBaWarehouseAreaVO tempVO = new SeBaWarehouseAreaVO();
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
            dao.updateOldpartWarehouseArea(tempVO);
            //返回更新结果和成功信息
            atx.setOutMsg(tempVO.getRowXml(),"旧件库区信息修改成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
        }
    }

    /**
     * 删除旧件库区
     * @throws Exception
     */
    public void delete() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //通过request获取页面传递的参数，对于null值通过该方法将转换为""
        String areaId = Pub.val(request, "areaId");
        
        SeBaWarehouseAreaVO tempVO = new SeBaWarehouseAreaVO();
		tempVO.setAreaId(areaId);
		tempVO.setUpdateTime(Pub.getCurrentDate());
		tempVO.setUpdateUser(user.getPersonName());
		tempVO.setStatus(DicConstant.YXBS_02);
        try
        {
                dao.updateOldpartWarehouseArea(tempVO);
                //返回更新结果和成功信息
                atx.setOutMsg("","旧件库位信息删除成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
        }
    }
    
    /**
     * 删除旧件仓库与库管的关系
     * @throws Exception
     */
    public void deleteUser() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	//通过request获取页面传递的参数，对于null值通过该方法将转换为""
    	String relationId = Pub.val(request, "relationId");
    	
    	SeBuWarehouseUserVO tempVO = new SeBuWarehouseUserVO();
    	tempVO.setRelationId(relationId);
    	tempVO.setUpdateTime(Pub.getCurrentDate());
    	tempVO.setUpdateUser(user.getPersonName());
    	tempVO.setStatus(DicConstant.YXBS_02);
    	try
    	{
    		dao.updateOldpartWarehouseUser(tempVO);
    		//返回更新结果和成功信息
    		atx.setOutMsg("","库管与仓库关系删除成功。");
    	}
    	catch (Exception e)
    	{
    		atx.setException(e);
    	}
    }
    /**
     * 删除旧件仓库与旧件集中点的关系
     * @throws Exception
     */
    public void deleteOrg() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	String relationId = Pub.val(request, "relationId");
    	
    	SeBuWarehouseCentrostigmaVO tempVO = new SeBuWarehouseCentrostigmaVO();
    	tempVO.setRelationId(relationId);
    	tempVO.setUpdateTime(Pub.getCurrentDate());
    	tempVO.setUpdateUser(user.getPersonName());
    	tempVO.setStatus(DicConstant.YXBS_02);
    	try
    	{
    		dao.updateOldpartWarehouseOrg(tempVO);
    		//返回更新结果和成功信息
    		atx.setOutMsg("","旧件集中点与仓库关系删除成功。");
    	}
    	catch (Exception e)
    	{
    		atx.setException(e);
    	}
    }

    /**
     * 查询旧件库位
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
     * 查询旧件仓库与用户之间的关系
     * @throws Exception
     */
    public void WarehouseSearch() throws Exception
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
    		BaseResultSet bs = dao.WarehouseSearch(page,user,conditions);
    		//输出结果集，第一个参数”bs”为固定名称，不可修改
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		atx.setException(e);
    	}
    }
    /**
     * 查询旧件仓库与旧件集中点之间的关系
     * @throws Exception
     */
    public void returnDealerSearch() throws Exception
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
    		BaseResultSet bs = dao.returnDealerSearch(page,user,conditions);
    		//输出结果集，第一个参数”bs”为固定名称，不可修改
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		atx.setException(e);
    	}
    }
}