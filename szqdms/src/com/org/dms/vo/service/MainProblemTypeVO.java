package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class MainProblemTypeVO extends BaseVO{
    public MainProblemTypeVO(){
    	//设置字段信息
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("PROBLEM_CODE",BaseVO.OP_STRING);
    	this.addField("ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("PROBLEM_NAME",BaseVO.OP_STRING);
    	this.addField("TYPE_STATUS",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
		this.bindFieldToSequence("ID","COMMON");
    	this.setVOTableName("MAIN_PROBLEM_TYPE");
		//设置字典类型定义
		this.bindFieldToDic("TYPE_STATUS","YXBS");//有效标识
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
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
	public void setProblemCode(String ProblemCode){
		this.setInternal("PROBLEM_CODE" ,ProblemCode );
	}


	public String getProblemCode(){
		return (String)this.getInternal("PROBLEM_CODE");
	}
	public void setId(String Id){
		this.setInternal("ID" ,Id );
	}


	public String getId(){
		return (String)this.getInternal("ID");
	}
	public void setProblemName(String ProblemName){
		this.setInternal("PROBLEM_NAME" ,ProblemName );
	}


	public String getProblemName(){
		return (String)this.getInternal("PROBLEM_NAME");
	}
	public void setTypeStatus(String TypeStatus){
		this.setInternal("TYPE_STATUS" ,TypeStatus );
	}


	public String getTypeStatus(){
		return (String)this.getInternal("TYPE_STATUS");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
}
