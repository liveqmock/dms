package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class SeBuClaimVO extends BaseVO{
    public SeBuClaimVO(){
    	//�����ֶ���Ϣ
    	this.addField("CLAIM_TYPE",BaseVO.OP_STRING);
    	this.addField("IF_STORAGE",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("WORK_ID",BaseVO.OP_STRING);
    	this.addField("APPLY_MOBIL",BaseVO.OP_STRING);
    	this.addField("RECHECK_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("RECHECK_DATE", "yyyy-MM-dd");
    	this.addField("MAINTENANCE_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("MAINTENANCE_DATE", "yyyy-MM-dd");
    	this.addField("OUT_BUYREJECT_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("OUT_BUYREJECT_DATE", "yyyy-MM-dd");
    	this.addField("DISPATCH_ID",BaseVO.OP_STRING);
    	this.addField("ADJUST_USER",BaseVO.OP_STRING);
    	this.addField("MILEAGE",BaseVO.OP_STRING);
    	this.addField("USERDESCRIPTION",BaseVO.OP_STRING);
    	this.addField("FACTORY_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("FACTORY_DATE", "yyyy-MM-dd");
    	this.addField("FAULT_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("FAULT_DATE", "yyyy-MM-dd");
    	this.addField("CLAIM_STATUS",BaseVO.OP_STRING);
    	this.addField("PROVINCE_CODE",BaseVO.OP_STRING);
    	this.addField("SERVICE_COST",BaseVO.OP_STRING);
    	this.addField("STOCK_MEET",BaseVO.OP_STRING);
    	this.addField("APPLY_USER",BaseVO.OP_STRING);
    	this.addField("SE_TYPE",BaseVO.OP_STRING);
    	this.addField("SINGLE_STEP",BaseVO.OP_STRING);
    	this.addField("CLAIM_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("MILEAGE_CHECK_OPINION",BaseVO.OP_STRING);
    	this.addField("REAPPLY_REASON",BaseVO.OP_STRING);
    	this.addField("FINAL_USER",BaseVO.OP_STRING);
    	this.addField("SE_METHOD",BaseVO.OP_STRING);
    	this.addField("FAULT_CODE",BaseVO.OP_STRING);
    	this.addField("IF_OVERDUE",BaseVO.OP_STRING);
    	this.addField("ACTIVITY_ID",BaseVO.OP_STRING);
    	this.addField("APPLY_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
    	this.addField("BUY_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
    	this.addField("PHONE",BaseVO.OP_STRING);
    	this.addField("ENGINE_COST",BaseVO.OP_STRING);
    	this.addField("USER_NO",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("BRIDGE_COST",BaseVO.OP_STRING);
    	this.addField("REJECT_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("REJECT_DATE", "yyyy-MM-dd");
    	this.addField("APPLY_ADDRES",BaseVO.OP_STRING);
    	this.addField("CITY_CODE",BaseVO.OP_STRING);
    	this.addField("OUT_AMOUNT",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("FAULT_ANLYSIE",BaseVO.OP_STRING);
    	this.addField("DISPOSECODE",BaseVO.OP_STRING);
    	this.addField("SERVICEID",BaseVO.OP_STRING);
    	this.addField("USER_TYPE",BaseVO.OP_STRING);
    	this.addField("ENSURE_MILEAGE",BaseVO.OP_STRING);
    	this.addField("OPERATE_USER",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("FAULT_ADDRESS",BaseVO.OP_STRING);
    	this.addField("LICENSE_PLATE",BaseVO.OP_STRING);
    	this.addField("REPAIR_USER",BaseVO.OP_STRING);
    	this.addField("COUNTY_CODE",BaseVO.OP_STRING);
    	this.addField("ADJUST_COSTS",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("ADJUST_REMARKS",BaseVO.OP_STRING);
    	this.addField("ENGINE_NO",BaseVO.OP_STRING);
    	this.addField("IF_FIXED",BaseVO.OP_STRING);
    	this.addField("CLAIM_IDENTITY_STATUS",BaseVO.OP_STRING);
    	this.addField("MATERIAL_COSTS",BaseVO.OP_STRING);
    	this.addField("PRE_AUTHOR_ID",BaseVO.OP_STRING);
    	this.addField("OUT_BUYPASS_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("OUT_BUYPASS_DATE", "yyyy-MM-dd");
    	this.addField("SERVICEDETAILCODE",BaseVO.OP_STRING);
    	this.addField("REAPPLY_STATUS",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("LINK_MAN",BaseVO.OP_STRING);
    	this.addField("IF_RECOVERY",BaseVO.OP_STRING);
    	this.addField("MAINTENANCE_COSTS",BaseVO.OP_STRING);
    	this.addField("VEHICLE_PRODUCT",BaseVO.OP_STRING);
    	this.addField("APPLY_COUNT",BaseVO.OP_STRING);
    	this.addField("REPAIR_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("REPAIR_DATE", "yyyy-MM-dd");
    	this.addField("IS_HAND",BaseVO.OP_STRING);
    	this.addField("OLDPART_FINAL_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("OLDPART_FINAL_DATE", "yyyy-MM-dd");
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("VIN",BaseVO.OP_STRING);
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("MILEAGE_APPLY_STATUS",BaseVO.OP_STRING);
    	this.addField("USER_ADDRESS",BaseVO.OP_STRING);
    	this.addField("GEARBOX_COST",BaseVO.OP_STRING);
    	this.addField("CHECKPASS_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("CHECKPASS_DATE", "yyyy-MM-dd");
    	this.addField("APPLY_REPAIR_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("APPLY_REPAIR_DATE", "yyyy-MM-dd");
    	this.addField("IF_PRE_AUTHOR",BaseVO.OP_STRING);
    	this.addField("VEHICLE_ID",BaseVO.OP_STRING);
    	this.addField("FAULT_ID",BaseVO.OP_STRING);
    	this.addField("REPAIR_ADDRESS",BaseVO.OP_STRING);
    	this.addField("CLAIM_NO",BaseVO.OP_STRING);
    	this.addField("MILEAGE_APPLY_REASON",BaseVO.OP_STRING);
    	this.addField("SAFE_COSTS",BaseVO.OP_STRING);
    	this.addField("CHECK_OPINION",BaseVO.OP_STRING);
    	this.addField("APPLY_USER_TYPE",BaseVO.OP_STRING);
    	this.addField("GUARANTEE_NO",BaseVO.OP_STRING);
    	this.addField("IF_DEAL",BaseVO.OP_STRING);
    	this.addField("IF_PERSON_CHECK",BaseVO.OP_STRING);
    	this.addField("G_TIMES",BaseVO.OP_STRING);
    	this.addField("FORMNO",BaseVO.OP_STRING);
    	this.addField("ADJUST_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("ADJUST_DATE", "yyyy-MM-dd");
    	this.addField("OVERDUE_DAYS",BaseVO.OP_STRING);
    	this.addField("VEHICLE_USE",BaseVO.OP_STRING);
    	this.addField("FAULT_FROM",BaseVO.OP_STRING);
    	this.addField("USER_NAME",BaseVO.OP_STRING);
    	this.addField("SVIN",BaseVO.OP_STRING);
    	this.addField("TRAIN_COSTS",BaseVO.OP_STRING);
    	this.addField("OLDPART_STATUS",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
		this.bindFieldToSequence("CLAIM_ID","COMMON");
    	this.setVOTableName("SE_BU_CLAIM");
}
	public void setClaimType(String ClaimType){
		this.setInternal("CLAIM_TYPE" ,ClaimType );
	}


	public String getClaimType(){
		return (String)this.getInternal("CLAIM_TYPE");
	}
	public void setIfStorage(String IfStorage){
		this.setInternal("IF_STORAGE" ,IfStorage );
	}


	public String getIfStorage(){
		return (String)this.getInternal("IF_STORAGE");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setWorkId(String WorkId){
		this.setInternal("WORK_ID" ,WorkId );
	}


	public String getWorkId(){
		return (String)this.getInternal("WORK_ID");
	}
	public void setApplyMobil(String ApplyMobil){
		this.setInternal("APPLY_MOBIL" ,ApplyMobil );
	}


	public String getApplyMobil(){
		return (String)this.getInternal("APPLY_MOBIL");
	}
	public void setRecheckDate(Date RecheckDate){
		this.setInternal("RECHECK_DATE" ,RecheckDate );
	}


	public Date getRecheckDate(){
		return (Date)this.getInternal("RECHECK_DATE");
	}
	public void setMaintenanceDate(Date MaintenanceDate){
		this.setInternal("MAINTENANCE_DATE" ,MaintenanceDate );
	}


	public Date getMaintenanceDate(){
		return (Date)this.getInternal("MAINTENANCE_DATE");
	}
	public void setOutBuyrejectDate(Date OutBuyrejectDate){
		this.setInternal("OUT_BUYREJECT_DATE" ,OutBuyrejectDate );
	}


	public Date getOutBuyrejectDate(){
		return (Date)this.getInternal("OUT_BUYREJECT_DATE");
	}
	public void setDispatchId(String DispatchId){
		this.setInternal("DISPATCH_ID" ,DispatchId );
	}


	public String getDispatchId(){
		return (String)this.getInternal("DISPATCH_ID");
	}
	public void setAdjustUser(String AdjustUser){
		this.setInternal("ADJUST_USER" ,AdjustUser );
	}


	public String getAdjustUser(){
		return (String)this.getInternal("ADJUST_USER");
	}
	public void setMileage(String Mileage){
		this.setInternal("MILEAGE" ,Mileage );
	}


	public String getMileage(){
		return (String)this.getInternal("MILEAGE");
	}
	public void setUserdescription(String Userdescription){
		this.setInternal("USERDESCRIPTION" ,Userdescription );
	}


	public String getUserdescription(){
		return (String)this.getInternal("USERDESCRIPTION");
	}
	public void setFactoryDate(Date FactoryDate){
		this.setInternal("FACTORY_DATE" ,FactoryDate );
	}


	public Date getFactoryDate(){
		return (Date)this.getInternal("FACTORY_DATE");
	}
	public void setFaultDate(Date FaultDate){
		this.setInternal("FAULT_DATE" ,FaultDate );
	}


	public Date getFaultDate(){
		return (Date)this.getInternal("FAULT_DATE");
	}
	public void setClaimStatus(String ClaimStatus){
		this.setInternal("CLAIM_STATUS" ,ClaimStatus );
	}


	public String getClaimStatus(){
		return (String)this.getInternal("CLAIM_STATUS");
	}
	public void setProvinceCode(String ProvinceCode){
		this.setInternal("PROVINCE_CODE" ,ProvinceCode );
	}


	public String getProvinceCode(){
		return (String)this.getInternal("PROVINCE_CODE");
	}
	public void setServiceCost(String ServiceCost){
		this.setInternal("SERVICE_COST" ,ServiceCost );
	}


	public String getServiceCost(){
		return (String)this.getInternal("SERVICE_COST");
	}
	public void setStockMeet(String StockMeet){
		this.setInternal("STOCK_MEET" ,StockMeet );
	}


	public String getStockMeet(){
		return (String)this.getInternal("STOCK_MEET");
	}
	public void setApplyUser(String ApplyUser){
		this.setInternal("APPLY_USER" ,ApplyUser );
	}


	public String getApplyUser(){
		return (String)this.getInternal("APPLY_USER");
	}
	public void setSeType(String SeType){
		this.setInternal("SE_TYPE" ,SeType );
	}


	public String getSeType(){
		return (String)this.getInternal("SE_TYPE");
	}
	public void setSingleStep(String SingleStep){
		this.setInternal("SINGLE_STEP" ,SingleStep );
	}


	public String getSingleStep(){
		return (String)this.getInternal("SINGLE_STEP");
	}
	public void setClaimId(String ClaimId){
		this.setInternal("CLAIM_ID" ,ClaimId );
	}


	public String getClaimId(){
		return (String)this.getInternal("CLAIM_ID");
	}
	public void setMileageCheckOpinion(String MileageCheckOpinion){
		this.setInternal("MILEAGE_CHECK_OPINION" ,MileageCheckOpinion );
	}


	public String getMileageCheckOpinion(){
		return (String)this.getInternal("MILEAGE_CHECK_OPINION");
	}
	public void setReapplyReason(String ReapplyReason){
		this.setInternal("REAPPLY_REASON" ,ReapplyReason );
	}


	public String getReapplyReason(){
		return (String)this.getInternal("REAPPLY_REASON");
	}
	public void setFinalUser(String FinalUser){
		this.setInternal("FINAL_USER" ,FinalUser );
	}


	public String getFinalUser(){
		return (String)this.getInternal("FINAL_USER");
	}
	public void setSeMethod(String SeMethod){
		this.setInternal("SE_METHOD" ,SeMethod );
	}


	public String getSeMethod(){
		return (String)this.getInternal("SE_METHOD");
	}
	public void setFaultCode(String FaultCode){
		this.setInternal("FAULT_CODE" ,FaultCode );
	}


	public String getFaultCode(){
		return (String)this.getInternal("FAULT_CODE");
	}
	public void setIfOverdue(String IfOverdue){
		this.setInternal("IF_OVERDUE" ,IfOverdue );
	}


	public String getIfOverdue(){
		return (String)this.getInternal("IF_OVERDUE");
	}
	public void setActivityId(String ActivityId){
		this.setInternal("ACTIVITY_ID" ,ActivityId );
	}


	public String getActivityId(){
		return (String)this.getInternal("ACTIVITY_ID");
	}
	public void setApplyDate(Date ApplyDate){
		this.setInternal("APPLY_DATE" ,ApplyDate );
	}


	public Date getApplyDate(){
		return (Date)this.getInternal("APPLY_DATE");
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
	public void setEngineCost(String EngineCost){
		this.setInternal("ENGINE_COST" ,EngineCost );
	}


	public String getEngineCost(){
		return (String)this.getInternal("ENGINE_COST");
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
	public void setBridgeCost(String BridgeCost){
		this.setInternal("BRIDGE_COST" ,BridgeCost );
	}


	public String getBridgeCost(){
		return (String)this.getInternal("BRIDGE_COST");
	}
	public void setRejectDate(Date RejectDate){
		this.setInternal("REJECT_DATE" ,RejectDate );
	}


	public Date getRejectDate(){
		return (Date)this.getInternal("REJECT_DATE");
	}
	public void setApplyAddres(String ApplyAddres){
		this.setInternal("APPLY_ADDRES" ,ApplyAddres );
	}


	public String getApplyAddres(){
		return (String)this.getInternal("APPLY_ADDRES");
	}
	public void setCityCode(String CityCode){
		this.setInternal("CITY_CODE" ,CityCode );
	}


	public String getCityCode(){
		return (String)this.getInternal("CITY_CODE");
	}
	public void setOutAmount(String OutAmount){
		this.setInternal("OUT_AMOUNT" ,OutAmount );
	}


	public String getOutAmount(){
		return (String)this.getInternal("OUT_AMOUNT");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
	public void setFaultAnlysie(String FaultAnlysie){
		this.setInternal("FAULT_ANLYSIE" ,FaultAnlysie );
	}


	public String getFaultAnlysie(){
		return (String)this.getInternal("FAULT_ANLYSIE");
	}
	public void setDisposecode(String Disposecode){
		this.setInternal("DISPOSECODE" ,Disposecode );
	}


	public String getDisposecode(){
		return (String)this.getInternal("DISPOSECODE");
	}
	public void setServiceid(String Serviceid){
		this.setInternal("SERVICEID" ,Serviceid );
	}


	public String getServiceid(){
		return (String)this.getInternal("SERVICEID");
	}
	public void setUserType(String UserType){
		this.setInternal("USER_TYPE" ,UserType );
	}


	public String getUserType(){
		return (String)this.getInternal("USER_TYPE");
	}
	public void setEnsureMileage(String EnsureMileage){
		this.setInternal("ENSURE_MILEAGE" ,EnsureMileage );
	}


	public String getEnsureMileage(){
		return (String)this.getInternal("ENSURE_MILEAGE");
	}
	public void setOperateUser(String OperateUser){
		this.setInternal("OPERATE_USER" ,OperateUser );
	}


	public String getOperateUser(){
		return (String)this.getInternal("OPERATE_USER");
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
	public void setLicensePlate(String LicensePlate){
		this.setInternal("LICENSE_PLATE" ,LicensePlate );
	}


	public String getLicensePlate(){
		return (String)this.getInternal("LICENSE_PLATE");
	}
	public void setRepairUser(String RepairUser){
		this.setInternal("REPAIR_USER" ,RepairUser );
	}


	public String getRepairUser(){
		return (String)this.getInternal("REPAIR_USER");
	}
	public void setCountyCode(String CountyCode){
		this.setInternal("COUNTY_CODE" ,CountyCode );
	}


	public String getCountyCode(){
		return (String)this.getInternal("COUNTY_CODE");
	}
	public void setAdjustCosts(String AdjustCosts){
		this.setInternal("ADJUST_COSTS" ,AdjustCosts );
	}


	public String getAdjustCosts(){
		return (String)this.getInternal("ADJUST_COSTS");
	}
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setAdjustRemarks(String AdjustRemarks){
		this.setInternal("ADJUST_REMARKS" ,AdjustRemarks );
	}


	public String getAdjustRemarks(){
		return (String)this.getInternal("ADJUST_REMARKS");
	}
	public void setEngineNo(String EngineNo){
		this.setInternal("ENGINE_NO" ,EngineNo );
	}


	public String getEngineNo(){
		return (String)this.getInternal("ENGINE_NO");
	}
	public void setIfFixed(String IfFixed){
		this.setInternal("IF_FIXED" ,IfFixed );
	}


	public String getIfFixed(){
		return (String)this.getInternal("IF_FIXED");
	}
	public void setClaimIdentityStatus(String ClaimIdentityStatus){
		this.setInternal("CLAIM_IDENTITY_STATUS" ,ClaimIdentityStatus );
	}


	public String getClaimIdentityStatus(){
		return (String)this.getInternal("CLAIM_IDENTITY_STATUS");
	}
	public void setMaterialCosts(String MaterialCosts){
		this.setInternal("MATERIAL_COSTS" ,MaterialCosts );
	}


	public String getMaterialCosts(){
		return (String)this.getInternal("MATERIAL_COSTS");
	}
	public void setPreAuthorId(String PreAuthorId){
		this.setInternal("PRE_AUTHOR_ID" ,PreAuthorId );
	}


	public String getPreAuthorId(){
		return (String)this.getInternal("PRE_AUTHOR_ID");
	}
	public void setOutBuypassDate(Date OutBuypassDate){
		this.setInternal("OUT_BUYPASS_DATE" ,OutBuypassDate );
	}


	public Date getOutBuypassDate(){
		return (Date)this.getInternal("OUT_BUYPASS_DATE");
	}
	public void setServicedetailcode(String Servicedetailcode){
		this.setInternal("SERVICEDETAILCODE" ,Servicedetailcode );
	}


	public String getServicedetailcode(){
		return (String)this.getInternal("SERVICEDETAILCODE");
	}
	public void setReapplyStatus(String ReapplyStatus){
		this.setInternal("REAPPLY_STATUS" ,ReapplyStatus );
	}


	public String getReapplyStatus(){
		return (String)this.getInternal("REAPPLY_STATUS");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setLinkMan(String LinkMan){
		this.setInternal("LINK_MAN" ,LinkMan );
	}


	public String getLinkMan(){
		return (String)this.getInternal("LINK_MAN");
	}
	public void setIfRecovery(String IfRecovery){
		this.setInternal("IF_RECOVERY" ,IfRecovery );
	}


	public String getIfRecovery(){
		return (String)this.getInternal("IF_RECOVERY");
	}
	public void setMaintenanceCosts(String MaintenanceCosts){
		this.setInternal("MAINTENANCE_COSTS" ,MaintenanceCosts );
	}


	public String getMaintenanceCosts(){
		return (String)this.getInternal("MAINTENANCE_COSTS");
	}
	public void setVehicleProduct(String VehicleProduct){
		this.setInternal("VEHICLE_PRODUCT" ,VehicleProduct );
	}


	public String getVehicleProduct(){
		return (String)this.getInternal("VEHICLE_PRODUCT");
	}
	public void setApplyCount(String ApplyCount){
		this.setInternal("APPLY_COUNT" ,ApplyCount );
	}


	public String getApplyCount(){
		return (String)this.getInternal("APPLY_COUNT");
	}
	public void setRepairDate(Date RepairDate){
		this.setInternal("REPAIR_DATE" ,RepairDate );
	}


	public Date getRepairDate(){
		return (Date)this.getInternal("REPAIR_DATE");
	}
	public void setIsHand(String IsHand){
		this.setInternal("IS_HAND" ,IsHand );
	}


	public String getIsHand(){
		return (String)this.getInternal("IS_HAND");
	}
	public void setOldpartFinalDate(Date OldpartFinalDate){
		this.setInternal("OLDPART_FINAL_DATE" ,OldpartFinalDate );
	}


	public Date getOldpartFinalDate(){
		return (Date)this.getInternal("OLDPART_FINAL_DATE");
	}
	public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
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
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setMileageApplyStatus(String MileageApplyStatus){
		this.setInternal("MILEAGE_APPLY_STATUS" ,MileageApplyStatus );
	}


	public String getMileageApplyStatus(){
		return (String)this.getInternal("MILEAGE_APPLY_STATUS");
	}
	public void setUserAddress(String UserAddress){
		this.setInternal("USER_ADDRESS" ,UserAddress );
	}


	public String getUserAddress(){
		return (String)this.getInternal("USER_ADDRESS");
	}
	public void setGearboxCost(String GearboxCost){
		this.setInternal("GEARBOX_COST" ,GearboxCost );
	}


	public String getGearboxCost(){
		return (String)this.getInternal("GEARBOX_COST");
	}
	public void setCheckpassDate(Date CheckpassDate){
		this.setInternal("CHECKPASS_DATE" ,CheckpassDate );
	}


	public Date getCheckpassDate(){
		return (Date)this.getInternal("CHECKPASS_DATE");
	}
	public void setApplyRepairDate(Date ApplyRepairDate){
		this.setInternal("APPLY_REPAIR_DATE" ,ApplyRepairDate );
	}


	public Date getApplyRepairDate(){
		return (Date)this.getInternal("APPLY_REPAIR_DATE");
	}
	public void setIfPreAuthor(String IfPreAuthor){
		this.setInternal("IF_PRE_AUTHOR" ,IfPreAuthor );
	}


	public String getIfPreAuthor(){
		return (String)this.getInternal("IF_PRE_AUTHOR");
	}
	public void setVehicleId(String VehicleId){
		this.setInternal("VEHICLE_ID" ,VehicleId );
	}


	public String getVehicleId(){
		return (String)this.getInternal("VEHICLE_ID");
	}
	public void setFaultId(String FaultId){
		this.setInternal("FAULT_ID" ,FaultId );
	}


	public String getFaultId(){
		return (String)this.getInternal("FAULT_ID");
	}
	public void setRepairAddress(String RepairAddress){
		this.setInternal("REPAIR_ADDRESS" ,RepairAddress );
	}


	public String getRepairAddress(){
		return (String)this.getInternal("REPAIR_ADDRESS");
	}
	public void setClaimNo(String ClaimNo){
		this.setInternal("CLAIM_NO" ,ClaimNo );
	}


	public String getClaimNo(){
		return (String)this.getInternal("CLAIM_NO");
	}
	public void setMileageApplyReason(String MileageApplyReason){
		this.setInternal("MILEAGE_APPLY_REASON" ,MileageApplyReason );
	}


	public String getMileageApplyReason(){
		return (String)this.getInternal("MILEAGE_APPLY_REASON");
	}
	public void setSafeCosts(String SafeCosts){
		this.setInternal("SAFE_COSTS" ,SafeCosts );
	}


	public String getSafeCosts(){
		return (String)this.getInternal("SAFE_COSTS");
	}
	public void setCheckOpinion(String CheckOpinion){
		this.setInternal("CHECK_OPINION" ,CheckOpinion );
	}


	public String getCheckOpinion(){
		return (String)this.getInternal("CHECK_OPINION");
	}
	public void setApplyUserType(String ApplyUserType){
		this.setInternal("APPLY_USER_TYPE" ,ApplyUserType );
	}


	public String getApplyUserType(){
		return (String)this.getInternal("APPLY_USER_TYPE");
	}
	public void setGuaranteeNo(String GuaranteeNo){
		this.setInternal("GUARANTEE_NO" ,GuaranteeNo );
	}


	public String getGuaranteeNo(){
		return (String)this.getInternal("GUARANTEE_NO");
	}
	public void setIfDeal(String IfDeal){
		this.setInternal("IF_DEAL" ,IfDeal );
	}


	public String getIfDeal(){
		return (String)this.getInternal("IF_DEAL");
	}
	public void setIfPersonCheck(String IfPersonCheck){
		this.setInternal("IF_PERSON_CHECK" ,IfPersonCheck );
	}


	public String getIfPersonCheck(){
		return (String)this.getInternal("IF_PERSON_CHECK");
	}
	public void setGTimes(String GTimes){
		this.setInternal("G_TIMES" ,GTimes );
	}


	public String getGTimes(){
		return (String)this.getInternal("G_TIMES");
	}
	public void setFormno(String Formno){
		this.setInternal("FORMNO" ,Formno );
	}


	public String getFormno(){
		return (String)this.getInternal("FORMNO");
	}
	public void setAdjustDate(Date AdjustDate){
		this.setInternal("ADJUST_DATE" ,AdjustDate );
	}


	public Date getAdjustDate(){
		return (Date)this.getInternal("ADJUST_DATE");
	}
	public void setOverdueDays(String OverdueDays){
		this.setInternal("OVERDUE_DAYS" ,OverdueDays );
	}


	public String getOverdueDays(){
		return (String)this.getInternal("OVERDUE_DAYS");
	}
	public void setVehicleUse(String VehicleUse){
		this.setInternal("VEHICLE_USE" ,VehicleUse );
	}


	public String getVehicleUse(){
		return (String)this.getInternal("VEHICLE_USE");
	}
	public void setFaultFrom(String FaultFrom){
		this.setInternal("FAULT_FROM" ,FaultFrom );
	}


	public String getFaultFrom(){
		return (String)this.getInternal("FAULT_FROM");
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
	public void setTrainCosts(String TrainCosts){
		this.setInternal("TRAIN_COSTS" ,TrainCosts );
	}


	public String getTrainCosts(){
		return (String)this.getInternal("TRAIN_COSTS");
	}
	public void setOldpartStatus(String OldpartStatus){
		this.setInternal("OLDPART_STATUS" ,OldpartStatus );
	}


	public String getOldpartStatus(){
		return (String)this.getInternal("OLDPART_STATUS");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
}
