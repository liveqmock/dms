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
    	<form method="post" id="fm-proActiveDeal" class="editForm" >
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
							<td><label>下  发  人：</label></td>
						    <td>
						    	<input type="text" id="dia-issueUser" name="dia-issueUser" datasource="ISSUE_USER" datatype="1,is_null,30" readonly="true"/>
						    </td>
						    <td><label>下发时间：</label></td>
						    <td>
						    	<input type="text" id="dia-issueTime" name="dia-issueTime" datasource="ISSUE_TIME" datatype="1,is_null,30" readonly="true"/>
						    </td>
						</tr>
						<tr>
						    <td><label>预计费用：</label></td>
						    <td>
					    		<input type="text" id="dia-planFee"  name="dia-planFee" dataSource="PLAN_FEE" datatype="0,is_null,10" />
					   		</td>
					   		<td><label>有效标识：</label></td>
						    <td>
						    	<select id="dia-status" name="dia-status" kind="dic" src="YXBS" datasource="STATUS" datatype="1,is_null,6" operation="=" >
						    		<option value="-1" selected>-----</option>
						    	</select>
						    </td>
						</tr>
						<tr>
						    <td><label>参与人数：</label></td>
						    <td colspan=3>
					    		<input type="text" id="dia-personNums"  name="dia-personNums" dataSource="PERSON_NUMS" datatype="0,is_digit,3" />
					   		</td>
						</tr>
						<tr>
						    <td><label>执行方案：</label></td>
						    <td colspan="3">
								<textarea id="dia-dealContent" style="width:93%;height:40px;" name="dia-dealContent" datasource="DEAL_CONTENT" datatype="0,is_null,1000"></textarea>
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
                <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
            </ul>
        </div>
	</div>
	</div>
</div>

<script type="text/javascript">
    
	//取父页面传过的参数确定新增或者修改
    var diaAction = "<%=action%>";
    var diaUrl = "<%=request.getContextPath()%>/part/marketMng/marketActiveMng/ProActiveDealAction";
    
    $(function () {
        
        var dealId = $("#dia-dealId").val();
        if(dealId)	//修改初始化
		{ 	
			var selectedRows = $("#tab-proActiveDealList").getSelectedRows();
          	setEditValue("fm-proActiveDeal", selectedRows[0].attr("rowdata"));
          	
		} else {  //新增初始化
			
			var selectedRows = $("#tab-proActiveDealList").getSelectedRows();
          	setEditValue("fm-proActiveDeal", selectedRows[0].attr("rowdata"));
          	
          	//默认显示有效标识
			$("#dia-status").val("<%=DicConstant.YXBS_01%>");
			$("#dia-status").attr("dic_code","<%=DicConstant.YXBS_01%>");
			$("#dia-status").find("option").val("<%=DicConstant.YXBS_01%>");
			$("#dia-status").find("option").text("有效");
      	} 
        
        //保存按钮
        $("#btn-save").bind("click", function(event){
			var $f = $("#fm-proActiveDeal");
			if (submitForm($f) == false) return false;
			var sCondition = {};
			sCondition = $f.combined(1) || {};
			var addUrl = diaUrl + "/insert.ajax";
			doNormalSubmit($f,addUrl,"btn-save",sCondition,diaInsertCallBack);
		});
	});
	
	//新增方法回调
    function diaInsertCallBack(res)
    {
    	try
    	{
    		var selectedIndex = $("#tab-proActiveDealList").getSelectedIndex();
    		$("#tab-proActiveDealList").updateResult(res,selectedIndex);
    		$.pdialog.closeCurrent();
    	}catch(e)
    	{
    		alertMsg.error(e);
    		return false;
    	}
    	return true;
    }

</script>