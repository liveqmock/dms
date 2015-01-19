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
    <div class="tabs"  eventType="click" id="dia-tabs">
        <div class="tabsHeader">
            <div class="tabsHeaderContent">
                <ul>
                    <li><a href="javascript:void(0);"><span>合同基本信息</span></a></li>
                    <li><a href="javascript:void(0);"><span>采购配件信息</span></a></li>
                </ul>
            </div>
        </div>
        <div class="tabsContent">
            <!--基本信息Tab begin-->
            <div style="height:auto;overflow:hidden;">
                <form method="post" id="dia-fm-contractEdit" class="editForm" style="width:99%;">
		<!-- 隐藏域 -->
				<input type="hidden" id="dia-CONTRACT_ID" name="dia-CONTRACT_ID" datasource="CONTRACT_ID" />
				<input type="hidden" id="FLAG" name="FLAG" />
		  <div align="left">
			  <fieldset>
						<table class="editTable" id="dia-tab-htxx">
							<tr>
								<td><label>合同编号：</label></td>
							    <td>
							    	<input type="text" id="dia-CONTRACT_NO" name="dia-CONTRACT_NO" datasource="CONTRACT_NO" datatype="0,is_null,30"/>
							    </td>
							    <td><label>合同名称：</label></td>
							    <td>
							    	<input type="text" id="dia-CONTRACT_NAME" name="dia-CONTRACT_NAME" datasource="CONTRACT_NAME" datatype="0,is_null,30"/>
							    </td>
							    <td><label>合同类别：</label></td>
							    <td>
								    <select type="text" class="combox" id="dia-CONTRACT_TYPE" name="dia-CONTRACT_TYPE" kind="dic" src="HTLX" datasource="CONTRACT_TYPE" datatype="0,is_null,6" readonly="readonly">
								    	<option value="-1" selected>--</option>
								    </select>
								</td>
							    
							</tr>
							<tr>
								<td><label>厂家代码：</label></td>
							    <td>
							    	<input type="text" id="dia-SUPPLIER_CODE" name="dia-SUPPLIER_CODE" datasource="SUPPLIER_CODE"  onblur="doCheck(this)" datatype="0,is_null,7" />
							    	<%-- <input type="text" id="dia-SUPPLIER_CODE" name="dia-SUPPLIER_CODE" datasource="SUPPLIER_CODE" kind="dic" src="T#PT_BA_SUPPLIER:SUPPLIER_CODE:SUPPLIER_NAME{SUPPLIER_ID}:1=1 AND PART_IDENTIFY=<%=DicConstant.YXBS_01 %>" datatype="0,is_null,30" />
							    	<input type="hidden" id="dia-SUPPLIER_ID" name="dia-SUPPLIER_ID" datasource="SUPPLIER_ID" /> --%>
							    </td>
								<td><label>厂家名称：</label></td>
							    <td>
							    	<input type="text" id="dia-SUPPLIER_NAME" name="dia-SUPPLIER_NAME" datasource="SUPPLIER_NAME" datatype="0,is_null,300"/>
							    </td>
								<td><label>发票类型：</label></td>
							    <td><input type="text" id="dia-INVOICE_TYPE" name="dia-INVOICE_TYPE" datasource="INVOICE_TYPE" kind="dic" src="FPLX" datatype="0,is_null,300"/></td>
							    
							</tr>
							<tr>
								<td><label>厂家资质：</label></td>
								<td>
							    	<input type="text" id="dia-SUPPLIER_QUALIFY" name="dia-SUPPLIER_QUALIFY" datasource="SUPPLIER_QUALIFY" datatype="0,is_null,30"/>
							    </td>
								<td><label>厂家法人：</label></td>
							    <td>
							    	<input type="text" id="dia-LEGAL_PERSON" name="dia-LEGAL_PERSON" datasource="LEGAL_PERSON" datatype="0,is_null,30"/>
							    </td>
								<td><label>法人联系方式：</label></td>
							    <td>
							    	<input type="text" id="dia-LEGAL_PERSON_PHONE" name="dia-LEGAL_PERSON_PHONE" datasource="LEGAL_PERSON_PHONE" datatype="0,is_digit_letter,30"/>
							    </td>
							</tr>
							<tr>
								<td><label>税率(%)：</label></td>
							    <td>
							    	<input type="text" id="dia-TAX_RATE" name="dia-TAX_RATE" datasource="TAX_RATE" datatype="0,is_null,30"/>
							    </td>
								<td><label>业务联系人：</label></td>
							    <td>
							    	<input type="text" id="dia-BUSINESS_PERSON" name="dia-BUSINESS_PERSON" datasource="BUSINESS_PERSON" datatype="0,is_null,30"/>
							    </td>
								<td><label>联系方式：</label></td>
							    <td>
							    	<input type="text" id="dia-BUSINESS_PERSON_PHONE" name="dia-BUSINESS_PERSON_PHONE" datasource="BUSINESS_PERSON_PHONE" datatype="0,is_digit_letter,30"/>
							    </td>
							</tr>
							<tr>
								<td><label>结算周期(月)：</label></td>
							    <td>
							    	<input type="text" id="dia-OPEN_ACCOUNT" name="dia-OPEN_ACCOUNT" datasource="OPEN_ACCOUNT" datatype="0,is_digit,30"/>
							    </td>	
							    <td><label>有效期开始时间：</label></td>
			                    <td>
			                    	<input type="text" id="dia-EFFECTIVE_CYCLE_BEGIN" name="dia-EFFECTIVE_CYCLE_BEGIN" datasource="EFFECTIVE_CYCLE_BEGIN" datatype="0,is_date,30" onclick="WdatePicker()" />
			                    </td>
			                    <td><label>有效期结束时间：</label></td>
			                    <td>
			                    	<input type="text" id="dia-EFFECTIVE_CYCLE_END" name="dia-EFFECTIVE_CYCLE_END" datasource="EFFECTIVE_CYCLE_END" datatype="0,is_date,30" onclick="WdatePicker({minDate:'%y-%M-%d'})" />
			                    </td>
								<!-- <td><label>有效期：</label></td>
							    <td>
							    	<input type="text"  id="dia-EFFECTIVE_CYCLE"  name="dia-EFFECTIVE_CYCLE"   dataSource="EFFECTIVE_CYCLE" style="width:75px;"  kind="date" datatype="0,is_date,30" onclick="WdatePicker()" onblur="doCheck(this)" />
						    		<input type="text" group="in-kscjrq,in-jscjrq"  id="in-kscjrq"  name="in-kscjrq"   dataSource="EFFECTIVE_CYCLE_BEGIN" style="width:75px;"  kind="date" datatype="0,is_date,30" onclick="WdatePicker()" readonly="true" />
						    			<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
									<input type="text" group="in-kscjrq,in-jscjrq"  id="in-jscjrq"  name="in-jscjrq" dataSource="EFFECTIVE_CYCLE_END" style="width:75px;margin-left:-30px;" kind="date" datatype="0,is_date,30" onclick="WdatePicker()" readonly="true" />
							    </td> -->
							</tr>
							<tr>
								<td><label>质保金(元)：</label></td>
							    <td>
							    	<input type="text" id="dia-GUARANTEE_MONEY" name="dia-GUARANTEE_MONEY" datasource="GUARANTEE_MONEY" datatype="0,is_money,30"/>
							    </td>
								<td><label>质保期(月)：</label></td>
							    <td>
							    	<input type="text" id="dia-WARRANTY_PERIOD" name="dia-WARRANTY_PERIOD" datasource="WARRANTY_PERIOD" datatype="0,is_digit,30"/>
							    </td>
							</tr>
							<tr>
							    <td><label>追偿条款：</label></td>
							    <td colspan="5">
								  <textarea id="dia-RECOVERY_CLAUSE" style="width:93%;height:40px;" name="dia-RECOVERY_CLAUSE" datasource="RECOVERY_CLAUSE" datatype="1,is_null,100"></textarea>
							    </td>
							</tr>
							<tr>
							    <td><label>备注：</label></td>
							    <td colspan="5">
								  <textarea id="dia-REMARKS" style="width:93%;height:40px;" name="dia-REMARKS" datasource="REMARKS" datatype="1,is_null,1000"></textarea>
							    </td>
							</tr>
						</table>
			  </fieldset>
			</div>
			</form>
                <div class="formBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-save">保&nbsp;&nbsp;存</button></div></div></li>
                        <li>
                            <div class="buttonActive">
                                <div class="buttonContent">
                                    <button type="button" name="btn-next">下&nbsp;一&nbsp;步</button>
                                </div>
                            </div>
                        </li>
                        <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
                    </ul>
                </div>
            </div>
            <!--基本信息Tab end-->
            <!--促销备件Tab begin-->
            <div class="page">
                <div class="pageContent" >
                <form method="post" id="fm-searchPart" class="editForm">
                        <table class="searchContent" id="tab-searchPart"></table>
                    </form>
                    <div id="dia-part" style="">
                        <table style="display:none;width:100%;" id="dia-tab-partinfo" name="tablist" ref="dia-part" refQuery="tab-searchPart" edit="true">
                            <thead>
                           <tr>
                                <th type="single" name="XH" style="display:" append="plus|addParts"></th>
                                <th fieldname="PART_CODE" colwidth="100" ftype="input" fdatatype="0,is_null,30" fonblur="checkChn(this)">配件代码</th>
                                <th fieldname="PART_NAME" colwidth="100" ftype="input" fdatatype="0,is_null,30">配件名称</th>
                                <th fieldname="UNIT_PRICE" colwidth="50" ftype="input" fdatatype="0,is_money,30">采购价(元)</th>
                                <th fieldname="DELIVERY_CYCLE" colwidth="100" ftype="input" fdatatype="0,is_digit,30">供货周期(天)</th>
                                <th fieldname="MIN_PACK_UNIT" colwidth="100" ftype="input" fdatatype="1,is_null,30" fonblur="isCHN(this)" >最小包装单位</th>
                                <th fieldname="MIN_PACK_COUNT" colwidth="100" ftype="input" fdatatype="0,is_digit,30">最小包装数</th>
                                <th fieldname="REMARKS" colwidth="100" ftype="input" fdatatype="1,is_null,1000" fonblur="blurSave(this)">备注</th>
                                <th colwidth="105" type="link" title="[编辑]|[删除]"  action="doPartSave|doPartDelete">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="formBar">
                    <ul>
                        <li><div class="button"><div class="buttonContent"><button type="button" name="btn-pre">上&nbsp;一&nbsp;步</button></div></div></li>
                        <li id="dia-download" ><div class="button"><div class="buttonContent"><button type="button" id="btn-download" >下&nbsp;载&nbsp;导&nbsp;入&nbsp;模&nbsp;板</button></div></div></li>
                        <li id="dia-import" ><div class="button"><div class="buttonContent"><button type="button" id="btn-import" >批&nbsp;量&nbsp;导&nbsp;入</button></div></div></li>
                        <li id="btn-deleteAll" ><div class="button"><div class="buttonContent"><button type="button" id="btn-deleteAll" >全&nbsp;部&nbsp;删&nbsp;除</button></div></div></li>
                         <li id="dia-contror" style="display: none"><div class="button"><div class="buttonContent"><button type="button" id="btn-report" >提&nbsp;&nbsp;报</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
                    </ul>
                </div>
            </div>
            <!--促销备件Tab end-->
        </div>
        <div class="tabsFooter">
            <div class="tabsFooterContent"></div>
        </div>
        <form method="post" id="fm_part" >
        <input type="text" id="P_CONTRACT_ID" datasource="CONTRACT_ID"/>
		<input type="text" id="P_DETAIL_ID" datasource="DETAIL_ID"/>
		<input type="text" id="P_PART_CODE" datasource="PART_CODE"/>
		<input type="text" id="P_PART_NAME" datasource="PART_NAME"/>
		<input type="text" id="P_UNIT_PRICE" datasource="UNIT_PRICE"/>
		<input type="text" id="P_DELIVERY_CYCLE" datasource="DELIVERY_CYCLE"/>
		<input type="text" id="P_MIN_PACK_UNIT" datasource="MIN_PACK_UNIT"/>
		<input type="text" id="P_MIN_PACK_COUNT" datasource="MIN_PACK_COUNT"/>
		<input type="text" id="P_REMARKS" datasource="REMARKS"/>
	</form>
    </div>
