<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("id");
%>
<div style="width: 100%;">
	<div class="page">
		<div class="pageHeader" >
					<div class="searchBar" align="left" >
						<table class="searchContent">
							<tr>
								<td><label>订单编号：</label></td>
								<td><input type="text" id="SPLIT_NO_D" /></td>
								<td><label>计配号：</label></td>
								<td><input type="text" id="PLAN_DISTRIBUTION_D" /></td>
								<td><label>供应商名称：</label></td>
								<td><input type="text" id="SUPPLIER_NAME_D" /></td>
							</tr>
							<tr>
								<td><label>采购类型：</label></td>
								<td>
									<input type="text" id="PURCHASE_TYPE_D" />
								</td>
								<td><label>所属月度：</label></td>
								<td><input type="text" id="SELECT_MONTH_D" /></td>
								<td><label>制单日期：</label></td>
								<td>
									<input type="text" id="APPLY_DATE_D" /> 
								</td>
							</tr>
							<tr>
								<td><label>采购单状态：</label></td>
								<td>
									<input type="text" id="ORDER_STATUS_D" /> 
								</td>
								<td><label>制单人：</label></td>
								<td colspan="3">
									<input type="text" id="APPLY_USER_D" /> 
								</td>
							</tr>
						</table>
						<div class="subBar">
							<ul>
								<li><div class="buttonActive"><div class="buttonContent"><button class="close" type="button" id="btn-close" >关&nbsp;&nbsp;闭</button></div></div></li>
								<li><div class="button"><div class="buttonContent"><button type="button" id="btn-export-index">导出数据</button></div></div></li>
							</ul>
						</div>
					</div>
		</div>
		<form id="exportFm" method="post" style="display:none">
         	<input type="hidden" id="data" name="data"></input>
        </form>
		<div class="pageContent">
			<div id="page_contract_d">
				<table style="display:none;width:100%;" id="tab-contract" name="tablist" ref="page_contract_d" refQuery="detailsInfoShow">
						<thead>
							<tr>
							<th fieldname="PART_CODE">配件代码</th>
                            <th fieldname="PART_NAME">配件名称</th>
                            <th fieldname="UNIT" colwidth="70px">计量单位</th>
							<th fieldname="MIN_PACK" colwidth="70px">最小包装</th>
                            <th fieldname="PCH_PRICE" refer="amountFormatStr" align="right">采购价格</th>
                            <th fieldname="PCH_COUNT" colwidth="75px" >采购数量</th>
                            <th fieldname="PCH_AMOUNT" refer="amountFormatStr" align="right">采购金额</th>
                            <th fieldname="STORAGE_COUNT" colwidth="70px">入库数量</th>
                            <th fieldname="STORAGE_COUNT" colwidth="70px" >开票数量</th>
                            <th fieldname="REMARKS">备注</th>
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
	<input type="hidden" datasource="SPLIT_ID" name="SPLIT_ID" id="SPLIT_ID" value="<%=id%>" />
</form>
<script type="text/javascript">

/**
 * 主信息查询加载
 */
$(function(){
	var id = "<%=id%>";	// 主信息ID
	var getDetailsURL = "<%=request.getContextPath()%>/part/purchaseMng/purchaseOrder/PchOrderForDealerQueryAction/queryInfoById.ajax"; // 查询ActionURL
	sendPost(getDetailsURL+"?id="+id,"","",callbackShowDetailsInfo,null,null);										   // 调用后台查询Action
	
	$("#btn-export-index").click(function(){
	     var searchContractPartUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseOrder/PchOrderForDealerQueryAction/download.do?SPLIT_ID="+id;
	     var url = encodeURI(searchContractPartUrl);
	     window.location.href = url;
	     $("#exportFm").attr("action",url);
	     $("#exportFm").submit();
	});
	
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
		$("input",".searchContent").each(function(index,obj){
			var inputName = $(obj).attr("id")
			$(obj).val(rowData[inputName]).prop("readonly", true);
		});
		// 调用显示主信息的方法
		showDetailsInfo();
	}
});

// 显示入库单详细信息
function showDetailsInfo(){
	var searchURL = "<%=request.getContextPath()%>/part/purchaseMng/purchaseOrder/PchOrderForDealerQueryAction/queryInfoDetailsById.ajax";
    var $f = $("#detailsInfoShow");
    var sCondition = {};
    sCondition = $f.combined() || {};
    doFormSubmit($f, searchURL, "btn-close", 1, sCondition, "tab-contract");
}

function formatAmount(obj){
    return "<div style='text-align:right;'>"+amountFormatNew($(obj).html())+"</div>";
}
</script>