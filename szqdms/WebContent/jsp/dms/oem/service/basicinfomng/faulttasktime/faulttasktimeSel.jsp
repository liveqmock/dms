<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchSelFault">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-searchSelFault">
					<!-- 定义查询条件 -->
					<tr>
					    <td><label>故障模式代码：</label></td>
					    <td><input type="text" id="sel_fault_code" name="sel_fault_code" datatype="1,is_null,17" dataSource="FAULT_CODE" operation="like" /></td>
					    <td><label>故障模式名称：</label></td>
					    <td><input type="text" id="sel_fault_name" name="sel_fault_name" datatype="1,is_null,30" dataSource="FAULT_NAME" operation="like" /></td>
					</tr>					
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-searchSelFault" >查&nbsp;&nbsp;询</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_selFaultList" >
			<table style="display:none;width:100%;" id="tab-selFaultList" name="tablist" ref="page_selFaultList" refQuery="tab-searchSelFault" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="FAULT_CODE" >故障模式代码</th>
							<th fieldname="FAULT_NAME" >故障模式名称</th>			
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
var searchSelUrl = "<%=request.getContextPath()%>/service/basicinfomng/FaultPatternMngAction/search.ajax";
$(function()
{
	//新增初始化,先执行查询  
	var $f = $("#fm-searchSelFault");
	var sCondition = {};
    sCondition = $f.combined() || {};
	doFormSubmit($f,searchSelUrl,"btn-searchSelFault",1,sCondition,"tab-selFaultList");

	$("#btn-searchSelFault").bind("click", function(event){
		var $f = $("#fm-searchSelFault");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchSelUrl,"btn-searchSelFault",1,sCondition,"tab-selFaultList");
	});
});
var dialog = $("body").data("faulttasktimeSel");

function doOk(rowobj)
{
	try
	{
		SelCallBack(rowobj);	
	}catch(e){}
	
	$.pdialog.close(dialog);
}
</script>
</div>

