<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>三包急件查询</title>
<script type="text/javascript">
//查询按钮响应方法
$(function(){
	$("#search").click(function(){
		if($("#sbjjlb").is(":hidden")){
			$("#sbjjlb").show();
			$("#sbjjlb").jTable();
		}
	});
});
function doDetail(rowobj){
	$("td input:first",$(rowobj)).attr("checked",true);
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/dealer/shfw/sbjjgl/sbjjgl/sbjjmx.jsp", "sbjjmx", "三包急件明细", options,true); 
}
function open(){
	alertMsg.info("弹出服务商树");
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：三包急件管理&gt;三包急件管理&gt;三包急件查询</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="sbjjform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="sbjjTable">
						<tr>
							<td><label>服务商代码：</label></td>
							<td><input type="text" id="WFSDM" name="WFSDM" datatype="1,is_null,100" value="" readOnly hasBtn="true" callFunction="open();"/></td>
							<td><label>服务商名称：</label></td>
							<td><input type="text" id="WFSMC" name="WFSMC" datatype="1,is_null,100" value="" /></td>
							<td><label>急件编号：</label></td>
							<td><input type="text" id="JJBH" name="JJBH" datatype="1,is_null,100" value="" /></td>
						</tr>
						<tr>
							<td><label>申请状态：</label></td>
							<td><select type="text" id="SQZT" name="SQZT" datatype="1,is_null,100" class="combox" kind="dic" src="E#1=已申请:2=驳回">
									<option value=-1>--</option>
								</select>
							</td>
							<td><label>申请日期：</label></td>
						    <td colspan="3">
					    		<input type="text" id="in-ckrq" style="width:75px;" name="in-ckrq" datasource="CKRQ" datatype="1,is_null,30" onclick="WdatePicker()" />
					    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
								<input type="text" id="in-jsrq" style="width:75px;margin-left:-30px;" name="in-ckrq" datasource="CKRQ" datatype="1,is_null,30" onclick="WdatePicker()" />
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
			<div id="sbjj">
				<table width="100%" id="sbjjlb" name="sbjjlb" style="display: none" >
					<thead>
						<tr>
							<th  name="XH" style="display:">序号</th>
							<th>服务商代码</th>
							<th>服务商名称</th>
							<th>急件编号</th>
							<th>申请状态</th>
							<th>申请日期</th>
							<th>驳回原因</th>
							<th colwidth="100" type="link"  title="[明细]" action="doDetail">操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>服务商代码1</td>
							<td>服务商名称1</td>
							<td>急件编号1</td>
							<td>已保存</td>
							<td>2014-5-25</td>
							<td></td>
							<td><a href="#" onclick="doDetail()" class="op">[明细]</a></td>
						</tr>
						<tr>
							<td>2</td>
							<td>服务商代码2</td>
							<td>服务商名称2</td>
							<td>急件编号2</td>
							<td>已保存</td>
							<td>2014-5-25</td>
							<td></td>
							<td><a href="#" onclick="doDetail()" class="op">[明细]</a></td>
						</tr>
						<tr>
							<td>3</td>
							<td>服务商代码3</td>
							<td>服务商名称3</td>
							<td>急件编号3</td>
							<td>已驳回</td>
							<td>2014-5-25</td>
							<td>不符合要求</td>
							<td><a href="#" onclick="doDetail()" class="op">[明细]</a></td>
						</tr>
						<tr>
							<td>4</td>
							<td>服务商代码4</td>
							<td>服务商名称4</td>
							<td>急件编号4</td>
							<td>已申请</td>
							<td>2014-5-25</td>
							<td></td>
							<td><a href="#" onclick="doDetail()" class="op">[明细]</a></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
</body>
</html>