<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.org.framework.util.Pub"%>
<%@page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>终审查询</title>
<%
	SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
	SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
	String year = yearFormat.format(Pub.getCurrentDate());
	String monthDate = monthFormat.format(Pub.getCurrentDate());
    Integer month = Integer.valueOf(monthDate);
	String date = year + "-" + String.valueOf(month);
%>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/oldpartMng/OldPartCheckStorageAction";
var passUrl = "<%=request.getContextPath()%>/service/oldpartMng/OldPartCheckStorageAction";
var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//查询按钮响应方法
$(function(){
	$("#search").bind("click",function(){
		var $f = $("#oldPartform");
		var claimNo=$("#claimNo").val();
		var dealerCode=$("#dealeOrgCode").val();
		var sCondition = {};
  		sCondition = $f.combined() || {};
  		var url =searchUrl+"/oldPartCheckedSearch.ajax?&claimNo="+claimNo+"&dealerCode="+dealerCode;
		doFormSubmit($f,url,"search",1,sCondition,"oldPartList");
	});
	$("#btn-reset").bind("click", function(event){
		$("#oldPartform")[0].reset();
		$("#orgCode").attr("code","");
		$("#orgCode").val("");
	});
});
function doUpdate(rowobj){
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps + "/jsp/dms/oem/service/oldpart/oldPartCheckSearchDetail.jsp", "oldPartCheck", "旧件明细", options,true);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：旧件管理&gt;旧件管理&gt;旧件终审</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="oldPartform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="oldPartTable">
						<tr>
							<td><label>旧件集中点名称：</label></td>
							<td><input type="text" id="R_ORGCODE" name="R_ORGCODE" datasource="T.CODE" kind="dic"   src="T#TM_ORG:CODE:ONAME{ORG_ID}:1=1 AND STATUS='100201' AND IS_IC = 100101" operation="like"  datatype="1,is_null,100" value="" /></td>
							<td><label>回运单号：</label></td>
							<td><input type="text" id="orederNo" name="orederNo"  datasource="O.ORDER_NO" operation="like" datatype="1,is_null,100"  value="" /></td>
							<td><label>运输方式：</label></td>
							<td><select  type="text" id="transType" name="transType" datasource="O.TRANS_TYPE" kind="dic" class="combox" src="HYDYSFS"  datatype="1,is_null,6" value="" >
									<option value="-1" selected>--</option>
								</select>
							</td>
						</tr>
						<tr>
							<td><label>索赔单号：</label></td>
							<td><input type="text" id="claimNo" name="claimNo" datatype="1,is_null,100"  value="" /></td>
							<td><label>渠道商代码：</label></td>
							<td><input type="text" id="dealeOrgCode" name="dealeOrgCode" datatype="1,is_null,100"  value="" /></td>
							<td><label>旧件产生年月：</label></td>
							<td><input type="text" id="produceDate" name="produceDate" datasource="PRODUCE_DATE" style="width:75px;"   onclick="WdatePicker({maxDate:'<%=date%>',dateFmt:'yyyy-MM'})" datatype="0,is_null,30" /></td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-reset">重&nbsp;&nbsp;置</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div id="oldPart">
				<table style="display:none;width:100%;"  id="oldPartList" name="oldPartList" ref="oldPart" refQuery="oldPartTable" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th  fieldname="PCODE">办事处代码</th>
							<th  fieldname="PNAME">办事处名称</th>
							<th  fieldname="ORG_CODE">渠道商代码</th>
							<th  fieldname="ORG_NAME">渠道商名称</th>
							<th  fieldname="ORDER_NO" ordertype="local" class="desc">回运单号</th>
							<th  fieldname="PARTCOUNT">回运旧件数量</th>
							<th  fieldname="AMOUNT">装箱总数</th>
							<th  fieldname="TRANS_TYPE">运输方式</th>
							<th  fieldname="RETURN_DATE">回运日期</th>
							<th colwidth="45" type="link"  title="[明细]" action="doUpdate">操作</th>
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