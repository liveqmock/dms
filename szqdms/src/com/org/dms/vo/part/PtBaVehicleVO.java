package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBaVehicleVO extends BaseVO{
    public PtBaVehicleVO(){
    	//设置字段信息
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("CARRIER_NAME",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("PHONE",BaseVO.OP_STRING);
    	this.addField("SEX",BaseVO.OP_STRING);
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("DRIVER_NAME",BaseVO.OP_STRING);
    	this.addField("FIXED_LINE",BaseVO.OP_STRING);
    	this.addField("EMAIL",BaseVO.OP_STRING);
    	this.addField("DRIVER_NO",BaseVO.OP_STRING);
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("VEHICLE_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("LICENSE_PLATE",BaseVO.OP_STRING);
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("CARRIER_ID",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("VEHICLE_TYPE",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("ADDRESS",BaseVO.OP_STRING);
    	this.addField("CARRIER_CODE",BaseVO.OP_STRING);
    	this.addField("VIN",BaseVO.OP_STRING);
    	this.addField("ENGINE_NO",BaseVO.OP_STRING);
		this.bindFieldToSequence("VEHICLE_ID","COMMON");
    	this.setVOTableName("PT_BA_VEHICLE");
}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setCarrierName(String CarrierName){
		this.setInternal("CARRIER_NAME" ,CarrierName );
	}


	public String getCarrierName(){
		return (String)this.getInternal("CARRIER_NAME");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setPhone(String Phone){
		this.setInternal("PHONE" ,Phone );
	}


	public String getPhone(){
		return (String)this.getInternal("PHONE");
	}
	public void setSex(String Sex){
		this.setInternal("SEX" ,Sex );
	}


	public String getSex(){
		return (String)this.getInternal("SEX");
	}
	public void setRemarks(String Remarks){
		this.setInternal("REMARKS" ,Remarks );
	}


	public String getRemarks(){
		return (String)this.getInternal("REMARKS");
	}
	public void setDriverName(String DriverName){
		this.setInternal("DRIVER_NAME" ,DriverName );
	}


	public String getDriverName(){
		return (String)this.getInternal("DRIVER_NAME");
	}
	public void setFixedLine(String FixedLine){
		this.setInternal("FIXED_LINE" ,FixedLine );
	}


	public String getFixedLine(){
		return (String)this.getInternal("FIXED_LINE");
	}
	public void setEmail(String Email){
		this.setInternal("EMAIL" ,Email );
	}


	public String getEmail(){
		return (String)this.getInternal("EMAIL");
	}
	public void setDriverNo(String DriverNo){
		this.setInternal("DRIVER_NO" ,DriverNo );
	}


	public String getDriverNo(){
		return (String)this.getInternal("DRIVER_NO");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setVehicleId(String VehicleId){
		this.setInternal("VEHICLE_ID" ,VehicleId );
	}


	public String getVehicleId(){
		return (String)this.getInternal("VEHICLE_ID");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setLicensePlate(String LicensePlate){
		this.setInternal("LICENSE_PLATE" ,LicensePlate );
	}


	public String getLicensePlate(){
		return (String)this.getInternal("LICENSE_PLATE");
	}
	public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
	public void setCarrierId(String CarrierId){
		this.setInternal("CARRIER_ID" ,CarrierId );
	}


	public String getCarrierId(){
		return (String)this.getInternal("CARRIER_ID");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setVehicleType(String VehicleType){
		this.setInternal("VEHICLE_TYPE" ,VehicleType );
	}


	public String getVehicleType(){
		return (String)this.getInternal("VEHICLE_TYPE");
	}
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setAddress(String Address){
		this.setInternal("ADDRESS" ,Address );
	}


	public String getAddress(){
		return (String)this.getInternal("ADDRESS");
	}
	public void setCarrierCode(String CarrierCode){
		this.setInternal("CARRIER_CODE" ,CarrierCode );
	}


	public String getCarrierCode(){
		return (String)this.getInternal("CARRIER_CODE");
	}
	public void setVin(String Vin){
		this.setInternal("VIN" ,Vin );
	}


	public String getVin(){
		return (String)this.getInternal("VIN");
	}
	public void setEngineNo(String EngineNo){
		this.setInternal("ENGINE_NO" ,EngineNo );
	}


	public String getEngineNo(){
		return (String)this.getInternal("ENGINE_NO");
	}
}
