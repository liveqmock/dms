package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class MainVehicleTmpVO extends BaseVO{
    public MainVehicleTmpVO(){
    	//�����ֶ���Ϣ
    	this.addField("VIN",BaseVO.OP_STRING);
    	this.addField("SALE_STATUS",BaseVO.OP_STRING);
    	this.addField("PRODUCTLINECODE",BaseVO.OP_STRING);
    	this.addField("MAINTENANCE_DATE",BaseVO.OP_STRING);
    	this.addField("BUY_DATE",BaseVO.OP_STRING);
    	this.addField("PHONE",BaseVO.OP_STRING);
    	this.addField("INSIDECODE",BaseVO.OP_STRING);
    	this.addField("USER_ADDRESS",BaseVO.OP_STRING);
    	this.addField("MODELS_CODE",BaseVO.OP_STRING);
    	this.addField("USER_NO",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("ENGINE_TYPE",BaseVO.OP_STRING);
    	this.addField("ENGINE_NO",BaseVO.OP_STRING);
    	this.addField("CERTIFICATEDATE",BaseVO.OP_STRING);
    	this.addField("FACTORY_DATE",BaseVO.OP_STRING);
    	this.addField("CERTIFICATE",BaseVO.OP_STRING);
    	this.addField("ROW_NO",BaseVO.OP_STRING);
    	this.addField("CONFIGURE",BaseVO.OP_STRING);
    	this.addField("GUARANTEE_NO",BaseVO.OP_STRING);
    	this.addField("USER_TYPE",BaseVO.OP_STRING);
    	this.addField("LINK_MAN",BaseVO.OP_STRING);
    	this.addField("LICENSE_PLATE",BaseVO.OP_STRING);
    	this.addField("VEHICLE_USE",BaseVO.OP_STRING);
    	this.addField("USER_NAME",BaseVO.OP_STRING);
    	this.addField("CONTRACTAREANO",BaseVO.OP_STRING);
    	this.addField("DRIVE_FORM",BaseVO.OP_STRING);
    	this.addField("TMP_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("BLACKLISTFLAG",BaseVO.OP_STRING);
		this.bindFieldToSequence("TMP_ID","COMMON");
    	this.setVOTableName("MAIN_VEHICLE_TMP");
}
	public void setVin(String Vin){
		this.setInternal("VIN" ,Vin );
	}


	public String getVin(){
		return (String)this.getInternal("VIN");
	}
	public void setSaleStatus(String SaleStatus){
		this.setInternal("SALE_STATUS" ,SaleStatus );
	}


	public String getSaleStatus(){
		return (String)this.getInternal("SALE_STATUS");
	}
	public void setProductlinecode(String Productlinecode){
		this.setInternal("PRODUCTLINECODE" ,Productlinecode );
	}


	public String getProductlinecode(){
		return (String)this.getInternal("PRODUCTLINECODE");
	}
	public void setMaintenanceDate(String MaintenanceDate){
		this.setInternal("MAINTENANCE_DATE" ,MaintenanceDate );
	}


	public String getMaintenanceDate(){
		return (String)this.getInternal("MAINTENANCE_DATE");
	}
	public void setBuyDate(String BuyDate){
		this.setInternal("BUY_DATE" ,BuyDate );
	}


	public String getBuyDate(){
		return (String)this.getInternal("BUY_DATE");
	}
	public void setPhone(String Phone){
		this.setInternal("PHONE" ,Phone );
	}


	public String getPhone(){
		return (String)this.getInternal("PHONE");
	}
	public void setInsidecode(String Insidecode){
		this.setInternal("INSIDECODE" ,Insidecode );
	}


	public String getInsidecode(){
		return (String)this.getInternal("INSIDECODE");
	}
	public void setUserAddress(String UserAddress){
		this.setInternal("USER_ADDRESS" ,UserAddress );
	}


	public String getUserAddress(){
		return (String)this.getInternal("USER_ADDRESS");
	}
	public void setModelsCode(String ModelsCode){
		this.setInternal("MODELS_CODE" ,ModelsCode );
	}


	public String getModelsCode(){
		return (String)this.getInternal("MODELS_CODE");
	}
	public void setUserNo(String UserNo){
		this.setInternal("USER_NO" ,UserNo );
	}


	public String getUserNo(){
		return (String)this.getInternal("USER_NO");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
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
	public void setCertificatedate(String Certificatedate){
		this.setInternal("CERTIFICATEDATE" ,Certificatedate );
	}


	public String getCertificatedate(){
		return (String)this.getInternal("CERTIFICATEDATE");
	}
	public void setFactoryDate(String FactoryDate){
		this.setInternal("FACTORY_DATE" ,FactoryDate );
	}


	public String getFactoryDate(){
		return (String)this.getInternal("FACTORY_DATE");
	}
	public void setCertificate(String Certificate){
		this.setInternal("CERTIFICATE" ,Certificate );
	}


	public String getCertificate(){
		return (String)this.getInternal("CERTIFICATE");
	}
	public void setRowNo(String RowNo){
		this.setInternal("ROW_NO" ,RowNo );
	}


	public String getRowNo(){
		return (String)this.getInternal("ROW_NO");
	}
	public void setConfigure(String Configure){
		this.setInternal("CONFIGURE" ,Configure );
	}


	public String getConfigure(){
		return (String)this.getInternal("CONFIGURE");
	}
	public void setGuaranteeNo(String GuaranteeNo){
		this.setInternal("GUARANTEE_NO" ,GuaranteeNo );
	}


	public String getGuaranteeNo(){
		return (String)this.getInternal("GUARANTEE_NO");
	}
	public void setUserType(String UserType){
		this.setInternal("USER_TYPE" ,UserType );
	}


	public String getUserType(){
		return (String)this.getInternal("USER_TYPE");
	}
	public void setLinkMan(String LinkMan){
		this.setInternal("LINK_MAN" ,LinkMan );
	}


	public String getLinkMan(){
		return (String)this.getInternal("LINK_MAN");
	}
	public void setLicensePlate(String LicensePlate){
		this.setInternal("LICENSE_PLATE" ,LicensePlate );
	}


	public String getLicensePlate(){
		return (String)this.getInternal("LICENSE_PLATE");
	}
	public void setVehicleUse(String VehicleUse){
		this.setInternal("VEHICLE_USE" ,VehicleUse );
	}


	public String getVehicleUse(){
		return (String)this.getInternal("VEHICLE_USE");
	}
	public void setUserName(String UserName){
		this.setInternal("USER_NAME" ,UserName );
	}


	public String getUserName(){
		return (String)this.getInternal("USER_NAME");
	}
	public void setContractareano(String Contractareano){
		this.setInternal("CONTRACTAREANO" ,Contractareano );
	}


	public String getContractareano(){
		return (String)this.getInternal("CONTRACTAREANO");
	}
	public void setDriveForm(String DriveForm){
		this.setInternal("DRIVE_FORM" ,DriveForm );
	}


	public String getDriveForm(){
		return (String)this.getInternal("DRIVE_FORM");
	}
	public void setTmpId(String TmpId){
		this.setInternal("TMP_ID" ,TmpId );
	}


	public String getTmpId(){
		return (String)this.getInternal("TMP_ID");
	}
	public void setBlacklistflag(String Blacklistflag){
		this.setInternal("BLACKLISTFLAG" ,Blacklistflag );
	}


	public String getBlacklistflag(){
		return (String)this.getInternal("BLACKLISTFLAG");
	}
}
