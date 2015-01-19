<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>配件出入库统计</title>
<script type="text/javascript">
$(function(){
	$("#btn-search").click(function(){
		var queryAction = "<%=request.getContextPath()%>/part/storageMng/search/MoveStockDetailsQueryAction/queryListInfo.ajax";
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
		$("#exportFm").attr("action","<%=request.getContextPath()%>/part/storageMng/search/MoveStockDetailsQueryAction/exportExcel.do");
		$("#exportFm").submit();
	});
	
});
//金额格式化
function amountFormat(obj){
    return "<div style='text-align:right;'>"+amountFormatNew($(obj).html())+"</div>";
}
</script>
</head>
<body>
	<div id="layout" style="width: 100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 仓储相关   &gt; 移库数据统计</h4>
		<div class="page">
			<div class="pageHeader" >
					<form method="post" id="fm-searchContract">
						<!-- 定义隐藏域条件 -->
						<div class="searchBar" align="left" >
							<table class="searchContent" id="tab-htcx">
								<!-- 定义查询条件 -->
								<tr>
									<td><label>零件编号：</label></td>
									<td><input type="text" id="PART_CODE" name="PART_CODE" datasource="PART_CODE"  operation="like" datatype="1, is_null, 30" /></td>
									<td><label>零件名称</label></td>
									<td><input type="text" id="PART_NAME" name="PART_NAME" datasource="PART_NAME"  operation="like" datatype="1, is_null, 30" /></td>
								</tr>
								<tr>
									<td><label>移库时间段：</label></td>
									<td>
										<input type="text" id="MOVE_DATE_B" name="MOVE_DATE_B" datasource="MOVE_DATE_B" style="width: 75px;" datatype="1,is_null,30"
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'MOVE_DATE_E\')}'})" kind ="date" operation=">="/> 
	                                    <span style="float: left; margin-left: -50px; margin-top: 5px;">至</span>
	                                    <input type="text" id="MOVE_DATE_E" name="MOVE_DATE_E" datasource="MOVE_DATE_E" style="width: 75px; margin-left: -30px;"  datatype="1,is_null,30"
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'MOVE_DATE_B\')}'})" kind ="date"  operation="<="/>
									</td>
								</tr>
								<tr>
									<td><label>出库仓库：</label></td>
									<td>										
										<input type="text" id="OUT_WAREHOUSE_CODE" name="OUT_WAREHOUSE_CODE" datasource="OUT_WAREHOUSE_CODE" datatype="1,is_digit_letter_cn,30" 
											   src="T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME{WAREHOUSE_CODE,WAREHOUSE_NAME}:1=1 AND WAREHOUSE_TYPE IN (100101, 100102, 100103, 100104, 100105, 100110, 100111) AND WAREHOUSE_STATUS=<%=DicConstant.YXBS_01 %> ORDER BY WAREHOUSE_CODE" 
											   operation="=" isreload="true" kind="dic"
										/>
									</td>
									<td><label>目标仓库：</label></td>
									<td>
										<input type="text" id="IN_WAREHOUSE_CODE" name="IN_WAREHOUSE_CODE" datasource="IN_WAREHOUSE_CODE" datatype="1,is_digit_letter_cn,30" 
											   src="T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME{WAREHOUSE_CODE,WAREHOUSE_NAME}:1=1 AND WAREHOUSE_TYPE IN (100101, 100102, 100103, 100104, 100105, 100110, 100111) AND WAREHOUSE_STATUS=<%=DicConstant.YXBS_01 %> ORDER BY WAREHOUSE_CODE" 
											   operation="=" isreload="true" kind="dic"
										/>
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
									<th fieldname="PART_CODE" >零件编号</th>
									<th fieldname="PART_NAME" colwidth="150px">零件名称</th>
									<th fieldname="OUT_NO" colwidth="150px">移库单号</th>
									<th fieldname="OUT_WAREHOUSE_NAME" colwidth="150px">出库仓库</th>
									<th fieldname="OUT_AMOUNT" colwidth="80px">移库数量</th>
<!-- 									<th fieldname="PLAN_PRICE" colwidth="80px" refer="amountFormat">计划价</th> -->
<!-- 									<th fieldname="PLAN_AMOUNT" colwidth="80px" refer="amountFormat">计划金额</th> -->
									<th fieldname="IN_WAREHOUSE_NAME" colwidth="150px">目标仓库</th>
									<th fieldname="MOVE_DATE" colwidth="130px">移库时间</th>
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
 	<form id="exportFm" method="post" style="display:none">
		<input type="hidden" id="params" name="data" datasource="data" />
	</form>
</body>
</html>