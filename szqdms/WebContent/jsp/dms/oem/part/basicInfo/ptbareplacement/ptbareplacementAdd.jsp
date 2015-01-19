<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%@ page import="com.org.dms.common.DicConstant"%>
<%@ page import="com.org.frameImpl.Constant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	String contentPath = request.getContextPath();
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="dia-layout">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="dia-fm-pjdawh" class="editForm" >
			<input type="hidden" id="dia-replace_id" name="dia-replace_id" datasource="REPLACE_ID" />
			<input type="hidden" id="dia-belong_assembly" name="dia-belong_assembly" datasource="BELONG_ASSEMBLY" />
			<div align="left">
				<fieldset>
				<legend align="right"><a onclick="onTitleClick('dia-tab-pjxx')">&nbsp;配件替换件关系信息编辑&gt;&gt;</a></legend>
				<table class="editTable" id="dia-tab-pjxx">		
					<tr>
					    <td><label>配件代码：</label></td>
					    <td><input type="text" id="dia-part_code"  name="dia-part_code" datasource="PART_CODE" datatype="0,is_digit_letter,300" hasBtn="true" callFunction="openPurchase(1);" readonly="true"/>					   			    
					    	<input type="hidden" id="dia-part_id" name="dia-part_id" datasource="PART_ID"/>
					    </td>
					     <td><label>配件名称：</label></td>
					    <td><input type="text" id="dia-part_name"  name="dia-part_name" datasource="PART_NAME" datatype="0,is_null,300" readonly="true"  value=""/></td>
					</tr>
						
<!--					<tr>					   -->
<!--						<td><label>替换配件代码：</label></td>-->
<!--					    <td><input type="text" id="dia-rpart_code"  name="dia-rpart_code" datasource="RPART_CODE" datatype="0,is_digit_letter,30" hasBtn="true" callFunction="openPurchase(2);" readonly="true"/>					   			    -->
<!--					    	<input type="hidden" id="dia-rpart_id" name="dia-rpart_id" datasource="RPART_ID"/>-->
<!--					    </td>		-->
<!--					    <td><label>替换配件名称：</label></td>-->
<!--					    <td><input type="text" id="dia-rpart_name"  name="dia-rpart_name" datasource="RPART_NAME" datatype="0,is_null,30" readonly="true" value=""/></td>-->
<!--					</tr>-->
					<tr>					    
					    <td><label>有效标识：</label></td>
			   		 	<td>
				    	<select type="text" class="combox" id="dia-status" name="dia-status" kind="dic" src="YXBS" datasource="STATUS" datatype="0,is_null,100" >	
				     		<option value="-1" selected>--</option>				 
				    	</select>    					    
					</tr>					
					<tr>
						<td><label>备注：</label></td>
						<td colspan="4"><textarea id="dia-remarks" style="width:89%;height:40px;" name="dia-remarks" datasource="REMARKS"  datatype="1,is_null,1000"></textarea></td>
					</tr>
				</table>
				</fieldset>	
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button"  id="btn-save">保&nbsp;&nbsp;存</button></div></div></li>
<!--				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doDiaSave();" id="btn-save">替换件选择</button></div></div></li>-->
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	<div class="pageHeader" >
		<form method="post" id="fm-searchPartInfo">
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-searchPartInfo">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>替换配件代码：</label></td>
					    <td>
					    	<input type="text" id="diaBA_partCode" name="diaBA_partCode" datasource="PART_CODE" datatype="1,is_null,300" operation="like" />
					    </td>
					    <td><label>替换配件名称：</label></td>
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
	<div class="pagesearchContent">
		<div id="page_searchPartList" >
			<table style="display:none;width:100%;" id="tab-searchPartList" multivals="di_hidInfo" name="tablist" ref="page_searchPartList" refQuery="tab-searchPartInfo">
					<thead>
						<tr>
							<th type="multi" id="CX-XH" name="CX-XH" style="align:center;" unique="PART_ID" ></th>
							<th fieldname="PART_CODE" >替换配件代码</th>
							<th fieldname="PART_NAME" >替换配件名称</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
			</table>
		</div>
		<table style="display:none">
			<tr>
				<td>
					<textarea id="val0" name="multivals" style="width:400px;height:10px" column="1" style="display:none" ></textarea>
				</td>
			</tr>
		</table>
<!--		<table id="di_hidInfo" style="display:none">-->
<!--			<tr><td>-->
<!--				<textarea id="val2" name="multivals" style="width:400px;height:10px" column="1" style="display:none" ></textarea>-->
<!--				<textarea id="val1" name="multivals" style="width:400px;height:10px"  style="display:none" ></textarea>-->
<!--			</td></tr>-->
<!--		</table>-->
	</div>
	</div>
