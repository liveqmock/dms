<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>移库查询</title>
</head>
<body>
	<div id="layout" style="width: 100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 仓储相关   &gt; 移库查询</h4>
		<div class="page">
			<div class="pageHeader" >
					<form method="post" id="fm-searchContract">
						<!-- 定义隐藏域条件 -->
						<div class="searchBar" align="left" >
							<table class="searchContent" id="tab-htcx">
								<!-- 定义查询条件 -->
								<tr>
									<td><label>出库单号：</label></td>
									<td><input type="text" id="OUT_NO" name="OUT_NO" datasource="OUT_NO" value="" operation="like" datatype="1,is_digit_letter,30" /></td>
									<td><label>配件代码：</label></td>
									<td><input type="text" id="PART_CODE" name="PART_CODE" datasource="PART_CODE"  value="" operation="like" datatype="1,is_null,300" /></td>
								</tr>
								<tr>
									<td><label>配件名称：</label></td>
									<td><input type="text" id="PART_NAME" name="PART_NAME" datasource="PART_NAME" value="" operation="like" datatype="1,is_null,300" /></td>
									<td><label>是否打印：</label></td>
									<td>
										<select class="combox" name="PRINT_STATUS" id="PRINT_STATUS" datasource="PRINT_STATUS" kind="dic" src="<%=DicConstant.DYZT%>" operation="=" datatype="1,is_null,6" >
											<option value="-1" selected="selected">--</option>
										</select>	
									</td>
								</tr>
								<tr>
									<td><label>打印日期：</label></td>
									<td>
										<input type="text" id="PRINT_DATE_B" name="PRINT_DATE_B" style="width: 75px;" datasource="PRINT_DATE" datatype="1,is_null,30"
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" kind ="date" operation=">="/> 
	                                    <span style="float: left; margin-left: -50px; margin-top: 5px;">至</span>
	                                    <input type="text" id="PRINT_DATE_E" name="PRINT_DATE_E" style="width: 75px; margin-left: -30px;" datasource="PRINT_DATE" datatype="1,is_null,30"
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" kind ="date"  operation="<="/>
									</td>
									<td><label>出库日期：</label></td>
									<td>
										<input type="text" id="OUT_DATE_B" name="OUT_DATE_B" style="width: 75px;" datasource="OUT_DATE" datatype="1,is_null,30"
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" kind ="date" operation=">="/> 
	                                    <span style="float: left; margin-left: -50px; margin-top: 5px;">至</span>
	                                    <input type="text" id="OUT_DATE_E" name="OUT_DATE_E" style="width: 75px; margin-left: -30px;" datasource="OUT_DATE" datatype="1,is_null,30"
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" kind ="date"  operation="<="/>
									</td>
								</tr>
								<tr>
									<td><label>出库仓库：</label></td>
									<td>
										<input type="text" id="CODE" name="CODE" datasource="O.WAREHOUSE_CODE" datatype="1,is_null,300" 
											   src="T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME{WAREHOUSE_CODE,WAREHOUSE_NAME}:1=1 AND WAREHOUSE_TYPE IN (100101, 100102, 100103, 100104, 100105, 100111, 100110) AND WAREHOUSE_STATUS=<%=DicConstant.YXBS_01 %> ORDER BY WAREHOUSE_CODE" 
											   operation="=" isreload="true" kind="dic"
										/>
									</td>
									<td><label>入库仓库：</label></td>
									<td>
										<input type="text" id="CODE" name="CODE" datasource="IN_WAREHOUSE_CODE" datatype="1,is_null,300"
											   src="T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME{WAREHOUSE_CODE,WAREHOUSE_NAME}:1=1 AND WAREHOUSE_TYPE IN (100101, 100102, 100103, 100104, 100105, 100111, 100110) AND WAREHOUSE_STATUS=<%=DicConstant.YXBS_01 %> ORDER BY WAREHOUSE_CODE" 
											   operation="=" isreload="true" kind="dic"
										/>
									</td>
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
									<th fieldname="OUT_NO" colwidth="175px" refer="showNoInfo">出库单号</th>
									<th fieldname="OUT_WAREHOUSE_CODE" colwidth="80px">出库仓库代码</th>
									<th fieldname="OUT_WAREHOUSE_NAME" colwidth="80px">出库仓库名称</th>
									<th fieldname="IN_WAREHOUSE_CODE" colwidth="80px">入库仓库代码</th>
									<th fieldname="IN_WAREHOUSE_NAME" colwidth="80px">入库仓库名称</th>
									<th fieldname="PLAN_AMOUNT" refer="amountFormat" align="right">计划金额(元)</th>
									<th fieldname="OUT_DATE" colwidth="140px">出库日期</th>
									<th fieldname="PRINT_STATUS" colwidth="60px">是否打印</th>
									<th fieldname="PRINT_DATE" colwidth="140px" >打印日期</th>
									<th fieldname="PRINT_MAN" >打印人</th>
									<th fieldname="MOVE_MAN" >移库人</th>
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
		<input type="hidden" id="data" name="data"></input>
	</form>
</body>
</html>
<script type="text/javascript">
$(function(){
	$("#btn-search").click(function(){
		var queryAction = "<%=request.getContextPath()%>/part/storageMng/search/MoveStockOutQueryAction/queryListInfo.ajax";
		var $f = $("#fm-searchContract");
		var sCondition = {};
		sCondition = $f.combined() || {};
		doFormSubmit($f,queryAction,"btn-search",1,sCondition,"invertoryTable");
	})
	// 导出按钮绑定
    $("#btn-export-index").click(function(){
        var $f = $("#fm-searchContract");
        if (submitForm($f) == false) {
            return false;
        }
        var sCondition = {};
        sCondition = $f.combined() || {};
        $("#data").val(sCondition);
        var url = encodeURI("<%=request.getContextPath()%>/part/storageMng/search/MoveStockOutQueryAction/download.do");
        window.location.href = url;
        $("#exportFm").attr("action",url);
        $("#exportFm").submit();
    });
})

// 入库单号超链接
function showNoInfo($cell){
	$tr = $cell.parent();
	return "<a style='color:red' href='javascript:openDetailPage(\""+$tr.attr("OUT_ID")+"\")'>"+$tr.attr("OUT_NO")+"</a>";
}

// 打开详细页面
function openDetailPage(outId){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
    $.pdialog.open(webApps+"/jsp/dms/oem/part/storage/searchInfo/moveStockInQueryForDetails.jsp?outId="+outId, "forDetailsPage", "移库查询详情", options);
}
//金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}
</script>