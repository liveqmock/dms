<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>预授权规则维护</title>
<script type="text/javascript">
var url = "<%=request.getContextPath()%>/Ysqgzgl/YsqgzglAction.do";
//查询按钮响应方法
 $(function(){
	$("#search").click(function(){
		if($("#ysqgzlb").is(":hidden")){
			$("#ysqgzlb").show();
			$("#ysqgzlb").jTable();
		}
	});
});
function doUpdate(rowobj){
	$("td input:first",$(rowobj)).attr("checked",true);
	var options = {max:false,width:1050,height:385,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/jcxxgl/ysqgzxz.jsp?action=2", "ysqgzxx", "预授权规则编辑", options);
}
function doAdd(){
	var options = {max:false,width:1050,height:385,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/jcxxgl/ysqgzxz.jsp?action=1", "ysqgzxx", "预授权规则新增", options);
}

</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;基础信息管理&gt;预授权规则维护</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="ysqgzwhform" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="ysqgzTable">
					<tr>
						<td><label>授权规则：</label></td>
						<td><input type="text" id="SQGZ" datatype="1,is_null,100"/></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li>
							<div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div>
						</li>
						<li>
							<div class="buttonActive"><div class="buttonContent"><button type="button" id="add" onclick="doAdd()">新&nbsp;&nbsp;增</button></div></div>
						</li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="ysqgz">
			<table width="100%" id="ysqgzlb" name="ysqgzlb" style="display: none" >
				<thead>
					<tr>
						<th  name="XH" style="display:">序号</th>
						<th>授权规则</th>
						<th>外出服务经理</th>
						<th>服务追偿科科员</th>
						<th>服务追偿科科长</th>
						<th>服务部经理</th>
						<th>销售公司经理</th>
						<th colwidth="85" type="link" title="[编辑]"  action="doUpdate">操作</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>1</td>
						<td>索赔类型 Equal 商务赔偿 AND 维修总金额 > 10000</td>
						<td> <input type="checkbox" disabled="disabled"  checked="checked"/></td>
						<td> <input type="checkbox" disabled="disabled"  /></td>
						<td> <input type="checkbox" disabled="disabled"  /></td>
						<td> <input type="checkbox" disabled="disabled"  checked="checked"/></td>
						<td> <input type="checkbox" disabled="disabled"  /></td>
						<td ><a href="#" onclick="doUpdate()" class="op">[编辑]</a></td>
					</tr>
					<tr>
						<td>2</td>
						<td>索赔类型 Equal...</td>
						<td> <input type="checkbox" disabled="disabled"  /></td>
						<td> <input type="checkbox" disabled="disabled"  /></td>
						<td> <input type="checkbox" disabled="disabled"  /></td>
						<td> <input type="checkbox" disabled="disabled"   checked="checked"/></td>
						<td> <input type="checkbox" disabled="disabled" checked="checked"/></td>
						<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a></td>
					</tr>
					<tr>
						<td>3</td>
						<td>索赔类型 Equal 商务赔偿 AND 维修总金额 <= 2000</td>
						<td> <input type="checkbox" disabled="disabled"  /></td>
						<td> <input type="checkbox" disabled="disabled" checked="checked" /></td>
						<td> <input type="checkbox" disabled="disabled" /></td>
						<td> <input type="checkbox" disabled="disabled" checked="checked"/></td>
						<td> <input type="checkbox" disabled="disabled" checked="checked"/></td>
						<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a></td>
					</tr>
					<tr>
						<td>4</td>
						<td>零件金额 > 8000 AND 零件金额 > 10000</td>
						<td> <input type="checkbox" disabled="disabled"  /></td>
						<td> <input type="checkbox" disabled="disabled" checked="checked" /></td>
						<td> <input type="checkbox" disabled="disabled" checked="checked"/></td>
						<td> <input type="checkbox" disabled="disabled"  /></td>
						<td> <input type="checkbox" disabled="disabled" /></td>
						<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a></td>
					</tr>
					<tr>
						<td>5</td>
						<td>零件金额 > 5000 AND 零件金额 <= 10000</td>
						<td> <input type="checkbox" disabled="disabled"   checked="checked"/></td>
						<td> <input type="checkbox" disabled="disabled" /></td>
						<td> <input type="checkbox" disabled="disabled"  /></td>
						<td> <input type="checkbox" disabled="disabled"  checked="checked"/></td>
						<td> <input type="checkbox" disabled="disabled"  /></td>
						<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>