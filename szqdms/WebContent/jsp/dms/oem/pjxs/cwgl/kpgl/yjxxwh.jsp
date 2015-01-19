<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>邮寄信息维护</title>
<script type="text/javascript">
//查询按钮响应方法
$(function(){
	$("#search").click(function(){
		if($("#yjlb").is(":hidden")){
			$("#yjlb").show();
			$("#yjlb").jTable();
		}
	});
});
function doMail(){
	var options = {max:false,width:1050,height:320,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/pjxs/cwgl/kpgl/yjxxmx.jsp", "yjmx", "邮寄信息", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：财务管理&gt;开票管理&gt;邮寄信息维护</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="yjxxwhform" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="yjxxwhTable">
					<tr>
						<td><label>配送中心代码：</label></td>
						<td><input type="text" id="WFSDM" name="WFSDM" datatype="1,is_null,100" value="" /></td>
						<td><label>配送中心名称：</label></td>
						<td><input type="text" id="WFSMC" name="WFSMC" datatype="1,is_null,100" value="" /></td>
						<td><label>订单号：</label></td>
						<td><input type="text" id="DDH" name="DDH" datatype="1,is_null,100" value="" /></td>
					</tr>	
					<tr>
						<td><label>开票单号：</label></td>
						<td><input type="text" id="KPDH" name="KPDH" datatype="1,is_null,100" value="" /></td>
						<td><label>开票日期：</label></td>
						<td><input type="text" id="KPRQ" name="KPRQ" datatype="1,is_null,100" value=""  onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" /></td>
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
		<div id="yj">
			<table width="100%" id="yjlb" name="yjlb" style="display: none" >
				<thead>
					<tr>
						<th  name="XH" style="display:">序号</th>
						<th>配送中心代码</th>
						<th>配送中心名称</th>
						<th>订单号</th>
						<th>开票状态</th>
						<th align="right">金额(元)</th>
						<th >开票单号</th>
						<th >开票金额</th>
						<th >开票日期</th>
						<th colwidth="85" type="link" title="[邮寄]"  action="doMail">操作</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>1</td>
						<td>配送中心代码1</td>
						<td>配送中心名称1</td>
						<td>订单1</td>
						<td>已开票</td>
						<td>400.00</td>
						<td>开票单号1</td>
						<td>400.00</td>
						<td>2014-06-03</td>
						<td><a href="#" onclick="doMail()" class="op">[邮寄]</a></td>
					</tr>
					<tr>
						<td>2</td>
						<td>配送中心代码2</td>
						<td>配送中心名称2</td>
						<td>订单2</td>
						<td>已开票</td>
						<td>400.00</td>
						<td>开票单号2</td>
						<td>400.00</td>
						<td>2014-06-03</td>
						<td><a href="#" onclick="doMail()" class="op">[邮寄]</a></td>
					</tr>
					<tr>
						<td>3</td>
						<td>配送中心代码3</td>
						<td>配送中心名称3</td>
						<td>订单3</td>
						<td>已开票</td>
						<td>400.00</td>
						<td>开票单号3</td>
						<td>400.00</td>
						<td>2014-06-03</td>
						<td><a href="#" onclick="doMail()" class="op">[邮寄]</a></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>