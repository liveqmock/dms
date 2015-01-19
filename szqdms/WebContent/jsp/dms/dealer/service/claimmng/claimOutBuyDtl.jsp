<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    String contentPath = request.getContextPath();
%>
<div>
    <div class="page">
        <div class="pageHeader">
            <form method="post" id="fm-searchUnChkPart">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="tab-searchFile">
                    <input type="hidden" id="dia-relationId" name="dia-relationId" datasource="relationId" />
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
                            <th fieldname="NEW_PART_CODE">配件代码</th>
                            <th fieldname="NEW_PART_NAME">配件名称</th>
                            <th fieldname="NEW_SUPPLIER_CODE">配件供应商代码</th>
                            <th fieldname="NEW_SUPPLIER_NAME">配件供应商名称</th>
                            <th fieldname="WCSL">外采数量</th>
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
    var diaOutBuyAction = "<%=request.getContextPath()%>/service/claimmng/WorkOrderMngAction";
    $(function () {
        $("#btn-closePart").click(function () {
            $.pdialog.close(partSelWin);
            return false;
        });
        var searchFPartUrl = diaOutBuyAction+"/outBuyPartSearch.ajax?claimId="+$("#dia-claimId").val();
        var $f = $("");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, searchFPartUrl, "", 1, sCondition, "tab-fileList");
    });
    
  //提报回调方法
    function diaReportCallBack1(res)
    {
    	try
    	{
    		
    	}catch(e)
    	{
    		alertMsg.error(e);
    		return false;
    	}
    	return true;
    }
</script>