<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.org.framework.Globals" %>
<%@ page import="com.org.framework.common.User" %>
<%@ page import="com.org.dms.common.DicConstant" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
    String contentPath = request.getContextPath();
    String action = request.getParameter("action");
    if (action == null)
        action = "1";
%>
<div>
    <div class="page">
        <div class="pageHeader">
            <form method="post" id="fm-searchUnChkPart">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="tab-searchPart">
                        <!-- 定义查询条件 -->
                        <tr>
                            <td><label>配件代码：</label></td>
                            <td><input type="text" id="dia-PART_CODE" name="dia-PART_CODE" datasource="T1.PART_CODE"
                                       datatype="1,is_null,30" operation="like"/></td>
                            <td><label>配件名称：</label></td>
                            <td><input type="text" id="dia-PART_NAME" name="dia-PART_NAME" datasource="T1.PART_NAME"
                                       datatype="1,is_null,30" operation="like"/></td>
                        </tr>
                    </table>
                    <div class="subBar">
                        <ul>
                            <li>
                                <div class="buttonActive">
                                    <div class="buttonContent">
                                        <button type="button" id="btn-searchPart">查&nbsp;&nbsp;询</button>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="button">
                                    <div class="buttonContent">
                                        <button type="button" id="btn-confirmPart">确&nbsp;&nbsp;定</button>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="button">
                                    <div class="buttonContent">
                                        <button type="button" id="btn-closePart">关&nbsp;&nbsp;闭</button>
                                    </div>
                                </div>
                            </li>
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
                            <th fieldname="UNIT">单位</th>
                            <th fieldname="MIN_PACK" colwidth="80" refer="toAppendStr">最小包装</th>
                            <th fieldname="PCH_PRICE" colwidth="80" refer="amountFormatStr" align="right">采购价格</th>
                            <th fieldname="PLAN_PRICE" colwidth="80" refer="amountFormatStr" align="right">计划价格</th>
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
                                <textarea style="width:80%;height:26px;display: none" id="val3" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;height:26px;display: none" id="val4" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;height:26px;display: none" id="val5" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;height:26px;display: none" id="val6" name="multivals" readOnly></textarea>
                            </td>
                        </tr>
                    </table>
                </div>
            </fieldset>
            <form id="fm-dia-partInfo">
                <input type="hidden" id="dia-returnid" name="returnid" datasource="RETURNID"/>
                <input type="hidden" id="dia-supplierid" name="supplierid" datasource="SUPPLIERID"/>
                <input type="hidden" id="dia-partIds" name="partIds" datasource="PARTIDS"/>
                <input type="hidden" id="dia-partCodes" name="partCodes" datasource="PARTCODES"/>
                <input type="hidden" id="dia-partNames" name="partNames" datasource="PARTNAMES"/>
                <input type="hidden" id="dia-pchPrices" name="pchPrices" datasource="PCH_PRICE"/>
                <input type="hidden" id="dia-planPrices" name="planPrices" datasource="PLAN_PRICE"/>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    //弹出窗体
    var partSelWin = $("body").data("partSelWin");
    var url = "<%=request.getContextPath()%>/part/purchaseMng/purchaseReturn/PurchaseOrderReturnMngAction";
    $(function () {
    	
    	 var searchPartUrl = url+"/searchPart.ajax?RETURN_ID="+$("#dia-RETURN_ID").val()+"&SUPPLIER_ID="+$("#dia-SUPPLIER_ID").val();
         var $f = $("#fm-searchUnChkPart");
         var sCondition = {};
         sCondition = $f.combined() || {};
         doFormSubmit($f, searchPartUrl, "btn-search", 1, sCondition, "tab-partList");
         $('#fie-selectedPart').show();
       
         $("#btn-closePart").click(function () {
            $.pdialog.close(partSelWin);
            return false;
        });
        $("#btn-searchPart").bind("click", function (event) {
            //查询配件URL
            var searchPartUrl = url+"/searchPart.ajax?RETURN_ID="+$("#dia-RETURN_ID").val()+"&SUPPLIER_ID="+$("#dia-SUPPLIER_ID").val();
            var $f = $("#fm-searchUnChkPart");
            var sCondition = {};
            sCondition = $f.combined() || {};
            doFormSubmit($f, searchPartUrl, "btn-search", 1, sCondition, "tab-partList");
            $('#fie-selectedPart').show();
        });
      //确定按钮响应
        $('#btn-confirmPart').bind('click',function(){
            //添加促销配件URL
            var insertPromPartUrl = url+"/insertOrderPart.ajax";
            var partIds = $('#val0').val();
            if(!partIds){
                alertMsg.warn('请选择配件!');
            }else{
            	$('#dia-returnid').val($('#dia-RETURN_ID').val());
            	$('#dia-supplierid').val($('#val5').val());
                $('#dia-partIds').val(partIds);
                $('#dia-partNames').val($('#val1').val());
                $('#dia-partCodes').val($('#val2').val());
                $('#dia-pchPrices').val($('#val3').val());
                $('#dia-planPrices').val($('#val4').val());
                var $f = $("#fm-dia-partInfo");
                if (submitForm($f) == false) return false;
                var sCondition = {};
                sCondition = $f.combined(1) || {};
                doNormalSubmit($f, insertPromPartUrl, "btn-confirmPart", sCondition, insertPromPartCallBack); 
            }
        });
    });
    function amountFormatStr(obj){
		return "<div style='text-align:right;'>"+amountFormatNew($(obj).html())+"</div>";
	}
    function toAppendStr(obj){
        var $tr =$(obj).parent();
        return $tr.attr("MIN_PACK")+"/"+$tr.attr("MIN_UNIT_sv");
    }
    function doCheckbox(checkbox) {
    	var $checkbox = $(checkbox);
    	var $tr = $(checkbox).parent().parent().parent();
        var arr = [];
        arr.push($tr.attr("PART_ID"));
        arr.push($tr.attr("PART_NAME"));
        arr.push($tr.attr("PART_CODE"));
        arr.push($tr.attr("PCH_PRICE"));
        arr.push($tr.attr("PLAN_PRICE"));
        arr.push($tr.attr("SUPPLIER_ID"));
        var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
        multiSelected($checkbox, arr,$('#div-selectedPart'));
    }
    function isNum($obj)
    {
        var reg = /^[1-9]\d*$/
        if(reg.test($obj.val()))
        {
            return true;
        }else
        {
            return false;
        }
    }
    function insertPromPartCallBack(){
        $.pdialog.close(partSelWin);
        searchPart();
    }
</script>