package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class SeBuPreAuthorVO extends BaseVO{
    public SeBuPreAuthorVO(){
    	//�����ֶ���Ϣ
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("RULE_ID",BaseVO.OP_STRING);
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("VIN",BaseVO.OP_STRING);
    	this.addField("OUT_UCOUNT",BaseVO.OP_STRING);
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("IF_APPLYCLAIM",BaseVO.OP_STRING);
    	this.addField("GPS_LICENSE_PLATE",BaseVO.OP_STRING);
    	this.addField("PHONE",BaseVO.OP_STRING);
    	this.addField("AUTHOR_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("USER_ADDRESS",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("USER_NO",BaseVO.OP_STRING);
    	this.addField("MILEAGE",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("ARRIVE_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("ARRIVE_DATE", "yyyy-MM-dd");
    	this.addField("TRAILER_COST",BaseVO.OP_STRING);
    	this.addField("VEHICLE_ID",BaseVO.OP_STRING);
    	this.addField("AUTHOR_TYPE",BaseVO.OP_STRING);
    	this.addField("AUTHOR_NO",BaseVO.OP_STRING);
    	this.addField("OUT_USER",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("AMOUNT",BaseVO.OP_STRING);
    	this.addField("TELEPHONE",BaseVO.OP_STRING);
    	this.addField("LINK_MAN",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("LICENSE_PLATE",BaseVO.OP_STRING);
    	this.addField("LEVEL_CODE",BaseVO.OP_STRING);
    	this.addField("USER_NAME",BaseVO.OP_STRING);
    	this.addField("AUTHOR_STATUS",BaseVO.OP_STRING);
    	this.addField("FAULTLOCATION",BaseVO.OP_STRING);
    	this.addField("REPORT_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("REPORT_DATE", "yyyy-MM-dd");
    	this.addField("GO_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("GO_DATE", "yyyy-MM-dd");
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
		this.bindFieldToSequence("AUTHOR_ID","COMMON");
    	this.setVOTableName("SE_BU_PRE_AUTHOR");
}
	public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
	}
	public void setRuleId(String RuleId){
		this.setInternal("RULE_ID" ,RuleId );
	}


	public String getRuleId(){
		return (String)this.getInternal("RULE_ID");
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
	public void setVin(String Vin){
		this.setInternal("VIN" ,Vin );
	}


	public String getVin(){
		return (String)this.getInternal("VIN");
	}
	public void setOutUcount(String OutUcount){
		this.setInternal("OUT_UCOUNT" ,OutUcount );
	}


	public String getOutUcount(){
		return (String)this.getInternal("OUT_UCOUNT");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setIfApplyclaim(String IfApplyclaim){
		this.setInternal("IF_APPLYCLAIM" ,IfApplyclaim );
	}


	public String getIfApplyclaim(){
		return (String)this.getInternal("IF_APPLYCLAIM");
	}
	public void setGpsLicensePlate(String GpsLicensePlate){
		this.setInternal("GPS_LICENSE_PLATE" ,GpsLicensePlate );
	}


	public String getGpsLicensePlate(){
		return (String)this.getInternal("GPS_LICENSE_PLATE");
	}
	public void setPhone(String Phone){
		this.setInternal("PHONE" ,Phone );
	}


	public String getPhone(){
		return (String)this.getInternal("PHONE");
	}
	public void setAuthorId(String AuthorId){
		this.setInternal("AUTHOR_ID" ,AuthorId );
	}


	public String getAuthorId(){
		return (String)this.getInternal("AUTHOR_ID");
	}
	public void setUserAddress(String UserAddress){
		this.setInternal("USER_ADDRESS" ,UserAddress );
	}


	public String getUserAddress(){
		return (String)this.getInternal("USER_ADDRESS");
	}
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setUserNo(String UserNo){
		this.setInternal("USER_NO" ,UserNo );
	}


	public String getUserNo(){
		return (String)this.getInternal("USER_NO");
	}
	public void setMileage(String Mileage){
		this.setInternal("MILEAGE" ,Mileage );
	}


	public String getMileage(){
		return (String)this.getInternal("MILEAGE");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setArriveDate(Date ArriveDate){
		this.setInternal("ARRIVE_DATE" ,ArriveDate );
	}


	public Date getArriveDate(){
		return (Date)this.getInternal("ARRIVE_DATE");
	}
	public void setTrailerCost(String TrailerCost){
		this.setInternal("TRAILER_COST" ,TrailerCost );
	}


	public String getTrailerCost(){
		return (String)this.getInternal("TRAILER_COST");
	}
	public void setVehicleId(String VehicleId){
		this.setInternal("VEHICLE_ID" ,VehicleId );
	}


	public String getVehicleId(){
		return (String)this.getInternal("VEHICLE_ID");
	}
	public void setAuthorType(String AuthorType){
		this.setInternal("AUTHOR_TYPE" ,AuthorType );
	}


	public String getAuthorType(){
		return (String)this.getInternal("AUTHOR_TYPE");
	}
	public void setAuthorNo(String AuthorNo){
		this.setInternal("AUTHOR_NO" ,AuthorNo );
	}


	public String getAuthorNo(){
		return (String)this.getInternal("AUTHOR_NO");
	}
	public void setOutUser(String OutUser){
		this.setInternal("OUT_USER" ,OutUser );
	}


	public String getOutUser(){
		return (String)this.getInternal("OUT_USER");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
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
	public void setTelephone(String Telephone){
		this.setInternal("TELEPHONE" ,Telephone );
	}


	public String getTelephone(){
		return (String)this.getInternal("TELEPHONE");
	}
	public void setLinkMan(String LinkMan){
		this.setInternal("LINK_MAN" ,LinkMan );
	}


	public String getLinkMan(){
		return (String)this.getInternal("LINK_MAN");
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
	public void setLevelCode(String LevelCode){
		this.setInternal("LEVEL_CODE" ,LevelCode );
	}


	public String getLevelCode(){
		return (String)this.getInternal("LEVEL_CODE");
	}
	public void setUserName(String UserName){
		this.setInternal("USER_NAME" ,UserName );
	}


	public String getUserName(){
		return (String)this.getInternal("USER_NAME");
	}
	public void setAuthorStatus(String AuthorStatus){
		this.setInternal("AUTHOR_STATUS" ,AuthorStatus );
	}


	public String getAuthorStatus(){
		return (String)this.getInternal("AUTHOR_STATUS");
	}
	public void setFaultlocation(String Faultlocation){
		this.setInternal("FAULTLOCATION" ,Faultlocation );
	}


	public String getFaultlocation(){
		return (String)this.getInternal("FAULTLOCATION");
	}
	public void setReportDate(Date ReportDate){
		this.setInternal("REPORT_DATE" ,ReportDate );
	}


	public Date getReportDate(){
		return (Date)this.getInternal("REPORT_DATE");
	}
	public void setGoDate(Date GoDate){
		this.setInternal("GO_DATE" ,GoDate );
	}


	public Date getGoDate(){
		return (Date)this.getInternal("GO_DATE");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
}
