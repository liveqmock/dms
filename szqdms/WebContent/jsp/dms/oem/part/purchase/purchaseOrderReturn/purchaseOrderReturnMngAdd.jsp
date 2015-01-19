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
								<td><label>订单编号：</label></td>
							    <td><div id="diaOrderNo">系统自动生成</div></td>
							    <td><label>供应商代码：</label></td>
							    <td>
							    	<input type="text" id="dia-SUPPLIER_CODE" name="dia-SUPPLIER_CODE" datasource="SUPPLIER_CODE" kind="dic" src="T#PT_BA_SUPPLIER:SUPPLIER_CODE:SUPPLIER_NAME{SUPPLIER_ID}:1=1 AND PART_IDENTIFY=<%=DicConstant.YXBS_01 %>" dicwidth="310" datatype="0,is_null,3000"/>
							    	<input type="hidden" id="dia-SUPPLIER_ID" name="dia-SUPPLIER_ID" datasource="SUPPLIER_ID" />
							    </td>
								<td><label>供应商名称：</label></td>
							    <td>
							    	<input type="text" id="dia-SUPPLIER_NAME" name="dia-SUPPLIER_NAME" datasource="SUPPLIER_NAME" datatype="0,is_null,3000" readonly="true" />
							    </td>
							</tr>
<!-- 							<tr>
								<td><label>原订单号：</label></td>
							    <td>
							    	<input type="text" id="dia-PURCHASE_NO" name="dia-PURCHASE_NO" datasource="PURCHASE_NO" datatype="0,is_null,30" hasBtn="true" callFunction="openPurchase('PURCHASE_ID');" readonly="true"/>
							    	<input type="hidden" id="dia-PURCHASE_ID" name="dia-PURCHASE_ID" datasource="PURCHASE_ID" />
							    </td>
							</tr>
 -->						</table>
			  </fieldset>
			</div>
			</form>
                <div class="formBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-save">保&nbsp;&nbsp;存</button></div></div></li>
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
                    <form id="diaSaveFm" method="post">
		                <input type="hidden" id="PCH_ID" name="PCH_ID" datasource="PCH_ID"/>
		                <input type="hidden" id="DTL_ID" name="DTL_ID" datasource="DTL_ID"/>
		                <input type="hidden" id="COUNT" name="COUNT" datasource="COUNT"/>
		                <input type="hidden" id="RMK" name="RMK" datasource="RMK"/>
		                <input type="hidden" id="P_ID" name="P_ID" datasource="P_ID"/>
		            </form>
		            <form id="dia-WAREHOUSE_ID_FORM" method="post">
		                <input type="hidden" id="dia-WAREHOUSE_ID" name="dia-WAREHOUSE_ID" datasource="WAREHOUSE_ID" />
		            </form>
		            <div class="panelBar">
					<ul class="toolBar">
						<li class="line">line</li>
						<li><a class="add" href="javascript:void(0);" id="addReturnParts" title=""><span>批量新增</span></a></li>
						<li class="line">line</li>
						<li><a class="icon" href="javascript:void(0);" id="btn-download" ><span>下载导入模板</span></a></li>
						<li class="line">line</li>
						<li><a class="add" href="javascript:void(0);" id="btn-import" title="确定要导入吗?"><span>导入数据</span></a></li>
					</ul>
					</div>
                    <div id="dia-part" style="">
                    <table style="display:none;width:100%;" id="dia-tab-partinfo"  name="tablist"ref="dia-part" refQuery="tab-searchPart">
                        <thead>
                        <tr>
                            <th fieldname="PART_CODE">配件代码</th>
                            <th fieldname="PART_NAME">配件名称</th>
                            <th fieldname="UNIT">计量单位</th>
                            <th fieldname="MIN_UNIT" refer="toAppendStr">最小包装</th>
                            <th fieldname="PCH_PRICE" refer="amountFormatStr" align="right" style="display:none">采购价格</th>
                            <th fieldname="AMOUNT" refer="amountFormatStr" align="right" style="display:none">金额</th>
                            <th fieldname="SL" style="display:none">可退货数</th>
                            <th fieldname="" refer="createInputBox3">库位</th>
                            <th fieldname="REMARKS" refer="myRemark">备注</th>
                            <th colwidth="150"  refer="createOpter"  >操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
                </div>
                 <form id="fm-partInfo" style="display:none;">
                    <!-- <input type="hidden" id="inId" name="inId" datasource="IN_ID"/> -->
                    <!--<input type="hidden" id="inNo" name="inNo" datasource="IN_NO"/>-->
                    <input type="hidden" id="returnId" name="returnId" datasource="RETURN_ID"/>
                    <input type="hidden" id="warehouseId" name="warehouseId" datasource="WAREHOUSE_ID"/>
                    <input type="hidden" id="warehouseCode" name="warehouseCode" datasource="WAREHOUSE_CODE"/>
                    <input type="hidden" id="warehouseName" name="warehouseName" datasource="WAREHOUSE_NAME"/>
                    <input type="hidden" id="partIds" name="partIds" datasource="PARTIDS"/>
                    <input type="hidden" id="partCodes" name="partCodes" datasource="PARTCODES"/>
                    <input type="hidden" id="partNames" name="partNames" datasource="PARTNAMES"/>
                    <input type="hidden" id="partNos" name="partNos" datasource="PARTNOS"/>
                    <input type="hidden" id="positionIds" name="positionIds" datasource="POSITIONIDS"/>
                    <input type="hidden" id="remarks" name="remarks" datasource="REMARKS"/>
                    <input type="hidden" id="diaSupplierId" name="diaSupplierId" datasource="SUPPLIER_ID"/>
                    <input type="hidden" id="diaSupplierCode" name="diaSupplierCode" datasource="SUPPLIER_CODE"/>
                    <input type="hidden" id="diaSupplierName" name="diaSupplierName" datasource="SUPPLIER_NAME"/>
                    <input type="hidden" id="dia-retCount" name="retCount" datasource="RETCOUNT"/>
                    <input type="hidden" id="detaliIds" name="detaliIds" datasource="DETAILIDS"/>
                    <input type="hidden" id="dia-yAmounts" name="dia-yAmounts" datasource="YAMOUNT"/>
                </form>
                <div class="formBar">
                    <ul>
                        <li><div class="button"><div class="buttonContent"><button type="button" name="btn-pre">上&nbsp;一&nbsp;步</button></div></div></li>
                        <li id="dia-contror" style="display:none"><div class="button"><div class="buttonContent"><button type="button" id="btn-report" >提&nbsp;&nbsp;报</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button class="close" id="closeBtn" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
                    </ul>
                </div>
            </div>
        </div>
        <form id="exportFm" method="post" style="display:none">
		<input type="hidden" id="params" name="data"></input>
		</form>
        <div class="tabsFooter">
            <div class="tabsFooterContent"></div>
        </div>
    </div>
