<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>配件档案维护</title>
<script type="text/javascript">
//变量定义
//查询提交方法
var url = "<%=request.getContextPath()%>/Pjjcxxgl/PjdaglAction.do";
//初始化方法
$(function(){
	//查询方法
	$("#btn-cx").bind("click",function(event){
		if($("#tab-pjlb").is(":hidden"))
		{
			$("#tab-pjlb").show();
			$("#tab-pjlb").jTable();
		}
	});
	//新增方法
	$("#btn-xz").bind("click",function(event){
		var options = {max:false,width:790,height:450,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps + "/jsp/dms/oem/pjxs/jcsjgl/jcxxgl/pjdaxz.jsp?action=1", "pjdawh", "配件档案新增", options);
	});
});

//列表编辑连接
function doUpdate(rowobj)
{
	$("td input:first",$(rowobj)).attr("checked",true);
	var options = {max:false,width:790,height:450,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/oem/pjxs/jcsjgl/jcxxgl/pjdaxz.jsp?action=2", "pjdawh", "配件档案维护", options);
}

var row;
//删除链接
function doDelete(rowobj)
{
	row = $(rowobj);
	alertMsg.info("调用删除操作");
}
//删除回调方法
function  deleteCallBack(res)
{
	try
	{
		if(row)
			row.remove();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 基础数据管理  &gt; 基础信息管理   &gt; 配件档案维护</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-pjcx">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-pjcx">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>配件代码：</label></td>
					    <td><input type="text" id="in-pjdm" name="in-pjdm" datasource="PJDM" datatype="1,is_digit_letter,30" operation="like" /></td>
					    <td><label>配件名称：</label></td>
					    <!-- 表选字典 -->
					    <td><input type="text" id="in-pjgys"  name="in-pjgys" kind="dic" src="E#1=供应商1:2=供应商2:3=供应商3" datasource="GYS"  datatype="1,is_null,30" operation="="  /></td>
						 <td><label>配件类型：</label></td>
					    <td><input type="text" id="in-pjlx"  name="in-pjlx" kind="dic" src="E#1=类型一:2=类型二" datasource="PJLX"  datatype="1,is_null,30" /></td>
					</tr>
					<tr>
					   
					    <td><label>配件属性：</label></td>
					    <td><input type="text" id="in-pjsx" name="in-pjsx" kind="dic" src="E#1=易损件:2=常用件:3=非常用件" datasource="PJSX" datatype="1,is_null,30" /></td>
					    <td><label>配件状态：</label></td>
					    <td>
					    	<select type="text" id="in-pjzt"  name="in-pjzt" datasource="PJZT" kind="dic" src="QYBS" datatype="1,is_null,30" >
					    		<option value="-1" selected>--</option>
					    	</select>
					    </td>
					    <td><label>是否直发：</label></td>
					    <td>
					    	<select type="text" id="in-sfzf"  name="in-sfzf" kind="dic" src="SF" datasource="SFZF"  datatype="1,is_null,30" >
					    		<option value="-1" selected>--</option>
					    	</select>
					    </td>
					</tr>
					<tr>
					    <td><label>是否保外：</label></td>
					    <td>
					    	<select type="text" id="in-sfbw" name="in-sfbw" kind="dic" src="SF" datasource="SFBW" datatype="1,is_null,30" >
					    		<option value="-1" selected>--</option>
					    	</select>
					    </td>
					    <td><label>是否可订：</label></td>
					    <td>
					    	<select type="text" id="in-sfkd"  name="in-sfkd" kind="dic" src="SF" datasource="SFKD"  datatype="1,is_null,30" >
					    		<option value="-1" selected>--</option>
					    	</select>
					    </td>
					    <td><label>是否回运：</label></td>
					    <td>
					    	<select type="text" id="in-sfkd"  name="in-sfkd" kind="dic" src="SF" datasource="SFHY"  datatype="1,is_null,30" >
					    		<option value="-1" selected>--</option>
					    	</select>
					    </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-cx" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-xz" >新&nbsp;&nbsp;增</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_yhlb" >
			<table style="display:none;width:100%;" id="tab-pjlb" name="tablist" ref="page_pjlb" refQuery="fm-pjcx" >
					<thead>
						<tr>
							<th fieldname="ROWNUMS" style="display:"></th>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="PJDM" >配件代码</th>
							<th fieldname="PJMC" >配件名称</th>
							<th fieldname="CKTH" >参考图号</th>
							<th fieldname="JLDW" colwidth="50">计量单位</th>
							<th fieldname="ZXBZS" colwidth="70">最小包装数</th>
							<th fieldname="ZXBZDW" colwidth="80">最小包装单位</th>
							<th fieldname="PJLX" >配件类型</th>
							<th fieldname="PJSX" >配件属性</th>
							<th fieldname="SFZF" >是否直发</th>
							<th fieldname="SFBW" >是否保外</th>
							<th fieldname="SFKD" >是否可订</th>
							<th fieldname="SFHY" >是否回运</th>
							<th fieldname="PJZT" >状态</th>
							<th colwidth="85" type="link" title="[编辑]|[删除]"  action="doUpdate|doDelete" >操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="rownums"><div>1</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>PJ001</div></td>
							<td><div>配件001</div></td>
							<td><div>TH001</div></td>
							<td><div>件</div></td>
							<td><div>10</div></td>
							<td><div>包</div></td>
							<td><div>配件类型一</div></td>
							<td><div>易损件</div></td>
							<td><div>否</div></td>
							<td><div>否</div></td>
							<td><div>是</div></td>
							<td><div>是</div></td>
							<td><div>启用</div></td>
							<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" class="op" onclick="doDelete()">[删除]</a></td>
						</tr>
						<tr>
							<td class="rownums"><div>2</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>PJ002</div></td>
							<td><div>配件002</div></td>
							<td><div>TH002</div></td>
							<td><div>件</div></td>
							<td><div>10</div></td>
							<td><div>包</div></td>
							<td><div>配件类型一</div></td>
							<td><div>易损件</div></td>
							<td><div>否</div></td>
							<td><div>否</div></td>
							<td><div>是</div></td>
							<td><div>是</div></td>
							<td><div>启用</div></td>
							<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" class="op" onclick="doDelete()">[删除]</a></td>
						</tr>
						<tr>
							<td class="rownums"><div>3</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>PJ003</div></td>
							<td><div>配件003</div></td>
							<td><div>TH003</div></td>
							<td><div>件</div></td>
							<td><div>10</div></td>
							<td><div>包</div></td>
							<td><div>配件类型一</div></td>
							<td><div>易损件</div></td>
							<td><div>否</div></td>
							<td><div>否</div></td>
							<td><div>是</div></td>
							<td><div>是</div></td>
							<td><div>启用</div></td>
							<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" class="op" onclick="doDelete()">[删除]</a></td>
						</tr>
					</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>