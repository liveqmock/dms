<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>付款结果更新</title>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
var options = {max:false,width:760,height:300,minable:true,resizable:true,drawable:true};
var searchUrl = "<%=request.getContextPath()%>/service/financeMng/PaymentAction/settleSearch.ajax";
//查询按钮响应方法
$(function(){
	$("#search").bind("click",function(){
		var $f = $("#paymentform");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"search",1,sCondition,"paymentList");		
	});
	$("#btn-reset").bind("click", function(event){
		$("#paymentform")[0].reset();
		$("#orgCode").attr("code","");
		$("#orgCode").val("");
	});
	//下载模板
	$('#download').bind('click', function () {
		var $f = $("#paymentform");
		if (submitForm($f) == false) return false;
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/service/financeMng/PaymentAction/UpdateDownload.do");
		$("#exportFm").submit();
	});
	//导入
	$("#dia-imp").bind("click",function(){
        importXls("SE_BU_CLAIM_SETTLE_MODIFY_TMP",'',7,3,"/jsp/dms/oem/service/finance/importSuccess2.jsp");
	});	
});

//付款结果更新
function doUpdate(rowobj){
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/service/finance/paymentUpdateDetail.jsp", "payment", "付款单据更新", options);
}
//金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：财务管理&gt;结算管理&gt;付款结果更新</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="paymentform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="paymentTable">
						<tr>
							<td><label>渠道商代码：</label></td>
							<td><input type="text" id="orgCode" name="orgCode" datasource="T.ORG_ID" datatype="1,is_null,100" value="" operation="in" hasBtn="true" callFunction="showOrgTree('orgCode',2)" readonly="readonly"/></td>
							<td><label>渠道商名称：</label></td>
							<td><input type="text" id="orgName" name="orgName" datasource="T.ORG_NAME"  datatype="1,is_null,100" operation="like" value="" /></td>
						</tr>	
						<tr>
							<td><label>结算单号：</label></td>
							<td><input type="text" id="settleNo" name="settleNo"  operation="like" datasource="T.SETTLE_NO"  datatype="1,is_null,30" value="" /></td>
							<td><label>结算产生日期：</label></td>
					    	<td>
					    		<input type="text" group="settleDateStart,settleDateEnd"  id="settleDateStart" kind="date" name="settleDateStart" style="width:75px;" operation=">=" datasource="T.SETTLE_DATE" datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
					    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
								<input type="text" group="settleDateStart,settleDateEnd"  id="settleDateEnd" kind="date" name="settleDateEnd" style="width:75px;margin-left:-30px;" operation="<=" datasource="T.SETTLE_DATE" datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
				   		 	</td>
					   		<td><label>结算类型：</label></td>
							<td><select type ="text" id="settleType" name="settleType" datasource="T.SETTLE_TYPE" class="combox" kind="dic" src="JSLX" datatype="1,is_null,6">
									<option value=-1>--</option>
								</select>
							</td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-reset">重&nbsp;&nbsp;置</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="panelBar">
					<ul class="toolBar">
						<li class="line">line</li>
						<li><a class="icon" href="javascript:void(0);" id="download" ><span>下载导入模板</span></a></li>
						<li class="line">line</li>
						<li><a class="add" href="javascript:void(0);" id="dia-imp" title="确定要导入吗?"><span>导入数据</span></a></li>
					</ul>
				</div>
		<div class="pageContent">
			<div id="payment" >
				<table style="display:none;width:100%;" id="paymentList" name="paymentList" ref="payment" refQuery="paymentTable" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none" colwidth="10"  ></th>
							<th colwidth="80" fieldname="ORG_CODE" >渠道商代码</th>
							<th colwidth="200" fieldname="ORG_NAME" >渠道商名称</th>
							<th colwidth="160" fieldname="SETTLE_NO" ordertype="local" class="desc">结算单号</th>
							<th colwidth="80" fieldname="SETTLE_DATE" >结算日期</th>
							<th colwidth="80" fieldname="SETTLE_TYPE" >结算类型</th>
							<th align="right" colwidth="90" fieldname="COSTS" refer="amountFormat">服务费/材料费</th>
							<!-- <th align="right" colwidth="120" fieldname="RE_COSTS" refer="amountFormat">旧件运费/配件返利</th>
							<th align="right" colwidth="60" fieldname="POLICY_SUP" refer="amountFormat">政策支持</th>
							<th align="right" colwidth="60" fieldname="CASH_GIFT" refer="amountFormat">礼金</th>
							<th align="right" colwidth="60" fieldname="CAR_AWARD" refer="amountFormat">售车奖励</th>
							<th align="right" colwidth="60" fieldname="AP_COSTS" refer="amountFormat">考核费用</th>
							<th align="right" colwidth="60" fieldname="OTHERS" refer="amountFormat">其他费用</th> -->
							<th align="right" colwidth="60" fieldname="SUMMARY" refer="amountFormat">汇总</th>
							<th colwidth="80" type="link" title="[结果更新]"  action="doUpdate" >操作</th>
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