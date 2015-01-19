package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuInvoiceEntrustVO extends BaseVO{
    public PtBuInvoiceEntrustVO(){
    	//设置字段信息
    	this.addField("TELEPHONE",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("ORG_NAME",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("ENTRUST_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("PRINT_STATUS",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("SELECT_MONTH",BaseVO.OP_STRING);
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("OPEN_BANK",BaseVO.OP_STRING);
    	this.addField("PRINT_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("PRINT_DATE", "yyyy-MM-dd");
    	this.addField("TARIFF_TYPE",BaseVO.OP_STRING);
    	this.addField("TARIFF_NO",BaseVO.OP_STRING);
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("BANK_ACCOUNT",BaseVO.OP_STRING);
    	this.addField("ORG_CODE",BaseVO.OP_STRING);
    	this.addField("ADDRESS",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("ENTRUST_NO",BaseVO.OP_STRING);
    	this.addField("USER_ACCOUNT",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
		this.bindFieldToSequence("ENTRUST_ID","COMMON");
    	this.setVOTableName("PT_BU_INVOICE_ENTRUST");
}
	public void setTelephone(String Telephone){
		this.setInternal("TELEPHONE" ,Telephone );
	}


	public String getTelephone(){
		return (String)this.getInternal("TELEPHONE");
	}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
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
	public void setEntrustId(String EntrustId){
		this.setInternal("ENTRUST_ID" ,EntrustId );
	}


	public String getEntrustId(){
		return (String)this.getInternal("ENTRUST_ID");
	}
	public void setPrintStatus(String PrintStatus){
		this.setInternal("PRINT_STATUS" ,PrintStatus );
	}


	public String getPrintStatus(){
		return (String)this.getInternal("PRINT_STATUS");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setSelectMonth(String SelectMonth){
		this.setInternal("SELECT_MONTH" ,SelectMonth );
	}


	public String getSelectMonth(){
		return (String)this.getInternal("SELECT_MONTH");
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
	public void setOpenBank(String OpenBank){
		this.setInternal("OPEN_BANK" ,OpenBank );
	}


	public String getOpenBank(){
		return (String)this.getInternal("OPEN_BANK");
	}
	public void setPrintDate(Date PrintDate){
		this.setInternal("PRINT_DATE" ,PrintDate );
	}


	public Date getPrintDate(){
		return (Date)this.getInternal("PRINT_DATE");
	}
	public void setTariffType(String TariffType){
		this.setInternal("TARIFF_TYPE" ,TariffType );
	}


	public String getTariffType(){
		return (String)this.getInternal("TARIFF_TYPE");
	}
	public void setTariffNo(String TariffNo){
		this.setInternal("TARIFF_NO" ,TariffNo );
	}


	public String getTariffNo(){
		return (String)this.getInternal("TARIFF_NO");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setBankAccount(String BankAccount){
		this.setInternal("BANK_ACCOUNT" ,BankAccount );
	}


	public String getBankAccount(){
		return (String)this.getInternal("BANK_ACCOUNT");
	}
	public void setOrgCode(String OrgCode){
		this.setInternal("ORG_CODE" ,OrgCode );
	}


	public String getOrgCode(){
		return (String)this.getInternal("ORG_CODE");
	}
	public void setAddress(String Address){
		this.setInternal("ADDRESS" ,Address );
	}


	public String getAddress(){
		return (String)this.getInternal("ADDRESS");
	}
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
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
	public void setEntrustNo(String EntrustNo){
		this.setInternal("ENTRUST_NO" ,EntrustNo );
	}


	public String getEntrustNo(){
		return (String)this.getInternal("ENTRUST_NO");
	}
	public void setUserAccount(String UserAccount){
		this.setInternal("USER_ACCOUNT" ,UserAccount );
	}


	public String getUserAccount(){
		return (String)this.getInternal("USER_ACCOUNT");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
}
