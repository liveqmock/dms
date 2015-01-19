<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
%>

	<div style="width: 100%;height: 100%;overflow-y: auto;overflow-x: hidden;">
			<div class="pageHeader"  style="position: static;">
					<!-- 定义隐藏域条件 -->
					<div class="searchBar" align="left">
						<table class="searchContent" id="applicationDetailsTables">
							<!-- 定义查询条件 -->
							<tr>
								<td><label>申请单号</label></td>
								<td>
									<input type="hidden" id="APPLICATION_ID_D" name="APPLICATION_ID_D" datatype="1,is_digit_letter,30" dataSource="APPLICATION_ID_D" />
									<input type="text" id="APPLICATION_NO_D" name="APPLICATION_NO_D" datatype="1,is_digit_letter,30" dataSource="APPLICATION_NO_D" readonly="readonly"/>
								</td>
								<td><label>申请单状态</label></td>
								<td colspan="3">
									<input type="text" id="APPLICATION_STATUS_D" name="APPLICATION_STATUS_D"  datasource="APPLICATION_STATUS_D" readonly="readonly"/>
								</td>
							</tr>
							<tr>
								<td><label>申请单类型</label></td>
								<td>
									<input type="hidden" id="APPLICATION_TYPE_CODE_D" name="APPLICATION_TYPE_CODE_D" datatype="1,is_digit_letter,30" dataSource="APPLICATION_TYPE_CODE_D"readonly="readonly" />
									<input type="text" id="APPLICATION_TYPE_D" name="APPLICATION_TYPE_D" datatype="1,is_digit_letter,30" dataSource="APPLICATION_TYPE_D"readonly="readonly" />
								</td>
								<td><label>申请时间</label></td>
								<td>
									<input type="text" id="APPLICATION_TIME_D" name="APPLICATION_TIME_D" datasource="APPLICATION_TIME_D" datatype="1,is_null,30" readonly="readonly" /> 
                                </td>
								<td><label>申请人</label></td>
								<td><input type="text" id="APPLICATION_PERSON_D" name="APPLICATION_PERSON_D"  dataSource="APPLICATION_PERSON_D" readonly="readonly"/></td>
							</tr>
							<tr>
								<td><label>申请单位</label></td>
								<td><input type="text" id="APPLICATION_WORK_D" name="APPLICATION_WORK_D"  dataSource="APPLICATION_WORK_D" readonly="readonly"/></td>
								<td><label>申请人联系方式</label></td>
								<td colspan="3"><input type="text" id="APPLICATION_INFOMATION_D" name="APPLICATION_INFOMATION_D"  dataSource="APPLICATION_INFOMATION_D" readonly="readonly"/></td>
							</tr>
							<tr>
								<td><label>技术科审批人</label></td>
								<td><input type="text" id="ENGINEERING_DEPARTMENT_D" name="ENGINEERING_DEPARTMENT_D"  dataSource="ENGINEERING_DEPARTMENT_D" value="<%=user.getPersonName() %>" readonly="readonly"/></td>
								<td><label>审批结果</label></td>
								<td>
									<select type="text" id="APPLICATION_STATUS_AUDIT_D" name="APPLICATION_STATUS_AUDIT_D"  datasource="APPLICATION_STATUS_AUDIT_D" datatype="0,is_null,30">
										<option value="-1" selected="selected">--</option>
										<option value="<%=DicConstant.PJWHSQZT_03%>">审核通过</option>
										<option value="<%=DicConstant.PJWHSQZT_04%>">审核驳回</option>
									</select>	
								</td>
								<td ><label>技术科审批备注</label></td>
								<td ><input type="text" id="ENGINEERING_DEPARTMENT_REMARK_D" name="ENGINEERING_DEPARTMENT_REMARK_D"  dataSource="ENGINEERING_DEPARTMENT_REMARK_D" datatype="0,is_null,100"/></td>
							</tr>
						</table>
						<div class="subBar">
							<ul>
								<li><div class="button"><div class="buttonContent"><button class="button" id="edAuditBtn" type="button">审&nbsp;&nbsp;核</button></div></div></li>
								<li><div class="button"><div class="buttonContent"><button class="close" id="edAuditCloseBtn" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
								<li>&nbsp;</li>
							</ul>
						</div>
					</div>
				</form>
			</div>
			<div style="widht:100%;">
					<iframe id="applicationTypeFrame" marginwidth="0" marginheight="0" src="" frameborder="0" scrolling="no" width="100%" border="0"></iframe>
			</div>
		</div>
<script type="text/javascript">

$(function(){
	
	var applicationId = $("#tab-contract").getSelectedRows()[0].attr("APPLICATION_ID");
	
	var getDetailsURL = "<%=request.getContextPath()%>/part/mainTenanceApplication/MainTenanceApplicationAction/applicationDetails.ajax";
	
	// 查询申请表主信息
	sendPost(getDetailsURL+"?applicationId="+applicationId,"","",callbackShowDetailsInfo,null,null);
	
	var applyTypeCode = ""; 		// 保存申请单类型Code, 保存校验时需要
	
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
	
	// 显示申请单主信息
	function showApplicationInfo(jsonObj){
		$("#applicationDetailsTables").find("input").each(function(index,obj){
			var inputName = $(obj).attr("name")
			if("ENGINEERING_DEPARTMENT_D" != inputName){
				$(obj).val(jsonObj[inputName]);
			}
		});
		var iframeSrc = "";
		switch(jsonObj["APPLICATION_TYPE_CODE_D"]){
			case "307201": // 307201 零件编号录入审核
				applyTypeCode = "307201";
				iframeSrc = "partInfoEDAudit.jsp";
				break;
			case "307202": // 307202 驾驶室总成录入
				iframeSrc = "cabAssemblyEDAudit.jsp";
				break;
			case "307203": // 307203 驾驶室本体录入
				iframeSrc = "cabInfoEDAudit.jsp";
				break;
			case "307204": // 307204 零件编号变更(禁用)
				iframeSrc = "partInfoChangeEDAudit.jsp";
				break;
			case "307205": // 307205 供货商变更(禁用)
				iframeSrc = "supplierChangeEDAudit.jsp";
				break;
			default:
				iframeSrc = "";
				break;
		}
		if(iframeSrc != ""){
			$("#applicationTypeFrame").attr("src",iframeSrc).show();
		}else{
			$("#applicationTypeFrame").hide();
		}
	}
	
	// 保存
	$("#edAuditBtn").click(function(){
		
		// 校验主页面及子页面填写信息
		if( validatePage() && $("#applicationTypeFrame")[0].contentWindow.validateSubpage() ){
			
			// TODO 调用子页面提交函数
			$("#applicationTypeFrame")[0].contentWindow.saveForm();
		}
	});
	
	// 主页面必填项校验
	function validatePage(){
		if($.trim($("#ENGINEERING_DEPARTMENT_D").val()) == ""){
			alertMsg.warn("请填写审批人信息");
			return false;
		}
		if($("#APPLICATION_STATUS_AUDIT_D").val() == "-1"){
			alertMsg.warn("请选择审批结果");
			return false;
		}
		if($.trim($("#ENGINEERING_DEPARTMENT_REMARK_D").val()) == ""){
			alertMsg.warn("请填写技术科审批备注");
			return false;
		}
		return true;
	}
});

</script>