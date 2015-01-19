<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
         
<%@ page import="com.org.frameImpl.Constant" %>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.org.framework.util.Pub"%>
<%@ page import="com.org.framework.Globals" %>
<%@ page import="com.org.framework.common.User" %>
<%@ page import="com.org.dms.common.DicConstant" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
    String contentPath = request.getContextPath();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    String date = format.format(Pub.getCurrentDate());
    String action = request.getParameter("action");
    if (action == null)
        action = "1";
%>
<div id="dia-layout">
    <div class="tabs" eventType="click" id="dia-tabs">
        <div class="tabsHeader">
            <div class="tabsHeaderContent">
                <ul>
                    <li><a href="javascript:void(0);"><span>订单基本信息</span></a></li>
                    <li><a href="javascript:void(0);"><span>采购配件信息</span></a></li>
                </ul>
            </div>
        </div>
        <div class="tabsContent">
            <div style="height:auto;overflow:hidden;">
                <form method="post" id="dia-fm-orderEdit" class="editForm" style="width:99%;">
				<input type="hidden" id="dia-SPLIT_ID" name="dia-SPLIT_ID" datasource="SPLIT_ID" />
				<input type="hidden" id="dia-DELIVERY_CYCLE" name="dia-DELIVERY_CYCLE" datasource="DELIVERY_CYCLE" />
		  <div align="left">
			  <fieldset>
						<table class="editTable" id="dia-tab-order">
							<tr>
								<td><label>订单编号：</label></td>
							    <td><input type="text" id="dia-SPLIT_NO" name="dia-SPLIT_NO" datasource="SPLIT_NO" value="系统自动生成" readonly="readonly"/></td>
							    <td><label>所属月度：</label></td>
							    <td>
							    	<input type="text"  id="dia-SELECT_MONTH"  name="dia-SELECT_MONTH"   dataSource="SELECT_MONTH" style="width:75px;"   datatype="0,is_null,30" />
							    </td>
							    <td><label>采购类型：</label></td>
							    <td>
							    	<input type="text" id="dia-PURCHASE_TYPE" name="dia-PURCHASE_TYPE" datasource="PURCHASE_TYPE" />
								</td>
							</tr>
							<tr>
								<td><label>厂家代码：</label></td>
							    <td>
							    	<input type="text" id="dia-SUPPLIER_CODE" name="dia-SUPPLIER_CODE" datasource="SUPPLIER_CODE"  datatype="0,is_null,30"/>
							    	<input type="hidden" id="dia-SUPPLIER_ID" name="dia-SUPPLIER_ID" datasource="SUPPLIER_ID" />
							    </td>
								<td><label>厂家名称：</label></td>
							    <td>
							    	<input type="text" id="dia-SUPPLIER_NAME" name="dia-SUPPLIER_NAME" datasource="SUPPLIER_NAME" datatype="0,is_null,3000" readonly="true"/>
							    </td>
							    <td><label>完成时间：</label></td>
							    <td>
                                    <input type="text" id="dia-REPUIREMENT_TIME" name="dia-REPUIREMENT_TIME" datasource="REPUIREMENT_TIME" datatype="1,is_null,30" onclick="WdatePicker({minDate:'<%=date%>'})"/>
                                </td>
							</tr>
							<tr id = "CONFIRM_ADVISE" display="none">
							    <td><label>供应商确认意见：</label></td>
							    <td colspan="5">
								  <textarea id="dia-CONFIRM_ADVISE" style="width:450px;height:40px;" name="dia-CONFIRM_ADVISE" datasource="CONFIRM_ADVISE" datatype="1,is_null,1000" readonly="true"></textarea>
							    </td>
							</tr>
						</table>
			  </fieldset>
			</div>
			</form>
                <div class="formBar">
                    <ul>
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
		            <form method="post" id="dia-fm-confirmEdit" class="editForm" style="width:99%;">
						<input type="hidden" id="N_REPUIREMENT_TIME" name="N_REPUIREMENT_TIME" datasource="REPUIREMENT_TIME"/>
                 	</form>
                    <div id="dia-part" style="">
                    <table style="display:none;width:100%;" id="dia-tab-partinfo"  name="tablist"ref="dia-part" refQuery="tab-searchPart">
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
                        <li><div class="button"><div class="buttonContent"><button type="button" name="btn-pre">上&nbsp;一&nbsp;步</button></div></div></li>
                        <li id="dia-contror" ><div class="button"><div class="buttonContent"><button type="button" id="btn-edit" >调整完成</button></div></div></li>
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
/* 取父页面传过的参数确定新增或者修改 */
    var diaAction = "<%=action%>";
    var diaUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseOrder/PurchaseOrderModifyAction";
    
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
                    if (!$('#dia-SPLIT_ID').val()) {
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
    	var selectedRows = $("#tab-order_info").getSelectedRows();
        setEditValue("dia-fm-orderEdit", selectedRows[0].attr("rowdata"));
        var status = selectedRows[0].attr("IF_ON_TIME");
        if(status==<%=DicConstant.SF_02%>){
        	$("#CONFIRM_ADVISE").show();
        	
        }
        $('input').attr("readonly","readonly");
        $("#btn-edit").bind("click", function(event){
        	var time = $("#dia-REPUIREMENT_TIME").val();
        	if(!time){
        		alertMsg.warn("请填写完成时间.");
                return false;
        	}
        	$("#N_REPUIREMENT_TIME").val(time);
        	var SPLIT_ID = $("#dia-SPLIT_ID").val();
        	var $f = $("#dia-fm-confirmEdit");
    		if (submitForm($f) == false) return false;
    		var sCondition = {};
    		sCondition = $("#dia-fm-confirmEdit").combined(1) || {};
    		var url = diaUrl+"/orderModify.ajax?&SPLIT_ID="+SPLIT_ID;
    		doNormalSubmit($f,url,"btn-comfirm",sCondition,comfirmCallBack);
		});
        $("button.close").click(function(){
    		$.pdialog.closeCurrent();
    		return false;
    	});
    });
	//修改方法回调
	function comfirmCallBack(res)
	{
		try
		{
			var result = getNodeText(res.getElementsByTagName("RESULT").item(0));
			if(result=='1'){
				var $row =  parent.$("#tab-order_info").getSelectedRows();
				if($row[0]){
					parent.$("#tab-order_info").removeResult($row[0]);
					parent.$.pdialog.close("orderModify");
				}
			}
		}catch(e)
		{
			alertMsg.error(e);
			return false;
		}
		return true;
	}
     //查询采购订单配件
    function searchPart(){
    	var SPLIT_ID = $("#dia-SPLIT_ID").val();
   		var searchSplitPart = diaUrl + "/orderPartSearch.ajax?SPLIT_ID="+SPLIT_ID;
   		var $f = $("#dia-fm-orderEdit");
   		var sCondition = {};
   	    sCondition = $f.combined() || {};
   	    doFormSubmit($f, searchSplitPart, "", 1, sCondition, "dia-tab-partinfo");
    } 
    function addParts() {
        var options = {max: false, width: 900, height: 500, mask: true, mixable: true, minable: true, resizable: false, drawable: true};
        $.pdialog.open(webApps + "/jsp/dms/oem/part/purchase/purchaseOrder/orderModifyPartSel.jsp", "partSelWin", "配件信息查询", options);
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
    		searchPart();
    	}catch(e)
    	{
    		alertMsg.error(e);
    		return false;
    	}
    	return true;
    }
    function amountFormatStr(obj){
		return "<div style='text-align:right;'>"+amountFormatNew($(obj).html())+"</div>";
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
		$("td input[type=radio]",$(rowobj)).attr("checked",true);
		$rowpart = $(rowobj);
		var $obj = $(rowobj).find("td").eq(7).find("input:first");
		var $obj1 = $(rowobj).find("td").eq(10).find("input:first");
		if($obj.val() && !isCount($obj))
	    {
	        alertMsg.warn("请正确输入采购数量！");
	        return false;
	    }
		$("#PCH_ID").val($("#dia-SPLIT_ID").val());
	    $("#DTL_ID").val($(rowobj).attr("DETAIL_ID"));
	    $("#COUNT").val($obj.val());
	    $("#RMK").val($obj1.val());
	    $("#P_ID").val($(rowobj).attr("PART_ID"));
	    var $f = $("#diaSaveFm");
	    if (submitForm($f) == false) return false;
	    var sCondition = {};
	    sCondition = $f.combined(1) || {};
	    sendPost(diaUrl + "/orderPartCountSave.ajax", "btnDiaSave", sCondition, savePartCountCallBack);
 	    /* var partCountSaveUrl = diaUrl + "/orderPartCountSave.ajax?SPLIT_ID="+$("#dia-SPLIT_ID").val()+"&DETAIL_ID=" + $(rowobj).attr("DETAIL_ID")+"&COUNT="+$obj.val()+"&REMARKS="+$obj1.val()+"&PART_ID="+ $(rowobj).attr("PART_ID");
	    sendPost(partCountSaveUrl, "", "", savePartCountCallBack, "true");  */
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
</script>