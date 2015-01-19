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
%>
<div id="dia-layout">
    <div class="page">
    <div class="pageContent" style="" >
    <form method="post" id="fm-remitInfo" class="editForm" >
        <div align="left">
            <fieldset>
            <table class="editTable" id="tab-remit_Edit_Info">
            	<input type="hidden" id="dias-SHIP_ID" name="dias-SHIP_ID" datasource="SHIP_ID" />
        		<input type="hidden" id="dias-VEHICLE_ID" name="dias-VEHICLE_ID" datasource="VEHICLE_ID" />
                <tr>
                    <td><label>木箱数量：</label></td>
                    <td>
                        <input type="text" id="dia-WOOD_BOX" name="dia-WOOD_BOX" datasource="WOOD_BOX" datatype="1,is_null,30"/>
                    </td>
                    <td><label>纸箱数量：</label></td>
                    <td>
                        <input type="text" id="dia-PAPER_BOX" name="dia-PAPER_BOX" datasource="PAPER_BOX" datatype="1,is_null,30"/>
                    </td>
                </tr>
                <tr>
                    <td><label>无包装数量：</label></td>
                    <td>
                        <input type="text" id="dia-NO_PACKED" name="dia-NO_PACKED" datasource="NO_PACKED" datatype="1,is_null,30"/>
                    </td>
                    <td><label>其他：</label></td>
                    <td>
                        <input type="text" id="dia-OTHER_PACKED" name="dia-OTHER_PACKED" datasource="OTHER_PACKED" datatype="1,is_null,30"/>
                    </td>
                </tr>
                <tr>
                    <td><label>预计到货日期：</label></td>
                    <td>
                    	<input type="text" id="diaExpectDate" name="diaExpectDate" datasource="EXPECT_DATE" datatype="0,is_date,30" onclick="WdatePicker({minDate:'%y-%M-%d'})" />
                    </td>
                </tr>
                <tr>
                    <td><label>发车备注：</label></td>
                    <td colspan="2">
                    	<textarea id="SHIP_VEL_REMARKS"  name="SHIP_VEL_REMARKS" datasource="SHIP_VEL_REMARKS" style="width:350px;height:50px;" datasource="EXPECT_DATE" datatype="1,is_null,1000"></textarea>
                    </td>
                </tr>
            </table>
            </fieldset>
        </div>
    </form>
    <div class="formBar">
        <ul>
            <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-save">保&nbsp;&nbsp;存</button></div></div></li>
            <li id="dia-contror" style="display: none"><div class="button"><div class="buttonContent"><button type="button" id="btn-report" >提&nbsp;&nbsp;报</button></div></div></li>
            <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
        </ul>
    </div>
    </div>
    </div>
</div>

<script type="text/javascript">
/* 取父页面传过的参数确定新增或者修改 */
    var diaUrl = "<%=request.getContextPath()%>/part/storageMng/shipMng/ShipOutAction";
    var areaSelWin = $("body").data("addPack");
    $(function () {

    	var selectedRows = $("#tab-carrierList").getSelectedRows();
        setEditValue("tab-remit_Edit_Info", selectedRows[0].attr("rowdata"));
        //保存基本信息按钮响应
        $("#btn-save").bind("click", function(event){
            var $f = $("#fm-remitInfo");
            if (submitForm($f) == false) return false;
            var sCondition = {};
            sCondition = $("#fm-remitInfo").combined(1) || {};
            var addUrl = diaUrl + "/packedInsert.ajax";
            doNormalSubmit($f,addUrl,"btn-save",sCondition,diaInsertCallBack);
        });
        //关闭按钮响应
        $("button.close").click(function(){
            $.pdialog.closeCurrent();
            return false;
        });

    })
function diaInsertCallBack(res)
    {
        var rows = res.getElementsByTagName("ROW");
        try
        {
        	searchCarrier();
        	$.pdialog.close(areaSelWin);
        	/* $.pdialog.closeCurrent(); */
        }catch(e)
        {
            alertMsg.error(e);
            return false;
        }
        return true;
    }
    
</script>