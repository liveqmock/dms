<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.org.frameImpl.Constant"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>旧件集中点与服务站关系</title>
<script type="text/javascript">
/**
 * 查询提交方法,方式为：/service/basicinfomng/TaskAmountMngAction/search.ajax
 * 其中，sysmng/usermsg/为类路径（去掉com.dms.actions）
 * TaskAmountMngAction/为提交到后台的action类名
 * search为提交请求类中需要执行的方法名
 * .ajax表示请求为ajax请求
 */
var searchUrl = "<%=request.getContextPath()%>/service/basicinfomng/ReturnDealerRelationMngAction/search.ajax";
var deleteUrl = "<%=request.getContextPath()%>/service/basicinfomng/ReturnDealerRelationMngAction/resetStatus.ajax";
//定义弹出窗口样式
var options = {max:true,width:800,height:300,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//查询按钮响应方法
 $(function(){
/* 	$("#search").click(function(){
		if($("#gzmslb").is(":hidden"))
		{
			$("#gzmslb").show();
			$("#gzmslb").jTable();
		}
	}); */
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-search");//获取页面提交请求的form对象
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
    	 * @"USER_TYPE":查询返回结果显示的table表格id
    	 */
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-list");
	});
	
		//新增按钮响应
	$("#btn-add").bind("click", function(event){
		$.pdialog.open(webApps + "/jsp/dms/oem/service/basicinfomng/returndealerrelation/ReturnDealerRelationAdd.jsp?action=1", "ReturnDealerRelation", "新增旧件集中点与服务站关系", options);
	});
});
function doUpdate(rowobj){
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/service/basicinfomng/returndealerrelation/ReturnDealerRelationAdd.jsp?action=2", "ReturnDealerRelation", "旧件集中点与服务站关系", options);
}
var $row;
//删除方法，rowobj：行对象，非jquery类型
function doDelete(rowobj)
{
	$row = $(rowobj);
	var status=<%=Constant.YXBS_02%>;
	var url = deleteUrl + "?relationId="+$(rowobj).attr("RELATION_ID")+"&status="+status;
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
			$("#tab-list").removeResult($row);
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
function open(){
	var id="ORG_CODE";
	var busType=2;
	/* 	showOrgTree(id,busType); */
		
	//最后一个参数1表示复选；2表示单选
	showOrgTree(id,busType,'',"2");
	//页面实现回调方法：
	//jsonObj:{"orgId":"","orgCode":"","orgName":""}
}
function showOrgTreeCallBack(jsonObj)
{
	  var orgId=jsonObj.orgId;
	  var orgCode=jsonObj.orgCode;
	  var orgName=jsonObj.orgName;
	   $("#D_ORGCODE").attr("code",orgCode); 	   
	   $("#D_ORGCODE").val(orgCode); 
	   $("#D_ORGID").val(orgId);
	  $("#D_ORGNAME").val(orgName);
}

//表选字典回调方法
function afterDicItemClick(id, $row, selIndex){
	//$row 行对象
	//selIndex 第几个字典
	
		//判断id中是否包含dia-D_ORGCODE的值
	if(id.indexOf("R_ORGCODE") == 0)
	{
		if($row.attr("ORG_ID")){
		
		$("#R_ORGID").val($row.attr("ORG_ID"));
		}
	
	/* 	if($row.attr("ORG_NAME")){
		
		$("#dia-R_ORGNAME").val($row.attr("ORG_NAME"));
		}
		 */
		
			$("#R_ORGNAME").val($("#"+id).val());
		return true;
	}
 
 
	return true;
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;基础信息管理&gt;旧件集中点与服务站关系</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="fm-search" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="tab-search">
				<input type="hidden" id="ORG_ID" name="ORG_ID" datasource="T.ORG_ID" datatype="1,is_null,100" value="" />
				<input type="hidden" id="R_ORGID" name="R_ORGID" datasource="T.R_ORGID" />
			    <input type="hidden" id="R_ORGNAME" name="R_ORGNAME" datasource="T.R_ORGNAME" />
				<tr>
					<td><label>旧件集中点名称：</label></td>
					<td><input type="text" id="R_ORGCODE" name="R_ORGCODE" datasource="T.R_ORGCODE" kind="dic"   src="T#TM_ORG:CODE:ONAME{ORG_ID}:1=1 AND STATUS='100201' AND IS_IC = 100101" operation="like"  datatype="1,is_null,100" value="" /></td>
					<td><label>服务商代码：</label></td>
					<td><input type="text" id="D_ORGCODE" name="D_ORGCODE"  datasource="T.D_ORGCODE"  datatype="1,is_null,100" value="" readOnly hasBtn="true" callFunction="open();"/></td>
					<td><label>服务站名称：</label></td>
			   	    <td><input type="text" id="D_ORGNAME" name="D_ORGNAME"  datasource="D_ORGNAME" datatype="1,is_null,100" readOnly/></td>
				</tr>
				</table>
				<div class="subBar">
					<ul>
						<li>
							<div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div>
						
						</li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-add" onclick="doAdd()">新&nbsp;&nbsp;增</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="gzms">
			<table width="100%" id="tab-list" name="tab-list" style="display: none" ref="gzms" refQuery="tab-search" pageRows="10">
					<thead>
					<tr>
						<th type="single" name="XH" style="display:none"></th>									
						<th fieldname="R_ORGNAME" >旧件集中点名称</th>
						<th fieldname="D_ORGNAME" >服务站名称</th>
						<th fieldname="RD_MILES" >服务商与旧件集中点距离</th>
						<th fieldname="RD_PRICE" >单价</th>
						<th fieldname="UPDATE_USER" >更新人</th>
						<th fieldname="UPDATE_TIME" >更新时间</th>
						<th fieldname="STATUS" >状态</th>
						<th colwidth="85" type="link" title="[编辑]|[删除]"  action="doUpdate|doDelete" >操作</th>
						<!-- <th colwidth="85" type="link" title="[关系]"  action="doUpdate" >操作</th> -->
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