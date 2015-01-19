<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>旧件回运</title>
<script type="text/javascript">
//查询按钮响应方法
$(function(){
	$("#search").click(function(){
		if($("#jjhylb").is(":hidden"))
		{
			$("#jjhylb").show();
			$("#jjhylb").jTable();
		}
		
	});
});
function open(){
	alertMsg.info("弹出服务商树！");
}
function doUpdate(rowobj){
	/* $("td input:first",$(rowobj)).attr("checked",true);
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/dealer/shfw/jjgl/jjgl/jjhyshmx.jsp", "jjhyshmx", "旧件回运装箱修改", options,true); */
	alertMsg.info("回运成功！");
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：旧件管理&gt;旧件管理&gt;旧件回运</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="jjhyform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="jjhyTable">
						<tr>
							<td><label>服务商代码：</label></td>
							<td><input type="text" id="WFSDM" name="WFSDM" datatype="1,is_null,100" value="" readOnly hasBtn="true" callFunction="open();"/></td>
							<td><label>服务商名称：</label></td>
							<td><input type="text" id="WFSMC" name="WFSMC" datatype="1,is_null,100" value="" /></td>
						</tr>	
						<tr>
							<td><label>回运单号：</label></td>
							<td><input type="text" id="HYDH" name="HYDH" datatype="1,is_null,100"  value="" /></td>
							<td><label>运输方式：</label></td>
							<td><select  type="text" id="YSFS" name="YSFS" kind="dic" class="combox" src="E#1=配货:2=专车:3=铁路:4=空运"  datatype="1,is_null,100" value="" >
									<option value=-1 selected>--</option>
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
			<div id="jjhy">
				<table width="100%" id="jjhylb" name="jjhylb" style="display: none" >
					<thead>
						<tr>
							<th  name="XH" style="display:">序号</th>
							<th>服务站代码</th>
							<th>服务站名称</th>
							<th>回运单号</th>
							<th>索赔单数量</th>
							<th>回运旧件数量</th>
							<th>装箱总数</th>
							<th>运输方式</th>
							<th>服务站回运日期</th>
							<th colwidth="85" type="link"  title="[回运]" action="doUpdate">操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>服务站代码1</td>
							<td>服务商名称1</td>
							<td>回运单号1</td>
							<td>2</td>
							<td>5</td>
							<td>1</td>
							<td>专车</td>
							<td>2014-5-20</td>
							<td><a href="#" onclick="doUpdate()" class="op">[回运]</a></td>
						</tr>
						<tr>
							<td>2</td>
							<td>服务站代码2</td>
							<td>服务商名称2</td>
							<td>回运单号2</td>
							<td>3</td>
							<td>6</td>
							<td>2</td>
							<td>铁路</td>
							<td>2014-5-20</td>
							<td><a href="#" onclick="doUpdate()" class="op">[回运]</a></td>
						</tr>
						<tr>
							<td>3</td>
							<td>服务站代码3</td>
							<td>服务商名称3</td>
							<td>回运单号3</td>
							<td>2</td>
							<td>5</td>
							<td>1</td>
							<td>配货</td>
							<td>2014-5-20</td>
							<td><a href="#" onclick="doUpdate()" class="op">[回运]</a></td>
						</tr>
						<tr>
							<td>4</td>
							<td>服务站代码4</td>
							<td>服务商名称4</td>
							<td>回运单号4</td>
							<td>1</td>
							<td>1</td>
							<td>1</td>
							<td>空运</td>
							<td>2014-5-20</td>
							<td><a href="#" onclick="doUpdate()" class="op">[回运]</a></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
</body>
</html>