<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%@ page import="com.org.dms.common.DicConstant"%>
<%@ page import="com.org.frameImpl.Constant"%>
<%@ page import="java.text.*"%>
<%
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="dia-layout">
	<div class="page">
	<div class="pageContent" >
		<form method="post" id="dia-fm" class="editForm" style="width:99%;">
		<div align="left">
			<fieldset>
			<legend align="right"><a onclick="onTitleClick('dia-tab-pjxx')">&nbsp;盘点设置维护&gt;&gt;</a></legend>
			<table class="editTable" id="dia-tab-pjxx">
				<tr>					
				    <td><label>盘点编号：</label></td>				   
				    <td>
				    	<input type="text" id="dia-inventory_no" name="dia-inventory_no" datasource="INVENTORY_NO" readonly value="自动生成"/>
				    	<input type="hidden" id="dia-inventory_id" name="dia-inventory_id" datasource="INVENTORY_ID"/>        
				    </td>
				    <td><label>盘点仓库：</label></td>
				    <td>
				    	<input type="hidden" id="dia-warehouse_id" name="dia-warehouse_id" datasource="WAREHOUSE_ID"/>
                        <input type="hidden" id="dia-warehouse_name" name="dia-warehouse_name" datasource="WAREHOUSE_NAME"/>
				    	<input type="text" id="dia-warehouse_code" name="dia-warehouse_code" kind="dic" src="T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME{WAREHOUSE_ID,WAREHOUSE_NAME}:1=1 AND WAREHOUSE_TYPE NOT IN(100106) AND STATUS=<%=DicConstant.YXBS_01 %> ORDER BY WAREHOUSE_CODE" datasource="WAREHOUSE_CODE" datatype="0,is_null,300" />				   
				    </td>				    
				</tr>
				<tr>
					<td><label>盘点类型：</label></td>
				    <td>
				    	<input type="text" id="dia-inventory_type" name="dia-inventory_type" kind="dic" src="PDLX" datasource="INVENTORY_TYPE" datatype="0,is_null,300"/>
				    	<input type="hidden" id="dia-inventory_type1" name="dia-inventory_type1" datasource="INVENTORY_TYPE1" />
				    </td>
				    <td><label>盘点人：</label></td>
				    <td>
				    	<input type="text" id="dia-inventory_user" name="dia-inventory_user" datasource="INVENTORY_USER" readonly value="<%=user.getPersonName()%>" datatype="1,is_null,300"/>
				    </td>
				</tr>
				<tr>
					<td><label>盘点时间：</label></td>
					<%
					     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					     String currDate = sdf.format(new java.util.Date());
					 %>
				    <td><input type="text"  id="dia-pdsj"  name="dia-inventory_date" style="width:75px;" datasource="INVENTORY_DATE" datatype="0,is_null,300" readonly value="<%=currDate %>"/></td>		
<!--				    <td><input type="text"  id="dia-pdsj"  name="dia-inventory_date" style="width:75px;" datasource="INVENTORY_DATE" datatype="0,is_null,30" onclick="WdatePicker()" value="<%=currDate %>"/></td>		-->
<!--					<td><label>盘点状态：</label></td>-->
<!--					    <td>-->
<!--					    	<select type="text" id="in-inventory_status" name="in-inventory_status" kind="dic" src="PDZT" datasource="INVENTORY_STATUS" datatype="1,is_null,30" operation="=">-->
<!--					    		<option value="-1" selected>--</option>-->
<!--					    	</select>-->
<!--					</td>-->
				</tr>
				
								
				<tr>
					<td><label>备注：</label></td>
					<td colspan="5">
						<textarea id="dia-bz" name="dia-remarks" style="width:460px;" datasource="REMARKS" datatype="1,is_null,500" ></textarea>
					</td>
				</tr>
			</table>
			</fieldset>	
		</div>        
		</form>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button  id="dia-save" type="button">保&nbsp;&nbsp;存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button  id="btn-batchDelete" type="button">批量删除</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" id="dia-close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
		<form method="post" id="dia-fm1" class="editForm" style="width:99%;">
		
		
		<div id="dia-pjmx" style="height:290px;overflow:hidden;">
			<div id="qbpj" style="display:none;">
			<table style="display:none;width:100%;" id="dia-tab-pjlb" layoutH="350" name="tablist" ref="dia-pjmx" pageRows="15" >
					<thead>
						<tr>
