package com.org.frameImpl.action;

import javax.servlet.http.*;

import java.util.*;

import com.org.framework.util.Encipher;
import com.org.framework.util.Pub;
import com.org.framework.component.orgmanage.OrgDeptManager;
import com.org.framework.component.orgmanage.UserManager;
import com.org.framework.Globals;

import org.apache.log4j.Logger;

import com.org.framework.log.LogManager;
import com.org.framework.common.OrgDept;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;
import com.org.mvc.context.SessionWrapper;

/**
 * @Description:系统登录类
 * @Copyright: Copyright (c) 2011
 * @Date: 2011-10-7
 * @mail   andy.ten@tom.com
 */
public class LoginAction 
{
    private Logger logger = com.org.framework.log.log.getLogger("LoginAction");
    private ActionContext atx = ActionContext.getContext();
    public String getIpAddr(HttpServletRequest request) 
    {  
        String ip = request.getHeader("x-forwarded-for");  
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) 
        {  
           ip = request.getHeader("Proxy-Client-IP");  
        }  
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) 
        {  
           ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) 
        {  
            ip = request.getRemoteAddr();  
        }
        if(ip.indexOf(",")>0)
        	ip = ip.split(",")[0];
        return ip;  
  }  
    public String login()
    {
        User user = null;
        try
        {
        	RequestWrapper request = atx.getRequest();
        	ResponseWrapper response = atx.getResponse();
        	SessionWrapper session = atx.getSession();
        	String userName = Pub.val(request, "username");
        	String password = Pub.val(request, "password");
        	String outerIp = Pub.val(request, "outerIp");
        	String outerArea = Pub.val(request, "outerArea");
            String serialNumber = Pub.val(request, "certcode");
            String loginStyle = Pub.val(request, "loginStyle");
            if("".equals(loginStyle)) loginStyle = "default";
            String classid = Pub.val(request, "classid");
            //中英文
            String zyw = Pub.val(request, "zyw");
            if("".equals(zyw)) zyw = "1";
            atx.getSession().set(Globals.SYS_LANGUAGE, zyw);
            Cookie sysLanguage = new Cookie("sysLanguage", zyw);
            sysLanguage.setMaxAge(365 * 60 * 60);
            sysLanguage.setPath("/");
    		atx.getResponse().addCookie(sysLanguage);
            //图片校验码
            String sessCode = Pub.val(request, "vercode");
            Object sessCode2 = atx.getSession().get("sess_code");
            if(sessCode2 == null) sessCode2 = "";
/*            if(!sessCode.equals(sessCode2))
            {
            	request.setAttribute("flag", "loginError");
            	if("1".equals(zyw))
            		throw new Exception("验证码不正确!");
            	else
            		throw new Exception("The verification code is incorrect !");
            }
*/
            if(Pub.empty(serialNumber))
            {
                userName = userName == null ? "" : userName.trim();
                user = UserManager.getInstance().getUserByAccount(userName.toUpperCase());
                //判断数据库中是否有记录，为解决数据库有记录，内存中没有问题
                if(user == null)
                {
                	user = UserManager.getInstance().loadUserByAccount(userName.toUpperCase());
                	//加入到内存中
                	if(user != null)
                	{
                		OrgDept orgDept = OrgDeptManager.getInstance().getDeptByID(user.getOrgId());
                		if(orgDept == null)
                			throw new Exception("用户所属组织不存在!");
                		UserManager.getInstance().getUserTable().put(userName.toUpperCase(), user);
                	}
                }
            }
            if (user == null)
            {
                request.setAttribute("flag", "loginError");
                if("1".equals(zyw))
                	throw new Exception("用户["+userName+"]不存在!");
                else
                	throw new Exception("The user does not exist !");
            }
            Date dateTime = Calendar.getInstance().getTime();
            user.setLoginTime(dateTime);
            user.setLoginIP(getIpAddr(request.getHttpRequest())); 
            String pass1 = user.getPassWord();
            if (pass1 == null)
                pass1 = "";
            if (password == null)
                password = "";
/*            if (!pass1.equals(Encipher.MD5Encryption(password))&&"".equals(classid))//add 是否pad登录验证，如果是pad登录系统密码不做校验
            {
                request.setAttribute("flag", "loginError");
                //request.setAttribute(Globals.KEY_ERROR_MESSAGE,"密码不正确!");
                //return mapping.findForward("error");
                if("1".equals(zyw))
                	throw new Exception(userName+"：密码不正确!");
                else
                	throw new Exception("The password is incorrect !");
            }*/
            //验证内外网
            String userAuth = user.getUserAuth();
            if(userAuth == null || "".equals(userAuth)) userAuth = "2";//2：不限制;0:内网;1:外网
			if("0".equals(userAuth))
			{
				if("10.0.0.1".equals(getIpAddr(request.getHttpRequest())))
				{
					atx.setForword("index");
					return "OK";
				}
			}else if("1".equals(userAuth))
			{
			}
			//判断密码是否是初始密码，如果是初始密码，则跳转到修改密码页
//            if(Encipher.MD5Encryption(password).equals(Encipher.MD5Encryption("000000")))
//            {
//            	session.set(Globals.USER_KEY, user);
//            	atx.setForword("setPwd");
//            	return "OK";
//            }
            //登陆样式用户名放入cookie begin
            Cookie cLogin = new Cookie("loginStyle", loginStyle);
            cLogin.setMaxAge(365 * 60 * 60);
            cLogin.setPath("/");
            response.addCookie(cLogin);
            Cookie loginID = new Cookie("loginID", userName);
            loginID.setMaxAge(365 * 60 * 60);
            loginID.setPath("/");
            response.addCookie(loginID);

            //登陆样式用户名放入cookie end
            logger.info("@user [" + user.getAccount() + "] login success...");
            session.set("LOGINSTYLE", loginStyle);
            session.set(Globals.USER_KEY, user);
            //session.set(Globals.DEPT_KEY,user.getOrgDept());
            session.set("CLASSID", classid);
            LogManager.writeLoginLog(user, LogManager.LOGIN_STATUS_SUCCESS, outerIp, outerArea, "");
            atx.setForword("login");
        }
        catch (Exception e)
        {
            //if (user != null)
                //LogManager.writeLoginLog(user, LogManager.LOGIN_STATUS_FAILURE);
             logger.error(e.toString());
             atx.setException(e);
             atx.setOutData(Globals.KEY_ERROR_MESSAGE, e.getMessage());
             atx.setForword("error");
        }
        return "OK";
    }
    
    public void loginDialog()
    		throws Exception
	{
		User user = null;
		RequestWrapper request = atx.getRequest();
		ResponseWrapper response = atx.getResponse();
    	SessionWrapper session = atx.getSession();
		try
		{
			String serialNumber = Pub.val(request, "certcode");
			String userName = Pub.val(request, "username");
			String password = Pub.val(request, "password");
			String loginStyle = Pub.val(request, "loginStyle");
			if("".equals(loginStyle)) loginStyle = "default";
			String classid = Pub.val(request, "classid");
			String zyw = Pub.val(request, "zyw");
	        if("".equals(zyw)) zyw = "1";
	        request.getSession().set(Globals.SYS_LANGUAGE, zyw);
			if(Pub.empty(serialNumber))
			{ 
				userName = userName == null ? "" : userName.trim();
                user = UserManager.getInstance().loadUserByAccount(userName.toUpperCase());
                //判断数据库中是否有记录，为解决数据库有记录，内存中没有问题
                if(user == null)
                {
                	user = UserManager.getInstance().loadUserByAccount(userName.toUpperCase());
                	//加入到内存中
                	if(user != null)
                	{
                		OrgDept orgDept = OrgDeptManager.getInstance().getDeptByID(user.getOrgId());
                		if(orgDept == null)
                			throw new Exception("用户所属组织不存在!");
                		UserManager.getInstance().getUserTable().put(userName.toUpperCase(), user);
                	}
                }
			}
			
			if (user == null)
			{
				request.setAttribute("flag", "loginError");
                if("1".equals(zyw))
                	throw new Exception("用户不存在!");
                else
                	throw new Exception("The user does not exist !");
			}
			Date dateTime = Calendar.getInstance().getTime();
            user.setLoginTime(dateTime);
            user.setLoginIP(getIpAddr(request.getHttpRequest()));
            String pass1 = user.getPassWord();
            if (pass1 == null)
                pass1 = "";
            if (password == null)
                password = "";
            if (!pass1.equals(Encipher.MD5Encryption(password))&&"".equals(classid))//add 是否pad登录验证，如果是pad登录系统密码不做校验
            {
                request.setAttribute("flag", "loginError");
                //request.setAttribute(Globals.KEY_ERROR_MESSAGE,"密码不正确!");
                //return mapping.findForward("error");
                if("1".equals(zyw))
                	throw new Exception("密码不正确!");
                else
                	throw new Exception("The password is incorrect !");
            }
			//登陆样式用户名放入cookie begin
			Cookie loginID = new Cookie("loginID", userName);
			loginID.setMaxAge(365 * 60 * 60);
			loginID.setPath("/");
			response.addCookie(loginID);
            //登陆样式用户名放入cookie end
            logger.info("@user [" + user.getAccount() + "] login success...");
            LogManager.writeLoginLog(user, LogManager.LOGIN_STATUS_SUCCESS);
            session.set("LOGINSTYLE", loginStyle);
            session.set(Globals.USER_KEY, user);
            //session.set(Globals.DEPT_KEY,user.getOrgDept());
            session.set("CLASSID", classid);
            
            atx.setForword("login");
		}
		catch (Exception e)
		{
			logger.error(e.toString());
            atx.setException(e);
            atx.setOutData(Globals.KEY_ERROR_MESSAGE, e.getMessage());
            atx.setForword("error");
		}
	}
}