<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchPurchase">
		<input type="hidden" id="dia-inventory_id" name="dia-inventory_id" datasource="INVENTORY_ID"/>       
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-orderSearch">
					<!-- 定义查询条件 -->
					<tr>
					<td><label>配件代码：</label></td>
				    <td><input type="text" id="dia-PART_CODE" name="dia-PART_CODE" datasource="T.PART_CODE" datatype="1,is_null,300" operation="like" /></td>
				    <td><label>配件名称：</label></td>
				    <td>
				    	<input type="text" id="dia-PART_NAME" name="dia-PART_NAME" datasource="T.PART_NAME" datatype="1,is_null,300" operation="like"/>
				    </td>
				    <td><label>盘点结果：</label></td>
				    <td>
				    	<input type="text" id="dia-INVENTORY_RESULT" name="dia-INVENTORY_RESULT" kind="dic" src="PDJG" datasource="T.INVENTORY_RESULT" datatype="1,is_null,300" operation="like"/>
				    </td>
				</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-searchOrder" >查&nbsp;&nbsp;询</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_purchase" >
			<table style="display:none;width:100%;" id="tab-purchase_info" name="tablist" multivals="di_hidInfo" ref="page_purchase" refQuery="fm-searchPurchase" >
					<thead>
						<tr>
							<th type="multi" id="CX-XH" name="CX-XH" style="align:center;" unique="DTL_ID" ></th>
							<th fieldname="PART_CODE">配件代码</th>
							<th fieldname="PART_NAME">配件名称</th>
							<th fieldname="SUPPLIER_NAME" colwidth="70">供应商名称</th>
							<th fieldname="AREA_CODE">库区代码</th>
							<th fieldname="POSITION_NAME">库位名称</th>
							<th fieldname="WHOUSE_KEEPER">库管员</th>
							<th fieldname="PLAN_PRICE">计划价</th>
							<th fieldname="PAPER_COUNT" colwidth="50">账面数量</th>
							<th fieldname="MATERIAL_COUNT" colwidth="50" refer="materialcount">实盘数量</th>
							<th fieldname="CYSL" colwidth="50">差异数量</th>
<!--							<th fieldname="AMOUNT">金额</th>-->
							<th fieldname="PAPER_AMOUNT">账面金额</th>
							<th fieldname="MATERIAL_AMOUNT">实盘金额</th>
							<th fieldname="CYJE">差异金额</th>
							<th fieldname="INVENTORY_RESULT">盘点结果</th>
							<th fieldname="REMARKS"  >差异原因</th>
							
						</tr>
					</thead>
					<tbody>
                    </tbody>
			</table>
		</div>
		<div >
		<form method="post" id="fm-tansCostsBala" class="editForm">
			<input type="hidden" id="dia-material_counts" name="dia-material_counts" datasource="MATERIAL_COUNTS" />
			<input type="hidden" id="dia-dtl_ids" name="dia-dtl_ids" datasource="DTL_IDS" />
			<input type="hidden" id="dia-remarks" name="dia-remarks" datasource="REMARKS" />
		<table id="di_hidInfo" style="display:none">
			<tr>
				<td>
<!--				<textarea id="val0" name="multivals" style="width:400px;height:10px" column="1" style="display:none" ></textarea>-->
					<textarea id="val2" name="multivals" style="width:400px;height:10px" column="1" style="display:none" ></textarea>
					<textarea id="val1" name="multivals" style="width:400px;height:10px" style="display:none" ></textarea>
				</td>
			</tr>
		</table>
		<div class="formBar">
				<ul>
					<li><div class="button"><div class="buttonContent"><button id="dia-save" type="button">保&nbsp;&nbsp;存</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button id="dia-downLoad" type="button">盘点结果导出</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button id="dia-submit" type="button">调整完成</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button class="close" id="dia-close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
				</ul>
		</div>
		</form>
		<form id="exportFm" method="post" style="display:none">
			<input type="hidden" id="params" name="data"></input>
			<input type="hidden" id="claimNoInfo" name="data"></input>
		</form>
		</div>
	</div>
<script type="text/javascript">
//查询提交方法
var searchUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PtBuInventoryAction";
var deleteUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PtBuInventoryAction/delete.ajax";
var trimUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PtBuInventoryAction/checkTrim.ajax";

