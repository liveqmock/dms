<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>车辆装箱信息</title>
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contentPath = request.getContextPath();
	String VEHICLE_ID = request.getParameter("VEHICLE_ID");
	String SHIP_ID = request.getParameter("SHIP_ID");
%>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script type="text/javascript">
var mngUrl = "<%=request.getContextPath()%>/part/storageMng/shipMng/ShipOutAction";
var VEHICLE_ID = "<%=VEHICLE_ID%>";
var SHIP_ID = "<%=SHIP_ID%>";
var diaAddOptions = {max:false,width:480,height:430,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
$(function(){
	$("button.close").click(function(){
		var dialog = parent.$("body").data("boxDetail");
		parent.$.pdialog.close(dialog);
		return false;
	});
	var $row;
	$("#btn-print").bind("click", function(event){
		var v_id = VEHICLE_ID;
		var s_id = SHIP_ID;
		var queryUrl = "<%=request.getContextPath()%>/part/storageMng/shipMng/ShipOutAction/printPdf.do?VEHICLE_ID="+v_id+"&SHIP_ID="+s_id;
        window.open(queryUrl);
        /* window.open(webApps + "/jsp/dms/oem/part/storage/shipMng/transReceipt.jsp?&VEHICLE_ID="+v_id+"&SHIP_ID="+s_id); */
	});
	$("#btn-addPack").bind("click", function(event){
		var v_id = VEHICLE_ID;
		var s_id = SHIP_ID;
		var diOption= {max:false,width:750,height:430,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps + "/jsp/dms/oem/part/storage/shipMng/packAdd.jsp?&VEHICLE_ID="+v_id+"&SHIP_ID="+s_id, "addPack", "添加包装信息", diOption);
	});
	var searchPartUrl = mngUrl+"/boxDetail.ajax?VEHICLE_ID="+VEHICLE_ID+"&SHIP_ID="+SHIP_ID;
	var $f = $("#fm-id");
	var sCondition = {};
	sCondition = $f.combined() || {};
	doFormSubmit($f,searchPartUrl,"",1,sCondition,"tab-partinfo");
	
	$('#btn-import').bind('click',function(){
	    //13:表示最大列，3：表示有效数据是第几行开始，第一行是1
	    //最后一个参数表示 导入成功后显示页
	    importXls("PT_BU_BOX_UP_TMP",$('#dia-ORDER_ID').val(),11,3,"/jsp/dms/oem/part/storage/boxUpMng/importSuccess.jsp");
	});
});

</script>
</head>
<body>
<div>
  <div class="tabsContent">
	<div class="page">
       <div class="pageContent">
			<fieldset>	
			<div id="part" style="overflow:hidden;">
					<table style="display:none;width:99%;" id="tab-partinfo"  name="tablist" ref="part" edit="false" >
							<thead>
								<tr>
									<th fieldname="SHIP_NO" >发运单号</th>
									<th fieldname="ORDER_NO" >订单编号</th>
									<th fieldname="PART_CODE" >配件代码</th>
									<th fieldname="BOX_NO" >箱号</th>
									<th fieldname="COUNT" >数量</th>
								</tr>
							</thead>
					</table>
				</div>
		</fieldset>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button  type="button" id="btn-addPack" name="btn-addPack">添&nbsp;加&nbsp;包&nbsp;装&nbsp;信&nbsp;息</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button  type="button" id="btn-print" name="btn-print">打&nbsp;印&nbsp;运&nbsp;输&nbsp;回&nbsp;执</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
		</div>						
      </div>				
	</div>		
</div>	
</body>
</html>