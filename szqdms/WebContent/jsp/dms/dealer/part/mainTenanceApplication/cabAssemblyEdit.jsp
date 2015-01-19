<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<style>
html, body { *overflow:hidden;}
body{
	overflow-x : hidden; 
    overflow-y : auto;    
}
.labelRadio{
	float: none!important;
}
.radioStyle{
	margin-top: 3px;
	float: none!important;
	margin: 0px!important;
}

.list{
	text-align: left;
}
.errorInput {
	border-color: red;
}
</style>
</head>
<body style="height: 800px;">
<div class="page">
	<div class="pageHeader">
		<div class="searchBar" align="left">
		<table class="searchContent">
			<tr>
				<td><label>驾驶室总成编号</label></td>
				<td><input type="text" id="CAB_ASSEMBLY_NO" name="CAB_ASSEMBLY_NO"style="margin: 0px" /><span style="FONT-SIZE: 12pt; HEIGHT: 18px; WIDTH: 10px; POSITION: relative; FLOAT: left; COLOR: red; top: 5px;">*</span></td>
			</tr>
			<tr>
				<td><label>车辆识别码</label></td>
				<td><input type="text" id="VIN" name="VIN" style="margin: 0px"/><span style="FONT-SIZE: 12pt; HEIGHT: 18px; WIDTH: 10px; POSITION: relative; FLOAT: left; COLOR: red; top: 5px;">*</span></td>
			</tr>
			<tr>
				<td><label>车体描述</label></td>
				<td>
					<table>
						<tr>
							<td><label>德龙F3000</label></td>
							<td>
								<input type="radio" id="<%=DicConstant.PJWHCTLX_01 %>_01" name="CAR_BODY_DESCRIPTION" class="radioStyle" value="N"/><label for="<%=DicConstant.PJWHCTLX_01 %>_01" class="labelRadio">N(加长高顶)</label>
							</td>
							<td>
								<input type="radio" id="<%=DicConstant.PJWHCTLX_01 %>_02" name="CAR_BODY_DESCRIPTION" class="radioStyle" value="J"/><label for="<%=DicConstant.PJWHCTLX_01 %>_02" class="labelRadio">J(加长平顶)</label>
							</td>
							<td>
								<input type="radio" id="<%=DicConstant.PJWHCTLX_01 %>_03" name="CAR_BODY_DESCRIPTION" class="radioStyle" value="D"/><label for="<%=DicConstant.PJWHCTLX_01 %>_03" class="labelRadio" >D(中长平顶)</label>
							</td>
							<td></td>
						</tr>
						<tr>
							<td><label>德龙F2000</label></td>
							<td>
								<input type="radio" id="<%=DicConstant.PJWHCTLX_02 %>_01" name="CAR_BODY_DESCRIPTION" class="radioStyle" value="N"/><label for="<%=DicConstant.PJWHCTLX_02 %>_01"  class="labelRadio">N(加长高顶)</label>
							</td>
							<td>
								<input type="radio" id="<%=DicConstant.PJWHCTLX_02 %>_02" name="CAR_BODY_DESCRIPTION"class="radioStyle" value="J"/><label for="<%=DicConstant.PJWHCTLX_02 %>_02" class="labelRadio">J(加长平顶)</label>
							</td>
							<td>
								<input type="radio" id="<%=DicConstant.PJWHCTLX_02 %>_03" name="CAR_BODY_DESCRIPTION"class="radioStyle" value="D"/><label for="<%=DicConstant.PJWHCTLX_02 %>_03" class="labelRadio">D(中长平顶)</label>
							</td>
							<td></td>
						</tr>
						<tr>
							<td><label>M3000</label></td>
							<td>
								<input type="radio" id="<%=DicConstant.PJWHCTLX_03 %>_01" name="CAR_BODY_DESCRIPTION" class="radioStyle" value="G" /><label for="<%=DicConstant.PJWHCTLX_03 %>_01" class="labelRadio">G(加长高顶)</label>
							</td>
							<td>
								<input type="radio" id="<%=DicConstant.PJWHCTLX_03 %>_02" name="CAR_BODY_DESCRIPTION" class="radioStyle" value="H" /><label for="<%=DicConstant.PJWHCTLX_03 %>_02" class="labelRadio">H(加长半高顶)</label>
							</td>
							<td>
								<input type="radio" id="<%=DicConstant.PJWHCTLX_03 %>_03" name="CAR_BODY_DESCRIPTION" class="radioStyle" value="M" /><label for="<%=DicConstant.PJWHCTLX_03 %>_03" class="labelRadio">M(标准)</label>
							</td>
							<td></td>
						</tr>
						<tr>
							<td><label>新M3000</label></td>
							<td>
								<input type="radio" id="<%=DicConstant.PJWHCTLX_04 %>_01" name="CAR_BODY_DESCRIPTION" class="radioStyle" value="G"/><label for="<%=DicConstant.PJWHCTLX_04 %>_01"class="labelRadio">G(加长高顶)</label>
							</td>
							<td>
								<input type="radio" id="<%=DicConstant.PJWHCTLX_04 %>_02" name="CAR_BODY_DESCRIPTION" class="radioStyle" value="H"/><label for="<%=DicConstant.PJWHCTLX_04 %>_02"class="labelRadio">H(加长半高顶)</label>
							</td>
							<td>
								<input type="radio" id="<%=DicConstant.PJWHCTLX_04 %>_03" name="CAR_BODY_DESCRIPTION" class="radioStyle" value="M"/><label for="<%=DicConstant.PJWHCTLX_04 %>_03"class="labelRadio">M(标准)</label>
							</td>
							<td></td>
						</tr>
						<tr>
							<td><label>奥龙</label></td>
							<td>
								<input type="radio" id="<%=DicConstant.PJWHCTLX_05%>_01" name="CAR_BODY_DESCRIPTION" class="radioStyle" value="T"/><label for="<%=DicConstant.PJWHCTLX_05%>_01"class="labelRadio">T(中长高顶)</label>
							</td>
							<td>
								<input type="radio" id="<%=DicConstant.PJWHCTLX_05%>_02" name="CAR_BODY_DESCRIPTION"class="radioStyle" value="L"/><label for="<%=DicConstant.PJWHCTLX_05%>_02"class="labelRadio">L(加长)</label>
							</td>
							<td>
								<input type="radio" id="<%=DicConstant.PJWHCTLX_05%>_03" name="CAR_BODY_DESCRIPTION"class="radioStyle" value="U"/><label for="<%=DicConstant.PJWHCTLX_05%>_03"class="labelRadio">U(中长半高顶)</label>
							</td>
							<td>
								<input type="radio" id="<%=DicConstant.PJWHCTLX_05%>_04" name="CAR_BODY_DESCRIPTION" class="radioStyle" value="B"/><label for="<%=DicConstant.PJWHCTLX_05%>_04"class="labelRadio">B(标准)</label>
							</td>
						</tr>
						<tr>
							<td><label>偏置码头车</label></td>
							<td>
								<input type="radio" id="<%=DicConstant.PJWHCTLX_06%>_01" name="CAR_BODY_DESCRIPTION" class="radioStyle" value="Z"/><label for="<%=DicConstant.PJWHCTLX_06%>_01"class="labelRadio">Z(编码)</label>
							</td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td><label>非公路矿用车</label></td>
							<td>
								<input type="radio" id="<%=DicConstant.PJWHCTLX_07%>_01" name="CAR_BODY_DESCRIPTION" class="radioStyle"/><label for="<%=DicConstant.PJWHCTLX_07%>_01"class="labelRadio">ZP(矿用)</label>
							</td>
							<td></td>
							<td></td>
							<td></td>
						</tr>					
					</table>
				</td>
			</tr>
			<tr>
				<td><label>车体颜色</label></td>
				<td>
					<input type="text" id="CAR_BODY_COLOR" name="CAR_BODY_COLOR"style="margin: 0px" /><span style="FONT-SIZE: 12pt; HEIGHT: 18px; WIDTH: 10px; POSITION: relative; FLOAT: left; COLOR: red; top: 5px;">*</span>
				</td>
			</tr>
			<tr>
				<td><label>发动机型号</label></td>
				<td>
					<input type="radio" id="ENGINE_TYPE_01" name="ENGINE_TYPE" class="radioStyle" value="2"/><label for="ENGINE_TYPE_01" class="labelRadio">国二</label>
					<input type="radio" id="ENGINE_TYPE_02" name="ENGINE_TYPE" class="radioStyle" value="3GG"/><label for="ENGINE_TYPE_02" class="labelRadio">国三共轨</label>
					<input type="radio" id="ENGINE_TYPE_03" name="ENGINE_TYPE" class="radioStyle" value="3EGR"/><label for="ENGINE_TYPE_03" class="labelRadio">国三EGR</label>
					<input type="radio" id="ENGINE_TYPE_04" name="ENGINE_TYPE" class="radioStyle" value="3TRQ"/><label for="ENGINE_TYPE_04" class="labelRadio">国三天然气</label>
					<input type="radio" id="ENGINE_TYPE_05" name="ENGINE_TYPE" class="radioStyle" value="4GG"/><label for="ENGINE_TYPE_05" class="labelRadio">国四共轨</label>
					<input type="radio" id="ENGINE_TYPE_06" name="ENGINE_TYPE" class="radioStyle" value="4TRQ"/><label for="ENGINE_TYPE_06" class="labelRadio">国四天然气</label>
					<input type="radio" id="ENGINE_TYPE_07" name="ENGINE_TYPE" class="radioStyle" value="5TRQ"/><label for="ENGINE_TYPE_07" class="labelRadio">国五天然气</label>
					<input type="radio" id="ENGINE_TYPE_08" name="ENGINE_TYPE" class="radioStyle" value="QT"/><label for="ENGINE_TYPE_08" class="labelRadio">其他</label>
				</td>
			</tr>
			<tr>
				<td><label>高低位换挡</label></td>
				<td>
					<input type="radio" id="HILO_SHIFT_01" name="HILO_SHIFT" class="radioStyle" value="1"/><label for="HILO_SHIFT_01"class="labelRadio">高位</label>
					<input type="radio" id="HILO_SHIFT_02" name="HILO_SHIFT" class="radioStyle" value="0"/><label for="HILO_SHIFT_02"class="labelRadio">低位</label>
				</td>
			</tr>
			<tr>
				<td><label>窗机类型</label></td>
				<td>
					<input type="radio" id="WINDOW_TYPE_01" name="WINDOW_TYPE" class="radioStyle" value="1" /><label for="WINDOW_TYPE_01"class="labelRadio">手摇</label>
					<input type="radio" id="WINDOW_TYPE_02" name="WINDOW_TYPE" class="radioStyle" value="0" /><label for="WINDOW_TYPE_02"class="labelRadio">电摇</label>
				</td>
			</tr>
			<tr>
				<td><label>空调类型</label></td>
				<td>
					<input type="radio" id="AIR_TYPE_01" name="AIR_TYPE" class="radioStyle" value="1" /><label for="AIR_TYPE_01" class="labelRadio">空调</label>
					<input type="radio" id="AIR_TYPE_02" name="AIR_TYPE" class="radioStyle" value="0" /><label for="AIR_TYPE_02" class="labelRadio">暖风</label>
				</td>
			</tr>
			<tr>
				<td><label>是否带导流罩</label></td>
				<td>
					<input type="radio" id="IS_DEFLECTOR_01" name="IS_DEFLECTOR" class="radioStyle" value="1" /><label for="IS_DEFLECTOR_01" class="labelRadio">带&nbsp;&nbsp;&nbsp;</label>
					<input type="radio" id="IS_DEFLECTOR_02" name="IS_DEFLECTOR" class="radioStyle" value="0" /><label for="IS_DEFLECTOR_02" class="labelRadio">不带</label>
				</td>
			</tr>
			<tr>
				<td><label>是否右置</label></td>
				<td>
					<input type="radio" id="IS_RIGHT_MOUNTED_01" name="IS_RIGHT_MOUNTED" class="radioStyle" value="R" /><label for="IS_RIGHT_MOUNTED_01" class="labelRadio">右置</label>
					<input type="radio" id="IS_RIGHT_MOUNTED_02" name="IS_RIGHT_MOUNTED" class="radioStyle" value="L" /><label for="IS_RIGHT_MOUNTED_02" class="labelRadio">左置</label>
				</td>
			</tr>
		</table>
		</div>
	</div>
