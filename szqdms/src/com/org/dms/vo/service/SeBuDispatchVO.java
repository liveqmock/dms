package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class SeBuDispatchVO extends BaseVO{
    public SeBuDispatchVO(){
    	//设置字段信息
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("VIN",BaseVO.OP_STRING);
    	this.addField("APPLY_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("BUY_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
    	this.addField("DISPATCH_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("PHONE",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("USER_ADDRESS",BaseVO.OP_STRING);
    	this.addField("MILEAGE",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("FAULT_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("FAULT_DATE", "yyyy-MM-dd");
    	this.addField("VEHICLE_ID",BaseVO.OP_STRING);
    	this.addField("RECEIPT_PHONE",BaseVO.OP_STRING);
    	this.addField("DISPATCH_NO",BaseVO.OP_STRING);
    	this.addField("RECEIPT_USER",BaseVO.OP_STRING);
    	this.addField("FAULT_ANALYSE",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("IF_CLAIM",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("LINK_MAN",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("USER_NAME",BaseVO.OP_STRING);
    	this.addField("RECEIPT_ADDRESS",BaseVO.OP_STRING);
    	this.addField("CHECK_REARKS",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("DISPATCH_STATUS",BaseVO.OP_STRING);
		this.bindFieldToSequence("DISPATCH_ID","COMMON");
    	this.setVOTableName("SE_BU_DISPATCH");
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
	public void setApplyDate(Date ApplyDate){
		this.setInternal("APPLY_DATE" ,ApplyDate );
	}


	public Date getApplyDate(){
		return (Date)this.getInternal("APPLY_DATE");
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
	public void setDispatchId(String DispatchId){
		this.setInternal("DISPATCH_ID" ,DispatchId );
	}


	public String getDispatchId(){
		return (String)this.getInternal("DISPATCH_ID");
	}
	public void setPhone(String Phone){
		this.setInternal("PHONE" ,Phone );
	}


	public String getPhone(){
		return (String)this.getInternal("PHONE");
	}
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setUserAddress(String UserAddress){
		this.setInternal("USER_ADDRESS" ,UserAddress );
	}


	public String getUserAddress(){
		return (String)this.getInternal("USER_ADDRESS");
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
	public void setFaultDate(Date FaultDate){
		this.setInternal("FAULT_DATE" ,FaultDate );
	}


	public Date getFaultDate(){
		return (Date)this.getInternal("FAULT_DATE");
	}
	public void setVehicleId(String VehicleId){
		this.setInternal("VEHICLE_ID" ,VehicleId );
	}


	public String getVehicleId(){
		return (String)this.getInternal("VEHICLE_ID");
	}
	public void setReceiptPhone(String ReceiptPhone){
		this.setInternal("RECEIPT_PHONE" ,ReceiptPhone );
	}


	public String getReceiptPhone(){
		return (String)this.getInternal("RECEIPT_PHONE");
	}
	public void setDispatchNo(String DispatchNo){
		this.setInternal("DISPATCH_NO" ,DispatchNo );
	}


	public String getDispatchNo(){
		return (String)this.getInternal("DISPATCH_NO");
	}
	public void setReceiptUser(String ReceiptUser){
		this.setInternal("RECEIPT_USER" ,ReceiptUser );
	}


	public String getReceiptUser(){
		return (String)this.getInternal("RECEIPT_USER");
	}
	public void setFaultAnalyse(String FaultAnalyse){
		this.setInternal("FAULT_ANALYSE" ,FaultAnalyse );
	}


	public String getFaultAnalyse(){
		return (String)this.getInternal("FAULT_ANALYSE");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
	public void setIfClaim(String IfClaim){
		this.setInternal("IF_CLAIM" ,IfClaim );
	}


	public String getIfClaim(){
		return (String)this.getInternal("IF_CLAIM");
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
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setUserName(String UserName){
		this.setInternal("USER_NAME" ,UserName );
	}


	public String getUserName(){
		return (String)this.getInternal("USER_NAME");
	}
	public void setReceiptAddress(String ReceiptAddress){
		this.setInternal("RECEIPT_ADDRESS" ,ReceiptAddress );
	}


	public String getReceiptAddress(){
		return (String)this.getInternal("RECEIPT_ADDRESS");
	}
	public void setCheckRearks(String CheckRearks){
		this.setInternal("CHECK_REARKS" ,CheckRearks );
	}


	public String getCheckRearks(){
		return (String)this.getInternal("CHECK_REARKS");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setDispatchStatus(String DispatchStatus){
		this.setInternal("DISPATCH_STATUS" ,DispatchStatus );
	}


	public String getDispatchStatus(){
		return (String)this.getInternal("DISPATCH_STATUS");
	}
}
