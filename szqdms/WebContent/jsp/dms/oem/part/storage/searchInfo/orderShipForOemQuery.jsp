<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>承运信息查询</title>
</head>
<body>
	<div id="layout" style="width: 100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 仓储相关   &gt; 承运信息查询</h4>
		<div class="page">
			<div class="pageHeader" >
					<form method="post" id="fm-searchContract">
						<!-- 定义隐藏域条件 -->
						<div class="searchBar" align="left" >
							<table class="searchContent" id="tab-htcx">
								<!-- 定义查询条件 -->
								<tr>
									<td><label>发运单号：</label></td>
									<td><input type="text" id="SHIP_NO" name="SHIP_NO" datasource="T.SHIP_NO" value="" operation="like" datatype="1,is_null,30" /></td>
									<td><label>订单号：</label></td>
									<td><input type="text" id="ORDER_NO" name="ORDER_NO" datasource="ORDER_NO" value="" action="show" operation="like" datatype="1,is_null,30" /></td>
								</tr>
								<tr>
									<td><label>发运单状态：</label></td>
									<td>
										<select id="SHIP_STATUS" name="SHIP_STATUS"  datasource="T.SHIP_STATUS" kind="dic" src="<%=DicConstant.FYDZT %>" operation="=" datatype="1,is_null,6">
											<option value="-1" selected="selected">--</option>
										</select>
									</td>
									<td><label>制单日期：</label></td>
									<td>
										<input type="text" id="CREATE_TIME_B" name="CREATE_TIME_B" style="width: 75px;" datasource="T.CREATE_TIME" datatype="1,is_null,30"
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" kind ="date" operation=">="/> 
	                                    <span style="float: left; margin-left: -50px; margin-top: 5px;">至</span>
	                                    <input type="text" id="CREATE_TIME_E" name="CREATE_TIME_E" style="width: 75px; margin-left: -30px;" datasource="T.CREATE_TIME" datatype="1,is_null,30"
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" kind ="date"  operation="<="/>
									</td>
								</tr>
								<tr>
									<td><label>回执单号：</label></td>
									<td><input type="text" id="RECEIPT_NO" name="RECEIPT_NO" datasource="T1.RECEIPT_NO" value="" operation="like" datatype="1,is_null,30" /></td>
								</tr>
							</table>
							<div class="subBar">
								<ul>
									<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
									<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-export" >导&nbsp;&nbsp;出</button></div></div></li>
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
									<th fieldname="SHIP_NO" colwidth="175px" >发运单号</th>
									<th fieldname="SHIP_STATUS" colwidth="80px">发运状态</th>
									<th fieldname="RECEIPT_NO" colwidth="110px">回执单号</th>
									<th fieldname="LICENSE_PLATE" colwidth="80px">车牌号</th>
									<th fieldname="SHIP_DATE" colwidth="80px">发运日期</th>
									<th fieldname="CREATE_USER" colwidth="80px">制单人</th>
									<th fieldname="CREATE_TIME" colwidth="140px">制单日期</th>
									<th fieldname="REMARKS" >备注</th>
									<th colwidth="105" type="link" title="[下载发运信息]"  action="doDownLoadBoxNo">操作</th>
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
		<input type="hidden" id="params" name="data" datasource="data" />
	</form>
</body>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	$("#btn-search").click(function(){
		var queryAction = "<%=request.getContextPath()%>/part/storageMng/search/OrderShipForDealerQueryAction/oemShipSearch.ajax";
		var $f = $("#fm-searchContract");
		var sCondition = {};
		sCondition = $f.combined() || {};
		doFormSubmit($f,queryAction,"btn-search",1,sCondition,"invertoryTable");
	})
	
	
	$("#btn-export").click(function(){
		var $f = $("#fm-searchContract");
		if (submitForm($f) == false) return false;
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/part/storageMng/search/OrderShipForDealerQueryAction/exportExcel.do");
		$("#exportFm").submit();
	})
})
// 入库单号超链接
function showNoInfo($cell){
	$tr = $cell.parent();
	return "<a style='color:red' href='javascript:openDetailPage(\""+$tr.attr("SHIP_ID")+"\")'>"+$tr.attr("SHIP_NO")+"</a>";
}

// 打开详细页面
function openDetailPage(id){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
    $.pdialog.open(webApps+"/jsp/dms/oem/part/storage/searchInfo/orderShipFroDealerDetails.jsp?id="+id, "forDetailsPage", "发运单详情", options);
}

//金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}
function doDownLoadBoxNo(rowobj){
	$.filestore.view($(rowobj).attr("DEL_ID"));
}
</script>
</html>
