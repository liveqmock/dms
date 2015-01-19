package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class SeBuPreAuthorCheckVO extends BaseVO{
    public SeBuPreAuthorCheckVO(){
    	//�����ֶ���Ϣ
    	this.addField("CHECK_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("CHECK_USER",BaseVO.OP_STRING);
    	this.addField("CHECK_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("CHECK_DATE", "yyyy-MM-dd");
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("AUTHOR_ID",BaseVO.OP_STRING);
    	this.addField("CHECK_RESULT",BaseVO.OP_STRING);
    	this.addField("CHECK_TYPE",BaseVO.OP_STRING);
    	this.addField("REMAKS",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
		this.bindFieldToSequence("CHECK_ID","COMMON");
    	this.setVOTableName("SE_BU_PRE_AUTHOR_CHECK");
}
	public void setCheckId(String CheckId){
		this.setInternal("CHECK_ID" ,CheckId );
	}


	public String getCheckId(){
		return (String)this.getInternal("CHECK_ID");
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
	public void setCheckUser(String CheckUser){
		this.setInternal("CHECK_USER" ,CheckUser );
	}


	public String getCheckUser(){
		return (String)this.getInternal("CHECK_USER");
	}
	public void setCheckDate(Date CheckDate){
		this.setInternal("CHECK_DATE" ,CheckDate );
	}


	public Date getCheckDate(){
		return (Date)this.getInternal("CHECK_DATE");
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
	public void setCheckResult(String CheckResult){
		this.setInternal("CHECK_RESULT" ,CheckResult );
	}


	public String getCheckResult(){
		return (String)this.getInternal("CHECK_RESULT");
	}
	public void setCheckType(String CheckType){
		this.setInternal("CHECK_TYPE" ,CheckType );
	}


	public String getCheckType(){
		return (String)this.getInternal("CHECK_TYPE");
	}
	public void setRemaks(String Remaks){
		this.setInternal("REMAKS" ,Remaks );
	}


	public String getRemaks(){
		return (String)this.getInternal("REMAKS");
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
}