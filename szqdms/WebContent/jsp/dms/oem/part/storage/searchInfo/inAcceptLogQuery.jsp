<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>入库单查询</title>
</head>
<body>
	<div id="layout" style="width: 100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 仓储相关   &gt; 入库验收查询</h4>
		<div class="page">
			<div class="pageHeader" >
					<form method="post" id="fm-searchContract">
						<!-- 定义隐藏域条件 -->
						<div class="searchBar" align="left" >
							<table class="searchContent" id="tab-htcx">
								<!-- 定义查询条件 -->
								<tr>
									<td><label>配件代码：</label></td>
									<td><input type="text" id="PART_CODE" name="PART_CODE" datasource="I.PART_CODE"  value="" operation="like" datatype="1,is_null,300" /></td>
									<td><label>配件名称：</label></td>
									<td><input type="text" id="PART_NAME" name="PART_NAME" datasource="I.PART_NAME" value="" operation="like" datatype="1,is_null,300" /></td>
								</tr>
								<tr>
									<td><label>供货商代码：</label></td>
									<td><input type="text" id="SUPPLIER_CODE" name="SUPPLIER_CODE" datasource="SUPPLIER_CODE" value="" operation="like" datatype="1,is_digit_letter,30" /></td>
									<td><label>供货商名称：</label></td>
									<td><input type="text" id="SUPPLIER_NAME" name="SUPPLIER_NAME" datasource="SUPPLIER_NAME" value="" operation="like" datatype="1,is_digit_letter_cn,30" /></td>
								</tr>
								<tr>
									<td><label>验收日期：</label></td>
									<td>
										<input type="text" id="CHECK_TIME_B" name="CHECK_TIME_B" style="width: 75px;" datasource="CHECK_TIME" datatype="1,is_null,30"
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:true,vel:'d1',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'CHECK_TIME_E\')}'})" kind ="date" operation=">="/> 
	                                    <span style="float: left; margin-left: -50px; margin-top: 5px;">至</span>
	                                    <input type="text" id="CHECK_TIME_E" name="CHECK_TIME_E" style="width: 75px; margin-left: -30px;" datasource="CHECK_TIME" datatype="1,is_null,30"
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:true,vel:'d1',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'CHECK_TIME_B\')}'})" kind ="date"  operation="<="/>
									</td>
									<td><label>配送号：</label></td>
									<td>
										<input type="text" id="DISTRIBUTION_NO" name="DISTRIBUTION_NO" datasource="DISTRIBUTION_NO"  value="" operation="like" datatype="1,is_null,300" />
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
									<th fieldname="PART_CODE" colwidth="120px">配件代码</th>
									<th fieldname="PART_NAME">配件名称</th>
									<th fieldname="UNIT" colwidth="60px">配件单位</th>
<!-- 									<th fieldname="PLAN_PRICE" >计划价格</th>
									<th fieldname="PLAN_AMOUNT" refer="amountFormat">计划金额</th> -->
									<th fieldname="CHECK_COUNT" >验收数量</th>
									<th fieldname="CHECK_TIME" colwidth="130px">验收时间</th>
									<th fieldname="SPLIT_NO" colwidth="140px">采购单号</th>
									<th fieldname="SUPPLIER_NAME">供货商名称</th>
									<th fieldname="SUPPLIER_CODE" colwidth="70px">供货商代码</th>
									<th fieldname="DISTRIBUTION_NO">配送号</th>
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
		<input type="hidden" id="params" name="data"></input>
	</form>
</body>
</html>
<script type="text/javascript">
$(function(){
	$("#btn-search").click(function(){
		var queryAction = "<%=request.getContextPath()%>/part/storageMng/search/AcceptInLogQueryAction/queryListInfo.ajax";
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
		$("#exportFm").attr("action","<%=request.getContextPath()%>/part/storageMng/search/AcceptInLogQueryAction/exportExcel.do");
		$("#exportFm").submit();
	})
	
})

// 金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}

</script>