</div>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
    //注册上一步/下一步按钮响应
    (function ($) {
        $.contractMng = {
            /**
             * 初始化方法
             */
            init: function () {
                //设置页面默认值
                $tabs = $("div.tabs:first");
                var iH = document.documentElement.clientHeight;
                $(".tabsContent").height(iH - 70);
                $("button[name='btn-next']").bind("click", function () {
                    $.contractMng.doNextTab($tabs);
                });
                $("button[name='btn-pre']").bind("click", function () {
                    $.contractMng.doPreTab($tabs);
                });
            },
            doNextTab: function ($tabs) {
                $tabs.switchTab(parseInt($tabs.attr("currentIndex")) + 1);
                (function (ci) {
                    switch (parseInt(ci)) {
                        case 1://第2个tab页
                            if(!$('#dia-CONTRACT_ID').val()){
                                alertMsg.warn('请先保存采购合同信息!');
                            }else{
                                if($("#dia-tab-partinfo").is(":hidden"))
                                {
                                    $("#dia-tab-partinfo").show();
                                    $("#dia-tab-partinfo").jTable();
                                }
                                searchPart();
                            }
                            break;
                        default:
                            break;
                    }
                })(parseInt($tabs.attr("currentIndex")));
            },
            doPreTab: function ($tabs) {
                $tabs.switchTab($tabs.attr("currentIndex") - 1);
            }
        };
    })(jQuery);
