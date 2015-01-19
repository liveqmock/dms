<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.org.dms.common.DicConstant"%>
<%
	String claimDtlId = request.getParameter("claimDtlId");
%>
<div id="dia-layout"  style="overflow:auto;">
	<form id="dia-SearchTabs" name="dia-SearchTabs" style="diaplay:none"></form>
	<div id="dia-div-ljxx" >
	    <div class="tabs" id="example">
			  <div class="tabsHeader" id="dia-tabsHeader">
				<div class="tabsHeaderContent" id="dia-tabsHeaderContent" >
				<ul id="dia-partTabs" ></ul>
				</div>
			  </div>
		      <div class="tabsContent" id="dia-partContent" >
		      </div>	
       </div>	
   </div>
   <div class="formBar" >
       <ul>
	        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-tabs-add" name="dia-tabs-add" onclick="addPartTabs(2);" >添加配件</button></div></div></li>
			<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-tabs-save" name="dia-tabs-save" onclick="saveTabs();">保存配件</button></div></div></li>
			<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-tabs-del" name="dia-tabs-del" onclick="delTabs();">删除配件</button></div></div></li>
			<li><div class="buttonActive"><div class="buttonContent"><button type="button" class="close">关&nbsp;&nbsp; 闭</button></div></div></li>
	   </ul>		
	</div>
	<div class="tabsFooter">
		<div class="tabsFooterContent"></div>
	</div>
