<%@ page import="com.org.dms.common.DicConstant" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <jsp:include page="/head.jsp"/>
    <title>库存锁定</title>
    <script type="text/javascript">
        var searchUrl = "<%=request.getContextPath()%>/part/storageMng/stockMng/StockLockAction/searchStock.ajax";
        var updateStockStatusUrl = "<%=request.getContextPath()%>/part/storageMng/stockMng/StockLockAction/updateStockStatus.ajax";
        //定义弹出窗口样式
        var diaEditOptions = {max: true, width: 720, height: 430, mask: true, mixable: true, minable: true, resizable: true, drawable: true};
        //初始化
        $(function () {
        	$("#tab-stockList").attr("layoutH",document.documentElement.clientHeight - 290);
            //查询按钮响应
            $("#btn-search").bind("click", function (event) {
                var $f = $("#fm-searchStock");
                var sCondition = {};
                sCondition = $f.combined() || {};
                doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-stockList");
                $('#fie-selectedStock').show();
                $('#tab-updateStockStatus').show();
            });
            //修改库存状态按钮响应
            $('#btn-updateStockStatus').bind('click',function(){
                if(!$('#val0').val()){
                    alertMsg.warn('请选择配件!');
                    return;
                }
                //获取需要提交的form对象
                var $f = $("#fm-updateStockStatus");
                //submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
                if (submitForm($f) == false) return false;
                var sCondition = {};
                //将需要提交的内容拼接成json
                sCondition = $f.combined(1) || {};
                doNormalSubmit($f, updateStockStatusUrl, "btn-confirmPart", sCondition, updateStockStatusCallBack);
            })
        });
        //列表复选
        function doCheckbox(checkbox) {
            var $tr = $(checkbox).parent().parent().parent();
            var arr = [];
            arr.push($tr.attr("STOCK_ID"));
            arr.push($tr.attr("PART_NAME"));
            var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']");
            multiSelected($checkbox, arr,$('#div-selectedStock'));
        }
        //修改库存状态回调
        function updateStockStatusCallBack(){
        	$("#val0").val("");
        	$("#val1").val("");
            $("#btn-search").click();
        }

        function ctrlShow(obj){
            var $tr =$(obj).parent();
            if($tr.attr('SUPPLIER_CODE')=='9XXXXXX'){
                return "";
            }else{
                return $(obj).text();
            }
        }
    </script>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif"/>&nbsp;当前位置：
        配件管理 &gt; 仓储管理 &gt; 库存管理 &gt; 库存锁定</h4>

    <div class="page">
        <div class="pageHeader">
            <!-- 提交查询请求form -->
            <form method="post" id="fm-searchStock">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="tab-searchStock">
                        <!-- 定义查询条件 -->
                        <tr>
                            <td><label>配件代码：</label></td>
                            <td><input type="text" id="PART_CODE" name="PART_CODE" datasource="PART_CODE" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>配件名称：</label></td>
                            <td><input type="text" id="PART_NAME" name="PART_NAME" datasource="PART_NAME" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>库存状态：</label></td>
                            <td>
                                <select class="combox" id="STOCK_STATUS" name="STOCK_STATUS" kind="dic" src="KCZT" datasource="STOCK_STATUS"  filtercode="<%=DicConstant.KCZT_01%>|<%=DicConstant.KCZT_02%>"  datatype="1,is_null,30" >
                                    <option value="-1" selected>--</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td><label>仓库名称：</label></td>
                            <td>
                                <input type="text" id="WAREHOUSE_CODE" name="WAREHOUSE_CODE" datasource="PBS.WAREHOUSE_CODE" kind="dic" src="T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME:1=1 AND STATUS=<%=DicConstant.YXBS_01 %> ORDER BY WAREHOUSE_CODE" datatype="1,is_null,30"/>
                            </td>
                        </tr>
                    </table>
                    <div class="subBar">
                        <ul>
                            <li>
                                <div class="buttonActive">
                                    <div class="buttonContent">
                                        <button type="button" id="btn-search">查&nbsp;&nbsp;询</button>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </form>
        </div>
        <div class="pageContent">
            <div id="div-stockList">
                <table style="display:none;width:100%;" multivals="div-selectedStock" id="tab-stockList" name="tablist" ref="div-stockList"
                       refQuery="tab-searchStock">
                    <thead>
                    <tr>
                        <th type="multi" name="XH"  unique="STOCK_ID"></th>
                        <th fieldname="PART_CODE">配件代码</th>
                        <th fieldname="PART_NAME">配件名称</th>
                        <th fieldname="SUPPLIER_NAME" refer="ctrlShow">供应商</th>
                        <th fieldname="WAREHOUSE_CODE">仓库代码</th>
                        <th fieldname="WAREHOUSE_NAME">仓库名称</th>
                        <th fieldname="AMOUNT">库存数量</th>
                        <th fieldname="OCCUPY_AMOUNT">占用数量</th>
                        <th fieldname="AVAILABLE_AMOUNT">可用数量</th>
                        <th fieldname="STOCK_STATUS">库存状态</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
            <form id="fm-updateStockStatus">
                <fieldset id="fie-selectedStock" style="width:70%;display: none">
                    <legend align="left" >&nbsp;[已选定配件]</legend>
                    <div id="div-selectedStock">
                        <table style="width:100%;">
                            <tr>
                                <td>
                                    <textarea style="width:99%;height:40px;display: none" id="val0" column="1" name="multivals" readOnly datasource = "STOCKIDS"></textarea>
                                    <textarea style="width:99%;height:40px;" id="val1" name="multivals" readOnly></textarea>
                                </td>
                            </tr>
                        </table>
                    </div>
                </fieldset>
                <table id="tab-updateStockStatus" style="margin-left:7px;display:none">
                    <tr>
                        <td><label>库存状态设定为：</label></td>
                        <td>
                            <select class="combox" kind="dic" id="stockStatus" name="stockStatus" src="KCZT" datasource="STOCK_STATUS" filtercode="<%=DicConstant.KCZT_01%>|<%=DicConstant.KCZT_02%>"  datatype="0,is_null,30" >
                                <option value="<%=DicConstant.KCZT_01%>" selected>正常库存</option>
                            </select>
                        </td>
                        <td>
                            <div style="margin-left: 10px"  class="button"><div class="buttonContent"><button type="button" id="btn-updateStockStatus">确&nbsp;&nbsp;定</button></div></div>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
</body>
</html>