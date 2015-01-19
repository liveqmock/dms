<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.org.dms.common.DicConstant" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>发票委托单查询</title>
</head>
<body>
	<div id="layout" style="width: 100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 仓储相关   &gt; 发票委托单查询</h4>
		<div class="page">
			<div class="pageHeader" >
					<form method="post" id="fm-searchContract">
						<!-- 定义隐藏域条件 -->
						<div class="searchBar" align="left" >
							<table class="searchContent" id="tab-htcx">
								<!-- 定义查询条件 -->
								<tr>
									<td><label>渠道代码：</label></td>
									<td>
										<input type="text" id="orgCode" name="orgCode" datasource="ORG_CODE" datatype="1,is_null,30" operation="like"/>
									</td>
									<td><label>渠道名称：</label></td>
									<td>
										<input type="text" id="orgName" name="orgName" datasource="ORG_NAME" datatype="1,is_null,100" operation="like"/>
									</td>
									<td><label>发票类型：</label></td>
									<td>
										<select class="combox" name="TARIFF_TYPE" id="TARIFF_TYPE" datasource="TARIFF_TYPE"  kind="dic" 
												src="<%=DicConstant.FPLX%>" operation="=" datatype="1,is_null,6">
											<option value="-1" selected="selected">--</option>
										</select>
									</td>
								</tr>
								<tr>
									<td><label>打印时间：</label></td>
									<td colspan="3">
										<input type="text" id="PRINT_DATE_B" name="PRINT_DATE_B" style="width: 75px;" datasource="PRINT_DATE" datatype="1,is_null,30" 
			                                    kind ="date" operation=">="
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,vel:'d1',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'PRINT_DATE_E\')}'})" />
	                                    <span style="float: left; margin-left: -50px; margin-top: 5px;">至</span>
	                                    <input type="text" id="PRINT_DATE_E" name="PRINT_DATE_E" style="width: 75px; margin-left: -30px;" datasource="PRINT_DATE" datatype="1,is_null,30" 
			                                     kind ="date"  operation="<="
			                                     onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,vel:'d2',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'PRINT_DATE_B\')}'})" />
									</td>
									
<%-- 									<td><label>状态：</label></td>
									<td>
										<select class="combox" name="STATUS" id="STATUS" datasource="STATUS"  kind="dic" 
												src="<%=DicConstant.YXBS%>" operation="=" datatype="1,is_null,6">
											<option value="-1" selected="selected">--</option>
										</select>
									</td> --%>
								</tr>
							</table>
							<div class="subBar">
								<ul>
									<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
									<li><div class="button"><div class="buttonContent"><button type="button" id="btn-export-index">导出数据</button></div></div></li>
								</ul>
							</div>
						</div>
					</form>
			</div>
			<div class="pageContent">
				<div id="page_contract" >
					<table style="display:none;width:100%;" id="invertoryTable" name="tablist" ref="page_contract" refQuery="fm-searchContract" >
							<thead>
								<tr>
									<th fieldname="ENTRUST_NO">委托单号</th>
									<th fieldname="PRINT_DATE" >打印日期</th>
									<th fieldname="USER_ACCOUNT">经办人</th>
									<th fieldname="TARIFF_TYPE">发票类别</th>
									<th fieldname="ORG_CODE">渠道代码</th>
									<th fieldname="ORG_NAME">渠道名称</th>
									<th fieldname="ADDRESS">地址</th>
									<th fieldname="TELEPHONE" >电话</th>
									<th fieldname="TARIFF_NO" >税号</th>
									<th fieldname="OPEN_BANK">开户行</th>
									<th fieldname="BANK_ACCOUNT">账号</th>
									<th fieldname="IN_INVOICE_AMOUNT" refer="amountFormat" align="right">合计金额</th>
									<th fieldname="REMARKS">备注</th>
									<!-- <th fieldname="STATUS">状态</th> -->
									<th colwidth="85" type="link" title="[打印]|[作废]"  action="doPrint|doDelete" >操作</th>
								</tr>
							</thead>
							<tbody>
		                    </tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
 	<form id="exportFm" method="post" style="display:none">
		<input type="hidden" id="data" name="data" datasource="data" />
	</form>
</body>
</html>
<script type="text/javascript">
$(function(){
	
	// 查询
	$("#btn-search").click(function(){
		var queryAction = "<%=request.getContextPath()%>/part/storageMng/search/InvoiceEntustQueryAction/queryListInfo.ajax";
		var $f = $("#fm-searchContract");
		var sCondition = {};
		sCondition = $f.combined() || {};
		doFormSubmit($f,queryAction,"btn-search",1,sCondition,"invertoryTable");
	});
	// 导出按钮绑定
    $("#btn-export-index").click(function(){
        var $f = $("#fm-searchContract");
        if (submitForm($f) == false) {
            return false;
        }
        var sCondition = {};
        sCondition = $f.combined() || {};
        $("#data").val(sCondition);
        var url = encodeURI("<%=request.getContextPath()%>/part/storageMng/search/InvoiceEntustQueryAction/download.do");
        window.location.href = url;
        $("#exportFm").attr("action",url);
        $("#exportFm").submit();
    });
});
function doPrint(rowobj){
	var $row = $(rowobj);
	var orgCode = $(rowobj).attr("ORG_CODE");
	var queryUrl = "";
	if(orgCode=='J029002'){
		queryUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/PartOrderEntrustAction/printPdf1.do?ENTRUST_ID="+$(rowobj).attr("ENTRUST_ID");
	}else{
		queryUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/PartOrderEntrustAction/printPdf.do?ENTRUST_ID="+$(rowobj).attr("ENTRUST_ID");
	}
    window.open(queryUrl); 
}

//金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}
function doDelete(rowobj){
	var url = "<%=request.getContextPath()%>/part/storageMng/search/InvoiceEntustQueryAction/deleteInvoiceEntust.ajax?ENTRUST_ID="+$(rowobj).attr("ENTRUST_ID");
	sendPost(url, "", "", doDeleteCallBack, "true");
}
function doDeleteCallBack(res){
	try {
		var queryAction = "<%=request.getContextPath()%>/part/storageMng/search/InvoiceEntustQueryAction/queryListInfo.ajax";
		var $f = $("#fm-searchContract");
		var sCondition = {};
		sCondition = $f.combined() || {};
		doFormSubmit($f,queryAction,"btn-search",1,sCondition,"invertoryTable");
    } catch (e) {
        alertMsg.error(e);
        return false;
    }
    return true;
}
</script>