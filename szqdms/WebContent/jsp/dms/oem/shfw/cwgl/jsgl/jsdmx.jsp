<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>结算单查询</title>
<script type="text/javascript">
//弹出窗体
var dialog = parent.$("body").data("jsdmx");
$(function(){
	$("#jsdmxlb").show();
	$("#jsdmxlb").jTable();
	$("button.close").click(function(){
		parent.$.pdialog.close(dialog);
		return false;
	});
});
function doDetail(){
	alertMsg.info("打开索赔单明细");
	/* var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/dealer/shfw/fwhdgl/fwhdgl/fwhdxxmx.jsp", "fwhdxxmx", "服务活动明细", options,true); */
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<div class="page">
		<div class="pageContent">
			<div id="jsdmx" >
				<table width="100%" id="jsdmxlb" name="jsdmxlb" style="display: none" >
					<thead>
						<tr>
							<th  name="XH" style="display:" colwidth="20">序号</th>
							<th colwidth="60">索赔单号</th>
							<th colwidth="60">结算单号</th>
							<th colwidth="60">结算产生日期</th>
							<th colwidth="60"> 结算类型</th>
							<th  align="right" colwidth="60">工时金额(元)</th>
							<th  align="right" colwidth="60">外出金额(元)</th>
							<th  align="right" colwidth="60">其他费用(元)</th>
							<th  align="right" colwidth="60">强保费(元)</th>
							<th  align="right" colwidth="60">材料费(元)</th>
							<th  align="right" colwidth="60">索赔总费(元)</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td><a href="#" onclick="doDetail()" class="op">索赔单号1</a></td>
							<td>结算单号1</td>
							<td>2014-4-8</td>
							<td>其他</td>
							<td>100</td>
							<td>100</td>
							<td>100</td>
							<td></td>
							<td></td>
							<td>300</td>
						</tr>
						<tr>
							<td>2</td>
							<td><a href="#" onclick="doDetail()" class="op">索赔单号1</a></td>
							<td>结算单号2</td>
							<td>2014-4-8</td>
							<td>材料</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td>100</td>
							<td>100</td>
						</tr>
						<tr>
							<td>3</td>
							<td><a href="#" onclick="doDetail()" class="op">索赔单号2</a></td>
							<td>结算单号3</td>
							<td>2014-4-8</td>
							<td>其他</td>
							<td>100</td>
							<td>100</td>
							<td>100</td>
							<td></td>
							<td></td>
							<td>300</td>
						</tr>
						<tr>
							<td>4</td>
							<td><a href="#" onclick="doDetail()" class="op">索赔单号3</a></td>
							<td>结算单号3</td>
							<td>2014-4-8</td>
							<td>其他</td>
							<td>100</td>
							<td>100</td>
							<td>100</td>
							<td></td>
							<td></td>
							<td>300</td>
						</tr>
						<tr>
							<td>5</td>
							<td><a href="#" onclick="doDetail()" class="op">索赔单号4</a></td>
							<td>结算单号3</td>
							<td>2014-4-8</td>
							<td>其他</td>
							<td>100</td>
							<td>100</td>
							<td>100</td>
							<td></td>
							<td></td>
							<td>300</td>
						</tr>
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
</body>
</html>