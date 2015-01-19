package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class SeBuActivityModelsVO extends BaseVO{
    public SeBuActivityModelsVO(){
    	//�����ֶ���Ϣ
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("MODELS_NAME",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("ACTIVITY_ID",BaseVO.OP_STRING);
    	this.addField("ACTIVITY_NAME",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("ACTIVITY_CODE",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("MODELS_ID",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("MODELS_CODE",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("RELATION_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
		this.bindFieldToSequence("RELATION_ID","COMMON");
    	this.setVOTableName("SE_BU_ACTIVITY_MODELS");
}
	public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
	}
	public void setModelsName(String ModelsName){
		this.setInternal("MODELS_NAME" ,ModelsName );
	}


	public String getModelsName(){
		return (String)this.getInternal("MODELS_NAME");
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
	public void setActivityCode(String ActivityCode){
		this.setInternal("ACTIVITY_CODE" ,ActivityCode );
	}


	public String getActivityCode(){
		return (String)this.getInternal("ACTIVITY_CODE");
	}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setModelsId(String ModelsId){
		this.setInternal("MODELS_ID" ,ModelsId );
	}


	public String getModelsId(){
		return (String)this.getInternal("MODELS_ID");
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
	public void setRelationId(String RelationId){
		this.setInternal("RELATION_ID" ,RelationId );
	}


	public String getRelationId(){
		return (String)this.getInternal("RELATION_ID");
	}
}
