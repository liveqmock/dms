package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class SeBuActivityProjectVO extends BaseVO{
    public SeBuActivityProjectVO(){
    	//�����ֶ���Ϣ
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("ACTIVITY_ID",BaseVO.OP_STRING);
    	this.addField("ACTIVITY_NAME",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("AMOUNT",BaseVO.OP_STRING);
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("ACTIVITY_CODE",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("PROJECT_ID",BaseVO.OP_STRING);
    	this.addField("PROJECT_CODE",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("RELATION_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("PROJECT_NAME",BaseVO.OP_STRING);
		this.bindFieldToSequence("RELATION_ID","COMMON");
    	this.setVOTableName("SE_BU_ACTIVITY_PROJECT");
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
	public void setAmount(String Amount){
		this.setInternal("AMOUNT" ,Amount );
	}


	public String getAmount(){
		return (String)this.getInternal("AMOUNT");
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
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setProjectId(String ProjectId){
		this.setInternal("PROJECT_ID" ,ProjectId );
	}


	public String getProjectId(){
		return (String)this.getInternal("PROJECT_ID");
	}
	public void setProjectCode(String ProjectCode){
		this.setInternal("PROJECT_CODE" ,ProjectCode );
	}


	public String getProjectCode(){
		return (String)this.getInternal("PROJECT_CODE");
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
	public void setProjectName(String ProjectName){
		this.setInternal("PROJECT_NAME" ,ProjectName );
	}


	public String getProjectName(){
		return (String)this.getInternal("PROJECT_NAME");
	}
}
