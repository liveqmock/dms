<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>计划提报</title>
<script type="text/javascript">
//查询按钮响应方法
 $(function(){
	$("#search").click(function(){
		if($("#pjjhlb").is(":hidden")){
			$("#pjjhlb").show();
			$("#pjjhlb").jTable();
		}
	});
});
function doDelete(){
	alertMsg.info("删除成功！");
}
function doApply(){
	alertMsg.info("提报成功！");
}
function doDetail(rowobj){
	$("td input:first",$(rowobj)).attr("checked",true);
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/pjxs/jhgl/jhgl/jhmxdz.jsp", "jhmx", "计划明细调整", options,true);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：计划管理&gt;计划管理&gt;计划提报</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="jhtbform" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="jhtbTable">
					<tr>
						
						<td><label>计划名称：</label></td>
						<td><input type="text" id="JHMC" name="JHMC"datatype="1,is_null,100" value="" /></td>
						<td><label>计划状态：</label></td>
						<td><select type="text" id="JHZT" name="JHZT" kind="dic" class="combox" src="E#1=已保存:2=审核驳回">
						    	<option value=-1>--</option>
						    </select></td>
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
		<div id="pjjh">
			<table width="100%" id="pjjhlb" name="pjjhlb" style="display: none" >
				<thead>
					<tr>
						<th  name="XH" style="display:">序号</th>
						<th>计划代码</th>
						<th>计划名称</th>
						<th>统计日期范围</th>
						<th>仓库范围</th>
						<th>计划日期</th>
						<th>计划状态</th>
						<th colwidth="145" type="link" title="[明细调整]|[提报]|[删除]"  action="doDetail|doApply|doDelete">操作</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>1</td>
						<td>计划代码1</td>
						<td>计划名称1</td>
						<td>2014-03-26至2014-04-25</td>
						<td>中心配件仓库</td>
						<td>2014-04-26至2014-05-25</td>
						<td>已保存</td>
						<td ><a href="#" onclick="doDetail()" class="op">[明细调整]</a><a class="op" href="#" onclick="doApply()">[提报]</a><a class="op" href="#" onclick="doDelete()">[删除]</a></td>
					</tr>
					<tr>
						<td>2</td>
						<td>计划代码2</td>
						<td>计划名称2</td>
						<td>2014-03-26至2014-04-25</td>
						<td>中心配件仓库</td>
						<td>2014-04-26至2014-05-25</td>
						<td>已保存</td>
						<td ><a href="#" onclick="doDetail()" class="op">[明细调整]</a><a class="op" href="#" onclick="doApply()">[提报]</a><a class="op" href="#" onclick="doDelete()">[删除]</a></td>
					</tr>
					<tr>
						<td>3</td>
						<td>计划代码3</td>
						<td>计划名称3</td>
						<td>2014-03-26至2014-04-25</td>
						<td>中心配件仓库</td>
						<td>2014-04-26至2014-05-25</td>
						<td>已保存</td>
						<td ><a href="#" onclick="doDetail()" class="op">[明细调整]</a><a class="op" href="#" onclick="doApply()">[提报]</a><a class="op" href="#" onclick="doDelete()">[删除]</a></td>
					</tr>
					<tr>
						<td>4</td>
						<td>计划代码4</td>
						<td>计划名称4</td>
						<td>2014-03-26至2014-04-25</td>
						<td>中心配件仓库</td>
						<td>2014-04-26至2014-05-25</td>
						<td>已保存</td>
						<td ><a href="#" onclick="doDetail()" class="op">[明细调整]</a><a class="op" href="#" onclick="doApply()">[提报]</a><a class="op" href="#" onclick="doDelete()">[删除]</a></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>