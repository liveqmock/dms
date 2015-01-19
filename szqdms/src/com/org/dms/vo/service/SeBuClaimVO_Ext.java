package com.org.dms.vo.service;

import java.util.Date;

import com.org.framework.base.BaseVO;

public class SeBuClaimVO_Ext extends SeBuClaimVO{
    public SeBuClaimVO_Ext(){
    	this.addField("USER_TYPE_NAME",BaseVO.OP_STRING);
    	this.addField("BUY_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
    	this.addField("MODELS_ID",BaseVO.OP_STRING);
    	this.addField("MODEL_CODE",BaseVO.OP_STRING);
    	this.addField("VEHICLE_NAME",BaseVO.OP_STRING);
    	this.addField("ENGINE_TYPE",BaseVO.OP_STRING);
    	this.addField("DRIVE_FORM",BaseVO.OP_STRING);
    	this.addField("GUARANTEE_NO",BaseVO.OP_STRING);
    	
    	this.addField("AUTHOR_NO",BaseVO.OP_STRING);
    	this.addField("ACTIVITY_CODE",BaseVO.OP_STRING);
    	this.addField("DISPATCH_NO",BaseVO.OP_STRING);
    }
    public void setAuthorNo(String AuthorNo){
		this.setInternal("AUTHOR_NO" ,AuthorNo );
	}
	public String getAuthorNo(){
		return (String)this.getInternal("AUTHOR_NO");
	}
    
	public void setActivityCode(String ActivityCode){
		this.setInternal("ACTIVITY_CODE" ,ActivityCode );
	}
	public String getActivityCode(){
		return (String)this.getInternal("ACTIVITY_CODE");
	}
	public void setDispatchNo(String DispatchNo){
		this.setInternal("DISPATCH_NO" ,DispatchNo );
	}
	public String getDispatchNo(){
		return (String)this.getInternal("DISPATCH_NO");
	}
    
    public void setGuaranteeNo(String GuaranteeNo){
		this.setInternal("GUARANTEE_NO" ,GuaranteeNo );
	}
	public String getGuaranteeNo(){
		return (String)this.getInternal("GUARANTEE_NO");
	}
    
	public void setDriveForm(String DriveForm){
		this.setInternal("DRIVE_FORM" ,DriveForm );
	}
	public String getDriveForm(){
		return (String)this.getInternal("DRIVE_FORM");
	}
    
	public void setUserTypeName(String UserTypeName){
		this.setInternal("USER_TYPE_NAME" ,UserTypeName);
	}
	public String getUserTypeName(){
		return (String)this.getInternal("USER_TYPE_NAME");
	}
	public void setBuyDate(Date BuyDate){
		this.setInternal("BUY_DATE" ,BuyDate );
	}


	public Date getBuyDate(){
		return (Date)this.getInternal("BUY_DATE");
	}
	public void setModelCode(String ModelCode){
		this.setInternal("MODEL_CODE" ,ModelCode );
	}


	public String getModelCode(){
		return (String)this.getInternal("MODEL_CODE");
	}
	public void setVehicleName(String VehicleName){
		this.setInternal("VEHICLE_NAME" ,VehicleName );
	}


	public String getVehicleName(){
		return (String)this.getInternal("VEHICLE_NAME");
	}
	public void setEngineType(String EngineType){
		this.setInternal("ENGINE_TYPE" ,EngineType );
	}


	public String getEngineType(){
		return (String)this.getInternal("ENGINE_TYPE");
	}

}
