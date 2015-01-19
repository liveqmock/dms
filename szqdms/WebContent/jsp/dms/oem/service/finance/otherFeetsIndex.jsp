<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>其他费用调整</title>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script type="text/javascript">
var options = {max:true,mixable:true,minable:true,resizable:true,drawable:true};
//查询按钮响应方法
var searchUrl = "<%=request.getContextPath()%>/service/financeMng/StatementAdjustAction/settleSearch.ajax";
var invoiceUrl = "<%=request.getContextPath()%>/service/financeMng/StatementAdjustAction/settleUpdate.ajax";
$(function(){
	$("#search").bind("click",function(event){
		var $f = $("#settleform");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"search",1,sCondition,"settleList");
	});
	//下载
	$("#exp").bind("click",function(){
		var $f = $("#settleform");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/service/financeMng/StatementAdjustAction/downloadOtherTemplet.do");
		$("#exportFm").submit();
	});
	//导入
	$("#imp").bind("click",function(){
        //9:表示最大列，3：表示有效数据是第几行开始，第一行是1
        //最后一个参数表示 导入成功后显示页
        importXls("SE_BU_CLAIM_SETTLE_TMP_OTHER",'', 8, 3,"/jsp/dms/oem/service/finance/importOterFeetsSuccess.jsp");
	});
	//生产开票通知单
	$("#invoice").bind("click",function(){
		sendPost(invoiceUrl,"invoice","",invoiceCallBack,"true");
	});
	$("#btn-reset").bind("click", function(event){
		$("#settleform")[0].reset();
		$("#orgCode").attr("code","");
		$("#orgCode").val("");
	}); 
});
//调整
function doUpdate(rowobj){
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/service/finance/otherFeetsDetail.jsp", "statementAdjust", "结算单调整明细", options,true);
}
//导入回调方法
function impCall(){
	var $f = $("#settleform");
	var sCondition = {};
	sCondition = $f.combined() || {};
	doFormSubmit($f,searchUrl,"search",1,sCondition,"settleList");
}
//生成开票通知单回调方法
function invoiceCallBack(res){
	try
	{
		var result = getNodeText(res.getElementsByTagName("RESULT").item(0));
		if(result=='1'){
			var $f = $("#settleform");
			var sCondition = {};
	    	sCondition = $f.combined() || {};
			doFormSubmit($f,searchUrl,"search",1,sCondition,"settleList");
		}else{
			alertMsg.info("缺少可以生成开票通知单数据.");
		}
		
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
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
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：财务管理&gt;结算管理&gt;其他费用调整</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="settleform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="settleTable">
						<tr>
							<td><label>渠道商代码：</label></td>
							<td><input type="text" id="orgCode" name="orgCode" datasource="T.ORG_ID" datatype="1,is_null,100" value="" operation="in" hasBtn="true" callFunction="showOrgTree('orgCode',2)" readonly="readonly"/></td>
							<td><label>渠道商名称：</label></td>
							<td><input type="text" id="orgName" name="orgName" datasource="T.ORG_NAME"  datatype="1,is_null,100" value="" /></td>
						</tr>
						<tr>
							<td><label>结算产生日期：</label></td>
					    	<td>
					    		<input type="text" group="settleDateStart,settleDateEnd"  id="settleDateStart" kind="date" name="settleDateStart" style="width:75px;" operation=">=" dataSource="T.SETTLE_DATE" datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
					    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
								<input type="text" group="settleDateStart,settleDateEnd"  id="settleDateEnd" kind="date" name="settleDateEnd" style="width:75px;margin-left:-30px;" operation="<=" dataSource="T.SETTLE_DATE" datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
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
							<li><div class="button"><div class="buttonContent"><button type="button" id="exp" >下载导入模板</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="imp" >批量调整</button></div></div></li>
							<!-- <li><div class="button"><div class="buttonContent"><button type="button" id="invoice" >生成开票通知单</button></div></div></li> -->
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div id="settle" >
				<table style="display:none;width:100%;" id="settleList" name="settleList"  ref="settle" refQuery="settleTable">
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none" colwidth="10"  ></th>
							<th colwidth="40" type="link" title="[调整]"  action="doUpdate" >操作</th>
							<th colwidth="80" fieldname="ORG_CODE" >渠道商代码</th>
							<th colwidth="200" fieldname="ORG_NAME" >渠道商名称</th>
							<th colwidth="160" fieldname="SETTLE_NO" ordertype="local" class="desc" >结算单号</th>
							<th colwidth="80" fieldname="SETTLE_DATE" >结算日期</th>
							<th colwidth="80" fieldname="SETTLE_TYPE" >结算类型</th>
							<th colwidth="80" fieldname="OTHERS_ADJUST_USER" >调整人</th>
							<th colwidth="80" fieldname="OTHERS_ADJUST_TIME" >调整时间</th>
							<th align="right" colwidth="100" fieldname="COSTS" refer="amountFormat">服务费/材料费</th>
							<th align="right"colwidth="120" fieldname="RE_COSTS" refer="amountFormat">旧件运费/配件返利</th>
							<th align="right" colwidth="60" fieldname="POLICY_SUP" refer="amountFormat">政策支持</th>
							<th align="right" colwidth="60" fieldname="CASH_GIFT" refer="amountFormat">礼金</th>
							<th align="right" colwidth="60" fieldname="CAR_AWARD" refer="amountFormat">售车奖励</th>
							<th align="right" colwidth="60" fieldname="AP_COSTS" refer="amountFormat">考核费用</th>
							<th align="right" colwidth="60" fieldname="OTHERS" refer="amountFormat">其他费用</th>
							<th align="right" colwidth="60" fieldname="MANUALLY_COST" refer="amountFormat">手工帐费用</th>
							<th align="right" colwidth="60" fieldname="SUMMARY" refer="amountFormat">汇总</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	 <form id="exportFm" method="post" style="display:none">
		<input type="hidden" id="params" name="data"></input>
	</form>
</div>
</body>
</html>