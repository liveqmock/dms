<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>服务活动类别管理</title>
<script type="text/javascript">
//查询提交方法
var searchUrl = "<%=request.getContextPath()%>/service/serviceactivity/ServiceActivityTypeMngAction/search.ajax";
var deleteUrl = "<%=request.getContextPath()%>/service/serviceactivity/ServiceActivityTypeMngAction/delete.ajax";
//定义弹出窗口样式
var diaAddOptions = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//查询按钮响应方法
$(function()
{
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-serviceActivity");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-serviceActivity");
	});
	//新增按钮响应
	$("#btn-add").bind("click", function(event){
		$.pdialog.open(webApps + "/jsp/dms/oem/service/serviceactivity/serviceActivityTypeMngAdd.jsp?action=1", "ServiceActivityType", "服务活动新增", diaAddOptions,true);
	});
});
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/service/serviceactivity/serviceActivityTypeMngAdd.jsp?action=2", "ServiceActivityType", "服务活动修改", diaAddOptions,true);
}
	var $row;
function doDelete(rowobj)
{
	$row = $(rowobj);
	var url = deleteUrl + "?activityId="+$(rowobj).attr("ACTIVITY_ID")+"&activityStatus="+$(rowobj).attr("ACTIVITY_STATUS");
	sendPost(url,"delete","",deleteCallBack,"true");
}
//删除回调方法
function  deleteCallBack(res)
{
	try
	{
		if($row) 
			$("#tab-serviceActivity").removeResult($row);
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
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：服务活动管理&gt;服务活动类别管理</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="fm-serviceActivity" method="post">
				<input type="hidden" id="serviceActivityInfoId" name="serviceActivityInfoId" datasource="ACTIVITY_ID" />
				<div class="searchBar" align="left">
					<table class="searchContent" id="serviceActivityTable">
						<tr>
							<td><label>活动代码：</label></td>
							<td><input type="text" id="ACTIVITY_CODE" name="ACTIVITY_CODE" datasource="ACTIVITY_CODE" datatype="1,is_digit_letter,30"  value="" operation="like" /></td>
							<td><label>活动名称：</label></td>
							<td><input type="text" id="ACTIVITY_NAME" name="ACTIVITY_NAME" datasource="ACTIVITY_NAME" datatype="1,is_null,30" value="" operation="like" /></td>
						</tr>
						<tr>
							<td><label>活动类别：</label></td>
							<td><select  type="text" id="ACTIVITY_TYPE" name="ACTIVITY_TYPE" datasource="ACTIVITY_TYPE" kind="dic" class="combox" src="HDLB"  datatype="1,is_null,8" value="" >
									<option value=-1 selected>--</option>
								</select>
							</td>
							<td><label>处理方式：</label></td>
							<td><select type="text" id="MANAGE_TYPE" name="MANAGE_TYPE" datasource="MANAGE_TYPE" kind="dic" class="combox" src="CLFS" datatype="1,is_null,8" value="" >
									<option value=-1 selected>--</option>
								</select>
							</td>
							<td><label>活动状态：</label></td>
							<td><select type="text" id="ACTIVITY_STATUS" name="ACTIVITY_STATUS" datasource="ACTIVITY_STATUS" kind="dic" class="combox" src="HDZT" datatype="1,is_null,8" value="" >
									<option value=-1 selected>--</option>
								</select>
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
			<div id="page_serviceActivity" >
				<!-- table参数说明：{ref=父级div的id（page_userlist）,refQuery=与结果列表对应的查询条件table的id} -->
				<table style="display:none;width:100%;" id="tab-serviceActivity" name="tablist" ref="page_serviceActivity" refQuery="tab-serviceActivity" >
						<thead>
							<tr>
								<th type="single" name="XH" style="display:none"></th>
								<th fieldname="ACTIVITY_CODE" >活动代码</th>
								<th fieldname="ACTIVITY_NAME" >活动名称</th>
								<th fieldname="ACTIVITY_TYPE">活动类别</th>
								<th fieldname="MANAGE_TYPE" >处理方式</th>
								<th fieldname="START_DATE" >开始日期</th>
								<th fieldname="END_DATE" >结束日期</th>
								<th fieldname="ACTIVITY_STATUS" >活动状态</th>
								<th colwidth="85" type="link" title="[修改]|[删除]"  action="doUpdate|doDelete" >操作</th>
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