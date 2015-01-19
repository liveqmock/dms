<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>总成附件确认</title>
<script type="text/javascript">
    //变量定义
    //初始化方法
    $(function(){

        //查询方法
        $("#btn-search-index").bind("click",function(event){
            // 查询方法
            searchDirectBusinessOrder();
        });
    });

    // 金额formcat
    function formatAmount(obj){
        return "<div style='text-align:right;'>"+amountFormatNew($(obj).html())+"</div>";
    }

    // 查询总成附件确认
    function searchDirectBusinessOrder() {
        var url = "<%=request.getContextPath()%>/part/salesMng/assemblyMng/AssemblyMngAction/searchAssemblyCheck.ajax";
        var $f = $("#fm-index");
        var sCondition = {};
        if (submitForm($f) == false) {
            return false;
        }
        sCondition = $f.combined() || {};
        doFormSubmit($f,url , "btn-search-index", 1, sCondition, "tab-index");
    }

    // 列表编辑链接
    function doUpdate(row) {
        $("td input:first",$(row)).attr("checked",true);
        var options = {max:true,mask:true,width:300,height:150,mixable:true,minable:true,resizable:true,drawable:true};
        $.pdialog.open(webApps + "/jsp/dms/oem/part/sales/assemblyMng/assemblyCheckEdit1.jsp", "directBusinessOrderCheckEdit", "总成附件确认明细", options);
    }
    function doClose(rowobj)
    {
    	$row = $(rowobj);
  		var url = "<%=request.getContextPath()%>/part/salesMng/assemblyMng/AssemblyMngAction/closeAss.ajax?assemblyId="+$(rowobj).attr("ASSEMBLY_ID");
  		sendPost(url,"report","",reportPurchaseCallBack,"true");
    	
    }
    function  reportPurchaseCallBack(res)
    {
    	try
    	{
    		searchDirectBusinessOrder();
    	}catch(e)
    	{
    		alertMsg.error(e);
    		return false;
    	}
    	return true;
    }
</script>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 销售管理  &gt; 总成附件确认管理   &gt; 总成附件确认</h4>
    <div class="page" >
    <div class="pageHeader" >
        <form method="post" id="fm-index">
            <!-- 定义隐藏域条件 -->
            <div class="searchBar" align="left" >
                <table class="searchContent" id="tab-ddcx">
                    <!-- 定义查询条件 -->
                    <tr>
                        <td><label>渠道商：</label></td>
                        <td><input type="text" id="orgCode" name="orgCode" datasource="ORG_ID" datatype="1,is_null,10000" readOnly hasBtn="true" callFunction="showOrgTree('orgCode',1,2)" operation="IN"/></td>
                        <td><label>提报日期：</label></td>
                        <td>
                            <input type="text" group="in-kscjrq,in-jscjrq"  id="in-kscjrq"  name="in-kscjrq" operation=">="  dataSource="APPLY_TIME" style="width:75px;"  kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
                            <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                            <input type="text" group="in-kscjrq,in-jscjrq"  id="in-jscjrq"  name="in-jscjrq" operation="<=" dataSource="APPLY_TIME" style="width:75px;margin-left:-30px;" kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
                        </td>
                    </tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search-index" >查&nbsp;&nbsp;询</button></div></div></li>
                    </ul>
                </div>
            </div>
        </form>
    </div>
    <div class="pageContent">
        <div id="page_index" >
            <table style="display:none;width:100%;" id="tab-index" name="tablist" ref="page_index" refQuery="fm-pjcx" >
                    <thead>
                        <tr>
                            <th type="single" name="XH" style="display:none;"></th>
                            <th fieldname="ORG_CODE" >渠道代码</th>
                            <th fieldname="ORG_NAME" >渠道名称</th>
                            <th fieldname="APPLY_TIME" colwidth="130">申请日期</th>
                            <th colwidth="90" type="link" title="[审核]|[关闭]"  action="doUpdate|doClose" >操作</th>
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