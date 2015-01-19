<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="com.org.frameImpl.Constant" %>
<%@ page import="com.org.framework.Globals" %>
<%@ page import="com.org.framework.common.User" %>
<%@ page import="com.org.dms.common.DicConstant" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
    String contentPath = request.getContextPath();
    String action = request.getParameter("action");
    if (action == null)
        action = "1";
%>
<div id="dia-layout">
    <div class="tabs">
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
				<input type="hidden" id="dia-PURCHASE_ID" name="dia-PURCHASE_ID" datasource="PURCHASE_ID" />
		  <div align="left">
			  <fieldset>
						<table class="editTable" id="dia-tab-order">
							<tr>
								<td><label>订单编号：</label></td>
							    <td><div id="diaOrderNo">系统自动生成</div></td>
							    <td><label>所属月度：</label></td>
							    <td>
							    	<input type="text"  id="dia-SELECT_MONTH"  name="dia-SELECT_MONTH"   dataSource="SELECT_MONTH" style="width:75px;"    />
							    </td>
							    <td><label>采购类型：</label></td>
							    <td>
							    	<input type="text" id="dia-PURCHASE_TYPE" name="dia-PURCHASE_TYPE" datasource="PURCHASE_TYPE" />
								</td>
							</tr>
							<tr>
								<td><label>厂家代码：</label></td>
							    <td>
							    	<input type="text" id="dia-SUPPLIER_CODE" name="dia-SUPPLIER_CODE" datasource="SUPPLIER_CODE" kind="dic" src="" datatype="0,is_null,300"  dicWidth="500"/>
							    	<input type="hidden" id="dia-SUPPLIER_ID" name="dia-SUPPLIER_ID" datasource="SUPPLIER_ID" />
							    </td>
								<td><label>厂家名称：</label></td>
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
                            <th fieldname="UNIT" colwidth="75">计量单位</th>
                            <th fieldname="MIN_UNIT" refer="appendStr"  colwidth="70">最小包装</th>
                            <th fieldname="PCH_COUNT" colwidth="75">采购数量</th>
                            <th fieldname="PCH_PRICE" colwidth="75" refer="amountFormatStr" align="right">采购价格(元)</th>
                            <th fieldname="AMOUNT" refer="amountFormatStr" align="right">金额(元)</th>
<!--                             <th fieldname="DELIVERY_CYCLE" colwidth="75">供货周期</th> -->
                            <th fieldname="REMARKS">备注</th>
                        </tr>
                        </thead>
                    </table>
                </div>
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
    //注册上一步/下一步按钮响应
    (function ($) {
        $.purchaseOrderMng = {
            /**
             * 初始化方法
             */
            init: function () {
                //设置页面默认值
                $tabs = $("div.tabs:first");
                var iH = document.documentElement.clientHeight;
                $(".tabsContent").height(iH - 70);
                $("button[name='btn-next']").bind("click", function () {
                    $.purchaseOrderMng.doNextTab($tabs);
                });
                $("button[name='btn-pre']").bind("click", function () {
                    $.purchaseOrderMng.doPreTab($tabs);
                });
            },
            doNextTab: function ($tabs) {
                $tabs.switchTab(parseInt($tabs.attr("currentIndex")) + 1);
                (function (ci) {
                    switch (parseInt(ci)) {
                        case 1://第2个tab页
                        	var PURCHASE_ID = $("#dia-PURCHASE_ID").val();
                        	var supplierId=$("#dia-SUPPLIER_ID").val();
                             	if(!supplierId){
                             		alertMsg.warn('请选择供应商');
                             		$tabs.switchTab(parseInt($tabs.attr("currentIndex"))-1);
                             		return false;
                             	}else{
                             		var searchContractPartUrl = diaUrl + "/orderPartSearch.ajax?PURCHASE_ID="+PURCHASE_ID+"&supplierId="+supplierId;
                             		var $f = $("#dia-fm-orderEdit");
                             		var sCondition = {};
                             	    sCondition = $f.combined() || {};
                             	    doFormSubmit($f, searchContractPartUrl, "", 1, sCondition, "dia-tab-partinfo");
                                    if($("#dia-tab-partinfo").is(":hidden"))
                                    {
                                        $("#dia-tab-partinfo").show();
                                        $("#dia-tab-partinfo").jTable();
                                    }
                                    searchPart();
                             	}
                            break;
                        default:
                            break;
                    }
                })(parseInt($tabs.attr("currentIndex")));
            },
            doPreTab: function ($tabs) {
                $tabs.switchTab($tabs.attr("currentIndex") - 1);
            }
        };
    })(jQuery);
