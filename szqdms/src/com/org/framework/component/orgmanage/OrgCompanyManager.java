package com.org.framework.component.orgmanage;

import java.util.Hashtable;
import java.sql.*;

import com.org.frameImpl.Constant;
import com.org.framework.common.*;
import com.org.framework.common.cache.*;
import com.org.framework.common.DBFactory;
import com.org.framework.util.Pub;

/**
 * @Description:公司管理类
 * @Copyright: Copyright (c) 2011
 * @Date: 2011-10-7
 * @mail   andy.ten@tom.com
 */
public class OrgCompanyManager
    implements Cache
{
    private Hashtable<String, OrgCompany> orgtable;
    static private OrgCompanyManager instance;
    static private org.apache.log4j.Logger log = org.apache.log4j.Logger.
        getLogger("OrgCompanyManager");
    private OrgCompanyManager()
    {
        init();
    }

    public void synchronize(String companyId, int action)
        throws Exception
    {
   	
        this.orgtable.remove(companyId);

        if (action == CacheManager.ADD || action == CacheManager.UPDATE)
        { //

            OrgCompany orgCompany = this.getCompanyByID(companyId);
            this.orgtable.put(companyId, orgCompany);
        }
        else if (action == CacheManager.DELETE)
        {
            this.orgtable.remove(companyId);
        }
    }

    private void init()
    {
        DBFactory factory = new DBFactory();
        try
        {
            String[][] list = factory.select(
            		"SELECT COMPANY_ID, CODE, CNAME, SNAME,COMPANY_TYPE"
            		+ " FROM TM_COMPANY WHERE STATUS = '"+Constant.YXBS_01+"' ");
            if (list != null)
            {
                orgtable = new Hashtable<String, OrgCompany>(list.length);
                for (int i = 0; i < list.length; i++)
                {
                	OrgCompany company = new OrgCompanyVO();
                	company.setCompanyId(list[i][0]);
                	company.setCode(list[i][1]);
                	company.setCName(list[i][2]);
                	company.setSName(list[i][3]);
                	company.setCompanyType(list[i][4]);
                	
                    orgtable.put(list[i][0], company);
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
    }

    public OrgCompany getCompanyByID(String companyId)
    {
    	if (Pub.empty(companyId))
            return null;
    	OrgCompany res = (OrgCompany) orgtable.get(companyId);
        if (res == null)
        {
            DBFactory factory = new DBFactory();
            try
            {
                res = getCompanyByID(factory, companyId);
            }
            catch (Exception e)
            {
                log.error(e);
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
                factory.setFactory(null);
                factory = null;
            }
        }
        return res;
    }

    public OrgCompany getCompanyByID(DBFactory factory, String companyId) throws Exception
    {
        if (Pub.empty(companyId))
            return null;
        Object res = orgtable.get(companyId);
        if (res != null)
            return (OrgCompany) res;
        String[][] list = factory.select(  
        		"SELECT COMPANY_ID, CODE, CNAME, SNAME,COMPANY_TYPE"
                		+ " FROM TM_COMPANY WHERE COMPANY_ID = "+companyId+ " AND STATUS = '"+Constant.YXBS_01+"' "); 
        if (list != null)
        {
            int i = 0;
            OrgCompany company = new OrgCompanyVO();
            company.setCompanyId(list[i][0]);
        	company.setCode(list[i][1]);
        	company.setCName(list[i][2]);
        	company.setSName(list[i][3]);
        	company.setCompanyType(list[i][4]);
            orgtable.put(list[i][0], company);
            
            return company;
        }
        return null;
    }

    static synchronized public OrgCompanyManager getInstance()
    {
        if (instance == null)
        {
            instance = new OrgCompanyManager();
            CacheManager.register(CacheManager.CACHE_ORG_COMPANY,instance);
        }
        return instance;
    }
}