<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%@page import="com.org.frameImpl.Constant"%>
<%@page import="com.org.dms.common.DicConstant"%>
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String userId = user.getUserId();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>通知通告查询</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/noticeManage/NoticeManageAction/noticeSearch.ajax";
//查询按钮响应方法
$(function(){
	//查询方法
	$("#btn-search").bind("click",function(event){
		var $f = $("#noticeManageform");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"noticeManagelb");
	});
});
function doUpdate(rowobj)
{
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/noticeManage/notice/noticeArchiveEdit.jsp?action=2", "noticeManage", "通知通告归档", options,true);
}
//删除回调方法
function  deleteCallBack(res)
{
	try
	{
		if($row) 
			$("#noticeManagelb").removeResult($row);
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：通知通告管理&gt;通知通告管理&gt;通告查询</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="noticeManageform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="noticeManageTable">
						<tr>
							<td><label>通告类别：</label></td>			
						 	<td id="test">
								<select  type="text" id="typeName" name="typeName" datasource="MT.TYPE_ID" kind="dic" class="combox" src="T#MAIN_BULLETIN_TYPE A, MAIN_BULLETIN_PERMISSION B:A.TYPE_ID:A.TYPE_NAME:1=1 AND A.TYPE_ID = B.TYPE_ID AND A.STATUS=<%=DicConstant.YXBS_01 %> AND USER_ID=<%=userId%>"  datatype="1,is_null,8" value="" >
								<option value=-1>全部</option>
								</select>
							</td>
							<td><label>签收状态：</label></td>			
						 	<td id="test">
								<select  type="text" id="typeName" name="typeName" datasource="MR.STATUS" kind="dic" class="combox" src="TGQSZT"   value=""  >
								<option value=-1>--</option> 
								</select>
							</td>
							<td><label>通告标题：</label></td>
							<td><input type="text" id="title" name="title" datasource="TITLE" datatype="1,is_null,100" value=""  operation="like" /></td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
		<div id="noticeManage" >
			<table style="display:none;width:100%;" layoutH="250" id="noticeManagelb" name="noticeManage" ref="noticeManage" refQuery="noticeManageTable" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="ORG_CODE" >渠道商代码</th>
							<th fieldname="ORG_NAME" >渠道商名称</th>
							<th fieldname="TYPE_NAME" >通告类别</th>
							<th fieldname="BULLETIN_ID" style="display:none">通告内容ID</th>
							<th fieldname="TITLE" >通告标题</th>
							<th fieldname="CONTENT" >通告内容</th>
							<th fieldname="BULLETIN_STATUS" >通告状态</th>
							<th fieldname="SIGN_STATUS" >通告签收状态</th>
							<!-- <th colwidth="140" type="link"  title="[明细]" action="doUpdate">操作</th> -->
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
</body>
</html>