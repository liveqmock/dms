<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>实销退件入库</title>
<script type="text/javascript">
    //变量定义
    //初始化方法
    var url = "<%=request.getContextPath()%>/part/storageMng/enterStorage/RealSaleInAction";
    var diaOptions = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
    $(function(){
        //查询方法
        $("#btn-search").bind("click",function(event){
            var $f =$("#fm-SaleOutSearch");
            var sCondition = {};
            sCondition = $f.combined() || {};
            var searchUrl = url+"/realSalesearch.ajax";
            doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-saleOut");
        });
    });

    //查看实销单详细信息
    function dorealSaleIn(rowobj) {
        $("td input[type=radio]",$(rowobj)).attr("checked",true);
        $.pdialog.open(webApps+"/jsp/dms/dealer/part/storage/instorage/realSaleIn.jsp", "orderCheckIn", "实销退件入库", diaOptions);
    }
</script>
</head>
<body>
    <div id="layout" style="width: 100%;">
        <div id='background1' class='background'></div>
        <div id='progressBar1' class='progressBar'>loading...</div>
        <h4 class="contentTitle" align="left">
            <img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：仓储管理 &gt; 入库管理 &gt; 实销退件入库
        </h4>
    <div class="page" >
    <div class="pageHeader" >
        <form method="post" id="fm-SaleOutSearch">
            <!-- 定义隐藏域条件 -->
            <input type="hidden" id="salestauts" name="salestauts" datasource="SALE_STATUS" datatype="1,is_digit_letter,30" operation="like" value="205002"/>
            <div class="searchBar" align="left" >
                <table class="searchContent" id="tab-salesSearch">
                    <!-- 定义查询条件 -->
                    <tr>
                        <td><label>实销单号：</label></td>
                        <td><input type="text" id="out-saleNo" name="out-saleNo" datasource="SALE_NO" datatype="1,is_digit_letter,30" operation="like" /></td>
                        <td><label>客户名称：</label></td>
                        <td><input type="text" id="out-custName" name="out-custName" datasource="CUSTOMER_NAME" datatype="1,is_digit_letter,30" operation="like" /></td>
                        <td><label>实销日期：</label></td>
                        <td >
                            <input type="text" group="in-kscjrq,in-jscjrq"  id="in-kscjrq"  name="in-kscjrq" operation=">="  dataSource="SALE_DATE" style="width:75px;"  kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
                            <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                            <input type="text" group="in-kscjrq,in-jscjrq"  id="in-jscjrq"  name="in-jscjrq" operation="<=" dataSource="SALE_DATE" style="width:75px;margin-left:-30px;" kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
                            </td>
                    </tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
                    </ul>
                </div>
            </div>
        </form>
    </div>
    <div class="pageContent">
        <div id="page_SaleOutlb" >
            <table style="display:none;width:100%;" id="tab-saleOut" name="tablist" ref="page_SaleOutlb" refQuery="fm-SaleOutSearch" >
                    <thead>
                        <tr>
                            <th fieldname="ROWNUMS" style="display:"></th>
                            <th type="single" name="XH" style="display:none;"></th>
                            <th fieldname="SALE_ID" style="display:none;">实销单号</th>
                            <th fieldname="REMARK" style="display:none;">实销单号</th>
                            <th fieldname="SALE_NO" ordertype='local' class="desc">实销单号</th>
                            <th fieldname="CUSTOMER_NAME">客户名称</th>
                            <th fieldname="LINK_PHONE">联系电话</th>
                            <th fieldname="LINK_ADDR">联系地址</th>
                            <th fieldname="SALE_STATUS">实销状态</th>
                            <th colwidth="150" type="link" title="[实销退件入库]"  action="dorealSaleIn" >操作</th>
                        </tr>
                    </thead>
            </table>
        </div>
    </div>
    </div>
    </div>
</body>
</html>