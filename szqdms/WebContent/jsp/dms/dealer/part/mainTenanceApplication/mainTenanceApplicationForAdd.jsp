<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%@ page import="com.org.framework.common.OrgDept"%>
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	OrgDept orgDept = user.getOrgDept();
	String orgName = orgDept.getOName();
%>
<div style="width: 100%;height: 100%;overflow-y: auto;overflow-x: hidden;" id="applicationAddPageDiv" >
		<div class="pageHeader" style="position: static;">
			<div class="searchBar" align="left" >
					<table class="searchContent" id="applicationAddTables">
						<tr>
							<td><label>申请人</label></td>
							<td><input type="text" id="APPLICATION_PERSON_A" name="APPLICATION_PERSON_A"  dataSource="APPLICATION_PERSON_A" value="<%=user.getPersonName()%>" readonly="readonly" /></td>
							<td><label>申请单位</label></td>
							<td><input type="text" id="APPLICATION_WORK_A" name="APPLICATION_WORK_A"  dataSource="APPLICATION_WORK_A" value="<%=orgName%>" readonly="readonly"/></td>
							<td><label>申请人联系方式</label></td>
							<td>
								<input type="text" style="margin: 0px" id="APPLICATION_INFOMATION_A" name="APPLICATION_INFOMATION_A"  dataSource="APPLICATION_INFOMATION_A" value="<%=user.getContactWay()%>" />
								<span style="COLOR: red; top: 5px; padding-left: 5px;padding-top: 5px;">*</span>
							</td>
						</tr>
						<tr>
							<td><label>申请单类型</label></td>
							<td colspan="5">
								<select type="text" id="APPLICATION_TYPE_A" name="APPLICATION_TYPE_A" datasource="APPLICATION_TYPE_A" >
								<%
									// 根据业务要求，需要对不同部门新增权限进行过滤，驾驶室总成壳体维护权限（计划员-保障科）；零件禁用更新申请人权限（采购科、技术科）；新增权限（配送中心、服务站、配件经销商）
									if(orgName.indexOf("采供科") != -1 || orgName.indexOf("技术科") != -1){
								%>	
										<option value="<%=DicConstant.PJWHSQLX_04%>" selected="selected">零件编号变更(禁用)</option>
								<%
									}else if(orgName.indexOf("保障科") != -1){
										
								%>
										<option value="<%=DicConstant.PJWHSQLX_02%>" selected="selected">驾驶室总成录入</option>
										<option value="<%=DicConstant.PJWHSQLX_03%>" >驾驶室本体录入</option>
								<%
									} else {
								%>
										<option value="<%=DicConstant.PJWHSQLX_01%>" selected="selected">零件编号录入</option>
										<option value="<%=DicConstant.PJWHSQLX_02%>" >驾驶室总成录入</option>
										<option value="<%=DicConstant.PJWHSQLX_03%>" >驾驶室本体录入</option>
										<option value="<%=DicConstant.PJWHSQLX_04%>" >零件编号变更(禁用)</option>
								<%		
									}
								%>
								</select>
							</td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="button"><div class="buttonContent"><button id="applicationAddSaveBtn" class="button" type="button">保&nbsp;&nbsp;存</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button id="applicationAddCloseBtn" class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
							<li>&nbsp;</li>
						</ul>
					</div>
			</div>
		</div>
		<div  style="widht:100%;">
			<iframe id="applicationTypeFrame" marginwidth="0" marginheight="0" src="<%
									// 根据业务要求，需要对不同部门新增权限进行过滤，驾驶室总成壳体维护权限（计划员-保障科）；零件禁用更新申请人权限（采购科、技术科）；新增权限（配送中心、服务站、配件经销商）
									if(orgName.indexOf("采供科") != -1 || orgName.indexOf("技术科") != -1){
								%>partInfoChangeAdd.jsp<%
									}else if(orgName.indexOf("保障科") != -1){
								%>cabAssemblyAdd.jsp<%
									} else {
								%>partInfoAdd.jsp<%		
									}
								%>" frameborder="0" scrolling="no" width="100%" border="0"></iframe>
		</div>
</div>
<script type="text/javascript">
//弹出临时保存变量
var dia_dialog = $("body").data("addApplication");
$(function(){
	$("#APPLICATION_TYPE_A").change(function(){
		getApplicationTypePage();
	});
	
	function getApplicationTypePage(){
		var typeValue = $("#APPLICATION_TYPE_A").val();
		var iframeSrc = "";
		switch($.trim(typeValue)){
		case "307201": // 307201 零件编号录入
			iframeSrc = "partInfoAdd.jsp";
			break;
		case "307202": // 307202 驾驶室总成录入
			iframeSrc = "cabAssemblyAdd.jsp";
			break;
		case "307203": // 307203 驾驶室本体录入
			iframeSrc = "cabInfoAdd.jsp";
			break;
		case "307204": // 307204 零件编号变更(禁用)
			iframeSrc = "partInfoChangeAdd.jsp";
			break;
		case "307205": // 307205 供货商变更(禁用)
			iframeSrc = "supplierChangeAdd.jsp";
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
	};
	
	// 保存
	$("#applicationAddSaveBtn").click(function(){
		
		// 校验主页面及子页面填写信息
		if( validatePage() && $("#applicationTypeFrame")[0].contentWindow.validateSubpage() ){
			
			// 调用子页面提交函数
			$("#applicationTypeFrame")[0].contentWindow.saveForm();
		}
	});
	
	// 主页面必填项校验
	function validatePage(){
		if($.trim($("#APPLICATION_TYPE_A").val()) == "-1"){
			alertMsg.warn("请选择申请单类型");
			return false;
		}
		if($.trim($("#APPLICATION_PERSON_A").val()) == ""){
			alertMsg.warn("请填写申请人信息");
			return false;
		}
		if($.trim($("#APPLICATION_WORK_A").val()) == ""){
			alertMsg.warn("请填写申请单位");
			return false;
		}
		if($.trim($("#APPLICATION_INFOMATION_A").val()) == ""){
			alertMsg.warn("请填写申请人联系方式");
			return false;
		}
		return true;
	}
});

// 页面布局
</script>