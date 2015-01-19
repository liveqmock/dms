package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class SeBuPreAuthorProjectVO extends BaseVO{
    public SeBuPreAuthorProjectVO(){
    	//�����ֶ���Ϣ
    	this.addField("PROJECT_TYPE",BaseVO.OP_STRING);
    	this.addField("AMOUNT_ID",BaseVO.OP_STRING);
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("TIME_NAME",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("AMOUNT_SET",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("AUTHOR_ID",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("PROJECT_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("TIME_CODE",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
		this.bindFieldToSequence("PROJECT_ID","COMMON");
    	this.setVOTableName("SE_BU_PRE_AUTHOR_PROJECT");
}
	public void setProjectType(String ProjectType){
		this.setInternal("PROJECT_TYPE" ,ProjectType );
	}


	public String getProjectType(){
		return (String)this.getInternal("PROJECT_TYPE");
	}
	public void setAmountId(String AmountId){
		this.setInternal("AMOUNT_ID" ,AmountId );
	}


	public String getAmountId(){
		return (String)this.getInternal("AMOUNT_ID");
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
	public void setAmountSet(String AmountSet){
		this.setInternal("AMOUNT_SET" ,AmountSet );
	}


	public String getAmountSet(){
		return (String)this.getInternal("AMOUNT_SET");
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
	public void setAuthorId(String AuthorId){
		this.setInternal("AUTHOR_ID" ,AuthorId );
	}


	public String getAuthorId(){
		return (String)this.getInternal("AUTHOR_ID");
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
	public void setTimeCode(String TimeCode){
		this.setInternal("TIME_CODE" ,TimeCode );
	}


	public String getTimeCode(){
		return (String)this.getInternal("TIME_CODE");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
}
