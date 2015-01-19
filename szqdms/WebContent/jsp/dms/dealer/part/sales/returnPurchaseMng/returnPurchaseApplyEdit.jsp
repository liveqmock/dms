<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.framework.params.ParaManager"%>
<%@page import="com.org.framework.params.UserPara.UserParaConfigureVO"%>
<%@page import="com.org.dms.common.DicConstant"%>
<%@page import="com.org.framework.Globals"%>
<%@page import="com.org.framework.common.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
    String orgType = user.getOrgDept().getOrgType();
    String contentPath = request.getContextPath();
    UserParaConfigureVO userPara = (UserParaConfigureVO) ParaManager.getInstance().getUserParameter("100200");
    String paraValue1 = userPara.getParavalue1();
    String paraValue2 = userPara.getParavalue2();
    String paraValue3 = userPara.getParavalue3();
    String action = request.getParameter("action");
    if(action == null)
        action = "1";
%>
<div id="dia-layout">
    <div class="page">
        <div class="pageContent">
            <form method="post" id="dia-fm-edit" class="editForm" style="width:99%;">
                <input type="hidden" id="dia-sourceOrderId-edit" readonly="readonly" datasource="SOURCE_ORDER_ID"/>
                <div align="left">
                    <fieldset>
                        <table class="editTable">
                            <tr>
                                <td><label>退件单号：</label></td>
                                <td><input type="text" id="dia-returnNo-edit" name="dia-returnNo-edit" value="系统自动生成" datasource="RETURN_NO" readonly="readonly"/></td>
                                <td><label>接收方：</label></td>
                                <td>
                                    <input type="text" id="dia-code-edit" name="dia-code-edit" datasource="RECEIVE_ORG_CODE" datatype="0,is_null,300" operation="like" style="width:280px;" dicwidth="380" isreload="true" src="" kind="dic"/>
                                    <input type="hidden" id="dia-orgId-edit" datasource="RECEIVE_ORG_ID"/>
                                    <input type="hidden" id="dia-oName-edit" datasource="RECEIVE_ORG_NAME"/>
                                </td>
