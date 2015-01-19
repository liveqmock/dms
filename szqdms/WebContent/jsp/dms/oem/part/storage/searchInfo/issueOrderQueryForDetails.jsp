<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String issueId = request.getParameter("issueId");
%>
<div style="width: 100%;">
	<div class="page">
		<div class="pageHeader" >
					<div class="searchBar" align="left" >
						<table class="searchContent" id="detailsTable">
							<tr>
								<td><label>发料单号：</label></td>
								<td><input type="text" id="ISSUE_NO_D" /></td>
								<td><label>订单号：</label></td>
								<td><input type="text" id="ORDER_NO_D" /></td>
								<td><label>发料状态：</label></td>
								<td>
									<select class="combox" id="ISSUE_STATUS_D" >
										<option value="<%=DicConstant.FLDFLZT_01%>" selected="selected">未发料</option>
										<option value="<%=DicConstant.FLDFLZT_02%>">已发料</option>
									</select>
								</td>
							</tr>
							<tr>
								<td><label>订单类型：</label></td>
								<td>
									<select class="combox" id="ORDER_TYPE_D" >
										<option value="<%=DicConstant.DDLX_01%>" selected="selected">月度订单</option>
										<option value="<%=DicConstant.DDLX_02%>">周度订单</option>
										<option value="<%=DicConstant.DDLX_03%>">临时订单</option>
										<option value="<%=DicConstant.DDLX_04%>">总成订单</option>
										<option value="<%=DicConstant.DDLX_05%>">直发订单</option>
										<option value="<%=DicConstant.DDLX_06%>">促销订单</option>
										<option value="<%=DicConstant.DDLX_07%>">直营订单</option>
										<option value="<%=DicConstant.DDLX_08%>">军品订单</option>
										<option value="<%=DicConstant.DDLX_09%>">三包急件</option>
										<option value="<%=DicConstant.DDLX_10%>">技术升级</option>
									</select>	
								</td>
								<td><label>订单状态：</label></td>
								<td>
									<select class="combox" id="ORDER_STATUS_D" >
										<option value="<%=DicConstant.DDZT_01 %>">未提报</option>
										<option value="<%=DicConstant.DDZT_02 %>">已提报</option>
										<option value="<%=DicConstant.DDZT_03 %>">审核通过</option>
										<option value="<%=DicConstant.DDZT_04 %>">审核驳回</option>
										<option value="<%=DicConstant.DDZT_05 %>">已取消</option>
										<option value="<%=DicConstant.DDZT_06 %>">已关闭</option>
										<option value="<%=DicConstant.DDZT_07 %>">直营提报</option>
									</select>
								</td>
								<td><label>计划年月：</label></td>
								<td>
									<input type="text" id="PALN_YMONTH_D" /> 
								</td>
							</tr>
							<tr>
								<td><label>客户代码：</label></td>
								<td>
									<input type="text" id="ORG_CODE_D" />
								</td>
								<td><label>客户名称：</label></td>
								<td>
									<input type="text" id="ORG_NAME_D" />
								</td>
								<td><label>销售员：</label></td>
								<td><input type="text" id="SALEUSER_NAME_D" /></td>
							</tr>
							<tr>
								<td><label>库管员：</label></td>
								<td>
									<input type="text" id="USER_NAME_D" />
								</td>
								<td><label>发料单打印状态：</label></td>
								<td>
									<select class="combox" id="PRINT_STATUS_D" >
										<option value="<%=DicConstant.DYZT_01%>" selected="selected">未打印</option>
										<option value="<%=DicConstant.DYZT_02%>">已打印</option>
									</select>	
								</td>
								<td><label>发料单打印日期：</label></td>
								<td><input type="text" id="PRINT_DATE_D" /></td>
							</tr>
							<tr>
								<td><label>打印人：</label></td>
								<td colspan="4">
									<input type="text" id="PRINT_MAN_D" />
								</td>
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
								<th fieldname="PART_NAME">配件名称</th>
								<th fieldname="SHOULD_COUNT" colwidth="60px">应发数量</th>
								<th fieldname="REAL_COUNT" colwidth="60px">实发数量</th>
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
	<input type="hidden" datasource="ISSUE_ID" value="<%=issueId%>" />
</form>
<script type="text/javascript">

/**
 * 主信息查询加载
 */
$(function(){
	var issueId = "<%=issueId%>";	// 主信息ID
	var getDetailsURL = "<%=request.getContextPath()%>/part/storageMng/search/IssueOrderQueryAction/queryIssueOrderInfoById.ajax"; // 查询ActionURL
	sendPost(getDetailsURL+"?issueId="+issueId,"","",callbackShowDetailsInfo,null,null);										   // 调用后台查询Action
	
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
		$("#ISSUE_STATUS_D").val(rowData["ISSUE_STATUS_D"]+"").prop("disabled", true);
		$("#ORDER_TYPE_D").val(rowData["ORDER_TYPE_D"]+"").prop("disabled", true);
		$("#ORDER_STATUS_D").val(rowData["ORDER_STATUS_D"]+"").prop("disabled", true);
		$("#PRINT_STATUS_D").val(rowData["PRINT_STATUS_D"]+"").prop("disabled", true);
		
		// 调用显示主信息的方法
		showDetailsInfo();
	}
});

// 显示入库单详细信息
function showDetailsInfo(){
	var searchURL = "<%=request.getContextPath()%>/part/storageMng/search/IssueOrderQueryAction/queryIssueDetailsById.ajax";
    var $f = $("#detailsInfoShow");
    var sCondition = {};
    sCondition = $f.combined() || {};
    doFormSubmit($f, searchURL, "btn-close", 1, sCondition, "tab-contract");
}

</script>