<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>三包急件查询</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/guaranteesHurryPart/GuaranteesHurryPartAction/dispatchDelSearch.ajax";
//查询按钮响应方法
$(function(){
	//查询方法
	$("#search").bind("click",function(event){
		var $f = $("#dispatchPartform");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"search",1,sCondition,"dispatchPartlist");
	});
});
function hyperlink(obj)
{
	var $row=$(obj).parent();
    return "<a href='#' onclick=doOpen("+$row.attr("DISPATCH_ID")+") class='op'>"+$row.attr("DISPATCH_NO")+"</a>";
}
function doOpen(dispatchId){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/dealer/service/guaranteesHurryPart/guaranteesHurryPartDetile.jsp?dispatchId="+dispatchId+"", "sbjjmx", "三包急件明细", options,true);
} 
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：三包急件管理&gt;三包急件管理&gt;三包急件查询</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="dispatchPartform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="dispatchPartTable">
						<tr>
							<td><label>急件编号：</label></td>
							<td><input type="text" id="dispatchNo" name="dispatchNo" datasource="DISPATCH_NO" datatype="1,is_null,100" value=""  operation="like" /></td>
							<td><label>申请状态：</label></td>
							<td><select type="text" id="dispatchStatus" name="dispatchStatus" datasource="DISPATCH_STATUS" datatype="1,is_null,100" operation="like"  class="combox" kind="dic" src="SBJJSQZT">
									<option value=-1>--</option>
								</select>
							</td>
						</tr>	
						<tr>
							<td><label>申请日期：</label></td>
						    <td colspan="3">
					    		<input type="text" id="in-ckrq" style="width:75px;" name="in-ckrq" datasource="APPLY_DATE" datatype="1,is_null,30" onclick="WdatePicker()" kind="date" operation = ">=" />
					    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
								<input type="text" id="in-jsrq" style="width:75px;margin-left:-30px;" name="in-ckrq" datasource="APPLY_DATE" datatype="1,is_null,30" kind="date" onclick="WdatePicker()" operation = "<=" />
					   		 </td>
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
		<div id="preAuth" >
			<table style="display:none;width:100%;" layoutH="250" id="dispatchPartlist" name="dispatchPartlist" ref="preAuth" refQuery="dispatchPartTable" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="DISPATCH_NO" refer="hyperlink">急件编号</th>
							<th fieldname="DISPATCH_STATUS" >申请状态</th>
							<th fieldname="APPLY_DATE" >申请日期</th>
							<th fieldname="CHECK_REARKS" >驳回原因</th>
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