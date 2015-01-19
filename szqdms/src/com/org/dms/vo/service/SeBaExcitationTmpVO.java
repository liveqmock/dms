package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class SeBaExcitationTmpVO extends BaseVO{
    public SeBaExcitationTmpVO(){
    	//�����ֶ���Ϣ
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("ORG_NAME",BaseVO.OP_STRING);
    	this.addField("END_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("END_DATE", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("STATUS_NA",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("MODELS_CODE",BaseVO.OP_STRING);
    	this.addField("ROW_NUM",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("COEFFICIENT_TYPE",BaseVO.OP_STRING);
    	this.addField("TYPE_CODE",BaseVO.OP_STRING);
    	this.addField("OFFICE_ID",BaseVO.OP_STRING);
    	this.addField("MODELS_NAME",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("COEFFICIENT_RADIO",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("ENGINE",BaseVO.OP_STRING);
    	this.addField("USER_ACCOUNT",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("TYPE_NAME",BaseVO.OP_STRING);
    	this.addField("MODELS_ID",BaseVO.OP_STRING);
    	this.addField("START_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("START_DATE", "yyyy-MM-dd");
    	this.addField("ORG_CODE",BaseVO.OP_STRING);
    	this.addField("COEFFICIENT_TYPE_NA",BaseVO.OP_STRING);
    	this.addField("TMP_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
		this.bindFieldToSequence("TMP_ID","COMMON");
    	this.setVOTableName("SE_BA_EXCITATION_TMP");
}
	public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
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
	public void setOrgName(String OrgName){
		this.setInternal("ORG_NAME" ,OrgName );
	}


	public String getOrgName(){
		return (String)this.getInternal("ORG_NAME");
	}
	public void setEndDate(Date EndDate){
		this.setInternal("END_DATE" ,EndDate );
	}


	public Date getEndDate(){
		return (Date)this.getInternal("END_DATE");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setModelsCode(String ModelsCode){
		this.setInternal("MODELS_CODE" ,ModelsCode );
	}


	public String getModelsCode(){
		return (String)this.getInternal("MODELS_CODE");
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
	public void setCoefficientType(String CoefficientType){
		this.setInternal("COEFFICIENT_TYPE" ,CoefficientType );
	}


	public String getCoefficientType(){
		return (String)this.getInternal("COEFFICIENT_TYPE");
	}
	public void setTypeCode(String TypeCode){
		this.setInternal("TYPE_CODE" ,TypeCode );
	}


	public String getTypeCode(){
		return (String)this.getInternal("TYPE_CODE");
	}
	public void setOfficeId(String OfficeId){
		this.setInternal("OFFICE_ID" ,OfficeId );
	}


	public String getOfficeId(){
		return (String)this.getInternal("OFFICE_ID");
	}
	public void setModelsName(String ModelsName){
		this.setInternal("MODELS_NAME" ,ModelsName );
	}


	public String getModelsName(){
		return (String)this.getInternal("MODELS_NAME");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
	public void setCoefficientRadio(String CoefficientRadio){
		this.setInternal("COEFFICIENT_RADIO" ,CoefficientRadio );
	}


	public String getCoefficientRadio(){
		return (String)this.getInternal("COEFFICIENT_RADIO");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setEngine(String Engine){
		this.setInternal("ENGINE" ,Engine );
	}


	public String getEngine(){
		return (String)this.getInternal("ENGINE");
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
	public void setTypeName(String TypeName){
		this.setInternal("TYPE_NAME" ,TypeName );
	}


	public String getTypeName(){
		return (String)this.getInternal("TYPE_NAME");
	}
	public void setModelsId(String ModelsId){
		this.setInternal("MODELS_ID" ,ModelsId );
	}


	public String getModelsId(){
		return (String)this.getInternal("MODELS_ID");
	}
	public void setStartDate(Date StartDate){
		this.setInternal("START_DATE" ,StartDate );
	}


	public Date getStartDate(){
		return (Date)this.getInternal("START_DATE");
	}
	public void setOrgCode(String OrgCode){
		this.setInternal("ORG_CODE" ,OrgCode );
	}


	public String getOrgCode(){
		return (String)this.getInternal("ORG_CODE");
	}
	public void setCoefficientTypeNa(String CoefficientTypeNa){
		this.setInternal("COEFFICIENT_TYPE_NA" ,CoefficientTypeNa );
	}


	public String getCoefficientTypeNa(){
		return (String)this.getInternal("COEFFICIENT_TYPE_NA");
	}
	public void setTmpId(String TmpId){
		this.setInternal("TMP_ID" ,TmpId );
	}


	public String getTmpId(){
		return (String)this.getInternal("TMP_ID");
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
