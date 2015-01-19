<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="dia-layout">
<div class="tabs" eventType="click" id="dia-tabs" >
			<div class="tabsHeader">
				<div class="tabsHeaderContent">
					<ul>
					    <li><a href="javascript:void(0)"><span>合同基本信息</span></a></li>
						<li><a href="javascript:void(0)"><span>供货品种信息</span></a></li>
					</ul>
				</div>
			</div>
  <div class="tabsContent">
  <div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="dia-fm-htwh" class="editForm" style="width:99%;">
		  <div align="left">
			  <fieldset>
						<table class="editTable" id="dia-tab-htxx">
							<tr><td><label>合同编号：</label></td>
							    <td><input type="text" id="dia-htbh" value="合同编号1" /></td>
							    <td><label>厂家名称：</label></td>
							    <td><input type="text" id="dia-cjmc" value="厂家名称1"  /></td>
							    <td><label>厂家代码：</label></td>
							    <td><input type="text" id="dia-cjdm" value="厂家代码1" /></td>
							    
							</tr>
							<tr><td><label>厂家资质：</label></td>
							    <td><input type="text" id="dia-cjzz" value="厂家资质1"  /></td>
							    <td><label>厂家法人：</label></td>
							    <td><input type="text" id="dia-cjmc" value="厂家法人1"  /></td>
							    <td><label>法人联系方式：</label></td>
							    <td><input type="text" id="dia-cjdm" value="联系方式1" /></td>							    
							</tr>
							<tr><td><label>税率：</label></td>
							    <td><input type="text" id="dia-sl" value="税率1"  /></td>
							    <td><label>业务联系人：</label></td>
							    <td><input type="text" id="dia-ywlxr" value="业务联系人1"  /></td>
							    <td><label>业务联系方式：</label></td>
							    <td><input type="text" id="dia-ywlxfs" value="业务联系方式2" /></td>							    
							</tr>
							<tr><td><label>质保金金额：</label></td>
							    <td><input type="text" id="dia-zbje" value="质保金金额1"  /></td>
							    <td><label>质保期：</label></td>
							    <td><input type="text" id="dia-zbq" value="质保期1"  /></td>
							    <td><label>挂账结算周期：</label></td>
							    <td><input type="text" id="dia-jszq" value="挂账结算周期1" /></td>							    
							</tr>
							<tr><td><label>厂家供货周期：</label></td>
							    <td colspan="5"><input type="text" id="dia-cjghzq" value="厂家供货周期1"  /></td>							    
							</tr>
							<tr>
							    <td><label>追偿条款：</label></td>
							    <td colspan="5">
								 追偿条款1
							    </td>
							</tr>
							<tr>
							    <td><label>备注：</label></td>
							    <td colspan="5">
								 备注
							    </td>
							</tr>
						</table>
			  </fieldset>
			</div>
			</form>		
				<form method="post" id="dia-fm-htsh" class="editForm" style="width:99%;">
		          	<div align="left">
					  <fieldset>
							<table class="editTable" id="dia-tab-shxx">
									
									<tr>
									    <td><label>审核意见：</label></td>
									    <td colspan="5">
								 		 <textarea id="dia-shyj" style="width:450px;height:40px;" name="dia-shyj"  datatype="0,is_null,100"></textarea>
							           </td>
									</tr>
								</table>
					  </fieldset>
					</div>
				</form>	
			<div class="formBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-save" onclick="doDiaHtSh(1);">通&nbsp;&nbsp;过</button></div></div></li>
					<li ><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-report" onclick="doDiaHtSh(2);">驳&nbsp;&nbsp;回</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button type="button" id="dia-next0" name="btn-next">下一页</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
				</ul>
			</div>
		</div>		
	</div>
	<div class="page">
       <div class="pageContent">
			<div id="dia-lpmx" style="overflow:hidden;">
							<table style="display:none;width:99%;" id="dia-tab-pjlb" layoutH="350" name="tablist" ref="dia-lpmx" edit="false" >
									<thead>
										<tr>
											<th fieldname="PJDM" freadonly="true">供货品种代码</th>
											<th fieldname="PJMC" freadonly="true">供货品种名称</th>
											<th fieldname="PJDJ" freadonly="true">单价(不含税)</th>
											<th fieldname="BZ"   freadonly="true">备注</th>
										</tr>
									</thead>
									<tbody>
									    <tr>
											<td><div>供货品种代码1</div></td>
											<td><div>供货品种名称1</div></td>
											<td><div>1,300.00</div></td>
											<td><div>备注1</div></td>
										</tr>
										<tr>
											<td><div>供货品种代码2</div></td>
											<td><div>供货品种名称2</div></td>
											<td><div>1,300.00</div></td>
											<td><div>备注2</div></td>
										</tr>
									</tbody>
							</table>
						</div>
				<div class="formBar">
					<ul>
						<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre1" name="btn-pre">上一页</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
				</div>						
      </div>				
	</div>		
</div>
<div class="tabsFooter">
	<div class="tabsFooterContent"></div>
</div>				
<script type="text/javascript">
$(function(){
	$("button.close").click(function(){
		$.pdialog.closeCurrent();
		return false;
	});
		$("#dia-tab-pjlb").show();
		$("#dia-tab-pjlb").jTable();
		$('input').attr("readonly","readonly");
		$("button[name='btn-pre']").bind("click",function(event) 
				{
						$("#dia-tabs").switchTab(parseInt($("#dia-tabs").attr("currentIndex")) - 1);
				});
				$("button[name='btn-next']").bind("click", function(event) 
					{
							var $tabs = $("#dia-tabs");
							switch (parseInt($tabs.attr("currentIndex"))) 
							{
								case 0:
									break;
								case 1:
									break;
							}
						$tabs.switchTab(parseInt($tabs.attr("currentIndex")) + 1);
						//跳转后实现方法
						(function(ci) 
						{
							switch (parseInt(ci)) 
							{
								case 1://第2个tab页					
									break;
								case 2://第3个tab页
									break;
								default:
									break;
							}
						})
						(parseInt($tabs.attr("currentIndex")));
				 });
});
function doDiaHtSh(action)
{
	if(action==1)
	{
		alertMsg.info("审核通过。");
		$.pdialog.closeCurrent();
		
	}else
	{
		alertMsg.info("审核驳回。");
		$.pdialog.closeCurrent();
	}
	
}
</script>
</div>

