<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>自动审核规则维护</title>
<script type="text/javascript">
var url = "<%=request.getContextPath()%>/Zdshgzgl/ZdshgzglAction.do";
//查询按钮响应方法
 $(function(){
	 $("#zdshgzlb").show();
	 $("#zdshgzlb").jTable();
});
function doStop(){
	alertMsg.info("停止设置成功！");
}
function doStart(){
	alertMsg.info("启动设置成功！");
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;基础信息管理&gt;自动审核规则维护</h4>
	<div class="pageContent">
	<div id="zdshgz">
		<table width="100%" id="zdshgzlb" name="zdshgzlb" style="display: none" >
			<thead>
				<tr>
					<th  name="XH" style="display:">序号</th>
					<th>规则名称</th>
					<th>规则类型</th>
					<th>状态</th>
					<th colwidth="85" type="link" title="[启动]|[停止]"  action="doStart|doStop">操作</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>1</td>
					<td>超出免费保修范围/索赔质保期未定义</td>
					<td>退回规则</td>
					<td>停止</td>
					<td ><a class="op" href="#" onclick="doStart()">[启动]</a></td>
				</tr>
				<tr>
					<td>2</td>
					<td>里程越跑越少</td>
					<td>拒绝规则 </td>
					<td>启动</td>
					<td><a href="#" class="op" onclick="doStop()">[停止]</a></td>
				</tr>
				<tr>
					<td>3</td>
					<td>需要预授权项目未通过预授权申请</td>
					<td>退回规则</td>
					<td>启动</td>
					<td><a href="#" onclick="doStop()" class="op">[停止]</a></td>
				</tr>
				<tr>
					<td>4</td>
					<td>强制保养报单系统判断报单是否唯一，唯一自动审核通过</td>
					<td>退回规则</td>
					<td>停止</td>
					<td><a href="#" onclick="doStart()" class="op">[启动]</a></td>
				</tr>
				<tr>
					<td>5</td>
					<td>定期保养系统自动审核通过</td>
					<td>拒绝规则</td>
					<td>停止</td>
					<td><a href="#" onclick="doStart()" class="op">[启动]</a></td>
				</tr>
				<tr>
					<td>6</td>
					<td>售前检修与培训系统判断是否唯一报单，是则通过审核</td>
					<td>拒绝规则</td>
					<td>启动</td>
					<td><a href="#" onclick="doStop()" class="op">[停止]</a></td>
				</tr>
				<tr>
					<td>7</td>
					<td>安全检查系统自动判断，一个车辆只限两次提报，如符合规则则通过审核</td>
					<td>退回规则</td>
					<td>停止</td>
					<td><a href="#" onclick="doStart()" class="op">[启动]</a></td>
				</tr>
			</tbody>
		</table>
	</div>
	</div>
</div>
</body>
</html>