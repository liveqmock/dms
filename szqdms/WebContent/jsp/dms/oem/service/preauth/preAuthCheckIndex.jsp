<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>预授权审核</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/preauthMng/PreAuthCheckAction/preAuthCheckSearch.ajax";
var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//初始化方法
$(function(){
	$("#search").bind("click",function(){
		var $f = $("#fm-preAuth");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"search",1,sCondition,"preAuthlist");
	});
	$("#btn-reset").bind("click", function(event){
		$("#fm-preAuth")[0].reset();
		$("#orgCode").attr("code","");
		$("#orgCode").val("");
	});
});
//列表编辑链接(审核)
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps + "/jsp/dms/oem/service/preauth/PreAuthCheckDetail.jsp", "preAuthCheck", "预授权审核信息", options,true);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 索赔管理  &gt; 预授权管理   &gt; 预授权审核</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-preAuth">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-preAuth">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>渠道商代码：</label></td>
						<td><input type="text" id="orgCode" name="orgCode" datasource="AU.ORG_ID" datatype="1,is_null,10000" operation="in" value="" hasBtn="true" callFunction="showOrgTree('orgCode',2)" readonly="readonly"/></td>
						<td><label>预授权单号：</label></td>
					    <td><input type="text" id="preAuthNo" name="preAuthNo" datasource="AU.AUTHOR_NO" datatype="1,is_null,30" operation="like" /></td>
					</tr>
					<tr>
					    <td ><label>VIN：</label></td> 
					    <td> <input type="text" name="vin" id="vin"  datasource="VH.VIN" datatype="1,is_null,30" operation="like"/></td> 
					    <td><label>发动机号：</label></td>
					    <td><input type="text" id="engineno" name="engineno" datasource="VH.ENGINE_NO" datatype="1,is_null,30" operation="like" /> </td>
					    <td><label>提报日期：</label></td>
					    <td colspan="5">
				    		<input type="text" group="reportDateStart,reportDateEnd"  id="reportDateStart"  kind="date" name="reportDateStart" style="width:75px;" operation=">=" datasource="AU.REPORT_DATE" datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="reportDateStart,reportDateEnd"  id="reportDateEnd" kind="date" name="reportDateEnd" style="width:75px;margin-left:-30px;" operation="<=" datasource="AU.REPORT_DATE" datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
				   		 </td>
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
		<div id="page_preAuth" >
			<table style="display:none;width:100%;" id="preAuthlist" name="preAuthlist" ref="page_preAuth" refQuery="tab-preAuth" >
					<thead>
						<tr>
							<th fieldname="ROWNUMS" style="display:" colwidth="20"></th>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="ORG_CODE" >渠道商代码</th>
							<th fieldname="ORG_NAME" >渠道商名称</th>
							<th fieldname="AUTHOR_NO"  ordertype='local' class="desc">预授权单号</th>
							<th fieldname="VIN">VIN</th>
							<th fieldname="ENGINE_NO" >发动机号</th>
							<th fieldname="MODELS_CODE" >车辆型号</th>
							<th fieldname="LICENSE_PLATE" colwidth="80">车牌号</th>
							<th fieldname="BUY_DATE" colwidth="80">销售日期</th>
							<th fieldname="REPORT_DATE" colwidth="150"  ordertype='local' class="desc">提报日期</th>
							<th colwidth="40" type="link" title="[审核]"  action="doUpdate" >操作</th>
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