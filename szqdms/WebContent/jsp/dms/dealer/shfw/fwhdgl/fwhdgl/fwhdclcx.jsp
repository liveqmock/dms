<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>服务活动车辆查询</title>
<script type="text/javascript">
//查询按钮响应方法
$(function(){
	$("#search").click(function(){
		if($("#fwhdcllb").is(":hidden"))
		{
			$("#fwhdcllb").show();
			$("#fwhdcllb").jTable();
		}
	});
});
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
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：服务活动管理&gt;服务活动管理&gt;服务活动车辆查询</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="fwhdclcxform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="fwhdclcxTable">
						<tr>
							<td><label>活动代码：</label></td>
							<td><input type="text" id="HDDM" name="HDDM" datatype="1,is_null,100"  value="" /></td>
							<td><label>活动名称：</label></td>
							<td><input type="text" id="HDMC" name="HDMC" datatype="1,is_null,100" value="" /></td>
							<td><label>登记车辆日期：</label></td>
						    <td colspan="5">
					    		<input type="text" id="in-ckrq" style="width:75px;" name="in-ckrq" datasource="CKRQ" datatype="1,is_null,30" onclick="WdatePicker()" />
					    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
								<input type="text" id="in-jsrq" style="width:75px;margin-left:-30px;" name="in-ckrq" datasource="CKRQ" datatype="1,is_null,30" onclick="WdatePicker()" />
					   		 </td>
						</tr>
						<tr>
							<td><label>VIN：</label></td>
      						<td><textarea name="textarea" id="vin" name="vin" cols="18" rows="3" ></textarea></td>
							<td><label>送修人姓名：</label></td>
							<td><input type ="text" id="SXRXM" name="SXRXM"/></td>
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
			<div id="fwhdcl">
				<table width="100%" id="fwhdcllb" name="fwhdcllb" style="display: none" >
					<thead>
						<tr>
							<th  name="XH" style="display:">序号</th>
							<th>活动代码</th>
							<th>活动名称</th>
							<th>VIN</th>
							<th>是否已报单</th>
							<th>送修人</th>
							<th>送修人电话</th>
							<th>登记车辆日期</th>
							<th>备注</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td><a href="#" onclick="doDetail()" class="op">活动代码1</a></td>
							<td>活动名称1</td>
							<td>VIN1</td>
							<td>是</td>
							<td>马闫鹏</td>
							<td>18636320654</td>
							<td>2014-04-25</td>
							<td></td>
						</tr>
						<tr>
							<td>2</td>
							<td><a href="#" onclick="doDetail()" class="op">活动代码2</a></td>
							<td>活动名称2</td>
							<td>VIN2</td>
							<td>是</td>
							<td>王青水</td>
							<td>18636320654</td>
							<td>2014-04-25</td>
							<td></td>
						</tr>
						<tr>
							<td>3</td>
							<td><a href="#" onclick="doDetail()" class="op">活动代码3</a></td>
							<td>活动名称3</td>
							<td>VIN3</td>
							<td>否</td>
							<td></td>
							<td></td>
							<td>2014-04-25</td>
							<td></td>
						</tr>
						<tr>
							<td>4</td>
							<td><a href="#" onclick="doDetail()" class="op">活动代码4</a></td>
							<td>活动名称4</td>
							<td>VIN4</td>
							<td>是</td>
							<td>李瑞</td>
							<td>18303598775</td>
							<td>2014-04-25</td>
							<td></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
</body>
</html>