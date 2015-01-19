package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuInventoryDtlVO extends BaseVO{
    public PtBuInventoryDtlVO(){
    	//�����ֶ���Ϣ
    	this.addField("MATERIAL_AMOUNT",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_NAME",BaseVO.OP_STRING);
    	this.addField("INVENTORY_NO",BaseVO.OP_STRING);
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("PART_NAME",BaseVO.OP_STRING);
    	this.addField("PAPER_AMOUNT",BaseVO.OP_STRING);
    	this.addField("AREA_CODE",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_CODE",BaseVO.OP_STRING);
    	this.addField("AREA_ID",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_ID",BaseVO.OP_STRING);
    	this.addField("INVENTORY_ID",BaseVO.OP_STRING);
    	this.addField("DTL_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("PART_ID",BaseVO.OP_STRING);
    	this.addField("INVENTORY_RESULT",BaseVO.OP_STRING);
    	this.addField("MATERIAL_COUNT",BaseVO.OP_STRING);
    	this.addField("POSITION_NAME",BaseVO.OP_STRING);
    	this.addField("POSITION_CODE",BaseVO.OP_STRING);
    	this.addField("PART_CODE",BaseVO.OP_STRING);
    	this.addField("AMOUNT",BaseVO.OP_STRING);
    	this.addField("STOCK_DTL_ID",BaseVO.OP_STRING);
    	this.addField("WHOUSE_KEEPER",BaseVO.OP_STRING);
    	this.addField("PLAN_PRICE",BaseVO.OP_STRING);
    	this.addField("PAPER_COUNT",BaseVO.OP_STRING);
    	this.addField("AREA_NAME",BaseVO.OP_STRING);
    	this.addField("POSITION_ID",BaseVO.OP_STRING);
		this.bindFieldToSequence("DTL_ID","COMMON");
    	this.setVOTableName("PT_BU_INVENTORY_DTL");
}
	public void setMaterialAmount(String MaterialAmount){
		this.setInternal("MATERIAL_AMOUNT" ,MaterialAmount );
	}


	public String getMaterialAmount(){
		return (String)this.getInternal("MATERIAL_AMOUNT");
	}
	public void setSupplierName(String SupplierName){
		this.setInternal("SUPPLIER_NAME" ,SupplierName );
	}


	public String getSupplierName(){
		return (String)this.getInternal("SUPPLIER_NAME");
	}
	public void setInventoryNo(String InventoryNo){
		this.setInternal("INVENTORY_NO" ,InventoryNo );
	}


	public String getInventoryNo(){
		return (String)this.getInternal("INVENTORY_NO");
	}
	public void setRemarks(String Remarks){
		this.setInternal("REMARKS" ,Remarks );
	}


	public String getRemarks(){
		return (String)this.getInternal("REMARKS");
	}
	public void setPartName(String PartName){
		this.setInternal("PART_NAME" ,PartName );
	}


	public String getPartName(){
		return (String)this.getInternal("PART_NAME");
	}
	public void setPaperAmount(String PaperAmount){
		this.setInternal("PAPER_AMOUNT" ,PaperAmount );
	}


	public String getPaperAmount(){
		return (String)this.getInternal("PAPER_AMOUNT");
	}
	public void setAreaCode(String AreaCode){
		this.setInternal("AREA_CODE" ,AreaCode );
	}


	public String getAreaCode(){
		return (String)this.getInternal("AREA_CODE");
	}
	public void setSupplierCode(String SupplierCode){
		this.setInternal("SUPPLIER_CODE" ,SupplierCode );
	}


	public String getSupplierCode(){
		return (String)this.getInternal("SUPPLIER_CODE");
	}
	public void setAreaId(String AreaId){
		this.setInternal("AREA_ID" ,AreaId );
	}


	public String getAreaId(){
		return (String)this.getInternal("AREA_ID");
	}
	public void setSupplierId(String SupplierId){
		this.setInternal("SUPPLIER_ID" ,SupplierId );
	}


	public String getSupplierId(){
		return (String)this.getInternal("SUPPLIER_ID");
	}
	public void setInventoryId(String InventoryId){
		this.setInternal("INVENTORY_ID" ,InventoryId );
	}


	public String getInventoryId(){
		return (String)this.getInternal("INVENTORY_ID");
	}
	public void setDtlId(String DtlId){
		this.setInternal("DTL_ID" ,DtlId );
	}


	public String getDtlId(){
		return (String)this.getInternal("DTL_ID");
	}
	public void setPartId(String PartId){
		this.setInternal("PART_ID" ,PartId );
	}


	public String getPartId(){
		return (String)this.getInternal("PART_ID");
	}
	public void setInventoryResult(String InventoryResult){
		this.setInternal("INVENTORY_RESULT" ,InventoryResult );
	}


	public String getInventoryResult(){
		return (String)this.getInternal("INVENTORY_RESULT");
	}
	public void setMaterialCount(String MaterialCount){
		this.setInternal("MATERIAL_COUNT" ,MaterialCount );
	}


	public String getMaterialCount(){
		return (String)this.getInternal("MATERIAL_COUNT");
	}
	public void setPositionName(String PositionName){
		this.setInternal("POSITION_NAME" ,PositionName );
	}


	public String getPositionName(){
		return (String)this.getInternal("POSITION_NAME");
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
	public void setAmount(String Amount){
		this.setInternal("AMOUNT" ,Amount );
	}


	public String getAmount(){
		return (String)this.getInternal("AMOUNT");
	}
	public void setStockDtlId(String StockDtlId){
		this.setInternal("STOCK_DTL_ID" ,StockDtlId );
	}


	public String getStockDtlId(){
		return (String)this.getInternal("STOCK_DTL_ID");
	}
	public void setWhouseKeeper(String WhouseKeeper){
		this.setInternal("WHOUSE_KEEPER" ,WhouseKeeper );
	}


	public String getWhouseKeeper(){
		return (String)this.getInternal("WHOUSE_KEEPER");
	}
	public void setPlanPrice(String PlanPrice){
		this.setInternal("PLAN_PRICE" ,PlanPrice );
	}


	public String getPlanPrice(){
		return (String)this.getInternal("PLAN_PRICE");
	}
	public void setPaperCount(String PaperCount){
		this.setInternal("PAPER_COUNT" ,PaperCount );
	}


	public String getPaperCount(){
		return (String)this.getInternal("PAPER_COUNT");
	}
	public void setAreaName(String AreaName){
		this.setInternal("AREA_NAME" ,AreaName );
	}


	public String getAreaName(){
		return (String)this.getInternal("AREA_NAME");
	}
	public void setPositionId(String PositionId){
		this.setInternal("POSITION_ID" ,PositionId );
	}


	public String getPositionId(){
		return (String)this.getInternal("POSITION_ID");
	}
}
