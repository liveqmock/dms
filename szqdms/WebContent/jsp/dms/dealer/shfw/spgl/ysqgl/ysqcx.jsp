<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>预授权查询</title>
<script type="text/javascript">
//变量定义
//查询提交方法
var url = "<%=request.getContextPath()%>/Spdgl/SpdglAction.do";
//初始化方法
$(function(){
	//查询方法
	$("#btn-cx").bind("click",function(event){
		if($("#tab-ysqlb").is(":hidden"))
		{
			$("#tab-ysqlb").show();
			$("#tab-ysqlb").jTable();
		}
	});
});
//列表编辑链接(明细)
function doDetail(row)
{
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/dealer/shfw/spgl/ysqgl/ysqmx.jsp", "ysqmxxx", "预授权明细信息", options,true);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 索赔管理  &gt; 预授权管理   &gt; 预授权查询</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-ysqcx">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-ysqcx">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>预授权单号：</label></td>
					    <td><input type="text" id="in-ysqdh" name="in-ysqdh" datatype="1,is_digit_letter,30" operation="like" /></td>
					    <td><label>发动机号：</label></td>
					    <td><input type="text" id="in-fdjh" name="in-fdjh" datatype="1,is_digit_letter,30" operation="like" /> </td>
					    <td ><label>VIN：</label></td> 
					    <td> <textarea  cols="18" name="in-vin" id="in-vin" rows="3" ></textarea></td> 
					</tr>
					<tr>
						<td><label>预授权状态：</label></td>
					    <td >
					         <select type="text" id="in-ysqzt"  name="in-ysqzt" datasource="PRETH_STATUS" kind="dic" src="E#1=已保存:2=审核驳回:3=审核通过" datatype="1,is_null,30" >
					    		<option value="-1" selected>--</option>
					    	</select></td>
					    <td><label>提报日期：</label></td>
					    <td colspan="3">
				    		<input type="text" group="in-kstbrq,in-jstbrq"  id="in-kstbrq"  name="in-kstbrq" style="width:75px;" datasource="REPORT_DATE" datatype="1,is_null,30" onclick="WdatePicker()" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="in-kstbrq,in-jstbrq"  id="in-jstbrq"  name="in-jstbrq" style="width:75px;margin-left:-30px;"  datasource="REPORT_DATE" datatype="1,is_null,30" onclick="WdatePicker()" />
				   		 </td>	
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-cx" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-add" onclick="doAdd();" >下&nbsp;&nbsp;载</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_ysqlb" >
			<table style="display:none;width:100%;" id="tab-ysqlb" name="tablist" ref="page_ysqlb" refQuery="fm-ysqcx" >
					<thead>
						<tr>
							<th fieldname="ROWNUMS" style="display:"></th>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="PRETH_NO" colwidth="60">预授权单号</th>
							<th fieldname="" colwidth="60">预授权状态</th>
							<th fieldname="VIN" colwidth="40">VIN</th>
							<th fieldname="" colwidth="60">发动机号</th>
							<th fieldname="" colwidth="60">车辆型号</th>
							<th fieldname="" colwidth="60">车牌号</th>
							<th fieldname="" colwidth="70">销售日期</th>
							<th fieldname="" colwidth="70">提报日期</th>
							<th fieldname="" colwidth="60">是否已报索赔单</th>
							<th colwidth="105" type="link" title="[明细]"  action="doDetail" >操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="rownums"><div>1</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>预授权单号1</div></td>
							<td><div>已保存</div></td>
							<td><div>VIN1</div></td>
							<td><div>发动机1</div></td>
							<td><div>车辆型号1</div></td>
							<td><div>车牌1</div></td>
							<td><div>2013-01-01</div></td>
							<td><div></div></td>
							<td><div>否</div></td>
							<td><a href="#" onclick="doDetail()" class="op">[明细]</a></td>
						</tr>
						<tr>
							<td class="rownums"><div>2</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>预授权单号2</div></td>
							<td><div>审核通过</div></td>
							<td><div>VIN2</div></td>
							<td><div>发动机2</div></td>
							<td><div>车辆型号2</div></td>
							<td><div>车牌2</div></td>
							<td><div>2013-01-01</div></td>
							<td><div>2013-05-28</div></td>
							<td><div>是</div></td>
							<td><a href="#" onclick="doDetail()" class="op">[明细]</a></td>
						</tr>
						<tr>
							<td class="rownums"><div>3</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>预授权单号3</div></td>
							<td><div>审核驳回</div></td>
							<td><div>VIN3</div></td>
							<td><div>发动机3</div></td>
							<td><div>车辆型号3</div></td>
							<td><div>车牌3</div></td>
							<td><div>2013-01-01</div></td>
							<td><div>2013-01-01</div></td>
							<td><div>否</div></td>
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