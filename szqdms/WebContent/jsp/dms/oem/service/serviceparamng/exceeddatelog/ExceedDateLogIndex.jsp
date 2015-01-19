<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>超单修改轨迹查询</title>
<script type="text/javascript">
/* //查询按钮响应方法
$(function(){
	$("#search").click(function(){
		if($("#cdrqgjlb").is(":hidden")){
			$("#cdrqgjlb").show();
			$("#cdrqgjlb").jTable();
		}
	});
});
function open(){
	alertMsg.info("弹出服务商树");
}
function doDetail(){
	var options = {max:false,width:650,height:260,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/ywcsgl/spdgj.jsp", "spdxx", "索赔单修改轨迹", options);
} */

var searchUrl = "<%=request.getContextPath()%>/service/serviceparamng/ExceedDateLogMngAction/search.ajax";
//查询按钮响应方法
 $(function(){
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
	 
});
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
	 
	   $("#ORG_CODE").attr("code",orgCode); 
	   $("#ORG_ID").val(orgId);
	  $("#ORG_NAME").val(orgName);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;业务参数管理&gt;超单修改轨迹查询</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="fm-search" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="tab-search">
						<input type="hidden" id="ORG_ID" name="ORG_ID" datasource="T.ORG_ID" datatype="1,is_null,100" value="" />
						<tr>
						   	<td><label>服务商代码：</label></td>
							<td><input type="text" id="ORG_CODE" name="ORG_CODE"  datasource="T.ORG_CODE"  datatype="1,is_null,100" value="" readOnly hasBtn="true" callFunction="open();"/></td>
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
			<div id="page-list">
				<table width="100%" id="tab-list" name="tab-list" style="display: none" ref="page-list" refQuery="tab-search" pageRows="10">
					<thead>
					<tr>
					<th type="single" name="XH" style="display:none"></th>
								 
					<th fieldname="ORG_CODE" >服务商代码</th>
					<th fieldname="ORG_NAME" >服务商名称</th>
					<th fieldname="CLAIM_ID" >索赔单主键</th>
					<th fieldname="CLAIM_NO" >索赔单号</th>
					<th fieldname="EXCEED_DAYS" >超单天数</th> 
					<th fieldname="CREATE_USER" >创建人</th>
					<th fieldname="CREATE_TIME" >创建时间</th>
					<th fieldname="UPDATE_USER" >更新人</th>
					<th fieldname="UPDATE_TIME" >更新时间</th> 						 
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