<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	String action = request.getParameter("action");
	if (action == null)
		action = "1";
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String userId = user.getUserId();
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>通知通告编辑</title>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
var $true=true;
var $fileTrue=true;
var action = "<%=action%>";
var diaSaveAction = "<%=request.getContextPath()%>/service/noticeManage/NoticeManageAction";
var fileSearchUrl = "<%=request.getContextPath()%>/service/noticeManage/NoticeManageAction/fileSearch.ajax";
var publishUrl = "<%=request.getContextPath()%>/service/noticeManage/NoticeManageAction/noticeManagePublish.ajax";
var searchUrl = "<%=request.getContextPath()%>/service/noticeManage/NoticeManageAction/noticeContentSearch.ajax";
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
	}else{
		$("#rangeTab").hide();
		$("#filesTab").hide();
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
			var addUrl = diaSaveAction + "/insertContent.ajax";
			doNormalSubmit($f,addUrl,"dia-save",sCondition,diaInsertCallBack);
		}else{	
			sCondition = $("#dia-fm-noticeManage").combined(1) || {};
			var addUrl = diaSaveAction + "/updateContent.ajax";
			doNormalSubmit($f,addUrl,"dia-save",sCondition,diaUpdateCallBack);
		};
	});
	$("#doPublish").bind("click",function(event){
		var bulletinId=$("#bulletinId").val();
		var url = publishUrl + "?bulletinId="+bulletinId;
		sendPost(url,"doPublish","",deleteCallBack,"true");
	});
	//新增渠道商页面
	$("#btn-add").bind("click",function(){
		var bulletin_range= $("#DI_fwlb").val();
		var options = {max:false,width:1000,height:600,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps+"/jsp/dms/oem/noticeManage/notice/noticeManageDealerAdd.jsp?bulletin_range="+bulletin_range, "noticeManageDealerAdd", "新增渠道商", options);
	});
	$("#btn-searchDel").bind("click",function(){
		var $f = $("#rangeform");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
    	var searchUrl =diaSaveAction+"/noticeRangeSearch.ajax?&bulletinId="+$("#bulletinId").val();
		doFormSubmit($f,searchUrl,"btn-searchDel",1,sCondition,"orgRangelb");
	});
	$("#deleteDealer").bind("click", function(event){
		var mxid=$("#val0").val();
	    if(mxid=="")
	    {
	    	 alertMsg.warn("请选择渠道商！");
	    	return false;
	    }else{
	    	var scDealerUrl =diaSaveAction+"/deleteDealer.ajax?mxid="+mxid;
			sendPost(scDealerUrl,"deleteDealer","",deleteDelCallBack,"true");
	    };
	});
	//上传附件
	$("#addAtt").bind("click",function(){
		var bulletinId=$("#bulletinId").val();
		$.filestore.open(bulletinId,{"folder":"true","holdName":"false","fileSizeLimit":0,"fileTypeDesc":"All Files","fileTypeExts":"*.*"});
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
					$("#btn-searchDel").click();
				}
				$true=false;
				break;
			case 1:
				if($fileTrue){
					var $f = $("#dia_fm_atta");
					var sCondition = {};
					sCondition = $f.combined() || {};
					var bulletinId=$("#bulletinId").val();
					var fileSearchUrl1 =fileSearchUrl+"?bulletinId="+bulletinId;
					doFormSubmit($f,fileSearchUrl1,"",1,sCondition,"dia-fileslb");
				}
				$fileTrue=false;
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
				case 2://第2个tab页					
					break;
				default:
					break;
			}
		})
		(parseInt($tabs.attr("currentIndex")));
	});
});
function  deleteDelCallBack(res)
{
	try
	{
		var $f = $("#rangeform");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
    	var searchUrl =diaSaveAction+"/noticeRangeSearch.ajax?&bulletinId="+$("#bulletinId").val();
		doFormSubmit($f,searchUrl,"btn-searchDel",1,sCondition,"orgRangelb");
		$("#val0").val("");
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//删除回调方法
function  deleteCallBack(res)
{
	try
	{
		
		parent.$.pdialog.close("noticeManage");
		parent.$("#btn-search").click();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//新增回调函数
function diaInsertCallBack(res)
{
	try
	{
		var rows = res.getElementsByTagName("ROW");
		var bulletinId =getNodeText(rows[0].getElementsByTagName("BULLETIN_ID").item(0));
		parent.$("#noticeManagelb").insertResult(res,0);
		if(parent.$("#noticeManagelb_content").size()>0){
			$("td input[type=radio]",parent.$("#noticeManagelb_content").find("tr").eq(0)).attr("checked",true);			
		}else{
			$("td input[type=radio]",parent.$("#noticeManagelb").find("tr").eq(0)).attr("checked",true);
		}
		action=2;
		$("#bulletinId").val(bulletinId);
		$("#rangeTab").show();
		$("#filesTab").show();
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
//列表复选
function doCheckbox(checkbox)
{
	var arr = [];
	var $checkbox = $(checkbox);
	while($checkbox[0].tagName != "TABLE"){
		$checkbox = $checkbox.parent();
	}
	if($checkbox.attr("id").indexOf("orgRangelb")==0){
		var $tr = $(checkbox).parent().parent().parent();
		arr.push($tr.attr("RANGE_ID"));
		var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
		multiSelected($checkbox,arr,$("#orgDelete"));
	}
	if($checkbox.attr("id").indexOf("mainDealerllb")==0){
		var $tr = $(checkbox).parent().parent().parent();
		arr.push($tr.attr("ORG_ID"));
		arr.push($tr.attr("ONAME"));
		var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
		multiSelected($checkbox,arr,$("#dealerVals"));
	}
}
//附件回调方法
function fjUpCallBack(fjid){
	var $f = $("#dia_fm_atta");
	var sCondition = {};
	sCondition = $f.combined() || {};
	var bulletinId=$("#bulletinId").val();
	var fileSearchUrl1 =fileSearchUrl+"?bulletinId="+bulletinId;
	doFormSubmit($f,fileSearchUrl1,"",1,sCondition,"dia-fileslb");
}
function doDeleteAtta(rowobj)
{
	$rowAtta = $(rowobj);
	var url = diaSaveAction + "/attaDelete.ajax?fjid="+$(rowobj).attr("FJID");
	sendPost(url,"","",deleteAttaCallBack,"true");
}
function doDownloadAtta(obj){
	var fjid = $(obj).attr("FID");
	var fjmc = $(obj).attr("FJMC");
	var wjjbs = $(obj).attr("WJJBS");
	var blwjm = $(obj).attr("BLWJM");
	$.filestore.download({"fjid":fjid,"fjmc":fjmc,"wjjbs":wjjbs,"blwjm":blwjm});
}
//删除回调方法
function  deleteAttaCallBack(res)
{
	try
	{
		if($rowAtta) 
			$("#dia-fileslb").removeResult($rowAtta);
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
	<!-- tab页  -->
	<div class="tabs" eventType="click" id="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="javascript:void(0)"><span>基本信息</span></a></li>
					<li id="rangeTab"><a href="javascript:void(0)"><span>发布范围</span></a></li>
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
									<a onclick="onTitleClick('dia-tab-preAuthBasic')">&nbsp;通知通告内容基本信息编辑&gt;&gt;</a>
									</legend>
									<table class="editTable" id="sbjjxx">
									 <tr>
									 	<td><label>通告类别：</label></td>			
									 	<td id="test">																	 	
											<select  type="text" id="typeName"  name="typeName" datasource="TYPE_ID" kind="dic" class="combox" src="T#MAIN_BULLETIN_TYPE A, MAIN_BULLETIN_PERMISSION B:A.TYPE_ID:A.TYPE_NAME:1=1 AND A.TYPE_ID = B.TYPE_ID AND A.STATUS=<%=DicConstant.YXBS_01 %> AND USER_ID=<%=userId%>" datatype="0,is_null,30"  value="" >
												<option value=-1>--</option>
											</select>
										</td>
										<td><label>发布范围：</label></td>
											<td><input type="text" id="DI_fwlb" name="DI_fwlb"  kind="dic" datasource="BULLETIN_RANGE" multi="true" src="ZZLB" datatype="0,is_null,100" />
										</td>
									</tr>
								    <tr>
							    		<td><label>通告标题：</label></td>
							    		<td><input type="text" id="title" style="width:500px;" name="title" datasource="TITLE" datatype="0,is_null,100" /></td>
									</tr>
									<tr>
										<td><label>通告内容(1000字以内)：</label></td>
										<td><textarea class="" rows="6" style="width:500px;" id="rejectRemarks" name="content" datasource="CONTENT" style="width:100%" datatype="1,is_null,2000" ></textarea></td>
										<td><input type="hidden" id="bulletinId" name="bulletinId" datasource="BULLETIN_ID"/></td>
									</tr>
							          </table>
									</fieldset>
								</div>
							</form>
						</div>
						<div class="formBar">
							<ul>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-save">保&nbsp;&nbsp;存</button></div></div></li>
								<li id="dia-nextH1"><div class="button"><div class="buttonContent"><button type="button" id="dia-next0" name="btn-next">下一步</button></div></div></li>
								<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
							</ul>
					  </div>
				 </div>
			<!-- 渠道商TAB -->
			<div class="page">
			<div class="pageHeader">
			<form id="rangeform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="fwhdxgTable">
						<tr>
							<td><label>渠道商代码：</label></td>
							<td><input type="text" id="dealerCode" name="dealerCode" datasource="CODE" datatype="1,is_null,30" value="" operation="like" /></td>
							<td><label>渠道商名称：</label></td>
							<td><input type="text" id="dealerName" name="dealerName" datasource="ONAME"  datatype="1,is_null,30" value="" operation="like" /></td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-searchDel">查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre3" name="btn-pre">上一步</button></div></div></li>
							<li id="dia-nextH1"><div class="button"><div class="buttonContent"><button type="button" id="dia-next0" name="btn-next">下一步</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div class="panelBar">
					<ul class="toolBar">
						<li class="line">line</li>
						<li><a class="add" href="javascript:void(0);" id="btn-add" title=""><span>批量新增</span></a></li>
						<li class="line">line</li>
						<li><a class="delete" href="javascript:void(0);" id="deleteDealer" title="确定要删除吗?"><span>批量删除</span></a></li>
					</ul>
				</div>
			<div id="fwhdxg">
				<table width="100%" id="orgRangelb" name="orgRangelb" ref="fwhdxg" multi="orgDelete" style="display: none"  refQuery="fwhdxgTable" >
					<thead>
							<tr>
								<th type="multi" name="XH" unique="RANGE_ID"></th>
								<th fieldname="CODE" >渠道商代码</th>
								<th fieldname="ONAME" >渠道商名称</th>
							</tr>
					</thead>
				<tbody>
				</tbody>
				</table>
			</div>
			<div id="orgDelete">
				<table style="width:100%;">
					<tr>
						<td><textarea id="val0" name="multivals" style="width:400px;height:10px;display: none" column="1" ></textarea></td>
					</tr>
				</table>
			</div>
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
										<th colwidth="85" type="link" title="[删除]|[下载]"  action="doDeleteAtta|doDownloadAtta">操作</th>
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
						<li><div class="button"><div class="buttonContent"><button type="button" id="addAtt">上传附件</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="doPublish" name="">发&nbsp;&nbsp;布</button></div></div></li>
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