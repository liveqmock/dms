package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class SeBuReturnorderNotVO extends BaseVO{
    public SeBuReturnorderNotVO(){
    	//设置字段信息
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("CLAIM_NO",BaseVO.OP_STRING);
    	this.addField("CLAIM_TYPE",BaseVO.OP_STRING);
    	this.addField("APPLY_MONTH",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("OFFICE_ORG_ID",BaseVO.OP_STRING);
    	this.addField("APPLY_USER",BaseVO.OP_STRING);
    	this.addField("CHECK_USER",BaseVO.OP_STRING);
    	this.addField("CHECK_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("CHECK_DATE", "yyyy-MM-dd");
    	this.addField("APPLY_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("RETURN_NO",BaseVO.OP_STRING);
    	this.addField("CHECK_REMARKS",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("CLAIM_ID",BaseVO.OP_STRING);
    	this.addField("NOTBACK_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("APPLY_STATUS",BaseVO.OP_STRING);
		this.bindFieldToSequence("NOTBACK_ID","COMMON");
    	this.setVOTableName("SE_BU_RETURNORDER_NOT");
}
	public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
	}
	public void setClaimNo(String ClaimNo){
		this.setInternal("CLAIM_NO" ,ClaimNo );
	}


	public String getClaimNo(){
		return (String)this.getInternal("CLAIM_NO");
	}
	public void setClaimType(String ClaimType){
		this.setInternal("CLAIM_TYPE" ,ClaimType );
	}


	public String getClaimType(){
		return (String)this.getInternal("CLAIM_TYPE");
	}
	public void setApplyMonth(String ApplyMonth){
		this.setInternal("APPLY_MONTH" ,ApplyMonth );
	}


	public String getApplyMonth(){
		return (String)this.getInternal("APPLY_MONTH");
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
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
	public void setOfficeOrgId(String OfficeOrgId){
		this.setInternal("OFFICE_ORG_ID" ,OfficeOrgId );
	}


	public String getOfficeOrgId(){
		return (String)this.getInternal("OFFICE_ORG_ID");
	}
	public void setApplyUser(String ApplyUser){
		this.setInternal("APPLY_USER" ,ApplyUser );
	}


	public String getApplyUser(){
		return (String)this.getInternal("APPLY_USER");
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
	public void setApplyDate(Date ApplyDate){
		this.setInternal("APPLY_DATE" ,ApplyDate );
	}


	public Date getApplyDate(){
		return (Date)this.getInternal("APPLY_DATE");
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
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setReturnNo(String ReturnNo){
		this.setInternal("RETURN_NO" ,ReturnNo );
	}


	public String getReturnNo(){
		return (String)this.getInternal("RETURN_NO");
	}
	public void setCheckRemarks(String CheckRemarks){
		this.setInternal("CHECK_REMARKS" ,CheckRemarks );
	}


	public String getCheckRemarks(){
		return (String)this.getInternal("CHECK_REMARKS");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setClaimId(String ClaimId){
		this.setInternal("CLAIM_ID" ,ClaimId );
	}


	public String getClaimId(){
		return (String)this.getInternal("CLAIM_ID");
	}
	public void setNotbackId(String NotbackId){
		this.setInternal("NOTBACK_ID" ,NotbackId );
	}


	public String getNotbackId(){
		return (String)this.getInternal("NOTBACK_ID");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setApplyStatus(String ApplyStatus){
		this.setInternal("APPLY_STATUS" ,ApplyStatus );
	}


	public String getApplyStatus(){
		return (String)this.getInternal("APPLY_STATUS");
	}
}
