package com.org.frameImpl.vo;
import com.org.frameImpl.Constant;
import com.org.framework.base.BaseVO;

import java.util.Date;
public class TmRoleVO extends BaseVO{
	private static final long serialVersionUID = 5419027246521811217L;

	public TmRoleVO(){
    	//设置字段信息
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("ROLE_REMARK",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("ROLE_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("RNAME",BaseVO.OP_STRING);
    	this.addField("ROLE_TYPE",BaseVO.OP_STRING);
    	this.addField("LEVEL_CODE",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("NAME_EN",BaseVO.OP_STRING);
    	this.addField("CODE",BaseVO.OP_STRING);
		this.bindFieldToSequence("ROLE_ID","ROLE_S");
		//设置字典类型定义
		this.bindFieldToDic("STATUS",Constant.YXBS);//有效标识   
		this.bindFieldToOrgDept("ORG_ID","0");//部门简称
		this.bindFieldToDic("LEVEL_CODE", "JGJB");
    	this.setVOTableName("TM_ROLE");
}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setRoleRemark(String RoleRemark){
		this.setInternal("ROLE_REMARK" ,RoleRemark );
	}


	public String getRoleRemark(){
		return (String)this.getInternal("ROLE_REMARK");
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
	public void setRoleId(String RoleId){
		this.setInternal("ROLE_ID" ,RoleId );
	}


	public String getRoleId(){
		return (String)this.getInternal("ROLE_ID");
	}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setRname(String Rname){
		this.setInternal("RNAME" ,Rname );
	}


	public String getRname(){
		return (String)this.getInternal("RNAME");
	}
	public void setRoleType(String RoleType){
		this.setInternal("ROLE_TYPE" ,RoleType );
	}


	public String getRoleType(){
		return (String)this.getInternal("ROLE_TYPE");
	}
	public void setLevelCode(String LevelCode){
		this.setInternal("LEVEL_CODE" ,LevelCode );
	}


	public String getLevelCode(){
		return (String)this.getInternal("LEVEL_CODE");
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
	public void setNameEn(String NameEn){
		this.setInternal("NAME_EN" ,NameEn );
	}


	public String getNameEn(){
		return (String)this.getInternal("NAME_EN");
	}
	public void setCode(String Code){
		this.setInternal("CODE" ,Code );
	}


	public String getCode(){
		return (String)this.getInternal("CODE");
	}
}
