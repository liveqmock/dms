package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class SeBuReturnorderDtlTmpVO extends BaseVO{
    public SeBuReturnorderDtlTmpVO(){
    	//设置字段信息
    	this.addField("FAULT_CODE",BaseVO.OP_STRING);
    	this.addField("CLAIM_NO",BaseVO.OP_STRING);
    	this.addField("ORG_NAME",BaseVO.OP_STRING);
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("VIN",BaseVO.OP_STRING);
    	this.addField("PART_NAME",BaseVO.OP_STRING);
    	this.addField("ORDER_NO",BaseVO.OP_STRING);
    	this.addField("BROKEN_REASON",BaseVO.OP_STRING);
    	this.addField("PART_CODE",BaseVO.OP_STRING);
    	this.addField("PART_AMOUNT",BaseVO.OP_STRING);
    	this.addField("SHOULD_COUNT",BaseVO.OP_STRING);
    	this.addField("USER_ACCOUNT",BaseVO.OP_STRING);
    	this.addField("BOX_NO",BaseVO.OP_STRING);
    	this.addField("MODELS_CODE",BaseVO.OP_STRING);
    	this.addField("ROW_NUM",BaseVO.OP_STRING);
    	this.addField("MILEAGE",BaseVO.OP_STRING);
    	this.addField("OUGHT_COUNT",BaseVO.OP_STRING);
    	this.addField("PROSUPPLY_CODE",BaseVO.OP_STRING);
    	this.addField("DUTYSUPPLY_CODE",BaseVO.OP_STRING);
    	this.addField("ORG_CODE",BaseVO.OP_STRING);
    	this.addField("TMP_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
		this.bindFieldToSequence("TMP_ID","COMMON");
    	this.setVOTableName("SE_BU_RETURNORDER_DTL_TMP");
}
	public void setFaultCode(String FaultCode){
		this.setInternal("FAULT_CODE" ,FaultCode );
	}


	public String getFaultCode(){
		return (String)this.getInternal("FAULT_CODE");
	}
	public void setClaimNo(String ClaimNo){
		this.setInternal("CLAIM_NO" ,ClaimNo );
	}


	public String getClaimNo(){
		return (String)this.getInternal("CLAIM_NO");
	}
	public void setOrgName(String OrgName){
		this.setInternal("ORG_NAME" ,OrgName );
	}


	public String getOrgName(){
		return (String)this.getInternal("ORG_NAME");
	}
	public void setRemarks(String Remarks){
		this.setInternal("REMARKS" ,Remarks );
	}


	public String getRemarks(){
		return (String)this.getInternal("REMARKS");
	}
	public void setVin(String Vin){
		this.setInternal("VIN" ,Vin );
	}


	public String getVin(){
		return (String)this.getInternal("VIN");
	}
	public void setPartName(String PartName){
		this.setInternal("PART_NAME" ,PartName );
	}


	public String getPartName(){
		return (String)this.getInternal("PART_NAME");
	}
	public void setOrderNo(String OrderNo){
		this.setInternal("ORDER_NO" ,OrderNo );
	}


	public String getOrderNo(){
		return (String)this.getInternal("ORDER_NO");
	}
	public void setBrokenReason(String BrokenReason){
		this.setInternal("BROKEN_REASON" ,BrokenReason );
	}


	public String getBrokenReason(){
		return (String)this.getInternal("BROKEN_REASON");
	}
	public void setPartCode(String PartCode){
		this.setInternal("PART_CODE" ,PartCode );
	}


	public String getPartCode(){
		return (String)this.getInternal("PART_CODE");
	}
	public void setPartAmount(String PartAmount){
		this.setInternal("PART_AMOUNT" ,PartAmount );
	}


	public String getPartAmount(){
		return (String)this.getInternal("PART_AMOUNT");
	}
	public void setShouldCount(String ShouldCount){
		this.setInternal("SHOULD_COUNT" ,ShouldCount );
	}


	public String getShouldCount(){
		return (String)this.getInternal("SHOULD_COUNT");
	}
	public void setUserAccount(String UserAccount){
		this.setInternal("USER_ACCOUNT" ,UserAccount );
	}


	public String getUserAccount(){
		return (String)this.getInternal("USER_ACCOUNT");
	}
	public void setBoxNo(String BoxNo){
		this.setInternal("BOX_NO" ,BoxNo );
	}


	public String getBoxNo(){
		return (String)this.getInternal("BOX_NO");
	}
	public void setModelsCode(String ModelsCode){
		this.setInternal("MODELS_CODE" ,ModelsCode );
	}


	public String getModelsCode(){
		return (String)this.getInternal("MODELS_CODE");
	}
	public void setRowNum(String RowNum){
		this.setInternal("ROW_NUM" ,RowNum );
	}


	public String getRowNum(){
		return (String)this.getInternal("ROW_NUM");
	}
	public void setMileage(String Mileage){
		this.setInternal("MILEAGE" ,Mileage );
	}


	public String getMileage(){
		return (String)this.getInternal("MILEAGE");
	}
	public void setOughtCount(String OughtCount){
		this.setInternal("OUGHT_COUNT" ,OughtCount );
	}


	public String getOughtCount(){
		return (String)this.getInternal("OUGHT_COUNT");
	}
	public void setProsupplyCode(String ProsupplyCode){
		this.setInternal("PROSUPPLY_CODE" ,ProsupplyCode );
	}


	public String getProsupplyCode(){
		return (String)this.getInternal("PROSUPPLY_CODE");
	}
	public void setDutysupplyCode(String DutysupplyCode){
		this.setInternal("DUTYSUPPLY_CODE" ,DutysupplyCode );
	}


	public String getDutysupplyCode(){
		return (String)this.getInternal("DUTYSUPPLY_CODE");
	}
	public void setOrgCode(String OrgCode){
		this.setInternal("ORG_CODE" ,OrgCode );
	}


	public String getOrgCode(){
		return (String)this.getInternal("ORG_CODE");
	}
	public void setTmpId(String TmpId){
		this.setInternal("TMP_ID" ,TmpId );
	}


	public String getTmpId(){
		return (String)this.getInternal("TMP_ID");
	}
}

