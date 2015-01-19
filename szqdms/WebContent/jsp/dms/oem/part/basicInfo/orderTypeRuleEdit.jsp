<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.org.framework.common.User" %>
<%@ page import="com.org.framework.Globals" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
    String contentPath = request.getContextPath();
    String action = request.getParameter("action");
    if (action == null) {
        action = "1";
    }
%>
<div id="dia-layout">
    <div class="page">
        <div class="pageContent" style="">
            <form method="post" id="dia-fm-orderTypeRule" class="editForm">
                <input type="hidden" id="dia-typeRuleId" name="dia-typeRuleId" datasource="TYPERULE_ID"/>
                <div align="left">
                      <table class="editTable" id="dia-tab-orderTypeRule">
                           <tr>
                           	<td><label>类型代码：</label></td>
                              <td><input type="text" id="dia-typeCode" name="dia-typeCode" datasource="TYPE_CODE" datatype="0,is_digit_letter,30"/></td>
                              <td><label>订单类型：</label></td>
                              <td><input type="text" id="dia-orderType" name="dia-orderType" kind="dic" src="DDLX" datasource="ORDER_TYPE" datatype="0,is_null,30"/></td>
                              <td><label>首位字符：</label></td>
                              <td><input type="text" id="dia-firstLetter" name="dia-firstLetter" style="width:50px;" datasource="FIRST_LETTER" onchange="checkFirstLetter();" datatype="0,is_letter,2"/></td>
                          </tr>
                          <tr>
                           	<td><label>渠道提报：</label></td>
                              <td>
                                  <select class="combox" id="dia-ifChanel" name="dia-ifChanel" kind="dic" src="SF" datasource="IF_CHANEL" datatype="0,is_null,6">
                                       <option value="-1">--</option>
                                  </select>
                              </td>
                              <td><label>需汇总：</label></td>
                              <td>
                                  <select class="combox" id="dia-ifSummary" name="dia-ifSummary" kind="dic" src="SF" datasource="IF_SUMMARY" datatype="0,is_null,6">
                                       <option value="-1">--</option>
                                  </select>
                              </td>
                              <td><label>地址可选：</label></td>
                              <td>
                                  <select class="combox" id="dia-ifChooseAddr" name="dia-ifChooseAddr" kind="dic" src="SF" datasource="IF_CHOOSEADDR" datatype="0,is_null,6">
                                       <option value="-1">--</option>
                                  </select>
                              </td>
                          </tr>
                          <tr>
                              <td><label>资金限制：</label></td>
                              <td>
                                  <select class="combox" id="dia-ifFunds" name="dia-ifFunds" kind="dic" src="SF" datasource="IF_FUNDS" datatype="0,is_null,6">
                                      <option value="-1">--</option>
                                  </select>
                              </td>
                              <td><label>占用库存：</label></td>
                              <td>
                                  <select class="combox" id="dia-ifStorage" name="dia-ifStorage" kind="dic" src="SF" datasource="IF_STORAGE" datatype="0,is_null,6">
                                       <option value="-1">--</option>
                                  </select>
                              </td>
                              <td><label>允许自提：</label></td>
                              <td>
                                  <select class="combox" id="dia-ifOwnPick" name="dia-ifOwnPick" kind="dic" src="SF" datasource="IF_OWNPICK" datatype="0,is_null,6">
                                       <option value="-1">--</option>
                                  </select>
                              </td>
                          </tr>
                          <tr>
                          	<td><label>是否免运费：</label></td>
                              <td>
                                  <select class="combox" id="dia-ifFree" name="dia-ifFree" kind="dic" src="SF" datasource="IF_FREE" datatype="0,is_null,6">
                                       <option value="-1">--</option>
                                  </select>
                              </td>
                              <td id="freeTimes1"><label>免运费次数：</label></td>
                              <td id="freeTimes2">
                              	<input type="text" id="dia-freeTimes" name="dia-freeTimes" datasource="FREE_TIMES" style="width:40px" datatype="0,is_digit,6"/>
                              </td>
                          </tr>
                          <tr>
                          	<td><label>限制提报次数：</label></td>
                              <td>
                                  <select class="combox" id="dia-ifApplyTimes" name="dia-ifApplyTimes" kind="dic" src="SF" datasource="IF_APPLYTIMES" datatype="0,is_digit,6">
                                       <option value="-1">--</option>
                                  </select>
                              </td>
                              <td id="applyTimes1"><label>提报次数：</label></td>
                              <td id="applyTimes2">
                              	<input type="text" id="dia-applyTimes" name="dia-applyTimes" datasource="APPLY_TIMES" style="width:40px" datatype="0,is_digit,10"/>
                              </td>
                          </tr>
                          <tr>
                          	<td><label>限制提报时间：</label></td>
                              <td>
                                  <select class="combox" id="dia-ifApplyDate" name="dia-ifApplyDate" kind="dic" src="SF" datasource="IF_APPLYDATE" datatype="0,is_null,6">
                                       <option value="-1">--</option>
                                  </select>
                              </td>
                              <td id="applyDate1"><label>提报时间(服)：</label></td>
                              <td id="applyDate2">
                              	<input type="text" id="dia-seStartDate" name="dia-seStartDate" style="width:30px" datasource="SE_STARTDATE" datatype="0,is_digit,2"/>
                              	<span style="float:left;margin-left:-30px;margin-top:5px;">至</span>
                              	<input type="text" id="dia-seEndDate" name="dia-seEndDate" style="width:30px" datasource="SE_ENDDATE" datatype="0,is_digit,2"/>
                              </td>
                              <td id="applyDate3"><label>提报时间(配)：</label></td>
                              <td id="applyDate4">
                              	<input type="text" id="dia-dcStartDate" name="dia-dcStartDate" style="width:30px" datasource="DC_STARTDATE" datatype="0,is_digit,2"/>
                              	<span style="float:left;margin-left:-30px;margin-top:5px;">至</span>
                              	<input type="text" id="dia-dcEndDate" name="dia-dcEndDate" style="width:30px" datasource="DC_ENDDATE" datatype="0,is_digit,2"/>
                              </td>
                          </tr>
                          <tr>
                          	<td><label>状态：</label></td>
                              <td>
                                  <select class="combox" id="dia-status" name="dia-status" kind="dic" src="YXBS" datasource="STATUS" datatype="0,is_null,10">
                                      <option value=<%=DicConstant.YXBS_01 %>>有效</option>
                                  </select>
                              </td>
                          </tr>
                      </table>
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
    var diaUrl = "<%=request.getContextPath()%>/part/basicInfoMng/OrderTypeRuleMngAction";
    var actionFlag = "<%=action%>";
    //初始化
    $(function () {
        $("#btn-save").bind("click", function (event) {
            var $f = $("#dia-fm-orderTypeRule");
            if (submitForm($f) == false) return false;
            var sCondition = {};
            sCondition = $("#dia-fm-orderTypeRule").combined(1) || {};
            if (actionFlag == 1) {
                var addUrl = diaUrl + "/orderTypeRuleInsert.ajax";
                doNormalSubmit($f, addUrl, "btn-save", sCondition, diaInsertCallBack);
            } else {
                var updateUrl = diaUrl + "/orderTypeRuleUpdate.ajax";
                doNormalSubmit($f, updateUrl, "btn-save", sCondition, diaUpdateCallBack);
            }
        });
		if(actionFlag=="1"){
			$("#freeTimes1").hide();
			$("#freeTimes2").hide();
			$("#applyTimes1").hide();
			$("#applyTimes2").hide();
			$("#applyDate1").hide();
			$("#applyDate2").hide();
			$("#applyDate3").hide();
			$("#applyDate4").hide();
		}else{
            var selectedRows = $("#tab-searchList").getSelectedRows();
            setEditValue("dia-fm-orderTypeRule", selectedRows[0].attr("rowdata"));
            $("#dia-typeCode").attr("readonly","readonly");
			$("#dia-orderType").attr("readonly","readonly");
            if(selectedRows[0].attr("IF_FREE")=="100101"){
            	$("#freeTimes1").show();
				$("#freeTimes2").show();
            }else{
            	$("#freeTimes1").hide();
				$("#freeTimes2").hide();
            }
            if(selectedRows[0].attr("IF_APPLYTIMES")=="100101"){
            	$("#applyTimes1").show();
				$("#applyTimes2").show();
            }else{
            	$("#applyTimes1").hide();
				$("#applyTimes2").hide();
            }
            if(selectedRows[0].attr("IF_APPLYDATE")=="100101"){
            	$("#applyDate1").show();
				$("#applyDate2").show();
				$("#applyDate3").show();
				$("#applyDate4").show();
            }else{
            	$("#applyDate1").hide();
				$("#applyDate2").hide();
				$("#applyDate3").hide();
				$("#applyDate4").hide();
            }
        }
    });

    // 首位字符验证
    function checkFirstLetter() {
        var reg = /^[A-Z]{2}$/;
        if(reg.test($("#dia-firstLetter").val())) {
            return true;
        } else {
            alertMsg.warn("请输入两位大写字母!");
            $("#dia-firstLetter").val("");
            return false;
        }
    }

    //新增回调函数
    function diaInsertCallBack(res) {
        try {
        	var rows = res.getElementsByTagName("ROW");
    		if(rows && rows.length > 0)
    		{
    			$("#dia-typeRuleId").val(getNodeText(rows[0].getElementsByTagName("TYPERULE_ID").item(0)));
    			$("#dia-typeCode").attr("readonly","readonly");
    			$("#dia-orderType").attr("readonly","readonly");
    			 var url = "<%=request.getContextPath()%>/part/basicInfoMng/OrderTypeRuleMngAction/orderTypeRuleSearch.ajax";
    			 var $f = $("#searchForm");
                 var sCondition = {};
                 sCondition = $f.combined() || {};
                 doFormSubmit($f, url, "btn-search", 1, sCondition, "tab-searchList");
    		}
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

    //更新回调函数
    function diaUpdateCallBack(res) {
        try {
        	var url = "<%=request.getContextPath()%>/part/basicInfoMng/OrderTypeRuleMngAction/orderTypeRuleSearch.ajax";
			var $f = $("#searchForm");
            var sCondition = {};
            sCondition = $f.combined() || {};
            doFormSubmit($f, url, "btn-search", 1, sCondition, "tab-searchList");
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }
    function afterDicItemClick(id,$row){
		var ret = true;
		if(id == "dia-ifFree")
		{
			switch($("#"+id).attr("code"))
			{
			case "100101":
				$("#freeTimes1").show();
				$("#freeTimes2").show();
				break;
			case "100102":
				$("#freeTimes1").hide();
				$("#freeTimes2").hide();
				$("#dia-freeTimes").val("");
				break;
			}
		}
		if(id == "dia-ifApplyTimes")
		{
			switch($("#"+id).attr("code"))
			{
			case "100101":
				$("#applyTimes1").show();
				$("#applyTimes2").show();
				break;
			case "100102":
				$("#applyTimes1").hide();
				$("#applyTimes2").hide();
				$("#dia-applyTimes").val("");
				break;
			}
		}
		if(id == "dia-ifApplyDate")
		{
			switch($("#"+id).attr("code"))
			{
			case "100101":
				$("#applyDate1").show();
				$("#applyDate2").show();
				$("#applyDate3").show();
				$("#applyDate4").show();
				break;
			case "100102":
				$("#applyDate1").hide();
				$("#applyDate2").hide();
				$("#applyDate3").hide();
				$("#applyDate4").hide();
				$("#dia-dcStartDate").val("");
				$("#dia-dcEndDate").val("");
				$("#dia-seStartDate").val("");
				$("#dia-seEndDate").val("");
				break;
			}
		}
		return ret;
	}
   
</script>