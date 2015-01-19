<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%@page import="com.org.frameImpl.Constant"%>
<%@page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contextPath = request.getContextPath();
	String id = request.getParameter("id");
	if(id == null) id = "";
	String busType = request.getParameter("busType");
	if(busType == null) busType = "";
	String partOrg = request.getParameter("partOrg");
	if(partOrg == null) partOrg = "";
	String multi = request.getParameter("multi");
	if(multi == null) multi = "1";
%>
<div id="dia-showOrgTreeInfo">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="fm-orgTreeInfo" class="editForm" >
			<!-- 隐藏域 -->
			<div align="left" style="padding-bottom:5px;">
				<table class="editTable" id="tab-showOrgTreeInfo">
					<tr><td><label>代码：</label></td>
					    <td><input type="text" id="dia-orgTreeCode" name="dia-orgTreeCode" datasource="CODE" datatype="1,is_digit_letter,30" /></td>
					    <td><label>名称：</label></td>
					    <td><input type="text" id="dia-orgTreeName" name="dia-orgTreeName" datasource="ONAME" datatype="1,is_null,100" /></td>
					    <td>
					    	<div class="button"><div class="buttonContent"><button type="button" id="btn-showOrgTreeSearch">查&nbsp;&nbsp;询</button></div></div>
							<div class="button" style="margin-left:5px;"><div class="buttonContent"><button class="close" id="btn-showOrgTreeClose" type="button">关&nbsp;&nbsp;闭</button></div></div>
					    </td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	</div>
	<div class="divider"></div>
	<div style="width:100%;height:100%;">
		<div id="diaShowOrgTreeLeft" style=" position:absolute;float:left; display:block; margin:0px; overflow:auto; width:200px; height:350px; border:solid 1px #CCC; line-height:21px; background:#FFF;">
			<ul class="tree"></ul>
		</div>
		<div id="diaShowOrgRight" style=" float:left; display:block; position:absolute;margin-left:220px;overflow:auto; width:540px; height:350px; border:solid 1px #CCC; line-height:21px; background:#FFF;">
			<div id="page_showOrgRightList" style="height:300px;overflow-y:auto;overflow-x:hidden;">
				<table style="width:100%;" class="dlist" id="tab-showOrgRightList" multivals="div-orgTreeMul" ref="page_showOrgRightList">
					<thead>
						<tr>
							<th style="width:50px;text-align:center;" unique="c">序号</th>
							<th style="width:30px;text-align:center;">
								<%
									if("1".equals(multi)){
								%>
									<input type="checkbox" onclick="doOrgTreeListCheck(this)" />
								<%
									}else{
								%>
									<input type="radio" onclick="doOrgTreeListCheck(this)" />
								<%
									}
								%>
							</th>
							<th fieldname="CODE"  style="width:120px;">代码</th>
							<th fieldname="SNAME" >简称</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
			<div id="div-orgTreeMul">
				<div class="button" style="float:right;margin-right:10px;"><div class="buttonContent"><button type="button" id="btn-showOrgTreeOk">确认</button></div></div>
				<textarea style="display:none;width:450px;height:30px;overflow:auto;" id="orgTree-val0" name="multivals" column="1" readOnly="true"></textarea>
				<textarea style="width:450px;height:30px;overflow:auto;" id="orgTree-val1" name="multivals" readOnly="true"></textarea>
				<textarea style="display:none;width:450px;height:30px;overflow:auto;" id="orgTree-val2" name="multivals" readOnly="true"></textarea>
			</div>
		</div>
	</div>
