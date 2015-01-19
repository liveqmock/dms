package com.org.dms.vo.service;
import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseVO;

import java.util.Date;
public class SeBuActivityVO extends BaseVO{
    public SeBuActivityVO(){
    	//�����ֶ���Ϣ
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("END_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("END_DATE", "yyyy-MM-dd");
    	this.addField("ACTIVITY_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("ACTIVITY_NAME",BaseVO.OP_STRING);
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("ACTIVITY_CODE",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("ACTIVITY_COSTS",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("IF_FIXCOSTS",BaseVO.OP_STRING);
    	this.addField("IN_ACCOUNT_TYPE",BaseVO.OP_STRING);
    	this.addField("ACTIVITY_TYPE",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("SOLUTION",BaseVO.OP_STRING);
    	this.addField("IF_CLAIM",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("IF_PERSON_CHECK",BaseVO.OP_STRING);
    	this.addField("ACTIVITY_STATUS",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("END_MILEAGE",BaseVO.OP_STRING);
    	this.addField("START_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("START_DATE", "yyyy-MM-dd");
    	this.addField("MANAGE_TYPE",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("BEGIN_MILEAGE",BaseVO.OP_STRING);
		this.bindFieldToSequence("ACTIVITY_ID","COMMON");
		this.bindFieldToDic("ACTIVITY_STATUS", DicConstant.HDZT);
		this.bindFieldToDic("ACTIVITY_TYPE", DicConstant.HDLB);
		this.bindFieldToDic("MANAGE_TYPE", DicConstant.CLFS);
    	this.setVOTableName("SE_BU_ACTIVITY");
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
	public void setEndDate(Date EndDate){
		this.setInternal("END_DATE" ,EndDate );
	}


	public Date getEndDate(){
		return (Date)this.getInternal("END_DATE");
	}
	public void setActivityId(String ActivityId){
		this.setInternal("ACTIVITY_ID" ,ActivityId );
	}


	public String getActivityId(){
		return (String)this.getInternal("ACTIVITY_ID");
	}
	public void setActivityName(String ActivityName){
		this.setInternal("ACTIVITY_NAME" ,ActivityName );
	}


	public String getActivityName(){
		return (String)this.getInternal("ACTIVITY_NAME");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setActivityCode(String ActivityCode){
		this.setInternal("ACTIVITY_CODE" ,ActivityCode );
	}


	public String getActivityCode(){
		return (String)this.getInternal("ACTIVITY_CODE");
	}
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setActivityCosts(String ActivityCosts){
		this.setInternal("ACTIVITY_COSTS" ,ActivityCosts );
	}


	public String getActivityCosts(){
		return (String)this.getInternal("ACTIVITY_COSTS");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setIfFixcosts(String IfFixcosts){
		this.setInternal("IF_FIXCOSTS" ,IfFixcosts );
	}


	public String getIfFixcosts(){
		return (String)this.getInternal("IF_FIXCOSTS");
	}
	public void setInAccountType(String InAccountType){
		this.setInternal("IN_ACCOUNT_TYPE" ,InAccountType );
	}


	public String getInAccountType(){
		return (String)this.getInternal("IN_ACCOUNT_TYPE");
	}
	public void setActivityType(String ActivityType){
		this.setInternal("ACTIVITY_TYPE" ,ActivityType );
	}


	public String getActivityType(){
		return (String)this.getInternal("ACTIVITY_TYPE");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
	public void setSolution(String Solution){
		this.setInternal("SOLUTION" ,Solution );
	}


	public String getSolution(){
		return (String)this.getInternal("SOLUTION");
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
	public void setIfPersonCheck(String IfPersonCheck){
		this.setInternal("IF_PERSON_CHECK" ,IfPersonCheck );
	}


	public String getIfPersonCheck(){
		return (String)this.getInternal("IF_PERSON_CHECK");
	}
	public void setActivityStatus(String ActivityStatus){
		this.setInternal("ACTIVITY_STATUS" ,ActivityStatus );
	}


	public String getActivityStatus(){
		return (String)this.getInternal("ACTIVITY_STATUS");
	}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setEndMileage(String EndMileage){
		this.setInternal("END_MILEAGE" ,EndMileage );
	}


	public String getEndMileage(){
		return (String)this.getInternal("END_MILEAGE");
	}
	public void setStartDate(Date StartDate){
		this.setInternal("START_DATE" ,StartDate );
	}


	public Date getStartDate(){
		return (Date)this.getInternal("START_DATE");
	}
	public void setManageType(String ManageType){
		this.setInternal("MANAGE_TYPE" ,ManageType );
	}


	public String getManageType(){
		return (String)this.getInternal("MANAGE_TYPE");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setBeginMileage(String BeginMileage){
		this.setInternal("BEGIN_MILEAGE" ,BeginMileage );
	}


	public String getBeginMileage(){
		return (String)this.getInternal("BEGIN_MILEAGE");
	}
}
