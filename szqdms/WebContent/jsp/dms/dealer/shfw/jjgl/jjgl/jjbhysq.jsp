<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>旧件不回运申请</title>
<script type="text/javascript">
//查询按钮响应方法
$(function(){
	$("#search").click(function(){
		if($("#jjbhylb").is(":hidden"))
		{
			$("#jjbhylb").show();
			$("#jjbhylb").jTable();
		}
		
	});
});
function doDetail(){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
 	$.pdialog.open(webApps+"/jsp/dms/dealer/shfw/spgl/common/comSpdwxxx.jsp", "comspdwxxx", "索赔单明细", options,true);
}
function doUpdate(rowobj){
	alertMsg.info("申请成功！");
// 	$("td input:first",$(rowobj)).attr("checked",true);
// 	var options = {max:false,width:400,heigth:450,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
// 	$.pdialog.open(webApps+"/jsp/dms/dealer/shfw/jjgl/jjgl/bqdy.jsp", "bqdy", "旧件标签申请", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：旧件管理&gt;旧件管理&gt;旧件不回运申请</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="jjbhylbform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="jjbhylbTable">
						<tr>
							<td><label>索赔单号：</label></td>
							<td><input type="text" id="SPDH" name="SPDH" datatype="1,is_null,100"  value="" /></td>
							<td><label>索赔类型：</label></td>
							<td><select type="text" id="SPLX" name="SPLX" class="combox" kind="dic" src="E#1=正常保修:2=强保">
									<option value="-1">--</option>
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
			<div id="jjbhy">
				<table width="100%" id="jjbhylb" name="jjbhylb" style="display: none" >
					<thead>
						<tr>
							<th  name="XH" style="display:">序号</th>
							<th>索赔单号</th>
							<th>索赔类型</th>
							<th>申请人</th>
							<th>申请时间</th>
							<th>申请状态</th>
							<th>备注</th>
							<th colwidth="85" type="link"  title="[申请]" action="doUpdate">操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td><a href="#" onclick="doDetail()" class="op">索赔单号1</a></td>
							<td>正常保修</td>
							<td>张三</td>
							<td>2014-5-20</td>
							<td>未审核</td>
							<td></td>
							<td><a href="#" onclick="doUpdate()" class="op">[申请]</a></td>
						</tr>
						<tr>
							<td>2</td>
							<td><a href="#" onclick="doDetail()" class="op">索赔单号2</a></td>
							<td>正常保修</td>
							<td>张三</td>
							<td>2014-5-20</td>
							<td>未审核</td>
							<td></td>
							<td><a href="#" onclick="doUpdate()" class="op">[申请]</a></td>
						</tr>
						<tr>
							<td>3</td>
							<td><a href="#" onclick="doDetail()" class="op">索赔单号3</a></td>
							<td>正常保修</td>
							<td>张三</td>
							<td>2014-5-20</td>
							<td>未审核</td>
							<td></td>
							<td><a href="#" onclick="doUpdate()" class="op">[申请]</a></td>
						</tr>
						<tr>
							<td>4</td>
							<td><a href="#" onclick="doDetail()" class="op">索赔单号4</a></td>
							<td>正常保修</td>
							<td>张三</td>
							<td>2014-5-20</td>
							<td>未审核</td>
							<td></td>
							<td><a href="#" onclick="doUpdate()" class="op">[申请]</a></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
</body>
</html>