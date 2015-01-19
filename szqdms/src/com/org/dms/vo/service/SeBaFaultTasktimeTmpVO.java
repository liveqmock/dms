package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class SeBaFaultTasktimeTmpVO extends BaseVO{
    public SeBaFaultTasktimeTmpVO(){
    	//�����ֶ���Ϣ
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("AMOUNT_ID",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("TIME_NAME",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("FAULT_PATTERN_NAME",BaseVO.OP_STRING);
    	this.addField("FAULT_PATTERN_CODE",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("STATUS_NA",BaseVO.OP_STRING);
    	this.addField("USER_ACCOUNT",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("ROW_NUM",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("TIME_CODE",BaseVO.OP_STRING);
    	this.addField("TMP_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("PATTERN_ID",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
		this.bindFieldToSequence("TMP_ID","COMMON");
    	this.setVOTableName("SE_BA_FAULT_TASKTIME_TMP");
}
	public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
	}
	public void setAmountId(String AmountId){
		this.setInternal("AMOUNT_ID" ,AmountId );
	}


	public String getAmountId(){
		return (String)this.getInternal("AMOUNT_ID");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setTimeName(String TimeName){
		this.setInternal("TIME_NAME" ,TimeName );
	}


	public String getTimeName(){
		return (String)this.getInternal("TIME_NAME");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
	public void setFaultPatternName(String FaultPatternName){
		this.setInternal("FAULT_PATTERN_NAME" ,FaultPatternName );
	}


	public String getFaultPatternName(){
		return (String)this.getInternal("FAULT_PATTERN_NAME");
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
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setRowNum(String RowNum){
		this.setInternal("ROW_NUM" ,RowNum );
	}


	public String getRowNum(){
		return (String)this.getInternal("ROW_NUM");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setTimeCode(String TimeCode){
		this.setInternal("TIME_CODE" ,TimeCode );
	}


	public String getTimeCode(){
		return (String)this.getInternal("TIME_CODE");
	}
	public void setTmpId(String TmpId){
		this.setInternal("TMP_ID" ,TmpId );
	}


	public String getTmpId(){
		return (String)this.getInternal("TMP_ID");
	}
	public void setPatternId(String PatternId){
		this.setInternal("PATTERN_ID" ,PatternId );
	}


	public String getPatternId(){
		return (String)this.getInternal("PATTERN_ID");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setStatusNa(String StatusNa){
		this.setInternal("STATUS_NA" ,StatusNa );
	}


	public String getStatusNa(){
		return (String)this.getInternal("STATUS_NA");
	}
}
