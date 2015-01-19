package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class SeBaExcitationCoefficientVO extends BaseVO{
    public SeBaExcitationCoefficientVO(){
    	//�����ֶ���Ϣ
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("ORG_NAME",BaseVO.OP_STRING);
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("END_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("END_DATE", "yyyy-MM-dd");
    	this.addField("COEFFICIENT_RADIO",BaseVO.OP_STRING);
    	this.addField("COEFFICIENT_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("ENGINE",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("START_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("START_DATE", "yyyy-MM-dd");
    	this.addField("MODEL_ID",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("COEFFICIENT_TYPE",BaseVO.OP_STRING);
    	this.addField("ORG_CODE",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("OFFICE_ID",BaseVO.OP_STRING);
		this.bindFieldToSequence("COEFFICIENT_ID","COMMON");
    	this.setVOTableName("SE_BA_EXCITATION_COEFFICIENT");
}
	public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
	}
	public void setOrgName(String OrgName){
		this.setInternal("ORG_NAME" ,OrgName );
	}


	public String getOrgName(){
		return (String)this.getInternal("ORG_NAME");
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
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
	public void setEndDate(Date EndDate){
		this.setInternal("END_DATE" ,EndDate );
	}


	public Date getEndDate(){
		return (Date)this.getInternal("END_DATE");
	}
	public void setCoefficientRadio(String CoefficientRadio){
		this.setInternal("COEFFICIENT_RADIO" ,CoefficientRadio );
	}


	public String getCoefficientRadio(){
		return (String)this.getInternal("COEFFICIENT_RADIO");
	}
	public void setCoefficientId(String CoefficientId){
		this.setInternal("COEFFICIENT_ID" ,CoefficientId );
	}


	public String getCoefficientId(){
		return (String)this.getInternal("COEFFICIENT_ID");
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
	public void setEngine(String Engine){
		this.setInternal("ENGINE" ,Engine );
	}


	public String getEngine(){
		return (String)this.getInternal("ENGINE");
	}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setStartDate(Date StartDate){
		this.setInternal("START_DATE" ,StartDate );
	}


	public Date getStartDate(){
		return (Date)this.getInternal("START_DATE");
	}
	public void setModelId(String ModelId){
		this.setInternal("MODEL_ID" ,ModelId );
	}


	public String getModelId(){
		return (String)this.getInternal("MODEL_ID");
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
	public void setCoefficientType(String CoefficientType){
		this.setInternal("COEFFICIENT_TYPE" ,CoefficientType );
	}


	public String getCoefficientType(){
		return (String)this.getInternal("COEFFICIENT_TYPE");
	}
	public void setOrgCode(String OrgCode){
		this.setInternal("ORG_CODE" ,OrgCode );
	}


	public String getOrgCode(){
		return (String)this.getInternal("ORG_CODE");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setOfficeId(String OfficeId){
		this.setInternal("OFFICE_ID" ,OfficeId );
	}


	public String getOfficeId(){
		return (String)this.getInternal("OFFICE_ID");
	}
}
