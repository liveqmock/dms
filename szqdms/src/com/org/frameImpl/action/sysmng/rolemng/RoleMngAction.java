package com.org.frameImpl.action.sysmng.rolemng;

import java.util.HashMap;

import com.org.frameImpl.dao.sysmng.rolemng.RoleMngDao;
import com.org.frameImpl.vo.TmRoleVO;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.Role;
import com.org.framework.common.cache.CacheManager;
import com.org.framework.log.LogManager;
import com.org.framework.Globals;
import com.org.framework.common.User;
import com.org.framework.component.orgmanage.OrgRoleManager;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * @Description:角色管理类
 * @Copyright: Copyright (c) 2011
 * @Date: 2011-10-7
 * @mail   andy.ten@tom.com
 */
public class RoleMngAction
{
    private static org.apache.log4j.Logger logger = org.apache.log4j.LogManager.
        getLogger("RoleMngAction");
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private RoleMngDao dao = RoleMngDao.getInstance(atx);

    public void insert()
        throws Exception
    {
        RequestWrapper request = atx.getRequest();
    	User user = (User) request.getSession().get(Globals.USER_KEY);
		try
		{
			TmRoleVO vo = new TmRoleVO();
			HashMap<String,String> hm = new HashMap<String, String>();
			hm = RequestUtil.getValues(request);
			vo.setValue(hm); 
			//判断角色代码是否已存在
			QuerySet qs = dao.checkRoleCode(vo.getCode());
			if(qs.getRowCount() > 0)
			{ 
				String n = qs.getString(1, 1);
				if(Integer.parseInt(n) > 0)
				{
					throw new Exception("角色代码已存在，保存失败！");
				}
			}
			dao.insertRole(vo);
			atx.setOutMsg(vo.getRowXml(), "角色保存成功");
			// 写日志
			LogManager.writeUserLog("", "",Globals.OPERATION_TYPE_INSERT, LogManager.RESULT_SUCCESS,"新增角色："+vo.getCode(), user);
		}
		catch (Exception e)
		{
			logger.error(e);
            atx.setException(e);
		}
    }
    public void synchronize() 
    	throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	String roleId = Pub.val(request, "roleId");
    	String action = Pub.val(request, "type");
    	int type = 0;
    	if("1".equals(action))
    		type = 1;
    	else if("2".equals(action))
    		type = 2;
    	else if("3".equals(action))
    		type = 3;
    	else if("4".equals(action))
    		type = 4;
    	//广播节点
        CacheManager.broadcastChanges(CacheManager.CACHE_ROLE,roleId, type);
    }
    public void update()
        throws Exception
    { 
    	RequestWrapper request = atx.getRequest();
        User user = (User) request.getSession().get(Globals.USER_KEY);
        TmRoleVO tempVO = new TmRoleVO();
        try
        {
            HashMap<String,String> hm = new HashMap<String, String>();
			hm = RequestUtil.getValues(request);
			tempVO.setValue(hm);
            tempVO.setUpdateUser(user.getAccount());
            tempVO.setUpdateTime(Pub.getCurrentDate());
            dao.updateRole(tempVO);
            atx.setOutMsg(tempVO.getRowXml(), "操作成功");
            LogManager.writeUserLog("", "", Globals.OPERATION_TYPE_UPDATE,LogManager.RESULT_SUCCESS,"角色变更：" + tempVO.getCode(), user);
            //广播节点
            CacheManager.broadcastChanges(CacheManager.CACHE_ROLE,tempVO.getRoleId(),CacheManager.UPDATE);
        }
        catch (Exception e)
        {
            logger.error(e);
            atx.setException(e);
        }
    }
    /**
     * 删除角色
     * @throws Exception
     * @author andy.ten@tom.com 
     * @Time Jul 10, 2014 3:01:16 PM
     */
    public void delete()
        throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) request.getSession().get(Globals.USER_KEY);
        try
        {
        	String roleId = Pub.val(request, "roleId");
        	if("".equals(roleId))
        		throw new Exception("无主键，删除失败！");
            boolean res = dao.deleteRole(roleId);
            dao.deleteRoleMenuMapByRid(roleId);
            dao.deleteRoleUserMapByRid(roleId);
            if (res)
            {
                logger.info("角色 [" + roleId + "] 删除...");
                atx.setOutMsg("", "删除角色成功！");
                LogManager.writeUserLog("", "",Globals.OPERATION_TYPE_DELETE,LogManager.RESULT_SUCCESS,"角色删除：" + roleId,user);
            }else
            {
            	throw new Exception("删除角色失败！");
            }
        }
        catch (Exception e)
        {
            logger.error(e);
            atx.setException(e);
        }
    }
    
    /**
     * 查询角色
     * @throws Exception
     * @author andy.ten@tom.com 
     * @Time Jul 10, 2014 11:24:56 AM
     */
    public void search()
    		throws Exception
	{
    	RequestWrapper request = atx.getRequest();
    	//如果需要使用user信息，则使用：
    	User user = (User) request.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.search(page, user, conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
            atx.setException(e);
		}
	}
    
    /**
     * 保存角色菜单关系
     * @throws Exception
     * @author andy.ten@tom.com 
     * @Time Jul 10, 2014 2:37:09 PM
     */
    public void saveRoleMenuMap()
            throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	String roleId = Pub.val(request, "roleId");
        String rootname = Pub.val(request, "rootname");
        String menusList = Pub.val(request, "data");
        
        try
        {
            String[] list = menusList.split(",");
            //删除角色和菜单对应关系
            dao.deleteRoleMenuMap(roleId, rootname);
            //插入角色和菜单对应关系
            if(menusList.length() > 0)
            {
                for (int i = 0; i < list.length; i++)
                {
                    if (Pub.empty(list[i]))
                        continue;
                    dao.insertRoleMenuMap(roleId, list[i], rootname);
                }
            }
            atx.setOutMsg("", "保存成功");
        }
        catch (Exception e)
        {
            logger.error(e);
            atx.setException(e);
        }
    }
    
    /**
     * 查询未赋予角色的用户
     */
    public void selectUnGrantedPerson()
            		throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) request.getSession().get(Globals.USER_KEY);
    	PageManager page = new PageManager();
    	String roleId = Pub.val(request, "roleId");
    	String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.selectUnGrantedPerson(page, user, conditions, roleId);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
            atx.setException(e);
		}
	}
    
    /**
     * 查询已赋予角色的用户
     */
    public void selectGrantedPerson()
            		throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) request.getSession().get(Globals.USER_KEY);
    	PageManager page = new PageManager();
    	String roleId = Pub.val(request, "roleId");
    	String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.selectGrantedPerson(page, user, conditions, roleId);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
            atx.setException(e);
		}
	}
    
    /**
     * 保存角色和用户关系
     * @throws Exception
     * @author andy.ten@tom.com 
     * @Time Jul 10, 2014 3:46:03 PM
     */
    public void saveRoleUserMap()
    		throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	String roleId = Pub.val(request, "roleId");
    	Role role = OrgRoleManager.getInstance().getRole(roleId);
    	String roleCode = role.getCode();
    	String userIds = "",accounts = "";
    	try
    	{
			HashMap<String,String> hm = new HashMap<String, String>();
			hm = RequestUtil.getValues(request);
			userIds = hm.get("USERIDS");
			accounts = hm.get("ACCOUNTS");
			String[] listUserId = userIds.split("\\,");
			String[] listAccount = accounts.split("\\,");
			for (int i = 0; i < listUserId.length; i++)
			{
				if (Pub.empty(listUserId[i]))
					continue;
				String userId = listUserId[i];
				String account = listAccount[i];
				dao.saveRoleUserMap(roleId, userId, roleCode, account);
			}

			atx.setOutMsg("", "保存成功！");
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    
    public void removeRoleUserMap()
    		throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	String roleId = Pub.val(request, "roleId");
    	String userIds = "";
    	try
    	{
			HashMap<String,String> hm = new HashMap<String, String>();
			hm = RequestUtil.getValues(request);
			userIds = hm.get("USERIDS");

			dao.removeRoleUserMap(roleId, userIds);

			atx.setOutMsg("", "保存成功！");
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
}
