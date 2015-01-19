package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class SeBuClaimOutVO extends BaseVO{
    public SeBuClaimOutVO(){
    	//�����ֶ���Ϣ
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("OUT_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("START_VEHICLE_LOCATION",BaseVO.OP_STRING);
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("END_VEHICLE_LATITUDE",BaseVO.OP_STRING);
    	this.addField("SEVEH_COSTS",BaseVO.OP_STRING);
    	this.addField("OUT_UCOUNT",BaseVO.OP_STRING);
    	this.addField("START_PHONE_LATITUDE",BaseVO.OP_STRING);
    	this.addField("OTHER_COSTS",BaseVO.OP_STRING);
    	this.addField("START_PHONE_LONGITUDE",BaseVO.OP_STRING);
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("COST_AMOUNT",BaseVO.OP_STRING);
    	this.addField("END_PHONE_LONGITUDE",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("START_VEHICLE_LONGITUDE",BaseVO.OP_STRING);
    	this.addField("VEHBOAT_COSTS",BaseVO.OP_STRING);
    	this.addField("OUT_TIMES",BaseVO.OP_STRING);
    	this.addField("MILEAGE",BaseVO.OP_STRING);
    	this.addField("ON_WAY_DAYS",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("END_VEHICLE_LOCATION",BaseVO.OP_STRING);
    	this.addField("ARRIVE_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("ARRIVE_DATE", "yyyy-MM-dd");
    	this.addField("MEALS_COSTS",BaseVO.OP_STRING);
    	this.addField("SEC_VEH_COSTS",BaseVO.OP_STRING);
    	this.addField("END_PHONE_LATITUDE",BaseVO.OP_STRING);
    	this.addField("OUT_USER",BaseVO.OP_STRING);
    	this.addField("OUT_TYPE",BaseVO.OP_STRING);
    	this.addField("GPS_MILEAGE",BaseVO.OP_STRING);
    	this.addField("START_PHONE_LOCATION",BaseVO.OP_STRING);
    	this.addField("TRAVEL_DAYS",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("VEHICLE_NO",BaseVO.OP_STRING);
    	this.addField("END_PHONE_LOCATION",BaseVO.OP_STRING);
    	this.addField("END_VEHICLE_LONGITUDE",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("LEAVE_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("LEAVE_DATE", "yyyy-MM-dd");
    	this.addField("START_VEHICLE_LATITUDE",BaseVO.OP_STRING);
    	this.addField("TRAVEL_COSTS",BaseVO.OP_STRING);
    	this.addField("IS_OUT_TIMES",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("VEHICLE_PHONE_MILEAGE",BaseVO.OP_STRING);
    	this.addField("CLAIM_ID",BaseVO.OP_STRING);
    	this.addField("OUTDATE_TYPE",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("GO_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("GO_DATE", "yyyy-MM-dd");
    	this.addField("OUT_COSTS",BaseVO.OP_STRING);
		this.bindFieldToSequence("OUT_ID","COMMON");
    	this.setVOTableName("SE_BU_CLAIM_OUT");
}
	public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
	}
	public void setOutId(String OutId){
		this.setInternal("OUT_ID" ,OutId );
	}


	public String getOutId(){
		return (String)this.getInternal("OUT_ID");
	}
	public void setStartVehicleLocation(String StartVehicleLocation){
		this.setInternal("START_VEHICLE_LOCATION" ,StartVehicleLocation );
	}


	public String getStartVehicleLocation(){
		return (String)this.getInternal("START_VEHICLE_LOCATION");
	}
	public void setRemarks(String Remarks){
		this.setInternal("REMARKS" ,Remarks );
	}


	public String getRemarks(){
		return (String)this.getInternal("REMARKS");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setEndVehicleLatitude(String EndVehicleLatitude){
		this.setInternal("END_VEHICLE_LATITUDE" ,EndVehicleLatitude );
	}


	public String getEndVehicleLatitude(){
		return (String)this.getInternal("END_VEHICLE_LATITUDE");
	}
	public void setSevehCosts(String SevehCosts){
		this.setInternal("SEVEH_COSTS" ,SevehCosts );
	}


	public String getSevehCosts(){
		return (String)this.getInternal("SEVEH_COSTS");
	}
	public void setOutUcount(String OutUcount){
		this.setInternal("OUT_UCOUNT" ,OutUcount );
	}


	public String getOutUcount(){
		return (String)this.getInternal("OUT_UCOUNT");
	}
	public void setStartPhoneLatitude(String StartPhoneLatitude){
		this.setInternal("START_PHONE_LATITUDE" ,StartPhoneLatitude );
	}


	public String getStartPhoneLatitude(){
		return (String)this.getInternal("START_PHONE_LATITUDE");
	}
	public void setOtherCosts(String OtherCosts){
		this.setInternal("OTHER_COSTS" ,OtherCosts );
	}


	public String getOtherCosts(){
		return (String)this.getInternal("OTHER_COSTS");
	}
	public void setStartPhoneLongitude(String StartPhoneLongitude){
		this.setInternal("START_PHONE_LONGITUDE" ,StartPhoneLongitude );
	}


	public String getStartPhoneLongitude(){
		return (String)this.getInternal("START_PHONE_LONGITUDE");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setCostAmount(String CostAmount){
		this.setInternal("COST_AMOUNT" ,CostAmount );
	}


	public String getCostAmount(){
		return (String)this.getInternal("COST_AMOUNT");
	}
	public void setEndPhoneLongitude(String EndPhoneLongitude){
		this.setInternal("END_PHONE_LONGITUDE" ,EndPhoneLongitude );
	}


	public String getEndPhoneLongitude(){
		return (String)this.getInternal("END_PHONE_LONGITUDE");
	}
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setStartVehicleLongitude(String StartVehicleLongitude){
		this.setInternal("START_VEHICLE_LONGITUDE" ,StartVehicleLongitude );
	}


	public String getStartVehicleLongitude(){
		return (String)this.getInternal("START_VEHICLE_LONGITUDE");
	}
	public void setVehboatCosts(String VehboatCosts){
		this.setInternal("VEHBOAT_COSTS" ,VehboatCosts );
	}


	public String getVehboatCosts(){
		return (String)this.getInternal("VEHBOAT_COSTS");
	}
	public void setOutTimes(String OutTimes){
		this.setInternal("OUT_TIMES" ,OutTimes );
	}


	public String getOutTimes(){
		return (String)this.getInternal("OUT_TIMES");
	}
	public void setMileage(String Mileage){
		this.setInternal("MILEAGE" ,Mileage );
	}


	public String getMileage(){
		return (String)this.getInternal("MILEAGE");
	}
	public void setOnWayDays(String OnWayDays){
		this.setInternal("ON_WAY_DAYS" ,OnWayDays );
	}


	public String getOnWayDays(){
		return (String)this.getInternal("ON_WAY_DAYS");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setEndVehicleLocation(String EndVehicleLocation){
		this.setInternal("END_VEHICLE_LOCATION" ,EndVehicleLocation );
	}


	public String getEndVehicleLocation(){
		return (String)this.getInternal("END_VEHICLE_LOCATION");
	}
	public void setArriveDate(Date ArriveDate){
		this.setInternal("ARRIVE_DATE" ,ArriveDate );
	}


	public Date getArriveDate(){
		return (Date)this.getInternal("ARRIVE_DATE");
	}
	public void setMealsCosts(String MealsCosts){
		this.setInternal("MEALS_COSTS" ,MealsCosts );
	}


	public String getMealsCosts(){
		return (String)this.getInternal("MEALS_COSTS");
	}
	public void setSecVehCosts(String SecVehCosts){
		this.setInternal("SEC_VEH_COSTS" ,SecVehCosts );
	}


	public String getSecVehCosts(){
		return (String)this.getInternal("SEC_VEH_COSTS");
	}
	public void setEndPhoneLatitude(String EndPhoneLatitude){
		this.setInternal("END_PHONE_LATITUDE" ,EndPhoneLatitude );
	}


	public String getEndPhoneLatitude(){
		return (String)this.getInternal("END_PHONE_LATITUDE");
	}
	public void setOutUser(String OutUser){
		this.setInternal("OUT_USER" ,OutUser );
	}


	public String getOutUser(){
		return (String)this.getInternal("OUT_USER");
	}
	public void setOutType(String OutType){
		this.setInternal("OUT_TYPE" ,OutType );
	}


	public String getOutType(){
		return (String)this.getInternal("OUT_TYPE");
	}
	public void setGpsMileage(String GpsMileage){
		this.setInternal("GPS_MILEAGE" ,GpsMileage );
	}


	public String getGpsMileage(){
		return (String)this.getInternal("GPS_MILEAGE");
	}
	public void setStartPhoneLocation(String StartPhoneLocation){
		this.setInternal("START_PHONE_LOCATION" ,StartPhoneLocation );
	}


	public String getStartPhoneLocation(){
		return (String)this.getInternal("START_PHONE_LOCATION");
	}
	public void setTravelDays(String TravelDays){
		this.setInternal("TRAVEL_DAYS" ,TravelDays );
	}


	public String getTravelDays(){
		return (String)this.getInternal("TRAVEL_DAYS");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
	public void setVehicleNo(String VehicleNo){
		this.setInternal("VEHICLE_NO" ,VehicleNo );
	}


	public String getVehicleNo(){
		return (String)this.getInternal("VEHICLE_NO");
	}
	public void setEndPhoneLocation(String EndPhoneLocation){
		this.setInternal("END_PHONE_LOCATION" ,EndPhoneLocation );
	}


	public String getEndPhoneLocation(){
		return (String)this.getInternal("END_PHONE_LOCATION");
	}
	public void setEndVehicleLongitude(String EndVehicleLongitude){
		this.setInternal("END_VEHICLE_LONGITUDE" ,EndVehicleLongitude );
	}


	public String getEndVehicleLongitude(){
		return (String)this.getInternal("END_VEHICLE_LONGITUDE");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setLeaveDate(Date LeaveDate){
		this.setInternal("LEAVE_DATE" ,LeaveDate );
	}


	public Date getLeaveDate(){
		return (Date)this.getInternal("LEAVE_DATE");
	}
	public void setStartVehicleLatitude(String StartVehicleLatitude){
		this.setInternal("START_VEHICLE_LATITUDE" ,StartVehicleLatitude );
	}


	public String getStartVehicleLatitude(){
		return (String)this.getInternal("START_VEHICLE_LATITUDE");
	}
	public void setTravelCosts(String TravelCosts){
		this.setInternal("TRAVEL_COSTS" ,TravelCosts );
	}


	public String getTravelCosts(){
		return (String)this.getInternal("TRAVEL_COSTS");
	}
	public void setIsOutTimes(String IsOutTimes){
		this.setInternal("IS_OUT_TIMES" ,IsOutTimes );
	}


	public String getIsOutTimes(){
		return (String)this.getInternal("IS_OUT_TIMES");
	}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setVehiclePhoneMileage(String VehiclePhoneMileage){
		this.setInternal("VEHICLE_PHONE_MILEAGE" ,VehiclePhoneMileage );
	}


	public String getVehiclePhoneMileage(){
		return (String)this.getInternal("VEHICLE_PHONE_MILEAGE");
	}
	public void setClaimId(String ClaimId){
		this.setInternal("CLAIM_ID" ,ClaimId );
	}


	public String getClaimId(){
		return (String)this.getInternal("CLAIM_ID");
	}
	public void setOutdateType(String OutdateType){
		this.setInternal("OUTDATE_TYPE" ,OutdateType );
	}


	public String getOutdateType(){
		return (String)this.getInternal("OUTDATE_TYPE");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setGoDate(Date GoDate){
		this.setInternal("GO_DATE" ,GoDate );
	}


	public Date getGoDate(){
		return (Date)this.getInternal("GO_DATE");
	}
	public void setOutCosts(String OutCosts){
		this.setInternal("OUT_COSTS" ,OutCosts );
	}


	public String getOutCosts(){
		return (String)this.getInternal("OUT_COSTS");
	}
}
