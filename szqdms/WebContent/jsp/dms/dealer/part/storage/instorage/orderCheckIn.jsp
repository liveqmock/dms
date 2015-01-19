<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="layout" style="width:100%;">
	<div class="page">
		<div class="pageHeader" >
			<form method="post" id="dib-fm-contract">
			    <input  type="hidden"  id="order_id" name="order_id" datasource="ORDER_ID"  value=""/>
			    <input  type="hidden"  id="diaShipId" name="diaShipId" datasource="SHIP_ID"  value=""/>
				<fieldset>
				<legend align="right"><a onclick="onTitleClick('order_info')">&nbsp;订单发运信息&gt;&gt;</a></legend>
				<table class="editTable" id="order_info">
				
					<tr>
						<td><label>订单编号：</label></td>
					    <td><input type="text" id="in-ddbh" readonly name="in-ddbh" datasource="ORDER_NO" datatype="1,is_null,30" value=""/></td>
						<td><label>订单类型：</label></td>
					    <td>
					    	<input type="text" id="in-ddlx" name="in-ddlx" readonly  datasource="ORDER_TYPE" datatype="1,is_null,30" value=""/>
					    </td>
					    <td><label>提报日期：</label></td>
					   <td>
				    		<input type="text" id="in-apply-date"  name="in-apply-date" readonly style="width:75px;" datasource="APPLY_DATE" datatype="1,is_null,30" value="" />
				   	   </td>
					</tr>
					<tr>
					   <td><label>供货库：</label></td>
					   <td><input type="text" id="in-check-user" name="in-check-user" readonly datasource="WAREHOUSE_NAME" datatype="1,is_null,100" value=""/></td>
					   <td><label>审核员：</label></td>
					   <td><input type="text" id="in-check-user" name="in-check-user" readonly datasource="CHECK_USER" datatype="1,is_null,30" value=""/></td>
					</tr>
				</table>
				</fieldset>
			</form>
		</div>
		<div class="pageHeader" >
	 	<form method="post" id="dib-fmserch-contract">
			
		</form> 
			 <form id="fm-orderInfo">
			  	<input type="hidden" id="dtl_ids" name="dtl_ids" datasource="DTL_ID"/>
			 	<input type="hidden" id="part_Ids" name="part_Ids" datasource="PART_ID"/>
			 	<input type="hidden" id="part_Codes" name="part_Codes" datasource="PART_Code"/>
			 	<input type="hidden" id="part_Names" name="part_Names" datasource="PART_Name"/>
                <!-- <input type="hidden" id="part_Nos" name="part_Nos" datasource="PART_NO"/> -->
                <input type="hidden" id="sign_counts" name="sign_counts" datasource="SIGN_COUNT"/>
                <input type="hidden" id="diaSupplierIds" name="diaSupplierIds" datasource="SUPPLIER_IDS"/>
                <input type="hidden" id="diaSupplierCodes" name="diaSupplierCodes" datasource="SUPPLIER_CODES"/>
                <input type="hidden" id="diaSupplierNames" name="diaSupplierNames" datasource="SUPPLIER_NAMES"/>
                <input type="hidden" id="dib-order_id" name="dib-order_id" datasource="ORDER_ID"  value=""/>
                <input type="hidden" id="dib-order_no" name="dib-order_no" datasource="ORDER_NO"  value=""/>
                <input type="hidden" id="shipId" name="shipId" datasource="SHIP_ID"/>
                <input type="hidden" id="dib-orderCheckDan" name="dib-orderCheckDan" datasource="REMARKS"  value=""/>
            </form>
		</div>
		<div class="pageContent" >
			<div id="dib-orderxx">
				<table style="width:100%;" id="dib-mxlb" name="tablist" multivals="partVals"  ref="dib-orderxx" >
				<thead>
					<tr>
						<th fieldname="PART_ID" style="display: none;">配件ID</th>
						<th fieldname="PART_CODE">配件代码</th>
						<th fieldname="PART_NO" style="display:none">配件图号</th>
						<th fieldname="PART_NAME" colwidth="100">配件名称</th>
						<th fieldname="UNIT">单位</th>
						<th fieldname="MIN_PACK" refer="toAppendStr">最小包装</th>
						<th fieldname="SUPPLIER_NAME" refer="ifSupplier">供应商</th>
						<th fieldname="AUDIT_COUNT">应发数量</th>
						<th fieldname="DELIVERY_COUNT">实发数量</th>
						<th fieldname="DELIVERY_COUNT" refer="createInputBox"  colwidth="70" >验收入库数量</th>
					</tr>
				</thead>
		</table>
		</div>
		<div class="formBar" style="height:80px;">
			<ul>
				<li style="align:right;"><div >验收回执：<textarea id="orderCheckDan" style="width:700px;" rows="3" value=""/></div></li>
				<li><div class="button"><div class="buttonContent"><button id="dib-save" type="button">验收入库</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" id="dib-close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
