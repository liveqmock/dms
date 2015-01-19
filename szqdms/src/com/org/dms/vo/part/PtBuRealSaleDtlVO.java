package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuRealSaleDtlVO extends BaseVO{
    public PtBuRealSaleDtlVO(){
    	//�����ֶ���Ϣ
    	this.addField("UNIT",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("PART_CODE",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_ID",BaseVO.OP_STRING);
    	this.addField("SALE_ID",BaseVO.OP_STRING);
    	this.addField("PART_ID",BaseVO.OP_STRING);
    	this.addField("SALE_PRICE",BaseVO.OP_STRING);
    	this.addField("DTL_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("PART_NAME",BaseVO.OP_STRING);
    	this.addField("AMOUNT",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("SUPPLIER_NAME",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_CODE",BaseVO.OP_STRING);
    	this.addField("SALE_COUNT",BaseVO.OP_STRING);
		this.bindFieldToSequence("DTL_ID","COMMON");
    	this.setVOTableName("PT_BU_REAL_SALE_DTL");
}
	public void setUnit(String Unit){
		this.setInternal("UNIT" ,Unit );
	}


	public String getUnit(){
		return (String)this.getInternal("UNIT");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setPartCode(String PartCode){
		this.setInternal("PART_CODE" ,PartCode );
	}


	public String getPartCode(){
		return (String)this.getInternal("PART_CODE");
	}
	public void setSupplierId(String SupplierId){
		this.setInternal("SUPPLIER_ID" ,SupplierId );
	}


	public String getSupplierId(){
		return (String)this.getInternal("SUPPLIER_ID");
	}
	public void setSaleId(String SaleId){
		this.setInternal("SALE_ID" ,SaleId );
	}


	public String getSaleId(){
		return (String)this.getInternal("SALE_ID");
	}
	public void setPartId(String PartId){
		this.setInternal("PART_ID" ,PartId );
	}


	public String getPartId(){
		return (String)this.getInternal("PART_ID");
	}
	public void setSalePrice(String SalePrice){
		this.setInternal("SALE_PRICE" ,SalePrice );
	}


	public String getSalePrice(){
		return (String)this.getInternal("SALE_PRICE");
	}
	public void setDtlId(String DtlId){
		this.setInternal("DTL_ID" ,DtlId );
	}


	public String getDtlId(){
		return (String)this.getInternal("DTL_ID");
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
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setSupplierName(String SupplierName){
		this.setInternal("SUPPLIER_NAME" ,SupplierName );
	}


	public String getSupplierName(){
		return (String)this.getInternal("SUPPLIER_NAME");
	}
	public void setSupplierCode(String SupplierCode){
		this.setInternal("SUPPLIER_CODE" ,SupplierCode );
	}


	public String getSupplierCode(){
		return (String)this.getInternal("SUPPLIER_CODE");
	}
	public void setSaleCount(String SaleCount){
		this.setInternal("SALE_COUNT" ,SaleCount );
	}


	public String getSaleCount(){
		return (String)this.getInternal("SALE_COUNT");
	}
}
