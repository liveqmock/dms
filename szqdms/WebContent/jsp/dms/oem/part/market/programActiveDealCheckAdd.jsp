<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="com.org.frameImpl.Constant" %>
<%@ page import="com.org.framework.Globals" %>
<%@ page import="com.org.framework.common.User" %>
<%@ page import="com.org.dms.common.DicConstant" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
    String contentPath = request.getContextPath();
    String action = request.getParameter("action");
    if (action == null)
        action = "1";
%>
<div id="dia-layout">
	<div class="page">
	<div class="pageContent" style="" >
    	<form method="post" id="fm-proActiveDeal" class="editForm" >
    		<input type="hidden" id="dia-checkId" name="dia-checkId" datasource="CHECK_ID" />
            <input type="hidden" id="dia-dealId" name="dia-dealId" datasource="DEAL_ID" />
			<input type="hidden" id="dia-activeId" name="dia-activeId" datasource="ACTIVE_ID" />
			<input type="hidden" id="dia-dealStatus" name="dia-dealStatus" datasource="DEAL_STATUS" />
	  		<div align="left">
		  		<fieldset>
					<table class="editTable" id="dia-tab-htxx">
						<tr>
							<td><label>活动代码：</label></td>
						    <td>
						    	<input type="text" id="dia-activeCode" name="dia-activeCode" datasource="ACTIVE_CODE" datatype="1,is_null,30" readonly=true/>
						    </td>
						    <td><label>活动名称：</label></td>
						    <td colspan=3>
						    	<input type="text" id="dia-activeName" name="dia-activeName" datasource="ACTIVE_NAME" datatype="1,is_null,30" readonly=true/>
						    </td>
						</tr>
						<tr>
							<td><label>开始时间：</label></td>
						    <td>
					    		<input type="text" id="dia-startDate"  name="dia-startDate" dataSource="START_DATE" datatype="1,is_date,30" readonly="true" />
					   		 </td>
						     <td><label>结束时间：</label></td>
						     <td>
					    		<input type="text" id="dia-endDate"  name="dia-endDate" dataSource="END_DATE" datatype="1,is_date,30" readonly="true" />
					   		 </td>
					   		 <td><label>参与人数：</label></td>
						     <td>
					    		<input type="text" id="dia-personNums"  name="dia-personNums" dataSource="PERSON_NUMS" datatype="1,is_null,3" readonly=true/>
					   		 </td>
						</tr>
						<tr>
							<td><label>渠道商代码：</label></td>
						    <td>
					    		<input type="text" id="dia-code"  name="dia-code" dataSource="CODE" datatype="1,is_null,30" readonly=true/>
					   		 </td>
						     <td><label>渠道商名称：</label></td>
						     <td colspan="3">
					    		<input type="text" id="dia-oname" style="width:89%;" name="dia-oname" dataSource="ONAME" datatype="1,is_null,100" readonly="true" />
					   		 </td>
						</tr>
						<tr>
							<td><label>预计费用：</label></td>
						    <td>
					    		<input type="text" id="dia-planFee"  name="dia-planFee" dataSource="PLAN_FEE" datatype="1,is_null,10" readonly=true/>
					   		</td>
							<td><label>执行提报人：</label></td>
						    <td>
					    		<input type="text" id="dia-reportUser"  name="dia-reportUser" dataSource="REPORT_USER" datatype="1,is_null,30" readonly=true/>
					   		 </td>
						     <td><label>提报时间：</label></td>
						     <td>
					    		<input type="text" id="dia-reportTime"  name="dia-reportTime" dataSource="REPORT_TIME" datatype="1,is_date,30" readonly="true" />
					   		 </td>
						</tr>
						<tr>
						    <td><label>活动方案内容：</label></td>
						    <td colspan="5">
							  <textarea id="dia-activeContent" style="width:93%;height:40px;" name="dia-activeContent" datasource="ACTIVE_CONTENT" datatype="1,is_null,1000" readonly=true></textarea>
						    </td>
						</tr>
						<tr>
						    <td><label>执行方案内容：</label></td>
						    <td colspan="5">
							  <textarea id="dia-dealContent" style="width:93%;height:40px;" name="dia-dealContent" datasource="DEAL_CONTENT" datatype="1,is_null,1000" readonly=true></textarea>
						    </td>
						</tr>
						<tr>
						    <td><label>审核意见：</label></td>
						    <td colspan="5">
							  <textarea id="dia-checkCon" style="width:93%;height:40px;" name="dia-checkCon" datasource="CHECK_CON" datatype="0,is_null,1000"></textarea>
						    </td>
						</tr>
						<tr>
						    <td><label>备　　注：</label></td>
						    <td colspan="5">
							  <textarea id="dia-remarks" style="width:93%;height:40px;" name="dia-remarks" datasource="REMARKS" datatype="1,is_null,1000"></textarea>
						    </td>
						</tr>
					</table>	
		  		</fieldset>
			</div>
		</form>
        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-doCheckPass">审核通过</button></div></div></li>
                <li><div class="button"><div class="buttonContent"><button type="button" id="btn-doCheckReturn">审核驳回</button></div></div></li>
                <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
            </ul>
        </div>
	</div>
