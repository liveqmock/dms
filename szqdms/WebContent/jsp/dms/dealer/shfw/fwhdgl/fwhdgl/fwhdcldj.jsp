<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>服务活动车辆登记</title>
<script type="text/javascript">
//查询按钮响应方法
$(function(){
	$("#search").click(function(){
		var $f = $("#fwhdcldjform");
		if (submitForm($f) == false) return false;
		if($("#fwhdcldjlb").is(":hidden"))
		{
			$("#fwhdcldjlb").show();
			$("#fwhdcldjlb").jTable();
		}
	});
});
function doUpdate(){
	alertMsg.info("车辆登记成功！");
}
function doDetail(){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/dealer/shfw/fwhdgl/fwhdgl/fwhdxxmx.jsp", "fwhdxxmx", "服务活动明细", options,true);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：服务活动管理&gt;服务活动管理&gt;服务活动车辆登记</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="fwhdcldjform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="fwhdcldjTable">
						<tr>
							<td><label>VIN：</label></td>
      						<td><textarea name="textarea" id="vin" name="vin" cols="18" rows="3" datatype="0,is_vin,17" ></textarea></td>
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
			<div id="fwhdcldj">
				<table width="100%" id="fwhdcldjlb" name="fwhdcldjlb" style="display: none" >
					<thead>
						<tr>
							<th  name="XH" style="display:">序号</th>
							<th>活动代码</th>
							<th>活动名称</th>
							<th>活动类别</th>
							<th>处理方式</th>
							<th>是否索赔</th>
							<th>开始日期</th>
							<th>结束日期</th>
							<th colwidth="85" type ="link" title="[车辆登记]"  action="doUpdate">操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td><a href="#" onclick="doDetail()" class="op">代码1</a></td>
							<td>名称1</td>
							<td>维修</td>
							<td>零件更换</td>
							<td>是</td>
							<td>2013-03-05</td>
							<td>2013-09-25</td>
							<td ><a href="#" onclick="doUpdate()" class="op">[车辆登记]</a></td>
						</tr>
						<tr>
							<td>2</td>
							<td><a href="#" onclick="doDetail()" class="op">代码2</a></td>
							<td>名称2</td>
							<td>保养</td>
							<td>维修</td>
							<td>否</td>
							<td>2012-06-01</td>
							<td>2012-07-31</td>
							<td ><a href="#" onclick="doUpdate()" class="op">[车辆登记]</a></td>
						</tr>
						<tr>
							<td>3</td>
							<td><a href="#" onclick="doDetail()" class="op">代码3</a></td>
							<td>名称3</td>
							<td>保养</td>
							<td>整车收回</td>
							<td>否</td>
							<td>2014-03-01</td>
							<td>2014-06-31</td>
							<td ><a href="#" onclick="doUpdate()" class="op">[车辆登记]</a></td>
						</tr>
						<tr>
							<td>4</td>
							<td><a href="#" onclick="doDetail()" class="op">代码4</a></td>
							<td>名称4</td>
							<td>技术升级</td>
							<td>整车收回</td>
							<td>否</td>
							<td>2014-03-01</td>
							<td>2014-06-31</td>
							<td ><a href="#" onclick="doUpdate()" class="op">[车辆登记]</a></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
</body>
</html>