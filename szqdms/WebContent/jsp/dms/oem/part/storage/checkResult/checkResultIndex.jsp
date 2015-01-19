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
<script type="text/javascript">
var Url = "<%=request.getContextPath()%>/part/basicInfoMng/PtBuInventoryAction/searchResult.ajax";
var deleteUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PtBuInventoryAction/delete.ajax";
//初始化方法
$(function(){
	//查询方法
	$("#btn-cx").bind("click",function(event){
//		if($("#tab-list").is(":hidden"))
//		{
//			$("#tab-list").show();
//			$("#tab-list").jTable();
//		}
		var $f = $("#fm-ccpd");
		var sCondition = {};
    	sCondition = $f.combined() || {};    	
		doFormSubmit($f,Url,"btn-cx",1,sCondition,"tab-list");
	});
	
});

function doUpdate(rowobj){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps + "/jsp/dms/oem/part/storage/checkResult/checkResultAdd.jsp", "fldmx", "盘点结果调整", options);
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
	$.pdialog.open(webApps+"/jsp/dms/oem/part/storage/checkResult/checkResultDetail.jsp", "pdmx", "盘点结果调整明细", options);
}

</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 仓储管理  &gt; 库存盘点  &gt; 库存盘点结果调整</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-ccpd">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-pkcx">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>盘点编号：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh" datasource="INVENTORY_NO" datatype="1,is_digit_letter,300"  operation="like" /></td>
					    <td><label>盘点仓库：</label></td>
					    <td>
					    	<input type="text" id="in-ddlx" name="in-ddlx" kind="dic" src="T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME:1=1 AND STATUS=<%=DicConstant.YXBS_01 %> ORDER BY WAREHOUSE_CODE" datasource="WAREHOUSE_CODE" datatype="1,is_null,300" />				    
					    </td>
					    <td><label>盘点类型：</label></td>
					    <td>
					    	<input type="text" id="in-ddlx" name="in-ddlx" kind="dic" src="PDLX" datasource="INVENTORY_TYPE" datatype="1,is_null,300" operation="="/>
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
<!--						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-xz" >新&nbsp;&nbsp;增</button></div></div></li>-->
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
<!--							<th fieldname="INVENTORY_NO" colwidth="135">盘点编号</th>-->
							<th fieldname="INVENTORY_NO" refer="showLink">盘点编号</th>
							<th fieldname="WAREHOUSE_NAME">盘点仓库</th>
							<th fieldname="INVENTORY_TYPE">盘点类型</th>
							<th fieldname="INVENTORY_DATE">盘点日期</th>
							<th fieldname="INVENTORY_STATUS">盘点状态</th>
							<th fieldname="INVENTORY_USER">盘点人</th>
							<th fieldname="CHECK_REMARKS">审核意见</th>
							<th colwidth="85" type="link" title="[盘点结果调整]"  action="doUpdate" >操作</th>
						</tr>
					</thead>		
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>