<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contentPath = request.getContextPath();
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="dia-cxhdmx">
	<div class="tabs" eventType="click" id="dia-tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="javascript:void(0)"><span>基本信息</span></a></li>
					<li><a href="javascript:void(0)"><span>促销配件</span></a></li>
					<li><a href="javascript:void(0)"><span>促销范围</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" >
			<div class="page">
			<div class="pageContent" >
				<form method="post" id="dia-fm-pjdawh" class="editForm" style="width:99%;">
					<div align="left">
						<fieldset>
						<legend align="right"><a onclick="onTitleClick('dia-tab-pjxx')">&nbsp;促销信息编辑&gt;&gt;</a></legend>
						<table class="editTable" id="dia-tab-pjxx">
							<tr>
							    <td><label>促销活动编码：</label></td>
							    <td><input type="text" id="dia-in-lxr"  name="dia-in-lxr" datasource="LXR" datatype="1,is_null,30" value="11111"/></td>
							    <td><label>促销活动名称：</label></td>
							    <td><input type="text" id="dia-in-lxfs"  name="dia-in-lxfs" datasource="LXFS" datatype="1,is_null,30" value="春季促销"/></td>
							</tr>
							<tr>
								<td><label>活动类型：</label></td>
							    <td><input type="text" id="dia-in-yzbm"  name="dia-in-yzbm" datasource="YZBM" datatype="1,is_digit,15" value="折扣促销"/></td>
						    </tr>
						    <tr>
						    	<td><label>活动开始日期：</label></td>
							    <td><input type="text" id="dia-in-lxr"  name="dia-in-lxr" datasource="LXR" datatype="1,is_null,30" value="2014-05-22"/></td>
							    <td><label>活动结束日期：</label></td>
							    <td><input type="text" id="dia-in-lxfs"  name="dia-in-lxfs" datasource="LXFS" datatype="1,is_null,30" value="2014-08-31"/></td>
						    </tr>
						    <tr>
						    	<td><label>是否免运费：</label></td>
							    <td>否
							    </td>
						    </tr>
						    <tr>
								<td><label>备注：</label></td>
								<td colspan="3"><textarea id="dia-ta-bz" style="width:89%;height:40px;" name="dia-ta-bz" datasource="BZ"  datatype="1,is_null,100"></textarea></td>
							</tr>
						</table>
						</fieldset>	
					</div>
				</form>
				<div class="formBar">
					<ul>
						<li><div class="button"><div class="buttonContent"><button type="button" id="dia-next0" name="btn-next">下一步</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
				<div id="dia-fjlb" style="overflow:hidden;">
					<table style="display:none;width:100%;" id="dia-tab-fjlb" name="tablist" ref="dia-fjlb" pageRows="15"  >
							<thead>
								<tr>
									<th type="link" title="[查看]|[下载]" action="doView|doFjView" colwidth="100">操作</th>
									<th type="single" name="XH" style="display:none;"></th>
									<th fieldname="FJMC" ordertype='local' class="asc">附件名称</th>
									<th fieldname="CJR" >上传人</th>
									<th fieldname="CJSJ" ordertype='local' class="asc">上传时间</th>
									<th fieldname="FJSM" ordertype='local' class="asc">备注</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td><a href="#" onclick="doReport()" class="op">[查看]</a><a href="#" onclick="doReport()" class="op">[下载]</a></td>
									<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
									<td><div>促销活动细则说明</div></td>
									<td><div>张三</div></td>
									<td><div>2014-05-22 12:30:23</div></td>
									<td><div></div></td>
								</tr>
							</tbody>
					</table>
				</div>
			</div>
			</div>
			<div class="page">
			<div class="pageContent" >
				<div id="dia-pjmx" style="height:340px;overflow:hidden;">
					<table style="display:none;width:100%;" id="dia-tab-pjlb" layoutH="350" name="tablist" ref="dia-pjmx" pageRows="15" >
							<thead>
								<tr>
									<th fieldname="ROWNUMS" style="display:"></th>
									<th type="single" name="XH" style="display:none;" ></th>
									<th fieldname="PJDM" ftype="input" fdatatype="0,is_null,30" freadonly="true">配件图号</th>
									<th fieldname="PJMC" colwidth="150" freadonly="true">配件名称</th>
									<th fieldname="PJID" style="display:none" freadonly="true">配件ID</th>
									<th fieldname="JLDWMC" freadonly="true" colwidth="50">计量单位</th>
									<th fieldname="ZXBZSL" freadonly="true" colwidth="70" >最小包装</th>
									<th fieldname="JXSJ" colwidth="60" ftype="input" fdatatype="0,is_money,30" freadonly="true" >经销商价</th>
									<th fieldname="SL" colwidth="60" fdatatype="0,is_money,30" style="color:red">本次促销价</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td class="rownums"><div>1</div></td>
									<td style="display:none;"><div><input type="checkbox" name="in-cx-xh" /></div></td>
									<td><div>PJ001</div></td>
									<td><div>配件名称1</div></td>
									<td style="display:none"><div>2323232323</div></td>
									<td><div>件</div></td>
									<td><div>10/包</div></td>
									<td><div>800.00</div></td>
									<td><div>700.00</div></td>
								</tr>
								<tr>
									<td class="rownums"><div>2</div></td>
									<td style="display:none;"><div><input type="checkbox" name="in-cx-xh" /></div></td>
									<td><div>PJ002</div></td>
									<td><div>配件名称2</div></td>
									<td style="display:none"><div>2323232323</div></td>
									<td><div>件</div></td>
									<td><div>10/袋</div></td>
									<td><div>800.00</div></td>
									<td><div>700.00</div></td>
								</tr>
								<tr>
									<td class="rownums"><div>3</div></td>
									<td style="display:none;"><div><input type="checkbox" name="in-cx-xh" /></div></td>
									<td><div>PJ003</div></td>
									<td><div>配件名称3</div></td>
									<td style="display:none"><div>2323232323</div></td>
									<td><div>件</div></td>
									<td><div>10/袋</div></td>
									<td><div>800.00</div></td>
									<td><div>700.00</div></td>
								</tr>
								<tr>
									<td class="rownums"><div>4</div></td>
									<td style="display:none;"><div><input type="checkbox" name="in-cx-xh" /></div></td>
									<td><div>PJ004</div></td>
									<td><div>配件名称4</div></td>
									<td style="display:none"><div>2323232323</div></td>
									<td><div>件</div></td>
									<td><div>10/箱</div></td>
									<td><div>800.00</div></td>
									<td><div>700.00</div></td>
								</tr>
								<tr>
									<td class="rownums"><div>5</div></td>
									<td style="display:none;"><div><input type="checkbox" name="in-cx-xh" /></div></td>
									<td><div>PJ005</div></td>
									<td><div>配件名称5</div></td>
									<td style="display:none"><div>2323232323</div></td>
									<td><div>件</div></td>
									<td><div>10/包</div></td>
									<td><div>800.00</div></td>
									<td><div>700.00</div></td>
								</tr>
								<tr>
									<td class="rownums"><div>6</div></td>
									<td style="display:none;"><div><input type="checkbox" name="in-cx-xh" /></div></td>
									<td><div>PJ006</div></td>
									<td><div>配件名称6</div></td>
									<td style="display:none"><div>2323232323</div></td>
									<td><div>件</div></td>
									<td><div>10/包</div></td>
									<td><div>800.00</div></td>
									<td><div>700.00</div></td>
								</tr>
							</tbody>
					</table>
				</div>
			</div>
			<div class="formBar">
					<ul>
						<li><div class="button"><div class="buttonContent"><button type="button"  id="dia-pre1" name="btn-pre">上一步</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="dia-next0" name="btn-next">下一步</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
			</div>
		</div>
		<div class="page">
			<div class="pageContent"  >
				<div id="dia-cxfw" style="overflow:hidden;width:60%;text-align:center;">
					<table style="display:none;width:100%;" id="dia-tab-fwlb" layoutH="350" name="tablist" ref="dia-cxfw">
							<thead>
								<tr>
									<th fieldname="ROWNUMS" style="display:" colwidth="20"></th>
									<th type="multi" name="XH" style="display:;">是否参加本次活动</th>
									<th fieldname="ZHLXDM">办事处代码</th>
									<th fieldname="ZHLXMC">办事处名称</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td class="rownums"><div>1</div></td>
									<td><div><input type="checkbox" name="in-cx-xh" /></div></td>
									<td>B01</td>
									<td>西安办事处</td>
								</tr>
								<tr>
									<td class="rownums"><div>2</div></td>
									<td><input type="checkbox" name="in-cx-xh" /></td>
									<td>B02</td>
									<td>办事处</td>
								</tr>
								<tr>
									<td class="rownums"><div>3</div></td>
									<td><input type="checkbox" name="in-cx-xh" /></td>
									<td>B03</td>
									<td>办事处</td>
								</tr>
								<tr>
									<td class="rownums"><div>4</div></td>
									<td><input type="checkbox" name="in-cx-xh" /></td>
									<td>B04</td>
									<td>办事处</td>
								</tr>
								
								<tr>
									<td class="rownums"><div>5</div></td>
									<td><input type="checkbox" name="in-cx-xh" /></td>
									<td>B05</td>
									<td>办事处</td>
								</tr>
								<tr>
									<td class="rownums"><div>6</div></td>
									<td><input type="checkbox" name="in-cx-xh" /></td>
									<td>B06</td>
									<td>办事处</td>
								</tr>
							</tbody>
					</table>
				</div>
				<div class="formBar">
					<ul>
						<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre2" name="btn-pre">上一步</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>
		</div>
		</div>
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>
</div>
<script type="text/javascript">
var action = "<%=action%>";
$("button[name='btn-pre']").bind("click",function(event){
	$("#dia-tabs").switchTab(parseInt($("#dia-tabs").attr("currentIndex"))-1);
});
$("button[name='btn-next']").bind("click",function(event){
	var $tabs = $("#dia-tabs");
	switch(parseInt($tabs.attr("currentIndex")))
	    {
	   	   case 0:
	   		   break;
	   	   case 1:
	   		   break;
	    }
	   $tabs.switchTab(parseInt($tabs.attr("currentIndex"))+1);
	   //跳转后实现方法
	   (function(ci){
		   switch(parseInt(ci))
 	   {
 	   		case 1://第2个tab页
     	   		//校验是否保存信息，如没保存，则提示首先需保存信息
     	   		//对于修改，需查询配件明细
     	   		if(action == "1")
     	   		{
     	   			if($("#dia-tab-pjlb").is(":hidden"))
     	   			{
     	   				$("#dia-tab-pjlb").show();
     	   				$("#dia-tab-pjlb").jTable();
     	   			}
     	   		}else
     	   		{
     	   		}
 	   			break;
 	   		default:
 	   			break;
 	   }
	   })(parseInt($tabs.attr("currentIndex")));
});

//弹出窗体
var diaDialog = $("body").data("cxxxmx");
$(function()
{
	$("input,textarea",$("#dia-cxhdmx")).attr("readonly", "true");
	$("#dia-tab-fjlb").show();
	$("#dia-tab-fjlb").jTable();
	$("#dia-tab-fwlb").show();
	$("#dia-tab-fwlb").jTable();
	
	$("button.close").click(function(){
		$.pdialog.close(diaDialog);
		return false;
	});
});

</script>