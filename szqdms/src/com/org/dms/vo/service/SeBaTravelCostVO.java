package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class SeBaTravelCostVO extends BaseVO{
    public SeBaTravelCostVO(){
    	//�����ֶ���Ϣ
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("END_MILES",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("ORG_NAME",BaseVO.OP_STRING);
    	this.addField("TRAVEL_TIMES",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("CODE_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("START_MILES",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("TRAVEL_DATE",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("COSTS_TYPE",BaseVO.OP_STRING);
    	this.addField("COST",BaseVO.OP_STRING);
    	this.addField("ORG_CODE",BaseVO.OP_STRING);
    	this.addField("VEHICLE_TYPE",BaseVO.OP_STRING);
    	this.addField("TRAVEL_STATUS",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("OFFICE_ID",BaseVO.OP_STRING);
		this.bindFieldToSequence("CODE_ID","COMMON");
    	this.setVOTableName("SE_BA_TRAVEL_COST");
}
	public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
	}
	public void setEndMiles(String EndMiles){
		this.setInternal("END_MILES" ,EndMiles );
	}


	public String getEndMiles(){
		return (String)this.getInternal("END_MILES");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setOrgName(String OrgName){
		this.setInternal("ORG_NAME" ,OrgName );
	}


	public String getOrgName(){
		return (String)this.getInternal("ORG_NAME");
	}
	public void setTravelTimes(String TravelTimes){
		this.setInternal("TRAVEL_TIMES" ,TravelTimes );
	}


	public String getTravelTimes(){
		return (String)this.getInternal("TRAVEL_TIMES");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
	public void setCodeId(String CodeId){
		this.setInternal("CODE_ID" ,CodeId );
	}


	public String getCodeId(){
		return (String)this.getInternal("CODE_ID");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setStartMiles(String StartMiles){
		this.setInternal("START_MILES" ,StartMiles );
	}


	public String getStartMiles(){
		return (String)this.getInternal("START_MILES");
	}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setTravelDate(String TravelDate){
		this.setInternal("TRAVEL_DATE" ,TravelDate );
	}


	public String getTravelDate(){
		return (String)this.getInternal("TRAVEL_DATE");
	}
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setCostsType(String CostsType){
		this.setInternal("COSTS_TYPE" ,CostsType );
	}


	public String getCostsType(){
		return (String)this.getInternal("COSTS_TYPE");
	}
	public void setCost(String Cost){
		this.setInternal("COST" ,Cost );
	}


	public String getCost(){
		return (String)this.getInternal("COST");
	}
	public void setOrgCode(String OrgCode){
		this.setInternal("ORG_CODE" ,OrgCode );
	}


	public String getOrgCode(){
		return (String)this.getInternal("ORG_CODE");
	}
	public void setVehicleType(String VehicleType){
		this.setInternal("VEHICLE_TYPE" ,VehicleType );
	}


	public String getVehicleType(){
		return (String)this.getInternal("VEHICLE_TYPE");
	}
	public void setTravelStatus(String TravelStatus){
		this.setInternal("TRAVEL_STATUS" ,TravelStatus );
	}


	public String getTravelStatus(){
		return (String)this.getInternal("TRAVEL_STATUS");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setOfficeId(String OfficeId){
		this.setInternal("OFFICE_ID" ,OfficeId );
	}


	public String getOfficeId(){
		return (String)this.getInternal("OFFICE_ID");
	}
}
