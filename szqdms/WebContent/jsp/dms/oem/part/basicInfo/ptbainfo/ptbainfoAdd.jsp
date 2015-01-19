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
				<legend align="right"><a onclick="onTitleClick('dia-tab-pjxx')">&nbsp;配件档案信息编辑&gt;&gt;</a></legend>
				<table class="editTable" id="dia-tab-pjxx">							
					<tr>
					    <td><label>配件代码：</label></td>
					    <td><input type="text" id="dia-in-pjdm"  name="dia-in-pjdm" datasource="PART_CODE" datatype="0,is_null,300" value=""/>					   			    
					    </td>					    
					     <td><label>配件名称：</label></td>
					    <td><input type="text" id="dia-in-pjmc"  name="dia-in-pjmc" datasource="PART_NAME" datatype="0,is_null,300" value=""/></td>					    
					</tr>												
					<tr>			
						<td><label>参图号：</label></td>
					    <td><input type="text" id="dia-in-cth"  name="dia-in-cth" datasource="PART_NO" datatype="1,is_digit_letter,100" value=""/></td>		    
<!--					    <td><label>返利类别：</label></td>-->
<!--					    <td><input type="text" id="dia-parakey"  name="dia-parakey" kind="dic"  src="T#USER_PARA_CONFIGURE:PARAKEY:PARAVALUE1{SN,PARAKEY,PARAVALUE1}:1=1 AND  APPTYPE = 'FLLB'  ORDER BY PARAKEY" datasource="PARAKEY" datatype="1,is_null,30" />-->
<!--					    	<input type="hidden" id="dia-rebate_type" name="dia-rebate_type" datasource="REBATE_TYPE"/>-->
<!--							<input type="hidden" id="dia-rebate_paravalue1" name="dia-rebate_paravalue1" datasource="REBATE_PARAVALUE1"/>-->
<!--					    </td>-->
						<td><label>返利类别：</label></td>
					    <td><input type="text" id="dia-parakey"  name="dia-parakey" kind="dic"  src="T#USER_PARA_CONFIGURE:PARAVALUE1:PARAKEY{PARAVALUE1,PARAKEY}:1=1 AND  APPTYPE = 'FLLB'  ORDER BY PARAKEY" datasource="PARAKEY" datatype="1,is_null,100" />
					    	<input type="hidden" id="dia-rebate_type" name="dia-rebate_type" datasource="REBATE_TYPE"/>
