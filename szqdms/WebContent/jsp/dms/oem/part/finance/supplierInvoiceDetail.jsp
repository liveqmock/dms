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
<div id="dia-layout">
	<div class="page">
	<div class="pageContent" style="" >
	<form method="post" id="fm-order_Info" class="editForm" >
		<div align="left">
			<fieldset>
			<table class="editTable" id="tab-cash_Edit_Info">
			<input type="hidden" id="dia-SUPPLIER_ID" name="dia-SUPPLIER_ID" datasource="SUPPLIER_ID" />
			</table>
			</fieldset>
		</div>
	</form>
	<div class="pageContent">
		<div id="page_order" >
			<table style="display:none;width:100%;" id="tab-order_info" name="tablist" ref="page_order" refQuery="tab-cash_Edit_Info" >
					<thead>
						<tr>
							<th type="single" name="XH"  style="display:none;"></th>
							<th fieldname="ORDER_NO" refer="showLink">单号</th>
							<th fieldname="TYPE" >类别</th>
							<th fieldname="APPLY_USER" >制单人</th>
							<th fieldname="CLOSE_DATE" >关单日期</th>
							<th fieldname="COUNT" >数量汇总</th>
							<th fieldname="AMOUNT" align="right" refer="amountFormatStr">金额汇总</th>
						</tr>
					</thead>
					<tbody>
                    </tbody>
			</table>
		</div>
	</div>
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
		</ul>
	</div>
	</div>
	</div>
</div>

<script type="text/javascript">
/* 取父页面传过的参数确定新增或者修改 */
    var SUM_ID = "<%=SUM_ID%>"
    var diaUrl = "<%=request.getContextPath()%>/part/financeMng/invoiceNotice/InvoiceNoticeMngAction";
    $(function () {
    	//关闭按钮响应
    	$("button.close").click(function(){
    		$.pdialog.closeCurrent();
    		return false;
    	});
		//修改页面赋值
/*             var selectedRows = $("#tab-invoice_info").getSelectedRows();
            var SUPPLIER_ID = selectedRows[0].attr("SUPPLIER_ID");
            $("#dia-SUPPLIER_ID").val(SUPPLIER_ID); */
            var searchUrl = mngUrl+"/invioceOrderSearch.ajax?SUM_ID="+SUM_ID;
    		var $f = $("#fm-order_Info");
    		var sCondition = {};
        	sCondition = $f.combined() || {};
    		doFormSubmit($f,searchUrl,"",1,sCondition,"tab-order_info");
		
    })
function showLink(obj)
{
	var $row=$(obj).parent();
	var type = $row.attr("TYPE");
	if(type=="采购"){
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
</script>