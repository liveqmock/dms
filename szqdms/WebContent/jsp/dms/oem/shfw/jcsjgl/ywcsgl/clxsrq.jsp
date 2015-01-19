<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>车辆销售日期更改</title>
<script type="text/javascript">
//查询按钮响应方法
$(function(){
	$("#search").click(function(){
		if($("#clxsrqlb").is(":hidden")){
			$("#clxsrqlb").show();
			$("#clxsrqlb").jTable();
		}
	});
});
function doUpdate(rowobj){
	$("td input:first",$(rowobj)).attr("checked",true);
	var options = {max:false,width:640,height:280,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/ywcsgl/clxsrqxg.jsp", "clxsrqxg", "车辆销售日期更改", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;业务参数管理&gt;车辆销售日期更改</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="clxsrqform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="clxsrqTable">
						<tr>
							<td><label>VIN：</label></td>
      					  	<td><textarea name="textarea" id="vin" name="vin" cols="18" rows="3" ></textarea></td>
      					   	<td><label>用户类别：</label></td>
							<td><select type="text" id="YHLB" name="YHLB" class="combox" kind="dic" src="E#1=军车:2=民车">
									<option value=-1>--</option>
								</select>
						</td>
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
			<div id="clxsrq">
				<table width="100%" id="clxsrqlb" name="clxsrqlb" style="display: none" >
					<thead>
						<tr>
							<th  name="XH" style="display:">序号</th>
							<th>VIN</th>
							<th>用户类别</th>
							<th>销售日期</th>
							<th>车辆用途</th>
							<th colwidth="85" type="link" title="[编辑]"  action="doUpdate">操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>VIN1</td>
							<td>军车</td>
							<td>2014-4-30</td>
							<td>公路用车</td>
							<td ><a href="#" onclick="doUpdate()" class="op">[编辑]</a></td>
						</tr>
						<tr>
							<td>2</td>
							<td>VIN2</td>
							<td>民车</td>
							<td>2014-4-30</td>
							<td>非公路用车</td>
							<td ><a href="#" onclick="doUpdate()" class="op">[编辑]</a></td>
						</tr>
						<tr>
							<td>3</td>
							<td>VIN3</td>
							<td>民车</td>
							<td>2014-4-30</td>
							<td>非公路用车</td>
							<td ><a href="#" onclick="doUpdate()" class="op">[编辑]</a></td>
						</tr>
						<tr>
							<td>4</td>
							<td>VIN4</td>
							<td>民车</td>
							<td>2014-4-30</td>
							<td>非公路用车</td>
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