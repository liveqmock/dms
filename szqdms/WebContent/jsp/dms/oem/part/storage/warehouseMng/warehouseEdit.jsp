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
    if (action == null) {
        action = "1";
    }
%>
<div id="dia-layout">
    <div class="page">
        <div class="pageContent" style="">
            <form method="post" id="fm-record" class="editForm">
                <!-- 隐藏域 -->
                <input type="hidden" id="dia-warehouseId" name="dia-warehouseId" datasource="WAREHOUSE_ID"/>
                <div align="left">
                    <fieldset>
                        <legend align="right"><a onclick="onTitleClick('tab-record')">&nbsp;仓库信息编辑&gt;&gt;</a>
                        </legend>
                        <table class="editTable" id="tab-record">
                             <tr>
                                <td><label>仓库代码：</label></td>
                                <td><input type="text" id="dia-wareahouseCode" name="dia-wareahouseCode" datasource="WAREHOUSE_CODE" datatype="0,is_null,30"/></td>
                                <td><label>仓库名称：</label></td>
                                <td><input type="text" id="dia-wareahouseName" name="dia-wareahouseName" datasource="WAREHOUSE_NAME" datatype="0,is_null,30"/></td>
                            </tr>
                            <tr>
                                <td><label>仓库状态：</label></td>
                                <td>
                                    <select class="combox" id="dia-wareahouseStatus" name="dia-wareahouseStatus" kind="dic" src="YXBS" datasource="WAREHOUSE_STATUS" datatype="0,is_null,10">
                                        <option value=<%=DicConstant.YXBS_01 %>>有效</option>
                                    </select>
                                </td>
                                <td><label>仓库类型：</label></td>
                                <td><input type="text" id="dia-parakey" name="dia-parakey" datasource="PARAKEY" datatype="0,is_null,10" isreload="true"
                                           src="T#USER_PARA_CONFIGURE:PARAKEY:PARAVALUE1{PARAKEY,PARAVALUE1}:1=1 AND STATUS=<%=DicConstant.YXBS_01 %> AND APPTYPE='1001' ORDER BY PARAKEY" kind="dic"/>
                                    <input type="hidden" id="dia-wareahousetype" datasource="WAREHOUSE_TYPE"/>
                                    <input type="hidden" id="dia-paravalue1" datasource="PARAVALUE1"/>
                                </td>
                            </tr>
                            <tr>
                            	<td><label>组织代码：</label></td>
							    <td>
							    	<input type="text" id="dia-ORG_CODE" name="dia-ORG_CODE" datasource="CODE" kind="dic" src="T#TM_ORG:CODE:ONAME{ORG_ID}:1=1 AND STATUS=<%=DicConstant.YXBS_01 %>" dicwidth="300" datatype="0,is_null,300"/>
							    	<input type="hidden" id="dia-ORG_ID" name="dia-ORG_ID" datasource="ORG_ID" />
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
    var actionUrl = "<%=request.getContextPath()%>/part/storageMng/warehouseMng/WarehouseMngAction";
    var actionFlag = "<%=action%>";
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
            if (actionFlag == 1) {
                // ------ 插入动作
                var addUrl = actionUrl + "/insertWareHouse.ajax";
                doNormalSubmit($f, addUrl, "btn-save", sCondition, diaInsertCallBack);
            } else {
                // ------ 更新动作
                var updateUrl = actionUrl + "/updateWareHouse.ajax";
                doNormalSubmit($f, updateUrl, "btn-save", sCondition, diaUpdateCallBack);
            }
        });

        //修改操作
        if (actionFlag != "1") {
            //#tab-grid：父页面列表的表格id
            //getSelectedRows():获取列表选中行对象，返回选中行数组
            var selectedRows = $("#tab-grid").getSelectedRows();
            //setEditValue：设置输入框赋值方法
            //"fm-record"：要赋值区域的id
            //selectedRows[0].attr("rowdata"):行对象属性rowdata中保存了xml格式的行数据
            setEditValue("fm-record", selectedRows[0].attr("rowdata"));
            $("#dia-parakey").attr("code",selectedRows[0].attr("PARAKEY"));
            $("#dia-parakey").val(selectedRows[0].attr("PARAVALUE1"));
            $("#dia-paravalue1").val(selectedRows[0].attr("PARAVALUE1"));
            $("#dia-wareahouseCode").attr("readonly",true);
        }
    });

    //新增回调函数
    function diaInsertCallBack(res) {
        try {
        	
        	$("#dia-wareahouseCode").attr("readonly",true);
            var rows = res.getElementsByTagName("ROW");
            // 读取XML中的FLAG属性(FLAG:true有重复数据;)
            var flag = getNodeText(rows[0].getElementsByTagName("FLAG").item(0));
            var wareHouseId = getNodeText(rows[0].getElementsByTagName("WAREHOUSE_ID").item(0));
            if (flag == "true") {
                alertMsg.info("仓库代码重复!");
                return false;
            }
            $("#dia-warehouseId").val(wareHouseId);
            actionFlag="2";
            if ($("#tab-grid").is(":visible") == false) {
                //不显示结果集的情况下，插入一行
                $("#tab-grid").insertResult(res, 0);
                $("#tab-grid").jTable();
            } else
            //显示结果集的情况下，插入一行
                $("#tab-grid").insertResult(res, 0);
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

    //更新回调函数
    function diaUpdateCallBack(res) {
        try {
            // 查询仓库
            searchWareHouse();
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

    // 数据字典回调函数
    function afterDicItemClick(id,$row){
        if(id=="dia-parakey"){
            $("#dia-wareahousetype").val($row.attr("PARAKEY"));
            $("#dia-paravalue1").val($row.attr("PARAVALUE1"));
        }
        if(id=="dia-ORG_CODE"){
            $("#dia-ORG_ID").val($row.attr("ORG_ID"));
        }
        return true;
    }

</script>