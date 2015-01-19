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
    <div class="tabs" eventType="click" currentIndex="1" id="dia-tabs">
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
							    <td><label>所属月度：</label></td>
							    <td>
							    	<input type="text"  id="dia-SELECT_MONTH"  name="dia-SELECT_MONTH"   dataSource="SELECT_MONTH" style="width:75px;"   datatype="1,is_null,30" readonly="readonly"/>
							    </td>
							    <td><label>采购类型：</label></td>
							    <td>
							    	<input type="text" id="dia-PURCHASE_TYPE" name="dia-PURCHASE_TYPE" datasource="PURCHASE_TYPE" readonly="readonly"/>
								</td>
							</tr>
							<tr>
								<td><label>厂家代码：</label></td>
							    <td>
							    	<input type="text" id="dia-SUPPLIER_CODE" name="dia-SUPPLIER_CODE" datasource="SUPPLIER_CODE"  datatype="1,is_null,30" readonly="readonly"/>
							    	<input type="hidden" id="dia-SUPPLIER_ID" name="dia-SUPPLIER_ID" datasource="SUPPLIER_ID" readonly="readonly"/>
							    </td>
								<td><label>厂家名称：</label></td>
							    <td>
							    	<input type="text" id="dia-SUPPLIER_NAME" name="dia-SUPPLIER_NAME" datasource="SUPPLIER_NAME" datatype="1,is_null,30" readonly="true"/>
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
            <div style="overflow:auto;">
               	    <form method="post" id="fm-searchPart" class="editForm">
                        <table class="searchContent" id="tab-searchPart"></table>
                    </form>
                    <div id="dia-part" style="overflow:auto;">
	                    <table style="display:none;width:80%;" id="dia-tab-partinfo"  name="tablist"ref="dia-part" refQuery="tab-searchPart">
	                        <thead>
		                        <tr>
		                            <th type="multi" name="XH" unique="PART_ID" style="display:none;"></th>
		                            <th fieldname="PART_CODE" refer="myPartCode">配件代码</th>
		                            <th fieldname="PART_NAME" refer="toSubStr">配件名称</th>
		                            <th fieldname="SHIP_COUNT">发货数量</th>
		                            <th fieldname="ACCEPT_COUNT">已验收数量</th>
		                            <th fieldname="WILL_ACCEPT_COUNT">待验收数量</th>
		                            <th fieldname="WILL_ACCEPT_COUNT" refer="createInputBox">本次验收数量</th>
		                            <th fieldname="DISTRIBUTION_NO" refer="createInputBox2">配送号</th>
		<!--                             <th fieldname="PCH_PRICE" refer="formatAmount" style="display:none">采购价格</th>
		                            <th fieldname="PCH_AMOUNT" refer="formatAmount" style="display:none">金额</th> -->
		                            <th fieldname="DELIVERY_CYCLE">供货周期</th>
		                            <th fieldname="USER_ACCOUNT">库管员</th>
		                            <th fieldname="REMARKS">备注</th>
		                        </tr>
	                        </thead>
	                        <tbody>
	                        </tbody>
	                    </table>
                	</div>
                </div>
	            <form id="fm-partInfo">
	                <input type="hidden" id="splitId" name="splitId" datasource="SPLITID"/>
	                <input type="hidden" id="detailIds" name="partCodes" datasource="DETAILIDS"/>
	                <input type="hidden" id="partIds" name="partIds" datasource="PARTIDS"/>
	                <input type="hidden" id="acceptCounts" name="acceptCounts" datasource="ACCEPTCOUNTS"/>
	                <input type="hidden" id="hadAcceptCounts" name="hadAcceptCounts" datasource="HADACCEPTCOUNTS"/>
	                <input type="hidden" id="distributionNos" name="distributionNos" datasource="DISTRIBUTIONNOS"/>
            	</form>
        </div>
        <div class="formBar">
            <ul>
                <li><div class="button"><div class="buttonContent"><button type="button" name="btn-pre">上&nbsp;一&nbsp;步</button></div></div></li>
                <li id="dia-contror" style="display:none;"><div class="button"><div class="buttonContent"><button type="button" id="btn-shipping" >验&nbsp;&nbsp;收</button></div></div></li>
                <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
            </ul>
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
    var win = $("body").data("shippingWin");
    var diaUrl = "<%=request.getContextPath()%>/part/storageMng/stockInMng/PchStockAcceptMngAction";
    var flag = true;
    $(function () {
//     	$("#dia-part").height(document.documentElement.clientHeight-100);
//     	var iH = document.documentElement.clientHeight;
//         $(".tabsContent").height(iH - 140);
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
                	var SPLIT_ID = $("#dia-SPLIT_ID").val();
             		var searchContractPartUrl = diaUrl + "/orderPartSearch.ajax?SPLIT_ID="+SPLIT_ID;
             		var $f = $("#fm-searchPart");
             		var sCondition = {};
             	    sCondition = $f.combined() || {};
             	    doFormSubmit($f, searchContractPartUrl, "", 1, sCondition, "dia-tab-partinfo");
                    break;
                default:
                    break;
            }
            
        });
    	$("button.close").click(function(){
    		$.pdialog.closeCurrent();
    		return false;
    	});
    	$('#btn-shipping').bind('click',function(){
            //添加促销配件URL
            var shippingUrl = diaUrl+"/acceptPart.ajax";
            var partIds="",detailIds="",acceptCounts="",oldAcceptCounts="",distributionNos="";
            $("tr",$("#dia-tab-partinfo")).each(function(){
            	var $tr = $(this);
            	var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
            	if($checkbox.is(":checked"))
            	{
            		partIds += partIds.length?"," + $tr.attr("PART_ID"):$tr.attr("PART_ID");
            		detailIds += detailIds.length?"," + $tr.attr("DETAIL_ID"):$tr.attr("DETAIL_ID");
            		var curAcceptCount = $tr.find("input[name='curAcceptCount']");
            		acceptCounts += acceptCounts.length?"," + curAcceptCount.val():curAcceptCount.val();
            		oldAcceptCounts += oldAcceptCounts.length?"," + $tr.attr("ACCEPT_COUNT"):$tr.attr("ACCEPT_COUNT");
            		var distributionNo = $tr.find("input[name='distributionNo']");
            		if(distributionNo.val()){
            			distributionNos += distributionNos.length?"," + distributionNo.val():distributionNo.val();
            		}else{
            			distributionNos += distributionNos.length?"," + "myNull":"myNull";
            		}
            		
            	}
            });
            if(partIds.length==0){
            	alertMsg.info("请输入本次验收数量,进行验收.");
            	return false;
            }
            $('#splitId').val($('#dia-SPLIT_ID').val());
            $('#partIds').val(partIds);
            $('#detailIds').val(detailIds);
            $('#acceptCounts').val(acceptCounts);
            $('#hadAcceptCounts').val(oldAcceptCounts);
            $('#distributionNos').val(distributionNos);
            var $f = $("#fm-partInfo");
            if (submitForm($f) == false) return false;
            var sCondition = {};
            sCondition = $f.combined(1) || {};
            doNormalSubmit($f, shippingUrl, "btn-shipping", sCondition, shippingPartCallBack); 
        });
		//修改页面赋值
        if (diaAction != "1") {
        	$("#dia-contror").show();
            var selectedRows = $("#tab-order_info").getSelectedRows();
            setEditValue("dia-fm-orderEdit", selectedRows[0].attr("rowdata"));
            
            var SPLIT_ID = selectedRows[0].attr("SPLIT_ID");
     		var searchContractPartUrl = diaUrl + "/orderPartSearch.ajax?SPLIT_ID="+SPLIT_ID;
     		var $f = $("#fm-searchPart");
     		var sCondition = {};
     	    sCondition = $f.combined() || {};
     	    doFormSubmit($f, searchContractPartUrl, "", 1, sCondition, "dia-tab-partinfo");
        } 
    });
    function shippingPartCallBack(){
    	$.pdialog.close(win);
    	doSearchOrder();
    }
    function isNum($obj)
    {
        var reg = /^(0|\+?[1-9][0-9]*)$/;
        if(reg.test($obj.val()))
        {
            return true;
        }else
        {
            return false;
        }
    }
	function createInputBox(obj)
    {
        return '<div style="width:50px;"><input style="width:50px;" type="text" name="curAcceptCount" onblur="doMyInputBlur(this)"/></div>';
    }
	function createInputBox2(obj)
    {
        return '<div style="width:100px;"><input style="width:100px;" type="text" name="distributionNo" datatype="1,is_null,100"/></div>';
    }
	function doMyInputBlur(obj){
		var $obj = $(obj);
		var $tr = $obj;
		while($tr.get(0).tagName != "TR")
			$tr = $tr.parent();
		var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
        var $obj = $(obj);
        if($obj.val().length==0){
        	$checkbox.attr("checked",false);
            return false;
        }
        if($obj.val() && !isNum($obj))
        {
            alertMsg.warn("请输入正确的数量！");
            $obj.val("");
            $checkbox.attr("checked",false);
            return false;
        }
        var shipCount = $tr.attr("SHIP_COUNT");
        var acceptCount = $tr.attr("ACCEPT_COUNT");
   	   	if(parseInt($obj.val())>(parseInt(shipCount)-parseInt(acceptCount))){
        	alertMsg.warn("验收数量大于发货数量,请重新输入！");
        	$obj.val("");
        	$checkbox.attr("checked",false);
        	return false;
        }
   	 	$checkbox.attr("checked",true);
    }
	function toSubStr(obj){
		 var $obj = $(obj);
		 var $tr = $obj.parent();
		 var partName = $tr.attr('PART_NAME');
		 if(partName.length>18){
			 return "<div style='width:200px;'>"+partName.substring(0,18)+"...</div>";
		 }else{
			 return "<div style='width:200px;'>"+partName+"</div>";
		 }
	}
    //输入框
    function myPartCode(obj){
        var $tr =$(obj).parent();
        var partCode = $tr.attr("PART_CODE");
        var search = $("#in-partCode").val();
        // 其他订单
        if(partCode.indexOf(search) > 0 ){
             $tr.find("td").eq(2).attr("style","color:red");
        }
    }
</script>