<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <jsp:include page="/head.jsp"/>
    <title>促销活动下发</title>
    <script type="text/javascript">
        var searchUrl = "<%=request.getContextPath()%>/part/salesMng/promotionMng/PromotionIssueAction/searchPromotion.ajax";
        var issueUrl = "<%=request.getContextPath()%>/part/salesMng/promotionMng/PromotionIssueAction/issuePromotion.ajax";
        var cancelUrl = "<%=request.getContextPath()%>/part/salesMng/promotionMng/PromotionIssueAction/cancelPromotion.ajax";
        //初始化
        $(function () {
            //查询按钮响应
            $("#btn-search").bind("click", function (event) {
                var $f = $("#fm-searchPromotion");
                var sCondition = {};
                sCondition = $f.combined() || {};
                doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-promotionList");
            });
            $("#btn-search").trigger("click");
        });

        var $row;
        //下发
        function doIssue(rowobj) {
            $row = $(rowobj);
            var url = issueUrl + "?promotionId=" + $(rowobj).attr("PROM_ID");
            sendPost(url, "", "", issueCallBack, "true");
        }
        //下发回调方法
        function issueCallBack(res) {
            try {
            	var $f = $("#fm-searchPromotion");
                var sCondition = {};
                sCondition = $f.combined() || {};
                doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-promotionList");
            } catch (e) {
                alertMsg.error(e);
                return false;
            }
            return true;
        }
        //表格内连接数量问题
        function showAnNiu(obj){
            var $tr = $(obj).parent();
            var SALE_STATUS = $tr.find("td").eq(8).attr("code");
            if(SALE_STATUS==<%=DicConstant.CXHDZT_01%>){
            obj.html("<A class=op title=[下发] onclick='doIssue(this.parentElement.parentElement.parentElement)' href='javascript:void(0);'>[下发]</A>");
            }
            if(SALE_STATUS==<%=DicConstant.CXHDZT_02%>){
            obj.html("<A class=op title=[活动取消] onclick='doCancel(this.parentElement.parentElement.parentElement)' href='javascript:void(0);'>[活动取消]</A>");
            }
        }
        //取消促销活动
        function doCancel(rowobj){
        	$row = $(rowobj);
            var url = cancelUrl + "?promotionId=" + $(rowobj).attr("PROM_ID");
            sendPost(url, "", "", cancelCallBack, "true");
        }
        function cancelCallBack(res){
        	try {
        		if ($row)
                    $("#tab-promotionList").removeResult($row);
            } catch (e) {
                alertMsg.error(e);
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif"/>&nbsp;当前位置：
        配件管理 &gt; 销售管理 &gt; 促销管理 &gt; 促销活动下发</h4>

    <div class="page">
        <div class="pageHeader">
            <!-- 提交查询请求form -->
            <form method="post" id="fm-searchPromotion">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="tab-searchPromotion">
                        <!-- 定义查询条件 -->
                        <tr>
                            <td><label>活动名称：</label></td>
                            <td><input type="text" id="PROM_NAME" name="PROM_NAME" datasource="PROM_NAME" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>活动类型：</label></td>
                            <td><select type="text" class="combox" id="PROM_TYPE" name="PROM_TYPE" kind="dic" src="CXHDLX" datasource="PROM_TYPE"  datatype="1,is_null,6" readonly="readonly">
							    	<option value="-1" selected>--</option>
							    </select>
						    </td>
                            <td><label>活动日期：</label></td>
                            <td>
                                <input  type="text" name="START_DATE" id="START_DATE" style="width:75px;" class="Wdate" operation=">=" group="START_DATE,END_DATE" datasource="START_DATE" kind="date" datatype="1,is_date,20" onclick="WdatePicker()" value=""/>
                                <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                                <input  type="text" name="END_DATE" id="END_DATE" style="width:75px;margin-left:-28px;;" class="Wdate" operation="<=" group="START_DATE,END_DATE" datasource="END_DATE" kind="date" datatype="1,is_date,20" onclick="WdatePicker()" value=""/>
                            </td>
                        </tr>
                        <tr>
                        	<td><label>促销状态：</label></td>
                            <td><select type="text" class="combox" id="PROM_STATUS" name="PROM_STATUS" kind="dic" src="CXHDZT" datasource="PROM_STATUS" filtercode="<%=DicConstant.CXHDZT_01%>|<%=DicConstant.CXHDZT_02%>"  datatype="1,is_null,6" >
							    	<option value="-1" selected>--</option>
							    </select>
						    </td>
						    <td><label>下发日期：</label></td>
                            <td>
				    		<input type="text" group="in-kscjrq,in-jscjrq"  id="in-kscjrq"  name="in-kscjrq" operation=">="  dataSource="PUBLISH_TIME" style="width:75px;"  kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="in-kscjrq,in-jscjrq"  id="in-jscjrq"  name="in-jscjrq" operation="<=" dataSource="PUBLISH_TIME" style="width:75px;margin-left:-30px;" kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
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
            <div id="div-promotionList">
                <table style="display:none;width:100%;" id="tab-promotionList" name="tablist" ref="div-promotionList"
                       refQuery="tab-searchPromotion">
                    <thead>
                    <tr>
                        <th type="single" name="XH" style="display:none"></th>
                        <th fieldname="PROM_CODE">活动代码</th>
                        <th fieldname="PROM_NAME">活动名称</th>
                        <th fieldname="PROM_TYPE">活动类型</th>
                        <th fieldname="START_DATE">活动开始时间</th>
                        <th fieldname="END_DATE">活动结束时间</th>
                        <th fieldname="IF_TRANS_FREE">是否免运费</th>
                        <th fieldname="PROM_STATUS">促销活动状态</th>
                        <th fieldname="PUBLISHER">下发人</th>
                        <th fieldname="PUBLISH_TIME">下发时间</th>
                        <th colwidth="145" type="link" refer="showAnNiu" title="[下发]" action="doIssue">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>