</div>	
<form id="applicationForm" style="display:none" method="post">
	<!-- 修改动作 -->
	<input type="hidden" id="EDIT_ACTION" name="EDIT_ACTION" datatype="1,is_digit_letter,30" dataSource="EDIT_ACTION" value="YES"/>
	<!-- 申请单ID -->
	<input type="hidden" id="APPLICATION_ID_A" name="APPLICATION_ID_A" datatype="1,is_digit_letter,30" dataSource="APPLICATION_ID_A" />
	<!-- 申请人 -->
	<input type="hidden" name="APPLICATION_PERSON_A" id="APPLICATION_PERSON_A" datasource="APPLICATION_PERSON_A" val=""/>
	<!-- 申请单位 -->
	<input type="hidden" name="APPLICATION_WORK_A" id="APPLICATION_WORK_A" datasource="APPLICATION_WORK_A" val=""/>
	<!-- 申请人联系方式 -->
	<input type="hidden" name="APPLICATION_INFOMATION_A" id="APPLICATION_INFOMATION_A" datasource="APPLICATION_INFOMATION_A" val=""/>
	<!-- 申请单类型 -->
	<input type="hidden" name="APPLICATION_TYPE_A" id="APPLICATION_TYPE_A" datasource="APPLICATION_TYPE_A" val=""/>
	<!-- 驾驶室总成编号 -->
	<input type="hidden" name="CAB_ASSEMBLY_NO_A" id="CAB_ASSEMBLY_NO_A" datasource="CAB_ASSEMBLY_NO_A" val="" />
	<!-- 车辆识别码 -->
	<input type="hidden" id="VIN_A" name="VIN_A"  dataSource="VIN_A" />
	<!-- 车体描述 -->
	<input type="hidden" id="CAR_BODY_DESCRIPTION_A" name="CAR_BODY_DESCRIPTION_A"  dataSource="CAR_BODY_DESCRIPTION_A" />
	<!-- 车体描述Code -->
	<input type="hidden" id="CAR_BODY_DESCRIPTION_CODE_A" name="CAR_BODY_DESCRIPTION_CODE_A"  dataSource="CAR_BODY_DESCRIPTION_CODE_A" />
	<!-- 车体颜色 -->
	<input type="hidden" name="CAR_BODY_COLOR_A" id="CAR_BODY_COLOR_A" datasource="CAR_BODY_COLOR_A" value="" />
	<!-- 发动机型号 -->
	<input type="hidden" name="ENGINE_TYPE_A" id="ENGINE_TYPE_A" datasource="ENGINE_TYPE_A" value="" />
	<!-- 高低位换挡 -->
	<input type="hidden" name="HILO_SHIFT_A" id="HILO_SHIFT_A" datasource="HILO_SHIFT_A" value="" />
	<!-- 窗机类型 -->
	<input type="hidden" name="WINDOW_TYPE_A" id="WINDOW_TYPE_A" datasource="WINDOW_TYPE_A" value="" />
	<!-- 空调类型 -->
	<input type="hidden" name="AIR_TYPE_A" id="AIR_TYPE_A" datasource="AIR_TYPE_A" value="" />
	<!-- 是否带导流罩 -->
	<input type="hidden" name="IS_DEFLECTOR_A" id="IS_DEFLECTOR_A" datasource="IS_DEFLECTOR_A" value="" />
	<!-- 是否右置 -->
	<input type="hidden" name="IS_RIGHT_MOUNTED_A" id="IS_RIGHT_MOUNTED_A" datasource="IS_RIGHT_MOUNTED_A" value="" />
