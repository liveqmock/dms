<?xmsl version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchPurchase">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-orderSearch">
					<!-- 定义查询条件 -->
					<tr>
					    <td><label>配件代码：</label></td>
					    <td><input type="text" id="dia-PART_CODE" name="b.part_code" datatype="1,is_null,300" dataSource="b.part_code" operation="like" /></td>
					    <td><label>配件名称：</label></td>
					    <td><input type="text" id="dia-PART_NAME" name="b.part_name" datatype="1,is_null,300" dataSource="b.part_name" operation="like" /></td>
					    <td><label>库区代码：</label></td>
					    <td><input type="text" id="dia-AREA_CODE" name="b.area_code" datatype="1,is_null,300" dataSource="b.area_code" operation="like" /></td>
					</tr>
				</table>
				
				
				<div class="subBar">
					<ul>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-searchOrder" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-purchaseAdd" >确&nbsp;&nbsp;认</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-importExcel" >导&nbsp;&nbsp;入</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-export-index">导&nbsp;&nbsp;出</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button" id="btn-closeOrder">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_purchase" >
			<table style="display:none;width:100%;" id="tab-purchase_info" name="tab-purchase_info" ref="page_purchase" refQuery="fm-searchPurchase" multivals="yhw">
					<thead>
						<tr>
							<th type="multi" id="CX-XH" name="CX-XH" style="align:center;" unique="DTL_ID" ></th>
							<th fieldname="PART_CODE" >配件代码</th>
							<th fieldname="PART_NAME" >配件名称</th>
							<th fieldname="SUPPLIER_NAME" >供应商名称</th>
							<th fieldname="AREA_CODE" >库区代码</th>
							<th fieldname="POSITION_NAME" >库位名称</th>
							<th fieldname="USER_NAME" >库管员</th>
							<th fieldname="PLAN_PRICE" refer="amountFormat" align="right">计划价格</th>
							<th fieldname="AMOUNT" >库存数量</th>
							<th fieldname="CCJE" refer="amountFormat" align="right">库存金额</th>
<!--							<th type="link" title="操作" action="doOk" class="btnSelect">操作</th>-->
						</tr>
					</thead>
					<tbody>
                    </tbody>
			</table>
		</div>
		<div id="yhw">
		<table style="display:none">
			<tr>
				<td>
					<textarea id="val0" name="multivals" style="width:400px;height:10px" column="1" style="display:none" ></textarea>
				</td>
			</tr>
		</table>
	</div>
	<form id="exportFm" method="post" style="display:none">
		<input type="hidden" id="params" name="data"></input>
	</form>
	</div>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>	
<script type="text/javascript">
//查询提交方法
var mngUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PtBuInventoryAction";
$(function()
{
		var WAREHOUSE_ID = $("#dia-warehouse_id").val();
		var searchUrl = mngUrl+"/partSearch.ajax?warehouse_id=" + WAREHOUSE_ID; 
		var $f = $("#fm-searchPurchase");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-searchOrder",1,sCondition,"tab-purchase_info");

	$("#btn-searchOrder").bind("click", function(event){
//		var supplierId = $("#dia-SUPPLIER_ID").val();
		var WAREHOUSE_ID = $("#dia-warehouse_id").val();
		//alert(WAREHOUSE_ID);
		var searchUrl = mngUrl+"/partSearch.ajax?warehouse_id=" + WAREHOUSE_ID; 
		var $f = $("#fm-searchPurchase");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-searchOrder",1,sCondition,"tab-purchase_info");
	});
	
	$("#btn-purchaseAdd").bind("click", function(event){
	var dtl_id=$("#val0").val();
	if(dtl_id){
		var INVENTORY_ID = $("#dia-inventory_id").val();
		var INVENTORY_NO = $("#dia-inventory_no").val();
		var WAREHOUSE_ID = $("#dia-warehouse_id").val();
		
		var $f = $("#fm-searchPurchase");
		if (submitForm($f) == false) return false;
		var sCondition = {};
		sCondition = $("#fm-searchPurchase").combined(1) || {};      
		var addUrl = diaSaveAction + "/batchInsert.ajax?dtl_id="+dtl_id+"&inventory_id="+INVENTORY_ID+"&inventory_no="+INVENTORY_NO+"&warehouse_id="+WAREHOUSE_ID;
//		doFormSubmit($f,searchUrl,"btn-searchOrder",1,sCondition,"dia-tab-pjbflb");
		doNormalSubmit($f,addUrl,"btn-purchaseAdd",sCondition,insertPromPartCallBack);
//		doFormSubmit($f, searchPartUrl, "btn-search", 1, sCondition, "tab-partList");
	}else{
		alertMsg.info("请选择！");
		return false;
	}
	});
	
	
	//导入 
	$('#btn-importExcel').bind('click',function(){
		var inventory_id = $("#dia-inventory_id").val();
        importXls("PT_BU_INVENTORY_TMP",inventory_id,11,3,"/jsp/dms/oem/part/storage/checkSetup/importSuccess.jsp");
	});   
	
	// 导出
    $("#btn-export-index").click(function(){
    	var $f = $("#fm-searchPurchase");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
    	
    	var diaUrl="<%=request.getContextPath()%>/part/basicInfoMng/PtBuInventoryAction/checkDownload.ajax"
    	
		
    	var url=diaUrl+"?warehouse_id=" + WAREHOUSE_ID+"&inventory_id="+$("#dia-inventory_id").val(); 
        $("#exportFm").attr("action",url);
        $("#exportFm").submit();
    });		
	
});

//新增配件回调
    function insertPromPartCallBack(){
//    	$.pdialog.closeCurrent();
 //       searchPart($("#dia-warehouse_id").val());
         searchPart();
        $("#btn-closeOrder").trigger("click");
    }
	
	
//批量新增回调函数
function diaBatchInsertCallBack(res)
{
	try
	{	
//		$("#dia-tab-pjbflb").insertResult(res,0);
		$("#dia-tab-pjbflb").trigger("click");
		$.pdialog.closeCurrent();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}	
//金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}
	
</script>
</div>

