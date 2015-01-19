<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>  
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<% 
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
 	String orgId=user.getOrgId();
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="dia-layout">
	<div class="page">
	<div class="pageContent" style="" >
		<!-- <form id="dia-fm-ju" method="post" type="hidden">	
	    	<input type="hidden" id="dia-WORK_STATUS-ju" name="dia-WORK_STATUS-ju" datasource="WORK_STATUS"  value=""/>
			<input type="hidden" id="dia-WORK_ID-ju" name="dia-WORK_ID-ju" datasource="WORK_ID" />	
		</form> -->
		<form method="post" id="dia-fm-option" class="editForm" style="width:100%;">
			<input type="hidden" id="dia-workId" name="dia-workId" datasource="WORK_ID" />
			<input type="hidden" id="dia-vehicleId" name="dia-vehicleId" datasource="VEHICLE_ID" />
			<input type="hidden" id="dia-licensePlate" name="dia-licensePlate" datasource="LICENSE_PLATE" />
			<input type="hidden" id="dia-workStatus" name="dia-workStatus" datasource="WORK_STATUS" />
			<input type="hidden" id="userIds" name="userIds" datasource="USER_IDS"/>
            <input type="hidden" id="userNames" name="userNames" datasource="USER_NAMES"/>
            <input type="hidden" id="mobils" name="mobils" datasource="MOBILS"/>
            <input type="hidden" id="flagId" name="flagId" datasource="FLAGID"/> 
            <input type="hidden" id="flagIf" name="flagIf" datasource="FLAGIF"/> 
			<div align="left">
			<fieldset>
				<table class="editTable" id="dia-tb-option">
					<tr>
						<td><label>派工单号：</label></td>
					    <td><input type="text" id="dia-WORK_NO" name="dia-WORK_NO" datasource="WORK_NO" datatype="1,is_null,30" readonly="readonly"/></td>
					    <td><label>客户姓名：</label></td>
					    <td><input type="text" id="dia-APPLY_USER" name="dia-APPLY_USER" datasource="APPLY_USER" datatype="1,is_null,30" readonly="readonly"/></td>
						<td><label>联系电话：</label></td>
					    <td><input type="text" id="dia-APPLY_MOBIL" name="dia-APPLY_MOBIL" datasource="APPLY_MOBIL" datatype="1,is_null,30" readonly="readonly" /></td>
					</tr>
				     <tr>
						<td><label>来电号码：</label></td>
					    <td><input type="text" id="dia-callNumber" name="dia-callNumber" datasource="CALL_NUMBER" datatype="1,is_null,30" readonly="readonly"/></td>
					    <td><label>400派工员姓名：</label></td>
					    <td><input type="text" id="dia-joborderOperator" name="dia-joborderOperator" datasource="JOBORDER_OPERATOR" datatype="1,is_null,30" readonly="readonly"/></td>
						<td><label>派工时间：</label></td>
					    <td><input type="text" id="dia-joborderTime" name="dia-joborderTime" datasource="JOBORDER_TIME" datatype="1,is_null,30" readonly="readonly" /></td>
					</tr>
					<tr>
					    <td><label>报修内容：</label></td>
					    <td colspan="5"><textarea id="dia-applyRemarks" style="width:450px;height:20px;" name="dia-applyRemarks"  datasource="APPLY_REMARKS" datatype="1,is_null,1000" readonly="readonly"></textarea></td>
					</tr>
					<tr>
						<td><label>是否外出：</label></td>
						<td><select type="text" id="dia-ifOut" name="dia-ifOut"  kind="dic" datasource="IF_OUT" class="combox" src="SF" datatype="0,is_null,8" value="-1" >	
					    		<option value="-1" selected>--</option>
					    	</select>
						</td>
						<td id="if_vn_flagH"><label>是否需服务车辆：</label></td>
						<td id="if_vn_flagC">
							<select type="text" id="dia-ifVehicle" name="dia-ifVehicle"  kind="dic" datasource="IF_VEHICLE" class="combox" src="SF" datatype="0,is_null,6" value="" >	
					    		<option value="-1" selected>--</option>
					    	</select>
						</td>
					    <td id="if_vehicH"><label>选择服务车辆：</label></td>
					    <td id="if_vehicC"> <input type="text" id="dia-vin" name="dia-vin" datasource="VIN" kind="dic" src="T#SE_BU_WORK_VEHICLES A:A.LICENSE_PLATE:A.LICENSE_PLATE{VEHICLE_ID}:1=1  AND A.VEHICLE_STATUS=100201 AND A.STATUS=100201 AND ORG_ID=<%=orgId%>" datatype="0,is_null,30" value=""/></td> 
					</tr>
					<tr>
					    <td><label>意见：</label></td>
					    <td colspan="3"><textarea id="dia-REMARKS" style="width:450px;height:40px;" name="dia-REMARKS"  datasource="REMARKS" datatype="1,is_null,1000"></textarea></td>
					</tr> 
				</table>
			  	</fieldset>
			</div>
			</form>		
			<div class="formBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-pg">派&nbsp;&nbsp;工</button></div></div></li>
                    <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-ju">拒&nbsp;&nbsp;绝</button></div></div></li>  
					<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
				</ul>
			</div>
		</div>
        <div class="pageHeader">
            <form method="post" id="fm-searchPart">
            	<input type="hidden" id="dia-orgId" name="dia-orgId" datasource="ORG_ID" />
            	<input type="hidden" id="dia-status" name="dia-status" datasource="STATUS" />
            	<input type="hidden" id="dia-user_status" name="dia-user_status" datasource="USER_STATUS" /> 
                <div class="searchBar" align="left">
                    <table class="searchContent" id="tab-searchUser">
                        <tr>
                            <td><label>电话号码：</label></td>
                            <td><input type="text" id="dia-MOBIL" name="dia-MOBIL" datasource="MOBIL" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>人员姓名：</label></td>
                            <td><input type="text" id="dia-USER_NAME" name="dia-USER_NAME" datasource="USER_NAME" datatype="1,is_null,30" operation="like"/></td>
                        	<td >
	                        	<div class="subBar">
			                        <ul>
			                            <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-searchUser">查&nbsp;&nbsp;询</button></div></div></li>
			                        </ul>
			                    </div>
                        	</td>
                        </tr>
                    </table>
                </div>
            </form>
        </div>
        <div class="pageContent">
            <div id="dispatchUser">
                <table style="display:none;width:100%;" id="dispatchUserList" name="dispatchUserList" ref="dispatchUser" refQuery="tab-searchUser">
                    <thead>
                        <tr>
                            <th type="multi" name="XH"  style="" unique="USER_ID"></th>
							<th fieldname="USER_NAME" >人员姓名</th>
							<th fieldname="MOBIL" >联系电话</th>
							<th fieldname="IF_MAIN" refer="ifMain" >是否主修为人</th>
							<th fieldname="USER_STATUS" >人员状态</th> 
                           </tr>
                    </thead>
                </table>
            </div>
            <fieldset id="selectedPart" style="display:none">
                <legend align="left" >&nbsp;[已选定人员]</legend>
                <div id="userVals">
                    <table style="width:100%;">
                        <tr>
                            <td>
                                <textarea id="val0" name="multivals" style="width:80%;height:26px;display:" column="1" ></textarea>
                                <textarea style="width:400px;height:10px; display:" id="val1" name="multivals" readOnly></textarea>
                                <textarea style="width:400px;height:10px; display:" id="val2" name="multivals" readOnly></textarea>
                            </td>
                        </tr>
                    </table>
                </div>
            </fieldset>
        </div>
	</div>