<!--	<form method="post" id="dia-fm-searchProActiveDealer" class="editForm">-->
<!--        <table class="searchContent" id="dia-tab-searchProActiveDealerList"></table>-->
<!--    </form>-->
<!--	<div id="dia-page_proActiveDealerList" style="">-->
<!--        <table style="display:none;width:100%;" id="dia-tab-proActiveDealerList" name="tablist" ref="dia-page_proActiveDealerList" -->
<!--        	refQuery="dia-tab-searchProActiveDealerList" >-->
<!--            <thead>-->
<!--            	<tr>-->
<!--                 <th type="single" name="XH" style="display:none;"></th>-->
<!--                 <th fieldname="ACTIVE_CODE" >活动代码</th>-->
<!--                 <th fieldname="ACTIVE_NAME" >活动名称</th>-->
<!--                 <th fieldname="CODE" >渠道商代码</th>-->
<!--                 <th fieldname="ONAME" >渠道商名称</th>-->
<!--                 <th fieldname="CREATE_USER" >创建人</th>-->
<!--                 <th fieldname="CREATE_TIME" >创建时间</th>-->
<!--                 <th fieldname="STATUS" >有效标识</th>-->
<!--             </tr>-->
<!--            </thead>-->
<!--            <tbody>-->
<!--            </tbody>-->
<!--        </table>-->
<!--    </div>-->
	</div>
</div>

<script type="text/javascript">
    
//取父页面传过的参数确定新增或者修改
   var diaAction = "<%=action%>";
   var diaUrl = "<%=request.getContextPath()%>/part/marketMng/marketActiveMng/ProActiveDealCheckAction";
   var searchProActiveUrl = "<%=request.getContextPath()%>/part/marketMng/marketActiveMng/ProgramActiveAction";
   
$(function () {
	var selectedRows = $("#tab-proActiveDealList").getSelectedRows();
	
   	if (diaAction == 2) {  
        setEditValue("fm-proActiveDeal", selectedRows[0].attr("rowdata"));
        
        /**
        var activeId = $("#dia-activeId").val();
		var searchProActiveDealerUrl = searchProActiveUrl + "/proActiveDealerSearch.ajax?activeId="+activeId;
		var $f = $("#dia-fm-searchProActiveDealer");
		var sCondition = {};
	    sCondition = $f.combined() || {};
	    doFormSubmit($f, searchProActiveDealerUrl, "", 1, sCondition, "dia-tab-proActiveDealerList");
	    */
    }
       
    //审核通过按钮
    $("#btn-doCheckPass").bind("click", function(event){
		var $f = $("#fm-proActiveDeal");
		if (submitForm($f) == false) return false;
		var sCondition = {};
		sCondition = $f.combined(1) || {};
		var addUrl = diaUrl + "/check.ajax?checkResult=<%=DicConstant.SHJG_01%>";
		doNormalSubmit($f,addUrl,"btn-checkPass",sCondition,diaCheckPassCallBack);
	});
	
	//审核驳回按钮
    $("#btn-doCheckReturn").bind("click", function(event){
		var $f = $("#fm-proActiveDeal");
		if (submitForm($f) == false) return false;
		var sCondition = {};
		sCondition = $f.combined(1) || {};
		var addUrl = diaUrl + "/check.ajax?checkResult=<%=DicConstant.SHJG_02%>";
		doNormalSubmit($f,addUrl,"btn-checkPass",sCondition,diaCheckPassCallBack);
	});
});

//审核通过方法回调
function diaCheckPassCallBack(res)
{
   	try
   	{
   		var $row = $("#tab-proActiveDealList").getSelectedRows();
		if($row[0]){
			$("#tab-proActiveDealList").removeResult($row[0]);
			$.pdialog.closeCurrent();
		}
   	}catch(e)
   	{
   		alertMsg.error(e);
   		return false;
   	}
   	return true;
}

</script>