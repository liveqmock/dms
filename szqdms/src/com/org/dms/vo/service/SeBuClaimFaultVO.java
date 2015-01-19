package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
@SuppressWarnings("serial")
public class SeBuClaimFaultVO extends BaseVO{
    public SeBuClaimFaultVO(){
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("FAULT_NAME",BaseVO.OP_STRING);
    	this.addField("FAULT_CODE",BaseVO.OP_STRING);
    	this.addField("STAR_LEVEL_UPRICE",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("WORK_TIME_UPRICE",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("WORK_TIME",BaseVO.OP_STRING);
    	this.addField("ENCOURAGE_UPRICE",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("CLAIM_ID",BaseVO.OP_STRING);
    	this.addField("WORK",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("FAULT_ID",BaseVO.OP_STRING);
    	this.addField("CLAIM_DTL_ID",BaseVO.OP_STRING);
    	this.addField("WORK_COSTS",BaseVO.OP_STRING);
		this.bindFieldToSequence("CLAIM_DTL_ID?TABLENAME=SE_BU_CLAIM_FAULT_PART","COMMON");
    	this.setVOTableName("SE_BU_CLAIM_FAULT");
}
	public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
	}
	public void setFaultName(String FaultName){
		this.setInternal("FAULT_NAME" ,FaultName );
	}


	public String getFaultName(){
		return (String)this.getInternal("FAULT_NAME");
	}
	public void setFaultCode(String FaultCode){
		this.setInternal("FAULT_CODE" ,FaultCode );
	}


	public String getFaultCode(){
		return (String)this.getInternal("FAULT_CODE");
	}
	public void setStarLevelUprice(String StarLevelUprice){
		this.setInternal("STAR_LEVEL_UPRICE" ,StarLevelUprice );
	}


	public String getStarLevelUprice(){
		return (String)this.getInternal("STAR_LEVEL_UPRICE");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setWorkTimeUprice(String WorkTimeUprice){
		this.setInternal("WORK_TIME_UPRICE" ,WorkTimeUprice );
	}


	public String getWorkTimeUprice(){
		return (String)this.getInternal("WORK_TIME_UPRICE");
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
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setWorkTime(String WorkTime){
		this.setInternal("WORK_TIME" ,WorkTime );
	}


	public String getWorkTime(){
		return (String)this.getInternal("WORK_TIME");
	}
	public void setEncourageUprice(String EncourageUprice){
		this.setInternal("ENCOURAGE_UPRICE" ,EncourageUprice );
	}


	public String getEncourageUprice(){
		return (String)this.getInternal("ENCOURAGE_UPRICE");
	}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
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
	public void setClaimId(String ClaimId){
		this.setInternal("CLAIM_ID" ,ClaimId );
	}


	public String getClaimId(){
		return (String)this.getInternal("CLAIM_ID");
	}
	public void setWork(String Work){
		this.setInternal("WORK" ,Work );
	}


	public String getWork(){
		return (String)this.getInternal("WORK");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setFaultId(String FaultId){
		this.setInternal("FAULT_ID" ,FaultId );
	}


	public String getFaultId(){
		return (String)this.getInternal("FAULT_ID");
	}
	public void setClaimDtlId(String ClaimDtlId){
		this.setInternal("CLAIM_DTL_ID" ,ClaimDtlId );
	}


	public String getClaimDtlId(){
		return (String)this.getInternal("CLAIM_DTL_ID");
	}
	public void setWorkCosts(String WorkCosts){
		this.setInternal("WORK_COSTS" ,WorkCosts );
	}


	public String getWorkCosts(){
		return (String)this.getInternal("WORK_COSTS");
	}
}
