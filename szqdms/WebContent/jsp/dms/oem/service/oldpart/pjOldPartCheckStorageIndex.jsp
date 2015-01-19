<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>旧件终审</title>
<script type="text/javascript">

// 终审查询
var searchUrl = "<%=request.getContextPath()%>/service/oldpartMng/OldPartPJCheckStorageAction/checkStorageSearch.ajax";

// 查询按钮响应方法
$(function(){
	
	// 查询回运数据
	$("#search").bind("click",function(){
		var $f = $("#oldpartform");
		var sCondition = {};
  		sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"search",1,sCondition,"oldPartList");
	});
	
});
var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
function doUpdate(rowobj){
	$("td input:first",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps + "/jsp/dms/oem/service/oldpart/pjOldPartCheckStorageDetail.jsp", "", "旧件终审", options,true);
}


</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：旧件管理&gt;配件管理&gt;旧件终审</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="oldpartform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="oldpartTable">
						<tr>
							<td><label>箱号：</label></td>
							<td><input type="text" id="BOX_NO" name="BOX_NO" datasource="BOX_NO" datatype="1,is_null,30" operation="like" value="" /></td>
							<td><label>配件代码：</label></td>
							<td><input type="text" id="PART_CODE" name="PART_CODE" datasource="PART_CODE" datatype="1,is_null,30" operation="like"  value="" /></td>
							<td><label>配件名称：</label></td>
							<td><input type="text" id="PART_NAME" name="PART_NAME" datasource="PART_NAME" datatype="1,is_null,30"  operation="like" value="" /></td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div id="oldpart">
				<table style="display:none;width:100%;" id="oldPartList" name="oldPartList" ref="oldpart" refQuery="oldpartTable" >
					<thead>
						<tr>
							<th type="multi" name="XH" unique="APPLY_ID" style="display:none"></th>
							<th fieldname="APPLY_NO"  ordertype='local' class="desc" colwidth="120px">申请单号</th>
							<th fieldname="BOX_NO"  ordertype='local' class="desc">箱号</th>
							<th fieldname="RETURN_DATE"  ordertype='local' class="desc">回运日期</th>
							<th fieldname="PART_CODE">配件代码</th>
							<th fieldname="PART_NAME">配件名称</th>
							<th fieldname="CLAIM_COUNT" align="right" >索赔数量</th>
							<th fieldname="ORG_CODE">渠道商代码</th>
							<th fieldname="ORG_NAME">渠道商名称</th>
							<th fieldname="SUPPLIER_CODE">供应商代码</th>
							<th fieldname="SUPPLIER_NAME">供应商名称</th>
							<th fieldname="FAULT_CONDITONS">故障情况</th>
							<th fieldname="IN_COUNT" style="display:none"></th>
							<th fieldname="ADUIT_REMARKS" style="display:none"></th>
							<th colwidth="45" type="link"  title="[审核]" action="doUpdate">操作</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<form id="applyIdForm" method="post" style="display:none">
		<input type="hidden" id="applyIds" name="applyIds" />
</form>
</body>
</html>