<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="com.org.frameImpl.Constant" %>
<%@ page import="com.org.framework.Globals" %>
<%@ page import="com.org.framework.common.User" %>
<%@ page import="com.org.dms.common.DicConstant" %>
<%@page import="com.org.framework.util.Pub"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contentPath = request.getContextPath();
	String action = request.getParameter("action");
	if (action == null)
	    action = "1";
	SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
	SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
	String yearStr1 = yearFormat.format(Pub.getCurrentDate());
	String monthStr1 = monthFormat.format(Pub.getCurrentDate());
%>
<div id="dia-layout">
    <div class="tabs"  eventType="click" id="dia-tabs">
        <div class="tabsHeader">
            <div class="tabsHeaderContent">
                <ul>
                    <li><a href="javascript:void(0);"><span>委托单信息</span></a></li>
                    <li><a href="javascript:void(0);"><span>材料信息</span></a></li>
                </ul>
            </div>
        </div>
        <div class="tabsContent">
            <!--基本信息Tab begin-->
            <div style="height:auto;overflow:hidden;">
                <form method="post" id="dia-fm-contractEdit" class="editForm" style="width:99%;">
		<!-- 隐藏域 -->
				<input type="hidden" id="dia-ENTRUST_ID" name="dia-ENTRUST_ID" datasource="ENTRUST_ID" />
		  <div align="left">
			  <fieldset>
						<table class="editTable" id="dia-tab-htxx">
			                <tr>
			                	<td><label>客户名称：</label></td>
			                    <td><input type="text" id="dia-orgName" name="dia-orgName" datasource="ORG_NAME" datatype="0,is_null,3000" /></td>
			                	<td><label>发票类型：</label></td>
							    <td>
								    <input type="text" id="dia-TARIFF_TYPE" name="dia-TARIFF_TYPE" datasource="TARIFF_TYPE" kind="dic" src="FPLX" datatype="0,is_null,30"/>
								</td>
								<td><label>所属月度：</label></td>
							    <td>
							    	<input type="text"  id="dia-SELECT_MONTH"  name="dia-SELECT_MONTH"   dataSource="SELECT_MONTH" style="width:75px;"   datatype="0,is_null,30" readonly="true" value="<%=yearStr1+"-"+monthStr1%>"/>
							    </td>
			                </tr>
			                <tr>
			                	<td><label>开户银行：</label></td>
			                    <td><input type="text" id="dia-OPEN_BANK" name="dia-OPEN_BANK" datasource="OPEN_BANK" datatype="0,is_null,3000" /></td>
			                	<td><label>银行帐号：</label></td>
			                    <td><input type="text" id="dia-BANK_ACCOUNT" name="dia-BANK_ACCOUNT" datasource="BANK_ACCOUNT" datatype="0,is_null,3000" /></td>
			                	<td><label>税号：</label></td>
			                    <td><input type="text" id="dia-TARIFF_NO" name="dia-TARIFF_NO" datasource="TARIFF_NO" datatype="0,is_null,3000" /></td>
			                </tr>
			                <tr>
			                	<td><label>地址：</label></td>
			                    <td><input type="text" id="dia-ADDRESS" name="dia-ADDRESS" datasource="ADDRESS" datatype="0,is_null,3000" /></td>
			                    <td><label>电话：</label></td>
			                    <td><input type="text" id="dia-TELEPHONE" name="dia-TELEPHONE" datasource="TELEPHONE" datatype="0,is_null,3000" /></td>
			                </tr>
			                <tr>
			                    <td><label>备注：</label></td>
			                    <td colspan="5">
			                      <textarea id="dia-REMARK" style="width:450px;height:40px;" name="dia-REMARK" datasource="REMARKS" datatype="1,is_null,1000"></textarea>
			                    </td>
			                </tr>
						</table>
			  </fieldset>
			</div>
			</form>
                <div class="formBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-save">保&nbsp;&nbsp;存</button></div></div></li>
                        <li>
                            <div class="buttonActive">
                                <div class="buttonContent">
                                    <button type="button" name="btn-next">下&nbsp;一&nbsp;步</button>
                                </div>
                            </div>
                        </li>
                        <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
                    </ul>
                </div>
            </div>
            <!--基本信息Tab end-->
            <!--促销备件Tab begin-->
            <div class="page">
                <div class="pageContent" >
                <form method="post" id="fm-searchPart" class="editForm">
                        <table class="searchContent" id="tab-searchPart"></table>
                    </form>
                    <div id="dia-part" style="">
                        <table style="display:none;width:100%;" id="dia-tab-partinfo" name="tablist" ref="dia-part" refQuery="tab-searchPart" edit="true">
                            <thead>
                           <tr>
                                <th type="single" name="XH" style="display:" append="plus|addParts"></th>
                                <th fieldname="PRO_NAME" colwidth="100" ftype="input" fdatatype="0,is_null,30" >材料名称</th>
                                <th fieldname="PRO_COUNT" colwidth="100" ftype="input" fdatatype="0,is_null,30">数量</th>
                                <th fieldname="PRO_UNIT" colwidth="50" ftype="input" fdatatype="0,is_null,30">单位</th>
                                <th fieldname="IN_INVOICE_PRICE" colwidth="100" ftype="input" fdatatype="0,is_null,30">含税单价</th>
                                <th fieldname="IN_INVOICE_AMOUNT" colwidth="100" ftype="input" fdatatype="0,is_null,30" >含税金额</th>
                                <th colwidth="105" type="link" title="[编辑]|[删除]"  action="doPartSave|doPartDelete">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="formBar">
                    <ul>
                        <li><div class="button"><div class="buttonContent"><button type="button" name="btn-pre">上&nbsp;一&nbsp;步</button></div></div></li>
                         <li id="dia-print"><div class="button"><div class="buttonContent"><button type="button" id="btn-printEntrust" >打&nbsp;&nbsp;印</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
                    </ul>
                </div>
            </div>
            <!--促销备件Tab end-->
        </div>
        <div class="tabsFooter">
            <div class="tabsFooterContent"></div>
        </div>
        <form method="post" id="fm_part" >
        <input type="hidden" id="P_ENTRUST_ID" datasource="ENTRUST_ID"/>
		<input type="hidden" id="P_DTL_ID" datasource="DTL_ID"/>
		<input type="hidden" id="P_PRO_NAME" datasource="PRO_NAME"/>
		<input type="hidden" id="P_PRO_COUNT" datasource="PRO_COUNT"/>
		<input type="hidden" id="P_PRO_UNIT" datasource="PRO_UNIT"/>
		<input type="hidden" id="P_IN_INVOICE_PRICE" datasource="IN_INVOICE_PRICE"/>
		<input type="hidden" id="P_IN_INVOICE_AMOUNT" datasource="IN_INVOICE_AMOUNT"/>
	</form>
    </div>
