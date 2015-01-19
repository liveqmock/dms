<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.org.dms.common.DicConstant"%>

<style type="text/css">
.searchBar1 table{
	border-collapse:collapse!important;
}
.searchContent1 tr th{
	background:#E2F0FF url(<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()%>/themes/default/images/grid/header-bg.gif) repeat-x left -1px;
	border-color:#A3C0E8;
	color:#00579b;
	font-size:13px;
	font-weight:normal;
	height:25px;
	line-height:25px;
	padding:0 3px;
	vertical-align:top;
	white-space:nowrap;
}
.searchContent1 tr td, .searchContent1 tr th{
	border: 1px solid #99BBE8;
}
.searchContent1 tr td{
	line-height:24px;
	white-space:nowrap;
	overflow:hidden;
	padding:0 3px;
	vertical-align:middle;
	background-color:white;
}

.searchContent1 tr td{
	TEXT-ALIGN: center; BORDER-LEFT: #99bbe8 1px solid; BACKGROUND-COLOR: #e0ecff;
}

.searchBar1 li {
	float: right;
}
</style>
	<div style="width: 100%;">
		<div class="page">
			<div class="pageHeader">
				<form method="post" id="fm-searchContract-ss">
					<div class="searchBar1" align="left">
						<table class="searchContent1">
							<!-- 定义查询条件 -->
							<tbody>
							<tr>
								<td><label>供应商名称</label></td>
								<td><input type="text" id="SUPPLIER_NAME" name="SUPPLIER_NAME" style="margin: 0px" datatype="1,is_digit_letter_cn,30" dataSource="SUPPLIER_NAME" operation="like" /></td>
								<td><label>供应商代码</label></td>
								<td><input type="text" id="SUPPLIER_CODE" name="SUPPLIER_CODE" style="margin: 0px"  datatype="1,is_digit_letter,30" dataSource="SUPPLIER_CODE" operation="like" /></td>
							</tr>
							</tbody>
						</table>
						<div class="subBar">
							<ul>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search-ss">查&nbsp;&nbsp;询</button></div></div></li>
								<li style="display: none;"><div class="button"><div class="buttonContent"><button class="close" id="getSupplierIdBtn" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
							</ul>
						</div>
					</div>
				</form>
			</div>
			<div class="pageContent">
				<div id="page_contract_ss">
					<table style="display:none; width: 100%;" id="tab-contract-ss"  ref="page_contract_ss" refQuery="fm-searchContract-ss">
						<thead>
							<tr>
								<th type="single" name="SUPPLIER_ID">选择</th>
								<th fieldname="SUPPLIER_NAME">供应商名称</th>
								<th fieldname="SUPPLIER_CODE">供应商代码</th>
								<th fieldname="IF_ARMY">是否军品供应商</th>
								<th fieldname="SE_ACCESS">服务负责人</th>
								<th fieldname="SE_ACCESS_TEL">服务联系电话</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

<script type="text/javascript">
$(function(){

    $("#btn-search-ss").click(function(){
    	var searchURL = "<%=request.getContextPath()%>/part/mainTenanceApplication/MainTenanceApplicationAction/suplierQuery.ajax";
        var $f = $("#fm-searchContract-ss");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, searchURL, "btn-search-ss", 1, sCondition, "tab-contract-ss");
    });

});

// 点击点选按钮触发
function doRadio(obj){
	var supplierId = $(obj).parent().parent().parent().attr("SUPPLIER_ID");
	var supplierName = $(obj).parent().parent().parent().attr("SUPPLIER_NAME");
	var supplierArray = [];
	supplierArray.push(supplierId);
	supplierArray.push(supplierName);
	// 调用回调函数
	var parentObj = self.frames[0];
	parentObj.callbackSupplierInfo(supplierArray);
	$("#getSupplierIdBtn").click();
}


</script>