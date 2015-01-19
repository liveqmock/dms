package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class SeBuWorkDispatchVO extends BaseVO{
    public SeBuWorkDispatchVO(){
    	//�����ֶ���Ϣ
    	this.addField("USER_ID",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("WORK_ID",BaseVO.OP_STRING);
    	this.addField("WORK_NO",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("IF_OUT",BaseVO.OP_STRING);
    	this.addField("DISPATCH_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("LICENSE_PLATE",BaseVO.OP_STRING);
    	this.addField("USER_MOBIL",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("USER_NAME",BaseVO.OP_STRING);
    	this.addField("IF_VEHICLE",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("VEHICLE_ID",BaseVO.OP_STRING);
		this.bindFieldToSequence("DISPATCH_ID","COMMON");
    	this.setVOTableName("SE_BU_WORK_DISPATCH");
}
	public void setUserId(String UserId){
		this.setInternal("USER_ID" ,UserId );
	}


	public String getUserId(){
		return (String)this.getInternal("USER_ID");
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
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
	public void setWorkId(String WorkId){
		this.setInternal("WORK_ID" ,WorkId );
	}


	public String getWorkId(){
		return (String)this.getInternal("WORK_ID");
	}
	public void setWorkNo(String WorkNo){
		this.setInternal("WORK_NO" ,WorkNo );
	}


	public String getWorkNo(){
		return (String)this.getInternal("WORK_NO");
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
	public void setIfOut(String IfOut){
		this.setInternal("IF_OUT" ,IfOut );
	}


	public String getIfOut(){
		return (String)this.getInternal("IF_OUT");
	}
	public void setDispatchId(String DispatchId){
		this.setInternal("DISPATCH_ID" ,DispatchId );
	}


	public String getDispatchId(){
		return (String)this.getInternal("DISPATCH_ID");
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
	public void setUserMobil(String UserMobil){
		this.setInternal("USER_MOBIL" ,UserMobil );
	}


	public String getUserMobil(){
		return (String)this.getInternal("USER_MOBIL");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setUserName(String UserName){
		this.setInternal("USER_NAME" ,UserName );
	}


	public String getUserName(){
		return (String)this.getInternal("USER_NAME");
	}
	public void setIfVehicle(String IfVehicle){
		this.setInternal("IF_VEHICLE" ,IfVehicle );
	}


	public String getIfVehicle(){
		return (String)this.getInternal("IF_VEHICLE");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setVehicleId(String VehicleId){
		this.setInternal("VEHICLE_ID" ,VehicleId );
	}


	public String getVehicleId(){
		return (String)this.getInternal("VEHICLE_ID");
	}
}