</div>
<script src="<%=contextPath %>/lib/core/sci.tree.js" type="text/javascript"></script>
<script type="text/javascript">
//弹出窗体
var dialogShowTree = $("body").data("showOrgTree");
var diaShowOrgTreeSId = "<%=id%>";
var diaShowOrgTreeSBusType = "<%=busType%>";
var diaShowOrgTreeSPartOrg = "<%=partOrg%>";
var diaShowOrgTreeSMulti = "<%=multi%>";
$(function()
{
	if(diaShowOrgTreeSMulti == "1")
		$("#div-orgTreeMul").show();
	else
		$("#div-orgTreeMul").hide();
	$("#btn-showOrgTreeClose").bind("click",function(){
		$.pdialog.close(dialogShowTree);
		return false;
	});
	
	$("#btn-showOrgTreeOk").bind("click",function(){
		if(!$("#orgTree-val0").val()){alertMsg.warn("请选择组织!");return false;}
	
		$("#"+diaShowOrgTreeSId).val($("#orgTree-val2").val());
		$("#"+diaShowOrgTreeSId).attr("code", $("#orgTree-val0").val());
		try{showOrgTreeCallBack({"orgId":$("#orgTree-val0").val(),"orgCode":$("#orgTree-val2").val(),"orgName":$("#orgTree-val1").val()});}catch(e){}
		$.pdialog.close(dialogShowTree);
	});
	
	$("#btn-showOrgTreeClear").bind("click",function(){
		$("#"+diaShowOrgTreeSId).val("");
		$("#"+diaShowOrgTreeSId).attr("code", "");
	});
	
	$("#btn-showOrgTreeSearch").bind("click",function(){
		var oCode =$("#dia-orgTreeCode").val();
		var sName = $("#dia-orgTreeName").val();
		var $tab = $("#tab-showOrgRightList");
		if(!oCode && !sName)
		{
			alertMsg.warn("请输入组织代码或组织名称！");
			return false;
		}
		if(orgTreeDoc != null)
		{
			orgTreeDoc = createDOMDocument();
			if(bOs == 6 || bOs ==3)
		    {
		        var xmlhttp = new window.XMLHttpRequest();
		        xmlhttp.open("GET", g_sDicPath + "ZZJG" + ".xml", false);
		        xmlhttp.send(null);
		        orgTreeDoc = loadFromStr(xmlhttp.responseText);
		    }else
		    	try{orgTreeDoc.load(g_sDicPath + "ZZJG" + ".xml");}catch(e){
                   	var xmlhttp = new window.XMLHttpRequest();
                   	xmlhttp.open("GET", g_sDicPath + "ZZJG" + ".xml", false);
                   	xmlhttp.send(null);
                   	//orgTreeDoc = xmlhttp.responseXML;
                   	orgTreeDoc = loadFromStr(xmlhttp.responseText);
		    	}
		}
		if(oCode && !sName) //按组织代码检索
		{
			var rows = orgTreeDoc.getElementsByTagName("R");
			if(rows && rows.length > 0)
			{
				var rowIndex = 1;
				$tab.clearRows();
				for(var i=0;i<rows.length;i++)
				{
					var oc = rows[i].getAttribute("oc");
					var lc = rows[i].getAttribute("lc");
					var bt = rows[i].getAttribute("bt");
					var ot = rows[i].getAttribute("ot");
					if(oc.indexOf(oCode) >=0 && lc == "100705")
					{
						if(diaShowOrgTreeSBusType == "1" && diaShowOrgTreeSPartOrg == "1" && ot == "<%=DicConstant.ZZLB_09%>")
						{
							genSingleRow($tab,rowIndex,rows[i].getAttribute("oc"),rows[i].getAttribute("t"),rows[i].getAttribute("c"));
							rowIndex++;
						}else if(diaShowOrgTreeSBusType == "1" && diaShowOrgTreeSPartOrg == "2")
						{
							if(ot == "<%=DicConstant.ZZLB_10%>" || ot == "<%=DicConstant.ZZLB_11%>")
							{
								genSingleRow($tab,rowIndex,rows[i].getAttribute("oc"),rows[i].getAttribute("t"),rows[i].getAttribute("c"));
								rowIndex++;
							}
						}else if(diaShowOrgTreeSBusType == "2" && ot == "<%=DicConstant.ZZLB_11%>")
						{
							genSingleRow($tab,rowIndex,rows[i].getAttribute("oc"),rows[i].getAttribute("t"),rows[i].getAttribute("c"));
							rowIndex++;
						}else if(diaShowOrgTreeSBusType == "1" && diaShowOrgTreeSPartOrg == "" && (ot == "<%=DicConstant.ZZLB_09%>" || ot == "<%=DicConstant.ZZLB_10%>" || ot == "<%=DicConstant.ZZLB_11%>")){
							// 此逻辑添加：当partOrg 不传参数时，则配送中心，经销商全部显示
							genSingleRow($tab,rowIndex,rows[i].getAttribute("oc"),rows[i].getAttribute("t"),rows[i].getAttribute("c"));
							rowIndex++;
						}
						
					}else
						continue;
				}
			}
				
		}else if(!oCode && sName) //按组织名称检索
		{
			var rows = orgTreeDoc.getElementsByTagName("R");
			if(rows && rows.length > 0)
			{
				var rowIndex = 1;
				$tab.clearRows();
				for(var i=0;i<rows.length;i++)
				{
					var t = rows[i].getAttribute("t");
					var lc = rows[i].getAttribute("lc");
					var bt = rows[i].getAttribute("bt");
					var ot = rows[i].getAttribute("ot");
					if(t.indexOf(sName) >=0 && lc == "<%=DicConstant.JGJB_05%>")
					{
						if(diaShowOrgTreeSBusType == "1" && diaShowOrgTreeSPartOrg == "1" && ot == "<%=DicConstant.ZZLB_09%>")
						{
							genSingleRow($tab,rowIndex,rows[i].getAttribute("oc"),rows[i].getAttribute("t"),rows[i].getAttribute("c"));
							rowIndex++;
						}else if(diaShowOrgTreeSBusType == "1" && diaShowOrgTreeSPartOrg == "2")
						{
							if(ot == "<%=DicConstant.ZZLB_10%>" || ot == "<%=DicConstant.ZZLB_11%>")
							{
								genSingleRow($tab,rowIndex,rows[i].getAttribute("oc"),rows[i].getAttribute("t"),rows[i].getAttribute("c"));
								rowIndex++;
							}
						}else if(diaShowOrgTreeSBusType == "2" && ot == "<%=DicConstant.ZZLB_11%>")
						{
							genSingleRow($tab,rowIndex,rows[i].getAttribute("oc"),rows[i].getAttribute("t"),rows[i].getAttribute("c"));
							rowIndex++;
						}else if(diaShowOrgTreeSBusType == "1" && diaShowOrgTreeSPartOrg == "" && (ot == "<%=DicConstant.ZZLB_09%>" || ot == "<%=DicConstant.ZZLB_10%>"|| ot == "<%=DicConstant.ZZLB_11%>")){
							// 此逻辑添加：当partOrg 不传参数时，则配送中心，经销商全部显示
							genSingleRow($tab,rowIndex,rows[i].getAttribute("oc"),rows[i].getAttribute("t"),rows[i].getAttribute("c"));
							rowIndex++;
						}
					}else
						continue;
				}
			}	
		}else //按组织名称+代码检索
		{
			var rows = orgTreeDoc.getElementsByTagName("R");
			if(rows && rows.length > 0)
			{
				var rowIndex = 1;
				$tab.clearRows();
				for(var i=0;i<rows.length;i++)
				{
					var t = rows[i].getAttribute("t");
					var oc = rows[i].getAttribute("oc");
					var lc = rows[i].getAttribute("lc");
					var bt = rows[i].getAttribute("bt");
					var ot = rows[i].getAttribute("ot");
					if(oc.indexOf(oCode) >=0 && t.indexOf(sName) >=0 && lc == "<%=DicConstant.JGJB_05%>")
					{
						if(diaShowOrgTreeSBusType == "1" && diaShowOrgTreeSPartOrg == "1" && ot == "<%=DicConstant.ZZLB_09%>")
						{
							genSingleRow($tab,rowIndex,rows[i].getAttribute("oc"),rows[i].getAttribute("t"),rows[i].getAttribute("c"));
							rowIndex++;
						}else if(diaShowOrgTreeSBusType == "1" && diaShowOrgTreeSPartOrg == "2")
						{
							if(ot == "<%=DicConstant.ZZLB_10%>" || ot == "<%=DicConstant.ZZLB_11%>")
							{
								genSingleRow($tab,rowIndex,rows[i].getAttribute("oc"),rows[i].getAttribute("t"),rows[i].getAttribute("c"));
								rowIndex++;
							}
						}else if(diaShowOrgTreeSBusType == "2" && ot == "<%=DicConstant.ZZLB_11%>")
						{
							genSingleRow($tab,rowIndex,rows[i].getAttribute("oc"),rows[i].getAttribute("t"),rows[i].getAttribute("c"));
							rowIndex++;
						}else if(diaShowOrgTreeSBusType == "1" && diaShowOrgTreeSPartOrg == "" && (ot == "<%=DicConstant.ZZLB_09%>" || ot == "<%=DicConstant.ZZLB_10%>"|| ot == "<%=DicConstant.ZZLB_11%>")){
							// 此逻辑添加：当partOrg 不传参数时，则配送中心，经销商全部显示
							genSingleRow($tab,rowIndex,rows[i].getAttribute("oc"),rows[i].getAttribute("t"),rows[i].getAttribute("c"));
							rowIndex++;
						}
					}else
						continue;
				}
			}	
		}
		if($("tbody>tr",$tab).size() == 0)
		{
			var $row = $("<tr></tr>");
			var $td0 = $("<td colspan='4' style='text-align:center;'>没有查询到符合条件的数据</td>");
			$row.append($td0);
			$tab.find("tbody").append($row);
		}
	});
});
var orgTreeDoc = null;
$(function(){
	//加载dom
	orgTreeDoc = createDOMDocument();
	if(bOs == 6 || bOs ==3)
    {
        var xmlhttp = new window.XMLHttpRequest();
        xmlhttp.open("GET", g_sDicPath + "ZZJG" + ".xml", false);
        xmlhttp.send(null);
        orgTreeDoc = loadFromStr(xmlhttp.responseText);
    }else
    {
    	try{orgTreeDoc.load(g_sDicPath + "ZZJG" + ".xml");}catch(e){
           	var xmlhttp = new window.XMLHttpRequest();
           	xmlhttp.open("GET", g_sDicPath + "ZZJG" + ".xml", false);
           	xmlhttp.send(null);
           	orgTreeDoc = loadFromStr(xmlhttp.responseText);
    	}
    }
	genOrgTree(orgTreeDoc);
});

