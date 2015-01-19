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
							<th fieldname="CYSL" colwidth="50" style="display:none">差异数量</th>
							<th fieldname="PAPER_AMOUNT" style="display:none">账面金额</th>
							<th fieldname="MATERIAL_AMOUNT" style="display:none">实盘金额</th>
							<th fieldname="CYJE" style="display:none">差异金额</th>
							<th fieldname="INVENTORY_RESULT" style="display:none">盘点结果</th>
							<th fieldname="REMARKS" refer="remarks" >差异原因</th>
							
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
					<textarea id="val3" name="multivals" style="width:400px;height:10px" style="display:none" ></textarea>
				</td>
			</tr>
		</table>
		<div class="formBar">
				<ul>
					<li><div class="button"><div class="buttonContent"><button id="dia-save" type="button">保&nbsp;&nbsp;存</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button id="dia-submit" type="button">盘点结束</button></div></div></li>
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
var inSearchUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PtBuInventoryAction";
var finishUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PtBuInventoryAction/checkOver.ajax";
var win = 
//初始化方法
$(function(){
	var selectedRows = $("#tab-list").getSelectedRows();
    setEditValue("fm-searchPurchase", selectedRows[0].attr("rowdata"));
	var inventory_id = $("#dia-inventory_id").val();
	var selectUrl = inSearchUrl + "/partCountSearch.ajax?inventory_id="+inventory_id;
	var $f = $("#fm-searchPurchase");
	var sCondition = {};
    sCondition = $f.combined() || {};    	
	doFormSubmit($f,selectUrl,"",1,sCondition,"tab-purchase_info");	
	
	//查询方法
	$("#btn-searchOrder").bind("click",function(event){
		var addUrl = inSearchUrl + "/partCountSearch.ajax?inventory_id="+inventory_id;
		var $f = $("#fm-searchPurchase");
		var sCondition = {};
    	sCondition = $f.combined() || {};    	
		doFormSubmit($f,addUrl,"btn-searchOrder",1,sCondition,"tab-purchase_info");
	});
	$("#dia-submit").bind("click",function(event){
		var url = finishUrl + "?inventory_id="+$("#dia-inventory_id").val();
		sendPost(url,"delete","",finishCallBack,"true");
	});
	
	$("#dia-close").bind("click",function(){
		$.pdialog.closeCurrent();
//		return false;
	});
	$("#dia-save").bind("click",function(){
		var dtl_ids=$("#val2").val();
		var material_counts=$("#val1").val();
		var remarks=$("#val3").val();
		$("#dia-dtl_ids").val(dtl_ids);//库存盘点明细IDs
		$("#dia-material_counts").val(material_counts);//实物数量s
		$("#dia-remarks").val(remarks);//差异原因
		if(dtl_ids&&material_counts)
		{              
		    var $f = $("#fm-tansCostsBala");
			if (submitForm($f) == false) return false;
			var sCondition = {};
			sCondition = $("#fm-tansCostsBala").combined(1) || {};
			var addUrl = inSearchUrl + "/addMCount.ajax";
			doNormalSubmit($f,addUrl,"btn-save",sCondition,diaInsertCallBack);
			
		}else
		{
			alertMsg.info("请选择记录");
			return false;
		}
		
	});
});


var action = "<%=action%>";
function doInit(){
		$("#dia-tab-pjlb").show();
		$("#dia-tab-pjlb").jTable();
}

function finishCallBack(res){
	$.pdialog.closeCurrent();
	doBackSearch();
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
function remarks(obj1)
{
	if(obj1.text())
	{
	 	return "<input type='text' style='width:50px;' id='remarks'  datasource='REMARKS' value='"+obj1.text()+"'onblur='doMyInputCheck(this)' maxlength='6'/>";
	}else{
	 	return "<input type='text' style='width:50px;' id='remarks'  datasource='REMARKS' onblur='doMyInputCheck(this)' maxlength='6'/>";
	}
	
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
function doMyInputCheck(obj){
	var $obj = $(obj);
	var $tr = $obj.parent().parent().parent();
	var checkObj = $("input:first",$tr.find("td").eq(1));
	var r = $obj.val();
	var sl =$tr.find("td").eq(10).text();
	var zmsl = $tr.find("td").eq(9).text();
	
	doSelectedBefore2($tr,checkObj,1);
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
function doSelectedBefore2($tr,$obj,type)
{
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
	    var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
	    var arr = [];
	    var sl = "";
	    if($input && $input.size()>0 && $input.get(0).tagName=="INPUT"){
	    	sl = $input.val();    
	    }
	    else
	    {
	        sl = $tr.find("td").eq(10).text();
	    }
	  	//判断实物数量是否有值，如没有值，提示输入
	    arr.push($tr.attr("DTL_ID"));
	    arr.push(sl);
	    var r = $tr.find("td").eq(16).find("input:first").val();
		var zmsl = $tr.find("td").eq("9").text();
		if(r.length==0){
			arr.push("nu");
		}else{
			arr.push(r);
		}
		
	    if($checkbox.is(":checked"))
	  	{
	  		if(!sl)
	  		{
	  			alertMsg.warn("请输入实物数量！");
	  			$(checkbox).attr("checked",false);
	  			return false;
	  		}
	  	}
	    multiSelected($checkbox, arr,$("#di_hidInfo"));
	    //设置input框显示或文本只读
	    if($checkbox.is(":checked")){
	    	$tr.find("td").eq(10).html("<div>"+sl+"</div>");
	    }else
	    {
	    	$tr.find("td").eq(10).html("<div><input type='text' name='materialcount' onblur='doInputStBlur(this);' maxlength='6' value='"+sl+"'/></div>");
	    }
	}
}

/*
 * 翻页回显方法:步骤四
 */
function customOtherValue($row,checkVal)
{
    var $inputObj = $row.find("td").eq(10);
    var $inputObj1 = $row.find("td").eq(16);
    var val="";
    var val1 = "";
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
	            break;
            }
        }
    }
    if(val)
    {
        $inputObj.html("<div>"+val+"</div>");
    }
    if($("#val3").val())
    {
        var pks =$("#val2").val();
        var pk = pks.split(",");
        var re1=$("#val3").val();
        var res1 = re.split(",");
        for(var i=0;i<pk.length;i++)
        {
            if(pk[i] == checkVal)
            {
                val1 = res[i];
	            break;
            }
        }
    }
    if(val1)
    {
        $inputObj1.html("<div>"+val+"</div>");
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
		searchPartIn();
		//清空的内容
		$("#val1").val("");
		$("#val2").val("");
		$("#val3").val("");
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
function searchPartIn(){
	var inventory_id = $("#dia-inventory_id").val();
	var addUrl = inSearchUrl + "/partCountSearch.ajax?inventory_id="+inventory_id;
	var $f = $("#fm-searchPurchase");
	var sCondition = {};
	sCondition = $f.combined() || {};    	
	doFormSubmit($f,addUrl,"btn-searchOrder",1,sCondition,"tab-purchase_info");
}
</script>
</div>

