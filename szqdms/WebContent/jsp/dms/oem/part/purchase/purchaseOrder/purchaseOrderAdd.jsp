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
                    <li><a href="javascript:void(0);"><span>采购订单基本信息</span></a></li>
                    <li><a href="javascript:void(0);"><span>采购配件信息</span></a></li>
                </ul>
            </div>
        </div>
        <div class="tabsContent">
            <div style="height:auto;overflow:hidden;">
                <form method="post" id="dia-fm-orderEdit" class="editForm" style="width:99%;">
				<input type="hidden" id="dia-PURCHASE_ID" name="dia-PURCHASE_ID" datasource="PURCHASE_ID" />
				<input type="hidden" id="dia-ORDER_NO" name="dia-ORDER_NO" datasource="ORDER_NO" />
				<input type="hidden" id="dia-APPLY_DATE" name="dia-APPLY_DATE" datasource="APPLY_DATE" />
				<input type="hidden" id="dia-APPLY_USER" name="dia-APPLY_USER" datasource="APPLY_USER" />
		  <div align="left">
			  <fieldset>
						<table class="editTable" id="dia-tab-order">
							<tr>
								<td><label>订单编号：</label></td>
							    <td><div id="diaOrderNo">系统自动生成</div></td>
							    <td><label>所属月度：</label></td>
							    <td>
							    	<input type="text"  id="dia-SELECT_MONTH"  name="dia-SELECT_MONTH"   dataSource="SELECT_MONTH" style="width:75px;"   datatype="0,is_null,30" readonly="true" value="<%=yearStr1+"-"+monthStr1%>"/>
							    </td>
							    <td><label>采购类型：</label></td>
							    <td >	
								    <input type="text" id="dia-PURCHASE_TYPE"  name="dia-PURCHASE_TYPE" src="CGDDLX" datasource="PURCHASE_TYPE" value="其他订单" code="<%=DicConstant.CGDDLX_03%>" readonly="true"/>
								</td>
							</tr>
							<tr>
								<td><label>厂家代码：</label></td>
							    <td>
							    	<input type="text" id="dia-SUPPLIER_CODE" name="dia-SUPPLIER_CODE" datasource="SUPPLIER_CODE" hasBtn="true" callFunction="openSupplier('SPLIT_ID');"  datatype="0,is_null,300" readonly="readonly"/>
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
			<form id="exportFm" method="post" style="display:none">
	          <input type="hidden" id="data" name="data"></input>
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
		                <input type="hidden" id="PCH_ID" name="PCH_ID" datasource="PCH_ID"/>
		                <input type="hidden" id="DTL_ID" name="DTL_ID" datasource="DTL_ID"/>
		                <input type="hidden" id="COUNT" name="COUNT" datasource="COUNT"/>
		                <input type="hidden" id="RMK" name="RMK" datasource="RMK"/>
		                <input type="hidden" id="P_ID" name="P_ID" datasource="P_ID"/>
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
                            <th fieldname="DELIVERY_CYCLE" colwidth="75">供货周期</th>
                            <th fieldname="REMARKS" colwidth="145" refer="myRemark">备注</th>
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
                        <li><div class="button"><div class="buttonContent"><button type="button" id="btn-export-index">导出数据</button></div></div></li>
                        <li id="dia-download" ><div class="button"><div class="buttonContent"><button type="button" id="btn-download" >下载导入模板</button></div></div></li>
                        <li id="dia-import" ><div class="button"><div class="buttonContent"><button type="button" id="btn-import" >批量导入</button></div></div></li>
                        <li id="dia-contror" style="display: none"><div class="button"><div class="buttonContent"><button type="button" id="btn-report" >提&nbsp;&nbsp;报</button></div></div></li>
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
                if (!$('#dia-PURCHASE_ID').val()) {
                    alertMsg.warn('请先保存订单信息!');
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
			var type=$("#dia-PURCHASE_TYPE").attr("code");
			if (submitForm($f) == false) return false;
			var sCondition = {};
			sCondition = $("#dia-fm-orderEdit").combined(1) || {};
			if(diaAction == 1)
			{
				var addUrl = diaUrl + "/purchaseOrderInsert.ajax?type="+type;
				doNormalSubmit($f,addUrl,"btn-save",sCondition,diaInsertCallBack);
			}else
			{
				var updateUrl = diaUrl + "/purchaseOrderUpdate.ajax?type="+type;
				doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
			}
		});
        // 导出按钮绑定
        $("#btn-export-index").click(function(){
            var PURCHASE_ID = $("#dia-PURCHASE_ID").val();
       		var searchContractPartUrl = diaUrl + "/download.do?PURCHASE_ID="+PURCHASE_ID;
            var url = encodeURI(searchContractPartUrl);
            window.location.href = url;
            $("#exportFm").attr("action",url);
            $("#exportFm").submit();
        });
        //提交按钮响应
        var row;
        $("#btn-report").bind("click", function(event){
        	if($("#dia-tab-partinfo_content").find("tr").size()==0){
    			alertMsg.warn("请添加订单所需订购的配件信息.");
    			return false;
    		}else if($("#dia-tab-partinfo_content").find("tr").size() == 1)
    		{
    			if($("#dia-tab-partinfo_content").find("tr").eq(0).find("td").size() == 1)
    			{
    				alertMsg.warn("请添加订单所需订购的配件信息.");
        			return false;
    			}
    		}
    		var PURCHASE_ID = $("#dia-PURCHASE_ID").val();
    		var reportUrl = diaUrl + "/purchaseOrderReport.ajax?PURCHASE_ID="+PURCHASE_ID;
    		sendPost(reportUrl,"btn-report","",reportCallBack,"true"); 
    	});
    	//关闭按钮响应
    	$("button.close").click(function(){
    		$.pdialog.closeCurrent();
    		return false;
    	});
		//修改页面赋值
        if (diaAction != "1") {
        	$("#dia-contror").show();
            var selectedRows = $("#tab-order_info").getSelectedRows();
            setEditValue("dia-fm-orderEdit", selectedRows[0].attr("rowdata"));
            $("#diaOrderNo").html(selectedRows[0].attr("ORDER_NO"));
            $("#dia-SUPPLIER_CODE").attr("readonly",true);
            $("#dia-SUPPLIER_CODE").attr("hasBtn",false);
			$("#dia-PURCHASE_TYPE").attr("disabled",true);
        }else{
        	var userAcc = $("#PERSON_NAME").attr("code");
			 $("#dia-APPLY_USER").val(userAcc);
        }
    })
    function afterDicItemClick(id,$row){
		var ret = true;
		if(id == "dia-SUPPLIER_CODE")
		{
			$("#"+id).attr("SUPPLIER_ID",$row.attr("SUPPLIER_ID"));
			$("#"+id).attr("code",$row.attr("SUPPLIER_CODE"));
			$("#dia-SUPPLIER_NAME").val($("#"+id).val());
			$("#dia-SUPPLIER_CODE").val($("#"+id).attr("code"));
			$("#dia-SUPPLIER_ID").val($("#"+id).attr("SUPPLIER_ID"));
			
		}
		return ret;
	}
    function setDiaDefaultValue(){
    	
    }
	//新增方法回调
    function diaInsertCallBack(res)
    {
    	var rows = res.getElementsByTagName("ROW");
    	try
    	{
    		var purchaseId = getNodeText(rows[0].getElementsByTagName("PURCHASE_ID").item(0));
    		var orderNo = getNodeText(rows[0].getElementsByTagName("ORDER_NO").item(0));
    		var applyUser = getNodeText(rows[0].getElementsByTagName("APPLY_USER").item(0));
    		var applyTime = getNodeText(rows[0].getElementsByTagName("APPLY_DATE").item(0));
            $('#dia-PURCHASE_ID').val(purchaseId);
            $('#dia-APPLY_USER').val(applyUser);
            $('#dia-APPLY_DATE').val(applyTime);
            $('#dia-ORDER_NO').val(orderNo);
            $("#diaOrderNo").html(orderNo);
            $("#dia-SUPPLIER_CODE").attr("readonly",true);
            $("#dia-SUPPLIER_CODE").attr("hasBtn",false);
			$("#dia-PURCHASE_TYPE").attr("disabled",true);
    		$("#tab-order_info").insertResult(res,0);
    		$("#dia-PURCHASE_TYPE").attr("disabled",true);
    		$("#dia-contror").show();
    		diaAction = 2;
    		if($("#tab-order_info_content").size()>0){
    			$("td input[type=radio]",$("#tab-order_info_content").find("tr").eq(0)).attr("checked",true);	
    		}else{
    			$("td input[type=radio]",$("#tab-order_info").find("tr").eq(0)).attr("checked",true);
    		}
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
    	var PURCHASE_ID = $("#dia-PURCHASE_ID").val();
   		var searchContractPartUrl = diaUrl + "/orderPartSearch.ajax?PURCHASE_ID="+PURCHASE_ID;
   		var $f = $("#dia-fm-orderEdit");
   		var sCondition = {};
   	    sCondition = $f.combined() || {};
   	    doFormSubmit($f, searchContractPartUrl, "", 1, sCondition, "dia-tab-partinfo");
    } 
    function addParts() {
        var diaoptions = {max: false, width: 900, height: 500, mask: true, mixable: true, minable: true, resizable: false, drawable: true};
        $.pdialog.open(webApps + "/jsp/dms/oem/part/purchase/purchaseOrder/orderPartSel.jsp", "partSelWin", "配件信息查询", diaoptions);
    }
    //采购订单配件删除
    var $row;
    function doPartDelete(rowobj)
    {
    	$row = $(rowobj);
    	var url = diaUrl + "/partDelete.ajax?DETAIL_ID="+$(rowobj).attr("DETAIL_ID");
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
    var $row;
    function reportCallBack(){
/*     	var selectedRows = $("#tab-order_info").getSelectedRows();
		$row =  selectedRows[0];
		$("#tab-order_info").removeResult($row); */
		
/* 		$.pdialog.closeCurrent();
		doSearchOrder(); */
			/* alertMsg.confirm("是否需要调整要求完成时间？",{okCall:doApproveOk1,cancelCall:doApproveOk2}); */
			doApproveOk1();
    	
    }
	//下载模板
	$('#btn-download').bind('click', function () {
        var url = encodeURI("<%=request.getContextPath()%>/jsp/dms/common/download.jsp?filename=PchPartImp.xls");
        window.location.href = url;
    });
	$('#btn-import').bind('click', function () {
        if(!$('#dia-PURCHASE_ID').val()){
            alertMsg.warn('请先保存基本信息!');
            return;
        }
        //9:表示最大列，3：表示有效数据是第几行开始，第一行是1
        //最后一个参数表示 导入成功后显示页
        importXls("PT_BU_PCH_ORDER_PART_TMP",$('#dia-PURCHASE_ID').val(),4,3,"/jsp/dms/oem/part/purchase/purchaseOrder/importSuccess.jsp");
    });
	function doImportConfirm(a,b)
	{
	    var impUrl = diaSaveAction +'/importPart.ajax?PURCHASE_ID='+$('#dia-PURCHASE_ID').val();
	    sendPost(impUrl,"","",impPromPartCallback);
	}
	function impPromPartCallback(res){
	    try
	    {
	        searchPromPart();
	    }catch(e)
	    {
	        alertMsg.error(e.description);
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
		return "<input type='text' name='myCount' value='"+$(obj).html()+"'/>";
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
		if($obj.val() && !isCount($obj))
	    {
	        alertMsg.warn("请正确输入采购数量！");
	        return false;
	    }
	    $("#PCH_ID").val($("#dia-PURCHASE_ID").val());
	    $("#DTL_ID").val($(rowobj).attr("DETAIL_ID"));
	    $("#COUNT").val($obj.val());
	    $("#RMK").val($obj1.val());
	    $("#P_ID").val($(rowobj).attr("PART_ID"));
	    
	    var $f = $("#diaSaveFm");
	    if (submitForm($f) == false) return false;
	    var sCondition = {};
	    sCondition = $f.combined(1) || {};
	    sendPost(diaUrl + "/orderPartCountSave.ajax", "btnDiaSave", sCondition, savePartCountCallBack);
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
	function openSupplier()
	{
		var options = {width:750,height:400,max:false,mask:true,mixable:false,minable:false,resizable:true,drawable:true}; 
		var url = webApps +"/jsp/dms/oem/part/purchase/purchaseOrder/supplierSel.jsp?&TYPE=1&ACCOUNT="+$("#dia-APPLY_USER").val();
		$.pdialog.open(url, "supplierSel", "供应商选择", options);
	}
	function SelCallBack(obj)
	{
		$("#dia-SUPPLIER_CODE").val(obj.getAttribute("SUPPLIER_CODE"));//原订单编号
		$("#dia-SUPPLIER_ID").val(obj.getAttribute("SUPPLIER_ID"));//原订单ID
		$("#dia-SUPPLIER_NAME").val(obj.getAttribute("SUPPLIER_NAME"));//原订单编号
	}
	//需调整
	function doApproveOk1() {
		var changeoptions = {max:true,width:1024,height:400,max:false,mask:true,mixable:false,minable:false,resizable:true,drawable:true}; 
		var orderUrl = webApps +"/jsp/dms/oem/part/purchase/purchaseOrder/orderTimeChange.jsp?&PURCHASE_ID="+$("#dia-PURCHASE_ID").val()+"&id=orderpartSel&flag=1";
		$.pdialog.open(orderUrl, "orderpartSel", "采购拆分单", changeoptions);
    }

/*     // 不需调整
    function doApproveOk2() {
    	sendPost(diaUrl + "/updateSplitNoChange.ajax?PURCHASE_ID="+$("#dia-PURCHASE_ID").val(), "", "", noChangeCallBack);
    }
    function noChangeCallBack(res){
        try {
	        	$.pdialog.closeCurrent();
	    		doSearchOrder(); 
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    } */
    function doClear(){
/*     	$.pdialog.closeCurrent(); */
 		var editWin = $("body").data("orderpartSel");
		$.pdialog.close(editWin);
		var editWin = $("body").data("addContract");
 		$.pdialog.close(editWin);
		doSearchOrder();
    }
</script>