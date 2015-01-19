<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>发票签收</title>
<script type="text/javascript">
//查询按钮响应方法
$(function(){
	$("#search").click(function(){
		if($("#qslb").is(":hidden")){
			$("#qslb").show();
			$("#qslb").jTable();
		}
	});
});
function doSave(){
	alertMsg.info("签收成功！");
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：财务管理&gt;开票管理&gt;发票签收</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="fpqsform" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="fpqsTable">
					<tr>
						<td><label>订单号：</label></td>
						<td><input type="text" id="DDH" name="DDH" datatype="1,is_null,100" value="" /></td>
						<td><label>快递单号：</label></td>
						<td><input type="text" id="KDDH" name="KDDH" datatype="1,is_null,100" value="" /></td>
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
		<div id="qs">
			<table width="100%" id="qslb" name="qslb" style="display: none" >
				<thead>
					<tr>
						<th  name="XH" style="display:">序号</th>
						<th>订单号</th>
						<th align="right">金额(元)</th>
						<th >开票单号</th>
						<th >开票金额</th>
						<th >开票日期</th>
						<th >快递单号</th>
						<th colwidth="85" type="link" title="[签收]"  action="doSave">操作</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>1</td>
						<td>订单1</td>
						<td>400.00</td>
						<td>开票单号1</td>
						<td>400.00</td>
						<td>2014-06-03</td>
						<td>快递单号1</td>
						<td><a href="#" onclick="doSave()" class="op">[签收]</a></td>
					</tr>
					<tr>
						<td>2</td>
						<td>订单2</td>
						<td>400.00</td>
						<td>开票单号2</td>
						<td>400.00</td>
						<td>2014-06-03</td>
						<td>快递单号2</td>
						<td><a href="#" onclick="doSave()" class="op">[签收]</a></td>
					</tr>
					<tr>
						<td>3</td>
						<td>订单3</td>
						<td>400.00</td>
						<td>开票单号3</td>
						<td>400.00</td>
						<td>2014-06-03</td>
						<td>快递单号3</td>
						<td><a href="#" onclick="doSave()" class="op">[签收]</a></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>