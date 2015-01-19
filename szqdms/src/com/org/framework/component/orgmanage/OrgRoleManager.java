package com.org.framework.component.orgmanage;

import java.util.*;
import java.sql.*;

import com.org.frameImpl.Constant;
import com.org.framework.common.*;
import com.org.framework.common.cache.*;
import com.org.framework.params.ParaManager;
import com.org.framework.params.SysPara.SysParaConfigureVO;
import com.org.framework.util.*;
import com.org.framework.common.DBFactory;
import com.org.framework.common.User;
import com.org.framework.common.Role;
/**
 * @Description:机构角色管理类
 * @Copyright: Copyright (c) 2011
 * @Date: 2011-10-7
 * @mail   andy.ten@tom.com
 */
public class OrgRoleManager
    implements Cache
{
    private Hashtable<String, Role> roletable;
    private static OrgRoleManager instance;
    private static org.apache.log4j.Logger log = org.apache.log4j.Logger.
        getLogger("OrgRoleManager");

    public void synchronize(String roleId, int action)
        throws Exception
    {
	
        Role role = this.loadRole(roleId);
        Role old = this.getRole(roleId);
        if (action == CacheManager.ADD)
        {
            this.roletable.put(role.getRoleId(), role);
        }
        else if (action == CacheManager.UPDATE)
        {
            old.setRName(role.getRName());
            old.setLevelCode(role.getLevelCode());
            old.setRoleType(role.getRoleType());
            old.setOrgId(role.getOrgId());
        }
        else if (action == CacheManager.DELETE)
        {
            role = (Role)roletable.remove(roleId);
        }
        if (action == CacheManager.MAP_CHANGEED || action == CacheManager.DELETE)
        {
            User[] users = old.getUsers();
            if(users != null)
            for (int i = 0; i < users.length; i++)
            {
            	if(users[i] == null)
            		continue;
                users[i].setRoles(null);
                users[i].setAllowedMenus(null);
            }
            old.setUsers(null);
            old.setMenus(null);
            users = role.getUsers();
            if (users != null)
            {
                for (int i = 0; i < users.length; i++)
                {
                    users[i].setRoles(null);
                    users[i].setAllowedMenus(null);
                }
            }
        }
    }

    private OrgRoleManager()
    {    
        DBFactory factory = new DBFactory();
        try
        {
            String[][] list = factory.select(
                "SELECT ROLE_ID,CODE,RNAME,LEVEL_CODE,ROLE_TYPE,ORG_ID  "
                + "FROM TM_ROLE WHERE STATUS = '"+Constant.YXBS_01+"' ");
            if (list != null)
            {
                roletable = new Hashtable<String, Role>(list.length);
                for (int i = 0; i < list.length; i++)
                {
                    Role role = new RoleVO();
                    role.setRoleId(list[i][0]);
                    role.setCode(list[i][1]);
                    role.setRName(list[i][2]);
                    role.setLevelCode(list[i][3]);
                    role.setOrgId(list[i][4]);
                    roletable.put(role.getRoleId(), role);
                }
            }
            else
                roletable = new Hashtable<String, Role>();
        }
        catch (Exception e)
        {
            log.error(e);
            e.printStackTrace(System.out);
        }
        finally
        {
            if (factory != null)
            {
                try
                {
                	if (factory != null)
                    {
                    	factory.getConnection().close();
                    	factory.setConnection(null);
                    }
                }
                catch (SQLException ex)
                {
                }
                factory.setFactory(null);
                factory = null;
            }
        }
    }
 
    private Role loadRole(String roleId)
    {
        DBFactory factory = new DBFactory();
        try
        {
            String[][] list = factory.select(
            		"SELECT ROLE_ID,CODE,RNAME,LEVEL_CODE,ROLE_TYPE,ORG_ID "
                            + "FROM TM_ROLE WHERE ROLE_ID = "+roleId+" ");
            if (list != null)
            {
                for (String[] element : list)
				{
                    Role role = new RoleVO();
                    role.setRoleId(element[0]);
                    role.setCode(element[1]);
                    role.setRName(element[2]);
                    role.setLevelCode(element[3]);
                    role.setRoleType(element[4]);
                    role.setOrgId(element[5]);
                    return role;
                }
            }
        }
        catch (Exception e)
        {
        	log.error(e);
            e.printStackTrace(System.out);
        }
        finally
        {
            if (factory != null)
            {
                try
                {
                	if (factory != null)
                    {
                    	factory.getConnection().close();
                    	factory.setConnection(null);
                    }
                }
                catch (SQLException ex)
                {
                }
            }
            factory.setFactory(null);
            factory = null;
        }
        return null;
    }


    public Role getRole(String roleId)
    {
        return (Role) this.roletable.get(roleId);
    }
    public Role[] getUserRoles(String userId)
    {
        if (Pub.empty(userId))
            return null;
        DBFactory factory = new DBFactory();
        try
        {
            String sql =
                "SELECT ROLE_ID, USER_ID FROM TR_ROLE_USER_MAP WHERE USER_ID=" +
                userId + "";
            String[][] list = factory.select(sql);
            if (list != null)
            {
                Role[] roles = new Role[list.length];
                for (int i = 0; i < list.length; i++)
                {
                    roles[i] = (Role)this.roletable.get(list[i][0]);
                }
                return roles;
            }
        }
        catch (Exception e)
        {
        	log.error(e);
            e.printStackTrace(System.out);
        }
        finally
        {
            try
            {
            	if (factory != null)
                {
                	factory.getConnection().close();
                	factory.setConnection(null);
                }
            }
            catch (Exception ee)
            {}
            factory.setFactory(null);
            factory = null;
        }
        return null;
        
    }

    public User[] getRoleUsers(String roleId)
    {
        if (Pub.empty(roleId))
            return null;
        DBFactory factory = new DBFactory();
        try
        {
            String sql =
                "SELECT ROLE_ID,ACCOUNT FROM TR_ROLE_USER_MAP WHERE ROLE_ID='" +
                		roleId + "'";
            String[][] list = factory.select(sql);
            if (list != null)
            {
                User[] users = new User[list.length];
                for (int i = 0; i < list.length; i++)
                {
                	if(list[i][1] == null) continue;
                    users[i] = UserManager.getInstance().getUserByAccount(
                        list[i][1].toUpperCase());
                }
                return users;
            }
        }
        catch (Exception e)
        {
        	log.error(e);
            e.printStackTrace(System.out);
        }
        finally
        {
            try
            {
            	if (factory != null)
                {
                	factory.getConnection().close();
                	factory.setConnection(null);
                }
            }
            catch (Exception ee)
            {}
            factory.setFactory(null);
            factory = null;
        }
        return null;
        
    }

    public Menu[] getRoleMenus(String roleId)
    {   	
        if (Pub.empty(roleId))
            return null;
        DBFactory factory = new DBFactory();
        try
        {
            String sql =
                "SELECT ROLE_ID,MENU_NAME FROM TR_ROLE_MENU_MAP r,EAP_MENU m"
                + " WHERE r.MENU_NAME=m.NAME AND ROLE_ID='" + roleId +
                "' order by m.PARENT,m.ORDERNO";
            String[][] list = factory.select(sql);
            if (list != null)
            {
                Menu[] menus = new Menu[list.length];
                for (int i = 0; i < list.length; i++)
                {
                    menus[i] = MenuManager.getInstance().getMenu(list[i][1]);
                }
                return menus;
            }
        }
        catch (Exception e)
        {
        	log.error(e);
            e.printStackTrace(System.out);
        }
        finally
        {
            try
            {
            	if (factory != null)
                {
                	factory.getConnection().close();
                	factory.setConnection(null);
                }
            }
            catch (Exception ee)
            {}
            factory.setFactory(null);
            factory = null;
        }
        return null;     
    }
    
    public Menu[] getGrantedMenusByParent(String roleId, String parentName)
    {   	
        if (Pub.empty(roleId))
            return null;
        DBFactory factory = new DBFactory();
        try
        {
            SysParaConfigureVO syspara = (SysParaConfigureVO) ParaManager.getInstance().getSysParameter("DATASOURCE");
            String sql;
            if (syspara != null && syspara.getParavalue1().equals("1"))//oracle
            {
            	sql = "select m.name,m.parent from tr_role_menu_map r,eap_menu m"
                        + " where r.MENU_NAME=m.NAME and ROLE_ID='" + roleId +
                        "' order by PARENT,ORDERNO";
            	//sql = "select name,parent from eap_menu start with parent='"+parentName+"' connect by prior  name = parent";
            //sqlserver
            }else
            {
            	sql = " WITH NODES AS (SELECT e.NAME,e.PARENT FROM EAP_MENU e,TR_ROLE_MENU_MAP m WHERE m.MENU_NAME = e.NAME AND m.ROLE_ID ="+roleId;
                sql += " UNION ALL SELECT EM.NAME,EM.PARENT FROM EAP_MENU AS EM INNER JOIN NODES  AS RC ON EM.PARENT = RC.NAME) ";
                sql += " SELECT a.NAME,a.PARENT FROM EAP_MENU a ,TR_ROLE_MENU_MAP m1 WHERE m1.MENU_NAME = a.NAME AND m1.ROLE_ID="+roleId+" AND NAME IN (SELECT NAME  FROM NODES N ) ";
            }
            
            String[][] list = factory.select(sql);
            if (list != null)
            {
                Menu[] menus = new Menu[list.length];
                for (int i = 0; i < list.length; i++)
                {
                    menus[i] = MenuManager.getInstance().getMenu(list[i][0]);
                }
                return menus;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace(System.out);
        }
        finally
        {
            try
            {
            	if (factory != null)
                {
                	factory.getConnection().close();
                	factory.setConnection(null);
                }
            }
            catch (Exception ee)
            {}
            factory.setFactory(null);
            factory = null;
        }
        return null;     
    }

    static synchronized public OrgRoleManager getInstance()
    {
        if (instance == null)
        {
            instance = new OrgRoleManager();
            CacheManager.register(CacheManager.CACHE_ROLE, instance);
        }
        return instance;
    }

}