<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
	 Title:订单审核员属性管理
	 Version:1.0
     Author：suoxiuli 2014-07-28
     Remark：order check
-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>订单审核员属性</title>
<script type="text/javascript">

var searchUrl = "<%=request.getContextPath()%>/part/basicInfoMng/OrderCheckAction/search.ajax";
var deleteUrl = "<%=request.getContextPath()%>/part/basicInfoMng/OrderCheckAction/delete.ajax";
//定义弹出窗口样式
var diaAddOptions = {max:false,width:400,height:170,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//初始化
$(function()
{
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-searchOrderCheck");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-orderCheckList");
	});
	//新增按钮响应
	$("#btn-add").bind("click", function(event){
		$.pdialog.open(webApps + "/jsp/dms/oem/part/basicInfo/orderCheck/orderCheckAdd.jsp?action=1", "新增订单审核员属性", "新增订单审核员属性", diaAddOptions);
	});                           
});

//列表编辑连接
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/part/basicInfo/orderCheck/orderCheckAdd.jsp?action=2", "修改订单审核员属性", "修改订单审核员属性", diaAddOptions);
}

</script>
</head>
<body>
<div id="layout" style="width:100%;">
	
	<div id="progressBar1" class="progressBar">loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置：  配件管理  &gt; 基础数据管理   &gt; 基础信息管理   &gt; 订单审核员属性管理</h4>
	<div class="page" >
	<div class="pageHeader" >
			<!-- 提交查询请求form -->
			<form method="post" id="fm-searchOrderCheck">
				<div class="searchBar" align="left" >
					<table class="searchContent" id="tab-searchOrderCheck">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>办事处名称：</label></td>
					    <td>
					    	<input type="text" id="orgCode"  name="orgCode" kind="dic" 
				    			src="T#TM_ORG:CODE:ONAME{ORG_ID}:1=1 AND ORG_TYPE='200004' AND STATUS='100201'" 
				    			datasource="ORG_CODE" datatype="1,is_null,100" operation="like" />
				    		<input type="hidden" id="orgName"  name="orgName" datasource="ORG_NAME"  />
					    </td>
						<td><label>审核员名称：</label></td>
					    <td>
					    	<input type="text" id="userAccount"  name="userAccount" kind="dic" 
					    		src="T#TM_USER:ACCOUNT:PERSON_NAME:1=1 AND ORG_ID='10000063' AND STATUS='100201'" 
					    		datasource="USER_ACCOUNT" datatype="1,is_null,32" operation="like" />
				   			<input type="hidden" id="userName"  name="userName" datasource="USER_NAME"  />
					    </td>
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
		<div id="page_orderCheckList" >
			<!-- table参数说明：{ref=父级div的id（page_userlist）,refQuery=与结果列表对应的查询条件table的id} -->
			<table style="display:none;width:100%;" id="tab-orderCheckList" name="tablist" ref="page_orderCheckList" refQuery="tab-searchOrderCheck" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="ORG_NAME" >办事处名称</th>
							<th fieldname="USER_NAME" >审核员姓名</th>
							<th fieldname="STATUS" >有效标识</th>
							<th fieldname="CREATE_USER" >创建人</th>
							<th fieldname="CREATE_TIME" >创建时间</th>
							<th colwidth="85" type="link" title="[编辑]"  action="doUpdate" >操作</th>
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