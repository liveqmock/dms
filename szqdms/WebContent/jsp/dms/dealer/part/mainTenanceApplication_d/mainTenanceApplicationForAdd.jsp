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
						<tr style="display: none;">
							<td><label>申请单类型</label></td>
							<td colspan="5">
								<select type="text" id="APPLICATION_TYPE_A" name="APPLICATION_TYPE_A" datasource="APPLICATION_TYPE_A">
									<option value="307201" selected="selected">零件编号录入</option>
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
			<iframe id="applicationTypeFrame" marginwidth="0" marginheight="0" src="partInfoAdd.jsp" frameborder="0" scrolling="no" width="100%" border="0"></iframe>
		</div>
</div>
<script type="text/javascript">
//弹出临时保存变量
var dia_dialog = $("body").data("addApplication");
$(function(){
	
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