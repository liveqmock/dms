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
							<tr><td><label>合同编号：</label></td>
							    <td><input type="text" id="dia-htbh" value="合同编号1"  datatype="0,is_digit_letter,30"/></td>
							    <td><label>厂家名称：</label></td>
							    <td><input type="text" id="dia-cjmc" value="厂家名称1"  datatype="0,is_null,60"/></td>
							    <td><label>厂家代码：</label></td>
							    <td><input type="text" id="dia-cjdm" value="厂家代码1" datatype="1,is_null,30"/></td>
							    
							</tr>
							<tr><td><label>厂家资质：</label></td>
							    <td><input type="text" id="dia-cjzz" value="厂家资质1"  datatype="0,is_digit_letter,30"/></td>
							    <td><label>厂家法人：</label></td>
							    <td><input type="text" id="dia-cjmc" value="厂家法人1"  datatype="0,is_null,60"/></td>
							    <td><label>法人联系方式：</label></td>
							    <td><input type="text" id="dia-cjdm" value="联系方式1" datatype="0,is_null,30"/></td>							    
							</tr>
							<tr><td><label>税率：</label></td>
							    <td><input type="text" id="dia-sl" value="税率1"  datatype="0,is_digit_letter,30"/></td>
							    <td><label>业务联系人：</label></td>
							    <td><input type="text" id="dia-ywlxr" value="业务联系人1"  datatype="0,is_null,60"/></td>
							    <td><label>业务联系方式：</label></td>
							    <td><input type="text" id="dia-ywlxfs" value="业务联系方式2" datatype="0,is_null,30"/></td>							    
							</tr>
							<tr><td><label>质保金金额：</label></td>
							    <td><input type="text" id="dia-zbje" value="质保金金额1"  datatype="0,is_money,30"/></td>
							    <td><label>质保期：</label></td>
							    <td><input type="text" id="dia-zbq" value="质保期1"  datatype="0,is_number,60"/></td>
							    <td><label>挂账结算周期：</label></td>
							    <td><input type="text" id="dia-jszq" value="挂账结算周期1" datatype="0,is_number,30"/></td>							    
							</tr>
							<tr><td><label>厂家供货周期：</label></td>
							    <td colspan="5"><input type="text" id="dia-cjghzq" value="厂家供货周期1"  datatype="0,is_number,30"/></td>							    
							</tr>
							<tr>
							    <td><label>追偿条款：</label></td>
							    <td colspan="5">
								  <textarea id="dia-zctk" style="width:450px;height:40px;" name="dia-zctk"  datatype="1,is_null,100"></textarea>
							    </td>
							</tr>
							<tr>
							    <td><label>备注：</label></td>
							    <td colspan="5">
								  <textarea id="dia-bz" style="width:450px;height:40px;" name="dia-bz"  datatype="1,is_null,100"></textarea>
							    </td>
							</tr>
						</table>
			  </fieldset>
			</div>
			</form>		
			<div class="formBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-save" onclick="doDiaHtSave();">保&nbsp;&nbsp;存</button></div></div></li>
					<li id="dia-contror" style="display: none"><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-report" onclick="doDiaHtReport();">提&nbsp;&nbsp;报</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
				</ul>
			</div>
			<div id="dia-lpmx" style="height:380px;overflow:hidden;">
					<table style="display:none;width:100%;" id="dia-tab-pjlb" layoutH="350" name="tablist" ref="dia-lpmx" pageRows="15" edit="true" >
							<thead>
								<tr>
									<th type="single" name="XH" style="display:" append="plus|addPjmx"></th>
									<th fieldname="PJDM" ftype="input" fvalue="供货品种代码1" fdatatype="0,is_null,30" freadonly="false">供货品种代码</th>
									<th fieldname="PJMC" ftype="input" fvalue="供货品种名称1" fdatatype="0,is_null,30" freadonly="false">供货品种名称</th>
									<th fieldname="PJDJ" ftype="input" fvalue="1,300.00" fdatatype="0,is_null,30" freadonly="false">单价(不含税)</th>
									<th fieldname="BZ"   ftype="input" fvalue="备注1"fdatatype="0,is_null,30" freadonly="false">备注</th>
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
		$("#dia-tab-pjlb").show();
		$("#dia-tab-pjlb").jTable();
	}
});
function doDiaHtSave()
{
	if(action==1)
	{
		$("#dia-contror").show();	
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
	var $tab = $("#dia-tab-pjlb_content");
	$tab.createRow();
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
</script>
</div>

