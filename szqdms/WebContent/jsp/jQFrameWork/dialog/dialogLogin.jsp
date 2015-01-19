<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%
	// 添加用户切换功能
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String userAccount = user.getAccount();
	
	// classId是LoginAction中用来判断是否校验密码的，如果classId不为空，则不校验密码
	String classId = String.valueOf(request.getSession().getAttribute("CLASSID"));
	if(classId == null) classId = "";
	String contextPath = request.getContextPath();
%>
<script src="<%=contextPath %>/lib/plugins/HashMap.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/plugins/mine.rightmenu.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/plugins/mine.info.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/plugins/validate/validate.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/plugins/mine.win.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/plugins/mine.dic.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/plugins/default.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/plugins/mine.table.js" type="text/javascript"></script>
<%
	// 如果登录账户不包含“admin”或者登录session中"CLASSID"属性值为空或空字符则进入原始切换用户界面（需要输入用户名密码）
	if(userAccount.toUpperCase().indexOf("ADMIN") == -1 && "".equals(classId)){
%>
	<div class="pageContent">
		<form name="loginForm" method="post" action="<%=request.getContextPath() %>/LoginAction/loginDialog.do?flag=0" class="pageForm" >
			<div class="pageFormContent" layoutH="58">
				<div class="unit">
					<label>用户名：</label>
					<input type="text" name="username" size="30" class="required"/>
				</div>
				<div class="unit">
					<label>密&nbsp;&nbsp;&nbsp;码：</label>
					<input type="password" name="password" size="30" class="required"/>
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
				</ul>
			</div>
		</form>
	</div>
<%
	}else{
		
		// 如果登录用户包含ADMIN字符串或者登录用户session的CLASSID属性值不为空时，可进入登录选择切换页面
		// 此处需要给classID属性复制，目的是用户切换后登陆账号不再为admin，这是需要使用sessin中的属性值判断来在此切换
		request.getSession().setAttribute("CLASSID", "1");
%>
		<form name="loginForm" method="post" action="<%=request.getContextPath() %>/LoginAction/loginDialog.do?flag=0" style="display: none;">
			<input type="hidden" name="classid" value="1"/>
			<input type="hidden" id="loginUserName" name="username" value="" />
			<div class="formBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit" id="doLoginButton">提交</button></div></div></li>
				</ul>
			</div>
		</form>
	
	<div class="page">
		<div class="pageHeader" >
			<!-- 提交查询请求form -->
			<form method="post" id="fm-searchUser">
				<!-- 定义隐藏域查询条件 -->
				<input type="hidden" id="stauts" name="status" datasource="U.STATUS" value="" ></input>
				<div class="searchBar" align="left" >
					<table class="searchContent" id="tab-searchUser">
					<!-- 定义查询条件 -->
					<tr><td><label>用户账号：</label></td>
					    <td>
					    	<input type="text" id="account" name="account" datasource="U.ACCOUNT" datatype="1,is_null,300" operation="like" />
					    </td>
					    <td><label>所属组织：</label></td>
					    <td>
					    	<input type="text" id="orgCode"  name="orgCode" datasource="U.ORG_ID" dicwidth="300"  kind="dic" src="ZZJG" datatype="1,is_null,300" operation="like" />
					    </td>
					    <td><label>姓名：</label></td>
					    <td>
					    	<input type="text" id="personName" name="personName" datasource="U.PERSON_NAME" operation="like"  datatype="1,is_null,100" />
					    </td>
					</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search-login" >查&nbsp;&nbsp;询</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
		<div id="page_userlist" >
			<!-- table参数说明：{ref=父级div的id（page_userlist）,refQuery=与结果列表对应的查询条件table的id} -->
			<table style="display:none;width:100%;" id="tab-userlist" name="tablist" ref="page_userlist" refQuery="tab-searchUser" limitH="true" pagerows="10">
					<thead>
						<tr>
							<th type="single" name="XH" style="display: none;"></th>
							<th fieldname="ACCOUNT" >用户账号</th>
							<th fieldname="PERSON_NAME" >姓名</th>
							<th fieldname="ORG_ID" >所属组织</th>
							<th colwidth="85" type="link" title="[登录]"  action="doLogin" >操作</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
			</table>
		</div>
		</div>
	</div>
	<script>
	$(function(){
		$("#btn-search-login").click("click", function(event){
			if($("#account").val()){
				$("#account").val($("#account").val().toUpperCase());
			}
			var searchUrl = "<%=request.getContextPath()%>/sysmng/usermng/UserMngAction/searchUser.ajax";
			var $f = $("#fm-searchUser");//获取页面提交请求的form对象
			var sCondition = {};//定义json条件对象
			//combined()：实现将页面条件按规则拼接成json
	    	sCondition = $f.combined() || {};
	    	/**
	    	 * doFormSubmit:提交查询请求
	    	 * @$f:提交form表单的jquery对象
	    	 * @searchUrl:提交请求url路径
	    	 * @"search":提交查询操作按钮id
	    	 * @1:查询结果返回时显示第几页，默认显示第一页数据
	    	 * @sCondition：页面定义的查询条件（json）
	    	 * @"yhlb":查询返回结果显示的table表格id
	    	 */
			doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-userlist");
		});
	});
	
	// 点击登录
	function doLogin(row){
		$("#loginUserName").val($(row).attr("ACCOUNT"));
		$("#doLoginButton").click();
	}
	</script>
<%
	}
%>