	<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%@ page import="com.org.dms.common.DicConstant"%>
<%@ page import="com.org.frameImpl.Constant"%>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
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
			<input type="hidden" id="dia-picture_id" name="dia-picture_id" datasource="PICTURE_ID" />
			<input type="hidden" id="dia-belong_assembly" name="dia-belong_assembly" datasource="BELONG_ASSEMBLY" />
			<input type="hidden" id="dia-status" name="dia-status" datasource="STATUS" />
			<div align="left">
				<fieldset>
				<table class="editTable" id="dia-tab-pjxx">			
					<tr>
						<td><label>配件代码：</label></td>
					    <td><input type="text" id="dia-part_code"  name="dia-part_code" datasource="PART_CODE" datatype="1,is_digit_letter,300"  " readonly="true"/>					   			    
					    	<input type="hidden" id="dia-part_id" name="dia-part_id" datasource="PART_ID"/>
					    </td>
					     <td><label>配件名称：</label></td>
					    <td><input type="text" id="dia-part_name"  name="dia-part_name" datasource="PART_NAME" datatype="1,is_null,500" readonly="true"  value=""/></td>					   				   			    
					</tr>
				</table>
				</fieldset>	
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="button" id="addAtt">上&nbsp;&nbsp;传</button></div></div></li>
<!--				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doDiaSave();" id="btn-save">保&nbsp;&nbsp;存</button></div></div></li>-->
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
var diaSaveAction = "<%=request.getContextPath()%>/part/basicInfoMng/PtBaPictureAction";
var diaAction = "<%=action%>";

//修改操作
	if(diaAction == "1")
	{
		//有效标示
		$("#dia-status").val("<%=Constant.YXBS_01%>");
	} else
	{	
		//getSelectedRows():获取列表选中行对象，返回选中行数组
		//var selectedRows = $("#tab-pjlb").getSelectedRows();
		//setEditValue：设置输入框赋值方法
		//"fm-userInfo"：要赋值区域的id
		//selectedRows[0].attr("rowdata"):行对象属性rowdata中保存了xml格式的行数据
		//setEditValue("dia-fm-pjdawh",selectedRows[0].attr("rowdata"));
		if(selectedRowObj)
		{
			$("#dia-part_id").val(selectedRowObj.find("td").eq(2).text());
			$("#dia-part_code").val(selectedRowObj.find("td").eq(3).text());
			$("#dia-part_name").val(selectedRowObj.find("td").eq(4).text());
		}
		$("#dia-part_id").attr("readonly",true);
		//var rowdata=selectedRows[0].attr("rowdata");	
		//var objXML;
		//if (typeof(rowdata) == "object") objXML = rowdata;
		//else objXML = $.parseXML(rowdata);
		
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
	
	$("#addAtt").bind("click",function(){
		var PART_ID = $("#dia-part_id").val();
		$.filestore.open(PART_ID,{"folder":"true","holdName":"true","uploadLimit":4,"fileSizeLimit":0,"fileTypeDesc":"All Files","fileTypeExts":"*.*"});
	});
	
});

//新增回调函数
function diaInsertCallBack(res)
{
	try
	{
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

function SelCallBack(obj,flag)
	{
			$("#dia-part_id").val($(obj).attr("PART_ID"));			//配件主键
			$("#dia-part_code").val($(obj).attr("PART_CODE"));		//配件代码
			$("#dia-part_name").val($(obj).attr("PART_NAME"));		//配件名称
	}
/*TD中放大镜按钮响应事件  */
	function openPurchase(flag)
	{
//		if(!$("#dia-SUPPLIER_ID").val()){
//			alertMsg.warn('请选择配件!')
//		}else{
			var options = {width:750,height:400,max:false,mask:true,mixable:false,minable:false,resizable:true,drawable:true}; 
			var url = webApps +"/jsp/dms/oem/part/basicInfo/ptbapicture/ptbapictureSel.jsp?flag="+flag;	
			$.pdialog.open(url, "ptbapictureSel", "配件查询", options);		
//		}
		
	}
 //下载附件
    $("#btn-downAtt").bind("click", function () {
        var promId = $("#dia-PROM_ID").val();
        if(!promId){
            alertMsg.warn('请先保存基本信息!');
            return;
        }
        $.filestore.view(promId);
    });	
	


</script>