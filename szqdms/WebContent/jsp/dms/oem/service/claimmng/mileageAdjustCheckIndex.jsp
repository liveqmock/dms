<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>里程调整审核</title>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/claimmng/MileageAdjustCheckAction/claimSearch.ajax";
$(function(){
	$("#search").bind("click",function(event){
		var $f = $("#fm-mileageAdjust");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"search",1,sCondition,"mileageAdjustList");
	});
	$("#btn-reset").bind("click", function(event){
		$("#fm-mileageAdjust")[0].reset();
		$("#orgCode").attr("code","");
		$("#orgCode").val("");
	});
});
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/oem/service/claimmng/mileageAdjustCheckDetail.jsp", "mileageAdjust", "里程调整审核", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 索赔管理  &gt; 索赔管理   &gt; 里程调整审核</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-mileageAdjust">
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-mileageAdjust">
					<tr>
						<td><label>渠道商代码：</label></td>
						<td><input type="text" id="orgCode" name="orgCode" datasource="T.ORG_ID" datatype="1,is_null,10000" operation="in" value="" hasBtn="true" callFunction="showOrgTree('orgCode',2)" readonly="readonly"/></td>
					    <td><label>索赔单号：</label></td>
					    <td><input type="text" id="claimNo" name="claimNo" datasource="T.CLAIM_NO" datatype="1,is_digit_letter,30" operation="like" /></td>
					 	<td ><label>VIN：</label></td> 
					    <td> <input type="text" name="vin" id="vin"  datasource="T.VIN" datatype="1,is_null,30" operation="like"/></td> 
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-reset">重&nbsp;&nbsp;置</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="mileageAdjust" >
			<table style="display:none;width:100%;" id="mileageAdjustList" name="mileageAdjustList" ref="mileageAdjust" refQuery="tab-mileageAdjust" >
				<thead>
					<tr>
						<th type="single" name="XH" style="display:none;"></th>
						<th fieldname="ORG_CODE">渠道商代码</th>
						<th fieldname="ORG_NAME" >渠道商名称</th>
						<th fieldname="CLAIM_NO" ordertype='local' class="desc" colwidth="120" >索赔单号</th>
						<th fieldname="VIN" colwidth="120">VIN</th>
						<th fieldname="MILEAGE" >原里程</th>
						<th fieldname="ENSURE_MILEAGE" >新里程数</th>
						<th fieldname="MILEAGE_APPLY_REASON" colwidth="120" maxlength="30">申请原因</th>
						<th colwidth="65" type="link" title="[审核]"  action="doUpdate" >操作</th>
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