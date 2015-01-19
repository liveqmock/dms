<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<!-- 车辆部位，弹出父级总成选择框 -->
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchSelPName">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-searchSelPName">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>总成级别：</label></td>
						<td><input type="text" id="sel-positionLevel" name="sel-positionLevel" datasource="POSITION_LEVEL" operation="like" datatype="1,is_null,6" /></td>
						<td><label>总成代码：</label></td>
						<td><input type="text" id="sel-positionCode" name="sel-positionCode" datasource="POSITION_CODE" operation="like" datatype="1,is_null,30" /></td>
						<td><label>总成名称：</label></td>
						<td><input type="text" id="sel-positionName" name="sel-positionName" datasource="POSITION_NAME" operation="like" datatype="1,is_null,30" /></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-searchSelPName" >查&nbsp;&nbsp;询</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_selPNameList" >
			<table style="display:none;width:100%;" id="tab-selPNameList" name="tablist" ref="page_selPNameList" refQuery="tab-searchSelPName" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="POSITION_LEVEL" >总成级别</th>
							<th fieldname="POSITION_CODE" >总成代码</th>
							<th fieldname="POSITION_NAME" >总成名称</th>						
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
var searchSelUrl = "<%=request.getContextPath()%>/service/basicinfomng/VehiclePositionMngAction/pNameSearch.ajax";
$(function()
{
	//新增初始化,先执行查询  
	var $f = $("#fm-searchSelPName");
	var sCondition = {};
    sCondition = $f.combined() || {};
	doFormSubmit($f,searchSelUrl,"btn-searchSelPName",1,sCondition,"tab-selPNameList");

	$("#btn-searchSelPName").bind("click", function(event){
		var $f = $("#fm-searchSelPName");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchSelUrl,"btn-searchSelPName",1,sCondition,"tab-selPNameList");
	});
});
var dialog = $("body").data("vehiclePositionSel");

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

