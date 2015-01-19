package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class SeBuOldpartStorageTmpVO extends BaseVO{
    public SeBuOldpartStorageTmpVO(){
    	//设置字段信息
    	this.addField("CLAIM_NO",BaseVO.OP_STRING);
    	this.addField("FAULT_CODE",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_NAME",BaseVO.OP_STRING);
    	this.addField("ROW_NO",BaseVO.OP_STRING);
    	this.addField("CLAIM_COUNT",BaseVO.OP_STRING);
    	this.addField("STORAGE_COUNT",BaseVO.OP_STRING);
    	this.addField("PART_NAME",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_CODE",BaseVO.OP_STRING);
    	this.addField("PART_CODE",BaseVO.OP_STRING);
    	this.addField("ALREADY_IN",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("OUGHT_COUNT",BaseVO.OP_STRING);
    	this.addField("MISS_COUNT",BaseVO.OP_STRING);
    	this.addField("TMP_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("RETURN_ORDER_NO",BaseVO.OP_STRING);
		this.bindFieldToSequence("TMP_ID","COMMON");
    	this.setVOTableName("SE_BU_OLDPART_STORAGE_TMP");
}
	public void setClaimNo(String ClaimNo){
		this.setInternal("CLAIM_NO" ,ClaimNo );
	}


	public String getClaimNo(){
		return (String)this.getInternal("CLAIM_NO");
	}
	public void setFaultCode(String FaultCode){
		this.setInternal("FAULT_CODE" ,FaultCode );
	}


	public String getFaultCode(){
		return (String)this.getInternal("FAULT_CODE");
	}
	public void setSupplierName(String SupplierName){
		this.setInternal("SUPPLIER_NAME" ,SupplierName );
	}


	public String getSupplierName(){
		return (String)this.getInternal("SUPPLIER_NAME");
	}
	public void setRowNo(String RowNo){
		this.setInternal("ROW_NO" ,RowNo );
	}


	public String getRowNo(){
		return (String)this.getInternal("ROW_NO");
	}
	public void setClaimCount(String ClaimCount){
		this.setInternal("CLAIM_COUNT" ,ClaimCount );
	}


	public String getClaimCount(){
		return (String)this.getInternal("CLAIM_COUNT");
	}
	public void setStorageCount(String StorageCount){
		this.setInternal("STORAGE_COUNT" ,StorageCount );
	}


	public String getStorageCount(){
		return (String)this.getInternal("STORAGE_COUNT");
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
	public void setPartCode(String PartCode){
		this.setInternal("PART_CODE" ,PartCode );
	}


	public String getPartCode(){
		return (String)this.getInternal("PART_CODE");
	}
	public void setAlreadyIn(String AlreadyIn){
		this.setInternal("ALREADY_IN" ,AlreadyIn );
	}


	public String getAlreadyIn(){
		return (String)this.getInternal("ALREADY_IN");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setOughtCount(String OughtCount){
		this.setInternal("OUGHT_COUNT" ,OughtCount );
	}


	public String getOughtCount(){
		return (String)this.getInternal("OUGHT_COUNT");
	}
	public void setMissCount(String MissCount){
		this.setInternal("MISS_COUNT" ,MissCount );
	}


	public String getMissCount(){
		return (String)this.getInternal("MISS_COUNT");
	}
	public void setTmpId(String TmpId){
		this.setInternal("TMP_ID" ,TmpId );
	}


	public String getTmpId(){
		return (String)this.getInternal("TMP_ID");
	}
	public void setReturnOrderNo(String ReturnOrderNo){
		this.setInternal("RETURN_ORDER_NO" ,ReturnOrderNo );
	}


	public String getReturnOrderNo(){
		return (String)this.getInternal("RETURN_ORDER_NO");
	}
}

