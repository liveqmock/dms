package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuAccountVO extends BaseVO{
    public PtBuAccountVO(){
    	//ÉèÖÃ×Ö¶ÎÐÅÏ¢
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("ACCOUNT_TYPE",BaseVO.OP_STRING);
    	this.addField("BALANCE_AMOUNT",BaseVO.OP_STRING);
    	this.addField("ACCOUNT_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("ORG_NAME",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("AVAILABLE_AMOUNT",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("CLOSE_AMOUNT",BaseVO.OP_STRING);
    	this.addField("ORG_CODE",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("OCCUPY_AMOUNT",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
		this.bindFieldToSequence("ACCOUNT_ID","COMMON");
    	this.setVOTableName("PT_BU_ACCOUNT");
}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setAccountType(String AccountType){
		this.setInternal("ACCOUNT_TYPE" ,AccountType );
	}


	public String getAccountType(){
		return (String)this.getInternal("ACCOUNT_TYPE");
	}
	public void setBalanceAmount(String BalanceAmount){
		this.setInternal("BALANCE_AMOUNT" ,BalanceAmount );
	}


	public String getBalanceAmount(){
		return (String)this.getInternal("BALANCE_AMOUNT");
	}
	public void setAccountId(String AccountId){
		this.setInternal("ACCOUNT_ID" ,AccountId );
	}


	public String getAccountId(){
		return (String)this.getInternal("ACCOUNT_ID");
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
	public void setAvailableAmount(String AvailableAmount){
		this.setInternal("AVAILABLE_AMOUNT" ,AvailableAmount );
	}


	public String getAvailableAmount(){
		return (String)this.getInternal("AVAILABLE_AMOUNT");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
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
	public void setCloseAmount(String CloseAmount){
		this.setInternal("CLOSE_AMOUNT" ,CloseAmount );
	}


	public String getCloseAmount(){
		return (String)this.getInternal("CLOSE_AMOUNT");
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
	public void setOccupyAmount(String OccupyAmount){
		this.setInternal("OCCUPY_AMOUNT" ,OccupyAmount );
	}


	public String getOccupyAmount(){
		return (String)this.getInternal("OCCUPY_AMOUNT");
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
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
}