<!--							<input type="hidden" id="dia-rebate_paravalue1" name="dia-rebate_paravalue1" datasource="REBATE_PARAVALUE1"/>-->
					    </td>
					</tr>							
					<tr>
						<td><label>配件类型：</label></td>						    
					    <td>
					    	<input type="text" id="dia-in-pjlx"  name="dia-in-pjlx" datasource="PART_TYPE" kind="dic" src="PJLB"  datatype="0,is_null,100" value=""/>
					    	<input id="dia-out" type="hidden"/>	
					    </td>
					    <td><label>配件属性：</label></td>
					    <td>
					    	<input type="text" id="dia-in-pjsx"  name="dia-in-pjsx" datasource="ATTRIBUTE" kind="dic" src="PJSX"  datatype="1,is_null,100" value=""/>
					    	<input id="dia-out" type="hidden"/>	
					    </td>				
					</tr>	
					<tr>
					    <td><label>父级零件编号：</label></td>
					    <td><input type="text" id="dia-f_part_code"  name="dia-f_part_code" datasource="F_PART_CODE" datatype="1,is_digit_letter,300" hasBtn="true" callFunction="openPurchase(1);" readonly="true"/>					   			    
					    	<input type="hidden" id="dia-f_part_id" name="dia-f_part_id" datasource="F_PART_ID"/>
					    </td>
					     <td><label>父级零件名称：</label></td>
					    <td><input type="text" id="dia-f_part_name"  name="dia-f_part_name" datasource="F_PART_NAME" datatype="1,is_null,300" readonly="true"  value=""/></td>
					</tr>	
					<tr>
						<td><label>一级总成：</label></td>
					    <td><input type="text" id="dia-position_code"  name="dia-position_code" kind="dic"  src="T#SE_BA_VEHICLE_POSITION:POSITION_CODE:POSITION_NAME{POSITION_ID,POSITION_NAME}:1=1  AND POSITION_LEVEL=1 ORDER BY POSITION_CODE" datasource="POSITION_CODE" datatype="1,is_null,300" />
							<input type="hidden" id="dia-position_id" name="dia-position_id" datasource="POSITION_ID"/>
							<input type="hidden" id="dia-position_name" name="dia-position_name" datasource="POSITION_NAME"/>
					    </td>
					    <td><label>二级总成：</label></td>
					    <td><input type="text" id="dia-f_position_name"  name="dia-f_position_name" datasource="F_POSITION_NAME" datatype="1,is_null,300" hasBtn="true" callFunction="openPurchaseTwo(2);" readonly="true"" />					   			    
					    	<input type="hidden" id="dia-f_position_id" name="dia-f_position_id" datasource="F_POSITION_ID"/>
					    	<input type="hidden" id="dia-f_position_code" name="dia-f_position_code" datasource="F_POSITION_CODE"/>
					    </td>
					</tr>	
					<tr>	
					    <td><label>最小包装数：</label></td>
					    <td><input type="text" id="dia-in-zxbzs"  name="dia-in-zxbzs" datasource="MIN_PACK" datatype="0,is_digit,30" value=""/></td>
					    <td><label>最小包装单位：</label></td>
					    <td><input type="text" id="dia-in-zxbzdw"  name="dia-in-zxbzdw" kind="dic" src="JLDW" datasource="MIN_UNIT" datatype="0,is_null,30" value=""/><input id="test" type="hidden" /></td>
					</tr>
					<tr>
						<td><label>直发类型：</label></td>
					    <td>
					    	<input type="text" id="dia-type_code"  name="dia-type_code" kind="dic" src="T#PT_BA_DIRECT_TYPE:TYPE_CODE:TYPE_NAME{TYPE_ID,TYPE_NAME,TYPE_CODE}:1=1 AND STATUS=<%=DicConstant.YXBS_01 %> ORDER BY TYPE_CODE" datasource="TYPE_CODE" datatype="1,is_null,300" />		
					    	<input type="hidden" id="dia-direct_type_id" name="dia-direct_type_id" datasource="DIRECT_TYPE_ID"/>
					    	<input type="hidden" id="dia-direct_type_code" name="dia-direct_type_code" datasource="DIRECT_TYPE_CODE"/>
                       	    <input type="hidden" id="dia-direct_type_name" name="dia-direct_type_name" datasource="DIRECT_TYPE_NAME"/>
					    </td>
					    <td><label>配件特殊分类：</label></td>
					    <td><input type="text" id="dia-spe_parakey"  name="dia-spe_parakey" kind="dic"  src="T#USER_PARA_CONFIGURE:PARAKEY:PARANAME{PARAKEY,PARANAME}:1=1 AND  APPTYPE = '2004'  ORDER BY PARAKEY" datasource="PARAKEY" datatype="1,is_null,300" />
					    	<input type="hidden" id="dia-spe_type" name="dia-spe_type" datasource="SPE_TYPE"/>
							<input type="hidden" id="dia-spe_name" name="dia-spe_name" datasource="SPE_NAME"/>
					    </td>		
					</tr>
					<tr>
						<td><label>所属七大总成：</label></td>
						<td>					    	
					    	<select type="text" id="dia-belong_assembly"  name="dia-belong_assembly" kind="dic" src="PJZCLB" datasource="BELONG_ASSEMBLY"  datatype="1,is_null,300" >		    							    	
					    		<option value="-1" selected>--</option>
					    	</select>
					    	<input id="dia-out" type="hidden"/>		    	
					    </td> 					  					    
						<td><label>是否大总成：</label></td>
						<td>
					    	<input type="text" id="dia-in-sfdzc"  name="dia-in-sfdzc" kind="dic" src="SF" datasource="IF_ASSEMBLY"  datatype="0,is_null,100" />
					    </td>					   
						<td><label>是否保外：</label></td>
					    <td>
					    	<select type="text" id="dia-in-sfbw" name="dia-in-sfbw" kind="dic" src="SF" datasource="IF_OUT" datatype="0,is_null,100" >
					    		<option value="-1" selected>--</option>
					    	</select>
					    </td>							    
					</tr>
						
					<tr>
					    <td><label>计量单位：</label></td>
					    <td>
					    	<select type="text" id="dia-in-jldw" class="combox" dicwidth="300" name="dia-in-jldw" kind="dic" src="JLDW" datasource="UNIT"  datatype="0,is_null,100" > 
					    		<option value="-1" selected>--</option>
					    	</select>		
					    </td>		
						<td><label>是否扫码件：</label></td>
						<td>
					    	<select type="text" id="dia-in-sfsmj"  name="dia-in-sfsmj" kind="dic" src="SF" datasource="IF_SCAN"  datatype="0,is_null,100" >
					    		<option value="-1" selected>--</option>
					    	</select>
					    </td>								
						<td><label>是否指定供应商：</label></td>
						<td>
					    	<select type="text" id="dia-in-sfzdgys"  name="dia-in-sfzdgys" kind="dic" src="SF" datasource="IF_SUPLY"  datatype="0,is_null,100" >
					    		<option value="-1" selected>--</option>
					    	</select>
					    </td>					
										   				    
					</tr>	
					<tr>
						<td><label>配件状态：</label></td>
					    <td>
					    	<select type="text" id="dia-part_status"  name="dia-part_status" kind="dic" src="PJZT" datasource="PART_STATUS"  datatype="0,is_null,100" >
					    		<option value="-1" selected>--</option>
					    	</select>
					    </td>		    					    
						<td><label>是否油品：</label></td>
					    <td>
					    	<select type="text" id="dia-if_oil" name="dia-if_oil" kind="dic" src="SF" datasource="IF_OIL" datatype="0,is_null,100" >
					    		<option value="-1" selected>--</option>
					    	</select>
					    </td>		
					</tr>	
					<tr>
						<td><label>备注：</label></td>
						<td colspan="4"><textarea id="dia-ta-bz" style="width:89%;height:40px;" name="dia-ta-bz" datasource="REMARKS"  datatype="1,is_null,1000"></textarea></td>
					</tr>
				</table>
				</fieldset>	
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button  id="btn-save" type="button">保&nbsp;&nbsp;存</button></div></div></li>
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
var diaSaveAction = "<%=request.getContextPath()%>/part/basicInfoMng/PtBaInfoAction";
var diaAction = "<%=action%>";



