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
								<td><label>订单号：</label></td>
								<td><input type="text" id="ORDER_NO_D" /></td>
								<td><label>订单类型：</label></td>
								<td><input type="text" id="ORDER_TYPE_D" /></td>
							</tr>
							<tr>
								<td><label>订单状态：</label></td>
								<td><input type="text" id="ORDER_STATUS_D" /></td>
								<td><label>申请日期：</label></td>
								<td><input type="text" id="APPLY_DATE_D" /></td>
							</tr>
							<tr>
								<td><label>渠道代码：</label></td>
								<td><input type="text" id="ORG_CODE_D" /></td>
								<td><label>渠道名称：</label></td>
								<td><input type="text" id="ORG_NAME_D" /></td>
							</tr>
							<tr>
								<td><label>订单金额（元）</label></td>
								<td colspan="3">
									<input type="text" id="ORDER_AMOUNT_D" /> 
								</td>
							</tr>
						</table>
					</div>
		</div>
		<div class="pageContent">
			    <div class="pageHeader">
			        <form id="fm-condition-details" method="post">
			        	<input type="hidden" id="ORDER_ID" datasource="O.ORDER_ID" value="<%=id%>" />
			            <div class="searchBar" align="left">
			                <table class="searchContent" id="part-search-table">
			                    <tr>
									<tr>
										<td><label>配件代码：</label></td>
										<td><input type="text" id="PART_CODE" name="PART_CODE" datasource="I.PART_CODE"  value="" operation="like" datatype="1,is_null,300" /></td>
										<td><label>配件名称：</label></td>
										<td><input type="text" id="PART_NAME" name="PART_NAME" datasource="I.PART_NAME" value="" operation="like" datatype="1,is_null,300" /></td>
									</tr>
			                    </tr>
			                </table>
			                <div class="subBar">
			                    <ul>
			                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search-details-index">查&nbsp;&nbsp;询</button></div></div></li>
			                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-down-details-index">导&nbsp;&nbsp;出</button></div></div></li>
									<li><div class="buttonActive"><div class="buttonContent"><button class="close" type="button" id="btn-close" >关&nbsp;&nbsp;闭</button></div></div></li>
			                    </ul>
			                </div>
			            </div>
			        </form>
			    </div>
				<div id="page_contract_d">
					<table style="display:none;width:100%;" id="tab-contract" name="tablist" ref="page_contract_d" refQuery="fm-condition-details">
							<thead>
								<tr>
									<th fieldname="PART_CODE" >配件代码</th>
									<th fieldname="PART_NAME" colwidth="150px">配件名称</th>
									<th fieldname="BOX_NO" >箱号</th>
									<th fieldname="COUNT" colwidth="100px">装箱数量</th>
									<th fieldname="ORDER_COUNT" colwidth="100px">订购数量</th>
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
	var id = "<%=id%>";	// 主信息ID
	var getDetailsURL = "<%=request.getContextPath()%>/part/salesMng/orderMng/BoxInfoQueryAction/queryBoxInfoById.ajax"; // 查询ActionURL
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
			$(obj).val(rowData[inputName]).attr("readonly", "readonly");
		});
		
		// 调用显示主信息的方法
		$("#btn-search-details-index").click(function(){
			var searchURL = "<%=request.getContextPath()%>/part/salesMng/orderMng/BoxInfoQueryAction/queryDetailsListInfoById.ajax";
		    var $f = $("#fm-condition-details");
		    var sCondition = {};
		    sCondition = $f.combined() || {};
		    doFormSubmit($f, searchURL, "btn-search-details-index", 1, sCondition, "tab-contract");
		}).click();
	}
	
	// 导出
	$("#btn-down-details-index").click(function(){
		var $f = $("#fm-condition-details");
		if (submitForm($f) == false) return false;
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/part/salesMng/orderMng/BoxInfoQueryAction/exportExcel.do");
		$("#exportFm").submit();
	});
});
</script>