<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- 
	 Title:提醒通用查看信息页
	 Version:2.0
     Collator：xukx0710@icloud.com
     Date：2013-03
     Remark：You can copy and/or use and/or modify this program free,
             but please reserve the segment above.
             Please mail me if you have any question.
-->
<div style="width:100%;height:100%;">
	<div class="page">
	<div class="pageContent">
	<form method="post"  class="editForm" >
		<div align="left">
		<input type="hidden" datasource="XH" ></input>
		<table class="editTable" >
			<tr>
			    <td><label>标题：</label></td>
			    <td><div datasource="TITLE" ></div></td>
			</tr>
			<tr><td><label>内容：</label></td>
			    <td><div datasource="DESC"></div></td>
			</tr>
			<tr>
				<td><label>发送人：</label></td>
			    <td><div datasource="CREATE_USER"></div></td>
			</tr>
			<tr>
				<td><label>发送部门：</label></td>
			    <td><div datasource="CREATE_ORGID"></div></td>
			</tr>
			<tr> 
			    <td><label>发送时间：</label></td>
			    <td><div datasource="CREATE_TIME"></div></td>
			</tr>
		</table>
		</div>
	</form>
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button class="close" type="button" >关&nbsp;&nbsp;闭</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" id="dia-btn-alertInfoNoAlert">不再提醒</button></div></div></li>
		</ul>
	</div>
	</div>
	</div>
</div>
<%
	String xh = request.getParameter("xh");
	if(xh == null) xh = "";
%>
<script type="text/javascript">
//查询提交方法
var searchDiaAlertInfo = "<%=request.getContextPath()%>/AlertInfoAction/searchAlertInfo.ajax";
var curDialog = $.pdialog.getCurrent();
$(function()
{
	$("#dia-btn-alertInfoNoAlert").bind("click",function(){
		var setDiaAlertFinish = "<%=request.getContextPath()%>/AlertInfoAction/setAlertFinish.ajax?xh=<%=xh%>";
		try
		{
		    $.ajax({
		        type: 'POST',
		        url:setDiaAlertFinish,
		        dataType:"xml",
		        data:{},
		        cache: false,
		        success: function(responseText){
		        			$.pdialog.close(curDialog);
		        		}
			});
		}catch(e){alert(e);}	
	});
	
	searchDiaAlertInfo += "?xh=<%=xh%>";
	try
	{
		//查询数据
	    $.ajax({
	        type: 'POST',
	        url:searchDiaAlertInfo,
	        dataType:"xml",
	        data:{},
	        cache: false,
	        success: function(responseText){
	                    var res = responseText.documentElement;
	                    //alert(res.xml);
	                    var titleNode = res.getElementsByTagName("ALERT_TITLE").item(0);
	                    $("div[datasource='TITLE']:first",curDialog).text(titleNode.text?titleNode.text:titleNode.textContent);
	                    var descNode = res.getElementsByTagName("DESR").item(0);
	                    $("div[datasource='DESC']:first",curDialog).text(descNode.text?descNode.text:descNode.textContent);
	                    var userNode = res.getElementsByTagName("CREATE_USER").item(0);
	                    $("div[datasource='CREATE_USER']:first",curDialog).text(userNode.getAttribute("sv"));
	                    var orgNode = res.getElementsByTagName("CREATE_ORGID").item(0);
	                    $("div[datasource='CREATE_ORGID']:first",curDialog).text(orgNode.getAttribute("sv"));
	                    var timeNode = res.getElementsByTagName("CREATE_TIME").item(0);
	                    $("div[datasource='CREATE_TIME']:first",curDialog).text(timeNode.getAttribute("sv"));
	                    
	        		}
		});
	}catch(e){alert(e);}
});
</script>