<!--                                 <td><label>原订单号：</label></td> -->
<!--                                 <td><input type="text" id="dia-sourceOrderNo-edit" readonly="readonly" datasource="SOURCE_ORDER_NO" datatype="0,is_null,30"  hasBtn="true"  callFunction="openSaleOrder()"/> -->
<!--                                 </td> -->
                            </tr>
                            <tr>
                                <td><label>备注：</label></td>
                                <td colspan="5"><textarea id="dia-remarks-edit" name="dia-remarks-edit" datatype="0,is_null,100" datasource="REMARKS" style="width:450px;height:40px;"></textarea></td>
                            </tr>
                        </table>
                    </fieldset>
                </div>
            </form>
            <div id="formBar" class="formBar">
               <ul>
                   <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-returnPurchase-edit">新增退件</button></div></div></li>
                   <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-save-edit">保&nbsp;&nbsp;存</button></div></div></li>
                   <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-report-edit">提&nbsp;&nbsp;报</button></div></div></li>
                   <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-download-edit">下载模板</button></div></div></li>
                   <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-import-edit">批量导入</button></div></div></li>
                   <li><div class="button"><div class="buttonContent"><button type="button" class="close">关&nbsp;&nbsp;闭</button></div></div></li>
               </ul>
            </div>
            <form id="exportFm" method="post" style="display:none">
                <input type="hidden" id="params" name="data"></input>
            </form>
            <form  method="post" id="dia-fm-edit-oderId">
                <input type="hidden" id="dia-returnId-edit" name="dia-returnId-edit" datasource="A.RETURN_ID"/>
            </form>
            <div id="dia-div-edit">
                <table style="display:none;width:95%;" id="dia-tab-edit" name="tablist" ref="dia-div-edit">
                    <thead>
                        <tr>
                            <th type="multi" name="XH" unique="DTL_ID" style="display:none;"></th>
                            <th fieldname="PART_CODE">配件代码</th>
                            <th fieldname="PART_NAME">配件名称</th>
                            <th fieldname="SUPPLIER_NAME">供应商</th>
                            <th fieldname="UNIT" colwidth="80">计量单位</th>
                            <th fieldname="" refer="appendPack_Unit">最小包装</th>
                            <th fieldname="RETURN_COUNT">退货数</th>
                            <th fieldname="SALE_PRICE" refer="formatAmount" align="right">经销商价格(元)</th>
                            <th fieldname="AMOUNT" refer="formatAmount" align="right">金额(元)</th>
                            <th colwidth="40" type="link" title="[删除]"  action="doDeleteEdit">操作</th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
    var action = <%=action%>
    // 弹出窗体
    var dialog = $("body").data("returnPurchaseApplyEdit");
    $(function(){

        var srcVal="";
        if(orgType=="<%=DicConstant.ZZLB_09%>"){
            // 配送中心登录
            srcVal = "T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME{WAREHOUSE_ID,WAREHOUSE_NAME}:1=1 AND STATUS=<%=DicConstant.YXBS_01 %> AND WAREHOUSE_ID=<%=paraValue1%>";
        }
        if(orgType=="<%=DicConstant.ZZLB_10%>"||orgType=="<%=DicConstant.ZZLB_11%>"){
            // 服务站登录
            srcVal = "T#TM_ORG A,PT_BA_SERVICE_DC B:A.CODE:A.ONAME{A.ORG_ID,A.ONAME}:1=1 AND A.ORG_ID = B.DC_ID AND B.STATUS=<%=DicConstant.YXBS_01 %> AND B.ORG_ID=<%=user.getOrgId() %> ";
<%--             srcVal = "T#TM_ORG:CODE:ONAME{ORG_ID,ONAME}:1=1 AND ORG_ID IN(SELECT DC_ID FROM PT_BA_SERVICE_DC WHERE STATUS=<%=DicConstant.YXBS_01 %> AND ORG_ID=<%=user.getOrgId() %> )"; --%>
        }

        // 接收方表选设置
        $("#dia-code-edit").attr("src",srcVal);

        if(action == "1") {
            // ------ 新增操作
            // 隐藏新增退件按钮
            $("#formBar").find("li:eq(0)").hide();
            // 隐藏提报按钮
            $("#formBar").find("li:eq(2)").hide();
            // 隐藏下载模板按钮
            $("#formBar").find("li:eq(3)").hide();
            // 隐藏批量导入按钮
            $("#formBar").find("li:eq(4)").hide();
        } else {
            // ------ 修改操作
            var selectedRows = $("#tab-index").getSelectedRows();
            //selectedRows[0].attr("rowdata"):行对象属性rowdata中保存了xml格式的行数据
            // 退件单号
            $("#dia-returnNo-edit").val(selectedRows[0].attr("RETURN_NO"));
            // 接收方代码
            $("#dia-code-edit").val(selectedRows[0].attr("RECEIVE_ORG_NAME"));
            $("#dia-code-edit").attr("code",selectedRows[0].attr("RECEIVE_ORG_CODE"));
            // 接收方ID
            $("#dia-orgId-edit").val(selectedRows[0].attr("RECEIVE_ORG_ID"));
            // 接收方名称
            $("#dia-oName-edit").val(selectedRows[0].attr("RECEIVE_ORG_NAME"));
            // 原订单号
            $("#dia-sourceOrderNo-edit").val(selectedRows[0].attr("SOURCE_ORDER_NO"));
            // 原订单ID
            $("#dia-sourceOrderId-edit").val(selectedRows[0].attr("SOURCE_ORDER_ID"));
            // 退件订单ID
            $("#dia-returnId-edit").val(selectedRows[0].attr("RETURN_ID"));
            // 备注
            $("#dia-remarks-edit").val(selectedRows[0].attr("REMARKS"));

            // 查询退件申请明细
            searchReturnPurchaseApplyDtl();
        }

        // 新增退件按钮绑定
        $("#btn-returnPurchase-edit").click(function(){
            var options = {max:true,mask:true,mixable:true,minable:true,resizable:false,drawable:true};
            $.pdialog.open(webApps + "/jsp/dms/dealer/part/sales/returnPurchaseMng/returnPurchaseSearch.jsp", "returnPurchaseSearch", "退件信息查询", options);
        });

        // 保存按钮绑定
        $("#btn-save-edit").click(function(){
            if(action == "1") {
                // ------ 新增操作
                var insertUrl = "<%=request.getContextPath()%>/part/salesMng/returnPurchaseMng/ReturnPurchaseApplyMngAction/insertReturnPurchaseApply.ajax";
                var $f = $("#dia-fm-edit");
                var sCondition = {};
                if (submitForm($f) == false) {
                    return false;
                }
                sCondition = $f.combined(1) || {};
                doNormalSubmit($f, insertUrl, "btn-save-edit", sCondition, insertCallBack);
            } else {
                // ------ 修改操作
                var insertUrl = "<%=request.getContextPath()%>/part/salesMng/returnPurchaseMng/ReturnPurchaseApplyMngAction/updateReturnApply.ajax";
                var $f = $("#dia-fm-edit");
                var sCondition = {};
                if (submitForm($f) == false) {
                    return false;
                }
                sCondition = $f.combined(1) || {};
                doNormalSubmit($f, insertUrl, "btn-save-edit", sCondition, updateCallBack);
            }
        });

        // 提报按钮绑定
        $("#btn-report-edit").click(function(){
            if($("#dia-tab-edit_content").find("tr").size()==0){
                alertMsg.warn("请添加配件.");
                return false;
            } else if($("#dia-tab-edit_content").find("tr").size() == 1) {
                if($("#dia-tab-edit_content").find("tr").eq(0).find("td").size() == 1) {
                    alertMsg.warn("请添加配件.");
                    return false;
                }
            }
            
            // begin by fuxiao 2015-01-14 提报时将按钮禁用掉，防止重复提交
            $("#btn-report-edit").prop("disabled",true);
            // end
            
            var doReportUrl = "<%=request.getContextPath()%>/part/salesMng/returnPurchaseMng/ReturnPurchaseApplyMngAction/updateReturnPurchaseApply.ajax";
            var url = doReportUrl + "?returnId=" + $('#dia-returnId-edit').val();
            sendPost(url, "", "", doReportCallBack, "true");
        });

        // 下载模板按钮绑定
        $("#btn-download-edit").click(function(){
            var url = encodeURI("<%=request.getContextPath()%>/jsp/dms/common/download.jsp?filename=tjsqdrmb.xls");
            window.location.href = url;
        });

        // 批量导入按钮绑定
        $("#btn-import-edit").click(function(){
            //9:表示最大列，3：表示有效数据是第几行开始，第一行是1
            //最后一个参数表示 导入成功后显示页
            importXls('PT_BU_RETURN_APPLY_DTL_TMP',$('#dia-returnId-edit').val(),4,3,"/jsp/dms/dealer/part/sales/returnPurchaseMng/importSuccess.jsp");
        });

        // 关闭按钮绑定
        $("button.close").click(function(){
            $.pdialog.closeCurrent();
            return false;
        });
    });

    // 经销商价
    function formatAmount(obj){
        return "<div style='text-align:right;'>"+amountFormatNew($(obj).html())+"</div>";
    }

    // 保存按钮（新增）回调函数
    function insertCallBack(res) {
        try {
            action = "2";
            var rows = res.getElementsByTagName("ROW");
            // 读取XML中的FLAG属性(FLAG:true有重复数据;)
            var returnId = getNodeText(rows[0].getElementsByTagName("RETURN_ID").item(0));
            $("#dia-returnNo-edit").val(getNodeText(rows[0].getElementsByTagName("RETURN_NO").item(0)));
            // 退件订单ID
            $("#dia-returnId-edit").val(returnId);
            // 显示提报按钮
            $("#formBar").find("li:eq(2)").show();
            // 显示新增退件按钮
            $("#formBar").find("li:eq(0)").show();
            // 显示下载模板按钮
            $("#formBar").find("li:eq(3)").show();
            // 显示批量导入按钮
            $("#formBar").find("li:eq(4)").show();
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

    // 保存按钮（修改）回调函数
    function updateCallBack(res) {
        try {
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

    // 提报按钮回调函数
    function doReportCallBack(res) {
    	
        // begin by fuxiao 2015-01-14 提报完毕将按钮禁用释放
        $("#btn-report-edit").prop("disabled",false);
        // end
        
        // 查询退件申请
        searchReturnPurchaseApply();
        $.pdialog.closeCurrent();
        return false;
    }

    // 订单查询
    function openSaleOrder(){
        var options = {max: false, width: 900, height: 500, mask: true, mixable: true, minable: true, resizable: false, drawable: true};
        $.pdialog.open(webApps+"/jsp/dms/dealer/part/sales/returnPurchaseMng/returnPurchaseSaleOrder.jsp", "salesOrderSearch", "退件订单查询", options);
    }

    // 查询退件申请明细
    function searchReturnPurchaseApplyDtl() {
        var edit_searchUrl = "<%=request.getContextPath()%>/part/salesMng/returnPurchaseMng/ReturnPurchaseApplyMngAction/searchReturnPurchaseApplyDtl.ajax";
        var $f = $("#dia-fm-edit-oderId");
        var sCondition = {};
        if (submitForm($f) == false) {
            return false;
        }
        sCondition = $f.combined() || {};
        doFormSubmit($f, edit_searchUrl, "", 1, sCondition, "dia-tab-edit");
    }

    // 删除操作
    function doDeleteEdit(rowObj) {
        $row = $(rowObj);
        var deleteReturnPurchaseApplyDtlUrl = "<%=request.getContextPath()%>/part/salesMng/returnPurchaseMng/ReturnPurchaseApplyMngAction/deleteReturnPurchaseApplyDtl.ajax";
        var url = deleteReturnPurchaseApplyDtlUrl + "?dtlId=" + $(rowObj).attr("DTL_ID");
        sendPost(url, "", "", doDeleteCallBack, "true");
    }

    //删除回调方法
    function doDeleteCallBack(res) {
        try {
            if ($row)
                $("#dia-tab-edit").removeResult($row);
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

    // 最小包装数+最小包装单位(例:10/包)
    function appendPack_Unit(obj) {
        var $tr = $(obj).parent();
        var minPack = $tr.attr("MIN_PACK");
        var minUnit = $tr.attr("MIN_UNIT_sv");
        if (minPack==null) {
            minPack="";
        }
        if (minUnit == null) {
            minUnit="";
        }
        return minPack + '/' + minUnit;
    }
</script>
