 <?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>发运单承运商指定</title>
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
		$.pdialog.open(webApps + "/jsp/dms/oem/pjxs/ccgl/fygl/fydxz.jsp", "fydxz", "发运单新增", options,true);
	});
});

//查看订单详细信息
function doViewOrderDetail(orderNo)
{
	var w = document.documentElement.clientWidth-100;
	var options = {max:false,width:w,height:500,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/dealer/pjxs/xsgl/ddgl/ddmx.jsp", "ddmx", "订单明细", options, true);
}
//列表审核链接
function doUpdate(row)
{
	var w = document.documentElement.clientWidth-100;
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/oem/pjxs/ccgl/fygl/fydxz.jsp", "fldmx", "发运单修改", options,true);
}
function doSubmit(){
	alertMsg.confirm("确认提交?",{okCall:doApproveOk1,cancelCall:doApproveOk2});
}
function toDelete(){
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
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 仓储管理  &gt; 发运管理   &gt; 发运单承运商指定</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-ddcx">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-ddcx">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>发运单号：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh" datasource="DDBH" datatype="1,is_digit_letter,30" operation="like" /></td>
					    <td><label>承运单位：</label></td>
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
		<div id="page_ddlb" >
			<table style="display:none;width:100%;" id="tab-ddlb" name="tablist" ref="page_ddlb" refQuery="fm-pjcx" >
					<thead>
						<tr>
							<th fieldname="ROWNUMS" style="display:"></th>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="DDBH" ordertype='local' class="desc">发运单号</th>
							<th fieldname="GHPJK" >承运单位</th>
							<th fieldname="GHPJK" >联系人</th>
							<th fieldname="GHPJK" >联系电话</th>
							<th fieldname="TBSJ" ordertype='local' class="desc">制单人</th>
							<th fieldname="SHY" >制单日期</th>
							<th colwidth="120" type="link" title="[提交]|[编辑]|[删除]"  action="doSubmit|doUpdate|doDelete" >操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="rownums"><div>1</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>FYD20140519001</div></td>
							<td><div>物流公司一</div></td>
							<td><div>王五</div></td>
							<td><div>029-85850355</div></td>
							<td><div>张三</div></td>
							<td><div>2014-05-20 8:30:26</div></td>
							<td><a href="#" onclick="doSubmit()" class="op">[提交]</a><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" onclick="toDelete()" class="op">[删除]</a></td>
						</tr>
						<tr>
							<td class="rownums"><div>2</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>FYD20140519002</div></td>
							<td><div>物流公司一</div></td>
							<td><div>王五</div></td>
							<td><div>029-85850355</div></td>
							<td><div>张三</div></td>
							<td><div>2014-05-20 8:30:26</div></td>
							<td><a href="#" onclick="doSubmit()" class="op">[提交]</a><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" onclick="toDelete()" class="op">[删除]</a></td>						</tr>
						<tr>
							<td class="rownums"><div>3</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>FYD20140519003</div></td>
							<td><div>物流公司二</div></td>
							<td><div>王五</div></td>
							<td><div>029-85850356</div></td>
							<td><div>张三</div></td>
							<td><div>2014-05-20 8:30:26</div></td>
							<td><a href="#" onclick="doSubmit()" class="op">[提交]</a><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" onclick="toDelete()" class="op">[删除]</a></td>
						</tr>
					</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>