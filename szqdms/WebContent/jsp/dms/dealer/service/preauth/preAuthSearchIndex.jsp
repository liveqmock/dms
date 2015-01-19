<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>预授权查询</title>
<script type="text/javascript">
//变量定义
//查询提交方法
var searchUrl = "<%=request.getContextPath()%>/service/preauthMng/PreAuthSearchAction/preAuthDrlSearch.ajax";
var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//初始化方法
$(function(){
	//查询方法
	$("#search").bind("click",function(event){
		var $f = $("#fm-preAuth");
		if (submitForm($f) == false) {
			return false;
		}
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"search",1,sCondition,"preAuthlist");
	});
});
//明细
function doDetail(rowobj)
{
	var authorId=$(rowobj).attr("AUTHOR_ID");
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps + "/jsp/dms/dealer/service/preauth/preAuthDetail.jsp?authorId="+authorId, "preAuthApply", "预授权明细", options,true);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 索赔管理  &gt; 预授权管理   &gt; 预授权查询</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-preAuth">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-preAuth">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>预授权单号：</label></td>
					    <td><input type="text" id="preAuthNo" name="preAuthNo" datasource="AU.AUTHOR_NO" datatype="1,is_null,30" operation="like" /></td>
					    <td><label>发动机号：</label></td>
					    <td><input type="text" id="engineno" name="engineno" datasource="VH.ENGINE_NO" datatype="1,is_null,30" operation="like" /> </td>
						<td><label>预授权状态：</label></td>
						<td>
							<select type="text" id="authorStatus" name="authorStatus" datasource="AU.AUTHOR_STATUS" kind="dic" src="YSQZT" datatype="1,is_null,30" value="">
									<option value="-1" selected>--</option>
							</select>
						</td>
					</tr>
					<tr>
						<td ><label>VIN：</label></td> 
					    <td> <input type="text" name="vin" id="vin"  datasource="VH.VIN" datatype="1,is_null,30" operation="like"/></td> 
				    	<td><label>提报日期：</label></td>
					    <td colspan="5">
				    		<input type="text" group="reportDateStart,reportDateEnd"  id="reportDateStart" kind="date"  name="reportDateStart" style="width:75px;" operation=">=" datasource="AU.REPORT_DATE" datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="reportDateStart,reportDateEnd"  id="reportDateEnd" kind="date"  name="reportDateEnd" style="width:75px;margin-left:-30px;" operation="<=" datasource="AU.REPORT_DATE" datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
				   		 </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search" >查&nbsp;&nbsp;询</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="preAuth" >
			<table style="display:none;width:100%;" layoutH="250" id="preAuthlist" name="preAuthlist" ref="preAuth" refQuery="tab-preAuth" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="AUTHOR_NO" ordertype='local' class="desc">预授权单号</th>
							<th fieldname="AUTHOR_STATUS">预授权状态</th>
							<th fieldname="VIN" >VIN</th>
							<th fieldname="ENGINE_NO" >发动机号</th>
							<th fieldname="MODELS_CODE" >车辆型号</th>
							<th fieldname="LICENSE_PLATE" >车牌号</th>
							<th fieldname="BUY_DATE" >销售日期</th>
							<th fieldname="REPORT_DATE" ordertype='local' class="desc">提报日期</th>
							<th fieldname="IF_APPLYCLAIM" colwidth="90" >是否已报索赔单</th>
							<th colwidth="45" type="link" title="[明细]"  action="doDetail" >操作</th>
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