</div>
</div>
<script type="text/javascript">
//变量定义
//查询提交方法
var searchurl = "<%=request.getContextPath()%>/part/storageMng/enterStorage/InStorageAction/orderXxsearch.ajax";
//初始化方法
$(function(){
	
	$("#dib-orderxx").attr("layoutH",document.documentElement.clientHeight-240);
	var selectedRows = $("#tab-contract").getSelectedRows();
	setEditValue("dib-fm-contract",selectedRows[0].attr("rowdata"));
	var order_id= $("#order_id").val();
	$("#dib-order_no").val(selectedRows[0].attr("ORDER_NO"));
	var $f = $("#dib-fmserch-contract");
	var sCondition = {};
	searchurl = searchurl+"?&orderId="+order_id;
	sCondition = $("#dib-fmserch-contract").combined() || {};
	doFormSubmit($f.eq(0),searchurl,"btn-search",1,sCondition,"dib-mxlb");		
	
	$('#dib-save').bind('click',function(){
         //订单URL
         var insertOrderOverUrl = "<%=request.getContextPath()%>/part/storageMng/enterStorage/InStorageAction/orderOverInsert.ajax";
//          var $tab =  $('#dib-mxlb');
//                   var $tr = $tab.find('tbody tr');
//                   var dtl_ids     = '';
//                   var signcounts  = '';
//                   var part_ids    = '';
//                   var part_codes  = '';
//                   var part_names  = '';
//                   //var part_nos    = '';
//                   var supplierIds = '';
//                   var supplierCodes = '';
//                   var supplierNames = '';
//                   var flag="";
//                   $.each($tr,function(i,v){
//                  	 var dtlId =    $(v).attr('DTL_ID');
//                  	 var partId =   $(v).attr('PART_ID');
//                  	 var partCode = $(v).attr('PART_CODE');
//                  	 var partName = $(v).attr('PART_NAME');
//                  	 var supplierId =   $(v).attr('SUPPLIER_ID');
//                 	 var supplierCode = $(v).attr('SUPPLIER_CODE');
//                 	 var supplierName = $(v).attr('SUPPLIER_NAME');
//                  	 //var partNo =   $(v).attr('PART_NO');
//                  	 var signcount = $tr.eq(i).find('input').val();
//                  	 if(!dtl_ids){
//                  		 dtl_ids=dtlId;
//                  	 }else{
//                  		 dtl_ids += ','+dtlId;
//                  	 }
//                  	if(!part_ids){
//                  		part_ids=partId;
//                 	 }else{
//                 		 part_ids += ','+partId;
//                 	 }
//                  	if(!part_codes){
//                  		part_codes=partCode;
//                 	 }else{
//                 		 part_codes += ','+partCode;
//                 	 }
//                  	if(!part_names){
//                  		part_names=partName;
//                 	 }else{
//                 		 part_names += ','+partName;
//                 	 }
//                  	if(!part_names){
//                  		part_names=partName;
//                 	 }else{
//                 		 part_names += ','+partName;
//                 	 }
//                  	//if(!part_nos){
//                  		//part_nos=partNo;
//                 	 //}else{
//                 	//	 part_nos += ','+partNo;
//                 	 //}
// 			         if(!supplierIds){
// 			        	 supplierIds=supplierId;
// 			         }else {
// 			        	 supplierIds+= ','+supplierId;
// 			         }
// 			         if(!supplierCodes){
// 			        	 supplierCodes=supplierCode;
// 			         }else {
// 			        	 supplierCodes+= ','+supplierCode;
// 			         }
// 			         if(!supplierNames){
// 			        	 supplierNames=supplierName;
// 			         }else {
// 			        	 supplierNames+= ','+supplierName;
// 			         }
// 			         if(signcount=="")
// 	                 {
// 	                     flag='0';
// 	                 }
// 			         if(!signcounts){
// 			        	 signcounts=signcount;
// 			         }else{
// 			        	 signcounts+=','+signcount;
// 			         }
//                   });
//                   if(flag == '0'){
//                 	alertMsg.warn("验收数量不能为空");
//                 	return false;
//                   }
//                   if($("#orderCheckDan").val().length==0){
//                 	alertMsg.warn("验收回执不能为空"); 
//                 	return false;
//                   }
//         	   $('#dtl_ids').val(dtl_ids);
//                $('#part_Ids').val(part_ids);
//                $('#part_Codes').val(part_codes);
//                $('#part_Names').val(part_names);
//                $('#diaSupplierIds').val(supplierIds);
//                $('#diaSupplierCodes').val(supplierCodes);
//                $('#diaSupplierNames').val(supplierNames);
//                $('#sign_counts').val(signcounts);
               $('#dib-order_id').val($('#order_id').val());
               $("#shipId").val($("#diaShipId").val());
               $('#dib-orderCheckDan').val($('#orderCheckDan').val());
               //获取需要提交的form对象
               var $f = $("#fm-orderInfo");
               //submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
               if (submitForm($f) == false) return false;
               var sCondition = {};
               //将需要提交的内容拼接成json
               sCondition = $f.combined(1) || {};
               doNormalSubmit($f, insertOrderOverUrl, "dib-save", sCondition, insertOrderCallBack);
     });
});


