<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>配件出入库统计</title>
</head>
<body>
	<div id="layout" style="width: 100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 仓储相关   &gt; 配件出入库统计</h4>
		<div class="page">
			<div class="pageHeader" >
					<form method="post" id="fm-searchContract">
						<!-- 定义隐藏域条件 -->
						<div class="searchBar" align="left" >
							<table class="searchContent" id="tab-htcx">
								<!-- 定义查询条件 -->
								<tr>
									<td><label>零件编号：</label></td>
									<td><input type="text" id="PART_CODE" name="PART_CODE" datasource="PART_CODE"  operation="like" datatype="1,is_null,300" /></td>
									<td><label>零件名称</label></td>
									<td><input type="text" id="PART_NAME" name="PART_NAME" datasource="PART_NAME"  operation="like" datatype="1,is_null,300" /></td>
								</tr>
								<tr>
									<td><label>出入库时间段：</label></td>
									<td>
										<input type="text" id="IN_OUT_DATE_B" name="IN_OUT_DATE_B" datasource="IN_OUT_DATE_B" style="width: 75px;" datatype="0,is_null,30"
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'IN_OUT_DATE_E\')}'})" kind ="date" operation=">="/> 
	                                    <span style="float: left; margin-left: -40px; margin-top: 5px;">至</span>
	                                    <input type="text" id="IN_OUT_DATE_E" name="IN_OUT_DATE_E" datasource="IN_OUT_DATE_E" style="width: 75px; margin-left: -20px;"  datatype="0,is_null,30"
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'IN_OUT_DATE_B\')}'})" kind ="date"  operation="<="/>
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
									<th fieldname="PART_CODE">零件编号</th>
									<th fieldname="PART_NAME">零件名称</th>
									<th fieldname="OUT_AMOUNT">出库数量</th>
									<th fieldname="IN_AMOUNT">入库数量</th>
									<th fieldname="OUT_NO_COUNT">出库单数</th>
									<th fieldname="IN_NO_COUNT">入库单量</th>
									<th fieldname="WAREHOUSE_NAME">库区</th>
									<th fieldname="USER_NAME">库管员</th>
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
		var queryAction = "<%=request.getContextPath()%>/part/storageMng/search/StockInAndOutQueryAction/queryListInfo.ajax";
		var $f = $("#fm-searchContract");
		var sCondition = {};
		sCondition = $f.combined() || {};
		doFormSubmit($f,queryAction,"btn-search",1,sCondition,"invertoryTable");
	})
	
		// 导出
	$("#btn-export").click(function(){
		var $f = $("#fm-searchContract");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/part/storageMng/search/StockInAndOutQueryAction/exportExcel.do");
		$("#exportFm").submit();
	})
	
})

</script>