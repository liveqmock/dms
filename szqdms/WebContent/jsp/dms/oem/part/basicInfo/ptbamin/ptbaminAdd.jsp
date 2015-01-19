<?xml version="1.0" encoding="UTF-8" ?>&nbsp;<%@ page language="java" contentType="text/html; charset=UTF-8"
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
			<input type="hidden" id="dia-PART_ID" name="dia-PART_ID" datasource="PART_ID" />
			<div align="left">
				<fieldset>
				<legend align="right"><a onclick="onTitleClick('dia-tab-pjxx')">&nbsp;最小包装信息编辑&gt;&gt;</a></legend>
				<table class="editTable" id="dia-tab-pjxx">							
					<tr>
					    <td><label>最小包装数：</label></td>
					    <td><input type="text" id="dia-in-pjdm"  name="dia-in-pjdm" datasource="MIN_PACK" datatype="0,is_null,30" value=""/>					   			    
					    </td>					    
					     <td><label>最小包装单位：</label></td>
					    <td><input type="text" id="dia-in-pjmc"  name="dia-in-pjmc" datasource="MIN_UNIT" datatype="0,is_null,30" value=""/></td>					    
					</tr>												
					<tr>
						<td><label>备注：</label></td>
						<td colspan="4"><textarea id="dia-ta-bz" style="width:89%;height:40px;" name="dia-ta-bz" datasource="REMARKS"  datatype="1,is_null,100"></textarea></td>
					</tr>
				</table>
				</fieldset>	
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doDiaSave();" id="btn-save">保&nbsp;&nbsp;存</button></div></div></li>
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
		$("#dia-in-pjdm").attr("readonly",true);
		var rowdata=selectedRows[0].attr("rowdata");	
		var objXML;
		if (typeof(rowdata) == "object") objXML = rowdata;
		else objXML = $.parseXML(rowdata);
		$("#dia-type_code").attr("code",selectedRows[0].attr("DIRECT_TYPE_CODE"));
        $("#dia-type_code").val(selectedRows[0].attr("DIRECT_TYPE_NAME"));
        $("#dia-direct_type_id").val(selectedRows[0].attr("DIRECT_TYPE_ID"));
        $("#dia-direct_type_name").val(selectedRows[0].attr("DIRECT_TYPE_NAME"));  
        
        $("#dia-parakey").attr("code",selectedRows[0].attr("REBATE_TYPE"));
        $("#dia-parakey").val(selectedRows[0].attr("REBATE_PARAVALUE1"));
        $("#dia-rebate_type").val(selectedRows[0].attr("REBATE_TYPE"));
        $("#dia-rebate_paravalue1").val(selectedRows[0].attr("REBATE_PARAVALUE1"));
         
        
		//车辆总成字典表数据显示		
		var positioncode=getNodeValue(objXML, "POSITION_CODE", 0);
		var positionname=getNodeValue(objXML, "POSITION_NAME", 0);
		$("#dia-position_code").val(positionname);
		$("#dia-position_code").attr("code",positioncode);
		
//		var parakey=getNodeValue(objXML, "PARAKEY", 0);
//		var paravalue1=getNodeValue(objXML, "PARAVALUE1", 0);
//		$("#dia-parakey").val(paravalue1);
//		$("#dia-parakey").attr("code",parakey);
		
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
		$("#dia-if_oil").find("option").text("否");
		$("#dia-in-pjzt").find("option").text("有效");		
		$("#dia-STATUS").attr("readonly",true);
		//$("#dia-STATUS1").attr("disabled",true); dia-in-pjzt

	}

//初始化
$(function(){
	//绑定保存按钮
	$("#btn-save").bind("click", function(event){
		//获取需要提交的form对象
		var $f = $("#dia-fm-pjdawh");
		//submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
		if (submitForm($f) == false) return false;
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#dia-fm-pjdawh").combined(1) || {};
		if(diaAction == "1")	//插入动作
		{
			var addUrl = diaSaveAction + "/insert.ajax";
			/**
			 * doNormalSubmit:提交编辑域form表单方法
			 * @$f:提交form的jquery对象
			 * @addUrl：提交请求的url路径
			 * @"btn-save":点击按钮的id
			 * @sCondition:提交内容的json封装
			 * @diaInsertCallBack:请求后台执行完毕后，页面的回调方法
			 */
			doNormalSubmit($f,addUrl,"btn-save",sCondition,diaInsertCallBack);
		}else	//更新动作
		{
			var updateUrl = diaSaveAction + "/update.ajax";
			doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
			
		}
	});
	

	
});

//表选字典回调方法
function afterDicItemClick(id, $row, selIndex){
	if(id == 'dia-type_code'){									
        $('#dia-direct_type_id').val($row.attr('TYPE_ID'));
        $('#dia-direct_type_code').val($row.attr('TYPE_CODE'));
        $('#dia-direct_type_name').val($row.attr('TYPE_NAME'));
    }	
    if(id == 'dia-parakey'){									
        $('#dia-rebate_type').val($row.attr('PARAKEY'));
        $('#dia-rebate_paravalue1').val($row.attr('PARAVALUE1'));
    }	
	return true;
}		
//表选字典回调方法1
function afterDicItemClick1(id, $row, selIndex){
	//$row 行对象
	//selIndex 第几个字典

	//判断id中是否包含dia-MODELS_CODE的值
	if(id.indexOf("dia-position_code") == 0)
	{
		$("#dia-position_name").val($("#"+id).val());
		
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
//		alert(res.xml);
		$("#tab-pjlb").insertResult(res,0);
//		$("#btn-cx").trigger("click");
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

function SelCallBack(obj)
	{
			$("#dia-f_part_id").val($(obj).attr("PART_ID"));			//父级零件ID
			$("#dia-f_part_code").val($(obj).attr("PART_CODE"));		//父级零件编号
			$("#dia-f_part_name").val($(obj).attr("PART_NAME"));		//父级零件名称
	}
/*TD中放大镜按钮响应事件  */
	function openPurchase(flag)
	{
//		if(!$("#dia-SUPPLIER_ID").val()){
//			alertMsg.warn('请选择配件!')
//		}else{
			var options = {width:750,height:400,max:false,mask:true,mixable:false,minable:false,resizable:true,drawable:true}; 
			var url = webApps +"/jsp/dms/oem/part/basicInfo/ptbainfo/ptbainfoSel.jsp";	
			$.pdialog.open(url, "ptbainfoSel", "配件查询", options);		
//		}
		
	}

</script>