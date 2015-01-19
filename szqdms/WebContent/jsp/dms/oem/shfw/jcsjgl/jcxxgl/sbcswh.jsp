<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>首保参数维护</title>
<script type="text/javascript">
var url = "<%=request.getContextPath()%>/Sbcsgl/SbcsglAction.do";
//查询按钮响应方法
 $(function(){
	$("#sbcslb").jTable();
});
function doUpdate(rowobj){
	$("td input:first",$(rowobj)).attr("checked",true);
	var options = {max:false,width:450,height:300,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/jcxxgl/sbcsxz.jsp", "sbcsxx", "首保参数编辑", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;基础信息管理&gt;首保参数维护</h4>
	<div class="page">
	<div class="pageContent">
		<div id="sbcs">
			<table width="100%" id="sbcslb" name="sbcslb" style="display:" >
				<thead>
					<tr>
						<th  name="XH" style="display:">序号</th>
						<th align="right">发动机(元)</th>
						<th align="right">变速箱(元)</th>
						<th align="right">桥(元)</th>
						<th colwidth="85" type="link" title="[编辑]"  action="doUpdate">操作</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>1</td>
						<td>50.00</td>
						<td>40.00</td>
						<td>110.00</td>
						<td ><a href="#" onclick="doUpdate()" class="op">[编辑]</a></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>