</div>

<script type="text/javascript">
/* 取父页面传过的参数确定新增或者修改 */
    var diaAction = "<%=action%>";
    var diaUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseReturn/PurchaseOrderReturnMngAction";
    var flag = true;
    $(function () {
    	 if (diaAction != "1") {
         	$("#dia-contror").show();
             var selectedRows = $("#tab-returnOrder_info").getSelectedRows();
             setEditValue("dia-fm-orderEdit", selectedRows[0].attr("rowdata"));
             $("#diaOrderNo").html(selectedRows[0].attr("RETURN_NO"));
         } 
    	var iH = document.documentElement.clientHeight;
        $(".tabsContent").height(iH - 20);
        $("#dia-part").height(iH-90);
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
                        alertMsg.warn('请先保存退货单信息!');
                        $tabs.switchTab(parseInt($tabs.attr("currentIndex"))-1);
                    } else {
                        if(flag){
                            searchPart();
                        }
                        flag = false;
                    }
                    break;
                default:
                    break;
            }
        });
        //保存基本信息按钮响应
        $("#btn-save").bind("click", function(event){
			var $f = $("#dia-fm-orderEdit");
			if (submitForm($f) == false) return false;
			var sCondition = {};
			sCondition = $("#dia-fm-orderEdit").combined(1) || {};
			if(diaAction == 1)
			{
				var addUrl = diaUrl + "/purchaseReturnOrderInsert.ajax";
				doNormalSubmit($f,addUrl,"btn-save",sCondition,diaInsertCallBack);
			}else	
			{
				var updateUrl = diaUrl + "/purchaseReturnOrderUpdate.ajax";
				doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
			}
		});
        //提报按钮响应
        $("#btn-report").bind("click", function(event){
			var $f = $("");
			var retId=$("#dia-RETURN_ID").val();
			if (submitForm($f) == false) return false;
			var sCondition = {};
			sCondition = $f.combined(1) || {};
			var addUrl = diaUrl + "/purRetSubmit.ajax?returnId="+retId;
			doNormalSubmit($f,addUrl,"btn-report",sCondition,reportCallBack);
			
		});
    	//关闭按钮响应
    	$("button.close").click(function(){
    		$.pdialog.closeCurrent();
    		return false;
    	});
    	$("#btn-download").bind("click",function(){
/*     		var purchaseId=$("#dia-PURCHASE_ID").val();
    		$("#exportFm").attr("action",diaUrl+"/download.do?purchaseId="+purchaseId+"");
    		$("#exportFm").submit(); */
    		var url = encodeURI("<%=request.getContextPath()%>/jsp/dms/common/download.jsp?filename=pchRetPart.xls");
            window.location.href = url;
    		
    	});
    	$('#btn-import').bind('click', function () {
            if(!$('#dia-RETURN_ID').val()){
                alertMsg.warn('请先保存基本信息!');
                return;
            }
            //9:表示最大列，3：表示有效数据是第几行开始，第一行是1
            //最后一个参数表示 导入成功后显示页
            importXls("PT_BU_PCH_RETURN_PART_TMP",$('#dia-RETURN_ID').val(),4,3,"/jsp/dms/oem/part/purchase/purchaseOrderReturn/importSuccess.jsp");
        });
    	 $('#addReturnParts').bind('click',function(){
            var doptions = {max: false, width: 900, height: 500, mask: true, mixable: true, minable: true, resizable: false, drawable: true};
            $.pdialog.open(webApps + "/jsp/dms/oem/part/purchase/purchaseOrderReturn/purchaseOrderPartSel.jsp", "partSelWin", "配件信息查询", doptions);
        });
    });
  	//删除操作
    var $diaRow;
    function doDiaDelete(rowobj) {
        $diaRow = $(rowobj);
        var dtlIds="";
        $diaRow.find("div[name='WPS']").each(function(){
	    	var $span = $(this).find("span:first");
			var dtlId = $span.attr("detailIds");
			dtlIds += dtlIds.length?"," + dtlId:dtlId;
    	});
    	var url = diaUrl + "/deleteParts.ajax?dtlIds="+dtlIds;
        sendPost(url, "", "", diaDeleteCallBack, "true");
    }
  //删除回调
    function diaDeleteCallBack(res) {
        try {
            if ($diaRow)
                $("#dia-tab-partinfo").removeResult($diaRow);
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }
 	 //创建删除和保存按钮
    function createOpter(obj)
    {
      obj.html("<A class=op title=[保存] onclick='doDiaUpdate(this.parentElement.parentElement)' href='javascript:void(0);'>[保存]</A><A class=op title=[删除] onclick='doDiaDelete(this.parentElement.parentElement)' href='javascript:void(0);'>[删除]</A>");
    	   
    }
    function afterDicItemClick(id,$row){
		var ret = true;
		if(id == "dia-SUPPLIER_CODE")
		{
			$("#"+id).attr("SUPPLIER_ID",$row.attr("SUPPLIER_ID"));
			$("#"+id).attr("code",$row.attr("SUPPLIER_CODE"));
			$("#dia-SUPPLIER_NAME").val($("#"+id).val());
			$("#dia-SUPPLIER_CODE").val($("#"+id).attr("code"));
			$("#dia-SUPPLIER_ID").val($("#"+id).attr("SUPPLIER_ID"));
			
		}
		if(id == 'dia-WAREHOUSE_CODE'){
	        $('#dia-WAREHOUSE_ID').val($row.attr('WAREHOUSE_ID'));
	        $('#dia-WAREHOUSE_NAME').val($row.attr('WAREHOUSE_NAME'));
	        $('#dia-WAREHOUSE_TYPE').val($row.attr('WAREHOUSE_TYPE'));
	        $('#dia-ORDER_ID').val('');
	        $('#dia-ORDER_NO').val('');
	        $('#dia-SUPPLIER_ID').val('');
	        $('#dia-SUPPLIER_CODE').val('');
	        $('#dia-SUPPLIER_NAME').val('');
	        $('#dia-BUYER').val('');
	        $('#dia-BUYER_SV').val('');
	    }
	    if(id.indexOf('WA_') == 0){
	        var $obj = $('#'+id);
	        var $tr = $obj.parent().parent().parent();
	        var partId = $tr.attr('PART_ID');
	        var areaId = $row.attr('A.AREA_ID');
	        var dicSql = 'T#PT_BA_WAREHOUSE_PART_RL A,PT_BA_WAREHOUSE_POSITION B:A.POSITION_CODE:A.POSITION_NAME{A.POSITION_ID,A.POSITION_NAME}:1=1';
	        dicSql+=' AND A.POSITION_ID = B.POSITION_ID';
	        dicSql+=' AND B.AREA_ID = '+areaId;
	        dicSql+=' AND A.PART_ID = '+partId+' ORDER BY B.POSITION_CODE';
	        var $wp = $('#WP_'+id.substr(3));
	        $wp.attr('code','');
	        $wp.attr('value','');
	        $wp.attr('POSITION_ID','');
	        $wp.attr('src',dicSql);
	        $obj.attr('AREA_ID',areaId);
	    }
	    if(id.indexOf('WP_')==0){
	        var $obj = $('#'+id);
	        var positionId = $row.attr('A.POSITION_ID');
	        $obj.attr('POSITION_ID',positionId);
	    }
		return ret;
	}
	//新增方法回调
    function diaInsertCallBack(res)
    {
    	var rows = res.getElementsByTagName("ROW");
    	try
    	{
    		var RETURN_ID = getNodeText(rows[0].getElementsByTagName("RETURN_ID").item(0));
    		var orderNo = getNodeText(rows[0].getElementsByTagName("RETURN_NO").item(0));
            $('#dia-RETURN_ID').val(RETURN_ID);
            $("#diaOrderNo").html(orderNo);
    		$("#tab-returnOrder_info").insertResult(res,0);
    		
    		diaAction = 2;
    		if($("#tab-order_info_content").size()>0){
    			$("td input[type=radio]",$("#tab-order_infocontent").find("tr").eq(0)).attr("checked",true);			
    		}else{
    			$("td input[type=radio]",$("#tab-order_info").find("tr").eq(0)).attr("checked",true);
    		}
    	}catch(e)
    	{
    		alertMsg.error(e);
    		return false;
    	}
    	return true;
    }
	//修改方法回调
    function diaUpdateCallBack(res)
    {
    	try
    	{
    		$("#dia-contror").show();
        	var searchContractPartUrl = diaUrl + "/returnPartSearch.ajax?RETURN_ID="+$("#dia-RETURN_ID").val()+"&SUPPLIER_ID="+$("#dia-SUPPLIER_ID").val();
       		var $f = $("#dia-fm-orderEdit");
       		var sCondition = {};
       	    sCondition = $f.combined() || {};
       	    doFormSubmit($f, searchContractPartUrl, "", 1, sCondition, "dia-tab-partinfo");
    	}catch(e)
    	{
    		alertMsg.error(e);
    		return false;
    	}
    	return true;
    }
     //查询采购订单配件
    function searchPart(){
    	$("#dia-contror").show();
    	var searchContractPartUrl = diaUrl + "/returnPartSearch.ajax?RETURN_ID="+$("#dia-RETURN_ID").val()+"&SUPPLIER_ID="+$("#dia-SUPPLIER_ID").val();
   		var $f = $("#dia-fm-orderEdit");
   		var sCondition = {};
   	    sCondition = $f.combined() || {};
   	    doFormSubmit($f, searchContractPartUrl, "", 1, sCondition, "dia-tab-partinfo");
    } 
    function addReturnParts() {
        var doptions = {max: false, width: 900, height: 500, mask: true, mixable: true, minable: true, resizable: false, drawable: true};
        $.pdialog.open(webApps + "/jsp/dms/oem/part/purchase/purchaseOrderReturn/purchaseOrderPartSel.jsp", "partSelWin", "配件信息查询", doptions);
    }
    //删除配件回调方法
    function  deleteCallBack(res)
    {
    	try
    	{
    		if($row) 
    			$("#dia-tab-partinfo").removeResult($row);
    	}catch(e)
    	{
    		alertMsg.error(e);
    		return false;
    	}
    	return true;
    }
    var row;
    //提报回调方法
   	function reportCallBack(res){
   		try
   		{
 				var $row =$("#tab-returnOrder_info").getSelectedRows();
 				if($row[0]){
 					$("#tab-returnOrder_info").removeResult($row[0]);
 					$("button.close").click();
 				}
   		}catch(e)
   		{
   			alertMsg.error(e);
   			return false;
   		}
   		return true;
   	}
	/*TD中放大镜按钮响应事件  */
	function openPurchase()
	{
		if(!$("#dia-SUPPLIER_ID").val()){
			alertMsg.warn('请先选择供应商!');
		}else{
			var options = {width:750,height:400,max:false,mask:true,mixable:false,minable:false,resizable:true,drawable:true}; 
			var url = webApps +"/jsp/dms/oem/part/purchase/purchaseOrderReturn/purchaseOrderSel.jsp";
			$.pdialog.open(url, "purchaseOrderSel", "原采购单查询", options);
		}
		
	}
	function SelCallBack(obj)
	{
		$("#dia-PURCHASE_NO").val(obj.getAttribute("SPLIT_NO"));//原订单编号
		$("#dia-PURCHASE_ID").val(obj.getAttribute("SPLIT_ID"));//原订单ID
	}
	
	
	function toAppendStr(obj){
		var $tr =$(obj).parent();
		return $tr.attr("MIN_PACK")+"/"+$tr.attr("MIN_UNIT_sv");
	}
	function amountFormatStr(obj){
		return "<div style='text-align:right;'>"+amountFormatNew($(obj).html())+"</div>";
	}
	function myOrderCount(obj){
		return "<input type='text' style='width:50px;' name='myCount' value='"+$(obj).html()+"' onblur='doMyCountBlur(this)' maxlength='6'/>";
	}
	function myRemark(obj){
		return "<input type='text' name='myCount' value='"+$(obj).html()+"'/>";
	}
	function doMyCountBlur(obj){
		var $obj = $(obj);
	    if($obj.val() == "")
	        return false;
	    if($obj.val() && !isCount($obj))
	    {
	        alertMsg.warn("请正确输入订购数量！");
	        return false;
	    }
	    var $tr = $(obj).parent().parent().parent();
	    var orderCount = $tr.find("td").eq(7).find("input:first").val();
		var unitPrice = $tr.find("td").eq(6).text();
		var amount = parseInt(orderCount)*removeAmountFormat(unitPrice);
		$tr.find("td").eq(8).html("<div style='text-align:right;'>"+amountFormatNew(amount.toFixed(2))+"</div>");
	}
	function isCount($obj)
	{
	    var reg = /^\+?[1-9][0-9]*$/;
	    if(reg.test($obj.val()))
	    {
	        return true;
	    }else
	    {
	        return false;
	    }
	}
	//function doMyInputDblclick(obj)
	//{
		//var $obj = $(obj);
		//$obj.find("input:first").attr("disabled",false);
	    //$obj.find("input:first").css("background","#fff");
	    //$obj.find("input:first").focus();
	//}
	function savePartCountCallBack(res){
		try {
			var orderCount = $rowpart.find("td").eq(7).find("input:first").val();
			var unitPrice = $rowpart.find("td").eq(6).text();
			var amount = parseInt(orderCount)*removeAmountFormat(unitPrice);
			$rowpart.find("td").eq(8).html("<div style='text-align:right;'>"+amountFormatNew(amount.toFixed(2))+"</div>");
	    } catch (e) {
	        alertMsg.error(e);
	        return false;
	    }
	    return true;
	}
	//重新添加有库位信息的退货明细
	function doDiaUpdate(rowobj)
	{
		var remark=$(rowobj).find("td").eq(9).find("input:first").val();
		var retCount="";//当前退货数量
		var yAmounts="";//保存之前的退货数量
		var dtlIds="";//明细ID
		var positionIds="";//当前库位ID
		var flag=false;
		$(rowobj).find("div[name='WPS']").each(function()
		{
			var $span = $(this).find("span:first");
			var $input = $(this).find("input:first");
			if($input.val().length > 0)
			{
				if($input.val()!=0){
					if(!isNumV($input.val()))
					{
						alertMsg.warn("请正确输入的退货数量.");
						return false;
					}
				}
			}
			//校验出库数量<=可用数量
			var avaCount = $span.attr("avCount");
			var yAmount = $span.attr("yAmount");
			var dtlId = $span.attr("detailIds");
		   	if(parseInt($input.val(),10) > parseInt(avaCount,10))
		   	{
		   		alertMsg.warn("退货数量不能大于可用库存.");
		   		flag=true;
		   		return false;
		   	}
		   	if(yAmount!=$input.val()){
		   		positionIds += positionIds.length?"," + $span.attr("wpId"):$span.attr("wpId");
		   		yAmounts += yAmounts.length?"," + yAmount:yAmount;
		   		retCount += retCount.length?"," + $input.val():$input.val();
		   		dtlIds += dtlIds.length?"," + dtlId:dtlId;
		   	}
		});
		if(flag)//当存在错误时不执行保存方法
		{
			return false;
		}else
		{
			if(positionIds=="")
			{
				alertMsg.warn("退货数据未发生变化,无需保存.");
				return false;
			}else
			{
				$("#positionIds").val(positionIds);
				$("#remarks").val(remark);
				$("#dia-retCount").val(retCount);
				$("#dia-yAmounts").val(yAmounts);
				$("#detaliIds").val(dtlIds);
				var url = diaUrl+"/retAmountInsert.ajax";
		        //获取需要提交的form对象
		        var $f = $("#fm-partInfo");
		        //submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
		        if (submitForm($f) == false) return false;
		        var sCondition = {};
		        //将需要提交的内容拼接成json
		        sCondition = $f.combined(1) || {};
		        doNormalSubmit($f, url, "btn-stockOut", sCondition, diaUpdateCallBack);
			}
		}
	}
	//库位框移开事件
	function doWpBlur(obj)
	{
		var $obj = $(obj);
		var $tr = $obj;
		while($tr.get(0).tagName != "TR")
			$tr = $tr.parent();
		if($obj.val().length > 0)
		{
			if(!isNumV($obj.val())){
				alertMsg.warn("请正确输入的退货数量.");
				return false;
			}
		}
		//校验输入数量：退货数量<= 可退货数量
		var $div = $tr.find("div[name='WPS']");
		//可退货数量
		var sl = $tr.attr("SL");
		var avaCount = $obj.attr("avCount");
		var count = 0;
		count=$obj.val();
/* 		if(sl-count<0)
		{
			alertMsg.warn("退货数量不能大于可退货数量.");
			return false;
		} */
		if(avaCount-count<0)
		{
			alertMsg.warn("退货数量不能大于可用库存.");
			return false;
		}
	}
	function isNumV(val)
	{
	    var reg = /^[1-9]\d*$/;
	    if(reg.test(val))
	    {
	        return true;
	    }else
	    {
	        return false;
	    }
	}
	
	//将出库数量字段渲染为文本框
	function createInputBox3(obj)
	{
		var $obj = $(obj);
	    var $tr = $obj.parent();
	    var partId = $tr.attr('PART_ID');
	    var yRetAmounts = $tr.attr('YRETURN_AMOUNT');
	    var avaAmounts = $tr.attr('AVAILABLE_AMOUNT');
	    var detailId = $tr.attr('DETAIL_ID');
	    //设置库位选择
	    var pIds = $tr.attr("POSITION_IDS");
	    var pCodes = $tr.attr("POSITION_CODES");
	    var pNames = $tr.attr("POSITION_NAMES");
	    var s = "";
	    var detailIds="";
	    if(pIds){
	    	var pId = pIds.split(",");
	        var pName = pNames.split(",");
	        var pCode = pCodes.split(",");
	        detailIds=detailId.split(",");
	        var yRetAmount = yRetAmounts.split(",");//原退货数量集合
	        var avaAmount = avaAmounts.split(",");//可用的数量集合
	    	for(var i=0;i<pId.length;i++)
	        { 
	    		if(yRetAmount[i]==null){
	    			yRetAmount[i]=0;
	    		}
	    		var turnAmount=parseInt(yRetAmount[i],10)+parseInt(avaAmount[i],10);//真实的可用库存
	    		s += "<div  name='WPS' style='line-height:23px;width:99%;border-bottom:1px solid red'>";
	        	s += "<span id='WP_'"+partId+"_"+pId[i]+" name='WAREHOUSE_POSITION_CODE' wpId='"+pId[i]+"' wpCode='"+pCode[i]+"'detailIds='"+detailIds[i]+"'avCount='"+turnAmount+"'yAmount="+yRetAmount[i]+" style='float:left;padding-top:3px;margin-right:2px;'>"+pCode[i]+"&nbsp;&nbsp;&nbsp;可退货数："+turnAmount+"</span>&nbsp;&nbsp;";
	        	s += "<input id='WPS_'"+partId+"_"+pId[i]+"' onblur='doWpBlur(this);' name='WAREHOUSE_POSITION_CODE_COUNT' value='"+yRetAmount[i]+"' style='width:30px;padding-top:-5px;' /></div>";
	        }
	        $tr.height(28*(pId.length));
	        $tr.css("line-height",26*(pId.length));
	    }
	    return "<div>"+s+"</div>";
	}
</script>