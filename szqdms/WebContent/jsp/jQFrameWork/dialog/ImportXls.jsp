<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.util.Pub"%>
<%
	String bType = Pub.val(request, "bType");
	String bParams = Pub.val(request, "bParams");
	String sCol = Pub.val(request, "maxCol");
	String sRow = Pub.val(request, "blankRows");
	String bUrl = Pub.val(request, "bUrl");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>上传管理</title>
</head>
<body>
<!-- 
	 Title:excel导入
	 Version:1.0
     Collator：andy.ten@tom.com
     Date：2012-01
     Remark：You can copy and/or use and/or modify this program free,
             but please reserve the segment above.
             Please mail me if you have any question.
-->

<div id="uploadXlsDiv" width="100%">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<div class="page">
	<div class="pageContent">
		<form method="post" name="ImportXlsForm" ENCTYPE="multipart/form-data" accept-charset="UTF-8" class="editForm" id="uploadForm">
			<input name="maxCol" id="sCol" type="hidden" value="<%=sCol%>"/>
			<input name="blankRows" id="sRow" type="hidden" value="<%=sRow%>" />
			<input name="bType" id="bType" type="hidden" value="<%=bType%>" />
			<input name="bUrl" id="bUrl" type="hidden" value="<%=bUrl%>" />
			<input name="bParams" id="bParams" type="hidden" value="<%=bParams%>" />
			<table class="editTable" id="uploadTable">
			   <tr>
			      <td ><label> 请选择要导入的文件:</label></td>
			      <td >
			          <input type="file" name="myfile" id="myfile" fileExt="*.jpg;*.jpeg;*.gif;*.png;" fileDesc="*.jpg;*.jpeg;*.gif;*.png;"style="height:20px;width:200px;"/>
			          
			      </td>
			   </tr>
			   <tr>
			   <td style="font-size:12px;font-family:宋体;background:#D6DEFF;letter-spacing:2px;margin-left:5px;" height="20px">
			    <label>备注：</label>
			   </td>
					<td align="left"  style="font-size:12px;font-family:宋体;background:#D6DEFF;letter-spacing:2px;margin-left:5px;" height="20px">
								  <label>  允许导入附件类型：*.xls，*.xlsx，*.csv</label>
				 </td>
			</tr>	   
			</table>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button"  id="uploadXls">上传</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关闭</button></div></div></li>
			</ul>
		</div>
		<br/>
	</div>
	
	</div>
</div>
<script type="text/javascript">
$(function(){
	$("#uploadXls").bind("click",function(){
		var file = $("#myfile").val();
		var upButton = $(this);
		var type = file.substring(file.lastIndexOf(".") + 1);
		if ($("#myfile").val() != "") 
		{
			if (type != null
					&& (type.toLowerCase().indexOf("csv") == 0
							|| type.toLowerCase().indexOf("xlsx") == 0
							|| type.toLowerCase().indexOf("xls") == 0
							&& "csv".indexOf(type) == 0 || "xls".indexOf(type) == 0)) 
			{
				upButton.disabled = true;
				importFile("<%=bType%>","<%=bParams%>","<%=sCol%>","<%=sRow%>");
			} else 
			{
				alertMsg.info("导入文件格式错误！");
			}
		} else 
		{
			alertMsg.info("导入文件不能为空！");
		}
	});
});
var uploadUrl = "importXls.do";
function importFile()
{
	 document.ImportXlsForm.action = "<%=request.getContextPath() %>/FileImportAction/importFile.do";
	 document.ImportXlsForm.submit();
}
//弹出窗体
var dialog = parent.$("body").data("importXls");
$(function()
{
	$("button.close").click(function(){
		parent.$.pdialog.close(dialog);
		return false;
	});
});
</script>
</body>
</html>