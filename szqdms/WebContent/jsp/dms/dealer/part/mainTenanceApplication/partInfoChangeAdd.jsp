<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<style type="text/css">
.searchBar table{
	border-collapse:collapse!important;
}
.searchContent tr th{
	background:#E2F0FF url(<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()%>/themes/default/images/grid/header-bg.gif) repeat-x left -1px;
	border-color:#A3C0E8;
	color:#00579b;
	font-size:13px;
	font-weight:normal;
	height:25px;
	line-height:25px;
	padding:0 3px;
	vertical-align:top;
	white-space:nowrap;
}
.searchContent tr td, .searchContent tr th{
	border: 1px solid #99BBE8;
}
.searchContent tr td{
	line-height:24px;
	white-space:nowrap;
	overflow:hidden;
	padding:0 3px;
	vertical-align:middle;
	background-color:white;
}

.searchContent tr td:first-child{
	TEXT-ALIGN: center; BORDER-LEFT: #99bbe8 1px solid; BACKGROUND-COLOR: #e0ecff;
}

.errorInput {
	border-color: red;
}
.inputStyle{
	margin: 0px;
}
</style>
</head>
<body>
	<div id="layout" style="width: 100%;">
		<div class="page">
			<div class="pageContent">

				<div class="panelBar">
					<ul class="toolBar">
						<li><a class="add" href="javascript:addTr()" ><span>添加</span></a></li>
						<li><a class="delete" href="javascript:removeRow()" title="确定要删除吗?"><span>删除</span></a></li>
					</ul>
				</div>
				<div class="searchBar" align="left">
					<table class="searchContent">
						<!-- 定义查询条件 -->
						<thead>
							<tr>
								<th width="30"><input type="checkbox" style="margin: 0px" id="allCheckbox"/></th>
								<th width="120">原零件编号</th>
								<th >原零件名称</th>
								<th width="120">车辆识别码</th>
								<th width="120">更新编号</th>
								<th >更新名称</th>
								<th width="150">更新（禁用）原因</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><input type="checkbox" style="margin: 0px" onclick="checkBoxClick(this)"/></td>
								<td><input type="text" class="textInput" style="margin: 0px;" name="OLD_PART_NO_D"/><span style='color:red'>*</span></td>
								<td><input type="text" class="textInput" style="margin: 0px;" name="OLD_PART_NAME_D"/><span style='color:red'>*</span></td>
								<td><input type="text" class="textInput"style="margin: 0px" name="VIN"/></td>
								<td><input type="text" class="textInput" style="margin: 0px;" name="NEW_PART_NO_D" /></td>
								<td><input type="text" class="textInput" style="margin: 0px;" name="NEW_PART_NAME_D" /></td>
								<td><input type="text" class="textInput" style="margin: 0px;" name="REMARK_D"/><span style='color:red'>*</span></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
<form id="applicationForm" style="display:none" method="post">
	<!-- 申请人 -->
	<input type="hidden" name="APPLICATION_PERSON_A" id="APPLICATION_PERSON_A" datasource="APPLICATION_PERSON_A" val=""/>
	<!-- 申请单位 -->
	<input type="hidden" name="APPLICATION_WORK_A" id="APPLICATION_WORK_A" datasource="APPLICATION_WORK_A" val=""/>
	<!-- 申请人联系方式 -->
	<input type="hidden" name="APPLICATION_INFOMATION_A" id="APPLICATION_INFOMATION_A" datasource="APPLICATION_INFOMATION_A" val=""/>
	<!-- 申请单类型 -->
	<input type="hidden" name="APPLICATION_TYPE_A" id="APPLICATION_TYPE_A" datasource="APPLICATION_TYPE_A" val=""/>
	<!-- 原零件编号 -->
	<input type="hidden" name="OLD_PART_NO_A" id="OLD_PART_NO_A" datasource="OLD_PART_NO_A" val="" />
	<!-- 原零件名称 -->
	<input type="hidden" name="OLD_PART_NAME_A" id="OLD_PART_NAME_A" datasource="OLD_PART_NAME_A" value="" />
	<!-- VIN -->
	<input type="hidden" name="VIN_A" id="VIN_A" datasource="VIN_A" value="" />
	<!-- 更新编号 -->
	<input type="hidden" id="NEW_PART_NO_A" name="NEW_PART_NO_A"  dataSource="NEW_PART_NO_A" />
	<!-- 更新名称-->
	<input type="hidden" id="NEW_PART_NAME_A" name="NEW_PART_NAME_A"  dataSource="NEW_PART_NAME_A" />
	<!-- 更新（禁用）原因-->
	<input type="hidden" id="REMARK_A" name="REMARK_A"  dataSource="REMARK_A" />
</form>
<script type="text/javascript">

