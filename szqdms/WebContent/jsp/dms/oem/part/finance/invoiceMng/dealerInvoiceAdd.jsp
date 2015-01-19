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
						    <td><label>渠道名称：</label></td>
						    <td><input type="text" id="dia-ORG_NAME" name="dia-ORG_NAME" datasource="ORG_NAME" readonly="readonly" datatype="1,is_null,300000"/>
						    </td>
						    <td><label>渠道代码：</label></td>
						    <td><input type="text" id="dia-ORG_CODE" name="dia-ORG_CODE" datasource="ORG_CODE" readonly="readonly" datatype="1,is_null,300000"/>
						    </td>
						</tr>
						<tr>
						    <td><label>开票总金额：</label></td>
						    <td><input type="text" id="dia-INVOICE_AMOUNT" name="dia-INVOICE_AMOUNT" datasource="INVOICE_AMOUNT" datatype="1,is_null,300000" readonly="readonly"/>
						    </td>
						</tr>
						<tr>
							<td><label>发票号：</label></td>
						    <td colspan="3"><input type="text" id="NO"  name=NO datasource="NO" datatype="0,is_null,300000"/></td> 
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
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="ORDER_NO" refer="showLink">单号</th>
							<th fieldname="ORDER_TYPE" refer="defaultCheck">类别</th>
							<th fieldname="COUNT" >数量汇总</th>
							<th fieldname="AMOUNT" align="right" refer="amountFormatStr">金额汇总</th>
							<th fieldname="INVOICE_STATUS" >开票状态</th>
						</tr>
                    </thead>
                </table>
            </div>
            <div class="subBar" >
		          <ul>
		              <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-finish">确&nbsp;&nbsp;定</button></div></div></li>
		              <li><div class="button"><div class="buttonContent"><button type="button" id="btn-closeWin">关&nbsp;&nbsp;闭</button></div></div></li>
		          </ul>
		    </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    //弹出窗体
    var id = "<%=SUM_ID%>"
    var diaUrl = "<%=request.getContextPath()%>/part/financeMng/invoiceMng/DealerInvoiceMngAction";
    $(function () {
    	$("#orderList").height(document.documentElement.clientHeight-90);
        $("#btn-closeWin").click(function () {
        	$.pdialog.closeCurrent();
            return false;
        });
        $('#btn-finish').bind('click',function(){
            //添加促销配件URL
            var finishInvoice = diaUrl+"/finishInvoice.ajax?SUM_ID="+id;
               var $f = $("#tab-cash_Edit_Info");
               if (submitForm($f) == false) return false;
               var sCondition = {};
               sCondition = $f.combined(1) || {};
               doNormalSubmit($f, finishInvoice, "btn-finish", sCondition, reportCallBack); 

        });
        doOrderSearch();
		var selectedRows = $("#tab-invoice_info").getSelectedRows();
		var name = selectedRows[0].attr("ORG_NAME");
		var code = selectedRows[0].attr("ORG_CODE")
		var amount = selectedRows[0].attr("INVOICE_AMOUNT");;
		$("#dia-INVOICE_AMOUNT").val(amount);
		$("#dia-ORG_NAME").val(name);
		$("#dia-ORG_CODE").val(code);
    });

    function doOrderSearch(){
    	var searchUrl = mngUrl+"/invioceOrderSearch.ajax?SUM_ID="+id;
		var $f = $("#fm-order_Info");
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
    	doSearch();
		$.pdialog.closeCurrent();
    }
    function showLink(obj)
    {
    	var $row=$(obj).parent();
    	var type = $row.attr("TYPE");
    	/* if(type=="1"){ */
    		return "<a href='#' onclick=openDetail1("+$row.attr("ORDER_ID")+") class='op'>"+$row.attr("ORDER_NO")+"</a>";
    	/* }else{
    		return "<a href='#' onclick=openDetail2("+$row.attr("ORDER_ID")+") class='op'>"+$row.attr("ORDER_NO")+"</a>";
    	} */
        
    }
    function openDetail1(ORDER_ID){
    	var options = {max:true,width:1024,height:600,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
    	$.pdialog.open(webApps+"/jsp/dms/common/saleOrderInfoDetail.jsp?ORDER_ID="+ORDER_ID, "saleOrderDetail", "销售订单明细", options,true);
    }
    /* function openDetail2(RETURN_ID){
    	var options = {max:true,width:1024,height:600,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
    	$.pdialog.open(webApps+"/jsp/dms/oem/part/purchase/purchaseOrder/pchReturnOrderForDetails.jsp?id=" +RETURN_ID, "pchOrderDetail", "采购退货单明细", options);
    } */
</script>