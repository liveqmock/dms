<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<div id="oldPartSelect" style="width:100%;">
	<div class="page">
	<div class="pageHeader">
		<form id="di_in_oldPartSelectform" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="di_in_oldPartSelectTable">
					<tr>
						<td><label>旧件代码：</label></td>
						<td><input type="text" id="di_in_partCode" name="di_in_partCode" datasource="T1.PART_CODE" operation="like" datatype="1,is_null,30"  value="" /></td>
						<td><label>旧件名称：</label></td>
						<td><input type="text" id="di_in_partName" name="di_in_partName" datasource="T1.PART_NAME" operation="like" datatype="1,is_null,30" value="" /></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="button"><div class="buttonContent"><button type="button" id="di_in_searchOldPart">查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="di_in_oldPart">
			<table style="display:none;width:100%;" id="di_in_oldPartList" name="di_in_oldPartList" ref="di_in_oldPart" refQuery="di_in_oldPartSelectTable" >
				<thead>
					<tr>
						<th type="single" name="XH" style="display:" ></th>
						<th fieldname="PART_CODE">旧件代码</th>
						<th fieldname="PART_NAME">旧件名称</th>
						<th fieldname="SUPPLIER_CODE">供应商代码</th>
						<th fieldname="SUPPLIER_NAME">供应商名称</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
<script type="text/javascript">
var di_in_searchUrl = "<%=request.getContextPath()%>/service/oldpartMng/ClaimUpdateAction/claimUpdateOldPartSearch.ajax";
//弹出窗体
var dia_di_log = $("body").data("oldPartSelect");
$(function()
{
	var $f = $("#di_in_oldPartSelectform");
	var sCondition = {};
	sCondition = $f.combined() || {};
	doFormSubmit($f,di_in_searchUrl,"",1,sCondition,"di_in_oldPartList");
	
	$("#di_in_searchOldPart").click(function(){
		var $f = $("#di_in_oldPartSelectform");
		var sCondition = {};
		sCondition = $f.combined() || {};
		doFormSubmit($f,di_in_searchUrl,"",1,sCondition,"di_in_oldPartList");
	});
	$("button.close").click(function(){
		$.pdialog.close(dia_di_log);
		return false;
	});
});
function doRadio(obj)
{
	var $tr = $(obj).parent().parent().parent();
	$("#updatePartId").val($tr.attr("PART_ID"));
	$("#dia_updatePartCode").val($tr.attr("PART_CODE"));
	$("#dia_updatePartName").val($tr.attr("PART_NAME"));
	$("#dia_updateSupplyCode").val($tr.attr("SUPPLIER_CODE"));
	$("#dia_updateSupplyName").val($tr.attr("SUPPLIER_NAME"));
	$("#updateSupplyId").val($tr.attr("SUPPLIER_ID"));
	$("#dia_updateSupplyCode").attr("src","T#PT_BA_SUPPLIER S,PT_BA_PART_SUPPLIER_RL R:S.SUPPLIER_CODE:S.SUPPLIER_NAME{S.SUPPLIER_ID}:1=1 AND S.SUPPLIER_ID=R.SUPPLIER_ID AND R.STATUS=100201 AND  R.PART_ID="+$tr.attr("PART_ID")+"");
	$.pdialog.close(dia_di_log);
} 
</script>