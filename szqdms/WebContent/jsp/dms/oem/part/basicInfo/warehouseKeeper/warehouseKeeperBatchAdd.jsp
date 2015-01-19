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
		<form method="post" id="fm-searchNewPartInfo">
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-searchNewPartInfo">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>配件代码：</label></td>
					    <td>
					    	<input type="text" id="diaN_partCode" name="diaN_partCode" datasource="PART_CODE" datatype="1,is_null,30" operation="like" />
					    </td>
					    <td><label>配件名称：</label></td>
					    <td>
					    	<input type="text" id="diaN_partName" name="diaN_partName" datasource="PART_NAME" datatype="1,is_null,30" operation="like" />
					    </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-newPartSearch" >查&nbsp;&nbsp;询</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>	
	</div>
	<div class="pageContent">
		<div id="page_searchNewPartList" >
			<table style="display:none;width:100%;" id="tab-searchNewPartList" name="tablist" ref="page_searchNewPartList" refQuery="tab-searchNewPartInfo">
					<thead>
						<tr>
							<th type="multi" name="CX-XH" style="align:center;" unique="PART_ID"></th>
							<th fieldname="PART_CODE" >配件代码</th>
							<th fieldname="PART_NAME" >配件名称</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
			</table>
		</div>
		<table style="display:none">
			<tr><td>
				<textarea id="val0" name="multivals" style="width:400px;height:10px" column="1" style="display:none" ></textarea>
			</td></tr>
		</table>
	</div>
	<form method="post" id="fm-newPartWarehouseKeeper">
		<table class="editTable" id="tab-newPartWarehouseKeeperInfo">
			<tr>
			    <td><label>仓库名称：</label></td>
			    <td>
			    	<input type="text" id="diaN-warehouseCode"  name="diaN-warehouseCode" kind="dic" 
			    		src="T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME{WAREHOUSE_ID}:1=1 
			    			AND WAREHOUSE_CODE IN ('K000001','K000002','K000003','K000004','K000005','K000006','K000007')" 
			    		datasource="WAREHOUSE_CODE" datatype="0,is_null,30" />
			    	<input type="hidden" id="diaN-warehouseName"  name="diaN-warehouseName" datasource="WAREHOUSE_NAME"  />
			    	<input type="hidden" id="diaN-warehouseId"  name="diaN-warehouseId" datasource="WAREHOUSE_ID"  />
			    </td>
			    <td><label>库管员姓名：</label></td>
			    <td>
			    	<input type="text" id="diaN-userAccount"  name="diaN-userAccount" kind="dic" 
			    		src="T#TM_USER U, TR_ROLE_USER_MAP RL:U.ACCOUNT:U.PERSON_NAME:1=1 AND RL.role_id IN(10000005,10000015,10000018,10000070,10000072)  AND U.STATUS='100201' AND U.USER_ID=RL.USER_ID AND U.ACCOUNT NOT LIKE '%ADMIN%'" 
			    		datasource="USER_ACCOUNT" datatype="0,is_null,32" />
			    	<input type="hidden" id="diaN-personName"  name="diaN-personName" datasource="PERSON_NAME"  />
			    </td>
			</tr>
		</table>
		<div class="searchBar" align="left" >
			<div class="subBar">
				<ul>
					<li><div class="button"><div class="buttonContent"><button type="button" id="btn-keeperAdd" >库管员新增</button></div></div></li>
				</ul>
			</div>
		</div>
	</form>
	</div>
</div>	
<script type="text/javascript">
var searchNewPartUrl = "<%=request.getContextPath()%>/part/basicInfoMng/WarehouseKeeperAction/searchNewPart.ajax";
var diaSaveAction = "<%=request.getContextPath()%>/part/basicInfoMng/WarehouseKeeperAction";
var diaAction = "<%=action%>";
//初始化
$(function()
{
	//设置高度
	$("#tab-searchNewPartList").attr("layoutH",document.documentElement.clientHeight-400);
	
	//查询按钮响应
	$("#btn-newPartSearch").bind("click", function(event){
		var $f = $("#fm-searchNewPartInfo");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchNewPartUrl,"btn-newPartSearch",1,sCondition,"tab-searchNewPartList");
	});
	
	if(diaAction == 1)	//新增初始化
	{  
		var $f = $("#fm-searchNewPartInfo");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchNewPartUrl,"btn-batchAdd",1,sCondition,"tab-searchNewPartList");
	}
	
	//库管员批量新增
	$("#btn-keeperAdd").bind("click", function(event){ 
		var partIds=$("#val0").val();
		if(partIds)
		{              
		    var $f = $("#fm-newPartWarehouseKeeper");
			if (submitForm($f) == false) return false;
			var sCondition = {};
			sCondition = $("#fm-newPartWarehouseKeeper").combined(1) || {};
			var addUrl = diaSaveAction + "/batchInsert.ajax?partIds=" + partIds;
			doNormalSubmit($f,addUrl,"btn-keeperAdd",sCondition,diaInsertCallBack);
			
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
		//上个页面的按钮parent
	 	//parent.$("#btn-search").trigger("click");
		//parent.$.pdialog.closeCurrent();
  	
		$("#btn-search").trigger("click");
		$.pdialog.closeCurrent();
		
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}

//列表复选
function doCheckbox(checkbox)
{
	var arr = [];
	var $checkbox = $(checkbox);
	var mxid = $(checkbox).val();
	arr.push(mxid);
	multiSelected($checkbox,arr);
}

function afterDicItemClick(id, $row, selIndex) 
{
	if(id.indexOf("diaN-warehouseCode") == 0)
	{  
		$("#diaN-warehouseName").val($("#"+id).val());
		
		if($row.attr("WAREHOUSE_ID")){
			$("#diaN-warehouseId").val($row.attr("WAREHOUSE_ID"));
		}
		return true;
	}
	
	if(id=="diaN-userAccount")
	{
		$("#diaN-personName").val($("#"+id).val());
	
	}
	return true;
}

</script>