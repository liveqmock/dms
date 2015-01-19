<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <form method="post" id="fm-cash_Info" class="editForm" >
        <div align="left">
            <fieldset>
            <table class="editTable" id="tab-cash_Edit_Info">
            <input type="hidden" id="dia-IN_AMOUNT" name="dia-IN_AMOUNT" datasource="IN_AMOUNT" />
            <input type="hidden" id="dia-ACCOUNT_ID" name="dia-ACCOUNT_ID" datasource="ACCOUNT_ID" />
            <input type="hidden" id="dia-IN_ACCOUNT_ID" name="dia-IN_ACCOUNT_ID" datasource="IN_ACCOUNT_ID" />
            <input type="hidden" id="dia-ORG_ID" name="dia-ORG_ID" datasource="ORG_ID" />
            <input type="hidden" id="dia-orgCode" name="dia-orgCode" datasource="" datatype="0,is_null,10000"/>
<!--             <input type="hidden" id="dia-ORG_NAME" name="dia-ORG_NAME" datasource="ORG_NAME"  readonly="true" style="width: 200px"/> -->
                <tr>
                    <td><label>渠道代码：</label></td>
                    <td><input type="text" id="dia-ORG_CODE" name="dia-ORG_CODE" datasource="ORG_CODE"  readonly="true"/></td>
                    <td><label>渠道名称：</label></td>
                    <td><div id="dia-ORG_NAME"></div></td>
                </tr>
                <tr>
                    <td><label>操作类型：</label></td>
                    <td>
                        <input type="text" id="dia-LOG_TYPE" name="dia-LOG_TYPE" kind="dic" src="ZJYDLX" datasource="LOG_TYPE" filtercode="" datatype="0,is_null,30" operation="="/>
                    </td>
                    <td id="amountType1"><label>账户类型：</label></td>
                    <td  id="amountType2">
                        <input type="text" id="dia-AMOUNT_TYPE" name="dia-AMOUNT_TYPE" kind="dic" src="ZJZHLX" datasource="AMOUNT_TYPE" filtercode="" datatype="0,is_null,30" operation="="/>
                    </td>
                </tr>
                <tr id="amountTr">
                    <td><label>可用余额：</label></td>
                    <td><input type="text" id="dia-AVAILABLE_AMOUNT" name="dia-AVAILABLE_AMOUNT" datasource="AVAILABLE_AMOUNT" readonly="true"/></td>
                    <td><label>入账金额：</label></td>
                    <td><input type="text" id="dia-AMOUNT" name="dia-AMOUNT" datasource="AMOUNT" datatype="1,is_money,10"/></td>
                </tr>
                <tr id="orgId" style="display:none;">
                    <td><label>转入渠道商：</label></td>
                    <td>
                    <input type="text" id="dia-orgName" name="dia-orgName" datasource="" datatype="0,is_null,10000" readOnly hasBtn="true" callFunction="showOrgTree('dia-orgName',1,1,2)" operation="="/></td>
                    <td><label>信用额度占用：</label></td>
                    <td><input type="text" id="dia-REBATE_OCCUPY_AMOUNT" name="dia-REBATE_OCCUPY_AMOUNT" datasource="REBATE_OCCUPY_AMOUNT" readonly="true" style="width:90px"/><div class="buttonActive" id="div-btn-dtl"><div class="buttonContent"><button type="button" id="btn-dtl">调账明细</button></div></div></td>
                </tr>	
                <tr>
                	<td><label>入账日期：</label></td>
                    <td>
                    	<input type="text" id="diaInDate" name="diaInDate" datasource="IN_DATE" datatype="0,is_date,30" onclick="WdatePicker()" />
                    </td>
                </tr>
                <tr>
                    <td><label>备注：</label></td>
                    <td colspan="5"><textarea id="dia-REMARKS" style="width:450px;height:40px;" name="dia-REMARKS" datasource="REMARKS" datatype="1,is_null,1000"></textarea></td>
                </tr>
            </table>
            </fieldset>
        </div>
    </form>
    <div class="page">
    <div class="pageContent" style="display:none;" id="div-table">
        <div id="page_grid">
            <table border="1" width="100%" class="dlist">
                <tr height="30px">
                    <th width="20%"></th>
                    <th width="20%">调出方可用(元)</th>
                    <th width="20%">调入方可用(元)</th>
                    <th width="20%">调入后可用(元)</th>
                    <th width="20%">抵扣信用额度(元)</th>
                </tr>
                <tr>
                    <td>现金</td>
                    <td><input type="text" id="out-CASH_AVAILABLE_AMOUNT" name="out-CASH_AVAILABLE_AMOUNT" readOnly/></td>
                    <td><input type="text" id="in-CASH_AVAILABLE_AMOUNT" name="in-CASH_AVAILABLE_AMOUNT" readOnly/></td>
                    <td><input type="text" id="end-CASH_AVAILABLE_AMOUNT" name="end-CASH_AVAILABLE_AMOUNT" readOnly/></td>
                    <td><input type="text" id="dia-CASH" name="dia-CASH" onblur="checkValue(this)" datatype="1,is_money,30"/></td>
                </tr>
                <tr>
                    <td>承兑</td>
                    <td><input type="text" id="out-ACCEPT_AVAILABLE_AMOUNT" name="out-ACCEPT_AVAILABLE_AMOUNT" readOnly/></td>
                    <td><input type="text" id="in-ACCEPT_AVAILABLE_AMOUNT" name="in-ACCEPT_AVAILABLE_AMOUNT" readOnly/></td>
                    <td><input type="text" id="end-ACCEPT_AVAILABLE_AMOUNT" name="end-ACCEPT_AVAILABLE_AMOUNT" readOnly/></td>
                    <td><input type="text" id="dia-ACCEPT" name="dia-ACCEPT" onblur="checkValue(this)" datatype="1,is_money,30"/></td>
                </tr>
                <tr>
                    <td>材料费</td>
                    <td><input type="text" id="out-MATERIAL_AVAILABLE_AMOUNT" name="out-MATERIAL_AVAILABLE_AMOUNT" readOnly/></td>
                    <td><input type="text" id="in-MATERIAL_AVAILABLE_AMOUNT" name="in-MATERIAL_AVAILABLE_AMOUNT" readOnly/></td>
                    <td><input type="text" id="end-MATERIAL_AVAILABLE_AMOUNT" name="end-MATERIAL_AVAILABLE_AMOUNT" readOnly/></td>
                    <td><input type="text" id="dia-MATERIAL" name="dia-MATERIAL" onblur="checkValue(this)" datatype="1,is_money,30"/></td>
                </tr>
                <tr>
                    <td>信用额度</td>
                    <td><input type="text" id="out-CREDIT_AVAILABLE_AMOUNT" name="out-CREDIT_AVAILABLE_AMOUNT" readOnly/></td>
                    <td><input type="text" id="in-CREDIT_AVAILABLE_AMOUNT" name="in-CREDIT_AVAILABLE_AMOUNT" readOnly/></td>
                    <td><input type="text" id="end-CREDIT_AVAILABLE_AMOUNT" name="end-CREDIT_AVAILABLE_AMOUNT" readOnly/></td>
                    <td><input type="text" id="dia-CREDIT" name="dia-CREDIT" readOnly/></td>
                </tr>
                <tr>
                    <td>返利</td>
                    <td><input type="text" id="out-REBATE_AVAILABLE_AMOUNT" name="out-REBATE_AVAILABLE_AMOUNT" readOnly/></td>
                    <td><input type="text" id="in-REBATE_AVAILABLE_AMOUNT" name="in-REBATE_AVAILABLE_AMOUNT" readOnly/></td>
                    <td><input type="text" id="end-REBATE_AVAILABLE_AMOUNT" name="end-REBATE_AVAILABLE_AMOUNT" readOnly/></td>
                    <td><input type="text" id="dia-REBATE" name="dia-REBATE" onblur="checkValue(this)" datatype="1,is_money,30"/></td>
                </tr>
            </table>
        </div>
    </div>
    </div>
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
/* 取父页面传过的参数确定新增或者修改 */
    var diaAction = "<%=action%>";
    var diaUrl = "<%=request.getContextPath()%>/part/financeMng/cashAccountMng/CashAccountMngAction";
    $(function () {

        var selectedRows = $("#tab-cash_info").getSelectedRows();
        var ORG_ID = selectedRows[0].attr("ORG_ID");
        $("#dia-ORG_ID").val(ORG_ID);
        var ORG_CODE = selectedRows[0].attr("ORG_CODE");
        $("#dia-ORG_CODE").val(ORG_CODE);
        var ORG_NAME = selectedRows[0].attr("ORG_NAME");
//         $("#dia-ORG_NAME").val(ORG_NAME);
        $("#dia-ORG_NAME").html(ORG_NAME);
//         var ACCOUNT_ID = selectedRows[0].attr("CASH_ACCOUNT_ID");
//         $("#dia-ACCOUNT_ID").val(ACCOUNT_ID);
        // 信用额度占用
        var REBATE_OCCUPY_AMOUNT = selectedRows[0].attr("CREDIT_OCCUPY_AMOUNT");
        $("#dia-REBATE_OCCUPY_AMOUNT").val(REBATE_OCCUPY_AMOUNT);
        if (REBATE_OCCUPY_AMOUNT == '0') {
            // 隐藏调账明细按钮
            $("#div-btn-dtl").hide();
        }
        
        //调出可用
        $("#out-CASH_AVAILABLE_AMOUNT").val(selectedRows[0].attr("CASH_AVAILABLE_AMOUNT"));
        $("#out-ACCEPT_AVAILABLE_AMOUNT").val(selectedRows[0].attr("ACCEPT_AVAILABLE_AMOUNT"));
        $("#out-MATERIAL_AVAILABLE_AMOUNT").val(selectedRows[0].attr("MATERIAL_AVAILABLE_AMOUNT"));
        $("#out-CREDIT_AVAILABLE_AMOUNT").val(selectedRows[0].attr("CREDIT_AVAILABLE_AMOUNT"));
        $("#out-REBATE_AVAILABLE_AMOUNT").val(selectedRows[0].attr("REBATE_AVAILABLE_AMOUNT"));
        
        //修改页面赋值
        if (diaAction == "1") {
            // 回款
            $("#dia-LOG_TYPE").attr("filtercode",'<%=DicConstant.ZJYDLX_04%>|<%=DicConstant.ZJYDLX_05 %>');
        } else {
            // 欠款
            $("#dia-LOG_TYPE").attr("filtercode",'<%=DicConstant.ZJYDLX_01%>|<%=DicConstant.ZJYDLX_06%>|<%=DicConstant.ZJYDLX_07%>|<%=DicConstant.ZJYDLX_08 %>');
        }

        //保存基本信息按钮响应
        $("#btn-save").bind("click", function(event){
            if (<%=DicConstant.ZJYDLX_08 %>==$("#dia-LOG_TYPE").attr("code")) {
                // 调账
                if ($("#dia-orgCode").val()==""||$("#dia-orgCode").val()==null) {
                    // 转入渠道商判断
                    alertMsg.warn('请选择转入渠道商!');
                    return;    
                }
                
                if ($("#dia-REBATE_OCCUPY_AMOUNT").val()!="0"&&$("#dia-REBATE_OCCUPY_AMOUNT").val()!="") {
                    // 信用额度占用不为0
                    var inAmount1 = $("#end-CASH_AVAILABLE_AMOUNT").val()*1-$("#dia-CASH").val()*1;
                    var inAmount2 = $("#end-ACCEPT_AVAILABLE_AMOUNT").val()-$("#dia-ACCEPT").val()*1;
                    var inAmount3 =  $("#end-MATERIAL_AVAILABLE_AMOUNT").val()-$("#dia-MATERIAL").val()*1;
                    var inAmount4 = $("#end-REBATE_AVAILABLE_AMOUNT").val()-$("#dia-REBATE").val()*1;
                    var inAmount = inAmount1+','+inAmount2+','+inAmount3+','+inAmount4+','+'0';
                    $("#dia-IN_AMOUNT").val(inAmount);
                    
                    // 抵扣信用额度验证
                    var rebateOccupyAmount = $("#dia-REBATE_OCCUPY_AMOUNT").val()*1;
                    var count = $("#dia-CASH").val()*1
                    +$("#dia-ACCEPT").val()*1
                    +$("#dia-MATERIAL").val()*1
                    +$("#dia-REBATE").val()*1;
                    if (rebateOccupyAmount != count) {
                        // 转入渠道商判断
                        alertMsg.warn('请满足信用额度占用和抵扣信用额度相等!');
                        return;
                    }
                }
                var selectedRows = $("#tab-cash_info").getSelectedRows();
                // 调出方ID
                var accountId = selectedRows[0].attr("CASH_ACCOUNT_ID")+","+selectedRows[0].attr("ACCEPT_ACCOUNT_ID")+
                ","+selectedRows[0].attr("MATERIAL_ACCOUNT_ID")+
                ","+selectedRows[0].attr("REBATE_ACCOUNT_ID")+
                ","+selectedRows[0].attr("CREDIT_ACCOUNT_ID");
                $("#dia-ACCOUNT_ID").val(accountId);
                // 调出方金额(现金，承兑，材料费，返利，信用额度)
                var amount = $("#out-CASH_AVAILABLE_AMOUNT").val()+","+$("#out-ACCEPT_AVAILABLE_AMOUNT").val()+","+$("#out-MATERIAL_AVAILABLE_AMOUNT").val()+","+$("#out-REBATE_AVAILABLE_AMOUNT").val()+","+$("#out-CREDIT_AVAILABLE_AMOUNT").val();
                $("#dia-AMOUNT").val(amount);
            } else {
                // 其他
                if(diaAction==1){
                	if ($("#dia-AMOUNT").val()*1>$("#dia-AVAILABLE_AMOUNT").val()*1) {
                        // 入账金额>可用余额
                        alertMsg.warn('入账金额不可大于可用余额!');
                        return;    
                    }
                }else{
                	
                }
                
            }
            var $f = $("#fm-cash_Info");
            if (submitForm($f) == false) return false;
            var sCondition = {};
            sCondition = $("#fm-cash_Info").combined(1) || {};
            var updateUrl = diaUrl + "/cashUpdate.ajax?orgCode=" + $("#dia-orgCode").val();
            doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
        });

        // 调账明细按钮绑定
        $("#btn-dtl").click(function(){
            if ($("#dia-orgCode").val()==""||$("#dia-orgCode").val()==null) {
                // 转入渠道商判断
                alertMsg.warn('请选择转入渠道商!');
                return;
            }
            var url = diaUrl + "/searchDtl.ajax?orgCode=" + $("#dia-orgCode").val();
            sendPost(url, "", "", dtlCallBack, "false");
        });

        //关闭按钮响应
        $("button.close").click(function(){
            $.pdialog.closeCurrent();
            return false;
        });

    })

    // 组织树的回调函数
    function showOrgTreeCallBack (res) {
    	// 渠道名称
    	$("#dia-orgName").val($(res).attr("orgName"));
    	// 渠道代码
    	$("#dia-orgCode").val($(res).attr("orgCode"));
    }
    // 调账明细按钮回调函数
    function dtlCallBack(res) {
        var rows = res.getElementsByTagName("ROW");
        // 读取XML中的FLAG属性(FLAG:true今天不在提报日内;)
        $("#in-CASH_AVAILABLE_AMOUNT").val(getNodeText(rows[0].getElementsByTagName("CASH_AVAILABLE_AMOUNT").item(0)));
        $("#in-ACCEPT_AVAILABLE_AMOUNT").val(getNodeText(rows[0].getElementsByTagName("ACCEPT_AVAILABLE_AMOUNT").item(0)));
        $("#in-MATERIAL_AVAILABLE_AMOUNT").val(getNodeText(rows[0].getElementsByTagName("MATERIAL_AVAILABLE_AMOUNT").item(0)));
        $("#in-CREDIT_AVAILABLE_AMOUNT").val(getNodeText(rows[0].getElementsByTagName("CREDIT_AVAILABLE_AMOUNT").item(0)));
        $("#in-REBATE_AVAILABLE_AMOUNT").val(getNodeText(rows[0].getElementsByTagName("REBATE_AVAILABLE_AMOUNT").item(0)));
        
        // 调入方账户ID(现金，承兑，材料费，返利，信用额度)
             var inAccountId = getNodeText(rows[0].getElementsByTagName("CASH_ACCOUNT_ID").item(0))+
             ','+getNodeText(rows[0].getElementsByTagName("ACCEPT_ACCOUNT_ID").item(0))+
             ','+getNodeText(rows[0].getElementsByTagName("MATERIAL_ACCOUNT_ID").item(0))+
             ','+getNodeText(rows[0].getElementsByTagName("REBATE_ACCOUNT_ID").item(0))+
             ','+getNodeText(rows[0].getElementsByTagName("CREDIT_ACCOUNT_ID").item(0));
        $("#dia-IN_ACCOUNT_ID").val(inAccountId);
        
        
        // 调入后可用
        $("#end-CASH_AVAILABLE_AMOUNT").val($("#out-CASH_AVAILABLE_AMOUNT").val()*1+$("#in-CASH_AVAILABLE_AMOUNT").val()*1);
        $("#end-ACCEPT_AVAILABLE_AMOUNT").val($("#out-ACCEPT_AVAILABLE_AMOUNT").val()*1+$("#in-ACCEPT_AVAILABLE_AMOUNT").val()*1);
        $("#end-MATERIAL_AVAILABLE_AMOUNT").val($("#out-MATERIAL_AVAILABLE_AMOUNT").val()*1+$("#in-MATERIAL_AVAILABLE_AMOUNT").val()*1);
//         $("#end-CREDIT_AVAILABLE_AMOUNT").val($("#out-CREDIT_AVAILABLE_AMOUNT").val()+$("#in-CREDIT_AVAILABLE_AMOUNT").val());
        $("#end-REBATE_AVAILABLE_AMOUNT").val($("#out-REBATE_AVAILABLE_AMOUNT").val()*1+$("#in-REBATE_AVAILABLE_AMOUNT").val()*1);
        
        if ($("#dia-REBATE_OCCUPY_AMOUNT").val()=="0") {
            // ------ 信用额度为0
            // 调入方账户ID(现金，承兑，材料费，返利，信用额度)
            var inAmount = getNodeText(rows[0].getElementsByTagName("CASH_AVAILABLE_AMOUNT").item(0))+
            ','+getNodeText(rows[0].getElementsByTagName("ACCEPT_AVAILABLE_AMOUNT").item(0))+
            ','+getNodeText(rows[0].getElementsByTagName("MATERIAL_AVAILABLE_AMOUNT").item(0))+
            ','+getNodeText(rows[0].getElementsByTagName("REBATE_AVAILABLE_AMOUNT").item(0))+
            ','+getNodeText(rows[0].getElementsByTagName("CREDIT_AVAILABLE_AMOUNT").item(0));
            $("#dia-IN_AMOUNT").val(inAmount);
            // 显示调账明细
        } else {
        }
            $("#div-table").show();
    }

    //新增方法回调
    function diaUpdateCallBack(res) {

        var rows = res.getElementsByTagName("ROW");
        try {
            doSearch();
            $.pdialog.closeCurrent();
        } catch(e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

    function checkValue(row) {
        if ($(row).val()*1 > $(row).parent().parent().find("td").eq(3).find("input").val()*1) {
            // 转入渠道商判断
            alertMsg.warn('抵扣信用额度大于调入后可用!');
            $(row).val("");
            return;
        }
    }
    // 数据字典回调函数
    function afterDicItemClick(id,$row){
    
        if (id=="dia-LOG_TYPE") {
            var logType = $("#dia-LOG_TYPE").attr("code");
            $("#dia-AMOUNT_TYPE").attr("code","");
            $("#dia-AMOUNT_TYPE").val("");
            $("#dia-ACCOUNT_ID").val("");
            $("#dia-AVAILABLE_AMOUNT").val("");
            $("#amountType1").show();
            $("#amountType2").show();
            $("#amountTr").show();
            $("#orgId").hide();
            var str = "";
            if (logType==<%=DicConstant.ZJYDLX_01%>) {
                // ------ 打款
                str = '<%=DicConstant.ZJZHLX_01%>|<%=DicConstant.ZJZHLX_02%>|<%=DicConstant.ZJZHLX_03 %>';
            } else if(logType==<%=DicConstant.ZJYDLX_04%>) {
                // ------ 扣款
                str = '<%=DicConstant.ZJZHLX_01%>|<%=DicConstant.ZJZHLX_02%>|<%=DicConstant.ZJZHLX_03 %>|<%=DicConstant.ZJZHLX_05%>';
            } else if(logType==<%=DicConstant.ZJYDLX_05%>) {
                // ------ 罚款
                str = '<%=DicConstant.ZJZHLX_03 %>';
            } else if(logType==<%=DicConstant.ZJYDLX_06%>) {
                // ------ 奖励
                str = '<%=DicConstant.ZJZHLX_03 %>';
            } else if(logType==<%=DicConstant.ZJYDLX_07%>) {
                // ------ 返利
                str = '<%=DicConstant.ZJZHLX_05 %>';
            } else if(logType==<%=DicConstant.ZJYDLX_08%>) {
                // ------ 调账
                $("#amountType1").hide();
                $("#amountType2").hide();
                $("#amountTr").hide();
                $("#orgId").show();
            }
            $("#dia-AMOUNT_TYPE").attr("filtercode",str);
        }
    
        if (id=="dia-AMOUNT_TYPE") {
            var selectedRows = $("#tab-cash_info").getSelectedRows();
            var type = $("#dia-AMOUNT_TYPE").attr("code");
            if(type==<%=DicConstant.ZJZHLX_01%>){
                // ------ 现金账户
                var ACCOUNT_ID = selectedRows[0].attr("CASH_ACCOUNT_ID");
                $("#dia-ACCOUNT_ID").val(ACCOUNT_ID);
                var AVAILABLE_AMOUNT = selectedRows[0].attr("CASH_AVAILABLE_AMOUNT");
                $("#dia-AVAILABLE_AMOUNT").val(AVAILABLE_AMOUNT);
            } else if(type==<%=DicConstant.ZJZHLX_02%>) {
                // ------ 承兑账户
                var ACCOUNT_ID = selectedRows[0].attr("ACCEPT_ACCOUNT_ID");
                $("#dia-ACCOUNT_ID").val(ACCOUNT_ID);
                var AVAILABLE_AMOUNT = selectedRows[0].attr("ACCEPT_AVAILABLE_AMOUNT");
                $("#dia-AVAILABLE_AMOUNT").val(AVAILABLE_AMOUNT);
            } else if(type==<%=DicConstant.ZJZHLX_03%>) {
                // ------ 材料费
                var ACCOUNT_ID = selectedRows[0].attr("MATERIAL_ACCOUNT_ID");
                $("#dia-ACCOUNT_ID").val(ACCOUNT_ID);
                var AVAILABLE_AMOUNT = selectedRows[0].attr("MATERIAL_AVAILABLE_AMOUNT");
                $("#dia-AVAILABLE_AMOUNT").val(AVAILABLE_AMOUNT);
            } else if(type==<%=DicConstant.ZJZHLX_04%>) {
                // ------ 信用额度
                var ACCOUNT_ID = selectedRows[0].attr("CREDIT_ACCOUNT_ID");
                $("#dia-ACCOUNT_ID").val(ACCOUNT_ID);
                var AVAILABLE_AMOUNT = selectedRows[0].attr("CREDIT_AVAILABLE_AMOUNT");
                $("#dia-AVAILABLE_AMOUNT").val(AVAILABLE_AMOUNT);
            } else if(type==<%=DicConstant.ZJZHLX_05%>) {
                // ------ 返利
                var ACCOUNT_ID = selectedRows[0].attr("REBATE_ACCOUNT_ID");
                $("#dia-ACCOUNT_ID").val(ACCOUNT_ID);
                var AVAILABLE_AMOUNT = selectedRows[0].attr("REBATE_AVAILABLE_AMOUNT");
                $("#dia-AVAILABLE_AMOUNT").val(AVAILABLE_AMOUNT);
                
            }
        }
    return true;
    }
</script>