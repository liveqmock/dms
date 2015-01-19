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
		<input type="hidden" id="dia-orgId" name="dia-orgId" datasource="ORG_ID" />
		<div align="left">
			<fieldset>
			<table class="editTable" id="tab-credit_Edit_Info">
				<tr>
				    <td><label>渠道代码：</label></td>
				    <td>
				    	<input type="hidden" id="dia-ORG_ID" name="dia-ORG_ID" datasource="ORG_ID" readonly="true"/>
				    	<input type="text" id="dia-ORG_CODE" name="dia-ORG_CODE" datasource="ORG_CODE" datatype="0,is_null,100" kind="dic" src="T#TM_ORG:CODE:ONAME{CODE,ONAME,ORG_ID}:1=1 AND ORG_TYPE=200005" readonly="true"/>
				    </td>
				    <td><label>渠道名称：</label></td>
				    <td>
				    	<input type="text" id="dia-ORG_NAME" name="dia-ORG_NAME" datasource="ORG_NAME" datatype="0,is_null,300" readonly="true"/>
				    </td>
				</tr>
				<tr>
					<td><label>额度类型：</label></td>
				    <td>
				    	<input type="text" id="dia-CREDIT_TYPE" name="dia-CREDIT_TYPE" datasource="CREDIT_TYPE" kind = "dic" src="XYEDLX" datatype="0,is_null,30"/>
				    </td>
					<td><label>输入金额(元)：</label></td>
				    <td>
				    	<input type="text" id="dia-NOW_LIMIT" name="dia-NOW_LIMIT" datasource="NOW_LIMIT" datatype="1,is_money,10"/>
				    </td>
				</tr>
				<tr>
                    <td><label>开始日期：</label></td>
                    <td>
                        <input name="dia-BEGIN_DATE" id="dia-BEGIN_DATE" kind="date" class="Wdate" datasource="BEGIN_DATE" group="BEGIN_DATE,END_DATE" datatype="0,is_date,20" onclick="WdatePicker()" value="" type="text"/>
                    </td>
                    <td><label>结束日期：</label></td>
                    <td>
                        <input name="dia-END_DATE" id="dia-END_DATE" kind="date" class="Wdate" datasource="END_DATE" group="BEGIN_DATE,END_DATE" datatype="0,is_date,20" onclick="WdatePicker()" value="" type="text"/>
                    </td>
				</tr>
				<tr>
				    <td><label>备注：</label></td>
				    <td colspan="5">
					  <textarea id="dia-REMARKS" style="width:450px;height:40px;" name="dia-REMARKS" datasource="REMARKS" datatype="1,is_null,1000"></textarea>
				    </td>
				</tr>
			</table>
			</fieldset>
		</div>
	</form>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-save">分&nbsp;&nbsp;配</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
		</ul>
	</div>
	</div>
	</div>
</div>

<script type="text/javascript">
/* 取父页面传过的参数确定新增或者修改 */
    var diaAction = "<%=action%>";
    var diaUrl = "<%=request.getContextPath()%>/part/financeMng/creditMng/CreditDistributionMngAction";
    $(function () {
        //保存基本信息按钮响应
        $("#btn-save").bind("click", function(event){
			var $f = $("#fm-creditInfo");
			if (submitForm($f) == false) return false;
			var sCondition = {};
			sCondition = $("#fm-creditInfo").combined(1) || {};
			var addUrl = diaUrl + "/creditInsert.ajax";
			doNormalSubmit($f,addUrl,"btn-save",sCondition,diaInsertCallBack);
		});
    	//关闭按钮响应
    	$("button.close").click(function(){
    		$.pdialog.closeCurrent();
    		return false;
    	});
    })
    var row;
    function diaInsertCallBack(){
    	doSearch();
		$.pdialog.closeCurrent();
    }
    function afterDicItemClick(id,$row){
		var ret = true;
		if(id == "dia-ORG_CODE")
		{
			$('#dia-ORG_CODE').attr('code',$row.attr('CODE'));
	        $('#dia-ORG_CODE').val($row.attr('CODE'));
	        $('#dia-ORG_NAME').val($row.attr('ONAME'));
	        $('#dia-ORG_ID').val($row.attr('ORG_ID'));
			
		}
		return ret;
	}

</script>