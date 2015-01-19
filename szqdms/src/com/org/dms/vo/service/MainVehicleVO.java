package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class MainVehicleVO extends BaseVO{
    public MainVehicleVO(){
    	//设置字段信息
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("SALE_STATUS",BaseVO.OP_STRING);
    	this.addField("MAINTENANCE_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("MAINTENANCE_DATE", "yyyy-MM-dd");
    	this.addField("INSIDECODE",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("MILEAGE",BaseVO.OP_STRING);
    	this.addField("ENGINE_NO",BaseVO.OP_STRING);
    	this.addField("CERTIFICATEDATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("CERTIFICATEDATE", "yyyy-MM-dd");
    	this.addField("FACTORY_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("FACTORY_DATE", "yyyy-MM-dd");
    	this.addField("VEHICLE_STATUS",BaseVO.OP_STRING);
    	this.addField("CERTIFICATE",BaseVO.OP_STRING);
    	this.addField("CONFIGURE",BaseVO.OP_STRING);
    	this.addField("VEHICLE_SUPP",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("FMAINTAINFLAG",BaseVO.OP_STRING);
    	this.addField("DRUNKM",BaseVO.OP_STRING);
    	this.addField("LINK_MAN",BaseVO.OP_STRING);
    	this.addField("G_LIMIT_DELAY",BaseVO.OP_STRING);
    	this.addField("MODELS_ID",BaseVO.OP_STRING);
    	this.addField("SCHEDULEDTIMES_DEL",BaseVO.OP_STRING);
    	this.addField("REPAIR_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("REPAIR_DATE", "yyyy-MM-dd");
    	this.addField("OLDVIN",BaseVO.OP_STRING);
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("VIN",BaseVO.OP_STRING);
    	this.addField("PRODUCTLINECODE",BaseVO.OP_STRING);
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("LRUNKM",BaseVO.OP_STRING);
    	this.addField("G_LIMIT",BaseVO.OP_STRING);
    	this.addField("BUY_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
    	this.addField("PHONE",BaseVO.OP_STRING);
    	this.addField("G_END",BaseVO.OP_DATE);
		this.setFieldDateFormat("G_END", "yyyy-MM-dd");
    	this.addField("USER_ADDRESS",BaseVO.OP_STRING);
    	this.addField("MODELS_CODE",BaseVO.OP_STRING);
    	this.addField("USER_NO",BaseVO.OP_STRING);
    	this.addField("ENGINE_TYPE",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("VEHICLE_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("G_START",BaseVO.OP_DATE);
		this.setFieldDateFormat("G_START", "yyyy-MM-dd");
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("GUARANTEE_NO",BaseVO.OP_STRING);
    	this.addField("USER_TYPE",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("LICENSE_PLATE",BaseVO.OP_STRING);
    	this.addField("VEHICLE_USE",BaseVO.OP_STRING);
    	this.addField("USER_NAME",BaseVO.OP_STRING);
    	this.addField("SVIN",BaseVO.OP_STRING);
    	this.addField("POSTPONETIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("POSTPONETIME", "yyyy-MM-dd");
    	this.addField("TRAINTIMES",BaseVO.OP_STRING);
    	this.addField("CONTRACTAREANO",BaseVO.OP_STRING);
    	this.addField("DRIVE_FORM",BaseVO.OP_STRING);
    	this.addField("SAFECHECKTIMES",BaseVO.OP_STRING);
    	this.addField("STRATEGY_ID",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("BLACKLISTFLAG",BaseVO.OP_STRING);
    	this.addField("G_COUNT",BaseVO.OP_STRING);
		this.bindFieldToSequence("VEHICLE_ID","COMMON");
    	this.setVOTableName("MAIN_VEHICLE");
}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setSaleStatus(String SaleStatus){
		this.setInternal("SALE_STATUS" ,SaleStatus );
	}


	public String getSaleStatus(){
		return (String)this.getInternal("SALE_STATUS");
	}
	public void setMaintenanceDate(Date MaintenanceDate){
		this.setInternal("MAINTENANCE_DATE" ,MaintenanceDate );
	}


	public Date getMaintenanceDate(){
		return (Date)this.getInternal("MAINTENANCE_DATE");
	}
	public void setInsidecode(String Insidecode){
		this.setInternal("INSIDECODE" ,Insidecode );
	}


	public String getInsidecode(){
		return (String)this.getInternal("INSIDECODE");
	}
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setMileage(String Mileage){
		this.setInternal("MILEAGE" ,Mileage );
	}


	public String getMileage(){
		return (String)this.getInternal("MILEAGE");
	}
	public void setEngineNo(String EngineNo){
		this.setInternal("ENGINE_NO" ,EngineNo );
	}


	public String getEngineNo(){
		return (String)this.getInternal("ENGINE_NO");
	}
	public void setCertificatedate(Date Certificatedate){
		this.setInternal("CERTIFICATEDATE" ,Certificatedate );
	}


	public Date getCertificatedate(){
		return (Date)this.getInternal("CERTIFICATEDATE");
	}
	public void setFactoryDate(Date FactoryDate){
		this.setInternal("FACTORY_DATE" ,FactoryDate );
	}


	public Date getFactoryDate(){
		return (Date)this.getInternal("FACTORY_DATE");
	}
	public void setVehicleStatus(String VehicleStatus){
		this.setInternal("VEHICLE_STATUS" ,VehicleStatus );
	}


	public String getVehicleStatus(){
		return (String)this.getInternal("VEHICLE_STATUS");
	}
	public void setCertificate(String Certificate){
		this.setInternal("CERTIFICATE" ,Certificate );
	}


	public String getCertificate(){
		return (String)this.getInternal("CERTIFICATE");
	}
	public void setConfigure(String Configure){
		this.setInternal("CONFIGURE" ,Configure );
	}


	public String getConfigure(){
		return (String)this.getInternal("CONFIGURE");
	}
	public void setVehicleSupp(String VehicleSupp){
		this.setInternal("VEHICLE_SUPP" ,VehicleSupp );
	}


	public String getVehicleSupp(){
		return (String)this.getInternal("VEHICLE_SUPP");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setFmaintainflag(String Fmaintainflag){
		this.setInternal("FMAINTAINFLAG" ,Fmaintainflag );
	}


	public String getFmaintainflag(){
		return (String)this.getInternal("FMAINTAINFLAG");
	}
	public void setDrunkm(String Drunkm){
		this.setInternal("DRUNKM" ,Drunkm );
	}


	public String getDrunkm(){
		return (String)this.getInternal("DRUNKM");
	}
	public void setLinkMan(String LinkMan){
		this.setInternal("LINK_MAN" ,LinkMan );
	}


	public String getLinkMan(){
		return (String)this.getInternal("LINK_MAN");
	}
	public void setGLimitDelay(String GLimitDelay){
		this.setInternal("G_LIMIT_DELAY" ,GLimitDelay );
	}


	public String getGLimitDelay(){
		return (String)this.getInternal("G_LIMIT_DELAY");
	}
	public void setModelsId(String ModelsId){
		this.setInternal("MODELS_ID" ,ModelsId );
	}


	public String getModelsId(){
		return (String)this.getInternal("MODELS_ID");
	}
	public void setScheduledtimesDel(String ScheduledtimesDel){
		this.setInternal("SCHEDULEDTIMES_DEL" ,ScheduledtimesDel );
	}


	public String getScheduledtimesDel(){
		return (String)this.getInternal("SCHEDULEDTIMES_DEL");
	}
	public void setRepairDate(Date RepairDate){
		this.setInternal("REPAIR_DATE" ,RepairDate );
	}


	public Date getRepairDate(){
		return (Date)this.getInternal("REPAIR_DATE");
	}
	public void setOldvin(String Oldvin){
		this.setInternal("OLDVIN" ,Oldvin );
	}


	public String getOldvin(){
		return (String)this.getInternal("OLDVIN");
	}
	public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
	}
	public void setVin(String Vin){
		this.setInternal("VIN" ,Vin );
	}


	public String getVin(){
		return (String)this.getInternal("VIN");
	}
	public void setProductlinecode(String Productlinecode){
		this.setInternal("PRODUCTLINECODE" ,Productlinecode );
	}


	public String getProductlinecode(){
		return (String)this.getInternal("PRODUCTLINECODE");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setLrunkm(String Lrunkm){
		this.setInternal("LRUNKM" ,Lrunkm );
	}


	public String getLrunkm(){
		return (String)this.getInternal("LRUNKM");
	}
	public void setGLimit(String GLimit){
		this.setInternal("G_LIMIT" ,GLimit );
	}


	public String getGLimit(){
		return (String)this.getInternal("G_LIMIT");
	}
	public void setBuyDate(Date BuyDate){
		this.setInternal("BUY_DATE" ,BuyDate );
	}


	public Date getBuyDate(){
		return (Date)this.getInternal("BUY_DATE");
	}
	public void setPhone(String Phone){
		this.setInternal("PHONE" ,Phone );
	}


	public String getPhone(){
		return (String)this.getInternal("PHONE");
	}
	public void setGEnd(Date GEnd){
		this.setInternal("G_END" ,GEnd );
	}


	public Date getGEnd(){
		return (Date)this.getInternal("G_END");
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
	public void setEngineType(String EngineType){
		this.setInternal("ENGINE_TYPE" ,EngineType );
	}


	public String getEngineType(){
		return (String)this.getInternal("ENGINE_TYPE");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setVehicleId(String VehicleId){
		this.setInternal("VEHICLE_ID" ,VehicleId );
	}


	public String getVehicleId(){
		return (String)this.getInternal("VEHICLE_ID");
	}
	public void setGStart(Date GStart){
		this.setInternal("G_START" ,GStart );
	}


	public Date getGStart(){
		return (Date)this.getInternal("G_START");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
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
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
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
	public void setSvin(String Svin){
		this.setInternal("SVIN" ,Svin );
	}


	public String getSvin(){
		return (String)this.getInternal("SVIN");
	}
	public void setPostponetime(Date Postponetime){
		this.setInternal("POSTPONETIME" ,Postponetime );
	}


	public Date getPostponetime(){
		return (Date)this.getInternal("POSTPONETIME");
	}
	public void setTraintimes(String Traintimes){
		this.setInternal("TRAINTIMES" ,Traintimes );
	}


	public String getTraintimes(){
		return (String)this.getInternal("TRAINTIMES");
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
	public void setSafechecktimes(String Safechecktimes){
		this.setInternal("SAFECHECKTIMES" ,Safechecktimes );
	}


	public String getSafechecktimes(){
		return (String)this.getInternal("SAFECHECKTIMES");
	}
	public void setStrategyId(String StrategyId){
		this.setInternal("STRATEGY_ID" ,StrategyId );
	}


	public String getStrategyId(){
		return (String)this.getInternal("STRATEGY_ID");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setBlacklistflag(String Blacklistflag){
		this.setInternal("BLACKLISTFLAG" ,Blacklistflag );
	}


	public String getBlacklistflag(){
		return (String)this.getInternal("BLACKLISTFLAG");
	}
	public void setGCount(String GCount){
		this.setInternal("G_COUNT" ,GCount );
	}


	public String getGCount(){
		return (String)this.getInternal("G_COUNT");
	}
}