</div>
<script type="text/javascript">
/**
 * 保存样例
 */
var diaSaveAction = "<%=request.getContextPath()%>/part/basicInfoMng/PtBaReplacementAction";
var searchPartUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PtBaReplacementAction/partSearch.ajax";
var diaAction = "<%=action%>";

//初始化
$(function()
{
	//修改操作
	if(diaAction == "1")
	{
		var $f = $("#fm-searchPartInfo");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchPartUrl,"btn-partSearch",1,sCondition,"tab-searchPartList");
		
		//有效标示
		$("#dia-status").val("<%=Constant.YXBS_01%>");
		$("#dia-status").attr("code","<%=Constant.YXBS_01%>");
		$("#dia-status").find("option").val("<%=Constant.YXBS_01%>");
		$("#dia-status").find("option").text("有效");
		$("#dia-status").attr("readonly",true);
		
	}else
	{			
		var selectedRows = $("#tab-pjlb").getSelectedRows();
		setEditValue("dia-fm-pjdawh",selectedRows[0].attr("rowdata"));
		$("#dia-part_id").attr("readonly",true);
		
		var rowdata=selectedRows[0].attr("rowdata");	
		var objXML;
		if (typeof(rowdata) == "object") objXML = rowdata;
		else objXML = $.parseXML(rowdata);

	}
//保存	
	$("#btn-save").bind("click", function(event){
		var partIds=$("#val0").val();
		if(partIds){
			var $f = $("#dia-fm-pjdawh");
			if (submitForm($f) == false) return false;
			var sCondition = {};
			sCondition = $("#dia-fm-pjdawh").combined(1) || {};
			if(diaAction == "1")	//插入动作
			{
				var addUrl = diaSaveAction + "/batchInsert.ajax?partIds="+ partIds;
				doNormalSubmit($f,addUrl,"btn-save",sCondition,diaInsertCallBack);
			}else	//更新动作
			{
				var updateUrl = diaSaveAction + "/update.ajax";
				doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
			}
		}else{
			alertMsg.info("请选择替换件！");
			return false;
		}
	
	
		
	});
	
	//查询配件按钮响应
	$("#btn-partSearch").bind("click", function(event){
		var $f = $("#fm-searchPartInfo");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchPartUrl,"btn-partSearch",1,sCondition,"tab-searchPartList");
	});
});




//表选字典回调方法
function afterDicItemClick(id, $row, selIndex){
	//$row 行对象
	//selIndex 第几个字典

	//判断id中是否包含dia-MODELS_CODE的值
	if(id.indexOf("dia-position_code") == 0)
	{
		if($row.attr("POSITION_ID")){
		
		$("#dia-belong_assembly").val($row.attr("POSITION_ID"));
		}
		return true;
	}
	return true;
}



//新增回调函数
function diaInsertCallBack(res)
{
	try
	{
//		$("#tab-pjlb").insertResult(res,0);
		
		$("#btn-cx").trigger("click");
		$.pdialog.closeCurrent();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}



//更新回调函数
function diaUpdateCallBack(res)
{
	try
	{
		var selectedIndex = $("#tab-pjlb").getSelectedIndex();
		$("#tab-pjlb").updateResult(res,selectedIndex);
//		$("#btn-cx").trigger("click");
		$.pdialog.closeCurrent();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
function SelCallBack(obj,flag)
	{
//	alert(flag);
		if(flag=="1"){
			$("#dia-part_id").val($(obj).attr("PART_ID"));			//配件主键
			$("#dia-part_code").val($(obj).attr("PART_CODE"));		//配件代码
			$("#dia-part_name").val($(obj).attr("PART_NAME"));		//配件名称
		}
		else{
			$("#dia-rpart_id").val($(obj).attr("PART_ID"));		//替换配件主键
			$("#dia-rpart_code").val($(obj).attr("PART_CODE"));	//替换配件代码
			$("#dia-rpart_name").val($(obj).attr("PART_NAME"));	//替换配件名称
		}
	}
/*TD中放大镜按钮响应事件  */
	function openPurchase(flag)
	{
//		alert(flag);
//		if(!$("#dia-SUPPLIER_ID").val()){
//			alertMsg.warn('请选择配件!')
//		}else{
			var options = {width:750,height:400,max:false,mask:true,mixable:false,minable:false,resizable:true,drawable:true}; 
			var url = webApps +"/jsp/dms/oem/part/basicInfo/ptbareplacement/ptbareplacementSel.jsp?flag="+flag;	
			$.pdialog.open(url, "ptbareplacementSel", "配件查询", options);		
//		}
		
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

</script>