//初始化方法
$(function(){
	var selectedRows = $("#tab-list").getSelectedRows();
    setEditValue("fm-searchPurchase", selectedRows[0].attr("rowdata"));
	var inventory_id = $("#dia-inventory_id").val();
	var selectUrl = searchUrl + "/partList.ajax?inventory_id="+inventory_id;
	var $f = $("#fm-searchPurchase");
	var sCondition = {};
    sCondition = $f.combined() || {};    	
	doFormSubmit($f,selectUrl,"",1,sCondition,"tab-purchase_info");	
	
	//查询方法
	$("#btn-searchOrder").bind("click",function(event){
		var addUrl = searchUrl + "/partList.ajax?inventory_id="+inventory_id;
		var $f = $("#fm-searchPurchase");
		var sCondition = {};
    	sCondition = $f.combined() || {};    	
		doFormSubmit($f,addUrl,"btn-searchOrder",1,sCondition,"tab-purchase_info");
	});
	
	$("#dia-close").bind("click",function(){
		$.pdialog.closeCurrent();
//		return false;
	});
	$("#dia-save").bind("click",function(){
		var dtl_ids=$("#val2").val();
		var material_counts=$("#val1").val();
		$("#dia-dtl_ids").val(dtl_ids);//库存盘点明细IDs
		$("#dia-material_counts").val(material_counts);//实物数量s
		if(dtl_ids&&material_counts)
		{              
		    var $f = $("#fm-tansCostsBala");
			if (submitForm($f) == false) return false;
			var sCondition = {};
			sCondition = $("#fm-tansCostsBala").combined(1) || {};
			var addUrl = searchUrl + "/batchUpdate.ajax";
			doNormalSubmit($f,addUrl,"btn-save",sCondition,diaInsertCallBack);
			
		}else
		{
			alertMsg.info("请选择记录");
			return false;
		}
		
	});
	$("#dia-downLoad").bind("click",function(){
/* 		//alertMsg.info("下载盘点结果execl.");
		var addUrl = searchUrl + "/modDownload.ajax?inventory_id="+inventory_id;
		var url = encodeURI(addUrl);
		var $f = $("#fm-searchPurchase");
		if (submitForm($f) == false) return false;
    	sCondition = $f.combined() || {};
	    window.location.href = url;
	    $("#params").val(sCondition);
	    $("#exportFm").attr("action",url);
	    $("#exportFm").submit(); */
		var $f = $("#fm-searchPurchase");
		if (submitForm($f) == false) return false;
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/part/basicInfoMng/PtBuInventoryAction/modDownload.ajax?inventory_id="+inventory_id);
		$("#exportFm").submit();
	});
	
	$("#dia-submit").bind("click",function(rowobj){
		//alertMsg.info("操作成功.");
		$.pdialog.closeCurrent();
		var url = trimUrl + "?inventory_id="+inventory_id;
		sendPost(url,"delete","",deleteCallBackf,"true");
	});
	
});


var action = "<%=action%>";
function doInit(){
		$("#dia-pdbh").html("PDBH2014050901");
		$("#dia-pdck").attr("code","1");
		$("#dia-pdck").val("本部库");
		$("#dia-pdck").attr("readonly","readonly");
		$("#dia-pdlx").attr("code","1");
		$("#dia-pdlx").val("全部");
		$("#dia-pdlx").attr("readonly","readonly");
		$("#dia-pdsj").val("2014-05-09");
		$("#dia-pdsj").attr("readonly","readonly");
		$("#dia-bz").val("测试");
		$("#dia-bz").attr("readonly","readonly");
		$("#dia-tab-pjlb").show();
		$("#dia-tab-pjlb").jTable();
}
function addPjmx(){
	alertMsg.info("可按照配件、库区等进行添加配件盘点明细.");
}

//实际费用
function materialcount(obj)
{ 	if(obj.text())
	{
	 return "<input type='text' style='width:50px;' id='materialcount'  datasource='MATERIAL_COUNT' value='"+obj.text()+"'onblur='doInputStBlur(this)' maxlength='6'/>";
	}else{
	 return "<input type='text' style='width:50px;' id='materialcount'  datasource='MATERIAL_COUNT' onblur='doInputStBlur(this)' maxlength='6'/>";
	}
	
   
}

