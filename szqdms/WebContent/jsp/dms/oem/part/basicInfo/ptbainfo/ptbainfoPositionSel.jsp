<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String flag = request.getParameter("flag");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchPurchase">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-orderSearch">
					<!-- 定义查询条件 -->
					<tr>
					    <td><label>二级总成代码：</label></td>
					    <td><input type="text" id="POSITION_CODE" name="POSITION_CODE" datatype="1,is_null,300" dataSource="POSITION_CODE" operation="like" /></td>
					    <td><label>二级总成名称：</label></td>
					    <td><input type="text" id="POSITION_NAME" name="POSITION_NAME" datatype="1,is_null,300" dataSource="POSITION_NAME" operation="like" /></td>
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
			<table style="display:none;width:100%;" id="tab-purchase_info" name="tablist" ref="page_purchase" refQuery="fm-searchPurchase" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="POSITION_CODE" >二级总成代码</th>
							<th fieldname="POSITION_NAME" >二级总成名称</th>
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
var mngUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PtBaInfoAction";
$(function()
{
	$("#btn-searchOrder").bind("click", function(event){
//		var supplierId = $("#dia-SUPPLIER_ID").val();
		var p_id = $("#dia-position_id").val();
		var searchUrl = mngUrl+"/positionSearch.ajax?p_id="+p_id; 
		var $f = $("#fm-searchPurchase");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-searchOrder",1,sCondition,"tab-purchase_info");
	});
});
var dialog2 = $("body").data("ptbainfoPositionSel");
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

