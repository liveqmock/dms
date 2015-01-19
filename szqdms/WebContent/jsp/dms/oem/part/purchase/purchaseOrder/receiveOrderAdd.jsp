<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="com.org.frameImpl.Constant" %>
<%@ page import="com.org.framework.Globals" %>
<%@ page import="com.org.framework.common.User" %>
<%@ page import="com.org.dms.common.DicConstant" %>
<%@page import="com.org.framework.util.Pub"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
    String contentPath = request.getContextPath();
    String action = request.getParameter("action");
    if (action == null)
        action = "1";
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
    String yearStr1 = yearFormat.format(Pub.getCurrentDate());
    String monthStr1 = monthFormat.format(Pub.getCurrentDate());
%>
<div id="dia-layout">
    <div class="tabs" eventType="click" id="dia-tabs">
        <div class="tabsHeader">
            <div class="tabsHeaderContent">
                <ul>
                    <li><a href="javascript:void(0);"><span>领料单基本信息</span></a></li>
                    <li><a href="javascript:void(0);"><span>配件信息</span></a></li>
                </ul>
            </div>
        </div>
        <div class="tabsContent">
            <div style="height:auto;overflow:hidden;">
                <form method="post" id="dia-fm-orderEdit" class="editForm" style="width:99%;">
		  <div align="left">
			  <fieldset>
						<table class="editTable" id="dia-tab-order">
						<input type="hidden" id="dia-REC_ID" name="dia-REC_ID" datasource="REC_ID" />
							<tr>
								<td><label>订单编号：</label></td>
								<td><input type="text" id="dia-SPLIT_NO" name="dia-SPLIT_NO" datasource="SPLIT_NO" datatype="0,is_null,30" hasBtn="true" callFunction="openPurchase('SPLIT_ID');" readonly="true"/>
							    	<input type="hidden" id="dia-SPLIT_ID" name="dia-SPLITID" datasource="SPLIT_ID" />
							    </td>
								<td><label>厂家代码：</label></td>
							    <td>
							    	<input type="text" id="dia-SUPPLIER_CODE" name="dia-SUPPLIER_CODE" datasource="SUPPLIER_CODE"  datatype="0,is_null,300" readonly="true"/>
							    	<input type="hidden" id="dia-SUPPLIER_ID" name="dia-SUPPLIER_ID" datasource="SUPPLIER_ID" />
							    </td>
								<td><label>厂家名称：</label></td>
							    <td>
							    	<input type="text" id="dia-SUPPLIER_NAME" name="dia-SUPPLIER_NAME" datasource="SUPPLIER_NAME" datatype="0,is_null,300" readonly="true"/>
							    </td>
							</tr>
						</table>
			  </fieldset>
			</div>
			</form>
                <div class="formBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-save">保&nbsp;&nbsp;存</button></div></div></li>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" name="btn-next">下&nbsp;一&nbsp;步</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
                    </ul>
                </div>
            </div>
            <div class="page">
                <div class="pageContent" >
                <form method="post" id="fm-searchPart" class="editForm">
                        <table class="searchContent" id="tab-searchPart"></table>
                    </form>
                    <form id="diaSaveFm" method="post">
		                <input type="hidden" id="RE_ID" name="RE_ID" datasource="PCH_ID"/>
		                <input type="hidden" id="DTL_ID" name="DTL_ID" datasource="DTL_ID"/>
		                <input type="hidden" id="COUNT" name="COUNT" datasource="COUNT"/>
		                <input type="hidden" id="RMK" name="RMK" datasource="RMK"/>
		                <input type="hidden" id="P_ID" name="P_ID" datasource="P_ID"/>
		                <input type="hidden" id="PBNO" name="PBNO" datasource="PBNO"/>
		            </form>
                    <div id="dia-part" style="">
                    <table style="display:none;width:100%;" id="dia-tab-partinfo" layoutH="350" name="tablist"ref="dia-part" refQuery="tab-searchPart">
                        <thead>
                        <tr>
                            <th type="single" name="XH" append="plus|addParts"></th>
                            <th fieldname="PART_CODE" colwidth="115">配件代码</th>
                            <th fieldname="PART_NAME" colwidth="115">配件名称</th>
                            <th fieldname="UNIT" colwidth="75">计量单位</th>
							<th fieldname="MIN_PACK" colwidth="75" refer="toAppendStr">最小包装</th>
                            <th fieldname="PCH_PRICE" colwidth="75" refer="amountFormatStr" align="right">采购价格</th>
                            <th fieldname="PCH_COUNT" colwidth="75" refer="myOrderCount">采购数量</th>
                            <th fieldname="PCH_AMOUNT" colwidth="75" refer="amountFormatStr" align="right">金额</th>
                            <th fieldname="PB_NO" colwidth="145" refer="myNo">计配号</th>
                            <th fieldname="REMARK" colwidth="145" refer="myRemark">备注</th>
                            <th colwidth="105" type="link" title="[保存]|[删除]"  action="doDiaPartSave|doPartDelete">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
                </div>
                <div class="formBar">
                    <ul>
                        <li><div class="button"><div class="buttonContent"><button type="button" id="btn-print">打&nbsp;&nbsp;印</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button type="button" name="btn-pre">上&nbsp;一&nbsp;步</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="tabsFooter">
            <div class="tabsFooterContent"></div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //注册上一步/下一步按钮响应
