<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String inId = request.getParameter("inId");
%>
<div style="width: 100%;">
	<div class="page">
		<div class="pageHeader" >
					<div class="searchBar" align="left" >
						<table class="searchContent" id="detailsTable">
							<tr>
								<td><label>入库单号：</label></td>
								<td><input type="text" id="IN_NO_D" /></td>
								<td><label>采购单号：</label></td>
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
								<td><label>入库类型：</label></td>
								<td>
									<select id="IN_TYPE_D" class="combox">
										<option value="<%=DicConstant.RKLX_01 %>" selected="selected">采购入库</option>
										<option value="<%=DicConstant.RKLX_02 %>">移库入库</option>
										<option value="<%=DicConstant.RKLX_03 %>">销售退件</option>
										<option value="<%=DicConstant.RKLX_04 %>">其他入库</option>
									</select>
								</td>
								<td><label>入库日期：</label></td>
								<td>
									<input type="text" id="IN_DATE_D" /> 
								</td>
								<td><label>供应商：</label></td>
								<td><input type="text" id="SUPPLIER_NAME_D" /></td>
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
								<th fieldname="PART_CODE" colwidth="175px">配件代码</th>
								<th fieldname="PART_NAME" colwidth="80px">配件名称</th>
								<th fieldname="IN_AMOUNT" colwidth="60px">入库数量</th>
								<th fieldname="REMARKS" colwidth="140px">备注</th>
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
	<input type="hidden" datasource="IN_ID" value="<%=inId%>" />
</form>
<script type="text/javascript">

/**
 * 主信息查询加载
 */
$(function(){
	var inId = "<%=inId%>";	// 主信息ID
	var getDetailsURL = "<%=request.getContextPath()%>/part/storageMng/search/StockInQueryAction/queryStockInfoById.ajax"; // 查询ActionURL
	sendPost(getDetailsURL+"?inId="+inId,"","",callbackShowDetailsInfo,null,null);										   // 调用后台查询Action
	
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
		$("#PRINT_STATUS_D").val(rowData["PRINT_STATUS_D"]+"").prop("disabled", true);
		$("#IN_TYPE_D").val(rowData["IN_TYPE_D"]+"").prop("disabled", true);
		
		// 调用显示主信息的方法
		showDetailsInfo();
	}
});

// 显示入库单详细信息
function showDetailsInfo(){
	var searchURL = "<%=request.getContextPath()%>/part/storageMng/search/StockInQueryAction/queryStockDetailsById.ajax";
    var $f = $("#detailsInfoShow");
    var sCondition = {};
    sCondition = $f.combined() || {};
    doFormSubmit($f, searchURL, "btn-close", 1, sCondition, "tab-contract");
}

</script>