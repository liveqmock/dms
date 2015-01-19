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
        <div class="pageHeader">
            <form method="post" id="fm-searchUnChkPart">
                <div class="searchBar" align="left">
                    <table class="editTable" id="tab-cash_Edit_Info">
						<tr>
						    <td><label>账户类型：</label></td>
					    	<td><input type="text" id="dia-AMOUNT_TYPE" name="dia-AMOUNT_TYPE" kind="dic" src="ZJZHLX" datasource="AMOUNT_TYPE" filtercode="<%=DicConstant.ZJZHLX_01%>|<%=DicConstant.ZJZHLX_02%>|<%=DicConstant.ZJZHLX_03%>" datatype="1,is_null,30" operation="="/>
						    </td>
						    <td><label>结算金额：</label></td>
						    <td><input type="text" id="amount" name="amount" datasource="AMOUNT"  datatype="1,is_money,10"/>
						    </td>
						</tr>
						<tr>
						    <td><label>开票备注：</label></td>
						    <td colspan="4"><div id="remark"></div></td>
						</tr>
					</table>
					<div class="subBar">
				          <ul>
				              <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-finish">确&nbsp;&nbsp;定</button></div></div></li>
				              <li><div class="button"><div class="buttonContent"><button type="button" id="btn-closeWin">关&nbsp;&nbsp;闭</button></div></div></li>
				          </ul>
				      </div>
                </div>
            </form>
        </div>
        <div class="pageContent">
            <div id="div-partList">
                <table style="display:none;width:100%;" id="tab-order_info" multivals="div-selectedOrder" name="tablist" ref="div-partList"
                       refQuery="tab-searchPart">
                    <thead>
                        <tr>
							<th type="multi" name="XH" unique="ORDER_ID" style=""></th>
							<th fieldname="ORDER_NO" refer="showLink">单号</th>
							<th fieldname="ORDER_TYPE" >类别</th>
							<th fieldname="COUNT" >数量汇总</th>
							<th fieldname="AMOUNT" align="right" refer="amountFormatStr">金额汇总</th>
						</tr>
                    </thead>
                </table>
            </div>
            <fieldset id="fie-selectedOrder">
                <div id="div-selectedOrder">
                    <table style="width:100%;">
                    	<legend align="left" >&nbsp;[已选定订单]</legend>
                        <tr>
                            <td>
                                <textarea style="width:80%;height:26px;display:none" id="O_IDS" column="1" name="multivals" readOnly></textarea>
                                <textarea style="width:99%;height:50px;" id="val1" name="multivals" readOnly></textarea>
                            </td>
                        </tr>
                    </table>
                    <table>
                    </table>
                </div>
            </fieldset>
            <form id="fm-orderinfo">
                <input type="hidden" id="orderids" name="orderids" datasource="ORDERIDS"/>
                <input type="hidden" id="ordernos" name="ordernos" datasource="ORDERNOS"/>
                <input type="hidden" id="types" name="types" datasource="TYPES"/>
                <input type="hidden" id="invoiceAmounts" name="invoiceAmounts" datasource="INVOICEAMOUNTS"/>
                <input type="hidden" id="invoicenos" name="invoicenos" datasource="INVOICENOS"/>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    //弹出窗体
    var id = "<%=SUM_ID%>"
    var diaUrl = "<%=request.getContextPath()%>/part/financeMng/settlement/SupplierSettlementAction";
    var selWin = $("body").data("orderSelWin");
    $(function () {
    	$("#btn-closeWin").click(function () {
            $.pdialog.close(selWin);
            return false;
        });
    	
    	$('#btn-finish').bind('click',function(){
            //添加促销配件URL
            var NO = $('#amount').val();
            var orderIds = $('#O_IDS').val();
            var type = $('#dia-AMOUNT_TYPE').attr("code");
            if(!NO){
                alertMsg.warn('请输入结算金额!');
            }else if(!orderIds){
            	alertMsg.warn('请选择订单!');
            }else{
            	$('#orderids').val(orderIds);
            	var settleUrl = diaUrl+"/finishSettle.ajax?SUM_ID="+id+"&AMOUNT="+NO+"&ACCOUNT_TYPE="+type;
                var $f = $("#fm-orderinfo");
                if (submitForm($f) == false) return false;
                var sCondition = {};
                sCondition = $f.combined(1) || {};
                doNormalSubmit($f, settleUrl, "btn-addInvoice", sCondition, insertInvoiceCallBack); 

            }
    	});
    	
    	 var selectedRows = $("#tab-invoice_info").getSelectedRows();
         $("#remark").html(selectedRows[0].attr("REMARKS")); 
		//修改页面赋值
            var searchUrl = mngUrl+"/settleOrderSearch.ajax?SUM_ID="+id;
    		var $f = $("#fm-order_Info");
    		var sCondition = {};
        	sCondition = $f.combined() || {};
    		doFormSubmit($f,searchUrl,"",1,sCondition,"tab-order_info");
    });

    function doOrderSearch(){
    	var searchUrl = mngUrl+"/invioceOrderSearch.ajax?SUM_ID="+id;
		var $f = $("#fm-order_Info");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"",1,sCondition,"tab-order_info");
    }
    function insertInvoiceCallBack(){
		doSearch();
        $.pdialog.closeCurrent();
    }
    function doCheckbox(checkbox) {
    	
        var $tr = $(checkbox).parent().parent().parent();
        var arr = [];
        arr.push($tr.attr("ORDER_ID"));
        arr.push($tr.attr("ORDER_NO"));
        var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
        multiSelected($checkbox, arr,$('#div-selectedOrder'));
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
</script>