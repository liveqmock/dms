<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html
xmlns:v="urn:schemas-microsoft-com:vml"
xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">
<!-- 
	 Title:系统框架空工程
	 Version:1.0
     Collator：andy.ten@tom.com
     Date：2011-10
     Remark：You can copy and/or use and/or modify this program free,
             but please reserve the segment above.
             Please mail me if you have any question.
-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name=ProgId content=Excel.Sheet />
<meta name=Generator content="Microsoft Excel 11" />
<!--[if !mso]>
<style>
v\:* {behavior:url(#default#VML);}
o\:* {behavior:url(#default#VML);}
x\:* {behavior:url(#default#VML);}
.shape {behavior:url(#default#VML);}
</style>
<![endif]--><!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Author>andy.ten</o:Author>
  <o:LastAuthor>andy.ten</o:LastAuthor>
  <o:Created>2013-07-10</o:Created>
  <o:LastSaved>2013-07-10</o:LastSaved>
  <o:Version>1.0</o:Version>
 </o:DocumentProperties>
</xml><![endif]-->
<jsp:include page="/jsp/jQFrameWork/sa/head1.jsp" />
<link type="text/css" rel="stylesheet" href="<%=contextPath %>/lib/plugins/sa/css/popmenu.css"></link>
<link type="text/css" rel="stylesheet" href="<%=contextPath %>/lib/plugins/sa/css/sa.define.css"></link>
<script src="<%=contextPath %>/lib/core/sci.tree.js" type="text/javascript"></script>
<link type="text/css" rel="stylesheet" href="<%=contextPath %>/lib/plugins/sa/spectrum/spectrum.css"></link>
<script src="<%=contextPath %>/lib/plugins/sa/spectrum/spectrum.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/plugins/sa/sa.core.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/plugins/sa/contextMenu.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/plugins/sa/sa.freetable.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/plugins/sa/sa.editfield.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/plugins/sa/sa.menuevent.js" type="text/javascript"></script>
<title>统计分析定义</title>
<script type="text/javascript">
var $tabs = null;
$(function(){
	$.sa.init();
});
function afterDicItemClick(id,selIndex)
{
	var ret =  true;
	switch(id)
	{
		case "in-ds":
				$("#in-ds-user").attr("src","T#SA_USER:USER_CODE:USER_NAME:USER_DS='"+$("#"+id).attr("code")+"'");
			break;
		case "in-firMenu":
			$("#in-secMenu").attr("src","T#EAP_MENU:NAME:TITLE:PARENT='"+$("#"+id).attr("code")+"' ORDER BY ORDERNO ");
			break;
		case "dia-in-fieldName":
			if(selIndex && selIndex>0)
				$("#dia-in-fieldName").attr("colIndex",parseInt(selIndex-1));
			break;
	}
	return ret;
}
//回调函数
var $curCell = null;
var allFieldRes = null;
var fieldRes = null;
var rows = 0,cols = 0;
var x = 0,y=0;
var str;
var preSql = "";

</script>
</head>
<body style="background-color:#fff">
<div id="layout">
	<h2 class="contentTitle">统计分析定制<span style="font-weight:normal;"> 版本1.0：灵活定义统计分析表，支持多数据源选择，表样灵活定制</span></h2>
	<div class="pageContent">
		<form id="fm-sa" name="fm-sa" style="display:none;">
			<input type="text" id="in-sql" name="in-sql" datatype="1,is_null,0" datasource="SQL"/>
		</form>
		<div class="tabs" currentIndex="0" eventType="click">
			<div class="tabsHeader">
				<div class="tabsHeaderContent">
					<ul>
						<li><a href="javascript:void(0);"><span>数据源</span></a></li>
						<li><a href="javascript:void(0);"><span>数据定义</span></a></li>
						<li><a href="javascript:void(0);"><span>表样定义</span></a></li>
						<li><a href="javascript:void(0);"><span>检索条件定义</span></a></li>
						<li><a href="javascript:void(0);"><span>生成报表</span></a></li>
					</ul>
				</div>
			</div>
			<div class="tabsContent">
				<!-- ds -->
				<div id="div-ds"  style="height:auto;overflow:hidden;">
					<table class="editTable" id="tab-source">
						<tr>
						    <td><label>数据源：</label></td>
						    <td><input type="text" id="in-ds"  name="in-ds" datasource="DS" kind="dic" src="T#SA_DS:DS_CODE:DS_NAME" dicWidth="260" datatype="0,is_null,30" value=""/>
						    </td>
						</tr>
						<tr><td><label>数据源用户：</label></td>
						    <td><input type="text" id="in-ds-user" name="in-ds-user" datasource="DS_USER" kind="dic" dicWidth="260" datatype="0,is_null,30" value=""/></td>
						</tr>
						<tr><td></td></tr>
					</table>
					<div class="formBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" name="btn-next">下&nbsp;一&nbsp;步</button></div></div></li>
						</ul>
					</div>
					<div>
						备注：<br/><br/>
						支持通过JDBC方式跨数据库访问，能够访问不同服务器的数据库数据.
					</div>
				</div>
				<!-- ds-table -->
				<div id="div-tab" class="" style="height:auto;overflow:hidden;">
					<div style="width:100%;height:300px;margin:10px 10px 10px 40px;">
						<table style="width:80%;height:95%;">
							<tr style="line-height:40px;height:40px;">
								<td>&nbsp;</td>
								<td style="float:right;">
									<ul >
										<li style="float:left;padding:0px 15px 5px 0px;height:23px;line-height:23px;"><input type="radio" name="radios" value="1" checked="true">自定义SQL</input></li>
										<li style="float:left;padding:0px 15px 5px 0px;height:23px;line-height:23px;"><input type="radio" name="radios" value="2">调用存储过程</input></li>
										<li style="float:left;padding:0px 0px 10px 0px;height:23px;line-height:23px;"><div class="button"><div class="buttonContent"><button type="button" name="btn-guide">向导配置</button></div></div></li>
									</ul>	
								</td>
							</tr>
							<tr>
								<td><div style="float:right;padding-right:30px;">SQL:</div></td>
								<td style="width:98%;height:70%;"><textarea id="ta-sql" name="ta-sql" style="width:100%;height:100%;overflow:auto" expand="false" datatype="1,is_null,0" datasource="ESQL"></textarea></td>
							</tr>
							<tr style="line-height:40px;height:40px;">
								<td>&nbsp;</td>
								<td>
									<ul>
										<li style="float:right;margin-right:0px;padding:10px 0px 10px 0px;height:23px;line-height:23px;"><div class="button"><div class="buttonContent"><button type="button" id="btn-insert" name="btn-insert">插入参数</button></div></div></li>
									</ul>
									<ul>
										<li style="float:left;padding:10px 15px 5px 0px;height:23px;line-height:23px;">[内置参数]：</li>
										<li style="float:left;padding:10px 15px 5px 0px;height:23px;line-height:23px;"><input type="radio" name="p-radios" />系统时间</li>
										<li style="float:left;padding:10px 15px 5px 0px;height:23px;line-height:23px;"><input type="radio" name="p-radios" />登录用户帐号</li>
										<li style="float:left;padding:10px 15px 5px 0px;height:23px;line-height:23px;"><input type="radio" name="p-radios" />登录用户部门</li>
									</ul>	
								</td>
							</tr>
						</table>
						<br/>
					</div>
					<div class="formBar">
						<ul>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-preView" name="btn-preView">预览执行</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" name="btn-pre">上&nbsp;一&nbsp;步</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" name="btn-next" id="btn-next1">下&nbsp;一&nbsp;步</button></div></div></li>
						</ul>
					</div>
					<div>
						备注：<br></br>
						报表SQL语句，建议在配置报表前，点击[预览执行]按钮，验证sql是否正确.<br></br>
						内置参数：sql语句中涉及变量，使用"#变量名#"方式代替.
					</div>
				</div>
				<div id="div-define">
					<div style="width:100%;height:300px;background-color:#fff">
						<div style="float:left;width:62%;height:300px;text-align:center;overflow-x:auto;overflow-y:hidden;">
							<table id="freetable" cellspacing="0" cellpadding="0" border="1px" style="width:100%;" >
								<tr>
									<td id="freeTitle" type="T" height="30px" style="text-align:center;font-size: 13pt; font-weight: bold;">标题</td>
								</tr>
								<tr>
									<td align="center">
									   <table id="freeST" align="left" border="1px" cellspacing="0" cellpadding="1" width="100%">
										 <tr><td x=0 y=0 type="H" rowSpan=1 colSpan=1 align="center" valign="middle" style="width:200px;background-color:#f5f3f3">&nbsp;</td>
										     <td x=1 y=0 type="X" >&nbsp;</td>
										     <td x=2 y=0 type="X" >&nbsp;</td>
										 </tr>
										 <tr><td x=0 y=1 type="Y" >&nbsp;</td>
									     	 <td x=1 y=1 type="S" >&nbsp;</td>
									     	 <td x=2 y=1 type="S" >&nbsp;</td>
									     </tr>
									   </table>
									</td>
								</tr>
							</table>
						</div>
						<fieldset style="float:right;width:380px;height:285px;background:#f8f7f4;">
						<legend align="left">&nbsp;字段属性</legend>
							<div style="display:block;margin-top:20px;background:#f8f7f4; margin-left:1px;overflow:hidden; width:380px; height:260px; border:solid 0px #CCC; line-height:21px;">
								<table class="editTable" id="tab-editFields" style="width:380px;" layoutH="260">
									<tr>
										<td><label>显示名称：</label></td>
				    					<td colspan="3"><input type="text" class="middleInput" style="width:260px;" id="in-showName" name="in-showName" datasource="SHOW_NAME" datatype="1,is_null,20" value="" readonly="true"/></td>
									</tr>
									<tr>
										<td><label>字段定义：</label></td>
									    <td colspan="3"><input type="text" class="middleInput" style="width:260px;" id="in-fieldName"  name="in-fieldName" datasource="FIELD_NAME" kind="dic"  datatype="1,is_null,60" value="" readonly="true"/></td>
									</tr>
									<tr>
										<td><label>对齐方式：</label></td>
									    <td style="width:60px;">
									    	<input type="text" class="middleInput" style="width:60px;" id="in-fieldAlign"  name="in-fieldAlign" datasource="FIELD_ALIGN"  datatype="1,is_null,60" value="" readonly="true"/>
									    </td>
									    <td><ul>
									    		<li style="float:left;padding-top:5px;">宽度：</li>
									    		<li style="float:left;"><div><input type="text" style="width:30px;" id="in-fieldWidth"  name="in-fieldWidth" datasource="FIELD_WIDTH" datatype="1,is_digit,60" value="0" readonly="true"/><span>px</span></div></li>
									    		<li style="float:left;padding-top:5px;padding-left:10px;">高度：</li>
									    		<li style="float:left;"><div><input type="text" style="width:30px;" id="in-fieldHeight" name="in-fieldHeight" datasource="FIELD_HEIGHT" datatype="1,is_digit,20" value="0" readonly="true"/><span>px</span></div></li>
									    	</ul>
				    					</td>
									</tr>
									<tr>
									    <td ><label>公式定义：</label></td>
									    <td colspan="3">
									    	<textarea style="width:260px;height:30px;overflow:auto;" id="in-fieldFunc"  name="in-fieldFunc" datasource="FIELD_FUNC" datatype="1,is_null,60" value="" readonly="true"></textarea>
									    </td>
									</tr>
									<tr>
									    <td ><label>字典设置：</label></td>
									    <td colspan="3">
									    	<textarea style="width:260px;height:60px;overflow:auto;" id="in-fieldDict"  name="in-fieldDict" datasource="FIELD_DICT"  datatype="1,is_null,60" value="" readonly="true"></textarea>
									    </td>
									</tr>
								</table>
							</div>
						</fieldset>
					</div>	
					<div class="formBar">
						<div style="float:left;padding:4px 0px 2px 5px;" ><span style="color:red;font-weight:bolder">报表种类：</span>
							<input type="radio" name="in-reportStyle" checked="true" value="1" disabled="true">标准报表</input>&nbsp;&nbsp;
							<input type="radio" name="in-reportStyle" value="2" disabled="true">交叉报表</input>
						</div>
						<ul>
							<li><div class="button"><div class="buttonContent"><button type="button" name="btn-pre">上&nbsp;一&nbsp;步</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" name="btn-next" id="btn-next2">下&nbsp;一&nbsp;步</button></div></div></li>
						</ul>
					</div>
					<div>
						备注：<br/><br/>
						交叉报表:sql列和表样列顺序必须保持一致<br/><br/>
						交叉报表:sql列和表样列顺序必须保持一致
					</div>
				</div>
				<div id="div-scond">
							
					<div class="formBar">
						<ul>
							<li><div class="button"><div class="buttonContent"><button type="button" name="btn-pre">上&nbsp;一&nbsp;步</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" name="btn-next" id="btn-next3">下&nbsp;一&nbsp;步</button></div></div></li>
						</ul>
					</div>
					<div>
						备注：<br/>
						数据源有2种，一种是通过JDBC方式访问的数据源，另一种是通过数据库直接访问的数据源（dblink或跨用户方式）；
					</div>
				</div>
				<div id="div-save">
					<form method="post" id="fm-saveReport" class="editForm" >
						<input type="hidden" id="ds" name="ds" datasource="DS" datatype="1,is_null,30" />
						<input type="hidden" id="reportDOM" name="reportDOM" action="show" datasource="REPORT_DOM" datatype="1,is_null,30"/>
						<input type="hidden" id="reportSQL" name="reportSQL" action="show" datasource="REPORT_SQL" datatype="1,is_null,30"/>
						<input type="hidden" id="reportType" name="reportType" datasource="REPORT_TYPE" datatype="1,is_null,30"/>
						<input type="hidden" id="rcColumns" name="rcColumns" datasource="RC_COLUMNS" datatype="1,is_null,30" />
						<input type="hidden" id="sumColumns" name="sumColumns" datasource="SUM_COLUMNS" datatype="1,is_null,30" />
						<input type="hidden" id="uColIndex" name="uColIndex" datasource="U_COLINDEX" datatype="1,is_null,30" />
						<table class="editTable" id="tab-saveReport" >
							<tr>
								<td><label>报表名称：</label></td>
		    					<td colspan="3"><input type="text" style="width:96%" id="in-reportName" name="in-reportName" datasource="REPORT_NAME" datatype="0,is_null,100" value=""/></td>
							</tr>
							<tr>
								<td><label>菜单代码：</label></td>
		    					<td><input type="text" id="in-menuName" name="in-menuName" datasource="MENU_NAME" datatype="0,is_null,30" value=""/></td>
		    					<td><label>菜单名称：</label></td>
		    					<td><input type="text" id="in-menuTitle" name="in-menuTitle" datasource="MENU_TITLE" datatype="0,is_null,30" value=""/></td>
							</tr>
							<tr>
								<td><label>一级菜单：</label></td>
		    					<td><input type="text" id="in-firMenu" name="in-firMenu" datasource="FIR_MENU" kind="dic" src="T#EAP_MENU:NAME:TITLE:PARENT IS NULL ORDER BY ORDERNO" datatype="0,is_null,30" value=""/></td>
		    					<td><label>上级菜单：</label></td>
		    					<td><input type="text" id="in-secMenu" name="in-secMenu" datasource="SEC_MENU" kind="dic" datatype="0,is_null,30" value=""/></td>
							</tr>
							<tr>
								<td><label>显示序号：</label></td>
		    					<td><input type="text" id="in-showOrder" name="in-showOrder" datasource="ORDER_NO" datatype="0,is_null,30" value=""/></td>
		    					<td><label>英文名称：</label></td>
		    					<td><input type="text" id="in-enName" name="in-enName" datasource="MENU_EN" datatype="1,is_null,30" value=""/></td>
							</tr>
							<tr>
								<td><label>权限角色：</label></td>
		    					<td colspan="3"><input type="text"  style="width:96%;" id="in-grantRoles" name="in-grantRoles"  multi="true" kind="dic" src="T#ORG_ROLE:JSDM:JSMC:YXBS=1 " datasource="GRANT_ROLES" datatype="0,is_null,200" value=""/></td>
							</tr>
							<tr>
								<td><label>备注：</label></td>
		    					<td colspan="3" >
		    						<textarea id="in-memo" name="in-memo" style="width:96%;height:60px;overflow:auto;" datasource="MEMO" datatype="1,is_null,100" value=""></textarea>
		    					</td>
							</tr>
						</table>
					</form>	
					<br/>			
					<div class="formBar">
						<ul>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-preReport" name="btn-preReport">预&nbsp;&nbsp;览</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-save" name="btn-save">保&nbsp;&nbsp;存</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" name="btn-pre">上&nbsp;一&nbsp;步</button></div></div></li>
						</ul>
					</div>
					<div>
						备注：<br/>
						数据源有2种，一种是通过JDBC方式访问的数据源，另一种是通过数据库直接访问的数据源（dblink或跨用户方式）；
					</div>
				</div>
			</div>	
			<div class="tabsFooter">
				<div class="tabsFooterContent"></div>
			</div>
		</div>
	</div>
</div>
</body>
</html>