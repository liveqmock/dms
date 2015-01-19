<%@ page import="com.org.dms.common.DicConstant" %>
<%@ page import="com.org.framework.common.User" %>
<%@ page import="com.org.framework.Globals" %>
<%
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="/head.jsp"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
    <div>
        <div class="page">
            <div class="pageHeader">
                <form method="post" id="fm-searchPart">
                    <div class="searchBar" align="left">
                        <table class="searchContent" id="tab-searchPart">
                            <!-- 定义查询条件 -->
                            <tr>
                                <td><label>配件代码：</label></td>
                                <td><input type="text" id="dia-PART_CODE" name="dia-PART_CODE" datasource="PART_CODE" datatype="1,is_null,30" operation="like"/></td>
                                <td><label>配件名称：</label></td>
                                <td><input type="text" id="dia-PART_NAME" name="dia-PART_NAME" datasource="PART_NAME" datatype="1,is_null,30" operation="like"/></td>
                            </tr>
                        </table>
                        <div class="subBar">
                            <ul>
                                <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-searchPart">查&nbsp;&nbsp;询</button></div></div></li>
                                <li><div class="button"><div class="buttonContent"><button type="button" id="btn-confirmPart">确&nbsp;&nbsp;定</button></div></div></li>
                                <li><div class="button"><div class="buttonContent"><button type="button" id="btn-closePart">关&nbsp;&nbsp;闭</button></div></div></li>
                            </ul>
                        </div>
                    </div>
                </form>
            </div>
            <div class="pageContent">
                <div id="div-partList">
                    <table style="display:none;width:100%;" id="tab-partList" multivals="div-selectedPart" name="tablist" ref="div-partList"
                           refQuery="tab-searchPart">
                        <thead>
                            <tr>
                                <th type="multi" name="XH" unique="PART_ID" style=""></th>
                                <th fieldname="PART_CODE">配件代码</th>
                                <th fieldname="PART_NAME">配件名称</th>
                                <th fieldname="PART_NO" style="display: none">配件图号</th>
                                <th fieldname="UNIT">计量单位</th>
                                <th fieldname="MIN_PACK" refer="toAppendStr">最小包装</th>
                                <th fieldname="SALE_PRICE" refer="formatAmount" align="right">渠道商价</th>
                                <th fieldname="IF_SUPLY">是否指定供应商</th>
                                <th fieldname=""  refer="createInputBox">供应商</th>
                               </tr>
                        </thead>
                    </table>
                </div>
                <fieldset id="fie-selectedPart" style="display: none">
                    <legend align="left" >&nbsp;[已选定配件]</legend>
                    <div id="div-selectedPart">
                        <table style="width:100%;">
                            <tr>
                                <td>
                                    <textarea style="width:80%;height:26px;display: none" id="val0" column="1" name="multivals" readOnly></textarea>
                                    <textarea style="width:80%;height:26px;display: none" id="val1" name="multivals" readOnly></textarea>
                                    <textarea style="width:99%;height:50px;" id="val2" name="multivals" readOnly></textarea>
                                    <textarea style="width:99%;height:50px;display: none" id="val3" name="multivals" readOnly></textarea>
                                    <textarea style="width:99%;height:50px;display: none" id="val4" name="multivals" readOnly></textarea>
                                </td>
                            </tr>
                        </table>
                    </div>
                </fieldset>
                <form id="fm-partInfo">
                    <input type="hidden" id="inId" name="inId" datasource="IN_ID"/>
                    <input type="hidden" id="inNo" name="inNo" datasource="IN_NO"/>
                    <input type="hidden" id="partIds" name="partIds" datasource="PARTIDS"/>
                    <input type="hidden" id="partCodes" name="partCodes" datasource="PARTCODES"/>
                    <input type="hidden" id="partNames" name="partNames" datasource="PARTNAMES"/>
                    <input type="hidden" id="supplierCodes" name="supplierCodes" datasource="SUPPLIERCODES"/>
                </form>
            </div>
        </div>
    </div>
