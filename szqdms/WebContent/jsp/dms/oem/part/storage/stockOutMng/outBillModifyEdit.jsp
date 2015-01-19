<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%@page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contentPath = request.getContextPath();
	String OUT_ID = request.getParameter("OUT_ID");
%>
<div id="dia-layout">
	<div class="page">
	<div class="pageContent" style="" >
	<form method="post" id="fm-order_Info" class="editForm" >
		<div align="left">
			<fieldset>
			<table class="editTable" id="tab-cash_Edit_Info">
				<input type="hidden" id="dia-OUT_ID" name="dia-OUT_ID" datasource="OUT_ID" />
				<tr>
					<td><label>出库单号：</label></td>
                    <td><input type="text" id="dia-OUT_NO" name="dia-OUT_NO" datasource="OUT_NO" datatype="1,is_null,30" readonly="readonly" /></td>
                    <td><label>订单号：</label></td>
                    <td><input type="text" id="dia-ORDER_NO" name="dia-ORDER_NO" datasource="ORDER_NO" datatype="1,is_null,30" readonly="readonly" /></td>
				</tr>
			</table>
			</fieldset>
		</div>
	</form>
	<div class="pageContent">
		<div id="page_order" >
			<table style="display:none;width:100%;" id="tab-order_info" name="tablist" ref="page_order" multivals="div-selectedOrder" refQuery="tab-cash_Edit_Info" pagerows="2000">
					<thead>
						<tr>
							<th type="single" name="XH" unique="PART_ID" style="display:none"></th>
							<th fieldname="PART_CODE" >配件代码</th>
							<th fieldname="PART_NAME" >配件名称</th>
							<th fieldname="PART_NO" >参图号</th>
							<th fieldname="UNIT" >单号</th>
							<th fieldname="AREA_CODE" >库区</th>
							<th fieldname="POSITION_CODE" >库位</th>
							<th fieldname="OUT_AMOUNT">数量</th>
							<th fieldname="OUT_AMOUNT" refer="myOrderCount">调整数量</th>
							<th colwidth="105" type="link" title="[保存]"  action="doDiaPartSave">操作</th>
						</tr>
					</thead>
					<tbody>
                    </tbody>
			</table>
		</div>
		<form id="diaSaveFm" method="post">
             <input type="hidden" id="O_ID" name="O_ID" datasource="OUT_ID"/>
             <input type="hidden" id="DTL_ID" name="DTL_ID" datasource="DTL_ID"/>
             <input type="hidden" id="COUNT" name="COUNT" datasource="COUNT"/>
             <input type="hidden" id="P_ID" name="P_ID" datasource="P_ID"/>
             <input type="hidden" id="POS_ID" name="POS_ID" datasource="POS_ID"/>
         </form>
	</div>
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
		</ul>
	</div>
	</div>
	</div>
</div>

<script type="text/javascript">
/* 取父页面传过的参数确定新增或者修改 */
    var id = "<%=OUT_ID%>"
    var diaUrl = "<%=request.getContextPath()%>/part/storageMng/stockOutMng/OutBillModAction";
    $(function () {
    	//关闭按钮响应
    	$("button.close").click(function(){
    		$.pdialog.closeCurrent();
    		return false;
    	});
    	var selectedRows = $("#tab-outBillList").getSelectedRows();
    	$("#dia-OUT_NO").val(selectedRows[0].attr("OUT_NO"));
    	$("#dia-ORDER_NO").val(selectedRows[0].attr("ORDER_NO"));
		//修改页面赋值
    	doSearchPart();
		
    });
    function doSearchPart(){
    	var searchUrl = diaUrl+"/searchPart.ajax?OUT_ID="+id;
   		var $f = $("#fm-order_Info");
   		var sCondition = {};
       	sCondition = $f.combined() || {};
   		doFormSubmit($f,searchUrl,"",1,sCondition,"tab-order_info");
    }
    function myOrderCount(obj){
		return "<input type='text' style='width:50px;' name='myCount' value='"+$(obj).html()+"' onblur='doMyCountBlur(this)' maxlength='6'/>";
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
	}
	//校验订购数量
	function isCount($obj)
	{
	    var reg = /^([1-9]\d*|0)(\.?\d+)?$/;
	    if(reg.test($obj.val()))
	    {
	        return true;
	    }else
	    {
	        return false;
	    }
	}
	var $rowpart;
	function doDiaPartSave(rowobj){
		$rowpart = $(rowobj);
		var $obj = $(rowobj).find("td").eq(9).find("input:first");
		var r = $obj.val();
		var s =  $(rowobj).find("td").eq(8).text();
		if($obj.val() && !isCount($obj))
	    {
	        alertMsg.warn("请正确输入数量");
	        return false;
	    }
		if(parseInt(r)-parseInt(s)>0){
			alertMsg.warn("调整数量不能大于原数量.");
	        return false;
		}
	    $("#O_ID").val(id);
	    $("#DTL_ID").val($(rowobj).attr("DETAIL_ID"));
	    $("#COUNT").val($obj.val());
	    $("#POS_ID").val($(rowobj).attr("POSITION_ID"));
	    
	    var $f = $("#diaSaveFm");
	    if (submitForm($f) == false) return false;
	    var sCondition = {};
	    sCondition = $f.combined(1) || {};
	    sendPost(diaUrl + "/orderPartCountSave.ajax", "btnDiaSave", sCondition, savePartCountCallBack);
	}
	function savePartCountCallBack(res){
		try {
			doSearchPart();
	    } catch (e) {
	        alertMsg.error(e);
	        return false;
	    }
	    return true;
	}
</script>