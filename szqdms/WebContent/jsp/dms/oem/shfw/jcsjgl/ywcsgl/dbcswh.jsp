<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>定保次数维护</title>
<script type="text/javascript">
//查询按钮响应方法
$(function(){
	$("#dbcslb").show();
	$("#dbcslb").jTable();
});
function doSave(){
	alertMsg.info("保存成功");
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;业务参数管理&gt;定保次数维护</h4>
	<div class="page">
	<div class="pageContent">
		<div id="dbcs">
			<table width="100%" id="dbcslb" name="dbcslb" style="display: none" >
				<thead>
					<tr>
						<th  name="XH" style="display:">序号</th>
						<th>定保次数</th>
						<th>是否需要费用</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>1</td>
						<td>定保一次</td>
						<td> <input type="checkbox"  checked="checked"/></td>
					</tr>
					<tr>
						<td>2</td>
						<td>定保二次</td>
						<td> <input type="checkbox"/></td>
					</tr>
					<tr>
						<td>3</td>
						<td>定保三次</td>
						<td> <input type="checkbox" checked="checked"/></td>
					</tr>
					<tr>
						<td>4</td>
						<td>定保四次</td>
						<td> <input type="checkbox"/></td>
					</tr>
					<tr>
						<td>5</td>
						<td>定保五次</td>
						<td> <input type="checkbox"  /></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doSave()" id="save">保&nbsp;&nbsp;存</button></div></div></li>
		</ul>
	</div>
	</div>
</div>
</body>
</html>