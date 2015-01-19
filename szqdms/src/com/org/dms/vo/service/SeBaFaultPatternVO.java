package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class SeBaFaultPatternVO extends BaseVO{
    public SeBaFaultPatternVO(){
    	//设置字段信息
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("FAULT_NAME",BaseVO.OP_STRING);
    	this.addField("FAULT_CODE",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("SEVERITY",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("POSITION_NAME",BaseVO.OP_STRING);
    	this.addField("FAULT_PATTERN_NAME",BaseVO.OP_STRING);
    	this.addField("POSITION_CODE",BaseVO.OP_STRING);
    	this.addField("CODE_ID",BaseVO.OP_STRING);
    	this.addField("FAULT_PATTERN_CODE",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("PATTERN_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("POSITION_ID",BaseVO.OP_STRING);
		this.bindFieldToSequence("PATTERN_ID","COMMON");
    	this.setVOTableName("SE_BA_FAULT_PATTERN");
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
	public void setSeverity(String Severity){
		this.setInternal("SEVERITY" ,Severity );
	}


	public String getSeverity(){
		return (String)this.getInternal("SEVERITY");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
	public void setPositionName(String PositionName){
		this.setInternal("POSITION_NAME" ,PositionName );
	}


	public String getPositionName(){
		return (String)this.getInternal("POSITION_NAME");
	}
	public void setFaultPatternName(String FaultPatternName){
		this.setInternal("FAULT_PATTERN_NAME" ,FaultPatternName );
	}


	public String getFaultPatternName(){
		return (String)this.getInternal("FAULT_PATTERN_NAME");
	}
	public void setPositionCode(String PositionCode){
		this.setInternal("POSITION_CODE" ,PositionCode );
	}


	public String getPositionCode(){
		return (String)this.getInternal("POSITION_CODE");
	}
	public void setCodeId(String CodeId){
		this.setInternal("CODE_ID" ,CodeId );
	}


	public String getCodeId(){
		return (String)this.getInternal("CODE_ID");
	}
	public void setFaultPatternCode(String FaultPatternCode){
		this.setInternal("FAULT_PATTERN_CODE" ,FaultPatternCode );
	}


	public String getFaultPatternCode(){
		return (String)this.getInternal("FAULT_PATTERN_CODE");
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
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setPatternId(String PatternId){
		this.setInternal("PATTERN_ID" ,PatternId );
	}


	public String getPatternId(){
		return (String)this.getInternal("PATTERN_ID");
	}
	public void setPositionId(String PositionId){
		this.setInternal("POSITION_ID" ,PositionId );
	}


	public String getPositionId(){
		return (String)this.getInternal("POSITION_ID");
	}
}
