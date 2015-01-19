<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contentPath = request.getContextPath();
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="dia-layout">
	<div class="page">
		<form id="dia-fm-preview" name="dia-fm-preview" style="display:none;">
			<table id="dia-tab-previewCx" name="dia-tab-previewCx" >
				<tr>
					<td><input type="text" id="dia-in-sql" name="dia-in-sql" datatype="1,is_null,0" datasource="SQL"></input></td>
				</tr>
			</table>
		</form>
		<div class="pageContent">
		<div id="page_dia-tab-preview" >
			<table style="display:none" width="100%"  id="dia-tab-preview" name="tablist" ref="page_dia-tab-preview" refQuery=""dia-tab-previewCx"" >
					<thead>
						<tr>
						</tr>
					</thead>
					<tbody>
					</tbody>
			</table>
		</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>
<script type="text/javascript">
/**
 * 保存样例
 */
//初始化
$(function(){
	$.sa.preViewInit();
});
//弹出窗体
var diaDialog = $("body").data("preExeSql");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(diaDialog);
		return false;
	});
});

</script>