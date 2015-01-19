<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
	 Title:直发类型
	 Version:1.0
     Author：suoxiuli 2014-07-11
     Remark：direct type
-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>直发类型</title>
<script type="text/javascript">

var searchUrl = "<%=request.getContextPath()%>/part/basicInfoMng/DirectTypeAction/search.ajax";
//var deleteUrl = "<%=request.getContextPath()%>/part/basicInfoMng/DirectTypeAction/delete.ajax";
//定义弹出窗口样式
var diaAddOptions = {max:false,width:720,height:190,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//初始化
$(function()
{
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-searchDirectType");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-directTypeList");
	});
	//新增按钮响应
	$("#btn-add").bind("click", function(event){
		$.pdialog.open(webApps + "/jsp/dms/oem/part/basicInfo/directType/directTypeAdd.jsp?action=1", "tab-directTypeList", "新增直发类型", diaAddOptions);
	});                           
});

//列表编辑连接
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/part/basicInfo/directType/directTypeAdd.jsp?action=2", "修改直发类型", "修改直发类型", diaAddOptions);
}

/**
var $row;
//删除方法，rowobj：行对象，非jquery类型
function doDelete(rowobj)
{
	$row = $(rowobj);
	var url = deleteUrl + "?typeId="+$(rowobj).attr("TYPE_ID")+"&typeName="+$(rowobj).attr("TYPE_NAME");
	sendPost(url,"delete","",deleteCallBack,"true");
}
//删除回调方法
function  deleteCallBack(res)
{
	try
	{
		if($row) 
		{   
		    $("#btn-search").trigger("click");
		}
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}*/
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置：  配件管理  &gt; 基础数据管理   &gt; 基础信息管理   &gt; 直发类型管理</h4>
	<div class="page" >
	<div class="pageHeader" >
			<!-- 提交查询请求form -->
			<form method="post" id="fm-searchDirectType">
				<div class="searchBar" align="left" >
					<table class="searchContent" id="tab-searchDirectType">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>类型代码：</label></td>
					    <td><input type="text" id="typeCode"  name="typeCode" datasource="TYPE_CODE"  datatype="1,is_null,30" operation="like" /></td>
					    <td><label>类型名称：</label></td>
					    <td><input type="text" id="typeName" name="typeName" datasource="TYPE_NAME" datatype="1,is_null,30" operation="like" /></td>
					    <td><label>有效标识：</label></td>
					    <td>
					    	<select id="status" name="status" kind="dic" src="YXBS" datasource="STATUS" datatype="1,is_null,6" operation="=" >
					    	<option value="100201" selected>有效</option>
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
		<div id="page_directTypeList" >
			<!-- table参数说明：{ref=父级div的id（page_userlist）,refQuery=与结果列表对应的查询条件table的id} -->
			<table style="display:none;width:100%;" id="tab-directTypeList" name="tablist" ref="page_directTypeList" refQuery="tab-searchDirectType" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="TYPE_CODE" >类型代码</th>
							<th fieldname="TYPE_NAME" >类型名称</th>
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