function genOrgTree(xDoc)
{
	//set left
	var $urlTree = $("ul:first",$("#diaShowOrgTreeLeft"));
	var rows = xDoc.getElementsByTagName("R");
	if(rows && rows.length > 0)
	{
		for(var i=0;i<rows.length;i++)
		{
			var lc = rows[i].getAttribute("lc");
			var p = rows[i].getAttribute("p");
			var c = rows[i].getAttribute("c");
			var t = rows[i].getAttribute("t");
			switch(lc)
			{
				case "<%=DicConstant.JGJB_01%>"://销售公司:10000001为陕汽集团
					if(p == "10000001")
					{
						var $li = genOrgTreeLi(lc,p,c,t);
						$urlTree.append($li);
					}
					break;
				case "<%=DicConstant.JGJB_03%>"://大区
					var $li = genOrgTreeLi(lc,p,c,t);
					var $pLi = $urlTree.find("li[c='"+p+"']:first");
					var $tmpUl = $pLi.find("ul[c='"+p+"']:first");
					if($tmpUl.size() == 0)
					{
						var $ul = genOrgTreeUl();
						$ul.append($li);
						$pLi.append($ul);
					}
					else
						$tmpUl.append($li);
					break;
				case "<%=DicConstant.JGJB_04%>"://小区、办事处
					var $li = genOrgTreeLi(lc,p,c,t);
					var $pLi = $urlTree.find("li[c='"+p+"']:first");
					var $tmpUl = $pLi.find("ul[c='"+p+"']:first");
					if($tmpUl.size() == 0)
					{
						var $ul = genOrgTreeUl();
						$ul.append($li);
						$pLi.append($ul);
					}
					else
						$tmpUl.append($li);
					break;
			}
		}
	}
	//alert($urlTree.html());
}
function genOrgTreeLi(lc,p,c,t)
{
	var $li = $("<li></li>");
	$li.attr("lc",lc);
	$li.attr("p",p);
	$li.attr("c",c)
	
	var $a = $("<a href='javascript:void(0);' nodeclick='false' onclick='doSelOrgTree(this,event);'></a>");
	$a.html(t);
	$li.append($a);
	return $li;
}
function genOrgTreeUl()
{
	var $ul = $("<ul></ul>");
	return $ul;
}
var curOrgTreeId;
var node0 = false;//判断点击的节点是否是跟节点（销售公司）
function doSelOrgTree(aObj,event)
{
	try{
		event.preventDefault();		
	}catch(e){}
	var $a = $(aObj);
	var $li = $a;
	while(!$li.isTag("li"))
	{
		$li = $li.parent();
	}
	if($li.attr("lc") == "<%=DicConstant.JGJB_01%>")
		node0 = true;
	else
		node0 = false;
	if($li.attr("p") != "0")
		genRightList($li.attr("c"));
	try{event.stopPropagation();}catch(e){}
	
}

