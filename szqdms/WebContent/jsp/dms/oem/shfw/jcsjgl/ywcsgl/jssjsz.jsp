<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>结算时间设置</title>
<script type="text/javascript">
//查询按钮响应方法
$(function(){
	for(var i=2013;i<=2015; i++){
		if(i==2014){
			$("#JSN").append("<option value="+i+" selected>"+i+"</option>");
		}else{
			$("#JSN").append("<option value="+i+">"+i+"</option>");
		}
	}
	$("#search").click(function(){
		if($("#jssjlb").is(":hidden")){
			$("#jssjlb").show();
			$("#jssjlb").jTable();
		}
	});
});
function doAdd(){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/ywcsgl/jssjxz.jsp", "jssjxz", "设置结算时间", options);
}
function doUpdate(rowobj){
	$("td input:first",$(rowobj)).attr("checked",true);
	var options = {max:false,width:400,height:220,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/ywcsgl/jssjxg.jsp", "jssjxg", "设置结算时间", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;业务参数管理&gt;结算时间设置</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="jssjszform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="jssjszTable">
						<tr>	
							<td><label>年：</label></td>
							<td><select  type="text" id="JSN" name="JSN" >
								</select> 
							</td>
					 	</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="add" onClick="doAdd()">新&nbsp;&nbsp;增</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div id="jssjsz">
				<table width="100%" id="jssjlb" name="jssjlb" layoutH="240" pageRows="15" style="display: none" >
					<thead>
						<tr>
							<th colwidth="60">年</th> 
							<th colwidth="60">月份</th> 
							<th colwidth="60">结算日期</th>
							<th colwidth="85" type="link" title="[编辑]"  action="doUpdate">操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>2014</td>
							<td>1</td>
							<td>8</td>
							<td ><a href="#" onclick="doUpdate()" class="op">[编辑]</a></td>
						</tr>
						<tr>
							<td>2014</td>
							<td>2</td>
							<td>8</td>
							<td ><a href="#" onclick="doUpdate()" class="op">[编辑]</a></td>
						</tr>
						<tr>
							<td>2014</td>
							<td>3</td>
							<td>8</td>
							<td ><a href="#" onclick="doUpdate()" class="op">[编辑]</a></td>
						</tr>
						<tr>
							<td>2014</td>
							<td>4</td>
							<td>8</td>
							<td ><a href="#" onclick="doUpdate()" class="op">[编辑]</a></td>
						</tr>
						<tr>
							<td>2014</td>
							<td>5</td>
							<td>8</td>
							<td ><a href="#" onclick="doUpdate()" class="op">[编辑]</a></td>
						</tr>
						<tr>
							<td>2014</td>
							<td>6</td>
							<td>8</td>
							<td ><a href="#" onclick="doUpdate()" class="op">[编辑]</a></td>
						</tr>
						<tr>
							<td>2014</td>
							<td>7</td>
							<td>8</td>
							<td ><a href="#" onclick="doUpdate()" class="op">[编辑]</a></td>
						</tr>
						<tr>
							<td>2014</td>
							<td>8</td>
							<td>8</td>
							<td ><a href="#" onclick="doUpdate()" class="op">[编辑]</a></td>
						</tr>
						<tr>
							<td>2014</td>
							<td>9</td>
							<td>8</td>
							<td ><a href="#" onclick="doUpdate()" class="op">[编辑]</a></td>
						</tr>
						<tr>
							<td>2014</td>
							<td>10</td>
							<td>8</td>
							<td ><a href="#" onclick="doUpdate()" class="op">[编辑]</a></td>
						</tr>
						<tr>
							<td>2014</td>
							<td>11</td>
							<td>8</td>
							<td ><a href="#" onclick="doUpdate()" class="op">[编辑]</a></td>
						</tr>
						<tr>
							<td>2014</td>
							<td>12</td>
							<td>8</td>
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