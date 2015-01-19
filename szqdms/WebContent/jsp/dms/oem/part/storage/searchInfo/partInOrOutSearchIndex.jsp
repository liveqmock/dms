<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>配件出入库查询</title>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 仓储相关   &gt; 配件出入库查询</h4>
	<div class="page" >
		<div class="pageHeader" >
		<form method="post" id="fm-searchContract">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-htcx">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>配件代码：</label></td>
						<td>
							<input type="text" id="PART_CODE" name="PART_CODE" datatype="0,is_null,600" action="show" dataSource="PART_CODE" readOnly hasBtn="true" callFunction="getPart()" />
							<input type="hidden" id="PART_ID" name="PART_ID" datatype="1,is_null,600" action="show" dataSource="PART_ID"  />
						</td>
						<td><label>配件名称：</label></td>
						<td><input type="text" id="PART_NAME" name="PART_NAME" datatype="1,is_null,600" action="show" dataSource="PART_NAME" readonly="readonly"/></td>
					    <td><label>所属仓库：</label></td>
					    <td>
					    	<input type="text" id="WAREHOUSE_NAME" name="WAREHOUSE_NAME" datasource="" datatype="0,is_digit_letter_cn,30" src="T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME{WAREHOUSE_ID,WAREHOUSE_NAME}:1=1 AND WAREHOUSE_TYPE IN (100101,100102,100109,100103,100105,100104,100110,100111) AND WAREHOUSE_STATUS=<%=DicConstant.YXBS_01 %> ORDER BY WAREHOUSE_CODE" kind="dic" readonly="readonly"/>
							<input type="hidden" id="WAREHOUSE_ID" name="WAREHOUSE_ID" datatype="1,is_null,30" dataSource="WAREHOUSE_ID" action="show"/>
						</td>
					</tr>
					<tr>
					    <td><label>出入库时间段：</label></td>
						<td>
							<input type="text" id="IN_OUT_DATE_B" name="IN_OUT_DATE_B" datasource="BEGIN_DATE" style="width: 75px;" datatype="0,is_null,30"
                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'IN_OUT_DATE_E\')}'})" kind ="date" operation=">="/> 
                                  <span style="float: left; margin-left: -40px; margin-top: 5px;">至</span>
                                  <input type="text" id="IN_OUT_DATE_E" name="IN_OUT_DATE_E" datasource="END_DATE" style="width: 75px; margin-left: -20px;"  datatype="0,is_null,30"
                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'IN_OUT_DATE_B\')}'})" kind ="date"  operation="<="/>
						</td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-clear" >重置</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
		</div>
		<div class="pageContent">
		<div id="page_order" style="overflow:auto;">
			<table style="display:none;width:100%;"class="dlist" id="tab-order_info" name="tablist" ref="page_order">
					<thead>
						<tr>	
							<th>序号</th>
                            <th>日期</th>
                            <th>单号</th>
                            <th>入库数量</th>
                            <th>出库数量</th>
                            <th>余额</th>
						</tr>
					</thead>
					<tbody id="partInfo">
                    </tbody>
			</table>
		</div>
		</div>
	</div>
