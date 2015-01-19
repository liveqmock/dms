<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>帮助说明</title>
<script type="text/javascript">
 //下载索赔提报流程
function download1() {
	 var url = encodeURI("<%=request.getContextPath()%>/jsp/dms/common/download.jsp?filename=claimOrderApplyFlow.doc");
     window.location.href = url;
};
//下载操作手册
function download2() {
	 var url = encodeURI("<%=request.getContextPath()%>/jsp/dms/common/download.jsp?filename=userManual_Service_dealer.doc");
     window.location.href = url;
};
//下载操作手册
function download3() {
	 var url = encodeURI("<%=request.getContextPath()%>/jsp/dms/common/download.jsp?filename=sqMobile451.apk");
     window.location.href = url;
};
//下载渠道配件操作手册
function download4() {
	 var url = encodeURI("<%=request.getContextPath()%>/jsp/dms/common/download.jsp?filename=userManual_Part_dealer.doc");
     window.location.href = url;
};
//下载订单提报流程
function download5() {
	 var url = encodeURI("<%=request.getContextPath()%>/jsp/dms/common/download.jsp?filename=partOrderReportFlow.doc");
     window.location.href = url;
};
function download6() {
	 var url = encodeURI("<%=request.getContextPath()%>/jsp/dms/common/download.jsp?filename=userManual_supplier.doc");
    window.location.href = url;
};
</script>
</head>
<body>
<div class="pageContent">
	<form name="lForm" method="post"  >
		<div class="pageContent">
			<table width="100%" id="helpText" name="helpText" class="dlist">
					<thead>
						<tr>
							<th>下载文件</th>
							<th colwidth="45" type="link" title="[下载]" action="download">操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td >配件订单提报操作流程</td>
							<td><a href="#" class="op" onclick="download5()">[下载]</a></td>
						</tr>
						<tr>
							<td >用户手册_配件管理_渠道商</td>
							<td><a href="#" class="op" onclick="download4()">[下载]</a></td>
						</tr>
						<tr>
							<td >售后索赔单提报流程</td>
							<td><a href="#" class="op" onclick="download1()">[下载]</a></td>
						</tr>
						<tr>
							<td >用户手册_售后服务_服务商</td>
							<td><a href="#" class="op" onclick="download2()">[下载]</a></td>
						</tr>
						<tr>
							<td >安卓手机外出服务APK(正式版)</td>
							<td><a href="#" class="op" onclick="download3()">[下载]</a></td>
						</tr>
						<tr>
							<td >用户手册_供应商</td>
							<td><a href="#" class="op" onclick="download6()">[下载]</a></td>
						</tr>
					</tbody>
			</table>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>
</body>
</html>