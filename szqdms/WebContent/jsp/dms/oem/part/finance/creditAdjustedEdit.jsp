<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%@page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contentPath = request.getContextPath();
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="dia-layout">
	<div class="page">
	<div class="pageContent" style="" >
	<form method="post" id="fm-creditInfo" class="editForm" >
		<input type="hidden" id="dia-ORG_ID" name="dia-ORG_ID" datasource="ORG_ID" />
		<input type="hidden" id="dia-LINE_ID" name="dia-LINE_ID" datasource="LINE_ID" />
		<input type="hidden" id="dia-ACCOUNT_ID" name="dia-ACCOUNT_ID" datasource="ACCOUNT_ID" />
		<div align="left">
			<fieldset>
			<table class="editTable" id="tab-credit_Edit_Info">
				<tr>
					<td><label>渠道名称：</label></td>
				    <td>
				    	<input type="text" id="dia-ORG_NAME" name="dia-ORG_NAME" datasource="ORG_NAME"  readonly="true"/>
				    </td>
				    <td><label>渠道代码：</label></td>
				    <td>
				    	<input type="text" id="dia-ORG_CODE" name="dia-ORG_CODE" datasource="ORG_CODE" readonly="true"/>
				    </td>
				</tr>
				<tr>
					<td><label>原额度(元)：</label></td>
				    <td>
				    	<input type="text" id="dia-NOW_LIMIT" name="dia-NOW_LIMIT" datasource="NOW_LIMIT" datatype="1,is_money,30" readonly="true"/>
				    </td>
<!-- 					<td><label>输入金额(元)：</label></td>
				    <td>
				    	<input type="text" id="dia-ADJUST_LIMIT" name="dia-ADJUST_LIMIT" datasource="ADJUST_LIMIT" datatype="0,is_null,30" onblur="doOnblur(this)"/>
				    </td> -->
				    <td><label>调整后额度(元)：</label></td>
				    <td>
				    	<input type="text" id="dia-AFT_LIMIT" name="dia-AFT_LIMIT" datasource="AFT_LIMIT"  datatype="1,is_money,10"/>
				    </td>
				</tr>
				 <tr>
				    <td><label>备注：</label></td>
				    <td colspan="5">
					  <textarea id="dia-REMARKS" style="width:450px;height:40px;" name="dia-REMARKS" datasource="REMARK" datatype="1,is_null,1000"></textarea>
				    </td>
				</tr> 
			</table>
			</fieldset>
		</div>
	</form>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-save">调&nbsp;&nbsp;整</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
		</ul>
	</div>
	</div>
	</div>
</div>

<script type="text/javascript">
/* 取父页面传过的参数确定新增或者修改 */
    var diaAction = "<%=action%>";
    var diaUrl = "<%=request.getContextPath()%>/part/financeMng/creditMng/CreditAdjustedMngAction";
    $(function () {
        //保存基本信息按钮响应
        $("#btn-save").bind("click", function(event){
			var $f = $("#fm-creditInfo");
			if (submitForm($f) == false) return false;
			var sCondition = {};
			sCondition = $("#fm-creditInfo").combined(1) || {};
			var addUrl = diaUrl + "/creditAdjusted.ajax";
			doNormalSubmit($f,addUrl,"btn-save",sCondition,diaInsertCallBack);
		});
    	//关闭按钮响应
    	$("button.close").click(function(){
    		$.pdialog.closeCurrent();
    		return false;
    	});
		//修改页面赋值
        if (diaAction != "1") {
            var selectedRows = $("#tab-credit_info").getSelectedRows();
            setEditValue("fm-creditInfo", selectedRows[0].attr("rowdata"));
            var aft = selectedRows[0].attr("NOW_LIMIT");
            $("#dia-AFT_LIMIT").val(aft);
        } 
    })
    function diaInsertCallBack(){
    	searchCredit();
		$.pdialog.closeCurrent();
    }
    function doOnblur(obj){
    	var $obj = $(obj);
    	var s = $obj.val();
    	var o = $("#dia-NOW_LIMIT").val();
    	var reg = /0|-?^[0-9]+(.[0-9]{1,2})?$/;
		if (!s || !reg.test(s)){
            alertMsg.warn("请输入正确的金额!");
            return false;
        }
    	var f = s*1+o*1;
    	$("#dia-AFT_LIMIT").val(f);
    	
    }

</script>