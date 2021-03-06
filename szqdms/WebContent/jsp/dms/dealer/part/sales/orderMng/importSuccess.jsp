<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@page import="com.org.framework.fileimport.ExcelErrors"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    List list = null;
    String bType,bRow,bCol;
    String rowStrNo="";
    String bParams="";
    String errorflag="";
    String flag="";
    try
    {
        bParams = (String)request.getAttribute("bParams");
        list = (List)request.getAttribute("itemslist");
        bType = (String)request.getAttribute("bType");
        bRow = (String)request.getAttribute("bRow");
        bCol = (String)request.getAttribute("bCol"); 
        flag = request.getParameter("flag"); 
        errorflag = (String)request.getAttribute("errorflag"); 
        System.out.println("错误列表-----------"+list.size());
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
        <div class="tabsContent" >
            <div class="page">
                <form id="fm-fileImport" method="post" >
                    <input type="hidden" id="tmpNo" name="tmpNo" datasource="A.ROW_NO" operation="not in"/>
                </form>
                <form id="exportFm" method="post" >
                    <input type="hidden" id="seqs" name="seqs" datasource="ROW_NO" operation="in"/>
                </form>
                <div class="pageContent">
                    <div  style="width:100%;height:300px;overflow:auto;" id="div-fileImportResult">
                        <table style="display:none;width:100%;" id="tab-fileImportResult" name="tablist" ref="div-fileImportResult" pagerows="10">
                            <thead>
                                <tr>
                                    <th type="single" name="XH" style="display:none"></th>
		                            <th fieldname="PART_CODE">配件代码</th>
		                            <th fieldname="PART_NAME">配件名称</th>
		                            <th fieldname="SUPPLIER_CODE" refer="showCode">供应商代码</th>
		                            <th fieldname="SUPPLIER_NAME" refer="showName">供应商名称</th>
		                            <th fieldname="REMARKS">备注</th>
		                            <th fieldname="COUNT">订购数量</th>
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
    var rowStrNo="<%=rowStrNo%>";
    $("#tmpNo").val(rowStrNo);
    $("#seqs").val(rowStrNo);
    var errorflag = "<%=errorflag%>";
    var searchUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/PartOrderReportAction/searchImportPart.ajax";
    $(function(){

    	if (errorflag=="0") {
	        var $f = $("#fm-fileImport");//获取页面提交请求的form对象
	        var sCondition = {};//定义json条件对象
	        sCondition = $f.combined() || {};
	        doFormSubmit($f,searchUrl,"",1,sCondition,"tab-fileImportResult");
    	}
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
                var insertUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/PartOrderReportAction/insertImportPart.ajax?tmpNo="+rowStrNo+"&flag="+<%=flag%>;
                sendPost(insertUrl+"&orderId="+<%=bParams%>, "", "", doConfirmCallBack, "true");
            }catch(e){}
        });

        // 点击导出按钮
        $("#btn-importSuccessClose").click(function() {
            try{
            	if($("#errorTab").find("tbody").find("tr").length==0){
                    alertMsg.warn("没有错误数据.");
                    return false;
                }
                var url = encodeURI("<%=request.getContextPath()%>/part/salesMng/orderMng/PartOrderReportAction/expData.do?seqs="+rowStrNo);
                $("#exportFm").attr("action",url);
                $("#exportFm").submit();
            }catch(e){}
        });

        //错误数据导出
        $("#btn-exportError1").bind("click",function(){
            try{
                
            }catch(e){}
        });

    });

    function doConfirmCallBack(res){
    	var rows = res.getElementsByTagName("ROW");
        if(rows && rows.length>0){
            var amount = getNodeText(rows[0].getElementsByTagName("ORDER_AMOUNT").item(0));
            parent.$("#diaOrderAmount").val(amount);
            parent.$("#divOrderAmount").html(amountFormatNew(amount)+"元");
            parent.$("#divOrderAmount1").html(amountFormatNew(amount)+"元");
            parent.$("#divOrderAmount2").html(amountFormatNew(amount)+"元");
        }
    	var dialog = parent.$("body").data("importXls");
    	parent.orderPartSearch();
    	parent.doSearch();
    }
    function showCode(obj){
    	$tr = $(obj).parent();
    	if($tr.attr("IF_SUPLY")==<%=DicConstant.SF_01%>){
    		return $tr.attr("SUPPLIER_CODE");
    	}else{
    		return "";
    	}
    }
	function showName(obj){
		$tr = $(obj).parent();
    	if($tr.attr("IF_SUPLY")==<%=DicConstant.SF_01%>){
    		return $tr.attr("SUPPLIER_NAME");
    	}else{
    		return "";
    	}
    }
</script>
</body>
</html>