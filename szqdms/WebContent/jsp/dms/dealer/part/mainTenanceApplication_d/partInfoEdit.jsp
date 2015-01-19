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
								<th width="0"><input type="checkbox" style="margin: 0px" id="allCheckbox"/></th>
								<th colwidth="150px">零件编号</th>
								<th colwidth="150px">零件名称</th>
								<th colwidth="150px">车辆识别码</th>
								<th colwidth="200px">技术科审核备注</th>
								<th colwidth="200px">采购科审核备注</th>
							</tr>
						</thead>
						<tbody id="partInfoTbody">

						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
<form id="applicationForm" style="display:none" method="post">
	<!-- 车辆识别码 -->
	<input type="hidden" name="VIN_S_A" id="VIN_S_A" datasource="VIN_S_A"/>
	<!-- 修改动作 -->
	<input type="hidden" id="EDIT_ACTION" name="EDIT_ACTION" datatype="1,is_digit_letter,30" dataSource="EDIT_ACTION" value="YES"/>
	<!-- 申请单ID -->
	<input type="hidden" id="APPLICATION_ID_A" name="APPLICATION_ID_A" datatype="1,is_digit_letter,30" dataSource="APPLICATION_ID_A" />
	<!-- 申请人 -->
	<input type="hidden" name="APPLICATION_PERSON_A" id="APPLICATION_PERSON_A" datasource="APPLICATION_PERSON_A"/>
	<!-- 申请单位 -->
	<input type="hidden" name="APPLICATION_WORK_A" id="APPLICATION_WORK_A" datasource="APPLICATION_WORK_A" />
	<!-- 申请人联系方式 -->
	<input type="hidden" name="APPLICATION_INFOMATION_A" id="APPLICATION_INFOMATION_A" datasource="APPLICATION_INFOMATION_A" />
	<!-- 申请单类型 -->
	<input type="hidden" name="APPLICATION_TYPE_A" id="APPLICATION_TYPE_A" datasource="APPLICATION_TYPE_A" />
	<!-- 配件编号 -->
	<input type="hidden" name="PART_NO_S_A" id="PART_NO_S_A" datasource="PART_NO_S_A" />
	<!-- 配件名称 -->
	<input type="hidden" name="PART_NAME_S_A" id="PART_NAME_S_A" datasource="PART_NAME_S_A" />
	<!-- 配件信息录入子表ID -->
	<input type="hidden" name="ENTRY_ID_S_A" id="ENTRY_ID_S_A" datasource="ENTRY_ID_S_A" />
</form>
<script type="text/javascript">
// 为表格添加新的行内容
function addTr(){
	var trHtml = "<tr>"+
					"<td><input type='checkbox' style='margin: 0px'  onclick='checkBoxClick(this)'/><input type='hidden' name='ENTRY_ID'/></td>"+
					"<td><input type='text' class='textInput' style='margin: 0px;' name='PART_NO' requiredInput='y' /><span style='color:red'>*</span></td>"+
					"<td><input type='text' class='textInput' style='margin: 0px;' name='PART_NAME' requiredInput='y' /><span style='color:red'>*</span></td>"+
					"<td><input type='text' class='textInput' style='margin: 0px' name='VIN' /></td>"+
					"<td><input type='text' class='textInput' style='margin: 0px' readonly='readonly' name='ENGINEERING_DEPARTMENT_REMARK' /></td>"+
					"<td><input type='text' class='textInput' style='margin: 0px' readonly='readonly' name='PURCHASING_DEPARTMENT_REMARK' /></td>"+
				"</tr>";
	$(".searchContent tbody").append(trHtml);
	changeParentIframeHeight();
}
// 删除选中行
function removeRow(){
	var selectCheckBox = $("input[type=checkbox]:checked",".searchContent tbody tr");
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
	$("input[type=checkbox]",".searchContent tbody tr").each(function(index,checkBoxObj){
		$(checkBoxObj).prop("checked",status);
	})
}