<script type="text/javascript">
var liLength=0;
var claimDtlId="<%=claimDtlId%>";//故障ID
var diaCliamId=$("#dia-claimId").val();//索赔单ID 
var diafaultPartAction = "<%=request.getContextPath()%>/service/claimmng/WorkOrderMngAction";//action前缀
var diaHtmlId;//页面存储当前的动态操作ID
var maxPartCout=100;//最多添加配件数量
var thisTitle=$("body").data("claimPartAdd").find("h1").text();//当前页面的Title
//取索赔单的类型
var partClaimType=$("#dia-in-splx").attr("code");
//取服务活动的处理方式
var partClfs=$("#dia-in-fwhclfs").val();
$(function(){
	$("#example").tabs({width: $("#example").parent().width(),height: "auto" }); 
	$("#dia-partTabs a").live("click", function() {
        var contentname = $(this).attr("id") + "_content";
        $("#dia-partContent div").hide();
        $("#dia-partTabs li").removeClass("selected");
        $("#" + contentname).show();
        $(this).parent().addClass("selected");
    });
	liLength=$("#dia-partTabs li").length;//当前添加配件的个数
	//先查询是否有已添加的零件信息
	searchPartTabs();
});
function searchPartTabs()
{
	var searchPartTabsUrl = diafaultPartAction + "/searchPartTabs.ajax?claimDtlId="+claimDtlId+"&maxPartCout="+maxPartCout;
	sendPost(searchPartTabsUrl,"","",searchPartTabsBack,"false");
}
//先查询已添加的零件信息回调方法
function searchPartTabsBack(res)
{
	try
	{
		var rows = res.getElementsByTagName("ROW");
		if(rows && rows.length > 0)
		{
			for(var i=0;i<rows.length;i++)
			{
				var id=addPartTabs();//先执行添加一条记录
				var objXML;
				if (typeof(rows[i]) == "object") objXML = rows[i];
				else objXML = $.parseXML(rows[i]);
				var faultPartId =getNodeValue(objXML, "FAULT_PART_ID", 0);//故障零件表主键
				var faultTypeCode =getNodeValue(objXML, "FAULT_TYPE", 0);//故障类别--代码
				var faultTypeName =getAttribValue(objXML, "FAULT_TYPE","sv", 0);//故障类别--名称
				var measuresCode =getNodeValue(objXML, "MEASURES", 0);//处理措施--代码
				var measuresName =getAttribValue(objXML, "MEASURES","sv", 0);//处理措施--名称
				var partType =getNodeValue(objXML, "PART_TYPE", 0);//配件类别
				var partTypeName =getAttribValue(objXML, "PART_TYPE","sv", 0);//配件类别--名称
				var oldPartCode =getNodeValue(objXML, "OLD_PART_CODE", 0);//旧件代码
				var oldPartName =getNodeValue(objXML, "OLD_PART_NAME", 0);//旧件名称
				var oldPartId =getNodeValue(objXML, "OLD_PART_ID", 0);//旧件ID
				var oldSupplierCode =getNodeValue(objXML, "OLD_SUPPLIER_CODE", 0);//旧件供应商名称
				var oldSupplierId =getNodeValue(objXML, "OLD_SUPPLIER_ID", 0);//旧件供应商ID
				//var firstPartCode =getNodeValue(objXML, "FIRST_PART_ID", 0);//祸首件代码
				//var firstPartName =getNodeValue(objXML, "FIRST_PART_CODE", 0);//祸首件名称
				//var firstPartId =getNodeValue(objXML, "FIRST_PART_ID", 0);//祸首件ID
				var oldPartCount=getNodeValue(objXML, "OLD_PART_COUNT", 0);//旧件数量
				var oldPartStream=getNodeValue(objXML, "OLD_PART_STREAM", 0);//旧件零件流水号
				var repayUprice=getNodeValue(objXML, "REPAY_UPRICE", 0);//追偿单价	
				var newPartCode =getNodeValue(objXML, "NEW_PART_CODE", 0);//新件代码
				var newPartName =getNodeValue(objXML, "NEW_PART_NAME", 0);//新件名称
				var newPartId =getNodeValue(objXML, "NEW_PART_ID", 0);//新件ID
				var newSupplierCode =getNodeValue(objXML, "NEW_SUPPLIER_CODE", 0);//新件供应商名称
				var newSupplierId =getNodeValue(objXML, "NEW_SUPPLIER_ID", 0);//新件供应商ID
				var newPartCount=getNodeValue(objXML, "NEW_PART_COUNT", 0);//新件数量
				var newPartStream=getNodeValue(objXML, "NEW_PART_STREAM", 0);//新件零件流水号
				var newPartFromCode=getNodeValue(objXML, "NEW_PART_FROM", 0);//新件来源--代码
				var newPartFromName =getAttribValue(objXML, "NEW_PART_FROM","sv", 0);//新件来源--名称
				var claimUprice=getNodeValue(objXML, "CLAIM_UPRICE", 0);//索赔单价
				var claimCosts=getNodeValue(objXML, "CLAIM_COSTS", 0);//索赔材料费
				var faultReason=getNodeValue(objXML, "FAULT_REASON", 0);//故障原因		
				var vehicleMax=getNodeValue(objXML, "VEHICLE_MAX", 0);//单车最大装车件数	
				var ifWorkMultiple=getNodeValue(objXML, "IF_WORK_MULTIPLE", 0);//工时倍数
				//var severityCode=getNodeValue(objXML, "SEVERITY", 0);//严重程度--代码
				//var severityName =getNodeValue(objXML, "SEVERITYNAME", 0);//严重程度--名称
				var ifReturn=getNodeValue(objXML, "IF_RETURN", 0);//是否回运
				var bridgeStatus=getNodeValue(objXML, "BRIDGE_STATUS", 0);//是否校验桥编码
				var bridgeSupplierNo=getNodeValue(objXML, "BRIDGE_SUPPLIER_NO", 0);//桥供应商号
				var bridgeCode=getNodeValue(objXML, "BRIDGE_CODE", 0);//桥编码
				var bridgeTypeCode=getNodeValue(objXML, "BRIDGE_TYPE", 0);//桥类型code
				var bridgeTypeName=getAttribValue(objXML, "BRIDGE_TYPE","sv", 0);//桥类型name
				if(faultPartId) $("#"+id+"_faultPartId").val(faultPartId);
				if(faultTypeCode)$("#"+id+"_faultType").attr("code",faultTypeCode);
				if(faultTypeName)$("#"+id+"_faultType").val(faultTypeName);
				if(measuresCode)$("#"+id+"_measures").attr("code",measuresCode);
				if(measuresName)$("#"+id+"_measures").val(measuresName);
				if(partType)$("#"+id+"_partType").val(partType);
				if(partTypeName)$("#"+id+"_partTypeName").val(partTypeName);
				if(oldPartCode)$("#"+id+"_oldPartCode").val(oldPartCode);
				if(oldPartName)$("#"+id+"_oldPartName").val(oldPartName);
				if(oldPartId)$("#"+id+"_oldPartId").val(oldPartId);
				if(oldSupplierCode)$("#"+id+"_oldSupplierCode").val(oldSupplierCode);
				if(oldSupplierId)$("#"+id+"_oldSupplierId").val(oldSupplierId);
				if(oldPartCount)$("#"+id+"_oldPartCount").val(oldPartCount);
				if(oldPartStream)$("#"+id+"_oldPartStream").val(oldPartStream);
				if(repayUprice)$("#"+id+"_repayUprice").val(repayUprice);
				if(faultReason)$("#"+id+"_faultReason").val(faultReason);
				//if(severityCode)$("#"+id+"_severity").attr("code",severityCode);
				//if(severityName)$("#"+id+"_severity").val(severityName);
				if(ifReturn)$("#"+id+"_ifReturn").val(ifReturn);
				if(bridgeStatus)$("#"+id+"_bridgeStatus").val(bridgeStatus);
				if(bridgeSupplierNo)$("#"+id+"_bridgeSupplierNo").val(bridgeSupplierNo);
				if(newPartCode)$("#"+id+"_newPartCode").val(newPartCode);
				if(newPartName)$("#"+id+"_newPartName").val(newPartName);
				if(newPartId)$("#"+id+"_newPartId").val(newPartId);
				if(newSupplierCode)$("#"+id+"_newSupplierCode").val(newSupplierCode);
				if(newSupplierId)$("#"+id+"_newSupplierId").val(newSupplierId);
				if(newPartCount)$("#"+id+"_newPartCount").val(newPartCount);
				if(newPartStream)$("#"+id+"_newPartStream").val(newPartStream);
				if(newPartFromCode)$("#"+id+"_newPartFrom").attr("code",newPartFromCode);
				if(newPartFromName)$("#"+id+"_newPartFrom").val(newPartFromName);
				if(claimUprice)$("#"+id+"_claimUprice").val(claimUprice);
				if(claimCosts)$("#"+id+"_claimCosts").val(claimCosts);
				$("#"+id+"_vehicleMax").val(vehicleMax);
				$("#"+id+"_ifWorkMultiple").val(ifWorkMultiple);
				if(bridgeCode)$("#"+id+"_bridgeCode").val(bridgeCode);
				if(bridgeTypeCode)$("#"+id+"_bridgeType").attr("code",bridgeTypeCode);
				if(bridgeTypeName)$("#"+id+"_bridgeType").val(bridgeTypeName);
				/*if(faultTypeCode=='')//设置祸首件值
				{
					if(firstPartCode)$("#"+id+"_firstPartCode").attr("code",firstPartCode);
					if(firstPartName)
					{
						$("#"+id+"_firstPartName").val(firstPartName);
						$("#"+id+"_firstPartCode").val(firstPartName);
					}
					if(firstPartId)$("#"+id+"_firstPartId").val(firstPartId);
				}else if(faultTypeCode=='')
				{
					$("#"+id+"_hsjbt").hide();
					$("#"+id+"_hsjnr").hide();
				}*/
				if(measuresCode=='<%=DicConstant.CLFS_01%>')//维修时不展示新件信息
				{
					$("#"+id+"_xjjgkz").hide();
					$("#"+id+"_xjkz").hide();
					$("#"+id+"_spclf").hide();
				}else if(measuresCode=='<%=DicConstant.CLFS_02%>')//更换时展示
				{
					$("#"+id+"_xjjgkz").show();
					$("#"+id+"_xjkz").show();
					$("#"+id+"_spclf").show();
				}else if(measuresCode=='<%=DicConstant.CLFS_03%>') //加装时， 不展示旧件信息
				{
					$("#"+id+"_jjxxdm").hide();
					$("#"+id+"_jjxxcj").hide();
				}
				if(oldPartCode)
				{
					$("#"+id).html("<span>"+oldPartCode+"</span>");
					$("#"+id).attr("title",oldPartName+"-"+faultTypeName);
				}else
				{
					$("#"+id).html("<span>"+newPartCode+"</span>");
					$("#"+id).attr("title",newPartName+"-"+faultTypeName);
				}
				
				
			}
		}else
		{
			addPartTabs(1);//如果没有零件信息时添加一条空的数据
		}
		showTabsLength();//更改本页面的Title
	}catch(e)
	{
		alertMsg.error(e.description);
		return false;
	}
	return true;
}
//更改本页面的Title
function showTabsLength()
{
	var showTitle=thisTitle+"--已选择配件:"+$("#dia-partTabs li").length+"个";
	$("body").data("claimPartAdd").find("h1").text(showTitle);
	
}
//实现动态添加零件信息的主方法
function addPartTabs(type)
{
	if($("#dia-partTabs li").length>=maxPartCout)
	{
		alertMsg.info("最大配件添加数量为:"+maxPartCout+",已超过,不能继续添加配件.");
		return true;
	}
	
	$("#dia-partTabs li").removeClass("selected");
	var id="DIALI"+liLength;
	$("#dia-partTabs").append("<li class='selected' ><a href='javascript:void(0)' id='"+id+"' title='配件信息"+liLength+"'><span>配件信息"+liLength+"</span></a></li>");
	$("#dia-partContent div").hide();
	var newDiv=$("<div id='"+id+"_content'></div>");
	var newForm=$("<form id='"+id+"_form' class='editForm' style='width:%;'></form>");
	var newTable=$("<table id='"+id+"_table' refer='true' class='editTable' ref='"+id+"_content' style='width:100%;'></table>");
	var newTr1=$("<tr></tr>");
	var newTd11=$("<td><label>故障类别：</label></td>");
	var newTd12="";
	if(liLength==0)
	{
		 newTd12=$("<td><input type='text' id='"+id+"_faultType' kind='dic' src='GZLB' datasource='"+id+"_FAULTTYPE' datatype='0,is_null,30' value='主损件' code='301601' disabled='true'/></td>");	
	}else
	{
		 newTd12=$("<td><input type='text' id='"+id+"_faultType' kind='dic' src='GZLB' datasource='"+id+"_FAULTTYPE' datatype='0,is_null,30' value='附损件' code='301602' disabled='true' /></td>");
	}
	
	var newTd13=$("<td><label>处理措施：</label></td>");
	var newTd14="";
	if(partClaimType=='301403')
	{
		if(partClfs=="")
		{
			newTd14=$("<td><input type='text' id='"+id+"_measures' kind='dic' src='CLFS' datasource='"+id+"_MEASURES' datatype='0,is_null,30' value=''/><input type='hidden'  id='"+id+"_faultPartId' datasource='"+id+"_FAULTPARTID' value=''/></td>");
		}else if(partClfs=="300601")
		{
			newTd14=$("<td><input type='text' id='"+id+"_measures' value='维修' code='300601' datasource='"+id+"_MEASURES' datatype='0,is_null,30' value=''/><input type='hidden'  id='"+id+"_faultPartId' datasource='"+id+"_FAULTPARTID' value=''/></td>");
		}else if(partClfs=="300602")
		{
			newTd14=$("<td><input type='text' id='"+id+"_measures' value='零件更换' code='300602' datasource='"+id+"_MEASURES' datatype='0,is_null,30' value=''/><input type='hidden'  id='"+id+"_faultPartId' datasource='"+id+"_FAULTPARTID' value=''/></td>");
		}else if(partClfs=="300603")
		{
			newTd14=$("<td><input type='text' id='"+id+"_measures' value='加装' code='300603' datasource='"+id+"_MEASURES' datatype='0,is_null,30' value='' /><input type='hidden'  id='"+id+"_faultPartId' datasource='"+id+"_FAULTPARTID' value=''/></td>");
		}
	}else
	{
		newTd14=$("<td><input type='text' id='"+id+"_measures' kind='dic' src='CLFS' datasource='"+id+"_MEASURES' datatype='0,is_null,30' value=''/><input type='hidden'  id='"+id+"_faultPartId' datasource='"+id+"_FAULTPARTID' value=''/></td>");
	}
	
	var newTd15=$("<td><label>配件类别：</label></td>");
	var newTd16=$("<td><input type='text' id='"+id+"_partTypeName'  value='' readonly='readonly' class='readonly' /><input type='hidden' id='"+id+"_partType' datasource='"+id+"_PARTTYPE' value='' readonly='readonly' /><input type='hidden' id='"+id+"_repayUprice' datasource='"+id+"_REPAYUPRICE'/></td>");
	newTr1.append(newTd11);
	newTr1.append(newTd12);
	newTr1.append(newTd13);
	newTr1.append(newTd14);
	var newTr2=$("<tr id='"+id+"_jjxxdm'></tr>");
	var newTd21=$("<td><label>旧件代码：</label></td>");
	var newTd22=$("<td><input type='text' id='"+id+"_oldPartCode' datasource='"+id+"_OLDPARTCODE' value='' readonly='readonly' class='readonly' datatype='0,is_null,30'  hasBtn='true' callFunction='selectOldPart(\""+id+"\")'/><input type='hidden'  id='"+id+"_oldPartId' datasource='"+id+"_OLDPARTID' /><input type='hidden'  id='"+id+"_bridgeStatus' datasource='"+id+"_BRIDGESTATUS' /></td>");
	var newTd23=$("<td><label>旧件名称：</label></td>");
	var newTd24=$("<td><input type='text' id='"+id+"_oldPartName' datasource='"+id+"_OLDPARTNAME' value='' readonly='readonly' class='readonly' /><input type='hidden'  id='"+id+"_ifReturn' datasource='"+id+"_IFRETURN' /></td>");
	//var newTd25=$("<td id='"+id+"_hsjbt'><label>祸首件：</label></td>");
	//var newTd26=$("<td id='"+id+"_hsjnr'><input type='text' id='"+id+"_firstPartCode'datasource='"+id+"_FIRSTPARTCODE' kind='dic' src='T#SE_BU_CLAIM_FAULT_PART:OLD_PART_CODE:OLD_PART_NAME{OLD_PART_ID}:CLAIM_DTL_ID="+claimDtlId+" AND FAULT_TYPE="+<%=DicConstant.GZLB_01%>+"' datatype='1,is_null,30' value=''/><input type='hidden' id='"+id+"_firstPartId'  datasource='"+id+"_FIRSTPARTID' value=''/><input type='hidden' id='"+id+"_firstPartName' datasource='"+id+"_FIRSTPARTNAME' value=''/></td>");
	newTr2.append(newTd21);
	newTr2.append(newTd22);
	newTr2.append(newTd23);
	newTr2.append(newTd24);
	newTr2.append(newTd15);
	newTr2.append(newTd16);
	//newTr2.append(newTd25);
	//newTr2.append(newTd26);
	var newTr3=$("<tr id='"+id+"_jjxxcj'></tr>");
	var newTd31=$("<td><label>旧件件数：</label></td>");
	var newTd32=$("<td><input type='text' id='"+id+"_oldPartCount' value=''  datasource='"+id+"_OLDPARTCOUNT' datatype='0,is_digit_0,30'  /><input type='hidden' id='"+id+"_vehicleMax' datasource='"+id+"_VEHICLEMAX' /><input type='hidden' id='"+id+"_ifWorkMultiple' datasource='"+id+"_IFWORKMULTIPLE' /></td>");
	var newTd33=$("<td><label>旧件供应厂家：</label></td>");
	var newTd34=$("<td><input type='text' id='"+id+"_oldSupplierCode' value='' class='readonly' datasource='"+id+"_OLDSUPPLIERCODE' readonly='readonly' /><input type='hidden' id='"+id+"_oldSupplierId' datasource='"+id+"_OLDSUPPLIERID' /></td>");
	var newTd35=$("<td><label>零件流水号(旧件)：</label></td>");
	var newTd36=$("<td><input type='text' id='"+id+"_oldPartStream' datasource='"+id+"_OLDPARTSTREAM'  value='' /><input type='hidden' id='"+id+"_oldIfStream'/></td>");
	newTr3.append(newTd31);
	newTr3.append(newTd32);
	newTr3.append(newTd33);
	newTr3.append(newTd34);
	newTr3.append(newTd35);
	newTr3.append(newTd36);
	var newTr4="";
	if(partClfs=="300601")
	{
		 newTr4=$("<tr id='"+id+"_xjkz' style='display:none'></tr>");
	}else
	{
		 newTr4=$("<tr id='"+id+"_xjkz' ></tr>");
	}
	
	var newTd41=$("<td><label>新件代码：</label></td>");
	var newTd42=$("<td><input type='text' id='"+id+"_newPartCode'   datasource='"+id+"_NEWPARTCODE' class='readonly' datatype='0,is_null,30' readonly='readonly' hasBtn='true' callFunction='selectNewPart(\""+id+"\")' /><input type='hidden'  id='"+id+"_newPartId' datasource='"+id+"_NEWPARTID' /></td>");
	var newTd43=$("<td><label>新件名称：</label></td>");
	var newTd44=$("<td><input type='text' id='"+id+"_newPartName'  datasource='"+id+"_NEWPARTNAME'  readonly='readonly' class='readonly' /></td>");
	var newTd45=$("<td><label>新件供应厂家：</label></td>");
	var newTd46=$("<td><input type='text' id='"+id+"_newSupplierCode'  datasource='"+id+"_NEWSUPPLIERCODE' readonly='readonly' class='readonly' /><input type='hidden' id='"+id+"_newSupplierId' datasource='"+id+"_NEWSUPPLIERID' /></td>");
	newTr4.append(newTd41);
	newTr4.append(newTd42);
	newTr4.append(newTd43);
	newTr4.append(newTd44);
	newTr4.append(newTd45);
	newTr4.append(newTd46);
	var newTr5="";
	if(partClfs=="300601")
	{
		 newTr5=$("<tr id='"+id+"_xjjgkz' style='display:none'></tr>");	
	}else
	{
		 newTr5=$("<tr id='"+id+"_xjjgkz'></tr>");	
	}
	
	var newTd51=$("<td><label>新件件数：</label></td>");
	var newTd52=$("<td><input type='text' id='"+id+"_newPartCount' datasource='"+id+"_NEWPARTCOUNT' value='' datatype='0,is_digit_0,10'  onchange='goChangeClaimCosts(\""+id+"\")'/></td>");
	var newTd53=$("<td><label>新件来源：</label></td>");
	var newTd54=$("<td><input type='text' id='"+id+"_newPartFrom'  datasource='"+id+"_NEWPARTFROM' kind='dic' src='XJLY' datasource='"+id+"_XJLY' datatype='0,is_null,30' value=''/></td>");
	var newTd55=$("<td><label>零件流水号(新件)：</label></td>");
	var newTd56=$("<td><input type='text' id='"+id+"_newPartStream' datasource='"+id+"_NEWPARTSTREAM' value='' /><input type='hidden' id='"+id+"_newIfStream'/></td>");
	newTr5.append(newTd51);
	newTr5.append(newTd52);
	newTr5.append(newTd53);
	newTr5.append(newTd54);
	newTr5.append(newTd55);
	newTr5.append(newTd56);
	var newTr6="";
	if(partClfs=="300601")
	{
		 newTr6=$("<tr id='"+id+"_spclf' style='display:none'></tr>");
	}else
	{
		 newTr6=$("<tr id='"+id+"_spclf'></tr>");
	}
	
	var newTd61=$("<td><label>索赔单价：</label></td>");
	var newTd62=$("<td><input type='text' id='"+id+"_claimUprice' datasource='"+id+"_CLAIMYPRICE' datatype='0,is_double,12' value='' onchange='goChangeClaimCosts(\""+id+"\")' readonly='readonly' class='readonly'/></td>");
	var newTd63=$("<td><label>索赔材料费：</label></td>");
	var newTd64=$("<td colspan='3'><input type='text' id='"+id+"_claimCosts' datasource='"+id+"_CLAIMCOSTS' datatype='0,is_double,12' value='' readonly='readonly' class='readonly'/></td>");
	newTr6.append(newTd61);
	newTr6.append(newTd62);
	newTr6.append(newTd63);
	newTr6.append(newTd64);
	var newTr7=$("<tr></tr>");
	var newTd71=$("<td><label>桥供应商号：</label></td>");
	var newTd72=$("<td><input type='text' id='"+id+"_bridgeSupplierNo' datasource='"+id+"_BRIDGESUPPLIERNO'  datatype='1,is_null,30' value=''/></td>");
	var newTd73=$("<td><label>故障原因：</label></td>");
	var newTd74=$("<td><textarea id='"+id+"_faultReason' style='width: 150px; height: 30px;'  datasource='"+id+"_FAULTREASON' datatype='1,is_null,1000'></textarea></td>");
	//var newTd75=$("<td><label>严重程度：</label></td>");
	//var newTd76=$("<td><input type='text' id='"+id+"_severity' kind='dic' src='T#SE_BA_CODE:CODE:NAME:CODE_TYPE=302703' datasource='"+id+"_SEVERITY' datatype='0,is_null,30' value=''/><input type='hidden' id='"+id+"_test'/></td>");
	newTr7.append(newTd71);
	newTr7.append(newTd72);
	newTr7.append(newTd73);
	newTr7.append(newTd74);
	var newTr8=$("<tr></tr>");
	var newTd81=$("<td><label>桥类别：</label></td>");
	var newTd82=$("<td><input type='text' id='"+id+"_bridgeType' datasource='"+id+"_BRIDGETYPE' kind='dic' src='QLX'  datatype='1,is_null,30' value=''/></td>");
	var newTd83=$("<td><label>桥编码：</label></td>");
	var newTd84=$("<td><input type='text' id='"+id+"_bridgeCode' datasource='"+id+"_BRIDGECODE'  datatype='1,is_null,30' value=''/></td>");
	//newTr7.append(newTd75);
	//newTr7.append(newTd76);
	newTr8.append(newTd81);
	newTr8.append(newTd82);
	newTr8.append(newTd83);
	newTr8.append(newTd84);
	
	newTable.append(newTr1);
	newTable.append(newTr2);
	newTable.append(newTr3);
	newTable.append(newTr4);
	newTable.append(newTr5);
	newTable.append(newTr6);
	newTable.append(newTr7);
	newTable.append(newTr8);
	newTable.addClass("editTable");
	newForm.append(newTable);
	newDiv.append(newForm);
	newDiv.addClass("page");
	$("#dia-partContent").append(newDiv);
	setStyle(id+"_table",2);
	$("#"+id+"_table").find("td span").each(function(){
		var $span = $(this);
		if($span.text() == "*")
		{
			if($span.parent().find("img").size() > 0)
				return true;
			$span.css("margin-left","-30px");
		}
	});
	$("#"+id+"_content").show();
	liLength++;
	if(type==2)
	{
		showTabsLength();
	}
	return id;
}
//保存
function saveTabs()
{
	 if($("#dia-partTabs li").length==0)
	 {
		 alertMsg.error("请先添加配件,再点击[保存]按钮.");
		 return false;
	 }
	var Id=$("#dia-partTabs li.selected >a").attr("id");
	if(Id)
	{
		var $f = $("#"+Id+"_form");
		if (submitForm($f) == false) return false;
		//如果提报的配件是附损件时,必须选择一个祸首件
		/*if($("#"+Id+"_faultType") && $("#"+Id+"_faultType").attr("code")=='')
		{
			if($("#"+Id+"_firstPartId").val()=="")
			{
				alertMsg.error("请选择一个祸首件");
				return false;
			}
		}else
		{
			if($("#"+Id+"_firstPartCode").val()!="")
			{
				alertMsg.error("主损件不用选择祸首件");
				return false;
			}
		}*/
		//一次故障只能有一个主损件,开始
		var cou=0;
		$("#dia-partTabs li").each(function()
		{
				var liId=$(this).find("a").attr("id");
				var faultType=$("#"+liId+"_faultType").attr("code");
				if(faultType)
				{
					if(faultType=='301601')
					{
						cou++;
					}
				}
		});
		if(cou>1)
		{
			alertMsg.error("一次故障只能有一个主损件.");				
			return false;
		}
		if(cou==0)
		{
			alertMsg.error("先保存主损件，再保存附损件.");				
			return false;
		}
	//结束
		var oldIfStream=$("#"+Id+"_oldIfStream").val();
		if(oldIfStream)
		{
			var oldPartStream=$("#"+Id+"_oldPartStream").val();
			if(oldPartStream=="")
			{
				alertMsg.error("请填写零件流水号(旧件)");				
				return false;
			}
		}
		var newIfStream=$("#"+Id+"_newIfStream").val();
		if(newIfStream)
		{
			var newPartStream=$("#"+Id+"_newPartStream").val();
			if(newPartStream=="")
			{
				alertMsg.error("请填写零件流水号(新件)");				
				return false;
			}
		}
	    var vehicleMax=$("#"+Id+"_vehicleMax").val();
		var oldPartCount=$("#"+Id+"_oldPartCount").val();
		if(vehicleMax > 0){
			if(oldPartCount > vehicleMax){
				alertMsg.error("旧件数量超过单车最大装车数量.");
				return false;
			}
		}
		var measures=$("#"+Id+"_measures").attr("code");
		//维修 清空新件
		if(measures == 300601){
			$("#"+Id+"_newPartId").val("");
			$("#"+Id+"_newPartCode").val("");
			$("#"+Id+"_newPartName").val("");
			$("#"+Id+"_newSupplierCode").val("");
			$("#"+Id+"_newPartCount").val("");
			$("#"+Id+"_newPartFrom").val("");
			$("#"+Id+"_newPartStream").val("");
			$("#"+Id+"_claimUprice").val("");
			$("#"+Id+"_claimCosts").val("");
			
		}else if(measures == 300603){//加装 清空旧件
			$("#"+Id+"_partTypeName").val("");
			$("#"+Id+"_oldPartId").val("");
			$("#"+Id+"_oldPartCode").val("");
			$("#"+Id+"_oldPartName").val("");
			$("#"+Id+"_oldPartCount").val("");
			$("#"+Id+"_oldSupplierCode").val("");
			$("#"+Id+"_oldPartStream").val("");
		}
		if(measures == 300601||measures == 300602){//处理措施为“维修”,“零件更换”的需要校验以下规则.
			var bridgeStatus= $("#"+Id+"_bridgeStatus").val(); 
			if(bridgeStatus==100101){//如果选择的旧件是否校验桥编码属性为“是”，则需要校验桥编码必填。
				var bridgeCode = $("#"+Id+"_bridgeCode").val();
				if(bridgeCode.length==0){
					alertMsg.error("保存前需先填写桥编码.");
					return false;
				}
			}
		}
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $f.combined(1) || {};
		var faultPartId=$("#"+Id+"_faultPartId").val();
		diaHtmlId=Id;
		if(faultPartId)//执行更新操作
		{
			var diaUpdateUrl=diafaultPartAction+"/updateFaultPart.ajax?faultPartId="+faultPartId+"&htmlId="+Id+"&claimDtlId="+claimDtlId;
			doNormalSubmit($f,diaUpdateUrl,"dia-tabs-save",sCondition,diaUpdatePartCallBack);
								
		}else//执行插入操作
		{
			var diaInsertUrl=diafaultPartAction+"/insertFaultPart.ajax?claimId="+diaCliamId+"&htmlId="+Id+"&claimDtlId="+claimDtlId;
			doNormalSubmit($f,diaInsertUrl,"dia-tabs-save",sCondition,diaInsertCallBack);
		}
	}
}
//更新后回调
function diaUpdatePartCallBack(res)
{
	try
	{
		var rows = res.getElementsByTagName("ROW");
		if(rows && rows.length > 0)
		{
			var faultPartId =getNodeText(rows[0].getElementsByTagName("FAULT_PART_ID").item(0));
			var oldPartCode =getNodeText(rows[0].getElementsByTagName("OLD_PART_CODE").item(0));
			var oldPartName =getNodeText(rows[0].getElementsByTagName("OLD_PART_NAME").item(0));
			var newPartName =getNodeText(rows[0].getElementsByTagName("NEW_PART_NAME").item(0));
			var newPartCode =getNodeText(rows[0].getElementsByTagName("NEW_PART_CODE").item(0));
			$("#"+diaHtmlId+"_faultPartId").val(faultPartId);
			if(oldPartCode)
			{
				$("#"+diaHtmlId).html("<span>"+oldPartCode+"</span>");
				$("#"+diaHtmlId).attr("title",oldPartName+"-"+$("#"+diaHtmlId+"_faultType").val());
			}else
			{
				$("#"+diaHtmlId).html("<span>"+newPartCode+"</span>");
				$("#"+diaHtmlId).attr("title",newPartName+"-"+$("#"+diaHtmlId+"_faultType").val());
			}
			
			var faultType= $("#"+diaHtmlId+"_faultType").attr("code");
			if(faultType== 301601){
				searchClaimPattern();//查询故障信息
			}
			searchClaimMaxCosts();
		}else
		{
			return false;
		}
		
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//插入后回调
function diaInsertCallBack(res)
{
	try
	{
		var rows = res.getElementsByTagName("ROW");
		if(rows && rows.length > 0)
		{
			var faultPartId =getNodeText(rows[0].getElementsByTagName("FAULT_PART_ID").item(0));
			var oldPartCode =getNodeText(rows[0].getElementsByTagName("OLD_PART_CODE").item(0));
			var oldPartName =getNodeText(rows[0].getElementsByTagName("OLD_PART_NAME").item(0));
			var newPartName =getNodeText(rows[0].getElementsByTagName("NEW_PART_NAME").item(0));
			var newPartCode =getNodeText(rows[0].getElementsByTagName("NEW_PART_CODE").item(0));
			$("#"+diaHtmlId+"_faultPartId").val(faultPartId);
			if(oldPartCode)
			{
				$("#"+diaHtmlId).html("<span>"+oldPartCode+"</span>");
				$("#"+diaHtmlId).attr("title",oldPartName+"-"+$("#"+diaHtmlId+"_faultType").val());
			}else
			{
				$("#"+diaHtmlId).html("<span>"+newPartCode+"</span>");
				$("#"+diaHtmlId).attr("title",newPartName+"-"+$("#"+diaHtmlId+"_faultType").val());
			}
			
			var faultType= $("#"+diaHtmlId+"_faultType").attr("code");
			if(faultType== 301601){
				searchClaimPattern();//查询故障信息
			}
			searchClaimMaxCosts();
		}else
		{
			return false;
		}
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//删除方法
function delTabs()
{
	var aId=$("#dia-partTabs li.selected >a").attr("id");
	if(aId)
	{
		var faultPartId=$("#"+aId+"_faultPartId").val();
		var faultType=$("#"+aId+"_faultType").attr("code");
		if(faultPartId)//如果存在faultPartId,删除后台后在页面中删除
		{
			diaHtmlId=aId;
			//modify by zhaojinyu 增加校验如果删除的是主损件,那么删除的主损件里不能是其他的附损配件的祸首件
			//var oldPartId=$("#"+aId+"_oldPartId").val();
			if(faultType=='<%=DicConstant.GZLB_01%>')
			{
				alertMsg.error("该主损件已是其他附损件的祸首件,不允许删除.");	
				return false;
				/* var checkUse=0;
				$("#dia-partTabs li").each(function()
					{
						var liId=$(this).find("a").attr("id");
						var firstPartId=$("#"+liId+"_firstPartId").val();
						if(firstPartId)
						{
							if(firstPartId==oldPartId)
							{
								checkUse=1;
								return false;
							}
						}
					});
				if(checkUse==1)
				{
					alertMsg.error("该主损件已是其他附损件的祸首件,不允许删除.");	
					return false;
				} */
			}
			//end
			var deletePartTabUrl= diafaultPartAction+"/deletePartTab.ajax?faultPartId="+faultPartId;
			sendPost(deletePartTabUrl,"","",deletePartTabUrlCallBack,"true");
		}else
		{
			if(faultType=='<%=DicConstant.GZLB_01%>')
			{
				alertMsg.error("请先保存主损件,不允许删除.");	
				return false;
			}
			//删除页面配件信息
			var liObj=$("#"+aId).parent();
			var contentId=aId+"_content";
			$("#" + contentId).remove();
			liObj.remove();
			if($("#dia-partTabs li").length>0)
			{
				var firsttab = $("#dia-partTabs li:first-child");
		        firsttab.addClass("selected");
		        var firsttabid = $(firsttab).find(">a").attr("id");
		        $("#" + firsttabid + "_content").show();
			}
			showTabsLength();
		}
		
	}
}
//删除回调方法
function deletePartTabUrlCallBack(res)
{
	try
	{
		//删除页面配件信息
		var liObj=$("#"+diaHtmlId).parent();
		var contentId=diaHtmlId+"_content";
		$("#" + contentId).remove();
		liObj.remove();
		if($("#dia-partTabs li").length>0)
		{
			var firsttab = $("#dia-partTabs li:first-child");
	        firsttab.addClass("selected");
	        var firsttabid = $(firsttab).find(">a").attr("id");
	        $("#" + firsttabid + "_content").show();
		}
		showTabsLength();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//字典选择回调
function afterDicItemClick(id, $row, selIndex) 
{
	
	/*if(id.indexOf("firstPartCode")>0)
	{
		var sjId=id.substring(0,id.indexOf("_"));
		if($("#"+sjId+"_firstPartId"))
		{
			$("#"+sjId+"_firstPartId").val($row.attr("OLD_PART_ID"));
		}
		if($("#"+sjId+"_firstPartName"))
		{
			$("#"+sjId+"_firstPartName").val($("#"+id).val());
		}
	
	}*/
	/*if(id.indexOf("faultType")>0)
	{
		var faultTypeVal=$("#"+id).attr("code");
		var sjId=id.substring(0,id.indexOf("_"));
		if(faultTypeVal=='')//主损件不用选择祸首件
		{
			$("#"+sjId+"_hsjbt").hide();
			$("#"+sjId+"_hsjnr").hide();
		}else if(faultTypeVal=='')//附损件需要选择祸首件
		{
			$("#"+sjId+"_hsjbt").show();
			$("#"+sjId+"_hsjnr").show();
		}
		
	}*/
	if(id.indexOf("measures")>0)
	{
		var measuresVal=$("#"+id).attr("code");
		var sjId=id.substring(0,id.indexOf("_"));
		if(measuresVal=='<%=DicConstant.CLFS_01%>')//维修时不用填写新件信息
		{
			$("#"+sjId+"_xjjgkz").hide();
			$("#"+sjId+"_xjkz").hide();
			$("#"+sjId+"_spclf").hide();
			$("#"+sjId+"_jjxxdm").show();
			$("#"+sjId+"_jjxxcj").show();
		}else if(measuresVal=='<%=DicConstant.CLFS_02%>')//更换时需要填写
		{
			$("#"+sjId+"_xjjgkz").show();
			$("#"+sjId+"_xjkz").show();
			$("#"+sjId+"_spclf").show();
			$("#"+sjId+"_jjxxdm").show();
			$("#"+sjId+"_jjxxcj").show();
			//goChangeClaimCosts(sjId);
		}else if(measuresVal=='<%=DicConstant.CLFS_03%>') //加装时， 不展示旧件信息 
		{
			$("#"+sjId+"_jjxxdm").hide();
			$("#"+sjId+"_jjxxcj").hide();
			$("#"+sjId+"_xjjgkz").show();
			$("#"+sjId+"_xjkz").show();
			$("#"+sjId+"_spclf").show();
			//goChangeClaimCosts(sjId);
		}
		
	}
	return true;
}
//查询旧件方法
function selectOldPart(id)
{
	var faultType=$("#"+id+"_faultType").attr("code");
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/dealer/service/claimmng/claimOldPartSearch.jsp?id="+id+"&selectType=1&claimType="+partClaimType+"&faultType="+faultType, "claimOldPartSearch", "选择旧件", options);
}
//查询新件方法
function selectNewPart(id)
{
	var faultType=$("#"+id+"_faultType").attr("code");
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/dealer/service/claimmng/claimOldPartSearch.jsp?id="+id+"&selectType=2&claimType="+partClaimType+"&faultType="+faultType, "claimOldPartSearch", "选择新件", options);
}
//计算材料费方法
function countClaimCosts(sl,id)
{
	var claimUprice=$("#"+id+"_claimUprice").val();
	if(!claimUprice) claimUprice=0;
	if(!sl) sl=0;
	var claimCosts=parseFloat(sl)*parseFloat(claimUprice);
	$("#"+id+"_claimCosts").val(claimCosts.toFixed(2));
}
//当新件数量或者索赔单价发生更改时触发该方法,计算材料费
function goChangeClaimCosts(id)
{
	//	var diaClfs=$("#"+id+"_measures").attr("code");
		
	// 	if(diaClfs)
	// 	{
	// 		<%-- if(diaClfs=='<%=DicConstant.CLFS_02%>' || diaClfs=='<%=DicConstant.CLFS_03%>' ) --%>
	// 		{
	// 			if($("#"+id+"_newPartCount").val())
	// 			{
	// 				sl=$("#"+id+"_newPartCount").val();
	// 			}
	// 			countClaimCosts(sl,id);
	// 		}
			
	// 	}
	var sl=0;
	if($("#"+id+"_newPartCount").val())
	{
		sl=$("#"+id+"_newPartCount").val();
	}
	countClaimCosts(sl,id);
}
</script>
</div>

