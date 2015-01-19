package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuCreditLineVO extends BaseVO{
    public PtBuCreditLineVO(){
    	//设置字段信息
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("CREDIT_TYPE",BaseVO.OP_STRING);
    	this.addField("ORG_NAME",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("END_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("END_DATE", "yyyy-MM-dd");
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("IS_SETTLE",BaseVO.OP_STRING);
    	this.addField("NOW_LIMIT",BaseVO.OP_STRING);
    	this.addField("LINE_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("BEGIN_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("BEGIN_DATE", "yyyy-MM-dd");
    	this.addField("ORG_CODE",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("FIRST_USE_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("FIRST_USE_DATE", "yyyy-MM-dd");
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("REPAY_DATE",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
		this.bindFieldToSequence("LINE_ID","COMMON");
    	this.setVOTableName("PT_BU_CREDIT_LINE");
}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setCreditType(String CreditType){
		this.setInternal("CREDIT_TYPE" ,CreditType );
	}


	public String getCreditType(){
		return (String)this.getInternal("CREDIT_TYPE");
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
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setEndDate(Date EndDate){
		this.setInternal("END_DATE" ,EndDate );
	}


	public Date getEndDate(){
		return (Date)this.getInternal("END_DATE");
	}
	public void setRemarks(String Remarks){
		this.setInternal("REMARKS" ,Remarks );
	}


	public String getRemarks(){
		return (String)this.getInternal("REMARKS");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setIsSettle(String IsSettle){
		this.setInternal("IS_SETTLE" ,IsSettle );
	}


	public String getIsSettle(){
		return (String)this.getInternal("IS_SETTLE");
	}
	public void setNowLimit(String NowLimit){
		this.setInternal("NOW_LIMIT" ,NowLimit );
	}


	public String getNowLimit(){
		return (String)this.getInternal("NOW_LIMIT");
	}
	public void setLineId(String LineId){
		this.setInternal("LINE_ID" ,LineId );
	}


	public String getLineId(){
		return (String)this.getInternal("LINE_ID");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setBeginDate(Date BeginDate){
		this.setInternal("BEGIN_DATE" ,BeginDate );
	}


	public Date getBeginDate(){
		return (Date)this.getInternal("BEGIN_DATE");
	}
	public void setOrgCode(String OrgCode){
		this.setInternal("ORG_CODE" ,OrgCode );
	}


	public String getOrgCode(){
		return (String)this.getInternal("ORG_CODE");
	}
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setFirstUseDate(Date FirstUseDate){
		this.setInternal("FIRST_USE_DATE" ,FirstUseDate );
	}


	public Date getFirstUseDate(){
		return (Date)this.getInternal("FIRST_USE_DATE");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
	}
	public void setRepayDate(String RepayDate){
		this.setInternal("REPAY_DATE" ,RepayDate );
	}


	public String getRepayDate(){
		return (String)this.getInternal("REPAY_DATE");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
}
