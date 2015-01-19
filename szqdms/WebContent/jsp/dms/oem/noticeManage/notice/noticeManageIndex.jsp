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
<title>通告类别维护</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/noticeManage/NoticeManageAction/noticeManageSearch.ajax";
var deleteUrl = "<%=request.getContextPath()%>/service/noticeManage/NoticeManageAction/noticeManagedelete.ajax";
//查询按钮响应方法
$(function(){
	//查询方法
	$("#btn-search").bind("click",function(event){
		var $f = $("#noticeManageform");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"noticeManagelb");
	});
	$("#btn-add").bind("click",function(event){
		var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps+"/jsp/dms/oem/noticeManage/notice/noticeManageEdit.jsp?action=1", "noticeManage", "通知通告类别新增", options,true); 
	});
});
function showAnNiu(obj){
	var $tr = $(obj).parent();
	var str=$tr.find("td").eq(6).text();
	var flag = false;
		if(str!=null&&str.length>0){
			strs = str.split(',');
			if(strs.length>0){
				for(var i=0;i<strs.length ;i++ ){
					var user = strs[i]; 
				    if(user == <%=userId%>){
				    	flag= true;
				    }
			    }
			    if(flag==true){
			    	obj.html("<A class=op title=[修改] onclick='doUpdate(this.parentElement.parentElement.parentElement)' href='javascript:void(0);'>[修改]</A><A class=op title=[删除] onclick='doDelete(this.parentElement.parentElement.parentElement)' href='javascript:void(0);'>[删除]</A>");
			    }else{
			    	obj.html("<A class=op   href='javascript:void(0);'></A>");
			    }
		    }
	}else{
		obj.html("<A class=op title=[修改] onclick='doUpdate(this.parentElement.parentElement.parentElement)' href='javascript:void(0);'>[修改]</A><A class=op title=[删除] onclick='doDelete(this.parentElement.parentElement.parentElement)' href='javascript:void(0);'>[删除]</A>");
	}
}
function doDelete(rowobj)
{
	$row = $(rowobj);
	var url = deleteUrl + "?typeId="+$(rowobj).attr("TYPE_ID")+"";
	sendPost(url,"doDelete","",deleteCallBack,"true");
}
function doUpdate(rowobj)
{
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/noticeManage/notice/noticeManageEdit.jsp?action=2", "noticeManage", "通知通告类别修改", options,true);
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
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：通知通告管理&gt;通知通告管理&gt;通告类别维护</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="noticeManageform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="noticeManageTable">
						<tr>
							<td><label>类别名称：</label></td>
							<td><input type="text" id="typeName" name="typeName" datasource="TYPE_NAME" datatype="1,is_null,100" value=""  operation="like" /></td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-add">新&nbsp;&nbsp;增</button></div></div></li>
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
							<th fieldname="TYPE_ID" style="display:none">类别ID</th>
							<th fieldname="TYPE_NAME" >类别名称</th>
							<th fieldname="ALL_NAME" >操作人员</th>
							<th fieldname="TYPE_STATUS" >状态</th>
							<th fieldname="ALL_ID"style="display:none" >操作人员ID</th>
							<th colwidth="85"  refer="showAnNiu"  >操作</th>
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