<!--							<th type="single" fieldname="ROWNUMS" name="XH" style="display:true;" append="plus|addPjmx"></th>-->
<!--							<th type="multi" id="CX-XH" name="CX-XH" style="align:center;" unique="DTL_ID" ></th>-->
							<th fieldname="PART_CODE" >配件代码</th>
							<th fieldname="PART_NAME" >配件名称</th>
							<th fieldname="SUPPLIER_NAME" >供应商名称</th>
							<th fieldname="AREA_CODE" >库区代码</th>
							<th fieldname="POSITION_NAME" >库位名称</th>
							<th fieldname="WHOUSE_KEEPER" >库管员</th>
							<th fieldname="PLAN_PRICE" refer="amountFormat" align="right">计划价格</th>
							<th fieldname="PAPER_COUNT" >账面数量</th>
						</tr>
					</thead>
					<tbody>
						
					</tbody>
			</table>
			</div>
			<div id="bfpj" style="display:true;">
			<table style="display:none;width:100%;" id="dia-tab-pjbflb" layoutH="350" name="dia-tab-pjbflb" ref="dia-pjmx" pageRows="15" multivals="partsupplie" >
					<thead>
						<tr>
<!--							<th type="single" fieldname="ROWNUMS" name="XH" style="display:false" append="plus|addPjmx"></th>-->
							
							
							<th type="single" fieldname="ROWNUMS" name="XH" style="display:true;" append="plus|addPjmx"></th>
							<th type="multi" id="CX-XH" name="CX-XH" style="align:center;" unique="DTL_ID" ></th>
							<th fieldname="PART_CODE" >配件代码</th>
							<th fieldname="PART_NAME" >配件名称</th>
							<th fieldname="SUPPLIER_NAME" >供应商名称</th>
							<th fieldname="AREA_CODE" colwidth="70">库区代码</th>
							<th fieldname="POSITION_NAME" >库位名称</th>
							<th fieldname="WHOUSE_KEEPER" >库管员</th>
							<th fieldname="PLAN_PRICE" refer="amountFormat" align="right">计划价格</th>
							<th fieldname="PAPER_COUNT" >账面数量</th>
							<th colwidth="70" type="link" title="[删除]"  action="Delete" >操作</th>
						</tr>
						
					</thead>
			</table>
			
				
			</div>
				
		</div>
<!--				<div class="formBar">-->
<!--				<ul>-->
<!--					<li><div class="button"><div class="buttonContent"><button class="close" id="dia-close" type="button">批量删除</button></div></div></li>-->
<!--				</ul>-->
<!--				</div>-->
		</form>
		<div id="partsupplie">
		<table style="display:none">
				<tr>
					<td>
						<textarea id="val1" name="multivals" style="width:400px;height:10px" column="1" style="display:none" ></textarea>
					</td>
				</tr>
		</table>
		</div>
	</div>
		
</div>
</div>
<script type="text/javascript">
/**
 * 保存样例
 */
var diaSaveAction = "<%=request.getContextPath()%>/part/basicInfoMng/PtBuInventoryAction";
var diaAction = "<%=action%>";
//初始化
$(function(){
	$("#dia-save").bind("click",function(){
		alertMsg.info("保存成功.");
		//部分
		if($("#dia-inventory_type").attr("code")==201801){
			$("#bfpj").attr("style","display:");
			$("#dia-tab-pjbflb").show();
			$("#dia-tab-pjbflb").jTable();
			
			var $f = $("#dia-fm");
			if (submitForm($f) == false) return false;
			var sCondition = {};
			sCondition = $("#dia-fm").combined() || {};
			if(diaAction == "1")	
			{
				var addUrl = diaSaveAction + "/insert.ajax";			
				doNormalSubmit($f,addUrl,"dia-save",sCondition,diaInsertCallBack);
			}else	//更新动作
			{
				var updateUrl = diaSaveAction + "/update.ajax";
				doNormalSubmit($f,updateUrl,"dia-save",sCondition,diaUpdateCallBack);
				
			}
		}
		//全部
		if($("#dia-inventory_type").attr("code")==201802){
			$("#qbpj").attr("style","display:");
			$("#dia-tab-pjlb").show();
			$("#dia-tab-pjlb").jTable();
			
			var $f = $("#dia-fm");
			if (submitForm($f) == false) return false;
			var sCondition = {};
			sCondition = $("#dia-fm").combined() || {};
			if(diaAction == "1")	
			{alert("将新增该仓库下的全部数据")
				var addUrl = diaSaveAction + "/insertAll.ajax";			
				doNormalSubmit($f,addUrl,"dia-save",sCondition,diaInsertAllCallBack);
			}else	//更新动作
			{
				var updateUrl = diaSaveAction + "/update.ajax";
				doNormalSubmit($f,updateUrl,"dia-save",sCondition,diaUpdateCallBack);
				
			}
		}
	});
	$("#dia-close").bind("click",function(){
		$.pdialog.closeCurrent();
		return false;
	});
});

