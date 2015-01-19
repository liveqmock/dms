<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	String action = request.getParameter("action");
	if (action == null)
		action = "1";
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>通知通告编辑</title>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
var $true=true;
var action = "<%=action%>";
var diaSaveAction = "<%=request.getContextPath()%>/service/noticeManage/NoticeManageAction";
$(function()
{
	if(action != "1"){
		  var selectedRows = parent.$("#noticeManagelb").getSelectedRows();
		  setEditValue("dia-fm-noticeManage",selectedRows[0].attr("rowdata"));
	}else{
		$("#czryTab").hide();
		$("#dia-nextH1").hide();
	}
	$("#dia-save").bind("click",function(event){
		//获取需要提交的form对象
		var $f = $("#dia-fm-noticeManage");
		if (submitForm($f) == false) {
			return false;
		}
		var sCondition = {};
		if(action == 1){
			sCondition = $("#dia-fm-noticeManage").combined(1) || {};
			var addUrl = diaSaveAction + "/insert.ajax";
			doNormalSubmit($f,addUrl,"dia-save",sCondition,diaInsertCallBack);
		}else{	
			sCondition = $("#dia-fm-noticeManage").combined(1) || {};
			var addUrl = diaSaveAction + "/update.ajax";
			doNormalSubmit($f,addUrl,"dia-save",sCondition,diaInsertCallBack);
		}
	});
	//新增操作用户页面
	$("#addUser").bind("click",function(){
		var options = {max:false,width:800,height:600,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps+"/jsp/dms/oem/noticeManage/notice/noticeManageUserAdd.jsp", "noticeManageUserAdd", "新增操作用户", options);
	});
	//关闭当前页面
	$("button.close").click(function() 
	{
		parent.$.pdialog.close("noticeManage");
		return false;
	});
	$("#searchUser").bind("click",function(){
		var $f = $("#fm-noticeUser");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
    	var searchUrl =diaSaveAction+"/searchNoticeUser.ajax?&typeId="+$("#typeId").val();
		doFormSubmit($f,searchUrl,"searchUser",1,sCondition,"noticeUserlb");
	});
	//删除按钮响应方法
	$("#deleteNoticeUser").bind("click", function(event)
	{
		var mxids=$("#noticeUserVal0").val();
	    if(mxids=="")
	    {
	    	 alertMsg.warn("请选择用户！");
	    	return false;
	    }else{
	    	var scgsUrl =diaSaveAction+"/deleteNoticeUser.ajax?mxids="+mxids;
			sendPost(scgsUrl,"deleteNoticeUser","",deleteNoticeUserCallBack,"true");
	    }
	});
	//上一步
	$("button[name='btn-pre']").bind("click",function(event){
		$("#tabs").switchTab(parseInt($("#tabs").attr("currentIndex")) - 1);
	});
	//下一步
	$("button[name='btn-next']").bind("click", function(event){
		var $tabs = $("#tabs");
		switch (parseInt($tabs.attr("currentIndex"))) 
		{
			case 0:if($true){
				var $f = $("#fm-noticeUser");//获取页面提交请求的form对象
				var sCondition = {};//定义json条件对象
		    	sCondition = $f.combined() || {};
		    	var searchUrl =diaSaveAction+"/searchNoticeUser.ajax?&typeId="+$("#typeId").val();
				doFormSubmit($f,searchUrl,"searchUser",1,sCondition,"noticeUserlb");
			}
				$true=false;
				break;
		};
		$tabs.switchTab(parseInt($tabs.attr("currentIndex")) + 1);
		//跳转后实现方法
		(function(ci) 
		{
			switch (parseInt(ci)) 
			{
				case 1://第2个tab页					
					break;
				default:
					break;
			}
		})
		(parseInt($tabs.attr("currentIndex")));
	});
});
//新增回调函数
function diaInsertCallBack(res)
{
	try
	{
		var rows = res.getElementsByTagName("ROW");
		var typeId =getNodeText(rows[0].getElementsByTagName("TYPE_ID").item(0));
		parent.$("#noticeManagelb").insertResult(res,0);
		if(parent.$("#noticeManagelb_content").size()>0){
			$("td input[type=radio]",parent.$("#noticeManagelb_content").find("tr").eq(0)).attr("checked",true);			
		}else{
			$("td input[type=radio]",parent.$("#noticeManagelb").find("tr").eq(0)).attr("checked",true);
		}
		action=2;
		$("#typeId").val(typeId);
		$("#dia-report_li").show();
		$("#czryTab").show();
		$("#dia-nextH1").show();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//修改回调
function diaUpdateCallBack(res){
	try
	{	
		var selectedIndex = parent.$("#noticeManagelb").getSelectedIndex();
		parent.$("#noticeManagelb").updateResult(res,selectedIndex);
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//删除回调
function deleteNoticeUserCallBack(res){
	try
	{	
		var $f = $("#fm-noticeUser");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
    	var searchUrl =diaSaveAction+"/searchNoticeUser.ajax?&typeId="+$("#typeId").val();
		doFormSubmit($f,searchUrl,"searchUser",1,sCondition,"noticeUserlb");
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//列表复选
function doCheckbox(checkbox)
{
	var $checkbox = $(checkbox);
	var arr = [];
	while($checkbox[0].tagName != "TABLE"){
		$checkbox = $checkbox.parent();
	}
	if($checkbox.attr("id").indexOf("noticeUserlb")==0){
		var $tr = $(checkbox).parent().parent().parent();
		arr.push($tr.attr("LIMIT_ID"));
		var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
		multiSelected($checkbox,arr,$("#noticeUserVal"));
	}
	if($checkbox.attr("id").indexOf("tab-userList")==0){
		var $tr = $(checkbox).parent().parent().parent();
		arr.push($tr.attr("USER_ID"));
		arr.push($tr.attr("PERSON_NAME"));
		var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
		multiSelected($checkbox,arr,$('#userVals'));
	}
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<!-- tab页  -->
	<div class="tabs" eventType="click" id="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="javascript:void(0)"><span>通知通告类别基本信息</span></a></li>
					<li id="czryTab"><a href="javascript:void(0)"><span>操作人员信息</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" >
		<div id="layout" style="width:100%;">
			<div id='background1' class='background'></div>
				<div id='progressBar1' class='progressBar'>loading...</div>
					<div class="page">
						<div class="pageContent" style="" >
							<form method="post" id="dia-fm-noticeManage" class="editForm" style="width: 99%;">
								<div align="left">
									<fieldset>
									<legend align="right">
										<a onclick="onTitleClick('dia-tab-preAuthBasic')">&nbsp;通知通告类别基本信息编辑&gt;&gt;</a>
									</legend>
									<table class="editTable" id="sbjjxx">
									<tr>
										<td><label>类别名称：</label></td>
										<td><input type="text" id="typeName" style="width:500px;" name="typeName" datasource="TYPE_NAME" datatype="0,is_null,500" /></td>
									</tr>
								    <tr>
								    <td><label>是否有效：</label></td>
										<td><select  type="text" id="typeStatus" name="typeStatus" datasource="TYPE_STATUS" kind="dic" class="combox" src="YXBS"  datatype="1,is_null,6" value="" >
												<option value="<%=DicConstant.YXBS_01%>">有效</option>
											</select>
										</td>
										<td><input type="hidden" id="typeId" name="typeId" datasource="TYPE_ID" /></td>
									</tr>
							          </table>
									</fieldset>
								</div>
							</form>
						</div>
						<div class="formBar">
							<ul>
								<li id="save_li"><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-save">保&nbsp;&nbsp;存</button></div></div></li>
								<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
								<li id="dia-nextH1"><div class="button"><div class="buttonContent"><button type="button" id="dia-next1" name="btn-next">下一步</button></div></div></li>
							</ul>
					  </div>
				 </div>
			</div>
			<!-- 配件TAB -->
			<div class="page">
			<div class="pageHeader">
			<form id="fm-noticeUser" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="fm-noticeUserTable">
						<tr>
							<td><label>操作人员代码：</label></td>
							<td><input type="text" id="userSn" name="userSn" datasource="USER_SN"  datatype="1,is_null,30"  value=""  operation="like"/></td>
							<td><label>操作人员名称：</label></td>
							<td><input type="text" id="personName" name="personName" datasource="PERSON_NAME"  datatype="1,is_null,30" value=""  operation="like"/></td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="searchUser">查&nbsp;&nbsp;询</button></div></div></li>
						    <li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre1" name="btn-pre">上一步</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
			</div>
			<div class="panelBar">
				<ul class="toolBar">
					<li class="line">line</li>
					<li><a class="add" href="javascript:void(0);" id="addUser" title=""><span>批量新增</span></a></li>
					<li class="line">line</li>
					<li><a class="delete" href="javascript:void(0);" id="deleteNoticeUser" title="确定要删除吗?"><span>批量删除</span></a></li>
				</ul>
			</div>
			<div class="pageContent">
				<div id="noticeUser">
					<table width="100%" id="noticeUserlb" name="noticeUserlb" multivals="noticeUserVal" ref="noticeUser"  style="display: none"  refQuery="fm-noticeUserTable">
						<thead>
							<tr>
								<th type="multi" name="XH" unique="LIMIT_ID"></th>
                           		<th fieldname="PERSON_NAME">用户名称</th>
                            	<th fieldname="ONAME">所属部门</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
				</table>
				</div>
				<div id="noticeUserVal">
					<table style="display:none">
						<tr>
							<td><textarea id="noticeUserVal0" name="multivals" style="width:400px;height:10px" column="1" style="" ></textarea></td>
						</tr>
					</table>
					</div>
					</div>
				</div>
		</div>
	</div>
</div>
</body>
</html>