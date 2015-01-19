<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>供应商旧件认领</title>
<script type="text/javascript">
//查询按钮响应方法
$(function(){
	$("#search").click(function(){
		if($("#jjrllb").is(":hidden")){
			$("#jjrllb").show();
			$("#jjrllb").jTable();
		}
	});
});
function doUpdate(){
	var options = {max:false,width:700,height:220,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jjgl/jjgl/jjrl.jsp", "jjrl", "旧件认领", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：旧件管理&gt;旧件管理&gt;供应商旧件认领</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="jjrlform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="jjrlTable">
						<tr>
							<td><label>配件代码：</label></td>
							<td><input type="text" id="PJDM" name="PJDM" datatype="1,is_null,100"  value="" /></td>
							<td><label>配件名称：</label></td>
							<td><input type="text" id="PJMC" name="PJMC" datatype="1,is_null,100" value="" /></td>
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
			<div id="jj">
				<table width="100%" id="jjrllb" name="jjrllb" style="display: none" >
					<thead>
						<tr>
							<th  name="XH" style="display:">序号</th>
							<th>配件代码</th>
							<th>配件名称</th>
							<th>库存数量</th>
							<th colwidth="45" type="link" title="[认领]" action="doUpdate">操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="rownums"><div>1</div></td>
							<td>配件代码1</td>
							<td>配件名称1</td>
							<td>7</td>
							<td><a href="#" onclick="doUpdate()" class="op">[认领]</a></td>
						</tr>
						<tr>
							<td class="rownums"><div>2</div></td>
							<td>配件代码2</td>
							<td>配件名称2</td>	
							<td>7</td>
							<td><a href="#" onclick="doUpdate()" class="op">[认领]</a></td>
						</tr>
						<tr>
							<td class="rownums"><div>3</div></td>
							<td>配件代码3</td>
							<td>配件名称3</td>
							<td>7</td>
							<td><a href="#" onclick="doUpdate()" class="op">[认领]</a></td>
						</tr>
						<tr>
							<td class="rownums"><div>4</div></td>
							<td>配件代码4</td>
							<td>配件名称4</td>
							<td>7</td>
							<td><a href="#" onclick="doUpdate()" class="op">[认领]</a></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
</body>
</html>