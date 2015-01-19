<?xml version="1.0" encoding="UTF-8" ?>
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
    try
    {
        list = (List)request.getAttribute("itemslist");
        bType = (String)request.getAttribute("bType");
        bRow = (String)request.getAttribute("bRow");
        bCol = (String)request.getAttribute("bCol"); 
        System.out.println("错误列表-----------"+list.size());
        bType = (String)request.getAttribute("bType");
        errorflag = (String)request.getAttribute("errorflag"); 
		bParams = (String)request.getAttribute("bParams");
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
                    <input type="hidden" id="tmpNo" name="tmpNo" datasource="ROW_NO" operation="not in"/>
                </form>
                <form id="exportFm" method="post" >
                    <input type="hidden" id="seqs" name="seqs" datasource="ROW_NO" operation="in"/>
                </form>
                <div class="pageContent">
                    <div  style="width:100%;height:300px;overflow:auto;" id="div-fileImportResult">
                        <table style="display:none;width:100%;" id="tab-fileImportResult" name="tablist" ref="div-fileImportResult" pagerows="10">
                            <thead>
                                <tr>
									<th type="single" name="XH" style="display:none" colwidth="10" ></th>
									<th fieldname="VIN" >VIN</th>
									<th fieldname="ENGINE_NO" >发动机号</th>
									<th fieldname="MODELS_CODE" >车辆型号</th>
									<th fieldname="ENGINE_TYPE" >发动机型号</th>
									<th fieldname="USER_TYPE" >用户类型</th>
									<th fieldname="BUY_DATE" >购车日期</th>
									<th fieldname="FACTORY_DATE" >车辆出厂日期</th>
									<th fieldname="MAINTENANCE_DATE" >首保日期</th>
									<th fieldname="CERTIFICATEDATE" >合格发证日期</th>
									<th fieldname="GUARANTEE_NO" >保修卡号</th>
									<th fieldname="SALE_STATUS" >车辆销售状态</th>
									<th fieldname="LICENSE_PLATE" >车牌号码</th>
									<th fieldname="USER_NAME" >用户姓名</th>
									<th fieldname="USER_NO" >身份证号</th>
									<th fieldname="LINK_MAN" >联系人</th>
									<th fieldname="PHONE" >联系电话</th>
									<th fieldname="USER_ADDRESS" >地址</th>
									<th fieldname="INSIDECODE" >内部车型编号</th>
									<th fieldname="CONFIGURE" >车辆配置</th>
									<th fieldname="CONTRACTAREANO" >合同号</th>
									<th fieldname="PRODUCTLINECODE" >系列</th>
									<th fieldname="BLACKLISTFLAG" >黑名单</th>
								</tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                    <div class="buttonActive" id ="confirm"><div class="buttonContent"><button type="button" id="doConfirm" >确定导入</button></div></div>
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
                    <table class="dlist" style="width:100%;">
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
    var rowStrNo="<%=rowStrNo%>";
    $("#tmpNo").val(rowStrNo);
    $("#seqs").val(rowStrNo);
    var errorflag = "<%=errorflag%>";
    var bParams="<%=bParams%>";
    var diaSaveAction = "<%=request.getContextPath()%>/service/basicinfomng/VehicleDataAction";
    $(function(){
    	if (errorflag=="0") {
	    	var $f = $("#fm-fileImport");
	    	var sCondition = {};
	    	sCondition = $f.combined() || {};
	    	var url=diaSaveAction+"/searchImportVehicle.ajax";
	    	doFormSubmit($f,url,"",1,sCondition,"tab-fileImportResult");
    	}
    	
        $("#doConfirm").click(function() {
            //点击确定按钮，提交后台请求
            try{
            	 var url =diaSaveAction+"/VehicleImport.ajax?tmpNo="+rowStrNo;
           	     sendPost(url,"","",doConfirmCallBack,"true");
            }catch(e){}
        });

        $("#btn-importSuccessClose").click(function() {
            //点击确定按钮，提交后台请求
            try{
                var url = encodeURI("<%=request.getContextPath()%>/service/basicinfomng/VehicleDataAction/expData.do?seqs="+rowStrNo);
                //window.location.href = url;
                $("#exportFm").attr("action",url);
                $("#exportFm").submit();
            }catch(e){}
        });

    });

    function doConfirmCallBack(){
        $("#confirm").hide();
        parent.impCall();//调用父jsp方法
    }
    
</script>
</body>
</html>