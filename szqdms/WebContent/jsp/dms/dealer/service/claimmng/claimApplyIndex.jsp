<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>索赔单提报</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/claimmng/WorkOrderMngAction/searchClaimApply.ajax";
var deleteUrl = "<%=request.getContextPath()%>/service/claimmng/WorkOrderMngAction/deleteClaim.ajax";
$(function(){
	$("#search").bind("click",function(event){
		var $f = $("#fm-claimApply");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"search",1,sCondition,"tab-workOrderList");
	});
});
var $row;
var workClaimId;
function doUpdate(rowobj)
{
	$row = $(rowobj);
	workClaimId=$(rowobj).attr("CLAIM_ID");
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/dealer/service/claimmng/claimAdd.jsp?action=2&rejectFlag=2", "cliamAdd", "索赔单信息维护", options,true);
}
var $delRow ;
function doDelete(rowobj){
	var claimId=$(rowobj).attr("CLAIM_ID");
	var workId=$(rowobj).attr("WORK_ID");
	$delRow=$(rowobj);
	var url = deleteUrl + "?claimId="+claimId+"&workId="+workId;
	sendPost(url,"","",deleteCallBack,"true");
}
//删除回调方法
function  deleteCallBack(res)
{
	try
	{
		if($delRow) 
			$("#tab-workOrderList").removeResult($delRow);
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}

//索赔单明细
function claimDetail(obj){
	var $tr=$(obj).parent();
	return "<a href='#' onclick=showClaimDetail("+$tr.attr("CLAIM_ID")+") class='op'>"+$tr.attr("CLAIM_NO")+"</a>";
}
function showClaimDetail(claimId){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/common/claimDetail.jsp?claimId="+claimId, "claimDetail", "索赔单明细", options,true);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 索赔管理  &gt; 索赔管理   &gt; 索赔单提报</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-claimApply">
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-claimApply">
					<tr>
						<td><label>索赔单号：</label></td>
					    <td><input type="text" id="claimNo" name="claimNo" datasource="C.CLAIM_NO" datatype="1,is_digit_letter,30" operation="like" /></td>
					    <td><label>索赔单状态：</label></td>
					    <td>
					    	<select type="text" id="claimStatus"  name="claimStatus" datasource="C.CLAIM_STATUS" kind="dic" src="SPDZT" filtercode="301004|301006" datatype="1,is_null,30" >
					    		<option value="-1" selected>--</option>
					    	</select>
					    </td>
					</tr>
					<tr>
					    <td><label>派工单号：</label></td>
					    <td><input type="text" id="workNo" name="workNo"  datasource="T.WORK_NO" datatype="1,is_digit_letter,30" operation="like" /></td>
				    	<td><label>提报日期：</label></td>
					    <td>
				    		<input type="text" group="reportDateStart,reportDateEnd"  id="reportDateStart" kind="date" name="reportDateStart" style="width:75px;" operation=">=" datasource="C.APPLY_DATE" datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="reportDateStart,reportDateEnd"  id="reportDateEnd" kind="date" name="reportDateEnd" style="width:75px;margin-left:-30px;" operation="<=" datasource="C.APPLY_DATE" datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
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
		<div id="claimApply" >
			<table style="display:none;width:100%;" id="tab-workOrderList" name="tab-workOrderList" ref="claimApply" refQuery="tab-claimApply" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="WORK_NO" >派工单号</th>
							<th fieldname="CLAIM_NO" ordertype='local' class="desc" refer="claimDetail">索赔单号</th>
							<th fieldname="CLAIM_STATUS" >索赔单状态</th>
							<th fieldname="APPLY_COUNT" >提报次数</th>
							<th fieldname="APPLY_DATE" ordertype='local' class="desc">提报时间</th>
							<th fieldname="REJECT_DATE" >驳回时间</th>
							<th colwidth="145" type="link" title="[编辑]|[删除]"  action="doUpdate|doDelete" >操作</th>
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