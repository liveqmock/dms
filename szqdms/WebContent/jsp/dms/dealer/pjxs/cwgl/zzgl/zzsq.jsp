<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>转账申请</title>
<script type="text/javascript">
//查询按钮响应方法
$(function(){
	$("#zzsqlb").show();
	$("#zzsqlb").jTable();
});
function doApply(){
	var options = {max:false,width:500,height:260,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/dealer/pjxs/cwgl/zzgl/zzmx.jsp", "zzmx", "转账申请", options);
}
function doUpdate(){
	var options = {max:false,width:500,height:260,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/dealer/pjxs/cwgl/zzgl/zzmx.jsp", "zzmx", "转账申请编辑", options);
}
function doDelete(){
	alertMsg.info("删除成功！");
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：财务管理&gt;转账管理&gt;转账申请</h4>
	<div class="page">
	<div class="pageContent">
		<div id="zzsq">
			<table width="100%" id="zzsqlb" name="zzsqlb" style="display: none" >
				<thead>
					<tr>
						<th type="single" name="XH" style="display:" append="plus|doApply"></th>
						<th>转出账户类型</th>
						<th>转入账户类型</th>
						<th align="right">转账金额</th>
						<th>申请日期</th>
						<th>转账原因</th>
						<th colwidth="85" type="link" title="[编辑]|[删除]"  action="doUpdate|doDelete">操作</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><input type ="radio" name="ra"/></td>
						<td>返利账户</td>
						<td>现金账户</td>
						<td>10,000,000.00</td>
						<td>2014-06-02</td>
						<td></td>
						<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" onclick="doDelete()" class="op">[删除]</a></td>
					</tr>
					<tr>
						<td><input type ="radio" name="ra"/></td>
						<td>返利账户</td>
						<td>现金账户</td>
						<td>10,000,000.00</td>
						<td>2014-06-06</td>
						<td></td>
						<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" onclick="doDelete()" class="op">[删除]</a></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>