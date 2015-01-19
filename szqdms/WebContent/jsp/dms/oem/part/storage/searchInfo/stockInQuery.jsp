<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>
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
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 仓储相关   &gt; 入库单查询</h4>
		<div class="page">
			<div class="pageHeader" >
					<form method="post" id="fm-searchContract">
						<!-- 定义隐藏域条件 -->
						<div class="searchBar" align="left" >
							<table class="searchContent" id="tab-htcx">
								<!-- 定义查询条件 -->
								<tr>
									<td><label>入库单号：</label></td>
									<td><input type="text" id="IN_NO" name="IN_NO" datasource="IN_NO" value="" operation="like" datatype="1,is_digit_letter,30" /></td>
									<td><label>采购单号：</label></td>
									<td><input type="text" id="ORDER_NO" name="ORDER_NO" datasource="ORDER_NO" value="" operation="like" datatype="1,is_digit_letter_cn,30" /></td>
								</tr>
								<tr>
									<td><label>配件代码：</label></td>
									<td><input type="text" id="PART_CODE" name="PART_CODE" datasource="PART_CODE"  value="" operation="like" datatype="1,is_null,300" /></td>
									<td><label>配件名称：</label></td>
									<td><input type="text" id="PART_NAME" name="PART_NAME" datasource="PART_NAME" value="" operation="like" datatype="1,is_null,300" /></td>
								</tr>
								<tr>
									<td><label>是否打印：</label></td>
									<td>
										<select class="combox" name="PRINT_STATUS" id="PRINT_STATUS" datasource="PRINT_STATUS" kind="dic" src="<%=DicConstant.DYZT%>" operation="=" datatype="1,is_null,6">
											<option value="-1" selected="selected">--</option>
										</select>	
									</td>
									<td><label>入库类型：</label></td>
									<td>
										<select name="IN_TYPE" id="IN_TYPE" PRINT_STATUS="IN_TYPE" datasource="IN_TYPE" kind="dic" src="<%=DicConstant.RKLX %>" operation="=" datatype="1,is_null,6">
											<option value="-1" selected="selected">--</option>
										</select>
									</td>
								</tr>
								<tr>
									<td><label>入库日期：</label></td>
									<td>
										<input type="text" id="IN_DATE_B" name="IN_DATE_B" style="width: 75px;" datasource="IN_DATE" datatype="1,is_null,30"
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" kind ="date" operation=">="/> 
	                                    <span style="float: left; margin-left: -50px; margin-top: 5px;">至</span>
	                                    <input type="text" id="IN_DATE_E" name="IN_DATE_E" style="width: 75px; margin-left: -30px;" datasource="IN_DATE" datatype="1,is_null,30"
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" kind ="date"  operation="<="/>
									</td>
									<td><label>打印日期：</label></td>
									<td>
										<input type="text" id="PRINT_DATE_B" name="PRINT_DATE_B" style="width: 75px;" datasource="PRINT_DATE" datatype="1,is_null,30"
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" kind ="date" operation=">="/> 
	                                    <span style="float: left; margin-left: -50px; margin-top: 5px;">至</span>
	                                    <input type="text" id="PRINT_DATE_E" name="PRINT_DATE_E" style="width: 75px; margin-left: -30px;" datasource="PRINT_DATE" datatype="1,is_null,30"
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" kind ="date"  operation="<="/>
									</td>
								</tr>
								<tr>
									<td><label>配送号：</label></td>
									<td colspan="3">
										<input type="text" id="DISTRIBUTION_NO" name="DISTRIBUTION_NO" datasource="DISTRIBUTION_NO"  value="" operation="like" datatype="1,is_null,300" />
									</td>
								</tr>
								<tr>
									<td><label>退件单位代码：</label></td>
									<td>										
										<input type="text" id="ORG_CODE" name="ORG_CODE"  datasource="ORG_CODE" action="show"  datatype="1,is_null,100" operation="=" readonly="readonly" hasBtn="true" callFunction="openDealer();"/>
									</td>
									<td><label>退件单位名称：</label></td>
									<td>										
										<input type="text" id="ORG_NAME" name="ORG_NAME"  datatype="1,is_null,100" operation="in" readonly="readonly" />
									</td>
								</tr>
							</table>
							<div class="subBar">
								<ul>
									<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
									<li><div class="button"><div class="buttonContent"><button type="button" id="btn-export-index">导出数据</button></div></div></li>
									<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn_clear_btn" >重置查询条件</button></div></div></li>
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
									<th fieldname="IN_NO" colwidth="175px" refer="showNoInfo">入库单号</th>
									<th fieldname="ORDER_NO" colwidth="175px">原单号</th>
									<th fieldname="PLAN_AMOUNT" refer="amountFormat" align="right">计划金额</th>
									<th fieldname="SALE_AMOUNT" refer="amountFormat" align="right">销售金额</th>
									<th fieldname="WAREHOUSE_NAME" colwidth="80px">仓库</th>
									<th fieldname="IN_TYPE" colwidth="60px">入库类型</th>
									<th fieldname="RETURN_NAME" colwidth="150px">退件单位名称</th>
									<th fieldname="IN_DATE" colwidth="140px">入库日期</th>
									<th fieldname="SUPPLIER_NAME" >供应商</th>
									<th fieldname="PRINT_STATUS" colwidth="60px">是否打印</th>
									<th fieldname="PRINT_DATE" colwidth="140px" >打印日期</th>
									<th fieldname="PRINT_MAN" >打印人</th>
									<th colwidth="50" type="link" title="[打印]"  action="doPrint" >操作</th>
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
		var queryAction = "<%=request.getContextPath()%>/part/storageMng/search/StockInQueryAction/queryListInfo.ajax";
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
        var url = encodeURI("<%=request.getContextPath()%>/part/storageMng/search/StockInQueryAction/download.do");
        window.location.href = url;
        $("#exportFm").attr("action",url);
        $("#exportFm").submit();
    });
	
	// 清除查询区域
	$("#btn_clear_btn").click(function(){
		$("input", "#tab-htcx").each(function(index, obj){
			$(obj).val("");
		});
	});
})

