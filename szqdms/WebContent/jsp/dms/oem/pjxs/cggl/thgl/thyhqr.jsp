<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>退货验货确认</title>
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
function doCheck(row)
{
	var options = {max:true,mask:true,width:300,height:150,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/oem/pjxs/cggl/thgl/cgthqrmx.jsp", "cgthyhqrmx", "退货验货确认明细", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 采购管理  &gt; 退货管理   &gt; 退货验货确认</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-ddcx">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-ddcx">
					<!-- 定义查询条件 -->
					<tr>
				  	    <td><label>退货编号：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh" datasource="DDBH" datatype="1,is_digit_letter,30" operation="like" /></td>
					    <td><label>原采购单号：</label></td>
					    <td><input type="text" id="in-cgdh" name="in-cgdh" datatype="1,is_digit_letter,30" operation="like" /></td>
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
							<th fieldname="DDBH" >退货单号</th>
							<th fieldname="" >原采购单号</th>
							<th fieldname="" >退货类别</th>
							<th fieldname="" >供应商代码</th>
							<th fieldname="" >供应商名称</th>
							<th colwidth="105" type="link" title="[确认]"  action="doCheck" >操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="rownums"><div>1</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>退货单号1</div></td>
							<td><div>采购单号1</div></td>
							<td><div>采购退回</div></td>
							<td><div>供应商代码1</div></td>
							<td><div>供应商名称1</div></td>
							<td><a href="#" onclick="doCheck()" class="op">[确认]</a></td>
						</tr>
						<tr>
							<td class="rownums"><div>2</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>退货单号2</div></td>
							<td><div>采购单号2</div></td>
							<td><div>采购退回</div></td>
							<td><div>供应商代码1</div></td>
							<td><div>供应商名称1</div></td>
							<td><a href="#" onclick="doCheck()" class="op">[确认]</a></td>
						</tr>
					</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>