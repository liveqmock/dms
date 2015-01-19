 <?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>车厂库存查询</title>
<script type="text/javascript">
//变量定义
//查询提交方法
var url = "<%=request.getContextPath()%>/Pjjcxxgl/PjdaglAction.do";
//初始化方法
$(function(){
	//查询方法
	$("#btn-cx").bind("click",function(event){
		if($("#tab-hzlb").is(":hidden"))
		{
			$("#page_kchzlb").show();
			$("#tab-hzlb").show();
			$("#tab-hzlb").jTable();
		}
	});
	$("#btn-xz").bind("click",function(event){
		alertMsg.info("下载库存Excel!");
	});
});

//列表审核链接
function doUpdate(row)
{
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/oem/pjxs/ccgl/fygl/fyckmx.jsp", "fldmx", "发运单信息维护", options,true);
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
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 仓储管理  &gt; 库存管理   &gt; 渠道库存查询</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-kccx">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-kccx">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>渠道代码：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh" datasource="DDBH" datatype="1,is_null,30" operation="like" /></td>
					    <td><label>渠道名称：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh" datasource="DDBH" datatype="1,is_null,30" operation="like" /></td>
					</tr>
					<tr>
					    <td><label>配件代码：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh"  datasource="DDBH" datatype="1,is_null,30" operation="like" /></td>
					    <td><label>配件名称：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh" datasource="DDBH" datatype="1,is_null,30" operation="like" /></td>
				   </tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-cx" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-xz" >下&nbsp;&nbsp;载</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_kchzlb" >
			<table style="display:none;width:100%;" id="tab-hzlb" name="tablist" ref="page_kchzlb" refQuery="fm-kccx" >
					<thead>
						<tr>
							<th fieldname="ROWNUMS" style="display:"></th>
							<th fieldname="DDBH" >渠道代码</th>
							<th fieldname="DDBH" >渠道名称</th>
							<th fieldname="DDBH" colwidth="100">配件代码</th>
							<th fieldname="GHPJK" colwidth="100">配件名称</th>
							<th fieldname="GHPJK" colwidth="80">库存数量</th>
							<th fieldname="GHPJK" colwidth="80">占用数量</th>
							<th fieldname="GHPJK" colwidth="80">可用库存</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="rownums"><div>1</div></td>
							<td><div>QD0001</div></td>
							<td><div>配送中心一</div></td>
							<td><div>PJ0001</div></td>
							<td><div>配件一</div></td>
							<td><div>50</div></td>
							<td><div>30</div></td>
							<td><div>20</div></td>
						</tr>
						<tr>
							<td class="rownums"><div>2</div></td>
							<td><div>QD0001</div></td>
							<td><div>配送中心一</div></td>
							<td><div>PJ0002</div></td>
							<td><div>配件二</div></td>
							<td><div>50</div></td>
							<td><div>30</div></td>
							<td><div>20</div></td>
						</tr>
						<tr>
							<td class="rownums"><div>3</div></td>
							<td><div>QD0001</div></td>
							<td><div>配送中心一</div></td>
							<td><div>PJ0003</div></td>
							<td><div>配件三</div></td>
							<td><div>50</div></td>
							<td><div>30</div></td>
							<td><div>20</div></td>
						</tr>
						<tr>
							<td class="rownums"><div>4</div></td>
							<td><div>QD0001</div></td>
							<td><div>配送中心一</div></td>
							<td><div>PJ0004</div></td>
							<td><div>配件四</div></td>
							<td><div>50</div></td>
							<td><div>30</div></td>
							<td><div>20</div></td>
						</tr>
						<tr>
							<td class="rownums"><div>5</div></td>
							<td><div>QD0002</div></td>
							<td><div>配送中心二</div></td>
							<td><div>PJ0001</div></td>
							<td><div>配件一</div></td>
							<td><div>50</div></td>
							<td><div>30</div></td>
							<td><div>20</div></td>
						</tr>
						<tr>
							<td class="rownums"><div>6</div></td>
							<td><div>QD0002</div></td>
							<td><div>配送中心二</div></td>
							<td><div>PJ0002</div></td>
							<td><div>配件二</div></td>
							<td><div>50</div></td>
							<td><div>30</div></td>
							<td><div>20</div></td>
						</tr>
					</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>