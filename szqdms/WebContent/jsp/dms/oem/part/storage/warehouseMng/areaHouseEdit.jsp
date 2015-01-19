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
                <input type="hidden" id="dia-areaid" name="dia-areaid" datasource="AREA_ID"/>
                <div align="left">
                    <fieldset>
                        <legend align="right">
                            <a onclick="onTitleClick('tab-record')">&nbsp;库区信息编辑&gt;&gt;</a>
                        </legend>
                        <table class="editTable" id="tab-record">
                            <tr>
                                <td><label>库区代码：</label></td>
                                <td><input type="text" id="dia-areaCode" name="dia-areaCode" datasource="AREA_CODE" datatype="0,is_null,30"/></td>
                                <td><label>库区名称：</label></td>
                                <td><input type="text" id="dia-areaName" name="dia-areaName" datasource="AREA_NAME" datatype="0,is_null,30"/></td>
                            </tr>
                            <tr>
                                <td><label>所属仓库：</label></td>
                                <td><input type="text" id="dia-warehouseCode" name="dia-warehouseCode" datasource="WAREHOUSE_CODE" datatype="0,is_null,50" isreload="true"
                                           src="T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME{WAREHOUSE_ID,WAREHOUSE_NAME}:1=1 AND WAREHOUSE_STATUS=<%=DicConstant.YXBS_01 %> ORDER BY WAREHOUSE_CODE" kind="dic" />
                                    <input type="hidden" id="dia-warehouseId" datasource="WAREHOUSE_ID"/>
                                    <input type="hidden" id="dia-warehouseName" datasource="WAREHOUSE_NAME"/>
                                </td>
                                <td><label>库区状态：</label></td>
                                <td>
                                    <select class="combox" id="dia-areaStatus" name="dia-areaStatus" kind="dic" src="YXBS" datasource="AREA_STATUS" datatype="0,is_null,10">
                                            <option value=<%=DicConstant.YXBS_01 %>>有效</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td><label>库区属性：</label></td>
                                <td><input type="text" id="dia-areaAttr" name="dia-areaAttr" datasource="AREA_ATTR" datatype="0,is_null,10" isreload="true" src="KQSX" kind="dic" />
                                </td>
                                <td><label>库管员：</label></td>
							    <td>
							    	<input type="text" id="diaU-userAccount"  name="diaU-userAccount" kind="dic" 
							    		src="T#TM_USER:ACCOUNT:PERSON_NAME:1=1 AND ORG_ID='10000063' AND STATUS='100201'" 
							    		datasource="KEEP_MAN" datatype="0,is_null,32" />
							    	<input type="hidden" id="diaU-userName"  name="diaU-userName" datasource="USER_NAME" />
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
    var diaSaveAction = "<%=request.getContextPath()%>/part/storageMng/warehouseMng/AreaHouseMngAction";
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
                var addUrl = diaSaveAction + "/insertAreaHouse.ajax";
                doNormalSubmit($f, addUrl, "btn-save", sCondition, diaInsertCallBack);
            } else    //更新动作
            {
                var updateUrl = diaSaveAction + "/updateAreaHouse.ajax";
                doNormalSubmit($f, updateUrl, "btn-save", sCondition, diaUpdateCallBack);
            }
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
            $("#dia-areaCode").attr("readonly",true);
            $("#dia-warehouseCode").attr("code",selectedRows[0].attr("WAREHOUSE_CODE"));
            $("#dia-warehouseCode").val(selectedRows[0].attr("WAREHOUSE_NAME"));
            $("#dia-warehouseId").val(selectedRows[0].attr("WAREHOUSE_ID"));
            $("#dia-warehouseName").val(selectedRows[0].attr("WAREHOUSE_NAME"));
        }
    });

    //新增回调函数
    function diaInsertCallBack(res) {
        try {
        	$("#dia-areaCode").attr("readonly",true);
            var rows = res.getElementsByTagName("ROW");
            // 读取XML中的FLAG属性(FLAG:true有重复数据;)
            var flag = getNodeText(rows[0].getElementsByTagName("FLAG").item(0));
            if (flag == "true") {
                alertMsg.info("库区代码重复!");
                return false;
            }
            $("#dia-areaid").val(getNodeText(rows[0].getElementsByTagName("AREA_ID").item(0)))
            diaAction = "2";
            // 库区查询
            searchAreaHouse();
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

    //更新回调函数
    function diaUpdateCallBack(res) {
        try {
            // 库区查询
            searchAreaHouse();
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

    // 数据字典回调函数
    function afterDicItemClick(id,$row){
        if(id=="dia-warehouseCode"){
            $("#dia-warehouseId").val($row.attr("WAREHOUSE_ID"));
            $("#dia-warehouseName").val($row.attr("WAREHOUSE_NAME"));
        }
        return true;
    }
</script>