<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String flag = request.getParameter("flag");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchDC">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-DCSearch">
					<!-- 定义查询条件 -->
					<tr>
					    <td><label id="lab_dealerCode">配送中心代码：</label></td>
					    <td><input type="text" id="PART_CODE" name="PART_CODE" datatype="1,is_digit_letter,30" dataSource="CODE" operation="like" /></td>
					    <td><label id="lab_dealerName">配送中心名称：</label></td>
					    <td><input type="text" id="PART_NAME" name="PART_NAME" datatype="1,is_null,30" dataSource="ONAME" operation="like" /></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-searchDC" >查&nbsp;&nbsp;询</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_searchDC" >
			<table style="display:none;width:100%;" id="tab-searchDCList" name="tablist" ref="page_searchDC" refQuery="tab-DCSearch" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="CODE" id="th_dealerCode">配送中心代码</th>
							<th fieldname="ONAME" id="th_dealerName">配送中心名称</th>							
							<th type="link" title="操作" action="doOk" class="btnSelect">操作</th>
						</tr>
					</thead>
					<tbody>
                    </tbody>
			</table>
		</div>
	</div>
	<script type="text/javascript">
//查询提交方法
var flag = '<%=flag%>'
var searchDCUrl = "<%=request.getContextPath()%>/part/basicInfoMng/ServiceDcAction/serviceDcSearch.ajax";
$(function()
{
	if (flag=="1" || flag=="2" ) {
		if (flag=="2") {
			$("#lab_dealerCode").text("渠道商代码：");
			$("#lab_dealerName").text("渠道商名称：");
			$("#th_dealerCode").text("渠道商代码");
			$("#th_dealerName").text("渠道商名称");
		}
		var $f = $("#fm-searchDC");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	searchDCUrl = searchDCUrl + "?flag="+flag
		doFormSubmit($f,searchDCUrl,"btn-searchDC",1,sCondition,"tab-searchDCList");
	}
	
	$("#btn-searchDC").bind("click", function(event){
		var $f = $("#fm-searchDC");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchDCUrl,"btn-searchDC",1,sCondition,"tab-searchDCList");
	});
});
var dialog2 = $("body").data("serviceDcSel");
function doOk(rowobj)
{
	try
	{
		SelCallBack(rowobj,flag);	
	}catch(e){}
	
	$.pdialog.close(dialog2);
}
</script>
</div>

