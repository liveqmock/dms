package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuInventoryDtlTmpVO extends BaseVO{
    public PtBuInventoryDtlTmpVO(){
    	//设置字段信息
    	this.addField("WHOUSE_KEEPER",BaseVO.OP_STRING);
    	this.addField("ROW_NUM",BaseVO.OP_STRING);
    	this.addField("POSITION_CODE",BaseVO.OP_STRING);
    	this.addField("PART_CODE",BaseVO.OP_STRING);
    	this.addField("MATERIAL_AMOUNT",BaseVO.OP_STRING);
    	this.addField("INVENTORY_ID",BaseVO.OP_STRING);
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("POSITION_NAME",BaseVO.OP_STRING);
    	this.addField("AREA_CODE",BaseVO.OP_STRING);
    	this.addField("PLAN_PRICE",BaseVO.OP_STRING);
    	this.addField("DTL_ID",BaseVO.OP_STRING);
    	this.addField("INVENTORY_NO",BaseVO.OP_STRING);
    	this.addField("PART_NAME",BaseVO.OP_STRING);
    	this.addField("AMOUNT",BaseVO.OP_STRING);
    	this.addField("TMP_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("PAPER_AMOUNT",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_NAME",BaseVO.OP_STRING);
    	this.addField("MATERIAL_COUNT",BaseVO.OP_STRING);
    	this.addField("USER_ACCOUNT",BaseVO.OP_STRING);
    	this.addField("PAPER_COUNT",BaseVO.OP_STRING);
    	this.addField("INVENTORY_RESULT",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_CODE",BaseVO.OP_STRING);
		this.bindFieldToSequence("TMP_ID","COMMON");
    	this.setVOTableName("PT_BU_INVENTORY_DTL_TMP");
}
	public void setWhouseKeeper(String WhouseKeeper){
		this.setInternal("WHOUSE_KEEPER" ,WhouseKeeper );
	}


	public String getWhouseKeeper(){
		return (String)this.getInternal("WHOUSE_KEEPER");
	}
	public void setRowNum(String RowNum){
		this.setInternal("ROW_NUM" ,RowNum );
	}


	public String getRowNum(){
		return (String)this.getInternal("ROW_NUM");
	}
	public void setPositionCode(String PositionCode){
		this.setInternal("POSITION_CODE" ,PositionCode );
	}


	public String getPositionCode(){
		return (String)this.getInternal("POSITION_CODE");
	}
	public void setPartCode(String PartCode){
		this.setInternal("PART_CODE" ,PartCode );
	}


	public String getPartCode(){
		return (String)this.getInternal("PART_CODE");
	}
	public void setMaterialAmount(String MaterialAmount){
		this.setInternal("MATERIAL_AMOUNT" ,MaterialAmount );
	}


	public String getMaterialAmount(){
		return (String)this.getInternal("MATERIAL_AMOUNT");
	}
	public void setInventoryId(String InventoryId){
		this.setInternal("INVENTORY_ID" ,InventoryId );
	}


	public String getInventoryId(){
		return (String)this.getInternal("INVENTORY_ID");
	}
	public void setRemarks(String Remarks){
		this.setInternal("REMARKS" ,Remarks );
	}


	public String getRemarks(){
		return (String)this.getInternal("REMARKS");
	}
	public void setPositionName(String PositionName){
		this.setInternal("POSITION_NAME" ,PositionName );
	}


	public String getPositionName(){
		return (String)this.getInternal("POSITION_NAME");
	}
	public void setAreaCode(String AreaCode){
		this.setInternal("AREA_CODE" ,AreaCode );
	}


	public String getAreaCode(){
		return (String)this.getInternal("AREA_CODE");
	}
	public void setPlanPrice(String PlanPrice){
		this.setInternal("PLAN_PRICE" ,PlanPrice );
	}


	public String getPlanPrice(){
		return (String)this.getInternal("PLAN_PRICE");
	}
	public void setDtlId(String DtlId){
		this.setInternal("DTL_ID" ,DtlId );
	}


	public String getDtlId(){
		return (String)this.getInternal("DTL_ID");
	}
	public void setInventoryNo(String InventoryNo){
		this.setInternal("INVENTORY_NO" ,InventoryNo );
	}


	public String getInventoryNo(){
		return (String)this.getInternal("INVENTORY_NO");
	}
	public void setPartName(String PartName){
		this.setInternal("PART_NAME" ,PartName );
	}


	public String getPartName(){
		return (String)this.getInternal("PART_NAME");
	}
	public void setAmount(String Amount){
		this.setInternal("AMOUNT" ,Amount );
	}


	public String getAmount(){
		return (String)this.getInternal("AMOUNT");
	}
	public void setTmpId(String TmpId){
		this.setInternal("TMP_ID" ,TmpId );
	}


	public String getTmpId(){
		return (String)this.getInternal("TMP_ID");
	}
	public void setPaperAmount(String PaperAmount){
		this.setInternal("PAPER_AMOUNT" ,PaperAmount );
	}


	public String getPaperAmount(){
		return (String)this.getInternal("PAPER_AMOUNT");
	}
	public void setSupplierName(String SupplierName){
		this.setInternal("SUPPLIER_NAME" ,SupplierName );
	}


	public String getSupplierName(){
		return (String)this.getInternal("SUPPLIER_NAME");
	}
	public void setMaterialCount(String MaterialCount){
		this.setInternal("MATERIAL_COUNT" ,MaterialCount );
	}


	public String getMaterialCount(){
		return (String)this.getInternal("MATERIAL_COUNT");
	}
	public void setUserAccount(String UserAccount){
		this.setInternal("USER_ACCOUNT" ,UserAccount );
	}


	public String getUserAccount(){
		return (String)this.getInternal("USER_ACCOUNT");
	}
	public void setPaperCount(String PaperCount){
		this.setInternal("PAPER_COUNT" ,PaperCount );
	}


	public String getPaperCount(){
		return (String)this.getInternal("PAPER_COUNT");
	}
	public void setInventoryResult(String InventoryResult){
		this.setInternal("INVENTORY_RESULT" ,InventoryResult );
	}


	public String getInventoryResult(){
		return (String)this.getInternal("INVENTORY_RESULT");
	}
	public void setSupplierCode(String SupplierCode){
		this.setInternal("SUPPLIER_CODE" ,SupplierCode );
	}


	public String getSupplierCode(){
		return (String)this.getInternal("SUPPLIER_CODE");
	}
}
