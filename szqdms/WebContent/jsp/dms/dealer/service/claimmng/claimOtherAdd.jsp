<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="dia-layout"  style="overflow:auto;">
  <div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-dia-other-search">
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-dia-other-search">
					<input type="hidden" id="dia-di-otherStatus" name="dia-di-otherStatus" datasource="O.STATUS" value="100201" />	
					<tr>
						<td><label>其他项目代码：</label></td>
					    <td><input type="text" id="dia-in-costsCode" name="dia-in-costsCode" datasource="O.COSTS_CODE" datatype="1,is_null,30" operation="like" /></td>
					    <td><label>其他项目名称：</label></td>
					    <td><input type="text" id="dia-in-costsName" name="dia-in-costsName" datasource="0.COSTS_NAME" datatype="1,is_null,30" operation="like" /></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-dia-other-search" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="diaConfirm" onclick="diaDoSelectOther();">确&nbsp;&nbsp;定</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" class="close">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page-dia-other" >
			<table style="display:none;width:100%;" id="tab-other-list" name="tablist" ref="page-dia-other" refQuery="fm-dia-other-search" >
					<thead>
						<tr>
							<th type="multi" name="XH" unique="COST_ID" style=""></th>
							<th fieldname="COSTS_CODE" >其他项目代码</th>
							<th fieldname="COSTS_NAME"  >其他项目名称</th>
							<th fieldname="COSTS_AMOUNT"  refer="createInputSt">费用</th>
						</tr>
					</thead>
			</table>
			</div>
	     </div>
     	<div id="di_hidInfo" style="display:none">
               <table style="width:100%;">
                   <tr>
                       <td>
                           <textarea style="width:80%;height:26px;display:" id="val0" name="multivals" column="1" readOnly></textarea>
                           <textarea style="width:80%;height:26px;display:" id="val1" name="multivals" readOnly></textarea>
                       </td>
                   </tr>
               </table>
               	<form id="fm-hidInfo">
					<input type="hidden" id="costIds" datasource="COST_IDS"/>
					<input type="hidden" id="instorAmounts" datasource="INSTOR_AMOUNTS"/>
   				 </form>
          </div>
	</div>
<script type="text/javascript">
var diaClaimOtherUrl="<%=request.getContextPath()%>/service/claimmng/WorkOrderMngAction";
$(function(){
		doDiaSearchOther();
	$("#btn-dia-other-search").bind("click",function(event){
		doDiaSearchOther();
	});
	
});
function doDiaSearchOther()
{
	var $f = $("#fm-dia-other-search");//获取页面提交请求的form对象
	var sCondition = {};//定义json条件对象
	sCondition = $f.combined() || {};
	var diaClaimOtherSearchUrl=diaClaimOtherUrl+"/searchOther.ajax?claimId="+$("#dia-claimId").val();
	doFormSubmit($f,diaClaimOtherSearchUrl,"btn-dia-other-search",1,sCondition,"tab-other-list");
}
//费用金额
function createInputSt(obj)
{
    return "<input type='text' name='AMOUNT' width='10px' onblur='doInputStBlur(this)'  value='"+obj.text()+"' />";
}
//input框入库数量焦点移开事件 步骤一
function doInputStBlur(obj){
	 var $obj = $(obj);
	 var $tr = $obj;
     while($tr[0].tagName != "TR")
    	$tr = $tr.parent();
    
    var checkObj = $("input:first",$tr.find("td").eq(1));
    var s = $obj.val();
    if(s)
    {
    	//alert(is_money($obj));
    	if(!isMoney($obj)) 
    	{
    		return true;
    	}else
    	{
    		checkObj.attr("checked", true);
    	}
        
    }
	doCheckbox(checkObj);
}
function doCheckbox(checkbox) {
	var $tr = $(checkbox);
	while($tr[0].tagName != "TR")
    {
		$tr = $tr.parent();
    }
    var sl = $tr.find("td").eq(4).find("input:first").val();
	if(sl)
	{
		var arr = [];
	    arr.push($(checkbox).val());
	    arr.push(sl);
	    multiSelected($(checkbox), arr,$("#di_hidInfo"));  
	}
    
}
function isMoney(handle) {
	var pattern = /^[\-\+]?([0-9]\d*|0|[1-9]\d{0,2}(,\d{3})*)(\.\d+)?$/;
	if (!pattern.exec(handle.val())) {
		alertMsg.info("只能输入数字,0不能开头,小数最多两位");
		return false;
	} else {
		return true;
	}
}
function diaDoSelectOther()
{
	var costIds = $('#val0').val();
    if(!costIds)
    {
        alertMsg.warn('请选择其他项目');
        return false;
    }else
    {
    	$("#costIds").val(costIds);
		$("#instorAmounts").val($("#val1").val());
    	var $f = $("#fm-hidInfo");
        var sCondition = {};
        sCondition = $f.combined(1) || {};
        var addUrl =diaClaimOtherUrl+"/insertOther.ajax?claimId="+$("#dia-claimId").val()+"&outId="+$("#dia-outId").val();
        doNormalSubmit($f, addUrl, "diaConfirm", sCondition, insertOtherCallBack);
    }
}
function insertOtherCallBack(res)
{
	try
	{	
		var rows = res.getElementsByTagName("ROW");
		var costAmount=0;
		if(rows && rows.length > 0)
		{
			var objXML;
			if (typeof(rows[0]) == "object") objXML = rows[0];
			else objXML = $.parseXML(rows[0]);
			costAmount =getNodeValue(objXML, "OTHER_COSTS", 0);
		}
		$("#dia-in-qtf").val(costAmount);
		diaCountOutCost();
		$.pdialog.closeCurrent();
	}catch(e)
	{
		alertMsg.error(e.description);
		return false;
	}
	return true;
	
}
</script>
</div>