</div>

<script type="text/javascript">
    //注册上一步/下一步按钮响应
    (function ($) {
        $.contractMng = {
            /**
             * 初始化方法
             */
            init: function () {
                //设置页面默认值
                $tabs = $("div.tabs:first");
                var iH = document.documentElement.clientHeight;
                $(".tabsContent").height(iH - 70);
                $("button[name='btn-next']").bind("click", function () {
                    $.contractMng.doNextTab($tabs);
                });
                $("button[name='btn-pre']").bind("click", function () {
                    $.contractMng.doPreTab($tabs);
                });
            },
            doNextTab: function ($tabs) {
                $tabs.switchTab(parseInt($tabs.attr("currentIndex")) + 1);
                (function (ci) {
                    switch (parseInt(ci)) {
                        case 1://第2个tab页
                            if(!$('#dia-ENTRUST_ID').val()){
                                alertMsg.warn('请先保存委托单信息.');
                            }else{
                                if($("#dia-tab-partinfo").is(":hidden"))
                                {
                                    $("#dia-tab-partinfo").show();
                                    $("#dia-tab-partinfo").jTable();
                                }
                                searchPart();
                            }
                            break;
                        default:
                            break;
                    }
                })(parseInt($tabs.attr("currentIndex")));
            },
            doPreTab: function ($tabs) {
                $tabs.switchTab($tabs.attr("currentIndex") - 1);
            }
        };
    })(jQuery);
