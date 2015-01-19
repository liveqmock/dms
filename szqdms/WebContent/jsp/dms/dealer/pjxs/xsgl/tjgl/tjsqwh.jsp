<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="dia-layout">
  <div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="dia-fm-htwh" class="editForm" style="width:99%;">
		  <div align="left">
			  <fieldset>
						<table class="editTable" id="dia-tab-htxx">
							<tr><td><label>退件单号：</label></td>
							    <td><input type="text" id="dia-thdh" value="系统自动生成"  readonly="readonly"/></td>
							    <td><label>接收方：</label></td>
							    <td>
								    <select type="text" id="in-jsf"  name="in-jsf"  kind="dic" src="E#1=配送中心1:2=配送中心2:3=配件销售公司" datatype="0,is_null,30" >
					    				<option value="1" selected>配送中心1</option>
					    			</select>
							    </td>
							   <td><label>申请日期：</label></td>
							   <td ><input type="text" id="dia-in-zdrq" name="dia-in-zdrq" value="2014-05-28 " datatype="0,is_null,30" onclick="WdatePicker({isShowWeek:false,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" /></td>
							</tr>
						</table>
			  </fieldset>
			</div>
			</form>		
			<div class="formBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-save" onclick="doDiaCgdSave();">保&nbsp;&nbsp;存</button></div></div></li>
					<li id="dia-contror" style="display: none"><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-report" onclick="doDiaCgdReport();">提&nbsp;&nbsp;报</button></div></div></li>
					<li id="dia-xzmb" style="display: none"><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-dr" onclick="doDiaPldr(1);">下载模板</button></div></div></li>
					<li id="dia-pldr" style="display: none"><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-dr" onclick="doDiaPldr(2);">批量导入</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
				</ul>
			</div>
				<div id="dia-lpmx" style="">
						<table style="display:none;width:99%;" id="dia-tab-pjlb" layoutH="350" name="tablist" ref="dia-lpmx"  edit="true">
								<thead>
									<tr>
										<th type="single" name="XH" style="display:" append="plus|addPjmx"></th>
										<th fieldname="PJDM" ftype="input" fdatatype="0,is_null,30" colwidth="80" freadonly="true" fvalue="PJ001">配件代码</th>
										<th fieldname="CKTH" ftype="input" fdatatype="0,is_null,30" colwidth="50" freadonly="true">参图号</th>
										<th fieldname="PJMC" ftype="input" fdatatype="0,is_null,30" colwidth="50" freadonly="true" fvalue="配件名称1">配件名称</th>
										<th fieldname="GYS"  ftype="input" fdatatype="0,is_null,30" colwidth="50" freadonly="true" fvalue="供应商1">供应商</th>
										<th fieldname="JLDW" ftype="input" fdatatype="0,is_null,30" colwidth="50" freadonly="true" fvalue="件">计量单位</th>
										<th fieldname="ZXBZ" ftype="input" fdatatype="0,is_null,30" colwidth="50" freadonly="true" fvalue="10/件">最小包装</th>
										<th fieldname="JHS"  ftype="input" fdatatype="0,is_null,30" colwidth="40" freadonly="false">退货数</th>
										<th fieldname="JHJG" ftype="input" fdatatype="0,is_null,30" colwidth="50" freadonly="true" fvalue="1,100.60">经销商价格</th>
										<th fieldname="JE"   ftype="input" fdatatype="0,is_null,30" colwidth="50" freadonly="true">金额</th>
										<th colwidth="85" type="link" title="[编辑]|[删除]"  action="doDiaListSave|doDiaListDelete">操作</th>
									</tr>
								</thead>
								<tbody>
								</tbody>
						</table>
					</div>
		</div>
				
	</div>
<script type="text/javascript">
var action = "<%=action%>";
$(function(){
	$("button.close").click(function(){
		$.pdialog.closeCurrent();
		return false;
	});
	if(action==2)
	{
		$("#dia-contror").show();
		$("#dia-pldr").show();
		$("#dia-xzmb").show();
		$("#dia-tab-pjlb").show();
		$("#dia-tab-pjlb").jTable();
	}
});
function doDiaCgdSave()
{
	if(action==1)
	{
		$("#dia-contror").show();
		$("#dia-pldr").show();
		$("#dia-xzmb").show();
		alertMsg.info("新增成功。");
		
	}else
	{
		alertMsg.info("修改成功。");
	}
	if($("#dia-tab-pjlb").is(":hidden"))
		{
			$("#dia-tab-pjlb").show();
			$("#dia-tab-pjlb").jTable();
		}
}
function addPjmx()
{
	var options = {max:false,width:900,height:450,mask:true,mixable:true,minable:true,resizable:false,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/dealer/pjxs/xsgl/tjgl/pjcx.jsp?", "pjcx", "配件信息查询", options);
	//var $tab = $("#dia-tab-pjlb_content");
	//$tab.createRow();
}
function doDiaListSave(row)
{
	var ret = true;
	$("td input[type=radio]",$(row)).attr("checked",true);
	//提交保存方法
	diaListSaveCallBack();
	return ret;
}
function diaListSaveCallBack(res)
{
	var selectedIndex = $("#dia-tab-pjlb_content").getSelectedIndex();
	//$("#dia-tab-pjlb_content").updateResult(res,selectedIndex);
	$("#dia-tab-pjlb_content").clearRowEdit($("#dia-tab-pjlb_content").getSelectedRows()[0],selectedIndex);
	return true;
}
//列表删除
function doDiaListDelete()
{
}
function doDiaCgdReport()
{
	alertMsg.info("提报完成");
}
function doDiaPldr(value)
{
	if(value==1)
	{
		alertMsg.info("下载模板");
	}else
	{
		alertMsg.info("导入EXCEL配件明细");
	}
	
}
</script>
</div>

