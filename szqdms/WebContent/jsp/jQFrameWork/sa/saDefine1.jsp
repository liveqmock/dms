<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html
xmlns:v="urn:schemas-microsoft-com:vml"
xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">
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
function afterDicItemClick(id)
{
	var ret =  true;
	switch(id)
	{
		case "in-ds":
				$("#in-ds-user").attr("src","T#SA_USER:USER_CODE:USER_NAME:USER_DS='"+$("#"+id).attr("code")+"'");
			break;
		case "in-reltab1":
			var v = $("#"+id).val();
			$.sa.fillField({usercode:v.split("@")[1],tablecomment:v.split("@")[0],tablename:$("#"+id).attr("code"),selId:"sel-allFields1"});
			break;
		case "in-reltab2":
			var v = $("#"+id).val();
			$.sa.fillField({usercode:v.split("@")[1],tablecomment:v.split("@")[0],tablename:$("#"+id).attr("code"),selId:"sel-allFields2"});
			break;
		case "in-firMenu":
			$("#in-secMenu").attr("src","T#EAP_MENU:NAME:TITLE:PARENT='"+$("#"+id).attr("code")+"' ORDER BY ORDERNO ");
			break;
	}
	return ret;
}
//列表复选
function doCheckbox(checkbox)
{
	var $tr = $(checkbox).parent().parent().parent();
	var arr = [];
	//分车明细ID
	arr.push($tr.attr("TABLE_NAME"));
	arr.push($tr.attr("TABLE_COMMENT"));
	arr.push($tr.attr("USER_CODE"));
	var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']");
	multiSelected($checkbox,arr);
}
//回调函数
var $curCell = null;
var allFieldRes = null;
var rows = 0,cols = 0;
var x = 0,y=0;
var str;
</script>
</head>
<body style="background-color:#fff">
<div id="layout">
	<h2 class="contentTitle">统计分析定制<span style="font-weight:normal;"> 版本1.0：灵活定义统计分析表，支持多数据源选择，表样灵活定制</span></h2>
	<div class="pageContent">
		<div class="tabs" currentIndex="0" eventType="click">
			<div class="tabsHeader">
				<div class="tabsHeaderContent">
					<ul>
						<li><a href="javascript:void(0);"><span>数据源</span></a></li>
						<li><a href="javascript:void(0);"><span>数据表定义</span></a></li>
						<li><a href="javascript:void(0);"><span>数据表关系定义</span></a></li>
						<li><a href="javascript:void(0);"><span>过滤条件定义</span></a></li>
						<li><a href="javascript:void(0);"><span>检索条件定义</span></a></li>
						<li><a href="javascript:void(0);"><span>表样定义</span></a></li>
						<li><a href="javascript:void(0);"><span>生成报表</span></a></li>
					</ul>
				</div>
			</div>
			<div class="tabsContent">
				<!-- ds -->
				<div id="div-ds"  style="height:auto;overflow:hidden;">
					<table class="editTable" id="dia-yhxx">
						<tr>
						    <td><label>数据源：</label></td>
						    <td><input type="text" id="in-ds"  name="in-ds" datasource="DS" kind="dic" src="T#SA_DS:DS_CODE:DS_NAME" datatype="0,is_null,30" value=""/>
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
						备注：<br/>
						数据源有2种，一种是通过JDBC方式访问的数据源，另一种是通过数据库直接访问的数据源（dblink或跨用户方式）；
					</div>
				</div>
				<!-- ds-table -->
				<div id="div-tab" class="" style="height:auto;overflow:hidden;">
					<form method="post" id="fm-ut-cx" >
						<div class="searchBar" align="left" >
							<table class="searchContent" id="tab-ut-cx">
								<!-- 定义查询条件 -->
								<tr><td><label>数据表名：</label></td>
								    <td><input type="text" id="in-tablename" name="in-tablename" datasource="TABLE_NAME" datatype="1,is_null,30"/></td>
								    <td><label>数据表注释：</label></td>
								    <td><input type="text" id="in-tablecomment" name="in-tablecomment" datasource="TABLE_COMMENT" datatype="1,is_null,30"/></td>
								    <td><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-ut-cx">查&nbsp;&nbsp;询</button></div></div></td>
								</tr>
							</table>
						</div>
					</form>
					<br/>
					<div id="div-ut-lb" >
						<div id="page-ut-lb">
						<table width="100%" style="display:none"  id="tab-ut-lb" name="tablist" ref="page-ut-lb" refQuery="tab-ut-cx" >
							<thead>
								<tr>
									<th type="multi" name="XH" unique="TABLE_ID"></th>
									<th fieldname="TABLE_NAME">数据表名</th>
									<th fieldname="TABLE_COMMENT">数据表名</th>
									<th fieldname="TABLE_TYPE">数据表类型</th>
									<th fieldname="DS_CODE">所属数据源</th>
									<th fieldname="USER_CODE" >所属用户</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
						</div>
					</div>
					<br/>
					<fieldset>
						<legend align="left">&nbsp;[已选定数据表]</legend>
						<div>
							<table style="width:100%;">
							<tr><td>
								<textarea style="width:80%;height:26px;" id="val0" name="multivals"  readOnly column="0"></textarea>
								<textarea style="width:80%;height:26px;" id="val1" name="multivals" readOnly></textarea>
								<textarea style="width:80%;height:26px;" id="val2" name="multivals" readOnly></textarea>
							</td></tr>
							</table>	
						</div>
					</fieldset>	
					<div class="formBar">
						<ul>
							<li><div class="button"><div class="buttonContent"><button type="button" name="btn-pre">上&nbsp;一&nbsp;步</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" name="btn-next">下&nbsp;一&nbsp;步</button></div></div></li>
						</ul>
					</div>
					<div>
						备注：<br/>
						数据源有2种，一种是通过JDBC方式访问的数据源，另一种是通过数据库直接访问的数据源（dblink或跨用户方式）；
					</div>
				</div>
				<div id="div-ref">
					<div style="display:block; margin:10px;margin-left:12%;">数据表：
							<input type="text" dicWidth="300" id="in-reltab1" name="in-reltab1" kind="dic" style="width:210px" />
					</div>
					<div style="float:left; display:block; margin-left:12%;overflow:hidden; width:300px; height:200px; border:solid 1px #CCC; line-height:21px; background:#FFF;">
						<select id="sel-allFields1" name="allFields" style="border:0px;width:100%;height:100%;"size="15" style="FONT-SIZE: 10pt;" >
						</select>
					</div>
					<div style="float:left; margin-top:50px;padding-left:30px;display:block;overflow:hidden; width:120px; line-height:21px; ">
						<select style="float:left;" style="width:120px;" id="sel-relation" name="sel-relation" class="combox" kind="dic" src="GXFH" datasource="GXFH">
							<option value="=" selected>等于[=]</option>
						</select>
						<br/>
						<br/>
						<div style="margin-left:0px;" class="button"><div class="buttonContent"><button type="button" name="btn-addRel" id="btn-addRel">添&nbsp;&nbsp;&nbsp;&nbsp;加</button></div></div>
					</div>
					<div style="float:left;display:block; margin:-33px 10px 10px 0px">数据表：
							<input type="text" dicWidth="300" id="in-reltab2" name="in-reltab2" kind="dic" style="width:210px" />
					</div>
					<div id="div-allFields1" style="display:block;margin-left:0%; overflow:hidden; width:300px; height:200px; border:solid 1px #CCC; line-height:21px; background:#FFF;">
						<select id="sel-allFields2" name="allFields" style="border:0px;width:100%;height:100%;"size="15" style="FONT-SIZE: 10pt;" >
						</select>
						<input type="text" style="display:none;" />
					</div>
					<div style="float:left; display:block;margin-top:20px; margin-left:12%;overflow:hidden; width:750px; height:150px; border:solid 1px #CCC; line-height:21px; background:#FFF;">
						<select id="sel-relcondition" name="sel-relcondition" style="border:0px;width:100%;height:100%;"size="15" style="FONT-SIZE: 10pt;" >
						</select>
					</div>
					<div style="margin-left:15px;margin-top:150px;" class="button"><div class="buttonContent"><button type="button" name="btn-addRel" id="btn-addRel">删除已加关系</button></div></div>
					<div class="formBar">
						<ul>
							<li><div class="button"><div class="buttonContent"><button type="button" name="btn-pre">上&nbsp;一&nbsp;步</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" name="btn-next">下&nbsp;一&nbsp;步</button></div></div></li>
						</ul>
					</div>
				</div>
				<div id="div-val">
					<div class="formBar">
						<ul>
							<li><div class="button"><div class="buttonContent"><button type="button" name="btn-pre">上&nbsp;一&nbsp;步</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" name="btn-next">下&nbsp;一&nbsp;步</button></div></div></li>
						</ul>
					</div>
					<div>
						备注：<br/>
						数据源有2种，一种是通过JDBC方式访问的数据源，另一种是通过数据库直接访问的数据源（dblink或跨用户方式）；
					</div>
				</div>
				<div id="div-search">
						<div class="formBar">
							<ul>
							<li><div class="button"><div class="buttonContent"><button type="button" name="btn-pre">上&nbsp;一&nbsp;步</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" name="btn-next">下&nbsp;一&nbsp;步</button></div></div></li>
							</ul>
						</div>
						<div>
							备注：<br/>
							数据源有2种，一种是通过JDBC方式访问的数据源，另一种是通过数据库直接访问的数据源（dblink或跨用户方式）；
						</div>
				</div>
				<div id="div-define">
					<div style="width:100%;height:300px;background-color:#fff">
					<div style="float:left;width:64%;height:300px;">
						<table id="freetable" align="left"  cellspacing="0" cellpadding="0" border="1px" >
							<tr>
								<td id="freeTitle" height="30px" style="text-align:center;font-size: 13pt; font-weight: bold;">标题</td>
							</tr>
							<tr>
								<td align="center">
								   <table id="freeST" align="left" border="1px" cellspacing="0" cellpadding="1" width="100%">
									 <tr><td x=0 y=0 type="H" rowSpan=1 colSpan=1 align="center" valign="middle">&nbsp;</td>
									     <td x=1 y=0 type="X" >&nbsp;</td>
									 </tr>
									 <tr><td x=0 y=1 type="Y" >&nbsp;</td>
								     	 <td x=1 y=1 type="S" >&nbsp;</td>
								     </tr>
								   </table>
								</td>
							</tr>
						</table>
					</div>
					<fieldset style="width:35%;height:285px;background:#f8f7f4;">
					<legend align="left">&nbsp;编辑字段属性</legend>
						<div style="display:block;margin-top:20px;background:#f8f7f4; margin-left:1px;overflow:hidden; width:99%; height:260px; border:solid 0px #CCC; line-height:21px;">
							<table class="editTable" id="tab-editFields" >
								<tr>
									<td><label>显示名称：</label></td>
			    					<td colspan="3"><input type="text" class="middleInput" style="width:95%;" id="in-showName" name="in-showName" datasource="SHOW_NAME" datatype="1,is_null,20" value="" readonly="true"/></td>
								</tr>
								<tr>
									<td><label>字段定义：</label></td>
								    <td colspan="3"><input type="text" class="middleInput" style="width:95%;" id="in-fieldName"  name="in-fieldName" datasource="FIELD_NAME" kind="dic"  datatype="1,is_null,60" value="" readonly="true"/></td>
								</tr>
								<tr>
								    <td><label>显示宽度：</label></td>
								    <td><input type="text" class="shortInput" style="width:100px;" id="in-fieldWidth"  name="in-fieldWidth" datasource="FIELD_WIDTH" datatype="1,is_digit,60" value="0" readonly="true"/><span>px</span></td>
								    <td><label>显示高度：</label></td>
			    					<td><input type="text" class="shortInput" style="width:100px;" id="in-fieldHeight" name="in-fieldHeight" datasource="FIELD_HEIGHT" datatype="1,is_digit,20" value="0" readonly="true"/><span>px</span></td>
								</tr>
								<tr> 
								    <td><label>对齐方式：</label></td>
								    <td>
								    	<input type="text" class="middleInput" style="width:100px;" id="in-fieldAlign"  name="in-fieldAlign" datasource="FIELD_ALIGN"  datatype="1,is_null,60" value="" readonly="true"/>
								    </td>
								</tr>
								<tr>
								    <td ><label>公式定义：</label></td>
								    <td colspan="3">
								    	<textarea style="width:95%;height:30px;overflow:auto;" id="in-fieldFunc"  name="in-fieldFunc" datasource="FIELD_FUNC" datatype="1,is_null,60" value="" readonly="true"></textarea>
								    </td>
								</tr>
								<tr>
								    <td ><label>字典设置：</label></td>
								    <td colspan="3">
								    	<textarea style="width:95%;height:60px;overflow:auto;" id="in-fieldDict"  name="in-fieldDict" datasource="FIELD_DICT"  datatype="1,is_null,60" value="" readonly="true"></textarea>
								    </td>
								</tr>
							</table>
						</div>
					</fieldset>
					</div>	
					<div class="formBar">
						<ul>
							<li><div class="button"><div class="buttonContent"><button type="button" name="btn-pre">上&nbsp;一&nbsp;步</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" name="btn-next">下&nbsp;一&nbsp;步</button></div></div></li>
						</ul>
					</div>
					<div>
						备注：<br/>
						数据源有2种，一种是通过JDBC方式访问的数据源，另一种是通过数据库直接访问的数据源（dblink或跨用户方式）；
					</div>
				</div>
				<div id="div-save">
					<form method="post" id="fm-saveReport" class="editForm" >
						<input type="hidden" id="reportDOM" name="reportDOM" />
						<table class="editTable" id="tab-saveReport" >
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
								<td><label>备注：</label></td>
		    					<td colspan="3" >
		    						<textarea id="in-memo" name="in-memo" style="width:96%;height:30px;overflow:auto;" datasource="MEMO" datatype="1,is_null,100" value=""></textarea>
		    					</td>
							</tr>
						</table>
					</form>	
					<br/>			
					<div class="formBar">
						<ul>
							<li><div class="button"><div class="buttonContent"><button type="button" name="btn-save">保&nbsp;&nbsp;存</button></div></div></li>
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