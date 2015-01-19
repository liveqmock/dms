<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="com.org.frameImpl.Constant" %>
<%@ page import="com.org.framework.Globals" %>
<%@ page import="com.org.framework.common.User" %>
<%@ page import="com.org.dms.common.DicConstant" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
    String contentPath = request.getContextPath();
    String action = request.getParameter("action");
    if (action == null)
        action = "1";
%>
<div id="dia-layout">
	<div class="page">
	<div class="pageContent" style="" >
    	<form method="post" id="fm-proActiveSummary" class="editForm" >
    		<input type="hidden" id="dia-summaryId" name="dia-summaryId" datasource="SUMMARY_ID" />
    		<input type="hidden" id="dia-dealId" name="dia-dealId" datasource="DEAL_ID" />
			<input type="hidden" id="dia-activeId" name="dia-activeId" datasource="ACTIVE_ID" />
			<input type="hidden" id="dia-dealStatus" name="dia-dealStatus" datasource="DEAL_STATUS" />
	  		<div align="left">
		  		<fieldset>
					<table class="editTable" id="dia-tab-htxx">
						<tr>
							<td><label>活动代码：</label></td>
						    <td>
						    	<input type="text" id="dia-activeCode" name="dia-activeCode" datasource="ACTIVE_CODE" datatype="1,is_null,30" readonly="true"/>
						    </td>
						    <td><label>活动名称：</label></td>
						    <td>
						    	<input type="text" id="dia-activeName" name="dia-activeName" datasource="ACTIVE_NAME" datatype="1,is_null,30" readonly="true"/>
						    </td>
						</tr>
						<tr>
						    <td><label>预计费用：</label></td>
						    <td>
					    		<input type="text" id="dia-planFee"  name="dia-planFee" dataSource="PLAN_FEE" datatype="1,is_null,10" readonly="true"/>
					   		</td>
					   		<td><label>实际费用：</label></td>
						    <td>
					    		<input type="text" id="dia-realFee"  name="dia-realFee" dataSource="REAL_FEE" datatype="0,is_null,10" />
					   		</td>
						</tr>
						<tr>
					   		<td><label>有效标识：</label></td>
						    <td colspan=3>
						    	<select id="dia-status" name="dia-status" kind="dic" src="YXBS" datasource="STATUS" datatype="1,is_null,6" operation="=" >
						    		<option value="-1" selected>-----</option>
						    	</select>
						    </td>
						</tr>
						<tr>
						    <td><label>总结内容：</label></td>
						    <td colspan="3">
								<textarea id="dia-summaryCon" style="width:93%;height:40px;" name="dia-summaryCon" datasource="SUMMARY_CON" datatype="1,is_null,1000"></textarea>
						    </td>
						</tr>
						<tr>
						    <td><label>备　　注：</label></td>
						    <td colspan="3">
							  <textarea id="dia-remarks" style="width:93%;height:40px;" name="dia-remarks" datasource="REMARKS" datatype="1,is_null,1000"></textarea>
						    </td>
						</tr>
					</table>	
		  		</fieldset>
			</div>
		</form>
        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-save">保&nbsp;&nbsp;存</button></div></div></li>
                <li><div class="button"><div class="buttonContent"><button type="button" id="btn-addAtt">添加附件</button></div></div></li>
                <li><div class="button"><div class="buttonContent"><button type="button" id="btn-downAtt">下载附件</button></div></div></li>
                <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
            </ul>
        </div>
	</div>
	</div>
</div>

<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
    
//取父页面传过的参数确定新增或者修改
var diaAction = "<%=action%>";
var diaUrl = "<%=request.getContextPath()%>/part/marketMng/marketActiveMng/ProActiveSummaryAction";
    
$(function () {
    
    var selectedRows = $("#tab-proActiveSummaryList").getSelectedRows();
    setEditValue("fm-proActiveSummary", selectedRows[0].attr("rowdata"));
    var summaryId = $("#dia-summaryId").val();
    if(!summaryId)	//新增初始化
	{ 	
        //默认显示有效标识
		$("#dia-status").val("<%=DicConstant.YXBS_01%>");
		$("#dia-status").attr("dic_code","<%=DicConstant.YXBS_01%>");
		$("#dia-status").find("option").val("<%=DicConstant.YXBS_01%>");
		$("#dia-status").find("option").text("有效");
    } 
       
    //保存按钮
    $("#btn-save").bind("click", function(event){
		var $f = $("#fm-proActiveSummary");
		if (submitForm($f) == false) return false;
		var sCondition = {};
		sCondition = $f.combined(1) || {};
		var addUrl = diaUrl + "/insert.ajax";
		doNormalSubmit($f,addUrl,"btn-save",sCondition,diaInsertCallBack);
	});
	
	//上传附件
	$("#btn-addAtt").bind("click", function () {
	    if(!summaryId){
	        alertMsg.warn('请先保存基本信息!');
	        return;
	    }
	    $.filestore.open(summaryId, {"folder": "true", "holdName": "true", "fileSizeLimit": 0, "fileTypeDesc": "All Files", "fileTypeExts": "*.*"});
	});
	
	//下载附件
	$("#btn-downAtt").bind("click", function () {
	    
	    if(!summaryId){
	        alertMsg.warn('请先保存基本信息!');
	        return;
	    }
	    $.filestore.view(summaryId);
	});
});
	
//新增方法回调
function diaInsertCallBack(res)
{
	try
	{
		var selectedIndex = $("#tab-proActiveSummaryList").getSelectedIndex();
		$("#tab-proActiveSummaryList").updateResult(res,selectedIndex);
		//$.pdialog.closeCurrent();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}


</script>