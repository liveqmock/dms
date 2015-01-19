package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class SeBuActivityVehageVO extends BaseVO{
    public SeBuActivityVehageVO(){
    	//�����ֶ���Ϣ
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("END_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("END_DATE", "yyyy-MM-dd");
    	this.addField("ACTIVITY_ID",BaseVO.OP_STRING);
    	this.addField("ACTIVITY_NAME",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("ACTIVITY_CODE",BaseVO.OP_STRING);
    	this.addField("VEHAGE_TYPE",BaseVO.OP_STRING);
    	this.addField("START_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("START_DATE", "yyyy-MM-dd");
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("RELATION_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
		this.bindFieldToSequence("RELATION_ID","COMMON");
    	this.setVOTableName("SE_BU_ACTIVITY_VEHAGE");
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
	public void setVehageType(String VehageType){
		this.setInternal("VEHAGE_TYPE" ,VehageType );
	}


	public String getVehageType(){
		return (String)this.getInternal("VEHAGE_TYPE");
	}
	public void setStartDate(Date StartDate){
		this.setInternal("START_DATE" ,StartDate );
	}


	public Date getStartDate(){
		return (Date)this.getInternal("START_DATE");
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
