<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.org.frameImpl.Constant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
	 Title:用户管理
	 Version:1.0
     Collator：andy.ten@tom.com
     Date：2012-01
     Remark：You can copy and/or use and/or modify this program free,
             but please reserve the segment above.
             Please mail me if you have any question.
-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<script src="<%=request.getContextPath()%>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<title>用户管理</title>
<script type="text/javascript">
/**
 * 查询提交方法,方式为：sysmng/usermsg/UserMngAction/search.ajax
 * 其中，sysmng/usermsg/为类路径（去掉com.dms.actions）
 * UserMngAction/为提交到后台的action类名
 * search为提交请求类中需要执行的方法名
 * .ajax表示请求为ajax请求
 */
var searchUrl = "<%=request.getContextPath()%>/sysmng/usermng/UserMngAction/search.ajax";
var syncUrl = "<%=request.getContextPath()%>/sysmng/usermng/UserMngAction/synchronize.ajax";
var deleteUrl = "<%=request.getContextPath()%>/sysmng/usermng/UserMngAction/delete.ajax";
//定义弹出窗口样式
var diaAddOptions = {max:false,width:720,height:430,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//初始化
$(function()
{
	//查看附件
	$("#btn-viewFj").click(function(){
			var ywzj = "123";
			$.filestore.view(ywzj);
	});
	//添加附件
	$("#btn-addAtt").click(function(){
		var ywzj = "123";
		/**
		 * uploadLimit:10,     //设置一次最多上传文件数量
		 * fileSizeLimit:5000,    //设置文件大小限制（单位为KB），0为不限制文件大小
		 * fileTypeDesc:'All Files',   //设置文件格式描述，如是图片，则：'*.jpg;*.jpeg;*.gif;*.png;'
		 * fileTypeExts: '*.*',    //设置文件格式，如是图片，则：'*.jpg;*.jpeg;*.gif;*.png;',
		 * multi:true,  //设置是否允许多文件批量上传,
		 * successTimeout  : 300, //上传超时
		 * holdName://是否保留原文件名，如果保留，则覆盖已上传的同名文件。默认为"false".
		 * folder://是否使用文件夹方式保留文件,如使用，则按日期命名文件夹；默认为"true";
		 */
		$.filestore.open(ywzj,{"folder":"true","holdName":"false","fileSizeLimit":0,"fileTypeDesc":"*.jpg;*.jpeg;*.gif;*.png;","fileTypeExts":"*.*"});
	});
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
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
	//新增按钮响应
	$("#btn-add").bind("click", function(event){
		$.pdialog.open(webApps + "/jsp/jQFrameWork/sysmng/usermng/userEdit.jsp?action=1", "editUser", "新增用户", diaAddOptions);
	});
});
//列表编辑连接
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/jQFrameWork/sysmng/usermng/userEdit.jsp?action=2", "editUser", "修改用户", diaAddOptions);
}

var $row;
//删除方法，rowobj：行对象，非jquery类型
function doDelete(rowobj)
{
	$row = $(rowobj);
	var url = deleteUrl + "?userId="+$(rowobj).attr("USER_ID")+"&account="+$(rowobj).attr("ACCOUNT");
	/**
	 * sendPost:提交一般请求方法
	 * @url:提交请求url路径
	 * @"delete":提交请求句柄id
	 * @"":提交请求参数，可为空（json格式）
	 * @deleteCallBack：提交请求后台执行成功后，页面回调函数
	 * @"true":是否需要confirm确认对话框，"true"为弹出确认框
	 */
	sendPost(url,"delete","",deleteCallBack,"true");
}
//删除回调方法
function  deleteCallBack(res)
{
	try
	{
		if($row) 
			$("#tab-userlist").removeResult($row);
		//同步集群节点（业务功能不需要）
		var sUrl = syncUrl + "?type=3&account="+$row.attr("ACCOUNT");
		sendPost(sUrl,"","","","false");
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
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 系统管理  &gt; 用户管理   &gt; 用户管理</h4>
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
					    <td><input type="text" id="account" name="account" datasource="U.ACCOUNT" datatype="1,is_null,30" operation="like" /></td>
					    <td><label>所属组织：</label></td>
					    <td><input type="text" id="orgCode"  name="orgCode" datasource="U.ORG_ID" dicwidth="300"  kind="dic" src="ZZJG" datatype="1,is_null,30" operation="like" />
					    </td>
					</tr>
					<tr>
						<td><label>姓名：</label></td>
					    <td><input type="text" id="personName" name="personName" datasource="U.PERSON_NAME" operation="like"  datatype="1,is_null,10" /></td>
					    <td><label >工号：</label></td>
					    <td><input type="text" id="userSn" name="userSn" datasource="U.USER_SN" operation="like" datatype="1,is_null,30" /></td>
					</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-add" >新&nbsp;&nbsp;增</button></div></div></li>
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
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="ACCOUNT" >用户账号</th>
							<th fieldname="PERSON_NAME" >姓名</th>
							<th fieldname="SEX" >性别</th>
							<th fieldname="CONTACT_WAY" >联系方式</th>
							<th fieldname="MAILNAME" >邮件地址</th>
							<th fieldname="PERSON_TYPE" >用户级别</th>
							<th fieldname="ORG_NAME" >所属组织</th>
							<th fieldname="PERSON_KIND" >用户类型</th>
							<th fieldname="STATUS" >有效标识</th>
							<th colwidth="85" type="link" title="[编辑]|[删除]"  action="doUpdate|doDelete" >操作</th>
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