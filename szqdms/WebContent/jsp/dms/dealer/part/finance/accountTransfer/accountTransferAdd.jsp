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
	String action = request.getParameter("action");
	String BALANCE_AMOUNT = request.getParameter("BALANCE_AMOUNT");
	if(action == null)
		action = "1";
%>
<div id="dia-layout">
	<div class="page">
	<div class="pageContent" style="" >
	<form method="post" id="fm-transferInfo" class="editForm" >
		<input type="hidden" id="dia-transferId" name="dia-transferId" datasource="TRANSFER_ID" />
		<input type="hidden" id="dia-TRANSFER_STATUS" name="dia-TRANSFER_STATUS" datasource="TRANSFER_STATUS" />
		<div align="left">
			<fieldset>
			<table class="editTable" id="tab-transfer_Edit_Info">
				<tr>
				    <td><label>转出账户类型：</label></td>
				    <td><input type="text" id="dia-OUT_TYPE" name="dia-OUT_TYPE" value="返利账户" datatype="0,is_null,30" readonly="true"/></td>
				</tr>
				<tr>
					<td><label>转入账户类型：</label></td>
				    <td><input type="text" id="dia-IN_TYPE" name="dia-IN_TYPE" value="材料费" datatype="0,is_null,30" readonly="true"/></td>
				</tr>
				<tr>
					<td><label>账户余额：</label></td>
				    <td><input type="text" id="dia-BALANCE_AMOUNT" name="dia-BALANCE_AMOUNT" datatype="0,is_money,30"  readonly="true"/></td>
				</tr>
				<tr>
					<td><label>转账金额：</label></td>
				    <td>
				    	<input type="text" id="dia-AMOUNT" name="dia-AMOUNT" datasource="AMOUNT" datatype="0,is_money,10" onblur="doMyInputBlur(this)"/>
				    </td>
				</tr>
				<tr>
				    <td><label>转账原因：</label></td>
				    <td colspan="5">
					  <textarea id="dia-REMARKS" style="width:450px;height:40px;" name="dia-REMARKS" datasource="REMARKS" datatype="1,is_null,1000"></textarea>
				    </td>
				</tr>
				<tr id = "AUDIT_REMARK" style="display:none">
				    <td><label>审核意见：</label></td>
				    <td colspan="5">
					  <textarea id="dia-AUDIT_REMARK" style="width:450px;height:40px;" name="dia-AUDIT_REMARK" datasource="AUDIT_REMARK" datatype="1,is_null,1000"></textarea>
				    </td>
				</tr>
			</table>
			</fieldset>
		</div>
	</form>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-save">保&nbsp;&nbsp;存</button></div></div></li>
			<li id="dia-contror" style="display: none"><div class="button"><div class="buttonContent"><button type="button" id="btn-report" >提&nbsp;&nbsp;报</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
		</ul>
	</div>
	</div>
	</div>
</div>

<script type="text/javascript">
/* 取父页面传过的参数确定新增或者修改 */
    var diaAction = "<%=action%>";
    var BALANCE_AMOUNT = "<%=BALANCE_AMOUNT%>";
    var diaUrl = "<%=request.getContextPath()%>/part/financeMng/transfer/TransferReportMngAction";
    $(function () {
        //保存基本信息按钮响应
        $("#btn-save").bind("click", function(event){
			var $f = $("#fm-transferInfo");
			if (submitForm($f) == false) return false;
			var sCondition = {};
			sCondition = $("#fm-transferInfo").combined(1) || {};
			if(diaAction == 1)
			{
				var addUrl = diaUrl + "/transferInsert.ajax";
				doNormalSubmit($f,addUrl,"btn-save",sCondition,diaInsertCallBack);
			}else	
			{
				var updateUrl = diaUrl + "/transferUpdate.ajax";
				doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
			}
		});
        $("#btn-report").bind("click", function(event){
    		var TRANSFER_ID = $("#dia-transferId").val();
    		var reportUrl = diaUrl + "/transferReport.ajax?TRANSFER_ID="+TRANSFER_ID;
    		sendPost(reportUrl,"report","",reportRemitCallBack,"true"); 
    	});
    	//关闭按钮响应
    	$("button.close").click(function(){
    		$.pdialog.closeCurrent();
    		return false;
    	});
		//修改页面赋值
        if (diaAction != "1") {
            var selectedRows = $("#tab-transfer_info").getSelectedRows();
            setEditValue("fm-transferInfo", selectedRows[0].attr("rowdata"));
            var status = $("#dia-TRANSFER_STATUS").val();
            $("#dia-contror").show();
            if(status==<%=DicConstant.ZZZT_04%>){
            	$("#AUDIT_REMARK").show();
            }
            $("#dia-BALANCE_AMOUNT").val(BALANCE_AMOUNT);
        }else{
        	$("#dia-BALANCE_AMOUNT").val(BALANCE_AMOUNT);
        }
        
    })
    
	//新增方法回调
    function diaInsertCallBack(res)
    {
    	var rows = res.getElementsByTagName("ROW");
    	var TRANSFER_ID = getNodeText(rows[0].getElementsByTagName("TRANSFER_ID").item(0));
		$('#dia-transferId').val(TRANSFER_ID);
    	try
    	{
    		$("#tab-transfer_info").insertResult(res,0);
    		$("#dia-contror").show();
    		diaAction = 2;
    		if($("#tab-transfer_info_content").size()>0){
    			$("td input[type=radio]",$("#tab-transfer_info_content").find("tr").eq(0)).attr("checked",true);			
    		}else{
    			$("td input[type=radio]",$("#tab-transfer_info").find("tr").eq(0)).attr("checked",true);
    		}
    	}catch(e)
    	{
    		alertMsg.error(e);
    		return false;
    	}
    	return true;
    }
	//修改方法回调
    function diaUpdateCallBack(res)
    {
    	try
    	{
    		var selectedIndex = $("#tab-transfer_info").getSelectedIndex();
    		$("#tab-transfer_info").updateResult(res,selectedIndex);
    	}catch(e)
    	{
    		alertMsg.error(e);
    		return false;
    	}
    	return true;
    }
	function doMyInputBlur(obj){
		 var $obj = $(obj);
		var t = $obj.val();
		var b = $("#dia-BALANCE_AMOUNT").val();
		if(t-b>0){
			alertMsg.warn("转账金额大于账户余额，请核对！");
			$obj.val("");
            return;
		}
	}
	
	var $row;
    function reportRemitCallBack(){
    	var selectedRows = $("#tab-transfer_info").getSelectedRows();
		$row =  selectedRows[0];
		$("#tab-transfer_info").removeResult($row);
		$.pdialog.closeCurrent();
    }

</script>