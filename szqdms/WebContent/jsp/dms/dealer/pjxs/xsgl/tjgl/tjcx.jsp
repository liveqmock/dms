<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>退件查询</title>
<script type="text/javascript">
//变量定义
//初始化方法
$(function(){
	//查询方法
	$("#btn-cx").bind("click",function(event){
		if($("#tab-pjlb").is(":hidden"))
		{
			$("#tab-pjlb").show();
			$("#tab-pjlb").jTable();
		}
	});
});
//列表编辑链接
function doDetail(row)
{
	var options = {max:true,mask:true,width:300,height:150,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/dealer/pjxs/xsgl/tjgl/tjcxmx.jsp", "tjcxmx", "退件查询明细", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 销售管理  &gt; 退货管理   &gt; 退件查询</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-ddcx">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-ddcx">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>服务站代码：</label></td>
					    <td><input type="text" id="in-dealercode" name="in-dealercode" datasource="DEALER_CODE" datatype="1,is_digit_letter,30" operation="like" /></td>
				    	<td><label>服务站名称：</label></td>
					    <td><input type="text" id="in-dealername" name="in-dealername" datasource="DEALER_NAME" datatype="1,is_digit_letter,30" operation="like" /></td>
					    <td><label>状态：</label></td>
					    <td>
					    	<select type="text" id="in-ddzt"  name="in-ddzt" datasource="DDZT" kind="dic" src="E#1=已提交:2=审核驳回:3=审核通过" datatype="1,is_null,30" >
					    		<option value="-1" selected>--</option>
					    	</select>
					    </td>
					</tr>
					<tr>
						<td><label>退件单号：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh" datasource="DDBH" datatype="1,is_digit_letter,30" operation="like" /></td>
				    	<td><label>申请日期：</label></td>
					    <td colspan="3">
				    		<input type="text" group="in-kstbrq,in-jstbrq"  id="in-kstbrq"  name="in-kstbrq" style="width:75px;" datasource="TBRQ" datatype="1,is_null,30" onclick="WdatePicker()" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="in-kstbrq,in-jstbrq"  id="in-jstbrq"  name="in-jstbrq" style="width:75px;margin-left:-30px;"  datasource="CKRQ" datatype="1,is_null,30" onclick="WdatePicker()" />
				   		 </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-cx" >查&nbsp;&nbsp;询</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_yhlb" >
			<table style="display:none;width:100%;" id="tab-pjlb" name="tablist" ref="page_pjlb" refQuery="fm-pjcx" >
					<thead>
						<tr>
							<th fieldname="ROWNUMS" style="display:"></th>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="DDBH" ordertype='local' class="desc">退件单号</th>
							<th fieldname="" >接收方</th>
							<th fieldname="" >服务代码</th>
							<th fieldname="" >服务站名称</th>
							<th fieldname="" >申请日期</th>
							<th fieldname="" >状态</th>
							<th colwidth="105" type="link" title="[明细]"  action="doDetail" >操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="rownums"><div>1</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>退件单号1</div></td>
							<td><div>配送中心1</div></td>
							<td><div>服务站代码1</div></td>
							<td><div>服务站名称1</div></td>
							<td><div>2014-05-20</div></td>
							<td><div>已提交</div></td>
							<td><a href="#" onclick="doDetail()" class="op">[明细]</a></td>
						</tr>
						<tr>
							<td class="rownums"><div>2</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>退件单号2</div></td>
							<td><div>配送中心1</div></td>
							<td><div>服务站代码2</div></td>
							<td><div>服务站名称2</div></td>
							<td><div>2014-05-20</div></td>
							<td><div>审核驳回</div></td>
							<td><a href="#" onclick="doDetail()" class="op">[明细]</a></td>
						</tr>
						<tr>
							<td class="rownums"><div>3</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>退件单号3</div></td>
							<td><div>配件销售公司</div></td>
							<td><div>配送中心代码</div></td>
							<td><div>配送中心名称</div></td>
							<td><div>2014-05-20</div></td>
							<td><div>审核通过</div></td>
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