<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>出库处理</title>
<script type="text/javascript">
//变量定义
//查询提交方法
var url = "<%=request.getContextPath()%>/Pjjcxxgl/PjdaglAction.do";
//初始化方法
$(function(){
	//查询方法
	$("#btn-cx").bind("click",function(event){
		if($("#tab-ddlb").is(":hidden"))
		{
			$("#tab-ddlb").show();
			$("#tab-ddlb").jTable();
		}
	});
	$("#btn-xz").bind("click",function(event){
		var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps + "/jsp/dms/oem/pjxs/ccgl/ckgl/ckdxz.jsp?action=1", "ckdmx", "出库单新增", options,true);
	});
});
function doUpdate(){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/oem/pjxs/ccgl/ckgl/ckdxz.jsp?action=2", "ckdmx", "出库单编辑", options,true);
}
function doSubmit(){
	alertMsg.confirm("确认出库?",{okCall:doApproveOk1,cancelCall:doApproveOk2});
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
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 仓储管理  &gt; 出库管理   &gt; 出库处理</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-ddcx">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-ckd">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>出库单号：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh" datasource="DDBH" datatype="1,is_digit_letter,30" operation="like" /></td>
					    <td><label>出库类型：</label></td>
					    <td>
					    	<input type="text" id="in-ddlx" name="in-ddlx" kind="dic" src="E#XSCK=销售出库:ZYCK=直营出库:YKCK=移库出库:CGTHCK=采购退货:QTCK=其他出库" datasource="DDLX" datatype="1,is_null,30"/>
					    </td>
					    <td><label>出库仓库：</label></td>
					    <td>
					    	<input type="text" id="in-ddlx" name="in-ddlx" kind="dic" src="E#1=本部库:2=待检库:3=报废库:4=军品库" datasource="DDLX" datatype="1,is_null,30"/>
					    </td>
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
		<div id="page_ddlb" >
			<table style="display:none;width:100%;" id="tab-ddlb" name="tablist" ref="page_ddlb" refQuery="fm-pjcx" >
					<thead>
						<tr>
							<th fieldname="ROWNUMS" style="display:"></th>
							<th fieldname="DDBH" colwidth="135">出库单号</th>
							<th fieldname="DDLX" >出库类型</th>
							<th fieldname="GHPJK">出库仓库</th>
							<th fieldname="GHPJK" colwidth="120">源单编号</th>
							<th fieldname="GHPJK">接收单位</th>
							<th fieldname="GHPJK">目标仓库</th>
							<th fieldname="SHY">计划金额</th>
							<th fieldname="SHY">经销金额</th>
							<th colwidth="115" type="link" title="[编辑]|[出库]|[删除]"  action="doUpate|doSubmit|doDelete" >操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="rownums"><div>1</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>YD20140519001CK</div></td>
							<td><div>销售出库</div></td>
							<td><div>本部库</div></td>
							<td><div>YD20140519001</div></td>
							<td><div>河南配送中心</div></td>
							<td><div></div></td>
							<td><div align="right">1,800.00</div></td>
							<td><div align="right">2,250.00</div></td>
							<td colwidth="150"><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" onclick="doSubmit()" class="op">[出库]</a><a href="#" onclick="doDelete()" class="op">[删除]</a></td>
						</tr>
						<tr>
							<td class="rownums"><div>1</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>YK20140519001CK</div></td>
							<td><div>移库出库</div></td>
							<td><div>本部库</div></td>
							<td><div></div></td>
							<td><div></div></td>
							<td><div>军品库</div></td>
							<td><div align="right">1,800.00</div></td>
							<td><div align="right">2,250.00</div></td>
							<td colwidth="150"><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" onclick="doSubmit()" class="op">[出库]</a><a href="#" onclick="doDelete()" class="op">[删除]</a></td>
						</tr>
						<tr>
							<td class="rownums"><div>1</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>CGTH20140519001CK</div></td>
							<td><div>采购退货</div></td>
							<td><div>本部库</div></td>
							<td><div>CGTH20140519001</div></td>
							<td><div>供应商一</div></td>
							<td><div></div></td>
							<td><div align="right">1,800.00</div></td>
							<td><div align="right">2,250.00</div></td>
							<td colwidth="150"><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" onclick="doSubmit()" class="op">[出库]</a><a href="#" onclick="doDelete()" class="op">[删除]</a></td>
						</tr>
						<tr>
							<td class="rownums"><div>1</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>ZY20140519001CK</div></td>
							<td><div>直营出库</div></td>
							<td><div>本部库</div></td>
							<td><div>ZY20140519001</div></td>
							<td><div>直营店</div></td>
							<td><div></div></td>
							<td><div align="right">1,800.00</div></td>
							<td><div align="right">2,250.00</div></td>
							<td colwidth="150"><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" onclick="doSubmit()" class="op">[出库]</a><a href="#" onclick="doDelete()" class="op">[删除]</a></td>
						</tr>
						<tr>
							<td class="rownums"><div>1</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>QT20140519001CK</div></td>
							<td><div>其他出库</div></td>
							<td><div>本部库</div></td>
							<td><div></div></td>
							<td><div></div></td>
							<td><div></div></td>
							<td><div align="right">1,800.00</div></td>
							<td><div align="right">2,250.00</div></td>
							<td colwidth="150"><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" onclick="doSubmit()" class="op">[出库]</a><a href="#" onclick="doDelete()" class="op">[删除]</a></td>
						</tr>
					</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>