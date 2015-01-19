package com.org.framework.component.orgmanage;

import java.util.Hashtable;
import java.sql.*;

import com.org.frameImpl.Constant;
import com.org.framework.common.*;
import com.org.framework.common.cache.*;

import java.util.Iterator;

/**
 * @Title: sciframe3.0
 * @Description:用户管理类
 * @Copyright: Copyright (c) 2014
 * @Date: Jul 2, 2014
 * @author andy.ten@tom.com
 */
public class UserManager
    implements Cache
{
    private Hashtable<String, User> usertable;
    static private UserManager instance;
    static private org.apache.log4j.Logger log = org.apache.log4j.Logger.
        getLogger("UserManager");

    private UserManager()
    {
        init();
    }

    public void synchronize(String account, int action)
        throws Exception
    {
   	
        User user = this.loadUserByAccount(account);
        if(user != null)
        {
        	User old = this.getUserByAccount(account.toUpperCase());
            if (action == CacheManager.ADD)
            {
                this.usertable.put(user.getAccount(), user);
            }
            else if (action == CacheManager.UPDATE)
            { 
                old.setPassWord(user.getPassWord());
                old.setPersonName(user.getPersonName());
                old.setSex(user.getSex());
                old.setOrgId(user.getOrgId());
                old.setOrgParentId(user.getOrgParentId());
                old.setOrgCode(user.getOrgCode());
                old.setPersonKind(user.getPersonKind());
                //old.setStatus(user.getStatus());
                old.setScretLevel(user.getScretLevel());
                old.setIdCard(user.getIdCard());
                old.setContactWay(user.getContactWay());
                old.setPersonType(user.getPersonType());
                old.setLoginIP(user.getLoginIP());
                old.setUserAuth(user.getUserAuth());
                
            }
            else if (action == CacheManager.DELETE)
            {
                usertable.remove(user.getAccount());
            }
            if (action == CacheManager.MAP_CHANGEED || action == CacheManager.DELETE)
            {
                Role[] roles = old.getRoles();
                if(roles != null)
                for (int i = 0; i < roles.length; i++)
                {
                	if(roles[i] != null)
                		roles[i].setUsers(null);
                }
                old.setRoles(null);
                old.setAllowedMenus(null);
                roles = user.getRoles();
                if (roles != null)
                {
                    for (int i = 0; i < roles.length; i++)
                    {
                    	if(roles[i] != null)
                    		roles[i].setUsers(null);
                    }
                }
            }
        }
    }

    private void init()
    {
    	DBFactory factory = new DBFactory();
        try
        {
            String[][] list = factory.select(
            		"SELECT U.ACCOUNT,U.PASSWORD,U.PERSON_NAME,U.SEX,U.ORG_ID, O.CODE ORG_CODE, U.PERSON_KIND,U.SECRET_LEVEL,U.IDCARD,U.CONTACT_WAY,U.PERSON_TYPE,U.USER_ID,O.COMPANY_ID,O.OEM_COMPANY_ID,O.PID,U.USER_AUTH "
                +" FROM TM_USER U,TM_ORG O WHERE U.ORG_ID = O.ORG_ID AND U.STATUS = '"+Constant.YXBS_01+"' AND O.STATUS = '"+Constant.YXBS_01+"'");
            if (list != null)
            {
                usertable = new Hashtable<String, User>(list.length);
                for (int i = 0; i < list.length; i++)
                {
                    User user = new UserVO();
                    user.setAccount(list[i][0]);
                    user.setPassWord(list[i][1]);
                    user.setPersonName(list[i][2]);
                    user.setSex(list[i][3]);
                    user.setOrgId(list[i][4]);
                    user.setOrgCode(list[i][5]);
                    user.setPersonKind(list[i][6]);
                    user.setScretLevel(list[i][7]);
                    user.setIdCard(list[i][8]);
                    user.setContactWay(list[i][9]);
                    user.setPersonType(list[i][10]);
                    user.setUserId(list[i][11]);
                    user.setCompanyId(list[i][12]);
                    user.setOemCompanyId(list[i][13]);
                    user.setOrgParentId(list[i][14]);
                    user.setUserAuth(list[i][15]);
                    usertable.put(list[i][0], user);
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
            if (factory.getConnection() != null)
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
                factory.setConnection(null);
                factory.setFactory(null);
                factory = null;
            }
        }
       
    }
    
    public User loadUserByAccount(String account)
    {
        DBFactory factory = new DBFactory();
        try
        {
            String[][] list = factory.select(
            		"SELECT U.ACCOUNT,U.PASSWORD,U.PERSON_NAME,U.SEX,U.ORG_ID, O.CODE ORG_CODE, U.PERSON_KIND,U.SECRET_LEVEL,U.IDCARD,U.CONTACT_WAY,U.PERSON_TYPE,U.USER_ID,O.COMPANY_ID,O.OEM_COMPANY_ID,O.PID,U.USER_AUTH "
                            +" FROM TM_USER U,TM_ORG O WHERE ACCOUNT = '"+account+"' AND U.ORG_ID = O.ORG_ID AND U.STATUS = '"+Constant.YXBS_01+"' AND O.STATUS = '"+Constant.YXBS_01+"'");
            if (list != null)
            {
                for (String[] element : list)
				{
                    User user = new UserVO();
                    user.setAccount(element[0]); 
                    user.setPassWord(element[1]);
                    user.setPersonName(element[2]);
                    user.setSex(element[3]);
                    user.setOrgId(element[4]);
                    user.setOrgCode(element[5]);
                    user.setPersonKind(element[6]);
                    user.setScretLevel(element[7]);
                    user.setIdCard(element[8]);
                    user.setContactWay(element[9]);
                    user.setPersonType(element[10]);
                    user.setUserId(element[11]);
                    user.setCompanyId(element[12]);
                    user.setOemCompanyId(element[13]);
                    user.setOrgParentId(element[14]);
                    user.setUserAuth(element[15]);
                    
                    return user;
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
                factory.setConnection(null);
                factory.setFactory(null);
                factory = null;
            }
        }
        return null;
    }
    
    public User getUserByXmAndIdcard(String name, String idcard)
    {   	    	
    	if(name == null || idcard == null)
    		return null;
    	
    	Iterator<User> itor = usertable.values().iterator();
    	while(itor.hasNext())
    	{
    		User user = (User) itor.next();
    		if(user.getPersonName() != null && user.getIdCard() != null)
    		{
    			if(user.getPersonName().trim().equals(name) && user.getIdCard().trim().equals(idcard))
    			{
    				return user;
    			}
    		}
    	}    	
    	return null;
    }

    
    public Hashtable<String, User> getUserTable()
    {
        return this.usertable;
    }
    
    public User getUserByAccount(String account)
    {
        return (User) usertable.get(account);
    } 
    
    static synchronized public UserManager getInstance()
    {
        if (instance == null)
        {
            instance = new UserManager();
            CacheManager.register(CacheManager.CACHE_USER, instance);
        }
        return instance;
    }
}