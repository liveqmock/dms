<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.org.framework.Globals" %>
<%@ page import="com.org.framework.common.User" %>
<%@ page import="com.org.dms.common.DicConstant" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
    String contentPath = request.getContextPath();
%>
<div id="dia-layout">
    <div style="height:auto;overflow:hidden;">
        <form method="post" id="fm-stockMoveInfo" class="editForm">
            <%--隐藏域查询条件--%>
            <input type="hidden" id="dia-DTL_ID" name="dia-DTL_ID" datasource="DTL_ID"/>
            <input type="hidden" id="dia-STOCK_ID" name="dia-STOCK_ID" datasource="STOCK_ID"/>
            <input type="hidden" id="dia-PART_ID" name="dia-PART_ID" datasource="PART_ID"/>
            <input type="hidden" id="dia-WAREHOUSE_ID" name="dia-WAREHOUSE_ID" datasource="WAREHOUSE_ID"/>
            <input type="hidden" id="dia-POSITION_ID" name="dia-POSITION_ID" datasource="POSITION_ID"/>
            <input type="hidden" id="dia-SUPPLIER_ID" name="dia-SUPPLIER_ID" datasource="SUPPLIER_ID"/>
            <input type="hidden" id="dia-SUPPLIER_CODE" name="dia-SUPPLIER_CODE" datasource="SUPPLIER_CODE"/>
            <input type="hidden" id="dia-SUPPLIER_NAME" name="dia-SUPPLIER_NAME" datasource="SUPPLIER_NAME"/>

            <div align="left">
                <fieldset>
                    <legend align="right"><a onclick="onTitleClick('tab-stockMoveInfo')">&nbsp;移位管理编辑&gt;&gt;</a>
                    </legend>
                    <table class="editTable" id="tab-stockMoveInfo">
                        <tr>
                            <td><label>配件代码：</label></td>
                            <td>
                                <input type="text" id="dia-PART_CODE" name="dia-PART_CODE" datasource="PART_CODE" readOnly="true"/>
                            </td>
                            <td><label>配件名称：</label></td>
                            <td>
                                <input type="text" id="dia-PART_NAME" name="dia-PART_NAME" datasource="PART_NAME" readOnly="true"/>
                            </td>
                        </tr>
                        <tr>
                            <td><label>原库区代码：</label></td>
                            <td>
                                <input type="text" id="dia-AREA_CODE" name="dia-AREA_CODE" datasource="AREA_CODE" readOnly="true"/>
                            </td>
                            <td><label>原库区名称：</label></td>
                            <td>
                                <input type="text" id="dia-AREA_NAME" name="dia-AREA_NAME" datasource="AREA_NAME" readOnly="true"/>
                            </td>
                        </tr>
                        <tr>
                            <td><label>原库位代码：</label></td>
                            <td>
                                <input type="text" id="dia-POSITION_CODE" name="dia-POSITION_CODE" datasource="POSITION_CODE" readOnly="true"/>
                            </td>
                            <td><label>原库位名称：</label></td>
                            <td>
                                <input type="text" id="dia-POSITION_NAME" name="dia-POSITION_NAME" datasource="POSITION_NAME" readOnly="true"/>
                            </td>
                        </tr>
                        <tr>
                            <td><label>库位库存：</label></td>
                            <td>
                                <input type="text" id="dia-AMOUNT" name="dia-AMOUNT" datasource="AMOUNT" readOnly="true"/>
                            </td>
                            <td><label>占用库存：</label></td>
                            <td>
                                <input type="text" id="dia-OCCUPY_AMOUNT" name="dia-OCCUPY_AMOUNT" datasource="OCCUPY_AMOUNT" readOnly="true"/>
                            </td>
                        </tr>
                        <tr>
                            <td><label>目标库区：</label></td>
                            <td>
                                <input type="text" id="dia-DES_AREA_CODE" name="dia-DES_AREA_CODE" datasource="DES_AREA_CODE" kind="dic" src="" datatype="0,is_null,30"/>
                                <input type="hidden" id="dia-DES_AREA_ID" name="dia-DES_AREA_ID" datasource="DES_AREA_ID"/>
                                <input type="hidden" id="dia-DES_AREA_NAME" name="dia-DES_AREA_NAME" datasource="DES_AREA_NAME"/>
                            </td>
                            <td><label>目标库位：</label></td>
                            <td>
                                <input type="text" id="dia-DES_POSITION_CODE" name="dia-DES_POSITION_CODE" datasource="DES_POSITION_CODE" kind="dic" src="" datatype="0,is_null,30"/>
                                <input type="hidden" id="dia-DES_POSITION_ID" name="dia-DES_POSITION_ID" datasource="DES_POSITION_ID"/>
                                <input type="hidden" id="dia-DES_POSITION_NAME" name="dia-DES_POSITION_NAME" datasource="DES_POSITION_NAME"/>
                            </td>
                        </tr>
                        <tr>
                            <td><label>可用库存：</label></td>
                            <td>
                                <input type="text" id="dia-AVAILABLE_AMOUNT" name="dia-AVAILABLE_AMOUNT" datasource="AVAILABLE_AMOUNT" readOnly="true"/>
                            </td>
                            <td><label>移动数量：</label></td>
                            <td>
                                <input type="text" id="dia-MOVE_AMOUNT" name="dia-MOVE_AMOUNT" datasource="MOVE_AMOUNT" datatype="0,is_digit_0,10"/>
                            </td>
                        </tr>
                    </table>
                </fieldset>
            </div>
        </form>
        <div class="formBar">
            <ul>
                <li>
                    <div class="buttonActive">
                        <div class="buttonContent">
                            <button type="button" id="btn-save">移&nbsp;&nbsp;位</button>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="button">
                        <div class="buttonContent">
                            <button class="close" type="button">关&nbsp;&nbsp;闭</button>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</div>
