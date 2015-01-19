<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="vinCh" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="di-vinCh" class="editForm" >
			<div align="left">
			<fieldset>
			<table class="editTable" id="di_tab_vinCh">
				<input type="hidden" id="dia-di-modelsId" name="dia-di-modelsId" datasource="MODELS_ID"/>
				<input type="hidden" id="dia-di-vehicleId" name="dia-di-vehicleId" datasource="VEHICLE_ID"/>
	        <tr>
	          <td><label>VIN：</label></td>
	          <td><input type="text" id="dia-di-vin" name="dia-di-vin" datasource="VIN"  readonly="readonly"/></td>
	          <td><label>发动机号：</label></td>
	          <td><input type="text" id="dia-di-engine_no" name="dia-di-engine_no" datasource="ENGINE_NO"  readonly="readonly"/></td>
	          <td><label>发动型号：</label></td>
	          <td><input type="text" id="dia-di-engine_type" name="dia-di-engine_type" datasource="ENGINE_TYPE"  readonly="readonly"/></td>
	        </tr>
	        <tr>
	          <td><label>车辆型号：</label></td>
	          <td><input type="text" id="dia-di-models_code" name="dia-di-models_code" datasource="MODELS_CODE"  readonly="readonly" /></td>
	          <td><label>购车日期：</label></td>
	          <td><input type="text" id="dia-di-buy_date" name="dia-di-buy_date" datasource="BUY_DATE" value=""  readonly="readonly" /></td>
	          <td><label>行驶里程：</label></td>
	          <td><input type="text" id="dia-di-mileage" name="dia-di-mileage"  datasource="MILEAGE" value="" readonly="readonly"/></td>
	        </tr>
	      </table>
	      </fieldset>
	      </div>
	    </form>
    <div class="formBar">
      <ul>
        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="enter">确&nbsp;&nbsp;定</button></div></div></li>
        <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
      </ul>
    </div>
  </div>
  </div>  
</div>
<script type="text/javascript">
//弹出窗体
var dia_di_dialog =$("body").data("vinCheck");
var vinChUrl="<%=request.getContextPath()%>/service/qualityretroaction/QualityretroactionMngAction/qualityretroactionVinCheck.ajax";
$(function()
{
  var diVinVal=$("#dia-vin").val();
  var diEngineNoVal=$("#dia-engine_no").val();
  var url = vinChUrl +"?diVinVal="+diVinVal+"&diEngineNoVal="+diEngineNoVal+"";
  sendPost(url,"","",searchCallBack,"false");
  
  $("#enter").bind("click",function(){
    checkVinCallBack(1);
    $.pdialog.close(dia_di_dialog);
    return false;
  });
  //关闭当前页面
  $("button.close").click(function() 
  {
    $.pdialog.close(dia_di_dialog);
    return false;
  });
});
function searchCallBack(res){
  try
  {
    var rows = res.getElementsByTagName("ROW");
    if(rows && rows.length > 0)
    {
      
      var objxml = res.documentElement;
      setEditValue("di_tab_vinCh",objxml);
    }else
    {
      checkVinCallBack(2);
      $.pdialog.close(dia_di_dialog);
      return false;
    }  
  }catch(e)
  {
    alertMsg.error(e.description);
    return false;
  }
  return true;
}
</script>