<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%@page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contentPath = request.getContextPath();
	String CONTRACT_ID = request.getParameter("CONTRACT_ID");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchPurchase">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-orderSearch">
				</table>
				<div class="subBar" style="display:none">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-searchOrder" >查&nbsp;&nbsp;询</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_purchase" >
			<table style="display:none;width:100%;" id="tab-purchase_info" name="tablist" ref="page_purchase" refQuery="fm-searchPurchase" >
					<thead>
						<tr>
							<th fieldname="PART_CODE" >供货品种代码</th>
							<th fieldname="PART_NAME" >供货品种名称</th>
							<th fieldname="UNIT_PRICE">单价(不含税)</th>
							<th fieldname="DELIVERY_CYCLE">供货周期</th>
							<th fieldname="MIN_PACK_UNIT">最小包装单位</th>
							<th fieldname="MIN_PACK_COUNT">最小包装数</th>
							<th fieldname="REMARKS">备注</th>
							<th refer="showPic"></th>
						</tr>
					</thead>
					<tbody>
                    </tbody>
			</table>
		</div>
	</div>
	</div>
<script type="text/javascript">
//查询提交方法
var mngUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseContract/ContractInfoSearchManageAciton";
var CONTRACT_ID = "<%=CONTRACT_ID%>"
$(function()
{
	$("#btn-searchOrder").bind("click", function(event){
		var searchUrl = mngUrl+"/contractDtlSearch.ajax?CONTRACT_ID="+CONTRACT_ID;
		var $f = $("#fm-searchPurchase");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-searchOrder",1,sCondition,"tab-purchase_info");
	});
	$("#btn-searchOrder").trigger("click");
});
function showPic(obj)
{
	var $row = $(obj).parent();
	var fid = $row.attr("FID").split(",");
	var fjmc = $row.attr("FJMC").split(",");
	var wjjbs = $row.attr("WJJBS").split(",");
	var blwjm = $row.attr("BLWJM").split(",");
	
	var s = "";
	if($row.attr("FID")){
		for(var i=0;i<wjjbs.length;i++)
		{
			var imgUrl = webApps + '/FileStoreAction/downloadFile.do?fjid='+fid[i]+'&fjmc='+fjmc[i]+'&wjjbs=' +wjjbs[i]+"&blwjm="+blwjm[i];
			s += "<a href='"+imgUrl+"'><img src='"+imgUrl+"' style='border:solid 1px #CE8031;width:80px;height:80px;margin:5px 5px 5px 5px;' data_src='"+imgUrl+"' name= 'photo' ></img></a>";
		}
		s += "";
		var k = "<div class='container' ><div class='gallery'>"+s+"</div></div>";
		
	}else{
		var imgUrl = '<%=request.getContextPath()%>/pldrmb/noPic.png';
		s += "<a href='"+imgUrl+"'><img src='"+imgUrl+"' style='border:solid 1px #CE8031;width:80px;height:80px;margin:5px 5px 5px 5px;' data_src='"+imgUrl+"' name= 'photo' ></img></a>";
		s += "";
		var k = "<div class='container' >"+s+"<div class='gallery'></div></div>";
	}
	return k;
}

function callbackSearch(res,tabId)
{
	if(tabId == "tab-purchase_info") 
	{
		$("#"+tabId+"").parent().height(document.documentElement.clientHeight-70);
		$("#tab-purchase_info").lightBox();
	}
}
</script>