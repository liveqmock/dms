<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>调价记录查询</title>
</head>
<body>
	<div id="layout" style="width: 100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left">
			<img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置： 报表管理 &gt; 服务调价记录查询
		</h4>
		<div class="page">
			<div class="pageHeader">
				<form method="post" id="fm-partPriceChange">
					<!-- 定义隐藏域条件 -->
					<div class="searchBar" align="left">
						<table class="searchContent" id="tab-pjcx">
							<!-- 定义查询条件 -->
							<tr>
								<td><label>配件代码：</label></td>
								<td><input type="text" id="PART_CODE" name="PART_CODE" datasource="A.PART_CODE" datatype="1,is_null,30" operation="like" /></td>
								<td><label>配件名称：</label></td>
								<td><input type="text" id="PART_NAME" name="PART_NAME" datasource="A.PART_NAME" datatype="1,is_null,30" operation="like" /></td>
								<td><label>仓库：</label></td>
								<td>
									<!-- 业务部门要求查询三包库:100106 -->
									<input type="text" id="WCODE" name="WCODE" kind="dic" 
										 src="T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME{WAREHOUSE_CODE,WAREHOUSE_NAME}:1=1 AND WAREHOUSE_TYPE IN (100101, 100102, 100103, 100104, 100105, 100106, 100110, 100111) AND WAREHOUSE_STATUS=<%=DicConstant.YXBS_01 %> ORDER BY WAREHOUSE_CODE" 
										datasource="A.WAREHOUSE_CODE" datatype="1,is_null,300" />
								</td>
							</tr>
							<tr>
								<td><label>服务价格类型：</label></td>
								<td>
									<!-- 服务索赔价、服务追偿价； -->
									<select class="combox" id="PRICE_TYPE" name="PRICE_TYPE" kind="dic" src="PJJGLX" datasource="A.PRICE_TYPE" operation="=" datatype="0,is_null,10"
										filtercode="<%=DicConstant.PJJGLX_06%>|<%=DicConstant.PJJGLX_07%>"
									>
										<option value="-1">--</option>
									</select>
								</td>
								<td><label>调价日期：</label></td>
								<td><input type="text" group="in-kscjrq,in-jscjrq" id="in-kscjrq" name="in-kscjrq" 
											operation=">=" dataSource="A.CREATE_TIME" style="width: 75px;" kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
									<span style="float: left; margin-left: -50px; margin-top: 5px;">至</span>
									<input type="text" group="in-kscjrq,in-jscjrq" id="in-jscjrq" name="in-jscjrq" operation="<=" dataSource="A.CREATE_TIME"
										style="width: 75px; margin-left: -30px;" kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
								</td>
								<td><label>价格变动：</label></td>
								<td>
									<select class="combox" id="PRICE_CHANGE" name="PRICE_CHANGE" kind="dic" datasource="PRICE_CHANGE" operation="=" datatype="1,is_null,10">
										<option value="-1" selected="selected">--</option>
										<option value="3">升价</option>
										<option value="2">降价</option>
									</select>
								</td>
							</tr>
						</table>
						<div class="subBar">
							<ul>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-cx">查&nbsp;&nbsp;询</button></div></div></li>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-export">导&nbsp;&nbsp;出</button></div></div></li>
							</ul>
						</div>
					</div>
				</form>
			</div>
			<div class="pageContent">
				<div id="page_grid">
					<table style="display: none; width: 100%;" id="tab-grid-index" name="tablist" ref="page_grid" refQuery="fm-partPriceChange">
						<thead>
							<tr>
								<th fieldname="PART_CODE">配件代码</th>
								<th fieldname="PART_NAME" colwidth="150px">配件名称</th>
								<th fieldname="ORIGINAL_PRICE" refer="amountFormat" align="right">调价前价格</th>
								<th fieldname="NOW_PRICE" refer="amountFormat" align="right">调价后价格</th>
								<th fieldname="JGCY" align="right" refer="amountFormat">价差</th>
								<th fieldname="PRICE_TYPE" colwidth="100px">配件价格类型</th>
								<th fieldname="CREATE_USER">调价人</th>
								<th fieldname="CREATE_TIME" colwidth="130px">调价时间</th>
								<th fieldname="WAREHOUSE_NAME">仓库名称</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
	<!-- 金额汇总Form -->
 	<form id="searchAmountFm" method="post" style="display:none">
		<input type="hidden" id="paramsAmount" name="data" datasource="data" />
	</form>
	<!-- 导出Form -->
	<form id="exportFm" method="post" style="display: none">
		<input type="hidden" id="paramsExcel" name="data" datasource="data" />
	</form>
</body>
<script type="text/javascript">
// 初始化方法
$(function(){
	
	// 查询
	$("#btn-cx").click(function(){
		var searchUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PartPriceChangeQueryAction/queryListInfo.ajax";
		var $f = $("#fm-partPriceChange");
		var sCondition = {};
		sCondition = $f.combined() || {};
		doFormSubmit($f, searchUrl, "btn-cx", 1, sCondition, "tab-grid-index");
	});

	// 导出
	$("#btn-export").click(function(){
		var $f = $("#fm-partPriceChange");
		if (submitForm($f) == false) return false;
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#paramsExcel").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/part/basicInfoMng/PartPriceChangeQueryAction/exportExcel.do");
		$("#exportFm").submit();

	});
	
});

// 查询结束后回调函数:
function callbackSearch(responseText, tabId){
	var $f = $("#fm-partPriceChange");
	var sCondition = {};
	sCondition = $f.combined() || {};
	$("#paramsAmount").val(sCondition);
	var searchUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PartPriceChangeQueryAction/queryListInfoSum.ajax";
	sendPost(searchUrl,"", $("#paramsAmount").val() ,callbackShowDetailsInfo,null,null);										   // 调用后台查询Action
}

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
	var hz1 = parseFloat(rowData["CYJEZH_D"]) == 0 ? "0.0" : parseFloat(rowData["CYJEZH_D"]);
	if(!hz1) hz1 = "0.0"; 					// 防止hz1 = NaN
	$("#changeAmountSum").val(hz1);
}

//金额格式化
function amountFormat(obj) {
	return amountFormatNew($(obj).html());
}
function afterDicItemClick(id,$row,selIndex){
	if(id == "CYJE") {
		if ($("#CYJE").attr("text")=="是") {
			$("#CYJE").attr("operation","IN");
		} else {
			$("#CYJE").attr("operation","NOT IN");
		}
	}
}
</script>
</html>