// 入库单号超链接
function showNoInfo($cell){
	$tr = $cell.parent();
	return "<a style='color:red' href='javascript:openDetailPage(\""+$tr.attr("IN_ID")+"\")'>"+$tr.attr("IN_NO")+"</a>";
}

// 打开详细页面
function openDetailPage(inId){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
    $.pdialog.open(webApps+"/jsp/dms/oem/part/storage/searchInfo/stockInQueryForDetails.jsp?inId="+inId, "forDetailsPage", "入库单详细信息", options);
}


function doPrint(rowobj) {
   $row = $(rowobj);
   var printUrl = "";
   var type= $row.attr("IN_TYPE");
   if(type == <%=DicConstant.RKLX_01%>){
	   printUrl = "<%=request.getContextPath()%>/part/storageMng/stockInMng/InBillPrintAction/printInPdf.do?IN_ID="+$row.attr("IN_ID")+"&flag=2";
   }else if(type == <%=DicConstant.RKLX_02%>){
	   printUrl = "<%=request.getContextPath()%>/part/storageMng/stockInMng/InBillPrintAction/printMovePdf.do?IN_ID="+$row.attr("IN_ID")+"&flag=2";
   }else if(type == <%=DicConstant.RKLX_03%>){
	   printUrl = "<%=request.getContextPath()%>/part/storageMng/stockInMng/InBillPrintAction/printRetPdf.do?IN_ID="+$row.attr("IN_ID")+"&flag=2";
   }else{
	   printUrl = "<%=request.getContextPath()%>/part/storageMng/stockInMng/InBillPrintAction/printOtherPdf.do?IN_ID="+$row.attr("IN_ID")+"&flag=2";
   }
   window.open(printUrl);
}

//金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}

function openDealer(){
	var id="ORG_CODE";
	/* 	showOrgTree(id,busType); */
	/**
	 * create by andy.ten@tom.com 2011-12-04
	 * 弹出组织选择树
	 * id:弹出选择树的input框id
	 * busType:业务类型[1,2]，1表示配件2表示售后
	 * partOrg:配件业务使用：[1,2]，1表示只显示配送中心2表示只显示服务商和经销商，不传表示全显示
	 * multi:[1,2],1表示默认是多选，2表示是单选
	 */
	showOrgTree(id,1,1,2);
	//页面实现回调方法：
	//jsonObj:{"orgId":"","orgCode":"","orgName":""}
}
function showOrgTreeCallBack(jsonObj)
{
	  var orgId=jsonObj.orgId;
	  var orgCode=jsonObj.orgCode;
	  var orgName=jsonObj.orgName;
	  $("#ORG_CODE").val(orgCode).attr("code",orgCode); 
	  $("#ORG_NAME").val(orgName);
}

</script>