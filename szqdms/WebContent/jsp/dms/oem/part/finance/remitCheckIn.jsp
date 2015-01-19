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
    <form method="post" id="fm-remitInfo" class="editForm" >
        <input type="hidden" id="dia-remitId" name="dia-remitId" datasource="REMIT_ID" />
        <div align="left">
            <fieldset>
            <table class="editTable" id="tab-remit_Edit_Info">
                <tr>
                    <td><label>账户类型：</label></td>
                    <td><input type="text" id="dia-AMOUNT_TYPE" name="dia-AMOUNT_TYPE" datasource="AMOUNT_TYPE" datatype="1,is_null,30" readonly="true"/></td>
                	<td><label>入账日期：</label></td>
                    <td>
                    	<input type="text" id="diaInDate" name="diaInDate" datasource="IN_DATE" datatype="0,is_date,30" onclick="WdatePicker()" />
                    </td>
                </tr>
                <tr id="DraftNo" style="display: none">
                    <td><label>承兑票号：</label></td>
                    <td>
                        <input type="text" id="dia-DRAFT_NO" name="dia-DRAFT_NO" datasource="DRAFT_NO" datatype="1,is_null,30" readonly="true"/>
                    </td>
                </tr>
                <tr>
                    <td><label>入账金额：</label></td>
                    <td>
                        <input type="text" id="dia-BILL_AMOUNT" name="dia-BILL_AMOUNT" datasource="BILL_AMOUNT" datatype="1,is_money,10" readonly="true"/>
                    </td>
                </tr>
                <tr>
                    <td><label>备注：</label></td>
                    <td colspan="5">
                      <textarea id="dia-REMARK" style="width:450px;height:40px;" name="dia-REMARK" datasource="REMARK" datatype="1,is_null,1000" readonly="true"></textarea>
                    </td>
                </tr>
            </table>
            </fieldset>
        </div>
    </form>
    <div class="formBar">
        <ul>
            <li id="dia-contror" style="display: none"><div class="button"><div class="buttonContent"><button type="button" id="btn-confirm" >确&nbsp;&nbsp;认</button></div></div></li>
            <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
        </ul>
    </div>
    </div>
    </div>
</div>

<script type="text/javascript">
/* 取父页面传过的参数确定新增或者修改 */
    var diaAction = "<%=action%>";
    var diaUrl = "<%=request.getContextPath()%>/part/financeMng/remitMng/RemitCheckInMngAction";
    $(function () {

        //修改页面赋值
        if (diaAction != "1") {
            var selectedRows = $("#tab-remit_info").getSelectedRows();
            setEditValue("fm-remitInfo", selectedRows[0].attr("rowdata"));
            $("#dia-contror").show();
        }

        var url = diaUrl+"/getIsDs.ajax";
        sendPost(url, "", "", searchCallBack, "false");
        //保存基本信息按钮响应
        $("#btn-confirm").bind("click", function(event){
        	if($("#diaInDate").val()){
        		
        		var $f = $("#fm-remitInfo");
                if (submitForm($f) == false) return false;
                var sCondition = {};
                sCondition = $("#fm-remitInfo").combined(1) || {};
                var addUrl = mngUrl + "/remitConfirm.ajax";
                doNormalSubmit($f,addUrl,"btn-confirm",sCondition,confirmCallBack);
        	}else{
        		alertMsg.warn("请填写入账日期.");
    			return false;
        	}
        	
        });
        //关闭按钮响应
        $("button.close").click(function(){
            $.pdialog.closeCurrent();
            return false;
        });

    })

        // 新增回调函数
    function searchCallBack(res) {
        try {
            var rows = res.getElementsByTagName("ROW");
            var isDs = getNodeText(rows[0].getElementsByTagName("IS_DS").item(0));
            if(isDs==<%=DicConstant.SF_01%>) {
                // 直营店
                $("#dia-AMOUNT_TYPE").attr("filtercode",<%=DicConstant.ZJZHLX_01%>);
            }
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

    function getVoucherNo(){
        var type = $("#dia-AMOUNT_TYPE").val();
        if(type==<%=DicConstant.ZJZHLX_02%>){
            $("#DraftNo").show();
        }else{
            $("#DraftNo").hide();
        }
    }
    //新增方法回调
    function diaInsertCallBack(res)
    {
        var rows = res.getElementsByTagName("ROW");
        try
        {
        	doSearch();
        	$.pdialog.closeCurrent();
        }catch(e)
        {
            alertMsg.error(e);
            return false;
        }
        return true;
    }
    function  confirmCallBack(res)
    {
    	try
    	{
    		$.pdialog.closeCurrent();
    		doSearch();
    	}catch(e)
    	{
    		alertMsg.error(e);
    		return false;
    	}
    	return true;
    }
    //修改方法回调
    function diaUpdateCallBack(res)
    {
        try
        {
            var selectedIndex = $("#tab-remit_info").getSelectedIndex();
            $("#tab-remit_info").updateResult(res,selectedIndex);
        }catch(e)
        {
            alertMsg.error(e);
            return false;
        }
        return true;
    }
    var $row;
    function reportRemitCallBack(){
        var selectedRows = $("#tab-remit_info").getSelectedRows();
        $row =  selectedRows[0];
        $("#tab-remit_info").removeResult($row);
        $.pdialog.closeCurrent();
    }
    
</script>