<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%@ page import="com.org.dms.common.DicConstant"%>
<%@ page import="com.org.frameImpl.Constant"%>
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
				    	<input type="hidden" id="inventory_id" name="inventory_id" datasource="INVENTORY_ID"/>        
				    </td>
				    <td><label>盘点仓库：</label></td>
				    <td>
				    	<input type="hidden" id="dia-warehouse_id" name="dia-warehouse_id" datasource="WAREHOUSE_ID"/>
                        <input type="hidden" id="dia-warehouse_name" name="dia-warehouse_name" datasource="WAREHOUSE_NAME"/>
				    	<input type="text" id="dia-warehouse_code" name="dia-warehouse_code" kind="dic" src="T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME{WAREHOUSE_ID,WAREHOUSE_NAME}:1=1 AND STATUS=<%=DicConstant.YXBS_01 %> ORDER BY WAREHOUSE_CODE" datasource="WAREHOUSE_CODE" datatype="0,is_null,30" />				   
				    </td>				    
				</tr>
				<tr>
					<td><label>盘点类型：</label></td>
				    <td>
				    	<input type="text" id="dia-pdlx" name="dia-inventory_type" kind="dic" src="PDLX" datasource="INVENTORY_TYPE" datatype="0,is_null,30"/>
				    </td>
				    <td><label>盘点人：</label></td>
				    <td>
				    	<input type="text" id="dia-inventory_user" name="dia-inventory_user" datasource="INVENTORY_USER" readonly value="<%=user.getPersonName()%>" datatype="1,is_null,30"/>
				    </td>
				</tr>
				<tr>
					<td><label>盘点时间：</label></td>
				    <td><input type="text"  id="dia-pdsj"  name=""dia-inventory_date"" style="width:75px;" datasource="INVENTORY_DATE" datatype="0,is_null,30" onclick="WdatePicker()" /></td>			
					<td><label>盘点状态：</label></td>
					    <td>
					    	<select type="text" id="in-inventory_status" name="in-inventory_status" kind="dic" src="PDZT" datasource="INVENTORY_STATUS" datatype="1,is_null,30" operation="=">
					    		<option value="-1" selected>--</option>
					    	</select>
					</td>
				</tr>
				<tr>
					<td><label>备注：</label></td>
					<td colspan="5">
						<textarea id="dia-bz" name="dia-remarks" style="width:460px;" datasource="REMARKS" datatype="1,is_null,30"></textarea>
					</td>
				</tr>
			</table>
			</fieldset>	
		</div>        
		</form>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button class="close" id="dia-save" type="button">保&nbsp;&nbsp;存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" id="dia-close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
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
	}else
	{			
		setDiaDefaultValue();
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
	//绑定保存按钮
	$("#dia-save").bind("click", function(event){
		var $f = $("#dia-fm");
		if (submitForm($f) == false) return false;
		var sCondition = {};
		sCondition = $("#dia-fm").combined(1) || {};
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
	);
	$("#dia-close").bind("click",function(){
		$.pdialog.closeCurrent();
		return false;
	});
});	

//表选字典回调方法
function afterDicItemClick(id, $row, selIndex){
	if(id == 'dia-warehouse_code'){									
        $('#dia-warehouse_id').val($row.attr('WAREHOUSE_ID'));
        $('#dia-warehouse_name').val($row.attr('WAREHOUSE_NAME'));
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


//更新回调函数
function diaUpdateCallBack(res)
{
	try
	{	
		var selectedIndex = $("#tab-list").getSelectedIndex();
		$("#tab-list").updateResult(res,selectedIndex);
//		parent.$("#tab-list").updateResult(res,selectedIndex);
		$.pdialog.closeCurrent();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}	
</script>

