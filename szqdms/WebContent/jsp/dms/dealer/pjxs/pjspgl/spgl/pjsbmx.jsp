<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="pjsbmx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="di-pjsbmx" class="editForm" >
			<div align="left">
			<fieldset>
			<table class="editTable" id="pjsbmxT">
				<tr>
					<td><label>三包申请单号：</label></td>
					<td><input type="text" id="FM_SBSQDH" name="FM_SBSQDH" datatype="1,is_null,100" value="三包申请单号1" readonly="readonly"/></td>
					<td><label>系统入库单：</label></td>
					<td><input type="text" id="FM_XDRKD" name="FM_XDRKD" datatype="1,is_null,100" value="入库单号1" readonly="readonly" /></td>
					<td><label>系统出库单：</label></td>
					<td><input type="text" id="FM_XDCKD" name="FM_XDCKD" datatype="1,is_null,100" value="出库单号1" readonly="readonly"/></td>
			    </tr>
			    <tr>
			    	<td><label>出库日期：</label></td>
					<td><input type="text" id="FM_CKRQ" name="FM_CKRQ" datatype="1,is_null,100" value="2014-04-24" readonly="readonly"/></td>
			    	<td><label>出库数量：</label></td>
					<td><input type="text" id="FM_CKSL" name="FM_CKSL" datatype="1,is_null,100" value="2" readonly="readonly"/></td>
					<td><label>购货单位：</label></td>
					<td><input type="text" id="FM_GHDW" name="FM_GHDW" datatype="1,is_double,100" value="张三" readonly="readonly" /></td>
				</tr>
				<tr>
					<td><label>联系人：</label></td>
					<td><input type="text" id="FM_LXR" name="FM_LXR" datatype="1,is_double,100" value="张三" readonly="readonly" /></td>
					<td><label>联系电话 ：</label></td>
					<td><input type="text" id="FM_LXDH" name="FM_LXDH" datatype="1,is_double,100" value="1355555555" readonly="readonly" /></td>
				</tr>
				<tr>
					<td><label>配件代码：</label></td>
					<td><input type="text" id="FM_PJDM" name="FM_PJDM"  datatype="1,is_null,100" value="配件代码1" readonly="readonly"/></td>
					<td><label>配件名称：</label></td>
					<td><input type="text" id="FM_PJMC" name="FM_PJMC" datatype="1,is_double,100" value="配件名称1" readonly="readonly" /></td>
					<td><label>单位 ：</label></td>
					<td><input type="text" id="FM_PJDW" name="FM_PJDW" datatype="1,is_double,100" value="个" readonly="readonly" /></td>
				</tr>
				 <tr>
					<td><label>数量：</label></td>
					<td><input type="text" id="FM_PJSL" name="FM_PJSL" datatype="1,is_double,100" value="2" readonly="readonly"/></td>
					<td><label>经销价：</label></td>
					<td><input type="text" id="FM_JXJ" name="FM_JXJ" datatype="1,is_double,100" value="59" readonly="readonly"/></td>
					<td><label>金额 ：</label></td>
					<td><input type="text" id="FM_JE" name="FM_JE" datatype="1,is_double,100" value="118" readonly="readonly" /></td>
				</tr>
				 <tr>
					<td><label>生产厂家：</label></td>
					<td colspan="5"><input type="text" id="FM_SCCJ" name="FM_SCCJ" value="厂家1" datatype="1,is_double,100" readonly="readonly"/></td>
				</tr>
				<tr>
					<td><label>故障情况：</label></td>
					<td colspan="5"><textarea id="FM_GZQK" name="FM_GZQK"  style="width:450px;height:40px;" datatype="1,is_null,500" >破裂</textarea></td>
				</tr>
				<tr>
					<td><label>备注：</label></td>
					<td colspan="5"><textarea id="FM_BZ" name="FM_BZ" style="width:450px;height:40px;" datatype="1,is_null,500" ></textarea></td>
				</tr>
			</table>
			</fieldset>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>	
</div>
<script type="text/javascript">
//弹出窗体
var dialog = $("body").data("pjsbmx");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>