function addPjmx(){
//	alertMsg.info("可按照配件、库区等进行添加配件盘点明细.");
	var options = {max:false,width:1100,height:510,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/oem/part/storage/checkSetup/checkSetupSel.jsp?", "pjmxwh", "配件供应商信息新增", options);
}
//查询盘点明细
    function searchPart(){
 //		var inventoryNo = $("#dia-inventory_no").val();
 		var INVENTORY_ID = $("#dia-inventory_id").val();
        var $f = $("#dia-fm1");
		if (submitForm($f) == false) return false;
		var sCondition = {};
		sCondition = $("#dia-fm1").combined() || {};
		var addUrl = diaSaveAction + "/partList.ajax?inventory_id="+INVENTORY_ID;
		doFormSubmit($f,addUrl,"",1,sCondition,"dia-tab-pjbflb");		
    } 
     function searchAllPart(){
 //		var inventoryNo = $("#dia-inventory_no").val();
 		var INVENTORY_ID = $("#dia-inventory_id").val();
        var $f = $("#dia-fm1");
		if (submitForm($f) == false) return false;
		var sCondition = {};
		sCondition = $("#dia-fm1").combined() || {};
		var addUrl = diaSaveAction + "/partList.ajax?inventory_id="+INVENTORY_ID;
		doFormSubmit($f,addUrl,"",1,sCondition,"dia-tab-pjlb");		
    } 


$(function(){
	//修改操作
	if(diaAction != "1")
	{		
		var selectedRows = $("#tab-list").getSelectedRows();
        setEditValue("dia-fm", selectedRows[0].attr("rowdata"));
        $("#dia-inventory_no").attr("readonly",true);
        $("#dia-warehouse_code").attr("code",selectedRows[0].attr("WAREHOUSE_CODE"));
        $("#dia-warehouse_code").val(selectedRows[0].attr("WAREHOUSE_NAME"));
        $("#dia-warehouse_id").val(selectedRows[0].attr("WAREHOUSE_ID"));
        $("#dia-warehouse_name").val(selectedRows[0].attr("WAREHOUSE_NAME"));  
        
        
        if($("#dia-inventory_type").attr("code")==201801){
        	$("#bfpj").attr("style","display:");
			$("#dia-tab-pjbflb").show();
			$("#dia-tab-pjbflb").jTable();
        
			var INVENTORY_ID = $("#dia-inventory_id").val();
	        var $f = $("#dia-fm1");
			if (submitForm($f) == false) return false;
			var sCondition = {};
			sCondition = $("#dia-fm1").combined() || {};
			var addUrl = diaSaveAction + "/partList.ajax?inventory_id="+INVENTORY_ID;
			doFormSubmit($f,addUrl,"",1,sCondition,"dia-tab-pjbflb");	
        }
        
        if($("#dia-inventory_type").attr("code")==201802){
			$("#qbpj").attr("style","display:");
			$("#dia-tab-pjlb").show();
			$("#dia-tab-pjlb").jTable();
			var INVENTORY_ID = $("#dia-inventory_id").val();
	        var $f = $("#dia-fm1");
			if (submitForm($f) == false) return false;
			var sCondition = {};
			sCondition = $("#dia-fm1").combined() || {};
			var addUrl = diaSaveAction + "/partList.ajax?inventory_id="+INVENTORY_ID;
			doFormSubmit($f,addUrl,"",1,sCondition,"dia-tab-pjlb");	
			
		}
						
            
	}else
	{			
		setDiaDefaultValue();
	}
	
	//绑定保存按钮
//	$("#dia-save").bind("click", function(event){
//	alert("%%%%%%");
//		var $f = $("#dia-fm");
//		if (submitForm($f) == false) return false;
//		var sCondition = {};
//		sCondition = $("#dia-fm").combined(1) || {};
//		if(diaAction == "1")	
//		{
//			var addUrl = diaSaveAction + "/insert.ajax";			
//			doNormalSubmit($f,addUrl,"dia-save",sCondition,diaInsertCallBack);
//		}else	//更新动作
//		{
//			var updateUrl = diaSaveAction + "/update.ajax";
//			doNormalSubmit($f,updateUrl,"dia-save",sCondition,diaUpdateCallBack);
			
//		}
//	}
//	);
	$("#dia-close").bind("click",function(){
		$.pdialog.closeCurrent();
		return false;
	});
	
	$("#btn-batchDelete").bind("click", function(event){
		var dtl_id=$("#val1").val();
//		alert("来西安看看海");
//		alert(1);
//		alert(dtl_id);
		if(dtl_id){
			var $f = $("#dia-fm");
			if (submitForm($f) == false) return false;
			var sCondition = {};
			sCondition = $("#dia-fm").combined() || {};      
			var deleteUrl = diaSaveAction + "/batchDelete.ajax?dtl_id="+dtl_id;
			//sendPost(deleteUrl,"btn-batchDelete","",deleteCallBack,"true");
			
			doNormalSubmit($f,deleteUrl,"btn-batchDelete",sCondition,deletePromPartCallBack);
		
//		doFormSubmit($f,searchUrl,"btn-searchOrder",1,sCondition,"dia-tab-pjbflb");
		//doNormalSubmit($f,deleteUrl,"btn-batchDelete",1,sCondition,"dia-tab-pjbflb");
		//searchPart();
//		doFormSubmit($f, searchPartUrl, "btn-search", 1, sCondition, "tab-partList");
	}else{
		alertMsg.info("请选择！");
		return false;
	}
	});
});	

//批量删除回调
    function deletePromPartCallBack(){
//    	$.pdialog.closeCurrent();
 //       searchPart($("#dia-warehouse_id").val());
         searchPart();
 //        $("#val1").val("");
 //       $("#btn-closeOrder").trigger("click");
    }

//表选字典回调方法
function afterDicItemClick(id, $row, selIndex){
	if(id == 'dia-warehouse_code'){									
        $('#dia-warehouse_id').val($row.attr('WAREHOUSE_ID'));
        $('#dia-warehouse_name').val($row.attr('WAREHOUSE_NAME'));
    }	
	return true;
}

//全部新增回调函数
function diaInsertAllCallBack(res)
{
	try
	{
		var rows = res.getElementsByTagName("ROW");
		if (rows && rows.length > 0) {
            //获取库存盘点ID 并设置到隐藏域中
            var inventory_id = getNodeText(rows[0].getElementsByTagName("INVENTORY_ID").item(0));
            var inventory_no = getNodeText(rows[0].getElementsByTagName("INVENTORY_NO").item(0));
            $('#dia-inventory_id').val(inventory_id);
            $('#dia-inventory_no').val(inventory_no);
        }
        searchAllPart();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//新增回调函数
function diaInsertCallBack(res)
{
	try
	{
		var rows = res.getElementsByTagName("ROW");
		if (rows && rows.length > 0) {
            //获取库存盘点ID 并设置到隐藏域中
            var inventory_id = getNodeText(rows[0].getElementsByTagName("INVENTORY_ID").item(0));
            var inventory_no = getNodeText(rows[0].getElementsByTagName("INVENTORY_NO").item(0));
            $('#dia-inventory_id').val(inventory_id);
            $('#dia-inventory_no').val(inventory_no);
        }
        //显示结果集的情况下，插入一行       
		$("#tab-list").insertResult(res,0);
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
var $row;
//删除方法，rowobj：行对象，非jquery类型
function Delete(rowobj)
{
	$row = $(rowobj);
	var url = diaSaveAction + "/dtlDelete.ajax?DTL_ID="+$(rowobj).attr("DTL_ID");
	sendPost(url,"delete","",deleteCallBack,"true");
}
//删除回调方法
function  deleteCallBack(res)
{
	try
	{
		if($row) 
//		$("#btn-cx").trigger("click");			
		searchPart();
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
		var selectedIndex = $("#tab-list").getSelectedIndex();
		$("#tab-list").updateResult(res,selectedIndex);
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}

function diaSetupAddCallBack(res)
{
	try
	{
		var selectedIndex = $("#dia-tab-pjbflb").getSelectedIndex();
		
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}	
//列表复选
function doCheckbox(checkbox){  
	var $t = $(checkbox);
	var arr = [];
   	while($t.get(0).tagName != "TABLE"){
		$t = $t.parent();
	} 
	if($t.attr("id").indexOf("dia-tab-pjbflb")==0){
		var $tr = $(checkbox).parent().parent().parent();
		var mxid = $(checkbox).val();
		arr.push($tr.attr("DTL_ID"));
		var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
		multiSelected($checkbox,arr,$("#partsupplie"));
	} 
	if($t.attr("id").indexOf("tab-purchase_info")==0){
		var $tr = $(checkbox).parent().parent().parent();
		var mxid = $(checkbox).val();
		arr.push($tr.attr("DTL_ID"));
		var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
		multiSelected($checkbox,arr,$("#yhw"));
	} 
}
function setDiaDefaultValue()
	{
		//有效标示
		$("#dia-STATUS").val("<%=Constant.YXBS_01%>");
		$("#dia-STATUS").attr("code","<%=Constant.YXBS_01%>");
		$("#dia-STATUS").find("option").val("<%=Constant.YXBS_01%>");
		$("#dia-if_oil").find("option").text("否");
		$("#dia-STATUS").find("option").text("有效");
	}
//金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}	
</script>

