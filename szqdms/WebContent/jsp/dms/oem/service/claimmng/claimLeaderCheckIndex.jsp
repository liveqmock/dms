<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>主管领导签批</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/claimmng/ClaimLeaderCheckAction/claimSearch.ajax";
$(function(){
	$("#search").bind("click",function(event){
		var $f = $("#fm-claimCheck");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"search",1,sCondition,"claimCheckList");
	});
});
//审批
function doUpdate(rowobj){
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/oem/service/claimmng/claimLeaderCheckDetail.jsp", "checkDetail", "索赔单领导审批", options,true);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 索赔管理  &gt; 索赔管理   &gt; 主管领导签批</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-claimCheck">
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-claimCheck">
					<tr>
						<td><label>索赔单号：</label></td>
					    <td><input type="text" id="claimNo" name="claimNo" datasource="CLAIM_NO" datatype="1,is_digit_letter,30" operation="like" /></td>
					    <td><label>索赔单类型：</label></td>
					    <td>
					    	<select type="text" id="claimType" name="claimType" class="combox" kind="dic" src="SPDLX" datasource="T.CLAIM_TYPE" datatype="1,is_null,30" value="">
								<option value="-1" >--</option>
							</select>
					    </td>
					</tr>
					<tr>
					    <td><label>派工单号：</label></td>
					    <td><input type="text" id="workNo" name="workNo" datasource="O.WORK_NO" datatype="1,is_digit_letter,30" operation="like" /></td>
				    	<td><label>提报日期：</label></td>
						<td>
						     <input type="text" group="applyDateStart,applyDateEnd"  id="applyDateStart" kind="date" name="applyDateStart" style="width:75px;" operation=">=" datasource="T.APPLY_DATE" datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
						     <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
						     <input type="text" group="applyDateStart,applyDateEnd"  id="applyDateEnd" kind="date" name="applyDateEnd" style="width:75px;margin-left:-30px;" operation="<=" datasource="T.APPLY_DATE" datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
						</td>
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
		<div id="claimCheck" >
			<table style="display:none;width:100%;" id="claimCheckList" name="claimCheckList" ref="claimCheck" refQuery="tab-claimCheck" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="WORK_NO" colwidth="120">派工单号</th>
							<th fieldname="CLAIM_NO" colwidth="120">索赔单号</th>
							<th fieldname="CLAIM_TYPE" >索赔类型</th>
							<th fieldname="CLAIM_STATUS" >索赔单状态</th>
							<th fieldname="APPLY_DATE" colwidth="120">提报时间</th>
							<th colwidth="65" type="link" title="[审批]"  action="doUpdate" >操作</th>
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