//备注
function remarks(obj)
{
    return "<input type='text' style='width:50px;' id='remarks' datasource='REMARKS' onblur='doInputStBlur(this)' maxlength='20'/>";
}
//input框实物数量、备注焦点移开事件 步骤一
function doInputStBlur(obj){
	var $obj = $(obj);
	var $tr = $obj.parent().parent().parent();
    if($obj.val() == "")//为空直接返回
        return ;	
    if($obj.val() && !isNum($obj))//
    {
        alertMsg.warn("请输入正确的数量！");
        $obj.val("");
        return;
    }
    var checkObj = $("input:first",$tr.find("td").eq(1));
    var s = $obj.val();
    if(s)
    {
        checkObj.attr("checked", true);
    }
    doSelectedBefore($tr,checkObj,1);
}
/**
 * $tr:当前行对象jquery 入库数量 步骤二
 * @param $obj:checkbox的jQuery对象
 * @param type:
 */
function doSelectedBefore($tr,$obj,type)
{
    var $input = $tr.find("td").eq(10).find("input:first");																
    var s = "";
    if($input && $input.get(0).tagName=="INPUT")
        s = $input.val();
    else
    {
        s = $tr.find("td").eq(10).text();
    }
    doCheckbox($obj.get(0));
}
//列表复选,步骤三
function doCheckbox(checkbox) {
	var $t=$(checkbox);
	while($t[0].tagName != "TABLE")
    {
		$t = $t.parent();
    }
	if($t.attr("id").indexOf("tab-purchase_info")==0){
		var $tr = $(checkbox).parent().parent().parent();
	    var $input = $tr.find("td").eq(10).find("input:first");
	    var sl = "";
	    if($input && $input.size()>0 && $input.get(0).tagName=="INPUT"){
	    	sl = $input.val();    
	    }
	    else
	    {
	        sl = $tr.find("td").eq(10).text();
	    }
	  	//判断实物数量是否有值，如没有值，提示输入
	    var arr = [];
	    arr.push($tr.attr("DTL_ID"));
	    arr.push(sl);
	    var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
	    multiSelected($checkbox, arr,$("#di_hidInfo"));
	    if($checkbox.is(":checked"))
	  	{
	  		if(!sl)
	  		{
	  			alertMsg.warn("请输入实物数量！");
	  			$(checkbox).attr("checked",false);
	  			return false;
	  		}
	  	}
	    //设置input框显示或文本只读
	    if($checkbox.is(":checked")){
	    	$tr.find("td").eq(10).html("<div>"+sl+"</div>");
	    }else
	    {
	    	$tr.find("td").eq(10).html("<div><input type='text' name='materialcount' onblur='doInputStBlur(this);' maxlength='6' value='"+sl+"'/></div>");
	    }
	}
   
//   if($t.attr("id").indexOf("tab-purchase_info")==0){
//	   	var arr = [];
//		var $checkbox = $(checkbox);
//		var mxid = $(checkbox).val();
//		arr.push(mxid);
//		multiSelected($checkbox,arr,$("#di_hidInfo"));
//  }
}

/*
 * 翻页回显方法:步骤四
 */
function customOtherValue($row,checkVal)
{
    var $inputObj = $row.find("td").eq(10);
    var val="";
    if($("#val1").val())
    {
        var pks =$("#val2").val();
        var pk = pks.split(",");
        var re=$("#val1").val();
        var res = re.split(",");
        for(var i=0;i<pk.length;i++)
        {
            if(pk[i] == checkVal)
            {
                val = res[i];
//				alert(val);
	            break;
            }
        }
    }
    if(val)
    {
        $inputObj.html("<div>"+val+"</div>");
    }
}
function isNum($obj)
{
	var reg = /^[1-9][0-9]*$/;//正则表达式(数字)
    if(reg.test($obj.val()))
    {
        return true;
    }else
    {
        return false;
    }
}
//批量新增回调函数
function diaInsertCallBack(res)
{
	try
	{   
		$("#btn-searchOrder").trigger("click");
//		$.pdialog.closeCurrent();
		
		//清空的内容
		$("#val1").val("");
		$("#val2").val("");
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//删除回调方法
function  deleteCallBackf(res)
{
	try
	{
		//if($row) 
		$("#btn-cx").trigger("click");			
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
</script>
</div>

