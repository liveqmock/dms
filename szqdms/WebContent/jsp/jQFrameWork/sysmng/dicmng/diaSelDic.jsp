<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<div id="dia-layout" width="100%">
	<div class="page">
	<div class="pageHeader">
		<form method="post" id="dia-fm-dicSearch">
		<!-- 定义查询条件 -->
		<div class="searchBar" align="left" >
			<table class="searchContent" id="dia-tab-dicSearch">
				<tr>
					<td><label>字典代码：</label></td>
				    <td><input type="text" id="dia-dicCode"  name="dia-dicCode" datasource="DIC_NAME_CODE" datatype="1,is_null,30" operation="like" /></td>
					<td><label>字典名称：</label></td>
				    <td><input type="text" id="dia-dicValue"  name="dia-dicValue" datasource="DIC_VALUE"  datatype="1,is_null,30" operation="like" /></td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="button"><div class="buttonContent"><button type="button" id="dia-btn-search">查&nbsp;&nbsp;询</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
				</ul>
			</div>
		</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="dia-page-dicList" >
			<table style="display:none" id="dia-tab-dicList" name="dicList" ref="dia-page-dicList" refQuery="dia-tab-dicSearch" pagerows="20">
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none;" ></th>
							<th fieldname="ID" freadonly="false" fdatatype="0,is_digit_letter,6">ID主键</th>
							<th fieldname="DIC_CODE" ordertype='local' class="asc" freadonly="false" fdatatype="0,is_digit_letter,30">字典代码</th>
							<th fieldname="DIC_VALUE" fdatatype="0,is_null,50">字典值</th>
							<th fieldname="DIC_VALUE_SPELL" fdatatype="0,is_digit_letter,30">字典值简拼</th>
							<th fieldname="DIC_VALUE_ASPELL" fdatatype="0,is_digit_letter,100">字典值全拼</th>
							<th type="link" title="[确定]" action="doDiaConfirm" >操作</th>
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
//查询提交方法
var diaSearchUrl = "<%=request.getContextPath()%>/DicTreeAction/searchRoot.ajax";
var diaSaveUrl = "<%=request.getContextPath()%>/DicTreeAction";
//定义弹出窗口样式
var diaSelOptions = {max:false,width:720,height:430,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
$(function()
{
	$("#dia-btn-search").click(function(){
		var $f = $("#dia-fm-dicSearch");
		var sCondition = {}; 
    	sCondition = $f.combined() || {}; 
		doFormSubmit($f,diaSearchUrl,"dia-btn-search",1,sCondition,"dia-tab-dicList");
	});
	$("#dia-btn-search").trigger("click");
});
function doDiaConfirm(obj)
{
	var $row = $(obj);
	while($row.get(0).tagName != "TR")
	{
		$row = $row.parent();
	}
	$("#dicCode").val($row.attr("DIC_VALUE"));
	$("#dicCode").attr("code", $row.attr("DIC_CODE"));
	$.pdialog.closeCurrent();
	$("#btn-search").trigger("click");
	return true;
}
</script>