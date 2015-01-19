<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant" %>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>采购订单查询(供)</title>
</head>
<body>
	<div id="layout" style="width: 100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 采购相关   &gt; 采购订单查询(供)</h4>
		<div class="page">
			<div class="pageHeader" >
					<form method="post" id="fm-searchContract">
						<!-- 定义隐藏域条件 -->
						<div class="searchBar" align="left" >
							<table class="searchContent" id="tab-htcx">
								<!-- 定义查询条件 -->
								<tr>
									<td><label>采购单号</label></td>
								    <td><input type="text" id="ORDER_NO" name="ORDER_NO" datatype="1,is_digit_letter,30" dataSource="T.SPLIT_NO" operation="like" /></td>
								    <td><label>采购类型：</label></td>
								    <td>
								    	<select type="text" class="combox" id="PURCHASE_TYPE" name="PURCHASE_TYPE" kind="dic" src="CGDDLX" datasource="PURCHASE_TYPE"  datatype="1,is_null,100">
									    	<option value="-1" selected>--</option>
										</select>
									</td>
								    <td><label>采购单状态：</label></td>
								    <td>
								    	<select type="text" class="combox" id="ORDER_STATUS" name="ORDER_STATUS" kind="dic" src="CGDDZT" datasource="ORDER_STATUS" 
								    			filtercode="<%=DicConstant.CGDDZT_02%>|<%=DicConstant.CGDDZT_03%>|<%=DicConstant.CGDDZT_04%>|<%=DicConstant.CGDDZT_05%>" datatype="1,is_null,100">
									    	<option value="-1" selected>--</option>
									    </select>
								    </td>
								</tr>
								<tr>
								    <td><label>关单日期：</label></td>
								    <td>
							    		<input type="text"  id="CLOSE_DATE_B"  name="CLOSE_DATE_B"  dataSource="CLOSE_DATE" operation=">=" 
							    			   style="width:75px;"  kind="date" datatype="1,is_date,30" 
							    			   onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,vel:'d1',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'CLOSE_DATE_E\')}'})" />
							    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
										<input type="text" id="CLOSE_DATE_E"  name="CLOSE_DATE_E" dataSource="CLOSE_DATE" operation="<=" 
											   style="width:75px;margin-left:-30px;" kind="date" datatype="1,is_date,30" 
											   onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,vel:'d1',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'CLOSE_DATE_B\')}'})" />
							   		 </td>
								    <td><label>制单日期：</label></td>
								    <td colwidth="3">
							    		<input type="text"  id="APPLY_DATE_B"  name="APPLY_DATE_B"  dataSource="APPLY_DATE" operation=">=" 
							    			   style="width:75px;"  kind="date" datatype="1,is_date,30" 
							    			   onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,vel:'d1',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'APPLY_DATE_E\')}'})" />
							    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
										<input type="text" id="APPLY_DATE_E"  name="APPLY_DATE_E" dataSource="APPLY_DATE" operation="<=" 
											   style="width:75px;margin-left:-30px;" kind="date" datatype="1,is_date,30" 
											   onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,vel:'d1',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'APPLY_DATE_B\')}'})" />
							   		 </td>
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
									<th type="single" name="XH" style="display:none;"></th>
									<th fieldname="SPLIT_NO" colwidth="160px" refer="showNoInfo">订单编号</th>
									<th fieldname="PURCHASE_AMOUNT" align="right" refer="amountFormat">订单金额</th>
									<th fieldname="PLAN_DISTRIBUTION" >计配号</th>
									<th fieldname="SUPPLIER_NAME" >供应商名称</th>
									<th fieldname="PURCHASE_TYPE" colwidth="60px">采购类型</th>
									<th fieldname="SELECT_MONTH" colwidth="70px">所属月度</th>
									<th fieldname="APPLY_DATE" colwidth="130px">制单日期</th>
									<th fieldname="CLOSE_DATE" colwidth="130px">关单日期</th>
									<th fieldname="ORDER_STATUS" colwidth="80px">采购单状态</th>
									<th fieldname="APPLY_USER" colwidth="60px">制单人</th>
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
</html>
<script type="text/javascript">
$(function(){
	$("#btn-search").click(function(){
		var queryAction = "<%=request.getContextPath()%>/part/purchaseMng/purchaseOrder/PchOrderForDealerQueryAction/queryListInfo.ajax";
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
		$("#exportFm").attr("action","<%=request.getContextPath()%>/part/purchaseMng/purchaseOrder/PchOrderForDealerQueryAction/exportExcel.do");
		$("#exportFm").submit();
	})
})

// 入库单号超链接
function showNoInfo($cell){
	$tr = $cell.parent();
	return "<a style='color:red' href='javascript:openDetailPage(\""+$tr.attr("SPLIT_ID")+"\")'>"+$tr.attr("SPLIT_NO")+"</a>";
}

// 打开详细页面
function openDetailPage(id){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
    $.pdialog.open(webApps+"/jsp/dms/dealer/part/sales/query/pchOrderForDealerQueryDetails.jsp?id="+id, "forDetailsPage", "采购订单详情", options);
}

//金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}
</script>