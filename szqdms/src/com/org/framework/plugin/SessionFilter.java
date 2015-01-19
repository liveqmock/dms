package com.org.framework.plugin;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.mvc.context.ResponseWrapper;

public class SessionFilter extends HttpServlet
  implements Filter
{
  private FilterConfig filterConfig;

  public void init(FilterConfig filterConfig)
  {
    this.filterConfig = filterConfig;
  }
  static private org.apache.log4j.Logger log = org.apache.log4j.Logger.
            getLogger("SessionFilter");
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
  {
    HttpServletRequest httprequest = (HttpServletRequest)request;
    String requestType = httprequest.getHeader("X-Requested-With");
    HttpServletResponse httpResponse = (HttpServletResponse)response;
    ResponseWrapper reps = new ResponseWrapper(httpResponse);
    HttpSession session = httprequest.getSession();
    String contextPath = httprequest.getContextPath();
    String padflag = httprequest.getParameter("padflag");
    if (padflag == null) padflag = "";
    try
    {
      String url = httprequest.getServletPath();
      if ((url.indexOf("login.do") == -1) && 
        (url.indexOf("LoginAction.do") == -1) && 
        (url.indexOf("logout.do") == -1) && 
        (url.indexOf("dialogLogin.jsp") == -1) && 
        (url.indexOf("index.jsp") == -1) && 
        (url.indexOf("error.jsp") == -1) && 
        (url.indexOf("logout.jsp") == -1) && 
        (url.indexOf("SessionInvalidate.jsp") == -1) && 
        (url.indexOf("LoginServlet") == -1) && 
        (url.indexOf("validatecode/image.jsp") == -1) && 
        (url.indexOf("FileUpload.do") == -1) && 
        (url.indexOf("CacheSynchronizeAction") == -1) && 
        (url.indexOf("WorkOrderMngAction") == -1)&& 
        (url.indexOf("mobile") == -1))
      {
        User user = (User)session.getAttribute("SESSION.USERINFO");
        if (user == null)
        {
            log.info("session invalid:"+session.getId());
          try
          {
            if ((requestType != null) && ("XMLHttpRequest".equals(requestType)))
            {
              httprequest.setAttribute("statusCode", Integer.valueOf(301));
              httprequest.setAttribute("message", "Session timeout!");
              String zyw = Pub.val(request, "zyw");
              if ("".equals(zyw)) zyw = "1";
              if ("1".equals(zyw)) {
                Pub.writeMessage(reps, "当前会话已超时，请重新登录！"); return;
              }
              Pub.writeMessage(reps, "The current session has timed out , please login again !");

              return;
            }
            ((HttpServletResponse)response).sendRedirect(contextPath + "/jsp/jQFrameWork/SessionInvalidate.jsp?padflag=" + padflag);
            return;
          }
          catch (IOException localIOException)
          {
          }

          return;
        }
        filterChain.doFilter(request, response);

         return;
      }
       filterChain.doFilter(request, response);
    }
    catch (Exception sx)
    {
       this.filterConfig.getServletContext().log(sx.toString());
       System.out.println("SessionFilter:" + sx.toString());
      try
      {
         ((HttpServletResponse)response).sendRedirect(contextPath + "/jsp/jQFrameWork/SessionInvalidate.jsp?padflag=" + padflag);
         return;
      }
      catch (IOException localIOException1)
      {
      }
    }
  }

  public void destroy()
  {
  }
}