<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant" %>
<%@ page import="com.org.framework.Globals" %>
<%@ page import="com.org.framework.common.User" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>库存盘点</title>
<script type="text/javascript"><!--
var searchUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PtBuInventoryAction/search.ajax";
var deleteUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PtBuInventoryAction/delete.ajax";
var startUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PtBuInventoryAction/checkStart.ajax";
//初始化方法
$(function(){
	//查询方法
	$("#btn-cx").bind("click",function(event){
		if($("#tab-list").is(":hidden"))
		{
			$("#tab-list").show();
			$("#tab-list").jTable();
		}
		var $f = $("#fm-ccpd");
		var sCondition = {};
    	sCondition = $f.combined() || {};    	
		doFormSubmit($f,searchUrl,"btn-cx",1,sCondition,"tab-list");
	});
	
	$("#btn-xz").bind("click",function(event){		
		var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps + "/jsp/dms/oem/part/storage/checkSetup/checkSetupAdd.jsp?action=1", "pdxz", "盘点设置新增", options);								  
	});
});

function doUpdate(rowobj){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps + "/jsp/dms/oem/part/storage/checkSetup/checkSetupUpdate.jsp?action=2", "pdbj", "盘点设置编辑", options);
}

var $row;
//删除方法，rowobj：行对象，非jquery类型
function doDelete(rowobj)
{
	$row = $(rowobj);
	var url = deleteUrl + "?inventory_id="+$(rowobj).attr("INVENTORY_ID");
	sendPost(url,"delete","",deleteCallBackf,"true");
}
//删除回调方法
function  deleteCallBackf(res)
{
	try
	{
		if($row) 
		$("#btn-cx").trigger("click");			
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}

function showLink(obj)
{
	var $row=$(obj).parent();
    return "<a href='#' onclick=toDetail("+$row.attr("INVENTORY_ID")+",this) class='op'>"+$row.attr("INVENTORY_NO")+"</a>";
}
function toDetail(INVENTORY_ID,link){
	var $row = $(link);
	while($row.get(0).tagName != "TR")
		$row = $row.parent();
	//var $row = $(link).parent();
	//alert($row.get(0).tagName);
	$("td input[type=radio]",$row).attr("checked",true);
//	var w = document.documentElement.clientWidth-100;
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/part/storage/checkSetup/checkSetupDetail.jsp", "pdmx", "盘点设置明细", options);
}

function doStart(rowobj){
$row = $(rowobj);
	//alertMsg.confirm("确认盘点开始?",{okCall:doApproveOk1,cancelCall:doApproveOk2});
	var url = startUrl + "?inventory_id="+$(rowobj).attr("INVENTORY_ID");
	sendPost(url,"delete","",deleteCallBackf,"true");	
}
function doOver(){
	alertMsg.confirm("确认盘点结束?",{okCall:doApproveOk1,cancelCall:doApproveOk2});
}
function doApproveOk1(){
	alertMsg.info("操作成功.");
}
function doApproveOk2(){
	return false;
}
--></script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 仓储管理  &gt; 库存盘点  &gt; 库存盘点设置</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-ccpd">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-pkcx">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>盘点编号：</label></td>
					    <td><input type="text" id="in-INVENTORY_NO" name="in-INVENTORY_NO" datasource="INVENTORY_NO" datatype="1,is_digit_letter,300"  operation="like" /></td>
					    <td><label>盘点仓库：</label></td>
					    <td>
					    	<input type="text" id="in-WAREHOUSE_CODE" name="in-WAREHOUSE_CODE" kind="dic" src="T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME:1=1 AND WAREHOUSE_TYPE NOT IN(100106)  AND STATUS=<%=DicConstant.YXBS_01 %> ORDER BY WAREHOUSE_CODE" datasource="WAREHOUSE_CODE" datatype="1,is_null,300" />				    
					    </td>
					    <td><label>盘点类型：</label></td>
					    <td>
					    	<input type="text" id="in-INVENTORY_TYPE" name="in-INVENTORY_TYPE" kind="dic" src="PDLX" datasource="INVENTORY_TYPE" datatype="1,is_null,300" operation="="/>
					    </td>
					</tr>
					<tr>
				   		<td><label>盘点日期：</label></td>
					    <td>
				    		<input type="text" group="in-kscjrq,in-jscjrq"  id="in-kscjrq"  name="in-kscjrq" operation=">="  dataSource="INVENTORY_DATE" style="width:75px;"  kind="date" datatype="1,is_date,300" onclick="WdatePicker()" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="in-kscjrq,in-jscjrq"  id="in-jscjrq"  name="in-jscjrq" operation="<=" dataSource="INVENTORY_DATE" style="width:75px;margin-left:-30px;" kind="date" datatype="1,is_date,300" onclick="WdatePicker()" />
				   		</td>	
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-cx" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-xz" >新&nbsp;&nbsp;增</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_ddlb" >
			<table style="display:none;width:100%;" id="tab-list" name="tab-list" ref="page_ddlb" refQuery="tab-pkcx" >
					<thead>
						<tr>
							<th fieldname="ROWNUMS" style="display:"></th>
							<th type="single" name="XH" style="display:none"></th>	
							<th fieldname="INVENTORY_NO" refer="showLink">盘点编号</th>
							<th fieldname="WAREHOUSE_NAME">盘点仓库</th>
							<th fieldname="INVENTORY_TYPE">盘点类型</th>
							<th fieldname="INVENTORY_DATE">盘点日期</th>
							<th fieldname="INVENTORY_STATUS" colwidth="100">盘点状态</th>
							<th fieldname="INVENTORY_USER">盘点人</th>
							<th colwidth="200" type="link" title="[编辑]|[删除]|[盘点开始]"  action="doUpdate|doDelete|doStart" >操作</th>
<!--							<th colwidth="500" type="link" title="[编辑]|[删除]|[盘点开始]|[盘点结束]"  action="doUpdate|doDelete|doStart|doOver" >操作</th>-->
<!--							<th colwidth="85" type="link" title="[编辑]|[删除]"  action="doUpdate|doDelete" >操作</th>-->
						</tr>
					</thead>					
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>