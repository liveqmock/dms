<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>计划明细</title>
<script type="text/javascript">
//查询按钮响应方法
$(function(){
	$("#jhmxlb").show();
	$("#jhmxlb").jTable();
	$("#searchDetail").click(function(){
		$("#jhmxlb").show();
		$("#jhmxlb").jTable();
	});
	var dialog = parent.$("body").data("jhmx");
	$("button.close").click(function(){
		parent.$.pdialog.close(dialog);
		return false;
	});
});
function doExp(){
	alertMsg.info("下载计划明细！");
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<div class="page">
	<div class="pageHeader">
		<form id="jhmxform" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="jhmxTable">
					<tr>
						<td><label>计划代码：</label></td>
						<td><input type="text" id="FM_JHDM" name="FM_JHDM"  value="计划代码1" readonly="readonly" /></td>
						<td><label>计划名称：</label></td>
						<td><input type="text" id="FM_JHMC" name="FM_JHMC"  value="计划名称1" readonly="readonly" /></td>
						<td><label>计划日期：</label></td>
						<td><input type="text" id="FM_JHRQ" name="FM_JHRQ" value="2014-04-26至2014-05-25" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label>配件仓库：</label></td>
						<td><select type="text" id="FM_PJCK" name="FM_PJCK" class="combox" kind="dic" src="E#1=中心配件仓库" >
								<option value=-1>--</option>
							</select>
						</td>
						<td><label>配件代码：</label></td>
						<td><input type="text" id="FM_PJDM" name="FM_PJDM"   /></td>
						<td><label>配件名称：</label></td>
						<td><input type="text" id="FM_PJMC" name="FM_PJMC"  /></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="searchDetail">查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="exp" onclick="doExp()">下&nbsp;&nbsp;载</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="jhmx">
			<table width="100%" id="jhmxlb" name="jhmxlb" style="display: none" >
				<thead>
					<tr>
						<th  name="XH" style="display:"></th>
						<th>配件仓库</th>
						<th>配件代码</th>
						<th>配件名称</th>
						<th>是否新增</th>
						<th>提报数量</th>
						<th>总出库量</th>
						<th>配件总装车量</th>
						<th>现有车型配件增量</th>
						<th>故障率</th>
						<th>是否保修故障率</th>
						<th>本区域出库量</th>
						<th>库存数量</th>
						<th>计划数量</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>1</td>
						<td>配件仓库1</td>
						<td>配件代码1</td>
						<td>配件名称1</td>
						<td>否</td>
						<td>50</td>
						<td>178</td>
						<td>300</td>
						<td>200</td>
						<td>0.078</td>
						<td>否</td>
						<td>140</td>
						<td>72</td>
						<td>83</td>
					</tr>
					<tr>
						<td>2</td>
						<td>配件仓库2</td>
						<td>配件代码2</td>
						<td>配件名称2</td>
						<td>否</td>
						<td>50</td>
						<td>178</td>
						<td>300</td>
						<td>200</td>
						<td>0.078</td>
						<td>否</td>
						<td>140</td>
						<td>72</td>
						<td>83</td>
					</tr>
					<tr>
						<td>3</td>
						<td>配件仓库3</td>
						<td>配件代码3</td>
						<td>配件名称3</td>
						<td>否</td>
						<td>50</td>
						<td>178</td>
						<td>300</td>
						<td>200</td>
						<td>0.078</td>
						<td>否</td>
						<td>140</td>
						<td>72</td>
						<td>83</td>
					</tr>
					<tr>
						<td>4</td>
						<td>配件仓库4</td>
						<td>配件代码4</td>
						<td>配件名称4</td>
						<td>否</td>
						<td>50</td>
						<td>178</td>
						<td>300</td>
						<td>200</td>
						<td>0.078</td>
						<td>否</td>
						<td>140</td>
						<td>72</td>
						<td>83</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>