/* 取父页面传过的参数确定新增或者修改 */
    var diaAction = "<%=action%>";
    var diaUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseContract/PurchaseContractManageAction";
    var flag = true;
    var sign = true;
    $(function () {
    //    $.contractMng.init();
        var iH = document.documentElement.clientHeight;
        $(".tabsContent").height(iH - 70);
        $("button[name='btn-pre']").bind("click",function(event){
            $("#dia-tabs").switchTab(parseInt($("#dia-tabs").attr("currentIndex"))-1);
        });
        $("button[name='btn-next']").bind("click",function(event){
            var $tabs = $("#dia-tabs");
            $tabs.switchTab(parseInt($tabs.attr("currentIndex"))+1);
            //跳转后实现方法
            switch(parseInt($tabs.attr("currentIndex")))
            {
                case 1:
                    if (!$('#dia-CONTRACT_ID').val()) {
                        alertMsg.warn('请先保存采购合同信息!')
                        $tabs.switchTab(parseInt($tabs.attr("currentIndex"))-1);
                    } else {
                        if(flag){
                            if ($("#dia-tab-partinfo").is(":hidden")) {
                                $("#dia-tab-partinfo").show();
                                $("#dia-tab-partinfo").jTable();
                            }
                            searchPart();
                        }
                        flag = false;
                    }
                    break;
                default:
                    break;
            }
        });
        //保存基本信息按钮响应
        $("#btn-save").bind("click", function(event){
			var $f = $("#dia-fm-contractEdit");
			if (submitForm($f) == false) return false;
			var sCondition = {};
			sCondition = $("#dia-fm-contractEdit").combined(1) || {};
			if(diaAction == 1)
			{
				var addUrl = diaUrl + "/contractInsert.ajax";
				doNormalSubmit($f,addUrl,"btn-save",sCondition,diaInsertCallBack);
			}else	
			{
				var updateUrl = diaUrl + "/contractUpdate.ajax";
				doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
			}
		});
        //提交按钮响应
        var row;
        $("#btn-report").bind("click", function(){
        	if($("#dia-tab-partinfo_content").find("tr").size()==0){
    			alertMsg.warn("请添加合同所需订购的配件信息.");
    			return false;
    		}else if($("#dia-tab-partinfo_content").find("tr").size() == 1)
    		{
    			if($("#dia-tab-partinfo_content").find("tr").eq(0).find("td").size() == 1)
    			{
    				alertMsg.warn("请添加合同所需订购的配件信息.");
        			return false;
    			}
    		}
        	var flag = $("#FLAG").val();
        	if(parseInt(flag)!=2){
        		alertMsg.warn("请添加合同所需订购的配件信息.");
        		return false;
        	}
    		var CONTRACT_ID = $("#dia-CONTRACT_ID").val();
    		var reportUrl = diaUrl + "/contractReport.ajax?CONTRACT_ID="+CONTRACT_ID;
    		sendPost(reportUrl,"report","",reportCallBack,"true");  
    	});
        
        //全部删除按钮确定
        $("#btn-deleteAll").bind("click", function(){
        	var CONTRACT_ID = $("#dia-CONTRACT_ID").val();
        	var deleteAllUrl = diaUrl + "/deleteAllParts.ajax?CONTRACT_ID="+CONTRACT_ID;
    		sendPost(deleteAllUrl,"","",deleteAllCallBack,"true");  
    	});
    	//关闭按钮响应
    	$("button.close").click(function(){
    		$.pdialog.closeCurrent();
    		return false;
    	});
		//修改页面赋值
        if (diaAction != "1") {
        	$("#dia-contror").show();
            var selectedRows = $("#tab-contract").getSelectedRows();
            setEditValue("dia-fm-contractEdit", selectedRows[0].attr("rowdata"));
            $("#FLAG").val(2);
            /**
            var CONTRACT_ID = $("#dia-CONTRACT_ID").val();
    		var searchContractPartUrl = diaUrl + "/contractPartSearch.ajax?CONTRACT_ID="+CONTRACT_ID;
    		var $f = $("#dia-fm-contractEdit");
    		var sCondition = {};
    	    sCondition = $f.combined() || {};
    	    doFormSubmit($f, searchContractPartUrl, "", 1, sCondition, "dia-tab-partinfo");
    	    */
        } 
    })
    function afterDicItemClick(id,$row){
		var ret = true;
		if(id == "dia-SUPPLIER_CODE")
		{
			var curRow = $("#"+id);
			curRow.attr("SUPPLIER_ID",$row.attr("SUPPLIER_ID"));
			curRow.attr("code",$row.attr("SUPPLIER_CODE"));
			$("#dia-SUPPLIER_NAME").val($("#"+id).val());
			$("#dia-SUPPLIER_CODE").val($("#"+id).attr("code"));
			$("#dia-SUPPLIER_ID").val($("#"+id).attr("SUPPLIER_ID"));
			
		}
		return ret;
	}
	//新增方法回调
    function diaInsertCallBack(res)
    {
    	var rows = res.getElementsByTagName("ROW");
    	try
    	{
    		var contractId = getNodeText(rows[0].getElementsByTagName("CONTRACT_ID").item(0));
            $('#dia-CONTRACT_ID').val(contractId);
            $("#dia-contror").show();
    		$("#tab-contract").insertResult(res,0);
    		
    		diaAction = 2;
    		if($("#tab-contract_content").size()>0){
    			$("td input[type=radio]",$("#tab-contract_content").find("tr").eq(0)).attr("checked",true);			
    		}else{
    			$("td input[type=radio]",$("#tab-contract").find("tr").eq(0)).attr("checked",true);
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
    		var selectedIndex = $("#tab-contract").getSelectedIndex();
    		$("#tab-contract").updateResult(res,selectedIndex);
    	}catch(e)
    	{
    		alertMsg.error(e);
    		return false;
    	}
    	return true;
    }
    //查询采购合同配件
    function searchPart(){
    	var CONTRACT_ID = $("#dia-CONTRACT_ID").val();
		var searchContractPartUrl = diaUrl + "/contractPartSearch.ajax?CONTRACT_ID="+CONTRACT_ID;
		var $f = $("#dia-fm-contractEdit");
		var sCondition = {};
	    sCondition = $f.combined() || {};
	    doFormSubmit($f, searchContractPartUrl, "", 1, sCondition, "dia-tab-partinfo");
    }
    //采购合同配件删除
    var $row;
    function doPartDelete(rowobj)
    {
    	$row = $(rowobj);
    	var id = $(rowobj).attr("DETAIL_ID");
    	if(id){
    		var url = diaUrl + "/contractPartDelete.ajax?DETAIL_ID="+$(rowobj).attr("DETAIL_ID");
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
    function addParts()
    {
    	var flag = $("#FLAG").val();
    	var $tab = $("#dia-tab-partinfo");
    	if(parseInt(flag)!=2){
    		alertMsg.warn("当前行数据尚未提交成功！");
    		return false;
    	}else{
    		$tab.createRow();
    		$("#FLAG").val(1);
    	}
    }
    var $row;
    function reportCallBack(res){
  /*   	var selectedRows = $("#tab-contract").getSelectedRows();
		$row =  selectedRows[0];
		$("#tab-contract").removeResult($row);
		$.pdialog.closeCurrent(); */
    	toSearchContract();
    	$.pdialog.closeCurrent();
    }
	
    /*行编辑保存提交后台方法  */
    function doPartSave(row){
		var ret = true;
		try
		{
			$("td input[type=radio]",$(row)).attr("checked",true);
			var $f = $("#fm_part");
			if (submitForm($(row)) == false) return false;
			var DETAIL_ID = $(row).attr("DETAIL_ID");
			var CONTRACT_ID = $("#dia-CONTRACT_ID").val();
			//设置隐藏域
			$("#P_DETAIL_ID").val(DETAIL_ID);
			$("#P_CONTRACT_ID").val(CONTRACT_ID);
			$("#P_PART_CODE").val($(row).find("td").eq(2).find("input:first").val());
			$("#P_PART_NAME").val($(row).find("td").eq(3).find("input:first").val());
			$("#P_UNIT_PRICE").val($(row).find("td").eq(4).find("input:first").val());
			$("#P_DELIVERY_CYCLE").val($(row).find("td").eq(5).find("input:first").val());
			$("#P_MIN_PACK_UNIT").val($(row).find("td").eq(6).find("input:first").val());
			$("#P_MIN_PACK_COUNT").val($(row).find("td").eq(7).find("input:first").val());
			$("#P_REMARKS").val($(row).find("td").eq(8).find("input:first").val());
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
         // 读取XML中的FLAG属性(FLAG:true有重复数据;)
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
         	sign = true;
         	$("#FLAG").val(2);
         	return true;
         }
    	

    }
/*     function doCheck(obj){
    	var $obj = $(obj);
		var t = $obj.val();
		var d = new Date();
		if(t<=d){
			alertMsg.warn("有效期不能小于当前时间！");
			$obj.val("");
			return false;
		}else{
			return true;
		}

    	
    } */
    function isCHN(obj)
    {
    	var $obj = $(obj);
        var reg =  /^[\u4e00-\u9fa5]{0,}$/;
        if(reg.test($obj.val()))
        {
            return true;
        }else
        {
        	alertMsg.warn("请输入汉字！");
			$obj.val("");
            return false;
        }
    }
    
    function blurSave(obj)
    {
    	var $obj = $(obj);
    	if(!$obj.val()){
    		alertMsg.warn("该处不能为空！");
    	}else{
    		try
    		{
    			while($obj.get(0).tagName != "TR")
	    			$obj = $obj.parent();
	    		doPartSave($obj);
			}catch(e){
				alertMsg.error(e);
			}
			return true;
    	}
    	
    }
    function checkChn(obj){
    	var $obj = $(obj);
    	var reg =  /^[\u4e00-\u9fa5]{0,}$/;
    	if(reg.test($obj.val()))
        {
    		alertMsg.warn("配件编码不能为汉字！");
			$obj.val("");
            return false;
        }else
        {
        	return true;
        }
    }
    $('#btn-download').bind('click', function () {
        var url = encodeURI("<%=request.getContextPath()%>/jsp/dms/common/download.jsp?filename=PchContPartImp.xls");
        window.location.href = url;
    });
	$('#btn-import').bind('click', function () {
        if(!$('#dia-CONTRACT_ID').val()){
            alertMsg.warn('请先保存基本信息!');
            return;
        }
        //9:表示最大列，3：表示有效数据是第几行开始，第一行是1
        //最后一个参数表示 导入成功后显示页
        importXls("PT_BU_PCH_CONT_PART_TMP",$('#dia-CONTRACT_ID').val(),8,3,"/jsp/dms/oem/part/purchase/purchaseContract/contractImportSuccess.jsp");
    });
	function doImportConfirm(a,b)
	{
	    var impUrl = diaSaveAction +'/importPart.ajax?CONTRACT_ID='+$('#dia-CONTRACT_ID').val();
	    sendPost(impUrl,"","",impPromPartCallback);
	}
	function impPromPartCallback(res){
	    try
	    {
	    	searchPart();
	    }catch(e)
	    {
	        alertMsg.error(e.description);
	        return false;
	    }
	    return true;
	}
	function deleteAllCallBack(res){
		searchPart();
	}
	function doCheck(obj){
		var $obj = $(obj);
		var supplierCode = $obj.val();
		var checkUrl = diaUrl + "/checkSupplier.ajax?&supplierCode="+supplierCode;
        sendPost(checkUrl, "", "", supplierSearchCallback, "false");
	}
	function supplierSearchCallback(res){
        try {
            var rows = res.getElementsByTagName("ROW");
            if(rows && rows.length > 0)
            {
                for(var i=0;i<rows.length;i++){
                    var supplierCode = getNodeText(rows[i].getElementsByTagName("SUPPLIER_CODE").item(0));
                    var supplierName = getNodeText(rows[i].getElementsByTagName("SUPPLIER_NAME").item(0));
                    var SUPPLIER_QUALIFY = getNodeText(rows[i].getElementsByTagName("SUPPLIER_QUALIFY").item(0));
                    var SUPPLIER_QUALIFY = getNodeText(rows[i].getElementsByTagName("SUPPLIER_QUALIFY").item(0));
                    var TAX_TYPE = getNodeText(rows[i].getElementsByTagName("TAX_TYPE").item(0));
                    var TAX_TYPE1 = getNodeText(rows[i].getElementsByTagName("DIC_VALUE").item(0));
                    var LEGAL_PERSON = getNodeText(rows[i].getElementsByTagName("LEGAL_PERSON").item(0));
                    var LEGAL_PERSON_PHONE = getNodeText(rows[i].getElementsByTagName("LEGAL_PERSON_PHONE").item(0));
                    var TAX_RATE = getNodeText(rows[i].getElementsByTagName("TAX_RATE").item(0));
                    var BUSINESS_PERSON = getNodeText(rows[i].getElementsByTagName("BUSINESS_PERSON").item(0));
                    var BUSINESS_PERSON_PHONE = getNodeText(rows[i].getElementsByTagName("BUSINESS_PERSON_PHONE").item(0));
                    var OPEN_ACCOUNT = getNodeText(rows[i].getElementsByTagName("OPEN_ACCOUNT").item(0));
                    var GUARANTEE_MONEY = getNodeText(rows[i].getElementsByTagName("GUARANTEE_MONEY").item(0));
                    var WARRANTY_PERIOD = getNodeText(rows[i].getElementsByTagName("WARRANTY_PERIOD").item(0));
                    $("#dia-SUPPLIER_CODE").val(supplierCode);
                    $("#dia-SUPPLIER_CODE").attr("readonly",true);
                    $("#dia-SUPPLIER_NAME").val(supplierName);
                    $("#dia-SUPPLIER_NAME").attr("readonly",true);
                    $("#dia-SUPPLIER_QUALIFY").val(SUPPLIER_QUALIFY);
                    $("#dia-SUPPLIER_CODE").attr("readonly",true);
                    $("#dia-INVOICE_TYPE").val(TAX_TYPE1);
                    $("#dia-INVOICE_TYPE").attr("code",TAX_TYPE);
                    $("#dia-INVOICE_TYPE").attr("readonly",true);
                    $("#dia-LEGAL_PERSON").val(LEGAL_PERSON);
                    $("#dia-LEGAL_PERSON").attr("readonly",true);
                    $("#dia-LEGAL_PERSON_PHONE").val(LEGAL_PERSON_PHONE);
                    $("#dia-LEGAL_PERSON_PHONE").attr("readonly",true);
                    $("#dia-TAX_RATE").val(TAX_RATE);
                    $("#dia-TAX_RATE").attr("readonly",true);
                    $("#dia-BUSINESS_PERSON").val(BUSINESS_PERSON);
                    $("#dia-BUSINESS_PERSON").attr("readonly",true);
                    $("#dia-BUSINESS_PERSON_PHONE").val(BUSINESS_PERSON_PHONE);
                    $("#dia-BUSINESS_PERSON_PHONE").attr("readonly",true);
                    $("#dia-OPEN_ACCOUNT").val(OPEN_ACCOUNT);
                    $("#dia-OPEN_ACCOUNT").attr("readonly",true);
                    $("#dia-GUARANTEE_MONEY").val(GUARANTEE_MONEY);
                    $("#dia-GUARANTEE_MONEY").attr("readonly",true);
                    $("#dia-WARRANTY_PERIOD").val(WARRANTY_PERIOD);
                    $("#dia-WARRANTY_PERIOD").attr("readonly",true);
                    
                }
            }else{
            	$("#dia-SUPPLIER_CODE").attr("readonly",false);
                $("#dia-SUPPLIER_NAME").attr("readonly",false);
            }
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }
</script>