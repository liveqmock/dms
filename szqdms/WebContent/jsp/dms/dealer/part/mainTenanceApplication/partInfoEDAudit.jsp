<%@page import="com.org.dms.common.DicConstant"%>
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
	<input type="hidden" id="getSupplierInfoLineNum" />
	<div id="layout" style="width: 100%;">
		<div class="page">
			<div class="pageContent" style="overflow-x:auto;overflow-y:auto;">
				<div class="panelBar">
					<ul class="toolBar">
						<li><a class="add" href="javascript:tempSave()" ><span>审核信息暂存</span></a></li>
					</ul>
				</div>
				<div class="searchBar" align="left">
					<table class="searchContent">
						<thead>
							<tr>
								<th width="30">&nbsp;</th>
								<th width="150">零件编号</th>
								<th width="150">零件名称</th>
								<th width="150">车辆识别码</th>
								<th width="150">工艺路线</th>
								<th width="150">所属一级</th>
								<th width="150">所属二级</th>
								<th width="150">技术科审核备注</th>
							</tr>
						</thead>
						<tbody id="partInfoEdTbody">
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
<form id="applicationForm" style="display:none" method="post">
	<!-- 申请单ID -->
	<input type="hidden" id="APPLICATION_ID_A" name="APPLICATION_ID_A" datatype="1,is_digit_letter,30" dataSource="APPLICATION_ID_A" />
	<!-- 申请单类型 -->
	<input type="hidden" name="APPLICATION_TYPE_A" id="APPLICATION_TYPE_A" datasource="APPLICATION_TYPE_A" val=""/>
	<!-- 技术科审批人-->
	<input type="hidden" id="ENGINEERING_DEPARTMENT_A" name="ENGINEERING_DEPARTMENT_A" datatype="1,is_digit_letter,30" dataSource="ENGINEERING_DEPARTMENT_A" />
	<!-- 审批状态 -->
	<input type="hidden" id="APPLICATION_STATUS_A" name="APPLICATION_STATUS_A" datatype="1,is_digit_letter,30" dataSource="APPLICATION_STATUS_A" />
	<!-- 技术科审批备注-->
	<input type="hidden" id="ENGINEERING_DEPARTMENT_REMARK_A" name="ENGINEERING_DEPARTMENT_REMARK_A" datatype="1,is_digit_letter,30" dataSource="ENGINEERING_DEPARTMENT_REMARK_A" />
	<!-- 配件信息录入子表ID -->
	<input type="hidden" name="ENTRY_ID_S_A" id="ENTRY_ID_S_A" datasource="ENTRY_ID_S_A" />
	<!-- 工艺路线 -->
	<input type="hidden" name="PROCESS_ROUTE_S_A" id="PROCESS_ROUTE_S_A" datasource="PROCESS_ROUTE_S_A"  />
	<!-- 所属一级 -->
	<input type="hidden" name="OWN_FIRST_LEVEL_S_A" id="OWN_FIRST_LEVEL_S_A" datasource="OWN_FIRST_LEVEL_S_A" />
	<!-- 所属二级 -->
	<input type="hidden" id="OWN_SECOND_LEVEL_A" name="OWN_SECOND_LEVEL_A"  dataSource="OWN_SECOND_LEVEL_A" />
	<!-- 技术科审批备注-->
	<input type="hidden" id="ENGINEERING_DEPARTMENT_REMARK_DA" name="ENGINEERING_DEPARTMENT_REMARK_DA" datatype="1,is_digit_letter,30" dataSource="ENGINEERING_DEPARTMENT_REMARK_DA" />
</form>
<script type="text/javascript">

// 校验子页面的必填项
function validateSubpage(){
	var result = true;
	
	// 获取父页面的审批结果:当审核通过后则校验子项，没有通过则对子项不做校验
	var auditStatus = $.trim($("#APPLICATION_STATUS_AUDIT_D",parent.document).val());
	if(auditStatus == <%=DicConstant.PJWHSQZT_03%>){
		var inputArray = $("input[type=text][requiredInput=y]", ".searchContent tbody");
		inputArray.each(function(index, inputObj){
				var inputVal = $(inputObj).val();
				if($.trim(inputVal) == "" && !($(inputObj).attr("name") == "OWN_SECOND_LEVEL" || $(inputObj).attr("name") == "ENGINEERING_DEPARTMENT_REMARK")){
					$(inputObj).addClass("errorInput");
					var message = "请填写表单内容";
					if($(inputObj).attr("name") == "PROCESS_ROUTE"){
						message = "请填写工艺路线";
					} else if($(inputObj).attr("name") == "OWN_FIRST_LEVEL"){
						message = "请填写所属一级";
					} 
					parent.alertMsg.warn(message);
					result = false;
					return false;
				}else{
					$(inputObj).removeClass("errorInput");
				}
		})
	} 
	
	return result;
}

