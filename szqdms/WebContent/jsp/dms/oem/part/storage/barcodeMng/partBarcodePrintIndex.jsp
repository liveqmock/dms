<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <jsp:include page="/head.jsp"/>
    <title>配件条码打印</title>
    <script type="text/javascript">
        var searchUrl = "<%=request.getContextPath()%>/part/storageMng/barcodeMng/PartBarcodePrintAction/searchPart.ajax";
        //定义弹出窗口样式
        var diaEditOptions = {max: true, width: 720, height: 430, mask: true, mixable: true, minable: true, resizable: true, drawable: true};
        //初始化
        $(function () {
            //查询按钮响应
            $("#btn-search").bind("click", function (event) {
                var $f = $("#fm-searchPart");
                var sCondition = {};
                sCondition = $f.combined() || {};
                doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-partList");
            });
        });
        var row = {};
        //列表打印连接
        function doPrint(rowobj) {
            row = $(rowobj);
            $("td input[type=radio]", row).attr("checked", true);
            
            // 获取用户输入的打印数
            var inputNum = $("input[type=text]",row).val();
            
            if( !inputNum ){
            	alertMsg.warn("请正确输入打印数量！");
    	        return false;
            }
            window.open(webApps + "/jsp/dms/oem/part/storage/barcodeMng/partBarcodePrintEdit2.jsp?PART_ID="+$(rowobj).attr('PART_ID')+"&SUPPLIER_ID="+$(rowobj).attr('SUPPLIER_ID')+"&CS=" + inputNum, "newwindow", "top=0, left=0, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no");
        }

        function ctrlShow(obj){
            var $tr =$(obj).parent();
            if($tr.attr('SUPPLIER_CODE')=='9XXXXXX'){
                return "<div></div>";
            }else{
                return $(obj).text();
            }
        }
        function toAppend(obj){
        	var $tr =$(obj).parent();
        	return $tr.attr("MIN_PACK")+"/"+$tr.attr("MIN_UNIT_sv");
        }
        function createInput(obj){
    		return "<input type='text' style='width:50px;' name='myCount' onblur='doMyCountBlur(this)' maxlength='2'/>";
    	}
        function doMyCountBlur(obj){
    		var $obj = $(obj);
    	    if($obj.val() == "")
    	        return false;
    	    if($obj.val() && !isCount($obj))
    	    {
    	        alertMsg.warn("请正确输入打印数量！");
    	        return false;
    	    }
    	    $("#CS").val($obj.val());
    	}
        function isCount($obj)
    	{
    	    var reg = /^\+?[1-9][0-9]*$/;
    	    if(reg.test($obj.val()))
    	    {
    	        return true;
    	    }else
    	    {
    	        return false;
    	    }
    	}
    </script>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif"/>&nbsp;当前位置：
        配件管理 &gt; 仓储管理 &gt; 条码管理 &gt; 配件条码打印</h4>

    <div class="page">
        <div class="pageHeader">
            <!-- 提交查询请求form -->
            <form method="post" id="fm-searchPart">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="tab-searchPart">
                    <input type="hidden" id="CS" name="CS" action = "show" />
                        <!-- 定义查询条件 -->
                        <tr>
                            <td><label>配件代码：</label></td>
                            <td><input type="text" id="PART_CODE" name="PART_CODE" datasource="PART_CODE" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>配件名称：</label></td>
                            <td><input type="text" id="PART_NAME" name="PART_NAME" datasource="PART_NAME" datatype="1,is_null,30" operation="like"/></td>
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
            <div id="div-partList">
                <table style="display:none;width:100%;" id="tab-partList" name="tablist" ref="div-partList"
                       refQuery="tab-searchPart">
                    <thead>
                    <tr>
                        <th type="single" name="XH" style="display:none"></th>
                        <th fieldname="PART_CODE">配件代码</th>
                        <th fieldname="PART_NAME" maxlength="40">配件名称</th>
                        <th fieldname="PART_NO" style="display: none">配件图号</th>
                        <th fieldname="UNIT">单位</th>
                        <th fieldname="MIN_PACK" colwidth="80" refer="toAppend">最小包装</th>
                        <th fieldname="SUPPLIER_CODE">供应商代码</th>
                        <th fieldname="SUPPLIER_NAME" refer="ctrlShow">供应商名称</th>
                        <th fieldname="SUPPLIER_NAME" refer="createInput">打印次数</th>
                        <th colwidth="50" type="link" title="[打印]" action="doPrint">操作</th>
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