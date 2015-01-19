<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>预授权人员维护</title>
<script type="text/javascript">
var url = "<%=request.getContextPath()%>/Ysgrygl/YsgryglAction.do";
//查询按钮响应方法
 $(function(){
	$("#search").click(function(){
		if($("#ysqrylb").is(":hidden")){
			$("#ysqrylb").show();
			$("#ysqrylb").jTable();
		}
	});
});
function doUpdate(rowobj){
	$("td input:first",$(rowobj)).attr("checked",true);
	var options = {max:false,width:470,height:200,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/jcxxgl/ysqryxz.jsp", "ysqryxx", "预授权人员编辑", options);
}
function doDelete(){
	alertMsg.info("删除成功");
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;基础信息管理&gt;预授权人员维护</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="ysqrywhform" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="ysqryTable">
					<tr>
						<td><label>预授权级别维护：</label></td>
						<td><select type="text" class="combox" id="YSQJB" name="YSQJB" kind="dic" src="E#1=外出服务经理:2=服务追偿科科员:3=服务追偿科科长:4=服务部经理:5=销售公司经理" datatype="1,is_null,10" >
				        	<option value="-1" selected>--</option>
				        </select>
				        </td>
					</tr>
					<tr>
						<td><label>员工代码：</label></td>
						<td><input type="text" id="YGDM" name="YGDM"datatype="1,is_null,100" value="" /></td>
						<td><label>员工名称：</label></td>
						<td><input type="text" id="YGMC" name="YGMC"datatype="1,is_null,100" value="" /></td>
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
		<div id="ysqry">
			<table width="100%" id="ysqrylb" name="ysqrylb" style="display: none" >
				<thead>
					<tr>
						<th  name="XH" style="display:">序号</th>
						<th>员工代码</th>
						<th>员工名称</th>
						<th>授权级别</th>
						<th colwidth="85" type="link" title="[编辑]"  action="doUpdate">操作</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>1</td>
						<td>XS1075</td>
						<td>霍华龙</td>
						<td></td>
						<td ><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a class="op" href="#" onclick="doDelete()">[删除]</a></td>
					</tr>
					<tr>
						<td>2</td>
						<td>LSHT</td>
						<td>梁山华泰</td>
						<td>服务追偿科科员</td>
						<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" class="op" onclick="doDelete()">[删除]</a></td>
					</tr>
					<tr>
						<td>3</td>
						<td>XS915</td>
						<td>杨凯</td>
						<td></td>
						<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" class="op" onclick="doDelete()">[删除]</a></td>
					</tr>
					<tr>
						<td>4</td>
						<td>100503</td>
						<td>丹阳华升</td>
						<td></td>
						<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" class="op" onclick="doDelete()">[删除]</a></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>