<script type="text/javascript">
var diaSaveAction = "<%=request.getContextPath()%>/service/claimmng/WorkDispatchMngAction";
var userAction = "<%=request.getContextPath()%>/service/claimmng/WorkDispatchUerMngAction";
var diaAction = "<%=action%>";
$(function(){
	$("#dia-orgId").val(<%=user.getOrgId()%>);
	//派工
    $("#dia-pg").bind("click", function(event){
    	var $f = $("#dia-fm-option");
    	$("#dia-workStatus").val("302202"); 
	    $("#dia-workStatus").attr("code","302202");
	    if($("#dia-ifOut").attr("code") ==100101 ){
	    	if (submitForm($f) == false) return false;
	    }
	 	var userIds = $('#val0').val();
	 	var flagId = $("#flagId").val();
        if(!userIds){
        	alertMsg.warn('请选择派工人.');
            return false;
        }else if(flagId==''){
        	alertMsg.warn('请选择主修人.');
        	return false;
        }else{
	        $('#userIds').val(userIds);           
	        $("#userNames").val($('#val1').val());
	        $("#mobils").val($("#val2").val());
	   		var sCondition = {};
	   		sCondition = $("#dia-fm-option").combined(1) || {};
	   		var updateUrl = diaSaveAction + "/insertWorkDispatch.ajax";
	   		doNormalSubmit($f,updateUrl,"dia-pg",sCondition,diaUpdateCallBack);
        }
	});
	//拒绝派工
    $("#dia-ju").bind("click", function(event){
       // $("#dia-workId").val($("#dia-WORK_ID").val());
      	var $f = $("#dia-fm-option");
        $("#dia-workStatus").val("302205"); 
	    $("#dia-workStatus").attr("code","302205");
		var diaRemarks=$("#dia-REMARKS").val();
		if(diaRemarks.length == 0){
			alertMsg.info("请输入拒绝意见.");
			return false;
		}
		var sCondition = {};
		sCondition = $("#dia-fm-option").combined(1) || {};
		var updateUrl = diaSaveAction + "/updateWorkOrder.ajax";
		doNormalSubmit($f,updateUrl,"dia-ju",sCondition,diaUpdateCallBack);
	});
	//派工人员查询
	$("#btn-searchUser").bind("click", function (event) {
		$("#flagId").val("");
	    $("#flagIf").val("");
	    $("#val0").val("");
	    $("#val1").val("");
	    $("#val2").val("");
		$("#dia-status").val("<%=DicConstant.YXBS_01%>"); 
		$("#dia-user_status").val("<%=DicConstant.YXBS_01%>"); 
		var $f = $("#fm-searchPart");
	    var sCondition = {};
	    sCondition = $f.combined() || {};
	    var searchUrl =userAction+"/search.ajax";
	    doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "dispatchUserList");
	});
	  //修改操作
	if(diaAction != "1")
	{
		var selectedRows = $("#tab-list").getSelectedRows();
		setEditValue("dia-fm-option",selectedRows[0].attr("rowdata"));
		var ifOut=selectedRows[0].attr("IF_OUT");
		if(ifOut==100102){
			$("#if_vn_flagH").hide();
			$("#if_vn_flagC").hide();
			$("#if_vehicH").hide();
			$("#if_vehicC").hide();
		}else{
			setDiaDefaultValue();
		}
	}
});