// 为表格添加新的行内容
function addTr(){
	var trHtml =    "<tr>"+
					"	<td><input type='checkbox' style='margin: 0px' onclick='checkBoxClick(this)'/></td>"+
					"	<td><input type='text' class='textInput' style='margin: 0px;' name='OLD_PART_NO_D'/><span style='color:red'>*</span></td>"+
					"	<td><input type='text' class='textInput' style='margin: 0px;' name='OLD_PART_NAME_D'/><span style='color:red'>*</span></td>"+
					"	<td><input type='text' class='textInput' style='margin: 0px;' name='VIN'/></td>"+
					"	<td><input type='text' class='textInput' style='margin: 0px;' name='NEW_PART_NO_D' /></td>"+
					"	<td><input type='text' class='textInput' style='margin: 0px;' name='NEW_PART_NAME_D' /></td>"+
					"	<td><input type='text' class='textInput' style='margin: 0px;' name='REMARK_D'/><span style='color:red'>*</span></td>"+
					"</tr>"
	$(".searchContent tbody").append(trHtml);
					
	changeParentIframeHeight();
}

// 删除选中行
function removeRow(){
	var selectCheckBox = $("input[type=checkbox]:checked",".searchContent");
	if(selectCheckBox.size() == 0){
		parent.alertMsg.error("请选择需要删除的行");
	} else {
		selectCheckBox.each(function(index,obj){
			$(obj).parent().parent().remove();
		})
		
		// 根据删除后checkbox状态初始化全选按钮
		checkBoxClick(null);
	}
}

// 设置全部CheckBoox的选中状态=参数status
function initCheckBoxStatus(status){
	$("input[type=checkbox]",".searchContent").each(function(index,checkBoxObj){
		$(checkBoxObj).prop("checked",status);
	})
}

// checkbox点击触发函数：用于判断是否改变全选checkbox的状态
function checkBoxClick(obj){
	var allCheckboxSize = $("input[type=checkbox]",".searchContent").size();
	var checkedBoxSize = $("input:checked",".searchContent").size();
	if(obj){
		var checkedStatus = $(obj).prop("checked");
		if(checkedStatus && allCheckboxSize == checkedBoxSize){
			$("#allCheckbox").prop("checked",true);
		}else if(!checkedStatus && checkedBoxSize != allCheckboxSize){
			$("#allCheckbox").prop("checked",false);
		}
	}else{
		if(allCheckboxSize == checkedBoxSize && allCheckboxSize != 0){
			$("#allCheckbox").prop("checked",true);
		}else if(checkedBoxSize != allCheckboxSize || allCheckboxSize == 0){
			$("#allCheckbox").prop("checked",false);
		}
	}
}

// 校验子页面的必填项
function validateSubpage(){
	var result = true;
	
	var inputArray = $("input[type=text]", ".searchContent");
	if(inputArray.size() == 0){
		parent.alertMsg.warn("请添加配件信息");
		result = false;
	} else {
		inputArray.each(function(index, inputObj){
			var inputVal = $(inputObj).val();
			if($.trim(inputVal) == "" && $(inputObj).attr("name") != "VIN"){
				var message = "";
				if($(inputObj).attr("name") == "OLD_PART_NO_D"){
					message = "请填写原零件编号";
					result = false;
				} else if($(inputObj).attr("name") == "OLD_PART_NAME_D"){
					message = "请填写原零件名称";
					result = false;
				} else if($(inputObj).attr("name") == "REMARK_D"){
					message = "请填写更新（禁用）原因";
					result = false;
				}
				
				if(result == false){
					$(inputObj).addClass("errorInput");
					parent.alertMsg.warn(message);
					return false;
				} else {
					$(inputObj).removeClass("errorInput");
				}
				
				// 获取当前行对象
				var trObj = $(inputObj).parent().parent();
				var $NEW_PART_NO = $("input[name=NEW_PART_NO_D]",trObj);
				var $NEW_PART_NAME = $("input[name=NEW_PART_NAME_D]",trObj);
				
				if(($.trim($NEW_PART_NO.val()) == "" && $.trim($NEW_PART_NAME.val()) != "") || 
						($.trim($NEW_PART_NO.val()) != "" && $.trim($NEW_PART_NAME.val()) == "")){
					message = "请填写完整的更新零件信息";
					$NEW_PART_NO.addClass("errorInput");
					$NEW_PART_NAME.addClass("errorInput");
					parent.alertMsg.warn(message);
					result = false;
					return false;
				}else{
					$NEW_PART_NO.removeClass("errorInput");
					$NEW_PART_NAME.removeClass("errorInput");
				}
			}else{
				$(inputObj).removeClass("errorInput");
				// 零件编号校验
				if($(inputObj).attr("name") == "OLD_PART_NO_D" || $(inputObj).attr("name") == "NEW_PART_NO_D"){
					
					// 只能为数字或字母
					if(is_null($(inputObj)) !== true){
						message = "请填写正确的零件编号";
						$(inputObj).addClass("errorInput");
						parent.alertMsg.warn(message);
						result = false;
						return false;
					} else {
						$(inputObj).removeClass("errorInput");
					}
					
				}
				
				// VIN校验
				if($(inputObj).attr("name") == "VIN" && $.trim($(inputObj).val()) != ""){
					if(is_vin($(inputObj)) !== true){
						message = "请填写正确的车辆识别码";
						$(inputObj).addClass("errorInput");
						parent.alertMsg.warn(message);
						result = false;
						return false;
					} else {
						$(inputObj).removeClass("errorInput");
					}
				}
				
			}
		})
	}
	return result;
}

