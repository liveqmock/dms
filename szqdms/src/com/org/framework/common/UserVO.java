package com.org.framework.common;

import java.util.Date;

import com.org.frameImpl.Constant;
import com.org.framework.component.orgmanage.*;
import com.org.framework.util.*;

import java.util.ArrayList;

/**
 * @Copyright: Copyright (c) 2011
 * @Date: 2011-10-7
 * @mail   andy.ten@tom.com
 */
public class UserVO
    implements java.io.Serializable, User
{
	private static final long serialVersionUID = -6674691703945104909L;
	public OrgDept getOrgDept()
    {
        return OrgDeptManager.getInstance().getDeptByID(this.getOrgId());
    }

    private Role[] roles;
    public Role[] getRoles()
    {
        if (roles == null)
        {
            roles = OrgRoleManager.getInstance().getUserRoles(this.getUserId());
        }
        return roles;
    }

    public String getRoleListString()
    {
        if (roles == null)
            roles = this.getRoles();
        String role = "";
        if (roles != null)
            for (int i = 0; i < roles.length; i++)
            {
                if (i > 0)
                    role += ",";
                role += "'" + roles[i].getCode() + "'";
            }
        return role;
    }

    private Menu[] menus;
    public Menu[] getAllowedMenus()
    {
        if (menus == null)
        {
            menus = MenuManager.getInstance().getAllowedMenus(this);
        }
        return menus;
    }
    
    public Menu[] getAllowedMenus(String parent)
    {
        try
        {
            ArrayList<Menu> ml = null;
            if (menus == null)
                this.getAllowedMenus();
            if (menus != null)
            {
                ml = new ArrayList<Menu>();
                for (int j = 0; j < menus.length; j++)
                {
                    if (menus[j] == null)
                    {
                        System.out.println(j);
                    }
                    if (menus[j] != null && Pub.empty(menus[j].getParent()) && Pub.empty(parent)) 
                    	ml.add(menus[j]);
                    else if(menus[j] != null && !Pub.empty(parent) && parent.equals(menus[j].getParent()))
                        ml.add(menus[j]);
                }
                return (Menu[]) ml.toArray(new Menu[ml.size()]);
            }
            else
                return null;
        }
        catch (Exception e)
        {
            e.printStackTrace(System.out);
            return null;
        }
    }
    
    private String userId;
    private String account;
    private String password;
    private String personName;
    private String personNameEn;
    private String sex;
    private String orgId;
    private String orgParentId;
    private String orgCode;
    private String personKind = "3";
    private String scretLevel = "1";
    private String status = Constant.YXBS_01;
    private String loginIp;
    private String loginLogID;
    private Date   loginTime;
    private String idcard;
    private String companyId;
    private String contactWay;
    private String personType;
    private String oemCompanyId;
    private String userAuth;
    public String getUserAuth()
	{
		return userAuth;
	}

	public void setUserAuth(String ua)
	{
		this.userAuth = ua;
	}
    public Menu[] getMenus()
	{
		return menus;
	}

	public void setMenus(Menu[] menus)
	{
		this.menus = menus;
	}

	public String getAccount()
	{
		return account;
	}

	public void setAccount(String account)
	{
		this.account = account;
	}

	public String getPersonName()
	{
		return personName;
	}

	public void setPersonName(String personName)
	{
		this.personName = personName;
	}

	public String getSex()
	{
		return sex;
	}

	public void setSex(String sex)
	{
		this.sex = sex;
	}

	public String getPersonKind()
	{
		return personKind;
	}

	public void setPersonKind(String personKind)
	{
		this.personKind = personKind;
	}

	public String getScretLevel()
	{
		return scretLevel;
	}

	public void setScretLevel(String scretLevel)
	{
		this.scretLevel = scretLevel;
	}


	public String getLoginLogID()
	{
		return loginLogID;
	}

	public void setLoginLogID(String loginLogID)
	{
		this.loginLogID = loginLogID;
	}

	public Date getLoginTime()
	{
		return loginTime;
	}

	public void setLoginTime(Date loginTime)
	{
		this.loginTime = loginTime;
	}

	public String getCompanyId()
	{
		return companyId;
	}

	public void setCompanyId(String companyId)
	{
		this.companyId = companyId;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	public UserVO()
    {

    }

    public void setRoles(Role[] roles)
    {
        this.roles = roles;
    }

    public void setAllowedMenus(Menu[] allowedMenus)
    {
        this.menus = allowedMenus;
    }

	public String getContactWay()
    {
	    return contactWay;
    }

	public void setContactWay(String contactway)
    {
	    contactWay = contactway;
    }


	@Override
	public String getUserId()
	{
		// TODO Auto-generated method stub
		return userId;
	}

	@Override
	public void setUserId(String suserId)
	{
		// TODO Auto-generated method stub
		userId = suserId;
	}

	@Override
	public String getOrgCode()
	{
		// TODO Auto-generated method stub
		return orgCode;
	}

	@Override
	public void setOrgCode(String sorgCode)
	{
		// TODO Auto-generated method stub
		orgCode = sorgCode;
	}
	
	@Override
	public String getOrgParentId()
	{
		// TODO Auto-generated method stub
		return orgParentId;
	}

	@Override
	public void setOrgParentId(String pId)
	{
		// TODO Auto-generated method stub
		orgParentId = pId;
	}

	@Override
	public String getPersonType()
	{
		// TODO Auto-generated method stub
		return personType;
	}

	@Override
	public void setPersonType(String spersontype)
	{
		// TODO Auto-generated method stub
		personType = spersontype;
	}

	public String getStatus()
	{
		// TODO Auto-generated method stub
		return status;
	}

	public void setStatus(String sstatus)
	{
		// TODO Auto-generated method stub
		status = sstatus;
	}

	public String getPersonNameEn()
	{
		// TODO Auto-generated method stub
		return personNameEn;
	}

	public void setPersonNameEn(String spersonname)
	{
		// TODO Auto-generated method stub
		personNameEn = spersonname;
	}

	public String getIdCard()
	{
		// TODO Auto-generated method stub
		return idcard;
	}

	public void setIdCard(String sidCard)
	{
		// TODO Auto-generated method stub
		idcard = sidCard;
	}

	@Override
	public String getPassWord()
	{
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public void setPassWord(String spassword)
	{
		// TODO Auto-generated method stub
		password = spassword;
	}

	@Override
	public String getLoginIP()
	{
		// TODO Auto-generated method stub
		return loginIp;
	}

	@Override
	public void setLoginIP(String sip)
	{
		// TODO Auto-generated method stub
		loginIp = sip;
	}

	@Override
	public String getOemCompanyId() {
		// TODO Auto-generated method stub
		return oemCompanyId;
	}

	@Override
	public void setOemCompanyId(String sOmeCompanyId) {
		// TODO Auto-generated method stub
		oemCompanyId = sOmeCompanyId;
	}

	@Override
	public void setOrgId(String sOrgId) {
		// TODO Auto-generated method stub
		orgId = sOrgId;
	}
	@Override
	public String getOrgId()
	{
		// TODO Auto-generated method stub
		return orgId;
	}
}
