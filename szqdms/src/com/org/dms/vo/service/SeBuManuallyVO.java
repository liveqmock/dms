package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class SeBuManuallyVO extends BaseVO{
    public SeBuManuallyVO(){
    	//设置字段信息
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("CLAIM_NO",BaseVO.OP_STRING);
    	this.addField("ORG_NAME",BaseVO.OP_STRING);
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("MANUALLY_STATUS",BaseVO.OP_STRING);
    	this.addField("MANUALLY_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("MANUALLY_TYPE",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("MANUALLY_COSTS",BaseVO.OP_STRING);
    	this.addField("MANUALLY_WAY",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("CHECK_REMARKS",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("PASS_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("PASS_DATE", "yyyy-MM-dd");
    	this.addField("ORG_CODE",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
		this.bindFieldToSequence("MANUALLY_ID","COMMON");
    	this.setVOTableName("SE_BU_MANUALLY");
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
	public void setOrgName(String OrgName){
		this.setInternal("ORG_NAME" ,OrgName );
	}


	public String getOrgName(){
		return (String)this.getInternal("ORG_NAME");
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
	public void setManuallyStatus(String ManuallyStatus){
		this.setInternal("MANUALLY_STATUS" ,ManuallyStatus );
	}


	public String getManuallyStatus(){
		return (String)this.getInternal("MANUALLY_STATUS");
	}
	public void setManuallyId(String ManuallyId){
		this.setInternal("MANUALLY_ID" ,ManuallyId );
	}


	public String getManuallyId(){
		return (String)this.getInternal("MANUALLY_ID");
	}
	public void setManuallyType(String ManuallyType){
		this.setInternal("MANUALLY_TYPE" ,ManuallyType );
	}


	public String getManuallyType(){
		return (String)this.getInternal("MANUALLY_TYPE");
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
	public void setManuallyCosts(String ManuallyCosts){
		this.setInternal("MANUALLY_COSTS" ,ManuallyCosts );
	}


	public String getManuallyCosts(){
		return (String)this.getInternal("MANUALLY_COSTS");
	}
	public void setManuallyWay(String ManuallyWay){
		this.setInternal("MANUALLY_WAY" ,ManuallyWay );
	}


	public String getManuallyWay(){
		return (String)this.getInternal("MANUALLY_WAY");
	}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
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
	public void setPassDate(Date PassDate){
		this.setInternal("PASS_DATE" ,PassDate );
	}


	public Date getPassDate(){
		return (Date)this.getInternal("PASS_DATE");
	}
	public void setOrgCode(String OrgCode){
		this.setInternal("ORG_CODE" ,OrgCode );
	}


	public String getOrgCode(){
		return (String)this.getInternal("ORG_CODE");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
}