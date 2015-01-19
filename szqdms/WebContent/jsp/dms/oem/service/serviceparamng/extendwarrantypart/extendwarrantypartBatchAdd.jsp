<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contentPath = request.getContextPath();
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="dia-layout">
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchPartInfo">
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-searchPartInfo">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>配件代码：</label></td>
					    <td>
					    	<input type="text" id="diaBA_partCode" name="diaBA_partCode" datasource="PART_CODE" datatype="1,is_null,300" operation="like" />
					    </td>
					    <td><label>配件名称：</label></td>
					    <td>
					    	<input type="text" id="diaBA_partName" name="diaBA_partName" datasource="PART_NAME" datatype="1,is_null,400" operation="like" />
					    </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-partSearch" >查&nbsp;&nbsp;询</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>	
	</div>
	<div class="pageContent">
		<div id="page_searchPartList" >
			<table style="display:none;width:100%;" id="tab-searchPartList" multivals="di_hidInfo" name="tablist" ref="page_searchPartList" refQuery="tab-searchPartInfo">
					<thead>
						<tr>
							<th type="multi" name="CX-XH" style="align:center;" unique="PART_ID"></th>
							<th fieldname="PART_CODE" >配件代码</th>
							<th fieldname="PART_NAME" >配件名称</th>
							<th fieldname="WARRANTY_MONTH_IN"  refer="createInputSt">延保月份</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
			</table>
		</div>
		<table id="di_hidInfo" style="display:none">
			<tr><td>
				<textarea id="val2" name="multivals" style="width:400px;height:10px" column="1" style="display:none" ></textarea>
				<textarea id="val1" name="multivals" style="width:400px;height:10px"  style="display:none" ></textarea>
			</td></tr>
		</table>
	</div>
	<form method="post" id="fm-extendWarrant">
			<table class="editTable" id="tab-extendWarrantInfo">
				<tr>
					<td><label>延保策略：</label></td>
					<td>
						<input type="text" id="dia-warrantyCode" name="dia-warrantyCode" datasource="WARRANTY_CODE" operation="like" kind="dic" src="T#SE_BA_EXTEND_WARRANTY:WARRANTY_CODE:WARRANTY_NAME{WARRANTY_ID}:STATUS=100201" datatype="0,is_null,30" />
						<input type="hidden" id="dia-warrantyId" name="dia-warrantyId" datasource="WARRANTY_ID"/>
						<input type="hidden" id="dia-warrantyName" name="dia-warrantyName" datasource="WARRANTY_NAME"/>
						<input type="hidden" id="dia-partIds" name="dia-partIds" datasource="PART_IDS"/>
						<input type="hidden" id="dia-warrantyMonths" name="dia-warrantyMonths" datasource="WARRANTY_MONTHS"/>
					</td>			    
				</tr>
			</table>
				<div class="searchBar" align="left" >
					<div class="subBar">
						<ul>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-extendWarrantSave" >保&nbsp;&nbsp;存</button></div></div></li>
						</ul>
					</div>
				</div>
	</form>
	</div>
</div>	
<script type="text/javascript">
var searchPartUrl = "<%=request.getContextPath()%>/service/serviceparamng/ExtendWarrantyPartMngAction/searchPart.ajax";
var diaSaveAction = "<%=request.getContextPath()%>/service/serviceparamng/ExtendWarrantyPartMngAction";
var diaAction = "<%=action%>";
//初始化
$(function()
{
	//设置高度
	$("#tab-searchPartList").attr("layoutH",document.documentElement.clientHeight-222);
	
	//查询配件按钮响应
	$("#btn-partSearch").bind("click", function(event){
		$("#val1").val("");
		$("#val2").val("");
		var $f = $("#fm-searchPartInfo");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchPartUrl,"btn-partSearch",1,sCondition,"tab-searchPartList");
	});
	
	if(diaAction == 1)	//新增初始化,先执行查询
	{  
		var $f = $("#fm-searchPartInfo");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchPartUrl,"btn-partSearch",1,sCondition,"tab-searchPartList");
	}
	
	//保存延保策略与配件关系
	$("#btn-extendWarrantSave").bind("click", function(event){ 
		var partIds=$("#val2").val();
		var warrantyMonths=$("#val1").val();
		$("#dia-partIds").val(partIds);//配件IDs
		$("#dia-warrantyMonths").val(warrantyMonths);//月份s
		if(partIds&&warrantyMonths)
		{              
		    var $f = $("#fm-extendWarrant");
			if (submitForm($f) == false) return false;
			var sCondition = {};
			sCondition = $("#fm-extendWarrant").combined(1) || {};
			var addUrl = diaSaveAction + "/batchInsert.ajax";
			doNormalSubmit($f,addUrl,"btn-extendWarrantSave",sCondition,diaInsertCallBack);
			
		}else
		{
			alertMsg.info("请选择记录");
			return false;
		}
		
	});
});

//批量新增回调函数
function diaInsertCallBack(res)
{
	try
	{   
		$("#btn-search").trigger("click");
		$.pdialog.closeCurrent();
		
		//清空的内容
		$("#val1").val("");
		$("#val2").val("");
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}

//给隐藏域赋值
function afterDicItemClick(id, $row, selIndex) 
{   
	//给延保策略隐藏域赋值
	if(id=="dia-warrantyCode") 
	{
		$("#dia-warrantyName").val($("#"+id).val());;
		$("#dia-warrantyId").val($row.attr("WARRANTY_ID"));
	}
	return true;
}
</script>