// 初始化提交表单的数据，将需要保存至后台的数据保存至form表单中
function initSaveForm(){
	
	// 获取父页面的值，并将值赋予子页面中提交表单的隐藏域中
	$("#APPLICATION_PERSON_A").val($.trim($("#APPLICATION_PERSON_A",parent.document).val())); 		   // 申请人
	$("#APPLICATION_WORK_A").val($.trim($("#APPLICATION_WORK_A",parent.document).val())); 	  		   // 申请单位
	$("#APPLICATION_INFOMATION_A").val($.trim($("#APPLICATION_INFOMATION_A",parent.document).val()));  // 申请人联系方式
	$("#APPLICATION_TYPE_A").val($.trim($("#APPLICATION_TYPE_A",parent.document).val())); 			   // 申请单类型
	
	// 调用子页面的初始化数据
	initSaveSubpageData();
}

// 初始化子页面中提交的数据
function initSaveSubpageData(){
	var oldPartNo = "";
	var oldPartName = "";
	var vinStr = "";
	var newPartNo = "";
	var newPartName = "";
	var remarks = "";
	
	var inputArray = $("input[type=text]", ".searchContent");
	inputArray.each(function(index, inputObj){
		var inputVal = $.trim($(inputObj).val() == null ? "" : $(inputObj).val());
		if($(inputObj).attr("name") == "OLD_PART_NO_D"){
			oldPartNo += inputVal + ",";
		} else if($(inputObj).attr("name") == "OLD_PART_NAME_D"){
			oldPartName += inputVal + ",";
		} else if($(inputObj).attr("name") == "VIN"){
			vinStr += inputVal + ",";
		} else if($(inputObj).attr("name") == "NEW_PART_NO_D"){
			newPartNo += inputVal + ",";
		} else if($(inputObj).attr("name") == "NEW_PART_NAME_D"){
			newPartName += inputVal + ",";
		} else if($(inputObj).attr("name") == "REMARK_D"){
			remarks += inputVal + ",";
		}
	})
	oldPartNo = oldPartNo.substring(0, oldPartNo.length - 1);
	oldPartName = oldPartName.substring(0, oldPartName.length - 1);
	vinStr = vinStr.substring(0, vinStr.length - 1);
	newPartNo = newPartNo.substring(0, newPartNo.length - 1);
	newPartName = newPartName.substring(0, newPartName.length - 1);
	remarks = remarks.substring(0, remarks.length - 1);

	// 将获取的值保存至隐藏的form表单中
	$("#OLD_PART_NO_A").val(oldPartNo);
	$("#OLD_PART_NAME_A").val(oldPartName);
	$("#VIN_A").val(vinStr);
	$("#NEW_PART_NO_A").val(newPartNo);
	$("#NEW_PART_NAME_A").val(newPartName);
	$("#REMARK_A").val(remarks);
}

function saveForm(){
	initSaveForm(); // 调用初始化表单数据
	var saveActionURL = "";
	var $f = $("#applicationForm"); // 保存操作
	sCondition = $f.combined(1) || {};
	var saveActionURL = "<%=request.getContextPath()%>/part/mainTenanceApplication/MainTenanceApplicationAction/saveApplication.ajax";
	doNormalSubmit($f, saveActionURL, "allCheckbox", sCondition, function(responseText,tabId,sParam){
    	$("#btn-search",parent.document).click();
    	parent.alertMsg.correct("保存成功");
		$("#applicationAddCloseBtn",parent.document).click();
    }); 
}

$(function(){
	
	// 为全选checkbox按钮绑定click事件：根据全选box的状态初始化全部checbox的状态
	$("#allCheckbox").click(function(){
		var checkedStatus = $(this).prop("checked");
		initCheckBoxStatus(checkedStatus);
	})
})

// 改变Iframe高度
var $parentIframe = $("#applicationTypeFrame",parent.document);
$parentIframe.height($("body").height() + 100);

function changeParentIframeHeight(){
	var height = $("tr","body").height() * $("tr","body").size();
	$parentIframe.height($("body").height() + height + 100);
}
</script>
</html>
