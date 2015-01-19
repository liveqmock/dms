package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuScanCodeBarcodeVO extends BaseVO{
    public PtBuScanCodeBarcodeVO(){
    	//�����ֶ���Ϣ
    	this.addField("SUPPLIER_NAME",BaseVO.OP_STRING);
    	this.addField("BARCODE_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("PART_NAME",BaseVO.OP_STRING);
    	this.addField("PART_ID",BaseVO.OP_STRING);
    	this.addField("BARCODE",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_CODE",BaseVO.OP_STRING);
    	this.addField("POSITION_ID",BaseVO.OP_STRING);
    	this.addField("COUNTS",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_ID",BaseVO.OP_STRING);
    	this.addField("PART_CODE",BaseVO.OP_STRING);
		this.bindFieldToSequence("BARCODE_ID","COMMON");
    	this.setVOTableName("PT_BU_SCAN_CODE_BARCODE");
}
	public void setSupplierName(String SupplierName){
		this.setInternal("SUPPLIER_NAME" ,SupplierName );
	}


	public String getSupplierName(){
		return (String)this.getInternal("SUPPLIER_NAME");
	}
	public void setBarcodeId(String BarcodeId){
		this.setInternal("BARCODE_ID" ,BarcodeId );
	}


	public String getBarcodeId(){
		return (String)this.getInternal("BARCODE_ID");
	}
	public void setPartName(String PartName){
		this.setInternal("PART_NAME" ,PartName );
	}


	public String getPartName(){
		return (String)this.getInternal("PART_NAME");
	}
	public void setPartId(String PartId){
		this.setInternal("PART_ID" ,PartId );
	}


	public String getPartId(){
		return (String)this.getInternal("PART_ID");
	}
	public void setBarcode(String Barcode){
		this.setInternal("BARCODE" ,Barcode );
	}


	public String getBarcode(){
		return (String)this.getInternal("BARCODE");
	}
	public void setSupplierCode(String SupplierCode){
		this.setInternal("SUPPLIER_CODE" ,SupplierCode );
	}


	public String getSupplierCode(){
		return (String)this.getInternal("SUPPLIER_CODE");
	}
	public void setPositionId(String PositionId){
		this.setInternal("POSITION_ID" ,PositionId );
	}


	public String getPositionId(){
		return (String)this.getInternal("POSITION_ID");
	}
	public void setCounts(String Counts){
		this.setInternal("COUNTS" ,Counts );
	}


	public String getCounts(){
		return (String)this.getInternal("COUNTS");
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
}
