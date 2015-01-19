<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.org.dms.common.DicConstant" %>
<%
	String VEHICLE_ID=request.getParameter("VEHICLE_ID");
%>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div>
    <div class="page">
        <div class="pageHeader">
            <form method="post" id="fm-searchBox">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="tab-searchBox">
                        <tr>
                            <td><label>订单编号：</label></td>
                            <td><input type="text" id="dia-ORDER_NO" name="dia-ORDER_NO" datasource="X.ORDER_NO" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>发运单号：</label></td>
                            <td><input type="text" id="dia-SHIP_NO" name="dia-SHIP_NO" datasource="X.SHIP_NO" datatype="1,is_null,30" operation="like"/></td>
                        </tr>
                        <tr>
                        	<td><label>箱号：</label></td>
					    	<td><input type="text" id="dia-BOX_NO" name="dia-BOX_NO" datasource="X.BOX_NO" kind="dic" src="" datatype="1,is_null,30" operation="like"/></td>
                        </tr>
                    </table>
                    <div class="subBar">
                        <ul>
                            <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-searchBox">查&nbsp;&nbsp;询</button></div></div></li>
                            <li id="dia-download" ><div class="button"><div class="buttonContent"><button type="button" id="btn-download" >下载导入模板</button></div></div></li>
                        	<li id="dia-import" ><div class="button"><div class="buttonContent"><button type="button" id="btn-import" >批量导入</button></div></div></li>
                            <li><div class="button"><div class="buttonContent"><button type="button" id="btn-confirmPart">确&nbsp;&nbsp;定</button></div></div></li>
                            <li><div class="button"><div class="buttonContent"><button type="button" id="btn-closePart">关&nbsp;&nbsp;闭</button></div></div></li>
                        </ul>
                    </div>
                </div>
            </form>
        </div>
        <div class="pageContent">
            <div id="div-partList">
                <table style="display:none;width:100%;" id="tab-partList" multivals="div-selectedPart" name="tablist" ref="div-partList" refQuery="tab-searchBox">
                    <thead>
                        <tr>
                            <th type="multi" name="XH" unique="T_KEY" style=""></th>
                            <th fieldname="ORDER_NO">订单编号</th>
                            <th fieldname="SHIP_NO">发运单号</th>
                            <th fieldname="BOX_NO" >箱号</th>
                            <th fieldname="PART_CODE" >配件代码</th>
                            <th fieldname="PART_NAME" >配件名称</th>
                            <th fieldname="COUNT" >数量</th>
                           </tr>
                    </thead>
                </table>
            </div>
            <fieldset id="fie-selectedPart" style="display:none">
                <div id="div-selectedPart">
                    <table style="width:100%;">
                        <tr>
                            <td>
                                <textarea style="width:80%;height:10px;display:none" id="val0" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;height:10px;display:none" id="val1" name="multivals" readOnly></textarea>
                                <textarea style="width:99%;height:30px;display:none" id="val2" name="multivals" readOnly></textarea>
                            </td>
                        </tr>
                    </table>
                </div>
            </fieldset>
            <form id="fm-partInfo" method="post">
            	<input type="hidden" id="veicleId" name="veicleId" datasource="VEHICLE_ID"/>
                <input type="hidden" id="orderIds" name="orderIds" datasource="ORDERIDS"/>
                <input type="hidden" id="shipIds" name="shipIds" datasource="SHIPIDS"/>
                <input type="hidden" id="boxNos" name="boxNos" datasource="BOXNOS"/>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    var addOrderPartSel = $("body").data("addOrderPartSel");
    var VEHICLE_ID = <%=VEHICLE_ID%>;
    var SHIP_ID = $('#dia-SHIP_ID').val();
    var url = "<%=request.getContextPath()%>/part/storageMng/shipMng/ShipOutAction";
    var iH = document.documentElement.clientHeight;
    $(function () {
    	$('#btn-download').bind('click', function () {
            var url = encodeURI("<%=request.getContextPath()%>/jsp/dms/common/download.jsp?filename=VelBoxRl.xls");
            window.location.href = url;
        });
    	$('#btn-import').bind('click',function(){
            //13:表示最大列，3：表示有效数据是第几行开始，第一行是1
            //最后一个参数表示 导入成功后显示页
            var id = VEHICLE_ID+","+SHIP_ID;
            importXls("PT_BU_VEL_BOX_RL_TMP",id,2,3,"/jsp/dms/oem/part/storage/shipMng/importSuccess.jsp");
        });
    	$("#tab-partList").height(iH - 140);
    	$("#dia-BOX_NO").attr("src","T#PT_BU_BOX_UP T,PT_BU_ORDER_SHIP_DTL T1:DISTINCT T.BOX_NO:T.BOX_NO{T.BOX_NO}:1=1 AND T.ORDER_ID = T1.ORDER_ID AND T1.SHIP_ID= "+SHIP_ID+"");
    	$("#btn-closePart").click(function () {
            $.pdialog.close(addOrderPartSel);
            return false;
        });
        $("#btn-searchBox").bind("click", function (event) {
            //查询配件URL
            var searchPartUrl = url+"/searchBoxNo.ajax?VEHICLE_ID="+VEHICLE_ID+"&SHIP_ID="+SHIP_ID;
            var $f = $("#fm-searchBox");
            var sCondition = {};
            sCondition = $f.combined() || {};
            doFormSubmit($f, searchPartUrl, "btn-searchBox", 1, sCondition, "tab-partList");
            $('#fie-selectedPart').show();
        });
        $('#btn-confirmPart').bind('click',function(){
            //添加促销配件URL
            var insertPromPartUrl = url+"/insertRl.ajax";
            var boxNos = $('#val0').val();
            if(!boxNos){
                alertMsg.warn('请选择包装箱!')
            }else{
                $('#veicleId').val(VEHICLE_ID);
                $('#boxNos').val(boxNos);
                $('#shipIds').val($('#val1').val());
                $('#orderIds').val($('#val2').val());
                var $f = $("#fm-partInfo");
                if (submitForm($f) == false) return false;
                var sCondition = {};
                sCondition = $f.combined(1) || {};
                doNormalSubmit($f, insertPromPartUrl, "btn-confirmPart", sCondition, insertPromPartCallBack); 
            }
        });
    });
      function doCheckbox(checkbox) {
        var $tr = $(checkbox).parent().parent().parent();
        var arr = [];
        arr.push($tr.attr("BOX_NO"));
        arr.push($tr.attr("SHIP_ID"));
        arr.push($tr.attr("ORDER_ID"));
        var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
        multiSelected($checkbox, arr,$('#div-selectedPart'));
    }
    function insertPromPartCallBack(){
        $.pdialog.close(addOrderPartSel);
    }
    
    

</script>