<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>旧件标签打印</title>
<script type="text/javascript">
//查询按钮响应方法
$(function(){
	$("#search").click(function(){
		if($("#jjbqdylb").is(":hidden"))
		{
			$("#jjbqdylb").show();
			$("#jjbqdylb").jTable();
		}
		
	});
});
function doUpdate(){
	var options = {max:false,width:550,heigth:550,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/dealer/shfw/jjgl/jjgl/bqdy.jsp", "bqdy", "旧件标签打印", options); 
	//alertMsg.info("提供打印模版！");
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：旧件管理&gt;旧件管理&gt;旧件标签打印</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="jjbqdyform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="jjbqdyTable">
						<tr>
							<td><label>索赔单号：</label></td>
							<td><input type="text" id="SPDH" name="SPDH" datatype="1,is_null,100"  value="" /></td>
							<td><label>配件代码：</label></td>
							<td><input type="text" id="PJDM" name="PJDM" datatype="1,is_null,100"  value="" /></td>
							<td><label>配件名称：</label></td>
							<td><input type="text" id="PJMC" name="PJMC" datatype="1,is_null,100"  value="" /></td>
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
			<div id="jjbqdy">
				<table width="100%" id="jjbqdylb" name="jjbqdylb" style="display: none" >
					<thead>
						<tr>
							<th  name="XH" style="display:">序号</th>
							<th>索赔单号</th>
							<th>车辆型号</th>
							<th>VIN</th>
							<th>行驶里程</th>
							<th>配件代码</th>
							<th>配件名称</th>
							<th align="right">配件金额(元)</th>
							<th>应返数量</th>
							<th>实返数量</th>
							<th>箱号</th>
							<th>生产供应商</th>
							<th>责任供应商</th>
							<th>责任供应商代码</th>
							<th>质损原因</th>
							<th colwidth="85" type="link"  title="[打印]" action="doUpdate">操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>索赔单号1</td>
							<td>车型1</td>
							<td>VIN1</td>
							<td>2000</td>
							<td>配件代码1</td>
							<td>配件名称1</td>
							<td>100.00</td>
							<td>5</td>
							<td>5</td>
							<td>1</td>
							<td>供应商1</td>
							<td>责任供应商1</td>
							<td>责任供应商代码1</td>
							<td>破裂</td>
							<td><a href="#" onclick="doUpdate()" class="op">[打印]</a></td>
						</tr>
						<tr>
							<td>2</td>
							<td>索赔单号2</td>
							<td>车型2</td>
							<td>VIN2</td>
							<td>2000</td>
							<td>配件代码2</td>
							<td>配件名称2</td>
							<td>100.00</td>
							<td>5</td>
							<td>5</td>
							<td>1</td>
							<td>供应商2</td>
							<td>责任供应商2</td>
							<td>责任供应商代码2</td>
							<td>破裂</td>
							<td><a href="#" onclick="doUpdate()" class="op">[打印]</a></td>
						</tr>
						<tr>
							<td>3</td>
							<td>索赔单号3</td>
							<td>车型3</td>
							<td>VIN3</td>
							<td>2000</td>
							<td>配件代码3</td>
							<td>配件名称3</td>
							<td>100.00</td>
							<td>5</td>
							<td>5</td>
							<td>1</td>
							<td>供应商3</td>
							<td>责任供应商3</td>
							<td>责任供应商代码3</td>
							<td>破裂</td>
							<td><a href="#" onclick="doUpdate()" class="op">[打印]</a></td>
						</tr>
						<tr>
							<td>4</td>
							<td>索赔单号4</td>
							<td>车型4</td>
							<td>VIN4</td>
							<td>2000</td>
							<td>配件代码4</td>
							<td>配件名称4</td>
							<td>100.00</td>
							<td>5</td>
							<td>5</td>
							<td>1</td>
							<td>供应商4</td>
							<td>责任供应商4</td>
							<td>责任供应商代码4</td>
							<td>破裂</td>
							<td><a href="#" onclick="doUpdate()" class="op">[打印]</a></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
</body>
</html>