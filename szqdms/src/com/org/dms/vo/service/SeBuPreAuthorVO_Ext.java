package com.org.dms.vo.service;

import java.util.Date;

import com.org.framework.base.BaseVO;

public class SeBuPreAuthorVO_Ext extends SeBuPreAuthorVO{
    public SeBuPreAuthorVO_Ext(){
    	this.addField("CERTIFICATE",BaseVO.OP_STRING);
    	this.addField("SALE_STATUS",BaseVO.OP_STRING);
    	this.addField("GUARANTEE_NO",BaseVO.OP_STRING);
    	this.addField("MAINTENANCE_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("MAINTENANCE_DATE", "yyyy-MM-dd");
    	this.addField("USER_TYPE",BaseVO.OP_STRING);
    	this.addField("BUY_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
    	this.addField("MODELS_ID",BaseVO.OP_STRING);
    	this.addField("MODELS_CODE",BaseVO.OP_STRING);
    	this.addField("VEHICLE_USE",BaseVO.OP_STRING);
    	this.addField("ENGINE_TYPE",BaseVO.OP_STRING);
    	this.addField("ENGINE_NO",BaseVO.OP_STRING);
    	this.addField("FACTORY_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("FACTORY_DATE", "yyyy-MM-dd");
    	this.addField("DRIVE_FORM",BaseVO.OP_STRING);
    }
    public void setCertificate(String Certificate){
		this.setInternal("CERTIFICATE" ,Certificate );
	}


	public String getCertificate(){
		return (String)this.getInternal("CERTIFICATE");
	}
	public void setSaleStatus(String SaleStatus){
		this.setInternal("SALE_STATUS" ,SaleStatus );
	}


	public String getSaleStatus(){
		return (String)this.getInternal("SALE_STATUS");
	}
	public void setGuaranteeNo(String GuaranteeNo){
		this.setInternal("GUARANTEE_NO" ,GuaranteeNo );
	}


	public String getGuaranteeNo(){
		return (String)this.getInternal("GUARANTEE_NO");
	}
	public void setMaintenanceDate(Date MaintenanceDate){
		this.setInternal("MAINTENANCE_DATE" ,MaintenanceDate );
	}


	public Date getMaintenanceDate(){
		return (Date)this.getInternal("MAINTENANCE_DATE");
	}
	public void setUserType(String UserType){
		this.setInternal("USER_TYPE" ,UserType );
	}


	public String getUserType(){
		return (String)this.getInternal("USER_TYPE");
	}
	public void setBuyDate(Date BuyDate){
		this.setInternal("BUY_DATE" ,BuyDate );
	}


	public Date getBuyDate(){
		return (Date)this.getInternal("BUY_DATE");
	}
	public void setModelsId(String ModelsId){
		this.setInternal("MODELS_ID" ,ModelsId );
	}


	public String getModelsId(){
		return (String)this.getInternal("MODELS_ID");
	}
	public void setModelsCode(String ModelsCode){
		this.setInternal("MODELS_CODE" ,ModelsCode );
	}


	public String getModelsCode(){
		return (String)this.getInternal("MODELS_CODE");
	}
	public void setVehicleUse(String VehicleUse){
		this.setInternal("VEHICLE_USE" ,VehicleUse );
	}


	public String getVehicleUse(){
		return (String)this.getInternal("VEHICLE_USE");
	}
	public void setEngineType(String EngineType){
		this.setInternal("ENGINE_TYPE" ,EngineType );
	}


	public String getEngineType(){
		return (String)this.getInternal("ENGINE_TYPE");
	}
	public void setEngineNo(String EngineNo){
		this.setInternal("ENGINE_NO" ,EngineNo );
	}


	public String getEngineNo(){
		return (String)this.getInternal("ENGINE_NO");
	}
	public void setFactoryDate(Date FactoryDate){
		this.setInternal("FACTORY_DATE" ,FactoryDate );
	}


	public Date getFactoryDate(){
		return (Date)this.getInternal("FACTORY_DATE");
	}
	public void setDriveForm(String DriveForm){
		this.setInternal("DRIVE_FORM" ,DriveForm );
	}


	public String getDriveForm(){
		return (String)this.getInternal("DRIVE_FORM");
	}
}