// checkbox点击触发函数：用于判断是否改变全选checkbox的状态
function checkBoxClick(obj){
	var allCheckboxSize = $("input[type=checkbox]",".searchContent tbody tr").size();
	var checkedBoxSize = $("input:checked",".searchContent tbody tr").size();
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
	var partNoStr = "";
	var inputArray = $("input[type=text][requiredInput='y']", "#partInfoTbody");
	if(inputArray.size() == 0){
		parent.alertMsg.warn("请添加配件信息");
		result = false;
	} else {
		inputArray.each(function(index, inputObj){
			var inputVal = $(inputObj).val();
			if($.trim(inputVal) == "" && $(inputObj).attr("name") != "VIN"){
				$(inputObj).addClass("errorInput");
				var message = "请填写表单内容";
				if($(inputObj).attr("name") == "PART_NO"){
					message = "请填写零件编号";
				} else if($(inputObj).attr("name") == "PART_NAME"){
					message = "请填写零件名称";
				} 
				parent.alertMsg.warn(message);
				result = false;
				return false;
			}else{ // 如果数据不为空则做数据有效性校验
				var message = "请填写表单内容";
			
				// 零件编号校验
				if($(inputObj).attr("name") == "PART_NO"){
					// 零件号是否重复
					if(partNoStr.indexOf($(inputObj).val()) != -1){
						message = "申请的零件号重复";
						$(inputObj).addClass("errorInput");
						parent.alertMsg.warn(message);
						result = false;
						return false;
					}
					// 只能为数字或字母
					if(is_null($(inputObj)) !== true){
						message = "请填写正确的零件编号";
						$(inputObj).addClass("errorInput");
						parent.alertMsg.warn(message);
						result = false;
						return false;
					} else {
						partNoStr += $(inputObj).val() + ",";
						$(inputObj).removeClass("errorInput");
					}
					
				}
				
				// VIN校验
				if($(inputObj).attr("name") == "VIN"  && $.trim($(inputObj).val()) != ""){
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
	$("#APPLICATION_ID_A").val($("#APPLICATION_ID_D",parent.document).val());						   // 申请单ID
	$("#APPLICATION_PERSON_A").val($.trim($("#APPLICATION_PERSON_D",parent.document).val())); 		   // 申请人
	$("#APPLICATION_WORK_A").val($.trim($("#APPLICATION_WORK_D",parent.document).val())); 	  		   // 申请单位
	$("#APPLICATION_INFOMATION_A").val($.trim($("#APPLICATION_INFOMATION_D",parent.document).val()));  // 申请人联系方式
	$("#APPLICATION_TYPE_A").val($.trim($("#APPLICATION_TYPE_CODE_D",parent.document).val())); 		   // 申请单类型
	
	// 调用子页面的初始化数据
	initSaveSubpageData();
}

// 初始化子页面中提交的数据
function initSaveSubpageData(){
	var partNoArray = [],
		pageNameArray = [],
		vinArray = [],
		entryId = [];		 /* 子表行ID */
	var inputArray = $("input", ".searchContent");
	inputArray.each(function(index, inputObj){
		var inputVal = $.trim($(inputObj).val());
		if($(inputObj).attr("name") == "PART_NO"){
			partNoArray.push(inputVal);
		} else if($(inputObj).attr("name") == "PART_NAME"){
			pageNameArray.push(inputVal);
		} else if($(inputObj).attr("name") == "VIN"){
			vinArray.push(inputVal == "" ? "null" : inputVal);
		} else if($(inputObj).attr("name") == "ENTRY_ID"){
			entryId.push(inputVal == "" ? "null" : inputVal);
		}
	})
	
	// 将获取的值保存至隐藏的form表单中
	$("#PART_NO_S_A").val(partNoArray.join(","));
	$("#PART_NAME_S_A").val(pageNameArray.join(","));
	$("#VIN_S_A").val(vinArray.join(","));
	$("#ENTRY_ID_S_A").val(entryId.join(","));
}

//保存From表单
function saveForm(){
	initSaveForm(); // 调用初始化表单数据
	checkPartInfo(); // 检测配件是否存在
}

//提交表单，在检测配件存在后调用此方法提交
function comitForm(){
	var saveActionURL = "";
	var $f = $("#applicationForm"); // 保存操作
	sCondition = $f.combined(1) || {};
	var saveActionURL = "<%=request.getContextPath()%>/part/mainTenanceApplication/MainTenanceApplicationAction/saveApplication.ajax";
	doNormalSubmit($f, saveActionURL, "allCheckbox", sCondition, function(responseText,tabId,sParam){
    	$("#btn-search",parent.document).click();
    	parent.alertMsg.correct("保存成功");
		$("#editCloseBtn",parent.document).click();
    }); 
}

//检测配件是否存在
function checkPartInfo(){
	var partCodes = $("#PART_NO_S_A").val();
	var checkUrl = "<%=request.getContextPath()%>/part/mainTenanceApplication/MainTenanceApplicationAction/checkPartCodeExists.ajax"; // 查询ActionURL
	sendPost(checkUrl+"?partCodes="+partCodes,"","",callBackCheckFun,null,null);	
}

// 查询回调函数
function callBackCheckFun(res,sData){
	var applicationInfo;							// 此变量保存回调对象中包含的后台查询到的数据
	var explorer = window.navigator.userAgent;		// 判断浏览器
	if(explorer.indexOf("MSIE") != -1 && explorer.indexOf("MSIE 10.0") == -1){
		applicationInfo = res.text;
	}else{
		applicationInfo = res.firstChild.textContent;
	}
	
	// 调用显示主信息的函数
	showApplicationInfo(eval("(" + applicationInfo + ")"))
}

// 显示申请单主信息:返回true标示配件已存在，false标示不存在
function showApplicationInfo(jsonObj){
	// result = true 标示配件已存在，false标示配件不存在
	if(jsonObj["result"] == "true"){
		message = jsonObj["message"];
		parent.alertMsg.warn(message);
	} else {
		
		// 校验通过后，调用保存函数
		comitForm();
	}
}
$(function(){
	
	var id = $("#APPLICATION_ID_D",parent.document).val(); 						// 获取申请表ID
	var getDetailsURL = "<%=request.getContextPath()%>/part/mainTenanceApplication/MainTenanceApplicationAction/applicationPartInfoDetailsForAuditOrEdit.ajax";
	
	// 查询申请表子信息
	sendPost(getDetailsURL+"?applicationId="+id,"","",callbackShowDetailsInfo,null,null);
	
	// 主信息查询回调函数
	function callbackShowDetailsInfo(res,sData){
		var applicationInfo; 													// 此变量保存回调对象中包含的后台查询到的数据
		var explorer = parent.window.navigator.userAgent; 								// 判断浏览器
		// 包含MSIE字符则为IE浏览器，MSIE10.0为IE10，去除IE10浏览器，其他浏览器使用其他另外的读取方式
		if(explorer.indexOf("MSIE") != -1 && explorer.indexOf("MSIE 10.0") == -1){
			applicationInfo = res.text;
		}else{
			applicationInfo = res.firstChild.textContent;
		}
		showApplicationInfo(eval("(" + applicationInfo + ")")); 				// 调用显示主信息的函数
	}
	
	// 显示申请单主信息
	function showApplicationInfo(jsonObj){
		
		var trHtmlStr = "<tr>"+
						"          <td width='30'><input type='checkbox' style='margin: 0px;' onclick='checkBoxClick(this)'/><input type='hidden' name='ENTRY_ID'/></td>"+
						"          <td><input type='text' class='textInput' style='margin: 0px;' name='PART_NO' requiredInput='y' /><span style='color:red'>*</span></td>"+
						"          <td><input type='text' class='textInput' style='margin: 0px;' name='PART_NAME'requiredInput='y' /><span style='color:red'>*</span></td>"+
					    "		   <td><input type='text' class='textInput' style='margin: 0px' name='VIN' /></td>"+
					    "		   <td><input type='text' class='textInput' style='margin: 0px' readonly='readonly' name='ENGINEERING_DEPARTMENT_REMARK' /></td>"+
					    "		   <td><input type='text' class='textInput' style='margin: 0px' readonly='readonly' name='PURCHASING_DEPARTMENT_REMARK' /></td>"+
						"</tr>";
		
		for (var son in jsonObj ){
			$(".searchContent tbody").append(trHtmlStr);
		}
		
		$("tr", ".searchContent tbody").each(function(index, obj){
			var trData = jsonObj["row"+index];
			$("input", obj).each(function(indexj, inputObj){
				var nameStr = $(inputObj).attr("name");
				$(inputObj).val(trData[$.trim(nameStr)]);
			});
			
		});
		
		changeParentIframeHeight();
	}

	
	// 为全选checkbox按钮绑定click事件：根据全选box的状态初始化全部checbox的状态
	$("#allCheckbox").click(function(){
		var checkedStatus = $(this).prop("checked");
		initCheckBoxStatus(checkedStatus);
	})
})

// 动态改变iframe高度
var $parentIframe = $("#applicationTypeFrame",parent.document);
$parentIframe.height($("body").height());

function changeParentIframeHeight(){
	var height = $("tr","body").height() * $("tr","body").size();
	$parentIframe.height($("body").height() + height + 200);
}
</script>
</html>
