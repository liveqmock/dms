<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String action = request.getParameter("action");
    if(action == null)
        action = "1";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<div id="dia-layout">
    <div class="tabs" eventType="click" id="dia-tabs">
        <div class="tabsHeader">
            <div class="tabsHeaderContent">
                <ul>
                    <li><a href="javascript:void(0)"><span>实销出库单信息</span></a></li>
                    <li><a href="javascript:void(0)"><span>配件出库清单</span></a></li>
                </ul>
            </div>
        </div>
        <div class="tabsContent" >
            <div class="page">
            <div class="pageContent" >
                <form method="post" id="dib-fm-realSale" class="editForm" style="width:99%;">
                  <input type="hidden" id="dib-Sale_ID" name="dib-Sale_ID" datasource="SALE_ID"/>
                <div align="left">
                    <fieldset>
                    <legend align="right"><a onclick="onTitleClick('dia-tab-realsale')">&nbsp;实销出库单维护&gt;&gt;</a></legend>
                    <table class="editTable" id="dia-tab-realsale">
                        <tr>
                            <td><label>实销单号：</label></td>
                            <td><input id ="dib-SaleNo" name="dib-SaleNo" readonly="readonly" type="text" datasource="SALE_NO" value="系统自动生成" /></td>
                            <td><label>实销类型：</label></td>
                            <td><div id="dia-SaleType" >实销出库</div></td>
                        </tr>
                        <tr id="cgrk" >
                            <td><label>客户名称：</label></td>
                            <td><input type="text" id="customer_name" name="customer_name" datasource="CUSTOMER_NAME" datatype="0,is_null,30"  value=""/></td>
                            <td><label>联系电话：</label></td>
                            <td><input type="text" id="link_phone"  name="link_phone" datasource="LINK_PHONE" datatype="0,is_null,30"  value=""/></td>
                            <td><label>联系地址：</label></td>
                            <td><input type="text" id="link_addr" name="link_addr"  datasource="LINK_ADDR"  value=""/></td>
                        </tr>
                        <tr>
                            <td><label>备注：</label></td>
                            <td colspan="5">
                                <textarea id="remark" name="remark" style="width:460px;" datasource="REMARK" datatype="1,is_null,600"></textarea>
                            </td>
                        </tr>
                    </table>
                    </fieldset>
                </div>
                </form>
                <div class="formBar">
                    <ul>
                        <li id="dia-save-btn"><div class="buttonActive"><div class="buttonContent"><button type="button" id="dib-save">保&nbsp;&nbsp;存</button></div></div></li>
                        <li ><div class="button"><div class="buttonContent"><button type="button" id="btn-next" name="btn-next">下一步</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button class="close" id="dia-close0" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
                    </ul>
                </div>
            </div>
            </div>
            <div class="page">
                <div class="pageContent" >
                <div align="left">
                <form method="post" id="dia-fm-partlist"></form>
                <div id="dia-partlist">
                <fieldset>
                <legend align="right"><a onclick="onTitleClick('dib-tab-partlist')">&nbsp;配件出库清单&gt;&gt;</a></legend>
                    <table style="display:none;width:100%;" id="dib-tab-partlist" name="dib-tab-partlist" ref="dia-partlist" edit="false" >
                        <thead>
                            <tr>
                                <th type="single" name="XH" style="display:" append="plus|addPart"></th>
                                <th fieldname="PART_ID" style="display: none;">配件ID</th>
                                <th fieldname="PART_CODE" >配件代码</th>
                                <th fieldname="PART_NAME" >配件名称</th>
                                <th fieldname="UNIT" >单位</th>
                                <th fieldname="MIN_PACK" >最小包装</th>
                                <th fieldname="SALE_COUNT" >出库数量</th>
                                <th fieldname="SALE_PRICE" refer="formatAmount">零售价</th>
                                <th fieldname="AMOUNT" refer="formatAmount">金额</th>
                                <th colwidth="105" type="link" title="[删除]"  action="doDelete">操作</th>
                            </tr>
                        </thead>
                    </table>
                </fieldset>
                </div>
                </div>
                </div>
                <div class="formBar">
                    <ul>
                        <li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre1" name="btn-pre">上一步</button></div></div></li>
                        <li id="dia-submit-btn"><div class="button"><div class="buttonContent"><button type="button" id="dia-submit" name="btn-out">实销出库</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button class="close" id="dia-close1" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="tabsFooter">
            <div class="tabsFooterContent"></div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var diaSaveAction = "<%=request.getContextPath()%>/part/storageMng/stockOutMng/RealSaleOutAction";
    var partSearchUrl = "<%=request.getContextPath()%>/part/storageMng/stockOutMng/RealSaleOutAction/realSaleDtlsearch.ajax";
    var url = "<%=request.getContextPath()%>/part/storageMng/stockOutMng/RealSaleOutAction";
    var action = "<%=action%>";

    $(function(){
        if (action == "2") {
            // 修改
            var selectedRows = $("#tab-saleOut" ).getSelectedRows();
            setEditValue("dib-fm-realSale", selectedRows[0].attr("rowdata"));
        } 

        $("button[name='btn-pre']").bind("click",function(event){
            $("#dia-tabs").switchTab(parseInt($("#dia-tabs").attr("currentIndex"))-1);
        });

        $("#btn-next").bind("click",function(event){
            var $tabs = $("#dia-tabs");
            $tabs.switchTab(parseInt($tabs.attr("currentIndex"))+1);
            //跳转后实现方法
            switch(parseInt($tabs.attr("currentIndex"))) {
                case 1:
                    if (!$('#dib-Sale_ID').val()) {
                        alertMsg.warn('请先保存实销单信息!');
                        $tabs.switchTab(parseInt($tabs.attr("currentIndex"))-1);
                    } else {
                        searchPromPart();
                    }
                    break;
                default:
                    break;
            }
        });

        $("#dia-close0").click(function(){
            $.pdialog.closeCurrent();
            return false;
        });

        $("#dia-close1").click(function(){
            $.pdialog.closeCurrent();
            return false;
        });

        //保存基本信息按钮响应
        $('#dib-save').bind('click', function () {
            //获取需要提交的form对象
            var $f = $("#dib-fm-realSale");
            if (submitForm($f) == false) return false;
            var sCondition = {};
            //将需要提交的内容拼接成json
            sCondition = $f.combined(1) || {};
            if (action == 1) {
                // 新增
                var addUrl = diaSaveAction + "/insertRealSale.ajax";
                doNormalSubmit($f, addUrl, "dib-save", sCondition, diaInsertCallBack);
            } else {
                // 更新
                var updateUrl = diaSaveAction + "/updateRealSale.ajax";
                doNormalSubmit($f, updateUrl, "dib-save", sCondition, diaUpdateCallBack);
            }
        });

        // 实销出库
        $("#dia-submit").click(function(){
        	if($("#dib-tab-partlist_content").find("tr").size()==0){
                alertMsg.warn("请添加配件.");
                return false;
            } else if($("#dib-tab-partlist_content").find("tr").size() == 1) {
                if($("#dib-tab-partlist_content").find("tr").eq(0).find("td").size() == 1) {
                    alertMsg.warn("请添加配件.");
                    return false;
                }
            }
            var updatesaleUrl = url + "/realSaleOut.ajax?&sale_id=" + $("#dib-Sale_ID").val()+"&out_no=" + $("#dib-SaleNo").val();
            $("#dia-submit").attr("style","display:none");
            sendPost(updatesaleUrl, "dia-submit", "", saleoutsubCallBack, "true");
        });
    });

    //实销出库 回调
    function saleoutsubCallBack(res) {
        try {
        	$("#dia-submit-btn").attr("style","display:none");
        	$("#dib-save-btn").attr("style","display:none");
            // 查询实销单
            searchRealSalesOut();
            $.pdialog.closeCurrent();
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true; 
    }

    function addPart() {
        var options = {max:true,mask:true,mixable:true,minable:true,resizable:false,drawable:true};
        $.pdialog.open(webApps + "/jsp/dms/dealer/part/storage/outstorage/realSalePartsearch.jsp", "partsearch", "配件库存查询", options);
    }

    // 查询配件明细
    function searchPromPart() {
        var $f = $("#dia-fm-partlist");
        var sCondition = {};
        sCondition = $f.combined() || {};
        var partSearchUrl1 =partSearchUrl+"?saleId="+$("#dib-Sale_ID").val(); 
        doFormSubmit($f,partSearchUrl1,"btn-next",1,sCondition,"dib-tab-partlist");
        $("#dib-tab-partlist").show();
        $("#dib-tab-partlist").jTable();
    }

    //新增回调函数
    function diaInsertCallBack(res) {
        try {
            var rows = res.getElementsByTagName("ROW");
            
            if (rows && rows.length > 0) {
                //获取新增出库单ID 并设置到隐藏域中
                var saleId = getNodeText(rows[0].getElementsByTagName("SALE_ID").item(0));
                $("#dib-SaleNo").val(getNodeText(rows[0].getElementsByTagName("SALE_NO").item(0)));
                $('#dib-Sale_ID').val(saleId);
            }
            action = 2;
            // 查询实销单
            searchRealSalesOut();
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

    //更新回调函数
    function diaUpdateCallBack(res) {
        try {
            // 查询实销单
            searchRealSalesOut();
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

    var $row;
    //删除方法，rowobj：行对象，非jquery类型
    function doDelete(rowobj) {
      $row = $(rowobj);
      var deleteUrl = url + "/realSaleDtlDelete.ajax?&dtl_id=" + $(rowobj).attr("DTL_ID")+"&part_id="+$(rowobj).attr("PART_ID")+"&sale_id="+$('#dib-Sale_ID').val()+"&supplierId="+$(rowobj).attr("SUPPLIER_ID");
      sendPost(deleteUrl, "", "", deleteCallBack, "true");
    }

    //删除回调方法
    function deleteCallBack(res) {
      try {
          searchPromPart();
      } catch (e) {
          alertMsg.error(e);
          return false;
      }
      return true;
    }
</script>
