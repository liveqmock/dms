package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuStockInDtlVO extends BaseVO{
    public PtBuStockInDtlVO(){
    	//�����ֶ���Ϣ
    	this.addField("SUPPLIER_NAME",BaseVO.OP_STRING);
    	this.addField("UNIT",BaseVO.OP_STRING);
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("PLAN_AMOUNT",BaseVO.OP_STRING);
    	this.addField("PART_NAME",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_CODE",BaseVO.OP_STRING);
    	this.addField("DETAIL_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("PCH_PRICE",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_ID",BaseVO.OP_STRING);
    	this.addField("PART_CODE",BaseVO.OP_STRING);
    	this.addField("IN_ID",BaseVO.OP_STRING);
    	this.addField("IN_AMOUNT",BaseVO.OP_STRING);
    	this.addField("PLAN_PRICE",BaseVO.OP_STRING);
    	this.addField("IN_AMOUNT_TMP",BaseVO.OP_STRING);
    	this.addField("PART_ID",BaseVO.OP_STRING);
    	this.addField("IN_NO",BaseVO.OP_STRING);
    	this.addField("PCH_AMOUNT",BaseVO.OP_STRING);
		this.bindFieldToSequence("DETAIL_ID","COMMON");
    	this.setVOTableName("PT_BU_STOCK_IN_DTL");
}
	public void setSupplierName(String SupplierName){
		this.setInternal("SUPPLIER_NAME" ,SupplierName );
	}


	public String getSupplierName(){
		return (String)this.getInternal("SUPPLIER_NAME");
	}
	public void setUnit(String Unit){
		this.setInternal("UNIT" ,Unit );
	}


	public String getUnit(){
		return (String)this.getInternal("UNIT");
	}
	public void setRemarks(String Remarks){
		this.setInternal("REMARKS" ,Remarks );
	}


	public String getRemarks(){
		return (String)this.getInternal("REMARKS");
	}
	public void setPlanAmount(String PlanAmount){
		this.setInternal("PLAN_AMOUNT" ,PlanAmount );
	}


	public String getPlanAmount(){
		return (String)this.getInternal("PLAN_AMOUNT");
	}
	public void setPartName(String PartName){
		this.setInternal("PART_NAME" ,PartName );
	}


	public String getPartName(){
		return (String)this.getInternal("PART_NAME");
	}
	public void setSupplierCode(String SupplierCode){
		this.setInternal("SUPPLIER_CODE" ,SupplierCode );
	}


	public String getSupplierCode(){
		return (String)this.getInternal("SUPPLIER_CODE");
	}
	public void setDetailId(String DetailId){
		this.setInternal("DETAIL_ID" ,DetailId );
	}


	public String getDetailId(){
		return (String)this.getInternal("DETAIL_ID");
	}
	public void setPchPrice(String PchPrice){
		this.setInternal("PCH_PRICE" ,PchPrice );
	}


	public String getPchPrice(){
		return (String)this.getInternal("PCH_PRICE");
	}
	public void setSupplierId(String SupplierId){
		this.setInternal("SUPPLIER_ID" ,SupplierId );
	}


	public String getSupplierId(){
		return (String)this.getInternal("SUPPLIER_ID");
	}
	public void setPartCode(String PartCode){
		this.setInternal("PART_CODE" ,PartCode );
	}


	public String getPartCode(){
		return (String)this.getInternal("PART_CODE");
	}
	public void setInId(String InId){
		this.setInternal("IN_ID" ,InId );
	}


	public String getInId(){
		return (String)this.getInternal("IN_ID");
	}
	public void setInAmount(String InAmount){
		this.setInternal("IN_AMOUNT" ,InAmount );
	}


	public String getInAmount(){
		return (String)this.getInternal("IN_AMOUNT");
	}
	public void setPlanPrice(String PlanPrice){
		this.setInternal("PLAN_PRICE" ,PlanPrice );
	}


	public String getPlanPrice(){
		return (String)this.getInternal("PLAN_PRICE");
	}
	public void setInAmountTmp(String InAmountTmp){
		this.setInternal("IN_AMOUNT_TMP" ,InAmountTmp );
	}


	public String getInAmountTmp(){
		return (String)this.getInternal("IN_AMOUNT_TMP");
	}
	public void setPartId(String PartId){
		this.setInternal("PART_ID" ,PartId );
	}


	public String getPartId(){
		return (String)this.getInternal("PART_ID");
	}
	public void setInNo(String InNo){
		this.setInternal("IN_NO" ,InNo );
	}


	public String getInNo(){
		return (String)this.getInternal("IN_NO");
	}
	public void setPchAmount(String PchAmount){
		this.setInternal("PCH_AMOUNT" ,PchAmount );
	}


	public String getPchAmount(){
		return (String)this.getInternal("PCH_AMOUNT");
	}
}
