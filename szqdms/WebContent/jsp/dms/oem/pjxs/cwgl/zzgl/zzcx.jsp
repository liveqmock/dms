<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>转账查询</title>
<script type="text/javascript">
//查询按钮响应方法
$(function(){
	$("#search").click(function(){
		if($("#zzcxlb").is(":hidden")){
			$("#zzcxlb").show();
			$("#zzcxlb").jTable();
		}
	});
	
});
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：财务管理&gt;转账管理&gt;转账查询</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="zzshform" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="zzshTable">
					<tr>
						<td><label>服务商代码：</label></td>
						<td><input type="text" id="WFSDM" name="WFSDM" datatype="1,is_null,100" value="" readOnly hasBtn="true" callFunction="open();"/></td>
						<td><label>服务商名称：</label></td>
						<td><input type="text" id="WFSMC" name="WFSMC" datatype="1,is_null,100" value="" /></td>
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
		<div id="zzcx">
			<table width="100%" id="zzcxlb" name="zzcxlb" style="display: none" >
				<thead>
					<tr>
						<th>服务商代码</th>
						<th>服务商名称</th>
						<th>转出账户类型</th>
						<th>转入账户类型</th>
						<th>申请日期</th>
						<th>转账日期</th>
						<th align="right">转账金额</th>
						<th>转账原因</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>服务商代码1</td>
						<td>服务商名称1</td>
						<td>返利账户</td>
						<td>现金账户</td>
						<td>2014-04-15</td>
						<td>2014-04-20</td>
						<td>1,000,000.00</td>
						<td></td>
					</tr>
					<tr>
						<td>服务商代码2</td>
						<td>服务商名称2</td>
						<td>返利账户</td>
						<td>现金账户</td>
						<td>2014-05-01</td>
						<td>2014-05-05</td>
						<td>1,000,000.00</td>
						<td></td>
					</tr>
					<tr>
						<td>服务商代码3</td>
						<td>服务商名称3</td>
						<td>返利账户</td>
						<td>现金账户</td>
						<td>2014-06-01</td>
						<td>2014-06-05</td>
						<td>1,000,000.00</td>
						<td></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>