<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    <div id="dia-part-layout">
        <div class="page">
            <div class="pageHeader">
                <form method="post" id="fm-searchPart">
                    <div class="searchBar" align="left">
                        <table class="searchContent" id="tab-searchPart">
                            <!-- 定义查询条件 -->
                            <tr>
                                <td><label>配件代码：</label></td>
                                <td><input type="text" id="dia-PART_CODE" name="dia-PART_CODE" datasource="A.PART_CODE" datatype="1,is_null,30" operation="like"/></td>
                                <td><label>配件名称：</label></td>
                                <td><input type="text" id="dia-PART_NAME" name="dia-PART_NAME" datasource="A.PART_NAME" datatype="1,is_null,30" operation="like"/></td>
                            </tr>
                        </table>
                        <div class="subBar">
                            <ul>
                                <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-searchPart">查&nbsp;&nbsp;询</button></div></div></li>
                                <li><div class="button"><div class="buttonContent"><button type="button" id="btn-dia-confirmPart">确&nbsp;&nbsp;定</button></div></div></li>
                                <li><div class="button"><div class="buttonContent"><button type="button" id="btn-closePart">关&nbsp;&nbsp;闭</button></div></div></li>
                            </ul>
                        </div>
                    </div>
                </form>
            </div>
            <div class="pageContent">
                <div id="div-partList">
                    <table style="display:none;width:100%;" id="tab-partList" multivals="div-selectedPart" name="tablist" ref="div-partList" refQuery="tab-searchPart">
                        <thead>
                            <tr>
                                <th type="multi" name="XH" unique="DTL_ID" style=""></th>
                                <th fieldname="PART_CODE" colwidth="100">配件代码</th>
                                <th fieldname="PART_NAME" maxlength="10" colwidth="100">配件名称</th>
                                <th fieldname="PART_NO" style="display: none">配件图号</th>
                                <th fieldname="UNIT" colwidth="60">计量单位</th>
                                <th fieldname="MIN_PACK" refer="toAppendStr1" colwidth="60">最小包装</th>
                                <th fieldname="SALE_PRICE" refer="formatAmount1" align="right" colwidth="60">经销商价</th>
                                <th fieldname="SUPPLIER_NAME" colwidth="60">供应商</th>
                                <th fieldname="POSITION_NAME" colwidth="100">库位名称</th>
                                <th fieldname="AVAILABLE_AMOUNT" colwidth="60">可用库存</th>
                                <th fieldname="AVAILABLE_AMOUNT" refer="myOutAmountInput" colwidth="60">移库数量</th>
                            </tr>
                        </thead>
                    </table>
                </div>
                <fieldset id="fie-selectedPart" style="display: ">
                    <legend align="left" >&nbsp;[已选定配件]</legend>
                    <div id="div-selectedPart">
                        <table style="width:100%;">
                            <tr>
                                <td>
                                    <textarea style="width:80%;height:26px;display: none" id="val0" column="1" name="multivals" readOnly></textarea>
                                    <textarea style="width:80%;height:26px;" id="val1" name="multivals" readOnly></textarea>
                                    <textarea style="width:99%;height:50px;display: none" id="val2" name="multivals" readOnly></textarea>
                                </td>
                            </tr>
                        </table>
                    </div>    
                </fieldset>
                 <form id="fm-dia-partInfo" style="display:none">
                    <input type="hidden" id="dia-part-outId" name="dia-part-outId" datasource="OUT_ID"/>
                    <input type="hidden" id="dia-dtlIds" name="dia-dtlIds" datasource="DTLIDS"/>
                    <input type="hidden" id="dia-outamounts" name="dia-outamounts" datasource="OUTAMOUNTS"/>
                </form>
            </div>
        </div>
    </div>
