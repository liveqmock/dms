<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>旧件修改</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/oldpartMng/OldPartUpdateApplyAction/oldPartOemSearch.ajax";
var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//初始化方法
$(function(){
	//查询方法 查询索赔单通过的、返旧件的、没结算的
	$("#search").bind("click",function(event){
		var $f = $("#fm-oldPart");
		var sCondition = {};
  		sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"search",1,sCondition,"oldPartList");
	});
});
//列表编辑链接(修改)
var $row;
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$row=$(rowobj);
	$.pdialog.open(webApps + "/jsp/dms/oem/service/oldpart/oldUpdateDetail.jsp", "oldPartUpdate", "旧件修改", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 旧件管理  &gt; 旧件管理   &gt; 旧件修改</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-oldPart">
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-oldPart">
					<tr>
						<td><label>渠道商代码：</label></td>
						<td><input type="text" id="orgCode" name="orgCode" datasource="C.ORG_ID" datatype="1,is_null,10000" operation="in" value="" hasBtn="true" callFunction="showOrgTree('orgCode',2)" readonly="readonly"/></td>
						<td><label>索赔单号：</label></td>
					    <td><input type="text" id="claimNo" name="claimNo" datasource="D.CLAIM_NO"  datatype="1,is_null,30" operation="like" /></td>
					</tr>
					<tr>
						<td><label>配件代码：</label></td>
					    <td><input type="text" id="partCode" name="partCode" datasource="D.PART_CODE"  datatype="1,is_null,30" operation="like" /></td>
						<td><label>配件名称：</label></td>
						<td><input type="text" id="partName" name="partName" datasource="D.PART_NAME"  datatype="1,is_null,30" operation="like" /></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="button"><div class="buttonContent"><button type="button" id="search" >查&nbsp;&nbsp;询</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="oldPart" >
			<table style="display:none;width:100%;" id="oldPartList" name="oldPartList" ref="oldPart" refQuery="tab-oldPart" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="ORG_CODE">渠道商代码</th>
							<th fieldname="ORG_NAME">渠道商名称</th>
							<th fieldname="CLAIM_NO">索赔单号</th>
							<th fieldname="PART_CODE">配件代码</th>
							<th fieldname="PART_NAME">配件名称</th>
							<th fieldname="ACTUL_COUNT">配件数量</th>
							<th fieldname="PROSUPPLY_CODE">供应商代码</th>
							<th fieldname="PROSUPPLY_NAME">供应商名称</th>
							<th colwidth="105" type="link" title="[编辑]"  action="doUpdate" >操作</th>
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