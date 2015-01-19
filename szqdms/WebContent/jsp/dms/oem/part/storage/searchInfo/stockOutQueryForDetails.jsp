<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String outId = request.getParameter("outId");
%>
<div style="width: 100%;">
	<div class="page">
		<div class="pageHeader" >
					<div class="searchBar" align="left" >
						<table class="searchContent" id="detailsTable">
							<tr>
								<td><label>出库单号：</label></td>
								<td><input type="text" id="OUT_NO_D" /></td>
								<td><label>订单号：</label></td>
								<td><input type="text" id="ORDER_NO_D" /></td>
								<td><label>仓库：</label></td>
								<td><input type="text" id="WAREHOUSE_NAME_D" /></td>
							</tr>
							<tr>
								<td><label>是否打印：</label></td>
								<td>
									<select class="combox" id="PRINT_STATUS_D" >
										<option value="<%=DicConstant.DYZT_01%>" selected="selected">未打印</option>
										<option value="<%=DicConstant.DYZT_02%>">已打印</option>
									</select>	
								</td>
								<td><label>打印人：</label></td>
								<td><input type="text" id="PRINT_MAN_D" /></td>
								<td><label>打印日期：</label></td>
								<td>
									<input type="text" id="PRINT_DATE_D" /> 
								</td>
							</tr>
							<tr>
								<td><label>出库类型：</label></td>
								<td>
									<select id="OUT_TYPE_D" class="combox">
										<option value="<%=DicConstant.CKLX_01 %>" selected="selected">销售出库</option>
										<option value="<%=DicConstant.CKLX_02 %>">直营出库</option>
										<option value="<%=DicConstant.CKLX_03 %>">移库出库</option>
										<option value="<%=DicConstant.CKLX_04 %>">采购退货</option>
										<option value="<%=DicConstant.CKLX_05 %>">其他出库</option>
										<option value="<%=DicConstant.CKLX_06 %>">三包急件出库</option>
									</select>
								</td>
								<td><label>其他出库类型：</label></td>
								<td colspan="3">
									<input type="text" id="OTHER_OUT_TYPE_D" /> 
								</td>
							</tr>
							<tr>
								<td><label>出库日期：</label></td>
								<td>
									<input type="text" id="OUT_DATE_D" /> 
								</td>
								<td><label>收货部门：</label></td>
								<td colspan="3"><input type="text" id="DEPARTEMENT_NAME_D" /></td>
							</tr>
						</table>
						<div class="subBar">
							<ul>
								<li><div class="buttonActive"><div class="buttonContent"><button class="close" type="button" id="btn-close" >关&nbsp;&nbsp;闭</button></div></div></li>
							</ul>
						</div>
					</div>
		</div>
		<div class="pageContent">
			<div id="page_contract_d">
				<table style="display:none;width:100%;" id="tab-contract" name="tablist" ref="page_contract_d" refQuery="detailsInfoShow">
						<thead>
							<tr>
								<th fieldname="PART_CODE" colwidth="160px">配件代码</th>
								<th fieldname="PART_NAME" colwidth="160px">配件名称</th>
								<th fieldname="PLAN_PRICE" refer="amountFormat" align="right" style="display:none">计划价格</th>
								<th fieldname="PLAN_AMOUNT" refer="amountFormat" align="right" style="display:none">计划金额</th>
								<th fieldname="OUT_AMOUNT" colwidth="60px">出库数量</th>
								<th fieldname="SHOULD_COUNT" colwidth="60px">应发数量</th>
								<th fieldname="REMARKS" colwidth="180px">备注</th>
							</tr>
						</thead>
						<tbody>
	                    </tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<form id="detailsInfoShow" method="post" style="display:none">
	<input type="hidden" datasource="T.OUT_ID" value="<%=outId%>" />
</form>
<script type="text/javascript">

/**
 * 主信息查询加载
 */
$(function(){
	var outId = "<%=outId%>";	// 主信息ID
	var getDetailsURL = "<%=request.getContextPath()%>/part/storageMng/search/StockOutQueryAction/queryStockInfoById.ajax"; // 查询ActionURL
	sendPost(getDetailsURL+"?outId="+outId,"","",callbackShowDetailsInfo,null,null);										   // 调用后台查询Action
	
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
		showApplicationInfo(eval("(" + applicationInfo + ")"));
	}
	
	// 显示申请单主信息
	function showApplicationInfo(jsonObj){
		var rowData = jsonObj["ROW_0"];			// 获取第一行的数据
		$("input","#detailsTable").each(function(index,obj){
			var inputName = $(obj).attr("id")
			$(obj).val(rowData[inputName]).prop("readonly", true);
		});
		$("#PRINT_STATUS_D").val(rowData["PRINT_STATUS_D"]+"").prop("disabled", true);	// 是否打印
		$("#OUT_TYPE_D").val(rowData["OUT_TYPE_D"]+"").prop("disabled", true);			// 
		
		// 调用显示主信息的方法
		showDetailsInfo();
	}
});

// 显示入库单详细信息
function showDetailsInfo(){
	var searchURL = "<%=request.getContextPath()%>/part/storageMng/search/StockOutQueryAction/queryStockDetailsById.ajax";
    var $f = $("#detailsInfoShow");
    var sCondition = {};
    sCondition = $f.combined() || {};
    doFormSubmit($f, searchURL, "btn-close", 1, sCondition, "tab-contract");
}

</script>