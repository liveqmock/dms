<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@page import="com.org.framework.fileimport.ExcelErrors"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
/* 	String bType="",bParams="";
	try
	{
		bType = (String)request.getAttribute("bType");
		bParams = (String)request.getAttribute("bParams");
	}catch(Exception e)
	{}
	 */
	 List list = null;
	    String bType,bRow,bCol;
	    String rowStrNo="";
	    String errorflag="";
	    try
	    {
	        list = (List)request.getAttribute("itemslist");
	        bType = (String)request.getAttribute("bType");
	        bRow = (String)request.getAttribute("bRow");
	        bCol = (String)request.getAttribute("bCol"); 
	        errorflag = (String)request.getAttribute("errorflag"); 
	    }catch(Exception e)
	    {e.printStackTrace();}
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>成功列表</title>
</head>
<body>
	  <div class="tabs" eventType="click" >
		  <div class="tabsHeader">
            <div class="tabsHeaderContent">
                <ul>
                    <li><a href="javascript:void(0)"><span>已验证通过列表</span></a></li>
                    <li><a href="javascript:void(0)"><span>未验证通过列表</span></a></li>
                </ul>
            </div>
        </div>
		<div class="pageContent">		
		 <div class="tabsContent" >
            <div class="page">
                <form id="fm-fileImport" method="post" >
                    <input type="hidden" id="rowNum" name="rowNum" datasource="ROW_NUM" operation="not in"/>
                </form>
                <form id="exportFm" method="post" >
                    <input type="hidden" id="seqs" name="seqs" datasource="ROW_NUM" operation="in"/>
                </form>
                <div class="pageContent">
                    <div  style="width:100%;height:300px;overflow:auto;" id="div-fileImportResult">
                        <table style="display:none;width:100%;" id="tab-fileImportResult" name="tablist" ref="div-fileImportResult" pagerows="10">
                            <thead>
                                <tr>
                                    <th type="single" name="XH" style="display:none"></th>
								<th fieldname="RULE_CODE" >规则代码</th>
							<th fieldname="RULE_NAME" >规则名称</th>
							<th fieldname="PART_CODE" >配件代码</th>
							<th fieldname="PART_NAME" >配件名称</th>
							<th fieldname="MONTHS" >月份</th>
							<th fieldname="MILEAGE" >里程</th>
                                </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                   <div class="buttonActive"><div class="buttonContent"><button type="button" id="doConfirm" >确定导入</button></div></div>
                    <div class="button" style="float:right;"><div class="buttonContent"><button class="close" onclick="javascript:history.go(-1);" type="button">返&nbsp;&nbsp;回</button></div></div>
            </div>
            </div>
            <div class="page" >
                <%
                    if(list != null)
                    {
                %>    <div class="page">
                    <div class="pageContent">
                    <div  style="width:100%;height:300px;overflow:auto;">
                    <table class="dlist" style="width:100%;" id="errorTab">
                        <thead>
                            <tr>
                               <th>序号</th>
                               <th>错误列表</th>
                            </tr>
                        </thead>
                        <tbody>
                        
                <%        
                        int len = list.size();
                        for(int i=0;i<len;i++)
                        {
                            ExcelErrors s = new ExcelErrors();
                            out.println("<tr style='height:20px;line-height:20px;'>");
                            s = (ExcelErrors)list.get(i);
                            System.out.println(s.getErrorDesc());
                            out.println("<td style='width:120px;'><div>"+"第["+s.getRowNum()+"]行"+"</div></td>");
                            out.println("<td><div>"+s.getErrorDesc()+"</div></td>");
                            out.println("</tr>");
                            rowStrNo += rowStrNo.length()!=0?"," + s.getRowNum():s.getRowNum();
                            //只输出前1000个错误
                            if(i>1000)
                                break;
                        }
                        System.out.println(rowStrNo);
                %>
                        </tbody>
                    </table>
                <%    
                    }
                %>
                    </div>
                    <div class="button" style="float:right;"><div class="buttonContent"><button class="close" id="btn-importSuccessClose" type="button">导出错误数据</button></div></div>
                    </div>
                    </div>
            </div><div class="tabsFooter">
                <div class="tabsFooterContent"></div>
            </div>
        </div>
	</div>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/basicinfomng/RulePartMngAction/searchImport.ajax";
var rowStrNo="<%=rowStrNo%>";
$("#rowNum").val(rowStrNo);
$("#seqs").val(rowStrNo);
var errorflag = "<%=errorflag%>";
$(function(){

	var $f = $("#fm-fileImport");//获取页面提交请求的form对象
	var sCondition = {};//定义json条件对象
	sCondition = $f.combined() || {};
	doFormSubmit($f,searchUrl,"",1,sCondition,"tab-fileImportResult");
	
	
    // 点击确定按钮
    $("#doConfirm").click(function() {
        try{
            if($("#tab-fileImportResult_content").find("tr").size()==0){
            	alertMsg.warn("没有正确数据.");
                return false;
            } else if($("#tab-fileImportResult_content").find("tr").size() == 1) {
                if($("#tab-fileImportResult_content").find("tr").eq(0).find("td").size() == 1) {
                	alertMsg.warn("没有正确数据.");
                    return false;
                }
            }
            var url = "<%=request.getContextPath()%>/service/basicinfomng/RulePartMngAction/insertImport.ajax?errorDataRowNum="+rowStrNo;
    		sendPost(url,"delete","",deleteCallBack,"true");
        }catch(e){}
    });

    // 点击导出按钮
    $("#btn-importSuccessClose").click(function() {
        try{
        	if($("#errorTab").find("tbody").find("tr").length==0){
                alertMsg.warn("没有错误数据.");
                return false;
            }
            var url = encodeURI("<%=request.getContextPath()%>/service/basicinfomng/RulePartMngAction/expTmpErrorData.ajax?errorDataRowNum="+rowStrNo);
            //window.location.href = url;
            $("#exportFm").attr("action",url);
            $("#exportFm").submit();
        }catch(e){}
    });
});
<%-- function doConfirm()
{
	//点击确定按钮，提交后台请求
	try{
		var url = "<%=request.getContextPath()%>/service/basicinfomng/RulePartMngAction/insertImport.ajax";
		sendPost(url,"delete","",deleteCallBack,"true");
	}catch(e){}
}
 --%>
//删除回调方法
function  deleteCallBack(res)
{
	try
	{
	    parent.$("#btn-search").trigger("click");
	    var dialog = parent.$("body").data("importXls");
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
</script>
</body>
</html>