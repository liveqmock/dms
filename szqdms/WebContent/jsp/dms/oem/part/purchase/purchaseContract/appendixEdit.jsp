<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.frameImpl.Constant" %>
<%@ page import="com.org.framework.Globals" %>
<%@ page import="com.org.framework.common.User" %>
<%@ page import="com.org.dms.common.DicConstant" %>
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);	
	String contentPath = request.getContextPath();
	String DETAIL_ID = request.getParameter("DETAIL_ID");
%>
<div class="page">
	<div class="pageContent" style="height:380px;" >
	<form id="lsjcform" >
	</form>
		<form id="accept_fm">
<!-- 			<table class="list" id="fileDetail"   style="margin-top:10px;width:100%" > -->
			<div id="fileDetail" style="margin-top:10px;margin-left:10px;float:left;">
			</div>
		</form>
		</div>
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button class="close" type="button">关闭</button></div></div></li>
		</ul>
	</div>
	
</div>

<script type="text/javascript">
var DETAIL_ID = <%=DETAIL_ID%>;
var gUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseContract/ContractAppendixAduitManageAction";
$(function(){
    var dtl_id= DETAIL_ID;
	var furl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseContract/ContractAppendixAduitManageAction/contractAppendixSearch.ajax?DETAIL_ID="+dtl_id;
	sendPost(furl,"","",fileCallBack,"false");
});

function fileCallBack(res){
	try
	{
		var rows = res.getElementsByTagName("ROW");
		if(rows && rows.length>0){
			len = rows.length;
			var $div = $("#fileDetail");
			$div.append($("<ul></ul>"));
			for(var i = 0;i<rows.length;i++){
				var id= getNodeText(rows[i].getElementsByTagName("FID").item(0));
				var fid = getNodeText(rows[i].getElementsByTagName("FID").item(0));
				var fjmc = getNodeText(rows[i].getElementsByTagName("FJMC").item(0));
				var wjjbs = getNodeText(rows[i].getElementsByTagName("WJJBS").item(0));
				var blwjm = getNodeText(rows[i].getElementsByTagName("BLWJM").item(0));
				var imgUrl = webApps + '/FileStoreAction/downloadFile.do?fjid='+fid+'&fjmc='+fjmc+'&wjjbs=' +wjjbs+"&blwjm="+blwjm;
				$div.find("ul").eq(0).append($("<li style='display:block;float:left;margin-right:10px;margin-bottom:10px;'>"+"<div style='border:solid 1px #CE8031;'><img style='cursor:pointer;' id='li"+i+"' src='"+imgUrl+"' width='140px' height='140px' name='photo"+i+"' onclick='blowUp(photo"+i+")' /></div>"+"</li>"));
			}
		}

	}catch(e)
	{
		alertMsg.error(e.description);
		return false;
	}
	return true;
}
var flag;
function blowUp(name){
	if(flag==true){
		name.height="800";
		name.width="1024";
		flag=false;
	}else{
		name.height="140";
		name.width="140";
		flag=true;
	}
	
}

</script>