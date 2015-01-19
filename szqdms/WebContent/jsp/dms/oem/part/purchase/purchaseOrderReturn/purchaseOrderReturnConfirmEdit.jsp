<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="com.org.frameImpl.Constant" %>
<%@ page import="com.org.framework.Globals" %>
<%@ page import="com.org.framework.common.User" %>
<%@ page import="com.org.dms.common.DicConstant" %>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
    String contentPath = request.getContextPath();
    String action = request.getParameter("action");
    if (action == null)
        action = "1";
%>
<div id="dia-layout">
    <div class="tabs" eventType="click" id="dia-tabs">
        <div class="tabsHeader">
            <div class="tabsHeaderContent">
                <ul>
                    <li><a href="javascript:void(0);"><span>退货单基本信息</span></a></li>
                    <li><a href="javascript:void(0);"><span>退货配件信息</span></a></li>
                </ul>
            </div>
        </div>
        <div class="tabsContent">
            <div style="height:auto;overflow:hidden;">
                <form method="post" id="dia-fm-orderEdit" class="editForm" style="width:99%;">
				<input type="hidden" id="dia-RETURN_ID" name="dia-RETURN_ID" datasource="RETURN_ID" />
		  <div align="left">
			  <fieldset>
						<table class="editTable" id="dia-tab-order">
							<tr>
								<td><label>退货单号：</label></td>
							    <td><input type="text" id="dia-RETURN_NO" name="dia-RETURN_NO" datasource="RETURN_NO" value="系统自动生成" readonly="readonly"/></td>
							    <td><label>供应商代码：</label></td>
							    <td>
							    	<input type="text" id="dia-SUPPLIER_CODE" name="dia-SUPPLIER_CODE" datasource="SUPPLIER_CODE" />
							    	<input type="hidden" id="dia-SUPPLIER_ID" name="dia-SUPPLIER_ID" datasource="SUPPLIER_ID" />
							    </td>
								<td><label>供应商名称：</label></td>
							    <td>
							    	<input type="text" id="dia-SUPPLIER_NAME" name="dia-SUPPLIER_NAME" datasource="SUPPLIER_NAME" readonly="true"/>
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
                    <div id="dia-part" style="height:auto;overflow:hidden;">
                    <table style="display:none;width:100%;" id="dia-tab-partinfo" layoutH="350" name="tablist"ref="dia-part" refQuery="tab-searchPart">
                        <thead>
                        <tr>
                            <th type="single" name="XH"  style="display:none;"></th>
                            <th fieldname="PART_CODE">配件代码</th>
                            <th fieldname="PART_NAME">配件名称</th>
                            <th fieldname="UNIT">计量单位</th>
                            <th fieldname="MIN_PACK" colwidth="75" refer="toAppendStr">最小包装</th>
                            <th fieldname="COUNT">退货数量</th>
                            <th fieldname="PCH_PRICE" refer="amountFormatStr">采购价格</th>
                            <th fieldname="AMOUNT" refer="amountFormatStr">金额</th>
                            <th fieldname="REMARKS">备注</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
                </div>
                <div>
                  <form method="post" id="dia-fm-confirmEdit" class="editForm" style="width:99%;">
				  <fieldset>
							<table class="editTable" id="dia-tab-order">
								<tr>
								    <td><label>是否同意退货：</label></td>
								    <td>
									    <select type="text" class="combox" id="dia-IS_AGREED" name="dia-IS_AGREED" kind="dic" src="SF" datasource="IS_AGREED" datatype="0,is_null,6" readonly="readonly">
									    	<option value="-1" selected>--</option>
									    </select>
									</td>
								</tr>
								<tr>
								    <td><label>确认意见：</label></td>
								    <td colspan="5">
									  <textarea id="dia-ADVICE" style="width:450px;height:40px;" name="dia-ADVICE" datasource="ADVICE" datatype="1,is_null,1000"></textarea>
								    </td>
								</tr>
							</table>
				  </fieldset>
                 </form>
                 </div>
                <div class="formBar">
                    <ul>
                        <li><div class="button"><div class="buttonContent"><button type="button" name="btn-pre">上&nbsp;一&nbsp;步</button></div></div></li>
                        <li id="dia-contror" style="display: none"><div class="button"><div class="buttonContent"><button type="button" id="btn-comfirm" >确&nbsp;&nbsp;认</button></div></div></li>
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
    var diaUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseReturn/PurchaseOrderReturnComfirmAction";
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
                    if (!$('#dia-RETURN_ID').val()) {
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
        //提交按钮响应
        var row;
        $("#btn-comfirm").bind("click", function(event){
    		var RETURN_ID = $("#dia-RETURN_ID").val();
    		var $f = $("#dia-fm-confirmEdit");
    		if (submitForm($f) == false) return false;
    		var sCondition = {};
    		sCondition = $("#dia-fm-confirmEdit").combined(1) || {};
    		var url = diaUrl + "/purchaseOrderReturnConfirm.ajax?RETURN_ID="+RETURN_ID;
    		doNormalSubmit($f,url,"btn-comfirm",sCondition,comfirmCallBack);
    	});
    	//关闭按钮响应
    	$("button.close").click(function(){
    		$.pdialog.closeCurrent();
    		return false;
    	});
        if (diaAction != "1") {
        	$("#dia-contror").show();
        	$('input').attr("readonly","readonly");
            var selectedRows = $("#tab-order_info").getSelectedRows();
            setEditValue("dia-fm-orderEdit", selectedRows[0].attr("rowdata"));
           
        } 
    })
     //查询采购订单配件
    function searchPart(){
    	$("#dia-contror").show();
    	var RETURN_ID = $("#dia-RETURN_ID").val();
   		var searchContractPartUrl = diaUrl + "/returnPartSearch.ajax?RETURN_ID="+RETURN_ID;
   		var $f = $("#dia-fm-orderEdit");
   		var sCondition = {};
   	    sCondition = $f.combined() || {};
   	    doFormSubmit($f, searchContractPartUrl, "", 1, sCondition, "dia-tab-partinfo");
    } 
    var row;
    function comfirmCallBack(res){
    	var selectedRows = $("#tab-order_info").getSelectedRows();
		$row =  selectedRows[0];
		$("#tab-order_info").removeResult($row);
		$.pdialog.closeCurrent();
    }
    function toAppendStr(obj){
		var $tr =$(obj).parent();
		return $tr.attr("MIN_PACK")+"/"+$tr.attr("MIN_UNIT_sv");
	}
    function amountFormatStr(obj){
		return "<div style='text-align:right;'>"+amountFormatNew($(obj).html())+"</div>";
	}
</script>