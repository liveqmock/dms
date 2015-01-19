package com.org.framework.component.orgmanage;

import java.util.Hashtable;
import java.sql.*;

import com.org.frameImpl.Constant;
import com.org.framework.common.*;
import com.org.framework.common.cache.*;
import com.org.framework.common.DBFactory;
import com.org.framework.util.Pub;

/**
 * @Copyright: Copyright (c) 2011
 * @Date: 2011-10-7
 * @mail   andy.ten@tom.com
 */
public class OrgDeptManager
    implements Cache
{
    private Hashtable<String, OrgDept> orgtable;
    static private OrgDeptManager instance;
    static private org.apache.log4j.Logger log = org.apache.log4j.Logger.
        getLogger("OrgDeptManager");
    private OrgDeptManager()
    {
        init();
    }

    public void synchronize(String orgId, int action)
        throws Exception
    {
   	
        this.orgtable.remove(orgId);
        if (action == CacheManager.ADD || action == CacheManager.UPDATE)
        { //
            OrgDept orgdept = this.getDeptByID(orgId);
            this.orgtable.put(orgId, orgdept);
        }
        else if (action == CacheManager.DELETE)
        {
            this.orgtable.remove(orgId);
        }
        //同步组织机构xml
        String path = AppInit.getAppPath();
        OrgDeptToXml.exportAllXml(path, "ZZJG", "'1','2','3','4'");
    }

    private void init()
    {
   	
        DBFactory factory = null;
        try
        {
        	factory = new DBFactory();
            String[][] list = factory.select(
            		"SELECT ORG_ID, CODE, ONAME , DIVISION_CODE, PID, SNAME, LEVEL_CODE, COMPANY_ID, ORG_TYPE, BUS_TYPE, OEM_COMPANY_ID "
            		+ " FROM TM_ORG WHERE STATUS = '" +Constant.YXBS_01+ "'");
            if (list != null)
            {
                orgtable = new Hashtable<String, OrgDept>(list.length);
                for (int i = 0; i < list.length; i++)
                {
                    OrgDept dept = new OrgDeptVO();
                    dept.setOrgId(list[i][0]);
                    dept.setOrgCode(list[i][1]);
                    dept.setOName(list[i][2]);
                    dept.setDivisionCode(list[i][3]);
                    dept.setPId(list[i][4]);
                    dept.setSname(list[i][5]);
                    dept.setLevelCode(list[i][6]);
                    dept.setCompanyId(list[i][7]);
                    dept.setOrgType(list[i][8]);
                    dept.setBusType(list[i][9]);
                    dept.setOemCompanyId(list[i][10]);
                    
                    orgtable.put(list[i][0], dept);
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
                factory.setFactory(null);
                factory = null;
            }
        }
    }
    
    public OrgDept loadDept(String orgCode)
    {
    	DBFactory factory = new DBFactory();
        OrgDept orgDept = null;
        try
        {
            String[][] list = factory.select(
            		"SELECT ORG_ID, CODE, ONAME , DIVISION_CODE, PID, SNAME, LEVEL_CODE, COMPANY_ID, ORG_TYPE, BUS_TYPE, OEM_COMPANY_ID "
                    		+ " FROM TM_ORG WHERE CODE = '"+orgCode+"' STATUS = '" +Constant.YXBS_01+ "'");
            if (list != null)
            {
                for (int i = 0; i < list.length; i++)
                {
                    OrgDept dept = new OrgDeptVO();
                    dept.setOrgId(list[i][0]);
                    dept.setOrgCode(list[i][1]);
                    dept.setOName(list[i][2]);
                    dept.setDivisionCode(list[i][3]);
                    dept.setPId(list[i][4]);
                    dept.setSname(list[i][5]);
                    dept.setLevelCode(list[i][6]);
                    dept.setCompanyId(list[i][7]);
                    dept.setOrgType(list[i][8]);
                    dept.setBusType(list[i][9]);
                    dept.setOemCompanyId(list[i][10]);
                    
                    orgtable.put(list[i][0], dept);
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
                factory.setFactory(null);
                factory = null;
            }
        }
       return orgDept;
    }

    public OrgDept getDeptByID(String orgId)
    {
    	if (Pub.empty(orgId))
            return null;
        OrgDept res = (OrgDept) orgtable.get(orgId);
        if (res == null)
        {
            DBFactory factory = new DBFactory();
            try
            {
                res = getDeptByID(factory, orgId);
            }
            catch (Exception e)
            {
                log.error(e);
                e.printStackTrace();
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
                catch (Exception ex)
                {}
                factory.setConnection(null);
            }
        }
        return res;
    }

    public OrgDept getDeptByID(DBFactory factory, String orgId) throws Exception
    {
        if (Pub.empty(orgId))
            return null;
        Object res = orgtable.get(orgId);
        if (res != null)
            return (OrgDept) res;
        String[][] list = factory.select( 
        		"SELECT ORG_ID, CODE, ONAME , DIVISION_CODE, PID, SNAME, LEVEL_CODE, COMPANY_ID, ORG_TYPE, BUS_TYPE, OEM_COMPANY_ID "
                		+ " FROM TM_ORG WHERE ORG_ID = "+orgId+" AND STATUS = '" +Constant.YXBS_01+ "'");
        if (list != null)
        {
            int i = 0;
            OrgDept dept = new OrgDeptVO();
            dept.setOrgId(list[i][0]);
            dept.setOrgCode(list[i][1]);
            dept.setOName(list[i][2]);
            dept.setDivisionCode(list[i][3]);
            dept.setPId(list[i][4]);
            dept.setSname(list[i][5]);
            dept.setLevelCode(list[i][6]);
            dept.setCompanyId(list[i][7]);
            dept.setOrgType(list[i][8]);
            dept.setBusType(list[i][9]);
            dept.setCompanyId(list[i][10]);
            
            orgtable.put(list[i][0], dept);
            return dept;
        }
        return null;
    }

    static synchronized public OrgDeptManager getInstance()
    {
        if (instance == null)
        {
            instance = new OrgDeptManager();
            CacheManager.register(CacheManager.CACHE_ORG_DEPT,instance);
        }
        return instance;
    }
}