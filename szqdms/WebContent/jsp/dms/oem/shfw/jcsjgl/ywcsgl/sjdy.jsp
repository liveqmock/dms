<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>时间定义</title>
<script type="text/javascript">
//查询按钮响应方法
 $(function(){
	 $("#sjlb").jTable();
});
function doUpdate(rowobj){
	/* $("td input:first",$(rowobj)).attr("checked",true);
	var options = {max:false,width:550,height:180,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/jcxxgl/ysqjbxz.jsp", "ysqjbxx", "预授权级别编辑", options); */
}
</script>
</head>
<body>
	<div id="layout" width="100%">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left">
			<img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;业务参数管理&gt;时间定义
		</h4>
			<div class="pageContent">
				<div id="sj">
					<table width="100%" id="sjlb" name="sjlb" style="display:" >
						<thead>
							<tr>
								<th  name="XH" style="display:">序号</th>
								<th>时间</th>
								<th>时间段</th>
								<th colwidth="85" type="link" title="[修改]"  action="doUpdate">操作</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>1</td>
								<td>白天</td>
								<td>6-18</td>
								<td ><a href="#" onclick="doUpdate()" class="op">[修改]</a></td>
							</tr>
							<tr>
								<td>2</td>
								<td>夜间</td>
								<td>18-6</td>
								<td ><a href="#" onclick="doUpdate()" class="op">[修改]</a></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
</body>
</html>