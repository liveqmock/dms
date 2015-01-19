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
            <form method="post" id="fm-invoiceInfo">
                <div class="searchBar" align="left">
                    <table class="editTable" id="tab-invoiceInfo">
                    <input type="hidden" id="dia-SUM_ID" name="dia-SUM_ID" datasource="SUM_ID"/>
                    	<tr>
						    <td><label>渠道名称：</label></td>
						    <td><input type="text" id="dia-ORG_NAME" name="dia-ORG_NAME" datasource="ORG_NAME" readonly="readonly" datatype="1,is_null,300000" />
						    </td>
						    <td><label>渠道代码：</label></td>
						    <td><input type="text" id="dia-ORG_CODE" name="dia-ORG_CODE" datasource="ORG_CODE" readonly="readonly" datatype="1,is_null,300000"/>
						    </td>
						    <td><label>开票总金额：</label></td>
						    <td><input type="text" id="dia-INVOICE_AMOUNT" name="dia-INVOICE_AMOUNT" datasource="INVOICE_AMOUNT" datatype="1,is_null,300000" readonly="readonly"/>
						    </td>
						</tr>
						<tr>
							<td><label>发票号：</label></td>
						    <td colspan="3" >
                                <input type="text" style="width:88%;" id="dia-INVOICE_NO"  name="dia-INVOICE_NO" datasource="INVOICE_NO" datatype="1,is_null,300000" readonly="readonly"/>
                            </td> 
						</tr>
						<tr>
						    <td><label>开票备注：</label></td>
						    <td colspan="4">
							  <textarea id="dia-REMARKS" style="width:450px;height:40px;" name="dia-REMARKS" datasource="REMARKS" datatype="1,is_null,1000" readonly="readonly"></textarea>
						    </td>
						</tr>
						<tr>
                        	<td><label>快递公司：</label></td>
                            <td>
                                <input type="text" id="dia-EXPRESS_COMPANY" name="dia-EXPRESS_COMPANY" datasource="EXPRESS_COMPANY" datatype="1,is_null,30" readonly="readonly"/>
                            </td>
                            <td><label>快递单号：</label></td>
                            <td>
                                <input type="text" id="dia-EXPRESS_NO" name="dia-EXPRESS_NO" datasource="EXPRESS_NO" datatype="1,is_null,300" readonly="readonly"/>
                            </td>
                            <td><label>收件人：</label></td>
                            <td>
                                 <input  type="text" name="RECIVE_USER" id="RECIVE_USER" datasource="RECIVE_USER" datatype="1,is_null,300" readonly="readonly"/>
                            </td>
                        </tr>
                        <tr>
                            <td><label>电话：</label></td>
                            <td>
                                <input type="text" id="dia-TEL" name="dia-TEL" datasource="TEL" datatype="1,is_phone,11" readonly="readonly"/>
                            </td>
                            <td><label>地址：</label></td>
                            <td>
                                <input type="text" id="dia-ADDRESS" name="dia-ADDRESS" datasource="ADDRESS" datatype="1,is_null,300" readonly="readonly"/>
                            </td>
                            <td><label>邮寄人：</label></td>
                            <td>
                                <input type="text" id="dia-SENDER" name="dia-SENDER" datasource="SENDER" datatype="1,is_null,300" readonly="readonly"/>
                            </td>
                        </tr>
                        <tr>
                            <td><label>邮寄备注：</label></td>
                            <td colspan="6"><textarea class="" rows="3" id="dia-EXPRESS_REMARKS" name="dia-EXPRESS_REMARKS" datasource="EXPRESS_REMARKS" style="width:500px;" datatype="1,is_null,500" readonly="readonly"></textarea></td>
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
		              <li><div class="button"><div class="buttonContent"><button type="button" id="btn-closeWin">关&nbsp;&nbsp;闭</button></div></div></li>
		          </ul>
		    </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var diaUrl = "<%=request.getContextPath()%>/part/financeMng/invoiceMng/DealerInvoiceMngAction";
    var selWin = $("body").data("editWin1");
    $(function () {
    	$("#orderList").height(document.documentElement.clientHeight-90);
        $("#btn-closeWin").click(function () {
            $.pdialog.close(selWin);
            return false;
        });
		var selectedRows = $("#tab-saleOrderList").getSelectedRows();
		setEditValue("fm-invoiceInfo", selectedRows[0].attr("rowdata"));
		doOrderSearch();
    });

    function doOrderSearch(){
    	var searchUrl = diaUrl+"/invioceOrderSearch.ajax?SUM_ID="+$("#dia-SUM_ID").val();
		var $f = $("#fm-invoiceInfo");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"",1,sCondition,"tab-order_info");
    }
    function showLink(obj)
    {
    	var $row=$(obj).parent();
    	var type = $row.attr("TYPE");
    	return "<a href='#' onclick=openDetail1("+$row.attr("ORDER_ID")+") class='op'>"+$row.attr("ORDER_NO")+"</a>";
        
    }
    function openDetail1(ORDER_ID){
    	var options = {max:true,width:1024,height:600,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
    	$.pdialog.open(webApps+"/jsp/dms/common/saleOrderInfoDetail.jsp?ORDER_ID="+ORDER_ID, "saleOrderDetail", "销售订单明细", options,true);
    }
</script>