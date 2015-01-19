<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<%@ page import="com.org.dms.common.DicConstant" %>
<title>现金账户管理</title>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
    var diaAddOptions = {max:false,width:800,height:430,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
    var mngUrl = "<%=request.getContextPath()%>/part/financeMng/cashAccountMng/CashAccountMngAction";
    $(function() {
    	// 查询按钮绑定
        $("#btn-search").bind("click", function(event){
            doSearch();
        });
        $("#btn-search").trigger("click");

        // 导出模板按钮绑定
        $("#doExp").click(function() {
            var url = encodeURI("<%=request.getContextPath()%>/jsp/dms/common/download.jsp?filename=pjzhjedrmb.xls");
            window.location.href = url;
        });

        // 材料费批量导入按钮绑定
        $("#doMaterialsImp").bind("click", function () {
            //9:表示最大列，3：表示有效数据是第几行开始，第一行是1
            //最后一个参数表示 导入成功后显示页
            importXls('PT_BU_ACCOUNT_TMP',<%=DicConstant.ZJZHLX_03%>,3,3,"/jsp/dms/oem/part/finance/importSuccess.jsp");
        });

        // 返利批量导入按钮绑定
        $("#doRebateImp").bind("click", function () {
            //9:表示最大列，3：表示有效数据是第几行开始，第一行是1
            //最后一个参数表示 导入成功后显示页
            importXls('PT_BU_ACCOUNT_TMP',<%=DicConstant.ZJZHLX_05%>,3,3,"/jsp/dms/oem/part/finance/importSuccess.jsp");
        });

        // 重置按钮绑定
        $("#btn-reset").bind("click", function(event){
                $("#fm-searchCash")[0].reset();
                $("#orgCode").attr("code","");
                $("#orgCode").val("");
        });
    });
    function doSearch(){
        var searchUrl = mngUrl+"/cashSearch.ajax";
        var $f = $("#fm-searchCash");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-cash_info");
    }
    function doUpdate1(rowobj)
    {
        $("td input[type=radio]",$(rowobj)).attr("checked",true);
        $.pdialog.open(webApps+"/jsp/dms/oem/part/finance/cashAccountEdit.jsp?action=1", "回款管理", "回款管理", diaAddOptions);
    }
    function doUpdate2(rowobj)
    {
        $("td input[type=radio]",$(rowobj)).attr("checked",true);
        $.pdialog.open(webApps+"/jsp/dms/oem/part/finance/cashAccountEdit.jsp?action=2", "欠款管理", "欠款管理", diaAddOptions);
    }
    function showLink1(obj)
    {
        var $row=$(obj).parent();
        return "<a href='#' onclick=openDetail("+$row.attr("CASH_ACCOUNT_ID")+") class='op'>"+amountFormatNew($(obj).html())+"</a>";
    }
    function showLink2(obj)
    {
        var $row=$(obj).parent();
        return "<a href='#' onclick=openDetail("+$row.attr("ACCEPT_ACCOUNT_ID")+") class='op'>"+amountFormatNew($(obj).html())+"</a>";
    }
    function showLink3(obj)
    {
        var $row=$(obj).parent();
        return "<a href='#' onclick=openDetail("+$row.attr("MATERIAL_ACCOUNT_ID")+") class='op'>"+amountFormatNew($(obj).html())+"</a>";
    }
    function openDetail(ACCOUNT_ID){
        var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
        $.pdialog.open(webApps+"/jsp/dms/oem/part/finance/cashOccupationDetail.jsp?ACCOUNT_ID="+ACCOUNT_ID, "cashDetail", "资金占用明细", options,true);
    }
    function formatAmount(obj){
        return "<div style='text-align:right;'>"+amountFormatNew($(obj).html())+"</div>";
    }
    function afterDicItemClick(id,$row){
    	var ret = true;
    	if(id == "ORG_CODE")
    	{
    		$("#ORG_CODE").val($row.attr("CODE"));
    		$("#ORG_NAME").val($row.attr("ONAME"));
    		$("#ORG_CODE").attr("code",$row.attr("CODE"));
    	}
    	return ret;
    }
</script>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 财务管理  &gt; 现金管理  &gt; 余额管理</h4>
    <div class="page" >
    <div class="pageHeader" >
        <form method="post" id="fm-searchCash">
            <!-- 定义隐藏域条件 -->
            <div class="searchBar" align="left" >
                <table class="searchContent" id="tab-cashSeach">
                    <tr>
                        <td><label>渠道代码：</label></td>
                        <td>
                            <input type="text" id="ORG_CODE" name="ORG_CODE" datasource="A.ORG_CODE" datatype="1,is_null,300000" kind="dic" src="T#TM_ORG:CODE:ONAME{ORG_ID,CODE,ONAME}:1=1 AND ORG_TYPE = 200005 AND STATUS='100201' ORDER BY CODE" readonly="true" operation="in"/>
                        </td>
                        <td><label>渠道名称：</label></td>
                        <td>
                            <input type="text" id="ORG_NAME" name="ORG_NAME" datasource="A.ORG_NAME"  operation="like"/>
                        </td>
                    </tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button type="button" id="doExp">导出模版</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button type="button" id="doMaterialsImp">材料费导入</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button type="button" id="doRebateImp">返利导入</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button type="button" id="btn-reset">重&nbsp;&nbsp;置</button></div></div></li>
                    </ul>
                </div>
            </div>
        </form>
    </div>
    <div class="pageContent">
        <div id="page_cash" >
            <table style="display:none;width:100%;" id="tab-cash_info" name="tablist" ref="page_cash" refQuery="fm-searchCash" >
                    <thead>
                    <tr>
                        <th type="single" name="XH" style="display:none;"></th>
                        <th fieldname="ORG_CODE">渠道代码</th>
                        <th fieldname="ORG_NAME">渠道名称</th>
                        <th align="right" fieldname="CASH_BALANCE_AMOUNT"  refer="formatAmount" align="right" style="display:none;">现金余额</th>
                        <th align="right" fieldname="CASH_OCCUPY_AMOUNT"  refer="showLink1"  align="right" style="display:none;">现金占用</th>
                        <th align="right" fieldname="CASH_AVAILABLE_AMOUNT"  refer="formatAmount" align="right" style="display:none;">现金可用</th>
                        <th align="right" fieldname="ACCEPT_BALANCE_AMOUNT"  refer="formatAmount" align="right" style="display:none;">承兑余额</th>
                        <th align="right" fieldname="ACCEPT_OCCUPY_AMOUNT"  refer="showLink2" align="right" style="display:none;">承兑占用</th>
                        <th align="right" fieldname="ACCEPT_AVAILABLE_AMOUNT"  refer="formatAmount" align="right" style="display:none;">承兑可用</th>
                        <th align="right" fieldname="MATERIAL_BALANCE_AMOUNT"  refer="formatAmount" align="right" style="display:none;">材料费余额</th>
                        <th align="right" fieldname="MATERIAL_OCCUPY_AMOUNT"  refer="showLink3" align="right" style="display:none;">材料费占用</th>
                        <th align="right" fieldname="MATERIAL_AVAILABLE_AMOUNT"  refer="formatAmount" align="right" style="display:none;">材料费可用</th>
                        <th rowspan="1" colwidth="85" type="link" title="[回款]|[欠款]"  action="doUpdate1|doUpdate2" style="text-align:center;vertical-align:middle;">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
            </table>
        </div>
    </div>
    </div>
</div>
</body>
</html>