<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.org.framework.common.User" %>
<%@ page import="com.org.framework.Globals" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
    String contentPath = request.getContextPath();
    String action = request.getParameter("action");
    if (action == null)
        action = "1";
%>
<div id="dia-layout">
    <div class="page">
        <div class="pageContent" style="">
            <form method="post" id="fm-record" class="editForm">
                <!-- 隐藏域 -->
                <input type="hidden" id="dia-relationId" name="dia-relationId" datasource="RELATION_ID"/>
                <input type="hidden" id="dia-partId" name="dia-partId" datasource="PART_ID"/>
                <div align="left">
                    <fieldset>
                        <legend align="right"><a onclick="onTitleClick('tab-record')">&nbsp;配件与库位关系维护信息编辑&gt;&gt;</a>
                        </legend>
                        <table class="editTable" id="tab-record">
                            <tr>
                                <td><label>配件代码：</label></td>
                                <td><input type="text" id="dia-partCode" name="dia-partCode" datasource="PART_CODE" readOnly datatype="0,is_null,30" onClick="getPart()"/></td>
                                <td><label>配件名称：</label></td>
                                <td><input type="text" id="dia-partName" name="dia-partName" datasource="PART_NAME" readOnly datatype="0,is_null,300"/></td>
                            </tr>
                            <tr>
                                <td><label>所属仓库：</label></td>
                                <td><input type="text" id="dia-warehouseCode" name="dia-warehouseCode" datasource="WAREHOUSE_CODE" datatype="0,is_null,30"
                                           src="T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME{WAREHOUSE_ID,WAREHOUSE_NAME}:1=1 AND WAREHOUSE_STATUS=<%=DicConstant.YXBS_01 %> ORDER BY WAREHOUSE_CODE" isreload="true" kind="dic" />
                                    <input type="hidden" id="dia-warehouseId" datasource="WAREHOUSE_ID"/>
                                    <input type="hidden" id="dia-warehouseName" datasource="WAREHOUSE_NAME"/>
                                </td>
                                <td><label>所属库区：</label></td>
                                <td>
                                    <input type="text" id="dia-areaCode" name="dia-areaCode" datasource="AREA_CODE" datatype="0,is_null,30" kind="dic" isreload="true" readonly="readonly" operation="like" src="" />
                                    <input type="hidden" id="dia-areaId" datasource="AREA_ID"/>
                                    <input type="hidden" id="dia-areaName" datasource="AREA_NAME"/>
                                </td>
                            </tr>
                            <tr>
                                <td><label>所属库位：</label></td>
                                <td>
                                    <input type="text" id="dia-positionCode" name="dia-positionCode" datasource="POSITION_CODE" datatype="0,is_null,30" kind="dic" isreload="true" readonly="readonly" operation="like" src="" />
                                    <input type="hidden" id="dia-positionId" datasource="POSITION_ID"/>
                                    <input type="hidden" id="dia-positionName" datasource="POSITION_NAME"/>
                                </td>
                                <td><label>状态：</label></td>
                                <td>
                                    <select type="text" class="combox" id="dia-STATUS" name="dia-STATUS" kind="dic" src="YXBS" datasource="STATUS" datatype="0,is_null,6" readonly="readonly">
                                        <option value="-1" selected>--</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td><label>默认库位：</label></td>
                                <td>
                                    <select type="text" class="combox" id="dia-IfDefault" name="dia-IfDefault" kind="dic" src="SF" datasource="IF_DEFAULT" datatype="0,is_null,6" readonly="readonly">
                                        <option value="-1" selected>--</option>
                                    </select>
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                </div>
            </form>
            <div class="formBar">
                <ul>
              <!--       <li>
                        <div class="buttonActive">
                            <div class="buttonContent">
                                <button type="button" id="dia-btn-search">配件查询</button>
                            </div>
                        </div>
                    </li> -->
                    <li>
                        <div class="button">
                            <div class="buttonContent">
                                <button type="button" id="btn-save">保&nbsp;&nbsp;存</button>
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
</div>
<script type="text/javascript">
    var diaSaveAction = "<%=request.getContextPath()%>/part/storageMng/warehouseMng/PartRlHouseMngAction";
    var diaAction = "<%=action%>";
    //初始化
    $(function () {
        //绑定保存按钮
        $("#btn-save").bind("click", function (event) {
            //获取需要提交的form对象
            var $f = $("#fm-record");
            //submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
            if (submitForm($f) == false) return false;
            var sCondition = {};
            //将需要提交的内容拼接成json
            sCondition = $("#fm-record").combined(1) || {};
            if (diaAction == 1)    //插入动作
            {
                var addUrl = diaSaveAction + "/insertPartRlHouse.ajax";
                doNormalSubmit($f, addUrl, "btn-save", sCondition, diaInsertCallBack);
            } else    //更新动作
            {
                var updateUrl = diaSaveAction + "/updatePartRlHouse.ajax";
                doNormalSubmit($f, updateUrl, "btn-save", sCondition, diaUpdateCallBack);
            }
        });

        
        $("#dia-btn-search").bind("click", function (event) {
             var options = {max: false, width: 900, height: 500, mask: true, mixable: true, minable: true, resizable: false, drawable: true};
             $.pdialog.open(webApps + "/jsp/dms/oem/part/storage/warehouseMng/partRlHouseEditSearch.jsp", "partSelWin", "配件信息查询", options);
        });

        //修改操作
        if (diaAction != "1") {
            //#tab-grid：父页面列表的表格id
            //getSelectedRows():获取列表选中行对象，返回选中行数组
            var selectedRows = $("#tab-grid").getSelectedRows();
            //setEditValue：设置输入框赋值方法
            //"fm-record"：要赋值区域的id
            //selectedRows[0].attr("rowdata"):行对象属性rowdata中保存了xml格式的行数据
            setEditValue("fm-record", selectedRows[0].attr("rowdata"));
            $("#dia-warehouseCode").attr("code",selectedRows[0].attr("WAREHOUSE_CODE"));
            $("#dia-warehouseCode").val(selectedRows[0].attr("WAREHOUSE_NAME"));
            $("#dia-warehouseId").val(selectedRows[0].attr("WAREHOUSE_ID"));
            $("#dia-warehouseName").val(selectedRows[0].attr("WAREHOUSE_NAME"));
            $("#dia-warehouseCode").attr("readonly",true);
            $("#dia-warehouseCode").attr("src","");
            $("#dia-areaCode").attr("code",selectedRows[0].attr("AREA_CODE"));
            $("#dia-areaCode").val(selectedRows[0].attr("AREA_NAME"));
            $("#dia-areaId").val(selectedRows[0].attr("AREA_ID"));
            $("#dia-areaName").val(selectedRows[0].attr("AREA_NAME"));
            $("#dia-positionCode").attr("code",selectedRows[0].attr("POSITION_CODE"));
            $("#dia-positionCode").val(selectedRows[0].attr("POSITION_NAME"));
            $("#dia-positionId").val(selectedRows[0].attr("POSITION_ID"));
            $("#dia-positionName").val(selectedRows[0].attr("POSITION_NAME"));
        }
    });

    //新增回调函数
    function diaInsertCallBack(res) {
        try {
            var rows = res.getElementsByTagName("ROW");
            // 读取XML中的FLAG属性(FLAG:true有重复数据;)
            var flag = getNodeText(rows[0].getElementsByTagName("FLAG").item(0));
            if (flag == "true") {
                alertMsg.info("对应库位的配件重复!");
                return false;
            }
            if ($("#tab-grid").is(":visible") == false) {
                //不显示结果集的情况下，插入一行
                $("#tab-grid").insertResult(res, 0);
                $("#tab-grid").jTable();
            } else
            //显示结果集的情况下，插入一行
                $("#tab-grid").insertResult(res, 0);
            $("#dia-warehouseCode").attr("readonly",true);
            $("#dia-warehouseCode").attr("src","");
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

    //更新回调函数
    function diaUpdateCallBack(res) {
        try {
            // 查询方法
            search();
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }
    function getPart(){
    	 var options = {max: false, width: 900, height: 500, mask: true, mixable: true, minable: true, resizable: false, drawable: true};
         $.pdialog.open(webApps + "/jsp/dms/oem/part/storage/warehouseMng/partRlHouseEditSearch.jsp", "partSelWin", "配件信息查询", options);
    }
</script>