//初始化
$(function(){

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
        
        //$("#dia-parakey").attr("code",selectedRows[0].attr("REBATE_TYPE"));
        //$("#dia-parakey").val(selectedRows[0].attr("REBATE_PARAVALUE1"));
        //$("#dia-rebate_type").val(selectedRows[0].attr("REBATE_TYPE"));
        //$("#dia-rebate_paravalue1").val(selectedRows[0].attr("REBATE_PARAVALUE1"));
        
        $("#dia-parakey").attr("code",selectedRows[0].attr("REBATE_TYPE"));
        $("#dia-parakey").val(selectedRows[0].attr("REBATE_TYPE"));
        //$("#dia-parakey").val(selectedRows[0].attr("REBATE_PARAVALUE1"));
        //$("#dia-rebate_type").val(selectedRows[0].attr("REBATE_TYPE"));
        //$("#dia-rebate_paravalue1").val(selectedRows[0].attr("REBATE_PARAVALUE1"));
        
        $("#dia-spe_parakey").attr("code",selectedRows[0].attr("SPE_TYPE"));
        $("#dia-spe_parakey").val(selectedRows[0].attr("SPE_NAME"));
        $("#dia-spe_type").val(selectedRows[0].attr("SPE_TYPE"));
        $("#dia-spe_name").val(selectedRows[0].attr("SPE_NAME"));
        
        $("#dia-position_code").attr("code",selectedRows[0].attr("POSITION_CODE"));
        $("#dia-position_code").val(selectedRows[0].attr("POSITION_NAME"));
        $("#dia-position_id").val(selectedRows[0].attr("POSITION_ID"));
        $("#dia-position_name").val(selectedRows[0].attr("POSITION_NAME"));
        
        
         
        
		//车辆总成字典表数据显示		
//		var positioncode=getNodeValue(objXML, "POSITION_CODE", 0);
//		var positionname=getNodeValue(objXML, "POSITION_NAME", 0);
//		$("#dia-position_code").val(positionname);
//		$("#dia-position_code").attr("code",positioncode);
		
//		var parakey=getNodeValue(objXML, "PARAKEY", 0);
//		var paravalue1=getNodeValue(objXML, "PARAVALUE1", 0);
//		$("#dia-parakey").val(paravalue1);
//		$("#dia-parakey").attr("code",parakey);
		
	}else
	{			
		setDiaDefaultValue();
	}
	
	
	//绑定保存按钮
	$("#btn-save").bind("click", function(event){
		// 替换配件名称及代码中的英文,为中文"，"
		$("#dia-in-pjdm").val($("#dia-in-pjdm").val().replace(/\,/g, "，"));
		$("#dia-in-pjmc").val($("#dia-in-pjmc").val().replace(/\,/g, "，"));
		
		if ($("#dia-type_code").val()=="") {
			$("#dia-direct_type_id").val("");
			$("#dia-direct_type_code").val("");
			$("#dia-direct_type_name").val("");
		}else{
			var rate = $("#dia-rebate_type").val();
			if(!rate){
				 alertMsg.warn('直发类型配件需选择返利比率!');
				 return false
			}
		}
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
	
	$("#dia-close").bind("click",function(){
			$.pdialog.closeCurrent();
			return false;
		});
	
});

function setDiaDefaultValue()
	{
		$("#dia-if_oil").val("<%=DicConstant.SF_02%>");
		$("#dia-if_oil").attr("code","<%=DicConstant.SF_02%>");
		$("#dia-if_oil").find("option").val("<%=DicConstant.SF_02%>");
		$("#dia-if_oil").find("option").text("否");
		
		$("#dia-part_status").val("<%=DicConstant.PJZT_01%>");
		$("#dia-part_status").attr("code","<%=DicConstant.PJZT_01%>");
		$("#dia-part_status").find("option").val("<%=DicConstant.PJZT_01%>");
		$("#dia-part_status").find("option").text("有效");

	}

//表选字典回调方法
function afterDicItemClick(id, $row, selIndex){
	if(id == 'dia-type_code'){									
        $('#dia-direct_type_id').val($row.attr('TYPE_ID'));
        $('#dia-direct_type_code').val($row.attr('TYPE_CODE'));
        $('#dia-direct_type_name').val($row.attr('TYPE_NAME'));
    }	
	if (id == 'dia-belong_assembly') {
		if ($('#dia-belong_assembly').attr("code") == '-1') {
		    $('#dia-in-sfdzc').val("否");
		    $('#dia-in-sfdzc').attr("code",'<%=DicConstant.SF_02%>');
		} else {
			$('#dia-in-sfdzc').val("是");
		    $('#dia-in-sfdzc').attr("code",'<%=DicConstant.SF_01%>');
		}
	}
    //if(id == 'dia-parakey'){									
    //    $('#dia-rebate_type').val($row.attr('PARAKEY'));
    //    $('#dia-rebate_paravalue1').val($row.attr('PARAVALUE1'));
    //}	
    
     if(id == 'dia-parakey'){									
        $('#dia-rebate_type').val($row.attr('PARAKEY'));
        //$('#dia-rebate_paravalue1').val($row.attr('PARAVALUE1'));
    }	
    if(id == 'dia-spe_parakey'){									
        $('#dia-spe_type').val($row.attr('PARAKEY'));
        $('#dia-spe_name').val($row.attr('PARANAME'));
    }	
     if(id == 'dia-position_code'){									
        $('#dia-position_id').val($row.attr('POSITION_ID'));
        $('#dia-position_name').val($row.attr('POSITION_NAME'));
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
		var selectedIndex = $("#tab-pjlb").getSelectedIndex();
		$("#tab-pjlb").updateResult(res,selectedIndex);
		$("#btn-cx").trigger("click");

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
			$("#dia-f_part_id").val($(obj).attr("PART_ID"));			//父级零件ID
			$("#dia-f_part_code").val($(obj).attr("PART_CODE"));		//父级零件编号
			$("#dia-f_part_name").val($(obj).attr("PART_NAME"));		//父级零件名称
		}
		else{
			$("#dia-f_position_id").val($(obj).attr("POSITION_ID"));			//子级总成ID
			$("#dia-f_position_code").val($(obj).attr("POSITION_CODE"));		//子级总成编号
			$("#dia-f_position_name").val($(obj).attr("POSITION_NAME"));		//子级总成名称
		}
	}
/*TD中放大镜按钮响应事件  */
	function openPurchase(flag)
	{
//		if(!$("#dia-SUPPLIER_ID").val()){
//			alertMsg.warn('请选择配件!')
//		}else{
			var options = {width:750,height:400,max:false,mask:true,mixable:false,minable:false,resizable:true,drawable:true}; 
			var url = webApps +"/jsp/dms/oem/part/basicInfo/ptbainfo/ptbainfoSel.jsp?flag="+flag;	
			$.pdialog.open(url, "ptbainfoSel", "配件查询", options);		
//		}
		
	}
function openPurchaseTwo(flag)
	{
		if(!$("#dia-position_id").val()){
			alertMsg.warn('请选择所属总成!')
		}else{
//			var P_ID = $("#dia-position_id").val();
			var options = {width:750,height:400,max:false,mask:true,mixable:false,minable:false,resizable:true,drawable:true}; 
//			var url = webApps +"/jsp/dms/oem/part/basicInfo/ptbainfo/ptbainfoPositionSel.jsp?flag="+flag+"&P_ID="+P_ID;	
			var url = webApps +"/jsp/dms/oem/part/basicInfo/ptbainfo/ptbainfoPositionSel.jsp?flag="+flag;	
			$.pdialog.open(url, "ptbainfoPositionSel", "二级总成查询", options);		
		}
		
	}	

</script>