<!DOCTYPE html PUBLIC “-//W3C//DTD XHTML 1.0 Transitional//EN” “http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd“>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.org.framework.Globals"%>
<%
	String path = "";
    String userName = "";
    String loginStyle = "";
    String sysLanguage = "1";
    try
    {
    	Cookie[] cList=request.getCookies();
    	for(int i=0;i<cList.length;i++)
    	{
      		if("loginID".equals(cList[i].getName()))
      		{
        		userName=cList[i].getValue();
      		}
      		if("loginStyle".equals(cList[i].getName()))
      		{
        		loginStyle=cList[i].getValue();
      		}
      		if("sysLanguage".equals(cList[i].getName()))
      		{
      			sysLanguage = cList[i].getValue();
      		}
    	}
    	if(loginStyle==null||"".equals(loginStyle))
    	{
       		loginStyle = "default";
    	}
  	}catch(Exception exp)
  	{
  		
  	}
 %>
 
<html>
<!-- 
     Collator：andy.ten@tom.com
     Date：2011-10
     Remark：You can copy and/or use and/or modify this program free,
             but please reserve the segment above.
             Please mail me if you have any question.
-->
<head>
<title>汽车行业DMS平台</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/login.css"/>
<script src="http://pv.sohu.com/cityjson" type="text/javascript" ></script>
<script src="http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js" type="text/javascript" ></script>
<script type="text/javascript">
function doInit(){
	  document.getElementById("outerIp").value=returnCitySN.cip;
	  document.getElementById("outerArea").value=remote_ip_info.province+"-"+remote_ip_info.city+"-"+remote_ip_info.isp;
	  usrObj=document.getElementById("username");
	  passObj=document.getElementById("password");
	  if(usrObj!=null)
	  {
	    if(usrObj.value==null||usrObj.value=="")
	    {
	      	usrObj.focus();
	    }else
	    {
	      if(passObj!=null)
	      {
	      	passObj.focus();
	      }
	    }
	  }
	  /**
	  var loginStyle = document.all("loginStyle");
	  for(var  i=0;i<loginStyle.length;i++)
	  {
		  if(loginStyle[i].value=="<%=loginStyle%>")
		  {
	      	 loginStyle[i].selected=true;
	         break;
	      }
	  }
	  */
}
function login()
{
  document.loginForm.submit();
}
function dologin()
{
  if(event.keyCode==13)
  { 
    try 
    {
      //document.getElementById("img-submit").focus();//登录
      login();
    }
    catch(e){}
  }
}
function removeValue()
{
   usrObj.value="";
   passObj.value="";
}
function enterToTab(action)
{
  if(event.keyCode==13)
  { //'回车'转化为'TAB'
	  switch(action)
	  {
	  	case 1:
	  			document.getElementById("password").focus();
	  		break;
	  	case 2:
	  			document.getElementById("vercode").focus();
	  		break;
	  }
    event.keyCode=9;
  }
}

/**
 * 使用密钥登录
 */
function loginSys()
{
	window.location='<%=path%>';
}

</script>
</head>
<body onload="doInit();">
<div class="wrapper" id="wrapper">
	<div class="loginTop">
        <h1><img src="images/login/loginLogo.png" alt=""></h1>
    </div>
    <div class="loginMain" id="loginMain">
        <div class="loginHeader" style=""><h2><span></span></h2></div>
        <div class="loginMainLeftImg" id="loginMainLeftImg"><img  style="margin-left:18px;width:640px;height:340px;" src="images/login/loginImg.png" alt="img" ></img></div>
        <div class="loginMainRightFrom" id="loginMainRightForm">
            <form  name="loginForm" action="<%=request.getContextPath() %>/LoginAction/login.do" method="post">
	            <table width="100%" class="loginFormTable" id="loginFormTable" border="0" cellspacing="0" cellpadding="0">
	                <tr>
	                    <th align="left">用户名</th>
	                    <td><input onkeydown="enterToTab(1)" class="loginText" name="username" id="username" value="<% if (userName!=null)out.println(userName);%>"></td>
	                </tr>
	                <tr>
	                    <th align="left">密　码</th>
	                    <td><input onkeydown="enterToTab(2)" class="loginPassword" type="password" name="password" id="password"></td>
	                </tr>
	                <tr>
	                    <th align="left">验证码</th>
	                    <td>
	                    	<input type="text" onkeydown="dologin()" class="loginText2" name="vercode"id="vercode" />
							<img src="jsp/jQFrameWork/validatecode/image.jsp" title="看不清楚?点击刷新！" id="vcode" alt="点击刷新！" style="cursor: pointer;width:50px;height:20px;float:left" onclick="vcode.src='jsp/jQFrameWork/validatecode/image.jsp?'+Math.random()" />
	                    </td>
	                </tr>
	                <tr style="display:none;">
	                    <th align="left">语　种</th>
	                    <td>
	                        <select id="zyw" name="zyw" value="1" class="loginSelect">
			  					<%
			  						if(sysLanguage.equals("1")){
			  					%>
			  						<option value="1" selected>中文</option>
			  						<option value="2">英文</option>
			  					<%
			  						}else{
			  					%>
			  						<option value="1" >中文</option>
			  						<option value="2" selected>英文</option>
			  					<%	
			  						}
			  					%>
			  				</select>
	                    </td>
	                </tr>
	                <tr>
	                    <th align="left"></th>
	                    <td><input class="loginBtn" type="button" value="登  录" onclick="login();"/></td>
	                </tr>
	            </table>
	            	<input type="hidden" size="16" name="method" id="method" value="login">
	            	<input type="hidden" id="outerIp" name="outerIp" />
	            	<input type="hidden" id="outerArea" name="outerArea" />
				</form>
        </div>
        <div id="loginFooter">Copyright ©2014 SQ All Rights Reserved 1.01版权所有·陕西重型汽车有限公司<br /> 
							建议使用1024*768以上的屏幕分辨率
		</div>
	</div>
</div>
<script type="text/javascript">
</script>
</body>
</html>