</form>
</body>
<script type="text/javascript">

// 校验子页面的必填项
function validateSubpage(){
	var result = true;
	var message = "";
	// 驾驶室总成编号, 车辆识别码 , 车体颜色
	var inputArray = $("input[type=text]", ".searchContent");
	inputArray.each(function(index, inputObj){
		var inputVal = $(inputObj).val();
		var inputName = $(inputObj).attr("name");
		if($.trim(inputVal) == ""){
			$(inputObj).addClass("errorInput");
			switch(inputName){
			case "CAB_ASSEMBLY_NO":
				message = "请填驾驶室总成编号";
				break;
			case "CAR_BODY_COLOR":
				message = "请填写车体颜色";
				break;
			case "VIN":
				message = "请填写VIN";
				break;
			}
			parent.alertMsg.warn(message);
			result = false;
			return false;
		}else{
			$(inputObj).removeClass("errorInput");
			message = "请填写正确的表单内容";
			switch(inputName){
			case "CAB_ASSEMBLY_NO":
				// 只能为数字或字母
				if(is_digit_letter($(inputObj)) !== true){
					message = "请填写正确的驾驶室总成编号";
					result = false;
				}
				break;
			case "VIN":
				// VIN校验
				if(is_vin($(inputObj)) !== true){
					message = "请填写正确的车辆识别码";
					result = false;
				}
				break;
			}
			
			if(result == false){
				$(inputObj).addClass("errorInput");
				return false;
			} 
		}
	})
	
	if(result == false){
		parent.alertMsg.warn(message);
		return result;
	}
	
	// 是否右置
	if($("input[name=IS_RIGHT_MOUNTED]:checked", ".searchContent").size() == 0){
		parent.alertMsg.warn("请选择是否右置");
		result = false;
	}
	
	// 是否带导流罩
	if($("input[name=IS_DEFLECTOR]:checked", ".searchContent").size() == 0){
		parent.alertMsg.warn("请选择是否带导流罩");
		result = false;
	}
	
	// 空调类型
	if($("input[name=AIR_TYPE]:checked", ".searchContent").size() == 0){
		parent.alertMsg.warn("请选择空调类型");
		result = false;
	}
	
	// 窗机类型
	if($("input[name=WINDOW_TYPE]:checked", ".searchContent").size() == 0){
		parent.alertMsg.warn("请选择窗机类型");
		result = false;
	}
	
	// 高低位换挡
	if($("input[name=HILO_SHIFT]:checked", ".searchContent").size() == 0){
		parent.alertMsg.warn("请选择高低位换挡");
		result = false;
	}
	
	// 发动机型号
	if($("input[name=ENGINE_TYPE]:checked", ".searchContent").size() == 0){
		parent.alertMsg.warn("请选择发动机型号");
		result = false;
	}
	
	// 车体描述
	if($("input[name=CAR_BODY_DESCRIPTION]:checked", ".searchContent").size() == 0){
		parent.alertMsg.warn("请选择车体描述");
		result = false;
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
	// 驾驶室总成编号, 车辆识别码 , 车体颜色
	var inputArray = $("input[type=text]", ".searchContent");
	inputArray.each(function(index, inputObj){
		var inputVal = $(inputObj).val();
		var inputName = $(inputObj).attr("name");
		switch(inputName){
		case "CAB_ASSEMBLY_NO":
			$("#CAB_ASSEMBLY_NO_A").val(inputVal);
			break;
		case "VIN":
			$("#VIN_A").val(inputVal);
			break;
		case "CAR_BODY_COLOR":
			$("#CAR_BODY_COLOR_A").val(inputVal);
			break;
		}
	})
	$("#IS_RIGHT_MOUNTED_A").val($("input[name=IS_RIGHT_MOUNTED]:checked", ".searchContent").val()); // 是否右置
	$("#IS_DEFLECTOR_A").val($("input[name=IS_DEFLECTOR]:checked", ".searchContent").val()); // 是否带导流罩
	$("#AIR_TYPE_A").val($("input[name=AIR_TYPE]:checked", ".searchContent").val()); // 空调类型
	$("#WINDOW_TYPE_A").val($("input[name=WINDOW_TYPE]:checked", ".searchContent").val()); // 窗机类型
	$("#HILO_SHIFT_A").val($("input[name=HILO_SHIFT]:checked", ".searchContent").val()); // 高低位换挡
	$("#ENGINE_TYPE_A").val($("input[name=ENGINE_TYPE]:checked", ".searchContent").val()); // 发动机型号
	$("#CAR_BODY_DESCRIPTION_A").val($("input[name=CAR_BODY_DESCRIPTION]:checked", ".searchContent").val()); // 车体描述
	
	// 获取车体描述Code
	var carBodyCode = $("input[name=CAR_BODY_DESCRIPTION]:checked", ".searchContent").attr("id"); // 车体描述Code
	carBodyCode = carBodyCode.substring(0, carBodyCode.length-3);
	$("#CAR_BODY_DESCRIPTION_CODE_A").val(carBodyCode);
}

function saveForm(){
	initSaveForm(); // 调用初始化表单数据
	var saveActionURL = "";
	var $f = $("#applicationForm"); // 保存操作
	sCondition = $f.combined() || {};
	var saveActionURL = "<%=request.getContextPath()%>/part/mainTenanceApplication/MainTenanceApplicationAction/saveApplication.ajax";
	doNormalSubmit($f, saveActionURL, "allCheckbox", sCondition, function(responseText,tabId,sParam){
    	$("#btn-search",parent.document).click();
    	parent.alertMsg.correct("保存成功");
		$("#editCloseBtn",parent.document).click();
    }); 
}


$(function(){
	
	var id = $("#APPLICATION_ID_D",parent.document).val();
	
	var getDetailsURL = "<%=request.getContextPath()%>/part/mainTenanceApplication/MainTenanceApplicationAction/applicationCABAssemblyDetails.ajax";
	
	// 查询申请表主信息
	sendPost(getDetailsURL+"?applicationId="+id,"","",callbackShowDetailsInfo,null,null);
	
	// 主信息查询回调函数
	function callbackShowDetailsInfo(res,sData){
		
		// 此变量保存回调对象中包含的后台查询到的数据
		var applicationInfo;
		
		// 判断浏览器
		var explorer = window.navigator.userAgent;
		// 包含MSIE字符则为IE浏览器，MSIE10.0为IE10，去除IE10浏览器，其他浏览器使用其他另外的读取方式
		if(explorer.indexOf("MSIE") != -1 && explorer.indexOf("MSIE 10.0") == -1){
			applicationInfo = res.text;
		}else{
			applicationInfo = res.firstChild.textContent;
		}
		// 调用显示主信息的函数
		showApplicationInfo(eval("(" + applicationInfo + ")"))
	}
	
	// 显示申请单详细信息
	function showApplicationInfo(jsonObj){
		// 显示text文本值
		$("input[type=text]", ".searchContent").each(function(index,obj){
			var inputName = $(obj).attr("name")
			$(obj).val(jsonObj[inputName]);
		});
		
		// 获取车体描述radio
		var F3000 = "<%=DicConstant.PJWHCTLX_01%>";
		var F3000Val = jsonObj[F3000];
		if(F3000Val != null){
			for(var i = 1; i <= 3; i++){
				var $obj = $("#<%=DicConstant.PJWHCTLX_01 %>_0"+i);
				if($obj.val() == F3000Val){
					$obj.prop("checked", true);
				}
			}
		}
		
		var F2000 = "<%=DicConstant.PJWHCTLX_02%>";
		var F2000Val = jsonObj[F2000];
		if(F2000Val != null){
			for(var i = 1; i <= 3; i++){
				var $obj = $("#<%=DicConstant.PJWHCTLX_02 %>_0"+i);
				if($obj.val() == F2000Val){
					$obj.prop("checked", true);
				}
			}
		}
		
		var M3000 = "<%=DicConstant.PJWHCTLX_03%>";
		var M3000Val = jsonObj[M3000];
		if(M3000Val != null){
			for(var i = 1; i <= 3; i++){
				var $obj = $("#<%=DicConstant.PJWHCTLX_03 %>_0"+i);
				if($obj.val() == M3000Val){
					$obj.prop("checked", true);
				}
			}
		}
		
		var NM3000 = "<%=DicConstant.PJWHCTLX_04%>";
		var NM3000Val = jsonObj[NM3000];
		if(NM3000Val != null){
			for(var i = 1; i <= 3; i++){
				var $obj = $("#<%=DicConstant.PJWHCTLX_04 %>_0"+i);
				if($obj.val() == NM3000Val){
					$obj.prop("checked", true);
				}
			}
		}
		
		
		var AL = "<%=DicConstant.PJWHCTLX_05%>";
		var ALVal = jsonObj[AL];
		if(ALVal != null){
			// 循环子项的数量因为是页面写死的，所以如果需要添加显示项，则这里的循环数目就需要改
			for(var i = 1; i <= 4; i++){
				var $obj = $("#<%=DicConstant.PJWHCTLX_05 %>_0"+i);
				if($obj.val() == ALVal){
					$obj.prop("checked", true);
				}
			}
		}
		
		var PJMTC = "<%=DicConstant.PJWHCTLX_06%>";
		var PJMTCVal = jsonObj[PJMTC];
		if(PJMTCVal != null){
			for(var i = 1; i <= 3; i++){
				var $obj = $("#<%=DicConstant.PJWHCTLX_06 %>_0"+i);
				if($obj.val() == PJMTCVal){
					$obj.prop("checked", true);
				}
			}
		}
		
		var FGLKYC = "<%=DicConstant.PJWHCTLX_07%>";
		var FGLKYCVal = jsonObj[FGLKYC];
		if(FGLKYCVal != null){
			for(var i = 1; i <= 3; i++){
				var $obj = $("#<%=DicConstant.PJWHCTLX_07 %>_0"+i);
				if($obj.val() == FGLKYCVal){
					$obj.prop("checked", true);
				}
			}
		}
		
		// 发动机型号
		$("input[name=ENGINE_TYPE]").each(function(index, obj){
			if($(obj).val() == jsonObj["ENGINE_TYPE"]){
				$(obj).prop("checked",true);
			}
		});
		
		// 高低位换挡
		$("input[name=HILO_SHIFT]").each(function(index, obj){
			if($(obj).val() == jsonObj["HILO_SHIFT"]){
				$(obj).prop("checked",true);
			}
		});
		
		// 窗机类型
		$("input[name=WINDOW_TYPE]").each(function(index, obj){
			if($(obj).val() == jsonObj["WINDOW_TYPE"]){
				$(obj).prop("checked",true);
			}
		});
		
		// 空调类型
		$("input[name=AIR_TYPE]").each(function(index, obj){
			if($(obj).val() == jsonObj["AIR_TYPE"]){
				$(obj).prop("checked",true);
			}
		});
		
		// 是否带导流罩
		$("input[name=IS_DEFLECTOR]").each(function(index, obj){
			if($(obj).val() == $.trim(jsonObj["IS_DEFLECTOR"])){
				$(obj).prop("checked",true);
			}
		});
		
		// 是否右置
		$("input[name=IS_RIGHT_MOUNTED]").each(function(index, obj){
			if($(obj).val() == $.trim(jsonObj["IS_RIGHT_MOUNTED"])){
				$(obj).prop("checked",true);
			}
		});
		
		// 动态改变iframe高度
		var $parentIframe = $("#applicationTypeFrame",parent.document);
		$parentIframe.height($(".searchContent").height());
	}
})
</script>
</html>