</div>
</body>
</html>
<script type="text/javascript">
var diaAddOptions = {max:true,width:1024,height:430,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
var mngUrl = "<%=request.getContextPath()%>/part/storageMng/search/PartStockInOrOutSearchMngAction";
$(function()
{
	$("#page_order").height(document.documentElement.clientHeight-140);
	$("#btn-search").bind("click", function(event){
		var searchUrl = mngUrl+"/stockSearch.ajax";
		var partId = $("#PART_ID").val();
		if(!partId){
			 alertMsg.warn('请先选择配件!');
			 return false;
		}
		var warehouseId = $("#WAREHOUSE_ID").val();
		if(!warehouseId){
			 alertMsg.warn('请先选择仓库!');
			 return false;
		}
		var begin = $("#IN_OUT_DATE_B").val();
		if(!begin){
			 alertMsg.warn('请先选择开始时间!');
			 return false;
		}
		var end = $("#IN_OUT_DATE_E").val();
		if(!end){
			 alertMsg.warn('请先选择结束时间!');
			 return false;
		}
		var $f = $("#tab-htcx");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		sendPost(searchUrl, "", sCondition, doSearchCallBack, "");
	});
	$("#btn-clear").click(function(){
		$("input","#tab-htcx").each(function(index, obj){
			$(obj).val("");
		});
		$("#partInfo").html("");
	});
});

function getPart(){
    var options = {max: true, mask: true, mixable: true, minable: true, resizable: false, drawable: true};
    $.pdialog.open(webApps+"/jsp/dms/oem/part/storage/searchInfo/getPartInfo.jsp", "getPartInfo", "配件查询", options);
}
function afterDicItemClick(id,$row){
	var ret = true;
	if(id == "WAREHOUSE_NAME")
	{
		$("#WAREHOUSE_NAME").val($row.attr("WAREHOUSE_NAME"));
		$("#WAREHOUSE_NAME").attr("code",$row.attr("WAREHOUSE_NAME"));
		$("#WAREHOUSE_ID").val($row.attr("WAREHOUSE_ID"));
		
	}
	return ret;
}
function doSearchCallBack(res){
	$("#partInfo").html("");
	$("#tab-order_info").show();
	var rows = res.getElementsByTagName("ROW");
	var inCount = 0;
	var outCount = 0;
    if(rows && rows.length > 0){
    	 $tab =$("#partInfo");
         var objXML;
         var xh = 0;
         var $row0 = $("<tr></tr>")
         var $tda = $("<td>期初余额</td>");
         $row0.append($tda);
         var $tdb = $("<td></td>");
         $row0.append($tdb);
         var $tdc = $("<td></td>");
         $row0.append($tdc);
         var $tdd = $("<td></td>");
         $row0.append($tdd);
         var $tde = $("<td></td>");
         $row0.append($tde);
         var amount = getNodeText(rows[0].getElementsByTagName("BALANCE_AMOUNT").item(0));
         var orderId = getNodeText(rows[0].getElementsByTagName("ORDER_ID").item(0));
         if(orderId=='1'){
        	 var $tdf = $("<td>"+amount+"</td>");
         }else{
        	 var $tdf = $("<td>"+"0"+"</td>");
         }
         $row0.append($tdf);
         $tab.append($row0);
         //订单列循环
         if(orderId=='1'){
        	 for(var i=1;i<rows.length;i++){
            	 xh++;
            	 var $row = $("<tr></tr>");
            	 var $td0 = $("<td><input type='hidden' name ='type' id='TYPE' value="+getNodeText(rows[i].getElementsByTagName("TYPE").item(0))+"></input>"+xh+"</td>");
            	 $row.append($td0);
            	 var $td1 = $("<td>"+getNodeText(rows[i].getElementsByTagName("STOCK_DATE").item(0))+"</td>");
             	 $row.append($td1);
             	 var $td2 = $("<td ondblclick='doDblclick(this)'><font size='4' color='red'><input type='hidden' name = 'id' id='ID' value="+getNodeText(rows[i].getElementsByTagName("ORDER_ID").item(0))+"></input>"+getNodeText(rows[i].getElementsByTagName("ORDER_NO").item(0))+"<fond></td>");
            	 $row.append($td2);
            	 var $td3 = $("<td>"+getNodeText(rows[i].getElementsByTagName("IN_AMOUNT").item(0))+"</td>");
            	 $row.append($td3);
            	 var $td4 = $("<td>"+getNodeText(rows[i].getElementsByTagName("OUT_AMOUNT").item(0))+"</td>");
            	 $row.append($td4);
            	 inCount = parseInt(inCount) +parseInt(getNodeText(rows[i].getElementsByTagName("IN_AMOUNT").item(0)));
            	 outCount = parseInt(outCount) + parseInt(getNodeText(rows[i].getElementsByTagName("OUT_AMOUNT").item(0)));
            	 /* var $td5 = $("<td>"+getNodeText(rows[i].getElementsByTagName("BALANCE_AMOUNT").item(0))+"</td>"); */
            	 var bCount = parseInt(amount)+parseInt(inCount)-parseInt(outCount);
            	 var $td5 = $("<td>"+bCount+"</td>");
            	 /* var $td5 = $("<td>"+getNodeText(rows[i].getElementsByTagName("BALANCE_AMOUNT").item(0))+"</td>");*/
            	 $row.append($td5); 
            	 $tab.append($row);
            	/*  inCount = parseInt(inCount) +parseInt(getNodeText(rows[i].getElementsByTagName("IN_AMOUNT").item(0)));
            	 outCount = parseInt(outCount) + parseInt(getNodeText(rows[i].getElementsByTagName("OUT_AMOUNT").item(0))) */
             }
         }else{
        	 for(var i=0;i<rows.length;i++){
            	 xh++;
            	 var $row = $("<tr></tr>");
            	 var $td0 = $("<td><input type='hidden' name ='type' id='TYPE' value="+getNodeText(rows[i].getElementsByTagName("TYPE").item(0))+"></input>"+xh+"</td>");
            	 $row.append($td0);
            	 var $td1 = $("<td>"+getNodeText(rows[i].getElementsByTagName("STOCK_DATE").item(0))+"</td>");
             	 $row.append($td1);
             	 var $td2 = $("<td ondblclick='doDblclick(this)'><font size='4' color='red'><input type='hidden' name = 'id' id='ID' value="+getNodeText(rows[i].getElementsByTagName("ORDER_ID").item(0))+"></input>"+getNodeText(rows[i].getElementsByTagName("ORDER_NO").item(0))+"<fond></td>");
            	 $row.append($td2);
            	 var $td3 = $("<td>"+getNodeText(rows[i].getElementsByTagName("IN_AMOUNT").item(0))+"</td>");
            	 $row.append($td3);
            	 var $td4 = $("<td>"+getNodeText(rows[i].getElementsByTagName("OUT_AMOUNT").item(0))+"</td>");
            	 $row.append($td4);
            	 inCount = parseInt(inCount) +parseInt(getNodeText(rows[i].getElementsByTagName("IN_AMOUNT").item(0)));
            	 outCount = parseInt(amount)+parseInt(outCount) + parseInt(getNodeText(rows[i].getElementsByTagName("OUT_AMOUNT").item(0)));
            	 /* var $td5 = $("<td>"+getNodeText(rows[i].getElementsByTagName("BALANCE_AMOUNT").item(0))+"</td>"); */
            	 var bCount = parseInt(inCount)-parseInt(outCount);
            	 var $td5 = $("<td>"+bCount+"</td>");
            	 $row.append($td5);
            	 $tab.append($row);
            	 
             }
         }
         
         //合计列
         var $row1 = $("<tr></tr>")
         var $tda1 = $("<td>合计</td>");
         $row1.append($tda1);
         var $tdb1 = $("<td></td>");
         $row1.append($tdb1);
         var $tdc1 = $("<td></td>");
         $row1.append($tdc1);
         var $tdd1 = $("<td>"+inCount+"</td>");
         $row1.append($tdd1);
         var $tde1 = $("<td>"+outCount+"</td>");
         $row1.append($tde1);
         var finish = parseInt(amount)+parseInt(inCount)-parseInt(outCount);
         var $tdf1 = $("<td>"+finish+"</td>");
         $row1.append($tdf1);
         $tab.append($row1);
         //期末余额
         var $row3 = $("<tr></tr>")
         var $tda3 = $("<td>期末余额</td>");
         $row3.append($tda3);
         var $tdb3 = $("<td></td>");
         $row3.append($tdb3);
         var $tdc3 = $("<td></td>");
         $row3.append($tdc3);
         var $tdd3 = $("<td></td>");
         $row3.append($tdd3);
         var $tde3 = $("<td></td>");
         $row3.append($tde3);
         var finish = parseInt(amount)+parseInt(inCount)-parseInt(outCount);
         var $tdf3 = $("<td>"+finish+"</td>");
         $row3.append($tdf3);
         $tab.append($row3);
    }
}
function doDblclick(obj)
{
	var $obj = $(obj);
	var $tr = $obj;
    while($tr.get(0).tagName != "TR")
    	$tr = $tr.parent();
    var type = $tr.find("td").eq(0).find("input:first").val();
    var id = $tr.find("td").eq(2).find("input:first").val();
    if(type=="1"){
    	openInDetailPage(id);
    }else{
    	openOutDetailPage(id);
    } 
}
function openOutDetailPage(outId){
	var Ooptions = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
    $.pdialog.open(webApps+"/jsp/dms/oem/part/storage/searchInfo/orderOfPartOut.jsp?OUT_ID="+outId, "outWin", "出库单详细信息", Ooptions);
}
function openInDetailPage(inId){
	var Ioptions = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
    $.pdialog.open(webApps+"/jsp/dms/oem/part/storage/searchInfo/orderOfPartIn.jsp?IN_ID="+inId, "inWin", "入库单详细信息", Ioptions);
} 
</script>