<script type="text/javascript">
    //弹出窗体
    var partSelWin = $("body").data("partSelWin");
    $("#tab-partList").attr("layoutH",document.documentElement.clientHeight-180);
    $(function () {

        // 查询配件
        searchPart();
        $("#btn-closePart").click(function () {
            $.pdialog.close(partSelWin);
            return false;
        });
        // 查询按钮绑定事件
        $("#btn-searchPart").bind("click", function (event) {
            // 查询配件
            searchPart();
        });
        //确定按钮响应
        $('#btn-dia-confirmPart').bind('click',function(){
            //添加配件URL
            var insertPromPartUrl = "<%=request.getContextPath()%>/part/storageMng/stockOutMng/MoveStockOutMngAction/insertOutBillPart.ajax";
            var stockIds = $('#val0').val();
            if(!stockIds){
                alertMsg.warn('请选择配件!');
                return false;
            }else{
                $('#dia-part-outId').val($('#dia-OUT_ID').val());//出库单ID
                $('#dia-dtlIds').val($('#val0').val());
                $('#dia-outamounts').val($('#val2').val());
                //获取需要提交的form对象
                var $f = $("#fm-dia-partInfo");
                //submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
                if (submitForm($f) == false) return false;
                var sCondition = {};
                //将需要提交的内容拼接成json
                sCondition = $f.combined(1) || {};
                doNormalSubmit($f, insertPromPartUrl, "btn-dia-confirmPart", sCondition, insertOutBillPartCallBack);
            }
        });
    });

    // 查询方法
    function searchPart() {
        //查询配件URL
        var searchPartUrl = "<%=request.getContextPath()%>/part/storageMng/stockOutMng/MoveStockOutMngAction/searchPart.ajax?outId="+$('#dia-OUT_ID').val()+"&warehouseId="+$('#dia-WAREHOUSE_ID').val();
        var $f = $("#fm-searchPart");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, searchPartUrl, "btn-searchPart", 1, sCondition, "tab-partList");
    }

    //新增出库单配件回调
    function insertOutBillPartCallBack(){
        //查询出库单配件
        searchOutBillPart();
        $.pdialog.close(partSelWin);
    }
    //格式化数量
    function formatAmount1(obj){
        return amountFormatNew($(obj).html());
    }
    function toAppendStr1(obj){
        var $tr =$(obj).parent();
        return $tr.attr("MIN_PACK")+"/"+$tr.attr("MIN_UNIT_sv");
    }
    //生成填写出库数量输入框
    function myOutAmountInput(obj)
    {
    	return "<input type='text' name='OUT_AMOUNT' maxlength='6' onblur='doWpBlur(this);' value='' style='width:40px;padding-top:-5px;'/>";
    }
    //输入数量后校验方法
    function doWpBlur(obj)
    {
    	var $obj = $(obj);
    	var $tr = $obj;
    	while($tr.get(0).tagName != "TR")
    		$tr = $tr.parent();
    	var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
    	if($obj.val() == "") {//为空直接返回
             return ;
         }
    	if($obj.val().length > 0)
    	{
    		if(!isNumV($obj.val()))
    		{
    			alertMsg.warn("请正确输入的入库数量.");
    			return false;
    		}
    	}
    	//校验输入数量：出库数量<=可用数量
    	//可用库存
    	var avaCount = $tr.attr("AVAILABLE_AMOUNT");
    	if(parseInt($obj.val(),10) > parseInt(avaCount,10))
    	{
    		alertMsg.warn("出库数量不能大于可用库存.");
    		return false;
    	}

    	if($obj.val() > 0)
    	{
    		$checkbox.attr("checked",true);
    	}
    	doCheckbox($checkbox.get(0));
    }
    /*
     * 选择方法
     */
    function doCheckbox(checkbox) {
    	var $obj = $(checkbox);
    	var $tr = $obj;
    	while($tr.get(0).tagName != "TR")
    	{
    		$tr = $tr.parent();
    	}
         var s = "";
         if($tr.find("td").eq(11).find("input:first").size()>0) {
             s = $tr.find("td").eq(11).find("input:first").val();
         } else {
             s = $tr.find("td").eq(11).text();
         }
         if (!s || !isNumV(s)) {//为空或者不是数量
             alertMsg.warn("请输入正确的数量!");
             $(checkbox).attr("checked",false);
             return false;
         }
        var arr = [];
        arr.push($tr.attr("DTL_ID"));
        arr.push($tr.attr("PART_CODE"));
        arr.push(s);
        var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
        multiSelected($checkbox, arr,$('#div-selectedPart'));
        //设置input框显示或文本只读
        if($checkbox.is(":checked")) {
            $tr.find("td").eq(11).html("<div>"+s+"</div>");
        } else {
            $tr.find("td").eq(11).html("<input type='text' name='OUT_AMOUNT' maxlength='6' onblur='doWpBlur(this);' value='"+s+"' style='width:40px;padding-top:-5px;'/>");
        }
    }
    /*
     * 翻页回显方法
     */
     function customOtherValue($row,checkVal)
     {
    	 var $inputObj = $row.find("td").eq(11);
         var val=0;
         if($("#val0") && $("#val0").val()) {
             var t = $("#val2").val();
             var pks = $("#val0").val();
             var ss = t.split(",");
             var pk = pks.split(",");
             for(var i=0;i<pk.length;i++) {
                 if(pk[i] == checkVal) {
                     val = ss[i];
                     break;
                 }
             }
         }
         if(val>0) {
             $inputObj.html("<div>"+val+"</div>");
         }
    	
     }
</script>