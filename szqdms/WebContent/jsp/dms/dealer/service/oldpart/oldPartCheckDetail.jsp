<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>旧件回运审核</title>
<script type="text/javascript">
var $true=true;
var diaSaveAction = "<%=request.getContextPath()%>/service/oldpartMng/OldPartPackAction";
var checkPassUrl = "<%=request.getContextPath()%>/service/oldpartMng/OldPartCheckAction/oldPartCheckPass.ajax";
var checkRejectUrl = "<%=request.getContextPath()%>/service/oldpartMng/OldPartCheckAction/oldPartCheckReject.ajax";
$(function(){
	var selectedRows = parent.$("#oldPartList").getSelectedRows();
	setEditValue("dia-oldpartfm",selectedRows[0].attr("rowdata"));
	//审核通过
	$("#dia_pass").bind("click",function(){
		//获取需要提交的form对象
		var $f = $("#dia-oldpartfm");
		if (submitForm($f) == false) {
			return false;
		}
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#dia-oldpartfm").combined(1) || {};
		doNormalSubmit($f,checkPassUrl,"dia_pass",sCondition,checkPassCallBack);
	});
	//审核驳回
	$("#dia_reject").bind("click",function(){
		//获取需要提交的form对象
		var $f = $("#dia-oldpartfm");
		if (submitForm($f) == false) {
			return false;
		}
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#dia-oldpartfm").combined(1) || {};
		doNormalSubmit($f,checkRejectUrl,"dia-reject",sCondition,checkRejectCallBack);
	});
	//明细查询
	$("#dia_searchDetail").bind("click",function(){
		var $f = $("#di_oldPartFm");
		var sCondition = {};
		sCondition = $f.combined() || {};
		var url =diaSaveAction+"/returnPartSearch.ajax?orderId="+$("#orderId").val();
		doFormSubmit($f,url,"",1,sCondition,"dia_oldPartList");
	});
	
	//上一步
	$("button[name='btn-pre']").bind("click",function(event){
		$("#tabs").switchTab(parseInt($("#tabs").attr("currentIndex"))-1);
	});
	 //下一步
	$("button[name='btn-next']").bind("click",function(event){
	var $tabs = $("#tabs");
	switch(parseInt($tabs.attr("currentIndex")))
 	{
		case 0:
			if($true){
				var $f = $("#di_oldPartFm");
				var sCondition = {};
		    	sCondition = $f.combined() || {};
		    	var url =diaSaveAction+"/returnPartSearch.ajax?orderId="+$("#orderId").val();
				doFormSubmit($f,url,"",1,sCondition,"dia_oldPartList");
			}
			$true=false;
			break;
		case 1:
			break;
    }
 	$tabs.switchTab(parseInt($tabs.attr("currentIndex"))+1);
 	//跳转后实现方法
 	(function(ci){ 
		switch(parseInt(ci))
     	{
			case 1://第2个tab页
    	   		break;
     	   	default:
     	   		break;
     	  }
 	   })(parseInt($tabs.attr("currentIndex")));
	});
	 
	//jsp关闭,与DIV不同
	var dialog = parent.$("body").data("oldPart");
	$("button.close").click(function(){
		parent.$.pdialog.close(dialog);
		return false;
	});
});
//通过 回调
function checkPassCallBack(res){
	try
	{
		var $row = parent.$("#oldPartList").getSelectedRows();//选择行
		if($row[0]){
			parent.$("#oldPartList").removeResult($row[0]);//移除选择行
			parent.$.pdialog.closeCurrent();
			return false;
		}
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//驳回 回调
function checkRejectCallBack(res){
	try
	{
		var $row = parent.$("#oldPartList").getSelectedRows();//选择行
		if($row[0]){
			parent.$("#oldPartList").removeResult($row[0]);//移除选择行
			parent.$.pdialog.closeCurrent();
			return false;
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
	<!-- tab页  -->
	<div class="tabs" eventType="click" id="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li ><a href="javascript:void(0)"><span>基本信息</span></a></li>
					<li ><a href="javascript:void(0)"><span>回运清单明细</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent">
		<div class="page">
		<div class="pageContent" style="" >
			<form method="post" id="dia-oldpartfm" class="editForm" >
				<input type="hidden" id="orderId" name="orderId" datasource="ORDER_ID"/>
				<div align="left">
				<fieldset>
				<table class="editTable" id="dia-oldpartTab">
				    <tr>
						<td ><label>回运单号：</label></td>
						<td colspan="3"><input type="text" id="orderNo" name="orderNo" datasource="ORDER_NO" value="" readonly="readonly"/></td>
					</tr>
					<tr>	
						<td><label>旧件产生年月：</label></td>
						<td><input type="text" id="produceDate" name="produceDate" datasource="PRODUCE_DATE" value=""  datatype="1,is_null,30" readonly="readonly" /></td>
						<td><label>回运日期：</label></td>
						<td><input type="text" id="returnDate" name="returnDate" datasource="RETURN_DATE" value="" datatype="1,is_null,30"  readonly="readonly" /></td>
					</tr>
					<tr>	
						<td><label>运输方式：</label></td>
						<td><input type="text" id="transType" name="transType" datasource="TRANS_TYPE" value=""  datatype="1,is_null,30" readonly="readonly" /></td>
						<td ><label>装箱总数量：</label></td>
						<td><input type="text" id="amount" name="amount" datasource="AMOUNT" datatype="1,is_digit,10" readonly="readonly" /></td>
					</tr>
					<tr>
						<td><label>备注：</label></td>
					    <td colspan="3"><textarea class="" rows="2" id="remarks" name="remarks" datasource="REMARKS" style="width:100%" datatype="1,is_null,1000"></textarea></td>
					</tr>
					<tr>
						<td><label>审核意见：</label></td>
					    <td colspan="3"><textarea class="" rows="2" id="checkOpinion" name="checkOpinion" datasource="CHECK_OPINION" style="width:100%" datatype="1,is_null,1000"></textarea></td>
					</tr>
				</table>
				</fieldset>
				</div>
			</form>
		</div>
		<div class="formBar">
				<ul>
					<li><div class="button"><div class="buttonContent"><button type="button" id="dia_pass">通&nbsp;&nbsp;过</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button type="button"  id="dia_reject">驳&nbsp;&nbsp;回</button></div></div></li>
					<li ><div class="button"><div class="buttonContent"><button type="button" id="next1" name="btn-next">下一步</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
				</ul>
			</div>
		</div>
		<div class="page">
		<div class="pageHeader">
		<form id="di_oldPartFm" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="di_oldPartTab">
					<tr>
						<td><label>箱号：</label></td>
						<td><input type="text" id="boxNo" name="boxNo" datasource="D.BOX_NO" datatype="1,is_null,100"  value="" /></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia_searchDetail">查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="next2" name="btn-pre">上一步</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
		</div>
		<div class="pageContent">
			<div id="dia_oldPart">
				<table style="display:none;width:100%;" id="dia_oldPartList" name="dia_oldPartList" ref="dia_oldPart" refQuery="di_oldPartTab">
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="CLAIM_NO">索赔单号</th>
							<th fieldname="PART_CODE">车辆型号</th>
							<th fieldname="VIN">VIN</th>
							<th fieldname="MILEAGE">行驶里程</th>
							<th fieldname="PART_CODE">配件代码</th>
							<th fieldname="PART_NAME">配件名称</th>
							<th fieldname="PART_AMOUNT" align="right" refer="amountFormat">配件金额(元)</th>
							<th fieldname="OUGHT_COUNT">配件数量</th>
							<th fieldname="BOX_NO">箱号</th>
							<th fieldname="PROSUPPLY_CODE">生产供应商</th>
							<th fieldname="DUTYSUPPLY_CODE">责任供应商代码</th>
							<th fieldname="BROKEN_REASON">质损原因</th>
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
	</div>
</div>
</body>
</html>