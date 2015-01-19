package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class SeBuReturnorderDetailVO extends BaseVO{
    public SeBuReturnorderDetailVO(){
    	//设置字段信息
    	this.addField("IS_MAIN",BaseVO.OP_STRING);
    	this.addField("UPDATE_STATUS",BaseVO.OP_STRING);
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("VIN",BaseVO.OP_STRING);
    	this.addField("PART_NAME",BaseVO.OP_STRING);
    	this.addField("PROSUPPLY_ID",BaseVO.OP_STRING);
    	this.addField("UPDATE_REMARKS",BaseVO.OP_STRING);
    	this.addField("CHECK_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("CHECK_DATE", "yyyy-MM-dd");
    	this.addField("DETAIL_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("UPDATE_PART_PRICE",BaseVO.OP_STRING);
    	this.addField("PART_AMOUNT",BaseVO.OP_STRING);
    	this.addField("BOX_NO",BaseVO.OP_STRING);
    	this.addField("UPDATE_PART_ID",BaseVO.OP_STRING);
    	this.addField("MODELS_CODE",BaseVO.OP_STRING);
    	this.addField("STORAGE_STATUS",BaseVO.OP_STRING);
    	this.addField("OLD_PART_STATUS",BaseVO.OP_STRING);
    	this.addField("MILEAGE",BaseVO.OP_STRING);
    	this.addField("ALREADY_OUT",BaseVO.OP_STRING);
    	this.addField("MEASURES",BaseVO.OP_STRING);
    	this.addField("PART_ID",BaseVO.OP_STRING);
    	this.addField("OUGHT_COUNT",BaseVO.OP_STRING);
    	this.addField("PROSUPPLY_CODE",BaseVO.OP_STRING);
    	this.addField("DUTYSUPPLY_CODE",BaseVO.OP_STRING);
    	this.addField("UPDATE_TYPE",BaseVO.OP_STRING);
    	this.addField("VEHICLE_ID",BaseVO.OP_STRING);
    	this.addField("CLAIM_DTL_ID",BaseVO.OP_STRING);
    	this.addField("UPDATE_OPINION",BaseVO.OP_STRING);
    	this.addField("DUTYSUPPLY_ID",BaseVO.OP_STRING);
    	this.addField("CLAIM_NO",BaseVO.OP_STRING);
    	this.addField("PROSUPPLY_NAME",BaseVO.OP_STRING);
    	this.addField("UPDATE_PART_CODE",BaseVO.OP_STRING);
    	this.addField("ORDER_NO",BaseVO.OP_STRING);
    	this.addField("CHECK_USER",BaseVO.OP_STRING);
    	this.addField("BROKEN_REASON",BaseVO.OP_STRING);
    	this.addField("ACTUL_COUNT",BaseVO.OP_STRING);
    	this.addField("UPDATE_SUPPLY_ID",BaseVO.OP_STRING);
    	this.addField("PART_CODE",BaseVO.OP_STRING);
    	this.addField("DUTYSUPPLY_NAME",BaseVO.OP_STRING);
    	this.addField("UPDATE_SUPPLY_NAME",BaseVO.OP_STRING);
    	this.addField("ORDER_ID",BaseVO.OP_STRING);
    	this.addField("ALREADY_IN",BaseVO.OP_STRING);
    	this.addField("UPDATE_SUPPLY_CODE",BaseVO.OP_STRING);
    	this.addField("CLAIM_ID",BaseVO.OP_STRING);
    	this.addField("MISS_COUNT",BaseVO.OP_STRING);
    	this.addField("FAULT_TYPE",BaseVO.OP_STRING);
    	this.addField("UPDATE_PART_COUNT",BaseVO.OP_STRING);
    	this.addField("UPDATE_PART_NAME",BaseVO.OP_STRING);
		this.bindFieldToSequence("DETAIL_ID","COMMON");
    	this.setVOTableName("SE_BU_RETURNORDER_DETAIL");
}
	public void setIsMain(String IsMain){
		this.setInternal("IS_MAIN" ,IsMain );
	}


	public String getIsMain(){
		return (String)this.getInternal("IS_MAIN");
	}
	public void setUpdateStatus(String UpdateStatus){
		this.setInternal("UPDATE_STATUS" ,UpdateStatus );
	}


	public String getUpdateStatus(){
		return (String)this.getInternal("UPDATE_STATUS");
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
	public void setProsupplyId(String ProsupplyId){
		this.setInternal("PROSUPPLY_ID" ,ProsupplyId );
	}


	public String getProsupplyId(){
		return (String)this.getInternal("PROSUPPLY_ID");
	}
	public void setUpdateRemarks(String UpdateRemarks){
		this.setInternal("UPDATE_REMARKS" ,UpdateRemarks );
	}


	public String getUpdateRemarks(){
		return (String)this.getInternal("UPDATE_REMARKS");
	}
	public void setCheckDate(Date CheckDate){
		this.setInternal("CHECK_DATE" ,CheckDate );
	}


	public Date getCheckDate(){
		return (Date)this.getInternal("CHECK_DATE");
	}
	public void setDetailId(String DetailId){
		this.setInternal("DETAIL_ID" ,DetailId );
	}


	public String getDetailId(){
		return (String)this.getInternal("DETAIL_ID");
	}
	public void setUpdatePartPrice(String UpdatePartPrice){
		this.setInternal("UPDATE_PART_PRICE" ,UpdatePartPrice );
	}


	public String getUpdatePartPrice(){
		return (String)this.getInternal("UPDATE_PART_PRICE");
	}
	public void setPartAmount(String PartAmount){
		this.setInternal("PART_AMOUNT" ,PartAmount );
	}


	public String getPartAmount(){
		return (String)this.getInternal("PART_AMOUNT");
	}
	public void setBoxNo(String BoxNo){
		this.setInternal("BOX_NO" ,BoxNo );
	}


	public String getBoxNo(){
		return (String)this.getInternal("BOX_NO");
	}
	public void setUpdatePartId(String UpdatePartId){
		this.setInternal("UPDATE_PART_ID" ,UpdatePartId );
	}


	public String getUpdatePartId(){
		return (String)this.getInternal("UPDATE_PART_ID");
	}
	public void setModelsCode(String ModelsCode){
		this.setInternal("MODELS_CODE" ,ModelsCode );
	}


	public String getModelsCode(){
		return (String)this.getInternal("MODELS_CODE");
	}
	public void setStorageStatus(String StorageStatus){
		this.setInternal("STORAGE_STATUS" ,StorageStatus );
	}


	public String getStorageStatus(){
		return (String)this.getInternal("STORAGE_STATUS");
	}
	public void setOldPartStatus(String OldPartStatus){
		this.setInternal("OLD_PART_STATUS" ,OldPartStatus );
	}


	public String getOldPartStatus(){
		return (String)this.getInternal("OLD_PART_STATUS");
	}
	public void setMileage(String Mileage){
		this.setInternal("MILEAGE" ,Mileage );
	}


	public String getMileage(){
		return (String)this.getInternal("MILEAGE");
	}
	public void setAlreadyOut(String AlreadyOut){
		this.setInternal("ALREADY_OUT" ,AlreadyOut );
	}


	public String getAlreadyOut(){
		return (String)this.getInternal("ALREADY_OUT");
	}
	public void setMeasures(String Measures){
		this.setInternal("MEASURES" ,Measures );
	}


	public String getMeasures(){
		return (String)this.getInternal("MEASURES");
	}
	public void setPartId(String PartId){
		this.setInternal("PART_ID" ,PartId );
	}


	public String getPartId(){
		return (String)this.getInternal("PART_ID");
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
	public void setUpdateType(String UpdateType){
		this.setInternal("UPDATE_TYPE" ,UpdateType );
	}


	public String getUpdateType(){
		return (String)this.getInternal("UPDATE_TYPE");
	}
	public void setVehicleId(String VehicleId){
		this.setInternal("VEHICLE_ID" ,VehicleId );
	}


	public String getVehicleId(){
		return (String)this.getInternal("VEHICLE_ID");
	}
	public void setClaimDtlId(String ClaimDtlId){
		this.setInternal("CLAIM_DTL_ID" ,ClaimDtlId );
	}


	public String getClaimDtlId(){
		return (String)this.getInternal("CLAIM_DTL_ID");
	}
	public void setUpdateOpinion(String UpdateOpinion){
		this.setInternal("UPDATE_OPINION" ,UpdateOpinion );
	}


	public String getUpdateOpinion(){
		return (String)this.getInternal("UPDATE_OPINION");
	}
	public void setDutysupplyId(String DutysupplyId){
		this.setInternal("DUTYSUPPLY_ID" ,DutysupplyId );
	}


	public String getDutysupplyId(){
		return (String)this.getInternal("DUTYSUPPLY_ID");
	}
	public void setClaimNo(String ClaimNo){
		this.setInternal("CLAIM_NO" ,ClaimNo );
	}


	public String getClaimNo(){
		return (String)this.getInternal("CLAIM_NO");
	}
	public void setProsupplyName(String ProsupplyName){
		this.setInternal("PROSUPPLY_NAME" ,ProsupplyName );
	}


	public String getProsupplyName(){
		return (String)this.getInternal("PROSUPPLY_NAME");
	}
	public void setUpdatePartCode(String UpdatePartCode){
		this.setInternal("UPDATE_PART_CODE" ,UpdatePartCode );
	}


	public String getUpdatePartCode(){
		return (String)this.getInternal("UPDATE_PART_CODE");
	}
	public void setOrderNo(String OrderNo){
		this.setInternal("ORDER_NO" ,OrderNo );
	}


	public String getOrderNo(){
		return (String)this.getInternal("ORDER_NO");
	}
	public void setCheckUser(String CheckUser){
		this.setInternal("CHECK_USER" ,CheckUser );
	}


	public String getCheckUser(){
		return (String)this.getInternal("CHECK_USER");
	}
	public void setBrokenReason(String BrokenReason){
		this.setInternal("BROKEN_REASON" ,BrokenReason );
	}


	public String getBrokenReason(){
		return (String)this.getInternal("BROKEN_REASON");
	}
	public void setActulCount(String ActulCount){
		this.setInternal("ACTUL_COUNT" ,ActulCount );
	}


	public String getActulCount(){
		return (String)this.getInternal("ACTUL_COUNT");
	}
	public void setUpdateSupplyId(String UpdateSupplyId){
		this.setInternal("UPDATE_SUPPLY_ID" ,UpdateSupplyId );
	}


	public String getUpdateSupplyId(){
		return (String)this.getInternal("UPDATE_SUPPLY_ID");
	}
	public void setPartCode(String PartCode){
		this.setInternal("PART_CODE" ,PartCode );
	}


	public String getPartCode(){
		return (String)this.getInternal("PART_CODE");
	}
	public void setDutysupplyName(String DutysupplyName){
		this.setInternal("DUTYSUPPLY_NAME" ,DutysupplyName );
	}


	public String getDutysupplyName(){
		return (String)this.getInternal("DUTYSUPPLY_NAME");
	}
	public void setUpdateSupplyName(String UpdateSupplyName){
		this.setInternal("UPDATE_SUPPLY_NAME" ,UpdateSupplyName );
	}


	public String getUpdateSupplyName(){
		return (String)this.getInternal("UPDATE_SUPPLY_NAME");
	}
	public void setOrderId(String OrderId){
		this.setInternal("ORDER_ID" ,OrderId );
	}


	public String getOrderId(){
		return (String)this.getInternal("ORDER_ID");
	}
	public void setAlreadyIn(String AlreadyIn){
		this.setInternal("ALREADY_IN" ,AlreadyIn );
	}


	public String getAlreadyIn(){
		return (String)this.getInternal("ALREADY_IN");
	}
	public void setUpdateSupplyCode(String UpdateSupplyCode){
		this.setInternal("UPDATE_SUPPLY_CODE" ,UpdateSupplyCode );
	}


	public String getUpdateSupplyCode(){
		return (String)this.getInternal("UPDATE_SUPPLY_CODE");
	}
	public void setClaimId(String ClaimId){
		this.setInternal("CLAIM_ID" ,ClaimId );
	}


	public String getClaimId(){
		return (String)this.getInternal("CLAIM_ID");
	}
	public void setMissCount(String MissCount){
		this.setInternal("MISS_COUNT" ,MissCount );
	}


	public String getMissCount(){
		return (String)this.getInternal("MISS_COUNT");
	}
	public void setFaultType(String FaultType){
		this.setInternal("FAULT_TYPE" ,FaultType );
	}


	public String getFaultType(){
		return (String)this.getInternal("FAULT_TYPE");
	}
	public void setUpdatePartCount(String UpdatePartCount){
		this.setInternal("UPDATE_PART_COUNT" ,UpdatePartCount );
	}


	public String getUpdatePartCount(){
		return (String)this.getInternal("UPDATE_PART_COUNT");
	}
	public void setUpdatePartName(String UpdatePartName){
		this.setInternal("UPDATE_PART_NAME" ,UpdatePartName );
	}


	public String getUpdatePartName(){
		return (String)this.getInternal("UPDATE_PART_NAME");
	}
}


