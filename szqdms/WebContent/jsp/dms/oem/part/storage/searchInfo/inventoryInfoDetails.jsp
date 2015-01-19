<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>车厂库存查询——详情</title>
<script type="text/javascript">

$(function(){
	
	// 获取Index页面审核行对象
	var partCode = parent.$("#invertoryTable").getSelectedRows()[0].attr("PART_CODE");
	var getPartInfoAction = "<%=request.getContextPath()%>/part/storageMng/search/InventoryQueryAction/queryPartInfoByPartCode.ajax?partCode="+partCode;

	// 根据PartCode查询详细信息
	sendPost(getPartInfoAction,"","",callbackFuncion,null,null);
	
	// 关闭按钮
	$("#closeButton").click(function(){
		parent.$.pdialog.closeCurrent();
	});
	
	// 根据PartCode查询配件信息的回调函数
	function callbackFuncion(res,sData){
		
		// 此变量保存回调对象中包含的零件JSON信息
		var applicationInfo;
		
		// 判断浏览器
		var explorer = window.navigator.userAgent;
		// 包含MSIE字符则为IE浏览器，MSIE10.0为IE10，去除IE10浏览器，其他浏览器使用其他另外的读取方式
		if(explorer.indexOf("MSIE") != -1 && explorer.indexOf("MSIE 10.0") == -1){
			applicationInfo = res.text;
		}else{
			applicationInfo = res.firstChild.textContent;
		}
		
		// 显示对应的值
		showInfo(eval("(" + applicationInfo + ")"))
	}
	
	// 循环显示
	function showInfo(jsonObj){
 		$("body").find("input").each(function(index,obj){
			var objVal = jsonObj[$(obj).attr("id")];
			if(objVal){
				$(obj).val(objVal);
			}
		}); 
	}
	
})
</script>

</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<div class="page">
		<div class="pageContent" style="" >
			<form method="post" id="dia-oldpartfm" class="editForm" >
				<div align="left">
				<table class="editTable" id="dia-oldpartTab">
				    <tr>
						<td ><label>仓库代码：</label></td>
						<td colspan="3"><input type="text" id="WAREHOUSE_CODE"  value="" readonly="readonly"/></td>
						<td ><label>仓库名称：</label></td>
						<td colspan="3"><input type="text" id="WAREHOUSE_NAME"  value="" readonly="readonly"/></td>
						<td ><label>库区代码：</label></td>
						<td colspan="3"><input type="text" id="AREA_CODE"  value="" readonly="readonly"/></td>
						<td ><label>库区名称：</label></td>
						<td colspan="3"><input type="text" id="AREA_NAME"  value="" readonly="readonly"/></td>
					</tr>
				    <tr>
						<td ><label>库位代码：</label></td>
						<td colspan="3"><input type="text" id="POSITION_CODE"  value="" readonly="readonly"/></td>
						<td ><label>库位名称：</label></td>
						<td colspan="3"><input type="text" id="POSITION_NAME"  value="" readonly="readonly"/></td>
						<td ><label>配件代码：</label></td>
						<td colspan="3"><input type="text" id="PART_CODE"  value="" readonly="readonly"/></td>
						<td ><label>配件名称：</label></td>
						<td colspan="3"><input type="text" id="PART_NAME"  value="" readonly="readonly"/></td>
					</tr>
				    <tr>
						<td ><label>供应商代码：</label></td>
						<td colspan="3"><input type="text" id="SUPPLIER_CODE"  value="" readonly="readonly"/></td>
						<td ><label>供应商名称：</label></td>
						<td colspan="3"><input type="text" id="SUPPLIER_NAME"  value="" readonly="readonly"/></td>
						<td ><label>库存数量：</label></td>
						<td colspan="3"><input type="text" id="AMOUNT"  value="" readonly="readonly"/></td>
						<td ><label>占用数量：</label></td>
						<td colspan="3"><input type="text" id="OCCUPY_AMOUNT"  value="" readonly="readonly"/></td>
					</tr>
				    <tr>
						<td ><label>可用数量：</label></td>
						<td colspan="3"><input type="text" id="AVAILABLE_AMOUNT"  value="" readonly="readonly"/></td>
						<td ><label>库管员姓名：</label></td>
						<td colspan="3"><input type="text" id="USER_NAME"  value="" readonly="readonly"/></td>
						<td ><label>库管员账号：</label></td>
						<td colspan="3"><input type="text" id="USER_ACCOUNT"  value="" readonly="readonly"/></td>
						<td ><label>计划价格：</label></td>
						<td colspan="3"><input type="text" id="PLAN_PRICE"  value="" readonly="readonly"/></td>
					</tr>
				    <tr>
						<td ><label>销售价格：</label></td>
						<td colspan="3"><input type="text" id="SALE_PRICE"  value="" readonly="readonly"/></td>
						<td ><label>最小包装数：</label></td>
						<td colspan="3"><input type="text" id="MIN_PACK"  value="" readonly="readonly"/></td>
						<td ><label>库存上限：</label></td>
						<td colspan="3"><input type="text" id="UPPER_LIMIT"  value="" readonly="readonly"/></td>
						<td ><label>库存下限：</label></td>
						<td colspan="3"><input type="text" id="LOWER_LIMIT"  value="" readonly="readonly"/></td>
					</tr>
				</table>
				</div>
			</form>
		</div>
		<div class="formBar">
				<ul>
					<li><div class="button"><div class="buttonContent"><button class="close" type="button" id="closeButton">关&nbsp;&nbsp;闭</button></div></div></li>
				</ul>
			</div>
		</div>
</div>
</body>
</html>