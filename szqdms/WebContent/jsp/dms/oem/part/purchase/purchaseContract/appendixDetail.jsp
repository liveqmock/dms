<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    String contentPath = request.getContextPath();
    String DETAIL_ID = request.getParameter("DETAIL_ID");
%>
<div>
    <div class="page">
        <div class="pageHeader">
            <form method="post" id="fm-searchUnChkPart">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="tab-searchFile">
                    <input type="hidden" id="dia-DETAIL_ID" name="dia-DETAIL_ID" datasource="DETAIL_ID" />
                    </table>
                </div>
            </form>
        </div>
        <div class="pageContent">
            <div id="div-partList">
                <table style="display:none;width:100%;" id="tab-fileList" multivals="div-selectedPart" name="tablist" ref="div-partList"
                       refQuery="tab-searchFile">
                    <thead>
                        <tr>
                        	<th type="single" name="XH" style="display:none;"></th>
                            <th fieldname="FJMC">附件名称</th>
                            <th fieldname="CREATE_TIME">上传时间</th>
                            <th fieldname="CREATE_USER">上传人</th>
                            <th colwidth="140" type="link" title="[下载]"  action="doFjdownLoad" >操作</th>
                       </tr>
                    </thead>
                </table>
            </div>
            <div>
				<form id="dialog-fm-download"style="display:none">
				</form>
			</div>
        </div>
    </div>
</div>
<script type="text/javascript">
    //弹出窗体
    var partSelWin = $("body").data("partSelWin");
    var furl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseContract/ContractAppendixAduitManageAction";
    var detailid = <%=DETAIL_ID%>
    $(function () {
        $("#btn-closePart").click(function () {
            $.pdialog.close(partSelWin);
            return false;
        });
        var searchFPartUrl = furl+"/contractAppendixSearch.ajax?DETAIL_ID="+detailid;
        var $f = $("#fm-searchUnChkPart");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, searchFPartUrl, "", 1, sCondition, "tab-fileList");
    });
    function doFjdownLoad(obj)
    {
    	var fjid = $(obj).attr("FID");
    	var fjmc = $(obj).attr("FJMC");
    	var wjjbs = $(obj).attr("WJJBS");
    	var blwjm = $(obj).attr("BLWJM");
    	$.filestore.download({"fjid":fjid,"fjmc":fjmc,"wjjbs":wjjbs,"blwjm":blwjm});
    }
</script>