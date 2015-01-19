package com.org.framework.common;

import java.util.Date;

/**
 * @Description:User对象vo
 * @Copyright: Copyright (c) 2011
 * @Date: 2011-10-7
 * @mail   andy.ten@tom.com
 */
public interface User
{
    public static final String COMMON_USER = "100401";
    public static final String SUPER_USER = "100403";
    public static final String ADMIN_USER = "100402";

    public String getUserId();                 //获取帐户id
    public  void  setUserId(String userId);   //设置帐户id
    
    public String getAccount();                 //获取帐户名称
    public  void  setAccount(String account);   //设置帐户名称

    public String getPassWord();                //获取帐户密码
    public  void  setPassWord(String password); //设置账户密码

    public String getPersonName();                     //获取用户名称
    public  void  setPersonName(String personname);          //设置用户名称
    
    public String getPersonNameEn();                     //获取用户名称
    public  void  setPersonNameEn(String personname);          //设置用户名称
    
    public String getCompanyId();               //获取公司编号
    public void   setCompanyId(String company);  //设置公司编号
    
    public String getOrgCode();               //获取部门编号
    public void   setOrgCode(String orgCode);  //设置部门编号
    
    public String getOrgId();               //获取部门ID
    public void   setOrgId(String orgId);  //设置部门ID
    
    public String getOrgParentId();               //获取上级部门ID
    public void   setOrgParentId(String pId);  //设置上级部门ID
    
    public String getPersonKind();  //获取用户类别1、超级用户2、管理员3、普通用户4、受限用户、5过期无效用户
    public void   setPersonKind(String personkind);  //设置用户类型
    
    public String getPersonType();  //获取用户类型1、用户类型:1主车厂端2渠道端3供应商端4其他
    public void   setPersonType(String persontype);  //设置用户类型

    public String getScretLevel();                  //获取密级级别
    public void   setScretLevel(String scretlevel); //设置密级级别

    public String getIdCard();                      //获取身份证号
    public void setIdCard(String idCard);           //设置身份证号

    public String getContactWay();                   
    public void setContactWay(String contactway);   
    
    public String getSex();
    public void setSex(String sex);
    
    public String getStatus();                 //获取有效标识
    public  void  setStatus(String status);   //设置有效标识
    
    public Role[] getRoles();
    public void setRoles(Role[] roles);

    public String getRoleListString();
    public Menu[] getAllowedMenus();
    public Menu[] getAllowedMenus(String parent);
    public void setAllowedMenus(Menu[] allowedMenus);
    public OrgDept getOrgDept();
    
    public String getOemCompanyId();		//设置oem公司id
    public void setOemCompanyId(String companyId);
    
    public String getLoginIP();
    public void setLoginIP(String ip); //设置登录ip
    public String getLoginLogID();
    public void setLoginLogID(String id);
    public Date getLoginTime();
    public void setLoginTime(Date datetime);
    public String getUserAuth();
    public void setUserAuth(String userAuth);
}
