<?xml version="1.0" encoding="UTF-8" ?>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%@page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contentPath = request.getContextPath();
	String SUM_ID = request.getParameter("SUM_ID");
%>
<div>
    <div class="page">
     <div class="pageHeader" >
		<form method="post" id="fm-searchPchOrder-details">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-orderSearch-details">
					<tr>
						<td><label>单号：</label></td>
						<td>
							<input type="text" id="ORDER_NO" name="ORDER_NO" datatype="1,is_null,600" operation="like" dataSource="T.ORDER_NO"  />
						</td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search-details" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-addInvoice">开票完成</button></div></div></li>
		              	<li><div class="button"><div class="buttonContent"><button type="button" id="btn-finish">确&nbsp;&nbsp;定</button></div></div></li> 
		              	<li><div class="button"><div class="buttonContent"><button type="button" id="btn-closeWin">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
		</div>
        <div class="pageHeader">
            <form method="post" id="fm-searchUnChkPart">
                <div class="searchBar" align="left">
                    <table class="editTable" id="tab-cash_Edit_Info">
						<tr>
						    <td><label>开票总金额：</label></td>
						    <td><input type="text" id="dia-INVOICE_AMOUNT" name="dia-INVOICE_AMOUNT" datasource="INVOICE_AMOUNT" datatype="1,is_money,30" readonly="readonly"/>
						    </td>
						    <td><label>开票金额：</label></td>
						    <td><input type="text" id="dia-HAS_INVOICE_AMOUNT" name="dia-HAS_INVOICE_AMOUNT" datasource="HAS_INVOICE_AMOUNT" datatype="1,is_money,30" readonly="readonly"/>
						    </td>
						    <td><label>未开票金额：</label></td>
						    <td><input type="text" id="dia-UN_INVOICE_AMOUNT" name="dia-UN_INVOICE_AMOUNT" datasource="UN_INVOICE_AMOUNT" datatype="1,is_money,30" readonly="readonly"/>
						    </td>
						</tr>
						<tr>
							<td><label>发票号：</label></td>
						    <!-- <td colspan="3" >
                                <input type="text" style="width:88%;" id="NO"  name=NO datasource="NO" datatype="1,is_null,3000"/>
                            </td>  -->
                            <td colspan="4">
							  <textarea id="NO" style="width:450px;height:40px;" name="NO" datasource="NO" datatype="0,is_null,1000"></textarea>
						    </td>
						</tr>
						<tr>
						    <td><label>开票备注：</label></td>
						    <td colspan="4">
							  <textarea id="dia-REMARKS" style="width:450px;height:40px;" name="dia-REMARKS" datasource="REMARKS" datatype="1,is_null,1000"></textarea>
						    </td>
						</tr>
					</table>
                </div>
            </form>
        </div>
        <div class="pageContent" id="orderList">
            <div id="div-partList">
                <table style="display:none;width:100%;" layoutH="200px" id="tab-order_info" multivals="div-selectedOrder" name="tablist" ref="div-partList" refQuery="tab-searchPart" pagerows="2000">
                    <thead>
                        <tr>
							<th type="multi" name="XH" unique="ORDER_ID" style=""></th>
							<th fieldname="ORDER_NO" refer="showLink">单号</th>
							<th fieldname="ORDER_TYPE" refer="defaultCheck">类别</th>
							<th fieldname="COUNT" >数量汇总</th>
							<th fieldname="AMOUNT" align="right" refer="amountFormatStr">金额汇总</th>
							<th fieldname="INVOICE_STATUS" >开票状态</th>
						</tr>
                    </thead>
                </table>
            </div>
            <fieldset id="fie-selectedOrder">
                <div id="div-selectedOrder">
                    <table style="width:100%;display:none;">
                    	<legend align="left" >&nbsp;[已选定订单]</legend>
                        <tr>
                            <td>
                                <textarea style="width:80%;height:26px;display:none" id="val0" column="1" name="multivals" readOnly></textarea>
                                <textarea style="width:99%;height:50px;" id="val1" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;height:26px;display:none" id="val2" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;height:26px;display:none" id="val3" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;height:26px;display:none" id="val4" name="multivals" readOnly></textarea>
                            </td>
                        </tr>
                    </table>
                    <table>
                    </table>
                    <table id = "ADUIT_REMARKS" style="display:none">
                     	<legend align="left" >&nbsp;[审核意见]</legend>
                    	<tr>
						    <td colspan="5">
							  <textarea id="dia-ADUIT_REMARKS" style="width:450px;height:40px;" name="dia-ADUIT_REMARKS" datasource="ADUIT_REMARKS" readonly="true"></textarea>
						    </td>
						</tr>
                    </table>
                </div>
            </fieldset>
            <form id="fm-orderinfo">
                <input type="hidden" id="orderids" name="orderids" datasource="ORDERIDS"/>
                <input type="hidden" id="ordernos" name="ordernos" datasource="ORDERNOS"/>
                <input type="hidden" id="types" name="types" datasource="TYPES"/>
                <input type="hidden" id="invoiceAmounts" name="invoiceAmounts" datasource="INVOICEAMOUNTS"/>
                <input type="hidden" id="invoicenos" name="invoicenos" datasource="INVOICENOS"/>
                <input type="hidden" id="HASINVOICEAMOUNTS" name="HASINVOICEAMOUNTS" datasource="HASINVOICEAMOUNTS"/>
                <input type="hidden" id="UNINVOICEAMOUNTS" name="UNINVOICEAMOUNTS" datasource="UNINVOICEAMOUNTS"/>
                <input type="hidden" id="rmk" name="rmk" datasource="REMARK"/>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    //弹出窗体
    var id = "<%=SUM_ID%>"
    var diaUrl = "<%=request.getContextPath()%>/part/financeMng/invoiceNotice/InvoiceAddMngAction";
    var selWin = $("body").data("orderSelWin");
    $(function () {
    	$("#orderList").height(document.documentElement.clientHeight-90);
        $("#btn-closeWin").click(function () {
            $.pdialog.close(selWin);
            return false;
        });
        $("#btn-search-details").click(function () {
        	doOrderSearch();
        	});
        //确定按钮响应
        $('#btn-addInvoice').bind('click',function(){
            //添加促销配件URL
            var addInvoice = diaUrl+"/insertInvoice.ajax?SUM_ID="+id;
            var orderIds = $('#val0').val();
            var NO = $('#NO').val();
            var remark = $('#dia-REMARKS').val();
            var amout = $("#dia-INVOICE_AMOUNT").val();
            var hasAmount = $("#dia-HAS_INVOICE_AMOUNT").val();
            var unAmount = $("#dia-UN_INVOICE_AMOUNT").val();
            if(!orderIds){
                alertMsg.warn('请选择订单!');
                return false;
            }else if(!NO){
            	alertMsg.warn('请输入发票号!');
            	return false;
            }else if(!amout){
            	alertMsg.warn('请输入开票金额!');
            	return false;
            }else if(parseFloat(hasAmount)<0){
            	alertMsg.warn('开票金额不能小于0!');
            	return false;
            }else{
                $('#orderids').val(orderIds);
                $('#ordernos').val($('#val1').val());
                $('#types').val($('#val2').val());
                $('#invoicenos').val(NO);
                $('#invoiceAmounts').val(amout);
                $('#UNINVOICEAMOUNTS').val(unAmount);
                $('#HASINVOICEAMOUNTS').val(hasAmount);
                $('#rmk').val(remark);
                var $f = $("#fm-orderinfo");
                if (submitForm($f) == false) return false;
                var sCondition = {};
                sCondition = $f.combined(1) || {};
                doNormalSubmit($f, addInvoice, "btn-addInvoice", sCondition, insertInvoiceCallBack); 

            }
        });
        //确定按钮响应
        $('#btn-finish').bind('click',function(){
        	//添加促销配件URL
            var addInvoice = diaUrl+"/addInvoice.ajax?SUM_ID="+id;
            var orderIds = $('#val0').val();
            var NO = $('#NO').val();
            var remark = $('#dia-REMARKS').val();
            var amout = $("#dia-INVOICE_AMOUNT").val();
            var hasAmount = $("#dia-HAS_INVOICE_AMOUNT").val();
            var unAmount = $("#dia-UN_INVOICE_AMOUNT").val();
            if(!orderIds){
                alertMsg.warn('请选择订单!');
                return false;
            }else if(!NO){
            	alertMsg.warn('请输入发票号!');
            	return false;
            }else if(!amout){
            	alertMsg.warn('请输入开票金额!');
            	return false;
            }else if(parseFloat(hasAmount)<0){
            	alertMsg.warn('开票金额不能小于0!');
            	return false;
            }else{
                $('#orderids').val(orderIds);
                $('#ordernos').val($('#val1').val());
                $('#types').val($('#val2').val());
                $('#invoicenos').val(NO);
                $('#invoiceAmounts').val(amout);
                $('#UNINVOICEAMOUNTS').val(unAmount);
                $('#HASINVOICEAMOUNTS').val(hasAmount);
                $('#rmk').val(remark);
                var $f = $("#fm-orderinfo");
                if (submitForm($f) == false) return false;
                var sCondition = {};
                sCondition = $f.combined(1) || {};
                doNormalSubmit($f, addInvoice, "btn-finish", sCondition, insertInvoiceCallBack); 
            }
        });
        doOrderSearch();
        
        var accountSearchUrl = diaUrl + "/getInvoiceNo.ajax?&SUM_ID="+id;
        sendPost(accountSearchUrl, "", "", accountSearchCallback, "false");
        
        
		var selectedRows = $("#tab-invoice_info").getSelectedRows();
		var status = selectedRows[0].attr("INVOICE_STATUS");
		var inAmount = selectedRows[0].attr("IN_AMOUNT");
		var outAmount = selectedRows[0].attr("RETURN_AMOUNT");
		var hadAmount = selectedRows[0].attr("HAS_INVOICE_AMOUNT");
		var unAmount = selectedRows[0].attr("UN_INVOICE_AMOUNT")
		var amount = selectedRows[0].attr("INVOICE_AMOUNT");
		var remark = selectedRows[0].attr("REMARKS");
		var no = selectedRows[0].attr("INVOICE_NO");
		/* $("#NO").val(no); */
		$("#dia-REMARKS").val(remark);
		$("#dia-INVOICE_AMOUNT").val(amount);
		if(parseFloat(hadAmount)==0){
			$("#dia-HAS_INVOICE_AMOUNT").val(0);
		}else{
			$("#dia-HAS_INVOICE_AMOUNT").val(hadAmount);
		}
		$("#dia-UN_INVOICE_AMOUNT").val(unAmount);
		
		if(status==<%=DicConstant.GYSKPZT_05%>){
			var remarks = selectedRows[0].attr("ADUIT_REMARKS");
			$("#ADUIT_REMARKS").show();
			$("#dia-ADUIT_REMARKS").val(remarks);
		}
		
    });
    function accountSearchCallback(res){
        try {
            var rows = res.getElementsByTagName("ROW");
            if(rows && rows.length > 0)
            {
                for(var i=0;i<rows.length;i++){
                	var no = getNodeText(rows[i].getElementsByTagName("I_NO").item(0));
                	if(no){
                		$("#NO").val(no);
                	}else{
                		$("#NO").val("");
                	}
                }
            }
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }
    function doOrderSearch(){
    	var searchUrl = mngUrl+"/invioceOrderSearch.ajax?SUM_ID="+id;
		var $f = $("#tab-orderSearch-details");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"",1,sCondition,"tab-order_info");
    }
    function insertInvoiceCallBack(){
    	doInvSearch();
		$.pdialog.closeCurrent();
    }
    var $row;
    function reportCallBack(){
    	doInvSearch();
		$.pdialog.closeCurrent();
    }
    //将本次促销价字段渲染为文本框
    function createInputBox(obj)
    {
    	var v = obj.text();
    	if(v){
    		 return '<input type="text" name="INVOICE_NO" value="'+obj.text()+'" onblur="doMyInputBlur(this)"/>';
    	}else{
    		 return '<input type="text" name="INVOICE_NO" onblur="doMyInputBlur(this)"/ >';
    	}
    }
    
    function createInput(obj)
    {
    	var v = obj.text();
    	if(v){
    		 return '<input type="text" name="REMARKS" value="'+obj.text()+'" onblur="doMyInputCheck(this)"/>';
    	}else{
    		return '<input type="text" name="REMARKS" onblur="doMyInputCheck(this)"/ >';
    	}
        
    }
    function doMyInputBlur(obj){
        var $obj = $(obj);
        if($obj.val() == "")
            return ;
        var $tr = $obj.parent().parent().parent();
        var checkObj = $("input:first",$tr.find("td").eq(1));
        var s = $obj.val();
        var count = $tr.find("td").eq(7).text();
        checkObj.attr("checked",true);
        $tr.find("td").eq(7).html("<div>"+s+"</div>");
        doSelectedBefore($tr,checkObj,1);
    }
    function doSelectedBefore($tr,$obj,type)
    {
        doCheckbox($obj.get(0));
    }
    function doMyInputCheck(obj){
    	var $obj = $(obj);
    	var $tr = $obj.parent().parent().parent();
    	var checkObj = $("input:first",$tr.find("td").eq(1));
    	var r = $obj.val();
//    	if(checkObj.is(":checked")){
//    		doCheckbox($obj.get(0));
//    	}
    	doSelectedBefore2($tr,checkObj,1); 
    }
    function doSelectedBefore2($tr,$obj,type)
    {
        doCheckbox($obj.get(0));
    }
    function doCheckbox(checkbox) {
    	var $obj = $(checkbox);
        var $tr = $obj;
        while($tr.get(0).tagName != "TR")
        	$tr = $tr.parent();
        
        var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
        var amount = $tr.attr("AMOUNT");
        var iAmount = $("#dia-INVOICE_AMOUNT").val();
        var totleAmount = $("#dia-HAS_INVOICE_AMOUNT").val();
        var unAmount = $("#dia-UN_INVOICE_AMOUNT").val();
        var status = $tr.attr("I_STATUS");
        if($checkbox.is(":checked")){
       		$("#dia-HAS_INVOICE_AMOUNT").val((parseFloat(totleAmount)+parseFloat(amount)).toFixed(2));
           	$("#dia-UN_INVOICE_AMOUNT").val((parseFloat(unAmount)-parseFloat(amount)).toFixed(2));
        	
        }else{
       		$("#dia-HAS_INVOICE_AMOUNT").val((parseFloat(totleAmount)-parseFloat(amount)).toFixed(2));
           	$("#dia-UN_INVOICE_AMOUNT").val((parseFloat(unAmount)+parseFloat(amount)).toFixed(2));
       		
        }
        var arr = [];
        arr.push($tr.attr("ORDER_ID"));
        arr.push($tr.attr("ORDER_NO"));
        arr.push($tr.attr("TYPE"));
        
        multiSelected($checkbox, arr,$('#div-selectedOrder'));
    }
    function customOtherValue($row,checkVal)
    {
        var $inputObj = $row.find("td").eq(7);
        var $inputObj1 = $row.find("td").eq(8);
        var val;
        var val1;
        if($("#val3") && $("#val3").val())
        {
            var nos = $("#val3").val();
            var pks = $("#val0").val();
            var no = nos.split(",");
            var pk = pks.split(",");
            for(var i=0;i<pk.length;i++)
            {
                if(pk[i] == checkVal)
                {
                    val = no[i];
                    break;
                }
            }
        }
        if(val)
        {
            $inputObj.html("<div>"+val+"<div/ >");
        }
        if($("#val4") && $("#val4").val())
        {
            var remarks = $("#val4").val();
            var pks = $("#val0").val();
            var remark = remarks.split(",");
            var pk = pks.split(",");
            for(var i=0;i<pk.length;i++)
            {
                if(pk[i] == checkVal)
                {
                    val1 = remark[i];
                    break;
                }
            }
        }
        if(val1)
        {
            $inputObj1.html("<div>"+val1+"<div/>");
        }
    }
    function showLink(obj)
    {
    	var $row=$(obj).parent();
    	var type = $row.attr("TYPE");
    	if(type=="1"){
    		return "<a href='#' onclick=openDetail1("+$row.attr("ORDER_ID")+") class='op'>"+$row.attr("ORDER_NO")+"</a>";
    	}else{
    		return "<a href='#' onclick=openDetail2("+$row.attr("ORDER_ID")+") class='op'>"+$row.attr("ORDER_NO")+"</a>";
    	}
        
    }
    function openDetail1(SPLIT_ID){
    	var options = {max:true,width:1024,height:600,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
    	$.pdialog.open(webApps+"/jsp/dms/common/purchaseOrderInfoDetail.jsp?SPLIT_ID="+SPLIT_ID, "pchOrderDetail", "采购拆分单明细", options,true);
    }
    function openDetail2(RETURN_ID){
    	var options = {max:true,width:1024,height:600,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
    	$.pdialog.open(webApps+"/jsp/dms/oem/part/purchase/purchaseOrder/pchReturnOrderForDetails.jsp?id=" +RETURN_ID, "pchOrderDetail", "采购退货单明细", options);
    }
    function defaultCheck(obj){
    	var $obj = $(obj);
        var $tr = $(obj).parent();
    	var checkObj = $("input:first",$tr.find("td").eq(1));
    	var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
    	var amount = $tr.attr("AMOUNT");
		var hasAmount = $("#dia-HAS_INVOICE_AMOUNT").val();
		var unAmount = $("#dia-UN_INVOICE_AMOUNT").val();
		var tAmount = $("#dia-INVOICE_AMOUNT").val();
		var type = $tr.attr("TYPE");
		var status = $tr.attr("INVOICE_STATUS");
		var i_status = $tr.attr("I_STATUS");
    	if(type=="2"){
    		if(i_status!=<%=DicConstant.GYSKPZT_02%>&&i_status!=<%=DicConstant.GYSKPZT_05%>){
    			$checkbox.attr("checked",false);
    		}else{
	   			$checkbox.attr("checked",true);
	   			doCheckbox($obj);
    		}
    		$checkbox.attr("disabled",true);
   			$("#dia-HAS_INVOICE_AMOUNT").val((parseFloat(hasAmount)-parseFloat(amount)).toFixed(2));
   			$("#dia-UN_INVOICE_AMOUNT").val((parseFloat(tAmount)-(parseFloat(hasAmount)-parseFloat(amount))).toFixed(2));
    	}
    	if(i_status!=<%=DicConstant.GYSKPZT_02%>&&i_status!=<%=DicConstant.GYSKPZT_05%>){
    		$checkbox.attr("checked",false);
    		$checkbox.attr("disabled",true);
    	}
    	if(i_status==<%=DicConstant.GYSKPZT_05%>){
    		if(type=="2"){
       			$checkbox.attr("checked",true);
        		$checkbox.attr("disabled",true);
        		doCheckbox($obj);
        	}else{
        		$checkbox.attr("checked",true);
        		$checkbox.attr("disabled",false);
        		doCheckbox($obj);
        	}
    		$("#dia-HAS_INVOICE_AMOUNT").val(hasAmount);
    		$("#dia-UN_INVOICE_AMOUNT").val(unAmount);
    	}
    }
</script>