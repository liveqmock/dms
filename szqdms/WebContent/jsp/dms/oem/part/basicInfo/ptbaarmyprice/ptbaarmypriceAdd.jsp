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
		<input type="hidden" id="dia-LOG_ID" name="dia-LOG_ID" datasource="LOG_ID" />
			<div align="left">
				<fieldset>
				<legend align="right"><a onclick="onTitleClick('dia-tab-pjxx')">&nbsp;配件军品价格编辑&gt;&gt;</a></legend>
				<table class="editTable" id="dia-tab-pjxx">
					<tr>
					    <td><label>配件代码：</label></td>
						<td>
						<input type="text" id="dia-part_code" name="dia-part_code" datasource="PART_CODE" datatype="0,is_null,300"  readonly="true"/>
						<input type="hidden" id="dia-part_id" name="dia-part_id" datasource="PART_ID" />
						</td>			
					     <td><label>配件名称：</label></td>
					    <td><input type="text" id="dia-part_name"  name="dia-part_name"   datasource="PART_NAME" datatype="0,is_null,300" readonly="true" />		
						</td>	
					</tr>
					<tr>
					    <td><label>军品价格：</label></td>
					    <td><input type="text" id="dia-army_price"  name="dia-army_price" datasource="ARMY_PRICE" datatype="0,is_digit_letter,30" value=""/>					   			    
					    </td>					    
					</tr>	
				</table>
				</fieldset>	
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button"  id="btn-save">保&nbsp;&nbsp;存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>
</div>
<script type="text/javascript">
/**
 * 保存样例
 */
var diaSaveAction = "<%=request.getContextPath()%>/part/basicInfoMng/PtBaInfoAction";
var diaAction = "<%=action%>";


//修改操作
	if(diaAction != "1")
	{
		//getSelectedRows():获取列表选中行对象，返回选中行数组
		var selectedRows = $("#tab-pjlb").getSelectedRows();
		//setEditValue：设置输入框赋值方法
		//"fm-userInfo"：要赋值区域的id
		//selectedRows[0].attr("rowdata"):行对象属性rowdata中保存了xml格式的行数据
		setEditValue("dia-fm-pjdawh",selectedRows[0].attr("rowdata"));
		
		var rowdata=selectedRows[0].attr("rowdata");	
		var objXML;
		if (typeof(rowdata) == "object") objXML = rowdata;
		else objXML = $.parseXML(rowdata);
	
		if(!$("#dia-army_price").val()){
			 $('#dia-army_price').val("0.00");
		}
		
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
		$("#dia-STATUS").find("option").text("有效");
		$("#dia-STATUS").attr("readonly",true);
		//$("#dia-STATUS1").attr("disabled",true);
		

	}

//初始化
$(function(){
	//绑定保存按钮
	$("#btn-save").bind("click", function(event){
		var $f = $("#dia-fm-pjdawh");
		if (submitForm($f) == false) return false;
		var sCondition = {};
		
		var pattern = /^[0-9]+(.[0-9]{1,2})?$/; 
		var army_price = $("#dia-army_price").val();	
		if (!pattern.test(army_price)) {
			alert("军品价格必须符合财务数字，如12.65或12");
			return false;
		}
		sCondition = $("#dia-fm-pjdawh").combined(1) || {};
		//判断军品价格有无更改
		var original_price=selectedRows[0].attr("ARMY_PRICE");
		var now_price=$('#dia-army_price').val();
		if(now_price==original_price){
       	alert("价格未做任何调整");
       }else{
       	var updateUrl = diaSaveAction + "/updateArmyPrice.ajax?original_price="+original_price+"&now_price="+now_price;
		doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
       }
	});
});



//新增回调函数
function diaInsertCallBack(res)
{
	try
	{
//		$("#tab-pjlb").insertResult(res,0);
		$("#btn-cx").trigger("click");
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
//		var selectedIndex = $("#tab-pjlb").getSelectedIndex();
//		$("#tab-pjlb").updateResult(res,selectedIndex);
		$("#btn-cx").trigger("click");

		$.pdialog.closeCurrent();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}


</script>