package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class SeBaTravelCostTmpVO extends BaseVO{
    public SeBaTravelCostTmpVO(){
    	//�����ֶ���Ϣ
    	this.addField("TRAVEL_DATE_NA",BaseVO.OP_STRING);
    	this.addField("END_MILES",BaseVO.OP_STRING);
    	this.addField("ORG_NAME",BaseVO.OP_STRING);
    	this.addField("TRAVEL_TIMES",BaseVO.OP_STRING);
    	this.addField("OFFICE_NA",BaseVO.OP_STRING);
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("START_MILES",BaseVO.OP_STRING);
    	this.addField("USER_ACCOUNT",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("VEHICLE_TYPE_NA",BaseVO.OP_STRING);
    	this.addField("TRAVEL_DATE",BaseVO.OP_STRING);
    	this.addField("TRAVEL_STATUS_NA",BaseVO.OP_STRING);
    	this.addField("STATUS_NA",BaseVO.OP_STRING);
    	this.addField("ROW_NUM",BaseVO.OP_STRING);
    	this.addField("COSTS_TYPE",BaseVO.OP_STRING);
    	this.addField("COST",BaseVO.OP_STRING);
    	this.addField("TRAVEL_TIMES_NA",BaseVO.OP_STRING);
    	this.addField("COSTS_TYPE_NA",BaseVO.OP_STRING);
    	this.addField("ORG_CODE",BaseVO.OP_STRING);
    	this.addField("VEHICLE_TYPE",BaseVO.OP_STRING);
    	this.addField("TRAVEL_STATUS",BaseVO.OP_STRING);
    	this.addField("TMP_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("OFFICE_ID",BaseVO.OP_STRING);
		this.bindFieldToSequence("TMP_ID","COMMON");
    	this.setVOTableName("SE_BA_TRAVEL_COST_TMP");
}
	public void setTravelDateNa(String TravelDateNa){
		this.setInternal("TRAVEL_DATE_NA" ,TravelDateNa );
	}


	public String getTravelDateNa(){
		return (String)this.getInternal("TRAVEL_DATE_NA");
	}
	public void setEndMiles(String EndMiles){
		this.setInternal("END_MILES" ,EndMiles );
	}


	public String getEndMiles(){
		return (String)this.getInternal("END_MILES");
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
	public void setOfficeNa(String OfficeNa){
		this.setInternal("OFFICE_NA" ,OfficeNa );
	}


	public String getOfficeNa(){
		return (String)this.getInternal("OFFICE_NA");
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
	public void setUserAccount(String UserAccount){
		this.setInternal("USER_ACCOUNT" ,UserAccount );
	}


	public String getUserAccount(){
		return (String)this.getInternal("USER_ACCOUNT");
	}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setVehicleTypeNa(String VehicleTypeNa){
		this.setInternal("VEHICLE_TYPE_NA" ,VehicleTypeNa );
	}


	public String getVehicleTypeNa(){
		return (String)this.getInternal("VEHICLE_TYPE_NA");
	}
	public void setTravelDate(String TravelDate){
		this.setInternal("TRAVEL_DATE" ,TravelDate );
	}


	public String getTravelDate(){
		return (String)this.getInternal("TRAVEL_DATE");
	}
	public void setTravelStatusNa(String TravelStatusNa){
		this.setInternal("TRAVEL_STATUS_NA" ,TravelStatusNa );
	}


	public String getTravelStatusNa(){
		return (String)this.getInternal("TRAVEL_STATUS_NA");
	}
	public void setStatusNa(String StatusNa){
		this.setInternal("STATUS_NA" ,StatusNa );
	}


	public String getStatusNa(){
		return (String)this.getInternal("STATUS_NA");
	}
	public void setRowNum(String RowNum){
		this.setInternal("ROW_NUM" ,RowNum );
	}


	public String getRowNum(){
		return (String)this.getInternal("ROW_NUM");
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
	public void setTravelTimesNa(String TravelTimesNa){
		this.setInternal("TRAVEL_TIMES_NA" ,TravelTimesNa );
	}


	public String getTravelTimesNa(){
		return (String)this.getInternal("TRAVEL_TIMES_NA");
	}
	public void setCostsTypeNa(String CostsTypeNa){
		this.setInternal("COSTS_TYPE_NA" ,CostsTypeNa );
	}


	public String getCostsTypeNa(){
		return (String)this.getInternal("COSTS_TYPE_NA");
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
	public void setTmpId(String TmpId){
		this.setInternal("TMP_ID" ,TmpId );
	}


	public String getTmpId(){
		return (String)this.getInternal("TMP_ID");
	}
	public void setOfficeId(String OfficeId){
		this.setInternal("OFFICE_ID" ,OfficeId );
	}


	public String getOfficeId(){
		return (String)this.getInternal("OFFICE_ID");
	}
}
