<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("id");
%>
<div style="width: 100%;">
	<div class="page">
		<div class="pageHeader" >
					<div class="searchBar" align="left" >
						<table class="searchContent" id="detailsTable">
							<tr>
								<td><label>采购退货单号：</label></td>
								<td><input type="text" id="RETURN_NO_D" /></td>
								<td><label>制单日期：</label></td>
								<td><input type="text" id="ORDER_DATE_D" /></td>
								<td><label>制单人：</label></td>
								<td><input type="text" id="ORDER_USER_D" /></td>
							</tr>
							<tr>
								<td><label>退货单状态：</label></td>
								<td><input type="text" id="RETURN_STATUS_D" /></td>
								<td><label>供应商代码：</label></td>
								<td><input type="text" id="SUPPLIER_CODE_D" /></td>
								<td><label>供应商名称：</label></td>
								<td><input type="text" id="SUPPLIER_NAME_D" /></td>
							</tr>
							<tr>
								<td><label>签收状态：</label></td>
								<td colspan="5">
									<input type="text" id="SIGN_STAUTS_D" /> 
								</td>
							</tr>
						</table>
					</div>
		</div>
		<div class="pageHeader" >
		<form method="post" id="fm-searchPchOrder-details">
			<!-- 定义隐藏域条件 -->
			<input type="hidden" id="returnId-details" name="returnId-dtetails" dataSource="RETURN_ID" value="<%=id%>" datatype="1,is_null,600" operation="="/>
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-orderSearch-details">
					<tr>
						<td><label>配件代码：</label></td>
						<td>
							<input type="text" id="PART_CODE_DETAILS" name="PART_CODE" datatype="1,is_null,600" operation="like" dataSource="I.PART_CODE"  />
						</td>
						<td><label>配件名称：</label></td>
						<td>
							<input type="text" id="PART_NAME_DETAILS" name="PART_NAME" datatype="1,is_null,600" operation="like" dataSource="I.PART_NAME" />
						</td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search-details" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button class="close" type="button" id="btn-close" >关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
		</div>
		<div class="pageContent">
			<div id="page_contract_d">
				<table style="display:none;width:100%;" id="tab-contract" name="tablist" ref="page_contract_d" refQuery="fm-searchPchOrder-details">
						<thead>
							<tr>
								<th fieldname="PART_CODE" colwidth="150px">配件代码</th>
								<th fieldname="PART_NAME" colwidth="150px">配件名称</th>
								<th fieldname="COUNT" >退货数量</th>
								<th fieldname="AMOUNT" >退货金额</th>
								<th fieldname="SUPPLIER_CODE" colwidth="120px">供应商代码</th>
								<th fieldname="SUPPLIER_NAME" colwidth="150px">供应商名称</th>
<!-- 								<th fieldname="PCH_PRICE" >采购价格</th>
								<th fieldname="PLAN_PRICE" >计划价格</th> -->
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

/**
 * 主信息查询加载
 */
$(function(){
	
	$("#btn-search-details").click(function(){
		var searchURL = "<%=request.getContextPath()%>/part/purchaseMng/purchaseOrder/PchReturnOrderQueryAction/queryDetailsById.ajax";
	    var $f = $("#fm-searchPchOrder-details");
	    var sCondition = {};
	    sCondition = $f.combined() || {};
	    doFormSubmit($f, searchURL, "btn-search-details", 1, sCondition, "tab-contract");
	});
	
	var id = "<%=id%>";	// 主信息ID
	var getDetailsURL = "<%=request.getContextPath()%>/part/purchaseMng/purchaseOrder/PchReturnOrderQueryAction/queryInfoById.ajax"; // 查询ActionURL
	sendPost(getDetailsURL+"?id="+id,"","",callbackShowDetailsInfo,null,null);										   // 调用后台查询Action
	
	// 查询回调函数
	function callbackShowDetailsInfo(res,sData){
		var applicationInfo;							// 此变量保存回调对象中包含的后台查询到的数据
		var explorer = window.navigator.userAgent;		// 判断浏览器
		// 包含MSIE字符则为IE浏览器，MSIE10.0为IE10，去除IE10浏览器，其他浏览器使用其他另外的读取方式
		if(explorer.indexOf("MSIE") != -1 && explorer.indexOf("MSIE 10.0") == -1){
			applicationInfo = res.text;
		}else{
			applicationInfo = res.firstChild.textContent;
		}
		
		// 调用显示主信息的函数
		showApplicationInfo(eval("(" + applicationInfo + ")"))
	}
	
	// 显示申请单主信息
	function showApplicationInfo(jsonObj){
		var rowData = jsonObj["ROW_0"];			// 获取第一行的数据
		$("input","#detailsTable").each(function(index,obj){
			var inputName = $(obj).attr("id")
			$(obj).val(rowData[inputName]).prop("readonly", true);
		});
		
		// 调用显示主信息的方法
		$("#btn-search-details").click();
	}
	

});


</script>