function genRightList(c)
{
	var orgTreeHm = new HashMap();
	//if(curOrgTreeId == c)
		//return;
	var rowIndex = 1;
	var $tab = $("#tab-showOrgRightList");
	$tab.clearRows();
	var rows = orgTreeDoc.getElementsByTagName("R");
	if(rows && rows.length > 0)
	{
		if(node0 == false)
		{
			for(var i=0;i<rows.length;i++)
			{
				var p = rows[i].getAttribute("p");
				var bt = rows[i].getAttribute("bt");
				var ot = rows[i].getAttribute("ot");
				if(orgTreeHm.get(p))
				{
					if(diaShowOrgTreeSBusType == "1" && diaShowOrgTreeSPartOrg == "1" && ot == "<%=DicConstant.ZZLB_09%>")
					{
						genSingleRow($tab,rowIndex,rows[i].getAttribute("oc"),rows[i].getAttribute("t"),rows[i].getAttribute("c"));
						rowIndex++;
					}else if(diaShowOrgTreeSBusType == "1" && diaShowOrgTreeSPartOrg == "2")
					{
						if(ot == "<%=DicConstant.ZZLB_10%>" || ot == "<%=DicConstant.ZZLB_11%>")
						{
							genSingleRow($tab,rowIndex,rows[i].getAttribute("oc"),rows[i].getAttribute("t"),rows[i].getAttribute("c"));
							rowIndex++;
						}
					}else if(diaShowOrgTreeSBusType == "2" && ot == "<%=DicConstant.ZZLB_11%>")
					{
						genSingleRow($tab,rowIndex,rows[i].getAttribute("oc"),rows[i].getAttribute("t"),rows[i].getAttribute("c"));
						rowIndex++;
					}else if(diaShowOrgTreeSBusType == "1" && diaShowOrgTreeSPartOrg == "" && (ot == "<%=DicConstant.ZZLB_09%>" || ot == "<%=DicConstant.ZZLB_10%>"|| ot == "<%=DicConstant.ZZLB_11%>")){
						// 此逻辑添加：当partOrg 不传参数时，则配送中心，经销商全部显示
						genSingleRow($tab,rowIndex,rows[i].getAttribute("oc"),rows[i].getAttribute("t"),rows[i].getAttribute("c"));
						rowIndex++;
					}
					continue;
				}else
				{
					if(p != c)
						continue;
				}
				if(rows[i].getAttribute("lc") == "<%=DicConstant.JGJB_04%>")//小区、办事处
				{
					var cc = rows[i].getAttribute("c");
					orgTreeHm.put(cc,cc);
				}else //渠道
				{
					var bt = rows[i].getAttribute("bt");
					var ot = rows[i].getAttribute("ot");
					if(diaShowOrgTreeSBusType == "1" && diaShowOrgTreeSPartOrg == "1" && ot == "<%=DicConstant.ZZLB_09%>")
					{
						genSingleRow($tab,rowIndex,rows[i].getAttribute("oc"),rows[i].getAttribute("t"),rows[i].getAttribute("c"));
						rowIndex++;
					}else if(diaShowOrgTreeSBusType == "1" && diaShowOrgTreeSPartOrg == "2")
					{
						if(ot == "<%=DicConstant.ZZLB_10%>" || ot == "<%=DicConstant.ZZLB_11%>")
						{
							genSingleRow($tab,rowIndex,rows[i].getAttribute("oc"),rows[i].getAttribute("t"),rows[i].getAttribute("c"));
							rowIndex++;
						}
					}else if(diaShowOrgTreeSBusType == "2" && ot == "<%=DicConstant.ZZLB_11%>")
					{
						genSingleRow($tab,rowIndex,rows[i].getAttribute("oc"),rows[i].getAttribute("t"),rows[i].getAttribute("c"));
						rowIndex++;
					}else if(diaShowOrgTreeSBusType == "1" && diaShowOrgTreeSPartOrg == "" && (ot == "<%=DicConstant.ZZLB_09%>" || ot == "<%=DicConstant.ZZLB_10%>"|| ot == "<%=DicConstant.ZZLB_11%>")){
						// 此逻辑添加：当partOrg 不传参数时，则配送中心，经销商全部显示
						genSingleRow($tab,rowIndex,rows[i].getAttribute("oc"),rows[i].getAttribute("t"),rows[i].getAttribute("c"));
						rowIndex++;
					}
					continue;
				}
			}
		}else //点击根节点
		{
			/**
			//alert(diaShowOrgTreeSBusType+":"+diaShowOrgTreeSPartOrg);
			for(var i=0;i<rows.length;i++)
			{
				var p = rows[i].getAttribute("p");
				var bt = rows[i].getAttribute("bt");
				var ot = rows[i].getAttribute("ot");
				if(rows[i].getAttribute("lc") != "<%=DicConstant.JGJB_05%>") continue;//只显示渠道网点层级
				
			 	if(diaShowOrgTreeSBusType == "1" && diaShowOrgTreeSPartOrg == "1" && ot == "<%=DicConstant.ZZLB_09%>")
				{
					genSingleRow($tab,rowIndex,rows[i].getAttribute("oc"),rows[i].getAttribute("t"),rows[i].getAttribute("c"));
					rowIndex++;
				}else if(diaShowOrgTreeSBusType == "1" && diaShowOrgTreeSPartOrg == "2")
				{
					if(ot == "<%=DicConstant.ZZLB_10%>" || ot == "<%=DicConstant.ZZLB_11%>")
					{
						genSingleRow($tab,rowIndex,rows[i].getAttribute("oc"),rows[i].getAttribute("t"),rows[i].getAttribute("c"));
						rowIndex++;
					}
				}else if(diaShowOrgTreeSBusType == "2" && ot == "<%=DicConstant.ZZLB_11%>")
				{
					genSingleRow($tab,rowIndex,rows[i].getAttribute("oc"),rows[i].getAttribute("t"),rows[i].getAttribute("c"));
					rowIndex++;
				}else if(diaShowOrgTreeSBusType == "1" && diaShowOrgTreeSPartOrg == "" && (ot == "<%=DicConstant.ZZLB_09%>" || ot == "<%=DicConstant.ZZLB_10%>"|| ot == "<%=DicConstant.ZZLB_11%>")){
					// 此逻辑添加：当partOrg 不传参数时，则配送中心，经销商全部显示
					genSingleRow($tab,rowIndex,rows[i].getAttribute("oc"),rows[i].getAttribute("t"),rows[i].getAttribute("c"));
					rowIndex++;
				}
				//genSingleRow($tab,rowIndex,rows[i].getAttribute("oc"),rows[i].getAttribute("t"),rows[i].getAttribute("c"));
				//rowIndex++;
				continue;
			}
			*/
		}
	}
	if($("tbody>tr",$tab).size() == 0)
	{
		var $row = $("<tr></tr>");
		var $td0 = $("<td colspan='4' style='text-align:center;'>没有查询到符合条件的数据</td>");
		$row.append($td0);
		$tab.find("tbody").append($row);
	}
	curOrgTreeId = c;
	orgTreeHm = null;
}
function genSingleRow($tab,rowIndex,oc,t,c)
{
	var $row = $("<tr></tr>");
	var $td0 = $("<td style='text-align:center;'>"+rowIndex+"</td>");
	//判断是否已选择
	var val0 = $("#orgTree-val0").val();
	var $td1;
	var taglib = diaShowOrgTreeSMulti=="1"?"checkbox":"radio";
	if(val0.indexOf(c) >=0 )
		$td1 = $("<td style='text-align:center;'><input type='"+taglib+"' checked='true' orgCode='"+oc+"' orgId='"+c+"' orgName='"+t+"' name='orgTreeChecks' onclick='doOrgTreeCheckbox(this);'></td>");
	else
		$td1 = $("<td style='text-align:center;'><input type='"+taglib+"' orgCode='"+oc+"' orgId='"+c+"' orgName='"+t+"' name='orgTreeChecks' onclick='doOrgTreeCheckbox(this);'></td>");
	var $td2 = $("<td>"+oc+"</td>");
	var $td3 = $("<td>"+t+"</td>");
	$row.append($td0);
	$row.append($td1);
	$row.append($td2);
	$row.append($td3);
	$tab.find("tbody").append($row);
}
function doOrgTreeCheckbox(inputH)
{
	var $checkbox = $(inputH);
	if(diaShowOrgTreeSMulti == "1") //复选
	{
		var arr = [];
		arr.push($checkbox.attr("orgId"));
		arr.push($checkbox.attr("orgName"));
		arr.push($checkbox.attr("orgCode"));
		multiSelected($checkbox,arr,$("#div-orgTreeMul"));
	}else							//单选
	{
		$("#orgTree-val0").val($checkbox.attr("orgId"));
		$("#orgTree-val1").val($checkbox.attr("orgName"));
		$("#orgTree-val2").val($checkbox.attr("orgCode"));
		$("#"+diaShowOrgTreeSId).val($("#orgTree-val2").val());
		$("#"+diaShowOrgTreeSId).attr("code", $("#orgTree-val0").val());
		try{showOrgTreeCallBack({"orgId":$("#orgTree-val0").val(),"orgCode":$("#orgTree-val2").val(),"orgName":$("#orgTree-val1").val()});}catch(e){}
		$.pdialog.close(dialogShowTree);
	}
	
}
function doOrgTreeListCheck(inputH)
{
	var $hCheckbox = $(inputH);
	if(diaShowOrgTreeSMulti == "1") //复选
	{
		if($hCheckbox.is(":checked") == false) //全部取消
		{
			var $tab = $("#tab-showOrgRightList");
			$("input[name='orgTreeChecks']",$tab).attr("checked",false);
			$("#orgTree-val0").val("");
			$("#orgTree-val1").val("");
			$("#orgTree-val2").val("");
		}else //全部选中
		{
			var $tab = $("#tab-showOrgRightList");
			$("#orgTree-val0").val("");
			$("#orgTree-val1").val("");
			$("#orgTree-val2").val("");
			$("input[name='orgTreeChecks']",$tab).each(function(){
				$(this).attr("checked",true);
				doOrgTreeCheckbox(this);
			});
		}
	}else   //单选
	{
		if($hCheckbox.is(":checked") == true) //全部取消
		{
			var $tab = $("#tab-showOrgRightList");
			$("input[name='orgTreeChecks']",$tab).attr("checked",false);
			$("#orgTree-val0").val("");
			$("#orgTree-val1").val("");
			$("#orgTree-val2").val("");
		}
	}
}
</script>