function setDiaDefaultValue()
{
	//是否
	$("#dia-ifVehicle").val("<%=DicConstant.SF_01%>");
	$("#dia-ifVehicle").attr("code","<%=DicConstant.SF_01%>");
	$("#dia-ifVehicle").find("option").val("<%=DicConstant.SF_01%>");
	$("#dia-ifVehicle").find("option").text("是");
	$("#dia-ifVehicle").attr("readonly",true);
	
	//是否
	$("#dia-ifOut").val("<%=DicConstant.SF_01%>");
	$("#dia-ifOut").attr("code","<%=DicConstant.SF_01%>");
	$("#dia-ifOut").find("option").val("<%=DicConstant.SF_01%>");
	$("#dia-ifOut").find("option").text("是");
	$("#dia-ifOut").attr("readonly",true);
}
function setDiaDefaultValue1()
{
	//是否
	$("#dia-ifVehicle").val("<%=DicConstant.SF_01%>");
	$("#dia-ifVehicle").attr("code","<%=DicConstant.SF_01%>");
	$("#op_combox_dia-ifVehicle").find(".selected").removeClass("selected");
	$("#op_combox_dia-ifVehicle").find("a[value='<%=DicConstant.SF_01%>']").addClass("selected");
	$("a[name='dia-ifVehicle']").text("是");
}
//修改回调
function diaUpdateCallBack(res){
	try
	{		
		var $row = $("#tab-list").getSelectedRows();
		if($row[0]){
			$("#tab-list").removeResult($row[0]);
			$.pdialog.closeCurrent();
			}
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//表选字典回调方法
function afterDicItemClick(id, $row, selIndex){
	
	//判断id中是否包含dia-D_ORGCODE的值
	if(id.indexOf("dia-vin") == 0)
	{
		if($row.attr("VEHICLE_ID")){
			$("#dia-vehicleId").val($row.attr("VEHICLE_ID"));
		}
	   	$("#dia-licensePlate").val($("#"+id).val());
	}
	if(id.indexOf("dia-ifVehicle") == 0)
	{	
    	if($("#dia-ifVehicle").attr("code") ==<%=DicConstant.SF_02%>){
    		$("#if_vehicH").hide();
    		$("#if_vehicC").hide();
    	}else if($("#dia-ifVehicle").attr("code")==<%=DicConstant.SF_01%>){
    		$("#if_vehicH").show();
    		$("#if_vehicC").show();
    	}
	}
	if(id.indexOf("dia-ifOut")==0){
		if($("#dia-ifOut").attr("code")==100101){
			$("#if_vn_flagH").show();
			$("#if_vn_flagC").show();
			$("#if_vehicH").show();
    		$("#if_vehicC").show();
			setDiaDefaultValue1();
		}else{
			$("#if_vehicH").hide();
    		$("#if_vehicC").hide();
			$("#if_vn_flagH").hide();
			$("#if_vn_flagC").hide();
		}
	}
	return true;
}


function ifMain(obj)
{
    return "<input type='radio' style='width:50px;' id='IF_MAIN' name='IF_MAIN' datasource='IF_MAIN' onClick='selectck(this);' maxlength='6'/>";
} 
 var tempradio= null;
function selectck(obj){
	var $obj = $(obj);//转换成jquery对象
	var $tr = $obj.parent().parent().parent();
	var checkObj = $("input:first",$tr.find("td").eq(1));
	if(tempradio== obj){
    	tempradio.checked=false;  
        tempradio=null;  
        checkObj.attr("checked", false);
        doCheckbox(checkObj.get(0),1);	
    }else{
        tempradio= obj; 
		checkObj.attr("checked", true);
		doCheckbox(checkObj.get(0),2);	 
    }  
}
function doCheckbox(checkbox,flag){  
	var $tr = $(checkbox).parent().parent().parent();
	var arr = [];
    arr.push($tr.attr("USER_ID"));
   	arr.push($tr.attr("USER_NAME"));
   	arr.push($tr.attr("MOBIL"));
   	var $checkboxx = $tr.find("td").eq(4).find("input[type='radio']:first");
   	var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
   	if($checkboxx.attr("checked")== "checked"){
   		if(flag==2){
   			$("#flagIf").val(100101);
   	    	$("#flagId").val($tr.attr("USER_ID"));
   		}else{
   			if($checkbox.attr("checked") == "checked"){
   				$("#flagIf").val(100101);
   	   	    	$("#flagId").val($tr.attr("USER_ID"));
   			}else{
   				$("#flagIf").val("");
   	   	    	$("#flagId").val("");
   			}
   		}
   	}else{
   		if(flag==1){
   			$("#flagIf").val("");
   	  	    $("#flagId").val("");
   		}
   	}
   	
	multiSelected($checkbox, arr,$("#userVals"));
}
/* function doMyInputBlur(obj){
    var $obj = $(obj);
    var $tr = $obj.parent().parent().parent();
    var checkObj = $("input:first",$tr.find("td").eq(1));
    var s = "";
	var $checkboxx = $tr.find("td").eq(4).find("input[type='checkbox']:first");
    if($checkboxx.attr("checked")){
    	s = 100102;
    }else{
    	s = 100102;
    }
    if(s)
    {
      	doCheckbox(checkObj.get(0));
    }
} */
/* function doCheckbox(checkbox){  
	var $tr = $(checkbox).parent().parent().parent();
    var arr = [];
    arr.push($tr.attr("USER_ID"));
	arr.push($tr.attr("USER_NAME"));
	arr.push($tr.attr("MOBIL"));
    var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
    multiSelected($checkbox, arr,$("#userVals"));
} */
//列表复选
/* function doCheckbox(checkbox){  
	var $t = $(checkbox);
	var arr = [];
   	while($t.get(0).tagName != "TABLE"){
		$t = $t.parent();
	} 
	if($t.attr("id").indexOf("tab-partList")==0){
		var $tr = $(checkbox).parent().parent().parent();
		var mxid = $(checkbox).val();
		var s = "";
		var $checkboxx = $tr.find("td").eq(5).find("input[type='radio']:first");
        if($checkboxx.attr("checked"))
        {
        	s = 100101;
	       // $tr.find("td input[type=checkbox]").attr("checked",true); 
        }else{
        	 s = 100102;
        }
		arr.push($tr.attr("USER_ID"));
		arr.push($tr.attr("USER_NAME"));
		arr.push($tr.attr("MOBIL"));
		arr.push(s);		
		var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
		multiSelected($checkbox,arr,$("#partVals"));
	} 
}	 */
//input框焦点移开事件 步骤一
/*  */
</script>
</div>