// 初始化提交表单的数据，将需要保存至后台的数据保存至form表单中
function initSaveForm(){

	// 获取父页面的值，并将值赋予子页面中提交表单的隐藏域中
	$("#APPLICATION_ID_A").val($("#APPLICATION_ID_D",parent.document).val());										 // 申请单ID
	$("#ENGINEERING_DEPARTMENT_A").val($.trim($("#ENGINEERING_DEPARTMENT_D",parent.document).val())); 		   		 // 技术科审批人
	$("#APPLICATION_STATUS_A").val($.trim($("#APPLICATION_STATUS_AUDIT_D",parent.document).val())); 	  		     // 审批结果
	$("#ENGINEERING_DEPARTMENT_REMARK_A").val($.trim($("#ENGINEERING_DEPARTMENT_REMARK_D",parent.document).val()));  // 技术科审批备注
	$("#APPLICATION_TYPE_A").val($.trim($("#APPLICATION_TYPE_CODE_D",parent.document).val())); 			   			 // 申请单类型
	initSaveSubpageData(); 																							 // 调用子页面的初始化数据
	
}

// 初始化子页面中提交的数据
function initSaveSubpageData(){
	var entryId = [],			/* 行数据主键ID */
		processRoute = [],		/* 工艺路线 */
		ownFirstLevel = [],		/* 所属一级 */
		ownSecondLevel = [],    /* 所属二级 */
		edRemarks = [];		    /* 技术科审核备注 */
		
    $("input[requiredInput=y]", ".searchContent tbody").each(function(index, inputObj){
		var inputVal = $.trim($(inputObj).val() ? $(inputObj).val() : "");
		if($(inputObj).attr("name") == "PROCESS_ROUTE"){
			processRoute.push(inputVal);
		} else if($(inputObj).attr("name") == "OWN_FIRST_LEVEL"){
			ownFirstLevel.push(inputVal);
		} else if($(inputObj).attr("name") == "OWN_SECOND_LEVEL"){
			ownSecondLevel .push((inputVal == "" ? "null" : inputVal));
		} else if($(inputObj).attr("name") == "ENTRY_ID"){
			entryId.push(inputVal);
		} else if($(inputObj).attr("name") == "ENGINEERING_DEPARTMENT_REMARK"){
			inputVal = inputVal.replace(/\,/g, "，"); // 将用户填写的英文,替换为中文，
			edRemarks.push(inputVal);
		}
	});
		
	// 将获取的值保存至隐藏的form表单中
	$("#ENTRY_ID_S_A").val(entryId.join(","));
	$("#PROCESS_ROUTE_S_A").val(processRoute.join(","));
	$("#OWN_FIRST_LEVEL_S_A").val(ownFirstLevel.join(","));
	$("#OWN_SECOND_LEVEL_A").val(ownSecondLevel.join(","));
	$("#ENGINEERING_DEPARTMENT_REMARK_DA").val(edRemarks.join(","));

}

// 保存
function saveForm(){
	initSaveForm(); // 调用初始化表单数据
	var saveActionURL = "";
	var $f = $("#applicationForm"); // 保存操作
	sCondition = $f.combined(1) || {};
	var saveActionURL = "<%=request.getContextPath()%>/part/mainTenanceApplication/MainTenanceApplicationAction/saveApplication.ajax";
	doNormalSubmit($f, saveActionURL, "allCheckbox", sCondition, function(responseText,tabId,sParam){
    	$("#btn-search",parent.document).click();
    	parent.alertMsg.correct("保存成功");
		$("#edAuditCloseBtn",parent.document).click();
    }); 
}

