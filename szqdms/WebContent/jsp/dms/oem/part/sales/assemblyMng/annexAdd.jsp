<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.framework.util.Pub"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.org.dms.common.DicConstant"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String action = request.getParameter("action");
	String DTL_ID = request.getParameter("DTL_ID");
    if(action == null)
        action = "1";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="dia-layout" style="width:100%;">
        <div class="page">
        <div class="pageContent" style="" >
            <form  method="post" id="fm-assemblyEdit1">
                <input type="hidden" id="assemblyId" name="assemblyId" datasource="ASSEMBLY_ID"/>
            </form>
            <form method="post" id="fm-forecastReportEdit-W" class="editForm" >
                <input type="hidden" id="edit-forcastId" name="edit-forcastId" datasource="FORCAST_ID"/>
            </form>
        </div>
        <div id="edit_page_grid1">
            <table style="display:none;width:100%;" id="dia-tab-partinfo" name="tablist" ref="edit_page_grid1" refQuery="tab-condition" edit="true">
                <thead>
                    <tr>
                         <th type="single" name="XH" style="display:" append="plus|addParts"></th>
                         <th fieldname="PART_CODE" colwidth="100" ftype="input" fdatatype="0,is_null,30" fonblur="checkChn(this)">配件代码</th>
                         <th fieldname="PART_NAME" colwidth="100" ftype="input" fdatatype="0,is_null,30">配件名称</th>
                         <th fieldname="REMARKS" colwidth="100" ftype="input" fdatatype="1,is_null,1000" fonblur="blurSave(this)">备注</th>
                         <th colwidth="105" type="link" title="[编辑]|[删除]"  action="doPartSave|doPartDelete">操作</th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
        <div id="formBar" class="formBar">
            <ul>
                <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
            </ul>
        </div>
        <form id="fm-partInfo" method="post">
            <input type="hidden" id="aIds" name="aIds" datasource="A_PART_ID"/>
            <input type="hidden" id="dtlIds" name="dtlIds" datasource="DTL_ID"/>
            <input type="hidden" id="partCodes" name="partCodes" datasource="PART_CODE"/>
            <input type="hidden" id="partNames" name="partNames" datasource="PART_NAME"/>
            <input type="hidden" id="remarks" name="remarks" datasource="REMARKS"/>
        </form>
    </div>
</div>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
    var action=<%=action%>;
    var dtlId = <%=DTL_ID%>;
    var diaUrl = "<%=request.getContextPath()%>/part/salesMng/assemblyMng/AssemblyMngAction";
    var Adialog = $("body").data("addWin");
    // 初始化方法
    $(function(){
        searchPromPart();

        // 关闭按钮绑定
        $("button.close").click(function(){
            $.pdialog.close(Adialog);
            return false;
        });
    });
    // 查询预测配件明细
    function searchPromPart() {
        var searchPromPartUrl = "<%=request.getContextPath()%>/part/salesMng/assemblyMng/AssemblyMngAction/searchForecastDetail1.ajax?ID="+dtlId;
        var $f = $("#fm-assemblyEdit1");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, searchPromPartUrl, "btn-search", 1, sCondition, "dia-tab-partinfo");
        $("#dia-tab-partinfo").show();
        $("#dia-tab-partinfo").jTable();
    }

    function addParts()
    {
    	var $tab = $("#dia-tab-partinfo");
   		$tab.createRow();
    }
    function doPartSave(row){
		var ret = true;
		try
		{
			$("td input[type=radio]",$(row)).attr("checked",true);
			var $f = $("#fm-partInfo");
			if (submitForm($(row)) == false) return false;
			var DETAIL_ID = $(row).attr("DTL_ID");
			var dtlId = <%=DTL_ID%>;
			//设置隐藏域
			$("#dtlIds").val(DETAIL_ID);
			$("#aIds").val(dtlId);
			$("#partCodes").val($(row).find("td").eq(2).find("input:first").val());
			$("#partNames").val($(row).find("td").eq(3).find("input:first").val());
			$("#remarks").val($(row).find("td").eq(4).find("input:first").val());
			if (submitForm($f) == false) return false;
			var sCondition = {};
			sCondition = $f.combined(1) || {};
	 		//需要将隐藏域或者列表只读域传给后台
			if(DETAIL_ID)
			{
				var url = diaUrl + "/partUpdate.ajax";
				sendPost(url,"",sCondition,diaPartSaveCallBack,"true");
			}else
			{
				var url = diaUrl + "/partInsert.ajax";
				sendPost(url,"",sCondition,diaPartSaveCallBack,"false");
			} 
		}catch(e){alertMsg.error(e);ret = false;}
		return ret;
	}
    //行编辑保存回调方法
    function diaPartSaveCallBack(res){
    	 var rows = res.getElementsByTagName("ROW");
         var t = getNodeText(rows[0].getElementsByTagName("FLAG").item(0));
         if(t == "true"){
        	 alertMsg.info("配件代码重复!");
        	 sign = false;
             $("#FLAG").val(1);
             return false;
         }else{
        	 var selectedIndex = $("#dia-tab-partinfo").getSelectedIndex();
         	$("#dia-tab-partinfo").updateResult(res,selectedIndex);
         	$("#dia-tab-partinfo").clearRowEdit($("#dia-tab-partinfo").getSelectedRows()[0],selectedIndex);
         	return true;
         }
    }
    var $row;
    function doPartDelete(rowobj)
    {
    	$row = $(rowobj);
    	var id = $(rowobj).attr("DTL_ID");
    	if(id){
    		var url = diaUrl + "/contractPartDelete.ajax?DTL_ID="+$(rowobj).attr("DTL_ID");
        	sendPost(url, "", "", deleteCallBack, "true");
    	}else{
    		alertMsg.warn("请先维护配件信息！");
			return false;
    	}
    	
    }
    //删除配件回调方法
    function  deleteCallBack(res)
    {
    	try
    	{
    		if($row) 
    			$("#dia-tab-partinfo").removeResult($row);
    	}catch(e)
    	{
    		alertMsg.error(e);
    		return false;
    	}
    	return true;
    }
</script>