</body>
<script type="text/javascript">
    //弹出窗体
    var partSelWin = parent.$("body").data("partSelWin");
    $(function () {

        $("#tab-partList").attr("layoutH",document.documentElement.clientHeight-200);

        // 查询配件
        searchPart();
        $("#btn-closePart").click(function () {
            $.pdialog.close(partSelWin);
            return false;
        });
        $("#btn-searchPart").bind("click", function (event) {
            // 查询配件
            searchPart();
        });
        //确定按钮响应
        $('#btn-confirmPart').bind('click',function(){
            //添加促销配件URL
            var insertPromPartUrl = "<%=request.getContextPath()%>/part/storageMng/stockInMng/OtherStockInMngAction/insertInBillPart.ajax";
            var partIds = $('#val0').val();
            if(!partIds){
                alertMsg.warn('请选择配件!')
            }else{
                $('#inId').val(parent.$('#dia-IN_ID').val());//入库单ID
                $('#inNo').val(parent.$('#dia-IN_NO').val());//入库单号
                $('#partIds').val(partIds);
                $('#partCodes').val($('#val1').val());
                $('#partNames').val($('#val2').val());
                $('#supplierCodes').val($('#val3').val());

                //获取需要提交的form对象
                var $f = $("#fm-partInfo");
                //submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
                if (submitForm($f) == false) return false;
                var sCondition = {};
                //将需要提交的内容拼接成json
                sCondition = $f.combined(1) || {};
                doNormalSubmit($f, insertPromPartUrl, "btn-confirmPart", sCondition, insertInBillPartCallBack);
            }
        });
    });

    // 查询配件
    function searchPart() {
        //查询配件URL
        var searchPartUrl = "<%=request.getContextPath()%>/part/storageMng/stockInMng/OtherStockInMngAction/searchPart.ajax?inId="+parent.$('#dia-IN_ID').val()+"&warehouseType="+parent.$('#dia-WAREHOUSE_TYPE').val();
        var $f = $("#fm-searchPart");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, searchPartUrl, "btn-search", 1, sCondition, "tab-partList");
        $('#fie-selectedPart').show();
    }

    //新增入库单配件回调
    function insertInBillPartCallBack(){
        //查询入库单配件
        parent.searchInBillPart();
        $.pdialog.close(partSelWin);
    }

    function formatAmount(obj){
        return amountFormatNew($(obj).html());
    }

    //将供应商字段渲染为文本框
    function createInputBox(obj)
    {
        var $obj = $(obj);
        var $tr = $obj.parent();
        var partId = $tr.attr('PART_ID');
        if('<%=DicConstant.SF_01%>' == $tr.attr('IF_SUPLY')){
            var dicSql = 'T#PT_BA_SUPPLIER A,PT_BA_PART_SUPPLIER_RL B:A.SUPPLIER_CODE:A.SUPPLIER_NAME:1=1';
            dicSql+=' AND A.SUPPLIER_ID = B.SUPPLIER_ID';
            dicSql+=' AND B.PART_ID = '+partId;
            dicSql+=' AND A.PART_IDENTIFY=<%=DicConstant.YXBS_01 %> AND B.PART_IDENTIFY=<%=DicConstant.YXBS_01 %>';
            dicSql+=' AND A.OEM_COMPANY_ID=<%=user.getOemCompanyId()%>';
            return '<input type="text" id=SUPPLIER_"'+partId+'" kind="dic" src="'+dicSql+'"/>';
        }else{
            return "";
        }
    }

    function doCheckbox(checkbox) {
        var $tr = $(checkbox).parent().parent().parent();

        var ifSuply = $tr.attr('IF_SUPLY');
        var supplierCode = "";
        var supplierName = "";
        if('<%=DicConstant.SF_01%>'==ifSuply){
            supplierCode = $tr.find("td").eq(9).find("input:first").attr('code');
            supplierName = $tr.find("td").eq(9).find("input:first").val();
        }else{
            supplierCode= '9XXXXXX';
            supplierName= '待定供应商';
        }
        if(!supplierCode){
            alertMsg.warn('请选择供应商!');
            $(checkbox).attr("checked",false);
            return;
        }

        var arr = [];
        arr.push($tr.attr("PART_ID"));
        arr.push($tr.attr("PART_CODE"));
        arr.push($tr.attr("PART_NAME"));
        arr.push(supplierCode);
        arr.push(supplierName);

        var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
        multiSelected($checkbox, arr,$('#div-selectedPart'));

        //设置input框显示或文本只读
        if($checkbox.is(":checked")){
            if('<%=DicConstant.SF_01%>'==ifSuply) {
                $tr.find("td").eq(9).find("input:first").attr('disabled', true);
            }
        }
        else
        {
            if('<%=DicConstant.SF_01%>'==ifSuply) {
                $tr.find("td").eq(9).find("input:first").attr('disabled', false);
            }
        }
    }

    function callbackSearch(responseText,tabId,sParam)
    {
        switch(tabId)
        {
            case "tab-partList":
                setStyle('tab-partList_content');
                break;
        }
    }


    function customOtherValue($row,checkVal)
    {

        var $inputObj = $row.find("td").eq(9);//供应商
        var supplierCode;
        var supplierName;
        if($("#val3") && $("#val3").val())
        {
            var supplierCodes = $("#val3").val();
            var supplierNames = $("#val4").val();
            var pks = $("#val0").val();
            var supplierCodeArr = supplierCodes.split(",");
            var supplierNameArr = supplierNames.split(",");
            var pk = pks.split(",");
            for(var i=0;i<pk.length;i++)
            {
                if(pk[i] == checkVal)
                {
                    supplierCode = supplierCodeArr[i];
                    supplierName = supplierNameArr[i];
                    break;
                }
            }
        }
        if(supplierCode!='9XXXXXX')
        {
            var supplierInput = $inputObj.find('input').eq(0);
            supplierInput.attr('code',supplierCode);
            supplierInput.attr('value',supplierName);
            supplierInput.attr('disabled',true);
        }
    }
    function toAppendStr(obj){
        var $tr =$(obj).parent();
        return $tr.attr("MIN_PACK")+"/"+$tr.attr("MIN_UNIT_sv");
    }
</script>
</html>