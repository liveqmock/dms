<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
	 Title:配送关系
	 Version:1.0
     Author：suoxiuli 2014-07-30
     Remark：service DC
-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>配送关系管理</title>
<script type="text/javascript">

var searchUrl = "<%=request.getContextPath()%>/part/basicInfoMng/ServiceDcAction/search.ajax";
var deleteUrl = "<%=request.getContextPath()%>/part/basicInfoMng/ServiceDcAction/delete.ajax";
//定义弹出窗口样式
var diaAddOptions = {max:false,width:450,height:200,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//初始化
$(function()
{
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-searchServiceDc");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-serviceDcList");
	});
	
	//新增按钮响应
	$("#btn-add").bind("click", function(event){
		$.pdialog.open(webApps + "/jsp/dms/oem/part/basicInfo/serviceDc/serviceDcAdd.jsp?action=1", "新增配送关系", "新增配送关系", diaAddOptions);
	});                           
});

//列表编辑连接
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/part/basicInfo/serviceDc/serviceDcAdd.jsp?action=2", "修改配送关系", "修改配送关系", diaAddOptions);
}

var $row;
//删除方法，rowobj：行对象，非jquery类型
function doDelete(rowobj)
{
	$row = $(rowobj);
	var url = deleteUrl + "?relationId="+$(rowobj).attr("RELATION_ID");
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
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置：  配件管理  &gt; 基础数据管理   &gt; 基础信息管理   &gt; 配送关系管理</h4>
	<div class="page" >
	<div class="pageHeader" >
			<!-- 提交查询请求form -->
			<form method="post" id="fm-searchServiceDc">
				<div class="searchBar" align="left" >
					<table class="searchContent" id="tab-searchServiceDc">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>配送中心名称：</label></td>
					    <td>
					    	<input type="text" id="dcId"  name="dcId" kind="dic" 
					    		src="T#TM_ORG:CODE:ONAME:1=1 AND ORG_TYPE='200005' AND STATUS='100201' ORDER BY CODE" 
					    		datasource="sd.DC_CODE" datatype="1,is_null,100" operation="=" />
					    </td>
					    <td><label>渠道商名称：</label></td>
					    <td>
					    	<input type="text" id="orgId"  name="orgId" kind="dic" 
					    		src="T#TM_ORG:CODE:ONAME:1=1 AND ORG_TYPE IN ('200006','200007') AND STATUS='100201' ORDER BY CODE" 
					    		datasource="sd.ORG_CODE" datatype="1,is_null,100" operation="=" />
					    </td>
					    <!-- td><label>有效标识：</label></td>
					    <td>
					    	<select id="status" name="status" kind="dic" src="YXBS" datasource="sd.STATUS" datatype="1,is_null,6" operation="=" >
					    		<option value="100201" selected>有效</option>
					    	</select>
					    </td -->
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
		<div id="page_serviceDcList" >
			<!-- table参数说明：{ref=父级div的id（page_userlist）,refQuery=与结果列表对应的查询条件table的id} -->
			<table style="display:none;width:100%;" id="tab-serviceDcList" name="tablist" ref="page_serviceDcList" refQuery="tab-searchServiceDc" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="DC_CODE" >配送代码</th>
							<th fieldname="DC_NAME" >配送中心</th>
							<th fieldname="ORG_CODE" >渠道商代码</th>
							<th fieldname="ORG_NAME" >渠道商名称</th>
							<th fieldname="IF_DEFAULT" >是否默认配送中心</th>
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