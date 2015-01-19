package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class MainDealerVO extends BaseVO{
    public MainDealerVO(){
    	//设置字段信息
    	this.addField("SE_MANAGEER",BaseVO.OP_STRING);
    	this.addField("SETUP_TIME",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("DEALER_STATUS",BaseVO.OP_STRING);
    	this.addField("INFO_PERSON",BaseVO.OP_STRING);
    	this.addField("PLANER",BaseVO.OP_STRING);
    	this.addField("KMS_NO",BaseVO.OP_STRING);
    	this.addField("NOW_FACTORY",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("INFO_PERSON_TEL",BaseVO.OP_STRING);
    	this.addField("FST_NO",BaseVO.OP_STRING);
    	this.addField("SE_ASSISTANT",BaseVO.OP_STRING);
    	this.addField("JIONSZQ_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("JIONSZQ_TIME", "yyyy-MM-dd");
    	this.addField("STATION_NAME",BaseVO.OP_STRING);
    	this.addField("SURVEYOR",BaseVO.OP_STRING);
    	this.addField("TEC_COUNTS",BaseVO.OP_STRING);
    	this.addField("FAX_REGIST",BaseVO.OP_STRING);
    	this.addField("FIN_STAFF_TEL",BaseVO.OP_STRING);
    	this.addField("PARKING_AREA",BaseVO.OP_STRING);
    	this.addField("FAX",BaseVO.OP_STRING);
    	this.addField("IF_MAKE_AMOUNT",BaseVO.OP_STRING);
    	this.addField("ZIP_CODE",BaseVO.OP_STRING);
    	this.addField("JIONFST_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("JIONFST_TIME", "yyyy-MM-dd");
    	this.addField("DEALER_CODE",BaseVO.OP_STRING);
    	this.addField("MAN_COUNTS",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("SCOPE_BUS",BaseVO.OP_STRING);
    	this.addField("HIGHER_COMPANY",BaseVO.OP_STRING);
    	this.addField("IF_WC",BaseVO.OP_STRING);
    	this.addField("MAKE_START_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("MAKE_START_DATE", "yyyy-MM-dd");
    	this.addField("BELONG_OFFICE",BaseVO.OP_STRING);
    	this.addField("REGIST_FUNDS",BaseVO.OP_STRING);
    	this.addField("OUT_MAX_MILES",BaseVO.OP_STRING);
    	this.addField("MAJOR_MODELS",BaseVO.OP_STRING);
    	this.addField("DEALER_TYPE",BaseVO.OP_STRING);
    	this.addField("DEALER_STAR",BaseVO.OP_STRING);
    	this.addField("WORKSHOP_AREA",BaseVO.OP_STRING);
    	this.addField("LEGAL_NAME",BaseVO.OP_STRING);
    	this.addField("INSPECT_COUNTS",BaseVO.OP_STRING);
    	this.addField("CITY",BaseVO.OP_STRING);
    	this.addField("STATION_TEL",BaseVO.OP_STRING);
    	this.addField("INVOICE_TYPE",BaseVO.OP_STRING);
    	this.addField("DEALER_STAR_NAME",BaseVO.OP_STRING);
    	this.addField("E_MAIL",BaseVO.OP_STRING);
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("REPAIR_COUNTS",BaseVO.OP_STRING);
    	this.addField("BANK_ACCOUNT",BaseVO.OP_STRING);
    	this.addField("DEALER_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("ENTERPRISE_NATURE",BaseVO.OP_STRING);
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("JIONKMS_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("JIONKMS_TIME", "yyyy-MM-dd");
    	this.addField("IF_FST",BaseVO.OP_STRING);
    	this.addField("IF_AUTO_CHECK",BaseVO.OP_STRING);
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("IF_KMS",BaseVO.OP_STRING);
    	this.addField("JIONWC_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("JIONWC_TIME", "yyyy-MM-dd");
    	this.addField("SURVEYOR_TEL",BaseVO.OP_STRING);
    	this.addField("WAREHOUSE_AREA",BaseVO.OP_STRING);
    	this.addField("SETTLE_END_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("SETTLE_END_DATE", "yyyy-MM-dd");
    	this.addField("SETTLE_TYPE",BaseVO.OP_STRING);
    	this.addField("STAFF_COUNTS",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("PART_MANAGER",BaseVO.OP_STRING);
    	this.addField("OFFICER_TEL",BaseVO.OP_STRING);
    	this.addField("DUTY_TEL",BaseVO.OP_STRING);
    	this.addField("BUS_PERSON_TEL",BaseVO.OP_STRING);
    	this.addField("BUS_PERSON",BaseVO.OP_STRING);
    	this.addField("DEALER_SHORTNAME",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("ADDRESS",BaseVO.OP_STRING);
    	this.addField("FIN_STAFF",BaseVO.OP_STRING);
    	this.addField("ALL_AREA",BaseVO.OP_STRING);
    	this.addField("SETTLE_START_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("SETTLE_START_DATE", "yyyy-MM-dd");
    	this.addField("SYSTEM_MANAGER",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("OPEN_BANK",BaseVO.OP_STRING);
    	this.addField("LEGAL_TEL",BaseVO.OP_STRING);
    	this.addField("DEALER_NAME",BaseVO.OP_STRING);
    	this.addField("OFFICER_NAME",BaseVO.OP_STRING);
    	this.addField("OVERDUE_DAYS",BaseVO.OP_STRING);
    	this.addField("WC_NO",BaseVO.OP_STRING);
    	this.addField("TRANS_PRICE",BaseVO.OP_STRING);
    	this.addField("PROVINCE",BaseVO.OP_STRING);
    	this.addField("FOCUS_MILES",BaseVO.OP_STRING);
    	this.addField("TARIFF",BaseVO.OP_STRING);
    	this.addField("MAKE_END_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("MAKE_END_DATE", "yyyy-MM-dd");
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("REPAIR_FUNDS",BaseVO.OP_STRING);
		this.bindFieldToSequence("DEALER_ID","COMMON");
    	this.setVOTableName("MAIN_DEALER");
}
	public void setSeManageer(String SeManageer){
		this.setInternal("SE_MANAGEER" ,SeManageer );
	}


	public String getSeManageer(){
		return (String)this.getInternal("SE_MANAGEER");
	}
	public void setSetupTime(String SetupTime){
		this.setInternal("SETUP_TIME" ,SetupTime );
	}


	public String getSetupTime(){
		return (String)this.getInternal("SETUP_TIME");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setDealerStatus(String DealerStatus){
		this.setInternal("DEALER_STATUS" ,DealerStatus );
	}


	public String getDealerStatus(){
		return (String)this.getInternal("DEALER_STATUS");
	}
	public void setInfoPerson(String InfoPerson){
		this.setInternal("INFO_PERSON" ,InfoPerson );
	}


	public String getInfoPerson(){
		return (String)this.getInternal("INFO_PERSON");
	}
	public void setPlaner(String Planer){
		this.setInternal("PLANER" ,Planer );
	}


	public String getPlaner(){
		return (String)this.getInternal("PLANER");
	}
	public void setKmsNo(String KmsNo){
		this.setInternal("KMS_NO" ,KmsNo );
	}


	public String getKmsNo(){
		return (String)this.getInternal("KMS_NO");
	}
	public void setNowFactory(String NowFactory){
		this.setInternal("NOW_FACTORY" ,NowFactory );
	}


	public String getNowFactory(){
		return (String)this.getInternal("NOW_FACTORY");
	}
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setInfoPersonTel(String InfoPersonTel){
		this.setInternal("INFO_PERSON_TEL" ,InfoPersonTel );
	}


	public String getInfoPersonTel(){
		return (String)this.getInternal("INFO_PERSON_TEL");
	}
	public void setFstNo(String FstNo){
		this.setInternal("FST_NO" ,FstNo );
	}


	public String getFstNo(){
		return (String)this.getInternal("FST_NO");
	}
	public void setSeAssistant(String SeAssistant){
		this.setInternal("SE_ASSISTANT" ,SeAssistant );
	}


	public String getSeAssistant(){
		return (String)this.getInternal("SE_ASSISTANT");
	}
	public void setJionszqTime(Date JionszqTime){
		this.setInternal("JIONSZQ_TIME" ,JionszqTime );
	}


	public Date getJionszqTime(){
		return (Date)this.getInternal("JIONSZQ_TIME");
	}
	public void setStationName(String StationName){
		this.setInternal("STATION_NAME" ,StationName );
	}


	public String getStationName(){
		return (String)this.getInternal("STATION_NAME");
	}
	public void setSurveyor(String Surveyor){
		this.setInternal("SURVEYOR" ,Surveyor );
	}


	public String getSurveyor(){
		return (String)this.getInternal("SURVEYOR");
	}
	public void setTecCounts(String TecCounts){
		this.setInternal("TEC_COUNTS" ,TecCounts );
	}


	public String getTecCounts(){
		return (String)this.getInternal("TEC_COUNTS");
	}
	public void setFaxRegist(String FaxRegist){
		this.setInternal("FAX_REGIST" ,FaxRegist );
	}


	public String getFaxRegist(){
		return (String)this.getInternal("FAX_REGIST");
	}
	public void setFinStaffTel(String FinStaffTel){
		this.setInternal("FIN_STAFF_TEL" ,FinStaffTel );
	}


	public String getFinStaffTel(){
		return (String)this.getInternal("FIN_STAFF_TEL");
	}
	public void setParkingArea(String ParkingArea){
		this.setInternal("PARKING_AREA" ,ParkingArea );
	}


	public String getParkingArea(){
		return (String)this.getInternal("PARKING_AREA");
	}
	public void setFax(String Fax){
		this.setInternal("FAX" ,Fax );
	}


	public String getFax(){
		return (String)this.getInternal("FAX");
	}
	public void setIfMakeAmount(String IfMakeAmount){
		this.setInternal("IF_MAKE_AMOUNT" ,IfMakeAmount );
	}


	public String getIfMakeAmount(){
		return (String)this.getInternal("IF_MAKE_AMOUNT");
	}
	public void setZipCode(String ZipCode){
		this.setInternal("ZIP_CODE" ,ZipCode );
	}


	public String getZipCode(){
		return (String)this.getInternal("ZIP_CODE");
	}
	public void setJionfstTime(Date JionfstTime){
		this.setInternal("JIONFST_TIME" ,JionfstTime );
	}


	public Date getJionfstTime(){
		return (Date)this.getInternal("JIONFST_TIME");
	}
	public void setDealerCode(String DealerCode){
		this.setInternal("DEALER_CODE" ,DealerCode );
	}


	public String getDealerCode(){
		return (String)this.getInternal("DEALER_CODE");
	}
	public void setManCounts(String ManCounts){
		this.setInternal("MAN_COUNTS" ,ManCounts );
	}


	public String getManCounts(){
		return (String)this.getInternal("MAN_COUNTS");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setScopeBus(String ScopeBus){
		this.setInternal("SCOPE_BUS" ,ScopeBus );
	}


	public String getScopeBus(){
		return (String)this.getInternal("SCOPE_BUS");
	}
	public void setHigherCompany(String HigherCompany){
		this.setInternal("HIGHER_COMPANY" ,HigherCompany );
	}


	public String getHigherCompany(){
		return (String)this.getInternal("HIGHER_COMPANY");
	}
	public void setIfWc(String IfWc){
		this.setInternal("IF_WC" ,IfWc );
	}


	public String getIfWc(){
		return (String)this.getInternal("IF_WC");
	}
	public void setMakeStartDate(Date MakeStartDate){
		this.setInternal("MAKE_START_DATE" ,MakeStartDate );
	}


	public Date getMakeStartDate(){
		return (Date)this.getInternal("MAKE_START_DATE");
	}
	public void setBelongOffice(String BelongOffice){
		this.setInternal("BELONG_OFFICE" ,BelongOffice );
	}


	public String getBelongOffice(){
		return (String)this.getInternal("BELONG_OFFICE");
	}
	public void setRegistFunds(String RegistFunds){
		this.setInternal("REGIST_FUNDS" ,RegistFunds );
	}


	public String getRegistFunds(){
		return (String)this.getInternal("REGIST_FUNDS");
	}
	public void setOutMaxMiles(String OutMaxMiles){
		this.setInternal("OUT_MAX_MILES" ,OutMaxMiles );
	}


	public String getOutMaxMiles(){
		return (String)this.getInternal("OUT_MAX_MILES");
	}
	public void setMajorModels(String MajorModels){
		this.setInternal("MAJOR_MODELS" ,MajorModels );
	}


	public String getMajorModels(){
		return (String)this.getInternal("MAJOR_MODELS");
	}
	public void setDealerType(String DealerType){
		this.setInternal("DEALER_TYPE" ,DealerType );
	}


	public String getDealerType(){
		return (String)this.getInternal("DEALER_TYPE");
	}
	public void setDealerStar(String DealerStar){
		this.setInternal("DEALER_STAR" ,DealerStar );
	}


	public String getDealerStar(){
		return (String)this.getInternal("DEALER_STAR");
	}
	public void setWorkshopArea(String WorkshopArea){
		this.setInternal("WORKSHOP_AREA" ,WorkshopArea );
	}


	public String getWorkshopArea(){
		return (String)this.getInternal("WORKSHOP_AREA");
	}
	public void setLegalName(String LegalName){
		this.setInternal("LEGAL_NAME" ,LegalName );
	}


	public String getLegalName(){
		return (String)this.getInternal("LEGAL_NAME");
	}
	public void setInspectCounts(String InspectCounts){
		this.setInternal("INSPECT_COUNTS" ,InspectCounts );
	}


	public String getInspectCounts(){
		return (String)this.getInternal("INSPECT_COUNTS");
	}
	public void setCity(String City){
		this.setInternal("CITY" ,City );
	}


	public String getCity(){
		return (String)this.getInternal("CITY");
	}
	public void setStationTel(String StationTel){
		this.setInternal("STATION_TEL" ,StationTel );
	}


	public String getStationTel(){
		return (String)this.getInternal("STATION_TEL");
	}
	public void setInvoiceType(String InvoiceType){
		this.setInternal("INVOICE_TYPE" ,InvoiceType );
	}


	public String getInvoiceType(){
		return (String)this.getInternal("INVOICE_TYPE");
	}
	public void setDealerStarName(String DealerStarName){
		this.setInternal("DEALER_STAR_NAME" ,DealerStarName );
	}


	public String getDealerStarName(){
		return (String)this.getInternal("DEALER_STAR_NAME");
	}
	public void setEMail(String EMail){
		this.setInternal("E_MAIL" ,EMail );
	}


	public String getEMail(){
		return (String)this.getInternal("E_MAIL");
	}
	public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
	}
	public void setRepairCounts(String RepairCounts){
		this.setInternal("REPAIR_COUNTS" ,RepairCounts );
	}


	public String getRepairCounts(){
		return (String)this.getInternal("REPAIR_COUNTS");
	}
	public void setBankAccount(String BankAccount){
		this.setInternal("BANK_ACCOUNT" ,BankAccount );
	}


	public String getBankAccount(){
		return (String)this.getInternal("BANK_ACCOUNT");
	}
	public void setDealerId(String DealerId){
		this.setInternal("DEALER_ID" ,DealerId );
	}


	public String getDealerId(){
		return (String)this.getInternal("DEALER_ID");
	}
	public void setEnterpriseNature(String EnterpriseNature){
		this.setInternal("ENTERPRISE_NATURE" ,EnterpriseNature );
	}


	public String getEnterpriseNature(){
		return (String)this.getInternal("ENTERPRISE_NATURE");
	}
	public void setRemarks(String Remarks){
		this.setInternal("REMARKS" ,Remarks );
	}


	public String getRemarks(){
		return (String)this.getInternal("REMARKS");
	}
	public void setJionkmsTime(Date JionkmsTime){
		this.setInternal("JIONKMS_TIME" ,JionkmsTime );
	}


	public Date getJionkmsTime(){
		return (Date)this.getInternal("JIONKMS_TIME");
	}
	public void setIfFst(String IfFst){
		this.setInternal("IF_FST" ,IfFst );
	}


	public String getIfFst(){
		return (String)this.getInternal("IF_FST");
	}
	public void setIfAutoCheck(String IfAutoCheck){
		this.setInternal("IF_AUTO_CHECK" ,IfAutoCheck );
	}


	public String getIfAutoCheck(){
		return (String)this.getInternal("IF_AUTO_CHECK");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setIfKms(String IfKms){
		this.setInternal("IF_KMS" ,IfKms );
	}


	public String getIfKms(){
		return (String)this.getInternal("IF_KMS");
	}
	public void setJionwcTime(Date JionwcTime){
		this.setInternal("JIONWC_TIME" ,JionwcTime );
	}


	public Date getJionwcTime(){
		return (Date)this.getInternal("JIONWC_TIME");
	}
	public void setSurveyorTel(String SurveyorTel){
		this.setInternal("SURVEYOR_TEL" ,SurveyorTel );
	}


	public String getSurveyorTel(){
		return (String)this.getInternal("SURVEYOR_TEL");
	}
	public void setWarehouseArea(String WarehouseArea){
		this.setInternal("WAREHOUSE_AREA" ,WarehouseArea );
	}


	public String getWarehouseArea(){
		return (String)this.getInternal("WAREHOUSE_AREA");
	}
	public void setSettleEndDate(Date SettleEndDate){
		this.setInternal("SETTLE_END_DATE" ,SettleEndDate );
	}


	public Date getSettleEndDate(){
		return (Date)this.getInternal("SETTLE_END_DATE");
	}
	public void setSettleType(String SettleType){
		this.setInternal("SETTLE_TYPE" ,SettleType );
	}


	public String getSettleType(){
		return (String)this.getInternal("SETTLE_TYPE");
	}
	public void setStaffCounts(String StaffCounts){
		this.setInternal("STAFF_COUNTS" ,StaffCounts );
	}


	public String getStaffCounts(){
		return (String)this.getInternal("STAFF_COUNTS");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setPartManager(String PartManager){
		this.setInternal("PART_MANAGER" ,PartManager );
	}


	public String getPartManager(){
		return (String)this.getInternal("PART_MANAGER");
	}
	public void setOfficerTel(String OfficerTel){
		this.setInternal("OFFICER_TEL" ,OfficerTel );
	}


	public String getOfficerTel(){
		return (String)this.getInternal("OFFICER_TEL");
	}
	public void setDutyTel(String DutyTel){
		this.setInternal("DUTY_TEL" ,DutyTel );
	}


	public String getDutyTel(){
		return (String)this.getInternal("DUTY_TEL");
	}
	public void setBusPersonTel(String BusPersonTel){
		this.setInternal("BUS_PERSON_TEL" ,BusPersonTel );
	}


	public String getBusPersonTel(){
		return (String)this.getInternal("BUS_PERSON_TEL");
	}
	public void setBusPerson(String BusPerson){
		this.setInternal("BUS_PERSON" ,BusPerson );
	}


	public String getBusPerson(){
		return (String)this.getInternal("BUS_PERSON");
	}
	public void setDealerShortname(String DealerShortname){
		this.setInternal("DEALER_SHORTNAME" ,DealerShortname );
	}


	public String getDealerShortname(){
		return (String)this.getInternal("DEALER_SHORTNAME");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
	public void setAddress(String Address){
		this.setInternal("ADDRESS" ,Address );
	}


	public String getAddress(){
		return (String)this.getInternal("ADDRESS");
	}
	public void setFinStaff(String FinStaff){
		this.setInternal("FIN_STAFF" ,FinStaff );
	}


	public String getFinStaff(){
		return (String)this.getInternal("FIN_STAFF");
	}
	public void setAllArea(String AllArea){
		this.setInternal("ALL_AREA" ,AllArea );
	}


	public String getAllArea(){
		return (String)this.getInternal("ALL_AREA");
	}
	public void setSettleStartDate(Date SettleStartDate){
		this.setInternal("SETTLE_START_DATE" ,SettleStartDate );
	}


	public Date getSettleStartDate(){
		return (Date)this.getInternal("SETTLE_START_DATE");
	}
	public void setSystemManager(String SystemManager){
		this.setInternal("SYSTEM_MANAGER" ,SystemManager );
	}


	public String getSystemManager(){
		return (String)this.getInternal("SYSTEM_MANAGER");
	}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setOpenBank(String OpenBank){
		this.setInternal("OPEN_BANK" ,OpenBank );
	}


	public String getOpenBank(){
		return (String)this.getInternal("OPEN_BANK");
	}
	public void setLegalTel(String LegalTel){
		this.setInternal("LEGAL_TEL" ,LegalTel );
	}


	public String getLegalTel(){
		return (String)this.getInternal("LEGAL_TEL");
	}
	public void setDealerName(String DealerName){
		this.setInternal("DEALER_NAME" ,DealerName );
	}


	public String getDealerName(){
		return (String)this.getInternal("DEALER_NAME");
	}
	public void setOfficerName(String OfficerName){
		this.setInternal("OFFICER_NAME" ,OfficerName );
	}


	public String getOfficerName(){
		return (String)this.getInternal("OFFICER_NAME");
	}
	public void setOverdueDays(String OverdueDays){
		this.setInternal("OVERDUE_DAYS" ,OverdueDays );
	}


	public String getOverdueDays(){
		return (String)this.getInternal("OVERDUE_DAYS");
	}
	public void setWcNo(String WcNo){
		this.setInternal("WC_NO" ,WcNo );
	}


	public String getWcNo(){
		return (String)this.getInternal("WC_NO");
	}
	public void setTransPrice(String TransPrice){
		this.setInternal("TRANS_PRICE" ,TransPrice );
	}


	public String getTransPrice(){
		return (String)this.getInternal("TRANS_PRICE");
	}
	public void setProvince(String Province){
		this.setInternal("PROVINCE" ,Province );
	}


	public String getProvince(){
		return (String)this.getInternal("PROVINCE");
	}
	public void setFocusMiles(String FocusMiles){
		this.setInternal("FOCUS_MILES" ,FocusMiles );
	}


	public String getFocusMiles(){
		return (String)this.getInternal("FOCUS_MILES");
	}
	public void setTariff(String Tariff){
		this.setInternal("TARIFF" ,Tariff );
	}


	public String getTariff(){
		return (String)this.getInternal("TARIFF");
	}
	public void setMakeEndDate(Date MakeEndDate){
		this.setInternal("MAKE_END_DATE" ,MakeEndDate );
	}


	public Date getMakeEndDate(){
		return (Date)this.getInternal("MAKE_END_DATE");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setRepairFunds(String RepairFunds){
		this.setInternal("REPAIR_FUNDS" ,RepairFunds );
	}


	public String getRepairFunds(){
		return (String)this.getInternal("REPAIR_FUNDS");
	}
}

