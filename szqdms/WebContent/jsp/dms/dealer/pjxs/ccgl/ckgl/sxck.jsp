<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>实销出库</title>
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
	
	$("#btn-xz").bind("click",function(event){
		var options = {max:true,mask:true,width:300,height:150,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps + "/jsp/dms/dealer/pjxs/ccgl/ckgl/sxckmx.jsp?action=1", "sxckxz", "实销出库新增", options);
	});
});
//列表编辑链接
function doUpdate(row)
{
	var options = {max:true,mask:true,width:300,height:150,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/dealer/pjxs/ccgl/ckgl/sxckmx.jsp?action=2", "sxckxg", "实销出库编辑", options);
}
function doSubmit(){
	alertMsg.confirm("确认实销出库?",{okCall:doApproveOk1,cancelCall:doApproveOk2});
}
function doDelete(){
	alertMsg.confirm("确认删除?",{okCall:doApproveOk1,cancelCall:doApproveOk2});
}
function doApproveOk1(){
	alertMsg.info("操作成功.");
}
function doApproveOk2(){
	return false;
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置：仓储管理  &gt; 出库管理   &gt; 实销出库</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-ddcx">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-ddcx">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>实销单号：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh" datasource="DDBH" datatype="1,is_digit_letter,30" operation="like" /></td>
					    <td><label>客户名称：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh" datasource="DDBH" datatype="1,is_digit_letter,30" operation="like" /></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-cx" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-xz" >新&nbsp;&nbsp;增</button></div></div></li>
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
							<th fieldname="DDBH" ordertype='local' class="desc">实销单号</th>
							<th fieldname="">客户名称</th>
							<th fieldname="">联系电话</th>
							<th fieldname="">联系地址</th>
							<th fieldname="">实销类型</th>
							<th colwidth="150" type="link" title="[编辑][实销出库][删除]"  action="doUpdate" >操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="rownums"><div>1</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>实销单号1</div></td>
							<td><div>客户名称1</div></td>
							<td><div>13201910022</div></td>
							<td><div>测试地址1</div></td>
							<td><div>实销出库</div></td>
							<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" onclick="doSubmit()" class="op">[实销出库]</a><a href="#" onclick="doDelete()" class="op">[删除]</a></td>
						</tr>
						<tr>
							<td class="rownums"><div>2</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>实销单号2</div></td>
							<td><div>客户名称2</div></td>
							<td><div>13201910033</div></td>
							<td><div>测试地址2</div></td>
							<td><div>实销出库</div></td>
							<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" onclick="doSubmit()" class="op">[实销出库]</a><a href="#" onclick="doDelete()" class="op">[删除]</a></td>
						</tr>
					</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>