function createInputBox(obj)
{
	
 	var $obj = $(obj);
  	var $tr =  $obj.parent();
	var count = $tr.attr('DELIVERY_COUNT'); 
    return '<input type="text" name="sign_count" readOnly="readOnly"  value ="'+count+'" />';
}
function toAppendStr(obj){
	var $tr = $(obj).parent();
	 return $tr.attr("MIN_PACK")+"/"+ $tr.attr("MIN_UNIT_sv");
}
function ifSupplier(obj){
	var $tr = $(obj).parent();
	if($tr.attr("IF_SUPPLIER")==<%=DicConstant.SF_01%>){
		return $tr.attr('SUPPLIER_NAME');
	}else{
		return "";
	}
}
//input框焦点移开事件 步骤一
function doMyInputBlur(obj){
    var $obj = $(obj);
    if($obj.val() == "")//为空直接返回
        return ;
    if($obj.val() && !isAmount($obj))//不为空并且金额不正确
    {
        alertMsg.warn("请输入正确的数量！");
        return;
    }

}

function isAmount($obj)
{
    var reg = /^(([1-9]\d*)|0)(\.\d{1,2})?$/;
    if(reg.test($obj.val()))
    {
        return true;
    }else
    {
        return false;
    }
}



function insertOrderCallBack(res){
		var rows = res.getElementsByTagName("ROW");
    		if(rows && rows.length > 0)
    		{
    			$.pdialog.close(orderCheckIn);
    			$("#btn-search").trigger("click");
    			
    		}
    		else{
    			
    			var $f = $("#dib-fmserch-contract");
    			var sCondition = {};
    			sCondition = $("#dib-fmserch-contract").combined() || {};
    			doFormSubmit($f.eq(0),searchurl,"btn-search",1,sCondition,"dib-mxlb");
    		}
}

var orderCheckIn = $("body").data("orderCheckIn");

</script>