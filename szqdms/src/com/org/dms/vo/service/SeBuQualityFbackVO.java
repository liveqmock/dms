package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class SeBuQualityFbackVO extends BaseVO{
    public SeBuQualityFbackVO(){
    	//�����ֶ���Ϣ
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("CUS_LINK_MAN",BaseVO.OP_STRING);
    	this.addField("FBACK_STATUS",BaseVO.OP_STRING);
    	this.addField("FAULT_MIELES",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("ENGINE_BOOK_NO",BaseVO.OP_STRING);
    	this.addField("VIN",BaseVO.OP_STRING);
    	this.addField("DRI_STATUS",BaseVO.OP_STRING);
    	this.addField("CUS_FAX",BaseVO.OP_STRING);
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("BUY_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
    	this.addField("DAILY_ROAD",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("MAINTENANCE_STATUS",BaseVO.OP_STRING);
    	this.addField("MODELS_CODE",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("ENGINE_TYPE",BaseVO.OP_STRING);
    	this.addField("TEL",BaseVO.OP_STRING);
    	this.addField("WRITE_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("WRITE_DATE", "yyyy-MM-dd");
    	this.addField("CUS_COM_NAME",BaseVO.OP_STRING);
    	this.addField("ENGINE_NO",BaseVO.OP_STRING);
    	this.addField("CUS_BUY_COUNT",BaseVO.OP_STRING);
    	this.addField("DRI_TEL",BaseVO.OP_STRING);
    	this.addField("FAULT_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("FAULT_DATE", "yyyy-MM-dd");
    	this.addField("VEHICLE_STATUS",BaseVO.OP_STRING);
    	this.addField("VEHICLE_ID",BaseVO.OP_STRING);
    	this.addField("FAX",BaseVO.OP_STRING);
    	this.addField("DRI_NAME",BaseVO.OP_STRING);
    	this.addField("FBACK_APPROACE",BaseVO.OP_STRING);
    	this.addField("CUS_TEL",BaseVO.OP_STRING);
    	this.addField("NAME",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("FBACK_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("AMOUNT",BaseVO.OP_STRING);
    	this.addField("DAILY_WORK",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("FAULT_ADDRESS",BaseVO.OP_STRING);
    	this.addField("VEHICLE_USE_TYPE",BaseVO.OP_STRING);
    	this.addField("COM_NAME",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
		this.bindFieldToSequence("FBACK_ID","COMMON");
    	this.setVOTableName("SE_BU_QUALITY_FBACK");
}
	public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
	}
	public void setCusLinkMan(String CusLinkMan){
		this.setInternal("CUS_LINK_MAN" ,CusLinkMan );
	}


	public String getCusLinkMan(){
		return (String)this.getInternal("CUS_LINK_MAN");
	}
	public void setFbackStatus(String FbackStatus){
		this.setInternal("FBACK_STATUS" ,FbackStatus );
	}


	public String getFbackStatus(){
		return (String)this.getInternal("FBACK_STATUS");
	}
	public void setFaultMieles(String FaultMieles){
		this.setInternal("FAULT_MIELES" ,FaultMieles );
	}


	public String getFaultMieles(){
		return (String)this.getInternal("FAULT_MIELES");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setRemarks(String Remarks){
		this.setInternal("REMARKS" ,Remarks );
	}


	public String getRemarks(){
		return (String)this.getInternal("REMARKS");
	}
	public void setEngineBookNo(String EngineBookNo){
		this.setInternal("ENGINE_BOOK_NO" ,EngineBookNo );
	}


	public String getEngineBookNo(){
		return (String)this.getInternal("ENGINE_BOOK_NO");
	}
	public void setVin(String Vin){
		this.setInternal("VIN" ,Vin );
	}


	public String getVin(){
		return (String)this.getInternal("VIN");
	}
	public void setDriStatus(String DriStatus){
		this.setInternal("DRI_STATUS" ,DriStatus );
	}


	public String getDriStatus(){
		return (String)this.getInternal("DRI_STATUS");
	}
	public void setCusFax(String CusFax){
		this.setInternal("CUS_FAX" ,CusFax );
	}


	public String getCusFax(){
		return (String)this.getInternal("CUS_FAX");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setBuyDate(Date BuyDate){
		this.setInternal("BUY_DATE" ,BuyDate );
	}


	public Date getBuyDate(){
		return (Date)this.getInternal("BUY_DATE");
	}
	public void setDailyRoad(String DailyRoad){
		this.setInternal("DAILY_ROAD" ,DailyRoad );
	}


	public String getDailyRoad(){
		return (String)this.getInternal("DAILY_ROAD");
	}
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setMaintenanceStatus(String MaintenanceStatus){
		this.setInternal("MAINTENANCE_STATUS" ,MaintenanceStatus );
	}


	public String getMaintenanceStatus(){
		return (String)this.getInternal("MAINTENANCE_STATUS");
	}
	public void setModelsCode(String ModelsCode){
		this.setInternal("MODELS_CODE" ,ModelsCode );
	}


	public String getModelsCode(){
		return (String)this.getInternal("MODELS_CODE");
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
	public void setTel(String Tel){
		this.setInternal("TEL" ,Tel );
	}


	public String getTel(){
		return (String)this.getInternal("TEL");
	}
	public void setWriteDate(Date WriteDate){
		this.setInternal("WRITE_DATE" ,WriteDate );
	}


	public Date getWriteDate(){
		return (Date)this.getInternal("WRITE_DATE");
	}
	public void setCusComName(String CusComName){
		this.setInternal("CUS_COM_NAME" ,CusComName );
	}


	public String getCusComName(){
		return (String)this.getInternal("CUS_COM_NAME");
	}
	public void setEngineNo(String EngineNo){
		this.setInternal("ENGINE_NO" ,EngineNo );
	}


	public String getEngineNo(){
		return (String)this.getInternal("ENGINE_NO");
	}
	public void setCusBuyCount(String CusBuyCount){
		this.setInternal("CUS_BUY_COUNT" ,CusBuyCount );
	}


	public String getCusBuyCount(){
		return (String)this.getInternal("CUS_BUY_COUNT");
	}
	public void setDriTel(String DriTel){
		this.setInternal("DRI_TEL" ,DriTel );
	}


	public String getDriTel(){
		return (String)this.getInternal("DRI_TEL");
	}
	public void setFaultDate(Date FaultDate){
		this.setInternal("FAULT_DATE" ,FaultDate );
	}


	public Date getFaultDate(){
		return (Date)this.getInternal("FAULT_DATE");
	}
	public void setVehicleStatus(String VehicleStatus){
		this.setInternal("VEHICLE_STATUS" ,VehicleStatus );
	}


	public String getVehicleStatus(){
		return (String)this.getInternal("VEHICLE_STATUS");
	}
	public void setVehicleId(String VehicleId){
		this.setInternal("VEHICLE_ID" ,VehicleId );
	}


	public String getVehicleId(){
		return (String)this.getInternal("VEHICLE_ID");
	}
	public void setFax(String Fax){
		this.setInternal("FAX" ,Fax );
	}


	public String getFax(){
		return (String)this.getInternal("FAX");
	}
	public void setDriName(String DriName){
		this.setInternal("DRI_NAME" ,DriName );
	}


	public String getDriName(){
		return (String)this.getInternal("DRI_NAME");
	}
	public void setFbackApproace(String FbackApproace){
		this.setInternal("FBACK_APPROACE" ,FbackApproace );
	}


	public String getFbackApproace(){
		return (String)this.getInternal("FBACK_APPROACE");
	}
	public void setCusTel(String CusTel){
		this.setInternal("CUS_TEL" ,CusTel );
	}


	public String getCusTel(){
		return (String)this.getInternal("CUS_TEL");
	}
	public void setName(String Name){
		this.setInternal("NAME" ,Name );
	}


	public String getName(){
		return (String)this.getInternal("NAME");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
	public void setFbackId(String FbackId){
		this.setInternal("FBACK_ID" ,FbackId );
	}


	public String getFbackId(){
		return (String)this.getInternal("FBACK_ID");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setAmount(String Amount){
		this.setInternal("AMOUNT" ,Amount );
	}


	public String getAmount(){
		return (String)this.getInternal("AMOUNT");
	}
	public void setDailyWork(String DailyWork){
		this.setInternal("DAILY_WORK" ,DailyWork );
	}


	public String getDailyWork(){
		return (String)this.getInternal("DAILY_WORK");
	}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setFaultAddress(String FaultAddress){
		this.setInternal("FAULT_ADDRESS" ,FaultAddress );
	}


	public String getFaultAddress(){
		return (String)this.getInternal("FAULT_ADDRESS");
	}
	public void setVehicleUseType(String VehicleUseType){
		this.setInternal("VEHICLE_USE_TYPE" ,VehicleUseType );
	}


	public String getVehicleUseType(){
		return (String)this.getInternal("VEHICLE_USE_TYPE");
	}
	public void setComName(String ComName){
		this.setInternal("COM_NAME" ,ComName );
	}


	public String getComName(){
		return (String)this.getInternal("COM_NAME");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
}
