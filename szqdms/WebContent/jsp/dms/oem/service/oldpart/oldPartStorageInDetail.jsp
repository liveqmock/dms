<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>旧件回运入库</title>
<script type="text/javascript">
var $true=true;
var diaSaveAction = "<%=request.getContextPath()%>/service/oldpartMng/OldPartCheckStorageAction";
$(function(){
	var selectedRows = parent.$("#oldPartList").getSelectedRows();
	setEditValue("dia-oldpartfm",selectedRows[0].attr("rowdata"));
	$("#dia_oldPartList").attr("layoutH",document.documentElement.clientHeight-70);
    //明细查询
	$("#dia_searchDetail").bind("click",function(){

		var $f = $("#di_oldPartFm");
		var sCondition = {};
		sCondition = $f.combined() || {};
		var url =diaSaveAction+"/returnPartSearch.ajax?orderId="+$("#orderId").val();
		doFormSubmit($f,url,"",1,sCondition,"dia_oldPartList");
	});
	//审核通过
	$("#dia_save").bind("click",function(){
		var orderId=$("#orderId").val();
       	var $f = $("#fm-hidInfo");
        if (submitForm($f) == false) return false;
        var sCondition = {};
        //将需要提交的内容拼接成json
        sCondition = $f.combined(1) || {};
        var passUrl =diaSaveAction+"/returnOldPartUpdate.ajax?orderId="+orderId;
        doNormalSubmit($f, passUrl, "dia_save", sCondition, saveCallBack);
	});
	//作废
	$("#dia_cancellation").bind("click",function(){
		var orderId=$("#orderId").val();
		var detailIds = $('#val0').val();
        if(!detailIds){
            alertMsg.warn('请选择需要入库的配件!');
        }else{
        	$("#detailIds").val($("#val0").val());
    		$("#instorAmounts").val($("#val2").val());
    		//$("#remarksHi").val($("#val2").val());
        	var $f = $("#fm-hidInfo");
	        if (submitForm($f) == false) return false;
	        var sCondition = {};
	        //将需要提交的内容拼接成json
	        sCondition = $f.combined(1) || {};
	        var cancelUrl =diaSaveAction+"/returnOldPartStorageIn.ajax?orderId="+orderId;
	        doNormalSubmit($f, cancelUrl, "dia_cancellation", sCondition, saveCallBack);
        }
	});
	//上一步
	$("button[name='btn-pre']").bind("click",function(event){
		$("#tabs").switchTab(parseInt($("#tabs").attr("currentIndex"))-1);
	});
	 //下一步
	$("button[name='btn-next']").bind("click",function(event){
	var $tabs = $("#tabs");
	switch(parseInt($tabs.attr("currentIndex")))
 	{
		case 0:
			if($true){
				var $f = $("#di_oldPartFm");
				var sCondition = {};
		    	sCondition = $f.combined() || {};
		    	var url =diaSaveAction+"/returnPartSearch.ajax?orderId="+$("#orderId").val();
				doFormSubmit($f,url,"",1,sCondition,"dia_oldPartList");
			}
			$true=false;
			break;
		case 1:
			break;
    }
 	$tabs.switchTab(parseInt($tabs.attr("currentIndex"))+1);
 	//跳转后实现方法
 	(function(ci){ 
		switch(parseInt(ci))
     	{
			case 1://第2个tab页
    	   		break;
     	   	default:
     	   		break;
     	  }
 	   })(parseInt($tabs.attr("currentIndex")));
	});
	 
	//jsp关闭,与DIV不同
	var dialog = parent.$("body").data("oldPartStorageIn");
	$("button.close").click(function(){
		parent.$.pdialog.close(dialog);
		return false;
	});
});
/* //查询回调
function callbackSearch(res,tabId,sParam){
    switch(tabId)
    {
        case "dia_oldPartList":
        	var rows = $("#dia_oldPartList").find("tr").size();
        	if(rows == 0)
        		rows = 1;
        	$("#dia_oldPart").height(document.documentElement.clientHeight-100);
        break;
    }
} */
//保存回调
function saveCallBack(res){
	try
	{
		var $f = $("#di_oldPartFm");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	var url =diaSaveAction+"/returnPartSearch.ajax?orderId="+$("#orderId").val();
		doFormSubmit($f,url,"",1,sCondition,"dia_oldPartList");
		$("#val0").val("");
		$("#val1").val("");
		$("#val2").val("");
		var result = getNodeText(res.getElementsByTagName("RESULT").item(0));
		if(result=='1'){
			var $row = parent.$("#oldPartList").getSelectedRows();//选择行
			if($row[0]){
				parent.$("#oldPartList").removeResult($row[0]);//移除选择行
				parent.$.pdialog.closeCurrent();
				return false;
			}
		}
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//入库完成回调
function saveFinalCallBack(res){
	try
	{
		var $row = parent.$("#oldPartList").getSelectedRows();//选择行
		if($row[0]){
			parent.$("#oldPartList").removeResult($row[0]);//移除选择行
			parent.$.pdialog.closeCurrent();
			return false;
		}
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//将本次促销价字段渲染为文本框
function createInputBox(obj)
{
    return '<input type="text" name="QUANTITY_IN" onblur="doMyInputBlur(this)" / >';
}
function isNum($obj)
{
	var reg = /^[0-9][0-9]*$/;//正则表达式(数字)
    if(reg.test($obj.val()))
    {
        return true;
    }else
    {
        return false;
    }
}
//input框焦点移开事件 步骤一
function doMyInputBlur(obj){
    var $obj = $(obj);
    var $tr = $obj.parent().parent().parent();
    if($obj.val() == "")//为空直接返回
        return ;
    var sfsl = $tr.find("td").eq(8).text();//配件数量
    var yfsl = $tr.find("td").eq(12).text();//已入库数量
    if($obj.val() > sfsl - yfsl )//入库数量不能大于差值
    {
        alertMsg.warn("入库数量不能大于实返配件数量与已入库数量的差，请输入正确的数量！");
        $obj.val("");
        return;
    }
    if($obj.val() && !isNum($obj))//不为空并且数量不正确
    {
        alertMsg.warn("请输入正确的数量！");
        $(obj).val("");
        return;
    }
    var $tr = $obj.parent().parent().parent();
    var checkObj = $("input:first",$tr.find("td").eq(1));
    var s = $obj.val();
    if(s)
    {
        checkObj.attr("checked", true);
    }
    doSelectedBefore($tr,checkObj,1);
}
/**
 * $tr:当前行对象jquery 步骤二
 * @param $obj:checkbox的jQuery对象
 * @param type:
 */
function doSelectedBefore($tr,$obj,type)
{
    var $input = $tr.find("td").eq(11).find("input:first");

    var s = "";
    if($input && $input.get(0).tagName=="INPUT")
        s = $input.val();
    else
    {
        s = $tr.find("td").eq(11).text();
    }
    doCheckbox($obj.get(0));
}
function doCheckbox(checkbox)
{
	var $checkbox = $(checkbox);
	var arr = [];
	while($checkbox[0].tagName != "TABLE"){
		$checkbox = $checkbox.parent();
	}
	if($checkbox.attr("id").indexOf("dia_oldPartList")==0){
		var $tr = $(checkbox).parent().parent().parent();
       	var reg = /^[0-9][0-9]*$/;//正则表达式(数字)
       	var s = "";
       	if($tr.find("td").eq(11).find("input:first").size()>0)
          	 s = $tr.find("td").eq(11).find("input:first").val();
       	else
        	   s = $tr.find("td").eq(11).text();
      	 if (!s || !reg.test(s)){
         	  alertMsg.warn("请输入正确的数量!");
          	  $(checkbox).attr("checked",false);
           	  return false;
       	}
       var arr = [];
       arr.push($tr.attr("DETAIL_ID"));
       arr.push($tr.attr("PART_CODE"));
       arr.push(s);
       var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
       multiSelected($checkbox, arr,$("#di_hidInfo"));
       //设置input框显示或文本只读
       if($checkbox.is(":checked"))
           $tr.find("td").eq(11).html("<div>"+s+"</div>");
       else
       {
           $tr.find("td").eq(11).html("<div><input type='text' name='QUANTITY' onblur='doMyInputBlur(this);' maxlength='6' value='"+s+"'/></div>");
       }
	}
}
/*
 * 翻页回显方法:步骤四
 */
function customOtherValue($row,checkVal)
{
    var $inputObj = $row.find("td").eq(11);
    var val;
    if($("#val2") && $("#val2").val())
    {
        var t = $("#val2").val();
        var pks = $("#val0").val();
        var ss = t.split(",");
        var pk = pks.split(",");
        for(var i=0;i<pk.length;i++)
        {
            if(pk[i] == checkVal)
            {
                val = ss[i];
                break;
            }
        }
    }
    if(val)
    {
        $inputObj.html("<div>"+val+"</div>");
    }
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<!-- tab页  -->
	<div class="tabs" eventType="click" id="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li ><a href="javascript:void(0)"><span>基本信息</span></a></li>
					<li ><a href="javascript:void(0)"><span>旧件回运入库</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent">
		<div class="page"> 
		<div class="pageContent" style="" >
			<form method="post" id="dia-oldpartfm" class="editForm" >
				<input type="hidden" id="orderId" name="orderId" datasource="ORDER_ID"/>
				<div align="left">
				<fieldset>
				<table class="editTable" id="dia-oldpartTab">
				    <tr>
						<td><label>渠道商代码：</label></td>
						<td><input type="text" id="orgCode" name="orgCode" datasource="ORG_CODE" datatype="1,is_null,100" value="" readonly="readonly"/></td>
						<td><label>渠道商名称：</label></td>
						<td><input type="text" id="orgName" name="orgName" datasource="ORG_NAME"  datatype="1,is_null,100" value="" readonly="readonly"/></td>
					</tr>
				    <tr>
						<td ><label>回运单号：</label></td>
						<td ><input type="text" id="orderNo" name="orderNo" datasource="ORDER_NO" value="" readonly="readonly"/></td>
						<td><label>运输方式：</label></td>
						<td><input type="text" id="transType" name="transType" datasource="TRANS_TYPE" value=""  datatype="1,is_null,30" readonly="readonly" /></td>
						<td ><label>装箱总数量：</label></td>
						<td><input type="text" id="amount" name="amount" datasource="AMOUNT" datatype="1,is_digit,10" readonly="readonly" /></td>
					</tr>
					<tr>	
						<%--<td><label>渠道回运日期：</label></td>
						<td><input type="text" id="focusDate" name="focusDate" datasource="FOCUS_DATE" value="" datatype="1,is_null,30"  readonly="readonly" /></td>	--%>
						<td><label>回运日期：</label></td>
						<td><input type="text" id="returnDate" name="returnDate" datasource="RETURN_DATE" value="" datatype="1,is_null,30"  readonly="readonly" /></td>	
						<td><label>旧件产生年月：</label></td>
						<td><input type="text" id="produceDate" name="produceDate" datasource="PRODUCE_DATE" value=""  datatype="1,is_null,30" readonly="readonly" /></td>
					</tr>
					<tr>
						<td><label>备注：</label></td>
					    <td colspan="3"><textarea class="" rows="2" id="remarks" name="remarks" datasource="REMARKS" style="width:100%" datatype="1,is_null,1000"></textarea></td>
					</tr>
				</table>
				</fieldset>
				</div>
			</form>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="button" id="next1" name="btn-next">下一步</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
		</div>
		<div class="page">
		<div class="pageHeader">
		<form id="di_oldPartFm" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="di_oldPartTab">
					<tr>
						<td><label>索赔单号：</label></td>
						<td><input type="text" id="dia_claimNo" name="dia_claimNo" datasource="D.CLAIM_NO" operation="like" datatype="1,is_null,100"  value="" /></td>
						<td><label>配件代码：</label></td>
						<td><input type="text" id="dia_partCode" name="dia_partCode" datasource="D.PART_CODE" operation="like" datatype="1,is_null,100"  value="" /></td>
						<td><label>配件名称：</label></td>
						<td><input type="text" id="dia_partName" name="dia_partName" datasource="D.PART_NAME" operation="like" datatype="1,is_null,100"  value="" /></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia_searchDetail">查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="dia_cancellation">入&nbsp;&nbsp;库</button></div></div></li>
					    <li><div class="button"><div class="buttonContent"><button type="button" id="next2" name="btn-pre">上一步</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
		</div>
		<div class="pageContent">
			<div id="dia_oldPart">
				<table style="display:none;width:100%;"  id="dia_oldPartList" multivals="di_hidInfo" name="dia_oldPartList" ref="dia_oldPart" refQuery="di_oldPartTab">
					<thead>
						<tr>
							<th type="multi" name="XH" unique="DETAIL_ID" style="display:"></th>
							<th fieldname="CLAIM_NO">索赔单号</th>
							<th fieldname="ORG_CODE">渠道商代码</th>
							<th fieldname="ORG_NAME">渠道商名称</th>
							<th fieldname="PART_CODE">配件代码</th>
							<th fieldname="PART_NAME">配件名称</th>
							<th fieldname="CLAIM_COUNT">索赔旧件总数</th>
							<th fieldname="OUGHT_COUNT">集中点实返旧件数量</th>
							<th fieldname="ACTUL_COUNT">待审数量</th>
							<th fieldname="MISS_COUNT">集中点缺失数量</th>
							<th fieldname="" colwidth="60" refer="createInputBox" >本次入库数量</th>
							<th fieldname="ALREADY_IN">已入库数量</th>
							<th fieldname="DUTYSUPPLY_CODE">责任供应商代码</th>
							<th fieldname="DUTYSUPPLY_NAME">责任供应商名称</th>
							<th fieldname="IS_MAIN">故障类别</th>
							<th fieldname="CLAIM_TYPE">保修类型</th>
							<th fieldname="MEASURES">处理方式</th>
							<th fieldname="BROKEN_REASON">质损原因</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
            
		</div>
		<div id="di_hidInfo" style="display:none">
                <table style="width:100%;">
                    <tr>
                        <td>
                            <textarea style="width:80%;height:26px;display:" id="val0" name="multivals" column="1" readOnly></textarea>
                            <textarea style="width:80%;height:26px;display:" id="val1" name="multivals" readOnly></textarea>
                            <textarea style="width:80%;height:26px;display:" id="val2" name="multivals" readOnly></textarea>
                        </td>
                    </tr>
                </table>
            </div>
	</div>
	</div>
	</div>
	<form id="fm-hidInfo">
		<input type="hidden" id="detailIds" datasource="DETAIL_IDS"/>
		<input type="hidden" id="instorAmounts" datasource="INSTOR_AMOUNTS"/>
    </form>
</div>
</body>
</html>