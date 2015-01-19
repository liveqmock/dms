<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.org.framework.util.Pub"%>
<%@page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>旧件回运装箱</title>
<%
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
	SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
	SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
	String year = yearFormat.format(Pub.getCurrentDate());
	String monthDate = monthFormat.format(Pub.getCurrentDate());
    Integer month = Integer.valueOf(monthDate);
	String date = year + "-" + String.valueOf(month);

%>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
var $true=true;
var action = "<%=action%>";
var diaSaveAction = "<%=request.getContextPath()%>/service/oldpartMng/OldPartPackAction";
$(function(){
	//初始化页面
	if(action !=1 ){
		var selectedRows = parent.$("#oldPartList").getSelectedRows();
		setEditValue("dia-oldpartfm",selectedRows[0].attr("rowdata"));
		//$("#dia_oldPartList").attr("layoutH",document.documentElement.clientHeight-30);
		$("#backH").show();
		//$("#submitB").show();
		$("#btnNext1").show();
	}
	$("#dia_save").bind("click",function(){
		//获取需要提交的form对象
		var $f = $("#dia-oldpartfm");
		if (submitForm($f) == false) {
			return false;
		}
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#dia-oldpartfm").combined(1) || {};
		var orderId=$("#orderId").val();
		if(orderId == '')	//插入动作
		{
			var addUrl = diaSaveAction + "/oldPartReturnInsert.ajax";
			doNormalSubmit($f,addUrl,"save",sCondition,diaInsertCallBack);
		}else	//更新动作
		{
			var updateUrl = diaSaveAction + "/oldPartReturnUpdate.ajax";
			doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
		}
	});
	//提报
	$("#dia_submit").bind("click",function(){
		var orderId=$("#orderId").val();
		var $f = $("#dia-oldpartfm");
		if (submitForm($f) == false) {
			return false;
		}
		var sCondition = {};
		sCondition = $("#dia-oldpartfm").combined(1) || {};
		var reportUrl = diaSaveAction + "/returnOldReport.ajax?orderId="+orderId+"&flag="+2;
		doNormalSubmit($f,reportUrl,"dia-report",sCondition,diaReportCallBack);
	});
	//明细查询
	$("#dia_searchDetail").bind("click",function(){
		var $f = $("#di_oldPartFm");
		var sCondition = {};
		sCondition = $f.combined() || {};
		var url =diaSaveAction+"/returnPartSearch.ajax?orderId="+$("#orderId").val();
		doFormSubmit($f,url,"",1,sCondition,"dia_oldPartList");
	});
	//下载回运数据
	$("#dia_exp").bind("click",function(){
		var produceDate=$("#produceDate").val();
		var $f = $("#di_oldPartFm");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/service/oldpartMng/OldPartPackAction/download.do?produceDate="+produceDate+"");
		$("#exportFm").submit();
	});
	//导入
	$("#dia_imp").bind("click",function(){
        //9:表示最大列，3：表示有效数据是第几行开始，第一行是1
        //最后一个参数表示 导入成功后显示页
        importXls("SE_BU_RETURNORDER_DTL_TMP",$('#orderId').val(),18,3,"/jsp/dms/dealer/service/oldpart/importSuccess.jsp");
	});
	//明细删除
	$("#dia_delete").bind("click",function(){
		var detailIds=$("#val0").val();
	    if(detailIds=="")
	    {
	    	 alertMsg.warn("请选择删除信息。");
	    	 return false;
	    }else{
	    	var url =diaSaveAction+"/detailDelete.ajax?detailIds="+detailIds;
			sendPost(url,"dia_delete","",deleteDetailCallBack,"true");
	    }

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
//新增回调
function diaInsertCallBack(res){
	try
	{
		var rows = res.getElementsByTagName("ROW");
		if(rows && rows.length > 0)
		{
			var orderId =getNodeText(rows[0].getElementsByTagName("ORDER_ID").item(0));
			var orderNo =getNodeText(rows[0].getElementsByTagName("ORDER_NO").item(0));
			$("#orderId").val(orderId);
			$("#orderNo").val(orderNo);
		}else
		{
			return false;
		}
		//不显示结果集的情况下，插入一行
		parent.$("#oldPartList").insertResult(res,0);
		if(parent.$("#oldPartList_content").size()>0){
			$("td input[type=radio]",parent.$("#oldPartList_content").find("tr").eq(0)).attr("checked",true);			
		}else{
			$("td input[type=radio]",parent.$("#oldPartList").find("tr").eq(0)).attr("checked",true);
		}
			
		$("#backH").show();
		//$("#submitB").show();
		$("#btnNext1").show();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//修改回调
function diaUpdateCallBack(res){
	try
	{
		var selectedIndex = parent.$("#oldPartList").getSelectedIndex();
		parent.$("#oldPartList").updateResult(res,selectedIndex);
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//提报回调
function diaReportCallBack(res){
	try
	{
		var result = getNodeText(res.getElementsByTagName("RESULT").item(0));
		if(result!='1'){
			var $row = parent.$("#oldPartList").getSelectedRows();//选择行
			if($row[0]){
				parent.$("#oldPartList").removeResult($row[0]);//移除选择行
				parent.$.pdialog.closeCurrent();
				return false;
			}
		}
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//列表复选
function doCheckbox(checkbox)
{
	var arr = [];
	var $checkbox = $(checkbox);
	var detailId = $(checkbox).val();
	arr.push(detailId);
	multiSelected($checkbox,arr);
}
//删除回调方法
function deleteDetailCallBack(res){
	try
	{
		var $f = $("#di_oldPartTab");
		var sCondition = {};
		sCondition = $f.combined() || {};
		var url =diaSaveAction+"/returnPartSearch.ajax?orderId="+$("#orderId").val();
		doFormSubmit($f,url,"",1,sCondition,"dia_oldPartList");
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//导入回调方法
function impCall(){
	var $f = $("#di_oldPartFm");
	var sCondition = {};
	sCondition = $f.combined() || {};
	var url =diaSaveAction+"/returnPartSearch.ajax?orderId="+$("#orderId").val();
	doFormSubmit($f,url,"",1,sCondition,"dia_oldPartList");
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
					<li id="backH" style="display:none"><a href="javascript:void(0)"><span>回运清单导入</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent">
		<div class="page">
		<div class="pageContent" style="" >
			<form method="post" id="dia-oldpartfm" class="editForm" >
				<input type="hidden" id="orderId" name="orderId" datasource="ORDER_ID"/>
				<input type="hidden" id="orderId" name="orderStatus" datasource="ORDER_STATUS"/>
				<div align="left">
				<fieldset>
				<table class="editTable" id="dia-oldpartTab">
				    <tr>
						<td ><label>回运单号：</label></td>
						<td colspan="3"><input type="text" id="orderNo" name="orderNo" datasource="ORDER_NO" value="系统自动生成" readonly="readonly"/></td>
					</tr>
					<tr>	
						<td><label>旧件产生年月：</label></td>
						<td><input type="text" id="produceDate" name="produceDate" datasource="PRODUCE_DATE" style="width:75px;"   onclick="WdatePicker({maxDate:'<%=date%>',dateFmt:'yyyy-MM'})" datatype="0,is_null,30" /></td>
						<!-- <td><label>回运日期：</label></td>
						<td><input type="text" id="returnDate" name="returnDate" datasource="RETURN_DATE" value=""  onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" datatype="0,is_null,30" /></td> -->
					</tr>
					<tr>	
						<td><label>运输方式：</label></td>
						<td><select  type="text" id="transType" name="transType" datasource="TRANS_TYPE" kind="dic" class="combox" src="HYDYSFS"  datatype="1,is_null,6" value="" >
								<option value="302601" selected>配货</option>
							</select>
						</td>
						<td ><label>装箱总数量：</label></td>
						<td><input type="text" id="amount" name="amount" datasource="AMOUNT" datatype="1,is_digit,10"/></td>
					</tr>
					<tr>
						<td><label>备注：</label></td>
					    <td colspan="3"><textarea class="" rows="2" id="remarks" name="remarks" datasource="REMARKS" style="width:100%" datatype="1,is_null,1000"></textarea></td>
					</tr>
				</table>
				</fieldset>
				</div>
			</form>
		</div>
		<div class="formBar">
				<ul>
					<li><div class="button"><div class="buttonContent"><button type="button" id="dia_save">保&nbsp;&nbsp;存</button></div></div></li>
					<li id="btnNext1" style="display:none"><div class="button"><div class="buttonContent"><button type="button" id="next1" name="btn-next">下一步</button></div></div></li>
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
						<td><label>渠道商代码：</label></td>
						<td><input type="text" id="dealerCode" name="dealerCode" datasource="M.DEALER_CODE" operation="like" datatype="1,is_null,100"  value="" /></td>
						<td><label>渠道商名称：</label></td>
						<td><input type="text" id="dealerName" name="dealerName" datasource="M.DEALER_SHORTNAME" operation="like" datatype="1,is_null,100"  value="" /></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia_searchDetail">查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="dia_submit">提&nbsp;&nbsp;报</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="next2" name="btn-pre">上一步</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
		</div>
		<div class="pageContent">
			<div class="panelBar">
				<ul class="toolBar">
					<li><a class="add" href="javascript:void(0);" id="dia_imp" ><span>批量导入</span></a></li>
					<li class="line">line</li>
					<li><a class="icon" href="javascript:void(0);" id="dia_exp" title="实要导出这些记录吗?"><span>批量导出</span></a></li>
					<li class="line">line</li>
					<li><a class="delete" href="javascript:void(0);" id="dia_delete" title="确定要删除吗?"><span>批量删除</span></a></li>
				</ul>
			</div>
			<div id="dia_oldPart">
				<table style="display:none;width:100%;"  id="dia_oldPartList" name="dia_oldPartList" ref="dia_oldPart" refQuery="di_oldPartTab">
					<thead>
						<tr>
							<th type="multi" name="XH" style="display:" unique="DETAIL_ID" ></th>
							<th fieldname="CODE">渠道商代码</th>
							<th fieldname="ONAME">渠道商名称</th>
							<th fieldname="CLAIM_NO">索赔单号</th>
							<th fieldname="PART_CODE">车辆型号</th>
							<th fieldname="VIN">VIN</th>
							<th fieldname="PART_CODE">配件代码</th>
							<th fieldname="PART_NAME">配件名称</th>
							<th fieldname="SHOULD_COUNT">应返数量</th>
							<th fieldname="OUGHT_COUNT">实返数量</th>
							<th fieldname="MISS_COUNT">缺失数量</th>
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
		<table style="display:none">
			<tr>
				<td><textarea id="val0" name="multivals" style="width:400px;height:10px" column="1" style="" ></textarea></td>
			</tr>
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