// 审核信息暂存
function tempSave(){
	
	// 初始化暂存数据
	initSaveForm();
	initSaveSubpageData();
	$("#APPLICATION_STATUS_A").val(<%=DicConstant.PJWHSQZT_07%>); 	  		     // 暂存
	var $f = $("#applicationForm"); // 保存操作
	sCondition = $f.combined() || {};
	var saveActionURL = "<%=request.getContextPath()%>/part/mainTenanceApplication/MainTenanceApplicationAction/saveApplication.ajax";
	doNormalSubmit($f, saveActionURL, "allCheckbox", sCondition, function(responseText,tabId,sParam){
    	parent.alertMsg.correct("暂存成功");
    }); 
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
		var lineNum = 1;
		for (var son in jsonObj ){
			var trHtmlStr = "<tr>"+
			"          <td></td>"+
			"          <td><input type='hidden' name='ENTRY_ID' requiredInput='y'/><input type='text' class='textInput readonly' style='margin: 0px;' name='PART_NO'  readonly='readonly' /></td>"+
			"          <td><input type='text' class='textInput readonly' style='margin: 0px;' name='PART_NAME' readonly='readonly' /></td>"+
			"          <td><input type='text' class='textInput readonly' style='margin: 0px;' name='VIN' readonly='readonly' /></td>"+
			"          <td><input type='text' class='textInput' style='margin: 0px;' name='PROCESS_ROUTE'  requiredInput='y'  /><span style='color:red;'>*</span></td>"+
			"          <td><input type='text' class='textInput readonly' style='margin: 0px;' id='OWN_FIRST_LEVEL_"+(lineNum)+"' name='OWN_FIRST_LEVEL'  datatype='1,is_null,100' requiredInput='y' kind='dic' operation='like' isreload='true' src='T#SE_BA_VEHICLE_POSITION:POSITION_CODE:POSITION_NAME{POSITION_ID,POSITION_NAME}:1=1 AND POSITION_LEVEL=1 ORDER BY POSITION_CODE' /><span style='color:red;'>*</span></td>"+
			"          <td><input type='text' class='textInput readonly' style='margin: 0px;' id='OWN_SECOND_LEVEL_"+(lineNum++)+"' name='OWN_SECOND_LEVEL'  datatype='1,is_null,100' requiredInput='y' kind='dic' operation='like' isreload='true' src='' /></td>"+
			"          <td><input type='text' class='textInput' style='margin: 0px;' name='ENGINEERING_DEPARTMENT_REMARK' requiredInput='y'/></td>"+
			"</tr>";
			$(".searchContent tbody").append(trHtmlStr);
		}
		
		$("tr", ".searchContent tbody").each(function(index, obj){
			var trData = jsonObj["row"+index];
			$("input", obj).each(function(indexj, inputObj){
				var nameStr = $(inputObj).attr("name");
				$(inputObj).val(trData[$.trim(nameStr)]);
			});
		});
		
		initRowNum();
		changeParentIframeHeight();
		setStyle();
	}
	
	// 初始化行No
	function initRowNum(){
		$("td:first",".searchContent tbody tr").each(function(index,obj){
			$(obj).text(index+1);
		})
	}
	
})

// 表选回调函数
function afterDicItemClick(id, $row, selIndex){
  var ret = true;
  //$row 行对象
  //selIndex 字典中第几行
  var POSITION_ID = "";
  if(id.indexOf('OWN_FIRST_LEVEL') != -1){
      $("#"+id).val($row.attr('POSITION_NAME'));
      POSITION_ID = $row.attr('POSITION_ID');
      var sql = "T#SE_BA_VEHICLE_POSITION:POSITION_CODE:POSITION_NAME{POSITION_ID,POSITION_NAME}:1=1 AND POSITION_LEVEL=2 AND P_ID="+POSITION_ID+" ORDER BY POSITION_CODE";
      var num = id.lastIndexOf("_")
      var idNum = id.substring(num + 1, id.length);
      $("#OWN_SECOND_LEVEL_"+idNum).attr("src",sql);
  }
  if(id.indexOf('OWN_SECOND_LEVEL') != -1 ){
	  $('#'+id).val($row.attr('POSITION_NAME'));
  }
  return ret;
}

// 动态改变iframe高度
var $parentIframe = $("#applicationTypeFrame",parent.document);
$parentIframe.height($("body").height() + 50);

function changeParentIframeHeight(){
	var bodyHeight = $("body").height()
	var height = $("tr","body").height() * $("tr","body").size();
	$parentIframe.height(bodyHeight + height + 50);
}

</script>
</html>
