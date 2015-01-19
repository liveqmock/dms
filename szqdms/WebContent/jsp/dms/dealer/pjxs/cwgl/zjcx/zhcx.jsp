<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>账户查询</title>
<script type="text/javascript">
$(function(){
	$("#zhcxlb").show();
	$("#zhcxlb").jTable();
});
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：财务管理&gt;资金查询&gt;账户查询</h4>
	<div class="page">
	<div class="pageContent">
		<div id="zhcx">
			<table width="100%" id="zhcxlb" name="zhcxlb" style="display: none" >
				<thead>
					<tr>
						<th>账户类型</th>
						<th align="right">余额(元)</th>
						<th align="right">占用(元)</th>
						<th align="right">可用余额(元)</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>现金账户</td>
						<td>2,000,000.00</td>
						<td>1,000,000.00</td>
						<td>1,000,000.00</td>
					</tr>
					<tr>
						<td>承兑汇票账户</td>
						<td>5,000,000.00</td>
						<td>3,000,000.00</td>
						<td>2,000,000.00</td>
					</tr>
					<tr>
						<td>材料费账户</td>
						<td>500,000.00</td>
						<td>100,000.00</td>
						<td>400,000.00</td>
					</tr>
					<tr>
						<td>信用额度账户</td>
						<td>1,000,000.00</td>
						<td>0.00</td>
						<td>1,000,000.00</td>
					</tr>
					<tr>
						<td>返利账户</td>
						<td>500,000.00</td>
						<td></td>
						<td>500,000.00</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>