<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>故障模式和工时定额关系</title>
<script type="text/javascript">
//查询按钮响应方法
 $(function(){
	$("#search").click(function(){
		if($("#gzmslb").is(":hidden"))
		{
			$("#gzmslb").show();
			$("#gzmslb").jTable();
		}
	});
});
function doUpdate(){
	var options = {max:false,width:500,height:220,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/jcxxgl/gzmshgsgx.jsp", "gzmshgsgx", "故障模式和工时定额关系", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;基础信息管理&gt;故障模式和工时定额关系</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="gzmsform" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="gzmsTable">
					<tr>
						<td><label>故障模式代码：</label></td>
						<td><input type="text" id="GZMSDM" name="GZMSDM"datatype="1,is_null,100" value="" /></td>
						<td><label>故障模式名称：</label></td>
						<td><input type="text" id="GZMSMC" name="GZMSMC"datatype="1,is_null,100" value="" /></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li>
							<div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div>
						</li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="gzms">
			<table width="100%" id="gzmslb" name="gzmslb" style="display: none" >
				<thead>
					<tr>
						<th  name="XH" style="display:">序号</th>
						<th>故障模式代码</th>
						<th>故障模式名称</th>
						<th>工时代码</th>
						<th>工时名称</th>
						<th colwidth="85" type="link" title="[关系]"  action="doUpdate">操作</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>1</td>
						<td>故障模式代码1</td>
						<td>故障模式名称1</td>
						<td>工时代码1</td>
						<td>工时名称1</td>
						<td ><a href="#" onclick="doUpdate()" class="op">[关系]</a></td>
					</tr>
					<tr>
						<td>2</td>
						<td>故障模式代码2</td>
						<td>故障模式名称2</td>
						<td></td>
						<td></td>
						<td><a href="#" onclick="doUpdate()" class="op">[关系]</a></td>
					</tr>
					<tr>
						<td>3</td>
						<td>故障模式代码3</td>
						<td>故障模式名称3</td>
						<td></td>
						<td></td>
						<td><a href="#" onclick="doUpdate()" class="op">[关系]</a></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>