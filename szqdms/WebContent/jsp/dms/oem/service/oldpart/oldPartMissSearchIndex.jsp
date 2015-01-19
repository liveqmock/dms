<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.org.framework.util.Pub"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>       
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<%
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
    String year = yearFormat.format(Pub.getCurrentDate());
    String monthDate = monthFormat.format(Pub.getCurrentDate());
    Integer month = Integer.valueOf(monthDate);
    String date = year + "-" + String.valueOf(month);
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
  	String orgId=user.getOrgId();
%>
<title>回运单查询</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/oldpartMng/OldPartSearchAction/oldPartMissSearch.ajax";
var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//查询按钮响应方法
$(function(){
	$("#search").bind("click",function(){
		var $f = $("#oldPartform");
		var sCondition = {};
  		sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"search",1,sCondition,"oldPartList");
	});
	 //重置按钮
	$("#btn-reset").bind("click", function(event){
		$("#oldPartform")[0].reset();
		$("#orgCode").attr("code","");
		$("#orgCode").val("");
	});
	//下载
	$("#btn-download").bind("click",function(){
		var $f = $("#oldPartform");
		if (submitForm($f) == false) return false;
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/service/oldpartMng/OldPartSearchAction/missPartDownload.do");
		$("#exportFm").submit();
	});
});
//回运单号反回 a标签
function doOpenDetail(obj){
	$tr=$(obj).parent();
	return "<a href='#' onclick='doOldPartDetail("+$tr.attr("ORDER_ID")+")' class='op'>"+$tr.attr("ORDER_NO")+"</a>";
}
//打开连接明细
function doOldPartDetail(orderId){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/dealer/service/oldpart/oldPartDetail.jsp?orderId="+orderId, "oldPart", "旧件回运明细", options,true);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：旧件管理&gt;旧件管理&gt;回运单查询</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="oldPartform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="oldPartTable">
						<tr>
							<td><label>渠道商代码：</label></td>
							<td><input type="text" id="orgCode" name="orgCode" datasource="G.CODE" datatype="1,is_null,10000" operation="like" value="" /></td>
							<td><label>旧件产生月份：</label></td>
							<td><input type="text" id="in-shrq"  name="in-shrq" operation=""  dataSource="PRODUCE_DATE" style="width:75px;"   datatype="0,is_null,30" onclick="WdatePicker({maxDate:'<%=date%>',dateFmt:'yyyy-MM'})" /></td>
							<td><label>旧件状态：</label></td>
							<td><select type="text" id="oldPartStatus" name="oldPartStatus" datasource="OLD_PART_STATUS" datatype="1,is_null,6" class="combox" kind="dic" src="JJZT">
									<option value=-1>--</option>
								</select>
							</td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-reset">重&nbsp;&nbsp;置</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-download">下&nbsp;&nbsp;载</button></div></div></li>
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
							<th  fieldname="ORDER_NO" refer="doOpenDetail" ordertype="local" class="desc">回运单号</th>
							<th  fieldname="PRODUCE_DATE">旧件产生月份</th>
							<th  fieldname="CLAIM_NO">索赔单号</th>
							<th  fieldname="ORG_CODE">渠道商代码</th>
							<th  fieldname="ORG_NAME">渠道商名称</th>
							<th  fieldname="PART_CODE">配件代码</th>
							<th  fieldname="PART_NAME">配件名称</th>
							<th  fieldname="PROSUPPLY_CODE">供应商代码</th>
							<th  fieldname="PROSUPPLY_NAME">供应商名称</th>
							<th  fieldname="SHOULD_COUNT">应返件数</th>
							<th  fieldname="OUGHT_COUNT">实返件数</th>
							<th  fieldname="MISS_COUNT">漏返件数</th>
							<th  fieldname="OLD_PART_STATUS">旧件状态</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	 <form id="exportFm" method="post" style="display:none">
		<input type="hidden" id="params" name="data"></input>
	</form>
</div>
</body>
</html>