/* 取父页面传过的参数确定新增或者修改 */
    var diaAction = "<%=action%>";
    var diaUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseOrder/VerticalShippingOrderAduitAction";
    $(function () {
        $.purchaseOrderMng.init();
        $('input').attr("readonly","readonly");
        //保存基本信息按钮响应
        $("#btn-comfirm").bind("click", function(event){
    		var SUPPLIER_ID = $("#dia-SUPPLIER_ID").val();
    		if(SUPPLIER_ID){
    			var $f = $("#dia-fm-orderEdit");
    			if (submitForm($f) == false) return false;
    			var sCondition = {};
    			sCondition = $("#dia-fm-orderEdit").combined(1) || {};
    			var addUrl = diaUrl + "/orderComfirm.ajax";
    			doNormalSubmit($f,addUrl,"btn-comfirm",sCondition,comfirmCallBack);
    		}else{
    			alertMsg.warn("请选择供应商！");
    			return false;
    		}
        	
		});
    	$("button.close").click(function(){
    		$.pdialog.closeCurrent();
    		return false;
    	});
		//修改页面赋值
        if (diaAction != "1") {
        	$("#dia-contror").show();
            var selectedRows = $("#tab-order_info").getSelectedRows();
            setEditValue("dia-fm-orderEdit", selectedRows[0].attr("rowdata"));
            var PURCHASE_ID = selectedRows[0].attr("PURCHASE_ID");
            var dicSql="T#PT_BA_PART_SUPPLIER_RL T,PT_BU_PCH_ORDER_DTL T1,PT_BA_SUPPLIER T2:DISTINCT T2.SUPPLIER_CODE:T2.SUPPLIER_NAME{T2.SUPPLIER_CODE,T2.SUPPLIER_NAME,T.SUPPLIER_ID}:1=1";
			dicSql+=" AND T.PART_ID  = T1.PART_ID AND T.SUPPLIER_ID = T2.SUPPLIER_ID AND T2.PART_IDENTIFY =<%=DicConstant.YXBS_01%> AND T.PART_IDENTIFY =<%=DicConstant.YXBS_01%>";
			dicSql+=" AND T1.PURCHASE_ID = "+PURCHASE_ID;
            $("#dia-SUPPLIER_CODE").attr("src",dicSql);
        } 
    })
    
   function afterDicItemClick(id,$row){
	var ret = true;
	if(id == "dia-SUPPLIER_CODE")
	{
		$("#"+id).attr("SUPPLIER_ID",$row.attr("T.SUPPLIER_ID"));
		$("#"+id).attr("code",$row.attr("T2.SUPPLIER_CODE"));
		$("#dia-SUPPLIER_NAME").val($row.attr("T2.SUPPLIER_NAME"));
		$("#dia-SUPPLIER_CODE").val($row.attr("T2.SUPPLIER_CODE"));
		$("#dia-SUPPLIER_ID").val($row.attr("T.SUPPLIER_ID"));
		
	}
	return ret;
}
function comfirmCallBack(res)
{
	try
	{
		var selectedRows = $("#tab-order_info").getSelectedRows();
		$row =  selectedRows[0];
		$("#tab-order_info").removeResult($row);
		$.pdialog.closeCurrent();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
    function appendStr(obj){
    	var $tr =$(obj).parent();
    	return $tr.attr("MIN_PACK")+"/"+$tr.attr("MIN_UNIT_sv");
    }
  //金额格式化
    function amountFormatStr(obj){
        return "<div style='text-align:right;'>"+amountFormatNew($(obj).html())+"</div>";
    }
</script>