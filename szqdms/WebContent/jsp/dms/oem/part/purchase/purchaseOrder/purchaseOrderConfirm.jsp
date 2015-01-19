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
				<input type="hidden" id="dia-SPLIT_ID" name="dia-SPLIT_ID" datasource="SPLIT_ID" />
		  <div align="left">
			  <fieldset>
						<table class="editTable" id="dia-tab-order">
							<tr>
								<td><label>订单编号：</label></td>
							    <td><input type="text" id="dia-SPLIT_NO" name="dia-SPLIT_NO" datasource="SPLIT_NO" value="系统自动生成" readonly="readonly"/></td>
							    <td id="mounth"><label>所属月度：</label></td>
							    <td id="mounth1">
							    	<input type="text"  id="dia-SELECT_MONTH"  name="dia-SELECT_MONTH"   dataSource="SELECT_MONTH" style="width:75px;"  readonly="readonly" />
							    </td>
							    <td><label>采购类型：</label></td>
							    <td>
							    	<input type="text" id="dia-PURCHASE_TYPE" name="dia-PURCHASE_TYPE" datasource="PURCHASE_TYPE" readonly="readonly"/>
								</td>
							</tr>
							<tr>
								<td><label>厂家代码：</label></td>
							    <td>
							    	<input type="text" id="dia-SUPPLIER_CODE" name="dia-SUPPLIER_CODE" datasource="SUPPLIER_CODE"  readonly="readonly"/>
							    	<input type="hidden" id="dia-SUPPLIER_ID" name="dia-SUPPLIER_ID" datasource="SUPPLIER_ID" />
							    </td>
								<td><label>厂家名称：</label></td>
							    <td>
							    	<input type="text" id="dia-SUPPLIER_NAME" name="dia-SUPPLIER_NAME" datasource="SUPPLIER_NAME" readonly="true"/>
							    </td>
							    <td><label>要求完成时间：</label></td>
							    <td>
							    	<input type="text" id="dia-REPUIREMENT_TIME" name="dia-REPUIREMENT_TIME" datasource="REPUIREMENT_TIME" readonly="true"/>
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
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" name="btn-next">下&nbsp;一&nbsp;步</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
                    </ul>
                </div>
            </div>
            <div>
                <div id="dia-part" style="overflow:hidden;">
                    <table style="display:none;width:100%;" id="dia-tab-partinfo"  name="tablist" ref="dia-part" refQuery="tab-searchPart">
                        <thead>
	                        <tr>
	                            <th type="single" name="XH"  style="display:none;"></th>
	                            <th fieldname="PART_CODE">配件代码</th>
	                            <th fieldname="PART_NAME">配件名称</th>
	                            <th fieldname="UNIT" colwidth="75">计量单位</th>
								<th fieldname="MIN_PACK" colwidth="75" refer="toAppendStr">最小包装</th>
	                            <th fieldname="PCH_COUNT">采购数量</th>
	                            <th fieldname="PCH_PRICE" refer="amountFormatStr" align="right">采购价格</th>
	                            <th fieldname="PCH_AMOUNT" refer="amountFormatStr" align="right">金额</th>
	                            <th fieldname="DELIVERY_CYCLE">供货周期</th>
	                            <th fieldname="REMARKS">备注</th>
	                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
	            <div>
                  <form method="post" id="dia-fm-confirmEdit" class="editForm" style="width:99%;">
				  <fieldset>
					<table class="editTable" id="dia-tab-order">
						<tr>
						    <td><label>及时供货：</label></td>
						    <td>
							    <select type="text" class="combox" id="dia-IF_ON_TIME" name="dia-IF_ON_TIME" kind="dic" src="SF" datasource="IF_ON_TIME" datatype="0,is_null,6" readonly="readonly">
							    	<option value="<%=DicConstant.SF_01 %>" selected>是</option>
							    </select> 
							    <!-- <input type="text" id="dia-IF_ON_TIME" name="dia-IF_ON_TIME" datasource="IF_ON_TIME" kind="dic" src="SF"  datatype="0,is_null,6" readonly="true"/> -->
							</td>
						</tr>
						<tr>
						    <td><label>确认意见：</label></td>
						    <td colspan="5">
							  <textarea id="dia-CONFIRM_ADVISE" style="width:450px;height:40px;" name="dia-CONFIRM_ADVISE" datasource="CONFIRM_ADVISE" datatype="1,is_null,1000"></textarea>
						    </td>
						</tr>
					</table>
				  </fieldset>
                 </form>
          		</div>
          		<div class="formBar">
	              <ul>
	                  <li><div class="button"><div class="buttonContent"><button type="button" name="btn-pre">上&nbsp;一&nbsp;步</button></div></div></li>
	                  <li><div class="button"><div class="buttonContent"><button type="button" id="btn-export-index">导出数据</button></div></div></li>
	                  <li id="dia-contror" style="display: none"><div class="button"><div class="buttonContent"><button type="button" id="btn-comfirm" >确&nbsp;&nbsp;认</button></div></div></li>
	                  <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
	              </ul>
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
                $("#dia-part").height(iH-170);
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
                            	 var SPLIT_ID = $("#dia-SPLIT_ID").val();
                         		var searchContractPartUrl = diaUrl + "/orderPartSearch.ajax?SPLIT_ID="+SPLIT_ID;
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
    var diaUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseOrder/PurchaseOrderComfirmAction";
    $("#dia-part").attr("layoutH",document.documentElement.clientHeight-240);
    $(function () {
        $.purchaseOrderMng.init();
        //保存基本信息按钮响应
        $("#btn-comfirm").bind("click", function(event){
        	var SPLIT_ID = $("#dia-SPLIT_ID").val();
        	var ifOnTime =$("#dia-IF_ON_TIME").val();
       		if(ifOnTime==<%=DicConstant.SF_02%>){
           		var yj=$("#dia-CONFIRM_ADVISE").val();
           		if(yj==""){
           			alertMsg.warn('未能及时供货的必须填写确认意见！');
                       return false;
           		}
           	}	
        	
        	var $f = $("#dia-fm-confirmEdit");
    		if (submitForm($f) == false) return false;
    		var sCondition = {};
    		sCondition = $("#dia-fm-confirmEdit").combined(1) || {};
    		var url = diaUrl+"/orderComfirm.ajax?SPLIT_ID="+SPLIT_ID;
    		doNormalSubmit($f,url,"btn-comfirm",sCondition,comfirmCallBack);
		});
        // 导出按钮绑定
        $("#btn-export-index").click(function(){
//             var $f = $("#fm-condition");
//             if (submitForm($f) == false) {
//                 return false;
//             }
//             var sCondition = {};
//             sCondition = $f.combined() || {};
//             $("#data").val(sCondition);
            var SPLIT_ID = $("#dia-SPLIT_ID").val();
            var searchContractPartUrl = diaUrl + "/download.do?SPLIT_ID="+SPLIT_ID;
            var url = encodeURI(searchContractPartUrl);
            window.location.href = url;
            $("#exportFm").attr("action",url);
            $("#exportFm").submit();
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
            var type = selectedRows[0].attr("PURCHASE_TYPE");
            if(type==<%=DicConstant.CGDDLX_04%>){
            	$("#mounth").hide();
                $("#mounth1").hide();
            }
        } 
    })
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
    function amountFormatStr(obj){
		return "<div style='text-align:right;'>"+amountFormatNew($(obj).html())+"</div>";
	}
    function toAppendStr(obj){
		var $tr =$(obj).parent();
		return $tr.attr("MIN_PACK")+"/"+$tr.attr("MIN_UNIT_sv");
	}
</script>