<script type="text/javascript">

    var diaSaveAction = "<%=request.getContextPath()%>/part/storageMng/stockMng/StockMoveAction";

    $(function () {
        //移位按钮响应
        $('#btn-save').bind('click', function () {
            var availableAmount = parseInt($('#dia-AVAILABLE_AMOUNT').val());
            var moveAmount = parseInt($('#dia-MOVE_AMOUNT').val());
            if(moveAmount>availableAmount){
                alertMsg.warn('移动数量不能大于可用库存!');
                return;
            }
            //获取需要提交的form对象
            var $f = $("#fm-stockMoveInfo");
            //submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
            if (submitForm($f) == false) return false;
            var sCondition = {};
            //将需要提交的内容拼接成json
            sCondition = $f.combined(1) || {};

            var updateUrl = diaSaveAction + "/movePosition.ajax";
            doNormalSubmit($f, updateUrl, "btn-save", sCondition, diaUpdateCallBack);
        });

        var selectedRows = $("#tab-stockDtlList").getSelectedRows();
        setEditValue("fm-stockMoveInfo", selectedRows[0].attr("rowdata"));

        $('#dia-DES_AREA_CODE').attr('src','T#PT_BA_WAREHOUSE_AREA A:A.AREA_CODE:A.AREA_NAME{A.AREA_ID,A.AREA_NAME}:1=1 AND A.WAREHOUSE_ID = '+$('#dia-WAREHOUSE_ID').val()+' AND A.STATUS=<%=DicConstant.YXBS_01 %> AND EXISTS(SELECT 1 FROM PT_BA_WAREHOUSE_PART_RL B,PT_BA_WAREHOUSE_POSITION C WHERE B.POSITION_ID = C.POSITION_ID AND B.PART_ID = '+$('#dia-PART_ID').val()+' AND C.AREA_ID = A.AREA_ID) ORDER BY A.AREA_CODE');

    });
    //表选字典回调方法
    function afterDicItemClick(id, $row, selIndex){
        //$row 行对象
        //selIndex 字典中第几行
		var ret =true;
        if(id == 'dia-DES_AREA_CODE')
        {
            var desAreaId = $row.attr('A.AREA_ID');
            var desAreaName = $row.attr('A.AREA_NAME');
            $('#dia-DES_AREA_ID').val(desAreaId);
            $('#dia-DES_AREA_NAME').val(desAreaName);
            $('#dia-DES_POSITION_CODE').attr('src','T#PT_BA_WAREHOUSE_PART_RL A,PT_BA_WAREHOUSE_POSITION B:A.POSITION_CODE:A.POSITION_NAME{A.POSITION_ID,A.POSITION_NAME}:1=1 AND A.POSITION_ID = B.POSITION_ID AND B.AREA_ID = '+desAreaId+' AND A.POSITION_ID <> '+$('#dia-POSITION_ID').val()+' AND A.PART_ID = '+$('#dia-PART_ID').val()+' ORDER BY B.POSITION_CODE');
        }
        if(id = 'dia-DES_POSITION_ID')
        {
            var desPositionId = $row.attr('A.POSITION_ID');
            var desPositionName = $row.attr('A.POSITION_NAME');
            $('#dia-DES_POSITION_ID').val(desPositionId);
            $('#dia-DES_POSITION_NAME').val(desPositionName);
        }
        return ret;
    }
    var editWin = $("body").data("editWin");
    //更新回调函数
    function diaUpdateCallBack(res) {
        try {
            $.pdialog.close(editWin);
            $('#btn-search').click();
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

</script>