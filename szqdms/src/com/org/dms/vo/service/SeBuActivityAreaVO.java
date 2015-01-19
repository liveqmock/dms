package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class SeBuActivityAreaVO extends BaseVO{
    public SeBuActivityAreaVO(){
    	//�����ֶ���Ϣ
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("ORG_NAME",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("AREA_NAME",BaseVO.OP_STRING);
    	this.addField("AREA_CODE",BaseVO.OP_STRING);
    	this.addField("ACTIVITY_ID",BaseVO.OP_STRING);
    	this.addField("ORG_CODE",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("AREA_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("OFFICE_ID",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
		this.bindFieldToSequence("AREA_ID","COMMON");
    	this.setVOTableName("SE_BU_ACTIVITY_AREA");
}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setOrgName(String OrgName){
		this.setInternal("ORG_NAME" ,OrgName );
	}


	public String getOrgName(){
		return (String)this.getInternal("ORG_NAME");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setAreaName(String AreaName){
		this.setInternal("AREA_NAME" ,AreaName );
	}


	public String getAreaName(){
		return (String)this.getInternal("AREA_NAME");
	}
	public void setAreaCode(String AreaCode){
		this.setInternal("AREA_CODE" ,AreaCode );
	}


	public String getAreaCode(){
		return (String)this.getInternal("AREA_CODE");
	}
	public void setActivityId(String ActivityId){
		this.setInternal("ACTIVITY_ID" ,ActivityId );
	}


	public String getActivityId(){
		return (String)this.getInternal("ACTIVITY_ID");
	}
	public void setOrgCode(String OrgCode){
		this.setInternal("ORG_CODE" ,OrgCode );
	}


	public String getOrgCode(){
		return (String)this.getInternal("ORG_CODE");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setAreaId(String AreaId){
		this.setInternal("AREA_ID" ,AreaId );
	}


	public String getAreaId(){
		return (String)this.getInternal("AREA_ID");
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
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
}
