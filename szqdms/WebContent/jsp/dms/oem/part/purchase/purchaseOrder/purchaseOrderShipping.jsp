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
    String orgCode = user.getOrgCode();
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
							    	<input type="hidden" id="dia-SUPPLIER_ID" name="dia-SUPPLIER_ID" datasource="SUPPLIER_ID" />
							    </td>
								<td><label>厂家名称：</label></td>
							    <td>
							    	<input type="text" id="dia-SUPPLIER_NAME" name="dia-SUPPLIER_NAME" datasource="SUPPLIER_NAME" datatype="1,is_null,30" readonly="true"/>
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
                <div class="formBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" name="btn-next">下&nbsp;一&nbsp;步</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
                    </ul>
                </div>
            </div>
            <div>
                <form method="post" id="fm-searchPart" class="editForm">
                        <table class="searchContent" id="tab-searchPart"></table>
                    </form>
                <div id="dia-part" style="overflow:auto;">
                    <table style="display:none;width:100%;" id="dia-tab-partinfo"  name="tablist"ref="dia-part" refQuery="tab-searchPart">
                        <thead>
                        <tr>
                            <th type="multi" name="XH" unique="PART_ID" style=""></th>
                            <th fieldname="PART_CODE" colwidth="200">配件代码</th>
                            <th fieldname="PART_NAME" colwidth="200">配件名称</th>
                            <th fieldname="PCH_COUNT" colwidth="100" >采购数量</th>
                            <th fieldname="SHIP_COUNT" colwidth="80" >已发货数量</th>
                            <th fieldname="WILL_SHIP_COUNT" colwidth="100" refer="createInputBox">本次发货数量</th>
                            <th fieldname="PCH_PRICE" colwidth="100"  refer="formatAmount" >采购价格</th>
                            <th fieldname="PCH_AMOUNT" refer="formatAmount"colwidth="100" >金额</th>
                            <th fieldname="DELIVERY_CYCLE"  colwidth="100">供货周期</th>
                            <th fieldname="REMARKS">备注</th>
                            <th fieldname="DISTRIBUTION_NO" colwidth="100" refer="createInputBox1" id="PB">配送号</th>
                            <th fieldname="DIRECT_TYPE_CODE" style="display:none">直发类型</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
                <fieldset id="fie-selectedPart" style="display: none">
                <legend align="left" >&nbsp;[已选定配件]</legend>
                <div id="div-selectedPart">
                    <table style="width:100%;">
                        <tr>
                            <td>
                                <textarea style="width:80%;height:26px;display: none" id="val0" column="1" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;height:26px;display: none" id="val1" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;height:26px;display: none" id="val2" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;height:26px;display: none" id="val3" name="multivals" readOnly></textarea>
                            </td>
                        </tr>
                    </table>
                </div>
	            </fieldset>
	            <form id="fm-partInfo">
	                <input type="hidden" id="splitId" name="splitId" datasource="SPLITID"/>
	                <input type="hidden" id="detailIds" name="partCodes" datasource="DETAILIDS"/>
	                <input type="hidden" id="partIds" name="partIds" datasource="PARTIDS"/>
	                <input type="hidden" id="shipCounts" name="shipCounts" datasource="SHIPCOUNTS"/>
	                <input type="hidden" id="hadShipCounts" name="hadShipCounts" datasource="HADSHIPCOUNTS"/>
	                <input type="hidden" id="pbNos" name="pbNos" datasource="PBNOS"/>
            	</form>
	            <div class="formBar">
	                <ul>
	                    <li><div class="button"><div class="buttonContent"><button type="button" name="btn-pre">上&nbsp;一&nbsp;步</button></div></div></li>
	                    <li id="dia-contror" style="display: none"><div class="button"><div class="buttonContent"><button type="button" id="btn-shipping" >发&nbsp;&nbsp;货</button></div></div></li>
	                    <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
	                </ul>
	            </div>
	        </div>
            	<div class="formBar">
		             <ul>
		                 <li><div class="button"><div class="buttonContent"><button type="button" name="btn-pre">上&nbsp;一&nbsp;步</button></div></div></li>
		                 <li id="dia-contror" style="display: none"><div class="button"><div class="buttonContent"><button type="button" id="btn-shipping" >发&nbsp;&nbsp;货</button></div></div></li>
		                 <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
		   	        </ul>
		 		</div>
            </div>
        </div>
        <div class="tabsFooter">
            <div class="tabsFooterContent"></div>
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
                $(".tabsContent").height(iH - 140);
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
    var orgCode = "<%=orgCode%>";
    var win = $("body").data("shippingWin");
    var diaUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseOrder/PurchaseOrderDeliveryAction";
    $(function () {
        $.purchaseOrderMng.init();
    	$("button.close").click(function(){
    		$.pdialog.closeCurrent();
    		return false;
    	});
    	

    	$("#dia-part").height(document.documentElement.clientHeight-200);
    	$('#btn-shipping').bind('click',function(){
        	var detailIds="";
        	var partIds="";
        	var hadShipCounts="";
        	var shipCounts="";
        	var pbNos="";
        	var flag = true;
        	$("tr",$("#dia-tab-partinfo")).each(function(){
            	var $tr = $(this);
            	var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
            	if($checkbox.is(":checked"))
            	{
            		detailIds += detailIds.length?"," + $tr.attr("DETAIL_ID"):$tr.attr("DETAIL_ID");
            		partIds += partIds.length?"," + $tr.attr("PART_ID"):$tr.attr("PART_ID");
            		hadShipCounts += hadShipCounts.length?"," + $tr.attr("SHIP_COUNT"):$tr.attr("SHIP_COUNT");
            		var shipCountsTmp = $tr.find("input[name='WILL_SHIP_COUNT']");
            		if(shipCountsTmp.val()){
            			shipCounts += shipCounts.length?"," + shipCountsTmp.val():shipCountsTmp.val();
            		}else{
            			shipCounts += shipCounts.length?"," + "0":"0";
            		}
            		if(orgCode =="SQGH001"){
            			
            			var psh = $tr.find("td").eq(11).find("input:first").val();
            			var count = $tr.find("td").eq(6).find("input:first").val();
                		var partCode = $tr.find("td").eq(2).text();
                		if(!psh)
                		{
                			if(parseInt(count)!=0){
                				flag = false;
                    			alertMsg.warn(partCode+":请输入配送号.");
                    			return false;
                			}
                		}
            			var pbNoTmp = $tr.find("input[name='PB_NO']");
                		if(pbNoTmp.val()){
                			pbNos += pbNos.length?"," + pbNoTmp.val():pbNoTmp.val();
                		}else{
                			pbNos += pbNos.length?"," + "myNull":"myNull";
                		}
            		}
            		
            	}
            
            });
        	if(flag == false) return false;
        	//将拼接串放入隐藏form中
        	$('#splitId').val($('#dia-SPLIT_ID').val());
            $('#partIds').val(partIds);
            $('#detailIds').val(detailIds);
            $('#shipCounts').val(shipCounts);
            $('#hadShipCounts').val(hadShipCounts);
            if(orgCode =="SQGH001"){
            	$('#pbNos').val(pbNos);
            }
            var shippingUrl = diaUrl+"/shippingPart.ajax";
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
            var type = selectedRows[0].attr("PURCHASE_TYPE");
            if(orgCode =="SQGH001"){
            	$("#PB").attr("style","");
            }else{
            	$("#PB").attr("style","display:none");
            }
        } 
    });
    function shippingPartCallBack(){
    	$.pdialog.close(win);
    	doSearchOrder();
    }
    function isNum($obj)
    {
        var reg = /^[0-9]\d*$/
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
		var type = $("#dia-PURCHASE_TYPE").attr("code");
        var supplierCode = $("#dia-SUPPLIER_CODE").val();
        var code = supplierCode.substring(0,4);
        var $obj = $(obj);
        var $tr = $(obj).parent();
        var d_code = $tr.find("td").eq("12").text();
        if(type==<%=DicConstant.CGDDLX_05%>){
        	if(d_code=="WCZF"||d_code=="THZF"){
        		/* 陕汽直发订单 */
				var a = $tr.find("td").eq("4").text();
				var b = $tr.find("td").eq("5").text();
				var default_no = a-b;
/* 				var default_no = $tr.find("td").eq("4").text(); */
				var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
				var checkObj = $("input:first",$tr.find("td").eq(1));
				var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
				$checkbox.attr("checked",true);
        		return '<input type="text" style="width:50px;" name="WILL_SHIP_COUNT" value="'+default_no+'"  onblur="doMyInputBlur(this)" / >';
        	}else{
        		/*非陕汽直发订单  */
/* 				var default_no = $tr.find("td").eq("4").text(); */
				var a = $tr.find("td").eq("4").text();
				var b = $tr.find("td").eq("5").text();
				var default_no = a-b;
				var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
				var checkObj = $("input:first",$tr.find("td").eq(1));
				var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
				$checkbox.attr("checked",true);
				$checkbox.attr("disabled",true);
        		return '<input type="text" style="width:50px;" name="WILL_SHIP_COUNT" value="'+default_no+'"  onblur="doMyInputBlur(this)" readonly="true"/ >';
        	}
        }else{
        	/* 非直发订单发货 */
/* 			var default_no = $tr.find("td").eq("4").text(); */
			var a = $tr.find("td").eq("4").text();
			var b = $tr.find("td").eq("5").text();
			var default_no = a-b;
			var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
			var checkObj = $("input:first",$tr.find("td").eq(1));
			var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
			$checkbox.attr("checked",true);
        	 return '<input type="text" style="width:50px;" name="WILL_SHIP_COUNT" value="'+default_no+'"  onblur="doMyInputBlur(this)" / >';
        }
    }
    function doMyInputBlur(obj){
    	
        var $obj = $(obj);
        if($obj.val() == "")
            return ;
        if($obj.val() && !isNum($obj))
        {
            alertMsg.warn("请输入正确的数量！");
            $obj.val("");
            return;
        }
        var s = $obj.val();
        var s = 0;
        s = $obj.val();
        var $obj1 = $(obj);
        var $tr = $obj1;
        while($tr.get(0).tagName != "TR")
        	$tr = $tr.parent();
        var checkObj = $("input:first",$tr.find("td").eq(1));
        var count = 0;
        var hasCount = 0;
        count = $tr.find("td").eq(4).text();
        hasCount = $tr.find("td").eq(5).text();
   	   	if(parseInt(s)-parseInt(count)+parseInt(hasCount)>0){
        	alertMsg.warn("发货数量大于订购数量,请重新输入.");
        	$obj.val(parseInt(count)-parseInt(hasCount));
        	$checkObj.attr("checked",false);
        	 return false;
        }
        if($obj.val() > 0)
    		$checkbox.attr("checked",true);
    	doSelectedBefore1($tr,checkObj,1);
    }
    function doCheckbox(checkbox) {
   	    var $tr = $(checkbox).parent().parent().parent();
   	 	var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
        var reg = /^[1-9]\d*$/;
        var s = "";
        if($tr.find("td").eq(6).find("input:first").size()>0){
            s = $tr.find("td").eq(6).find("input:first").val();
        }
        else{
            s = $tr.find("td").eq(6).text();
        }
        if(!s&&!isNum(s)){
        	alertMsg.warn("请输入正确的发货数量.");
        	$checkbox.attr("checked",false);
        	 return false;
        }
        var count = 0;
        var hasCount = 0;
        count = $tr.find("td").eq(4).text();
        hasCount = $tr.find("td").eq(5).text();
   	   	if(parseInt(s)-parseInt(count)+parseInt(hasCount)>0){
        	alertMsg.warn("发货数量大于订购数量,请重新输入.");
        	$checkbox.attr("checked",false);
        	 return false;
        }
        var arr = [];
        arr.push($tr.attr("PART_ID"));
        arr.push($tr.attr("DETAIL_ID"));
        arr.push(s);
        arr.push($tr.attr("SHIP_COUNT"));
        if(orgCode=="SQGH001"){
     		arr.push(p);
        }
        multiSelected($checkbox, arr,$('#div-selectedPart'));
        

    }
    function customOtherValue($row,checkVal)
    {
        var $inputObj = $row.find("td").eq(6);
        var val;
        if($("#val2") && $("#val2").val())
        {
            var counts = $("#val2").val();
            var pks = $("#val0").val();
            var count = counts.split(",");
            var pk = pks.split(",");
            for(var i=0;i<pk.length;i++)
            {
                if(pk[i] == checkVal)
                {
                    val = count[i];
                    break;
                }
            }
        }
        if(val)
        {
            $inputObj.html("<div>"+val+"<div/ >");
        }
    }
    function formatAmount(obj){
        return "<div style='text-align:right;'>"+amountFormatNew($(obj).html())+"</div>";
    }
    
    function createInputBox1(obj){
		 return '<input type="text" name="PB_NO" onblur="doMyPbBlur(this)"/ >';
	}
	function doMyPbBlur(obj){
    	
        var $obj = $(obj);
        if($obj.val() == "")
            return ;
        var s = 0;
        s = $obj.val();
        var $obj = $(obj);
        var $tr = $obj;
        while($tr.get(0).tagName != "TR")
        	$tr = $tr.parent();
        var checkObj = $("input:first",$tr.find("td").eq(1));
        if($obj.val() > 0)
    		$checkbox.attr("checked",true);
    	doSelectedBefore2($tr,checkObj,1);
    }
	function doSelectedBefore2($tr,$obj,type)
    {
        doCheckbox($obj.get(0));
    }
	function doSelectedBefore1($tr,$obj,type)
    {
        doCheckbox($obj.get(0));
    }
</script>