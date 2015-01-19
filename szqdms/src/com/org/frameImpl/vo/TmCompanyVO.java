package com.org.frameImpl.vo;
import com.org.frameImpl.Constant;
import com.org.framework.base.BaseVO;

import java.util.Date;
public class TmCompanyVO extends BaseVO{
	private static final long serialVersionUID = 1L;

	public TmCompanyVO(){
    	//设置字段信息
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("COMPANY_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("SNAME",BaseVO.OP_STRING);
    	this.addField("ADDRESS",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("CONTACT",BaseVO.OP_STRING);
    	this.addField("COMPANY_TYPE",BaseVO.OP_STRING);
    	this.addField("CNAME",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("INVOICE_NAME",BaseVO.OP_STRING);
    	this.addField("LEGAL_PERSON",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("CODE",BaseVO.OP_STRING);
		this.bindFieldToSequence("COMPANY_ID","ORG_S");
		//公司类型字典
		//this.bindFieldToDic("COMPANY_TYPE", DicConstant.GSLX);
		//设置有效标识
		this.bindFieldToDic("STATUS", Constant.YXBS);
    	this.setVOTableName("TM_COMPANY");
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
	public void setSname(String Sname){
		this.setInternal("SNAME" ,Sname );
	}


	public String getSname(){
		return (String)this.getInternal("SNAME");
	}
	public void setAddress(String Address){
		this.setInternal("ADDRESS" ,Address );
	}


	public String getAddress(){
		return (String)this.getInternal("ADDRESS");
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
	public void setContact(String Contact){
		this.setInternal("CONTACT" ,Contact );
	}


	public String getContact(){
		return (String)this.getInternal("CONTACT");
	}
	public void setCompanyType(String CompanyType){
		this.setInternal("COMPANY_TYPE" ,CompanyType );
	}


	public String getCompanyType(){
		return (String)this.getInternal("COMPANY_TYPE");
	}
	public void setCname(String Cname){
		this.setInternal("CNAME" ,Cname );
	}


	public String getCname(){
		return (String)this.getInternal("CNAME");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setInvoiceName(String InvoiceName){
		this.setInternal("INVOICE_NAME" ,InvoiceName );
	}


	public String getInvoiceName(){
		return (String)this.getInternal("INVOICE_NAME");
	}
	public void setLegalPerson(String LegalPerson){
		this.setInternal("LEGAL_PERSON" ,LegalPerson );
	}


	public String getLegalPerson(){
		return (String)this.getInternal("LEGAL_PERSON");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setCode(String Code){
		this.setInternal("CODE" ,Code );
	}


	public String getCode(){
		return (String)this.getInternal("CODE");
	}
}