/* 取父页面传过的参数确定新增或者修改 */
    var diaAction = "<%=action%>";
    var diaUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/PartOrderEntrustAction";
    var flag = true;
    var sign = true;
    $(function () {
        var iH = document.documentElement.clientHeight;
        $(".tabsContent").height(iH - 140);
        $("button[name='btn-pre']").bind("click",function(event){
            $("#dia-tabs").switchTab(parseInt($("#dia-tabs").attr("currentIndex"))-1);
        });
        $("button[name='btn-next']").bind("click",function(event){
            var $tabs = $("#dia-tabs");
            $tabs.switchTab(parseInt($tabs.attr("currentIndex"))+1);
            //跳转后实现方法
            switch(parseInt($tabs.attr("currentIndex")))
            {
                case 1:
                    if (!$('#dia-ENTRUST_ID').val()) {
                        alertMsg.warn('请先保存委托单信息.')
                        $tabs.switchTab(parseInt($tabs.attr("currentIndex"))-1);
                    } else {
                        if(flag){
                            if ($("#dia-tab-partinfo").is(":hidden")) {
                                $("#dia-tab-partinfo").show();
                                $("#dia-tab-partinfo").jTable();
                            }
                            searchPart();
                        }
                        flag = false;
                    }
                    break;
                default:
                    break;
            }
        });
        //保存基本信息按钮响应
        $("#btn-save").bind("click", function(event){
			var $f = $("#dia-fm-contractEdit");
			if (submitForm($f) == false) return false;
			var sCondition = {};
			sCondition = $("#dia-fm-contractEdit").combined(1) || {};
			if(diaAction == 1)
			{
				var addUrl = diaUrl + "/entrustInsert1.ajax";
				doNormalSubmit($f,addUrl,"btn-save",sCondition,diaInsertCallBack);
			}else	
			{
				var updateUrl = diaUrl + "/entrustUpdate.ajax";
				doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
			}
		});
        $('#btn-printEntrust').click(function(){
        	//window.open(webApps + "/jsp/dms/oem/part/storage/stockIssueMng/printIssuePrint.jsp");
        	var ENTRUST_ID = $("#dia-ENTRUST_ID").val();
        	var queryUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/PartOrderEntrustAction/printPdf1.do?ENTRUST_ID="+ENTRUST_ID+"&flag=1";
            window.open(queryUrl);
        });
    	//关闭按钮响应
    	$("button.close").click(function(){
    		$.pdialog.closeCurrent();
    		return false;
    	});
		//修改页面赋值
        if (diaAction != "1") {
        	$("#dia-Print").show();
            var selectedRows = $("#orderList").getSelectedRows();
            setEditValue("dia-fm-contractEdit", selectedRows[0].attr("rowdata"));
        } 
    })
	//新增方法回调
    function diaInsertCallBack(res)
    {
    	var rows = res.getElementsByTagName("ROW");
    	try
    	{
    		var contractId = getNodeText(rows[0].getElementsByTagName("ENTRUST_ID").item(0));
            $('#dia-ENTRUST_ID').val(contractId);
            $("#dia-print").show();
    		$("#orderList").insertResult(res,0);
    		
    		diaAction = 2;
    		if($("#tab-contract_content").size()>0){
    			$("td input[type=radio]",$("#tab-contract_content").find("tr").eq(0)).attr("checked",true);			
    		}else{
    			$("td input[type=radio]",$("#tab-contract").find("tr").eq(0)).attr("checked",true);
    		}
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
    		var selectedIndex = $("#orderList").getSelectedIndex();
    		$("#orderList").updateResult(res,selectedIndex);
    	}catch(e)
    	{
    		alertMsg.error(e);
    		return false;
    	}
    	return true;
    }
    //查询采购合同配件
    function searchPart(){
    	var ENTRUST_ID = $("#dia-ENTRUST_ID").val();
		var searchContractPartUrl = diaUrl + "/proSearch.ajax?ENTRUST_ID="+ENTRUST_ID;
		var $f = $("#dia-fm-contractEdit");
		var sCondition = {};
	    sCondition = $f.combined() || {};
	    doFormSubmit($f, searchContractPartUrl, "", 1, sCondition, "dia-tab-partinfo");
    }
    //采购合同配件删除
    var $row;
    function doPartDelete(rowobj)
    {
    	$row = $(rowobj);
    	var id = $(rowobj).attr("DETAIL_ID");
    	if(id){
    		var url = diaUrl + "/contractPartDelete.ajax?DETAIL_ID="+$(rowobj).attr("DETAIL_ID");
        	sendPost(url, "", "", deleteCallBack, "true");
    	}else{
    		alertMsg.warn("请先维护项目信息！");
			return false;
    	}
    	
    }
    //删除配件回调方法
    function  deleteCallBack(res)
    {
    	try
    	{
    		if($row) 
    			$("#dia-tab-partinfo").removeResult($row);
    	}catch(e)
    	{
    		alertMsg.error(e);
    		return false;
    	}
    	return true;
    }
    function addParts()
    {
    	var $tab = $("#dia-tab-partinfo");
   		$tab.createRow();
    }
	
    /*行编辑保存提交后台方法  */
    function doPartSave(row){
		var ret = true;
		try
		{
			$("td input[type=radio]",$(row)).attr("checked",true);
			var $f = $("#fm_part");
			if (submitForm($(row)) == false) return false;
			var DETAIL_ID = $(row).attr("DTL_ID");
			var ENTRUST_ID = $("#dia-ENTRUST_ID").val();
			//设置隐藏域
			$("#P_DTL_ID").val(DETAIL_ID);
			$("#P_ENTRUST_ID").val(ENTRUST_ID);
			$("#P_PRO_NAME").val($(row).find("td").eq(2).find("input:first").val());
			$("#P_PRO_COUNT").val($(row).find("td").eq(3).find("input:first").val());
			$("#P_PRO_UNIT").val($(row).find("td").eq(4).find("input:first").val());
			$("#P_IN_INVOICE_PRICE").val($(row).find("td").eq(5).find("input:first").val());
			$("#P_IN_INVOICE_AMOUNT").val($(row).find("td").eq(6).find("input:first").val());
			
			if (submitForm($f) == false) return false;
			var sCondition = {};
			sCondition = $f.combined(1) || {};
	 		//需要将隐藏域或者列表只读域传给后台
			if(DETAIL_ID)
			{
				var url = diaUrl + "/partUpdate.ajax";
				sendPost(url,"",sCondition,diaPartSaveCallBack,"true");
			}else
			{
				var url = diaUrl + "/partInsert.ajax";
				sendPost(url,"",sCondition,diaPartSaveCallBack,"false");
			} 
		}catch(e){alertMsg.error(e);ret = false;}
		return ret;
	}
    //行编辑保存回调方法
    function diaPartSaveCallBack(res){
    	 var rows = res.getElementsByTagName("ROW");
         // 读取XML中的FLAG属性(FLAG:true有重复数据;)
         var t = getNodeText(rows[0].getElementsByTagName("FLAG").item(0));
         if(t == "true"){
        	 alertMsg.info("配件代码重复!");
        	 sign = false;
             return false;
         }else{
        	 var selectedIndex = $("#dia-tab-partinfo").getSelectedIndex();
         	$("#dia-tab-partinfo").updateResult(res,selectedIndex);
         	$("#dia-tab-partinfo").clearRowEdit($("#dia-tab-partinfo").getSelectedRows()[0],selectedIndex);
         	sign = true;
         	return true;
         }
    	

    }
    
	function deleteAllCallBack(res){
		searchPart();
	}
	
	var $row;
    function doPartDelete(rowobj)
    {
    	$row = $(rowobj);
    	var id = $(rowobj).attr("DTL_ID");
    	if(id){
    		var url = diaUrl + "/contractPartDelete.ajax?DTL_ID="+$(rowobj).attr("DTL_ID");
        	sendPost(url, "", "", deleteCallBack, "true");
    	}else{
    		alertMsg.warn("请先维护配件信息！");
			return false;
    	}
    	
    }
    //删除配件回调方法
    function  deleteCallBack(res)
    {
    	try
    	{
    		if($row) 
    			$("#dia-tab-partinfo").removeResult($row);
    	}catch(e)
    	{
    		alertMsg.error(e);
    		return false;
    	}
    	return true;
    }
</script>