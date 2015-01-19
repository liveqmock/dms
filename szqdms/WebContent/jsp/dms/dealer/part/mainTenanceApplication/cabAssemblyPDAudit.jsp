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
<body>
<div class="page">
	<div class="pageHeader">
		<div class="searchBar" align="left">
		<table class="searchContent">
			<tr>
				<td><label>驾驶室总成编号</label></td>
				<td><input type="text" id="CAB_ASSEMBLY_NO" name="CAB_ASSEMBLY_NO" /></td>
			</tr>
			<tr>
				<td><label>车辆识别码</label></td>
				<td><input type="text" id="VIN" name="VIN" /></td>
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
					<input type="text" id="CAR_BODY_COLOR" name="CAR_BODY_COLOR" />
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
	<!-- 申请单ID -->
	<input type="hidden" id="APPLICATION_ID_A" name="APPLICATION_ID_A" datatype="1,is_digit_letter,30" dataSource="APPLICATION_ID_A" />
	<!-- 申请单类型 -->
	<input type="hidden" name="APPLICATION_TYPE_A" id="APPLICATION_TYPE_A" datasource="APPLICATION_TYPE_A" val=""/>
	<!-- 采购科审批人-->
	<input type="hidden" id="PURCHASING_DEPARTMENT_A" name="PURCHASING_DEPARTMENT_A" datatype="1,is_digit_letter,30" dataSource="PURCHASING_DEPARTMENT_A" />
	<!-- 审批状态 -->
	<input type="hidden" id="APPLICATION_STATUS_A" name="APPLICATION_STATUS_A" datatype="1,is_digit_letter,30" dataSource="APPLICATION_STATUS_A" />
	<!-- 采购科审批备注-->
	<input type="hidden" id="PURCHASING_DEPARTMENT_REMARK_A" name="PURCHASING_DEPARTMENT_REMARK_A" datatype="1,is_digit_letter,30" dataSource="PURCHASING_DEPARTMENT_REMARK_A" />
</form>
</body>
<script type="text/javascript">

// 校验子页面的必填项
function validateSubpage(){
	var result = true;
	
	return result;
}

// 初始化提交表单的数据，将需要保存至后台的数据保存至form表单中
function initSaveForm(){
	
	// 获取父页面的值，并将值赋予子页面中提交表单的隐藏域中
	$("#APPLICATION_ID_A").val($("#APPLICATION_ID_D",parent.document).val());										 // 申请单ID
	$("#PURCHASING_DEPARTMENT_A").val($.trim($("#PURCHASING_DEPARTMENT_D",parent.document).val())); 		   		 // 采购科审批人
	$("#APPLICATION_STATUS_A").val($.trim($("#APPLICATION_STATUS_AUDIT_D",parent.document).val())); 	  		     // 审批结果
	$("#PURCHASING_DEPARTMENT_REMARK_A").val($.trim($("#PURCHASING_DEPARTMENT_REMARK_D",parent.document).val()));    // 采购科审批备注
	$("#APPLICATION_TYPE_A").val($.trim($("#APPLICATION_TYPE_CODE_D",parent.document).val())); 			   			 // 申请单类型
	
	// 调用子页面的初始化数据
	initSaveSubpageData();
}

// 初始化子页面中提交的数据
function initSaveSubpageData(){

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
		$("#edAuditCloseBtn",parent.document).click();
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
	
	$(".searchContent input").prop("disabled",true);
})
</script>
</html>
