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
								<td><label>出库仓库代码：</label></td>
								<td><input type="text" id="OUT_WAREHOUSE_CODE_D" /></td>
								<td><label>出库仓库名称：</label></td>
								<td><input type="text" id="OUT_WAREHOUSE_NAME_D" /></td>
							</tr>
							<tr>
								<td><label>入库仓库代码：</label></td>
								<td><input type="text" id="IN_WAREHOUSE_CODE_D" /></td>
								<td><label>入库仓库名称：</label></td>
								<td><input type="text" id="IN_WAREHOUSE_NAME_D" /></td>
								<td><label>出库日期：</label></td>
								<td><input type="text" id="OUT_DATE_D" /></td>
							</tr>
							<tr>
								<td><label>是否打印：</label></td>
								<td>
									<input type="text" id="PRINT_STATUS_D" />	
								</td>
								<td><label>打印人：</label></td>
								<td><input type="text" id="PRINT_MAN_D" /></td>
								<td><label>移库人：</label></td>
								<td>
									<input type="text" id="MOVE_MAN_D" /> 
								</td>
							</tr>
						</table>
					</div>
		</div>
		<div class="pageHeader" >
				<form method="post" id="fm-searchContract-details">
					<!-- 定义隐藏域条件 -->
					<input type="hidden" id="OUT_ID_DETAILS" datasource="T.OUT_ID" value="<%=outId%>" />
					<div class="searchBar" align="left" >
						<table class="searchContent" id="tab-htcx-details">
							<tr>
								<td><label>配件代码：</label></td>
								<td><input type="text" id="PART_CODE_DETAILS" name="PART_CODE" datasource="T.PART_CODE"  value="" operation="like" datatype="1,is_null,300" /></td>
								<td><label>配件名称：</label></td>
								<td><input type="text" id="PART_NAME_DETAILS" name="PART_NAME" datasource="T.PART_NAME" value="" operation="like" datatype="1,is_null,300" /></td>
							</tr>
						</table>
						<div class="subBar">
							<ul>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search-details" >查&nbsp;&nbsp;询</button></div></div></li>
								<li><div class="buttonActive"><div class="buttonContent"><button class="close" type="button" id="btn-close-details" >关&nbsp;&nbsp;闭</button></div></div></li>
							</ul>
						</div>
					</div>
				</form>
		</div>
		<div class="pageContent">
			<div id="page_contract_d">
				<table style="display:none;width:100%;" id="tab-contract-details" name="tablist" ref="page_contract_d" refQuery="fm-searchContract-details">
						<thead>
							<tr>
								<th fieldname="PART_CODE" colwidth="150px">配件代码</th>
								<th fieldname="PART_NAME" colwidth="150px">配件名称</th>
								<th fieldname="OUT_AMOUNT" colwidth="80px">移库数量</th>
<!-- 								<th fieldname="PLAN_PRICE" >计划价</th> -->
<!-- 								<th fieldname="PLAN_AMOUNT">计划金额</th> -->
								<th fieldname="POSITION_NAME">原库位名称</th>
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
<script type="text/javascript">

/**
 * 主信息查询加载
 */
$(function(){
	

	
	var outId = "<%=outId%>";	// 主信息ID
	var getDetailsURL = "<%=request.getContextPath()%>/part/storageMng/search/MoveStockOutQueryAction/queryStockInfoById.ajax"; // 查询ActionURL
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
		showApplicationInfo(eval("(" + applicationInfo + ")"))
	}
	
	// 显示申请单主信息
	function showApplicationInfo(jsonObj){
		var rowData = jsonObj["ROW_0"];			// 获取第一行的数据
		$("input","#detailsTable").each(function(index,obj){
			var inputName = $(obj).attr("id")
			$(obj).val(rowData[inputName]).attr("readonly", "readonly");
		});
		
		// 调用显示主信息的方法
		// 显示入库单详细信息
		$("#btn-search-details").click(function(){
			var searchURL = "<%=request.getContextPath()%>/part/storageMng/search/MoveStockOutQueryAction/queryStockDetailsById.ajax";
			var $f = $("#fm-searchContract-details");
			var sCondition = {};
			sCondition = $f.combined() || {};
			doFormSubmit($f, searchURL, "btn-close-details", 1, sCondition, "tab-contract-details");
		}).click();
	}
	
	

	
});

</script>