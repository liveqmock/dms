<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="dia-layout">
  <div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="dia-fm-htwh" class="editForm" style="width:99%;">
		  <div align="left">
			  <fieldset>
						<table class="editTable" id="dia-tab-htxx">
							<tr><td><label>退货单号：</label></td>
							    <td><input type="text" id="dia-thdh" value="退货单号"  readonly="readonly"/></td>
							    <td><label>原采购单号：</label></td>
							    <td><input type="text" id="dia-cgdh"  name="dia-cgdh" value="采购单号" readonly="readonly"/></td>
							    <td><label>供应商：</label></td>
							    <td><input type="text" id="in-ddzt"  name="in-ddzt" value="供应商1" readonly="readonly"/></td>
							</tr>
							<tr>
							    <td><label>退货类别：</label></td>
							    <td><input type="text" id="dia-cglb"  name="dia-cglb" value="采购退货" readonly="readonly"/></td>
							    <td><label>制单日期：</label></td>
							    <td><input type="text" id="dia-in-zdrq" name="dia-in-zdrq" value="2014-05-28 "  readonly="readonly"/></td>
							</tr>
						</table>
			  </fieldset>
			</div>
			</form>		
				<div id="dia-lpmx" style="">
						<table style="display:none;width:99%;" id="dia-tab-pjlb" layoutH="350" name="tablist" ref="dia-lpmx"  edit="true">
								<thead>
									<tr>
										<th type="single" name="XH" style="display:" append="plus|addPjmx"></th>
										<th fieldname="PJDM" ftype="input" fdatatype="0,is_null,30" colwidth="80" fsrc="E#1=配件代码1:2=配件代码2:3=配件代码3" fkind="dic" freadonly="false">配件代码</th>
										<th fieldname="CKTH" ftype="input" fdatatype="0,is_null,30" colwidth="50" freadonly="true">参图号</th>
										<th fieldname="PJMC" ftype="input" fdatatype="0,is_null,30" colwidth="50" freadonly="true">配件名称</th>
										<th fieldname="JLDW" ftype="input" fdatatype="0,is_null,30" colwidth="50" freadonly="true">计量单位</th>
										<th fieldname="ZXBZ" ftype="input" fdatatype="0,is_null,30" colwidth="50" freadonly="true">最小包装</th>
										<th fieldname="JHS"  ftype="input" fdatatype="0,is_null,30" colwidth="40" freadonly="false" fvalue="20">退货数</th>
										<th fieldname="JHJG" ftype="input" fdatatype="0,is_null,30" colwidth="50" freadonly="true" >采购价格</th>
										<th fieldname="JE"   ftype="input" fdatatype="0,is_null,30" colwidth="50" freadonly="true">金额</th>
										<th fieldname="BZ"   ftype="input" fdatatype="0,is_null,30" colwidth="50" freadonly="false">调整备注</th>
										<th colwidth="85" type="link" title="[编辑]|[删除]"  action="doDiaListSave|doDiaListDelete">操作</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td><div><input type="radio" /></div></td>
										<td><div>配件代码1</div></td>
										<td><div></div></td>
										<td><div>配件名称1</div></td>
										<td><div>件</div></td>
										<td><div>10/件</div></td>
										<td><div>10</div></td>
										<td><div>10.00</div></td>
										<td><div>100</div></td>
										<td></td>
										<td><a href="#" onclick="doDiaListSave()" class="op">[编辑]</a><a href="#" onclick="doDiaListDelete()" class="op">[删除]</a></td>
									</tr>
									<tr>
										<td><div><input type="radio" /></div></td>
										<td><div>配件代码2</div></td>
										<td><div></div></td>
										<td><div>配件名称2</div></td>
										<td><div>件</div></td>
										<td><div>10/件</div></td>
										<td><div>20</div></td>
										<td><div>10.00</div></td>
										<td><div>200</div></td>
										<td></td>
										<td><a href="#" onclick="doDiaListSave()" class="op">[编辑]</a><a href="#" onclick="doDiaListDelete()" class="op">[删除]</a></td>
									</tr>
								</tbody>
						</table>
					</div>
				<div class="formBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-save" onclick="doDiaCgdSave();">调整完成</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
			    </div>
		</div>
				
	</div>
<script type="text/javascript">
$(function(){
	$("button.close").click(function(){
		$.pdialog.closeCurrent();
		return false;
	});
	$("#dia-tab-pjlb").show();
	$("#dia-tab-pjlb").jTable();
});
function doDiaCgdSave()
{
	alertMsg.info("调整完成");
	$.pdialog.closeCurrent();
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
function afterDicItemClick(id,$row,selIndex)
{
	var ret =  true;
	if(id.indexOf("PJDM") == 0)
	{
		var curRow = $("#"+id);
		while(curRow.isTag("tr") == false)
		{
			curRow = curRow.parent();
		}
		curRow.find("td").eq(2).text("参考图号1");
		curRow.find("td").eq(3).text("配件名称");
		curRow.find("td").eq(4).text("件");
		curRow.find("td").eq(5).text("10/件");
		curRow.find("td").eq(7).text("100.00");
	}
	
	return ret;
}
function addPjmx()
{
	var $tab = $("#dia-tab-pjlb_content");
	$tab.createRow();
}
</script>
</div>
