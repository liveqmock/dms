<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	String action = request.getParameter("action");
	String flag = request.getParameter("flag");
	if (action == null)
		action = "1";
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String userId = user.getUserId();
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>通知通告</title>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
var $true=true;
var $fileTrue=true;
var action = "<%=action%>";
var flag = "<%=flag%>";
var diaSaveAction = "<%=request.getContextPath()%>/service/noticeManage/NoticeManageAction";
var fileSearchUrl = "<%=request.getContextPath()%>/service/noticeManage/NoticeManageAction/fileSearch.ajax";
$(function(){
	if(action != "1"){
		var selectedRows = parent.$("#noticeManagelb").getSelectedRows();
	  	setEditValue("dia-fm-noticeManage",selectedRows[0].attr("rowdata"));
     	$("#typeName").attr("code",selectedRows[0].attr("TYPE_ID"));
	  	$("#typeName").val(selectedRows[0].attr("TYPE_NAME"));
	  	$("#typeName").find("option").val(selectedRows[0].attr("TYPE_ID"));
		$("#typeName").find("option").text(selectedRows[0].attr("TYPE_NAME"));
	  	$("#typeName").find("option[text='"+selectedRows[0].attr("TYPE_NAME")+"']").attr("selected",true);
		$("#rangeTab").show();
		$("#filesTab").show();
		$("#dia-nextH1").show();
		if($("#combox_typeName"))
		{
			$("#combox_typeName").find("a").eq(0).text(selectedRows[0].attr("TYPE_NAME"));
			$("#combox_typeName").find("a").eq(0).val(selectedRows[0].attr("TYPE_ID"));
		}
		if(flag=="1"){
			$("#dia_li_sign").hide();
		}
	}
	//提报
	$("#dia-archive").bind("click",function(){
		var bulletinId=selectedRows[0].attr("BULLETIN_ID");
		var reportUrl = diaSaveAction + "/noticeArchive.ajax?bulletinId="+bulletinId+"";
		sendPost(reportUrl,"dia-archive","",diaArchiveCallBack,"false");
	});
	//关闭当前页面
	$("button.close").click(function(){
		parent.$.pdialog.close("noticeManage");
		return false;
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
			case 0:
				if($true){
					var $f = $("#dia_fm_atta");
					var sCondition = {};
					sCondition = $f.combined() || {};
					var bulletinId=$("#bulletinId").val();
					var fileSearchUrl1 =fileSearchUrl+"?bulletinId="+bulletinId;
					doFormSubmit($f,fileSearchUrl1,"",1,sCondition,"dia-fileslb");
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
//附件回调方法
function fjUpCallBack(fjid){
	var $f = $("#dia_fm_atta");
	var sCondition = {};
	sCondition = $f.combined() || {};
	var bulletinId=$("#bulletinId").val();
	var fileSearchUrl1 =fileSearchUrl+"?bulletinId="+bulletinId;
	doFormSubmit($f,fileSearchUrl1,"",1,sCondition,"dia-fileslb");
}
function diaArchiveCallBack(res){
	try
	{
		var result = getNodeText(res.getElementsByTagName("RESULT").item(0));
		if(result=='1'){
			var $row =  parent.$("#noticeManagelb").getSelectedRows();
			if($row[0]){
				parent.$("#noticeManagelb").removeResult($row[0]);
				$("button.close").click();
			}
		}
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
function doDownloadAtta(obj){
	var fjid = $(obj).attr("FID");
	var fjmc = $(obj).attr("FJMC");
	var wjjbs = $(obj).attr("WJJBS");
	var blwjm = $(obj).attr("BLWJM");
	$.filestore.download({"fjid":fjid,"fjmc":fjmc,"wjjbs":wjjbs,"blwjm":blwjm});
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
					<li><a href="javascript:void(0)"><span>基本信息</span></a></li>
					<li id="filesTab"><a href="javascript:void(0)"><span>附件信息</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" >
					<div class="page">
						<div class="pageContent" style="" >
							<form method="post" id="dia-fm-noticeManage" class="editForm" style="width: 99%;">
								<div align="left">
									<fieldset>
									<legend align="right">
									<a onclick="onTitleClick('dia-tab-preAuthBasic')">&nbsp;通告基本信息&gt;&gt;</a>
									</legend>
									<table class="editTable" id="sbjjxx">
									 <tr>
									 	<td><label>通告类别：</label></td>			
									 	<td id="test">				
									 		<input type="text" id="typeName" name="typeName" datasource="TYPE_NAME" readonly="readonly" />													 	
										</td>
									</tr>
								    <tr>
							    		<td><label>通告标题：</label></td>
							    		<td><input type="text" id="title" name="title" datasource="TITLE" readonly="readonly" /></td>
									</tr>
									<tr>
										<td><label>通告内容：</label></td>
										<td colspan="12"><textarea class="" rows="6" id="content" name="content" datasource="CONTENT" style="width:100%" readonly="readonly"></textarea></td>
										<td><input type="hidden" id="bulletinId" name="bulletinId" datasource="BULLETIN_ID"/></td>
										<td><input type="hidden" id="rangeId" name="rangeId" datasource="RANGE_ID"/></td>
									</tr>
							          </table>
									</fieldset>
								</div>
							</form>
						</div>
						<div class="formBar">
							<ul>
								<li id="dia-nextH1"><div class="button"><div class="buttonContent"><button type="button" id="dia-next0" name="btn-next">下一步</button></div></div></li>
								<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
							</ul>
					  </div>
				 </div>
			<div class="page">
				<div class="pageHeader">
					<form id="fm-fwhdfj" method="post">
						<div class="searchBar" align="left">
							<table class="searchContent" id="fm-fwhdfjTable">
							</table>
						</div>
					</form>
				</div>
				<div class="pageContent">  
				<form method="post" id="dia_fm_atta" class="editForm" >
				</form>
				<div align="left">
	              <fieldset>
					<legend align="right"><a onclick="onTitleClick('dia-spdfj')">&nbsp;已传附件列表&gt;&gt;</a></legend>
						<div id="dia-files">
							<table style="display:none;width:100%;" id="dia-fileslb" name="dia-fileslb" ref="dia-files" refQuery="dia_tab_atta" >
								<thead>
									<tr>
										<th type="single" name="XH" style="display:none"></th>
										<th fieldname="FJMC" >附件名称</th>
										<th fieldname="CJR" >上传人</th>
										<th fieldname="CJSJ" >上传时间</th>
										<th colwidth="85" type="link" title="[下载]"  action="doDownloadAtta">操作</th>
									</tr>
								  </thead>
								<tbody>
							  </tbody>
						   </table>
						</div>
					 </fieldset>
	              </div> 
				</div>
				<div class="formBar">
					<ul>
						<li id="dia_li_sign"><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-archive">归&nbsp;&nbsp;档</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre4" name="btn-pre">上一步</button></div></div></li>
					</ul>
				</div>
				<form id="dialog-fm-download"style="display:none"></form>
			</div>
		</div>
	</div>
</div>
</body>
</html>