/* 取父页面传过的参数确定新增或者修改 */
    var diaAction = "<%=action%>";
    var diaUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseOrder/PurchaseOrderMngAction";
    var flag = true;
    $(function () {
    var iH = document.documentElement.clientHeight;
    $(".tabsContent").height(iH - 70);
    $("button[name='btn-pre']").bind("click",function(event){
        $("#dia-tabs").switchTab(parseInt($("#dia-tabs").attr("currentIndex"))-1);
    });
    $("button[name='btn-next']").bind("click",function(event){
        var $tabs = $("#dia-tabs");
        $tabs.switchTab(parseInt($tabs.attr("currentIndex"))+1);
        //跳转后实现方法
        switch(parseInt($tabs.attr("currentIndex")))
        {
            case 1:
                if (!$('#dia-REC_ID').val()) {
                    alertMsg.warn('请先保存领料票信息!');
                    $tabs.switchTab(parseInt($tabs.attr("currentIndex"))-1);
                } else {
                    if(flag){
                        if ($("#dia-tab-partinfo").is(":hidden")) {
                            $("#dia-tab-partinfo").show();
                            $("#dia-tab-partinfo").jTable();
                        }
                        searchPart();
                    }
                    flag = false;
                }
                break;
            default:
                break;
        }
        
    });
        //保存基本信息按钮响应
        $("#btn-save").bind("click", function(event){
			var $f = $("#dia-fm-orderEdit");
			if (submitForm($f) == false) return false;
			var sCondition = {};
			sCondition = $("#dia-fm-orderEdit").combined(1) || {};
			if(diaAction == 1)
			{
				var addUrl = diaUrl + "/purchaseReceiveOrderInsert.ajax";
				doNormalSubmit($f,addUrl,"btn-save",sCondition,diaInsertCallBack);
			}else
			{
				var updateUrl = diaUrl + "/purchaseOrderUpdate.ajax?";
				doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
			}
		});
    	//关闭按钮响应
    	$("#button.close").click(function(){
    		$.pdialog.closeCurrent();
    		return false;
    	});
    	<%--  $("#btn-print").click(function(){
    		var queryUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseOrder/PurchaseOrderMngAction/printReceivePdf.do?REC_ID="+$("#dia-REC_ID").val();
    		window.open(queryUrl);
    	});  --%>
    	 $('#btn-print').click(function(){
         	//window.open(webApps + "/jsp/dms/oem/part/storage/stockIssueMng/printIssuePrint.jsp");
         	var queryUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseOrder/PurchaseOrderMngAction/printReceivePdf.do?REC_ID="+$("#dia-REC_ID").val();
             window.open(queryUrl);
         });
		//修改页面赋值
        if (diaAction != "1") {
        	$("#dia-contror").show();
            var selectedRows = $("#tab-order_info").getSelectedRows();
            setEditValue("dia-fm-orderEdit", selectedRows[0].attr("rowdata"));
            $("#diaOrderNo").html(selectedRows[0].attr("ORDER_NO"));
            $("#dia-SUPPLIER_CODE").attr("readonly",true);
            $("#dia-SUPPLIER_CODE").attr("src","");
			$("#dia-PURCHASE_TYPE").attr("disabled",true);
			$("#dia-REC_ID").val(selectedRows[0].attr("REC_ID"));
			$("#btn-save").hide();
        }
    })
	//新增方法回调
    function diaInsertCallBack(res)
    {
    	var rows = res.getElementsByTagName("ROW");
    	try
    	{
    		var recId = getNodeText(rows[0].getElementsByTagName("REC_ID").item(0));
            $('#dia-REC_ID').val(recId);
    		$("#dia-contror").show();
    		diaAction = 2;
    		if($("#tab-order_info_content").size()>0){
    			$("td input[type=radio]",$("#tab-order_info_content").find("tr").eq(0)).attr("checked",true);	
    		}else{
    			$("td input[type=radio]",$("#tab-order_info").find("tr").eq(0)).attr("checked",true);
    		}
    		$("#tab-order_info").insertResult(res,0);
    		$("#btn-save").hide();
    	}catch(e)
    	{
    		alertMsg.error(e);
    		return false;
    	}
    	return true;
    }
	//修改方法回调
    function diaUpdateCallBack(res)
    {
    	try
    	{
     		var selectedIndex = $("#tab-order_info").getSelectedIndex();
    		$("#tab-order_info").updateResult(res,selectedIndex); 
    	}catch(e)
    	{
    		alertMsg.error(e);
    		return false;
    	}
    	return true;
    }
     //查询采购订单配件
    function searchPart(){
    	$("#dia-contror").show();
    	var REC_ID = $("#dia-REC_ID").val();
   		var searchContractPartUrl = diaUrl + "/receivePartSearch.ajax?REC_ID="+REC_ID;
   		var $f = $("#dia-fm-orderEdit");
   		var sCondition = {};
   	    sCondition = $f.combined() || {};
   	    doFormSubmit($f, searchContractPartUrl, "", 1, sCondition, "dia-tab-partinfo");
    } 
    function addParts() {
        var diaoptions = {max: false, width: 900, height: 500, mask: true, mixable: true, minable: true, resizable: false, drawable: true};
        $.pdialog.open(webApps + "/jsp/dms/oem/part/purchase/purchaseOrder/receivePartSel.jsp", "partSelWin", "配件信息查询", diaoptions);
    }
    //采购订单配件删除
    var $row;
    function doPartDelete(rowobj)
    {
    	$row = $(rowobj);
    	var url = diaUrl + "/receivePartDelete.ajax?DETAIL_ID="+$(rowobj).attr("DETAIL_ID");
    	sendPost(url, "", "", deleteCallBack, "true");
    }
    //删除配件回调方法
    function  deleteCallBack(res)
    {
    	try
    	{
    		if($row) 
    			$("#dia-tab-partinfo").removeResult($row);
    	}catch(e)
    	{
    		alertMsg.error(e);
    		return false;
    	}
    	return true;
    }
	function toAppend(obj){
		 var $tr = $(obj).parent();
		 return $tr.attr("MIN_PACK")+"/"+ $tr.attr("UNIT_sv");
	}
	function toAppendStr(obj){
		var $tr =$(obj).parent();
		return $tr.attr("MIN_PACK")+"/"+$tr.attr("MIN_UNIT_sv");
	}
	function myOrderCount(obj){
		return "<input type='text' style='width:50px;' name='myCount' value='"+$(obj).html()+"' onblur='doMyCountBlur(this)' maxlength='6'/>";
	}
	function myRemark(obj){
		return "<input type='text' name='myRemark' value='"+$(obj).html()+"'/>";
	}
	function myNo(obj){
		return "<input type='text' name='myNo' value='"+$(obj).html()+"'/>";
	}
	function doMyCountBlur(obj){
		var $obj = $(obj);
	    if($obj.val() == "")
	        return false;
	    if($obj.val() && !isCount($obj))
	    {
	        alertMsg.warn("请正确输入订购数量！");
	        return false;
	    }
	    var $tr = $(obj).parent().parent().parent();
	    var orderCount = $tr.find("td").eq(7).find("input:first").val();
		var unitPrice = $tr.find("td").eq(6).text();
		var amount = parseInt(orderCount)*removeAmountFormat(unitPrice);
		$tr.find("td").eq(8).html("<div style='text-align:right;'>"+amountFormatNew(amount.toFixed(2))+"</div>");
	}
	//校验订购数量
	function isCount($obj)
	{
	    var reg = /^\+?[1-9][0-9]*$/;
	    if(reg.test($obj.val()))
	    {
	        return true;
	    }else
	    {
	        return false;
	    }
	}
	var $rowpart;
	function doDiaPartSave(rowobj){
		$rowpart = $(rowobj);
		var $obj = $(rowobj).find("td").eq(7).find("input:first");
		var $obj1 = $(rowobj).find("td").eq(10).find("input:first");
		var $obj2 = $(rowobj).find("td").eq(9).find("input:first");
		if($obj.val() && !isCount($obj))
	    {
	        alertMsg.warn("请正确输入数量！");
	        return false;
	    }
	    $("#RE_ID").val($("#dia-REC_ID").val());
	    $("#DTL_ID").val($(rowobj).attr("DETAIL_ID"));
	    $("#COUNT").val($obj.val());
	    $("#RMK").val($obj1.val());
	    $("#PBNO").val($obj2.val());
	    $("#P_ID").val($(rowobj).attr("PART_ID"));
	    
	    var $f = $("#diaSaveFm");
	    if (submitForm($f) == false) return false;
	    var sCondition = {};
	    sCondition = $f.combined(1) || {};
	    sendPost(diaUrl + "/receivOerderPartCountSave.ajax", "btnDiaSave", sCondition, savePartCountCallBack);
	}
	function savePartCountCallBack(res){
		try {
			var orderCount = $rowpart.find("td").eq(7).find("input:first").val();
			var unitPrice = $rowpart.find("td").eq(6).text();
			var amount = parseInt(orderCount)*removeAmountFormat(unitPrice);
			$rowpart.find("td").eq(8).html("<div style='text-align:right;'>"+amountFormatNew(amount.toFixed(2))+"</div>");
	    } catch (e) {
	        alertMsg.error(e);
	        return false;
	    }
	    return true;
	}
	function amountFormatStr(obj){
		return "<div style='text-align:right;'>"+amountFormatNew($(obj).html())+"</div>";
	}
	
	
	function openPurchase()
	{
		var options = {width:750,height:400,max:false,mask:true,mixable:false,minable:false,resizable:true,drawable:true}; 
		var url = webApps +"/jsp/dms/oem/part/purchase/purchaseOrderReturn/purchaseOrderSel.jsp";
		$.pdialog.open(url, "purchaseOrderSel", "采购单查询", options);

	}
	function SelCallBack(obj)
	{
		$("#dia-SPLIT_NO").val(obj.getAttribute("SPLIT_NO"));//原订单编号
		$("#dia-SPLIT_ID").val(obj.getAttribute("SPLIT_ID"));//原订单ID
		$("#dia-SUPPLIER_CODE").val(obj.getAttribute("SUPPLIER_CODE"));//原订单编号
		$("#dia-SUPPLIER_ID").val(obj.getAttribute("SUPPLIER_ID"));//原订单ID
		$("#dia-SUPPLIER_NAME").val(obj.getAttribute("SUPPLIER_NAME"));//原订单编号
	}
</script>